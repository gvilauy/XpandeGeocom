package org.xpande.geocom.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.core.utils.DateUtils;
import org.xpande.core.utils.PriceListUtils;
import org.xpande.financial.utils.FinancialUtils;
import org.xpande.geocom.model.MZGCVtaCtaCte;
import org.xpande.geocom.model.MZGeocomConfig;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/22/22.
 */
public class ReadCreditSales extends SvrProcess {

    private int adOrgID = 0;
    private Timestamp dateTrx = null;
    private boolean isDeleteOld = true;
    private int counter = 0;
    private MZGeocomConfig geocomConfig = null;

    @Override
    protected void prepare() {
        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase("AD_Org_ID")){
                        this.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("DateTrx")){
                        this.dateTrx = (Timestamp)para[i].getParameter();
                    }
                    else if (name.trim().equalsIgnoreCase("DeleteOld")) {
                        this.isDeleteOld = (((String) para[i].getParameter()).trim().equalsIgnoreCase("Y")) ? true : false;
                    }
                }
            }
        }
    }

    @Override
    protected String doIt() throws Exception {

        this.geocomConfig = MZGeocomConfig.getDefault(getCtx(), null);

        // Elimino información previamente generada para esta fecha y organización
        String message = this.deleteData();
        if (message != null) {
            return "@Error@ " + message;
        }
        // Cargo Ventas a Crédito
        message = this.setVentasCredito();
        if (message != null) {
            return "@Error@ " + message;
        }
        // Cargo ventas para ser informadas en formularios de DGI (2/181)
        message = this.setVentasComprobantes();
        if (message != null) {
            return "@Error@ " + message;
        }
        return "OK. Cantidad de Ventas Crédito procesadas: " + this.counter;
    }

    /**
     * Elimina información previamente generada para esta fecha y organización
     * Tanane. Created by Gabriel Vila on 2022-08-17
     * @return
     */
    private String deleteData() {

        String sql, action;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Elimino ventas a crédito si así lo indica el usuario
            if (this.isDeleteOld) {
                // Obtengo primero invoices generadas y asociadas con ventas a credito
                sql = " select c_invoice_id from Z_GC_VtaCtaCte " +
                        " where ad_org_id = " + this.adOrgID +
                        " and datetrx ='" + this.dateTrx + "' ";

                pstmt = DB.prepareStatement(sql, get_TrxName());
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    // Intento reactivar y luego eliminar esta invoice
                    MInvoice invoice = new MInvoice(getCtx(), rs.getInt("c_invoice_id"), get_TrxName());
                    if ((invoice != null) && (invoice.get_ID() > 0)) {
                        if (!invoice.processIt(DocAction.ACTION_ReActivate)) {
                            String message = invoice.getProcessMsg();
                            if ((message == null) || (message.trim().equalsIgnoreCase(""))) {
                                message = "No se pudo reactivar el comprobante de venta numero: " + invoice.getDocumentNo();
                            }
                            return message;
                        }
                        invoice.saveEx();
                        invoice.deleteEx(true);
                    }
                }
                // Actualizo tickets como no procesados
                action = " update z_gc_interfacevta set iscreditreaded ='N' " +
                        " where z_gc_interfacevta_id in " +
                        " (select z_gc_interfacevta_id from Z_GC_VtaCtaCte " +
                        " where ad_org_id = " + this.adOrgID +
                        " and datetrx ='" + this.dateTrx + "') ";
                DB.executeUpdateEx(action, get_TrxName());

                // Elimino ahora si las ventas a credito
                action = " delete from Z_GC_VtaCtaCte " +
                        " where ad_org_id = " + this.adOrgID +
                        " and datetrx ='" + this.dateTrx + "' ";
                DB.executeUpdateEx(action, get_TrxName());
            }
        }
        catch (Exception e) {
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
        }
        return null;
    }

    /***
     * Obtengo ventas por credito de la casa desde POS y genero los documentos correspondientes en ADempiere.
     * Xpande. Created by Gabriel Vila on 4/30/20.
     */
    private String setVentasCredito() {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            Date dateFechaAux = new Date(this.dateTrx.getTime());
            dateFechaAux =  DateUtils.addDays(dateFechaAux, 1);
            Timestamp dateTo2 = new Timestamp(dateFechaAux.getTime());

            sql = " select a.z_gc_interfacevta_id, a.fechaticket, a.nroticket, coalesce(a.c_bpartner_id, b.c_bpartner_id) as c_bpartner_id, " +
                    " a.tipocfe, a.numerocfe, a.seriecfe, " +
                    " a.iso_code, a.codcaja, a.codcajero, sum(b.totalamt) as totalamt " +
                    " from z_gc_interfacevta a " +
                    " inner join z_gc_interfacevtapago b on a.z_gc_interfacevta_id = b.z_gc_interfacevta_id " +
                    " inner join z_mediopagopos mpos on (b.codmediopago = mpos.codmediopagopos and mpos.z_posvendor_id = 1000002) " +
                    " inner join z_mediopago mp on mp.z_mediopago_id = mpos.z_mediopago_id " +
                    " where a.ad_org_id =" + this.adOrgID +
                    " and a.fechaticket between '" + this.dateTrx + "' and '" + dateTo2 + "' " +
                    " and a.iscreditreaded = 'N' " +
                    " and mp.isventacredito ='Y'" +
                    " group by 1,2,3,4,5,6,7,8,9,10 ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                // Obtengo medio de pago pos
                sql = " select max(a.codmediopago) as codmediopago from z_gc_interfacevtapago a " +
                        " inner join z_mediopagopos mpos on (a.codmediopago = mpos.codmediopagopos and mpos.z_posvendor_id = 1000002) " +
                        " inner join z_mediopago mp on mp.z_mediopago_id = mpos.z_mediopago_id " +
                        " where z_gc_interfacevta_id =" + rs.getInt("z_gc_interfacevta_id") +
                        " and mp.isventacredito ='Y' ";
                String codMediPagoAux = DB.getSQLValueStringEx(get_TrxName(), sql);

                // Obtengo producto asociado al medio de pago
                sql = " select max(m_product_id) as m_product_id from Z_MedioPago_Acct where z_mediopago_id in " +
                        " (select z_mediopago_id from z_mediopagopos where codmediopagopos='" + codMediPagoAux + "' and z_posvendor_id = 1000002) ";
                int mProductIDAux = DB.getSQLValueEx(get_TrxName(), sql);
                if (mProductIDAux <= 0){
                    return "No se obtuvo producto para el medio de pago pos: " + codMediPagoAux;
                }
                MProduct product = new MProduct(getCtx(), mProductIDAux, get_TrxName());
                if ((product == null) || (product.get_ID() <= 0)){
                    return null;
                }
                // Impuesto asociado al producto
                MTaxCategory taxCategory = (MTaxCategory) product.getC_TaxCategory();
                if ((taxCategory == null) || (taxCategory.get_ID() <= 0)){
                    return null;
                }
                // Obtengo impuesto asociado al producto para este tipo de documentos
                MTax taxProduct = taxCategory.getDefaultTax();
                if ((taxProduct == null) || (taxProduct.get_ID() <= 0)){
                    return null;
                }

                int cCurrencyID = 142;
                if ((!rs.getString("iso_code").equalsIgnoreCase("858")) && (!rs.getString("iso_code").equalsIgnoreCase("UYU"))){
                    cCurrencyID = 100;
                }

                BigDecimal amtTotal = rs.getBigDecimal("totalamt");
                if (amtTotal == null) amtTotal = Env.ZERO;

                MZGCVtaCtaCte vtaCtaCte = new MZGCVtaCtaCte(getCtx(), 0, get_TrxName());
                vtaCtaCte.setZ_GC_InterfaceVta_ID(rs.getInt("Z_GC_InterfaceVta_ID"));
                vtaCtaCte.setAD_Org_ID(this.adOrgID);
                vtaCtaCte.set_ValueOfColumn("AD_Client_ID", this.geocomConfig.getAD_Client_ID());
                vtaCtaCte.setDateTrx(this.dateTrx);
                vtaCtaCte.setTipoCFE(rs.getString("tipocfe"));
                vtaCtaCte.setNumeroCFE(rs.getString("numerocfe"));
                vtaCtaCte.setSerieCFE(rs.getString("seriecfe"));
                vtaCtaCte.setTotalAmt(amtTotal);
                vtaCtaCte.setcodcaja(rs.getString("codcaja"));
                vtaCtaCte.setcodcajero(rs.getString("codcajero"));
                vtaCtaCte.setC_Currency_ID(cCurrencyID);
                vtaCtaCte.setfechaticket(rs.getTimestamp("fechaticket"));
                vtaCtaCte.setNroTicket(rs.getString("nroticket"));
                vtaCtaCte.setIsVentaEmpresa(false);

                if (amtTotal.compareTo(Env.ZERO) == 0){
                    vtaCtaCte.setIsExecuted(false);
                    vtaCtaCte.setErrorMsg("Venta con importe CERO");
                    vtaCtaCte.saveEx();
                    continue;
                }
                if (amtTotal.compareTo(Env.ZERO) < 0){
                    amtTotal = amtTotal.negate();
                }

                // Determino tipo de documento segun tipo cfe obtenido
                int cDocTypeID = 0;
                String tipoCFE = String.valueOf(rs.getString("tipocfe"));
                String descCFE ="", numeroComprobante= "";

                // e-ticket o e-factura
                if ((tipoCFE.equalsIgnoreCase("101")) || (tipoCFE.equalsIgnoreCase("111"))){
                    cDocTypeID = this.geocomConfig.getDefaultDocPosARI_ID();

                    if (tipoCFE.equalsIgnoreCase("101")){
                        descCFE ="E-Ticket";
                    }
                    else {
                        descCFE ="E-Factura";
                    }
                }
                // e-ticket nc o e-factura nc
                else if ((tipoCFE.equalsIgnoreCase("102")) || (tipoCFE.equalsIgnoreCase("112"))){
                    cDocTypeID = this.geocomConfig.getDefaultDocPosARC_ID();

                    if (tipoCFE.equalsIgnoreCase("102")){
                        descCFE ="E-Ticket Nota de Crédito";
                    }
                    else {
                        descCFE ="E-Factura Nota de Crédito";
                    }
                }
                // e-ticket nd o e-factura nd
                else if ((tipoCFE.equalsIgnoreCase("103")) || (tipoCFE.equalsIgnoreCase("113"))){
                    /*
                    cDocTypeID = this.geocomConfig.getDefaultDocPosNDARI_ID();

                    if (tipoCFE.equalsIgnoreCase("103")){
                        descCFE ="E-Ticket Nota de Débito";
                    }
                    else {
                        descCFE ="E-Factura Nota de Débito";
                    }
                    */
                }

                if (cDocTypeID <= 0){
                    vtaCtaCte.setIsExecuted(false);
                    vtaCtaCte.setErrorMsg("No se pudo obtener tipo de documento a considerar desde configuración scanntech");
                    vtaCtaCte.saveEx();
                    continue;
                }
                MDocType docType = new MDocType(getCtx(), cDocTypeID, null);

                int cBPartnerID = rs.getInt("c_bpartner_id");

                if (cBPartnerID <= 0){
                    // Tomo socio de cobro a domicilio
                    cBPartnerID = this.geocomConfig.get_ValueAsInt("c_bpartner_id");
                    // No tengo socio de negocio, no hago nada.
                    //vtaCtaCte.setIsExecuted(false);
                    //vtaCtaCte.setErrorMsg("Venta sin Socio de Negocio asociado");
                    //vtaCtaCte.saveEx();
                    //continue;
                }
                MBPartner partner = new MBPartner(getCtx(), cBPartnerID, null);
                MBPartnerLocation[] partnerLocations = partner.getLocations(true);
                if (partnerLocations.length <= 0){
                    vtaCtaCte.setIsExecuted(false);
                    vtaCtaCte.setErrorMsg("Socio de Negocio no tiene Localización configurada");
                    vtaCtaCte.saveEx();
                    continue;
                }
                MBPartnerLocation partnerLocation = partnerLocations[0];

                MPaymentTerm paymentTerm = FinancialUtils.getPaymentTermByDefault(getCtx(), null);
                if ((paymentTerm == null) || (paymentTerm.get_ID() <= 0)){
                    vtaCtaCte.setIsExecuted(false);
                    vtaCtaCte.setErrorMsg("No se pudo obtener Término de Pago por defecto.");
                    vtaCtaCte.saveEx();
                    continue;
                }

                numeroComprobante = rs.getString("seriecfe") + rs.getString("numerocfe");
                numeroComprobante = numeroComprobante.trim();

                MInvoice invoice = new MInvoice(getCtx(), 0, get_TrxName());
                invoice.set_ValueOfColumn("AD_Client_ID", this.geocomConfig.getAD_Client_ID());
                invoice.setAD_Org_ID(this.adOrgID);
                invoice.setIsSOTrx(true);
                invoice.setC_DocTypeTarget_ID(cDocTypeID);
                invoice.setC_DocType_ID(cDocTypeID);
                invoice.setDocumentNo(numeroComprobante);
                invoice.setDescription("Generado desde POS. Datos CFE : " + descCFE + " " + numeroComprobante +
                        " - Ticket: " + rs.getString("nroticket"));
                if (docType.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_ARCredit)){
                    invoice.set_ValueOfColumn("ReferenciaCFE", "Referencia Comprobante POS");
                }

                Timestamp fechaDoc = TimeUtil.trunc(this.dateTrx, TimeUtil.TRUNC_DAY);
                invoice.setDateInvoiced(fechaDoc);
                invoice.setDateAcct(fechaDoc);
                invoice.setC_BPartner_ID(partner.get_ID());
                invoice.setC_BPartner_Location_ID(partnerLocation.get_ID());
                invoice.setC_Currency_ID(cCurrencyID);
                invoice.setPaymentRule(X_C_Invoice.PAYMENTRULE_OnCredit);
                invoice.setC_PaymentTerm_ID(paymentTerm.get_ID());

                MPriceList priceList = PriceListUtils.getPriceListByOrg(getCtx(), invoice.getAD_Client_ID(), invoice.getAD_Org_ID(),
                        invoice.getC_Currency_ID(), true, null, null);
                if ((priceList == null) || (priceList.get_ID() <= 0)){
                    vtaCtaCte.setIsExecuted(false);
                    vtaCtaCte.setErrorMsg("No se pudo obtener Lista de Precios para esta organización - moneda.");
                    vtaCtaCte.saveEx();
                    continue;
                }

                invoice.setM_PriceList_ID(priceList.get_ID());
                invoice.setIsTaxIncluded(priceList.isTaxIncluded());
                invoice.set_ValueOfColumn("DocBaseType", docType.getDocBaseType());
                invoice.set_ValueOfColumn("EstadoAprobacion", "APROBADO");
                invoice.set_ValueOfColumn("TipoFormaPago", "CREDITO");
                invoice.set_ValueOfColumn("AmtSubtotal", amtTotal);
                invoice.setTotalLines(amtTotal);
                invoice.setGrandTotal(amtTotal);
                invoice.saveEx();

                // Linea de Factura
                MInvoiceLine line = new MInvoiceLine(invoice);
                line.set_ValueOfColumn("AD_Client_ID", invoice.getAD_Client_ID());
                line.set_ValueOfColumn("IsBySelection", true);
                line.setAD_Org_ID(invoice.getAD_Org_ID());
                line.setM_Product_ID(product.get_ID());
                line.setC_UOM_ID(product.getC_UOM_ID());
                line.setQtyEntered(Env.ONE);
                line.setQtyInvoiced(Env.ONE);
                line.setPriceEntered(amtTotal);
                line.setPriceActual(amtTotal);
                line.setLineNetAmt(amtTotal);
                line.setC_Tax_ID(taxProduct.get_ID());
                line.setTaxAmt();
                line.setLineNetAmt();
                line.set_ValueOfColumn("AmtSubTotal", invoice.getTotalLines());
                line.saveEx();

                if (!invoice.processIt(DocAction.ACTION_Complete)){
                    String message = "";
                    if (invoice.getProcessMsg() != null) message = invoice.getProcessMsg();
                    System.out.println("No se pudo completar Invoice en Venta Crédito Geocom : " + message);
                    vtaCtaCte.setIsExecuted(false);
                    vtaCtaCte.setErrorMsg("Error al completar Invoice : " + message);
                }
                else{
                    invoice.saveEx();
                    vtaCtaCte.setIsExecuted(true);
                }
                if (invoice.get_ID() > 0){
                    vtaCtaCte.setC_Invoice_ID(invoice.get_ID());
                }

                vtaCtaCte.setC_BPartner_ID(invoice.getC_BPartner_ID());
                vtaCtaCte.setC_BPartner_Location_ID(invoice.getC_BPartner_Location_ID());
                vtaCtaCte.setC_BP_Group_ID(partner.getC_BP_Group_ID());
                vtaCtaCte.saveEx();

                String action = " update z_gc_interfacevta set iscreditreaded ='Y' " +
                        " where z_gc_interfacevta_id =" + vtaCtaCte.getZ_GC_InterfaceVta_ID();
                DB.executeUpdateEx(action, get_TrxName());
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }
        return null;
    }

    /***
     * Obtengo información de comprobantes de venta: eFactura, eFacturaNC y eFacturaND desde POS.
     * No se generan invoices en el sistema. Esto es solo para luego poder utilizar este info en
     * la generación de formularios para DGI (Ej: 2/181)
     * Xpande. Created by Gabriel Vila on 7/10/20.
     */
    private String setVentasComprobantes() {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            Date dateFechaAux = new Date(this.dateTrx.getTime());
            dateFechaAux =  DateUtils.addDays(dateFechaAux, 1);
            Timestamp dateTo2 = new Timestamp(dateFechaAux.getTime());

            sql = " select a.z_gc_interfacevta_id, a.c_bpartner_id, a.tipocfe, a.seriecfe, a.numerocfe, " +
                    " a.iso_code, a.codcaja, a.codcajero, a.fechaticket, a.nroticket, " +
                    " a.totalamt, sdgi.c_doctype_id " +
                    " from z_gc_interfacevta a " +
                    " left outer join z_cfe_configdocdgi dgi on a.tipocfe = dgi.codigodgi " +
                    " left outer join z_cfe_configdocsend sdgi on (dgi.z_cfe_configdocdgi_id = sdgi.z_cfe_configdocdgi_id and sdgi.ad_orgtrx_id = a.ad_org_id) " +
                    " where a.ad_org_id =" + this.adOrgID +
                    " and a.fechaticket between '" + this.dateTrx + "' and '" + dateTo2 + "' " +
                    " and a.istaxreaded = 'N' " +
                    " and a.tipocfe in ('111', '112', '113') ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                int cCurrencyID = 142;
                if ((!rs.getString("iso_code").equalsIgnoreCase("858")) && (!rs.getString("iso_code").equalsIgnoreCase("UYU"))){
                    cCurrencyID = 100;
                }

                BigDecimal amtTotal = rs.getBigDecimal("totalamt");
                if (amtTotal == null) amtTotal = Env.ZERO;

                MZGCVtaCtaCte vtaCtaCte = new MZGCVtaCtaCte(getCtx(), 0, get_TrxName());
                vtaCtaCte.setZ_GC_InterfaceVta_ID(rs.getInt("Z_GC_InterfaceVta_ID"));
                vtaCtaCte.setAD_Org_ID(this.adOrgID);
                vtaCtaCte.set_ValueOfColumn("AD_Client_ID", this.geocomConfig.getAD_Client_ID());
                vtaCtaCte.setDateTrx(this.dateTrx);
                vtaCtaCte.setTipoCFE(rs.getString("tipocfe"));
                vtaCtaCte.setNumeroCFE(rs.getString("numerocfe"));
                vtaCtaCte.setSerieCFE(rs.getString("seriecfe"));
                vtaCtaCte.setTotalAmt(amtTotal);
                vtaCtaCte.setcodcaja(rs.getString("codcaja"));
                vtaCtaCte.setcodcajero(rs.getString("codcajero"));
                vtaCtaCte.setC_Currency_ID(cCurrencyID);
                vtaCtaCte.setfechaticket(rs.getTimestamp("fechaticket"));
                vtaCtaCte.setNroTicket(rs.getString("nroticket"));
                vtaCtaCte.setIsExecuted(true);
                vtaCtaCte.setIsVentaEmpresa(true);

                if (rs.getInt("c_doctype_id") > 0){
                    vtaCtaCte.setC_DocType_ID(rs.getInt("c_doctype_id"));
                }

                if (amtTotal.compareTo(Env.ZERO) == 0){
                    vtaCtaCte.setIsExecuted(false);
                    vtaCtaCte.setErrorMsg("Venta con importe CERO");
                    vtaCtaCte.saveEx();
                    continue;
                }
                if (amtTotal.compareTo(Env.ZERO) < 0){
                    amtTotal = amtTotal.negate();
                }

                // Determino tipo de documento segun tipo cfe obtenido
                String tipoCFE = rs.getString("tipocfe");
                String descCFE ="", numeroComprobante= "";

                // e-ticket o e-factura
                if ((tipoCFE.equalsIgnoreCase("101")) || (tipoCFE.equalsIgnoreCase("111"))){
                    if (tipoCFE.equalsIgnoreCase("101")){
                        descCFE ="E-Ticket";
                    }
                    else {
                        descCFE ="E-Factura";
                    }
                }
                // e-ticket nc o e-factura nc
                else if ((tipoCFE.equalsIgnoreCase("102")) || (tipoCFE.equalsIgnoreCase("112"))){
                    if (tipoCFE.equalsIgnoreCase("102")){
                        descCFE ="E-Ticket Nota de Crédito";
                    }
                    else {
                        descCFE ="E-Factura Nota de Crédito";
                    }
                }
                // e-ticket nd o e-factura nd
                else if ((tipoCFE.equalsIgnoreCase("103")) || (tipoCFE.equalsIgnoreCase("113"))){
                    if (tipoCFE.equalsIgnoreCase("103")){
                        descCFE ="E-Ticket Nota de Débito";
                    }
                    else {
                        descCFE ="E-Factura Nota de Débito";
                    }
                }

                int cBParnterID = rs.getInt("c_bpartner_id");
                if (cBParnterID <= 0) {
                    //vtaCtaCte.setIsExecuted(false);
                    //vtaCtaCte.setErrorMsg("Venta sin Socio de Negocio asociado");
                    //vtaCtaCte.saveEx();
                    continue;
                }
                MBPartner partner = new MBPartner(getCtx(), cBParnterID, null);
                MBPartnerLocation[] partnerLocations = partner.getLocations(true);
                if (partnerLocations.length <= 0){
                    vtaCtaCte.setIsExecuted(false);
                    vtaCtaCte.setErrorMsg("Socio de Negocio no tiene Localización configurada");
                    vtaCtaCte.saveEx();
                    continue;
                }
                MBPartnerLocation partnerLocation = partnerLocations[0];
                MPaymentTerm paymentTerm = FinancialUtils.getPaymentTermByDefault(getCtx(), null);
                if ((paymentTerm == null) || (paymentTerm.get_ID() <= 0)){
                    vtaCtaCte.setIsExecuted(false);
                    vtaCtaCte.setErrorMsg("No se pudo obtener Término de Pago por defecto.");
                    vtaCtaCte.saveEx();
                    continue;
                }
                vtaCtaCte.setC_BPartner_ID(partner.get_ID());
                vtaCtaCte.setC_BPartner_Location_ID(partnerLocation.get_ID());
                vtaCtaCte.setC_BP_Group_ID(partner.getC_BP_Group_ID());
                vtaCtaCte.saveEx();

                String action = " update z_gc_interfacevta set istaxreaded ='Y' where z_gc_interfacevta_id =" + vtaCtaCte.getZ_GC_InterfaceVta_ID();
                DB.executeUpdateEx(action, get_TrxName());
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }
        return null;
    }
}

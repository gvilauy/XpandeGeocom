package org.xpande.geocom.utils;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.comercial.model.MZProdSalesOffer;
import org.xpande.core.model.I_Z_ProductoUPC;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.core.utils.FileUtils;
import org.xpande.core.utils.PriceListUtils;
import org.xpande.geocom.model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/23/21.
 */
public class ProcesadorInterfaceOut {

    private Properties ctx = null;
    private String trxName = null;
    private boolean reProcessByComPos = false;
    /***
     * Constructor
     * @param ctx
     * @param trxName
     */
    public ProcesadorInterfaceOut(Properties ctx, String trxName) {
        this.ctx = ctx;
        this.trxName = trxName;
    }

    /***
     * Ejecuta proceso de interface de salida para Sisteco.
     * En caso de venir un ID correspondiente un proceso de comunicacin de datos al pos, entonces verifico que datos debo enviar o no.
     * En caso de no venir dicho ID, considero los flags que indican si debo procesar lo correspondiente a productos y socios.
     * Xpande. Created by Gabriel Vila on 7/27/17.
     * @param adOrgID
     * @param zComunicacionPosID
     * @param processPrices
     * @param processProducts
     * @param processPartners
     * @return
     */
    public String executeInterfaceOut(int adOrgID, int zComunicacionPosID, boolean processPrices, boolean processProducts, boolean processPartners){

        try{
            // Si no tengo id de proceso de comunicacion
            if (zComunicacionPosID <= 0){
                // Si no recibo flags de procesar productos o socios, no hago nada
                if (!processProducts && !processPartners){
                    return null;
                }
            }
            else{
                // Siempre comunico socios si recibo ID de proceso de comunicacion de datos al pos
                processPartners = true;
            }

            // Proceso lineas de interface de salida correspondiente a productos
            if ((zComunicacionPosID > 0) || (processProducts)){
                String message = this.executeInterfaceOutProducts(adOrgID, zComunicacionPosID, processPrices);
                if (message != null) return message;
            }

            // Proces lineas de socios de negocio
            if (processPartners){
                String message = this.executeInterfaceOutPartners(adOrgID, zComunicacionPosID);
                if (message != null) return message;
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        return null;
    }

    private String executeInterfaceOutProducts(int adOrgID, int zComunicacionPosID, boolean processPrices) {

        String action = "";

        HashMap<Integer, Integer> hashProds = new HashMap<Integer, Integer>();

        try{
            Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

            // Obtengo y recorro lineas de interface de productos, segun si es proceso o reproceso de interface
            List<MZGeocomInterfaceOut> interfaceOuts = null;
            if (!this.reProcessByComPos){
                // Lineas de interface aun no ejecutadas
                interfaceOuts = this.getLinesProdsNotExecuted(adOrgID, zComunicacionPosID, processPrices);
            }
            else{
                // Lineas de interface ya ejecutadas anteriormente para reprocesar, asociadas a un ID de Comunicación al POS.
                interfaceOuts = this.getLinesProdsByComPos(adOrgID, zComunicacionPosID, processPrices);
            }
            for (MZGeocomInterfaceOut interfaceOut: interfaceOuts){
                MZGCInterfaceOut gcOut = new MZGCInterfaceOut(this.ctx, 0, this.trxName);
                gcOut.setAD_Org_ID(interfaceOut.getAD_OrgTrx_ID());
                gcOut.setCRUDType(interfaceOut.getCRUDType());
                gcOut.setM_Product_ID(interfaceOut.getRecord_ID());

                MProduct product = (MProduct) gcOut.getM_Product();
                MTaxCategory taxCategory = (MTaxCategory) product.getC_TaxCategory();
                gcOut.setCodImpuestoPOS(taxCategory.getCommodityCode());

                gcOut.setISO_Code("UYU");
                gcOut.setTipoDatoInterface("P");
                gcOut.setDateTrx(fechaHoy);

                // Me aseguro que para Geocom, siempre vaya el precio de venta
                BigDecimal priceSO = Env.ZERO;
                if ((interfaceOut.getPriceSO() != null) && (interfaceOut.getPriceSO().compareTo(Env.ZERO) > 0)){
                    priceSO = interfaceOut.getPriceSO();
                }
                else{
                    MPriceList priceList = null;
                    if (interfaceOut.getM_PriceList_ID() > 0){
                        priceList = (MPriceList) interfaceOut.getM_PriceList();
                    }
                    else{
                        priceList = PriceListUtils.getPriceListByOrg(ctx, interfaceOut.getAD_Client_ID(), interfaceOut.getAD_OrgTrx_ID(),
                                142, true, true, null);
                    }
                    MPriceListVersion plVersion = priceList.getPriceListVersion(null);
                    MProductPrice productPrice = MProductPrice.get(ctx, plVersion.get_ID(), interfaceOut.getRecord_ID(), null);
                    if (productPrice != null){
                        priceSO = productPrice.getPriceStd();
                    }
                    // Si tengo oferta de venta vigente para este producto y organización me aseguro de setear este precio de oferta
                    // De esta manera la marca se crea pero el precio es el de oferta
                    BigDecimal salesOfferPrice = null;
                    Timestamp today = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
                    String sql = " select max(z_prodsalesoffer_id) as z_prodsalesoffer_id " +
                            " from z_prodsalesoffer " +
                            " where ad_org_id =" + priceList.getAD_Org_ID() +
                            " and m_product_id =" + product.get_ID() +
                            " and enddate >= '" + today + "' ";
                    int offerID = DB.getSQLValueEx(trxName, sql);
                    if (offerID > 0) {
                        MZProdSalesOffer prodSalesOffer = new MZProdSalesOffer(ctx, offerID, null);
                        priceSO = prodSalesOffer.getPrice();
                    }
                }
                gcOut.setPrice(priceSO);
                gcOut.saveEx();

                interfaceOut.set_ValueOfColumn("Z_GCInterfaceOut_ID", gcOut.get_ID());
                interfaceOut.setIsExecuted(true);
                if (zComunicacionPosID > 0){
                    if (interfaceOut.getZ_ComunicacionPOS_ID() <= 0){
                        interfaceOut.setZ_ComunicacionPOS_ID(zComunicacionPosID);
                    }
                }
                interfaceOut.saveEx();

                // Marco el producto como creado en POS si es que ya no esta ahí
                MZGCProdOrgPos prodOrgPos = MZGCProdOrgPos.getByProdOrg(ctx, gcOut.getM_Product_ID(), gcOut.getAD_Org_ID(), trxName);
                if ((prodOrgPos == null) || (prodOrgPos.get_ID() <= 0)){
                    prodOrgPos = new MZGCProdOrgPos(ctx, 0, trxName);
                    prodOrgPos.setAD_Org_ID(gcOut.getAD_Org_ID());
                    prodOrgPos.setM_Product_ID(gcOut.getM_Product_ID());
                    prodOrgPos.saveEx();
                }
            }

            // Obtengo y recorro lineas de interface de codigos de barra, segun si es proceso o reproceso de interface
            if (!this.reProcessByComPos){
                // Lineas de interface aun no ejecutadas
                interfaceOuts = this.getLinesUPCNotExecuted(adOrgID);
            }
            else{
                // Lineas de interface ya ejecutadas anteriormente y asociadas a un ID de comunicacion de POS.
                interfaceOuts = this.getLinesUPCByComPos(adOrgID, zComunicacionPosID);
            }
            for (MZGeocomInterfaceOut interfaceOut: interfaceOuts){

                MZProductoUPC productoUPC = new MZProductoUPC(this.ctx, interfaceOut.getRecord_ID(), this.trxName);

                // Si el producto asociado a esta barra NO esta creado en el POS, no comunico la barra.
                MZGCProdOrgPos prodOrgPos = MZGCProdOrgPos.getByProdOrg(ctx, productoUPC.getM_Product_ID(), interfaceOut.getAD_OrgTrx_ID(), trxName);
                if ((prodOrgPos == null) || (prodOrgPos.get_ID() <= 0)){
                    continue;
                }

                MZGCInterfaceOut gcOut = new MZGCInterfaceOut(this.ctx, 0, this.trxName);
                gcOut.setAD_Org_ID(interfaceOut.getAD_OrgTrx_ID());
                gcOut.setCRUDType(interfaceOut.getCRUDType());
                gcOut.setM_Product_ID(productoUPC.getM_Product_ID());

                MProduct product = (MProduct) gcOut.getM_Product();
                MTaxCategory taxCategory = (MTaxCategory) product.getC_TaxCategory();
                gcOut.setCodImpuestoPOS(taxCategory.getCommodityCode());

                gcOut.setISO_Code("UYU");
                gcOut.setPrice(interfaceOut.getPriceSO());
                gcOut.setTipoDatoInterface("B");
                gcOut.setDateTrx(fechaHoy);
                gcOut.setUPC(productoUPC.getUPC());
                gcOut.saveEx();

                interfaceOut.set_ValueOfColumn("Z_GCInterfaceOut_ID", gcOut.get_ID());
                interfaceOut.setIsExecuted(true);
                if (zComunicacionPosID > 0){
                    if (interfaceOut.getZ_ComunicacionPOS_ID() <= 0){
                        interfaceOut.setZ_ComunicacionPOS_ID(zComunicacionPosID);
                    }
                }
                interfaceOut.saveEx();
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        return null;
    }

    /***
     * Obtiene y retorna lineas de interface de salida para productos no ejecutadas al momento.
     * En caso de recibir un id de proceso de comunicacion de datos al pos, debo filtrar segun proceso o no precios.
     * Xpande. Created by Gabriel Vila on 7/24/17.
     * @return
     * @param adOrgID
     * @param zComunicacionPosID
     * @param processPrices
     */
    private List<MZGeocomInterfaceOut> getLinesProdsNotExecuted(int adOrgID, int zComunicacionPosID, boolean processPrices){

        String whereClause = X_Z_GeocomInterfaceOut.COLUMNNAME_IsExecuted + " ='N' " +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_AD_Table_ID + " =" + I_M_Product.Table_ID +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_AD_OrgTrx_ID + " =" + adOrgID;

        Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

        // Si recibo ID de proceso de comunicacion de datos al pos
        if (zComunicacionPosID > 0){
            // Si en este proceso No se quiere comunicar precios de productos
            if (!processPrices){
                // No proceso ninguna marca de crear o actualizar productos. Solo considero las marcas de eliminar y aquellas marcas de
                // actualización pero que no sean por cambios de precio
                whereClause += " AND ((" + X_Z_GeocomInterfaceOut.COLUMNNAME_CRUDType + " ='" + X_Z_GeocomInterfaceOut.CRUDTYPE_DELETE + "') " +
                        " OR (" + X_Z_GeocomInterfaceOut.COLUMNNAME_CRUDType + " ='" + X_Z_GeocomInterfaceOut.CRUDTYPE_UPDATE + "'" +
                        " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_IsPriceChanged + " ='N')) ";
            }
            else {
                // Solo debo conisderar marcas de aquellos productos contenidos en el proceso de comunicacion de datos al pos.
                whereClause += " AND ((" + X_Z_GeocomInterfaceOut.COLUMNNAME_Record_ID + " IN " +
                        " (select m_product_id from z_confirmacionetiquetaprod " +
                        " where WithOfferSO ='N' and z_confirmacionetiquetadoc_id in " +
                        " (select z_confirmacionetiquetadoc_id from z_confirmacionetiquetadoc " +
                        " where comunicadopos='N' and isselected='Y' and isconfirmed='Y' " +
                        " and ((DateToPos is null) or (DateToPos <='" + fechaHoy + "')) " +
                        " and z_confirmacionetiqueta_id in " +
                        " (select z_confirmacionetiqueta_id from z_confirmacionetiqueta where z_comunicacionpos_id =" + zComunicacionPosID + ")))) ";

                whereClause += " OR (((" + X_Z_GeocomInterfaceOut.COLUMNNAME_CRUDType + " ='" + X_Z_GeocomInterfaceOut.CRUDTYPE_DELETE + "') " +
                        " OR (" + X_Z_GeocomInterfaceOut.COLUMNNAME_CRUDType + " ='" + X_Z_GeocomInterfaceOut.CRUDTYPE_UPDATE + "'" +
                        " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_IsPriceChanged + " ='N')))) ";
            }
        }

        List<MZGeocomInterfaceOut> lines = new Query(ctx, I_Z_GeocomInterfaceOut.Table_Name, whereClause, trxName).setOrderBy(" SeqNo, Created  ").list();

        return lines;
    }
    /***
     * Procesa interface de salida de clientes para Sisteco.
     * Xpande. Created by Gabriel Vila on 10/9/17.
     * @param adOrgID
     * @param zComunicacionPosID
     * @param bufferedWriterBatch
     * @param bufferedWriterOnline
     * @param sistecoConfig
     * @return
     */
    private String executeInterfaceOutPartners(int adOrgID, int zComunicacionPosID) {

        String message = null;

        try{
            Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

            // Obtengo y recorro lineas de interface de socios de negocio, segun si es proceso o reproceso de interface
            List<MZGeocomInterfaceOut> interfaceOuts = null;
            if (!this.reProcessByComPos){
                // Lineas de interface aun no ejecutadas
                interfaceOuts = this.getLinesBPartnerNotExecuted(adOrgID);
            }
            else{
                // Lineas de interface ya ejecutadas anteriormente y asociadas a un ID de comunicacion al POS.
                interfaceOuts = this.getLinesBPartnerByComPos(adOrgID, zComunicacionPosID);
            }

            for (MZGeocomInterfaceOut interfaceOut: interfaceOuts){
                MZGCInterfaceOut gcOut = new MZGCInterfaceOut(this.ctx, 0, this.trxName);
                gcOut.setAD_Org_ID(interfaceOut.getAD_OrgTrx_ID());
                gcOut.setCRUDType(interfaceOut.getCRUDType());
                gcOut.setC_BPartner_ID(interfaceOut.getRecord_ID());
                gcOut.setTipoDatoInterface("C");
                gcOut.setDateTrx(fechaHoy);
                gcOut.saveEx();

                interfaceOut.set_ValueOfColumn("Z_GCInterfaceOut_ID", gcOut.get_ID());
                interfaceOut.setIsExecuted(true);
                if (zComunicacionPosID > 0){
                    if (interfaceOut.getZ_ComunicacionPOS_ID() <= 0){
                        interfaceOut.setZ_ComunicacionPOS_ID(zComunicacionPosID);
                    }
                }
                interfaceOut.saveEx();
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        return message;
    }

    /***
     * Obtiene y retorna lineas de interface correspondientes a productos segun un determinado ID de comunicacio a POS.
     * No importa si ya fueron ejecutadas anteriormente o no.
     * Xpande. Created by Gabriel Vila on 3/27/18.
     * @param adOrgID
     * @param zComunicacionPosID
     * @param processPrices
     * @return
     */
    private List<MZGeocomInterfaceOut> getLinesProdsByComPos(int adOrgID, int zComunicacionPosID, boolean processPrices){

        String whereClause = X_Z_GeocomInterfaceOut.COLUMNNAME_Z_ComunicacionPOS_ID + " =" + zComunicacionPosID +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_AD_Table_ID + " =" + I_M_Product.Table_ID +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_AD_OrgTrx_ID + " =" + adOrgID;

        List<MZGeocomInterfaceOut> lines = new Query(ctx, I_Z_GeocomInterfaceOut.Table_Name, whereClause, trxName).setOrderBy(" SeqNo, Created  ").list();

        return lines;

    }

    /***
     * Obtiene y retorna lineas de interface de salida para códigos de barras no ejecutadas al momento.
     * Xpande. Created by Gabriel Vila on 7/24/17.
     * @param adOrgID
     * @return
     */
    private List<MZGeocomInterfaceOut> getLinesUPCNotExecuted(int adOrgID){

        String whereClause = X_Z_GeocomInterfaceOut.COLUMNNAME_IsExecuted + " ='N' " +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_AD_Table_ID + " =" + I_Z_ProductoUPC.Table_ID +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_AD_OrgTrx_ID + " =" + adOrgID;

        List<MZGeocomInterfaceOut> lines = new Query(ctx, I_Z_GeocomInterfaceOut.Table_Name, whereClause, trxName).setOrderBy(" SeqNo, Created  ").list();

        return lines;

    }


    /***
     * Obtiene y retorna lineas de interface de salida para codigos de barra según un determinado ID de comunicacion al POS.
     * Xpande. Created by Gabriel Vila on 3/27/18.
     * @param adOrgID
     * @param zComunicacionPosID
     * @return
     */
    private List<MZGeocomInterfaceOut> getLinesUPCByComPos(int adOrgID, int zComunicacionPosID){

        String whereClause = X_Z_GeocomInterfaceOut.COLUMNNAME_Z_ComunicacionPOS_ID + " =" + zComunicacionPosID +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_AD_Table_ID + " =" + I_Z_ProductoUPC.Table_ID +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_AD_OrgTrx_ID + " =" + adOrgID;

        List<MZGeocomInterfaceOut> lines = new Query(ctx, I_Z_GeocomInterfaceOut.Table_Name, whereClause, trxName).setOrderBy(" SeqNo, Created  ").list();

        return lines;

    }

    /***
     * Obtiene y retorna lineas de interface de salida para socios de negocio no ejecutadas al momento.
     * Xpande. Created by Gabriel Vila on 7/24/17.
     * @param adOrgID
     * @return
     */
    private List<MZGeocomInterfaceOut> getLinesBPartnerNotExecuted(int adOrgID){

        String whereClause = X_Z_GeocomInterfaceOut.COLUMNNAME_IsExecuted + " ='N' " +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_AD_Table_ID + " =" + I_C_BPartner.Table_ID +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_AD_OrgTrx_ID + " =" + adOrgID;

        List<MZGeocomInterfaceOut> lines = new Query(ctx, I_Z_GeocomInterfaceOut.Table_Name, whereClause, trxName).setOrderBy(" SeqNo, Created  ").list();

        return lines;

    }


    /***
     * Obtiene y retorna lineas de interface para socios de negocio según ID determinado de comunicacion al pos.
     * Xpande. Created by Gabriel Vila on 3/27/18.
     * @param adOrgID
     * @param zComunicacionPosID
     * @return
     */
    private List<MZGeocomInterfaceOut> getLinesBPartnerByComPos(int adOrgID, int zComunicacionPosID){

        String whereClause = X_Z_GeocomInterfaceOut.COLUMNNAME_Z_ComunicacionPOS_ID + " =" + zComunicacionPosID +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_AD_Table_ID + " =" + I_C_BPartner.Table_ID +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_AD_OrgTrx_ID + " =" + adOrgID;

        List<MZGeocomInterfaceOut> lines = new Query(ctx, I_Z_GeocomInterfaceOut.Table_Name, whereClause, trxName).setOrderBy(" SeqNo, Created  ").list();

        return lines;
    }

}

package org.xpande.geocom.model;

import org.compiere.model.*;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.comercial.model.MZProdSalesOffer;
import org.xpande.core.model.I_Z_ProductoUPC;
import org.xpande.core.model.MZProductoUPC;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/23/21.
 */
public class ValidatorGeocom implements ModelValidator {

    private int adClientID = 0;

    @Override
    public void initialize(ModelValidationEngine engine, MClient client) {

        // Guardo compañia
        if (client != null){
            this.adClientID = client.get_ID();
        }

        // DB Validations
        engine.addModelChange(I_M_Product.Table_Name, this);
        engine.addModelChange(I_Z_ProductoUPC.Table_Name, this);
        engine.addModelChange(I_M_ProductPrice.Table_Name, this);
        engine.addModelChange(I_C_BPartner.Table_Name, this);
    }

    @Override
    public int getAD_Client_ID() {
        return this.adClientID;
    }

    @Override
    public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
        return null;
    }

    @Override
    public String modelChange(PO po, int type) throws Exception {

        if (po.get_TableName().equalsIgnoreCase(I_M_Product.Table_Name)){
            return modelChange((MProduct) po, type);
        }
        else if (po.get_TableName().equalsIgnoreCase(I_Z_ProductoUPC.Table_Name)){
            return modelChange((MZProductoUPC) po, type);
        }
        else if (po.get_TableName().equalsIgnoreCase(I_M_ProductPrice.Table_Name)){
            return modelChange((MProductPrice) po, type);
        }
        else if (po.get_TableName().equalsIgnoreCase(I_C_BPartner.Table_Name)){
            return modelChange((MBPartner) po, type);
        }

        return null;
    }

    @Override
    public String docValidate(PO po, int timing) {
        return null;
    }


    /***
     * Validaciones para el modelo de Productos
     * Xpande. Created by Gabriel Vila on 6/30/17.
     * @param model
     * @param type
     * @return
     * @throws Exception
     */
    public String modelChange(MProduct model, int type) throws Exception {

        int adOrgID = 1000001;

        // Interface salida POS
        if ((type == ModelValidator.TYPE_AFTER_NEW) || (type == ModelValidator.TYPE_AFTER_CHANGE)){

            if (type == ModelValidator.TYPE_AFTER_NEW){

                // Si el producto no se vende o no esta activo al momento de crearse, no hago nada
                if ((!model.isSold()) || (!model.isActive())){
                    return null;
                }

                // Marca de Creacion de Producto
                MZGeocomInterfaceOut geocomInterfaceOut = new MZGeocomInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                geocomInterfaceOut.setCRUDType(X_Z_GeocomInterfaceOut.CRUDTYPE_CREATE);
                geocomInterfaceOut.setSeqNo(10);
                geocomInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                geocomInterfaceOut.setRecord_ID(model.get_ID());
                geocomInterfaceOut.setAD_Org_ID(1000001);
                geocomInterfaceOut.setAD_OrgTrx_ID(1000001);
                geocomInterfaceOut.saveEx();
            }
            else if (type == ModelValidator.TYPE_AFTER_CHANGE){

                // Pregunto por los campos cuyo cambio requiere informar a Geocom
                if ((model.is_ValueChanged("C_UOM_ID")) || (model.is_ValueChanged("M_Product_Tandem_ID"))
                        || (model.is_ValueChanged("Description")) || (model.is_ValueChanged("C_TaxCategory_ID"))
                        || (model.is_ValueChanged("Name")) || (model.is_ValueChanged("EsProductoBalanza"))
                        || (model.is_ValueChanged("Z_ProductoSeccion_ID")) || (model.is_ValueChanged("Z_ProductoRubro_ID"))
                        || (model.is_ValueChanged("Z_ProductoFamilia_ID")) || (model.is_ValueChanged("Z_ProductoSubfamilia_ID"))
                        || (model.is_ValueChanged("IsBonificable")) || (model.is_ValueChanged("IsSold"))
                        || (model.is_ValueChanged("IsActive"))){


                    if ((model.is_ValueChanged("IsActive")) || (model.is_ValueChanged(X_M_Product.COLUMNNAME_IsSold))){

                        // Si desactiva o marca producto como no vendible
                        if ((!model.isActive()) || (!model.isSold())){
                            // Marca Delete para Geocom
                            MZGeocomInterfaceOut geocomInterfaceOut = MZGeocomInterfaceOut.getRecord(model.getCtx(), I_M_Product.Table_ID, model.get_ID(), adOrgID, model.get_TrxName());
                            if ((geocomInterfaceOut != null) && (geocomInterfaceOut.get_ID() > 0)){
                                // Proceso segun marca que ya tenía este socio antes de su actualización.
                                // Si marca anterior es CREATE
                                if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_GeocomInterfaceOut.CRUDTYPE_CREATE)){
                                    // Elimino marca anterior de create, ya que finalmente este socio de negocio no va al POS
                                    geocomInterfaceOut.deleteEx(true);
                                    return null;
                                }
                                else if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_GeocomInterfaceOut.CRUDTYPE_DELETE)){
                                    // Si marca anterior es DELETEAR, es porque el socio se inactivo anteriormente.
                                    // No hago nada y respeto primer marca.
                                    return null;                                }
                            }
                            // Si no tengo marca de delete, la creo ahora.
                            if ((geocomInterfaceOut == null) || (geocomInterfaceOut.get_ID() <= 0)){
                                geocomInterfaceOut = new MZGeocomInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                                geocomInterfaceOut.setCRUDType(X_Z_GeocomInterfaceOut.CRUDTYPE_DELETE);
                                geocomInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                                geocomInterfaceOut.setSeqNo(10);
                                geocomInterfaceOut.setRecord_ID(model.get_ID());
                                geocomInterfaceOut.setAD_Org_ID(1000001);
                                geocomInterfaceOut.setAD_OrgTrx_ID(1000001);
                                geocomInterfaceOut.saveEx();
                            }
                        }
                        else{
                            // Si es producto esta activo y se vende
                            if (model.isActive() && model.isSold()){
                                // Doy de alta
                                MZGeocomInterfaceOut geocomInterfaceOut = MZGeocomInterfaceOut.getRecord(model.getCtx(), I_M_Product.Table_ID, model.get_ID(), adOrgID, model.get_TrxName());
                                if ((geocomInterfaceOut != null) && (geocomInterfaceOut.get_ID() > 0)){
                                    // Proceso segun marca que ya tenía este socio antes de su actualización.
                                    // Si marca anterior es CREATE
                                    if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_GeocomInterfaceOut.CRUDTYPE_CREATE)){
                                        // No hago nada
                                        return null;
                                    }
                                    else if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_GeocomInterfaceOut.CRUDTYPE_DELETE)){
                                        // Si marca anterior es DELETEAR, es porque el socio se inactivo anteriormente.
                                        // Elimino marca anterior de create, ya que finalmente este socio de negocio va al POS
                                        geocomInterfaceOut.deleteEx(true);
                                        geocomInterfaceOut = null;
                                    }
                                }
                                // Si no tengo marca, la creo ahora.
                                if ((geocomInterfaceOut == null) || (geocomInterfaceOut.get_ID() <= 0)){
                                    geocomInterfaceOut = new MZGeocomInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                                    geocomInterfaceOut.setCRUDType(X_Z_GeocomInterfaceOut.CRUDTYPE_CREATE);
                                    geocomInterfaceOut.setSeqNo(10);
                                    geocomInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                                    geocomInterfaceOut.setRecord_ID(model.get_ID());
                                    geocomInterfaceOut.setAD_Org_ID(1000001);
                                    geocomInterfaceOut.setAD_OrgTrx_ID(1000001);
                                    geocomInterfaceOut.saveEx();
                                }
                            }
                        }
                    }
                    // Marca Update
                    MZGeocomInterfaceOut geocomInterfaceOut = MZGeocomInterfaceOut.getRecord(model.getCtx(), I_M_Product.Table_ID, model.get_ID(), adOrgID, model.get_TrxName());
                    if ((geocomInterfaceOut != null) && (geocomInterfaceOut.get_ID() > 0)){
                        // Proceso segun marca que ya tenía este producto antes de su actualización.
                        // Si marca anterior es CREATE
                        if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_GeocomInterfaceOut.CRUDTYPE_CREATE)){
                            // No hago nada y respeto primer marca
                            return null;
                        }
                        else if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_GeocomInterfaceOut.CRUDTYPE_DELETE)){
                            // Si marca anterior es DELETEAR, es porque el producto se inactivo anteriormente.
                            // Si este producto sigue estando inactivo
                            if (!model.isActive()){
                                // No hago nada y respeto primer marca.
                                return null;
                            }
                        }
                    }

                    // Si no tengo marca de update, la creo ahora.
                    if ((geocomInterfaceOut == null) || (geocomInterfaceOut.get_ID() <= 0)){
                        // No existe aun marca de UPDATE sobre este producto, la creo ahora.
                        geocomInterfaceOut = new MZGeocomInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                        geocomInterfaceOut.setCRUDType(X_Z_GeocomInterfaceOut.CRUDTYPE_UPDATE);
                        geocomInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                        geocomInterfaceOut.setSeqNo(20);
                        geocomInterfaceOut.setRecord_ID(model.get_ID());
                        geocomInterfaceOut.setIsPriceChanged(false);
                        geocomInterfaceOut.setAD_Org_ID(1000001);
                        geocomInterfaceOut.setAD_OrgTrx_ID(1000001);
                        geocomInterfaceOut.saveEx();
                    }

                    // Marca de update para Tandem si cambio
                    if (model.is_ValueChanged("M_Product_Tandem_ID")){
                        geocomInterfaceOut.setIsTandemChanged(true);
                        // Guardo tandem anterior en caso de haber cambio
                        if (model.get_ValueOldAsInt("M_Product_Tandem_ID") > 0){
                            if (geocomInterfaceOut.getM_Product_Tandem_ID() <= 0){
                                geocomInterfaceOut.setM_Product_Tandem_ID(model.get_ValueOldAsInt("M_Product_Tandem_ID"));
                            }
                        }
                        geocomInterfaceOut.saveEx();
                    }
                }
            }
        }
        return null;
    }


    /***
     * Validaciones para el modelo de Códigos de Barras de Productos.
     * Xpande. Created by Gabriel Vila on 6/30/17.
     * @param model
     * @param type
     * @return
     * @throws Exception
     */
    public String modelChange(MZProductoUPC model, int type) throws Exception {

        MProduct product = (MProduct)model.getM_Product();
        int adOrgID = 1000001;

        // Si el producto no se vende o no esta activo, no hago nada
        if ((!product.isSold()) || (!product.isActive())){
            return null;
        }

        // Geocom. Interface salida POS
        // Para Geocom, solo se crean la marcas para luego considerarse en la generación del archivo plano.
        if (type == ModelValidator.TYPE_AFTER_NEW){

            // Marca Create
            MZGeocomInterfaceOut geocomInterfaceOut = new MZGeocomInterfaceOut(model.getCtx(), 0, model.get_TrxName());
            geocomInterfaceOut.setCRUDType(X_Z_GeocomInterfaceOut.CRUDTYPE_CREATE);
            geocomInterfaceOut.setAD_Table_ID(I_Z_ProductoUPC.Table_ID);
            geocomInterfaceOut.setRecord_ID(model.get_ID());
            geocomInterfaceOut.setSeqNo(15);
            geocomInterfaceOut.setAD_Org_ID(1000001);
            geocomInterfaceOut.setAD_OrgTrx_ID(1000001);
            geocomInterfaceOut.saveEx();

        }
        else if (type == ModelValidator.TYPE_AFTER_DELETE){

            // Marca Update si tengo UPC
            if (model.getUPC() != null){
                MZGeocomInterfaceOut geocomInterfaceOut = new MZGeocomInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                geocomInterfaceOut.setCRUDType(X_Z_GeocomInterfaceOut.CRUDTYPE_DELETE);
                geocomInterfaceOut.setAD_Table_ID(I_Z_ProductoUPC.Table_ID);
                geocomInterfaceOut.setRecord_ID(model.get_ID());
                geocomInterfaceOut.setDescription(model.getUPC().trim());
                geocomInterfaceOut.setSeqNo(13);
                geocomInterfaceOut.setAD_Org_ID(1000001);
                geocomInterfaceOut.setAD_OrgTrx_ID(1000001);
                geocomInterfaceOut.saveEx();
            }
        }
        return null;
    }

    /***
     * Validaciones para el modelo de Precios de Productos
     * Xpande. Created by Gabriel Vila on 6/30/17.
     * @param model
     * @param type
     * @return
     * @throws Exception
     */
    public String modelChange(MProductPrice model, int type) throws Exception {

        int adOrgID = 1000001;

        // Retail. Interface salida POS
        if ((type == ModelValidator.TYPE_AFTER_NEW) || (type == ModelValidator.TYPE_AFTER_CHANGE)){

            // Si no cambió el precio de lista no hago nada (puede que solo haya cambiado la fecha de vigencia)
            if (!model.is_ValueChanged(MProductPrice.COLUMNNAME_PriceList)){
                if ((Env.getContext(model.getCtx(), "UpdatePrice") == null) || (Env.getContext(model.getCtx(), "UpdatePrice").equalsIgnoreCase("N"))){
                    return null;
                }
            }

            // Si el precio de lista es CERO no hago nada
            if ((model.getPriceList() == null) || (model.getPriceList().compareTo(Env.ZERO) <= 0)){
                return null;
            }

            // Solo listas de ventas con organización distinto de *
            MPriceListVersion priceListVersion = new MPriceListVersion(model.getCtx(), model.getM_PriceList_Version_ID(), model.get_TrxName());
            MPriceList priceList = priceListVersion.getPriceList();
            if (!priceList.isSOPriceList()) return null;
            if (priceList.getAD_Org_ID() == 0) return null;

            MProduct product = (MProduct)model.getM_Product();
            // Si el producto no se vende o no esta activo, no hago nada
            if ((!product.isSold()) || (!product.isActive())){
                return null;
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
            int offerID = DB.getSQLValueEx(model.get_TrxName(), sql);
            if (offerID > 0) {
                MZProdSalesOffer prodSalesOffer = new MZProdSalesOffer(model.getCtx(), offerID, model.get_TrxName());
                salesOfferPrice = prodSalesOffer.getPrice();
            }

            // Debo verificar que la organizacion asociada a esta lista de precios, trabaje con el POS de Geocom, sino es asi no hago nada
            sql = " select count(a.*) from z_posvendororg a " +
                    " inner join z_posvendor b on a.z_posvendor_id = b.z_posvendor_id and b.value='GEOCOM' " +
                    " where ad_orgtrx_id =" + priceList.getAD_Org_ID();
            int contador = DB.getSQLValueEx(null, sql);
            if (contador <= 0){
                return null;
            }

            // Si existe, obtengo marca de interface de este producto
            MZGeocomInterfaceOut geocomInterfaceOut = MZGeocomInterfaceOut.getRecord(model.getCtx(), I_M_Product.Table_ID, product.get_ID(),
                    priceList.getAD_Org_ID(), model.get_TrxName());
            if ((geocomInterfaceOut != null) && (geocomInterfaceOut.get_ID() > 0)){
                // Proceso segun marca que ya tenía este producto antes de su actualización.
                // Si marca anterior es CREATE
                if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_GeocomInterfaceOut.CRUDTYPE_CREATE)){
                    // Si tengo precio de venta de oferta se lo seteo a esta marca
                    if (salesOfferPrice != null) {
                        geocomInterfaceOut.setPriceSO(salesOfferPrice);
                        geocomInterfaceOut.setWithOfferSO(true);
                        geocomInterfaceOut.saveEx();
                    }
                    // No hago nada y respeto primer marca
                    return null;
                }
                else if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_GeocomInterfaceOut.CRUDTYPE_DELETE)){
                    // Si marca anterior es DELETEAR, es porque el producto se inactivo anteriormente.
                    // Si este producto sigue estando inactivo
                    if (!model.isActive()){
                        // No hago nada y respeto primer marca.
                        return null;
                    }
                }
            }

            // Si no tengo marca de update, la creo ahora.
            if ((geocomInterfaceOut == null) || (geocomInterfaceOut.get_ID() <= 0)) {
                // No existe aun marca de UPDATE sobre este producto, la creo ahora.
                geocomInterfaceOut = new MZGeocomInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                geocomInterfaceOut.setCRUDType(X_Z_GeocomInterfaceOut.CRUDTYPE_UPDATE);
                geocomInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                geocomInterfaceOut.setRecord_ID(product.get_ID());
                geocomInterfaceOut.setAD_Org_ID(1000001);
                geocomInterfaceOut.setAD_OrgTrx_ID(1000001);
                geocomInterfaceOut.setSeqNo(30);
            }

            geocomInterfaceOut.setIsPriceChanged(true);
            geocomInterfaceOut.setM_PriceList_ID(priceList.get_ID());
            // Si tengo precio de venta de oferta se lo seteo a esta marca
            if (salesOfferPrice != null) {
                geocomInterfaceOut.setPriceSO(salesOfferPrice);
                geocomInterfaceOut.setWithOfferSO(true);
            }
            geocomInterfaceOut.saveEx();
        }
        return null;
    }

    /***
     * Validaciones para el modelo de Socios de Negocio
     * Xpande. Created by Gabriel Vila on 6/30/17.
     * @param model
     * @param type
     * @return
     * @throws Exception
     */
    public String modelChange(MBPartner model, int type) throws Exception {

        int adOrgID = 1000001;

        // Geocom. Interface salida POS
        if ((type == ModelValidator.TYPE_AFTER_NEW) || (type == ModelValidator.TYPE_AFTER_CHANGE)){

            if (type == ModelValidator.TYPE_AFTER_NEW){

                // Si el socio no es cliente o no esta activo al momento de crearse, no hago nada
                if ((!model.isCustomer()) || (!model.isActive())){
                    return null;
                }

                // Marca de Creacion de Socio
                MZGeocomInterfaceOut geocomInterfaceOut = new MZGeocomInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                geocomInterfaceOut.setCRUDType(X_Z_GeocomInterfaceOut.CRUDTYPE_CREATE);
                geocomInterfaceOut.setSeqNo(10);
                geocomInterfaceOut.setAD_Table_ID(I_C_BPartner.Table_ID);
                geocomInterfaceOut.setRecord_ID(model.get_ID());
                geocomInterfaceOut.setAD_Org_ID(1000001);
                geocomInterfaceOut.setAD_OrgTrx_ID(1000001);
                geocomInterfaceOut.saveEx();
            }
            else if (type == ModelValidator.TYPE_AFTER_CHANGE){

                // Pregunto por los campos cuyo cambio requiere informar a Geocom
                if ((model.is_ValueChanged(X_C_BPartner.COLUMNNAME_Name)) || (model.is_ValueChanged(X_C_BPartner.COLUMNNAME_Name2))
                        || (model.is_ValueChanged(X_C_BPartner.COLUMNNAME_TaxID)) || (model.is_ValueChanged("EMail"))
                        || (model.is_ValueChanged(X_C_BPartner.COLUMNNAME_IsActive)) || (model.is_ValueChanged(X_C_BPartner.COLUMNNAME_IsCustomer))){

                    if ((model.is_ValueChanged(X_C_BPartner.COLUMNNAME_IsActive)) || (model.is_ValueChanged(X_C_BPartner.COLUMNNAME_IsCustomer))){

                        // Si desactiva cliente, mando marca de delete
                        if ((!model.isActive()) || (!model.isCustomer())){
                            // Marca Delete para Geocom
                            MZGeocomInterfaceOut geocomInterfaceOut = MZGeocomInterfaceOut.getRecord(model.getCtx(), I_C_BPartner.Table_ID, model.get_ID(), adOrgID, model.get_TrxName());
                            if ((geocomInterfaceOut != null) && (geocomInterfaceOut.get_ID() > 0)){
                                // Proceso segun marca que ya tenía este socio antes de su actualización.
                                // Si marca anterior es CREATE
                                if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_GeocomInterfaceOut.CRUDTYPE_CREATE)){
                                    // Elimino marca anterior de create, ya que finalmente este socio de negocio no va al POS
                                    geocomInterfaceOut.deleteEx(true);
                                    return null;
                                }
                                else if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_GeocomInterfaceOut.CRUDTYPE_DELETE)){
                                    // Si marca anterior es DELETEAR, es porque el socio se inactivo anteriormente.
                                    // No hago nada y respeto primer marca.
                                    return null;
                                }
                            }
                            // Si no tengo marca de delete, la creo ahora.
                            if ((geocomInterfaceOut == null) || (geocomInterfaceOut.get_ID() <= 0)){
                                geocomInterfaceOut = new MZGeocomInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                                geocomInterfaceOut.setCRUDType(X_Z_GeocomInterfaceOut.CRUDTYPE_DELETE);
                                geocomInterfaceOut.setAD_Table_ID(I_C_BPartner.Table_ID);
                                geocomInterfaceOut.setSeqNo(30);
                                geocomInterfaceOut.setRecord_ID(model.get_ID());
                                geocomInterfaceOut.setAD_Org_ID(1000001);
                                geocomInterfaceOut.setAD_OrgTrx_ID(1000001);
                                geocomInterfaceOut.saveEx();
                            }
                        }
                        else{
                            // Si es cliente y esta activo
                            if (model.isActive() && model.isCustomer()){
                                // Doy de alta
                                MZGeocomInterfaceOut geocomInterfaceOut = MZGeocomInterfaceOut.getRecord(model.getCtx(), I_C_BPartner.Table_ID, model.get_ID(), adOrgID, model.get_TrxName());
                                if ((geocomInterfaceOut != null) && (geocomInterfaceOut.get_ID() > 0)){
                                    // Proceso segun marca que ya tenía este socio antes de su actualización.
                                    // Si marca anterior es CREATE
                                    if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_GeocomInterfaceOut.CRUDTYPE_CREATE)){
                                        // No hago nada
                                        return null;
                                    }
                                    else if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_GeocomInterfaceOut.CRUDTYPE_DELETE)){
                                        // Si marca anterior es DELETEAR, es porque el socio se inactivo anteriormente.
                                        // Elimino marca anterior de create, ya que finalmente este socio de negocio va al POS
                                        geocomInterfaceOut.deleteEx(true);
                                        geocomInterfaceOut = null;
                                    }
                                }
                                // Si no tengo marca, la creo ahora.
                                if ((geocomInterfaceOut == null) || (geocomInterfaceOut.get_ID() <= 0)){
                                    geocomInterfaceOut = new MZGeocomInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                                    geocomInterfaceOut.setCRUDType(X_Z_GeocomInterfaceOut.CRUDTYPE_CREATE);
                                    geocomInterfaceOut.setSeqNo(10);
                                    geocomInterfaceOut.setAD_Table_ID(I_C_BPartner.Table_ID);
                                    geocomInterfaceOut.setRecord_ID(model.get_ID());
                                    geocomInterfaceOut.setAD_Org_ID(1000001);
                                    geocomInterfaceOut.setAD_OrgTrx_ID(1000001);
                                    geocomInterfaceOut.saveEx();
                                }
                            }
                        }

                    }
                    else{

                        // Si el socio no es cliente o no esta activo al momento de crearse, no hago nada
                        if ((!model.isCustomer()) || (!model.isActive())){
                            return null;
                        }

                        // Marca Update para Geocom
                        MZGeocomInterfaceOut geocomInterfaceOut = MZGeocomInterfaceOut.getRecord(model.getCtx(), I_C_BPartner.Table_ID, model.get_ID(), adOrgID, model.get_TrxName());
                        if ((geocomInterfaceOut != null) && (geocomInterfaceOut.get_ID() > 0)){
                            // Proceso segun marca que ya tenía este producto antes de su actualización.
                            // Si marca anterior es CREATE
                            if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_GeocomInterfaceOut.CRUDTYPE_CREATE)){
                                // No hago nada y respeto primer marca
                                return null;
                            }
                            else if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_GeocomInterfaceOut.CRUDTYPE_DELETE)){
                                // Si marca anterior es DELETEAR, es porque el producto se inactivo anteriormente.
                                // Si este producto sigue estando inactivo
                                if (!model.isActive()){
                                    // No hago nada y respeto primer marca.
                                    return null;
                                }
                            }
                        }
                        // Si no tengo marca de update, la creo ahora.
                        if ((geocomInterfaceOut == null) || (geocomInterfaceOut.get_ID() <= 0)){
                            geocomInterfaceOut = new MZGeocomInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                            geocomInterfaceOut.setCRUDType(X_Z_GeocomInterfaceOut.CRUDTYPE_UPDATE);
                            geocomInterfaceOut.setAD_Table_ID(I_C_BPartner.Table_ID);
                            geocomInterfaceOut.setSeqNo(20);
                            geocomInterfaceOut.setRecord_ID(model.get_ID());
                            geocomInterfaceOut.setAD_Org_ID(1000001);
                            geocomInterfaceOut.setAD_OrgTrx_ID(1000001);
                            geocomInterfaceOut.saveEx();
                        }
                    }
                }
            }
        }
        return null;
    }
}

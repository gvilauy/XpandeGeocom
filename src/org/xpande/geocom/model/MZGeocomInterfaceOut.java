package org.xpande.geocom.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.X_C_TaxGroup;
import org.xpande.core.model.I_Z_ProductoUPC;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.core.utils.PriceListUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/23/21.
 */
public class MZGeocomInterfaceOut extends X_Z_GeocomInterfaceOut {

    public MZGeocomInterfaceOut(Properties ctx, int Z_GeocomInterfaceOut_ID, String trxName) {
        super(ctx, Z_GeocomInterfaceOut_ID, trxName);
    }

    public MZGeocomInterfaceOut(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    /***
     * Obtiene y retorna modelo según parametros recibidos
     * Xpande. Created by Gabriel Vila on 7/6/17.
     * @param ctx
     * @param adTableID
     * @param recordID
     * @param adOrgID
     * @param trxName
     * @return
     */
    public static MZGeocomInterfaceOut getRecord(Properties ctx, int adTableID, int recordID, int adOrgID, String trxName){

        String whereClause = X_Z_GeocomInterfaceOut.COLUMNNAME_AD_Table_ID + " =" + adTableID +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_Record_ID + " =" + recordID +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_AD_OrgTrx_ID + " =" + adOrgID +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_IsExecuted + " ='N'";

        MZGeocomInterfaceOut model = new Query(ctx, I_Z_GeocomInterfaceOut.Table_Name, whereClause, trxName).first();

        return model;
    }

    /***
     * Obtiene y retorna modelo según parametros recibidos
     * Xpande. Created by Gabriel Vila on 7/6/17.
     * @param ctx
     * @param adTableID
     * @param recordID
     * @param crudType
     * @param trxName
     * @return
     */
    public static MZGeocomInterfaceOut getRecordByCrud(Properties ctx, int adTableID, int recordID, String crudType, String trxName){

        String whereClause = X_Z_GeocomInterfaceOut.COLUMNNAME_AD_Table_ID + " =" + adTableID +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_Record_ID + " =" + recordID +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_IsExecuted + " ='N'" +
                " AND " + X_Z_GeocomInterfaceOut.COLUMNNAME_CRUDType + " ='" + crudType + "'";

        MZGeocomInterfaceOut model = new Query(ctx, MZGeocomInterfaceOut.Table_Name, whereClause, trxName).first();

        return model;
    }
}

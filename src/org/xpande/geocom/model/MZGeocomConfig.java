package org.xpande.geocom.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/22/22.
 */
public class MZGeocomConfig extends X_Z_GeocomConfig{

    public MZGeocomConfig(Properties ctx, int Z_GeocomConfig_ID, String trxName) {
        super(ctx, Z_GeocomConfig_ID, trxName);
    }

    public MZGeocomConfig(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    /***
     * Xpande. Created by Gabriel Vila on 6/1/17.
     * Obtiene modelo único de configuración del proceso de Interface contra Sisteco.
     * @param ctx
     * @param trxName
     * @return
     */
    public static MZGeocomConfig getDefault(Properties ctx, String trxName){

        MZGeocomConfig model = new Query(ctx, I_Z_GeocomConfig.Table_Name, "", trxName).first();

        return model;
    }

}

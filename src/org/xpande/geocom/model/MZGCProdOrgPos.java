package org.xpande.geocom.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 5/12/22.
 */
public class MZGCProdOrgPos extends X_Z_GC_ProdOrgPos {

    public MZGCProdOrgPos(Properties ctx, int Z_GC_ProdOrgPos_ID, String trxName) {
        super(ctx, Z_GC_ProdOrgPos_ID, trxName);
    }

    public MZGCProdOrgPos(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    public static MZGCProdOrgPos getByProdOrg(Properties ctx, int mProductID, int adOrgID, String trxName){

        String whereClause = X_Z_GC_ProdOrgPos.COLUMNNAME_AD_Org_ID  + " =" + adOrgID +
                " AND " + X_Z_GC_ProdOrgPos.COLUMNNAME_M_Product_ID + " =" + mProductID;

        MZGCProdOrgPos model = new Query(ctx, I_Z_GC_ProdOrgPos.Table_Name, whereClause, trxName).first();

        return model;
    }
}

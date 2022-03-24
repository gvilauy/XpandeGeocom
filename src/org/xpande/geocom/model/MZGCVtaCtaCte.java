package org.xpande.geocom.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/22/22.
 */
public class MZGCVtaCtaCte extends X_Z_GC_VtaCtaCte {

    public MZGCVtaCtaCte(Properties ctx, int Z_GC_VtaCtaCte_ID, String trxName) {
        super(ctx, Z_GC_VtaCtaCte_ID, trxName);
    }

    public MZGCVtaCtaCte(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}

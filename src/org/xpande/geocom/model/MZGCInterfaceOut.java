package org.xpande.geocom.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/23/21.
 */
public class MZGCInterfaceOut extends X_Z_GCInterfaceOut {

    public MZGCInterfaceOut(Properties ctx, int Z_GCInterfaceOut_ID, String trxName) {
        super(ctx, Z_GCInterfaceOut_ID, trxName);
    }

    public MZGCInterfaceOut(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}

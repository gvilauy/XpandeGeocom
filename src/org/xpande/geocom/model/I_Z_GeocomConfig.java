/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2006-2017 ADempiere Foundation, All Rights Reserved.         *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * or (at your option) any later version.										*
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * or via info@adempiere.net or http://www.adempiere.net/license.html         *
 *****************************************************************************/
package org.xpande.geocom.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for Z_GeocomConfig
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1
 */
public interface I_Z_GeocomConfig 
{

    /** TableName=Z_GeocomConfig */
    public static final String Table_Name = "Z_GeocomConfig";

    /** AD_Table_ID=1000398 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DefaultDocPosARC_ID */
    public static final String COLUMNNAME_DefaultDocPosARC_ID = "DefaultDocPosARC_ID";

	/** Set DefaultDocPosARC_ID.
	  * ID de documento para migración de notas de crédito de venta crédito desde POS
	  */
	public void setDefaultDocPosARC_ID (int DefaultDocPosARC_ID);

	/** Get DefaultDocPosARC_ID.
	  * ID de documento para migración de notas de crédito de venta crédito desde POS
	  */
	public int getDefaultDocPosARC_ID();

    /** Column name DefaultDocPosARI_ID */
    public static final String COLUMNNAME_DefaultDocPosARI_ID = "DefaultDocPosARI_ID";

	/** Set DefaultDocPosARI_ID.
	  * ID de documento para migración de facturas de venta crédito desde POS
	  */
	public void setDefaultDocPosARI_ID (int DefaultDocPosARI_ID);

	/** Get DefaultDocPosARI_ID.
	  * ID de documento para migración de facturas de venta crédito desde POS
	  */
	public int getDefaultDocPosARI_ID();

    /** Column name DocIntPosARC_ID */
    public static final String COLUMNNAME_DocIntPosARC_ID = "DocIntPosARC_ID";

	/** Set DocIntPosARC_ID.
	  * ID de Documento Interno NC para migraciones desde POS
	  */
	public void setDocIntPosARC_ID (int DocIntPosARC_ID);

	/** Get DocIntPosARC_ID.
	  * ID de Documento Interno NC para migraciones desde POS
	  */
	public int getDocIntPosARC_ID();

    /** Column name DocIntPosARI_ID */
    public static final String COLUMNNAME_DocIntPosARI_ID = "DocIntPosARI_ID";

	/** Set DocIntPosARI_ID.
	  * ID de Documento Interno para migraciones desde POS
	  */
	public void setDocIntPosARI_ID (int DocIntPosARI_ID);

	/** Get DocIntPosARI_ID.
	  * ID de Documento Interno para migraciones desde POS
	  */
	public int getDocIntPosARI_ID();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name ProdVtasCredPOS_ID */
    public static final String COLUMNNAME_ProdVtasCredPOS_ID = "ProdVtasCredPOS_ID";

	/** Set ProdVtasCredPOS_ID.
	  * Producto para Migración de Ventas Crédito desde POS
	  */
	public void setProdVtasCredPOS_ID (int ProdVtasCredPOS_ID);

	/** Get ProdVtasCredPOS_ID.
	  * Producto para Migración de Ventas Crédito desde POS
	  */
	public int getProdVtasCredPOS_ID();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name Z_GeocomConfig_ID */
    public static final String COLUMNNAME_Z_GeocomConfig_ID = "Z_GeocomConfig_ID";

	/** Set Z_GeocomConfig ID	  */
	public void setZ_GeocomConfig_ID (int Z_GeocomConfig_ID);

	/** Get Z_GeocomConfig ID	  */
	public int getZ_GeocomConfig_ID();
}

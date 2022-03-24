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
/** Generated Model - DO NOT CHANGE */
package org.xpande.geocom.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for Z_GeocomConfig
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1 - $Id$ */
public class X_Z_GeocomConfig extends PO implements I_Z_GeocomConfig, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220322L;

    /** Standard Constructor */
    public X_Z_GeocomConfig (Properties ctx, int Z_GeocomConfig_ID, String trxName)
    {
      super (ctx, Z_GeocomConfig_ID, trxName);
      /** if (Z_GeocomConfig_ID == 0)
        {
			setZ_GeocomConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_GeocomConfig (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_Z_GeocomConfig[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set DefaultDocPosARC_ID.
		@param DefaultDocPosARC_ID 
		ID de documento para migración de notas de crédito de venta crédito desde POS
	  */
	public void setDefaultDocPosARC_ID (int DefaultDocPosARC_ID)
	{
		if (DefaultDocPosARC_ID < 1) 
			set_Value (COLUMNNAME_DefaultDocPosARC_ID, null);
		else 
			set_Value (COLUMNNAME_DefaultDocPosARC_ID, Integer.valueOf(DefaultDocPosARC_ID));
	}

	/** Get DefaultDocPosARC_ID.
		@return ID de documento para migración de notas de crédito de venta crédito desde POS
	  */
	public int getDefaultDocPosARC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DefaultDocPosARC_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DefaultDocPosARI_ID.
		@param DefaultDocPosARI_ID 
		ID de documento para migración de facturas de venta crédito desde POS
	  */
	public void setDefaultDocPosARI_ID (int DefaultDocPosARI_ID)
	{
		if (DefaultDocPosARI_ID < 1) 
			set_Value (COLUMNNAME_DefaultDocPosARI_ID, null);
		else 
			set_Value (COLUMNNAME_DefaultDocPosARI_ID, Integer.valueOf(DefaultDocPosARI_ID));
	}

	/** Get DefaultDocPosARI_ID.
		@return ID de documento para migración de facturas de venta crédito desde POS
	  */
	public int getDefaultDocPosARI_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DefaultDocPosARI_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DocIntPosARC_ID.
		@param DocIntPosARC_ID 
		ID de Documento Interno NC para migraciones desde POS
	  */
	public void setDocIntPosARC_ID (int DocIntPosARC_ID)
	{
		if (DocIntPosARC_ID < 1) 
			set_Value (COLUMNNAME_DocIntPosARC_ID, null);
		else 
			set_Value (COLUMNNAME_DocIntPosARC_ID, Integer.valueOf(DocIntPosARC_ID));
	}

	/** Get DocIntPosARC_ID.
		@return ID de Documento Interno NC para migraciones desde POS
	  */
	public int getDocIntPosARC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DocIntPosARC_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DocIntPosARI_ID.
		@param DocIntPosARI_ID 
		ID de Documento Interno para migraciones desde POS
	  */
	public void setDocIntPosARI_ID (int DocIntPosARI_ID)
	{
		if (DocIntPosARI_ID < 1) 
			set_Value (COLUMNNAME_DocIntPosARI_ID, null);
		else 
			set_Value (COLUMNNAME_DocIntPosARI_ID, Integer.valueOf(DocIntPosARI_ID));
	}

	/** Get DocIntPosARI_ID.
		@return ID de Documento Interno para migraciones desde POS
	  */
	public int getDocIntPosARI_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DocIntPosARI_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ProdVtasCredPOS_ID.
		@param ProdVtasCredPOS_ID 
		Producto para Migración de Ventas Crédito desde POS
	  */
	public void setProdVtasCredPOS_ID (int ProdVtasCredPOS_ID)
	{
		if (ProdVtasCredPOS_ID < 1) 
			set_Value (COLUMNNAME_ProdVtasCredPOS_ID, null);
		else 
			set_Value (COLUMNNAME_ProdVtasCredPOS_ID, Integer.valueOf(ProdVtasCredPOS_ID));
	}

	/** Get ProdVtasCredPOS_ID.
		@return Producto para Migración de Ventas Crédito desde POS
	  */
	public int getProdVtasCredPOS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ProdVtasCredPOS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Z_GeocomConfig ID.
		@param Z_GeocomConfig_ID Z_GeocomConfig ID	  */
	public void setZ_GeocomConfig_ID (int Z_GeocomConfig_ID)
	{
		if (Z_GeocomConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_GeocomConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_GeocomConfig_ID, Integer.valueOf(Z_GeocomConfig_ID));
	}

	/** Get Z_GeocomConfig ID.
		@return Z_GeocomConfig ID	  */
	public int getZ_GeocomConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_GeocomConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
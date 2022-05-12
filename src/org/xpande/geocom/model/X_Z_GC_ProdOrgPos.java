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

/** Generated Model for Z_GC_ProdOrgPos
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1 - $Id$ */
public class X_Z_GC_ProdOrgPos extends PO implements I_Z_GC_ProdOrgPos, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220512L;

    /** Standard Constructor */
    public X_Z_GC_ProdOrgPos (Properties ctx, int Z_GC_ProdOrgPos_ID, String trxName)
    {
      super (ctx, Z_GC_ProdOrgPos_ID, trxName);
      /** if (Z_GC_ProdOrgPos_ID == 0)
        {
			setM_Product_ID (0);
			setZ_GC_ProdOrgPos_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_GC_ProdOrgPos (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_GC_ProdOrgPos[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_GC_ProdOrgPos ID.
		@param Z_GC_ProdOrgPos_ID Z_GC_ProdOrgPos ID	  */
	public void setZ_GC_ProdOrgPos_ID (int Z_GC_ProdOrgPos_ID)
	{
		if (Z_GC_ProdOrgPos_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_GC_ProdOrgPos_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_GC_ProdOrgPos_ID, Integer.valueOf(Z_GC_ProdOrgPos_ID));
	}

	/** Get Z_GC_ProdOrgPos ID.
		@return Z_GC_ProdOrgPos ID	  */
	public int getZ_GC_ProdOrgPos_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_GC_ProdOrgPos_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
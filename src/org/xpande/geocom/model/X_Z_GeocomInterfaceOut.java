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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for Z_GeocomInterfaceOut
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1 - $Id$ */
public class X_Z_GeocomInterfaceOut extends PO implements I_Z_GeocomInterfaceOut, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210923L;

    /** Standard Constructor */
    public X_Z_GeocomInterfaceOut (Properties ctx, int Z_GeocomInterfaceOut_ID, String trxName)
    {
      super (ctx, Z_GeocomInterfaceOut_ID, trxName);
      /** if (Z_GeocomInterfaceOut_ID == 0)
        {
			setCRUDType (null);
			setIsError (false);
// N
			setIsExecuted (false);
// N
			setIsPriceChanged (false);
// N
			setIsTandemChanged (false);
// N
			setRecord_ID (0);
			setSeqNo (0);
			setWithOfferSO (false);
// N
			setZ_GeocomInterfaceOut_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_GeocomInterfaceOut (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_GeocomInterfaceOut[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Trx Organization.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Organization.
		@return Performing or initiating organization
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Table getAD_Table() throws RuntimeException
    {
		return (I_AD_Table)MTable.get(getCtx(), I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** CRUDType AD_Reference_ID=1000007 */
	public static final int CRUDTYPE_AD_Reference_ID=1000007;
	/** CREATE = C */
	public static final String CRUDTYPE_CREATE = "C";
	/** UPDATE = U */
	public static final String CRUDTYPE_UPDATE = "U";
	/** DELETE = D */
	public static final String CRUDTYPE_DELETE = "D";
	/** READ = R */
	public static final String CRUDTYPE_READ = "R";
	/** Set CRUDType.
		@param CRUDType 
		Tipo de acción de alta, baja, modificacion o lectura de registro en Base de Datos
	  */
	public void setCRUDType (String CRUDType)
	{

		set_Value (COLUMNNAME_CRUDType, CRUDType);
	}

	/** Get CRUDType.
		@return Tipo de acción de alta, baja, modificacion o lectura de registro en Base de Datos
	  */
	public String getCRUDType () 
	{
		return (String)get_Value(COLUMNNAME_CRUDType);
	}

	/** Set DateExecuted.
		@param DateExecuted 
		Fecha ejecutado
	  */
	public void setDateExecuted (Timestamp DateExecuted)
	{
		set_Value (COLUMNNAME_DateExecuted, DateExecuted);
	}

	/** Get DateExecuted.
		@return Fecha ejecutado
	  */
	public Timestamp getDateExecuted () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateExecuted);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** Set Error Msg.
		@param ErrorMsg Error Msg	  */
	public void setErrorMsg (String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Error Msg.
		@return Error Msg	  */
	public String getErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set Error.
		@param IsError 
		An Error occurred in the execution
	  */
	public void setIsError (boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Error.
		@return An Error occurred in the execution
	  */
	public boolean isError () 
	{
		Object oo = get_Value(COLUMNNAME_IsError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsExecuted.
		@param IsExecuted IsExecuted	  */
	public void setIsExecuted (boolean IsExecuted)
	{
		set_Value (COLUMNNAME_IsExecuted, Boolean.valueOf(IsExecuted));
	}

	/** Get IsExecuted.
		@return IsExecuted	  */
	public boolean isExecuted () 
	{
		Object oo = get_Value(COLUMNNAME_IsExecuted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsPriceChanged.
		@param IsPriceChanged 
		El precio fue modificado
	  */
	public void setIsPriceChanged (boolean IsPriceChanged)
	{
		set_Value (COLUMNNAME_IsPriceChanged, Boolean.valueOf(IsPriceChanged));
	}

	/** Get IsPriceChanged.
		@return El precio fue modificado
	  */
	public boolean isPriceChanged () 
	{
		Object oo = get_Value(COLUMNNAME_IsPriceChanged);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsTandemChanged.
		@param IsTandemChanged 
		Si se modifico asociación de Tandem en un Producto para Reatil
	  */
	public void setIsTandemChanged (boolean IsTandemChanged)
	{
		set_Value (COLUMNNAME_IsTandemChanged, Boolean.valueOf(IsTandemChanged));
	}

	/** Get IsTandemChanged.
		@return Si se modifico asociación de Tandem en un Producto para Reatil
	  */
	public boolean isTandemChanged () 
	{
		Object oo = get_Value(COLUMNNAME_IsTandemChanged);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_M_PriceList getM_PriceList() throws RuntimeException
    {
		return (I_M_PriceList)MTable.get(getCtx(), I_M_PriceList.Table_Name)
			.getPO(getM_PriceList_ID(), get_TrxName());	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Product getM_Product_Tandem() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_Tandem_ID(), get_TrxName());	}

	/** Set M_Product_Tandem_ID.
		@param M_Product_Tandem_ID 
		Producto Tandem que se asocia a otro producto en Retail
	  */
	public void setM_Product_Tandem_ID (int M_Product_Tandem_ID)
	{
		if (M_Product_Tandem_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Tandem_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Tandem_ID, Integer.valueOf(M_Product_Tandem_ID));
	}

	/** Get M_Product_Tandem_ID.
		@return Producto Tandem que se asocia a otro producto en Retail
	  */
	public int getM_Product_Tandem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Tandem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PriceSO.
		@param PriceSO 
		PriceSO
	  */
	public void setPriceSO (BigDecimal PriceSO)
	{
		set_Value (COLUMNNAME_PriceSO, PriceSO);
	}

	/** Get PriceSO.
		@return PriceSO
	  */
	public BigDecimal getPriceSO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceSO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** Set WithOfferSO.
		@param WithOfferSO 
		Si tiene o no oferta en precio de venta en Retail
	  */
	public void setWithOfferSO (boolean WithOfferSO)
	{
		set_Value (COLUMNNAME_WithOfferSO, Boolean.valueOf(WithOfferSO));
	}

	/** Get WithOfferSO.
		@return Si tiene o no oferta en precio de venta en Retail
	  */
	public boolean isWithOfferSO () 
	{
		Object oo = get_Value(COLUMNNAME_WithOfferSO);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Z_ComunicacionPOS ID.
		@param Z_ComunicacionPOS_ID Z_ComunicacionPOS ID	  */
	public void setZ_ComunicacionPOS_ID (int Z_ComunicacionPOS_ID)
	{
		if (Z_ComunicacionPOS_ID < 1) 
			set_Value (COLUMNNAME_Z_ComunicacionPOS_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ComunicacionPOS_ID, Integer.valueOf(Z_ComunicacionPOS_ID));
	}

	/** Get Z_ComunicacionPOS ID.
		@return Z_ComunicacionPOS ID	  */
	public int getZ_ComunicacionPOS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ComunicacionPOS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_GeocomInterfaceOut ID.
		@param Z_GeocomInterfaceOut_ID Z_GeocomInterfaceOut ID	  */
	public void setZ_GeocomInterfaceOut_ID (int Z_GeocomInterfaceOut_ID)
	{
		if (Z_GeocomInterfaceOut_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_GeocomInterfaceOut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_GeocomInterfaceOut_ID, Integer.valueOf(Z_GeocomInterfaceOut_ID));
	}

	/** Get Z_GeocomInterfaceOut ID.
		@return Z_GeocomInterfaceOut ID	  */
	public int getZ_GeocomInterfaceOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_GeocomInterfaceOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
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

/** Generated Model for Z_GCInterfaceOut
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1 - $Id$ */
public class X_Z_GCInterfaceOut extends PO implements I_Z_GCInterfaceOut, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210923L;

    /** Standard Constructor */
    public X_Z_GCInterfaceOut (Properties ctx, int Z_GCInterfaceOut_ID, String trxName)
    {
      super (ctx, Z_GCInterfaceOut_ID, trxName);
      /** if (Z_GCInterfaceOut_ID == 0)
        {
			setCRUDType (null);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setIsError (false);
// N
			setTipoDatoInterface (null);
			setZ_GCInterfaceOut_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_GCInterfaceOut (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_GCInterfaceOut[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CodImpuestoPOS.
		@param CodImpuestoPOS 
		Codigo de impuesto en POS
	  */
	public void setCodImpuestoPOS (String CodImpuestoPOS)
	{
		set_Value (COLUMNNAME_CodImpuestoPOS, CodImpuestoPOS);
	}

	/** Get CodImpuestoPOS.
		@return Codigo de impuesto en POS
	  */
	public String getCodImpuestoPOS () 
	{
		return (String)get_Value(COLUMNNAME_CodImpuestoPOS);
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

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	/** Set ISO Currency Code.
		@param ISO_Code 
		Three letter ISO 4217 Code of the Currency
	  */
	public void setISO_Code (String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	/** Get ISO Currency Code.
		@return Three letter ISO 4217 Code of the Currency
	  */
	public String getISO_Code () 
	{
		return (String)get_Value(COLUMNNAME_ISO_Code);
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

	/** Set Price.
		@param Price 
		Price
	  */
	public void setPrice (BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	/** Get Price.
		@return Price
	  */
	public BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** TipoDatoInterface AD_Reference_ID=1000066 */
	public static final int TIPODATOINTERFACE_AD_Reference_ID=1000066;
	/** PRODUCTO = P */
	public static final String TIPODATOINTERFACE_PRODUCTO = "P";
	/** CODIGO DE BARRAS = B */
	public static final String TIPODATOINTERFACE_CODIGODEBARRAS = "B";
	/** CLIENTE = C */
	public static final String TIPODATOINTERFACE_CLIENTE = "C";
	/** Set TipoDatoInterface.
		@param TipoDatoInterface 
		TipoDatoInterface
	  */
	public void setTipoDatoInterface (String TipoDatoInterface)
	{

		set_Value (COLUMNNAME_TipoDatoInterface, TipoDatoInterface);
	}

	/** Get TipoDatoInterface.
		@return TipoDatoInterface
	  */
	public String getTipoDatoInterface () 
	{
		return (String)get_Value(COLUMNNAME_TipoDatoInterface);
	}

	/** Set UPC/EAN.
		@param UPC 
		Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public void setUPC (String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	/** Get UPC/EAN.
		@return Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public String getUPC () 
	{
		return (String)get_Value(COLUMNNAME_UPC);
	}

	/** Set Immutable Universally Unique Identifier.
		@param UUID 
		Immutable Universally Unique Identifier
	  */
	public void setUUID (String UUID)
	{
		set_Value (COLUMNNAME_UUID, UUID);
	}

	/** Get Immutable Universally Unique Identifier.
		@return Immutable Universally Unique Identifier
	  */
	public String getUUID () 
	{
		return (String)get_Value(COLUMNNAME_UUID);
	}

	/** Set Z_GCInterfaceOut ID.
		@param Z_GCInterfaceOut_ID Z_GCInterfaceOut ID	  */
	public void setZ_GCInterfaceOut_ID (int Z_GCInterfaceOut_ID)
	{
		if (Z_GCInterfaceOut_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_GCInterfaceOut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_GCInterfaceOut_ID, Integer.valueOf(Z_GCInterfaceOut_ID));
	}

	/** Get Z_GCInterfaceOut ID.
		@return Z_GCInterfaceOut ID	  */
	public int getZ_GCInterfaceOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_GCInterfaceOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
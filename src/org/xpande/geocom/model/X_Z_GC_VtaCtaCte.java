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

/** Generated Model for Z_GC_VtaCtaCte
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1 - $Id$ */
public class X_Z_GC_VtaCtaCte extends PO implements I_Z_GC_VtaCtaCte, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220323L;

    /** Standard Constructor */
    public X_Z_GC_VtaCtaCte (Properties ctx, int Z_GC_VtaCtaCte_ID, String trxName)
    {
      super (ctx, Z_GC_VtaCtaCte_ID, trxName);
      /** if (Z_GC_VtaCtaCte_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setfechaticket (new Timestamp( System.currentTimeMillis() ));
			setIsExecuted (false);
// N
			setIsVentaEmpresa (false);
// N
			setZ_GC_InterfaceVta_ID (0);
			setZ_GC_VtaCtaCte_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_GC_VtaCtaCte (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_GC_VtaCtaCte[")
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

	public I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (I_C_BPartner_Location)MTable.get(getCtx(), I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_BP_Group getC_BP_Group() throws RuntimeException
    {
		return (I_C_BP_Group)MTable.get(getCtx(), I_C_BP_Group.Table_Name)
			.getPO(getC_BP_Group_ID(), get_TrxName());	}

	/** Set Business Partner Group.
		@param C_BP_Group_ID 
		Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Business Partner Group.
		@return Business Partner Group
	  */
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_DocType getC_DocType() throws RuntimeException
    {
		return (I_C_DocType)MTable.get(getCtx(), I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (I_C_Invoice)MTable.get(getCtx(), I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set codcaja.
		@param codcaja codcaja	  */
	public void setcodcaja (String codcaja)
	{
		set_Value (COLUMNNAME_codcaja, codcaja);
	}

	/** Get codcaja.
		@return codcaja	  */
	public String getcodcaja () 
	{
		return (String)get_Value(COLUMNNAME_codcaja);
	}

	/** Set codcajero.
		@param codcajero codcajero	  */
	public void setcodcajero (String codcajero)
	{
		set_Value (COLUMNNAME_codcajero, codcajero);
	}

	/** Get codcajero.
		@return codcajero	  */
	public String getcodcajero () 
	{
		return (String)get_Value(COLUMNNAME_codcajero);
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

	/** Set docdginame.
		@param docdginame docdginame	  */
	public void setdocdginame (String docdginame)
	{
		set_Value (COLUMNNAME_docdginame, docdginame);
	}

	/** Get docdginame.
		@return docdginame	  */
	public String getdocdginame () 
	{
		return (String)get_Value(COLUMNNAME_docdginame);
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

	/** Set fechaticket.
		@param fechaticket fechaticket	  */
	public void setfechaticket (Timestamp fechaticket)
	{
		set_Value (COLUMNNAME_fechaticket, fechaticket);
	}

	/** Get fechaticket.
		@return fechaticket	  */
	public Timestamp getfechaticket () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fechaticket);
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

	/** Set Es Venta Empresa.
		@param IsVentaEmpresa 
		Es Venta Empresa
	  */
	public void setIsVentaEmpresa (boolean IsVentaEmpresa)
	{
		set_Value (COLUMNNAME_IsVentaEmpresa, Boolean.valueOf(IsVentaEmpresa));
	}

	/** Get Es Venta Empresa.
		@return Es Venta Empresa
	  */
	public boolean isVentaEmpresa () 
	{
		Object oo = get_Value(COLUMNNAME_IsVentaEmpresa);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set NroTicket.
		@param NroTicket 
		Número de Ticket
	  */
	public void setNroTicket (String NroTicket)
	{
		set_Value (COLUMNNAME_NroTicket, NroTicket);
	}

	/** Get NroTicket.
		@return Número de Ticket
	  */
	public String getNroTicket () 
	{
		return (String)get_Value(COLUMNNAME_NroTicket);
	}

	/** Set NumeroCFE.
		@param NumeroCFE 
		Número de CFE
	  */
	public void setNumeroCFE (String NumeroCFE)
	{
		set_Value (COLUMNNAME_NumeroCFE, NumeroCFE);
	}

	/** Get NumeroCFE.
		@return Número de CFE
	  */
	public String getNumeroCFE () 
	{
		return (String)get_Value(COLUMNNAME_NumeroCFE);
	}

	/** Set SerieCFE.
		@param SerieCFE 
		Serie para CFE
	  */
	public void setSerieCFE (String SerieCFE)
	{
		set_Value (COLUMNNAME_SerieCFE, SerieCFE);
	}

	/** Get SerieCFE.
		@return Serie para CFE
	  */
	public String getSerieCFE () 
	{
		return (String)get_Value(COLUMNNAME_SerieCFE);
	}

	/** Set TipoCFE.
		@param TipoCFE 
		Tipo CFE para CFE
	  */
	public void setTipoCFE (String TipoCFE)
	{
		set_Value (COLUMNNAME_TipoCFE, TipoCFE);
	}

	/** Get TipoCFE.
		@return Tipo CFE para CFE
	  */
	public String getTipoCFE () 
	{
		return (String)get_Value(COLUMNNAME_TipoCFE);
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Z_GC_InterfaceVta ID.
		@param Z_GC_InterfaceVta_ID Z_GC_InterfaceVta ID	  */
	public void setZ_GC_InterfaceVta_ID (int Z_GC_InterfaceVta_ID)
	{
		if (Z_GC_InterfaceVta_ID < 1) 
			set_Value (COLUMNNAME_Z_GC_InterfaceVta_ID, null);
		else 
			set_Value (COLUMNNAME_Z_GC_InterfaceVta_ID, Integer.valueOf(Z_GC_InterfaceVta_ID));
	}

	/** Get Z_GC_InterfaceVta ID.
		@return Z_GC_InterfaceVta ID	  */
	public int getZ_GC_InterfaceVta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_GC_InterfaceVta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_GC_VtaCtaCte ID.
		@param Z_GC_VtaCtaCte_ID Z_GC_VtaCtaCte ID	  */
	public void setZ_GC_VtaCtaCte_ID (int Z_GC_VtaCtaCte_ID)
	{
		if (Z_GC_VtaCtaCte_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_GC_VtaCtaCte_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_GC_VtaCtaCte_ID, Integer.valueOf(Z_GC_VtaCtaCte_ID));
	}

	/** Get Z_GC_VtaCtaCte ID.
		@return Z_GC_VtaCtaCte ID	  */
	public int getZ_GC_VtaCtaCte_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_GC_VtaCtaCte_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
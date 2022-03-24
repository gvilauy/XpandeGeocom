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

/** Generated Interface for Z_GC_VtaCtaCte
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1
 */
public interface I_Z_GC_VtaCtaCte 
{

    /** TableName=Z_GC_VtaCtaCte */
    public static final String Table_Name = "Z_GC_VtaCtaCte";

    /** AD_Table_ID=1000399 */
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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

    /** Column name C_BP_Group_ID */
    public static final String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/** Set Business Partner Group.
	  * Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID);

	/** Get Business Partner Group.
	  * Business Partner Group
	  */
	public int getC_BP_Group_ID();

	public I_C_BP_Group getC_BP_Group() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public I_C_Invoice getC_Invoice() throws RuntimeException;

    /** Column name codcaja */
    public static final String COLUMNNAME_codcaja = "codcaja";

	/** Set codcaja	  */
	public void setcodcaja (String codcaja);

	/** Get codcaja	  */
	public String getcodcaja();

    /** Column name codcajero */
    public static final String COLUMNNAME_codcajero = "codcajero";

	/** Set codcajero	  */
	public void setcodcajero (String codcajero);

	/** Get codcajero	  */
	public String getcodcajero();

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

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

    /** Column name docdginame */
    public static final String COLUMNNAME_docdginame = "docdginame";

	/** Set docdginame	  */
	public void setdocdginame (String docdginame);

	/** Get docdginame	  */
	public String getdocdginame();

    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/** Set Error Msg	  */
	public void setErrorMsg (String ErrorMsg);

	/** Get Error Msg	  */
	public String getErrorMsg();

    /** Column name fechaticket */
    public static final String COLUMNNAME_fechaticket = "fechaticket";

	/** Set fechaticket	  */
	public void setfechaticket (Timestamp fechaticket);

	/** Get fechaticket	  */
	public Timestamp getfechaticket();

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

    /** Column name IsExecuted */
    public static final String COLUMNNAME_IsExecuted = "IsExecuted";

	/** Set IsExecuted	  */
	public void setIsExecuted (boolean IsExecuted);

	/** Get IsExecuted	  */
	public boolean isExecuted();

    /** Column name IsVentaEmpresa */
    public static final String COLUMNNAME_IsVentaEmpresa = "IsVentaEmpresa";

	/** Set Es Venta Empresa.
	  * Es Venta Empresa
	  */
	public void setIsVentaEmpresa (boolean IsVentaEmpresa);

	/** Get Es Venta Empresa.
	  * Es Venta Empresa
	  */
	public boolean isVentaEmpresa();

    /** Column name NroTicket */
    public static final String COLUMNNAME_NroTicket = "NroTicket";

	/** Set NroTicket.
	  * Número de Ticket
	  */
	public void setNroTicket (String NroTicket);

	/** Get NroTicket.
	  * Número de Ticket
	  */
	public String getNroTicket();

    /** Column name NumeroCFE */
    public static final String COLUMNNAME_NumeroCFE = "NumeroCFE";

	/** Set NumeroCFE.
	  * Número de CFE
	  */
	public void setNumeroCFE (String NumeroCFE);

	/** Get NumeroCFE.
	  * Número de CFE
	  */
	public String getNumeroCFE();

    /** Column name SerieCFE */
    public static final String COLUMNNAME_SerieCFE = "SerieCFE";

	/** Set SerieCFE.
	  * Serie para CFE
	  */
	public void setSerieCFE (String SerieCFE);

	/** Get SerieCFE.
	  * Serie para CFE
	  */
	public String getSerieCFE();

    /** Column name TipoCFE */
    public static final String COLUMNNAME_TipoCFE = "TipoCFE";

	/** Set TipoCFE.
	  * Tipo CFE para CFE
	  */
	public void setTipoCFE (String TipoCFE);

	/** Get TipoCFE.
	  * Tipo CFE para CFE
	  */
	public String getTipoCFE();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt);

	/** Get Total Amount.
	  * Total Amount
	  */
	public BigDecimal getTotalAmt();

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

    /** Column name Z_GC_InterfaceVta_ID */
    public static final String COLUMNNAME_Z_GC_InterfaceVta_ID = "Z_GC_InterfaceVta_ID";

	/** Set Z_GC_InterfaceVta ID	  */
	public void setZ_GC_InterfaceVta_ID (int Z_GC_InterfaceVta_ID);

	/** Get Z_GC_InterfaceVta ID	  */
	public int getZ_GC_InterfaceVta_ID();

    /** Column name Z_GC_VtaCtaCte_ID */
    public static final String COLUMNNAME_Z_GC_VtaCtaCte_ID = "Z_GC_VtaCtaCte_ID";

	/** Set Z_GC_VtaCtaCte ID	  */
	public void setZ_GC_VtaCtaCte_ID (int Z_GC_VtaCtaCte_ID);

	/** Get Z_GC_VtaCtaCte ID	  */
	public int getZ_GC_VtaCtaCte_ID();
}

// $Id: SupplierMasterModify2.java 164 2008-10-10 07:42:43Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.suppmodify;
import jp.co.daifuku.bluedog.ui.control.Page;

/**
 * ツールが生成した不可変クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 164 $, $Date: 2008-10-10 16:42:43 +0900 (金, 10 10 2008) $
 * @author  $Author: okayama $
 */
public class SupplierMasterModify2 extends Page
{

	// Class variables -----------------------------------------------

	/**
	 * ControlID	lbl_SettingName
	 * TemplateKey	In_SettingName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");

	/**
	 * ControlID	message
	 * TemplateKey	OperationMsg
	 * ControlType	Message
	 */
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");

	/**
	 * ControlID	tab
	 * TemplateKey	W_SetModifyDelete2
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_SetModifyDelete2");

	/**
	 * ControlID	btn_Back
	 * TemplateKey	W_Back
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "W_Back");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_MenuNoFunc
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_MenuNoFunc");

	/**
	 * ControlID	lbl_SupplierCode
	 * TemplateKey	W_SupplierCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SupplierCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SupplierCode" , "W_SupplierCode");

	/**
	 * ControlID	lbl_JavaSetSupplierCode
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetSupplierCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetSupplierCode" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_SupplierName
	 * TemplateKey	W_SupplierName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SupplierName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SupplierName" , "W_SupplierName");

	/**
	 * ControlID	txt_SupplierName
	 * TemplateKey	W_SupplierName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SupplierName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SupplierName" , "W_SupplierName");

	/**
	 * ControlID	lbl_LastUpdateDate
	 * TemplateKey	W_LastUpdate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LastUpdateDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LastUpdateDate" , "W_LastUpdate");

	/**
	 * ControlID	txt_LastUpdateDate
	 * TemplateKey	W_R_Date
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_LastUpdateDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_LastUpdateDate" , "W_R_Date");

	/**
	 * ControlID	lbl_LastUseDate
	 * TemplateKey	W_LastUseDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LastUseDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LastUseDate" , "W_LastUseDate");

	/**
	 * ControlID	txt_LastUseDate
	 * TemplateKey	W_R_Date
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_LastUseDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_LastUseDate" , "W_R_Date");

	/**
	 * ControlID	btn_Modify
	 * TemplateKey	W_ModifySet
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "W_ModifySet");

	/**
	 * ControlID	btn_Delete
	 * TemplateKey	W_Delete
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "W_Delete");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

}
//end of class

// $Id: CustomerMasterModify2.java 3065 2009-02-06 07:23:01Z tanaka $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.custmodify;
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
 * @version $Revision: 3065 $, $Date: 2009-02-06 16:23:01 +0900 (金, 06 2 2009) $
 * @author  $Author: tanaka $
 */
public class CustomerMasterModify2 extends Page
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
	 * ControlID	lbl_CustomerCode
	 * TemplateKey	W_CustomerCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerCode" , "W_CustomerCode");

	/**
	 * ControlID	lbl_JavaSetCustomerCode
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetCustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetCustomerCode" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_CustomerName
	 * TemplateKey	W_CustomerName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerName" , "W_CustomerName");

	/**
	 * ControlID	txt_CustomerName
	 * TemplateKey	W_CustomerName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerName" , "W_CustomerName");

	/**
	 * ControlID	lbl_Route
	 * TemplateKey	W_Route
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Route = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Route" , "W_Route");

	/**
	 * ControlID	txt_Route
	 * TemplateKey	W_Route
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Route = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Route" , "W_Route");

	/**
	 * ControlID	lbl_ZipCode
	 * TemplateKey	W_ZipCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ZipCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ZipCode" , "W_ZipCode");

	/**
	 * ControlID	txt_ZipCode
	 * TemplateKey	W_ZipCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ZipCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ZipCode" , "W_ZipCode");

	/**
	 * ControlID	lbl_14
	 * TemplateKey	W_AdministrativeDivisions
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_14 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_14" , "W_AdministrativeDivisions");

	/**
	 * ControlID	txt_AdministrativeDivisions
	 * TemplateKey	W_AdministrativeDivisions
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_AdministrativeDivisions = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_AdministrativeDivisions" , "W_AdministrativeDivisions");

	/**
	 * ControlID	lbl_Address
	 * TemplateKey	W_Address
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Address = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Address" , "W_Address");

	/**
	 * ControlID	txt_Address
	 * TemplateKey	W_ContactAddress
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Address = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Address" , "W_ContactAddress");

	/**
	 * ControlID	lbl_BuildingName
	 * TemplateKey	W_BuildingName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BuildingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BuildingName" , "W_BuildingName");

	/**
	 * ControlID	txt_BuildingName
	 * TemplateKey	W_BuildingName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BuildingName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BuildingName" , "W_BuildingName");

	/**
	 * ControlID	lbl_Tel
	 * TemplateKey	W_Tel
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Tel = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Tel" , "W_Tel");

	/**
	 * ControlID	txt_TelephoneNumber
	 * TemplateKey	W_TelephoneNumber
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TelephoneNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TelephoneNumber" , "W_TelephoneNumber");

	/**
	 * ControlID	lbl_Contact1
	 * TemplateKey	W_Contact1
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Contact1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Contact1" , "W_Contact1");

	/**
	 * ControlID	txt_Contact1
	 * TemplateKey	W_Address1
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Contact1 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Contact1" , "W_Address1");

	/**
	 * ControlID	lbl_Contact2
	 * TemplateKey	W_Contact2
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Contact2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Contact2" , "W_Contact2");

	/**
	 * ControlID	txt_Contact2
	 * TemplateKey	W_Address2
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Contact2 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Contact2" , "W_Address2");

	/**
	 * ControlID	lbl_SortPlace
	 * TemplateKey	W_SortPlace
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SortPlace = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SortPlace" , "W_SortPlace");

	/**
	 * ControlID	txt_SortPlace
	 * TemplateKey	W_SortPlace
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SortPlace = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SortPlace" , "W_SortPlace");

	/**
	 * ControlID	lbl_LastUpdate
	 * TemplateKey	W_LastUpdate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LastUpdate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LastUpdate" , "W_LastUpdate");

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
	 * ControlID	txt_LastUsedDate
	 * TemplateKey	W_R_Date
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_LastUsedDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_LastUsedDate" , "W_R_Date");

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

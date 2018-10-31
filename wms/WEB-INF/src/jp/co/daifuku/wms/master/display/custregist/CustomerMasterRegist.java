// $Id: CustomerMasterRegist.java 3066 2009-02-06 07:23:24Z tanaka $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.custregist;
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
 * @version $Revision: 3066 $, $Date: 2009-02-06 16:23:24 +0900 (金, 06 2 2009) $
 * @author  $Author: tanaka $
 */
public class CustomerMasterRegist extends Page
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
	 * ControlID	submit
	 * TemplateKey	W_Regist
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab submit = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("submit" , "W_Regist");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	l_CustomerCode
	 * TemplateKey	W_CustomerCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label l_CustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("l_CustomerCode" , "W_CustomerCode");

	/**
	 * ControlID	lbl_Require
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Require = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Require" , "W_Require");

	/**
	 * ControlID	txt_CustomerCode
	 * TemplateKey	W_CustomerCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerCode" , "W_CustomerCode");

	/**
	 * ControlID	btn_Search
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Search" , "W_P_Search");

	/**
	 * ControlID	l_CustomerName
	 * TemplateKey	W_CustomerName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label l_CustomerName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("l_CustomerName" , "W_CustomerName");

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
	 * ControlID	lbl_AdministrativeDivisions
	 * TemplateKey	W_AdministrativeDivisions
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AdministrativeDivisions = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AdministrativeDivisions" , "W_AdministrativeDivisions");

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
	 * ControlID	l_SortPlace
	 * TemplateKey	W_SortPlace
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label l_SortPlace = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("l_SortPlace" , "W_SortPlace");

	/**
	 * ControlID	txt_SortPlace
	 * TemplateKey	W_SortPlace
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SortPlace = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SortPlace" , "W_SortPlace");

	/**
	 * ControlID	btn_Submit
	 * TemplateKey	W_Set
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "W_Set");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

}
//end of class

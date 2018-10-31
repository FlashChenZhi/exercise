// $Id: AccessNgShelf.java 4122 2009-04-10 10:58:38Z ota $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.accessngshelf;
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
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 */
public class AccessNgShelf extends Page
{

	// Class variables -----------------------------------------------

	/**
	 * ControlID	lbl_SettingName
	 * TemplateKey	In_SettingName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");

	/**
	 * ControlID	btn_Help
	 * TemplateKey	Help
	 * ControlType	LinkButton
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");

	/**
	 * ControlID	message
	 * TemplateKey	OperationMsg
	 * ControlType	Message
	 */
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");

	/**
	 * ControlID	tab_Create
	 * TemplateKey	AST_Create
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Create = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Create" , "AST_Create");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_WareHouseNumber
	 * TemplateKey	AST_WareHouseNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouseNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouseNumber" , "AST_WareHouseNumber");

	/**
	 * ControlID	pul_StoreAs
	 * TemplateKey	AST_StoreAs
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_StoreAs = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_StoreAs" , "AST_StoreAs");

	/**
	 * ControlID	lbl_Location
	 * TemplateKey	AST_LocNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Location = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Location" , "AST_LocNumber");

	/**
	 * ControlID	txt_Bank
	 * TemplateKey	AST_Bank
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Bank = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Bank" , "AST_Bank");

	/**
	 * ControlID	lbl_Hyphen1
	 * TemplateKey	AST_Hyphen
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen1" , "AST_Hyphen");

	/**
	 * ControlID	txt_Bay
	 * TemplateKey	AST_Bay
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Bay = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Bay" , "AST_Bay");

	/**
	 * ControlID	lbl_Hyphen2
	 * TemplateKey	AST_Hyphen
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen2" , "AST_Hyphen");

	/**
	 * ControlID	txt_Level
	 * TemplateKey	AST_Level
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Level = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Level" , "AST_Level");

	/**
	 * ControlID	lbl_Width
	 * TemplateKey	AST_Width
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Width = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Width" , "AST_Width");

	/**
	 * ControlID	pul_Width
	 * TemplateKey	AST_Width
	 * ControlType	LinkedPullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_Width = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_Width" , "AST_Width");

	/**
	 * ControlID	lbl_Range
	 * TemplateKey	AST_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range" , "AST_Range");

	/**
	 * ControlID	lbl_Address
	 * TemplateKey	AST_Address
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Address = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Address" , "AST_Address");

	/**
	 * ControlID	txt_FAddress
	 * TemplateKey	AST_FAddress
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FAddress = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FAddress" , "AST_FAddress");

	/**
	 * ControlID	lbl_Hyphen3
	 * TemplateKey	AST_Hyphen2
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen3 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen3" , "AST_Hyphen2");

	/**
	 * ControlID	txt_TAddress
	 * TemplateKey	AST_TAddress
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TAddress = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TAddress" , "AST_TAddress");

	/**
	 * ControlID	btn_Add
	 * TemplateKey	AST_Add
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Add = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Add" , "AST_Add");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	AST_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "AST_Clear");

	/**
	 * ControlID	btn_Commit
	 * TemplateKey	AST_Commit
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "AST_Commit");

	/**
	 * ControlID	btn_Cancel
	 * TemplateKey	AST_Cancel
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "AST_Cancel");

	/**
	 * ControlID	lst_AccessNgShelf
	 * TemplateKey	AST_S_AccessNgShelf
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_AccessNgShelf = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_AccessNgShelf" , "AST_S_AccessNgShelf");

}
//end of class

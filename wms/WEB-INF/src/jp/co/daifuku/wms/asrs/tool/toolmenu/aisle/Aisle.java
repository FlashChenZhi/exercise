// $Id: Aisle.java 5298 2009-10-28 05:34:24Z ota $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.aisle;
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
 * @version $Revision: 5298 $, $Date: 2009-10-28 14:34:24 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 */
public class Aisle extends Page
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
	 * ControlID	tab
	 * TemplateKey	AST_Create
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "AST_Create");

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
	 * ControlID	lbl_StationNumber
	 * TemplateKey	AST_StationNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StationNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StationNumber" , "AST_StationNumber");

	/**
	 * ControlID	txt_StNumber
	 * TemplateKey	AST_StNumber
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StNumber" , "AST_StNumber");

	/**
	 * ControlID	lbl_AisleNumber
	 * TemplateKey	AST_AisleNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AisleNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AisleNumber" , "AST_AisleNumber");

	/**
	 * ControlID	txt_AisleNumber
	 * TemplateKey	AST_AisleNumber
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_AisleNumber = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_AisleNumber" , "AST_AisleNumber");

	/**
	 * ControlID	lbl_AgcNumber
	 * TemplateKey	AST_AgcNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AgcNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AgcNumber" , "AST_AgcNumber");

	/**
	 * ControlID	pul_AGCNo
	 * TemplateKey	AST_AGCNo
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_AGCNo = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_AGCNo" , "AST_AGCNo");

	/**
	 * ControlID	lbl_Range
	 * TemplateKey	AST_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range" , "AST_Range");

	/**
	 * ControlID	lbl_Bank
	 * TemplateKey	AST_Bank
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Bank = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Bank" , "AST_Bank");

	/**
	 * ControlID	txt_FBank
	 * TemplateKey	AST_FBank
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FBank = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FBank" , "AST_FBank");

	/**
	 * ControlID	lbl_Hyphen1
	 * TemplateKey	AST_Hyphen2
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen1" , "AST_Hyphen2");

	/**
	 * ControlID	txt_TBank
	 * TemplateKey	AST_TBank
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TBank = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TBank" , "AST_TBank");

	/**
	 * ControlID	lbl_AislePosition
	 * TemplateKey	AST_AislePosition
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AislePosition = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AislePosition" , "AST_AislePosition");

	/**
	 * ControlID	txt_FAislePosition
	 * TemplateKey	AST_FAislePosition
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FAislePosition = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FAislePosition" , "AST_FAislePosition");

	/**
	 * ControlID	lbl_And
	 * TemplateKey	AST_And
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_And = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_And" , "AST_And");

	/**
	 * ControlID	txt_TAislePosition
	 * TemplateKey	AST_TAislePosition
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TAislePosition = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TAislePosition" , "AST_TAislePosition");

	/**
	 * ControlID	lbl_Space
	 * TemplateKey	AST_Space
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Space = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Space" , "AST_Space");

	/**
	 * ControlID	lbl_Bay
	 * TemplateKey	AST_Bay
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Bay = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Bay" , "AST_Bay");

	/**
	 * ControlID	txt_FBay
	 * TemplateKey	AST_FBay
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FBay = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FBay" , "AST_FBay");

	/**
	 * ControlID	lbl_Hyphen2
	 * TemplateKey	AST_Hyphen2
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen2" , "AST_Hyphen2");

	/**
	 * ControlID	txt_TBay
	 * TemplateKey	AST_TBay
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TBay = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TBay" , "AST_TBay");

	/**
	 * ControlID	lbl_AislePositionMsg
	 * TemplateKey	AST_AislePositionMsg
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AislePositionMsg = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AislePositionMsg" , "AST_AislePositionMsg");

	/**
	 * ControlID	lbl_Level
	 * TemplateKey	AST_Level
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Level = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Level" , "AST_Level");

	/**
	 * ControlID	txt_FLevel
	 * TemplateKey	AST_FLevel
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FLevel = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FLevel" , "AST_FLevel");

	/**
	 * ControlID	lbl_Hyphen3
	 * TemplateKey	AST_Hyphen2
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen3 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen3" , "AST_Hyphen2");

	/**
	 * ControlID	txt_TLevel
	 * TemplateKey	AST_TLevel
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TLevel = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TLevel" , "AST_TLevel");

	/**
	 * ControlID	lbl_MaxCarry
	 * TemplateKey	AST_MaxCarry
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxCarry = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxCarry" , "AST_MaxCarry");

	/**
	 * ControlID	txt_MaxCarry
	 * TemplateKey	AST_MaxCarry
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_MaxCarry = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_MaxCarry" , "AST_MaxCarry");

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
	 * ControlID	lst_Aisle
	 * TemplateKey	AST_S_Aisle
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_Aisle = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_Aisle" , "AST_S_Aisle");

}
//end of class

// $Id: SoftZoneMasterMnt.java 6348 2009-12-03 10:40:42Z fukuwa $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.softzonemnt;
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
 * @version $Revision: 6348 $, $Date: 2009-12-03 19:40:42 +0900 (木, 03 12 2009) $
 * @author  $Author: fukuwa $
 */
public class SoftZoneMasterMnt extends Page
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
	 * TemplateKey	W_Set
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Set");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_Area
	 * TemplateKey	W_Area
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Area = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Area" , "W_Area");

	/**
	 * ControlID	pul_Area
	 * TemplateKey	W_Area
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Area = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Area" , "W_Area");

	/**
	 * ControlID	lbl_SoftZone
	 * TemplateKey	W_SoftZone
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SoftZone = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SoftZone" , "W_SoftZone");

	/**
	 * ControlID	pul_SoftZone
	 * TemplateKey	W_SoftZone
	 * ControlType	LinkedPullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_SoftZone = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_SoftZone" , "W_SoftZone");

	/**
	 * ControlID	lbl_Range
	 * TemplateKey	W_Range2
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range" , "W_Range2");

	/**
	 * ControlID	lbl_Bank
	 * TemplateKey	W_Bank
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Bank = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Bank" , "W_Bank");

	/**
	 * ControlID	lbl_Ast_Bank
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Ast_Bank = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Ast_Bank" , "W_Require");

	/**
	 * ControlID	txt_BankFrom
	 * TemplateKey	W_Bank
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BankFrom = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BankFrom" , "W_Bank");

	/**
	 * ControlID	lbl_Range2
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range2" , "W_Range");

	/**
	 * ControlID	txt_BankTo
	 * TemplateKey	W_Bank
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BankTo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BankTo" , "W_Bank");

	/**
	 * ControlID	lbl_Bay
	 * TemplateKey	W_Bay
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Bay = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Bay" , "W_Bay");

	/**
	 * ControlID	lbl_Ast_Bay
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Ast_Bay = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Ast_Bay" , "W_Require");

	/**
	 * ControlID	txt_BayFrom
	 * TemplateKey	W_Bay
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BayFrom = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BayFrom" , "W_Bay");

	/**
	 * ControlID	lbl_Range3
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range3 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range3" , "W_Range");

	/**
	 * ControlID	txt_BayTo
	 * TemplateKey	W_Bay
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BayTo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BayTo" , "W_Bay");

	/**
	 * ControlID	lbl_Level
	 * TemplateKey	W_Level
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Level = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Level" , "W_Level");

	/**
	 * ControlID	lbl_Ast_Level
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Ast_Level = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Ast_Level" , "W_Require");

	/**
	 * ControlID	txt_LevelFrom
	 * TemplateKey	W_Level
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_LevelFrom = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_LevelFrom" , "W_Level");

	/**
	 * ControlID	lbl_Range4
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range4 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range4" , "W_Range");

	/**
	 * ControlID	txt_LevelTo
	 * TemplateKey	W_Level
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_LevelTo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_LevelTo" , "W_Level");

	/**
	 * ControlID	btn_Input
	 * TemplateKey	W_Input
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Input = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Input" , "W_Input");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

	/**
	 * ControlID	btn_Set
	 * TemplateKey	W_Set2
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "W_Set2");

	/**
	 * ControlID	btn_AllClear
	 * TemplateKey	W_AllClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllClear" , "W_AllClear");

	/**
	 * ControlID	lst_ZoneMaintenanceRange
	 * TemplateKey	W_ZoneMaintenanceRange
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ZoneMaintenanceRange = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ZoneMaintenanceRange" , "W_ZoneMaintenanceRange");

}
//end of class

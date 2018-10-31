// $Id: InventoryInput.java 7106 2010-02-18 01:51:41Z fukuwa $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.inventorychk.display.input;
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
 * @version $Revision: 7106 $, $Date: 2010-02-18 10:51:41 +0900 (木, 18 2 2010) $
 * @author  $Author: fukuwa $
 */
public class InventoryInput extends Page
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
	 * TemplateKey	W_InventoryInput
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_InventoryInput");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_15
	 * TemplateKey	W_ConditionDesignateWay
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_15 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_15" , "W_ConditionDesignateWay");

	/**
	 * ControlID	rdo_JobNo
	 * TemplateKey	W_JobNo
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_JobNo = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_JobNo" , "W_JobNo");

	/**
	 * ControlID	rdo_LocationRange
	 * TemplateKey	W_LocationRange
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_LocationRange = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_LocationRange" , "W_LocationRange");

	/**
	 * ControlID	lbl_19
	 * TemplateKey	W_ListWorkNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_19 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_19" , "W_ListWorkNo");

	/**
	 * ControlID	lbl_20
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_20 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_20" , "W_Require");

	/**
	 * ControlID	txt_ListWorkNo
	 * TemplateKey	W_ListWorkNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ListWorkNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ListWorkNo" , "W_ListWorkNo");

	/**
	 * ControlID	btn_P_Search_JobNo
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search_JobNo = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search_JobNo" , "W_P_Search");

	/**
	 * ControlID	lbl_13
	 * TemplateKey	W_Area
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_13 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_13" , "W_Area");

	/**
	 * ControlID	pul_Area
	 * TemplateKey	W_Area_Event
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Area = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Area" , "W_Area_Event");

	/**
	 * ControlID	lbl_12
	 * TemplateKey	W_Location
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_12 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_12" , "W_Location");

	/**
	 * ControlID	txt_LocationFrom
	 * TemplateKey	W_Location
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_LocationFrom = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_LocationFrom" , "W_Location");

	/**
	 * ControlID	lbl_26
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_26 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_26" , "W_Range");

	/**
	 * ControlID	txt_LocationTo
	 * TemplateKey	W_Location
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_LocationTo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_LocationTo" , "W_Location");

	/**
	 * ControlID	lbl_InputStyle
	 * TemplateKey	W_InputStyle
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InputStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InputStyle" , "W_InputStyle");

	/**
	 * ControlID	lbl_LocationStyle
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationStyle" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_11
	 * TemplateKey	W_ItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_11 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_11" , "W_ItemCode");

	/**
	 * ControlID	txt_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	chk_InventoryOnlyDisp
	 * TemplateKey	W_InventoryOnlyDisp
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_InventoryOnlyDisp = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_InventoryOnlyDisp" , "W_InventoryOnlyDisp");

	/**
	 * ControlID	chk_InventoryQtyInput
	 * TemplateKey	W_InventoryQtyInput
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_InventoryQtyInput = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_InventoryQtyInput" , "W_InventoryQtyInput");

	/**
	 * ControlID	chk_InventoryStockQtyReport
	 * TemplateKey	W_InventoryStockQtyReport
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_InventoryStockQtyReport = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_InventoryStockQtyReport" , "W_InventoryStockQtyReport");

	/**
	 * ControlID	btn_Display
	 * TemplateKey	W_Display
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Display" , "W_Display");

	/**
	 * ControlID	btn_InventoryListIssue
	 * TemplateKey	W_InventoryListIssue
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_InventoryListIssue = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_InventoryListIssue" , "W_InventoryListIssue");

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
	 * ControlID	btn_AllCheck
	 * TemplateKey	W_AllCheck
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllCheck = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllCheck" , "W_AllCheck");

	/**
	 * ControlID	btn_AllCheckClear
	 * TemplateKey	W_AllCheckClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllCheckClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllCheckClear" , "W_AllCheckClear");

	/**
	 * ControlID	btn_P_AddNewData
	 * TemplateKey	W_P_AddNewData
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_AddNewData = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_AddNewData" , "W_P_AddNewData");

	/**
	 * ControlID	lst_InventoryResultInput
	 * TemplateKey	W_InventoryResultInput
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_InventoryResultInput = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_InventoryResultInput" , "W_InventoryResultInput");

}
//end of class

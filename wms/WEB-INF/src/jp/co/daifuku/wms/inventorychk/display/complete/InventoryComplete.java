// $Id: InventoryComplete.java 7109 2010-02-18 02:05:56Z fukuwa $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.inventorychk.display.complete;
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
 * @version $Revision: 7109 $, $Date: 2010-02-18 11:05:56 +0900 (木, 18 2 2010) $
 * @author  $Author: fukuwa $
 */
public class InventoryComplete extends Page
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
	 * ControlID	lbl_ConditionDesignateWay
	 * TemplateKey	W_ConditionDesignateWay
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConditionDesignateWay = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConditionDesignateWay" , "W_ConditionDesignateWay");

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
	 * ControlID	lbl_ListWorkNo
	 * TemplateKey	W_ListWorkNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListWorkNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListWorkNo" , "W_ListWorkNo");

	/**
	 * ControlID	lbl_Require
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Require = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Require" , "W_Require");

	/**
	 * ControlID	txt_ListWorkNo
	 * TemplateKey	W_ListWorkNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ListWorkNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ListWorkNo" , "W_ListWorkNo");

	/**
	 * ControlID	btn_PSearchListWorkNo
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchListWorkNo = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchListWorkNo" , "W_P_Search");

	/**
	 * ControlID	lbl_Area
	 * TemplateKey	W_Area
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Area = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Area" , "W_Area");

	/**
	 * ControlID	pul_Area
	 * TemplateKey	W_Area_Event
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Area = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Area" , "W_Area_Event");

	/**
	 * ControlID	lbl_Location
	 * TemplateKey	W_Location
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Location = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Location" , "W_Location");

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
	 * ControlID	lbl_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	txt_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	btn_Display
	 * TemplateKey	W_Display
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Display" , "W_Display");

	/**
	 * ControlID	btn_DiffListIssue
	 * TemplateKey	W_DiffListIssue
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_DiffListIssue = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_DiffListIssue" , "W_DiffListIssue");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

	/**
	 * ControlID	btn_Decision
	 * TemplateKey	W_Decision
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Decision = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Decision" , "W_Decision");

	/**
	 * ControlID	btn_NoDiffSelect
	 * TemplateKey	W_NoDiffSelect
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_NoDiffSelect = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_NoDiffSelect" , "W_NoDiffSelect");

	/**
	 * ControlID	btn_AllCheckClear
	 * TemplateKey	W_AllCheckClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllCheckClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllCheckClear" , "W_AllCheckClear");

	/**
	 * ControlID	lst_InventoryResult
	 * TemplateKey	W_InventoryResult
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_InventoryResult = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_InventoryResult" , "W_InventoryResult");

}
//end of class

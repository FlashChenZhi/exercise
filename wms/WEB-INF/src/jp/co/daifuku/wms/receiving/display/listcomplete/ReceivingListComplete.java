// $Id: ReceivingListComplete.java 7152 2010-02-19 06:44:43Z fukuwa $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.receiving.display.listcomplete;
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
 * @version $Revision: 7152 $, $Date: 2010-02-19 15:44:43 +0900 (金, 19 2 2010) $
 * @author  $Author: fukuwa $
 */
public class ReceivingListComplete extends Page
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
	 * ControlID	tab_ReceivingResultInput
	 * TemplateKey	W_ReceivingInput
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_ReceivingResultInput = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_ReceivingResultInput" , "W_ReceivingInput");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_ReceivingPlanDate
	 * TemplateKey	W_ReceivingPlanDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ReceivingPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ReceivingPlanDate" , "W_ReceivingPlanDate");

	/**
	 * ControlID	lbl_Require_ReceivingPlan
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Require_ReceivingPlan = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Require_ReceivingPlan" , "W_Require");

	/**
	 * ControlID	txt_ReceivingPlanDate
	 * TemplateKey	W_PlanDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_ReceivingPlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_ReceivingPlanDate" , "W_PlanDate");

	/**
	 * ControlID	lbl_InputStyle
	 * TemplateKey	W_InputStyle
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InputStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InputStyle" , "W_InputStyle");

	/**
	 * ControlID	lbl_Day
	 * TemplateKey	W_InputStyleDay
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Day = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Day" , "W_InputStyleDay");

	/**
	 * ControlID	lbl_SupplierCode
	 * TemplateKey	W_SupplierCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SupplierCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SupplierCode" , "W_SupplierCode");

	/**
	 * ControlID	txt_SupplierCode
	 * TemplateKey	W_SupplierCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SupplierCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SupplierCode" , "W_SupplierCode");

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
	 * ControlID	chk_InitialInputReceivingNumber
	 * TemplateKey	W_InitialInputReceivingNumber
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_InitialInputReceivingNumber = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_InitialInputReceivingNumber" , "W_InitialInputReceivingNumber");

	/**
	 * ControlID	btn_Display
	 * TemplateKey	W_Display
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Display" , "W_Display");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

	/**
	 * ControlID	btn_Complete
	 * TemplateKey	W_Complete
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Complete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Complete" , "W_Complete");

	/**
	 * ControlID	btn_ListClear
	 * TemplateKey	W_AllClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ListClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ListClear" , "W_AllClear");

	/**
	 * ControlID	lbl_LReceivingPlanDate
	 * TemplateKey	W_ReceivingPlanDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LReceivingPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LReceivingPlanDate" , "W_ReceivingPlanDate");

	/**
	 * ControlID	txt_LRReceivingPlanDate
	 * TemplateKey	W_PlanDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_LRReceivingPlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_LRReceivingPlanDate" , "W_PlanDate");

	/**
	 * ControlID	lst_ReceivingListResultInput
	 * TemplateKey	W_ReceivingListResultInput
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ReceivingListResultInput = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ReceivingListResultInput" , "W_ReceivingListResultInput");

}
//end of class

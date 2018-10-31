// $Id: StorageListComplete.java 7154 2010-02-19 07:15:20Z fukuwa $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.listcomplete;
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
 * @version $Revision: 7154 $, $Date: 2010-02-19 16:15:20 +0900 (金, 19 2 2010) $
 * @author  $Author: fukuwa $
 */
public class StorageListComplete extends Page
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
	 * TemplateKey	W_ListWorkComplete
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_ListWorkComplete");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_StoregePlanDate
	 * TemplateKey	W_StoregePlanDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StoregePlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StoregePlanDate" , "W_StoregePlanDate");

	/**
	 * ControlID	lbl_Require_StoragePlan
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Require_StoragePlan = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Require_StoragePlan" , "W_Require");

	/**
	 * ControlID	txt_StoragePlanDate
	 * TemplateKey	W_StorageDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_StoragePlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_StoragePlanDate" , "W_StorageDate");

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
	 * ControlID	lbl_ListWorkNo
	 * TemplateKey	W_ListWorkNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListWorkNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListWorkNo" , "W_ListWorkNo");

	/**
	 * ControlID	lbl_Require_ListWorkNo
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Require_ListWorkNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Require_ListWorkNo" , "W_Require");

	/**
	 * ControlID	txt_ListWorkNo
	 * TemplateKey	W_ListWorkNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ListWorkNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ListWorkNo" , "W_ListWorkNo");

	/**
	 * ControlID	chk_InitialInputStorageNumber
	 * TemplateKey	W_InitialInputStorageNumber
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_InitialInputStorageNumber = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_InitialInputStorageNumber" , "W_InitialInputStorageNumber");

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
	 * ControlID	btn_ClearStorageQty
	 * TemplateKey	W_ClearListInput
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ClearStorageQty = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ClearStorageQty" , "W_ClearListInput");

	/**
	 * ControlID	btn_ListClear
	 * TemplateKey	W_AllClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ListClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ListClear" , "W_AllClear");

	/**
	 * ControlID	lbl_LStoregePlanDate
	 * TemplateKey	W_StoregePlanDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LStoregePlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LStoregePlanDate" , "W_StoregePlanDate");

	/**
	 * ControlID	txt_LRStoragePlanDate
	 * TemplateKey	W_StorageDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_LRStoragePlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_LRStoragePlanDate" , "W_StorageDate");

	/**
	 * ControlID	lbl_LListWorkNo
	 * TemplateKey	W_ListWorkNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LListWorkNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LListWorkNo" , "W_ListWorkNo");

	/**
	 * ControlID	txt_LRListWorkNo
	 * TemplateKey	W_ListWorkNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_LRListWorkNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_LRListWorkNo" , "W_ListWorkNo");

	/**
	 * ControlID	lst_StorageListResultInput
	 * TemplateKey	W_StorageListResultInput
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_StorageListResultInput = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_StorageListResultInput" , "W_StorageListResultInput");

}
//end of class

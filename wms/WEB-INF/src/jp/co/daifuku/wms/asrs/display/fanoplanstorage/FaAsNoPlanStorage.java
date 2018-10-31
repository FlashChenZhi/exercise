// $Id: FaAsNoPlanStorage.java 5198 2009-10-21 00:40:59Z kishimoto $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.fanoplanstorage;
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
 * @version $Revision: 5198 $, $Date: 2009-10-21 09:40:59 +0900 (水, 21 10 2009) $
 * @author  $Author: kishimoto $
 */
public class FaAsNoPlanStorage extends Page
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
	 * TemplateKey	W_Area_Event
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Area = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Area" , "W_Area_Event");

	/**
	 * ControlID	lbl_WorkPlace
	 * TemplateKey	W_WorkPlace
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkPlace = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkPlace" , "W_WorkPlace");

	/**
	 * ControlID	pul_WorkPlace
	 * TemplateKey	W_WorkPlace
	 * ControlType	LinkedPullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_WorkPlace = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_WorkPlace" , "W_WorkPlace");

	/**
	 * ControlID	lbl_Station
	 * TemplateKey	W_Station
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Station = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Station" , "W_Station");

	/**
	 * ControlID	pul_Station
	 * TemplateKey	W_Station
	 * ControlType	LinkedPullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_Station = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_Station" , "W_Station");

	/**
	 * ControlID	lbl_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	lbl_Require1
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Require1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Require1" , "W_Require");

	/**
	 * ControlID	txt_ItemCode
	 * TemplateKey	W_ItemCode_EntEvent
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode_EntEvent");

	/**
	 * ControlID	btn_PSearchItemCode
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchItemCode" , "W_P_Search");

	/**
	 * ControlID	lbl_ItemName
	 * TemplateKey	W_ItemName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemName" , "W_ItemName");

	/**
	 * ControlID	txt_ItemName
	 * TemplateKey	W_ItemName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemName" , "W_ItemName");

	/**
	 * ControlID	btn_PSearchItemName
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchItemName = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchItemName" , "W_P_Search");

	/**
	 * ControlID	lbl_SoftZone
	 * TemplateKey	W_SoftZone
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SoftZone = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SoftZone" , "W_SoftZone");

	/**
	 * ControlID	txt_SoftZoneName
	 * TemplateKey	W_SoftZoneName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SoftZoneName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SoftZoneName" , "W_SoftZoneName");

	/**
	 * ControlID	lbl_LotNo
	 * TemplateKey	W_LotNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LotNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LotNo" , "W_LotNo");

	/**
	 * ControlID	txt_LotNo
	 * TemplateKey	W_LotNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_LotNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_LotNo" , "W_LotNo");

	/**
	 * ControlID	lbl_StorageQty
	 * TemplateKey	W_StorageQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageQty" , "W_StorageQty");

	/**
	 * ControlID	lbl_Require2
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Require2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Require2" , "W_Require");

	/**
	 * ControlID	txt_StorageQty
	 * TemplateKey	W_StorageQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StorageQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StorageQty" , "W_StorageQty");

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
	 * ControlID	btn_WorkListPrint
	 * TemplateKey	W_InventoryListIssue
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_WorkListPrint = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_WorkListPrint" , "W_InventoryListIssue");

	/**
	 * ControlID	chk_LWorkListPrint
	 * TemplateKey	W_WorkListPrint
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_LWorkListPrint = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_LWorkListPrint" , "W_WorkListPrint");

	/**
	 * ControlID	lbl_MixedItemsMaxMixedItems
	 * TemplateKey	W_MixedItemsMaxMixedItems
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MixedItemsMaxMixedItems = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MixedItemsMaxMixedItems" , "W_MixedItemsMaxMixedItems");

	/**
	 * ControlID	txt_LMixedItems
	 * TemplateKey	W_MixedItems
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_LMixedItems = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_LMixedItems" , "W_MixedItems");

	/**
	 * ControlID	txt_LMaxMixedItems
	 * TemplateKey	W_MaxMixedItems
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_LMaxMixedItems = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_LMaxMixedItems" , "W_MaxMixedItems");

	/**
	 * ControlID	lbl_LSoftZone
	 * TemplateKey	W_SoftZone
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LSoftZone = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LSoftZone" , "W_SoftZone");

	/**
	 * ControlID	pul_LSoftZone
	 * TemplateKey	W_SoftZone
	 * ControlType	LinkedPullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_LSoftZone = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_LSoftZone" , "W_SoftZone");

	/**
	 * ControlID	lst_FaAsNoPlanStorage
	 * TemplateKey	W_FaAsNoPlanStorage
	 * ControlType	ScrollListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_FaAsNoPlanStorage = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_FaAsNoPlanStorage" , "W_FaAsNoPlanStorage");

}
//end of class

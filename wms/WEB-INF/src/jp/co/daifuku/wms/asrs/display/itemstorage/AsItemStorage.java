// $Id: skelten.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.itemstorage;
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
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class AsItemStorage extends Page
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
	 * ControlID	tab_Set
	 * TemplateKey	W_Set
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Set = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Set" , "W_Set");

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
	 * ControlID	lbl_Zone
	 * TemplateKey	W_Zone
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Zone = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Zone" , "W_Zone");

	/**
	 * ControlID	pul_Zone
	 * TemplateKey	W_Zone
	 * ControlType	LinkedPullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_Zone = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_Zone" , "W_Zone");

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
	 * ControlID	lbl_StoregePlanDate
	 * TemplateKey	W_StoregePlanDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StoregePlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StoregePlanDate" , "W_StoregePlanDate");

	/**
	 * ControlID	lbl_RequireStoragePlanDate
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireStoragePlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireStoragePlanDate" , "W_Require");

	/**
	 * ControlID	txt_StoragePlanDate
	 * TemplateKey	W_PlanDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_StoragePlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_StoragePlanDate" , "W_PlanDate");

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
	 * ControlID	lbl_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	lbl_RequireItemCode
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireItemCode" , "W_Require");

	/**
	 * ControlID	txt_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	lbl_StoragePlanLotNo
	 * TemplateKey	W_StoragePlanLotNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StoragePlanLotNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StoragePlanLotNo" , "W_StoragePlanLotNo");

	/**
	 * ControlID	txt_StoragePlanLotNo
	 * TemplateKey	W_StoragePlanLotNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StoragePlanLotNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StoragePlanLotNo" , "W_StoragePlanLotNo");

	/**
	 * ControlID	btn_PSearchPlan
	 * TemplateKey	W_P_SearchPlan
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchPlan = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchPlan" , "W_P_SearchPlan");

	/**
	 * ControlID	lbl_EnteringQty
	 * TemplateKey	W_EnteringQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EnteringQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EnteringQty" , "W_EnteringQty");

	/**
	 * ControlID	txt_InEnteringQty
	 * TemplateKey	W_EnteringQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_InEnteringQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_InEnteringQty" , "W_EnteringQty");

	/**
	 * ControlID	lbl_RestCaseQty
	 * TemplateKey	W_RestCaseQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RestCaseQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RestCaseQty" , "W_RestCaseQty");

	/**
	 * ControlID	txt_InRestCaseQty
	 * TemplateKey	W_RemainingCaseQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_InRestCaseQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_InRestCaseQty" , "W_RemainingCaseQty");

	/**
	 * ControlID	lbl_RestPieceQty
	 * TemplateKey	W_RestPieceQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RestPieceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RestPieceQty" , "W_RestPieceQty");

	/**
	 * ControlID	txt_InRestPieceQty
	 * TemplateKey	W_RemainingPieceQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_InRestPieceQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_InRestPieceQty" , "W_RemainingPieceQty");

	/**
	 * ControlID	lbl_StorageLotNo
	 * TemplateKey	W_StorageLotNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageLotNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageLotNo" , "W_StorageLotNo");

	/**
	 * ControlID	txt_StorageLotNo
	 * TemplateKey	W_StorageLotNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StorageLotNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StorageLotNo" , "W_StorageLotNo");

	/**
	 * ControlID	lbl_StorageCaseQty
	 * TemplateKey	W_StorageCaseQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageCaseQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageCaseQty" , "W_StorageCaseQty");

	/**
	 * ControlID	txt_StorageCaseQty
	 * TemplateKey	W_StockCaseQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StorageCaseQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StorageCaseQty" , "W_StockCaseQty");

	/**
	 * ControlID	lbl_StoragePieceQty
	 * TemplateKey	W_StoragePieceQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StoragePieceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StoragePieceQty" , "W_StoragePieceQty");

	/**
	 * ControlID	txt_StoragePieceQty
	 * TemplateKey	W_StockPieceQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StoragePieceQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StoragePieceQty" , "W_StockPieceQty");

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
	 * ControlID	btn_StorageStart
	 * TemplateKey	W_Set2
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_StorageStart = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_StorageStart" , "W_Set2");

	/**
	 * ControlID	btn_AllClear
	 * TemplateKey	W_AllClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllClear" , "W_AllClear");

	/**
	 * ControlID	chk_IssueReport
	 * TemplateKey	W_IssueReport
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_IssueReport = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_IssueReport" , "W_IssueReport");

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
	 * ControlID	lst_ASRSStorageSet
	 * TemplateKey	W_ASRSStorageSet
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ASRSStorageSet = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ASRSStorageSet" , "W_ASRSStorageSet");

}
//end of class

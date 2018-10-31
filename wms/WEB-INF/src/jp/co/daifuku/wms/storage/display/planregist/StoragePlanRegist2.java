// $Id: StoragePlanRegist2.java 7005 2010-02-16 01:43:17Z fukuwa $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.planregist;
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
 * @version $Revision: 7005 $, $Date: 2010-02-16 10:43:17 +0900 (火, 16 2 2010) $
 * @author  $Author: fukuwa $
 */
public class StoragePlanRegist2 extends Page
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
	 * ControlID	tab_SetRegist2
	 * TemplateKey	W_SetRegist2
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_SetRegist2 = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_SetRegist2" , "W_SetRegist2");

	/**
	 * ControlID	btn_Back
	 * TemplateKey	W_Back
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "W_Back");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_MenuNoFunc
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_MenuNoFunc");

	/**
	 * ControlID	lbl_StoragePlanDate
	 * TemplateKey	W_StoregePlanDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StoragePlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StoragePlanDate" , "W_StoregePlanDate");

	/**
	 * ControlID	lbl_InPlanDate
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InPlanDate" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_SlipNumber
	 * TemplateKey	W_SlipNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SlipNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SlipNumber" , "W_SlipNumber");

	/**
	 * ControlID	lbl_InSlip
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InSlip = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InSlip" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_LineNo
	 * TemplateKey	W_LineNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LineNo" , "W_LineNo");

	/**
	 * ControlID	txt_LineNo
	 * TemplateKey	W_LineNo
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_LineNo = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_LineNo" , "W_LineNo");

	/**
	 * ControlID	lbl_BranchNo
	 * TemplateKey	W_BranchNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BranchNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BranchNo" , "W_BranchNo");

	/**
	 * ControlID	txt_BranchNo
	 * TemplateKey	W_BranchNo
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_BranchNo = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_BranchNo" , "W_BranchNo");

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
	 * ControlID	btn_PSearchItem
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchItem = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchItem" , "W_P_Search");

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
	 * ControlID	lbl_EnteringQty
	 * TemplateKey	W_EnteringQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EnteringQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EnteringQty" , "W_EnteringQty");

	/**
	 * ControlID	txt_EnteringQty
	 * TemplateKey	W_EnteringQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_EnteringQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_EnteringQty" , "W_EnteringQty");

	/**
	 * ControlID	lbl_JanCode
	 * TemplateKey	W_JanCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JanCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JanCode" , "W_JanCode");

	/**
	 * ControlID	txt_JanCode
	 * TemplateKey	W_JanCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_JanCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_JanCode" , "W_JanCode");

	/**
	 * ControlID	lbl_CaseITF
	 * TemplateKey	W_CaseITF
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseITF = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseITF" , "W_CaseITF");

	/**
	 * ControlID	txt_CaseITF
	 * TemplateKey	W_CaseITF
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CaseITF = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CaseITF" , "W_CaseITF");

	/**
	 * ControlID	lbl_PlanCaseQty
	 * TemplateKey	W_PlanCaseQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PlanCaseQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PlanCaseQty" , "W_PlanCaseQty");

	/**
	 * ControlID	txt_PlanCaseQty
	 * TemplateKey	W_WorkCaseQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PlanCaseQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PlanCaseQty" , "W_WorkCaseQty");

	/**
	 * ControlID	lbl_PlanPieceQty
	 * TemplateKey	W_PlanPieceQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PlanPieceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PlanPieceQty" , "W_PlanPieceQty");

	/**
	 * ControlID	txt_PlanPieceQty
	 * TemplateKey	W_WorkPieceQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PlanPieceQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PlanPieceQty" , "W_WorkPieceQty");

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
	 * ControlID	lbl_StorageArea
	 * TemplateKey	W_StorageArea
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageArea = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageArea" , "W_StorageArea");

	/**
	 * ControlID	pul_StorageArea
	 * TemplateKey	W_Area_Event
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_StorageArea = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_StorageArea" , "W_Area_Event");

	/**
	 * ControlID	lbl_StoraeLocation
	 * TemplateKey	W_StoraeLocation
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StoraeLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StoraeLocation" , "W_StoraeLocation");

	/**
	 * ControlID	txt_StorageLocation
	 * TemplateKey	W_Location
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StorageLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StorageLocation" , "W_Location");

	/**
	 * ControlID	lbl_InputStyle
	 * TemplateKey	W_InputStyle
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InputStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InputStyle" , "W_InputStyle");

	/**
	 * ControlID	lbl_InLocationStyle
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InLocationStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InLocationStyle" , "W_In_JavaSet");

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
	 * TemplateKey	W_Set
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "W_Set");

	/**
	 * ControlID	btn_AllClear
	 * TemplateKey	W_AllClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllClear" , "W_AllClear");

	/**
	 * ControlID	lst_StoragePlanInput
	 * TemplateKey	W_StoragePlanInput
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_StoragePlanInput = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_StoragePlanInput" , "W_StoragePlanInput");

}
//end of class

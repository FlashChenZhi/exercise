// $Id: RetrievalPlanRegist2.java 1966 2008-12-11 08:13:43Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.planregist;
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
 * @version $Revision: 1966 $, $Date: 2008-12-11 17:13:43 +0900 (木, 11 12 2008) $
 * @author  $Author: okayama $
 */
public class RetrievalPlanRegist2 extends Page
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
	 * ControlID	Regist2
	 * TemplateKey	W_SetRegist2
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab Regist2 = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("Regist2" , "W_SetRegist2");

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
	 * ControlID	lbl_RetrievalPlanDate
	 * TemplateKey	W_RetrievalPlanDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalPlanDate" , "W_RetrievalPlanDate");

	/**
	 * ControlID	txt_R_DateFlat
	 * TemplateKey	W_R_DateFlat
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_R_DateFlat = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_R_DateFlat" , "W_R_DateFlat");

	/**
	 * ControlID	lbl_TicketNo
	 * TemplateKey	W_SlipNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TicketNo" , "W_SlipNumber");

	/**
	 * ControlID	lbl_In_TicketNo
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_TicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_TicketNo" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_LineNo
	 * TemplateKey	W_LineNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LineNo" , "W_LineNo");

	/**
	 * ControlID	lbl_LineRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LineRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LineRequire" , "W_Require");

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
	 * ControlID	lbl_BatchNo
	 * TemplateKey	W_BatchNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BatchNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BatchNo" , "W_BatchNo");

	/**
	 * ControlID	lbl_BatchRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BatchRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BatchRequire" , "W_Require");

	/**
	 * ControlID	txt_BatchNo
	 * TemplateKey	W_BatchNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BatchNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BatchNo" , "W_BatchNo");

	/**
	 * ControlID	lbl_OrderNo
	 * TemplateKey	W_OrderNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OrderNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OrderNo" , "W_OrderNo");

	/**
	 * ControlID	lbl_OrderRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OrderRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OrderRequire" , "W_Require");

	/**
	 * ControlID	txt_OrderNo
	 * TemplateKey	W_OrderNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_OrderNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_OrderNo" , "W_OrderNo");

	/**
	 * ControlID	lbl_CustomerCode
	 * TemplateKey	W_CustomerCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerCode" , "W_CustomerCode");

	/**
	 * ControlID	txt_CustomerCode
	 * TemplateKey	W_CustomerCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerCode" , "W_CustomerCode");

	/**
	 * ControlID	btn_SearchCustomer
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchCustomer = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchCustomer" , "W_P_Search");

	/**
	 * ControlID	lbl_CustomerName
	 * TemplateKey	W_CustomerName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerName" , "W_CustomerName");

	/**
	 * ControlID	txt_CustomerName
	 * TemplateKey	W_CustomerName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerName" , "W_CustomerName");

	/**
	 * ControlID	lbl_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	lbl_ItemRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemRequire" , "W_Require");

	/**
	 * ControlID	txt_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	btn_SearchItem
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchItem = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchItem" , "W_P_Search");

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
	 * ControlID	lbl_EneteringQty
	 * TemplateKey	W_EnteringQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EneteringQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EneteringQty" , "W_EnteringQty");

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
	 * ControlID	lbl_RetrievalArea
	 * TemplateKey	W_RetrievalArea
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalArea = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalArea" , "W_RetrievalArea");

	/**
	 * ControlID	pul_RetrievalArea
	 * TemplateKey	W_RetrievalArea
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_RetrievalArea = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_RetrievalArea" , "W_RetrievalArea");

	/**
	 * ControlID	lbl_RetrievalPlanLocation
	 * TemplateKey	W_RetrievalPlanLocation
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalPlanLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalPlanLocation" , "W_RetrievalPlanLocation");

	/**
	 * ControlID	txt_RetrievalPlanLocation
	 * TemplateKey	W_Location
	 * ControlType	FormatTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FormatTextBox txt_RetrievalPlanLocation = jp.co.daifuku.bluedog.ui.control.FormatTextBoxFactory.getInstance("txt_RetrievalPlanLocation" , "W_Location");

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
	 * ControlID	btn_Submit
	 * TemplateKey	W_Set
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "W_Set");

	/**
	 * ControlID	btn_AllClear
	 * TemplateKey	W_AllClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllClear" , "W_AllClear");

	/**
	 * ControlID	lst_RetrievalPlanInput
	 * TemplateKey	W_RetrievalPlanInput
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_RetrievalPlanInput = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_RetrievalPlanInput" , "W_RetrievalPlanInput");

}
//end of class

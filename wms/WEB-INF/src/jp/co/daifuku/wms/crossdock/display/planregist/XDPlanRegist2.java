// $Id: XDPlanRegist2.java 1650 2008-12-02 00:49:06Z mikuriya $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.crossdock.display.planregist;
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
 * @version $Revision: 1650 $, $Date: 2008-12-02 09:49:06 +0900 (火, 02 12 2008) $
 * @author  $Author: mikuriya $
 */
public class XDPlanRegist2 extends Page
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
	 * ControlID	lbl_PlanDate
	 * TemplateKey	W_PlanDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PlanDate" , "W_PlanDate");

	/**
	 * ControlID	lbl_InPlanDate
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InPlanDate" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_BatchNo
	 * TemplateKey	W_BatchNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BatchNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BatchNo" , "W_BatchNo");

	/**
	 * ControlID	lbl_InBatchNo
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InBatchNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InBatchNo" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_ReceivingSlipNo
	 * TemplateKey	W_ReceivingSlipNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ReceivingSlipNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ReceivingSlipNo" , "W_ReceivingSlipNo");

	/**
	 * ControlID	lbl_InReceivingSlipNo
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InReceivingSlipNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InReceivingSlipNo" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_ReceivingSlipLineNo
	 * TemplateKey	W_ReceivingSlipLineNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ReceivingSlipLineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ReceivingSlipLineNo" , "W_ReceivingSlipLineNo");

	/**
	 * ControlID	lbl_InReceivingSlipLineNo
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InReceivingSlipLineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InReceivingSlipLineNo" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_Supplier
	 * TemplateKey	W_Supplier
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Supplier = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Supplier" , "W_Supplier");

	/**
	 * ControlID	lbl_InSupplierCode
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InSupplierCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InSupplierCode" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_InSupplierName
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InSupplierName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InSupplierName" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_Item
	 * TemplateKey	W_Item
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Item = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Item" , "W_Item");

	/**
	 * ControlID	lbl_InItemCode
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InItemCode" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_InItemName
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InItemName" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_EnteringQty
	 * TemplateKey	W_EnteringQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EnteringQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EnteringQty" , "W_EnteringQty");

	/**
	 * ControlID	lbl_InEnteringQty
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InEnteringQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InEnteringQty" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_JanCode
	 * TemplateKey	W_JanCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JanCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JanCode" , "W_JanCode");

	/**
	 * ControlID	lbl_InJanCode
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InJanCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InJanCode" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_CaseITF
	 * TemplateKey	W_CaseITF
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseITF = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseITF" , "W_CaseITF");

	/**
	 * ControlID	lbl_InCaseITF
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InCaseITF = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InCaseITF" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_LotNo
	 * TemplateKey	W_LotNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LotNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LotNo" , "W_LotNo");

	/**
	 * ControlID	lbl_InLotNo
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InLotNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InLotNo" , "W_In_JavaSet");

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
	 * ControlID	lbl_ShipSlipNo
	 * TemplateKey	W_ShipSlipNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ShipSlipNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ShipSlipNo" , "W_ShipSlipNo");

	/**
	 * ControlID	lbl_RequireShipSlipNo
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireShipSlipNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireShipSlipNo" , "W_Require");

	/**
	 * ControlID	txt_ShipSlipNumber
	 * TemplateKey	W_SlipNumberKeyEvent
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ShipSlipNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ShipSlipNumber" , "W_SlipNumberKeyEvent");

	/**
	 * ControlID	lbl_ShipSlipLineNo
	 * TemplateKey	W_ShipSlipLineNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ShipSlipLineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ShipSlipLineNo" , "W_ShipSlipLineNo");

	/**
	 * ControlID	txt_ShipSlipLineNo
	 * TemplateKey	W_LineNo
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ShipSlipLineNo = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ShipSlipLineNo" , "W_LineNo");

	/**
	 * ControlID	lbl_CustomerCode
	 * TemplateKey	W_CustomerCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerCode" , "W_CustomerCode");

	/**
	 * ControlID	lbl_RequireCustomerCode
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireCustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireCustomerCode" , "W_Require");

	/**
	 * ControlID	txt_CustomerCode
	 * TemplateKey	W_CustomerCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerCode" , "W_CustomerCode");

	/**
	 * ControlID	btn_PSearch
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearch = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearch" , "W_P_Search");

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
	 * ControlID	lbl_SortPlace
	 * TemplateKey	W_SortPlace
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SortPlace = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SortPlace" , "W_SortPlace");

	/**
	 * ControlID	lbl_RequireSortPlace
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireSortPlace = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireSortPlace" , "W_Require");

	/**
	 * ControlID	txt_SortPlace
	 * TemplateKey	W_SortPlace
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SortPlace = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SortPlace" , "W_SortPlace");

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
	 * ControlID	lst_TcPlanDataRegist
	 * TemplateKey	W_TcPlanDataRegist
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_TcPlanDataRegist = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_TcPlanDataRegist" , "W_TcPlanDataRegist");

}
//end of class

// $Id: PCTRetResultMnt.java 3362 2009-03-12 04:18:25Z kumano $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.retrieval.display.resultmnt;
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
 * @version $Revision: 3362 $, $Date: 2009-03-12 13:18:25 +0900 (木, 12 3 2009) $
 * @author  $Author: kumano $
 */
public class PCTRetResultMnt extends Page
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
	 * ControlID	tab_WorkMnt
	 * TemplateKey	W_WorkMnt
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_WorkMnt = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_WorkMnt" , "W_WorkMnt");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_ConsignorCode
	 * TemplateKey	W_ConsignorCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");

	/**
	 * ControlID	lbl_RequireConsignor
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireConsignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireConsignor" , "W_Require");

	/**
	 * ControlID	txt_ConsignorCode
	 * TemplateKey	W_ConsignorCodePCT
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCodePCT");

	/**
	 * ControlID	txt_ConsignorName
	 * TemplateKey	T_R_FunctionName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorName" , "T_R_FunctionName");

	/**
	 * ControlID	lbl_OrderNo
	 * TemplateKey	W_OrderNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OrderNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OrderNo" , "W_OrderNo");

	/**
	 * ControlID	lbl_RequireOrderNo
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireOrderNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireOrderNo" , "W_Require");

	/**
	 * ControlID	txt_OrderNo
	 * TemplateKey	W_OrderNoPCT
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_OrderNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_OrderNo" , "W_OrderNoPCT");

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
	 * ControlID	btn_SearchItemCode
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchItemCode" , "W_P_Search");

	/**
	 * ControlID	txt_ItemName
	 * TemplateKey	W_R_ItemName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemName" , "W_R_ItemName");

	/**
	 * ControlID	lbl_WorkStatus
	 * TemplateKey	W_WorkStatus
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkStatus" , "W_WorkStatus");

	/**
	 * ControlID	pul_WorkStatusPCT4
	 * TemplateKey	W_WorkStatusPCT4
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_WorkStatusPCT4 = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_WorkStatusPCT4" , "W_WorkStatusPCT4");

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
	 * ControlID	btn_ItemUnit
	 * TemplateKey	W_ItemUnit
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ItemUnit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ItemUnit" , "W_ItemUnit");

	/**
	 * ControlID	btn_ModifySet
	 * TemplateKey	W_ModifySet
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ModifySet = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ModifySet" , "W_ModifySet");

	/**
	 * ControlID	btn_ListClear
	 * TemplateKey	W_ListClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ListClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ListClear" , "W_ListClear");

	/**
	 * ControlID	lbl_PlanDate
	 * TemplateKey	W_PlanDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PlanDate" , "W_PlanDate");

	/**
	 * ControlID	txt_PlanDay
	 * TemplateKey	W_R_PlanDay
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_PlanDay = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_PlanDay" , "W_R_PlanDay");

	/**
	 * ControlID	lbl_BatchNo
	 * TemplateKey	W_BatchNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BatchNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BatchNo" , "W_BatchNo");

	/**
	 * ControlID	txt_BatchNo
	 * TemplateKey	W_R_BatchNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BatchNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BatchNo" , "W_R_BatchNo");

	/**
	 * ControlID	lbl_BatchSeqNo
	 * TemplateKey	W_BatchSeqNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BatchSeqNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BatchSeqNo" , "W_BatchSeqNo");

	/**
	 * ControlID	txt_BatchSeqNoPCT
	 * TemplateKey	W_R_BatchSeqNoPCT
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BatchSeqNoPCT = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BatchSeqNoPCT" , "W_R_BatchSeqNoPCT");

	/**
	 * ControlID	lbl_Area
	 * TemplateKey	W_Area
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Area = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Area" , "W_Area");

	/**
	 * ControlID	txt_Area
	 * TemplateKey	W_R_Area
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Area = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Area" , "W_R_Area");

	/**
	 * ControlID	txt_AreaName
	 * TemplateKey	W_R_AreaName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_AreaName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_AreaName" , "W_R_AreaName");

	/**
	 * ControlID	lbl_RegularCostomer
	 * TemplateKey	W_RegularCostomer
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RegularCostomer = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RegularCostomer" , "W_RegularCostomer");

	/**
	 * ControlID	txt_RegularCustomerCode
	 * TemplateKey	W_R_RegularCustomerCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RegularCustomerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RegularCustomerCode" , "W_R_RegularCustomerCode");

	/**
	 * ControlID	txt_RegularCustomerName
	 * TemplateKey	W_R_RegularCustomerName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RegularCustomerName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RegularCustomerName" , "W_R_RegularCustomerName");

	/**
	 * ControlID	lbl_Costomer
	 * TemplateKey	W_Costomer
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Costomer = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Costomer" , "W_Costomer");

	/**
	 * ControlID	txt_CustomerCode
	 * TemplateKey	W_R_CustomerCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerCode" , "W_R_CustomerCode");

	/**
	 * ControlID	txt_CustomerName
	 * TemplateKey	W_R_CustomerName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerName" , "W_R_CustomerName");

	/**
	 * ControlID	lst_resultMntList
	 * TemplateKey	W_PCTResultMntList
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_resultMntList = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_resultMntList" , "W_PCTResultMntList");

}
//end of class

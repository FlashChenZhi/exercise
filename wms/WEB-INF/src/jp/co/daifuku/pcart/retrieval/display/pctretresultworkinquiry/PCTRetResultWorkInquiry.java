// $Id: PCTRetResultWorkInquiry.java 3561 2009-03-17 02:54:48Z rnakai $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.retrieval.display.pctretresultworkinquiry;
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
 * @version $Revision: 3561 $, $Date: 2009-03-17 11:54:48 +0900 (火, 17 3 2009) $
 * @author  $Author: rnakai $
 */
public class PCTRetResultWorkInquiry extends Page
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
	 * TemplateKey	W_Inquiry2
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Inquiry2");

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
	 * ControlID	lbl_Require
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Require = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Require" , "W_Require");

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
	 * ControlID	lbl_WorkDate
	 * TemplateKey	W_WorkDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkDate" , "W_WorkDate");

	/**
	 * ControlID	txt_WorkDate
	 * TemplateKey	W_WorkDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_WorkDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_WorkDate" , "W_WorkDate");

	/**
	 * ControlID	lbl_BatchNo
	 * TemplateKey	W_BatchNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BatchNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BatchNo" , "W_BatchNo");

	/**
	 * ControlID	txt_BatchNo
	 * TemplateKey	W_BatchNoPCT
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BatchNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BatchNo" , "W_BatchNoPCT");

	/**
	 * ControlID	lbl_BatchSeqNo
	 * TemplateKey	W_BatchSeqNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BatchSeqNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BatchSeqNo" , "W_BatchSeqNo");

	/**
	 * ControlID	txt_BatchSeqNo
	 * TemplateKey	W_BatchSeqNoPCT
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BatchSeqNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BatchSeqNo" , "W_BatchSeqNoPCT");

	/**
	 * ControlID	lbl_Area
	 * TemplateKey	W_Area
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Area = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Area" , "W_Area");

	/**
	 * ControlID	pul_AreaNo
	 * TemplateKey	W_RetrievalArea
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_AreaNo = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_AreaNo" , "W_RetrievalArea");

	/**
	 * ControlID	lbl_txtRegularCustomerCode
	 * TemplateKey	W_RegularConsignorCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_txtRegularCustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_txtRegularCustomerCode" , "W_RegularConsignorCode");

	/**
	 * ControlID	txt_RegularCustomerCode
	 * TemplateKey	W_RegularCustomerCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RegularCustomerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RegularCustomerCode" , "W_RegularCustomerCode");

	/**
	 * ControlID	btn_P_Search_RegularCustomerCd
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search_RegularCustomerCd = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search_RegularCustomerCd" , "W_P_Search");

	/**
	 * ControlID	txt_RegularCustomerName
	 * TemplateKey	T_R_FunctionName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RegularCustomerName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RegularCustomerName" , "T_R_FunctionName");

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
	 * ControlID	btn_P_Search_CustomerCode
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search_CustomerCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search_CustomerCode" , "W_P_Search");

	/**
	 * ControlID	txt_CustomerName
	 * TemplateKey	T_R_FunctionName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerName" , "T_R_FunctionName");

	/**
	 * ControlID	lbl_OrderNo
	 * TemplateKey	W_OrderNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OrderNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OrderNo" , "W_OrderNo");

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
	 * ControlID	btn_P_Search_ItemCode
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search_ItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search_ItemCode" , "W_P_Search");

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
	 * ControlID	pul_WorkStatus
	 * TemplateKey	W_WorkStatusPCT2
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_WorkStatus = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_WorkStatus" , "W_WorkStatusPCT2");

	/**
	 * ControlID	lbl_User
	 * TemplateKey	W_User
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_User = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_User" , "W_User");

	/**
	 * ControlID	txt_User
	 * TemplateKey	W_User
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_User = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_User" , "W_User");

	/**
	 * ControlID	btn_P_Search_User
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search_User = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search_User" , "W_P_Search");

	/**
	 * ControlID	txt_UserName
	 * TemplateKey	W_R_UserName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserName" , "W_R_UserName");

	/**
	 * ControlID	btn_P_Display
	 * TemplateKey	W_P_Display
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Display" , "W_P_Display");

	/**
	 * ControlID	btn_Print
	 * TemplateKey	W_Print
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Print = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Print" , "W_Print");

	/**
	 * ControlID	btn_XLS
	 * TemplateKey	W_XLS
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_XLS = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_XLS" , "W_XLS");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

}
//end of class

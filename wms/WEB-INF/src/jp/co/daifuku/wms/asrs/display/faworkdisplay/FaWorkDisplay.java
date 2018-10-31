// $Id: skelten.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.faworkdisplay;
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
public class FaWorkDisplay extends Page
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
	 * TemplateKey	W_WorkDisplay
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_WorkDisplay");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_Station
	 * TemplateKey	W_Station
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Station = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Station" , "W_Station");

	/**
	 * ControlID	pul_Station
	 * TemplateKey	W_Station
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Station = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Station" , "W_Station");

	/**
	 * ControlID	btn_ReDisplay
	 * TemplateKey	W_ReDisplayFunc
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ReDisplay = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ReDisplay" , "W_ReDisplayFunc");

	/**
	 * ControlID	btn_Complete
	 * TemplateKey	W_Complete
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Complete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Complete" , "W_Complete");

	/**
	 * ControlID	btn_PrevWork
	 * TemplateKey	W_PrevWork
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PrevWork = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PrevWork" , "W_PrevWork");

	/**
	 * ControlID	btn_NextWork
	 * TemplateKey	W_NextWork
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_NextWork = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_NextWork" , "W_NextWork");

	/**
	 * ControlID	lbl_RestWork
	 * TemplateKey	W_RestWork_Large
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RestWork = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RestWork" , "W_RestWork_Large");

	/**
	 * ControlID	txt_RestWork
	 * TemplateKey	W_RestWork_Large
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RestWork = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RestWork" , "W_RestWork_Large");

	/**
	 * ControlID	lbl_WorkNo
	 * TemplateKey	W_WorkNo_Large
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkNo" , "W_WorkNo_Large");

	/**
	 * ControlID	txt_WorkNo
	 * TemplateKey	W_WorkNo_Large
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkNo" , "W_WorkNo_Large");

	/**
	 * ControlID	lbl_Location
	 * TemplateKey	W_Location_Large
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Location = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Location" , "W_Location_Large");

	/**
	 * ControlID	txt_Location
	 * TemplateKey	W_Location_Large
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Location = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Location" , "W_Location_Large");

	/**
	 * ControlID	lbl_CarryingFlag
	 * TemplateKey	W_CarryingFlag_Large
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CarryingFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CarryingFlag" , "W_CarryingFlag_Large");

	/**
	 * ControlID	txt_CarryingFlag
	 * TemplateKey	W_CarryingFlag_Large
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CarryingFlag = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CarryingFlag" , "W_CarryingFlag_Large");

	/**
	 * ControlID	lbl_InstructionDetail
	 * TemplateKey	W_RetrievalInstructionDetail_Large
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InstructionDetail = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InstructionDetail" , "W_RetrievalInstructionDetail_Large");

	/**
	 * ControlID	txt_InstructionDetail
	 * TemplateKey	W_RetrievalInstructionDetail_Large
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_InstructionDetail = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_InstructionDetail" , "W_RetrievalInstructionDetail_Large");

	/**
	 * ControlID	lbl_ItemCode
	 * TemplateKey	W_ItemCode_Large
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode_Large");

	/**
	 * ControlID	txt_ItemCode
	 * TemplateKey	W_ItemCode_Large
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode_Large");

	/**
	 * ControlID	lbl_ItemName
	 * TemplateKey	W_ItemName_Large
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemName" , "W_ItemName_Large");

	/**
	 * ControlID	txt_ItemName
	 * TemplateKey	W_ItemName_Large
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemName" , "W_ItemName_Large");

	/**
	 * ControlID	lbl_LotNo
	 * TemplateKey	W_LotNo_Large
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LotNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LotNo" , "W_LotNo_Large");

	/**
	 * ControlID	txt_LotNo
	 * TemplateKey	W_LotNo_Large
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_LotNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_LotNo" , "W_LotNo_Large");

	/**
	 * ControlID	lbl_StockQtyWorkQty
	 * TemplateKey	W_StockQtyWorkQty_Large
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockQtyWorkQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockQtyWorkQty" , "W_StockQtyWorkQty_Large");

	/**
	 * ControlID	txt_StockQty
	 * TemplateKey	W_StockQty_Large
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StockQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StockQty" , "W_StockQty_Large");

	/**
	 * ControlID	txt_WorkQty
	 * TemplateKey	W_WorkQty_Large
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_WorkQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_WorkQty" , "W_WorkQty_Large");

	/**
	 * ControlID	lbl_StorageDateTime
	 * TemplateKey	W_StorageDateTime_Large
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageDateTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageDateTime" , "W_StorageDateTime_Large");

	/**
	 * ControlID	txt_StorageDate
	 * TemplateKey	W_Day_Large
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StorageDate = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StorageDate" , "W_Day_Large");

	/**
	 * ControlID	txt_StorageTime
	 * TemplateKey	W_Time_Large
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StorageTime = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StorageTime" , "W_Time_Large");

	/**
	 * ControlID	lbl_BatchNoTicketNo
	 * TemplateKey	W_BatchNoTicketNo_Large
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BatchNoTicketNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BatchNoTicketNo" , "W_BatchNoTicketNo_Large");

	/**
	 * ControlID	txt_BatchNo
	 * TemplateKey	W_BatchNo_Large
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BatchNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BatchNo" , "W_BatchNo_Large");

	/**
	 * ControlID	txt_SlipNumber
	 * TemplateKey	W_SlipNumber_Large
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SlipNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SlipNumber" , "W_SlipNumber_Large");

	/**
	 * ControlID	lbl_LineNo
	 * TemplateKey	W_LineNo_Large
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LineNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LineNo" , "W_LineNo_Large");

	/**
	 * ControlID	txt_LineNo
	 * TemplateKey	W_LineNo_Large
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_LineNo = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_LineNo" , "W_LineNo_Large");

}
//end of class

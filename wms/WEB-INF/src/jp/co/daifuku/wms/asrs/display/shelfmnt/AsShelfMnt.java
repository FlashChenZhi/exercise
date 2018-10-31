// $Id: skelten.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.shelfmnt;
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
public class AsShelfMnt extends Page
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
	 * ControlID	tab_InfomationChange
	 * TemplateKey	W_InfomationChange
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_InfomationChange = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_InfomationChange" , "W_InfomationChange");

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
	 * ControlID	lbl_LocationStatus
	 * TemplateKey	W_LocationStatus
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationStatus" , "W_LocationStatus");

	/**
	 * ControlID	chk_EmptyLocation
	 * TemplateKey	W_EmptyLocation
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_EmptyLocation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_EmptyLocation" , "W_EmptyLocation");

	/**
	 * ControlID	chk_EmptyPBLocation
	 * TemplateKey	W_EmptyPBLocation
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_EmptyPBLocation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_EmptyPBLocation" , "W_EmptyPBLocation");

	/**
	 * ControlID	chk_StoredLocation
	 * TemplateKey	W_StoredLocation
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_StoredLocation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_StoredLocation" , "W_StoredLocation");

	/**
	 * ControlID	lbl_LocationList
	 * TemplateKey	W_LocationList
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationList = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationList" , "W_LocationList");

	/**
	 * ControlID	btn_PInquiry
	 * TemplateKey	W_P_Inquiry
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PInquiry = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PInquiry" , "W_P_Inquiry");

	/**
	 * ControlID	lbl_Location
	 * TemplateKey	W_Location
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Location = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Location" , "W_Location");

	/**
	 * ControlID	lbl_RequireLocation
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireLocation" , "W_Require");

	/**
	 * ControlID	txt_Location
	 * TemplateKey	W_Location
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Location = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Location" , "W_Location");

	/**
	 * ControlID	btn_PLocationDetail
	 * TemplateKey	W_P_LocationDetail
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PLocationDetail = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PLocationDetail" , "W_P_LocationDetail");

	/**
	 * ControlID	lbl_InputStyleLoc
	 * TemplateKey	W_InputStyle
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InputStyleLoc = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InputStyleLoc" , "W_InputStyle");

	/**
	 * ControlID	lbl_LocationStyle
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationStyle" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_ProcessFlag
	 * TemplateKey	W_ProcessFlag
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ProcessFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ProcessFlag" , "W_ProcessFlag");

	/**
	 * ControlID	btn_Submit
	 * TemplateKey	W_Set5
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "W_Set5");

	/**
	 * ControlID	btn_PModify
	 * TemplateKey	W_P_Modify
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PModify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PModify" , "W_P_Modify");

	/**
	 * ControlID	btn_PDelete
	 * TemplateKey	W_P_Delete
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PDelete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PDelete" , "W_P_Delete");

	/**
	 * ControlID	lbl_LocationAndLocationStatus
	 * TemplateKey	W_LocationAndLocationStatus
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationAndLocationStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationAndLocationStatus" , "W_LocationAndLocationStatus");

	/**
	 * ControlID	lbl_InJavaSetLocation
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InJavaSetLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InJavaSetLocation" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_InJavaSetLocationStatus
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InJavaSetLocationStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InJavaSetLocationStatus" , "W_In_JavaSet");

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
	 * ControlID	lbl_StockCaseQty
	 * TemplateKey	W_StockCaseQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockCaseQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockCaseQty" , "W_StockCaseQty");

	/**
	 * ControlID	txt_StockCaseQty
	 * TemplateKey	W_StockCaseQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StockCaseQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StockCaseQty" , "W_StockCaseQty");

	/**
	 * ControlID	lbl_StockPieceQty
	 * TemplateKey	W_StockPieceQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockPieceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockPieceQty" , "W_StockPieceQty");

	/**
	 * ControlID	txt_StockPieceQty
	 * TemplateKey	W_StockPieceQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StockPieceQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StockPieceQty" , "W_StockPieceQty");

	/**
	 * ControlID	lbl_StorageDate
	 * TemplateKey	W_StorageDateTime
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageDate" , "W_StorageDateTime");

	/**
	 * ControlID	txt_StorageDate
	 * TemplateKey	W_StorageDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_StorageDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_StorageDate" , "W_StorageDate");

	/**
	 * ControlID	txt_StorageTime
	 * TemplateKey	W_StorageTime
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_StorageTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_StorageTime" , "W_StorageTime");

	/**
	 * ControlID	lbl_InputStyleDate
	 * TemplateKey	W_InputStyle
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InputStyleDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InputStyleDate" , "W_InputStyle");

	/**
	 * ControlID	lbl_DateStyle
	 * TemplateKey	W_InputStyleDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DateStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DateStyle" , "W_InputStyleDate");

	/**
	 * ControlID	btn_Set
	 * TemplateKey	W_Set2
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "W_Set2");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

}
//end of class

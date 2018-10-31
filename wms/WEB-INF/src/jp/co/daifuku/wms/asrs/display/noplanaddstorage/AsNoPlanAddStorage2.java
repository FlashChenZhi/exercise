// $Id: AsNoPlanAddStorage2.java,v 1.1.1.1 2009/02/10 08:55:19 arai Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.noplanaddstorage;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2009/02/10 08:55:19 $
 * @author  $Author: arai $
 */
public class AsNoPlanAddStorage2 extends Page
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
	 * ControlID	NoPlanAddStorage2
	 * TemplateKey	W_LocationSelectInputStorageInfo2
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab NoPlanAddStorage2 = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("NoPlanAddStorage2" , "W_LocationSelectInputStorageInfo2");

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
	 * ControlID	lbl_Area
	 * TemplateKey	W_Area
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Area = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Area" , "W_Area");

	/**
	 * ControlID	lbl_In_Area
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_Area = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_Area" , "W_In_JavaSet");

	/**
	 * ControlID	pul_Area
	 * TemplateKey	W_Area
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Area = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Area" , "W_Area");

	/**
	 * ControlID	lbl_Location
	 * TemplateKey	W_Location
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Location = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Location" , "W_Location");

	/**
	 * ControlID	lbl_In_Location
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_Location = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_Location" , "W_In_JavaSet");

	/**
	 * ControlID	btn_LocationDetail
	 * TemplateKey	W_P_LocationDetail
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_LocationDetail = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_LocationDetail" , "W_P_LocationDetail");

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
	 * ControlID	lbl_ItemCode_Require
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode_Require = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode_Require" , "W_Require");

	/**
	 * ControlID	txt_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	btn_ItemCodeSearch
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ItemCodeSearch = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ItemCodeSearch" , "W_P_Search");

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
	 * ControlID	lbl_StorageCaseQty
	 * TemplateKey	W_StorageCaseQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageCaseQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageCaseQty" , "W_StorageCaseQty");

	/**
	 * ControlID	txt_StorageCaseQty
	 * TemplateKey	W_WorkCaseQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StorageCaseQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StorageCaseQty" , "W_WorkCaseQty");

	/**
	 * ControlID	lbl_StoragePieceQty
	 * TemplateKey	W_StoragePieceQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StoragePieceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StoragePieceQty" , "W_StoragePieceQty");

	/**
	 * ControlID	txt_StoragePieceQty
	 * TemplateKey	W_WorkPieceQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StoragePieceQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StoragePieceQty" , "W_WorkPieceQty");

	/**
	 * ControlID	lbl_Why
	 * TemplateKey	W_Why
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Why = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Why" , "W_Why");

	/**
	 * ControlID	pul_Why
	 * TemplateKey	W_Why
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Why = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Why" , "W_Why");

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
	 * ControlID	lst_AsRsNoPlanStorage
	 * TemplateKey	W_ASRSNoPlanStorage
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_AsRsNoPlanStorage = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_AsRsNoPlanStorage" , "W_ASRSNoPlanStorage");

}
//end of class

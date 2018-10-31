// $Id: RetrievalListStart.java 7138 2010-02-18 07:55:09Z shibamoto $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.liststart;
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
 * @version $Revision: 7138 $, $Date: 2010-02-18 16:55:09 +0900 (木, 18 2 2010) $
 * @author  $Author: shibamoto $
 */
public class RetrievalListStart extends Page
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
	 * TemplateKey	W_ListWorkStart
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_ListWorkStart");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_RetrievalPlanDate
	 * TemplateKey	W_RetrievalPlanDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalPlanDate" , "W_RetrievalPlanDate");

	/**
	 * ControlID	txt_RetrievalPlanDate
	 * TemplateKey	W_PlanDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_RetrievalPlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_RetrievalPlanDate" , "W_PlanDate");

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
	 * ControlID	lbl_BatchNo
	 * TemplateKey	W_BatchNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BatchNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BatchNo" , "W_BatchNo");

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
	 * ControlID	txt_OrderNoFrom
	 * TemplateKey	W_OrderNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_OrderNoFrom = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_OrderNoFrom" , "W_OrderNo");

	/**
	 * ControlID	lbl_Range
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range" , "W_Range");

	/**
	 * ControlID	txt_OrderNoTo
	 * TemplateKey	W_OrderNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_OrderNoTo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_OrderNoTo" , "W_OrderNo");

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
	 * ControlID	btn_WorkStart
	 * TemplateKey	W_Start
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_WorkStart = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_WorkStart" , "W_Start");

	/**
	 * ControlID	btn_AllCheck
	 * TemplateKey	W_AllCheck
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllCheck = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllCheck" , "W_AllCheck");

	/**
	 * ControlID	btn_AllCheckClear
	 * TemplateKey	W_AllCheckClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllCheckClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllCheckClear" , "W_AllCheckClear");

	/**
	 * ControlID	btn_ListClear
	 * TemplateKey	W_AllClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ListClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ListClear" , "W_AllClear");

	/**
	 * ControlID	chk_LIssueReport
	 * TemplateKey	W_IssueReport
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_LIssueReport = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_LIssueReport" , "W_IssueReport");

	/**
	 * ControlID	lst_RetrievalListStart
	 * TemplateKey	W_RetrievalListStart
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_RetrievalListStart = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_RetrievalListStart" , "W_RetrievalListStart");

}
//end of class

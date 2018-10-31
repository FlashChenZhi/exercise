// $Id: WorkerResultInquiry.java 7138 2010-02-18 07:55:09Z shibamoto $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.system.display.workerresultinquiry;
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
public class WorkerResultInquiry extends Page
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
	 * TemplateKey	W_InquiryPrint
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_InquiryPrint");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_WorkContents
	 * TemplateKey	W_WorkContents
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkContents = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkContents" , "W_WorkContents");

	/**
	 * ControlID	pul_FWorkContents
	 * TemplateKey	W_F_WorkContents
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_FWorkContents = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_FWorkContents" , "W_F_WorkContents");

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
	 * ControlID	lbl_Range
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range" , "W_Range");

	/**
	 * ControlID	txt_WorkDateTo
	 * TemplateKey	W_WorkDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_WorkDateTo = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_WorkDateTo" , "W_WorkDate");

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
	 * ControlID	lbl_UserName
	 * TemplateKey	W_UserName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserName" , "W_UserName");

	/**
	 * ControlID	txt_UserName
	 * TemplateKey	W_UserName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserName" , "W_UserName");

	/**
	 * ControlID	btn_PSearchUserName
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchUserName = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchUserName" , "W_P_Search");

	/**
	 * ControlID	lbl_GroupCondition
	 * TemplateKey	W_GroupCondition
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_GroupCondition = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_GroupCondition" , "W_GroupCondition");

	/**
	 * ControlID	rdo_DetailDisplay
	 * TemplateKey	W_GroupCondition_DetailDisplay
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_DetailDisplay = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_DetailDisplay" , "W_GroupCondition_DetailDisplay");

	/**
	 * ControlID	rdo_TotalDisplayDayUnit
	 * TemplateKey	W_GroupCondition_TotalDisplayDayUnit
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TotalDisplayDayUnit = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TotalDisplayDayUnit" , "W_GroupCondition_TotalDisplayDayUnit");

	/**
	 * ControlID	rdo_TotalDisplayInPeriod
	 * TemplateKey	W_GroupCondition_TotalDisplayInPeriod
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TotalDisplayInPeriod = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TotalDisplayInPeriod" , "W_GroupCondition_TotalDisplayInPeriod");

	/**
	 * ControlID	btn_Display
	 * TemplateKey	W_Display
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Display" , "W_Display");

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

	/**
	 * ControlID	pgr_U
	 * TemplateKey	Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_U = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_U" , "Pager");

	/**
	 * ControlID	lst_ResultByUserList
	 * TemplateKey	W_ResultByUserList
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ResultByUserList = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ResultByUserList" , "W_ResultByUserList");

	/**
	 * ControlID	pgr_D
	 * TemplateKey	Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_D = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_D" , "Pager");

}
//end of class

// $Id: PctOperationLog.java 3490 2009-03-16 05:24:59Z tanaka $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.system.display.pctoperationlog;
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
 * @version $Revision: 3490 $, $Date: 2009-03-16 14:24:59 +0900 (月, 16 3 2009) $
 * @author  $Author: tanaka $
 */
public class PctOperationLog extends Page
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
	 * ControlID	tab_OperationLog
	 * TemplateKey	P_OperationLog
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_OperationLog = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_OperationLog" , "P_OperationLog");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_RetrievalPeriod
	 * TemplateKey	T_RetrievalPeriod
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalPeriod = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalPeriod" , "T_RetrievalPeriod");

	/**
	 * ControlID	txt_StartSearchDate
	 * TemplateKey	W_SearchDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_StartSearchDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_StartSearchDate" , "W_SearchDate");

	/**
	 * ControlID	txt_StartSearchTime
	 * TemplateKey	W_SearchTime
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_StartSearchTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_StartSearchTime" , "W_SearchTime");

	/**
	 * ControlID	lbl_Hyphen
	 * TemplateKey	T_Hyphen
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen" , "T_Hyphen");

	/**
	 * ControlID	txt_EndSearchDate
	 * TemplateKey	W_SearchDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_EndSearchDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_EndSearchDate" , "W_SearchDate");

	/**
	 * ControlID	txt_EndSearchTime
	 * TemplateKey	W_SearchTime
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_EndSearchTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_EndSearchTime" , "W_SearchTime");

	/**
	 * ControlID	lbl_UserID
	 * TemplateKey	T_UserID
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserID = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserID" , "T_UserID");

	/**
	 * ControlID	txt_User
	 * TemplateKey	W_User
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_User = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_User" , "W_User");

	/**
	 * ControlID	lbl_DSNo
	 * TemplateKey	T_DSNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DSNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DSNo" , "T_DSNo");

	/**
	 * ControlID	txt_DSNo
	 * TemplateKey	W_DSNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_DSNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_DSNo" , "W_DSNo");

	/**
	 * ControlID	btn_P_Search
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search" , "W_P_Search");

	/**
	 * ControlID	txt_R_PageName
	 * TemplateKey	W_R_PageName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_PageName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_PageName" , "W_R_PageName");

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
	 * ControlID	pgr_U
	 * TemplateKey	Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_U = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_U" , "Pager");

	/**
	 * ControlID	lbl_RecordCount
	 * TemplateKey	T_RecordCount
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RecordCount = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RecordCount" , "T_RecordCount");

	/**
	 * ControlID	txt_RecordCount
	 * TemplateKey	W_RecordCount
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RecordCount = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RecordCount" , "W_RecordCount");

	/**
	 * ControlID	lst_ScreenControlLog
	 * TemplateKey	W_ScreenControlLog
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ScreenControlLog = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ScreenControlLog" , "W_ScreenControlLog");

	/**
	 * ControlID	pgr_D
	 * TemplateKey	Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_D = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_D" , "Pager");

}
//end of class

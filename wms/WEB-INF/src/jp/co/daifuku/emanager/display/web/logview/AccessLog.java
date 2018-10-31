// $Id: AccessLog.java 7765 2010-03-31 02:29:07Z shibamoto $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.logview;
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
 * @version $Revision: 7765 $, $Date: 2010-03-31 11:29:07 +0900 (水, 31 3 2010) $
 * @author  $Author: shibamoto $
 */
public class AccessLog extends Page
{

	// Class variables -----------------------------------------------

	/**
	 * ControlID	lbl_SettingName
	 * TemplateKey	T_In_SettingName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "T_In_SettingName");

	/**
	 * ControlID	btn_Help
	 * TemplateKey	T_Help
	 * ControlType	LinkButton
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "T_Help");

	/**
	 * ControlID	message
	 * TemplateKey	T_OperationMsg
	 * ControlType	Message
	 */
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "T_OperationMsg");

	/**
	 * ControlID	tab_AccessLog
	 * TemplateKey	T_AccessLog
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_AccessLog = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_AccessLog" , "T_AccessLog");

	/**
	 * ControlID	tab_MasterLog
	 * TemplateKey	T_MasterLog
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_MasterLog = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_MasterLog" , "T_MasterLog");

	/**
	 * ControlID	tab_InventoryLog
	 * TemplateKey	T_InventoryLog
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_InventoryLog = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_InventoryLog" , "T_InventoryLog");

	/**
	 * ControlID	tab_OperationLog
	 * TemplateKey	T_OperationLog
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_OperationLog = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_OperationLog" , "T_OperationLog");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	T_ToMenu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "T_ToMenu");

	/**
	 * ControlID	lbl_RetrievalPeriod
	 * TemplateKey	T_RetrievalPeriod
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalPeriod = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalPeriod" , "T_RetrievalPeriod");

	/**
	 * ControlID	txt_RetrievalBeginning
	 * TemplateKey	T_RetrievalBeginning
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_RetrievalBeginning = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_RetrievalBeginning" , "T_RetrievalBeginning");

	/**
	 * ControlID	txt_TimeRetrievalBeginning
	 * TemplateKey	T_RetrievalBeginning
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_TimeRetrievalBeginning = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_TimeRetrievalBeginning" , "T_RetrievalBeginning");

	/**
	 * ControlID	lbl_Hyphen
	 * TemplateKey	T_Hyphen
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen" , "T_Hyphen");

	/**
	 * ControlID	txt_RetrievalEnd
	 * TemplateKey	T_RetrievalEnd
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_RetrievalEnd = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_RetrievalEnd" , "T_RetrievalEnd");

	/**
	 * ControlID	txt_TimeRetrievalEnd
	 * TemplateKey	T_RetrievalEnd
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_TimeRetrievalEnd = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_TimeRetrievalEnd" , "T_RetrievalEnd");

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
	 * ControlID	lbl_UserId
	 * TemplateKey	T_UserId
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserId" , "T_UserId");

	/**
	 * ControlID	txt_UserId
	 * TemplateKey	T_UserId
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserId" , "T_UserId");

	/**
	 * ControlID	lbl_DSNo
	 * TemplateKey	T_DSNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DSNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DSNo" , "T_DSNo");

	/**
	 * ControlID	txt_DSNo
	 * TemplateKey	T_DsNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_DSNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_DSNo" , "T_DsNo");

	/**
	 * ControlID	btn_Search
	 * TemplateKey	T_SearchPopup
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Search" , "T_SearchPopup");

	/**
	 * ControlID	txt_R_PageName
	 * TemplateKey	T_R_PageName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_PageName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_PageName" , "T_R_PageName");

	/**
	 * ControlID	btn_View
	 * TemplateKey	T_P_View
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "T_P_View");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	T_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "T_Clear");

}
//end of class

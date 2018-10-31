// $Id: skelten.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.system.display.messageloginquiry;
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
public class MessageLogInquiry extends Page
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
	 * ControlID	tab_Inquiry
	 * TemplateKey	W_Inquiry
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Inquiry = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Inquiry" , "W_Inquiry");

	/**
	 * ControlID	btn_Menu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Menu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Menu" , "To_Menu");

	/**
	 * ControlID	lbl_SearchDate
	 * TemplateKey	W_SearchDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SearchDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SearchDate" , "W_SearchDate");

	/**
	 * ControlID	txt_FromSearchDate
	 * TemplateKey	W_SearchDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_FromSearchDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_FromSearchDate" , "W_SearchDate");

	/**
	 * ControlID	txt_FromSearchTime
	 * TemplateKey	W_SearchTime
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_FromSearchTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_FromSearchTime" , "W_SearchTime");

	/**
	 * ControlID	lbl_Range
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range" , "W_Range");

	/**
	 * ControlID	txt_ToSearchDate
	 * TemplateKey	W_SearchDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_ToSearchDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_ToSearchDate" , "W_SearchDate");

	/**
	 * ControlID	txt_ToSearchTime
	 * TemplateKey	W_SearchTime
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_ToSearchTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_ToSearchTime" , "W_SearchTime");

	/**
	 * ControlID	lbl_InputStyle
	 * TemplateKey	W_InputStyle
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InputStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InputStyle" , "W_InputStyle");

	/**
	 * ControlID	lbl_In_JavaSet
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_JavaSet = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_JavaSet" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_SearchCondition
	 * TemplateKey	W_SearchCondition
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SearchCondition = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SearchCondition" , "W_SearchCondition");

	/**
	 * ControlID	rdo_Log_All
	 * TemplateKey	W_Log_All
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Log_All = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Log_All" , "W_Log_All");

	/**
	 * ControlID	rdo_Log_Information
	 * TemplateKey	W_Log_Information
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Log_Information = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Log_Information" , "W_Log_Information");

	/**
	 * ControlID	rdo_Log_Caution
	 * TemplateKey	W_Log_Caution
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Log_Caution = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Log_Caution" , "W_Log_Caution");

	/**
	 * ControlID	rdo_Log_Warning
	 * TemplateKey	W_Log_Warning
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Log_Warning = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Log_Warning" , "W_Log_Warning");

	/**
	 * ControlID	rdo_Log_Error
	 * TemplateKey	W_Log_Error
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Log_Error = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Log_Error" , "W_Log_Error");

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
	 * ControlID	btn_Preview
	 * TemplateKey	W_Preview
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Preview = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Preview" , "W_Preview");

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
	 * ControlID	lst_Log
	 * TemplateKey	W_Log
	 * ControlType	ScrollListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_Log = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_Log" , "W_Log");

}
//end of class

// $Id: skelten.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.system.display.hostcommunicationinquiry;
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
public class HostCommunicationInquiry extends Page
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
	 * ControlID	tab
	 * TemplateKey	W_Inquiry
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Inquiry");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_Communication_Type
	 * TemplateKey	W_Communication_Type
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Communication_Type = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Communication_Type" , "W_Communication_Type");

	/**
	 * ControlID	rdo_Receive
	 * TemplateKey	W_Receive
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Receive = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Receive" , "W_Receive");

	/**
	 * ControlID	rdo_Send
	 * TemplateKey	W_Send
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Send = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Send" , "W_Send");

	/**
	 * ControlID	lbl_Data
	 * TemplateKey	W_CommunicationData
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Data = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Data" , "W_CommunicationData");

	/**
	 * ControlID	pul_CommunicationData
	 * TemplateKey	W_CommunicationData
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_CommunicationData = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_CommunicationData" , "W_CommunicationData");

	/**
	 * ControlID	lbl_DateRange
	 * TemplateKey	W_DateRange
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DateRange = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DateRange" , "W_DateRange");

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
	 * ControlID	txt_SearchDate
	 * TemplateKey	W_SearchDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_SearchDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_SearchDate" , "W_SearchDate");

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
	 * ControlID	lbl_JavaSet
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSet = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSet" , "W_In_JavaSet");

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
	 * ControlID	lst_HostCommunicationInquiry
	 * TemplateKey	W_HostCommunicationInquiry
	 * ControlType	ScrollListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_HostCommunicationInquiry = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_HostCommunicationInquiry" , "W_HostCommunicationInquiry");

}
//end of class

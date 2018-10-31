// $Id: AsOperationCycle.java 7138 2010-02-18 07:55:09Z shibamoto $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.operationcycle;
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
public class AsOperationCycle extends Page
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
	 * ControlID	lbl_Area
	 * TemplateKey	W_Area
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Area = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Area" , "W_Area");

	/**
	 * ControlID	pul_Area
	 * TemplateKey	W_Area
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Area = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Area" , "W_Area");

	/**
	 * ControlID	lbl_SearchDate
	 * TemplateKey	W_SearchDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SearchDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SearchDate" , "W_SearchDate");

	/**
	 * ControlID	txt_SearchDate
	 * TemplateKey	W_SearchDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_SearchDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_SearchDate" , "W_SearchDate");

	/**
	 * ControlID	txt_SearchTime
	 * TemplateKey	W_SearchTime
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_SearchTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_SearchTime" , "W_SearchTime");

	/**
	 * ControlID	lbl_Range1
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range1" , "W_Range");

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
	 * ControlID	lbl_DateStyle
	 * TemplateKey	W_InputStyleDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DateStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DateStyle" , "W_InputStyleDate");

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
	 * ControlID	txt_InSearchDate
	 * TemplateKey	AST_InSearchDate
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_InSearchDate = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_InSearchDate" , "AST_InSearchDate");

	/**
	 * ControlID	lbl_Range2
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range2" , "W_Range");

	/**
	 * ControlID	txt_InToSearchDate
	 * TemplateKey	AST_InToSearchDate
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_InToSearchDate = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_InToSearchDate" , "AST_InToSearchDate");

	/**
	 * ControlID	lst_ASRSOperationResult
	 * TemplateKey	W_ASRSOperationResult
	 * ControlType	ScrollListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_ASRSOperationResult = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_ASRSOperationResult" , "W_ASRSOperationResult");

}
//end of class

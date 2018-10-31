// $Id: InventoryMovement.java 681 2008-10-24 12:24:01Z nakai $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.analysis.display.inventorymovement;
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
 * @version $Revision: 681 $, $Date: 2008-10-24 21:24:01 +0900 (金, 24 10 2008) $
 * @author  $Author: nakai $
 */
public class InventoryMovement extends Page
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
	 * TemplateKey	W_Moving
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Moving");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	lbl_ItemCodeRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCodeRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCodeRequire" , "W_Require");

	/**
	 * ControlID	txt_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	btn_ItemSearch
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ItemSearch = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ItemSearch" , "W_P_Search");

	/**
	 * ControlID	lbl_SearchCondition
	 * TemplateKey	W_SearchCondition
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SearchCondition = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SearchCondition" , "W_SearchCondition");

	/**
	 * ControlID	pul_SearchCond
	 * TemplateKey	W_SearchCond
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_SearchCond = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_SearchCond" , "W_SearchCond");

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
	 * ControlID	lbl_AnalysisUnit
	 * TemplateKey	W_AnalysisUnit
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AnalysisUnit = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AnalysisUnit" , "W_AnalysisUnit");

	/**
	 * ControlID	rdo_UnitMonth
	 * TemplateKey	W_UnitMonth
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_UnitMonth = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_UnitMonth" , "W_UnitMonth");

	/**
	 * ControlID	rdo_UnitDay
	 * TemplateKey	W_UnitDay
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_UnitDay = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_UnitDay" , "W_UnitDay");

	/**
	 * ControlID	lbl_TermYearMonth
	 * TemplateKey	W_TermYearMonth
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TermYearMonth = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TermYearMonth" , "W_TermYearMonth");

	/**
	 * ControlID	pul_TermYearMonth
	 * TemplateKey	W_TermYearMonth
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_TermYearMonth = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_TermYearMonth" , "W_TermYearMonth");

	/**
	 * ControlID	btn_View
	 * TemplateKey	W_Display
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "W_Display");

	/**
	 * ControlID	btn_ExcelOutput
	 * TemplateKey	W_XLS
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ExcelOutput = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ExcelOutput" , "W_XLS");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

	/**
	 * ControlID	vbc_Chart
	 * TemplateKey	W_InventMove
	 * ControlType	VerticalBarLineChart
	 */
	public jp.co.daifuku.bluedog.ui.control.VerticalBarLineChart vbc_Chart = jp.co.daifuku.bluedog.ui.control.VerticalBarLineChartFactory.getInstance("vbc_Chart" , "W_InventMove");

}
//end of class

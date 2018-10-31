// $Id: PCTUserResultInquiry.java 4437 2009-06-15 03:09:55Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.retrieval.display.pctuserresultinquiry;
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
 * @version $Revision: 4437 $, $Date: 2009-06-15 12:09:55 +0900 (月, 15 6 2009) $
 * @author  $Author: okayama $
 */
public class PCTUserResultInquiry extends Page
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
	 * TemplateKey	W_Inquiry2
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Inquiry2");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_WorkDay
	 * TemplateKey	W_WorkDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkDay = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkDay" , "W_WorkDate");

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
	 * ControlID	txt_ToWorkDate
	 * TemplateKey	W_WorkDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_ToWorkDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_ToWorkDate" , "W_WorkDate");

	/**
	 * ControlID	lbl_DateRange
	 * TemplateKey	W_SelectWeekDay
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DateRange = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DateRange" , "W_SelectWeekDay");

	/**
	 * ControlID	rdo_DateSelect_All
	 * TemplateKey	P_DateSelect_All
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_DateSelect_All = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_DateSelect_All" , "P_DateSelect_All");

	/**
	 * ControlID	rdo_DateSelect_Assignation
	 * TemplateKey	P_DateSelect_Assignation
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_DateSelect_Assignation = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_DateSelect_Assignation" , "P_DateSelect_Assignation");

	/**
	 * ControlID	lbl_WeekDay
	 * TemplateKey	W_WeekDay
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WeekDay = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WeekDay" , "W_WeekDay");

	/**
	 * ControlID	chk_Monday
	 * TemplateKey	P_Monday
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Monday = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Monday" , "P_Monday");

	/**
	 * ControlID	chk_Tuesday
	 * TemplateKey	P_Tuesday
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Tuesday = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Tuesday" , "P_Tuesday");

	/**
	 * ControlID	chk_Wednesday
	 * TemplateKey	P_Wednesday
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Wednesday = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Wednesday" , "P_Wednesday");

	/**
	 * ControlID	chk_Thursday
	 * TemplateKey	P_Thursday
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Thursday = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Thursday" , "P_Thursday");

	/**
	 * ControlID	chk_Friday
	 * TemplateKey	P_Friday
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Friday = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Friday" , "P_Friday");

	/**
	 * ControlID	chk_Saturday
	 * TemplateKey	P_Saturday
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Saturday = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Saturday" , "P_Saturday");

	/**
	 * ControlID	chk_Sunday
	 * TemplateKey	P_Sunday
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Sunday = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Sunday" , "P_Sunday");

	/**
	 * ControlID	btn_DateSelectWeekday
	 * TemplateKey	W_BesidesWeekend
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_DateSelectWeekday = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_DateSelectWeekday" , "W_BesidesWeekend");

	/**
	 * ControlID	btn_DateSelectClear
	 * TemplateKey	W_ClearDay
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_DateSelectClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_DateSelectClear" , "W_ClearDay");

	/**
	 * ControlID	lbl_ConsignorCode
	 * TemplateKey	W_ConsignorCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");

	/**
	 * ControlID	txt_ConsignorCode
	 * TemplateKey	W_ConsignorCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");

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
	 * ControlID	lbl_BatchNo
	 * TemplateKey	W_BatchNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BatchNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BatchNo" , "W_BatchNo");

	/**
	 * ControlID	txt_BatchNo
	 * TemplateKey	W_BatchNoPCT
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BatchNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BatchNo" , "W_BatchNoPCT");

	/**
	 * ControlID	lbl_Level
	 * TemplateKey	W_PCTLeval
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Level = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Level" , "W_PCTLeval");

	/**
	 * ControlID	chk_levelA
	 * TemplateKey	P_levelA
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_levelA = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_levelA" , "P_levelA");

	/**
	 * ControlID	chk_levelB
	 * TemplateKey	P_levelB
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_levelB = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_levelB" , "P_levelB");

	/**
	 * ControlID	chk_levelC
	 * TemplateKey	P_levelC
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_levelC = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_levelC" , "P_levelC");

	/**
	 * ControlID	lbl_CollectUnit
	 * TemplateKey	W_CollectUnit
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CollectUnit = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CollectUnit" , "W_CollectUnit");

	/**
	 * ControlID	rdo_CollectCondition_Worker
	 * TemplateKey	P_CollectCondition_Worker
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CollectCondition_Worker = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CollectCondition_Worker" , "P_CollectCondition_Worker");

	/**
	 * ControlID	rdo_CollectCondition_WorkDate
	 * TemplateKey	P_CollectCondition_WorkDate
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CollectCondition_WorkDate = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CollectCondition_WorkDate" , "P_CollectCondition_WorkDate");

	/**
	 * ControlID	rdo_CollectCondition_Consignor
	 * TemplateKey	P_CollectCondition_Consignor
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CollectCondition_Consignor = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CollectCondition_Consignor" , "P_CollectCondition_Consignor");

	/**
	 * ControlID	rdo_CollectCondition_Area
	 * TemplateKey	P_CollectCondition_Area
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CollectCondition_Area = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CollectCondition_Area" , "P_CollectCondition_Area");

	/**
	 * ControlID	rdo_CollectCondition_Batch
	 * TemplateKey	P_CollectCondition_Batch
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CollectCondition_Batch = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CollectCondition_Batch" , "P_CollectCondition_Batch");

	/**
	 * ControlID	lbl_DispRank
	 * TemplateKey	W_DispRank
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DispRank = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DispRank" , "W_DispRank");

	/**
	 * ControlID	rdo_DisplayRank_Lot
	 * TemplateKey	P_DisplayRank_Lot
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_DisplayRank_Lot = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_DisplayRank_Lot" , "P_DisplayRank_Lot");

	/**
	 * ControlID	rdo_DisplayRank_Order
	 * TemplateKey	P_DisplayRank_Order
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_DisplayRank_Order = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_DisplayRank_Order" , "P_DisplayRank_Order");

	/**
	 * ControlID	rdo_DisplayRank_Line
	 * TemplateKey	P_DisplayRank_Line
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_DisplayRank_Line = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_DisplayRank_Line" , "P_DisplayRank_Line");

	/**
	 * ControlID	slb_Download
	 * TemplateKey	ANA_Download
	 * ControlType	SubmitLabel
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitLabel slb_Download = jp.co.daifuku.bluedog.ui.control.SubmitLabelFactory.getInstance("slb_Download" , "ANA_Download");

	/**
	 * ControlID	btn_Display
	 * TemplateKey	W_P_Display
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Display" , "W_P_Display");

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

}
//end of class

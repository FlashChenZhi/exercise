// $Id: PCTAllProgress.java 3541 2009-03-16 11:56:57Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.retrieval.display.pctallprogress;
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
 * @version $Revision: 3541 $, $Date: 2009-03-16 20:56:57 +0900 (月, 16 3 2009) $
 * @author  $Author: okayama $
 */
public class PCTAllProgress extends Page
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
	 * TemplateKey	W_Progress
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Progress");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_ConsignorCode
	 * TemplateKey	W_ConsignorCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");

	/**
	 * ControlID	lbl_Require
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Require = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Require" , "W_Require");

	/**
	 * ControlID	txt_ConsignorCode
	 * TemplateKey	W_ConsignorCodePCT
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCodePCT");

	/**
	 * ControlID	txt_ConsignorName
	 * TemplateKey	T_R_FunctionName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorName" , "T_R_FunctionName");

	/**
	 * ControlID	lbl_Area
	 * TemplateKey	W_Area
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Area = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Area" , "W_Area");

	/**
	 * ControlID	pul_AreaNo
	 * TemplateKey	W_RetrievalArea
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_AreaNo = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_AreaNo" , "W_RetrievalArea");

	/**
	 * ControlID	lbl_Display
	 * TemplateKey	W_Display
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Display = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Display" , "W_Display");

	/**
	 * ControlID	rdo_Auto
	 * TemplateKey	W_ProgressDisplay_Auto
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Auto = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Auto" , "W_ProgressDisplay_Auto");

	/**
	 * ControlID	rdo_Manual
	 * TemplateKey	W_ProgressDisplay_Manual
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Manual = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Manual" , "W_ProgressDisplay_Manual");

	/**
	 * ControlID	lbl_Group
	 * TemplateKey	W_Group
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Group = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Group" , "W_Group");

	/**
	 * ControlID	rdo_BatchNoUnit
	 * TemplateKey	W_Group_PlanDateBatchSeqNoUnit
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_BatchNoUnit = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_BatchNoUnit" , "W_Group_PlanDateBatchSeqNoUnit");

	/**
	 * ControlID	rdo_PlanDateUnit
	 * TemplateKey	W_Group_PlanDateUnit
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PlanDateUnit = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PlanDateUnit" , "W_Group_PlanDateUnit");

	/**
	 * ControlID	rdo_AllPlanDate
	 * TemplateKey	W_Group_AllPlanDate
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AllPlanDate = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AllPlanDate" , "W_Group_AllPlanDate");

	/**
	 * ControlID	lbl_EndPlanTime
	 * TemplateKey	W_EndPlanTime
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EndPlanTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EndPlanTime" , "W_EndPlanTime");

	/**
	 * ControlID	rdo_LotStandard
	 * TemplateKey	W_EndPlanTime_LotStandard
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_LotStandard = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_LotStandard" , "W_EndPlanTime_LotStandard");

	/**
	 * ControlID	rdo_OrderStandard
	 * TemplateKey	W_EndPlanTime_OrderStandard
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_OrderStandard = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_OrderStandard" , "W_EndPlanTime_OrderStandard");

	/**
	 * ControlID	rdo_LineStandard
	 * TemplateKey	W_EndPlanTime_LineStandard
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_LineStandard = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_LineStandard" , "W_EndPlanTime_LineStandard");

	/**
	 * ControlID	lst_PCTAllProgress1
	 * TemplateKey	W_PCTAllProgress
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_PCTAllProgress1 = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_PCTAllProgress1" , "W_PCTAllProgress");

	/**
	 * ControlID	hbc_PCTAllTask1
	 * TemplateKey	W_TaskProgress
	 * ControlType	HorizontalBarChart
	 */
	public jp.co.daifuku.bluedog.ui.control.HorizontalBarChart hbc_PCTAllTask1 = jp.co.daifuku.bluedog.ui.control.HorizontalBarChartFactory.getInstance("hbc_PCTAllTask1" , "W_TaskProgress");

	/**
	 * ControlID	txt_ProgressRate1
	 * TemplateKey	W_ProgressRate
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ProgressRate1 = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ProgressRate1" , "W_ProgressRate");

	/**
	 * ControlID	lbl_Percent1
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Percent1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Percent1" , "W_Percent");

	/**
	 * ControlID	lst_PCTAllProgress2
	 * TemplateKey	W_PCTAllProgress
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_PCTAllProgress2 = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_PCTAllProgress2" , "W_PCTAllProgress");

	/**
	 * ControlID	hbc_PCTAllTask2
	 * TemplateKey	W_TaskProgress
	 * ControlType	HorizontalBarChart
	 */
	public jp.co.daifuku.bluedog.ui.control.HorizontalBarChart hbc_PCTAllTask2 = jp.co.daifuku.bluedog.ui.control.HorizontalBarChartFactory.getInstance("hbc_PCTAllTask2" , "W_TaskProgress");

	/**
	 * ControlID	txt_ProgressRate2
	 * TemplateKey	W_ProgressRate
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ProgressRate2 = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ProgressRate2" , "W_ProgressRate");

	/**
	 * ControlID	lbl_Percent2
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Percent2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Percent2" , "W_Percent");

	/**
	 * ControlID	lst_PCTAllProgress3
	 * TemplateKey	W_PCTAllProgress
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_PCTAllProgress3 = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_PCTAllProgress3" , "W_PCTAllProgress");

	/**
	 * ControlID	hbc_PCTAllTask3
	 * TemplateKey	W_TaskProgress
	 * ControlType	HorizontalBarChart
	 */
	public jp.co.daifuku.bluedog.ui.control.HorizontalBarChart hbc_PCTAllTask3 = jp.co.daifuku.bluedog.ui.control.HorizontalBarChartFactory.getInstance("hbc_PCTAllTask3" , "W_TaskProgress");

	/**
	 * ControlID	txt_ProgressRate3
	 * TemplateKey	W_ProgressRate
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ProgressRate3 = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ProgressRate3" , "W_ProgressRate");

	/**
	 * ControlID	lbl_Percent3
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Percent3 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Percent3" , "W_Percent");

	/**
	 * ControlID	lst_PCTAllProgress4
	 * TemplateKey	W_PCTAllProgress
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_PCTAllProgress4 = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_PCTAllProgress4" , "W_PCTAllProgress");

	/**
	 * ControlID	hbc_PCTAllTask4
	 * TemplateKey	W_TaskProgress
	 * ControlType	HorizontalBarChart
	 */
	public jp.co.daifuku.bluedog.ui.control.HorizontalBarChart hbc_PCTAllTask4 = jp.co.daifuku.bluedog.ui.control.HorizontalBarChartFactory.getInstance("hbc_PCTAllTask4" , "W_TaskProgress");

	/**
	 * ControlID	txt_ProgressRate4
	 * TemplateKey	W_ProgressRate
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ProgressRate4 = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ProgressRate4" , "W_ProgressRate");

	/**
	 * ControlID	lbl_Percent4
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Percent4 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Percent4" , "W_Percent");

	/**
	 * ControlID	btn_Display
	 * TemplateKey	W_Display
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Display" , "W_Display");

	/**
	 * ControlID	btn_PrevPage
	 * TemplateKey	W_PrevPage
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PrevPage = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PrevPage" , "W_PrevPage");

	/**
	 * ControlID	btn_NextPage
	 * TemplateKey	W_NextPage
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_NextPage = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_NextPage" , "W_NextPage");

}
//end of class

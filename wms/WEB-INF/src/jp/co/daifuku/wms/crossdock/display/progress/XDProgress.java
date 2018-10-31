// $Id: XDProgress.java 929 2008-10-30 04:04:32Z tanaka $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.crossdock.display.progress;
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
 * @version $Revision: 929 $, $Date: 2008-10-30 13:04:32 +0900 (木, 30 10 2008) $
 * @author  $Author: tanaka $
 */
public class XDProgress extends Page
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
	 * ControlID	tab_Progress
	 * TemplateKey	W_Progress
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Progress = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Progress" , "W_Progress");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

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
	 * ControlID	rdo_PlanDateBatchNoUnit
	 * TemplateKey	W_Group_PlanDateBatchNoUnit
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PlanDateBatchNoUnit = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PlanDateBatchNoUnit" , "W_Group_PlanDateBatchNoUnit");

	/**
	 * ControlID	rdo_PlanDateUnit
	 * TemplateKey	W_Group_PlanDateUnit
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PlanDateUnit = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PlanDateUnit" , "W_Group_PlanDateUnit");

	/**
	 * ControlID	lst_TcPlanProgress
	 * TemplateKey	W_TcPlanProgress
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_TcPlanProgress = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_TcPlanProgress" , "W_TcPlanProgress");

	/**
	 * ControlID	lbl_Receiving
	 * TemplateKey	W_Receiving
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Receiving = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Receiving" , "W_Receiving");

	/**
	 * ControlID	hbc_TaskProgressReceiving
	 * TemplateKey	W_TaskProgress
	 * ControlType	HorizontalBarChart
	 */
	public jp.co.daifuku.bluedog.ui.control.HorizontalBarChart hbc_TaskProgressReceiving = jp.co.daifuku.bluedog.ui.control.HorizontalBarChartFactory.getInstance("hbc_TaskProgressReceiving" , "W_TaskProgress");

	/**
	 * ControlID	txt_ProgressRateReceiving
	 * TemplateKey	W_ProgressRate
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ProgressRateReceiving = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ProgressRateReceiving" , "W_ProgressRate");

	/**
	 * ControlID	lbl_PercentReceiving
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PercentReceiving = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PercentReceiving" , "W_Percent");

	/**
	 * ControlID	lbl_Sort
	 * TemplateKey	W_Sort
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Sort = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Sort" , "W_Sort");

	/**
	 * ControlID	hbc_TaskProgressSort
	 * TemplateKey	W_TaskProgress
	 * ControlType	HorizontalBarChart
	 */
	public jp.co.daifuku.bluedog.ui.control.HorizontalBarChart hbc_TaskProgressSort = jp.co.daifuku.bluedog.ui.control.HorizontalBarChartFactory.getInstance("hbc_TaskProgressSort" , "W_TaskProgress");

	/**
	 * ControlID	txt_ProgressRateSort
	 * TemplateKey	W_ProgressRate
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ProgressRateSort = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ProgressRateSort" , "W_ProgressRate");

	/**
	 * ControlID	lbl_PercentSort
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PercentSort = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PercentSort" , "W_Percent");

	/**
	 * ControlID	lst_TcPlanProgress2
	 * TemplateKey	W_TcPlanProgress
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_TcPlanProgress2 = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_TcPlanProgress2" , "W_TcPlanProgress");

	/**
	 * ControlID	lbl_Receiving2
	 * TemplateKey	W_Receiving
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Receiving2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Receiving2" , "W_Receiving");

	/**
	 * ControlID	hbc_TaskProgressReceiving2
	 * TemplateKey	W_TaskProgress
	 * ControlType	HorizontalBarChart
	 */
	public jp.co.daifuku.bluedog.ui.control.HorizontalBarChart hbc_TaskProgressReceiving2 = jp.co.daifuku.bluedog.ui.control.HorizontalBarChartFactory.getInstance("hbc_TaskProgressReceiving2" , "W_TaskProgress");

	/**
	 * ControlID	txt_ProgressRateReceiving2
	 * TemplateKey	W_ProgressRate
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ProgressRateReceiving2 = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ProgressRateReceiving2" , "W_ProgressRate");

	/**
	 * ControlID	lbl_PercentReceiving2
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PercentReceiving2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PercentReceiving2" , "W_Percent");

	/**
	 * ControlID	lbl_Sort2
	 * TemplateKey	W_Sort
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Sort2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Sort2" , "W_Sort");

	/**
	 * ControlID	hbc_TaskProgressSort2
	 * TemplateKey	W_TaskProgress
	 * ControlType	HorizontalBarChart
	 */
	public jp.co.daifuku.bluedog.ui.control.HorizontalBarChart hbc_TaskProgressSort2 = jp.co.daifuku.bluedog.ui.control.HorizontalBarChartFactory.getInstance("hbc_TaskProgressSort2" , "W_TaskProgress");

	/**
	 * ControlID	txt_ProgressRateSort2
	 * TemplateKey	W_ProgressRate
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ProgressRateSort2 = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ProgressRateSort2" , "W_ProgressRate");

	/**
	 * ControlID	lbl_PercentSort2
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PercentSort2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PercentSort2" , "W_Percent");

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

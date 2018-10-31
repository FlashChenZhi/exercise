// $Id: RetrievalProgress.java 932 2008-10-30 04:06:14Z tanaka $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.display.progress;
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
 * @version $Revision: 932 $, $Date: 2008-10-30 13:06:14 +0900 (木, 30 10 2008) $
 * @author  $Author: tanaka $
 */
public class RetrievalProgress extends Page
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
	 * TemplateKey	W_Group_PlanDateBatchNoUnit
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_BatchNoUnit = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_BatchNoUnit" , "W_Group_PlanDateBatchNoUnit");

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
	 * ControlID	lst_RetrievalProgress1
	 * TemplateKey	W_RetrievalProgress
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_RetrievalProgress1 = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_RetrievalProgress1" , "W_RetrievalProgress");

	/**
	 * ControlID	hbc_RetrievalTask1
	 * TemplateKey	W_TaskProgress
	 * ControlType	HorizontalBarChart
	 */
	public jp.co.daifuku.bluedog.ui.control.HorizontalBarChart hbc_RetrievalTask1 = jp.co.daifuku.bluedog.ui.control.HorizontalBarChartFactory.getInstance("hbc_RetrievalTask1" , "W_TaskProgress");

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
	 * ControlID	lst_RetrievalProgress2
	 * TemplateKey	W_RetrievalProgress
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_RetrievalProgress2 = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_RetrievalProgress2" , "W_RetrievalProgress");

	/**
	 * ControlID	hbc_RetrievalTask2
	 * TemplateKey	W_TaskProgress
	 * ControlType	HorizontalBarChart
	 */
	public jp.co.daifuku.bluedog.ui.control.HorizontalBarChart hbc_RetrievalTask2 = jp.co.daifuku.bluedog.ui.control.HorizontalBarChartFactory.getInstance("hbc_RetrievalTask2" , "W_TaskProgress");

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

// $Id: WorkingTimeSimu.java 7549 2010-03-15 00:52:40Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.analysis.display.workingtimesimu;
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
 * @version $Revision: 7549 $, $Date: 2010-03-15 09:52:40 +0900 (月, 15 3 2010) $
 * @author  $Author: okayama $
 */
public class WorkingTimeSimu extends Page
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
	 * TemplateKey	W_WorkSimu
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_WorkSimu");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_SearchObject
	 * TemplateKey	W_SearchObject
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SearchObject = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SearchObject" , "W_SearchObject");

	/**
	 * ControlID	rdo_SearchPlanDateAll
	 * TemplateKey	W_SearchPlanDateAll
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SearchPlanDateAll = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SearchPlanDateAll" , "W_SearchPlanDateAll");

	/**
	 * ControlID	rdo_SearchPlanDateInput
	 * TemplateKey	W_SearchPlanDateInput
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SearchPlanDateInput = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SearchPlanDateInput" , "W_SearchPlanDateInput");

	/**
	 * ControlID	lbl_WorkPlanDate
	 * TemplateKey	W_WorkPlanDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkPlanDate" , "W_WorkPlanDate");

	/**
	 * ControlID	txt_WorkPlanDate
	 * TemplateKey	W_PlanDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_WorkPlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_WorkPlanDate" , "W_PlanDate");

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
	 * ControlID	chk_BeforePlanDate
	 * TemplateKey	W_BeforePlanDate
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_BeforePlanDate = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_BeforePlanDate" , "W_BeforePlanDate");

	/**
	 * ControlID	chk_AfterPlanDate
	 * TemplateKey	W_AfterPlanDate
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_AfterPlanDate = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_AfterPlanDate" , "W_AfterPlanDate");

	/**
	 * ControlID	btn_Select
	 * TemplateKey	W_Display
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Select = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Select" , "W_Display");

	/**
	 * ControlID	lbl_WorkerNum
	 * TemplateKey	W_WorkerNum
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerNum = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerNum" , "W_WorkerNum");

	/**
	 * ControlID	lbl_WorkerNumRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkerNumRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkerNumRequire" , "W_Require");

	/**
	 * ControlID	lbl_WorkStartTime
	 * TemplateKey	W_WorkStartTime
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkStartTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkStartTime" , "W_WorkStartTime");

	/**
	 * ControlID	lbl_WorkStartTimeRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkStartTimeRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkStartTimeRequire" , "W_Require");

	/**
	 * ControlID	lbl_PlanItemQty
	 * TemplateKey	W_PlanItemQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PlanItemQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PlanItemQty" , "W_PlanItemQty");

	/**
	 * ControlID	lbl_PlanPieceQty
	 * TemplateKey	W_PlanTotalPieceQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PlanPieceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PlanPieceQty" , "W_PlanTotalPieceQty");

	/**
	 * ControlID	lbl_InputItemQty
	 * TemplateKey	W_InputItemQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InputItemQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InputItemQty" , "W_InputItemQty");

	/**
	 * ControlID	lbl_InputPieceQty
	 * TemplateKey	W_InputPieceQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InputPieceQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InputPieceQty" , "W_InputPieceQty");

	/**
	 * ControlID	lbl_WorkingTime
	 * TemplateKey	W_WorkingTime
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkingTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkingTime" , "W_WorkingTime");

	/**
	 * ControlID	lbl_WorkEndTime
	 * TemplateKey	W_WorkEndTime
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkEndTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkEndTime" , "W_WorkEndTime");

	/**
	 * ControlID	lbl_StorageWork
	 * TemplateKey	W_StorageWork
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageWork = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageWork" , "W_StorageWork");

	/**
	 * ControlID	txt_StorageWorkerNum
	 * TemplateKey	W_WorkerNum
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StorageWorkerNum = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StorageWorkerNum" , "W_WorkerNum");

	/**
	 * ControlID	lbl_StorageUnitPerson
	 * TemplateKey	W_UnitPerson
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageUnitPerson = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageUnitPerson" , "W_UnitPerson");

	/**
	 * ControlID	txt_StorageWorkStartTime
	 * TemplateKey	W_WorkStartTime
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_StorageWorkStartTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_StorageWorkStartTime" , "W_WorkStartTime");

	/**
	 * ControlID	txt_StorageItemQtyPlan
	 * TemplateKey	W_R_ItemQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StorageItemQtyPlan = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StorageItemQtyPlan" , "W_R_ItemQty");

	/**
	 * ControlID	txt_StoragePieceQtyPlan
	 * TemplateKey	W_R_PieceQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StoragePieceQtyPlan = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StoragePieceQtyPlan" , "W_R_PieceQty");

	/**
	 * ControlID	txt_StorageItemQtyInp
	 * TemplateKey	W_ItemQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StorageItemQtyInp = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StorageItemQtyInp" , "W_ItemQty");

	/**
	 * ControlID	txt_StoragePieceQtyInp
	 * TemplateKey	W_PieceQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StoragePieceQtyInp = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StoragePieceQtyInp" , "W_PieceQty");

	/**
	 * ControlID	txt_StorageWorkingTime
	 * TemplateKey	W_WorkingTime
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StorageWorkingTime = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StorageWorkingTime" , "W_WorkingTime");

	/**
	 * ControlID	lbl_StorageUnitMinute
	 * TemplateKey	W_UnitMinute
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageUnitMinute = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageUnitMinute" , "W_UnitMinute");

	/**
	 * ControlID	txt_StorageWorkEndTime
	 * TemplateKey	W_WorkEndTime
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_StorageWorkEndTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_StorageWorkEndTime" , "W_WorkEndTime");

	/**
	 * ControlID	lbl_RetrievalWork
	 * TemplateKey	W_RetrievalWork
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalWork = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalWork" , "W_RetrievalWork");

	/**
	 * ControlID	txt_RetrievalWorkerNum
	 * TemplateKey	W_WorkerNum
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RetrievalWorkerNum = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RetrievalWorkerNum" , "W_WorkerNum");

	/**
	 * ControlID	lbl_RetrievalUnitPerson
	 * TemplateKey	W_UnitPerson
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalUnitPerson = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalUnitPerson" , "W_UnitPerson");

	/**
	 * ControlID	txt_RetrievalWorkStartTime
	 * TemplateKey	W_WorkStartTime
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_RetrievalWorkStartTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_RetrievalWorkStartTime" , "W_WorkStartTime");

	/**
	 * ControlID	txt_RetrievalItemQtyPlan
	 * TemplateKey	W_R_ItemQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RetrievalItemQtyPlan = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RetrievalItemQtyPlan" , "W_R_ItemQty");

	/**
	 * ControlID	txt_RetrievalPieceQtyPlan
	 * TemplateKey	W_R_PieceQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RetrievalPieceQtyPlan = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RetrievalPieceQtyPlan" , "W_R_PieceQty");

	/**
	 * ControlID	txt_RetrievalItemQtyInp
	 * TemplateKey	W_ItemQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RetrievalItemQtyInp = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RetrievalItemQtyInp" , "W_ItemQty");

	/**
	 * ControlID	txt_RetrievalPieceQtyInp
	 * TemplateKey	W_PieceQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RetrievalPieceQtyInp = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RetrievalPieceQtyInp" , "W_PieceQty");

	/**
	 * ControlID	txt_RetrievalWorkingTime
	 * TemplateKey	W_WorkingTime
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RetrievalWorkingTime = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RetrievalWorkingTime" , "W_WorkingTime");

	/**
	 * ControlID	lbl_RetrievalUnitMinute
	 * TemplateKey	W_UnitMinute
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalUnitMinute = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalUnitMinute" , "W_UnitMinute");

	/**
	 * ControlID	txt_RetrievalWorkEndTime
	 * TemplateKey	W_WorkEndTime
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_RetrievalWorkEndTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_RetrievalWorkEndTime" , "W_WorkEndTime");

	/**
	 * ControlID	btn_Simulate
	 * TemplateKey	W_Simulate
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Simulate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Simulate" , "W_Simulate");

}
//end of class

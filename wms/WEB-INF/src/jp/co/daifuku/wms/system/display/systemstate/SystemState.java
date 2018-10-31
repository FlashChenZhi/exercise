// $Id: SystemState.java 5899 2009-11-16 05:14:53Z fukuwa $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.system.display.systemstate;
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
 * @version $Revision: 5899 $, $Date: 2009-11-16 14:14:53 +0900 (月, 16 11 2009) $
 * @author  $Author: fukuwa $
 */
public class SystemState extends Page
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
	 * TemplateKey	W_Set
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Set");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_DailyProcess
	 * TemplateKey	W_DailyProcess
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DailyProcess = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DailyProcess" , "W_DailyProcess");

	/**
	 * ControlID	txt_InDailyProcess
	 * TemplateKey	W_InProcessState
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_InDailyProcess = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_InDailyProcess" , "W_InProcessState");

	/**
	 * ControlID	lbl_LoadPlanData
	 * TemplateKey	W_LoadPlanData
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LoadPlanData = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LoadPlanData" , "W_LoadPlanData");

	/**
	 * ControlID	txt_InLoadPlanData
	 * TemplateKey	W_InProcessState
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_InLoadPlanData = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_InLoadPlanData" , "W_InProcessState");

	/**
	 * ControlID	lbl_CreateReportData
	 * TemplateKey	W_CreateReportData
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CreateReportData = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CreateReportData" , "W_CreateReportData");

	/**
	 * ControlID	txt_InCreateReportData
	 * TemplateKey	W_InProcessState
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_InCreateReportData = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_InCreateReportData" , "W_InProcessState");

	/**
	 * ControlID	lbl_RetrievalAllocate
	 * TemplateKey	W_RetrievalAllocate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalAllocate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalAllocate" , "W_RetrievalAllocate");

	/**
	 * ControlID	txt_InRetrievalAllocate
	 * TemplateKey	W_R_SystemMnt
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_InRetrievalAllocate = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_InRetrievalAllocate" , "W_R_SystemMnt");

	/**
	 * ControlID	lbl_AllocationClear
	 * TemplateKey	W_AllocationClear
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AllocationClear = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AllocationClear" , "W_AllocationClear");

	/**
	 * ControlID	txt_InAllocationClear
	 * TemplateKey	W_R_SystemMnt
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_InAllocationClear = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_InAllocationClear" , "W_R_SystemMnt");

	/**
	 * ControlID	lbl_HostCommunication
	 * TemplateKey	W_HostCommunication
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_HostCommunication = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_HostCommunication" , "W_HostCommunication");

	/**
	 * ControlID	txt_InHostCommunication
	 * TemplateKey	W_R_HostCommunication
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_InHostCommunication = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_InHostCommunication" , "W_R_HostCommunication");

	/**
	 * ControlID	lbl_PlanDataHoldDays
	 * TemplateKey	W_PlanDataHoldDays
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PlanDataHoldDays = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PlanDataHoldDays" , "W_PlanDataHoldDays");

	/**
	 * ControlID	txt_InPlanDataHoldDays
	 * TemplateKey	W_InPlanDataHoldDays
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_InPlanDataHoldDays = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_InPlanDataHoldDays" , "W_InPlanDataHoldDays");

	/**
	 * ControlID	lbl_ResultDataHoldDays
	 * TemplateKey	W_ResultDataHoldDays
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ResultDataHoldDays = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ResultDataHoldDays" , "W_ResultDataHoldDays");

	/**
	 * ControlID	txt_InResultDataHoldDays
	 * TemplateKey	W_InResultDataHoldDays
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_InResultDataHoldDays = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_InResultDataHoldDays" , "W_InResultDataHoldDays");

	/**
	 * ControlID	btn_Set
	 * TemplateKey	W_P_Set
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "W_P_Set");

	/**
	 * ControlID	btn_ReDisplayFunc
	 * TemplateKey	W_ReDisplayFunc
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ReDisplayFunc = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ReDisplayFunc" , "W_ReDisplayFunc");

	/**
	 * ControlID	lbl_SystemEnvironment
	 * TemplateKey	W_SystemEnvironment
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SystemEnvironment = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SystemEnvironment" , "W_SystemEnvironment");

	/**
	 * ControlID	lst_SystemStatus
	 * TemplateKey	W_SystemStatus
	 * ControlType	ScrollListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_SystemStatus = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_SystemStatus" , "W_SystemStatus");

}
//end of class

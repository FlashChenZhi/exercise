// $Id: SystemStateMaintenance.java 6030 2009-11-18 04:22:56Z fukuwa $

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
 * @version $Revision: 6030 $, $Date: 2009-11-18 13:22:56 +0900 (水, 18 11 2009) $
 * @author  $Author: fukuwa $
 */
public class SystemStateMaintenance extends Page
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
	 * TemplateKey	W_LstOperationMsg
	 * ControlType	Message
	 */
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "W_LstOperationMsg");

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
	 * ControlID	btn_DailyProcess
	 * TemplateKey	W_NoProcess
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_DailyProcess = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_DailyProcess" , "W_NoProcess");

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
	 * ControlID	btn_LoadPlanData
	 * TemplateKey	W_NoProcess
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_LoadPlanData = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_LoadPlanData" , "W_NoProcess");

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
	 * ControlID	btn_CreateReportData
	 * TemplateKey	W_NoProcess
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_CreateReportData = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_CreateReportData" , "W_NoProcess");

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
	 * ControlID	btn_RetrievalAllocate
	 * TemplateKey	W_NoProcess
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_RetrievalAllocate = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_RetrievalAllocate" , "W_NoProcess");

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
	 * ControlID	btn_AllocationClear
	 * TemplateKey	W_NoProcess
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllocationClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllocationClear" , "W_NoProcess");

	/**
	 * ControlID	lbl_HostCommunication
	 * TemplateKey	W_HostCommunication
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_HostCommunication = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_HostCommunication" , "W_HostCommunication");

	/**
	 * ControlID	rdo_HostCommunication_Effectiv
	 * TemplateKey	W_HostCommunication_Effective
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_HostCommunication_Effectiv = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_HostCommunication_Effectiv" , "W_HostCommunication_Effective");

	/**
	 * ControlID	rdo_HostCommunication_Invalidi
	 * TemplateKey	W_HostCommunication_Invalidity
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_HostCommunication_Invalidi = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_HostCommunication_Invalidi" , "W_HostCommunication_Invalidity");

	/**
	 * ControlID	btn_Set
	 * TemplateKey	W_Set2
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "W_Set2");

	/**
	 * ControlID	btn_ReDisplay
	 * TemplateKey	W_ReDisplayFunc
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ReDisplay = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ReDisplay" , "W_ReDisplayFunc");

	/**
	 * ControlID	btn_Close
	 * TemplateKey	W_Close
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close" , "W_Close");

}
//end of class

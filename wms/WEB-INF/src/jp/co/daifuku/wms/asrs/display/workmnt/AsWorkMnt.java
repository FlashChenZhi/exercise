// $Id: AsWorkMnt.java 5672 2009-11-11 07:30:49Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.workmnt;
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
 * @version $Revision: 5672 $, $Date: 2009-11-11 16:30:49 +0900 (水, 11 11 2009) $
 * @author  $Author: okayama $
 */
public class AsWorkMnt extends Page
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
	 * TemplateKey	W_Maintenance
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Maintenance");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_WrokPlaceStation
	 * TemplateKey	W_WrokPlaceStation
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WrokPlaceStation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WrokPlaceStation" , "W_WrokPlaceStation");

	/**
	 * ControlID	pul_WorkPlace
	 * TemplateKey	W_WorkPlace
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_WorkPlace = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_WorkPlace" , "W_WorkPlace");

	/**
	 * ControlID	lbl_Slash
	 * TemplateKey	W_Slash
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Slash = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Slash" , "W_Slash");

	/**
	 * ControlID	pul_Station
	 * TemplateKey	W_Station
	 * ControlType	LinkedPullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_Station = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_Station" , "W_Station");

	/**
	 * ControlID	btn_PInquiry
	 * TemplateKey	W_P_Inquiry
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PInquiry = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PInquiry" , "W_P_Inquiry");

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
	 * ControlID	btn_Set
	 * TemplateKey	W_Set2
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "W_Set2");

	/**
	 * ControlID	btn_ReDisplay
	 * TemplateKey	W_ReDisplay
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ReDisplay = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ReDisplay" , "W_ReDisplay");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

	/**
	 * ControlID	lbl_Process
	 * TemplateKey	W_Process
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Process = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Process" , "W_Process");

	/**
	 * ControlID	rdo_ASRSProcess_NoIndication
	 * TemplateKey	W_ASRSProcess_NoIndication
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ASRSProcess_NoIndication = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ASRSProcess_NoIndication" , "W_ASRSProcess_NoIndication");

	/**
	 * ControlID	rdo_ASRSProcess_CancelAllocate
	 * TemplateKey	W_ASRSProcess_CancelAllocate
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ASRSProcess_CancelAllocate = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ASRSProcess_CancelAllocate" , "W_ASRSProcess_CancelAllocate");

	/**
	 * ControlID	rdo_ASRSProcess_TrackingDelete
	 * TemplateKey	W_ASRSProcess_TrackingDelete
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ASRSProcess_TrackingDelete = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ASRSProcess_TrackingDelete" , "W_ASRSProcess_TrackingDelete");

	/**
	 * ControlID	rdo_ASRSProcess_Finished
	 * TemplateKey	W_ASRSProcess_Finished
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ASRSProcess_Finished = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ASRSProcess_Finished" , "W_ASRSProcess_Finished");

	/**
	 * ControlID	lbl_FromCarryingToCarrying
	 * TemplateKey	W_FromCarryingToCarrying
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromCarryingToCarrying = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromCarryingToCarrying" , "W_FromCarryingToCarrying");

	/**
	 * ControlID	txt_FromCarrying
	 * TemplateKey	W_R_Carrying
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FromCarrying = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FromCarrying" , "W_R_Carrying");

	/**
	 * ControlID	txt_ToCarrying
	 * TemplateKey	W_R_Carrying
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ToCarrying = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ToCarrying" , "W_R_Carrying");

	/**
	 * ControlID	btn_PSTOperation
	 * TemplateKey	W_P_STOperation
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSTOperation = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSTOperation" , "W_P_STOperation");

	/**
	 * ControlID	lbl_Location
	 * TemplateKey	W_Location
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Location = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Location" , "W_Location");

	/**
	 * ControlID	txt_Location
	 * TemplateKey	W_Location
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Location = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Location" , "W_Location");

	/**
	 * ControlID	lbl_CarryingFlag
	 * TemplateKey	W_CarryingFlag
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CarryingFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CarryingFlag" , "W_CarryingFlag");

	/**
	 * ControlID	txt_CarryingFlag
	 * TemplateKey	W_R_CarryingFlag
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CarryingFlag = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CarryingFlag" , "W_R_CarryingFlag");

	/**
	 * ControlID	lbl_CarryingStatus
	 * TemplateKey	W_CarryingStatus
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CarryingStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CarryingStatus" , "W_CarryingStatus");

	/**
	 * ControlID	txt_CarryingStatus
	 * TemplateKey	W_R_CarryingStatus
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CarryingStatus = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CarryingStatus" , "W_R_CarryingStatus");

	/**
	 * ControlID	lbl_WorkKind
	 * TemplateKey	W_WorkKind
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkKind = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkKind" , "W_WorkKind");

	/**
	 * ControlID	txt_WorkKind
	 * TemplateKey	W_WorkKind
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkKind = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkKind" , "W_WorkKind");

	/**
	 * ControlID	lbl_RetrievalInstructionDetail
	 * TemplateKey	W_RetrievalInstructionDetail
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalInstructionDetail = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalInstructionDetail" , "W_RetrievalInstructionDetail");

	/**
	 * ControlID	txt_RetrievalDetail
	 * TemplateKey	W_RetrievalInstructionDetail
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RetrievalDetail = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RetrievalDetail" , "W_RetrievalInstructionDetail");

	/**
	 * ControlID	lbl_WorkNo
	 * TemplateKey	W_WorkNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkNo" , "W_WorkNo");

	/**
	 * ControlID	txt_WorkNo
	 * TemplateKey	W_WorkNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkNo" , "W_WorkNo");

	/**
	 * ControlID	btn_Details
	 * TemplateKey	W_P_DetailFnc
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Details = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Details" , "W_P_DetailFnc");

	/**
	 * ControlID	lbl_ScheduleNo
	 * TemplateKey	W_ScheduleNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ScheduleNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ScheduleNo" , "W_ScheduleNo");

	/**
	 * ControlID	txt_ScheduleNo
	 * TemplateKey	W_ScheduleNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ScheduleNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ScheduleNo" , "W_ScheduleNo");

	/**
	 * ControlID	lbl_CarryingKey
	 * TemplateKey	W_McKey
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CarryingKey = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CarryingKey" , "W_McKey");

	/**
	 * ControlID	txt_CarryingKey
	 * TemplateKey	W_R_CarryingKey
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CarryingKey = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CarryingKey" , "W_R_CarryingKey");

}
//end of class

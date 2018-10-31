// $Id: DailyUpdate.java 6996 2010-02-08 06:49:03Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.system.display.dailyupdate;
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
 * @version $Revision: 6996 $, $Date: 2010-02-08 15:49:03 +0900 (月, 08 2 2010) $
 * @author  $Author: okayama $
 */
public class DailyUpdate extends Page
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
	 * TemplateKey	W_DailyUpdate
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_DailyUpdate");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_WorkDate
	 * TemplateKey	W_WorkDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkDate" , "W_WorkDate");

	/**
	 * ControlID	txt_InWorkDate
	 * TemplateKey	W_WorkDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_InWorkDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_InWorkDate" , "W_WorkDate");

	/**
	 * ControlID	lbl_In_WorkDay
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_WorkDay = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_WorkDay" , "W_In_JavaSet");

	/**
	 * ControlID	btn_DvdCopy
	 * TemplateKey	W_DvdCopy
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_DvdCopy = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_DvdCopy" , "W_DvdCopy");

	/**
	 * ControlID	lbl_LastDailyCleanup
	 * TemplateKey	W_LastDailyCleanup
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LastDailyCleanup = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LastDailyCleanup" , "W_LastDailyCleanup");

	/**
	 * ControlID	txt_ExecutionDate
	 * TemplateKey	W_WorkDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_ExecutionDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_ExecutionDate" , "W_WorkDate");

	/**
	 * ControlID	txt_ExecutionTime
	 * TemplateKey	W_WorkTime
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_ExecutionTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_ExecutionTime" , "W_WorkTime");

	/**
	 * ControlID	lbl_NoWorkInfo
	 * TemplateKey	W_NoWorkInfo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_NoWorkInfo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_NoWorkInfo" , "W_NoWorkInfo");

	/**
	 * ControlID	rdo_NoWorkInfomation_Delete
	 * TemplateKey	W_NoWorkInfomation_Delete
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_NoWorkInfomation_Delete = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_NoWorkInfomation_Delete" , "W_NoWorkInfomation_Delete");

	/**
	 * ControlID	rdo_NoWorkInfomation_CarryOver
	 * TemplateKey	W_NoWorkInfomation_CarryOver
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_NoWorkInfomation_CarryOver = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_NoWorkInfomation_CarryOver" , "W_NoWorkInfomation_CarryOver");

	/**
	 * ControlID	chk_BackupAndShutdown
	 * TemplateKey	W_BackupAndShutdown
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_BackupAndShutdown = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_BackupAndShutdown" , "W_BackupAndShutdown");

	/**
	 * ControlID	btn_Start
	 * TemplateKey	W_Start
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Start = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Start" , "W_Start");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

	/**
	 * ControlID	btn_ReDisplay
	 * TemplateKey	W_ReDisplayFunc
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ReDisplay = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ReDisplay" , "W_ReDisplayFunc");

	/**
	 * ControlID	lst_DailyUpdate
	 * TemplateKey	W_DailyUpdate
	 * ControlType	ScrollListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_DailyUpdate = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_DailyUpdate" , "W_DailyUpdate");

}
//end of class

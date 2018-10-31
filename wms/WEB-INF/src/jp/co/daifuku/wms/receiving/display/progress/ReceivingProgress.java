// $Id: ReceivingProgress.java 887 2008-10-29 00:33:10Z tanaka $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.receiving.display.progress;
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
 * @version $Revision: 887 $, $Date: 2008-10-29 09:33:10 +0900 (水, 29 10 2008) $
 * @author  $Author: tanaka $
 */
public class ReceivingProgress extends Page
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
	 * ControlID	rdo_ProgressDisplayAuto
	 * TemplateKey	W_ProgressDisplay_Auto
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ProgressDisplayAuto = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ProgressDisplayAuto" , "W_ProgressDisplay_Auto");

	/**
	 * ControlID	rdo_ProgressDisplayManual
	 * TemplateKey	W_ProgressDisplay_Manual
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ProgressDisplayManual = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ProgressDisplayManual" , "W_ProgressDisplay_Manual");

	/**
	 * ControlID	lst_ReceivingProgress_up
	 * TemplateKey	W_ReceivingProgress
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ReceivingProgress_up = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ReceivingProgress_up" , "W_ReceivingProgress");

	/**
	 * ControlID	hbc_TaskProgress_up
	 * TemplateKey	W_TaskProgress
	 * ControlType	HorizontalBarChart
	 */
	public jp.co.daifuku.bluedog.ui.control.HorizontalBarChart hbc_TaskProgress_up = jp.co.daifuku.bluedog.ui.control.HorizontalBarChartFactory.getInstance("hbc_TaskProgress_up" , "W_TaskProgress");

	/**
	 * ControlID	txt_ProgressRate_up
	 * TemplateKey	W_ProgressRate
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ProgressRate_up = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ProgressRate_up" , "W_ProgressRate");

	/**
	 * ControlID	lbl_Percent_up
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Percent_up = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Percent_up" , "W_Percent");

	/**
	 * ControlID	lst_ReceivingProgress_down
	 * TemplateKey	W_ReceivingProgress
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ReceivingProgress_down = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ReceivingProgress_down" , "W_ReceivingProgress");

	/**
	 * ControlID	hbc_TaskProgress_down
	 * TemplateKey	W_TaskProgress
	 * ControlType	HorizontalBarChart
	 */
	public jp.co.daifuku.bluedog.ui.control.HorizontalBarChart hbc_TaskProgress_down = jp.co.daifuku.bluedog.ui.control.HorizontalBarChartFactory.getInstance("hbc_TaskProgress_down" , "W_TaskProgress");

	/**
	 * ControlID	txt_ProgressRate_down
	 * TemplateKey	W_ProgressRate
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ProgressRate_down = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ProgressRate_down" , "W_ProgressRate");

	/**
	 * ControlID	lbl_Percent_down
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Percent_down = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Percent_down" , "W_Percent");

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

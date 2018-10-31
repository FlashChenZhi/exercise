// $Id: BreakTimeRegist.java 7138 2010-02-18 07:55:09Z shibamoto $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.analysis.display.breaktimeregist;
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
 * @version $Revision: 7138 $, $Date: 2010-02-18 16:55:09 +0900 (木, 18 2 2010) $
 * @author  $Author: shibamoto $
 */
public class BreakTimeRegist extends Page
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
	 * TemplateKey	W_Regist
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Regist");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_BreakTime
	 * TemplateKey	W_BreakTime
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BreakTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BreakTime" , "W_BreakTime");

	/**
	 * ControlID	lbl_BreakStartRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BreakStartRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BreakStartRequire" , "W_Require");

	/**
	 * ControlID	txt_BreakStartTime
	 * TemplateKey	W_BreakStartTime
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_BreakStartTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_BreakStartTime" , "W_BreakStartTime");

	/**
	 * ControlID	lbl_range
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_range = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_range" , "W_Range");

	/**
	 * ControlID	txt_BreakEndTime
	 * TemplateKey	W_BreakEndTime
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_BreakEndTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_BreakEndTime" , "W_BreakEndTime");

	/**
	 * ControlID	lbl_InputStyle
	 * TemplateKey	W_InputStyle
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InputStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InputStyle" , "W_InputStyle");

	/**
	 * ControlID	lbl_1
	 * TemplateKey	W_InputStyleHHmm
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_1" , "W_InputStyleHHmm");

	/**
	 * ControlID	btn_Input
	 * TemplateKey	W_Input
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Input = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Input" , "W_Input");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

	/**
	 * ControlID	btn_Setting
	 * TemplateKey	W_Set2
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Setting = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Setting" , "W_Set2");

	/**
	 * ControlID	lst_BreakTimeList
	 * TemplateKey	W_BreakTimeList
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_BreakTimeList = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_BreakTimeList" , "W_BreakTimeList");

}
//end of class

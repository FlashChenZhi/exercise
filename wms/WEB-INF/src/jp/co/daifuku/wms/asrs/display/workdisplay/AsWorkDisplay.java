// $Id: AsWorkDisplay.java,v 1.1.1.1 2009/02/10 08:55:20 arai Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.workdisplay;
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
 * @version $Revision: 1.1.1.1 $, $Date: 2009/02/10 08:55:20 $
 * @author  $Author: arai $
 */
public class AsWorkDisplay extends Page
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
	 * TemplateKey	W_WorkDisplay
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_WorkDisplay");

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
	 * ControlID	lbl_2
	 * TemplateKey	W_Station
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_2" , "W_Station");

	/**
	 * ControlID	pul_Station
	 * TemplateKey	W_Station
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Station = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Station" , "W_Station");

	/**
	 * ControlID	btn_ReDisplayFunc
	 * TemplateKey	W_ReDisplayFunc
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ReDisplayFunc = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ReDisplayFunc" , "W_ReDisplayFunc");

	/**
	 * ControlID	btn_Complete
	 * TemplateKey	W_Complete
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Complete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Complete" , "W_Complete");

	/**
	 * ControlID	btn_PickComplete
	 * TemplateKey	W_PickComplete
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PickComplete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PickComplete" , "W_PickComplete");

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
	 * ControlID	lbl_InstructDetail
	 * TemplateKey	W_RetrievalInstructionDetail
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InstructDetail = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InstructDetail" , "W_RetrievalInstructionDetail");

	/**
	 * ControlID	txt_InstructDetail
	 * TemplateKey	W_RetrievalInstructionDetail
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_InstructDetail = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_InstructDetail" , "W_RetrievalInstructionDetail");

	/**
	 * ControlID	lst_AsrsWorkDisplay
	 * TemplateKey	W_ASRSWorkCommand
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_AsrsWorkDisplay = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_AsrsWorkDisplay" , "W_ASRSWorkCommand");

}
//end of class

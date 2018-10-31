// $Id: PCTFloorProgress.java 3551 2009-03-17 02:33:12Z rnakai $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.retrieval.display.pctfloorprogress;
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
 * @version $Revision: 3551 $, $Date: 2009-03-17 11:33:12 +0900 (火, 17 3 2009) $
 * @author  $Author: rnakai $
 */
public class PCTFloorProgress extends Page
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
	 * ControlID	lbl_ConsignorCode
	 * TemplateKey	W_ConsignorCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");

	/**
	 * ControlID	lbl_Require
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Require = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Require" , "W_Require");

	/**
	 * ControlID	txt_ConsignorCode
	 * TemplateKey	W_ConsignorCodePCT
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCodePCT");

	/**
	 * ControlID	txt_ConsignorName
	 * TemplateKey	T_R_FunctionName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorName" , "T_R_FunctionName");

	/**
	 * ControlID	lbl_BatchNo
	 * TemplateKey	W_BatchNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BatchNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BatchNo" , "W_BatchNo");

	/**
	 * ControlID	txt_BatchNo
	 * TemplateKey	W_BatchNoPCT
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BatchNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BatchNo" , "W_BatchNoPCT");

	/**
	 * ControlID	lbl_BatchSeqNo
	 * TemplateKey	W_BatchSeqNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BatchSeqNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BatchSeqNo" , "W_BatchSeqNo");

	/**
	 * ControlID	txt_BatchSeqNo
	 * TemplateKey	W_BatchSeqNoPCT
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BatchSeqNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BatchSeqNo" , "W_BatchSeqNoPCT");

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
	 * ControlID	btn_Display
	 * TemplateKey	W_DisplayCall
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Display" , "W_DisplayCall");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

	/**
	 * ControlID	lst_PCTAreaProgress
	 * TemplateKey	W_PCTAreaProgress
	 * ControlType	ScrollListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_PCTAreaProgress = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_PCTAreaProgress" , "W_PCTAreaProgress");

}
//end of class

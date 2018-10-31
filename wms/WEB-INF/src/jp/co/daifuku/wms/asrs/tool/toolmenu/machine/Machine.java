// $Id: Machine.java 7259 2010-02-26 06:02:47Z kanda $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.machine;
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
 * @version $Revision: 7259 $, $Date: 2010-02-26 15:02:47 +0900 (金, 26 2 2010) $
 * @author  $Author: kanda $
 */
public class Machine extends Page
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
	 * TemplateKey	AST_Create
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "AST_Create");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_AgcNumber
	 * TemplateKey	AST_AgcNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AgcNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AgcNumber" , "AST_AgcNumber");

	/**
	 * ControlID	pul_AGCNo
	 * TemplateKey	AST_AGCNo
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_AGCNo = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_AGCNo" , "AST_AGCNo");

	/**
	 * ControlID	lbl_MachineCode
	 * TemplateKey	AST_MachineCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MachineCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MachineCode" , "AST_MachineCode");

	/**
	 * ControlID	pul_MachineTypeCode
	 * TemplateKey	AST_MachineTypeCode
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_MachineTypeCode = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_MachineTypeCode" , "AST_MachineTypeCode");

	/**
	 * ControlID	lbl_MachineNumber
	 * TemplateKey	AST_MachineNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MachineNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MachineNumber" , "AST_MachineNumber");

	/**
	 * ControlID	txt_MachineNumber
	 * TemplateKey	AST_MachineNumber
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_MachineNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_MachineNumber" , "AST_MachineNumber");

	/**
	 * ControlID	lbl_MachineName
	 * TemplateKey	W_MachineName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MachineName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MachineName" , "W_MachineName");

	/**
	 * ControlID	txt_MachineName
	 * TemplateKey	W_MachineName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_MachineName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_MachineName" , "W_MachineName");

	/**
	 * ControlID	lbl_StationNumber
	 * TemplateKey	AST_StationNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StationNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StationNumber" , "AST_StationNumber");

	/**
	 * ControlID	pul_StationNumber
	 * TemplateKey	AST_StationNumber
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_StationNumber = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_StationNumber" , "AST_StationNumber");

	/**
	 * ControlID	btn_Add
	 * TemplateKey	AST_Add
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Add = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Add" , "AST_Add");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	AST_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "AST_Clear");

	/**
	 * ControlID	btn_Commit
	 * TemplateKey	AST_Commit
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "AST_Commit");

	/**
	 * ControlID	btn_Cancel
	 * TemplateKey	AST_Cancel
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "AST_Cancel");

	/**
	 * ControlID	lst_MachineStatus
	 * TemplateKey	AST_S_MachineStatus
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_MachineStatus = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_MachineStatus" , "AST_S_MachineStatus");

}
//end of class

// $Id: AsrsReplenishStart.java 135 2008-10-08 22:42:24Z okamura $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.replenishstart;
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
 * @version $Revision: 135 $, $Date: 2008-10-09 07:42:24 +0900 (木, 09 10 2008) $
 * @author  $Author: okamura $
 */
public class AsrsReplenishStart extends Page
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
	 * ControlID	tab_Start
	 * TemplateKey	W_Start
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Start = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Start" , "W_Start");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_ListWorkNo
	 * TemplateKey	W_ListWorkNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListWorkNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListWorkNo" , "W_ListWorkNo");

	/**
	 * ControlID	txt_ListWorkNo
	 * TemplateKey	W_ListWorkNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ListWorkNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ListWorkNo" , "W_ListWorkNo");

	/**
	 * ControlID	lbl_AreaNo
	 * TemplateKey	AST_AreaNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AreaNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AreaNo" , "AST_AreaNo");

	/**
	 * ControlID	pul_Area
	 * TemplateKey	W_Area
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Area = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Area" , "W_Area");

	/**
	 * ControlID	btn_Display
	 * TemplateKey	W_Display
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Display" , "W_Display");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

	/**
	 * ControlID	btn_Start
	 * TemplateKey	W_Start
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Start = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Start" , "W_Start");

	/**
	 * ControlID	btn_AllCheck
	 * TemplateKey	W_AllCheck
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllCheck = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllCheck" , "W_AllCheck");

	/**
	 * ControlID	btn_AllCheckClear
	 * TemplateKey	W_AllCheckClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllCheckClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllCheckClear" , "W_AllCheckClear");

	/**
	 * ControlID	btn_AllClear
	 * TemplateKey	W_AllClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllClear" , "W_AllClear");

	/**
	 * ControlID	lst_ReplenishmentStart
	 * TemplateKey	W_ReplenishmentStart
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ReplenishmentStart = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ReplenishmentStart" , "W_ReplenishmentStart");

}
//end of class

// $Id: FaAsReStoring.java 7348 2010-03-04 04:17:40Z kishimoto $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.farestoring;
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
 * @version $Revision: 7348 $, $Date: 2010-03-04 13:17:40 +0900 (木, 04 3 2010) $
 * @author  $Author: kishimoto $
 */
public class FaAsReStoring extends Page
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
	 * ControlID	lbl_Area
	 * TemplateKey	W_Area
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Area = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Area" , "W_Area");

	/**
	 * ControlID	pul_Area
	 * TemplateKey	W_Area
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Area = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Area" , "W_Area");

	/**
	 * ControlID	lbl_WorkPlace
	 * TemplateKey	W_WorkPlace
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkPlace = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkPlace" , "W_WorkPlace");

	/**
	 * ControlID	pul_WorkPlace
	 * TemplateKey	W_WorkPlace
	 * ControlType	LinkedPullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_WorkPlace = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_WorkPlace" , "W_WorkPlace");

	/**
	 * ControlID	lbl_Station
	 * TemplateKey	W_Station
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Station = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Station" , "W_Station");

	/**
	 * ControlID	pul_Station
	 * TemplateKey	W_Station
	 * ControlType	LinkedPullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_Station = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_Station" , "W_Station");

	/**
	 * ControlID	lbl_WorkNo
	 * TemplateKey	W_WorkNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkNo" , "W_WorkNo");

	/**
	 * ControlID	lbl_Require
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Require = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Require" , "W_Require");

	/**
	 * ControlID	txt_WorkNo
	 * TemplateKey	W_WorkNo_InputCompEvent
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WorkNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WorkNo" , "W_WorkNo_InputCompEvent");

	/**
	 * ControlID	lbl_Detail
	 * TemplateKey	W_Detail
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Detail = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Detail" , "W_Detail");

	/**
	 * ControlID	btn_PWorkNoDetail
	 * TemplateKey	W_P_WorkNoDetail
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PWorkNoDetail = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PWorkNoDetail" , "W_P_WorkNoDetail");

	/**
	 * ControlID	lbl_SoftZone
	 * TemplateKey	W_SoftZone
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SoftZone = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SoftZone" , "W_SoftZone");

	/**
	 * ControlID	pul_SoftZone
	 * TemplateKey	W_SoftZone
	 * ControlType	LinkedPullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_SoftZone = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_SoftZone" , "W_SoftZone");

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
	 * ControlID	btn_Set
	 * TemplateKey	W_Set2
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "W_Set2");

	/**
	 * ControlID	btn_AllClear
	 * TemplateKey	W_AllClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllClear" , "W_AllClear");

	/**
	 * ControlID	btn_WorkListPrint
	 * TemplateKey	W_InventoryListIssue
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_WorkListPrint = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_WorkListPrint" , "W_InventoryListIssue");

	/**
	 * ControlID	chk_LWorkListPrint
	 * TemplateKey	W_WorkListPrint
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_LWorkListPrint = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_LWorkListPrint" , "W_WorkListPrint");

	/**
	 * ControlID	lst_FaAsReStoring
	 * TemplateKey	W_FaAsReStoring
	 * ControlType	ScrollListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_FaAsReStoring = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_FaAsReStoring" , "W_FaAsReStoring");

}
//end of class

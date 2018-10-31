// $Id: NamePlateList.java 3287 2009-03-10 09:27:30Z tanaka $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.system.display.nameplatelist;
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
 * @version $Revision: 3287 $, $Date: 2009-03-10 18:27:30 +0900 (火, 10 3 2009) $
 * @author  $Author: tanaka $
 */
public class NamePlateList extends Page
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
	 * TemplateKey	W_Issue
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Issue");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_UserID
	 * TemplateKey	T_UserID
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserID = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserID" , "T_UserID");

	/**
	 * ControlID	txt_UserIdFrom
	 * TemplateKey	T_UserId
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserIdFrom = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserIdFrom" , "T_UserId");

	/**
	 * ControlID	btn_SearchFrom
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchFrom = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchFrom" , "W_P_Search");

	/**
	 * ControlID	lbl_Range
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range" , "W_Range");

	/**
	 * ControlID	txt_UserIdTo
	 * TemplateKey	T_UserId
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserIdTo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserIdTo" , "T_UserId");

	/**
	 * ControlID	btn_SearchTo
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchTo = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchTo" , "W_P_Search");

	/**
	 * ControlID	lbl_Role
	 * TemplateKey	T_Role
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Role = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Role" , "T_Role");

	/**
	 * ControlID	pul_Role
	 * TemplateKey	T_Role
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Role = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Role" , "T_Role");

	/**
	 * ControlID	slb_Preview
	 * TemplateKey	W_Preview
	 * ControlType	SubmitLabel
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitLabel slb_Preview = jp.co.daifuku.bluedog.ui.control.SubmitLabelFactory.getInstance("slb_Preview" , "W_Preview");

	/**
	 * ControlID	btn_Preview
	 * TemplateKey	W_Preview
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Preview = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Preview" , "W_Preview");

	/**
	 * ControlID	btn_Print
	 * TemplateKey	W_Print
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Print = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Print" , "W_Print");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

}
//end of class

// $Id: StoragePlanRegist.java 7138 2010-02-18 07:55:09Z shibamoto $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.display.planregist;
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
public class StoragePlanRegist extends Page
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
	 * ControlID	tab_SetRegist
	 * TemplateKey	W_SetRegist
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_SetRegist = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_SetRegist" , "W_SetRegist");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_StoragePlanDate
	 * TemplateKey	W_StoregePlanDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StoragePlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StoragePlanDate" , "W_StoregePlanDate");

	/**
	 * ControlID	lbl_RequirePlanDate
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequirePlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequirePlanDate" , "W_Require");

	/**
	 * ControlID	txt_StoragePlanDate
	 * TemplateKey	W_PlanDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_StoragePlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_StoragePlanDate" , "W_PlanDate");

	/**
	 * ControlID	lbl_InputStyle
	 * TemplateKey	W_InputStyle
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InputStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InputStyle" , "W_InputStyle");

	/**
	 * ControlID	lbl_Day
	 * TemplateKey	W_InputStyleDay
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Day = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Day" , "W_InputStyleDay");

	/**
	 * ControlID	lbl_SlipNumber
	 * TemplateKey	W_SlipNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SlipNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SlipNumber" , "W_SlipNumber");

	/**
	 * ControlID	lbl_RequireSlip
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireSlip = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireSlip" , "W_Require");

	/**
	 * ControlID	txt_SlipNumber
	 * TemplateKey	W_SlipNumber
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SlipNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SlipNumber" , "W_SlipNumber");

	/**
	 * ControlID	btn_PSearchStoragePlan
	 * TemplateKey	W_P_SearchPlan
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchStoragePlan = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchStoragePlan" , "W_P_SearchPlan");

	/**
	 * ControlID	btn_Next
	 * TemplateKey	W_Next
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Next = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Next" , "W_Next");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

}
//end of class

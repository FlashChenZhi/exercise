// $Id: ReceivingPlanRegist.java 7156 2010-02-19 07:37:05Z fukuwa $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.receiving.display.planregist;
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
 * @version $Revision: 7156 $, $Date: 2010-02-19 16:37:05 +0900 (金, 19 2 2010) $
 * @author  $Author: fukuwa $
 */
public class ReceivingPlanRegist extends Page
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
	 * ControlID	lbl_ReceivingPlanDate
	 * TemplateKey	W_ReceivingPlanDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ReceivingPlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ReceivingPlanDate" , "W_ReceivingPlanDate");

	/**
	 * ControlID	lbl_RequirePlanDate
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequirePlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequirePlanDate" , "W_Require");

	/**
	 * ControlID	txt_ReceivingPlanDate
	 * TemplateKey	W_PlanDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_ReceivingPlanDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_ReceivingPlanDate" , "W_PlanDate");

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
	 * ControlID	lbl_SupplierCode
	 * TemplateKey	W_SupplierCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SupplierCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SupplierCode" , "W_SupplierCode");

	/**
	 * ControlID	lbl_RequireSlip
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireSlip = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireSlip" , "W_Require");

	/**
	 * ControlID	txt_SupplierCode
	 * TemplateKey	W_SupplierCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SupplierCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SupplierCode" , "W_SupplierCode");

	/**
	 * ControlID	btn_SearchSupplier
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchSupplier = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchSupplier" , "W_P_Search");

	/**
	 * ControlID	lbl_SupplierName
	 * TemplateKey	W_SupplierName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SupplierName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SupplierName" , "W_SupplierName");

	/**
	 * ControlID	txt_SupplierName
	 * TemplateKey	W_SupplierName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SupplierName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SupplierName" , "W_SupplierName");

	/**
	 * ControlID	lbl_SlipNumber
	 * TemplateKey	W_SlipNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SlipNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SlipNumber" , "W_SlipNumber");

	/**
	 * ControlID	lbl_5
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_5 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_5" , "W_Require");

	/**
	 * ControlID	txt_SlipNumber
	 * TemplateKey	W_SlipNumber
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_SlipNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_SlipNumber" , "W_SlipNumber");

	/**
	 * ControlID	btn_PSearchReceivingPlan
	 * TemplateKey	W_P_SearchPlan
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PSearchReceivingPlan = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PSearchReceivingPlan" , "W_P_SearchPlan");

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

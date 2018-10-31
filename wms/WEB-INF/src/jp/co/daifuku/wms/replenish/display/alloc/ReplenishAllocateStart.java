// $Id: ReplenishAllocateStart.java 7008 2010-02-16 01:58:21Z fukuwa $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.replenish.display.alloc;
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
 * @version $Revision: 7008 $, $Date: 2010-02-16 10:58:21 +0900 (火, 16 2 2010) $
 * @author  $Author: fukuwa $
 */
public class ReplenishAllocateStart extends Page
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
	 * ControlID	lbl_ToReplenishmentArea
	 * TemplateKey	W_ToReplenishmentArea
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ToReplenishmentArea = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ToReplenishmentArea" , "W_ToReplenishmentArea");

	/**
	 * ControlID	pul_ToReplenishmentArea
	 * TemplateKey	W_ToReplenishmentArea
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_ToReplenishmentArea = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_ToReplenishmentArea" , "W_ToReplenishmentArea");

	/**
	 * ControlID	lbl_ToReplenishmentLocation
	 * TemplateKey	W_ToReplenishmentLocation
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ToReplenishmentLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ToReplenishmentLocation" , "W_ToReplenishmentLocation");

	/**
	 * ControlID	txt_FromLocation
	 * TemplateKey	W_Location
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FromLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FromLocation" , "W_Location");

	/**
	 * ControlID	lbl_Range
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range" , "W_Range");

	/**
	 * ControlID	txt_ToLocation
	 * TemplateKey	W_Location
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ToLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ToLocation" , "W_Location");

	/**
	 * ControlID	lbl_InputStyle
	 * TemplateKey	W_InputStyle
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InputStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InputStyle" , "W_InputStyle");

	/**
	 * ControlID	lbl_LocationStyle
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationStyle" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	txt_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	lbl_ReplenishmentRate
	 * TemplateKey	W_ReplenishmentRate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ReplenishmentRate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ReplenishmentRate" , "W_ReplenishmentRate");

	/**
	 * ControlID	lbl_Ast_ReplenishmentRate
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Ast_ReplenishmentRate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Ast_ReplenishmentRate" , "W_Require");

	/**
	 * ControlID	txt_ReplenishmentRate
	 * TemplateKey	W_ReplenishmentRate
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ReplenishmentRate = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ReplenishmentRate" , "W_ReplenishmentRate");

	/**
	 * ControlID	lbl_Percent
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Percent = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Percent" , "W_Percent");

	/**
	 * ControlID	lbl_ReplenishmentRateEpn
	 * TemplateKey	W_ReplenishmentRateExplanation
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ReplenishmentRateEpn = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ReplenishmentRateEpn" , "W_ReplenishmentRateExplanation");

	/**
	 * ControlID	lbl_AllocatedPattern
	 * TemplateKey	W_AllocatedPattern
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AllocatedPattern = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AllocatedPattern" , "W_AllocatedPattern");

	/**
	 * ControlID	pul_AllocatedPattern
	 * TemplateKey	W_AllocatedPattern
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_AllocatedPattern = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_AllocatedPattern" , "W_AllocatedPattern");

	/**
	 * ControlID	chk_IssueReport
	 * TemplateKey	W_IssueReport
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_IssueReport = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_IssueReport" , "W_IssueReport");

	/**
	 * ControlID	btn_Start
	 * TemplateKey	W_Start
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Start = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Start" , "W_Start");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

}
//end of class

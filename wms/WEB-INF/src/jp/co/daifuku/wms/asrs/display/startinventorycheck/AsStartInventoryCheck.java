// $Id: AsStartInventoryCheck.java 5433 2009-11-06 10:41:25Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.startinventorycheck;
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
 * @version $Revision: 5433 $, $Date: 2009-11-06 19:41:25 +0900 (金, 06 11 2009) $
 * @author  $Author: okayama $
 */
public class AsStartInventoryCheck extends Page
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
	 * TemplateKey	W_Area_Event
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Area = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Area" , "W_Area_Event");

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
	 * ControlID	lbl_Loaction
	 * TemplateKey	W_Location
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Loaction = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Loaction" , "W_Location");

	/**
	 * ControlID	txt_StartLocation
	 * TemplateKey	W_Location
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StartLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StartLocation" , "W_Location");

	/**
	 * ControlID	lbl_Range1
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range1" , "W_Range");

	/**
	 * ControlID	txt_EndLocation
	 * TemplateKey	W_Location
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EndLocation = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EndLocation" , "W_Location");

	/**
	 * ControlID	lbl_InputStyle
	 * TemplateKey	W_InputStyle
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InputStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InputStyle" , "W_InputStyle");

	/**
	 * ControlID	lbl_In_LocationStyle
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_LocationStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_LocationStyle" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	txt_StartItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StartItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StartItemCode" , "W_ItemCode");

	/**
	 * ControlID	btn_SearchFromItemCode
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchFromItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchFromItemCode" , "W_P_Search");

	/**
	 * ControlID	lbl_Range2
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range2" , "W_Range");

	/**
	 * ControlID	txt_EndItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EndItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EndItemCode" , "W_ItemCode");

	/**
	 * ControlID	btn_SearchToItemCode
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchToItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchToItemCode" , "W_P_Search");

	/**
	 * ControlID	chk_CommonUse
	 * TemplateKey	W_WorkListPrint
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_CommonUse = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_CommonUse" , "W_WorkListPrint");

	/**
	 * ControlID	btn_Setting
	 * TemplateKey	W_Set2
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Setting = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Setting" , "W_Set2");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

}
//end of class

// $Id: skelten.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stock.display.fashelfinquiry;
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
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class FaShelfInquiry extends Page
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
	 * TemplateKey	W_Inquiry
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Inquiry");

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
	 * ControlID	lbl_LocationStatus
	 * TemplateKey	W_LocationStatus
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationStatus" , "W_LocationStatus");

	/**
	 * ControlID	chk_StoredLocation
	 * TemplateKey	W_StoredLocation
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_StoredLocation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_StoredLocation" , "W_StoredLocation");

	/**
	 * ControlID	chk_EmptyLocation
	 * TemplateKey	W_EmptyLocation
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_EmptyLocation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_EmptyLocation" , "W_EmptyLocation");

	/**
	 * ControlID	chk_WorkLocation
	 * TemplateKey	W_WorkLocation
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkLocation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkLocation" , "W_WorkLocation");

	/**
	 * ControlID	chk_ProhibitedLocation
	 * TemplateKey	W_ProhibitedLocation
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_ProhibitedLocation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_ProhibitedLocation" , "W_ProhibitedLocation");

	/**
	 * ControlID	chk_UnreachableLocation
	 * TemplateKey	W_UnreachableLocation
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_UnreachableLocation = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_UnreachableLocation" , "W_UnreachableLocation");

	/**
	 * ControlID	lbl_StLocate
	 * TemplateKey	W_StLocate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StLocate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StLocate" , "W_StLocate");

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
	 * ControlID	lbl_EdLocate
	 * TemplateKey	W_EdLocate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_EdLocate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_EdLocate" , "W_EdLocate");

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
	 * ControlID	txt_ItemCode_EntEvent
	 * TemplateKey	W_ItemCode_EntEvent
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode_EntEvent = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode_EntEvent" , "W_ItemCode_EntEvent");

	/**
	 * ControlID	btn_SearchItemCode
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchItemCode" , "W_P_Search");

	/**
	 * ControlID	lbl_ItemName
	 * TemplateKey	W_ItemName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemName" , "W_ItemName");

	/**
	 * ControlID	txt_ItemName
	 * TemplateKey	W_ItemName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemName" , "W_ItemName");

	/**
	 * ControlID	btn_SearchItemName
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchItemName = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchItemName" , "W_P_Search");

	/**
	 * ControlID	btn_Display
	 * TemplateKey	W_Display
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Display" , "W_Display");

	/**
	 * ControlID	btn_Print
	 * TemplateKey	W_Print
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Print = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Print" , "W_Print");

	/**
	 * ControlID	btn_Preview
	 * TemplateKey	W_Preview
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Preview = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Preview" , "W_Preview");

	/**
	 * ControlID	btn_XLS
	 * TemplateKey	W_XLS
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_XLS = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_XLS" , "W_XLS");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

	/**
	 * ControlID	pager
	 * TemplateKey	Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pager = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pager" , "Pager");

	/**
	 * ControlID	lst_FaShelfInquiry
	 * TemplateKey	W_FaShelfInquiry
	 * ControlType	ScrollListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_FaShelfInquiry = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_FaShelfInquiry" , "W_FaShelfInquiry");

}
//end of class

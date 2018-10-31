// $Id: LocateSetting.java 5539 2009-11-09 09:26:37Z fukuwa $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.locatesetting;
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
 * @version $Revision: 5539 $, $Date: 2009-11-09 18:26:37 +0900 (月, 09 11 2009) $
 * @author  $Author: fukuwa $
 */
public class LocateSetting extends Page
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
	 * ControlID	lbl_SettingDivision
	 * TemplateKey	AST_CreateClass
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingDivision = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingDivision" , "AST_CreateClass");

	/**
	 * ControlID	rdo_Regist
	 * TemplateKey	W_LocationSetting_Regist
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Regist = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Regist" , "W_LocationSetting_Regist");

	/**
	 * ControlID	rdo_Delete
	 * TemplateKey	W_LocationSetting_Delete
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Delete = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Delete" , "W_LocationSetting_Delete");

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
	 * ControlID	lbl_StLocate
	 * TemplateKey	W_Location
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StLocate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StLocate" , "W_Location");

	/**
	 * ControlID	lbl_Require1
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Require1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Require1" , "W_Require");

	/**
	 * ControlID	txt_StLocate
	 * TemplateKey	W_Location
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StLocate = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StLocate" , "W_Location");

	/**
	 * ControlID	lbl_Range
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range" , "W_Range");

	/**
	 * ControlID	txt_EdLocate
	 * TemplateKey	W_Location
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_EdLocate = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_EdLocate" , "W_Location");

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
	 * ControlID	lbl_AisleNo
	 * TemplateKey	AST_AisleNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AisleNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AisleNo" , "AST_AisleNumber");

	/**
	 * ControlID	txt_AisleNo
	 * TemplateKey	W_AisleNo
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_AisleNo = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_AisleNo" , "W_AisleNo");

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

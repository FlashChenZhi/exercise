// $Id: LocateRegist.java 5539 2009-11-09 09:26:37Z fukuwa $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.locateregist;
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
public class LocateRegist extends Page
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
	 * ControlID	submit
	 * TemplateKey	W_Regist
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab submit = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("submit" , "W_Regist");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	l_ItemCode
	 * TemplateKey	W_Area
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label l_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("l_ItemCode" , "W_Area");

	/**
	 * ControlID	pul_AreaNo
	 * TemplateKey	W_Area_Event
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_AreaNo = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_AreaNo" , "W_Area_Event");

	/**
	 * ControlID	l_Locate
	 * TemplateKey	W_Location
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label l_Locate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("l_Locate" , "W_Location");

	/**
	 * ControlID	lbl_Require
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Require = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Require" , "W_Require");

	/**
	 * ControlID	txt_LocateNo
	 * TemplateKey	W_Location
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_LocateNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_LocateNo" , "W_Location");

	/**
	 * ControlID	btn_Search
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Search" , "W_P_Search");

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
	 * ControlID	l_Replenishment
	 * TemplateKey	AST_AisleNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label l_Replenishment = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("l_Replenishment" , "AST_AisleNumber");

	/**
	 * ControlID	txt_AisleNo
	 * TemplateKey	W_AisleNo
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_AisleNo = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_AisleNo" , "W_AisleNo");

	/**
	 * ControlID	btn_Submit
	 * TemplateKey	W_Set
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "W_Set");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

}
//end of class

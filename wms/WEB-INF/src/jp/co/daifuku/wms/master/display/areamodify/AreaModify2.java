// $Id: AreaModify2.java 5853 2009-11-14 06:01:37Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.areamodify;
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
 * @version $Revision: 5853 $, $Date: 2009-11-14 15:01:37 +0900 (土, 14 11 2009) $
 * @author  $Author: okayama $
 */
public class AreaModify2 extends Page
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
	 * TemplateKey	W_SetModifyDelete2
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_SetModifyDelete2");

	/**
	 * ControlID	btn_Back
	 * TemplateKey	W_Back
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "W_Back");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_MenuNoFunc
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_MenuNoFunc");

	/**
	 * ControlID	lbl_Area
	 * TemplateKey	W_Area
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Area = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Area" , "W_Area");

	/**
	 * ControlID	lbl_InArea
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InArea = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InArea" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_AreaType
	 * TemplateKey	W_AreaType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AreaType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AreaType" , "W_AreaType");

	/**
	 * ControlID	lbl_InAreaType
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InAreaType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InAreaType" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_LocationType
	 * TemplateKey	W_LocationType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationType" , "W_LocationType");

	/**
	 * ControlID	lbl_InLocationType
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InLocationType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InLocationType" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_LocationStyle
	 * TemplateKey	W_LocationStyle
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationStyle" , "W_LocationStyle");

	/**
	 * ControlID	lbl_InLocationStyle
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InLocationStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InLocationStyle" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_AreaName
	 * TemplateKey	W_AreaName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AreaName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AreaName" , "W_AreaName");

	/**
	 * ControlID	lbl_Require
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Require = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Require" , "W_Require");

	/**
	 * ControlID	txt_AreaName
	 * TemplateKey	W_AreaName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_AreaName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_AreaName" , "W_AreaName");

	/**
	 * ControlID	lbl_TemporaryArea
	 * TemplateKey	W_TemporaryArea
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TemporaryArea = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TemporaryArea" , "W_TemporaryArea");

	/**
	 * ControlID	pul_TemporaryArea
	 * TemplateKey	W_TemporaryArea
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_TemporaryArea = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_TemporaryArea" , "W_TemporaryArea");

	/**
	 * ControlID	chk_MoveTemporaryStorage
	 * TemplateKey	W_MoveTemporaryArea
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_MoveTemporaryStorage = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_MoveTemporaryStorage" , "W_MoveTemporaryArea");

	/**
	 * ControlID	lbl_VacantSearchType
	 * TemplateKey	W_VacantSearchType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_VacantSearchType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_VacantSearchType" , "W_VacantSearchType");

	/**
	 * ControlID	pul_VacantSearchType
	 * TemplateKey	W_VacantSearchType
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_VacantSearchType = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_VacantSearchType" , "W_VacantSearchType");

	/**
	 * ControlID	lbl_ReceivingArea
	 * TemplateKey	W_ReceivingArea
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ReceivingArea = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ReceivingArea" , "W_ReceivingArea");

	/**
	 * ControlID	pul_ReceivingArea
	 * TemplateKey	W_ReceivingArea
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_ReceivingArea = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_ReceivingArea" , "W_ReceivingArea");

	/**
	 * ControlID	chk_ReceivingArea
	 * TemplateKey	W_ReceivingArea
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_ReceivingArea = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_ReceivingArea" , "W_ReceivingArea");

	/**
	 * ControlID	btn_Modify
	 * TemplateKey	W_ModifySet
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "W_ModifySet");

	/**
	 * ControlID	btn_Delete
	 * TemplateKey	W_Delete
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "W_Delete");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

}
//end of class

// $Id: AreaRegist.java 5826 2009-11-13 14:49:41Z fukuwa $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.arearegist;
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
 * @version $Revision: 5826 $, $Date: 2009-11-13 23:49:41 +0900 (金, 13 11 2009) $
 * @author  $Author: fukuwa $
 */
public class AreaRegist extends Page
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
	 * TemplateKey	W_Regist
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Regist");

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
	 * ControlID	lbl_Ast_AreaNo
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Ast_AreaNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Ast_AreaNo" , "W_Require");

	/**
	 * ControlID	txt_Area
	 * TemplateKey	W_Area
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Area = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Area" , "W_Area");

	/**
	 * ControlID	btn_Search
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Search" , "W_P_Search");

	/**
	 * ControlID	lbl_AreaName
	 * TemplateKey	W_AreaName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AreaName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AreaName" , "W_AreaName");

	/**
	 * ControlID	lbl_Ast_AreaName
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Ast_AreaName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Ast_AreaName" , "W_Require");

	/**
	 * ControlID	txt_AreaName
	 * TemplateKey	W_AreaName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_AreaName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_AreaName" , "W_AreaName");

	/**
	 * ControlID	lbl_AreaType
	 * TemplateKey	W_AreaType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AreaType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AreaType" , "W_AreaType");

	/**
	 * ControlID	pul_AreaType
	 * TemplateKey	W_AreaType
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_AreaType = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_AreaType" , "W_AreaType");

	/**
	 * ControlID	lbl_LocationType
	 * TemplateKey	W_LocationType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationType" , "W_LocationType");

	/**
	 * ControlID	pul_LocationType
	 * TemplateKey	W_LocationType
	 * ControlType	LinkedPullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_LocationType = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_LocationType" , "W_LocationType");

	/**
	 * ControlID	lbl_1
	 * TemplateKey	W_LocationStyle
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_1" , "W_LocationStyle");

	/**
	 * ControlID	lbl_2
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_2" , "W_Require");

	/**
	 * ControlID	txt_Location
	 * TemplateKey	W_Location
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Location = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Location" , "W_Location");

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
	 * ControlID	btn_Set
	 * TemplateKey	W_Set
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "W_Set");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

}
//end of class

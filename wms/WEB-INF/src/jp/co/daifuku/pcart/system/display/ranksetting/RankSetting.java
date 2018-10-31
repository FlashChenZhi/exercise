// $Id: RankSetting.java 3537 2009-03-16 11:49:21Z dmori $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.system.display.ranksetting;
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
 * @version $Revision: 3537 $, $Date: 2009-03-16 20:49:21 +0900 (月, 16 3 2009) $
 * @author  $Author: dmori $
 */
public class RankSetting extends Page
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
	 * ControlID	lbl_ConsignorCode
	 * TemplateKey	W_ConsignorCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");

	/**
	 * ControlID	lbl_ConsignorRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorRequire" , "W_Require");

	/**
	 * ControlID	txt_ConsignorCode
	 * TemplateKey	W_ConsignorCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");

	/**
	 * ControlID	lbl_Area
	 * TemplateKey	W_Area
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Area = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Area" , "W_Area");

	/**
	 * ControlID	pul_Area
	 * TemplateKey	W_Area
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Area = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Area" , "W_Area");

	/**
	 * ControlID	btn_Display
	 * TemplateKey	W_Display
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Display" , "W_Display");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

	/**
	 * ControlID	btn_Set
	 * TemplateKey	W_Set2
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "W_Set2");

	/**
	 * ControlID	btn_AutoCalculation
	 * TemplateKey	W_AutoCalculation
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AutoCalculation = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AutoCalculation" , "W_AutoCalculation");

	/**
	 * ControlID	btn_ListClear
	 * TemplateKey	W_ListClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ListClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ListClear" , "W_ListClear");

	/**
	 * ControlID	lbl_RankA
	 * TemplateKey	W_RankA
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RankA = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RankA" , "W_RankA");

	/**
	 * ControlID	lbl_PercentRankA
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PercentRankA = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PercentRankA" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_PercentA
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PercentA = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PercentA" , "W_Percent");

	/**
	 * ControlID	lbl_RankB
	 * TemplateKey	W_RankB
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RankB = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RankB" , "W_RankB");

	/**
	 * ControlID	lbl_PercentRankB
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PercentRankB = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PercentRankB" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_PercentB
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PercentB = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PercentB" , "W_Percent");

	/**
	 * ControlID	lbl_ConsignorCodeLower
	 * TemplateKey	W_ConsignorCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCodeLower = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCodeLower" , "W_ConsignorCode");

	/**
	 * ControlID	lbl_ConsignorCodeDisp
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCodeDisp = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCodeDisp" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_AreaLower
	 * TemplateKey	W_Area
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AreaLower = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AreaLower" , "W_Area");

	/**
	 * ControlID	lbl_AreaDisp
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AreaDisp = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AreaDisp" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_LotNumber_h
	 * TemplateKey	W_LotNumber_h
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LotNumber_h = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LotNumber_h" , "W_LotNumber_h");

	/**
	 * ControlID	txt_LotPerH
	 * TemplateKey	W_LotPerHour
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_LotPerH = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_LotPerH" , "W_LotPerHour");

	/**
	 * ControlID	lbl_OrderNumber_h
	 * TemplateKey	W_OrderNumber_h
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OrderNumber_h = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OrderNumber_h" , "W_OrderNumber_h");

	/**
	 * ControlID	txt_OrderPerH
	 * TemplateKey	W_OrderPerHour
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_OrderPerH = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_OrderPerH" , "W_OrderPerHour");

	/**
	 * ControlID	lbl_LineNumber_h
	 * TemplateKey	W_LineNumber_h
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LineNumber_h = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LineNumber_h" , "W_LineNumber_h");

	/**
	 * ControlID	txt_LinePerH
	 * TemplateKey	W_LinePerHour
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_LinePerH = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_LinePerH" , "W_LinePerHour");

	/**
	 * ControlID	lst_RankSet
	 * TemplateKey	W_RankSet
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_RankSet = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_RankSet" , "W_RankSet");

}
//end of class

// $Id: PCTSystemSetting.java 3810 2009-03-26 03:36:24Z ose $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.system.display.pctsystemsetting;
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
 * @version $Revision: 3810 $, $Date: 2009-03-26 12:36:24 +0900 (木, 26 3 2009) $
 * @author  $Author: ose $
 */
public class PCTSystemSetting extends Page
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
	 * ControlID	lbl_WorkStdNumSetType
	 * TemplateKey	W_WorkStdNumSetType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkStdNumSetType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkStdNumSetType" , "W_WorkStdNumSetType");

	/**
	 * ControlID	rdo_WorkRank_Manual
	 * TemplateKey	W_WorkRank_Manual
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_WorkRank_Manual = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_WorkRank_Manual" , "W_WorkRank_Manual");

	/**
	 * ControlID	rdo_WorkRank_Auto
	 * TemplateKey	W_WorkRank_Auto
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_WorkRank_Auto = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_WorkRank_Auto" , "W_WorkRank_Auto");

	/**
	 * ControlID	lbl_RankAStdNumber
	 * TemplateKey	W_RankAStdNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RankAStdNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RankAStdNumber" , "W_RankAStdNumber");

	/**
	 * ControlID	lbl_RankARequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RankARequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RankARequire" , "W_Require");

	/**
	 * ControlID	txt_RankAStdNumber
	 * TemplateKey	W_RankAStdNumber
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RankAStdNumber = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RankAStdNumber" , "W_RankAStdNumber");

	/**
	 * ControlID	lbl_Percent_RankA
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Percent_RankA = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Percent_RankA" , "W_Percent");

	/**
	 * ControlID	lbl_RankBStdNumber
	 * TemplateKey	W_RankBStdNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RankBStdNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RankBStdNumber" , "W_RankBStdNumber");

	/**
	 * ControlID	lbl_RankBRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RankBRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RankBRequire" , "W_Require");

	/**
	 * ControlID	txt_RankBStdNumber
	 * TemplateKey	W_RankBStdNumber
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RankBStdNumber = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RankBStdNumber" , "W_RankBStdNumber");

	/**
	 * ControlID	lbl_Percent_RankB
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Percent_RankB = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Percent_RankB" , "W_Percent");

	/**
	 * ControlID	lbl_WeightErrorInit
	 * TemplateKey	W_WeightErrorInit
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WeightErrorInit = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WeightErrorInit" , "W_WeightErrorInit");

	/**
	 * ControlID	lbl_WeightErrorRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WeightErrorRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WeightErrorRequire" , "W_Require");

	/**
	 * ControlID	txt_WeightErrorRateInit
	 * TemplateKey	W_WeightErrorRate
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_WeightErrorRateInit = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_WeightErrorRateInit" , "W_WeightErrorRate");

	/**
	 * ControlID	lbl_Percent_WeightErrorRate
	 * TemplateKey	W_Percent
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Percent_WeightErrorRate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Percent_WeightErrorRate" , "W_Percent");

	/**
	 * ControlID	lbl_Range1_49
	 * TemplateKey	W_Range1_49
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range1_49 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range1_49" , "W_Range1_49");

	/**
	 * ControlID	lbl_OriconMaxWeight
	 * TemplateKey	W_OriconMaxWeight
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OriconMaxWeight = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OriconMaxWeight" , "W_OriconMaxWeight");

	/**
	 * ControlID	lbl_OrderMaxWeightRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OrderMaxWeightRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OrderMaxWeightRequire" , "W_Require");

	/**
	 * ControlID	txt_OriconMaxWeight
	 * TemplateKey	W_OriconMaxWeight
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_OriconMaxWeight = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_OriconMaxWeight" , "W_OriconMaxWeight");

	/**
	 * ControlID	lbl_Gram_OrderMax
	 * TemplateKey	W_Gram
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Gram_OrderMax = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Gram_OrderMax" , "W_Gram");

	/**
	 * ControlID	lbl_CenterName
	 * TemplateKey	W_CenterName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CenterName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CenterName" , "W_CenterName");

	/**
	 * ControlID	txt_CenterName
	 * TemplateKey	W_CenterName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CenterName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CenterName" , "W_CenterName");

	/**
	 * ControlID	lbl_ItemDataload
	 * TemplateKey	W_ItemDataload
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemDataload = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemDataload" , "W_ItemDataload");

	/**
	 * ControlID	txt_ItemDataload
	 * TemplateKey	W_ItemDataload
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemDataload = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemDataload" , "W_ItemDataload");

	/**
	 * ControlID	btn_NoProcess
	 * TemplateKey	W_NoProcess
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_NoProcess = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_NoProcess" , "W_NoProcess");

	/**
	 * ControlID	btn_Set
	 * TemplateKey	W_Set2
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "W_Set2");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

}
//end of class

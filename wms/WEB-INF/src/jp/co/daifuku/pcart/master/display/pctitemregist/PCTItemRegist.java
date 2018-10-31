// $Id: PCTItemRegist.java 3419 2009-03-13 07:11:24Z ose $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.master.display.pctitemregist;
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
 * @version $Revision: 3419 $, $Date: 2009-03-13 16:11:24 +0900 (金, 13 3 2009) $
 * @author  $Author: ose $
 */
public class PCTItemRegist extends Page
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
	 * ControlID	lbl_ConsignorCode
	 * TemplateKey	W_ConsignorCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");

	/**
	 * ControlID	lbl_RequireConsignor
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireConsignor = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireConsignor" , "W_Require");

	/**
	 * ControlID	txt_ConsignorCode
	 * TemplateKey	W_ConsignorCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");

	/**
	 * ControlID	lbl_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	lbl_RequireItem
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireItem = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireItem" , "W_Require");

	/**
	 * ControlID	txt_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	btn_SearchItem
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchItem = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchItem" , "W_P_Search");

	/**
	 * ControlID	lbl_ItemName
	 * TemplateKey	W_ItemName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemName" , "W_ItemName");

	/**
	 * ControlID	txt_ItemName
	 * TemplateKey	W_ItemNamePCT
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemName" , "W_ItemNamePCT");

	/**
	 * ControlID	lbl_JanCode
	 * TemplateKey	W_JanCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JanCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JanCode" , "W_JanCode");

	/**
	 * ControlID	txt_JanCode
	 * TemplateKey	W_JanCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_JanCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_JanCode" , "W_JanCode");

	/**
	 * ControlID	lbl_LotEnteringQty
	 * TemplateKey	W_LotEnteringQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LotEnteringQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LotEnteringQty" , "W_LotEnteringQty");

	/**
	 * ControlID	lbl_5
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_5 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_5" , "W_Require");

	/**
	 * ControlID	txt_LotEnteringQty
	 * TemplateKey	W_LotEnteringQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_LotEnteringQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_LotEnteringQty" , "W_LotEnteringQty");

	/**
	 * ControlID	lbl_CasITF
	 * TemplateKey	W_CasITF
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CasITF = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CasITF" , "W_CasITF");

	/**
	 * ControlID	txt_CaseITF
	 * TemplateKey	W_CaseITF
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CaseITF = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CaseITF" , "W_CaseITF");

	/**
	 * ControlID	lbl_BundleItf
	 * TemplateKey	W_BundleItf
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_BundleItf = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_BundleItf" , "W_BundleItf");

	/**
	 * ControlID	txt_BundleItf
	 * TemplateKey	W_BundleItf
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_BundleItf = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_BundleItf" , "W_BundleItf");

	/**
	 * ControlID	lbl_UnitWeight
	 * TemplateKey	W_UnitWeight
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UnitWeight = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UnitWeight" , "W_UnitWeight");

	/**
	 * ControlID	txt_UnitWeight
	 * TemplateKey	W_UnitWeight
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_UnitWeight = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_UnitWeight" , "W_UnitWeight");

	/**
	 * ControlID	lbl_WeightErrorRate
	 * TemplateKey	W_WeightErrorRate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WeightErrorRate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WeightErrorRate" , "W_WeightErrorRate");

	/**
	 * ControlID	txt_WeightErrorRate
	 * TemplateKey	W_WeightErrorRate
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_WeightErrorRate = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_WeightErrorRate" , "W_WeightErrorRate");

	/**
	 * ControlID	lbl_Range1_49
	 * TemplateKey	W_Range1_49
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range1_49 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range1_49" , "W_Range1_49");

	/**
	 * ControlID	lbl_MaxCheckUnitNumber
	 * TemplateKey	W_MaxCheckUnitNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxCheckUnitNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxCheckUnitNumber" , "W_MaxCheckUnitNumber");

	/**
	 * ControlID	txt_MaxCheckUnitNumber
	 * TemplateKey	W_MaxCheckUnitNumber
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_MaxCheckUnitNumber = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_MaxCheckUnitNumber" , "W_MaxCheckUnitNumber");

	/**
	 * ControlID	lbl_Message
	 * TemplateKey	T_Message
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Message = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Message" , "T_Message");

	/**
	 * ControlID	txt_Message
	 * TemplateKey	W_Message
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Message = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Message" , "W_Message");

	/**
	 * ControlID	lbl_LocationNo
	 * TemplateKey	W_LocationNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationNo" , "W_LocationNo");

	/**
	 * ControlID	txt_LocationNo
	 * TemplateKey	W_LocationNo
	 * ControlType	FormatTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FormatTextBox txt_LocationNo = jp.co.daifuku.bluedog.ui.control.FormatTextBoxFactory.getInstance("txt_LocationNo" , "W_LocationNo");

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

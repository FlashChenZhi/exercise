// $Id: PCTItemModify2.java 3429 2009-03-13 07:22:48Z ose $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.master.display.pctitemmodify;
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
 * @version $Revision: 3429 $, $Date: 2009-03-13 16:22:48 +0900 (金, 13 3 2009) $
 * @author  $Author: ose $
 */
public class PCTItemModify2 extends Page
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
	 * ControlID	lbl_ConsignorCode
	 * TemplateKey	W_ConsignorCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorCode" , "W_ConsignorCode");

	/**
	 * ControlID	lbl_InConsignorCode
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InConsignorCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InConsignorCode" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	lbl_InItemCode
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InItemCode" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_LotEnteringQty
	 * TemplateKey	W_LotEnteringQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LotEnteringQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LotEnteringQty" , "W_LotEnteringQty");

	/**
	 * ControlID	lbl_InLotEnteringQty
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InLotEnteringQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InLotEnteringQty" , "W_In_JavaSet");

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
	 * ControlID	lbl_CaseITF
	 * TemplateKey	W_CaseITF
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseITF = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseITF" , "W_CaseITF");

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
	 * ControlID	lbl_ItemImageSet
	 * TemplateKey	W_ItemImageSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemImageSet = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemImageSet" , "W_ItemImageSet");

	/**
	 * ControlID	txt_ItemImageSet
	 * TemplateKey	W_ItemImageSet
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemImageSet = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemImageSet" , "W_ItemImageSet");

	/**
	 * ControlID	chk_Delete
	 * TemplateKey	P_Delete
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Delete = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Delete" , "P_Delete");

	/**
	 * ControlID	btn_ModifySet
	 * TemplateKey	W_ModifySet
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ModifySet = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ModifySet" , "W_ModifySet");

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

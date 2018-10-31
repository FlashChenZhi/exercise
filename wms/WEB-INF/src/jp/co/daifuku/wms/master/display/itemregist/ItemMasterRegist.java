// $Id: ItemMasterRegist.java 4482 2009-06-23 01:07:47Z kishimoto $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.itemregist;
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
 * @version $Revision: 4482 $, $Date: 2009-06-23 10:07:47 +0900 (火, 23 6 2009) $
 * @author  $Author: kishimoto $
 */
public class ItemMasterRegist extends Page
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
	 * ControlID	lbl_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	lbl_Require
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Require = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Require" , "W_Require");

	/**
	 * ControlID	txt_ItemCode
	 * TemplateKey	W_ItemCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode" , "W_ItemCode");

	/**
	 * ControlID	btn_Search
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Search" , "W_P_Search");

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
	 * ControlID	lbl_SoftZone
	 * TemplateKey	W_SoftZone
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SoftZone = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SoftZone" , "W_SoftZone");

	/**
	 * ControlID	pul_SoftZone
	 * TemplateKey	W_SoftZone
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_SoftZone = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_SoftZone" , "W_SoftZone");

	/**
	 * ControlID	lbl_CaseEnteringQty
	 * TemplateKey	W_EnteringQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CaseEnteringQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CaseEnteringQty" , "W_EnteringQty");

	/**
	 * ControlID	txt_EnteringQty
	 * TemplateKey	W_EnteringQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_EnteringQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_EnteringQty" , "W_EnteringQty");

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
	 * ControlID	txt_CaseItf
	 * TemplateKey	W_CaseITF
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CaseItf = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CaseItf" , "W_CaseITF");

	/**
	 * ControlID	lbl_UpperStockQty
	 * TemplateKey	W_UpperQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UpperStockQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UpperStockQty" , "W_UpperQty");

	/**
	 * ControlID	txt_UpperStockQty
	 * TemplateKey	W_UpperQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_UpperStockQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_UpperStockQty" , "W_UpperQty");

	/**
	 * ControlID	lbl_LowerStockQty
	 * TemplateKey	W_LowerQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LowerStockQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LowerStockQty" , "W_LowerQty");

	/**
	 * ControlID	txt_LowerStockQty
	 * TemplateKey	W_LowerQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_LowerStockQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_LowerStockQty" , "W_LowerQty");

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

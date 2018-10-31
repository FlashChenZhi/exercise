// $Id: FaItemInquiry.java 5228 2009-10-22 06:21:49Z kumano $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.faiteminquiry;
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
 * @version $Revision: 5228 $, $Date: 2009-10-22 15:21:49 +0900 (木, 22 10 2009) $
 * @author  $Author: kumano $
 */
public class FaItemInquiry extends Page
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
	 * ControlID	lbl_FromItemCode
	 * TemplateKey	W_FromItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromItemCode" , "W_FromItemCode");

	/**
	 * ControlID	txt_FromItemCode
	 * TemplateKey	W_ItemCode_EntEvent
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FromItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FromItemCode" , "W_ItemCode_EntEvent");

	/**
	 * ControlID	btn_P_FromSearchItemCode
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_FromSearchItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_FromSearchItemCode" , "W_P_Search");

	/**
	 * ControlID	lbl_FromItemName
	 * TemplateKey	W_FromItemName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromItemName" , "W_FromItemName");

	/**
	 * ControlID	txt_FromItemName
	 * TemplateKey	W_ItemName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FromItemName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FromItemName" , "W_ItemName");

	/**
	 * ControlID	btn_P_FromSearchItemName
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_FromSearchItemName = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_FromSearchItemName" , "W_P_Search");

	/**
	 * ControlID	lbl_ToItemCode
	 * TemplateKey	W_ToItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ToItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ToItemCode" , "W_ToItemCode");

	/**
	 * ControlID	txt_ToItemCode
	 * TemplateKey	W_ItemCode_EntEvent
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ToItemCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ToItemCode" , "W_ItemCode_EntEvent");

	/**
	 * ControlID	btn_P_ToSearchItemCode
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_ToSearchItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_ToSearchItemCode" , "W_P_Search");

	/**
	 * ControlID	lbl_ToItemName
	 * TemplateKey	W_ToItemName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ToItemName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ToItemName" , "W_ToItemName");

	/**
	 * ControlID	txt_ToItemName
	 * TemplateKey	W_ItemName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ToItemName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ToItemName" , "W_ItemName");

	/**
	 * ControlID	btn_P_ToSearchItemName
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_ToSearchItemName = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_ToSearchItemName" , "W_P_Search");

	/**
	 * ControlID	lbl_StockExistence
	 * TemplateKey	W_StockExistence
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockExistence = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockExistence" , "W_StockExistence");

	/**
	 * ControlID	rdo_StockExistence_existence
	 * TemplateKey	W_StockExistence_existence
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_StockExistence_existence = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_StockExistence_existence" , "W_StockExistence_existence");

	/**
	 * ControlID	rdo_StockExistence_Noexistence
	 * TemplateKey	W_StockExistence_Noexistence
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_StockExistence_Noexistence = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_StockExistence_Noexistence" , "W_StockExistence_Noexistence");

	/**
	 * ControlID	rdo_StockExistence_All
	 * TemplateKey	W_StockExistence_All
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_StockExistence_All = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_StockExistence_All" , "W_StockExistence_All");

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
	 * ControlID	pgr_Pager
	 * TemplateKey	Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_Pager = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_Pager" , "Pager");

	/**
	 * ControlID	lst_FaItemInquiry
	 * TemplateKey	W_FaItemInquiry
	 * ControlType	ScrollListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_FaItemInquiry = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_FaItemInquiry" , "W_FaItemInquiry");

}
//end of class

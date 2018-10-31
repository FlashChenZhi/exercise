// $Id: skelten.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stock.display.faitemworkinginquiry;
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
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class FaItemWorkingInquiry extends Page
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
	 * ControlID	lbl_FromItemCode
	 * TemplateKey	W_FromItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromItemCode" , "W_FromItemCode");

	/**
	 * ControlID	txt_FromItemCode_EntEvent
	 * TemplateKey	W_ItemCode_EntEvent
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FromItemCode_EntEvent = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FromItemCode_EntEvent" , "W_ItemCode_EntEvent");

	/**
	 * ControlID	btn_SearchFromItemCode
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchFromItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchFromItemCode" , "W_P_Search");

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
	 * ControlID	btn_SearchFromItemName
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchFromItemName = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchFromItemName" , "W_P_Search");

	/**
	 * ControlID	lbl_ToItemCode
	 * TemplateKey	W_ToItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ToItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ToItemCode" , "W_ToItemCode");

	/**
	 * ControlID	txt_ToItemCode_EntEvent
	 * TemplateKey	W_ItemCode_EntEvent
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ToItemCode_EntEvent = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ToItemCode_EntEvent" , "W_ItemCode_EntEvent");

	/**
	 * ControlID	btn_SearchToItemCode
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchToItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchToItemCode" , "W_P_Search");

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
	 * ControlID	btn_SearchToItemName
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchToItemName = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchToItemName" , "W_P_Search");

	/**
	 * ControlID	lbl_Type
	 * TemplateKey	W_Type
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Type = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Type" , "W_Type");

	/**
	 * ControlID	rdo_Search_CountByItemCode
	 * TemplateKey	W_Search_CountByItemCode
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Search_CountByItemCode = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Search_CountByItemCode" , "W_Search_CountByItemCode");

	/**
	 * ControlID	rdo_Search_CountByItemCodeAndL
	 * TemplateKey	W_Search_CountByItemCodeAndLotNo
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Search_CountByItemCodeAndL = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Search_CountByItemCodeAndL" , "W_Search_CountByItemCodeAndLotNo");

	/**
	 * ControlID	rdo_Search_DetailsPerItemCode
	 * TemplateKey	W_Search_DetailsPerItemCode
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Search_DetailsPerItemCode = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Search_DetailsPerItemCode" , "W_Search_DetailsPerItemCode");

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
	 * ControlID	pager
	 * TemplateKey	Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pager = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pager" , "Pager");

	/**
	 * ControlID	lst_FaStockInquiry
	 * TemplateKey	W_FaStockInquiry
	 * ControlType	ScrollListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_FaStockInquiry = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_FaStockInquiry" , "W_FaStockInquiry");

}
//end of class

// $Id: RftAdd.java 2986 2009-02-03 08:09:46Z kumano $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.system.display.rftadd;
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
 * @version $Revision: 2986 $, $Date: 2009-02-03 17:09:46 +0900 (火, 03 2 2009) $
 * @author  $Author: kumano $
 */
public class RftAdd extends Page
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
	 * ControlID	tab_Set
	 * TemplateKey	W_Set
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Set = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Set" , "W_Set");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_MachineNumber
	 * TemplateKey	AST_MachineNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MachineNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MachineNumber" , "AST_MachineNumber");

	/**
	 * ControlID	lbl_RequirePlanDate
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequirePlanDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequirePlanDate" , "W_Require");

	/**
	 * ControlID	txt_MachineNumber
	 * TemplateKey	W_MachineNumber
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_MachineNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_MachineNumber" , "W_MachineNumber");

	/**
	 * ControlID	lbl_RftAssort
	 * TemplateKey	W_RftAssort
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RftAssort = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RftAssort" , "W_RftAssort");

	/**
	 * ControlID	pul_RftAssort
	 * TemplateKey	W_RftAssort
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_RftAssort = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_RftAssort" , "W_RftAssort");

	/**
	 * ControlID	btn_Entry
	 * TemplateKey	W_Set5
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Entry = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Entry" , "W_Set5");

	/**
	 * ControlID	btn_Modify
	 * TemplateKey	T_Modify
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "T_Modify");

	/**
	 * ControlID	btn_Delete
	 * TemplateKey	W_Delet
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "W_Delet");

	/**
	 * ControlID	lbl_IpAddress
	 * TemplateKey	W_IpAddress
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_IpAddress = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_IpAddress" , "W_IpAddress");

	/**
	 * ControlID	lbl_6
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_6 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_6" , "W_Require");

	/**
	 * ControlID	txt_IpAddress
	 * TemplateKey	T_IpAddress
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_IpAddress = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_IpAddress" , "T_IpAddress");

	/**
	 * ControlID	lbl_language
	 * TemplateKey	W_language
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_language = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_language" , "W_language");

	/**
	 * ControlID	rdo_Japanese
	 * TemplateKey	W_Japanese
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Japanese = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Japanese" , "W_Japanese");

	/**
	 * ControlID	rdo_English
	 * TemplateKey	W_English
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_English = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_English" , "W_English");

	/**
	 * ControlID	rdo_Chinese
	 * TemplateKey	W_Chinese
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Chinese = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Chinese" , "W_Chinese");

	/**
	 * ControlID	lbl_Model
	 * TemplateKey	W_Model
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Model = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Model" , "W_Model");

	/**
	 * ControlID	pul_Model
	 * TemplateKey	W_Model
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Model = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Model" , "W_Model");

	/**
	 * ControlID	lbl_Menu
	 * TemplateKey	W_Menu
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Menu = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Menu" , "W_Menu");

	/**
	 * ControlID	chk_WorkKind_TC_Receiving
	 * TemplateKey	W_WorkKind_TC_Receiving
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_TC_Receiving = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_TC_Receiving" , "W_WorkKind_TC_Receiving");

	/**
	 * ControlID	chk_WorkKind_DC_Receiving
	 * TemplateKey	W_WorkKind_DC_Receiving
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_DC_Receiving = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_DC_Receiving" , "W_WorkKind_DC_Receiving");

	/**
	 * ControlID	chk_WorkKind_Storage_Receiving
	 * TemplateKey	W_WorkKind_Storage_Receiving_Area
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_Storage_Receiving = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_Storage_Receiving" , "W_WorkKind_Storage_Receiving_Area");

	/**
	 * ControlID	chk_WorkKind_Storage
	 * TemplateKey	W_WorkKind_Storage
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_Storage = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_Storage" , "W_WorkKind_Storage");

	/**
	 * ControlID	chk_WorkKind_Order_Retrieval
	 * TemplateKey	W_WorkKind_Order_Retrieval
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_Order_Retrieval = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_Order_Retrieval" , "W_WorkKind_Order_Retrieval");

	/**
	 * ControlID	chk_WorkKind_Sort
	 * TemplateKey	W_WorkKind_Sort
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_Sort = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_Sort" , "W_WorkKind_Sort");

	/**
	 * ControlID	chk_WorkKind_Shipping_Pick
	 * TemplateKey	W_WorkKind_Shipping_Pick
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_Shipping_Pick = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_Shipping_Pick" , "W_WorkKind_Shipping_Pick");

	/**
	 * ControlID	chk_WorkKind_Shipping_Loading
	 * TemplateKey	W_WorkKind_Shipping_Loading
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_Shipping_Loading = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_Shipping_Loading" , "W_WorkKind_Shipping_Loading");

	/**
	 * ControlID	chk_WorkKind_NoPlanStorage
	 * TemplateKey	W_WorkKind_NoPlanStorage
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_NoPlanStorage = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_NoPlanStorage" , "W_WorkKind_NoPlanStorage");

	/**
	 * ControlID	chk_WorkKind_NoPlanRetrieval
	 * TemplateKey	W_WorkKind_NoPlanRetrieval
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_NoPlanRetrieval = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_NoPlanRetrieval" , "W_WorkKind_NoPlanRetrieval");

	/**
	 * ControlID	chk_WorkKind_Inventry
	 * TemplateKey	W_WorkKind_Inventry
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_Inventry = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_Inventry" , "W_WorkKind_Inventry");

	/**
	 * ControlID	chk_WorkKind_RelocatinoRetriev
	 * TemplateKey	W_WorkKind_RelocatinoRetrieval
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_RelocatinoRetriev = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_RelocatinoRetriev" , "W_WorkKind_RelocatinoRetrieval");

	/**
	 * ControlID	chk_WorkKind_RelocatinoStorage
	 * TemplateKey	W_WorkKind_RelocatinoStorage
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_RelocatinoStorage = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_RelocatinoStorage" , "W_WorkKind_RelocatinoStorage");

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

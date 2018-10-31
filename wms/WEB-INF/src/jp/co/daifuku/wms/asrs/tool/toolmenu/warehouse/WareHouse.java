// $Id: WareHouse.java 5299 2009-10-28 05:34:56Z ota $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.warehouse;
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
 * @version $Revision: 5299 $, $Date: 2009-10-28 14:34:56 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 */
public class WareHouse extends Page
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
	 * ControlID	tab_Create
	 * TemplateKey	AST_Create
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Create = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Create" , "AST_Create");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_WareHouseNumber
	 * TemplateKey	AST_WareHouseNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouseNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouseNumber" , "AST_WareHouseNumber");

	/**
	 * ControlID	txt_WareHouseNumber
	 * TemplateKey	AST_WareHouseNumber
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_WareHouseNumber = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_WareHouseNumber" , "AST_WareHouseNumber");

	/**
	 * ControlID	lbl_StationNumber
	 * TemplateKey	AST_StationNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StationNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StationNumber" , "AST_StationNumber");

	/**
	 * ControlID	txt_StNumber
	 * TemplateKey	AST_StNumber
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StNumber" , "AST_StNumber");

	/**
	 * ControlID	lbl_WareHouseName
	 * TemplateKey	AST_WareHouseName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouseName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouseName" , "AST_WareHouseName");

	/**
	 * ControlID	txt_WareHouseName
	 * TemplateKey	AST_WareHouseName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_WareHouseName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_WareHouseName" , "AST_WareHouseName");

	/**
	 * ControlID	lbl_AWCUseType
	 * TemplateKey	AST_AWCUseType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AWCUseType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AWCUseType" , "AST_AWCUseType");

	/**
	 * ControlID	rdo_Open
	 * TemplateKey	AST_Open
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Open = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Open" , "AST_Open");

	/**
	 * ControlID	rdo_Close
	 * TemplateKey	AST_Close
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Close = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Close" , "AST_Close");

	/**
	 * ControlID	lbl_FreeAllocationType
	 * TemplateKey	AST_FreeAllocationType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FreeAllocationType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FreeAllocationType" , "AST_FreeAllocationType");

	/**
	 * ControlID	rdo_NotFreeAllocation
	 * TemplateKey	AST_NotFreeAllocation
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_NotFreeAllocation = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_NotFreeAllocation" , "AST_NotFreeAllocation");

	/**
	 * ControlID	rdo_FreeAllocation
	 * TemplateKey	AST_FreeAllocation
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FreeAllocation = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FreeAllocation" , "AST_FreeAllocation");

	/**
	 * ControlID	lbl_LocationSearchType
	 * TemplateKey	AST_LocationSearchType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LocationSearchType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LocationSearchType" , "AST_LocationSearchType");

	/**
	 * ControlID	rdo_AislePriority
	 * TemplateKey	AST_AislePriority
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AislePriority = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AislePriority" , "AST_AislePriority");

	/**
	 * ControlID	rdo_ZonePriority
	 * TemplateKey	AST_ZonePriority
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ZonePriority = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ZonePriority" , "AST_ZonePriority");

	/**
	 * ControlID	lbl_AisleSearchType
	 * TemplateKey	AST_AisleSearchType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AisleSearchType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AisleSearchType" , "AST_AisleSearchType");

	/**
	 * ControlID	rdo_Ascending
	 * TemplateKey	AST_Ascending
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Ascending = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Ascending" , "AST_Ascending");

	/**
	 * ControlID	rdo_Descending
	 * TemplateKey	AST_Descending
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Descending = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Descending" , "AST_Descending");

	/**
	 * ControlID	lbl_MaxMixedQty
	 * TemplateKey	AST_MaxMixedQty
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxMixedQty = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxMixedQty" , "AST_MaxMixedQty");

	/**
	 * ControlID	txt_MaxMixedQty
	 * TemplateKey	AST_MaxMixedQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_MaxMixedQty = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_MaxMixedQty" , "AST_MaxMixedQty");

	/**
	 * ControlID	lbl_AreaNo
	 * TemplateKey	AST_AreaNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AreaNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AreaNo" , "AST_AreaNo");

	/**
	 * ControlID	txt_AreaNo
	 * TemplateKey	AST_AreaNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_AreaNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_AreaNo" , "AST_AreaNo");

	/**
	 * ControlID	lbl_VacantSearchType
	 * TemplateKey	AST_VacantSearchType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_VacantSearchType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_VacantSearchType" , "AST_VacantSearchType");

	/**
	 * ControlID	rdo_VacantSearchTypeASRSLevelH
	 * TemplateKey	AST_VacantSearchTypeASRSLevelHP
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_VacantSearchTypeASRSLevelH = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_VacantSearchTypeASRSLevelH" , "AST_VacantSearchTypeASRSLevelHP");

	/**
	 * ControlID	rdo_VacantSearchTypeAsrsLevelO
	 * TemplateKey	AST_VacantSearchTypeAsrsLevelOP
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_VacantSearchTypeAsrsLevelO = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_VacantSearchTypeAsrsLevelO" , "AST_VacantSearchTypeAsrsLevelOP");

	/**
	 * ControlID	rdo_VacantSearchTypeAsrsBayHP
	 * TemplateKey	AST_VacantSearchTypeAsrsBayHP
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_VacantSearchTypeAsrsBayHP = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_VacantSearchTypeAsrsBayHP" , "AST_VacantSearchTypeAsrsBayHP");

	/**
	 * ControlID	rdo_VacantSearchTypeAsrsBayOP
	 * TemplateKey	AST_VacantSearchTypeAsrsBayOP
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_VacantSearchTypeAsrsBayOP = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_VacantSearchTypeAsrsBayOP" , "AST_VacantSearchTypeAsrsBayOP");

	/**
	 * ControlID	lbl_TemporaryAreaType
	 * TemplateKey	AST_TemporaryAreaType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TemporaryAreaType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TemporaryAreaType" , "AST_TemporaryAreaType");

	/**
	 * ControlID	rdo_NotCreate
	 * TemplateKey	AST_NotCreate
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_NotCreate = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_NotCreate" , "AST_NotCreate");

	/**
	 * ControlID	rdo_TemporaryArea
	 * TemplateKey	AST_TemporaryArea
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TemporaryArea = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TemporaryArea" , "AST_TemporaryArea");

	/**
	 * ControlID	lbl_TemporaryArea
	 * TemplateKey	AST_TemporaryArea
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TemporaryArea = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TemporaryArea" , "AST_TemporaryArea");

	/**
	 * ControlID	txt_TemporaryArea
	 * TemplateKey	AST_TemporaryArea
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TemporaryArea = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TemporaryArea" , "AST_TemporaryArea");

	/**
	 * ControlID	btn_Add
	 * TemplateKey	AST_Add
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Add = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Add" , "AST_Add");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	AST_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "AST_Clear");

	/**
	 * ControlID	btn_Commit
	 * TemplateKey	AST_Commit
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "AST_Commit");

	/**
	 * ControlID	btn_Cancel
	 * TemplateKey	AST_Cancel
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "AST_Cancel");

	/**
	 * ControlID	lst_WareHouse
	 * TemplateKey	AST_S_WareHouse
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_WareHouse = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_WareHouse" , "AST_S_WareHouse");

}
//end of class

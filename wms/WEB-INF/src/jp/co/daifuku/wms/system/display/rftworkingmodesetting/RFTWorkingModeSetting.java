// $Id: RFTWorkingModeSetting.java 2829 2009-01-20 10:35:17Z arai $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.system.display.rftworkingmodesetting;
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
 * @version $Revision: 2829 $, $Date: 2009-01-20 19:35:17 +0900 (火, 20 1 2009) $
 * @author  $Author: arai $
 */
public class RFTWorkingModeSetting extends Page
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
	 * ControlID	lbl_MachineNumber
	 * TemplateKey	AST_MachineNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MachineNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MachineNumber" , "AST_MachineNumber");

	/**
	 * ControlID	pul_RFTNo
	 * TemplateKey	W_RFTNo
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_RFTNo = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_RFTNo" , "W_RFTNo");

	/**
	 * ControlID	lbl_2
	 * TemplateKey	W_WorkKind
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_2" , "W_WorkKind");

	/**
	 * ControlID	btn_AllCheck
	 * TemplateKey	W_AllCheck
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllCheck = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllCheck" , "W_AllCheck");

	/**
	 * ControlID	btn_AllCheckClear
	 * TemplateKey	W_AllCheckClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllCheckClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllCheckClear" , "W_AllCheckClear");

	/**
	 * ControlID	chk_WorkKind_Receiving
	 * TemplateKey	W_WorkKind_Receiving
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_Receiving = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_Receiving" , "W_WorkKind_Receiving");

	/**
	 * ControlID	chk_WorkKind_Storage
	 * TemplateKey	W_WorkKind_Storage
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_Storage = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_Storage" , "W_WorkKind_Storage");

	/**
	 * ControlID	chk_WorkKind_Retrieval
	 * TemplateKey	W_WorkKind_Retrieval
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_Retrieval = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_Retrieval" , "W_WorkKind_Retrieval");

	/**
	 * ControlID	chk_WorkKind_Sort
	 * TemplateKey	W_WorkKind_Sort
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_Sort = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_Sort" , "W_WorkKind_Sort");

	/**
	 * ControlID	chk_WorkKind_Shipping
	 * TemplateKey	W_WorkKind_Shipping
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_Shipping = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_Shipping" , "W_WorkKind_Shipping");

	/**
	 * ControlID	chk_WorkKind_RelocatinoStorage
	 * TemplateKey	W_WorkKind_RelocatinoStorage
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_RelocatinoStorage = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_RelocatinoStorage" , "W_WorkKind_RelocatinoStorage");

	/**
	 * ControlID	chk_WorkKind_RelocatinoRetriev
	 * TemplateKey	W_WorkKind_RelocatinoRetrieval
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_RelocatinoRetriev = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_RelocatinoRetriev" , "W_WorkKind_RelocatinoRetrieval");

	/**
	 * ControlID	chk_WorkKind_Inventry
	 * TemplateKey	W_WorkKind_Inventry
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_WorkKind_Inventry = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_WorkKind_Inventry" , "W_WorkKind_Inventry");

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
	 * ControlID	lbl_ConsignorSkip
	 * TemplateKey	W_ConsignorSkip
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorSkip = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorSkip" , "W_ConsignorSkip");

	/**
	 * ControlID	txt_ConsignorCode
	 * TemplateKey	W_ConsignorCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ConsignorCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ConsignorCode" , "W_ConsignorCode");

	/**
	 * ControlID	lbl_ITFtoJAN
	 * TemplateKey	W_ITFtoJAN
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ITFtoJAN = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ITFtoJAN" , "W_ITFtoJAN");

	/**
	 * ControlID	rdo_ITFtoJAN_ON
	 * TemplateKey	W_ITFtoJAN_ON
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ITFtoJAN_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ITFtoJAN_ON" , "W_ITFtoJAN_ON");

	/**
	 * ControlID	rdo_ITFtoJAN_OFF
	 * TemplateKey	W_ITFtoJAN_OFF
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ITFtoJAN_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ITFtoJAN_OFF" , "W_ITFtoJAN_OFF");

	/**
	 * ControlID	lbl_DefaultCasePieceMode
	 * TemplateKey	W_DefaultCasePieceMode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DefaultCasePieceMode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DefaultCasePieceMode" , "W_DefaultCasePieceMode");

	/**
	 * ControlID	rdo_CasePieseMode_Case
	 * TemplateKey	W_CasePieseMode_Case
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CasePieseMode_Case = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CasePieseMode_Case" , "W_CasePieseMode_Case");

	/**
	 * ControlID	rdo_CasePieseMode_Piese
	 * TemplateKey	W_CasePieseMode_Piese
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_CasePieseMode_Piese = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_CasePieseMode_Piese" , "W_CasePieseMode_Piese");

	/**
	 * ControlID	lbl_5
	 * TemplateKey	W_InspectionMode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_5 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_5" , "W_InspectionMode");

	/**
	 * ControlID	rdo_InspectionMode_ON
	 * TemplateKey	W_InspectionMode_ON
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_InspectionMode_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_InspectionMode_ON" , "W_InspectionMode_ON");

	/**
	 * ControlID	rdo_InspectionMode_OFF
	 * TemplateKey	W_InspectionMode_OFF
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_InspectionMode_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_InspectionMode_OFF" , "W_InspectionMode_OFF");

	/**
	 * ControlID	btn_Set2
	 * TemplateKey	W_Set2
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set2 = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set2" , "W_Set2");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

}
//end of class

// $Id: FaItemMasterMnt.java 5210 2009-10-22 01:28:44Z fukuwa $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.faitemmnt;
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
 * @version $Revision: 5210 $, $Date: 2009-10-22 10:28:44 +0900 (木, 22 10 2009) $
 * @author  $Author: fukuwa $
 */
public class FaItemMasterMnt extends Page
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
	 * ControlID	lbl_ItemCode_U
	 * TemplateKey	W_ItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode_U = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode_U" , "W_ItemCode");

	/**
	 * ControlID	lbl_RequireItemCode
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireItemCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireItemCode" , "W_Require");

	/**
	 * ControlID	txt_ItemCode_U
	 * TemplateKey	W_ItemCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode_U = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode_U" , "W_ItemCode");

	/**
	 * ControlID	btn_P_SearchItemCode
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_SearchItemCode = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_SearchItemCode" , "W_P_Search");

	/**
	 * ControlID	lbl_ProcessFlag
	 * TemplateKey	W_ProcessFlag
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ProcessFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ProcessFlag" , "W_ProcessFlag");

	/**
	 * ControlID	btn_Set
	 * TemplateKey	W_Set5
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "W_Set5");

	/**
	 * ControlID	btn_Modify
	 * TemplateKey	W_Modify
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "W_Modify");

	/**
	 * ControlID	btn_Delet
	 * TemplateKey	W_Delet
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delet = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delet" , "W_Delet");

	/**
	 * ControlID	lbl_ItemCode_D
	 * TemplateKey	W_ItemCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ItemCode_D = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ItemCode_D" , "W_ItemCode");

	/**
	 * ControlID	txt_ItemCode_D
	 * TemplateKey	W_ItemCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ItemCode_D = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ItemCode_D" , "W_ItemCode");

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
	 * ControlID	lbl_UsageType
	 * TemplateKey	W_UsageType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UsageType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UsageType" , "W_UsageType");

	/**
	 * ControlID	rdo_UsageType_generally
	 * TemplateKey	W_UsageType_generally
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_UsageType_generally = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_UsageType_generally" , "W_UsageType_generally");

	/**
	 * ControlID	rdo_UsageType_temporarily
	 * TemplateKey	W_UsageType_temporarily
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_UsageType_temporarily = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_UsageType_temporarily" , "W_UsageType_temporarily");

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

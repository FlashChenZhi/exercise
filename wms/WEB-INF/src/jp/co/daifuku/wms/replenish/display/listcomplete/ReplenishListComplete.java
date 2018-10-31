// $Id: ReplenishListComplete.java 483 2008-10-22 01:18:19Z okamura $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.replenish.display.listcomplete;
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
 * @version $Revision: 483 $, $Date: 2008-10-22 10:18:19 +0900 (水, 22 10 2008) $
 * @author  $Author: okamura $
 */
public class ReplenishListComplete extends Page
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
	 * ControlID	tab_ListWorkComplete
	 * TemplateKey	W_ListWorkComplete
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_ListWorkComplete = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_ListWorkComplete" , "W_ListWorkComplete");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_WorkFlag
	 * TemplateKey	W_WorkFlag
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkFlag" , "W_WorkFlag");

	/**
	 * ControlID	rdo_ReplenishmentWorkFlagPlan
	 * TemplateKey	W_ReplenishmentWorkFlag_Plan
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ReplenishmentWorkFlagPlan = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ReplenishmentWorkFlagPlan" , "W_ReplenishmentWorkFlag_Plan");

	/**
	 * ControlID	rdo_ReplenishmentWorkFlagUgt
	 * TemplateKey	W_ReplenishmentWorkFlag_Urgent
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ReplenishmentWorkFlagUgt = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ReplenishmentWorkFlagUgt" , "W_ReplenishmentWorkFlag_Urgent");

	/**
	 * ControlID	lbl_ListWorkNo
	 * TemplateKey	W_ListWorkNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListWorkNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListWorkNo" , "W_ListWorkNo");

	/**
	 * ControlID	lbl_RequireListWorkNo
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireListWorkNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireListWorkNo" , "W_Require");

	/**
	 * ControlID	txt_ListWorkNo
	 * TemplateKey	W_ListWorkNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ListWorkNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ListWorkNo" , "W_ListWorkNo");

	/**
	 * ControlID	btn_Display
	 * TemplateKey	W_Display
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Display" , "W_Display");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

	/**
	 * ControlID	btn_Complete
	 * TemplateKey	W_Complete
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Complete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Complete" , "W_Complete");

	/**
	 * ControlID	btn_AllClear
	 * TemplateKey	W_AllClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllClear" , "W_AllClear");

	/**
	 * ControlID	lbl_LWorkFlag
	 * TemplateKey	W_WorkFlag
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LWorkFlag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LWorkFlag" , "W_WorkFlag");

	/**
	 * ControlID	txt_LRWorkFlag
	 * TemplateKey	W_R_WorkFlag
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_LRWorkFlag = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_LRWorkFlag" , "W_R_WorkFlag");

	/**
	 * ControlID	lbl_LListWorkNo
	 * TemplateKey	W_ListWorkNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LListWorkNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LListWorkNo" , "W_ListWorkNo");

	/**
	 * ControlID	txt_LListWorkNo
	 * TemplateKey	W_ListWorkNo
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_LListWorkNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_LListWorkNo" , "W_ListWorkNo");

	/**
	 * ControlID	lst_ReplenishmentResultInput
	 * TemplateKey	W_ReplenishmentListResultInput
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ReplenishmentResultInput = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ReplenishmentResultInput" , "W_ReplenishmentListResultInput");

}
//end of class

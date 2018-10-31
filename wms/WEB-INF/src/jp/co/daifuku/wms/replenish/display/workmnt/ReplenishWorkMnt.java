// $Id: ReplenishWorkMnt.java 640 2008-10-24 07:34:14Z okamura $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.replenish.display.workmnt;
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
 * @version $Revision: 640 $, $Date: 2008-10-24 16:34:14 +0900 (金, 24 10 2008) $
 * @author  $Author: okamura $
 */
public class ReplenishWorkMnt extends Page
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
	 * ControlID	tab_CancelRePrint
	 * TemplateKey	W_CancelRePrint
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_CancelRePrint = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_CancelRePrint" , "W_CancelRePrint");

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
	 * ControlID	btn_Display
	 * TemplateKey	W_Display
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Display" , "W_Display");

	/**
	 * ControlID	btn_RePrint
	 * TemplateKey	W_RePrint
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_RePrint = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_RePrint" , "W_RePrint");

	/**
	 * ControlID	btn_WorkCancel
	 * TemplateKey	W_WorkCancel
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_WorkCancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_WorkCancel" , "W_WorkCancel");

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
	 * ControlID	btn_AllClear
	 * TemplateKey	W_AllClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllClear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllClear" , "W_AllClear");

	/**
	 * ControlID	lst_ReplenishmentCancelRePrint
	 * TemplateKey	W_ReplenishmentCancelRePrint
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ReplenishmentCancelRePrint = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ReplenishmentCancelRePrint" , "W_ReplenishmentCancelRePrint");

}
//end of class

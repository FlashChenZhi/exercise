// $Id: AsWorkEnd.java 5432 2009-11-06 10:41:10Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.workend;
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
 * @version $Revision: 5432 $, $Date: 2009-11-06 19:41:10 +0900 (金, 06 11 2009) $
 * @author  $Author: okayama $
 */
public class AsWorkEnd extends Page
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
	 * ControlID	lbl_Mode
	 * TemplateKey	W_Mode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Mode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Mode" , "W_Mode");

	/**
	 * ControlID	rdo_WorkingEndUsual
	 * TemplateKey	W_ASRSMode_Normal
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_WorkingEndUsual = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_WorkingEndUsual" , "W_ASRSMode_Normal");

	/**
	 * ControlID	rdo_WorkingEnd_Date
	 * TemplateKey	W_ASRSMode_Abort
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_WorkingEnd_Date = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_WorkingEnd_Date" , "W_ASRSMode_Abort");

	/**
	 * ControlID	rdo_WorkingSeparate
	 * TemplateKey	W_ASRSMode_Separate
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_WorkingSeparate = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_WorkingSeparate" , "W_ASRSMode_Separate");

	/**
	 * ControlID	lst_AsrsWorkEnd
	 * TemplateKey	W_ASRSSetList
	 * ControlType	ScrollListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_AsrsWorkEnd = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_AsrsWorkEnd" , "W_ASRSSetList");

	/**
	 * ControlID	btn_Set2
	 * TemplateKey	W_Set2
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set2 = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set2" , "W_Set2");

	/**
	 * ControlID	btn_ReDisplayFunc
	 * TemplateKey	W_ReDisplayFunc
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ReDisplayFunc = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ReDisplayFunc" , "W_ReDisplayFunc");

	/**
	 * ControlID	btn_RemainWork
	 * TemplateKey	W_P_RestWorkList
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_RemainWork = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_RemainWork" , "W_P_RestWorkList");

}
//end of class

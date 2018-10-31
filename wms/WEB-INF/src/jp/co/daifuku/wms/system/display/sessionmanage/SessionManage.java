// $Id: SessionManage.java 3856 2009-03-27 02:49:05Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.system.display.sessionmanage;
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
 * @version $Revision: 3856 $, $Date: 2009-03-27 11:49:05 +0900 (金, 27 3 2009) $
 * @author  $Author: okayama $
 */
public class SessionManage extends Page
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
	 * ControlID	Tab_SessionManager
	 * TemplateKey	SM_SessionManager
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab Tab_SessionManager = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("Tab_SessionManager" , "SM_SessionManager");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_ServerName
	 * TemplateKey	SM_ServerName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ServerName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ServerName" , "SM_ServerName");

	/**
	 * ControlID	pul_TerminalName
	 * TemplateKey	W_TerminalName
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_TerminalName = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_TerminalName" , "W_TerminalName");

	/**
	 * ControlID	btn_View
	 * TemplateKey	W_Display
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "W_Display");

	/**
	 * ControlID	btn_Station_Stop
	 * TemplateKey	W_Suspend
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Station_Stop = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Station_Stop" , "W_Suspend");

	/**
	 * ControlID	btn_Logoff
	 * TemplateKey	W_Logoff
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Logoff = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Logoff" , "W_Logoff");

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
	 * ControlID	lst_UserList
	 * TemplateKey	SM_UserList
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_UserList = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_UserList" , "SM_UserList");

}
//end of class

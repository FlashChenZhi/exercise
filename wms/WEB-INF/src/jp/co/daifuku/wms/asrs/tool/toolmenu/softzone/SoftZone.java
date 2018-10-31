// $Id: SoftZone.java 4122 2009-04-10 10:58:38Z ota $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.softzone;
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
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 */
public class SoftZone extends Page
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
	 * ControlID	lbl_ZoneId
	 * TemplateKey	AST_ZoneId
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ZoneId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ZoneId" , "AST_ZoneId");

	/**
	 * ControlID	txt_SoftZoneId
	 * TemplateKey	AST_SoftZoneId
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_SoftZoneId = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_SoftZoneId" , "AST_SoftZoneId");

	/**
	 * ControlID	lbl_ZoneName
	 * TemplateKey	AST_ZoneName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ZoneName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ZoneName" , "AST_ZoneName");

	/**
	 * ControlID	txt_ZoneName
	 * TemplateKey	AST_ZoneName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ZoneName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ZoneName" , "AST_ZoneName");

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
	 * ControlID	lst_SoftZone
	 * TemplateKey	AST_S_SoftZone
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_SoftZone = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_SoftZone" , "AST_S_SoftZone");

}
//end of class

// $Id: PCTAreaModify2.java 3853 2009-03-27 02:20:10Z shibamoto $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.master.display.pctareamodify;
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
 * @version $Revision: 3853 $, $Date: 2009-03-27 11:20:10 +0900 (金, 27 3 2009) $
 * @author  $Author: shibamoto $
 */
public class PCTAreaModify2 extends Page
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
	 * TemplateKey	W_SetModifyDelete2
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_SetModifyDelete2");

	/**
	 * ControlID	btn_Back
	 * TemplateKey	W_Back
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "W_Back");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_MenuNoFunc
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_MenuNoFunc");

	/**
	 * ControlID	lbl_Area
	 * TemplateKey	AST_AreaNo
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Area = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Area" , "AST_AreaNo");

	/**
	 * ControlID	lbl_InAreaNo
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InAreaNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InAreaNo" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_AreaName
	 * TemplateKey	W_AreaName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AreaName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AreaName" , "W_AreaName");

	/**
	 * ControlID	txt_AreaName
	 * TemplateKey	W_AreaName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_AreaName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_AreaName" , "W_AreaName");

	/**
	 * ControlID	lbl_LastUpdate
	 * TemplateKey	W_LastUpdate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LastUpdate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LastUpdate" , "W_LastUpdate");

	/**
	 * ControlID	lbl_JavaSetLastUpdate
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetLastUpdate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetLastUpdate" , "W_In_JavaSet");

	/**
	 * ControlID	btn_Modify
	 * TemplateKey	W_ModifySet
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "W_ModifySet");

	/**
	 * ControlID	btn_Delete
	 * TemplateKey	W_Delete
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "W_Delete");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	W_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");

}
//end of class

// $Id: PCTItemDelete3.java 3940 2009-04-03 03:47:17Z shibamoto $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.master.display.pctitemdelete;
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
 * @version $Revision: 3940 $, $Date: 2009-04-03 12:47:17 +0900 (金, 03 4 2009) $
 * @author  $Author: shibamoto $
 */
public class PCTItemDelete3 extends Page
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
	 * TemplateKey	W_Save
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Save");

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
	 * ControlID	lbl_DataFileFolder
	 * TemplateKey	W_DataFileFolder
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DataFileFolder = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DataFileFolder" , "W_DataFileFolder");

	/**
	 * ControlID	lbl_In_DataFileFolder
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_DataFileFolder = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_DataFileFolder" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_DateFileName
	 * TemplateKey	W_DateFileName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DateFileName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DateFileName" , "W_DateFileName");

	/**
	 * ControlID	lbl_In_DataFileName
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_DataFileName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_DataFileName" , "W_In_JavaSet");

	/**
	 * ControlID	btn_Start
	 * TemplateKey	W_Start
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Start = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Start" , "W_Start");

}
//end of class

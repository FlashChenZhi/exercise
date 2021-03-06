// $Id: skelten.java 7996 2011-07-06 00:52:24Z kitamaki $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.test.query.display;
import jp.co.daifuku.bluedog.ui.control.Page;

/**
 * This class can not be changed and it is generated by ScreenGenerator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 */
public class QueryStudent extends Page
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
	 * TemplateKey	T_Help
	 * ControlType	LinkButton
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "T_Help");

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
	 * TemplateKey	T_ToMenu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "T_ToMenu");

	/**
	 * ControlID	lbl_Name
	 * TemplateKey	lbl_Name
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Name = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Name" , "lbl_Name");

	/**
	 * ControlID	txt_Name
	 * TemplateKey	txt_Name
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Name = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Name" , "txt_Name");

	/**
	 * ControlID	lbl_Number
	 * TemplateKey	lbl_Number
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Number = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Number" , "lbl_Number");

	/**
	 * ControlID	txt_Number
	 * TemplateKey	ntb_Number
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_Number = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_Number" , "ntb_Number");

	/**
	 * ControlID	lbl_Sex
	 * TemplateKey	lbl_Sex
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Sex = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Sex" , "lbl_Sex");

	/**
	 * ControlID	rdo_Man
	 * TemplateKey	rdo_Man
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Man = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Man" , "rdo_Man");

	/**
	 * ControlID	rdo_WoMan
	 * TemplateKey	rdo_WoMan
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_WoMan = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_WoMan" , "rdo_WoMan");

	/**
	 * ControlID	lbl_Telephone
	 * TemplateKey	lbl_Telephone
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Telephone = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Telephone" , "lbl_Telephone");

	/**
	 * ControlID	txt_Telephone
	 * TemplateKey	ntb_Telephone
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_Telephone = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_Telephone" , "ntb_Telephone");

	/**
	 * ControlID	lbl_FromDate
	 * TemplateKey	lbl_FromDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromDate" , "lbl_FromDate");

	/**
	 * ControlID	txt_FromDate
	 * TemplateKey	dtb_FromDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_FromDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_FromDate" , "dtb_FromDate");

	/**
	 * ControlID	lbl_FromTime
	 * TemplateKey	lbl_FromTime
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FromTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FromTime" , "lbl_FromTime");

	/**
	 * ControlID	txt_FromTime
	 * TemplateKey	ttb_FromTime
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_FromTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_FromTime" , "ttb_FromTime");

	/**
	 * ControlID	lbl_ToDate
	 * TemplateKey	lbl_ToDate
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ToDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ToDate" , "lbl_ToDate");

	/**
	 * ControlID	txt_ToDate
	 * TemplateKey	dtb_ToDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_ToDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_ToDate" , "dtb_ToDate");

	/**
	 * ControlID	lbl_ToTime
	 * TemplateKey	lbl_ToTime
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ToTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ToTime" , "lbl_ToTime");

	/**
	 * ControlID	txt_ToTime
	 * TemplateKey	ttb_ToTime
	 * ControlType	TimeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_ToTime = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_ToTime" , "ttb_ToTime");

	/**
	 * ControlID	lbl_Major
	 * TemplateKey	lbl_Major
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Major = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Major" , "lbl_Major");

	/**
	 * ControlID	pul_Major
	 * TemplateKey	pld_Major
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Major = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Major" , "pld_Major");

	/**
	 * ControlID	lbl_Address
	 * TemplateKey	lbl_Address
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Address = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Address" , "lbl_Address");

	/**
	 * ControlID	txt_Address
	 * TemplateKey	txt_Address
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Address = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Address" , "txt_Address");

	/**
	 * ControlID	lbl_Hobby
	 * TemplateKey	lbl_Hobby
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hobby = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hobby" , "lbl_Hobby");

	/**
	 * ControlID	chk_Read
	 * TemplateKey	chk_Read
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Read = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Read" , "chk_Read");

	/**
	 * ControlID	chk_Music
	 * TemplateKey	chk_Music
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Music = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Music" , "chk_Music");

	/**
	 * ControlID	chk_Program
	 * TemplateKey	chk_Program
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Program = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Program" , "chk_Program");

	/**
	 * ControlID	chk_Run
	 * TemplateKey	chk_Run
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Run = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Run" , "chk_Run");

	/**
	 * ControlID	chk_Film
	 * TemplateKey	chk_Film
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Film = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Film" , "chk_Film");

	/**
	 * ControlID	chk_Travel
	 * TemplateKey	chk_Travel
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Travel = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Travel" , "chk_Travel");

	/**
	 * ControlID	chk_Swimming
	 * TemplateKey	chk_Swimming
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Swimming = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Swimming" , "chk_Swimming");

	/**
	 * ControlID	chk_Photography
	 * TemplateKey	chk_Photography
	 * ControlType	CheckBox
	 */
	public jp.co.daifuku.bluedog.ui.control.CheckBox chk_Photography = jp.co.daifuku.bluedog.ui.control.CheckBoxFactory.getInstance("chk_Photography" , "chk_Photography");

	/**
	 * ControlID	btn_Query
	 * TemplateKey	AST_Query
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Query = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Query" , "AST_Query");

	/**
	 * ControlID	btn_Set
	 * TemplateKey	W_P_Set
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "W_P_Set");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	T_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "T_Clear");

	/**
	 * ControlID	btn_AllCheck
	 * TemplateKey	W_AllCheck
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllCheck = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllCheck" , "W_AllCheck");

	/**
	 * ControlID	btn_AllDelete
	 * TemplateKey	W_AllDelete
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_AllDelete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_AllDelete" , "W_AllDelete");

	/**
	 * ControlID	btn_2
	 * TemplateKey	W_AllClear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_2 = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_2" , "W_AllClear");

	/**
	 * ControlID	pgr_14
	 * TemplateKey	
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_14 = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_14" , "");

	/**
	 * ControlID	lst_StudentInformation
	 * TemplateKey	StudentInformation
	 * ControlType	ScrollListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_StudentInformation = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_StudentInformation" , "StudentInformation");

}
//end of class

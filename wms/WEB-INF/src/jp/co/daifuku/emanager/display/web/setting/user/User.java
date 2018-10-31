// $Id: User.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.user;
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
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 </jp> */
/** <en>
 * This invariable class is created by the screen generator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 </en> */

public class User extends Page
{

	// Class variables -----------------------------------------------

	/**
	 * ControlID	lbl_SettingName
	 * TemplateKey	T_In_SettingName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "T_In_SettingName");

	/**
	 * ControlID	btn_Help
	 * TemplateKey	T_Help
	 * ControlType	LinkButton
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "T_Help");

	/**
	 * ControlID	message
	 * TemplateKey	T_OperationMsg
	 * ControlType	Message
	 */
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "T_OperationMsg");

	/**
	 * ControlID	tab
	 * TemplateKey	T_Configure
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_Configure");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	T_ToMenu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "T_ToMenu");

	/**
	 * ControlID	lbl_UserId
	 * TemplateKey	T_UserId
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserId" , "T_UserId");

	/**
	 * ControlID	lbl_RequireUserId
	 * TemplateKey	T_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireUserId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireUserId" , "T_Require");

	/**
	 * ControlID	txt_UserId
	 * TemplateKey	T_UserId
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserId" , "T_UserId");

	/**
	 * ControlID	btn_P_Search
	 * TemplateKey	T_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search" , "T_P_Search");

	/**
	 * ControlID	lbl_ProcessingType
	 * TemplateKey	T_ProcessingType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ProcessingType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ProcessingType" , "T_ProcessingType");

	/**
	 * ControlID	btn_Submit
	 * TemplateKey	T_Submit
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "T_Submit");

	/**
	 * ControlID	btn_Modify
	 * TemplateKey	T_Modify
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "T_Modify");

	/**
	 * ControlID	btn_Delete
	 * TemplateKey	T_Delete
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "T_Delete");

	/**
	 * ControlID	lbl_UserId2
	 * TemplateKey	T_UserId
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserId2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserId2" , "T_UserId");

	/**
	 * ControlID	txt_R_UserId
	 * TemplateKey	T_R_UserId
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_UserId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_UserId" , "T_R_UserId");

	/**
	 * ControlID	lbl_Password
	 * TemplateKey	T_Password
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "T_Password");

	/**
	 * ControlID	lbl_RequirePassword
	 * TemplateKey	T_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequirePassword = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequirePassword" , "T_Require");

	/**
	 * ControlID	txt_Password
	 * TemplateKey	T_Password
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "T_Password");

	/**
	 * ControlID	lbl_Password_Re
	 * TemplateKey	T_Password_Re
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password_Re = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password_Re" , "T_Password_Re");

	/**
	 * ControlID	lbl_RequirePassword_Re
	 * TemplateKey	T_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequirePassword_Re = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequirePassword_Re" , "T_Require");

	/**
	 * ControlID	txt_Password_Re
	 * TemplateKey	T_Password
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password_Re = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password_Re" , "T_Password");

	/**
	 * ControlID	lbl_PwdChangeInterval
	 * TemplateKey	T_PwdChangeInterval
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PwdChangeInterval = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PwdChangeInterval" , "T_PwdChangeInterval");

	/**
	 * ControlID	rdo_PwdChangeInterval
	 * TemplateKey	T_PwdChangeInterval
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PwdChangeInterval = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PwdChangeInterval" , "T_PwdChangeInterval");

	/**
	 * ControlID	txt_PwdChangeInterval
	 * TemplateKey	T_PwdChangeInterval
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PwdChangeInterval = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PwdChangeInterval" , "T_PwdChangeInterval");

	/**
	 * ControlID	rdo_PwdChangeUnrestricted
	 * TemplateKey	T_PwdChange_Unrestricted
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PwdChangeUnrestricted = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PwdChangeUnrestricted" , "T_PwdChange_Unrestricted");

	/**
	 * ControlID	rdo_PwdChangeRollSuccession
	 * TemplateKey	T_PwdChangeRollSuccession
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PwdChangeRollSuccession = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PwdChangeRollSuccession" , "T_PwdChangeRollSuccession");

	/**
	 * ControlID	lbl_PwdExpires
	 * TemplateKey	T_PwdExpires
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PwdExpires = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PwdExpires" , "T_PwdExpires");

	/**
	 * ControlID	txt_PwdExpires
	 * TemplateKey	T_PwdExpires
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_PwdExpires = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_PwdExpires" , "T_PwdExpires");

	/**
	 * ControlID	lbl_RoleId
	 * TemplateKey	T_RoleId
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RoleId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RoleId" , "T_RoleId");

	/**
	 * ControlID	lbl_RequireRoleId
	 * TemplateKey	T_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireRoleId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireRoleId" , "T_Require");

	/**
	 * ControlID	txt_RoleId
	 * TemplateKey	T_RoleId
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RoleId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RoleId" , "T_RoleId");

	/**
	 * ControlID	btn_P_Search2
	 * TemplateKey	T_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search2 = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search2" , "T_P_Search");

	/**
	 * ControlID	lbl_SameUserLoginMax
	 * TemplateKey	T_SameUserLoginMax
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SameUserLoginMax = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SameUserLoginMax" , "T_SameUserLoginMax");

	/**
	 * ControlID	rdo_SameUserLoginMax
	 * TemplateKey	T_SameUserLoginMax
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SameUserLoginMax = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SameUserLoginMax" , "T_SameUserLoginMax");

	/**
	 * ControlID	txt_SameUserLoginMax
	 * TemplateKey	T_SameUserLoginMax
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_SameUserLoginMax = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_SameUserLoginMax" , "T_SameUserLoginMax");

	/**
	 * ControlID	rdo_SameUserLoginUnrestricted
	 * TemplateKey	T_SameUserLoginUnrestricted
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SameUserLoginUnrestricted = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SameUserLoginUnrestricted" , "T_SameUserLoginUnrestricted");

	/**
	 * ControlID	lbl_UserStatus
	 * TemplateKey	T_UserStatus
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserStatus = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserStatus" , "T_UserStatus");

	/**
	 * ControlID	rdo_UserStatus_Active
	 * TemplateKey	T_UserStatus_Active
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_UserStatus_Active = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_UserStatus_Active" , "T_UserStatus_Active");

	/**
	 * ControlID	rdo_UserStatus_Disable
	 * TemplateKey	T_UserStatus_Disable
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_UserStatus_Disable = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_UserStatus_Disable" , "T_UserStatus_Disable");

	/**
	 * ControlID	rdo_UserStatus_Locked
	 * TemplateKey	T_UserStatus_Locked
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_UserStatus_Locked = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_UserStatus_Locked" , "T_UserStatus_Locked");

	/**
	 * ControlID	lbl_FailedLoginAttempts
	 * TemplateKey	T_FailedLoginAttempts
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FailedLoginAttempts = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FailedLoginAttempts" , "T_FailedLoginAttempts");

	/**
	 * ControlID	rdo_FailedLoginRestricted
	 * TemplateKey	T_FailedLoginAttempts
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLoginRestricted = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLoginRestricted" , "T_FailedLoginAttempts");

	/**
	 * ControlID	txt_FailedLoginAttempts
	 * TemplateKey	T_FailedLoginAttempts
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_FailedLoginAttempts = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_FailedLoginAttempts" , "T_FailedLoginAttempts");

	/**
	 * ControlID	rdo_FailedLoginUnrestricted
	 * TemplateKey	T_FailedLogin_Unrestricted
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLoginUnrestricted = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLoginUnrestricted" , "T_FailedLogin_Unrestricted");

	/**
	 * ControlID	rdo_FailedLoginRollSuccession
	 * TemplateKey	T_FailedLoginRollSuccession
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLoginRollSuccession = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLoginRollSuccession" , "T_FailedLoginRollSuccession");

	/**
	 * ControlID	lbl_UserName
	 * TemplateKey	T_UserName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserName" , "T_UserName");

	/**
	 * ControlID	txt_UserName
	 * TemplateKey	T_UserName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserName" , "T_UserName");

	/**
	 * ControlID	lbl_Belogning
	 * TemplateKey	T_Belonging
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Belogning = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Belogning" , "T_Belonging");

	/**
	 * ControlID	txt_Belonging
	 * TemplateKey	T_Belonging
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Belonging = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Belonging" , "T_Belonging");

	/**
	 * ControlID	lbl_Note
	 * TemplateKey	T_Note
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Note = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Note" , "T_Note");

	/**
	 * ControlID	txt_Note
	 * TemplateKey	T_Note
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Note = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Note" , "T_Note");

	/**
	 * ControlID	btn_Commit
	 * TemplateKey	T_Commit
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "T_Commit");

	/**
	 * ControlID	btn_Clear
	 * TemplateKey	T_Clear
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "T_Clear");

}
//end of class

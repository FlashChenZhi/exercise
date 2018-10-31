// $Id: AuthenticationSystem2.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.system;
import jp.co.daifuku.bluedog.ui.control.Page;

/** <jp>
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
public class AuthenticationSystem2 extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "T_In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "T_Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "T_OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_Configure");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "T_ToMenu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FailedLoginScreenLockTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FailedLoginScreenLockTime" , "T_FailedLoginScreenLockTime");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireScreenLockTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireScreenLockTime" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_FailedLoginScreenLockTime = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_FailedLoginScreenLockTime" , "T_FailedLoginScreenLockTime");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SessionTimeoutTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SessionTimeoutTime" , "T_SessionTimeoutTime");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireSessionTimeout = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireSessionTimeout" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_SessionTimeoutTime = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_SessionTimeoutTime" , "T_SessionTimeoutTime");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_IPRange_Min = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_IPRange_Min" , "T_IPRange_Min");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_IPRange_Min = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_IPRange_Min" , "T_IPRange_Min");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_IPRange_Max = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_IPRange_Max" , "T_IPRange_Max");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_IPRange_Max = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_IPRange_Max" , "T_IPRange_Max");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PasswordLogCheckTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PasswordLogCheckTime" , "T_PasswordLogCheckTime");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequirePwdLogCheckTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequirePwdLogCheckTime" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PasswordLogCheckTime = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PasswordLogCheckTime" , "T_PasswordLogCheckTime");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PasswordMinLength = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PasswordMinLength" , "T_PasswordMinLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequirePasswordMinLength = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequirePasswordMinLength" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PasswordMinLength = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PasswordMinLength" , "T_PasswordMinLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FailedLoginUserLock_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FailedLoginUserLock_Flag" , "T_FailedLoginUserLock_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLoginUserLock_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLoginUserLock_ON" , "T_FailedLoginUserLock_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLoginUserLock_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLoginUserLock_OFF" , "T_FailedLoginUserLock_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FailedLoginAttempts = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FailedLoginAttempts" , "T_FailedLoginAttempts");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLoginAttempts = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLoginAttempts" , "T_FailedLoginAttempts");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_FailedLoginAttempts = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_FailedLoginAttempts" , "T_FailedLoginAttempts");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLogin_Unrestricted = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLogin_Unrestricted" , "T_FailedLogin_Unrestricted");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PwdChangeInterval = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PwdChangeInterval" , "T_PwdChangeInterval");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PwdChangeInterval = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PwdChangeInterval" , "T_PwdChangeInterval");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PwdChangeInterval = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PwdChangeInterval" , "T_PwdChangeInterval");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PwdChange_Unrestricted = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PwdChange_Unrestricted" , "T_PwdChange_Unrestricted");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "T_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "T_Cancel");

}
//end of class

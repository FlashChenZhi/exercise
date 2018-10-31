// $Id: AuthenticationSystem1.java 3965 2009-04-06 02:55:05Z admin $

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
public class AuthenticationSystem1 extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "T_In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "T_Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "T_OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_Configure");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "T_ToMenu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LoginMax = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LoginMax" , "T_LoginMax");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireLoginMax = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireLoginMax" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_LoginMax = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_LoginMax" , "T_LoginMax");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SameLoginUser_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SameLoginUser_Flag" , "T_SameLoginUser_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SameLoginUser_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SameLoginUser_ON" , "T_Permission");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SameLoginUser_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SameLoginUser_OFF" , "T_Deny");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AutoLogin_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AutoLogin_Flag" , "T_AutoLogin_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AutoLogin_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AutoLogin_ON" , "T_AutoLogin_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AutoLogin_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AutoLogin_OFF" , "T_AutoLogin_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ScreenLogInCheck_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ScreenLogInCheck_Flag" , "T_ScreenLogInCheck_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ScreenLogInCheck_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ScreenLogInCheck_ON" , "T_ScreenLogInCheck_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ScreenLogInCheck_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ScreenLogInCheck_OFF" , "T_ScreenLogInCheck_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SessionTimeoutTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SessionTimeoutTime" , "T_SessionTimeoutTime");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireSessionTimeout = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireSessionTimeout" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_SessionTimeoutTime = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_SessionTimeoutTime" , "T_SessionTimeoutTime");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TerminalChange_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TerminalChange_Flag" , "T_TerminalChange_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TerminalChange_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TerminalChange_ON" , "T_TerminalChange_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TerminalChange_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TerminalChange_OFF" , "T_TerminalChange_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TerminalUserCheck_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TerminalUserCheck_Flag" , "T_TerminalUserCheck_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TerminalUserCheck_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TerminalUserCheck_ON" , "T_TerminalUserCheck_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TerminalUserCheck_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TerminalUserCheck_OFF" , "T_TerminalUserCheck_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TerminalCheck_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TerminalCheck_Flag" , "T_TerminalCheck_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TerminalCheck_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TerminalCheck_ON" , "T_TerminalCheck_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TerminalCheck_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TerminalCheck_OFF" , "T_TerminalCheck_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_IPRangeCheck_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_IPRangeCheck_Flag" , "T_IPRangeCheck_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_IPRangeCheck_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_IPRangeCheck_ON" , "T_IPRangeCheck_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_IPRangeCheck_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_IPRangeCheck_OFF" , "T_IPRangeCheck_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_IPRange_Min = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_IPRange_Min" , "T_IPRange_Min");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_IPRange_Min = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_IPRange_Min" , "T_IPRange_Min");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_IPRange_Max = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_IPRange_Max" , "T_IPRange_Max");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_IPRange_Max = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_IPRange_Max" , "T_IPRange_Max");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FailedLoginScreenLockCount = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FailedLoginScreenLockCount" , "T_FailedLoginScreenLockCount");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLoginScreenLock_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLoginScreenLock_ON" , "T_FailedLoginScreenLock_ON");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_FailedLoginScreenLockCount = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_FailedLoginScreenLockCount" , "T_FailedLoginScreenLockCount");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLoginScreenLock_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLoginScreenLock_OFF" , "T_FailedLoginScreenLock_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FailedLoginScreenLockTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FailedLoginScreenLockTime" , "T_FailedLoginScreenLockTime");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_FailedLoginScreenLockTime = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_FailedLoginScreenLockTime" , "T_FailedLoginScreenLockTime");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FailedLoginUserLock_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FailedLoginUserLock_Flag" , "T_FailedLoginUserLock_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLoginUserLock_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLoginUserLock_ON" , "T_FailedLoginUserLock_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLoginUserLock_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLoginUserLock_OFF" , "T_FailedLoginUserLock_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FailedLoginAttempts = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FailedLoginAttempts" , "T_FailedLoginAttempts");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLoginAttempts = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLoginAttempts" , "T_FailedLoginAttempts");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_FailedLoginAttempts = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_FailedLoginAttempts" , "T_FailedLoginAttempts");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLogin_Unrestricted = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLogin_Unrestricted" , "T_FailedLogin_Unrestricted");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DummyPassWord_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DummyPassWord_Flag" , "T_DummyPassWord_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_DummyPassWord_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_DummyPassWord_ON" , "T_DummyPassWord_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_DummyPassWord_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_DummyPassWord_OFF" , "T_DummyPassWord_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PasswordLogCheckTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PasswordLogCheckTime" , "T_PasswordLogCheckTime");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequirePwdLogCheckTime = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequirePwdLogCheckTime" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PasswordLogCheckTime = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PasswordLogCheckTime" , "T_PasswordLogCheckTime");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PasswordMinLength = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PasswordMinLength" , "T_PasswordMinLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequirePasswordMinLength = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequirePasswordMinLength" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PasswordMinLength = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PasswordMinLength" , "T_PasswordMinLength");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PassWordSaftyCheck_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PassWordSaftyCheck_Flag" , "T_PassWordSaftyCheck_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PassWordSaftyCheck_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PassWordSaftyCheck_ON" , "T_PassWordSaftyCheck_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PassWordSaftyCheck_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PassWordSaftyCheck_OFF" , "T_PassWordSaftyCheck_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PasswordExpire_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PasswordExpire_Flag" , "T_PasswordExpire_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PasswordExpire_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PasswordExpire_ON" , "T_PasswordExpire_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PasswordExpire_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PasswordExpire_OFF" , "T_PasswordExpire_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PwdChangeInterval = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PwdChangeInterval" , "T_PwdChangeInterval");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PwdChangeInterval = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PwdChangeInterval" , "T_PwdChangeInterval");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PwdChangeInterval = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PwdChangeInterval" , "T_PwdChangeInterval");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PwdChange_Unrestricted = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PwdChange_Unrestricted" , "T_PwdChange_Unrestricted");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PasswordExpireAlertDays = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PasswordExpireAlertDays" , "T_PasswordExpireAlertDays");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PasswordExpireAlertDays = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PasswordExpireAlertDays" , "T_PasswordExpireAlertDays");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Part11Log_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Part11Log_Flag" , "T_Part11Log_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Part11Log_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Part11Log_ON" , "T_Part11Log_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Part11Log_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Part11Log_OFF" , "T_Part11Log_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AccessLog_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AccessLog_Flag" , "T_AccessLog_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AccessLog_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AccessLog_ON" , "T_AccessLog_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AccessLog_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AccessLog_OFF" , "T_AccessLog_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MasterLog_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MasterLog_Flag" , "T_MasterLog_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_MasterLog_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_MasterLog_ON" , "T_MasterLog_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_MasterLog_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_MasterLog_OFF" , "T_MasterLog_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StockLog_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StockLog_Flag" , "T_StockLog_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_StockLog_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_StockLog_ON" , "T_StockLog_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_StockLog_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_StockLog_OFF" , "T_StockLog_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OperationLog_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OperationLog_Flag" , "T_OperationLog_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_OperationLog_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_OperationLog_ON" , "T_OperationLog_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_OperationLog_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_OperationLog_OFF" , "T_OperationLog_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DBLogHoldDays = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DBLogHoldDays" , "T_DBLogHoldDays");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_DBLogHoldDays = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_DBLogHoldDays" , "T_DBLogHoldDays");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CsvLogHoldDays = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CsvLogHoldDays" , "T_CsvLogHoldDays");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_CsvLogHoldDays = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_CsvLogHoldDays" , "T_CsvLogHoldDays");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_HDLogHoldYears = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_HDLogHoldYears" , "T_HDLogHoldYears");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_HDLogHoldYears = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_HDLogHoldYears" , "T_HDLogHoldYears");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AuthenticationLog_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AuthenticationLog_Flag" , "T_AuthenticationLog_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AuthenticationLog_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AuthenticationLog_ON" , "T_AuthenticationLog_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AuthenticationLog_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AuthenticationLog_OFF" , "T_AuthenticationLog_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserMaintenanceLog_Flag = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserMaintenanceLog_Flag" , "T_UserMaintenanceLog_Flag");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_UserMaintenanceLog_ON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_UserMaintenanceLog_ON" , "T_UserMaintenanceLog_ON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_UserMaintenanceLog_OFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_UserMaintenanceLog_OFF" , "T_UserMaintenanceLog_OFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SameUserCreateBlockPeriod = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SameUserCreateBlockPeriod" , "T_SameUserCreateBlockPeriod");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SameUserBlock_restricted = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SameUserBlock_restricted" , "T_SameUserCreateBlockPeriod_restricted");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_SameUserCreateBlockPeriod = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_SameUserCreateBlockPeriod" , "T_SameUserCreateBlockPeriod");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SameUserBlock_Unrestricted = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SameUserBlock_Unrestricted" , "T_SameUserCreateBlockPeriod_Unrestricted");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MainMenuType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MainMenuType" , "T_MainMenuType");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_LargeIcon = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_LargeIcon" , "T_LargeIcon");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SmallIcon = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SmallIcon" , "T_SmallIcon");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_CommitSync = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_CommitSync" , "T_CommitSync");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "T_Cancel");

}
//end of class

// $Id: Role.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.user;
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
public class Role extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "T_In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "T_Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "T_OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_Configure");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "T_ToMenu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RoleId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RoleId" , "T_RoleId");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireRoleId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireRoleId" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RoleId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RoleId" , "T_RoleId");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search" , "T_P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ProcessingType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ProcessingType" , "T_ProcessingType");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "T_Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "T_Modify");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "T_Delete");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RoleId2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RoleId2" , "T_RoleId");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_RoleId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_RoleId" , "T_R_RoleId");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RoleName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RoleName" , "T_RoleName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireRoleName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireRoleName" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RoleName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RoleName" , "T_RoleName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FailedLoginAttempts = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FailedLoginAttempts" , "T_FailedLoginAttempts");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLoginAttempts1 = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLoginAttempts1" , "T_FailedLoginAttempts1");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_FailedLoginAttempts = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_FailedLoginAttempts" , "T_FailedLoginAttempts");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLoginAttempts2 = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLoginAttempts2" , "T_FailedLoginAttempts2");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_FailedLoginAttempts3 = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_FailedLoginAttempts3" , "T_FailedLoginAttempts3");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PasswordValidityTermDays = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PasswordValidityTermDays" , "T_PwdChangeInterval");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PWDChangeInterval1 = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PWDChangeInterval1" , "T_PWDChangeInterval1");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_PasswordChangeInterval = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_PasswordChangeInterval" , "T_PasswordChangeInterval");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PWDChangeInterval2 = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PWDChangeInterval2" , "T_PWDChangeInterval2");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PWDChangeInterval3 = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PWDChangeInterval3" , "T_PWDChangeInterval3");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Target = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Target" , "T_Target");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TargetCommon = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TargetCommon" , "T_TargetCommon");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TargetUser = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TargetUser" , "T_TargetUser");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_TargetTerminal = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_TargetTerminal" , "T_TargetTerminal");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "T_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "T_Clear");

}
//end of class

// $Id: UserRecovery.java 3965 2009-04-06 02:55:05Z admin $

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
public class UserRecovery extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "T_In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "T_Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "T_OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_Configure");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "T_ToMenu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserId" , "T_UserId");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireUserId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireUserId" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserId" , "T_UserId");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_UserIdSearch = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_UserIdSearch" , "T_P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_JobMenu = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JobMenu" , "T_JobMenu");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_LockCancellation = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_LockCancellation" , "T_LockCancellation");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PasswordReissue = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PasswordReissue" , "T_PasswordReissue");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserId2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserId2" , "T_UserID");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_UserId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_UserId" , "T_UserId");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserName" , "T_UserName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserName" , "T_UserName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StateOfLock = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StateOfLock" , "T_StateOfLock");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StateOfLock = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StateOfLock" , "T_StateOfLock");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "T_Password");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequirePassword = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequirePassword" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "T_UserRecoveryPassword");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PasswordAgain = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PasswordAgain" , "T_PasswordAgain");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequirePasswordAgain = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequirePasswordAgain" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_PasswordAgain = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_PasswordAgain" , "T_UserRecoveryPassword");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PwdExpires = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PwdExpires" , "T_PwdExpires");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_PwdExpires = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_PwdExpires" , "T_PwdExpires");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "T_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "T_Clear");

}
//end of class

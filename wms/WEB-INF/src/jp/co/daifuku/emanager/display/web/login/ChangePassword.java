// $Id: ChangePassword.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.login;
import jp.co.daifuku.bluedog.ui.control.Page;

/** 
 * <jp> パスワード変更画面不可変クラスです。<br></jp>
 * <en>It is a password change screen non-variable class. <br></en>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 */
public class ChangePassword extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_msg0 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_msg0" , "T_ChangePasswordMsg0");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_msg1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_msg1" , "T_ChangePasswordMsg1");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_msg2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_msg2" , "T_ChangePasswordMsg2");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OldPassword = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OldPassword" , "T_OldPassword");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_OldPassword = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_OldPassword" , "T_OldPassword");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_NewPassword = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_NewPassword" , "T_NewPassword");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_NewPassword = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_NewPassword" , "T_NewPassword");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ReenterPassword = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ReenterPassword" , "T_ReenterPassword");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_ReenterPassword = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_ReenterPassword" , "T_ReenterPassword");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "T_Back2");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PasswordModify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PasswordModify" , "T_PasswordModify");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Next = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Next" , "T_Next");

}
//end of class

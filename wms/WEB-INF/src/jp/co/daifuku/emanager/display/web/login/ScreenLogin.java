// $Id: ScreenLogin.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.login;
import jp.co.daifuku.bluedog.ui.control.Page;

/** 
 * <jp> ログイン画面の不可変クラスです。<br></jp>
 * <en> It is the non-variable class of a login screen. <br></en>
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
public class ScreenLogin extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Message1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Message1" , "T_LoginMessage1");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Message2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Message2" , "T_LoginMessage2");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LoginID = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LoginID" , "T_LoginUserID");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_LoginID = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_LoginID" , "T_LoginUserID");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Password = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Password" , "T_LoginPassword");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Password = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Password" , "T_LoginPassword");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Login = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Login" , "T_Login");
	public jp.co.daifuku.bluedog.ui.control.ImageButton btn_ChangePassword = jp.co.daifuku.bluedog.ui.control.ImageButtonFactory.getInstance("btn_ChangePassword" , "T_ChangePassword");

}
//end of class

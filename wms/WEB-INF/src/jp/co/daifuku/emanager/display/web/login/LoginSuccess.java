// $Id: LoginSuccess.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.login;
import jp.co.daifuku.bluedog.ui.control.Page;

/** 
 * <jp>端末切替画面の不可変クラスです。<jp></jp>
 * <en> It is the non-variable class which the tool generated. <jp></en>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 */
public class LoginSuccess extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Msg01 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Msg01" , "T_InJspMessage");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Msg02 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Msg02" , "T_InJspMessage");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TerminalName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TerminalName" , "T_TerminalName");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Terminal = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Terminal" , "T_TerminalNumber");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "T_Modify");	
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkDay = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkDay" , "T_WorkDay");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_WorkDay = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_WorkDay" , "T_WorkDate");
	
}
//end of class

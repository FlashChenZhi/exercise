// $Id: Terminal.java 3965 2009-04-06 02:55:05Z admin $

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
public class Terminal extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "T_In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "T_Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "T_OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_Configure");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "T_ToMenu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TerminalNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TerminalNumber" , "T_TerminalNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireTerminalNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireTerminalNumber" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TerminalNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TerminalNumber" , "T_TerminalNumber");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search" , "T_P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ProcessingType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ProcessingType" , "T_ProcessingType");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "T_Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "T_Modify");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "T_Delete");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TerminalNumber2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TerminalNumber2" , "T_TerminalNumber");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_TerminalNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_TerminalNumber" , "T_R_TerminalNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TerminalName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TerminalName" , "T_TerminalName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireTerminalName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireTerminalName" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_TerminalName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_TerminalName" , "T_TerminalName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_IpAddress = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_IpAddress" , "T_TerminalAddress");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireIpAddress = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireIpAddress" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SpecifiesIPAddress = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SpecifiesIPAddress" , "T_Addressing");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_IpAddress = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_IpAddress" , "T_IpAddress");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_SpecifiesHostName = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_SpecifiesHostName" , "T_Addressing2");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_HostName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_HostName" , "T_HostName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RoleId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RoleId" , "T_RoleId");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireRoleId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireRoleId" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_RoleId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_RoleId" , "T_RoleId");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search2 = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search2" , "T_P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PrinterName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PrinterName" , "T_PrinterName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_PrinterName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_PrinterName" , "T_PrinterName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AutoLogin = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AutoLogin" , "T_AutoLogin");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AutoLoginON = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AutoLoginON" , "T_AutoLoginON");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AutoLoginOFF = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AutoLoginOFF" , "T_AutoLoginOFF");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TerminalSwitch = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TerminalSwitch" , "T_TERMINALSWITCH");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_TerminalSwitch = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_TerminalSwitch" , "T_TerminalSwitch");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TerminalUser = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TerminalUser" , "T_TERMINALUSER");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_TerminalUser = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_TerminalUser" , "T_TerminalSwitch");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "T_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "T_Clear");

}
//end of class

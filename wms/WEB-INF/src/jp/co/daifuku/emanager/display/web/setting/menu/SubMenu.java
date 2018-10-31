// $Id: SubMenu.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.menu;
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
public class SubMenu extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "T_In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "T_Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "T_OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_Configure");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "T_ToMenu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MainFunctionId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MainFunctionId" , "T_MainFunctionId");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_Search" , "T_P_Search");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_MainFunctionId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_MainFunctionId" , "T_R_MainFunctionId");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MenuInfoMsg = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MenuInfoMsg" , "T_MenuInfoMsg");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ProcessingType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ProcessingType" , "T_ProcessingType");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit" , "T_Submit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Modify = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Modify" , "T_Modify");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Delete = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Delete" , "T_Delete");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MainFunctionId2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MainFunctionId2" , "T_MainFunctionId");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_MainFunctionId2 = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_MainFunctionId2" , "T_R_MainFunctionId");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FunctionDispNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FunctionDispNumber" , "T_FunctionDispNumber");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_R_FunctionDispNumber = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_R_FunctionDispNumber" , "T_R_FunctionDispNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MenuId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MenuId" , "T_MenuId");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireMenuId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireMenuId" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_MenuId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_MenuId" , "T_MenuId");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_MenuId = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_MenuId" , "T_P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MenuName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MenuName" , "T_MenuName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_MenuName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_MenuName" , "T_R_MenuName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FunctionResourceKey = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FunctionResourceKey" , "T_FunctionResouceKey");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireFunctionResourceKey = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireFunctionResourceKey" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_FunctionResourceKey = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_FunctionResourceKey" , "T_FunctionResourceKey");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_P_FunctionResouceKey = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_P_FunctionResouceKey" , "T_P_Search");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FunctionName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FunctionName" , "T_FunctionName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_R_FunctionName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_R_FunctionName" , "T_R_FunctionName");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "T_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "T_Clear");

}
//end of class

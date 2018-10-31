// $Id: SubMenuOrder.java 3965 2009-04-06 02:55:05Z admin $

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
public class SubMenuOrder extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "T_In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "T_Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "T_OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_Configure");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "T_ToMenu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MainMenu = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MainMenu" , "T_MainMenu");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_MainMenu = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_MainMenu" , "T_MainMenu");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_View = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_View" , "T_View");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_FunctionOrder = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_FunctionOrder" , "T_FunctionOrder");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_DisplayItem = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DisplayItem" , "T_DisplayItem");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_NotUseItem = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_NotUseItem" , "T_NotUseItem");
	public jp.co.daifuku.bluedog.ui.control.ImageButton img_ArrowUp = jp.co.daifuku.bluedog.ui.control.ImageButtonFactory.getInstance("img_ArrowUp" , "T_ArrowUpSync");
	public jp.co.daifuku.bluedog.ui.control.ImageButton img_ArrowDown = jp.co.daifuku.bluedog.ui.control.ImageButtonFactory.getInstance("img_ArrowDown" , "T_ArrowDownSync");
	public jp.co.daifuku.bluedog.ui.control.ListBox ltb_FunctionOrder = jp.co.daifuku.bluedog.ui.control.ListBoxFactory.getInstance("ltb_FunctionOrder" , "T_SubMenuOrder");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Add = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Add" , "T_Add");
	public jp.co.daifuku.bluedog.ui.control.ImageButton img_ArrowLeft = jp.co.daifuku.bluedog.ui.control.ImageButtonFactory.getInstance("img_ArrowLeft" , "T_ArrowLeftSync");
	public jp.co.daifuku.bluedog.ui.control.ImageButton img_ArrowRight = jp.co.daifuku.bluedog.ui.control.ImageButtonFactory.getInstance("img_ArrowRight" , "T_ArrowRightSync");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Delete = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Delete" , "T_Delete");
	public jp.co.daifuku.bluedog.ui.control.ListBox ltb_FunctionOrder2 = jp.co.daifuku.bluedog.ui.control.ListBoxFactory.getInstance("ltb_FunctionOrder2" , "T_SubMenuOrder");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "T_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "T_Cancel");

}
//end of class

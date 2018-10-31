// $Id: PCTUserWorkInquiry.java 3209 2009-03-02 06:34:19Z arai $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.retrieval.display.pctuserworkinquiry;
import jp.co.daifuku.bluedog.ui.control.Page;

/**
 * ツールが生成した不可変クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  $Author: arai $
 */
public class PCTUserWorkInquiry extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_Inquiry2");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StdLotNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StdLotNumber" , "W_StdLotNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StdLotNumberDisp = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StdLotNumberDisp" , "W_In_JavaSet");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PerHour = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PerHour" , "W_PerHour");
	public jp.co.daifuku.bluedog.ui.control.VerticalBarLineChart vbc_VerticalChart = jp.co.daifuku.bluedog.ui.control.VerticalBarLineChartFactory.getInstance("vbc_VerticalChart" , "W_V_PCTUserWorkInquiry");
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_UserWorkInquiry = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_UserWorkInquiry" , "W_UserWorkInquiry");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Display" , "W_Display");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_PrevPage = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_PrevPage" , "W_PrevPage");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_NextPage = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_NextPage" , "W_NextPage");

}
//end of class

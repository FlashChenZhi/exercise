// $Id: SearchAuthLog2.java 7279 2010-02-26 10:07:48Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.logview;
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
 * @version $Revision: 7279 $, $Date: 2010-02-26 19:07:48 +0900 (金, 26 2 2010) $
 * @author  $Author: okayama $
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
 * @version $Revision: 7279 $, $Date: 2010-02-26 19:07:48 +0900 (金, 26 2 2010) $
 * @author  $Author: okayama $
 </en> */
public class SearchAuthLog2 extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "T_In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "T_Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "T_OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "T_CsvOut");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "T_Back");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_MenuNoFunc");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LogDateRetrievalPeriod = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LogDateRetrievalPeriod" , "T_RetrievalPeriod");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireLogDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireLogDate" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_LogDateRetrievalBeginning = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_LogDateRetrievalBeginning" , "T_RetrievalBeginning");
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_LogTimeRetrievalBeginn = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_LogTimeRetrievalBeginn" , "T_RetrievalBeginning");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen" , "T_Hyphen");
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_LogDateRetrievalEnd = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_LogDateRetrievalEnd" , "T_RetrievalEnd");
	public jp.co.daifuku.bluedog.ui.control.TimeTextBox txt_LogTimeRetrievalEnd = jp.co.daifuku.bluedog.ui.control.TimeTextBoxFactory.getInstance("txt_LogTimeRetrievalEnd" , "T_RetrievalEnd");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_LogClass = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_LogClass" , "T_LogClass");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireLogClass = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireLogClass" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_LogClass = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_LogClass" , "T_LogClass");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserId" , "T_UserId");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RequireUserId = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RequireUserId" , "T_Require");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserId = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserId" , "T_UserId");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchUserId = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchUserId" , "T_P_Search");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_SearchLog = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_SearchLog" , "T_LogSearchPopup");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "T_Clear");

}
//end of class

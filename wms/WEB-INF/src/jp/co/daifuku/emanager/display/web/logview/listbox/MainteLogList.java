// $Id: MainteLogList.java 6715 2010-01-20 01:37:21Z kajiwara $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.logview.listbox;
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
 * @version $Revision: 6715 $, $Date: 2010-01-20 10:37:21 +0900 (水, 20 1 2010) $
 * @author  $Author: kajiwara $
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
 * @version $Revision: 6715 $, $Date: 2010-01-20 10:37:21 +0900 (水, 20 1 2010) $
 * @author  $Author: kajiwara $
 </en> */
public class MainteLogList extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListName" , "T_In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "T_LstOperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Pager pager = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pager" , "T_PagerSync");
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_LogList = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_LogList" , "T_SeachLogResultList");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Message = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Message" , "T_Message");
	public jp.co.daifuku.bluedog.ui.control.TextArea txa_Message = jp.co.daifuku.bluedog.ui.control.TextAreaFactory.getInstance("txa_Message" , "T_LogMessage");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Detail = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Detail" , "T_Detail");
	public jp.co.daifuku.bluedog.ui.control.TextArea txa_Detail = jp.co.daifuku.bluedog.ui.control.TextAreaFactory.getInstance("txa_Detail" , "T_LogDetail");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close" , "T_Close");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "T_Clear");

}
//end of class

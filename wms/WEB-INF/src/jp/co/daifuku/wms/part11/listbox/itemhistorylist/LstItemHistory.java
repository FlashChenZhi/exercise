// $Id: LstItemHistory.java 7707 2010-03-20 07:23:11Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.part11.listbox.itemhistorylist;
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
 * @version $Revision: 7707 $, $Date: 2010-03-20 16:23:11 +0900 (土, 20 3 2010) $
 * @author  $Author: okayama $
 */
public class LstItemHistory extends Page
{

	// Class variables -----------------------------------------------

	/**
	 * ControlID	lbl_SettingName
	 * TemplateKey	In_SettingName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");

	/**
	 * ControlID	message
	 * TemplateKey	OperationMsg
	 * ControlType	Message
	 */
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "W_LstOperationMsg");

	/**
	 * ControlID	lst_SearchCondition
	 * TemplateKey	SearchConditionTwoColumn
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_SearchCondition = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_SearchCondition" , "SearchConditionTwoColumn");

	/**
	 * ControlID	pgr_U
	 * TemplateKey	Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_U = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_U" , "Pager");

	/**
	 * ControlID	lst_ItemHistoryList
	 * TemplateKey	W_ItemHistoryList
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ItemHistoryList = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ItemHistoryList" , "W_ItemHistoryList");

	/**
	 * ControlID	pgr_D
	 * TemplateKey	Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_D = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_D" , "Pager");

	/**
	 * ControlID	btn_Close_D
	 * TemplateKey	W_Close
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_D = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_D" , "W_Close");

}
//end of class

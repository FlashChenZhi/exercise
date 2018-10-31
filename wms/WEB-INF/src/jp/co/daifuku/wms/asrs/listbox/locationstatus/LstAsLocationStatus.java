// $Id: LstAsLocationStatus.java 6580 2009-12-21 08:12:57Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.listbox.locationstatus;
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
 * @version $Revision: 6580 $, $Date: 2009-12-21 17:12:57 +0900 (月, 21 12 2009) $
 * @author  $Author: okayama $
 */
public class LstAsLocationStatus extends Page
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
	 * TemplateKey	W_LstOperationMsg
	 * ControlType	Message
	 */
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "W_LstOperationMsg");

	/**
	 * ControlID	lst_SearchConditionOneColumn
	 * TemplateKey	SearchConditionTwoColumn
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_SearchConditionOneColumn = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_SearchConditionOneColumn" , "SearchConditionTwoColumn");

	/**
	 * ControlID	pager_up
	 * TemplateKey	Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pager_up = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pager_up" , "Pager");

	/**
	 * ControlID	btn_Close_Up
	 * TemplateKey	W_Close
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_Up = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_Up" , "W_Close");

	/**
	 * ControlID	lst_StatusByLocationWithButton
	 * TemplateKey	W_StatusByLocationIncludeButtonList
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_StatusByLocationWithButton = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_StatusByLocationWithButton" , "W_StatusByLocationIncludeButtonList");

	/**
	 * ControlID	pager_down
	 * TemplateKey	Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pager_down = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pager_down" , "Pager");

	/**
	 * ControlID	btn_Close_Down
	 * TemplateKey	W_Close
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_Down = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_Down" , "W_Close");

}
//end of class

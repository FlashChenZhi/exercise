// $Id: skelten.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.listbox.faworkdetail;
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
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class FaLstAsWorkDetail extends Page
{

	// Class variables -----------------------------------------------

	/**
	 * ControlID	lbl_ListName
	 * TemplateKey	In_SettingName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListName" , "In_SettingName");

	/**
	 * ControlID	message
	 * TemplateKey	OperationMsg
	 * ControlType	Message
	 */
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "W_LstOperationMsg");

	/**
	 * ControlID	lst_SearchCondition
	 * TemplateKey	SearchConditionOneColumn
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_SearchCondition = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_SearchCondition" , "SearchConditionOneColumn");

	/**
	 * ControlID	pager
	 * TemplateKey	Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pager = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pager" , "Pager");

	/**
	 * ControlID	lst_WorkInquiryList
	 * TemplateKey	W_WorkInquiryList
	 * ControlType	ScrollListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_WorkInquiryList = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_WorkInquiryList" , "W_WorkInquiryList");

	/**
	 * ControlID	btn_Close
	 * TemplateKey	Close
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close" , "Close");

}
//end of class
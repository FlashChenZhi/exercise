// $Id: LstStoragePlanMnt.java 6578 2009-12-21 06:00:32Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.listbox.plan;
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
 * @version $Revision: 6578 $, $Date: 2009-12-21 15:00:32 +0900 (月, 21 12 2009) $
 * @author  $Author: okayama $
 */
public class LstStoragePlanMnt extends Page
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
	 * TemplateKey	W_LstOperationMsg
	 * ControlType	Message
	 */
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "W_LstOperationMsg");

	/**
	 * ControlID	pager
	 * TemplateKey	Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pager = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pager" , "Pager");

	/**
	 * ControlID	lst_StoragePlan
	 * TemplateKey	W_StoragePlanList
	 * ControlType	ScrollListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ScrollListCell lst_StoragePlan = jp.co.daifuku.bluedog.ui.control.ScrollListCellFactory.getInstance("lst_StoragePlan" , "W_StoragePlanList");

	/**
	 * ControlID	btn_Close
	 * TemplateKey	W_Close
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close" , "W_Close");

}
//end of class

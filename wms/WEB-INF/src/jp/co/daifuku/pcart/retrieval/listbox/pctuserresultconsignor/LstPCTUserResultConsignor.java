// $Id: LstPCTUserResultConsignor.java 6602 2009-12-23 01:16:12Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.retrieval.listbox.pctuserresultconsignor;
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
 * @version $Revision: 6602 $, $Date: 2009-12-23 10:16:12 +0900 (水, 23 12 2009) $
 * @author  $Author: okayama $
 */
public class LstPCTUserResultConsignor extends Page
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
	 * ControlID	lst_SearchCondition
	 * TemplateKey	SearchConditionTwoColumn
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_SearchCondition = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_SearchCondition" , "SearchConditionTwoColumn");

	/**
	 * ControlID	lst_Header
	 * TemplateKey	W_PCTUserResultAvg2Header
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_Header = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_Header" , "W_PCTUserResultAvg2Header");

	/**
	 * ControlID	lst_UserResultSummaryAvg
	 * TemplateKey	W_PCTUserResultSummaryAvg
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_UserResultSummaryAvg = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_UserResultSummaryAvg" , "W_PCTUserResultSummaryAvg");

	/**
	 * ControlID	pgr_U
	 * TemplateKey	Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_U = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_U" , "Pager");

	/**
	 * ControlID	btn_Preview
	 * TemplateKey	W_Preview
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Preview = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Preview" , "W_Preview");

	/**
	 * ControlID	btn_Print
	 * TemplateKey	W_Print
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Print = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Print" , "W_Print");

	/**
	 * ControlID	btn_XLS
	 * TemplateKey	W_XLS
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_XLS = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_XLS" , "W_XLS");

	/**
	 * ControlID	btn_Close_U
	 * TemplateKey	Close
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_U = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_U" , "Close");

	/**
	 * ControlID	lst_UserResultConsignorList
	 * TemplateKey	W_PCTUserResultConsignorList
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_UserResultConsignorList = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_UserResultConsignorList" , "W_PCTUserResultConsignorList");

	/**
	 * ControlID	slb_Download
	 * TemplateKey	W_Download
	 * ControlType	SubmitLabel
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitLabel slb_Download = jp.co.daifuku.bluedog.ui.control.SubmitLabelFactory.getInstance("slb_Download" , "W_Download");

	/**
	 * ControlID	slb_Preview
	 * TemplateKey	W_Preview
	 * ControlType	SubmitLabel
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitLabel slb_Preview = jp.co.daifuku.bluedog.ui.control.SubmitLabelFactory.getInstance("slb_Preview" , "W_Preview");

	/**
	 * ControlID	pgr_D
	 * TemplateKey	Pager
	 * ControlType	Pager
	 */
	public jp.co.daifuku.bluedog.ui.control.Pager pgr_D = jp.co.daifuku.bluedog.ui.control.PagerFactory.getInstance("pgr_D" , "Pager");

	/**
	 * ControlID	btn_Close_D
	 * TemplateKey	Close
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_D = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_D" , "Close");

}
//end of class

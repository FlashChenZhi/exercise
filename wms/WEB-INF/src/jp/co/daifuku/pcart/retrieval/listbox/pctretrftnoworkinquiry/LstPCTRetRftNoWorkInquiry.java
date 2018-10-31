// $Id: LstPCTRetRftNoWorkInquiry.java 6602 2009-12-23 01:16:12Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.retrieval.listbox.pctretrftnoworkinquiry;
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
public class LstPCTRetRftNoWorkInquiry extends Page
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
	 * TemplateKey	SearchConditionOneColumn
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_SearchCondition = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_SearchCondition" , "SearchConditionOneColumn");

	/**
	 * ControlID	lbl_UserName
	 * TemplateKey	W_UserName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UserName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserName" , "W_UserName");

	/**
	 * ControlID	txt_UserName
	 * TemplateKey	W_UserName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_UserName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_UserName" , "W_UserName");

	/**
	 * ControlID	lbl_Area
	 * TemplateKey	W_Area
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Area = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Area" , "W_Area");

	/**
	 * ControlID	txt_AreaNo
	 * TemplateKey	W_Area
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_AreaNo = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_AreaNo" , "W_Area");

	/**
	 * ControlID	txt_AreaName
	 * TemplateKey	W_AreaName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_AreaName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_AreaName" , "W_AreaName");

	/**
	 * ControlID	lst_PCTRetRftNoWorkInquiry2
	 * TemplateKey	W_PCTRetRftNoWorkInquiry2
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_PCTRetRftNoWorkInquiry2 = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_PCTRetRftNoWorkInquiry2" , "W_PCTRetRftNoWorkInquiry2");

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
	 * ControlID	lst_PCTRetRftNoWorkInquiry
	 * TemplateKey	W_PCTRetRftNoWorkInquiry
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_PCTRetRftNoWorkInquiry = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_PCTRetRftNoWorkInquiry" , "W_PCTRetRftNoWorkInquiry");

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

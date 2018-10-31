// $Id: ProductionList.java 1792 2008-12-08 05:41:34Z okamura $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.productionlist;
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
 * @version $Revision: 1792 $, $Date: 2008-12-08 14:41:34 +0900 (月, 08 12 2008) $
 * @author  $Author: okamura $
 */
public class ProductionList extends Page
{

	// Class variables -----------------------------------------------

	/**
	 * ControlID	lbl_ListName
	 * TemplateKey	In_SettingName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ListName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ListName" , "In_SettingName");

	/**
	 * ControlID	btn_Close_U
	 * TemplateKey	W_Close
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_U = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_U" , "W_Close");

	/**
	 * ControlID	lbl_InMsg
	 * TemplateKey	AST_In_ErrorJsp
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InMsg = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InMsg" , "AST_In_ErrorJsp");

	/**
	 * ControlID	lst_ProductionNumberList
	 * TemplateKey	AST_ProductionNumberList
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_ProductionNumberList = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_ProductionNumberList" , "AST_ProductionNumberList");

	/**
	 * ControlID	btn_Close_D
	 * TemplateKey	W_Close
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_D = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_D" , "W_Close");

}
//end of class

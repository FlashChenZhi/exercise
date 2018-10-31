// $Id: WorkingTimeConfig2.java 571 2008-10-23 04:38:24Z nakai $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.analysis.display.workingtimeconfig;
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
 * @version $Revision: 571 $, $Date: 2008-10-23 13:38:24 +0900 (木, 23 10 2008) $
 * @author  $Author: nakai $
 */
public class WorkingTimeConfig2 extends Page
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
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");

	/**
	 * ControlID	lbl_AveTimePerItem
	 * TemplateKey	W_SecPerItem
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AveTimePerItem = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AveTimePerItem" , "W_SecPerItem");

	/**
	 * ControlID	txt_AveTimeItem
	 * TemplateKey	W_WorkUnitTime
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_AveTimeItem = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_AveTimeItem" , "W_WorkUnitTime");

	/**
	 * ControlID	lbl_AveTimeUnit
	 * TemplateKey	W_UnitSecond
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AveTimeUnit = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AveTimeUnit" , "W_UnitSecond");

	/**
	 * ControlID	lbl_AvePiecePerItem
	 * TemplateKey	W_AvePiecePerItem
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AvePiecePerItem = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AvePiecePerItem" , "W_AvePiecePerItem");

	/**
	 * ControlID	txt_AvePiece
	 * TemplateKey	W_PieceQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_AvePiece = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_AvePiece" , "W_PieceQty");

	/**
	 * ControlID	lbl_AveTimePerPiece
	 * TemplateKey	W_SecPerPiece
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AveTimePerPiece = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AveTimePerPiece" , "W_SecPerPiece");

	/**
	 * ControlID	txt_AveTimePiece
	 * TemplateKey	W_WorkUnitTime
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_AveTimePiece = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_AveTimePiece" , "W_WorkUnitTime");

	/**
	 * ControlID	lbl_AveTimeUnitP
	 * TemplateKey	W_UnitSecond
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AveTimeUnitP = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AveTimeUnitP" , "W_UnitSecond");

	/**
	 * ControlID	btn_Close_U
	 * TemplateKey	W_Close
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_U = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_U" , "W_Close");

	/**
	 * ControlID	vbc_Chart
	 * TemplateKey	W_WorkTimeResult
	 * ControlType	VerticalBarLineChart
	 */
	public jp.co.daifuku.bluedog.ui.control.VerticalBarLineChart vbc_Chart = jp.co.daifuku.bluedog.ui.control.VerticalBarLineChartFactory.getInstance("vbc_Chart" , "W_WorkTimeResult");

}
//end of class

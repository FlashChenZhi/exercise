// $Id: WorkingTimeConfig.java 571 2008-10-23 04:38:24Z nakai $

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
public class WorkingTimeConfig extends Page
{

	// Class variables -----------------------------------------------

	/**
	 * ControlID	lbl_SettingName
	 * TemplateKey	In_SettingName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");

	/**
	 * ControlID	btn_Help
	 * TemplateKey	Help
	 * ControlType	LinkButton
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");

	/**
	 * ControlID	message
	 * TemplateKey	OperationMsg
	 * ControlType	Message
	 */
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");

	/**
	 * ControlID	tab
	 * TemplateKey	W_InputCond
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_InputCond");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_TimeSimuInput
	 * TemplateKey	W_TimeSimuInput
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TimeSimuInput = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TimeSimuInput" , "W_TimeSimuInput");

	/**
	 * ControlID	lbl_TimeSimuResult
	 * TemplateKey	W_TimeSimuResult
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_TimeSimuResult = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TimeSimuResult" , "W_TimeSimuResult");

	/**
	 * ControlID	lbl_SecPerItem
	 * TemplateKey	W_SecPerItem
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SecPerItem = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SecPerItem" , "W_SecPerItem");

	/**
	 * ControlID	lbl_SecPerItemRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SecPerItemRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SecPerItemRequire" , "W_Require");

	/**
	 * ControlID	lbl_SecPerPiece
	 * TemplateKey	W_SecPerPiece
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SecPerPiece = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SecPerPiece" , "W_SecPerPiece");

	/**
	 * ControlID	lbl_SecPerPieceRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SecPerPieceRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SecPerPieceRequire" , "W_Require");

	/**
	 * ControlID	lbl_SecPerItemResult
	 * TemplateKey	W_SecPerItem
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SecPerItemResult = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SecPerItemResult" , "W_SecPerItem");

	/**
	 * ControlID	lbl_AvePiecePerItem
	 * TemplateKey	W_AvePiecePerItem
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AvePiecePerItem = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AvePiecePerItem" , "W_AvePiecePerItem");

	/**
	 * ControlID	lbl_SecPerPieceResult
	 * TemplateKey	W_SecPerPiece
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SecPerPieceResult = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SecPerPieceResult" , "W_SecPerPiece");

	/**
	 * ControlID	lbl_StorageWork
	 * TemplateKey	W_StorageWork
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageWork = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageWork" , "W_StorageWork");

	/**
	 * ControlID	txt_StorageSecPerItem
	 * TemplateKey	W_WorkUnitTime
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StorageSecPerItem = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StorageSecPerItem" , "W_WorkUnitTime");

	/**
	 * ControlID	lbl_StorageUnitSecondItem
	 * TemplateKey	W_UnitSecond
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageUnitSecondItem = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageUnitSecondItem" , "W_UnitSecond");

	/**
	 * ControlID	txt_StorageSecPerPiece
	 * TemplateKey	W_WorkUnitTimePiece
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StorageSecPerPiece = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StorageSecPerPiece" , "W_WorkUnitTimePiece");

	/**
	 * ControlID	lbl_StorageUnitSecondWork
	 * TemplateKey	W_UnitSecond
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageUnitSecondWork = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageUnitSecondWork" , "W_UnitSecond");

	/**
	 * ControlID	txt_StorageAveTimeItem
	 * TemplateKey	W_WorkUnitTime
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StorageAveTimeItem = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StorageAveTimeItem" , "W_WorkUnitTime");

	/**
	 * ControlID	lbl_StorageAveTimeUnit
	 * TemplateKey	W_UnitSecond
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageAveTimeUnit = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageAveTimeUnit" , "W_UnitSecond");

	/**
	 * ControlID	txt_StorageAvePiece
	 * TemplateKey	W_PieceQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StorageAvePiece = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StorageAvePiece" , "W_PieceQty");

	/**
	 * ControlID	txt_StorageAveTimePiece
	 * TemplateKey	W_WorkUnitTime
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_StorageAveTimePiece = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_StorageAveTimePiece" , "W_WorkUnitTime");

	/**
	 * ControlID	lbl_StorageAveTimeUnitP
	 * TemplateKey	W_UnitSecond
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StorageAveTimeUnitP = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StorageAveTimeUnitP" , "W_UnitSecond");

	/**
	 * ControlID	btn_StorageWkTimeResult
	 * TemplateKey	W_P_WorkTimeResult
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_StorageWkTimeResult = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_StorageWkTimeResult" , "W_P_WorkTimeResult");

	/**
	 * ControlID	lbl_RetrievalWork
	 * TemplateKey	W_RetrievalWork
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalWork = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalWork" , "W_RetrievalWork");

	/**
	 * ControlID	txt_RetrievalSecPerItem
	 * TemplateKey	W_WorkUnitTime
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RetrievalSecPerItem = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RetrievalSecPerItem" , "W_WorkUnitTime");

	/**
	 * ControlID	lbl_RetrievalUnitSecondItem
	 * TemplateKey	W_UnitSecond
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalUnitSecondItem = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalUnitSecondItem" , "W_UnitSecond");

	/**
	 * ControlID	txt_RetrievalSecPerPiece
	 * TemplateKey	W_WorkUnitTimePiece
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RetrievalSecPerPiece = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RetrievalSecPerPiece" , "W_WorkUnitTimePiece");

	/**
	 * ControlID	lbl_RetrievalUnitSecondWork
	 * TemplateKey	W_UnitSecond
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalUnitSecondWork = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalUnitSecondWork" , "W_UnitSecond");

	/**
	 * ControlID	txt_RetrievalAveTimeItem
	 * TemplateKey	W_WorkUnitTime
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RetrievalAveTimeItem = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RetrievalAveTimeItem" , "W_WorkUnitTime");

	/**
	 * ControlID	lbl_RetrievalAveTimeUnit
	 * TemplateKey	W_UnitSecond
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalAveTimeUnit = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalAveTimeUnit" , "W_UnitSecond");

	/**
	 * ControlID	txt_RetrievalAvePiece
	 * TemplateKey	W_PieceQty
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RetrievalAvePiece = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RetrievalAvePiece" , "W_PieceQty");

	/**
	 * ControlID	txt_RetrievalAveTimePiece
	 * TemplateKey	W_WorkUnitTime
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_RetrievalAveTimePiece = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_RetrievalAveTimePiece" , "W_WorkUnitTime");

	/**
	 * ControlID	lbl_RetrievalAveTimeUnitP
	 * TemplateKey	W_UnitSecond
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_RetrievalAveTimeUnitP = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RetrievalAveTimeUnitP" , "W_UnitSecond");

	/**
	 * ControlID	btn_RetrievalWkTimeResult
	 * TemplateKey	W_P_WorkTimeResult
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_RetrievalWkTimeResult = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_RetrievalWkTimeResult" , "W_P_WorkTimeResult");

	/**
	 * ControlID	lbl_SecPerItemGuide
	 * TemplateKey	W_SecPerItem
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SecPerItemGuide = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SecPerItemGuide" , "W_SecPerItem");

	/**
	 * ControlID	lbl_GuideTimePerItem1
	 * TemplateKey	W_GuideTimePerItem1
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_GuideTimePerItem1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_GuideTimePerItem1" , "W_GuideTimePerItem1");

	/**
	 * ControlID	lbl_SecPerPieceGuide
	 * TemplateKey	W_SecPerPiece
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SecPerPieceGuide = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SecPerPieceGuide" , "W_SecPerPiece");

	/**
	 * ControlID	lbl_GuideTimePerPiece
	 * TemplateKey	W_GuideTimePerPiece
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_GuideTimePerPiece = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_GuideTimePerPiece" , "W_GuideTimePerPiece");

	/**
	 * ControlID	btn_Setting
	 * TemplateKey	W_Set2
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Setting = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Setting" , "W_Set2");

	/**
	 * ControlID	btn_Restore
	 * TemplateKey	W_Restore
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Restore = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Restore" , "W_Restore");

	/**
	 * ControlID	btn_Reflesh
	 * TemplateKey	W_ReDisplay
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Reflesh = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Reflesh" , "W_ReDisplay");

}
//end of class

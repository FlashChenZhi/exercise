// $Id: ShippingResultABCAnalysis.java 7138 2010-02-18 07:55:09Z shibamoto $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.analysis.display.shippingresultabcanalysis;
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
 * @version $Revision: 7138 $, $Date: 2010-02-18 16:55:09 +0900 (木, 18 2 2010) $
 * @author  $Author: shibamoto $
 */
public class ShippingResultABCAnalysis extends Page
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
	 * TemplateKey	W_ABCAnalysis
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_ABCAnalysis");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_AnalysisTerm
	 * TemplateKey	W_AnalysisTerm
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AnalysisTerm = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AnalysisTerm" , "W_AnalysisTerm");

	/**
	 * ControlID	lbl_AnalysisTermRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AnalysisTermRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AnalysisTermRequire" , "W_Require");

	/**
	 * ControlID	txt_AnaFromDate
	 * TemplateKey	W_AnalysisDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_AnaFromDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_AnaFromDate" , "W_AnalysisDate");

	/**
	 * ControlID	lbl_fromTo
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_fromTo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_fromTo" , "W_Range");

	/**
	 * ControlID	txt_AnaToDate
	 * TemplateKey	W_AnalysisDate
	 * ControlType	DateTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.DateTextBox txt_AnaToDate = jp.co.daifuku.bluedog.ui.control.DateTextBoxFactory.getInstance("txt_AnaToDate" , "W_AnalysisDate");

	/**
	 * ControlID	lbl_InputStyle
	 * TemplateKey	W_InputStyle
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_InputStyle = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_InputStyle" , "W_InputStyle");

	/**
	 * ControlID	lbl_Day
	 * TemplateKey	W_InputStyleDay
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Day = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Day" , "W_InputStyleDay");

	/**
	 * ControlID	lbl_CustomerCode
	 * TemplateKey	W_CustomerCode
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerCode" , "W_CustomerCode");

	/**
	 * ControlID	txt_CustomerCode
	 * TemplateKey	W_CustomerCode
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerCode = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerCode" , "W_CustomerCode");

	/**
	 * ControlID	btn_CustomerSearch
	 * TemplateKey	W_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_CustomerSearch = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_CustomerSearch" , "W_P_Search");

	/**
	 * ControlID	lbl_CustomerName
	 * TemplateKey	W_CustomerName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerName" , "W_CustomerName");

	/**
	 * ControlID	txt_CustomerName
	 * TemplateKey	W_CustomerName
	 * ControlType	FreeTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_CustomerName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_CustomerName" , "W_CustomerName");

	/**
	 * ControlID	lbl_AnalysisType
	 * TemplateKey	W_AnalysisType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AnalysisType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AnalysisType" , "W_AnalysisType");

	/**
	 * ControlID	rdo_WorkingCnt
	 * TemplateKey	W_WorkingCnt
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_WorkingCnt = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_WorkingCnt" , "W_WorkingCnt");

	/**
	 * ControlID	rdo_ShippingCnt
	 * TemplateKey	W_ShippingCnt
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ShippingCnt = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ShippingCnt" , "W_ShippingCnt");

	/**
	 * ControlID	lbl_ThresholdA
	 * TemplateKey	W_ThresholdA
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ThresholdA = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ThresholdA" , "W_ThresholdA");

	/**
	 * ControlID	lbl_ThresholdARequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ThresholdARequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ThresholdARequire" , "W_Require");

	/**
	 * ControlID	txt_ThresholdA
	 * TemplateKey	W_ThresholdA
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ThresholdA = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ThresholdA" , "W_ThresholdA");

	/**
	 * ControlID	lbl_ThresholdUnitA
	 * TemplateKey	W_ThresholdUnit
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ThresholdUnitA = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ThresholdUnitA" , "W_ThresholdUnit");

	/**
	 * ControlID	lbl_ThresholdB
	 * TemplateKey	W_ThresholdB
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ThresholdB = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ThresholdB" , "W_ThresholdB");

	/**
	 * ControlID	lbl_ThresholdBRequire
	 * TemplateKey	W_Require
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ThresholdBRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ThresholdBRequire" , "W_Require");

	/**
	 * ControlID	txt_ThresholdB
	 * TemplateKey	W_ThresholdB
	 * ControlType	NumberTextBox
	 */
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_ThresholdB = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_ThresholdB" , "W_ThresholdB");

	/**
	 * ControlID	lbl_ThresholdUnitB
	 * TemplateKey	W_ThresholdUnit
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ThresholdUnitB = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ThresholdUnitB" , "W_ThresholdUnit");

	/**
	 * ControlID	btn_Next
	 * TemplateKey	W_NextGraph
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Next = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Next" , "W_NextGraph");

	/**
	 * ControlID	btn_ListAll
	 * TemplateKey	W_P_ListAll
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ListAll = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ListAll" , "W_P_ListAll");

	/**
	 * ControlID	btn_ListA
	 * TemplateKey	W_P_ListA
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ListA = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ListA" , "W_P_ListA");

	/**
	 * ControlID	btn_ListB
	 * TemplateKey	W_P_ListB
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ListB = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ListB" , "W_P_ListB");

	/**
	 * ControlID	btn_ListC
	 * TemplateKey	W_P_ListC
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ListC = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ListC" , "W_P_ListC");

}
//end of class

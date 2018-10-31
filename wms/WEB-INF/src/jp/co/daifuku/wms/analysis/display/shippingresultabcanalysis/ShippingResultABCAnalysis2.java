// $Id: ShippingResultABCAnalysis2.java 879 2008-10-29 00:22:35Z tanaka $

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
 * @version $Revision: 879 $, $Date: 2008-10-29 09:22:35 +0900 (水, 29 10 2008) $
 * @author  $Author: tanaka $
 */
public class ShippingResultABCAnalysis2 extends Page
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
	 * ControlID	btn_Back
	 * TemplateKey	W_Back
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Back = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Back" , "W_Back");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_MenuNoFunc
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_MenuNoFunc");

	/**
	 * ControlID	lbl_AnalysisTerm
	 * TemplateKey	W_AnalysisTerm
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AnalysisTerm = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AnalysisTerm" , "W_AnalysisTerm");

	/**
	 * ControlID	lbl_AnaFromDate
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AnaFromDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AnaFromDate" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_fromTo
	 * TemplateKey	W_Range
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_fromTo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_fromTo" , "W_Range");

	/**
	 * ControlID	lbl_AnaToDate
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AnaToDate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AnaToDate" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_Customer
	 * TemplateKey	W_Customer
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Customer = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Customer" , "W_Customer");

	/**
	 * ControlID	lbl_CustomerCode
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerCode = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerCode" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_CustomerName
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CustomerName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CustomerName" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_AnalysisTypeLabel
	 * TemplateKey	W_AnalysisType
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AnalysisTypeLabel = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AnalysisTypeLabel" , "W_AnalysisType");

	/**
	 * ControlID	lbl_AnalysisType
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AnalysisType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AnalysisType" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_ThresholdALabel
	 * TemplateKey	W_ThresholdA
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ThresholdALabel = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ThresholdALabel" , "W_ThresholdA");

	/**
	 * ControlID	lbl_ThresholdA
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ThresholdA = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ThresholdA" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_ThresholdUnitA
	 * TemplateKey	W_ThresholdUnit
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ThresholdUnitA = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ThresholdUnitA" , "W_ThresholdUnit");

	/**
	 * ControlID	lbl_ThresholdBLabel
	 * TemplateKey	W_ThresholdB
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ThresholdBLabel = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ThresholdBLabel" , "W_ThresholdB");

	/**
	 * ControlID	lbl_ThresholdB
	 * TemplateKey	W_In_JavaSet
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ThresholdB = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ThresholdB" , "W_In_JavaSet");

	/**
	 * ControlID	lbl_ThresholdUnitB
	 * TemplateKey	W_ThresholdUnit
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ThresholdUnitB = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ThresholdUnitB" , "W_ThresholdUnit");

	/**
	 * ControlID	glc_Chart
	 * TemplateKey	W_ShipResultABC
	 * ControlType	GeneralChart
	 */
	public jp.co.daifuku.bluedog.ui.control.GeneralChart glc_Chart = jp.co.daifuku.bluedog.ui.control.GeneralChartFactory.getInstance("glc_Chart" , "W_ShipResultABC");

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

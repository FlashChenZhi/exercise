// $Id: WeightErrorRateSetting.java 3209 2009-03-02 06:34:19Z arai $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.master.display.weighterrorratesetting;
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
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  $Author: arai $
 */
public class WeightErrorRateSetting extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "W_ErrorRateSetting");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_UnitWeight_g = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UnitWeight_g" , "W_UnitWeight_g");
    public jp.co.daifuku.bluedog.ui.control.Label lbl_WeightRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WeightRequire" , "W_Require");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_UnitWeightFrom = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_UnitWeightFrom" , "W_UnitWeight");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range" , "W_Range");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_UnitWeightTo = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_UnitWeightTo" , "W_UnitWeight");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ErrorRate = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ErrorRate" , "W_ErrorRate");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ConsignorRequire = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ConsignorRequire" , "W_Require");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_WeightErrorRate = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_WeightErrorRate" , "W_WeightErrorRate");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Range1_49 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Range1_49" , "W_Range1_49");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Set = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Set" , "W_Set2");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Display = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Display" , "W_P_Display");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "W_Clear");
    
}
//end of class

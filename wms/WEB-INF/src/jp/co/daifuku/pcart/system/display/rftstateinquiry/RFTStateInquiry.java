// $Id: RFTStateInquiry.java 3990 2009-04-07 06:26:00Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.pcart.system.display.rftstateinquiry;
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
 * @version $Revision: 3990 $, $Date: 2009-04-07 15:26:00 +0900 (火, 07 4 2009) $
 * @author  $Author: okayama $
 */
public class RFTStateInquiry extends Page
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
	 * ControlID	tab_Maintenance
	 * TemplateKey	W_Inquiry2
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Maintenance = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Maintenance" , "W_Inquiry2");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_OderOfDsplay
	 * TemplateKey	W_OderOfDsplay
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_OderOfDsplay = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_OderOfDsplay" , "W_OderOfDsplay");

	/**
	 * ControlID	rdo_DisplayOder_Area
	 * TemplateKey	P_DisplayOder_Area
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_DisplayOder_Area = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_DisplayOder_Area" , "P_DisplayOder_Area");

	/**
	 * ControlID	rdo_DisplayOder_RFTNo
	 * TemplateKey	P_DisplayOder_RFTNo
	 * ControlType	RadioButton
	 */
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_DisplayOder_RFTNo = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_DisplayOder_RFTNo" , "P_DisplayOder_RFTNo");

	/**
	 * ControlID	lst_RFTState
	 * TemplateKey	P_RFTStateDisp
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_RFTState = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_RFTState" , "P_RFTStateDisp");

	/**
	 * ControlID	btn_ReDisplay
	 * TemplateKey	W_ReDisplayFunc
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ReDisplay = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ReDisplay" , "W_ReDisplayFunc");

}
//end of class

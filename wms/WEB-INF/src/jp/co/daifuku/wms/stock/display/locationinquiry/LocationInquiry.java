// $Id: LocationInquiry.java 144 2008-10-09 04:38:26Z nakai $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.stock.display.locationinquiry;
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
 * @version $Revision: 144 $, $Date: 2008-10-09 13:38:26 +0900 (木, 09 10 2008) $
 * @author  $Author: nakai $
 */
public class LocationInquiry extends Page
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
	 * ControlID	tab_Inquiry
	 * TemplateKey	W_Inquiry
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Inquiry = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Inquiry" , "W_Inquiry");

	/**
	 * ControlID	btn_ToMenu
	 * TemplateKey	To_Menu
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");

	/**
	 * ControlID	lbl_Area
	 * TemplateKey	W_Area
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Area = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Area" , "W_Area");

	/**
	 * ControlID	pul_Area
	 * TemplateKey	W_Area
	 * ControlType	PullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_Area = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_Area" , "W_Area");

	/**
	 * ControlID	lbl_Bank
	 * TemplateKey	W_Bank
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Bank = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Bank" , "W_Bank");

	/**
	 * ControlID	pul_Bank
	 * TemplateKey	W_Bank
	 * ControlType	LinkedPullDown
	 */
	public jp.co.daifuku.bluedog.ui.control.LinkedPullDown pul_Bank = jp.co.daifuku.bluedog.ui.control.LinkedPullDownFactory.getInstance("pul_Bank" , "W_Bank");

	/**
	 * ControlID	btn_DisplaySync
	 * TemplateKey	W_DisplaySync
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_DisplaySync = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_DisplaySync" , "W_DisplaySync");

}
//end of class

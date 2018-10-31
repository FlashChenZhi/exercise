// $Id: WorkPlace2.java 6014 2009-11-18 01:16:31Z ota $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.workplace;
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
 * @version $Revision: 6014 $, $Date: 2009-11-18 10:16:31 +0900 (水, 18 11 2009) $
 * @author  $Author: ota $
 */
public class WorkPlace2 extends Page
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
	 * ControlID	tab_WorkPlace_Create
	 * TemplateKey	AST_WorkPlace_Create
	 * ControlType	Tab
	 */
	public jp.co.daifuku.bluedog.ui.control.Tab tab_WorkPlace_Create = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_WorkPlace_Create" , "AST_WorkPlace_Create");

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
	 * ControlID	lbl_WareHouseName
	 * TemplateKey	AST_WareHouseName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouseName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouseName" , "AST_WareHouseName");

	/**
	 * ControlID	lbl_In_WareHouseName
	 * TemplateKey	AST_In_Item
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_WareHouseName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_WareHouseName" , "AST_In_Item");

	/**
	 * ControlID	lbl_ParentStnumber
	 * TemplateKey	AST_ParentStnumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ParentStnumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ParentStnumber" , "AST_ParentStnumber");

	/**
	 * ControlID	lbl_In_ParentStationNumber
	 * TemplateKey	AST_In_Item
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_ParentStationNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_ParentStationNumber" , "AST_In_Item");

	/**
	 * ControlID	lbl_ParentstationName
	 * TemplateKey	AST_ParentstationName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ParentstationName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ParentstationName" , "AST_ParentstationName");

	/**
	 * ControlID	lbl_In_ParentStationName
	 * TemplateKey	AST_In_Item
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_ParentStationName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_ParentStationName" , "AST_In_Item");

	/**
	 * ControlID	lbl_WorkPlaceTypeChar
	 * TemplateKey	AST_WorkPlaceTypeChar
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkPlaceTypeChar = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkPlaceTypeChar" , "AST_WorkPlaceTypeChar");

	/**
	 * ControlID	lbl_In_WorkPlaceType
	 * TemplateKey	AST_In_Item
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_WorkPlaceType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_WorkPlaceType" , "AST_In_Item");

	/**
	 * ControlID	lbl_StationNumberWorkPlaceNum
	 * TemplateKey	AST_StationNumberWorkPlaceNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StationNumberWorkPlaceNum = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StationNumberWorkPlaceNum" , "AST_StationNumberWorkPlaceNumber");

	/**
	 * ControlID	lbl_In_StationNoWorkPlaceNo
	 * TemplateKey	AST_In_Item
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_StationNoWorkPlaceNo = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_StationNoWorkPlaceNo" , "AST_In_Item");

	/**
	 * ControlID	btn_Search
	 * TemplateKey	AST_P_Search
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Search = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Search" , "AST_P_Search");

	/**
	 * ControlID	lbl_StationNameWorkPlaceName
	 * TemplateKey	AST_StationNameWorkPlaceName
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StationNameWorkPlaceName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StationNameWorkPlaceName" , "AST_StationNameWorkPlaceName");

	/**
	 * ControlID	lbl_In_StNameWorkPlaceName
	 * TemplateKey	AST_In_Item
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_StNameWorkPlaceName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_StNameWorkPlaceName" , "AST_In_Item");

	/**
	 * ControlID	lbl_StationTypeChar
	 * TemplateKey	AST_StationTypeChar
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StationTypeChar = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StationTypeChar" , "AST_StationTypeChar");

	/**
	 * ControlID	lbl_In_StationType
	 * TemplateKey	AST_In_Item
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_StationType = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_StationType" , "AST_In_Item");

	/**
	 * ControlID	lbl_AisleStationNumber
	 * TemplateKey	AST_AisleStationNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AisleStationNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AisleStationNumber" , "AST_AisleStationNumber");

	/**
	 * ControlID	lbl_In_AisleStationNumber
	 * TemplateKey	AST_In_Item
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_AisleStationNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_AisleStationNumber" , "AST_In_Item");

	/**
	 * ControlID	lbl_AgcNumber
	 * TemplateKey	AST_AgcNumber
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AgcNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AgcNumber" , "AST_AgcNumber");

	/**
	 * ControlID	lbl_In_AGCNumber
	 * TemplateKey	AST_In_Item
	 * ControlType	Label
	 */
	public jp.co.daifuku.bluedog.ui.control.Label lbl_In_AGCNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_In_AGCNumber" , "AST_In_Item");

	/**
	 * ControlID	btn_Add
	 * TemplateKey	AST_Add
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Add = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Add" , "AST_Add");

	/**
	 * ControlID	btn_Commit
	 * TemplateKey	AST_Commit
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "AST_Commit");

	/**
	 * ControlID	btn_Cancel
	 * TemplateKey	AST_Cancel
	 * ControlType	SubmitButton
	 */
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "AST_Cancel");

	/**
	 * ControlID	lst_WorkPlace
	 * TemplateKey	AST_S_WorkPlace
	 * ControlType	ListCell
	 */
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_WorkPlace = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_WorkPlace" , "AST_S_WorkPlace");

}
//end of class

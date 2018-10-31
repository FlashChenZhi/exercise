// $Id: Station.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.station;
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
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class Station extends Page
{

	// Class variables -----------------------------------------------
	public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
	public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
	public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
	public jp.co.daifuku.bluedog.ui.control.Tab tab_Create = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab_Create" , "AST_Create");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouseNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouseNumber" , "AST_WareHouseNumber");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_StoreAs = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_StoreAs" , "AST_StoreAs");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StationNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StationNumber" , "AST_StationNumber");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StNumber = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StNumber" , "AST_StNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StationName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StationName" , "AST_StationName");
	public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_StationName = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_StationName" , "AST_StationName");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Type = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Type" , "AST_Type");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PrivateStorage = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PrivateStorage" , "AST_PrivateStorage");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PrivateRetrieval = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PrivateRetrieval" , "AST_PrivateRetrieval");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_StandShuttleCart = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_StandShuttleCart" , "AST_StandShuttleCart");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ConveyorStorageSide = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ConveyorStorageSide" , "AST_ConveyorStorageSide");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_ConveyorRetrievalSide = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_ConveyorRetrievalSide" , "AST_ConveyorRetrievalSide");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AisleNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AisleNumber" , "AST_AisleNumber");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_AisleNumber = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_AisleNumber" , "AST_AisleNumber");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AisleStationMessage = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AisleStationMessage" , "AST_AisleStationMessage");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_AgcNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AgcNumber" , "AST_AgcNumber");
	public jp.co.daifuku.bluedog.ui.control.PullDown pul_AGCNo = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_AGCNo" , "AST_AGCNo");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_CreateClass = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_CreateClass" , "AST_CreateClass");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_LoadConfirmationSetup = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_LoadConfirmationSetup" , "AST_LoadConfirmationSetup");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_PrecedenceSetup = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_PrecedenceSetup" , "AST_PrecedenceSetup");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_Arrival = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Arrival" , "AST_Arrival");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_NotAssignedsArrive = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_NotAssignedsArrive" , "AST_NotAssignedsArrive");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AssignedArrive = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AssignedArrive" , "AST_AssignedArrive");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_StyleInspection = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_StyleInspection" , "AST_StyleInspection");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_NotAssignedsCarryStyle = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_NotAssignedsCarryStyle" , "AST_NotAssignedsCarryStyle");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AssignedCarryStyle = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AssignedCarryStyle" , "AST_AssignedCarryStyle");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_WorkIndication = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WorkIndication" , "AST_WorkIndication");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_NotAssignedsWork = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_NotAssignedsWork" , "AST_NotAssignedsWork");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_JobsDisplay = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_JobsDisplay" , "AST_JobsDisplay");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_OperationDisplay = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_OperationDisplay" , "AST_OperationDisplay");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ReStoringWork = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ReStoringWork" , "AST_ReStoringWork");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_NotAssignedsReStoring = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_NotAssignedsReStoring" , "AST_NotAssignedsReStoring");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AssignedReStoring = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AssignedReStoring" , "AST_AssignedReStoring");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ReStoringRouteIndication = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ReStoringRouteIndication" , "AST_ReStoringRouteIndication");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_NotAssignedsReStoringCarry = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_NotAssignedsReStoringCarry" , "AST_NotAssignedsReStoringCarry");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AssignedReStoringCarry = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AssignedReStoringCarry" , "AST_AssignedReStoringCarry");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_PayOut = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_PayOut" , "AST_PayOut");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_NotAssignedsPayOut = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_NotAssignedsPayOut" , "AST_NotAssignedsPayOut");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AssignedPayOut = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AssignedPayOut" , "AST_AssignedPayOut");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_ModeChange = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ModeChange" , "AST_ModeChange");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_NotAssignedsMode = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_NotAssignedsMode" , "AST_NotAssignedsMode");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AWCModeChange = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AWCModeChange" , "AST_AWCModeChange");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_EquipmentModeChange = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_EquipmentModeChange" , "AST_EquipmentModeChange");
	public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_AutoModeChange = jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_AutoModeChange" , "AST_AutoModeChange");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxInstruction = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxInstruction" , "AST_MaxInstruction");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_MaxInstrucion = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_MaxInstrucion" , "AST_MaxInstruction");
	public jp.co.daifuku.bluedog.ui.control.Label lbl_MaxPalletAQuantity = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_MaxPalletAQuantity" , "AST_MaxPalletAQuantity");
	public jp.co.daifuku.bluedog.ui.control.NumberTextBox txt_MaxPalletQuantity = jp.co.daifuku.bluedog.ui.control.NumberTextBoxFactory.getInstance("txt_MaxPalletQuantity" , "AST_MaxPalletQuantity");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Add = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Add" , "AST_Add");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "AST_Clear");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "AST_Commit");
	public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "AST_Cancel");
	public jp.co.daifuku.bluedog.ui.control.ListCell lst_Station = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_Station" , "AST_S_Station");

}
//end of class

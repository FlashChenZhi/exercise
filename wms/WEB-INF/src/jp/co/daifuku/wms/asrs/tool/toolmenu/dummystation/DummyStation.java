// $Id: DummyStation.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.dummystation;
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
public class DummyStation extends Page
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
    public jp.co.daifuku.bluedog.ui.control.Label lbl_AgcNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_AgcNumber" , "AST_AgcNumber");
    public jp.co.daifuku.bluedog.ui.control.PullDown pul_AGCNo = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_AGCNo" , "AST_AGCNo");
    public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Add = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Add" , "AST_Add");
    public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "AST_Clear");
    public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "AST_Commit");
    public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "AST_Cancel");
    public jp.co.daifuku.bluedog.ui.control.ListCell lst_DummyStation = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_DummyStation" , "AST_S_DummyStation");

}
//end of class
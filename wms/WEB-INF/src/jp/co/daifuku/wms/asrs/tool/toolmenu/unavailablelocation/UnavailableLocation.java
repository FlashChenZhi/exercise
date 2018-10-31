// $Id: UnavailableLocation.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.unavailablelocation;
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
public class UnavailableLocation extends Page
{

    // Class variables -----------------------------------------------
    public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName" , "In_SettingName");
    public jp.co.daifuku.bluedog.ui.control.LinkButton btn_Help = jp.co.daifuku.bluedog.ui.control.LinkButtonFactory.getInstance("btn_Help" , "Help");
    public jp.co.daifuku.bluedog.ui.control.Message message = jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message" , "OperationMsg");
    public jp.co.daifuku.bluedog.ui.control.Tab tab = jp.co.daifuku.bluedog.ui.control.TabFactory.getInstance("tab" , "AST_Create");
    public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ToMenu = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ToMenu" , "To_Menu");
    public jp.co.daifuku.bluedog.ui.control.Label lbl_WareHouseNumber = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_WareHouseNumber" , "AST_WareHouseNumber");
    public jp.co.daifuku.bluedog.ui.control.PullDown pul_StoreAs = jp.co.daifuku.bluedog.ui.control.PullDownFactory.getInstance("pul_StoreAs" , "AST_StoreAs");
    public jp.co.daifuku.bluedog.ui.control.Label lbl_ImpossibleLocation = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_ImpossibleLocation" , "AST_ImpossibleLocation");
    public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Bank = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Bank" , "AST_Bank");
    public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen1 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen1" , "AST_Hyphen");
    public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Bay = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Bay" , "AST_Bay");
    public jp.co.daifuku.bluedog.ui.control.Label lbl_Hyphen2 = jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_Hyphen2" , "AST_Hyphen");
    public jp.co.daifuku.bluedog.ui.control.FreeTextBox txt_Level = jp.co.daifuku.bluedog.ui.control.FreeTextBoxFactory.getInstance("txt_Level" , "AST_Level");
    public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Add = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Add" , "AST_Add");
    public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Clear = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Clear" , "AST_Clear");
    public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Commit = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Commit" , "AST_Commit");
    public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Cancel = jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Cancel" , "AST_Cancel");
    public jp.co.daifuku.bluedog.ui.control.ListCell lst_UnUsableShelf = jp.co.daifuku.bluedog.ui.control.ListCellFactory.getInstance("lst_UnUsableShelf" , "AST_S_UnUsableShelf");

}
//end of class

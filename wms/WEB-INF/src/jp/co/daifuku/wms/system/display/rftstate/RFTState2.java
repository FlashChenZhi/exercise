// $Id: RFTState2.java,v 1.1.1.1 2009/02/10 08:55:53 arai Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.system.display.rftstate;

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
 * @version $Revision: 1.1.1.1 $, $Date: 2009/02/10 08:55:53 $
 * @author  $Author: arai $
 */
public class RFTState2
        extends Page
{

    // Class variables -----------------------------------------------

    /**
     * ControlID	lbl_SettingName
     * TemplateKey	In_SettingName
     * ControlType	Label
     */
    public jp.co.daifuku.bluedog.ui.control.Label lbl_SettingName =
            jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_SettingName", "In_SettingName");

    /**
     * ControlID	message
     * TemplateKey	OperationMsg
     * ControlType	Message
     */
    public jp.co.daifuku.bluedog.ui.control.Message message =
            jp.co.daifuku.bluedog.ui.control.MessageFactory.getInstance("message", "OperationMsg");

    /**
     * ControlID	lbl_RFTNo
     * TemplateKey	W_RFTNo
     * ControlType	Label
     */
    public jp.co.daifuku.bluedog.ui.control.Label lbl_RFTNo =
            jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RFTNo", "W_RFTNo");

    /**
     * ControlID	lbl_JavaSetRftNo
     * TemplateKey	W_In_JavaSet
     * ControlType	Label
     */
    public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetRftNo =
            jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetRftNo", "W_In_JavaSet");

    /**
     * ControlID	lbl_TerminalFlag
     * TemplateKey	W_TerminalFlag
     * ControlType	Label
     */
    public jp.co.daifuku.bluedog.ui.control.Label lbl_TerminalFlag =
            jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_TerminalFlag", "W_TerminalFlag");

    /**
     * ControlID	lbl_JavaSetTerminalType
     * TemplateKey	W_In_JavaSet
     * ControlType	Label
     */
    public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetTerminalType =
            jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetTerminalType", "W_In_JavaSet");

    /**
     * ControlID	lbl_RFTStatus
     * TemplateKey	W_RFTStatus
     * ControlType	Label
     */
    public jp.co.daifuku.bluedog.ui.control.Label lbl_RFTStatus =
            jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_RFTStatus", "W_RFTStatus");

    /**
     * ControlID	lbl_JavaSetRftStatus
     * TemplateKey	W_In_JavaSet
     * ControlType	Label
     */
    public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetRftStatus =
            jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetRftStatus", "W_In_JavaSet");

    /**
     * ControlID	lbl_UserName
     * TemplateKey	W_UserName
     * ControlType	Label
     */
    public jp.co.daifuku.bluedog.ui.control.Label lbl_UserName =
            jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_UserName", "W_UserName");

    /**
     * ControlID	lbl_JavaSetUserName
     * TemplateKey	W_In_JavaSet
     * ControlType	Label
     */
    public jp.co.daifuku.bluedog.ui.control.Label lbl_JavaSetUserName =
            jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_JavaSetUserName", "W_In_JavaSet");

    /**
     * ControlID	lbl_HalfWork
     * TemplateKey	W_HalfWork
     * ControlType	Label
     */
    public jp.co.daifuku.bluedog.ui.control.Label lbl_HalfWork =
            jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_HalfWork", "W_HalfWork");

    /**
     * ControlID	rdo_HalfWork_Cancel
     * TemplateKey	W_HalfWork_Cancel
     * ControlType	RadioButton
     */
    public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_HalfWork_Cancel =
            jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_HalfWork_Cancel", "W_HalfWork_Cancel");

    /**
     * ControlID	rdo_HalfWork_Commit
     * TemplateKey	W_HalfWork_Commit
     * ControlType	RadioButton
     */
    public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_HalfWork_Commit =
            jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_HalfWork_Commit", "W_HalfWork_Commit");

    /**
     * ControlID	lbl_DeficiencyQty
     * TemplateKey	W_DeficiencyQty
     * ControlType	Label
     */
    public jp.co.daifuku.bluedog.ui.control.Label lbl_DeficiencyQty =
            jp.co.daifuku.bluedog.ui.control.LabelFactory.getInstance("lbl_DeficiencyQty", "W_DeficiencyQty");

    /**
     * ControlID	rdo_Deficiency_PartialDelivery
     * TemplateKey	W_Deficiency_PartialDelivery
     * ControlType	RadioButton
     */
    public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Deficiency_PartialDelivery =
            jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Deficiency_PartialDelivery",
                    "W_Deficiency_PartialDelivery");

    /**
     * ControlID	rdo_Deficiency_Shortage
     * TemplateKey	W_Deficiency_Shortage
     * ControlType	RadioButton
     */
    public jp.co.daifuku.bluedog.ui.control.RadioButton rdo_Deficiency_Shortage =
            jp.co.daifuku.bluedog.ui.control.RadioButtonFactory.getInstance("rdo_Deficiency_Shortage",
                    "W_Deficiency_Shortage");

    /**
     * ControlID	btn_Submit
     * TemplateKey	W_Set2
     * ControlType	SubmitButton
     */
    public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Submit =
            jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Submit", "W_Set2");

    /**
     * ControlID	btn_ReDisplay
     * TemplateKey	W_ReDisplayFunc
     * ControlType	SubmitButton
     */
    public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_ReDisplay =
            jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_ReDisplay", "W_ReDisplayFunc");

    /**
     * ControlID	btn_Close_U
     * TemplateKey	W_Close
     * ControlType	SubmitButton
     */
    public jp.co.daifuku.bluedog.ui.control.SubmitButton btn_Close_U =
            jp.co.daifuku.bluedog.ui.control.SubmitButtonFactory.getInstance("btn_Close_U", "W_Close");

}
//end of class

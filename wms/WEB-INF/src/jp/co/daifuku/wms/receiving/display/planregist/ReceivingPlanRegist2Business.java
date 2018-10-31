// $Id: ReceivingPlanRegist2Business.java 7399 2010-03-05 11:44:30Z okayama $
package jp.co.daifuku.wms.receiving.display.planregist;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.LineNumber;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.SupplierController;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.receiving.schedule.ReceivingOutParameter;
import jp.co.daifuku.wms.receiving.schedule.ReceivingPlanRegistSCH;
import jp.co.daifuku.wms.receiving.schedule.ReceivingPlanRegistSCHParams;

/**
 * 入荷予定データ登録（詳細情報）の画面処理を行います。
 *
 * @version $Revision: 7399 $, $Date: 2010-03-05 20:44:30 +0900 (金, 05 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class ReceivingPlanRegist2Business
        extends ReceivingPlanRegist2
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** 最大行No. */
    private static final int MAX_LINE_NO = 1000;

    /** lst_ReceivingPlanInput(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY =
            new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_ReceivingPlanInput(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL =
            new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_ReceivingPlanInput(LST_LINE) */
    private static final ListCellKey KEY_LST_LINE = new ListCellKey("LST_LINE", new NumberCellColumn(0), true, false);

    /** lst_ReceivingPlanInput(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_ReceivingPlanInput(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_ReceivingPlanInput(LST_CASE_PACK) */
    private static final ListCellKey KEY_LST_CASE_PACK =
            new ListCellKey("LST_CASE_PACK", new NumberCellColumn(0), true, false);

    /** lst_ReceivingPlanInput(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY =
            new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReceivingPlanInput(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY =
            new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReceivingPlanInput(LST_LOT) */
    private static final ListCellKey KEY_LST_LOT = new ListCellKey("LST_LOT", new StringCellColumn(), true, false);

    /** lst_ReceivingPlanInput(LST_UPC_CODE) */
    private static final ListCellKey KEY_LST_UPC_CODE =
            new ListCellKey("LST_UPC_CODE", new StringCellColumn(), true, false);

    /** lst_ReceivingPlanInput(LST_CASE_ITF) */
    private static final ListCellKey KEY_LST_CASE_ITF =
            new ListCellKey("LST_CASE_ITF", new StringCellColumn(), true, false);

    /** lst_ReceivingPlanInput kyes */
    private static final ListCellKey[] LST_RECEIVINGPLANINPUT_KEYS = {
            KEY_LST_MODIFY,
            KEY_LST_CANCEL,
            KEY_LST_LINE,
            KEY_LST_ITEM_CODE,
            KEY_LST_CASE_PACK,
            KEY_LST_PLAN_CASE_QTY,
            KEY_LST_LOT,
            KEY_LST_UPC_CODE,
            KEY_LST_ITEM_NAME,
            KEY_LST_PLAN_PIECE_QTY,
            KEY_LST_CASE_ITF,
    };

    // DFKLOOK: DAC modification from here
    protected static final int HIDDEN_LOCATION_NO = 0;

    // DFKLOOK: DAC modification to here

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    ////////////////////////////////////////////////
    // DFKLOOK: Auto Numbering from here
    ////////////////////////////////////////////////
    private static ArrayList<Integer> ListOfLineNo;

    private static boolean modifying;

    private static int modifyingRow;

    ////////////////////////////////////////////////
    // DFKLOOK: Auto Numbering to here
    ////////////////////////////////////////////////

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_ReceivingPlanInput */
    private ListCellModel _lcm_lst_ReceivingPlanInput;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public ReceivingPlanRegist2Business()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        setTitle();

        // save a popup event source.
        viewState.setString(_KEY_POPUPSOURCE, request.getParameter(_KEY_POPUPSOURCE));

        // initialize pulldown models.
        initializePulldownModels();

        // process call.
        page_Load_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        // initialize componenets.
        initializeComponents();

        // process call.
        page_Initialize_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        // DKFLOOK: DAC added from here
        ListOfLineNo.clear();
        this.viewState.setObject(ViewStateKeys.LIST_LINE_NO, ListOfLineNo);
        // DKFLOOK: DAC added to here

        // get event source.
        DialogParameters dialogParams = ((DialogEvent)e).getDialogParameters();
        String eventSource = dialogParams.getParameter(_KEY_POPUPSOURCE);
        if (StringUtil.isBlank(eventSource))
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_PSearchItem_Click"))
        {
            // process call.
            btn_PSearchItem_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // get event source.
        String eventSource = viewState.getString(_KEY_CONFIRMSOURCE);
        if (eventSource == null)
        {
            return;
        }

        // remove event source.
        viewState.remove(_KEY_CONFIRMSOURCE);

        // check result.
        boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();
        if (!isExecute)
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_Input_Click"))
        {
            // process call.
            btn_Input_Click_Process(false);
        }
        // DFKLOOK ここから修正
        else if (eventSource.startsWith("btn_Set_Click"))
        {
            // process call.
            btn_Set_Click_Process(eventSource);
        }               
        // DFKLOOK ここまで修正
        
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Back_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Back_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PSearchItem_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSearchItem_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Input_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Input_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Clear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Set_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ReceivingPlanInput_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_ReceivingPlanInput.getActiveCol();

        // choose process.
        if (_lcm_lst_ReceivingPlanInput.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_ReceivingPlanInput.getColumnIndex(KEY_LST_CANCEL) == activeCol)
        {
            // process call.
            lst_Cancel_Click_Process();
        }
    }

    /**
     * Menu button click event handling.

     * @param e ActionEvent, event information
     * @throws Exception
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        // Delete Connection From Session
        SessionUtil.deleteSession(getSession());
        // Forward to Submenu screen
        forward(BusinessClassHelper.getSubMenuPath(viewState.getString(Constants.M_MENUID_KEY)));
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     *
     * @throws Exception
     */
    private void initializeComponents()
            throws Exception
    {
        // get locale.
        Locale locale = httpRequest.getLocale();

        // initialize lst_ReceivingPlanInput.
        _lcm_lst_ReceivingPlanInput = new ListCellModel(lst_ReceivingPlanInput, LST_RECEIVINGPLANINPUT_KEYS, locale);
        _lcm_lst_ReceivingPlanInput.setToolTipVisible(KEY_LST_MODIFY, true);
        _lcm_lst_ReceivingPlanInput.setToolTipVisible(KEY_LST_CANCEL, true);
        _lcm_lst_ReceivingPlanInput.setToolTipVisible(KEY_LST_LINE, true);
        _lcm_lst_ReceivingPlanInput.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_ReceivingPlanInput.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_ReceivingPlanInput.setToolTipVisible(KEY_LST_CASE_PACK, true);
        _lcm_lst_ReceivingPlanInput.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_ReceivingPlanInput.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_ReceivingPlanInput.setToolTipVisible(KEY_LST_LOT, true);
        _lcm_lst_ReceivingPlanInput.setToolTipVisible(KEY_LST_UPC_CODE, true);
        _lcm_lst_ReceivingPlanInput.setToolTipVisible(KEY_LST_CASE_ITF, true);

    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_ReceivingPlanInput_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // DFKLOOK ここから
        // set focus.
        if (WmsParam.AUTO_LINE_NO)
        {
            setFocus(txt_ItemCode);
        }
        else
        {
            setFocus(txt_LineNo);
        }
        // DFKLOOK ここまで
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // output display.
        //////////////////////////////////////
        // DFKLOOK: DAC Modification from here
        //////////////////////////////////////

        Connection conn = null;
        ReceivingPlanRegistSCH sch = null;

        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        try
        {
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReceivingPlanRegistSCH(conn, this.getClass(), locale, ui);
            ReceivingOutParameter outparam = (ReceivingOutParameter)sch.initFind(new Parameter());
            this.viewState.setBoolean(ViewStateKeys.MASTER, outparam.isMasterFlag());
            if (this.viewState.getBoolean(ViewStateKeys.MASTER))
            {
                //txt_CustomerName.setReadOnly(true);
                txt_ItemName.setReadOnly(true);
                txt_EnteringQty.setReadOnly(true);
                txt_JanCode.setReadOnly(true);
                txt_CaseITF.setReadOnly(true);

                // Supplier Name
                SupplierController supplierController = new SupplierController(conn, this.getClass());
                String supplierName =
                        supplierController.getName((String)viewState.getObject(ViewStateKeys.SUPPLIER_CODE),
                                WmsParam.DEFAULT_CONSIGNOR_CODE);
                lbl_InSupplierName.setText(supplierName);
                viewState.getObject(lbl_InSupplierName.getText());
            }
            else
            {
                lbl_InSupplierName.setValue(viewState.getObject(ViewStateKeys.SUPPLIER_NAME));
            }
            lbl_InPlanDate.setValue(WmsFormatter.toDispDate(viewState.getDate(ViewStateKeys.RECEIVING_PLAN_DATE),
                    locale));

            lbl_InSlip.setValue(viewState.getObject(ViewStateKeys.SLIP_NUMBER));
            lbl_InSupplierCode.setValue(viewState.getObject(ViewStateKeys.SUPPLIER_CODE));

            viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            //////////////////////////////////////
            // DFKLOOK: DAC Modification from here
            //////////////////////////////////////

            // clear.
            txt_LineNo.setValue(null);
            txt_ItemCode.setValue(null);
            txt_ItemName.setValue(null);
            txt_EnteringQty.setValue(null);
            txt_JanCode.setValue(null);
            txt_CaseITF.setValue(null);
            txt_PlanCaseQty.setValue(null);
            txt_PlanPieceQty.setValue(null);
            txt_LotNo.setValue(null);
            btn_Set.setEnabled(false);
            btn_AllClear.setEnabled(false);
            _lcm_lst_ReceivingPlanInput.clear();

            //////////////////////////////////////////////////
            //  DFKLOOK: Auto Numbering from here
            //////////////////////////////////////////////////
            if (WmsParam.AUTO_LINE_NO)
            {
                txt_LineNo.setReadOnly(true);
            }
            else
            {
                txt_LineNo.setReadOnly(false);
            }
            int lineNo = LineNumber.getNextLineNo(lbl_InSlip.getText(), 1, this, WmsParam.AUTO_LINE_NO);

            // Checks the Max Line No.  
            //If the Line No. exceeds 999, then shows an error message
            if (!isLineNoOver(lineNo))
            {
                txt_LineNo.setInt(lineNo);
                ListOfLineNo = new ArrayList<Integer>(); // For maintenace of Line No if a plan is deleted by cancel button.
                this.viewState.setObject(ViewStateKeys.LIST_LINE_NO, ListOfLineNo);
                modifying = false;
                this.viewState.setBoolean(ViewStateKeys.MODIFY_FLAG, modifying);
                modifyingRow = 0;
                this.viewState.setInt(ViewStateKeys.MODIFY_ROW, modifyingRow);
            }
            ///////////////////////////////////////////////////
            //  DFKLOOK: Auto Numbering to here
            ///////////////////////////////////////////////////
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Back_Click_Process()
            throws Exception
    {
        // DFKLOOK: DAC modification from here
        viewState.setObject(ViewStateKeys.RECEIVING_PLAN_DATE, viewState.getObject(ViewStateKeys.RECEIVING_PLAN_DATE));
        viewState.setObject(ViewStateKeys.SLIP_NUMBER, viewState.getObject(ViewStateKeys.SLIP_NUMBER));
        viewState.setObject(ViewStateKeys.SUPPLIER_CODE, lbl_InSupplierCode.getText());
        viewState.setObject(ViewStateKeys.SUPPLIER_NAME, lbl_InSupplierName.getText());
        // DFKLOOK: DAC Modification to here2

        // forward.
        forward("/receiving/planregist/ReceivingPlanRegist.do");
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PSearchItem_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSearchItem_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSearchItem_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));
        txt_EnteringQty.setValue(outparam.get(LstItemParams.ENTERING_QTY));

        // DFKLOOK: DAC modified from here
        txt_JanCode.setValue(outparam.get(LstItemParams.JAN));
        txt_CaseITF.setValue(outparam.get(LstItemParams.ITF));
        // DFKLOOK: DAC modified to here

        // set focus.
        setFocus(txt_ItemCode);

    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private void btn_Input_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        ////////////////////////////////////////////////
        // DFKLOOK: Auto Numbering from here
        ////////////////////////////////////////////////
        int lineNo = this.txt_LineNo.getInt();
        // Checks the Max Line No.
        if (this.isLineNoOver(lineNo))
        {
            return;
        }
        if (WmsParam.AUTO_LINE_NO)
        {
            txt_LineNo.validate(this, false);
        }
        else
        {
            txt_LineNo.validate(this, true);
        }
        ////////////////////////////////////////////////
        // DFKLOOK: Auto Numbering to here
        ////////////////////////////////////////////////

        txt_ItemCode.validate(this, true);
        txt_ItemName.validate(this, false);
        txt_EnteringQty.validate(this, false);
        txt_JanCode.validate(this, false);
        txt_CaseITF.validate(this, false);
        txt_PlanCaseQty.validate(this, false);
        txt_PlanPieceQty.validate(this, false);
        txt_LotNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ReceivingPlanRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReceivingPlanRegistSCH(conn, this.getClass(), locale, ui);

            ReceivingPlanRegistSCHParams inparam = new ReceivingPlanRegistSCHParams();

            //////////////////////////////////////////////////////////////////////////////////
            // DFKLOOK: Add this portion from here
            //////////////////////////////////////////////////////////////////////////////////

            if (this.getViewState().getBoolean(ViewStateKeys.MASTER))
            {
                //ReceivingPlanRegistSCHParams inparam = new ReceivingPlanRegistSCHParams(ui);
                inparam.set(ReceivingPlanRegistSCHParams.CONSIGNOR_CODE,
                        viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
                inparam.set(ReceivingPlanRegistSCHParams.ITEM_CODE, txt_ItemCode.getValue());

                List<Params> outParams = sch.query(inparam);
                if (outParams.size() == 0)
                {
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }
                for (Params outparam : outParams)
                {
                    if (null != outparam)
                    {
                        // Item Name:
                        txt_ItemName.setText(outparam.getString(ReceivingPlanRegistSCHParams.ITEM_NAME));
                        // Case Pack:
                        int casePack = outparam.getInt(ReceivingPlanRegistSCHParams.CASE_PACK);
                        txt_EnteringQty.setText(String.valueOf(casePack));
                        // UPC Code:
                        txt_JanCode.setText(outparam.getString(ReceivingPlanRegistSCHParams.JAN_CODE));
                        // Case ITF:
                        txt_CaseITF.setText(outparam.getString(ReceivingPlanRegistSCHParams.CASE_ITF));
                    }
                }
            }
            //////////////////////////////////////////////////////////////////////////////////
            // DFKLOOK: Add this portion to here
            //////////////////////////////////////////////////////////////////////////////////

            ///////////////////////////////////////////////
            // DFKLOOK: Auto Numbering from here
            ///////////////////////////////////////////////
            if (WmsParam.AUTO_LINE_NO)
            {
                if (LineNumber.isLineNoUsed(lbl_SlipNumber.getText(), 1, txt_LineNo.getInt(), this))
                {
                    message.setMsgResourceKey("6023020");
                    return;
                }
            }
            ////////////////////////////////////////////////
            // DFKLOOK: Auto Numbering to here
            ////////////////////////////////////////////////

            // set input parameters.
            inparam.set(ReceivingPlanRegistSCHParams.LINE_NO, txt_LineNo.getValue());
            inparam.set(ReceivingPlanRegistSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(ReceivingPlanRegistSCHParams.ITEM_NAME, txt_ItemName.getValue());
            // DFKLOOK: DAC modified from here
            inparam.set(ReceivingPlanRegistSCHParams.CASE_PACK, txt_EnteringQty.getValue());
            // DFKLOOK: DAC modified to here
            inparam.set(ReceivingPlanRegistSCHParams.JAN_CODE, txt_JanCode.getValue());
            inparam.set(ReceivingPlanRegistSCHParams.CASE_ITF, txt_CaseITF.getValue());
            inparam.set(ReceivingPlanRegistSCHParams.PLAN_CASE_QTY, txt_PlanCaseQty.getValue());
            inparam.set(ReceivingPlanRegistSCHParams.PLAN_PIECE_QTY, txt_PlanPieceQty.getValue());
            inparam.set(ReceivingPlanRegistSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(ReceivingPlanRegistSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(ReceivingPlanRegistSCHParams.RECEIVING_PLAN_DATE,
                    viewState.getObject(ViewStateKeys.RECEIVING_PLAN_DATE));
            inparam.set(ReceivingPlanRegistSCHParams.SLIP_NUMBER, viewState.getObject(ViewStateKeys.SLIP_NUMBER));
            inparam.set(ReceivingPlanRegistSCHParams.SUPPLIER_CODE, viewState.getObject(ViewStateKeys.SUPPLIER_CODE));
            inparam.set(ReceivingPlanRegistSCHParams.SUPPLIER_NAME, viewState.getObject(ViewStateKeys.SUPPLIER_NAME));

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ReceivingPlanInput.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_ReceivingPlanInput.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_ReceivingPlanInput.get(i);
                ReceivingPlanRegistSCHParams lineparam = new ReceivingPlanRegistSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(ReceivingPlanRegistSCHParams.LINE, line.getValue(KEY_LST_LINE));
                lineparam.set(ReceivingPlanRegistSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(ReceivingPlanRegistSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(ReceivingPlanRegistSCHParams.CASE_PACK, line.getValue(KEY_LST_CASE_PACK));
                lineparam.set(ReceivingPlanRegistSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(ReceivingPlanRegistSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(ReceivingPlanRegistSCHParams.LOT, line.getValue(KEY_LST_LOT));
                lineparam.set(ReceivingPlanRegistSCHParams.UPC_CODE, line.getValue(KEY_LST_UPC_CODE));
                lineparam.set(ReceivingPlanRegistSCHParams.CASE_ITF, line.getValue(KEY_LST_CASE_ITF));
                lineparam.set(ReceivingPlanRegistSCHParams.CONSIGNOR_CODE,
                        viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
                lineparam.set(ReceivingPlanRegistSCHParams.RECEIVING_PLAN_DATE,
                        viewState.getObject(ViewStateKeys.RECEIVING_PLAN_DATE));
                lineparam.set(ReceivingPlanRegistSCHParams.SLIP_NUMBER, viewState.getObject(ViewStateKeys.SLIP_NUMBER));
                lineparam.set(ReceivingPlanRegistSCHParams.SUPPLIER_CODE,
                        viewState.getObject(ViewStateKeys.SUPPLIER_CODE));
                lineparam.set(ReceivingPlanRegistSCHParams.SUPPLIER_NAME,
                        viewState.getObject(ViewStateKeys.SUPPLIER_NAME));

                //////////////////////////////////////////////////////////////////////////////////
                // DFKLOOK: DAC added this portion from here
                //////////////////////////////////////////////////////////////////////////////////                
                lineparam.set(ReceivingPlanRegistSCHParams.LINE_NO, line.getValue(KEY_LST_LINE));
                lineparam.set(ReceivingPlanRegistSCHParams.RECEIVING_PLAN_DATE, this.lbl_ReceivingPlanDate.getText());
                lineparam.set(ReceivingPlanRegistSCHParams.SLIP_NUMBER, this.lbl_SlipNumber.getText());
                //////////////////////////////////////////////////////////////////////////////////
                // DFKLOOK: DAC added this portion to here
                //////////////////////////////////////////////////////////////////////////////////

                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (confirm && !sch.check(inparam, inparams))
            {
                if (StringUtil.isBlank(sch.getDispMessage()))
                {
                    // show message.
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }
                else
                {
                    // show confirm message.
                    this.setConfirm(sch.getDispMessage(), false, true);
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Input_Click");
                    return;
                }
            }

            message.setMsgResourceKey(sch.getMessage());

            // output display.
            int editRow = _lcm_lst_ReceivingPlanInput.getEditRow();
            Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
            ListCellLine line = newline ? _lcm_lst_ReceivingPlanInput.getNewLine()
                                       : _lcm_lst_ReceivingPlanInput.get(editRow);
            line.setValue(KEY_LST_LINE, txt_LineNo.getValue());

            ////////////////////////////////////////////////////
            // DFKLOOK: Auto Numbering from here
            ////////////////////////////////////////////////////
            //line.setValue(KEY_LST_LINE, txt_LineNo.getValue());
            String strLineNo = "";
            if (txt_LineNo.getInt() == 0)
            {
                ListOfLineNo = (ArrayList<Integer>)this.viewState.getObject(ViewStateKeys.LIST_LINE_NO);
                if (WmsParam.AUTO_LINE_NO)
                {
                    if (ListOfLineNo.size() == 0)
                    {
                        strLineNo = "" + LineNumber.getNextLineNo(lbl_InSlip.getText(), 1, this, WmsParam.AUTO_LINE_NO);
                    }
                    else
                    {
                        strLineNo = "" + (ListOfLineNo.get(ListOfLineNo.size() - 1) + 1);
                    }
                }
                else
                {
                    strLineNo = "" + txt_LineNo.getInt();
                }

                modifying = this.viewState.getBoolean(ViewStateKeys.MODIFY_FLAG);
                if (!modifying)
                {
                    ListOfLineNo.add(Integer.parseInt(strLineNo));
                }
                else
                {
                    if (WmsParam.AUTO_LINE_NO)
                    {
                        ListOfLineNo.add(lst_ReceivingPlanInput.getActiveRow(), Integer.parseInt(strLineNo)); // (target row, Line No)
                    }
                    else
                    {
                        modifyingRow = this.viewState.getInt(ViewStateKeys.MODIFY_ROW);
                        ListOfLineNo.set(modifyingRow - 1, txt_LineNo.getInt()); // (target row, Line No)
                    }
                }
                this.viewState.setObject(ViewStateKeys.LIST_LINE_NO, ListOfLineNo);
            }
            else
            {
                strLineNo = String.valueOf(txt_LineNo.getInt()); // From screen       
            }
            line.setValue(KEY_LST_LINE, Integer.parseInt(strLineNo));
            ////////////////////////////////////////////////////
            // DFKLOOK: Auto Numbering to here
            ////////////////////////////////////////////////////

            line.setValue(KEY_LST_ITEM_CODE, txt_ItemCode.getValue());
            line.setValue(KEY_LST_ITEM_NAME, txt_ItemName.getValue());
            // DFKLOOK:ここから修正
            line.setValue(KEY_LST_CASE_PACK, txt_EnteringQty.getInt());
            line.setValue(KEY_LST_PLAN_CASE_QTY, txt_PlanCaseQty.getInt());
            line.setValue(KEY_LST_PLAN_PIECE_QTY, txt_PlanPieceQty.getInt());
            // DFKLOOK:ここまで修正
            line.setValue(KEY_LST_LOT, txt_LotNo.getValue());
            line.setValue(KEY_LST_UPC_CODE, txt_JanCode.getValue());
            line.setValue(KEY_LST_CASE_ITF, txt_CaseITF.getValue());

            // add new row or update editing row.
            lst_ReceivingPlanInput_SetLineToolTip(line);
            if (newline)
            {
                _lcm_lst_ReceivingPlanInput.add(line, true);
            }
            else
            {
                _lcm_lst_ReceivingPlanInput.set(editRow, line);
            }

            // reset editing row.
            _lcm_lst_ReceivingPlanInput.resetEditRow();
            _lcm_lst_ReceivingPlanInput.resetHighlight();

            // clear.
            btn_Set.setEnabled(true);
            btn_AllClear.setEnabled(true);

            ////////////////////////////////////////////////
            // DFKLOOK: Auto Numbering from here
            ////////////////////////////////////////////////
            // Store the current line number
            ListOfLineNo = (ArrayList<Integer>)this.viewState.getObject(ViewStateKeys.LIST_LINE_NO);
            modifying = this.viewState.getBoolean(ViewStateKeys.MODIFY_FLAG);
            modifyingRow = this.viewState.getInt(ViewStateKeys.MODIFY_ROW);
            if (!modifying)
            {
                if (this.txt_LineNo.getInt() != 0)
                {
                    ListOfLineNo.add(txt_LineNo.getInt()); // Storing the given Line No for deletion                
                }
            }
            else
            {
                if (this.txt_LineNo.getInt() != 0)
                {
                    ListOfLineNo.set(modifyingRow - 1, txt_LineNo.getInt()); // (target row, Line No)
                }
                modifying = false;
                this.viewState.setBoolean(ViewStateKeys.MODIFY_FLAG, modifying);
            }

            // Gets the next line number and shows it on the Line Number Textbox.
            int next =
                    LineNumber.getNextLineNoForReceivingPlan(ListOfLineNo, lbl_InSlip, _lcm_lst_ReceivingPlanInput,
                            txt_LineNo, WmsParam.AUTO_LINE_NO, modifying, false, KEY_LST_LINE, this);
            txt_LineNo.setInt(next);
            this.viewState.setObject(ViewStateKeys.LIST_LINE_NO, ListOfLineNo);
            ////////////////////////////////////////////////
            // DFKLOOK: Auto Numbering to here
            ////////////////////////////////////////////////
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_EnteringQty.setValue(null);
        txt_JanCode.setValue(null);
        txt_CaseITF.setValue(null);
        txt_PlanCaseQty.setValue(null);
        txt_PlanPieceQty.setValue(null);
        txt_LotNo.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        
        // DFKLOOK:ここから修正        
        if (StringUtil.isBlank(eventSource))
        {
            // show confirm message = 登録しますか？
            this.setConfirm("MSG-T0056", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;         
        }
        // DFKLOOK:ここまで修正
        
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ReceivingPlanRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReceivingPlanRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ReceivingPlanInput.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ReceivingPlanInput.get(i);

                // DFKLOOK: DAC commented out from here
                // if (!(line.isAppend() || line.isEdited()))
                // {
                //     continue;
                // }
                // DFKLOOK: DAC commented out to here

                ReceivingPlanRegistSCHParams lineparam = new ReceivingPlanRegistSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);

                lineparam.set(ReceivingPlanRegistSCHParams.LINE_NO, line.getValue(KEY_LST_LINE));
                lineparam.set(ReceivingPlanRegistSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(ReceivingPlanRegistSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(ReceivingPlanRegistSCHParams.CASE_PACK, line.getValue(KEY_LST_CASE_PACK));
                lineparam.set(ReceivingPlanRegistSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(ReceivingPlanRegistSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(ReceivingPlanRegistSCHParams.LOT_NO, line.getValue(KEY_LST_LOT));
                lineparam.set(ReceivingPlanRegistSCHParams.UPC_CODE, line.getValue(KEY_LST_UPC_CODE));
                lineparam.set(ReceivingPlanRegistSCHParams.CASE_ITF, line.getValue(KEY_LST_CASE_ITF));
                lineparam.set(ReceivingPlanRegistSCHParams.CONSIGNOR_CODE,
                        viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
                lineparam.set(ReceivingPlanRegistSCHParams.RECEIVING_PLAN_DATE,
                        viewState.getObject(ViewStateKeys.RECEIVING_PLAN_DATE));
                lineparam.set(ReceivingPlanRegistSCHParams.SLIP_NUMBER, viewState.getObject(ViewStateKeys.SLIP_NUMBER));
                lineparam.set(ReceivingPlanRegistSCHParams.SUPPLIER_CODE,
                        viewState.getObject(ViewStateKeys.SUPPLIER_CODE));
                lineparam.set(ReceivingPlanRegistSCHParams.SUPPLIER_NAME,
                        viewState.getObject(ViewStateKeys.SUPPLIER_NAME));
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highligiting error row.
                _lcm_lst_ReceivingPlanInput.resetEditRow();
                _lcm_lst_ReceivingPlanInput.resetHighlight();
                _lcm_lst_ReceivingPlanInput.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ReceivingPlanInput.size(); i++)
            {
                ListCellLine line = _lcm_lst_ReceivingPlanInput.get(i);
                lst_ReceivingPlanInput.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(lbl_InPlanDate.getStringValue(), "");
                part11List.add(lbl_InSupplierCode.getStringValue(), "");
                part11List.add(lbl_InSlip.getStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_LINE), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_CASE_PACK), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_LOT), "");
                part11List.add(line.getViewString(KEY_LST_UPC_CODE), "");
                part11List.add(line.getViewString(KEY_LST_CASE_ITF), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_REGIST), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ReceivingPlanInput.resetEditRow();
            _lcm_lst_ReceivingPlanInput.resetHighlight();

            // clear.
            _lcm_lst_ReceivingPlanInput.clear();
            btn_Set.setEnabled(false);
            btn_AllClear.setEnabled(false);
            txt_LineNo.setValue(null);

            //////////////////////////////////////////////////////////
            // DFKLOOK: Auto Line Numbering: DAC modified from here
            //////////////////////////////////////////////////////////
            int next = LineNumber.getNextLineNo(lbl_InSlip.getText(), 1, this, WmsParam.AUTO_LINE_NO);
            txt_LineNo.setValue(next);
            ListOfLineNo.clear();
            this.viewState.setObject(ViewStateKeys.LIST_LINE_NO, ListOfLineNo);
            //////////////////////////////////////////////////////////
            // DFKLOOK: Auto Line Numbering: DAC modified to here
            //////////////////////////////////////////////////////////

            txt_ItemCode.setValue(null);
            txt_ItemName.setValue(null);
            txt_EnteringQty.setValue(null);
            txt_JanCode.setValue(null);
            txt_CaseITF.setValue(null);
            txt_PlanCaseQty.setValue(null);
            txt_PlanPieceQty.setValue(null);
            txt_LotNo.setValue(null);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_ReceivingPlanInput.clear();
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);

        // DFKLOOK: Auto Nunmbering from here
        ListOfLineNo.clear();
        this.viewState.setObject(ViewStateKeys.LIST_LINE_NO, ListOfLineNo);
        int next = LineNumber.getNextLineNo(this.lbl_InSlip.getText(), 1, this, WmsParam.AUTO_LINE_NO);
        txt_LineNo.setInt(next);
        this.viewState.setBoolean(ViewStateKeys.MODIFY_FLAG, false);
        this.viewState.setInt(ViewStateKeys.MODIFY_ROW, 0);
        // DFKLOOK: Auto Nunmbering to here
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Modify_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_ReceivingPlanInput.getActiveRow();
        lst_ReceivingPlanInput.setCurrentRow(row);
        ListCellLine line = _lcm_lst_ReceivingPlanInput.get(row);

        // output display.
        txt_LineNo.setValue(line.getValue(KEY_LST_LINE));
        txt_ItemCode.setValue(line.getValue(KEY_LST_ITEM_CODE));
        txt_ItemName.setValue(line.getValue(KEY_LST_ITEM_NAME));
        txt_EnteringQty.setValue(line.getValue(KEY_LST_CASE_PACK));
        txt_PlanCaseQty.setValue(line.getValue(KEY_LST_PLAN_CASE_QTY));
        txt_PlanPieceQty.setValue(line.getValue(KEY_LST_PLAN_PIECE_QTY));
        txt_LotNo.setValue(line.getValue(KEY_LST_LOT));
        txt_JanCode.setValue(line.getValue(KEY_LST_UPC_CODE));
        txt_CaseITF.setValue(line.getValue(KEY_LST_CASE_ITF));

        // highligit active row.
        _lcm_lst_ReceivingPlanInput.resetHighlight();
        _lcm_lst_ReceivingPlanInput.addHighlight(row);
        _lcm_lst_ReceivingPlanInput.setEditRow(row);

        ////////////////////////////////////////////////
        // DFKLOOK: Auto Numbering from here
        ////////////////////////////////////////////////
        modifying = true;
        this.viewState.setBoolean(ViewStateKeys.MODIFY_FLAG, modifying);
        modifyingRow = row;
        this.viewState.setInt(ViewStateKeys.MODIFY_ROW, modifyingRow);
        ////////////////////////////////////////////////
        // DFKLOOK: Auto Numbering to here
        ////////////////////////////////////////////////
    }

    /**
     *
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_ReceivingPlanInput.getActiveRow();
        lst_ReceivingPlanInput.setCurrentRow(row);

        // reset editing row.
        lst_ReceivingPlanInput.removeRow(row);
        _lcm_lst_ReceivingPlanInput.resetEditRow();
        _lcm_lst_ReceivingPlanInput.resetHighlight();

        ///////////////////////////////////////////////////////////////////////////////
        // DFKLOOK: DAC added this portion from here
        ///////////////////////////////////////////////////////////////////////////////
        // Disable these buttons if there is no row in the listcell area after it deletes a row.
        ListOfLineNo = (ArrayList<Integer>)this.viewState.getObject(ViewStateKeys.LIST_LINE_NO);
        if (lst_ReceivingPlanInput.getMaxRows() == 1)
        {
            this.btn_Set.setEnabled(false);
            btn_AllClear.setEnabled(false);
        }

        int next =
                LineNumber.getNextLineNoForReceivingPlan(ListOfLineNo, lbl_InSlip, _lcm_lst_ReceivingPlanInput,
                        txt_LineNo, WmsParam.AUTO_LINE_NO, modifying, true, KEY_LST_LINE, this);
        txt_LineNo.setInt(next);
        modifying = false;
        this.viewState.setObject(ViewStateKeys.LIST_LINE_NO, ListOfLineNo);
        this.viewState.setBoolean(ViewStateKeys.MODIFY_FLAG, modifying);
        ////////////////////////////////////////////////
        // DFKLOOK: Auto Numbering to here
        ////////////////////////////////////////////////
    }

    /**
     * Displays Screen title.
     *
     * @throws Exception
     */
    private void setTitle()
            throws Exception
    {
        // get Menu parameters from httpRequest
        String menuparam = this.getHttpRequest().getParameter(Constants.MENUPARAM);
        if (menuparam != null)
        {
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);

            // update Title information to view state to display when screen is refreshed
            viewState.setString(Constants.M_TITLE_KEY, title);
            viewState.setString(Constants.M_FUNCTIONID_KEY, functionID);
            viewState.setString(Constants.M_MENUID_KEY, menuID);

            lbl_SettingName.setResourceKey(title);
        }
        else if (this.getTitleResourceKey() != null && !this.getTitleResourceKey().equals(""))
        {
            // If there is no title , get the title key from ResourceKey (page.xml)
            // this is used for POPUP tilte keys
            lbl_SettingName.setResourceKey(this.getTitleResourceKey());
        }
        else if (viewState.getString(Constants.M_TITLE_KEY) != null)
        {
            // Set screen title
            lbl_SettingName.setResourceKey(viewState.getString(Constants.M_TITLE_KEY));
        }
    }

    /**
     * Checks if the Line No is over the max or not.
     * If it exceeds the Max, then shows a message.
     */
    private boolean isLineNoOver(int lineNo)
            throws Exception
    {
        if (lineNo >= MAX_LINE_NO)
        {
            this.btn_Input.setEnabled(false);
            this.btn_Clear.setEnabled(false);
            this.btn_PSearchItem.setEnabled(false);
            message.setMsgResourceKey("6023008"); // 6407008 -> 6023008 
            return true;
        }
        return false;
    }

    // DFKLOOK: DAC added to here

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Returns current repository info for this class
     *
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }
}
//end of class

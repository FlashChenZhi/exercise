// $Id: XDPlanRegist2Business.java 7517 2010-03-13 05:02:16Z okayama $
package jp.co.daifuku.wms.crossdock.display.planregist;

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
import jp.co.daifuku.bluedog.webapp.ViewState;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.listbox.cust.LstCustomerBusiness;
import jp.co.daifuku.wms.base.listbox.cust.LstCustomerParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.crossdock.display.planregist.ViewStateKeys;
import jp.co.daifuku.wms.crossdock.display.planregist.XDPlanRegist2;
import jp.co.daifuku.wms.crossdock.schedule.XDPlanRegistSCH;
import jp.co.daifuku.wms.crossdock.schedule.XDPlanRegistSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7517 $, $Date:: 2010-03-13 14:02:16 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class XDPlanRegist2Business
        extends XDPlanRegist2
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_TcPlanDataRegist(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_TcPlanDataRegist(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_TcPlanDataRegist(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_TcPlanDataRegist(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_TcPlanDataRegist(LST_SHIPPING_TICKET) */
    private static final ListCellKey KEY_LST_SHIPPING_TICKET = new ListCellKey("LST_SHIPPING_TICKET", new StringCellColumn(), true, false);

    /** lst_TcPlanDataRegist(LST_SHIPPING_TICKET_LINE) */
    private static final ListCellKey KEY_LST_SHIPPING_TICKET_LINE = new ListCellKey("LST_SHIPPING_TICKET_LINE", new StringCellColumn(), true, false);

    /** lst_TcPlanDataRegist(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE = new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_TcPlanDataRegist(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_TcPlanDataRegist(LST_CONSOLIDATION_AREA) */
    private static final ListCellKey KEY_LST_CONSOLIDATION_AREA = new ListCellKey("LST_CONSOLIDATION_AREA", new StringCellColumn(), true, false);

    /** lst_TcPlanDataRegist keys */
    private static final ListCellKey[] LST_TCPLANDATAREGIST_KEYS = {
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_SHIPPING_TICKET,
        KEY_LST_CUSTOMER_CODE,
        KEY_LST_CONSOLIDATION_AREA,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_SHIPPING_TICKET_LINE,
        KEY_LST_CUSTOMER_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_TcPlanDataRegist */
    private ListCellModel _lcm_lst_TcPlanDataRegist;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public XDPlanRegist2Business()
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
        // initialize components.
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
        // get event source.
        DialogParameters dialogParams = ((DialogEvent)e).getDialogParameters();
        String eventSource = dialogParams.getParameter(_KEY_POPUPSOURCE);
        if (StringUtil.isBlank(eventSource))
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_PSearch_Click"))
        {
            // process call.
            btn_PSearch_Click_DlgBack(dialogParams);
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
    public void btn_PSearch_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSearch_Click_Process();
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
        btn_Set_Click_Process();
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
    public void lst_TcPlanDataRegist_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_TcPlanDataRegist.getActiveCol();

        // choose process.
        if (_lcm_lst_TcPlanDataRegist.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_TcPlanDataRegist.getColumnIndex(KEY_LST_CANCEL) == activeCol)
        {
            // process call.
            lst_Cancel_Click_Process();
        }
    }

    /**
     * メニューへ遷移します。

     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        // セッションからコネクションを削除する
        SessionUtil.deleteSession(getSession());
        // メニューへ遷移します
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

        // initialize lst_TcPlanDataRegist.
        _lcm_lst_TcPlanDataRegist = new ListCellModel(lst_TcPlanDataRegist, LST_TCPLANDATAREGIST_KEYS, locale);
        _lcm_lst_TcPlanDataRegist.setToolTipVisible(KEY_LST_MODIFY, true);
        _lcm_lst_TcPlanDataRegist.setToolTipVisible(KEY_LST_CANCEL, true);
        _lcm_lst_TcPlanDataRegist.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_TcPlanDataRegist.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_TcPlanDataRegist.setToolTipVisible(KEY_LST_SHIPPING_TICKET, true);
        _lcm_lst_TcPlanDataRegist.setToolTipVisible(KEY_LST_SHIPPING_TICKET_LINE, true);
        _lcm_lst_TcPlanDataRegist.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_TcPlanDataRegist.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_TcPlanDataRegist.setToolTipVisible(KEY_LST_CONSOLIDATION_AREA, true);
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
     * @throws Exception
     */
    private void dispose()
            throws Exception
    {
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_TcPlanDataRegist_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_PlanCaseQty);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // output display.
        lbl_InPlanDate.setValue(viewState.getObject(ViewStateKeys.PLAN_DATE));
        lbl_InBatchNo.setValue(viewState.getObject(ViewStateKeys.BATCH_NO));
        lbl_InReceivingSlipNo.setValue(viewState.getObject(ViewStateKeys.RECEIVING_SLIP_NUMBER));
        lbl_InReceivingSlipLineNo.setValue(viewState.getObject(ViewStateKeys.LINE_NO));
        lbl_InSupplierCode.setValue(viewState.getObject(ViewStateKeys.SUPPLIER_CODE));
        lbl_InSupplierName.setValue(viewState.getObject(ViewStateKeys.SUPPLIER_NAME));
        lbl_InItemCode.setValue(viewState.getObject(ViewStateKeys.ITEM_CODE));
        lbl_InItemName.setValue(viewState.getObject(ViewStateKeys.ITEM_NAME));
        lbl_InEnteringQty.setValue(viewState.getObject(ViewStateKeys.CASE_PACK));
        lbl_InJanCode.setValue(viewState.getObject(ViewStateKeys.JAN_CODE));
        lbl_InCaseITF.setValue(viewState.getObject(ViewStateKeys.CASE_ITF));
        lbl_InLotNo.setValue(viewState.getObject(ViewStateKeys.LOT_NO));
        viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        viewState.setObject(ViewStateKeys.MAP_LINE_NO, viewState.getObject(ViewStateKeys.MAP_LINE_NO));
        viewState.setObject(ViewStateKeys.NEXT_LINE_NO, viewState.getObject(ViewStateKeys.NEXT_LINE_NO));

        // clear.
        txt_PlanCaseQty.setValue(null);
        txt_PlanPieceQty.setValue(null);
        txt_ShipSlipNumber.setValue(null);
        txt_ShipSlipLineNo.setValue(null);
        txt_CustomerCode.setValue(null);
        txt_CustomerName.setValue(null);
        txt_SortPlace.setValue(null);
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
        _lcm_lst_TcPlanDataRegist.clear();
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Back_Click_Process()
            throws Exception
    {
        try
        {
            // set ViewState parameters.
            viewState.setObject(ViewStateKeys.SUPPLIER_NAME, lbl_InSupplierName.getValue());
            viewState.setObject(ViewStateKeys.ITEM_NAME, lbl_InItemName.getValue());
            viewState.setObject(ViewStateKeys.CASE_PACK, lbl_InEnteringQty.getValue());
            viewState.setObject(ViewStateKeys.JAN_CODE, lbl_InJanCode.getValue());
            viewState.setObject(ViewStateKeys.CASE_ITF, lbl_InCaseITF.getValue());
            viewState.setObject(ViewStateKeys.BATCH_NO, lbl_InBatchNo.getValue());

            // forward.
            forward("/crossdock/planregist/XDPlanRegist.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PSearch_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstCustomerParams inparam = new LstCustomerParams();
        inparam.set(LstCustomerParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstCustomerParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstCustomerParams.SORT_PLACE_DISP, LstCustomerBusiness.LST_SORT_PLACE_DISP);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSearch_Click");
        redirect("/base/listbox/cust/LstCustomer.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSearch_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstCustomerParams outparam = new LstCustomerParams(dialogParams);
        txt_CustomerCode.setValue(outparam.get(LstCustomerParams.CUSTOMER_CODE));
        txt_CustomerName.setValue(outparam.get(LstCustomerParams.CUSTOMER_NAME));
        txt_SortPlace.setValue(outparam.get(LstCustomerParams.SORT_PLACE));

        // set focus.
        setFocus(txt_CustomerCode);
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Input_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        txt_PlanCaseQty.validate(this, false);
        txt_PlanPieceQty.validate(this, false);
        txt_ShipSlipNumber.validate(this, true);
        txt_ShipSlipLineNo.validate(this, false);
        txt_CustomerCode.validate(this, true);
        txt_CustomerName.validate(this, false);
        txt_SortPlace.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        XDPlanRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new XDPlanRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            XDPlanRegistSCHParams inparam = new XDPlanRegistSCHParams();
            inparam.set(XDPlanRegistSCHParams.PLAN_CASE_QTY, txt_PlanCaseQty.getValue());
            inparam.set(XDPlanRegistSCHParams.PLAN_PIECE_QTY, txt_PlanPieceQty.getValue());
            inparam.set(XDPlanRegistSCHParams.SHIP_SLIP_NUMBER, txt_ShipSlipNumber.getValue());
            inparam.set(XDPlanRegistSCHParams.SHIP_SLIP_LINE_NO, txt_ShipSlipLineNo.getValue());
            inparam.set(XDPlanRegistSCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
            inparam.set(XDPlanRegistSCHParams.CUSTOMER_NAME, txt_CustomerName.getValue());
            inparam.set(XDPlanRegistSCHParams.SORT_PLACE, txt_SortPlace.getValue());
            inparam.set(XDPlanRegistSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.BATCH_NO));
            inparam.set(XDPlanRegistSCHParams.CASE_ITF, viewState.getObject(ViewStateKeys.CASE_ITF));
            inparam.set(XDPlanRegistSCHParams.CASE_PACK, viewState.getObject(ViewStateKeys.CASE_PACK));
            inparam.set(XDPlanRegistSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(XDPlanRegistSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(XDPlanRegistSCHParams.ITEM_NAME, viewState.getObject(ViewStateKeys.ITEM_NAME));
            inparam.set(XDPlanRegistSCHParams.JAN_CODE, viewState.getObject(ViewStateKeys.JAN_CODE));
            inparam.set(XDPlanRegistSCHParams.LINE_NO, viewState.getObject(ViewStateKeys.LINE_NO));
            inparam.set(XDPlanRegistSCHParams.LOT_NO, viewState.getObject(ViewStateKeys.LOT_NO));
            inparam.set(XDPlanRegistSCHParams.PLAN_DATE, viewState.getObject(ViewStateKeys.PLAN_DATE));
            inparam.set(XDPlanRegistSCHParams.RECEIVING_SLIP_NUMBER, viewState.getObject(ViewStateKeys.RECEIVING_SLIP_NUMBER));
            inparam.set(XDPlanRegistSCHParams.SUPPLIER_CODE, viewState.getObject(ViewStateKeys.SUPPLIER_CODE));
            inparam.set(XDPlanRegistSCHParams.SUPPLIER_NAME, viewState.getObject(ViewStateKeys.SUPPLIER_NAME));

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_TcPlanDataRegist.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_TcPlanDataRegist.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_TcPlanDataRegist.get(i);
                XDPlanRegistSCHParams lineparam = new XDPlanRegistSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(XDPlanRegistSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(XDPlanRegistSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(XDPlanRegistSCHParams.SHIPPING_TICKET, line.getValue(KEY_LST_SHIPPING_TICKET));
                lineparam.set(XDPlanRegistSCHParams.SHIPPING_TICKET_LINE, line.getValue(KEY_LST_SHIPPING_TICKET_LINE));
                lineparam.set(XDPlanRegistSCHParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
                lineparam.set(XDPlanRegistSCHParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                lineparam.set(XDPlanRegistSCHParams.CONSOLIDATION_AREA, line.getValue(KEY_LST_CONSOLIDATION_AREA));
                lineparam.set(XDPlanRegistSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.BATCH_NO));
                lineparam.set(XDPlanRegistSCHParams.CASE_ITF, viewState.getObject(ViewStateKeys.CASE_ITF));
                lineparam.set(XDPlanRegistSCHParams.CASE_PACK, viewState.getObject(ViewStateKeys.CASE_PACK));
                lineparam.set(XDPlanRegistSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
                lineparam.set(XDPlanRegistSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
                lineparam.set(XDPlanRegistSCHParams.ITEM_NAME, viewState.getObject(ViewStateKeys.ITEM_NAME));
                lineparam.set(XDPlanRegistSCHParams.JAN_CODE, viewState.getObject(ViewStateKeys.JAN_CODE));
                lineparam.set(XDPlanRegistSCHParams.LINE_NO, viewState.getObject(ViewStateKeys.LINE_NO));
                lineparam.set(XDPlanRegistSCHParams.LOT_NO, viewState.getObject(ViewStateKeys.LOT_NO));
                lineparam.set(XDPlanRegistSCHParams.PLAN_DATE, viewState.getObject(ViewStateKeys.PLAN_DATE));
                lineparam.set(XDPlanRegistSCHParams.RECEIVING_SLIP_NUMBER, viewState.getObject(ViewStateKeys.RECEIVING_SLIP_NUMBER));
                lineparam.set(XDPlanRegistSCHParams.SUPPLIER_CODE, viewState.getObject(ViewStateKeys.SUPPLIER_CODE));
                lineparam.set(XDPlanRegistSCHParams.SUPPLIER_NAME, viewState.getObject(ViewStateKeys.SUPPLIER_NAME));
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
            int editRow = _lcm_lst_TcPlanDataRegist.getEditRow();
            Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
            ListCellLine line = newline ? _lcm_lst_TcPlanDataRegist.getNewLine()
                                        : _lcm_lst_TcPlanDataRegist.get(editRow);
            line.setValue(KEY_LST_PLAN_CASE_QTY, txt_PlanCaseQty.getValue());
            line.setValue(KEY_LST_PLAN_PIECE_QTY, txt_PlanPieceQty.getValue());
            line.setValue(KEY_LST_SHIPPING_TICKET, txt_ShipSlipNumber.getValue());
            line.setValue(KEY_LST_SHIPPING_TICKET_LINE, txt_ShipSlipLineNo.getValue());
            line.setValue(KEY_LST_CUSTOMER_CODE, txt_CustomerCode.getValue());
            line.setValue(KEY_LST_CUSTOMER_NAME, txt_CustomerName.getValue());
            line.setValue(KEY_LST_CONSOLIDATION_AREA, txt_SortPlace.getValue());

            // add new row or update editing row.
            lst_TcPlanDataRegist_SetLineToolTip(line);
            if (newline)
            {
                _lcm_lst_TcPlanDataRegist.add(line, true);
            }
            else
            {
                _lcm_lst_TcPlanDataRegist.set(editRow, line);
            }

            // reset editing row.
            _lcm_lst_TcPlanDataRegist.resetEditRow();
            _lcm_lst_TcPlanDataRegist.resetHighlight();

            // clear.
            btn_Set.setEnabled(true);
            btn_AllClear.setEnabled(true);
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
        txt_PlanCaseQty.setValue(null);
        txt_PlanPieceQty.setValue(null);
        txt_ShipSlipNumber.setValue(null);
        txt_ShipSlipLineNo.setValue(null);
        txt_CustomerCode.setValue(null);
        txt_CustomerName.setValue(null);
        txt_SortPlace.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        XDPlanRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new XDPlanRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_TcPlanDataRegist.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_TcPlanDataRegist.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                XDPlanRegistSCHParams lineparam = new XDPlanRegistSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(XDPlanRegistSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(XDPlanRegistSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(XDPlanRegistSCHParams.SHIPPING_TICKET, line.getValue(KEY_LST_SHIPPING_TICKET));
                lineparam.set(XDPlanRegistSCHParams.SHIPPING_TICKET_LINE, line.getValue(KEY_LST_SHIPPING_TICKET_LINE));
                lineparam.set(XDPlanRegistSCHParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
                lineparam.set(XDPlanRegistSCHParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                lineparam.set(XDPlanRegistSCHParams.CONSOLIDATION_AREA, line.getValue(KEY_LST_CONSOLIDATION_AREA));
                lineparam.set(XDPlanRegistSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.BATCH_NO));
                lineparam.set(XDPlanRegistSCHParams.CASE_ITF, viewState.getObject(ViewStateKeys.CASE_ITF));
                lineparam.set(XDPlanRegistSCHParams.CASE_PACK, viewState.getObject(ViewStateKeys.CASE_PACK));
                lineparam.set(XDPlanRegistSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
                lineparam.set(XDPlanRegistSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
                lineparam.set(XDPlanRegistSCHParams.ITEM_NAME, viewState.getObject(ViewStateKeys.ITEM_NAME));
                lineparam.set(XDPlanRegistSCHParams.JAN_CODE, viewState.getObject(ViewStateKeys.JAN_CODE));
                lineparam.set(XDPlanRegistSCHParams.LINE_NO, viewState.getObject(ViewStateKeys.LINE_NO));
                lineparam.set(XDPlanRegistSCHParams.LOT_NO, viewState.getObject(ViewStateKeys.LOT_NO));
                lineparam.set(XDPlanRegistSCHParams.PLAN_DATE, viewState.getObject(ViewStateKeys.PLAN_DATE));
                lineparam.set(XDPlanRegistSCHParams.RECEIVING_SLIP_NUMBER, viewState.getObject(ViewStateKeys.RECEIVING_SLIP_NUMBER));
                lineparam.set(XDPlanRegistSCHParams.SUPPLIER_CODE, viewState.getObject(ViewStateKeys.SUPPLIER_CODE));
                lineparam.set(XDPlanRegistSCHParams.SUPPLIER_NAME, viewState.getObject(ViewStateKeys.SUPPLIER_NAME));
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

                // reset editing row or highlighting error row.
                _lcm_lst_TcPlanDataRegist.resetEditRow();
                _lcm_lst_TcPlanDataRegist.resetHighlight();
                _lcm_lst_TcPlanDataRegist.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_TcPlanDataRegist.size(); i++)
            {
                ListCellLine line = _lcm_lst_TcPlanDataRegist.get(i);
                lst_TcPlanDataRegist.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(lbl_InPlanDate.getStringValue(), "");
                part11List.add(lbl_InBatchNo.getStringValue(), "");
                part11List.add(lbl_InReceivingSlipNo.getStringValue(), "");
                part11List.add(lbl_InReceivingSlipLineNo.getStringValue(), "");
                part11List.add(lbl_InSupplierCode.getStringValue(), "");
                part11List.add(lbl_InItemCode.getStringValue(), "");
                part11List.add(lbl_InEnteringQty.getStringValue(), "0");
                part11List.add(lbl_InJanCode.getStringValue(), "");
                part11List.add(lbl_InCaseITF.getStringValue(), "");
                part11List.add(lbl_InLotNo.getStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_SHIPPING_TICKET), "");
                part11List.add(line.getViewString(KEY_LST_SHIPPING_TICKET_LINE), "");
                part11List.add(line.getViewString(KEY_LST_CUSTOMER_CODE), "");
                part11List.add(line.getViewString(KEY_LST_CONSOLIDATION_AREA), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_REGIST), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_TcPlanDataRegist.resetEditRow();
            _lcm_lst_TcPlanDataRegist.resetHighlight();

            // clear.
            txt_PlanCaseQty.setValue(null);
            txt_PlanPieceQty.setValue(null);
            txt_ShipSlipNumber.setValue(null);
            txt_ShipSlipLineNo.setValue(null);
            txt_CustomerCode.setValue(null);
            txt_CustomerName.setValue(null);
            txt_SortPlace.setValue(null);
            btn_Set.setEnabled(false);
            btn_AllClear.setEnabled(false);
            _lcm_lst_TcPlanDataRegist.clear();
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
        _lcm_lst_TcPlanDataRegist.clear();
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Modify_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_TcPlanDataRegist.getActiveRow();
        lst_TcPlanDataRegist.setCurrentRow(row);
        ListCellLine line = _lcm_lst_TcPlanDataRegist.get(row);

        // output display.
        txt_PlanCaseQty.setValue(line.getValue(KEY_LST_PLAN_CASE_QTY));
        txt_PlanPieceQty.setValue(line.getValue(KEY_LST_PLAN_PIECE_QTY));
        txt_ShipSlipNumber.setValue(line.getValue(KEY_LST_SHIPPING_TICKET));
        txt_ShipSlipLineNo.setValue(line.getValue(KEY_LST_SHIPPING_TICKET_LINE));
        txt_CustomerCode.setValue(line.getValue(KEY_LST_CUSTOMER_CODE));
        txt_CustomerName.setValue(line.getValue(KEY_LST_CUSTOMER_NAME));
        txt_SortPlace.setValue(line.getValue(KEY_LST_CONSOLIDATION_AREA));

        // highlight active row.
        _lcm_lst_TcPlanDataRegist.resetHighlight();
        _lcm_lst_TcPlanDataRegist.addHighlight(row);
        _lcm_lst_TcPlanDataRegist.setEditRow(row);
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_TcPlanDataRegist.getActiveRow();
        lst_TcPlanDataRegist.setCurrentRow(row);

        // reset editing row.
        lst_TcPlanDataRegist.removeRow(row);
        _lcm_lst_TcPlanDataRegist.resetEditRow();
        _lcm_lst_TcPlanDataRegist.resetHighlight();
    }

    /**
     * 画面タイトルを設定します。
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void setTitle()
            throws Exception
    {
        // httpRequestからメニュー用パラメータを取得する
        String menuparam = this.getHttpRequest().getParameter(Constants.MENUPARAM);
        if (menuparam != null)
        {
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);

            // ViewStateへ保存する
            viewState.setString(Constants.M_TITLE_KEY, title);
            viewState.setString(Constants.M_FUNCTIONID_KEY, functionID);
            viewState.setString(Constants.M_MENUID_KEY, menuID);

            lbl_SettingName.setResourceKey(title);
        }
        else if (this.getTitleResourceKey() != null && !this.getTitleResourceKey().equals(""))
        {
            // リストボックスの場合はpage.xmlから取得する
            lbl_SettingName.setResourceKey(this.getTitleResourceKey());
        }
        else if (viewState.getString(Constants.M_TITLE_KEY) != null)
        {
            // 2画面遷移から戻ってきた場合はViewStateから取得する
            lbl_SettingName.setResourceKey(viewState.getString(Constants.M_TITLE_KEY));
        }
    }

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

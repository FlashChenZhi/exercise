// $Id: RetrievalPlanRegist2Business.java 7446 2010-03-08 04:20:52Z okayama $
package jp.co.daifuku.wms.retrieval.display.planregist;

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
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
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
import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.listbox.cust.LstCustomerBusiness;
import jp.co.daifuku.wms.base.listbox.cust.LstCustomerParams;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.retrieval.display.planregist.RetrievalPlanRegist2;
import jp.co.daifuku.wms.retrieval.display.planregist.ViewStateKeys;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalPlanRegistSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalPlanRegistSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7446 $, $Date:: 2010-03-08 13:20:52 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RetrievalPlanRegist2Business
        extends RetrievalPlanRegist2
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_RetrievalPlanInput(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanInput(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanInput(LST_LINE_NO) */
    private static final ListCellKey KEY_LST_LINE_NO = new ListCellKey("LST_LINE_NO", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanInput(LST_BRANCH_NO) */
    private static final ListCellKey KEY_LST_BRANCH_NO = new ListCellKey("LST_BRANCH_NO", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanInput(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanInput(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO = new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanInput(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE = new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanInput(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanInput(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanInput(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanInput(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanInput(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanInput(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanInput(LST_PLAN_LOT_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOT_NO = new ListCellKey("LST_PLAN_LOT_NO", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanInput(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanInput(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanInput(LST_PLAN_AREA_NO) */
    private static final ListCellKey KEY_LST_PLAN_AREA_NO = new ListCellKey("LST_PLAN_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_RetrievalPlanInput(LST_PLAN_LOCATION_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOCATION_NO = new ListCellKey("LST_PLAN_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_RetrievalPlanInput keys */
    private static final ListCellKey[] LST_RETRIEVALPLANINPUT_KEYS = {
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_LINE_NO,
        KEY_LST_BATCH_NO,
        KEY_LST_ORDER_NO,
        KEY_LST_CUSTOMER_CODE,
        KEY_LST_ITEM_CODE,
        KEY_LST_ENTERING_QTY,
        KEY_LST_JAN,
        KEY_LST_PLAN_LOT_NO,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_PLAN_AREA_NO,
        KEY_LST_BRANCH_NO,
        KEY_LST_CUSTOMER_NAME,
        KEY_LST_ITEM_NAME,
        KEY_LST_ITF,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_PLAN_LOCATION_NO,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_RetrievalArea */
    private WmsAreaPullDownModel _pdm_pul_RetrievalArea;

    /** ListCellModel lst_RetrievalPlanInput */
    private ListCellModel _lcm_lst_RetrievalPlanInput;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RetrievalPlanRegist2Business()
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
        if (eventSource.equals("btn_SearchCustomer_Click"))
        {
            // process call.
            btn_SearchCustomer_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_SearchItem_Click"))
        {
            // process call.
            btn_SearchItem_Click_DlgBack(dialogParams);
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
    public void btn_SearchCustomer_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchCustomer_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchItem_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchItem_Click_Process();
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
    public void btn_Submit_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Submit_Click_Process();
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
    public void lst_RetrievalPlanInput_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_RetrievalPlanInput.getActiveCol();

        // choose process.
        if (_lcm_lst_RetrievalPlanInput.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_RetrievalPlanInput.getColumnIndex(KEY_LST_CANCEL) == activeCol)
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
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_RetrievalArea.
        _pdm_pul_RetrievalArea = new WmsAreaPullDownModel(pul_RetrievalArea, locale, ui);

        // initialize lst_RetrievalPlanInput.
        _lcm_lst_RetrievalPlanInput = new ListCellModel(lst_RetrievalPlanInput, LST_RETRIEVALPLANINPUT_KEYS, locale);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_MODIFY, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_CANCEL, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_LINE_NO, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_BRANCH_NO, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_BATCH_NO, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_ORDER_NO, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_JAN, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_ITF, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_PLAN_LOT_NO, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_PLAN_AREA_NO, true);
        _lcm_lst_RetrievalPlanInput.setToolTipVisible(KEY_LST_PLAN_LOCATION_NO, true);

    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // load pul_RetrievalArea.
            _pdm_pul_RetrievalArea.init(conn, AreaType.FLOOR_AND_TEMP_AND_RECEIVE, StationType.ALL, "", false);

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
    private void dispose()
            throws Exception
    {
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_RetrievalPlanInput_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W0035", KEY_LST_PLAN_LOT_NO);
        line.addToolTip("LBL-W0183", KEY_LST_PLAN_CASE_QTY);
        line.addToolTip("LBL-W0184", KEY_LST_PLAN_PIECE_QTY);
        line.addToolTip("LBL-W0116", KEY_LST_PLAN_AREA_NO);
        line.addToolTip("LBL-W0124", KEY_LST_PLAN_LOCATION_NO);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_LineNo);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // output display.
        txt_R_DateFlat.setValue(viewState.getObject(ViewStateKeys.PLAN_DAY));
        lbl_In_TicketNo.setValue(viewState.getObject(ViewStateKeys.SHIP_TICKET_NO));
        viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        viewState.setObject(ViewStateKeys.LIST_LINE_NO, viewState.getObject(ViewStateKeys.LIST_LINE_NO));
        viewState.setObject(ViewStateKeys.MODIFY_FLAG, viewState.getObject(ViewStateKeys.MODIFY_FLAG));
        viewState.setObject(ViewStateKeys.MODIFY_ROW, viewState.getObject(ViewStateKeys.MODIFY_ROW));

        // clear.
        txt_LineNo.setValue(null);
        txt_BatchNo.setValue(null);
        txt_CustomerCode.setValue(null);
        txt_ItemCode.setValue(null);
        txt_EnteringQty.setValue(null);
        txt_LotNo.setValue(null);
        _pdm_pul_RetrievalArea.setSelectedValue(null);
        txt_BranchNo.setValue(null);
        txt_OrderNo.setValue(null);
        txt_CustomerName.setValue(null);
        txt_ItemName.setValue(null);
        txt_JanCode.setValue(null);
        txt_PlanCaseQty.setValue(null);
        txt_CaseITF.setValue(null);
        txt_PlanPieceQty.setValue(null);
        txt_RetrievalPlanLocation.setValue(null);
        btn_Submit.setEnabled(false);
        btn_AllClear.setEnabled(false);
        _lcm_lst_RetrievalPlanInput.clear();

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
            viewState.setObject(ViewStateKeys.PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
            viewState.setObject(ViewStateKeys.SHIP_TICKET_NO, lbl_In_TicketNo.getValue());

            // forward.
            forward("/retrieval/planregist/RetrievalPlanRegist.do");
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
    private void btn_SearchCustomer_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstCustomerParams inparam = new LstCustomerParams();
        inparam.set(LstCustomerParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstCustomerParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstCustomerParams.SORT_PLACE_DISP, LstCustomerBusiness.LST_SORT_PLACE_HIDDEN);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchCustomer_Click");
        redirect("/base/listbox/cust/LstCustomer.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchCustomer_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstCustomerParams outparam = new LstCustomerParams(dialogParams);
        txt_CustomerCode.setValue(outparam.get(LstCustomerParams.CUSTOMER_CODE));
        txt_CustomerName.setValue(outparam.get(LstCustomerParams.CUSTOMER_NAME));

        // set focus.
        setFocus(txt_CustomerCode);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchItem_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchItem_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchItem_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));
        txt_EnteringQty.setValue(outparam.get(LstItemParams.ENTERING_QTY));
        txt_JanCode.setValue(outparam.get(LstItemParams.JAN));
        txt_CaseITF.setValue(outparam.get(LstItemParams.ITF));

        // set focus.
        setFocus(txt_ItemCode);

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
        txt_LineNo.validate(this, true);
        txt_BranchNo.validate(this, false);
        txt_BatchNo.validate(this, true);
        txt_OrderNo.validate(this, true);
        txt_CustomerCode.validate(this, false);
        txt_CustomerName.validate(this, false);
        txt_ItemCode.validate(this, true);
        txt_ItemName.validate(this, false);
        txt_EnteringQty.validate(this, false);
        txt_JanCode.validate(this, false);
        txt_CaseITF.validate(this, false);
        txt_LotNo.validate(this, false);
        txt_PlanCaseQty.validate(this, false);
        txt_PlanPieceQty.validate(this, false);
        txt_RetrievalPlanLocation.validate(this, false);
        pul_RetrievalArea.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalPlanRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalPlanRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RetrievalPlanRegistSCHParams inparam = new RetrievalPlanRegistSCHParams();
            inparam.set(RetrievalPlanRegistSCHParams.LINE_NO, txt_LineNo.getValue());
            inparam.set(RetrievalPlanRegistSCHParams.BRANCH_NO, txt_BranchNo.getValue());
            inparam.set(RetrievalPlanRegistSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalPlanRegistSCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(RetrievalPlanRegistSCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
            inparam.set(RetrievalPlanRegistSCHParams.CUSTOMER_NAME, txt_CustomerName.getValue());
            inparam.set(RetrievalPlanRegistSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(RetrievalPlanRegistSCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(RetrievalPlanRegistSCHParams.ENTERING_QTY, txt_EnteringQty.getValue());
            inparam.set(RetrievalPlanRegistSCHParams.JAN_CODE, txt_JanCode.getValue());
            inparam.set(RetrievalPlanRegistSCHParams.CASE_ITF, txt_CaseITF.getValue());
            inparam.set(RetrievalPlanRegistSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(RetrievalPlanRegistSCHParams.PLAN_CASE_QTY, txt_PlanCaseQty.getValue());
            inparam.set(RetrievalPlanRegistSCHParams.PLAN_PIECE_QTY, txt_PlanPieceQty.getValue());
            inparam.set(RetrievalPlanRegistSCHParams.RETRIEVAL_AREA, _pdm_pul_RetrievalArea.getSelectedValue());
            inparam.set(RetrievalPlanRegistSCHParams.RETRIEVAL_PLAN_LOCATION, txt_RetrievalPlanLocation.getValue());
            inparam.set(RetrievalPlanRegistSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(RetrievalPlanRegistSCHParams.PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
            inparam.set(RetrievalPlanRegistSCHParams.SHIP_TICKET_NO, viewState.getObject(ViewStateKeys.SHIP_TICKET_NO));

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_RetrievalPlanInput.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_RetrievalPlanInput.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_RetrievalPlanInput.get(i);
                RetrievalPlanRegistSCHParams lineparam = new RetrievalPlanRegistSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(RetrievalPlanRegistSCHParams.LINE_NO, line.getValue(KEY_LST_LINE_NO));
                lineparam.set(RetrievalPlanRegistSCHParams.BRANCH_NO, line.getValue(KEY_LST_BRANCH_NO));
                lineparam.set(RetrievalPlanRegistSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                lineparam.set(RetrievalPlanRegistSCHParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                lineparam.set(RetrievalPlanRegistSCHParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
                lineparam.set(RetrievalPlanRegistSCHParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                lineparam.set(RetrievalPlanRegistSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(RetrievalPlanRegistSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(RetrievalPlanRegistSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(RetrievalPlanRegistSCHParams.JAN, line.getValue(KEY_LST_JAN));
                lineparam.set(RetrievalPlanRegistSCHParams.ITF, line.getValue(KEY_LST_ITF));
                lineparam.set(RetrievalPlanRegistSCHParams.PLAN_LOT_NO, line.getValue(KEY_LST_PLAN_LOT_NO));
                lineparam.set(RetrievalPlanRegistSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(RetrievalPlanRegistSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(RetrievalPlanRegistSCHParams.PLAN_AREA_NO, line.getValue(KEY_LST_PLAN_AREA_NO));
                lineparam.set(RetrievalPlanRegistSCHParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_PLAN_LOCATION_NO));
                lineparam.set(RetrievalPlanRegistSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
                lineparam.set(RetrievalPlanRegistSCHParams.PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
                lineparam.set(RetrievalPlanRegistSCHParams.SHIP_TICKET_NO, viewState.getObject(ViewStateKeys.SHIP_TICKET_NO));
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
            int editRow = _lcm_lst_RetrievalPlanInput.getEditRow();
            Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
            ListCellLine line = newline ? _lcm_lst_RetrievalPlanInput.getNewLine()
                                        : _lcm_lst_RetrievalPlanInput.get(editRow);
            line.setValue(KEY_LST_LINE_NO, txt_LineNo.getValue());
            line.setValue(KEY_LST_BRANCH_NO, txt_BranchNo.getValue());
            line.setValue(KEY_LST_BATCH_NO, txt_BatchNo.getValue());
            line.setValue(KEY_LST_ORDER_NO, txt_OrderNo.getValue());
            line.setValue(KEY_LST_CUSTOMER_CODE, txt_CustomerCode.getValue());
            line.setValue(KEY_LST_CUSTOMER_NAME, txt_CustomerName.getValue());
            line.setValue(KEY_LST_ITEM_CODE, txt_ItemCode.getValue());
            line.setValue(KEY_LST_ITEM_NAME, txt_ItemName.getValue());
            line.setValue(KEY_LST_ENTERING_QTY, txt_EnteringQty.getValue());
            line.setValue(KEY_LST_JAN, txt_JanCode.getValue());
            line.setValue(KEY_LST_ITF, txt_CaseITF.getValue());
            line.setValue(KEY_LST_PLAN_LOT_NO, txt_LotNo.getValue());
            line.setValue(KEY_LST_PLAN_CASE_QTY, txt_PlanCaseQty.getValue());
            line.setValue(KEY_LST_PLAN_PIECE_QTY, txt_PlanPieceQty.getValue());
            line.setValue(KEY_LST_PLAN_AREA_NO, _pdm_pul_RetrievalArea.getSelectedValue());
            line.setValue(KEY_LST_PLAN_LOCATION_NO, txt_RetrievalPlanLocation.getValue());

            // add new row or update editing row.
            lst_RetrievalPlanInput_SetLineToolTip(line);
            if (newline)
            {
                _lcm_lst_RetrievalPlanInput.add(line, true);
            }
            else
            {
                _lcm_lst_RetrievalPlanInput.set(editRow, line);
            }

            // reset editing row.
            _lcm_lst_RetrievalPlanInput.resetEditRow();
            _lcm_lst_RetrievalPlanInput.resetHighlight();

            // clear.
            btn_Submit.setEnabled(true);
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
        txt_LineNo.setValue(null);
        txt_BranchNo.setValue(null);
        txt_BatchNo.setValue(null);
        txt_OrderNo.setValue(null);
        txt_CustomerCode.setValue(null);
        txt_CustomerName.setValue(null);
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_EnteringQty.setValue(null);
        txt_JanCode.setValue(null);
        txt_CaseITF.setValue(null);
        txt_LotNo.setValue(null);
        txt_PlanCaseQty.setValue(null);
        txt_PlanPieceQty.setValue(null);
        _pdm_pul_RetrievalArea.setSelectedValue(null);
        txt_RetrievalPlanLocation.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Submit_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalPlanRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalPlanRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_RetrievalPlanInput.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_RetrievalPlanInput.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                RetrievalPlanRegistSCHParams lineparam = new RetrievalPlanRegistSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(RetrievalPlanRegistSCHParams.LINE_NO, line.getValue(KEY_LST_LINE_NO));
                lineparam.set(RetrievalPlanRegistSCHParams.BRANCH_NO, line.getValue(KEY_LST_BRANCH_NO));
                lineparam.set(RetrievalPlanRegistSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                lineparam.set(RetrievalPlanRegistSCHParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                lineparam.set(RetrievalPlanRegistSCHParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
                lineparam.set(RetrievalPlanRegistSCHParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                lineparam.set(RetrievalPlanRegistSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(RetrievalPlanRegistSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(RetrievalPlanRegistSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(RetrievalPlanRegistSCHParams.JAN, line.getValue(KEY_LST_JAN));
                lineparam.set(RetrievalPlanRegistSCHParams.ITF, line.getValue(KEY_LST_ITF));
                lineparam.set(RetrievalPlanRegistSCHParams.PLAN_LOT_NO, line.getValue(KEY_LST_PLAN_LOT_NO));
                lineparam.set(RetrievalPlanRegistSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(RetrievalPlanRegistSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(RetrievalPlanRegistSCHParams.PLAN_AREA_NO, line.getValue(KEY_LST_PLAN_AREA_NO));
                lineparam.set(RetrievalPlanRegistSCHParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_PLAN_LOCATION_NO));
                lineparam.set(RetrievalPlanRegistSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
                lineparam.set(RetrievalPlanRegistSCHParams.PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
                lineparam.set(RetrievalPlanRegistSCHParams.SHIP_TICKET_NO, viewState.getObject(ViewStateKeys.SHIP_TICKET_NO));
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
                _lcm_lst_RetrievalPlanInput.resetEditRow();
                _lcm_lst_RetrievalPlanInput.resetHighlight();
                _lcm_lst_RetrievalPlanInput.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_RetrievalPlanInput.size(); i++)
            {
                ListCellLine line = _lcm_lst_RetrievalPlanInput.get(i);
                lst_RetrievalPlanInput.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(txt_R_DateFlat.getStringValue(), "");
                part11List.add(lbl_In_TicketNo.getStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_LINE_NO), "");
                part11List.add(line.getViewString(KEY_LST_BRANCH_NO), "");
                part11List.add(line.getViewString(KEY_LST_BATCH_NO), "");
                part11List.add(line.getViewString(KEY_LST_ORDER_NO), "");
                part11List.add(line.getViewString(KEY_LST_CUSTOMER_CODE), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "");
                part11List.add(line.getViewString(KEY_LST_JAN), "");
                part11List.add(line.getViewString(KEY_LST_ITF), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_LOCATION_NO), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_REGIST), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_RetrievalPlanInput.resetEditRow();
            _lcm_lst_RetrievalPlanInput.resetHighlight();

            // clear.
            txt_LineNo.setValue(null);
            txt_BranchNo.setValue(null);
            txt_BatchNo.setValue(null);
            txt_OrderNo.setValue(null);
            txt_CustomerCode.setValue(null);
            txt_CustomerName.setValue(null);
            txt_ItemCode.setValue(null);
            txt_ItemName.setValue(null);
            txt_EnteringQty.setValue(null);
            txt_JanCode.setValue(null);
            txt_CaseITF.setValue(null);
            txt_LotNo.setValue(null);
            txt_PlanCaseQty.setValue(null);
            txt_PlanPieceQty.setValue(null);
            _pdm_pul_RetrievalArea.setSelectedValue(null);
            txt_RetrievalPlanLocation.setValue(null);
            btn_Submit.setEnabled(false);
            btn_AllClear.setEnabled(false);
            _lcm_lst_RetrievalPlanInput.clear();

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
        _lcm_lst_RetrievalPlanInput.clear();
        btn_Submit.setEnabled(false);
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
        int row = lst_RetrievalPlanInput.getActiveRow();
        lst_RetrievalPlanInput.setCurrentRow(row);
        ListCellLine line = _lcm_lst_RetrievalPlanInput.get(row);

        // output display.
        txt_LineNo.setValue(line.getValue(KEY_LST_LINE_NO));
        txt_BranchNo.setValue(line.getValue(KEY_LST_BRANCH_NO));
        txt_BatchNo.setValue(line.getValue(KEY_LST_BATCH_NO));
        txt_OrderNo.setValue(line.getValue(KEY_LST_ORDER_NO));
        txt_CustomerCode.setValue(line.getValue(KEY_LST_CUSTOMER_CODE));
        txt_CustomerName.setValue(line.getValue(KEY_LST_CUSTOMER_NAME));
        txt_ItemCode.setValue(line.getValue(KEY_LST_ITEM_CODE));
        txt_ItemName.setValue(line.getValue(KEY_LST_ITEM_NAME));
        txt_EnteringQty.setValue(line.getValue(KEY_LST_ENTERING_QTY));
        txt_JanCode.setValue(line.getValue(KEY_LST_JAN));
        txt_CaseITF.setValue(line.getValue(KEY_LST_ITF));
        txt_LotNo.setValue(line.getValue(KEY_LST_PLAN_LOT_NO));
        txt_PlanCaseQty.setValue(line.getValue(KEY_LST_PLAN_CASE_QTY));
        txt_PlanPieceQty.setValue(line.getValue(KEY_LST_PLAN_PIECE_QTY));
        _pdm_pul_RetrievalArea.setSelectedValue(line.getValue(KEY_LST_PLAN_AREA_NO));
        txt_RetrievalPlanLocation.setValue(line.getValue(KEY_LST_PLAN_LOCATION_NO));

        // highlight active row.
        _lcm_lst_RetrievalPlanInput.resetHighlight();
        _lcm_lst_RetrievalPlanInput.addHighlight(row);
        _lcm_lst_RetrievalPlanInput.setEditRow(row);

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_RetrievalPlanInput.getActiveRow();
        lst_RetrievalPlanInput.setCurrentRow(row);

        // reset editing row.
        lst_RetrievalPlanInput.removeRow(row);
        _lcm_lst_RetrievalPlanInput.resetEditRow();
        _lcm_lst_RetrievalPlanInput.resetHighlight();

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

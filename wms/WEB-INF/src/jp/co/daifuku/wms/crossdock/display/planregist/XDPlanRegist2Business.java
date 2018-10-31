// $Id: XDPlanRegist2Business.java 7515 2010-03-13 04:40:51Z okayama $
package jp.co.daifuku.wms.crossdock.display.planregist;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
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
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.LineNumber;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.SupplierController;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.listbox.cust.LstCustomerBusiness;
import jp.co.daifuku.wms.base.listbox.cust.LstCustomerParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.crossdock.schedule.XDOutParameter;
import jp.co.daifuku.wms.crossdock.schedule.XDPlanRegistSCH;
import jp.co.daifuku.wms.crossdock.schedule.XDPlanRegistSCHParams;

/**
 * TC予定データ登録（詳細情報）の画面処理を行います。
 *
 * @version $Revision: 7515 $, $Date: 2010-03-13 13:40:51 +0900 (土, 13 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
@SuppressWarnings("serial")
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

    // DFKLOOK:DAC added from here
    /**
     * 最大行No.
     */
    private static final int MAX_LINE_NO = 1000;

    /**
     * Container to store Line No. used in the current screen. 
     */
    private static ArrayList<Integer> ListOfLineNo;

    /**
     * The current container of Line No. for the current Slip (Ticket) No. 
     */
    private static ArrayList<Integer> temp;

    /**
     * Flag for modification of a row in Listcell
     */
    private static boolean modifying;

    /**
     * Flag for modification of Slip (Ticket) No.
     */
    private static boolean modifyingSlipNo;

    /**
     * HashMap to store containers of Line No. for each Slip (Ticket) No. 
     */
    private static HashMap<String, ArrayList<Integer>> map;

    /**
     * Flag for enterkey event
     */
    private static boolean enterKey;

    /**
     * Variable to store the preivous Shippping Slip (Ticket) No.
     */
    private static String previousShippingNo;

    /**
     * Variable to store the preivous Shippping Slip (Ticket) Line No.
     */
    private static int previousLineNo;

    /**
     * Start index for a row in Listcell when modification or deletion happens.
     */
    private static int start;

    /**
     * Variable to store the next line No. when Auto_Line_No is false.
     */
    private static int nonAutoNext;
    // DFKLOOK:DAC added to here

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

        // DFKLOOK:ここから修正
        initAutoLineNumber_process();
        // DFKLOOK:ここまで修正
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
        //DFKLOOK:ここから修正
        if (eventSource.startsWith("btn_Input_Click"))
        {
            // process call.
            btn_Input_Click_Process(eventSource);
        }
        else if (eventSource.startsWith("btn_Set_Click"))
        {
            // process call.
            btn_Set_Click_Process(eventSource);
        }
        //DFKLOOK:ここまで修正
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
    @SuppressWarnings("unchecked")
    public void txt_ShipSlipNumber_EnterKey(ActionEvent e)
            throws Exception
    {
        //DFKLOOK:DAC add start
        enterKey = true;
        // First check if Shipping Ticket # is used or nor
        String slipNo = txt_ShipSlipNumber.getText();
        map = (HashMap<String, ArrayList<Integer>>)this.viewState.getObject(ViewStateKeys.MAP_LINE_NO);
        temp = (ArrayList<Integer>)map.get(slipNo);
        int next = 0;
        if (temp == null || temp.size() == 0)
        {
            next = LineNumber.getNextLineNo(slipNo, 5, this, WmsParam.AUTO_LINE_NO);
        }
        else
        {
            next = temp.get(temp.size() - 1) + 1;
        }

        // checks the Max Line No.
        // If the Line No. exceeds 999, then shows an error message
        // next = 998; // for debug
        if (!isLineNoOver(next))
        {
            this.txt_ShipSlipLineNo.setInt(next);
            nonAutoNext = next;
            this.viewState.setInt(ViewStateKeys.NEXT_LINE_NO, nonAutoNext);
            setCustomerCodeFocus();
        }
        //DFKLOOK:DAC add end
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
        //DFKLOOK:ここから修正
        btn_Input_Click_Process(null);
        //DFKLOOK:ここまで修正
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
        //DFKLOOK:ここから修正
        btn_Set_Click_Process(null);
        //DFKLOOK:ここまで修正
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
    @SuppressWarnings("all")
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
        // DFKLOOK:DAC added from here
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        XDPlanRegistSCH sch = null;

        Connection conn = null;
        Locale locale = httpRequest.getLocale();

        try
        {
            conn = ConnectionManager.getRequestConnection(this);

            // Master check :マスタ管理有無チェック
            sch = new XDPlanRegistSCH(conn, this.getClass(), locale, ui);
            XDOutParameter outparam = (XDOutParameter)sch.initFind(new Parameter());

            // マスタ管理有無フラグをViewStateにセット
            this.viewState.setBoolean(ViewStateKeys.MASTER, outparam.isMasterFlag());
            //マスタパッケージありの場合、テキストボックス無効化
            if (this.viewState.getBoolean(ViewStateKeys.MASTER))
            {
                txt_CustomerName.setReadOnly(true);
            }
            // DFKLOOK:DAC added to here

            // output display.
            // DFKLOOK:ここから修正
            lbl_InPlanDate.setValue(WmsFormatter.toDispDate(viewState.getDate(ViewStateKeys.PLAN_DATE), locale));
            if (StringUtil.isBlank((String)viewState.getObject(ViewStateKeys.BATCH_NO)))
            {
                String date = WmsFormatter.toDispDate(viewState.getDate(ViewStateKeys.PLAN_DATE), locale);
                lbl_InBatchNo.setValue(date.replace("/", ""));
                viewState.setObject(ViewStateKeys.BATCH_NO, date.replace("/", ""));
            }
            else
            {
                lbl_InBatchNo.setValue(viewState.getObject(ViewStateKeys.BATCH_NO));
            }
            // DFKLOOK:ここまで修正

            lbl_InReceivingSlipNo.setValue(viewState.getObject(ViewStateKeys.RECEIVING_SLIP_NUMBER));

            // DFKLOOK:ここから修正
            lbl_InReceivingSlipLineNo.setValue(String.valueOf(viewState.getObject(ViewStateKeys.LINE_NO)));
            // DFKLOOK:ここまで修正

            lbl_InSupplierCode.setValue(viewState.getObject(ViewStateKeys.SUPPLIER_CODE));
            lbl_InItemCode.setValue(viewState.getObject(ViewStateKeys.ITEM_CODE));
            lbl_InLotNo.setValue(viewState.getObject(ViewStateKeys.LOT_NO));
            viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // DFKLOOK: from here
            // With Master
            if (this.viewState.getBoolean(ViewStateKeys.MASTER))
            {
                // Supplier Name
                SupplierController supplierController = new SupplierController(conn, this.getClass());
                String supplierCode = viewState.getString(ViewStateKeys.SUPPLIER_CODE);
                String supplierName = supplierController.getName(supplierCode, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lbl_InSupplierName.setText(supplierName);

                // gets item information 
                String itemCode = viewState.getString(ViewStateKeys.ITEM_CODE);
                Item itemInfo = ItemController.getItemInfo(itemCode, WmsParam.DEFAULT_CONSIGNOR_CODE, conn);
                lbl_InItemName.setText(itemInfo.getItemName());
                lbl_InEnteringQty.setValue(String.valueOf(itemInfo.getValue(Item.ENTERING_QTY)));
                lbl_InJanCode.setValue(itemInfo.getJan());
                lbl_InCaseITF.setValue(itemInfo.getItf());

                // viewState
                viewState.setObject(ViewStateKeys.SUPPLIER_NAME, supplierName);
                viewState.setObject(ViewStateKeys.ITEM_NAME, itemInfo.getItemName());
                viewState.setObject(ViewStateKeys.CASE_PACK, String.valueOf(itemInfo.getValue(Item.ENTERING_QTY)));
                viewState.setObject(ViewStateKeys.JAN_CODE, itemInfo.getJan());
                viewState.setObject(ViewStateKeys.CASE_ITF, itemInfo.getItf());
            }
            // Without Master
            else
            {
                // Supplier Name
                if (StringUtil.isBlank((String)viewState.getObject(ViewStateKeys.SUPPLIER_NAME)))
                {
                    lbl_InSupplierName.setValue("");
                }
                else
                {
                    lbl_InSupplierName.setValue(viewState.getObject(ViewStateKeys.SUPPLIER_NAME));
                }

                // Item Name
                if (StringUtil.isBlank((String)viewState.getObject(ViewStateKeys.ITEM_NAME)))
                {
                    lbl_InItemName.setText("");
                }
                else
                {
                    lbl_InItemName.setValue(viewState.getObject(ViewStateKeys.ITEM_NAME));
                }
                // Case pack
                if (null == viewState.getObject(ViewStateKeys.CASE_PACK))
                {
                    lbl_InEnteringQty.setValue("0");
                }
                else
                {
                    lbl_InEnteringQty.setValue(viewState.getObject(ViewStateKeys.CASE_PACK).toString());
                }
                // UPC
                if (StringUtil.isBlank((String)viewState.getObject(ViewStateKeys.JAN_CODE)))
                {
                    lbl_InJanCode.setValue("");
                }
                else
                {
                    lbl_InJanCode.setValue(viewState.getObject(ViewStateKeys.JAN_CODE));
                }
                // ITF
                if (StringUtil.isBlank((String)viewState.getObject(ViewStateKeys.CASE_ITF)))
                {
                    lbl_InCaseITF.setValue("");
                }
                else
                {
                    lbl_InCaseITF.setValue(viewState.getObject(ViewStateKeys.CASE_ITF));
                }
            }
            // DFKLOOK: from here

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
    @SuppressWarnings("unchecked")
    private void btn_Input_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        txt_PlanCaseQty.validate(this, false);
        txt_PlanPieceQty.validate(this, false);
        txt_ShipSlipNumber.validate(this, true);
        txt_ShipSlipLineNo.validate(this, false);

        // DFKLOOK: Auto Numbering from here
        int check = this.txt_ShipSlipLineNo.getInt();
        if (this.isLineNoOver(check))
        {
            return;
        }
        if (WmsParam.AUTO_LINE_NO)
        {
            txt_ShipSlipLineNo.validate(this, false);
        }
        else
        {
            txt_ShipSlipLineNo.validate(this, true);
        }
        // DFKLOOK: Auto Numbering to here

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

            // DFKLOOK:DAC added from here
            // If MASTER exists
            if (this.getViewState().getBoolean(ViewStateKeys.MASTER))
            {
                XDPlanRegistSCHParams inparam = new XDPlanRegistSCHParams();
                inparam.set(XDPlanRegistSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
                inparam.set(XDPlanRegistSCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());

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
                        // Customer Name : 出荷先名称, Sorting place
                        txt_CustomerName.setText(outparam.getString(XDPlanRegistSCHParams.CUSTOMER_NAME));
                    }
                }
            }

            if (WmsParam.AUTO_LINE_NO)
            {
                if (LineNumber.isLineNoUsed(txt_ShipSlipNumber.getText(), 5, this.txt_ShipSlipLineNo.getInt(), this))
                {
                    message.setMsgResourceKey("6023020");
                    return;
                }
            }

            if (previousShippingNo.compareTo(txt_ShipSlipNumber.getText()) != 0)
            {
                modifyingSlipNo = true;
            }

            if (WmsParam.AUTO_LINE_NO)
            {
                if (!enterKey || modifyingSlipNo)
                {
                    map = (HashMap<String, ArrayList<Integer>>)this.viewState.getObject(ViewStateKeys.MAP_LINE_NO);
                    int next =
                            LineNumber.getNextLineNoForXDShippingPlan(map, txt_ShipSlipNumber.getText(),
                                    txt_ShipSlipLineNo.getInt(), _lcm_lst_TcPlanDataRegist, WmsParam.AUTO_LINE_NO,
                                    false, false, KEY_LST_SHIPPING_TICKET_LINE, KEY_LST_SHIPPING_TICKET, this, -1,
                                    txt_ShipSlipNumber.getText());
                    // checks the Max Line No.
                    // If the Line No. exceeds 999, then shows an error message
                    // next = 998; // bebug
                    if (isLineNoOver(next))
                    {
                        return;
                    }
                    txt_ShipSlipLineNo.setInt(next);
                }
            }
            else
            {
                //  User does not input
                nonAutoNext = this.viewState.getInt(ViewStateKeys.NEXT_LINE_NO);
                if (nonAutoNext == this.txt_ShipSlipLineNo.getInt())
                {
                    if (!enterKey || modifyingSlipNo)
                    {
                        map = (HashMap<String, ArrayList<Integer>>)this.viewState.getObject(ViewStateKeys.MAP_LINE_NO);
                        int next =
                                LineNumber.getNextLineNoForXDShippingPlan(map, txt_ShipSlipNumber.getText(),
                                        txt_ShipSlipLineNo.getInt(), _lcm_lst_TcPlanDataRegist, WmsParam.AUTO_LINE_NO,
                                        false, false, KEY_LST_SHIPPING_TICKET_LINE, KEY_LST_SHIPPING_TICKET, this, -1,
                                        txt_ShipSlipNumber.getText());
                        // checks the Max Line No.
                        // If the Line No. exceeds 999, then shows an error message
                        // next = 998; // bebug
                        if (isLineNoOver(next))
                        {
                            return;
                        }
                        txt_ShipSlipLineNo.setInt(next);
                    }
                }
                // User input
                else
                {
                    // Check if this line no is used or not
                    // In map and in DB
                    map = (HashMap<String, ArrayList<Integer>>)this.viewState.getObject(ViewStateKeys.MAP_LINE_NO);
                    boolean checkInMap =
                            LineNumber.isUsedInMap(map, txt_ShipSlipNumber.getText(), txt_ShipSlipLineNo.getInt());
                    boolean checkInDB =
                            LineNumber.isLineNoUsed(txt_ShipSlipNumber.getText(), 5, txt_ShipSlipLineNo.getInt(), this);
                    if (checkInMap || checkInDB)
                    {
                        message.setMsgResourceKey("6023020");
                        return;
                    }
                }
            }
            // DFKLOOK:DAC added to here

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
            // DFKLOOK:ここから修正
            inparam.set(XDPlanRegistSCHParams.PLAN_DATE, WmsFormatter.toDispDate(
                    viewState.getDate(ViewStateKeys.PLAN_DATE), locale));
            // DFKLOOK:ここまで修正
            inparam.set(XDPlanRegistSCHParams.RECEIVING_SLIP_NUMBER,
                    viewState.getObject(ViewStateKeys.RECEIVING_SLIP_NUMBER));
            inparam.set(XDPlanRegistSCHParams.SUPPLIER_CODE, viewState.getObject(ViewStateKeys.SUPPLIER_CODE));
            inparam.set(XDPlanRegistSCHParams.SUPPLIER_NAME, viewState.getObject(ViewStateKeys.SUPPLIER_NAME));

            // DFKLOOK: DAC modified from here
            inparam.set(XDPlanRegistSCHParams.SHIPPING_TICKET, txt_ShipSlipNumber.getValue());
            inparam.set(XDPlanRegistSCHParams.SHIPPING_TICKET_LINE, txt_ShipSlipLineNo.getValue());
            // DFKLOOK: DAC modified to here

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
            //DFKLOOK:ここから修正
            if (StringUtil.isBlank(eventSource) && !sch.check(inparam, inparams))
            //DFKLOOK:ここまで修正
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
            // DFKLOOK:ここから修正
            line.setValue(KEY_LST_PLAN_CASE_QTY, txt_PlanCaseQty.getInt());
            line.setValue(KEY_LST_PLAN_PIECE_QTY, txt_PlanPieceQty.getInt());
            // DFKLOOK:ここまで修正
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

            // DFKLOOK:ここから修正
            if (!modifying)
            {
                if (txt_ShipSlipLineNo.getInt() != 0)
                {
                    map = (HashMap<String, ArrayList<Integer>>)this.viewState.getObject(ViewStateKeys.MAP_LINE_NO);
                    LineNumber.addLineNo(map, txt_ShipSlipNumber.getText(), txt_ShipSlipLineNo.getInt());
                }

                if (!WmsParam.AUTO_LINE_NO)
                {
                    if (txt_ShipSlipLineNo.getInt() == 0)
                    {
                        map = (HashMap<String, ArrayList<Integer>>)this.viewState.getObject(ViewStateKeys.MAP_LINE_NO);
                        LineNumber.addLineNo(map, txt_ShipSlipNumber.getText(), txt_ShipSlipLineNo.getInt());
                    }
                }
            }
            else
            {
                if (!modifyingSlipNo)
                {
                    map = (HashMap<String, ArrayList<Integer>>)this.viewState.getObject(ViewStateKeys.MAP_LINE_NO);
                    temp = (ArrayList<Integer>)map.get(this.txt_ShipSlipNumber.getText());
                    if (this.txt_ShipSlipLineNo.getInt() != 0)
                    {
                        int index = temp.indexOf(previousLineNo);
                        temp.set(index, txt_ShipSlipLineNo.getInt());
                    }
                }
                else
                {
                    map = (HashMap<String, ArrayList<Integer>>)this.viewState.getObject(ViewStateKeys.MAP_LINE_NO);
                    LineNumber.addLineNo(map, txt_ShipSlipNumber.getText(), txt_ShipSlipLineNo.getInt());
                    // delete old data from map
                    LineNumber.deleteElement(map, previousShippingNo, previousLineNo);
                }
            }
            map = (HashMap<String, ArrayList<Integer>>)this.viewState.getObject(ViewStateKeys.MAP_LINE_NO);
            int next =
                    LineNumber.getNextLineNoForXDShippingPlan(map, txt_ShipSlipNumber.getText(),
                            txt_ShipSlipLineNo.getInt(), _lcm_lst_TcPlanDataRegist, WmsParam.AUTO_LINE_NO, modifying,
                            false, KEY_LST_SHIPPING_TICKET_LINE, KEY_LST_SHIPPING_TICKET, this, start,
                            txt_ShipSlipNumber.getText());

            // Checks the Max Line No.
            // If the Line No. exceeds 999, then shows an error message.
            //next = 998; // for debug
            if (!isLineNoOver(next))
            {
                txt_ShipSlipLineNo.setInt(next);

                if (WmsParam.AUTO_LINE_NO)
                {
                    map = (HashMap<String, ArrayList<Integer>>)this.viewState.getObject(ViewStateKeys.MAP_LINE_NO);
                    LineNumber.sortSlipNoAndLineNo(_lcm_lst_TcPlanDataRegist, KEY_LST_SHIPPING_TICKET,
                            KEY_LST_SHIPPING_TICKET_LINE, map);
                }

                enterKey = true;
                modifying = false;
                modifyingSlipNo = false;
                previousShippingNo = txt_ShipSlipNumber.getText();
                nonAutoNext = next; // this is used when Auto_Line_No = false
                this.viewState.setInt(ViewStateKeys.NEXT_LINE_NO, nonAutoNext);
            }

            // DFKLOOK:ここまで修正
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

        // DFKLOOK:ここから修正
        enterKey = false;
        this.btn_Input.setEnabled(true);
        this.btn_PSearch.setEnabled(true);
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 登録しますか？
            this.setConfirm("MSG-W0012", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;
        }
        // DFKLOOK end

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
                //DFKLOOK:ここから修正
                part11List.add(lbl_InJanCode.getText(), "");
                part11List.add(lbl_InCaseITF.getText(), "");
                part11List.add(lbl_InLotNo.getText(), "");
                //DFKLOOK:ここまで修正
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

        // DFKLOOK:ここから修正
        clearAllAutoLineNumber_process();
        // DFKLOOK:ここまで修正
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
        txt_PlanCaseQty.setInt(Integer.parseInt(line.getStringValue(KEY_LST_PLAN_CASE_QTY)));
        txt_PlanPieceQty.setInt(Integer.parseInt(line.getStringValue(KEY_LST_PLAN_PIECE_QTY)));
        txt_ShipSlipNumber.setValue(line.getValue(KEY_LST_SHIPPING_TICKET));

        // DFKLOOK:DAC modified from here
        txt_ShipSlipLineNo.setText((String)(line.getValue(KEY_LST_SHIPPING_TICKET_LINE)));
        // DFKLOOK:DAC modified to here

        txt_CustomerCode.setValue(line.getValue(KEY_LST_CUSTOMER_CODE));
        txt_CustomerName.setValue(line.getValue(KEY_LST_CUSTOMER_NAME));
        txt_SortPlace.setValue(line.getValue(KEY_LST_CONSOLIDATION_AREA));

        // highlight active row.
        _lcm_lst_TcPlanDataRegist.resetHighlight();
        _lcm_lst_TcPlanDataRegist.addHighlight(row);
        _lcm_lst_TcPlanDataRegist.setEditRow(row);

        // DFKLOOK:ここから修正
        start = row;
        modifying = true;
        previousShippingNo = (String)(_lcm_lst_TcPlanDataRegist.get(row)).getValue(KEY_LST_SHIPPING_TICKET);
        previousLineNo =
                Integer.parseInt((String)(_lcm_lst_TcPlanDataRegist.get(row)).getValue(KEY_LST_SHIPPING_TICKET_LINE));
        nonAutoNext = previousLineNo;
        this.viewState.setInt(ViewStateKeys.NEXT_LINE_NO, nonAutoNext);
        // DFKLOOK:ここまで修正
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
        int row = lst_TcPlanDataRegist.getActiveRow();
        lst_TcPlanDataRegist.setCurrentRow(row);

        // DFKLOOK:ここから修正
        // row is active row number.
        String slipNo = _lcm_lst_TcPlanDataRegist.get(row).getStringValue(KEY_LST_SHIPPING_TICKET);
        int lineNo = Integer.parseInt(_lcm_lst_TcPlanDataRegist.get(row).getStringValue(KEY_LST_SHIPPING_TICKET_LINE));

        // DFKLOOK:ここまで修正

        // reset editing row.
        lst_TcPlanDataRegist.removeRow(row);
        _lcm_lst_TcPlanDataRegist.resetEditRow();
        _lcm_lst_TcPlanDataRegist.resetHighlight();

        // DFKLOOK:ここから修正
        if (this.lst_TcPlanDataRegist.getMaxRows() == 1)
        {
            btn_Set.setEnabled(false);
            btn_AllClear.setEnabled(false);
        }

        map = (HashMap<String, ArrayList<Integer>>)this.viewState.getObject(ViewStateKeys.MAP_LINE_NO);
        int next =
                LineNumber.getNextLineNoForXDShippingPlan(map, slipNo, lineNo, _lcm_lst_TcPlanDataRegist,
                        WmsParam.AUTO_LINE_NO, modifying, true, KEY_LST_SHIPPING_TICKET_LINE, KEY_LST_SHIPPING_TICKET,
                        this, row, txt_ShipSlipNumber.getText());
        this.txt_ShipSlipLineNo.setInt(next);
        modifying = false;
        // DFKLOOK:ここまで修正
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

    // DFKLOOK:ここから修正
    /**
     * Initializes auto line numbering
     */
    private void initAutoLineNumber_process()
    {
        if (WmsParam.AUTO_LINE_NO)
        {
            this.txt_ShipSlipLineNo.setReadOnly(true);
        }
        else
        {
            this.txt_ShipSlipLineNo.setReadOnly(false);
        }
        ListOfLineNo = new ArrayList<Integer>();
        modifying = false;
        modifyingSlipNo = false;
        map = new HashMap<String, ArrayList<Integer>>();
        this.viewState.setObject(ViewStateKeys.MAP_LINE_NO, map);
        enterKey = false;
        previousShippingNo = "";
        start = 0;
        nonAutoNext = 0;
        this.viewState.setInt(ViewStateKeys.NEXT_LINE_NO, nonAutoNext);
    }

    /**
     * Clear all for auto line nunmbering
     * @throws Exception
     */
    private void clearAllAutoLineNumber_process()
            throws Exception
    {
        ListOfLineNo.clear();
        modifying = false;
        modifyingSlipNo = false;
        map.clear();
        this.viewState.setObject(ViewStateKeys.MAP_LINE_NO, map);
        enterKey = false;
        previousShippingNo = "";
        start = 0;
        int next =
                LineNumber.getNextLineNoForXDShippingPlan(map, txt_ShipSlipNumber.getText(),
                        txt_ShipSlipLineNo.getInt(), _lcm_lst_TcPlanDataRegist, WmsParam.AUTO_LINE_NO, modifying,
                        false, KEY_LST_SHIPPING_TICKET_LINE, KEY_LST_SHIPPING_TICKET, this, start,
                        txt_ShipSlipNumber.getText());
        txt_ShipSlipLineNo.setInt(next);
    }


    /**
     * Gets the previous shipping  no.
     * @return String
     */
    public String getPreviousShippingSlipNo()
    {
        return previousShippingNo;
    }

    /**
     * Set a focus on Customer Code
     */
    private void setCustomerCodeFocus()
    {
        if (WmsParam.AUTO_LINE_NO)
        {
            setFocus(txt_CustomerCode);
        }
        else
        {
            setFocus(txt_ShipSlipLineNo);
        }
    }

    /**
     * Checks if the Line No is over the max or not.
     * If it exceeds the Max, then shows a message.
     * @param lineNo lineNo
     * @return boolean
     */
    private boolean isLineNoOver(int lineNo)
            throws Exception
    {
        if (lineNo >= MAX_LINE_NO)
        {
            message.setMsgResourceKey("6023008"); // 6407008 ->6023008
            return true;
        }
        return false;
    }

    // DFKLOOK:ここまで修正

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

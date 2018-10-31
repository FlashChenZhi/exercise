// $Id: BatchStartCancelBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.pcart.retrieval.display.batchstartcancel;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.retrieval.dasch.BatchStartCancelDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.BatchStartCancelDASCHParams;
import jp.co.daifuku.pcart.retrieval.exporter.PctItemMasterListParams;
import jp.co.daifuku.pcart.retrieval.schedule.BatchStartCancelSCH;
import jp.co.daifuku.pcart.retrieval.schedule.BatchStartCancelSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * バッチ開始/キャンセルの画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class BatchStartCancelBusiness
        extends BatchStartCancel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // DFKLOOK:ここから修正
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH2";

    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK:ここまで修正

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_BatchStartCancel(HIDDEN_STATUS_FLAG) */
    private static final ListCellKey KEY_HIDDEN_STATUS_FLAG =
            new ListCellKey("HIDDEN_STATUS_FLAG", new StringCellColumn(), false, false);

    /** lst_BatchStartCancel(HIDDEN_REPORT_FLAG) */
    private static final ListCellKey KEY_HIDDEN_REPORT_FLAG =
            new ListCellKey("HIDDEN_REPORT_FLAG", new StringCellColumn(), false, false);

    /** lst_BatchStartCancel(HIDDEN_NUMBERING_FLAG) */
    private static final ListCellKey KEY_HIDDEN_NUMBERING_FLAG =
            new ListCellKey("HIDDEN_NUMBERING_FLAG", new StringCellColumn(), false, false);

    /** lst_BatchStartCancel(HIDDEN_SCHEDULE_FLAG) */
    private static final ListCellKey KEY_HIDDEN_SCHEDULE_FLAG =
            new ListCellKey("HIDDEN_SCHEDULE_FLAG", new StringCellColumn(), false, false);

    /** lst_BatchStartCancel(HIDDEN_WEIGHT_FLAG) */
    private static final ListCellKey KEY_HIDDEN_WEIGHT_FLAG =
            new ListCellKey("HIDDEN_WEIGHT_FLAG", new StringCellColumn(), false, false);

    /** lst_BatchStartCancel(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_BatchStartCancel(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO =
            new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_BatchStartCancel(LST_BATCH_SEQ_NO) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO =
            new ListCellKey("LST_BATCH_SEQ_NO", new StringCellColumn(), true, false);

    /** lst_BatchStartCancel(LST_ORDER_QTY) */
    private static final ListCellKey KEY_LST_ORDER_QTY =
            new ListCellKey("LST_ORDER_QTY", new StringCellColumn(), true, false);

    /** lst_BatchStartCancel(LST_LINE_NO) */
    private static final ListCellKey KEY_LST_LINE_NO =
            new ListCellKey("LST_LINE_NO", new StringCellColumn(), true, false);

    /** lst_BatchStartCancel(LST_LAST_UPDATE_DATE) */
    private static final ListCellKey KEY_LST_LAST_UPDATE_DATE =
            new ListCellKey("LST_LAST_UPDATE_DATE", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_SEC), true, false);

    /** lst_BatchStartCancel(LST_STATUS) */
    private static final ListCellKey KEY_LST_STATUS =
            new ListCellKey("LST_STATUS", new StringCellColumn(), true, false);

    /** lst_BatchStartCancel(LST_WEIGHT_UNREGISTERED) */
    private static final ListCellKey KEY_LST_WEIGHT_UNREGISTERED =
            new ListCellKey("LST_WEIGHT_UNREGISTERED", new StringCellColumn(), true, false);

    /** lst_PCTUnRegistItemList(HIDDEN_JAN) */
    private static final ListCellKey KEY_HIDDEN_JAN =
            new ListCellKey("HIDDEN_JAN", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_ITF) */
    private static final ListCellKey KEY_HIDDEN_ITF =
            new ListCellKey("HIDDEN_ITF", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_LOCATION_NO) */
    private static final ListCellKey KEY_HIDDEN_LOCATION_NO =
            new ListCellKey("HIDDEN_LOCATION_NO", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_SINGLE_WEIGHT) */
    private static final ListCellKey KEY_HIDDEN_SINGLE_WEIGHT =
            new ListCellKey("HIDDEN_SINGLE_WEIGHT", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_WEGHT_DISTINCT_RATE) */
    private static final ListCellKey KEY_HIDDEN_WEGHT_DISTINCT_RATE =
            new ListCellKey("HIDDEN_WEGHT_DISTINCT_RATE", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_MAX_INSPECTION_UNIT_QTY) */
    private static final ListCellKey KEY_HIDDEN_MAX_INSPECTION_UNIT_QTY =
            new ListCellKey("HIDDEN_MAX_INSPECTION_UNIT_QTY", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_LAST_UPDATE) */
    private static final ListCellKey KEY_HIDDEN_LAST_UPDATE =
            new ListCellKey("HIDDEN_LAST_UPDATE", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_WORK_DAY) */
    private static final ListCellKey KEY_HIDDEN_WORK_DAY =
            new ListCellKey("HIDDEN_WORK_DAY", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_ITEM_PICTURE_FLAG) */
    private static final ListCellKey KEY_HIDDEN_ITEM_PICTURE_FLAG =
            new ListCellKey("HIDDEN_ITEM_PICTURE_FLAG", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_MESSAGE1) */
    private static final ListCellKey KEY_HIDDEN_MESSAGE1 =
            new ListCellKey("HIDDEN_MESSAGE1", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(LST_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_LST_CONSIGNOR_CODE =
            new ListCellKey("LST_CONSIGNOR_CODE", new StringCellColumn(), true, false);

    /** lst_PCTUnRegistItemList(LST_CONSIGNOR_NAME) */
    private static final ListCellKey KEY_LST_CONSIGNOR_NAME =
            new ListCellKey("LST_CONSIGNOR_NAME", new StringCellColumn(), true, false);

    /** lst_PCTUnRegistItemList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_PCTUnRegistItemList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_PCTUnRegistItemList(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY =
            new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTUnRegistItemList(LST_CUSTOMER_LIST) */
    private static final ListCellKey KEY_LST_CUSTOMER_LIST =
            new ListCellKey("LST_CUSTOMER_LIST", new StringCellColumn(), true, false);

    /** lst_BatchStartCancel kyes */
    private static final ListCellKey[] LST_BATCHSTARTCANCEL_KEYS = {
            KEY_HIDDEN_STATUS_FLAG,
            KEY_HIDDEN_REPORT_FLAG,
            KEY_HIDDEN_NUMBERING_FLAG,
            KEY_HIDDEN_SCHEDULE_FLAG,
            KEY_HIDDEN_WEIGHT_FLAG,
            KEY_LST_SELECT,
            KEY_LST_BATCH_NO,
            KEY_LST_BATCH_SEQ_NO,
            KEY_LST_ORDER_QTY,
            KEY_LST_LINE_NO,
            KEY_LST_LAST_UPDATE_DATE,
            KEY_LST_STATUS,
            KEY_LST_WEIGHT_UNREGISTERED,
    };

    /** lst_PCTUnRegistItemList kyes */
    private static final ListCellKey[] LST_PCTUNREGISTITEMLIST_KEYS = {
            KEY_HIDDEN_JAN,
            KEY_HIDDEN_ITF,
            KEY_HIDDEN_LOCATION_NO,
            KEY_HIDDEN_SINGLE_WEIGHT,
            KEY_HIDDEN_WEGHT_DISTINCT_RATE,
            KEY_HIDDEN_MAX_INSPECTION_UNIT_QTY,
            KEY_HIDDEN_LAST_UPDATE,
            KEY_HIDDEN_WORK_DAY,
            KEY_HIDDEN_ITEM_PICTURE_FLAG,
            KEY_HIDDEN_MESSAGE1,
            KEY_LST_CONSIGNOR_CODE,
            KEY_LST_CONSIGNOR_NAME,
            KEY_LST_ITEM_CODE,
            KEY_LST_ITEM_NAME,
            KEY_LST_ENTERING_QTY,
            KEY_LST_CUSTOMER_LIST,
    };

    // DFKLOOK:ここから修正
    // オーダー数
    private static final int LST_ORDER = 4;

    // 重量未登録商品
    private static final int LST_WEIGH = 8;

    // DFKLOOK:ここまで修正

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_BatchStartCancel */
    private ListCellModel _lcm_lst_BatchStartCancel;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_PCTUnRegistItemList */
    private ListCellModel _lcm_lst_PCTUnRegistItemList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public BatchStartCancelBusiness()
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
    }

    // DFKLOOK start
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
        if (eventSource.startsWith("btn_Start_Click"))
        {
            btn_Start_Click_Process(eventSource);
        }
        if (eventSource.startsWith("btn_WorkCancel_Click"))
        {
        	btn_WorkCancel_Click_Process(eventSource);
        }
    }
    // DFKLOOK end
    
    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Start_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Start_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ReDisplayFunc_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ReDisplayFunc_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_WorkCancel_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_WorkCancel_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllCheck_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheck_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllCheckClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheckClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_UnRegistItem_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_UnRegistItem_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_up_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        btn_UnRegistItem_Click_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_up_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        btn_UnRegistItem_Click_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_up_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        btn_UnRegistItem_Click_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_up_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        btn_UnRegistItem_Click_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Preview_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Preview_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Print_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Print_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_XLS_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_XLS_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ListClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_PCTUnRegistItemList_Click(ActionEvent e)
            throws Exception
    {
        lst_PCTUnRegistItemList_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_down_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        btn_UnRegistItem_Click_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_down_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        btn_UnRegistItem_Click_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_down_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        btn_UnRegistItem_Click_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_down_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        btn_UnRegistItem_Click_SetList();
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

        // initialize lst_BatchStartCancel.
        _lcm_lst_BatchStartCancel = new ListCellModel(lst_BatchStartCancel, LST_BATCHSTARTCANCEL_KEYS, locale);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_BATCH_NO, false);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_BATCH_SEQ_NO, false);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_ORDER_QTY, false);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_LINE_NO, false);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_LAST_UPDATE_DATE, false);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_STATUS, false);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_WEIGHT_UNREGISTERED, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[] {
                pager_up,
                pager_down
        }, locale);

        // initialize lst_PCTUnRegistItemList.
        _lcm_lst_PCTUnRegistItemList =
                new ListCellModel(lst_PCTUnRegistItemList, LST_PCTUNREGISTITEMLIST_KEYS, locale);
        _lcm_lst_PCTUnRegistItemList.setToolTipVisible(KEY_LST_CONSIGNOR_CODE, true);
        _lcm_lst_PCTUnRegistItemList.setToolTipVisible(KEY_LST_CONSIGNOR_NAME, true);
        _lcm_lst_PCTUnRegistItemList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_PCTUnRegistItemList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_PCTUnRegistItemList.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_PCTUnRegistItemList.setToolTipVisible(KEY_LST_CUSTOMER_LIST, true);
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
    private void lst_BatchStartCancel_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTUnRegistItemList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-P0016", KEY_LST_CONSIGNOR_NAME);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        BatchStartCancelSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new BatchStartCancelSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            BatchStartCancelSCHParams inparam = new BatchStartCancelSCHParams();

            // DFKLOOK:ここから修正
            // マスタフラグ
            Params initParam = sch.initFind(inparam);

            viewState.setObject(ViewStateKeys.MASTER_FLAG, initParam.get(BatchStartCancelSCHParams.MASTER_FLAG));
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_BatchStartCancel.clear();

            // DFKLOOK:ここから修正
            if (outparams.size() <= 0)
            {
                buttonEnabled(false);
                setbtnEnabele(false);
                return;
            }
            else
            {
                buttonEnabled(true);
            }
            // DFKLOOK:ここまで修正

            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_BatchStartCancel.getNewLine();
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(BatchStartCancelSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(BatchStartCancelSCHParams.BATCH_NO));

                // DFKLOOK:ここから修正
                line.setValue(KEY_LST_ORDER_QTY,
                        Formatter.getNumFormat(outparam.getInt(BatchStartCancelSCHParams.ORDER_QTY)));
                line.setValue(KEY_LST_LINE_NO,
                        Formatter.getNumFormat(outparam.getInt(BatchStartCancelSCHParams.LINE_NO)));
                // DFKLOOK:ここまで修正

                line.setValue(KEY_LST_LAST_UPDATE_DATE, outparam.get(BatchStartCancelSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_STATUS, outparam.get(BatchStartCancelSCHParams.STATUS));
                line.setValue(KEY_HIDDEN_STATUS_FLAG, outparam.get(BatchStartCancelSCHParams.STATUS_FLAG));
                line.setValue(KEY_HIDDEN_REPORT_FLAG, outparam.get(BatchStartCancelSCHParams.REPORT_FLAG));
                line.setValue(KEY_LST_WEIGHT_UNREGISTERED, outparam.get(BatchStartCancelSCHParams.WEIGHT_UNREGISTERED));
                line.setValue(KEY_HIDDEN_NUMBERING_FLAG,
                        outparam.get(BatchStartCancelSCHParams.ORDER_NO_NUMBERING_FLAG));
                line.setValue(KEY_HIDDEN_SCHEDULE_FLAG, outparam.get(BatchStartCancelSCHParams.SCHEDULE_FLAG));
                line.setValue(KEY_HIDDEN_WEIGHT_FLAG, outparam.get(BatchStartCancelSCHParams.WEIGHT_FLAG));
                lst_BatchStartCancel_SetLineToolTip(line);
                _lcm_lst_BatchStartCancel.add(line);

                // DFKLOOK:ここから修正
                // PCTマスタがない場合重量未登録商品の列を非表示
                if (!viewState.getBooleanObject(ViewStateKeys.MASTER_FLAG))
                {
                    // リストセルから重量未登録商品の列を非表示
                    _lcm_lst_BatchStartCancel.getListCell().setColumnHidden(LST_WEIGH, true);
                }
                // オーダー数(eWareNaviでオーダーNo.を採番する場合は非表示とする)
                if (outparam.getBoolean(BatchStartCancelSCHParams.ORDER_NO_NUMBERING_FLAG))
                {
                    _lcm_lst_BatchStartCancel.getListCell().setColumnHidden(LST_ORDER, true);
                }
                // DFKLOOK:ここまで修正
            }

            // DFKLOOK:ここから修正
            // 商品一覧表示/非表示
            setItemListCell();
            // ボタンの初期化
            setbtnEnabele(false);
            // フォーカスセット
            setFocus(lst_BatchStartCancel);
            // DFKLOOK:ここまで修正
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
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
    private void btn_Start_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_BatchStartCancel.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_BatchStartCancel.get(i);

            if ((checkline.isAppend() || checkline.isEdited())
                    && SystemDefine.SCH_FLAG_NOT_SCHEDULE.equals(checkline.getValue(KEY_HIDDEN_SCHEDULE_FLAG)))
            {
                existEditedRow = true;
                break;
            }

        }

        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003110");
            // 商品一覧表示/非表示
            setItemListCell();

            // フォーカスセット
            setFocus(lst_BatchStartCancel);

            return;
        }

        if (StringUtil.isBlank(eventSource))
        {         
            // 開始しますか?
            this.setConfirm("MSG-W0031", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Start_Click");
            return;         
        }
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        BatchStartCancelSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new BatchStartCancelSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_BatchStartCancel.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_BatchStartCancel.get(i);

                // DFKLOOK:ここから修正
                if (!(line.isAppend() || line.isEdited()
                        && SystemDefine.SCH_FLAG_NOT_SCHEDULE.equals(line.getValue(KEY_HIDDEN_SCHEDULE_FLAG))))
                {
                    continue;
                }
                // DFKLOOK:ここまで修正

                BatchStartCancelSCHParams lineparam = new BatchStartCancelSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(BatchStartCancelSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(BatchStartCancelSCHParams.BATCH_SEQ_NO, line.getValue(KEY_LST_BATCH_SEQ_NO));
                lineparam.set(BatchStartCancelSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                lineparam.set(BatchStartCancelSCHParams.ORDER_QTY, line.getValue(KEY_LST_ORDER_QTY));
                lineparam.set(BatchStartCancelSCHParams.LINE_NO, line.getValue(KEY_LST_LINE_NO));
                lineparam.set(BatchStartCancelSCHParams.LAST_UPDATE_DATE, line.getValue(KEY_LST_LAST_UPDATE_DATE));
                lineparam.set(BatchStartCancelSCHParams.STATUS, line.getValue(KEY_LST_STATUS));
                lineparam.set(BatchStartCancelSCHParams.STATUS_FLAG, line.getValue(KEY_HIDDEN_STATUS_FLAG));
                lineparam.set(BatchStartCancelSCHParams.REPORT_FLAG, line.getValue(KEY_HIDDEN_REPORT_FLAG));
                lineparam.set(BatchStartCancelSCHParams.SCHEDULE_FLAG, line.getValue(KEY_HIDDEN_SCHEDULE_FLAG));
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
                _lcm_lst_BatchStartCancel.resetEditRow();
                _lcm_lst_BatchStartCancel.resetHighlight();
                _lcm_lst_BatchStartCancel.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());
            
            // DFKLOOK:ここから修正
            // 統計情報の取得を行います。
            PCTRetWorkInfoHandler infoHandler = new PCTRetWorkInfoHandler(conn);
            PCTOrderInfoHandler orderHandler = new PCTOrderInfoHandler(conn);
            infoHandler.getStatics();
            orderHandler.getStatics();
            // DFKLOOK:ここまで修正
            // reset editing row.
            _lcm_lst_BatchStartCancel.resetEditRow();
            _lcm_lst_BatchStartCancel.resetHighlight();

            // set input parameters.
            BatchStartCancelSCHParams inparam = new BatchStartCancelSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_BatchStartCancel.clear();

            // DFKLOOK:ここから修正
            if (outparams.size() <= 0)
            {
                buttonEnabled(false);
                return;
            }
            else
            {
                buttonEnabled(true);
            }
            // DFKLOOK:ここまで修正

            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_BatchStartCancel.getNewLine();
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(BatchStartCancelSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(BatchStartCancelSCHParams.BATCH_NO));

                // DFKLOOK:ここから修正
                line.setValue(KEY_LST_ORDER_QTY,
                        Formatter.getNumFormat(outparam.getInt(BatchStartCancelSCHParams.ORDER_QTY)));
                line.setValue(KEY_LST_LINE_NO,
                        Formatter.getNumFormat(outparam.getInt(BatchStartCancelSCHParams.LINE_NO)));
                // DFKLOOK:ここまで修正

                line.setValue(KEY_LST_LAST_UPDATE_DATE, outparam.get(BatchStartCancelSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_STATUS, outparam.get(BatchStartCancelSCHParams.STATUS));
                line.setValue(KEY_HIDDEN_STATUS_FLAG, outparam.get(BatchStartCancelSCHParams.STATUS_FLAG));
                line.setValue(KEY_HIDDEN_REPORT_FLAG, outparam.get(BatchStartCancelSCHParams.REPORT_FLAG));
                line.setValue(KEY_LST_WEIGHT_UNREGISTERED, outparam.get(BatchStartCancelSCHParams.WEIGHT_UNREGISTERED));
                line.setValue(KEY_HIDDEN_NUMBERING_FLAG,
                        outparam.get(BatchStartCancelSCHParams.ORDER_NO_NUMBERING_FLAG));
                line.setValue(KEY_HIDDEN_SCHEDULE_FLAG, outparam.get(BatchStartCancelSCHParams.SCHEDULE_FLAG));
                line.setValue(KEY_HIDDEN_WEIGHT_FLAG, outparam.get(BatchStartCancelSCHParams.WEIGHT_FLAG));
                lst_BatchStartCancel_SetLineToolTip(line);
                _lcm_lst_BatchStartCancel.add(line);

                // DFKLOOK:ここから修正
                // PCTマスタがない場合重量未登録商品の列を非表示
                if (!viewState.getBooleanObject(ViewStateKeys.MASTER_FLAG))
                {
                    // リストセルから重量未登録商品の列を非表示
                    _lcm_lst_BatchStartCancel.getListCell().setColumnHidden(LST_WEIGH, true);
                }
                // オーダー数(eWareNaviでオーダーNo.を採番する場合は非表示とする)
                if (outparam.getBoolean(BatchStartCancelSCHParams.ORDER_NO_NUMBERING_FLAG))
                {
                    _lcm_lst_BatchStartCancel.getListCell().setColumnHidden(LST_ORDER, true);
                }
                // DFKLOOK:ここまで修正
            }

            // DFKLOOK:ここから修正
            // 商品一覧表示/非表示
            setItemListCell();
            // ボタンの初期化
            setbtnEnabele(false);
            // 一覧クリア
            btn_ListClear_Click_Process();
            // フォーカスセット
            setFocus(lst_BatchStartCancel);
            // DFKLOOK:ここまで修正
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
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
    private void btn_ReDisplayFunc_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        BatchStartCancelSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new BatchStartCancelSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            BatchStartCancelSCHParams inparam = new BatchStartCancelSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_BatchStartCancel.clear();

            // DFKLOOK:ここから修正
            if (outparams.size() <= 0)
            {
                buttonEnabled(false);
                return;
            }
            else
            {
                buttonEnabled(true);
            }
            // DFKLOOK:ここまで修正

            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_BatchStartCancel.getNewLine();
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(BatchStartCancelSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(BatchStartCancelSCHParams.BATCH_NO));

                // DFKLOOK:ここから修正
                line.setValue(KEY_LST_ORDER_QTY,
                        Formatter.getNumFormat(outparam.getInt(BatchStartCancelSCHParams.ORDER_QTY)));
                line.setValue(KEY_LST_LINE_NO,
                        Formatter.getNumFormat(outparam.getInt(BatchStartCancelSCHParams.LINE_NO)));
                // DFKLOOK:ここまで修正

                line.setValue(KEY_LST_LAST_UPDATE_DATE, outparam.get(BatchStartCancelSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_STATUS, outparam.get(BatchStartCancelSCHParams.STATUS));
                line.setValue(KEY_HIDDEN_STATUS_FLAG, outparam.get(BatchStartCancelSCHParams.STATUS_FLAG));
                line.setValue(KEY_HIDDEN_REPORT_FLAG, outparam.get(BatchStartCancelSCHParams.REPORT_FLAG));
                line.setValue(KEY_LST_WEIGHT_UNREGISTERED, outparam.get(BatchStartCancelSCHParams.WEIGHT_UNREGISTERED));
                line.setValue(KEY_HIDDEN_NUMBERING_FLAG,
                        outparam.get(BatchStartCancelSCHParams.ORDER_NO_NUMBERING_FLAG));
                line.setValue(KEY_HIDDEN_SCHEDULE_FLAG, outparam.get(BatchStartCancelSCHParams.SCHEDULE_FLAG));
                line.setValue(KEY_HIDDEN_WEIGHT_FLAG, outparam.get(BatchStartCancelSCHParams.WEIGHT_FLAG));
                lst_BatchStartCancel_SetLineToolTip(line);
                _lcm_lst_BatchStartCancel.add(line);

                // DFKLOOK:ここから修正
                // PCTマスタがない場合重量未登録商品の列を非表示
                if (!viewState.getBooleanObject(ViewStateKeys.MASTER_FLAG))
                {
                    // リストセルから重量未登録商品の列を非表示
                    _lcm_lst_BatchStartCancel.getListCell().setColumnHidden(LST_WEIGH, true);
                }
                // オーダー数(eWareNaviでオーダーNo.を採番する場合は非表示とする)
                if (outparam.getBoolean(BatchStartCancelSCHParams.ORDER_NO_NUMBERING_FLAG))
                {
                    _lcm_lst_BatchStartCancel.getListCell().setColumnHidden(LST_ORDER, true);
                }
                // DFKLOOK:ここまで修正
            }

            // DFKLOOK:ここから修正
            message.setMsgResourceKey("6001013");

            // 商品一覧表示/非表示
            setItemListCell();
            // ボタンの初期化
            setbtnEnabele(false);
            // 一覧クリア
            btn_ListClear_Click_Process();
            // フォーカスセット
            setFocus(lst_BatchStartCancel);
            // DFKLOOK:ここまで修正

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
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
    private void btn_WorkCancel_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_BatchStartCancel.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_BatchStartCancel.get(i);
            if ((checkline.isAppend() || checkline.isEdited())
                    && SystemDefine.SCH_FLAG_SCHEDULE.equals(checkline.getValue(KEY_HIDDEN_SCHEDULE_FLAG)))
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003110");
            // 商品一覧表示/非表示
            setItemListCell();

            // フォーカスセット
            setFocus(lst_BatchStartCancel);

            return;
        }

        if (StringUtil.isBlank(eventSource))
        {         
            // キャンセルしますか?
            this.setConfirm("MSG-P0001", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_WorkCancel_Click");
            return;         
        }
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        BatchStartCancelSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new BatchStartCancelSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_BatchStartCancel.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_BatchStartCancel.get(i);

                // DFKLOOK:ここから修正
                if (!(line.isAppend() || line.isEdited()
                        && SystemDefine.SCH_FLAG_SCHEDULE.equals(line.getValue(KEY_HIDDEN_SCHEDULE_FLAG))))
                {
                    continue;
                }
                // DFKLOOK:ここまで修正

                BatchStartCancelSCHParams lineparam = new BatchStartCancelSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(BatchStartCancelSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(BatchStartCancelSCHParams.BATCH_SEQ_NO, line.getValue(KEY_LST_BATCH_SEQ_NO));
                lineparam.set(BatchStartCancelSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                lineparam.set(BatchStartCancelSCHParams.ORDER_QTY, line.getValue(KEY_LST_ORDER_QTY));
                lineparam.set(BatchStartCancelSCHParams.LINE_NO, line.getValue(KEY_LST_LINE_NO));
                lineparam.set(BatchStartCancelSCHParams.LAST_UPDATE_DATE, line.getValue(KEY_LST_LAST_UPDATE_DATE));
                lineparam.set(BatchStartCancelSCHParams.STATUS, line.getValue(KEY_LST_STATUS));
                lineparam.set(BatchStartCancelSCHParams.STATUS_FLAG, line.getValue(KEY_HIDDEN_STATUS_FLAG));
                lineparam.set(BatchStartCancelSCHParams.REPORT_FLAG, line.getValue(KEY_HIDDEN_REPORT_FLAG));
                lineparam.set(BatchStartCancelSCHParams.SCHEDULE_FLAG, line.getValue(KEY_HIDDEN_SCHEDULE_FLAG));
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
                _lcm_lst_BatchStartCancel.resetEditRow();
                _lcm_lst_BatchStartCancel.resetHighlight();
                _lcm_lst_BatchStartCancel.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_BatchStartCancel.resetEditRow();
            _lcm_lst_BatchStartCancel.resetHighlight();

            // set input parameters.
            BatchStartCancelSCHParams inparam = new BatchStartCancelSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_BatchStartCancel.clear();

            // DFKLOOK:ここから修正
            if (outparams.size() <= 0)
            {
                buttonEnabled(false);
                return;
            }
            else
            {
                buttonEnabled(true);
            }
            // DFKLOOK:ここまで修正

            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_BatchStartCancel.getNewLine();
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(BatchStartCancelSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(BatchStartCancelSCHParams.BATCH_NO));

                // DFKLOOK:ここから修正
                line.setValue(KEY_LST_ORDER_QTY,
                        Formatter.getNumFormat(outparam.getInt(BatchStartCancelSCHParams.ORDER_QTY)));
                line.setValue(KEY_LST_LINE_NO,
                        Formatter.getNumFormat(outparam.getInt(BatchStartCancelSCHParams.LINE_NO)));
                // DFKLOOK:ここまで修正

                line.setValue(KEY_LST_LAST_UPDATE_DATE, outparam.get(BatchStartCancelSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_STATUS, outparam.get(BatchStartCancelSCHParams.STATUS));
                line.setValue(KEY_HIDDEN_STATUS_FLAG, outparam.get(BatchStartCancelSCHParams.STATUS_FLAG));
                line.setValue(KEY_HIDDEN_REPORT_FLAG, outparam.get(BatchStartCancelSCHParams.REPORT_FLAG));
                line.setValue(KEY_LST_WEIGHT_UNREGISTERED, outparam.get(BatchStartCancelSCHParams.WEIGHT_UNREGISTERED));
                line.setValue(KEY_HIDDEN_NUMBERING_FLAG,
                        outparam.get(BatchStartCancelSCHParams.ORDER_NO_NUMBERING_FLAG));
                line.setValue(KEY_HIDDEN_SCHEDULE_FLAG, outparam.get(BatchStartCancelSCHParams.SCHEDULE_FLAG));
                line.setValue(KEY_HIDDEN_WEIGHT_FLAG, outparam.get(BatchStartCancelSCHParams.WEIGHT_FLAG));
                lst_BatchStartCancel_SetLineToolTip(line);
                _lcm_lst_BatchStartCancel.add(line);

                // DFKLOOK:ここから修正
                // PCTマスタがない場合重量未登録商品の列を非表示
                if (!viewState.getBooleanObject(ViewStateKeys.MASTER_FLAG))
                {
                    // リストセルから重量未登録商品の列を非表示
                    _lcm_lst_BatchStartCancel.getListCell().setColumnHidden(LST_WEIGH, true);
                }
                // オーダー数(eWareNaviでオーダーNo.を採番する場合は非表示とする)
                if (outparam.getBoolean(BatchStartCancelSCHParams.ORDER_NO_NUMBERING_FLAG))
                {
                    _lcm_lst_BatchStartCancel.getListCell().setColumnHidden(LST_ORDER, true);
                }
                // DFKLOOK:ここまで修正
            }

            // DFKLOOK:ここから修正
            // 商品一覧表示/非表示
            setItemListCell();
            // ボタンの初期化
            setbtnEnabele(false);
            // 一覧クリア
            btn_ListClear_Click_Process();
            // フォーカスセット
            setFocus(lst_BatchStartCancel);
            // DFKLOOK:ここまで修正
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
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
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_BatchStartCancel.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_BatchStartCancel.get(i);
            lst_BatchStartCancel.setCurrentRow(i);
            clearline.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_BatchStartCancel_SetLineToolTip(clearline);
            _lcm_lst_BatchStartCancel.set(i, clearline);
        }

        // DFKLOOK:ここから修正
        // 商品一覧表示/非表示
        setItemListCell();

        // フォーカスセット
        setFocus(lst_BatchStartCancel);
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheckClear_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_BatchStartCancel.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_BatchStartCancel.get(i);
            lst_BatchStartCancel.setCurrentRow(i);
            clearline.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_BatchStartCancel_SetLineToolTip(clearline);
            _lcm_lst_BatchStartCancel.set(i, clearline);
        }

        // DFKLOOK:ここから修正
        // 商品一覧表示/非表示
        setItemListCell();

        // フォーカスセット
        setFocus(lst_BatchStartCancel);
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_UnRegistItem_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_BatchStartCancel.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_BatchStartCancel.get(i);
            if ((checkline.isAppend() || checkline.isEdited()))
            {
                if (Boolean.valueOf((checkline.getStringValue(KEY_HIDDEN_WEIGHT_FLAG))))
                {
                    existEditedRow = true;
                    break;
                }
            }
        }
        // フォーカスセット
        setFocus(lst_BatchStartCancel);

        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003110");

            btn_ListClear_Click_Process();

            return;
        }
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        BatchStartCancelDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new BatchStartCancelDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // DFKLOOK:ここから修正
            // バッチNo.
            List<String> batchList = new ArrayList<String>();
            // バッチSeqNo.
            List<String> batchSeqList = new ArrayList<String>();

            for (int i = 1; i <= _lcm_lst_BatchStartCancel.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_BatchStartCancel.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }
                if (!Boolean.valueOf((line.getStringValue(KEY_HIDDEN_WEIGHT_FLAG))))
                {
                    continue;
                }

                BatchStartCancelSCHParams lineparam = new BatchStartCancelSCHParams();
                lineparam.setRowIndex(i);

                batchList.add(line.getStringValue(KEY_LST_BATCH_NO));
                batchSeqList.add(line.getStringValue(KEY_LST_BATCH_SEQ_NO));
            }
            // DFKLOOK:ここまで修正

            // set input parameters.
            BatchStartCancelDASCHParams inparam = new BatchStartCancelDASCHParams();

            // DFKLOOK:ここから修正
            inparam.set(BatchStartCancelDASCHParams.BATCH_NO, batchList);
            inparam.set(BatchStartCancelDASCHParams.BATCH_SEQ_NO, batchSeqList);
            this.getViewState().setObject(ViewStateKeys.VS_BATCH_NO, batchList);
            this.getViewState().setObject(ViewStateKeys.VS_BATCH_SEQ_NO, batchSeqList);
            // DFKLOOK：ここまで修正

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_PCTUnRegistItemList.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }
            else if (count > _pager.getMax())
            {
                message.setMsgResourceKey("6001023\t" + Formatter.getNumFormat(count) + "\t"
                        + Formatter.getNumFormat(_pager.getMax()));
            }
            else
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTUnRegistItemList.getNewLine();
                line.setValue(KEY_LST_CONSIGNOR_CODE, outparam.get(BatchStartCancelDASCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_LST_CONSIGNOR_NAME, outparam.get(BatchStartCancelDASCHParams.CONSIGNOR_NAME));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(BatchStartCancelDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(BatchStartCancelDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(BatchStartCancelDASCHParams.ENTERING_QTY));
                line.setValue(KEY_HIDDEN_JAN, outparam.get(BatchStartCancelDASCHParams.JAN));
                line.setValue(KEY_HIDDEN_ITF, outparam.get(BatchStartCancelDASCHParams.ITF));
                line.setValue(KEY_HIDDEN_LOCATION_NO, outparam.get(BatchStartCancelDASCHParams.LOCATION_NO));
                line.setValue(KEY_HIDDEN_SINGLE_WEIGHT, outparam.get(BatchStartCancelDASCHParams.SINGLE_WEIGHT));
                line.setValue(KEY_HIDDEN_WEGHT_DISTINCT_RATE,
                        outparam.get(BatchStartCancelDASCHParams.WEGHT_DISTINCT_RATE));
                line.setValue(KEY_HIDDEN_MAX_INSPECTION_UNIT_QTY,
                        outparam.get(BatchStartCancelDASCHParams.MAX_INSPECTION_UNIT_QTY));
                line.setValue(KEY_HIDDEN_LAST_UPDATE, outparam.get(BatchStartCancelDASCHParams.LAST_UPDATE));
                line.setValue(KEY_HIDDEN_WORK_DAY, outparam.get(BatchStartCancelDASCHParams.WORK_DAY));
                line.setValue(KEY_HIDDEN_ITEM_PICTURE_FLAG, outparam.get(BatchStartCancelDASCHParams.ITEM_PICTURE_FLAG));
                line.setValue(KEY_HIDDEN_MESSAGE1, outparam.get(BatchStartCancelDASCHParams.MESSAGE1));
                lst_PCTUnRegistItemList_SetLineToolTip(line);
                _lcm_lst_PCTUnRegistItemList.add(line);
            }

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;

            // DFKLOOK:ここから修正
            setbtnEnabele(true);
            // DFKLOOK:ここまで修正
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_PCTUnRegistItemList.clear();
        }
        finally
        {
            if (!isSuccess)
            {
                if (dasch != null)
                {
                    dasch.close();
                }
                DBUtil.close(conn);
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_UnRegistItem_Click_SetList()
            throws Exception
    {
        BatchStartCancelDASCH dasch = null;
        try
        {
            // get session.
            dasch = (BatchStartCancelDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_PCTUnRegistItemList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTUnRegistItemList.getNewLine();
                line.setValue(KEY_LST_CONSIGNOR_CODE, outparam.get(BatchStartCancelDASCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_LST_CONSIGNOR_NAME, outparam.get(BatchStartCancelDASCHParams.CONSIGNOR_NAME));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(BatchStartCancelDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(BatchStartCancelDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(BatchStartCancelDASCHParams.ENTERING_QTY));
                line.setValue(KEY_HIDDEN_JAN, outparam.get(BatchStartCancelDASCHParams.JAN));
                line.setValue(KEY_HIDDEN_ITF, outparam.get(BatchStartCancelDASCHParams.ITF));
                line.setValue(KEY_HIDDEN_LOCATION_NO, outparam.get(BatchStartCancelDASCHParams.LOCATION_NO));
                line.setValue(KEY_HIDDEN_SINGLE_WEIGHT, outparam.get(BatchStartCancelDASCHParams.SINGLE_WEIGHT));
                line.setValue(KEY_HIDDEN_WEGHT_DISTINCT_RATE,
                        outparam.get(BatchStartCancelDASCHParams.WEGHT_DISTINCT_RATE));
                line.setValue(KEY_HIDDEN_MAX_INSPECTION_UNIT_QTY,
                        outparam.get(BatchStartCancelDASCHParams.MAX_INSPECTION_UNIT_QTY));
                line.setValue(KEY_HIDDEN_LAST_UPDATE, outparam.get(BatchStartCancelDASCHParams.LAST_UPDATE));
                line.setValue(KEY_HIDDEN_WORK_DAY, outparam.get(BatchStartCancelDASCHParams.WORK_DAY));
                line.setValue(KEY_HIDDEN_ITEM_PICTURE_FLAG, outparam.get(BatchStartCancelDASCHParams.ITEM_PICTURE_FLAG));
                line.setValue(KEY_HIDDEN_MESSAGE1, outparam.get(BatchStartCancelDASCHParams.MESSAGE1));
                lst_PCTUnRegistItemList_SetLineToolTip(line);
                _lcm_lst_PCTUnRegistItemList.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_PCTUnRegistItemList.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void disposeDasch()
            throws Exception
    {
        // disposing DASCH.
        DataAccessSCH dasch = (DataAccessSCH)session.getAttribute(_KEY_DASCH);
        if (dasch != null)
        {
            session.removeAttribute(_KEY_DASCH);
            dasch.close();
            DBUtil.close(dasch.getConnection());
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Preview_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        PrintExporter exporter = null;
        try
        {
            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPVExporter("PctItemMasterList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTUnRegistItemList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTUnRegistItemList.get(i);
                PctItemMasterListParams expparam = new PctItemMasterListParams();
                expparam.set(PctItemMasterListParams.CONSIGNOR_CODE, line.getValue(KEY_LST_CONSIGNOR_CODE));
                expparam.set(PctItemMasterListParams.CONSIGNOR_NAME, line.getValue(KEY_LST_CONSIGNOR_NAME));

                // DFKLOOK:ここから修正
                expparam.set(PctItemMasterListParams.SYS_DAY, DbDateUtil.getTimeStamp());
                expparam.set(PctItemMasterListParams.SYS_TIME, DbDateUtil.getTimeStamp());
                // DFKLOOK:ここまで修正

                expparam.set(PctItemMasterListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctItemMasterListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctItemMasterListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                expparam.set(PctItemMasterListParams.JAN, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PctItemMasterListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PctItemMasterListParams.LOCATION_NO, line.getValue(KEY_HIDDEN_LOCATION_NO));
                expparam.set(PctItemMasterListParams.SINGLE_WEIGHT, line.getValue(KEY_HIDDEN_SINGLE_WEIGHT));
                expparam.set(PctItemMasterListParams.WEGHT_DISTINCT_RATE, line.getValue(KEY_HIDDEN_WEGHT_DISTINCT_RATE));
                expparam.set(PctItemMasterListParams.MAX_INSPECTION_UNIT_QTY, line.getValue(KEY_HIDDEN_MAX_INSPECTION_UNIT_QTY));

                // DFKLOOK:ここから修正
                expparam.set(PctItemMasterListParams.LAST_UPDATE,
                        WmsFormatter.toDate(line.getStringValue(KEY_HIDDEN_LAST_UPDATE)));
                expparam.set(PctItemMasterListParams.WORK_DAY, WmsFormatter.toDate(line.getStringValue(KEY_HIDDEN_WORK_DAY)));
                expparam.set(
                		PctItemMasterListParams.ITEM_PICTURE_FLAG,
                        DisplayResource.getWeightFlag(Boolean.valueOf(line.getStringValue(KEY_HIDDEN_ITEM_PICTURE_FLAG))));
                // DFKLOOK:ここまで修正

                expparam.set(PctItemMasterListParams.MESSAGE1, line.getValue(KEY_HIDDEN_MESSAGE1));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                downloadFile = exporter.print();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey("6007034");
            }

            // redirect.
            previewPDF(downloadFile.getPath());

            // DFKLOOK start
            setFocus(null);
            // DFKLOOK end
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (exporter != null)
            {
                exporter.close();
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Print_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        PrintExporter exporter = null;
        try
        {
            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("PctItemMasterList", false);
            exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTUnRegistItemList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTUnRegistItemList.get(i);
                PctItemMasterListParams expparam = new PctItemMasterListParams();
                expparam.set(PctItemMasterListParams.CONSIGNOR_CODE, line.getValue(KEY_LST_CONSIGNOR_CODE));
                expparam.set(PctItemMasterListParams.CONSIGNOR_NAME, line.getValue(KEY_LST_CONSIGNOR_NAME));

                // DFKLOOK:ここから修正
                expparam.set(PctItemMasterListParams.SYS_DAY, DbDateUtil.getTimeStamp());
                expparam.set(PctItemMasterListParams.SYS_TIME, DbDateUtil.getTimeStamp());
                // DFKLOOK:ここまで修正

                expparam.set(PctItemMasterListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctItemMasterListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctItemMasterListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                expparam.set(PctItemMasterListParams.JAN, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PctItemMasterListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PctItemMasterListParams.LOCATION_NO, line.getValue(KEY_HIDDEN_LOCATION_NO));
                expparam.set(PctItemMasterListParams.SINGLE_WEIGHT, line.getValue(KEY_HIDDEN_SINGLE_WEIGHT));
                expparam.set(PctItemMasterListParams.WEGHT_DISTINCT_RATE, line.getValue(KEY_HIDDEN_WEGHT_DISTINCT_RATE));
                expparam.set(PctItemMasterListParams.MAX_INSPECTION_UNIT_QTY, line.getValue(KEY_HIDDEN_MAX_INSPECTION_UNIT_QTY));

                // DFKLOOK:ここから修正
                expparam.set(PctItemMasterListParams.LAST_UPDATE,
                        WmsFormatter.toDate(line.getStringValue(KEY_HIDDEN_LAST_UPDATE)));
                expparam.set(PctItemMasterListParams.WORK_DAY, WmsFormatter.toDate(line.getStringValue(KEY_HIDDEN_WORK_DAY)));
                expparam.set(
                		PctItemMasterListParams.ITEM_PICTURE_FLAG,
                        DisplayResource.getWeightFlag(Boolean.valueOf(line.getStringValue(KEY_HIDDEN_ITEM_PICTURE_FLAG))));
                // DFKLOOK:ここまで修正

                expparam.set(PctItemMasterListParams.MESSAGE1, line.getValue(KEY_HIDDEN_MESSAGE1));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                exporter.print();
                message.setMsgResourceKey("6001010");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey("6007034");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (exporter != null)
            {
                exporter.close();
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_XLS_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Exporter exporter = null;
        try
        {
            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newExcelExporter("PctItemMasterList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTUnRegistItemList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTUnRegistItemList.get(i);
                PctItemMasterListParams expparam = new PctItemMasterListParams();
                expparam.set(PctItemMasterListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctItemMasterListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctItemMasterListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                expparam.set(PctItemMasterListParams.JAN, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PctItemMasterListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PctItemMasterListParams.LOCATION_NO, line.getValue(KEY_HIDDEN_LOCATION_NO));
                expparam.set(PctItemMasterListParams.SINGLE_WEIGHT, line.getValue(KEY_HIDDEN_SINGLE_WEIGHT));
                expparam.set(PctItemMasterListParams.WEGHT_DISTINCT_RATE, line.getValue(KEY_HIDDEN_WEGHT_DISTINCT_RATE));
                expparam.set(PctItemMasterListParams.MAX_INSPECTION_UNIT_QTY,
                        line.getValue(KEY_HIDDEN_MAX_INSPECTION_UNIT_QTY));

                // DFKLOOK:ここから修正
                expparam.set(PctItemMasterListParams.LAST_UPDATE,
                        WmsFormatter.toDate(line.getStringValue(KEY_HIDDEN_LAST_UPDATE)));
                expparam.set(PctItemMasterListParams.WORK_DAY,
                        WmsFormatter.toDate(line.getStringValue(KEY_HIDDEN_WORK_DAY)));
                expparam.set(
                		PctItemMasterListParams.ITEM_PICTURE_FLAG,
                        DisplayResource.getWeightFlag(Boolean.valueOf(line.getStringValue(KEY_HIDDEN_ITEM_PICTURE_FLAG))));
                // DFKLOOK:ここまで修正

                expparam.set(PctItemMasterListParams.MESSAGE1, line.getValue(KEY_HIDDEN_MESSAGE1));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // redirect.
            download(downloadFile.getPath());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (exporter != null)
            {
                exporter.close();
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ListClear_Click_Process()
            throws Exception
    {
        // clear.
        _pager.clear();
        btn_Preview.setEnabled(false);
        btn_Print.setEnabled(false);
        btn_XLS.setEnabled(false);
        btn_ListClear.setEnabled(false);
        _pager.clear();
        _lcm_lst_PCTUnRegistItemList.clear();

        // DFKLOOK:ここから修正
        // フォーカスセット
        setFocus(lst_BatchStartCancel);
        // DFKLOOK:ここまで修正
    }

    // DFKLOOK:ここから修正
    /**
     * 出荷先一覧一覧ボタン押下
     * 
     */
    private void lst_PCTUnRegistItemList_Process()
            throws Exception
    {
        // get active row.
        int row = lst_PCTUnRegistItemList.getActiveRow();
        lst_PCTUnRegistItemList.setCurrentRow(row);
        ListCellLine line = _lcm_lst_PCTUnRegistItemList.get(row);

        // dialog parameters set.
        BatchStartCancelDASCHParams inparam = new BatchStartCancelDASCHParams();

        inparam.set(BatchStartCancelDASCHParams.CONSIGNOR_CODE, line.getValue(KEY_LST_CONSIGNOR_CODE));
        inparam.set(BatchStartCancelDASCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
        inparam.set(BatchStartCancelDASCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
        inparam.set(BatchStartCancelDASCHParams.BATCH_NO, this.getViewState().getObject(ViewStateKeys.VS_BATCH_NO));
        inparam.set(BatchStartCancelDASCHParams.BATCH_SEQ_NO, this.getViewState().getObject(
                ViewStateKeys.VS_BATCH_SEQ_NO));

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "lst_PCTUnRegistItemList_ColumClick");
        redirect("/pcart/retrieval/listbox/customernobtn/LstCustomerNoBtn.do", forwardParam, "/Progress.do");
    }

    /**
     * 商品一覧リストセル表示・非表示設定
     * 
     */
    private void setItemListCell()
    {
        // 商品一覧表示/非表示
        if (!viewState.getBooleanObject(ViewStateKeys.MASTER_FLAG))
        {
            btn_UnRegistItem.setVisible(false);

            pager_up.setVisible(false);
            pager_down.setVisible(false);
            btn_Preview.setVisible(false);
            btn_Print.setVisible(false);
            btn_XLS.setVisible(false);
            btn_ListClear.setVisible(false);
            lst_PCTUnRegistItemList.setHeaderStatus(false);

        }
    }

    /**
     * ボタンの使用可/使用不可を設定します。
     *
     * @param arg 
     */
    private void buttonEnabled(boolean arg)
    {
        btn_Start.setEnabled(arg);
        btn_WorkCancel.setEnabled(arg);
        btn_AllCheck.setEnabled(arg);
        btn_AllCheckClear.setEnabled(arg);
        btn_UnRegistItem.setEnabled(arg);
    }

    /**
     * ボタンを使用不可に設定します。
     */
    private void setbtnEnabele(boolean arg)
    {
        btn_Preview.setEnabled(arg);
        btn_Print.setEnabled(arg);
        btn_XLS.setEnabled(arg);
        btn_ListClear.setEnabled(arg);
    }

    // DFKLOOK:ここまで修正

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

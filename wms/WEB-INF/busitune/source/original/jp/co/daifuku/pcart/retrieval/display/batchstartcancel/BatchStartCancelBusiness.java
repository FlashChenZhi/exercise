// $Id: BatchStartCancelBusiness.java 5329 2009-10-29 05:35:28Z shibamoto $
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
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.bluedog.webapp.ViewState;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.retrieval.dasch.BatchStartCancelDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.BatchStartCancelDASCHParams;
import jp.co.daifuku.pcart.retrieval.display.batchstartcancel.BatchStartCancel;
import jp.co.daifuku.pcart.retrieval.display.batchstartcancel.ViewStateKeys;
import jp.co.daifuku.pcart.retrieval.exporter.PCTItemMasterListParams;
import jp.co.daifuku.pcart.retrieval.schedule.BatchStartCancelSCH;
import jp.co.daifuku.pcart.retrieval.schedule.BatchStartCancelSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5329 $, $Date:: 2009-10-29 14:35:28 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class BatchStartCancelBusiness
        extends BatchStartCancel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_BatchStartCancel(HIDDEN_STATUS_FLAG) */
    private static final ListCellKey KEY_HIDDEN_STATUS_FLAG = new ListCellKey("HIDDEN_STATUS_FLAG", new StringCellColumn(), false, false);

    /** lst_BatchStartCancel(HIDDEN_REPORT_FLAG) */
    private static final ListCellKey KEY_HIDDEN_REPORT_FLAG = new ListCellKey("HIDDEN_REPORT_FLAG", new StringCellColumn(), false, false);

    /** lst_BatchStartCancel(HIDDEN_NUMBERING_FLAG) */
    private static final ListCellKey KEY_HIDDEN_NUMBERING_FLAG = new ListCellKey("HIDDEN_NUMBERING_FLAG", new StringCellColumn(), false, false);

    /** lst_BatchStartCancel(HIDDEN_SCHEDULE_FLAG) */
    private static final ListCellKey KEY_HIDDEN_SCHEDULE_FLAG = new ListCellKey("HIDDEN_SCHEDULE_FLAG", new StringCellColumn(), false, false);

    /** lst_BatchStartCancel(HIDDEN_WEIGHT_FLAG) */
    private static final ListCellKey KEY_HIDDEN_WEIGHT_FLAG = new ListCellKey("HIDDEN_WEIGHT_FLAG", new StringCellColumn(), false, false);

    /** lst_BatchStartCancel(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_BatchStartCancel(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_BatchStartCancel(LST_BATCH_SEQ_NO) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO = new ListCellKey("LST_BATCH_SEQ_NO", new StringCellColumn(), true, false);

    /** lst_BatchStartCancel(LST_ORDER_QTY) */
    private static final ListCellKey KEY_LST_ORDER_QTY = new ListCellKey("LST_ORDER_QTY", new StringCellColumn(), true, false);

    /** lst_BatchStartCancel(LST_LINE_NO) */
    private static final ListCellKey KEY_LST_LINE_NO = new ListCellKey("LST_LINE_NO", new StringCellColumn(), true, false);

    /** lst_BatchStartCancel(LST_LAST_UPDATE_DATE) */
    private static final ListCellKey KEY_LST_LAST_UPDATE_DATE = new ListCellKey("LST_LAST_UPDATE_DATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), true, false);

    /** lst_BatchStartCancel(LST_STATUS) */
    private static final ListCellKey KEY_LST_STATUS = new ListCellKey("LST_STATUS", new StringCellColumn(), true, false);

    /** lst_BatchStartCancel(LST_WEIGHT_UNREGISTERED) */
    private static final ListCellKey KEY_LST_WEIGHT_UNREGISTERED = new ListCellKey("LST_WEIGHT_UNREGISTERED", new StringCellColumn(), true, false);

    /** lst_PCTUnRegistItemList(HIDDEN_JAN) */
    private static final ListCellKey KEY_HIDDEN_JAN = new ListCellKey("HIDDEN_JAN", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_ITF) */
    private static final ListCellKey KEY_HIDDEN_ITF = new ListCellKey("HIDDEN_ITF", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_LOCATION_NO) */
    private static final ListCellKey KEY_HIDDEN_LOCATION_NO = new ListCellKey("HIDDEN_LOCATION_NO", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_SINGLE_WEIGHT) */
    private static final ListCellKey KEY_HIDDEN_SINGLE_WEIGHT = new ListCellKey("HIDDEN_SINGLE_WEIGHT", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_WEGHT_DISTINCT_RATE) */
    private static final ListCellKey KEY_HIDDEN_WEGHT_DISTINCT_RATE = new ListCellKey("HIDDEN_WEGHT_DISTINCT_RATE", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_MAX_INSPECTION_UNIT_QTY) */
    private static final ListCellKey KEY_HIDDEN_MAX_INSPECTION_UNIT_QTY = new ListCellKey("HIDDEN_MAX_INSPECTION_UNIT_QTY", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_LAST_UPDATE) */
    private static final ListCellKey KEY_HIDDEN_LAST_UPDATE = new ListCellKey("HIDDEN_LAST_UPDATE", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_WORK_DAY) */
    private static final ListCellKey KEY_HIDDEN_WORK_DAY = new ListCellKey("HIDDEN_WORK_DAY", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_ITEM_PICTURE_FLAG) */
    private static final ListCellKey KEY_HIDDEN_ITEM_PICTURE_FLAG = new ListCellKey("HIDDEN_ITEM_PICTURE_FLAG", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(HIDDEN_MESSAGE1) */
    private static final ListCellKey KEY_HIDDEN_MESSAGE1 = new ListCellKey("HIDDEN_MESSAGE1", new StringCellColumn(), false, false);

    /** lst_PCTUnRegistItemList(LST_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_LST_CONSIGNOR_CODE = new ListCellKey("LST_CONSIGNOR_CODE", new StringCellColumn(), true, false);

    /** lst_PCTUnRegistItemList(LST_CONSIGNOR_NAME) */
    private static final ListCellKey KEY_LST_CONSIGNOR_NAME = new ListCellKey("LST_CONSIGNOR_NAME", new StringCellColumn(), true, false);

    /** lst_PCTUnRegistItemList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_PCTUnRegistItemList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_PCTUnRegistItemList(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTUnRegistItemList(LST_CUSTOMER_LIST) */
    private static final ListCellKey KEY_LST_CUSTOMER_LIST = new ListCellKey("LST_CUSTOMER_LIST", new StringCellColumn(), true, false);

    /** lst_BatchStartCancel keys */
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

    /** lst_PCTUnRegistItemList keys */
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

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_BatchStartCancel */
    private ScrollListCellModel _lcm_lst_BatchStartCancel;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_PCTUnRegistItemList */
    private ScrollListCellModel _lcm_lst_PCTUnRegistItemList;

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
        // initialize components.
        initializeComponents();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Start_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Start_Click_Process();
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
        btn_WorkCancel_Click_Process();
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
        pager_SetPage();
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
        pager_SetPage();
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
        pager_SetPage();
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
        pager_SetPage();
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
    public void pager_down_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        pager_SetPage();
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
        pager_SetPage();
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
        pager_SetPage();
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
        pager_SetPage();
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
        _lcm_lst_BatchStartCancel = new ScrollListCellModel(lst_BatchStartCancel, LST_BATCHSTARTCANCEL_KEYS, locale);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_BATCH_NO, false);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_BATCH_SEQ_NO, false);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_ORDER_QTY, false);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_LINE_NO, false);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_LAST_UPDATE_DATE, false);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_STATUS, false);
        _lcm_lst_BatchStartCancel.setToolTipVisible(KEY_LST_WEIGHT_UNREGISTERED, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pager_up, pager_down}, locale);

        // initialize lst_PCTUnRegistItemList.
        _lcm_lst_PCTUnRegistItemList = new ScrollListCellModel(lst_PCTUnRegistItemList, LST_PCTUNREGISTITEMLIST_KEYS, locale);
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
     * @throws Exception
     */
    private void dispose()
            throws Exception
    {
        // dispose DASCH.
        disposeDasch();
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
    private void pager_SetPage()
            throws Exception
    {
        // get event source.
        String eventSource = viewState.getString(_KEY_PAGERSOURCE);
        if (eventSource == null)
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_UnRegistItem_Click"))
        {
            // process call.
            btn_UnRegistItem_Click_SetList();
        }
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

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_BatchStartCancel.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_BatchStartCancel.getNewLine();
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(BatchStartCancelSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(BatchStartCancelSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_QTY, outparam.get(BatchStartCancelSCHParams.ORDER_QTY));
                line.setValue(KEY_LST_LINE_NO, outparam.get(BatchStartCancelSCHParams.LINE_NO));
                line.setValue(KEY_LST_LAST_UPDATE_DATE, outparam.get(BatchStartCancelSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_STATUS, outparam.get(BatchStartCancelSCHParams.STATUS));
                line.setValue(KEY_HIDDEN_STATUS_FLAG, outparam.get(BatchStartCancelSCHParams.STATUS_FLAG));
                line.setValue(KEY_HIDDEN_REPORT_FLAG, outparam.get(BatchStartCancelSCHParams.REPORT_FLAG));
                line.setValue(KEY_LST_WEIGHT_UNREGISTERED, outparam.get(BatchStartCancelSCHParams.WEIGHT_UNREGISTERED));
                line.setValue(KEY_HIDDEN_NUMBERING_FLAG, outparam.get(BatchStartCancelSCHParams.ORDER_NO_NUMBERING_FLAG));
                line.setValue(KEY_HIDDEN_SCHEDULE_FLAG, outparam.get(BatchStartCancelSCHParams.SCHEDULE_FLAG));
                line.setValue(KEY_HIDDEN_WEIGHT_FLAG, outparam.get(BatchStartCancelSCHParams.WEIGHT_FLAG));
                viewState.setObject(ViewStateKeys.MASTER_FLAG, outparam.get(BatchStartCancelSCHParams.MASTER_FLAG));
                lst_BatchStartCancel_SetLineToolTip(line);
                _lcm_lst_BatchStartCancel.add(line);
            }

            // clear.
            btn_Preview.setEnabled(true);
            btn_Print.setEnabled(true);
            btn_XLS.setEnabled(true);
            btn_ListClear.setEnabled(true);
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
    private void btn_Start_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_BatchStartCancel.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_BatchStartCancel.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }

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
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

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

                // reset editing row or highlighting error row.
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
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_BatchStartCancel.getNewLine();
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(BatchStartCancelSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(BatchStartCancelSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_QTY, outparam.get(BatchStartCancelSCHParams.ORDER_QTY));
                line.setValue(KEY_LST_LINE_NO, outparam.get(BatchStartCancelSCHParams.LINE_NO));
                line.setValue(KEY_LST_LAST_UPDATE_DATE, outparam.get(BatchStartCancelSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_STATUS, outparam.get(BatchStartCancelSCHParams.STATUS));
                line.setValue(KEY_HIDDEN_STATUS_FLAG, outparam.get(BatchStartCancelSCHParams.STATUS_FLAG));
                line.setValue(KEY_HIDDEN_REPORT_FLAG, outparam.get(BatchStartCancelSCHParams.REPORT_FLAG));
                line.setValue(KEY_LST_WEIGHT_UNREGISTERED, outparam.get(BatchStartCancelSCHParams.WEIGHT_UNREGISTERED));
                line.setValue(KEY_HIDDEN_NUMBERING_FLAG, outparam.get(BatchStartCancelSCHParams.ORDER_NO_NUMBERING_FLAG));
                line.setValue(KEY_HIDDEN_SCHEDULE_FLAG, outparam.get(BatchStartCancelSCHParams.SCHEDULE_FLAG));
                line.setValue(KEY_HIDDEN_WEIGHT_FLAG, outparam.get(BatchStartCancelSCHParams.WEIGHT_FLAG));
                lst_BatchStartCancel_SetLineToolTip(line);
                _lcm_lst_BatchStartCancel.add(line);
            }
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
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_BatchStartCancel.getNewLine();
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(BatchStartCancelSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(BatchStartCancelSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_QTY, outparam.get(BatchStartCancelSCHParams.ORDER_QTY));
                line.setValue(KEY_LST_LINE_NO, outparam.get(BatchStartCancelSCHParams.LINE_NO));
                line.setValue(KEY_LST_LAST_UPDATE_DATE, outparam.get(BatchStartCancelSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_STATUS, outparam.get(BatchStartCancelSCHParams.STATUS));
                line.setValue(KEY_HIDDEN_STATUS_FLAG, outparam.get(BatchStartCancelSCHParams.STATUS_FLAG));
                line.setValue(KEY_HIDDEN_REPORT_FLAG, outparam.get(BatchStartCancelSCHParams.REPORT_FLAG));
                line.setValue(KEY_LST_WEIGHT_UNREGISTERED, outparam.get(BatchStartCancelSCHParams.WEIGHT_UNREGISTERED));
                line.setValue(KEY_HIDDEN_NUMBERING_FLAG, outparam.get(BatchStartCancelSCHParams.ORDER_NO_NUMBERING_FLAG));
                line.setValue(KEY_HIDDEN_SCHEDULE_FLAG, outparam.get(BatchStartCancelSCHParams.SCHEDULE_FLAG));
                line.setValue(KEY_HIDDEN_WEIGHT_FLAG, outparam.get(BatchStartCancelSCHParams.WEIGHT_FLAG));
                lst_BatchStartCancel_SetLineToolTip(line);
                _lcm_lst_BatchStartCancel.add(line);
            }
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
    private void btn_WorkCancel_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_BatchStartCancel.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_BatchStartCancel.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }

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
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

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

                // reset editing row or highlighting error row.
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
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_BatchStartCancel.getNewLine();
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(BatchStartCancelSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(BatchStartCancelSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_QTY, outparam.get(BatchStartCancelSCHParams.ORDER_QTY));
                line.setValue(KEY_LST_LINE_NO, outparam.get(BatchStartCancelSCHParams.LINE_NO));
                line.setValue(KEY_LST_LAST_UPDATE_DATE, outparam.get(BatchStartCancelSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_STATUS, outparam.get(BatchStartCancelSCHParams.STATUS));
                line.setValue(KEY_HIDDEN_STATUS_FLAG, outparam.get(BatchStartCancelSCHParams.STATUS_FLAG));
                line.setValue(KEY_HIDDEN_REPORT_FLAG, outparam.get(BatchStartCancelSCHParams.REPORT_FLAG));
                line.setValue(KEY_LST_WEIGHT_UNREGISTERED, outparam.get(BatchStartCancelSCHParams.WEIGHT_UNREGISTERED));
                line.setValue(KEY_HIDDEN_NUMBERING_FLAG, outparam.get(BatchStartCancelSCHParams.ORDER_NO_NUMBERING_FLAG));
                line.setValue(KEY_HIDDEN_SCHEDULE_FLAG, outparam.get(BatchStartCancelSCHParams.SCHEDULE_FLAG));
                line.setValue(KEY_HIDDEN_WEIGHT_FLAG, outparam.get(BatchStartCancelSCHParams.WEIGHT_FLAG));
                lst_BatchStartCancel_SetLineToolTip(line);
                _lcm_lst_BatchStartCancel.add(line);
            }
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
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_BatchStartCancel.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_BatchStartCancel.get(i);
            lst_BatchStartCancel.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_BatchStartCancel_SetLineToolTip(clearLine);
            _lcm_lst_BatchStartCancel.set(i, clearLine);
        }
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
            ListCellLine clearLine = _lcm_lst_BatchStartCancel.get(i);
            lst_BatchStartCancel.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_BatchStartCancel_SetLineToolTip(clearLine);
            _lcm_lst_BatchStartCancel.set(i, clearLine);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_UnRegistItem_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        BatchStartCancelDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_UnRegistItem_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new BatchStartCancelDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            BatchStartCancelDASCHParams inparam = new BatchStartCancelDASCHParams();
            inparam.set(BatchStartCancelDASCHParams.BATCH_NO, "BATCH_NO");
            inparam.set(BatchStartCancelDASCHParams.BATCH_SEQ_NO, "BATCH_SEQ_NO");
            inparam.set(BatchStartCancelDASCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.VS_BATCH_NO));
            inparam.set(BatchStartCancelDASCHParams.BATCH_SEQ_NO, viewState.getObject(ViewStateKeys.VS_BATCH_SEQ_NO));

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
                message.setMsgResourceKey("6001023\t" + Formatter.getNumFormat(count)
                        + "\t" + Formatter.getNumFormat(_pager.getMax()));
            }
            else
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;

            // clear.
            btn_Preview.setEnabled(false);
            btn_Print.setEnabled(false);
            btn_XLS.setEnabled(false);
            btn_ListClear.setEnabled(false);
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
            if (isSuccess)
            {
                // set list.
                btn_UnRegistItem_Click_SetList();
            }
            else
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
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
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
                line.setValue(KEY_HIDDEN_WEGHT_DISTINCT_RATE, outparam.get(BatchStartCancelDASCHParams.WEGHT_DISTINCT_RATE));
                line.setValue(KEY_HIDDEN_MAX_INSPECTION_UNIT_QTY, outparam.get(BatchStartCancelDASCHParams.MAX_INSPECTION_UNIT_QTY));
                line.setValue(KEY_HIDDEN_LAST_UPDATE, outparam.get(BatchStartCancelDASCHParams.LAST_UPDATE));
                line.setValue(KEY_HIDDEN_WORK_DAY, outparam.get(BatchStartCancelDASCHParams.WORK_DAY));
                line.setValue(KEY_HIDDEN_ITEM_PICTURE_FLAG, outparam.get(BatchStartCancelDASCHParams.ITEM_PICTURE_FLAG));
                line.setValue(KEY_HIDDEN_MESSAGE1, outparam.get(BatchStartCancelDASCHParams.MESSAGE1));
                lst_PCTUnRegistItemList_SetLineToolTip(line);
                _lcm_lst_PCTUnRegistItemList.add(line);
            }
            // clear.
            btn_Preview.setEnabled(false);
            btn_Print.setEnabled(false);
            btn_XLS.setEnabled(false);
            btn_ListClear.setEnabled(false);
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
    private void btn_Preview_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPVExporter("PctItemMasterList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTUnRegistItemList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTUnRegistItemList.get(i);
                PCTItemMasterListParams expparam = new PCTItemMasterListParams();
                expparam.set(PCTItemMasterListParams.CONSIGNOR_CODE, line.getValue(KEY_LST_CONSIGNOR_CODE));
                expparam.set(PCTItemMasterListParams.CONSIGNOR_NAME, line.getValue(KEY_LST_CONSIGNOR_NAME));
                expparam.set(PCTItemMasterListParams.SYS_DAY, "SYS_DAY");
                expparam.set(PCTItemMasterListParams.SYS_TIME, "SYS_TIME");
                expparam.set(PCTItemMasterListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PCTItemMasterListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PCTItemMasterListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                expparam.set(PCTItemMasterListParams.JAN, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PCTItemMasterListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PCTItemMasterListParams.LOCATION_NO, line.getValue(KEY_HIDDEN_LOCATION_NO));
                expparam.set(PCTItemMasterListParams.SINGLE_WEIGHT, line.getValue(KEY_HIDDEN_SINGLE_WEIGHT));
                expparam.set(PCTItemMasterListParams.WEGHT_DISTINCT_RATE, line.getValue(KEY_HIDDEN_WEGHT_DISTINCT_RATE));
                expparam.set(PCTItemMasterListParams.MAX_INSPECTION_UNIT_QTY, line.getValue(KEY_HIDDEN_MAX_INSPECTION_UNIT_QTY));
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
            DBUtil.rollback(conn);
            DBUtil.close(conn);
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

        Connection conn = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("PctItemMasterList", false);
            exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTUnRegistItemList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTUnRegistItemList.get(i);
                PCTItemMasterListParams expparam = new PCTItemMasterListParams();
                expparam.set(PCTItemMasterListParams.CONSIGNOR_CODE, line.getValue(KEY_LST_CONSIGNOR_CODE));
                expparam.set(PCTItemMasterListParams.CONSIGNOR_NAME, line.getValue(KEY_LST_CONSIGNOR_NAME));
                expparam.set(PCTItemMasterListParams.SYS_DAY, "SYS_DAY");
                expparam.set(PCTItemMasterListParams.SYS_TIME, "SYS_TIME");
                expparam.set(PCTItemMasterListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PCTItemMasterListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PCTItemMasterListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                expparam.set(PCTItemMasterListParams.JAN, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PCTItemMasterListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PCTItemMasterListParams.LOCATION_NO, line.getValue(KEY_HIDDEN_LOCATION_NO));
                expparam.set(PCTItemMasterListParams.SINGLE_WEIGHT, line.getValue(KEY_HIDDEN_SINGLE_WEIGHT));
                expparam.set(PCTItemMasterListParams.WEGHT_DISTINCT_RATE, line.getValue(KEY_HIDDEN_WEGHT_DISTINCT_RATE));
                expparam.set(PCTItemMasterListParams.MAX_INSPECTION_UNIT_QTY, line.getValue(KEY_HIDDEN_MAX_INSPECTION_UNIT_QTY));
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
            DBUtil.rollback(conn);
            DBUtil.close(conn);
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

        Connection conn = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newExcelExporter("PctItemMasterList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTUnRegistItemList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTUnRegistItemList.get(i);
                PCTItemMasterListParams expparam = new PCTItemMasterListParams();
                expparam.set(PCTItemMasterListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PCTItemMasterListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PCTItemMasterListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                expparam.set(PCTItemMasterListParams.JAN, line.getValue(KEY_HIDDEN_JAN));
                expparam.set(PCTItemMasterListParams.ITF, line.getValue(KEY_HIDDEN_ITF));
                expparam.set(PCTItemMasterListParams.LOCATION_NO, line.getValue(KEY_HIDDEN_LOCATION_NO));
                expparam.set(PCTItemMasterListParams.SINGLE_WEIGHT, line.getValue(KEY_HIDDEN_SINGLE_WEIGHT));
                expparam.set(PCTItemMasterListParams.WEGHT_DISTINCT_RATE, line.getValue(KEY_HIDDEN_WEGHT_DISTINCT_RATE));
                expparam.set(PCTItemMasterListParams.MAX_INSPECTION_UNIT_QTY, line.getValue(KEY_HIDDEN_MAX_INSPECTION_UNIT_QTY));
                expparam.set(PCTItemMasterListParams.LAST_UPDATE, line.getValue(KEY_HIDDEN_LAST_UPDATE));
                expparam.set(PCTItemMasterListParams.WORK_DAY, line.getValue(KEY_HIDDEN_WORK_DAY));
                expparam.set(PCTItemMasterListParams.ITEM_PICTURE_FLAG, line.getValue(KEY_HIDDEN_ITEM_PICTURE_FLAG));
                expparam.set(PCTItemMasterListParams.MESSAGE1, line.getValue(KEY_HIDDEN_MESSAGE1));
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
            DBUtil.rollback(conn);
            DBUtil.close(conn);
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
        btn_Preview.setEnabled(true);
        btn_Print.setEnabled(true);
        btn_XLS.setEnabled(true);
        btn_ListClear.setEnabled(true);
        _pager.clear();
        _lcm_lst_PCTUnRegistItemList.clear();
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

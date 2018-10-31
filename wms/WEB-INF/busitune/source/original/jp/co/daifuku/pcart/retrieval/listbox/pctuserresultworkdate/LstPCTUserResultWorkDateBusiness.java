// $Id: LstPCTUserResultWorkDateBusiness.java 5329 2009-10-29 05:35:28Z shibamoto $
package jp.co.daifuku.pcart.retrieval.listbox.pctuserresultworkdate;

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
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
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
import jp.co.daifuku.Constants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultWorkDateDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultWorkDateDASCHParams;
import jp.co.daifuku.pcart.retrieval.exporter.PCTUserResultWorkDateListParams;
import jp.co.daifuku.pcart.retrieval.listbox.pctuserresultworkdate.LstPCTUserResultWorkDate;
import jp.co.daifuku.pcart.retrieval.listbox.pctuserresultworkdate.LstPCTUserResultWorkDateParams;
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
public class LstPCTUserResultWorkDateBusiness
        extends LstPCTUserResultWorkDate
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

    /** lst_SearchCondition(HIDDEN_CONSIGNOR_NAME) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_NAME = new ListCellKey("HIDDEN_CONSIGNOR_NAME", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_AREA_NAME) */
    private static final ListCellKey KEY_HIDDEN_AREA_NAME = new ListCellKey("HIDDEN_AREA_NAME", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_WORK_DAY_FROM) */
    private static final ListCellKey KEY_HIDDEN_WORK_DAY_FROM = new ListCellKey("HIDDEN_WORK_DAY_FROM", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_WORK_DAY_TO) */
    private static final ListCellKey KEY_HIDDEN_WORK_DAY_TO = new ListCellKey("HIDDEN_WORK_DAY_TO", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_WEEK_DAY) */
    private static final ListCellKey KEY_HIDDEN_WEEK_DAY = new ListCellKey("HIDDEN_WEEK_DAY", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_BATCH_NO) */
    private static final ListCellKey KEY_HIDDEN_BATCH_NO = new ListCellKey("HIDDEN_BATCH_NO", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_AREA_NO) */
    private static final ListCellKey KEY_HIDDEN_AREA_NO = new ListCellKey("HIDDEN_AREA_NO", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_LEVEL) */
    private static final ListCellKey KEY_HIDDEN_LEVEL = new ListCellKey("HIDDEN_LEVEL", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE = new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_1) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_1 = new ListCellKey("LST_SEARCH_CONDITION_1", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_2) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_2 = new ListCellKey("LST_SEARCH_CONDITION_2", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_3) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_3 = new ListCellKey("LST_SEARCH_CONDITION_3", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_4) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_4 = new ListCellKey("LST_SEARCH_CONDITION_4", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_1) */
    private static final ListCellKey KEY_LST_AVERAGE_1 = new ListCellKey("LST_AVERAGE_1", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_2) */
    private static final ListCellKey KEY_LST_AVERAGE_2 = new ListCellKey("LST_AVERAGE_2", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_3) */
    private static final ListCellKey KEY_LST_AVERAGE_3 = new ListCellKey("LST_AVERAGE_3", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_4) */
    private static final ListCellKey KEY_LST_AVERAGE_4 = new ListCellKey("LST_AVERAGE_4", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_5) */
    private static final ListCellKey KEY_LST_AVERAGE_5 = new ListCellKey("LST_AVERAGE_5", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_6) */
    private static final ListCellKey KEY_LST_AVERAGE_6 = new ListCellKey("LST_AVERAGE_6", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_7) */
    private static final ListCellKey KEY_LST_AVERAGE_7 = new ListCellKey("LST_AVERAGE_7", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_8) */
    private static final ListCellKey KEY_LST_AVERAGE_8 = new ListCellKey("LST_AVERAGE_8", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_9) */
    private static final ListCellKey KEY_LST_AVERAGE_9 = new ListCellKey("LST_AVERAGE_9", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_10) */
    private static final ListCellKey KEY_LST_AVERAGE_10 = new ListCellKey("LST_AVERAGE_10", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_11) */
    private static final ListCellKey KEY_LST_AVERAGE_11 = new ListCellKey("LST_AVERAGE_11", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_PRODUCTION_RATE) */
    private static final ListCellKey KEY_LST_AVG_PRODUCTION_RATE = new ListCellKey("LST_AVG_PRODUCTION_RATE", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_WORKER_COUNT) */
    private static final ListCellKey KEY_LST_AVG_WORKER_COUNT = new ListCellKey("LST_AVG_WORKER_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_TERMINAL_COUNT) */
    private static final ListCellKey KEY_LST_AVG_TERMINAL_COUNT = new ListCellKey("LST_AVG_TERMINAL_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_OPERATE_TIME) */
    private static final ListCellKey KEY_LST_AVG_OPERATE_TIME = new ListCellKey("LST_AVG_OPERATE_TIME", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_AVG_ORDER_COUNT = new ListCellKey("LST_AVG_ORDER_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_ORDER_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_AVG_ORDER_COUNT_PER_HOUR = new ListCellKey("LST_AVG_ORDER_COUNT_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_BOX_COUNT) */
    private static final ListCellKey KEY_LST_AVG_BOX_COUNT = new ListCellKey("LST_AVG_BOX_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_BOX_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_AVG_BOX_COUNT_PER_HOUR = new ListCellKey("LST_AVG_BOX_COUNT_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_LINE_COUNT) */
    private static final ListCellKey KEY_LST_AVG_LINE_COUNT = new ListCellKey("LST_AVG_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_LINE_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_AVG_LINE_COUNT_PER_HOUR = new ListCellKey("LST_AVG_LINE_COUNT_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_LOT_QTY) */
    private static final ListCellKey KEY_LST_AVG_LOT_QTY = new ListCellKey("LST_AVG_LOT_QTY", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_LOT_QTY_PER_HOUR) */
    private static final ListCellKey KEY_LST_AVG_LOT_QTY_PER_HOUR = new ListCellKey("LST_AVG_LOT_QTY_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_PIECE_QTY) */
    private static final ListCellKey KEY_LST_AVG_PIECE_QTY = new ListCellKey("LST_AVG_PIECE_QTY", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_PIECE_QTY_PER_HOUR) */
    private static final ListCellKey KEY_LST_AVG_PIECE_QTY_PER_HOUR = new ListCellKey("LST_AVG_PIECE_QTY_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_BOX_COUNT_PER_ORDER) */
    private static final ListCellKey KEY_LST_AVG_BOX_COUNT_PER_ORDER = new ListCellKey("LST_AVG_BOX_COUNT_PER_ORDER", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_LINE_COUNT_PER_ORDER) */
    private static final ListCellKey KEY_LST_AVG_LINE_COUNT_PER_ORDER = new ListCellKey("LST_AVG_LINE_COUNT_PER_ORDER", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_LOT_QTY_PER_LINE_COUNT) */
    private static final ListCellKey KEY_LST_AVG_LOT_QTY_PER_LINE_COUNT = new ListCellKey("LST_AVG_LOT_QTY_PER_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_PIECE_QTY_PER_LINE_COUNT) */
    private static final ListCellKey KEY_LST_AVG_PIECE_QTY_PER_LINE_COUNT = new ListCellKey("LST_AVG_PIECE_QTY_PER_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_MISS_RATE) */
    private static final ListCellKey KEY_LST_AVG_MISS_RATE = new ListCellKey("LST_AVG_MISS_RATE", new StringCellColumn(), true, false);

    /** lst_UserResultWorkDateList(LST_WORK_DATE) */
    private static final ListCellKey KEY_LST_WORK_DATE = new ListCellKey("LST_WORK_DATE", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_UserResultWorkDateList(LST_PRODUCTION_RATE) */
    private static final ListCellKey KEY_LST_PRODUCTION_RATE = new ListCellKey("LST_PRODUCTION_RATE", new StringCellColumn(), true, false);

    /** lst_UserResultWorkDateList(LST_WORKER_COUNT) */
    private static final ListCellKey KEY_LST_WORKER_COUNT = new ListCellKey("LST_WORKER_COUNT", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_TERMINAL_COUNT) */
    private static final ListCellKey KEY_LST_TERMINAL_COUNT = new ListCellKey("LST_TERMINAL_COUNT", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_START_TIME) */
    private static final ListCellKey KEY_LST_START_TIME = new ListCellKey("LST_START_TIME", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMSS), true, false);

    /** lst_UserResultWorkDateList(LST_END_TIME) */
    private static final ListCellKey KEY_LST_END_TIME = new ListCellKey("LST_END_TIME", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMSS), true, false);

    /** lst_UserResultWorkDateList(LST_OPERATE_TIME) */
    private static final ListCellKey KEY_LST_OPERATE_TIME = new ListCellKey("LST_OPERATE_TIME", new StringCellColumn(), true, false);

    /** lst_UserResultWorkDateList(LST_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_ORDER_COUNT = new ListCellKey("LST_ORDER_COUNT", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_ORDER_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_PER_HOUR = new ListCellKey("LST_ORDER_COUNT_PER_HOUR", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_BOX_COUNT) */
    private static final ListCellKey KEY_LST_BOX_COUNT = new ListCellKey("LST_BOX_COUNT", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_BOX_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_BOX_COUNT_PER_HOUR = new ListCellKey("LST_BOX_COUNT_PER_HOUR", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LINE_COUNT = new ListCellKey("LST_LINE_COUNT", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_LINE_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_LINE_COUNT_PER_HOUR = new ListCellKey("LST_LINE_COUNT_PER_HOUR", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_LOT_QTY) */
    private static final ListCellKey KEY_LST_LOT_QTY = new ListCellKey("LST_LOT_QTY", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_LOT_QTY_PER_HOUR) */
    private static final ListCellKey KEY_LST_LOT_QTY_PER_HOUR = new ListCellKey("LST_LOT_QTY_PER_HOUR", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PIECE_QTY = new ListCellKey("LST_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_PIECE_QTY_PER_HOUR) */
    private static final ListCellKey KEY_LST_PIECE_QTY_PER_HOUR = new ListCellKey("LST_PIECE_QTY_PER_HOUR", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_BOX_COUNT_PER_ORDER) */
    private static final ListCellKey KEY_LST_BOX_COUNT_PER_ORDER = new ListCellKey("LST_BOX_COUNT_PER_ORDER", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_LINE_COUNT_PER_ORDER) */
    private static final ListCellKey KEY_LST_LINE_COUNT_PER_ORDER = new ListCellKey("LST_LINE_COUNT_PER_ORDER", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_LOT_QTY_PER_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LOT_QTY_PER_LINE_COUNT = new ListCellKey("LST_LOT_QTY_PER_LINE_COUNT", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_PIECE_QTY_PER_LINE_COUNT) */
    private static final ListCellKey KEY_LST_PIECE_QTY_PER_LINE_COUNT = new ListCellKey("LST_PIECE_QTY_PER_LINE_COUNT", new NumberCellColumn(0), true, false);

    /** lst_UserResultWorkDateList(LST_MISS_RATE) */
    private static final ListCellKey KEY_LST_MISS_RATE = new ListCellKey("LST_MISS_RATE", new StringCellColumn(), true, false);

    /** lst_SearchCondition keys */
    private static final ListCellKey[] LST_SEARCHCONDITION_KEYS = {
        KEY_HIDDEN_CONSIGNOR_NAME,
        KEY_HIDDEN_AREA_NAME,
        KEY_HIDDEN_WORK_DAY_FROM,
        KEY_HIDDEN_WORK_DAY_TO,
        KEY_HIDDEN_WEEK_DAY,
        KEY_HIDDEN_BATCH_NO,
        KEY_HIDDEN_AREA_NO,
        KEY_HIDDEN_LEVEL,
        KEY_HIDDEN_CONSIGNOR_CODE,
        KEY_LST_SEARCH_CONDITION_1,
        KEY_LST_SEARCH_CONDITION_2,
        KEY_LST_SEARCH_CONDITION_3,
        KEY_LST_SEARCH_CONDITION_4,
    };

    /** lst_Header keys */
    private static final ListCellKey[] LST_HEADER_KEYS = {
        KEY_LST_AVERAGE_1,
        KEY_LST_AVERAGE_2,
        KEY_LST_AVERAGE_3,
        KEY_LST_AVERAGE_4,
        KEY_LST_AVERAGE_5,
        KEY_LST_AVERAGE_6,
        KEY_LST_AVERAGE_7,
        KEY_LST_AVERAGE_8,
        KEY_LST_AVERAGE_9,
        KEY_LST_AVERAGE_10,
        KEY_LST_AVERAGE_11,
    };

    /** lst_UserResultSummaryAvg keys */
    private static final ListCellKey[] LST_USERRESULTSUMMARYAVG_KEYS = {
        KEY_LST_AVG_PRODUCTION_RATE,
        KEY_LST_AVG_WORKER_COUNT,
        KEY_LST_AVG_OPERATE_TIME,
        KEY_LST_AVG_ORDER_COUNT,
        KEY_LST_AVG_BOX_COUNT,
        KEY_LST_AVG_LINE_COUNT,
        KEY_LST_AVG_LOT_QTY,
        KEY_LST_AVG_PIECE_QTY,
        KEY_LST_AVG_BOX_COUNT_PER_ORDER,
        KEY_LST_AVG_LOT_QTY_PER_LINE_COUNT,
        KEY_LST_AVG_MISS_RATE,
        KEY_LST_AVG_TERMINAL_COUNT,
        KEY_LST_AVG_ORDER_COUNT_PER_HOUR,
        KEY_LST_AVG_BOX_COUNT_PER_HOUR,
        KEY_LST_AVG_LINE_COUNT_PER_HOUR,
        KEY_LST_AVG_LOT_QTY_PER_HOUR,
        KEY_LST_AVG_PIECE_QTY_PER_HOUR,
        KEY_LST_AVG_LINE_COUNT_PER_ORDER,
        KEY_LST_AVG_PIECE_QTY_PER_LINE_COUNT,
    };

    /** lst_UserResultWorkDateList keys */
    private static final ListCellKey[] LST_USERRESULTWORKDATELIST_KEYS = {
        KEY_LST_WORK_DATE,
        KEY_LST_PRODUCTION_RATE,
        KEY_LST_WORKER_COUNT,
        KEY_LST_START_TIME,
        KEY_LST_OPERATE_TIME,
        KEY_LST_ORDER_COUNT,
        KEY_LST_BOX_COUNT,
        KEY_LST_LINE_COUNT,
        KEY_LST_LOT_QTY,
        KEY_LST_PIECE_QTY,
        KEY_LST_BOX_COUNT_PER_ORDER,
        KEY_LST_LOT_QTY_PER_LINE_COUNT,
        KEY_LST_MISS_RATE,
        KEY_LST_TERMINAL_COUNT,
        KEY_LST_END_TIME,
        KEY_LST_ORDER_COUNT_PER_HOUR,
        KEY_LST_BOX_COUNT_PER_HOUR,
        KEY_LST_LINE_COUNT_PER_HOUR,
        KEY_LST_LOT_QTY_PER_HOUR,
        KEY_LST_PIECE_QTY_PER_HOUR,
        KEY_LST_LINE_COUNT_PER_ORDER,
        KEY_LST_PIECE_QTY_PER_LINE_COUNT,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_SearchCondition */
    private ListCellModel _lcm_lst_SearchCondition;

    /** ListCellModel lst_Header */
    private ListCellModel _lcm_lst_Header;

    /** ListCellModel lst_UserResultSummaryAvg */
    private ListCellModel _lcm_lst_UserResultSummaryAvg;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_UserResultWorkDateList */
    private ListCellModel _lcm_lst_UserResultWorkDateList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstPCTUserResultWorkDateBusiness()
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
    public void pgr_U_First(ActionEvent e)
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
    public void pgr_U_Prev(ActionEvent e)
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
    public void pgr_U_Next(ActionEvent e)
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
    public void pgr_U_Last(ActionEvent e)
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
    public void btn_Close_U_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_U_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_First(ActionEvent e)
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
    public void pgr_D_Prev(ActionEvent e)
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
    public void pgr_D_Next(ActionEvent e)
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
    public void pgr_D_Last(ActionEvent e)
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
    public void btn_Close_D_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_D_Click_Process();
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

        // initialize lst_SearchCondition.
        _lcm_lst_SearchCondition = new ListCellModel(lst_SearchCondition, LST_SEARCHCONDITION_KEYS, locale);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_SEARCH_CONDITION_1, true);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_SEARCH_CONDITION_2, true);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_SEARCH_CONDITION_3, true);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_SEARCH_CONDITION_4, true);

        // initialize lst_Header.
        _lcm_lst_Header = new ListCellModel(lst_Header, LST_HEADER_KEYS, locale);
        _lcm_lst_Header.setToolTipVisible(KEY_LST_AVERAGE_1, false);
        _lcm_lst_Header.setToolTipVisible(KEY_LST_AVERAGE_2, false);
        _lcm_lst_Header.setToolTipVisible(KEY_LST_AVERAGE_3, false);
        _lcm_lst_Header.setToolTipVisible(KEY_LST_AVERAGE_4, false);
        _lcm_lst_Header.setToolTipVisible(KEY_LST_AVERAGE_5, false);
        _lcm_lst_Header.setToolTipVisible(KEY_LST_AVERAGE_6, false);
        _lcm_lst_Header.setToolTipVisible(KEY_LST_AVERAGE_7, false);
        _lcm_lst_Header.setToolTipVisible(KEY_LST_AVERAGE_8, false);
        _lcm_lst_Header.setToolTipVisible(KEY_LST_AVERAGE_9, false);
        _lcm_lst_Header.setToolTipVisible(KEY_LST_AVERAGE_10, false);
        _lcm_lst_Header.setToolTipVisible(KEY_LST_AVERAGE_11, false);

        // initialize lst_UserResultSummaryAvg.
        _lcm_lst_UserResultSummaryAvg = new ListCellModel(lst_UserResultSummaryAvg, LST_USERRESULTSUMMARYAVG_KEYS, locale);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_PRODUCTION_RATE, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_WORKER_COUNT, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_TERMINAL_COUNT, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_OPERATE_TIME, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_ORDER_COUNT, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_ORDER_COUNT_PER_HOUR, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_BOX_COUNT, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_BOX_COUNT_PER_HOUR, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_LINE_COUNT, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_LINE_COUNT_PER_HOUR, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_LOT_QTY, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_LOT_QTY_PER_HOUR, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_PIECE_QTY, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_PIECE_QTY_PER_HOUR, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_BOX_COUNT_PER_ORDER, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_LINE_COUNT_PER_ORDER, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_LOT_QTY_PER_LINE_COUNT, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_PIECE_QTY_PER_LINE_COUNT, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_MISS_RATE, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_UserResultWorkDateList.
        _lcm_lst_UserResultWorkDateList = new ListCellModel(lst_UserResultWorkDateList, LST_USERRESULTWORKDATELIST_KEYS, locale);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_WORK_DATE, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_PRODUCTION_RATE, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_WORKER_COUNT, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_TERMINAL_COUNT, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_START_TIME, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_END_TIME, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_OPERATE_TIME, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_ORDER_COUNT, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_ORDER_COUNT_PER_HOUR, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_BOX_COUNT, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_BOX_COUNT_PER_HOUR, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_LINE_COUNT, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_LINE_COUNT_PER_HOUR, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_LOT_QTY, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_LOT_QTY_PER_HOUR, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_PIECE_QTY, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_PIECE_QTY_PER_HOUR, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_BOX_COUNT_PER_ORDER, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_LINE_COUNT_PER_ORDER, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_LOT_QTY_PER_LINE_COUNT, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_PIECE_QTY_PER_LINE_COUNT, true);
        _lcm_lst_UserResultWorkDateList.setToolTipVisible(KEY_LST_MISS_RATE, true);

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
    private void lst_SearchCondition_SetLineToolTip(ListCellLine line)
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
    private void lst_Header_SetLineToolTip(ListCellLine line)
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
    private void lst_UserResultSummaryAvg_SetLineToolTip(ListCellLine line)
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
    private void lst_UserResultWorkDateList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-P0080", KEY_LST_MISS_RATE);
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
        if (eventSource.equals("page_Load"))
        {
            // process call.
            page_Load_SetList();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(pgr_U);

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
        LstPCTUserResultWorkDateDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstPCTUserResultWorkDateDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstPCTUserResultWorkDateDASCHParams inparam = new LstPCTUserResultWorkDateDASCHParams();
            LstPCTUserResultWorkDateParams requestParam = new LstPCTUserResultWorkDateParams(request);
            inparam.set(LstPCTUserResultWorkDateDASCHParams.WORKDAY_FROM, requestParam.get(LstPCTUserResultWorkDateParams.WORKDAY_FROM));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.WORKDAY_TO, requestParam.get(LstPCTUserResultWorkDateParams.WORKDAY_TO));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.MONDAY, requestParam.get(LstPCTUserResultWorkDateParams.MONDAY));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.TUESDAY, requestParam.get(LstPCTUserResultWorkDateParams.TUESDAY));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.WEDNESDAY, requestParam.get(LstPCTUserResultWorkDateParams.WEDNESDAY));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.THURSDAY, requestParam.get(LstPCTUserResultWorkDateParams.THURSDAY));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.FRIDAY, requestParam.get(LstPCTUserResultWorkDateParams.FRIDAY));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.SATURDAY, requestParam.get(LstPCTUserResultWorkDateParams.SATURDAY));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.SUNDAY, requestParam.get(LstPCTUserResultWorkDateParams.SUNDAY));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.CONSIGNOR_CODE, requestParam.get(LstPCTUserResultWorkDateParams.CONSIGNOR_CODE));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.AREA_NO, requestParam.get(LstPCTUserResultWorkDateParams.AREA_NO));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.BATCH_NO, requestParam.get(LstPCTUserResultWorkDateParams.BATCH_NO));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.LEVEL_A, requestParam.get(LstPCTUserResultWorkDateParams.LEVEL_A));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.LEVEL_B, requestParam.get(LstPCTUserResultWorkDateParams.LEVEL_B));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.LEVEL_C, requestParam.get(LstPCTUserResultWorkDateParams.LEVEL_C));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.COLLECT_CONDITION, requestParam.get(LstPCTUserResultWorkDateParams.COLLECT_CONDITION));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.DISPLAY_RANK, requestParam.get(LstPCTUserResultWorkDateParams.DISPLAY_RANK));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.USEDAY_OF_WEEK_FLAG, requestParam.get(LstPCTUserResultWorkDateParams.USEDAY_OF_WEEK_FLAG));
            inparam.set(LstPCTUserResultWorkDateDASCHParams.SYS_DAY, "SYS_DAY");
            inparam.set(LstPCTUserResultWorkDateDASCHParams.SYS_TIME, "SYS_TIME");
            inparam.set(LstPCTUserResultWorkDateDASCHParams.CONSIGNOR_NAME, "CONSIGNOR_NAME");
            inparam.set(LstPCTUserResultWorkDateDASCHParams.AREA_NAME, "AREA_NAME");

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_UserResultWorkDateList.clear();

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

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_UserResultWorkDateList.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                page_Load_SetList();
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
    private void page_Load_SetList()
            throws Exception
    {
        LstPCTUserResultWorkDateDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstPCTUserResultWorkDateDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_UserResultWorkDateList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_UserResultWorkDateList.getNewLine();
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.WORKDAY));
                line.setValue(KEY_LST_PRODUCTION_RATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.PRODUCTION_RATE));
                line.setValue(KEY_LST_WORKER_COUNT, outparam.get(LstPCTUserResultWorkDateDASCHParams.WORKER_COUNT));
                line.setValue(KEY_LST_TERMINAL_COUNT, outparam.get(LstPCTUserResultWorkDateDASCHParams.TERMINAL_COUNT));
                line.setValue(KEY_LST_START_TIME, outparam.get(LstPCTUserResultWorkDateDASCHParams.START_TIME));
                line.setValue(KEY_LST_END_TIME, outparam.get(LstPCTUserResultWorkDateDASCHParams.END_TIME));
                line.setValue(KEY_LST_OPERATE_TIME, outparam.get(LstPCTUserResultWorkDateDASCHParams.OPERATE_TIME));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(LstPCTUserResultWorkDateDASCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_ORDER_COUNT_PER_HOUR, outparam.get(LstPCTUserResultWorkDateDASCHParams.ORDER_COUNT_PER_HOUR));
                line.setValue(KEY_LST_BOX_COUNT, outparam.get(LstPCTUserResultWorkDateDASCHParams.BOX_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_PER_HOUR, outparam.get(LstPCTUserResultWorkDateDASCHParams.BOX_COUNT_PER_HOUR));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(LstPCTUserResultWorkDateDASCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_PER_HOUR, outparam.get(LstPCTUserResultWorkDateDASCHParams.LINE_COUNT_PER_HOUR));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(LstPCTUserResultWorkDateDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_LOT_QTY_PER_HOUR, outparam.get(LstPCTUserResultWorkDateDASCHParams.LOT_QTY_PER_HOUR));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(LstPCTUserResultWorkDateDASCHParams.PIECE_QTY));
                line.setValue(KEY_LST_PIECE_QTY_PER_HOUR, outparam.get(LstPCTUserResultWorkDateDASCHParams.PIECE_QTY_PER_HOUR));
                line.setValue(KEY_LST_BOX_COUNT_PER_ORDER, outparam.get(LstPCTUserResultWorkDateDASCHParams.BOX_COUNT_PER_ORDER));
                line.setValue(KEY_LST_LINE_COUNT_PER_ORDER, outparam.get(LstPCTUserResultWorkDateDASCHParams.LINE_COUNT_PER_ORDER));
                line.setValue(KEY_LST_LOT_QTY_PER_LINE_COUNT, outparam.get(LstPCTUserResultWorkDateDASCHParams.LOT_QTY_PER_LINE_COUNT));
                line.setValue(KEY_LST_PIECE_QTY_PER_LINE_COUNT, outparam.get(LstPCTUserResultWorkDateDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                line.setValue(KEY_LST_MISS_RATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.MISS_RATE));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_PRODUCTION_RATE));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_WORKER_COUNT));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_TERMINAL_COUNT));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_OPERATE_TIME));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_ORDER_COUNT));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_ORDER_COUNT_PER_HOUR));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_BOX_COUNT));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_BOX_COUNT_PER_HOUR));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LINE_COUNT));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LINE_COUNT_PER_HOUR));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LOT_QTY));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LOT_QTY_PER_HOUR));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_PIECE_QTY));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_PIECE_QTY_PER_HOUR));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_BOX_COUNT_PER_ORDER));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LINE_COUNT_PER_ORDER));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_LOT_QTY_PER_LINE_COUNT));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_PIECE_QTY_PER_LINE_COUNT));
                line.setValue(KEY_LST_WORK_DATE, outparam.get(LstPCTUserResultWorkDateDASCHParams.AVG_MISS_RATE));
                lst_UserResultWorkDateList_SetLineToolTip(line);
                _lcm_lst_UserResultWorkDateList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_UserResultWorkDateList.clear();
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
            exporter = factory.newPVExporter("PctUserResultWorkDateList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_UserResultWorkDateList.size(); i++)
            {
                ListCellLine line = _lcm_lst_UserResultWorkDateList.get(i);
                PCTUserResultWorkDateListParams expparam = new PCTUserResultWorkDateListParams();
                expparam.set(PCTUserResultWorkDateListParams.SYS_DAY, "SYS_DAY");
                expparam.set(PCTUserResultWorkDateListParams.SYS_TIME, "SYS_TIME");
                expparam.set(PCTUserResultWorkDateListParams.WORK_DAY_FROM, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.WORK_DAY_TO, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.WEEK_DAY, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.BATCH_NO, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LEVEL, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.CONSIGNOR_CODE, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.CONSIGNOR_NAME, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.AREA_NO, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.AREA_NAME, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.PRODUCTION_RATE_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.HEADS_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.WORK_TIME_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.ORDER_CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.ORDER_CNT_PER_HOUR_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.BOX_CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.BOX_CNT_PER_HOUR_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LINE_CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LINE_CNT_PER_HOUR_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LOT_CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LOT_CNT_PER_HOUR_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_CNT_PER_HOUR_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.BOX_PER_ORDER_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LINE_PER_ORDER_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LOT_PER_LINE_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_PER_LINE_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.MISS_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.WORK_DAY, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.PRODUCTION_RATE, line.getValue(KEY_LST_PRODUCTION_RATE));
                expparam.set(PCTUserResultWorkDateListParams.HEADS, line.getValue(KEY_LST_WORKER_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.CNT, line.getValue(KEY_LST_TERMINAL_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.START_DATE, line.getValue(KEY_LST_START_TIME));
                expparam.set(PCTUserResultWorkDateListParams.END_DATE, line.getValue(KEY_LST_END_TIME));
                expparam.set(PCTUserResultWorkDateListParams.WORK_TIME, line.getValue(KEY_LST_OPERATE_TIME));
                expparam.set(PCTUserResultWorkDateListParams.ORDER_CNT, line.getValue(KEY_LST_ORDER_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.ORDER_CNT_PER_HOUR, line.getValue(KEY_LST_ORDER_COUNT_PER_HOUR));
                expparam.set(PCTUserResultWorkDateListParams.BOX_CNT, line.getValue(KEY_LST_BOX_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.BOX_CNT_PER_HOUR, line.getValue(KEY_LST_BOX_COUNT_PER_HOUR));
                expparam.set(PCTUserResultWorkDateListParams.LINE_CNT, line.getValue(KEY_LST_LINE_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.LINE_CNT_PER_HOUR, line.getValue(KEY_LST_LINE_COUNT_PER_HOUR));
                expparam.set(PCTUserResultWorkDateListParams.LOT_CNT, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PCTUserResultWorkDateListParams.LOT_CNT_PER_HOUR, line.getValue(KEY_LST_LOT_QTY_PER_HOUR));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_CNT, line.getValue(KEY_LST_PIECE_QTY));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_CNT_PER_HOUR, line.getValue(KEY_LST_PIECE_QTY_PER_HOUR));
                expparam.set(PCTUserResultWorkDateListParams.BOX_PER_ORDER, line.getValue(KEY_LST_BOX_COUNT_PER_ORDER));
                expparam.set(PCTUserResultWorkDateListParams.LINE_PER_ORDER, line.getValue(KEY_LST_LINE_COUNT_PER_ORDER));
                expparam.set(PCTUserResultWorkDateListParams.LOT_PER_LINE, line.getValue(KEY_LST_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_PER_LINE, line.getValue(KEY_LST_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.MISS, line.getValue(KEY_LST_MISS_RATE));
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
            exporter = factory.newPrinterExporter("PctUserResultWorkDateList", false);
            exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_UserResultWorkDateList.size(); i++)
            {
                ListCellLine line = _lcm_lst_UserResultWorkDateList.get(i);
                PCTUserResultWorkDateListParams expparam = new PCTUserResultWorkDateListParams();
                expparam.set(PCTUserResultWorkDateListParams.SYS_DAY, "SYS_DAY");
                expparam.set(PCTUserResultWorkDateListParams.SYS_TIME, "SYS_TIME");
                expparam.set(PCTUserResultWorkDateListParams.WORK_DAY_FROM, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.WORK_DAY_TO, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.WEEK_DAY, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.BATCH_NO, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LEVEL, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.CONSIGNOR_CODE, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.CONSIGNOR_NAME, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.AREA_NO, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.AREA_NAME, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.PRODUCTION_RATE_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.HEADS_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.WORK_TIME_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.ORDER_CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.ORDER_CNT_PER_HOUR_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.BOX_CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.BOX_CNT_PER_HOUR_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LINE_CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LINE_CNT_PER_HOUR_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LOT_CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LOT_CNT_PER_HOUR_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_CNT_PER_HOUR_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.BOX_PER_ORDER_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LINE_PER_ORDER_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LOT_PER_LINE_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_PER_LINE_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.MISS_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.WORK_DAY, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.PRODUCTION_RATE, line.getValue(KEY_LST_PRODUCTION_RATE));
                expparam.set(PCTUserResultWorkDateListParams.HEADS, line.getValue(KEY_LST_WORKER_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.CNT, line.getValue(KEY_LST_TERMINAL_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.START_DATE, line.getValue(KEY_LST_START_TIME));
                expparam.set(PCTUserResultWorkDateListParams.END_DATE, line.getValue(KEY_LST_END_TIME));
                expparam.set(PCTUserResultWorkDateListParams.WORK_TIME, line.getValue(KEY_LST_OPERATE_TIME));
                expparam.set(PCTUserResultWorkDateListParams.ORDER_CNT, line.getValue(KEY_LST_ORDER_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.ORDER_CNT_PER_HOUR, line.getValue(KEY_LST_ORDER_COUNT_PER_HOUR));
                expparam.set(PCTUserResultWorkDateListParams.BOX_CNT, line.getValue(KEY_LST_BOX_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.BOX_CNT_PER_HOUR, line.getValue(KEY_LST_BOX_COUNT_PER_HOUR));
                expparam.set(PCTUserResultWorkDateListParams.LINE_CNT, line.getValue(KEY_LST_LINE_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.LINE_CNT_PER_HOUR, line.getValue(KEY_LST_LINE_COUNT_PER_HOUR));
                expparam.set(PCTUserResultWorkDateListParams.LOT_CNT, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PCTUserResultWorkDateListParams.LOT_CNT_PER_HOUR, line.getValue(KEY_LST_LOT_QTY_PER_HOUR));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_CNT, line.getValue(KEY_LST_PIECE_QTY));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_CNT_PER_HOUR, line.getValue(KEY_LST_PIECE_QTY_PER_HOUR));
                expparam.set(PCTUserResultWorkDateListParams.BOX_PER_ORDER, line.getValue(KEY_LST_BOX_COUNT_PER_ORDER));
                expparam.set(PCTUserResultWorkDateListParams.LINE_PER_ORDER, line.getValue(KEY_LST_LINE_COUNT_PER_ORDER));
                expparam.set(PCTUserResultWorkDateListParams.LOT_PER_LINE, line.getValue(KEY_LST_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_PER_LINE, line.getValue(KEY_LST_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.MISS, line.getValue(KEY_LST_MISS_RATE));
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
            exporter = factory.newExcelExporter("PctUserResultWorkDateList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_UserResultWorkDateList.size(); i++)
            {
                ListCellLine line = _lcm_lst_UserResultWorkDateList.get(i);
                PCTUserResultWorkDateListParams expparam = new PCTUserResultWorkDateListParams();
                expparam.set(PCTUserResultWorkDateListParams.WORK_DAY_FROM, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.WORK_DAY_TO, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.WEEK_DAY, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.BATCH_NO, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LEVEL, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.CONSIGNOR_CODE, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.CONSIGNOR_NAME, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.AREA_NO, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.AREA_NAME, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.PRODUCTION_RATE_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.HEADS_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.WORK_TIME_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.ORDER_CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.ORDER_CNT_PER_HOUR_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.BOX_CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.BOX_CNT_PER_HOUR_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LINE_CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LINE_CNT_PER_HOUR_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LOT_CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LOT_CNT_PER_HOUR_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_CNT_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_CNT_PER_HOUR_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.BOX_PER_ORDER_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LINE_PER_ORDER_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.LOT_PER_LINE_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_PER_LINE_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.MISS_AVG, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.WORK_DAY, line.getValue(KEY_LST_WORK_DATE));
                expparam.set(PCTUserResultWorkDateListParams.PRODUCTION_RATE, line.getValue(KEY_LST_PRODUCTION_RATE));
                expparam.set(PCTUserResultWorkDateListParams.HEADS, line.getValue(KEY_LST_WORKER_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.CNT, line.getValue(KEY_LST_TERMINAL_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.START_DATE, line.getValue(KEY_LST_START_TIME));
                expparam.set(PCTUserResultWorkDateListParams.END_DATE, line.getValue(KEY_LST_END_TIME));
                expparam.set(PCTUserResultWorkDateListParams.WORK_TIME, line.getValue(KEY_LST_OPERATE_TIME));
                expparam.set(PCTUserResultWorkDateListParams.ORDER_CNT, line.getValue(KEY_LST_ORDER_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.ORDER_CNT_PER_HOUR, line.getValue(KEY_LST_ORDER_COUNT_PER_HOUR));
                expparam.set(PCTUserResultWorkDateListParams.BOX_CNT, line.getValue(KEY_LST_BOX_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.BOX_CNT_PER_HOUR, line.getValue(KEY_LST_BOX_COUNT_PER_HOUR));
                expparam.set(PCTUserResultWorkDateListParams.LINE_CNT, line.getValue(KEY_LST_LINE_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.LINE_CNT_PER_HOUR, line.getValue(KEY_LST_LINE_COUNT_PER_HOUR));
                expparam.set(PCTUserResultWorkDateListParams.LOT_CNT, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PCTUserResultWorkDateListParams.LOT_CNT_PER_HOUR, line.getValue(KEY_LST_LOT_QTY_PER_HOUR));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_CNT, line.getValue(KEY_LST_PIECE_QTY));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_CNT_PER_HOUR, line.getValue(KEY_LST_PIECE_QTY_PER_HOUR));
                expparam.set(PCTUserResultWorkDateListParams.BOX_PER_ORDER, line.getValue(KEY_LST_BOX_COUNT_PER_ORDER));
                expparam.set(PCTUserResultWorkDateListParams.LINE_PER_ORDER, line.getValue(KEY_LST_LINE_COUNT_PER_ORDER));
                expparam.set(PCTUserResultWorkDateListParams.LOT_PER_LINE, line.getValue(KEY_LST_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.PIECE_PER_LINE, line.getValue(KEY_LST_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PCTUserResultWorkDateListParams.MISS, line.getValue(KEY_LST_MISS_RATE));
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
    private void btn_Close_U_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Close_D_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();

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

// $Id: LstPCTUserResultWorkerBusiness.java 7707 2010-03-20 07:23:11Z okayama $
package jp.co.daifuku.pcart.retrieval.listbox.pctuserresultworker;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultWorkerDASCHParams.*;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultWorkerDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultWorkerDASCHParams;
import jp.co.daifuku.pcart.retrieval.exporter.PctUserResultWorkerListParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * ユーザ別実績照会の画面処理を行います。
 *
 * @version $Revision: 7707 $, $Date:: 2010-03-20 16:23:11 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class LstPCTUserResultWorkerBusiness
        extends LstPCTUserResultWorker
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_SearchCondition(HIDDEN_CONSIGNOR_NAME) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_NAME =
            new ListCellKey("HIDDEN_CONSIGNOR_NAME", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_AREA_NAME) */
    private static final ListCellKey KEY_HIDDEN_AREA_NAME =
            new ListCellKey("HIDDEN_AREA_NAME", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_WORK_DAY_FROM) */
    private static final ListCellKey KEY_HIDDEN_WORK_DAY_FROM =
            new ListCellKey("HIDDEN_WORK_DAY_FROM", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_WORKDAY_TO) */
    private static final ListCellKey KEY_HIDDEN_WORK_DAY_TO =
            new ListCellKey("HIDDEN_WORKDAY_TO", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_WEEK_DAY) */
    private static final ListCellKey KEY_HIDDEN_WEEK_DAY =
            new ListCellKey("HIDDEN_WEEK_DAY", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_BATCH_NO) */
    private static final ListCellKey KEY_HIDDEN_BATCH_NO =
            new ListCellKey("HIDDEN_BATCH_NO", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_AREA_NO) */
    private static final ListCellKey KEY_HIDDEN_AREA_NO =
            new ListCellKey("HIDDEN_AREA_NO", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_LEVEL) */
    private static final ListCellKey KEY_HIDDEN_LEVEL =
            new ListCellKey("HIDDEN_LEVEL", new StringCellColumn(), false, false);

    /** lst_SearchCondition(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE =
            new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_1) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_1 =
            new ListCellKey("LST_SEARCH_CONDITION_1", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_2) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_2 =
            new ListCellKey("LST_SEARCH_CONDITION_2", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_3) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_3 =
            new ListCellKey("LST_SEARCH_CONDITION_3", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_SEARCH_CONDITION_4) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_4 =
            new ListCellKey("LST_SEARCH_CONDITION_4", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_1) */
    private static final ListCellKey KEY_LST_AVERAGE_1 =
            new ListCellKey("LST_AVERAGE_1", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_2) */
    private static final ListCellKey KEY_LST_AVERAGE_2 =
            new ListCellKey("LST_AVERAGE_2", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_3) */
    private static final ListCellKey KEY_LST_AVERAGE_3 =
            new ListCellKey("LST_AVERAGE_3", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_4) */
    private static final ListCellKey KEY_LST_AVERAGE_4 =
            new ListCellKey("LST_AVERAGE_4", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_5) */
    private static final ListCellKey KEY_LST_AVERAGE_5 =
            new ListCellKey("LST_AVERAGE_5", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_6) */
    private static final ListCellKey KEY_LST_AVERAGE_6 =
            new ListCellKey("LST_AVERAGE_6", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_7) */
    private static final ListCellKey KEY_LST_AVERAGE_7 =
            new ListCellKey("LST_AVERAGE_7", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_8) */
    private static final ListCellKey KEY_LST_AVERAGE_8 =
            new ListCellKey("LST_AVERAGE_8", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_9) */
    private static final ListCellKey KEY_LST_AVERAGE_9 =
            new ListCellKey("LST_AVERAGE_9", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_10) */
    private static final ListCellKey KEY_LST_AVERAGE_10 =
            new ListCellKey("LST_AVERAGE_10", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_11) */
    private static final ListCellKey KEY_LST_AVERAGE_11 =
            new ListCellKey("LST_AVERAGE_11", new StringCellColumn(), true, false);

    /** lst_Header(LST_AVERAGE_12) */
    private static final ListCellKey KEY_LST_AVERAGE_12 =
            new ListCellKey("LST_AVERAGE_12", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_RANK) */
    private static final ListCellKey KEY_LST_AVG_RANK =
            new ListCellKey("LST_AVG_RANK", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_PRODUCTION_RATE) */
    private static final ListCellKey KEY_LST_AVG_PRODUCTION_RATE =
            new ListCellKey("LST_AVG_PRODUCTION_RATE", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_ALL_OPERATE_TIME) */
    private static final ListCellKey KEY_LST_AVG_ALL_OPERATE_TIME =
            new ListCellKey("LST_AVG_ALL_OPERATE_TIME", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_REAL_OPERATE_TIME) */
    private static final ListCellKey KEY_LST_AVG_REAL_OPERATE_TIME =
            new ListCellKey("LST_AVG_REAL_OPERATE_TIME", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_COLLECT_TIME) */
    private static final ListCellKey KEY_LST_AVG_COLLECT_TIME =
            new ListCellKey("LST_AVG_COLLECT_TIME", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_STOP_TIME) */
    private static final ListCellKey KEY_LST_AVG_STOP_TIME =
            new ListCellKey("LST_AVG_STOP_TIME", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_OPERATE_TIME) */
    private static final ListCellKey KEY_LST_AVG_OPERATE_TIME =
            new ListCellKey("LST_AVG_OPERATE_TIME", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_AVG_ORDER_COUNT =
            new ListCellKey("LST_AVG_ORDER_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_ORDER_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_AVG_ORDER_COUNT_PER_HOUR =
            new ListCellKey("LST_AVG_ORDER_COUNT_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_BOX_COUNT) */
    private static final ListCellKey KEY_LST_AVG_BOX_COUNT =
            new ListCellKey("LST_AVG_BOX_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_BOX_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_AVG_BOX_COUNT_PER_HOUR =
            new ListCellKey("LST_AVG_BOX_COUNT_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_LINE_COUNT) */
    private static final ListCellKey KEY_LST_AVG_LINE_COUNT =
            new ListCellKey("LST_AVG_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_LINE_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_AVG_LINE_COUNT_PER_HOUR =
            new ListCellKey("LST_AVG_LINE_COUNT_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_LOT_QTY) */
    private static final ListCellKey KEY_LST_AVG_LOT_QTY =
            new ListCellKey("LST_AVG_LOT_QTY", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_LOT_QTY_PER_HOUR) */
    private static final ListCellKey KEY_LST_AVG_LOT_QTY_PER_HOUR =
            new ListCellKey("LST_AVG_LOT_QTY_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_PIECE_QTY) */
    private static final ListCellKey KEY_LST_AVG_PIECE_QTY =
            new ListCellKey("LST_AVG_PIECE_QTY", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_PIECE_QTY_PER_HOUR) */
    private static final ListCellKey KEY_LST_AVG_PIECE_QTY_PER_HOUR =
            new ListCellKey("LST_AVG_PIECE_QTY_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_BOX_COUNT_PER_ORDER) */
    private static final ListCellKey KEY_LST_AVG_BOX_COUNT_PER_ORDER =
            new ListCellKey("LST_AVG_BOX_COUNT_PER_ORDER", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_LINE_COUNT_PER_ORDER) */
    private static final ListCellKey KEY_LST_AVG_LINE_COUNT_PER_ORDER =
            new ListCellKey("LST_AVG_LINE_COUNT_PER_ORDER", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_LOT_QTY_PER_LINE_COUNT) */
    private static final ListCellKey KEY_LST_AVG_LOT_QTY_PER_LINE_COUNT =
            new ListCellKey("LST_AVG_LOT_QTY_PER_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_PIECE_QTY_PER_LINE_COUNT) */
    private static final ListCellKey KEY_LST_AVG_PIECE_QTY_PER_LINE_COUNT =
            new ListCellKey("LST_AVG_PIECE_QTY_PER_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerAvg(LST_AVG_MISS_RATE) */
    private static final ListCellKey KEY_LST_AVG_MISS_RATE =
            new ListCellKey("LST_AVG_MISS_RATE", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_USER_ID) */
    private static final ListCellKey KEY_LST_USER_ID =
            new ListCellKey("LST_USER_ID", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME =
            new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_LEVEL) */
    private static final ListCellKey KEY_LST_LEVEL = new ListCellKey("LST_LEVEL", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_RANK) */
    private static final ListCellKey KEY_LST_RANK = new ListCellKey("LST_RANK", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_PRODUCTION_RATE) */
    private static final ListCellKey KEY_LST_PRODUCTION_RATE =
            new ListCellKey("LST_PRODUCTION_RATE", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_START_TIME) */
    private static final ListCellKey KEY_LST_START_TIME =
            new ListCellKey("LST_START_TIME", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_SEC), true, false);

    /** lst_UserResultWorkerList(LST_END_TIME) */
    private static final ListCellKey KEY_LST_END_TIME =
            new ListCellKey("LST_END_TIME", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_SEC), true, false);

    /** lst_UserResultWorkerList(LST_ALL_OPERATE_TIME) */
    private static final ListCellKey KEY_LST_ALL_OPERATE_TIME =
            new ListCellKey("LST_ALL_OPERATE_TIME", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_REAL_OPERATE_TIME) */
    private static final ListCellKey KEY_LST_REAL_OPERATE_TIME =
            new ListCellKey("LST_REAL_OPERATE_TIME", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_COLLECT_TIME) */
    private static final ListCellKey KEY_LST_COLLECT_TIME =
            new ListCellKey("LST_COLLECT_TIME", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_STOP_TIME) */
    private static final ListCellKey KEY_LST_STOP_TIME =
            new ListCellKey("LST_STOP_TIME", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_OPERATE_TIME) */
    private static final ListCellKey KEY_LST_OPERATE_TIME =
            new ListCellKey("LST_OPERATE_TIME", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_ORDER_COUNT =
            new ListCellKey("LST_ORDER_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_ORDER_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_PER_HOUR =
            new ListCellKey("LST_ORDER_COUNT_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_BOX_COUNT) */
    private static final ListCellKey KEY_LST_BOX_COUNT =
            new ListCellKey("LST_BOX_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_BOX_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_BOX_COUNT_PER_HOUR =
            new ListCellKey("LST_BOX_COUNT_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LINE_COUNT =
            new ListCellKey("LST_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_LINE_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_LINE_COUNT_PER_HOUR =
            new ListCellKey("LST_LINE_COUNT_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_LOT_QTY) */
    private static final ListCellKey KEY_LST_LOT_QTY =
            new ListCellKey("LST_LOT_QTY", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_LOT_QTY_PER_HOUR) */
    private static final ListCellKey KEY_LST_LOT_QTY_PER_HOUR =
            new ListCellKey("LST_LOT_QTY_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PIECE_QTY =
            new ListCellKey("LST_PIECE_QTY", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_PIECE_QTY_PER_HOUR) */
    private static final ListCellKey KEY_LST_PIECE_QTY_PER_HOUR =
            new ListCellKey("LST_PIECE_QTY_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_BOX_COUNT_PER_ORDER) */
    private static final ListCellKey KEY_LST_BOX_COUNT_PER_ORDER =
            new ListCellKey("LST_BOX_COUNT_PER_ORDER", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_LINE_COUNT_PER_ORDER) */
    private static final ListCellKey KEY_LST_LINE_COUNT_PER_ORDER =
            new ListCellKey("LST_LINE_COUNT_PER_ORDER", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_LOT_QTY_PER_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LOT_QTY_PER_LINE_COUNT =
            new ListCellKey("LST_LOT_QTY_PER_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_PIECE_QTY_PER_LINE_COUNT) */
    private static final ListCellKey KEY_LST_PIECE_QTY_PER_LINE_COUNT =
            new ListCellKey("LST_PIECE_QTY_PER_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_MISS_RATE) */
    private static final ListCellKey KEY_LST_MISS_RATE =
            new ListCellKey("LST_MISS_RATE", new StringCellColumn(), true, false);

    /** lst_SearchCondition kyes */
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

    /** lst_Header kyes */
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
            KEY_LST_AVERAGE_12,
    };

    /** lst_UserResultWorkerAvg kyes */
    private static final ListCellKey[] LST_USERRESULTWORKERAVG_KEYS = {
            KEY_LST_AVG_RANK,
            KEY_LST_AVG_ALL_OPERATE_TIME,
            KEY_LST_AVG_COLLECT_TIME,
            KEY_LST_AVG_OPERATE_TIME,
            KEY_LST_AVG_ORDER_COUNT,
            KEY_LST_AVG_BOX_COUNT,
            KEY_LST_AVG_LINE_COUNT,
            KEY_LST_AVG_LOT_QTY,
            KEY_LST_AVG_PIECE_QTY,
            KEY_LST_AVG_BOX_COUNT_PER_ORDER,
            KEY_LST_AVG_LOT_QTY_PER_LINE_COUNT,
            KEY_LST_AVG_MISS_RATE,
            KEY_LST_AVG_PRODUCTION_RATE,
            KEY_LST_AVG_REAL_OPERATE_TIME,
            KEY_LST_AVG_STOP_TIME,
            KEY_LST_AVG_ORDER_COUNT_PER_HOUR,
            KEY_LST_AVG_BOX_COUNT_PER_HOUR,
            KEY_LST_AVG_LINE_COUNT_PER_HOUR,
            KEY_LST_AVG_LOT_QTY_PER_HOUR,
            KEY_LST_AVG_PIECE_QTY_PER_HOUR,
            KEY_LST_AVG_LINE_COUNT_PER_ORDER,
            KEY_LST_AVG_PIECE_QTY_PER_LINE_COUNT,
    };

    /** lst_UserResultWorkerList kyes */
    private static final ListCellKey[] LST_USERRESULTWORKERLIST_KEYS = {
            KEY_LST_USER_ID,
            KEY_LST_LEVEL,
            KEY_LST_RANK,
            KEY_LST_START_TIME,
            KEY_LST_ALL_OPERATE_TIME,
            KEY_LST_COLLECT_TIME,
            KEY_LST_OPERATE_TIME,
            KEY_LST_ORDER_COUNT,
            KEY_LST_BOX_COUNT,
            KEY_LST_LINE_COUNT,
            KEY_LST_LOT_QTY,
            KEY_LST_PIECE_QTY,
            KEY_LST_BOX_COUNT_PER_ORDER,
            KEY_LST_LOT_QTY_PER_LINE_COUNT,
            KEY_LST_MISS_RATE,
            KEY_LST_USER_NAME,
            KEY_LST_PRODUCTION_RATE,
            KEY_LST_END_TIME,
            KEY_LST_REAL_OPERATE_TIME,
            KEY_LST_STOP_TIME,
            KEY_LST_ORDER_COUNT_PER_HOUR,
            KEY_LST_BOX_COUNT_PER_HOUR,
            KEY_LST_LINE_COUNT_PER_HOUR,
            KEY_LST_LOT_QTY_PER_HOUR,
            KEY_LST_PIECE_QTY_PER_HOUR,
            KEY_LST_LINE_COUNT_PER_ORDER,
            KEY_LST_PIECE_QTY_PER_LINE_COUNT,
    };


    // DFKLOOK:ここから修正
    /**
     * 検索条件用リスト列番号用
     */
    protected static final int S_LIST1 = 1;

    /**
     * 検索条件用リスト列番号用
     */
    protected static final int S_LIST2 = 2;

    /**
     * 検索条件用リスト列番号用
     */
    protected static final int S_LIST3 = 3;

    /**
     * 検索条件用リスト列番号用
     */
    protected static final int S_LIST4 = 4;

    /**
     * 検索条件用リスト列番号用
     */
    protected static final int S_LIST5 = 5;

    /**
     * 平均リスト情報
     */
    protected interface ListAvg
    {
        /**
         * 検索結果表示用リスト表示用 ランク
         */
        public static final int RANK = 1;

        /**
         * 検索結果表示用リスト表示用 生産率
         */
        public static final int PRODUCTION_RATE = 13;

        /**
         * 検索結果表示用リスト表示用 総稼働時間
         */
        public static final int TOTAL_TIME = 2;

        /**
         * 検索結果表示用リスト表示用 実稼働時間
         */
        public static final int REAL_TIME = 14;

        /**
         * 検索結果表示用リスト表示用 集品時間
         */
        public static final int COLLECT_TIME = 3;

        /**
         * 検索結果表示用リスト表示用 中断時間
         */
        public static final int STOP_TIME = 15;

        /**
         * 検索結果表示用リスト表示用 作業時間
         */
        public static final int TOTAL_WORK_TIME = 4;

        /**
         * 検索結果表示用リスト表示用 オーダー数
         */
        public static final int ORDER_CNT = 5;

        /**
         * 検索結果表示用リスト表示用 オーダー数/H
         */
        public static final int ORDER_CNT_PER_HOUR = 16;

        /**
         * 検索結果表示用リスト表示用 箱数
         */
        public static final int BOX_CNT = 6;

        /**
         * 検索結果表示用リスト表示用 箱数/H
         */
        public static final int BOX_CNT_PER_HOUR = 17;

        /**
         * 検索結果表示用リスト表示用 行数
         */
        public static final int LINE_CNT = 7;

        /**
         * 検索結果表示用リスト表示用 行数/H
         */
        public static final int LINE_CNT_PER_HOUR = 18;

        /**
         * 検索結果表示用リスト表示用 数量（ロット）
         */
        public static final int LOT_QTY = 8;

        /**
         * 検索結果表示用リスト表示用 数量（ロット）/H
         */
        public static final int LOT_QTY_PER_HOUR = 19;

        /**
         * 検索結果表示用リスト表示用 数量（バラ）
         */
        public static final int PIECE_QTY = 9;

        /**
         * 検索結果表示用リスト表示用 数量（バラ）/H
         */
        public static final int PIECE_QTY_PER_HOUR = 20;

        /**
         * 検索結果表示用リスト表示用 箱/オーダー
         */
        public static final int BOX_CNT_PER_ORDER = 10;

        /**
         * 検索結果表示用リスト表示用 行/オーダー
         */
        public static final int LINE_CNT_PER_ORDER = 21;

        /**
         * 検索結果表示用リスト表示用 ロット/行
         */
        public static final int LOT_QTY_PER_LINE = 11;

        /**
         * 検索結果表示用リスト表示用 バラ/行
         */
        public static final int PIECE_QTY_PER_LINE = 22;

        /**
         * 検索結果表示用リスト表示用 ミス率
         */
        public static final int MISS_RATE = 12;
    }

    // DFKLOOK:ここまで修正

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

    /** ListCellModel lst_UserResultWorkerAvg */
    private ListCellModel _lcm_lst_UserResultWorkerAvg;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_UserResultWorkerList */
    private ListCellModel _lcm_lst_UserResultWorkerList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstPCTUserResultWorkerBusiness()
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
    public void pgr_U_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        _lcm_lst_Header.setToolTipVisible(KEY_LST_AVERAGE_12, false);

        // initialize lst_UserResultWorkerAvg.
        _lcm_lst_UserResultWorkerAvg = new ListCellModel(lst_UserResultWorkerAvg, LST_USERRESULTWORKERAVG_KEYS, locale);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_RANK, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_PRODUCTION_RATE, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_ALL_OPERATE_TIME, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_REAL_OPERATE_TIME, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_COLLECT_TIME, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_STOP_TIME, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_OPERATE_TIME, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_ORDER_COUNT, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_ORDER_COUNT_PER_HOUR, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_BOX_COUNT, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_BOX_COUNT_PER_HOUR, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_LINE_COUNT, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_LINE_COUNT_PER_HOUR, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_LOT_QTY, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_LOT_QTY_PER_HOUR, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_PIECE_QTY, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_PIECE_QTY_PER_HOUR, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_BOX_COUNT_PER_ORDER, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_LINE_COUNT_PER_ORDER, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_LOT_QTY_PER_LINE_COUNT, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_PIECE_QTY_PER_LINE_COUNT, false);
        _lcm_lst_UserResultWorkerAvg.setToolTipVisible(KEY_LST_AVG_MISS_RATE, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[] {
                pgr_U,
                pgr_D
        }, locale);

        // initialize lst_UserResultWorkerList.
        _lcm_lst_UserResultWorkerList =
                new ListCellModel(lst_UserResultWorkerList, LST_USERRESULTWORKERLIST_KEYS, locale);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_USER_ID, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_USER_NAME, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_LEVEL, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_RANK, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_PRODUCTION_RATE, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_START_TIME, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_END_TIME, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_ALL_OPERATE_TIME, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_REAL_OPERATE_TIME, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_COLLECT_TIME, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_STOP_TIME, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_OPERATE_TIME, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_ORDER_COUNT, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_ORDER_COUNT_PER_HOUR, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_BOX_COUNT, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_BOX_COUNT_PER_HOUR, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_LINE_COUNT, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_LINE_COUNT_PER_HOUR, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_LOT_QTY, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_LOT_QTY_PER_HOUR, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_PIECE_QTY, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_PIECE_QTY_PER_HOUR, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_BOX_COUNT_PER_ORDER, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_LINE_COUNT_PER_ORDER, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_LOT_QTY_PER_LINE_COUNT, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_PIECE_QTY_PER_LINE_COUNT, true);
        _lcm_lst_UserResultWorkerList.setToolTipVisible(KEY_LST_MISS_RATE, true);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_UserResultWorkerList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0033", KEY_LST_USER_NAME);
        line.addToolTip("LBL-P0075", KEY_LST_PIECE_QTY);

        // DFKLOOK:ここから修正
        line.addToolTip("LBL-P0100", KEY_LST_PIECE_QTY_PER_HOUR);
        // DFKLOOK:ここまで修正

        line.addToolTip("LBL-P0076", KEY_LST_BOX_COUNT_PER_ORDER);
        line.addToolTip("LBL-P0077", KEY_LST_LINE_COUNT_PER_ORDER);
        line.addToolTip("LBL-P0078", KEY_LST_LOT_QTY_PER_LINE_COUNT);
        line.addToolTip("LBL-P0079", KEY_LST_PIECE_QTY_PER_LINE_COUNT);
        line.addToolTip("LBL-P0080", KEY_LST_MISS_RATE);
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

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        LstPCTUserResultWorkerDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstPCTUserResultWorkerDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstPCTUserResultWorkerDASCHParams inparam = new LstPCTUserResultWorkerDASCHParams();
            LstPCTUserResultWorkerParams requestParam = new LstPCTUserResultWorkerParams(request);
            inparam.set(LstPCTUserResultWorkerDASCHParams.WORKDAY_FROM,
                    requestParam.get(LstPCTUserResultWorkerParams.WORKDAY_FROM));
            inparam.set(LstPCTUserResultWorkerDASCHParams.WORKDAY_TO,
                    requestParam.get(LstPCTUserResultWorkerParams.WORKDAY_TO));
            inparam.set(LstPCTUserResultWorkerDASCHParams.MONDAY, requestParam.get(LstPCTUserResultWorkerParams.MONDAY));
            inparam.set(LstPCTUserResultWorkerDASCHParams.TUESDAY,
                    requestParam.get(LstPCTUserResultWorkerParams.TUESDAY));
            inparam.set(LstPCTUserResultWorkerDASCHParams.WEDNESDAY,
                    requestParam.get(LstPCTUserResultWorkerParams.WEDNESDAY));
            inparam.set(LstPCTUserResultWorkerDASCHParams.THURSDAY,
                    requestParam.get(LstPCTUserResultWorkerParams.THURSDAY));
            inparam.set(LstPCTUserResultWorkerDASCHParams.FRIDAY, requestParam.get(LstPCTUserResultWorkerParams.FRIDAY));
            inparam.set(LstPCTUserResultWorkerDASCHParams.SATURDAY,
                    requestParam.get(LstPCTUserResultWorkerParams.SATURDAY));
            inparam.set(LstPCTUserResultWorkerDASCHParams.SUNDAY, requestParam.get(LstPCTUserResultWorkerParams.SUNDAY));
            inparam.set(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_CODE,
                    requestParam.get(LstPCTUserResultWorkerParams.CONSIGNOR_CODE));
            inparam.set(LstPCTUserResultWorkerDASCHParams.AREA_NO,
                    requestParam.get(LstPCTUserResultWorkerParams.AREA_NO));
            inparam.set(LstPCTUserResultWorkerDASCHParams.BATCH_NO,
                    requestParam.get(LstPCTUserResultWorkerParams.BATCH_NO));
            inparam.set(LstPCTUserResultWorkerDASCHParams.LEVEL_A,
                    requestParam.get(LstPCTUserResultWorkerParams.LEVEL_A));
            inparam.set(LstPCTUserResultWorkerDASCHParams.LEVEL_B,
                    requestParam.get(LstPCTUserResultWorkerParams.LEVEL_B));
            inparam.set(LstPCTUserResultWorkerDASCHParams.LEVEL_C,
                    requestParam.get(LstPCTUserResultWorkerParams.LEVEL_C));
            inparam.set(LstPCTUserResultWorkerDASCHParams.COLLECT_CONDITION,
                    requestParam.get(LstPCTUserResultWorkerParams.COLLECT_CONDITION));
            inparam.set(LstPCTUserResultWorkerDASCHParams.DISPLAY_RANK,
                    requestParam.get(LstPCTUserResultWorkerParams.DISPLAY_RANK));
            inparam.set(LstPCTUserResultWorkerDASCHParams.USEDAY_OF_WEEK_FLAG,
                    requestParam.get(LstPCTUserResultWorkerParams.USEDAY_OF_WEEK_FLAG));

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_UserResultWorkerList.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");

                // DFKLOOK:ここから修正
                // ボタン押下不可
                setBtnEnable();

                // 条件リストセル
                lst_SearchCondition_Make(inparam, conn, "");

                // 平均リストセルクリア
                setClearListCell();
                // DFKLOOK:ここまで修正

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

            // DFKLOOK:ここから修正
            // 平均リストセルフラグ
            boolean isSet = true;
            // DFKLOOK:ここまで修正

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_UserResultWorkerList.getNewLine();

                // DFKLOOK:ここから修正
                if (isSet)
                {
                    // 条件リストセル
                    lst_SearchCondition_Make(inparam, conn,
                            outparam.getString(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_NAME));

                    // 平均リストセル
                    setListAvg(outparam);
                    isSet = false;
                }
                // DFKLOOK:ここまで修正

                line.setValue(KEY_LST_USER_ID, outparam.get(LstPCTUserResultWorkerDASCHParams.USER_ID));
                line.setValue(KEY_LST_USER_NAME, outparam.get(LstPCTUserResultWorkerDASCHParams.USER_NAME));
                line.setValue(KEY_LST_LEVEL, outparam.get(LstPCTUserResultWorkerDASCHParams.LEVEL));
                line.setValue(KEY_LST_RANK, outparam.get(LstPCTUserResultWorkerDASCHParams.RANK));
                line.setValue(KEY_LST_PRODUCTION_RATE, outparam.get(LstPCTUserResultWorkerDASCHParams.PRODUCTION_RATE));
                line.setValue(KEY_LST_START_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.START_TIME));
                line.setValue(KEY_LST_END_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.END_TIME));
                line.setValue(KEY_LST_ALL_OPERATE_TIME,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.ALL_OPERATE_TIME));
                line.setValue(KEY_LST_REAL_OPERATE_TIME,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.REAL_OPERATE_TIME));
                line.setValue(KEY_LST_COLLECT_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.COLLECT_TIME));
                line.setValue(KEY_LST_STOP_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.STOP_TIME));
                line.setValue(KEY_LST_OPERATE_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.OPERATE_TIME));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(LstPCTUserResultWorkerDASCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_ORDER_COUNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.ORDER_COUNT_PER_HOUR));
                line.setValue(KEY_LST_BOX_COUNT, outparam.get(LstPCTUserResultWorkerDASCHParams.BOX_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.BOX_COUNT_PER_HOUR));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(LstPCTUserResultWorkerDASCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LINE_COUNT_PER_HOUR));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(LstPCTUserResultWorkerDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_LOT_QTY_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LOT_QTY_PER_HOUR));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(LstPCTUserResultWorkerDASCHParams.PIECE_QTY));
                line.setValue(KEY_LST_PIECE_QTY_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.PIECE_QTY_PER_HOUR));
                line.setValue(KEY_LST_BOX_COUNT_PER_ORDER,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.BOX_COUNT_PER_ORDER));
                line.setValue(KEY_LST_LINE_COUNT_PER_ORDER,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LINE_COUNT_PER_ORDER));
                line.setValue(KEY_LST_LOT_QTY_PER_LINE_COUNT,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LOT_QTY_PER_LINE_COUNT));
                line.setValue(KEY_LST_PIECE_QTY_PER_LINE_COUNT,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                line.setValue(KEY_LST_MISS_RATE, outparam.get(LstPCTUserResultWorkerDASCHParams.MISS_RATE));
                lst_UserResultWorkerList_SetLineToolTip(line);
                _lcm_lst_UserResultWorkerList.add(line);
            }

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_UserResultWorkerList.clear();

            // DFKLOOK:ここから修正
            // ボタン押下不可
            setBtnEnable();
            // DFKLOOK:ここまで修正
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
    private void page_Load_SetList()
            throws Exception
    {
        LstPCTUserResultWorkerDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstPCTUserResultWorkerDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_UserResultWorkerList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_UserResultWorkerList.getNewLine();
                line.setValue(KEY_LST_USER_ID, outparam.get(LstPCTUserResultWorkerDASCHParams.USER_ID));
                line.setValue(KEY_LST_USER_NAME, outparam.get(LstPCTUserResultWorkerDASCHParams.USER_NAME));
                line.setValue(KEY_LST_LEVEL, outparam.get(LstPCTUserResultWorkerDASCHParams.LEVEL));
                line.setValue(KEY_LST_RANK, outparam.get(LstPCTUserResultWorkerDASCHParams.RANK));
                line.setValue(KEY_LST_PRODUCTION_RATE, outparam.get(LstPCTUserResultWorkerDASCHParams.PRODUCTION_RATE));
                line.setValue(KEY_LST_START_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.START_TIME));
                line.setValue(KEY_LST_END_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.END_TIME));
                line.setValue(KEY_LST_ALL_OPERATE_TIME,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.ALL_OPERATE_TIME));
                line.setValue(KEY_LST_REAL_OPERATE_TIME,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.REAL_OPERATE_TIME));
                line.setValue(KEY_LST_COLLECT_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.COLLECT_TIME));
                line.setValue(KEY_LST_STOP_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.STOP_TIME));
                line.setValue(KEY_LST_OPERATE_TIME, outparam.get(LstPCTUserResultWorkerDASCHParams.OPERATE_TIME));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(LstPCTUserResultWorkerDASCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_ORDER_COUNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.ORDER_COUNT_PER_HOUR));
                line.setValue(KEY_LST_BOX_COUNT, outparam.get(LstPCTUserResultWorkerDASCHParams.BOX_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.BOX_COUNT_PER_HOUR));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(LstPCTUserResultWorkerDASCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LINE_COUNT_PER_HOUR));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(LstPCTUserResultWorkerDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_LOT_QTY_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LOT_QTY_PER_HOUR));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(LstPCTUserResultWorkerDASCHParams.PIECE_QTY));
                line.setValue(KEY_LST_PIECE_QTY_PER_HOUR,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.PIECE_QTY_PER_HOUR));
                line.setValue(KEY_LST_BOX_COUNT_PER_ORDER,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.BOX_COUNT_PER_ORDER));
                line.setValue(KEY_LST_LINE_COUNT_PER_ORDER,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LINE_COUNT_PER_ORDER));
                line.setValue(KEY_LST_LOT_QTY_PER_LINE_COUNT,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.LOT_QTY_PER_LINE_COUNT));
                line.setValue(KEY_LST_PIECE_QTY_PER_LINE_COUNT,
                        outparam.get(LstPCTUserResultWorkerDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                line.setValue(KEY_LST_MISS_RATE, outparam.get(LstPCTUserResultWorkerDASCHParams.MISS_RATE));
                lst_UserResultWorkerList_SetLineToolTip(line);
                _lcm_lst_UserResultWorkerList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_UserResultWorkerList.clear();
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
            exporter = factory.newPVExporter("PctUserResultWorkerList", getSession());
            File downloadFile = exporter.open();

            // DFKLOOK:ここから修正
            // 検索条件セット
            ListCellLine searchLine = _lcm_lst_SearchCondition.get(1);
            String searchWorkDayFrom = searchLine.getStringValue(KEY_HIDDEN_WORK_DAY_FROM);
            String searchWorkDayTo = searchLine.getStringValue(KEY_HIDDEN_WORK_DAY_TO);
            String searchWeekDay = searchLine.getStringValue(KEY_HIDDEN_WEEK_DAY);
            String searchBatchNo = searchLine.getStringValue(KEY_HIDDEN_BATCH_NO);
            String searchLevel = searchLine.getStringValue(KEY_HIDDEN_LEVEL);
            String searchConsignorCode = searchLine.getStringValue(KEY_HIDDEN_CONSIGNOR_CODE);
            String searchConsignorName = searchLine.getStringValue(KEY_HIDDEN_CONSIGNOR_NAME);
            String searchAreaNo = searchLine.getStringValue(KEY_HIDDEN_AREA_NO);
            String searchAreaName = searchLine.getStringValue(KEY_HIDDEN_AREA_NAME);

            // 平均値セット
            ListCellLine avghLine = _lcm_lst_UserResultWorkerAvg.get(1);
            String avgRank = avghLine.getStringValue(KEY_LST_AVG_RANK);
            String avgProducate = avghLine.getStringValue(KEY_LST_AVG_PRODUCTION_RATE);
            String avgTotalTime = avghLine.getStringValue(KEY_LST_AVG_ALL_OPERATE_TIME);
            String avgRealTime = avghLine.getStringValue(KEY_LST_AVG_REAL_OPERATE_TIME);
            String avgCollTime = avghLine.getStringValue(KEY_LST_AVG_COLLECT_TIME);
            String avgStopTime = avghLine.getStringValue(KEY_LST_AVG_STOP_TIME);
            String avgOpeTime = avghLine.getStringValue(KEY_LST_AVG_OPERATE_TIME);
            String avgOderCnt = avghLine.getStringValue(KEY_LST_AVG_ORDER_COUNT);
            String avgOrderCntPerH = avghLine.getStringValue(KEY_LST_AVG_ORDER_COUNT_PER_HOUR);
            String avgBoxCnt = avghLine.getStringValue(KEY_LST_AVG_BOX_COUNT);
            String avgBoxCntPerH = avghLine.getStringValue(KEY_LST_AVG_BOX_COUNT_PER_HOUR);
            String avgLineCnt = avghLine.getStringValue(KEY_LST_AVG_LINE_COUNT);
            String avgLineCntPerH = avghLine.getStringValue(KEY_LST_AVG_LINE_COUNT_PER_HOUR);
            String avgLotQty = avghLine.getStringValue(KEY_LST_AVG_LOT_QTY);
            String avgLotQtyPerH = avghLine.getStringValue(KEY_LST_AVG_LOT_QTY_PER_HOUR);
            String avgPeiceQty = avghLine.getStringValue(KEY_LST_AVG_PIECE_QTY);
            String avgPeiceQtyPerH = avghLine.getStringValue(KEY_LST_AVG_PIECE_QTY_PER_HOUR);
            String avgBoxPerOder = avghLine.getStringValue(KEY_LST_AVG_BOX_COUNT_PER_ORDER);
            String avgLinePerOder = avghLine.getStringValue(KEY_LST_AVG_LINE_COUNT_PER_ORDER);
            String avgLotQtyPerLine = avghLine.getStringValue(KEY_LST_AVG_LOT_QTY_PER_LINE_COUNT);
            String avgPeiceQtyPerLine = avghLine.getStringValue(KEY_LST_AVG_PIECE_QTY_PER_LINE_COUNT);
            String avgMissRate = avghLine.getStringValue(KEY_LST_AVG_MISS_RATE);
            // export.
            for (int i = 1; i <= _lcm_lst_UserResultWorkerList.size(); i++)
            {
                ListCellLine line = _lcm_lst_UserResultWorkerList.get(i);
                PctUserResultWorkerListParams expparam = new PctUserResultWorkerListParams();
                expparam.set(PctUserResultWorkerListParams.SYS_DAY, DbDateUtil.getTimeStamp());
                expparam.set(PctUserResultWorkerListParams.SYS_TIME, DbDateUtil.getTimeStamp());
                expparam.set(PctUserResultWorkerListParams.WORK_DAY_FROM, WmsFormatter.toDate(searchWorkDayFrom));
                expparam.set(PctUserResultWorkerListParams.WORK_DAY_TO, WmsFormatter.toDate(searchWorkDayTo));
                expparam.set(PctUserResultWorkerListParams.WEEK_DAY, searchWeekDay);
                expparam.set(PctUserResultWorkerListParams.BATCH_NO, searchBatchNo);
                expparam.set(PctUserResultWorkerListParams.LEVEL, searchLevel);
                expparam.set(PctUserResultWorkerListParams.CONSIGNOR_CODE, searchConsignorCode);
                expparam.set(PctUserResultWorkerListParams.CONSIGNOR_NAME, searchConsignorName);
                expparam.set(PctUserResultWorkerListParams.AREA_NO, searchAreaNo);
                expparam.set(PctUserResultWorkerListParams.AREA_NAME, searchAreaName);
                expparam.set(PctUserResultWorkerListParams.WORKER_NO, line.getValue(KEY_LST_USER_ID));
                expparam.set(PctUserResultWorkerListParams.WORKER_NAME, line.getValue(KEY_LST_USER_NAME));
                expparam.set(PctUserResultWorkerListParams.LEVEL1, line.getValue(KEY_LST_LEVEL));
                expparam.set(PctUserResultWorkerListParams.RANK, line.getValue(KEY_LST_RANK));
                expparam.set(PctUserResultWorkerListParams.PRODUCTION_RATE, line.getValue(KEY_LST_PRODUCTION_RATE));
                expparam.set(PctUserResultWorkerListParams.START_DATE, line.getValue(KEY_LST_START_TIME));
                expparam.set(PctUserResultWorkerListParams.END_DATE, line.getValue(KEY_LST_END_TIME));
                expparam.set(PctUserResultWorkerListParams.TOTAL_TIME, line.getValue(KEY_LST_ALL_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.REAL_TIME, line.getValue(KEY_LST_REAL_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.COLLECT_TIME, line.getValue(KEY_LST_COLLECT_TIME));
                expparam.set(PctUserResultWorkerListParams.STOP_TIME, line.getValue(KEY_LST_STOP_TIME));
                expparam.set(PctUserResultWorkerListParams.WORK_TIME, line.getValue(KEY_LST_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT, line.getValue(KEY_LST_ORDER_COUNT));
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT_PER_HOUR, line.getValue(KEY_LST_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.BOX_CNT, line.getValue(KEY_LST_BOX_COUNT));
                expparam.set(PctUserResultWorkerListParams.BOX_CNT_PER_HOUR, line.getValue(KEY_LST_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.LINE_CNT, line.getValue(KEY_LST_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.LINE_CNT_PER_HOUR, line.getValue(KEY_LST_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.LOT_CNT, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctUserResultWorkerListParams.LOT_CNT_PER_HOUR, line.getValue(KEY_LST_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT, line.getValue(KEY_LST_PIECE_QTY));
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT_PER_HOUR, line.getValue(KEY_LST_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.BOX_PER_ORDER, line.getValue(KEY_LST_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkerListParams.LINE_PER_ORDER, line.getValue(KEY_LST_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkerListParams.LOT_PER_LINE, line.getValue(KEY_LST_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.PIECE_PER_LINE, line.getValue(KEY_LST_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.MISS, line.getValue(KEY_LST_MISS_RATE));
                expparam.set(PctUserResultWorkerListParams.RANK_AVG, avgRank);
                expparam.set(PctUserResultWorkerListParams.PRODUCTION_RATE_AVG, avgProducate);
                expparam.set(PctUserResultWorkerListParams.TOTAL_TIME_AVG, avgTotalTime);
                expparam.set(PctUserResultWorkerListParams.REAL_TIME_AVG, avgRealTime);
                expparam.set(PctUserResultWorkerListParams.COLLECT_TIME_AVG, avgCollTime);
                expparam.set(PctUserResultWorkerListParams.STOP_TIME_AVG, avgStopTime);
                expparam.set(PctUserResultWorkerListParams.WORK_TIME_AVG, avgOpeTime);
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT_AVG, avgOderCnt);
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT_PER_HOUR_AVG, avgOrderCntPerH);
                expparam.set(PctUserResultWorkerListParams.BOX_CNT_AVG, avgBoxCnt);
                expparam.set(PctUserResultWorkerListParams.BOX_CNT_PER_HOUR_AVG, avgBoxCntPerH);
                expparam.set(PctUserResultWorkerListParams.LINE_CNT_AVG, avgLineCnt);
                expparam.set(PctUserResultWorkerListParams.LINE_CNT_PER_HOUR_AVG, avgLineCntPerH);
                expparam.set(PctUserResultWorkerListParams.LOT_CNT_AVG, avgLotQty);
                expparam.set(PctUserResultWorkerListParams.LOT_CNT_PER_HOUR_AVG, avgLotQtyPerH);
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT_AVG, avgPeiceQty);
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT_PER_HOUR_AVG, avgPeiceQtyPerH);
                expparam.set(PctUserResultWorkerListParams.BOX_PER_ORDER_AVG, avgBoxPerOder);
                expparam.set(PctUserResultWorkerListParams.LINE_PER_ORDER_AVG, avgLinePerOder);
                expparam.set(PctUserResultWorkerListParams.LOT_PER_LINE_AVG, avgLotQtyPerLine);
                expparam.set(PctUserResultWorkerListParams.PIECE_PER_LINE_AVG, avgPeiceQtyPerLine);
                expparam.set(PctUserResultWorkerListParams.MISS_AVG, avgMissRate);

                if (!exporter.write(expparam))
                {
                    break;
                }
            }
            // DFKLOOK:ここまで修正

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
            exporter = factory.newPrinterExporter("PctUserResultWorkerList", false);
            exporter.open();

            // DFKLOOK:ここから修正
            // 検索条件セット
            ListCellLine searchLine = _lcm_lst_SearchCondition.get(1);
            String searchWorkDayFrom = searchLine.getStringValue(KEY_HIDDEN_WORK_DAY_FROM);
            String searchWorkDayTo = searchLine.getStringValue(KEY_HIDDEN_WORK_DAY_TO);
            String searchWeekDay = searchLine.getStringValue(KEY_HIDDEN_WEEK_DAY);
            String searchBatchNo = searchLine.getStringValue(KEY_HIDDEN_BATCH_NO);
            String searchLevel = searchLine.getStringValue(KEY_HIDDEN_LEVEL);
            String searchConsignorCode = searchLine.getStringValue(KEY_HIDDEN_CONSIGNOR_CODE);
            String searchConsignorName = searchLine.getStringValue(KEY_HIDDEN_CONSIGNOR_NAME);
            String searchAreaNo = searchLine.getStringValue(KEY_HIDDEN_AREA_NO);
            String searchAreaName = searchLine.getStringValue(KEY_HIDDEN_AREA_NAME);

            // 平均値セット
            ListCellLine avghLine = _lcm_lst_UserResultWorkerAvg.get(1);
            String avgRank = avghLine.getStringValue(KEY_LST_AVG_RANK);
            String avgProducate = avghLine.getStringValue(KEY_LST_AVG_PRODUCTION_RATE);
            String avgTotalTime = avghLine.getStringValue(KEY_LST_AVG_ALL_OPERATE_TIME);
            String avgRealTime = avghLine.getStringValue(KEY_LST_AVG_REAL_OPERATE_TIME);
            String avgCollTime = avghLine.getStringValue(KEY_LST_AVG_COLLECT_TIME);
            String avgStopTime = avghLine.getStringValue(KEY_LST_AVG_STOP_TIME);
            String avgOpeTime = avghLine.getStringValue(KEY_LST_AVG_OPERATE_TIME);
            String avgOderCnt = avghLine.getStringValue(KEY_LST_AVG_ORDER_COUNT);
            String avgOrderCntPerH = avghLine.getStringValue(KEY_LST_AVG_ORDER_COUNT_PER_HOUR);
            String avgBoxCnt = avghLine.getStringValue(KEY_LST_AVG_BOX_COUNT);
            String avgBoxCntPerH = avghLine.getStringValue(KEY_LST_AVG_BOX_COUNT_PER_HOUR);
            String avgLineCnt = avghLine.getStringValue(KEY_LST_AVG_LINE_COUNT);
            String avgLineCntPerH = avghLine.getStringValue(KEY_LST_AVG_LINE_COUNT_PER_HOUR);
            String avgLotQty = avghLine.getStringValue(KEY_LST_AVG_LOT_QTY);
            String avgLotQtyPerH = avghLine.getStringValue(KEY_LST_AVG_LOT_QTY_PER_HOUR);
            String avgPeiceQty = avghLine.getStringValue(KEY_LST_AVG_PIECE_QTY);
            String avgPeiceQtyPerH = avghLine.getStringValue(KEY_LST_AVG_PIECE_QTY_PER_HOUR);
            String avgBoxPerOder = avghLine.getStringValue(KEY_LST_AVG_BOX_COUNT_PER_ORDER);
            String avgLinePerOder = avghLine.getStringValue(KEY_LST_AVG_LINE_COUNT_PER_ORDER);
            String avgLotQtyPerLine = avghLine.getStringValue(KEY_LST_AVG_LOT_QTY_PER_LINE_COUNT);
            String avgPeiceQtyPerLine = avghLine.getStringValue(KEY_LST_AVG_PIECE_QTY_PER_LINE_COUNT);
            String avgMissRate = avghLine.getStringValue(KEY_LST_AVG_MISS_RATE);
            // export.
            for (int i = 1; i <= _lcm_lst_UserResultWorkerList.size(); i++)
            {
                ListCellLine line = _lcm_lst_UserResultWorkerList.get(i);
                PctUserResultWorkerListParams expparam = new PctUserResultWorkerListParams();
                expparam.set(PctUserResultWorkerListParams.SYS_DAY, DbDateUtil.getTimeStamp());
                expparam.set(PctUserResultWorkerListParams.SYS_TIME, DbDateUtil.getTimeStamp());
                expparam.set(PctUserResultWorkerListParams.WORK_DAY_FROM, WmsFormatter.toDate(searchWorkDayFrom));
                expparam.set(PctUserResultWorkerListParams.WORK_DAY_TO, WmsFormatter.toDate(searchWorkDayTo));
                expparam.set(PctUserResultWorkerListParams.WEEK_DAY, searchWeekDay);
                expparam.set(PctUserResultWorkerListParams.BATCH_NO, searchBatchNo);
                expparam.set(PctUserResultWorkerListParams.LEVEL, searchLevel);
                expparam.set(PctUserResultWorkerListParams.CONSIGNOR_CODE, searchConsignorCode);
                expparam.set(PctUserResultWorkerListParams.CONSIGNOR_NAME, searchConsignorName);
                expparam.set(PctUserResultWorkerListParams.AREA_NO, searchAreaNo);
                expparam.set(PctUserResultWorkerListParams.AREA_NAME, searchAreaName);

                expparam.set(PctUserResultWorkerListParams.WORKER_NO, line.getValue(KEY_LST_USER_ID));
                expparam.set(PctUserResultWorkerListParams.WORKER_NAME, line.getValue(KEY_LST_USER_NAME));
                expparam.set(PctUserResultWorkerListParams.LEVEL1, line.getValue(KEY_LST_LEVEL));
                expparam.set(PctUserResultWorkerListParams.RANK, line.getValue(KEY_LST_RANK));
                expparam.set(PctUserResultWorkerListParams.PRODUCTION_RATE, line.getValue(KEY_LST_PRODUCTION_RATE));
                expparam.set(PctUserResultWorkerListParams.START_DATE, line.getValue(KEY_LST_START_TIME));
                expparam.set(PctUserResultWorkerListParams.END_DATE, line.getValue(KEY_LST_END_TIME));
                expparam.set(PctUserResultWorkerListParams.TOTAL_TIME, line.getValue(KEY_LST_ALL_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.REAL_TIME, line.getValue(KEY_LST_REAL_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.COLLECT_TIME, line.getValue(KEY_LST_COLLECT_TIME));
                expparam.set(PctUserResultWorkerListParams.STOP_TIME, line.getValue(KEY_LST_STOP_TIME));
                expparam.set(PctUserResultWorkerListParams.WORK_TIME, line.getValue(KEY_LST_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT, line.getValue(KEY_LST_ORDER_COUNT));
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT_PER_HOUR, line.getValue(KEY_LST_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.BOX_CNT, line.getValue(KEY_LST_BOX_COUNT));
                expparam.set(PctUserResultWorkerListParams.BOX_CNT_PER_HOUR, line.getValue(KEY_LST_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.LINE_CNT, line.getValue(KEY_LST_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.LINE_CNT_PER_HOUR, line.getValue(KEY_LST_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.LOT_CNT, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctUserResultWorkerListParams.LOT_CNT_PER_HOUR, line.getValue(KEY_LST_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT, line.getValue(KEY_LST_PIECE_QTY));
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT_PER_HOUR, line.getValue(KEY_LST_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.BOX_PER_ORDER, line.getValue(KEY_LST_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkerListParams.LINE_PER_ORDER, line.getValue(KEY_LST_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkerListParams.LOT_PER_LINE, line.getValue(KEY_LST_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.PIECE_PER_LINE, line.getValue(KEY_LST_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.MISS, line.getValue(KEY_LST_MISS_RATE));
                expparam.set(PctUserResultWorkerListParams.RANK_AVG, avgRank);
                expparam.set(PctUserResultWorkerListParams.PRODUCTION_RATE_AVG, avgProducate);
                expparam.set(PctUserResultWorkerListParams.TOTAL_TIME_AVG, avgTotalTime);
                expparam.set(PctUserResultWorkerListParams.REAL_TIME_AVG, avgRealTime);
                expparam.set(PctUserResultWorkerListParams.COLLECT_TIME_AVG, avgCollTime);
                expparam.set(PctUserResultWorkerListParams.STOP_TIME_AVG, avgStopTime);
                expparam.set(PctUserResultWorkerListParams.WORK_TIME_AVG, avgOpeTime);
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT_AVG, avgOderCnt);
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT_PER_HOUR_AVG, avgOrderCntPerH);
                expparam.set(PctUserResultWorkerListParams.BOX_CNT_AVG, avgBoxCnt);
                expparam.set(PctUserResultWorkerListParams.BOX_CNT_PER_HOUR_AVG, avgBoxCntPerH);
                expparam.set(PctUserResultWorkerListParams.LINE_CNT_AVG, avgLineCnt);
                expparam.set(PctUserResultWorkerListParams.LINE_CNT_PER_HOUR_AVG, avgLineCntPerH);
                expparam.set(PctUserResultWorkerListParams.LOT_CNT_AVG, avgLotQty);
                expparam.set(PctUserResultWorkerListParams.LOT_CNT_PER_HOUR_AVG, avgLotQtyPerH);
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT_AVG, avgPeiceQty);
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT_PER_HOUR_AVG, avgPeiceQtyPerH);
                expparam.set(PctUserResultWorkerListParams.BOX_PER_ORDER_AVG, avgBoxPerOder);
                expparam.set(PctUserResultWorkerListParams.LINE_PER_ORDER_AVG, avgLinePerOder);
                expparam.set(PctUserResultWorkerListParams.LOT_PER_LINE_AVG, avgLotQtyPerLine);
                expparam.set(PctUserResultWorkerListParams.PIECE_PER_LINE_AVG, avgPeiceQtyPerLine);
                expparam.set(PctUserResultWorkerListParams.MISS_AVG, avgMissRate);

                if (!exporter.write(expparam))
                {
                    break;
                }
            }
            // DFKLOOK:ここまで修正

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
            exporter = factory.newExcelExporter("PctUserResultWorkerList", getSession());
            File downloadFile = exporter.open();

            // DFKLOOK:ここから修正
            // 検索条件セット
            ListCellLine searchLine = _lcm_lst_SearchCondition.get(1);
            String searchWorkDayFrom = searchLine.getStringValue(KEY_HIDDEN_WORK_DAY_FROM);
            String searchWorkDayTo = searchLine.getStringValue(KEY_HIDDEN_WORK_DAY_TO);
            String searchWeekDay = searchLine.getStringValue(KEY_HIDDEN_WEEK_DAY);
            String searchBatchNo = searchLine.getStringValue(KEY_HIDDEN_BATCH_NO);
            String searchLevel = searchLine.getStringValue(KEY_HIDDEN_LEVEL);
            String searchConsignorCode = searchLine.getStringValue(KEY_HIDDEN_CONSIGNOR_CODE);
            String searchConsignorName = searchLine.getStringValue(KEY_HIDDEN_CONSIGNOR_NAME);
            String searchAreaNo = searchLine.getStringValue(KEY_HIDDEN_AREA_NO);
            String searchAreaName = searchLine.getStringValue(KEY_HIDDEN_AREA_NAME);

            // 平均値セット
            ListCellLine avghLine = _lcm_lst_UserResultWorkerAvg.get(1);
            String avgRank = avghLine.getStringValue(KEY_LST_AVG_RANK);
            String avgProducate = avghLine.getStringValue(KEY_LST_AVG_PRODUCTION_RATE);
            String avgTotalTime = avghLine.getStringValue(KEY_LST_AVG_ALL_OPERATE_TIME);
            String avgRealTime = avghLine.getStringValue(KEY_LST_AVG_REAL_OPERATE_TIME);
            String avgCollTime = avghLine.getStringValue(KEY_LST_AVG_COLLECT_TIME);
            String avgStopTime = avghLine.getStringValue(KEY_LST_AVG_STOP_TIME);
            String avgOpeTime = avghLine.getStringValue(KEY_LST_AVG_OPERATE_TIME);
            String avgOderCnt = avghLine.getStringValue(KEY_LST_AVG_ORDER_COUNT);
            String avgOrderCntPerH = avghLine.getStringValue(KEY_LST_AVG_ORDER_COUNT_PER_HOUR);
            String avgBoxCnt = avghLine.getStringValue(KEY_LST_AVG_BOX_COUNT);
            String avgBoxCntPerH = avghLine.getStringValue(KEY_LST_AVG_BOX_COUNT_PER_HOUR);
            String avgLineCnt = avghLine.getStringValue(KEY_LST_AVG_LINE_COUNT);
            String avgLineCntPerH = avghLine.getStringValue(KEY_LST_AVG_LINE_COUNT_PER_HOUR);
            String avgLotQty = avghLine.getStringValue(KEY_LST_AVG_LOT_QTY);
            String avgLotQtyPerH = avghLine.getStringValue(KEY_LST_AVG_LOT_QTY_PER_HOUR);
            String avgPeiceQty = avghLine.getStringValue(KEY_LST_AVG_PIECE_QTY);
            String avgPeiceQtyPerH = avghLine.getStringValue(KEY_LST_AVG_PIECE_QTY_PER_HOUR);
            String avgBoxPerOder = avghLine.getStringValue(KEY_LST_AVG_BOX_COUNT_PER_ORDER);
            String avgLinePerOder = avghLine.getStringValue(KEY_LST_AVG_LINE_COUNT_PER_ORDER);
            String avgLotQtyPerLine = avghLine.getStringValue(KEY_LST_AVG_LOT_QTY_PER_LINE_COUNT);
            String avgPeiceQtyPerLine = avghLine.getStringValue(KEY_LST_AVG_PIECE_QTY_PER_LINE_COUNT);
            String avgMissRate = avghLine.getStringValue(KEY_LST_AVG_MISS_RATE);

            // export.
            for (int i = 1; i <= _lcm_lst_UserResultWorkerList.size(); i++)
            {
                ListCellLine line = _lcm_lst_UserResultWorkerList.get(i);
                PctUserResultWorkerListParams expparam = new PctUserResultWorkerListParams();
                expparam.set(PctUserResultWorkerListParams.WORK_DAY_FROM, WmsFormatter.toDate(searchWorkDayFrom));
                expparam.set(PctUserResultWorkerListParams.WORK_DAY_TO, WmsFormatter.toDate(searchWorkDayTo));
                expparam.set(PctUserResultWorkerListParams.WEEK_DAY, searchWeekDay);
                expparam.set(PctUserResultWorkerListParams.BATCH_NO, searchBatchNo);
                expparam.set(PctUserResultWorkerListParams.CONSIGNOR_CODE, searchConsignorCode);
                expparam.set(PctUserResultWorkerListParams.CONSIGNOR_NAME, searchConsignorName);
                expparam.set(PctUserResultWorkerListParams.AREA_NO, searchAreaNo);
                expparam.set(PctUserResultWorkerListParams.AREA_NAME, searchAreaName);
                expparam.set(PctUserResultWorkerListParams.LEVEL, searchLevel);
                expparam.set(PctUserResultWorkerListParams.WORKER_NO, line.getValue(KEY_LST_USER_ID));
                expparam.set(PctUserResultWorkerListParams.WORKER_NAME, line.getValue(KEY_LST_USER_NAME));
                expparam.set(PctUserResultWorkerListParams.RANK, line.getValue(KEY_LST_RANK));
                expparam.set(PctUserResultWorkerListParams.PRODUCTION_RATE, line.getValue(KEY_LST_PRODUCTION_RATE));
                expparam.set(PctUserResultWorkerListParams.START_DATE, line.getValue(KEY_LST_START_TIME));
                expparam.set(PctUserResultWorkerListParams.END_DATE, line.getValue(KEY_LST_END_TIME));
                expparam.set(PctUserResultWorkerListParams.TOTAL_TIME, line.getValue(KEY_LST_ALL_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.REAL_TIME, line.getValue(KEY_LST_REAL_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.COLLECT_TIME, line.getValue(KEY_LST_COLLECT_TIME));
                expparam.set(PctUserResultWorkerListParams.STOP_TIME, line.getValue(KEY_LST_STOP_TIME));
                expparam.set(PctUserResultWorkerListParams.WORK_TIME, line.getValue(KEY_LST_OPERATE_TIME));
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT, line.getValue(KEY_LST_ORDER_COUNT));
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT_PER_HOUR,
                        line.getValue(KEY_LST_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.BOX_CNT, line.getValue(KEY_LST_BOX_COUNT));
                expparam.set(PctUserResultWorkerListParams.BOX_CNT_PER_HOUR, line.getValue(KEY_LST_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.LINE_CNT, line.getValue(KEY_LST_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.LINE_CNT_PER_HOUR,
                        line.getValue(KEY_LST_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.LOT_CNT, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctUserResultWorkerListParams.LOT_CNT_PER_HOUR, line.getValue(KEY_LST_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT, line.getValue(KEY_LST_PIECE_QTY));
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT_PER_HOUR,
                        line.getValue(KEY_LST_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultWorkerListParams.BOX_PER_ORDER, line.getValue(KEY_LST_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkerListParams.LINE_PER_ORDER, line.getValue(KEY_LST_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultWorkerListParams.LOT_PER_LINE, line.getValue(KEY_LST_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.PIECE_PER_LINE,
                        line.getValue(KEY_LST_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultWorkerListParams.MISS, line.getValue(KEY_LST_MISS_RATE));
                expparam.set(PctUserResultWorkerListParams.RANK_AVG, avgRank);
                expparam.set(PctUserResultWorkerListParams.PRODUCTION_RATE_AVG, avgProducate);
                expparam.set(PctUserResultWorkerListParams.TOTAL_TIME_AVG, avgTotalTime);
                expparam.set(PctUserResultWorkerListParams.REAL_TIME_AVG, avgRealTime);
                expparam.set(PctUserResultWorkerListParams.COLLECT_TIME_AVG, avgCollTime);
                expparam.set(PctUserResultWorkerListParams.STOP_TIME_AVG, avgStopTime);
                expparam.set(PctUserResultWorkerListParams.WORK_TIME_AVG, avgOpeTime);
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT_AVG, avgOderCnt);
                expparam.set(PctUserResultWorkerListParams.ORDER_CNT_PER_HOUR_AVG, avgOrderCntPerH);
                expparam.set(PctUserResultWorkerListParams.BOX_CNT_AVG, avgBoxCnt);
                expparam.set(PctUserResultWorkerListParams.BOX_CNT_PER_HOUR_AVG, avgBoxCntPerH);
                expparam.set(PctUserResultWorkerListParams.LINE_CNT_AVG, avgLineCnt);
                expparam.set(PctUserResultWorkerListParams.LINE_CNT_PER_HOUR_AVG, avgLineCntPerH);
                expparam.set(PctUserResultWorkerListParams.LOT_CNT_AVG, avgLotQty);
                expparam.set(PctUserResultWorkerListParams.LOT_CNT_PER_HOUR_AVG, avgLotQtyPerH);
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT_AVG, avgPeiceQty);
                expparam.set(PctUserResultWorkerListParams.PIECE_CNT_PER_HOUR_AVG, avgPeiceQtyPerH);
                expparam.set(PctUserResultWorkerListParams.BOX_PER_ORDER_AVG, avgBoxPerOder);
                expparam.set(PctUserResultWorkerListParams.LINE_PER_ORDER_AVG, avgLinePerOder);
                expparam.set(PctUserResultWorkerListParams.LOT_PER_LINE_AVG, avgLotQtyPerLine);
                expparam.set(PctUserResultWorkerListParams.PIECE_PER_LINE_AVG, avgPeiceQtyPerLine);
                expparam.set(PctUserResultWorkerListParams.MISS_AVG, avgMissRate);
                expparam.set(PctUserResultWorkerListParams.LEVEL1, line.getValue(KEY_LST_LEVEL));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }
            // DFKLOOK:ここまで修正

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

    // DFKLOOK:ここから修正
    /**
     * 検索条件のリストセルを作成します。
     * 
     * @param inParam 検索条件
     * @param conn コネクション
     * @param consignorName 荷主名称
     * @throws Exception 全ての例外を報告します。
     */
    protected void lst_SearchCondition_Make(Params inParam, Connection conn, String consignorName)
            throws Exception
    {
        try
        {
            // ロケールを取得
            Locale locale = this.getHttpRequest().getLocale();

            // 表示用
            String[] day =
                    WmsFormatter.getFromTo(WmsFormatter.toParamDate(inParam.getDate(WORKDAY_FROM)),
                            WmsFormatter.toParamDate(inParam.getDate(WORKDAY_TO)));

            // 作業日
            String date = "";
            // 作業日(FROM)が空でない場合
            if (!StringUtil.isBlank(day[0]))
            {
                // 作業日(TO)が空でない場合
                if (!StringUtil.isBlank(day[1]))
                {
                    // 作業日～To作業日
                    date =
                            WmsFormatter.toDispDate(day[0], locale) + DispResources.getText("LBL-W9035")
                                    + WmsFormatter.toDispDate(day[1], locale);
                }
                else
                {
                    // 作業日～
                    date = WmsFormatter.toDispDate(day[0], locale) + DispResources.getText("LBL-W9035");
                }
            }
            else
            {
                // 作業日(TO)が空でない場合
                if (!StringUtil.isBlank(day[1]))
                {
                    // ～To作業日
                    date = DispResources.getText("LBL-W9035") + WmsFormatter.toDispDate(day[1], locale);
                }
                else
                {
                    // 空
                    date = "";
                }
            }

            // 曜日
            String dayOfWeek = "";
            // 月曜
            if (inParam.getBoolean(LstPCTUserResultWorkerDASCHParams.MONDAY))
            {
                dayOfWeek += DispResources.getText("CHK-P0001");
            }
            // 火曜
            if (inParam.getBoolean(LstPCTUserResultWorkerDASCHParams.TUESDAY))
            {
                dayOfWeek += DispResources.getText("CHK-P0002");
            }
            // 水曜
            if (inParam.getBoolean(LstPCTUserResultWorkerDASCHParams.WEDNESDAY))
            {
                dayOfWeek += DispResources.getText("CHK-P0003");
            }
            // 木曜
            if (inParam.getBoolean(LstPCTUserResultWorkerDASCHParams.THURSDAY))
            {
                dayOfWeek += DispResources.getText("CHK-P0004");
            }
            // 金曜
            if (inParam.getBoolean(LstPCTUserResultWorkerDASCHParams.FRIDAY))
            {
                dayOfWeek += DispResources.getText("CHK-P0005");
            }
            // 土曜
            if (inParam.getBoolean(LstPCTUserResultWorkerDASCHParams.SATURDAY))
            {
                dayOfWeek += DispResources.getText("CHK-P0006");
            }
            // 日曜
            if (inParam.getBoolean(LstPCTUserResultWorkerDASCHParams.SUNDAY))
            {
                dayOfWeek += DispResources.getText("CHK-P0007");
            }

            // エリア名称
            String areaName = "";
            if (!WmsParam.ALL_AREA_NO.equals(inParam.getString(LstPCTUserResultWorkerDASCHParams.AREA_NO)))
            {
                AreaController aController = new AreaController(conn, this.getClass());
                areaName = aController.getAreaName(inParam.getString(LstPCTUserResultWorkerDASCHParams.AREA_NO));
            }
            else
            {
                areaName = DispResources.getText("CMB-W0023");
            }

            // レベル
            String level = "";
            if (inParam.getBoolean(LstPCTUserResultWorkerDASCHParams.LEVEL_A))
            {
                level = DispResources.getText("LBL-P0247");
            }
            if (inParam.getBoolean(LstPCTUserResultWorkerDASCHParams.LEVEL_B))
            {
                level = level + DispResources.getText("LBL-P0248");
            }
            if (inParam.getBoolean(LstPCTUserResultWorkerDASCHParams.LEVEL_C))
            {
                level = level + DispResources.getText("LBL-P0249");
            }

            // 1行目
            ListCellLine cellLine = _lcm_lst_SearchCondition.getNewLine();
            // 隠し項目
            cellLine.setValue(KEY_HIDDEN_WORK_DAY_FROM, day[0]);
            cellLine.setValue(KEY_HIDDEN_WORK_DAY_TO, day[1]);
            cellLine.setValue(KEY_HIDDEN_WEEK_DAY, dayOfWeek);
            cellLine.setValue(KEY_HIDDEN_BATCH_NO, inParam.getString(LstPCTUserResultWorkerDASCHParams.BATCH_NO));
            cellLine.setValue(KEY_HIDDEN_LEVEL, level);
            cellLine.setValue(KEY_HIDDEN_CONSIGNOR_CODE,
                    inParam.getString(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_CODE));
            cellLine.setValue(
                    KEY_HIDDEN_CONSIGNOR_NAME,
                    StringUtil.isBlank(inParam.getString(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_CODE)) ? ""
                                                                                                           : consignorName);
            cellLine.setValue(KEY_HIDDEN_AREA_NO, inParam.getString(LstPCTUserResultWorkerDASCHParams.AREA_NO));
            cellLine.setValue(KEY_HIDDEN_AREA_NAME, areaName);

            // 作業日
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-W9076"));
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_2, date);
            // 曜日
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-P0064"));
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_4, dayOfWeek);

            _lcm_lst_SearchCondition.add(cellLine);

            // 2行目
            cellLine = _lcm_lst_SearchCondition.getNewLine();
            // 荷主
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-W1362"));
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_2,
                    inParam.getString(LstPCTUserResultWorkerDASCHParams.CONSIGNOR_CODE));
            _lcm_lst_SearchCondition.add(cellLine);

            // 3行目
            cellLine = _lcm_lst_SearchCondition.getNewLine();
            // エリアNo.
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-W9915"));
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_2, inParam.getString(LstPCTUserResultWorkerDASCHParams.AREA_NO));
            // エリア名称
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-W0229"));
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_4, areaName);
            _lcm_lst_SearchCondition.add(cellLine);

            // 4行目
            cellLine = _lcm_lst_SearchCondition.getNewLine();
            // バッチNo.
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-W0224"));
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_2, inParam.getString(LstPCTUserResultWorkerDASCHParams.BATCH_NO));
            // レベル
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-P0251"));
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_4, level);
            _lcm_lst_SearchCondition.add(cellLine);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            // リストボックスのクリアを行います
            errorProcessing();
            // エラーメッセージの表示
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }


    /**
     * 平均リストの表示を行います。
     * 
     * @param outParam 出力パラメータ
     * @param isWorker trueならユーザ一覧
     */
    protected void setListAvg(Params outParam)
    {
        lst_UserResultWorkerAvg.clearRow();

        // 行追加
        lst_UserResultWorkerAvg.addRow();
        lst_UserResultWorkerAvg.setCurrentRow(1);

        try
        {
            // ランク(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.RANK,
                    outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_RANK));
            // 生産率(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.PRODUCTION_RATE,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_PRODUCTION_RATE)));
            // 総稼働時間(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.TOTAL_TIME,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_ALL_OPERATE_TIME)));
            // 実稼働時間(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.REAL_TIME,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_REAL_OPERATE_TIME)));
            // 集品時間(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.COLLECT_TIME,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_COLLECT_TIME)));
            // 中断時間(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.STOP_TIME,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_STOP_TIME)));
            // 作業時間（延べ）(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.TOTAL_WORK_TIME,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_OPERATE_TIME)));
            // オーダー数(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.ORDER_CNT,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_ORDER_COUNT)));
            // オーダー数/H(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.ORDER_CNT_PER_HOUR,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_ORDER_COUNT_PER_HOUR)));
            // 箱数(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.BOX_CNT,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_BOX_COUNT)));
            // 箱数/H(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.BOX_CNT_PER_HOUR,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_BOX_COUNT_PER_HOUR)));
            // 行数(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.LINE_CNT,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_LINE_COUNT)));
            // 行数/H(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.LINE_CNT_PER_HOUR,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_LINE_COUNT_PER_HOUR)));
            // 数量（ロット）(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.LOT_QTY,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_LOT_QTY)));
            // 数量（ロット）/H(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.LOT_QTY_PER_HOUR,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_LOT_QTY_PER_HOUR)));
            // 数量（バラ）(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.PIECE_QTY,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_PIECE_QTY)));
            // 数量（バラ）/H(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.PIECE_QTY_PER_HOUR,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_PIECE_QTY_PER_HOUR)));
            // 箱/オーダー(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.BOX_CNT_PER_ORDER,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_BOX_COUNT_PER_ORDER)));
            // 行/オーダー(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.LINE_CNT_PER_ORDER,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_LINE_COUNT_PER_ORDER)));
            // ロット/行(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.LOT_QTY_PER_LINE,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_LOT_QTY_PER_LINE_COUNT)));
            // バラ/行(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.PIECE_QTY_PER_LINE,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_PIECE_QTY_PER_LINE_COUNT)));
            // ミス率(平均)
            lst_UserResultWorkerAvg.setValue(ListAvg.MISS_RATE,
                    (outParam.getString(LstPCTUserResultWorkerDASCHParams.AVG_MISS_RATE)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            errorProcessing();
        }
    }

    /**
     * エラー時の処理を行います。<BR>
     * ・リストセルのヘッダを使用不可 <BR>
     * ・ページャの件数をクリア <BR>
     * ・セッションからコネクションを削除 <BR>
     * 
     */
    protected void errorProcessing()
    {
        // 各種ボタンを無効にする
        btn_Preview.setEnabled(false);
        btn_Print.setEnabled(false);
        btn_XLS.setEnabled(false);

        lst_UserResultWorkerList.setVisible(false);
        lst_UserResultWorkerAvg.setVisible(false);
        lst_Header.setVisible(false);
    }

    /**
     * ボタンを押下不可に設定
     *
     */
    protected void setBtnEnable()
    {
        btn_Preview.setEnabled(false);
        btn_Print.setEnabled(false);
        btn_XLS.setEnabled(false);
    }

    /**
     * 平均リストセルクリア処理
     */
    protected void setClearListCell()
    {
        lst_Header.setVisible(false);
        lst_UserResultWorkerAvg.setVisible(false);
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

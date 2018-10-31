// $Id: LstPCTUserResultAreaBusiness.java 7707 2010-03-20 07:23:11Z okayama $
package jp.co.daifuku.pcart.retrieval.listbox.pctuserresultarea;

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
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultAreaDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultAreaDASCHParams;
import jp.co.daifuku.pcart.retrieval.exporter.PctUserResultAreaListParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * エリア別実績照会の画面処理を行います。
 *
 * @version $Revision: 7707 $, $Date:: 2010-03-20 16:23:11 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class LstPCTUserResultAreaBusiness
        extends LstPCTUserResultArea
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

    /** lst_SearchCondition(HIDDEN_WORK_DAY_TO) */
    private static final ListCellKey KEY_HIDDEN_WORK_DAY_TO =
            new ListCellKey("HIDDEN_WORK_DAY_TO", new StringCellColumn(), false, false);

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

    /** lst_UserResultSummaryAvg(LST_AVG_PRODUCTION_RATE) */
    private static final ListCellKey KEY_LST_AVG_PRODUCTION_RATE =
            new ListCellKey("LST_AVG_PRODUCTION_RATE", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_WORKER_COUNT) */
    private static final ListCellKey KEY_LST_AVG_WORKER_COUNT =
            new ListCellKey("LST_AVG_WORKER_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_CART_COUNT) */
    private static final ListCellKey KEY_LST_AVG_CART_COUNT =
            new ListCellKey("LST_AVG_CART_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_OPERATE_TIME) */
    private static final ListCellKey KEY_LST_AVG_OPERATE_TIME =
            new ListCellKey("LST_AVG_OPERATE_TIME", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_AVG_ORDER_COUNT =
            new ListCellKey("LST_AVG_ORDER_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_ORDER_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_AVG_ORDER_COUNT_PER_HOUR =
            new ListCellKey("LST_AVG_ORDER_COUNT_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_BOX_COUNT) */
    private static final ListCellKey KEY_LST_AVG_BOX_COUNT =
            new ListCellKey("LST_AVG_BOX_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_BOX_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_AVG_BOX_COUNT_PER_HOUR =
            new ListCellKey("LST_AVG_BOX_COUNT_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_LINE_COUNT) */
    private static final ListCellKey KEY_LST_AVG_LINE_COUNT =
            new ListCellKey("LST_AVG_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_LINE_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_AVG_LINE_COUNT_PER_HOUR =
            new ListCellKey("LST_AVG_LINE_COUNT_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_LOT_QTY) */
    private static final ListCellKey KEY_LST_AVG_LOT_QTY =
            new ListCellKey("LST_AVG_LOT_QTY", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_LOT_QTY_PER_HOUR) */
    private static final ListCellKey KEY_LST_AVG_LOT_QTY_PER_HOUR =
            new ListCellKey("LST_AVG_LOT_QTY_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_PIECE_QTY) */
    private static final ListCellKey KEY_LST_AVG_PIECE_QTY =
            new ListCellKey("LST_AVG_PIECE_QTY", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_PIECE_QTY_PER_HOUR) */
    private static final ListCellKey KEY_LST_AVG_PIECE_QTY_PER_HOUR =
            new ListCellKey("LST_AVG_PIECE_QTY_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_BOX_COUNT_PER_ORDER) */
    private static final ListCellKey KEY_LST_AVG_BOX_COUNT_PER_ORDER =
            new ListCellKey("LST_AVG_BOX_COUNT_PER_ORDER", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_LINE_COUNT_PER_ORDER) */
    private static final ListCellKey KEY_LST_AVG_LINE_COUNT_PER_ORDER =
            new ListCellKey("LST_AVG_LINE_COUNT_PER_ORDER", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_LOT_QTY_PER_LINE_COUNT) */
    private static final ListCellKey KEY_LST_AVG_LOT_QTY_PER_LINE_COUNT =
            new ListCellKey("LST_AVG_LOT_QTY_PER_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_PIECE_QTY_PER_LINE_COUNT) */
    private static final ListCellKey KEY_LST_AVG_PIECE_QTY_PER_LINE_COUNT =
            new ListCellKey("LST_AVG_PIECE_QTY_PER_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultSummaryAvg(LST_AVG_MISS_RATE) */
    private static final ListCellKey KEY_LST_AVG_MISS_RATE =
            new ListCellKey("LST_AVG_MISS_RATE", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO =
            new ListCellKey("LST_AREA_NO", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_AREA_NAME) */
    private static final ListCellKey KEY_LST_AREA_NAME =
            new ListCellKey("LST_AREA_NAME", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_PRODUCTION_RATE) */
    private static final ListCellKey KEY_LST_PRODUCTION_RATE =
            new ListCellKey("LST_PRODUCTION_RATE", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_WORKER_COUNT) */
    private static final ListCellKey KEY_LST_WORKER_COUNT =
            new ListCellKey("LST_WORKER_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_TERMINAL_COUNT) */
    private static final ListCellKey KEY_LST_TERMINAL_COUNT =
            new ListCellKey("LST_TERMINAL_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultWorkerList(LST_START_TIME) */
    private static final ListCellKey KEY_LST_START_TIME =
            new ListCellKey("LST_START_TIME", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_SEC), true, false);

    /** lst_UserResultWorkerList(LST_END_TIME) */
    private static final ListCellKey KEY_LST_END_TIME =
            new ListCellKey("LST_END_TIME", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_SEC), true, false);

    /** lst_UserResultAreaList(LST_OPERATE_TIME) */
    private static final ListCellKey KEY_LST_OPERATE_TIME =
            new ListCellKey("LST_OPERATE_TIME", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_ORDER_COUNT =
            new ListCellKey("LST_ORDER_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_ORDER_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_PER_HOUR =
            new ListCellKey("LST_ORDER_COUNT_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_BOX_COUNT) */
    private static final ListCellKey KEY_LST_BOX_COUNT =
            new ListCellKey("LST_BOX_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_BOX_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_BOX_COUNT_PER_HOUR =
            new ListCellKey("LST_BOX_COUNT_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LINE_COUNT =
            new ListCellKey("LST_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_LINE_COUNT_PER_HOUR) */
    private static final ListCellKey KEY_LST_LINE_COUNT_PER_HOUR =
            new ListCellKey("LST_LINE_COUNT_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_LOT_QTY) */
    private static final ListCellKey KEY_LST_LOT_QTY =
            new ListCellKey("LST_LOT_QTY", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_LOT_QTY_PER_HOUR) */
    private static final ListCellKey KEY_LST_LOT_QTY_PER_HOUR =
            new ListCellKey("LST_LOT_QTY_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PIECE_QTY =
            new ListCellKey("LST_PIECE_QTY", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_PIECE_QTY_PER_HOUR) */
    private static final ListCellKey KEY_LST_PIECE_QTY_PER_HOUR =
            new ListCellKey("LST_PIECE_QTY_PER_HOUR", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_BOX_COUNT_PER_ORDER) */
    private static final ListCellKey KEY_LST_BOX_COUNT_PER_ORDER =
            new ListCellKey("LST_BOX_COUNT_PER_ORDER", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_LINE_COUNT_PER_ORDER) */
    private static final ListCellKey KEY_LST_LINE_COUNT_PER_ORDER =
            new ListCellKey("LST_LINE_COUNT_PER_ORDER", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_LOT_QTY_PER_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LOT_QTY_PER_LINE_COUNT =
            new ListCellKey("LST_LOT_QTY_PER_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_PIECE_QTY_PER_LINE_COUNT) */
    private static final ListCellKey KEY_LST_PIECE_QTY_PER_LINE_COUNT =
            new ListCellKey("LST_PIECE_QTY_PER_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_UserResultAreaList(LST_MISS_RATE) */
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
    };

    /** lst_UserResultSummaryAvg kyes */
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
            KEY_LST_AVG_CART_COUNT,
            KEY_LST_AVG_ORDER_COUNT_PER_HOUR,
            KEY_LST_AVG_BOX_COUNT_PER_HOUR,
            KEY_LST_AVG_LINE_COUNT_PER_HOUR,
            KEY_LST_AVG_LOT_QTY_PER_HOUR,
            KEY_LST_AVG_PIECE_QTY_PER_HOUR,
            KEY_LST_AVG_LINE_COUNT_PER_ORDER,
            KEY_LST_AVG_PIECE_QTY_PER_LINE_COUNT,
    };

    /** lst_UserResultAreaList kyes */
    private static final ListCellKey[] LST_USERRESULTAREALIST_KEYS = {
            KEY_LST_AREA_NO,
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
            KEY_LST_AREA_NAME,
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
         * 検索結果表示用リスト表示用 生産率
         */
        public static final int PRODUCTION_RATE = 1;

        /**
         * 検索結果表示用リスト表示用 人数
         */
        public static final int WORKER_CNT = 2;

        /**
         * 検索結果表示用リスト表示用 台数
         */
        public static final int TERMINAL_CNT = 12;

        /**
         * 検索結果表示用リスト表示用 作業時間（延べ）
         */
        public static final int TOTAL_WORK_TIME = 3;

        /**
         * 検索結果表示用リスト表示用 オーダー数
         */
        public static final int ORDER_CNT = 4;

        /**
         * 検索結果表示用リスト表示用 オーダー数/H
         */
        public static final int ORDER_CNT_PER_HOUR = 13;

        /**
         * 検索結果表示用リスト表示用 箱数
         */
        public static final int BOX_CNT = 5;

        /**
         * 検索結果表示用リスト表示用 箱数/H
         */
        public static final int BOX_CNT_PER_HOUR = 14;

        /**
         * 検索結果表示用リスト表示用 行数
         */
        public static final int LINE_CNT = 6;

        /**
         * 検索結果表示用リスト表示用 行数/H
         */
        public static final int LINE_CNT_PER_HOUR = 15;

        /**
         * 検索結果表示用リスト表示用 数量（ロット）
         */
        public static final int LOT_QTY = 7;

        /**
         * 検索結果表示用リスト表示用 数量（ロット）/H
         */
        public static final int LOT_QTY_PER_HOUR = 16;

        /**
         * 検索結果表示用リスト表示用 数量（バラ）
         */
        public static final int PIECE_QTY = 8;

        /**
         * 検索結果表示用リスト表示用 数量（バラ）/H
         */
        public static final int PIECE_QTY_PER_HOUR = 17;

        /**
         * 検索結果表示用リスト表示用 箱/オーダー
         */
        public static final int BOX_CNT_PER_ORDER = 9;

        /**
         * 検索結果表示用リスト表示用 行/オーダー
         */
        public static final int LINE_CNT_PER_ORDER = 18;

        /**
         * 検索結果表示用リスト表示用 ロット/行
         */
        public static final int LOT_QTY_PER_LINE = 10;

        /**
         * 検索結果表示用リスト表示用 バラ/行
         */
        public static final int PIECE_QTY_PER_LINE = 19;

        /**
         * 検索結果表示用リスト表示用 ミス率
         */
        public static final int MISS_RATE = 11;
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

    /** ListCellModel lst_UserResultSummaryAvg */
    private ListCellModel _lcm_lst_UserResultSummaryAvg;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_UserResultAreaList */
    private ListCellModel _lcm_lst_UserResultAreaList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstPCTUserResultAreaBusiness()
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

        // initialize lst_UserResultSummaryAvg.
        _lcm_lst_UserResultSummaryAvg =
                new ListCellModel(lst_UserResultSummaryAvg, LST_USERRESULTSUMMARYAVG_KEYS, locale);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_PRODUCTION_RATE, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_WORKER_COUNT, false);
        _lcm_lst_UserResultSummaryAvg.setToolTipVisible(KEY_LST_AVG_CART_COUNT, false);
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
        _pager = new PagerModel(new Pager[] {
                pgr_U,
                pgr_D
        }, locale);

        // initialize lst_UserResultAreaList.
        _lcm_lst_UserResultAreaList = new ListCellModel(lst_UserResultAreaList, LST_USERRESULTAREALIST_KEYS, locale);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_AREA_NAME, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_PRODUCTION_RATE, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_WORKER_COUNT, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_TERMINAL_COUNT, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_START_TIME, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_END_TIME, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_OPERATE_TIME, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_ORDER_COUNT, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_ORDER_COUNT_PER_HOUR, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_BOX_COUNT, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_BOX_COUNT_PER_HOUR, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_LINE_COUNT, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_LINE_COUNT_PER_HOUR, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_LOT_QTY, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_LOT_QTY_PER_HOUR, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_PIECE_QTY, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_PIECE_QTY_PER_HOUR, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_BOX_COUNT_PER_ORDER, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_LINE_COUNT_PER_ORDER, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_LOT_QTY_PER_LINE_COUNT, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_PIECE_QTY_PER_LINE_COUNT, true);
        _lcm_lst_UserResultAreaList.setToolTipVisible(KEY_LST_MISS_RATE, true);

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
    private void lst_UserResultAreaList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0013", KEY_LST_AREA_NAME);
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
        LstPCTUserResultAreaDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstPCTUserResultAreaDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstPCTUserResultAreaDASCHParams inparam = new LstPCTUserResultAreaDASCHParams();
            LstPCTUserResultAreaParams requestParam = new LstPCTUserResultAreaParams(request);
            inparam.set(LstPCTUserResultAreaDASCHParams.WORKDAY_FROM,
                    requestParam.get(LstPCTUserResultAreaParams.WORKDAY_FROM));
            inparam.set(LstPCTUserResultAreaDASCHParams.WORKDAY_TO,
                    requestParam.get(LstPCTUserResultAreaParams.WORKDAY_TO));
            inparam.set(LstPCTUserResultAreaDASCHParams.MONDAY, requestParam.get(LstPCTUserResultAreaParams.MONDAY));
            inparam.set(LstPCTUserResultAreaDASCHParams.TUESDAY, requestParam.get(LstPCTUserResultAreaParams.TUESDAY));
            inparam.set(LstPCTUserResultAreaDASCHParams.WEDNESDAY,
                    requestParam.get(LstPCTUserResultAreaParams.WEDNESDAY));
            inparam.set(LstPCTUserResultAreaDASCHParams.THURSDAY, requestParam.get(LstPCTUserResultAreaParams.THURSDAY));
            inparam.set(LstPCTUserResultAreaDASCHParams.FRIDAY, requestParam.get(LstPCTUserResultAreaParams.FRIDAY));
            inparam.set(LstPCTUserResultAreaDASCHParams.SATURDAY, requestParam.get(LstPCTUserResultAreaParams.SATURDAY));
            inparam.set(LstPCTUserResultAreaDASCHParams.SUNDAY, requestParam.get(LstPCTUserResultAreaParams.SUNDAY));
            inparam.set(LstPCTUserResultAreaDASCHParams.CONSIGNOR_CODE,
                    requestParam.get(LstPCTUserResultAreaParams.CONSIGNOR_CODE));
            inparam.set(LstPCTUserResultAreaDASCHParams.AREA_NO, requestParam.get(LstPCTUserResultAreaParams.AREA_NO));
            inparam.set(LstPCTUserResultAreaDASCHParams.BATCH_NO, requestParam.get(LstPCTUserResultAreaParams.BATCH_NO));
            inparam.set(LstPCTUserResultAreaDASCHParams.LEVEL_A, requestParam.get(LstPCTUserResultAreaParams.LEVEL_A));
            inparam.set(LstPCTUserResultAreaDASCHParams.LEVEL_B, requestParam.get(LstPCTUserResultAreaParams.LEVEL_B));
            inparam.set(LstPCTUserResultAreaDASCHParams.LEVEL_C, requestParam.get(LstPCTUserResultAreaParams.LEVEL_C));
            inparam.set(LstPCTUserResultAreaDASCHParams.COLLECT_CONDITION,
                    requestParam.get(LstPCTUserResultAreaParams.COLLECT_CONDITION));
            inparam.set(LstPCTUserResultAreaDASCHParams.DISPLAY_RANK,
                    requestParam.get(LstPCTUserResultAreaParams.DISPLAY_RANK));
            inparam.set(LstPCTUserResultAreaDASCHParams.USEDAY_OF_WEEK_FLAG,
                    requestParam.get(LstPCTUserResultAreaParams.USEDAY_OF_WEEK_FLAG));

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_UserResultAreaList.clear();

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
            // リストセルフラグ
            boolean isSet = true;
            // DFKLOOK:ここまで修正

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            for (Params outparam : outparams)
            {
                // DFKLOOK:ここから修正
                if (isSet)
                {
                    // 条件リストセル
                    lst_SearchCondition_Make(inparam, conn,
                            outparam.getString(LstPCTUserResultAreaDASCHParams.CONSIGNOR_NAME));

                    // 平均リストセル
                    setListAvg(outparam);
                    isSet = false;
                }
                // DFKLOOK:ここまで修正

                ListCellLine line = _lcm_lst_UserResultAreaList.getNewLine();
                line.setValue(KEY_LST_AREA_NO, outparam.get(LstPCTUserResultAreaDASCHParams.AREA_NO));
                line.setValue(KEY_LST_AREA_NAME, outparam.get(LstPCTUserResultAreaDASCHParams.AREA_NAME));
                line.setValue(KEY_LST_PRODUCTION_RATE, outparam.get(LstPCTUserResultAreaDASCHParams.PRODUCTION_RATE));
                line.setValue(KEY_LST_TERMINAL_COUNT, outparam.get(LstPCTUserResultAreaDASCHParams.TERMINAL_COUNT));
                line.setValue(KEY_LST_WORKER_COUNT, outparam.get(LstPCTUserResultAreaDASCHParams.WORKER_COUNT));
                line.setValue(KEY_LST_START_TIME, outparam.get(LstPCTUserResultAreaDASCHParams.START_TIME));
                line.setValue(KEY_LST_END_TIME, outparam.get(LstPCTUserResultAreaDASCHParams.END_TIME));
                line.setValue(KEY_LST_OPERATE_TIME, outparam.get(LstPCTUserResultAreaDASCHParams.OPERATE_TIME));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(LstPCTUserResultAreaDASCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_ORDER_COUNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.ORDER_COUNT_PER_HOUR));
                line.setValue(KEY_LST_BOX_COUNT, outparam.get(LstPCTUserResultAreaDASCHParams.BOX_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.BOX_COUNT_PER_HOUR));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(LstPCTUserResultAreaDASCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.LINE_COUNT_PER_HOUR));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(LstPCTUserResultAreaDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_LOT_QTY_PER_HOUR, outparam.get(LstPCTUserResultAreaDASCHParams.LOT_QTY_PER_HOUR));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(LstPCTUserResultAreaDASCHParams.PIECE_QTY));
                line.setValue(KEY_LST_PIECE_QTY_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.PIECE_QTY_PER_HOUR));
                line.setValue(KEY_LST_BOX_COUNT_PER_ORDER,
                        outparam.get(LstPCTUserResultAreaDASCHParams.BOX_COUNT_PER_ORDER));
                line.setValue(KEY_LST_LINE_COUNT_PER_ORDER,
                        outparam.get(LstPCTUserResultAreaDASCHParams.LINE_COUNT_PER_ORDER));
                line.setValue(KEY_LST_LOT_QTY_PER_LINE_COUNT,
                        outparam.get(LstPCTUserResultAreaDASCHParams.LOT_QTY_PER_LINE_COUNT));
                line.setValue(KEY_LST_PIECE_QTY_PER_LINE_COUNT,
                        outparam.get(LstPCTUserResultAreaDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                line.setValue(KEY_LST_MISS_RATE, outparam.get(LstPCTUserResultAreaDASCHParams.MISS_RATE));
                lst_UserResultAreaList_SetLineToolTip(line);
                _lcm_lst_UserResultAreaList.add(line);
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
            _lcm_lst_UserResultAreaList.clear();

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
        LstPCTUserResultAreaDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstPCTUserResultAreaDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_UserResultAreaList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_UserResultAreaList.getNewLine();
                line.setValue(KEY_LST_AREA_NO, outparam.get(LstPCTUserResultAreaDASCHParams.AREA_NO));
                line.setValue(KEY_LST_AREA_NAME, outparam.get(LstPCTUserResultAreaDASCHParams.AREA_NAME));
                line.setValue(KEY_LST_PRODUCTION_RATE, outparam.get(LstPCTUserResultAreaDASCHParams.PRODUCTION_RATE));
                line.setValue(KEY_LST_TERMINAL_COUNT, outparam.get(LstPCTUserResultAreaDASCHParams.TERMINAL_COUNT));
                line.setValue(KEY_LST_WORKER_COUNT, outparam.get(LstPCTUserResultAreaDASCHParams.WORKER_COUNT));
                line.setValue(KEY_LST_START_TIME, outparam.get(LstPCTUserResultAreaDASCHParams.START_TIME));
                line.setValue(KEY_LST_END_TIME, outparam.get(LstPCTUserResultAreaDASCHParams.END_TIME));
                line.setValue(KEY_LST_OPERATE_TIME, outparam.get(LstPCTUserResultAreaDASCHParams.OPERATE_TIME));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(LstPCTUserResultAreaDASCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_ORDER_COUNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.ORDER_COUNT_PER_HOUR));
                line.setValue(KEY_LST_BOX_COUNT, outparam.get(LstPCTUserResultAreaDASCHParams.BOX_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.BOX_COUNT_PER_HOUR));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(LstPCTUserResultAreaDASCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.LINE_COUNT_PER_HOUR));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(LstPCTUserResultAreaDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_LOT_QTY_PER_HOUR, outparam.get(LstPCTUserResultAreaDASCHParams.LOT_QTY_PER_HOUR));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(LstPCTUserResultAreaDASCHParams.PIECE_QTY));
                line.setValue(KEY_LST_PIECE_QTY_PER_HOUR,
                        outparam.get(LstPCTUserResultAreaDASCHParams.PIECE_QTY_PER_HOUR));
                line.setValue(KEY_LST_BOX_COUNT_PER_ORDER,
                        outparam.get(LstPCTUserResultAreaDASCHParams.BOX_COUNT_PER_ORDER));
                line.setValue(KEY_LST_LINE_COUNT_PER_ORDER,
                        outparam.get(LstPCTUserResultAreaDASCHParams.LINE_COUNT_PER_ORDER));
                line.setValue(KEY_LST_LOT_QTY_PER_LINE_COUNT,
                        outparam.get(LstPCTUserResultAreaDASCHParams.LOT_QTY_PER_LINE_COUNT));
                line.setValue(KEY_LST_PIECE_QTY_PER_LINE_COUNT,
                        outparam.get(LstPCTUserResultAreaDASCHParams.PIECE_QTY_PER_LINE_COUNT));
                line.setValue(KEY_LST_MISS_RATE, outparam.get(LstPCTUserResultAreaDASCHParams.MISS_RATE));
                lst_UserResultAreaList_SetLineToolTip(line);
                _lcm_lst_UserResultAreaList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_UserResultAreaList.clear();
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
            exporter = factory.newPVExporter("PctUserResultAreaList", getSession());
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
            ListCellLine avghLine = _lcm_lst_UserResultSummaryAvg.get(1);
            String avgProducate = avghLine.getStringValue(KEY_LST_AVG_PRODUCTION_RATE);
            String avgWorkerCnt = avghLine.getStringValue(KEY_LST_AVG_WORKER_COUNT);
            String avgTermCnt = avghLine.getStringValue(KEY_LST_AVG_CART_COUNT);
            String avgWorkTime = avghLine.getStringValue(KEY_LST_AVG_OPERATE_TIME);
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
            for (int i = 1; i <= _lcm_lst_UserResultAreaList.size(); i++)
            {
                ListCellLine line = _lcm_lst_UserResultAreaList.get(i);
                PctUserResultAreaListParams expparam = new PctUserResultAreaListParams();
                expparam.set(PctUserResultAreaListParams.SYS_DAY, DbDateUtil.getTimeStamp());
                expparam.set(PctUserResultAreaListParams.SYS_TIME, DbDateUtil.getTimeStamp());
                expparam.set(PctUserResultAreaListParams.WORK_DAY_FROM, WmsFormatter.toDate(searchWorkDayFrom));
                expparam.set(PctUserResultAreaListParams.WORK_DAY_TO, WmsFormatter.toDate(searchWorkDayTo));
                expparam.set(PctUserResultAreaListParams.WEEK_DAY, searchWeekDay);
                expparam.set(PctUserResultAreaListParams.BATCH_NO, searchBatchNo);
                expparam.set(PctUserResultAreaListParams.LEVEL, searchLevel);
                expparam.set(PctUserResultAreaListParams.CONSIGNOR_CODE, searchConsignorCode);
                expparam.set(PctUserResultAreaListParams.CONSIGNOR_NAME, searchConsignorName);
                expparam.set(PctUserResultAreaListParams.AREA_NO, searchAreaNo);
                expparam.set(PctUserResultAreaListParams.AREA_NAME, searchAreaName);

                expparam.set(PctUserResultAreaListParams.AREA_NO1, line.getValue(KEY_LST_AREA_NO));
                expparam.set(PctUserResultAreaListParams.AREA_NAME1, line.getValue(KEY_LST_AREA_NAME));
                expparam.set(PctUserResultAreaListParams.PRODUCTION_RATE, line.getValue(KEY_LST_PRODUCTION_RATE));
                expparam.set(PctUserResultAreaListParams.START_DATE, line.getValue(KEY_LST_START_TIME));
                expparam.set(PctUserResultAreaListParams.END_DATE, line.getValue(KEY_LST_END_TIME));
                expparam.set(PctUserResultAreaListParams.HEADS, line.getValue(KEY_LST_WORKER_COUNT));
                expparam.set(PctUserResultAreaListParams.CNT, line.getValue(KEY_LST_TERMINAL_COUNT));
                expparam.set(PctUserResultAreaListParams.WORK_TIME, line.getValue(KEY_LST_OPERATE_TIME));
                expparam.set(PctUserResultAreaListParams.ORDER_CNT, line.getValue(KEY_LST_ORDER_COUNT));
                expparam.set(PctUserResultAreaListParams.ORDER_CNT_PER_HOUR, line.getValue(KEY_LST_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.BOX_CNT, line.getValue(KEY_LST_BOX_COUNT));
                expparam.set(PctUserResultAreaListParams.BOX_CNT_PER_HOUR, line.getValue(KEY_LST_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.LINE_CNT, line.getValue(KEY_LST_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.LINE_CNT_PER_HOUR, line.getValue(KEY_LST_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.LOT_CNT, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctUserResultAreaListParams.LOT_CNT_PER_HOUR, line.getValue(KEY_LST_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.PIECE_CNT, line.getValue(KEY_LST_PIECE_QTY));
                expparam.set(PctUserResultAreaListParams.PIECE_CNT_PER_HOUR, line.getValue(KEY_LST_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.BOX_PER_ORDER, line.getValue(KEY_LST_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultAreaListParams.LINE_PER_ORDER, line.getValue(KEY_LST_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultAreaListParams.LOT_PER_LINE, line.getValue(KEY_LST_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.PIECE_PER_LINE, line.getValue(KEY_LST_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.MISS, line.getValue(KEY_LST_MISS_RATE));

                expparam.set(PctUserResultAreaListParams.PRODUCTION_RATE_AVG, avgProducate);
                expparam.set(PctUserResultAreaListParams.HEADS_AVG, avgWorkerCnt);
                expparam.set(PctUserResultAreaListParams.CNT_AVG, avgTermCnt);
                expparam.set(PctUserResultAreaListParams.WORK_TIME_AVG, avgWorkTime);
                expparam.set(PctUserResultAreaListParams.ORDER_CNT_AVG, avgOderCnt);
                expparam.set(PctUserResultAreaListParams.ORDER_CNT_PER_HOUR_AVG, avgOrderCntPerH);
                expparam.set(PctUserResultAreaListParams.BOX_CNT_AVG, avgBoxCnt);
                expparam.set(PctUserResultAreaListParams.BOX_CNT_PER_HOUR_AVG, avgBoxCntPerH);
                expparam.set(PctUserResultAreaListParams.LINE_CNT_AVG, avgLineCnt);
                expparam.set(PctUserResultAreaListParams.LINE_CNT_PER_HOUR_AVG, avgLineCntPerH);
                expparam.set(PctUserResultAreaListParams.LOT_CNT_AVG, avgLotQty);
                expparam.set(PctUserResultAreaListParams.LOT_CNT_PER_HOUR_AVG, avgLotQtyPerH);
                expparam.set(PctUserResultAreaListParams.PIECE_CNT_AVG, avgPeiceQty);
                expparam.set(PctUserResultAreaListParams.PIECE_CNT_PER_HOUR_AVG, avgPeiceQtyPerH);
                expparam.set(PctUserResultAreaListParams.BOX_PER_ORDER_AVG, avgBoxPerOder);
                expparam.set(PctUserResultAreaListParams.LINE_PER_ORDER_AVG, avgLinePerOder);
                expparam.set(PctUserResultAreaListParams.LOT_PER_LINE_AVG, avgLotQtyPerLine);
                expparam.set(PctUserResultAreaListParams.PIECE_PER_LINE_AVG, avgPeiceQtyPerLine);
                expparam.set(PctUserResultAreaListParams.MISS_AVG, avgMissRate);

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
            exporter = factory.newPrinterExporter("PctUserResultAreaList", false);
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
            ListCellLine avghLine = _lcm_lst_UserResultSummaryAvg.get(1);
            String avgProducate = avghLine.getStringValue(KEY_LST_AVG_PRODUCTION_RATE);
            String avgWorkerCnt = avghLine.getStringValue(KEY_LST_AVG_WORKER_COUNT);
            String avgTermCnt = avghLine.getStringValue(KEY_LST_AVG_CART_COUNT);
            String avgWorkTime = avghLine.getStringValue(KEY_LST_AVG_OPERATE_TIME);
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
            for (int i = 1; i <= _lcm_lst_UserResultAreaList.size(); i++)
            {
                ListCellLine line = _lcm_lst_UserResultAreaList.get(i);
                PctUserResultAreaListParams expparam = new PctUserResultAreaListParams();
                expparam.set(PctUserResultAreaListParams.SYS_DAY, DbDateUtil.getTimeStamp());
                expparam.set(PctUserResultAreaListParams.SYS_TIME, DbDateUtil.getTimeStamp());
                expparam.set(PctUserResultAreaListParams.WORK_DAY_FROM, WmsFormatter.toDate(searchWorkDayFrom));
                expparam.set(PctUserResultAreaListParams.WORK_DAY_TO, WmsFormatter.toDate(searchWorkDayTo));
                expparam.set(PctUserResultAreaListParams.WEEK_DAY, searchWeekDay);
                expparam.set(PctUserResultAreaListParams.BATCH_NO, searchBatchNo);
                expparam.set(PctUserResultAreaListParams.LEVEL, searchLevel);
                expparam.set(PctUserResultAreaListParams.CONSIGNOR_CODE, searchConsignorCode);
                expparam.set(PctUserResultAreaListParams.CONSIGNOR_NAME, searchConsignorName);
                expparam.set(PctUserResultAreaListParams.AREA_NO, searchAreaNo);
                expparam.set(PctUserResultAreaListParams.AREA_NAME, searchAreaName);

                expparam.set(PctUserResultAreaListParams.AREA_NO1, line.getValue(KEY_LST_AREA_NO));
                expparam.set(PctUserResultAreaListParams.AREA_NAME1, line.getValue(KEY_LST_AREA_NAME));
                expparam.set(PctUserResultAreaListParams.PRODUCTION_RATE, line.getValue(KEY_LST_PRODUCTION_RATE));
                expparam.set(PctUserResultAreaListParams.START_DATE, line.getValue(KEY_LST_START_TIME));
                expparam.set(PctUserResultAreaListParams.END_DATE, line.getValue(KEY_LST_END_TIME));
                expparam.set(PctUserResultAreaListParams.HEADS, line.getValue(KEY_LST_WORKER_COUNT));
                expparam.set(PctUserResultAreaListParams.CNT, line.getValue(KEY_LST_TERMINAL_COUNT));
                expparam.set(PctUserResultAreaListParams.WORK_TIME, line.getValue(KEY_LST_OPERATE_TIME));
                expparam.set(PctUserResultAreaListParams.ORDER_CNT, line.getValue(KEY_LST_ORDER_COUNT));
                expparam.set(PctUserResultAreaListParams.ORDER_CNT_PER_HOUR, line.getValue(KEY_LST_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.BOX_CNT, line.getValue(KEY_LST_BOX_COUNT));
                expparam.set(PctUserResultAreaListParams.BOX_CNT_PER_HOUR, line.getValue(KEY_LST_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.LINE_CNT, line.getValue(KEY_LST_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.LINE_CNT_PER_HOUR, line.getValue(KEY_LST_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.LOT_CNT, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctUserResultAreaListParams.LOT_CNT_PER_HOUR, line.getValue(KEY_LST_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.PIECE_CNT, line.getValue(KEY_LST_PIECE_QTY));
                expparam.set(PctUserResultAreaListParams.PIECE_CNT_PER_HOUR, line.getValue(KEY_LST_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.BOX_PER_ORDER, line.getValue(KEY_LST_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultAreaListParams.LINE_PER_ORDER, line.getValue(KEY_LST_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultAreaListParams.LOT_PER_LINE, line.getValue(KEY_LST_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.PIECE_PER_LINE, line.getValue(KEY_LST_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.MISS, line.getValue(KEY_LST_MISS_RATE));

                expparam.set(PctUserResultAreaListParams.PRODUCTION_RATE_AVG, avgProducate);
                expparam.set(PctUserResultAreaListParams.HEADS_AVG, avgWorkerCnt);
                expparam.set(PctUserResultAreaListParams.CNT_AVG, avgTermCnt);
                expparam.set(PctUserResultAreaListParams.WORK_TIME_AVG, avgWorkTime);
                expparam.set(PctUserResultAreaListParams.ORDER_CNT_AVG, avgOderCnt);
                expparam.set(PctUserResultAreaListParams.ORDER_CNT_PER_HOUR_AVG, avgOrderCntPerH);
                expparam.set(PctUserResultAreaListParams.BOX_CNT_AVG, avgBoxCnt);
                expparam.set(PctUserResultAreaListParams.BOX_CNT_PER_HOUR_AVG, avgBoxCntPerH);
                expparam.set(PctUserResultAreaListParams.LINE_CNT_AVG, avgLineCnt);
                expparam.set(PctUserResultAreaListParams.LINE_CNT_PER_HOUR_AVG, avgLineCntPerH);
                expparam.set(PctUserResultAreaListParams.LOT_CNT_AVG, avgLotQty);
                expparam.set(PctUserResultAreaListParams.LOT_CNT_PER_HOUR_AVG, avgLotQtyPerH);
                expparam.set(PctUserResultAreaListParams.PIECE_CNT_AVG, avgPeiceQty);
                expparam.set(PctUserResultAreaListParams.PIECE_CNT_PER_HOUR_AVG, avgPeiceQtyPerH);
                expparam.set(PctUserResultAreaListParams.BOX_PER_ORDER_AVG, avgBoxPerOder);
                expparam.set(PctUserResultAreaListParams.LINE_PER_ORDER_AVG, avgLinePerOder);
                expparam.set(PctUserResultAreaListParams.LOT_PER_LINE_AVG, avgLotQtyPerLine);
                expparam.set(PctUserResultAreaListParams.PIECE_PER_LINE_AVG, avgPeiceQtyPerLine);
                expparam.set(PctUserResultAreaListParams.MISS_AVG, avgMissRate);

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
            exporter = factory.newExcelExporter("PCTUserResultAreaList", getSession());
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
            ListCellLine avghLine = _lcm_lst_UserResultSummaryAvg.get(1);
            String avgProducate = avghLine.getStringValue(KEY_LST_AVG_PRODUCTION_RATE);
            String avgWorkerCnt = avghLine.getStringValue(KEY_LST_AVG_WORKER_COUNT);
            String avgTermCnt = avghLine.getStringValue(KEY_LST_AVG_CART_COUNT);
            String avgWorkTime = avghLine.getStringValue(KEY_LST_AVG_OPERATE_TIME);
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
            for (int i = 1; i <= _lcm_lst_UserResultAreaList.size(); i++)
            {
                ListCellLine line = _lcm_lst_UserResultAreaList.get(i);
                PctUserResultAreaListParams expparam = new PctUserResultAreaListParams();
                expparam.set(PctUserResultAreaListParams.WORK_DAY_FROM, WmsFormatter.toDate(searchWorkDayFrom));
                expparam.set(PctUserResultAreaListParams.WORK_DAY_TO, WmsFormatter.toDate(searchWorkDayTo));
                expparam.set(PctUserResultAreaListParams.WEEK_DAY, searchWeekDay);
                expparam.set(PctUserResultAreaListParams.BATCH_NO, searchBatchNo);
                expparam.set(PctUserResultAreaListParams.LEVEL, searchLevel);
                expparam.set(PctUserResultAreaListParams.CONSIGNOR_CODE, searchConsignorCode);
                expparam.set(PctUserResultAreaListParams.CONSIGNOR_NAME, searchConsignorName);
                expparam.set(PctUserResultAreaListParams.AREA_NO, searchAreaNo);
                expparam.set(PctUserResultAreaListParams.AREA_NAME, searchAreaName);

                expparam.set(PctUserResultAreaListParams.AREA_NO1, line.getValue(KEY_LST_AREA_NO));
                expparam.set(PctUserResultAreaListParams.AREA_NAME1, line.getValue(KEY_LST_AREA_NAME));
                expparam.set(PctUserResultAreaListParams.PRODUCTION_RATE, line.getValue(KEY_LST_PRODUCTION_RATE));
                expparam.set(PctUserResultAreaListParams.HEADS, line.getValue(KEY_LST_WORKER_COUNT));
                expparam.set(PctUserResultAreaListParams.CNT, line.getValue(KEY_LST_TERMINAL_COUNT));
                expparam.set(PctUserResultAreaListParams.START_DATE, line.getValue(KEY_LST_START_TIME));
                expparam.set(PctUserResultAreaListParams.END_DATE, line.getValue(KEY_LST_END_TIME));
                expparam.set(PctUserResultAreaListParams.WORK_TIME, line.getValue(KEY_LST_OPERATE_TIME));
                expparam.set(PctUserResultAreaListParams.ORDER_CNT, line.getValue(KEY_LST_ORDER_COUNT));
                expparam.set(PctUserResultAreaListParams.ORDER_CNT_PER_HOUR,
                        line.getValue(KEY_LST_ORDER_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.BOX_CNT, line.getValue(KEY_LST_BOX_COUNT));
                expparam.set(PctUserResultAreaListParams.BOX_CNT_PER_HOUR, line.getValue(KEY_LST_BOX_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.LINE_CNT, line.getValue(KEY_LST_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.LINE_CNT_PER_HOUR, line.getValue(KEY_LST_LINE_COUNT_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.LOT_CNT, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctUserResultAreaListParams.LOT_CNT_PER_HOUR, line.getValue(KEY_LST_LOT_QTY_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.PIECE_CNT, line.getValue(KEY_LST_PIECE_QTY));
                expparam.set(PctUserResultAreaListParams.PIECE_CNT_PER_HOUR, line.getValue(KEY_LST_PIECE_QTY_PER_HOUR));
                expparam.set(PctUserResultAreaListParams.BOX_PER_ORDER, line.getValue(KEY_LST_BOX_COUNT_PER_ORDER));
                expparam.set(PctUserResultAreaListParams.LINE_PER_ORDER, line.getValue(KEY_LST_LINE_COUNT_PER_ORDER));
                expparam.set(PctUserResultAreaListParams.LOT_PER_LINE, line.getValue(KEY_LST_LOT_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.PIECE_PER_LINE,
                        line.getValue(KEY_LST_PIECE_QTY_PER_LINE_COUNT));
                expparam.set(PctUserResultAreaListParams.MISS, line.getValue(KEY_LST_MISS_RATE));

                expparam.set(PctUserResultAreaListParams.PRODUCTION_RATE_AVG, avgProducate);
                expparam.set(PctUserResultAreaListParams.HEADS_AVG, avgWorkerCnt);
                expparam.set(PctUserResultAreaListParams.CNT_AVG, avgTermCnt);
                expparam.set(PctUserResultAreaListParams.WORK_TIME_AVG, avgWorkTime);
                expparam.set(PctUserResultAreaListParams.ORDER_CNT_AVG, avgOderCnt);
                expparam.set(PctUserResultAreaListParams.ORDER_CNT_PER_HOUR_AVG, avgOrderCntPerH);
                expparam.set(PctUserResultAreaListParams.BOX_CNT_AVG, avgBoxCnt);
                expparam.set(PctUserResultAreaListParams.BOX_CNT_PER_HOUR_AVG, avgBoxCntPerH);
                expparam.set(PctUserResultAreaListParams.LINE_CNT_AVG, avgLineCnt);
                expparam.set(PctUserResultAreaListParams.LINE_CNT_PER_HOUR_AVG, avgLineCntPerH);
                expparam.set(PctUserResultAreaListParams.LOT_CNT_AVG, avgLotQty);
                expparam.set(PctUserResultAreaListParams.LOT_CNT_PER_HOUR_AVG, avgLotQtyPerH);
                expparam.set(PctUserResultAreaListParams.PIECE_CNT_AVG, avgPeiceQty);
                expparam.set(PctUserResultAreaListParams.PIECE_CNT_PER_HOUR_AVG, avgPeiceQtyPerH);
                expparam.set(PctUserResultAreaListParams.BOX_PER_ORDER_AVG, avgBoxPerOder);
                expparam.set(PctUserResultAreaListParams.LINE_PER_ORDER_AVG, avgLinePerOder);
                expparam.set(PctUserResultAreaListParams.LOT_PER_LINE_AVG, avgLotQtyPerLine);
                expparam.set(PctUserResultAreaListParams.PIECE_PER_LINE_AVG, avgPeiceQtyPerLine);
                expparam.set(PctUserResultAreaListParams.MISS_AVG, avgMissRate);

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
            if (inParam.getBoolean(LstPCTUserResultAreaDASCHParams.MONDAY))
            {
                dayOfWeek += DispResources.getText("CHK-P0001");
            }
            // 火曜
            if (inParam.getBoolean(LstPCTUserResultAreaDASCHParams.TUESDAY))
            {
                dayOfWeek += DispResources.getText("CHK-P0002");
            }
            // 水曜
            if (inParam.getBoolean(LstPCTUserResultAreaDASCHParams.WEDNESDAY))
            {
                dayOfWeek += DispResources.getText("CHK-P0003");
            }
            // 木曜
            if (inParam.getBoolean(LstPCTUserResultAreaDASCHParams.THURSDAY))
            {
                dayOfWeek += DispResources.getText("CHK-P0004");
            }
            // 金曜
            if (inParam.getBoolean(LstPCTUserResultAreaDASCHParams.FRIDAY))
            {
                dayOfWeek += DispResources.getText("CHK-P0005");
            }
            // 土曜
            if (inParam.getBoolean(LstPCTUserResultAreaDASCHParams.SATURDAY))
            {
                dayOfWeek += DispResources.getText("CHK-P0006");
            }
            // 日曜
            if (inParam.getBoolean(LstPCTUserResultAreaDASCHParams.SUNDAY))
            {
                dayOfWeek += DispResources.getText("CHK-P0007");
            }

            // エリア名称
            String areaName = "";
            if (!WmsParam.ALL_AREA_NO.equals(inParam.getString(LstPCTUserResultAreaDASCHParams.AREA_NO)))
            {
                AreaController aController = new AreaController(conn, this.getClass());
                areaName = aController.getAreaName(inParam.getString(LstPCTUserResultAreaDASCHParams.AREA_NO));
            }
            else
            {
                areaName = DispResources.getText("CMB-W0023");
            }

            // レベル
            String level = "";
            if (inParam.getBoolean(LstPCTUserResultAreaDASCHParams.LEVEL_A))
            {
                level = DispResources.getText("LBL-P0247");
            }
            if (inParam.getBoolean(LstPCTUserResultAreaDASCHParams.LEVEL_B))
            {
                level = level + DispResources.getText("LBL-P0248");
            }
            if (inParam.getBoolean(LstPCTUserResultAreaDASCHParams.LEVEL_C))
            {
                level = level + DispResources.getText("LBL-P0249");
            }

            // 1行目
            ListCellLine cellLine = _lcm_lst_SearchCondition.getNewLine();
            // 隠し項目
            cellLine.setValue(KEY_HIDDEN_WORK_DAY_FROM, day[0]);
            cellLine.setValue(KEY_HIDDEN_WORK_DAY_TO, day[1]);
            cellLine.setValue(KEY_HIDDEN_WEEK_DAY, dayOfWeek);
            cellLine.setValue(KEY_HIDDEN_BATCH_NO, inParam.getString(LstPCTUserResultAreaDASCHParams.BATCH_NO));
            cellLine.setValue(KEY_HIDDEN_LEVEL, level);
            cellLine.setValue(KEY_HIDDEN_CONSIGNOR_CODE,
                    inParam.getString(LstPCTUserResultAreaDASCHParams.CONSIGNOR_CODE));
            cellLine.setValue(
                    KEY_HIDDEN_CONSIGNOR_NAME,
                    StringUtil.isBlank(inParam.getString(LstPCTUserResultAreaDASCHParams.CONSIGNOR_CODE)) ? ""
                                                                                                         : consignorName);
            cellLine.setValue(KEY_HIDDEN_AREA_NO, inParam.getString(LstPCTUserResultAreaDASCHParams.AREA_NO));
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
                    inParam.getString(LstPCTUserResultAreaDASCHParams.CONSIGNOR_CODE));
            _lcm_lst_SearchCondition.add(cellLine);

            // 3行目
            cellLine = _lcm_lst_SearchCondition.getNewLine();
            // エリアNo.
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-W9915"));
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_2, inParam.getString(LstPCTUserResultAreaDASCHParams.AREA_NO));
            // エリア名称
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-W0229"));
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_4, areaName);
            _lcm_lst_SearchCondition.add(cellLine);

            // 4行目
            cellLine = _lcm_lst_SearchCondition.getNewLine();
            // バッチNo.
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-W0224"));
            cellLine.setValue(KEY_LST_SEARCH_CONDITION_2, inParam.getString(LstPCTUserResultAreaDASCHParams.BATCH_NO));
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
     * @param outParam
     *            出力パラメータ
     * @param isWorker
     *            trueならユーザ一覧
     */
    protected void setListAvg(Params outParam)
    {
        lst_UserResultSummaryAvg.clearRow();

        // 行追加
        lst_UserResultSummaryAvg.addRow();
        lst_UserResultSummaryAvg.setCurrentRow(1);

        try
        {
            // 生産率(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.PRODUCTION_RATE,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_PRODUCTION_RATE)));
            // 人数(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.WORKER_CNT,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_WORKER_COUNT)));
            // 台数(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.TERMINAL_CNT,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_TERMINAL_COUNT)));
            // 作業時間（延べ）(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.TOTAL_WORK_TIME,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_OPERATE_TIME)));
            // オーダー数(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.ORDER_CNT,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_ORDER_COUNT)));
            // オーダー数/H(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.ORDER_CNT_PER_HOUR,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_ORDER_COUNT_PER_HOUR)));
            // 箱数(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.BOX_CNT,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_BOX_COUNT)));
            // 箱数/H(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.BOX_CNT_PER_HOUR,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_BOX_COUNT_PER_HOUR)));
            // 行数(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.LINE_CNT,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_LINE_COUNT)));
            // 行数/H(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.LINE_CNT_PER_HOUR,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_LINE_COUNT_PER_HOUR)));
            // 数量（ロット）(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.LOT_QTY,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_LOT_QTY)));
            // 数量（ロット）/H(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.LOT_QTY_PER_HOUR,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_LOT_QTY_PER_HOUR)));
            // 数量（バラ）(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.PIECE_QTY,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_PIECE_QTY)));
            // 数量（バラ）/H(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.PIECE_QTY_PER_HOUR,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_PIECE_QTY_PER_HOUR)));
            // 箱/オーダー(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.BOX_CNT_PER_ORDER,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_BOX_COUNT_PER_ORDER)));
            // 行/オーダー(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.LINE_CNT_PER_ORDER,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_LINE_COUNT_PER_ORDER)));
            // ロット/行(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.LOT_QTY_PER_LINE,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_LOT_QTY_PER_LINE_COUNT)));
            // バラ/行(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.PIECE_QTY_PER_LINE,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_PIECE_QTY_PER_LINE_COUNT)));
            // ミス率(平均)
            lst_UserResultSummaryAvg.setValue(ListAvg.MISS_RATE,
                    (outParam.getString(LstPCTUserResultAreaDASCHParams.AVG_MISS_RATE)));
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
        btn_XLS.setEnabled(false);

        lst_UserResultSummaryAvg.setVisible(false);
        lst_UserResultAreaList.setVisible(false);
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
        lst_UserResultSummaryAvg.setVisible(false);
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

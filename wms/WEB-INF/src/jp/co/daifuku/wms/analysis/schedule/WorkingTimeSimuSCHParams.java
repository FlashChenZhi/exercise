// $Id: WorkingTimeSimuSCHParams.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.analysis.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.authentication.DfkUserInfo;

import jp.co.daifuku.foundation.common.ParamKey;
import jp.co.daifuku.foundation.common.ScheduleParams;

/**
 * BusinessクラスとSCH間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class WorkingTimeSimuSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AFTER_PLAN_DATE */
    public static final ParamKey AFTER_PLAN_DATE = new ParamKey("AFTER_PLAN_DATE");

    /** BEFORE_PLAN_DATE */
    public static final ParamKey BEFORE_PLAN_DATE = new ParamKey("BEFORE_PLAN_DATE");

    /** INSTOCK_ITEM_QTY */
    public static final ParamKey INSTOCK_ITEM_QTY = new ParamKey("INSTOCK_ITEM_QTY");

    /** INSTOCK_PIECE_QTY */
    public static final ParamKey INSTOCK_PIECE_QTY = new ParamKey("INSTOCK_PIECE_QTY");

    /** INSTOCK_WK_START_TIME */
    public static final ParamKey INSTOCK_WK_START_TIME = new ParamKey("INSTOCK_WK_START_TIME");

    /** INSTOCK_WORKER_NUM */
    public static final ParamKey INSTOCK_WORKER_NUM = new ParamKey("INSTOCK_WORKER_NUM");

    /** RETRIEVAL_ITEM_QTY_INP */
    public static final ParamKey RETRIEVAL_ITEM_QTY_INP = new ParamKey("RETRIEVAL_ITEM_QTY_INP");

    /** RETRIEVAL_ITEM_QTY_PLAN */
    public static final ParamKey RETRIEVAL_ITEM_QTY_PLAN = new ParamKey("RETRIEVAL_ITEM_QTY_PLAN");

    /** RETRIEVAL_PIECE_QTY_INP */
    public static final ParamKey RETRIEVAL_PIECE_QTY_INP = new ParamKey("RETRIEVAL_PIECE_QTY_INP");

    /** RETRIEVAL_PIECE_QTY_PLAN */
    public static final ParamKey RETRIEVAL_PIECE_QTY_PLAN = new ParamKey("RETRIEVAL_PIECE_QTY_PLAN");

    /** RETRIEVAL_WORK_END_TIME */
    public static final ParamKey RETRIEVAL_WORK_END_TIME = new ParamKey("RETRIEVAL_WORK_END_TIME");

    /** RETRIEVAL_WORK_START_TIME */
    public static final ParamKey RETRIEVAL_WORK_START_TIME = new ParamKey("RETRIEVAL_WORK_START_TIME");

    /** RETRIEVAL_WORKER_NUM */
    public static final ParamKey RETRIEVAL_WORKER_NUM = new ParamKey("RETRIEVAL_WORKER_NUM");

    /** RETRIEVAL_WORKING_TIME */
    public static final ParamKey RETRIEVAL_WORKING_TIME = new ParamKey("RETRIEVAL_WORKING_TIME");

    /** SHIPPING_ITEM_QTY */
    public static final ParamKey SHIPPING_ITEM_QTY = new ParamKey("SHIPPING_ITEM_QTY");

    /** SHIPPING_PIECE_QTY */
    public static final ParamKey SHIPPING_PIECE_QTY = new ParamKey("SHIPPING_PIECE_QTY");

    /** SHIPPING_WK_START_TIME */
    public static final ParamKey SHIPPING_WK_START_TIME = new ParamKey("SHIPPING_WK_START_TIME");

    /** SHIPPING_WORKER_NUM */
    public static final ParamKey SHIPPING_WORKER_NUM = new ParamKey("SHIPPING_WORKER_NUM");

    /** SORTING_ITEM_QTY */
    public static final ParamKey SORTING_ITEM_QTY = new ParamKey("SORTING_ITEM_QTY");

    /** SORTING_PIECE_QTY */
    public static final ParamKey SORTING_PIECE_QTY = new ParamKey("SORTING_PIECE_QTY");

    /** SORTING_WK_START_TIME */
    public static final ParamKey SORTING_WK_START_TIME = new ParamKey("SORTING_WK_START_TIME");

    /** SORTING_WORKER_NUM */
    public static final ParamKey SORTING_WORKER_NUM = new ParamKey("SORTING_WORKER_NUM");

    /** STORAGE_ITEM_QTY_INP */
    public static final ParamKey STORAGE_ITEM_QTY_INP = new ParamKey("STORAGE_ITEM_QTY_INP");

    /** STORAGE_ITEM_QTY_PLAN */
    public static final ParamKey STORAGE_ITEM_QTY_PLAN = new ParamKey("STORAGE_ITEM_QTY_PLAN");

    /** STORAGE_PIECE_QTY_INP */
    public static final ParamKey STORAGE_PIECE_QTY_INP = new ParamKey("STORAGE_PIECE_QTY_INP");

    /** STORAGE_PIECE_QTY_PLAN */
    public static final ParamKey STORAGE_PIECE_QTY_PLAN = new ParamKey("STORAGE_PIECE_QTY_PLAN");

    /** STORAGE_WORK_END_TIME */
    public static final ParamKey STORAGE_WORK_END_TIME = new ParamKey("STORAGE_WORK_END_TIME");

    /** STORAGE_WORK_START_TIME */
    public static final ParamKey STORAGE_WORK_START_TIME = new ParamKey("STORAGE_WORK_START_TIME");

    /** STORAGE_WORKER_NUM */
    public static final ParamKey STORAGE_WORKER_NUM = new ParamKey("STORAGE_WORKER_NUM");

    /** STORAGE_WORKING_TIME */
    public static final ParamKey STORAGE_WORKING_TIME = new ParamKey("STORAGE_WORKING_TIME");

    /** WORK_PLAN_DATE */
    public static final ParamKey WORK_PLAN_DATE = new ParamKey("WORK_PLAN_DATE");

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ユーザ情報を元にパラメータクラスを作成します。
     * @param dui ユーザ情報
     */
    public WorkingTimeSimuSCHParams(DfkUserInfo dui)
    {
        super(dui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

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

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class

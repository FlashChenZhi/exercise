package jp.co.daifuku.wms.crossdock.schedule;

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
 * @version
 * @author BusiTune 1.0 Generator.
 */
public class XDProgressSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** GROUP */
    public static final ParamKey GROUP = new ParamKey("GROUP");

    /** PLAN_DAY */
    public static final ParamKey PLAN_DAY = new ParamKey("PLAN_DAY");

    /** PROCESS_FLAG */
    public static final ParamKey PROCESS_FLAG = new ParamKey("PROCESS_FLAG");

    /** PROGRESS_DISPLAY */
    public static final ParamKey PROGRESS_DISPLAY = new ParamKey("PROGRESS_DISPLAY");

    /** PROGRESS_RATE_RECEIVING */
    public static final ParamKey PROGRESS_RATE_RECEIVING = new ParamKey("PROGRESS_RATE_RECEIVING");

    /** PROGRESS_RATE_RECEIVING2 */
    public static final ParamKey PROGRESS_RATE_RECEIVING2 = new ParamKey("PROGRESS_RATE_RECEIVING2");

    /** PROGRESS_RATE_SORT */
    public static final ParamKey PROGRESS_RATE_SORT = new ParamKey("PROGRESS_RATE_SORT");

    /** PROGRESS_RATE_SORT2 */
    public static final ParamKey PROGRESS_RATE_SORT2 = new ParamKey("PROGRESS_RATE_SORT2");

    /** RECEIVE_CASE_QTY */
    public static final ParamKey RECEIVE_CASE_QTY = new ParamKey("RECEIVE_CASE_QTY");

    /** RECEIVE_DETAIL_COUNT */
    public static final ParamKey RECEIVE_DETAIL_COUNT = new ParamKey("RECEIVE_DETAIL_COUNT");

    /** RECEIVE_PIECE_QTY */
    public static final ParamKey RECEIVE_PIECE_QTY = new ParamKey("RECEIVE_PIECE_QTY");

    /** SHORTAGE_QTY */
    public static final ParamKey SHORTAGE_QTY = new ParamKey("SHORTAGE_QTY");

    /** SORT_CASE_QTY */
    public static final ParamKey SORT_CASE_QTY = new ParamKey("SORT_CASE_QTY");

    /** SORT_DETAIL_COUNT */
    public static final ParamKey SORT_DETAIL_COUNT = new ParamKey("SORT_DETAIL_COUNT");

    /** SORT_PIECE_QTY */
    public static final ParamKey SORT_PIECE_QTY = new ParamKey("SORT_PIECE_QTY");

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
     * Default constructor
     */
    public XDProgressSCHParams()
    {
        super();
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
     * @return
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class

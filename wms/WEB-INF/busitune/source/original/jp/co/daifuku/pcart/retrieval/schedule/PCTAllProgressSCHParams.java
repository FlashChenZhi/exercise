package jp.co.daifuku.pcart.retrieval.schedule;

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
public class PCTAllProgressSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** BATCH_SEQ_NO */
    public static final ParamKey BATCH_SEQ_NO = new ParamKey("BATCH_SEQ_NO");

    /** BOX_COUNT */
    public static final ParamKey BOX_COUNT = new ParamKey("BOX_COUNT");

    /** BUTTON_FLAG */
    public static final ParamKey BUTTON_FLAG = new ParamKey("BUTTON_FLAG");

    /** CART_COUNT */
    public static final ParamKey CART_COUNT = new ParamKey("CART_COUNT");

    /** COLLECT_FLAG */
    public static final ParamKey COLLECT_FLAG = new ParamKey("COLLECT_FLAG");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CONSIGNOR_NAME */
    public static final ParamKey CONSIGNOR_NAME = new ParamKey("CONSIGNOR_NAME");

    /** END_PLAN_TIME */
    public static final ParamKey END_PLAN_TIME = new ParamKey("END_PLAN_TIME");

    /** END_PLAN_TIME_FLAG */
    public static final ParamKey END_PLAN_TIME_FLAG = new ParamKey("END_PLAN_TIME_FLAG");

    /** LINE_COUNT */
    public static final ParamKey LINE_COUNT = new ParamKey("LINE_COUNT");

    /** LOT_COUNT */
    public static final ParamKey LOT_COUNT = new ParamKey("LOT_COUNT");

    /** ORDER_COUNT */
    public static final ParamKey ORDER_COUNT = new ParamKey("ORDER_COUNT");

    /** PLAN_DATE */
    public static final ParamKey PLAN_DATE = new ParamKey("PLAN_DATE");

    /** PROCESS_FLAG */
    public static final ParamKey PROCESS_FLAG = new ParamKey("PROCESS_FLAG");

    /** PROGRESS_RATE */
    public static final ParamKey PROGRESS_RATE = new ParamKey("PROGRESS_RATE");

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
    public PCTAllProgressSCHParams()
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

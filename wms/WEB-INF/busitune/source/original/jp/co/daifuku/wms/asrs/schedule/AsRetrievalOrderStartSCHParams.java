package jp.co.daifuku.wms.asrs.schedule;

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
public class AsRetrievalOrderStartSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA */
    public static final ParamKey AREA = new ParamKey("AREA");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** CUSTOMER_NAME */
    public static final ParamKey CUSTOMER_NAME = new ParamKey("CUSTOMER_NAME");

    /** DETAIL_COUNT */
    public static final ParamKey DETAIL_COUNT = new ParamKey("DETAIL_COUNT");

    /** FUNCTION_ID */
    public static final ParamKey FUNCTION_ID = new ParamKey("FUNCTION_ID");

    /** LINE_COUNT */
    public static final ParamKey LINE_COUNT = new ParamKey("LINE_COUNT");

    /** ORDER_NO */
    public static final ParamKey ORDER_NO = new ParamKey("ORDER_NO");

    /** ORDER_NO_FROM */
    public static final ParamKey ORDER_NO_FROM = new ParamKey("ORDER_NO_FROM");

    /** ORDER_NO_TO */
    public static final ParamKey ORDER_NO_TO = new ParamKey("ORDER_NO_TO");

    /** PRINT_FLAG */
    public static final ParamKey PRINT_FLAG = new ParamKey("PRINT_FLAG");

    /** RETRIEVAL_PLAN_DATE */
    public static final ParamKey RETRIEVAL_PLAN_DATE = new ParamKey("RETRIEVAL_PLAN_DATE");

    /** SELECT */
    public static final ParamKey SELECT = new ParamKey("SELECT");

    /** STATION */
    public static final ParamKey STATION = new ParamKey("STATION");

    /** STATION_NAME */
    public static final ParamKey STATION_NAME = new ParamKey("STATION_NAME");

    /** STATION_NO */
    public static final ParamKey STATION_NO = new ParamKey("STATION_NO");

    /** WORK_PLACE */
    public static final ParamKey WORK_PLACE = new ParamKey("WORK_PLACE");

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
    public AsRetrievalOrderStartSCHParams()
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

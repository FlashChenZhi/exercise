package jp.co.daifuku.wms.system.schedule;

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
public class SystemStateSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** IN_ALLOCATION_CLEAR */
    public static final ParamKey IN_ALLOCATION_CLEAR = new ParamKey("IN_ALLOCATION_CLEAR");

    /** IN_CREATE_REPORT_DATA */
    public static final ParamKey IN_CREATE_REPORT_DATA = new ParamKey("IN_CREATE_REPORT_DATA");

    /** IN_DAILY_PROCESS */
    public static final ParamKey IN_DAILY_PROCESS = new ParamKey("IN_DAILY_PROCESS");

    /** IN_HOST_COMMUNICATION */
    public static final ParamKey IN_HOST_COMMUNICATION = new ParamKey("IN_HOST_COMMUNICATION");

    /** IN_LOAD_PLAN_DATA */
    public static final ParamKey IN_LOAD_PLAN_DATA = new ParamKey("IN_LOAD_PLAN_DATA");

    /** IN_PLAN_DATA_HOLD_DAYS */
    public static final ParamKey IN_PLAN_DATA_HOLD_DAYS = new ParamKey("IN_PLAN_DATA_HOLD_DAYS");

    /** IN_RESULT_DATA_HOLD_DAYS */
    public static final ParamKey IN_RESULT_DATA_HOLD_DAYS = new ParamKey("IN_RESULT_DATA_HOLD_DAYS");

    /** IN_RETRIEVAL_ALLOCATE */
    public static final ParamKey IN_RETRIEVAL_ALLOCATE = new ParamKey("IN_RETRIEVAL_ALLOCATE");

    /** PRODUCT */
    public static final ParamKey PRODUCT = new ParamKey("PRODUCT");

    /** VERSION */
    public static final ParamKey VERSION = new ParamKey("VERSION");

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
    public SystemStateSCHParams()
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

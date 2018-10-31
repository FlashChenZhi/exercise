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
public class ReportDataPlanSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CLASS_NAME */
    public static final ParamKey CLASS_NAME = new ParamKey("CLASS_NAME");

    /** DATA_TYPE */
    public static final ParamKey DATA_TYPE = new ParamKey("DATA_TYPE");

    /** LOG_FLAG */
    public static final ParamKey LOG_FLAG = new ParamKey("LOG_FLAG");

    /** MESSAGE */
    public static final ParamKey MESSAGE = new ParamKey("MESSAGE");

    /** REPORT_DATA_TYPE */
    public static final ParamKey REPORT_DATA_TYPE = new ParamKey("REPORT_DATA_TYPE");

    /** SELECT */
    public static final ParamKey SELECT = new ParamKey("SELECT");

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
    public ReportDataPlanSCHParams()
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

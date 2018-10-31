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
public class AsStationModeSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AFTER_CURRENT_MODE */
    public static final ParamKey AFTER_CURRENT_MODE = new ParamKey("AFTER_CURRENT_MODE");

    /** BEFORE_CURRENT_MODE */
    public static final ParamKey BEFORE_CURRENT_MODE = new ParamKey("BEFORE_CURRENT_MODE");

    /** HIDDEN_BEFORE_CURRENT_MODE */
    public static final ParamKey HIDDEN_BEFORE_CURRENT_MODE = new ParamKey("HIDDEN_BEFORE_CURRENT_MODE");

    /** HIDDEN_MACHINE_STATUS */
    public static final ParamKey HIDDEN_MACHINE_STATUS = new ParamKey("HIDDEN_MACHINE_STATUS");

    /** HIDDEN_MODE_TYPE */
    public static final ParamKey HIDDEN_MODE_TYPE = new ParamKey("HIDDEN_MODE_TYPE");

    /** HIDDEN_STATION_NAME */
    public static final ParamKey HIDDEN_STATION_NAME = new ParamKey("HIDDEN_STATION_NAME");

    /** HIDDEN_STATION_TYPE */
    public static final ParamKey HIDDEN_STATION_TYPE = new ParamKey("HIDDEN_STATION_TYPE");

    /** HIDDEN_SUSPEND */
    public static final ParamKey HIDDEN_SUSPEND = new ParamKey("HIDDEN_SUSPEND");

    /** MACHINE_STATUS */
    public static final ParamKey MACHINE_STATUS = new ParamKey("MACHINE_STATUS");

    /** MODE_TYPE */
    public static final ParamKey MODE_TYPE = new ParamKey("MODE_TYPE");

    /** SETTING_RESULT */
    public static final ParamKey SETTING_RESULT = new ParamKey("SETTING_RESULT");

    /** STATION_NO */
    public static final ParamKey STATION_NO = new ParamKey("STATION_NO");

    /** SUSPEND */
    public static final ParamKey SUSPEND = new ParamKey("SUSPEND");

    /** WORK_COUNT */
    public static final ParamKey WORK_COUNT = new ParamKey("WORK_COUNT");

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
    public AsStationModeSCHParams()
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

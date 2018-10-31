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
public class AsChangeStationPopupSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CURRENT_MODE */
    public static final ParamKey CURRENT_MODE = new ParamKey("CURRENT_MODE");

    /** CURRENT_MODE_NAME */
    public static final ParamKey CURRENT_MODE_NAME = new ParamKey("CURRENT_MODE_NAME");

    /** MODE_TYPE */
    public static final ParamKey MODE_TYPE = new ParamKey("MODE_TYPE");

    /** MODE_TYPE_NAME */
    public static final ParamKey MODE_TYPE_NAME = new ParamKey("MODE_TYPE_NAME");

    /** STATION_NAME */
    public static final ParamKey STATION_NAME = new ParamKey("STATION_NAME");

    /** STATION_NO */
    public static final ParamKey STATION_NO = new ParamKey("STATION_NO");

    /** STATUS */
    public static final ParamKey STATUS = new ParamKey("STATUS");

    /** STATUS_NAME */
    public static final ParamKey STATUS_NAME = new ParamKey("STATUS_NAME");

    /** SUSPEND */
    public static final ParamKey SUSPEND = new ParamKey("SUSPEND");

    /** SUSPEND_NAME */
    public static final ParamKey SUSPEND_NAME = new ParamKey("SUSPEND_NAME");

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
    public AsChangeStationPopupSCHParams()
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

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
public class FaAsReStoringSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** FUNCTION_ID */
    public static final ParamKey FUNCTION_ID = new ParamKey("FUNCTION_ID");

    /** PRINT_FLAG */
    public static final ParamKey PRINT_FLAG = new ParamKey("PRINT_FLAG");

    /** SETTING_UNIT_KEY */
    public static final ParamKey SETTING_UNIT_KEY = new ParamKey("SETTING_UNIT_KEY");

    /** SOFT_ZONE_ID */
    public static final ParamKey SOFT_ZONE_ID = new ParamKey("SOFT_ZONE_ID");

    /** STATION_NO */
    public static final ParamKey STATION_NO = new ParamKey("STATION_NO");

    /** WORK_NO */
    public static final ParamKey WORK_NO = new ParamKey("WORK_NO");

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
    public FaAsReStoringSCHParams()
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

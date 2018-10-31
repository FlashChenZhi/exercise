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
public class AsStoringStatusSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** EMPTY */
    public static final ParamKey EMPTY = new ParamKey("EMPTY");

    /** EMPTY_PALLET */
    public static final ParamKey EMPTY_PALLET = new ParamKey("EMPTY_PALLET");

    /** ERROR */
    public static final ParamKey ERROR = new ParamKey("ERROR");

    /** INACCESSIBLE */
    public static final ParamKey INACCESSIBLE = new ParamKey("INACCESSIBLE");

    /** LOAD_SIZE */
    public static final ParamKey LOAD_SIZE = new ParamKey("LOAD_SIZE");

    /** OCCUPANCY_RATE */
    public static final ParamKey OCCUPANCY_RATE = new ParamKey("OCCUPANCY_RATE");

    /** OCCUPIED */
    public static final ParamKey OCCUPIED = new ParamKey("OCCUPIED");

    /** RESTRICTED */
    public static final ParamKey RESTRICTED = new ParamKey("RESTRICTED");

    /** RM_NO */
    public static final ParamKey RM_NO = new ParamKey("RM_NO");

    /** SOFTZONE_NAME */
    public static final ParamKey SOFTZONE_NAME = new ParamKey("SOFTZONE_NAME");

    /** TOTAL */
    public static final ParamKey TOTAL = new ParamKey("TOTAL");

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
    public AsStoringStatusSCHParams()
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

package jp.co.daifuku.wms.replenish.schedule;

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
public class ReplenishWorkMntSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_TYPE */
    public static final ParamKey AREA_TYPE = new ParamKey("AREA_TYPE");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** FROM_LOCATION_NO */
    public static final ParamKey FROM_LOCATION_NO = new ParamKey("FROM_LOCATION_NO");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_COUNT */
    public static final ParamKey ITEM_COUNT = new ParamKey("ITEM_COUNT");

    /** RETRIEVAL_AREA_NO */
    public static final ParamKey RETRIEVAL_AREA_NO = new ParamKey("RETRIEVAL_AREA_NO");

    /** SELECTED_JOB_TYPE */
    public static final ParamKey SELECTED_JOB_TYPE = new ParamKey("SELECTED_JOB_TYPE");

    /** SETTING_UNIT_KEY */
    public static final ParamKey SETTING_UNIT_KEY = new ParamKey("SETTING_UNIT_KEY");

    /** START_DATETIME */
    public static final ParamKey START_DATETIME = new ParamKey("START_DATETIME");

    /** TO_LOCATION_NO */
    public static final ParamKey TO_LOCATION_NO = new ParamKey("TO_LOCATION_NO");

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
    public ReplenishWorkMntSCHParams()
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

package jp.co.daifuku.wms.storage.schedule;

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
public class StorageListStartSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** DETAIL_COUNT */
    public static final ParamKey DETAIL_COUNT = new ParamKey("DETAIL_COUNT");

    /** FUNCTION_ID */
    public static final ParamKey FUNCTION_ID = new ParamKey("FUNCTION_ID");

    /** ISSUE_REPORT */
    public static final ParamKey ISSUE_REPORT = new ParamKey("ISSUE_REPORT");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** PLAN_DAY */
    public static final ParamKey PLAN_DAY = new ParamKey("PLAN_DAY");

    /** SELECT */
    public static final ParamKey SELECT = new ParamKey("SELECT");

    /** SETTING_UKEY */
    public static final ParamKey SETTING_UKEY = new ParamKey("SETTING_UKEY");

    /** TO_ITEM_CODE */
    public static final ParamKey TO_ITEM_CODE = new ParamKey("TO_ITEM_CODE");


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
    public StorageListStartSCHParams()
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

    public StorageListStartSCHParams(DfkUserInfo ui)
    {
        super(ui);
    }

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

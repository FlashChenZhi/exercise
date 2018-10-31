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
public class ReplenishListCompleteSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CASE_PACK */
    public static final ParamKey CASE_PACK = new ParamKey("CASE_PACK");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** FROM_AREA */
    public static final ParamKey FROM_AREA = new ParamKey("FROM_AREA");

    /** FROM_LOCATION */
    public static final ParamKey FROM_LOCATION = new ParamKey("FROM_LOCATION");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** JOB_NO */
    public static final ParamKey JOB_NO = new ParamKey("JOB_NO");

    /** L_SETTING_UKEY */
    public static final ParamKey L_SETTING_UKEY = new ParamKey("L_SETTING_UKEY");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** PLAN_CASE_QTY */
    public static final ParamKey PLAN_CASE_QTY = new ParamKey("PLAN_CASE_QTY");

    /** PLAN_PIECE_QTY */
    public static final ParamKey PLAN_PIECE_QTY = new ParamKey("PLAN_PIECE_QTY");

    /** REPLENISH_CASE_QTY */
    public static final ParamKey REPLENISH_CASE_QTY = new ParamKey("REPLENISH_CASE_QTY");

    /** REPLENISH_PIECE_QTY */
    public static final ParamKey REPLENISH_PIECE_QTY = new ParamKey("REPLENISH_PIECE_QTY");

    /** REPLENISH_QTY */
    public static final ParamKey REPLENISH_QTY = new ParamKey("REPLENISH_QTY");

    /** SELECT_JOB_TYPE */
    public static final ParamKey SELECT_JOB_TYPE = new ParamKey("SELECT_JOB_TYPE");

    /** SETTING_UKEY */
    public static final ParamKey SETTING_UKEY = new ParamKey("SETTING_UKEY");

    /** TO_AREA */
    public static final ParamKey TO_AREA = new ParamKey("TO_AREA");

    /** TO_LOCATION */
    public static final ParamKey TO_LOCATION = new ParamKey("TO_LOCATION");

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
    public ReplenishListCompleteSCHParams()
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

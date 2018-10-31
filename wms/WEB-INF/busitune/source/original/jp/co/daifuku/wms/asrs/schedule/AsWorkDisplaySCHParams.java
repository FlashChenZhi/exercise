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
public class AsWorkDisplaySCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** BRANCH_NO */
    public static final ParamKey BRANCH_NO = new ParamKey("BRANCH_NO");

    /** CANREMOVE */
    public static final ParamKey CANREMOVE = new ParamKey("CANREMOVE");

    /** CARRY_FLAG */
    public static final ParamKey CARRY_FLAG = new ParamKey("CARRY_FLAG");

    /** CARRY_KEY */
    public static final ParamKey CARRY_KEY = new ParamKey("CARRY_KEY");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** CUSTOMER_NAME */
    public static final ParamKey CUSTOMER_NAME = new ParamKey("CUSTOMER_NAME");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** JOB_TYPE */
    public static final ParamKey JOB_TYPE = new ParamKey("JOB_TYPE");

    /** LAST_UPDATE_DATE */
    public static final ParamKey LAST_UPDATE_DATE = new ParamKey("LAST_UPDATE_DATE");

    /** LINE_NO */
    public static final ParamKey LINE_NO = new ParamKey("LINE_NO");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** NO */
    public static final ParamKey NO = new ParamKey("NO");

    /** ORDER_NO */
    public static final ParamKey ORDER_NO = new ParamKey("ORDER_NO");

    /** PLAN_CASE_QTY */
    public static final ParamKey PLAN_CASE_QTY = new ParamKey("PLAN_CASE_QTY");

    /** PLAN_PIECE_QTY */
    public static final ParamKey PLAN_PIECE_QTY = new ParamKey("PLAN_PIECE_QTY");

    /** PROCESS_STATUS */
    public static final ParamKey PROCESS_STATUS = new ParamKey("PROCESS_STATUS");

    /** RETRIEVAL_DETAIL */
    public static final ParamKey RETRIEVAL_DETAIL = new ParamKey("RETRIEVAL_DETAIL");

    /** RETRIEVAL_DETAIL_NAME */
    public static final ParamKey RETRIEVAL_DETAIL_NAME = new ParamKey("RETRIEVAL_DETAIL_NAME");

    /** STATION_NO */
    public static final ParamKey STATION_NO = new ParamKey("STATION_NO");

    /** STOCK_CASE_QTY */
    public static final ParamKey STOCK_CASE_QTY = new ParamKey("STOCK_CASE_QTY");

    /** STOCK_PIECE_QTY */
    public static final ParamKey STOCK_PIECE_QTY = new ParamKey("STOCK_PIECE_QTY");

    /** TICKET_NO */
    public static final ParamKey TICKET_NO = new ParamKey("TICKET_NO");

    /** WAREHOUSE_NO */
    public static final ParamKey WAREHOUSE_NO = new ParamKey("WAREHOUSE_NO");

    /** WORK_DISPLAY_OPERATE */
    public static final ParamKey WORK_DISPLAY_OPERATE = new ParamKey("WORK_DISPLAY_OPERATE");

    /** WORK_NO */
    public static final ParamKey WORK_NO = new ParamKey("WORK_NO");

    /** WORK_TYPE */
    public static final ParamKey WORK_TYPE = new ParamKey("WORK_TYPE");

    /** WORK_TYPE_NAME */
    public static final ParamKey WORK_TYPE_NAME = new ParamKey("WORK_TYPE_NAME");

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
    public AsWorkDisplaySCHParams()
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

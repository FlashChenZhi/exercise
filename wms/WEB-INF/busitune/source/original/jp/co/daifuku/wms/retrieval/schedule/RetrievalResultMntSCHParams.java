package jp.co.daifuku.wms.retrieval.schedule;

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
public class RetrievalResultMntSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NAME */
    public static final ParamKey AREA_NAME = new ParamKey("AREA_NAME");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** BRANCH */
    public static final ParamKey BRANCH = new ParamKey("BRANCH");

    /** CASE */
    public static final ParamKey CASE = new ParamKey("CASE");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** CUSTOMER_NAME */
    public static final ParamKey CUSTOMER_NAME = new ParamKey("CUSTOMER_NAME");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** HIDDEN_FLAG */
    public static final ParamKey HIDDEN_FLAG = new ParamKey("HIDDEN_FLAG");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** JOB_NO */
    public static final ParamKey JOB_NO = new ParamKey("JOB_NO");

    /** LASTUPDATE */
    public static final ParamKey LASTUPDATE = new ParamKey("LASTUPDATE");

    /** LINE */
    public static final ParamKey LINE = new ParamKey("LINE");

    /** LOT */
    public static final ParamKey LOT = new ParamKey("LOT");

    /** ORDER_NO */
    public static final ParamKey ORDER_NO = new ParamKey("ORDER_NO");

    /** PIECE */
    public static final ParamKey PIECE = new ParamKey("PIECE");

    /** PLAN */
    public static final ParamKey PLAN = new ParamKey("PLAN");

    /** PLAN_CASE_QTY */
    public static final ParamKey PLAN_CASE_QTY = new ParamKey("PLAN_CASE_QTY");

    /** PLAN_DAY */
    public static final ParamKey PLAN_DAY = new ParamKey("PLAN_DAY");

    /** PLAN_PIECE_QTY */
    public static final ParamKey PLAN_PIECE_QTY = new ParamKey("PLAN_PIECE_QTY");

    /** PLANUKEY */
    public static final ParamKey PLANUKEY = new ParamKey("PLANUKEY");

    /** REPORT_FLAG */
    public static final ParamKey REPORT_FLAG = new ParamKey("REPORT_FLAG");

    /** RESULT_AREA_NO */
    public static final ParamKey RESULT_AREA_NO = new ParamKey("RESULT_AREA_NO");

    /** RESULT_CASE_QTY */
    public static final ParamKey RESULT_CASE_QTY = new ParamKey("RESULT_CASE_QTY");

    /** RESULT_LOCATION_NO */
    public static final ParamKey RESULT_LOCATION_NO = new ParamKey("RESULT_LOCATION_NO");

    /** RESULT_LOT_NO */
    public static final ParamKey RESULT_LOT_NO = new ParamKey("RESULT_LOT_NO");

    /** RESULT_PIECE_QTY */
    public static final ParamKey RESULT_PIECE_QTY = new ParamKey("RESULT_PIECE_QTY");

    /** RETRIEVAL_AREA */
    public static final ParamKey RETRIEVAL_AREA = new ParamKey("RETRIEVAL_AREA");

    /** RETRIEVAL_PLAN_DATE */
    public static final ParamKey RETRIEVAL_PLAN_DATE = new ParamKey("RETRIEVAL_PLAN_DATE");

    /** SELECT */
    public static final ParamKey SELECT = new ParamKey("SELECT");

    /** TICKET */
    public static final ParamKey TICKET = new ParamKey("TICKET");

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
    public RetrievalResultMntSCHParams()
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

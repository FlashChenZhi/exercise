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
public class RetrievalListStartSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NAME */
    public static final ParamKey AREA_NAME = new ParamKey("AREA_NAME");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** CUSTOMER_NAME */
    public static final ParamKey CUSTOMER_NAME = new ParamKey("CUSTOMER_NAME");

    /** DETAIL_COUNT */
    public static final ParamKey DETAIL_COUNT = new ParamKey("DETAIL_COUNT");

    /** FUNCTION_ID */
    public static final ParamKey FUNCTION_ID = new ParamKey("FUNCTION_ID");

    /** L_ISSUE_REPORT */
    public static final ParamKey L_ISSUE_REPORT = new ParamKey("L_ISSUE_REPORT");

    /** ORDER_NO */
    public static final ParamKey ORDER_NO = new ParamKey("ORDER_NO");

    /** ORDER_NO_FROM */
    public static final ParamKey ORDER_NO_FROM = new ParamKey("ORDER_NO_FROM");

    /** ORDER_NO_TO */
    public static final ParamKey ORDER_NO_TO = new ParamKey("ORDER_NO_TO");

    /** PLAN_AREA_NO */
    public static final ParamKey PLAN_AREA_NO = new ParamKey("PLAN_AREA_NO");

    /** PLAN_DAY */
    public static final ParamKey PLAN_DAY = new ParamKey("PLAN_DAY");

    /** RETRIEVAL_PLAN_DATE */
    public static final ParamKey RETRIEVAL_PLAN_DATE = new ParamKey("RETRIEVAL_PLAN_DATE");

    /** SELECT */
    public static final ParamKey SELECT = new ParamKey("SELECT");

    /** VS_BATCH_NO */
    public static final ParamKey VS_BATCH_NO = new ParamKey("VS_BATCH_NO");

    /** VS_ORDER_NO_FROM */
    public static final ParamKey VS_ORDER_NO_FROM = new ParamKey("VS_ORDER_NO_FROM");

    /** VS_ORDER_NO_TO */
    public static final ParamKey VS_ORDER_NO_TO = new ParamKey("VS_ORDER_NO_TO");

    /** VS_RETRIEVAL_PLAN_DATE */
    public static final ParamKey VS_RETRIEVAL_PLAN_DATE = new ParamKey("VS_RETRIEVAL_PLAN_DATE");

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
    public RetrievalListStartSCHParams()
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

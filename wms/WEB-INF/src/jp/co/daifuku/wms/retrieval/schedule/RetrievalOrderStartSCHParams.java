package jp.co.daifuku.wms.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.foundation.common.ParamKey;
import jp.co.daifuku.foundation.common.ScheduleParams;

/**
 * BusinessクラスとSCH間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version
 * @author BusiTune 1.0 Generator.
 */
public class RetrievalOrderStartSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ALLOCATED_PATTERN */
    public static final ParamKey ALLOCATED_PATTERN = new ParamKey("ALLOCATED_PATTERN");

    /** ALLOCATION_RESULT */
    public static final ParamKey ALLOCATION_RESULT = new ParamKey("ALLOCATION_RESULT");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** COLUMN_1 */
    public static final ParamKey COLUMN_1 = new ParamKey("COLUMN_1");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** CUSTOMER_NAME */
    public static final ParamKey CUSTOMER_NAME = new ParamKey("CUSTOMER_NAME");

    /** DETAIL_COUNT */
    public static final ParamKey DETAIL_COUNT = new ParamKey("DETAIL_COUNT");

    /** ORDER_NO */
    public static final ParamKey ORDER_NO = new ParamKey("ORDER_NO");

    /** ORDER_NO_FROM */
    public static final ParamKey ORDER_NO_FROM = new ParamKey("ORDER_NO_FROM");

    /** ORDER_NO_TO */
    public static final ParamKey ORDER_NO_TO = new ParamKey("ORDER_NO_TO");

    /** RETRIEVAL_PLAN_DATE */
    public static final ParamKey RETRIEVAL_PLAN_DATE = new ParamKey("RETRIEVAL_PLAN_DATE");

    /** SHORTAGE_COMPLETION_FLAG */
    public static final ParamKey SHORTAGE_COMPLETION_FLAG = new ParamKey("SHORTAGE_COMPLETION_FLAG");

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
     * ユーザ情報を元にパラメータクラスを作成します。
     * @param dui ユーザ情報
     */
    public RetrievalOrderStartSCHParams()
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

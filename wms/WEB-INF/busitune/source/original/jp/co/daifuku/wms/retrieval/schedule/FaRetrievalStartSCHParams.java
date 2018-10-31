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
public class FaRetrievalStartSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ALLOCATED_PATTERN */
    public static final ParamKey ALLOCATED_PATTERN = new ParamKey("ALLOCATED_PATTERN");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** FUNCTION_ID */
    public static final ParamKey FUNCTION_ID = new ParamKey("FUNCTION_ID");

    /** PLAN_DAY */
    public static final ParamKey PLAN_DAY = new ParamKey("PLAN_DAY");

    /** SHORTAGE_LIST_PRINT_FLAG */
    public static final ParamKey SHORTAGE_LIST_PRINT_FLAG = new ParamKey("SHORTAGE_LIST_PRINT_FLAG");

    /** SHORTAGE_WORK_FLAG */
    public static final ParamKey SHORTAGE_WORK_FLAG = new ParamKey("SHORTAGE_WORK_FLAG");

    /** WORK_LIST_PRINT_FLAG */
    public static final ParamKey WORK_LIST_PRINT_FLAG = new ParamKey("WORK_LIST_PRINT_FLAG");

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
    public FaRetrievalStartSCHParams()
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

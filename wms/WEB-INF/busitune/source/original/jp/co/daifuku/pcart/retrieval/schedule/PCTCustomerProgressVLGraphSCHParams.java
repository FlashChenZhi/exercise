package jp.co.daifuku.pcart.retrieval.schedule;

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
public class PCTCustomerProgressVLGraphSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** BOX_COUNT */
    public static final ParamKey BOX_COUNT = new ParamKey("BOX_COUNT");

    /** BUTTON_FLAG */
    public static final ParamKey BUTTON_FLAG = new ParamKey("BUTTON_FLAG");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** CUSTOMER_NAME */
    public static final ParamKey CUSTOMER_NAME = new ParamKey("CUSTOMER_NAME");

    /** LINE_COUNT */
    public static final ParamKey LINE_COUNT = new ParamKey("LINE_COUNT");

    /** LOT_COUNT */
    public static final ParamKey LOT_COUNT = new ParamKey("LOT_COUNT");

    /** LOT_QTY */
    public static final ParamKey LOT_QTY = new ParamKey("LOT_QTY");

    /** ORDER_COUNT */
    public static final ParamKey ORDER_COUNT = new ParamKey("ORDER_COUNT");

    /** PROCESS_FLAG */
    public static final ParamKey PROCESS_FLAG = new ParamKey("PROCESS_FLAG");

    /** PROGRESS_RATE */
    public static final ParamKey PROGRESS_RATE = new ParamKey("PROGRESS_RATE");

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
    public PCTCustomerProgressVLGraphSCHParams()
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

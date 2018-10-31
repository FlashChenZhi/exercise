package jp.co.daifuku.pcart.retrieval.schedule;

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
public class BatchStartCancelSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** BATCH */
    public static final ParamKey BATCH = new ParamKey("BATCH");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** BATCH_SEQ */
    public static final ParamKey BATCH_SEQ = new ParamKey("BATCH_SEQ");

    /** BATCH_SEQ_NO */
    public static final ParamKey BATCH_SEQ_NO = new ParamKey("BATCH_SEQ_NO");

    /** ITEMS_WITH_NO_REGISTERED_WEIGHT */
    public static final ParamKey ITEMS_WITH_NO_REGISTERED_WEIGHT = new ParamKey("ITEMS_WITH_NO_REGISTERED_WEIGHT");

    /** LAST_UPDATE_DATE */
    public static final ParamKey LAST_UPDATE_DATE = new ParamKey("LAST_UPDATE_DATE");

    /** LAST_UPDATE_DATETIME */
    public static final ParamKey LAST_UPDATE_DATETIME = new ParamKey("LAST_UPDATE_DATETIME");

    /** LINE_NO */
    public static final ParamKey LINE_NO = new ParamKey("LINE_NO");

    /** MASTER_FLAG */
    public static final ParamKey MASTER_FLAG = new ParamKey("MASTER_FLAG");
    
    /** NO_OF_ORDERS */
    public static final ParamKey NO_OF_ORDERS = new ParamKey("NO_OF_ORDERS");

    /** NO_OF_RECORDS */
    public static final ParamKey NO_OF_RECORDS = new ParamKey("NO_OF_RECORDS");

    /** NUMBERING_FLAG */
    public static final ParamKey NUMBERING_FLAG = new ParamKey("NUMBERING_FLAG");

    /** ORDER_NO_NUMBERING_FLAG */
    public static final ParamKey ORDER_NO_NUMBERING_FLAG = new ParamKey("ORDER_NO_NUMBERING_FLAG");

    /** ORDER_QTY */
    public static final ParamKey ORDER_QTY = new ParamKey("ORDER_QTY");

    /** REPORT_FLAG */
    public static final ParamKey REPORT_FLAG = new ParamKey("REPORT_FLAG");

    /** SCHEDULE_FLAG */
    public static final ParamKey SCHEDULE_FLAG = new ParamKey("SCHEDULE_FLAG");

    /** SELECT */
    public static final ParamKey SELECT = new ParamKey("SELECT");

    /** STATUS */
    public static final ParamKey STATUS = new ParamKey("STATUS");

    /** STATUS_FLAG */
    public static final ParamKey STATUS_FLAG = new ParamKey("STATUS_FLAG");

    /** WEIGHT_FLAG */
    public static final ParamKey WEIGHT_FLAG = new ParamKey("WEIGHT_FLAG");

    /** WEIGHT_UNREGISTERED */
    public static final ParamKey WEIGHT_UNREGISTERED = new ParamKey("WEIGHT_UNREGISTERED");

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
    public BatchStartCancelSCHParams()
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

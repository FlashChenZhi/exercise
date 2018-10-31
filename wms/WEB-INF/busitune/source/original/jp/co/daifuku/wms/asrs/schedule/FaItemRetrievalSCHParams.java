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
public class FaItemRetrievalSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** PRIORITY_FLAG */
    public static final ParamKey PRIORITY_FLAG = new ParamKey("PRIORITY_FLAG");

    /** RETRIEVAL_QTY */
    public static final ParamKey RETRIEVAL_QTY = new ParamKey("RETRIEVAL_QTY");

    /** RETRIEVAL_RESULT_QTY */
    public static final ParamKey RETRIEVAL_RESULT_QTY = new ParamKey("RETRIEVAL_RESULT_QTY");

    /** SHORTAGE_LIST_PRINT */
    public static final ParamKey SHORTAGE_LIST_PRINT = new ParamKey("SHORTAGE_LIST_PRINT");

    /** STATION_NO */
    public static final ParamKey STATION_NO = new ParamKey("STATION_NO");

    /** TOTAL_STOCK_QTY */
    public static final ParamKey TOTAL_STOCK_QTY = new ParamKey("TOTAL_STOCK_QTY");

    /** TYPE */
    public static final ParamKey TYPE = new ParamKey("TYPE");

    /** WORK_LIST_PRINT */
    public static final ParamKey WORK_LIST_PRINT = new ParamKey("WORK_LIST_PRINT");

    /** WORK_PLACE */
    public static final ParamKey WORK_PLACE = new ParamKey("WORK_PLACE");

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
    public FaItemRetrievalSCHParams()
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

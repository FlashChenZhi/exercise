package jp.co.daifuku.wms.stock.schedule;

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
public class NoPlanRetrievalSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ALL */
    public static final ParamKey ALL = new ParamKey("ALL");

    /** ALLOC_CASE_QTY */
    public static final ParamKey ALLOC_CASE_QTY = new ParamKey("ALLOC_CASE_QTY");

    /** ALLOC_PIECE_QTY */
    public static final ParamKey ALLOC_PIECE_QTY = new ParamKey("ALLOC_PIECE_QTY");

    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** FROM_LOCATION */
    public static final ParamKey FROM_LOCATION = new ParamKey("FROM_LOCATION");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** ITF */
    public static final ParamKey ITF = new ParamKey("ITF");

    /** JAN */
    public static final ParamKey JAN = new ParamKey("JAN");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** PLAN_LOT */
    public static final ParamKey PLAN_LOT = new ParamKey("PLAN_LOT");

    /** REASON */
    public static final ParamKey REASON = new ParamKey("REASON");

    /** RESULT_LOT */
    public static final ParamKey RESULT_LOT = new ParamKey("RESULT_LOT");

    /** RETRIEVAL_CASE_QTY */
    public static final ParamKey RETRIEVAL_CASE_QTY = new ParamKey("RETRIEVAL_CASE_QTY");

    /** RETRIEVAL_PIECE_QTY */
    public static final ParamKey RETRIEVAL_PIECE_QTY = new ParamKey("RETRIEVAL_PIECE_QTY");

    /** SETTING_UKEY */
    public static final ParamKey SETTING_UKEY = new ParamKey("SETTING_UKEY");

    /** STOCK_ID */
    public static final ParamKey STOCK_ID = new ParamKey("STOCK_ID");

    /** STORAGE_DATE */
    public static final ParamKey STORAGE_DATE = new ParamKey("STORAGE_DATE");

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
    public NoPlanRetrievalSCHParams()
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

package jp.co.daifuku.wms.inventorychk.schedule;

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
public class InventoryCompleteSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** CASE_QTY */
    public static final ParamKey CASE_QTY = new ParamKey("CASE_QTY");

    /** CONFIRM */
    public static final ParamKey CONFIRM = new ParamKey("CONFIRM");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** INVENTORY */
    public static final ParamKey INVENTORY = new ParamKey("INVENTORY");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** JOB_NO */
    public static final ParamKey JOB_NO = new ParamKey("JOB_NO");

    /** LIST_WORK_NO */
    public static final ParamKey LIST_WORK_NO = new ParamKey("LIST_WORK_NO");

    /** LOCATION_FROM */
    public static final ParamKey LOCATION_FROM = new ParamKey("LOCATION_FROM");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** LOCATION_TO */
    public static final ParamKey LOCATION_TO = new ParamKey("LOCATION_TO");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** PIECE_QTY */
    public static final ParamKey PIECE_QTY = new ParamKey("PIECE_QTY");

    /** RESULT_CASE_QTY */
    public static final ParamKey RESULT_CASE_QTY = new ParamKey("RESULT_CASE_QTY");

    /** RESULT_PIECE_QTY */
    public static final ParamKey RESULT_PIECE_QTY = new ParamKey("RESULT_PIECE_QTY");

    /** ROW_NO */
    public static final ParamKey ROW_NO = new ParamKey("ROW_NO");

    /** SEARCH_CRITERIA */
    public static final ParamKey SEARCH_CRITERIA = new ParamKey("SEARCH_CRITERIA");

    /** STOCK_CASE_QTY */
    public static final ParamKey STOCK_CASE_QTY = new ParamKey("STOCK_CASE_QTY");

    /** STOCK_PIECE_QTY */
    public static final ParamKey STOCK_PIECE_QTY = new ParamKey("STOCK_PIECE_QTY");

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
    public InventoryCompleteSCHParams()
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

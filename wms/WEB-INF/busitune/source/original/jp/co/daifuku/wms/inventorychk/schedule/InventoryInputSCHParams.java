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
public class InventoryInputSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA */
    public static final ParamKey AREA = new ParamKey("AREA");

    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** CONDITION_SELECT */
    public static final ParamKey CONDITION_SELECT = new ParamKey("CONDITION_SELECT");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CYCLE_COUNT */
    public static final ParamKey CYCLE_COUNT = new ParamKey("CYCLE_COUNT");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** INVENTCASEQTY */
    public static final ParamKey INVENTCASEQTY = new ParamKey("INVENTCASEQTY");

    /** INVENTORY_ONLY_DISP */
    public static final ParamKey INVENTORY_ONLY_DISP = new ParamKey("INVENTORY_ONLY_DISP");

    /** INVENTORY_QTY_INPUT */
    public static final ParamKey INVENTORY_QTY_INPUT = new ParamKey("INVENTORY_QTY_INPUT");

    /** INVENTORY_STOCK_QTY_REPORT */
    public static final ParamKey INVENTORY_STOCK_QTY_REPORT = new ParamKey("INVENTORY_STOCK_QTY_REPORT");

    /** INVENTPIECEQTY */
    public static final ParamKey INVENTPIECEQTY = new ParamKey("INVENTPIECEQTY");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** JOBNO */
    public static final ParamKey JOBNO = new ParamKey("JOBNO");

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

    /** NEWDATE_FLAG */
    public static final ParamKey NEWDATE_FLAG = new ParamKey("NEWDATE_FLAG");

    /** PROCESS_FLAG */
    public static final ParamKey PROCESS_FLAG = new ParamKey("PROCESS_FLAG");

    /** RESULT_CASE_QTY */
    public static final ParamKey RESULT_CASE_QTY = new ParamKey("RESULT_CASE_QTY");

    /** RESULT_PIECE_QTY */
    public static final ParamKey RESULT_PIECE_QTY = new ParamKey("RESULT_PIECE_QTY");

    /** SEARCH_CRITERIA */
    public static final ParamKey SEARCH_CRITERIA = new ParamKey("SEARCH_CRITERIA");

    /** STATUS_FLAG */
    public static final ParamKey STATUS_FLAG = new ParamKey("STATUS_FLAG");

    /** STATUSFLAG */
    public static final ParamKey STATUSFLAG = new ParamKey("STATUSFLAG");

    /** STOCK_CASE_QTY */
    public static final ParamKey STOCK_CASE_QTY = new ParamKey("STOCK_CASE_QTY");

    /** STOCK_MASTER */
    public static final ParamKey STOCK_MASTER = new ParamKey("STOCK_MASTER");

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
    public InventoryInputSCHParams()
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

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
public class StockMoveMntSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CANCEL */
    public static final ParamKey CANCEL = new ParamKey("CANCEL");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** FROM_MOVE_AREA */
    public static final ParamKey FROM_MOVE_AREA = new ParamKey("FROM_MOVE_AREA");

    /** FROM_MOVE_AREA_NO */
    public static final ParamKey FROM_MOVE_AREA_NO = new ParamKey("FROM_MOVE_AREA_NO");

    /** FUNCTION_ID */
    public static final ParamKey FUNCTION_ID = new ParamKey("FUNCTION_ID");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** JOB_NO */
    public static final ParamKey JOB_NO = new ParamKey("JOB_NO");

    /** L_ISSUE_REPORT */
    public static final ParamKey L_ISSUE_REPORT = new ParamKey("L_ISSUE_REPORT");

    /** LOCATION */
    public static final ParamKey LOCATION = new ParamKey("LOCATION");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** MOVE_AREA_NO */
    public static final ParamKey MOVE_AREA_NO = new ParamKey("MOVE_AREA_NO");

    /** MOVE_CASE_QTY */
    public static final ParamKey MOVE_CASE_QTY = new ParamKey("MOVE_CASE_QTY");

    /** MOVE_LOCATION_NO */
    public static final ParamKey MOVE_LOCATION_NO = new ParamKey("MOVE_LOCATION_NO");

    /** MOVE_PIECE_QTY */
    public static final ParamKey MOVE_PIECE_QTY = new ParamKey("MOVE_PIECE_QTY");

    /** PLAN_CASE_QTY */
    public static final ParamKey PLAN_CASE_QTY = new ParamKey("PLAN_CASE_QTY");

    /** PLAN_PIECE_QTY */
    public static final ParamKey PLAN_PIECE_QTY = new ParamKey("PLAN_PIECE_QTY");

    /** RETRIEVAL_AREA_NO */
    public static final ParamKey RETRIEVAL_AREA_NO = new ParamKey("RETRIEVAL_AREA_NO");

    /** RETRIEVAL_LOCATION_NO */
    public static final ParamKey RETRIEVAL_LOCATION_NO = new ParamKey("RETRIEVAL_LOCATION_NO");

    /** RETRIEVALDATE */
    public static final ParamKey RETRIEVALDATE = new ParamKey("RETRIEVALDATE");

    /** RFT_NO */
    public static final ParamKey RFT_NO = new ParamKey("RFT_NO");

    /** RFTNO */
    //public static final ParamKey RFTNO = new ParamKey("RFTNO");

    /** USER_NAME */
    public static final ParamKey USER_NAME = new ParamKey("USER_NAME");

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
    public StockMoveMntSCHParams()
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

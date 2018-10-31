package jp.co.daifuku.wms.receiving.schedule;

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
public class TcReceivingShortageMntSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** HIDDEN_CROSS_DOCK_UKEY */
    public static final ParamKey HIDDEN_CROSS_DOCK_UKEY = new ParamKey("HIDDEN_CROSS_DOCK_UKEY");

    /** HIDDEN_JOB_NO */
    public static final ParamKey HIDDEN_JOB_NO = new ParamKey("HIDDEN_JOB_NO");

    /** HIDDEN_LAST_UPDATEDATE_KEY */
    public static final ParamKey HIDDEN_LAST_UPDATEDATE_KEY = new ParamKey("HIDDEN_LAST_UPDATEDATE_KEY");

    /** HIDDEN_PLAN_UKEY */
    public static final ParamKey HIDDEN_PLAN_UKEY = new ParamKey("HIDDEN_PLAN_UKEY");

    /** HIDDEN_SETTING_UKEY */
    public static final ParamKey HIDDEN_SETTING_UKEY = new ParamKey("HIDDEN_SETTING_UKEY");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** PLAN_CASE_QTY */
    public static final ParamKey PLAN_CASE_QTY = new ParamKey("PLAN_CASE_QTY");

    /** PLAN_DAY */
    public static final ParamKey PLAN_DAY = new ParamKey("PLAN_DAY");

    /** PLAN_PIECE_QTY */
    public static final ParamKey PLAN_PIECE_QTY = new ParamKey("PLAN_PIECE_QTY");

    /** PROCESS_FLAG */
    public static final ParamKey PROCESS_FLAG = new ParamKey("PROCESS_FLAG");

    /** RECEIVE_LINE_NO */
    public static final ParamKey RECEIVE_LINE_NO = new ParamKey("RECEIVE_LINE_NO");

    /** RECEIVE_TICKET_NO */
    public static final ParamKey RECEIVE_TICKET_NO = new ParamKey("RECEIVE_TICKET_NO");

    /** RESULT_CASE_QTY */
    public static final ParamKey RESULT_CASE_QTY = new ParamKey("RESULT_CASE_QTY");

    /** RESULT_PIECE_QTY */
    public static final ParamKey RESULT_PIECE_QTY = new ParamKey("RESULT_PIECE_QTY");

    /** SHORTAGE_CASE_QTY */
    public static final ParamKey SHORTAGE_CASE_QTY = new ParamKey("SHORTAGE_CASE_QTY");

    /** SHORTAGE_PIECE_QTY */
    public static final ParamKey SHORTAGE_PIECE_QTY = new ParamKey("SHORTAGE_PIECE_QTY");

    /** SUPPLIER_CODE */
    public static final ParamKey SUPPLIER_CODE = new ParamKey("SUPPLIER_CODE");

    /** SUPPLIER_NAME */
    public static final ParamKey SUPPLIER_NAME = new ParamKey("SUPPLIER_NAME");

    /** TICKET_NO */
    public static final ParamKey TICKET_NO = new ParamKey("TICKET_NO");

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
    public TcReceivingShortageMntSCHParams()
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

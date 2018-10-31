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
public class ReceivingListCompleteSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** COLUMN_1 */
    public static final ParamKey COLUMN_1 = new ParamKey("COLUMN_1");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** HIDDEN_JOBNO */
    public static final ParamKey HIDDEN_JOBNO = new ParamKey("HIDDEN_JOBNO");

    /** HIDDEN_PLANUKEY */
    public static final ParamKey HIDDEN_PLANUKEY = new ParamKey("HIDDEN_PLANUKEY");

    /** INITIAL_INPUT_RECEIVING_NUMBER */
    public static final ParamKey INITIAL_INPUT_RECEIVING_NUMBER = new ParamKey("INITIAL_INPUT_RECEIVING_NUMBER");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** LINE */
    public static final ParamKey LINE = new ParamKey("LINE");

    /** LOT */
    public static final ParamKey LOT = new ParamKey("LOT");

    /** PLAN_CASE_QTY */
    public static final ParamKey PLAN_CASE_QTY = new ParamKey("PLAN_CASE_QTY");

    /** PLAN_PIECE_QTY */
    public static final ParamKey PLAN_PIECE_QTY = new ParamKey("PLAN_PIECE_QTY");

    /** RECEIVING_AREA */
    public static final ParamKey RECEIVING_AREA = new ParamKey("RECEIVING_AREA");

    /** RECEIVING_CASE_QTY */
    public static final ParamKey RECEIVING_CASE_QTY = new ParamKey("RECEIVING_CASE_QTY");

    /** RECEIVING_PIECE_QTY */
    public static final ParamKey RECEIVING_PIECE_QTY = new ParamKey("RECEIVING_PIECE_QTY");

    /** RECEIVING_PLAN_DATE */
    public static final ParamKey RECEIVING_PLAN_DATE = new ParamKey("RECEIVING_PLAN_DATE");

    /** SHORTAGE */
    public static final ParamKey SHORTAGE = new ParamKey("SHORTAGE");

    /** SUPPLIER_CODE */
    public static final ParamKey SUPPLIER_CODE = new ParamKey("SUPPLIER_CODE");

    /** SUPPLIER_NAME */
    public static final ParamKey SUPPLIER_NAME = new ParamKey("SUPPLIER_NAME");

    /** TICKET */
    public static final ParamKey TICKET = new ParamKey("TICKET");

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
    public ReceivingListCompleteSCHParams()
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

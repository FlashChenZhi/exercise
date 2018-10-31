package jp.co.daifuku.wms.storage.schedule;

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
public class StoragePlanRegistSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** BRANCH_NO */
    public static final ParamKey BRANCH_NO = new ParamKey("BRANCH_NO");

    /** CASE_ITF */
    public static final ParamKey CASE_ITF = new ParamKey("CASE_ITF");

    /** CASE_PACK */
    public static final ParamKey CASE_PACK = new ParamKey("CASE_PACK");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** JAN_CODE */
    public static final ParamKey JAN_CODE = new ParamKey("JAN_CODE");

    /** LINE */
    public static final ParamKey LINE = new ParamKey("LINE");

    /** LINE_NO */
    public static final ParamKey LINE_NO = new ParamKey("LINE_NO");

    /** LOT */
    public static final ParamKey LOT = new ParamKey("LOT");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** PLAN_CASE_QTY */
    public static final ParamKey PLAN_CASE_QTY = new ParamKey("PLAN_CASE_QTY");

    /** PLAN_PIECE_QTY */
    public static final ParamKey PLAN_PIECE_QTY = new ParamKey("PLAN_PIECE_QTY");

    /** SERIAL */
    public static final ParamKey SERIAL = new ParamKey("SERIAL");

    /** SLIP_NUMBER */
    public static final ParamKey SLIP_NUMBER = new ParamKey("SLIP_NUMBER");

    /** STORAGE_AREA */
    public static final ParamKey STORAGE_AREA = new ParamKey("STORAGE_AREA");

    /** STORAGE_LOCATION */
    public static final ParamKey STORAGE_LOCATION = new ParamKey("STORAGE_LOCATION");

    /** STORAGE_PLAN_DATE */
    public static final ParamKey STORAGE_PLAN_DATE = new ParamKey("STORAGE_PLAN_DATE");

    /** UPC_CODE */
    public static final ParamKey UPC_CODE = new ParamKey("UPC_CODE");

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
    public StoragePlanRegistSCHParams()
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

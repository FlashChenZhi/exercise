package jp.co.daifuku.wms.master.schedule;

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
public class ItemMasterModifySCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CASE_ENTERING_QTY */
    public static final ParamKey CASE_ENTERING_QTY = new ParamKey("CASE_ENTERING_QTY");

    /** CASE_ITF */
    public static final ParamKey CASE_ITF = new ParamKey("CASE_ITF");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** JAN_CODE */
    public static final ParamKey JAN_CODE = new ParamKey("JAN_CODE");

    /** LAST_UPDATE_DATE */
    public static final ParamKey LAST_UPDATE_DATE = new ParamKey("LAST_UPDATE_DATE");

    /** LAST_USED_DATE */
    public static final ParamKey LAST_USED_DATE = new ParamKey("LAST_USED_DATE");

    /** LOWER_STOCK_QTY */
    public static final ParamKey LOWER_STOCK_QTY = new ParamKey("LOWER_STOCK_QTY");

    /** PROCESS_FLAG */
    public static final ParamKey PROCESS_FLAG = new ParamKey("PROCESS_FLAG");

    /** SOFT_ZONE_ID */
    public static final ParamKey SOFT_ZONE_ID = new ParamKey("SOFT_ZONE_ID");

    /** UPPER_STOCK_QTY */
    public static final ParamKey UPPER_STOCK_QTY = new ParamKey("UPPER_STOCK_QTY");

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
    public ItemMasterModifySCHParams()
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

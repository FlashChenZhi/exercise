package jp.co.daifuku.wms.system.schedule;

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
public class RftAddSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** BTN_DISTINCTION */
    public static final ParamKey BTN_DISTINCTION = new ParamKey("BTN_DISTINCTION");

    /** IP_ADDRESS */
    public static final ParamKey IP_ADDRESS = new ParamKey("IP_ADDRESS");

    /** LANGUAGE */
    public static final ParamKey LANGUAGE = new ParamKey("LANGUAGE");

    /** MACHINE_NUMBER */
    public static final ParamKey MACHINE_NUMBER = new ParamKey("MACHINE_NUMBER");

    /** MODEL */
    public static final ParamKey MODEL = new ParamKey("MODEL");

    /** RFT_ASSORT */
    public static final ParamKey RFT_ASSORT = new ParamKey("RFT_ASSORT");

    /** TERMINALTYPE */
    public static final ParamKey TERMINALTYPE = new ParamKey("TERMINALTYPE");

    /** WORK_KIND_DC_RECEIVING */
    public static final ParamKey WORK_KIND_DC_RECEIVING = new ParamKey("WORK_KIND_DC_RECEIVING");

    /** WORK_KIND_INVENTRY */
    public static final ParamKey WORK_KIND_INVENTRY = new ParamKey("WORK_KIND_INVENTRY");

    /** WORK_KIND_NO_PLAN_RETRIEVAL */
    public static final ParamKey WORK_KIND_NO_PLAN_RETRIEVAL = new ParamKey("WORK_KIND_NO_PLAN_RETRIEVAL");

    /** WORK_KIND_NO_PLAN_STORAGE */
    public static final ParamKey WORK_KIND_NO_PLAN_STORAGE = new ParamKey("WORK_KIND_NO_PLAN_STORAGE");

    /** WORK_KIND_ORDER_RETRIEVAL */
    public static final ParamKey WORK_KIND_ORDER_RETRIEVAL = new ParamKey("WORK_KIND_ORDER_RETRIEVAL");

    /** WORK_KIND_RELOCATINO_RETRIEV */
    public static final ParamKey WORK_KIND_RELOCATINO_RETRIEV = new ParamKey("WORK_KIND_RELOCATINO_RETRIEV");

    /** WORK_KIND_RELOCATINO_STORAGE */
    public static final ParamKey WORK_KIND_RELOCATINO_STORAGE = new ParamKey("WORK_KIND_RELOCATINO_STORAGE");

    /** WORK_KIND_SHIPPING_LOADING */
    public static final ParamKey WORK_KIND_SHIPPING_LOADING = new ParamKey("WORK_KIND_SHIPPING_LOADING");

    /** WORK_KIND_SHIPPING_PICK */
    public static final ParamKey WORK_KIND_SHIPPING_PICK = new ParamKey("WORK_KIND_SHIPPING_PICK");

    /** WORK_KIND_SORT */
    public static final ParamKey WORK_KIND_SORT = new ParamKey("WORK_KIND_SORT");

    /** WORK_KIND_STORAGE */
    public static final ParamKey WORK_KIND_STORAGE = new ParamKey("WORK_KIND_STORAGE");

    /** WORK_KIND_STORAGE_RECEIVING */
    public static final ParamKey WORK_KIND_STORAGE_RECEIVING = new ParamKey("WORK_KIND_STORAGE_RECEIVING");

    /** WORK_KIND_TC_RECEIVING */
    public static final ParamKey WORK_KIND_TC_RECEIVING = new ParamKey("WORK_KIND_TC_RECEIVING");

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
    public RftAddSCHParams()
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

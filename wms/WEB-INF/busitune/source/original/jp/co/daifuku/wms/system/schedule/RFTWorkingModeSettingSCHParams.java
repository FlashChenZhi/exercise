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
public class RFTWorkingModeSettingSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CASE_PIESE_MODE */
    public static final ParamKey CASE_PIESE_MODE = new ParamKey("CASE_PIESE_MODE");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** INSPECTION_MODE */
    public static final ParamKey INSPECTION_MODE = new ParamKey("INSPECTION_MODE");

    /** ITF_TO_JAN */
    public static final ParamKey ITF_TO_JAN = new ParamKey("ITF_TO_JAN");

    /** RFT_NO */
    public static final ParamKey RFT_NO = new ParamKey("RFT_NO");

    /** WORK_KIND_INVENTRY */
    public static final ParamKey WORK_KIND_INVENTRY = new ParamKey("WORK_KIND_INVENTRY");

    /** WORK_KIND_NO_PLAN_RETRIEVAL */
    public static final ParamKey WORK_KIND_NO_PLAN_RETRIEVAL = new ParamKey("WORK_KIND_NO_PLAN_RETRIEVAL");

    /** WORK_KIND_NO_PLAN_STORAGE */
    public static final ParamKey WORK_KIND_NO_PLAN_STORAGE = new ParamKey("WORK_KIND_NO_PLAN_STORAGE");

    /** WORK_KIND_RECEIVING */
    public static final ParamKey WORK_KIND_RECEIVING = new ParamKey("WORK_KIND_RECEIVING");

    /** WORK_KIND_RELOCATINO_RETRIEV */
    public static final ParamKey WORK_KIND_RELOCATINO_RETRIEV = new ParamKey("WORK_KIND_RELOCATINO_RETRIEV");

    /** WORK_KIND_RELOCATINO_STORAGE */
    public static final ParamKey WORK_KIND_RELOCATINO_STORAGE = new ParamKey("WORK_KIND_RELOCATINO_STORAGE");

    /** WORK_KIND_RETRIEVAL */
    public static final ParamKey WORK_KIND_RETRIEVAL = new ParamKey("WORK_KIND_RETRIEVAL");

    /** WORK_KIND_SHIPPING */
    public static final ParamKey WORK_KIND_SHIPPING = new ParamKey("WORK_KIND_SHIPPING");

    /** WORK_KIND_SORT */
    public static final ParamKey WORK_KIND_SORT = new ParamKey("WORK_KIND_SORT");

    /** WORK_KIND_STORAGE */
    public static final ParamKey WORK_KIND_STORAGE = new ParamKey("WORK_KIND_STORAGE");

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
    public RFTWorkingModeSettingSCHParams()
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

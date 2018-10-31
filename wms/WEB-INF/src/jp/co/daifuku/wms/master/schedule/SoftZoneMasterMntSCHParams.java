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
public class SoftZoneMasterMntSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** BANK */
    public static final ParamKey BANK = new ParamKey("BANK");

    /** BANK_FROM */
    public static final ParamKey BANK_FROM = new ParamKey("BANK_FROM");

    /** BANK_TO */
    public static final ParamKey BANK_TO = new ParamKey("BANK_TO");

    /** BAY */
    public static final ParamKey BAY = new ParamKey("BAY");

    /** BAY_FROM */
    public static final ParamKey BAY_FROM = new ParamKey("BAY_FROM");

    /** BAY_TO */
    public static final ParamKey BAY_TO = new ParamKey("BAY_TO");

    /** LEVEL */
    public static final ParamKey LEVEL = new ParamKey("LEVEL");

    /** LEVEL_FROM */
    public static final ParamKey LEVEL_FROM = new ParamKey("LEVEL_FROM");

    /** LEVEL_TO */
    public static final ParamKey LEVEL_TO = new ParamKey("LEVEL_TO");

    /** SOFT_ZONE */
    public static final ParamKey SOFT_ZONE = new ParamKey("SOFT_ZONE");

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
    public SoftZoneMasterMntSCHParams()
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

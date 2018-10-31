package jp.co.daifuku.pcart.system.schedule;

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
public class PCTSystemSettingSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** A_RANK_STANDARD_VALUE */
    public static final ParamKey A_RANK_STANDARD_VALUE = new ParamKey("A_RANK_STANDARD_VALUE");

    /** B_RANK_STANDARD_VALUE */
    public static final ParamKey B_RANK_STANDARD_VALUE = new ParamKey("B_RANK_STANDARD_VALUE");

    /** CENTER_NAME */
    public static final ParamKey CENTER_NAME = new ParamKey("CENTER_NAME");

    /** DEFULT_DISTINCT_RATE */
    public static final ParamKey DEFULT_DISTINCT_RATE = new ParamKey("DEFULT_DISTINCT_RATE");

    /** ORICON_MAX_WEIGHT */
    public static final ParamKey ORICON_MAX_WEIGHT = new ParamKey("ORICON_MAX_WEIGHT");

    /** PCTMASTER_LOAD_FLAG */
    public static final ParamKey PCTMASTER_LOAD_FLAG = new ParamKey("PCTMASTER_LOAD_FLAG");

    /** RANK_SETTING_FLAG */
    public static final ParamKey RANK_SETTING_FLAG = new ParamKey("RANK_SETTING_FLAG");

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
    public PCTSystemSettingSCHParams()
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

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
public class RankSettingSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** A_RANK_STANDARD_VALUE */
    public static final ParamKey A_RANK_STANDARD_VALUE = new ParamKey("A_RANK_STANDARD_VALUE");

    /** AREA */
    public static final ParamKey AREA = new ParamKey("AREA");

    /** AREA_DISP */
    public static final ParamKey AREA_DISP = new ParamKey("AREA_DISP");

    /** B_RANK_STANDARD_VALUE */
    public static final ParamKey B_RANK_STANDARD_VALUE = new ParamKey("B_RANK_STANDARD_VALUE");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CONSIGNOR_CODE_DISP */
    public static final ParamKey CONSIGNOR_CODE_DISP = new ParamKey("CONSIGNOR_CODE_DISP");

    /** LAST_UP_DATE */
    public static final ParamKey LAST_UP_DATE = new ParamKey("LAST_UP_DATE");

    /** LEVEL */
    public static final ParamKey LEVEL = new ParamKey("LEVEL");

    /** LINE_COUNT */
    public static final ParamKey LINE_COUNT = new ParamKey("LINE_COUNT");

    /** LOT_COUNT */
    public static final ParamKey LOT_COUNT = new ParamKey("LOT_COUNT");

    /** ORDER_COUNT */
    public static final ParamKey ORDER_COUNT = new ParamKey("ORDER_COUNT");

    /** S_LINE_COUNT */
    public static final ParamKey S_LINE_COUNT = new ParamKey("S_LINE_COUNT");

    /** S_LOT_COUNT */
    public static final ParamKey S_LOT_COUNT = new ParamKey("S_LOT_COUNT");

    /** S_ORDER_COUNT */
    public static final ParamKey S_ORDER_COUNT = new ParamKey("S_ORDER_COUNT");

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
    public RankSettingSCHParams()
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

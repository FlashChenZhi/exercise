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
public class MessageLogInquirySCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CLASS */
    public static final ParamKey CLASS = new ParamKey("CLASS");

    /** CONTENT */
    public static final ParamKey CONTENT = new ParamKey("CONTENT");

    /** LOG */
    public static final ParamKey LOG = new ParamKey("LOG");

    /** LOG_DATE */
    public static final ParamKey LOG_DATE = new ParamKey("LOG_DATE");

    /** MESSAGE */
    public static final ParamKey MESSAGE = new ParamKey("MESSAGE");

    /** SEARCH_END_DAY */
    public static final ParamKey SEARCH_END_DAY = new ParamKey("SEARCH_END_DAY");

    /** SEARCH_END_TIME */
    public static final ParamKey SEARCH_END_TIME = new ParamKey("SEARCH_END_TIME");

    /** SEARCH_START_DAY */
    public static final ParamKey SEARCH_START_DAY = new ParamKey("SEARCH_START_DAY");

    /** SEARCH_START_TIME */
    public static final ParamKey SEARCH_START_TIME = new ParamKey("SEARCH_START_TIME");

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
    public MessageLogInquirySCHParams()
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

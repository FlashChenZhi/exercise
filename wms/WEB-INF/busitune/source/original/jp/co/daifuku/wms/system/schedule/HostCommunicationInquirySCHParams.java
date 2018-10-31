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
public class HostCommunicationInquirySCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** COMMUNICATION_DATA */
    public static final ParamKey COMMUNICATION_DATA = new ParamKey("COMMUNICATION_DATA");

    /** COMMUNICATION_TYPE */
    public static final ParamKey COMMUNICATION_TYPE = new ParamKey("COMMUNICATION_TYPE");

    /** FILE_NAME */
    public static final ParamKey FILE_NAME = new ParamKey("FILE_NAME");

    /** HID_START_DATE */
    public static final ParamKey HID_START_DATE = new ParamKey("HID_START_DATE");

    /** NO */
    public static final ParamKey NO = new ParamKey("NO");

    /** SEARCH_DATE_FROM */
    public static final ParamKey SEARCH_DATE_FROM = new ParamKey("SEARCH_DATE_FROM");

    /** SEARCH_DATE_TO */
    public static final ParamKey SEARCH_DATE_TO = new ParamKey("SEARCH_DATE_TO");

    /** SEARCH_TIME_FROM */
    public static final ParamKey SEARCH_TIME_FROM = new ParamKey("SEARCH_TIME_FROM");

    /** SEARCH_TIME_TO */
    public static final ParamKey SEARCH_TIME_TO = new ParamKey("SEARCH_TIME_TO");

    /** START_DATE */
    public static final ParamKey START_DATE = new ParamKey("START_DATE");

    /** STATUS */
    public static final ParamKey STATUS = new ParamKey("STATUS");

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
    public HostCommunicationInquirySCHParams()
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

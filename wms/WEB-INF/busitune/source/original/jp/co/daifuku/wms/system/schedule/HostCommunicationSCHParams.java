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
public class HostCommunicationSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CLASS_NAME */
    public static final ParamKey CLASS_NAME = new ParamKey("CLASS_NAME");

    /** CYCLE */
    public static final ParamKey CYCLE = new ParamKey("CYCLE");

    /** DATA_NAME */
    public static final ParamKey DATA_NAME = new ParamKey("DATA_NAME");

    /** ERROR_STATUS */
    public static final ParamKey ERROR_STATUS = new ParamKey("ERROR_STATUS");

    /** EXCHANGE_TYPE */
    public static final ParamKey EXCHANGE_TYPE = new ParamKey("EXCHANGE_TYPE");

    /** HIDDEN_CYCLE */
    public static final ParamKey HIDDEN_CYCLE = new ParamKey("HIDDEN_CYCLE");

    /** HIDDEN_ISSUE_LIST */
    public static final ParamKey HIDDEN_ISSUE_LIST = new ParamKey("HIDDEN_ISSUE_LIST");

    /** HIDDEN_TIME */
    public static final ParamKey HIDDEN_TIME = new ParamKey("HIDDEN_TIME");

    /** HOST_DISABLED */
    public static final ParamKey HOST_DISABLED = new ParamKey("HOST_DISABLED");

    /** IS_DEFINED */
    public static final ParamKey IS_DEFINED = new ParamKey("IS_DEFINED");

    /** ISSUE_LIST */
    public static final ParamKey ISSUE_LIST = new ParamKey("ISSUE_LIST");

    /** JOB_TYPE */
    public static final ParamKey JOB_TYPE = new ParamKey("JOB_TYPE");

    /** SPECIFIED_TIME */
    public static final ParamKey SPECIFIED_TIME = new ParamKey("SPECIFIED_TIME");

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
    public HostCommunicationSCHParams()
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

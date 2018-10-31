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
 * @version $Revision: 7241 $, $Date: 2010-02-26 14:32:14 +0900 (金, 26 2 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class MasterDataLoadSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AUTO_PRINT */
    public static final ParamKey AUTO_PRINT = new ParamKey("AUTO_PRINT");

    /** CLASS_NAME */
    public static final ParamKey CLASS_NAME = new ParamKey("CLASS_NAME");

    /** DATA_TYPE */
    public static final ParamKey DATA_TYPE = new ParamKey("DATA_TYPE");

    /** FILES_COUNT */
    public static final ParamKey FILES_COUNT = new ParamKey("FILES_COUNT");

    /** LOAD_DATA_TYPE */
    public static final ParamKey LOAD_DATA_TYPE = new ParamKey("LOAD_DATA_TYPE");

    /** MESSAGE */
    public static final ParamKey MESSAGE = new ParamKey("MESSAGE");

    /** SELECT */
    public static final ParamKey SELECT = new ParamKey("SELECT");

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
    public MasterDataLoadSCHParams()
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

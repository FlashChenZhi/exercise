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
 * Transfer Object class is used for transfering the data between Business and Scheduler classes
 *
 * @version $Revision: 7650 $, $Date: 2010-03-17 18:31:17 +0900 (水, 17 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class HostDBDataLoadSCHParams
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

    /** IMPORT_DATA_TYPE */
    public static final ParamKey IMPORT_DATA_TYPE = new ParamKey("IMPORT_DATA_TYPE");

    /** FILES_COUNT */
    public static final ParamKey FILES_COUNT = new ParamKey("FILES_COUNT");

    /** NO_OF_IMPORT_MSGS */
    public static final ParamKey NO_OF_IMPORT_MSGS = new ParamKey("NO_OF_IMPORT_MSGS");

    /** RESULT_OF_IMPORT */
    public static final ParamKey RESULT_OF_IMPORT = new ParamKey("RESULT_OF_IMPORT");

    /** SELECT */
    public static final ParamKey SELECT = new ParamKey("SELECT");

    //the stuff above came from Busitune; the stuff below is the kitchen sink stuff that we added

    /**CUMULATIVE_REGIST_FLAG */
    //Watch to see if the regist flag was set for an message of a particular type
    public static final ParamKey CUMULATIVE_REGIST_FLAG = new ParamKey("CUMULATIVE_REGIST_FLAG");

    /**CUMULATIVE_SKIP_FLAG */
    //Watch to see if any message was skipped for a particular type
    public static final ParamKey CUMULATIVE_SKIP_FLAG = new ParamKey("CUMULATIVE_SKIP_FLAG");

    /**CUMULATIVE_RESULT_FLAG */
    //True until a process for a particular message comes back false
    public static final ParamKey CUMULATIVE_RESULT_FLAG = new ParamKey("CUMULATIVE_RESULT_FLAG");

    /**EXPECTED_LOADLINES_COUNT */
    //The number of lines of a type we expect to load
    public static final ParamKey EXPECTED_LOADLINES_COUNT = new ParamKey("EXPECTED_LOADLINES_COUNT");

    /**ATTEMPTED_LOADLINES_COUNT */
    //Increment this number each time we try to load a line (used to test whether we have exceeded the max
    public static final ParamKey ATTEMPTED_LOADLINES_COUNT = new ParamKey("ATTEMPTED_LOADLINES_COUNT");

    /**REGIST_TIME */
    //Use this timestamp to generate file names
    public static final ParamKey REGIST_TIME = new ParamKey("REGIST_TIME");

    /**CUMULATIVE_WARNING_FLAG */
    //Watch to see if a warning occurs for a particular type
    public static final ParamKey CUMULATIVE_WARNING_FLAG = new ParamKey("CUMULATIVE_WARNING_FLAG");

    /**LOADER_MESSAGE */
    //A message returned by the loader
    public static final ParamKey LOADER_MESSAGE = new ParamKey("LOADER_MESSAGE");

    /**THE_LOADER */
    //A loader of the correct type for this message
    public static final ParamKey THE_LOADER = new ParamKey("THE_LOADER");

    /**THE LOADERS LIMIT */
    //This is the limit beyond which the loader will not load (from WMSParam.properties)
    public static final ParamKey THE_LOADERS_LIMIT = new ParamKey("THE_LOADERS_LIMIT");


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**
     * Data Type (a two-digit code)
     */
    private String _dataType;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public HostDBDataLoadSCHParams()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * データ区分を返します。
     * @return データ区分を返します。
     */
    public String getDataType()
    {
        return _dataType;
    }

    /**
     * データ区分を設定します。
     * @param dataType データ区分
     */
    public void setDataType(String dataType)
    {
        _dataType = dataType;
    }

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

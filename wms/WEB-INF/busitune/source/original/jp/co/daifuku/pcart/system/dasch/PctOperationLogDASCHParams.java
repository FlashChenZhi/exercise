// $Id: PctOperationLogDASCHParams.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.pcart.system.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.util.Map;
import jp.co.daifuku.bluedog.util.StringParameters;
import jp.co.daifuku.foundation.common.Key;
import jp.co.daifuku.foundation.common.ParamKey;
import jp.co.daifuku.wms.base.common.WmsDASCHParams;

/**
 * BusinessクラスとDASCH間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class PctOperationLogDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DETAIL */
    public static final ParamKey DETAIL = new ParamKey("DETAIL");

    /** DS_NO */
    public static final ParamKey DS_NO = new ParamKey("DS_NO");

    /** FROM_SEARCH_DATE */
    public static final ParamKey FROM_SEARCH_DATE = new ParamKey("FROM_SEARCH_DATE");

    /** FROM_SEARCH_TIME */
    public static final ParamKey FROM_SEARCH_TIME = new ParamKey("FROM_SEARCH_TIME");

    /** HIDDEN_LOG_DAY */
    public static final ParamKey HIDDEN_LOG_DAY = new ParamKey("HIDDEN_LOG_DAY");

    /** HIDDEN_OPERATION_TYPE */
    public static final ParamKey HIDDEN_OPERATION_TYPE = new ParamKey("HIDDEN_OPERATION_TYPE");

    /** IP_ADDRESS */
    public static final ParamKey IP_ADDRESS = new ParamKey("IP_ADDRESS");

    /** LINE_NO */
    public static final ParamKey LINE_NO = new ParamKey("LINE_NO");

    /** LOG_DAY */
    public static final ParamKey LOG_DAY = new ParamKey("LOG_DAY");

    /** LOG_TIME */
    public static final ParamKey LOG_TIME = new ParamKey("LOG_TIME");

    /** OPERATION_TYPE */
    public static final ParamKey OPERATION_TYPE = new ParamKey("OPERATION_TYPE");

    /** PAGENAME_RESOURCE_KEY */
    public static final ParamKey PAGENAME_RESOURCE_KEY = new ParamKey("PAGENAME_RESOURCE_KEY");

    /** SCREEN_NAME */
    public static final ParamKey SCREEN_NAME = new ParamKey("SCREEN_NAME");

    /** TERMINAL_NAME */
    public static final ParamKey TERMINAL_NAME = new ParamKey("TERMINAL_NAME");

    /** TO_SEARCH_DATE */
    public static final ParamKey TO_SEARCH_DATE = new ParamKey("TO_SEARCH_DATE");

    /** TO_SEARCH_TIME */
    public static final ParamKey TO_SEARCH_TIME = new ParamKey("TO_SEARCH_TIME");

    /** USER_ID */
    public static final ParamKey USER_ID = new ParamKey("USER_ID");

    /** USER_NAME */
    public static final ParamKey USER_NAME = new ParamKey("USER_NAME");

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
    public PctOperationLogDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public PctOperationLogDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public PctOperationLogDASCHParams(Map<Key, Object> initMap)
    {
        super(initMap);
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
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class

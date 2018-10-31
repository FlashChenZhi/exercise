// $Id: DaschParams_ja.java 112 2008-10-06 10:51:43Z admin $
package jp.co.daifuku.wms.system.dasch;

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
 * @version $Revision: 112 $, $Date:: 2008-10-06 19:51:43 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class DataLoadDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DATA */
    public static final ParamKey DATA = new ParamKey("DATA");

    /** DATA_TYPE */
    public static final ParamKey DATA_TYPE = new ParamKey("DATA_TYPE");

    /** DFK_DS_NO */
    public static final ParamKey DFK_DS_NO = new ParamKey("DFK_DS_NO");

    /** DFK_USER_ID */
    public static final ParamKey DFK_USER_ID = new ParamKey("DFK_USER_ID");

    /** DFK_USER_NAME */
    public static final ParamKey DFK_USER_NAME = new ParamKey("DFK_USER_NAME");

    /** ERROR_DETAIL */
    public static final ParamKey ERROR_DETAIL = new ParamKey("ERROR_DETAIL");

    /** ERROR_LEVEL */
    public static final ParamKey ERROR_LEVEL = new ParamKey("ERROR_LEVEL");

    /** FILE_LINE_NO */
    public static final ParamKey FILE_LINE_NO = new ParamKey("FILE_LINE_NO");

    /** FILE_NAME */
    public static final ParamKey FILE_NAME = new ParamKey("FILE_NAME");

    /** ITEM_NO */
    public static final ParamKey ITEM_NO = new ParamKey("ITEM_NO");

    /** LOAD_START_DATE */
    public static final ParamKey LOAD_START_DATE = new ParamKey("LOAD_START_DATE");

    /** LOAD_TYPE */
    public static final ParamKey LOAD_TYPE = new ParamKey("LOAD_TYPE");

    /** START_DAY */
    public static final ParamKey START_DAY = new ParamKey("START_DAY");

    /** START_TIME */
    public static final ParamKey START_TIME = new ParamKey("START_TIME");

    /** STATUS */
    public static final ParamKey STATUS = new ParamKey("STATUS");

    /** SYS_DAY */
    public static final ParamKey SYS_DAY = new ParamKey("SYS_DAY");

    /** SYS_TIME */
    public static final ParamKey SYS_TIME = new ParamKey("SYS_TIME");

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
    public DataLoadDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public DataLoadDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public DataLoadDASCHParams(Map<Key, Object> initMap)
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

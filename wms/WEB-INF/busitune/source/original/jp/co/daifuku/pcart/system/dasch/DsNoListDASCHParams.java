// $Id: DsNoListDASCHParams.java 5290 2009-10-28 04:29:37Z kishimoto $
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
public class DsNoListDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DS_NO */
    public static final ParamKey DS_NO = new ParamKey("DS_NO");

    /** FROM_SEARCH_DATE */
    public static final ParamKey FROM_SEARCH_DATE = new ParamKey("FROM_SEARCH_DATE");

    /** FROM_SEARCH_TIME */
    public static final ParamKey FROM_SEARCH_TIME = new ParamKey("FROM_SEARCH_TIME");

    /** SCREEN_NAME */
    public static final ParamKey SCREEN_NAME = new ParamKey("SCREEN_NAME");

    /** SELECT */
    public static final ParamKey SELECT = new ParamKey("SELECT");

    /** TO_SEARCH_DATE */
    public static final ParamKey TO_SEARCH_DATE = new ParamKey("TO_SEARCH_DATE");

    /** TO_SEARCH_TIME */
    public static final ParamKey TO_SEARCH_TIME = new ParamKey("TO_SEARCH_TIME");

    /** USER_ID */
    public static final ParamKey USER_ID = new ParamKey("USER_ID");

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
    public DsNoListDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public DsNoListDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public DsNoListDASCHParams(Map<Key, Object> initMap)
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

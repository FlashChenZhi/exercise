// $Id: LstLocateHistoryDASCHParams.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.wms.part11.dasch;

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
public class LstLocateHistoryDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AISLE_NO */
    public static final ParamKey AISLE_NO = new ParamKey("AISLE_NO");

    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** DISPFROMDAY_KEY */
    public static final ParamKey DISPFROMDAY_KEY = new ParamKey("DISPFROMDAY_KEY");

    /** DISPFROMTIME_KEY */
    public static final ParamKey DISPFROMTIME_KEY = new ParamKey("DISPFROMTIME_KEY");

    /** DISPTODAY_KEY */
    public static final ParamKey DISPTODAY_KEY = new ParamKey("DISPTODAY_KEY");

    /** DISPTOTIME_KEY */
    public static final ParamKey DISPTOTIME_KEY = new ParamKey("DISPTOTIME_KEY");

    /** DSNUMBER_KEY */
    public static final ParamKey DSNUMBER_KEY = new ParamKey("DSNUMBER_KEY");

    /** IP_ADDRESS */
    public static final ParamKey IP_ADDRESS = new ParamKey("IP_ADDRESS");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** LOGDATE_DAY */
    public static final ParamKey LOGDATE_DAY = new ParamKey("LOGDATE_DAY");

    /** LOGDATE_TIME */
    public static final ParamKey LOGDATE_TIME = new ParamKey("LOGDATE_TIME");

    /** NO */
    public static final ParamKey NO = new ParamKey("NO");

    /** PAGENAMERESOURCEKEY */
    public static final ParamKey PAGENAMERESOURCEKEY = new ParamKey("PAGENAMERESOURCEKEY");

    /** TABLE_NAME */
    public static final ParamKey TABLE_NAME = new ParamKey("TABLE_NAME");

    /** TERMINAL_NAME */
    public static final ParamKey TERMINAL_NAME = new ParamKey("TERMINAL_NAME");

    /** UPDATE_AISLE_NO */
    public static final ParamKey UPDATE_AISLE_NO = new ParamKey("UPDATE_AISLE_NO");

    /** UPDATE_KIND */
    public static final ParamKey UPDATE_KIND = new ParamKey("UPDATE_KIND");

    /** USER_ID */
    public static final ParamKey USER_ID = new ParamKey("USER_ID");

    /** USER_NAME */
    public static final ParamKey USER_NAME = new ParamKey("USER_NAME");

    /** USERID_KEY */
    public static final ParamKey USERID_KEY = new ParamKey("USERID_KEY");

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
    public LstLocateHistoryDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstLocateHistoryDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstLocateHistoryDASCHParams(Map<Key, Object> initMap)
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

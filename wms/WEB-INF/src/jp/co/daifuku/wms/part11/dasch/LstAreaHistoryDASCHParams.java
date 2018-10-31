// $Id: LstAreaHistoryDASCHParams.java,v 1.1.1.1 2009/02/10 08:55:41 arai Exp $
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
 * @version $Revision: 1.1.1.1 $, $Date: 2009/02/10 08:55:41 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class LstAreaHistoryDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NAME */
    public static final ParamKey AREA_NAME = new ParamKey("AREA_NAME");

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

    /** IP_ADRESS */
    public static final ParamKey IP_ADRESS = new ParamKey("IP_ADRESS");

    /** LOGDATE_DAY */
    public static final ParamKey LOGDATE_DAY = new ParamKey("LOGDATE_DAY");

    /** LOGDATE_TIME */
    public static final ParamKey LOGDATE_TIME = new ParamKey("LOGDATE_TIME");

    /** NO */
    public static final ParamKey NO = new ParamKey("NO");

    /** PAGENAMERESOURCEKEY */
    public static final ParamKey PAGENAMERESOURCEKEY = new ParamKey("PAGENAMERESOURCEKEY");

    /** RECEIVING_AREA */
    public static final ParamKey RECEIVING_AREA = new ParamKey("RECEIVING_AREA");

    // DFKLOOK ここから修正 不要なので、コメントアウト
    /** TABLE_NAME */
//    public static final ParamKey TABLE_NAME = new ParamKey("TABLE_NAME");
    // DFKLOOK ここまで修正

    /** TEMPORARY_AREA */
    public static final ParamKey TEMPORARY_AREA = new ParamKey("TEMPORARY_AREA");

    /** TEMPORARY_AREA_TYPE */
    public static final ParamKey TEMPORARY_AREA_TYPE = new ParamKey("TEMPORARY_AREA_TYPE");

    /** TERMINAL_NAME */
    public static final ParamKey TERMINAL_NAME = new ParamKey("TERMINAL_NAME");

    /** UPDATE_AREA_NAME */
    public static final ParamKey UPDATE_AREA_NAME = new ParamKey("UPDATE_AREA_NAME");

    /** UPDATE_KIND */
    public static final ParamKey UPDATE_KIND = new ParamKey("UPDATE_KIND");

    /** UPDATE_RECEIVING_AREA */
    public static final ParamKey UPDATE_RECEIVING_AREA = new ParamKey("UPDATE_RECEIVING_AREA");

    /** UPDATE_TEMP_AREA */
    public static final ParamKey UPDATE_TEMP_AREA = new ParamKey("UPDATE_TEMP_AREA");

    /** UPDATE_TEMP_AREA_TYPE */
    public static final ParamKey UPDATE_TEMP_AREA_TYPE = new ParamKey("UPDATE_TEMP_AREA_TYPE");

    /** UPDATE_VACANT_SEARCH_TYPE */
    public static final ParamKey UPDATE_VACANT_SEARCH_TYPE = new ParamKey("UPDATE_VACANT_SEARCH_TYPE");

    /** USER_ID */
    public static final ParamKey USER_ID = new ParamKey("USER_ID");

    /** USER_NAME */
    public static final ParamKey USER_NAME = new ParamKey("USER_NAME");

    /** USERID_KEY */
    public static final ParamKey USERID_KEY = new ParamKey("USERID_KEY");

    /** VACANT_SEARCH_TYPE */
    public static final ParamKey VACANT_SEARCH_TYPE = new ParamKey("VACANT_SEARCH_TYPE");

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
    public LstAreaHistoryDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstAreaHistoryDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstAreaHistoryDASCHParams(Map<Key, Object> initMap)
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

// $Id: LstItemHistoryDASCHParams.java,v 1.1.1.1 2009/02/10 08:55:41 arai Exp $
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
public class LstItemHistoryDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
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

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** IP_ADDRESS */
    public static final ParamKey IP_ADDRESS = new ParamKey("IP_ADDRESS");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** ITF */
    public static final ParamKey ITF = new ParamKey("ITF");

    /** JAN */
    public static final ParamKey JAN = new ParamKey("JAN");

    /** LOGDATE_DAY */
    public static final ParamKey LOGDATE_DAY = new ParamKey("LOGDATE_DAY");

    /** LOGDATE_TIME */
    public static final ParamKey LOGDATE_TIME = new ParamKey("LOGDATE_TIME");

    /** LOWER_QTY */
    public static final ParamKey LOWER_QTY = new ParamKey("LOWER_QTY");

    /** NO */
    public static final ParamKey NO = new ParamKey("NO");

    /** PAGENAMERESOURCEKEY */
    public static final ParamKey PAGENAMERESOURCEKEY = new ParamKey("PAGENAMERESOURCEKEY");

    /** SOFT_ZONE_ID */
    public static final ParamKey SOFT_ZONE_ID = new ParamKey("SOFT_ZONE_ID");

    // DFKLOOK start
//    /** TABLE_NAME */
//    public static final ParamKey TABLE_NAME = new ParamKey("TABLE_NAME");
    // DFKLOOK end

    /** TEMPORARY_TYPE */
    public static final ParamKey TEMPORARY_TYPE = new ParamKey("TEMPORARY_TYPE");

    /** TERMINAL_NAME */
    public static final ParamKey TERMINAL_NAME = new ParamKey("TERMINAL_NAME");

    /** UPDATE_ENTERING_QTY */
    public static final ParamKey UPDATE_ENTERING_QTY = new ParamKey("UPDATE_ENTERING_QTY");

    /** UPDATE_ITEM_NAME */
    public static final ParamKey UPDATE_ITEM_NAME = new ParamKey("UPDATE_ITEM_NAME");

    /** UPDATE_ITF */
    public static final ParamKey UPDATE_ITF = new ParamKey("UPDATE_ITF");

    /** UPDATE_JAN */
    public static final ParamKey UPDATE_JAN = new ParamKey("UPDATE_JAN");

    /** UPDATE_KIND */
    public static final ParamKey UPDATE_KIND = new ParamKey("UPDATE_KIND");

    /** UPDATE_LOWER_QTY */
    public static final ParamKey UPDATE_LOWER_QTY = new ParamKey("UPDATE_LOWER_QTY");

    /** UPDATE_SOFT_ZONE_ID */
    public static final ParamKey UPDATE_SOFT_ZONE_ID = new ParamKey("UPDATE_SOFT_ZONE_ID");

    /** UPDATE_TEMPORARY_TYPE */
    public static final ParamKey UPDATE_TEMPORARY_TYPE = new ParamKey("UPDATE_TEMPORARY_TYPE");

    /** UPDATE_UPPER_QTY */
    public static final ParamKey UPDATE_UPPER_QTY = new ParamKey("UPDATE_UPPER_QTY");

    /** UPPER_QTY */
    public static final ParamKey UPPER_QTY = new ParamKey("UPPER_QTY");

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
    public LstItemHistoryDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstItemHistoryDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstItemHistoryDASCHParams(Map<Key, Object> initMap)
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

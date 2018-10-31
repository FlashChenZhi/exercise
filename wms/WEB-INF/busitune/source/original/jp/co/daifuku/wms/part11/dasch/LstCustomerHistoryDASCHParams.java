// $Id: LstCustomerHistoryDASCHParams.java 7590 2010-03-15 13:47:52Z kishimoto $
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
 * @version $Revision: 7590 $, $Date:: 2010-03-15 22:47:52 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class LstCustomerHistoryDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ADDRESS1 */
    public static final ParamKey ADDRESS1 = new ParamKey("ADDRESS1");

    /** ADDRESS2 */
    public static final ParamKey ADDRESS2 = new ParamKey("ADDRESS2");

    /** CONTACT1 */
    public static final ParamKey CONTACT1 = new ParamKey("CONTACT1");

    /** CONTACT2 */
    public static final ParamKey CONTACT2 = new ParamKey("CONTACT2");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** CUSTOMER_NAME */
    public static final ParamKey CUSTOMER_NAME = new ParamKey("CUSTOMER_NAME");

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

    /** LOGDATE_DAY */
    public static final ParamKey LOGDATE_DAY = new ParamKey("LOGDATE_DAY");

    /** LOGDATE_TIME */
    public static final ParamKey LOGDATE_TIME = new ParamKey("LOGDATE_TIME");

    /** NO */
    public static final ParamKey NO = new ParamKey("NO");

    /** PAGENAMERESOURCEKEY */
    public static final ParamKey PAGENAMERESOURCEKEY = new ParamKey("PAGENAMERESOURCEKEY");

    /** POSTAL_CODE */
    public static final ParamKey POSTAL_CODE = new ParamKey("POSTAL_CODE");

    /** PREFECTURE */
    public static final ParamKey PREFECTURE = new ParamKey("PREFECTURE");

    /** ROUTE */
    public static final ParamKey ROUTE = new ParamKey("ROUTE");

    /** SORTING_PLACE */
    public static final ParamKey SORTING_PLACE = new ParamKey("SORTING_PLACE");

    /** TABLE_NAME */
    public static final ParamKey TABLE_NAME = new ParamKey("TABLE_NAME");

    /** TELEPHONE */
    public static final ParamKey TELEPHONE = new ParamKey("TELEPHONE");

    /** TERMINAL_NAME */
    public static final ParamKey TERMINAL_NAME = new ParamKey("TERMINAL_NAME");

    /** UPDATE_ADDRESS1 */
    public static final ParamKey UPDATE_ADDRESS1 = new ParamKey("UPDATE_ADDRESS1");

    /** UPDATE_ADDRESS2 */
    public static final ParamKey UPDATE_ADDRESS2 = new ParamKey("UPDATE_ADDRESS2");

    /** UPDATE_CONTACT1 */
    public static final ParamKey UPDATE_CONTACT1 = new ParamKey("UPDATE_CONTACT1");

    /** UPDATE_CONTACT2 */
    public static final ParamKey UPDATE_CONTACT2 = new ParamKey("UPDATE_CONTACT2");

    /** UPDATE_CUSTOMER_NAME */
    public static final ParamKey UPDATE_CUSTOMER_NAME = new ParamKey("UPDATE_CUSTOMER_NAME");

    /** UPDATE_KIND */
    public static final ParamKey UPDATE_KIND = new ParamKey("UPDATE_KIND");

    /** UPDATE_POSTAL_CODE */
    public static final ParamKey UPDATE_POSTAL_CODE = new ParamKey("UPDATE_POSTAL_CODE");

    /** UPDATE_PREFECTURE */
    public static final ParamKey UPDATE_PREFECTURE = new ParamKey("UPDATE_PREFECTURE");

    /** UPDATE_ROUTE */
    public static final ParamKey UPDATE_ROUTE = new ParamKey("UPDATE_ROUTE");

    /** UPDATE_SORTING_PLACE */
    public static final ParamKey UPDATE_SORTING_PLACE = new ParamKey("UPDATE_SORTING_PLACE");

    /** UPDATE_TELEPHONE */
    public static final ParamKey UPDATE_TELEPHONE = new ParamKey("UPDATE_TELEPHONE");

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
    public LstCustomerHistoryDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstCustomerHistoryDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstCustomerHistoryDASCHParams(Map<Key, Object> initMap)
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

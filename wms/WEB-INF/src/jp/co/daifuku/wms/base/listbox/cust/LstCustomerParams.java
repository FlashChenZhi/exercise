// $Id: LstCustomerParams.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.base.listbox.cust;

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
import jp.co.daifuku.foundation.common.Params;

/**
 * 親画面とポップアップ画面間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class LstCustomerParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ADDRESS1 */
    public static final ParamKey ADDRESS1 = new ParamKey("ADDRESS1");

    /** ADDRESS2 */
    public static final ParamKey ADDRESS2 = new ParamKey("ADDRESS2");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CONSOLIDATION_AREA */
    public static final ParamKey CONSOLIDATION_AREA = new ParamKey("CONSOLIDATION_AREA");

    /** CONTACT1 */
    public static final ParamKey CONTACT1 = new ParamKey("CONTACT1");

    /** CONTACT2 */
    public static final ParamKey CONTACT2 = new ParamKey("CONTACT2");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** CUSTOMER_NAME */
    public static final ParamKey CUSTOMER_NAME = new ParamKey("CUSTOMER_NAME");

    /** FROM_TO_FLAG */
    public static final ParamKey FROM_TO_FLAG = new ParamKey("FROM_TO_FLAG");

    /** POSTAL_CODE */
    public static final ParamKey POSTAL_CODE = new ParamKey("POSTAL_CODE");

    /** PREFECTURE */
    public static final ParamKey PREFECTURE = new ParamKey("PREFECTURE");

    /** ROUTE */
    public static final ParamKey ROUTE = new ParamKey("ROUTE");

    /** SORT_PLACE */
    public static final ParamKey SORT_PLACE = new ParamKey("SORT_PLACE");

    /** SORT_PLACE_DISP */
    public static final ParamKey SORT_PLACE_DISP = new ParamKey("SORT_PLACE_DISP");

    /** TELEPHONE */
    public static final ParamKey TELEPHONE = new ParamKey("TELEPHONE");

    /** TO_CUSTOMER_CODE */
    public static final ParamKey TO_CUSTOMER_CODE = new ParamKey("TO_CUSTOMER_CODE");

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
     * デフォルトコンストラクターです。
     */
    public LstCustomerParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstCustomerParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstCustomerParams(Map<Key, Object> initMap)
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

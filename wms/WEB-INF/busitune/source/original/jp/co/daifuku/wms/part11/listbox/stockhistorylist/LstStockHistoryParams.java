// $Id: LstStockHistoryParams.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.wms.part11.listbox.stockhistorylist;

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
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class LstStockHistoryParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
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

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** STYLE_KEY */
    public static final ParamKey STYLE_KEY = new ParamKey("STYLE_KEY");

    /** TABLE_NAME */
    public static final ParamKey TABLE_NAME = new ParamKey("TABLE_NAME");

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
     * デフォルトコンストラクターです。
     */
    public LstStockHistoryParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstStockHistoryParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstStockHistoryParams(Map<Key, Object> initMap)
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

// $Id: LstAsStockDetailNoBtnDASCHParams.java 6560 2009-12-18 09:03:17Z okayama $
package jp.co.daifuku.wms.asrs.dasch;

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
 * @version $Revision: 6560 $, $Date:: 2009-12-18 18:03:17 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class LstAsStockDetailNoBtnDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** COLUMN_2 */
    public static final ParamKey COLUMN_2 = new ParamKey("COLUMN_2");

    /** COLUMN_3 */
    public static final ParamKey COLUMN_3 = new ParamKey("COLUMN_3");

    /** COLUMN_4 */
    public static final ParamKey COLUMN_4 = new ParamKey("COLUMN_4");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** NO */
    public static final ParamKey NO = new ParamKey("NO");

    /** PALLET_ID */
    public static final ParamKey PALLET_ID = new ParamKey("PALLET_ID");

    /** SEARCH_CONDITION */
    public static final ParamKey SEARCH_CONDITION = new ParamKey("SEARCH_CONDITION");

    /** SHELF_STATUS */
    public static final ParamKey SHELF_STATUS = new ParamKey("SHELF_STATUS");

    /** STOCK_QTY */
    public static final ParamKey STOCK_QTY = new ParamKey("STOCK_QTY");

    /** STORAGE_DATETIME */
    public static final ParamKey STORAGE_DATETIME = new ParamKey("STORAGE_DATETIME");

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
    public LstAsStockDetailNoBtnDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstAsStockDetailNoBtnDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstAsStockDetailNoBtnDASCHParams(Map<Key, Object> initMap)
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

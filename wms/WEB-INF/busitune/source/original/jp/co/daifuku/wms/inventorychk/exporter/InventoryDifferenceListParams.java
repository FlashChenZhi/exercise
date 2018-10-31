// $Id: InventoryDifferenceListParams.java 7449 2010-03-08 04:24:04Z okayama $
package jp.co.daifuku.wms.inventorychk.exporter;

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
 * BusinessクラスとExporter間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7449 $, $Date:: 2010-03-08 13:24:04 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class InventoryDifferenceListParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AFTER_STOCK_CASE_QTY */
    public static final ParamKey AFTER_STOCK_CASE_QTY = new ParamKey("AFTER_STOCK_CASE_QTY");

    /** AFTER_STOCK_PIECE_QTY */
    public static final ParamKey AFTER_STOCK_PIECE_QTY = new ParamKey("AFTER_STOCK_PIECE_QTY");

    /** AREA_NAME */
    public static final ParamKey AREA_NAME = new ParamKey("AREA_NAME");

    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** BEFORE_STOCK_CASE_QTY */
    public static final ParamKey BEFORE_STOCK_CASE_QTY = new ParamKey("BEFORE_STOCK_CASE_QTY");

    /** BEFORE_STOCK_PIECE_QTY */
    public static final ParamKey BEFORE_STOCK_PIECE_QTY = new ParamKey("BEFORE_STOCK_PIECE_QTY");

    /** DFK_DS_NO */
    public static final ParamKey DFK_DS_NO = new ParamKey("DFK_DS_NO");

    /** DFK_USER_ID */
    public static final ParamKey DFK_USER_ID = new ParamKey("DFK_USER_ID");

    /** DFK_USER_NAME */
    public static final ParamKey DFK_USER_NAME = new ParamKey("DFK_USER_NAME");

    /** DIFFERENCE_CASE_QTY */
    public static final ParamKey DIFFERENCE_CASE_QTY = new ParamKey("DIFFERENCE_CASE_QTY");

    /** DIFFERENCE_PIECE_QTY */
    public static final ParamKey DIFFERENCE_PIECE_QTY = new ParamKey("DIFFERENCE_PIECE_QTY");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** INVENTORY_STOCK_CASE_QTY */
    public static final ParamKey INVENTORY_STOCK_CASE_QTY = new ParamKey("INVENTORY_STOCK_CASE_QTY");

    /** INVENTORY_STOCK_PIECE_QTY */
    public static final ParamKey INVENTORY_STOCK_PIECE_QTY = new ParamKey("INVENTORY_STOCK_PIECE_QTY");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

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
    public InventoryDifferenceListParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public InventoryDifferenceListParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public InventoryDifferenceListParams(Map<Key, Object> initMap)
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

// $Id: DaschParams_ja.java 112 2008-10-06 10:51:43Z admin $
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
 * @version $Revision: 112 $, $Date:: 2008-10-06 19:51:43 +0900 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaInquiryRetrievalDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ALLOCATE_QTY */
    public static final ParamKey ALLOCATE_QTY = new ParamKey("ALLOCATE_QTY");

    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** AREA_TYPE */
    public static final ParamKey AREA_TYPE = new ParamKey("AREA_TYPE");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** FROM_RM_NO */
    public static final ParamKey FROM_RM_NO = new ParamKey("FROM_RM_NO");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** LAST_UPDATE_DATE */
    public static final ParamKey LAST_UPDATE_DATE = new ParamKey("LAST_UPDATE_DATE");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** MIXED_LOAD */
    public static final ParamKey MIXED_LOAD = new ParamKey("MIXED_LOAD");

    /** PALLET_ID */
    public static final ParamKey PALLET_ID = new ParamKey("PALLET_ID");

    /** PICKING_QTY */
    public static final ParamKey PICKING_QTY = new ParamKey("PICKING_QTY");

    /** SEARCH_CONDITION */
    public static final ParamKey SEARCH_CONDITION = new ParamKey("SEARCH_CONDITION");

    /** STATION_NO */
    public static final ParamKey STATION_NO = new ParamKey("STATION_NO");

    /** STOCK_ID */
    public static final ParamKey STOCK_ID = new ParamKey("STOCK_ID");

    /** STORAGE_DATE */
    public static final ParamKey STORAGE_DATE = new ParamKey("STORAGE_DATE");

    /** TO_RM_NO */
    public static final ParamKey TO_RM_NO = new ParamKey("TO_RM_NO");
    
    //DFKLOOK:ここから修正
    /** STOCK_QTY */
    public static final ParamKey STOCK_QTY = new ParamKey("STOCK_QTY");
    //DFKLOOK:ここまで修正

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
    public FaInquiryRetrievalDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public FaInquiryRetrievalDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public FaInquiryRetrievalDASCHParams(Map<Key, Object> initMap)
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

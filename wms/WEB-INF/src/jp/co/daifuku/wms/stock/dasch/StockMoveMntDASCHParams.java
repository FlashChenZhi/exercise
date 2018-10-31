// $Id: StockMoveMntDASCHParams.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.stock.dasch;

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
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class StockMoveMntDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    /** CANCEL */
    public static final ParamKey CANCEL = new ParamKey("CANCEL");

    /** CASE_PACK */
    public static final ParamKey CASE_PACK = new ParamKey("CASE_PACK");

    /** DFK_DS_NO */
    public static final ParamKey DFK_DS_NO = new ParamKey("DFK_DS_NO");

    /** DFK_USER_ID */
    public static final ParamKey DFK_USER_ID = new ParamKey("DFK_USER_ID");

    /** DFK_USER_NAME */
    public static final ParamKey DFK_USER_NAME = new ParamKey("DFK_USER_NAME");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** FROM_AREA */
    public static final ParamKey FROM_AREA = new ParamKey("FROM_AREA");

    /** FROM_LOCATION */
    public static final ParamKey FROM_LOCATION = new ParamKey("FROM_LOCATION");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** JOB_NO */
    public static final ParamKey JOB_NO = new ParamKey("JOB_NO");

    /** LISTNO */
    public static final ParamKey LISTNO = new ParamKey("LISTNO");

    /** LOT */
    public static final ParamKey LOT = new ParamKey("LOT");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** MOVEMENT_CASE_QTY */
    public static final ParamKey MOVEMENT_CASE_QTY = new ParamKey("MOVEMENT_CASE_QTY");

    /** MOVEMENT_PIECE_QTY */
    public static final ParamKey MOVEMENT_PIECE_QTY = new ParamKey("MOVEMENT_PIECE_QTY");

    /** PLAN_CASE_QTY */
    public static final ParamKey PLAN_CASE_QTY = new ParamKey("PLAN_CASE_QTY");

    /** PLAN_PIECE_QTY */
    public static final ParamKey PLAN_PIECE_QTY = new ParamKey("PLAN_PIECE_QTY");

    /** RELOCATION_CASE_QTY */
    public static final ParamKey RELOCATION_CASE_QTY = new ParamKey("RELOCATION_CASE_QTY");

    /** RELOCATION_PIECE_QTY */
    public static final ParamKey RELOCATION_PIECE_QTY = new ParamKey("RELOCATION_PIECE_QTY");

    /** RETRIEVAL_AREA_NO */
    public static final ParamKey RETRIEVAL_AREA_NO = new ParamKey("RETRIEVAL_AREA_NO");

    /** RETRIEVAL_LOCATION_NO */
    public static final ParamKey RETRIEVAL_LOCATION_NO = new ParamKey("RETRIEVAL_LOCATION_NO");

    /** RETRIEVALDATE */
    public static final ParamKey RETRIEVALDATE = new ParamKey("RETRIEVALDATE");

    /** RFTNO */
    public static final ParamKey RFTNO = new ParamKey("RFTNO");

    /** STORAGE_AREA_NO */
    public static final ParamKey STORAGE_AREA_NO = new ParamKey("STORAGE_AREA_NO");

    /** STORAGE_LOCATION_NO */
    public static final ParamKey STORAGE_LOCATION_NO = new ParamKey("STORAGE_LOCATION_NO");

    /** SYS_DAY */
    public static final ParamKey SYS_DAY = new ParamKey("SYS_DAY");

    /** SYS_TIME */
    public static final ParamKey SYS_TIME = new ParamKey("SYS_TIME");

    /** TO_AREA */
    public static final ParamKey TO_AREA = new ParamKey("TO_AREA");

    /** TO_LOCATION */
    public static final ParamKey TO_LOCATION = new ParamKey("TO_LOCATION");

    /** USER_NAME */
    public static final ParamKey USER_NAME = new ParamKey("USER_NAME");

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
    public StockMoveMntDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public StockMoveMntDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public StockMoveMntDASCHParams(Map<Key, Object> initMap)
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

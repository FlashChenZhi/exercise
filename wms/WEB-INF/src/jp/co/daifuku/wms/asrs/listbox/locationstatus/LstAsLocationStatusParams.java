// $Id: LstAsLocationStatusParams.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.asrs.listbox.locationstatus;

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
public class LstAsLocationStatusParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** CASE_ITF */
    public static final ParamKey CASE_ITF = new ParamKey("CASE_ITF");

    /** CASE_PACK */
    public static final ParamKey CASE_PACK = new ParamKey("CASE_PACK");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** LOCATION */
    public static final ParamKey LOCATION = new ParamKey("LOCATION");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** LOT */
    public static final ParamKey LOT = new ParamKey("LOT");

    /** PROHIBITION_EMPTY */
    public static final ParamKey PROHIBITION_EMPTY = new ParamKey("PROHIBITION_EMPTY");

    /** PROHIBITION_EMPTY_PALLET */
    public static final ParamKey PROHIBITION_EMPTY_PALLET = new ParamKey("PROHIBITION_EMPTY_PALLET");

    /** PROHIBITION_ERROR */
    public static final ParamKey PROHIBITION_ERROR = new ParamKey("PROHIBITION_ERROR");

    /** PROHIBITION_STORAGED */
    public static final ParamKey PROHIBITION_STORAGED = new ParamKey("PROHIBITION_STORAGED");

    /** STATUS */
    public static final ParamKey STATUS = new ParamKey("STATUS");

    /** STOCK_CASE_QTY */
    public static final ParamKey STOCK_CASE_QTY = new ParamKey("STOCK_CASE_QTY");

    /** STOCK_PIECE_QTY */
    public static final ParamKey STOCK_PIECE_QTY = new ParamKey("STOCK_PIECE_QTY");

    /** STORAGE_DATE */
    public static final ParamKey STORAGE_DATE = new ParamKey("STORAGE_DATE");

    /** STORAGE_TIME */
    public static final ParamKey STORAGE_TIME = new ParamKey("STORAGE_TIME");

    /** UPC_CODE */
    public static final ParamKey UPC_CODE = new ParamKey("UPC_CODE");

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
    public LstAsLocationStatusParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstAsLocationStatusParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstAsLocationStatusParams(Map<Key, Object> initMap)
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

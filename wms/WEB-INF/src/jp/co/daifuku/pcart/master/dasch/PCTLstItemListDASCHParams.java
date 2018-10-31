// $Id: PCTLstItemListDASCHParams.java 3378 2009-03-12 08:12:31Z tanaka $
package jp.co.daifuku.pcart.master.dasch;

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
 * @version $Revision: 3378 $, $Date:: 2009-03-12 17:12:31 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: tanaka $
 */
public class PCTLstItemListDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CONSIGNOR_NAME */
    public static final ParamKey CONSIGNOR_NAME = new ParamKey("CONSIGNOR_NAME");

    /** FROM_ITEM_CODE */
    public static final ParamKey FROM_ITEM_CODE = new ParamKey("FROM_ITEM_CODE");

    /** FROM_LOCATION_NO */
    public static final ParamKey FROM_LOCATION_NO = new ParamKey("FROM_LOCATION_NO");

    /** FROM_SINGLE_WEIGHT */
    public static final ParamKey FROM_SINGLE_WEIGHT = new ParamKey("FROM_SINGLE_WEIGHT");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** ITEM_PICTURE_REGIST */
    public static final ParamKey ITEM_PICTURE_REGIST = new ParamKey("ITEM_PICTURE_REGIST");

    /** ITF */
    public static final ParamKey ITF = new ParamKey("ITF");

    /** JAN */
    public static final ParamKey JAN = new ParamKey("JAN");

    /** LAST_UPDATE_DATE */
    public static final ParamKey LAST_UPDATE_DATE = new ParamKey("LAST_UPDATE_DATE");

    /** LAST_USED_DATE */
    public static final ParamKey LAST_USED_DATE = new ParamKey("LAST_USED_DATE");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** LOT_QTY */
    public static final ParamKey LOT_QTY = new ParamKey("LOT_QTY");

    /** MAX_INSPECTION_QTY */
    public static final ParamKey MAX_INSPECTION_QTY = new ParamKey("MAX_INSPECTION_QTY");

    /** MESSAGE */
    public static final ParamKey MESSAGE = new ParamKey("MESSAGE");

    /** SINGLE_WEIGHT */
    public static final ParamKey SINGLE_WEIGHT = new ParamKey("SINGLE_WEIGHT");

    /** TO_ITEM_CODE */
    public static final ParamKey TO_ITEM_CODE = new ParamKey("TO_ITEM_CODE");

    /** TO_LOCATION_NO */
    public static final ParamKey TO_LOCATION_NO = new ParamKey("TO_LOCATION_NO");

    /** TO_SINGLE_WEIGHT */
    public static final ParamKey TO_SINGLE_WEIGHT = new ParamKey("TO_SINGLE_WEIGHT");

    /** WEIGHT_DISTINCT_RATE */
    public static final ParamKey WEIGHT_DISTINCT_RATE = new ParamKey("WEIGHT_DISTINCT_RATE");

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
    public PCTLstItemListDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public PCTLstItemListDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public PCTLstItemListDASCHParams(Map<Key, Object> initMap)
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

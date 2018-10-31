// $Id: PCTLstItemListParams.java 3377 2009-03-12 08:12:00Z tanaka $
package jp.co.daifuku.pcart.master.listbox.pctitemlist;

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
 * @version $Revision: 3377 $, $Date:: 2009-03-12 17:12:00 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: tanaka $
 */
public class PCTLstItemListParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** FROM_ITEM_CODE */
    public static final ParamKey FROM_ITEM_CODE = new ParamKey("FROM_ITEM_CODE");

    /** FROM_LOCATION_NO */
    public static final ParamKey FROM_LOCATION_NO = new ParamKey("FROM_LOCATION_NO");

    /** FROM_SINGLE_WEIGHT */
    public static final ParamKey FROM_SINGLE_WEIGHT = new ParamKey("FROM_SINGLE_WEIGHT");

    /** ITEM_PICTURE_REGIST */
    public static final ParamKey ITEM_PICTURE_REGIST = new ParamKey("ITEM_PICTURE_REGIST");

    /** ITF */
    public static final ParamKey ITF = new ParamKey("ITF");

    /** JAN */
    public static final ParamKey JAN = new ParamKey("JAN");

    /** LOT_QTY */
    public static final ParamKey LOT_QTY = new ParamKey("LOT_QTY");

    /** TO_ITEM_CODE */
    public static final ParamKey TO_ITEM_CODE = new ParamKey("TO_ITEM_CODE");

    /** TO_LOCATION_NO */
    public static final ParamKey TO_LOCATION_NO = new ParamKey("TO_LOCATION_NO");

    /** TO_SINGLE_WEIGHT */
    public static final ParamKey TO_SINGLE_WEIGHT = new ParamKey("TO_SINGLE_WEIGHT");

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
    public PCTLstItemListParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public PCTLstItemListParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public PCTLstItemListParams(Map<Key, Object> initMap)
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

// $Id: LstItemDASCHParams.java 5874 2009-11-14 12:36:19Z shibamoto $
package jp.co.daifuku.wms.base.dasch;

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
 * @version $Revision: 5874 $, $Date:: 2009-11-14 21:36:19 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstItemDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** FROM_TO_FLAG */
    public static final ParamKey FROM_TO_FLAG = new ParamKey("FROM_TO_FLAG");

    /** IS_ITEM_CODE_SEARCH */
    public static final ParamKey IS_ITEM_CODE_SEARCH = new ParamKey("IS_ITEM_CODE_SEARCH");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** ITF */
    public static final ParamKey ITF = new ParamKey("ITF");

    /** JAN */
    public static final ParamKey JAN = new ParamKey("JAN");

    /** LOWER_QTY */
    public static final ParamKey LOWER_QTY = new ParamKey("LOWER_QTY");

    /** SELECT */
    public static final ParamKey SELECT = new ParamKey("SELECT");

    /** SOFT_ZONE_ID */
    public static final ParamKey SOFT_ZONE_ID = new ParamKey("SOFT_ZONE_ID");

    /** SOFT_ZONE_NAME */
    public static final ParamKey SOFT_ZONE_NAME = new ParamKey("SOFT_ZONE_NAME");

    /** TEMPORARY_TYPE */
    public static final ParamKey TEMPORARY_TYPE = new ParamKey("TEMPORARY_TYPE");

    /** TEMPORARY_TYPE_NAME */
    public static final ParamKey TEMPORARY_TYPE_NAME = new ParamKey("TEMPORARY_TYPE_NAME");

    /** TO_ITEM_CODE */
    public static final ParamKey TO_ITEM_CODE = new ParamKey("TO_ITEM_CODE");

    /** UPPER_QTY */
    public static final ParamKey UPPER_QTY = new ParamKey("UPPER_QTY");

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
    public LstItemDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstItemDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstItemDASCHParams(Map<Key, Object> initMap)
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

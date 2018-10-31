// $Id: PCTWeightDistinctionListDASCHParams.java 3446 2009-03-13 07:50:43Z dmori $
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
 * @version $Revision: 3446 $, $Date:: 2009-03-13 16:50:43 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: dmori $
 */
public class PCTWeightDistinctionListDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CONSIGNOR_NAME */
    public static final ParamKey CONSIGNOR_NAME = new ParamKey("CONSIGNOR_NAME");

    /** CORRECT_QTY */
    public static final ParamKey CORRECT_QTY = new ParamKey("CORRECT_QTY");

    /** CORRECT_WEIGHT */
    public static final ParamKey CORRECT_WEIGHT = new ParamKey("CORRECT_WEIGHT");

    /** DIFFERENCE */
    public static final ParamKey DIFFERENCE = new ParamKey("DIFFERENCE");

    /** FROM_OCCUR_DAY */
    public static final ParamKey FROM_OCCUR_DAY = new ParamKey("FROM_OCCUR_DAY");

    /** FROM_OCCUR_TIME */
    public static final ParamKey FROM_OCCUR_TIME = new ParamKey("FROM_OCCUR_TIME");

    /** INSPECT_QTY */
    public static final ParamKey INSPECT_QTY = new ParamKey("INSPECT_QTY");

    /** INSPECT_WEIGHT */
    public static final ParamKey INSPECT_WEIGHT = new ParamKey("INSPECT_WEIGHT");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** LOT_ENTERING_QTY */
    public static final ParamKey LOT_ENTERING_QTY = new ParamKey("LOT_ENTERING_QTY");

    /** OCCUR_DAY */
    public static final ParamKey OCCUR_DAY = new ParamKey("OCCUR_DAY");

    /** OCCUR_TIME */
    public static final ParamKey OCCUR_TIME = new ParamKey("OCCUR_TIME");

    /** SEARCH_DATE */
    public static final ParamKey SEARCH_DATE = new ParamKey("SEARCH_DATE");

    /** SEARCH_TIME */
    public static final ParamKey SEARCH_TIME = new ParamKey("SEARCH_TIME");

    /** SINGLE_WEIGHT */
    public static final ParamKey SINGLE_WEIGHT = new ParamKey("SINGLE_WEIGHT");

    /** SYS_DAY */
    public static final ParamKey SYS_DAY = new ParamKey("SYS_DAY");

    /** SYS_TIME */
    public static final ParamKey SYS_TIME = new ParamKey("SYS_TIME");

    /** TERMINAL_NO */
    public static final ParamKey TERMINAL_NO = new ParamKey("TERMINAL_NO");

    /** TO_OCCUR_DAY */
    public static final ParamKey TO_OCCUR_DAY = new ParamKey("TO_OCCUR_DAY");

    /** TO_OCCUR_TIME */
    public static final ParamKey TO_OCCUR_TIME = new ParamKey("TO_OCCUR_TIME");

    /** TO_SEARCH_DATE */
    public static final ParamKey TO_SEARCH_DATE = new ParamKey("TO_SEARCH_DATE");

    /** TO_SEARCH_TIME */
    public static final ParamKey TO_SEARCH_TIME = new ParamKey("TO_SEARCH_TIME");

    /** USER_ID */
    public static final ParamKey USER_ID = new ParamKey("USER_ID");

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
    public PCTWeightDistinctionListDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public PCTWeightDistinctionListDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public PCTWeightDistinctionListDASCHParams(Map<Key, Object> initMap)
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

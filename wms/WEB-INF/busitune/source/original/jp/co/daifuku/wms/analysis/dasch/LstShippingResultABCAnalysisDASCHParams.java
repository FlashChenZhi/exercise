// $Id: LstShippingResultABCAnalysisDASCHParams.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.wms.analysis.dasch;

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
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class LstShippingResultABCAnalysisDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ANALYSIS_TYPE */
    public static final ParamKey ANALYSIS_TYPE = new ParamKey("ANALYSIS_TYPE");

    /** CLASS */
    public static final ParamKey CLASS = new ParamKey("CLASS");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CUMULATIVE_PERCENT */
    public static final ParamKey CUMULATIVE_PERCENT = new ParamKey("CUMULATIVE_PERCENT");

    /** CUMULATIVE_PRECENT */
    public static final ParamKey CUMULATIVE_PRECENT = new ParamKey("CUMULATIVE_PRECENT");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** FROM_DATE */
    public static final ParamKey FROM_DATE = new ParamKey("FROM_DATE");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** NO */
    public static final ParamKey NO = new ParamKey("NO");

    /** PERCENT */
    public static final ParamKey PERCENT = new ParamKey("PERCENT");

    /** SHIP_COUNT */
    public static final ParamKey SHIP_COUNT = new ParamKey("SHIP_COUNT");

    /** SHIP_QTY */
    public static final ParamKey SHIP_QTY = new ParamKey("SHIP_QTY");

    /** THRESHOLD_A */
    public static final ParamKey THRESHOLD_A = new ParamKey("THRESHOLD_A");

    /** THRESHOLD_B */
    public static final ParamKey THRESHOLD_B = new ParamKey("THRESHOLD_B");

    /** TO_DATE */
    public static final ParamKey TO_DATE = new ParamKey("TO_DATE");

    /** WORK_QTY */
    public static final ParamKey WORK_QTY = new ParamKey("WORK_QTY");

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
    public LstShippingResultABCAnalysisDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstShippingResultABCAnalysisDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstShippingResultABCAnalysisDASCHParams(Map<Key, Object> initMap)
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

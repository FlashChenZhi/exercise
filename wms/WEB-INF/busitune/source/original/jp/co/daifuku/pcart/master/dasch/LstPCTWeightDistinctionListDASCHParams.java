// $Id: LstPCTWeightDistinctionListDASCHParams.java 5290 2009-10-28 04:29:37Z kishimoto $
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
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class LstPCTWeightDistinctionListDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AMOUNT */
    public static final ParamKey AMOUNT = new ParamKey("AMOUNT");

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

    /** FRONTAGE */
    public static final ParamKey FRONTAGE = new ParamKey("FRONTAGE");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** OCCUR_DAY */
    public static final ParamKey OCCUR_DAY = new ParamKey("OCCUR_DAY");

    /** OCCUR_TIME */
    public static final ParamKey OCCUR_TIME = new ParamKey("OCCUR_TIME");

    /** QTY */
    public static final ParamKey QTY = new ParamKey("QTY");

    /** RFT_NO */
    public static final ParamKey RFT_NO = new ParamKey("RFT_NO");

    /** SINGLE_WEIGHT */
    public static final ParamKey SINGLE_WEIGHT = new ParamKey("SINGLE_WEIGHT");

    /** TO_OCCUR_DAY */
    public static final ParamKey TO_OCCUR_DAY = new ParamKey("TO_OCCUR_DAY");

    /** TO_OCCUR_TIME */
    public static final ParamKey TO_OCCUR_TIME = new ParamKey("TO_OCCUR_TIME");

    /** USER_ID */
    public static final ParamKey USER_ID = new ParamKey("USER_ID");

    /** USER_NAME */
    public static final ParamKey USER_NAME = new ParamKey("USER_NAME");

    /** WEIGHT */
    public static final ParamKey WEIGHT = new ParamKey("WEIGHT");

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
    public LstPCTWeightDistinctionListDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstPCTWeightDistinctionListDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstPCTWeightDistinctionListDASCHParams(Map<Key, Object> initMap)
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

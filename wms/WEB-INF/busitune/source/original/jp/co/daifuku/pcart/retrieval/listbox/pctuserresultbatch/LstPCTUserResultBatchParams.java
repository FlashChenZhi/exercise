// $Id: LstPCTUserResultBatchParams.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.pcart.retrieval.listbox.pctuserresultbatch;

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
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class LstPCTUserResultBatchParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** COLLECT_CONDITION */
    public static final ParamKey COLLECT_CONDITION = new ParamKey("COLLECT_CONDITION");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** DISPLAY_RANK */
    public static final ParamKey DISPLAY_RANK = new ParamKey("DISPLAY_RANK");

    /** FRIDAY */
    public static final ParamKey FRIDAY = new ParamKey("FRIDAY");

    /** LEVEL_A */
    public static final ParamKey LEVEL_A = new ParamKey("LEVEL_A");

    /** LEVEL_B */
    public static final ParamKey LEVEL_B = new ParamKey("LEVEL_B");

    /** LEVEL_C */
    public static final ParamKey LEVEL_C = new ParamKey("LEVEL_C");

    /** MONDAY */
    public static final ParamKey MONDAY = new ParamKey("MONDAY");

    /** SATURDAY */
    public static final ParamKey SATURDAY = new ParamKey("SATURDAY");

    /** SUNDAY */
    public static final ParamKey SUNDAY = new ParamKey("SUNDAY");

    /** THURSDAY */
    public static final ParamKey THURSDAY = new ParamKey("THURSDAY");

    /** TUESDAY */
    public static final ParamKey TUESDAY = new ParamKey("TUESDAY");

    /** USEDAY_OF_WEEK_FLAG */
    public static final ParamKey USEDAY_OF_WEEK_FLAG = new ParamKey("USEDAY_OF_WEEK_FLAG");

    /** WEDNESDAY */
    public static final ParamKey WEDNESDAY = new ParamKey("WEDNESDAY");

    /** WORKDAY_FROM */
    public static final ParamKey WORKDAY_FROM = new ParamKey("WORKDAY_FROM");

    /** WORKDAY_TO */
    public static final ParamKey WORKDAY_TO = new ParamKey("WORKDAY_TO");

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
    public LstPCTUserResultBatchParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstPCTUserResultBatchParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstPCTUserResultBatchParams(Map<Key, Object> initMap)
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

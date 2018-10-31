// $Id: OperationLogListDASCHParams.java 3493 2009-03-16 05:26:25Z tanaka $
package jp.co.daifuku.pcart.system.dasch;

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
 * @version $Revision: 3493 $, $Date:: 2009-03-16 14:26:25 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: tanaka $
 */
public class OperationLogListDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DS_NO */
    public static final ParamKey DS_NO = new ParamKey("DS_NO");

    /** HIDDEN_LOG_DAY */
    public static final ParamKey HIDDEN_LOG_DAY = new ParamKey("HIDDEN_LOG_DAY");

    /** HIDDEN_OPERATION_TYPE */
    public static final ParamKey HIDDEN_OPERATION_TYPE = new ParamKey("HIDDEN_OPERATION_TYPE");

    /** IP_ADDRESS */
    public static final ParamKey IP_ADDRESS = new ParamKey("IP_ADDRESS");

    /** ITEM_DATA_1 */
    public static final ParamKey ITEM_DATA_1 = new ParamKey("ITEM_DATA_1");

    /** ITEM_DATA_10 */
    public static final ParamKey ITEM_DATA_10 = new ParamKey("ITEM_DATA_10");

    /** ITEM_DATA_11 */
    public static final ParamKey ITEM_DATA_11 = new ParamKey("ITEM_DATA_11");

    /** ITEM_DATA_12 */
    public static final ParamKey ITEM_DATA_12 = new ParamKey("ITEM_DATA_12");

    /** ITEM_DATA_13 */
    public static final ParamKey ITEM_DATA_13 = new ParamKey("ITEM_DATA_13");

    /** ITEM_DATA_14 */
    public static final ParamKey ITEM_DATA_14 = new ParamKey("ITEM_DATA_14");

    /** ITEM_DATA_15 */
    public static final ParamKey ITEM_DATA_15 = new ParamKey("ITEM_DATA_15");

    /** ITEM_DATA_16 */
    public static final ParamKey ITEM_DATA_16 = new ParamKey("ITEM_DATA_16");

    /** ITEM_DATA_17 */
    public static final ParamKey ITEM_DATA_17 = new ParamKey("ITEM_DATA_17");

    /** ITEM_DATA_18 */
    public static final ParamKey ITEM_DATA_18 = new ParamKey("ITEM_DATA_18");

    /** ITEM_DATA_19 */
    public static final ParamKey ITEM_DATA_19 = new ParamKey("ITEM_DATA_19");

    /** ITEM_DATA_2 */
    public static final ParamKey ITEM_DATA_2 = new ParamKey("ITEM_DATA_2");

    /** ITEM_DATA_20 */
    public static final ParamKey ITEM_DATA_20 = new ParamKey("ITEM_DATA_20");

    /** ITEM_DATA_21 */
    public static final ParamKey ITEM_DATA_21 = new ParamKey("ITEM_DATA_21");

    /** ITEM_DATA_22 */
    public static final ParamKey ITEM_DATA_22 = new ParamKey("ITEM_DATA_22");

    /** ITEM_DATA_23 */
    public static final ParamKey ITEM_DATA_23 = new ParamKey("ITEM_DATA_23");

    /** ITEM_DATA_24 */
    public static final ParamKey ITEM_DATA_24 = new ParamKey("ITEM_DATA_24");

    /** ITEM_DATA_25 */
    public static final ParamKey ITEM_DATA_25 = new ParamKey("ITEM_DATA_25");

    /** ITEM_DATA_26 */
    public static final ParamKey ITEM_DATA_26 = new ParamKey("ITEM_DATA_26");

    /** ITEM_DATA_27 */
    public static final ParamKey ITEM_DATA_27 = new ParamKey("ITEM_DATA_27");

    /** ITEM_DATA_28 */
    public static final ParamKey ITEM_DATA_28 = new ParamKey("ITEM_DATA_28");

    /** ITEM_DATA_29 */
    public static final ParamKey ITEM_DATA_29 = new ParamKey("ITEM_DATA_29");

    /** ITEM_DATA_3 */
    public static final ParamKey ITEM_DATA_3 = new ParamKey("ITEM_DATA_3");

    /** ITEM_DATA_30 */
    public static final ParamKey ITEM_DATA_30 = new ParamKey("ITEM_DATA_30");

    /** ITEM_DATA_31 */
    public static final ParamKey ITEM_DATA_31 = new ParamKey("ITEM_DATA_31");

    /** ITEM_DATA_32 */
    public static final ParamKey ITEM_DATA_32 = new ParamKey("ITEM_DATA_32");

    /** ITEM_DATA_33 */
    public static final ParamKey ITEM_DATA_33 = new ParamKey("ITEM_DATA_33");

    /** ITEM_DATA_34 */
    public static final ParamKey ITEM_DATA_34 = new ParamKey("ITEM_DATA_34");

    /** ITEM_DATA_35 */
    public static final ParamKey ITEM_DATA_35 = new ParamKey("ITEM_DATA_35");

    /** ITEM_DATA_36 */
    public static final ParamKey ITEM_DATA_36 = new ParamKey("ITEM_DATA_36");

    /** ITEM_DATA_37 */
    public static final ParamKey ITEM_DATA_37 = new ParamKey("ITEM_DATA_37");

    /** ITEM_DATA_38 */
    public static final ParamKey ITEM_DATA_38 = new ParamKey("ITEM_DATA_38");

    /** ITEM_DATA_39 */
    public static final ParamKey ITEM_DATA_39 = new ParamKey("ITEM_DATA_39");

    /** ITEM_DATA_4 */
    public static final ParamKey ITEM_DATA_4 = new ParamKey("ITEM_DATA_4");

    /** ITEM_DATA_40 */
    public static final ParamKey ITEM_DATA_40 = new ParamKey("ITEM_DATA_40");

    /** ITEM_DATA_5 */
    public static final ParamKey ITEM_DATA_5 = new ParamKey("ITEM_DATA_5");

    /** ITEM_DATA_6 */
    public static final ParamKey ITEM_DATA_6 = new ParamKey("ITEM_DATA_6");

    /** ITEM_DATA_7 */
    public static final ParamKey ITEM_DATA_7 = new ParamKey("ITEM_DATA_7");

    /** ITEM_DATA_8 */
    public static final ParamKey ITEM_DATA_8 = new ParamKey("ITEM_DATA_8");

    /** ITEM_DATA_9 */
    public static final ParamKey ITEM_DATA_9 = new ParamKey("ITEM_DATA_9");

    /** LOG_DAY */
    public static final ParamKey LOG_DAY = new ParamKey("LOG_DAY");

    /** LOG_TIME */
    public static final ParamKey LOG_TIME = new ParamKey("LOG_TIME");

    /** OPERATION_TYPE */
    public static final ParamKey OPERATION_TYPE = new ParamKey("OPERATION_TYPE");

    /** PAGENAME_RESOURCE_KEY */
    public static final ParamKey PAGENAME_RESOURCE_KEY = new ParamKey("PAGENAME_RESOURCE_KEY");

    /** SCREEN_NAME */
    public static final ParamKey SCREEN_NAME = new ParamKey("SCREEN_NAME");

    /** TERMINAL_NAME */
    public static final ParamKey TERMINAL_NAME = new ParamKey("TERMINAL_NAME");

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
    public OperationLogListDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public OperationLogListDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public OperationLogListDASCHParams(Map<Key, Object> initMap)
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

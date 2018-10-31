// $Id: LstStationStatusDASCHParams.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.asrs.dasch;

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
 * BusinessクラスとDASCH間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class LstStationStatusDASCHParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CURRENT_MODE */
    public static final ParamKey CURRENT_MODE = new ParamKey("CURRENT_MODE");

    /** HIDDEN_CURRENT_MODE */
    public static final ParamKey HIDDEN_CURRENT_MODE = new ParamKey("HIDDEN_CURRENT_MODE");

    /** HIDDEN_MODE_REQUEST */
    public static final ParamKey HIDDEN_MODE_REQUEST = new ParamKey("HIDDEN_MODE_REQUEST");

    /** HIDDEN_MODE_TYPE */
    public static final ParamKey HIDDEN_MODE_TYPE = new ParamKey("HIDDEN_MODE_TYPE");

    /** HIDDEN_STATUS */
    public static final ParamKey HIDDEN_STATUS = new ParamKey("HIDDEN_STATUS");

    /** HIDDEN_SUSPEND */
    public static final ParamKey HIDDEN_SUSPEND = new ParamKey("HIDDEN_SUSPEND");

    /** MACHINE_STATUS */
    public static final ParamKey MACHINE_STATUS = new ParamKey("MACHINE_STATUS");

    /** MODE_REQUEST */
    public static final ParamKey MODE_REQUEST = new ParamKey("MODE_REQUEST");

    /** MODE_TYPE */
    public static final ParamKey MODE_TYPE = new ParamKey("MODE_TYPE");

    /** SELECT */
    public static final ParamKey SELECT = new ParamKey("SELECT");

    /** STATION */
    public static final ParamKey STATION = new ParamKey("STATION");

    /** STATION_NO */
    public static final ParamKey STATION_NO = new ParamKey("STATION_NO");

    /** STATUS */
    public static final ParamKey STATUS = new ParamKey("STATUS");

    /** SUSPEND */
    public static final ParamKey SUSPEND = new ParamKey("SUSPEND");

    /** WORK_COUNT */
    public static final ParamKey WORK_COUNT = new ParamKey("WORK_COUNT");

    /** WORK_MODE_CHANGE */
    public static final ParamKey WORK_MODE_CHANGE = new ParamKey("WORK_MODE_CHANGE");

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
    public LstStationStatusDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstStationStatusDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstStationStatusDASCHParams(Map<Key, Object> initMap)
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

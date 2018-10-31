// $Id: BusinessParams_ja.java 110 2008-10-06 10:50:07Z admin $
package jp.co.daifuku.wms.asrs.listbox.carry;

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
 * @version $Revision: 110 $, $Date:: 2008-10-06 19:50:07 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class LstAsCarry2Params
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** CARRY_FLAG */
    public static final ParamKey CARRY_FLAG = new ParamKey("CARRY_FLAG");

    /** CARRY_KEY */
    public static final ParamKey CARRY_KEY = new ParamKey("CARRY_KEY");

    /** CMD_STATUS */
    public static final ParamKey CMD_STATUS = new ParamKey("CMD_STATUS");

    /** DEST_STATION_NAME */
    public static final ParamKey DEST_STATION_NAME = new ParamKey("DEST_STATION_NAME");

    /** DEST_STATION_NO */
    public static final ParamKey DEST_STATION_NO = new ParamKey("DEST_STATION_NO");

    /** FROMTO */
    public static final ParamKey FROMTO = new ParamKey("FROMTO");

    /** LOCATION */
    public static final ParamKey LOCATION = new ParamKey("LOCATION");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** MC_KEY */
    public static final ParamKey MC_KEY = new ParamKey("MC_KEY");

    /** PRIORITY_FLAG */
    public static final ParamKey PRIORITY_FLAG = new ParamKey("PRIORITY_FLAG");

    /** RETRIEVAL_DETAIL */
    public static final ParamKey RETRIEVAL_DETAIL = new ParamKey("RETRIEVAL_DETAIL");

    /** SCHEDULE_NO */
    public static final ParamKey SCHEDULE_NO = new ParamKey("SCHEDULE_NO");

    /** SOURCE_STATION_NAME */
    public static final ParamKey SOURCE_STATION_NAME = new ParamKey("SOURCE_STATION_NAME");

    /** SOURCE_STATION_NO */
    public static final ParamKey SOURCE_STATION_NO = new ParamKey("SOURCE_STATION_NO");

    /** STATION */
    public static final ParamKey STATION = new ParamKey("STATION");

    /** STATION_NO_KEY */
    public static final ParamKey STATION_NO_KEY = new ParamKey("STATION_NO_KEY");

    /** TERMINAL_NO_KEY */
    public static final ParamKey TERMINAL_NO_KEY = new ParamKey("TERMINAL_NO_KEY");

    /** TRANSPORT_STATUS */
    public static final ParamKey TRANSPORT_STATUS = new ParamKey("TRANSPORT_STATUS");

    /** TRANSPORT_TYPE */
    public static final ParamKey TRANSPORT_TYPE = new ParamKey("TRANSPORT_TYPE");

    /** WORK */
    public static final ParamKey WORK = new ParamKey("WORK");

    /** WORK_NO */
    public static final ParamKey WORK_NO = new ParamKey("WORK_NO");

    /** WORK_PLACE_KEY */
    public static final ParamKey WORK_PLACE_KEY = new ParamKey("WORK_PLACE_KEY");

    /** WORK_TYPE */
    public static final ParamKey WORK_TYPE = new ParamKey("WORK_TYPE");

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
    public LstAsCarry2Params()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstAsCarry2Params(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstAsCarry2Params(Map<Key, Object> initMap)
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

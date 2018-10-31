// $Id: LstAsCarryParams.java 7423 2010-03-06 08:36:58Z shibamoto $
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
 * @version $Revision: 7423 $, $Date:: 2010-03-06 17:36:58 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstAsCarryParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** CARRY_FLAG */
    public static final ParamKey CARRY_FLAG = new ParamKey("CARRY_FLAG");

    /** CARRY_FLAG_NAME */
    public static final ParamKey CARRY_FLAG_NAME = new ParamKey("CARRY_FLAG_NAME");

    /** CARRY_KEY */
    public static final ParamKey CARRY_KEY = new ParamKey("CARRY_KEY");

    /** CMD_STATUS */
    public static final ParamKey CMD_STATUS = new ParamKey("CMD_STATUS");

    /** CMD_STATUS_NAME */
    public static final ParamKey CMD_STATUS_NAME = new ParamKey("CMD_STATUS_NAME");

    /** DEST_STATION_NAME */
    public static final ParamKey DEST_STATION_NAME = new ParamKey("DEST_STATION_NAME");

    /** DEST_STATION_NO */
    public static final ParamKey DEST_STATION_NO = new ParamKey("DEST_STATION_NO");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** RETRIEVAL_DETAIL */
    public static final ParamKey RETRIEVAL_DETAIL = new ParamKey("RETRIEVAL_DETAIL");

    /** RETRIEVAL_DETAIL_NAME */
    public static final ParamKey RETRIEVAL_DETAIL_NAME = new ParamKey("RETRIEVAL_DETAIL_NAME");

    /** SCHEDULE_NO */
    public static final ParamKey SCHEDULE_NO = new ParamKey("SCHEDULE_NO");

    /** SOURCE_STATION_NAME */
    public static final ParamKey SOURCE_STATION_NAME = new ParamKey("SOURCE_STATION_NAME");

    /** SOURCE_STATION_NO */
    public static final ParamKey SOURCE_STATION_NO = new ParamKey("SOURCE_STATION_NO");

    /** STATION_NO_KEY */
    public static final ParamKey STATION_NO_KEY = new ParamKey("STATION_NO_KEY");

    /** TERMINAL_NO_KEY */
    public static final ParamKey TERMINAL_NO_KEY = new ParamKey("TERMINAL_NO_KEY");

    /** WORK_NO */
    public static final ParamKey WORK_NO = new ParamKey("WORK_NO");

    /** WORK_PLACE_KEY */
    public static final ParamKey WORK_PLACE_KEY = new ParamKey("WORK_PLACE_KEY");

    /** WORK_TYPE */
    public static final ParamKey WORK_TYPE = new ParamKey("WORK_TYPE");

    /** WORK_TYPE_NAME */
    public static final ParamKey WORK_TYPE_NAME = new ParamKey("WORK_TYPE_NAME");

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
    public LstAsCarryParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstAsCarryParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstAsCarryParams(Map<Key, Object> initMap)
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

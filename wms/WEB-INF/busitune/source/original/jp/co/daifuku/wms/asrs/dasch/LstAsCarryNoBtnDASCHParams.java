// $Id: LstAsCarryNoBtnDASCHParams.java 6025 2009-11-18 04:14:10Z okayama $
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
import jp.co.daifuku.wms.base.common.WmsDASCHParams;

/**
 * BusinessクラスとDASCH間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 6025 $, $Date:: 2009-11-18 13:14:10 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class LstAsCarryNoBtnDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** CARRY_FLAG_NAME */
    public static final ParamKey CARRY_FLAG_NAME = new ParamKey("CARRY_FLAG_NAME");

    /** CARRY_KEY */
    public static final ParamKey CARRY_KEY = new ParamKey("CARRY_KEY");

    /** CMD_STATUS_NAME */
    public static final ParamKey CMD_STATUS_NAME = new ParamKey("CMD_STATUS_NAME");

    /** DEST_STATION_NAME */
    public static final ParamKey DEST_STATION_NAME = new ParamKey("DEST_STATION_NAME");

    /** DEST_STATION_NO */
    public static final ParamKey DEST_STATION_NO = new ParamKey("DEST_STATION_NO");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** NO */
    public static final ParamKey NO = new ParamKey("NO");

    /** PRIORITY_NAME */
    public static final ParamKey PRIORITY_NAME = new ParamKey("PRIORITY_NAME");

    /** RETRIEVAL_DETAIL_NAME */
    public static final ParamKey RETRIEVAL_DETAIL_NAME = new ParamKey("RETRIEVAL_DETAIL_NAME");

    /** SCHEDULE_NO */
    public static final ParamKey SCHEDULE_NO = new ParamKey("SCHEDULE_NO");

    /** SOURCE_DEST */
    public static final ParamKey SOURCE_DEST = new ParamKey("SOURCE_DEST");

    /** SOURCE_STATION_NAME */
    public static final ParamKey SOURCE_STATION_NAME = new ParamKey("SOURCE_STATION_NAME");

    /** SOURCE_STATION_NO */
    public static final ParamKey SOURCE_STATION_NO = new ParamKey("SOURCE_STATION_NO");

    /** STATION_NO */
    public static final ParamKey STATION_NO = new ParamKey("STATION_NO");

    /** TO_LOCATION_NO */
    public static final ParamKey TO_LOCATION_NO = new ParamKey("TO_LOCATION_NO");

    /** WORK_NO */
    public static final ParamKey WORK_NO = new ParamKey("WORK_NO");

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
     * Default constructor
     */
    public LstAsCarryNoBtnDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstAsCarryNoBtnDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstAsCarryNoBtnDASCHParams(Map<Key, Object> initMap)
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

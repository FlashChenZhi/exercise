package jp.co.daifuku.wms.replenish.exporter;

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
 * BusinessクラスとExporter間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version
 * @author BusiTune 1.0 Generator.
 */
public class AsrsReplenishmentListParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DFK_DS_NO */
    public static final ParamKey DFK_DS_NO = new ParamKey("DFK_DS_NO");

    /** DFK_USER_ID */
    public static final ParamKey DFK_USER_ID = new ParamKey("DFK_USER_ID");

    /** DFK_USER_NAME */
    public static final ParamKey DFK_USER_NAME = new ParamKey("DFK_USER_NAME");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** JOB_NO */
    public static final ParamKey JOB_NO = new ParamKey("JOB_NO");

    /** JOB_TYPE */
    public static final ParamKey JOB_TYPE = new ParamKey("JOB_TYPE");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** PLAN_CASE_QTY */
    public static final ParamKey PLAN_CASE_QTY = new ParamKey("PLAN_CASE_QTY");

    /** PLAN_PIECE_QTY */
    public static final ParamKey PLAN_PIECE_QTY = new ParamKey("PLAN_PIECE_QTY");

    /** REP_LOCATION_NO */
    public static final ParamKey REP_LOCATION_NO = new ParamKey("REP_LOCATION_NO");

    /** REP_RETRIEVAL_AREA_NAME */
    public static final ParamKey REP_RETRIEVAL_AREA_NAME = new ParamKey("REP_RETRIEVAL_AREA_NAME");

    /** REP_RETRIEVAL_AREA_NO */
    public static final ParamKey REP_RETRIEVAL_AREA_NO = new ParamKey("REP_RETRIEVAL_AREA_NO");

    /** REP_RETRIEVAL_LOCATION_NO */
    public static final ParamKey REP_RETRIEVAL_LOCATION_NO = new ParamKey("REP_RETRIEVAL_LOCATION_NO");

    /** REP_STORAGE_AREA_NO */
    public static final ParamKey REP_STORAGE_AREA_NO = new ParamKey("REP_STORAGE_AREA_NO");

    /** SERCH_JOB_NO */
    public static final ParamKey SERCH_JOB_NO = new ParamKey("SERCH_JOB_NO");

    /** STATION_NO */
    public static final ParamKey STATION_NO = new ParamKey("STATION_NO");

    /** SYS_DAY */
    public static final ParamKey SYS_DAY = new ParamKey("SYS_DAY");

    /** SYS_TIME */
    public static final ParamKey SYS_TIME = new ParamKey("SYS_TIME");

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
    public AsrsReplenishmentListParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public AsrsReplenishmentListParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public AsrsReplenishmentListParams(Map<Key, Object> initMap)
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

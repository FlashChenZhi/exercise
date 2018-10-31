// $Id: AsrsWorkMaintenanceListParams.java 4670 2009-07-14 10:58:39Z shibamoto $
package jp.co.daifuku.wms.asrs.exporter;

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
 * @version $Revision: 4670 $, $Date: 2009-07-14 19:58:39 +0900 (火, 14 7 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AsrsWorkMaintenanceListParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ALLOCATION_FLAG */
    public static final ParamKey ALLOCATION_FLAG = new ParamKey("ALLOCATION_FLAG");

    /** CARRY_KEY */
    public static final ParamKey CARRY_KEY = new ParamKey("CARRY_KEY");

    /** CMD_STATUS */
    public static final ParamKey CMD_STATUS = new ParamKey("CMD_STATUS");

    /** DEST_STATION_NO */
    public static final ParamKey DEST_STATION_NO = new ParamKey("DEST_STATION_NO");

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

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** MAINTENANCE_TYPE */
    public static final ParamKey MAINTENANCE_TYPE = new ParamKey("MAINTENANCE_TYPE");

    /** RETRIEVAL_DETAIL */
    public static final ParamKey RETRIEVAL_DETAIL = new ParamKey("RETRIEVAL_DETAIL");

    /** SCHEDULE_NO */
    public static final ParamKey SCHEDULE_NO = new ParamKey("SCHEDULE_NO");

    /** SOURCE_STATION_NO */
    public static final ParamKey SOURCE_STATION_NO = new ParamKey("SOURCE_STATION_NO");

    /** STOCK_CASE_QTY */
    public static final ParamKey STOCK_CASE_QTY = new ParamKey("STOCK_CASE_QTY");

    /** STOCK_PIECE_QTY */
    public static final ParamKey STOCK_PIECE_QTY = new ParamKey("STOCK_PIECE_QTY");

    /** STORAGE_DAY */
    public static final ParamKey STORAGE_DAY = new ParamKey("STORAGE_DAY");

    /** STORAGE_TIME */
    public static final ParamKey STORAGE_TIME = new ParamKey("STORAGE_TIME");

    /** SYS_DAY */
    public static final ParamKey SYS_DAY = new ParamKey("SYS_DAY");

    /** SYS_TIME */
    public static final ParamKey SYS_TIME = new ParamKey("SYS_TIME");

    /** WORK_CASE_QTY */
    public static final ParamKey WORK_CASE_QTY = new ParamKey("WORK_CASE_QTY");

    /** WORK_PIECE_QTY */
    public static final ParamKey WORK_PIECE_QTY = new ParamKey("WORK_PIECE_QTY");

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
     * Default constructor
     */
    public AsrsWorkMaintenanceListParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public AsrsWorkMaintenanceListParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public AsrsWorkMaintenanceListParams(Map<Key, Object> initMap)
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

package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.foundation.common.ParamKey;
import jp.co.daifuku.foundation.common.ScheduleParams;

/**
 * BusinessクラスとSCH間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version
 * @author BusiTune 1.0 Generator.
 */
public class FaInquiryRetrievalSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ALL_QTY */
    public static final ParamKey ALL_QTY = new ParamKey("ALL_QTY");

    /** ALLOCATE_QTY */
    public static final ParamKey ALLOCATE_QTY = new ParamKey("ALLOCATE_QTY");

    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** AREA_TYPE */
    public static final ParamKey AREA_TYPE = new ParamKey("AREA_TYPE");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** FROM_RM_NO */
    public static final ParamKey FROM_RM_NO = new ParamKey("FROM_RM_NO");

    /** FUNCTION_ID */
    public static final ParamKey FUNCTION_ID = new ParamKey("FUNCTION_ID");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** LAST_UPDATE_DATE */
    public static final ParamKey LAST_UPDATE_DATE = new ParamKey("LAST_UPDATE_DATE");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** MIXED_LOAD */
    public static final ParamKey MIXED_LOAD = new ParamKey("MIXED_LOAD");

    /** PALLET_ID */
    public static final ParamKey PALLET_ID = new ParamKey("PALLET_ID");

    /** PICKING_QTY */
    public static final ParamKey PICKING_QTY = new ParamKey("PICKING_QTY");

    /** PUL_AREA_NO */
    public static final ParamKey PUL_AREA_NO = new ParamKey("PUL_AREA_NO");

    /** SELECT */
    public static final ParamKey SELECT = new ParamKey("SELECT");

    /** STATION_NO */
    public static final ParamKey STATION_NO = new ParamKey("STATION_NO");

    /** STATUS_FLAG */
    public static final ParamKey STATUS_FLAG = new ParamKey("STATUS_FLAG");

    /** STOCK_ID */
    public static final ParamKey STOCK_ID = new ParamKey("STOCK_ID");

    /** STOCK_QTY */
    public static final ParamKey STOCK_QTY = new ParamKey("STOCK_QTY");

    /** TO_RM_NO */
    public static final ParamKey TO_RM_NO = new ParamKey("TO_RM_NO");

    /** WORK_LIST_PRINT_FLAG */
    public static final ParamKey WORK_LIST_PRINT_FLAG = new ParamKey("WORK_LIST_PRINT_FLAG");

    /** WORK_PLACE */
    public static final ParamKey WORK_PLACE = new ParamKey("WORK_PLACE");
    
    //DFKLOOK:ここから修正
    /** COLUMN_1 */
    public static final ParamKey COLUMN_1 = new ParamKey("COLUMN_1");

    /** ALLOCATE_ALL*/
    public static final ParamKey ALL = new ParamKey("ALL");

    /** SETTING_UKEY */
    public static final ParamKey SETTING_UKEY = new ParamKey("SETTING_UKEY");
    //DFKLOOK:ここまで修正

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
    public FaInquiryRetrievalSCHParams()
    {
        super();
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
     * @return
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class

// $Id: LstPCTUserResultWorkerDASCHParams.java 4075 2009-04-10 09:21:05Z okayama $
package jp.co.daifuku.pcart.retrieval.dasch;

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
 * @version $Revision: 4075 $, $Date:: 2009-04-10 18:21:05 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class LstPCTUserResultWorkerDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ALL_OPERATE_TIME */
    public static final ParamKey ALL_OPERATE_TIME = new ParamKey("ALL_OPERATE_TIME");

    /** AREA_NAME */
    public static final ParamKey AREA_NAME = new ParamKey("AREA_NAME");

    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** AVG_ALL_OPERATE_TIME */
    public static final ParamKey AVG_ALL_OPERATE_TIME = new ParamKey("AVG_ALL_OPERATE_TIME");

    /** AVG_BOX_COUNT */
    public static final ParamKey AVG_BOX_COUNT = new ParamKey("AVG_BOX_COUNT");

    /** AVG_BOX_COUNT_PER_HOUR */
    public static final ParamKey AVG_BOX_COUNT_PER_HOUR = new ParamKey("AVG_BOX_COUNT_PER_HOUR");

    /** AVG_BOX_COUNT_PER_ORDER */
    public static final ParamKey AVG_BOX_COUNT_PER_ORDER = new ParamKey("AVG_BOX_COUNT_PER_ORDER");

    /** AVG_COLLECT_TIME */
    public static final ParamKey AVG_COLLECT_TIME = new ParamKey("AVG_COLLECT_TIME");

    /** AVG_LINE_COUNT */
    public static final ParamKey AVG_LINE_COUNT = new ParamKey("AVG_LINE_COUNT");

    /** AVG_LINE_COUNT_PER_HOUR */
    public static final ParamKey AVG_LINE_COUNT_PER_HOUR = new ParamKey("AVG_LINE_COUNT_PER_HOUR");

    /** AVG_LINE_COUNT_PER_ORDER */
    public static final ParamKey AVG_LINE_COUNT_PER_ORDER = new ParamKey("AVG_LINE_COUNT_PER_ORDER");

    /** AVG_LOT_QTY */
    public static final ParamKey AVG_LOT_QTY = new ParamKey("AVG_LOT_QTY");

    /** AVG_LOT_QTY_PER_HOUR */
    public static final ParamKey AVG_LOT_QTY_PER_HOUR = new ParamKey("AVG_LOT_QTY_PER_HOUR");

    /** AVG_LOT_QTY_PER_LINE_COUNT */
    public static final ParamKey AVG_LOT_QTY_PER_LINE_COUNT = new ParamKey("AVG_LOT_QTY_PER_LINE_COUNT");

    /** AVG_MISS_RATE */
    public static final ParamKey AVG_MISS_RATE = new ParamKey("AVG_MISS_RATE");

    /** AVG_OPERATE_TIME */
    public static final ParamKey AVG_OPERATE_TIME = new ParamKey("AVG_OPERATE_TIME");

    /** AVG_ORDER_COUNT */
    public static final ParamKey AVG_ORDER_COUNT = new ParamKey("AVG_ORDER_COUNT");

    /** AVG_ORDER_COUNT_PER_HOUR */
    public static final ParamKey AVG_ORDER_COUNT_PER_HOUR = new ParamKey("AVG_ORDER_COUNT_PER_HOUR");

    /** AVG_PIECE_QTY */
    public static final ParamKey AVG_PIECE_QTY = new ParamKey("AVG_PIECE_QTY");

    /** AVG_PIECE_QTY_PER_HOUR */
    public static final ParamKey AVG_PIECE_QTY_PER_HOUR = new ParamKey("AVG_PIECE_QTY_PER_HOUR");

    /** AVG_PIECE_QTY_PER_LINE_COUNT */
    public static final ParamKey AVG_PIECE_QTY_PER_LINE_COUNT = new ParamKey("AVG_PIECE_QTY_PER_LINE_COUNT");

    /** AVG_PRODUCTION_RATE */
    public static final ParamKey AVG_PRODUCTION_RATE = new ParamKey("AVG_PRODUCTION_RATE");

    /** AVG_RANK */
    public static final ParamKey AVG_RANK = new ParamKey("AVG_RANK");

    /** AVG_REAL_OPERATE_TIME */
    public static final ParamKey AVG_REAL_OPERATE_TIME = new ParamKey("AVG_REAL_OPERATE_TIME");

    /** AVG_STOP_TIME */
    public static final ParamKey AVG_STOP_TIME = new ParamKey("AVG_STOP_TIME");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** BOX_COUNT */
    public static final ParamKey BOX_COUNT = new ParamKey("BOX_COUNT");

    /** BOX_COUNT_PER_HOUR */
    public static final ParamKey BOX_COUNT_PER_HOUR = new ParamKey("BOX_COUNT_PER_HOUR");

    /** BOX_COUNT_PER_ORDER */
    public static final ParamKey BOX_COUNT_PER_ORDER = new ParamKey("BOX_COUNT_PER_ORDER");

    /** COLLECT_CONDITION */
    public static final ParamKey COLLECT_CONDITION = new ParamKey("COLLECT_CONDITION");

    /** COLLECT_TIME */
    public static final ParamKey COLLECT_TIME = new ParamKey("COLLECT_TIME");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CONSIGNOR_NAME */
    public static final ParamKey CONSIGNOR_NAME = new ParamKey("CONSIGNOR_NAME");

    /** DISPLAY_RANK */
    public static final ParamKey DISPLAY_RANK = new ParamKey("DISPLAY_RANK");

    /** END_TIME */
    public static final ParamKey END_TIME = new ParamKey("END_TIME");

    /** FRIDAY */
    public static final ParamKey FRIDAY = new ParamKey("FRIDAY");

    /** LEVEL */
    public static final ParamKey LEVEL = new ParamKey("LEVEL");

    /** LEVEL_A */
    public static final ParamKey LEVEL_A = new ParamKey("LEVEL_A");

    /** LEVEL_B */
    public static final ParamKey LEVEL_B = new ParamKey("LEVEL_B");

    /** LEVEL_C */
    public static final ParamKey LEVEL_C = new ParamKey("LEVEL_C");

    /** LINE_COUNT */
    public static final ParamKey LINE_COUNT = new ParamKey("LINE_COUNT");

    /** LINE_COUNT_PER_HOUR */
    public static final ParamKey LINE_COUNT_PER_HOUR = new ParamKey("LINE_COUNT_PER_HOUR");

    /** LINE_COUNT_PER_ORDER */
    public static final ParamKey LINE_COUNT_PER_ORDER = new ParamKey("LINE_COUNT_PER_ORDER");

    /** LOT_QTY */
    public static final ParamKey LOT_QTY = new ParamKey("LOT_QTY");

    /** LOT_QTY_PER_HOUR */
    public static final ParamKey LOT_QTY_PER_HOUR = new ParamKey("LOT_QTY_PER_HOUR");

    /** LOT_QTY_PER_LINE_COUNT */
    public static final ParamKey LOT_QTY_PER_LINE_COUNT = new ParamKey("LOT_QTY_PER_LINE_COUNT");

    /** MISS_RATE */
    public static final ParamKey MISS_RATE = new ParamKey("MISS_RATE");

    /** MONDAY */
    public static final ParamKey MONDAY = new ParamKey("MONDAY");

    /** OPERATE_TIME */
    public static final ParamKey OPERATE_TIME = new ParamKey("OPERATE_TIME");

    /** ORDER_COUNT */
    public static final ParamKey ORDER_COUNT = new ParamKey("ORDER_COUNT");

    /** ORDER_COUNT_PER_HOUR */
    public static final ParamKey ORDER_COUNT_PER_HOUR = new ParamKey("ORDER_COUNT_PER_HOUR");

    /** PIECE_QTY */
    public static final ParamKey PIECE_QTY = new ParamKey("PIECE_QTY");

    /** PIECE_QTY_PER_HOUR */
    public static final ParamKey PIECE_QTY_PER_HOUR = new ParamKey("PIECE_QTY_PER_HOUR");

    /** PIECE_QTY_PER_LINE_COUNT */
    public static final ParamKey PIECE_QTY_PER_LINE_COUNT = new ParamKey("PIECE_QTY_PER_LINE_COUNT");

    /** PRODUCTION_RATE */
    public static final ParamKey PRODUCTION_RATE = new ParamKey("PRODUCTION_RATE");

    /** RANK */
    public static final ParamKey RANK = new ParamKey("RANK");

    /** REAL_OPERATE_TIME */
    public static final ParamKey REAL_OPERATE_TIME = new ParamKey("REAL_OPERATE_TIME");

    /** SATURDAY */
    public static final ParamKey SATURDAY = new ParamKey("SATURDAY");

    /** START_TIME */
    public static final ParamKey START_TIME = new ParamKey("START_TIME");

    /** STOP_TIME */
    public static final ParamKey STOP_TIME = new ParamKey("STOP_TIME");

    /** SUNDAY */
    public static final ParamKey SUNDAY = new ParamKey("SUNDAY");

    /** SYS_DAY */
    public static final ParamKey SYS_DAY = new ParamKey("SYS_DAY");

    /** SYS_TIME */
    public static final ParamKey SYS_TIME = new ParamKey("SYS_TIME");

    /** THURSDAY */
    public static final ParamKey THURSDAY = new ParamKey("THURSDAY");

    /** TUESDAY */
    public static final ParamKey TUESDAY = new ParamKey("TUESDAY");

    /** USEDAY_OF_WEEK_FLAG */
    public static final ParamKey USEDAY_OF_WEEK_FLAG = new ParamKey("USEDAY_OF_WEEK_FLAG");

    /** USER_ID */
    public static final ParamKey USER_ID = new ParamKey("USER_ID");

    /** USER_NAME */
    public static final ParamKey USER_NAME = new ParamKey("USER_NAME");

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
     * Default constructor
     */
    public LstPCTUserResultWorkerDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstPCTUserResultWorkerDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstPCTUserResultWorkerDASCHParams(Map<Key, Object> initMap)
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

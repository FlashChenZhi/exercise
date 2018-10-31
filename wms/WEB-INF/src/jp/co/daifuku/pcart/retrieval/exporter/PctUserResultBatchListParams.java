// $Id$
package jp.co.daifuku.pcart.retrieval.exporter;

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
 * @version $Revision$, $Date::                           $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author$
 */
public class PctUserResultBatchListParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NAME */
    public static final ParamKey AREA_NAME = new ParamKey("AREA_NAME");

    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** BATCH_NO1 */
    public static final ParamKey BATCH_NO1 = new ParamKey("BATCH_NO1");

    /** BOX_CNT */
    public static final ParamKey BOX_CNT = new ParamKey("BOX_CNT");

    /** BOX_CNT_AVG */
    public static final ParamKey BOX_CNT_AVG = new ParamKey("BOX_CNT_AVG");

    /** BOX_CNT_PER_HOUR */
    public static final ParamKey BOX_CNT_PER_HOUR = new ParamKey("BOX_CNT_PER_HOUR");

    /** BOX_CNT_PER_HOUR_AVG */
    public static final ParamKey BOX_CNT_PER_HOUR_AVG = new ParamKey("BOX_CNT_PER_HOUR_AVG");

    /** BOX_PER_ORDER */
    public static final ParamKey BOX_PER_ORDER = new ParamKey("BOX_PER_ORDER");

    /** BOX_PER_ORDER_AVG */
    public static final ParamKey BOX_PER_ORDER_AVG = new ParamKey("BOX_PER_ORDER_AVG");

    /** CNT */
    public static final ParamKey CNT = new ParamKey("CNT");

    /** CNT_AVG */
    public static final ParamKey CNT_AVG = new ParamKey("CNT_AVG");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CONSIGNOR_NAME */
    public static final ParamKey CONSIGNOR_NAME = new ParamKey("CONSIGNOR_NAME");

    /** END_DATE */
    public static final ParamKey END_DATE = new ParamKey("END_DATE");

    /** HEADS */
    public static final ParamKey HEADS = new ParamKey("HEADS");

    /** HEADS_AVG */
    public static final ParamKey HEADS_AVG = new ParamKey("HEADS_AVG");

    /** LEVEL */
    public static final ParamKey LEVEL = new ParamKey("LEVEL");

    /** LINE_CNT */
    public static final ParamKey LINE_CNT = new ParamKey("LINE_CNT");

    /** LINE_CNT_AVG */
    public static final ParamKey LINE_CNT_AVG = new ParamKey("LINE_CNT_AVG");

    /** LINE_CNT_PER_HOUR */
    public static final ParamKey LINE_CNT_PER_HOUR = new ParamKey("LINE_CNT_PER_HOUR");

    /** LINE_CNT_PER_HOUR_AVG */
    public static final ParamKey LINE_CNT_PER_HOUR_AVG = new ParamKey("LINE_CNT_PER_HOUR_AVG");

    /** LINE_PER_ORDER */
    public static final ParamKey LINE_PER_ORDER = new ParamKey("LINE_PER_ORDER");

    /** LINE_PER_ORDER_AVG */
    public static final ParamKey LINE_PER_ORDER_AVG = new ParamKey("LINE_PER_ORDER_AVG");

    /** LOT_CNT */
    public static final ParamKey LOT_CNT = new ParamKey("LOT_CNT");

    /** LOT_CNT_AVG */
    public static final ParamKey LOT_CNT_AVG = new ParamKey("LOT_CNT_AVG");

    /** LOT_CNT_PER_HOUR */
    public static final ParamKey LOT_CNT_PER_HOUR = new ParamKey("LOT_CNT_PER_HOUR");

    /** LOT_CNT_PER_HOUR_AVG */
    public static final ParamKey LOT_CNT_PER_HOUR_AVG = new ParamKey("LOT_CNT_PER_HOUR_AVG");

    /** LOT_PER_LINE */
    public static final ParamKey LOT_PER_LINE = new ParamKey("LOT_PER_LINE");

    /** LOT_PER_LINE_AVG */
    public static final ParamKey LOT_PER_LINE_AVG = new ParamKey("LOT_PER_LINE_AVG");

    /** MISS */
    public static final ParamKey MISS = new ParamKey("MISS");

    /** MISS_AVG */
    public static final ParamKey MISS_AVG = new ParamKey("MISS_AVG");

    /** ORDER_CNT */
    public static final ParamKey ORDER_CNT = new ParamKey("ORDER_CNT");

    /** ORDER_CNT_AVG */
    public static final ParamKey ORDER_CNT_AVG = new ParamKey("ORDER_CNT_AVG");

    /** ORDER_CNT_PER_HOUR */
    public static final ParamKey ORDER_CNT_PER_HOUR = new ParamKey("ORDER_CNT_PER_HOUR");

    /** ORDER_CNT_PER_HOUR_AVG */
    public static final ParamKey ORDER_CNT_PER_HOUR_AVG = new ParamKey("ORDER_CNT_PER_HOUR_AVG");

    /** PIECE_CNT */
    public static final ParamKey PIECE_CNT = new ParamKey("PIECE_CNT");

    /** PIECE_CNT_AVG */
    public static final ParamKey PIECE_CNT_AVG = new ParamKey("PIECE_CNT_AVG");

    /** PIECE_CNT_PER_HOUR */
    public static final ParamKey PIECE_CNT_PER_HOUR = new ParamKey("PIECE_CNT_PER_HOUR");

    /** PIECE_CNT_PER_HOUR_AVG */
    public static final ParamKey PIECE_CNT_PER_HOUR_AVG = new ParamKey("PIECE_CNT_PER_HOUR_AVG");

    /** PIECE_PER_LINE */
    public static final ParamKey PIECE_PER_LINE = new ParamKey("PIECE_PER_LINE");

    /** PIECE_PER_LINE_AVG */
    public static final ParamKey PIECE_PER_LINE_AVG = new ParamKey("PIECE_PER_LINE_AVG");

    /** PRODUCTION_RATE */
    public static final ParamKey PRODUCTION_RATE = new ParamKey("PRODUCTION_RATE");

    /** PRODUCTION_RATE_AVG */
    public static final ParamKey PRODUCTION_RATE_AVG = new ParamKey("PRODUCTION_RATE_AVG");

    /** START_DATE */
    public static final ParamKey START_DATE = new ParamKey("START_DATE");

    /** SYS_DAY */
    public static final ParamKey SYS_DAY = new ParamKey("SYS_DAY");

    /** SYS_TIME */
    public static final ParamKey SYS_TIME = new ParamKey("SYS_TIME");

    /** WEEK_DAY */
    public static final ParamKey WEEK_DAY = new ParamKey("WEEK_DAY");

    /** WORK_DAY_FROM */
    public static final ParamKey WORK_DAY_FROM = new ParamKey("WORK_DAY_FROM");

    /** WORK_DAY_TO */
    public static final ParamKey WORK_DAY_TO = new ParamKey("WORK_DAY_TO");

    /** WORK_TIME */
    public static final ParamKey WORK_TIME = new ParamKey("WORK_TIME");

    /** WORK_TIME_AVG */
    public static final ParamKey WORK_TIME_AVG = new ParamKey("WORK_TIME_AVG");

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
    public PctUserResultBatchListParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public PctUserResultBatchListParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public PctUserResultBatchListParams(Map<Key, Object> initMap)
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

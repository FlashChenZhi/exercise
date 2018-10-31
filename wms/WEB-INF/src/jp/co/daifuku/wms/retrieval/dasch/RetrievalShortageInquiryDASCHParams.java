// $Id: RetrievalShortageInquiryDASCHParams.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.retrieval.dasch;

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
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class RetrievalShortageInquiryDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ALLOCATED */
    public static final ParamKey ALLOCATED = new ParamKey("ALLOCATED");

    /** ALLOCATED_PATTEN_NAME */
    public static final ParamKey ALLOCATED_PATTEN_NAME = new ParamKey("ALLOCATED_PATTEN_NAME");

    /** ALLOCATED_PATTERN_NO */
    public static final ParamKey ALLOCATED_PATTERN_NO = new ParamKey("ALLOCATED_PATTERN_NO");

    /** BATCH */
    public static final ParamKey BATCH = new ParamKey("BATCH");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** CASE_PACK */
    public static final ParamKey CASE_PACK = new ParamKey("CASE_PACK");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** CUSTOMER_NAME */
    public static final ParamKey CUSTOMER_NAME = new ParamKey("CUSTOMER_NAME");

    /** DFK_DS_NO */
    public static final ParamKey DFK_DS_NO = new ParamKey("DFK_DS_NO");

    /** DFK_USER_ID */
    public static final ParamKey DFK_USER_ID = new ParamKey("DFK_USER_ID");

    /** DFK_USER_NAME */
    public static final ParamKey DFK_USER_NAME = new ParamKey("DFK_USER_NAME");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** ORDER */
    public static final ParamKey ORDER = new ParamKey("ORDER");

    /** ORDER_NO */
    public static final ParamKey ORDER_NO = new ParamKey("ORDER_NO");

    /** ORDER_NO_TO */
    public static final ParamKey ORDER_NO_TO = new ParamKey("ORDER_NO_TO");

    /** PLAN_CASE_QTY */
    public static final ParamKey PLAN_CASE_QTY = new ParamKey("PLAN_CASE_QTY");

    /** PLAN_DATE */
    public static final ParamKey PLAN_DATE = new ParamKey("PLAN_DATE");

    /** PLAN_DAY */
    public static final ParamKey PLAN_DAY = new ParamKey("PLAN_DAY");

    /** PLAN_LOT_NO */
    public static final ParamKey PLAN_LOT_NO = new ParamKey("PLAN_LOT_NO");

    /** PLAN_PIECE_QTY */
    public static final ParamKey PLAN_PIECE_QTY = new ParamKey("PLAN_PIECE_QTY");

    /** REP_CASE_QTY */
    public static final ParamKey REP_CASE_QTY = new ParamKey("REP_CASE_QTY");

    /** REP_PIECE_QTY */
    public static final ParamKey REP_PIECE_QTY = new ParamKey("REP_PIECE_QTY");

    /** REPLENISHMENT_SHORTAGE_FLAG */
    public static final ParamKey REPLENISHMENT_SHORTAGE_FLAG = new ParamKey("REPLENISHMENT_SHORTAGE_FLAG");

    /** SHORTAGE_CASE_QTY */
    public static final ParamKey SHORTAGE_CASE_QTY = new ParamKey("SHORTAGE_CASE_QTY");

    /** SHORTAGE_PIECE_QTY */
    public static final ParamKey SHORTAGE_PIECE_QTY = new ParamKey("SHORTAGE_PIECE_QTY");

    /** START_DATE */
    public static final ParamKey START_DATE = new ParamKey("START_DATE");

    /** START_DAY */
    public static final ParamKey START_DAY = new ParamKey("START_DAY");

    /** START_TIM */
    public static final ParamKey START_TIM = new ParamKey("START_TIM");

    /** START_TIME */
    public static final ParamKey START_TIME = new ParamKey("START_TIME");

    /** START_UNIT_KEY */
    public static final ParamKey START_UNIT_KEY = new ParamKey("START_UNIT_KEY");

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
    public RetrievalShortageInquiryDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public RetrievalShortageInquiryDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public RetrievalShortageInquiryDASCHParams(Map<Key, Object> initMap)
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

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
 * @version
 * @author BusiTune 1.0 Generator.
 */
public class RetrievalResultInquiryDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** BATCH */
    public static final ParamKey BATCH = new ParamKey("BATCH");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** CASE_ITF */
    public static final ParamKey CASE_ITF = new ParamKey("CASE_ITF");

    /** CASE_PACK */
    public static final ParamKey CASE_PACK = new ParamKey("CASE_PACK");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** CUSTOMER_CODE_NO */
    public static final ParamKey CUSTOMER_CODE_NO = new ParamKey("CUSTOMER_CODE_NO");

    /** CUSTOMER_NAME */
    public static final ParamKey CUSTOMER_NAME = new ParamKey("CUSTOMER_NAME");

    /** DFK_DS_NO */
    public static final ParamKey DFK_DS_NO = new ParamKey("DFK_DS_NO");

    /** DFK_USER_ID */
    public static final ParamKey DFK_USER_ID = new ParamKey("DFK_USER_ID");

    /** DFK_USER_NAME */
    public static final ParamKey DFK_USER_NAME = new ParamKey("DFK_USER_NAME");

    /** GROUP_CONDITION */
    public static final ParamKey GROUP_CONDITION = new ParamKey("GROUP_CONDITION");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** LINE */
    public static final ParamKey LINE = new ParamKey("LINE");

    /** LOT */
    public static final ParamKey LOT = new ParamKey("LOT");

    /** ORDER */
    public static final ParamKey ORDER = new ParamKey("ORDER");

    /** ORDER_NO */
    public static final ParamKey ORDER_NO = new ParamKey("ORDER_NO");

    /** PICKING_AREA */
    public static final ParamKey PICKING_AREA = new ParamKey("PICKING_AREA");

    /** PICKING_DATE */
    public static final ParamKey PICKING_DATE = new ParamKey("PICKING_DATE");

    /** PICKING_LOCATION */
    public static final ParamKey PICKING_LOCATION = new ParamKey("PICKING_LOCATION");

    /** PLAN_CASE_QTY */
    public static final ParamKey PLAN_CASE_QTY = new ParamKey("PLAN_CASE_QTY");

    /** PLAN_DATE */
    public static final ParamKey PLAN_DATE = new ParamKey("PLAN_DATE");

    /** PLAN_PIECE_QTY */
    public static final ParamKey PLAN_PIECE_QTY = new ParamKey("PLAN_PIECE_QTY");

    /** RESULT_CASE_QTY */
    public static final ParamKey RESULT_CASE_QTY = new ParamKey("RESULT_CASE_QTY");

    /** RESULT_PIECE_QTY */
    public static final ParamKey RESULT_PIECE_QTY = new ParamKey("RESULT_PIECE_QTY");

    /** RETRIEVAL_AREA */
    public static final ParamKey RETRIEVAL_AREA = new ParamKey("RETRIEVAL_AREA");

    /** RETRIEVAL_DATE_FROM */
    public static final ParamKey RETRIEVAL_DATE_FROM = new ParamKey("RETRIEVAL_DATE_FROM");

    /** RETRIEVAL_DATE_TO */
    public static final ParamKey RETRIEVAL_DATE_TO = new ParamKey("RETRIEVAL_DATE_TO");

    /** SERIAL */
    public static final ParamKey SERIAL = new ParamKey("SERIAL");

    /** SHORTAGE_CASE_QTY */
    public static final ParamKey SHORTAGE_CASE_QTY = new ParamKey("SHORTAGE_CASE_QTY");

    /** SHORTAGE_PIECE_QTY */
    public static final ParamKey SHORTAGE_PIECE_QTY = new ParamKey("SHORTAGE_PIECE_QTY");

    /** SLIP_NUMBER */
    public static final ParamKey SLIP_NUMBER = new ParamKey("SLIP_NUMBER");

    /** SYS_DAY */
    public static final ParamKey SYS_DAY = new ParamKey("SYS_DAY");

    /** SYS_TIME */
    public static final ParamKey SYS_TIME = new ParamKey("SYS_TIME");

    /** TERMINAL */
    public static final ParamKey TERMINAL = new ParamKey("TERMINAL");

    /** TICKET */
    public static final ParamKey TICKET = new ParamKey("TICKET");

    /** UPC_CODE */
    public static final ParamKey UPC_CODE = new ParamKey("UPC_CODE");

    /** USER_NAME */
    public static final ParamKey USER_NAME = new ParamKey("USER_NAME");

    /** WORK_DATE */
    public static final ParamKey WORK_DATE = new ParamKey("WORK_DATE");

    /** WORK_TIME */
    public static final ParamKey WORK_TIME = new ParamKey("WORK_TIME");

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
    public RetrievalResultInquiryDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public RetrievalResultInquiryDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public RetrievalResultInquiryDASCHParams(Map<Key, Object> initMap)
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

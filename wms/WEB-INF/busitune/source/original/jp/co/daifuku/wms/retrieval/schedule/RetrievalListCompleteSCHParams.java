package jp.co.daifuku.wms.retrieval.schedule;

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
public class RetrievalListCompleteSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** CASE_QTY */
    public static final ParamKey CASE_QTY = new ParamKey("CASE_QTY");

    /** CODE */
    public static final ParamKey CODE = new ParamKey("CODE");

    /** COLLECT_JOBNO */
    public static final ParamKey COLLECT_JOBNO = new ParamKey("COLLECT_JOBNO");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** INITIAL_INPUT_RETRIEVAL_NO */
    public static final ParamKey INITIAL_INPUT_RETRIEVAL_NO = new ParamKey("INITIAL_INPUT_RETRIEVAL_NO");

    /** LIST_WORK_NO */
    public static final ParamKey LIST_WORK_NO = new ParamKey("LIST_WORK_NO");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** LR_LIST_WORK_NO */
    public static final ParamKey LR_LIST_WORK_NO = new ParamKey("LR_LIST_WORK_NO");

    /** LR_RETRIEVAL_PLAN_DATE */
    public static final ParamKey LR_RETRIEVAL_PLAN_DATE = new ParamKey("LR_RETRIEVAL_PLAN_DATE");

    /** NAME */
    public static final ParamKey NAME = new ParamKey("NAME");

    /** ORDER_NO */
    public static final ParamKey ORDER_NO = new ParamKey("ORDER_NO");

    /** PIECE_QTY */
    public static final ParamKey PIECE_QTY = new ParamKey("PIECE_QTY");

    /** PLAN_LOT */
    public static final ParamKey PLAN_LOT = new ParamKey("PLAN_LOT");

    /** QTY */
    public static final ParamKey QTY = new ParamKey("QTY");

    /** RETRIEVAL_PLAN_DATE */
    public static final ParamKey RETRIEVAL_PLAN_DATE = new ParamKey("RETRIEVAL_PLAN_DATE");

    /** SHORTAGE */
    public static final ParamKey SHORTAGE = new ParamKey("SHORTAGE");

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
    public RetrievalListCompleteSCHParams()
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

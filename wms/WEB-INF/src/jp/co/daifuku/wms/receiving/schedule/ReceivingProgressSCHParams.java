package jp.co.daifuku.wms.receiving.schedule;

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
public class ReceivingProgressSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CASE_QTY */
    public static final ParamKey CASE_QTY = new ParamKey("CASE_QTY");

    /** DETAIL_COUNT */
    public static final ParamKey DETAIL_COUNT = new ParamKey("DETAIL_COUNT");

    /** PIECE_QTY */
    public static final ParamKey PIECE_QTY = new ParamKey("PIECE_QTY");

    /** PLAN_DAY */
    public static final ParamKey PLAN_DAY = new ParamKey("PLAN_DAY");

    /** PROCESS_FLAG */
    public static final ParamKey PROCESS_FLAG = new ParamKey("PROCESS_FLAG");

    /** PROGRESS_DISPLAY */
    public static final ParamKey PROGRESS_DISPLAY = new ParamKey("PROGRESS_DISPLAY");

    /** PROGRESS_RATE_DOWN */
    public static final ParamKey PROGRESS_RATE_DOWN = new ParamKey("PROGRESS_RATE_DOWN");

    /** PROGRESS_RATE_UP */
    public static final ParamKey PROGRESS_RATE_UP = new ParamKey("PROGRESS_RATE_UP");

    /** RECEIVE_COUNT */
    public static final ParamKey RECEIVE_COUNT = new ParamKey("RECEIVE_COUNT");

    /** SHORTAGE_QTY */
    public static final ParamKey SHORTAGE_QTY = new ParamKey("SHORTAGE_QTY");
    
    // DFKLOOK ここから
    /** RESULT_CASE_QTY */
    public static final ParamKey RESULT_CASE_QTY = new ParamKey("RESULT_CASE_QTY");

    /** RESULT_PIECE_QTY */
    public static final ParamKey RESULT_PIECE_QTY = new ParamKey("RESULT_PIECE_QTY");
    
    /** ボタン制御フラグ */
    public static final ParamKey  BUTTON_CONTROL_FLAG = new ParamKey("BUTTON_CONTROL_FLAG");
    
    /** 実績数 **/
    public static final ParamKey  RESULT_QTY = new ParamKey("RESULT_QTY");

    /** 予定カウント **/
    public static final ParamKey  PLAN_ITEM_CNT = new ParamKey("PLAN_ITEM_CNT");
    
    /** 予定数 **/
    public static final ParamKey  PLAN_QTY = new ParamKey("PLAN_QTY");

    /** 実績カウント **/
    public static final ParamKey  RESULT_ITEM_CNT = new ParamKey("RESULT_ITEM_CNT");
    
    /** ProgressRate **/
    public static final ParamKey  PROGRESS_RATE = new ParamKey("PROGRESS_RATE");
    
    /** CONSIGNOR_CODE */
    public static final ParamKey  CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");
    
    // DFKLOOK ここまで

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
    public ReceivingProgressSCHParams()
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

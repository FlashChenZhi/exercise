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
public class RetrievalProgressSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** CASE_QTY */
    public static final ParamKey CASE_QTY = new ParamKey("CASE_QTY");

    /** DETAIL_COUNT */
    public static final ParamKey DETAIL_COUNT = new ParamKey("DETAIL_COUNT");

    /** GROUP */
    public static final ParamKey GROUP = new ParamKey("GROUP");

    /** ORDER_COUNT */
    public static final ParamKey ORDER_COUNT = new ParamKey("ORDER_COUNT");

    /** PIECE_QTY */
    public static final ParamKey PIECE_QTY = new ParamKey("PIECE_QTY");

    /** PLAN_DAY */
    public static final ParamKey PLAN_DAY = new ParamKey("PLAN_DAY");

    /** PROCESS_FLAG */
    public static final ParamKey PROCESS_FLAG = new ParamKey("PROCESS_FLAG");

    /** PROGRESS_DISPLAY */
    public static final ParamKey PROGRESS_DISPLAY = new ParamKey("PROGRESS_DISPLAY");

    /** PROGRESS_RATE1 */
    public static final ParamKey PROGRESS_RATE1 = new ParamKey("PROGRESS_RATE1");

    /** PROGRESS_RATE2 */
    public static final ParamKey PROGRESS_RATE2 = new ParamKey("PROGRESS_RATE2");

    /** RETRIEVAL_COUNT */
    public static final ParamKey RETRIEVAL_COUNT = new ParamKey("RETRIEVAL_COUNT");

    /** SHORTAGE_QTY */
    public static final ParamKey SHORTAGE_QTY = new ParamKey("SHORTAGE_QTY");

    /** TICKET_COUNT */
    public static final ParamKey TICKET_COUNT = new ParamKey("TICKET_COUNT");
    
    // DFKLOOK ここから
    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");
    
    /** COLLECT_FLAG  */
    public static final ParamKey COLLECT_FLAG = new ParamKey("COLLECT_FLAG");
    
    /** ボタン制御フラグ */
    public static final ParamKey  BUTTON_CONTROL_FLAG = new ParamKey("BUTTON_CONTROL_FLAG");
    
    /** PLAN_ORDER_COUNT **/
    public static final ParamKey  PLAN_ORDER_COUNT = new ParamKey("PLAN_ORDER_COUNT");
    
    /** RESULT_ORDER_COUNT **/
    public static final ParamKey  RESULT_ORDER_COUNT = new ParamKey("RESULT_ORDER_COUNT");
       
    /** PLAN_ITEM_CONT **/
    public static final ParamKey  PLAN_ITEM_CONT = new ParamKey("PLAN_ITEM_CONT");
    
    /** RESULT_ITEM_CONT **/
    public static final ParamKey  RESULT_ITEM_CONT = new ParamKey("RESULT_ITEM_CONT");
    
    /** PLAN_QTY **/
    public static final ParamKey  PLAN_QTY = new ParamKey("PLAN_QTY");
    
    /** RESULT_QTY **/
    public static final ParamKey  RESULT_QTY = new ParamKey("RESULT_QTY");
    
    /** PLAN_CASE_QTY **/
    public static final ParamKey  PLAN_CASE_QTY = new ParamKey("PLAN_CASE_QTY");
    
    /** PLAN_PIECE_QTY **/
    public static final ParamKey  PLAN_PIECE_QTY = new ParamKey("PLAN_PIECE_QTY");
    
    /** RESULT_CASE_QTY **/
    public static final ParamKey  RESULT_CASE_QTY = new ParamKey("RESULT_CASE_QTY");
    
    /** RESULT_PIECE_QTY **/
    public static final ParamKey  RESULT_PIECE_QTY = new ParamKey("RESULT_PIECE_QTY");
    
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
    public RetrievalProgressSCHParams()
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

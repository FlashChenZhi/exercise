// $Id: XDProgressSCHParams.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.crossdock.schedule;

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
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class XDProgressSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** GROUP */
    public static final ParamKey GROUP = new ParamKey("GROUP");

    /** PLAN_DAY */
    public static final ParamKey PLAN_DAY = new ParamKey("PLAN_DAY");

    /** PROCESS_FLAG */
    public static final ParamKey PROCESS_FLAG = new ParamKey("PROCESS_FLAG");

    /** PROGRESS_DISPLAY */
    public static final ParamKey PROGRESS_DISPLAY = new ParamKey("PROGRESS_DISPLAY");

    /** PROGRESS_RATE_RECEIVING */
    public static final ParamKey PROGRESS_RATE_RECEIVING = new ParamKey("PROGRESS_RATE_RECEIVING");

    /** PROGRESS_RATE_RECEIVING2 */
    public static final ParamKey PROGRESS_RATE_RECEIVING2 = new ParamKey("PROGRESS_RATE_RECEIVING2");

    /** PROGRESS_RATE_SORT */
    public static final ParamKey PROGRESS_RATE_SORT = new ParamKey("PROGRESS_RATE_SORT");

    /** PROGRESS_RATE_SORT2 */
    public static final ParamKey PROGRESS_RATE_SORT2 = new ParamKey("PROGRESS_RATE_SORT2");

    /** RECEIVE_CASE_QTY */
    public static final ParamKey RECEIVE_CASE_QTY = new ParamKey("RECEIVE_CASE_QTY");

    /** RECEIVE_DETAIL_COUNT */
    public static final ParamKey RECEIVE_DETAIL_COUNT = new ParamKey("RECEIVE_DETAIL_COUNT");

    /** RECEIVE_PIECE_QTY */
    public static final ParamKey RECEIVE_PIECE_QTY = new ParamKey("RECEIVE_PIECE_QTY");

    /** SHORTAGE_QTY */
    public static final ParamKey SHORTAGE_QTY = new ParamKey("SHORTAGE_QTY");

    /** SORT_CASE_QTY */
    public static final ParamKey SORT_CASE_QTY = new ParamKey("SORT_CASE_QTY");

    /** SORT_DETAIL_COUNT */
    public static final ParamKey SORT_DETAIL_COUNT = new ParamKey("SORT_DETAIL_COUNT");

    /** SORT_PIECE_QTY */
    public static final ParamKey SORT_PIECE_QTY = new ParamKey("SORT_PIECE_QTY");

    // DFKLOOK ここから
    /** COLLECT_FLAG */
    public static final ParamKey COLLECT_FLAG = new ParamKey("COLLECT_FLAG");
    
    /** RESULT_CASE_QTY */
    public static final ParamKey RESULT_CASE_QTY = new ParamKey("RESULT_CASE_QTY");

    /** RESULT_PIECE_QTY */
    public static final ParamKey RESULT_PIECE_QTY = new ParamKey("RESULT_PIECE_QTY");
    
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
    
    /** EnabledPrevPage */
    public static final ParamKey  ENABLED_PREV_FLAG = new ParamKey("ENABLED_PREV_FLAG");
    
    /** EnabledNextPage */
    public static final ParamKey  ENABLED_NEXT_FLAG = new ParamKey("ENABLED_NEXT_FLAG");
        
    /** CONSIGNOR_CODE */
    public static final ParamKey  CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");
    
    /** PLAN_RECEIVE_CASE */
    public static final ParamKey  PLAN_RECEIVE_CASE_KEY = new ParamKey("PLAN_RECEIVE_CASE_KEY");
    
    /** RESULT_RECEIVE_CASE */
    public static final ParamKey  RESULT_RECEIVE_CASE_KEY = new ParamKey("RESULT_RECEIVE_CASE_KEY");
    
    /** PLAN_RECEIVE_PIECE */
    public static final ParamKey  PLAN_RECEIVE_PIECE_KEY = new ParamKey("PLAN_RECEIVE_PIECE_KEY");
    
    /** RESULT_RECEIVE_PIECE */
    public static final ParamKey  RESULT_RECEIVE_PIECE_KEY = new ParamKey("RESULT_RECEIVE_PIECE_KEY");
    
    /** PLAN_SORT_CASE */
    public static final ParamKey  PLAN_SORT_CASE_KEY = new ParamKey("PLAN_SORT_CASE_KEY");
    
    /** RESULT_SORT_CASE */
    public static final ParamKey  RESULT_SORT_CASE_KEY = new ParamKey("RESULT_SORT_CASE_KEY");
    
    /** PLAN_SORT_PIECE */
    public static final ParamKey  PLAN_SORT_PIECE_KEY = new ParamKey("PLAN_SORT_PIECE_KEY");
    
    /** RESULT_SORT_PIECE */
    public static final ParamKey  RESULT_SORT_PIECE_KEY = new ParamKey("RESULT_SORT_PIECE_KEY");
    
    /** 入荷進捗率 */
    public static final ParamKey  RECEIVE_PROGRESS = new ParamKey("RECEIVE_PROGRESS");
    
    /** 仕分け進捗率 */
    public static final ParamKey  SORT_PROGRESS = new ParamKey("SORT_PROGRESS");
    
    /** 入荷明細数 分母 **/
    public static final ParamKey  RECEIVE_COUNT_DENOMINATOR = new ParamKey("RECEIVE_COUNT_DENOMINATOR");
    
    /** 入荷明細数 分子 **/
    public static final ParamKey  RECEIVE_COUNT_MOLECULE = new ParamKey("RECEIVE_COUNT_MOLECULE");
    
    /** 仕分明細数 分母 **/
    public static final ParamKey  SORT_COUNT_DENOMINATOR = new ParamKey("SORT_COUNT_DENOMINATOR");
    
    /** 仕分明細数 分子 **/
    public static final ParamKey  SORT_COUNT_MOLECULE = new ParamKey("SORT_COUNT_MOLECULE");
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
     * ユーザ情報を元にパラメータクラスを作成します。
     * @param dui ユーザ情報
     */
    public XDProgressSCHParams(DfkUserInfo dui)
    {
        super(dui);
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

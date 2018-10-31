// $Id: PCTRetrievalListBoxDefine.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.retrieval.listbox;

import jp.co.daifuku.wms.base.common.ListBoxDefine;


/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer :  K.Matsuse<BR>
 * Maker :     K.Matsuse<BR>
 * PCT出庫パッケージで使用するリストボックスの定義情報を管理するクラスです。
 * 親画面からリストボックスを呼び出すときに指定する検索条件とURLを定義します。
 * 検索キーやリストボックスの追加が発生した場合、本クラスを変更してください。<BR>
 * 1.検索キー<BR>
 * 2.リストボックスのパス<BR>
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  K.Matsuse
 * @author  Last commit: $Author: arai $
 */
public final class PCTRetrievalListBoxDefine extends ListBoxDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    // リストボックキー定義

    /**
     * 作業日の受け渡しに使用するキーです
     */
    public static final String WORKDAY_KEY = "WORKDAY_KEY";

    /**
     * 作業日(TO)の受け渡しに使用するキーです
     */
    public static final String TO_WORKDAY_KEY = "TO_WORKDAY_KEY";

    /**
     * 範囲指定フラグの受け渡しに使用するキーです
     */
    public static final String RANGE_FLAG_KEY = "RANGE_FLAG_KEY";

    /**
     * 曜日指定フラグの受け渡しに使用するキーです
     */
    public static final String USE_DAY_OF_WEEK_FLAG_KEY = "USE_DAY_OF_WEEK_FLAG_KEY";

    /**
     * 月曜検索フラグの受け渡しに使用するキーです
     */
    public static final String MONDAY_FLAG_KEY = "MONDAY_FLAG_KEY";

    /**
     * 火曜検索フラグの受け渡しに使用するキーです
     */
    public static final String TUESDAY_FLAG_KEY = "TUESDAY_FLAG_KEY";

    /**
     * 水曜検索フラグの受け渡しに使用するキーです
     */
    public static final String WEDNESDAY_FLAG_KEY = "WEDNESDAY_FLAG_KEY";

    /**
     * 木曜検索フラグの受け渡しに使用するキーです
     */
    public static final String THURSDAY_FLAG_KEY = "THURSDAY_FLAG_KEY";

    /**
     * 金曜検索フラグの受け渡しに使用するキーです
     */
    public static final String FRIDAY_FLAG_KEY = "FRIDAY_FLAG_KEY";

    /**
     * 土曜検索フラグの受け渡しに使用するキーです
     */
    public static final String SATURDAY_FLAG_KEY = "SATURDAY_FLAG_KEY";

    /**
     * 日曜検索フラグの受け渡しに使用するキーです
     */
    public static final String SUNDAY_FLAG_KEY = "SUNDAY_FLAG_KEY";

    /**
     * 集約単位の受け渡しに使用するキーです
     */
    public static final String COLLECT_CONDITION_KEY = "COLLECT_CONDITION_KEY";

    /**
     * 表示ランクの受け渡しに使用するキーです
     */
    public static final String DISPLAY_RANK_KEY = "DISPLAY_RANK_KEY";

    /**
     * ランクの受け渡しに使用するキーです
     */
    public static final String RANK_KEY = "RANK_KEY";

    /**
     * エリアNo.の受け渡しに使用するキーです
     */
    public static final String AREA_NO_KEY = "AREA_NO_KEY";
    /**
     * エリア名称の受け渡しに使用するキーです
     */
    public static final String AREA_NAME_KEY = "AREA_NAME_KEY";

    /**
     * バッチNo.の受け渡しに使用するキーです
     */
    public static final String BATCHNO_KEY = "BATCHNO_KEY";

    /**
     * バッチSeqNo.の受け渡しに使用するキーです
     */
    public static final String BATCHSEQNO_KEY = "BATCHSEQNO_KEY";

    /**
     * 荷主コードの受け渡しに使用するキーです
     */
    public static final String CONSIGNOR_CODE_KEY = "CONSIGNORCODE_KEY";

    /**
     * フラグ：あり
     */
    public static final String FLAG_TRUE = "1";

    /**
     * フラグ：なし
     */
    public static final String FLAG_FALSE = "0";

    /**
     * 荷主名称の受け渡しに使用するキーです
     */
    public static final String CONSIGNORNAME_KEY = "CONSIGNORNAME_KEY";
     /**
     * 予定日の受け渡しに使用するキーです
     */
    public static final String PLANDAY_KEY = "PLANDAY_KEY";
    /**
     * 得意先コードの受け渡しに使用するキーです
     */
    public static final String REGULARCUSTOMERCODE_KEY = "REGULARCUSTOMERCODE_KEY";
    /**
     * 得意先名称の受け渡しに使用するキーです
     */
    public static final String REGULARCUSTOMERNAME_KEY = "REGULARCUSTOMERNAME_KEY";
    /**
     * 出荷先コードの受け渡しに使用するキーです
     */
    public static final String CUSTOMERCODE_KEY = "CUSTOMERCODE_KEY";
    /**
     * 出荷先名称の受け渡しに使用するキーです
     */
    public static final String CUSTOMERNAME_KEY = "CUSTOMERNAME_KEY";
    /**
     * オーダーNo.の受け渡しに使用するキーです
     */
    public static final String ORDERNO_KEY = "ORDERNO_KEY";
    /**
     * 商品コードの受け渡しに使用するキーです
     */
    public static final String ITEMCODE_KEY = "ITEMCODE_KEY";
    /**
     * 商品名称の受け渡しに使用するキーです
     */
    public static final String ITEMNAME_KEY = "ITEMNAME_KEY";
    /**
     * 作業状態の受け渡しに使用するキーです
     */
    public static final String STATUSFLAG_KEY = "STATUSFLAG_KEY";
    /**
     * 作業状態の受け渡しに使用するキーです
     */
    public static final String STATUSFLAG2_KEY = "STATUSFLAG2_KEY";
    /**
     * バッチ状態の受け渡しに使用するキーです
     */
    public static final String BATCHSTATUSFLAG_KEY = "BATCHSTATUSFLAG_KEY";
    /**
     * 号機No.の受け渡しに使用するキーです
     */
    public static final String RFTNO_KEY = "RFTNO_KEY";
    /**
     * SeqNo.の受け渡しに使用するキーです
     */
    public static final String SEQNO_KEY = "SEQNO_KEY";
    /**
     * ユーザIDの受け渡しに使用するキーです
     */
    public static final String USERID_KEY = "USERID_KEY";
    /**
     * ユーザID(TO)の受け渡しに使用するキーです
     */
    public static final String TO_USERID_KEY = "TO_USERID_KEY";
    /**
     * 検索対象テーブルの受け渡しに使用するキーです
     */
    public static final String SEARCHTABLE_KEY = "SEARCHTABLE_KEY";

    /**
     * 開始終了フラグの受け渡しに使用するキーです
     */
    public static final String RANGE_KEY = "RANG_KEY";

    /**
     * 範囲フラグ：開始
     */
    public static final String RANGE_START = "0";

    /**
     * 範囲フラグ：終了
     */
    public static final String RANGE_END = "1";

    /**
     * 遷移元の受け渡しに使用するキーです
     */
    public static final String TRANSITION_KEY = "TRANSITION_KEY";

    /**
     * 終了予測時間基準フラグの受け渡しに使用するキーです
     */
    public static final String ENDPLANTIME_STANDARD_FLAG_KEY = "ENDPLANTIME_STANDARD_FLAG__KEY";    
        
    /**
     * 日曜日
     */
    public static final String SUNDAY = "1";

    /**
     * 月曜日
     */
    public static final String MONDAY = "2";

    /**
     * 火曜日
     */
    public static final String TUESDAY = "3";

    /**
     * 水曜日
     */
    public static final String WEDNESDAY = "4";

    /**
     * 木曜日
     */
    public static final String THURSDAY = "5";

    /**
     * 金曜日
     */
    public static final String FRIDAY = "6";

    /**
     * 土曜日
     */
    public static final String SATURDAY = "7";

    /**
     * 作業月の受け渡しに使用するキーです
     */
    public static final String WORKMONTH_KEY = "WORKMONTH_KEY";

    /**
     * 作業月(TO)の受け渡しに使用するキーです
     */
    public static final String TO_WORKMONTH_KEY = "TO_WORKMONTH_KEY";

    
    // リストボックスパス定義
    /**
     * 実績一覧リストボックス(作業者別)のパスです
     */
    public static final String LST_PCT_USR_RESULT_WORKER = "/pcart/retrieval/listbox/pctuserresultworker/LstPCTUserResultWorker.do";
    /**
     * 実績一覧リストボックス(作業日別)のパスです
     */
    public static final String LST_PCT_USR_RESULT_WORKDATE = "/pcart/retrieval/listbox/pctuserresultworkdate/LstPCTUserResultWorkDate.do";
    /**
     * 実績一覧リストボックス(荷主別)のパスです
     */
    public static final String LST_PCT_USR_RESULT_CONSIGNOR = "/pcart/retrieval/listbox/pctuserresultconsignor/LstPCTUserResultConsignor.do";
    /**
     * 実績一覧リストボックス(エリア別)のパスです
     */
    public static final String LST_PCT_USR_RESULT_AREA = "/pcart/retrieval/listbox/pctuserresultarea/LstPCTUserResultArea.do";
    /**
     * 実績一覧リストボックス(バッチ別)のパスです
     */
    public static final String LST_PCT_USR_RESULT_BATCH = "/pcart/retrieval/listbox/pctuserresultbatch/LstPCTUserResultBatch.do";
    /**
     * 荷主検索リストボックスのパスです
     */
    public static final String LST_CONSIGNOR = "/pcart/retrieval/listbox/consignor/LstConsignor.do";
    /**
     * 予定日検索リストボックスのパスです
     */
    public static final String LST_PLANDAY = "/pcart/retrieval/listbox/planday/LstPlanDay.do";
    /**
     * バッチNo.検索リストボックスのパスです
     */
    public static final String LST_BATCH_NO = "/pcart/retrieval/listbox/batchno/LstBatchNo.do";
    /**
     * バッチSeqNo.検索リストボックスのパスです
     */
    public static final String LST_BATCH_SEQ_NO = "/pcart/retrieval/listbox/batchseqno/LstBatchSeqNo.do";
    /**
     * 得意先検索一覧リストボックスのパスです
     */
    public static final String LST_REGULARCUSTOMER = "/pcart/retrieval/listbox/regularCustomer/LstRegularCustomer.do";
    /**
     * 出荷先検索一覧リストボックスのパスです
     */
    public static final String LST_CUSTOMER = "/pcart/retrieval/listbox/customer/LstCustomer.do";
    /**
     * オーダーNo.検索リストボックスのパスです
     */
    public static final String LST_ORDER_NO = "/pcart/retrieval/listbox/order/LstOrder.do";
    /**
     * 商品検索一覧リストボックスのパスです
     */
    public static final String LST_ITEM = "/pcart/retrieval/listbox/item/LstItem.do";
    /**
     * 作業日一覧検索一覧リストボックスのパスです
     */
    public static final String LST_WORKDAY = "/pcart/retrieval/listbox/workday/LstWorkDay.do";    
    /**
     * 作業状況照会一覧リストボックスのパスです
     */
    public static final String LST_PCTRETWORKINGINQUIRY = "/pcart/retrieval/listbox/pctretworkinginquiry/LstPCTRetWorkingInquiry.do";
    /**
     * 端末別作業照会一覧リストボックスのパスです
     */
    public static final String LST_PCTRETRFTNOWORKINQUIRY = "/pcart/retrieval/listbox/pctretrftnoworkinquiry/LstPCTRetRftNoWorkInquiry.do";
    /**
     * 作業予定照会一覧リストボックスのパスです
     */
    public static final String LST_PCTRETPLANWORKINQUIRY = "/pcart/retrieval/listbox/pctretplanworkinquiry/LstPCTRetPlanWorkInquiry.do";
    /**
     * 作業実績照会一覧リストボックスのパスです
     */
    public static final String LST_PCTRETRESULTWORKINQUIRY = "/pcart/retrieval/listbox/pctretresultworkinquiry/LstPCTRetResultWorkInquiry.do";
    /**
     * 号機No.照会一覧リストボックスのパスです
     */
    public static final String LST_RFTNO =  "/pcart/retrieval/listbox/rftno/LstRftNo.do";
//    /**
//     * 出荷先別作業進捗一覧リストボックスのパスです
//     */
//    public static final String LST_PCTCUSTOMERPROGRESS =  "/pcart/retrieval/listbox/pctcustomerprogress/LstPCTCustomerProgress.do";
    /**
     * 作業メンテナンス（商品単位）のパスです
     */
    public static final String MTN_ITEM =  "/pcart/retrieval/resultmnt/PCTRetResultMnt2.do";
    /**
    * 作業メンテナンス（オーダー単位単位）のパスです
    */
    public static final String MTN_ORDER =  "/pcart/retrieval/resultmnt/PCTRetResultMnt.do";       
    /**
     * ユーザID検索一覧リストボックスのパスです
     */
    public static final String LST_USERID = "/pcart/retrieval/listbox/userid/LstUserId.do";    

//    /**
//     * 月別実績集計一覧リストボックスのパスです
//     */
//    public static final String LST_PCTRETMONTHRESULTINQUIRY = "/pcart/retrieval/listbox/pctretmonthresultinquiry/LstPCTRetMonthResultInquiry.do";    

    /**
     * ユーザ別生産性一覧リストボックスのパスです
     */
    public static final String LST_PCTPRODUCTIVITYINQUIRY = "/pcart/retrieval/listbox/pctproductivityinquiry/LstPCTProductivityInquiry.do";
    

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * default constructor.
     */
    private PCTRetrievalListBoxDefine()
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
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PCTRetrievalListBoxDefine.java 3209 2009-03-02 06:34:19Z arai $";
    }
}

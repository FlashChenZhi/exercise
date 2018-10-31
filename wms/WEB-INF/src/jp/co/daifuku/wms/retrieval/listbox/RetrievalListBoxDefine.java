// $Id: RetrievalListBoxDefine.java 3213 2009-03-02 06:59:20Z arai $
package jp.co.daifuku.wms.retrieval.listbox;

import jp.co.daifuku.wms.base.common.ListBoxDefine;


/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 出庫パッケージで使用するリストボックスの定義情報を管理するクラスです。
 * 親画面からリストボックスを呼び出すときに指定する検索条件とURLを定義します。
 * 検索キーやリストボックスの追加が発生した場合、本クラスを変更してください。<BR>
 * 1.検索キー<BR>
 * 2.リストボックスのパス<BR>
 * 
 * Designer :  Y.Miyake<BR>
 * Maker :     Y.Miyake<BR>
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  Last commit: $Author: arai $
 */
public final class RetrievalListBoxDefine extends ListBoxDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    // リストボックキー定義
    /**
     * 引当パターンNo.の受け渡しに使用するキーです。
     */
    public static final String ALLOCATE_PRIORITY_NO = "ALLOCATE_PRIORITY_NO";

    /**
     * 引当パターン名称の受け渡しに使用するキーです。
     */
    public static final String ALLOCATE_PRIORITY_NAME = "ALLOCATE_PRIORITY_NAME";

    /**
     * 引当パターン区分の受け渡しに使用するキーです。
     */
    public static final String ALLOCATE_PRIORITY_TYPE = "ALLOCATE_PRIORITY_TYPE";

    /**
     * 作業No.の受け渡しに使用するキーです
     */
    public static final String JOBNO_KEY = "JOBNO_KEY";

    /**
     * 荷主コードの受け渡しに使用するキーです
     */
    public static final String CONSIGNORCODE_KEY = "CONSIGNORCODE_KEY";

    /**
     * 出庫予定日の受け渡しに使用するキーです
     */
    public static final String RETRIEVALPLANDAY_KEY = "RETRIEVALPLANDAY_KEY";

    /**
     * 伝票No.の受け渡しに使用するキーです
     */
    public static final String TICKETNO_KEY = "TICKETNO_KEY";

    /**
     * バッチNo.の受け渡しに使用するキーです
     */
    public static final String BATCHNO_KEY = "BATCHNO_KEY";

    /**
     * オーダーNo.の受け渡しに使用するキーです
     */
    public static final String ORDERNO_KEY = "ORDERNO_KEY";

    /**
     * オーダーNo.(to)の受け渡しに使用するキーです
     */
    public static final String TO_ORDERNO_KEY = "TO_ORDERNO_KEY";

    /**
     * オーダーNo.の開始終了フラグの受け渡しに使用するキーです
     */
    public static final String RANGE_ORDERNO_KEY = "RANGE_ORDERNO_KEY";

    /**
     * 出庫日の開始終了フラグの受け渡しに使用するキーです
     */
    public static final String RANGE_RETRIEVALDAY_KEY = "RANGE_RETRIEVALDAY_KEY";

    /**
     * 開始終了フラグの受け渡しに使用するキーです
     */
    public static final String RANGE_KEY = "RANGE_KEY";

    /**
     * 範囲フラグ：開始
     */
    public static final String RANGE_START = "0";

    /**
     * 範囲フラグ：終了
     */
    public static final String RANGE_END = "1";

    /**
     * 出荷先コードの受け渡しに使用するキーです
     */
    public static final String CUSTOMERCODE_KEY = "CUSTOMERCODE_KEY";

    /**
     * 出荷先名称の受け渡しに使用するキーです
     */
    public static final String CUSTOMERNAME_KEY = "CUSTOMERNAME_KEY";

    /**
     * 出庫日の受け渡しに使用するキーです
     */
    public static final String RETRIEVALDAY_KEY = "RETRIEVALDAY_KEY";

    /**
     * 行No.の受け渡しに使用するキーです
     */
    public static final String LINENO_KEY = "LINENO_KEY";

    /**
     * 作業枝番の受け渡しに使用するキーです
     */
    public static final String BRANCHNO_KEY = "BRANCHNO_KEY";

    /**
     * 商品コードの受け渡しに使用するキーです
     */
    public static final String ITEMCODE_KEY = "ITEMCODE_KEY";

    /**
     * 商品名称の受け渡しに使用するキーです
     */
    public static final String ITEMNAME_KEY = "ITEMNAME_KEY";

    /**
     * ケース入数の受け渡しに使用するキーです
     */
    public static final String ENTERINGQTY_KEY = "ENTERINGQTY_KEY";

    /**
     * JANコードの受け渡しに使用するキーです
     */
    public static final String JANCODE_KEY = "JANCODE_KEY";

    /**
     * ケースITFの受け渡しに使用するキーです
     */
    public static final String CASEITF_KEY = "CASEITF_KEY";

    /**
     * エリアの受け渡しに使用するキーです
     */
    public static final String AREA_KEY = "AREA_KEY";

    /**
     * 棚の受け渡しに使用するキーです
     */
    public static final String LOCATION_KEY = "LOCATION_KEY";

    /**
     * 検索対象テーブルの受け渡しに使用するキーです
     */
    public static final String SEARCHTABLE_KEY = "SEARCHTABLE_KEY";

    /**
     * 出庫開始日の受け渡しに使用するキーです
     */
    public static final String STARTRETRIEVALDATE_KEY = "STARTRETRIEVALDATE_KEY";

    /**
     * 出庫終了日時の受け渡しに使用するキーです
     */
    public static final String ENDRETRIEVALDATE_KEY = "ENDRETRIEVALDATE_KEY";

    /**
     * 出庫開始日(day)の受け渡しに使用するキーです
     */
    public static final String STARTRETRIEVALDAY_KEY = "STARTRETRIEVALDAY_KEY";

    /**
     * 出庫開始日(time)の受け渡しに使用するキーです
     */
    public static final String STARTRETRIEVALTIME_KEY = "STARTRETRIEVALTIME_KEY";

    /**
     * 終了開始日(day)の受け渡しに使用するキーです
     */
    public static final String ENDRETRIEVALDAY_KEY = "ENDRETRIEVALDAY_KEY";

    /**
     * 終了開始日(time)の受け渡しに使用するキーです
     */
    public static final String ENDRETRIEVALTIME_KEY = "ENDRETRIEVALTIME_KEY";
    
    /**
     * 検索時刻の受け渡しに使用するキーです
     */
    public static final String SERCH_TIME = "SERCH_TIME";

    /**
     * 取込単位キーの受け渡しに使用するキーです
     */
    public static final String LOADUNIT_KEY = "LOADUNIT_KEY";

    /**
     * 集約条件の受け渡しに使用するキーです
     */
    public static final String COLLECTCONDITION_KEY = "COLLECTCONDITION_KEY";

    /**
     * 出庫エリアの受け渡しに使用するキーです
     */
    public static final String RETRIEVALAREA_KEY = "RETRIEVALAREA_KEY";

    /**
     * スケジュールフラグの受け渡しに使用するキーです
     */
    public static final String SCHEDULEFLAG_KEY = "SCHEDULEFLAG_KEY";

    /**
     * 作業状態の受け渡しに使用するキーです
     */
    public static final String STATUSFLAG_KEY = "STATUSFLAG_KEY";

    /**
     * 予定情報検索対象状態フラグの受け渡しに使用するキーです。
     */
    public static final String PLAN_STATUS_KEY = "PLAN_STATUS_KEY";

    /**
     * 設定単位キー(リスト作業No.)の受け渡しに使用するキーです
     */
    public static final String SETTINGUNIT_KEY = "SETTINGUNIT_KEY";

    /**
     * ハードウェア区分の受け渡しに使用するキー
     */
    public static final String HARDWAETYPE_KEY = "HARDWAETYPE_KEY";

    /**
     * 作業区分
     */
    public static final String JOBTYPE_KEY = "JOBTYPE_KEY";

    /**
     * 出庫日範囲チェック有無の受け渡しに使用するキーです
     */
    public static final String RETRIEVALDATE_FROM_TO_FLAG = "RETRIEVALDATE_FROM_TO_FLAG";
    
    /**
     * ステーションNo.の受け渡しに使用するキーです
     */
    public static final String STATIONNO_KEY = "STATIONNO_KEY";

    /**
     * 範囲チェックフラグ：チェック有り
     */
    public static final String FROM_TO_FLAG_TRUE = "1";

    /**
     * 範囲フラグ：チェック無し
     */
    public static final String FROM_TO_FLAG_FALSE = "0";


    // リストボックスパス定義
    /**
     * 出庫予定日検索リストボックスのパスです
     */
    public static final String LST_RETRIEVAL_PLAN_DATE = "/retrieval/listbox/plandate/LstRetrievalPlanDate.do";

    /**
     * 出庫日検索リストボックスのパスです
     */
    public static final String LST_RETRIEVAL_DATE = "/retrieval/listbox/retrievaldate/LstRetrievalDate.do";

    /**
     * 伝票No.検索リストボックスのパスです
     */
    public static final String LST_RETRIEVAL_PLAN_TICKET = "/retrieval/listbox/ticketno/LstRetrievalTicketNo.do";

    /**
     * 出庫予定検索リストボックスのパスです
     */
    public static final String LST_RETRIEVAL_PLAN = "/retrieval/listbox/plan/LstRetrievalPlanMnt.do";

    /**
     * 出庫棚検索リストボックスのパスです
     */
    public static final String LST_RETRIEVAL_LOCATE = "/retrieval/listbox/locate/LstRetrievalLocate.do";

    /**
     * バッチNo.検索リストボックスのパスです
     */
    public static final String LST_BATCH_NO = "/retrieval/listbox/batchno/LstRetrievalBatchNo.do";

    /**
     * オーダーNo.検索リストボックスのパスです
     */
    public static final String LST_ORDER_NO = "/retrieval/listbox/orderno/LstRetrievalOrderNo.do";

    /**
     */
    public static final String LST_RETRIEVAL_LOADUNITKEY = "/retrieval/listbox/loadunitkey/LstRetrievalLoadUnitKey.do";

    /**
     * 出庫予定一覧リストボックスのパスです
     */
    public static final String LST_RETRIEVAL_PLAN_INQUIRY = "/retrieval/listbox/planlist/LstRetrievalPlan.do";

    /**
     * 出庫実績一覧リストボックスのパスです
     */
    public static final String LST_RETRIEVAL_RESULT = "/retrieval/listbox/resultlist/LstRetrievalResult.do";

    /**
     * 商品検索一覧リストボックスのパスです
     */
    public static final String LST_RETRIEVAL_ITEM = "/retrieval/listbox/item/LstRetrievalItem.do";

    /**
     * 出荷先検索一覧リストボックスのパスです
     */
    public static final String LST_CUSTOMER = "/retrieval/listbox/customer/LstRetrievalCustomer.do";

    /**
     * 出庫作業一覧リストボックスのパスです
     */
    public static final String LST_RETRIEVALWORK = "/retrieval/listbox/worklist/LstRetrievalWork.do";

    /**
     * 出庫引当一覧リストボックスのパスです
     */
    public static final String LST_RETRIEVAL_ALLOCATE = "/retrieval/listbox/alloclist/LstRetrievalAllocate.do";

    /**
     * 引当パターンNo.検索リストボックスパスです。
     */
    public static final String LST_ALLOCATE_PRIORITY = "/retrieval/listbox/allocatepriority/LstAllocatePriority.do";

    /**
     * 欠品チェック一覧リストボックスのパスです
     */
    public static final String LST_SHORTAGECHECK = "/retrieval/listbox/shortage/LstShortageCheck.do";

    /**
     * リスト作業No.検索リストボックスのパスです
     */
    public static final String LST_RETRIEVAL_JOBNO = "/retrieval/listbox/jobno/LstRetrievalJobNo.do";

    /**
     * 伝票No.検索リストボックスのパスです
     */
    public static final String LST_RETRIEVAL_TICKET_NO = "/retrieval/listbox/ticketno/LstRetrievalTicketNo.do";

    /**
     * 出庫開始日時検索リストボックスのパスです
     */
    public static final String LST_RETRIEVAL_STSRTDATA = "/retrieval/listbox/startdate/LstRetrievalStartDate.do";


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
     * 本クラスは定義情報を管理するクラスなのでインスタンスの生成は行えません。
     */
    private RetrievalListBoxDefine()
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
        return "$Id: RetrievalListBoxDefine.java 3213 2009-03-02 06:59:20Z arai $";
    }
}

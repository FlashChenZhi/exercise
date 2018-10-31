package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.wms.base.common.InParameter;

/**
 * <CODE>PCTRetrievalInParameter</CODE>クラスは、pcart.retrievalパッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスではpcart.retrievalパッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>PCTRetrievalInParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 * 
 * オーダーNo.<BR>
 * 出荷先コード<BR>
 * 出荷先名称<BR>
 * 出荷先分類コード<BR>
 * 検索対象テーブル<BR>
 * 荷主コード<BR>
 * 予定日<BR>
 * バッチNo.<BR>
 * 得意先コード<BR>
 * 状態フラグ<BR>
 * 作業日<BR>
 * 作業日(TO)<BR>
 * 範囲指定フラグ<BR>
 * 曜日選択フラグ<BR>
 * 月曜検索フラグ<BR>
 * 火曜検索フラグ<BR>
 * 水曜検索フラグ<BR>
 * 木曜検索フラグ<BR>
 * 金曜検索フラグ<BR>
 * 土曜検索フラグ<BR>
 * 日曜検索フラグ<BR>
 * 予定エリア<BR>
 * ランク<BR>
 * 集約条件<BR>
 * 表示ランク<BR>
 * 処理<BR>
 * 開始終了フラグ<BR>
 * ホスト取消区分<BR>
 * 取込単位キー<BR>
 * リストセル行No.<BR>
 * 出庫予定日<BR>
 * 伝票No.<BR>
 * 行No.<BR>
 * 作業枝番<BR>
 * 商品コード<BR>
 * 商品名称<BR>
 * JANコード<BR>
 * ケースITF<BR>
 * ロットNo.<BR>
 * ケース入数<BR>
 * 予定数<BR>
 * 出庫エリアNo.<BR>
 * 出庫棚<BR>
 * 最終更新日時
 * SeqNo.
 * 最終更新日時(リスト)<BR>
 * 予定一意キー(リスト)<BR>
 * 曜日<BR>
 * Rft号機No.<BR>
 * 作業No.<BR>
 * 実績数<BR>
 * 予定一意キー<BR>
 * SeqNo.(作業メンテナンス(オーダー単位))<BR>
 * 実績No.<BR>
 * 処理フラグ<BR>
 * 遷移元情報<BR>
 * 設定単位キー<BR>
 * 箱替フラグ<BR>
 * 完了フラグ<BR>
 * 基準日<BR>
 * アイテム情報コメント<BR>
 * オーダー情報コメント<BR>
 * ゾーンNo.<BR>
 * ボール入数<BR>
 * ボールITF<BR>
 * ロット入数<BR>
 * 棚<BR>
 * バッチSEQNo.<BR>
 * 予定オーダーNo.<BR>
 * 通番<BR>
 * オーダー内商品数<BR>
 * オーダー通番<BR>
 * オーダー通番合計<BR>
 * 汎用フラグ<BR>
 * シュートNo.<BR>
 * 作業ゾーン<BR>
 * 開始棚<BR>
 * 終了棚<BR>
 * ユーザID(FROM)<BR>
 * ユーザID(TO)<BR>
 * 開始作業月<BR>
 * 終了作業月<BR>
 * 行数<BR>
 * 実績オーダーNo.<BR>
 * 完了処理区分<BR>
 * 作業No.リスト<BR>
 * レベルA<BR>
 * レベルB<BR>
 * レベルC<BR>
 * </DIR>
 *
 * Designer : K.Bingo <BR>
 * Maker : K.Bingo <BR>
 *
 * @version $Revision: 4055 $, $Date: 2009-04-09 19:46:24 +0900 (木, 09 4 2009) $
 * @author  $Author: okayama $
 */
public class PCTRetrievalInParameter
        extends InParameter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    /**
     * 検索対象テーブル : ユーザ実績
     */
    public static final String SEARCH_TABLE_USERRESULT = "16";

    /**
     * 検索対象テーブル : ピッキング実績
     */
    public static final String SEARCH_TABLE_PICRESULT = "18";

    /**
     * 検索対象テーブル : 月別集計実績
     */
    public static final String SEARCH_TABLE_MONTHLYRESULT = "19";

    /**
     * ランク：全て
     */
    public static final String RANK_ALL = "1";

    /**
     * ランク：Aランク
     */
    public static final String RANK_A = "2";

    /**
     * ランク：Bランク
     */
    public static final String RANK_B = "3";

    /**
     * ランク：Cランク
     */
    public static final String RANK_C = "4";

    /**
     * 集約条件 : 作業者別
     */
    public static final String COLLECT_CONDITION_WORKER = "1";

    /**
     * 集約条件：作業日別
     */
    public static final String COLLECT_CONDITION_WORKDATE = "2";

    /**
     * 集約単位：荷主別
     */
    public static final String COLLECT_CONDITION_CONSIGNOR = "3";

    /**
     * 集約単位：エリア
     */
    public static final String COLLECT_CONDITION_AREA = "4";

    /**
     * 集約単位：バッチ
     */
    public static final String COLLECT_CONDITION_BATCH = "5";

    /**
     * 表示ランク：ロット
     */
    public static final String DISPLAY_RANK_LOT = "0";

    /**
     * 表示ランク：オーダー
     */
    public static final String DISPLAY_RANK_ORDER = "1";

    /**
     * 表示ランク：行
     */
    public static final String DISPLAY_RANK_LINE = "2";

    /**
     * 処理フラグ：全完了
     */
    public static final String PROCESSING_DIVISION_COMPLETION = "1";

    /**
     * 処理フラグ：全欠品完了
     */
    public static final String PROCESSING_DIVISION_STOCKOUT = "2";

    /**
     * 処理フラグ：削除
     */
    public static final String PROCESSING_DIVISION_DELETE = "3";

    /**
     * 状態フラグ：未作業
     */
    public static final String STATUS_FLAG_UNWORK = "0";

    /**
     * 状態フラグ：作業中
     */
    public static final String STATUS_FLAG_EXIST_WORKING = "1";

    /**
     * 状態フラグ：作業済
     */
    public static final String STATUS_FLAG_EXIST_WORKED = "2";

    /**
     * 状態フラグ：完了
     */
    public static final String STATUS_FLAG_COMPLETION = "4";

    /**
     * 状態フラグ：メンテ完了
     */
    public static final String STATUS_FLAG_MAINTENANCE_COMPLETION = "5";

    /**
     * 状態フラグ：重量未登録
     */
    public static final String STATUS_FLAG_WEIGHT_UNREGIST = "6";

    /**
     * 状態フラグ：欠品完了
     */
    public static final String STATUS_FLAG_SHORTAGE_COMPLETION = "7";
    
    /**
     * 状態フラグ：削除
     */
    public static final String STATUS_FLAG_DELETE = "9";

    /**
     * バッチ状態フラグ：未開始
     */
    public static final String BATCHSTATUS_FLAG_UNSTART = "0";

    /**
     * バッチ状態フラグ：作業中
     */
    public static final String BATCHSTATUS_FLAG_WORKING = "1";

    /**
     * バッチ状態フラグ：一部作業中
     */
    public static final String BATCHSTATUS_FLAG_PART_WORKED = "2";

    /**
     * バッチ状態フラグ：完了
     */
    public static final String BATCHSTATUS_FLAG_COMPLETION = "4";

    /**
     * バッチ状態フラグ：メンテ完了
     */
    public static final String BATCHSTATUS_FLAG_MAINTENANCE_COMPLETION = "5";

    /**
     * スケジュール処理フラグ：未スケジュール
     */
    public static final String SCH_FLAG_UNDO = "0";

    /**
     * スケジュール処理フラグ：スケジュール済
     */
    public static final String SCH_FLAG_COMPLETION = "1";

    /**
     * 実績区分 : 運用
     */
    public static final String RESULT_TYPE_OPERATION = "1";

    /**
     * 実績区分 : 休憩
     */
    public static final String RESULT_TYPE_BREAK = "2";

    /**
     * 実績区分 : バッテリ交換
     */
    public static final String RESULT_TYPE_BATTERY = "3";

    /**
     * 曜日 : 日曜
     */
    public static final String SUNDAY = "1";

    /**
     * 曜日 : 月曜
     */
    public static final String MONDAY = "2";

    /**
     * 曜日 : 火曜
     */
    public static final String TUESDAY = "3";

    /**
     * 曜日 : 水曜
     */
    public static final String WEDNESDAY = "4";

    /**
     * 曜日 : 木曜
     */
    public static final String THURSDAY = "5";

    /**
     * 曜日 : 金曜
     */
    public static final String FRIDAY = "6";

    /**
     * 曜日 : 土曜
     */
    public static final String SATURDAY = "7";

    /**
     * 曜日「0」
     */
    public static final int NO_DAY = 0;


    /**
     * 登録区分 : ファイル取込
     */
    public static final String REGIST_KIND_DATALOADER = "0";

    /**
     * 登録区分 : 端末登録
     */
    public static final String REGIST_KIND_TERMINAL_REGIST = "1";

    /**
     * チェックディジット有無フラグ : チェックディジット有り
     */
    public static final String CHECK_DIGIT_ON = "0";

    /**
     * チェックディジット有無フラグ : チェックディジット無し
     */
    public static final String CHECK_DIGIT_OFF = "1";

    /**
     * 処理画面区分 : 作業メンテナンス（オーダー）
     */
    public static final String MNT_PROCESS_KIND_ORDER = "0";

    /**
     * 処理画面区分 : 作業メンテナンス（商品）
     */
    public static final String MNT_PROCESS_KIND_ITEM = "1";

    /**
     * 完了区分 : Pカート
     */
    public static final String COMPLETE_KIND_PCART = "0";

    /**
     * 完了区分 : Web
     */
    public static final String COMPLETE_KIND_WEB = "1";

    /**
     * 実績報告区分 : 未報告
     */
    public static final String REPORT_FLAG_UNDO = "0";

    /**
     * 実績報告区分 : 報告済
     */
    public static final String REPORT_FLAG_COMPLETION = "1";

    /**
     * 完了処理区分 : 作業メンテナンス
     */
    public static final String WORK_FLAG_MNT = "1";

    /**
     * 完了処理区分：作業メンテナンス
     */
    public static final String WORK_FLAG_MNT_COLLECT = "2";

    /**
     * CSV処理フラグ : CSV
     */
    public static final String PRINT_CSV = "1";

    /**
     * 表示順：商品コード
     */
    public static final String ORDERING_ITEM_CODE = "0";

    /**
     * 表示順：オーダーNo.
     */
    public static final String ORDERING_ORDER_NO = "1";

    /**
     * レベル：レベルA
     */
    public static final String LEVEL_A = "0";

    /**
     * レベル：レベルB
     */
    public static final String LEVEL_B = "1";

    /**
     * レベル：レベルC
     */
    public static final String LEVEL_C = "2";


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    /**
     * オーダーNo.
     */
    private String _orderNo;

    /**
     * 出荷先コード
     */
    private String _customerCode;

    /**
     * 出荷先コード(to)
     */
    private String _tocustomerCode;

    /**
     * 出荷先名称
     */
    private String _customerName;

    /**
     * 出荷先分類コード
     */
    private String _customerCategory;

    /** 
     * 検索対象テーブル
     */
    private String _searchTable;

    /**
     * 荷主コード
     */
    private String _consignorCode;

    /**
     * 荷主名称
     */
    private String _consignorName;

    /**
     * 予定日
     */
    private String _planDay;

    /**
     * バッチNo.
     */
    private String _batchNo;

    /**
     * 得意先コード
     */
    private String _regularCustomerCode;

    /**
     * 得意先コード
     */
    private String _regularCustomerName;

    /**
     * 状態フラグ1
     */
    private String _statusFlag1;

    /**
     * 状態フラグ2
     */
    private String _statusFlag2;

    /**
     * 作業日
     */
    private String _workDay;

    /**
     * 作業日(TO)
     */
    private String _toWorkDay;

    /**
     * 範囲指定フラグ
     */
    private boolean _rangeFlag;

    /**
     * 曜日選択フラグ
     */
    private boolean _useDayOfWeekFlag;

    /**
     * 月曜検索フラグ
     */
    private boolean _mondayFlag;

    /**
     * 火曜検索フラグ
     */
    private boolean _tuesdayFlag;

    /**
     * 水曜検索フラグ
     */
    private boolean _wednesdayFlag;

    /**
     * 木曜検索フラグ
     */
    private boolean _thursdayFlag;

    /**
     * 金曜検索フラグ
     */
    private boolean _fridayFlag;

    /**
     * 土曜検索フラグ
     */
    private boolean _saturdayFlag;

    /**
     * 日曜検索フラグ
     */
    private boolean _sundayFlag;

    /**
     * 予定エリア
     */
    private String _planAreaNo;

    /**
     * エリア名称
     */
    private String _areaName;

    /**
     * ランク
     */
    private String _rank;

    /**
     * 集約条件
     */
    private String _collectCondition;

    /**
     * 表示ランク
     */
    private String _displayRank;

    /**
     * 処理
     */
    private String _processingDivision;

    /**
     * 開始完了フラグ
     */
    private String _fromtoFrag;

    /**
     * 取込単位キー
     */
    private String _loadUnitKey;

    /**
     * 出庫予定日
     */
    private String _retrievalPlanDay;

    /**
     * リストセル行No.
     */
    private int _rowNo;

    /**
     * 伝票No.
     */
    private String _ticketNo;

    /**
     * 行No.
     */
    private int _lineNo;

    /**
     * 作業枝番
     */
    private int _branchNo;

    /**
     * 商品コード
     */
    private String _itemCode;

    /**
     * 商品コード
     */
    private String _itemName;

    /**
     * JANコード
     */
    private String _janCode;

    /**
     * ケースITF
     */
    private String _itf;

    /**
     * ロットNo.
     */
    private String _lotNo;

    /**
     * ケース入数
     */
    private int _enteringQty;

    /**
     * 予定数
     */
    private int _planQty;

    /**
     * 取込開始日時
     */
    private java.util.Date _loadStartDate;

    /**
     * ホスト取消区分
     */
    private String _cancelFlag;


    /**
     * 最終更新日時
     */
    private java.util.Date _lastUpdateDate;

    /**
     * SeqNo.
     */
    private int _seqNo;

    /**
     * バッチ状態フラグ
     */
    private String _batchStatusFlag;

    /**
     * 最終更新日時(リスト)
     */
    private ArrayList<Date> _lastUpdateDateList;

    /**
     * 予定一意キー(リスト)
     */
    private ArrayList<String> _ukeyList;


    /**
     * エリアNo.
     */
    private String _areaNo;

    /**
     * 曜日
     */
    private int _dayOfWeek;

    /**
     * Rft号機No.
     */
    private String _rftNo;

    /**
     * 作業No.
     */
    private String _jobNo;

    /**
     * 実績数
     */
    private int _resultQty;

    /**
     * 予定一意キー
     */
    private String _planUkey;

    /**
     * SeqNo.(作業メンテナンス(オーダー単位))
     */
    private String _workMntSeqNo;

    /**
     * 処理フラグ
     */
    private String _processFlag;

    /**
     * 終了予測時間基準フラグ
     */
    private String _endPlanTimeStandardFlag;

    /**
     * 遷移元情報
     */
    private String _transition;

    /**
     * 設定単位キー
     */
    private String _settingUnitKey;

    /**
     * 箱替フラグ
     */
    private boolean _substitutes;

    /**
     * 完了フラグ
     */
    private String _completionFlag;

    /**
     * 実績ケース数
     */
    private int _resultCaseQty;

    /**
     * 基準日
     */
    private String _useByDate;

    /**
     * アイテム情報コメント
     */
    private String _itemInfo;

    /**
     * オーダー情報コメント
     */
    private String _orderInfo;

    /**
     * ゾーンNo.
     */
    private String _zoneNo;

    /**
     * ボール入数
     */
    private int _bundleEnteringQty;

    /**
     * ボールITF
     */
    private String _bundleItf;

    /**
     * ロット入数
     */
    private int _lotEnteringQty;

    /**
     * 棚
     */
    private String _location;

    /**
     * バッチ予定SeqNo.
     */
    private String _batchSeqNo;

    /**
     * 予定オーダーNo.
     */
    private String _planOrderNo;

    /**
     * 通番
     */
    private int _throughNo;

    /**
     * オーダー内商品数
     */
    private int _orderItemQty;

    /**
     * オーダー通番
     */
    private int _orderThroughNo;

    /**
     * オーダー通番合計
     */
    private int _orderThroughNoCnt;

    /**
     * 汎用フラグ
     */
    private String _generalFlag;

    /**
     * シュートNo.
     */
    private String _shootNo;

    /**
     * 作業ゾーン
     */
    private String _workZoneNo;

    /**
     * 開始棚
     */
    private String _startLocationNo;

    /**
     * 終了棚
     */
    private String _endLocationNo;

    /**
     * ユーザID(FROM)
     */
    private String _userIdFrom;

    /**
     * ユーザID(TO)
     */
    private String _userIdTo;

    /**
     * 開始作業月
     */
    private String _startWorkManth;

    /**
     * 終了作業月
     */
    private String _endWorkManth;

    /**
     * 行数
     */
    private int _lineCnt;

    /**
     * 実績オーダーNo.
     */
    private String _resultOrderNo;

    /**
     * 完了処理区分
     */
    private String _workFlag;

    /**
     * CSV処理フラグ
     */
    private String _printCsv;

    /**
     * 作業No.リスト
     */
    private List<String> _jobNoList;

    /**
     * レベルA
     */
    private boolean _levelA;

    /**
     * レベルB
     */
    private boolean _levelB;

    /**
     * レベルC
     */
    private boolean _levelC;

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PCTRetrievalInParameter.java 4055 2009-04-09 10:46:24Z okayama $";
    }

    /**
     * オーダーNoを設定します。
     * @param orderNo オーダーNo
     */
    public void setOrderNo(String orderNo)
    {
        _orderNo = orderNo;
    }

    /**
     * オーダーNoを返します。
     * @return オーダーNo.
     */
    public String getOrderNo()
    {
        return _orderNo;
    }

    /**
     * 出荷先コードを設定します。
     * @param customerCode 出荷先コード
     */
    public void setCustomerCode(String customerCode)
    {
        _customerCode = customerCode;
    }

    /**
     * 出荷先コードを返します。
     * @return 出荷先コードを返します。
     */
    public String getCustomerCode()
    {
        return _customerCode;
    }

    /**
     * 出荷先コード(to)を設定します。
     * @param tocustomerCode 出荷先コード(to)
     */
    public void setToCustomerCode(String tocustomerCode)
    {
        _tocustomerCode = tocustomerCode;
    }

    /**
     * 出荷先コードを返します。
     * @return 出荷先コードを返します。
     */
    public String getToCustomerCode()
    {
        return _tocustomerCode;
    }

    /**
     * 出荷先名称を設定します。
     * @param customerName 出荷先名称
     */
    public void setCustomerName(String customerName)
    {
        _customerName = customerName;
    }

    /**
     * 出荷先名称を返します。
     * @return 出荷先名称を返します。
     */
    public String getCustomerName()
    {
        return _customerName;
    }

    /**
     * 出荷先分類を設定します。
     * @param customerCategory 出荷先分類
     */
    public void setCustomerCategory(String customerCategory)
    {
        _customerCategory = customerCategory;
    }

    /**
     * 出荷先分類を返します。
     * @return 出荷先分類を返します。
     */
    public String getCustomerCategory()
    {
        return _customerCategory;
    }

    /**
     * 検索対象テーブルを設定します。
     * @param searchTable 検索対象テーブル
     */
    public void setSearchTable(String searchTable)
    {
        _searchTable = searchTable;
    }

    /**
     * 検索対象テーブルを返します。
     * @return 検索対象テーブルを返します。
     */
    public String getSearchTable()
    {
        return _searchTable;
    }

    /**
     * 荷主コードを設定します。
     * @param consignorCode 荷主コード
     */
    public void setConsignorCode(String consignorCode)
    {
        _consignorCode = consignorCode;
    }

    /**
     * 荷主コードを返します。
     * @return 荷主コードを返します。
     */
    public String getConsignorCode()
    {
        return _consignorCode;
    }

    /**
     * 荷主名称を設定します。
     * @param consignorName 荷主名称
     */
    public void setConsignorName(String consignorName)
    {
        _consignorName = consignorName;
    }

    /**
     * 荷主名称を返します。
     * @return 荷主名称を返します。
     */
    public String getConsignorName()
    {
        return _consignorName;
    }

    /**
     * 予定日を設定します。
     * @param planDay 予定日
     */
    public void setPlanDay(String planDay)
    {
        _planDay = planDay;
    }

    /**
     * 予定日を返します。
     * @return 予定日を返します。
     */
    public String getPlanDay()
    {
        return _planDay;
    }

    /**
     * バッチNo.を設定します。
     * @param batchNo バッチNo.
     */
    public void setBatchNo(String batchNo)
    {
        _batchNo = batchNo;
    }

    /**
     * バッチNo.を返します。
     * @return バッチNo.を返します。
     */
    public String getBatchNo()
    {
        return _batchNo;
    }

    /**
     * 得意先コードを設定します。
     * @param regularCustomerCode 得意先コード
     */
    public void setRegularCustomerCode(String regularCustomerCode)
    {
        _regularCustomerCode = regularCustomerCode;
    }

    /**
     * 得意先コードを返します。
     * @return 得意先コードを返します。
     */
    public String getRegularCustomerCode()
    {
        return _regularCustomerCode;
    }

    /**
     * 得意先名称を設定します。
     * @param regularCustomerName 得意先名称
     */
    public void setRegularCustomerName(String regularCustomerName)
    {
        _regularCustomerName = regularCustomerName;
    }

    /**
     * 得意先名称を返します。
     * @return 得意先名称を返します。
     */
    public String getRegularCustomerName()
    {
        return _regularCustomerName;
    }

    /**
     * 状態フラグ１を返します。
     * @return 状態フラグ１を返します。
     */
    public String getStatusFlag1()
    {
        return _statusFlag1;
    }


    /**
     * 状態フラグ１を設定します。
     * @param statusFlag1 状態フラグ
     */
    public void setStatusFlag1(String statusFlag1)
    {
        _statusFlag1 = statusFlag1;
    }

    /**
     * 状態フラグ２を返します。
     * @return 状態フラグ２を返します。
     */
    public String getStatusFlag2()
    {
        return _statusFlag2;
    }


    /**
     * 状態フラグ２を設定します。
     * @param statusFlag2 状態フラグ
     */
    public void setStatusFlag2(String statusFlag2)
    {
        _statusFlag2 = statusFlag2;
    }

    /**
     * 作業日を返します。
     * @return 作業日を返します。
     */
    public String getWorkDay()
    {
        return _workDay;
    }

    /**
     * 作業日を設定します。
     * @param workDay 作業日
     */
    public void setWorkDay(String workDay)
    {
        _workDay = workDay;
    }

    /**
     * 作業日(TO)を返します。
     * @return 作業日(TO)を返します。
     */
    public String getToWorkDay()
    {
        return _toWorkDay;
    }

    /**
     * 作業日(TO)を設定します。
     * @param toWorkDay 作業日(TO)
     */
    public void setToWorkDay(String toWorkDay)
    {
        _toWorkDay = toWorkDay;
    }

    /**
     * 範囲指定フラグを返します。
     * @return 範囲指定フラグを返します。
     */
    public boolean isRangeFlag()
    {
        return _rangeFlag;
    }

    /**
     * 範囲指定フラグを設定します。
     * @param rangeFlag 範囲指定フラグ
     */
    public void setRangeFlag(boolean rangeFlag)
    {
        _rangeFlag = rangeFlag;
    }

    /**
     * 曜日選択フラグを返します。
     * @return 曜日選択フラグを返します。
     */
    public boolean isUseDayOfWeekFlag()
    {
        return _useDayOfWeekFlag;
    }

    /**
     * 曜日選択フラグを設定します。
     * @param useDayOfWeekFlag 曜日選択フラグ
     */
    public void setUseDayOfWeekFlag(boolean useDayOfWeekFlag)
    {
        _useDayOfWeekFlag = useDayOfWeekFlag;
    }

    /**
     * 月曜検索フラグを返します。
     * @return 月曜検索フラグを返します。
     */
    public boolean isMondayFlag()
    {
        return _mondayFlag;
    }

    /**
     * 月曜検索フラグを設定します。
     * @param mondayFlag 月曜検索フラグ
     */
    public void setMondayFlag(boolean mondayFlag)
    {
        _mondayFlag = mondayFlag;
    }

    /**
     * 火曜検索フラグを返します。
     * @return 火曜検索フラグを返します。
     */
    public boolean isTuesdayFlag()
    {
        return _tuesdayFlag;
    }

    /**
     * 火曜検索フラグを設定します。
     * @param tuesdayFlag 火曜検索フラグ
     */
    public void setTuesdayFlag(boolean tuesdayFlag)
    {
        _tuesdayFlag = tuesdayFlag;
    }

    /**
     * 水曜検索フラグを返します。
     * @return 水曜検索フラグを返します。
     */
    public boolean isWednesdayFlag()
    {
        return _wednesdayFlag;
    }

    /**
     * 水曜検索フラグを設定します。
     * @param wednesdayFlag 水曜検索フラグ
     */
    public void setWednesdayFlag(boolean wednesdayFlag)
    {
        _wednesdayFlag = wednesdayFlag;
    }

    /**
     * 木曜検索フラグを返します。
     * @return 木曜検索フラグを返します。
     */
    public boolean isThursdayFlag()
    {
        return _thursdayFlag;
    }

    /**
     * 木曜検索フラグを設定します。
     * @param thursdayFlag 木曜検索フラグ
     */
    public void setThursdayFlag(boolean thursdayFlag)
    {
        _thursdayFlag = thursdayFlag;
    }

    /**
     * 金曜検索フラグを返します。
     * @return 金曜検索フラグを返します。
     */
    public boolean isFridayFlag()
    {
        return _fridayFlag;
    }

    /**
     * 金曜検索フラグを設定します。
     * @param fridayFlag 金曜検索フラグ
     */
    public void setFridayFlag(boolean fridayFlag)
    {
        _fridayFlag = fridayFlag;
    }

    /**
     * 土曜検索フラグを返します。
     * @return 土曜検索フラグを返します。
     */
    public boolean isSaturdayFlag()
    {
        return _saturdayFlag;
    }

    /**
     * 土曜検索フラグを設定します。
     * @param saturdayFlag 土曜検索フラグ
     */
    public void setSaturdayFlag(boolean saturdayFlag)
    {
        _saturdayFlag = saturdayFlag;
    }

    /**
     * 日曜検索フラグを返します。
     * @return 日曜検索フラグを返します。
     */
    public boolean isSundayFlag()
    {
        return _sundayFlag;
    }

    /**
     * 日曜検索フラグを設定します。
     * @param sundayFlag 日曜検索フラグ
     */
    public void setSundayFlag(boolean sundayFlag)
    {
        _sundayFlag = sundayFlag;
    }

    /**
     * 集約単位を返します。
     * @return 集約単位を返します。
     */
    public String getCollectCondition()
    {
        return _collectCondition;
    }

    /**
     * 集約単位を設定します。
     * @param collectCondition 集約単位
     */
    public void setCollectCondition(String collectCondition)
    {
        _collectCondition = collectCondition;
    }

    /**
     * 表示ランクを返します。
     * @return 表示ランクを返します。
     */
    public String getDisplayRank()
    {
        return _displayRank;
    }

    /**
     * 表示ランクを設定します。
     * @param displayRank 表示ランク
     */
    public void setDisplayRank(String displayRank)
    {
        _displayRank = displayRank;
    }

    /**
     * 予定エリアを返します。
     * @return 予定エリアNo.を返します。
     */
    public String getPlanAreaNo()
    {
        return _planAreaNo;
    }

    /**
     * 予定エリアを設定します。
     * @param planAreaNo 予定エリアNo.
     */
    public void setPlanAreaNo(String planAreaNo)
    {
        _planAreaNo = planAreaNo;
    }

    /**
     * 予定エリアを返します。
     * @return 予定エリアNo.を返します。
     */
    public String getAreaName()
    {
        return _areaName;
    }

    /**
     * 予定エリアを設定します。
     * @param areaName 予定エリアNo.
     */
    public void setAreaName(String areaName)
    {
        _areaName = areaName;
    }

    /**
     * ランクを返します。
     * @return ランクを返します。
     */
    public String getRank()
    {
        return _rank;
    }

    /**
     * ランクを設定します。
     * @param rank ランク
     */
    public void setRank(String rank)
    {
        _rank = rank;
    }

    /**
     * 処理を返します。
     * @return 処理を返します。
     */
    public String getProcessingDivision()
    {
        return _processingDivision;
    }

    /**
     * 処理を設定します。
     * @param processingDivision 処理
     */
    public void setProcessingDivision(String processingDivision)
    {
        _processingDivision = processingDivision;
    }


    /**
     * 開始終了フラグを返します。
     * @return 開始終了フラグ
     */
    public String getFromToFrag()
    {
        return _fromtoFrag;
    }

    /**
     * 開始終了フラグを設定します。
     * @param fromtoFlag 開始終了フラグ
     */
    public void setFromToFlag(String fromtoFlag)
    {
        _fromtoFrag = fromtoFlag;
    }

    /**
     * 取込単位キーを返します。
     * @return 取込単位キーを返します。
     */
    public String getLoadUnitKey()
    {
        return _loadUnitKey;
    }

    /**
     * 取込単位キーを設定します。
     * @param loadUnitKey 取込単位キー
     */
    public void setLoadUnitKey(String loadUnitKey)
    {
        _loadUnitKey = loadUnitKey;
    }

    /**
     * 取込開始日時を返します。
     * @return 取込開始日時を返します。
     */
    public java.util.Date getLoadStartDate()
    {
        return _loadStartDate;
    }

    /**
     * 取込開始日時を設定します。
     * @param loadStartDate 取込開始日時
     */
    public void setLoadStartDate(java.util.Date loadStartDate)
    {
        _loadStartDate = loadStartDate;
    }


    /**
     * ホスト取消区分を返します。
     * @return ホスト取消区分を返します。
     */
    public String getCancelFlag()
    {
        return _cancelFlag;
    }

    /**
     * ホスト取消区分を設定します。
     * @param cancelFlag ホスト取消区分
     */
    public void setCancelFlag(String cancelFlag)
    {
        _cancelFlag = cancelFlag;
    }

    /**
     * 出庫予定日を返します。
     * @return 出庫予定日を返します。
     */
    public String getRetrievalPlanDay()
    {
        return _retrievalPlanDay;
    }

    /**
     * 出庫予定日を設定します。
     * @param retrievalPlanDay 出庫予定日
     */
    public void setRetrievalPlanDay(String retrievalPlanDay)
    {
        _retrievalPlanDay = retrievalPlanDay;
    }

    /**
     * リストセル行No.を返します。
     * @return リストセル行No.を返します。
     */
    public int getRowNo()
    {
        return _rowNo;
    }

    /**
     * リストセル行No.を設定します。
     * @param rowNo リストセル行No.
     */
    public void setRowNo(int rowNo)
    {
        _rowNo = rowNo;
    }

    /**
     * 伝票No.を返します。
     * @return 伝票No.を返します。
     */
    public String getTicketNo()
    {
        return _ticketNo;
    }

    /**
     * 伝票No.を設定します。
     * @param ticketNo 伝票No.
     */
    public void setTicketNo(String ticketNo)
    {
        _ticketNo = ticketNo;
    }

    /**
     * 行No.を返します。
     * @return 行No.を返します。
     */
    public int getLineNo()
    {
        return _lineNo;
    }

    /**
     * 行No.を設定します。
     * @param lineNo 行No.
     */
    public void setLineNo(int lineNo)
    {
        _lineNo = lineNo;
    }

    /**
     * 作業枝番を返します。
     * @return 作業枝番を返します。
     */
    public int getBranchNo()
    {
        return _branchNo;
    }

    /**
     * 作業枝番を設定します。
     * @param branchNo 作業枝番
     */
    public void setBranchNo(int branchNo)
    {
        _branchNo = branchNo;
    }

    /**
     * 商品コードを返します。
     * @return 商品コードを返します。
     */
    public String getItemCode()
    {
        return _itemCode;
    }

    /**
     * 商品コードを設定します。
     * @param itemCode 商品コード
     */
    public void setItemCode(String itemCode)
    {
        _itemCode = itemCode;
    }

    /**
     * 商品名称を返します。
     * @return 商品名称を返します。
     */
    public String getItemName()
    {
        return _itemName;
    }

    /**
     * 商品名称を設定します。
     * @param itemName 商品名称
     */
    public void setItemName(String itemName)
    {
        _itemName = itemName;
    }

    /**
     * JANコードを返します。
     * @return JANコードを返します。
     */
    public String getJanCode()
    {
        return _janCode;
    }

    /**
     * JANコードを設定します。
     * @param janCode JANコード
     */
    public void setJanCode(String janCode)
    {
        _janCode = janCode;
    }

    /**
     * ケースITFを返します。
     * @return ケースITFを返します。
     */
    public String getItf()
    {
        return _itf;
    }

    /**
     * ケースITFを設定します。
     * @param itf ケースITF
     */
    public void setItf(String itf)
    {
        _itf = itf;
    }

    /**
     * ロットNo.を返します。
     * @return ロットNo.を返します。
     */
    public String getLotNo()
    {
        return _lotNo;
    }

    /**
     * ロットNo.を設定します。
     * @param lotNo ロットNo.
     */
    public void setLotNo(String lotNo)
    {
        _lotNo = lotNo;
    }

    /**
     * ケース入数を返します。
     * @return ケース入数を返します。
     */
    public int getEnteringQty()
    {
        return _enteringQty;
    }

    /**
     * ケース入数を設定します。
     * @param enteringQty ケース入数
     */
    public void setEnteringQty(int enteringQty)
    {
        _enteringQty = enteringQty;
    }

    /**
     * 予定数を返します。
     * @return 予定数を返します。
     */
    public int getPlanQty()
    {
        return _planQty;
    }

    /**
     * 予定数を設定します。
     * @param planQty 予定数
     */
    public void setPlanQty(int planQty)
    {
        _planQty = planQty;
    }

    /**
     * 最終更新日時を返します。
     * @return 最終更新日時を返します。
     */
    public java.util.Date getLastUpdateDate()
    {
        return _lastUpdateDate;
    }

    /**
     * 最終更新日時を設定します。
     * @param lastUpdateDate  最終更新日時
     */
    public void setLastUpdateDate(java.util.Date lastUpdateDate)
    {
        _lastUpdateDate = lastUpdateDate;
    }

    /**
     * SeqNo.を返します。
     * @return SeqNo.を返します。
     */
    public int getSeqNo()
    {
        return _seqNo;
    }

    /**
     * SeqNo.を設定します。
     * @param seqNo  SeqNo.
     */
    public void setSeqNo(int seqNo)
    {
        _seqNo = seqNo;
    }

    /**
     * バッチ状態フラグを返します。
     * @return バッチ状態フラグを返します。
     */
    public String getBatchStatusFlag()
    {
        return _batchStatusFlag;
    }

    /**
     * バッチ状態フラグを設定します。
     * @param batchStatusFlag  バッチ状態フラグ
     */
    public void setBatchStatusFlag(String batchStatusFlag)
    {
        _batchStatusFlag = batchStatusFlag;
    }

    /**
     * 最終更新日時(リスト)を返します。
     * @return 最終更新日時(リスト)を返します。
     */
    public ArrayList<Date> getLastUpdateDateList()
    {
        return _lastUpdateDateList;
    }

    /**
     * 最終更新日時(リスト)を設定します。
     * @param lastUpdateDateList  最終更新日時(リスト)
     */
    public void setLastUpdateDateList(ArrayList<Date> lastUpdateDateList)
    {
        _lastUpdateDateList = lastUpdateDateList;
    }

    /**
     * 予定一意キー(リスト)を返します。
     * @return 予定一意キー(リスト)を返します。
     */
    public ArrayList<String> getUkeyList()
    {
        return _ukeyList;
    }

    /**
     * 予定一意キー(リスト)を設定します。
     * @param ukeyList  予定一意キー(リスト)
     */
    public void setUkeyList(ArrayList<String> ukeyList)
    {
        _ukeyList = ukeyList;
    }

    /**
     * エリアNo.を返します。
     * @return エリアNo.を返します。
     */
    public String getAreaNo()
    {
        return _areaNo;
    }

    /**
     * エリアNo.を設定します。
     * @param areaNo エリアNo.
     */
    public void setAreaNo(String areaNo)
    {
        _areaNo = areaNo;
    }


    /**
     * 曜日を返します。
     * @return DayOfWeekを返します。
     */
    public int getDayOfWeek()
    {
        return _dayOfWeek;
    }

    /**
     * 曜日を設定します。
     * @param dayOfWeek 曜日.
     */
    public void setDayOfWeek(int dayOfWeek)
    {
        _dayOfWeek = dayOfWeek;
    }

    /**
     * Rft号機No.を返します。
     * @return Rft号機No.を返します。
     */
    public String getRftNo()
    {
        return _rftNo;
    }

    /**
     * Rft号機No.を設定します。
     * @param rftNo Rft号機No.
     */
    public void setRftNo(String rftNo)
    {
        _rftNo = rftNo;
    }

    /**
     * 作業No.を返します。
     * @return 作業No.を返します。
     */
    public String getJobNo()
    {
        return _jobNo;
    }

    /**
     * 作業No.を設定します。
     * @param jobNo 作業No.
     */
    public void setJobNo(String jobNo)
    {
        _jobNo = jobNo;
    }

    /**
     * 実績数を返します。
     * @return 実績数を返します。
     */
    public int getResultQty()
    {
        return _resultQty;
    }

    /**
     * 実績数を設定します。
     * @param resultQty 実績数
     */
    public void setResultQty(int resultQty)
    {
        _resultQty = resultQty;
    }

    /**
     * 予定一意キーを返します。
     * @return 予定一意キーを返します。
     */
    public String getPlanUkey()
    {
        return _planUkey;
    }

    /**
     * 予定一意キーを設定します。
     * @param planUkey  予定一意キー
     */
    public void setPlanUkey(String planUkey)
    {
        _planUkey = planUkey;
    }

    /**
     * SeqNo.(作業メンテナンス(オーダー単位))を返します。
     * @return SeqNo.(作業メンテナンス(オーダー単位))を返します。
     */
    public String getWorkMntSeqNo()
    {
        return _workMntSeqNo;
    }

    /**
     * SeqNo.(作業メンテナンス(オーダー単位))を設定します。
     * @param workMntSeqNo  SeqNo.(作業メンテナンス(オーダー単位))
     */
    public void setWorkMntSeqNo(String workMntSeqNo)
    {
        _workMntSeqNo = workMntSeqNo;
    }

    /**
     * 処理フラグを返します。
     * @return 処理フラグを返します。
     */
    public String getProcessFlag()
    {
        return _processFlag;
    }

    /**
     * 処理フラグを設定します。
     * @param processFlag 処理フラグ
     */
    public void setProcessFlag(String processFlag)
    {
        _processFlag = processFlag;
    }

    /**
     * 終了予測時間基準フラグを返します。
     * @return 終了予測時間基準フラグ
     */
    public String getEndPlanTimeStandardFlag()
    {
        return _endPlanTimeStandardFlag;
    }

    /**
     * 終了予測時間基準フラグを設定します。
     * @param endPlanTimeStandardFlag 終了予測時間基準フラグ
     */
    public void setEndPlanTimeStandardFlag(String endPlanTimeStandardFlag)
    {
        _endPlanTimeStandardFlag = endPlanTimeStandardFlag;
    }

    /**
     * 遷移元情報を返します。
     * @return 遷移元情報を返します。
     */
    public String getTransition()
    {
        return _transition;
    }

    /**
     * 遷移元情報を設定します。
     * @param transition 遷移元情報
     */
    public void setTransition(String transition)
    {
        _transition = transition;
    }

    /**
     * 設定単位キーを返します。
     * @return 設定単位キーを返します。
     */
    public String getSettingUnitKey()
    {
        return _settingUnitKey;
    }

    /**
     * 設定単位キーを設定します。
     * @param settingUnitKey 設定単位キー
     */
    public void setSettingUnitKey(String settingUnitKey)
    {
        _settingUnitKey = settingUnitKey;
    }

    /**
     * 箱替フラグを返します。
     * @return 日曜検索フラグを返します。
     */
    public boolean isSubstitutes()
    {
        return _substitutes;
    }

    /**
     * 箱替フラグを設定します。
     * @param substitutes 日曜検索フラグ
     */
    public void setSubstitutes(boolean substitutes)
    {
        _substitutes = substitutes;
    }

    /**
     * 完了フラグを返します。
     * @return 完了フラグを返します。
     */
    public String getCompletionFlag()
    {
        return _completionFlag;
    }

    /**
     * 完了フラグを設定します。
     * @param completionFlag 完了フラグ
     */
    public void setCompletionFlag(String completionFlag)
    {
        _completionFlag = completionFlag;
    }

    /**
     * 実績ケース数を返します。
     * @return 実績ケース数を返します。
     */
    public int getResultCaseQty()
    {
        return _resultCaseQty;
    }

    /**
     * 実績ケース数を設定します。
     * @param resultCaseQty 実績ケース数
     */
    public void setResultCaseQty(int resultCaseQty)
    {
        _resultCaseQty = resultCaseQty;
    }

    /**
     * 基準日を返します。
     * @return 基準日を返します。
     */
    public String getUseByDate()
    {
        return _useByDate;
    }

    /**
     * 基準日を設定します。
     * @param useByDate 基準日
     */
    public void setUseByDate(String useByDate)
    {
        _useByDate = useByDate;
    }

    /**
     * アイテム情報コメントを返します。
     * @return アイテム情報コメントを返します。
     */
    public String getItemInfo()
    {
        return _itemInfo;
    }

    /**
     * アイテム情報コメントを設定します。
     * @param itemInfo アイテム情報コメント
     */
    public void setItemInfo(String itemInfo)
    {
        _itemInfo = itemInfo;
    }

    /**
     * オーダー情報コメントを設定します。
     * @param orderInfo オーダー情報コメント
     */
    public void setOrderInfo(String orderInfo)
    {
        _orderInfo = orderInfo;
    }

    /**
     * オーダー情報コメントを返します。
     * @return オーダー情報コメントを返します。
     */
    public String getOrderInfo()
    {
        return _orderInfo;
    }

    /**
     * ゾーンNo.を返します。
     * @return ゾーンNo.を返します。
     */
    public String getZoneNo()
    {
        return _zoneNo;
    }

    /**
     * ゾーンNo.を設定します。
     * @param zoneNo ゾーンNo.
     */
    public void setZoneNo(String zoneNo)
    {
        _zoneNo = zoneNo;
    }

    /**
     * ボール入数を返します。
     * @return ボール入数を返します。
     */
    public int getBundleEnteringQty()
    {
        return _bundleEnteringQty;
    }

    /**
     * ボール入数を設定します。
     * @param bundleEnteringQty ボール入数
     */
    public void setBundleEnteringQty(int bundleEnteringQty)
    {
        _bundleEnteringQty = bundleEnteringQty;
    }

    /**
     * ボールITFを返します。
     * @return ボールITFを返します。
     */
    public String getBundleItf()
    {
        return _bundleItf;
    }

    /**
     * ボールITFを設定します。
     * @param bundleItf ボールITF
     */
    public void setBundleItf(String bundleItf)
    {
        _bundleItf = bundleItf;
    }

    /**
     * ロット入数を返します。
     * @return ロット入数を返します。
     */
    public int getLotEnteringQty()
    {
        return _lotEnteringQty;
    }

    /**
     * ロット入数を設定します。
     * @param lotEnteringQty ロット入数
     */
    public void setLotEnteringQty(int lotEnteringQty)
    {
        _lotEnteringQty = lotEnteringQty;
    }

    /**
     * 棚を返します。
     * @return 棚を返します。
     */
    public String getLocation()
    {
        return _location;
    }

    /**
     * 棚を設定します。
     * @param location 棚
     */
    public void setLocation(String location)
    {
        _location = location;
    }

    /**
     * バッチSeqNoを返します。
     * @return バッチSeqNoを返します。
     */
    public String getBatchSeqNo()
    {
        return _batchSeqNo;
    }

    /**
     * バッチSeqNoを設定します。
     * @param batchSeqNo バッチSeqNo
     */
    public void setBatchSeqNo(String batchSeqNo)
    {
        _batchSeqNo = batchSeqNo;
    }

    /**
     * 予定オーダーNo.を返します。
     * @return 予定オーダーNo.を返します。
     */
    public String getPlanOrderNo()
    {
        return _planOrderNo;
    }

    /**
     * 予定オーダーNo.を設定します。
     * @param planOrderNo 予定オーダーNo.
     */
    public void setPlanOrderNo(String planOrderNo)
    {
        _planOrderNo = planOrderNo;
    }

    /**
     * 通番を返します。
     * @return 通番を返します。
     */
    public int getThroughNo()
    {
        return _throughNo;
    }

    /**
     * 通番を設定します。
     * @param throughNo 通番
     */
    public void setThroughNo(int throughNo)
    {
        _throughNo = throughNo;
    }

    /**
     * オーダー内商品数を返します。
     * @return オーダー内商品数を返します。
     */
    public int getOrderItemQty()
    {
        return _orderItemQty;
    }

    /**
     * オーダー内商品数を設定します。
     * @param orderItemQty オーダー内商品数
     */
    public void setOrderItemQty(int orderItemQty)
    {
        _orderItemQty = orderItemQty;
    }

    /**
     * オーダー通番を返します。
     * @return オーダー通番を返します。
     */
    public int getOrderThroughNo()
    {
        return _orderThroughNo;
    }

    /**
     * オーダー通番を設定します。
     * @param orderThroughNo オーダー通番
     */
    public void setOrderThroughNo(int orderThroughNo)
    {
        _orderThroughNo = orderThroughNo;
    }

    /**
     * オーダー通番合計を返します。
     * @return オーダー通番合計を返します。
     */
    public int getOrderThroughNoCnt()
    {
        return _orderThroughNoCnt;
    }

    /**
     * オーダー通番合計を設定します。
     * @param orderThroughNoCnt オーダー通番合計
     */
    public void setOrderThroughNoCnt(int orderThroughNoCnt)
    {
        _orderThroughNoCnt = orderThroughNoCnt;
    }

    /**
     * 汎用フラグを返します。
     * @return 汎用フラグを返します。
     */
    public String getGeneralFlag()
    {
        return _generalFlag;
    }

    /**
     * 汎用フラグを設定します。
     * @param generalFlag 汎用フラグ
     */
    public void setGeneralFlag(String generalFlag)
    {
        _generalFlag = generalFlag;
    }

    /**
     * シュートNoを返します。
     * @return シュートNoを返します。
     */
    public String getShootNo()
    {
        return _shootNo;
    }

    /**
     * シュートNoを設定します。
     * @param shootNo シュートNo
     */
    public void setShootNo(String shootNo)
    {
        _shootNo = shootNo;
    }

    /**
     * 作業ゾーンを返します。
     * @return 作業ゾーンを返します。
     */
    public String getWorkZoneNo()
    {
        return _workZoneNo;
    }

    /**
     * 作業ゾーンを設定します。
     * @param workZoneNo 作業ゾーン
     */
    public void setWorkZoneNo(String workZoneNo)
    {
        _workZoneNo = workZoneNo;
    }

    /**
     * 開始棚を返します。
     * @return 開始棚を返します。
     */
    public String getStartLocationNo()
    {
        return _startLocationNo;
    }

    /**
     * 開始棚を設定します。
     * @param startLocationNo 開始棚
     */
    public void setStartLocationNo(String startLocationNo)
    {
        _startLocationNo = startLocationNo;
    }

    /**
     * 終了棚を返します。
     * @return 終了棚を返します。
     */
    public String getEndLocationNo()
    {
        return _endLocationNo;
    }

    /**
     * 終了棚を設定します。
     * @param endLocationNo 終了棚
     */
    public void setEndLocationNo(String endLocationNo)
    {
        _endLocationNo = endLocationNo;
    }

    /**
     * ユーザID(FROM)を返します。
     * @return  ユーザID(FROM)を返します。
     */
    public String getUserIdFrom()
    {
        return _userIdFrom;
    }

    /**
     * ユーザID(FROM)を設定します。
     * @param userIdFrom ユーザID(FROM)
     */
    public void setUserIdFrom(String userIdFrom)
    {
        _userIdFrom = userIdFrom;
    }

    /**
     * ユーザID(TO)を返します。
     * @return  ユーザID(TO)を返します。
     */
    public String getUserIdTo()
    {
        return _userIdTo;
    }

    /**
     * ユーザID(TO)を設定します。
     * @param userIdTo ユーザID(TO)
     */
    public void setUserIdTo(String userIdTo)
    {
        _userIdTo = userIdTo;
    }

    /**
     * 開始作業月を返します。
     * @return 開始作業月を返します。
     */
    public String getStartWorkManth()
    {
        return _startWorkManth;
    }

    /**
     * 開始作業月を設定します。
     * @param startWorkManth 開始作業月
     */
    public void setStartWorkManth(String startWorkManth)
    {
        _startWorkManth = startWorkManth;
    }

    /**
     * 終了作業月を返します。
     * @return 終了作業月を返します。
     */
    public String getEndWorkManth()
    {
        return _endWorkManth;
    }

    /**
     * 終了作業月を設定します。
     * @param endWorkManth 終了作業月
     */
    public void setEndWorkManth(String endWorkManth)
    {
        _endWorkManth = endWorkManth;
    }

    /**
     * 行数を返します。
     * @return 行数を返します。
     */
    public int getLineCnt()
    {
        return _lineCnt;
    }

    /**
     * 行数を設定します。
     * @param lineCnt 行数
     */
    public void setLineCnt(int lineCnt)
    {
        _lineCnt = lineCnt;
    }

    /**
     * 実績オーダーNo.を返します。
     * @return 実績オーダーNo.を返します。
     */
    public String getResultOrderNo()
    {
        return _resultOrderNo;
    }

    /**
     * 実績オーダーNo.を設定します。
     * @param resultOrderNo 実績オーダーNo.
     */
    public void setResultOrderNo(String resultOrderNo)
    {
        _resultOrderNo = resultOrderNo;
    }

    /**
     * 完了処理フラグを返します。
     * @return 完了処理フラグを返します。
     */
    public String getWorkflag()
    {
        return _workFlag;
    }

    /**
     * 完了処理フラグを設定します。
     * @param workFlag 完了処理フラグ
     */
    public void setWorkflag(String workFlag)
    {
        _workFlag = workFlag;
    }

    /**
     * CSV処理フラグを返します。
     * @return CSV処理フラグを返します。
     */
    public String getPrintCsv()
    {
        return _printCsv;
    }

    /**
     * CSV処理フラグを設定します。
     * @param printCsv CSV処理フラグ
     */
    public void setPrintCsv(String printCsv)
    {
        _printCsv = printCsv;
    }

    /**
     * 作業No.リストを取得します。
     * @return 作業No.リスト
     */
    public List<String> getJobNoList()
    {
        return _jobNoList;
    }

    /**
     * 作業No.リストを設定します。
     * @param jobNoList 作業No.リスト
     */
    public void setJobNoList(List<String> jobNoList)
    {
        _jobNoList = jobNoList;
    }

    /**
     * レベルA検索フラグを返します。
     * @return レベルA検索フラグを返します。
     */
    public boolean isLevelAFlag()
    {
        return _levelA;
    }

    /**
     * レベルA検索フラグを設定します。
     * @param mondayFlag レベルA検索フラグ
     */
    public void setLevelAFlag(boolean levelA)
    {
        _levelA = levelA;
    }

    /**
     * レベルB検索フラグを返します。
     * @return レベルB検索フラグを返します。
     */
    public boolean isLevelBFlag()
    {
        return _levelB;
    }

    /**
     * レベルB検索フラグを設定します。
     * @param tuesdayFlag レベルB検索フラグ
     */
    public void setLevelBFlag(boolean levelB)
    {
        _levelB = levelB;
    }

    /**
     * レベルC検索フラグを返します。
     * @return レベルC検索フラグを返します。
     */
    public boolean isLevelCFlag()
    {
        return _levelC;
    }

    /**
     * レベルC検索フラグを設定します。
     * @param wednesdayFlag レベルC検索フラグ
     */
    public void setLevelCFlag(boolean levelC)
    {
        _levelC = levelC;
    }

}
//end of class

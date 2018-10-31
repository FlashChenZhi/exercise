// $Id: ParameterDefine.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * BlueDog等の画面処理を行うクラスとスケジュール処理を行うクラス間で受け渡すパラメータに
 * セットするためのフラグ・定数を宣言するためのインターフェースです。<BR>
 * パッケージ共通で使用する定義を宣言します。
 *
 * <BR>
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Last commit: $Author: admin $
 */
public interface ParameterDefine
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    // 共通 ------------------------------------------------------------
    /**
     * 検索条件(プルダウンなど) : 全て
     */
    public static final String SEARCH_ALL = "99";

    /**
     * 状態フラグ：未開始
     */
    public static final String STATUS_FLAG_UNSTART = SystemDefine.STATUS_FLAG_UNSTART;

    /**
     * 状態フラグ：作業中
     */
    public static final String STATUS_FLAG_NOWWORKING = SystemDefine.STATUS_FLAG_NOWWORKING;

    /**
     * 状態フラグ：完了
     */
    public static final String STATUS_FLAG_COMPLETION = SystemDefine.STATUS_FLAG_COMPLETION;

    /**
     * 状態フラグ：削除
     */
    public static final String STATUS_FLAG_DELETE = SystemDefine.STATUS_FLAG_DELETE;

    /**
     * 実績報告：未報告
     */
    public static final String REPORT_FLAG_NOT_REPORT = SystemDefine.REPORT_FLAG_NOT_REPORT;

    /**
     * 実績報告：未報済
     */
    public static final String REPORT_FLAG_REPORT = SystemDefine.REPORT_FLAG_REPORT;

    /**
     * 完了フラグ : 未作業に戻す
     */
    public static final String COMPLETION_FLAG_UNSTART = "0";

    /**
     * 完了フラグ : 確定(全数完了or欠品完了)
     */
    public static final String COMPLETION_FLAG_DECISION = "1";

    /**
     * 完了フラグ : 分納or分割or保留
     */
    public static final String COMPLETION_FLAG_REMNANT = "2";

    /**
     * 完了フラグ(RFT) : 箱替
     */
    public static final String COMPLETION_FLAG_BOXCHANGE = "3";

    /**
     * 完了フラグ(RFT) : 完了処理済
     */
    public static final String COMPLETION_FLAG_COMPLETED = "5";

    /**
     * 完了フラグ(未使用) : 上書
     */
    public static final String COMPLETION_FLAG_OVERWRITE = "7";

    /**
     * 完了フラグ (未使用): 追加
     */
    public static final String COMPLETION_FLAG_ADD = "8";

    /**
     * 完了フラグ (RFT): キャンセル
     */
    public static final String COMPLETION_FLAG_CANCEL = "9";

    // InParameter
    /**
     * 処理フラグ : 修正
     */
    public static final String PROCESS_FLAG_MODIFY = "0";

    /**
     * 処理フラグ : 削除
     */
    public static final String PROCESS_FLAG_DELETE = "1";

    /**
     * 処理フラグ : 全削除
     */
    public static final String PROCESS_FLAG_DELETE_ALL = "2";

    /**
     * 処理フラグ : 再発行
     */
    public static final String PROCESS_FLAG_REPRINT = "3";

    /**
     * 処理フラグ : 作業キャンセル
     */
    public static final String PROCESS_FLAG_WORK_CANCEL = "4";

    /**
     * 処理フラグ : 表示(進捗画面用)
     */
    public static final String PROCESS_FLAG_VIEW = "5";

    /**
     * 処理フラグ : 次頁(進捗画面用)
     */
    public static final String PROCESS_FLAG_NEXT_PAGE = "6";

    /**
     * 処理フラグ : 前頁(進捗画面用)
     */
    public static final String PROCESS_FLAG_PREVIOUS_PAGE = "7";

    /**
     * 処理フラグ : 入力
     */
    public static final String PROCESS_FLAG_INPUT = "8";

    /**
     * 処理フラグ：登録
     */
    public static final String PROCESS_FLAG_REGIST = "9";

    /**
     * 集約条件 : 予定単位
     */
    public static final String COLLECT_CONDITION_PLAN = "1";

    /**
     * 集約条件 : 詳細表示
     */
    public static final String COLLECT_CONDITION_DETAIL = "2";

    /**
     * 集約条件：全予定日
     */
    public static final String COLLECT_ALL_PLANDATE = "3";

    /**
     * 集約条件：予定日別
     */
    public static final String COLLECT_PLANDATE_UNIT = "4";

    /**
     * 集約条件：予定日＋バッチNo.別
     */
    public static final String COLLECT_BATCHNO_UNIT = "5";

    /**
     * 集約条件 : 商品単位
     */
    public static final String COLLECT_CONDITION_ITEM = "6";

    /**
     * 集約条件 : 期間内合計表示
     */
    public static final String COLLECT_CONDITION_TEAM = "7";

    /**
     * 集約条件 : 日別合計表示
     */
    public static final String COLLECT_CONDITION_DAILY = "8";

    /**
     * 取込データの拡張子
     */
    public static final String EXTENSION = ".txt";

    /**
     * 開始終了フラグ : 開始
     */
    public static final String FROM_TO_FLAG_FROM = "0";

    /**
     * 開始終了フラグ : 終了
     */
    public static final String FROM_TO_FLAG_TO = "1";

    /**
     * 検索対象テーブル : 予定
     */
    public static final String SEARCH_TABLE_PLAN = "1";

    /**
     * 検索対象テーブル : 作業
     */
    public static final String SEARCH_TABLE_WORK = "2";

    /**
     * 検索対象テーブル : 在庫
     */
    public static final String SEARCH_TABLE_STOCK = "3";

    /**
     * 検索対象テーブル : 実績
     */
    public static final String SEARCH_TABLE_RESULT = "4";

    /**
     * 検索対象テーブル : 実績送信
     */
    public static final String SEARCH_TABLE_HOSTSEND = "5";

    /**
     * 検索対象テーブル : マスタ
     */
    public static final String SEARCH_TABLE_MASTER = "6";

    /**
     * 検索対象テーブル：欠品
     */
    public static final String SEARCH_TABLE_SHORTAGE = "7";

    /**
     * 検索対象テーブル ： AS/RS棚情報
     */
    public static final String SEARCH_TABLE_SHELF = "8";

    /**
     * 検索対象テーブル : 作業者実績情報
     */
    public static final String SEARCH_TABLE_WORKERRESULT = "9";

    /**
     * 検索対象テーブル : 移動作業
     */
    public static final String SEARCH_TABLE_MOVE_WORK = "10";

    /**
     * 検索対象テーブル : 移動実績
     */
    public static final String SEARCH_TABLE_MOVE_RESULT = "11";

    /**
     * 検索対象テーブル : 棚卸作業
     */
    public static final String SEARCH_TABLE_INVENTORY_WORK = "12";

    /**
     * 検索対象テーブル : 棚卸実績
     */
    public static final String SEARCH_TABLE_INVENTORY_RESULT = "13";

    /**
     * 検索対象テーブル : 棚マスタ
     */
    public static final String SEARCH_TABLE_LOCATE = "14";

    /**
     * 検索対象テーブル : 在庫履歴情報
     */
    public static final String SEARCH_TABLE_STOCKHISTORY = "15";

    // OutParameter
    /**
     * ボタン制御フラグ : 前次頁ボタン使用不可
     */
    public static final String BUTTON_CONTROL_FLAG_ALL_OFF = "0";

    /**
     * ボタン制御フラグ : 前頁ボタン使用不可
     */
    public static final String BUTTON_CONTROL_FLAG_PREVIOUS_OFF = "1";

    /**
     * ボタン制御フラグ : 次頁ボタン使用不可
     */
    public static final String BUTTON_CONTROL_FLAG_NEXT_OFF = "2";

    /**
     * ボタン制御フラグ : 前次頁ボタン使用不可
     */
    public static final String BUTTON_CONTROL_FLAG_ALL_ON = "3";

    // 2007/06/27 SystemPrameterDefineより移動 E.Takeda
    /**
     * データ区分 : 入荷
     */
    public static final String DATA_TYPE_RECEIVE = "01";

    /**
     * データ区分 : 入庫
     */
    public static final String DATA_TYPE_STORAGE = "02";

    /**
     * データ区分 : 出庫
     */
    public static final String DATA_TYPE_RETRIEVAL = "03";

    /**
     * データ区分 : 仕分
     */
    public static final String DATA_TYPE_SORTING = "04";

    /**
     * データ区分 : 出荷
     */
    public static final String DATA_TYPE_SHIPPING = "05";

    /**
     * データ区分 : 在庫移動
     */
    public static final String DATA_TYPE_MOVEMENT = "11";

    /**
     * データ区分 : 棚卸
     */
    public static final String DATA_TYPE_INVENTORY = "12";

    /**
     * データ区分 : 予定外入庫
     */
    public static final String DATA_TYPE_NOPLAN_STORAGE = "13";

    /**
     * データ区分 : 予定外出庫
     */
    public static final String DATA_TYPE_NOPLAN_RETRIEVAL = "14";

    // END

    /**
     * 作業種別 : 入庫
     */
    public static final String WORK_TYPE_STORAGE = SystemDefine.WORK_TYPE_STORAGE;

    /**
     * 作業種別 : 出庫
     */
    public static final String WORK_TYPE_RETRIEVAL = SystemDefine.WORK_TYPE_RETRIEVAL;

    /**
     * 作業種別 : 予定外入庫
     */
    public static final String WORK_TYPE_NOPLAN_STORAGE = SystemDefine.WORK_TYPE_NOPLAN_STORAGE;

    /**
     * 作業種別 : 予定外出庫
     */
    public static final String WORK_TYPE_NOPLAN_RETRIEVAL = SystemDefine.WORK_TYPE_NOPLAN_RETRIEVAL;

    /**
     * 作業種別 : 予定外入庫(積増)
     */
    public static final String WORK_TYPE_ADD_STORAGE = SystemDefine.WORK_TYPE_ADD_STORAGE;

    /**
     * 作業種別 : 在庫確認
     */
    public static final String WORK_TYPE_INVENTORYCHECK = SystemDefine.WORK_TYPE_INVENTORYCHECK;

    /**
     * 作業種別 : 棚間移動(From)
     */
    public static final String WORK_TYPE_RACKMOVE_FROM = SystemDefine.WORK_TYPE_RACKMOVE_FROM;

    /**
     * 作業種別 : 計画補充
     */
    public static final String WORK_TYPE_NORMAL_REPLENISHMENT = SystemDefine.WORK_TYPE_NORMAL_REPLENISHMENT;

    /**
     * 作業種別 : 緊急補充
     */
    public static final String WORK_TYPE_EMERGENCY_REPLENISHMENT = SystemDefine.WORK_TYPE_EMERGENCY_REPLENISHMENT;

    /**
     * 作業種別 : 強制払出し
     */
    public static final String WORK_TYPE_EXPENDITURE = SystemDefine.WORK_TYPE_EXPENDITURE;

    /**
     * 作業種別 : 搬送データ削除
     */
    public static final String WORK_TYPE_CARRYINFODELETE = SystemDefine.WORK_TYPE_CARRYINFODELETE;

    /**
     * 作業種別 : 空出荷
     */
    public static final String WORK_TYPE_EMPTYRETRIEVAL = SystemDefine.WORK_TYPE_EMPTYRETRIEVAL;

    /**
     * 作業区分 : 未作業
     */
    public static final String JOB_TYPE_UNSTART = SystemDefine.JOB_TYPE_UNSTART;

    /**
     * 作業区分 : 入荷
     */
    public static final String JOB_TYPE_RECEIVING = SystemDefine.JOB_TYPE_RECEIVING;

    /**
     * 作業区分 : 入庫
     */
    public static final String JOB_TYPE_STORAGE = SystemDefine.JOB_TYPE_STORAGE;

    /**
     * 作業区分 : 出庫
     */
    public static final String JOB_TYPE_RETRIEVAL = SystemDefine.JOB_TYPE_RETRIEVAL;

    /**
     * 作業区分 : 仕分
     */
    public static final String JOB_TYPE_SORTING = SystemDefine.JOB_TYPE_SORTING;

    /**
     * 作業区分 : 出荷
     */
    public static final String JOB_TYPE_SHIPPING = SystemDefine.JOB_TYPE_SHIPPING;

    /**
     * 作業区分 : 移動
     */
    public static final String JOB_TYPE_MOVEMENT = SystemDefine.JOB_TYPE_MOVEMENT;

    /**
     * 作業区分 : 移動入庫
     */
    public static final String JOB_TYPE_MOVEMENT_STORAGE = SystemDefine.JOB_TYPE_MOVEMENT_STORAGE;

    /**
     * 作業区分 : 移動出庫
     */
    public static final String JOB_TYPE_MOVEMENT_RETRIEVAL = SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL;

    /**
     * 作業区分 : 予定外入庫
     */
    public static final String JOB_TYPE_NOPLAN_STORAGE = SystemDefine.JOB_TYPE_NOPLAN_STORAGE;

    /**
     * 作業区分 : 予定外出庫
     */
    public static final String JOB_TYPE_NOPLAN_RETRIEVAL = SystemDefine.JOB_TYPE_NOPLAN_RETRIEVAL;

    /**
     * 作業区分 : メンテナンス増
     */
    public static final String JOB_TYPE_MAINTENANCE_PLUS = SystemDefine.JOB_TYPE_MAINTENANCE_PLUS;

    /**
     * 作業区分 : メンテナンス減
     */
    public static final String JOB_TYPE_MAINTENANCE_MINUS = SystemDefine.JOB_TYPE_MAINTENANCE_MINUS;

    /**
     * 作業区分 : 棚卸
     */
    public static final String JOB_TYPE_INVENTORY = SystemDefine.JOB_TYPE_INVENTORY;

    /**
     * 作業区分 : 棚卸増
     */
    public static final String JOB_TYPE_INVENTORY_PLUS = SystemDefine.JOB_TYPE_INVENTORY_PLUS;

    /**
     * 作業区分 : 棚卸減
     */
    public static final String JOB_TYPE_INVENTORY_MINUS = SystemDefine.JOB_TYPE_INVENTORY_MINUS;

    /**
     * 作業区分 : 在庫確認
     */
    public static final String JOB_TYPE_ASRS_INVENTORYCHECK = SystemDefine.JOB_TYPE_ASRS_INVENTORYCHECK;

    /**
     * 作業区分 : 計画補充
     */
    public static final String JOB_TYPE_NORMAL_REPLENISHMENT = SystemDefine.JOB_TYPE_NORMAL_REPLENISHMENT;

    /**
     * 作業区分 : 緊急補充
     */
    public static final String JOB_TYPE_EMERGENCY_REPLENISHMENT = SystemDefine.JOB_TYPE_EMERGENCY_REPLENISHMENT;

    /**
     * 作業区分 : 強制払出し
     */
    public static final String JOB_TYPE_ASRS_EXPENDITURE = SystemDefine.JOB_TYPE_ASRS_EXPENDITURE;

    /**
     * 作業区分 : 搬送データ削除
     */
    public static final String JOB_TYPE_ASRS_CARRYINFODELETE = SystemDefine.JOB_TYPE_ASRS_CARRYINFODELETE;

    /**
     * ハードウェア区分 : 未作業
     */
    public static final String HARDWARE_TYPE_UNSTART = "0";

    /**
     * ハードウェア区分 : リスト
     */
    public static final String HARDWARE_TYPE_LIST = "1";

    /**
     * ハードウェア区分 : RFT
     */
    public static final String HARDWARE_TYPE_RFT = "2";

    /**
     * ハードウェア区分 : AS/RS
     */
    public static final String HARDWARE_TYPE_ASRS = "3";

    /**
     * 増減区分 : 在庫加算(入庫)
     */
    public static final String INC_DEC_TYPE_STOCK_INCREMENT = "1";

    /**
     * 増減区分 : 在庫減算(出庫)
     */
    public static final String INC_DEC_TYPE_STOCK_DECREMENT = "2";

    /**
     * 増減区分 : 欠品減算
     */
    public static final String INC_DEC_TYPE_SHORTAGE_DECREMENT = "3";

    /**
     * 端末区分 : HT
     */
    public static final String TERMINAL_TYPE_HT = "00";

    /**
     * 端末区分 : 検品端末
     */
    public static final String TERMINAL_TYPE_IT = "10";

    /**
     * 端末区分 : Pカート
     */
    public static final String TERMINAL_TYPE_PCART = "20";

    /**
     * ホスト取消区分 : 通常データ
     */
    public static final String CANCEL_FLAG_NORMAL = SystemDefine.CANCEL_FLAG_NORMAL;

    /**
     * ホスト取消区分 : ホスト取消
     */
    public static final String CANCEL_FLAG_HOST_CANCEL = SystemDefine.CANCEL_FLAG_HOST_CANCEL;

    /**
     * マスタデータ取込区分 : 通常(登録)
     */
    public static final String MASTERDATA_LOAD_FLAG_NORMAL = SystemDefine.MASTERDATA_LOAD_FLAG_NORMAL;

    /**
     * マスタデータ取込区分 : 削除
     */
    public static final String MASTERDATA_LOAD_FLAG_DELETE = SystemDefine.MASTERDATA_LOAD_FLAG_DELETE;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
}

//$Id: SystemDefine.java 7650 2010-03-17 09:31:17Z okayama $
package jp.co.daifuku.wms.base.entity;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
/**
 * WareNaviシステム共通となる固定値項目の定義をするためのクラスです。<BR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>Kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7650 $, $Date: 2010-03-17 18:31:17 +0900 (水, 17 3 2010) $
 * @author  $Author: okayama $
 */
public interface SystemDefine
{
    // Class feilds ------------------------------------------------
    /**
     * 作業区分 : 未作業
     */
    public static final String JOB_TYPE_UNSTART = "00";

    /**
     * 作業区分 : 入荷
     */
    public static final String JOB_TYPE_RECEIVING = "01";

    /**
     * 作業区分 : 入庫
     */
    public static final String JOB_TYPE_STORAGE = "02";

    /**
     * 作業区分 : 出庫
     */
    public static final String JOB_TYPE_RETRIEVAL = "03";

    /**
     * 作業区分 : 仕分
     */
    public static final String JOB_TYPE_SORTING = "04";

    /**
     * 作業区分 : 出荷
     */
    public static final String JOB_TYPE_SHIPPING = "05";

    /**
     * 作業区分 : 移動
     */
    public static final String JOB_TYPE_MOVEMENT = "10";

    /**
     * 作業区分 : 在庫
     */
    public static final String JOB_TYPE_STOCK = "11";

    /**
     * 作業区分 : 移動入庫
     */
    public static final String JOB_TYPE_MOVEMENT_STORAGE = "12";

    /**
     * 作業区分 : 移動出庫
     */
    public static final String JOB_TYPE_MOVEMENT_RETRIEVAL = "13";

    /**
     * 作業区分 : 予定外入庫
     */
    public static final String JOB_TYPE_NOPLAN_STORAGE = "22";

    /**
     * 作業区分 : 予定外出庫
     */
    public static final String JOB_TYPE_NOPLAN_RETRIEVAL = "23";

    /**
     * 作業区分 : 予定外入出庫
     */
    public static final String JOB_TYPE_NOPLAN_STORAGE_RETRIEVAL = "24";

    /**
     * 作業区分 : 直行
     */
    public static final String JOB_TYPE_DIRECT_TRAVEL = "26";

    /**
     * 作業区分 : 再入庫
     */
    public static final String JOB_TYPE_RESTORING = "27";

    /**
     * 作業区分 : メンテナンス増
     */
    public static final String JOB_TYPE_MAINTENANCE_PLUS = "32";

    /**
     * 作業区分 : メンテナンス減
     */
    public static final String JOB_TYPE_MAINTENANCE_MINUS = "33";

    /**
     * 作業区分 : 棚卸
     */
    public static final String JOB_TYPE_INVENTORY = "40";

    /**
     * 作業区分 : 棚卸増
     */
    public static final String JOB_TYPE_INVENTORY_PLUS = "42";

    /**
     * 作業区分 : 棚卸減
     */
    public static final String JOB_TYPE_INVENTORY_MINUS = "43";

    /**
     * 作業区分 : 在庫確認
     */
    public static final String JOB_TYPE_ASRS_INVENTORYCHECK = "40";

    /**
     * 作業区分 : 棚間移動
     */
    public static final String JOB_TYPE_ASRS_RACK_TO_RACK = "45";

    /**
     * 作業区分 : 棚替
     */
    public static final String JOB_TYPE_ASRS_REARRANGE = "46";

    /**
     * 作業区分 : 計画補充
     */
    public static final String JOB_TYPE_NORMAL_REPLENISHMENT = "55";

    /**
     * 作業区分 : 緊急補充
     */
    public static final String JOB_TYPE_EMERGENCY_REPLENISHMENT = "56";

    /**
     * 作業区分 : 強制払出し
     */
    public static final String JOB_TYPE_ASRS_EXPENDITURE = "60";

    /**
     * 作業区分 : 搬送データ削除
     */
    public static final String JOB_TYPE_ASRS_CARRYINFODELETE = "90";

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
     * 状態フラグ : 未作業
     */
    public static final String STATUS_FLAG_UNSTART = "0";

    /**
     * 状態フラグ : 作業中
     */
    public static final String STATUS_FLAG_NOWWORKING = "1";

    /**
     * 状態フラグ : 出庫作業中
     */
    public static final String STATUS_FLAG_MOVE_RETRIEVAL_WORKING = "1";

    /**
     * 状態フラグ : 棚卸作業中
     */
    public static final String STATUS_FLAG_INVENTORY_WORKING = "1";

    /**
     * 状態フラグ : 棚卸作業済み
     */
    public static final String STATUS_FLAG_INVENTORY_WORKING_COMPLETED = "2";

    /**
     * 状態フラグ : 棚卸確定済み
     */
    public static final String STATUS_FLAG_INVENTORY_ALREADY_COMPLETED = "4";

    /**
     * 状態フラグ : 棚卸設定開始
     */
    public static final String STATUS_FLAG_INVENTORY_SETTING_START = "1";

    /**
     * 状態フラグ : 出庫済入庫待
     */
    public static final String STATUS_FLAG_MOVE_STORAGE_WAITING = "2";

    /**
     * 状態フラグ : 入庫作業中
     */
    public static final String STATUS_FLAG_MOVE_STORAGE_WORKING = "3";

    /**
     * 状態フラグ : 完了
     */
    public static final String STATUS_FLAG_COMPLETION = "4";

    /**
     * 状態フラグ : 入庫キャンセル
     */
    public static final String STATUS_FLAG_MOVE_STORAGE_CANCEL = "5";

    /**
     * 状態フラグ : 削除
     */
    public static final String STATUS_FLAG_DELETE = "9";

    /**
     * 状態フラグ : 在庫確認未作業
     */
    public static final String STATUS_FLAG_NO_CONFIRM = "0";

    /**
     * 状態フラグ : 在庫確認中
     */
    public static final String STATUS_FLAG_CONFIRM = "1";

    /**
     * 状態フラグ : 欠品予約
     */
    public static final String STATUS_FLAG_SHORTAGE_RESERVATION = "2";

    /**
     * Status flag for HostToEWN and EWNToHost - Unprocessed
     */
    public static final String STATUS_FLAG_UNPROCESSED = "0";

    /**
     * Status flag for HostToEWN and EWNToHost - Processed Normal
     */
    public static final String STATUS_FLAG_PROCESS_NORMAL = "1";

    /**
     * Status flag for HostToEWN and EWNToHost - Processed with Error
     */
    public static final String STATUS_FLAG_PROCESS_ERROR = "2";

    /**
     * ホスト取消区分 : 通常データ
     */
    public static final String CANCEL_FLAG_NORMAL = "0";

    /**
     * ホスト取消区分 : ホスト取消
     */
    public static final String CANCEL_FLAG_HOST_CANCEL = "1";

    /**
     * ホスト取消区分 : 修正
     */
    public static final String CANCEL_FLAG_HOST_MODIFY = "2";

    /**
     * マスタデータ取込区分 : 通常(登録)
     */
    public static final String MASTERDATA_LOAD_FLAG_NORMAL = "0";

    /**
     * マスタデータ取込区分 : 削除
     */
    public static final String MASTERDATA_LOAD_FLAG_DELETE = "1";

    /**
     * RFT状態フラグ : 停止中
     */
    public static final String RFT_STATUS_FLAG_STOP = "0";

    /**
     * RFT状態フラグ : 起動中
     */
    public static final String RFT_STATUS_FLAG_START = "1";

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
     * 端末区分 : 画像登録用HT
     */
    public static final String TERMINAL_TYPE_CAMERA_HT = "21";

    /**
     * 無線状態フラグ : 無線エリア内
     */
    public static final String RADIO_FLAG_IN = "0";

    /**
     * 無線状態フラグ : 無線エリア外
     */
    public static final String RADIO_FLAG_OUT = "1";

    /**
     * 登録区分 : ファイル取込
     */
    public static final String REGIST_KIND_DATALOADER = "0";

    /**
     * 登録区分 : 端末登録
     */
    public static final String REGIST_KIND_TERMINAL_REGIST = "1";

    /**
     * 実績報告区分 : 未報告
     */
    public static final String REPORT_FLAG_NOT_REPORT = "0";

    /**
     * 実績報告区分 : 報告済
     */
    public static final String REPORT_FLAG_REPORT = "1";

    /**
     * 実績報告区分 : 搬送中
     */
    public static final String REPORT_FLAG_CARRY = "2";

    /**
     * スケジュール処理フラグ : 未スケジュール
     */
    public static final String SCH_FLAG_NOT_SCHEDULE = "0";

    /**
     * スケジュール処理フラグ : スケジュール済
     */
    public static final String SCH_FLAG_SCHEDULE = "1";

    /**
     * スケジュール処理フラグ : 欠品予約
     */
    public static final String SCH_FLAG_RESERVATION_SHORTAGE = "2";

    /**
     * 予定一意キー : ダミー
     */
    public static final String PLAN_UKEY_DUMMY = "0";

    /**
     * 作業種別 : 入庫
     */
    public static final String WORK_TYPE_STORAGE = "02";

    /**
     * 作業種別 : 出庫
     */
    public static final String WORK_TYPE_RETRIEVAL = "03";

    /**
     * 作業種別 : 予定外入庫
     */
    public static final String WORK_TYPE_NOPLAN_STORAGE = "22";

    /**
     * 作業種別 : 予定外出庫
     */
    public static final String WORK_TYPE_NOPLAN_RETRIEVAL = "23";

    /**
     * 作業種別 : 予定外入庫(積増)
     */
    public static final String WORK_TYPE_ADD_STORAGE = "25";

    /**
     * 作業種別 : 直行
     */
    public static final String WORK_TYPE_DIRECT_TRAVEL = "26";

    /**
     * 作業種別 : 再入庫
     */
    public static final String WORK_TYPE_RESTORING = "27";

    /**
     * 作業種別 : 在庫確認
     */
    public static final String WORK_TYPE_INVENTORYCHECK = "40";

    /**
     * 作業種別 : 棚間移動(From)
     */
    public static final String WORK_TYPE_RACKMOVE_FROM = "45";

    /**
     * 作業種別 : 計画補充
     */
    public static final String WORK_TYPE_NORMAL_REPLENISHMENT = "55";

    /**
     * 作業種別 : 緊急補充
     */
    public static final String WORK_TYPE_EMERGENCY_REPLENISHMENT = "56";

    /**
     * 作業種別 : 強制払出し
     */
    public static final String WORK_TYPE_EXPENDITURE = "60";

    /**
     * 作業種別 : 搬送データ削除
     */
    public static final String WORK_TYPE_CARRYINFODELETE = "90";

    /**
     * 作業種別 : 空出荷
     */
    public static final String WORK_TYPE_EMPTYRETRIEVAL = "91";

    /**
     * パッケージ導入区分 : 導入済み
     */
    public static final String PACK_INSTALLED = "1";

    /**
     * パッケージ導入区分 : 未導入
     */
    public static final String PACK_NOT_INSTALLED = "0";

    /**
     * ログ区分 : 有効
     */
    public static final String LOG_ENABLED = "1";

    /**
     * ログ区分 : 無効
     */
    public static final String LOG_DISABLED = "0";

    /**
     * パッケージ区分 : 入荷
     */
    public static final String PACKAGE_KIND_RECEVING = "01";

    /**
     * パッケージ区分 : 入庫
     */
    public static final String PACKAGE_KIND_STORAGE = "02";

    /**
     * パッケージ区分 : 出庫
     */
    public static final String PACKAGE_KIND_RETRIEVAL = "03";

    /**
     * パッケージ区分 : 仕分
     */
    public static final String PACKAGE_KIND_SORTING = "04";

    /**
     * パッケージ区分 : 出荷
     */
    public static final String PACKAGE_KIND_SHIPPING = "05";

    /**
     * パッケージ区分 : 在庫
     */
    public static final String PACKAGE_KIND_STOCK = "06";

    /**
     * パッケージ区分 : AS/RS
     */
    public static final String PACKAGE_KIND_ASRS = "07";

    /**
     * パッケージ区分 : マスタ
     */
    public static final String PACKAGE_KIND_MASTER = "08";

    /**
     * パッケージ区分 : システム
     */
    public static final String PACKAGE_KIND_SYSTEM = "09";

    /**
     * 操作区分 : 出庫開始
     */
    public static final String OPERATION_KIND_RETRIEVAL_START = "01";

    /**
     * 操作区分 : 開始
     */
    public static final String OPERATION_KIND_START = "02";

    /**
     * 操作区分 : 完了
     */
    public static final String OPERATION_KIND_COMPLETE = "03";

    /**
     * 操作区分 : キャンセル
     */
    public static final String OPERATION_KIND_CANCEL = "04";

    /**
     * 操作区分 : RFT作業
     */
    public static final String OPERATION_KIND_RFT = "05";

    /**
     * 操作区分 : 修正登録
     */
    public static final String OPERATION_KIND_REGIST_MODIFY = "06";

    /**
     * 操作区分 : 印刷
     */
    public static final String OPERATION_KIND_PRINT = "07";

    /**
     * 操作区分 : XLS
     */
    public static final String OPERATION_KIND_XLS = "08";

    /**
     * 操作区分 : CSV
     */
    public static final String OPERATION_KIND_CSV = "09";

    /**
     * 操作区分 : 予定データ登録
     */
    public static final String OPERATION_KIND_PLAN_REGIST = "10";

    /**
     * 操作区分 : 予定データ修正
     */
    public static final String OPERATION_KIND_PLAN_MODIFY = "11";

    /**
     * 操作区分 : 予定データ削除
     */
    public static final String OPERATION_KIND_PLAN_DELETE = "12";

    /**
     * 操作区分 : 予定データ一括削除
     */
    public static final String OPERATION_KIND_PLAN_DELETE_BATCH = "13";

    /**
     * 操作区分 : 自動取込
     */
    public static final String OPERATION_KIND_LOADDATA_AUTOMATIC = "14";

    /**
     * 操作区分 : 手動取込
     */
    public static final String OPERATION_KIND_LOADDATA_MANUAL = "15";

    /**
     * 操作区分 : 自動報告
     */
    public static final String OPERATION_KIND_REPORT_AUTOMATIC = "16";

    /**
     * 操作区分 : 手動報告
     */
    public static final String OPERATION_KIND_REPORT_MANUAL = "17";

    /**
     * 更新区分 : 登録
     */
    public static final String UPDATE_KIND_REGIST = "1";

    /**
     * 更新区分 : 修正
     */
    public static final String UPDATE_KIND_MODIFY = "2";

    /**
     * 更新区分 : 削除
     */
    public static final String UPDATE_KIND_DELETE = "3";

    /**
     * 処理中フラグ : 処理中
     */
    public static final String PROCESS_IN_PROGRESS = "1";

    /**
     * 処理中フラグ : 未処理(処理完了済み)
     */
    public static final String PROCESS_COMPLETED = "0";

    /**
     * デフォルトのシステムNo.
     */
    public static final String SYSTEM_NO_DEFAULT = "1";

    /**
     * ホスト通信 : 有効
     */
    public static final String HOST_ENABLED = "1";

    /**
     * ホスト通信 : 無効
     */
    public static final String HOST_DISABLED = "0";

    /**
     * FA/DA区分 : DA
     */
    public static final String FADA_DA = "0";

    /**
     * FA/DA区分 : FA
     */
    public static final String FADA_FA = "1";

    /**
     * システム管理区分 : システム
     */
    public static final String MANAGEMENT_TYPE_SYSTEM = "1";

    /**
     * システム管理区分 : ユーザ
     */
    public static final String MANAGEMENT_TYPE_USER = "0";

    /**
     * エリア種別 : 平置･移動ラック
     */
    public static final String AREA_TYPE_FLOOR = "0";

    /**
     * エリア種別 : AS/RS
     */
    public static final String AREA_TYPE_ASRS = "1";

    /**
     * エリア種別 : 入荷エリア
     */
    public static final String AREA_TYPE_RECEIVING = "7";

    /**
     * エリア種別 : 移動中エリア
     */
    public static final String AREA_TYPE_MOVING = "8";

    /**
     * エリア種別 : 仮置エリア
     */
    public static final String AREA_TYPE_TEMPORARY = "9";

    /**
     * 棚管理方式 : フリー管理
     */
    public static final String LOCATION_TYPE_FREE = "0";

    /**
     * 棚管理方式 : 棚マスタ管理
     */
    public static final String LOCATION_TYPE_MASTER = "1";

    /**
     * 棚管理方式 : 固定棚管理
     */
    public static final String LOCATION_TYPE_FIXED = "2";

    /**
     * 仮置在庫作成区分 : 作成しない
     */
    public static final String TEMPORARY_AREA_TYPE_NONE = "0";

    /**
     * 仮置在庫作成区分 : 全ての在庫を作成する
     */
    public static final String TEMPORARY_AREA_TYPE_ALL = "1";

    /**
     * 空棚検索方向 : バンク垂直検索
     */
    public static final String VACANT_SEARCH_TYPE_BANK_VERTICAL = "01";

    /**
     * 空棚検索方向 : アイル垂直検索
     */
    public static final String VACANT_SEARCH_TYPE_AISLE_VERTICAL = "02";

    /**
     * 空棚検索方向 : バンク水平検索
     */
    public static final String VACANT_SEARCH_TYPE_BANK_HORIZONTAL = "03";

    /**
     * 空棚検索方向 : アイル水平検索
     */
    public static final String VACANT_SEARCH_TYPE_AISLE_HORIZONTAL = "04";

    /**
     * 空棚検索方向 : レベル方向検索(HP側から)
     */
    public static final String VACANT_SEARCH_TYPE_ASRS_LEVEL_HP = "11";

    /**
     * 空棚検索方向 : レベル方向検索(OP側から)
     */
    public static final String VACANT_SEARCH_TYPE_ASRS_LEVEL_OP = "12";

    /**
     * 空棚検索方向 : ベイ方向検索(HP側から)
     */
    public static final String VACANT_SEARCH_TYPE_ASRS_BAY_HP = "13";

    /**
     * 空棚検索方向 : ベイ方向検索(OP側から)
     */
    public static final String VACANT_SEARCH_TYPE_ASRS_BAY_OP = "14";

    /**
     * 棚状態フラグ : 空棚
     */
    public static final String LOCATION_STATUS_FLAG_EMPTY = "0";

    /**
     * 棚状態フラグ : 実棚
     */
    public static final String LOCATION_STATUS_FLAG_STORAGED = "1";

    /**
     * 棚状態フラグ : 予約棚
     */
    public static final String LOCATION_STATUS_FLAG_RESERVATION = "2";

    /**
     * 引当パターン区分 : 通常用
     */
    public static final String ALLOCATE_TYPE_NORMAL = "0";

    /**
     * 引当パターン区分 : 補充用
     */
    public static final String ALLOCATE_TYPE_REPLENISHMENT = "1";

    /**
     * 補充元エリア区分 : 通常エリア
     */
    public static final String REPLENISHMENT_AREA_TYPE_NORMAL_AREA = "0";

    /**
     * 補充元エリア区分 : 補充エリア
     */
    public static final String REPLENISHMENT_AREA_TYPE_REPLENISHMENT_AREA = "1";

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
     * 分析区分 : 分析対象外
     */
    public static final String ANALYSIS_TYPE_NO_ANALYSIS = "0";

    /**
     * 分析区分 : 分析対象
     */
    public static final String ANALYSIS_TYPE_ANALYSIS = "1";

    /**
     * アイル状態 : 切離し
     */
    public static final String AISLE_STATUS_DISCONNECTED = "0";

    /**
     * アイル状態 : 正常
     */
    public static final String AISLE_STATUS_NORMAL = "1";

    /**
     * アイル状態 : 異常
     */
    public static final String AISLE_STATUS_ERROR = "2";

    /**
     * ステーション状態 : 搬送不可
     */
    public static final String STATION_STATUS_DISCONNECTED = "0";

    /**
     * ステーション状態 : 搬送可
     */
    public static final String STATION_STATUS_NORMAL = "1";

    /**
     * ステーション状態 : 異常
     */
    public static final String STATION_STATUS_ERROR = "2";

    /**
     * 在庫確認中フラグ : 未作業
     */
    public static final String INVENTORY_CHECK_FLAG_UNSTART = "0";

    /**
     * 在庫確認中フラグ : 在庫確認中
     */
    public static final String INVENTORY_CHECK_FLAG_WORKING = "1";

    /**
     * 在庫確認中フラグ : 空棚確認中
     */
    public static final String INVENTORY_CHECK_FLAG_WORKING_EMPTY = "2";

    /**
     * 棚使用不可フラグ : 使用可
     */
    public static final String PROHIBITION_FLAG_OK = "0";

    /**
     * 棚使用不可フラグ : 使用不可
     */
    public static final String PROHIBITION_FLAG_NG = "1";

    /**
     * アクセス不可棚フラグ : アクセス可
     */
    public static final String ACCESS_NG_FLAG_OK = "0";

    /**
     * アクセス不可棚フラグ : アクセス不可
     */
    public static final String ACCESS_NG_FLAG_NG = "1";

    /**
     * 送信不可フラグ : 送信不可
     */
    public static final String SENDABLE_FALSE = "0";

    /**
     * 送信不可フラグ : 送信可
     */
    public static final String SENDABLE_TRUE = "1";

    /**
     * ステーション種別 : その他
     */
    public static final String STATION_TYPE_OTHER = "0";

    /**
     * ステーション種別 : 入庫
     */
    public static final String STATION_TYPE_IN = "1";

    /**
     * ステーション種別 : 出庫
     */
    public static final String STATION_TYPE_OUT = "2";

    /**
     * ステーション種別 : 入出庫兼用
     */
    public static final String STATION_TYPE_INOUT = "3";

    /**
     * 設定種別 : 未使用
     */
    public static final String SETTING_TYPE_NONE = "0";

    /**
     * 設定種別 : 先行設定
     */
    public static final String SETTING_TYPE_PRECEDE = "1";

    /**
     * 設定種別 : 在荷確認設定
     */
    public static final String SETTING_TYPE_CONFIRM = "2";

    /**
     * 作業場種別 : 通常ステーション
     */
    public static final String WORKPLACE_TYPE_FLOOR = "0";

    /**
     * 作業場種別 : アイル独立ステーションの集合
     */
    public static final String WORKPLACE_TYPE_STAND_ALONE_STATIONS = "1";

    /**
     * 作業場種別 : アイル結合ステーションの集合
     */
    public static final String WORKPLACE_TYPE_AISLE_CONNECT_STATIONS = "2";

    /**
     * 作業場種別 : 代表ステーション
     */
    public static final String WORKPLACE_TYPE_MAIN_STATION = "3";

    /**
     * 作業場種別 : 全体作業場
     */
    public static final String WORKPLACE_TYPE_ALL = "4";

    /**
     * 作業表示運用 : 端末なし
     */
    public static final String OPERATION_DISPLAY_NONE = "0";

    /**
     * 作業表示運用 : 作業表示
     */
    public static final String OPERATION_DISPLAY_DISP_ONLY = "1";

    /**
     * 作業表示運用 : 作業指示 完了ボタンあり
     */
    public static final String OPERATION_DISPLAY_INSTRUCTION = "2";

    /**
     * 中断中フラグ : 使用可能
     */
    public static final String SUSPEND_OFF = "0";

    /**
     * 中断中フラグ : 中断中
     */
    public static final String SUSPEND_ON = "1";

    /**
     * 到着報告チェック : なし
     */
    public static final String ARRIVAL_OFF = "0";

    /**
     * 到着報告チェック : あり
     */
    public static final String ARRIVAL_ON = "1";

    /**
     * 荷姿チェック機能 : なし
     */
    public static final String LOAD_SIZE_OFF = "0";

    /**
     * 荷姿チェック機能 : あり
     */
    public static final String LOAD_SIZE_ON = "1";

    /**
     * 払出し区分 : 払い出し可
     */
    public static final String REMOVE_OK = "0";

    /**
     * 払出し区分 : 払い出し不可
     */
    public static final String REMOVE_NG = "1";

    /**
     * 再入庫作業有無 : 再入庫データ作成無し
     */
    public static final String RESTORING_OPERATION_NOT_CREATE = "0";

    /**
     * 再入庫作業有無 : 再入庫データ作成有り
     */
    public static final String RESTORING_OPERATION_CREATE = "1";

    /**
     * 再入庫搬送指示送信有無 : 搬送指示不要(AGC側で自動的入庫)
     */
    public static final String RESTORING_INSTRUCTION_AGC_STORAGE_SEND = "0";

    /**
     * 再入庫搬送指示送信有無 : 搬送指示必要(AWC側で指示作成)
     */
    public static final String RESTORING_INSTRUCTION_AWC_STORAGE_SEND = "1";

    /**
     * モード切替種別 : 未使用
     */
    public static final String MODE_TYPE_NONE = "0";

    /**
     * モード切替種別 : AWCモード切替
     */
    public static final String MODE_TYPE_AWC_CHANGE = "1";

    /**
     * モード切替種別 : AGCモード切替
     */
    public static final String MODE_TYPE_AGC_CHANGE = "2";

    /**
     * モード切替種別 : 自動モード切替
     */
    public static final String MODE_TYPE_AUTO_CHANGE = "3";

    /**
     * 現在作業モード : ニュートラル
     */
    public static final String CURRENT_MODE_NEUTRAL = "0";

    /**
     * 現在作業モード : 入庫モード
     */
    public static final String CURRENT_MODE_STORAGE = "1";

    /**
     * 現在作業モード : 出庫モード
     */
    public static final String CURRENT_MODE_RETRIEVAL = "2";

    /**
     * モード切替要求 : モード切替要求なし
     */
    public static final String MODE_REQUEST_NONE = "0";

    /**
     * モード切替要求 : 入庫モード切替要求
     */
    public static final String MODE_REQUEST_STORAGE = "1";

    /**
     * モード切替要求 : 出庫モード切替要求
     */
    public static final String MODE_REQUEST_RETRIEVAL = "2";

    /**
     * 倉庫種別 : 自動倉庫
     */
    public static final String WAREHOUSE_TYPE_AUTOMATED_WAREHOUSE = "1";

    /**
     * 倉庫種別 : 平置
     */
    public static final String WAREHOUSE_TYPE_CONVENTIONAL_WAREHOUSE = "2";

    /**
     * 自動倉庫運用種別 : オープン運用
     */
    public static final String EMPLOYMENT_TYPE_OPEN = "1";

    /**
     * 自動倉庫運用種別 : クローズ運用
     */
    public static final String EMPLOYMENT_TYPE_CLOSE = "2";

    /**
     *  フリーアロケーション運用 : フリーアロケーション運用なし
     */
    public static final String FREE_ALLOCATION_OFF = "0";

    /**
     *  フリーアロケーション運用 : フリーアロケーション運用あり
     */
    public static final String FREE_ALLOCATION_ON = "1";

    /**
     * 空棚検索優先順位 : アイル優先
     */
    public static final String LOCATION_SEARCH_AISLE = "1";

    /**
     * 空棚検索優先順位 : ゾーン優先
     */
    public static final String LOCATION_SEARCH_ZONE = "2";

    /**
     * アイル検索優先順 : 昇順
     */
    public static final String AISLE_SEARCH_ASCENDING = "1";

    /**
     * アイル検索優先順 : 降順
     */
    public static final String AISLE_SEARCH_DESCENDING = "2";

    /**
     * グループコントローラ状態 : 不明
     */
    public static final String GC_STATUS_UNKNOWN = "0";

    /**
     * グループコントローラ状態 : オンライン
     */
    public static final String GC_STATUS_ONLINE = "1";

    /**
     * グループコントローラ状態 : オフライン
     */
    public static final String GC_STATUS_OFFLINE = "2";

    /**
     * グループコントローラ状態 : オフライン予約
     */
    public static final String GC_STATUS_END_RESERVATION = "3";

    /**
     * 搬送状態 : 引当
     */
    public static final String CMD_STATUS_ALLOCATION = "0";

    /**
     * 搬送状態 : 開始
     */
    public static final String CMD_STATUS_START = "1";

    /**
     * 搬送状態 : 応答待ち
     */
    public static final String CMD_STATUS_WAIT_RESPONSE = "2";

    /**
     * 搬送状態 : 指示済み
     */
    public static final String CMD_STATUS_INSTRUCTION = "3";

    /**
     * 搬送状態 : 掬い完了
     */
    public static final String CMD_STATUS_PICKUP = "4";

    /**
     * 搬送状態 : 出庫完了
     */
    public static final String CMD_STATUS_COMP_RETRIEVAL = "5";

    /**
     * 搬送状態 : 到着
     */
    public static final String CMD_STATUS_ARRIVAL = "6";

    /**
     * 搬送状態 : 異常
     */
    public static final String CMD_STATUS_ERROR = "9";

    /**
     * 優先区分 : 緊急
     */
    public static final String PRIORITY_EMERGENCY = "1";

    /**
     * 優先区分 : 通常
     */
    public static final String PRIORITY_NORMAL = "2";

    /**
     * 優先区分 : 空棚確認
     */
    public static final String PRIORITY_CHECK_EMPTY = "9";

    /**
     * 再入庫フラグ : 同一棚に再入庫しない
     */
    public static final String RESTORING_FLAG_NOT_SAME_LOC = "0";

    /**
     * 再入庫フラグ : 同一棚に再入庫する
     */
    public static final String RESTORING_FLAG_SAME_LOC = "1";

    /**
     * 搬送区分 : 入庫
     */
    public static final String CARRY_FLAG_STORAGE = "1";

    /**
     * 搬送区分 : 出庫
     */
    public static final String CARRY_FLAG_RETRIEVAL = "2";

    /**
     * 搬送区分 : 直行
     */
    public static final String CARRY_FLAG_DIRECT_TRAVEL = "3";

    /**
     * 搬送区分 : 棚間移動
     */
    public static final String CARRY_FLAG_RACK_TO_RACK = "5";

    /**
     * 出庫指示詳細 : 在庫確認
     */
    public static final String RETRIEVAL_DETAIL_INVENTORY_CHECK = "0";

    /**
     * 出庫指示詳細 : ユニット出庫
     */
    public static final String RETRIEVAL_DETAIL_UNIT = "1";

    /**
     * 出庫指示詳細 : ピッキング出庫
     */
    public static final String RETRIEVAL_DETAIL_PICKING = "2";

    /**
     * 出庫指示詳細 : 積増入庫
     */
    public static final String RETRIEVAL_DETAIL_ADD_STORING = "3";

    /**
     * 出庫指示詳細 : 指定なし
     */
    public static final String RETRIEVAL_DETAIL_UNKNOWN = "9";

    /**
     * キャンセル要求区分 : 未要求
     */
    public static final String CANCEL_REQUEST_UNDEMAND = "0";

    /**
     * キャンセル要求区分 : データキャンセル要求中
     */
    public static final String CANCEL_REQUEST_CANCEL = "1";

    /**
     * キャンセル要求区分 : 引当解除要求中
     */
    public static final String CANCEL_REQUEST_RELEASE = "2";

    /**
     * キャンセル要求区分 : 在庫データ削除要求
     */
    public static final String CANCEL_REQUEST_DROP = "3";

    /**
     * 実績作成区分 : 在庫増減なし
     */
    public static final String RESULT_KIND_NO_VARIATION = "0";

    /**
     * 実績作成区分 : 入庫
     */
    public static final String RESULT_KIND_STORAGE = "1";

    /**
     * 実績作成区分 : 出庫
     */
    public static final String RESULT_KIND_RETRIEVAL = "2";

    /**
     * 実績作成区分 : メンテナンス増
     */
    public static final String RESULT_KIND_MAINTENANCE_PLUS = "3";

    /**
     * 実績作成区分 : メンテナンス減
     */
    public static final String RESULT_KIND_MAINTENANCE_MINUS = "4";

    /**

     /**
     * AS/RS稼動実績強制払い出し区分: 正常<br>
     * 正常以外は作業種別の値を使用します。
     */
    public static final String IN_OUT_RESULT_REMOVE_NORMAL = "00";

    /**
     * 在庫状態 : 入庫予約
     */
    public static final String PALLET_STATUS_STORAGE_PLAN = "1";

    /**
     * 在庫状態 : 実棚
     */
    public static final String PALLET_STATUS_REGULAR = "2";

    /**
     * 在庫状態 : 出庫予約
     */
    public static final String PALLET_STATUS_RETRIEVAL_PLAN = "3";

    /**
     * 在庫状態 : 出庫中
     */
    public static final String PALLET_STATUS_RETRIEVAL = "4";

    /**
     * 在庫状態 : 異常
     */
    public static final String PALLET_STATUS_IRREGULAR = "5";

    /**
     * 引当状態 : 未引当
     */
    public static final String ALLOCATION_FLAG_NOT_ALLOCATED = "0";

    /**
     * 引当状態 : 引当
     */
    public static final String ALLOCATION_FLAG_ALLOCATED = "1";

    /**
     * 空パレット状態 : 通常パレット
     */
    public static final String EMPTY_FLAG_NORMAL = "0";

    /**
     * 空パレット状態 : 空パレット
     */
    public static final String EMPTY_FLAG_EMPTY = "1";

    /**
     * 補充区分 : 補充なし
     */
    public static final String REPLENISHMENT_FLAG_OFF = "0";

    /**
     * 補充区分 : 補充あり
     */
    public static final String REPLENISHMENT_FLAG_ON = "1";

    /**
     * 補充区分 : 補充指示済
     */
    public static final String REPLENISHMENT_FLAG_INSTRUCTED = "2";


    /**
     * 取込データ区分 : 入荷予定
     */
    public static final String LOAD_DATA_TYPE_RECEVING = "01";

    /**
     * 取込データ区分 : 入庫予定
     */
    public static final String LOAD_DATA_TYPE_STORAGE = "02";

    /**
     * 取込データ区分 : 出庫予定
     */
    public static final String LOAD_DATA_TYPE_RETRIEVAL = "03";

    /**
     * 取込データ区分 : 仕分予定
     */
    public static final String LOAD_DATA_TYPE_SORTING = "04";

    /**
     * 取込データ区分 : 出荷予定
     */
    public static final String LOAD_DATA_TYPE_SHIPPING = "05";

    /**
     * 取込データ区分 : 荷主マスタ
     */
    public static final String LOAD_DATA_TYPE_MASTER_CONSIGNOR = "06";

    /**
     * 取込データ区分 : 仕入先マスタ
     */
    public static final String LOAD_DATA_TYPE_MASTER_SUPPLIER = "07";

    /**
     * 取込データ区分 : 出荷先マスタ
     */
    public static final String LOAD_DATA_TYPE_MASTER_CUSTOMER = "08";

    /**
     * 取込データ区分 : 商品マスタ
     */
    public static final String LOAD_DATA_TYPE_MASTER_ITEM = "09";

    /**
     * 取込データ区分 : 商品固定棚
     */
    public static final String LOAD_DATA_TYPE_FIXED_LOCATION = "10";

    /**
     * 取込データ区分 : ピッキングカート【出庫】
     */
    public static final String LOAD_DATA_TYPE_PICKINGRET = "31";

    /**
     * 取込データ区分 : PCT商品マスタ
     */
    public static final String LOAD_DATA_TYPE_MASTER_ITEM_PCT = "32";

    /**
     * 日次更新停止フラグ
     */
    public static final String DAILY_STOP = "0";

    /**
     * 日次更新起動フラグ
     */
    public static final String DAILY_START = "1";

    /**
     * 取込停止フラグ
     */
    public static final String LOAD_STOP = "0";

    /**
     * 取込起動フラグ
     */
    public static final String LOAD_START = "1";

    /**
     * 報告停止フラグ
     */
    public static final String REPORT_STOP = "0";

    /**
     * 報告起動フラグ
     */
    public static final String REPORT_START = "1";

    /**
     * エラーレベル : 警告
     */
    public static final String ERROR_LEVEL_WARNING = "1";

    /**
     * エラーレベル : 異常
     */
    public static final String ERROR_LEVEL_ERROR = "2";

    /**
     * エラー区分 : 取込可能行数超過
     */
    public static final String ERROR_FLAG_OVER_LINES = "001";

    /**
     * エラー区分 : 項目数(行サイズ)異常
     */
    public static final String ERROR_FLAG_ITEM_NUMBER_ERROR = "002";

    /**
     * エラー区分 : 必須項目空白
     */
    public static final String ERROR_FLAG_INDISPENSABLE_ITEM_BLANK = "003";

    /**
     * エラー区分 : 指定禁止項目に値あり
     */
    public static final String ERROR_FLAG_INPUT_PROHIBITION_ITEM = "004";

    /**
     * エラー区分 : マスタ未登録
     */
    public static final String ERROR_FLAG_MASTER_UNREGIST = "005";

    /**
     * エラー区分 : 禁止文字あり
     */
    public static final String ERROR_FLAG_PROHIBITION_CHARACTER = "006";

    /**
     * エラー区分 : 重複データあり
     */
    public static final String ERROR_FLAG_REPETITION_DATA = "007";

    /**
     * エラー区分 : 作業単位MAX件数超過
     */
    public static final String ERROR_FLAG_OVER_WORKING_UNIT_QTY = "008";

    /**
     * エラー区分 : 取消該当データ無し
     */
    public static final String ERROR_FLAG_NO_CANCELLATION_DATA = "009";

    /**
     * エラー区分 : 取消データ作業開始済み
     */
    public static final String ERROR_FLAG_CANCELLATION_DATA_STARTED = "010";

    /**
     * エラー区分 : データの値が不正
     */
    public static final String ERROR_FLAG_VALIDATE_ERROR = "011";

    /**
     * エラー区分 : 取消データ他端末作業中
     */
    public static final String ERROR_FLAG_CANCELLATION_DATA_LOCK = "012";

    /**
     * エラー区分 : データ使用中(削除不可)
     */
    public static final String ERROR_FLAG_USEED_MASTER_CODE = "013";

    /**
     * エラー区分 : 削除該当データ無し
     */
    public static final String ERROR_FLAG_NO_DELETE_DATA = "014";

    /**
     * エラー区分 : 削除不可データ
     */
    public static final String ERROR_FLAG_DELETE_NG_DATA = "015";

    /**
     * エラー区分 : 作業開始済バッチ
     */
    public static final String ERROR_FLAG_STARTEDBATCH = "016";

    /**
     * エラー区分 : 修正・削除不可データ
     */
    public static final String ERROR_FLAG_MODIFY_DELETE_NG_DATA = "017";

    /**
     * エラー区分 : ロット誤差重量 > バラ重量
     */
    public static final String WORNING_FLAG_WEIGHT_OVER = "101";

    /**
     * ダブルディープ区分 : シングルディープ
     */
    public static final String DOUBLE_DEEP_KIND_SINGLE = "0";

    /**
     * ダブルディープ区分 : ダブルディープ
     */
    public static final String DOUBLE_DEEP_KIND_DOUBLE = "1";

    /**<jp>
     * 機器状態を表すフィールド (未接続)
     </jp>*/
    /**<en>
     * Field for the machine state (disconnected)
     </en>*/
    public static final String MACHINE_STATE_DISCONNECT = "";

    /**<jp>
     * 機器状態を表すフィールド (運転中)
     </jp>*/
    /**<en>
     * Field for the machine state (active)
     </en>*/
    public static final String MACHINE_STATE_ACTIVE = "0";

    /**<jp>
     * 機器状態を表すフィールド (停止中)
     </jp>*/
    /**<en>
     * Field for the machine state (stopped)
     </en>*/
    public static final String MACHINE_STATE_STOP = "1";

    /**<jp>
     * 機器状態を表すフィールド (異常中)
     </jp>*/
    /**<en>
     * Field for the machine state (failed)
     </en>*/
    public static final String MACHINE_STATE_FAIL = "2";

    /**<jp>
     * 機器状態を表すフィールド (切離し)
     </jp>*/
    /**<en>
     * Field for the machine state (off-line)
     </en>*/
    public static final String MACHINE_STATE_OFFLINE = "3";
    
    /**<jp>
     * 機器種別を表すフィールド (RM)
     </jp>*/
    public static final String MACHINE_TYPE_RM = "11";

    /**<jp>
     * 機器種別を表すフィールド (MSS)
     </jp>*/
    public static final String MACHINE_TYPE_MSS = "15";

    /**<jp>
     * 機器種別を表すフィールド (CO)
     </jp>*/
    public static final String MACHINE_TYPE_CO = "21";

    /**<jp>
     * 機器種別を表すフィールド (MV)
     </jp>*/
    public static final String MACHINE_TYPE_MV = "31";

    /**<jp>
     * 機器種別を表すフィールド (STVS)
     </jp>*/
    public static final String MACHINE_TYPE_STVS = "54";

    /**<jp>
     * 機器種別を表すフィールド (STVL)
     </jp>*/
    public static final String MACHINE_TYPE_STVL = "55";
    
    /**
     * 手前、奥棚区分(手前)
     */
    public static final String BANK_SELECT_NEAR = "0";

    /**
     * 手前、奥棚区分(奥)
     */
    public static final String BANK_SELECT_FAR = "1";


    /**
     * バンク
     */
    public static final int BANK = 0;

    /**
     * ベイ
     */
    public static final int BAY = 1;

    /**
     * レベル
     */
    public static final int LEVEL = 2;



    /**
     * 欠品フラグ(通常欠品)
     */
    public static final String SHORTAGE_FLAG_SHORTAGE_NORMAL = "0";

    /**
     * 欠品フラグ(欠品完了)
     */
    public static final String SHORTAGE_FLAG_SHORTAGE_COMPLETE = "1";

    /**
     * 帳票出力なし
     */
    public static final String PRINT_NOOUTPUT = "0";

    /**
     * 帳票出力あり
     */
    public static final String PRINT_OUTPUT = "1";

    /**
     * 範囲指定なし
     */
    public static final String RANGE_FALSE = "0";

    /**
     * 範囲指定あり
     */
    public static final String RANGE_TRUE = "1";

    /**
     * 欠品完了なし
     */
    public static final String REPLISHMENT_NOUSE = "0";

    /**
     * 欠品完了あり
     */
    public static final String REPLISHMENT_USED = "1";

    /**
     * 全数指定なし
     */
    public static final String NOALL_RETRIEVAL = "0";

    /**
     * 全数指定
     */
    public static final String ALL_RETRIEVAL = "1";

    /**
     * TC/DC区分：DC
     */
    public static final String TCDC_FLAG_DC = "0";

    /**
     * TC/DC区分：TC
     */
    public static final String TCDC_FLAG_TC = "1";


    /**
     * バース状態フラグ : 未作業
     */
    public static final String BERTH_STATUS_FLAG_UNSTART = "0";

    /**
     * バース状態フラグ : 作業中
     */
    public static final String BERTH_STATUS_FLAG_NOWWORKING = "1";

    /**
     * バース状態フラグ : 完了
     */
    public static final String BERTH_STATUS_FLAG_COMPLETION = "4";

    /**
     * バース状態フラグ : 削除
     */
    public static final String BERTH_STATUS_FLAG_DELETE = "9";

    /**
     * 入荷フラグ : 入荷入庫以外
     */
    public static final String RECEIVING_FLAG_EXCEPT_RECEIVEING_STORAGE = "0";

    /**
     * 入荷フラグ : 入荷入庫
     */
    public static final String RECEIVING_FLAG_RECEIVEING_STORAGE = "1";

    /**
     * ランクNo.A
     */
    public static final String RANK_NO_A = "0";

    /**
     * ランクNo.B
     */
    public static final String RANK_NO_B = "1";

    /**
     * ランクNo.C
     */
    public static final String RANK_NO_C = "2";

    /**
     * 理由区分：未使用(デフォルト)
     */
    public static final int DEFAULT_REASON_TYPE = 0;

    /**
     * RFT作業状態フラグ : 未作業
     */
    public static final String RETRIEVAL_JOB_STATUS_FLAG_UNSTART = "0";

    /**
     * RFT作業状態フラグ : 作業中
     */
    public static final String RETRIEVAL_JOB_STATUS_FLAG_NOWWORKING = "1";

    /**
     * RFT作業状態フラグ : 完了
     */
    public static final String RETRIEVAL_JOB_STATUS_FLAG_COMPLETION = "4";

    /**
     * 作業区分詳細：TC入荷
     */
    public static final String JOB_DETAIL_TCRECEIVING = "1";

    /**
     * 作業区分詳細：DC入荷
     */
    public static final String JOB_DETAIL_DCRECEIVING = "2";

    /**
     * 作業区分詳細：入庫
     */
    public static final String JOB_DETAIL_STORAGE = "1";

    /**
     * 作業区分詳細：DC入庫
     */
    public static final String JOB_DETAIL_DCSTORAGE = "2";

    /**
     * 作業区分詳細：オーダー出庫
     */
    public static final String JOB_DETAIL_RETRIEVALORDER = "1";

    /**
     * 作業区分詳細 : 商品単位検品
     */
    public static final String JOB_DETAIL_ITEMSHIPPING = "1";

    /**
     * 作業区分詳細 : 出荷先単位検品
     */
    public static final String JOB_DETAIL_CUSTOMERSHIPPING = "2";

    /**
     * 作業区分詳細 : 積込
     */
    public static final String JOB_DETAIL_SHIPPINGLOAD = "3";

    /**
     * 作業区分詳細：その他
     */
    public static final String JOB_DETAIL_OTHER = "0";

    /**
     * レベルNo.A
     */
    public static final String LEVEL_NO_A = "0";

    /**
     * レベルNo.B
     */
    public static final String LEVEL_NO_B = "1";

    /**
     * レベルNo.C
     */
    public static final String LEVEL_NO_C = "2";

    /**
     * 完了区分(Pカート)
     */
    public static final String COMPLETE_KIND_PCART = "0";

    /**
     * 完了区分(Web)
     */
    public static final String COMPLETE_KIND_WEB = "1";

    /**
     * マスタ取込みフラグ(停止中)
     */
    public static final String PCTMASTER_LOAD_FLAG_STOP = "0";

    /**
     * マスタ取込みフラグ(セーブ中)
     */
    public static final String PCTMASTER_LOAD_FLAG_SAVE = "1";

    /**
     * マスタ取込みフラグ(ロード中)
     */
    public static final String PCTMASTER_LOAD_FLAG_LOAD = "2";

    /**
     * 作業優先度:高
     */
    public static final int JOB_PRIORITY_HIGH = 1;

    /**
     * 作業優先度:中
     */
    public static final int JOB_PRIORITY_INSIDE = 2;

    /**
     * 作業優先度:低
     */
    public static final int JOB_PRIORITY_LOW = 3;

    /**
     * 操作区分(ログ):設定
     */
    public static final String OPELOG_SETTING = "1";

    /**
     * 操作区分(ログ):登録
     */
    public static final String OPELOG_REGIST = "2";

    /**
     * 操作区分(ログ):修正
     */
    public static final String OPELOG_MODIFY = "3";

    /**
     * 操作区分(ログ):削除
     */
    public static final String OPELOG_DELETE = "4";

    /**
     * 操作区分(ログ):全削除
     */
    public static final String OPELOG_ALL_DELETE = "5";

    /**
     * 操作区分(ログ):キャンセル
     */
    public static final String OPELOG_CANCEL = "6";

    /**
     * 操作区分(ログ):印刷
     */
    public static final String OPELOG_PRINT = "11";

    /**
     * 操作区分(ログ):XLS
     */
    public static final String OPELOG_XLS = "12";

    /**
     * 操作区分(ログ):CSV
     */
    public static final String OPELOG_CSV = "13";

    /**
     * 操作区分(ログ):プレビュー
     */
    public static final String OPELOG_PRINT_LIST = "14";

    /**
     * 操作区分(ログ):自動取込
     */
    public static final String OPELOG_AUTO_LOADING = "21";

    /**
     * 操作区分(ログ):手動取込
     */
    public static final String OPELOG_MANUAL_LOADING = "22";

    /**
     * 操作区分(ログ):自動報告
     */
    public static final String OPELOG_AUTO_REPORT = "23";

    /**
     * 操作区分(ログ):手動報告
     */
    public static final String OPELOG_MANUAL_REPORT = "24";

    /**
     * ハードゾーン(フリー)
     */
    public static final String HARD_ZONE_FREE = "0";

    /**
     * 荷高(フリー)
     */
    public static final int HEIGHT_FREE = 0;

    /**
     * 荷幅(フリー)
     */
    public static final int WIDTH_FREE = 0;

    /**
     * ソフトゾーン(フリー)
     */
    public static final String SOFT_ZONE_FREE = "0";

    /**
     * ソフトゾーン(オール)
     */
    public static final String SOFT_ZONE_ALL = "000";
    
    /**
     * 棚使用フラグ : 使用可
     */
    public static final String LOCATION_USE_OK = "0";

    /**
     * 棚使用フラグ : 使用不可
     */
    public static final String LOCATION_USE_NG = "1";

    /**
     * 配置替え状態フラグ : 停止
     */
    public static final String REARRANGE_STATUS_FLAG_STOP = "0";

    /**
     * 配置替え状態フラグ : 開始
     */
    public static final String REARRANGE_STATUS_FLAG_START = "1";

    /**
     * 配置替え状態フラグ : 引当済
     */
    public static final String REARRANGE_STATUS_FLAG_ALLOCATED = "2";

    /**
     * 配置替え状態フラグ : 完了
     */
    public static final String REARRANGE_STATUS_FLAG_COMPLETION = "4";

    /**
     * 配置替え区分 : ソフトゾーン最適化
     */
    public static final String REARRANGE_TYPE_SOFTZONE = "1";

    /**
     * 配置替え区分 : 荷姿最適化
     */
    public static final String REARRANGE_TYPE_LOADSIZE = "2";

    /**
     * 配置替え区分 : 空棚最優先完了
     */
    public static final String REARRANGE_TYPE_VACANT = "3";

    /**
     * 最適化 : 最適化しない
     */
    public static final String OPTIMUM_OFF = "0";

    /**
     * 最適化 : 最適化する
     */
    public static final String OPTIMUM_ON = "1";

    /**
     * 到着報告：未送信
     */
    public static final String ARRIVAL_NOT_SEND = "0";

    /**
     * 到着報告：送信済み
     */
    public static final String ARRIVAL_SENDED = "1";

    /**
     * キーデータ：チェックON
     */
    public static final String KEYDATA_ON = "1";

    /**
     * キーデータ：チェックOFF
     */
    public static final String KEYDATA_OFF = "0";

    /**
     * 一時商品区分：通常
     */
    public static final String TEMPORARY_TYPE_NORMAL = "0";

    /**
     * 一時商品区分：一時商品
     */
    public static final String TEMPORARY_TYPE_TEMPORARY = "1";

    /**
     * 交換データ通信状態：正常完了
     */
    public static final String EXCHANGE_STATUS_NORMAL = "0";

    /**
     * 交換データ通信状態：同一ファイルを2度受信
     */
    public static final String EXCHANGE_STATUS_SAME_FILE = "1";

    /**
     * 交換データ通信状態：受信中にエラー発生
     */
    public static final String EXCHANGE_STATUS_RECEIVING_ERROR = "2";

    /**
     * 交換データ通信状態：不正データ受信
     */
    public static final String EXCHANGE_STATUS_VALIDATE_DATA = "3";

    /**
     * 交換データ通信状態：削除中にエラー発生
     */
    public static final String EXCHANGE_STATUS_REMOVE_ERROR = "4";

    /**
     * 送受信区分：送信
     */
    public static final String EXCHANGE_TYPE_SEND = "0";

    /**
     * 送受信区分：受信
     */
    public static final String EXCHANGE_TYPE_RECEIVE = "1";

    /**
     * データ種別 : 予定
     */
    public static final String DATA_PLAN = "0";

    /**
     * データ種別 : 予定外
     */
    public static final String DATA_NO_PLAN = "1";

    /**
     * データ種別 : 在庫
     */
    public static final String DATA_STOCK = "2";

    /**
     * データ種別 : マスタ
     */
    public static final String DATA_MASTER = "3";

    /**
     * データ種別 : ピッキング
     */
    public static final String DATA_PICKING = "4";

    /**
     * データ種別 : DB
     */
    public static final String DATA_DB = "5";

    /**
     * データ種別 : 予定外
     */
    public static final String REPORT_TYPE_PLAN_UNIT_SUM = "0";

    /**
     * データ種別 : 在庫
     */
    public static final String REPORT_TYPE_PLAN_UNIT_SPEC = "1";

    /**
     * データ種別 : マスタ
     */
    public static final String REPORT_TYPE_WORK_UNIT = "2";

    /**
     * データ区分 : クロスドック
     */
    public static final String DATA_TYPE_CROSSDOCK = "00";

    /**
     * データ区分 : 入荷予定
     */
    public static final String DATA_TYPE_RECEVING = "01";

    /**
     * データ区分 : 入庫予定
     */
    public static final String DATA_TYPE_STORAGE = "02";

    /**
     * データ区分 : 出庫予定
     */
    public static final String DATA_TYPE_RETRIEVAL = "03";

    /**
     * データ区分 : 仕分予定
     */
    public static final String DATA_TYPE_SORTING = "04";

    /**
     * データ区分 : 出荷予定
     */
    public static final String DATA_TYPE_SHIPPING = "05";

    /**
     * データ区分 : 荷主マスタ
     */
    public static final String DATA_TYPE_MASTER_CONSIGNOR = "06";

    /**
     * データ区分 : 仕入先マスタ
     */
    public static final String DATA_TYPE_MASTER_SUPPLIER = "07";

    /**
     * データ区分 : 出荷先マスタ
     */
    public static final String DATA_TYPE_MASTER_CUSTOMER = "08";

    /**
     * データ区分 : 商品マスタ
     */
    public static final String DATA_TYPE_MASTER_ITEM = "09";

    /**
     * データ区分 : 商品固定棚
     */
    public static final String DATA_TYPE_FIXED_LOCATION = "10";

    /**
     * データ区分 : 在庫
     */
    public static final String DATA_TYPE_STOCK = "11";
    
    /**
     * データ区分 : 予定外入出庫
     */
    public static final String DATA_TYPE_NO_PLAN_INOUT = "24";

    /**
     * 状態：ファイル名が不正
     */
    public static final String EXCHANGE_STATUS_SKIP = "1";

    /**
     * 状態：ファイル名が不正
     */
    public static final String EXCHANGE_STATUS_ALL_SKIP = "2";

    /**
     * 状態：ファイル名が不正
     */
    public static final String EXCHANGE_STATUS_FILE_INVALID = "11";

    /**
     * 状態：ファイル取得エラー
     */
    public static final String EXCHANGE_STATUS_FILE_NOT_FOUND = "12";

    /**
     * 状態：同一ファイル名二重受信
     */
    public static final String EXCHANGE_STATUS_FILE_EXISTS = "13";

    /**
     * 状態：受信データ内エラー
     */
    public static final String EXCHANGE_STATUS_RECEIVE_ERROR = "14";

    /**
     * 状態：ファイル削除中エラー
     */
    public static final String EXCHANGE_STATUS_FILE_DELETE_ERROR = "15";

    /**
     * 状態：例外発生
     */
    public static final String EXCHANGE_STATUS_EXCEPTION = "20";

    /**
     * リソースタイプ：WMS
     */
    public static final String RESOURCE_WMS = "02";

    /**
     * リソースタイプ：WCS
     */
    public static final String RESOURCE_WCS = "05";

    /**
     * リソースタイプ：WCS(製番改造)
     */
    public static final String RESOURCE_WCS_CUSTOM = "55";

    // Class method --------------------------------------------------
}
//end of class

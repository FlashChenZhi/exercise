package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;

/**
 * <CODE>AsrsInParameter</CODE>クラスは、AS/RSパッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスではAS/RSパッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>AsrsInParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     設定単位キー.<BR>
 *     作業No.<BR>
 *     状態フラグ<BR>
 *     完了フラグ<BR>
 *     中断中フラグ<BR>
 *     作業区分<BR>
 *     処理区分<BR>
 *     接続状態<BR>
 *     接続処理区分<BR>
 *     AGCNo.<BR>
 *     RMNo.<BR>
 *     倉庫No.<BR>
 *     搬送キー<BR>
 *     搬送状態<BR>
 *     現在作業モード<BR>
 *     作業モード切替要求<BR>
 *     モード切替種別<BR>
 *     予定エリアNo.<BR>
 *     エリアNo.<BR>
 *     バンクNo.<BR>
 *     ステーションNo.<BR>
 *     作業場<BR>
 *     棚<BR>
 *     棚(to)<BR>
 *     棚状態<BR>
 *     使用不可状態<BR>
 *     ゾーンNo.<BR>
 *     作業種別<BR>
 *     出庫指示詳細<BR>
 *     メンテナンス区分<BR>
 *     在庫ID<BR>
 *     入庫予定日<BR>
 *     出庫予定日<BR>
 *     バッチNo.<BR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *     商品コード<BR>
 *     商品コード(to)<BR>
 *     商品名称<BR>
 *     出荷先コード<BR>
 *     出荷先名称<BR>
 *     ロットNo.<BR>
 *     予定ロットNo.<BR>
 *     実績ロットNo.<BR>
 *     ケース入数<BR>
 *     ボール入数<BR>
 *     入庫数<BR>
 *     出庫数<BR>
 *     在庫数<BR>
 *     引当可能数<BR>
 *     引当可能ケース数<BR>
 *     引当可能ピース数<BR>
 *     予定数<BR>
 *     在庫ケース数<BR>
 *     在庫ピース数<BR>
 *     入庫ケース数<BR>
 *     入庫ピース数<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     ボールITF<BR>
 *     検索日時<BR>
 *     検索日時(to)<BR>
 *     検索日<BR>
 *     検索日(to)<BR>
 *     検索時刻<BR>
 *     検索時刻(to)<BR>
 *     表示開始日<BR>
 *     表示開始時間<BR>
 *     表示終了日<BR>
 *     表示終了時間<BR>
 *     入庫日<BR>
 *     入庫日(to)<BR>
 *     入庫時刻<BR>
 *     入庫日時<BR>
 *     最終出庫日<BR>
 *     リストセル行No.<BR>
 *     帳票発行フラグ<BR>
 *     検索対象テーブル<BR>
 *     最終更新日時<BR>
 *     棚状態：実棚チェックフラグ<BR>
 *     棚状態：空PB棚チェックフラグ<BR>
 *     棚状態：異常棚チェックフラグ<BR>
 *     オーダーNo.(to)<BR>
 *     範囲指定フラグ<BR>
 *     出庫棚範囲指定フラグ<BR>
 *     開始終了フラグ<BR>
 *     全数フラグ<BR>
 *     集約作業No<BR>
 *     システム接続キー<BR>
 *     出庫ステーションNo.<BR>
 *     入庫ステーションNo.<BR>
 *     パレットID<BR>
 *     理由区分<BR>
 *     理由名称<BR>
 * </DIR>
 *
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * @version $Revision: 6747 $, $Date: 2010-01-21 15:26:39 +0900 (木, 21 1 2010) $
 * @author  $Author: okayama $
 */
public class AsrsInParameter
        extends InParameter
{
    // Class variables -----------------------------------------------
    /**
     * 接続処理区分 : 作業開始
     */
    public static final String PROCESS_TYPE_WORK_START = "0";

    /**
     * 接続処理区分 : 通常終了
     */
    public static final String PROCESS_TYPE_WORK_END = "1";

    /**
     * 接続処理区分 : データ保存終了
     */
    public static final String PROCESS_TYPE_WORK_END_DATAKEEP = "2";

    /**
     * 接続処理区分 : 強制終了
     */
    public static final String PROCESS_TYPE_WORK_ONLYEND = "3";

    /**
     * 処理区分 : 未指示
     */
    public static final String PROCESS_STATUS_UNINSTRUCT = "0";

    /**
     * 処理区分 : 完了
     */
    public static final String PROCESS_STATUS_COMPLETE = "1";

    /**
     * 処理区分 : トラッキング削除
     */
    public static final String PROCESS_STATUS_TRACKING_DELETE = "2";

    /**
     * 処理区分：払出し完了
     */
    public static final String PROCESS_STATUS_COMPLETE_REMOVE = "3";

    /**
     * 処理区分 : 引当解除
     */
    public static final String PROCESS_STATUS_CANCEL_ALLOCATE = "4";

    /**
     * 処理区分 : 空出荷
     */
    public static final String PROCESS_STATUS_EMPTY_RETRIEVAL = "5";

    /**
     * 棚状態を表すフィールド 実棚
     */
    public static final String STATUS_STORAGED = "0";

    /**
     * 棚状態を表すフィールド 空棚
     */
    public static final String STATUS_EMPTY = "1";

    /**
     * 棚状態を表すフィールド 異常棚
     */
    public static final String STATUS_IRREGULAR = "2";

    /**
     * 棚状態を表すフィールド 作業棚
     */
    public static final String STATUS_WORK = "3";

    /**
     * 棚状態を表すフィールド 空パレット
     */
    public static final String STATUS_EMPTYPALLET = "4";

    /**
     * 棚状態を表すフィールド 禁止棚
     */
    public static final String STATUS_UNAVAILABLE = "5";

    /**
     * 棚状態を表すフィールド アクセス不可棚
     */
    public static final String STATUS_UNABLEACCESS = "6";

    /**
     * 登録
     */
    public static final String M_CREATE = "1";

    /**
     * 修正
     */
    public static final String M_MODIFY = "2";

    /**
     * 削除
     */
    public static final String M_DELETE = "3";

    /**
     * 検索
     */
    public static final String M_SEARCH = "4";

    /**
     * 設定単位キー
     */
    private String _settingUnitKey;

    /**
     * 作業No.
     */
    private String _jobNo;

    /**
     * 状態フラグ
     */
    private String _statusFlag;

    /**
     * 完了フラグ
     */
    private String _completionFlag;

    /**
     * 中断中フラグ
     */
    private String _suspend;

    /**
     * 作業区分
     */
    private String _jobType;

    /**
     * 処理区分
     */
    private String _processStatus;

    /**
     * 接続状態
     */
    private String _gcStatusFlag;

    /**
     * 接続処理区分
     */
    private String _processType;

    /**
     * AGCNo.
     */
    private String _agcNo;

    /**
     * RMNo.
     */
    private String _rmNo;

    /**
     * 倉庫No.
     */
    private String _warehouseNo;

    /**
     * 搬送キー
     */
    private String _carryKey;

    /**
     * 搬送状態
     */
    private String _carryFlag;

    /**
     * 現在作業モード
     */
    private String _currentMode;

    /**
     * 設定作業モード(プルダウンで選択した作業モード)
     */
    private String _settingCurrentMode;

    /**
     * 作業モード切替要求
     */
    private String _modeRequest;

    /**
     * モード切替種別
     */
    private String _modeType;

    /**
     * 動作モード設定ラジオボタン選択値
     */
    private String _rdoSelectModeType;

    /**
     * 作業モード設定ラジオボタン選択値
     */
    private String _rdoSelectWorkMode;

    /**
     * 予定エリアNo.
     */
    private String _planAreaNo;

    /**
     * エリアNo.
     */
    private String _areaNo;

    /**
     * バンクNo.
     */
    private int _bankNo;

    /**
     * ステーションNo.
     */
    private String _stationNo;

    /**
     * ステーションNo.(配列)
     */
    private String[] _stationNoArray;

    /**
     * ステーション名称
     */
    private String _stationName;

    /**
     * 倉庫ステーションNo.
     */
    private String _whStationNo;

    /**
     * 作業場
     */
    private String _workplace;

    /**
     * 棚
     */
    private String _location;

    /**
     * 棚(to)
     */
    private String _toLocation;

    /**
     * 棚状態
     */
    private String _locationStatus;

    /**
     * 使用不可状態
     */
    private String _prohibitionFlag;

    /**
     * ソフトゾーンNo.
     */
    private String _softZoneNo;

    /**
     * ハードゾーンNo.
     */
    private String _zoneNo;

    /**
     * 荷幅
     */
    private int _width;

    /**
     * 作業種別
     */
    private String _workType;

    /**
     * 出庫指示詳細
     */
    private String _retrievalDetail;

    /**
     * メンテナンス区分
     */
    private String _maintenanceType;

    /**
     * 在庫ID
     */
    private String _stockId;

    /**
     * 入庫予定日
     */
    private String _storagePlanDay;

    /**
     * 出庫予定日
     */
    private String _retrievalPlanDay;

    /**
     * バッチNo.
     */
    private String _batchNo;

    /**
     * 荷主コード
     */
    private String _consignorCode;

    /**
     * 荷主名称
     */
    private String _consignorName;

    /**
     * 商品コード
     */
    private String _itemCode;

    /**
     * 商品コード(to)
     */
    private String _toItemCode;

    /**
     * 商品名称
     */
    private String _itemName;

    /**
     * 出荷先コード
     */
    private String _customerCode;

    /**
     * 出荷先名称
     */
    private String _customerName;

    /**
     * ロットNo.
     */
    private String _lotNo;

    /**
     * 予定ロットNo.
     */
    private String _planLotNo;

    /**
     * 実績ロットNo.
     */
    private String _resultLotNo;

    /**
     * ケース入数
     */
    private int _enteringQty;

    /**
     * ボール入数
     */
    private int _bundleEnteringQty;

    /**
     * 入庫数
     */
    private int _storageQty;

    /**
     * 出庫数
     */
    private int _retrievalQty;

    /**
     * 出庫ケース数
     */
    private int _retrievalCaseQty;

    /**
     * 出庫ピース数
     */
    private int _retrievalPieceQty;

    /**
     * 在庫数
     */
    private long _stockQty;

    /**
     * 引当可能数
     */
    private long _allocateQty;

    /**
     * 引当可能ケース数
     */
    private int _allocCaseQty;

    /**
     * 引当可能ピース数
     */
    private int _allocPieceQty;

    /**
     * 予定数
     */
    private int _planQty;

    /**
     * 在庫ケース数
     */
    private int _stockCaseQty;

    /**
     * 在庫ピース数
     */
    private int _stockPieceQty;

    /**
     * 入庫ケース数
     */
    private int _storageCaseQty;

    /**
     * 入庫ピース数
     */
    private int _storagePieceQty;

    /**
     * JANコード
     */
    private String _janCode;

    /**
     * ケースITF
     */
    private String _itf;

    /**
     * ボールITF
     */
    private String _bundleItf;

    /**
     * 検索日時
     */
    private java.util.Date _searchDate;

    /**
     * 検索日時(to)
     */
    private java.util.Date _toSearchDate;

    /**
     * 検索日
     */
    private String _searchDay;

    /**
     * 検索日(to)
     */
    private String _toSearchDay;

    /**
     * 検索時刻
     */
    private String _searchTime;

    /**
     * 検索時刻(to)
     */
    private String _toSearchTime;

    /**
     * 表示用開始日
     */
    private String _dispDayFrom;

    /**
     * 表示用開始時間
     */
    private String _dispTimeFrom;

    /**
     * 表示用終了日
     */
    private String _dispDayTo;

    /**
     * 表示用終了時間
     */
    private String _dispTimeTo;

    /**
     * 入庫日
     */
    private String _storageDay;

    /**
     * 入庫日(to)
     */
    private String _toStorageDay;

    /**
     * 入庫時刻
     */
    private String _storageTime;

    /**
     * 入庫日時
     */
    private java.util.Date _storageDate;

    /**
     * 作業時間
     */
    private int _workSeconds;

    /**
     * 最終出庫日
     */
    private String _retrievalDay;

    /**
     * リストセル行No.
     */
    private int _rowNo;

    /**
     * 帳票発行フラグ
     */
    private boolean _printFlag;

    /**
     * 検索対象テーブル
     */
    private String _searchTable;

    /**
     * 最終更新日時
     */
    private java.util.Date _lastUpdateDate;

    /**
     * オーダーNo.
     */
    private String _orderNo;

    /**
     * 棚状態：空棚チェックフラグ
     */
    private boolean _emptyLocationFlag;

    /**
     * 棚状態：実棚チェックフラグ
     */
    private boolean _storedLocationFlag;

    /**
     * 棚状態：空PB棚チェックフラグ
     */
    private boolean _emptyPBLocationFlag;

    /**
     * 棚状態：異常棚チェックフラグ
     */
    private boolean _errorLocationFlag;

    /**
     * オーダーNo. (to)
     */
    private String _toOrderNo;

    /**
     * 範囲指定フラグ
     */
    private boolean _rangeFlag;

    /**
     * 出庫棚範囲指定フラグ
     */
    private boolean _retrievalLocRangeFlag;

    /**
     * 開始終了フラグ
     */
    private String _fromtoFrag;

    /**
     * 全数フラグ
     */
    private boolean _allQtyFlag;

    /**
     * 作業表示運用
     */
    private String _operationDisp;

    /**
     * 作業No.(搬送情報用)
     */
    private String _workNo;

    /**
     * 集約作業No.
     */
    private String _collectJobNo;

    /**
     * システム接続キー
     */
    private String _systemConnKey;

    /**
     * 出庫ステーションNo.
     */
    private String _retrievalStationNo;

    /**
     * 入庫ステーションNo.
     */
    private String _storageStationNo;

    /**
     * パレットID
     */
    private String _palletId;

    /**
     * 前搬送エラーフラグ
     */
    private boolean _errorAllocCarry;

    /**
     * 理由区分
     */
    private int _reasonType;

    /**
     * 理由名称
     */
    private String _reasonName;

    /**
     * 最適化：ソフトゾーン最適化チェックフラグ
     */
    private boolean _softZoneOptimum;

    /**
     * 最適化：荷姿最適化チェックフラグ
     */
    private boolean _loadSizeOptimum;

    /**
     * 最適化：空棚最適化チェックフラグ
     */
    private boolean _vacantOptimum;

    /**
     * 優先区分
     */
    private String _priorityType;

    /**
     * 選択チェック
     */
    private boolean _selected;

    // Constractor --------------------------------------------------
    /**
     * コンストラクタ <BR>
     * WmsUserInfoを指定します。
     * 
     * @param info WmsUserInfo
     */
    public AsrsInParameter(WmsUserInfo info)
    {
        super();
        setWmsUserInfo(info);
    }

    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 6747 $,$Date: 2010-01-21 15:26:39 +0900 (木, 21 1 2010) $");
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
     * 状態フラグを返します。
     * @return 状態フラグを返します。
     */
    public String getStatusFlag()
    {
        return _statusFlag;
    }

    /**
     * 状態フラグを設定します。
     * @param statusFlag 状態フラグ
     */
    public void setStatusFlag(String statusFlag)
    {
        _statusFlag = statusFlag;
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
     * 中断中フラグを返します。
     * @return 中断中フラグを返します。
     */
    public String getSuspend()
    {
        return _suspend;
    }

    /**
     * 中断中フラグを設定します。
     * @param suspend 中断中フラグ
     */
    public void setSuspend(String suspend)
    {
        _suspend = suspend;
    }

    /**
     * 作業区分を返します。
     * @return 作業区分を返します。
     */
    public String getJobType()
    {
        return _jobType;
    }

    /**
     * 作業区分を設定します。
     * @param jobType 作業区分
     */
    public void setJobType(String jobType)
    {
        _jobType = jobType;
    }

    /**
     * 処理区分を返します。
     * @return 処理区分を返します。
     */
    public String getProcessStatus()
    {
        return _processStatus;
    }

    /**
     * 処理区分を設定します。
     * @param processStatus 処理区分
     */
    public void setProcessStatus(String processStatus)
    {
        _processStatus = processStatus;
    }

    /**
     * 接続状態を返します。
     * @return 接続状態を返します。
     */
    public String getGCStatusFlag()
    {
        return _gcStatusFlag;
    }

    /**
     * 接続状態を設定します。
     * @param gcStatusFlag 接続状態
     */
    public void setGCStatusFlag(String gcStatusFlag)
    {
        _gcStatusFlag = gcStatusFlag;
    }

    /**
     * 接続処理区分を返します。
     * @return 接続処理区分を返します。
     */
    public String getProcessType()
    {
        return _processType;
    }

    /**
     * 接続処理区分を設定します。
     * @param processType 接続処理区分
     */
    public void setProcessType(String processType)
    {
        _processType = processType;
    }

    /**
     * AGCNo.を返します。
     * @return AGCNo.を返します。
     */
    public String getAgcNo()
    {
        return _agcNo;
    }

    /**
     * AGCNo.を設定します。
     * @param agcNo AGCNo.
     */
    public void setAgcNo(String agcNo)
    {
        _agcNo = agcNo;
    }

    /**
     * RMNo.を返します。
     * @return RMNo.を返します。
     */
    public String getRmNo()
    {
        return _rmNo;
    }

    /**
     * RMNo.を設定します。
     * @param rmNo RMNo.
     */
    public void setRmNo(String rmNo)
    {
        _rmNo = rmNo;
    }

    /**
     * 倉庫No.を返します。
     * @return 倉庫No.を返します。
     */
    public String getWarehouseNo()
    {
        return _warehouseNo;
    }

    /**
     * 倉庫No.を設定します。
     * @param warehouseNo 倉庫No.
     */
    public void setWarehouseNo(String warehouseNo)
    {
        _warehouseNo = warehouseNo;
    }

    /**
     * 搬送キーを返します。
     * @return 搬送キーを返します。
     */
    public String getCarryKey()
    {
        return _carryKey;
    }

    /**
     * 搬送キーを設定します。
     * @param carryKey 搬送キー
     */
    public void setCarryKey(String carryKey)
    {
        _carryKey = carryKey;
    }

    /**
     * 搬送状態を返します。
     * @return 搬送状態を返します。
     */
    public String getCarryFlag()
    {
        return _carryFlag;
    }

    /**
     * 搬送状態を設定します。
     * @param carryFlag 搬送状態
     */
    public void setCarryFlag(String carryFlag)
    {
        _carryFlag = carryFlag;
    }

    /**
     * 現在作業モードを返します。
     * @return 現在作業モードを返します。
     */
    public String getCurrentMode()
    {
        return _currentMode;
    }

    /**
     * 現在作業モードを設定します。
     * @param currentMode 現在作業モード
     */
    public void setCurrentMode(String currentMode)
    {
        _currentMode = currentMode;
    }

    /**
     * 設定作業モード(プルダウンで選択した作業モード)を返します。
     * @return 設定作業モードを返します。
     */
    public String getSettingCurrentMode()
    {
        return _settingCurrentMode;
    }

    /**
     * 設定作業モード(プルダウンで選択した作業モード)を設定します。
     * @param settingCurrentMode 設定作業モード
     */
    public void setSettingCurrentMode(String settingCurrentMode)
    {
        _settingCurrentMode = settingCurrentMode;
    }

    /**
     * 作業モード切替要求を返します。
     * @return 作業モード切替要求を返します。
     */
    public String getModeRequest()
    {
        return _modeRequest;
    }

    /**
     * 作業モード切替要求を設定します。
     * @param modeRequest 作業モード切替要求
     */
    public void setModeRequest(String modeRequest)
    {
        _modeRequest = modeRequest;
    }

    /**
     * モード切替種別を返します。
     * @return モード切替種別を返します。
     */
    public String getModeType()
    {
        return _modeType;
    }

    /**
     * モード切替種別を設定します。
     * @param modeType モード切替種別
     */
    public void setModeType(String modeType)
    {
        _modeType = modeType;
    }

    /**
     * 動作モード設定ラジオボタン選択値を返します。
     * @return 動作モード設定ラジオボタン選択値を返します。
     */
    public String getRdoSelectModeType()
    {
        return _rdoSelectModeType;
    }

    /**
     * 動作モード設定ラジオボタン選択値を設定します。
     * @param rdoSelectModeType 動作モード設定ラジオボタン選択値
     */
    public void setRdoSelectModeType(String rdoSelectModeType)
    {
        _rdoSelectModeType = rdoSelectModeType;
    }

    /**
     * 作業モード設定ラジオボタン選択値を返します。
     * @return 作業モード設定ラジオボタン選択値を返します。
     */
    public String getRdoSelectWorkMode()
    {
        return _rdoSelectWorkMode;
    }

    /**
     * 作業モード設定ラジオボタン選択値を設定します。
     * @param rdoSelectWorkMode 作業モード設定ラジオボタン選択値
     */
    public void setRdoSelectWorkMode(String rdoSelectWorkMode)
    {
        _rdoSelectWorkMode = rdoSelectWorkMode;
    }

    /**
     * 予定エリアNo.を返します。
     * @return 予定エリアNo.を返します。
     */
    public String getPlanAreaNo()
    {
        return _planAreaNo;
    }

    /**
     * 予定エリアNo.を設定します。
     * @param planAreaNo 予定エリアNo.
     */
    public void setPlanAreaNo(String planAreaNo)
    {
        _planAreaNo = planAreaNo;
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
     * バンクNo.を返します。
     * @return バンクNo.を返します。
     */
    public int getBankNo()
    {
        return _bankNo;
    }

    /**
     * バンクNo.を設定します。
     * @param bankNo バンクNo.
     */
    public void setBankNo(int bankNo)
    {
        _bankNo = bankNo;
    }

    /**
     * ステーションNo.を返します。
     * @return ステーションNo.を返します。
     */
    public String getStationNo()
    {
        return _stationNo;
    }

    /**
     * ステーションNo.を設定します。
     * @param stationNo ステーションNo.
     */
    public void setStationNo(String stationNo)
    {
        _stationNo = stationNo;
    }

    /**
     * ステーションNo.(配列)を返します。
     * @return ステーションNo.(配列)を返します。
     */
    public String[] getStationNoArray()
    {
        return _stationNoArray;
    }

    /**
     * ステーションNo.(配列)を設定します。
     * @param stationNoArray ステーションNo.(配列)
     */
    public void setStationNoArray(String[] stationNoArray)
    {
        _stationNoArray = stationNoArray;
    }

    /**
     * ステーション名称を返します。
     * @return ステーション名称を返します。
     */
    public String getStationName()
    {
        return _stationName;
    }

    /**
     * ステーション名称を設定します。
     * @param stationName ステーション名称
     */
    public void setStationName(String stationName)
    {
        _stationName = stationName;
    }

    /**
     * 倉庫ステーションNo.を返します。
     * @return 倉庫ステーションNo.を返します。
     */
    public String getWhStationNo()
    {
        return _whStationNo;
    }

    /**
     * 倉庫ステーションNo.を設定します。
     * @param whStationNo 倉庫ステーションNo.
     */
    public void setWhStationNo(String whStationNo)
    {
        _whStationNo = whStationNo;
    }

    /**
     * 作業場を返します。
     * @return 作業場を返します。
     */
    public String getWorkplace()
    {
        return _workplace;
    }

    /**
     * 作業場を設定します。
     * @param workplace 作業場
     */
    public void setWorkplace(String workplace)
    {
        _workplace = workplace;
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
     * 棚(to)を返します。
     * @return 棚(to)を返します。
     */
    public String getToLocation()
    {
        return _toLocation;
    }

    /**
     * 棚(to)を設定します。
     * @param toLocation 棚(to)
     */
    public void setToLocation(String toLocation)
    {
        _toLocation = toLocation;
    }

    /**
     * 棚状態を返します。
     * @return 棚状態を返します。
     */
    public String getLocationStatus()
    {
        return _locationStatus;
    }

    /**
     * 棚状態を設定します。
     * @param locationStatus 棚状態
     */
    public void setLocationStatus(String locationStatus)
    {
        _locationStatus = locationStatus;
    }

    /**
     * 使用不可状態を返します。
     * @return 使用不可状態を返します。
     */
    public String getProhibitionFlag()
    {
        return _prohibitionFlag;
    }

    /**
     * 使用不可状態を設定します。
     * @param prohibitionFlag 使用不可状態
     */
    public void setProhibitionFlag(String prohibitionFlag)
    {
        _prohibitionFlag = prohibitionFlag;
    }

    /**
     * ソフトゾーンNo.を返します。
     * @return ソフトゾーンNo.を返します。
     */
    public String getSoftZoneNo()
    {
        return _softZoneNo;
    }

    /**
     * ソフトゾーンNo.を設定します。
     * @param zoneNo ソフトゾーンNo.
     */
    public void setSoftZoneNo(String zoneNo)
    {
        _softZoneNo = zoneNo;
    }

    /**
     * ハードゾーンNo.を返します。
     * @return ハードゾーンNo.を返します。
     */
    public String getZoneNo()
    {
        return _zoneNo;
    }

    /**
     * ハードゾーンNo.を設定します。
     * @param zoneNo ハードゾーンNo.
     */
    public void setZoneNo(String zoneNo)
    {
        _zoneNo = zoneNo;
    }

    /**
     * 荷幅を返します。
     * @return 荷幅を返します。
     */
    public int getWidth()
    {
        return _width;
    }

    /**
     * 荷幅を設定します。
     * @param width 荷幅
     */
    public void setWidth(int width)
    {
        _width = width;
    }

    /**
     * 作業種別を返します。
     * @return 作業種別を返します。
     */
    public String getWorkType()
    {
        return _workType;
    }

    /**
     * 作業種別を設定します。
     * @param workType 作業種別
     */
    public void setWorkType(String workType)
    {
        _workType = workType;
    }

    /**
     * 出庫指示詳細を返します。
     * @return 出庫指示詳細を返します。
     */
    public String getRetrievalDetail()
    {
        return _retrievalDetail;
    }

    /**
     * 出庫指示詳細を設定します。
     * @param retrievalDetail 出庫指示詳細
     */
    public void setRetrievalDetail(String retrievalDetail)
    {
        _retrievalDetail = retrievalDetail;
    }

    /**
     * メンテナンス区分を返します。
     * @return メンテナンス区分を返します。
     */
    public String getMaintenanceType()
    {
        return _maintenanceType;
    }

    /**
     * メンテナンス区分を設定します。
     * @param maintenanceType メンテナンス区分
     */
    public void setMaintenanceType(String maintenanceType)
    {
        _maintenanceType = maintenanceType;
    }

    /**
     * 在庫IDを返します。
     * @return 在庫IDを返します。
     */
    public String getStockId()
    {
        return _stockId;
    }

    /**
     * 在庫IDを設定します。
     * @param stockId 在庫ID
     */
    public void setStockId(String stockId)
    {
        _stockId = stockId;
    }

    /**
     * 入庫予定日を返します。
     * @return 入庫予定日を返します。
     */
    public String getStoragePlanDay()
    {
        return _storagePlanDay;
    }

    /**
     * 入庫予定日を設定します。
     * @param storagePlanDay 入庫予定日
     */
    public void setStoragePlanDay(String storagePlanDay)
    {
        _storagePlanDay = storagePlanDay;
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
     * バッチNo.を返します。
     * @return バッチNo.を返します。
     */
    public String getBatchNo()
    {
        return _batchNo;
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
     * 荷主コードを返します。
     * @return 荷主コードを返します。
     */
    public String getConsignorCode()
    {
        return _consignorCode;
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
     * 荷主名称を返します。
     * @return 荷主名称を返します。
     */
    public String getConsignorName()
    {
        return _consignorName;
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
     * 商品コード(to)を返します。
     * @return 商品コード(to)を返します。
     */
    public String getToItemCode()
    {
        return _toItemCode;
    }

    /**
     * 商品コード(to)を設定します。
     * @param toItemCode 商品コード(to)
     */
    public void setToItemCode(String toItemCode)
    {
        _toItemCode = toItemCode;
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
     * 出荷先コードを返します。
     * @return 出荷先コードを返します。
     */
    public String getCustomerCode()
    {
        return _customerCode;
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
     * 出荷先名称を返します。
     * @return 出荷先名称を返します。
     */
    public String getCustomerName()
    {
        return _customerName;
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
     * 予定ロットNo.を返します。
     * @return 予定ロットNo.を返します。
     */
    public String getPlanLotNo()
    {
        return _planLotNo;
    }

    /**
     * 予定ロットNo.を設定します。
     * @param planLotNo 予定ロットNo.
     */
    public void setPlanLotNo(String planLotNo)
    {
        _planLotNo = planLotNo;
    }

    /**
     * 実績ロットNo.を返します。
     * @return 実績ロットNo.を返します。
     */
    public String getResultLotNo()
    {
        return _resultLotNo;
    }

    /**
     * 実績ロットNo.を設定します。
     * @param resultLotNo 実績ロットNo.
     */
    public void setResultLotNo(String resultLotNo)
    {
        _resultLotNo = resultLotNo;
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
     * 入庫数を返します。
     * @return 入庫数を返します。
     */
    public int getStorageQty()
    {
        return _storageQty;
    }

    /**
     * 入庫数を設定します。
     * @param storageQty 入庫数
     */
    public void setStorageQty(int storageQty)
    {
        _storageQty = storageQty;
    }

    /**
     * 出庫数を返します。
     * @return 出庫数を返します。
     */
    public int getRetrievalQty()
    {
        return _retrievalQty;
    }

    /**
     * 出庫数を設定します。
     * @param retrievalQty 出庫数
     */
    public void setRetrievalQty(int retrievalQty)
    {
        _retrievalQty = retrievalQty;
    }

    /**
     * 出庫ケース数を返します。
     * @return 出庫ケース数を返します。
     */
    public int getRetrievalCaseQty()
    {
        return _retrievalCaseQty;
    }

    /**
     * 出庫ケース数を設定します。
     * @param retrievalCaseQty 出庫ケース数
     */
    public void setRetrievalCaseQty(int retrievalCaseQty)
    {
        _retrievalCaseQty = retrievalCaseQty;
    }

    /**
     * 出庫ピース数を返します。
     * @return 出庫ピース数を返します。
     */
    public int getRetrievalPieceQty()
    {
        return _retrievalPieceQty;
    }

    /**
     * 引当可能ピース数を設定します。
     * @param retrievalPieceQty 引当可能ピース数
     */
    public void setRetrievalPieceQty(int retrievalPieceQty)
    {
        _retrievalPieceQty = retrievalPieceQty;
    }

    /**
     * 在庫数を返します。
     * @return 在庫数を返します。
     */
    public long getStockQty()
    {
        return _stockQty;
    }

    /**
     * 在庫数を設定します。
     * @param stockQty 在庫数
     */
    public void setStockQty(long stockQty)
    {
        _stockQty = stockQty;
    }

    /**
     * 引当可能数を返します。
     * @return 引当可能数を返します。
     */
    public long getAllocateQty()
    {
        return _allocateQty;
    }

    /**
     * 引当可能数を設定します。
     * @param allocateQty 引当可能数
     */
    public void setAllocateQty(long allocateQty)
    {
        _allocateQty = allocateQty;
    }

    /**
     * 引当可能ケース数を返します。
     * @return 引当可能ケース数を返します。
     */
    public int getAllocCaseQty()
    {
        return _allocCaseQty;
    }

    /**
     * 引当可能ケース数を設定します。
     * @param allocCaseQty 引当可能ケース数
     */
    public void setAllocCaseQty(int allocCaseQty)
    {
        _allocCaseQty = allocCaseQty;
    }

    /**
     * 引当可能ピース数を返します。
     * @return 引当可能ピース数を返します。
     */
    public int getAllocPieceQty()
    {
        return _allocPieceQty;
    }

    /**
     * 引当可能ピース数を設定します。
     * @param allocPieceQty 引当可能ピース数
     */
    public void setAllocPieceQty(int allocPieceQty)
    {
        _allocPieceQty = allocPieceQty;
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
     * 在庫ケース数を返します。
     * @return 在庫ケース数を返します。
     */
    public int getStockCaseQty()
    {
        return _stockCaseQty;
    }

    /**
     * 在庫ケース数を設定します。
     * @param stockCaseQty 在庫ケース数
     */
    public void setStockCaseQty(int stockCaseQty)
    {
        _stockCaseQty = stockCaseQty;
    }

    /**
     * 在庫ピース数を返します。
     * @return 在庫ピース数を返します。
     */
    public int getStockPieceQty()
    {
        return _stockPieceQty;
    }

    /**
     * 在庫ピース数を設定します。
     * @param stockPieceQty 在庫ピース数
     */
    public void setStockPieceQty(int stockPieceQty)
    {
        _stockPieceQty = stockPieceQty;
    }

    /**
     * 入庫ケース数を返します。
     * @return 入庫ケース数を返します。
     */
    public int getStorageCaseQty()
    {
        return _storageCaseQty;
    }

    /**
     * 入庫ケース数を設定します。
     * @param storageCaseQty 入庫ケース数
     */
    public void setStorageCaseQty(int storageCaseQty)
    {
        _storageCaseQty = storageCaseQty;
    }

    /**
     * 入庫ピース数を返します。
     * @return 入庫ピース数を返します。
     */
    public int getStoragePieceQty()
    {
        return _storagePieceQty;
    }

    /**
     * 入庫ピース数を設定します。
     * @param storagePieceQty 入庫ピース数
     */
    public void setStoragePieceQty(int storagePieceQty)
    {
        _storagePieceQty = storagePieceQty;
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
     * 検索日を返します。
     * @return 検索日を返します。
     */
    public String getSearchDay()
    {
        return _searchDay;
    }

    /**
     * 検索日時を返します。
     * @return 検索日時を返します。
     */
    public java.util.Date getSearchDate()
    {
        return _searchDate;
    }

    /**
     * 検索日時を設定します。
     * @param searchDate 検索日時
     */
    public void setSearchDate(java.util.Date searchDate)
    {
        _searchDate = searchDate;
    }

    /**
     * 検索日時(to)を返します。
     * @return 検索日時(to)を返します。
     */
    public java.util.Date getToSearchDate()
    {
        return _toSearchDate;
    }

    /**
     * 検索日時(to)を設定します。
     * @param toSearchDate 検索日時(to)
     */
    public void setToSearchDate(java.util.Date toSearchDate)
    {
        _toSearchDate = toSearchDate;
    }

    /**
     * 検索日を設定します。
     * @param searchDay 検索日
     */
    public void setSearchDay(String searchDay)
    {
        _searchDay = searchDay;
    }

    /**
     * 検索日(to)を返します。
     * @return 検索日(to)を返します。
     */
    public String getToSearchDay()
    {
        return _toSearchDay;
    }

    /**
     * 検索日(to)を設定します。
     * @param toSearchDay 検索日(to)
     */
    public void setToSearchDay(String toSearchDay)
    {
        _toSearchDay = toSearchDay;
    }

    /**
     * 検索時刻を返します。
     * @return 検索時刻を返します。
     */
    public String getSearchTime()
    {
        return _searchTime;
    }

    /**
     * 検索時刻を設定します。
     * @param searchTime 検索時刻
     */
    public void setSearchTime(String searchTime)
    {
        _searchTime = searchTime;
    }

    /**
     * 検索時刻(to)を返します。
     * @return 検索時刻(to)を返します。
     */
    public String getToSearchTime()
    {
        return _toSearchTime;
    }

    /**
     * 検索時刻(to)を設定します。
     * @param toSearchTime 検索時刻(to)
     */
    public void setToSearchTime(String toSearchTime)
    {
        _toSearchTime = toSearchTime;
    }

    /**
     * 表示用開始日を返します。
     * @return 表示用開始日を返します。
     */
    public String getDispDayFrom()
    {
        return _dispDayFrom;
    }

    /**
     * 表示用開始日を設定します。
     * @param dispDayFrom 表示用開始日
     */
    public void setDispDayFrom(String dispDayFrom)
    {
        _dispDayFrom = dispDayFrom;
    }

    /**
     * 表示用終了日を返します。
     * @return 表示用終了日を返します。
     */
    public String getDispDayTo()
    {
        return _dispDayTo;
    }

    /**
     * 表示用終了日を設定します。
     * @param dispDayTo 表示用終了日
     */
    public void setDispDayTo(String dispDayTo)
    {
        _dispDayTo = dispDayTo;
    }

    /**
     * 表示用開始時間を返します。
     * @return 表示用開始時間を返します。
     */
    public String getDispTimeFrom()
    {
        return _dispTimeFrom;
    }

    /**
     * 表示用開始時間を設定します。
     * @param dispTimeFrom 表示用開始時間
     */
    public void setDispTimeFrom(String dispTimeFrom)
    {
        _dispTimeFrom = dispTimeFrom;
    }

    /**
     * 表示用終了時間を返します。
     * @return 表示用終了時間を返します。
     */
    public String getDispTimeTo()
    {
        return _dispTimeTo;
    }

    /**
     * 表示用終了時間を設定します。
     * @param dispTimeTo 表示用終了時間
     */
    public void setDispTimeTo(String dispTimeTo)
    {
        _dispTimeTo = dispTimeTo;
    }

    /**
     * 入庫日を返します。
     * @return 入庫日を返します。
     */
    public String getStorageDay()
    {
        return _storageDay;
    }

    /**
     * 入庫日を設定します。
     * @param storageDay 入庫日
     */
    public void setStorageDay(String storageDay)
    {
        _storageDay = storageDay;
    }

    /**
     * 入庫日(to)を返します。
     * @return 入庫日(to)を返します。
     */
    public String getToStorageDay()
    {
        return _toStorageDay;
    }

    /**
     * 入庫日(to)を設定します。
     * @param toStorageDay 入庫日(to)
     */
    public void setToStorageDay(String toStorageDay)
    {
        _toStorageDay = toStorageDay;
    }

    /**
     * 入庫時刻を返します。
     * @return 入庫時刻を返します。
     */
    public String getStorageTime()
    {
        return _storageTime;
    }

    /**
     * 入庫時刻を設定します。
     * @param storageTime 入庫時刻
     */
    public void setStorageTime(String storageTime)
    {
        _storageTime = storageTime;
    }

    /**
     * 入庫日時を返します。
     * @return 入庫日時を返します。
     */
    public java.util.Date getStorageDate()
    {
        return _storageDate;
    }

    /**
     * 入庫日時を設定します。
     * @param storageDate 入庫日時
     */
    public void setStorageDate(java.util.Date storageDate)
    {
        _storageDate = storageDate;
    }

    /**
     * 作業時間を返します。
     * @return 作業時間を返します。
     */
    public int getWorkSeconds()
    {
        return _workSeconds;
    }

    /**
     * 作業時間を設定します。
     * @param workSeconds 作業時間
     */
    public void setWorkSeconds(int workSeconds)
    {
        _workSeconds = workSeconds;
    }

    /**
     * 最終出庫日を返します。
     * @return 最終出庫日を返します。
     */
    public String getRetrievalDay()
    {
        return _retrievalDay;
    }

    /**
     * 最終出庫日を設定します。
     * @param retrievalDay 最終出庫日
     */
    public void setRetrievalDay(String retrievalDay)
    {
        _retrievalDay = retrievalDay;
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
     * 帳票発行フラグを返します。
     * @return 帳票発行フラグを返します。
     */
    public boolean isPrintFlag()
    {
        return _printFlag;
    }

    /**
     * 帳票発行フラグを設定します。
     * @param printFlag 帳票発行フラグ
     */
    public void setPrintFlag(boolean printFlag)
    {
        _printFlag = printFlag;
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
     * 検索対象テーブルを設定します。
     * @param searchTable 検索対象テーブル
     */
    public void setSearchTable(String searchTable)
    {
        _searchTable = searchTable;
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
     * @param lastUpdateDate 最終更新日時
     */
    public void setLastUpdateDate(java.util.Date lastUpdateDate)
    {
        _lastUpdateDate = lastUpdateDate;
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
     * オーダーNoを設定します。
     * @param orderNo オーダーNo
     */
    public void setOrderNo(String orderNo)
    {
        _orderNo = orderNo;
    }

    /**
     * 棚状態：実棚チェックフラグを返します。
     * @return 棚状態：実棚チェックフラグを返します。
     */
    public boolean isStoredLocationFlag()
    {
        return _storedLocationFlag;
    }

    /**
     * 棚状態：実棚チェックフラグを設定します。
     * @param storedLocationFlag 棚状態：実棚チェックフラグ
     */
    public void setStoredLocationFlag(boolean storedLocationFlag)
    {
        _storedLocationFlag = storedLocationFlag;
    }

    /**
     * 棚状態：空PB棚チェックフラグを返します。
     * @return 棚状態：空PB棚チェックフラグを返します。
     */
    public boolean isEmptyPBLocationFlag()
    {
        return _emptyPBLocationFlag;
    }

    /**
     * 棚状態：空PB棚チェックフラグを設定します。
     * @param emptyPBLocationFlag 棚状態：空PB棚チェックフラグ
     */
    public void setEmptyPBLocationFlag(boolean emptyPBLocationFlag)
    {
        _emptyPBLocationFlag = emptyPBLocationFlag;
    }

    /**
     * 棚状態：異常棚チェックフラグを返します。
     * @return 棚状態：異常棚チェックフラグを返します。
     */
    public boolean isErrorLocationFlag()
    {
        return _errorLocationFlag;
    }

    /**
     * 棚状態：異常棚チェックフラグを設定します。
     * @param errorLocationFlag 棚状態：異常棚チェックフラグ
     */
    public void setErrorLocationFlag(boolean errorLocationFlag)
    {
        _errorLocationFlag = errorLocationFlag;
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
     * 出庫棚範囲指定フラグを返します。
     * @return 出庫棚範囲指定フラグを返します。
     */
    public boolean isRetrievalLocRangeFlag()
    {
        return _retrievalLocRangeFlag;
    }

    /**
     * 出庫棚範囲指定フラグを設定します。
     * @param retrievalLocRangeFlag 出庫棚範囲指定フラグ
     */
    public void setRetrievalLocRangeFlag(boolean retrievalLocRangeFlag)
    {
        _retrievalLocRangeFlag = retrievalLocRangeFlag;
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
     * 全数フラグを返します。
     * @return 全数フラグを返します。
     */
    public boolean isAllQtyFlag()
    {
        return _allQtyFlag;
    }

    /**
     * 全数フラグを設定します。
     * @param allQtyFlag 全数フラグ
     */
    public void setAllQtyFlag(boolean allQtyFlag)
    {
        _allQtyFlag = allQtyFlag;
    }

    /**
     * オーダーNo.(to)を返します。
     * @return オーダーNo.(to)を返します。
     */
    public String getToOrderNo()
    {
        return _toOrderNo;
    }

    /**
     * オーダーNo.(to)を設定します。
     * @param toOrderNo オーダーNo.(to)
     */
    public void setToOrderNo(String toOrderNo)
    {
        _toOrderNo = toOrderNo;
    }

    /**
     * 作業表示運用を返します。
     * @return 作業表示運用を返します。
     */
    public String getWorkDispOperate()
    {
        return _operationDisp;
    }

    /**
     * 作業表示運用を設定します。
     * @param operatoinDisp 作業表示運用
     */
    public void setWorkDispOperate(String operatoinDisp)
    {
        _operationDisp = operatoinDisp;
    }

    /**
     * 作業No.(搬送情報用)を返します。
     * @return 作業No.(搬送情報用)を返します。
     */
    public String getWorkNo()
    {
        return _workNo;
    }

    /**
     * 作業No.(搬送情報用)を設定します。
     * @param workNo 作業No.(搬送情報用)
     */
    public void setWorkNo(String workNo)
    {
        _workNo = workNo;
    }

    /**
     * collectJobNoを返します。
     * @return collectJobNoを返します。
     */
    public String getCollectJobNo()
    {
        return _collectJobNo;
    }

    /**
     * collectJobNoを設定します。
     * @param collectJobNo collectJobNo
     */
    public void setCollectJobNo(String collectJobNo)
    {
        _collectJobNo = collectJobNo;
    }

    /**
     * systemConnKeyを返します。
     * @return systemConnKeyを返します。
     */
    public String getSystemConnKey()
    {
        return _systemConnKey;
    }

    /**
     * systemConnKeyを設定します。
     * @param systemConnKey systemConnKey
     */
    public void setSystemConnKey(String systemConnKey)
    {
        _systemConnKey = systemConnKey;
    }

    /**
     * 棚状態：空棚チェックフラグを返します。
     * @return 棚状態：空棚チェックフラグを返します。
     */
    public boolean isEmptyLocationFlag()
    {
        return _emptyLocationFlag;
    }

    /**
     * emptyLoca棚状態：空棚チェックフラグionFlagを設定します。
     * @param emptyLocationFlag 棚状態：空棚チェックフラグ
     */
    public void setEmptyLocationFlag(boolean emptyLocationFlag)
    {
        _emptyLocationFlag = emptyLocationFlag;
    }

    /**
     * 出庫ステーションNo.を返します。
     * @return 出庫ステーションNo.を返します。
     */
    public String getRetrievalStationNo()
    {
        return _retrievalStationNo;
    }

    /**
     * 出庫ステーションNo.を設定します。
     * @param station 出庫ステーションNo.
     */
    public void setRetrievalStationNo(String station)
    {
        _retrievalStationNo = station;
    }

    /**
     * 入庫ステーションNo.を返します。
     * @return 入庫ステーションNo.を返します。
     */
    public String getStorageStationNo()
    {
        return _storageStationNo;
    }

    /**
     * 入庫ステーションNo.を設定します。
     * @param station 入庫ステーションNo.
     */
    public void setStorageStationNo(String station)
    {
        _storageStationNo = station;
    }

    /**
     * パレットIDを取得します。
     * @return パレットID
     */
    public String getPalletId()
    {
        return _palletId;
    }

    /**
     * パレットIDを設定します。
     * @param palletId パレットID
     */
    public void setPalletId(String palletId)
    {
        this._palletId = palletId;
    }

    /**
     * 前搬送エラーフラグを返します。
     * true:開始されていない搬送があった場合、エラーとします <BR>
     * false:開始されていない搬送があっても処理を続行します <BR>
     * @return _errorAllocCarryを返します。
     */
    public boolean isErrorAllocCarry()
    {
        return _errorAllocCarry;
    }

    /**
     * 前搬送エラーフラグを設定します。<BR>
     * true:開始されていない搬送があった場合、エラーとします <BR>
     * false:開始されていない搬送があっても処理を続行します <BR>
     * @param errorAllocCarry errorAllocCarry
     */
    public void setErrorAllocCarry(boolean errorAllocCarry)
    {
        _errorAllocCarry = errorAllocCarry;
    }

    /**
     * 理由区分を返します。
     * @return 理由区分を返します。
     */
    public int getReasonType()
    {
        return _reasonType;
    }

    /**
     * 理由区分を設定します。
     * @param reasonType 理由区分
     */
    public void setReasonType(int reasonType)
    {
        _reasonType = reasonType;
    }

    /**
     * 理由名称を返します。
     * @return 理由区分を返します。
     */
    public String getReasonName()
    {
        return _reasonName;
    }

    /**
     * 理由名称を設定します。
     * @param reasonName 理由名称
     */
    public void setReasonName(String reasonName)
    {
        _reasonName = reasonName;
    }

    /**
     * 最適化：ソフトゾーン最適化チェックフラグを返します。
     * @return 最適化：ソフトゾーン最適化チェックフラグを返します。
     */
    public boolean isSoftZoneOptimum()
    {
        return _softZoneOptimum;
    }

    /**
     * 最適化：ソフトゾーン最適化チェックフラグを設定します。
     * @param softZoneOptimum 最適化：ソフトゾーン最適化チェックフラグ
     */
    public void setSoftZoneOptimum(boolean softZoneOptimum)
    {
        _softZoneOptimum = softZoneOptimum;
    }

    /**
     * 最適化：荷姿最適化チェックフラグを返します。
     * @return 最適化：荷姿最適化チェックフラグを返します。
     */
    public boolean isLoadSizeOptimum()
    {
        return _loadSizeOptimum;
    }

    /**
     * 最適化：荷姿最適化チェックフラグを設定します。
     * @param loadSizeOptimum 最適化：荷姿最適化チェックフラグ
     */
    public void setLoadSizeOptimum(boolean loadSizeOptimum)
    {
        _loadSizeOptimum = loadSizeOptimum;
    }

    /**
     * 最適化：空棚最適化チェックフラグを返します。
     * @return 最適化：空棚最適化チェックフラグを返します。
     */
    public boolean isVacantOptimum()
    {
        return _vacantOptimum;
    }

    /**
     * 最適化：空棚最適化チェックフラグを設定します。
     * @param vacantOptimum 最適化：空棚最適化チェックフラグ
     */
    public void setVacantOptimum(boolean vacantOptimum)
    {
        _vacantOptimum = vacantOptimum;
    }

    /**
     * 優先区分を設定します。
     * @param 優先区分を設定します。 
     */
    public void setPriorityType(String priorityType)
    {
        _priorityType = priorityType;
    }

    /**
     * 優先区分を返します。
     * @return 優先区分
     */
    public String getPriorityType()
    {
        return _priorityType;
    }

    /**
     * 選択チェックを設定します。
     * @param 優先区分を設定します。 
     */
    public void setSelected(boolean selected)
    {
        _selected = selected;
    }

    /**
     * 選択チェックを返します。
     * @return 選択チェック
     */
    public boolean isSelected()
    {
        return _selected;
    }
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

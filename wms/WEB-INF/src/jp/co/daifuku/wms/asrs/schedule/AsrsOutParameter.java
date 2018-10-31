package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.OutParameter;

/**
 * <CODE>AsRsOutParameter</CODE>クラスは、AS/RSパッケージ内のスケジュール→画面間のパラメータの受渡しに使用します。<BR>
 * このクラスではAS/RSパッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>AsRsOutParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     設定単位キー<BR>
 *     作業No.<BR>
 *     作業No.(搬送情報用)<BR>
 *     状態フラグ<BR>
 *     中断中フラグ<BR>
 *     機器状態<BR>
 *     接続状態<BR>
 *     AGCNo.<BR>
 *     RMNo.<BR>
 *     作業件数<BR>
 *     現在作業モード<BR>
 *     作業モード<BR>
 *     作業モード切替要求<BR>
 *     モード切替種別<BR>
 *     搬送キー<BR>
 *     搬送区分<BR>
 *     搬送状態<BR>
 *     優先区分<BR>
 *     出庫指示詳細<BR>
 *     エリアNo.<BR>
 *     エリア名称<BR>
 *     ステーションNo.<BR>
 *     ステーションNo.(表示用)<BR>
 *     ステーション名称<BR>
 *     搬送元ステーションNo.<BR>
 *     搬送元ステーション名称<BR>
 *     搬送先ステーションNo.<BR>
 *     搬送先ステーション名称<BR>
 *     作業場<BR>
 *     棚<BR>
 *     棚(to)<BR>
 *     棚状態<BR>
 *     ゾーンNo.<BR>
 *     作業種別<BR>
 *     在庫ID<BR>
 *     作業予定日<BR>
 *     荷主コード<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     出荷先コード<BR>
 *     出荷先名称<BR>
 *     オーダーNo.<BR>
 *     ロットNo.<BR>
 *     予定ロットNo.<BR>
 *     実績ロットNo.<BR>
 *     ケース入数<BR>
 *     作業予定数<BR>
 *     出庫数<BR>
 *     在庫数<BR>
 *     引当可能ケース数<BR>
 *     引当可能ピース数<BR>
 *     在庫ケース数<BR>
 *     在庫ピース数<BR>
 *     入庫ケース数<BR>
 *     入庫ピース数<BR>
 *     出庫ケース数<BR>
 *     出庫ピース数<BR>
 *     総稼動数<BR>
 *     入庫稼動数<BR>
 *     出庫稼動数<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     実棚数<BR>
 *     空棚数<BR>
 *     空PB棚数<BR>
 *     異常棚数<BR>
 *     禁止棚数<BR>
 *     アクセス不可棚数<BR>
 *     総棚数<BR>
 *     格納率<BR>
 *     出庫日<BR>
 *     入庫日<BR>
 *     入庫時刻<BR>
 *     在庫管理導入フラグ<BR>
 *     最終更新日時<BR>
 *     バッチNo.<BR>
 *     明細数<BR>
 *     集約作業No<BR>
 *     システム接続キー<BR>
 *     アクセス不可棚<BR>
 *     空パレット状態<BR>
 *     作業区分<BR>
 *     伝票No.<BR>
 *     伝票行No.<BR>
 *     作業枝番<BR>
 *     払出し区分<BR>
 *     引当<BR>
 *     パレットID<BR>
 * </DIR>
 * 
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 */
public class AsrsOutParameter
        extends OutParameter
{
    // Class variables -----------------------------------------------       
    /**
     * 設定単位キー
     */
    private String _settingUnitKey;

    /**
     * 作業No.
     */
    private String _jobNo;

    /**
     * 作業No.(搬送情報)
     */
    private String _workNo;

    /**
     * 状態フラグ
     */
    private String _statusFlag;

    /**
     * 中断中フラグ
     */
    private String _suspend;

    /**
     * 機器状態
     */
    private String _controllerStatus;

    /**
     * 接続状態
     */
    private String _gcStatusFlag;

    /**
     * AGCNo.
     */
    private String _agcNo;

    /**
     * RMNo.
     */
    private String _rmNo;

    /**
     * 作業件数
     */
    private int _workingCnt;

    /**
     * 現在作業モード
     */
    private String _currentMode;

    /**
     * 作業モード
     */
    private String _mode;

    /**
     * 作業モード切替要求
     */
    private String _modeRequest;

    /**
     * モード切替種別
     */
    private String _modeType;

    /**
     * 搬送キー
     */
    private String _carryKey;

    /**
     * 搬送区分
     */
    private String _carryFlag;

    /**
     * 搬送状態
     */
    private String _cmdStatus;

    /**
     * 優先区分
     */
    private String _priority;

    /**
     * 出庫指示詳細
     */
    private String _retrievalDetail;

    /**
     * 作業表示運用種別
     */
    private String _workDispOperate;

    /**
     * エリアNo.
     */
    private String _areaNo;

    /**
     * エリア名称
     */
    private String _areaName;

    /**
     * ステーションNo.
     */
    private String _stationNo;

    /**
     * ステーションNo.(表示用)
     */
    private String _dispStationNo;

    /**
     * ステーション名称
     */
    private String _stationName;

    /**
     * 搬送元ステーションNo.
     */
    private String _sourceStationNo;

    /**
     * 搬送元ステーション名称
     */
    private String _sourceStationName;

    /**
     * 搬送先ステーションNo.
     */
    private String _destStationNo;

    /**
     * 搬送先ステーション名称
     */
    private String _destStationName;

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
     * ゾーンNo.
     */
    private String _zoneNo;

    /**
     * 作業種別
     */
    private String _workType;

    /**
     * 荷姿チェック
     */
    private String _loadSize;

    /**
     * 在庫ID
     */
    private String _stockId;

    /**
     * 作業予定日
     */
    private String _workPlanDay;

    /**
     * 荷主コード
     */
    private String _consignorCode;

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
     * オーダーNo.
     */
    private String _orderNo;

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
     * 作業予定数
     */
    private int _workPlanQty;

    /**
     * 出庫数
     */
    private int _retrievalQty;

    /**
     * 在庫数
     */
    private long _stockQty;

    /**
     * 引当可能ケース数
     */
    private long _allocateCaseQty;

    /**
     * 引当可能ピース数
     */
    private long _allocatePieceQty;

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
     * 出庫ケース数
     */
    private int _retrievalCaseQty;

    /**
     * 出庫ピース数
     */
    private int _retrievalPieceQty;

    /**
     * 総稼動数
     */
    private int _totalInOutResultCnt;

    /**
     * 入庫稼動数
     */
    private int _storageResultCnt;

    /**
     * 出庫稼動数
     */
    private int _retrievalResultCnt;

    /**
     * JANコード
     */
    private String _janCode;

    /**
     * ケースITF
     */
    private String _itf;

    /**
     * ハードゾーン(荷姿)
     */
    private String _HardZone;

    /**
     * 実棚数
     */
    private int _realLocationCnt;

    /**
     * 空棚数
     */
    private int _vacantLocationCnt;

    /**
     * 空PB棚数
     */
    private int _emptyPBLocationCnt;

    /**
     * 異常棚数
     */
    private int _abnormalLocationCnt;

    /**
     * 禁止棚数
     */
    private int _prohibitionLocationCnt;

    /**
     * アクセス不可棚数
     */
    private int _notAccessLocationCnt;

    /**
     * 総棚数
     */
    private int _totalLocationCnt;

    /**
     * 格納率
     */
    private String _locationRate;

    /**
     * 出庫日
     */
    private String _retrievalDay;

    /**
     * 入庫日
     */
    private String _storageDay;

    /**
     * 入庫予定日
     */
    private String _storagePlanDay;

    /**
     * 入庫時刻
     */
    private String _storageTime;

    /**
     * 入庫日時
     */
    private java.util.Date _storageDate;

    /**
     * マスタ管理導入フラグ
     */
    private boolean _masterFlag;

    /**
     * 最終更新日時
     */
    private java.util.Date _lastUpdateDate;

    /**
     * バッチNo.
     */
    private String _batchNo;

    /**
     * 明細数
     */
    private long _detailCnt;

    /**
     * 集約作業No.
     */
    private String _collectJobNo;


    /**
     * システム接続キー
     */
    private String _systemConnKey;

    /**
     * アクセス不可棚
     */
    private String _notAccessFlag;

    /**
     * 空パレット状態
     */
    private String _palletEmptyFlag;

    /**
     * 作業区分
     */
    private String _jobType;

    /**
     * 伝票No.
     */
    private String _ticketNo;

    /**
     * 伝票行No.
     */
    private int _lineNo;

    /**
     * 作業枝番
     */
    private int _branchNo;

    /**
     * 払出し区分 <BR>
     * true:払出し可能 false:払出し不可
     */
    private boolean _canRemove = false;

    /**
     * 引当<BR>
     * true:引当中 false:それ以外
     */
    private boolean _allocate;

    /**
     * パレットID
     */
    private String _palletId;

    /**
     * スケジュールNo.
     */
    private String _schNo;

    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 4122 $,$Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $");
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
     * 機器状態を返します。
     * @return 機器状態を返します。
     */
    public String getControllerStatus()
    {
        return _controllerStatus;
    }

    /**
     * 機器状態を設定します。
     * @param controllerStatus 機器状態
     */
    public void setControllerStatus(String controllerStatus)
    {
        _controllerStatus = controllerStatus;
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
     * 作業件数を返します。
     * @return 作業件数を返します。
     */
    public int getWorkingCnt()
    {
        return _workingCnt;
    }

    /**
     * 作業件数を設定します。
     * @param workingCnt 作業件数
     */
    public void setWorkingCnt(int workingCnt)
    {
        _workingCnt = workingCnt;
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
     * 作業モードを返します。
     * @return 作業モードを返します。
     */
    public String getMode()
    {
        return _mode;
    }

    /**
     * 作業モードを設定します。
     * @param mode 作業モード
     */
    public void setMode(String mode)
    {
        _mode = mode;
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
     * 搬送区分を返します。
     * @return 搬送区分を返します。
     */
    public String getCarryFlag()
    {
        return _carryFlag;
    }

    /**
     * 搬送区分を設定します。
     * @param carryFlag 搬送区分
     */
    public void setCarryFlag(String carryFlag)
    {
        _carryFlag = carryFlag;
    }

    /**
     * 搬送状態を返します。
     * @return 搬送状態を返します。
     */
    public String getCmdStatus()
    {
        return _cmdStatus;
    }

    /**
     * 搬送状態を設定します。
     * @param cmdStatus 搬送状態
     */
    public void setCmdStatus(String cmdStatus)
    {
        _cmdStatus = cmdStatus;
    }

    /**
     * 優先区分を返します。
     * @return 優先区分を返します。
     */
    public String getPriority()
    {
        return _priority;
    }

    /**
     * 優先区分を設定します。
     * @param priority 優先区分
     */
    public void setPriority(String priority)
    {
        _priority = priority;
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
     * 作業表示運用種別を返します。
     * @return 作業表示運用種別
     */
    public String getWorkDispOperate()
    {
        return _workDispOperate;
    }

    /**
     * 作業表示運用種別を設定します。
     * @param workDispOperate 作業表示運用種別
     */
    public void setWorkDispOperate(String workDispOperate)
    {
        _workDispOperate = workDispOperate;
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
     * エリア名称を返します。
     * @return エリア名称を返します。
     */
    public String getAreaName()
    {
        return _areaName;
    }

    /**
     * エリア名称を設定します。
     * @param areaName エリア名称
     */
    public void setAreaName(String areaName)
    {
        _areaName = areaName;
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
     * ステーションNo.(表示用)を返します。
     * @return ステーションNo.(表示用)を返します。
     */
    public String getDispStationNo()
    {
        return _dispStationNo;
    }

    /**
     * ステーションNo.(表示用)を設定します。
     * @param dispStationNo ステーションNo.(表示用)
     */
    public void setDispStationNo(String dispStationNo)
    {
        _dispStationNo = dispStationNo;
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
     * 搬送元ステーションNo.を返します。
     * @return 搬送元ステーションNo.を返します。
     */
    public String getSourceStationNo()
    {
        return _sourceStationNo;
    }

    /**
     * 搬送元ステーションNo.を設定します。
     * @param sourceStationNo 搬送元ステーションNo.
     */
    public void setSourceStationNo(String sourceStationNo)
    {
        _sourceStationNo = sourceStationNo;
    }

    /**
     * 搬送元ステーション名称を返します。
     * @return 搬送元ステーション名称を返します。
     */
    public String getSourceStationName()
    {
        return _sourceStationName;
    }

    /**
     * 搬送元ステーション名称を設定します。
     * @param sourceStationName 搬送元ステーション名称
     */
    public void setSourceStationName(String sourceStationName)
    {
        _sourceStationName = sourceStationName;
    }

    /**
     * 搬送先ステーションNo.を返します。
     * @return 搬送先ステーションNo.を返します。
     */
    public String getDestStationNo()
    {
        return _destStationNo;
    }

    /**
     * 搬送先ステーションNo.を設定します。
     * @param destStationNo 搬送先ステーションNo.
     */
    public void setDestStationNo(String destStationNo)
    {
        _destStationNo = destStationNo;
    }

    /**
     * 搬送先ステーション名称を返します。
     * @return 搬送先ステーション名称を返します。
     */
    public String getDestStationName()
    {
        return _destStationName;
    }

    /**
     * 搬送先ステーション名称を設定します。
     * @param destStationName 搬送先ステーション名称
     */
    public void setDestStationName(String destStationName)
    {
        _destStationName = destStationName;
    }

    /**
     * 倉庫ステーションNo.を返します。
     * @return 倉庫ステーションNoを返します。
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
     * @return 棚を返します。
     */
    public String getToLocation()
    {
        return _toLocation;
    }

    /**
     * 棚(to)を設定します。
     * @param location 棚
     */
    public void setToLocation(String location)
    {
        _toLocation = location;
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
     * 荷姿チェックを返します。
     * @return 荷姿チェックを返します。
     */
    public String getLoadSize()
    {
        return _loadSize;
    }

    /**
     * 荷姿チェックを設定します。
     * @param loadSize 荷姿チェック
     */
    public void setLoadSize(String loadSize)
    {
        _loadSize = loadSize;
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
     * 作業予定日を返します。
     * @return 作業予定日を返します。
     */
    public String getWorkPlanDay()
    {
        return _workPlanDay;
    }

    /**
     * 作業予定日を設定します。
     * @param workPlanDay 作業予定日
     */
    public void setWorkPlanDay(String workPlanDay)
    {
        _workPlanDay = workPlanDay;
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
     * @return 商品コードを返します。
     */
    public String getToItemCode()
    {
        return _toItemCode;
    }

    /**
     * 商品コード(to)設定します。
     * @param itemCode 商品コード
     */
    public void setToItemCode(String itemCode)
    {
        _toItemCode = itemCode;
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
     * オーダーNo.を返します。
     * @return オーダーNo.を返します。
     */
    public String getOrderNo()
    {
        return _orderNo;
    }

    /**
     * オーダーNo.を設定します。
     * @param orderNo オーダーNo.
     */
    public void setOrderNo(String orderNo)
    {
        _orderNo = orderNo;
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
     * 作業予定数を返します。
     * @return 作業予定数を返します。
     */
    public int getWorkPlanQty()
    {
        return _workPlanQty;
    }

    /**
     * 作業予定数を設定します。
     * @param workPlanQty 作業予定数
     */
    public void setWorkPlanQty(int workPlanQty)
    {
        _workPlanQty = workPlanQty;
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
     * 引当可能ケース数を返します。
     * @return 引当可能ケース数を返します。
     */
    public long getAllocateCaseQty()
    {
        return _allocateCaseQty;
    }

    /**
     * 引当可能ケース数を設定します。
     * @param allocateCaseQty 引当可能ケース数
     */
    public void setAllocateCaseQty(long allocateCaseQty)
    {
        _allocateCaseQty = allocateCaseQty;
    }

    /**
     * 引当可能ピース数を返します。
     * @return 引当可能ピース数を返します。
     */
    public long getAllocatePieceQty()
    {
        return _allocatePieceQty;
    }

    /**
     * 引当可能ピース数を設定します。
     * @param allocatePieceQty 引当可能ピース数
     */
    public void setAllocatePieceQty(long allocatePieceQty)
    {
        _allocatePieceQty = allocatePieceQty;
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
     * 出庫ピース数を設定します。
     * @param retrievalPieceQty 出庫ピース数
     */
    public void setRetrievalPieceQty(int retrievalPieceQty)
    {
        _retrievalPieceQty = retrievalPieceQty;
    }

    /**
     * 総稼動数を返します。
     * @return 総稼動数を返します。
     */
    public int getTotalInOutResultCnt()
    {
        return _totalInOutResultCnt;
    }

    /**
     * 総稼動数を設定します。
     * @param totalInOutResultCnt 総稼動数
     */
    public void setTotalInOutResultCnt(int totalInOutResultCnt)
    {
        _totalInOutResultCnt = totalInOutResultCnt;
    }

    /**
     * 入庫稼動数を返します。
     * @return 入庫稼動数を返します。
     */
    public int getStorageResultCnt()
    {
        return _storageResultCnt;
    }

    /**
     * 入庫稼動数を設定します。
     * @param storageResultCnt 入庫稼動数
     */
    public void setStorageResultCnt(int storageResultCnt)
    {
        _storageResultCnt = storageResultCnt;
    }

    /**
     * 出庫稼動数を返します。
     * @return 出庫稼動数を返します。
     */
    public int getRetrievalResultCnt()
    {
        return _retrievalResultCnt;
    }

    /**
     * 出庫稼動数を設定します。
     * @param retrievalResultCnt 出庫稼動数
     */
    public void setRetrievalResultCnt(int retrievalResultCnt)
    {
        _retrievalResultCnt = retrievalResultCnt;
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
     * ハードゾーンをセットします。
     * @param arg セットするハードゾーン
     */
    public void setHardZone(String arg)
    {
        _HardZone = arg;
    }

    /**
     * ハードゾーンを取得します。
     * @return ハードゾーン
     */
    public String getHardZone()
    {
        return _HardZone;
    }

    /**
     * 実棚数を返します。
     * @return 実棚数を返します。
     */
    public int getRealLocationCnt()
    {
        return _realLocationCnt;
    }

    /**
     * 実棚数を設定します。
     * @param realLocationCnt 実棚数
     */
    public void setRealLocationCnt(int realLocationCnt)
    {
        _realLocationCnt = realLocationCnt;
    }

    /**
     * 空棚数を返します。
     * @return 空棚数を返します。
     */
    public int getVacantLocationCnt()
    {
        return _vacantLocationCnt;
    }

    /**
     * 空棚数を設定します。
     * @param vacantLocationCnt 空棚数
     */
    public void setVacantLocationCnt(int vacantLocationCnt)
    {
        _vacantLocationCnt = vacantLocationCnt;
    }

    /**
     * 空PB棚数を返します。
     * @return 空PB棚数を返します。
     */
    public int getEmptyPBLocationCnt()
    {
        return _emptyPBLocationCnt;
    }

    /**
     * 空PB棚数を設定します。
     * @param emptyPBLocationCnt 空PB棚数
     */
    public void setEmptyPBLocationCnt(int emptyPBLocationCnt)
    {
        _emptyPBLocationCnt = emptyPBLocationCnt;
    }

    /**
     * 異常棚数を返します。
     * @return 異常棚数を返します。
     */
    public int getAbnormalLocationCnt()
    {
        return _abnormalLocationCnt;
    }

    /**
     * 異常棚数を設定します。
     * @param abnormalLocationCnt 異常棚数
     */
    public void setAbnormalLocationCnt(int abnormalLocationCnt)
    {
        _abnormalLocationCnt = abnormalLocationCnt;
    }

    /**
     * 禁止棚数を返します。
     * @return 禁止棚数を返します。
     */
    public int getProhibitionLocationCnt()
    {
        return _prohibitionLocationCnt;
    }

    /**
     * 禁止棚数を設定します。
     * @param prohibitionLocationCnt 禁止棚数
     */
    public void setProhibitionLocationCnt(int prohibitionLocationCnt)
    {
        _prohibitionLocationCnt = prohibitionLocationCnt;
    }

    /**
     * アクセス不可棚数を返します。
     * @return アクセス不可棚数を返します。
     */
    public int getNotAccessLocationCnt()
    {
        return _notAccessLocationCnt;
    }

    /**
     * アクセス不可棚数を設定します。
     * @param notAccessLocationCnt アクセス不可棚数
     */
    public void setNotAccessLocationCnt(int notAccessLocationCnt)
    {
        _notAccessLocationCnt = notAccessLocationCnt;
    }

    /**
     * 総棚数を返します。
     * @return 総棚数を返します。
     */
    public int getTotalLocationCnt()
    {
        return _totalLocationCnt;
    }

    /**
     * 総棚数を設定します。
     * @param totalLocationCnt 総棚数
     */
    public void setTotalLocationCnt(int totalLocationCnt)
    {
        _totalLocationCnt = totalLocationCnt;
    }

    /**
     * 格納率を返します。
     * @return 格納率を返します。
     */
    public String getLocationRate()
    {
        return _locationRate;
    }

    /**
     * 格納率を設定します。
     * @param locationRate 格納率
     */
    public void setLocationRate(String locationRate)
    {
        _locationRate = locationRate;
    }

    /**
     * 出庫日を返します。
     * @return 出庫日を返します。
     */
    public String getRetrievalDay()
    {
        return _retrievalDay;
    }

    /**
     * 出庫日を設定します。
     * @param retrievalDay 出庫日
     */
    public void setRetrievalDay(String retrievalDay)
    {
        _retrievalDay = retrievalDay;
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
     * マスタ管理導入フラグを返します。
     * @return マスタ管理導入フラグを返します。
     */
    public boolean isMasterFlag()
    {
        return _masterFlag;
    }

    /**
     * マスタ管理導入フラグを設定します。
     * @param masterFlag マスタ管理導入フラグ
     */
    public void setMasterFlag(boolean masterFlag)
    {
        _masterFlag = masterFlag;
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
     * batchNoを返します。
     * @return batchNoを返します。
     */
    public String getBatchNo()
    {
        return _batchNo;
    }

    /**
     * batchNoを設定します。
     * @param batchNo batchNo
     */
    public void setBatchNo(String batchNo)
    {
        _batchNo = batchNo;
    }

    /**
     * detailCntを返します。
     * @return detailCntを返します。
     */
    public long getDetailCnt()
    {
        return _detailCnt;
    }

    /**
     * detailCntを設定します。
     * @param detailCnt detailCnt
     */
    public void setDetailCnt(long detailCnt)
    {
        _detailCnt = detailCnt;
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
     * アクセス不可棚を返します。
     * @return アクセス不可棚を返します。
     */
    public String getNotAccessFlag()
    {
        return _notAccessFlag;
    }

    /**
     * アクセス不可棚を設定します。
     * @param notAccessFlag アクセス不可棚
     */
    public void setNotAccessFlag(String notAccessFlag)
    {
        _notAccessFlag = notAccessFlag;
    }

    /**
     * 空パレット状態を返します。
     * @return 空パレット状態を返します。
     */
    public String getPalletEmptyFlag()
    {
        return _palletEmptyFlag;
    }

    /**
     * 空パレット状態を設定します。
     * @param palletEmptyFlag 空パレット状態
     */
    public void setPalletEmptyFlag(String palletEmptyFlag)
    {
        _palletEmptyFlag = palletEmptyFlag;
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
     * 作業区分を返します。
     * @return 作業区分を返します。
     */
    public Object getJobType()
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
     * 伝票No.取得します。
     * @return 伝票No.
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
     * 伝票行No.を取得します。
     * @return 伝票行No.
     */
    public int getLineNo()
    {
        return _lineNo;
    }

    /**
     * 伝票行No.を設定します。
     * @param lineNo 伝票行No.
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
     * 払出し区分を返します。<BR>
     * true:払出し可能 false:払出し不可
     * @return 払出し区分を返します。
     */
    public boolean canRemove()
    {
        return _canRemove;
    }

    /**
     * 払出し区分を設定します。<BR>
     * true:払出し可能 false:払出し不可
     * @param remove 払出し区分
     */
    public void setCanRemove(boolean remove)
    {
        _canRemove = remove;
    }

    /**
     * 引当を取得します。<BR>
     * true:引当中 false:それ以外
     * @return 引当
     */
    public boolean isAllocate()
    {
        return _allocate;
    }

    /**
     * 引当を設定します。<BR>
     * true:引当中 false:それ以外
     * @param allocate 引当
     */
    public void setAllocate(boolean allocate)
    {
        this._allocate = allocate;
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
     * スケジュールNo.を取得します。
     * @return スケジュールNo.
     */
    public String getScheduleNo()
    {
        return _schNo;
    }

    /**
     * スケジュールNo.を設定します。
     * @param schNo スケジュールNo.
     */
    public void setScheduleNo(String schNo)
    {
        this._schNo = schNo;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

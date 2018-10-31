package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.OutParameter;

/**
 * <CODE>StockOutParameter</CODE>クラスは、在庫パッケージ内のスケジュール→画面間のパラメータの受渡しに使用します。<BR>
 * このクラスでは在庫パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>StockOutParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     設定単位キー<BR>
 *     作業No.<BR>
 *     状態フラグ<BR>
 *     作業区分<BR>
 *     在庫ID<BR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     エリアNo.<BR>
 *     エリア名称<BR>
 *     棚<BR>
 *     棚(to)<BR>
 *     ロットNo.<BR>
 *     ケース入数<BR>
 *     ボール入数<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     ボールITF<BR>
 *     予定ケース数<BR>
 *     予定ピース数<BR>
 *     引当可能ケース数<BR>
 *     引当可能ピース数<BR>
 *     移動ケース数<BR>
 *     移動ピース数<BR>
 *     移動可能数<BR>
 *     移動可能ケース数<BR>
 *     移動可能ピース数<BR>
 *     在庫ケース数<BR>
 *     在庫ピース数<BR>
 *     実績ケース数<BR>
 *     実績ケース数<BR>
 *     入庫日<BR>
 *     入庫日(to)<BR>
 *     入庫時刻<BR>
 *     入庫日時<BR>
 *     出庫日時<BR>
 *     作業日<BR>
 *     作業時刻<BR>
 *     最終出庫日<BR>
 *     最終出庫日(検索条件用)<BR>
 *     移動元エリアNo.<BR>
 *     移動元棚<BR>
 *     移動先エリアNo.<BR>
 *     移動先棚<BR>
 *     実績報告区分<BR>
 *     棚表示形式<BR>
 *     RFTNo.<BR>
 *     移動出庫ユーザ名称<BR>
 *     移動出庫端末No.<BR>
 *     移動入庫ユーザ名称<BR>
 *     移動入庫端末No.<BR>
 *     リストセル行No.<BR>
 *     マスタ管理導入フラグ<BR>
 *     最終更新日時<BR>
 *     登録日時<BR>
 *     集約条件<BR>
 * </DIR>
 * 
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class StockOutParameter
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
     * 状態フラグ
     */
    private String _statusFlag;

    /**
     * 作業区分
     */
    private String _jobType;

    /**
     * 在庫ID
     */
    private String _stockId;

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
     * 商品名称
     */
    private String _itemName;

    /**
     * エリアNo.
     */
    private String _areaNo;

    /**
     * エリア名称
     */
    private String _areaName;

    /**
     * 棚
     */
    private String _location;

    /**
     * 棚(to)
     */
    private String _toLocation;

    /**
     * ロットNo.
     */
    private String _lotNo;

    /**
     * ケース入数
     */
    private int _enteringQty;

    /**
     * ボール入数
     */
    private int _bundleEnteringQty;

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
     * 予定ケース数
     */
    private int _planCaseQty;

    /**
     * 予定ピース数
     */
    private int _planPieceQty;

    /**
     * 引当可能ケース数
     */
    private long _allocateCaseQty;

    /**
     * 引当可能ピース数
     */
    private long _allocatePieceQty;

    /**
     * 移動ケース数
     */
    private long _movementCaseQty;

    /**
     * 移動ピース数
     */
    private long _movementPieceQty;
    
    /**
     * 移動可能数
     */
    private long _movableQty;

    /**
     * 移動可能ケース数
     */
    private long _movableCaseQty;

    /**
     * 移動可能ピース数
     */
    private long _movablePieceQty;

    /**
     * 在庫ケース数
     */
    private long _stockCaseQty;

    /**
     * 在庫ピース数
     */
    private long _stockPieceQty;

    /**
     * 実績ケース数
     */
    private int _resultCaseQty;

    /**
     * 実績ピース数
     */
    private int _resultPieceQty;

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
     * 出庫日時
     */
    private java.util.Date _retrievalDate;

    /**
     * 作業日
     */
    private String _workDay;

    /**
     * 作業時刻
     */
    private String _workTime;

    /**
     * 最終出庫日
     */
    private String _retrievalDay;

    /**
     * 最終出庫日(検索条件用)
     */
    private String _searchRetrievalDay;

    /**
     * 移動元エリアNo.
     */
    private String _sourceAreaNo;

    /**
     * 移動元棚
     */
    private String _sourceLocation;

    /**
     * 移動先エリアNo.
     */
    private String _destAreaNo;

    /**
     * 移動先棚
     */
    private String _destLocation;

    /**
     * 実績報告区分
     */
    private String _reportFlag;

    /**
     * 棚表示形式
     */
    private String _locationStyle;

    /**
     * RFTNo.
     */
    private String _rftNo;

    /**
     * 移動出庫ユーザ名称
     */
    private String _moveRetrievalUserName;

    /**
     * 移動出庫端末No.
     */
    private String _moveRetrievalTerminalNo;

    /**
     * 移動入庫ユーザ名称
     */
    private String _moveStorageUserName;

    /**
     * 移動入庫端末No.
     */
    private String _moveStorageTerminalNo;

    /**
     * リストセル行No.
     */
    private int _rowNo;

    /**
     * マスタ管理導入フラグ
     */
    private boolean _masterFlag;

    /**
     * 最終更新日時
     */
    private java.util.Date _lastUpdateDate;
    
    /**
     * 登録日時
     */
    private java.util.Date _registDate;

    /**
     * 集約条件
     */
    private String _collectCondition;

    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
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
     * ケースITFを返します。
     * @return ケースITFを返します。
     */
    public String getBundleItf()
    {
        return _bundleItf;
    }

    /**
     * ケースITFを設定します。
     * @param bundleItf ケースITF
     */
    public void setBundleItf(String bundleItf)
    {
        _bundleItf = bundleItf;
    }

    /**
     * 予定ケース数を返します。
     * @return 予定ケース数を返します。
     */
    public int getPlanCaseQty()
    {
        return _planCaseQty;
    }

    /**
     * 予定ケース数を設定します。
     * @param planCaseQty 予定ケース数
     */
    public void setPlanCaseQty(int planCaseQty)
    {
        _planCaseQty = planCaseQty;
    }

    /**
     * 予定ピース数を返します。
     * @return 予定ピース数を返します。
     */
    public int getPlanPieceQty()
    {
        return _planPieceQty;
    }

    /**
     * 予定ピース数を設定します。
     * @param planPieceQty 予定ピース数
     */
    public void setPlanPieceQty(int planPieceQty)
    {
        _planPieceQty = planPieceQty;
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
     * 移動ケース数を返します。
     * @return 移動ケース数を返します。
     */
    public long getMovementCaseQty()
    {
        return _movementCaseQty;
    }

    /**
     * 移動ケース数を設定します。
     * @param movementCaseQty 移動ケース数
     */
    public void setMovementCaseQty(long movementCaseQty)
    {
        _movementCaseQty = movementCaseQty;
    }

    /**
     * 移動ピース数を返します。
     * @return 移動ピース数を返します。
     */
    public long getMovementPieceQty()
    {
        return _movementPieceQty;
    }

    /**
     * 移動ピース数を設定します。
     * @param movementPieceQty 移動ピース数
     */
    public void setMovementPieceQty(long movementPieceQty)
    {
        _movementPieceQty = movementPieceQty;
    }
    
    /**
     * 移動可能数を返します。
     * @return 移動可能数を返します。
     */
    public long getMovableQty()
    {
        return _movableQty;
    }

    /**
     * 移動可能数を設定します。
     * @param movableQty 移動可能数
     */
    public void setMovableQty(long movableQty)
    {
        _movableQty = movableQty;
    }

    /**
     * 移動可能ケース数を返します。
     * @return 移動可能ケース数を返します。
     */
    public long getMovableCaseQty()
    {
        return _movableCaseQty;
    }

    /**
     * 移動可能ケース数を設定します。
     * @param movableCaseQty 移動可能ケース数
     */
    public void setMovableCaseQty(long movableCaseQty)
    {
        _movableCaseQty = movableCaseQty;
    }

    /**
     * 移動可能ピース数を返します。
     * @return 移動可能ピース数を返します。
     */
    public long getMovablePieceQty()
    {
        return _movablePieceQty;
    }

    /**
     * 移動可能ピース数を設定します。
     * @param movablePieceQty 移動可能ピース数
     */
    public void setMovablePieceQty(long movablePieceQty)
    {
        _movablePieceQty = movablePieceQty;
    }

    /**
     * 在庫ケース数を返します。
     * @return 在庫ケース数を返します。
     */
    public long getStockCaseQty()
    {
        return _stockCaseQty;
    }

    /**
     * 在庫ケース数を設定します。
     * @param stockCaseQty 在庫ケース数
     */
    public void setStockCaseQty(long stockCaseQty)
    {
        _stockCaseQty = stockCaseQty;
    }

    /**
     * 在庫ピース数を返します。
     * @return 在庫ピース数を返します。
     */
    public long getStockPieceQty()
    {
        return _stockPieceQty;
    }

    /**
     * 在庫ピース数を設定します。
     * @param stockPieceQty 在庫ピース数
     */
    public void setStockPieceQty(long stockPieceQty)
    {
        _stockPieceQty = stockPieceQty;
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
     * 実績ピース数を返します。
     * @return 実績ピース数を返します。
     */
    public int getResultPieceQty()
    {
        return _resultPieceQty;
    }

    /**
     * 実績ピース数を設定します。
     * @param resultPieceQty 実績ピース数
     */
    public void setResultPieceQty(int resultPieceQty)
    {
        _resultPieceQty = resultPieceQty;
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
     * 出庫日時を返します。
     * @return 出庫日時を返します。
     */
    public java.util.Date getRetrievalDate()
    {
        return _retrievalDate;
    }

    /**
     * 出庫日時を設定します。
     * @param retrievalDate 出庫日時
     */
    public void setRetrievalDate(java.util.Date retrievalDate)
    {
        _retrievalDate = retrievalDate;
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
     * 作業時刻を返します。
     * @return 作業時刻を返します。
     */
    public String getWorkTime()
    {
        return _workTime;
    }

    /**
     * 作業時刻を設定します。
     * @param workTime 作業時刻
     */
    public void setWorkTime(String workTime)
    {
        _workTime = workTime;
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
     * 最終出庫日(検索条件用)を返します。
     * @return 最終出庫日(検索条件用)を返します。
     */
    public String getSearchRetrievalDay()
    {
        return _searchRetrievalDay;
    }

    /**
     * 最終出庫日(検索条件用)を設定します。
     * @param searchRetrievalDay 最終出庫日(検索条件用)
     */
    public void setSearchRetrievalDay(String searchRetrievalDay)
    {
        _searchRetrievalDay = searchRetrievalDay;
    }

    /**
     * 移動元エリアNo.を返します。
     * @return 移動元エリアNo.を返します。
     */
    public String getSourceAreaNo()
    {
        return _sourceAreaNo;
    }

    /**
     * 移動元エリアNo.を設定します。
     * @param sourceAreaNo 移動元エリアNo.
     */
    public void setSourceAreaNo(String sourceAreaNo)
    {
        _sourceAreaNo = sourceAreaNo;
    }

    /**
     * 移動元棚を返します。
     * @return 移動元棚を返します。
     */
    public String getSourceLocation()
    {
        return _sourceLocation;
    }

    /**
     * 移動元棚を設定します。
     * @param sourceLocation 移動元棚
     */
    public void setSourceLocation(String sourceLocation)
    {
        _sourceLocation = sourceLocation;
    }

    /**
     * 移動先エリアNo.を返します。
     * @return 移動先エリアNo.を返します。
     */
    public String getDestAreaNo()
    {
        return _destAreaNo;
    }

    /**
     * 移動先エリアNo.を設定します。
     * @param destAreaNo 移動先エリアNo.
     */
    public void setDestAreaNo(String destAreaNo)
    {
        _destAreaNo = destAreaNo;
    }

    /**
     * 移動先棚を返します。
     * @return 移動先棚を返します。
     */
    public String getDestLocation()
    {
        return _destLocation;
    }

    /**
     * 移動先棚を設定します。
     * @param destLocation 移動先棚
     */
    public void setDestLocation(String destLocation)
    {
        _destLocation = destLocation;
    }

    /**
     * 実績報告区分を返します。
     * @return 実績報告区分を返します。
     */
    public String getReportFlag()
    {
        return _reportFlag;
    }

    /**
     * 実績報告区分を設定します。
     * @param reportFlag 実績報告区分
     */
    public void setReportFlag(String reportFlag)
    {
        _reportFlag = reportFlag;
    }

    /**
     * 棚表示形式を返します。
     * @return 棚表示形式を返します。
     */
    public String getLocationStyle()
    {
        return _locationStyle;
    }

    /**
     * 棚表示形式を設定します。
     * @param locationStyle 棚表示形式
     */
    public void setLocationStyle(String locationStyle)
    {
        _locationStyle = locationStyle;
    }

    /**
     * RFTNo.を返します。
     * @return RFTNo.を返します。
     */
    public String getRftNo()
    {
        return _rftNo;
    }

    /**
     * RFTNo.を設定します。
     * @param rftNo RFTNo.
     */
    public void setRftNo(String rftNo)
    {
        _rftNo = rftNo;
    }

    /**
     * 移動出庫ユーザ名称を返します。
     * @return 移動出庫ユーザ名称を返します。
     */
    public String getMoveRetrievalUserName()
    {
        return _moveRetrievalUserName;
    }

    /**
     * 移動出庫ユーザ名称を設定します。
     * @param moveRetrievalUserName 移動出庫ユーザ名称
     */
    public void setMoveRetrievalUserName(String moveRetrievalUserName)
    {
        _moveRetrievalUserName = moveRetrievalUserName;
    }

    /**
     * 移動出庫端末No.を返します。
     * @return 移動出庫端末No.を返します。
     */
    public String getMoveRetrievalTerminalNo()
    {
        return _moveRetrievalTerminalNo;
    }

    /**
     * 移動出庫端末No.を設定します。
     * @param moveRetrievalTerminalNo 移動出庫端末No.
     */
    public void setMoveRetrievalTerminalNo(String moveRetrievalTerminalNo)
    {
        _moveRetrievalTerminalNo = moveRetrievalTerminalNo;
    }

    /**
     * 移動入庫ユーザ名称を返します。
     * @return 移動入庫ユーザ名称を返します。
     */
    public String getMoveStorageUserName()
    {
        return _moveStorageUserName;
    }

    /**
     * 移動入庫ユーザ名称を設定します。
     * @param moveStorageUserName 移動入庫ユーザ名称
     */
    public void setMoveStorageUserName(String moveStorageUserName)
    {
        _moveStorageUserName = moveStorageUserName;
    }

    /**
     * 移動入庫端末No.を返します。
     * @return 移動入庫端末No.を返します。
     */
    public String getMoveStorageTerminalNo()
    {
        return _moveStorageTerminalNo;
    }

    /**
     * 移動入庫端末No.を設定します。
     * @param moveStorageTerminalNo 移動入庫端末No.
     */
    public void setMoveStorageTerminalNo(String moveStorageTerminalNo)
    {
        _moveStorageTerminalNo = moveStorageTerminalNo;
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
     * 登録日時を返します。
     * @return 登録日時を返します。
     */
    public java.util.Date getRegistDate()
    {
        return _registDate;
    }

    /**
     * 登録日時を設定します。
     * @param registDate 登録日時
     */
    public void setRegistDate(java.util.Date registDate)
    {
        _registDate = registDate;
    }

    /**
     * 集約条件を返します。
     * @return 集約条件を返します。
     */
    public String getCollectCondition()
    {
        return _collectCondition;
    }

    /**
     * 集約条件を設定します。
     * @param collectCondition 集約条件
     */
    public void setCollectCondition(String collectCondition)
    {
        _collectCondition = collectCondition;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

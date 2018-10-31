package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;

/**
 * <CODE>StockInParameter</CODE>クラスは、在庫パッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスでは在庫パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>StockInParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     設定単位キー<BR>
 *     作業No.<BR>
 *     作業No.(配列)<BR>
 *     状態フラグ<BR>
 *     完了フラグ<BR>
 *     作業区分<BR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *     商品コード<BR>
 *     商品コード(to)<BR>
 *     商品名称<BR>
 *     伝票No.<BR>
 *     エリアNo.<BR>
 *     バンクNo.<BR>
 *     棚<BR>
 *     棚(to)<BR>
 *     ロットNo.<BR>
 *     予定ロットNo.<BR>
 *     実績ロットNo.<BR>
 *     ケース入数<BR>
 *     ボール入数<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     ボールITF<BR>
 *     在庫数<BR>
 *     在庫ケース数<BR>
 *     在庫ピース数<BR>
 *     実績数<BR>
 *     実績ケース数<BR>
 *     実績ピース数<BR>
 *     入庫予定日<BR>
 *     入庫日<BR>
 *     入庫日(to)<BR>
 *     入庫時刻<BR>
 *     入庫日時<BR>
 *     作業日<BR>
 *     作業日(to)<BR>
 *     作業時間<BR>
 *     作業時刻<BR>
 *     作業時刻(to)<BR>
 *     作業日時<BR>
 *     最終出庫日<BR>
 *     移動元エリアNo.<BR>
 *     移動元エリア名称<BR>
 *     移動元棚<BR>
 *     移動元棚(to)<BR>
 *     移動先エリアNo.<BR>
 *     移動先エリア名称<BR>
 *     移動先棚<BR>
 *     移動先棚(to)<BR>
 *     移動数<BR>
 *     移動ケース数<BR>
 *     移動ピース数<BR>
 *     移動可能数<BR>
 *     移動可能ケース数<BR>
 *     移動可能ピース数<BR>
 *     RFTNo.<BR>
 *     リストセル行No.<BR>
 *     帳票発行フラグ<BR>
 *     範囲指定フラグ<BR>
 *     日時範囲指定フラグ<BR>
 *     移動元範囲指定フラグ<BR>
 *     移動先範囲指定フラグ<BR>
 *     入出庫棚列検索フラグ<BR>
 *     開始終了フラグ<BR>
 *     処理フラグ<BR>
 *     キャンセルチェックボックス<BR>
 *     集約条件<BR>
 *     検索対象テーブル<BR>
 *     最終更新日時<BR>
 *     強制入庫区分<BR>
 *     理由区分<BR>
 *     理由名称<BR>
 *     全数フラグ<BR>
 * </DIR>
 *
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * @version $Revision: 6747 $, $Date: 2010-01-21 15:26:39 +0900 (木, 21 1 2010) $
 * @author  $Author: okayama $
 */
public class StockInParameter
        extends InParameter
{
    // Class variables -----------------------------------------------
    /**
     * 空棚区分(RFT)：空棚
     */
    public static final String EMPTY_LOCATION_FLAG_EMP = "0";

    /**
     * 空棚区分(RFT)：補充棚
     */
    public static final String EMPTY_LOCATION_FLAG_SPLY = "1";

    /**
     * 移動実績テーブル検索列指定：出庫棚
     */
    public static final String LOCATION_COLUMN_RETRIEVAL = "0";

    /**
     * 移動実績テーブル検索列指定：入庫棚
     */
    public static final String LOCATION_COLUMN_STORAGE = "1";

    /**
     * 集約条件：詳細
     */
    public static final String GROUP_VIEW_ALL = "2";

    /**
     * 集約条件：商品単位
     */
    public static final String GROUP_BY_ITEM = "6";

    /**
     * 在庫ID
     */
    private String _stockID;

    /**
     * 設定単位キー
     */
    private String _settingUnitKey;

    /**
     * 作業No.
     */
    private String _jobNo;

    /**
     * 作業No.(配列)
     */
    private String[] _jobNoArray;

    /**
     * 状態フラグ
     */
    private String _statusFlag;

    /**
     * 完了フラグ
     */
    private String _completionFlag;

    /**
     * 作業区分
     */
    private String _jobType;

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
     * 伝票No.
     */
    private String _ticketNo;

    /**
     * エリアNo.
     */
    private String _areaNo;

    /**
     * バンクNo.
     */
    private int _bankNo;

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
     * 在庫数
     */
    private long _stockQty;

    /**
     * 在庫ケース数
     */
    private int _stockCaseQty;

    /**
     * 在庫ピース数
     */
    private int _stockPieceQty;

    /**
     * 実績数
     */
    private int _resultQty;

    /**
     * 実績ケース数
     */
    private int _resultCaseQty;

    /**
     * 実績ピース数
     */
    private int _resultPieceQty;

    /**
     * 入庫予定日
     */
    private String _storagePlanDay;

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
    private String _StorageTime;

    /**
     * 入庫日時
     */
    private java.util.Date _StorageDate;

    /**
     * 作業日
     */
    private String _workDay;

    /**
     * 作業日(to)
     */
    private String _toWorkDay;

    /**
     * 作業時間
     */
    private int _workSeconds;

    /**
     * 作業時刻
     */
    private String _workTime;

    /**
     * 作業時刻(to)
     */
    private String _toWorkTime;

    /**
     * 開始作業日時
     */
    private java.util.Date _FromWorkDate;

    /**
     * 終了作業日時
     */
    private java.util.Date _ToWorkDate;

    /**
     * 最終出庫日
     */
    private String _retrievalDay;

    /**
     * 移動元エリアNo.
     */
    private String _sourceAreaNo;

    /**
     * 移動元エリア名称
     */
    private String _sourceAreaName;

    /**
     * 移動元棚
     */
    private String _sourceLocation;

    /**
     * 移動元棚(to)
     */
    private String _toSourceLocation;

    /**
     * 移動先エリアNo.
     */
    private String _destAreaNo;

    /**
     * 移動先エリア名称
     */
    private String _destAreaName;

    /**
     * 移動先棚
     */
    private String _destLocation;

    /**
     * 移動先棚(to)
     */
    private String _toDestLocation;

    /**
     * 移動数
     */
    private long _movementQty;

    /**
     * 移動ケース数
     */
    private int _movementCaseQty;

    /**
     * 移動ピース数
     */
    private int _movementPieceQty;

    /**
     * 移動可能数
     */
    private long _allocateQty;

    /**
     * 移動可能ケース数
     */
    private int _allocateCaseQty;

    /**
     * 移動可能ピース数
     */
    private int _allocatePieceQty;

    /**
     * リストセル行No.
     */
    private int _rowNo;

    /**
     * 帳票発行フラグ
     */
    private boolean _printFlag;

    /**
     * 範囲指定フラグ
     */
    private boolean _rangeFlag;

    /**
     * 日時範囲指定フラグ
     */
    private boolean _workDayRangeFlag;

    /**
     * 移動元範囲指定フラグ
     */
    private boolean _sourceLocationRangeFlag;

    /**
     * 移動先範囲指定フラグ
     */
    private boolean _destLocationRangeFlag;

    /**
     * 入出庫棚列検索フラグ
     */
    private String _locationColumnSearchFlag;

    /**
     * 開始終了フラグ
     */
    private String _fromtoFrag;

    /**
     * 開始終了フラグ(入庫日)
     */
    private String _fromtoDateFrag;

    /**
     * 開始終了フラグ(棚)
     */
    private String _fromtoLocationFrag;

    /**
     * 処理フラグ
     */
    private String _processFlag;

    /**
     * キャンセルチェックボックス
     */
    private boolean _cancelCheck;

    /**
     * 集約条件
     */
    private String _collectCondition;

    /**
     * 検索対象テーブル
     */
    private String _searchTable;

    /**
     * 最終更新日時
     */
    private java.util.Date _lastUpdateDate;

    /**
     * 強制入庫区分
     */
    private boolean _forceStrageFlag;

    /**
     * 空棚区分
     */
    private String _emptyLocationFlag;

    /**
     * 検索日時
     */
    private java.util.Date _searchDate;

    /**
     * 検索日時(to)
     */
    private java.util.Date _toSearchDate;

    /**
     * 表示用終了時刻(to)
     */
    private java.util.Date _toDispDate;

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
     * 帳票再発行フラグ
     */
    private boolean _rePrintFlag;

    /**
     * 移動先棚フラグ
     */
    private boolean _destLocationFlag;

    /**
     * 入荷作業フラグ
     */
    private String _receivingFlag;

    /**
     * 理由区分
     */
    private int _reasonType;

    /**
     * 理由名称
     */
    private String _reasonName;

    /**
     * 全数フラグ
     */
    private boolean _allQtyFlag;

    /**
     * 選択フラグ
     */
    private boolean _selected;

    // Constractor --------------------------------------------------
    /**
     * コンストラクタ <BR>
     * WmsUserInfoを指定します。
     *
     * @param info WmsUserInfo
     */
    public StockInParameter(WmsUserInfo info)
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
    public String getStockID()
    {
        return _stockID;
    }

    /**
     * 設定単位キーを設定します。
     * @param stockID 設定単位キー
     */
    public void setStockID(String stockID)
    {
        _stockID = stockID;
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
     * 作業No.(配列)を返します。<BR>
     * 在庫移動RFTメンテナンス用
     * @return 作業No.(配列)を返します。
     */
    public String[] getJobNoArray()
    {
        return _jobNoArray;
    }

    /**
     * 作業No.(配列)を設定します。
     * @param jobNoArray 作業No.(配列)
     */
    public void setJobNoArray(String[] jobNoArray)
    {
        _jobNoArray = jobNoArray;
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
        return _StorageTime;
    }

    /**
     * 入庫時刻を設定します。
     * @param storageTime 入庫時刻
     */
    public void setStorageTime(String storageTime)
    {
        _StorageTime = storageTime;
    }

    /**
     * 入庫日時を返します。
     * @return 入庫日時を返します。
     */
    public java.util.Date getStorageDate()
    {
        return _StorageDate;
    }

    /**
     * 入庫日時を設定します。
     * @param storageDate 入庫日時
     */
    public void setStorageDate(java.util.Date storageDate)
    {
        _StorageDate = storageDate;
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
     * 作業日(to)を返します。
     * @return 作業日(to)を返します。
     */
    public String getToWorkDay()
    {
        return _toWorkDay;
    }

    /**
     * 作業日(to)を設定します。
     * @param toWorkDay 作業日(to)
     */
    public void setToWorkDay(String toWorkDay)
    {
        _toWorkDay = toWorkDay;
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
     * 作業時刻(to)を返します。
     * @return 作業時刻(to)を返します。
     */
    public String getToWorkTime()
    {
        return _toWorkTime;
    }

    /**
     * 作業時刻(to)を設定します。
     * @param toWorkTime 作業時刻(to)
     */
    public void setToWorkTime(String toWorkTime)
    {
        _toWorkTime = toWorkTime;
    }

    /**
     * 開始作業日時を返します。
     * @return 開始作業日時を返します。
     */
    public java.util.Date getFromWorkDate()
    {
        return _FromWorkDate;
    }

    /**
     * 開始作業日時を設定します。
     * @param fromWorkDate 開始作業日時
     */
    public void setFromWorkDate(java.util.Date fromWorkDate)
    {
        _FromWorkDate = fromWorkDate;
    }

    /**
     * 終了作業日時を返します。
     * @return 終了作業日時を返します。
     */
    public java.util.Date getToWorkDate()
    {
        return _ToWorkDate;
    }

    /**
     * 開始作業日時を設定します。
     * @param toWorkDate 終了作業日時
     */
    public void setToWorkDate(java.util.Date toWorkDate)
    {
        _ToWorkDate = toWorkDate;
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
     * 移動元エリア名称を返します。
     * @return 移動元エリア名称を返します。
     */
    public String getSourceAreaName()
    {
        return _sourceAreaName;
    }

    /**
     * 移動元エリア名称を設定します。
     * @param sourceAreaName 移動元エリア名称
     */
    public void setSourceAreaName(String sourceAreaName)
    {
        _sourceAreaName = sourceAreaName;
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
     * 移動元棚(to)を返します。
     * @return 移動元棚(to)を返します。
     */
    public String getToSourceLocation()
    {
        return _toSourceLocation;
    }

    /**
     * 移動元棚(to)を設定します。
     * @param toSourceLocation 移動元棚(to)
     */
    public void setToSourceLocation(String toSourceLocation)
    {
        _toSourceLocation = toSourceLocation;
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
     * 移動先エリア名称を返します。
     * @return 移動先エリア名称を返します。
     */
    public String getDestAreaName()
    {
        return _destAreaName;
    }

    /**
     * 移動先エリア名称を設定します。
     * @param destAreaName 移動先エリア名称
     */
    public void setDestAreaName(String destAreaName)
    {
        _destAreaName = destAreaName;
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
     * 移動先棚(to)を返します。
     * @return 移動先棚(to)を返します。
     */
    public String getToDestLocation()
    {
        return _toDestLocation;
    }

    /**
     * 移動先棚(to)を設定します。
     * @param toDestLocation 移動先棚(to)
     */
    public void setToDestLocation(String toDestLocation)
    {
        _toDestLocation = toDestLocation;
    }

    /**
     * 移動数を返します。
     * @return 移動数を返します。
     */
    public long getMovementQty()
    {
        return _movementQty;
    }

    /**
     * 移動数を設定します。
     * @param movementQty 移動数
     */
    public void setMovementQty(long movementQty)
    {
        _movementQty = movementQty;
    }

    /**
     * 移動ケース数を返します。
     * @return 移動ケース数を返します。
     */
    public int getMovementCaseQty()
    {
        return _movementCaseQty;
    }

    /**
     * 移動ケース数を設定します。
     * @param movementCaseQty 移動ケース数
     */
    public void setMovementCaseQty(int movementCaseQty)
    {
        _movementCaseQty = movementCaseQty;
    }

    /**
     * 移動ピース数を返します。
     * @return 移動ピース数を返します。
     */
    public int getMovementPieceQty()
    {
        return _movementPieceQty;
    }

    /**
     * 移動ピース数を設定します。
     * @param movementPieceQty 移動ピース数
     */
    public void setMovementPieceQty(int movementPieceQty)
    {
        _movementPieceQty = movementPieceQty;
    }

    /**
     * 移動可能数を返します。
     * @return 移動可能数を返します。
     */
    public long getAllocateQty()
    {
        return _allocateQty;
    }

    /**
     * 移動可能数を設定します。
     * @param allocateQty 移動可能数
     */
    public void setAllocateQty(long allocateQty)
    {
        _allocateQty = allocateQty;
    }

    /**
     * 移動可能ケース数を返します。
     * @return 移動可能ケース数を返します。
     */
    public int getAllocateCaseQty()
    {
        return _allocateCaseQty;
    }

    /**
     * 移動可能ケース数を設定します。
     * @param allocateCaseQty 移動可能ケース数
     */
    public void setAllocateCaseQty(int allocateCaseQty)
    {
        _allocateCaseQty = allocateCaseQty;
    }

    /**
     * 移動可能ピース数を返します。
     * @return 移動可能ピース数を返します。
     */
    public int getAllocatePieceQty()
    {
        return _allocatePieceQty;
    }

    /**
     * 移動可能ピース数を設定します。
     * @param allocatePieceQty 移動可能ピース数
     */
    public void setAllocatePieceQty(int allocatePieceQty)
    {
        _allocatePieceQty = allocatePieceQty;
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
     * 範囲フラグを返します。
     * @return 範囲フラグを返します。
     */
    public boolean isRangeFlag()
    {
        return _rangeFlag;
    }

    /**
     * 範囲フラグを設定します。
     * @param rangeFlag 範囲フラグ
     */
    public void setRangeFlag(boolean rangeFlag)
    {
        _rangeFlag = rangeFlag;
    }

    /**
     * 日時範囲指定フラグを返します。
     * @return 日時範囲指定フラグを返します。
     */
    public boolean isWorkDayRangeFlag()
    {
        return _workDayRangeFlag;
    }

    /**
     * 日時範囲指定フラグを設定します。
     * @param workDayRangeFlag 日時範囲指定フラグ
     */
    public void setWorkDayRangeFlag(boolean workDayRangeFlag)
    {
        _workDayRangeFlag = workDayRangeFlag;
    }

    /**
     * 移動元範囲フラグを返します。
     * @return 移動元範囲フラグを返します。
     */
    public boolean isSourceLocationRangeFlag()
    {
        return _sourceLocationRangeFlag;
    }

    /**
     * 移動元範囲フラグを設定します。
     * @param sourceLocationRangeFlag 移動元範囲フラグ
     */
    public void setSourceLocationRangeFlag(boolean sourceLocationRangeFlag)
    {
        _sourceLocationRangeFlag = sourceLocationRangeFlag;
    }

    /**
     * 移動先範囲フラグを返します。
     * @return 移動先範囲フラグを返します。
     */
    public boolean isDestLocationRangeFlag()
    {
        return _destLocationRangeFlag;
    }

    /**
     * 移動先範囲フラグを設定します。
     * @param destLocationRangeFlag 範囲フラグ
     */
    public void setDestLocationRangeFlag(boolean destLocationRangeFlag)
    {
        _destLocationRangeFlag = destLocationRangeFlag;
    }

    /**
     * 入出庫棚列検索フラグを返します。
     * @return 入出庫棚列検索フラグを返します。
     */
    public String getLocationColumnSearchFlag()
    {
        return _locationColumnSearchFlag;
    }

    /**
     * 入出庫棚列検索フラグを設定します。
     * @param locationColumnSearchFlag 入出庫棚列検索フラグ
     */
    public void setLocationColumnSearchFlag(String locationColumnSearchFlag)
    {
        _locationColumnSearchFlag = locationColumnSearchFlag;
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
     * 開始終了フラグ(入庫日)を返します。
     * @return 開始終了フラグ(入庫日)
     */
    public String getFromToDateFrag()
    {
        return _fromtoDateFrag;
    }

    /**
     * 開始終了フラグ(入庫日)を設定します。
     * @param fromtoDateFlag 開始終了フラグ(入庫日)
     */
    public void setFromToDateFlag(String fromtoDateFlag)
    {
        _fromtoDateFrag = fromtoDateFlag;
    }

    /**
     * 開始終了フラグ(棚)を返します。
     * @return 開始終了フラグ(棚)
     */
    public String getFromToLocationFrag()
    {
        return _fromtoLocationFrag;
    }

    /**
     * 開始終了フラグ(棚)を設定します。
     * @param fromtoLocationFlag 開始終了フラグ(棚)
     */
    public void setFromToLocationFlag(String fromtoLocationFlag)
    {
        _fromtoLocationFrag = fromtoLocationFlag;
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
     * キャンセルチェックボックスを返します。
     * @return キャンセルチェックボックスを返します。
     */
    public boolean isCancelCheck()
    {
        return _cancelCheck;
    }

    /**
     * キャンセルチェックボックスを設定します。
     * @param cancelCheck キャンセルチェックボックス
     */
    public void setCancelCheck(boolean cancelCheck)
    {
        _cancelCheck = cancelCheck;
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
     * 強制入庫区分を返します。
     * @return 強制入庫区分を返します。
     */
    public boolean getForceStrageFlag()
    {
        return _forceStrageFlag;
    }

    /**
     * 強制入庫区分を設定します。
     * @param forceStrageFlag 強制入庫区分
     */
    public void setForceStrageFlag(boolean forceStrageFlag)
    {
        _forceStrageFlag = forceStrageFlag;
    }

    /**
     * 空棚区分を返します。
     * @return 空棚区分を返します。
     */
    public String getEmpLocationFlag()
    {
        return _emptyLocationFlag;
    }

    /**
     * 空棚区分を設定します。
     * @param emptyLocationFlag 空棚区分
     */
    public void setEmpLocationFlag(String emptyLocationFlag)
    {
        _emptyLocationFlag = emptyLocationFlag;
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
     * 表示用終了日時を返します。
     * @return 表示用終了日時を返します。
     */
    public java.util.Date getDispDate()
    {
        return _toDispDate;
    }

    /**
     * 表示用終了日時を設定します。
     * @param dispDate 表示用終了日時
     */
    public void setDispDate(java.util.Date dispDate)
    {
        _toDispDate = dispDate;
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
     * 検索日を返します。
     * @return 検索日を返します。
     */
    public String getSearchDay()
    {
        return _searchDay;
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
     * 帳票再発行フラグを返します。
     * @return 帳票再発行フラグを返します。
     */
    public boolean isRePrintFlag()
    {
        return _rePrintFlag;
    }

    /**
     * 帳票再発行フラグを設定します。
     * @param rePrintFlag 帳票再発行フラグ
     */
    public void setRePrintFlag(boolean rePrintFlag)
    {
        _rePrintFlag = rePrintFlag;
    }

    /**
     * 移動先棚フラグを返します。
     * @return 移動先棚フラグを返します。
     */
    public boolean isDestLocationFlag()
    {
        return _destLocationFlag;
    }

    /**
     * 移動先棚フラグを設定します。
     * @param destLocationFlag 移動先棚フラグ
     */
    public void setDestLocationFlag(boolean destLocationFlag)
    {
        _destLocationFlag = destLocationFlag;
    }

    /**
     * 入荷作業フラグを返します。
     * @return 入荷作業フラグを返します。
     */
    public String getReceivingFlag()
    {
        return _receivingFlag;
    }

    /**
     * 入荷作業フラグを設定します。
     * @param receivingFlag 入荷作業フラグ
     */
    public void setReceivingFlag(String receivingFlag)
    {
        _receivingFlag = receivingFlag;
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
     * 全数チェックボックスを返します。
     * @return 全数チェックボックスを返します。
     */
    public boolean isAllQtyFlag()
    {
        return _allQtyFlag;
    }

    /**
     * 全数チェックボックスを設定します。
     * @param reasonName 全数チェックボックス
     */
    public void setAllQtyFlag(boolean allqtyflag)
    {
        _allQtyFlag = allqtyflag;
    }

    /**
     * 選択チェックボックスを返します。
     * @return 選択チェックボックスを返します。
     */
    public boolean isSelected()
    {
        return _selected;
    }

    /**
     * 選択チェックボックスを設定します。
     * @param reasonName 選択チェックボックス
     */
    public void setSelected(boolean selected)
    {
        _selected = selected;
    }
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.OutParameter;

/**
 * <CODE>StorageOutParameter</CODE>クラスは、入庫パッケージ内のスケジュール→画面間のパラメータの受渡しに使用します。<BR>
 * このクラスでは入庫パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>StorageOutParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     予定一意キー<BR>
 *     取込単位キー<BR>
 *     設定単位キー<BR>
 *     作業No.<BR>
 *     集約作業No.<BR>
 *     状態フラグ<BR>
 *     入庫予定日<BR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     伝票No.<BR>
 *     行No.<BR>
 *     作業枝番<BR>
 *     入庫エリアNo.<BR>
 *     実績エリアNo.<BR>
 *     入庫エリア名称<BR>
 *     入庫棚<BR>
 *     実績棚<BR>
 *     予定ロットNo.<BR>
 *     実績ロットNo.<BR>
 *     明細数<BR>
 *     予定商品数<BR>
 *     実績商品数<BR>
 *     ケース入数<BR>
 *     ボール入数<BR>
 *     予定数<BR>
 *     実績数<BR>
 *     予定ケース数<BR>
 *     予定ピース数<BR>
 *     実績ケース数<BR>
 *     実績ピース数<BR>
 *     欠品数<BR>
 *     欠品ケース数<BR>
 *     欠品ピース数<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     ボールITF<BR>
 *     入庫日<BR>
 *     進捗率<BR>
 *     作業日<BR>
 *     作業時刻<BR>
 *     実績報告区分<BR>
 *     棚表示形式<BR>
 *     ボタン制御フラグ<BR>
 *     在庫管理導入フラグ<BR>
 *     マスタ管理導入フラグ<BR>
 *     ケース入数変更フラグ<BR>
 *     集約条件<BR>
 *     登録日時<BR>
 *     最終更新日時<BR>
 * </DIR>
 * 
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  $Author: arai $
 */
public class StorageOutParameter
        extends OutParameter
{
    // Class variables -----------------------------------------------
    /**
     * 予定一意キー
     */
    private String _planUKey;

    /**
     * 取込単位キー
     */
    private String _loadUnitKey;

    /**
     * 設定単位キー
     */
    private String _settingUnitKey;

    /**
     * 作業No.
     */
    private String _jobNo;

    /**
     * 集約作業No.
     */
    private String _collectJobNo;

    /**
     * 状態フラグ
     */
    private String _statusFlag;

    /**
     * 入庫予定日
     */
    private String _storagePlanDay;

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
     * 入庫エリアNo.
     */
    private String _storageAreaNo;

    /**
     * 実績エリアNo.
     */
    private String _resultAreaNo;

    /**
     * 入庫エリア名称
     */
    private String _storageAreaName;

    /**
     * 入庫棚
     */
    private String _storageLocation;

    /**
     * 実績棚
     */
    private String _resultLocation;

    /**
     * 予定ロットNo.
     */
    private String _planLotNo;

    /**
     * 実績ロットNo.
     */
    private String _resultLotNo;

    /**
     * 明細数
     */
    private long _detailCnt;

    /**
     * 予定商品数
     */
    private long _planItemCnt;

    /**
     * 実績商品数
     */
    private long _resultItemCnt;

    /**
     * ケース入数
     */
    private int _enteringQty;

    /**
     * ボール入数
     */
    private int _bundleEnteringQty;

    /**
     * 予定数
     */
    private int _planQty;

    /**
     * 実績数
     */
    private int _resultQty;

    /**
     * 予定ケース数
     */
    private int _planCaseQty;

    /**
     * 予定ピース数
     */
    private int _planPieceQty;

    /**
     * 実績ケース数
     */
    private int _resultCaseQty;

    /**
     * 実績ピース数
     */
    private int _resultPieceQty;

    /**
     * 欠品数
     */
    private int _shortageQty;

    /**
     * 欠品ケース数
     */
    private int _shortageCaseQty;

    /**
     * 欠品ピース数
     */
    private int _shortagePieceQty;

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
     * 入庫日
     */
    private String _storageDay;

    /**
     * 進捗率
     */
    private double _progressRate;

    /**
     * 作業日
     */
    private String _workDay;

    /**
     * 作業時刻
     */
    private String _workTime;

    /**
     * 実績報告区分
     */
    private String _reportFlag;

    /**
     * 棚表示形式
     */
    private String _locationStyle;

    /**
     * ボタン制御フラグ
     */
    private String _buttonControlFlag;

    /**
     * 在庫管理導入フラグ
     */
    private boolean _stockFlag;

    /**
     * マスタ管理導入フラグ
     */
    private boolean _masterFlag;

    /**
     * 集約条件
     */
    private String _collectCondition;

    /**
     * ケース入数変更フラグ
     */
    private boolean _chgEnteringQty;

    /**
     * 登録日時
     */
    private java.util.Date _registDate;

    /**
     * 最終更新日時
     */
    private java.util.Date _lastUpdateDate;

    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 3208 $,$Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $");
    }

    /**
     * 予定一意キーを返します。
     * @return 予定一意キーを返します。
     */
    public String getPlanUKey()
    {
        return _planUKey;
    }

    /**
     * 予定一意キーを設定します。
     * @param planUKey 予定一意キー
     */
    public void setPlanUKey(String planUKey)
    {
        _planUKey = planUKey;
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
     * 集約作業No.を返します。
     * @return 集約作業No.を返します。
     */
    public String getCollectJobNo()
    {
        return _collectJobNo;
    }

    /**
     * 集約作業No.を設定します。
     * @param collectJobNo 集約作業No.
     */
    public void setCollectJobNo(String collectJobNo)
    {
        _collectJobNo = collectJobNo;
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
     * 入庫エリアNo.を返します。
     * @return 入庫エリアNo.を返します。
     */
    public String getStorageAreaNo()
    {
        return _storageAreaNo;
    }

    /**
     * 入庫エリアNo.を設定します。
     * @param storageAreaNo 入庫エリアNo.
     */
    public void setStorageAreaNo(String storageAreaNo)
    {
        _storageAreaNo = storageAreaNo;
    }

    /**
     * 実績エリアを返します。
     * @return 実績エリアを返します。
     */
    public String getResultAreaNo()
    {
        return _resultAreaNo;
    }

    /**
     * 実績エリアを設定します。
     * @param resultAreaNo 実績エリア
     */
    public void setResultAreaNo(String resultAreaNo)
    {
        _resultAreaNo = resultAreaNo;
    }

    /**
     * 入庫エリア名称を返します。
     * @return 入庫エリア名称を返します。
     */
    public String getStorageAreaName()
    {
        return _storageAreaName;
    }

    /**
     * 入庫エリア名称を設定します。
     * @param storageAreaName 入庫エリア名称
     */
    public void setStorageAreaName(String storageAreaName)
    {
        _storageAreaName = storageAreaName;
    }

    /**
     * 入庫棚を返します。
     * @return 入庫棚を返します。
     */
    public String getStorageLocation()
    {
        return _storageLocation;
    }

    /**
     * 入庫棚を設定します。
     * @param storageLocation 入庫棚
     */
    public void setStorageLocation(String storageLocation)
    {
        _storageLocation = storageLocation;
    }

    /**
     * 実績棚を返します。
     * @return 実績棚を返します。
     */
    public String getResultLocation()
    {
        return _resultLocation;
    }

    /**
     * 実績棚を設定します。
     * @param resultLocation 実績棚
     */
    public void setResultLocation(String resultLocation)
    {
        _resultLocation = resultLocation;
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
     * 明細数を返します。
     * @return 明細数を返します。
     */
    public long getDetailCnt()
    {
        return _detailCnt;
    }

    /**
     * 明細数を設定します。
     * @param detailCnt 明細数
     */
    public void setDetailCnt(long detailCnt)
    {
        _detailCnt = detailCnt;
    }

    /**
     * 予定商品数を返します。
     * @return 予定商品数を返します。
     */
    public long getPlanItemCnt()
    {
        return _planItemCnt;
    }

    /**
     * 予定商品数を設定します。
     * @param planItemCnt 予定商品数
     */
    public void setPlanItemCnt(long planItemCnt)
    {
        _planItemCnt = planItemCnt;
    }

    /**
     * 実績商品数を返します。
     * @return 実績商品数を返します。
     */
    public long getResultItemCnt()
    {
        return _resultItemCnt;
    }

    /**
     * 実績商品数を設定します。
     * @param resultItemCnt 実績商品数
     */
    public void setResultItemCnt(long resultItemCnt)
    {
        _resultItemCnt = resultItemCnt;
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
     * 欠品数を返します。
     * @return 欠品数を返します。
     */
    public int getShortageQty()
    {
        return _shortageQty;
    }

    /**
     * 欠品数を設定します。
     * @param shortageQty 欠品数
     */
    public void setShortageQty(int shortageQty)
    {
        _shortageQty = shortageQty;
    }

    /**
     * 欠品ケース数を返します。
     * @return 欠品ケース数を返します。
     */
    public int getShortageCaseQty()
    {
        return _shortageCaseQty;
    }

    /**
     * 欠品ケース数を設定します。
     * @param shortageCaseQty 欠品ケース数
     */
    public void setShortageCaseQty(int shortageCaseQty)
    {
        _shortageCaseQty = shortageCaseQty;
    }

    /**
     * 欠品ピース数を返します。
     * @return 欠品ピース数を返します。
     */
    public int getShortagePieceQty()
    {
        return _shortagePieceQty;
    }

    /**
     * 欠品ピース数を設定します。
     * @param shortagePieceQty 欠品ピース数
     */
    public void setShortagePieceQty(int shortagePieceQty)
    {
        _shortagePieceQty = shortagePieceQty;
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
     * 進捗率を返します。
     * @return 進捗率を返します。
     */
    public double getProgressRate()
    {
        return _progressRate;
    }

    /**
     * 進捗率を設定します。
     * @param progressRate 進捗率
     */
    public void setProgressRate(double progressRate)
    {
        _progressRate = progressRate;
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
     * ボタン制御フラグを返します。
     * @return ボタン制御フラグを返します。
     */
    public String getButtonControlFlag()
    {
        return _buttonControlFlag;
    }

    /**
     * ボタン制御フラグを設定します。
     * @param buttonControlFlag ボタン制御フラグ
     */
    public void setButtonControlFlag(String buttonControlFlag)
    {
        _buttonControlFlag = buttonControlFlag;
    }

    /**
     * 在庫管理導入フラグを返します。
     * @return 在庫管理導入フラグを返します。
     */
    public boolean isStockFlag()
    {
        return _stockFlag;
    }

    /**
     * 在庫管理導入フラグを設定します。
     * @param stockFlag 在庫管理導入フラグ
     */
    public void setStockFlag(boolean stockFlag)
    {
        _stockFlag = stockFlag;
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
     * ケース入数変更フラグを返します。
     * @return ケース入数変更フラグを返します。
     */
    public boolean isChgEnteringQty()
    {
        return _chgEnteringQty;
    }

    /**
     * ケース入数変更フラグを設定します。
     * @param chgEnteringQty ケース入数変更フラグ
     */
    public void setChgEnteringQty(boolean chgEnteringQty)
    {
        _chgEnteringQty = chgEnteringQty;
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

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

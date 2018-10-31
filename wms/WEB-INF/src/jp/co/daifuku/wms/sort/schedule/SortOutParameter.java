package jp.co.daifuku.wms.sort.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.OutParameter;

/**
 * <CODE>SortOutParameter</CODE>クラスは、仕分パッケージ内のスケジュール→画面間のパラメータの受渡しに使用します。<BR>
 * このクラスでは仕分パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
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
 * Designer : M.Itokawa <BR>
 * Maker : M.Itokawa <BR>
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2009/02/10 08:55:48 $
 * @author  $Author: arai $
 */
public class SortOutParameter
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
     * クロスドック連携キー
     */
    private String _crossDockUkey;

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
     * 仕入先コード
     */
    private String _supplierCode;

    /**
     * 仕入先名称
     */
    private String _supplierName;

    /**
     * 出荷先コード
     */
    private String _customerCode;

    /**
     * 出荷先名称
     */
    private String _customerName;

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
     * ケース入数
     */
    private int _enteringQty;

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
     * 予定日
     */
    private String _planDay;

    /**
     * 実績報告区分
     */
    private String _reportFlag;

    /**
     * 作業時刻
     */
    private String _workTime;

    /**
     * 登録日時
     */
    private java.util.Date _registDate;

    /**
     * 最終更新日時
     */
    private java.util.Date _lastUpdateDate;

    /**
     * 集約条件
     */
    private String _collectCondition;

    /**
     * バッチNo
     */
    private String _batchNo;

    /**
     * 仕分場所
     */
    private String _sortingPlace;

    /** ボール入数 */
    private int _bundleEnteringQty;

    /** 総仕分先数 */
    private int _sortingLocationQty;

    /** 総仕分数 */
    private int _sortingTotalQty;

    // Public methods ------------------------------------------------

    /**
     * キー項目のセットと内容の比較を行います。<br>
     * 比較の結果、内容が異なっていれば、このインスタンスの内容を パラメータの内容に置き換えます。
     * 
     * @param workInfo 比較対象の作業情報
     * @return 以下の内容を比較します。
     * <ul>
     * <li>仕分場所
     * <li>出荷先コード
     * <li>予定ロットNo
     * </ul>
     * @since 2008/4/4 add by Softecs.
     */
    public boolean setKeys(SortOutParameter workInfo)
    {
        boolean isEqual = true;
        // 仕分場所
        String wkstr = workInfo.getSortingPlace();
        if (!equals(getSortingPlace(), wkstr))
        {
            setSortingPlace(wkstr);
            isEqual &= false;
        }
        // 出荷先コード
        wkstr = workInfo.getCustomerCode();
        if (!equals(getCustomerCode(), wkstr))
        {
            setCustomerCode(wkstr);
            isEqual &= false;
        }
        // ロットNo.
        wkstr = workInfo.getPlanLotNo();
        if (!equals(getPlanLotNo(), wkstr))
        {
            setPlanLotNo(wkstr);
            isEqual &= false;
        }

        return isEqual;
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 1.1.1.1 $,$Date: 2009/02/10 08:55:48 $");
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
     * クロスドック連携キーを返します。
     * @return クロスドック連携キーを返します。
     */
    public String getCrossDockUkey()
    {
        return _crossDockUkey;
    }

    /**
     * クロスドック連携キーを設定します。
     * @param crossDockUkey crossDockUkey
     */
    public void setCrossDockUkey(String crossDockUkey)
    {
        _crossDockUkey = crossDockUkey;
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
     * 仕入先コードを返します。
     * @return 仕入先コードを返します。
     */
    public String getSupplierCode()
    {
        return _supplierCode;
    }

    /**
     * 仕入先コードを設定します。
     * @param supplierCode 仕入先コード
     */
    public void setSupplierCode(String supplierCode)
    {
        _supplierCode = supplierCode;
    }

    /**
     * 仕入先名称を返します。
     * @return 仕入先名称を返します。
     */
    public String getSupplierName()
    {
        return _supplierName;
    }

    /**
     * 仕入先名称を設定します。
     * @param supplierName 仕入先名称
     */
    public void setSupplierName(String supplierName)
    {
        _supplierName = supplierName;
    }

    /**
     * 仕入先コードを返します。
     * @return 仕入先コードを返します。
     */
    public String getCustomerCode()
    {
        return _customerCode;
    }

    /**
     * 仕入先コードを設定します。
     * @param customerCode customerCode
     */
    public void setCustomerCode(String customerCode)
    {
        _customerCode = customerCode;
    }

    /**
     * 仕入先名称を返します。
     * @return 仕入先名称を返します。
     */
    public String getCustomerName()
    {
        return _customerName;
    }

    /**
     * 仕入先名称を設定します。
     * @param customerName customerName
     */
    public void setCustomerName(String customerName)
    {
        _customerName = customerName;
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
     * 実績報告区分を返します。
     * @return reportFlagを返します。
     */
    public String getReportFlag()
    {
        return _reportFlag;
    }

    /**
     * 実績報告区分を設定します。
     * @param reportFlag reportFlag
     */
    public void setReportFlag(String reportFlag)
    {
        _reportFlag = reportFlag;
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
     * 予定日を返します。
     * @return planDayを返します。
     */
    public String getPlanDay()
    {
        return _planDay;
    }

    /**
     * 予定日を設定します。
     * @param planDay planDay
     */
    public void setPlanDay(String planDay)
    {
        _planDay = planDay;
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
     * バッチNoを返します。
     * @return バッチNoを返します。
     */
    public String getBatchNo()
    {
        return _batchNo;
    }

    /**
     * バッチNoを設定します。
     * @param batchNo バッチNo
     */
    public void setBatchNo(String batchNo)
    {
        _batchNo = batchNo;
    }

    /**
     * 仕分場所を返します。
     * @return 仕分場所を返します。
     */
    public String getSortingPlace()
    {
        return _sortingPlace;
    }

    /**
     * 仕分場所を設定します。
     * @param sortingPlace 仕分場所
     */
    public void setSortingPlace(String sortingPlace)
    {
        _sortingPlace = sortingPlace;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**
     * 二つのオブジェクトを比較します。<br>
     * 両方ともNULLの時は同じものと見なします。
     * 
     * @param o1 比較対象1
     * @param o2 比較対象2
     * @return 同じ内容のオブジェクトまたは両方ともnullのときtrue.
     * @since 2008/4/1 add by Softecs.
     */
    protected boolean equals(Object o1, Object o2)
    {
        if (o1 == null && o2 == null)
        {
            // 両方 null なら 同じものとみなす
            return true;
        }
        if (o1 == null)
        {
            // 片方だけ null なら異なったものと見なす
            return false;
        }
        if (o2 == null)
        {
            // 片方だけ null なら異なったものと見なす
            return false;
        }
        // 両方とも値を持つ場合は、内容の比較を行う
        return o1.equals(o2);
    }

    /**
     * bundleEnteringQtyを返します。
     * @return bundleEnteringQtyを返します。
     */
    public int getBundleEnteringQty()
    {
        return _bundleEnteringQty;
    }

    /**
     * bundleEnteringQtyを設定します。
     * @param bundleEnteringQty bundleEnteringQty
     */
    public void setBundleEnteringQty(int bundleEnteringQty)
    {
        _bundleEnteringQty = bundleEnteringQty;
    }

    /**
     * sortingLocationQtyを返します。
     * @return sortingLocationQtyを返します。
     */
    public int getSortingLocationQty()
    {
        return _sortingLocationQty;
    }

    /**
     * sortingLocationQtyを設定します。
     * @param sortingLocationQty sortingLocationQty
     */
    public void setSortingLocationQty(int sortingLocationQty)
    {
        _sortingLocationQty = sortingLocationQty;
    }

    /**
     * sortingTotalQtyを返します。
     * @return sortingTotalQtyを返します。
     */
    public int getSortingTotalQty()
    {
        return _sortingTotalQty;
    }

    /**
     * sortingTotalQtyを設定します。
     * @param sortingTotalQty sortingTotalQty
     */
    public void setSortingTotalQty(int sortingTotalQty)
    {
        _sortingTotalQty = sortingTotalQty;
    }


    // Private methods -----------------------------------------------
}
//end of class

package jp.co.daifuku.wms.sort.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.handler.Entity;

/**
 * <CODE>SortInParameter</CODE>クラスは、仕分パッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスでは仕分パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>SortInParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     検索対象テーブル<BR>
 *     荷主コード<BR>
 *     仕入先コード<BR>
 *     入荷伝票No<BR>
 *     商品コード<BR>
 *     状態フラグ<BR>
 *     入荷予定日<BR>
 *     入荷日(From)<BR>
 *     入荷日(To)<BR>
 *     ロットNo<BR>
 *     バッチNo.<BR>
 *     作業No.<BR>
 *     設定単位キー<BR>
 *     クロスドック連携キー<BR>
 *     予定一意キー<BR>
 *     ケース入数<BR>
 *     予定数<BR>
 *     実績数<BR>
 *     予定ケース数<BR>
 *     予定ピース数<BR>
 *     実績ケース数<BR>
 *     実績ピース数<BR>
 *     欠品数<BR>
 *     欠品ケース数<BR>
 *     欠品ピース数<BR>
 *     処理区分<BR>
 *     リストセル行番号<BR>
 *     最終更新日時<BR>
 *     ロック情報<BR>
 * </DIR>
 * 
 * Designer : M.Itokawa <BR>
 * Maker : M.Itokawa <BR>
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2009/02/10 08:55:48 $
 * @author  $Author: arai $
 */
public class SortInParameter
        extends InParameter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 画面名：仕分作業照会
     */
    public static final String DISPNAME_WORKINQ = "DISPNAME_WORKINQ";

    /**
     * 画面名：仕分一括確定
     */
    public static final String DISPNAME_COMPLETE = "DISPNAME_COMPLETE";


    // Class variables -----------------------------------------------
    /**
     * 検索対象テーブル
     */
    private String _searchTable;

    /**
     * 荷主コード
     */
    private String _consignorCode;

    /**
     * 仕入先コード
     */
    private String _supplierCode;

    /**
     * 出荷先コード
     */
    private String _customerCode;

    /**
     * 入荷伝票No
     */
    private String _recievingTicketNo;

    /**
     * 商品コード
     */
    private String _itemCode;

    /**
     * 状態フラグ
     */
    private String _statusFlag;

    /**
     * 入荷予定日
     */
    private String _planDay;

    /**
     * 入荷日(From)
     */
    private String _fromWorkDay;

    /**
     * 入荷日(To)
     */
    private String _toWorkDay;

    /**
     * ロットNo
     */
    private String _lotNo;

    /**
     * バッチNo
     */
    private String _batchNo;

    /**
     * 作業No.
     */
    private String _jobNo;

    /**
     * 設定単位キー
     */
    private String _settingUnitKey;

    /**
     * クロスドック連携キー
     */
    private String _crossDockUkey;

    /**
     * 予定一意キー
     */
    private String _planUKey;

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
     * 処理区分
     */
    private String _processType;

    /**
     * リストセル行番号
     */
    private int _rowNo;

    /**
     * 作業日
     */
    private String _workDay;

    /**
     * 最終更新日時
     */
    private java.util.Date _lastUpdateDate;

    /**
     * 範囲指定フラグ
     */
    private boolean _rangeFlag;

    /**
     * 検索ボタン判定フラグ
     */
    private String _fromToFlag;

    /**
     * 集約条件
     */
    private String _collectCondition;

    /**
     * ロック情報(ForUpdateでロックした際の取得情報を保持)
     */
    private Entity _lockEntity;

    /** スキャンコード */
    private String _scanItemCode;

    /** ミス回数 */
    private int _missCnt;

    /** 予定日 */
    private String _planDate;

    /** ロット選択フラグ */
    private String _lotSelectionFlag;

    /** 完了フラグ */
    private String _completionFlag;

    /** 集約作業No. */
    private String _collectJobNo;

    /** 作業秒数 */
    private int _workSeconds;

    /** 荷主名称 */
    private String _consignorName;

    /** 商品名称 */
    private String _itemName;

    /** 出荷先名称 */
    private String _customerName;

    /** 仕分場所 */
    private String _sortingPlace;

    // Constractor --------------------------------------------------
    /**
     * コンストラクタ <BR>
     * WmsUserInfoを指定します。
     * 
     * @param info WmsUserInfo
     */
    public SortInParameter(WmsUserInfo info)
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
        return ("$Revision: 1.1.1.1 $,$Date: 2009/02/10 08:55:48 $");
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
        this._searchTable = searchTable;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

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
        this._consignorCode = consignorCode;
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
        this._itemCode = itemCode;
    }

    /**
     * 入荷伝票Noを返します。
     * @return 入荷伝票Noを返します。
     */
    public String getRecievingTicketNo()
    {
        return _recievingTicketNo;
    }

    /**
     * 入荷伝票Noを設定します。
     * @param recievingTicketNo 入荷伝票No
     */
    public void setRecievingTicketNo(String recievingTicketNo)
    {
        this._recievingTicketNo = recievingTicketNo;
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
        this._statusFlag = statusFlag;
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
        this._supplierCode = supplierCode;
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
        this._customerCode = customerCode;
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
     * 予定日を設定します。
     * @param planDay 予定日
     */
    public void setPlanDay(String planDay)
    {
        this._planDay = planDay;
    }

    /**
     * 出荷日（from）を返します。
     * @return 出荷日（from）を返します。
     */
    public String getFromWorkDay()
    {
        return _fromWorkDay;
    }

    /**
     * 出荷日（from）を設定します。
     * @param fromWorkDay 出荷日（from）
     */
    public void setFromWorkDay(String fromWorkDay)
    {
        this._fromWorkDay = fromWorkDay;
    }

    /**
     * 出荷日（to）を返します。
     * @return 出荷日（to）を返します。
     */
    public String getToWorkDay()
    {
        return _toWorkDay;
    }

    /**
     * 出荷日（to）を設定します。
     * @param toWorkDay 出荷日（to）
     */
    public void setToWorkDay(String toWorkDay)
    {
        this._toWorkDay = toWorkDay;
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
        this._lotNo = lotNo;
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
        this._batchNo = batchNo;
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
     * @param crossDockUkey クロスドック連携キー
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
     * 予定一意キーを返します。
     * @return 予定一意キーを返します。
     */
    public String getPlanUKey()
    {
        return _planUKey;
    }

    /**
     * 予定一意キーを設定します。
     * @param planukey 予定一意キー
     */
    public void setPlanUKey(String planukey)
    {
        _planUKey = planukey;
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
     * 処理区分を返します。
     * @return 処理区分を返します。
     */
    public String getProcessType()
    {
        return _processType;
    }

    /**
     * 処理区分を設定します。
     * @param processType 処理区分
     */
    public void setProcessType(String processType)
    {
        _processType = processType;
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
     * リストセル行No.を返します。
     * @return リストセル行No.を返します。
     */
    public int getRowNo()
    {
        return _rowNo;
    }

    /**
     * リストセル行No.を設定します。
     * @param listcellRowNum リストセル行No.
     */
    public void setRowNo(int listcellRowNum)
    {
        _rowNo = listcellRowNum;
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
     * ロック情報を返します。
     * @return ロック情報を返します。
     */
    public Entity getLockEntity()
    {
        return _lockEntity;
    }

    /**
     * ロック情報を設定します。
     * @param lockEntity ロック情報
     */
    public void setLockEntity(Entity lockEntity)
    {
        _lockEntity = lockEntity;
    }


    /**
     * 範囲指定フラグを返します。
     * @return 出範囲フラグを返します。
     */
    public boolean getRangeFlag()
    {
        return _rangeFlag;
    }

    /**
     * 範囲指定フラグを設定します。
     * @param rangeFlag 範囲フラグ
     */
    public void setRangeFlag(boolean rangeFlag)
    {
        _rangeFlag = rangeFlag;
    }

    /**
     * 検索ボタン判定フラグを返します。
     * @return 検索ボタン判定フラグルを返します。
     */
    public String getFromToFlag()
    {
        return _fromToFlag;
    }

    /**
     * 検索対象テーブルを設定します。
     * @param fromToFlag 検索ボタン判定フラグ
     */
    public void setFromToFlag(String fromToFlag)
    {
        _fromToFlag = fromToFlag;
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
     * 二つのオブジェクトを比較します。<br>
     * 両方ともNULLの時は同じものと見なします。
     *
     * @param o1 比較対象1
     * @param o2 比較対象2
     * @return 同じ内容のオブジェクトまたは両方ともnullのときtrue.
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
     * missCntを返します。
     * @return missCntを返します。
     */
    public int getMissCnt()
    {
        return _missCnt;
    }

    /**
     * missCntを設定します。
     * @param missCnt missCnt
     */
    public void setMissCnt(int missCnt)
    {
        _missCnt = missCnt;
    }

    /**
     * scanItemCodeを返します。
     * @return scanItemCodeを返します。
     */
    public String getScanItemCode()
    {
        return _scanItemCode;
    }

    /**
     * scanItemCodeを設定します。
     * @param scanItemCode scanItemCode
     */
    public void setScanItemCode(String scanItemCode)
    {
        _scanItemCode = scanItemCode;
    }

    /**
     * planDateを返します。
     * @return planDateを返します。
     */
    public String getPlanDate()
    {
        return _planDate;
    }

    /**
     * planDateを設定します。
     * @param planDate planDate
     */
    public void setPlanDate(String planDate)
    {
        _planDate = planDate;
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
     * completionFlagを返します。
     * @return completionFlagを返します。
     */
    public String getCompletionFlag()
    {
        return _completionFlag;
    }

    /**
     * completionFlagを設定します。
     * @param completionFlag completionFlag
     */
    public void setCompletionFlag(String completionFlag)
    {
        _completionFlag = completionFlag;
    }

    /**
     * lotSelectionFlagを返します。
     * @return lotSelectionFlagを返します。
     */
    public String getLotSelectionFlag()
    {
        return _lotSelectionFlag;
    }

    /**
     * lotSelectionFlagを設定します。
     * @param lotSelectionFlag lotSelectionFlag
     */
    public void setLotSelectionFlag(String lotSelectionFlag)
    {
        _lotSelectionFlag = lotSelectionFlag;
    }

    /**
     * workSecondsを返します。
     * @return workSecondsを返します。
     */
    public int getWorkSeconds()
    {
        return _workSeconds;
    }

    /**
     * workSecondsを設定します。
     * @param workSeconds workSeconds
     */
    public void setWorkSeconds(int workSeconds)
    {
        _workSeconds = workSeconds;
    }

    /**
     * consignorNameを返します。
     * @return consignorNameを返します。
     */
    public String getConsignorName()
    {
        return _consignorName;
    }

    /**
     * consignorNameを設定します。
     * @param consignorName consignorName
     */
    public void setConsignorName(String consignorName)
    {
        _consignorName = consignorName;
    }

    /**
     * itemNameを返します。
     * @return itemNameを返します。
     */
    public String getItemName()
    {
        return _itemName;
    }

    /**
     * itemNameを設定します。
     * @param itemName itemName
     */
    public void setItemName(String itemName)
    {
        _itemName = itemName;
    }

    /**
     * customerNameを返します。
     * @return customerNameを返します。
     */
    public String getCustomerName()
    {
        return _customerName;
    }

    /**
     * customerNameを設定します。
     * @param customerName customerName
     */
    public void setCustomerName(String customerName)
    {
        _customerName = customerName;
    }

    /**
     * sortingPlaceを返します。
     * @return sortingPlaceを返します。
     */
    public String getSortingPlace()
    {
        return _sortingPlace;
    }

    /**
     * sortingPlaceを設定します。
     * @param sortingPlace sortingPlace
     */
    public void setSortingPlace(String sortingPlace)
    {
        _sortingPlace = sortingPlace;
    }

    // Private methods -----------------------------------------------
}
//end of class

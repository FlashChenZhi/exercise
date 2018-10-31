package jp.co.daifuku.wms.ship.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;

/**
 * <CODE>ReceivingInParameter</CODE>クラスは、出荷パッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスでは出荷パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>ShipInParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     検索対象テーブル<BR>
 *     荷主コード<BR>
 *     出荷予定日<BR>
 *     出荷日(From)<BR>
 *     出荷日(To)<BR>
 *     バッチNo<BR>
 *     出荷先コード<BR>
 *     バースNo<BR>
 *     状態フラグ<BR>
 *     範囲指定<BR>
 *     検索ボタン判定フラグ<BR>
 *     集約条件<BR>
 *     表示実績条件<BR>
 *     検品済フラグ<BR>
 *     リストセル行No.<BR>
 * </DIR>
 * 
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2009/02/10 08:55:48 $
 * @author  $Author: arai $
 */
public class ShipInParameter
        extends InParameter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

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
     * 出荷予定日
     */
    private String _planDay;

    /**
     * 出荷日(From)
     */
    private String _fromWorkDay;

    /**
     * 出荷日(To)
     */
    private String _toWorkDay;

    /**
     * バッチNo
     */
    private String _batchNo;

    /**
     * 出荷先コード
     */
    private String _customerCode;

    /**
     * バースNo
     */
    private String _berthNo;

    /**
     * 状態フラグ
     */
    private String _statusFlag;

    /**
     * バース登録フラグ
     */
    private String _berthStatusFlag;

    /**
     * 範囲指定フラグ
     */
    private boolean _rangeFlag;

    /**
     * 検索ボタン判定フラグ
     */
    private String _fromToFlag;

    /**
     * 実績表示条件
     */
    private String _dispResultStatus;

    /**
     * 集約条件
     */
    private String _collectCondition;

    /** 予定日 */
    private String _planDate;

    /** 設定単位キー */
    private String _settingUnitKey;

    /** 作業秒数 */
    private int _workSeconds;

    /** 集約作業No. */
    private String _collectJobNo;

    /** 実績数 */
    private int _resultQty;

    /** 完了フラグ */
    private String _completionFlag;

    /**
     * 検品済フラグ
     */
    private boolean _inspectionFlag;

    /**
     * リストセル行No.
     */
    private int _rowNo;

    /** ロットNo. */
    private String _lotNo;

    private String _jobDetails;

    private String _consignorName;

    private String _customerName;

    private int _inspectionErrCount;

    private String _ticketNo;

    private int _ticketLineNo;

    /** 商品コード */
    private String _itemCode;

    private String _jan;

    private String _itf;

    private String _bundleItf;

    private int _enteringQty;

    private int _bundleEnteringQty;

    private String _planLotNo;

    private int _planQty;

    private String _resultLotNo;

    private String _itemName;

    /** スキャン商品コード */
    private String _scanItemCode;
    
    // Constractor --------------------------------------------------
    /**
     * コンストラクタ <BR>
     * WmsUserInfoを指定します。
     * 
     * @param info WmsUserInfo
     */
    public ShipInParameter(WmsUserInfo info)
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
     * @param consignorCode consignorCode
     */
    public void setConsignorCode(String consignorCode)
    {
        this._consignorCode = consignorCode;
    }

    /**
     * 出荷日を返します。
     * @return 出荷日を返します。
     */
    public String getPlanDay()
    {
        return _planDay;
    }

    /**
     * 出荷日を設定します。
     * @param planDay planDay
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
     * @param fromWorkDay fromWorkDay
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
     * @param toWorkDay toWorkDay
     */
    public void setToWorkDay(String toWorkDay)
    {
        this._toWorkDay = toWorkDay;
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
     * @param batchNo batchNo
     */
    public void setBatchNo(String batchNo)
    {
        this._batchNo = batchNo;
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
     * @param customerCode customerCode
     */
    public void setCustomerCode(String customerCode)
    {
        this._customerCode = customerCode;
    }

    /**
     * バースNoを返します。
     * @return バースNoを返します。
     */
    public String getBerthNo()
    {
        return _berthNo;
    }

    /**
     * バースNoを設定します。
     * @param berthNo berthNo
     */
    public void setBerthNo(String berthNo)
    {
        this._berthNo = berthNo;
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
     * @param statusFlag statusFlag
     */
    public void setStatusFlag(String statusFlag)
    {
        this._statusFlag = statusFlag;
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
     * 表示実績条件を返します。
     * @return 表示実績条件を返します。
     */
    public String getDispResult()
    {
        return _dispResultStatus;
    }

    /**
     * 表示実績条件を設定します。
     * @param dispResultStatus 表示実績条件
     */
    public void setDispResult(String dispResultStatus)
    {
        _dispResultStatus = dispResultStatus;
    }

    /**
     * @return 予定日を返します。
     */
    public String getPlanDate()
    {
        return _planDate;
    }

    /**
     * 予定日を設定します。
     * @param planDate 予定日
     */
    public void setPlanDate(String planDate)
    {
        _planDate = planDate;
    }

    /**
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
     * @return 作業秒数を返します。
     */
    public int getWorkSeconds()
    {
        return _workSeconds;
    }

    /**
     * 作業秒数を設定します。
     * @param workSeconds 作業秒数
     */
    public void setWorkSeconds(int workSeconds)
    {
        _workSeconds = workSeconds;
    }

    /**
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
     * @return 検品済フラグを返します。
     */
    public boolean getInspectionFlag()
    {
        return _inspectionFlag;
    }

    /**
     * 検品済フラグを設定します。
     * @param inspectionFlag 検品済フラグ
     */
    public void setInspectionFlag(boolean inspectionFlag)
    {
        _inspectionFlag = inspectionFlag;
    }

    /**
     * リストセル行No.を返します。
     * 
     * @return リストセル行No.を返します。
     */
    public int getRowNo()
    {
        return _rowNo;
    }

    /**
     * リストセル行No.を設定します。
     * 
     * @param rowNo リストセル行No.
     */
    public void setRowNo(int rowNo)
    {
        _rowNo = rowNo;
    }

    /**
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
     * jobDetailsを返します。
     * @return jobDetailsを返します。
     */
    public String getJobDetails()
    {
        return _jobDetails;
    }

    /**
     * @param jobDetails
     * @since 2008-04-06 SOFTECS
     */
    public void setJobDetails(String jobDetails)
    {
        _jobDetails = jobDetails;
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
     * inspectionErrCountを返します。
     * @return inspectionErrCountを返します。
     */
    public int getInspectionErrCount()
    {
        return _inspectionErrCount;
    }

    /**
     * inspectionErrCountを設定します。
     * @param inspectionErrCount inspectionErrCount
     */
    public void setInspectionErrCount(int inspectionErrCount)
    {
        _inspectionErrCount = inspectionErrCount;
    }

    /**
     * ticketNoを返します。
     * @return ticketNoを返します。
     */
    public String getTicketNo()
    {
        return _ticketNo;
    }

    /**
     * ticketNoを設定します。
     * @param ticketNo ticketNo
     */
    public void setTicketNo(String ticketNo)
    {
        _ticketNo = ticketNo;
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
     * bundleItfを返します。
     * @return bundleItfを返します。
     */
    public String getBundleItf()
    {
        return _bundleItf;
    }

    /**
     * bundleItfを設定します。
     * @param bundleItf bundleItf
     */
    public void setBundleItf(String bundleItf)
    {
        _bundleItf = bundleItf;
    }

    /**
     * enteringQtyを返します。
     * @return enteringQtyを返します。
     */
    public int getEnteringQty()
    {
        return _enteringQty;
    }

    /**
     * enteringQtyを設定します。
     * @param enteringQty enteringQty
     */
    public void setEnteringQty(int enteringQty)
    {
        _enteringQty = enteringQty;
    }

    /**
     * itemCodeを返します。
     * @return itemCodeを返します。
     */
    public String getItemCode()
    {
        return _itemCode;
    }

    /**
     * itemCodeを設定します。
     * @param itemCode itemCode
     */
    public void setItemCode(String itemCode)
    {
        _itemCode = itemCode;
    }

    /**
     * itfを返します。
     * @return itfを返します。
     */
    public String getItf()
    {
        return _itf;
    }

    /**
     * itfを設定します。
     * @param itf itf
     */
    public void setItf(String itf)
    {
        _itf = itf;
    }

    /**
     * janを返します。
     * @return janを返します。
     */
    public String getJan()
    {
        return _jan;
    }

    /**
     * janを設定します。
     * @param jan jan
     */
    public void setJan(String jan)
    {
        _jan = jan;
    }

    /**
     * planLotNoを返します。
     * @return planLotNoを返します。
     */
    public String getPlanLotNo()
    {
        return _planLotNo;
    }

    /**
     * planLotNoを設定します。
     * @param planLotNo planLotNo
     */
    public void setPlanLotNo(String planLotNo)
    {
        _planLotNo = planLotNo;
    }

    /**
     * planQtyを返します。
     * @return planQtyを返します。
     */
    public int getPlanQty()
    {
        return _planQty;
    }

    /**
     * planQtyを設定します。
     * @param planQty planQty
     */
    public void setPlanQty(int planQty)
    {
        _planQty = planQty;
    }

    /**
     * resultLotNoを返します。
     * @return resultLotNoを返します。
     */
    public String getResultLotNo()
    {
        return _resultLotNo;
    }

    /**
     * resultLotNoを設定します。
     * @param resultLotNo resultLotNo
     */
    public void setResultLotNo(String resultLotNo)
    {
        _resultLotNo = resultLotNo;
    }

    /**
     * ticketLineNoを返します。
     * @return ticketLineNoを返します。
     */
    public int getTicketLineNo()
    {
        return _ticketLineNo;
    }

    /**
     * ticketLineNoを設定します。
     * @param ticketLineNo ticketLineNo
     */
    public void setTicketLineNo(int ticketLineNo)
    {
        _ticketLineNo = ticketLineNo;
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
     * バース登録状態を返します。
     * @return バース登録状態を返します。
     */
    public String getBerthStatusFlag()
    {
        return _berthStatusFlag;
    }

    /**
     * バース登録状態を設定します。
     * @param berthStatusFlag バース登録状態
     */
    public void setBerthStatusFlag(String berthStatusFlag)
    {
        _berthStatusFlag = berthStatusFlag;
    }

    /**
     * スキャン商品コードを返します。
     * @return スキャン商品コードを返します。
     */
    public String getScanItemCode()
    {
        return _scanItemCode;
    }

    /**
     * スキャン商品コードを設定します。
     * @param value スキャン商品コード
     */
    public void setScanItemCode(String value)
    {
        _scanItemCode = value;
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

    // Private methods -----------------------------------------------
}
//end of class

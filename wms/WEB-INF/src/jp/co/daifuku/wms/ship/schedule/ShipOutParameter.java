package jp.co.daifuku.wms.ship.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.OutParameter;
import jp.co.daifuku.wms.base.entity.ShipWorkInfo;

/**
 * <CODE>ShipOutParameter</CODE>クラスは、出荷パッケージ内のスケジュール→画面間のパラメータの受渡しに使用します。<BR>
 * このクラスでは出荷パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>ShipOutParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     出荷予定日<BR>
 *     作業日<BR>
 *     バッチNo<BR>
 *     出荷先コード<BR>
 *     出荷先名称<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     ケース入数<BR>
 *     予定数<BR>
 *     実績数<BR>
 *     欠品数<BR>
 *     予定ロットNo.<BR>
 *     実績ロットNo.<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     状態フラグ<BR>
 *     バースNo<BR>
 *     伝票No.<BR>
 *     行No.<BR>
 *     登録日時<BR>
 *     表示実績条件<BR>
 * </DIR>
 * 
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2009/02/10 08:55:48 $
 * @author  $Author: arai $
 */
public class ShipOutParameter
        extends OutParameter
{
    // Class variables -----------------------------------------------

    /**
     * 出荷予定日
     */
    private String _planDay;

    /**
     * 作業日
     */
    private String _workDay;

    /**
     * バッチNo
     */
    private String _batchNo;

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
     * 欠品数
     */
    private int _shortageQty;

    /**
     * 予定ロットNo.
     */
    private String _planLotNo;

    /**
     * 実績ロットNo.
     */
    private String _resultLotNo;

    /**
     * JANコード
     */
    private String _janCode;

    /**
     * ケースITF
     */
    private String _itf;

    /**
     * 状態フラグ
     */
    private String _statusFlag;

    /**
     * バースNo
     */
    private String _berthNo;

    /**
     * 伝票No.
     */
    private String _ticketNo;

    /**
     * 行No.
     */
    private int _lineNo;

    /**
     * 登録日時
     */
    private java.util.Date _registDate;

    /**
     * 集約条件
     */
    private String _collectCondition;

    /**
     * 実績表示条件
     */
    private String _dispResultStatus;

    private String _settingUnitKey;

    /**
     * 荷主名称
     */
    private String _consignorName;
    
    /**
     * 荷主コード
     */
    private String _consignorCode;

    private String _collectJobNo;

    private String _shipTicketNo;

    private int _shipLIneNo;

    private String _jan;

    private String _bundleItf;

    private int _bundleEnteringQty;

    private int _workTime;

    private int _totalItem;

    private int _totalQty;

    private int _totalPieceQty;

    /**
     * JOB NO
     */
    private String _jobNo;

    /**
     * 予定一意キー
     */
    private String _planUkey;
    
    /**
     * クロスドッグキー
     */
    private String _crossDockUkey;
    
    /**
     * バース作業状態
     */
    private String _berthStatusFlag;
 

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
     * 開始出荷作業情報を設定します。<br>
     * キー比較メソッドでは、商品コード、予定ロットNoがパラメータの同項目と
     * 異なる場合は、新しい項目を保持しfalseを返します。
     * @param workInfo 開始出荷作業情報
     * @return 同一キーを持つ出荷作業情報を保持していればtrueを返します。
     */
    public boolean setKeys(ShipWorkInfo workInfo)
    {
        boolean isEqual = true;
        // 商品コードチェック
        String wkstr = workInfo.getItemCode();
        if (!equals(getItemCode(), wkstr))
        {
            setItemCode(wkstr);
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

    /**
     * 開始出荷作業情報を設定します。<br>
     * キー比較メソッドでは、商品コード、予定ロットNoがパラメータの同項目と
     * 異なる場合は、新しい項目を保持しfalseを返します。
     * @param workInfo 開始出荷作業情報
     * @return 同一キーを持つ出荷作業情報を保持していればtrueを返します。
     */
    public boolean setKeys(ShipOutParameter workInfo)
    {
        boolean isEqual = true;
        // 商品コードチェック
        String wkstr = workInfo.getItemCode();
        if (!equals(getItemCode(), wkstr))
        {
            setItemCode(wkstr);
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
    
    /**
     * 出荷予定日を返します。
     * @return 出荷予定日を返します。
     */
    public String getPlanDay()
    {
        return _planDay;
    }

    /**
     * 出荷予定日を設定します。
     * @param planDay 出荷予定日
     */
    public void setPlanDay(String planDay)
    {
        _planDay = planDay;
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
     * バースNoを返します。
     * @return バースNoを返します。
     */
    public String getBerthNo()
    {
        return _berthNo;
    }

    /**
     * バースNoを設定します。
     * @param berthNo バースNo
     */
    public void setBerthNo(String berthNo)
    {
        _berthNo = berthNo;
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
     * consignorNameを返します。
     * @return consignorNameを返します。
     */
    public String getConsignorName()
    {
        return _consignorName;
    }

    /**
     * 荷主名称を返します。
     * @param consignorName 荷主名称
     */
    public void setConsignorName(String consignorName)
    {
        _consignorName = consignorName;
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
     * 荷主コードを返します。
     * @param consignorCode 荷主コード
     */
    public void setConsignorCode(String consignorCode)
    {
        _consignorCode = consignorCode;
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
     * 設定単位キーを返します。
     * @param collectJobNo 設定単位キー
     */
    public void setCollectJobNo(String collectJobNo)
    {
        _collectJobNo = collectJobNo;
    }

    /**
     * shipTicketNoを返します。
     * @return shipTicketNoを返します。
     */
    public String getShipTicketNo()
    {
        return _shipTicketNo;
    }

    /**
     * 出荷伝票Noを返します。
     * @param tkNo 出荷伝票No
     */
    public void setShipTicketNo(String tkNo)
    {
        _shipTicketNo = tkNo;
    }

    /**
     * shipLIneNoを返します。
     * @return shipLIneNoを返します。
     */
    public int getShipLIneNo()
    {
        return _shipLIneNo;
    }

    /**
     * 出荷伝票行No.を返します。
     * @param lineNo 出荷伝票行No.
     */
    public void setShipLIneNo(int lineNo)
    {
        _shipLIneNo = lineNo;
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
     * JANコードを設定ます。
     * @param jan JANコード
     */
    public void setJan(String jan)
    {
        _jan = jan;
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
     * ボールITFを設定ます。
     * @param bundleItf ボールITF
     */
    public void setBundleItf(String bundleItf)
    {
        _bundleItf = bundleItf;
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
     * ボール入数を設定ます。
     * @param bundleEnteringQty ボール入数
     */
    public void setBundleEnteringQty(int bundleEnteringQty)
    {
        _bundleEnteringQty = bundleEnteringQty;
    }

    /**
     * workTimeを返します。
     * @return workTimeを返します。
     */
    public int getWorkTime()
    {
        return _workTime;
    }

    /**
     * workTimeを設定します。
     * @param workTime workTime
     */
    public void setWorkTime(int workTime)
    {
        _workTime = workTime;
    }

    /**
     * totalItemを返します。
     * @return totalItemを返します。
     */
    public int getTotalItem()
    {
        return _totalItem;
    }

    /**
     * totalItemを設定します。
     * @param totalItem totalItem
     */
    public void setTotalItem(int totalItem)
    {
        _totalItem = totalItem;
    }

    /**
     * totalPieceQtyを返します。
     * @return totalPieceQtyを返します。
     */
    public int getTotalPieceQty()
    {
        return _totalPieceQty;
    }

    /**
     * totalPieceQtyを設定します。
     * @param totalPieceQty totalPieceQty
     */
    public void setTotalPieceQty(int totalPieceQty)
    {
        _totalPieceQty = totalPieceQty;
    }

    /**
     * totalQtyを返します。
     * @return totalQtyを返します。
     */
    public int getTotalQty()
    {
        return _totalQty;
    }

    /**
     * totalQtyを設定します。
     * @param totalQty totalQty
     */
    public void setTotalQty(int totalQty)
    {
        _totalQty = totalQty;
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
     * 予定一意キーを返します。
     * @return 予定一意キーを返します。
     */
    public String getPlanUkey()
    {
        return _planUkey;
    }

    /**
     * 予定一意キーを設定します。
     * @param planUkey 予定一意キー
     */
    public void setPlanUkey(String planUkey)
    {
        _planUkey = planUkey;
    }

    /**
     * jobNoを返します。
     * @return jobNoを返します。
     */
    public String getJobNo()
    {
        return _jobNo;
    }

    /**
     * jobNoを設定します。
     * @param jobNo jobNo
     */
    public void setJobNo(String jobNo)
    {
        _jobNo = jobNo;
    }
    
    /**
     * crossDockUkeyを返します。
     * @return crossDockUkeyを返します。
     */
    public String getCrossDockUkey()
    {
        return _crossDockUkey;
    }

    /**
     * crossDockUkeyを設定します。
     * @param crossDockUkey crossDockUkey
     */
    public void setCrossDockUkey(String crossDockUkey)
    {
        _crossDockUkey = crossDockUkey;
    }
    
    /**
     * berthStatusFlagを返します。
     * @return berthStatusFlagを返します。
     */
    public String getBerthStatusFlag()
    {
        return _berthStatusFlag;
    }

    /**
     * berthStatusFlagを設定します。
     * @param berthStatusFlag berthStatusFlag
     */
    public void setBerthStatusFlag(String berthStatusFlag)
    {
        _berthStatusFlag = berthStatusFlag;
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





    // Private methods -----------------------------------------------
}
//end of class

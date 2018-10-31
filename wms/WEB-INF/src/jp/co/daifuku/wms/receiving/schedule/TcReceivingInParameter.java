package jp.co.daifuku.wms.receiving.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;

import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.handler.Entity;

/**
 * <CODE>TcReceivingInParameter</CODE>クラスは、入荷パッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスでは入荷パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>ReceivingInParameter</CODE>クラスが保持する項目<BR>
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
 *     予定数<BR>
 *     実績数<BR>
 *     欠品数<BR>
 *     ロットNo<BR>
 *     作業No.<BR>
 *     集約作業No.<BR>
 *     設定単位キー<BR>
 *     クロスドック連携キー<BR>
 *     予定一意キー<BR>
 *     処理区分<BR>
 *     作業時間(秒)<BR>
 *     リストセル行No.<BR>
 *     最終更新日時<BR>
 *     最終更新処理名<BR>
 *     ロック情報<BR>
 * </DIR>
 * 
 * Designer : M.Itokawa <BR>
 * Maker : M.Itokawa <BR>
 *
 * @version $Revision: 911 $, $Date: 2008-10-29 10:37:54 +0900 (水, 29 10 2008) $
 * @author  $Author: matsuda $
 */
public class TcReceivingInParameter
        extends InParameter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    /**
     * 状態フラグ : 欠品確定
     */
    public static final String PROCESS_FLAG_SHORTAGE_DECISION = "1";

    /**
     * 状態フラグ : 欠品キャンセル
     */
    public static final String PROCESS_FLAG_SHORTAGE_CANCEL = "2";
    
    /**
     * 画面名：入荷作業照会
     */
    public static final String DISPNAME_WORKINQ = "DISPNAME_WORKINQ";

    /**
     * 画面名：入荷一括確定
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
     * ロットNo
     */
    private String _lotNo;

    /**
     * 作業No.
     */
    private String _jobNo;

    /**
     * 集約作業No.
     */
    private String _collectJobNo;

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
    private String _planukey;

    /**
     * 処理区分
     */
    private String _processFlag;

    /**
     * リストセル行No.
     */
    private int _rowNo;

    /**
     * 最終更新日時
     */
    private Date _lastUpdateDate;

    /**
     * 最終更新処理名
     */
    private String _lastUpdateName;

    /**
     * ロック情報(ForUpdateでロックした際の取得情報を保持)
     */
    private Entity _lockEntity;
    
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

    /** 作業完了フラグ */
    private String _completionFlag;

    /** 作業時間 */
    private int _workSeconds;

    /** 入荷伝票行番号 */
    private int _receivingTicketLineNo;

    /** ロット選択フラグ */
    private String _lotSelectionFlag;

    // Constractor --------------------------------------------------
    /**
     * コンストラクタ <BR>
     * WmsUserInfoを指定します。
     * 
     * @param info WmsUserInfo
     */
    public TcReceivingInParameter(WmsUserInfo info)
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
        return ("$Revision: 911 $,$Date: 2008-10-29 10:37:54 +0900 (水, 29 10 2008) $");
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
     * 商品コードを返します。
     * @return 商品コードを返します。
     */
    public String getItemCode()
    {
        return _itemCode;
    }

    /**
     * 商品コードを設定します。
     * @param itemCode itemCode
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
     * @param recievingTicketNo recievingTicketNo
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
     * @param statusFlag statusFlag
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
     * @param supplierCode supplierCode
     */
    public void setSupplierCode(String supplierCode)
    {
        this._supplierCode = supplierCode;
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
     * @param planDay planDay
     */
    public void setPlanDay(String planDay)
    {
        this._planDay = planDay;
    }

    /**
     * 入荷日（from）を返します。
     * @return 入荷日（from）を返します。
     */
    public String getFromWorkDay()
    {
        return _fromWorkDay;
    }

    /**
     * 入荷日（from）を設定します。
     * @param fromWorkDay fromWorkDay
     */
    public void setFromWorkDay(String fromWorkDay)
    {
        this._fromWorkDay = fromWorkDay;
    }

    /**
     * 入荷日（to）を返します。
     * @return 入荷日（to）を返します。
     */
    public String getToWorkDay()
    {
        return _toWorkDay;
    }

    /**
     * 入荷日（to）を設定します。
     * @param toWorkDay toWorkDay
     */
    public void setToWorkDay(String toWorkDay)
    {
        this._toWorkDay = toWorkDay;
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
     * @param planQty p予定数lanQty
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
     * ロットNo.を返します。
     * @return ロットNo.を返します。
     */
    public String getLotNo()
    {
        return _lotNo;
    }

    /**
     * ロットNo.を設定します。
     * @param lotNo lotNo
     */
    public void setLotNo(String lotNo)
    {
        this._lotNo = lotNo;
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
     * @param jobNo jobNo
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
     * 予定一意キーを返します。
     * @return 予定一意キーを返します。
     */
    public String getPlanukey()
    {
        return _planukey;
    }

    /**
     * 予定一意キーを設定します。
     * @param planukey planukey
     */
    public void setPlanukey(String planukey)
    {
        _planukey = planukey;
    }

    /**
     * 処理区分を返します。
     * @return 処理区分を返します。
     */
    public String getProcessFlag()
    {
        return _processFlag;
    }

    /**
     * 処理区分を設定します。
     * @param processFlag 処理区分
     */
    public void setProcessFlag(String processFlag)
    {
        _processFlag = processFlag;
    }

    /**
     * 作業時間 (秒)を返します。
     * @return 作業時間 (秒)を返します。
     */
    public int getWorkSeconds()
    {
        return _workSeconds;
    }

    /**
     * 作業時間 (秒)を設定します。
     * @param workSeconds 作業時間 (秒)
     */
    public void setWorkSeconds(int workSeconds)
    {
        _workSeconds = workSeconds;
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
     * @param settingUnitKey settingUnitKey
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
     * @param rowNo リストセル行No.
     */
    public void setRowNo(int rowNo)
    {
        _rowNo = rowNo;
    }

    /**
     * 最終更新日時を返します。
     * @return 最終更新日時を返します。
     */
    public Date getLastUpdateDate()
    {
        return _lastUpdateDate;
    }

    /**
     * 最終更新日時を設定します。
     * @param lastUpdateDate 最終更新日時
     */
    public void setLastUpdateDate(Date lastUpdateDate)
    {
        _lastUpdateDate = lastUpdateDate;
    }

    /**
     * 最終更新処理名を返します。
     * @return 最終更新処理名を返します。
     */
    public String getLastUpdateName()
    {
        return _lastUpdateName;
    }

    /**
     * 最終更新処理名を設定します。
     * @param lastUpdateName 最終更新処理名
     */
    public void setLastUpdateName(String lastUpdateName)
    {
        _lastUpdateName = lastUpdateName;
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
     * 作業完了フラグを設定します。
     * @param completionFlag 作業完了フラグ
     * @since 2008/4/1 add by Softecs.
     */
    public void setCompletionFlag(String completionFlag)
    {
        _completionFlag = completionFlag;
    }

    /**
     * @return 作業完了フラグを返します。
     * @since 2008/4/1 add by Softecs.
     */
    public String getCompletionFlag()
    {
        return _completionFlag;
    }

    /**
     * 入荷伝票行番号を設定します。
     * @param receivingTicketLineNo 入荷伝票行番号
     * @since 2008/4/2 add by Softecs.
     */
    public void setReceivingTicketLineNo(int receivingTicketLineNo)
    {
        _receivingTicketLineNo = receivingTicketLineNo;
    }

    /**
     * @return 入荷伝票行番号を返します。
     */
    public int getReceivingTicketLineNo()
    {
        return _receivingTicketLineNo;
    }

    /**
     * ロット選択フラグを返します。
     * @return ロット選択フラグを返します。
     * @since 2008/4/11 add by Softecs.
     */
    public String getLotSelectionFlag()
    {
        return _lotSelectionFlag;
    }

    /**
     * ロット選択フラグを設定します。
     * @param lotSelectionFlag ロット選択フラグ
     * @since 2008/4/11 add by Softecs.
     */
    public void setLotSelectionFlag(String lotSelectionFlag)
    {
        _lotSelectionFlag = lotSelectionFlag;
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

// $Id: RetrievalInParameter.java 6317 2009-12-02 08:35:16Z okamura $
package jp.co.daifuku.wms.retrieval.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.entity.WorkInfo;

/**
 * <CODE>RetrievalInParameter</CODE>クラスは、入庫パッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスでは出庫パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR> <CODE>RetrievalInParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 * 予定一意キー<BR>
 * 取込単位キー<BR>
 * 設定単位キー<BR>
 * 出庫開始単位キー<BR>
 * ホスト取消区分<BR>
 * 作業No.<BR>
 * 集約作業No.<BR>
 * 状態フラグ<BR>
 * 開始フラグ<BR>
 * 完了フラグ<BR>
 * 引当パターンNo.<BR>
 * 引当パターン名称<BR>
 * 引当パターン区分<BR>
 * 引当対象エリアNo.<BR>
 * 引当対象エリア名称<BR>
 * 優先順位<BR>
 * 補充元エリア区分<BR>
 * 出庫予定日<BR>
 * 出庫日<BR>
 * 出庫日(to)<BR>
 * 荷主コード<BR>
 * 荷主名称<BR>
 * バッチNo.<BR>
 * オーダーNo.<BR>
 * オーダーNo.(to)<BR>
 * 商品コード<BR>
 * 商品名称<BR>
 * 出荷先コード<BR>
 * 出荷先名称<BR>
 * 伝票No.<BR>
 * 行No.<BR>
 * 作業枝番<BR>
 * 出庫エリアNo.<BR>
 * 出庫棚<BR>
 * ロットNo.<BR>
 * ケース入数<BR>
 * 予定ケース数<BR>
 * 予定ピース数<BR>
 * 予定数<BR>
 * 実績ケース数<BR>
 * 実績ピース数<BR>
 * 実績数<BR>
 * 欠品ケース数<BR>
 * 欠品ピース数<BR>
 * JANコード<BR>
 * ケースITF<BR>
 * 出庫開始日時<BR>
 * 作業時間(秒)<BR>
 * ステーションNo.<BR>
 * ミス回数<BR>
 * リストセル行No.<BR>
 * 帳票発行フラグ<BR>
 * 出庫数の初期入力フラグ<BR>
 * 範囲指定フラグ<BR>
 * 開始終了フラグ<BR>
 * 欠品完了フラグ<BR>
 * 補充欠品フラグ<BR>
 * 処理フラグ<BR>
 * 集約条件<BR>
 * 検索対象テーブル<BR>
 * 最終更新日時<BR>
 * 入庫予定日（一括削除帳票用）<BR>
 * 取込単位キー（一括削除帳票用）<BR>
 * 最終更新日時（一括削除帳票用）<BR>
 * </DIR>
 * 
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 * 
 * @version $Revision: 6317 $, $Date: 2009-12-02 17:35:16 +0900 (水, 02 12 2009) $
 * @author $Author: okamura $
 */
// UPDATE_SS (2007-07-06)

public class RetrievalInParameter
        extends InParameter
{
    // Class variables -----------------------------------------------
    /**
     * 紐付く作業場・ステーションなし(平置エリアなど) : なし
     */
    public static final String SELECT_STATION_NONE = "STATIONNONE_";
    
    /**
     * 引当結果（未引当）
     */
    public static final String ALLOCATE_RESULT_UNSTART = "0";

    /**
     * 引当結果（引当済）
     */
    public static final String ALLOCATE_RESULT_COMPLETION = "1";

    /**
     * 引当結果（欠品完了）
     */
    public static final String ALLOCATE_RESULT_SHORTAGECOMP = "2";

    /**
     * 引当結果（欠品）
     */
    public static final String ALLOCATE_RESULT_SHORTAGE = "3";

    /**
     * 引当結果（エラー）
     */
    public static final String ALLOCATE_RESULT_ERROR = "4";

    /**
     * 引当結果（補充あり）
     */
    public static final String ALLOCATE_RESULT_REPLENISHMENT = "5";
    
    /**
     * 検索対象状態フラグ : 削除以外
     */
    public static final String PLAN_STATUS_NOT_DEL = "5";

    /**
     * 検索対象状態フラグ : 削除完了以外
     */
    public static final String PLAN_STATUS_NOT_DEL_AND_COMP = "6";
    
    /**
     * 欠品発生時の作業判定 : 伝票No.単位で作業を取消
     */
    public static final String SHORTAGE_WORK_TICKET_CANCEL = "0";
    
    /**
     * 欠品発生時の作業判定 : 可能な作業は出庫する
     */
    public static final String SHORTAGE_WORK_POSSIBLE_RETRIEVAL = "1";

    /**
     * 実績更新区分 : 出庫開始
     */
    public static final String RESULT_UPDATE_TYPE_RETRIEVAL_START = "0";
    
    /**
     * 実績更新区分 : 出庫確定
     */
    public static final String RESULT_UPDATE_TYPE_RETRIEVAL_CONFIRM = "1";
    
    /**
     * 実績更新区分 : スキップ
     */    
    public static final String RESULT_UPDATE_TYPE_RETRIEVAL_SKIP = "2";
    
    /**
     * 作業種別 : AS/RS出庫作業リスト
     */
    public static final String SEARCH_ASRS_RETRIEVAL_LIST = "01";
    
    /**
     * 作業種別 : 平置出庫作業リスト
     */
    public static final String SEARCH_FLOOR_RETRIEVAL_LIST = "02";
    
    /**
     * 作業種別 : 平置出庫作業リスト
     */
    public static final String SEARCH_INVENTORY_CHECK_LIST = "03";
    
    /**
     * 出庫引当順(ロットNo.昇順)
     */
    public static final int RETRIEVAL_ALLOCATE_PRIORITY_LOT_TRUE = 1;
    
    /**
     * 出庫引当順(ロットNo.無視)
     */
    public static final int RETRIEVAL_ALLOCATE_PRIORITY_LOT_FALSE = 2;

    /**
     * 商品コード指定出庫引当順(エリアの昇順)
     */
    public static final int ITEM_RET_ALLOC_PRIORITY_AREA_ASC = 1;

    /**
     * 商品コード指定出庫引当順(エリアの降順)
     */
    public static final int ITEM_RET_ALLOC_PRIORITY_AREA_DESC = 2;

    /**
     * 商品コード指定出庫引当順(入庫日時の古いもの(昇順))
     */
    public static final int ITEM_RET_ALLOC_PRIORITY_STRAGE_DATE_ASC = 3;

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
     * 出庫開始単位キー
     */
    private String _startUnitKey;

    /**
     * ホスト取消区分
     */
    private String _cancelFlag;

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
     * 開始フラグ
     */
    private String _schFlag;

    /**
     * 完了フラグ
     */
    private String _completionFlag;

    /**
     * 引当パターンNo.
     */
    private String _allocateNo;

    /**
     * 引当パターン名称
     */
    private String _allocateName;

    /**
     * 引当パターン区分
     */
    private String _allocateType;

    /**
     * 引当対象エリアNo.
     */
    private String _allocateAreaNo;

    /**
     * 修正前の引当対象エリアNo.
     */
    private String _allocateAreaNoBeforeUpdate;

    /**
     * 引当対象エリア名称
     */
    private String _allocateAreaName;

    /**
     * 優先順位
     */
    private int _allocatePriority;

    /**
     * 補充元エリア区分
     */
    private String _replenishmentAreaType;

    /**
     * 出庫予定日
     */
    private String _retrievalPlanDay;

    /**
     * 出庫日
     */
    private String _retrievalDay;

    /**
     * 出庫日(to)
     */
    private String _toRetrievalDay;

    /**
     * 荷主コード
     */
    private String _consignorCode;

    /**
     * 荷主名称
     */
    private String _consignorName;

    /**
     * バッチNo.
     */
    private String _batchNo;

    /**
     * オーダーNo.
     */
    private String _orderNo;

    /**
     * オーダーNo.(to)
     */
    private String _toOrderNo;

    /**
     * 商品コード
     */
    private String _itemCode;

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
     * 出庫エリアNo.
     */
    private String _retrievalAreaNo;

    /**
     * 出庫棚
     */
    private String _retrievalLocation;

    /**
     * ロットNo.
     */
    private String _lotNo;

    /**
     * ケース入数
     */
    private int _enteringQty;

    /**
     * 予定ケース数
     */
    private int _planCaseQty;

    /**
     * 予定ピース数
     */
    private int _planPieceQty;

    /**
     * 予定数
     */
    private int _planQty;

    /**
     * 実績ケース数
     */
    private int _resultCaseQty;

    /**
     * 実績ピース数
     */
    private int _resultPieceQty;

    /**
     * 実績数
     */
    private int _resultQty;

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
     * 出庫開始日時
     */
    private java.util.Date _retrievalStartDate;

    /**
     * 出庫終了日時
     */
    private java.util.Date _retrievalEndDate;

    /**
     * 作業時間(秒)
     */
    private int _workSeconds;
    
    /**
     * ステーションNo.
     */
    private String[] _stationNo;

    /**
     * ミス回数
     */
    private int _missCnt;

    /**
     * リストセル行No.
     */
    private int _rowNo;

    /**
     * 帳票発行フラグ
     */
    private boolean _printFlag;

    /**
     * 出庫数の初期入力フラグ
     */
    private boolean _retrievalQtyInputFlag;

    /**
     * 範囲指定フラグ
     */
    private boolean _rangeFlag;

    /**
     * 開始終了フラグ
     */
    private String _fromtoFrag;

    /**
     * 欠品完了フラグ
     */
    private boolean _shortageCompletionFlag;

    /**
     * 補充欠品フラグ
     */
    private boolean _replenishmentShortageFlag;

    /**
     * 処理フラグ
     */
    private String _processFlag;

    /**
     * 予定情報検索対象状態フラグ
     */
    private String _planStatus;

    /**
     * 集約条件
     */
    private String _collectCondition;

    /**
     * 検索対象テーブル
     */
    private String _searchTable;

    /**
     * 作業区分
     */
    private String _JobType;

    /**
     * 最終更新日時
     */
    private java.util.Date _lastUpdateDate;

    /**
     * 出庫予定日（一括削除帳票用）
     * 
     */
    private String[] _retrievalPlanDays;

    /**
     * 取込単位キー（一括削除帳票用）
     */
    private String[] _loadUnitKeys;

    /**
     * 最終更新日時（一括削除帳票用）
     */
    private java.util.Date[] _lastUpdateDates;

    /** 完了ロットNo. */
    private String _resultLotNo;

    // Constractor --------------------------------------------------
    /**
     * コンストラクタ <BR>
     * WmsUserInfoを指定します。
     * 
     * @param info WmsUserInfo
     */
    public RetrievalInParameter(WmsUserInfo info)
    {
        super();
        setWmsUserInfo(info);
    }
    
    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * 
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 6317 $,$Date: 2009-12-02 17:35:16 +0900 (水, 02 12 2009) $");
    }

    /**
     * キー項目のセットと内容の比較を行います。<br>
     * 比較の結果、内容が異なっていれば、このインスタンスの内容を パラメータの内容に置き換えます。
     * 
     * @param newWorkInfo 比較対象の入出庫作業情報
     * @return 以下の内容を比較します。<br>
     *         <li>予定棚
     *         <li>
     *         <li>商品コード
     *         <li>
     *         <li>予定ロットNo.
     *         <li>
     *         <li>オーダーNo.
     *         <li>
     */
    public boolean setKeys(WorkInfo newWorkInfo)
    {
        boolean isEqual = true;
        // 棚
        String wkstr = newWorkInfo.getPlanLocationNo();
        if (!equals(getRetrievalLocation(), wkstr))
        {
            setRetrievalLocation(wkstr);
            isEqual &= false;
        }
        // 商品コード
        wkstr = newWorkInfo.getItemCode();
        if (!equals(getItemCode(), wkstr))
        {
            setItemCode(wkstr);
            isEqual &= false;
        }
        // ロットNo.
        wkstr = newWorkInfo.getPlanLotNo();
        if (!equals(getLotNo(), wkstr))
        {
            setLotNo(wkstr);
            isEqual &= false;
        }
        // オーダーNo.
        wkstr = newWorkInfo.getOrderNo();
        if (!equals(getOrderNo(), wkstr))
        {
            setOrderNo(wkstr);
            isEqual &= false;
        }

        return isEqual;
    }

    /**
     * 予定一意キーを返します。
     * 
     * @return 予定一意キーを返します。
     */
    public String getPlanUKey()
    {
        return _planUKey;
    }

    /**
     * 予定一意キーを設定します。
     * 
     * @param planUKey 予定一意キー
     */
    public void setPlanUKey(String planUKey)
    {
        _planUKey = planUKey;
    }

    /**
     * 取込単位キーを返します。
     * 
     * @return 取込単位キーを返します。
     */
    public String getLoadUnitKey()
    {
        return _loadUnitKey;
    }

    /**
     * 取込単位キーを設定します。
     * 
     * @param loadUnitKey 取込単位キー
     */
    public void setLoadUnitKey(String loadUnitKey)
    {
        _loadUnitKey = loadUnitKey;
    }

    /**
     * 設定単位キーを返します。
     * 
     * @return 設定単位キーを返します。
     */
    public String getSettingUnitKey()
    {
        return _settingUnitKey;
    }

    /**
     * 設定単位キーを設定します。
     * 
     * @param settingUnitKey 設定単位キー
     */
    public void setSettingUnitKey(String settingUnitKey)
    {
        _settingUnitKey = settingUnitKey;
    }

    /**
     * 出庫開始単位キーを返します。
     * 
     * @return 出庫開始単位キーを返します。
     */
    public String getStartUnitKey()
    {
        return _startUnitKey;
    }

    /**
     * 出庫開始単位キーを設定します。
     * 
     * @param startUnitKey 出庫開始単位キー
     */
    public void setStartUnitKey(String startUnitKey)
    {
        _startUnitKey = startUnitKey;
    }

    /**
     * ホスト取消区分を返します。
     * 
     * @return ホスト取消区分を返します。
     */
    public String getCancelFlag()
    {
        return _cancelFlag;
    }

    /**
     * ホスト取消区分を設定します。
     * 
     * @param cancelFlag ホスト取消区分
     */
    public void setCancelFlag(String cancelFlag)
    {
        _cancelFlag = cancelFlag;
    }

    /**
     * 作業No.を返します。
     * 
     * @return 作業No.を返します。
     */
    public String getJobNo()
    {
        return _jobNo;
    }

    /**
     * 作業No.を設定します。
     * 
     * @param jobNo 作業No.
     */
    public void setJobNo(String jobNo)
    {
        _jobNo = jobNo;
    }

    /**
     * 集約作業No.を返します。
     * 
     * @return 集約作業No.を返します。
     */
    public String getCollectJobNo()
    {
        return _collectJobNo;
    }

    /**
     * 集約作業No.を設定します。
     * 
     * @param collectJobNo 集約作業No.
     */
    public void setCollectJobNo(String collectJobNo)
    {
        _collectJobNo = collectJobNo;
    }

    /**
     * 状態フラグを返します。
     * 
     * @return 状態フラグを返します。
     */
    public String getStatusFlag()
    {
        return _statusFlag;
    }

    /**
     * 状態フラグを設定します。
     * 
     * @param statusFlag 状態フラグ
     */
    public void setStatusFlag(String statusFlag)
    {
        _statusFlag = statusFlag;
    }

    /**
     * 開始フラグを返します。
     * 
     * @return 開始フラグを返します。
     */
    public String getSchFlag()
    {
        return _schFlag;
    }

    /**
     * 開始フラグを設定します。
     * 
     * @param schFlag 開始フラグ
     */
    public void setSchFlag(String schFlag)
    {
        _schFlag = schFlag;
    }

    /**
     * 完了フラグを返します。
     * 
     * @return 完了フラグを返します。
     */
    public String getCompletionFlag()
    {
        return _completionFlag;
    }

    /**
     * 完了フラグを設定します。
     * 
     * @param completionFlag 完了フラグ
     */
    public void setCompletionFlag(String completionFlag)
    {
        _completionFlag = completionFlag;
    }

    /**
     * 引当パターンNo.を返します。
     * 
     * @return 引当パターンNo.を返します。
     */
    public String getAllocateNo()
    {
        return _allocateNo;
    }

    /**
     * 引当パターンNo.を設定します。
     * 
     * @param allocateNo 引当パターンNo.
     */
    public void setAllocateNo(String allocateNo)
    {
        _allocateNo = allocateNo;
    }

    /**
     * 引当パターン名称を返します。
     * 
     * @return 引当パターン名称を返します。
     */
    public String getAllocateName()
    {
        return _allocateName;
    }

    /**
     * 引当パターン名称を設定します。
     * 
     * @param allocateName 引当パターン名称
     */
    public void setAllocateName(String allocateName)
    {
        _allocateName = allocateName;
    }

    /**
     * 引当パターン区分を返します。
     * 
     * @return 引当パターン区分を返します。
     */
    public String getAllocateType()
    {
        return _allocateType;
    }

    /**
     * 引当パターン区分を設定します。
     * 
     * @param allocateType 引当パターン区分
     */
    public void setAllocateType(String allocateType)
    {
        _allocateType = allocateType;
    }

    /**
     * 引当対象エリアを返します。
     * 
     * @return 引当対象エリアを返します。
     */
    public String getAllocateAreaNo()
    {
        return _allocateAreaNo;
    }

    /**
     * 引当対象エリアを設定します。
     * 
     * @param allocateAreaNo 引当パターン区分
     */
    public void setAllocateAreaNo(String allocateAreaNo)
    {
        _allocateAreaNo = allocateAreaNo;
    }

    /**
     * 修正前の引当対象エリアNo.を返します。
     * 
     * @return 修正前の引当対象エリアNo.を返します。
     */
    public String getAllocateAreaNoBeforeUpdate()
    {
        return _allocateAreaNoBeforeUpdate;
    }

    /**
     * 修正前の引当対象エリアNo.を設定します。
     * 
     * @param allocateAreaNoBeforeUpdate 修正前の引当対象エリアNo.
     */
    public void setAllocateAreaNoBeforeUpdate(String allocateAreaNoBeforeUpdate)
    {
        _allocateAreaNoBeforeUpdate = allocateAreaNoBeforeUpdate;
    }

    /**
     * 引当対象エリアを返します。
     * 
     * @return 引当対象エリアを返します。
     */
    public String getAllocateAreaName()
    {
        return _allocateAreaName;
    }

    /**
     * 引当対象エリアを設定します。
     * 
     * @param allocateAreaName 引当パターン区分
     */
    public void setAllocateAreaName(String allocateAreaName)
    {
        _allocateAreaName = allocateAreaName;
    }

    /**
     * 優先順位を返します。
     * 
     * @return 優先順位を返します。
     */
    public int getAllocatePriority()
    {
        return _allocatePriority;
    }

    /**
     * 優先順位を設定します。
     * 
     * @param allocatePriority 優先順位
     */
    public void setAllocatePriority(int allocatePriority)
    {
        _allocatePriority = allocatePriority;
    }

    /**
     * 補充元エリア区分を返します。
     * 
     * @return 補充元エリア区分を返します。
     */
    public String getReplenishmentAreaType()
    {
        return _replenishmentAreaType;
    }

    /**
     * 補充元エリア区分を設定します。
     * 
     * @param replenishmentAreaType 引当パターン区分
     */
    public void setReplenishmentAreaType(String replenishmentAreaType)
    {
        _replenishmentAreaType = replenishmentAreaType;
    }

    /**
     * 出庫予定日を返します。
     * 
     * @return 出庫予定日を返します。
     */
    public String getRetrievalPlanDay()
    {
        return _retrievalPlanDay;
    }

    /**
     * 出庫予定日を設定します。
     * 
     * @param retrievalPlanDay 出庫予定日
     */
    public void setRetrievalPlanDay(String retrievalPlanDay)
    {
        _retrievalPlanDay = retrievalPlanDay;
    }

    /**
     * 出庫日を返します。
     * 
     * @return 出庫日を返します。
     */
    public String getRetrievalDay()
    {
        return _retrievalDay;
    }

    /**
     * 出庫日を設定します。
     * 
     * @param retrievalDay 出庫日
     */
    public void setRetrievalDay(String retrievalDay)
    {
        _retrievalDay = retrievalDay;
    }

    /**
     * 出庫日(to)を返します。
     * 
     * @return 出庫日(to)を返します。
     */
    public String getToRetrievalDay()
    {
        return _toRetrievalDay;
    }

    /**
     * 出庫日(to)を設定します。
     * 
     * @param toRetrievalDay 出庫日(to)
     */
    public void setToRetrievalDay(String toRetrievalDay)
    {
        _toRetrievalDay = toRetrievalDay;
    }

    /**
     * 荷主コードを返します。
     * 
     * @return 荷主コードを返します。
     */
    public String getConsignorCode()
    {
        return _consignorCode;
    }

    /**
     * 荷主コードを設定します。
     * 
     * @param consignorCode 荷主コード
     */
    public void setConsignorCode(String consignorCode)
    {
        _consignorCode = consignorCode;
    }

    /**
     * 荷主名称を返します。
     * 
     * @return 荷主名称を返します。
     */
    public String getConsignorName()
    {
        return _consignorName;
    }

    /**
     * 荷主名称を設定します。
     * 
     * @param consignorName 荷主名称
     */
    public void setConsignorName(String consignorName)
    {
        _consignorName = consignorName;
    }

    /**
     * バッチNo.を返します。
     * 
     * @return バッチNo.を返します。
     */
    public String getBatchNo()
    {
        return _batchNo;
    }

    /**
     * バッチNo.を設定します。
     * 
     * @param batchNo バッチNo.
     */
    public void setBatchNo(String batchNo)
    {
        _batchNo = batchNo;
    }

    /**
     * オーダーNo.を返します。
     * 
     * @return オーダーNo.を返します。
     */
    public String getOrderNo()
    {
        return _orderNo;
    }

    /**
     * オーダーNo.を設定します。
     * 
     * @param orderNo オーダーNo.
     */
    public void setOrderNo(String orderNo)
    {
        _orderNo = orderNo;
    }

    /**
     * オーダーNo.(to)を返します。
     * 
     * @return オーダーNo.(to)を返します。
     */
    public String getToOrderNo()
    {
        return _toOrderNo;
    }

    /**
     * オーダーNo.(to)を設定します。
     * 
     * @param toOrderNo オーダーNo.(to)
     */
    public void setToOrderNo(String toOrderNo)
    {
        _toOrderNo = toOrderNo;
    }

    /**
     * 商品コードを返します。
     * 
     * @return 商品コードを返します。
     */
    public String getItemCode()
    {
        return _itemCode;
    }

    /**
     * 商品コードを設定します。
     * 
     * @param itemCode 商品コード
     */
    public void setItemCode(String itemCode)
    {
        _itemCode = itemCode;
    }

    /**
     * 商品名称を返します。
     * 
     * @return 商品名称を返します。
     */
    public String getItemName()
    {
        return _itemName;
    }

    /**
     * 商品名称を設定します。
     * 
     * @param itemName 商品名称
     */
    public void setItemName(String itemName)
    {
        _itemName = itemName;
    }

    /**
     * 出荷先コードを返します。
     * 
     * @return 出荷先コードを返します。
     */
    public String getCustomerCode()
    {
        return _customerCode;
    }

    /**
     * 出荷先コードを設定します。
     * 
     * @param customerCode 出荷先コード
     */
    public void setCustomerCode(String customerCode)
    {
        _customerCode = customerCode;
    }

    /**
     * 出荷先名称を返します。
     * 
     * @return 出荷先名称を返します。
     */
    public String getCustomerName()
    {
        return _customerName;
    }

    /**
     * 出荷先名称を設定します。
     * 
     * @param customerName 出荷先名称
     */
    public void setCustomerName(String customerName)
    {
        _customerName = customerName;
    }

    /**
     * 伝票No.を返します。
     * 
     * @return 伝票No.を返します。
     */
    public String getTicketNo()
    {
        return _ticketNo;
    }

    /**
     * 伝票No.を設定します。
     * 
     * @param ticketNo 伝票No.
     */
    public void setTicketNo(String ticketNo)
    {
        _ticketNo = ticketNo;
    }

    /**
     * 行No.を返します。
     * 
     * @return 行No.を返します。
     */
    public int getLineNo()
    {
        return _lineNo;
    }

    /**
     * 行No.を設定します。
     * 
     * @param lineNo 行No.
     */
    public void setLineNo(int lineNo)
    {
        _lineNo = lineNo;
    }

    /**
     * 作業枝番を返します。
     * 
     * @return 作業枝番を返します。
     */
    public int getBranchNo()
    {
        return _branchNo;
    }

    /**
     * 作業枝番を設定します。
     * 
     * @param branchNo 作業枝番
     */
    public void setBranchNo(int branchNo)
    {
        _branchNo = branchNo;
    }

    /**
     * 出庫エリアNo.を返します。
     * 
     * @return 出庫エリアNo.を返します。
     */
    public String getRetrievalAreaNo()
    {
        return _retrievalAreaNo;
    }

    /**
     * 出庫エリアNo.を設定します。
     * 
     * @param retrievalAreaNo 出庫エリアNo.
     */
    public void setRetrievalAreaNo(String retrievalAreaNo)
    {
        _retrievalAreaNo = retrievalAreaNo;
    }

    /**
     * 出庫棚を返します。
     * 
     * @return 出庫棚を返します。
     */
    public String getRetrievalLocation()
    {
        return _retrievalLocation;
    }

    /**
     * 出庫棚を設定します。
     * 
     * @param retrievalLocation 出庫棚
     */
    public void setRetrievalLocation(String retrievalLocation)
    {
        _retrievalLocation = retrievalLocation;
    }

    /**
     * ロットNo.を返します。
     * 
     * @return ロットNo.を返します。
     */
    public String getLotNo()
    {
        return _lotNo;
    }

    /**
     * ロットNo.を設定します。
     * 
     * @param lotNo ロットNo.
     */
    public void setLotNo(String lotNo)
    {
        _lotNo = lotNo;
    }

    /**
     * 完了ロットNo.を取得します。
     * 
     * @return 完了ロットNo.
     */
    public String getResultLotNo()
    {
        return _resultLotNo;
    }

    /**
     * 完了ロットNo.を設定します。
     * 
     * @param lotNo 完了ロットNo.
     */
    public void setResultLotNo(String lotNo)
    {
        _resultLotNo = lotNo;
    }

    /**
     * ケース入数を返します。
     * 
     * @return ケース入数を返します。
     */
    public int getEnteringQty()
    {
        return _enteringQty;
    }

    /**
     * ケース入数を設定します。
     * 
     * @param enteringQty ケース入数
     */
    public void setEnteringQty(int enteringQty)
    {
        _enteringQty = enteringQty;
    }

    /**
     * 予定ケース数を返します。
     * 
     * @return 予定ケース数を返します。
     */
    public int getPlanCaseQty()
    {
        return _planCaseQty;
    }

    /**
     * 予定ケース数を設定します。
     * 
     * @param planCaseQty 予定ケース数
     */
    public void setPlanCaseQty(int planCaseQty)
    {
        _planCaseQty = planCaseQty;
    }

    /**
     * 予定数を返します。
     * 
     * @return 予定数を返します。
     */
    public int getPlanQty()
    {
        return _planQty;
    }

    /**
     * 予定数を設定します。
     * 
     * @param planQty 予定数
     */
    public void setPlanQty(int planQty)
    {
        _planQty = planQty;
    }

    /**
     * 予定ピース数を返します。
     * 
     * @return 予定ピース数を返します。
     */
    public int getPlanPieceQty()
    {
        return _planPieceQty;
    }

    /**
     * 予定ピース数を設定します。
     * 
     * @param planPieceQty 予定ピース数
     */
    public void setPlanPieceQty(int planPieceQty)
    {
        _planPieceQty = planPieceQty;
    }

    /**
     * 実績ケース数を返します。
     * 
     * @return 実績ケース数を返します。
     */
    public int getResultCaseQty()
    {
        return _resultCaseQty;
    }

    /**
     * 実績ケース数を設定します。
     * 
     * @param resultCaseQty 実績ケース数
     */
    public void setResultCaseQty(int resultCaseQty)
    {
        _resultCaseQty = resultCaseQty;
    }

    /**
     * 実績ピース数を返します。
     * 
     * @return 実績ピース数を返します。
     */
    public int getResultPieceQty()
    {
        return _resultPieceQty;
    }

    /**
     * 実績ピース数を設定します。
     * 
     * @param resultPieceQty 実績ピース数
     */
    public void setResultPieceQty(int resultPieceQty)
    {
        _resultPieceQty = resultPieceQty;
    }

    /**
     * 実績数を返します。
     * 
     * @return 実績数を返します。
     */
    public int getResultQty()
    {
        return _resultQty;
    }

    /**
     * 実績数を設定します。
     * 
     * @param resultQty 実績数
     */
    public void setResultQty(int resultQty)
    {
        _resultQty = resultQty;
    }

    /**
     * 欠品ケース数を返します。
     * 
     * @return 欠品ケース数を返します。
     */
    public int getShortageCaseQty()
    {
        return _shortageCaseQty;
    }

    /**
     * 欠品ケース数を設定します。
     * 
     * @param shortageCaseQty 欠品ケース数
     */
    public void setShortageCaseQty(int shortageCaseQty)
    {
        _shortageCaseQty = shortageCaseQty;
    }

    /**
     * 欠品ピース数を返します。
     * 
     * @return 欠品ピース数を返します。
     */
    public int getShortagePieceQty()
    {
        return _shortagePieceQty;
    }

    /**
     * 欠品ピース数を設定します。
     * 
     * @param shortagePieceQty 欠品ピース数
     */
    public void setShortagePieceQty(int shortagePieceQty)
    {
        _shortagePieceQty = shortagePieceQty;
    }

    /**
     * JANコードを返します。
     * 
     * @return JANコードを返します。
     */
    public String getJanCode()
    {
        return _janCode;
    }

    /**
     * JANコードを設定します。
     * 
     * @param janCode JANコード
     */
    public void setJanCode(String janCode)
    {
        _janCode = janCode;
    }

    /**
     * ケースITFを返します。
     * 
     * @return ケースITFを返します。
     */
    public String getItf()
    {
        return _itf;
    }

    /**
     * ケースITFを設定します。
     * 
     * @param itf ケースITF
     */
    public void setItf(String itf)
    {
        _itf = itf;
    }

    /**
     * 出庫開始日を返します。
     * 
     * @return 出庫開始日を返します。
     */
    public java.util.Date getRetrievalStartDate()
    {
        return _retrievalStartDate;
    }

    /**
     * 出庫開始日を設定します。
     * 
     * @param retrievalStartDate 出庫開始日
     */
    public void setRetrievalStartDate(java.util.Date retrievalStartDate)
    {
        _retrievalStartDate = retrievalStartDate;
    }

    /**
     * 出庫終了日を返します。
     * 
     * @return 出庫終了日を返します。
     */
    public java.util.Date getRetrievalEndDate()
    {
        return _retrievalEndDate;
    }

    /**
     * 出庫終了日を設定します。
     * 
     * @param retrievalEndDate 出庫終了日
     */
    public void setRetrievalEndDate(java.util.Date retrievalEndDate)
    {
        _retrievalEndDate = retrievalEndDate;
    }

    /**
     * 作業時間(秒)を返します。
     * 
     * @return 作業時間(秒)を返します。
     */
    public int getWorkSeconds()
    {
        return _workSeconds;
    }

    /**
     * 作業時間(秒)を設定します。
     * 
     * @param workSeconds 作業時間(秒)
     */
    public void setWorkSeconds(int workSeconds)
    {
        _workSeconds = workSeconds;
    }

    /**
     * ステーションNo.を返します。
     * 
     * @return ステーションNo.を返します。
     */
    public String[] getStationNo()
    {
        return _stationNo;
    }

    /**
     * ステーションNo.を設定します。
     * 
     * @param stationNo ステーションNo.
     */
    public void setStationNo(String[] stationNo)
    {
        _stationNo = stationNo;
    }
    
    /**
     * ミス回数を返します。
     * 
     * @return ミス回数を返します。
     */
    public int getMissCnt()
    {
        return _missCnt;
    }

    /**
     * ミス回数を設定します。
     * 
     * @param missCnt ミス回数
     */
    public void setMissCnt(int missCnt)
    {
        _missCnt = missCnt;
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
     * 帳票発行フラグを返します。
     * 
     * @return 帳票発行フラグを返します。
     */
    public boolean isPrintFlag()
    {
        return _printFlag;
    }

    /**
     * 帳票発行フラグを設定します。
     * 
     * @param printFlag 帳票発行フラグ
     */
    public void setPrintFlag(boolean printFlag)
    {
        _printFlag = printFlag;
    }

    /**
     * 出庫数の初期入力フラグを返します。
     * 
     * @return 出庫数の初期入力フラグを返します。
     */
    public boolean isRetrievalQtyInputFlag()
    {
        return _retrievalQtyInputFlag;
    }

    /**
     * 出庫数の初期入力フラグを設定します。
     * 
     * @param retrievalQtyInputFlag 出庫数の初期入力フラグ
     */
    public void setRetrievalQtyInputFlag(boolean retrievalQtyInputFlag)
    {
        _retrievalQtyInputFlag = retrievalQtyInputFlag;
    }

    /**
     * 範囲フラグを返します。
     * 
     * @return 出範囲フラグを返します。
     */
    public boolean isRangeFlag()
    {
        return _rangeFlag;
    }

    /**
     * 範囲フラグを設定します。
     * 
     * @param rangeFlag 範囲フラグ
     */
    public void setRangeFlag(boolean rangeFlag)
    {
        _rangeFlag = rangeFlag;
    }

    /**
     * 開始終了フラグを返します。
     * 
     * @return 開始終了フラグ
     */
    public String getFromToFrag()
    {
        return _fromtoFrag;
    }

    /**
     * 開始終了フラグを設定します。
     * 
     * @param fromtoFlag 開始終了フラグ
     */
    public void setFromToFlag(String fromtoFlag)
    {
        _fromtoFrag = fromtoFlag;
    }

    /**
     * 欠品完了フラグを返します。
     * 
     * @return 欠品完了フラグを返します。
     */
    public boolean isShortageCompletionFlag()
    {
        return _shortageCompletionFlag;
    }

    /**
     * 欠品完了フラグを設定します。
     * 
     * @param shortageCompletionFlag 欠品完了フラグ
     */
    public void setShortageCompletionFlag(boolean shortageCompletionFlag)
    {
        _shortageCompletionFlag = shortageCompletionFlag;
    }

    /**
     * 補充欠品フラグを返します。
     * 
     * @return 補充欠品フラグを返します。
     */
    public boolean isReplenishmentShortageFlag()
    {
        return _replenishmentShortageFlag;
    }

    /**
     * 補充欠品フラグを設定します。
     * 
     * @param replenishmentShortageFlag 補充欠品フラグ
     */
    public void setReplenishmentShortageFlag(boolean replenishmentShortageFlag)
    {
        _replenishmentShortageFlag = replenishmentShortageFlag;
    }

    /**
     * 処理フラグを返します。
     * 
     * @return 処理フラグを返します。
     */
    public String getProcessFlag()
    {
        return _processFlag;
    }

    /**
     * 処理フラグを設定します。
     * 
     * @param processFlag 処理フラグ
     */
    public void setProcessFlag(String processFlag)
    {
        _processFlag = processFlag;
    }

    /**
     * 予定情報検索対象状態フラグを返します。
     * 
     * @return 処理フラグを返します。
     */
    public String getPlanStatus()
    {
        return _planStatus;
    }

    /**
     * 予定情報検索対象状態フラグを設定します。
     * 
     * @param planStatus 処理フラグ
     */
    public void setPlanStatus(String planStatus)
    {
        _planStatus = planStatus;
    }

    /**
     * 集約条件を返します。
     * 
     * @return 集約条件を返します。
     */
    public String getCollectCondition()
    {
        return _collectCondition;
    }

    /**
     * 集約条件を設定します。
     * 
     * @param collectCondition 集約条件
     */
    public void setCollectCondition(String collectCondition)
    {
        _collectCondition = collectCondition;
    }

    /**
     * 検索対象テーブルを返します。
     * 
     * @return 検索対象テーブルを返します。
     */
    public String getSearchTable()
    {
        return _searchTable;
    }

    /**
     * 検索対象テーブルを設定します。
     * 
     * @param searchTable 検索対象テーブル
     */
    public void setSearchTable(String searchTable)
    {
        _searchTable = searchTable;
    }

    /**
     * 作業区分を返します。
     * 
     * @return 作業区分テーブルを返します。
     */
    public String getJobType()
    {
        return _JobType;
    }

    /**
     * 作業区分を設定します。
     * 
     * @param jobType 作業区分
     */
    public void setJobType(String jobType)
    {
        _JobType = jobType;
    }

    /**
     * 最終更新日時を返します。
     * 
     * @return lastUpdateDateを返します。
     */
    public java.util.Date getLastUpdateDate()
    {
        return _lastUpdateDate;
    }

    /**
     * 最新更新日時を設定します。
     * 
     * @param lastUpdateDate lastUpdateDate
     */
    public void setLastUpdateDate(java.util.Date lastUpdateDate)
    {
        _lastUpdateDate = lastUpdateDate;
    }

    /**
     * 出庫予定日を取得します。
     * 
     * @return 入庫予定日
     */
    public String[] getPlanDays()
    {
        return _retrievalPlanDays;
    }

    /**
     * 出庫予定日に値をセットします。
     * 
     * @param plandate String[] セットする入庫予定日
     */
    public void setPlanDays(String[] plandate)
    {
        _retrievalPlanDays = plandate;
    }

    /**
     * 取込単位キーを返します。
     * 
     * @return 取込単位キーを返します。
     */
    public String[] getLoadUnitKeys()
    {
        return _loadUnitKeys;
    }

    /**
     * 取込単位キーを設定します。
     * 
     * @param loadUnitKey 取込単位キー
     */
    public void setLoadUnitKeys(String[] loadUnitKey)
    {
        _loadUnitKeys = loadUnitKey;
    }

    /**
     * 最終更新日時を返します。
     * 
     * @return 最終更新日時を返します。
     */
    public java.util.Date[] getLastUpdateDates()
    {
        return _lastUpdateDates;
    }

    /**
     * 最終更新日時を設定します。
     * 
     * @param lastUpdateDate 最終更新日時
     */
    public void setLastUpdateDates(java.util.Date[] lastUpdateDate)
    {
        _lastUpdateDates = lastUpdateDate;
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
// end of class

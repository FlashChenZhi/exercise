package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.entity.WorkInfo;

/**
 * <CODE>StorageInParameter</CODE>クラスは、入庫パッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスでは入庫パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>StorageInParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     予定一意キー<BR>
 *     取込単位キー<BR>
 *     設定単位キー<BR>
 *     ホスト取消区分<BR>
 *     作業No.<BR>
 *     集約作業No.<BR>
 *     状態フラグ<BR>
 *     完了フラグ<BR>
 *     入庫予定日<BR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *     商品コード<BR>
 *     商品コード(to)<BR>
 *     商品名称<BR>
 *     伝票No.<BR>
 *     行No.<BR>
 *     作業枝番<BR>
 *     入庫エリアNo.<BR>
 *     入庫棚<BR>
 *     ロットNo.<BR>
 *     ケース入数<BR>
 *     予定ケース数<BR>
 *     予定ピース数<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     予定数<BR>
 *     実績数<BR>
 *     入庫日<BR>
 *     入庫日(to)<BR>
 *     作業時間(秒)<BR>
 *     ミス回数<BR>
 *     強制入庫区分<BR>
 *     リストセル行No.<BR>
 *     帳票発行フラグ<BR>
 *     入庫数の初期入力フラグ<BR>
 *     範囲指定フラグ<BR>
 *     開始終了フラグ<BR>
 *     処理フラグ<BR>
 *     集約条件<BR>
 *     検索対象テーブル<BR>
 *     最終更新日時<BR>
 *     ハードウエア区分<BR>
 * </DIR>
 * 
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * @version $Revision: 7351 $, $Date: 2010-03-04 13:18:36 +0900 (木, 04 3 2010) $
 * @author  $Author: kishimoto $
 */
public class StorageInParameter
        extends InParameter
{
    
    // Class variables -----------------------------------------------
    /**
     * 作業種別 : AS/RS入庫作業リスト
     */
    public static final String SEARCH_ASRS_STORAGE_LIST = "01";
    
    /**
     * 作業種別 : 平置入庫作業リスト
     */
    public static final String SEARCH_FLOOR_STORAGE_LIST = "02";
    
    /**
     * 作業種別 : AS/RS再入庫作業リスト
     */
    public static final String SEARCH_ASRS_RESTORING_LIST = "03";
    
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
     * 完了フラグ
     */
    private String _completionFlag;

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
     * 行No.
     */
    private int _lineNo;

    /**
     * 行No.（String）
     */
    private String _strLineNo;

    /**
     * 作業枝番
     */
    private int _branchNo;

    /**
     * 入庫エリアNo.
     */
    private String _storageAreaNo;

    /**
     * 入庫棚
     */
    private String _storageLocation;

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
     * 実績ケース数
     */
    private int _resultCaseQty;

    /**
     * 実績ピース数
     */
    private int _resultPieceQty;

    /**
     * JANコード
     */
    private String _janCode;

    /**
     * ケースITF
     */
    private String _itf;

    /**
     * 予定数
     */
    private int _planQty;

    /**
     * 実績数
     */
    private int _resultQty;

    /**
     * 入庫日
     */
    private String _storageDay;

    /**
     * 入庫日(to)
     */
    private String _toStorageDay;

    /**
     * 作業時間 (秒)
     */
    private int _workSeconds;

    /**
     * ミス回数
     */
    private int _missCnt;

    /**
     * 強制入庫区分
     */
    private boolean _forceStorageFlag;

    /**
     * リストセル行No.
     */
    private int _rowNo;

    /**
     * 帳票発行フラグ
     */
    private boolean _printFlag;

    /**
     * 入庫数の初期入力フラグ
     */
    private boolean _storageQtyInputFlag;

    /**
     * 範囲指定フラグ
     */
    private boolean _rangeFlag;

    /**
     * 開始終了フラグ
     */
    private String _fromtoFrag;

    /**
     * 開始終了フラグ(入庫日)
     */
    private String _fromtoDateFrag;

    /**
     * 処理フラグ
     */
    private String _processFlag;

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
     * 欠品チェックフラグ
     */
    private boolean _shortageFlag;

    /**
     * 入庫予定日（一括削除帳票用）
     *
     */
    private String[] _storagePlanDays;

    /**
     * 取込単位キー（一括削除帳票用）
     */
    private String[] _loadUnitKeys;

    /**
     * 最終更新日時（一括削除帳票用）
     */
    private java.util.Date[] _lastUpdateDates;


    // Constructor --------------------------------------------------
    /**
     * コンストラクタ <BR>
     * WmsUserInfoを指定します。
     * 
     * @param info WmsUserInfo
     */
    public StorageInParameter(WmsUserInfo info)
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
        return ("$Revision: 7351 $,$Date: 2010-03-04 13:18:36 +0900 (木, 04 3 2010) $");
    }

    /**
     * キー項目のセットと内容の比較を行います。<br>
     * 比較の結果、内容が異なっていれば、このインスタンスの内容を
     * パラメータの内容に置き換えます。
     *
     * @param newWorkInfo 比較対象の入出庫作業情報
     * @return 以下の内容を比較します。
     * <ol>
     * <li>エリア
     * <li>棚
     * <li>ロットNo.
     * </ol>
     */
    public boolean setKeys(WorkInfo newWorkInfo)
    {
        boolean isEqual = true;
        // AREA
        String wkstr = newWorkInfo.getPlanAreaNo();
        if (!equals(getStorageAreaNo(), wkstr))
        {
            setStorageAreaNo(wkstr);
            isEqual &= false;
        }
        // LOCATION
        wkstr = newWorkInfo.getPlanLocationNo();
        if (!equals(getStorageLocation(), wkstr))
        {
            setStorageLocation(wkstr);
            isEqual &= false;
        }

        // LOT
        wkstr = newWorkInfo.getPlanLotNo();
        if (!equals(getLotNo(), wkstr))
        {
            setLotNo(wkstr);
            isEqual &= false;
        }

        return isEqual;
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
     * ホスト取消区分を返します。
     * @return ホスト取消区分を返します。
     */
    public String getCancelFlag()
    {
        return _cancelFlag;
    }

    /**
     * ホスト取消区分を設定します。
     * @param cancelFlag ホスト取消区分
     */
    public void setCancelFlag(String cancelFlag)
    {
        _cancelFlag = cancelFlag;
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
     * 行No.(String)を返します。
     * @return 行No.を返します。
     */
    public String getStrLineNo()
    {
        return _strLineNo;
    }

    /**
     * 行No.(String)を設定します。
     * @param strLineNo 行No.
     */
    public void setStrLineNo(String strLineNo)
    {
        _strLineNo = strLineNo;
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
     * 作業時間(秒)を返します。
     * @return 作業時間
     */
    public int getWorkSeconds()
    {
        return _workSeconds;
    }

    /**
     * 作業時間(秒)を設定します。
     * @param secs 作業時間
     */
    public void setWorkSeconds(int secs)
    {
        _workSeconds = secs;
    }

    /**
     * ミス回数を返します。
     * @return ミス回数を返します。
     */
    public int getMissCnt()
    {
        return _missCnt;
    }

    /**
     * ミス回数を設定します。
     * @param missCnt ミス回数
     */
    public void setMissCnt(int missCnt)
    {
        _missCnt = missCnt;
    }

    /**
     * 強制入庫区分を返します。
     * @return 強制入庫区分を返します。
     */
    public boolean isForceStorageFlag()
    {
        return _forceStorageFlag;
    }

    /**
     * 強制入庫区分を設定します。
     * @param forceStorageFlag 強制入庫区分
     */
    public void setForceStorageFlag(boolean forceStorageFlag)
    {
        _forceStorageFlag = forceStorageFlag;
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
     * 入庫数の初期入力フラグを返します。
     * @return 入庫数の初期入力フラグを返します。
     */
    public boolean isStorageQtyInputFlag()
    {
        return _storageQtyInputFlag;
    }

    /**
     * 入庫数の初期入力フラグを設定します。
     * @param storageQtyInputFlag 入庫数の初期入力フラグ
     */
    public void setStorageQtyInputFlag(boolean storageQtyInputFlag)
    {
        _storageQtyInputFlag = storageQtyInputFlag;
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
     * 欠品チェックフラグを返します。
     * @return 欠品チェックフラグを返します。
     */
    public boolean isShortageFlag()
    {
        return _shortageFlag;
    }

    /**
     * 欠品チェックフラグを設定します。
     * @param shortageFlag 欠品チェックフラグ
     */
    public void setShortageFlag(boolean shortageFlag)
    {
        _shortageFlag = shortageFlag;
    }

    /**
     * 入庫予定日を取得します。
     * @return 入庫予定日
     */
    public String[] getPlanDays()
    {
        return _storagePlanDays;
    }

    /**
     * 入庫予定日に値をセットします。
     * @param plandate String[] セットする入庫予定日
     */
    public void setPlanDays(String[] plandate)
    {
        _storagePlanDays = plandate;
    }


    /**
     * 取込単位キーを返します。
     * @return 取込単位キーを返します。
     */
    public String[] getLoadUnitKeys()
    {
        return _loadUnitKeys;
    }

    /**
     * 取込単位キーを設定します。
     * @param loadUnitKey 取込単位キー
     */
    public void setLoadUnitKeys(String[] loadUnitKey)
    {
        _loadUnitKeys = loadUnitKey;
    }

    /**
     * 最終更新日時を返します。
     * @return 最終更新日時を返します。
     */
    public java.util.Date[] getLastUpdateDates()
    {
        return _lastUpdateDates;
    }

    /**
     * 最終更新日時を設定します。
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
//end of class

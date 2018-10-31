// $Id: ReplenishInParameter.java 607 2008-10-24 02:42:10Z okamura $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.replenish.schedule;


import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;

/**
 * <CODE>ReplenishInParameter</CODE>クラスは、補充パッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスでは補充パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>ReplenishInParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     設定単位キー<BR>
 *     補充開始単位キー<BR>
 *     作業No.<BR>
 *     状態フラグ<BR>
 *     作業区分<BR>
 *     荷主コード<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     ロットNo.<BR>
 *     ケース入数<BR>
 *     予定数<BR>
 *     入庫ケース数<BR>
 *     入庫ピース数<BR>
 *     リストセル行No.<BR>
 *     出庫ステーションNo.<BR>
 *     入庫ステーションNo.<BR>
 *     パレットID<BR>
 *     移動出庫エリア<BR>
 *     移動出庫棚<BR>
 *     移動入庫エリア<BR>
 *     移動入庫予定棚<BR>
 *     予定ケース数<BR>
 *     予定ピース数<BR>
 *     補充実績数<BR>
 *     ユーザ情報<BR>
 *     引当パターンNo.<BR>
 *     補充先棚(from)<BR>
 *     補充先棚(to)<BR>
 *     範囲指定フラグ<BR>
 *     補充率<BR>
 *     帳票発行フラグ<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     操作区分<BR>
 *     エリアタイプ<BR>
 *     移動出庫エリアタイプ<BR>
 *     ハードウェア区分検索キー
 * </DIR>
 *
 * Designer : T.Aoike<BR>
 * Maker    : T.Aoike<BR>
 *
 * @version $Revision: 607 $
 * @author  Last commit: $Author: okamura $
 */
public class ReplenishInParameter
        extends InParameter
{
    // Class variables -----------------------------------------------
    /**
     * 再発行処理
     */
    public static final String OPERATION_REPRINT = "0";

    /**
     * 作業キャンセル処理
     */
    public static final String OPERATION_CANCEL = "1";

    /**
     * 設定単位キー
     */
    private String _settingUnitKey;
    
    /**
     * 補充開始単位キー
     */
    private String _startUnitKey;

    /**
     * 作業No.
     */
    private String _jobNo;

    /**
     * 状態フラグ
     */
    private String _statusFlag;

    /**
     * 状態フラグ配列
     */
    private String[] _statusFlags;

    /**
     * 作業区分
     */
    private String _jobType;

    /**
     * 作業区分配列
     */
    private String[] _jobTypes;

    /**
     * 荷主コード
     */
    private String _consignorCode;

    /**
     * 商品コード
     */
    private String _itemCode;

    /**
     * 商品名称
     */
    private String _itemName;

    /**
     * ロットNo.
     */
    private String _lotNo;

    /**
     * ケース入数
     */
    private int _enteringQty;

    /**
     * 予定数
     */
    private int _planQty;

    /**
     * 入庫ケース数
     */
    private int _storageCaseQty;

    /**
     * 入庫ピース数
     */
    private int _storagePieceQty;

    /**
     * リストセル行No.
     */
    private int _rowNo;

    /**
     * 出庫ステーションNo.
     */
    private String _retrievalStationNo;

    /**
     * 入庫ステーションNo.
     */
    private String _storageStationNo;

    /**
     * パレットID
     */
    private String _palletId;

    /**
     * 移動出庫エリア
     */
    private String _retrievalAreaNo;

    /**
     * 移動出庫棚
     */
    private String _retrievalLocationNo;

    /**
     * 移動入庫エリア
     */
    private String _storageAreaNo;

    /**
     * 移動入庫予定棚
     */
    private String _storageLocationNo;

    /**
     * 予定ケース数
     */
    private int _planCaseQty;

    /**
     * 予定ピース数
     */
    private int _planPieceQty;

    /**
     * 補充実績数
     */
    private int _replenishQty;

    /**
     * 引当パターンNo.
     */
    private String _allocateNo;

    /**
     * 補充先エリアNo.
     */
    private String _areaNo;

    /**
     * 補充先棚(from)
     */
    private String _fromLocation;

    /**
     * 補充先棚(to)
     */
    private String _toLocation;

    /**
     * 範囲指定フラグ
     */
    private boolean _fromtoFrag;

    /**
     * 補充率
     */
    private int _replenishmentRate;

    /**
     * 帳票発行フラグ
     */
    private boolean _printFlag;

    /**
     * JANコード
     */
    private String _janCode;

    /**
     * ケースITF
     */
    private String _itf;

    /**
     * 操作区分
     */
    private String _operation;

    /**
     * 移動出庫エリア
     */
    private String _retrievalAreaType;

    /**
     * ハードウェア区分検索キー
     */
    private String _hardwareTypeSearchKey;

    // Constractor --------------------------------------------------
    /**
     * コンストラクタ <BR>
     * WmsUserInfoを指定します。
     * 
     * @param info WmsUserInfo
     */
    public ReplenishInParameter(WmsUserInfo info)
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
        return ("$Revision: 607 $");
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
     * startUnitKeyを返します。
     * @return startUnitKeyを返します。
     */
    public String getStartUnitKey()
    {
        return _startUnitKey;
    }

    /**
     * startUnitKeyを設定します。
     * @param startUnitKey startUnitKey
     */
    public void setStartUnitKey(String startUnitKey)
    {
        _startUnitKey = startUnitKey;
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
    public String[] getStatusFlags()
    {
        return _statusFlags;
    }

    /**
     * 状態フラグ配列を設定します。
     * @param statusFlags 状態フラグ配列
     */
    public void setStatusFlag(String[] statusFlags)
    {
        _statusFlags = statusFlags;
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
     * 作業区分配列を設定します。
     * @param jobTypes 作業区分配列
     */
    public void setJobType(String[] jobTypes)
    {
        _jobTypes = jobTypes;
    }

    /**
     * 作業区分配列を返します。
     * @return 作業区分配列を返します。
     */
    public String[] getJobTypes()
    {
        return _jobTypes;
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
     * 入庫ケース数を返します。
     * @return 入庫ケース数を返します。
     */
    public int getStorageCaseQty()
    {
        return _storageCaseQty;
    }

    /**
     * 入庫ケース数を設定します。
     * @param storageCaseQty 入庫ケース数
     */
    public void setStorageCaseQty(int storageCaseQty)
    {
        _storageCaseQty = storageCaseQty;
    }

    /**
     * 入庫ピース数を返します。
     * @return 入庫ピース数を返します。
     */
    public int getStoragePieceQty()
    {
        return _storagePieceQty;
    }

    /**
     * 入庫ピース数を設定します。
     * @param storagePieceQty 入庫ピース数
     */
    public void setStoragePieceQty(int storagePieceQty)
    {
        _storagePieceQty = storagePieceQty;
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
     * 出庫ステーションNo.を返します。
     * @return 出庫ステーションNo.を返します。
     */
    public String getRetrievalStationNo()
    {
        return _retrievalStationNo;
    }

    /**
     * 出庫ステーションNo.を設定します。
     * @param station 出庫ステーションNo.
     */
    public void setRetrievalStationNo(String station)
    {
        _retrievalStationNo = station;
    }

    /**
     * 入庫ステーションNo.を返します。
     * @return 入庫ステーションNo.を返します。
     */
    public String getStorageStationNo()
    {
        return _storageStationNo;
    }

    /**
     * 入庫ステーションNo.を設定します。
     * @param station 入庫ステーションNo.
     */
    public void setStorageStationNo(String station)
    {
        _storageStationNo = station;
    }

    /**
     * パレットIDを取得します。
     * @return パレットID
     */
    public String getPalletId()
    {
        return _palletId;
    }

    /**
     * パレットIDを設定します。
     * @param palletId パレットID
     */
    public void setPalletId(String palletId)
    {
        this._palletId = palletId;
    }

    /**
     * 移動出庫エリアを取得します。
     * @return 移動出庫エリア
     */
    public String getRetrievalAreaNo()
    {
        return _retrievalAreaNo;
    }

    /**
     * 移動出庫エリアを設定します。
     * @param retrievalAreaNo 移動出庫エリア
     */
    public void setRetrievalAreaNo(String retrievalAreaNo)
    {
        _retrievalAreaNo = retrievalAreaNo;
    }

    /**
     * 移動出庫棚を取得します。
     * @return 移動出庫棚
     */
    public String getRetrievalLocationNo()
    {
        return _retrievalLocationNo;
    }

    /**
     * 移動出庫棚を設定します。
     * @param retrievalLocationNo 移動出庫棚
     */
    public void setRetrievalLocationNo(String retrievalLocationNo)
    {
        _retrievalLocationNo = retrievalLocationNo;
    }

    /**
     * 移動入庫エリアを取得します。
     * @return 移動入庫エリア
     */
    public String getStorageAreaNo()
    {
        return _storageAreaNo;
    }

    /**
     * 移動入庫エリアを設定します。
     * @param storageAreaNo 移動入庫エリア
     */
    public void setStorageAreaNo(String storageAreaNo)
    {
        _storageAreaNo = storageAreaNo;
    }

    /**
     * 移動入庫予定棚を取得します。
     * @return 移動入庫予定棚
     */
    public String getStorageLocationNo()
    {
        return _storageLocationNo;
    }

    /**
     * 移動入庫予定棚を設定します。
     * @param storageLocationNo 移動入庫予定棚
     */
    public void setStorageLocationNo(String storageLocationNo)
    {
        _storageLocationNo = storageLocationNo;
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
     * 補充実績数を返します。
     * @return 補充実績数を返します。
     */
    public int getReplenishQty()
    {
        return _replenishQty;
    }

    /**
     * 補充実績数を設定します。
     * @param replenishQty 補充実績数
     */
    public void setReplenishQty(int replenishQty)
    {
        _replenishQty = replenishQty;
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
     * 棚(from)を返します。
     * @return 棚(from)を返します。
     */
    public String getFromLocation()
    {
        return _fromLocation;
    }

    /**
     * 棚(from)を設定_fromLocationします。
     * @param fromLocation 棚(from)
     */
    public void setFromLocation(String fromLocation)
    {
        _fromLocation = fromLocation;
    }

    /**
     * 棚_fromLocationを返します。
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
     * 補充先エリアNo.を返します。
     * @return 補充先エリアNo.を返します。
     */
    public String getAreaNo()
    {
        return _areaNo;
    }

    /**
     * 補充先エリアNo.を設定します。
     * @param areaNo 補充先エリアNo.
     */
    public void setAreaNo(String areaNo)
    {
        _areaNo = areaNo;
    }

    /**
     * 範囲指定フラグを返します。
     * @return 範囲指定フラグ
     */
    public boolean getFromToFrag()
    {
        return _fromtoFrag;
    }

    /**
     * 範囲指定フラグを設定します。
     * @param fromtoFlag 範囲指定フラグ
     */
    public void setFromToFlag(boolean fromtoFlag)
    {
        _fromtoFrag = fromtoFlag;
    }

    /**
     * 補充率を返します。
     * @return 補充率を返します。
     */
    public int getReplenishmentRate()
    {
        return _replenishmentRate;
    }

    /**
     * 補充率を設定します。
     * @param replenishmentRate 補充率
     */
    public void setReplenishmentRate(int replenishmentRate)
    {
        _replenishmentRate = replenishmentRate;
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
     * 操作区分を返します。
     * 
     * @return 操作区分を返します。
     */
    public String getOperation()
    {
        return _operation;
    }

    /**
     * 操作区分を設定します。
     * 
     * @param operation 操作区分
     */
    public void setOperation(String operation)
    {
        _operation = operation;
    }

    /**
     * 移動出庫エリアタイプを取得します。
     * @return 移動出庫エリアタイプ
     */
    public String getRetrievalAreaType()
    {
        return _retrievalAreaType;
    }

    /**
     * 移動出庫エリアタイプを設定します。
     * @param retrievalAreaType 移動出庫エリアタイプ
     */
    public void setRetrievalAreaType(String retrievalAreaType)
    {
        _retrievalAreaType = retrievalAreaType;
    }

    /**
     * ハードウェア区分検索キーを返します。
     * @return ハードウェア区分検索キー
     */
    public String getHardwareTypeSerachKey()
    {
        return _hardwareTypeSearchKey;
    }

    /**
     * ハードウェア区分検索キーを設定します。
     * @param hardwareTypeSearchKey ハードウェア区分検索キー<br>
     * <code>ReplenishListBoxDefine.HARDWAETYPE_SARCHKEY_XXXX</code> を指定してください。<br>
     */
    public void setHardwareTypeSerachKey(String hardwareTypeSearchKey)
    {
        _hardwareTypeSearchKey = hardwareTypeSearchKey;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

package jp.co.daifuku.wms.retrieval.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.OutParameter;

/**
 * <CODE>RetrievalOutParameter</CODE>クラスは、出庫パッケージ内のスケジュール→画面間のパラメータの受渡しに使用します。<BR>
 * このクラスでは出庫パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>RetrievalOutParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     予定一意キー<BR>
 *     取込単位キー<BR>
 *     設定単位キー<BR>
 *     作業No.<BR>
 *     集約作業No.<BR>
 *     状態フラグ<BR>
 *     引当パターンNo.<BR>
 *     引当パターン名称<BR>
 *     引当パターン区分<BR>
 *     引当結果<BR>
 *     優先順位<BR>
 *     出庫予定日<BR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *     バッチNo.<BR>
 *     オーダーNo.<BR>
 *     予定オーダー件数<BR>
 *     実績オーダー件数<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     出荷先コード<BR>
 *     出荷先名称<BR>
 *     伝票No.<BR>
 *     伝票数<BR>
 *     行No.<BR>
 *     作業枝番<BR>
 *     エリア区分<BR>
 *     出庫エリアNo.<BR>
 *     出庫エリア名称<BR>
 *     出庫実績エリアNo.<BR>
 *     出庫棚<BR>
 *     出庫実績棚<BR>
 *     予定ロットNo.<BR>
 *     実績ロットNo.<BR>
 *     明細数<BR>
 *     予定商品数<BR>
 *     実績商品数<BR>
 *     ケース入数<BR>
 *     ボール入数<BR>
 *     予定数<BR>
 *     実績数<BR>
 *     欠品数<BR>
 *     予定ケース数<BR>
 *     予定ピース数<BR>
 *     引当可能ケース数<BR>
 *     引当可能ピース数<BR>
 *     実績ケース数<BR>
 *     実績ピース数<BR>
 *     補充ケース数<BR>
 *     補充ピース数<BR>
 *     欠品ケース数<BR>
 *     欠品ピース数<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     ボールITF<BR>
 *     出庫日<BR>
 *     リストセル行No.<BR>
 *     進捗率<BR>
 *     作業日<BR>
 *     作業時刻<BR>
 *     出庫開始日時<BR>
 *     実績報告区分<BR>
 *     棚表示形式<BR>
 *     ボタン制御フラグ<BR>
 *     マスタ管理導入フラグ<BR>
 *     ケース入数変更フラグ<BR>
 *     登録日時<BR>
 *     最終更新日時<BR>
 *     スキップ回数<BR>
 * </DIR>
 * 
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 * 
 * @version $Revision: 2525 $, $Date: 2009-01-06 11:28:40 +0900 (火, 06 1 2009) $
 * @author  $Author: kishimoto $
 */
public class RetrievalOutParameter
        extends OutParameter
{
    // Class variables -----------------------------------------------
    /**
     * 欠品フラグ(通常欠品)
     */
    public static final String SHORTAGE_FLAG_SHORTAGE_NORMAL = "0";

    /**
     * 欠品フラグ(欠品完了)
     */
    public static final String SHORTAGE_FLAG_SHORTAGE_COMPLETE = "1";
    
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
     * 引当結果
     */
    private String _allocateResult;

    /**
     * 優先順位
     */
    private String _allocatePriority;

    /**
     * 出庫予定日
     */
    private String _retrievalPlanDay;

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
     * 予定オーダー件数
     */
    private long _planOrderCnt;

    /**
     * 実績オーダー件数
     */
    private long _resultOrderCnt;

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
     * 伝票数
     */
    private int _ticketCnt;

    /**
     * 行No.
     */
    private int _lineNo;

    /**
     * 作業枝番
     */
    private int _branchNo;

    /**
     * エリア区分
     */
    private String _areaType;

    /**
     * 出庫エリアNo.
     */
    private String _retrievalAreaNo;

    /**
     * 出庫エリア名称
     */
    private String _retrievalAreaName;

    /**
     * 出庫実績エリアNo.
     */
    private String _retrievalResultAreaNo;

    /**
     * 出庫棚
     */
    private String _retrievalLocation;

    /**
     * 出庫実績棚
     */
    private String _retrievalResultLocation;

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
     * 欠品数
     */
    private int _shortageQty;

    /**
     * 予定ケース数
     */
    private int _planCaseQty;

    /**
     * 予定ピース数
     */
    private int _planPieceQty;

    /**
     * 引当可能ケース数
     */
    private int _allocateCaseQty;

    /**
     * 引当可能ピース数
     */
    private int _allocatePieceQty;

    /**
     * 実績ケース数
     */
    private int _resultCaseQty;

    /**
     * 実績ピース数
     */
    private int _resultPieceQty;

    /**
     * 補充ケース数
     */
    private int _replenishCaseQty;

    /**
     * 補充ピース数
     */
    private int _replenishPieceQty;

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
     * 出庫日
     */
    private String _retrievalDay;

    /**
     * リストセル行No.
     */
    private int _rowNo;

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
     * 出庫開始日時
     */
    private java.util.Date _retrievalStartDate;

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
     * マスタ管理導入フラグ
     */
    private boolean _masterFlag;

    /**
     * 在庫パッケージ導入フラグ
     */
    private boolean _stockPackFlag;

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

    /**
     * スキップ回数
     */
    private int _skipCnt;

    /**
     * 作業秒数
     */
    private int _workSecond;
    
    /**
     * オーダー通番
     */
    private String _orderSerialNo;

    /**
     * RFT状態フラグ
     */
    private String _rftStatusFlag;

    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 2525 $,$Date: 2009-01-06 11:28:40 +0900 (火, 06 1 2009) $");
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
     * 引当パターンNo.を返します。
     * @return 引当パターンNo.を返します。
     */
    public String getAllocateNo()
    {
        return _allocateNo;
    }

    /**
     * 引当パターンNo.を設定します。
     * @param allocateNo 引当パターンNo.
     */
    public void setAllocateNo(String allocateNo)
    {
        _allocateNo = allocateNo;
    }

    /**
     * 引当パターン名称を返します。
     * @return 引当パターン名称を返します。
     */
    public String getAllocateName()
    {
        return _allocateName;
    }

    /**
     * 引当パターン名称を設定します。
     * @param allocateName 引当パターン名称
     */
    public void setAllocateName(String allocateName)
    {
        _allocateName = allocateName;
    }

    /**
     * 引当パターン区分を返します。
     * @return 引当パターン区分を返します。
     */
    public String getAllocateType()
    {
        return _allocateType;
    }

    /**
     * 引当パターン区分を設定します。
     * @param allocateType 引当パターン区分
     */
    public void setAllocateType(String allocateType)
    {
        _allocateType = allocateType;
    }

    /**
     * 引当結果を返します。
     * @return 引当結果を返します。
     */
    public String getAllocateResult()
    {
        return _allocateResult;
    }

    /**
     * 引当結果を設定します。
     * @param allocateResult 引当結果
     */
    public void setAllocateResult(String allocateResult)
    {
        _allocateResult = allocateResult;
    }

    /**
     * 優先順位を返します。
     * @return 優先順位を返します。
     */
    public String getAllocatePriority()
    {
        return _allocatePriority;
    }

    /**
     * 優先順位を設定します。
     * @param allocatePriority 優先順位
     */
    public void setAllocatePriority(String allocatePriority)
    {
        _allocatePriority = allocatePriority;
    }

    /**
     * 出庫予定日を返します。
     * @return 出庫予定日を返します。
     */
    public String getRetrievalPlanDay()
    {
        return _retrievalPlanDay;
    }

    /**
     * 出庫予定日を設定します。
     * @param retrievalPlanDay 出庫予定日
     */
    public void setRetrievalPlanDay(String retrievalPlanDay)
    {
        _retrievalPlanDay = retrievalPlanDay;
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
        _batchNo = batchNo;
    }

    /**
     * オーダーNo.を返します。
     * @return オーダーNo.を返します。
     */
    public String getOrderNo()
    {
        return _orderNo;
    }

    /**
     * オーダーNo.を設定します。
     * @param orderNo オーダーNo.
     */
    public void setOrderNo(String orderNo)
    {
        _orderNo = orderNo;
    }

    /**
     * 予定オーダー件数を返します。
     * @return 予定オーダー件数を返します。
     */
    public long getPlanOrderCnt()
    {
        return _planOrderCnt;
    }

    /**
     * 予定オーダー件数を設定します。
     * @param planOrderCnt 予定オーダー件数
     */
    public void setPlanOrderCnt(long planOrderCnt)
    {
        _planOrderCnt = planOrderCnt;
    }

    /**
     * 実績オーダー件数を返します。
     * @return 実績オーダー件数を返します。
     */
    public long getResultOrderCnt()
    {
        return _resultOrderCnt;
    }

    /**
     * 実績オーダー件数を設定します。
     * @param resultOrderCnt 実績オーダー件数
     */
    public void setResultOrderCnt(long resultOrderCnt)
    {
        _resultOrderCnt = resultOrderCnt;
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
     * 伝票数を返します。
     * @return 伝票数を返します。
     */
    public int getTicketCnt()
    {
        return _ticketCnt;
    }

    /**
     * 伝票数を設定します。
     * @param ticketCnt 伝票数
     */
    public void setTicketCnt(int ticketCnt)
    {
        _ticketCnt = ticketCnt;
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
     * エリア区分を返します。
     * @return エリア区分を返します。
     */
    public String getAreaType()
    {
        return _areaType;
    }

    /**
     * エリア区分を設定します。
     * @param areaType エリア区分
     */
    public void setAreaType(String areaType)
    {
        _areaType = areaType;
    }

    /**
     * 出庫エリアNo.を返します。
     * @return 出庫エリアNo.を返します。
     */
    public String getRetrievalAreaNo()
    {
        return _retrievalAreaNo;
    }

    /**
     * 出庫エリアNo.を設定します。
     * @param retrievalAreaNo 出庫エリアNo.
     */
    public void setRetrievalAreaNo(String retrievalAreaNo)
    {
        _retrievalAreaNo = retrievalAreaNo;
    }

    /**
     * 出庫エリア名称を返します。
     * @return 出庫エリア名称を返します。
     */
    public String getRetrievalAreaName()
    {
        return _retrievalAreaName;
    }

    /**
     * 出庫エリア名称を設定します。
     * @param retrievalAreaName 出庫エリア名称
     */
    public void setRetrievalAreaName(String retrievalAreaName)
    {
        _retrievalAreaName = retrievalAreaName;
    }

    /**
     * 出庫実績エリアNo.を返します。
     * @return 出庫実績エリアNo.を返します。
     */
    public String getRetrievalResultAreaNo()
    {
        return _retrievalResultAreaNo;
    }

    /**
     * 出庫実績エリアNo.を設定します。
     * @param retrievalResultAreaNo 出庫実績エリアNo.
     */
    public void setRetrievalResultAreaNo(String retrievalResultAreaNo)
    {
        _retrievalResultAreaNo = retrievalResultAreaNo;
    }

    /**
     * 出庫棚を返します。
     * @return 出庫棚を返します。
     */
    public String getRetrievalLocation()
    {
        return _retrievalLocation;
    }

    /**
     * 出庫棚を設定します。
     * @param retrievalLocation 出庫棚
     */
    public void setRetrievalLocation(String retrievalLocation)
    {
        _retrievalLocation = retrievalLocation;
    }

    /**
     * 出庫実績棚を返します。
     * @return 出庫実績棚を返します。
     */
    public String getRetrievalResultLocation()
    {
        return _retrievalResultLocation;
    }

    /**
     * 出庫実績棚を設定します。
     * @param retrievalResultLocation 出庫実績棚
     */
    public void setRetrievalResultLocation(String retrievalResultLocation)
    {
        _retrievalResultLocation = retrievalResultLocation;
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
     * 補充ケース数を返します。
     * @return 補充ケース数を返します。
     */
    public int getReplenishCaseQty()
    {
        return _replenishCaseQty;
    }

    /**
     * 補充ケース数を設定します。
     * @param replenishCaseQty 補充ケース数
     */
    public void setReplenishCaseQty(int replenishCaseQty)
    {
        _replenishCaseQty = replenishCaseQty;
    }

    /**
     * 補充ピース数を返します。
     * @return 補充ピース数を返します。
     */
    public int getReplenishPieceQty()
    {
        return _replenishPieceQty;
    }

    /**
     * 補充ピース数を設定します。
     * @param replenishPieceQty 補充ピース数
     */
    public void setReplenishPieceQty(int replenishPieceQty)
    {
        _replenishPieceQty = replenishPieceQty;
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
     * 引当可能ケース数を返します。
     * @return 引当可能ケース数を返します。
     */
    public int getAllocateCaseQty()
    {
        return _allocateCaseQty;
    }

    /**
     * 引当可能ケース数を設定します。
     * @param allocateCaseQty 引当可能ケース数
     */
    public void setAllocateCaseQty(int allocateCaseQty)
    {
        _allocateCaseQty = allocateCaseQty;
    }

    /**
     * 引当可能ピース数を返します。
     * @return 引当可能ピース数を返します。
     */
    public int getAllocatePieceQty()
    {
        return _allocatePieceQty;
    }

    /**
     * 引当可能ピース数を設定します。
     * @param allocatePieceQty 引当可能ピース数
     */
    public void setAllocatePieceQty(int allocatePieceQty)
    {
        _allocatePieceQty = allocatePieceQty;
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
     * 出庫日を返します。
     * @return 出庫日を返します。
     */
    public String getRetrievalDay()
    {
        return _retrievalDay;
    }

    /**
     * 出庫日を設定します。
     * @param retrievalDay 出庫日
     */
    public void setRetrievalDay(String retrievalDay)
    {
        _retrievalDay = retrievalDay;
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
     * 出庫開始日時を返します。
     * @return 出庫開始日時を返します。
     */
    public java.util.Date getRetrievalStartDate()
    {
        return _retrievalStartDate;
    }

    /**
     * 出庫開始日時を設定します。
     * @param retrievalStartDate 出庫開始日時
     */
    public void setRetrievalStartDate(java.util.Date retrievalStartDate)
    {
        _retrievalStartDate = retrievalStartDate;
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
     * 在庫パッケージ導入フラグを返します。
     * @return 在庫パッケージ導入フラグを返します。
     */
    public boolean isStockPackFlag()
    {
        return _stockPackFlag;
    }

    /**
     * 在庫パッケージ導入フラグを設定します。
     * @param stockPackFlag 在庫パッケージ導入フラグ
     */
    public void setStockPackFlag(boolean stockPackFlag)
    {
        _stockPackFlag = stockPackFlag;
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

    /**
     * スキップ回数を返します。
     * @return スキップ回数を返します。
     */
    public int getSkipCnt()
    {
        return _skipCnt;
    }

    /**
     * スキップ回数を設定します。
     * @param skipCnt スキップ回数
     */
    public void setSkipCnt(int skipCnt)
    {
        _skipCnt = skipCnt;
    }

    /**
     * 作業秒数を返します。
     * @return 作業秒数を返します。
     */
    public int getWorkSecond()
    {
        return _workSecond;
    }

    /**
     * 作業秒数を設定します。
     * @param workSecond 作業秒数
     */
    public void setWorkSecond(int workSecond)
    {
        _workSecond = workSecond;
    }

    /**
     * オーダー通番を取得します。
     * @return オーダー通番
     */
    public String getOrderSerialNo()
    {
        return _orderSerialNo;
    }
    
    /**
     * オーダー通番を設定します。
     * @param value オーダー通番
     */
    public void setOrderSerialNo(String value)
    {
        _orderSerialNo = value;
    }
    
    /**
     * RFT状態フラグを返します。
     * @return RFT状態フラグを返します。
     */
    public String getRftStatusFlag()
    {
        return _rftStatusFlag;
    }

    /**
     * RFT状態フラグを設定します。
     * @param statusFlag RFT状態フラグ
     */
    public void setRftStatusFlag(String statusFlag)
    {
        _rftStatusFlag = statusFlag;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

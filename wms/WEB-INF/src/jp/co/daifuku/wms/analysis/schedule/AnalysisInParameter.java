// $Id: AnalysisInParameter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.schedule;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.InParameter;


/**
 * <code>AnalysisInParameter</code>クラスは、分析パッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<br>
 * このクラスでは分析パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<br>
 * <br>
 * <dir>
 * <code>AnalysisInParameter</code>クラスが保持する項目<br>
 * <br>
 *     休憩開始時刻(休憩時間設定画面)<br>
 *     休憩終了時刻(休憩時間設定画面)<br>
 *     荷主コード<br>
 *     荷主名称<br>
 *     仕入先コード<br>
 *     仕入先名称<br>
 *     出荷先コード<br>
 *     出荷先名称<br>
 *     商品コード<br>
 *     商品名称<br>
 *     最終更新日<br>
 *     最終更新日(String型)<br>
 *     最終更新処理名<br>
 *     分析単位(在庫推移)<br>
 *     検索開始年月日(在庫推移)<br>
 *     検索終了年月日(在庫推移)<br>
 *     作業予定日(作業時間予測画面)<br>
 *     作業予定日以前(範囲)(作業時間予測画面)<br>
 *     作業予定日以降(範囲)(作業時間予測画面)<br>
 *     入荷作業者数(作業時間予測画面)<br>
 *     入荷作業開始時刻(作業時間予測画面)<br>
 *     入荷作業商品毎時間(作業時間予測画面)<br>
 *     入荷ピース毎時間(作業時間予測画面)<br>
 *     入庫作業者数(作業時間予測画面)<br>
 *     入庫作業開始時刻(作業時間予測画面)<br>
 *     入庫作業商品毎時間(作業時間予測画面)<br>
 *     入庫ピース毎時間(作業時間予測画面)<br>
 *     出庫作業者数(作業時間予測画面)<br>
 *     出庫作業開始時刻(作業時間予測画面)<br>
 *     出庫作業商品毎時間(作業時間予測画面)<br>
 *     出庫ピース毎時間(作業時間予測画面)<br>
 *     仕分作業者数(作業時間予測画面)<br>
 *     仕分作業開始時刻(作業時間予測画面)<br>
 *     仕分作業商品毎時間(作業時間予測画面)<br>
 *     仕分ピース毎時間(作業時間予測画面)<br>
 *     出荷作業者数(作業時間予測画面)<br>
 *     出荷作業開始時刻(作業時間予測画面)<br>
 *     出荷作業商品毎時間(作業時間予測画面)<br>
 *     出荷ピース毎時間(作業時間予測画面)<br>
 * </dir>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Softecs
 * @author  Last commit: $Author: admin $
 */


public class AnalysisInParameter
        extends InParameter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * 検索フラグ(荷主一覧)
     */
    public static final String SEARCHFLAG_CONSIGNOR = "0";

    /**
     * 分析対象実績：出荷
     */
    public static final int SHIPPING_RESULT_MODE_1 = 1;

    /**
     * デフォルト作業単位数(ケース入数)
     */
    public static final int WORK_UNIT_TYPE_CASE = 2;

    /**
     * 出荷実績ABC分析画面
     */
    /** ランクコード(全て) */
    public static final String RANK_ALL = "All";

    /** ランクコード(Aのみ) */
    public static final String RANK_A = "A";

    /** ランクコード(Bのみ) */
    public static final String RANK_B = "B";

    /** ランクコード(Cのみ) */
    public static final String RANK_C = "C";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    /**
     * 休憩開始時刻
     */
    private String _breakStartTime;

    /**
     * 休憩終了時刻
     */
    private String _breakEndTime;

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
     * 商品一覧検索条件
     */
    private String _itemListCondition;

    /**
     * 最終更新日
     */
    private java.util.Date _lastUpdateDate;

    /**
     * 最終更新日(String型)
     */
    private String _lastUpdateDateString;

    /**
     * 最終更新処理名
     */
    private String _lastUpdatePName;

    /**
     * 分析単位
     */
    private String _analysisMode;

    /**
     * 検索開始年月日
     */
    private String _analysisStartDate;

    /**
     * 検索終了年月日
     */
    private String _analysisEndDate;

    /**
     * エリアNo
     */
    private String _areaNo;

    /**
     * 分析種別
     */
    private String _analysisType;

    /**
     * 分析対象期間(FROM)
     */
    private String _fromDate;

    /**
     * 分析対象期間(TO)
     */
    private String _toDate;

    /**
     * Aランクしきい値
     */
    private int _thresholdA;

    /**
     * Bランクしきい値
     */
    private int _thresholdB;

    /**
     * 作業種別
     */
    private String _kindOfWork;

    /**
     * 作業予定日
     */
    private String _planDate;

    /**
     * 作業予定日以前(範囲)
     */
    private boolean _beforePlanDate = false;

    /**
     * 作業予定日以降(範囲)
     */
    private boolean _afterPlanDate = false;

    /**
     * 入荷作業者数
     */
    private String _instockWorkerNum;

    /**
     * 入荷作業開始時刻
     */
    private String _instockWkStartTime;

    /**
     * 入荷商品数
     */
    private long _instockItemQty;

    /**
     * 入荷ピース数
     */
    private long _instockPieceQty;

    /**
     * 入荷作業商品毎時間
     */
    private String _instockSecPerItem;

    /**
     * 入荷ピース毎時間
     */
    private String _instockSecPerPiece;

    /**
     * 入庫作業者数
     */
    private String _storageWorkerNum;

    /**
     * 入庫作業開始時刻
     */
    private String _storageWkStartTime;

    /**
     * 入庫商品数
     */
    private long _storageItemQty;

    /**
     * 入庫ピース数
     */
    private long _storagePieceQty;

    /**
     * 入庫作業商品毎時間
     */
    private String _storageSecPerItem;

    /**
     * 入庫ピース毎時間
     */
    private String _storageSecPerPiece;

    /**
     * 出庫作業者数
     */
    private String _retrievalWorkerNum;

    /**
     * 出庫作業開始時刻
     */
    private String _retrievalWkStartTime;

    /**
     * 出庫商品数
     */
    private long _retrievalItemQty;

    /**
     * 出庫ピース数
     */
    private long _retrievalPieceQty;

    /**
     * 出庫作業商品毎時間
     */
    private String _retrievalSecPerItem;

    /**
     * 出庫ピース毎時間
     */
    private String _retrievalSecPerPiece;

    /**
     * 仕分作業者数
     */
    private String _sortingWorkerNum;

    /**
     * 仕分作業開始時刻
     */
    private String _sortingWkStartTime;

    /**
     * 仕分商品数
     */
    private long _sortingItemQty;

    /**
     * 仕分ピース数
     */
    private long _sortingPieceQty;

    /**
     * 仕分作業商品毎時間
     */
    private String _sortingSecPerItem;

    /**
     * 仕分ピース毎時間
     */
    private String _sortingSecPerPiece;

    /**
     * 出荷作業者数
     */
    private String _shippingWorkerNum;

    /**
     * 出荷作業開始時刻
     */
    private String _shippingWkStartTime;

    /**
     * 出荷商品数
     */
    private long _shippingItemQty;

    /**
     * 出荷ピース数
     */
    private long _shippingPieceQty;

    /**
     * 出荷作業商品毎時間
     */
    private String _shippingSecPerItem;

    /**
     * 出荷ピース毎時間
     */
    private String _shippingSecPerPiece;

    /**
     * 入荷作業予測作業時間
     */
    private String _instockWorkingTime;

    /**
     * 入荷作業終了予測時刻
     */
    private String _instockEndTime;

    /**
     * 入庫作業予測作業時間
     */
    private String _storageWorkingTime;

    /**
     * 入庫作業終了予測時刻
     */
    private String _storageEndTime;

    /**
     * 出庫作業予測作業時間
     */
    private String _retrievalWorkingTime;

    /**
     * 出庫作業終了予測時刻
     */
    private String _retrievalEndTime;

    /**
     * 仕分作業予測作業時間
     */
    private String _sortingWorkingTime;

    /**
     * 仕分作業終了予測時刻
     */
    private String _sortingEndTime;

    /**
     * 出荷作業予測作業時間
     */
    private String _shippingWorkingTime;

    /**
     * 出荷作業終了予測時刻
     */
    private String _shippingEndTime;

    /**
     * 商品毎平均時間
     */
    private double _aveSecPerItem;

    /**
     * 商品毎平均ピース数
     */
    private double _avePiecesPerItem;

    /**
     * ピース毎平均時間
     */
    private double _aveSecPerPiece;

    /**
     * 作業単位数
     */
    private int _workUnitQty;

    /**
     * リストボックス検索
     */
    private boolean _listboxSearch = false;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * 休憩開始時刻を取得します。
     * @return 休憩開始時刻
     */
    public String getBreakStartTime()
    {
        return _breakStartTime;
    }

    /**
     * 休憩開始時刻をセットします。
     * @param breakStartTime 休憩開始時刻
     */
    public void setBreakStartTime(String breakStartTime)
    {
        _breakStartTime = breakStartTime;
    }

    /**
     * 休憩終了時刻を取得します。
     * @return 休憩終了時刻
     */
    public String getBreakEndTime()
    {
        return _breakEndTime;
    }

    /**
     * 休憩終了時刻をセットします。
     * @param breakEndTime 休憩終了時刻
     */
    public void setBreakEndTime(String breakEndTime)
    {
        _breakEndTime = breakEndTime;
    }

    /**
     * 荷主コードを取得します。
     * @return 荷主コード
     */
    public String getConsignorCode()
    {
        return _consignorCode;
    }

    /**
     * 荷主コードをセットします。
     * @param consignorCode 荷主コード
     */
    public void setConsignorCode(String consignorCode)
    {
        _consignorCode = consignorCode;
    }

    /**
     * 荷主名称を取得します。
     * @return 荷主名称
     */
    public String getConsignorName()
    {
        return _consignorName;
    }

    /**
     * 荷主名称をセットします。
     * @param consignorName 荷主名称
     */
    public void setConsignorName(String consignorName)
    {
        _consignorName = consignorName;
    }

    /**
     * 仕入先コードを取得します。
     * @return 仕入先コード
     */
    public String getSupplierCode()
    {
        return _supplierCode;
    }

    /**
     * 仕入先コードをセットします。
     * @param supplierCode 仕入先コード
     */
    public void setSupplierCode(String supplierCode)
    {
        _supplierCode = supplierCode;
    }

    /**
     * 仕入先名称を取得します。
     * @return 仕入先名称
     */
    public String getSupplierName()
    {
        return _supplierName;
    }

    /**
     * 仕入先名称をセットします。
     * @param supplierName 仕入先名称
     */
    public void setSupplierName(String supplierName)
    {
        _supplierName = supplierName;
    }

    /**
     * 出荷先コードを取得します。
     * @return 出荷先コード
     */
    public String getCustomerCode()
    {
        return _customerCode;
    }

    /**
     * 出荷先コードをセットします。
     * @param customerCode 出荷先コード
     */
    public void setCustomerCode(String customerCode)
    {
        _customerCode = customerCode;
    }

    /**
     * 出荷先名称を取得します。
     * @return 出荷先名称
     */
    public String getCustomerName()
    {
        return _customerName;
    }

    /**
     * 出荷先名称をセットします。
     * @param customerName 出荷先名称
     */
    public void setCustomerName(String customerName)
    {
        _customerName = customerName;
    }

    /**
     * 商品コードを取得します。
     * @return 商品コード
     */
    public String getItemCode()
    {
        return _itemCode;
    }

    /**
     * 商品コードをセットします
     * @param itemCode 商品コード
     */
    public void setItemCode(String itemCode)
    {
        _itemCode = itemCode;
    }

    /**
     * 商品名称を取得します。
     * @return 商品名称
     */
    public String getItemName()
    {
        return _itemName;
    }

    /**
     * 商品名称をセットします。
     * @param itemName セットする商品名称
     */
    public void setItemName(String itemName)
    {
        _itemName = itemName;
    }

    /**
     * 商品一覧検索条件を取得します。
     * @return 商品一覧検索条件
     */
    public String getItemListCondition()
    {
        return _itemListCondition;
    }

    /**
     * 商品一覧検索条件をセットします。
     * @param cond 商品一覧検索条件
     */
    public void setItemListCondition(String cond)
    {
        _itemListCondition = cond;
    }

    /**
     * 最終更新日を取得します。
     * @return 最終更新日
     */
    public java.util.Date getLastUpdateDate()
    {
        return _lastUpdateDate;
    }

    /**
     * 最終更新日をセットします。
     * @param lastUpdateDate 最終更新日
     */
    public void setLastUpdateDate(java.util.Date lastUpdateDate)
    {
        _lastUpdateDate = lastUpdateDate;
    }

    /**
     * 最終更新日（String)を取得します。
     * @return 最終更新日
     */
    public String getLastUpdateDateString()
    {
        return _lastUpdateDateString;
    }

    /**
     * 最終更新日（String)をセットします。
     * @param lastUpdateDateString 最終更新日
     */
    public void setLastUpdateDateString(String lastUpdateDateString)
    {
        _lastUpdateDateString = lastUpdateDateString;
    }

    /**
     * 最終更新処理名を取得します。
     * @return 最終更新処理名
     */
    public String getLastUpdatePName()
    {
        return _lastUpdatePName;
    }

    /**
     * 最終更新処理名をセットします。
     * @param lastUpdatePName 最終更新処理名
     */
    public void setLastUpdatePName(String lastUpdatePName)
    {
        _lastUpdatePName = lastUpdatePName;
    }

    /**
     * 分析単位を取得します。
     * @return 分析単位
     */
    public String getAnalysisMode()
    {
        return _analysisMode;
    }

    /**
     * 分析単位をセットします。
     * @param analysisMode 分析単位
     */
    public void setAnalysisMode(String analysisMode)
    {
        _analysisMode = analysisMode;
    }

    /**
     * 検索開始年月日を取得します。
     * @return 検索開始年月日
     */
    public String getAnalysisStartDate()
    {
        return _analysisStartDate;
    }

    /**
     * 検索開始年月日をセットします。
     * @param analysisStartDate 検索開始年月日
     */
    public void setAnalysisStartDate(String analysisStartDate)
    {
        _analysisStartDate = analysisStartDate;
    }

    /**
     * 検索終了年月日を取得します。
     * @return 検索終了年月日
     */
    public String getAnalysisEndDate()
    {
        return _analysisEndDate;
    }

    /**
     * 検索終了年月日をセットします。
     * @param analysisEndDate 検索終了年月日
     */
    public void setAnalysisEndDate(String analysisEndDate)
    {
        _analysisEndDate = analysisEndDate;
    }

    /**
     * エリアNoを取得します。
     * @return エリアNo
     */
    public String getAreaNo()
    {
        return _areaNo;
    }

    /**
     * エリアNoをセットします。
     * @param areaNo エリアNo
     */
    public void setAreaNo(String areaNo)
    {
        _areaNo = areaNo;
    }

    /**
     * 分析種別を取得します。
     * @return 分析種別
     */
    public String getAnalysisType()
    {
        return _analysisType;
    }

    /**
     * 分析種別をセットします
     * @param type 分析種別
     */
    public void setAnalysisType(String type)
    {
        _analysisType = type;
    }

    /**
     * 分析対象期間(FROM)を取得します。
     * @return 分析対象期間(FROM)
     */
    public String getFromDate()
    {
        return _fromDate;
    }

    /**
     * 分析対象期間(FROM)をセットします
     * @param fromDate 分析対象期間(FROM)
     */
    public void setFromDate(String fromDate)
    {
        _fromDate = fromDate;
    }

    /**
     * 分析対象期間(TO)を取得します。
     * @return 分析対象期間(TO)
     */
    public String getToDate()
    {
        return _toDate;
    }

    /**
     * 分析対象期間(TO)をセットします
     * @param toDate 分析対象期間(TO)
     */
    public void setToDate(String toDate)
    {
        _toDate = toDate;
    }

    /**
     * Aランクしきい値を取得します。
     * @return Aランクしきい値
     */
    public int getThresholdA()
    {
        return _thresholdA;
    }

    /**
     * Aランクしきい値をセットします。
     * @param thresholdA Aランクしきい値
     */
    public void setThresholdA(int thresholdA)
    {
        _thresholdA = thresholdA;
    }

    /**
     * Bランクしきい値を取得します。
     * @return Bランクしきい値
     */
    public int getThresholdB()
    {
        return _thresholdB;
    }

    /**
     * Bランクしきい値をセットします。
     * @param thresholdB Bランクしきい値
     */
    public void setThresholdB(int thresholdB)
    {
        _thresholdB = thresholdB;
    }

    /**
     * 作業種別を取得します。
     * @return 作業種別
     */
    public String getKindOfWork()
    {
        return _kindOfWork;
    }

    /**
     * 作業種別をセットします。
     * @param kind 作業種別
     */
    public void setKindOfWork(String kind)
    {
        _kindOfWork = kind;
    }

    /**
     * 作業予定日を取得します。
     * @return 作業予定日
     */
    public String getPlanDate()
    {
        return _planDate;
    }

    /**
     * 作業予定日をセットします。
     * @param planDate 作業予定日
     */
    public void setPlanDate(String planDate)
    {
        _planDate = planDate;
    }

    /**
     * 作業予定日以前(範囲)を取得します。
     * @return 作業予定日以前(範囲)
     */
    public boolean isCheckedBeforePlanDate()
    {
        return _beforePlanDate;
    }

    /**
     * 作業予定日以前(範囲)をセットします。
     * @param arg 作業予定日以前(範囲)
     */
    public void setBeforePlanDate(boolean arg)
    {
        _beforePlanDate = arg;
    }

    /**
     * 作業予定日以降(範囲)を取得します。
     * @return 作業予定日以降(範囲)
     */
    public boolean isCheckedAfterPlanDate()
    {
        return _afterPlanDate;
    }

    /**
     * 作業予定日以降(範囲)をセットします。
     * @param arg 作業予定日以降(範囲)
     */
    public void setAfterPlanDate(boolean arg)
    {
        _afterPlanDate = arg;
    }

    /**
     * 入荷作業者数を取得します。
     * @return 入荷作業者数
     */
    public String getInstockWorkerNum()
    {
        return _instockWorkerNum;
    }

    /**
     * 入荷作業者数をセットします。
     * @param num 入荷作業者数
     */
    public void setInstockWorkerNum(String num)
    {
        _instockWorkerNum = num;
    }

    /**
     * 入荷作業開始時刻を取得します。
     * @return 入荷作業開始時刻
     */
    public String getInstockWkStartTime()
    {
        return _instockWkStartTime;
    }

    /**
     * 入荷作業開始時刻をセットします。
     * @param tim 入荷作業開始時刻
     */
    public void setInstockWkStartTime(String tim)
    {
        _instockWkStartTime = tim;
    }

    /**
     * 入荷商品数を取得します。
     * @return 入荷商品数
     */
    public long getInstockItemQty()
    {
        return _instockItemQty;
    }

    /**
     * 入荷商品数をセットします。
     * @param val 入荷商品数
     */
    public void setInstockItemQty(long val)
    {
        _instockItemQty = val;
    }

    /**
     * 入荷ピース数を取得します。
     * @return 入荷ピース数
     */
    public long getInstockPieceQty()
    {
        return _instockPieceQty;
    }

    /**
     * 入荷ピース数をセットします。
     * @param val 入荷ピース数
     */
    public void setInstockPieceQty(long val)
    {
        _instockPieceQty = val;
    }

    /**
     * 入荷作業商品毎時間を取得します。
     * @return 入荷作業商品毎時間
     */
    public String getInstockSecPerItem()
    {
        return _instockSecPerItem;
    }

    /**
     * 入荷作業商品毎時間をセットします。
     * @param val 入荷作業商品毎時間
     */
    public void setInstockSecPerItem(String val)
    {
        _instockSecPerItem = val;
    }

    /**
     * 入荷ピース毎時間を取得します。
     * @return 入荷ピース毎時間
     */
    public String getInstockSecPerPiece()
    {
        return _instockSecPerPiece;
    }

    /**
     * 入荷ピース毎時間をセットします。
     * @param val 入荷ピース毎時間
     */
    public void setInstockSecPerPiece(String val)
    {
        _instockSecPerPiece = val;
    }

    /**
     * 入庫作業者数を取得します。
     * @return 入庫作業者数
     */
    public String getStorageWorkerNum()
    {
        return _storageWorkerNum;
    }

    /**
     * 入庫作業者数をセットします。
     * @param num 入庫作業者数
     */
    public void setStorageWorkerNum(String num)
    {
        _storageWorkerNum = num;
    }

    /**
     * 入庫作業開始時刻を取得します。
     * @return 入庫作業開始時刻
     */
    public String getStorageWkStartTime()
    {
        return _storageWkStartTime;
    }

    /**
     * 入庫作業開始時刻をセットします。
     * @param tim 入庫作業開始時刻
     */
    public void setStorageWkStartTime(String tim)
    {
        _storageWkStartTime = tim;
    }

    /**
     * 入庫商品数を取得します。
     * @return 入庫商品数
     */
    public long getStorageItemQty()
    {
        return _storageItemQty;
    }

    /**
     * 入庫商品数をセットします。
     * @param val 入庫商品数
     */
    public void setStorageItemQty(long val)
    {
        _storageItemQty = val;
    }

    /**
     * 入庫ピース数を取得します。
     * @return 入庫ピース数
     */
    public long getStoragePieceQty()
    {
        return _storagePieceQty;
    }

    /**
     * 入庫ピース数をセットします。
     * @param val 入庫ピース数
     */
    public void setStoragePieceQty(long val)
    {
        _storagePieceQty = val;
    }

    /**
     * 入庫作業商品毎時間を取得します。
     * @return 入庫作業商品毎時間
     */
    public String getStorageSecPerItem()
    {
        return _storageSecPerItem;
    }

    /**
     * 入庫作業商品毎時間をセットします。
     * @param val 入庫作業商品毎時間
     */
    public void setStorageSecPerItem(String val)
    {
        _storageSecPerItem = val;
    }

    /**
     * 入庫ピース毎時間を取得します。
     * @return 入庫ピース毎時間
     */
    public String getStorageSecPerPiece()
    {
        return _storageSecPerPiece;
    }

    /**
     * 入庫ピース毎時間をセットします。
     * @param val 入庫ピース毎時間
     */
    public void setStorageSecPerPiece(String val)
    {
        _storageSecPerPiece = val;
    }

    /**
     * 出庫作業者数を取得します。
     * @return 出庫作業者数
     */
    public String getRetrievalWorkerNum()
    {
        return _retrievalWorkerNum;
    }

    /**
     * 出庫作業者数をセットします。
     * @param num 出庫作業者数
     */
    public void setRetrievalWorkerNum(String num)
    {
        _retrievalWorkerNum = num;
    }

    /**
     * 出庫作業開始時刻を取得します。
     * @return 出庫作業開始時刻
     */
    public String getRetrievalWkStartTime()
    {
        return _retrievalWkStartTime;
    }

    /**
     * 出庫作業開始時刻をセットします。
     * @param tim 出庫作業開始時刻
     */
    public void setRetrievalWkStartTime(String tim)
    {
        _retrievalWkStartTime = tim;
    }

    /**
     * 出庫商品数を取得します。
     * @return 出庫商品数
     */
    public long getRetrievalItemQty()
    {
        return _retrievalItemQty;
    }

    /**
     * 出庫商品数をセットします。
     * @param val 出庫商品数
     */
    public void setRetrievalItemQty(long val)
    {
        _retrievalItemQty = val;
    }

    /**
     * 出庫ピース数を取得します。
     * @return 出庫ピース数
     */
    public long getRetrievalPieceQty()
    {
        return _retrievalPieceQty;
    }

    /**
     * 出庫ピース数をセットします。
     * @param val 出庫ピース数
     */
    public void setRetrievalPieceQty(long val)
    {
        _retrievalPieceQty = val;
    }

    /**
     * 出庫作業商品毎時間を取得します。
     * @return 出庫作業商品毎時間
     */
    public String getRetrievalSecPerItem()
    {
        return _retrievalSecPerItem;
    }

    /**
     * 出庫作業商品毎時間をセットします。
     * @param val 出庫作業商品毎時間
     */
    public void setRetrievalSecPerItem(String val)
    {
        _retrievalSecPerItem = val;
    }

    /**
     * 出庫ピース毎時間を取得します。
     * @return 出庫ピース毎時間
     */
    public String getRetrievalSecPerPiece()
    {
        return _retrievalSecPerPiece;
    }

    /**
     * 出庫ピース毎時間をセットします。
     * @param val 出庫ピース毎時間
     */
    public void setRetrievalSecPerPiece(String val)
    {
        _retrievalSecPerPiece = val;
    }

    /**
     * 仕分作業者数を取得します。
     * @return 仕分作業者数
     */
    public String getSortingWorkerNum()
    {
        return _sortingWorkerNum;
    }

    /**
     * 仕分作業者数をセットします。
     * @param num 仕分作業者数
     */
    public void setSortingWorkerNum(String num)
    {
        _sortingWorkerNum = num;
    }

    /**
     * 仕分作業開始時刻を取得します。
     * @return 仕分作業開始時刻
     */
    public String getSortingWkStartTime()
    {
        return _sortingWkStartTime;
    }

    /**
     * 仕分作業開始時刻をセットします。
     * @param tim 仕分作業開始時刻
     */
    public void setSortingWkStartTime(String tim)
    {
        _sortingWkStartTime = tim;
    }

    /**
     * 仕分商品数を取得します。
     * @return 仕分商品数
     */
    public long getSortingItemQty()
    {
        return _sortingItemQty;
    }

    /**
     * 仕分商品数をセットします。
     * @param val 仕分商品数
     */
    public void setSortingItemQty(long val)
    {
        _sortingItemQty = val;
    }

    /**
     * 仕分ピース数を取得します。
     * @return 仕分ピース数
     */
    public long getSortingPieceQty()
    {
        return _sortingPieceQty;
    }

    /**
     * 仕分ピース数をセットします。
     * @param val 仕分ピース数
     */
    public void setSortingPieceQty(long val)
    {
        _sortingPieceQty = val;
    }

    /**
     * 仕分作業商品毎時間を取得します。
     * @return 仕分作業商品毎時間
     */
    public String getSortingSecPerItem()
    {
        return _sortingSecPerItem;
    }

    /**
     * 仕分作業商品毎時間をセットします。
     * @param val 仕分作業商品毎時間
     */
    public void setSortingSecPerItem(String val)
    {
        _sortingSecPerItem = val;
    }

    /**
     * 仕分ピース毎時間を取得します。
     * @return 仕分ピース毎時間
     */
    public String getSortingSecPerPiece()
    {
        return _sortingSecPerPiece;
    }

    /**
     * 仕分ピース毎時間をセットします。
     * @param val 仕分ピース毎時間
     */
    public void setSortingSecPerPiece(String val)
    {
        _sortingSecPerPiece = val;
    }

    /**
     * 出荷作業者数を取得します。
     * @return 出荷作業者数
     */
    public String getShippingWorkerNum()
    {
        return _shippingWorkerNum;
    }

    /**
     * 出荷作業者数をセットします。
     * @param num 出荷作業者数
     */
    public void setShippingWorkerNum(String num)
    {
        _shippingWorkerNum = num;
    }

    /**
     * 出荷作業開始時刻を取得します。
     * @return 出荷作業開始時刻
     */
    public String getShippingWkStartTime()
    {
        return _shippingWkStartTime;
    }

    /**
     * 出荷作業開始時刻をセットします。
     * @param tim 出荷作業開始時刻
     */
    public void setShippingWkStartTime(String tim)
    {
        _shippingWkStartTime = tim;
    }

    /**
     * 出荷商品数を取得します。
     * @return 出荷商品数
     */
    public long getShippingItemQty()
    {
        return _shippingItemQty;
    }

    /**
     * 出荷商品数をセットします。
     * @param val 出荷商品数
     */
    public void setShippingItemQty(long val)
    {
        _shippingItemQty = val;
    }

    /**
     * 出荷ピース数を取得します。
     * @return 出荷ピース数
     */
    public long getShippingPieceQty()
    {
        return _shippingPieceQty;
    }

    /**
     * 出荷ピース数をセットします。
     * @param val 出荷ピース数
     */
    public void setShippingPieceQty(long val)
    {
        _shippingPieceQty = val;
    }

    /**
     * 出荷作業商品毎時間を取得します。
     * @return 出荷作業商品毎時間
     */
    public String getShippingSecPerItem()
    {
        return _shippingSecPerItem;
    }

    /**
     * 出荷作業商品毎時間をセットします。
     * @param val 出荷作業商品毎時間
     */
    public void setShippingSecPerItem(String val)
    {
        _shippingSecPerItem = val;
    }

    /**
     * 出荷ピース毎時間を取得します。
     * @return 出荷ピース毎時間
     */
    public String getShippingSecPerPiece()
    {
        return _shippingSecPerPiece;
    }

    /**
     * 出荷ピース毎時間をセットします。
     * @param val 出荷ピース毎時間
     */
    public void setShippingSecPerPiece(String val)
    {
        _shippingSecPerPiece = val;
    }

    /**
     * 入荷作業予測時間を取得します。
     * @return 入荷作業予測時間
     */
    public String getInstockWorkingTime()
    {
        return _instockWorkingTime;
    }

    /**
     * 入荷作業予測時間をセットします。
     * @param tim 入荷作業予測時間
     */
    public void setInstockWorkingTime(String tim)
    {
        _instockWorkingTime = tim;
    }

    /**
     * 入荷作業終了予測時刻を取得します。
     * @return 入荷作業終了予測時刻
     */
    public String getInstockEndTime()
    {
        return _instockEndTime;
    }

    /**
     * 入荷作業終了予測時刻をセットします。
     * @param tim 入荷作業終了予測時刻
     */
    public void setInstockEndTime(String tim)
    {
        _instockEndTime = tim;
    }

    /**
     * 入庫作業予測時間を取得します。
     * @return 入庫作業予測時間
     */
    public String getStorageWorkingTime()
    {
        return _storageWorkingTime;
    }

    /**
     * 入庫作業予測時間をセットします。
     * @param tim 入庫作業予測時間
     */
    public void setStorageWorkingTime(String tim)
    {
        _storageWorkingTime = tim;
    }

    /**
     * 入庫作業終了予測時刻を取得します。
     * @return 入庫作業終了予測時刻
     */
    public String getStorageEndTime()
    {
        return _storageEndTime;
    }

    /**
     * 入庫作業終了予測時刻をセットします。
     * @param tim 入庫作業終了予測時刻
     */
    public void setStorageEndTime(String tim)
    {
        _storageEndTime = tim;
    }

    /**
     * 出庫作業予測時間を取得します。
     * @return 出庫作業予測時間
     */
    public String getRetrievalWorkingTime()
    {
        return _retrievalWorkingTime;
    }

    /**
     * 出庫作業予測時間をセットします。
     * @param tim 出庫作業予測時間
     */
    public void setRetrievalWorkingTime(String tim)
    {
        _retrievalWorkingTime = tim;
    }

    /**
     * 出庫作業終了予測時刻を取得します。
     * @return 出庫作業終了予測時刻
     */
    public String getRetrievalEndTime()
    {
        return _retrievalEndTime;
    }

    /**
     * 出庫作業終了予測時刻をセットします。
     * @param tim 出庫作業終了予測時刻
     */
    public void setRetrievalEndTime(String tim)
    {
        _retrievalEndTime = tim;
    }

    /**
     * 仕分作業予測時間を取得します。
     * @return 仕分作業予測時間
     */
    public String getSortingWorkingTime()
    {
        return _sortingWorkingTime;
    }

    /**
     * 仕分作業予測時間をセットします。
     * @param tim 仕分作業予測時間
     */
    public void setSortingWorkingTime(String tim)
    {
        _sortingWorkingTime = tim;
    }

    /**
     * 仕分作業終了予測時刻を取得します。
     * @return 仕分作業終了予測時刻
     */
    public String getSortingEndTime()
    {
        return _sortingEndTime;
    }

    /**
     * 仕分作業終了予測時刻をセットします。
     * @param tim 仕分作業終了予測時刻
     */
    public void setSortingEndTime(String tim)
    {
        _sortingEndTime = tim;
    }

    /**
     * 出荷作業予測時間を取得します。
     * @return 出荷作業予測時間
     */
    public String getShippingWorkingTime()
    {
        return _shippingWorkingTime;
    }

    /**
     * 出荷作業予測時間をセットします。
     * @param tim 出荷作業予測時間
     */
    public void setShippingWorkingTime(String tim)
    {
        _shippingWorkingTime = tim;
    }

    /**
     * 出荷作業終了予測時刻を取得します。
     * @return 出荷作業終了予測時刻
     */
    public String getShippingEndTime()
    {
        return _shippingEndTime;
    }

    /**
     * 出荷作業終了予測時刻をセットします。
     * @param tim 出荷作業終了予測時刻
     */
    public void setShippingEndTime(String tim)
    {
        _shippingEndTime = tim;
    }

    /**
     * 商品毎平均時間を取得します。
     * @return 商品毎平均時間
     */
    public double getAveSecPerItem()
    {
        return _aveSecPerItem;
    }

    /**
     * 商品毎平均時間をセットします。
     * @param sec 商品毎平均時間
     */
    public void setAveSecPerItem(double sec)
    {
        _aveSecPerItem = sec;
    }

    /**
     * 商品毎平均ピース数を取得します。
     * @return 商品毎平均ピース数
     */
    public double getAvePiecesPerItem()
    {
        return _avePiecesPerItem;
    }

    /**
     * 商品毎平均ピース数をセットします。
     * @param qty 商品毎平均ピース数
     */
    public void setAvePiecesPerItem(double qty)
    {
        _avePiecesPerItem = qty;
    }

    /**
     * ピース毎平均時間を取得します。
     * @return ピース毎平均時間
     */
    public double getAveSecPerPiece()
    {
        return _aveSecPerPiece;
    }

    /**
     * ピース毎平均時間をセットします。
     * @param sec ピース毎平均時間
     */
    public void setAveSecPerPiece(double sec)
    {
        _aveSecPerPiece = sec;
    }

    /**
     * 作業単位数を取得します。
     * @return 作業単位数
     */
    public int getWorkUnitQty()
    {
        return _workUnitQty;
    }

    /**
     * 作業単位数をセットします。
     * @param workUnitQty 作業単位数
     */
    public void setWorkUnitQty(int workUnitQty)
    {
        _workUnitQty = workUnitQty;
    }

    /**
     * リストボックス検索を取得します。
     * @return リストボックス検索が指定されている場合<code>true</code>を返します。
     */
    public boolean isListboxSearch()
    {
        return _listboxSearch;
    }

    /**
     * リストボックス検索を指定します。
     * @param listboxSearch リストボックス検索を指定する場合は<code>true</code>を指定します。
     */
    public void setListboxSearch(boolean listboxSearch)
    {
        _listboxSearch = listboxSearch;
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AnalysisInParameter.java 87 2008-10-04 03:07:38Z admin $";
    }
}

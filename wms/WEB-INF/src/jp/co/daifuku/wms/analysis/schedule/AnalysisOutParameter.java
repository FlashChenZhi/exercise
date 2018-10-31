// $Id: AnalysisOutParameter.java 443 2008-10-21 10:29:08Z nakai $
package jp.co.daifuku.wms.analysis.schedule;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;


/**
 * <code>AnalysisOutParameter</code>クラスは、分析パッケージ内のスケジュール→画面間のパラメータの受渡しに使用します。<br>
 * このクラスでは分析パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<br>
 * <br>
 * <dir>
 * <code>AnalysisOutParameter</code>クラスが保持する項目<br>
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
 *     入荷作業予測作業時間(作業時間予測画面)<br>
 *     入荷作業終了予測時刻(作業時間予測画面)<br>
 *     入庫作業予測作業時間(作業時間予測画面)<br>
 *     入庫作業終了予測時刻(作業時間予測画面)<br>
 *     出庫作業予測作業時間(作業時間予測画面)<br>
 *     出庫作業終了予測時刻(作業時間予測画面)<br>
 *     仕分作業予測作業時間(作業時間予測画面)<br>
 *     仕分作業終了予測時刻(作業時間予測画面)<br>
 *     出荷作業予測作業時間(作業時間予測画面)<br>
 *     出荷作業終了予測時刻(作業時間予測画面)<br>
 *     在庫推移分析データ配列(在庫推移画面)<br>
 *     ABC分析データ配列(出荷実績ABC分析画面)<br>
 *     実績グラフデータ配列(作業時間予測画面)<br>
 * </dir>
 *
 * @version $Revision: 443 $, $Date: 2008-10-21 19:29:08 +0900 (火, 21 10 2008) $
 * @author  Softecs
 * @author  Last commit: $Author: nakai $
 */


public class AnalysisOutParameter
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /** 処理結果 (OK) */
    public static final int RET_OK = 0;

    /** 処理結果 (NG) */
    public static final int RET_NG = -1;

    /** 処理結果 (整合性エラー: 名称の重複) */
    public static final int RET_CONSIST_NAME_EXIST = 3;

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
     * 在庫推移画面
     */
    /**
     * 在庫推移分析データ配列
     */
    private List<InventoryDataSet> _inventoryList = new ArrayList<InventoryDataSet>();

    /**
     * 在庫推移分析データ配列内最大最小値
     */
    private long _maxInventoryQty = 0;

    private long _minInventoryQty = 999999999;

    private long _maxStorageQty = 0;

    private long _minStorageQty = 999999999;

    private long _maxRetrievalQty = 0;

    private long _minRetrievalQty = 999999999;

    private long _maxUpperLimitQty = 0;

    private long _minLowerLimitQty = 999999999;

    /**
     * 在庫推移分析データセット
     */
    private class InventoryDataSet
    {
        /** タイムスタンプ */
        private String _timeStamp;

        /** 在庫数 */
        private long _inventoryQty;

        /** 入庫数 */
        private long _storageQty;

        /** 出庫数 */
        private long _retrievalQty;

        /** 上限在庫数 */
        private long _upperLimitQty;

        /** 下限在庫数 */
        private long _lowerLimitQty;

        /**
         * @param timeStamp タイムスタンプ
         * @param inventoryQty 在庫数
         * @param storageQty 入庫数
         * @param retrievalQty 出庫数
         * @param upperLimitQty 上限在庫数
         * @param lowerLimitQty 下限在庫数
         */
        public InventoryDataSet(String timeStamp, long inventoryQty, long storageQty, long retrievalQty,
                long upperLimitQty, long lowerLimitQty)
        {
            this._timeStamp = timeStamp;
            this._inventoryQty = inventoryQty;
            this._storageQty = storageQty;
            this._retrievalQty = retrievalQty;
            this._upperLimitQty = upperLimitQty;
            this._lowerLimitQty = lowerLimitQty;
        }

        /**
         * inventoryQtyを返します。
         * @return inventoryQtyを返します。
         */
        public long getInventoryQty()
        {
            return _inventoryQty;
        }

        /**
         * lowerLimitQtyを返します。
         * @return lowerLimitQtyを返します。
         */
        public long getLowerLimitQty()
        {
            return _lowerLimitQty;
        }

        /**
         * retrievalQtyを返します。
         * @return retrievalQtyを返します。
         */
        public long getRetrievalQty()
        {
            return _retrievalQty;
        }

        /**
         * storageQtyを返します。
         * @return storageQtyを返します。
         */
        public long getStorageQty()
        {
            return _storageQty;
        }

        /**
         * timeStampを返します。
         * @return timeStampを返します。
         */
        public String getTimeStamp()
        {
            return _timeStamp;
        }

        /**
         * upperLimitQtyを返します。
         * @return upperLimitQtyを返します。
         */
        public long getUpperLimitQty()
        {
            return _upperLimitQty;
        }
    }

    /**
     * 出荷実績ABC分析画面
     */
    /**
     * ABC分析データ配列
     */
    private List<AbcDataSet> _abcAnalysisList = new ArrayList<AbcDataSet>();

    /**
     * ABC分析データセット
     */
    private class AbcDataSet
    {
        /** 出荷先コード */
        private String _customerCode1;

        /** 出荷先名称 */
        private String _customerName1;

        /** 商品コード */
        private String _itemCode1;

        /** 商品名称 */
        private String _itemName1;

        /** 出荷数 */
        private long _shippingQty1;

        /** 作業数 */
        private long _workingQty1;

        /** 出荷回数 */
        private long _shippingCnt1;

        /** 構成比 */
        private double _ratio1;

        /** 累積構成比 */
        private double _accumRatio1;

        /** ランク */
        private String _rankCode1;

        /**
         * @param customerCode 出荷先コード
         * @param customerName 出荷先名称
         * @param itemCode 商品コード
         * @param itemName 商品名称
         * @param shippingQty 出荷数
         * @param workingQty 作業数
         * @param shippingCnt 出荷回数
         */
        public AbcDataSet(String customerCode, String customerName, String itemCode, String itemName, long shippingQty,
                long workingQty, long shippingCnt)
        {
            this._customerCode1 = customerCode;
            this._customerName1 = customerName;
            this._itemCode1 = itemCode;
            this._itemName1 = itemName;
            this._shippingQty1 = shippingQty;
            this._workingQty1 = workingQty;
            this._shippingCnt1 = shippingCnt;
            this._ratio1 = 0;
            this._accumRatio1 = 0;
            this._rankCode1 = "";
        }

        /**
         * accumRatioを返します。
         * @return accumRatioを返します。
         */
        public double getAccumRatio()
        {
            return _accumRatio1;
        }

        /**
         * customerCodeを返します。
         * @return customerCodeを返します。
         */
        public String getCustomerCode()
        {
            return _customerCode1;
        }

        /**
         * customerNameを返します。
         * @return customerNameを返します。
         */
        public String getCustomerName()
        {
            return _customerName1;
        }

        /**
         * itemCodeを返します。
         * @return itemCodeを返します。
         */
        public String getItemCode()
        {
            return _itemCode1;
        }

        /**
         * itemNameを返します。
         * @return itemNameを返します。
         */
        public String getItemName()
        {
            return _itemName1;
        }

        /**
         * rankCodeを返します。
         * @return rankCodeを返します。
         */
        public String getRankCode()
        {
            return _rankCode1;
        }

        /**
         * ratioを返します。
         * @return ratioを返します。
         */
        public double getRatio()
        {
            return _ratio1;
        }

        /**
         * shippingCntを返します。
         * @return shippingCntを返します。
         */
        public long getShippingCnt()
        {
            return _shippingCnt1;
        }

        /**
         * shippingQtyを返します。
         * @return shippingQtyを返します。
         */
        public long getShippingQty()
        {
            return _shippingQty1;
        }

        /**
         * workingQtyを返します。
         * @return workingQtyを返します。
         */
        public long getWorkingQty()
        {
            return _workingQty1;
        }

        /**
         * accumRatioを設定します。
         * @param accumRatio accumRatio
         */
        public void setAccumRatio(double accumRatio)
        {
            this._accumRatio1 = accumRatio;
        }

        /**
         * customerCodeを設定します。
         * @param customerCode customerCode
         */
        public void setCustomerCode(String customerCode)
        {
            this._customerCode1 = customerCode;
        }

        /**
         * customerNameを設定します。
         * @param customerName customerName
         */
        public void setCustomerName(String customerName)
        {
            this._customerName1 = customerName;
        }

        /**
         * itemCodeを設定します。
         * @param itemCode itemCode
         */
        public void setItemCode(String itemCode)
        {
            this._itemCode1 = itemCode;
        }

        /**
         * itemNameを設定します。
         * @param itemName itemName
         */
        public void setItemName(String itemName)
        {
            this._itemName1 = itemName;
        }

        /**
         * rankCodeを設定します。
         * @param rankCode rankCode
         */
        public void setRankCode(String rankCode)
        {
            this._rankCode1 = rankCode;
        }

        /**
         * ratioを設定します。
         * @param ratio ratio
         */
        public void setRatio(double ratio)
        {
            this._ratio1 = ratio;
        }

        /**
         * shippingCntを設定します。
         * @param shippingCnt shippingCnt
         */
        public void setShippingCnt(long shippingCnt)
        {
            this._shippingCnt1 = shippingCnt;
        }

        /**
         * shippingQtyを設定します。
         * @param shippingQty shippingQty
         */
        public void setShippingQty(long shippingQty)
        {
            this._shippingQty1 = shippingQty;
        }

        /**
         * workingQtyを設定します。
         * @param workingQty workingQty
         */
        public void setWorkingQty(long workingQty)
        {
            this._workingQty1 = workingQty;
        }
    }

    /**
     * ABC分析データ分析数合計
     */
    private long _totalQty;

    /**
     * 作業時間予測画面
     */
    /**
     * 実績グラフデータ配列
     */
    private List<ResultDataSet> _resultDataList = new ArrayList<ResultDataSet>();

    /**
     * 実績グラフデータ配列内最大最小値
     */
    private double _maxWorkingTime = 0;

    private double _minWorkingTime = 999999999;

    private double _maxItemQty = 0;

    private double _minItemQty = 999999999;

    private double _maxTotalPieces = 0;

    private double _minTotalPieces = 999999999;

    /**
     * 実績グラフデータセット
     */
    private class ResultDataSet
    {
        /** 日付 */
        private String _workDate;

        /** 総作業時間 */
        private double _totalWorkinTime;

        /** 商品数 */
        private double _itemQty;

        /** 総ピース数 */
        private double _totalPieces;

        /** 予測作業時間 */
        private double _simuWorkingTime;

        /**
         * @param workDate 日付
         * @param totalWorkinTime 総作業時間
         * @param itemQty 商品数
         * @param totalPieces 総ピース数
         * @param simuWorkingTime 予測作業時間
         */
        public ResultDataSet(String workDate, double totalWorkinTime, double itemQty, double totalPieces,
                double simuWorkingTime)
        {
            this._workDate = workDate;
            this._totalWorkinTime = totalWorkinTime;
            this._itemQty = itemQty;
            this._totalPieces = totalPieces;
            this._simuWorkingTime = simuWorkingTime;
        }

        /**
         * itemQtyを返します。
         * @return itemQtyを返します。
         */
        public double getItemQty()
        {
            return _itemQty;
        }

        /**
         * simuWorkingTimeを返します。
         * @return simuWorkingTimeを返します。
         */
        public double getSimuWorkingTime()
        {
            return _simuWorkingTime;
        }

        /**
         * totalPiecesを返します。
         * @return totalPiecesを返します。
         */
        public double getTotalPieces()
        {
            return _totalPieces;
        }

        /**
         * totalWorkinTimeを返します。
         * @return totalWorkinTimeを返します。
         */
        public double getTotalWorkinTime()
        {
            return _totalWorkinTime;
        }

        /**
         * workDateを返します。
         * @return workDateを返します。
         */
        public String getWorkDate()
        {
            return _workDate;
        }
    }

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 在庫推移分析データ配列へデータをセット（追加）します。
     * 在庫数、入庫数、出庫数の最大値、最小値も更新していきます。
     * @param timeStamp タイムスタンプ
     * @param inventoryQty 在庫数
     * @param storageQty 入庫数
     * @param retrievalQty 出庫数
     * @param upperLimitQty 上限在庫数
     * @param lowerLimitQty 下限在庫数
     */
    public void addToInventoryList(String timeStamp, long inventoryQty, long storageQty, long retrievalQty,
            long upperLimitQty, long lowerLimitQty)
    {
        InventoryDataSet dtSet =
                new InventoryDataSet(timeStamp, inventoryQty, storageQty, retrievalQty, upperLimitQty, lowerLimitQty);
        _inventoryList.add(dtSet);
        if (_maxInventoryQty < inventoryQty)
        {
            _maxInventoryQty = inventoryQty;
        }
        if (_minInventoryQty > inventoryQty)
        {
            _minInventoryQty = inventoryQty;
        }
        if (_maxStorageQty < storageQty)
        {
            _maxStorageQty = storageQty;
        }
        if (_minStorageQty > storageQty)
        {
            _minStorageQty = storageQty;
        }
        if (_maxRetrievalQty < retrievalQty)
        {
            _maxRetrievalQty = retrievalQty;
        }
        if (_minRetrievalQty > retrievalQty)
        {
            _minRetrievalQty = retrievalQty;
        }
        if (_maxUpperLimitQty < upperLimitQty)
        {
            _maxUpperLimitQty = upperLimitQty;
        }
        if (_minLowerLimitQty > lowerLimitQty)
        {
            _minLowerLimitQty = lowerLimitQty;
        }
    }

    /**
     * 在庫推移分析データ配列の初期化を行います。
     * 記憶済みの最大値、最小値も初期化します。
     */
    public void clearInventoryList()
    {
        _inventoryList.clear();
        _maxInventoryQty = 0;
        _minInventoryQty = 999999999;
        _maxStorageQty = 0;
        _minStorageQty = 999999999;
        _maxRetrievalQty = 0;
        _minRetrievalQty = 999999999;
        _maxUpperLimitQty = 0;
        _minLowerLimitQty = 999999999;
    }

    /**
     * 在庫推移分析データ配列のサイズを取得します。
     * @return 配列のサイズ
     */
    public int getSizeOfInventoryList()
    {
        return _inventoryList.size();
    }

    /**
     * 在庫推移分析データ配列内の在庫数最大値を取得します。
     * @return 最大値
     */
    public long getMaxInventoryQty()
    {
        return _maxInventoryQty;
    }

    /**
     * 在庫推移分析データ配列内の在庫数最小値を取得します。
     * @return 最小値
     */
    public long getMinInventoryQty()
    {
        return _minInventoryQty;
    }

    /**
     * 在庫推移分析データ配列内の入庫数最大値を取得します。
     * @return 最大値
     */
    public long getMaxStorageQty()
    {
        return _maxStorageQty;
    }

    /**
     * 在庫推移分析データ配列内の入庫数最小値を取得します。
     * @return 最小値
     */
    public long getMinStorageQty()
    {
        return _minStorageQty;
    }

    /**
     * 在庫推移分析データ配列内の出庫数最大値を取得します。
     * @return 最大値
     */
    public long getMaxRetrievalQty()
    {
        return _maxRetrievalQty;
    }

    /**
     * 在庫推移分析データ配列内の出庫数最小値を取得します。
     * @return 最小値
     */
    public long getMinRetrievalQty()
    {
        return _minRetrievalQty;
    }

    /**
     * 在庫推移分析データ配列内の上限在庫数最大値を取得します。
     * @return 最大値
     */
    public long getMaxUpperLimitQty()
    {
        return _maxUpperLimitQty;
    }

    /**
     * 在庫推移分析データ配列内の下限在庫数最小値を取得します。
     * @return 最小値
     */
    public long getMinLowerLimitQty()
    {
        return _minLowerLimitQty;
    }

    /**
     * 在庫推移分析データ配列から指定した位置のタイムスタンプを取得します。
     * @param idx 配列添え字
     * @return タイムスタンプ
     */
    public String getTimestampFromInventoryList(int idx)
    {
        if (idx >= getSizeOfInventoryList())
        {
            return null;
        }

        InventoryDataSet dt = _inventoryList.get(idx);
        return dt.getTimeStamp();
    }

    /**
     * 在庫推移分析データ配列から指定した位置の在庫数を取得します。
     * @param idx 配列添え字
     * @return 在庫数
     */
    public long getInventoryQtyFromInventoryList(int idx)
    {
        if (idx >= getSizeOfInventoryList())
        {
            return -1;
        }

        InventoryDataSet dt = _inventoryList.get(idx);
        return dt.getInventoryQty();
    }

    /**
     * 在庫推移分析データ配列から指定した位置の入庫数を取得します。
     * @param idx 配列添え字
     * @return 入庫数
     */
    public long getStorageQtyFromInventoryList(int idx)
    {
        if (idx >= getSizeOfInventoryList())
        {
            return -1;
        }

        InventoryDataSet dt = _inventoryList.get(idx);
        return dt.getStorageQty();
    }

    /**
     * 在庫推移分析データ配列から指定した位置の出庫数を取得します。
     * @param idx 配列添え字
     * @return 出庫数
     */
    public long getRetrievalQtyFromInventoryList(int idx)
    {
        if (idx >= getSizeOfInventoryList())
        {
            return -1;
        }

        InventoryDataSet dt = _inventoryList.get(idx);
        return dt.getRetrievalQty();
    }

    /**
     * 在庫推移分析データ配列から指定した位置の上限在庫数を取得します。
     * @param idx 配列添え字
     * @return 上限在庫数
     */
    public long getUpperLimitQtyFromInventoryList(int idx)
    {
        if (idx >= getSizeOfInventoryList())
        {
            return -1;
        }

        InventoryDataSet dt = _inventoryList.get(idx);
        return dt.getUpperLimitQty();
    }

    /**
     * 在庫推移分析データ配列から指定した位置の下限在庫数を取得します。
     * @param idx 配列添え字
     * @return 下限在庫数
     */
    public long getLowerLimitQtyFromInventoryList(int idx)
    {
        if (idx >= getSizeOfInventoryList())
        {
            return -1;
        }

        InventoryDataSet dt = _inventoryList.get(idx);
        return dt.getLowerLimitQty();
    }

    /**
     * ABC分析データ配列へデータをセット（追加）します。
     * @param customerCode 出荷先コード
     * @param customerName 出荷先名
     * @param itemCode 商品コード
     * @param itemName 商品名
     * @param shippingQty 出荷数
     * @param workingQty 作業数
     * @param shippingCnt 出荷回数
     */
    public void addToAbcAnaList(String customerCode, String customerName, String itemCode, String itemName,
            long shippingQty, long workingQty, long shippingCnt)
    {
        AbcDataSet dtSet =
                new AbcDataSet(customerCode, customerName, itemCode, itemName, shippingQty, workingQty, shippingCnt);
        _abcAnalysisList.add(dtSet);
    }

    /**
     * ABC分析データ配列の初期化を行います。
     */
    public void clearAbcAnaList()
    {
        _abcAnalysisList.clear();
    }

    /**
     * ABC分析データ配列のサイズを取得します。
     * @return 配列のサイズ
     */
    public int getSizeOfAbcAnaList()
    {
        return _abcAnalysisList.size();
    }

    /**
     * ABC分析データ配列を作業数の降順でソートします。
     */
    public void sortAbcAnaListByWorkingQty()
    {
        Collections.sort(_abcAnalysisList, new Comparator<AbcDataSet>()
        {
            public int compare(AbcDataSet d1, AbcDataSet d2)
            {
                return (int)(d2._workingQty1 - d1._workingQty1);
            }
        });
    }

    /**
     * ABC分析データ配列を出荷回数の降順でソートします。
     */
    public void sortAbcAnaListByShippingCnt()
    {
        Collections.sort(_abcAnalysisList, new Comparator<AbcDataSet>()
        {
            public int compare(AbcDataSet d1, AbcDataSet d2)
            {
                return (int)(d2._shippingCnt1 - d1._shippingCnt1);
            }
        });
    }

    /**
     * ABC分析を行います。（ランク付けを行います。）
     * @param thA Aランクしきい値
     * @param thB Bランクしきい値
     */
    public void calcAbcAnaList(int thA, int thB)
    {
        // 分析種別
        int analysisType = 0;
        if (_analysisType.equals(DispResources.getText("LBL-W1206")))
        {
            analysisType = 1;
        }

        long totalQty = 0;
        if (analysisType == 0)
        {
            // 作業数の降順でソートする。
            sortAbcAnaListByWorkingQty();

            // 作業数の総和を求める。
            for (int i = 0; i < getSizeOfAbcAnaList(); i++)
            {
                AbcDataSet dt = _abcAnalysisList.get(i);
                totalQty += dt.getWorkingQty();
            }
        }
        else
        {
            // 出荷回数の降順でソートする。
            sortAbcAnaListByShippingCnt();

            // 出荷回数の総和を求める。
            for (int i = 0; i < getSizeOfAbcAnaList(); i++)
            {
                AbcDataSet dt = _abcAnalysisList.get(i);
                totalQty += dt.getShippingCnt();
            }
        }
        _totalQty = totalQty;

        // 各要素の構成比と累積構成比、ランクをセットする。
        double accumRatio = 0;
        for (int i = 0; i < getSizeOfAbcAnaList(); i++)
        {
            AbcDataSet dt = _abcAnalysisList.get(i);
            if (analysisType == 0)
            {
                double ratio = (double)dt.getWorkingQty() / (double)totalQty;
                dt.setRatio(ratio);
            }
            else
            {
                double ratio = (double)dt.getShippingCnt() / (double)totalQty;
                dt.setRatio(ratio);
            }
            accumRatio += dt.getRatio();
            dt.setAccumRatio(accumRatio);
            if (accumRatio <= ((double)thA / 100))
            {
                dt.setRankCode(RANK_A);
            }
            else if (accumRatio <= ((double)thB / 100))
            {
                dt.setRankCode(RANK_B);
            }
            else
            {
                dt.setRankCode(RANK_C);
            }
        }
    }

    /**
     * ABC分析データ配列より表示対象外のレコードを削除します。
     *
     * @param dispRank 表示対象ランク 
     */
    public void removeUndisplayRecords(String dispRank)
    {
        if (StringUtil.isBlank(dispRank) || dispRank.equals(RANK_ALL))
        {
            return;
        }

        for (int i = 0; i < getSizeOfAbcAnaList(); i++)
        {
            if (dispRank.equals(getRankFromAbcAnaList(i)))
            {
                continue;
            }
            removeRecordFromAbcAnaList(i);
            i--;
        }
    }

    /**
     * ABC分析データ配列そのものを取得します。
     * @return ABC分析データ配列
     */
    public List getAbcAnaList()
    {
        return _abcAnalysisList;
    }

    /**
     * ABC分析データ配列そのものを代入します。
     * @param abcList ABC分析データ配列
     */
    public void setAbcAnaList(List<AbcDataSet> abcList)
    {
        _abcAnalysisList = abcList;
    }

    /**
     * ABC分析データ配列から指定した位置のレコード（要素）を取得します。
     * @param idx 配列添え字
     * @return レコード（要素）
     */
    public Object getRecordFromAbcAnaList(int idx)
    {
        if (idx >= getSizeOfAbcAnaList())
        {
            return null;
        }

        return _abcAnalysisList.get(idx);
    }

    /**
     * ABC分析データ配列から指定した位置のレコード（要素）を削除します。
     * @param idx 配列添え字
     */
    public void removeRecordFromAbcAnaList(int idx)
    {
        if (idx < getSizeOfAbcAnaList())
        {
            _abcAnalysisList.remove(idx);
        }
        return;
    }

    /**
     * ABC分析データ配列から指定した位置の出荷先コードを取得します。
     * @param idx 配列添え字
     * @return 出荷先コード
     */
    public String getCustomerCodeFromAbcAnaList(int idx)
    {
        if (idx >= getSizeOfAbcAnaList())
        {
            return "";
        }

        AbcDataSet dt = _abcAnalysisList.get(idx);
        if (dt._customerCode1 == null)
        {
            return "";
        }
        return dt._customerCode1;
    }

    /**
     * ABC分析データ配列から指定した位置の出荷先名称を取得します。
     * @param idx 配列添え字
     * @return 出荷先名称
     */
    public String getCustomerNameFromAbcAnaList(int idx)
    {
        if (idx >= getSizeOfAbcAnaList())
        {
            return "";
        }

        AbcDataSet dt = _abcAnalysisList.get(idx);
        if (dt._customerName1 == null)
        {
            return "";
        }
        return dt._customerName1;
    }

    /**
     * ABC分析データ配列から指定した位置の商品コードを取得します。
     * @param idx 配列添え字
     * @return 商品コード
     */
    public String getItemCodeFromAbcAnaList(int idx)
    {
        if (idx >= getSizeOfAbcAnaList())
        {
            return "";
        }

        AbcDataSet dt = _abcAnalysisList.get(idx);
        if (dt._itemCode1 == null)
        {
            return "";
        }
        return dt._itemCode1;
    }

    /**
     * ABC分析データ配列から指定した位置の商品名称を取得します。
     * @param idx 配列添え字
     * @return 商品名称
     */
    public String getItemNameFromAbcAnaList(int idx)
    {
        if (idx >= getSizeOfAbcAnaList())
        {
            return "";
        }

        AbcDataSet dt = _abcAnalysisList.get(idx);
        if (dt._itemName1 == null)
        {
            return "";
        }
        return dt._itemName1;
    }

    /**
     * ABC分析データ配列から指定した位置の出荷数を取得します。
     * @param idx 配列添え字
     * @return 出荷数
     */
    public long getShippingQtyFromAbcAnaList(int idx)
    {
        if (idx >= getSizeOfAbcAnaList())
        {
            return -1;
        }

        AbcDataSet dt = _abcAnalysisList.get(idx);
        return dt._shippingQty1;
    }

    /**
     * ABC分析データ配列から指定した位置の出荷回数を取得します。
     * @param idx 配列添え字
     * @return 出荷回数
     */
    public long getShippingCntFromAbcAnaList(int idx)
    {
        if (idx >= getSizeOfAbcAnaList())
        {
            return -1;
        }

        AbcDataSet dt = _abcAnalysisList.get(idx);
        return dt._shippingCnt1;
    }

    /**
     * ABC分析データ配列から指定した位置の作業数を取得します。
     * @param idx 配列添え字
     * @return 作業数
     */
    public long getWorkingQtyFromAbcAnaList(int idx)
    {
        if (idx >= getSizeOfAbcAnaList())
        {
            return -1;
        }

        AbcDataSet dt = _abcAnalysisList.get(idx);
        return dt._workingQty1;
    }

    /**
     * ABC分析データ配列から指定した位置の構成比を取得します。
     * @param idx 配列添え字
     * @return 構成比
     */
    public double getRatioFromAbcAnaList(int idx)
    {
        if (idx >= getSizeOfAbcAnaList())
        {
            return -1;
        }

        AbcDataSet dt = _abcAnalysisList.get(idx);
        return dt._ratio1;
    }

    /**
     * ABC分析データ配列から指定した位置の累積構成比を取得します。
     * @param idx 配列添え字
     * @return 累積構成比
     */
    public double getAccumRatioFromAbcAnaList(int idx)
    {
        if (idx >= getSizeOfAbcAnaList())
        {
            return -1;
        }

        AbcDataSet dt = _abcAnalysisList.get(idx);
        return dt._accumRatio1;
    }

    /**
     * ABC分析データ配列から指定した位置のランクコードを取得します。
     * @param idx 配列添え字
     * @return ランクコード
     */
    public String getRankFromAbcAnaList(int idx)
    {
        if (idx >= getSizeOfAbcAnaList())
        {
            return "";
        }

        AbcDataSet dt = _abcAnalysisList.get(idx);
        return dt._rankCode1;
    }

    /**
     * ABC分析データの分析数量合計を取得します。
     *
     * @return 分析数量合計
     */
    public long getTotalQty()
    {
        return _totalQty;
    }

    /**
     * 実績グラフデータ配列へデータをセット（追加）します。
     * @param workDate 日付
     * @param totalWorkinTime 総作業時間
     * @param itemQty 商品数
     * @param totalPieces 総ピース数
     * @param simuWorkingTime 予測作業時間
     */
    public void addToAbcAnaList(String workDate, double totalWorkinTime, double itemQty, double totalPieces,
            double simuWorkingTime)
    {
        ResultDataSet dtSet = new ResultDataSet(workDate, totalWorkinTime, itemQty, totalPieces, simuWorkingTime);
        _resultDataList.add(dtSet);
        // 総作業時間
        if (_maxWorkingTime < totalWorkinTime)
        {
            _maxWorkingTime = totalWorkinTime;
        }
        if (_minWorkingTime > totalWorkinTime)
        {
            _minWorkingTime = totalWorkinTime;
        }
        if (_maxWorkingTime < simuWorkingTime)
        {
            _maxWorkingTime = simuWorkingTime;
        }
        if (_minWorkingTime > simuWorkingTime)
        {
            _minWorkingTime = simuWorkingTime;
        }

        // 商品数
        if (_maxItemQty < itemQty)
        {
            _maxItemQty = itemQty;
        }
        if (_minItemQty > itemQty)
        {
            _minItemQty = itemQty;
        }

        // 総ピース数
        if (_maxTotalPieces < totalPieces)
        {
            _maxTotalPieces = totalPieces;
        }
        if (_minTotalPieces > totalPieces)
        {
            _minTotalPieces = totalPieces;
        }
    }

    /**
     * 実績グラフデータ配列の初期化を行います。
     */
    public void clearResultDataList()
    {
        _resultDataList.clear();
        _maxWorkingTime = 0;
        _minWorkingTime = 999999999;
        _maxItemQty = 0;
        _minItemQty = 999999999;
        _maxTotalPieces = 0;
        _minTotalPieces = 999999999;
    }

    /**
     * 実績グラフデータ配列のサイズを取得します。
     * @return 配列のサイズ
     */
    public int getSizeOfResultDataList()
    {
        return _resultDataList.size();
    }

    /**
     * 実績グラフデータ配列そのものを取得します。
     * @return ABC分析データ配列
     */
    public List getResultDataList()
    {
        return _resultDataList;
    }

    /**
     * 実績グラフデータ配列そのものを代入します。
     * @param abcList 実績グラフデータ配列
     */
    public void setResultDataList(List<ResultDataSet> abcList)
    {
        _resultDataList = abcList;
    }

    /**
     * 実績グラフデータ配列から指定した位置のレコード（要素）を取得します。
     * @param idx 配列添え字
     * @return レコード（要素）
     */
    public Object getRecordFromResultDataList(int idx)
    {
        if (idx >= getSizeOfResultDataList())
        {
            return null;
        }

        return _resultDataList.get(idx);
    }

    /**
     * 実績グラフデータ配列から指定した位置のレコード（要素）を削除します。
     * @param idx 配列添え字
     */
    public void removeRecordFromResultDataList(int idx)
    {
        if (idx < getSizeOfResultDataList())
        {
            _resultDataList.remove(idx);
        }

        return;
    }

    /**
     * 実績グラフデータ配列内の総作業時間最大値を取得します。
     * @return 総作業時間最大値
     */
    public double getMaxWorkingTime()
    {
        return _maxWorkingTime;
    }

    /**
     * 実績グラフデータ配列内の総作業時間最小値を取得します。
     * @return 総作業時間最小値
     */
    public double getMinWorkingTime()
    {
        return _minWorkingTime;
    }

    /**
     * 実績グラフデータ配列内の商品数最大値を取得します。
     * @return 商品数最大値
     */
    public double getMaxItemQty()
    {
        return _maxItemQty;
    }

    /**
     * 実績グラフデータ配列内の商品数最小値を取得します。
     * @return 商品数最小値
     */
    public double getMinItemQty()
    {
        return _minItemQty;
    }

    /**
     * 実績グラフデータ配列内の総ピース数最大値を取得します。
     * @return 総ピース数最大値
     */
    public double getMaxTotalPieces()
    {
        return _maxTotalPieces;
    }

    /**
     * 実績グラフデータ配列内の総ピース数最小値を取得します。
     * @return 総ピース数最小値
     */
    public double getMinTotalPieces()
    {
        return _minTotalPieces;
    }

    /**
     * 実績グラフデータ配列から指定した位置の日付を取得します。
     * @param idx 配列添え字
     * @return 日付
     */
    public String getWorkDateFromResultDataList(int idx)
    {
        if (idx >= getSizeOfResultDataList())
        {
            return "";
        }

        ResultDataSet dt = _resultDataList.get(idx);
        if (dt._workDate == null)
        {
            return "";
        }
        return dt._workDate;
    }

    /**
     * 実績グラフデータ配列から指定した位置の総作業時間を取得します。
     * @param idx 配列添え字
     * @return 総作業時間
     */
    public double getTotalWorkingTimeFromResultDataList(int idx)
    {
        if (idx >= getSizeOfResultDataList())
        {
            return -1;
        }

        ResultDataSet dt = _resultDataList.get(idx);
        return dt.getTotalWorkinTime();
    }

    /**
     * 実績グラフデータ配列から指定した位置の商品数を取得します。
     * @param idx 配列添え字
     * @return 商品数
     */
    public double getItemQtyFromResultDataList(int idx)
    {
        if (idx >= getSizeOfResultDataList())
        {
            return -1;
        }

        ResultDataSet dt = _resultDataList.get(idx);
        return dt.getItemQty();
    }

    /**
     * 実績グラフデータ配列から指定した位置の作業数を取得します。
     * @param idx 配列添え字
     * @return 総ピース数
     */
    public double getTotalPiecesFromResultDataList(int idx)
    {
        if (idx >= getSizeOfResultDataList())
        {
            return -1;
        }

        ResultDataSet dt = _resultDataList.get(idx);
        return dt.getTotalPieces();
    }

    /**
     * 実績グラフデータ配列から指定した位置の予測作業時間を取得します。
     * @param idx 配列添え字
     * @return 予測作業時間
     */
    public double getSimuWorkingTimeFromResultDataList(int idx)
    {
        if (idx >= getSizeOfResultDataList())
        {
            return -1;
        }

        ResultDataSet dt = _resultDataList.get(idx);
        return dt.getSimuWorkingTime();
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
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
        return "$Id: AnalysisOutParameter.java 443 2008-10-21 10:29:08Z nakai $";
    }
}

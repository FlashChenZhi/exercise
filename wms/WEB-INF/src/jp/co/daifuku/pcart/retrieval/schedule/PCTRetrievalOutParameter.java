package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.List;

import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.OutParameter;

/**
 * <CODE>PCTRetrievalOutParameter</CODE>クラスは、pcart.retrievalパッケージ内のスケジュール→画面間のパラメータの受渡しに使用します。<BR>
 * このクラスではpcart.retrievalパッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>PCTRetrievalOutParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 * 
 * オーダーNo.<BR>
 * 出荷先コード<BR>
 * 出荷先名称<BR>
 * 予定エリアNo.<BR>
 * エリア名称<BR>
 * 得意先コード<BR>
 * ユーザID<BR>
 * ユーザ名称<BR>
 * ランク<BR>
 * 作業日<BR>
 * 荷主コード<BR>
 * 荷主名称<BR>
 * エリアNo.<BR>
 * バッチNo.<BR>
 * 生産率<BR>
 * 人数<BR>
 * 台数<BR>
 * 開始日時<BR>
 * 終了日時<BR>
 * 総時間<BR>
 * 実時間<BR>
 * 集品時間<BR> 
 * 中断時間<BR> 
 * 投入時間<BR>
 * 作業時間（延べ）<BR>
 * オーダー数<BR>
 * オーダー数/H<BR>
 * 箱数<BR>
 * 箱数/H<BR>
 * 行数<BR> 
 * 行数/H<BR> 
 * 数量（ロット）<BR> 
 * 数量（ロット）/H<BR> 
 * 数量（バラ）<BR> 
 * 数量（バラ）/H<BR> 
 * 箱/オーダー<BR> 
 * 行/オーダー<BR> 
 * ロット/行<BR> 
 * バラ/行<BR> 
 * ミス率<BR> 
 * ランク(平均)<BR>
 * 生産率(平均)<BR> 
 * 人数(平均)<BR> 
 * 台数(平均)<BR> 
 * 総時間(平均)<BR> 
 * 実時間(平均)<BR> 
 * 集品時間(平均)<BR> 
 * 中断時間(平均)<BR>
 * 投入時間(平均)<BR> 
 * 作業時間（延べ）(平均)<BR> 
 * オーダー数(平均)<BR> 
 * オーダー数/H(平均)<BR> 
 * 箱数(平均)<BR> 
 * 箱数/H(平均)<BR> 
 * 行数(平均)<BR> 
 * 行数/H(平均)<BR> 
 * 数量（ロット）(平均)<BR> 
 * 数量（ロット）/H(平均)<BR> 
 * 数量（バラ）(平均)<BR> 
 * 数量（バラ）/H(平均)<BR> 
 * 箱/オーダー(平均)<BR> 
 * 行/オーダー(平均)<BR> 
 * ロット/行(平均)<BR> 
 * バラ/行(平均)<BR> 
 * ミス率(平均)<BR> 
 * 予定日<BR>
 * 商品コード<BR>
 * 商品名称<BR>
 * JANコード<BR>
 * オーダー分割No.<BR>
 * 予定数<BR>
 * 実績数<BR>
 * 作業状態<BR>
 * ロット入数<BR>
 * ゾーンNo.<BR>
 * 予定棚<BR>
 * ケースITF<BR>
 * ボールITF<BR>
 * 行数(商品アイテム数)<BR>
 * 最終更新日時(最近)<BR>
 * 最終更新日時(リスト)<BR>
 * 予定一意キー(リスト)<BR> 
 * バッチ状態フラグ<BR>
 * Rft号機No.<BR>
 * 予定一意キー<BR>
 * 作業No.<BR>
 * 最終更新日時<BR>
 * ボタン制御フラグ<BR>
 * 予定オーダー件数<BR>
 * 実績オーダー件数<BR>
 * 予定行数<BR>
 * 実績行数<BR>
 * 予定ロット数<BR>
 * 実績ロット数<BR>
 * 終了予測時間<BR>
 * カート台数<BR>
 * 設定単位キー<BR>
 * バッチSEQNo.<BR>
 * 予定オーダーNo.<BR>
 * 通番<BR>
 * オーダー内商品数<BR>
 * オーダー通番<BR>
 * オーダー通番合計<BR>
 * 汎用フラグ<BR>
 * シュートNo.<BR>
 * 作業ゾーン<BR>
 * 開始棚<BR>
 * 終了棚<BR>
 * ロール<BR>
 * ロール名<BR>
 * ランクA基準値<BR>
 * ランクB基準値<BR>
 * 総ロット数<BR>
 * 総オーダー数<BR>
 * 総行数<BR>
 * 月別生産率(ロット）<BR>
 * 月別生産率(オーダー）<BR>
 * 月別生産率(行）<BR>
 * 作業月<BR>
 * 生産率(double)<BR>
 * 重量未登録商品フラグ<BR>
 * オーダーNo.採番フラグ<BR>
 * </DIR>
 * 
 * Designer : K.Bingo <BR>
 * Maker : K.Bingo <BR>
 *
 * @version $Revision: 3294 $, $Date: 2009-03-10 21:25:32 +0900 (火, 10 3 2009) $
 * @author  $Author: rnakai $
 */
public class PCTRetrievalOutParameter
        extends OutParameter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * オーダーNo.
     */
    private String _orderNo;

    /**
     * 出荷先コード
     */
    private String _customerCode;

    /**
     * 出荷先名称
     */
    private String _customerName;

    /**
     * 予定エリアNo.
     */
    private String _planAreaNo;

    /**
     * エリア名称
     */
    private String _areaName;

    /**
     * 得意先コード
     */
    private String _regularCustomerCode;

    /**
     * 得意先名称
     */
    private String _regularCustomerName;

    /**
     * ユーザID
     */
    private String _userId;

    /**
     * ユーザ名称
     */
    private String _userName;

    /**
     * ランク
     */
    private String _rank;

    /**
     * 作業日
     */
    private String _workDay;

    /**
     * 荷主コード
     */
    private String _consignorCode;

    /**
     * 荷主名称
     */
    private String _consignorName;

    /**
     * エリアNo.
     */
    private String _areaNo;

    /**
     * バッチNo.
     */
    private String _batchNo;

    /**
     * 生産率
     */
    private String _productionRate;

    /**
     * 人数
     */
    private int _workerCnt;

    /**
     * 台数
     */
    private int _terminalCnt;

    /**
     * 開始日時
     */
    private String _startTime;

    /**
     * 終了日時
     */
    private String _endTime;

    /**
     * 総時間
     */
    private int _totalTime;

    /**
     * 実時間
     */
    private int _realTime;

    /**
     * 集品時間
     */
    private int _collectTime;

    /**
     * 中断時間
     */
    private int _stopTime;

    /**
     * 投入時間
     */
    private String _inputTime;

    /**
     * 作業時間（延べ）
     */
    private int _totalWorkTime;

    /**
     * オーダー数
     */
    private int _orderCnt;

    /**
     * オーダー数/H
     */
    private double _orderCntPerHour;

    /**
     * 箱数
     */
    private int _boxCnt;

    /**
     * 箱数/H
     */
    private double _boxCntPerHour;

    /**
     * 行数
     */
    private int _lineCnt;

    /**
     * 行数/H
     */
    private double _lineCntPerHour;

    /**
     * 数量（ロット）
     */
    private int _lotQty;

    /**
     * 数量（ロット）/H
     */
    private double _lotQtyPerHour;

    /**
     * 数量（バラ）
     */
    private int _pieceQty;

    /**
     * 数量（バラ）/H
     */
    private double _pieceQtyPerHour;

    /**
     * 箱/オーダー
     */
    private double _boxCntPerOrder;

    /**
     * 行/オーダー
     */
    private double _lineCntPerOrder;

    /**
     * ロット/行
     */
    private double _lotQtyPerLine;

    /**
     * バラ/行
     */
    private double _pieceQtyPerLine;

    /**
     * ミス率
     */
    private int _missRate;

    /**
     * ランク(平均)
     */
    private String _rankAvg;

    /**
     * 生産率(平均)
     */
    private double _productionRateAvg;

    /**
     * 人数(平均)
     */
    private int _workerCntAvg;

    /**
     * 台数(平均)
     */
    private int _terminalCntAvg;

    /**
     * 総時間(平均)
     */
    private int _totalTimeAvg;

    /**
     * 実時間(平均)
     */
    private int _realTimeAvg;

    /**
     * 集品時間(平均)
     */
    private int _collectTimeAvg;

    /**
     * 中断時間(平均)
     */
    private int _stopTimeAvg;

    /**
     * 投入時間(平均)
     */
    private String _inputTimeAvg;

    /**
     * 作業時間（延べ）(平均)
     */
    private int _totalWorkTimeAvg;

    /**
     * オーダー数(平均)
     */
    private int _orderCntAvg;

    /**
     * オーダー数/H(平均)
     */
    private double _orderCntPerHourAvg;

    /**
     * 箱数(平均)
     */
    private int _boxCntAvg;

    /**
     * 箱数/H(平均)
     */
    private double _boxCntPerHourAvg;

    /**
     * 行数(平均)
     */
    private int _lineCntAvg;

    /**
     * 行数/H(平均)
     */
    private double _lineCntPerHourAvg;

    /**
     * 数量（ロット）(平均)
     */
    private int _lotQtyAvg;

    /**
     * 数量（ロット）/H(平均)
     */
    private double _lotQtyPerHourAvg;

    /**
     * 数量（バラ）(平均)
     */
    private int _pieceQtyAvg;

    /**
     * 数量（バラ）/H(平均)
     */
    private double _pieceQtyPerHourAvg;

    /**
     * 箱/オーダー(平均)
     */
    private double _boxCntPerOrderAvg;

    /**
     * 行/オーダー(平均)
     */
    private double _lineCntPerOrderAvg;

    /**
     * ロット/行(平均)
     */
    private double _lotQtyPerLineAvg;

    /**
     * バラ/行(平均)
     */
    private double _pieceQtyPerLineAvg;

    /**
     * ミス率(平均)
     */
    private int _missRateAvg;

    /**
     * 予定日
     */
    private String _planDay;

    /**
     * 商品コード
     */
    private String _itemCode;

    /**
     * 商品名称
     */
    private String _itemName;

    /**
     * JANコード
     */
    private String _jan;

    /**
     * オーダー分割No.
     */
    private int _orderSeqNo;

    /**
     * 予定数
     */
    private int _planQty;

    /**
     * 実績数
     */
    private int _resultQty;

    /**
     * ロット入数
     */
    private int _lotEnteringQty;

    /**
     * 状態フラグ
     */
    private String _statusFlag;

    /**
     * 予定ゾーン
     */
    private String _planZoneNo;

    /**
     * 予定棚
     */
    private String _planLocationNo;

    /**
     * ケースITF
     */
    private String _itf;

    /**
     * ボールITF
     */
    private String _bundleItf;

    /**
     * 行数(バッチNo数)
     */
    private int _line;

    /**
     * 最終更新日時(最近)
     */
    private java.util.Date _update;

    /**
     * 最終更新日時(リスト)
     */
    private java.util.Date[] _lastUpdateDateList;

    /**
     * 予定一意キー(リスト)
     */
    private String[] _ukeyList;

    /**
     * バッチ状態フラグ
     */
    private String _batchStatusFlag;

    /**
     * Rft号機No.
     */
    private String _rftNo;

    /**
     * 予定一意キー
     */
    private String _planUkey;

    /**
     * 作業No.
     */
    private String _jobNo;

    /**
     * 最終更新日時
     */
    private String _lastUpdateDate;

    /**
     * ボタン制御フラグ
     */
    private String _buttonControlFlag;

    /**
     * 予定オーダー件数
     */
    private long _planOrderCnt;

    /**
     * 実績オーダー件数
     */
    private long _resultOrderCnt;

    /**
     * 予定行数
     */
    private long _planLineCnt;

    /**
     * 実績行数
     */
    private long _resultLineCnt;

    /**
     * 予定ロット数
     */
    private long _planLotCnt;

    /**
     * 実績ロット数
     */
    private long _resultLotCnt;

    /**
     * 終了予測時間
     */
    private double _endPlanTime;

    /**
     * カート台数
     */
    private long _cartCnt;

    /**
     * 設定単位キー
     */
    private String _settingUnitKey;

    /**
     * 曜日
     */
    private String _dayOfWeek;

    /**
     * 開始作業日
     */
    private String _workDayto;

    /**
     * 終了作業日
     */
    private String _workDayFrom;

    /**
     * 検索条件荷主ｺｰﾄﾞ
     */
    private String _searchConsignorCode;

    /**
     * 検索条件ｴﾘｱ
     */
    private String _searchArea;

    /**
     * 検索条件バッチNo.
     */
    private String _searchBatchNo;

    /**
     * 検索条件荷主名称
     */
    private String _searchConsignorName;

    /**
     * 検索条件ｴﾘｱ名称
     */
    private String _searchAreaName;

    /**
     * バッチ予定SeqNo.
     */
    private String _batchSeqNo;

    /**
     * 予定オーダーNo.
     */
    private String _planOrderNo;

    /**
     * 実績オーダーNo.
     */
    private String _resultOrderNo;

    /**
     * オーダーSeqNo.
     */
    private String _OrderSeq;

    /**
     * 通番
     */
    private int _throughNo;

    /**
     * オーダー内商品数
     */
    private int _orderItemQty;

    /**
     * オーダー通番
     */
    private int _orderThroughNo;

    /**
     * オーダー通番合計
     */
    private int _orderThroughNoCnt;

    /**
     * 汎用フラグ
     */
    private String _generalFlag;

    /**
     * シュートNo.
     */
    private String _shootNo;

    /**
     * 作業ゾーン
     */
    private String _workZoneNo;

    /**
     * 開始棚
     */
    private String _startLocationNo;

    /**
     * 終了棚
     */
    private String _endLocationNo;

    /**
     * ロール
     */
    private String _role;

    /**
     * ロール名
     */
    private String _roleName;

    /**
     * ランクA基準値
     */
    private int _rankA;

    /**
     * ランクB基準値
     */
    private int _rankB;

    /**
     * 総ロット数
     */
    private long _totalLot;

    /**
     * 総オーダー数
     */
    private long _totalOrder;

    /**
     * 総行数
     */
    private long _totalLine;

    /**
     * 月別生産率（ロット）
     */
    private double _monthProductRateLot;

    /**
     * 月別生産率（オーダー）
     */
    private double _monthProductRateOrder;

    /**
     * 月別生産率（行）
     */
    private double _monthProductRateLine;

    /**
     * 作業月
     */
    private String _workMonth;

    /**
     * 生産率(double)
     */
    private double _productionRateVal;

    /**
     * 重量未登録商品フラグ
     */
    private boolean _weightUnregist;

    /**
     * 残ｵｰﾀﾞｰ数
     */
    private int _undoneOrderCnt;

    /**
     * 最小ゾーン
     */
    private String _minWorkZoneNo;

    /**
     * 欠品数
     */
    private int _shotageQty;

    /**
     * オーダーNo.採番フラグ（オーダーをeWareNaviで採番する場合はtrue、上位指定の場合はfalse）
     */
    private boolean _createOrder;
    
    /**
     * 作業No.リスト
     */
    private List<String> _jobNoList;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    /**
     * searchAreaNameを返します。
     * @return searchAreaNameを返します。
     */
    public String getSearchAreaName()
    {
        return _searchAreaName;
    }

    /**
     * searchAreaNameを設定します。
     * @param searchAreaName searchAreaName
     */
    public void setSearchAreaName(String searchAreaName)
    {
        _searchAreaName = searchAreaName;
    }

    /**
     * searchConsignorNameを返します。
     * @return searchConsignorNameを返します。
     */
    public String getSearchConsignorName()
    {
        return _searchConsignorName;
    }

    /**
     * searchConsignorNameを設定します。
     * @param searchConsignorName searchConsignorName
     */
    public void setSearchConsignorName(String searchConsignorName)
    {
        _searchConsignorName = searchConsignorName;
    }

    /**
     * searchBatchNoを返します。
     * @return searchBatchNoを返します。
     */
    public String getSearchBatchNo()
    {
        return _searchBatchNo;
    }

    /**
     * searchBatchNoを設定します。
     * @param searchBatchNo searchBatchNo
     */
    public void setSearchBatchNo(String searchBatchNo)
    {
        _searchBatchNo = searchBatchNo;
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
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
     * 出荷先コードを返します。
     * @return 出荷先コードを返します。
     */
    public String getCustomerCode()
    {
        return _customerCode;
    }

    /**
     * 出荷先コードを設定します。
     * @param customorCode 出荷先コード
     */
    public void setCustomerCode(String customorCode)
    {
        _customerCode = customorCode;
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
     * 予定エリアNo.を返します。
     * @return エリアNo.を返します。
     */
    public String getPlanAreaNo()
    {
        return _planAreaNo;
    }

    /**
     * 予定エリアNo.を設定します。
     * @param planAreaNo エリアNo.
     */
    public void setPlanAreaNo(String planAreaNo)
    {
        _planAreaNo = planAreaNo;
    }

    /**
     * エリア名称を返します。
     * @return エリア名称を返します。
     */
    public String getAreaName()
    {
        return _areaName;
    }

    /**
     * エリア名称を設定します。
     * @param areaName エリア名称
     */
    public void setAreaName(String areaName)
    {
        _areaName = areaName;
    }

    /**
     * 得意先コードを返します。
     * @return 得意先コードを返します。
     */
    public String getRegularCustomerCode()
    {
        return _regularCustomerCode;
    }

    /**
     * 得意先コードを設定します。
     * @param regularCustomerCode 得意先名称
     */
    public void setRegularCustomerCode(String regularCustomerCode)
    {
        _regularCustomerCode = regularCustomerCode;
    }

    /**
     * 得意先名称を返します。
     * @return 得意先コードを返します。
     */
    public String getRegularCustomerName()
    {
        return _regularCustomerName;
    }

    /**
     * 得意先名称を設定します。
     * @param regularCustomerName 得意先名称
     */
    public void setRegularCustomerName(String regularCustomerName)
    {
        _regularCustomerName = regularCustomerName;
    }

    /**
     * ユーザIDを返します。
     * @return ユーザIDを返します。
     */
    public String getUserId()
    {
        return _userId;
    }

    /**
     * ユーザIDを設定します。
     * @param userId ユーザID
     */
    public void setUserId(String userId)
    {
        _userId = userId;
    }

    /**
     * ユーザ名称を返します。
     * @return ユーザ名称を返します。
     */
    public String getUserName()
    {
        return _userName;
    }

    /**
     * ユーザ名称を設定します。
     * @param userName ユーザ名称
     */
    public void setUserName(String userName)
    {
        _userName = userName;
    }

    /**
     * ランクを返します。
     * @return ランクを返します。
     */
    public String getRank()
    {
        return _rank;
    }

    /**
     * ランクを設定します。
     * @param rank ランク
     */
    public void setRank(String rank)
    {
        _rank = rank;
    }

    /**
     * 生産率からランクを設定します。
     * @param rate 生産率
     * @param rankAStandard ランクA基準値
     * @param rankBStandard ランクB基準値
     */
    public void setRank(int rate, int rankAStandard, int rankBStandard)
    {
        // ランクの設定を行う
        if (rate >= rankAStandard)
        {
            _rank = DisplayText.getText("LBL-P0087");
        }
        else if (rate < rankBStandard)
        {
            _rank = DisplayText.getText("LBL-P0089");
        }
        else
        {
            _rank = DisplayText.getText("LBL-P0088");
        }
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
     * エリアNo.を返します。
     * @return エリアNo.を返します。
     */
    public String getAreaNo()
    {
        return _areaNo;
    }

    /**
     * エリアNo.を設定します。
     * @param areaNo エリアNo.
     */
    public void setAreaNo(String areaNo)
    {
        _areaNo = areaNo;
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
     * 生産率を返します。
     * @return 生産率を返します。
     */
    public String getProductionRate()
    {
        return _productionRate;
    }

    /**
     * 生産率を設定します。
     * @param productionRate 生産率
     */
    public void setProductionRate(String productionRate)
    {
        _productionRate = productionRate;
    }

    /**
     * 人数を返します。
     * @return 人数を返します。
     */
    public int getWorkerCnt()
    {
        return _workerCnt;
    }

    /**
     * 人数を設定します。
     * @param workerCount 人数
     */
    public void setWorkerCnt(int workerCount)
    {
        _workerCnt = workerCount;
    }

    /**
     * 台数を返します。
     * @return 台数を返します。
     */
    public int getTerminalCnt()
    {
        return _terminalCnt;
    }

    /**
     * 台数を設定します。
     * @param terminalCount 台数
     */
    public void setTerminalCnt(int terminalCount)
    {
        _terminalCnt = terminalCount;
    }

    /**
     * 開始日時を返します。
     * @return 開始日時を返します。
     */
    public String getStartTime()
    {
        return _startTime;
    }

    /**
     * 開始日時を設定します。
     * @param startTime 開始日時
     */
    public void setStartTime(String startTime)
    {
        _startTime = startTime;
    }

    /**
     * 終了日時を返します。
     * @return 終了日時を返します。
     */
    public String getEndTime()
    {
        return _endTime;
    }

    /**
     * 終了日時を設定します。
     * @param endTime 終了日時
     */
    public void setEndTime(String endTime)
    {
        _endTime = endTime;
    }

    /**
     * 総時間を返します。
     * @return 総時間を返します。
     */
    public int getTotalTime()
    {
        return _totalTime;
    }

    /**
     * 総時間を設定します。
     * @param totalTime 総時間
     */
    public void setTotalTime(int totalTime)
    {
        _totalTime = totalTime;
    }

    /**
     * 実時間を返します。
     * @return 実時間を返します。
     */
    public int getRealTime()
    {
        return _realTime;
    }

    /**
     * 実時間を設定します。
     * @param i 実時間
     */
    public void setRealTime(int i)
    {
        _realTime = i;
    }

    /**
     * 集品時間を返します。
     * @return 集品時間を返します。
     */
    public int getCollectTime()
    {
        return _collectTime;
    }

    /**
     * 集品時間を設定します。
     * @param collectTime 集品時間
     */
    public void setCollectTime(int collectTime)
    {
        _collectTime = collectTime;
    }

    /**
     * 中断時間を返します。
     * @return 中断時間を返します。
     */
    public int getStopTime()
    {
        return _stopTime;
    }

    /**
     * 中断時間を設定します。
     * @param stopTime 中断時間
     */
    public void setStopTime(int stopTime)
    {
        _stopTime = stopTime;
    }

    /**
     * 投入時間を返します。
     * @return 投入時間をかえします。
     */
    public String getInputTime()
    {
        return _inputTime;
    }

    /**
     * 投入時間を設定します。
     * @param inputTime 投入時間
     */
    public void setInputTime(String inputTime)
    {
        _inputTime = inputTime;
    }

    /**
     * 作業時間（延べ）を返します。
     * @return 作業時間（延べ）を返します。
     */
    public int getTotalWorkTime()
    {
        return _totalWorkTime;
    }

    /**
     * 作業時間（延べ）を設定します。
     * @param totalWorkTime 作業時間（延べ）
     */
    public void setTotalWorkTime(int totalWorkTime)
    {
        _totalWorkTime = totalWorkTime;
    }

    /**
     * オーダー数を返します。
     * @return オーダー数を返します。
     */
    public int getOrderCnt()
    {
        return _orderCnt;
    }

    /**
     * オーダー数を設定します。
     * @param orderCnt オーダー数
     */
    public void setOrderCnt(int orderCnt)
    {
        _orderCnt = orderCnt;
    }

    /**
     * オーダー数/Hを返します。
     * @return オーダー数/Hを返します。
     */
    public double getOrderCntPerHour()
    {
        return _orderCntPerHour;
    }

    /**
     * オーダー数/Hを設定します。
     * @param orderCntPerHour オーダー数/H
     */
    public void setOrderCntPerHour(double orderCntPerHour)
    {
        _orderCntPerHour = orderCntPerHour;
    }

    /**
     * 箱数を返します。
     * @return 箱数を返します。
     */
    public int getBoxCnt()
    {
        return _boxCnt;
    }

    /**
     * 箱数を設定します。
     * @param boxCnt 箱数
     */
    public void setBoxCnt(int boxCnt)
    {
        _boxCnt = boxCnt;
    }

    /**
     * 箱数/Hを返します。
     * @return 箱数/Hを返します。
     */
    public double getBoxCntPerHour()
    {
        return _boxCntPerHour;
    }

    /**
     * 箱数/Hを設定します。
     * @param boxCntPerHour 箱数/H
     */
    public void setBoxCntPerHour(double boxCntPerHour)
    {
        _boxCntPerHour = boxCntPerHour;
    }

    /**
     * 行数を返します。
     * @return 行数を返します。
     */
    public int getLineCnt()
    {
        return _lineCnt;
    }

    /**
     * 行数を設定します。
     * @param lineCnt 行数
     */
    public void setLineCnt(int lineCnt)
    {
        _lineCnt = lineCnt;
    }

    /**
     * 行数/Hを返します。
     * @return 行数/Hを返します。
     */
    public double getLineCntPerHour()
    {
        return _lineCntPerHour;
    }

    /**
     * 行数/Hを設定します。
     * @param lineCntPerHour 行数/H
     */
    public void setLineCntPerHour(double lineCntPerHour)
    {
        _lineCntPerHour = lineCntPerHour;
    }

    /**
     * 数量（ロット）を返します。
     * @return 数量（ロット）を返します。
     */
    public int getLotQty()
    {
        return _lotQty;
    }

    /**
     * 数量（ロット）を設定します。
     * @param lotQty 数量（ロット）
     */
    public void setLotQty(int lotQty)
    {
        _lotQty = lotQty;
    }

    /**
     * 数量（ロット）/Hを返します。
     * @return 数量（ロット）/Hを返します。
     */
    public double getLotQtyPerHour()
    {
        return _lotQtyPerHour;
    }

    /**
     * 数量（ロット）/Hを設定します。
     * @param lotQtyPerHour 数量（ロット）/H
     */
    public void setLotQtyPerHour(double lotQtyPerHour)
    {
        _lotQtyPerHour = lotQtyPerHour;
    }

    /**
     * 数量（バラ）を返します。
     * @return 数量（バラ）を返します。
     */
    public int getPieceQty()
    {
        return _pieceQty;
    }

    /**
     * 数量（バラ）を設定します。
     * @param pieceQty 数量（バラ）
     */
    public void setPieceQty(int pieceQty)
    {
        _pieceQty = pieceQty;
    }

    /**
     * 数量（バラ）/Hを返します。
     * @return 数量（バラ）/Hを返します。
     */
    public double getPieceQtyPerHour()
    {
        return _pieceQtyPerHour;
    }

    /**
     * 数量（バラ）/Hを設定します。
     * @param pieceQtyPerHour 数量（バラ）/H
     */
    public void setPieceQtyPerHour(double pieceQtyPerHour)
    {
        _pieceQtyPerHour = pieceQtyPerHour;
    }

    /**
     * 箱/オーダーを返します。
     * @return 箱/オーダーを返します。
     */
    public double getBoxCntPerOrder()
    {
        return _boxCntPerOrder;
    }

    /**
     * 箱/オーダーを設定します。
     * @param boxCntPerOrder 箱/オーダー
     */
    public void setBoxCntPerOrder(double boxCntPerOrder)
    {
        _boxCntPerOrder = boxCntPerOrder;
    }

    /**
     * 行/オーダーを返します。
     * @return 行/オーダーを返します。
     */
    public double getLineCntPerOrder()
    {
        return _lineCntPerOrder;
    }

    /**
     * 行/オーダーを設定します。
     * @param lineCntPerOrder 行/オーダー
     */
    public void setLineCntPerOrder(double lineCntPerOrder)
    {
        _lineCntPerOrder = lineCntPerOrder;
    }

    /**
     * ロット/行を返します。
     * @return ロット/行を返します。
     */
    public double getLotQtyPerLine()
    {
        return _lotQtyPerLine;
    }

    /**
     * ロット/行を設定します。
     * @param lotQtyPerLine ロット/行
     */
    public void setLotQtyPerLine(double lotQtyPerLine)
    {
        _lotQtyPerLine = lotQtyPerLine;
    }

    /**
     * バラ/行を返します。
     * @return バラ/行を返します。
     */
    public double getPieceQtyPerLine()
    {
        return _pieceQtyPerLine;
    }

    /**
     * バラ/行を設定します。
     * @param pieceQtyPerLine バラ/行
     */
    public void setPieceQtyPerLine(double pieceQtyPerLine)
    {
        _pieceQtyPerLine = pieceQtyPerLine;
    }

    /**
     * ミス率を返します。
     * @return ミス率を返します。
     */
    public int getMissRate()
    {
        return _missRate;
    }

    /**
     * ミス率を設定します。
     * @param missRate ミス率
     */
    public void setMissRate(int missRate)
    {
        _missRate = missRate;
    }

    /**
     * ランク(平均)を返します。
     * @return ランク(平均)を返します。
     */
    public String getRankAvg()
    {
        return _rankAvg;
    }

    /**
     * ランク(平均)を設定します。
     * @param rankAvg ランク(平均)
     */
    public void setRankAvg(String rankAvg)
    {
        _rankAvg = rankAvg;
    }

    /**
     * 生産率(平均)を返します。
     * @return 生産率(平均)を返します。
     */
    public double getProductionRateAvg()
    {
        return _productionRateAvg;
    }

    /**
     * 生産率(平均)を設定します。
     * @param productionRateAvg 生産率(平均)
     */
    public void setProductionRateAvg(double productionRateAvg)
    {
        _productionRateAvg = productionRateAvg;
    }

    /**
     * 人数(平均)を返します。
     * @return 人数(平均)を返します。
     */
    public int getWorkerCntAvg()
    {
        return _workerCntAvg;
    }

    /**
     * 人数(平均)を設定します。
     * @param workerCntAvg 人数(平均)
     */
    public void setWorkerCntAvg(int workerCntAvg)
    {
        _workerCntAvg = workerCntAvg;
    }

    /**
     * 台数(平均)を返します。
     * @return 台数(平均)を返します。
     */
    public int getTerminalCntAvg()
    {
        return _terminalCntAvg;
    }

    /**
     * 台数(平均)を設定します。
     * @param terminalCntAvg 台数(平均)
     */
    public void setTerminalCntAvg(int terminalCntAvg)
    {
        _terminalCntAvg = terminalCntAvg;
    }

    /**
     * 総時間(平均)を返します。
     * @return 総時間(平均)を返します。
     */
    public int getTotalTimeAvg()
    {
        return _totalTimeAvg;
    }

    /**
     * 総時間(平均)を設定します。
     * @param totalTimeAvg 総時間(平均)
     */
    public void setTotalTimeAvg(int totalTimeAvg)
    {
        _totalTimeAvg = totalTimeAvg;
    }

    /**
     * 実時間(平均)を返します。
     * @return 実時間(平均)を返します。
     */
    public int getRealTimeAvg()
    {
        return _realTimeAvg;
    }

    /**
     * 実時間(平均)を設定します。
     * @param realTimeAvg 実時間(平均)
     */
    public void setRealTimeAvg(int realTimeAvg)
    {
        _realTimeAvg = realTimeAvg;
    }

    /**
     * 集品時間(平均)を返します。
     * @return 集品時間(平均)を返します。
     */
    public int getCollectTimeAvg()
    {
        return _collectTimeAvg;
    }

    /**
     * 集品時間(平均)を設定します。
     * @param collectTimeAvg 集品時間(平均)
     */
    public void setCollectTimeAvg(int collectTimeAvg)
    {
        _collectTimeAvg = collectTimeAvg;
    }

    /**
     * 中断時間(平均)を返します。
     * @return 中断時間(平均)を返します。
     */
    public int getStopTimeAvg()
    {
        return _stopTimeAvg;
    }

    /**
     * 中断時間(平均)を設定します。
     * @param stopTimeAvg 中断時間(平均)
     */
    public void setStopTimeAvg(int stopTimeAvg)
    {
        _stopTimeAvg = stopTimeAvg;
    }

    /**
     * 投入時間(平均)を返します。
     * @return 投入時間(平均)を返します。
     */
    public String getInputTimeAvg()
    {
        return _inputTimeAvg;
    }

    /**
     * 投入時間(平均)を設定します。
     * @param inputTimeAvg 投入時間(平均)
     */
    public void setInputTimeAvg(String inputTimeAvg)
    {
        _inputTimeAvg = inputTimeAvg;
    }

    /**
     * 作業時間（延べ）(平均)を返します。
     * @return 作業時間（延べ）(平均)を返します。
     */
    public int getTotalWorkTimeAvg()
    {
        return _totalWorkTimeAvg;
    }

    /**
     * 作業時間（延べ）(平均)を設定します。
     * @param totalWorkTimeAvg 作業時間（延べ）(平均)
     */
    public void setTotalWorkTimeAvg(int totalWorkTimeAvg)
    {
        _totalWorkTimeAvg = totalWorkTimeAvg;
    }

    /**
     * オーダー数(平均)を返します。
     * @return オーダー数(平均)を返します。
     */
    public int getOrderCntAvg()
    {
        return _orderCntAvg;
    }

    /**
     * オーダー数(平均)を設定します。
     * @param orderCntAvg オーダー数(平均)
     */
    public void setOrderCntAvg(int orderCntAvg)
    {
        _orderCntAvg = orderCntAvg;
    }

    /**
     * オーダー数/H(平均)を返します。
     * @return オーダー数/H(平均)を返します。
     */
    public double getOrderCntPerHourAvg()
    {
        return _orderCntPerHourAvg;
    }

    /**
     * オーダー数/H(平均)を設定します。
     * @param orderCntPerHourAvg オーダー数/H(平均)
     */
    public void setOrderCntPerHourAvg(double orderCntPerHourAvg)
    {
        _orderCntPerHourAvg = orderCntPerHourAvg;
    }

    /**
     * 箱数(平均)を返します。
     * @return 箱数(平均)を返します。
     */
    public int getBoxCntAvg()
    {
        return _boxCntAvg;
    }

    /**
     * 箱数(平均)を設定します。
     * @param boxCntAvg 箱数(平均)
     */
    public void setBoxCntAvg(int boxCntAvg)
    {
        _boxCntAvg = boxCntAvg;
    }

    /**
     * 箱数/H(平均)を返します。
     * @return 箱数/H(平均)を返します。
     */
    public double getBoxCntPerHourAvg()
    {
        return _boxCntPerHourAvg;
    }

    /**
     * 箱数/H(平均)を設定します。
     * @param boxPerHourAvg 箱数/H(平均)
     */
    public void setBoxCntPerHourAvg(double boxPerHourAvg)
    {
        _boxCntPerHourAvg = boxPerHourAvg;
    }

    /**
     * 行数(平均)を返します。
     * @return 行数(平均)を返します。
     */
    public int getLineCntAvg()
    {
        return _lineCntAvg;
    }

    /**
     * 行数(平均)を設定します。
     * @param lineCntAvg 行数(平均)
     */
    public void setLineCntAvg(int lineCntAvg)
    {
        _lineCntAvg = lineCntAvg;
    }

    /**
     * 行数/H(平均)を返します。
     * @return 行数/H(平均)を返します。
     */
    public double getLineCntPerHourAvg()
    {
        return _lineCntPerHourAvg;
    }

    /**
     * 行数/H(平均)を設定します。
     * @param lineCntPerHourAvg 行数/H(平均)
     */
    public void setLineCntPerHourAvg(double lineCntPerHourAvg)
    {
        _lineCntPerHourAvg = lineCntPerHourAvg;
    }

    /**
     * 数量（ロット）(平均)を返します。
     * @return 数量（ロット）(平均)を返します。
     */
    public int getLotQtyAvg()
    {
        return _lotQtyAvg;
    }

    /**
     * 数量（ロット）(平均)を設定します。
     * @param lotQtyAvg 数量（ロット）(平均)
     */
    public void setLotQtyAvg(int lotQtyAvg)
    {
        _lotQtyAvg = lotQtyAvg;
    }

    /**
     * 数量（ロット）/H(平均)を返します。
     * @return 数量（ロット）/H(平均)を返します。
     */
    public double getLotQtyPerHourAvg()
    {
        return _lotQtyPerHourAvg;
    }

    /**
     * 数量（ロット）/H(平均)を設定します。
     * @param lotQtyPerHourAvg 数量（ロット）/H(平均)
     */
    public void setLotQtyPerHourAvg(double lotQtyPerHourAvg)
    {
        _lotQtyPerHourAvg = lotQtyPerHourAvg;
    }

    /**
     * 数量（バラ）(平均)を返します。
     * @return 数量（バラ）(平均)を返します。
     */
    public int getPieceQtyAvg()
    {
        return _pieceQtyAvg;
    }

    /**
     * 数量（バラ）(平均)を設定します。
     * @param pieceQtyAvg 数量（バラ）(平均)
     */
    public void setPieceQtyAvg(int pieceQtyAvg)
    {
        _pieceQtyAvg = pieceQtyAvg;
    }

    /**
     * 数量（バラ）/H(平均)を返します。
     * @return 数量（バラ）/H(平均)を返します。
     */
    public double getPieceQtyPerHourAvg()
    {
        return _pieceQtyPerHourAvg;
    }

    /**
     * 数量（バラ）/H(平均)を設定します。
     * @param pieceQtyPerHourAvg 数量（バラ）/H(平均)
     */
    public void setPieceQtyPerHourAvg(double pieceQtyPerHourAvg)
    {
        _pieceQtyPerHourAvg = pieceQtyPerHourAvg;
    }

    /**
     * 箱/オーダー(平均)を返します。
     * @return 箱/オーダー(平均)を返します。
     */
    public double getBoxCntPerOrderAvg()
    {
        return _boxCntPerOrderAvg;
    }

    /**
     * 箱/オーダー(平均)を設定します。
     * @param boxPerOrderAvg 箱/オーダー(平均)
     */
    public void setBoxCntPerOrderAvg(double boxPerOrderAvg)
    {
        _boxCntPerOrderAvg = boxPerOrderAvg;
    }

    /**
     * 行/オーダー(平均)を返します。
     * @return 行/オーダー(平均)を返します。
     */
    public double getLineCntPerOrderAvg()
    {
        return _lineCntPerOrderAvg;
    }

    /**
     * 行/オーダー(平均)を設定します。
     * @param linePerOrderAvg 行/オーダー(平均)
     */
    public void setLineCntPerOrderAvg(double linePerOrderAvg)
    {
        _lineCntPerOrderAvg = linePerOrderAvg;
    }

    /**
     * ロット/行(平均)を返します。
     * @return ロット/行(平均)を返します。
     */
    public double getLotQtyPerLineAvg()
    {
        return _lotQtyPerLineAvg;
    }

    /**
     * ロット/行(平均)を設定します。
     * @param lotQtyPerLineAvg ロット/行(平均)
     */
    public void setLotQtyPerLineAvg(double lotQtyPerLineAvg)
    {
        _lotQtyPerLineAvg = lotQtyPerLineAvg;
    }

    /**
     * バラ/行(平均)を返します。
     * @return バラ/行(平均)を返します。
     */
    public double getPieceQtyPerLineAvg()
    {
        return _pieceQtyPerLineAvg;
    }

    /**
     * バラ/行(平均)を設定します。
     * @param pieceQtyPerLineAvg バラ/行(平均)
     */
    public void setPieceQtyPerLineAvg(double pieceQtyPerLineAvg)
    {
        _pieceQtyPerLineAvg = pieceQtyPerLineAvg;
    }

    /**
     * ミス率(平均)を返します。
     * @return ミス率(平均)を返します。
     */
    public int getMissRateAvg()
    {
        return _missRateAvg;
    }

    /**
     * ミス率(平均)を設定します。
     * @param missRateAvg ミス率(平均)
     */
    public void setMissRateAvg(int missRateAvg)
    {
        _missRateAvg = missRateAvg;
    }

    /**
     * 予定日を設定します。
     * @param planDay 予定日
     */
    public void setPlanDay(String planDay)
    {
        _planDay = planDay;
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
     * JANコードを返します。
     * @return JANコードを返します。
     */
    public String getJan()
    {
        return _jan;
    }

    /**
     * JANコードを設定します。
     * @param jan JANコード
     */
    public void setJan(String jan)
    {
        _jan = jan;
    }

    /**
     * オーダー分割No.を返します。
     * @return オーダー分割No.を返します。
     */
    public int getOrderSeqNo()
    {
        return _orderSeqNo;
    }

    /**
     * オーダー分割No.を設定します。
     * @param orderSeqNo オーダー分割No.
     * 
     */
    public void setOrderSeqNo(int orderSeqNo)
    {
        _orderSeqNo = orderSeqNo;
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
     * ロット入数を返します。
     * @return ロット入数を返します。
     */
    public int getLotEnteringQty()
    {
        return _lotEnteringQty;
    }

    /**
     * ロット入数を設定します。
     * @param lotEnteringQty ロット入数
     */
    public void setLotEnteringQty(int lotEnteringQty)
    {
        _lotEnteringQty = lotEnteringQty;
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
     * 予定ゾーンを返します。
     * @return 予定ゾーンを返します。
     */
    public String getPlanZoneNo()
    {
        return _planZoneNo;
    }

    /**
     * 予定ゾーンを設定します。
     * @param planZoneNo 予定ゾーン
     */
    public void setPlanZoneNo(String planZoneNo)
    {
        _planZoneNo = planZoneNo;
    }

    /**
     * 予定棚を返します。
     * @return 予定棚を返します。
     */
    public String getPlanLocationNo()
    {
        return _planLocationNo;
    }

    /**
     * 予定棚を設定します。
     * @param planLocationNo 予定棚
     */
    public void setPlanLocationNo(String planLocationNo)
    {
        _planLocationNo = planLocationNo;
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
     * 行数(バッチNo数)を返します。
     * @return 行数(バッチNo数)を返します。
     */
    public int getLine()
    {
        return _line;
    }

    /**
     * 行数(バッチNo数)を設定します。
     * @param line 行数(バッチNo数)
     */
    public void setLine(int line)
    {
        _line = line;
    }

    /**
     * 最終更新日時(最近)を返します。
     * @return 最終更新日時(最近)を返します。
     */
    public java.util.Date getUpdate()
    {
        return _update;
    }

    /**
     * 最終更新日時(最近)を設定します。
     * @param update 最終更新日時(最近)
     */
    public void setUpdate(java.util.Date update)
    {
        _update = update;
    }


    /**
     * 最終更新日時(リスト)を返します。
     * @return 最終更新日時(リスト)を返します。
     */
    public java.util.Date[] getLastUpdateDateList()
    {
        return _lastUpdateDateList;
    }

    /**
     * 最終更新日時(リスト)を設定します。
     * @param lastUpdateDateList  最終更新日時(リスト)
     */
    public void setLastUpdateDateList(java.util.Date[] lastUpdateDateList)
    {
        _lastUpdateDateList = lastUpdateDateList;
    }

    /**
     * 予定一意キー(リスト)を返します。
     * @return 予定一意キー(リスト)を返します。
     */
    public String[] getUkeyList()
    {
        return _ukeyList;
    }

    /**
     * 予定一意キーリスト)を設定します。
     * @param ukeyList  予定一意キー(リスト)
     */
    public void setUkeyList(String[] ukeyList)
    {
        _ukeyList = ukeyList;
    }

    /**
     * バッチ状態フラグを返します。
     * @return バッチ状態フラグを返します。
     */
    public String getBatchStatusFlag()
    {
        return _batchStatusFlag;
    }

    /**
     * バッチ状態フラグを設定します。
     * @param batchStatusFlag バッチ状態フラグ
     */
    public void setBatchStatusFlag(String batchStatusFlag)
    {
        _batchStatusFlag = batchStatusFlag;
    }

    /**
     * Rft号機No.を返します。
     * @return Rft号機No.を返します。
     */
    public String getRftNo()
    {
        return _rftNo;
    }

    /**
     * Rft号機No.を設定します。
     * @param rftNo Rft号機No.
     */
    public void setRftNo(String rftNo)
    {
        _rftNo = rftNo;
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
     * @param planUkey  予定一意キー
     */
    public void setPlanUkey(String planUkey)
    {
        _planUkey = planUkey;
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
     * 予定行数を返します。
     * @return 予定行数を返します。
     */
    public long getPlanLineCnt()
    {
        return _planLineCnt;
    }

    /**
     * 予定行数を設定します。
     * @param planLineCnt 予定行数
     */
    public void setPlanLineCnt(long planLineCnt)
    {
        _planLineCnt = planLineCnt;
    }

    /**
     * 実績行数を返します。
     * @return 実績行数を返します。
     */
    public long getResultLineCnt()
    {
        return _resultLineCnt;
    }

    /**
     * 実績行数を設定します。
     * @param resultLineCnt 実績行数
     */
    public void setResultLineCnt(long resultLineCnt)
    {
        _resultLineCnt = resultLineCnt;
    }

    /**
     * 予定ロット数を返します。
     * @return 予定ロット数を返します。
     */
    public long getPlanLotCnt()
    {
        return _planLotCnt;
    }

    /**
     * 予定ロット数を設定します。
     * @param planLotCnt 予定ロット数
     */
    public void setPlanLotCnt(long planLotCnt)
    {
        _planLotCnt = planLotCnt;
    }

    /**
     * 実績ロット数を返します。
     * @return 実績ロット数を返します。
     */
    public long getResultLotCnt()
    {
        return _resultLotCnt;
    }

    /**
     * 実績ロット数を設定します。
     * @param resultLotCnt 実績ロット数
     */
    public void setResultLotCnt(long resultLotCnt)
    {
        _resultLotCnt = resultLotCnt;
    }

    /**
     * 終了予測時間を返します。
     * @return 終了予測時間を返します。
     */
    public double getEndPlanTime()
    {
        return _endPlanTime;
    }

    /**
     * 終了予測時間を設定します。
     * @param endPlanTime 終了予測時間
     */
    public void setEndPlanTime(double endPlanTime)
    {
        _endPlanTime = endPlanTime;
    }


    /**
     * カート台数を返します。
     * @return カート台数を返します。
     */
    public long getCartCnt()
    {
        return _cartCnt;
    }

    /**
     * カート台数を設定します。
     * @param cartCnt カート台数
     */
    public void setCartCnt(long cartCnt)
    {
        _cartCnt = cartCnt;
    }


    /**
     * 最終更新日時を返します。
     * @return 最終更新日時を返します。
     */
    public String getLastUpdateDate()
    {
        return _lastUpdateDate;
    }

    /**
     * 最終更新日時を設定します。
     * @param lastUpdateDate  最終更新日時
     */
    public void setLastUpdateDate(String lastUpdateDate)
    {
        _lastUpdateDate = lastUpdateDate;
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
     * dayOfWeekを返します。
     * @return dayOfWeekを返します。
     */
    public String getDayOfWeek()
    {
        return _dayOfWeek;
    }

    /**
     * dayOfWeekを設定します。
     * @param dayOfWeek dayOfWeek
     */
    public void setDayOfWeek(String dayOfWeek)
    {
        _dayOfWeek = dayOfWeek;
    }

    /**
     * workDayFromを返します。
     * @return workDayFromを返します。
     */
    public String getWorkDayFrom()
    {
        return _workDayFrom;
    }

    /**
     * workDayFromを設定します。
     * @param workDayFrom workDayFrom
     */
    public void setWorkDayFrom(String workDayFrom)
    {
        _workDayFrom = workDayFrom;
    }

    /**
     * workDaytoを返します。
     * @return workDaytoを返します。
     */
    public String getWorkDayto()
    {
        return _workDayto;
    }

    /**
     * workDaytoを設定します。
     * @param workDayto workDayto
     */
    public void setWorkDayto(String workDayto)
    {
        _workDayto = workDayto;
    }

    /**
     * searchAreaを返します。
     * @return searchAreaを返します。
     */
    public String getSearchArea()
    {
        return _searchArea;
    }

    /**
     * searchAreaを設定します。
     * @param searchArea searchArea
     */
    public void setSearchArea(String searchArea)
    {
        _searchArea = searchArea;
    }

    /**
     * searchConsignorCodeを返します。
     * @return searchConsignorCodeを返します。
     */
    public String getSearchConsignorCode()
    {
        return _searchConsignorCode;
    }

    /**
     * searchConsignorCodeを設定します。
     * @param searchConsignorCode searchConsignorCode
     */
    public void setSearchConsignorCode(String searchConsignorCode)
    {
        _searchConsignorCode = searchConsignorCode;
    }

    /**
     * バッチSeqNoを返します。
     * @return バッチSeqNoを返します。
     */
    public String getBatchSeqNo()
    {
        return _batchSeqNo;
    }

    /**
     * バッチSeqNoを設定します。
     * @param batchSeqNo バッチSeqNo
     */
    public void setBatchSeqNo(String batchSeqNo)
    {
        _batchSeqNo = batchSeqNo;
    }

    /**
     * 予定オーダーNo.を返します。
     * @return 予定オーダーNo.を返します。
     */
    public String getPlanOrderNo()
    {
        return _planOrderNo;
    }

    /**
     * 予定オーダーNo.を設定します。
     * @param planOrderNo 予定オーダーNo.
     */
    public void setPlanOrderNo(String planOrderNo)
    {
        _planOrderNo = planOrderNo;
    }

    /**
     * 実績オーダーNo.を返します。
     * @return 実績オーダーNo.を返します。
     */
    public String getResultOrderNo()
    {
        return _resultOrderNo;
    }

    /**
     * 実績オーダーNo.を設定します。
     * @param resultOrderNo 実績オーダーNo.
     */
    public void setResultOrderNo(String resultOrderNo)
    {
        _resultOrderNo = resultOrderNo;
    }

    /**
     * オーダーSeqNo.を返します。
     * @return オーダーSeqNo.を返します。
     */
    public String getOrderSeq()
    {
        return _OrderSeq;
    }

    /**
     * オーダーSeqNo.を設定します。
     * @param orderSeq オーダーSeqNo.
     */
    public void setOrderSeq(String orderSeq)
    {
        _OrderSeq = orderSeq;
    }

    /**
     * 通番を返します。
     * @return 通番を返します。
     */
    public int getThroughNo()
    {
        return _throughNo;
    }

    /**
     * 通番を設定します。
     * @param throughNo 通番
     */
    public void setThroughNo(int throughNo)
    {
        _throughNo = throughNo;
    }

    /**
     * オーダー内商品数を返します。
     * @return オーダー内商品数を返します。
     */
    public int getOrderItemQty()
    {
        return _orderItemQty;
    }

    /**
     * オーダー内商品数を設定します。
     * @param orderItemQty オーダー内商品数
     */
    public void setOrderItemQty(int orderItemQty)
    {
        _orderItemQty = orderItemQty;
    }

    /**
     * オーダー通番を返します。
     * @return オーダー通番を返します。
     */
    public int getOrderThroughNo()
    {
        return _orderThroughNo;
    }

    /**
     * オーダー通番を設定します。
     * @param orderThroughNo オーダー通番
     */
    public void setOrderThroughNo(int orderThroughNo)
    {
        _orderThroughNo = orderThroughNo;
    }

    /**
     * オーダー通番合計を返します。
     * @return オーダー通番合計を返します。
     */
    public int getOrderThroughNoCnt()
    {
        return _orderThroughNoCnt;
    }

    /**
     * オーダー通番合計を設定します。
     * @param orderThroughNoCnt オーダー通番合計
     */
    public void setOrderThroughNoCnt(int orderThroughNoCnt)
    {
        _orderThroughNoCnt = orderThroughNoCnt;
    }

    /**
     * 汎用フラグを返します。
     * @return 汎用フラグを返します。
     */
    public String getGeneralFlag()
    {
        return _generalFlag;
    }

    /**
     * 汎用フラグを設定します。
     * @param generalFlag 汎用フラグ
     */
    public void setGeneralFlag(String generalFlag)
    {
        _generalFlag = generalFlag;
    }

    /**
     * シュートNoを返します。
     * @return シュートNoを返します。
     */
    public String getShootNo()
    {
        return _shootNo;
    }

    /**
     * シュートNoを設定します。
     * @param shootNo シュートNo
     */
    public void setShootNo(String shootNo)
    {
        _shootNo = shootNo;
    }

    /**
     * 作業ゾーンを返します。
     * @return 作業ゾーンを返します。
     */
    public String getWorkZoneNo()
    {
        return _workZoneNo;
    }

    /**
     * 作業ゾーンを設定します。
     * @param workZoneNo 作業ゾーン
     */
    public void setWorkZoneNo(String workZoneNo)
    {
        _workZoneNo = workZoneNo;
    }

    /**
     * 開始棚を返します。
     * @return 開始棚を返します。
     */
    public String getStartLocationNo()
    {
        return _startLocationNo;
    }

    /**
     * 開始棚を設定します。
     * @param startLocationNo 開始棚
     */
    public void setStartLocationNo(String startLocationNo)
    {
        _startLocationNo = startLocationNo;
    }

    /**
     * 終了棚を返します。
     * @return 終了棚を返します。
     */
    public String getEndLocationNo()
    {
        return _endLocationNo;
    }

    /**
     * 終了棚を設定します。
     * @param endLocationNo 終了棚
     */
    public void setEndLocationNo(String endLocationNo)
    {
        _endLocationNo = endLocationNo;
    }

    /**
     * roleを返します。
     * @return ロールを返します。
     */
    public String getRole()
    {
        return _role;
    }

    /**
     * roleを設定します。
     * @param role ロール
     */
    public void setRole(String role)
    {
        _role = role;
    }

    /**
     * roleNameを返します。
     * @return ロール名を返します。
     */
    public String getRoleName()
    {
        return _roleName;
    }

    /**
     * roleNameを設定します。
     * @param roleName ロール名
     */
    public void setRoleName(String roleName)
    {
        _roleName = roleName;
    }

    /**
     * ランクA基準値を返します。
     * @return ランクA基準値を返します。
     */
    public int getRankA()
    {
        return _rankA;
    }

    /**
     * ランクA基準値を設定します。
     * @param rankA ランクA基準値
     */
    public void setRankA(int rankA)
    {
        _rankA = rankA;
    }

    /**
     * ランクB基準値を返します。
     * @return ランクB基準値を返します。
     */
    public int getRankB()
    {
        return _rankB;
    }

    /**
     * ランクB基準値を設定します。
     * @param rankB ランクB基準値
     */
    public void setRankB(int rankB)
    {
        _rankB = rankB;
    }

    /**
     * 総行数を返します。
     * @return 総行数を返します。
     */
    public long getTotalLine()
    {
        return _totalLine;
    }

    /**
     * 総行数を設定します。
     * @param totalLine 総行数
     */
    public void setTotalLine(long totalLine)
    {
        _totalLine = totalLine;
    }

    /**
     * 総ロット数を返します。
     * @return 総ロット数を返します。
     */
    public long getTotalLot()
    {
        return _totalLot;
    }

    /**
     * 総ロットを設定します。
     * @param totalLot 総ロット
     */
    public void setTotalLot(long totalLot)
    {
        _totalLot = totalLot;
    }

    /**
     * 総オーダー数を返します。
     * @return 総オーダー数を返します。
     */
    public long getTotalOrder()
    {
        return _totalOrder;
    }

    /**
     * 総オーダー数を設定します。
     * @param totalOrder 総オーダー数
     */
    public void setTotalOrder(long totalOrder)
    {
        _totalOrder = totalOrder;
    }

    /**
     * 月別生産率（行）を返します。
     * @return 月別生産率（行）を返します。
     */
    public double getMonthProductRateLine()
    {
        return _monthProductRateLine;
    }

    /**
     * 月別生産率（行）を設定します。
     * @param monthProductRateLine 月別生産率（行）
     */
    public void setMonthProductRateLine(double monthProductRateLine)
    {
        _monthProductRateLine = monthProductRateLine;
    }

    /**
     * 月別生産率（ロット）を返します。
     * @return 月別生産率（ロット）を返します。
     */
    public double getMonthProductRateLot()
    {
        return _monthProductRateLot;
    }

    /**
     * 月別生産率（ロット）を設定します。
     * @param monthProductRateLot 月別生産率（ロット）
     */
    public void setMonthProductRateLot(double monthProductRateLot)
    {
        _monthProductRateLot = monthProductRateLot;
    }

    /**
     * 月別生産率（オーダー）を返します。
     * @return 月別生産率（オーダー）を返します。
     */
    public double getMonthProductRateOrder()
    {
        return _monthProductRateOrder;
    }

    /**
     * 月別生産率（オーダー）を設定します。
     * @param monthProductRateOrder 月別生産率（オーダー）
     */
    public void setMonthProductRateOrder(double monthProductRateOrder)
    {
        _monthProductRateOrder = monthProductRateOrder;
    }

    /**
     * 作業月を返します。
     * @return 作業月を返します。
     */
    public String getWorkMonth()
    {
        return _workMonth;
    }

    /**
     * 作業月を設定します。
     * @param workMonth 作業月
     */
    public void setWorkMonth(String workMonth)
    {
        _workMonth = workMonth;
    }

    /**
     * 生産率（double）を返します。
     * @return 生産率（double）を返します。
     */
    public double getProductionRateVal()
    {
        return _productionRateVal;
    }

    /**
     * 生産率（double）を設定します。
     * @param productionRate 生産率（double）
     */
    public void setProductionRateVal(double productionRate)
    {
        _productionRateVal = productionRate;
    }

    /**
     * 重量未登録商品フラグを返します。
     * @return 重量未登録商品フラグを返します。
     */
    public boolean getWeightUnregist()
    {
        return _weightUnregist;
    }

    /**
     * 重量未登録商品フラグを設定します。
     * @param weightunregist 重量未登録商品フラグ
     */
    public void setWeightUnregist(boolean weightunregist)
    {
        _weightUnregist = weightunregist;
    }

    /**
     * 残オーダー件数を返します。
     * @return 残オーダー件数を返します。
     */
    public int getUnodoneOrderCnt()
    {
        return _undoneOrderCnt;
    }

    /**
     * 残オーダー件数を設定します。
     * @param undoneOrderCnt 残オーダー件数
     */
    public void setUndoneOrderCnt(int undoneOrderCnt)
    {
        _undoneOrderCnt = undoneOrderCnt;
    }

    /**
     * 最小ゾーンを返します。
     * @return 最小ゾーンを返します。
     */
    public String getMinWorkZoneNo()
    {
        return _minWorkZoneNo;
    }

    /**
     * 最小ゾーンを設定します。
     * @param minWorkZoneNo 最小ゾーン
     */
    public void setMinWorkZoneNo(String minWorkZoneNo)
    {
        _minWorkZoneNo = minWorkZoneNo;
    }

    /**
     * 欠品数を返します。
     * @return 欠品数を返します。
     */
    public int getShotageQty()
    {
        return _shotageQty;
    }

    /**
     * 欠品数を設定します。
     * @param shotageQty 欠品数
     */
    public void setShotageQty(int shotageQty)
    {
        _shotageQty = shotageQty;
    }

    /**
     * オーダーNo.採番フラグを返します。
     * @return オーダーNo.採番フラグを返します。
     */
    public boolean getCreateOrder()
    {
        return _createOrder;
    }

    /**
     * オーダーNo.採番フラグを設定します。
     * @param weightunregist オーダーNo.採番フラグ
     */
    public void setCreateOrder(boolean createOrder)
    {
        _createOrder = createOrder;
    }
    
    /**
     * 作業No.リストを取得します。
     * @return 作業No.リスト
     */
    public List<String> getJobNoList()
    {
        return _jobNoList;
    }
    
    /**
     * 作業No.リストを設定します。
     * @param jobNoList 作業No.リスト
     */
    public void setJobNoList(List<String> jobNoList)
    {
        _jobNoList = jobNoList;
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


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
}
//end of class

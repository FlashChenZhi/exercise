package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;

import jp.co.daifuku.wms.base.common.OutParameter;

/**
 * <CODE>SystemOutParameter</CODE>クラスは、システムパッケージ内のスケジュール→画面間のパラメータの受渡しに使用します。<BR>
 * このクラスではシステムパッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>SystemOutParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     報告単位<BR>
 *     データ区分<BR>
 *     パッケージ選択<BR>
 *     取込内容<BR>
 *     作業日<BR>
 *     作業日(to)<BR>
 *     エリアNo.<BR>
 *     エリア名称<BR>
 *     棚<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     ロットNo.<BR>
 *     入庫日<BR>
 *     入庫時刻<BR>
 *     入庫日時<BR>
 *     検索日<BR>
 *     検索日(to)<BR>
 *     検索時刻<BR>
 *     検索時刻(to)<BR>
 *     登録日<BR>
 *     登録時刻<BR>
 *     作業区分<BR>
 *     集約条件<BR>
 *     日付/時間<BR>
 *     内容<BR>
 *     発生クラス<BR>
 *     メッセージ<BR>
 *     作業開始時刻<BR>
 *     作業終了時刻<BR>
 *     作業時間<BR>
 *     実作業時間<BR>
 *     作業数量<BR>
 *     作業回数(明細数)<BR>
 *     作業回数(作業オーダー数)<BR>
 *     RFTNo.<BR>
 *     増減区分<BR>
 *     在庫増減数<BR>
 *     取込ファイル数<BR>
 *     入荷パッケージ有無<BR>
 *     入庫パッケージ有無<BR>
 *     在庫パッケージ有無<BR>
 *     出庫パッケージ有無<BR>
 *     仕分パッケージ有無<BR>
 *     出荷パッケージ有無<BR>
 *     AS/RSパッケージ有無<BR>
 *     マスタパッケージ有無<BR>
 *     分析パッケージ有無<BR>
 *     クロスドックパッケージ有無<BR>    
 *     プリンタ名<BR>
 *     日次更新中フラグ<BR>
 *     予定データ取込中フラグ<BR>
 *     報告データ作成中フラグ<BR>
 *     出庫引当中フラグ<BR>
 *     ホスト通信<BR>
 *     実績保持日数<BR>
 *     予定保持日数<BR>
 *     システム環境<BR>
 *     最終更新日時<BR>
 *     端末区分<BR>
 *     RFT状態<BR>
 *     ファイルパス<BR>
 *     ファイル名称<BR>
 *     端末IPアドレス<BR>
 *     log書き込みフラグ<BR>
 * </DIR>
 * 
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class SystemOutParameter
        extends OutParameter
{
    // Class variables -----------------------------------------------
    /**
     * 日次更新状態 : 警告
     */
    public static final String DAILYUPDATE_STATUS_WARNING = "1";

    /**
     * 日次更新状態: 日次更新不可
     */
    public static final String DAILYUPDATE_STATUS_NG = "2";

    /**
     * 報告単位
     */
    private String _reportType;

    /**
     * データ区分
     */
    private String _dataType;

    /**
     * パッケージ選択
     */
    private boolean _packageSelect;

    /**
     * 取込内容
     */
    private String _loadDataType;

    /**
     * 作業日
     */
    private String _workDay;

    /**
     * 作業日(from)
     */
    private String _fromWorkDay;

    /**
     * 作業日(to)
     */
    private String _toWorkDay;

    /**
     * エリアNo.
     */
    private String _areaNo;

    /**
     * エリア名称
     */
    private String _areaName;

    /**
     * 棚
     */
    private String _location;

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
     * 入庫日
     */
    private String _storageDay;

    /**
     * 入庫時刻
     */
    private String _storageTime;

    /**
     * 入庫日時
     */
    private java.util.Date _storageDate;

    /**
     * 検索日
     */
    private String _searchDay;

    /**
     * 検索日(to)
     */
    private String _toSearchDay;

    /**
     * 検索時刻
     */
    private String _searchTime;

    /**
     * 検索時刻(to)
     */
    private String _toSearchTime;

    /**
     * 登録日
     */
    private String _registDay;

    /**
     * 登録時刻
     */
    private String _registTime;

    /**
     * 作業区分
     */
    private String _jobType;

    /**
     * 集約条件
     */
    private String _collectCondition;

    /**
     * 日付/時間
     */
    private Date _logDate;

    /**
     * 内容
     */
    private String _content;

    /**
     * 発生クラス
     */
    private String _errorClass;

    /**
     * メッセージ
     */
    private String _message;

    /**
     * 作業開始時刻
     */
    private String _workStartTime;

    /**
     * 作業終了時刻
     */
    private String _workEndTime;

    /**
     * 作業時間
     */
    private String _workTime;

    /**
     * 実作業時間
     */
    private String _realWorkTime;

    /**
     * 作業数量
     */
    private long _workQty;

    /**
     * 作業回数(明細数)
     */
    private long _workCnt;

    /**
     * 作業回数(作業オーダー数)
     */
    private long _orderCnt;

    /**
     * RFTNo.
     */
    private String _rftNo;

    /**
     * 増減区分
     */
    private String _incDecType;

    /**
     * 在庫増減数
     */
    private long _incDecQty;

    /**
     * 取込ファイル数
     */
    private int _loadFiles_count;

    /**
     * 入荷パッケージ有無
     */
    private boolean _receivingPackageFlag;

    /**
     * 入庫パッケージ有無
     */
    private boolean _storagePackageFlag;

    /**
     * 在庫パッケージ有無
     */
    private boolean _stockPackageFlag;

    /**
     * 出庫パッケージ有無
     */
    private boolean _retrievalPackageFlag;

    /**
     * 仕分パッケージ有無
     */
    private boolean _sortingPackageFlag;

    /**
     * 出荷パッケージ有無
     */
    private boolean _shippingPackageFlag;

    /**
     * AS/RSパッケージ有無
     */
    private boolean _asrsPackageFlag;

    /**
     * マスタパッケージ有無
     */
    private boolean _masterPackageFlag;

    /**
     * 分析パッケージ有無
     */
    private boolean _analysisPackageFlag;

    /**
     * クロスドックパッケージ有無
     */
    private boolean _crossdockPackageFlag;

    /**
     * プリンタ名
     */
    private String _printerName;

    /**
     * 日次更新中フラグ
     */
    private boolean _dailyUpdateFlag;

    /**
     * 予定データ取込中フラグ
     */
    private boolean _loadDataFlag;

    /**
     * 報告データ作成中フラグ
     */
    private boolean _reportDataFlag;

    /**
     * 出庫引当中フラグ
     */
    private boolean _retrievalAllocate;

    /**
     * ホスト通信フラグ
     */
    private boolean _commStatusFlag;

    /**
     * 実績保持日数
     */
    private int _resultHoldPeriod;

    /**
     * 予定保持日数
     */
    private int _planHoldPeriod;

    /**
     * システム環境
     */
    private String[] _systemEnvironment;

    /**
     * 最終更新日時
     */
    private java.util.Date _lastUpdateDate;

    /**
     * 端末区分
     */
    private String _terminalType;

    /**
     * RFT状態
     */
    private String _rftStatus;

    /**
     * 検索日時
     */
    private java.util.Date _StartSearchDate;

    /**
     * 状態
     */
    private String _status;

    /**
     * NG理由
     */
    private String _ngReason;

    /**
     * 発生箇所
     */
    private String _happeningPoint;

    /**
     * ファイルパス
     */
    private String _filePath;

    /**
     * ファイル名称
     */
    private String _fileName;

    /**
     * 端末IPアドレス
     */
    private String _terminalIPAddress;

    /**
     * log書き込みフラグ
     */
    private boolean _dataFlag;

    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    /**
     * 報告単位を返します。
     * @return 報告単位を返します。
     */
    public String getReportType()
    {
        return _reportType;
    }

    /**
     * 報告単位を設定します。
     * @param reportType 報告単位
     */
    public void setReportType(String reportType)
    {
        _reportType = reportType;
    }

    /**
     * データ区分を返します。
     * @return データ区分を返します。
     */
    public String getDataType()
    {
        return _dataType;
    }

    /**
     * データ区分を設定します。
     * @param dataType データ区分
     */
    public void setDataType(String dataType)
    {
        _dataType = dataType;
    }

    /**
     * パッケージ選択を返します。
     * @return パッケージ選択を返します。
     */
    public boolean isPackageSelect()
    {
        return _packageSelect;
    }

    /**
     * パッケージ選択を設定します。
     * @param packageSelect パッケージ選択
     */
    public void setPackageSelect(boolean packageSelect)
    {
        _packageSelect = packageSelect;
    }

    /**
     * 取込内容を返します。
     * @return 取込内容を返します。
     */
    public String getLoadDataType()
    {
        return _loadDataType;
    }

    /**
     * 取込内容を設定します。
     * @param loadDataType 取込内容
     */
    public void setLoadDataType(String loadDataType)
    {
        _loadDataType = loadDataType;
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
     * 作業日(from)を返します。
     * @return 作業日を返します。
     */
    public String getFromWorkDay()
    {
        return _fromWorkDay;
    }

    /**
     * 作業日(from)を設定します。
     * @param fromWorkDay 作業日(from)
     */
    public void setFromWorkDay(String fromWorkDay)
    {
        _fromWorkDay = fromWorkDay;
    }

    /**
     * 作業日(to)を返します。
     * @return 作業日を返します。
     */
    public String getToWorkDay()
    {
        return _toWorkDay;
    }

    /**
     * 作業日(to)を設定します。
     * @param toWorkDay 作業日(to)
     */
    public void setToWorkDay(String toWorkDay)
    {
        _toWorkDay = toWorkDay;
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
     * 棚を返します。
     * @return 棚を返します。
     */
    public String getLocation()
    {
        return _location;
    }

    /**
     * 棚を設定します。
     * @param location 棚
     */
    public void setLocation(String location)
    {
        _location = location;
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
     * 入庫時刻を返します。
     * @return 入庫時刻を返します。
     */
    public String getStorageTime()
    {
        return _storageTime;
    }

    /**
     * 入庫時刻を設定します。
     * @param storageTime 入庫時刻
     */
    public void setStorageTime(String storageTime)
    {
        _storageTime = storageTime;
    }

    /**
     * 入庫日時を返します。
     * @return 入庫日時を返します。
     */
    public java.util.Date getStorageDate()
    {
        return _storageDate;
    }

    /**
     * 入庫日時を設定します。
     * @param storageDate 入庫日時
     */
    public void setStorageDate(java.util.Date storageDate)
    {
        _storageDate = storageDate;
    }

    /**
     * 検索日を返します。
     * @return 検索日を返します。
     */
    public String getSearchDay()
    {
        return _searchDay;
    }

    /**
     * 検索日を設定します。
     * @param searchDay 検索日
     */
    public void setSearchDay(String searchDay)
    {
        _searchDay = searchDay;
    }

    /**
     * 検索日(to)を返します。
     * @return 検索日(to)を返します。
     */
    public String getToSearchDay()
    {
        return _toSearchDay;
    }

    /**
     * 検索日(to)を設定します。
     * @param toSearchDay 検索日(to)
     */
    public void setToSearchDay(String toSearchDay)
    {
        _toSearchDay = toSearchDay;
    }

    /**
     * 検索時刻を返します。
     * @return 検索時刻を返します。
     */
    public String getSearchTime()
    {
        return _searchTime;
    }

    /**
     * 検索時刻を設定します。
     * @param searchTime 検索時刻
     */
    public void setSearchTime(String searchTime)
    {
        _searchTime = searchTime;
    }

    /**
     * 検索時刻(to)を返します。
     * @return 検索時刻(to)を返します。
     */
    public String getToSearchTime()
    {
        return _toSearchTime;
    }

    /**
     * 検索時刻(to)を設定します。
     * @param toSearchTime 検索時刻(to)
     */
    public void setToSearchTime(String toSearchTime)
    {
        _toSearchTime = toSearchTime;
    }

    /**
     * 登録日を返します。
     * @return 登録日を返します。
     */
    public String getRegistDay()
    {
        return _registDay;
    }

    /**
     * 登録日を設定します。
     * @param registDay 登録日
     */
    public void setRegistDay(String registDay)
    {
        _registDay = registDay;
    }

    /**
     * 登録時刻を返します。
     * @return 登録時刻を返します。
     */
    public String getRegistTime()
    {
        return _registTime;
    }

    /**
     * 登録時刻を設定します。
     * @param registTime 登録時刻
     */
    public void setRegistTime(String registTime)
    {
        _registTime = registTime;
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
     * 作業区分を設定します。
     * @param jobType 作業区分
     */
    public void setJobType(String jobType)
    {
        _jobType = jobType;
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
     * 日付/時間を返します。
     * @return 日付/時間を返します。
     */
    public Date getLogDate()
    {
        return _logDate;
    }

    /**
     * 日付/時間を設定します。
     * @param logDate 日付/時間
     */
    public void setLogDate(Date logDate)
    {
        _logDate = logDate;
    }

    /**
     * 内容を返します。
     * @return 内容を返します。
     */
    public String getContent()
    {
        return _content;
    }

    /**
     * 内容を設定します。
     * @param content 内容
     */
    public void setContent(String content)
    {
        _content = content;
    }

    /**
     * 発生クラスを返します。
     * @return 発生クラスを返します。
     */
    public String getErrorClass()
    {
        return _errorClass;
    }

    /**
     * 発生クラスを設定します。
     * @param errorClass 発生クラス
     */
    public void setErrorClass(String errorClass)
    {
        _errorClass = errorClass;
    }

    /**
     * メッセージを返します。
     * @return メッセージを返します。
     */
    public String getMessage()
    {
        return _message;
    }

    /**
     * メッセージを設定します。
     * @param message メッセージ
     */
    public void setMessage(String message)
    {
        _message = message;
    }

    /**
     * 作業開始時刻を返します。
     * @return 作業開始時刻を返します。
     */
    public String getWorkStartTime()
    {
        return _workStartTime;
    }

    /**
     * 作業開始時刻を設定します。
     * @param workStartTime 作業開始時刻
     */
    public void setWorkStartTime(String workStartTime)
    {
        _workStartTime = workStartTime;
    }

    /**
     * 作業終了時刻を返します。
     * @return 作業終了時刻を返します。
     */
    public String getWorkEndTime()
    {
        return _workEndTime;
    }

    /**
     * 作業終了時刻を設定します。
     * @param workEndTime 作業終了時刻
     */
    public void setWorkEndTime(String workEndTime)
    {
        _workEndTime = workEndTime;
    }

    /**
     * 作業時間を返します。
     * @return 作業時間を返します。
     */
    public String getWorkTime()
    {
        return _workTime;
    }

    /**
     * 作業時間を設定します。
     * @param workTime 作業時間
     */
    public void setWorkTime(String workTime)
    {
        _workTime = workTime;
    }

    /**
     * 実作業時間を返します。
     * @return 実作業時間を返します。
     */
    public String getRealWorkTime()
    {
        return _realWorkTime;
    }

    /**
     * 実作業時間を設定します。
     * @param realWorkTime 実作業時間
     */
    public void setRealWorkTime(String realWorkTime)
    {
        _realWorkTime = realWorkTime;
    }

    /**
     * 作業数量を返します。
     * @return 作業数量を返します。
     */
    public long getWorkQty()
    {
        return _workQty;
    }

    /**
     * 作業数量を設定します。
     * @param workQty 作業数量
     */
    public void setWorkQty(long workQty)
    {
        _workQty = workQty;
    }

    /**
     * 作業回数(明細数)を返します。
     * @return 作業回数(明細数)を返します。
     */
    public long getWorkCnt()
    {
        return _workCnt;
    }

    /**
     * 作業回数(明細数)を設定します。
     * @param workCnt 作業回数(明細数)
     */
    public void setWorkCnt(long workCnt)
    {
        _workCnt = workCnt;
    }

    /**
     * 作業回数(作業オーダー数)を返します。
     * @return 作業回数(作業オーダー数)を返します。
     */
    public long getOrderCnt()
    {
        return _orderCnt;
    }

    /**
     * 作業回数(作業オーダー数)を設定します。
     * @param orderCnt 作業回数(作業オーダー数)
     */
    public void setOrderCnt(long orderCnt)
    {
        _orderCnt = orderCnt;
    }

    /**
     * RFTNo.を返します。
     * @return RFTNo.を返します。
     */
    public String getRftNo()
    {
        return _rftNo;
    }

    /**
     * RFTNo.を設定します。
     * @param rftNo RFTNo.
     */
    public void setRftNo(String rftNo)
    {
        _rftNo = rftNo;
    }

    /**
     * 増減区分を返します。
     * @return 増減区分を返します。
     */
    public String getIncDecType()
    {
        return _incDecType;
    }

    /**
     * 増減区分を設定します。
     * @param incDecType 増減区分
     */
    public void setIncDecType(String incDecType)
    {
        _incDecType = incDecType;
    }

    /**
     * 在庫増減数を返します。
     * @return 在庫増減数を返します。
     */
    public long getIncDecQty()
    {
        return _incDecQty;
    }

    /**
     * 在庫増減数を設定します。
     * @param incDecQty 在庫増減数
     */
    public void setIncDecQty(long incDecQty)
    {
        _incDecQty = incDecQty;
    }


    /**
     * 取込ファイル数を返します。
     * @return 取込ファイル数を返します。
     */
    public int getLoadFilesCount()
    {
        return _loadFiles_count;
    }

    /**
     * 取込ファイル数を設定します。
     * @param loadFilesCount 取込ファイル数
     */
    public void setLoadFilesCount(int loadFilesCount)
    {
        _loadFiles_count = loadFilesCount;
    }

    /**
     * 入荷パッケージ有無を返します。
     * @return 入荷パッケージ有無を返します。
     */
    public boolean isReceivingPackageFlag()
    {
        return _receivingPackageFlag;
    }

    /**
     * 入庫パッケージ有無を返します。
     * @return 入庫パッケージ有無を返します。
     */
    public boolean isStoragePackageFlag()
    {
        return _storagePackageFlag;
    }

    /**
     * 在庫パッケージ有無を返します。
     * @return 在庫パッケージ有無を返します。
     */
    public boolean isStockPackageFlag()
    {
        return _stockPackageFlag;
    }

    /**
     * 出庫パッケージ有無を返します。
     * @return 出庫パッケージ有無を返します。
     */
    public boolean isRetrievalPackageFlag()
    {
        return _retrievalPackageFlag;
    }

    /**
     * 仕分パッケージ有無を返します。
     * @return 仕分パッケージ有無を返します。
     */
    public boolean isSortingPackageFlag()
    {
        return _sortingPackageFlag;
    }

    /**
     * 出荷パッケージ有無を返します。
     * @return 出荷パッケージ有無を返します。
     */
    public boolean isShippingPackageFlag()
    {
        return _shippingPackageFlag;
    }

    /**
     * AS/RSパッケージ有無を返します。
     * @return AS/RSパッケージ有無を返します。
     */
    public boolean isAsrsPackageFlag()
    {
        return _asrsPackageFlag;
    }

    /**
     * マスタパッケージ有無を返します。
     * @return マスタパッケージ有無を返します。
     */
    public boolean isMasterPackageFlag()
    {
        return _masterPackageFlag;
    }

    /**
     * 分析パッケージ有無を返します。
     * @return 分析パッケージ有無を返します。
     */
    public boolean isAnalysisPackageFlag()
    {
        return _analysisPackageFlag;
    }

    /**
     * クロスドックパッケージ有無を返します。
     * @return クロスドックパッケージ有無を返します。
     */
    public boolean isCrossdockPackageFlag()
    {
        return _crossdockPackageFlag;
    }

    /**
     * 入荷パッケージ有無を設定します。
     * @param receivingPackageFlag 入荷パッケージ有無
     */
    public void setReceivingPackageFlag(boolean receivingPackageFlag)
    {
        _receivingPackageFlag = receivingPackageFlag;
    }

    /**
     * 入庫パッケージ有無を設定します。
     * @param storagePackageFlag 入庫パッケージ有無
     */
    public void setStoragePackageFlag(boolean storagePackageFlag)
    {
        _storagePackageFlag = storagePackageFlag;
    }

    /**
     * 在庫パッケージ有無を設定します。
     * @param stockPackageFlag 在庫パッケージ有無
     */
    public void setStockPackageFlag(boolean stockPackageFlag)
    {
        _stockPackageFlag = stockPackageFlag;
    }

    /**
     * 出庫パッケージ有無を設定します。
     * @param retrievalPackageFlag 出庫パッケージ有無
     */
    public void setRetrievalPackageFlag(boolean retrievalPackageFlag)
    {
        _retrievalPackageFlag = retrievalPackageFlag;
    }

    /**
     * 仕分パッケージ有無を設定します。
     * @param sortingPackageFlag 仕分パッケージ有無
     */
    public void setSortingPackageFlag(boolean sortingPackageFlag)
    {
        _sortingPackageFlag = sortingPackageFlag;
    }

    /**
     * 出荷パッケージ有無を設定します。
     * @param shippingPackageFlag 出荷パッケージ有無
     */
    public void setShippingPackageFlag(boolean shippingPackageFlag)
    {
        _shippingPackageFlag = shippingPackageFlag;
    }

    /**
     * AS/RSパッケージ有無を設定します。
     * @param asrsPackageFlag AS/RSパッケージ有無
     */
    public void setAsrsPackageFlag(boolean asrsPackageFlag)
    {
        _asrsPackageFlag = asrsPackageFlag;
    }

    /**
     * マスタパッケージ有無を設定します。
     * @param masterPackageFlag マスタパッケージ有無
     */
    public void setMasterPackageFlag(boolean masterPackageFlag)
    {
        _masterPackageFlag = masterPackageFlag;
    }

    /**
     * 分析パッケージ有無を設定します。
     * @param analysisPackageFlag 分析パッケージ有無
     */
    public void setAnalysisPackageFlag(boolean analysisPackageFlag)
    {
        _analysisPackageFlag = analysisPackageFlag;
    }

    /**
     * クロスドックパッケージ有無を設定します。
     * @param crossdockPackageFlag クロスドックパッケージ有無
     */
    public void setCrossdockPackageFlag(boolean crossdockPackageFlag)
    {
        _crossdockPackageFlag = crossdockPackageFlag;
    }

    /**
     * プリンタ名を返します。
     * @return プリンタ名を返します。
     */
    public String getPrinterName()
    {
        return _printerName;
    }

    /**
     * プリンタ名を設定します。
     * @param printerName プリンタ名
     */
    public void setPrinterName(String printerName)
    {
        _printerName = printerName;
    }

    /**
     * 日次更新中フラグを返します。
     * @return 日次更新中フラグを返します。
     */
    public boolean isDailyUpdateFlag()
    {
        return _dailyUpdateFlag;
    }

    /**
     * 日次更新中フラグを設定します。
     * @param dailyUpdateFlag 日次更新中フラグ
     */
    public void setDailyUpdateFlag(boolean dailyUpdateFlag)
    {
        _dailyUpdateFlag = dailyUpdateFlag;
    }

    /**
     * 予定データ取込中フラグを返します。
     * @return 予定データ取込中フラグを返します。
     */
    public boolean isLoadDataFlag()
    {
        return _loadDataFlag;
    }

    /**
     * 予定データ取込中フラグを設定します。
     * @param loadDataFlag 予定データ取込中フラグ
     */
    public void setLoadDataFlag(boolean loadDataFlag)
    {
        _loadDataFlag = loadDataFlag;
    }

    /**
     * 報告データ作成中フラグを返します。
     * @return 報告データ作成中フラグを返します。
     */
    public boolean isReportDataFlag()
    {
        return _reportDataFlag;
    }

    /**
     * 報告データ作成中フラグを設定します。
     * @param reportDataFlag 報告データ作成中フラグ
     */
    public void setReportDataFlag(boolean reportDataFlag)
    {
        _reportDataFlag = reportDataFlag;
    }

    /**
     * 出庫引当中フラグを返します。
     * @return 出庫引当中フラグを返します。
     */
    public boolean isRetrievalAllocate()
    {
        return _retrievalAllocate;
    }

    /**
     * 出庫引当中フラグを設定します。
     * @param retrievalAllocate 出庫引当中フラグ
     */
    public void setRetrievalAllocate(boolean retrievalAllocate)
    {
        _retrievalAllocate = retrievalAllocate;
    }

    /**
     * ホスト通信フラグを返します。
     * @return ホスト通信フラグを返します。
     */
    public boolean isCommStatusFlag()
    {
        return _commStatusFlag;
    }

    /**
     * ホスト通信フラグを設定します。
     * @param commStatusFlag ホスト通信フラグ
     */
    public void setCommStatusFlag(boolean commStatusFlag)
    {
        _commStatusFlag = commStatusFlag;
    }

    /**
     * 実績保持日数を返します。
     * @return 実績保持日数を返します。
     */
    public int getResultHoldPeriod()
    {
        return _resultHoldPeriod;
    }

    /**
     * 実績保持日数を設定します。
     * @param resultHoldPeriod 実績保持日数
     */
    public void setResultHoldPeriod(int resultHoldPeriod)
    {
        _resultHoldPeriod = resultHoldPeriod;
    }

    /**
     * 予定保持日数を返します。
     * @return 予定保持日数を返します。
     */
    public int getPlanHoldPeriod()
    {
        return _planHoldPeriod;
    }

    /**
     * 予定保持日数を設定します。
     * @param planHoldPeriod 予定保持日数
     */
    public void setPlanHoldPeriod(int planHoldPeriod)
    {
        _planHoldPeriod = planHoldPeriod;
    }

    /**
     * システム環境を返します。
     * @return システム環境を返します。
     */
    public String[] getSystemEnvironment()
    {
        return _systemEnvironment;
    }

    /**
     * システム環境を設定します。
     * @param systemEnvironment システム環境
     */
    public void setSystemEnvironment(String[] systemEnvironment)
    {
        _systemEnvironment = systemEnvironment;
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
     * 端末区分を返します。
     * @return 端末区分を返します。
     */
    public String getTerminalType()
    {
        return _terminalType;
    }

    /**
     * 端末区分を設定します。
     * @param terminalType 端末区分
     */
    public void setTerminalType(String terminalType)
    {
        _terminalType = terminalType;
    }

    /**
     * RFT状態を返します。
     * @return RFT状態を返します。
     */
    public String getRftStatus()
    {
        return _rftStatus;
    }

    /**
     * RFT状態を設定します。
     * @param rftStatus RFT状態
     */
    public void setRftStatus(String rftStatus)
    {
        _rftStatus = rftStatus;
    }

    /**
     * 開始日時を返します。
     * @return 開始日時を返します。
     */
    public java.util.Date getStartSearchDate()
    {
        return _StartSearchDate;
    }

    /**
     * 開始日時を設定します。
     * @param startSearchDate 開始日時
     */
    public void setStartSearchDate(java.util.Date startSearchDate)
    {
        _StartSearchDate = startSearchDate;
    }

    /**
     * 状態を返します。
     * @return 状態を返します。
     */
    public String getStatus()
    {
        return _status;
    }

    /**
     * 状態を設定します。
     * @param status 状態
     */
    public void setStatus(String status)
    {
        _status = status;
    }

    /**
     * NG理由を返します。
     * @return 状態を返します。
     */
    public String getNgReason()
    {
        return _ngReason;
    }

    /**
     * NG理由を設定します。
     * @param ngReason NG理由
     */
    public void setNgReason(String ngReason)
    {
        _ngReason = ngReason;
    }

    /**
     * 発生箇所を返します。
     * @return 発生箇所を返します。
     */
    public String getHappeningPoint()
    {
        return _happeningPoint;
    }

    /**
     * 発生箇所を設定します。
     * @param happeningPoint 発生箇所
     */
    public void setHappeningPoint(String happeningPoint)
    {
        _happeningPoint = happeningPoint;
    }

    /**
     * ファイル名称を返します。
     * @return ファイル名称を返します。
     */
    public String getFileName()
    {
        return _fileName;
    }

    /**
     * ファイル名称を設定します。
     * @param fileName ファイル名称
     */
    public void setFileName(String fileName)
    {
        _fileName = fileName;
    }

    /**
     * ファイルパスを返します。
     * @return ファイルパスを返します。
     */
    public String getFilePath()
    {
        return _filePath;
    }

    /**
     * ファイルパスを設定します。
     * @param filePath ファイルパス
     */
    public void setFilePath(String filePath)
    {
        _filePath = filePath;
    }

    /**
     * 端末IPアドレスを返します。
     * @return 端末IPアドレスを返します。
     */
    public String getTerminalIPAddress()
    {
        return _terminalIPAddress;
    }

    /**
     * 端末IPアドレスを設定します。
     * @param terminalIPAddress 端末IPアドレス
     */
    public void setTerminalIPAddress(String terminalIPAddress)
    {
        _terminalIPAddress = terminalIPAddress;
    }

    /**
     * log書き込みフラグを返します。
     * @return log書き込みフラグを返します。
     */
    public boolean isDataFlag()
    {
        return _dataFlag;
    }

    /**
     * log書き込みフラグを設定します。
     * @param flag log書き込みフラグ
     */
    public void setDataFlag(boolean flag)
    {
        _dataFlag = flag;
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;

/**
 * <CODE>SystemInParameter</CODE>クラスは、システムパッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスではシステムパッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>SystemInParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     報告単位<BR>
 *     データ区分<BR>
 *     作業日<BR>
 *     エリアNo.<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     ロットNo.<BR>
 *     検索日時<BR>
 *     検索日時(to)<BR>
 *     検索日<BR>
 *     検索日(to)<BR>
 *     検索時刻<BR>
 *     検索時刻(to)<BR>
 *     登録時刻<BR>
 *     登録時刻(to)<BR>
 *     表示開始日<BR>
 *     表示開始時間<BR>
 *     表示終了日<BR>
 *     表示終了時間<BR>
 *     表示終了日時<Br>
 *     取込開始日時<BR>
 *     作業区分<BR>
 *     プリンタ名<BR>
 *     集約条件<BR>
 *     表示条件<BR>
 *     荷主コード<BR>
 *     開始終了フラグ<BR>
 *     途中作業区分<BR>
 *     不足数量区分<BR>
 *     範囲指定フラグ<BR>
 *     開始終了フラグ(入庫日)<BR>
 *     ファイルパス<BR>
 *     ファイル名称<BR>
 *     行No.<BR>
 *     パッケージ選択<BR>
 * </DIR>
 * 
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  $Author: arai $
 */
public class SystemInParameter
        extends InParameter
{
    // Class variables -----------------------------------------------
    /**
     * ファイルパス最大文字数
     */
    public static final int FILE_PATH_MAXLENGTH = 255;

    /**
     * 途中作業：キャンセル
     */
    public static final String RFT_WORK_CANSEL = "1";

    /**
     * 途中作業：確定
     */
    public static final String RFT_WORK_COMFIRM = "2";

    /**
     * 表示条件：全て
     */
    public static final String DISPLAY_CONDITION_ALL = "0";

    /**
     * 表示条件 : 案内
     */
    public static final String DISPLAY_CONDITION_INFORMATION = "1";

    /**
     * 表示条件 : 注意
     */
    public static final String DISPLAY_CONDITION_ATTENTION = "2";

    /**
     * 表示条件 : 警告
     */
    public static final String DISPLAY_CONDITION_WARNING = "3";

    /**
     * 表示条件 : 異常
     */
    public static final String DISPLAY_CONDITION_ERROR = "4";

    /**
     * 未作業情報：削除する
     */
    public static final String UNSTART_DELETE = "0";

    /**
     * 未作業情報：作業情報を持ち越す。
     */
    public static final String UNSTART_HOLD = "1";

    /**
     * 報告単位区分:予定単位(集約)
     */
    public static final String REPORT_PLANCOLLECT = "0";

    /**
     * 報告単位区分:予定単位(明細)
     */
    public static final String REPORT_PLAN = "1";

    /**
     * 報告単位区分:作業単位
     */
    public static final String REPORT_WORK = "2";

    /**
     * 報告単位
     */
    private String _reportType;

    /**
     * データ区分
     */
    private String _dataType;

    /**
     * 作業日
     */
    private String _workDay;

    /**
     * 作業日(to)
     */
    private String _toWorkDay;

    /**
     * エリアNo.
     */
    private String _areaNo;

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
     * 検索日時
     */
    private java.util.Date _searchDate;

    /**
     * 検索日時(to)
     */
    private java.util.Date _toSearchDate;

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
     * 最終更新日時
     */
    private java.util.Date _lastUpdateDate;

    /**
     * 検索時刻(to)
     */
    private String _toSearchTime;

    /**
     * 登録時刻
     */
    private String _registTime;

    /**
     * 登録時刻(to)
     */
    private String _toRegistTime;

    /**
     * 表示用開始日
     */
    private String _dispDayFrom;

    /**
     * 表示用開始時間
     */
    private String _dispTimeFrom;

    /**
     * 表示用終了日
     */
    private String _dispDayTo;

    /**
     * 表示用終了時間
     */
    private String _dispTimeTo;

    /**
     * 表示用終了時刻(to)
     */
    private java.util.Date _toDispDate;

    /**
     * 取込開始日時
     */
    private java.util.Date _loadStartDate;

    /**
     * 作業区分
     */
    private String _jobType;

    /**
     * プリンタ名
     */
    private String _printerName;

    /**
     * 集約条件
     */
    private String _collectCondition;

    /**
     * 表示条件
     */
    private String _displayCondition;

    /**
     * 日次処理
     */
    private String _dailyUpdating;

    /**
     * 予定データ取込
     */
    private String _dataLoading;

    /**
     * 報告データ作成
     */
    private String _dataReporting;

    /**
     * 出庫引当
     */
    private String _retrievalAllocate;

    /**
     * ホスト通信
     */
    private String _hostEnabled;

    /**
     * 荷主コード
     */
    private String _consignorCode;

    /**
     * 開始終了フラグ
     */
    private String _fromtoFrag;

    /**
     * 取込ファイル数
     */
    private int _loadFiles_count;

    /**
     * 途中作業区分
     */
    private String _workOnTheWay;

    /**
     * 不足数量区分
     */
    private String _lackFlag;

    /**
     * 範囲指定フラグ
     */
    private boolean _rangeFlag;

    /**
     * 開始終了フラグ(検索日時)
     */
    private String _fromtoDateFrag;

    /**
     * ファイルパス
     */
    private String _filePath;

    /**
     * ファイル名称
     */
    private String _fileName;

    /**
     * 行No.
     */
    private int _rowNo;

    /**
     * パッケージ選択
     */
    private boolean _packageSelect;

    // Constractor --------------------------------------------------
    // TODO:削除
    /**
     * コンストラクタ <BR>
     * WmsUserInfoを指定します。
     * 
     * @param info WmsUserInfo
     */
    public SystemInParameter()
    {
        super();
    }
       
    /**
     * コンストラクタ <BR>
     * WmsUserInfoを指定します。
     * 
     * @param info WmsUserInfo
     */
    public SystemInParameter(WmsUserInfo info)
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
        return ("$Revision: 3208 $,$Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $");
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
     * 作業日(to)を返します。
     * @return 作業日(to)を返します。
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
     * 検索日時を返します。
     * @return 検索日時を返します。
     */
    public java.util.Date getSearchDate()
    {
        return _searchDate;
    }

    /**
     * 検索日時を設定します。
     * @param searchDate 検索日時
     */
    public void setSearchDate(java.util.Date searchDate)
    {
        _searchDate = searchDate;
    }

    /**
     * 検索日時(to)を返します。
     * @return 検索日時(to)を返します。
     */
    public java.util.Date getToSearchDate()
    {
        return _toSearchDate;
    }

    /**
     * 検索日時(to)を設定します。
     * @param toSearchDate 検索日時(to)
     */
    public void setToSearchDate(java.util.Date toSearchDate)
    {
        _toSearchDate = toSearchDate;
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
     * 登録時刻(to)を返します。
     * @return 登録時刻(to)を返します。
     */
    public String getToRegistTime()
    {
        return _toRegistTime;
    }

    /**
     * 登録時刻(to)を設定します。
     * @param toRegistTime 登録時刻(to)
     */
    public void setToRegistTime(String toRegistTime)
    {
        _toRegistTime = toRegistTime;
    }

    /**
     * 表示用開始日を返します。
     * @return 表示用開始日を返します。
     */
    public String getDispDayFrom()
    {
        return _dispDayFrom;
    }

    /**
     * 表示用開始日を設定します。
     * @param dispDayFrom 表示用開始日
     */
    public void setDispDayFrom(String dispDayFrom)
    {
        _dispDayFrom = dispDayFrom;
    }

    /**
     * 表示用終了日を返します。
     * @return 表示用終了日を返します。
     */
    public String getDispDayTo()
    {
        return _dispDayTo;
    }

    /**
     * 表示用終了日を設定します。
     * @param dispDayTo 表示用終了日
     */
    public void setDispDayTo(String dispDayTo)
    {
        _dispDayTo = dispDayTo;
    }

    /**
     * 表示用開始時間を返します。
     * @return 表示用開始時間を返します。
     */
    public String getDispTimeFrom()
    {
        return _dispTimeFrom;
    }

    /**
     * 表示用開始時間を設定します。
     * @param dispTimeFrom 表示用開始時間
     */
    public void setDispTimeFrom(String dispTimeFrom)
    {
        _dispTimeFrom = dispTimeFrom;
    }

    /**
     * 表示用終了時間を返します。
     * @return 表示用終了時間を返します。
     */
    public String getDispTimeTo()
    {
        return _dispTimeTo;
    }

    /**
     * 表示用終了時間を設定します。
     * @param dispTimeTo 表示用終了時間
     */
    public void setDispTimeTo(String dispTimeTo)
    {
        _dispTimeTo = dispTimeTo;
    }

    /**
     * 表示用終了日時を返します。
     * @return 表示用終了日時を返します。
     */
    public java.util.Date getDispDate()
    {
        return _toDispDate;
    }

    /**
     * 表示用終了日時を設定します。
     * @param dispDate 表示用終了日時
     */
    public void setDispDate(java.util.Date dispDate)
    {
        _toDispDate = dispDate;
    }

    /**
     * 取込開始日時を返します。
     * @return 取込開始日時を返します。
     */
    public java.util.Date getLoadStartDate()
    {
        return _loadStartDate;
    }

    /**
     * 取込開始日時を設定します。
     * @param loadStartDate 取込開始日時
     */
    public void setLoadStartDate(java.util.Date loadStartDate)
    {
        _loadStartDate = loadStartDate;
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
     * 表示条件を返します。
     * @return 表示条件を返します。
     */
    public String getDisplayCondition()
    {
        return _displayCondition;
    }

    /**
     * 表示条件を設定します。
     * @param displayCondition 表示条件
     */
    public void setDisplayCondition(String displayCondition)
    {
        _displayCondition = displayCondition;
    }

    /**
     * 日次処理状態を返します。
     * @return 日次処理状態を返します。
     */
    public String getDatilyUpdating()
    {
        return _dailyUpdating;
    }

    /**
     * 日次処理状態を設定します。
     * @param datilyUpdating 日次処理状態
     */
    public void setDatilyUpdating(String datilyUpdating)
    {
        _dailyUpdating = datilyUpdating;
    }

    /**
     * 予定データ取込を返します。
     * @return 予定データ取込を返します。
     */
    public String getDataLoading()
    {
        return _dataLoading;
    }

    /**
     * 予定データ取込を設定します。
     * @param dataLoading 予定データ取込
     */
    public void setDataLoading(String dataLoading)
    {
        _dataLoading = dataLoading;
    }

    /**
     * 報告データ作成を返します。
     * @return 報告データ作成を返します。
     */
    public String getDataReporting()
    {
        return _dataReporting;
    }

    /**
     * 報告データ作成を設定します。
     * @param dataReporting 報告データ作成
     */
    public void setDataReporting(String dataReporting)
    {
        _dataReporting = dataReporting;
    }

    /**
     * 出庫引当を返します。
     * @return 出庫引当を返します。
     */
    public String getRetrievalAllocate()
    {
        return _retrievalAllocate;
    }

    /**
     * 出庫引当を設定します。
     * @param retrievalAllocate 出庫引当
     */
    public void setRetrievalAllocate(String retrievalAllocate)
    {
        _retrievalAllocate = retrievalAllocate;
    }

    /**
     * ホスト通信を返します。
     * @return ホスト通信を返します。
     */
    public String getHostEnabled()
    {
        return _hostEnabled;
    }

    /**
     * ホスト通信を設定します。
     * @param hostEnabled ホスト通信
     */
    public void setHostEnabled(String hostEnabled)
    {
        _hostEnabled = hostEnabled;
    }

    /**
     * バッチ処理（未処理）
     */
    public static final String JOB_STATUS_WAIT = "0";

    /**
     * バッチ処理（処理中）
     */
    public static final String JOB_STATUS_RUN = "1";


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
     * 途中作業区分を返します。
     * @return 途中作業区分を返します。
     */
    public String getWorkOnTheWay()
    {
        return _workOnTheWay;
    }

    /**
     * 途中作業区分を設定します。
     * @param workOnTheWay 途中作業区分
     */
    public void setWorkOnTheWay(String workOnTheWay)
    {
        _workOnTheWay = workOnTheWay;
    }

    /**
     * 不足数量区分を返します。
     * @return 不足数量区分を返します。
     */
    public String getLackFlag()
    {
        return _lackFlag;
    }

    /**
     * 不足数量区分を設定します。
     * @param lackFlag 不足数量区分
     */
    public void setLackFlag(String lackFlag)
    {
        _lackFlag = lackFlag;
    }

    /**
     * 範囲フラグを返します。
     * @return 出範囲フラグを返します。
     */
    public boolean isRangeFlag()
    {
        return _rangeFlag;
    }

    /**
     * 範囲フラグを設定します。
     * @param rangeFlag 範囲フラグ
     */
    public void setRangeFlag(boolean rangeFlag)
    {
        _rangeFlag = rangeFlag;
    }

    /**
     * 開始終了フラグ(検索日時)を返します。
     * @return 開始終了フラグ(検索日時)
     */
    public String getFromToDateFrag()
    {
        return _fromtoDateFrag;
    }

    /**
     * 開始終了フラグ(検索日時)を設定します。
     * @param fromtoDateFlag 開始終了フラグ(検索日時)
     */
    public void setFromToDateFlag(String fromtoDateFlag)
    {
        _fromtoDateFrag = fromtoDateFlag;
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
     * 行No.を返します。
     * @return 行No.を返します。
     */
    public int getRowNo()
    {
        return _rowNo;
    }

    /**
     * 行No.を設定します。
     * @param rowNo 行No.
     */
    public void setRowNo(int rowNo)
    {
        _rowNo = rowNo;
    }

    /**
     * パッケージ選択を返します。
     * @return パッケージ選択を返します。
     */
    public boolean getPackageSelect()
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


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

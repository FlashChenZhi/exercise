package jp.co.daifuku.pcart.system.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.OutParameter;

/**
 * <CODE>PCTSystemOutParameter</CODE>クラスは、システムパッケージ内のスケジュール→画面間のパラメータの受渡しに使用します。<BR>
 * このクラスではシステムパッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>PCTSystemOutParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     RFTNo.<BR>
 *     端末区分<BR>
 *     RFT状態<BR>
 *     作業区分<BR>
 *     エリア名称<BR>
 *     ユーザー名<BR> 
 *     作業区分<BR>    
 *     データ区分<BR>
 *     パッケージ選択<BR>
 *     取込内容<BR>
 *     ピッキング出庫予定データ格納ファイル名<BR>
 *     取込ファイル数<BR>
 *     内容<BR>
 *     発生クラス<BR>
 *     メッセージ<BR>
 *     荷主コード<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     エリアNo.<BR>
 *     棚<BR>
 *     棚(to)<BR>
 *     作業日<BR>
 *     ゾーンNo.<BR>
 *     ゾーン名称<BR>
 *     入荷予定データ格納フォルダ<BR>
 *     入荷実績格納フォルダ<BR>
 *     入荷報告単位<BR>
 *     出庫予定データ格納フォルダ<BR>
 *     出庫実績格納フォルダ<BR>
 *     出庫報告単位<BR>
 *     棚卸実績格納フォルダ<BR>
 *     入荷実績格納ファイル名<BR>
 *     出庫実績格納ファイル名<BR>
 *     棚卸実績格納ファイル名<BR>
 *     入荷実績_選択<BR>
 *     出庫実績_選択<BR>
 *     棚卸実績_選択<BR>
 *     ﾛｯﾄ/H<BR>
 *     ｵｰﾀﾞｰ/H<BR>
 *     行/H<BR>
 *     現ﾛｯﾄ/H<BR>
 *     現ｵｰﾀﾞｰ/H<BR>
 *     現行/H<BR>
 *     最終更新日時<BR>   
 *     レベルA最終更新日時<BR>   
 *     レベルC最終更新日時<BR>     
 *     作業日<BR>
 *     ランクA基準値<BR>
 *     ランクB基準値<BR>  
 *     作業ランク設定方法<BR>
 *     重量誤差率初期値<BR>
 *     オーダー最大重量<BR>
 *     センター名<BR>
 *     ランク(0:Aランク, 1:Bランク, 2:Cランク)<BR>
 *     ロール<BR>
 *     ロール名<BR>
 *     バッチNo><BR>
 *     バッチSeqNo><BR>
 * </DIR>
 * 
 * Designer : E.Takeda <BR>
 * Maker : E.Takeda <BR>
 *
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  $Author: arai $
 */
public class PCTSystemOutParameter
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
     * PCT商品マスタ取込：未処理
     */
    public static final String FLAG_WAIT = "0";
    
    /**
     * RftNo.
     */
    private String _rftNo;

    /**
     * 端末区分
     */
    private String _terminalType;

    /**
     * RFT状態
     */
    private String _rftStatus;

    /**
     * エリア名称
     */
    private String _areaName;

    /**
     * ユーザー名
     */
    private String _userName;

    /**
     * 作業区分
     */
    private String _jobType;

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
     * ファイル名称
     */
    private String _fileName;

    /**
     * PCT出庫予定データ格納ファイル名
     */
    private String _pctRetrievalLoad_Filename;

    /**
     * PCT入荷予定データ格納ファイル名
     */
    private String _pctInstockLoad_Filename;

    /**
     * 取込ファイル数
     */
    private int _loadFiles_count;

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
     * 商品名称
     */
    private String _itemName;

    /**
     * エリアNo.
     */
    private String _areaNo;

    /**
     * 棚
     */
    private String _location;

    /**
     * 棚(to)
     */
    private String _toLocation;

    /**
     * 作業日
     */
    private String _workDate;

    /**
     * ゾーンNo.
     */
    private String _zoneNo;

    /**
     * ゾーン名称
     */
    private String _zoneName;

    /**
     * 入荷実績格納フォルダ
     */
    private String _receivingReport_Folder;

    /**
     * 入荷報告単位
     */
    private String _receivingReport_Type;

    /**
     * 出庫実績格納フォルダ
     */
    private String _retrievalReport_Folder;

    /**
     * 出庫報告単位
     */
    private String _retrievalReport_Type;

    /**
     * 棚卸実績格納フォルダ
     */
    private String _inventoryReport_Folder;

    /**
     * 入荷実績格納ファイル名
     */
    private String _receivingReport_Filename;

    /**
     * 出庫実績格納ファイル名
     */
    private String _retrievalReport_Filename;

    /**
     * 棚卸実績格納ファイル名
     */
    private String _inventoryReport_Filename;

    /**
     * 入荷実績_選択
     */
    private boolean _selectReceivingReport;

    /**
     * 出庫実績_選択
     */
    private boolean _selectRetrievalReport;

    /**
     * 棚卸実績_選択
     */
    private boolean _selectInventoryReport;

    /**
     * PCT入荷予定データ格納フォルダ
     */
    private String _pctRetrievalLoadData_Folder;

    /**
     * PCT出庫予定データ格納フォルダ
     */
    private String _pctInstockLoadData_Folder;

    /**
     * JANコード
     */
    private String _jan;

    /**
     * ロット入数
     */
    private int _lotEnteringQty;

    /**
     * ケース入数
     */
    private int _enteringQty;

    /**
     * ケースITF
     */
    private String _itf;

    /**
     * ボール入数
     */
    private int _bundleEnteringQty;

    /**
     * ボールITF
     */
    private String _bundleItf;

    /**
     * 発注単位
     */
    private int _unit;

    /**
     * ロケーションNo.1
     */
    private String _locationNo1;

    /**
     * 入数1
     */
    private int _enteringQty1;

    /**
     * ロケーションNo.2
     */
    private String _locationNo2;

    /**
     * 入数2
     */
    private int _enteringQty2;

    /**
     * ロケーションNo.3
     */
    private String _locationNo3;

    /**
     * 入数3
     */
    private int _enteringQty3;

    /**
     * ロケーションNo.4
     */
    private String _locationNo4;

    /**
     * 入数4
     */
    private int _enteringQty4;

    /**
     * 賞味期間
     */
    private String _useByPeriod;

    /**
     * 入荷限度日
     */
    private String _instockLimitDate;

    /**
     * 出荷限度日
     */
    private String _shippingLimitDate;

    /**
     * 最新賞味期限
     */
    private String _latestUseByDate;

    /**
     * 最新製造日
     */
    private String _latestManufacutureDate;

    /**
     * 最新出庫日
     */
    private String _latestRetrievalDate;

    /**
     * 最新在庫
     */
    private String _latestStock;

    /**
     * 最古在庫
     */
    private String _oldestStock;

    /**
     * 管理フラグ
     */
    private String _managementFlg;

    /**
     * 上限在庫数
     */
    private int _upperQty;

    /**
     * 下限在庫数
     */
    private int _lowerQty;

    /**
     * 最終更新日時
     */
    private String _lastUpdateDate;

    /**
     * 最終更新日時
     */
    private String _lastUpdateDateA;
    
    /**
     * 最終更新日時
     */
    private String _lastUpdateDateC;
    
    /**
     * 最終使用日時
     */
    private String _lastUsedDate;

    /**
     * ﾛｯﾄ/H
     */
    private double _allLotPerHour;

    /**
     * ｵｰﾀﾞｰ/H
     */
    private double _allOrderPerHour;

    /**
     * 行/H
     */
    private double _allItemPerHour;

    /**
     * 現ﾛｯﾄ/H
     */
    private String _nowAllLotPerHour;

    /**
     * 現ｵｰﾀﾞｰ/H
     */
    private String _nowAllOrderPerHour;

    /**
     * 現行/H
     */
    private String _nowAllItemPerHour;

    /**
     * 最終更新日時(Date)
     */
    private java.util.Date _lastUpdate;

    /**
     * 作業日
     */
    private String _workDay;

    /**
     * ランクA基準値
     */
    private int _rankA;

    /**
     * ランクB基準値
     */
    private int _rankB;

    /**
     * 作業ランク設定方法
     */
    private String _workRankSetType;

    /**
     * 重量誤差率初期値
     */
    private int _weightErrorRateInit;

    /**
     * オーダー最大重量
     */
    private int _orderMaxWeight;

    /**
     * センター名
     */
    private String _centerName;

    /**
     * ランク(0:Aランク, 1:Bランク, 2:Cランク)
     */
    private String _rank;

    /**
     * ロール
     */
    private String _role;

    /**
     * ロール名
     */
    private String _roleName;

    /**
     * バッチNo
     */
    private String _batchNo;

    /**
     * バッチSeqNo
     */
    private String _batchSeqNo;
    
    /**
     * PCT商品マスタ取込フラグ
     */
    private String _itemLoad;

    /**
     * ランク基準値設定出力パラメータのセット位置定義
     */
    public static final int OUT_POINT_SYSTEM = 0;

    /**
     *  ランクA出力パラメータデータ位置
     */
    public static final int OUT_POINT_RANK_A = 1;

    /**
     * ランクB出力パラメータデータ位置
     */
    public static final int OUT_POINT_RANK_B = 2;

    /**
     * ランクC出力パラメータデータ位置
     */
    public static final int OUT_POINT_RANK_C = 3;


    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 3209 $,$Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $");
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
     * ユーザー名を返します。
     * @return ユーザー名を返します。
     */
    public String getUserName()
    {
        return _userName;
    }

    /**
     * ユーザー名を設定します。
     * @param userName エリア名称
     */
    public void setUserName(String userName)
    {
        _userName = userName;
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
     * PCT出庫予定データ格納ファイル名を返します。
     * @return 出荷実績格納ファイル名を返します。
     */
    public String getPCTRetrievalLoad_Filename()
    {
        return _pctRetrievalLoad_Filename;
    }

    /**
     * PCT出庫予定データ格納ファイル名を設定します。
     * @param pctRetrievalLoadFilename 出荷実績格納ファイル名
     */
    public void setPCTRetrievalLoad_Filename(String pctRetrievalLoadFilename)
    {
        _pctRetrievalLoad_Filename = pctRetrievalLoadFilename;
    }

    /**
     * PCT入荷予定データ格納ファイル名を返します。
     * @return PCT入荷実績格納ファイル名を返します。
     */
    public String getPCTInstockLoad_Filename()
    {
        return _pctInstockLoad_Filename;
    }

    /**
     * PCT入荷予定データ格納ファイル名を設定します。
     * @param pctInstockLoadFilename PCT入荷実績格納ファイル名
     */
    public void setPCTInstockLoad_Filename(String pctInstockLoadFilename)
    {
        _pctInstockLoad_Filename = pctInstockLoadFilename;
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
     * 棚(to)を返します。
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
     * 作業日を返します。
     * @return 作業日を返します。
     */
    public String getWorkDate()
    {
        return _workDate;
    }

    /**
     * 作業日を設定します。
     * @param workDate 作業日
     */
    public void setWorkDate(String workDate)
    {
        _workDate = workDate;
    }

    /**
     * ゾーンNo.を返します。
     * @return ゾーンNo.を返します。
     */
    public String getZoneNo()
    {
        return _zoneNo;
    }

    /**
     * ゾーンNo.を設定します。
     * @param zoneNo ゾーンNo.
     */
    public void setZoneNo(String zoneNo)
    {
        _zoneNo = zoneNo;
    }

    /**
     * ゾーン名称を返します。
     * @return ゾーン名称を返します。
     */
    public String getZoneName()
    {
        return _zoneName;
    }

    /**
     * ゾーン名称を設定します。
     * @param zoneName ゾーン名称
     */
    public void setZoneName(String zoneName)
    {
        _zoneName = zoneName;
    }

    /**
     * 入荷実績格納フォルダを返します。
     * @return 入荷実績格納フォルダを返します。
     */
    public String getReceivingReport_Folder()
    {
        return _receivingReport_Folder;
    }

    /**
     * 入荷実績格納フォルダを設定します。
     * @param receivingReportFolder 入荷実績格納フォルダ
     */
    public void setReceivingReport_Folder(String receivingReportFolder)
    {
        _receivingReport_Folder = receivingReportFolder;
    }

    /**
     * 入荷実績格納ファイル名を返します。
     * @return 入荷実績格納ファイル名を返します。
     */
    public String getReceivingReport_Filename()
    {
        return _receivingReport_Filename;
    }

    /**
     * 入荷実績格納ファイル名を設定します。
     * @param receivingReportFilename 入荷実績格納ファイル名
     */
    public void setReceivingReport_Filename(String receivingReportFilename)
    {
        _receivingReport_Filename = receivingReportFilename;
    }

    /**
     * 出庫実績格納フォルダを返します。
     * @return 出庫実績格納フォルダを返します。
     */
    public String getRetrievalReport_Folder()
    {
        return _retrievalReport_Folder;
    }

    /**
     * 出庫実績格納フォルダを設定します。
     * @param retrievalReportFolder 出庫実績格納フォルダ
     */
    public void setRetrievalReport_Folder(String retrievalReportFolder)
    {
        _retrievalReport_Folder = retrievalReportFolder;
    }

    /**
     * 出庫実績格納ファイル名を返します。
     * @return 出庫実績格納ファイル名を返します。
     */
    public String getRetrievalReport_Filename()
    {
        return _retrievalReport_Filename;
    }

    /**
     * 出庫実績格納ファイル名を設定します。
     * @param retrievalReportFilename 出庫実績格納ファイル名
     */
    public void setRetrievalReport_Filename(String retrievalReportFilename)
    {
        _retrievalReport_Filename = retrievalReportFilename;
    }

    /**
     * 棚卸実績格納フォルダを返します。
     * @return 棚卸実績格納フォルダを返します。
     */
    public String getInventoryReport_Folder()
    {
        return _inventoryReport_Folder;
    }

    /**
     * 棚卸実績格納フォルダを設定します。
     * @param inventoryReportFolder 棚卸実績格納フォルダ
     */
    public void setInventoryReport_Folder(String inventoryReportFolder)
    {
        _inventoryReport_Folder = inventoryReportFolder;
    }

    /**
     * 棚卸実績格納ファイル名を返します。
     * @return 棚卸実績格納ファイル名を返します。
     */
    public String getInventoryReport_Filename()
    {
        return _inventoryReport_Filename;
    }

    /**
     * 棚卸実績格納ファイル名を設定します。
     * @param inventoryReportFilename 棚卸実績格納ファイル名
     */
    public void setInventoryReport_Filename(String inventoryReportFilename)
    {
        _inventoryReport_Filename = inventoryReportFilename;
    }

    /**
     * 入荷報告単位を返します。
     * @return 入荷報告単位を返します。
     */
    public String getReceivingReport_Type()
    {
        return _receivingReport_Type;
    }

    /**
     * 入荷報告単位を設定します。
     * @param receivingReportType 入荷報告単位
     */
    public void setReceivingReport_Type(String receivingReportType)
    {
        _receivingReport_Type = receivingReportType;
    }

    /**
     * 出庫報告単位を返します。
     * @return 出庫報告単位を返します。
     */
    public String getRetrievalReport_Type()
    {
        return _retrievalReport_Type;
    }

    /**
     * 出庫報告単位を設定します。
     * @param retrievalReportType 出庫報告単位
     */
    public void setRetrievalReport_Type(String retrievalReportType)
    {
        _retrievalReport_Type = retrievalReportType;
    }

    /**
     * 入荷実績_選択を返します。
     * @return 入荷実績_選択を返します。
     */
    public boolean isSelectReceivingReport()
    {
        return _selectReceivingReport;
    }

    /**
     * 出庫実績_選択を返します。
     * @return 出庫実績_選択を返します。
     */
    public boolean isSelectRetrievalReport()
    {
        return _selectRetrievalReport;
    }

    /**
     * 棚卸実績_選択を設定します。
     * @param selectInventoryReport 棚卸実績_選択
     */
    public void setSelectInventoryReport(boolean selectInventoryReport)
    {
        _selectInventoryReport = selectInventoryReport;
    }

    /**
     * 入荷実績_選択を設定します。
     * @param selectReceivingReport 入荷実績_選択
     */
    public void setSelectReceivingReport(boolean selectReceivingReport)
    {
        _selectReceivingReport = selectReceivingReport;
    }

    /**
     * 出庫実績_選択を設定します。
     * @param selectRetrievalReport 出庫実績_選択
     */
    public void setSelectRetrievalReport(boolean selectRetrievalReport)
    {
        _selectRetrievalReport = selectRetrievalReport;
    }

    /**
     * 棚卸実績_選択を返します。
     * @return 棚卸実績_選択を返します。
     */
    public boolean isSelectInventoryReport()
    {
        return _selectInventoryReport;
    }

    /**
     * PCT入荷予定データ格納フォルダを返します。
     * @return PCT入荷予定予定データ格納フォルダを返します。
     */
    public String getPCTRetrievalLoadData_Folder()
    {
        return _pctRetrievalLoadData_Folder;
    }

    /**
     * PCT入荷予定データ格納フォルダを設定します。
     * @param pctRetrievalLoadDataFolder PCT入荷予定予定データ格納フォルダ
     */
    public void setPCTRetrievalLoadData_Folder(String pctRetrievalLoadDataFolder)
    {
        _pctRetrievalLoadData_Folder = pctRetrievalLoadDataFolder;
    }

    /**
     * PCT出庫予定データ格納フォルダを返します。
     * @return PCT出庫予定データ格納フォルダを返します。
     */
    public String getPCTInstockLoadData_Folder()
    {
        return _pctInstockLoadData_Folder;
    }

    /**
     * PCT出庫予定データ格納フォルダを設定します。
     * @param pctInstockLoadDataFolder PCT出庫予定データ格納フォルダ
     */
    public void setPCTInstockLoadData_Folder(String pctInstockLoadDataFolder)
    {
        _pctInstockLoadData_Folder = pctInstockLoadDataFolder;
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
     * 発注単位を返します。
     * @return 発注単位を返します。
     */
    public int getUnit()
    {
        return _unit;
    }

    /**
     * 発注単位を設定します。
     * @param unit 発注単位
     */
    public void setUnit(int unit)
    {
        _unit = unit;
    }

    /**
     * ロケーションNo.1を返します。
     * @return ロケーションNo.1を返します。
     */
    public String getLocationNo1()
    {
        return _locationNo1;
    }

    /**
     * ロケーションNo.1を設定します。
     * @param locationNo1 ロケーションNo.1
     */
    public void setLocationNo1(String locationNo1)
    {
        _locationNo1 = locationNo1;
    }

    /**
     * 入数1を返します。
     * @return 入数1を返します。
     */
    public int getEnteringQty1()
    {
        return _enteringQty1;
    }

    /**
     * 入数1を設定します。
     * @param enteringQty1 入数1
     */
    public void setEnteringQty1(int enteringQty1)
    {
        _enteringQty1 = enteringQty1;
    }

    /**
     * ロケーションNo.2を返します。
     * @return ロケーションNo.2を返します。
     */
    public String getLocationNo2()
    {
        return _locationNo2;
    }

    /**
     * ロケーションNo.2を設定します。
     * @param locationNo2 ロケーションNo.2
     */
    public void setLocationNo2(String locationNo2)
    {
        _locationNo2 = locationNo2;
    }

    /**
     * 入数2を返します。
     * @return 入数2を返します。
     */
    public int getEnteringQty2()
    {
        return _enteringQty2;
    }

    /**
     * 入数2を設定します。
     * @param enteringQty2 入数2
     */
    public void setEnteringQty2(int enteringQty2)
    {
        _enteringQty2 = enteringQty2;
    }

    /**
     * ロケーションNo.3を返します。
     * @return ロケーションNo.3を返します。
     */
    public String getLocationNo3()
    {
        return _locationNo3;
    }

    /**
     * ロケーションNo.3を設定します。
     * @param locationNo3 ロケーションNo.3
     */
    public void setLocationNo3(String locationNo3)
    {
        _locationNo3 = locationNo3;
    }

    /**
     * 入数3を返します。
     * @return 入数3を返します。
     */
    public int getEnteringQty3()
    {
        return _enteringQty3;
    }

    /**
     * 入数3を設定します。
     * @param enteringQty3 入数3
     */
    public void setEnteringQty3(int enteringQty3)
    {
        _enteringQty3 = enteringQty3;
    }

    /**
     * ロケーションNo.4を返します。
     * @return ロケーションNo.4を返します。
     */
    public String getLocationNo4()
    {
        return _locationNo4;
    }

    /**
     * ロケーションNo.4を設定します。
     * @param locationNo4 ロケーションNo.4
     */
    public void setLocationNo4(String locationNo4)
    {
        _locationNo4 = locationNo4;
    }

    /**
     * 入数4を返します。
     * @return 入数4を返します。
     */
    public int getEnteringQty4()
    {
        return _enteringQty4;
    }

    /**
     * 入数4を設定します。
     * @param enteringQty4 入数4
     */
    public void setEnteringQty4(int enteringQty4)
    {
        _enteringQty4 = enteringQty4;
    }

    /**
     * 賞味期間を返します。
     * @return 賞味期間を返します。
     */
    public String getUseByPeriod()
    {
        return _useByPeriod;
    }

    /**
     * 賞味期間を設定します。
     * @param useByPeriod 賞味期間
     */
    public void setUseByPeriod(String useByPeriod)
    {
        _useByPeriod = useByPeriod;
    }

    /**
     * 入荷限度日を返します。
     * @return 入荷限度日を返します。
     */
    public String getInstockLimitDate()
    {
        return _instockLimitDate;
    }

    /**
     * 入荷限度日を設定します。
     * @param instockLimitDate 入荷限度日
     */
    public void setInstockLimitDate(String instockLimitDate)
    {
        _instockLimitDate = instockLimitDate;
    }

    /**
     * 出荷限度日を返します。
     * @return 出荷限度日を返します。
     */
    public String getShippingLimitDate()
    {
        return _shippingLimitDate;
    }

    /**
     * 出荷限度日を設定します。
     * @param shippingLimitDate 出荷限度日
     */
    public void setShippingLimitDate(String shippingLimitDate)
    {
        _shippingLimitDate = shippingLimitDate;
    }

    /**
     * 最新賞味期限を返します。
     * @return 最新賞味期限を返します。
     */
    public String getLatestUseByDate()
    {
        return _latestUseByDate;
    }

    /**
     * 最新賞味期限を設定します。
     * @param latestUseByDate 最新賞味期限
     */
    public void setLatestUseByDate(String latestUseByDate)
    {
        _latestUseByDate = latestUseByDate;
    }

    /**
     * 最新製造日を返します。
     * @return 最新製造日を返します。
     */
    public String getLatestManufacutureDate()
    {
        return _latestManufacutureDate;
    }

    /**
     * 最新製造日を設定します。
     * @param latestManufacutureDate 最新製造日
     */
    public void setLatestManufacutureDate(String latestManufacutureDate)
    {
        _latestManufacutureDate = latestManufacutureDate;
    }

    /**
     * 最新出庫日を返します。
     * @return 最新出庫日を返します。
     */
    public String getLatestRetrievalDate()
    {
        return _latestRetrievalDate;
    }

    /**
     * 最新出庫日を設定します。
     * @param latestRetrievalDate 最新出庫日
     */
    public void setLatestRetrievalDate(String latestRetrievalDate)
    {
        _latestRetrievalDate = latestRetrievalDate;
    }

    /**
     * 最新在庫を返します。
     * @return 最新在庫を返します。
     */
    public String getLatestStock()
    {
        return _latestStock;
    }

    /**
     * 最新在庫を設定します。
     * @param latestStock 最新在庫
     */
    public void setLatestStock(String latestStock)
    {
        _latestStock = latestStock;
    }

    /**
     * 最古在庫を返します。
     * @return 最古在庫を返します。
     */
    public String getOldestStock()
    {
        return _oldestStock;
    }

    /**
     * 最古在庫を設定します。
     * @param oldestStock 最古在庫
     */
    public void setOldestStock(String oldestStock)
    {
        _oldestStock = oldestStock;
    }

    /**
     * 管理フラグを返します。
     * @return 管理フラグを返します。
     */
    public String getManagementFlag()
    {
        return _managementFlg;
    }

    /**
     * 管理フラグを設定します。
     * @param managementFlg 管理フラグ
     */
    public void setManagementFlag(String managementFlg)
    {
        _managementFlg = managementFlg;
    }

    /**
     * 上限在庫数を返します。
     * @return 上限在庫数を返します。
     */
    public int getUpperQty()
    {
        return _upperQty;
    }

    /**
     * 上限在庫数を設定します。
     * @param upperQty 上限在庫数
     */
    public void setUpperQty(int upperQty)
    {
        _upperQty = upperQty;
    }

    /**
     * 下限在庫数を返します。
     * @return 下限在庫数を返します。
     */
    public int getLowerQty()
    {
        return _lowerQty;
    }

    /**
     * 下限在庫数を設定します。
     * @param lowerQty 下限在庫数
     */
    public void setLowerQty(int lowerQty)
    {
        _lowerQty = lowerQty;
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
     * @param lastUpdateDate 最終更新日時
     */
    public void setLastUpdateDate(String lastUpdateDate)
    {
        _lastUpdateDate = lastUpdateDate;
    }
    
    /**
     * レベルAの最終更新日時を返します。
     * @return レベルAの最終更新日時を返します。
     */
    public String getLastUpdateDateA()
    {
        return _lastUpdateDateA;
    }

    /**
     * レベルAの最終更新日時を設定します。
     * @param lastUpdateDateA レベルAの最終更新日時
     */
    public void setLastUpdateDateA(String lastUpdateDateA)
    {
        _lastUpdateDateA = lastUpdateDateA;
    }
    /**
     * レベルCの最終更新日時を返します。
     * @return レベルCの最終更新日時を返します。
     */
    public String getLastUpdateDateC()
    {
        return _lastUpdateDateC;
    }

    /**
     * レベルCの最終更新日時を設定します。
     * @param lastUpdateDateC レベルCの最終更新日時
     */
    public void setLastUpdateDateC(String lastUpdateDateC)
    {
        _lastUpdateDateC = lastUpdateDateC;
    }

    /**
     * 最終使用日を返します。
     * @return 最終使用日を返します。
     */
    public String getLastUsedDate()
    {
        return _lastUsedDate;
    }

    /**
     * 最終使用日を設定します。
     * @param lastUsedDate 最終使用日
     */
    public void setLastUsedDate(String lastUsedDate)
    {
        _lastUsedDate = lastUsedDate;
    }

    /**
     * 行/Hを返します。
     * @return 行/Hを返します。
     */
    public double getAllItemPerHour()
    {
        return _allItemPerHour;
    }

    /**
     * 行/Hを設定します。
     * @param allItemPerHour 行/H
     */
    public void setAllItemPerHour(double allItemPerHour)
    {
        _allItemPerHour = allItemPerHour;
    }

    /**
     * ﾛｯﾄ/Hを返します。
     * @return ﾛｯﾄ/Hを返します。
     */
    public double getAllLotPerHour()
    {
        return _allLotPerHour;
    }

    /**
     * ﾛｯﾄ/Hを設定します。
     * @param allLotPerHour ﾛｯﾄ/H
     */
    public void setAllLotPerHour(double allLotPerHour)
    {
        _allLotPerHour = allLotPerHour;
    }

    /**
     * ｵｰﾀﾞｰ/Hを返します。
     * @return ｵｰﾀﾞｰ/Hを返します。
     */
    public double getAllOrderPerHour()
    {
        return _allOrderPerHour;
    }

    /**
     * ｵｰﾀﾞｰ/Hを設定します。
     * @param allOrderPerHour ｵｰﾀﾞｰ/H
     */
    public void setAllOrderPerHour(double allOrderPerHour)
    {
        _allOrderPerHour = allOrderPerHour;
    }

    /**
     * 現・行/Hを返します。
     * @return 現・行/Hを返します。
     */
    public String getNowAllItemPerHour()
    {
        return _nowAllItemPerHour;
    }

    /**
     * 現・行/Hを設定します。
     * @param nowAllItemPerHour 現・行/H
     */
    public void setNowAllItemPerHour(String nowAllItemPerHour)
    {
        _nowAllItemPerHour = nowAllItemPerHour;
    }

    /**
     * 現・ﾛｯﾄ/Hを返します。
     * @return 現・ﾛｯﾄ/Hを返します。
     */
    public String getNowAllLotPerHour()
    {
        return _nowAllLotPerHour;
    }

    /**
     * 現・ﾛｯﾄ/Hを設定します。
     * @param nowAllLotPerHour 現・ﾛｯﾄ/H
     */
    public void setNowAllLotPerHour(String nowAllLotPerHour)
    {
        _nowAllLotPerHour = nowAllLotPerHour;
    }

    /**
     * 現・ｵｰﾀﾞｰ/Hを返します。
     * @return 現・ｵｰﾀﾞｰ/Hを返します。
     */
    public String getNowAllOrderPerHour()
    {
        return _nowAllOrderPerHour;
    }

    /**
     * 現・ｵｰﾀﾞｰ/Hを設定します。
     * @param nowAllOrderPerHour 現・ｵｰﾀﾞｰ/H
     */
    public void setNowAllOrderPerHour(String nowAllOrderPerHour)
    {
        _nowAllOrderPerHour = nowAllOrderPerHour;
    }

    /**
     * 最終更新日時を返します。
     * @return 最終更新日時を返します。
     */
    public java.util.Date getLastUpdate()
    {
        return _lastUpdate;
    }

    /**
     * 最終更新日時を設定します。
     * @param lastUpdate  最終更新日時
     */
    public void setLastUpdate(java.util.Date lastUpdate)
    {
        _lastUpdate = lastUpdate;
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
     * ランクA基準を返します。
     * @return ランクA基準を返します。
     */
    public int getRankA()
    {
        return _rankA;
    }

    /**
     * ランクA基準を設定します。
     * @param rankA ランクA基準
     */
    public void setRankA(int rankA)
    {
        _rankA = rankA;
    }

    /**
     * ランクB基準を返します。
     * @return ランクB基準を返します。
     */
    public int getRankB()
    {
        return _rankB;
    }

    /**
     * ランクB基準を設定します。
     * @param rankB ランクB基準
     */
    public void setRankB(int rankB)
    {
        _rankB = rankB;
    }

    /**
     * 作業ランク設定方法を返します。
     * @return 作業ランク設定方法を返します。
     */
    public String getWorkRankSetType()
    {
        return _workRankSetType;
    }

    /**
     * 作業ランク設定方法を設定します。
     * @param workRankSetType 作業ランク設定方法
     */
    public void setWorkRankSetType(String workRankSetType)
    {
        _workRankSetType = workRankSetType;
    }

    /**
     * 重量誤差率初期値を返します。
     * @return 重量誤差率初期値を返します。
     */
    public int getWeightErrorRateInit()
    {
        return _weightErrorRateInit;
    }

    /**
     * 重量誤差率初期値を設定します。
     * @param weightErrorRateInit 重量誤差率初期値
     */
    public void setWeightErrorRateInit(int weightErrorRateInit)
    {
        _weightErrorRateInit = weightErrorRateInit;
    }

    /**
     * オーダー最大重量を返します。
     * @return オーダー最大重量を返します。
     */
    public int getOrderMaxWeight()
    {
        return _orderMaxWeight;
    }

    /**
     * オーダー最大重量を設定します。
     * @param orderMaxWeight オーダー最大重量
     */
    public void setOrderMaxWeight(int orderMaxWeight)
    {
        _orderMaxWeight = orderMaxWeight;
    }

    /**
     * センター名を返します。
     * @return センター名を返します。
     */
    public String getCenterName()
    {
        return _centerName;
    }

    /**
     * センター名を設定します。
     * @param centerName センター名
     */
    public void setCenterName(String centerName)
    {
        _centerName = centerName;
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
     * バッチNo.を設定します。
     * @param batchNo バッチNo.
     */
    public void setBatchNo(String batchNo)
    {
        _batchNo = batchNo;
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
     * バッチSeqNo.を設定します。
     * @param batchSeqNo バッチSeqNo.
     */
    public void setBatchSeqNo(String batchSeqNo)
    {
        _batchSeqNo = batchSeqNo;
    }

    /**
     * バッチSeqNo.を返します。
     * @return バッチSeqNo.を返します。
     */
    public String getBatchSeqNo()
    {
        return _batchSeqNo;
    }
    
    /**
     * PCT商品マスタ取込フラグを返します
     * @return PCT商品マスタ取込フラグを返します
     */
    public String getItemLoad()
    {
        return _itemLoad;
    }

    /**
     * PCT商品マスタ取込フラグを設定します
     * @param itemLoad PCT商品マスタ取込フラグを設定します
     */
    public void setItemLoad(String itemLoad)
    {
        _itemLoad = itemLoad;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

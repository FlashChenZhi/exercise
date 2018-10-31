// $Id: PCTMasterInParameter.java 3209 2009-03-02 06:34:19Z arai $

package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.InParameter;

/**
 * <CODE>PCTSystemInParameter</CODE>クラスは、システムパッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスではシステムパッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>PCTSystemInParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *     商品コード<BR>
 *     JANコード<BR>
 *     商品名称<BR>
 *     ロット入数<BR>
 *     ケース入数<BR>
 *     ケースITF<BR>
 *     ボール入数<BR>
 *     ボールITF<BR>
 *     発注単位<BR>
 *     ロケーションNo.1<BR>
 *     入数1<BR>
 *     ロケーションNo.2<BR>
 *     入数2<BR>
 *     ロケーションNo.3<BR>
 *     入数3<BR>
 *     ロケーションNo.4<BR>
 *     入数4<BR>
 *     賞味期限<BR>
 *     入荷限度日<BR>
 *     出荷限度日<BR>
 *     最新賞味期限<BR>
 *     最新製造日<BR>
 *     最新出庫日<BR>
 *     最新在庫<BR>
 *     最古在庫<BR>
 *     管理フラグ<BR>
 *     上限在庫数<BR>
 *     下限在庫数<BR>
 *     最終更新日時<BR>
 *     最終使用日時<BR>
 *     処理フラグ<BR>
 *     途中作業区分<BR>
 *     作業区分<BR>
 *     データ区分<BR>
 *     パッケージ選択<BR>
 *     取込ファイル数<BR>
 *     取込開始日時<BR>
 *     開始終了フラグ<BR>
 *     ホスト取消区分<BR>
 *     取込単位キー<BR>
 *     リストセル行No.<BR>
 *     出庫予定日<BR>
 *     伝票No.<BR>
 *     行No.<BR>
 *     作業枝番<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     ロットNo.<BR>
 *     ケース入数<BR>
 *     予定数<BR>
 *     出荷先コード<BR>
 *     出荷先名称<BR>
 *     出荷先分類コード<BR>
 *     得意先コード<BR>
 *     得意先名称<BR>
 *     オーダーNo.<BR>
 *     バッチNo.<BR>
 *     出庫予定一意キー<BR>
 *     エリアNo.<BR>
 *     棚<BR>
 *     棚(To)<BR>
 *     作業日<BR>
 *     作業日(to)<BR>
 *     ゾーンNo.<BR>
 *     ゾーン名称<BR>
 *     システム管理区分<BR>
 *     管理フラグ<BR>
 *     範囲指定フラグ<BR>
 *     ﾛｯﾄ/H<BR>
 *     ｵｰﾀﾞｰ/H<BR>
 *     行/H<BR>
 *     Aランク基準値<BR>
 *     Bランク基準値<BR>
 *     エリア名称<BR>
 *     棚表示形式<BR>
 *     登録日時<BR>
 *     登録処理名<BR>
 *     最終更新日時(Date)<BR>
 *     最終更新処理名<BR>
 *     基準日<BR>
 *     アイテム情報コメント<BR>
 *     オーダー情報コメント<BR>
 *     RFTNo.<BR>
 *     スキャンコード<BR>
 *     単重量<BR>
 *     単重量(From)<BR>
 *     単重量(TO)<BR>
 *     重量誤差率<BR>
 *     最大検品単位数<BR>
 *     メッセージ<BR>
 *     作業ゾーンNo.<BR>
 *     更新値作業ゾーンNo.<BR>
 *     更新値ゾーンNo.<BR>
 *     更新値開始棚No.<BR>
 *     更新値終了棚No.<BR>
 *     最大作業数<BR>
 *     商品画像(あり・なし)<BR>
 *     作業優先度<BR>
 *     ロット入数フラグ<BR>
 *     PCT商品マスタ取込みフラグ<BR>
 * </DIR>
 * 
 * Designer : E.Takeda <BR>
 * Maker : E.Takeda <BR>
 *
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  $Author: arai $
 */
public class PCTMasterInParameter
        extends InParameter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 検索対象テーブル : PCT商品マスタ
     */
    public static final String SEARCH_TABLE_ITEM = "1";

    /**
     * 検索対象テーブル : 荷主マスタ
     */
    public static final String SEARCH_TABLE_CONSIGNOR = "2";

    /**
     * 検索対象テーブル：予定情報
     */
    public static final String SEARCH_TABLE_PLANINFO = "3";

    /**
     * 検索対象テーブル：PCT出荷マスタ
     */
    public static final String SEARCH_TABLE_CUSTOMER = "4";

    /**
     * 検索対象テーブル：重量差異情報
     */
    public static final String SEARCH_TABLE_WEIGTHDISTINCT = "5";

    /**
     * 状態フラグ：削除
     */
    public static final String STATUS_FLAG_DELETE = "9";

    /**
     * CSV処理フラグ : CSV
     */
    public static final String PRINT_CSV = "1";


    // Class variables -----------------------------------------------
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
     * JANコード
     */
    private String _jan;

    /**
     * 商品名称
     */
    private String _itemName;

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
    private java.util.Date _lastUpdateDate;

    /**
     * 最終使用日時
     */
    private java.util.Date _lastUsedDate;

    /**
     * 処理フラグ
     */
    private String _processFlag;

    /**
     * 途中作業区分
     */
    private String _workOnTheWay;

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
     * 取込ファイル数
     */
    private int _loadFiles_count;

    /**
     * 取込開始日時
     */
    private java.util.Date _loadStartDate;

    /**
     * ホスト取消区分
     */
    private String _cancelFlag;

    /**
     * 取込単位キー
     */
    private String _loadUnitKey;

    /**
     * 出庫予定日
     */
    private String _retrievalPlanDay;

    /**
     * リストセル行No.
     */
    private int _rowNo;

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
     * JANコード
     */
    private String _janCode;

    /**
     * ロットNo.
     */
    private String _lotNo;

    /**
     * 予定数
     */
    private int _planQty;

    /**
     * 出荷先コード
     */
    private String _customerCode;

    /**
     * 出荷先名称
     */
    private String _customerName;

    /**
     * 出荷先分類コード
     */
    private String _customerCategory;

    /**
     * 得意先コード
     */
    private String _regularCustomerCode;

    /**
     * 得意先名称
     */
    private String _regularCustomerName;

    /**
     * オーダーNo.
     */
    private String _orderNo;

    /**
     * バッチNo.
     */
    private String _batchNo;

    /**
     * 出庫予定一意キー
     */
    private String _planUkey;

    /**
     * 開始終了フラグ
     */
    private String _fromToFlag;

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
     * 作業日(to)
     */
    private String _toWorkDate;

    /**
     * ゾーンNo.
     */
    private String _zoneNo;

    /**
     * ゾーン名称
     */
    private String _zoneName;

    /**
     * ボタン区分
     */
    private String _buttonKubn;

    /**
     * PCT入荷予定データ格納フォルダ
     */
    private String _pctRetrievalLoadData_Folder;

    /**
     * PCT出庫予定データ格納フォルダ
     */
    private String _pctInstockLoadData_Folder;

    /**
     * PCT商品マスタデータ格納フォルダ
     */
    private String _pctItemLoadData_Folder;

    /**
     * PCT商品マスタデータ格納フォルダ(報告)
     */
    private String _pctItemReportData_Folder;

    /**
     * PCT入荷予定データファイル名
     */
    private String _pctRetrievalLoadData_Filename;

    /**
     * PCT出庫予定データファイル名
     */
    private String _pctInstockLoadData_Filename;

    /**
     * PCT商品マスタデータファイル名
     */
    private String _pctItemLoadData_Filename;

    /**
     * PCT商品マスタデータファイル名(報告)
     */
    private String _pctItemReportData_Filename;

    /**
     * PCT出庫実績格納フォルダ
     */
    private String _pctRetrievalReport_Folder;

    /**
     * PCT入荷実績格納フォルダ
     */
    private String _pctInstockReport_Folder;

    /**
     * PCT出庫実績格納ファイル名
     */
    private String _pctRetrievalReport_Filename;

    /**
     * PCT入荷実績格納ファイル名
     */
    private String _pctInstockReport_Filename;

    /**
     * PCT入荷実績格納単位
     */
    private String _pctInstockReport_Type;

    /**
     * PCT入荷実績_選択
     */
    private boolean _selectPctInstockReport;

    /**
     * PCT出庫実績_選択
     */
    private boolean _selectPctRetrievalReport;

    /**
     * PCT棚卸実績_選択
     */
    private boolean _selectPctInventoryReport;

    /**
     * PCT出庫実績単位
     */
    private String _pctRetrievalReport_Type;

    /**
     * PCT棚卸実績フォルダ名
     */
    private String _pctInventoryReport_Folder;

    /**
     * PCT棚卸実績ファイル名
     */
    private String _pctInventoryReport_Filename;

    /**
     * PCT出庫予定格納ファイル名
     */
    private String _pctRetrievalLoad_Filename;

    /**
     * PCT入庫予定格納ファイル名
     */
    private String _pctInstockLoad_Filename;

    /**
     * システム管理区分
     */
    private String _managementType;

    /**
     * 管理フラグ
     */
    private String _managementFlag;

    /**
     * 範囲指定フラグ
     */
    private boolean _rangeFlag;

    /**
     * ﾛｯﾄ/H
     */
    private double _lotPerHour;

    /**
     * ｵｰﾀﾞｰ/H
     */
    private double _orderPerHour;

    /**
     * 行/H
     */
    private double _itemPerHour;

    /**
     * Aランク基準値
     */
    private int _aRankStandardValue;

    /**
     * Bランク基準値
     */
    private int _bRankStandardValue;

    /**
     * エリア名称
     */
    private String _areaName;

    /**
     * 棚表示形式
     */
    private String _locationStyle;

    /**
     * 登録日時
     */
    private java.util.Date _registDate;

    /**
     * 登録処理名
     */
    private String _registPname;

    /**
     * 最終更新日時(Date)
     */
    private java.util.Date _lastUpdate;

    /**
     * 最終更新処理名
     */
    private String _lastUpdatePname;

    /**
     * 表示条件
     */
    private String _displayCondition;

    /**
     * 作業日
     */
    private String _workDay;

    /**
     * 基準日
     */
    private String _useByDate;

    /**
     * アイテム情報コメント
     */
    private String _itemInfo;

    /**
     * オーダー情報コメント
     */
    private String _orderInfo;

    /**
     * ファイルパス
     */
    private String _filePath;

    /**
     * ファイル名称
     */
    private String _fileName;

    /**
     * データ区分 : 仕入先マスタ
     */
    public static final String DATA_TYPE_SUPPLIER_MASTER = "07";

    /**
     * データ区分 : 出荷先マスタ
     */
    public static final String DATA_TYPE_CUSTOMER_MASTER = "08";

    /**
     * データ区分 : 商品マスタ
     */
    public static final String DATA_TYPE_ITEM_MASTER = "09";

    /**
     * ファイルパス最大文字数
     */
    public static final int FILE_PATH_MAXLENGTH = 255;

    /**
     * システム管理区分 : ユーザ
     */
    public static final String MANAGEMENT_TYPE_USER = "0";

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
     * 検索時刻(to)
     */
    private String _toSearchTime;

    /**
     * RFTNo.(端末No.)
     */
    private String _rftNo;

    /**
     * スキャンコード
     */
    private String _scanCode;

    /** 
     * 検索対象テーブル
     */
    private String _searchTable;

    /** 
     * 単重量
     */
    private Double _singleWeight;

    /** 
     * 単重量(From)
     */
    private String _singleWeightFrom;

    /** 
     * 単重量(TO)
     */
    private String _singleWeightTo;

    /** 
     * 重量誤差率
     */
    private int _weightDistinctRate;

    /** 
     * 最大検品単位数
     */
    private int _maxInspectioUnitQty;

    /** 
     * メッセージ
     */
    private String _information;

    /**
     * 作業ゾーンNo.
     */
    private String _workingZoneNo;

    /**
     * 更新値作業ゾーンNo.
     */
    private String _updateWorkingZoneNo;

    /**
     * 更新値ゾーンNo.
     */
    private String _updateZoneNo;

    /**
     * 更新値開始棚No.
     */
    private String _updateStartLocationNo;

    /**
     * 更新値終了棚No.
     */
    private String _updateEndLocationNo;

    /**
     * 最大作業数
     */
    private int _maxWorkCnt;

    /**
     * 商品画像(あり・なし)
     */
    private boolean _pictureFlag;

    /**
     * 商品コード(to)
     */
    private String _toItemCode;

    /**
     * 範囲指定フラグ(最終更新日時)
     */
    private boolean _lastUpdateRangeFlag;

    /**
     * 作業優先度
     */
    private String _customerPriority;

    /**
     * CSV処理フラグ
     */
    private String _printCsv;

    /**
     * ロット入数フラグ
     */
    private boolean _lotEnteringQtyFlag;

    /***
     * PCT商品マスタ取込みフラグ
     */
    private String _pctMasterLoadFlag;

    // Public methods ------------------------------------------------

    /**
     * lastUpdatePnameを設定します。
     * @param lastUpdatePname lastUpdatePname
     */
    public void setLastUpdatePname(String lastUpdatePname)
    {
        _lastUpdatePname = lastUpdatePname;
    }

    /**
     * aRankStandardValueを返します。
     * @return aRankStandardValueを返します。
     */
    public int getARankStandardValue()
    {
        return _aRankStandardValue;
    }

    /**
     * aRankStandardValueを設定します。
     * @param rankStandardValue aRankStandardValue
     */
    public void setARankStandardValue(int rankStandardValue)
    {
        _aRankStandardValue = rankStandardValue;
    }

    /**
     * bRankStandardValueを返します。
     * @return bRankStandardValueを返します。
     */
    public int getBRankStandardValue()
    {
        return _bRankStandardValue;
    }

    /**
     * bRankStandardValueを設定します。
     * @param rankStandardValue bRankStandardValue
     */
    public void setBRankStandardValue(int rankStandardValue)
    {
        _bRankStandardValue = rankStandardValue;
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
    public String getManagementFlg()
    {
        return _managementFlg;
    }

    /**
     * 管理フラグを設定します。
     * @param managementFlg 管理フラグ
     */
    public void setManagementFlg(String managementFlg)
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
     * 最終使用日を返します。
     * @return 最終使用日を返します。
     */
    public java.util.Date getLastUsedDate()
    {
        return _lastUsedDate;
    }

    /**
     * 最終使用日を設定します。
     * @param lastUsedDate 最終使用日
     */
    public void setLastUsedDate(java.util.Date lastUsedDate)
    {
        _lastUsedDate = lastUsedDate;
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
     * 出荷先コードを設定します。
     * @param customerCode 出荷先コード
     */
    public void setCustomerCode(String customerCode)
    {
        _customerCode = customerCode;
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
     * 出荷先名称を設定します。
     * @param customerName 出荷先名称
     */
    public void setCustomerName(String customerName)
    {
        _customerName = customerName;
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
     * 出荷先分類を設定します。
     * @param customerCategory 出荷先分類
     */
    public void setCustomerCategory(String customerCategory)
    {
        _customerCategory = customerCategory;
    }

    /**
     * 出荷先分類を返します。
     * @return 出荷先分類を返します。
     */
    public String getCustomerCategory()
    {
        return _customerCategory;
    }

    /**
     * 得意先コードを設定します。
     * @param regularCustomerCode 得意先コード
     */
    public void setRegularCustomerCode(String regularCustomerCode)
    {
        _regularCustomerCode = regularCustomerCode;
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
     * 得意先名称を設定します。
     * @param RegularCustomerName 得意先名称
     */
    public void setRegularCustomerName(String RegularCustomerName)
    {
        _regularCustomerName = RegularCustomerName;
    }

    /**
     * @return 得意先名称を返します。
     * 得意先名称を返します。
     */
    public String getRegularCustomerName()
    {
        return _regularCustomerName;
    }

    /**
     * オーダーNoを設定します。
     * @param orderNo オーダーNo
     */
    public void setOrderNo(String orderNo)
    {
        _orderNo = orderNo;
    }

    /**
     * オーダーNoを返します。
     * @return オーダーNo.
     */
    public String getOrderNo()
    {
        return _orderNo;
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
     * 出庫予定一意キーを設定します。
     * @param planUkey 出庫予定一意キー
     */
    public void setPlanUkey(String planUkey)
    {
        _planUkey = planUkey;
    }

    /**
     * 出庫予定一意キーを返します。
     * @return 出庫予定一意キーを返します。
     */
    public String getPlanUkey()
    {
        return _planUkey;
    }

    /**
     * 開始終了フラグを返します。
     * @return 開始終了フラグを返します。
     */
    public String getFromToFlag()
    {
        return _fromToFlag;
    }

    /**
     * 開始終了フラグを設定します。
     * @param fromToFlag 開始終了フラグ
     */
    public void setFromToFlag(String fromToFlag)
    {
        _fromToFlag = fromToFlag;
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
     * @param workDay 作業日
     */
    public void setWorkDate(String workDay)
    {
        _workDate = workDay;
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
     * ゾーン品名称を返します。
     * @return 商品名称を返します。
     */
    public String getZoneName()
    {
        return _zoneName;
    }

    /**
     * ゾーン品名称を設定します。
     * @param zoneName ゾーン名称
     */
    public void setZoneName(String zoneName)
    {
        _zoneName = zoneName;
    }

    /**
     * ボタン区分を返します。
     * @return ボタン区分を返します。
     */
    public String getButtonKubn()
    {
        return _buttonKubn;
    }

    /**
     * ボタン区分を設定します。
     * @param buttonKubn ボタン区分
     */
    public void setButtonKubn(String buttonKubn)
    {
        _buttonKubn = buttonKubn;
    }

    /**
     * PCT入荷予定データ格納フォルダを返します。
     * @return PCT入荷予定データ格納フォルダを返します。
     */
    public String getPCTInstockLoadData_Folder()
    {
        return _pctInstockLoadData_Folder;
    }

    /**
     * PCT入荷予定データ格納フォルダを設定します。
     * @param pctInstockLoadData_Folder PCT入荷予定データ格納フォルダ
     */
    public void setPCTInstockLoadData_Folder(String pctInstockLoadData_Folder)
    {
        _pctInstockLoadData_Folder = pctInstockLoadData_Folder;
    }

    /**
     * PCT出庫予定データ格納フォルダを返します。
     * @return PCT出庫予定データ格納フォルダを返します。
     */
    public String getPCTRetrievalLoadData_Folder()
    {
        return _pctRetrievalLoadData_Folder;
    }

    /**
     * PCT出庫予定データ格納フォルダを設定します。
     * @param pctRetrievalLoadData_Folder PCT出庫予定データ格納フォルダ
     */
    public void setPCTRetrievalLoadData_Folder(String pctRetrievalLoadData_Folder)
    {
        _pctRetrievalLoadData_Folder = pctRetrievalLoadData_Folder;
    }

    /**
     * PCT商品マスタデータ格納フォルダを返します。
     * @return PCT商品マスタデータ格納フォルダを返します。
     */
    public String getPCTItemLoadData_Folder()
    {
        return _pctItemLoadData_Folder;
    }

    /**
     * PCT商品マスタデータ格納フォルダを設定します。
     * @param pctItemLoadData_Folder PCT商品マスタデータ格納フォルダ
     */
    public void setPCTItemLoadData_Folder(String pctItemLoadData_Folder)
    {
        _pctItemLoadData_Folder = pctItemLoadData_Folder;
    }

    /**
     * PCT入荷予定データファイル名を返します。
     * @return PCT入荷予定データファイル名を返します。
     */
    public String getPCTInstockLoadData_Filename()
    {
        return _pctInstockLoadData_Filename;
    }

    /**
     * PCT入荷予定データファイル名を設定します。
     * @param pctInstockLoadData_Filename PCT入荷予定データファイル名
     */
    public void setPCTInstockLoadData_Filename(String pctInstockLoadData_Filename)
    {
        _pctInstockLoadData_Filename = pctInstockLoadData_Filename;
    }

    /**
     * PCT出庫予定データファイル名を返します。
     * @return PCT出庫予定データファイル名を返します。
     */
    public String getPCTRetrievalLoadData_Filename()
    {
        return _pctRetrievalLoadData_Filename;
    }

    /**
     * PCT出庫予定データファイル名を設定します。
     * @param pctRetrievalLoadData_Filename PCT出庫予定データファイル名
     */
    public void setPCTRetrievalLoadData_Filename(String pctRetrievalLoadData_Filename)
    {
        _pctRetrievalLoadData_Filename = pctRetrievalLoadData_Filename;
    }

    /**
     * PCT商品マスタデータファイル名を返します。
     * @return PCT商品マスタデータファイル名を返します。
     */
    public String getPCTItemLoadData_Filename()
    {
        return _pctItemLoadData_Filename;
    }

    /**
     * PCT商品マスタデータファイル名を設定します。
     * @param pctItemLoadData_Filename PCT商品マスタデータファイル名
     */
    public void setPCTItemLoadData_Filename(String pctItemLoadData_Filename)
    {
        _pctItemLoadData_Filename = pctItemLoadData_Filename;
    }

    /**
     * PCT出庫実績格納フォルダを返します。
     * @return PCT出庫実績格納フォルダを返します。
     */
    public String getPCTRetrievalReport_Folder()
    {
        return _pctRetrievalReport_Folder;
    }

    /**
     * PCT出庫実績格納フォルダを設定します。
     * @param pctRetrievalReport_Folder PPCT出庫実績格納フォルダ
     */
    public void setPCTRetrievalReport_Folder(String pctRetrievalReport_Folder)
    {
        _pctRetrievalReport_Folder = pctRetrievalReport_Folder;
    }

    /**
     * PCT入荷実績格納フォルダを返します。
     * @return PCT入荷実績格納フォルダを返します。
     */
    public String getPCTInstockReport_Folder()
    {
        return _pctInstockReport_Folder;
    }

    /**
     * PCT入荷実績格納フォルダを設定します。
     * @param pctInstockReport_Folder PPCT入荷実績格納フォルダ
     */
    public void setPCTInstockReport_Folder(String pctInstockReport_Folder)
    {
        _pctInstockReport_Folder = pctInstockReport_Folder;
    }

    /**
     * PCT出庫実績格納ファイル名を返します。
     * @return PCT出庫実績格納ファイル名を返します。
     */
    public String getPCTRetrievalReport_Filename()
    {
        return _pctRetrievalReport_Filename;
    }

    /**
     * PCT出庫実績格納ファイル名を設定します。
     * @param pctRetrievalReportFilename PCT出庫実績格納ファイル名
     */
    public void setPCTRetrievalReport_Filename(String pctRetrievalReportFilename)
    {
        _pctRetrievalReport_Filename = pctRetrievalReportFilename;
    }

    /**
     * PCT入荷実績格納ファイル名を返します。
     * @return PCT入荷実績格納ファイル名を返します。
     */
    public String getPCTInstockReport_Filename()
    {
        return _pctInstockReport_Filename;
    }

    /**
     * PCT入荷実績格納ファイル名を設定します。
     * @param pctInstockReportFilename PCT入荷実績格納ファイル名
     */
    public void setPCTInstockReport_Filename(String pctInstockReportFilename)
    {
        _pctInstockReport_Filename = pctInstockReportFilename;
    }

    /**
     * PCT商品マスタデータ格納フォルダを返します。
     * @return PCT商品マスタデータ格納フォルダを返します。
     */
    public String getPCTItemReportData_Folder()
    {
        return _pctItemReportData_Folder;
    }

    /**
     * PCT商品マスタデータ格納フォルダを設定します。
     * @param pctItemReportData_Folder PCT商品マスタデータ格納フォルダ
     */
    public void setPCTItemReportData_Folder(String pctItemReportData_Folder)
    {
        _pctItemReportData_Folder = pctItemReportData_Folder;
    }

    /**
     * PCT商品マスタデータファイル名を返します。
     * @return PCT商品マスタデータファイル名を返します。
     */
    public String getPCTItemReportData_Filename()
    {
        return _pctItemReportData_Filename;
    }

    /**
     * PCT商品マスタデータファイル名を設定します。
     * @param pctItemReportData_Filename PCT商品マスタデータファイル名
     */
    public void setPCTItemReportData_Filename(String pctItemReportData_Filename)
    {
        _pctItemReportData_Filename = pctItemReportData_Filename;
    }

    /**
     * PCT入荷報告単位を返します。
     * @return PCT入荷報告単位を返します。
     */
    public String getPCTInstockReport_Type()
    {
        return _pctInstockReport_Type;
    }

    /**
     * PCT入荷報告単位を設定します。
     * @param pctInstockReportType PCT入荷報告単位
     */
    public void setPCTInstockReport_Type(String pctInstockReportType)
    {
        _pctInstockReport_Type = pctInstockReportType;
    }

    /**
     * PCT入荷実績_選択を返します。
     * @return PCT入荷実績_選択を返します。
     */
    public boolean isSelectPCTInstockReport()
    {
        return _selectPctInstockReport;
    }

    /**
     * PCT棚卸実績_選択を返します。
     * @return PCT入荷実績_選択を返します。
     */
    public boolean isSelectPCTInventoryReport()
    {
        return _selectPctInventoryReport;
    }

    /**
     * PCT出庫実績_選択を返します。
     * @return PCT出庫実績_選択を返します。
     */
    public boolean isSelectPCTRetrievalReport()
    {
        return _selectPctRetrievalReport;
    }

    /**
     * PCT入荷実績_選択を設定します。
     * @param selectPctInstockReport PCT入荷実績_選択
     */
    public void setSelectPCTInstockReport(boolean selectPctInstockReport)
    {
        _selectPctInstockReport = selectPctInstockReport;
    }

    /**
     * PCT出庫実績_選択を設定します。
     * @param selectPctRetrievalReport PCT出庫実績_選択
     */
    public void setSelectPCTRetrievalReport(boolean selectPctRetrievalReport)
    {
        _selectPctRetrievalReport = selectPctRetrievalReport;
    }

    /**
     * PCT棚卸実績_選択を設定します。
     * @param selectPctInventoryReport PCT出庫実績_選択
     */
    public void setSelectPCTInventoryReport(boolean selectPctInventoryReport)
    {
        _selectPctInventoryReport = selectPctInventoryReport;
    }

    /**
     * PCT出庫報告単位を返します。
     * @return PCT出庫報告単位を返します。
     */
    public String getPCTRetrievalReport_Type()
    {
        return _pctRetrievalReport_Type;
    }

    /**
     * PCT出庫報告単位を設定します。
     * @param pctRetrievalReportType PCT出庫報告単位
     */
    public void setPCTRetrievalReport_Type(String pctRetrievalReportType)
    {
        _pctRetrievalReport_Type = pctRetrievalReportType;
    }

    /**
     * PCT棚卸実績格納フォルダを返します。
     * @return 棚卸実績格納フォルダを返します。
     */
    public String getPCTInventoryReport_Folder()
    {
        return _pctInventoryReport_Folder;
    }

    /**
     * PCT棚卸実績格納フォルダを設定します。
     * @param pctInventoryReportFolder 棚卸実績格納フォルダ
     */
    public void setPCTInventoryReport_Folder(String pctInventoryReportFolder)
    {
        _pctInventoryReport_Folder = pctInventoryReportFolder;
    }

    /**
     * PCT棚卸実績格納ファイル名を設定します。
     * @param pctInventoryReportFilename 棚卸実績格納ファイル名
     */
    public void setPCTInventoryReport_Filename(String pctInventoryReportFilename)
    {
        _pctInventoryReport_Filename = pctInventoryReportFilename;
    }

    /**
     * PCT棚卸実績格納ファイル名を返します。
     * @return PCT棚卸実績格納ファイル名を返します。
     */
    public String getPCTInventoryReport_Filename()
    {
        return _pctInventoryReport_Filename;
    }

    /**
     * PCT出庫予定格納ファイル名を設定します。
     * @param pctRetrievalLoadFilename 出庫実績格納ファイル名
     */
    public void setPCTRetrievalLoad_Filename(String pctRetrievalLoadFilename)
    {
        _pctRetrievalLoad_Filename = pctRetrievalLoadFilename;
    }

    /**
     * PCT出庫実績格納ファイル名を返します。
     * @return PCT棚卸実績格納ファイル名を返します。
     */
    public String getPCTRetrievalLoad_Filename()
    {
        return _pctRetrievalLoad_Filename;
    }

    /**
     * PCT入庫予定格納ファイル名を設定します。
     * @param pctInstockLoadFilename 出庫実績格納ファイル名
     */
    public void setPCTInstockLoad_Filename(String pctInstockLoadFilename)
    {
        _pctInstockLoad_Filename = pctInstockLoadFilename;
    }

    /**
     * PCT入庫実績格納ファイル名を返します。
     * @return PCT入庫実績格納ファイル名を返します。
     */
    public String getPCTInstockLoad_Filename()
    {
        return _pctInstockLoad_Filename;
    }

    /**
     * システム管理区分を設定します。
     * @param managementType システム管理区分
     */
    public void setManagementType(String managementType)
    {
        _managementType = managementType;
    }

    /**
     * システム管理区分を返します。
     * @return システム管理区分を返します。
     */
    public String getManagementType()
    {
        return _managementType;
    }

    /**
     * システム管理フラグを設定します。
     * @param managementFlag システム管理フラグ
     */
    public void setManagementFlag(String managementFlag)
    {
        _managementFlag = managementFlag;
    }

    /**
     * システム管理フラグを返します。
     * @return システム管理フラグを返します。
     */
    public String getManagementFlag()
    {
        return _managementFlag;
    }

    /**
     * 終了作業日を返します。
     * @return 終了作業日を返します。
     */
    public String getToWorkDate()
    {
        return _toWorkDate;
    }

    /**
     * 終了作業日を設定します。
     * @param toWorkDate 終了作業日
     */
    public void setToWorkDate(String toWorkDate)
    {
        _toWorkDate = toWorkDate;
    }

    /**
     * 範囲フラグを返します。
     * @return 範囲フラグを返します。
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
     * 行/Hを返します。
     * @return 行/Hを返します。
     */
    public double getItemPerHour()
    {
        return _itemPerHour;
    }

    /**
     * 行/Hを設定します。
     * @param itemPerHour 行/H
     */
    public void setItemPerHour(double itemPerHour)
    {
        _itemPerHour = itemPerHour;
    }

    /**
     * ﾛｯﾄ/Hを返します。
     * @return ﾛｯﾄ/Hを返します。
     */
    public double getLotPerHour()
    {
        return _lotPerHour;
    }

    /**
     * ﾛｯﾄ/Hを設定します。
     * @param lotPerHour ﾛｯﾄ/H
     */
    public void setLotPerHour(double lotPerHour)
    {
        _lotPerHour = lotPerHour;
    }

    /**
     * ｵｰﾀﾞｰ/Hを返します。
     * @return ｵｰﾀﾞｰ/Hを返します。
     */
    public double getOrderPerHour()
    {
        return _orderPerHour;
    }

    /**
     * ｵｰﾀﾞｰ/Hを設定します。
     * @param orderPerHour ｵｰﾀﾞｰ/H
     */
    public void setOrderPerHour(double orderPerHour)
    {
        _orderPerHour = orderPerHour;
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
     * 登録日時を返します。
     * @return 登録日時を返します。
     */
    public java.util.Date getRegistDate()
    {
        return _registDate;
    }

    /**
     * 登録日時を設定します。
     * @param registDate  登録日時
     */
    public void setRegistDate(java.util.Date registDate)
    {
        _registDate = registDate;
    }

    /**
     * 登録処理名を返します。
     * @return 登録処理名を返します。
     */
    public String getRegistPname()
    {
        return _registPname;
    }

    /**
     * 登録処理名を設定します。
     * @param registPname  登録処理名
     */
    public void setRegistProcess(String registPname)
    {
        _registPname = registPname;
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
     * 最終更新処理名を返します。
     * @return 最終更新処理名を返します。
     */
    public String getLastUpdatePname()
    {
        return _lastUpdatePname;
    }

    /**
     * 最終更新処理名を設定します。
     * @param lastUpdatePname  最終更新処理名
     */
    public void setLastUpdateProcess(String lastUpdatePname)
    {
        _lastUpdatePname = lastUpdatePname;
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
     * 基準日を返します。
     * @return 基準日を返します。
     */
    public String getUseByDate()
    {
        return _useByDate;
    }

    /**
     * 基準日を設定します。
     * @param useByDate 基準日
     */
    public void setUseByDate(String useByDate)
    {
        _useByDate = useByDate;
    }

    /**
     * アイテム情報コメントを返します。
     * @return アイテム情報コメントを返します。
     */
    public String getItemInfo()
    {
        return _itemInfo;
    }

    /**
     * アイテム情報コメントを設定します。
     * @param itemInfo アイテム情報コメント
     */
    public void setItemInfo(String itemInfo)
    {
        _itemInfo = itemInfo;
    }

    /**
     * オーダー情報コメントを設定します。
     * @param orderInfo オーダー情報コメント
     */
    public void setOrderInfo(String orderInfo)
    {
        _orderInfo = orderInfo;
    }

    /**
     * オーダー情報コメントを返します。
     * @return オーダー情報コメントを返します。
     */
    public String getOrderInfo()
    {
        return _orderInfo;
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
     * 検索日を返します。
     * @return 検索日を返します。
     */
    public String getSearchDay()
    {
        return _searchDay;
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
     * RFTNo.を返します、
     * @return RFTNo.を返します、
     */
    public String getRftNo()
    {
        return _rftNo;
    }

    /**
     * RFTNo.を設定します、
     * @param rftNo RFTNo.
     */
    public void setRftNo(String rftNo)
    {
        _rftNo = rftNo;
    }

    /**
     * スキャンコードを返します。
     * @return スキャンコードを返します。
     */
    public String getScanCode()
    {
        return _scanCode;
    }

    /**
     * スキャンコードを設定します。
     * @param scanCode スキャンコード
     */
    public void setScanCode(String scanCode)
    {
        _scanCode = scanCode;
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
     * 検索対象テーブルを返します。
     * @return 検索対象テーブルを返します。
     */
    public String getSearchTable()
    {
        return _searchTable;
    }

    /**
     * 単重量を設定します。
     * @param singleWeight 単重量
     */
    public void setSingleWeight(Double singleWeight)
    {
        _singleWeight = singleWeight;
    }

    /**
     * 単重量を返します。
     * @return 単重量を返します。
     */
    public double getSingleWeight()
    {
        return _singleWeight;
    }

    /**
     * 単重量(From)を設定します。
     * @param singleWeight 単重量(From)
     */
    public void setSingleWeightFrom(String singleWeight)
    {
        _singleWeightFrom = singleWeight;
    }

    /**
     * 単重量(From)を返します。
     * @return 単重量(From)を返します。
     */
    public String getSingleWeightFrom()
    {
        return _singleWeightFrom;
    }

    /**
     * 単重量(To)を設定します。
     * @param singleWeight 単重量(To)
     */
    public void setSingleWeightTo(String singleWeight)
    {
        _singleWeightTo = singleWeight;
    }

    /**
     * 単重量(To)を返します。
     * @return 単重量(To)を返します。
     */
    public String getSingleWeightTo()
    {
        return _singleWeightTo;
    }

    /**
     * 重量誤差率を設定します。
     * @param weightDistinctRate 重量誤差率
     */
    public void setWeightDistinctRate(int weightDistinctRate)
    {
        _weightDistinctRate = weightDistinctRate;
    }

    /**
     * 重量誤差率を返します。
     * @return 重量誤差率を返します。
     */
    public int getWeightDistinctRate()
    {
        return _weightDistinctRate;
    }

    /**
     * 最大検品単位数を設定します。
     * @param maxInspectioUnitQty 最大検品単位数
     */
    public void setMaxInspectioUnitQty(int maxInspectioUnitQty)
    {
        _maxInspectioUnitQty = maxInspectioUnitQty;
    }

    /**
     * 最大検品単位数を返します。
     * @return 最大検品単位数を返します。
     */
    public int getMaxInspectioUnitQty()
    {
        return _maxInspectioUnitQty;
    }

    /**
     * メッセージを設定します。
     * @param information メッセージ
     */
    public void setInformation(String information)
    {
        _information = information;
    }

    /**
     *メッセージを返します。
     * @return メッセージを返します。
     */
    public String getInformation()
    {
        return _information;
    }

    /**
     * 作業ゾーンNoを設定します。
     * @param workingZoneNo 作業ゾーンNo
     */
    public void setWorkingZoneNo(String workingZoneNo)
    {
        _workingZoneNo = workingZoneNo;
    }

    /**
     * 作業ゾーンNoを返します。
     * @return 作業ゾーンNoを返します。
     */
    public String getWorkingZoneNo()
    {
        return _workingZoneNo;
    }

    /**
     * 更新値作業ゾーンNoを設定します。
     * @param workingZoneNo 更新値作業ゾーンNo
     */
    public void setUpdWorkingZoneNo(String workingZoneNo)
    {
        _updateWorkingZoneNo = workingZoneNo;
    }

    /**
     * 更新値作業ゾーンNoを返します。
     * @return 更新値作業ゾーンNoを返します。
     */
    public String getUpdWorkingZoneNo()
    {
        return _updateWorkingZoneNo;
    }

    /**
     * 更新値ゾーンNoを設定します。
     * @param zoneNo 更新値ゾーンNo
     */
    public void setUpdZoneNo(String zoneNo)
    {
        _updateZoneNo = zoneNo;
    }

    /**
     * 更新値ゾーンNoを返します。
     * @return 更新値ゾーンNoを返します。
     */
    public String getUpdZoneNo()
    {
        return _updateZoneNo;
    }

    /**
     * 更新値開始棚Noを設定します。
     * @param locationNo 更新値開始棚No
     */
    public void setUpdStartLocationNo(String locationNo)
    {
        _updateStartLocationNo = locationNo;
    }

    /**
     * 更新値開始棚Noを返します。
     * @return 更新値開始棚Noを返します。
     */
    public String getUpdStartLocationNo()
    {
        return _updateStartLocationNo;
    }

    /**
     * 更新値終了棚Noを設定します。
     * @param locationNo 更新値終了棚No
     */
    public void setUpdEndLocationNo(String locationNo)
    {
        _updateEndLocationNo = locationNo;
    }

    /**
     * 更新値終了棚Noを返します。
     * @return 更新値終了棚Noを返します。
     */
    public String getUpdEndLocationNo()
    {
        return _updateEndLocationNo;
    }

    /**
     * 最大作業数を設定します。
     * @param maxWorkCnt 最大作業数
     */
    public void setMaxWorkCnt(int maxWorkCnt)
    {
        _maxWorkCnt = maxWorkCnt;
    }

    /**
     * 最大作業数を返します。
     * @return 最大作業数を返します。
     */
    public int getMaxWorkCnt()
    {
        return _maxWorkCnt;
    }

    /**
     * 商品画像(あり・なし)を設定します。
     * @param pictureFlag 商品画像(あり・なし)
     */
    public void setPictureFlag(boolean pictureFlag)
    {
        _pictureFlag = pictureFlag;
    }

    /**
     * 商品画像(あり・なし)を返します。
     * @return 商品画像(あり・なし)を返します。
     */
    public boolean getPictureFlag()
    {
        return _pictureFlag;
    }

    /**
     * 商品コードを返します。
     * @return 商品コードを返します。
     */
    public String getToItemCode()
    {
        return _toItemCode;
    }

    /**
     * 商品コードを設定します。
     * @param toItemCode 商品コード
     */
    public void setToItemCode(String toItemCode)
    {
        _toItemCode = toItemCode;
    }

    /**
     * 範囲フラグ(最終更新日時)を返します。
     * @return 範囲フラグ(最終更新日時)を返します。
     */
    public boolean isLastUpdateRangeFlag()
    {
        return _lastUpdateRangeFlag;
    }

    /**
     * 範囲フラグ(最終更新日時)を設定します。
     * @param rangeFlag 範囲フラグ(最終更新日時)
     */
    public void setLastUpdateRangeFlag(boolean rangeFlag)
    {
        _lastUpdateRangeFlag = rangeFlag;
    }

    /**
     * 作業優先度を返します。
     * @return 作業優先度を返します。
     */
    public String getCustimerPriority()
    {
        return _customerPriority;
    }

    /**
     * 作業優先度を設定します。
     * @param custimerPriority 作業優先度
     */
    public void setCustimerPriority(String custimerPriority)
    {
        _customerPriority = custimerPriority;
    }

    /**
     * CSV処理フラグを返します。
     * @return CSV処理フラグを返します。
     */
    public String getPrintCsv()
    {
        return _printCsv;
    }

    /**
     * CSV処理フラグを設定します。
     * @param printCsv CSV処理フラグ
     */
    public void setPrintCsv(String printCsv)
    {
        _printCsv = printCsv;
    }

    /**
     * ロット入数フラグを返します。
     * @return ロット入数フラグを返します。
     */
    public boolean isLotEnteringQtyFlag()
    {
        return _lotEnteringQtyFlag;
    }

    /**
     * ロット入数フラグを設定します。
     * @param enteringQtyFlag ロット入数フラグ
     */
    public void setLotEnteringQtyFlag(boolean enteringQtyFlag)
    {
        _lotEnteringQtyFlag = enteringQtyFlag;
    }

    /**
     * PCT商品マスタ取込みフラグを返します。
     * @return PCT商品マスタ取込みフラグを返します。
     */
    public String getPctMasterLoadFlag()
    {
        return _pctMasterLoadFlag;
    }

    /**
     * PCT商品マスタ取込みフラグを設定します。
     * @param flg PCT商品マスタ取込みフラグ
     */
    public void setPctMasterLoadFlag(String flg)
    {
        _pctMasterLoadFlag = flg;
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

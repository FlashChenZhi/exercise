package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;

/**
 * <CODE>MasterInParameter</CODE>クラスは、マスタパッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスではマスタパッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>MasterInParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     荷主コード<BR>
 *     仕入先コード<BR>
 *     仕入先コード(to)<BR>
 *     仕入先名称<BR>
 *     出荷先コード<BR>
 *     出荷先コード(to)<BR>
 *     出荷先名称<BR>
 *     仕分場所<BR>
 *     商品コード<BR>
 *     商品コード(to)<BR>
 *     商品名称<BR>
 *     ケース入数<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     上限在庫数<BR>
 *     下限在庫数<BR>
 *     処理フラグ<BR>
 *     開始終了フラグ<BR>
 *     システム管理区分<BR>
 *     最終使用日<BR>
 *     検索対象テーブル<BR>
 *     最終更新日時<BR>
 *     範囲指定フラグ<BR>
 *     ファイルパス<BR>
 *     ファイル名称<BR>
 *     取込ファイル数<BR>
 *     バンク<BR>
 *     ベイ<BR>
 *     レベル<BR>
 *     ルート<BR>
 *     郵便番号<BR>
 *     都道府県名<BR>
 *     住所1<BR>
 *     住所2<BR>
 *     TEL<BR>
 *     連絡先1<BR>
 *     連絡先2<BR>
 *     ソフトゾーンID<BR>
 * </DIR>
 * 
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * @version $Revision: 4533 $, $Date: 2009-06-30 10:06:18 +0900 (火, 30 6 2009) $
 * @author  $Author: okayama $
 */
public class MasterInParameter
        extends InParameter
{
    // Class variables -----------------------------------------------
    /**
     * 荷主コード
     */
    private String _consignorCode;

    /**
     * 仕入先コード
     */
    private String _supplierCode;

    /**
     * 仕入先コード(to)
     */
    private String _toSupplierCode;

    /**
     * 仕入先名称
     */
    private String _supplierName;

    /**
     * 出荷先コード
     */
    private String _customerCode;

    /**
     * 出荷先コード(to)
     */
    private String _toCustomerCode;

    /**
     * 出荷先名称
     */
    private String _customerName;

    /**
     * 仕分場所
     */
    private String _sortingPlace;

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
     * ケース入数
     */
    private int _enteringQty;

    /**
     * JANコード
     */
    private String _janCode;

    /**
     * ケースITF
     */
    private String _itf;

    /**
     * 上限在庫数
     */
    private int _upperQty;

    /**
     * 下限在庫数
     */
    private int _lowerQty;

    /**
     * 処理フラグ
     */
    private String _processFlag;

    /**
     * 開始終了フラグ
     */
    private String _fromtoFrag;

    /**
     * システム管理区分
     */
    private String _managementType;

    /**
     * 最終使用日
     */
    private java.util.Date _lastUsedDate;

    /**
     * 検索対象テーブル
     */
    private String _searchTable;

    /**
     * 最終更新日時
     */
    private java.util.Date _lastUpdateDate;

    /**
     * 範囲指定フラグ
     */
    private boolean _rangeFlag;

    /**
     * 取込区分
     */
    private String _loadFlag;

    /**
     * ファイル行No.
     */
    private int _rowNo;

    /**
     * エリアNo
     */
    private String _areaNo;

    /**
     * エリア名称
     */
    private String _areaName;

    /**
     * エリア種別
     */
    private String _areaType;

    /**
     * 棚管理方式
     */
    private String _locationType;

    /**
     * 棚表示方式
     */
    private String _locationStyle;

    /**
     * 仮置エリア移動
     */
    private String _temporaryAreaType;

    /**
     * 移動先仮置きエリア
     */
    private String _temporaryArea;

    /**
     * 入荷エリア
     */
    private String _receivingArea;

    /**
     * 空棚検索方法
     */
    private String _vacantSearchType;

    /**
     * アイルNo
     */
    private int _aisleNo;

    /**
     * 登録日時
     */
    private java.util.Date _registDate;

    /**
     * 登録処理名
     */
    private String _registPname;

    /**
     * 最終更新処理名
     */
    private String _lastUpdatePname;

    /**
     * 棚No
     */
    private String _locationNo;

    /**
     * 棚No.(to)
     */
    private String _toLocation;

    /**
     * 補充点
     */
    private int _replenishmentQty;

    /**
     * 設定区分
     */
    private String _settingDivision;

    /**
     * 取込開始日時
     */
    private java.util.Date _loadStartDate;

    /**
     * データ区分
     */
    private String _dataType;

    /**
     * パッケージ選択
     */
    private boolean _packageSelect;

    /**
     * ファイルパス
     */
    private String _filePath;

    /**
     * ファイル名称
     */
    private String _fileName;

    /**
     * 取込ファイル数
     */
    private int _loadFilesCount;

    /**
     * バンク
     */
    private String _bank;

    /**
     * ベイ
     */
    private String _bay;

    /**
     * レベル
     */
    private String _level;

    /**
     * ルート
     */
    private String _route;

    /**
     * 郵便番号
     */
    private String _postalCode;

    /**
     * 都道府県名
     */
    private String _prefecture;

    /**
     * 住所1
     */
    private String _address1;

    /**
     * 住所2
     */
    private String _address2;

    /**
     * TEL
     */
    private String _telephone;

    /**
     * 連絡先1
     */
    private String _contact1;

    /**
     * 連絡先2
     */
    private String _contact2;

    /**
     * ソフトゾーンID
     */
    private String _softZoneId;

    // Constractor --------------------------------------------------
    /**
     * コンストラクタ <BR>
     * WmsUserInfoを指定します。
     * 
     * @param info WmsUserInfo
     */
    public MasterInParameter(WmsUserInfo info)
    {
        super();
        setWmsUserInfo(info);
    }

    public MasterInParameter()
    {
        // TODO 自動生成されたコンストラクター・スタブ
    }

    // Public methods ------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 4533 $,$Date: 2009-06-30 10:06:18 +0900 (火, 30 6 2009) $");
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
     * 仕入先コードを返します。
     * @return 仕入先コードを返します。
     */
    public String getSupplierCode()
    {
        return _supplierCode;
    }

    /**
     * 仕入先コードを設定します。
     * @param supplierCode 仕入先コード
     */
    public void setSupplierCode(String supplierCode)
    {
        _supplierCode = supplierCode;
    }

    /**
     * 仕入先コード(to)を返します。
     * @return 仕入先コード(to)を返します。
     */
    public String getToSupplierCode()
    {
        return _toSupplierCode;
    }

    /**
     * 仕入先コード(to)を設定します。
     * @param toSupplierCode 仕入先コード(to)
     */
    public void setToSupplierCode(String toSupplierCode)
    {
        _toSupplierCode = toSupplierCode;
    }

    /**
     * 仕入先名称を返します。
     * @return 仕入先名称を返します。
     */
    public String getSupplierName()
    {
        return _supplierName;
    }

    /**
     * 仕入先名称を設定します。
     * @param supplierName 仕入先名称
     */
    public void setSupplierName(String supplierName)
    {
        _supplierName = supplierName;
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
     * 出荷先コード(to)を返します。
     * @return 出荷先コード(to)を返します。
     */
    public String getToCustomerCode()
    {
        return _toCustomerCode;
    }

    /**
     * 出荷先コード(to)を設定します。
     * @param toCustomerCode 出荷先コード(to)
     */
    public void setToCustomerCode(String toCustomerCode)
    {
        _toCustomerCode = toCustomerCode;
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
     * 仕分場所を返します。
     * @return 仕分場所を返します。
     */
    public String getSortingPlace()
    {
        return _sortingPlace;
    }

    /**
     * 仕分場所を設定します。
     * @param sortingPlace 仕分場所
     */
    public void setSortingPlace(String sortingPlace)
    {
        _sortingPlace = sortingPlace;
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
     * システム管理区分を返します。
     * @return システム管理区分を返します。
     */
    public String getManagementType()
    {
        return _managementType;
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
     * 取込区分を返します。
     * @return 取込区分を返します。
     */
    public String getLoadFlag()
    {
        return _loadFlag;
    }

    /**
     * 取込区分を設定します。
     * @param loadFlag 取込区分
     */
    public void setLoadFlag(String loadFlag)
    {
        _loadFlag = loadFlag;
    }

    /**
     * ファイル行No.を返します。
     * @return ファイル行No.
     */
    public int getRowNo()
    {
        return _rowNo;
    }

    /**
     * ファイル行No.を設定します。
     * @param rowNo ファイル行No.
     */
    public void setRowNo(int rowNo)
    {
        _rowNo = rowNo;
    }

    // Public methods ------------------------------------------------

    /**
     * 設定区分を返します。
     * @return 設定区分を返します。
     */
    public String getSettingDivision()
    {
        return _settingDivision;
    }

    /**
     * 設定区分を設定します。
     * @param settingDivision 設定区分
     */
    public void setSettingDivision(String settingDivision)
    {
        _settingDivision = settingDivision;
    }

    /**
     * エリアNoを返します。
     * @return エリアNoを返します。
     */
    public String getAreaNo()
    {
        return _areaNo;
    }

    /**
     * エリアNoを設定します。
     * @param areaNo エリアNo
     */
    public void setAreaNo(String areaNo)
    {
        _areaNo = areaNo;
    }

    /**
     * 棚Noを返します。
     * @return 棚No
     */
    public String getLocation()
    {
        return _locationNo;
    }

    /**
     * 棚Noを設定します。
     * @param locationNo 棚No
     */
    public void setLocation(String locationNo)
    {
        _locationNo = locationNo;
    }

    /**
     * 棚No.(to)を返します。
     * @return 棚No.(to)
     */
    public String getToLocation()
    {
        return _toLocation;
    }

    /**
     * 棚No.(to)を設定します。
     * @param toLocation 終了棚No.
     */
    public void setToLocation(String toLocation)
    {
        _toLocation = toLocation;
    }

    /**
     * 補充点を返します。
     * @return 補充点を返します。
     */
    public int getReplenishmentQty()
    {
        return _replenishmentQty;
    }

    /**
     * 補充点を設定します。
     * @param replenishmentQty  補充点
     */
    public void setReplenishmentQty(int replenishmentQty)
    {
        _replenishmentQty = replenishmentQty;
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
     * @param areaName  エリア名称
     */
    public void setAreaName(String areaName)
    {
        _areaName = areaName;
    }

    /**
     * エリア種別を返します。
     * @return エリア種別を返します。
     */
    public String getAreaType()
    {
        return _areaType;
    }

    /**
     * エリア種別を設定します。
     * @param areaType  エリア種別
     */
    public void setAreaType(String areaType)
    {
        _areaType = areaType;
    }

    /**
     * アイルNoを返します。
     * @return アイルNoを返します。
     */
    public int getAisleNo()
    {
        return _aisleNo;
    }

    /**
     * アイルNoを設定します。
     * @param aisleNo アイルNo
     */
    public void setAisleNo(int aisleNo)
    {
        this._aisleNo = aisleNo;
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
     * 棚表示方式を返します。
     * @return 棚表示方式を返します。
     */
    public String getLocationStyle()
    {
        return _locationStyle;
    }

    /**
     * 棚表示方式を設定します。
     * @param locationStyle  棚表示方式
     */
    public void setLocationStyle(String locationStyle)
    {
        _locationStyle = locationStyle;
    }

    /**
     * 棚管理方式を返します。
     * @return 棚管理方式を返します。
     */
    public String getLocationType()
    {
        return _locationType;
    }

    /**
     * 棚管理方式を設定します。
     * @param locationType  棚管理方式
     */
    public void setLocationType(String locationType)
    {
        _locationType = locationType;
    }

    /**
     * 仮置エリア移動を返します。
     * @return 仮置エリア移動を返します。
     */
    public String getTemporaryAreaType()
    {
        return _temporaryAreaType;
    }

    /**
     * 仮置エリア移動を設定します。
     * @param temporaryAreaType  仮置エリア移動
     */
    public void setTemporaryAreaType(String temporaryAreaType)
    {
        _temporaryAreaType = temporaryAreaType;
    }

    /**
     * 移動先仮置エリアを返します。
     * @return 移動先仮置エリアを返します。
     */
    public String getTemporaryArea()
    {
        return _temporaryArea;
    }

    /**
     * 移動先仮置エリアを設定します。
     * @param temporaryArea  移動先仮置エリア
     */
    public void setTemporaryArea(String temporaryArea)
    {
        _temporaryArea = temporaryArea;
    }

    /**
     * 入荷エリアを返します。
     * @return 入荷エリアを返します。
     */
    public String getReceivingArea()
    {
        return _receivingArea;
    }

    /**
     * 入荷エリアを設定します。
     * @param receivingArea  入荷エリア
     */
    public void setReceivingArea(String receivingArea)
    {
        _receivingArea = receivingArea;
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
     * 空棚検索条件を返します。
     * @return 空棚検索条件を返します。
     */
    public String getVacantSearchType()
    {
        return _vacantSearchType;
    }

    /**
     * 空棚検索条件を設定します。
     * @param vacantSearchType  空棚検索条件
     */
    public void setVacantSearchType(String vacantSearchType)
    {
        _vacantSearchType = vacantSearchType;
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
     * 取込ファイル数を返します。
     * @return 取込ファイル数を返します。
     */
    public int getLoadFilesCount()
    {
        return _loadFilesCount;
    }

    /**
     * 取込ファイル数を設定します。
     * @param loadFilesCount 取込ファイル数
     */
    public void setLoadFilesCount(int loadFilesCount)
    {
        _loadFilesCount = loadFilesCount;
    }

    /**
     * バンクを返します。
     * @return バンクを返します。
     */
    public String getBank()
    {
        return _bank;
    }

    /**
     * バンクを設定します。
     * @param bank バンク
     */
    public void setBank(String bank)
    {
        this._bank = bank;
    }

    /**
     * ベイを返します。
     * @return ベイを返します。
     */
    public String getBay()
    {
        return _bay;
    }

    /**
     * ベイを設定します。
     * @param bay ベイ
     */
    public void setBay(String bay)
    {
        this._bay = bay;
    }

    /**
     * レベルを返します。
     * @return レベルを返します。
     */
    public String getLevel()
    {
        return _level;
    }

    /**
     * レベルを設定します。
     * @param level レベル
     */
    public void setLevel(String level)
    {
        this._level = level;
    }

    /**
     * ルートを返します。
     * @return ルートを返します。
     */
    public String getRoute()
    {
        return _route;
    }

    /**
     * ルートを設定します。
     * @param route ルート
     */
    public void setRoute(String route)
    {
        this._route = route;
    }

    /**
     * 郵便番号を返します。
     * @return 郵便番号を返します。
     */
    public String getPostalCode()
    {
        return _postalCode;
    }

    /**
     * 郵便番号を設定します。
     * @param postalCode 郵便番号
     */
    public void setPostalCode(String postalCode)
    {
        this._postalCode = postalCode;
    }

    /**
     * 都道府県名を返します。
     * @return 都道府県名を返します。
     */
    public String getPrefecture()
    {
        return _prefecture;
    }

    /**
     * 都道府県名を設定します。
     * @param prefecture 都道府県名
     */
    public void setPrefecture(String prefecture)
    {
        this._prefecture = prefecture;
    }

    /**
     * 住所1を返します。
     * @return 住所1を返します。
     */
    public String getAddress1()
    {
        return _address1;
    }

    /**
     * 住所1を設定します。
     * @param address1 住所1
     */
    public void setAddress1(String address1)
    {
        this._address1 = address1;
    }

    /**
     * 住所2を返します。
     * @return 住所2を返します。
     */
    public String getAddress2()
    {
        return _address2;
    }

    /**
     * 住所2を設定します。
     * @param address2 住所2
     */
    public void setAddress2(String address2)
    {
        this._address2 = address2;
    }

    /**
     * TELを返します。
     * @return TELを返します。
     */
    public String getTelephone()
    {
        return _telephone;
    }

    /**
     * TELを設定します。
     * @param telephone TEL
     */
    public void setTelephone(String telephone)
    {
        this._telephone = telephone;
    }

    /**
     * 連絡先1を返します。
     * @return 連絡先1を返します。
     */
    public String getContact1()
    {
        return _contact1;
    }

    /**
     * 連絡先1を設定します。
     * @param contact1 連絡先1
     */
    public void setContact1(String contact1)
    {
        this._contact1 = contact1;
    }

    /**
     * 連絡先2を返します。
     * @return 連絡先2を返します。
     */
    public String getContact2()
    {
        return _contact2;
    }

    /**
     * 連絡先2を設定します。
     * @param contact2 連絡先2
     */
    public void setContact2(String contact2)
    {
        this._contact2 = contact2;
    }

    /**
     * ソフトゾーンIDを返します。
     * @return ソフトゾーンIDを返します。
     */
    public String getSoftZoneId()
    {
        return _softZoneId;
    }

    /**
     * ソフトゾーンIDを設定します。
     * @param softZoneId ソフトゾーンID
     */
    public void setSoftZoneId(String softZoneId)
    {
        this._softZoneId = softZoneId;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

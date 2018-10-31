package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.OutParameter;

/**
 * <CODE>MasterOutParameter</CODE>クラスは、マスタパッケージ内のスケジュール→画面間のパラメータの受渡しに使用します。<BR>
 * このクラスではマスタパッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>MasterOutParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *     仕入先コード<BR>
 *     仕入先名称<BR>
 *     出荷先コード<BR>
 *     出荷先名称<BR>
 *     仕分場所<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     ケース入数<BR>
 *     ボール入数<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     ボールITF<BR>
 *     上限在庫数<BR>
 *     下限在庫数<BR>
 *     最終使用日<BR>
 *     最終更新日時<BR>
 *     取込ファイル数<BR>
 *     log書き込みフラグ<BR>
 * </DIR>
 * 
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class MasterOutParameter
        extends OutParameter
{
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
     * 仕分場所
     */
    private String _sortingPlace;
    
    /**
     * 商品コード
     */
    private String _itemCode;

    /**
     * 商品名称
     */
    private String _itemName;

    /**
     * ケース入数
     */
    private int _enteringQty;

    /**
     * ボール入数
     */
    private int _bundleQty;

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
     * 上限在庫数
     */
    private int _upperQty;

    /**
     * 下限在庫数
     */
    private int _lowerQty;

    /**
     * 最終使用日
     */
    private java.util.Date _lastUsedDay;

    /**
     * 最終更新日時
     */
    private java.util.Date _lastUpdateDate;

    /**
     * エリアNo
     */
    private String _areaNo;

    /**
     * エリア名称
     */
    private String _areaName;

    /**
     * 棚No
     */
    private String _locationNo;

    /**
     * エリア種別
     */
    private String _areaType;

    /**
     * 棚管理方式
     */
    private String _locationType;

    /**
     * 移動先仮置エリア
     */
    private String _temporaryArea;

    /**
     * 入荷エリア
     */
    private String _receivingArea;
    
    /**
     * 空棚検索条件
     */
    private String _vacantSearchType;

    /**
     * 登録日時
     */
    private java.util.Date _registDate;

    /**
     * 登録処理名
     */
    private String _registPname;

    /**
     * 補充点
     */
    private int _replenishmentQty;

    /**
     * アイルNo
     */
    private int _aisleNo;

    /**
     * 取込内容
     */
    private String _loadDataType;

    /**
     * データ区分
     */
    private String _dataType;

    /**
     * パッケージ選択
     */
    private boolean _packageSelect;

    /**
     * 商品固定棚情報格納フォルダ
     */
    private String _fixedLocateLoadData_Folder;

    /**
     * 商品固定棚情報ファイル名
     */
    private String _fixedLocateLoadData_FileName;

    /**
     * 取込ファイル数
     */
    private int _loadFilesCount;

    /**
     * メッセージ
     */
    private String _message;

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
     * ボール入数を返します。
     * @return ボール入数を返します。
     */
    public int getBundleQty()
    {
        return _bundleQty;
    }

    /**
     * ボール入数を設定します。
     * @param bundleQty ボール入数
     */
    public void setBundleQty(int bundleQty)
    {
        _bundleQty = bundleQty;
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
     * 最終使用日を返します。
     * @return 最終使用日を返します。
     */
    public java.util.Date getLastUsedDay()
    {
        return _lastUsedDay;
    }

    /**
     * 最終使用日を設定します。
     * @param lastUsedDay 最終使用日
     */
    public void setLastUsedDate(java.util.Date lastUsedDay)
    {
        _lastUsedDay = lastUsedDay;
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
     * 棚管理方式を返します。
     * @return 棚管理方式を返します。
     */
    public String getLocationType()
    {
        return _locationType;
    }

    /**
     * 棚管理方式を設定します。
     * @param locationType 棚管理方式
     */
    public void setLocationType(String locationType)
    {
        _locationType = locationType;
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
     * @param temporaryArea 移動先仮置エリア
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
     * @param receivingArea 入荷エリア
     */
    public void setReceivingArea(String receivingArea)
    {
        _receivingArea = receivingArea;
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
     * @param vacantSearchType 空棚検索条件
     */
    public void setVacantSearchType(String vacantSearchType)
    {
        _vacantSearchType = vacantSearchType;
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
     * エリア種別を返します。
     * @return エリア種別を返します。
     */
    public String getAreaType()
    {
        return _areaType;
    }

    /**
     * エリア種別を設定します。
     * @param areaType エリア種別
     */
    public void setAreaType(String areaType)
    {
        _areaType = areaType;
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
     * 棚Noを返します。
     * @return 棚Noを返します。
     */
    public String getLocationNo()
    {
        return _locationNo;
    }

    /**
     * 棚Noを設定します。
     * @param locationNo 棚No
     */
    public void setLocationNo(String locationNo)
    {
        _locationNo = locationNo;
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
     * データ区分を返します。
     * @return データ区分を返します。
     */
    public String getDataType()
    {
        return _dataType;
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
     * 商品固定棚情報格納フォルダを返します。
     * @return 商品固定棚情報格納フォルダを返します。
     */
    public String getFixedLocateLoadData_Folder()
    {
        return _fixedLocateLoadData_Folder;
    }

    /**
     * 商品固定棚情報格納フォルダを設定します。
     * @param folder 商品固定棚情報格納フォルダ
     */
    public void setFixedLocateLoadData_Folder(String folder)
    {
        _fixedLocateLoadData_Folder = folder;
    }

    /**
     * 商品固定棚情報ファイル名
     */
    /**
     * 商品固定棚情報格納フォルダを返します。
     * @return 商品固定棚情報格納フォルダを返します。
     */
    public String getFixedLocateLoadData_FileName()
    {
        return _fixedLocateLoadData_FileName;
    }

    /**
     * 商品固定棚情報ファイル名を設定します。
     * @param fileName 商品固定棚情報ファイル名
     */
    public void setFixedLocateLoadData_FileName(String fileName)
    {
        _fixedLocateLoadData_FileName = fileName;
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

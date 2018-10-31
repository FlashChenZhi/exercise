// $Id: InventoryOutParameter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.inventorychk.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.OutParameter;


/**
 * <CODE>InventoryOutParameter</CODE>クラスは、棚卸パッケージ内のスケジュール→画面間のパラメータの受渡しに使用します。<BR>
 * このクラスでは棚卸パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <CODE>StockOutParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     設定単位キー<BR>
 *     スケジュールNo.<BR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *     棚表示形式<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     ボールITF<BR>
 *     ケース入数<BR>
 *     ボール入数<BR>
 *     ロットNo.<BR>
 *     在庫数<BR>
 *     元棚卸数<BR>
 *     マスタパッケージ有無フラグ<BR>
 *     商品情報有無フラグ<BR>
 *     エリアNo<BR>
 *     エリア名称<BR>
 *     棚No
 *     棚No(from)<BR>
 *     棚No(to)<BR>
 *     在庫ケース数<BR>
 *     在庫ピース数<BR>
 *     棚卸ケース数<BR>
 *     棚卸ピース数<BR>
 *     棚卸可能ケース数<BR>
 *     棚卸可能ピース数<BR>
 *     元在庫ケース数<BR>
 *     元在庫ピース数<BR>
 *     マスタ管理導入フラグ<BR>
 *     棚卸開始日時<BR>
 *     作業状態フラグ<BR>
 *     作業No.<BR>
 *     
 * <BR>
 * Designer : M.Sakashita <BR>
 * Maker : M.Sakashita <BR>
 * 
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Last commit: $Author: admin $
 */
public class InventoryOutParameter
        extends OutParameter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * 設定単位キー
     */
    private String _settingUnitKey;

    /**
     *スケジュールNo.
     */
    private String _scheduleNo;

    /**
     * 荷主コード
     */
    private String _consignorCode;

    /**
     * 荷主名称
     */
    private String _consignorName;

    /**
     * 棚表示形式
     */
    private String _locationStyle;

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
     * ケース入数
     */
    private int _enteringQty;

    /**
     * ボール入数
     */
    private int _bundleEnteringQty;

    /**
     * ロットNo.
     */
    private String _lotNo;

    /**
     * 在庫数
     */
    private int _stockQty;

    /**
     * 元棚卸数
     */
    private int _inventoryBaseInspQty;

    /**
     * マスタパッケージ有無フラグ
     */
    private String _masterPackExist;

    /**
     * 商品情報有無フラグ
     */
    private String _itemDataExist;

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
    private String _location;

    /**
     * 棚No(from)
     */
    private String _fromLocation;

    /**
     * 棚No(to)
     */
    private String _toLocation;

    /**
     * 在庫ケース数
     */
    private long _stockCaseQty;

    /**
     * 在庫ピース数
     */
    private long _stockPieceQty;

    /**
     * マスタ管理導入フラグ
     */
    private boolean _masterFlag;

    /**
     * 棚卸ケース数
     */
    private long _resultCaseQty;

    /**
     * 棚卸ピース数
     */
    private long _resultPieceQty;

    /**
     * 在庫情報ケース数
     */
    private long _caseQty;

    /**
     * 在庫情報ピース数
     */
    private long _pieceQty;

    /**
     * 棚卸開始日時
     */
    private java.util.Date _inventStartDate;

    /**
     * 作業状態フラグ
     */
    private String _statusFlag;

    /**
     * 作業No.
     */
    private String _jobNo;

    /**
     * 最終更新日時
     */
    private java.util.Date _lastUpdateDate;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


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
     * スケジュールNo.を返します。
     * @return スケジュールNo.を返します。
     */
    public String getScheduleNo()
    {
        return _scheduleNo;
    }

    /**
     * スケジュールNo.を設定します。
     * @param scheduleNo スケジュールNo.
     */
    public void setScheduleNo(String scheduleNo)
    {
        _scheduleNo = scheduleNo;
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
     * @return ケースITFを返します。
     */
    public String getBundleItf()
    {
        return _bundleItf;
    }

    /**
     * ボールITFを設定します。
     * @param bundleItf ケースITF
     */
    public void setBundleItf(String bundleItf)
    {
        _bundleItf = bundleItf;
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
     * 在庫数を返します。
     * @return 在庫数を返します。
     */
    public int getStockQty()
    {
        return _stockQty;
    }

    /**
     * 在庫数を設定します。
     * @param stockQty 在庫数
     */
    public void setStockQty(int stockQty)
    {
        _stockQty = stockQty;
    }

    /**
     * 元棚卸数を返します。
     * @return 元棚卸数を返します。
     */
    public int getInventoryBaseInspQty()
    {
        return _inventoryBaseInspQty;
    }

    /**
     * 元棚卸数を設定します。
     * @param inventoryBaseInspQty 元棚卸数
     */
    public void setInventoryBaseInspQty(int inventoryBaseInspQty)
    {
        _inventoryBaseInspQty = inventoryBaseInspQty;
    }

    /**
     * マスタパッケージ有無フラグを返します。
     * @return マスタパッケージ有無フラグを返します。
     */
    public String getMasterPackExist()
    {
        return _masterPackExist;
    }

    /**
     * マスタパッケージ有無フラグを設定します。
     * @param masterPackExist マスタパッケージ有無フラグ
     */
    public void setMasterPackExist(String masterPackExist)
    {
        _masterPackExist = masterPackExist;
    }

    /**
     * 商品情報有無フラグを返します。
     * @return 商品情報有無フラグを返します。
     */
    public String getItemDataExist()
    {
        return _itemDataExist;
    }

    /**
     * 商品情報有無フラグを設定します。
     * @param itemDataExist 商品情報有無フラグ
     */
    public void setItemDataExist(String itemDataExist)
    {
        _itemDataExist = itemDataExist;
    }

    /**
     * エリアを返します。
     * @return エリアを返します。
     */
    public String getAreaNo()
    {
        return _areaNo;
    }

    /**
     * エリアを設定します。
     * @param areaNo エリア
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
     * 棚(from)を返します。
     * @return 棚(from)を返します。
     */
    public String getFromLocation()
    {
        return _fromLocation;
    }

    /**
     * 棚(from)を設定します。
     * @param fromLocation 棚(from)
     */
    public void setFromLocation(String fromLocation)
    {
        _fromLocation = fromLocation;
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
     * 在庫ケース数を返します。
     * @return 在庫ケース数を返します。
     */
    public long getStockCaseQty()
    {
        return _stockCaseQty;
    }

    /**
     * 在庫ケース数を設定します。
     * @param stockCaseQty 在庫ケース数
     */
    public void setStockCaseQty(long stockCaseQty)
    {
        _stockCaseQty = stockCaseQty;
    }

    /**
     * 在庫ピース数を返します。
     * @return 在庫ピース数を返します。
     */
    public long getStockPieceQty()
    {
        return _stockPieceQty;
    }

    /**
     * 在庫ピース数を設定します。
     * @param stockPieceQty 在庫ピース数
     */
    public void setStockPieceQty(long stockPieceQty)
    {
        _stockPieceQty = stockPieceQty;
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
     * 棚卸ケース数を返します。
     * @return 棚卸ケース数を返します。
     */
    public long getResultCaseQty()
    {
        return _resultCaseQty;
    }

    /**
     * 棚卸ケース数を設定します。
     * @param resultCaseQty 棚卸ケース数
     */
    public void setResultCaseQty(long resultCaseQty)
    {
        _resultCaseQty = resultCaseQty;
    }

    /**
     * 棚卸ピース数を返します。
     * @return 棚卸ピース数を返します。
     */
    public long getResultPieceQty()
    {
        return _resultPieceQty;
    }

    /**
     * 棚卸ピース数を設定します。
     * @param resultPieceQty 棚卸ピース数
     */
    public void setResultPieceQty(long resultPieceQty)
    {
        _resultPieceQty = resultPieceQty;
    }

    /**
     * 在庫情報ケース数を返します。
     * @return 在庫情報ケース数を返します。
     */
    public long getCaseQty()
    {
        return _caseQty;
    }

    /**
     * 在庫情報ケース数を設定します。
     * @param caseQty 在庫情報ケース数
     */
    public void setCaseQty(long caseQty)
    {
        _caseQty = caseQty;
    }

    /**
     * 在庫情報ピース数を返します。
     * @return 在庫情報ピース数を返します。
     */
    public long getPieceQty()
    {
        return _pieceQty;
    }

    /**
     * 在庫情報ピース数を設定します。
     * @param pieceQty 在庫情報ピース数
     */
    public void setPieceQty(long pieceQty)
    {
        _pieceQty = pieceQty;
    }

    /**
     * 棚卸開始日時を返します。
     * @return 棚卸開始日時を返します。
     */
    public java.util.Date getInventStartDate()
    {
        return _inventStartDate;
    }

    /**
     * 棚卸開始日時を設定します。
     * @param startDate 棚卸開始日時
     */
    public void setInventStartDate(java.util.Date startDate)
    {
        _inventStartDate = startDate;
    }

    /**
     * 作業状態フラグを返します。
     * @return 作業状態フラグを返します。
     */
    public String getStatusFlag()
    {
        return _statusFlag;
    }

    /**
     * 作業状態フラグを設定します。
     * @param statusFlag 作業状態フラグ
     */
    public void setStatusFlag(String statusFlag)
    {
        _statusFlag = statusFlag;
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
     * 最終更新日時を返します。
     * @return lastUpdateDateを返します。
     */
    public java.util.Date getLastUpdateDate()
    {
        return _lastUpdateDate;
    }

    /**
     * 最終更新日時を設定します。
     * @param lastUpdateDate lastUpdateDate
     */
    public void setLastUpdateDate(java.util.Date lastUpdateDate)
    {
        _lastUpdateDate = lastUpdateDate;
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
        return "$Id: InventoryOutParameter.java 87 2008-10-04 03:07:38Z admin $";
    }

}

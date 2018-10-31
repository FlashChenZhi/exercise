// $Id: InventoryInParameter.java 1586 2008-11-28 00:05:30Z ota $
package jp.co.daifuku.wms.inventorychk.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;


/**
 * <CODE>InventoryInParameter</CODE>クラスは、棚卸パッケージ内の画面→スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスでは棚卸パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 *
 * <CODE>InventoryInParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     作業No.<BR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *     エリア<BR>
 *     棚<BR>
 *     棚(to)
 *     リスト作業No<BR>
 *     端末号機No.<BR>
 *     ユーザID<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     JANコード<BR>
 *     ケースITF<BR>
 *     ボールITF<BR>
 *     ケース入数<BR>
 *     ボール入数<BR>
 *     ロットNo.<BR>
 *     強制開始区分<BR>
 *     在庫数<BR>
 *     在庫ケース数<BR>
 *     在庫ピース数<BR>
 *     棚卸数<BR>
 *     棚卸ケース数<BR>
 *     棚卸ピース数<BR>
 *     棚卸実績数(int型)<BR>
 *     棚卸実績数(long型)<BR>
 *     作業時間<BR>
 *     完了フラグ<BR>
 *     作業状態フラグ<BR>
 *     範囲指定フラグ<BR>
 *     開始終了フラグ<BR>
 *     帳票発行フラグ<BR>
 *     在庫数印字フラグ<BR>
 *     新規データ表示フラグ<BR>
 *     新規データフラグ<BR>
 *     条件指定方法<BR>
 *     マスタ管理導入フラグ<BR>
 *     棚卸設定情報フラグ<BR>
 *     棚卸作業情報チェックフラグ<BR>
 * <BR>
 * Designer : Sakashita <BR>
 * Maker : Sakashita <BR>
 *
 * @version $Revision: 1586 $, $Date: 2008-11-28 09:05:30 +0900 (金, 28 11 2008) $
 * @author  Last commit: $Author: ota $
 */
public class InventoryInParameter
        extends InParameter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * 状態フラグ : 未作業
     */
    public static final String STATUS_FLAG_INVENTORY_UNSTART = "0";

    /**
     * 状態フラグ : 棚卸作業中
     */
    public static final String STATUS_FLAG_INVENTORY_WORKING = "1";

    /**
     * 状態フラグ : 棚卸作業済み
     */
    public static final String STATUS_FLAG_INVENTORY_WORKING_COMPLETED = "2";

    /**
     * 状態フラグ : 棚卸確定済み
     */
    public static final String STATUS_FLAG_INVENTORY_ALREADY_COMPLETED = "4";
    
    /**
     * 状態フラグ : 削除
     */
    public static final String STATUS_FLAG_INVENTORY_DELETE = "9";

    /**
     * 処理区分：棚卸開始
     */
    public static final String PROCESS_FLAG_INVENTORY_STATRT = "8";

    /**
     * 処理区分：棚卸キャンセル
     */
    public static final String PROCESS_FLAG_INVENTORY_CANCEL = "9";

    /**
     * 処理区分：棚卸結果入力
     */
    public static final String PROCESS_FLAG_INVENTORY_INPUT = "10";

    /**
     * 処理区分：棚卸確定
     */
    public static final String PROCESS_FLAG_INVENTORY_COMPLETE = "11";

    /**
     * 処理区分：帳票印刷
     */
    public static final String PROCESS_FLAG_INVENTORY_PRINT = "12";
    
    /**
     * 処理区分：削除
     */
    public static final String PROCESS_FLAG_INVENTORY_DELETE = "13";

    /**
     * マスタパッケージ有無フラグ : 有り
     */
    public static final String MASTER_UMU_FLAG_YES = "1";

    /**
     * マスタパッケージ有無フラグ : 無し
     */
    public static final String MASTER_UMU_FLAG_NO = "0";

    /**
     * 商品情報有無フラグ(RFT) : 有り
     */
    public static final String ITEM_UMU_FLAG_YES = "1";

    /**
     * 商品情報有無フラグ(RFT) : 無し
     */
    public static final String ITEM_UMU_FLAG_NO = "0";
    
    /**
     * 条件指定方法フラグ : リスト作業No.指定
     */
    public static final String COLLECT_STATUS_LISTNO = "1";
    
    /**
     * 条件指定方法フラグ : エリア棚指定
     */
    public static final String COLLECT_STATUS_LOCALTION = "0";
    
    /**
     * 新規データ区分：通常在庫
     */
    public static final String INVENTORY_NORMALSTOCK = "0";

    /**
     * 新規データ区分：新規
     */
    public static final String INVENTORY_NEWSTOCK = "1";
    
    /**
     * 在庫数印字区分：在庫数印字しない
     */
    public static final String STOCKQTY_PRINT_NOOUTPUT = "0";
    
    /**
     * 在庫数印字区分：在庫数印字する
     */
    public static final String STOCKQTY_PRINT_OUTPUT = "1";
    
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    /**
     * 作業No.
     */
    private String _jobNo;

    /**
     * 荷主コード
     */
    private String _consignorCode;

    /**
     * 荷主名称
     */
    private String _consignorName;

    /**
     * エリア
     */
    private String _areaNo;

    /**
     * 棚
     */
    private String _locationNo;

    /**
     * 棚(to)
     */
    private String _locationNoTo;

    /**
     * リスト作業No.
     */
    private String _settingUnitKey;

    /**
     * スケジュールNo.
     */
    private String _scheduleNo;

    /**
     * 範囲指定フラグ
     */
    private boolean _rangeFlag;

    /**
     * 開始終了フラグ
     */
    private String _fromtoFrag;

    /**
     * 帳票発行フラグ
     */
    private boolean _reportFlag;

    /**
     * 在庫数印字フラグ
     */
    private boolean _stockprintFlag;

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
     * 強制開始区分
     */
    private boolean _forceStartFlag;

    /**
     * 在庫数
     */
    private int _stockQty;

    /**
     * 在庫ケース数
     */
    private int _stockCaseQty;

    /**
     * 在庫ピース数
     */
    private int _stockPieceQty;

    /**
     * 棚卸数
     */
    private int _inventoryQty;

    /**
     * 棚卸ケース数
     */
    private int _resultCaseQty;

    /**
     * 棚卸ピース数
     */
    private int _resultPieceQty;

    /**
     * 棚卸実績数(int型)
     */
    private int _inventoryInsResultQty;

    /**
     * 棚卸実績数(long型)
     */
    private long _inventoryInsResultQtyLong;

    /**
     * 作業時間
     */
    private int _workSeconds;

    /**
     * 完了フラグ
     */
    private String _completionFlag;

    /**
     * 作業状態フラグ
     */
    private String _statusFlag;

    /**
     * 新規棚卸データ表示フラグ
     */
    private boolean _newDispFlag;

    /**
     * 新規棚卸データフラグ
     */
    private boolean _addDataFlag;

    /**
     * 条件指定方法
     */
    private boolean _conditionSelect;

    /**
     * マスタ管理導入フラグ
     */
    private boolean _masterFlag;

    /**
     * 検索対象テーブル
     */
    private String _searchTable;

    /**
     * リストセル行No.
     */
    private int _rowNo;

    /**
     * 処理区分
     */
    private String _processFlag;

    /**
     * 棚卸設定情報フラグ
     */
    private boolean _inventSettingFlag;

    /**
     * 棚卸作業情報チェックフラグ
     */
    private boolean _workInfoCheckFlag;

    /**
     * 最終更新日時
     */
    private java.util.Date _lastUpdateDate;
    
    /**
     * 印刷呼び出し元フラグ
     */
    private String _originFlag;
    

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ <BR>
     * WmsUserInfoを指定します。
     * 
     * @param info WmsUserInfo
     */
    public InventoryInParameter(WmsUserInfo info)
    {
        super();
        setWmsUserInfo(info);
    }
    

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


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
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: InventoryInParameter.java 1586 2008-11-28 00:05:30Z ota $";
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
     * リスト作業Noを返します。
     * @return リスト作業Noを返します。
     */
    public String getSettingUnitKey()
    {
        return _settingUnitKey;
    }

    /**
     * リスト作業Noを設定します。
     * @param settingUnitKey リスト作業No
     */
    public void setSettingUnitKey(String settingUnitKey)
    {
        _settingUnitKey = settingUnitKey;
    }

    /**
     * リスト作業Noを返します。
     * @return リスト作業Noを返します。
     */
    public String getScheduleNo()
    {
        return _scheduleNo;
    }

    /**
     * リスト作業Noを設定します。
     * @param scheduleNo リスト作業No
     */
    public void setScheduleNo(String scheduleNo)
    {
        _scheduleNo = scheduleNo;
    }

    /**
     * 棚を返します。
     * @return 棚を返します。
     */
    public String getLocationNo()
    {
        return _locationNo;
    }

    /**
     * 棚を設定します。
     * @param locationNo 棚
     */
    public void setLocationNo(String locationNo)
    {
        _locationNo = locationNo;
    }

    /**
     * 棚(to)を返します。
     * @return 棚(to)を返します。
     */
    public String getLocationNoTo()
    {
        return _locationNoTo;
    }

    /**
     * 棚(to)を設定します。
     * @param locationNoTo 棚(to)
     */
    public void setLocationNoTo(String locationNoTo)
    {
        _locationNoTo = locationNoTo;
    }

    /**
     * 範囲指定フラグを返します。
     * @return 範囲指定フラグを返します。
     */
    public boolean isRangeFlag()
    {
        return _rangeFlag;
    }

    /**
     * 範囲指定フラグを設定します。
     * @param rangeFlag 範囲指定フラグ
     */
    public void setRangeFlag(boolean rangeFlag)
    {
        _rangeFlag = rangeFlag;
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
     * 帳票発行フラグを返します。
     * @return 帳票発行フラグを返します。
     */
    public boolean isReportFlag()
    {
        return _reportFlag;
    }

    /**
     * 帳票発行フラグを設定します。
     * @param reportFlag 帳票発行フラグ
     */
    public void setReportFlag(boolean reportFlag)
    {
        _reportFlag = reportFlag;
    }

    /**
     * 在庫印字フラグを返します。
     * @return 在庫印字フラグを返します。
     */
    public boolean isStockprintFlag()
    {
        return _stockprintFlag;
    }

    /**
     * 在庫印字フラグを設定します。
     * @param stockprintFlag 在庫印字フラグ
     */
    public void setStockprintFlag(boolean stockprintFlag)
    {
        _stockprintFlag = stockprintFlag;
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
     * 強制開始区分を返します。
     * @return 強制開始区分を返します。
     */
    public boolean getForceStartFlag()
    {
        return _forceStartFlag;
    }

    /**
     * 強制開始区分を設定します。
     * @param forceStartFlag 強制開始区分
     */
    public void setForceStartFlag(boolean forceStartFlag)
    {
        _forceStartFlag = forceStartFlag;
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
     * 在庫ケース数を返します。
     * @return 在庫ケース数を返します。
     */
    public int getStockCaseQty()
    {
        return _stockCaseQty;
    }

    /**
     * 在庫ケース数を設定します。
     * @param stockCaseQty 在庫ケース数
     */
    public void setStockCaseQty(int stockCaseQty)
    {
        _stockCaseQty = stockCaseQty;
    }

    /**
     * 在庫ピース数を返します。
     * @return 在庫ピース数を返します。
     */
    public int getStockPieceQty()
    {
        return _stockPieceQty;
    }

    /**
     * 在庫ピース数を設定します。
     * @param stockPieceQty 在庫ピース数
     */
    public void setStockPieceQty(int stockPieceQty)
    {
        _stockPieceQty = stockPieceQty;
    }

    /**
     * 棚卸数を返します。
     * @return 棚卸数を返します。
     */
    public int getInventoryQty()
    {
        return _inventoryQty;
    }

    /**
     * 棚卸数を設定します。
     * @param inventoryQty 棚卸数
     */
    public void setInventoryQty(int inventoryQty)
    {
        _inventoryQty = inventoryQty;
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
    public void setResultCaseQty(int resultCaseQty)
    {
        _resultCaseQty = resultCaseQty;
    }

    /**
     * 棚卸ピース数を返します。
     * @return 棚卸ピース数を返します。
     */
    public int getResultPieceQty()
    {
        return _resultPieceQty;
    }

    /**
     * 棚卸ピース数を設定します。
     * @param resultPieceQty 棚卸ピース数
     */
    public void setResultPieceQty(int resultPieceQty)
    {
        _resultPieceQty = resultPieceQty;
    }

    /**
     * 棚卸実績数(int型)を返します。
     * @return 棚卸実績数(int型)を返します。
     */
    public int getInventoryInsResultQty()
    {
        return _inventoryInsResultQty;
    }

    /**
     * 棚卸実績数(int型)を設定します。
     * @param inventoryInsResultQty 棚卸実績数(int型)
     */
    public void setInventoryInsResultQty(int inventoryInsResultQty)
    {
        _inventoryInsResultQty = inventoryInsResultQty;
    }

    /**
     * 棚卸実績数(long型)を返します。
     * @return 棚卸実績数(long型)を返します。
     */
    public long getInventoryInsResultQtyLong()
    {
        return _inventoryInsResultQtyLong;
    }

    /**
     * 棚卸実績数(long型)を設定します。
     * @param inventoryInsResultQtyLong 棚卸実績数(long型)
     */
    public void setInventoryInsResultQtyLong(long inventoryInsResultQtyLong)
    {
        _inventoryInsResultQtyLong = inventoryInsResultQtyLong;
    }

    /**
     * 作業時間を返します。
     * @return 作業時間を返します。
     */
    public int getWorkSeconds()
    {
        return _workSeconds;
    }

    /**
     * 作業時間を設定します。
     * @param workSeconds 作業時間
     */
    public void setWorkSeconds(int workSeconds)
    {
        _workSeconds = workSeconds;
    }

    /**
     * 完了フラグを返します。
     * @return 完了フラグを返します。
     */
    public String getCompletionFlag()
    {
        return _completionFlag;
    }

    /**
     * 完了フラグを設定します。
     * @param completionFlag 完了フラグ
     */
    public void setCompletionFlag(String completionFlag)
    {
        _completionFlag = completionFlag;
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
     * 新規棚卸データ表示フラグを返します。
     * @return 新規棚卸データ表示フラグを返します。
     */
    public boolean isNewDispFlag()
    {
        return _newDispFlag;
    }

    /**
     * 新規棚卸データ表示フラグを設定します。
     * @param newDispFlag 新規棚卸データ表示フラグ
     */
    public void setNewDispFlag(boolean newDispFlag)
    {
        _newDispFlag = newDispFlag;
    }

    /**
     * 新規棚卸データフラグを返します。
     * @return 新規棚卸データフラグを返します。
     */
    public boolean isAddDataFlag()
    {
        return _addDataFlag;
    }

    /**
     * 新規棚卸データフラグを設定します。
     * @param addDataFlag 新規棚卸データフラグ
     */
    public void setAddDataFlag(boolean addDataFlag)
    {
        _addDataFlag = addDataFlag;
    }

    /**
     * 条件指定方法を返します。
     * @return 条件指定方法を返します。
     */
    public boolean isConditionSelect()
    {
        return _conditionSelect;
    }

    /**
     * 条件指定方法を設定します。
     * @param conditionSelect 条件指定方法
     */
    public void setConditionSelect(boolean conditionSelect)
    {
        _conditionSelect = conditionSelect;
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
     * 処理フラグを設定します。
     * @param processFlag 処理区分
     */
    public void setProcessFlag(String processFlag)
    {
        _processFlag = processFlag;
    }

    /**
     * 処理フラグを返します。
     * @return 処理区分を返します。
     */
    public String getProcessFlag()
    {
        return _processFlag;
    }

    /**
     * 棚卸設定情報フラグを返します。
     * @return 棚卸設定情報フラグを返します。
     */
    public boolean isInventSettingFlag()
    {
        return _inventSettingFlag;
    }

    /**
     * 棚卸設定情報フラグを設定します。
     * @param settingFlag 棚卸設定情報フラグ
     */
    public void setInventSettingFlag(boolean settingFlag)
    {
        _inventSettingFlag = settingFlag;
    }

    /**
     * 棚卸作業情報チェックフラグを返します。
     * @return 棚卸作業情報チェックフラグを返します。
     */
    public boolean isWorkInfoCheckFlag()
    {
        return _workInfoCheckFlag;
    }

    /**
     * 棚卸作業情報チェックフラグを設定します。
     * @param workInfoCheckFlag 棚卸作業情報チェックフラグ
     */
    public void setWorkInfoCheckFlag(boolean workInfoCheckFlag)
    {
        _workInfoCheckFlag = workInfoCheckFlag;
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
    
    /**
     * 印刷呼び出し元フラグを設定します。
     * @param originFlag 印刷呼び出し元フラグ
     */
    public void setOriginFlag(String originFlag)
    {
        _originFlag = originFlag;
    }

    /**
     * 印刷呼び出し元フラグを返します。
     * @return 印刷呼び出し元フラグを返します。
     */
    public String getOriginFlag()
    {
        return _originFlag;
    }
}

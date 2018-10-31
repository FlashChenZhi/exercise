package jp.co.daifuku.wms.part11.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.common.OutParameter;
import jp.co.daifuku.wms.base.entity.AreaHistory;
import jp.co.daifuku.wms.base.entity.CustomerHistory;
import jp.co.daifuku.wms.base.entity.FixedLocateHistory;
import jp.co.daifuku.wms.base.entity.ItemHistory;
import jp.co.daifuku.wms.base.entity.LocateHistory;
import jp.co.daifuku.wms.base.entity.Part11StockHistory;
import jp.co.daifuku.wms.base.entity.SupplierHistory;

/**
 * <CODE>StorageOutParameter</CODE>クラスは、入庫パッケージ内のスケジュール→画面間のパラメータの受渡しに使用します。<BR>
 * このクラスでは入庫パッケージの各画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>StorageOutParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *	SVFTest
 *     在庫更新履歴情報<BR>
 *     商品マスタ更新履歴情報<BR>
 *     仕入先マスタ更新履歴情報<BR>
 *     出荷先マスタ更新履歴情報<BR>
 *     エリアマスタ更新履歴情報<BR>
 *     棚マスタ更新履歴情報<BR>
 *     商品固定棚マスタ更新履歴情報<BR>
 * </DIR>
 * 
 * Designer : T.Kishimoto <BR>
 * Maker : T.Kishimoto <BR>
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  $Author: arai $
 */
public class Part11OutParameter
        extends OutParameter
{
    // Class variables -----------------------------------------------
    /**
     * 在庫更新履歴情報
     */
    private Part11StockHistory _stockHistory;

    /**
     * 商品マスタ更新履歴情報
     */
    private ItemHistory _itemHistory;

    /**
     * 仕入先マスタ更新履歴情報
     */
    private SupplierHistory _supplierHistory;

    /**
     * 出荷先マスタ更新履歴情報
     */
    private CustomerHistory _customerHistory;

    /**
     * エリアマスタ更新履歴情報
     */
    private AreaHistory _areaHistory;

    /**
     * 棚マスタ更新履歴情報
     */
    private LocateHistory _locateHistory;

    /**
     * 商品固定棚マスタ更新履歴情報
     */
    private FixedLocateHistory _fixedLocateHistory;

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
     * 在庫更新履歴情報を返します。
     * @return 在庫更新履歴情報を返します。
     */
    public Part11StockHistory getStockHistory()
    {
        return _stockHistory;
    }

    /**
     * 在庫更新履歴情報を設定します。
     * @param rEntity 在庫更新履歴情報
     */
    public void setStockHistory(Part11StockHistory rEntity)
    {
        _stockHistory = rEntity;
    }

    /**
     * 商品マスタ更新履歴情報を返します。
     * @return 商品マスタ更新履歴情報を返します。
     */
    public ItemHistory getItemHistory()
    {
        return _itemHistory;
    }

    /**
     * 商品マスタ更新履歴情報を設定します。
     * @param rEntity 商品マスタ更新履歴情報
     */
    public void setItemHistory(ItemHistory rEntity)
    {
        _itemHistory = rEntity;
    }

    /**
     * 仕入先マスタ更新履歴情報を返します。
     * @return 仕入先マスタ更新履歴情報を返します。
     */
    public SupplierHistory getSupplierHistory()
    {
        return _supplierHistory;
    }

    /**
     * 仕入先マスタ更新履歴情報を設定します。
     * @param rEntity 仕入先マスタ更新履歴情報
     */
    public void setSupplierHistory(SupplierHistory rEntity)
    {
        _supplierHistory = rEntity;
    }

    /**
     * 出荷先マスタ更新履歴情報を返します。
     * @return 出荷先マスタ更新履歴情報を返します。
     */
    public CustomerHistory getCustomerHistory()
    {
        return _customerHistory;
    }

    /**
     * 出荷先マスタ更新履歴情報を設定します。
     * @param rEntity 出荷先マスタ更新履歴情報
     */
    public void setCustomerHistory(CustomerHistory rEntity)
    {
        _customerHistory = rEntity;
    }

    /**
     * エリアマスタ更新履歴情報を返します。
     * @return エリアマスタ更新履歴情報を返します。
     */
    public AreaHistory getAreaHistory()
    {
        return _areaHistory;
    }

    /**
     * エリアマスタ更新履歴情報を設定します。
     * @param rEntity エリアマスタ更新履歴情報
     */
    public void setAreaHistory(AreaHistory rEntity)
    {
        _areaHistory = rEntity;
    }

    /**
     * 棚マスタ更新履歴情報を返します。
     * @return 棚マスタ更新履歴情報を返します。
     */
    public LocateHistory getLocateHistory()
    {
        return _locateHistory;
    }

    /**
     * 棚マスタ更新履歴情報を設定します。
     * @param rEntity 棚マスタ更新履歴情報
     */
    public void setLocateHistory(LocateHistory rEntity)
    {
        _locateHistory = rEntity;
    }

    /**
     * 商品固定棚マスタ更新履歴情報を返します。
     * @return 商品固定棚マスタ更新履歴情報を返します。
     */
    public FixedLocateHistory getFixedLocateHistory()
    {
        return _fixedLocateHistory;
    }

    /**
     * 商品固定棚マスタ更新履歴情報を設定します。
     * @param rEntity 商品固定棚マスタ更新履歴情報
     */
    public void setFixedLocateHistory(FixedLocateHistory rEntity)
    {
        _fixedLocateHistory = rEntity;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

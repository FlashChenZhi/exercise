// $Id: ItemDataLoader.java 7636 2010-03-17 04:11:45Z okayama $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2010 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava;
import jp.co.daifuku.wms.base.common.DsNumberDefine;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingUnitHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingUnitSearchKey;
import jp.co.daifuku.wms.base.entity.Com_terminal;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.fileentity.HostItem;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;

/**
 * 商品マスタの取り込み処理を行います。<br>
 *
 * @version $Revision: 7636 $, $Date: 2010-03-17 13:11:45 +0900 (水, 17 3 2010) $
 * @author  H.Okayama
 * @author  Last commit: $Author: okayama $
 */
public class ItemDataLoader
        extends AbstractDataLoaderForJava
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** WMSサーバの端末No. */
    private static final String SERVER_TERMINAL_NO = "Svr";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** 商品マスタハンドラ */
    private ItemHandler _ItemHandler = null;

    /** 商品マスタ検索キー */
    private ItemSearchKey _ItemKey = null;

    /** 商品固定棚情報ハンドラ */
    private FixedLocateInfoHandler _fixedHandler = null;

    /** 商品固定棚情報検索キー */
    private FixedLocateInfoSearchKey _fixedKey = null;

    /** 入荷予定情報ハンドラ */
    private ReceivingPlanHandler _recHandler = null;

    /** 入荷予定情報検索キー */
    private ReceivingPlanSearchKey _recKey = null;

    /** 入庫予定情報ハンドラ */
    private StoragePlanHandler _stHandler = null;

    /** 入庫予定情報検索キー */
    private StoragePlanSearchKey _stKey = null;

    /** 出庫予定情報ハンドラ */
    private RetrievalPlanHandler _retHandler = null;

    /** 出庫予定情報検索キー */
    private RetrievalPlanSearchKey _retKey = null;

    /** 入出庫作業情報ハンドラ */
    private WorkInfoHandler _wIHandler = null;

    /** 入出庫作業情報検索キー */
    private WorkInfoSearchKey _wIKey = null;

    /** 出荷予定情報ハンドラ */
    private ShipPlanHandler _shipHandler = null;

    /** 出荷予定情報検索キー */
    private ShipPlanSearchKey _shipKey = null;

    /** 移動作業情報ハンドラ */
    private MoveWorkInfoHandler _moveHandler = null;

    /** 移動作業情報検索キー */
    private MoveWorkInfoSearchKey _moveKey = null;

    /** 棚卸作業情報ハンドラ */
    private InventWorkInfoHandler _invHandler = null;

    /** 棚卸作業情報検索キー */
    private InventWorkInfoSearchKey _invKey = null;

    /** 在庫情報ハンドラ */
    private StockHandler _stkHandler = null;

    /** 在庫情報検索キー */
    private StockSearchKey _stkKey = null;

    /** 作業単位数マスタハンドラ */
    private WorkingUnitHandler _wkUnitHandler = null;

    /** 作業単位数マスタ検索キー */
    private WorkingUnitSearchKey _wkUnitKey = null;

    /** AS/RSソフトゾーン情報ハンドラ */
    private SoftZoneHandler _softHandler = null;

    /** AS/RSソフトゾーン情報検索キー */
    private SoftZoneSearchKey _softKey = null;

    /** ユーザ情報 */
    private DfkUserInfo _userInfo = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 取り込み区分仕入先マスタの受信データの取り込みを行います。<BR>
     * 自動取込みから使用します。
     */
    public ItemDataLoader()
    {
        super(ExchangeEnvironment.LOAD_DATA_TYPE_MASTER_ITEM);
    }

    /**
     * 取り込み区分仕入先マスタの受信データの取り込みを行います。<BR>
     * WEB画面から使用します。
     * 
     * @param userinfo ユーザ情報
     * @param locale ロケール
     */
    public ItemDataLoader(DfkUserInfo userinfo, Locale locale)
    {
        super(ExchangeEnvironment.LOAD_DATA_TYPE_MASTER_ITEM, userinfo, locale);
        _userInfo = userinfo;
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
    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#init()
     */
    @Override
    protected void init()
            throws ReadWriteException
    {
        // 商品マスタ
        _ItemHandler = new ItemHandler(getConnection());
        _ItemKey = new ItemSearchKey();

        // 商品固定棚マスタ
        _fixedHandler = new FixedLocateInfoHandler(getConnection());
        _fixedKey = new FixedLocateInfoSearchKey();

        // 入荷予定情報
        _recHandler = new ReceivingPlanHandler(getConnection());
        _recKey = new ReceivingPlanSearchKey();

        // 入庫予定情報
        _stHandler = new StoragePlanHandler(getConnection());
        _stKey = new StoragePlanSearchKey();

        // 出庫予定情報
        _retHandler = new RetrievalPlanHandler(getConnection());
        _retKey = new RetrievalPlanSearchKey();

        // 入出庫作業情報
        _wIHandler = new WorkInfoHandler(getConnection());
        _wIKey = new WorkInfoSearchKey();

        // 出荷予定情報
        _shipHandler = new ShipPlanHandler(getConnection());
        _shipKey = new ShipPlanSearchKey();

        // 移動作業情報
        _moveHandler = new MoveWorkInfoHandler(getConnection());
        _moveKey = new MoveWorkInfoSearchKey();

        // 棚卸作業情報
        _invHandler = new InventWorkInfoHandler(getConnection());
        _invKey = new InventWorkInfoSearchKey();

        // 在庫情報
        _stkHandler = new StockHandler(getConnection());
        _stkKey = new StockSearchKey();

        // 作業単位数マスタ
        _wkUnitHandler = new WorkingUnitHandler(getConnection());
        _wkUnitKey = new WorkingUnitSearchKey();

        // AS/RSソフトゾーン情報
        _softHandler = new SoftZoneHandler(getConnection());
        _softKey = new SoftZoneSearchKey();
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#check(jp.co.daifuku.wms.handler.Entity)
     */
    @Override
    protected RESULT check(Entity ent)
            throws CommonException
    {
        // システム定義コントローラ
        WarenaviSystemController sysCtrl = new WarenaviSystemController(getConnection(), this.getClass());
        HostItem item = (HostItem)ent;

        // 禁止文字を含むかチェック
        RESULT chk = hasNGParameterText(item);
        if (!chk.equals(RESULT.LOAD))
        {
            return chk;
        }

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(item.getLoadFlag()))
        {
            // 同一データが存在する場合はスキップ
            _ItemKey.clear();
            _ItemKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _ItemKey.setItemCode(item.getItemCode());
            if (_ItemHandler.count(_ItemKey) > 0)
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025",
                        item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 下限在庫数が上限在庫数を上回る場合はスキップ
            if (item.getUpperQty() < item.getLowerQty())
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                        item.getStoreMetaData().getFieldMetaData(HostItem.LOWER_QTY.getName()).getDescription());
                return RESULT.SKIP;
            }

            // AS/RSパッケージが導入されている場合
            if (sysCtrl.hasAsrsPack())
            {
                // 指定したソフトゾーンが登録されていない場合はスキップ
                _softKey.clear();
                _softKey.setSoftZoneId(item.getSoftZoneId());
                if (_softHandler.count(_softKey) == 0)
                {
                    // MSG-W0023=マスタ未登録
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                            item.getStoreMetaData().getFieldMetaData(HostItem.SOFT_ZONE_ID.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        // 取消区分が削除の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(item.getLoadFlag()))
        {
            // 取消対象データが存在しない場合はスキップ
            _ItemKey.clear();
            _ItemKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _ItemKey.setItemCode(item.getItemCode());
            Item[] items = (Item[])_ItemHandler.find(_ItemKey);
            if (items == null || items.length == 0)
            {
                // MSG-W0052=削除該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0052",
                        item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 対象データがシステム管理されている場合はスキップ
            if (SystemDefine.MANAGEMENT_TYPE_SYSTEM.equals(items[0].getManagementType()))
            {
                // MSG-W0053=削除不可データ
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0053",
                        item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // マスタパッケージが導入されている場合
            if (sysCtrl.hasMasterPack())
            {
                // 商品固定棚で使用されている場合はスキップ
                _fixedKey.clear();
                _fixedKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _fixedKey.setItemCode(item.getItemCode());
                if (_fixedHandler.count(_fixedKey) > 0)
                {
                    // MSG-W0054=データ使用中(削除不可)
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0054",
                            item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // 入荷パッケージが導入されている場合
            if (sysCtrl.hasReceivingPack())
            {
                // 入荷予定情報で使用されている場合はスキップ
                _recKey.clear();
                _recKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _recKey.setItemCode(item.getItemCode());
                _recKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                if (_recHandler.count(_recKey) > 0)
                {
                    // MSG-W0054=データ使用中(削除不可)
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0054",
                            item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // 入庫パッケージが導入されている場合
            if (sysCtrl.hasStoragePack())
            {
                // 入庫予定情報で使用されている場合はスキップ
                _stKey.clear();
                _stKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _stKey.setItemCode(item.getItemCode());
                _stKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                if (_stHandler.count(_stKey) > 0)
                {
                    // MSG-W0054=データ使用中(削除不可)
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0054",
                            item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }

                // 棚卸作業情報で使用されている場合はスキップ
                _invKey.clear();
                _invKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _invKey.setItemCode(item.getItemCode());
                _invKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                if (_invHandler.count(_invKey) > 0)
                {
                    // MSG-W0054=データ使用中(削除不可)
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0054",
                            item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // 出庫パッケージが導入されている場合
            if (sysCtrl.hasRetrievalPack())
            {
                // 出庫予定情報で使用されている場合はスキップ
                _retKey.clear();
                _retKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _retKey.setItemCode(item.getItemCode());
                _retKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                if (_retHandler.count(_retKey) > 0)
                {
                    // MSG-W0054=データ使用中(削除不可)
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0054",
                            item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // AS/RSパッケージが導入されている場合
            if (sysCtrl.hasAsrsPack())
            {
                // 入出庫作業情報で使用されている場合はスキップ
                _wIKey.clear();
                _wIKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _wIKey.setItemCode(item.getItemCode());
                _wIKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                if (_wIHandler.count(_wIKey) > 0)
                {
                    // MSG-W0054=データ使用中(削除不可)
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0054",
                            item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // クロスドックパッケージ
            // または出荷パッケージが導入されている場合
            if (sysCtrl.hasCrossdockPack() || sysCtrl.hasShippingPack())
            {
                // クロスドックではTC予定情報が出荷予定情報より先に削除される可能性があるため
                // 出荷予定情報で使用されている場合はスキップ
                _shipKey.clear();
                _shipKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _shipKey.setItemCode(item.getItemCode());
                _shipKey.setWorkStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=", "(", "", false);
                _shipKey.setBerthStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=", "", ")", true);
                if (_shipHandler.count(_shipKey) > 0)
                {
                    // MSG-W0054=データ使用中(削除不可)
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0054",
                            item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // 在庫パッケージが導入されている場合
            if (sysCtrl.hasStockPack())
            {
                // 在庫情報で使用されている場合はスキップ
                _stkKey.clear();
                _stkKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _stkKey.setItemCode(item.getItemCode());
                if (_stkHandler.count(_stkKey) > 0)
                {
                    // MSG-W0054=データ使用中(削除不可)
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0054",
                            item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }

                // 移動作業情報で使用されている場合はスキップ
                _moveKey.clear();
                _moveKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _moveKey.setItemCode(item.getItemCode());
                _moveKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                if (_moveHandler.count(_moveKey) > 0)
                {
                    // MSG-W0054=データ使用中(削除不可)
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0054",
                            item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // 分析パッケージが導入されている場合
            if (sysCtrl.hasAnalysisPack())
            {
                // 作業単位数マスタで使用されている場合はスキップ
                _wkUnitKey.clear();
                _wkUnitKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _wkUnitKey.setItemCode(item.getItemCode());
                if (_wkUnitHandler.count(_wkUnitKey) > 0)
                {
                    // MSG-W0054=データ使用中(削除不可)
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0054",
                            item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068",
                    item.getStoreMetaData().getFieldMetaData(HostItem.LOAD_FLAG.getName()).getDescription());
            return RESULT.SKIP;
        }
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#loadData(jp.co.daifuku.wms.handler.Entity)
     */
    @Override
    protected RESULT loadData(Entity ent)
            throws CommonException
    {
        // 商品マスタコントローラの生成
        ItemController itemCtrl = new ItemController(getConnection(), this.getClass());
        // 取込商品情報
        HostItem item = (HostItem)ent;

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(item.getLoadFlag()))
        {
            // 商品マスタエンティティ
            Item items = new Item();

            // 値の設定
            items.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
            items.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            items.setItemCode(item.getItemCode());
            items.setItemName(item.getItemName());
            items.setSoftZoneId(item.getSoftZoneId());
            items.setJan(item.getJan());
            items.setItf(item.getItf());
            items.setBundleItf("");
            items.setEnteringQty(item.getEnteringQty());
            items.setBundleEnteringQty(0);
            items.setUpperQty(item.getUpperQty());
            items.setLowerQty(item.getLowerQty());
            items.setRegistPname(this.getClass().getSimpleName());
            items.setLastUpdatePname(this.getClass().getSimpleName());

            try
            {
                // 登録
                _ItemHandler.create(items);

                // 商品マスタ改廃履歴登録
                itemCtrl.insertHistory(items, SystemDefine.UPDATE_KIND_REGIST, this.getUserInfo());

                return RESULT.LOAD;
            }
            // 同一データがすでに存在した場合はスキップ
            catch (DataExistsException e)
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025",
                        item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }
        }
        // 取消区分が削除の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(item.getLoadFlag()))
        {
            // 値の設定
            _ItemKey.clear();
            _ItemKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _ItemKey.setItemCode(item.getItemCode());

            try
            {
                // 削除
                Item items = (Item)_ItemHandler.findPrimary(_ItemKey);
                _ItemHandler.drop(_ItemKey);

                // 商品マスタ改廃履歴登録
                itemCtrl.insertHistory(items, SystemDefine.UPDATE_KIND_DELETE, this.getUserInfo());

                return RESULT.LOAD;
            }
            // 対象データなしの場合はスキップ
            catch (NotFoundException e)
            {
                // MSG-W0027=取消該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0027",
                        item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }
        }
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068",
                    item.getStoreMetaData().getFieldMetaData(HostItem.LOAD_FLAG.getName()).getDescription());
            return RESULT.SKIP;
        }
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#getEntity()
     */
    @Override
    protected AbstractEntity getEntity()
    {
        return new HostItem();
    }

    /**
     * 統計情報の呼出しを行います。
     * 
     * @throws CommonException
     * @throws SQLException
     */
    protected void statics()
            throws CommonException
    {
        // 統計情報の呼出し
        _ItemHandler.getStatics();
    }

    /**
     * ユーザ情報を取得します。
     *
     * @return ユーザ情報
     */
    private DfkUserInfo getUserInfo()
            throws CommonException
    {
        // ユーザ情報がある場合も無い場合も念のため取得
        Com_terminalHandler com = new Com_terminalHandler(getConnection());
        Com_terminalSearchKey comKey = new Com_terminalSearchKey();
        comKey.setTerminalnumber(SERVER_TERMINAL_NO);
        Com_terminal comInfo = (Com_terminal)com.findPrimary(comKey);

        // 画面からUserInfoが指定された場合 かつ
        // 保持している情報とDBのプリンター名が同一の場合は生成しない
        if (_userInfo != null && comInfo.getPrintername().equals(_userInfo.getTerminalPrinterName()))
        {
            return _userInfo;
        }

        _userInfo = new DfkUserInfo();
        _userInfo.setDsNumber(DsNumberDefine.DS_AUTOLOAD);
        _userInfo.setUserId(WmsParam.SYS_USER_ID);
        _userInfo.setUserName(WmsParam.SYS_USER_NAME);
        _userInfo.setTerminalNumber(SERVER_TERMINAL_NO);
        _userInfo.setTerminalName(WmsParam.SYS_TERMINAL_NAME);
        _userInfo.setTerminalAddress(WmsParam.SYS_IP_ADDRESS);
        _userInfo.setTerminalPrinterName(comInfo.getPrintername());
        _userInfo.setPageNameResourceKey(DsNumberDefine.PAGERESOUCE_AUTOLOAD);

        return _userInfo;
    }

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
        return "$Id: ItemDataLoader.java 7636 2010-03-17 04:11:45Z okayama $";
    }
}

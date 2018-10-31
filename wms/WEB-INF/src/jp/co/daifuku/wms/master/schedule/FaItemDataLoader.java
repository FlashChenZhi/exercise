// $Id: FaItemDataLoader.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.master.schedule;

import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava;
import jp.co.daifuku.wms.base.common.DsNumberDefine;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Com_terminal;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.fileentity.HostItem;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * FA商品マスタ情報の取り込み処理を行います。<br>
 *
 * @version $Revision: 5709 $, $Date: 2009-11-12 19:55:36 +0900 (木, 12 11 2009) $
 * @author  H.Okayama
 * @author  Last commit: $Author: okamura $
 */
public class FaItemDataLoader
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
    private ItemHandler _itmHandler = null;

    /** 商品マスタ検索キー */
    private ItemSearchKey _itmKey = null;

    /** 商品マスタ更新キー */
    private ItemAlterKey _itmAltKey = null;

    /** 出庫予定ハンドラ */
    private RetrievalPlanHandler _retPlanHandler = null;

    /** 出庫予定検索キー */
    private RetrievalPlanSearchKey _retPlanSearchKey = null;

    /** AS/RSソフトゾーンハンドラ */
    private SoftZoneHandler _softZoneHandler = null;

    /** AS/RSソフトゾーン検索キー */
    private SoftZoneSearchKey _softZoneSearchKey = null;

    /** 在庫検索キー */
    private StockHandler _stockHandler = null;

    /** 在庫検索キー */
    private StockSearchKey _stockSearchKey = null;

    /** ユーザ情報 */
    private DfkUserInfo _userInfo = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 取り込み区分商品マスタの取り込みを行います。<BR>
     * 自動取込みから使用します。
     * 
     */
    public FaItemDataLoader()
    {
        super(ExchangeEnvironment.LOAD_DATA_TYPE_MASTER_ITEM);
    }

    /**
     * 取り込み区分商品マスタの取り込みを行います。<BR>
     * WEB画面から使用します。
     * 
     * @param userinfo ユーザ情報
     * @param locale ロケール
     */
    public FaItemDataLoader(DfkUserInfo userinfo, Locale locale)
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
        _itmHandler = new ItemHandler(getConnection());
        _itmKey = new ItemSearchKey();
        _itmAltKey = new ItemAlterKey();

        // 出庫予定
        _retPlanHandler = new RetrievalPlanHandler(getConnection());
        _retPlanSearchKey = new RetrievalPlanSearchKey();

        // AS/RSソフトゾーン
        _softZoneHandler = new SoftZoneHandler(getConnection());
        _softZoneSearchKey = new SoftZoneSearchKey();

        // 在庫情報
        _stockHandler = new StockHandler(getConnection());
        _stockSearchKey = new StockSearchKey();
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#check(jp.co.daifuku.wms.handler.Entity)
     */
    @Override
    protected RESULT check(Entity ent)
            throws CommonException
    {
        HostItem item = (HostItem)ent;

        // 禁止文字を含むかチェック
        RESULT chk2 = hasNGParameterText(item);
        if (!chk2.equals(RESULT.LOAD))
        {
            return chk2;
        }

        // 取込区分が新規登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(item.getLoadFlag()))
        {
            // 既に同一データが存在する場合
            if (itemExists(item))
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025",
                        item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // ソフトゾーンが登録されていない場合
            if (!softZoneExists(item))
            {
                // MSG-W0023=マスタ未登録
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                        item.getStoreMetaData().getFieldMetaData(HostItem.SOFT_ZONE_ID.getName()).getDescription());
                return RESULT.SKIP;
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        // 取消区分が削除の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(item.getLoadFlag()))
        {
            // 対象データが存在しない場合
            if (!itemExists(item))
            {
                // MSG-W0052=削除該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0052",
                        item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // システム管理区分がシステム管理の場合
            if (SystemDefine.MANAGEMENT_TYPE_SYSTEM.equals(getManagementType(item)))
            {
                // MSG-W0053=削除不可データ
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0053",
                        item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 出庫予定情報で使用されている場合
            if (retPlanExists(item))
            {
                // MSG-W0054=データ使用中(削除不可)
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0054",
                        item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 在庫情報で使用されている場合
            if (stockExists(item))
            {
                // MSG-W0054=データ使用中(削除不可)
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0054",
                        item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        else if (SystemDefine.CANCEL_FLAG_HOST_MODIFY.equals(item.getLoadFlag()))
        {
            // 対象データが存在しない場合
            if (!itemExists(item))
            {
                // MSG-W0077=修正該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0077",
                        item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // システム管理区分がシステム管理の場合
            if (SystemDefine.MANAGEMENT_TYPE_SYSTEM.equals(getManagementType(item)))
            {
                // MSG-W0053=削除不可データ
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0053",
                        item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }
            // 在庫情報で使用されている場合
            if (stockExists(item))
            {
                if (!WmsParam.MASTER_MODIFY_FLAG)
                {
                    // MSG-W0076=データ使用中(変更不可)
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0076",
                            item.getStoreMetaData().getFieldMetaData(HostItem.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }
            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        // 区分誤りの場合はスキップ(ここは通らないはず)
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

        // 取込区分が新規登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(item.getLoadFlag()))
        {
            // 商品マスタエンティティ
            Item items = new Item();

            // 商品マスタ情報.システム管理区分
            items.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
            // 商品マスタ情報.荷主コード
            items.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            // 商品マスタ情報.商品コード
            items.setItemCode(item.getItemCode());
            // 商品マスタ情報.商品名称
            items.setItemName(item.getItemName());
            // 商品マスタ情報.JANコード
            items.setJan(null);
            // 商品マスタ情報.ケースITF
            items.setItf(null);
            // 商品マスタ情報.ボールITF
            items.setBundleItf(null);
            // 商品マスタ情報.ケース入数
            items.setEnteringQty(0);
            // 商品マスタ情報.ボール入数
            items.setBundleEnteringQty(0);
            // 商品マスタ情報.上限在庫数
            items.setUpperQty(0);
            // 商品マスタ情報.下限在庫数
            items.setLowerQty(0);
            // ソフトゾーンIDが指定されている場合
            if (!StringUtil.isBlank(item.getSoftZoneId()))
            {
                // 商品マスタ情報.ソフトゾーンID
                item.setSoftZoneId(item.getSoftZoneId());
            }
            // ソフトゾーンIDが指定されていない場合
            else
            {
                // 商品マスタ情報.ソフトゾーンID
                item.setSoftZoneId(SystemDefine.SOFT_ZONE_ALL);
            }
            // 商品マスタ情報.把持区分
            items.setHoldType(null);
            // 商品マスタ情報.一時商品区分
            items.setTemporaryType(SystemDefine.TEMPORARY_TYPE_NORMAL);

            try
            {
                // 登録
                _itmHandler.create(items);

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
            // 商品マスタ検索キーをクリア
            _itmKey.clear();
            // 商品マスタ情報.システム管理区分
            _itmKey.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
            // 商品マスタ情報.荷主コード
            _itmKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            // 商品マスタ情報.商品コード
            _itmKey.setItemCode(item.getItemCode());

            try
            {
                // 削除
                Item items = (Item)_itmHandler.findPrimary(_itmKey);
                _itmHandler.drop(_itmKey);

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
        // 取消区分が変更の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_MODIFY.equals(item.getLoadFlag()))
        {
            try
            {
                // 商品マスタ更新キーをクリア
                _itmAltKey.clear();
                // 商品マスタ情報.システム管理区分
                _itmAltKey.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
                // 商品マスタ情報.荷主コード
                _itmAltKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                // 商品マスタ情報.商品コード
                _itmAltKey.setItemCode(item.getItemCode());
                // 商品マスタ情報.商品名称
                _itmAltKey.updateItemName(item.getItemName());
                // 商品マスタ情報.ソフトゾーンID
                _itmAltKey.updateSoftZoneId(item.getSoftZoneId());

                _itmHandler.modify(_itmAltKey);
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
     * 商品マスタ情報の存在チェックを行います。
     * 
     * @param loadItem 取込情報
     * @return true:存在、false:非存在
     * @throws CommonException
     */
    protected boolean itemExists(HostItem loadItem)
            throws CommonException
    {
        // 商品マスタ検索キーをクリア
        _itmKey.clear();
        // 商品マスタ情報.荷主コード
        _itmKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        // 商品マスタ情報.商品コード
        _itmKey.setItemCode(loadItem.getItemCode());

        // 検索実行
        if (_itmHandler.count(_itmKey) > 0)
        {
            // データが存在する場合
            return true;
        }
        // データが存在しない場合
        return false;
    }

    /**
     * AS/RSソフトゾーン情報の存在チェックを行います。
     * 
     * @param loadItem 取込情報
     * @return true:存在、false:非存在
     * @throws CommonException
     */
    protected boolean softZoneExists(HostItem loadItem)
            throws CommonException
    {
        // 指定されていない場合はチェックしない
        if (StringUtil.isBlank(loadItem.getSoftZoneId()))
        {
            return true;
        }

        // AS/RSソフトゾーン検索キーをクリア
        _softZoneSearchKey.clear();
        // AS/RSソフトゾーン情報.ソフトゾーンID
        _softZoneSearchKey.setSoftZoneId(loadItem.getSoftZoneId());

        // 検索実行
        if (_softZoneHandler.count(_softZoneSearchKey) > 0)
        {
            // データが存在する場合
            return true;
        }
        // データが存在しない場合
        return false;
    }

    /**
     * 出庫予定情報の存在チェックを行います。
     * 
     * @param loadItem 取込情報
     * @return true:存在、false:非存在
     * @throws CommonException
     */
    protected boolean retPlanExists(HostItem loadItem)
            throws CommonException
    {
        // 出庫予定検索キーをクリア
        _retPlanSearchKey.clear();
        // 出庫予定情報.荷主コード
        _retPlanSearchKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        // 出庫予定情報.商品コード
        _retPlanSearchKey.setItemCode(loadItem.getItemCode());
        // 出庫予定情報.状態フラグ
        _retPlanSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // 検索実行
        if (_retPlanHandler.count(_retPlanSearchKey) > 0)
        {
            // データが存在する場合
            return true;
        }
        // データが存在しない場合
        return false;
    }

    /**
     * 在庫情報の存在チェックを行います。
     * 
     * @param loadItem 取込情報
     * @return true:存在、false:非存在
     * @throws CommonException
     */
    protected boolean stockExists(HostItem loadItem)
            throws CommonException
    {
        // 在庫検索キーをクリア
        _stockSearchKey.clear();
        // 在庫情報.荷主コード
        _stockSearchKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        // 在庫情報.商品コード
        _stockSearchKey.setItemCode(loadItem.getItemCode());

        // 検索実行
        if (_stockHandler.count(_stockSearchKey) > 0)
        {
            // データが存在する場合
            return true;
        }
        // データが存在しない場合
        return false;
    }

    /** 
     * 商品マスタ情報.システム管理区分を取得します。
     * 
     * @param loadItem 取込情報
     * @return 存在:システム管理区分、非存在:空文字
     * @throws CommonException
     */
    protected String getManagementType(HostItem loadItem)
            throws CommonException
    {
        // 商品マスタ検索キーをクリア
        _itmKey.clear();
        // 商品マスタ情報.荷主コード
        _itmKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        // 商品マスタ情報.商品コード
        _itmKey.setItemCode(loadItem.getItemCode());

        // 検索実行
        Item[] item = (Item[])_itmHandler.find(_itmKey);
        if (item != null && item.length > 0)
        {
            // データが存在する場合
            return item[0].getManagementType();
        }
        // データが存在しない場合
        return String.valueOf("");
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
        // 取得するテーブルのハンドラーを生成して下さい。
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
        return "$Id: codetemplates.xml 87 2008-10-04 03:07:38Z admin $";
    }
}

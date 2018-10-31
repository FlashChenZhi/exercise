// $Id: SupplierDataLoader.java 7636 2010-03-17 04:11:45Z okayama $
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
import jp.co.daifuku.wms.base.controller.SupplierController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SupplierHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.entity.Com_terminal;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.fileentity.HostSupplier;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;

/**
 * 仕入先マスタの取り込み処理を行います。<br>
 *
 * @version $Revision: 7636 $, $Date: 2010-03-17 13:11:45 +0900 (水, 17 3 2010) $
 * @author  H.Okayama
 * @author  Last commit: $Author: okayama $
 */
public class SupplierDataLoader
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
    /** 仕入先マスタハンドラ */
    private SupplierHandler _supHandler = null;

    /** 仕入先マスタ検索キー */
    private SupplierSearchKey _supKey = null;

    /** 入荷予定情報ハンドラ */
    private ReceivingPlanHandler _recHandler = null;

    /** 入荷予定情報検索キー */
    private ReceivingPlanSearchKey _recKey = null;

    /** TC予定情報ハンドラ */
    private CrossDockPlanHandler _cdHandler = null;

    /** TC予定情報検索キー */
    private CrossDockPlanSearchKey _cdKey = null;

    /** ユーザ情報 */
    private DfkUserInfo _userInfo = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 取り込み区分仕入先マスタの受信データの取り込みを行います。<BR>
     * 自動取込みから使用します。
     */
    public SupplierDataLoader()
    {
        super(ExchangeEnvironment.LOAD_DATA_TYPE_MASTER_SUPPLIER);
    }

    /**
     * 取り込み区分仕入先マスタの受信データの取り込みを行います。<BR>
     * WEB画面から使用します。
     * 
     * @param userinfo ユーザ情報
     * @param locale ロケール
     */
    public SupplierDataLoader(DfkUserInfo userinfo, Locale locale)
    {
        super(ExchangeEnvironment.LOAD_DATA_TYPE_MASTER_SUPPLIER, userinfo, locale);
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
        // 仕入先マスタ
        _supHandler = new SupplierHandler(getConnection());
        _supKey = new SupplierSearchKey();

        // 入荷予定情報
        _recHandler = new ReceivingPlanHandler(getConnection());
        _recKey = new ReceivingPlanSearchKey();

        // TC予定情報
        _cdHandler = new CrossDockPlanHandler(getConnection());
        _cdKey = new CrossDockPlanSearchKey();
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
        HostSupplier sup = (HostSupplier)ent;

        // 禁止文字を含むかチェック
        RESULT chk = hasNGParameterText(sup);
        if (!chk.equals(RESULT.LOAD))
        {
            return chk;
        }

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(sup.getLoadFlag()))
        {
            // 同一データが存在する場合はスキップ
            _supKey.clear();
            _supKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _supKey.setSupplierCode(sup.getSupplierCode());
            if (_supHandler.count(_supKey) > 0)
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025",
                        sup.getStoreMetaData().getFieldMetaData(HostSupplier.SUPPLIER_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        // 取消区分が削除の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(sup.getLoadFlag()))
        {
            // 取消対象データが存在しない場合
            _supKey.clear();
            _supKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _supKey.setSupplierCode(sup.getSupplierCode());
            if (_supHandler.count(_supKey) == 0)
            {
                // MSG-W0052=削除該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0052",
                        sup.getStoreMetaData().getFieldMetaData(HostSupplier.SUPPLIER_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 入荷パッケージが導入されている場合
            if (sysCtrl.hasReceivingPack())
            {
                // 入荷予定情報で使用されている場合はスキップ
                _recKey.clear();
                _recKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _recKey.setSupplierCode(sup.getSupplierCode());
                _recKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                if (_recHandler.count(_recKey) > 0)
                {
                    // MSG-W0054=データ使用中(削除不可)
                    insertLoadErrorInfo(
                            SystemDefine.ERROR_LEVEL_WARNING,
                            "MSG-W0054",
                            sup.getStoreMetaData().getFieldMetaData(HostSupplier.SUPPLIER_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // クロスドックパッケージが導入されている場合
            if (sysCtrl.hasCrossdockPack())
            {
                // TC予定情報で使用されている場合はスキップ
                _cdKey.clear();
                _cdKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _cdKey.setSupplierCode(sup.getSupplierCode());
                _cdKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                if (_cdHandler.count(_cdKey) > 0)
                {
                    // MSG-W0054=データ使用中(削除不可)
                    insertLoadErrorInfo(
                            SystemDefine.ERROR_LEVEL_WARNING,
                            "MSG-W0054",
                            sup.getStoreMetaData().getFieldMetaData(HostSupplier.SUPPLIER_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", sup.getStoreMetaData().getFieldMetaData(
                    HostSupplier.LOAD_FLAG.getName()).getDescription());
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
        // 仕入先マスタコントローラの生成
        SupplierController suppCtrl = new SupplierController(getConnection(), this.getClass());
        // 仕入先マスタエンティティ
        HostSupplier sup = (HostSupplier)ent;

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(sup.getLoadFlag()))
        {
            // 仕入先マスタエンティティ
            Supplier supplier = new Supplier();

            // 値の設定
            supplier.clear();
            supplier.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            supplier.setSupplierCode(sup.getSupplierCode());
            supplier.setSupplierName(sup.getSupplierName());
            supplier.setRegistPname(this.getClass().getSimpleName());
            supplier.setLastUpdatePname(this.getClass().getSimpleName());

            try
            {
                // 登録
                _supHandler.create(supplier);

                // 仕入先マスタ改廃履歴登録
                suppCtrl.insertHistory(supplier, SystemDefine.UPDATE_KIND_REGIST, this.getUserInfo());

                return RESULT.LOAD;
            }
            // 同一データがすでに存在した場合はスキップ
            catch (DataExistsException e)
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025",
                        sup.getStoreMetaData().getFieldMetaData(HostSupplier.SUPPLIER_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }
        }
        // 取消区分が削除の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(sup.getLoadFlag()))
        {
            // 値の設定
            _supKey.clear();
            _supKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _supKey.setSupplierCode(sup.getSupplierCode());

            try
            {
                // 削除
                Supplier supplier = (Supplier)_supHandler.findPrimary(_supKey);
                _supHandler.drop(_supKey);

                // 仕入先マスタ改廃履歴登録
                suppCtrl.insertHistory(supplier, SystemDefine.UPDATE_KIND_DELETE, this.getUserInfo());

                return RESULT.LOAD;
            }
            // 対象データなしの場合はスキップ
            catch (NotFoundException e)
            {
                // MSG-W0027=取消該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0027",
                        sup.getStoreMetaData().getFieldMetaData(HostSupplier.SUPPLIER_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }
        }
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", sup.getStoreMetaData().getFieldMetaData(
                    HostSupplier.LOAD_FLAG.getName()).getDescription());
            return RESULT.SKIP;
        }
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#getEntity()
     */
    @Override
    protected AbstractEntity getEntity()
    {
        return new HostSupplier();
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
        _supHandler.getStatics();
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
        return "$Id: SupplierDataLoader.java 7636 2010-03-17 04:11:45Z okayama $";
    }
}

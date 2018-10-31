// $Id: CustomerDataLoader.java 7636 2010-03-17 04:11:45Z okayama $
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
import jp.co.daifuku.wms.base.controller.CustomerController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Com_terminal;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.fileentity.HostCustomer;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;

/**
 * 出荷先マスタの取り込み処理を行います。<br>
 *
 * @version $Revision: 7636 $, $Date: 2010-03-17 13:11:45 +0900 (水, 17 3 2010) $
 * @author  H.Okayama
 * @author  Last commit: $Author: okayama $
 */
public class CustomerDataLoader
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
    /** 出荷先マスタハンドラ */
    private CustomerHandler _custHandler = null;

    /** 出荷先マスタ検索キー */
    private CustomerSearchKey _custKey = null;

    /** 出庫予定情報ハンドラ */
    private RetrievalPlanHandler _retHandler = null;

    /** 出庫予定情報検索キー */
    private RetrievalPlanSearchKey _retKey = null;

    /** 出荷予定情報ハンドラ */
    private ShipPlanHandler _shipHandler = null;

    /** 出荷予定情報検索キー */
    private ShipPlanSearchKey _shipKey = null;

    /** 入出庫作業情報ハンドラ */
    private WorkInfoHandler _wIHandler = null;

    /** 入出庫作業情報検索キー */
    private WorkInfoSearchKey _wIKey = null;

    /** ユーザ情報 */
    private DfkUserInfo _userInfo = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 取り込み区分仕入先マスタの受信データの取り込みを行います。<BR>
     * 自動取込みから使用します。
     */
    public CustomerDataLoader()
    {
        super(ExchangeEnvironment.LOAD_DATA_TYPE_MASTER_CUSTOMER);
    }

    /**
     * 取り込み区分仕入先マスタの受信データの取り込みを行います。<BR>
     * WEB画面から使用します。
     * 
     * @param userinfo ユーザ情報
     * @param locale ロケール
     */
    public CustomerDataLoader(DfkUserInfo userinfo, Locale locale)
    {
        super(ExchangeEnvironment.LOAD_DATA_TYPE_MASTER_CUSTOMER, userinfo, locale);
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
        // 出荷先マスタ
        _custHandler = new CustomerHandler(getConnection());
        _custKey = new CustomerSearchKey();

        // 出庫予定情報
        _retHandler = new RetrievalPlanHandler(getConnection());
        _retKey = new RetrievalPlanSearchKey();

        // 出荷予定情報
        _shipHandler = new ShipPlanHandler(getConnection());
        _shipKey = new ShipPlanSearchKey();

        // 入出庫作業情報
        _wIHandler = new WorkInfoHandler(getConnection());
        _wIKey = new WorkInfoSearchKey();
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
        HostCustomer cust = (HostCustomer)ent;

        // 禁止文字を含むかチェック
        RESULT chk = hasNGParameterText(cust);
        if (!chk.equals(RESULT.LOAD))
        {
            return chk;
        }

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(cust.getLoadFlag()))
        {
            // 同一データが存在する場合はスキップ
            _custKey.clear();
            _custKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _custKey.setCustomerCode(cust.getCustomerCode());
            if (_custHandler.count(_custKey) > 0)
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025",
                        cust.getStoreMetaData().getFieldMetaData(HostCustomer.CUSTOMER_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        // 取消区分が削除の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(cust.getLoadFlag()))
        {
            // 取消対象データが存在しない場合
            _custKey.clear();
            _custKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _custKey.setCustomerCode(cust.getCustomerCode());
            if (_custHandler.count(_custKey) == 0)
            {
                // MSG-W0052=削除該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0052",
                        cust.getStoreMetaData().getFieldMetaData(HostCustomer.CUSTOMER_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 出庫パッケージが導入されている場合
            if (sysCtrl.hasRetrievalPack())
            {
                // 出庫予定情報で使用されている場合はスキップ
                _retKey.clear();
                _retKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _retKey.setCustomerCode(cust.getCustomerCode());
                _retKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                if (_retHandler.count(_retKey) > 0)
                {
                    // MSG-W0054=データ使用中(削除不可)
                    insertLoadErrorInfo(
                            SystemDefine.ERROR_LEVEL_WARNING,
                            "MSG-W0054",
                            cust.getStoreMetaData().getFieldMetaData(HostCustomer.CUSTOMER_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // AS/RSパッケージが導入されている場合
            if (sysCtrl.hasAsrsPack())
            {
                // 入出庫作業情報で使用されている場合はスキップ
                _wIKey.clear();
                _wIKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _wIKey.setCustomerCode(cust.getCustomerCode());
                _wIKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                if (_wIHandler.count(_wIKey) > 0)
                {
                    // MSG-W0054=データ使用中(削除不可)
                    insertLoadErrorInfo(
                            SystemDefine.ERROR_LEVEL_WARNING,
                            "MSG-W0054",
                            cust.getStoreMetaData().getFieldMetaData(HostCustomer.CUSTOMER_CODE.getName()).getDescription());
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
                _shipKey.setCustomerCode(cust.getCustomerCode());
                _shipKey.setWorkStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=", "(", "", false);
                _shipKey.setBerthStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=", "", ")", true);
                if (_shipHandler.count(_shipKey) > 0)
                {
                    // MSG-W0054=データ使用中(削除不可)
                    insertLoadErrorInfo(
                            SystemDefine.ERROR_LEVEL_WARNING,
                            "MSG-W0054",
                            cust.getStoreMetaData().getFieldMetaData(HostCustomer.CUSTOMER_CODE.getName()).getDescription());
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
                    cust.getStoreMetaData().getFieldMetaData(HostCustomer.LOAD_FLAG.getName()).getDescription());
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
        // 出荷先マスタコントローラの生成
        CustomerController custCtrl = new CustomerController(getConnection(), this.getClass());
        // 取込出荷先情報
        HostCustomer cust = (HostCustomer)ent;

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(cust.getLoadFlag()))
        {
            // 出荷先マスタエンティティ
            Customer customer = new Customer();

            // 値の設定
            customer.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            customer.setCustomerCode(cust.getCustomerCode());
            customer.setCustomerName(cust.getCustomerName());
            customer.setRoute(cust.getRoute());
            customer.setPostalCode(cust.getPostalCode());
            customer.setPrefecture(cust.getPrefecture());
            customer.setAddress1(cust.getAddress1());
            customer.setAddress2(cust.getAddress2());
            customer.setTelephone(cust.getTelephone());
            customer.setContact1(cust.getContact1());
            customer.setContact2(cust.getContact2());
            customer.setSortingPlace(cust.getSortingPlace());
            customer.setRegistPname(this.getClass().getSimpleName());
            customer.setLastUpdatePname(this.getClass().getSimpleName());

            try
            {
                // 登録
                _custHandler.create(customer);

                // 出荷先マスタ改廃履歴登録
                custCtrl.insertHistory(customer, SystemDefine.UPDATE_KIND_REGIST, this.getUserInfo());

                return RESULT.LOAD;
            }
            // 同一データがすでに存在した場合はスキップ
            catch (DataExistsException e)
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025",
                        cust.getStoreMetaData().getFieldMetaData(HostCustomer.CUSTOMER_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }
        }
        // 取消区分が削除の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(cust.getLoadFlag()))
        {
            // 値の設定
            _custKey.clear();
            _custKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _custKey.setCustomerCode(cust.getCustomerCode());

            try
            {
                // 削除
                Customer customer = (Customer)_custHandler.findPrimary(_custKey);
                _custHandler.drop(_custKey);

                // 出荷先マスタ改廃履歴登録
                custCtrl.insertHistory(customer, SystemDefine.UPDATE_KIND_DELETE, this.getUserInfo());

                return RESULT.LOAD;
            }
            // 対象データなしの場合はスキップ
            catch (NotFoundException e)
            {
                // MSG-W0027=取消該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0027",
                        cust.getStoreMetaData().getFieldMetaData(HostCustomer.CUSTOMER_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }
        }
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068",
                    cust.getStoreMetaData().getFieldMetaData(HostCustomer.LOAD_FLAG.getName()).getDescription());
            return RESULT.SKIP;
        }
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#getEntity()
     */
    @Override
    protected AbstractEntity getEntity()
    {
        return new HostCustomer();
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
        _custHandler.getStatics();
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
        return "$Id: CustomerDataLoader.java 7636 2010-03-17 04:11:45Z okayama $";
    }
}

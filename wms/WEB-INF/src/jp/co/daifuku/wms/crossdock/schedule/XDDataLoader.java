// $Id: XDDataLoader.java 7637 2010-03-17 04:12:24Z okayama $
package jp.co.daifuku.wms.crossdock.schedule;

/*
 * Copyright(c) 2000-2010 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.CustomerController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.SupplierController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.entity.CrossDockPlan;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.base.entity.ShipPlan;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.fileentity.CrossDock;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * クロスドック予定データの取り込み処理を行います。<br>
 *
 * @version $Revision: 7637 $, $Date: 2010-03-17 13:12:24 +0900 (水, 17 3 2010) $
 * @author  H.Okayama
 * @author  Last commit: $Author: okayama $
 */
public class XDDataLoader
        extends AbstractDataLoaderForJava
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** TC予定情報ハンドラ */
    private CrossDockPlanHandler _crsHandler = null;

    /** TC予定情報検索キー */
    private CrossDockPlanSearchKey _crsKey = null;

    /** TC予定情報更新キー */
    private CrossDockPlanAlterKey _crsAKey = null;

    /** 入荷作業情報ハンドラ */
    private ReceivingWorkInfoHandler _recWHandler = null;

    /** 入荷作業情報更新キー */
    private ReceivingWorkInfoAlterKey _recWAKey = null;

    /** 出荷予定情報ハンドラ */
    private ShipPlanHandler _shipHandler = null;

    /** 出荷予定情報更新キー */
    private ShipPlanAlterKey _shipAKey = null;

    /** 商品マスタハンドラ */
    private ItemHandler _itemHandler = null;

    /** 商品マスタ検索キー */
    private ItemSearchKey _itemKey = null;

    /** 仕入先マスタハンドラ */
    private SupplierHandler _suppHandler = null;

    /** 仕入先マスタ検索キー */
    private SupplierSearchKey _suppKey = null;

    /** 出荷先マスタハンドラ */
    private CustomerHandler _custHandler = null;

    /** 出荷先マスタ検索キー */
    private CustomerSearchKey _custKey = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 取り込み区分出庫の受信データの取り込みを行います。<BR>
     * 自動取込みから使用します。
     */
    public XDDataLoader()
    {
        super(ExchangeEnvironment.DATA_TYPE_CROSSDOCK);
    }

    /**
     * 取り込み区分入荷の受信データの取り込みを行います。<BR>
     * WEB画面から使用します。
     * 
     * @param userinfo ユーザ情報
     * @param locale ロケール
     */
    public XDDataLoader(DfkUserInfo userinfo, Locale locale)
    {
        super(ExchangeEnvironment.DATA_TYPE_CROSSDOCK, userinfo, locale);
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
        // TC予定情報
        _crsHandler = new CrossDockPlanHandler(getConnection());
        _crsKey = new CrossDockPlanSearchKey();
        _crsAKey = new CrossDockPlanAlterKey();

        // 入荷作業情報
        _recWHandler = new ReceivingWorkInfoHandler(getConnection());
        _recWAKey = new ReceivingWorkInfoAlterKey();

        // 出荷予定情報
        _shipHandler = new ShipPlanHandler(getConnection());
        _shipAKey = new ShipPlanAlterKey();

        // 商品マスタ
        _itemHandler = new ItemHandler(getConnection());
        _itemKey = new ItemSearchKey();

        // 仕入先マスタ
        _suppHandler = new SupplierHandler(getConnection());
        _suppKey = new SupplierSearchKey();

        // 出荷先マスタ
        _custHandler = new CustomerHandler(getConnection());
        _custKey = new CustomerSearchKey();
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
        CrossDock crs = (CrossDock)ent;

        // 日付のフォーマットチェック
        RESULT chk = isDay(CrossDock.PLAN_DAY, crs.getPlanDay());
        if (!chk.equals(RESULT.LOAD))
        {
            return chk;
        }
        // 禁止文字を含むかチェック
        RESULT chk2 = hasNGParameterText(crs);
        if (!chk2.equals(RESULT.LOAD))
        {
            return chk2;
        }

        // 取込情報のチェック、及び補完
        checkParam(crs, sysCtrl);

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(crs.getCancelFlag()))
        {
            // マスタパッケージが導入されている場合
            if (sysCtrl.hasMasterPack())
            {
                // 出荷先コード存在チェック
                _custKey.clear();
                _custKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _custKey.setCustomerCode(crs.getCustomerCode());
                if (_custHandler.count(_custKey) == 0)
                {
                    // MSG-W0023=マスタ未登録
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                            crs.getStoreMetaData().getFieldMetaData(CrossDock.CUSTOMER_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }

                // 仕分場所チェック
                if (StringUtil.isBlank(crs.getSortingPlace()))
                {
                    // MSG-W0023=マスタ未登録
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                            crs.getStoreMetaData().getFieldMetaData(CrossDock.SORTING_PLACE.getName()).getDescription());
                    return RESULT.SKIP;
                }

                // 商品コード存在チェック
                _itemKey.clear();
                _itemKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _itemKey.setItemCode(crs.getItemCode());
                if (_itemHandler.count(_itemKey) == 0)
                {
                    // MSG-W0023=マスタ未登録
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                            crs.getStoreMetaData().getFieldMetaData(CrossDock.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }

                // 商品コードチェック(異常棚商品コード、簡易直行商品コード)
                if (WmsParam.IRREGULAR_ITEMCODE.equals(crs.getItemCode())
                        || WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE.equals(crs.getItemCode()))
                {
                    // MSG-W0029=データの値が不正
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                            crs.getStoreMetaData().getFieldMetaData(CrossDock.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }

                // 仕入先コード存在チェック
                _suppKey.clear();
                _suppKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _suppKey.setSupplierCode(crs.getSupplierCode());
                if (_suppHandler.count(_suppKey) == 0)
                {
                    // MSG-W0023=マスタ未登録
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                            crs.getStoreMetaData().getFieldMetaData(CrossDock.SUPPLIER_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }
            else
            {
                // 仕分場所チェック
                if (StringUtil.isBlank(crs.getSortingPlace()))
                {
                    // MSG-W0021=必須項目空白
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0021",
                            crs.getStoreMetaData().getFieldMetaData(CrossDock.SORTING_PLACE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // 同一キー(予定日 + [仕入先コード] + 入荷伝票No. + 入荷伝票行No.)に対する
            // 商品(商品コード + ロットNo.)一意チェック
            _crsKey.clear();
            _crsKey.setPlanDay(crs.getPlanDay());
            // 仕入先の一意チェックを行う場合
            if (WmsParam.IS_UNIQUE_CHECK_SUPPLIER)
            {
                _crsKey.setSupplierCode(crs.getSupplierCode());
            }
            _crsKey.setReceiveTicketNo(crs.getReceiveTicketNo());
            _crsKey.setReceiveLineNo(crs.getReceiveLineNo());
            _crsKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _crsKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            _crsKey.setItemCode(crs.getItemCode(), "!=", "(", "", false);
            _crsKey.setPlanLotNo(crs.getLotNo(), "!=", "", ")", true);
            if (_crsHandler.count(_crsKey) > 0)
            {
                // MSG-W0080=入荷伝票内に複数商品/ロットNo.
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0080",
                        crs.getStoreMetaData().getFieldMetaData(CrossDock.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 同一キー(予定日 + (出荷先コード) + 出荷伝票No. + 出荷伝票行No.)に対する
            // 商品(商品コード + ロットNo.)一意チェック
            _crsKey.clear();
            _crsKey.setPlanDay(crs.getPlanDay());
            // 出荷先の一意チェックを行う場合
            if (WmsParam.IS_UNIQUE_CHECK_CUSTOMER)
            {
                _crsKey.setCustomerCode(crs.getCustomerCode());
            }
            _crsKey.setShipTicketNo(crs.getShipTicketNo());
            _crsKey.setShipLineNo(crs.getShipLineNo());
            _crsKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _crsKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            _crsKey.setItemCode(crs.getItemCode(), "!=", "(", "", false);
            _crsKey.setPlanLotNo(crs.getLotNo(), "!=", "", ")", true);
            if (_crsHandler.count(_crsKey) > 0)
            {
                // MSG-W0081=出荷伝票内に複数商品/ロットNo.
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0081",
                        crs.getStoreMetaData().getFieldMetaData(CrossDock.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 数量チェック
            if (crs.getPlanQty() <= 0)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                        crs.getStoreMetaData().getFieldMetaData(CrossDock.PLAN_QTY.getName()).getDescription());
                return RESULT.SKIP;
            }
            else if (crs.getPlanQty() > WmsParam.MAX_TOTAL_QTY)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                        crs.getStoreMetaData().getFieldMetaData(CrossDock.PLAN_QTY.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 同一キー(予定日 + (出荷先コード) + 入荷伝票No. + 入荷伝票行No.
            //          + 出荷伝票No. + 出荷伝票行No. + (仕入先コード))チェック
            _crsKey.clear();
            _crsKey.setPlanDay(crs.getPlanDay());
            // 出荷先の一意チェックを行う場合
            if (WmsParam.IS_UNIQUE_CHECK_CUSTOMER)
            {
                _crsKey.setCustomerCode(crs.getCustomerCode());
            }
            _crsKey.setShipTicketNo(crs.getShipTicketNo());
            _crsKey.setShipLineNo(crs.getShipLineNo());
            _crsKey.setReceiveTicketNo(crs.getReceiveTicketNo());
            _crsKey.setReceiveLineNo(crs.getReceiveLineNo());
            // 仕入先の一意チェックを行う場合
            if (WmsParam.IS_UNIQUE_CHECK_SUPPLIER)
            {
                _crsKey.setSupplierCode(crs.getSupplierCode());
            }
            _crsKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _crsKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (_crsHandler.count(_crsKey) > 0)
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025", "");
                return RESULT.SKIP;
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        // 取消区分が取消の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(crs.getCancelFlag()))
        {
            // TC予定情報チェック
            RESULT res = checkCancelPlan(crs);
            if (!RESULT.LOAD.equals(res))
            {
                return res;
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        // 取消区分が上記以外であった場合
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", crs.getStoreMetaData().getFieldMetaData(
                    CrossDock.CANCEL_FLAG.getName()).getDescription());
            return RESULT.SKIP;
        }
    }

    /**
     * 取込情報のチェックを行い、必要があれば値の補完を行います。<BR>
     * 
     * @param crs 取込情報
     * @param sysCtrl システム定義コントローラ
     * @throws CommonException
     */
    protected void checkParam(CrossDock crs, WarenaviSystemController sysCtrl)
            throws CommonException
    {
        // バッチNo.が省略されていた場合
        if (StringUtil.isBlank(crs.getBatchNo()))
        {
            crs.setBatchNo(crs.getPlanDay());
        }

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(crs.getCancelFlag()))
        {
            // マスタパッケージが導入されている場合
            if (sysCtrl.hasMasterPack())
            {
                // 出荷先コードが指定されている場合
                if (!StringUtil.isBlank(crs.getCustomerCode()))
                {
                    // 検索
                    _custKey.clear();
                    _custKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                    _custKey.setCustomerCode(crs.getCustomerCode());
                    Customer[] ents = (Customer[])_custHandler.find(_custKey);
                    if (!ArrayUtil.isEmpty(ents))
                    {
                        // 出荷先名称
                        crs.setCustomerName(ents[0].getCustomerName());

                        // 仕分場所
                        if (StringUtil.isBlank(crs.getSortingPlace()))
                        {
                            crs.setSortingPlace(ents[0].getSortingPlace());
                        }
                    }
                }

                // 商品マスタ
                if (!StringUtil.isBlank(crs.getItemCode()))
                {
                    // 検索
                    _itemKey.clear();
                    _itemKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                    _itemKey.setItemCode(crs.getItemCode());
                    Item[] ents = (Item[])_itemHandler.find(_itemKey);
                    if (!ArrayUtil.isEmpty(ents))
                    {
                        // 商品名称
                        if (!StringUtil.isBlank(ents[0].getItemName()))
                        {
                            crs.setItemName(ents[0].getItemName());
                        }

                        // JANコード
                        if (!StringUtil.isBlank(ents[0].getJan()))
                        {
                            crs.setJan(ents[0].getJan());
                        }

                        // ケースITF
                        if (!StringUtil.isBlank(ents[0].getItf()))
                        {
                            crs.setItf(ents[0].getItf());
                        }

                        // ケース入り数
                        crs.setEnteringQty(ents[0].getEnteringQty());
                    }
                }

                // 仕入先マスタ
                if (!StringUtil.isBlank(crs.getSupplierCode()))
                {
                    // 検索
                    _suppKey.clear();
                    _suppKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                    _suppKey.setSupplierCode(crs.getSupplierCode());
                    Supplier[] ents = (Supplier[])_suppHandler.find(_suppKey);
                    if (!ArrayUtil.isEmpty(ents))
                    {
                        // 仕入先名称
                        crs.setSupplierName(ents[0].getSupplierName());
                    }
                }
            }
        }
    }

    /**
     * TC予定情報の存在、状態チェックを行う。<BR>
     * (取消時)
     * 
     * @param crs 取込情報
     * @return 取込可否フラグ
     * @throws CommonException
     */
    protected RESULT checkCancelPlan(CrossDock crs)
            throws CommonException
    {
        // 未登録データの場合はスキップとする
        // 未作業の同一データを検索し、なければスキップとする
        // 同一条件 = 予定日 + (出荷先コード) + 入荷伝票No. + 入荷伝票行No.
        //            + 出荷伝票No. + 出荷伝票行No. + (仕入先コード)
        // 同時に入荷作業情報と出荷予定情報もロックを行う
        _crsKey.clear();
        _crsKey.setPlanDay(crs.getPlanDay());

        // 出荷先の一意チェックを行う場合
        if (WmsParam.IS_UNIQUE_CHECK_CUSTOMER)
        {
            _crsKey.setCustomerCode(crs.getCustomerCode());
        }
        _crsKey.setShipTicketNo(crs.getShipTicketNo());
        _crsKey.setShipLineNo(crs.getShipLineNo());
        _crsKey.setReceiveTicketNo(crs.getReceiveTicketNo());
        _crsKey.setReceiveLineNo(crs.getReceiveLineNo());

        // 仕入先の一意チェックを行う場合
        if (WmsParam.IS_UNIQUE_CHECK_SUPPLIER)
        {
            _crsKey.setSupplierCode(crs.getSupplierCode());
        }
        _crsKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        _crsKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // 結合条件
        _crsKey.setJoin(CrossDockPlan.CROSS_DOCK_UKEY, ShipPlan.CROSS_DOCK_UKEY);
        _crsKey.setJoin(CrossDockPlan.PLAN_UKEY, ReceivingWorkInfo.PLAN_UKEY);

        try
        {
            CrossDockPlan plan = (CrossDockPlan)_crsHandler.findPrimaryForUpdate(_crsKey);
            if (plan == null)
            {
                // MSG-W0027=取消該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0027", "");
                return RESULT.SKIP;
            }
            else if (!CrossDockPlan.STATUS_FLAG_UNSTART.equals(plan.getStatusFlag()))
            {
                // MSG-W0028=取消データ作業開始済み
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0028", "");
                return RESULT.SKIP;
            }
        }
        catch (LockTimeOutException e)
        {
            // MSG-W0030=取消データ他端末作業中
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0030", "");
            return RESULT.SKIP;
        }

        // エラーなしの場合は取込可能
        return RESULT.LOAD;
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#loadData(jp.co.daifuku.wms.handler.Entity)
     */
    @Override
    protected RESULT loadData(Entity ent)
            throws CommonException
    {
        // システム定義コントローラ
        WarenaviSystemController sysCtrl = new WarenaviSystemController(getConnection(), this.getClass());
        CrossDock crs = (CrossDock)ent;

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(crs.getCancelFlag()))
        {
            try
            {
                // シーケンスハンドラ
                WMSSequenceHandler _seq = new WMSSequenceHandler(getConnection());
                String planUKey = _seq.nextCrossDockPlanUkey();
                String uKey = _seq.nextCrossDockUkey();

                // TC予定情報の登録
                createCrossDockPlan(crs, planUKey, uKey);

                // 入荷作業情報の登録
                String jobNo = _seq.nextReceivingJobNo();
                createReceivingWorkInfo(crs, planUKey, uKey, jobNo);

                // 出荷予定情報の登録
                String sPlanUKey = _seq.nextShipPlanUkey();
                createShipPlan(crs, sPlanUKey, uKey);

                // マスタパッケージが導入されていない場合
                if (!sysCtrl.hasMasterPack())
                {
                    // 商品マスタの登録
                    createItem(crs);
                    // 仕入先マスタの登録
                    createSupplier(crs);
                    // 出荷先マスタの登録
                    createCustomer(crs);
                }

                // エラーなしの場合は取込可能
                return RESULT.LOAD;
            }
            // 同一データがすでに存在した場合はスキップ
            catch (DataExistsException e)
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025", "");
                return RESULT.SKIP;
            }
        }
        // 取消区分が取消の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(crs.getCancelFlag()))
        {
            try
            {
                // 削除対象のロック、削除キー取得
                CrossDockPlan plan = new CrossDockPlan();
                plan = lockCrossDock(crs);

                // TC予定情報の論理削除
                deleteCrossDockPlan(plan);
                // 入荷作業情報の論理削除
                deleteReceivingWorkInfo(plan);
                // 出荷予定情報の論理削除
                deleteShipPlan(plan);
            }
            // 対象データなしの場合はスキップ
            catch (NotFoundException e)
            {
                // MSG-W0027=取消該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0027", "");
                return RESULT.SKIP;
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        // 取消区分が上記以外であった場合
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", crs.getStoreMetaData().getFieldMetaData(
                    CrossDock.CANCEL_FLAG.getName()).getDescription());
            return RESULT.SKIP;
        }
    }

    /**
     * TC予定情報の登録を行います。
     * 
     * @param crs 取込情報
     * @param planUkey 予定一意キー(TC予定情報用)
     * @param uKey クロスドック連携キー
     * @return 生成した登録データ
     * @throws CommonException
     */
    protected void createCrossDockPlan(CrossDock crs, String planUKey, String uKey)
            throws CommonException
    {
        // TC予定情報エンティティ
        CrossDockPlan crsPlan = new CrossDockPlan();

        // 値の設定
        crsPlan.setPlanUkey(planUKey);
        crsPlan.setCrossDockUkey(uKey);
        crsPlan.setLoadUnitKey(getSourceFileNameOutOfExtention());
        crsPlan.setFileLineNo(getDataCnt());
        crsPlan.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        crsPlan.setCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        crsPlan.setPlanDay(crs.getPlanDay());
        crsPlan.setBatchNo(crs.getBatchNo());
        crsPlan.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        crsPlan.setSupplierCode(crs.getSupplierCode());
        crsPlan.setReceiveTicketNo(crs.getReceiveTicketNo());
        crsPlan.setReceiveLineNo(crs.getReceiveLineNo());
        crsPlan.setCustomerCode(crs.getCustomerCode());
        crsPlan.setShipTicketNo(crs.getShipTicketNo());
        crsPlan.setShipLineNo(crs.getShipLineNo());
        crsPlan.setSortingPlace(crs.getSortingPlace());
        crsPlan.setItemCode(crs.getItemCode());
        crsPlan.setPlanLotNo(crs.getLotNo());
        crsPlan.setPlanQty(crs.getPlanQty());
        crsPlan.setReceiveResultQty(0);
        crsPlan.setReceiveShortageQty(0);
        crsPlan.setSortResultQty(0);
        crsPlan.setSortShortageQty(0);
        crsPlan.setResultQty(0);
        crsPlan.setShortageQty(0);
        crsPlan.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
        crsPlan.setWorkDay("");
        crsPlan.setRegistKind(SystemDefine.REGIST_KIND_DATALOADER);
        crsPlan.setRegistPname(this.getClass().getSimpleName());
        crsPlan.setLastUpdatePname(this.getClass().getSimpleName());

        // 登録
        _crsHandler.create(crsPlan);
    }

    /**
     * 入荷作業情報の登録を行います。
     * 
     * @param crs 取込情報
     * @param planUkey 予定一意キー(TC予定情報用)
     * @param uKey クロスドック連携キー
     * @param jobNo 作業No.
     * @return 生成した登録データ
     * @throws CommonException
     */
    protected void createReceivingWorkInfo(CrossDock crs, String planUKey, String uKey, String jobNo)
            throws CommonException
    {
        ReceivingWorkInfo recWorkInfo = new ReceivingWorkInfo();

        // 値の設定
        recWorkInfo.setJobNo(jobNo);
        recWorkInfo.setSettingUnitKey("");
        recWorkInfo.setCollectJobNo("");
        recWorkInfo.setCrossDockUkey(uKey);
        recWorkInfo.setJobType(SystemDefine.JOB_TYPE_RECEIVING);
        recWorkInfo.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        recWorkInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);
        recWorkInfo.setPlanUkey(planUKey);
        recWorkInfo.setTcdcFlag(SystemDefine.TCDC_FLAG_TC);
        recWorkInfo.setPlanDay(crs.getPlanDay());
        recWorkInfo.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        recWorkInfo.setSupplierCode(crs.getSupplierCode());
        recWorkInfo.setReceiveTicketNo(crs.getReceiveTicketNo());
        recWorkInfo.setReceiveLineNo(crs.getReceiveLineNo());
        recWorkInfo.setItemCode(crs.getItemCode());
        recWorkInfo.setPlanLotNo(crs.getLotNo());
        recWorkInfo.setResultLotNo("");
        recWorkInfo.setPlanQty(crs.getPlanQty());
        recWorkInfo.setResultQty(0);
        recWorkInfo.setShortageQty(0);
        recWorkInfo.setWorkDay("");
        recWorkInfo.setUserId("");
        recWorkInfo.setTerminalNo("");
        recWorkInfo.setWorkSecond(0);
        recWorkInfo.setRegistPname(this.getClass().getSimpleName());
        recWorkInfo.setLastUpdatePname(this.getClass().getSimpleName());

        // 登録
        _recWHandler.create(recWorkInfo);
    }

    /**
     * 出荷予定情報の登録を行います。
     * 
     * @param crs 取込情報
     * @param planUkey 予定一意キー(出荷予定情報用)
     * @param uKey クロスドック連携キー
     * @return 生成した登録データ
     * @throws CommonException
     */
    protected void createShipPlan(CrossDock crs, String sPlanUKey, String uKey)
            throws CommonException
    {
        // 出荷予定情報エンティティ
        ShipPlan shipPlan = new ShipPlan();

        // 値の設定
        shipPlan.setPlanUkey(sPlanUKey);
        shipPlan.setCrossDockUkey(uKey);
        shipPlan.setLoadUnitKey(getSourceFileNameOutOfExtention());
        shipPlan.setFileLineNo(getDataCnt());
        shipPlan.setWorkStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        shipPlan.setBerthStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        shipPlan.setCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        shipPlan.setTcdcFlag(SystemDefine.TCDC_FLAG_TC);
        shipPlan.setPlanDay(crs.getPlanDay());
        shipPlan.setBatchNo(crs.getBatchNo());
        shipPlan.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        shipPlan.setCustomerCode(crs.getCustomerCode());
        shipPlan.setShipTicketNo(crs.getShipTicketNo());
        shipPlan.setShipLineNo(crs.getShipLineNo());
        shipPlan.setItemCode(crs.getItemCode());
        shipPlan.setPlanLotNo(crs.getLotNo());
        shipPlan.setDistributedQty(0);
        shipPlan.setPlanQty(crs.getPlanQty());
        shipPlan.setResultQty(0);
        shipPlan.setShortageQty(0);
        shipPlan.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
        shipPlan.setWorkDay("");
        shipPlan.setRegistKind(SystemDefine.REGIST_KIND_DATALOADER);
        shipPlan.setRegistPname(this.getClass().getSimpleName());
        shipPlan.setLastUpdatePname(this.getClass().getSimpleName());

        // 登録
        _shipHandler.create(shipPlan);
    }

    /**
     * 商品マスタの登録、及び更新を行います。
     * 
     * @param crs 取込情報
     * @throws CommonException
     */
    protected void createItem(CrossDock crs)
            throws CommonException
    {
        // 商品コードが入力されていない場合は処理しない
        if (StringUtil.isBlank(crs.getItemCode()))
        {
            return;
        }

        // 商品情報コントローラ
        ItemController itemctrl = new ItemController(getConnection(), this.getClass());
        // 商品マスタエンティティ
        Item item = new Item();

        // 値の設定
        item.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
        item.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        item.setItemCode(crs.getItemCode());

        // 商品名称が指定されている場合
        if (!StringUtil.isBlank(crs.getItemName()))
        {
            item.setItemName(crs.getItemName());
        }

        // JANコードが指定されている場合
        if (!StringUtil.isBlank(crs.getJan()))
        {
            item.setJan(crs.getJan());
        }

        // ITFが指定されている場合
        if (!StringUtil.isBlank(crs.getItf()))
        {
            item.setItf(crs.getItf());
        }

        // ケース入り数が0以上の場合
        if (crs.getEnteringQty() >= 0)
        {
            item.setEnteringQty(crs.getEnteringQty());
        }
        item.setRegistPname(this.getClass().getSimpleName());
        item.setLastUpdatePname(this.getClass().getSimpleName());

        // 登録
        WmsUserInfo ui = new WmsUserInfo();
        itemctrl.autoCreate(item, ui);
    }

    /**
     * 仕入先マスタの登録、及び更新を行います。
     * 
     * @param crs 取込情報
     * @throws CommonException
     */
    protected void createSupplier(CrossDock crs)
            throws CommonException
    {
        // 仕入先コードが入力されていない場合は処理しない
        if (StringUtil.isBlank(crs.getSupplierCode()))
        {
            return;
        }

        // 仕入先情報コントローラ
        SupplierController suppCtrl = new SupplierController(getConnection(), this.getClass());
        Supplier supplier = new Supplier();

        // 値の設定
        supplier.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        supplier.setSupplierCode(crs.getSupplierCode());

        // 仕入先名称が指定されている場合
        if (!StringUtil.isBlank(crs.getSupplierName()))
        {
            supplier.setSupplierName(crs.getSupplierName());
        }
        supplier.setRegistPname(this.getClass().getSimpleName());
        supplier.setLastUpdatePname(this.getClass().getSimpleName());

        // 登録
        WmsUserInfo ui = new WmsUserInfo();
        suppCtrl.autoCreate(supplier, ui);
    }

    /**
     * 出荷先マスタの登録、及び更新を行います。
     * 
     * @param crs 取込情報
     * @throws CommonException
     */
    protected void createCustomer(CrossDock crs)
            throws CommonException
    {
        // 出荷先コードが入力されていない場合は処理しない
        if (StringUtil.isBlank(crs.getCustomerCode()))
        {
            return;
        }

        // 出荷先マスタコントローラ
        CustomerController custctrl = new CustomerController(getConnection(), this.getClass());
        // 出荷先マスタエンティティ
        Customer cust = new Customer();

        // 値の設定
        cust.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        cust.setCustomerCode(crs.getCustomerCode());

        // 出荷先名称が指定されている場合
        if (!StringUtil.isBlank(crs.getCustomerName()))
        {
            cust.setCustomerName(crs.getCustomerName());
        }

        // 仕分場所が指定されている場合
        if (!StringUtil.isBlank(crs.getSortingPlace()))
        {
            cust.setSortingPlace(crs.getSortingPlace());
        }
        cust.setRegistPname(this.getClass().getSimpleName());
        cust.setLastUpdatePname(this.getClass().getSimpleName());

        // 登録
        WmsUserInfo ui = new WmsUserInfo();
        custctrl.autoCreate(cust, ui);
    }

    /**
     * 削除対象のロックを行い、削除キーを取得します。
     * 
     * @param crs 取込情報
     * @return 削除対象エンティティ
     * @throws CommonException
     */
    protected CrossDockPlan lockCrossDock(CrossDock crs)
            throws CommonException
    {
        // 削除対象の削除キーを取得する
        _crsKey.clear();
        _crsKey.setPlanDay(crs.getPlanDay());

        // 出荷先の一意チェックを行う場合
        if (WmsParam.IS_UNIQUE_CHECK_CUSTOMER)
        {
            _crsKey.setCustomerCode(crs.getCustomerCode());
        }
        _crsKey.setShipTicketNo(crs.getShipTicketNo());
        _crsKey.setShipLineNo(crs.getShipLineNo());
        _crsKey.setReceiveTicketNo(crs.getReceiveTicketNo());
        _crsKey.setReceiveLineNo(crs.getReceiveLineNo());

        // 仕入先の一意チェックを行う場合
        if (WmsParam.IS_UNIQUE_CHECK_SUPPLIER)
        {
            _crsKey.setSupplierCode(crs.getSupplierCode());
        }
        _crsKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        _crsKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // 取得項目
        _crsKey.setCollect(new FieldName(CrossDockPlan.STORE_NAME, FieldName.ALL_FIELDS));
        _crsKey.setCollect(ShipPlan.PLAN_UKEY);
        _crsKey.setCollect(ReceivingWorkInfo.JOB_NO);

        // 結合条件
        _crsKey.setJoin(CrossDockPlan.CROSS_DOCK_UKEY, ShipPlan.CROSS_DOCK_UKEY);
        _crsKey.setJoin(CrossDockPlan.PLAN_UKEY, ReceivingWorkInfo.PLAN_UKEY);

        // LOCK検索
        CrossDockPlan plan = (CrossDockPlan)_crsHandler.findPrimaryForUpdate(_crsKey);

        if (plan == null)
        {
            throw new NotFoundException();
        }
        else
        {
            return plan;
        }
    }

    /**
     * TC予定情報の削除を行います。
     * 
     * @param rec 取込情報
     * @throws CommonException
     */
    protected void deleteCrossDockPlan(CrossDockPlan plan)
            throws CommonException
    {
        // 条件の設定
        _crsAKey.clear();
        _crsAKey.setPlanUkey((String)plan.getValue(CrossDockPlan.PLAN_UKEY));

        // 値の設定
        _crsAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        _crsAKey.updateCancelFlag(SystemDefine.CANCEL_FLAG_HOST_CANCEL);
        _crsAKey.updateLastUpdatePname(this.getClass().getSimpleName());

        // 更新
        _crsHandler.modify(_crsAKey);
    }

    /**
     * 入荷作業情報の削除を行います。
     * 
     * @param rec 取込情報
     * @throws CommonException
     */
    protected void deleteReceivingWorkInfo(CrossDockPlan plan)
            throws CommonException
    {
        // 条件の設定
        _recWAKey.clear();
        _recWAKey.setJobNo((String)plan.getValue(ReceivingWorkInfo.JOB_NO));

        // 値の設定
        _recWAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        _recWAKey.updateLastUpdatePname(this.getClass().getSimpleName());

        // 更新
        _recWHandler.modify(_recWAKey);
    }

    /**
     * 出荷予定情報の削除を行います。
     * 
     * @param rec 取込情報
     * @throws CommonException
     */
    protected void deleteShipPlan(CrossDockPlan plan)
            throws CommonException
    {
        // 条件の設定
        _shipAKey.clear();
        _shipAKey.setPlanUkey((String)plan.getValue(ShipPlan.PLAN_UKEY));

        // 値の設定
        _shipAKey.updateWorkStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        _shipAKey.updateBerthStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        _shipAKey.updateCancelFlag(SystemDefine.CANCEL_FLAG_HOST_CANCEL);
        _shipAKey.updateLastUpdatePname(this.getClass().getSimpleName());

        // 更新
        _shipHandler.modify(_shipAKey);
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#getEntity()
     */
    @Override
    protected AbstractEntity getEntity()
    {
        return new CrossDock();
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
        _crsHandler.getStatics();
        _recWHandler.getStatics();
        _itemHandler.getStatics();
        _suppHandler.getStatics();
        _custHandler.getStatics();
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
        return "$Id: XDDataLoader.java 7637 2010-03-17 04:12:24Z okayama $";
    }
}

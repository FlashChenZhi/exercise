// $Id: ReceivingDataLoader.java 7636 2010-03-17 04:11:45Z okayama $
package jp.co.daifuku.wms.receiving.schedule;

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
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.SupplierController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SupplierHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ReceivingPlan;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.fileentity.Receiving;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;

/**
 * 入荷予定データの取り込み処理を行います。<br>
 *
 * @version $Revision: 7636 $, $Date: 2010-03-17 13:11:45 +0900 (水, 17 3 2010) $
 * @author  H.Okayama
 * @author  Last commit: $Author: okayama $
 */
public class ReceivingDataLoader
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
    /** 入荷予定ハンドラ */
    private ReceivingPlanHandler _recHander = null;

    /** 入荷予定検索キー */
    private ReceivingPlanSearchKey _recKey = null;

    /** 入荷予定更新キー */
    private ReceivingPlanAlterKey _recAKey = null;

    /** 入荷作業ハンドラ */
    private ReceivingWorkInfoHandler _recWHandler = null;

    /** 入荷作業検索キー */
    private ReceivingWorkInfoSearchKey _recWKey = null;

    /** 入荷作業更新キー */
    private ReceivingWorkInfoAlterKey _recWAKey = null;

    /** 商品マスタハンドラ */
    private ItemHandler _itemHandler = null;

    /** 商品マスタ検索キー */
    private ItemSearchKey _itemKey = null;

    /** 仕入先マスタハンドラ */
    private SupplierHandler _suppHandler = null;

    /** 仕入先マスタ検索キー */
    private SupplierSearchKey _suppKey = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 取り込み区分出庫の受信データの取り込みを行います。<BR>
     * 自動取込みから使用します。
     */
    public ReceivingDataLoader()
    {
        super(ExchangeEnvironment.LOAD_DATA_TYPE_RECEVING);
    }

    /**
     * 取り込み区分入荷の受信データの取り込みを行います。<BR>
     * WEB画面から使用します。
     * 
     * @param userinfo ユーザ情報
     * @param locale ロケール
     */
    public ReceivingDataLoader(DfkUserInfo userinfo, Locale locale)
    {
        super(ExchangeEnvironment.LOAD_DATA_TYPE_RECEVING, userinfo, locale);
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
        // 入荷作業情報
        _recHander = new ReceivingPlanHandler(getConnection());
        _recKey = new ReceivingPlanSearchKey();
        _recAKey = new ReceivingPlanAlterKey();

        // 入荷作業情報
        _recWHandler = new ReceivingWorkInfoHandler(getConnection());
        _recWKey = new ReceivingWorkInfoSearchKey();
        _recWAKey = new ReceivingWorkInfoAlterKey();

        // 商品マスタ
        _itemHandler = new ItemHandler(getConnection());
        _itemKey = new ItemSearchKey();

        // 仕入先マスタ
        _suppHandler = new SupplierHandler(getConnection());
        _suppKey = new SupplierSearchKey();
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
        Receiving rec = (Receiving)ent;

        // 日付のフォーマットチェック
        RESULT chk = isDay(Receiving.PLAN_DAY, rec.getPlanDay());
        if (!chk.equals(RESULT.LOAD))
        {
            return chk;
        }
        // 禁止文字を含むかチェック
        RESULT chk2 = hasNGParameterText(rec);
        if (!chk2.equals(RESULT.LOAD))
        {
            return chk2;
        }

        // 取込情報のチェック、及び補完
        checkParam(rec, sysCtrl);

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(rec.getCancelFlag()))
        {
            // マスタパッケージが導入されている場合
            if (sysCtrl.hasMasterPack())
            {
                // 仕入先コード存在チェック
                _suppKey.clear();
                _suppKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _suppKey.setSupplierCode(rec.getSupplierCode());
                if (_suppHandler.count(_suppKey) == 0)
                {
                    // MSG-W0023=マスタ未登録
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                            rec.getStoreMetaData().getFieldMetaData(Receiving.SUPPLIER_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }

                // 商品コード存在チェック
                _itemKey.clear();
                _itemKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _itemKey.setItemCode(rec.getItemCode());
                if (_itemHandler.count(_itemKey) == 0)
                {
                    // MSG-W0023=マスタ未登録
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                            rec.getStoreMetaData().getFieldMetaData(Receiving.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }

                // 商品コードチェック(異常棚商品コード、簡易直行商品コード)
                if (WmsParam.IRREGULAR_ITEMCODE.equals(rec.getItemCode())
                        || WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE.equals(rec.getItemCode()))
                {
                    // MSG-W0029=データの値が不正
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                            rec.getStoreMetaData().getFieldMetaData(Receiving.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // 数量チェック
            if (rec.getPlanQty() <= 0)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                        rec.getStoreMetaData().getFieldMetaData(Receiving.PLAN_QTY.getName()).getDescription());
                return RESULT.SKIP;
            }
            else if (rec.getPlanQty() > WmsParam.MAX_TOTAL_QTY)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                        rec.getStoreMetaData().getFieldMetaData(Receiving.PLAN_QTY.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 同キー(入荷予定日＋仕入先コード + 入荷伝票No.＋入荷伝票行No.)チェック
            _recKey.clear();
            _recKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _recKey.setPlanDay(rec.getPlanDay());
            _recKey.setSupplierCode(rec.getSupplierCode());
            _recKey.setReceiveTicketNo(rec.getReceiveTicketNo());
            _recKey.setReceiveLineNo(rec.getReceiveLineNo());
            _recKey.setTcdcFlag(ReceivingPlan.TCDC_FLAG_DC);
            _recKey.setStatusFlag(ReceivingPlan.STATUS_FLAG_DELETE, "!=");
            if (_recHander.count(_recKey) > 0)
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025", "");
                return RESULT.SKIP;
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        // 取消区分が取消の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(rec.getCancelFlag()))
        {
            // 入荷予定情報チェック
            RESULT res = checkCancelPlan(rec);
            if (!RESULT.LOAD.equals(res))
            {
                return res;
            }

            // 在庫パッケージが導入されていない場合
            if (!sysCtrl.hasStockPack())
            {
                // 入荷作業情報チェック
                res = checkCancelWorkInfo(rec);
                if (!RESULT.LOAD.equals(res))
                {
                    return res;
                }
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        // 取消区分が上記以外であった場合
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", rec.getStoreMetaData().getFieldMetaData(
                    Receiving.CANCEL_FLAG.getName()).getDescription());
            return RESULT.SKIP;
        }
    }

    /**
     * 取込情報のチェックを行い、必要があれば値の補完を行います。<BR>
     * 
     * @param rec 取込情報
     * @param sysCtrl システム定義情報コントローラ
     * @throws CommonException
     */
    protected void checkParam(Receiving rec, WarenaviSystemController sysCtrl)
            throws CommonException
    {
        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(rec.getCancelFlag()))
        {
            // マスタパッケージが導入されている場合
            if (sysCtrl.hasMasterPack())
            {
                // 仕入先コードが指定されている場合
                if (!StringUtil.isBlank(rec.getSupplierCode()))
                {
                    // 検索
                    _suppKey.clear();
                    _suppKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                    _suppKey.setSupplierCode(rec.getSupplierCode());
                    Supplier[] ents = (Supplier[])_suppHandler.find(_suppKey);
                    if (!ArrayUtil.isEmpty(ents))
                    {
                        // 仕入先名称
                        rec.setSupplierName(ents[0].getSupplierName());
                    }
                }

                // 商品コードが指定されている場合
                if (!StringUtil.isBlank(rec.getItemCode()))
                {
                    // 検索
                    _itemKey.clear();
                    _itemKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                    _itemKey.setItemCode(rec.getItemCode());
                    Item[] ents = (Item[])_itemHandler.find(_itemKey);
                    if (!ArrayUtil.isEmpty(ents))
                    {
                        // 商品名称が省略されている場合
                        if (!StringUtil.isBlank(ents[0].getItemName()))
                        {
                            rec.setItemName(ents[0].getItemName());
                        }

                        // JANコードが省略されている場合
                        if (!StringUtil.isBlank(ents[0].getJan()))
                        {
                            rec.setJan(ents[0].getJan());
                        }

                        // ITFコードが省略されている場合
                        if (!StringUtil.isBlank(ents[0].getItf()))
                        {
                            rec.setItf(rec.getItf());
                        }

                        // ケース入り数
                        rec.setEnteringQty(ents[0].getEnteringQty());
                    }
                }
            }
        }

    }

    /**
     * 入荷予定情報の存在、状態チェックを行う。<BR>
     * (取消時)
     * 
     * @param rec 取込情報
     * @return 取込可否フラグ
     * @throws CommonException
     */
    protected RESULT checkCancelPlan(Receiving rec)
            throws CommonException
    {
        // 未登録データの場合はスキップとする
        // 未作業の同一データを検索し、なければスキップとする
        // 同一条件 = 入荷予定日 + 仕入先コード + 入荷伝票No. + 入荷伝票行No.
        _recKey.clear();
        _recKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        _recKey.setPlanDay(rec.getPlanDay());
        _recKey.setSupplierCode(rec.getSupplierCode());
        _recKey.setReceiveTicketNo(rec.getReceiveTicketNo());
        _recKey.setReceiveLineNo(rec.getReceiveLineNo());
        _recKey.setTcdcFlag(ReceivingPlan.TCDC_FLAG_DC);
        _recKey.setStatusFlag(ReceivingPlan.STATUS_FLAG_DELETE, "!=");

        try
        {
            ReceivingPlan[] recPlans = (ReceivingPlan[])_recHander.findForUpdate(_recKey);
            if (recPlans == null || recPlans.length == 0)
            {
                // MSG-W0027=取消該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0027", "");
                return RESULT.SKIP;
            }
            else if (!ReceivingPlan.STATUS_FLAG_UNSTART.equals(recPlans[0].getStatusFlag()))
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

    /**
     * 入荷作業情報の存在、状態チェックを行う。<BR>
     * (取消時)
     * 
     * @param rec 取込情報
     * @return 取込可否フラグ
     * @throws CommonException
     */
    protected RESULT checkCancelWorkInfo(Receiving rec)
            throws CommonException
    {
        // 未登録のデータの場合はスキップとする
        // 未作業の同一データを検索し、なければスキップとする
        // 同一条件 = 入荷予定日 + 仕入先コード + 入荷伝票No. + 入荷伝票行No.
        _recWKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        _recWKey.setPlanDay(rec.getPlanDay());
        _recWKey.setSupplierCode(rec.getSupplierCode());
        _recWKey.setReceiveTicketNo(rec.getReceiveTicketNo());
        _recWKey.setReceiveLineNo(rec.getReceiveLineNo());
        _recWKey.setKey(ReceivingWorkInfo.JOB_TYPE, SystemDefine.JOB_TYPE_RECEIVING);
        _recWKey.setTcdcFlag(ReceivingPlan.TCDC_FLAG_DC);
        _recWKey.setStatusFlag(ReceivingWorkInfo.STATUS_FLAG_DELETE, "!=");

        try
        {
            ReceivingWorkInfo[] recWorkInfos = (ReceivingWorkInfo[])_recWHandler.findForUpdate(_recWKey);
            if (recWorkInfos == null || recWorkInfos.length == 0)
            {
                // MSG-W0027=取消該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0027", "");
                return RESULT.SKIP;
            }
            else if (!ReceivingWorkInfo.STATUS_FLAG_UNSTART.equals(recWorkInfos[0].getStatusFlag()))
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
        // システム定義情報コントローラ
        WarenaviSystemController sysCtrl = new WarenaviSystemController(getConnection(), this.getClass());
        Receiving rec = (Receiving)ent;

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(rec.getCancelFlag()))
        {
            try
            {
                // シーケンスハンドラ
                WMSSequenceHandler _seq = new WMSSequenceHandler(getConnection());
                String planUKey = _seq.nextReceivingPlanUkey();

                // 入荷予定情報の登録
                createReceivingPlan(rec, planUKey);
                // 入荷作業情報の登録
                String jobNo = _seq.nextReceivingJobNo();
                createReceivingWorkInfo(rec, planUKey, jobNo);

                // マスタパッケージが導入されていない場合
                if (!sysCtrl.hasMasterPack())
                {
                    // 商品マスタの登録
                    createItem(rec);
                    // 仕入先マスタの登録
                    createSupplier(rec);
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
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(rec.getCancelFlag()))
        {
            try
            {
                // 入荷予定情報の論理削除
                deleteReceivingPlan(rec);
                // 入荷作業情報の論理削除
                deleteReceivingWorkInfo(rec);

                // エラーなしの場合は取込可能
                return RESULT.LOAD;
            }
            // 対象データなしの場合はスキップ
            catch (NotFoundException e)
            {
                // MSG-W0027=取消該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0027", "");
                return RESULT.SKIP;
            }
        }
        // 取消区分が上記以外であった場合
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", rec.getStoreMetaData().getFieldMetaData(
                    Receiving.CANCEL_FLAG.getName()).getDescription());
            return RESULT.SKIP;
        }
    }

    /**
     * 入荷予定情報の登録を行います。
     * 
     * @param rec 取込情報
     * @param planUkey 予定一意キー
     * @return 生成した登録データ
     * @throws CommonException
     */
    protected void createReceivingPlan(Receiving rec, String planUkey)
            throws CommonException
    {
        // 入荷予定情報エンティティ
        ReceivingPlan recPlan = new ReceivingPlan();

        // 値の設定
        recPlan.setPlanUkey(planUkey);
        recPlan.setLoadUnitKey(getSourceFileNameOutOfExtention());
        recPlan.setFileLineNo(getDataCnt());
        recPlan.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        recPlan.setTcdcFlag(SystemDefine.TCDC_FLAG_DC);
        recPlan.setCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        recPlan.setPlanDay(rec.getPlanDay());
        recPlan.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        recPlan.setSupplierCode(rec.getSupplierCode());
        recPlan.setReceiveTicketNo(rec.getReceiveTicketNo());
        recPlan.setReceiveLineNo(rec.getReceiveLineNo());
        recPlan.setItemCode(rec.getItemCode());

        // ロットNo.が指定されている場合
        if (!StringUtil.isBlank(rec.getLotNo()))
        {
            recPlan.setPlanLotNo(rec.getLotNo());
        }
        else
        {
            recPlan.setPlanLotNo("");
        }
        recPlan.setPlanQty(rec.getPlanQty());
        recPlan.setResultQty(0);
        recPlan.setShortageQty(0);
        recPlan.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
        recPlan.setWorkDay("");
        recPlan.setRegistKind(ReceivingPlan.REGIST_KIND_DATALOADER);
        recPlan.setRegistPname(this.getClass().getSimpleName());
        recPlan.setLastUpdatePname(this.getClass().getSimpleName());

        // 登録
        _recHander.create(recPlan);
    }

    /**
     * 入荷作業情報の登録を行います。
     * 
     * @param rec 取込情報
     * @param planUkey 予定一意キー
     * @param jobNo 作業No.
     * @return 生成した登録データ
     * @throws CommonException
     */
    protected void createReceivingWorkInfo(Receiving rec, String planUkey, String jobNo)
            throws CommonException
    {
        // 入荷作業情報エンティティ
        ReceivingWorkInfo recWorkInfo = new ReceivingWorkInfo();

        // 値の設定
        recWorkInfo.setJobNo(jobNo);
        recWorkInfo.setSettingUnitKey("");
        recWorkInfo.setCollectJobNo("");
        recWorkInfo.setJobType(SystemDefine.JOB_TYPE_RECEIVING);
        recWorkInfo.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        recWorkInfo.setTcdcFlag(SystemDefine.TCDC_FLAG_DC);
        recWorkInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);
        recWorkInfo.setPlanUkey(planUkey);
        recWorkInfo.setPlanDay(rec.getPlanDay());
        recWorkInfo.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        recWorkInfo.setSupplierCode(rec.getSupplierCode());
        recWorkInfo.setReceiveTicketNo(rec.getReceiveTicketNo());
        recWorkInfo.setReceiveLineNo(rec.getReceiveLineNo());
        recWorkInfo.setItemCode(rec.getItemCode());

        // ロットNo.が指定されている場合
        if (!StringUtil.isBlank(rec.getLotNo()))
        {
            recWorkInfo.setPlanLotNo(rec.getLotNo());
        }
        else
        {
            recWorkInfo.setPlanLotNo("");
        }
        recWorkInfo.setPlanQty(rec.getPlanQty());
        recWorkInfo.setResultQty(0);
        recWorkInfo.setShortageQty(0);
        recWorkInfo.setResultLotNo("");
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
     * 商品マスタの登録、及び更新を行います。
     * 
     * @param rec 取込情報
     * @throws CommonException
     */
    protected void createItem(Receiving rec)
            throws CommonException
    {
        // 商品コードが入力されていない場合は処理しない
        if (StringUtil.isBlank(rec.getItemCode()))
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
        item.setItemCode(rec.getItemCode());

        // 商品名称が指定されている場合
        if (!StringUtil.isBlank(rec.getItemName()))
        {
            item.setItemName(rec.getItemName());
        }

        // JANコードが指定されている場合
        if (!StringUtil.isBlank(rec.getJan()))
        {
            item.setJan(rec.getJan());
        }

        // ITFが指定されている場合
        if (!StringUtil.isBlank(rec.getItf()))
        {
            item.setItf(rec.getItf());
        }
        item.setBundleItf("");

        // ケース入り数が0以上の場合
        if (rec.getEnteringQty() > 0)
        {
            item.setEnteringQty(rec.getEnteringQty());
        }
        item.setBundleEnteringQty(0);
        item.setLowerQty(0);
        item.setUpperQty(0);

        // 登録
        WmsUserInfo ui = new WmsUserInfo();
        itemctrl.autoCreate(item, ui);
    }

    /**
     * 仕入先マスタの登録、及び更新を行います。
     * 
     * @param rec 取込情報
     * @throws CommonException
     */
    protected void createSupplier(Receiving rec)
            throws CommonException
    {
        // 仕入先コードが入力されていない場合は処理しない
        if (StringUtil.isBlank(rec.getSupplierCode()))
        {
            return;
        }

        // 仕入先情報コントローラ
        SupplierController suppCtrl = new SupplierController(getConnection(), this.getClass());
        Supplier supplier = new Supplier();

        // 値の設定
        supplier.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        supplier.setSupplierCode(rec.getSupplierCode());

        // 仕入先名称が入力されている場合
        if (!StringUtil.isBlank(rec.getSupplierName()))
        {
            supplier.setSupplierName(rec.getSupplierName());
        }

        // 登録
        WmsUserInfo ui = new WmsUserInfo();
        suppCtrl.autoCreate(supplier, ui);
    }

    /**
     * 入荷予定情報の削除を行います。
     * 
     * @param rec 取込情報
     * @throws CommonException
     */
    protected void deleteReceivingPlan(Receiving rec)
            throws CommonException
    {
        // 条件の設定
        _recAKey.clear();
        _recAKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);

        // 仕入先コードが指定されている場合
        if (!StringUtil.isBlank(rec.getSupplierCode()))
        {
            _recAKey.setSupplierCode(rec.getSupplierCode());
        }

        // 入荷予定日が指定されている場合
        if (!StringUtil.isBlank(rec.getPlanDay()))
        {
            _recAKey.setPlanDay(rec.getPlanDay());
        }

        // 入荷伝票No.が指定されている場合
        if (!StringUtil.isBlank(rec.getReceiveTicketNo()))
        {
            _recAKey.setReceiveTicketNo(rec.getReceiveTicketNo());
        }
        _recAKey.setReceiveLineNo(rec.getReceiveLineNo());
        _recAKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        // 値の設定
        _recAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        _recAKey.updateTcdcFlag(SystemDefine.TCDC_FLAG_DC);
        _recAKey.updateCancelFlag(SystemDefine.CANCEL_FLAG_HOST_CANCEL);
        _recAKey.updateLastUpdatePname(this.getClass().getSimpleName());

        // 更新
        _recHander.modify(_recAKey);
    }

    /**
     * 入荷作業情報の削除を行います。
     * 
     * @param rec 取込情報
     * @throws CommonException
     */
    protected void deleteReceivingWorkInfo(Receiving rec)
            throws CommonException
    {
        // 条件の設定
        _recWAKey.clear();
        _recWAKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);

        // 仕入先コードが指定されている場合
        if (!StringUtil.isBlank(rec.getSupplierCode()))
        {
            _recWAKey.setSupplierCode(rec.getSupplierCode());
        }

        // 入荷予定日が指定されている場合
        if (!StringUtil.isBlank(rec.getPlanDay()))
        {
            _recWAKey.setPlanDay(rec.getPlanDay());
        }

        // 入荷伝票No.が指定されている場合
        if (!StringUtil.isBlank(rec.getReceiveTicketNo()))
        {
            _recWAKey.setReceiveTicketNo(rec.getReceiveTicketNo());
        }
        _recWAKey.setReceiveLineNo(rec.getReceiveLineNo());
        _recWAKey.setTcdcFlag(SystemDefine.TCDC_FLAG_DC);
        _recWAKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        // 値の設定
        _recWAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        _recWAKey.updateLastUpdatePname(this.getClass().getSimpleName());

        // 更新
        _recWHandler.modify(_recWAKey);
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#getEntity()
     */
    @Override
    protected AbstractEntity getEntity()
    {
        return new Receiving();
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
        _recHander.getStatics();
        _recWHandler.getStatics();
        _itemHandler.getStatics();
        _suppHandler.getStatics();
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Returns a revision of this class.
     * @return String of a revision.
     */
    public static String getVersion()
    {
        return "$Id: ReceivingDataLoader.java 7636 2010-03-17 04:11:45Z okayama $";
    }
}

// $Id: StorageDataLoader.java 7660 2010-03-17 10:45:36Z kishimoto $
package jp.co.daifuku.wms.storage.schedule;

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
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.FixedLocateInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.fileentity.Storage;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;

/**
 * 入庫予定データの取り込み処理を行います。<br>
 *
 * @version $Revision: 7660 $, $Date: 2010-03-17 19:45:36 +0900 (水, 17 3 2010) $
 * @author  H.Okayama
 * @author  Last commit: $Author: kishimoto $
 */
public class StorageDataLoader
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
    /** 入庫作業情報ハンドラ */
    private StoragePlanHandler _stHandler = null;

    /** 入庫作業情報検索キー */
    private StoragePlanSearchKey _stKey = null;

    /** 入庫作業情報更新キー */
    private StoragePlanAlterKey _stAKey = null;

    /** 入出庫作業情報ハンドラ */
    private WorkInfoHandler _wInfoHandler = null;

    /** 入出庫作業情報検索キー */
    private WorkInfoSearchKey _wInfoKey = null;

    /** 入出庫作業情報更新キー */
    private WorkInfoAlterKey _wInfoAKey = null;

    /** 商品マスタハンドラ */
    private ItemHandler _itemHandler = null;

    /** 商品マスタ検索キー */
    private ItemSearchKey _itemKey = null;

    /** エリアマスタハンドラ */
    private AreaHandler _areaHandler = null;

    /** エリアマスタ検索キー */
    private AreaSearchKey _areaKey = null;

    /** 商品固定棚情報ハンドラ */
    private FixedLocateInfoHandler _fixedHandler = null;

    /** 商品固定棚情報検索キー */
    private FixedLocateInfoSearchKey _fixedKey = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 取り込み区分出庫の受信データの取り込みを行います。<BR>
     * 自動取込みから使用します。
     */
    public StorageDataLoader()
    {
        super(ExchangeEnvironment.DATA_TYPE_STORAGE);
    }

    /**
     * 取り込み区分入荷の受信データの取り込みを行います。<BR>
     * WEB画面から使用します。
     * 
     * @param userinfo ユーザ情報
     * @param locale ロケール
     */
    public StorageDataLoader(DfkUserInfo userinfo, Locale locale)
    {
        super(ExchangeEnvironment.DATA_TYPE_STORAGE, userinfo, locale);
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
        // 入庫予定情報
        _stHandler = new StoragePlanHandler(getConnection());
        _stKey = new StoragePlanSearchKey();
        _stAKey = new StoragePlanAlterKey();

        // 入出庫作業情報
        _wInfoHandler = new WorkInfoHandler(getConnection());
        _wInfoKey = new WorkInfoSearchKey();
        _wInfoAKey = new WorkInfoAlterKey();

        // 商品マスタ
        _itemHandler = new ItemHandler(getConnection());
        _itemKey = new ItemSearchKey();

        // エリアマスタ
        _areaHandler = new AreaHandler(getConnection());
        _areaKey = new AreaSearchKey();

        // 商品固定棚情報
        _fixedHandler = new FixedLocateInfoHandler(getConnection());
        _fixedKey = new FixedLocateInfoSearchKey();
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
        Storage st = (Storage)ent;

        // 日付のフォーマットチェック
        RESULT chk = isDay(Storage.PLAN_DAY, st.getPlanDay());
        if (!chk.equals(RESULT.LOAD))
        {
            return chk;
        }
        // 禁止文字を含むかチェック
        RESULT chk2 = hasNGParameterText(st);
        if (!chk2.equals(RESULT.LOAD))
        {
            return chk2;
        }

        // 取込情報のチェック、及び補完
        checkParam(st, sysCtrl);

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(st.getCancelFlag()))
        {
            // マスタパッケージが導入されている場合
            if (sysCtrl.hasMasterPack())
            {
                // 商品コード存在チェック
                _itemKey.clear();
                _itemKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _itemKey.setItemCode(st.getItemCode());
                if (_itemHandler.count(_itemKey) == 0)
                {
                    // MSG-W0023=マスタ未登録
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                            st.getStoreMetaData().getFieldMetaData(Storage.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }

                // 商品コードチェック(異常棚商品コード、簡易直行商品コード)
                if (WmsParam.IRREGULAR_ITEMCODE.equals(st.getItemCode())
                        || WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE.equals(st.getItemCode()))
                {
                    // MSG-W0029=データの値が不正
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                            st.getStoreMetaData().getFieldMetaData(Storage.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // エリアが指定されていた場合
            if (!StringUtil.isBlank(st.getPlanAreaNo()))
            {
                // エリア存在チェック
                _areaKey.clear();
                _areaKey.setAreaNo(st.getPlanAreaNo());
                if (_areaHandler.count(_areaKey) == 0)
                {
                    // MSG-W0023=マスタ未登録
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                            st.getStoreMetaData().getFieldMetaData(Storage.PLAN_AREA_NO.getName()).getDescription());
                    return RESULT.SKIP;
                }

                // 棚が指定されていた場合、形式チェック
                if (!StringUtil.isBlank(st.getPlanLocationNo()))
                {
                    try
                    {
                        // 棚フォーマットを行う
                        AreaController areaCtrl = new AreaController(getConnection(), this.getClass());
                        areaCtrl.checkLocateFormat(st.getPlanAreaNo(), st.getPlanLocationNo());
                    }
                    // 棚変換に失敗した場合
                    catch (OperatorException e)
                    {
                        // MSG-W0029=データの値が不正
                        insertLoadErrorInfo(
                                SystemDefine.ERROR_LEVEL_WARNING,
                                "MSG-W0029",
                                st.getStoreMetaData().getFieldMetaData(Storage.PLAN_LOCATION_NO.getName()).getDescription());
                        return RESULT.SKIP;
                    }
                    // 棚変換に失敗した場合
                    catch (ScheduleException e)
                    {
                        // MSG-W0029=データの値が不正
                        insertLoadErrorInfo(
                                SystemDefine.ERROR_LEVEL_WARNING,
                                "MSG-W0029",
                                st.getStoreMetaData().getFieldMetaData(Storage.PLAN_LOCATION_NO.getName()).getDescription());
                        return RESULT.SKIP;
                    }
                }
            }
            // 棚が指定されているが、エリアが指定されていない場合
            else if (!StringUtil.isBlank(st.getPlanLocationNo()))
            {
                // MSG-W0021=必須項目空白
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0021",
                        st.getStoreMetaData().getFieldMetaData(Storage.PLAN_AREA_NO.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 数量チェック
            if (st.getPlanQty() <= 0)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                        st.getStoreMetaData().getFieldMetaData(Storage.PLAN_QTY.getName()).getDescription());
                return RESULT.SKIP;
            }
            else if (st.getPlanQty() > WmsParam.MAX_TOTAL_QTY)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                        st.getStoreMetaData().getFieldMetaData(Storage.PLAN_QTY.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 同一キー(予定日 + 入庫伝票No. + 入庫伝票行No. + 作業枝番)チェック
            _stKey.clear();
            _stKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _stKey.setPlanDay(st.getPlanDay());
            _stKey.setReceiveTicketNo(st.getReceiveTicketNo());
            _stKey.setReceiveLineNo(st.getReceiveLineNo());
            _stKey.setBranchNo(st.getBranchNo());
            _stKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (_stHandler.count(_stKey) > 0)
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025", "");
                return RESULT.SKIP;
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        // 取消区分が取消の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(st.getCancelFlag()))
        {
            // 入庫予定情報チェック
            RESULT res = checkCancelPlan(st);
            if (!RESULT.LOAD.equals(res))
            {
                return res;
            }

            // 在庫パッケージが導入されていない場合
            if (!sysCtrl.hasStockPack())
            {
                // 入出庫作業情報チェック
                res = checkCancelWorkInfo(st);
                if (!RESULT.LOAD.equals(res))
                {
                    return res;
                }
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", st.getStoreMetaData().getFieldMetaData(
                    Storage.CANCEL_FLAG.getName()).getDescription());
            return RESULT.SKIP;
        }
    }

    /**
     * 取込情報のチェックを行い、必要があれば値の補完を行います。<BR>
     * 
     * @param st 取込情報
     * @param sysCtrl システム定義情報コントローラ
     * @throws CommonException
     */
    protected void checkParam(Storage st, WarenaviSystemController sysCtrl)
            throws CommonException
    {
        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(st.getCancelFlag()))
        {
            // マスタパッケージが導入されている場合
            if (sysCtrl.hasMasterPack())
            {
                // 商品コードが指定されている場合
                if (!StringUtil.isBlank(st.getItemCode()))
                {
                    // 検索
                    _itemKey.clear();
                    _itemKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                    _itemKey.setItemCode(st.getItemCode());
                    Item[] ents = (Item[])_itemHandler.find(_itemKey);
                    if (!ArrayUtil.isEmpty(ents))
                    {
                        // 商品名称が省略されている場合
                        if (!StringUtil.isBlank(ents[0].getItemName()))
                        {
                            st.setItemName(st.getItemName());
                        }

                        // JANコードが省略されている場合
                        if (!StringUtil.isBlank(ents[0].getJan()))
                        {
                            st.setJan(ents[0].getJan());
                        }

                        // ITFコードが省略されている場合
                        if (!StringUtil.isBlank(ents[0].getItf()))
                        {
                            st.setItf(ents[0].getItf());
                        }

                        // ケース入り数
                        st.setEnteringQty(ents[0].getEnteringQty());
                    }
                }
            }

            // 在庫パッケージが導入されている場合
            if (sysCtrl.hasStockPack())
            {
                // 入庫予定エリア、または入庫予定棚が省略されていた場合
                if (StringUtil.isBlank(st.getPlanAreaNo()) || StringUtil.isBlank(st.getPlanLocationNo()))
                {
                    // 検索
                    _fixedKey.clear();
                    _fixedKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                    _fixedKey.setItemCode(st.getItemCode());
                    _fixedKey.setAreaNoCollect();
                    _fixedKey.setLocationNoCollect();
                    _fixedKey.setLastUpdateDateOrder(false);

                    // エリアが指定されていた場合
                    if (!StringUtil.isBlank(st.getPlanAreaNo()))
                    {
                        // 固定棚管理エリアであること
                        _areaKey.clear();
                        _areaKey.setAreaNo(st.getPlanAreaNo());
                        _areaKey.setLocationType(SystemDefine.LOCATION_TYPE_FIXED);
                        _areaKey.setAreaNoCollect();

                        // 結合
                        _fixedKey.setKey(FixedLocateInfo.AREA_NO, _areaKey);
                    }
                    FixedLocateInfo[] ents = (FixedLocateInfo[])_fixedHandler.find(_fixedKey);
                    if (!ArrayUtil.isEmpty(ents))
                    {
                        // 入庫予定エリアが省略されている場合
                        if (!StringUtil.isBlank(ents[0].getAreaNo()))
                        {
                            st.setPlanAreaNo(ents[0].getAreaNo());
                        }

                        // 入庫予定棚が省略されている場合
                        if (!StringUtil.isBlank(ents[0].getLocationNo()))
                        {
                            st.setPlanLocationNo(ents[0].getLocationNo());
                        }
                    }
                }
            }
        }
    }

    /**
     * 入庫予定情報の存在、状態チェックを行う。<BR>
     * (取消時)
     * 
     * @param st 取込情報
     * @return 取込可否フラグ
     * @throws CommonException
     */
    protected RESULT checkCancelPlan(Storage st)
            throws CommonException
    {
        // 未登録データの場合はスキップとする
        // 未作業の同一データを検索し、なければスキップとする
        // 同一条件 = 予定日 + 入庫伝票No. + 入庫伝票行No. + 作業枝番
        _stKey.clear();
        _stKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        _stKey.setPlanDay(st.getPlanDay());
        _stKey.setReceiveTicketNo(st.getReceiveTicketNo());
        _stKey.setReceiveLineNo(st.getReceiveLineNo());
        _stKey.setBranchNo(st.getBranchNo());
        _stKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        try
        {
            StoragePlan[] plans = (StoragePlan[])_stHandler.findForUpdate(_stKey);
            if (plans == null || plans.length == 0)
            {
                // MSG-W0027=取消該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0027", "");
                return RESULT.SKIP;
            }
            else if (!StoragePlan.STATUS_FLAG_UNSTART.equals(plans[0].getStatusFlag()))
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
     * 入出庫作業情報の存在、状態チェックを行う。<BR>
     * (取消時)
     * 
     * @param ret 取込情報
     * @return 取込可否フラグ
     * @throws CommonException
     */
    protected RESULT checkCancelWorkInfo(Storage st)
            throws CommonException
    {
        // 未登録データの場合はスキップとする
        // 未作業の同一データを検索し、なければスキップとする
        // 同一条件 = 予定日 + 入庫伝票No. + 入庫伝票No. + 作業枝番 + 作業区分:入庫
        _wInfoKey.clear();
        _wInfoKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        _wInfoKey.setPlanDay(st.getPlanDay());
        _wInfoKey.setReceiveTicketNo(st.getReceiveTicketNo());
        _wInfoKey.setReceiveLineNo(st.getReceiveLineNo());
        _wInfoKey.setReceiveBranchNo(st.getBranchNo());
        _wInfoKey.setJobType(SystemDefine.JOB_TYPE_STORAGE);
        _wInfoKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        try
        {
            WorkInfo[] workInfos = (WorkInfo[])_wInfoHandler.findForUpdate(_wInfoKey);
            if (workInfos == null || workInfos.length == 0)
            {
                // MSG-W0027=取消該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0027", "");
                return RESULT.SKIP;
            }
            else if (!WorkInfo.STATUS_FLAG_UNSTART.equals(workInfos[0].getStatusFlag()))
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
        Storage st = (Storage)ent;

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(st.getCancelFlag()))
        {
            try
            {
                // シーケンスハンドラ
                WMSSequenceHandler _seq = new WMSSequenceHandler(getConnection());
                String planUKey = _seq.nextStoragePlanUkey();

                // 入庫予定情報の登録
                createReceivingPlan(st, planUKey);

                // 入出庫作業情報の登録
                String jobNo = _seq.nextWorkInfoJobNo();
                createWorkInfo(st, planUKey, jobNo);

                // マスタパッケージが導入されていない場合
                if (!sysCtrl.hasMasterPack())
                {
                    // 商品マスタの登録
                    createItem(st);
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
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(st.getCancelFlag()))
        {
            try
            {
                // 入庫予定情報の論理削除
                deleteStoragePlan(st);

                // 入出庫作業情報の論理削除
                deleteWorkInfo(st);

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
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", st.getStoreMetaData().getFieldMetaData(
                    Storage.CANCEL_FLAG.getName()).getDescription());
            return RESULT.SKIP;
        }
    }

    /**
     * 入荷予定情報の登録を行います。
     * 
     * @param rec 取込情報
     * @param planUkey 予定一意キー
     * @throws CommonException
     */
    protected void createReceivingPlan(Storage st, String planUkey)
            throws CommonException
    {
        // 入庫予定情報エンティティ
        StoragePlan stPlan = new StoragePlan();

        // 値の設定
        stPlan.setPlanUkey(planUkey);
        stPlan.setLoadUnitKey(getSourceFileNameOutOfExtention());
        stPlan.setFileLineNo(getDataCnt());
        stPlan.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        stPlan.setCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        stPlan.setPlanDay(st.getPlanDay());
        stPlan.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        stPlan.setSupplierCode("");
        stPlan.setReceiveTicketNo(st.getReceiveTicketNo());
        stPlan.setReceiveLineNo(st.getReceiveLineNo());
        stPlan.setBranchNo(st.getBranchNo());

        // エリアが指定されている場合
        if (!StringUtil.isBlank(st.getPlanAreaNo()))
        {
            stPlan.setPlanAreaNo(st.getPlanAreaNo());
        }
        else
        {
            stPlan.setPlanAreaNo("");
        }

        // 棚が指定されている場合
        if (!StringUtil.isBlank(st.getPlanLocationNo()))
        {
            stPlan.setPlanLocationNo(st.getPlanLocationNo());
        }
        else
        {
            stPlan.setPlanLocationNo("");
        }
        stPlan.setItemCode(st.getItemCode());

        // ロットNo.が指定されている場合
        if (!StringUtil.isBlank(st.getLotNo()))
        {
            stPlan.setPlanLotNo(st.getLotNo());
        }
        else
        {
            stPlan.setPlanLotNo("");
        }
        stPlan.setPlanQty(st.getPlanQty());
        stPlan.setResultQty(0);
        stPlan.setShortageQty(0);
        stPlan.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
        stPlan.setWorkDay("");
        stPlan.setRegistKind(SystemDefine.REGIST_KIND_DATALOADER);
        stPlan.setRegistPname(this.getClass().getSimpleName());
        stPlan.setLastUpdatePname(this.getClass().getSimpleName());

        // 登録
        _stHandler.create(stPlan);
    }

    /**
     * 入出庫作業情報の登録を行います。
     * 
     * @param st 取込情報
     * @param planUkey 予定一意キー
     * @param jobNo 作業No.
     * @throws CommonException
     */
    protected void createWorkInfo(Storage st, String planUkey, String jobNo)
            throws CommonException
    {
        // 入出庫作業情報エンティティ
        WorkInfo wInfo = new WorkInfo();

        // 値の設定
        wInfo.setJobNo(jobNo);
        wInfo.setSettingUnitKey("");
        wInfo.setCollectJobNo("");
        wInfo.setJobType(SystemDefine.JOB_TYPE_STORAGE);
        wInfo.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        wInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);
        wInfo.setPlanUkey(planUkey);
        wInfo.setStockId("");
        wInfo.setSystemConnKey("");
        wInfo.setPlanDay(st.getPlanDay());
        wInfo.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        wInfo.setSupplierCode("");
        wInfo.setReceiveTicketNo(st.getReceiveTicketNo());
        wInfo.setReceiveLineNo(st.getReceiveLineNo());
        wInfo.setReceiveBranchNo(st.getBranchNo());
        wInfo.setCustomerCode("");
        wInfo.setShipTicketNo("");
        wInfo.setShipLineNo(0);
        wInfo.setShipBranchNo(0);
        wInfo.setBatchNo("");
        wInfo.setOrderNo("");

        // エリアが指定されている場合
        if (!StringUtil.isBlank(st.getPlanAreaNo()))
        {
            wInfo.setPlanAreaNo(st.getPlanAreaNo());
        }
        else
        {
            wInfo.setPlanAreaNo("");
        }

        // 棚が指定されている場合
        if (!StringUtil.isBlank(st.getPlanLocationNo()))
        {
            wInfo.setPlanLocationNo(st.getPlanLocationNo());
        }
        else
        {
            wInfo.setPlanLocationNo("");
        }
        wInfo.setItemCode(st.getItemCode());

        // ロットNo.が指定されている場合
        if (!StringUtil.isBlank(st.getLotNo()))
        {
            wInfo.setPlanLotNo(st.getLotNo());
        }
        else
        {
            wInfo.setPlanLotNo("");
        }
        wInfo.setPlanQty(st.getPlanQty());
        wInfo.setResultQty(0);
        wInfo.setShortageQty(0);
        wInfo.setResultAreaNo("");
        wInfo.setResultLocationNo("");
        wInfo.setResultLotNo("");
        wInfo.setWorkDay("");
        wInfo.setUserId("");
        wInfo.setTerminalNo("");
        wInfo.setWorkSecond(0);
        wInfo.setRegistPname(this.getClass().getSimpleName());
        wInfo.setLastUpdatePname(this.getClass().getSimpleName());

        // 登録
        _wInfoHandler.create(wInfo);
    }

    /**
     * 商品マスタの登録、及び更新を行います。
     * 
     * @param rec 取込情報
     * @throws CommonException
     */
    protected void createItem(Storage st)
            throws CommonException
    {
        // 商品コードが入力されていない場合は処理しない
        if (StringUtil.isBlank(st.getItemCode()))
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
        item.setItemCode(st.getItemCode());

        // 商品名称が指定されている場合
        if (!StringUtil.isBlank(st.getItemName()))
        {
            item.setItemName(st.getItemName());
        }

        // JANコードが指定されている場合
        if (!StringUtil.isBlank(st.getJan()))
        {
            item.setJan(st.getJan());
        }

        // ITFが指定されている場合
        if (!StringUtil.isBlank(st.getItf()))
        {
            item.setItf(st.getItf());
        }
        item.setBundleItf("");

        // ケース入数が0以上の場合
        if (st.getEnteringQty() > 0)
        {
            item.setEnteringQty(st.getEnteringQty());
        }
        item.setBundleEnteringQty(0);
        item.setLowerQty(0);
        item.setUpperQty(0);

        // 登録
        WmsUserInfo ui = new WmsUserInfo();
        itemctrl.autoCreate(item, ui);
    }

    /**
     * 入庫予定情報の削除を行います。
     * 
     * @param rec 取込情報
     * @throws CommonException
     */
    protected void deleteStoragePlan(Storage st)
            throws CommonException
    {
        // 条件の設定
        _stAKey.clear();
        _stAKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);

        // 入庫予定日が指定されている場合
        if (!StringUtil.isBlank(st.getPlanDay()))
        {
            _stAKey.setPlanDay(st.getPlanDay());
        }

        // 入庫伝票No.が指定されている場合
        if (!StringUtil.isBlank(st.getReceiveTicketNo()))
        {
            _stAKey.setReceiveTicketNo(st.getReceiveTicketNo());
        }
        _stAKey.setReceiveLineNo(st.getReceiveLineNo());
        _stAKey.setBranchNo(st.getBranchNo());
        _stAKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        // 値の設定
        _stAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        _stAKey.updateCancelFlag(SystemDefine.CANCEL_FLAG_HOST_CANCEL);
        _stAKey.updateLastUpdatePname(this.getClass().getSimpleName());

        // 更新
        _stHandler.modify(_stAKey);
    }

    /**
     * 入出庫作業情報の削除を行います。
     * 
     * @param rec 取込情報
     * @throws CommonException
     */
    protected void deleteWorkInfo(Storage st)
            throws CommonException
    {
        // 条件の設定
        _wInfoAKey.clear();
        _wInfoAKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);

        // 入庫予定日が指定されている場合
        if (!StringUtil.isBlank(st.getPlanDay()))
        {
            _wInfoAKey.setPlanDay(st.getPlanDay());
        }

        // 入庫伝票No.が指定されている場合
        if (!StringUtil.isBlank(st.getReceiveTicketNo()))
        {
            _wInfoAKey.setReceiveTicketNo(st.getReceiveTicketNo());
        }
        _wInfoAKey.setReceiveLineNo(st.getReceiveLineNo());
        _wInfoAKey.setReceiveBranchNo(st.getBranchNo());
        _wInfoAKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        // 値の設定
        _wInfoAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        _wInfoAKey.updateLastUpdatePname(this.getClass().getSimpleName());

        // 更新
        _wInfoHandler.modify(_wInfoAKey);
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#getEntity()
     */
    @Override
    protected AbstractEntity getEntity()
    {
        return new Storage();
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
        _stHandler.getStatics();
        _wInfoHandler.getStatics();
        _itemHandler.getStatics();
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
        return "$Id: StorageDataLoader.java 7660 2010-03-17 10:45:36Z kishimoto $";
    }
}

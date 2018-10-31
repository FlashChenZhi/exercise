// $Id: RetrievalDataLoader.java 7909 2010-05-07 05:43:54Z kumano $
package jp.co.daifuku.wms.retrieval.schedule;

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
import jp.co.daifuku.wms.base.controller.CustomerController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.fileentity.Retrieval;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;

/**
 * 出庫予定データの取り込み処理を行います。<br>
 *
 * @version $Revision: 7909 $, $Date: 2010-05-07 14:43:54 +0900 (金, 07 5 2010) $
 * @author  H.Okayama
 * @author  Last commit: $Author: kumano $
 */
public class RetrievalDataLoader
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
    /** 出庫予定情報ハンドラ */
    private RetrievalPlanHandler _retHandler = null;

    /** 出庫予定情報検索キー */
    private RetrievalPlanSearchKey _retKey = null;

    /** 出庫予定情報更新キー */
    private RetrievalPlanAlterKey _retAKey = null;

    /** 入出庫作業情報ハンドラ */
    private WorkInfoHandler _wInfoHandler = null;

    /** 入出庫作業情報検索キー */
    private WorkInfoSearchKey _wInfoKey = null;

    /** 入出庫作業情報更新キー */
    private WorkInfoAlterKey _wInfoAKey = null;

    /** 出荷先マスタハンドラ */
    private CustomerHandler _custHandler = null;

    /** 出荷先マスタ検索キー */
    private CustomerSearchKey _custKey = null;

    /** 商品マスタハンドラ */
    private ItemHandler _itemHandler = null;

    /** 商品マスタ検索キー */
    private ItemSearchKey _itemKey = null;

    /** エリアマスタハンドラ */
    private AreaHandler _areaHandler = null;

    /** エリアマスタ検索キー */
    private AreaSearchKey _areaKey = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 取り込み区分出庫の受信データの取り込みを行います。<BR>
     * 自動取込みから使用します。
     */
    public RetrievalDataLoader()
    {
        super(ExchangeEnvironment.DATA_TYPE_RETRIEVAL);
    }

    /**
     * 取り込み区分入荷の受信データの取り込みを行います。<BR>
     * WEB画面から使用します。
     * 
     * @param userinfo ユーザ情報
     * @param locale ロケール
     */
    public RetrievalDataLoader(DfkUserInfo userinfo, Locale locale)
    {
        super(ExchangeEnvironment.DATA_TYPE_RETRIEVAL, userinfo, locale);
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
        // 出庫作業情報
        _retHandler = new RetrievalPlanHandler(getConnection());
        _retKey = new RetrievalPlanSearchKey();
        _retAKey = new RetrievalPlanAlterKey();

        // 入出庫作業情報
        _wInfoHandler = new WorkInfoHandler(getConnection());
        _wInfoKey = new WorkInfoSearchKey();
        _wInfoAKey = new WorkInfoAlterKey();

        // 出荷先マスタ
        _custHandler = new CustomerHandler(getConnection());
        _custKey = new CustomerSearchKey();

        // 商品マスタ
        _itemHandler = new ItemHandler(getConnection());
        _itemKey = new ItemSearchKey();

        // エリアマスタ
        _areaHandler = new AreaHandler(getConnection());
        _areaKey = new AreaSearchKey();
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
        Retrieval ret = (Retrieval)ent;

        // 日付のフォーマットチェック
        RESULT chk = isDay(Retrieval.PLAN_DAY, ret.getPlanDay());
        if (!chk.equals(RESULT.LOAD))
        {
            return chk;
        }
        // 禁止文字を含むかチェック
        RESULT chk2 = hasNGParameterText(ret);
        if (!chk2.equals(RESULT.LOAD))
        {
            return chk2;
        }

        // 取込情報のチェック、及び補完
        checkParam(ret, sysCtrl);

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(ret.getCancelFlag()))
        {
            // マスタパッケージが導入されている場合
            if (sysCtrl.hasMasterPack())
            {
                // 出荷先コード存在チェック
                _custKey.clear();
                _custKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _custKey.setCustomerCode(ret.getCustomerCode());
                if (_custHandler.count(_custKey) == 0)
                {
                    // MSG-W0023=マスタ未登録
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                            ret.getStoreMetaData().getFieldMetaData(Retrieval.CUSTOMER_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }

                // 商品コード存在チェック
                _itemKey.clear();
                _itemKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _itemKey.setItemCode(ret.getItemCode());
                if (_itemHandler.count(_itemKey) == 0)
                {
                    // MSG-W0023=マスタ未登録
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                            ret.getStoreMetaData().getFieldMetaData(Retrieval.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }

                // 商品コードチェック(異常棚商品コード、簡易直行商品コード)
                if (WmsParam.IRREGULAR_ITEMCODE.equals(ret.getItemCode())
                        || WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE.equals(ret.getItemCode()))
                {
                    // MSG-W0029=データの値が不正
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                            ret.getStoreMetaData().getFieldMetaData(Retrieval.ITEM_CODE.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // 在庫パッケージが導入されている場合
            if (sysCtrl.hasStockPack())
            {
                // エリアが指定されている場合
                if (!StringUtil.isBlank(ret.getPlanAreaNo()))
                {
                    // MSG-W0022=指定禁止項目に値あり
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0022",
                            ret.getStoreMetaData().getFieldMetaData(Retrieval.PLAN_AREA_NO.getName()).getDescription());
                    return RESULT.SKIP;
                }
                // 棚が指定されている場合
                else if (!StringUtil.isBlank(ret.getPlanLocationNo()))
                {
                    // MSG-W0022=指定禁止項目に値あり
                    insertLoadErrorInfo(
                            SystemDefine.ERROR_LEVEL_WARNING,
                            "MSG-W0022",
                            ret.getStoreMetaData().getFieldMetaData(Retrieval.PLAN_LOCATION_NO.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }
            else
            {
                // 必須(エリア)が指定されていない場合
                if (StringUtil.isBlank(ret.getPlanAreaNo()))
                {
                    // MSG-W0021=必須項目空白
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0021",
                            ret.getStoreMetaData().getFieldMetaData(Retrieval.PLAN_AREA_NO.getName()).getDescription());
                    return RESULT.SKIP;
                }
                // 必須(棚)が指定されていない場合
                else if (StringUtil.isBlank(ret.getPlanLocationNo()))
                {
                    // MSG-W0021=必須項目空白
                    insertLoadErrorInfo(
                            SystemDefine.ERROR_LEVEL_WARNING,
                            "MSG-W0021",
                            ret.getStoreMetaData().getFieldMetaData(Retrieval.PLAN_LOCATION_NO.getName()).getDescription());
                    return RESULT.SKIP;
                }
            }

            // エリアが指定されていた場合
            if (!StringUtil.isBlank(ret.getPlanAreaNo()))
            {
                // エリア存在チェック
                _areaKey.clear();
                _areaKey.setAreaNo(ret.getPlanAreaNo());
                if (_areaHandler.count(_areaKey) == 0)
                {
                    // MSG-W0023=マスタ未登録
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                            ret.getStoreMetaData().getFieldMetaData(Retrieval.PLAN_AREA_NO.getName()).getDescription());
                    return RESULT.SKIP;
                }

                // 棚が指定されていた場合、形式チェック
                if (!StringUtil.isBlank(ret.getPlanLocationNo()))
                {
                    try
                    {
                        // 棚フォーマットを行う
                        AreaController areaCtrl = new AreaController(getConnection(), this.getClass());
                        areaCtrl.checkLocateFormat(ret.getPlanAreaNo(), ret.getPlanLocationNo());
                    }
                    // 棚変換に失敗した場合
                    catch (OperatorException e)
                    {
                        // MSG-W0029=データの値が不正
                        insertLoadErrorInfo(
                                SystemDefine.ERROR_LEVEL_WARNING,
                                "MSG-W0029",
                                ret.getStoreMetaData().getFieldMetaData(Retrieval.PLAN_LOCATION_NO.getName()).getDescription());
                        return RESULT.SKIP;
                    }
                    // 棚変換に失敗した場合
                    catch (ScheduleException e)
                    {
                        // MSG-W0029=データの値が不正
                        insertLoadErrorInfo(
                                SystemDefine.ERROR_LEVEL_WARNING,
                                "MSG-W0029",
                                ret.getStoreMetaData().getFieldMetaData(Retrieval.PLAN_LOCATION_NO.getName()).getDescription());
                        return RESULT.SKIP;
                    }
                }
            }

            // 数量チェック
            if (ret.getPlanQty() <= 0)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                        ret.getStoreMetaData().getFieldMetaData(Retrieval.PLAN_QTY.getName()).getDescription());
                return RESULT.SKIP;
            }
            else if (ret.getPlanQty() > WmsParam.MAX_TOTAL_QTY)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                        ret.getStoreMetaData().getFieldMetaData(Retrieval.PLAN_QTY.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 同一オーダー、複数予定日チェック
            _retKey.clear();
            _retKey.setOrderNo(ret.getOrderNo());
            _retKey.setPlanDay(ret.getPlanDay(), "!=");
            _retKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (_retHandler.count(_retKey) > 0)
            {
                // MSG-W0078=同一オーダー内に複数予定日
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0078",
                        ret.getStoreMetaData().getFieldMetaData(Retrieval.PLAN_DAY.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 同一オーダー、複数出荷先チェック
            _retKey.clear();
            _retKey.setOrderNo(ret.getOrderNo());
            _retKey.setCustomerCode(ret.getCustomerCode(), "!=");
            _retKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (_retHandler.count(_retKey) > 0)
            {
                // MSG-W0079=同一オーダー内に複数の出荷先
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0079",
                        ret.getStoreMetaData().getFieldMetaData(Retrieval.CUSTOMER_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 同一キー(出荷伝票No. + 出荷伝票行No.+ 作業枝番)チェック
            _retKey.clear();
            _retKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _retKey.setShipTicketNo(ret.getShipTicketNo());
            _retKey.setShipLineNo(ret.getShipLineNo());
            _retKey.setBranchNo(ret.getBranchNo());
            _retKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (_retHandler.count(_retKey) > 0)
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025", "");
                return RESULT.SKIP;
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        // 取消区分が取消の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(ret.getCancelFlag()))
        {
            // 出庫予定情報チェック
            RESULT res = checkCancelPlan(ret, sysCtrl);
            if (!RESULT.LOAD.equals(res))
            {
                return res;
            }

            // 在庫パッケージが導入されていない場合
            if (!sysCtrl.hasStockPack())
            {
                // 入出庫作業情報チェック
                res = checkCancelWorkInfo(ret);
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
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", ret.getStoreMetaData().getFieldMetaData(
                    Retrieval.CANCEL_FLAG.getName()).getDescription());
            return RESULT.SKIP;
        }
    }

    /**
     * 取込情報のチェックを行い、必要があれば値の補完を行います。<BR>
     * 
     * @param ret 取込情報
     * @param sysCtrl システム定義情報コントローラ
     * @throws CommonException
     */
    protected void checkParam(Retrieval ret, WarenaviSystemController sysCtrl)
            throws CommonException
    {
        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(ret.getCancelFlag()))
        {
            // 出荷先コードが指定されている場合
            if (!StringUtil.isBlank(ret.getCustomerCode()))
            {
                // 検索
                _custKey.clear();
                _custKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _custKey.setCustomerCode(ret.getCustomerCode());
                Customer[] ents = (Customer[])_custHandler.find(_custKey);
                if (!ArrayUtil.isEmpty(ents))
                {
                    // 出荷先名称
                    ret.setCustomerName(ents[0].getCustomerName());
                }
            }

            // 商品コードが指定されていた場合
            if (!StringUtil.isBlank(ret.getItemCode()))
            {
                // 検索
                _itemKey.clear();
                _itemKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                _itemKey.setItemCode(ret.getItemCode());
                Item[] ents = (Item[])_itemHandler.find(_itemKey);
                if (!ArrayUtil.isEmpty(ents))
                {
                    // 商品名称が省略されている場合
                    if (!StringUtil.isBlank(ents[0].getItemName()))
                    {
                        ret.setItemName(ents[0].getItemName());
                    }

                    // JANコードが省略されている場合
                    if (!StringUtil.isBlank(ents[0].getJan()))
                    {
                        ret.setJan(ents[0].getJan());
                    }

                    // ITFコードが省略されている場合
                    if (!StringUtil.isBlank(ents[0].getItf()))
                    {
                        ret.setItf(ents[0].getItf());
                    }

                    // ケース入り数
                    ret.setEnteringQty(ents[0].getEnteringQty());
                }
            }
        }
    }

    /**
     * 出庫予定情報の存在、状態チェックを行う。<BR>
     * (取消時)
     * 
     * @param ret 取込情報
     * @param sysCtrl システム定義情報コントローラ
     * @return 取込可否フラグ
     * @throws CommonException
     */
    protected RESULT checkCancelPlan(Retrieval ret, WarenaviSystemController sysCtrl)
            throws CommonException
    {
        // 未登録データの場合はスキップとする
        // 未作業の同一データを検索し、なければスキップとする
        // 同一条件 = 予定日 + 出荷伝票No. + 出荷伝票No. + 作業枝番
        _retKey.clear();
        _retKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        _retKey.setPlanDay(ret.getPlanDay());
        _retKey.setShipTicketNo(ret.getShipTicketNo());
        _retKey.setShipLineNo(ret.getShipLineNo());
        _retKey.setBranchNo(ret.getBranchNo());
        _retKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        try
        {
            RetrievalPlan[] plans = (RetrievalPlan[])_retHandler.findForUpdate(_retKey);
            if (plans == null || plans.length == 0)
            {
                // MSG-W0027=取消該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0027", "");
                return RESULT.SKIP;
            }
            else if (!RetrievalPlan.STATUS_FLAG_UNSTART.equals(plans[0].getStatusFlag()))
            {
                // MSG-W0028=取消データ作業開始済み
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0028", "");
                return RESULT.SKIP;
            }
            else if (sysCtrl.hasStockPack() && RetrievalPlan.SCH_FLAG_SCHEDULE.equals(plans[0].getSchFlag()))
            {
                // MSG-W0082=取消データスケジュール済み
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0082", "");
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
    protected RESULT checkCancelWorkInfo(Retrieval ret)
            throws CommonException
    {
        // 未登録データの場合はスキップとする
        // 未作業の同一データを検索し、なければスキップとする
        // 同一条件 = 出庫予定日 + 出荷伝票No. + 出荷伝票No. + 作業枝番 + 作業区分:出庫
        _wInfoKey.clear();
        _wInfoKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        _wInfoKey.setPlanDay(ret.getPlanDay());
        _wInfoKey.setShipTicketNo(ret.getShipTicketNo());
        _wInfoKey.setShipLineNo(ret.getShipLineNo());
        _wInfoKey.setShipBranchNo(ret.getBranchNo());
        _wInfoKey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL);
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
        // システム定義情報コントローラ
        WarenaviSystemController sysCtrl = new WarenaviSystemController(getConnection(), this.getClass());
        Retrieval ret = (Retrieval)ent;

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(ret.getCancelFlag()))
        {
            try
            {
                // シーケンスハンドラ
                WMSSequenceHandler _seq = new WMSSequenceHandler(getConnection());
                String planUKey = _seq.nextRetrievalPlanUkey();

                // 出庫予定情報の登録
                createRetrievalPlan(ret, planUKey, sysCtrl);

                // 在庫パッケージが導入されていない場合
                if (!sysCtrl.hasStockPack())
                {
                    // 入出庫作業情報の登録
                    String jobNo = _seq.nextWorkInfoJobNo();
                    createWorkInfo(ret, planUKey, jobNo);
                }

                // マスタパッケージが導入されていない場合
                if (!sysCtrl.hasMasterPack())
                {
                    // 商品マスタの登録
                    createItem(ret);
                    // 出荷先マスタの登録
                    createCustomer(ret);
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
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(ret.getCancelFlag()))
        {
            try
            {
                // 出庫予定情報を論理削除
                deleteRetrievalPlan(ret);

                // 在庫パッケージが導入されていない場合
                if (!sysCtrl.hasStockPack())
                {
                    // 入出庫作業情報を論理削除
                    deleteWorkInfo(ret);
                }

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
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", ret.getStoreMetaData().getFieldMetaData(
                    Retrieval.CANCEL_FLAG.getName()).getDescription());
            return RESULT.SKIP;
        }
    }

    /**
     * 出庫予定情報の登録を行います。
     * 
     * @param rec 取込情報
     * @param planUkey 予定一意キー
     * @param sysCtrl システム定義コントローラ
     * @throws CommonException
     */
    protected void createRetrievalPlan(Retrieval ret, String planUkey, WarenaviSystemController sysCtrl)
            throws CommonException
    {
        // 出庫予定情報エンティティ
        RetrievalPlan retPlan = new RetrievalPlan();

        // 値の設定
        retPlan.setPlanUkey(planUkey);
        retPlan.setLoadUnitKey(getSourceFileNameOutOfExtention());
        retPlan.setFileLineNo(getDataCnt());
        retPlan.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        retPlan.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        // 在庫パッケージが導入されている場合
        if (sysCtrl.hasStockPack())
        {
            retPlan.setSchFlag(SystemDefine.SCH_FLAG_NOT_SCHEDULE);
        }
        else
        {
            retPlan.setSchFlag(SystemDefine.SCH_FLAG_SCHEDULE);
        }
        retPlan.setCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        retPlan.setPlanDay(ret.getPlanDay());
        retPlan.setCustomerCode(ret.getCustomerCode());
        retPlan.setShipTicketNo(ret.getShipTicketNo());
        retPlan.setShipLineNo(ret.getShipLineNo());
        retPlan.setBranchNo(ret.getBranchNo());
        retPlan.setBatchNo(ret.getBatchNo());
        retPlan.setOrderNo(ret.getOrderNo());
        retPlan.setPlanAreaNo(ret.getPlanAreaNo());
        retPlan.setPlanLocationNo(ret.getPlanLocationNo());
        retPlan.setItemCode(ret.getItemCode());
        retPlan.setPlanLotNo(ret.getLotNo());
        retPlan.setPlanQty(ret.getPlanQty());
        retPlan.setResultQty(0);
        retPlan.setShortageQty(0);
        retPlan.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
        retPlan.setWorkDay("");
        retPlan.setRegistKind(SystemDefine.REGIST_KIND_DATALOADER);
        retPlan.setRegistPname(this.getClass().getSimpleName());
        retPlan.setLastUpdatePname(this.getClass().getSimpleName());

        // 登録
        _retHandler.create(retPlan);
    }

    /**
     * 入出庫作業情報の登録を行います。
     * 
     * @param rec 取込情報
     * @param planUkey 予定一意キー
     * @param jobNo 作業No.
     * @throws CommonException
     */
    protected void createWorkInfo(Retrieval ret, String planUkey, String jobNo)
            throws CommonException
    {
        // 入出庫作業情報エンティティ
        WorkInfo wInfo = new WorkInfo();

        // 値の設定
        wInfo.setJobNo(jobNo);
        wInfo.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);
        wInfo.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
        wInfo.setHardwareType(WorkInfo.HARDWARE_TYPE_UNSTART);
        wInfo.setPlanUkey(planUkey);
        wInfo.setPlanDay(ret.getPlanDay());
        wInfo.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        wInfo.setSupplierCode("");
        wInfo.setReceiveTicketNo("");
        wInfo.setReceiveLineNo(0);
        wInfo.setCustomerCode(ret.getCustomerCode());
        wInfo.setShipTicketNo(ret.getShipTicketNo());
        wInfo.setShipLineNo(ret.getShipLineNo());
        wInfo.setShipBranchNo(ret.getBranchNo());
        wInfo.setBatchNo(ret.getBatchNo());
        wInfo.setOrderNo(ret.getOrderNo());
        wInfo.setPlanAreaNo(ret.getPlanAreaNo());
        wInfo.setPlanLocationNo(ret.getPlanLocationNo());
        wInfo.setItemCode(ret.getItemCode());
        wInfo.setPlanLotNo(ret.getLotNo());
        wInfo.setPlanQty(ret.getPlanQty());
        wInfo.setResultQty(0);
        wInfo.setShortageQty(0);
        wInfo.setWorkSecond(0);
        wInfo.setReasonType(SystemDefine.DEFAULT_REASON_TYPE);
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
    protected void createItem(Retrieval ret)
            throws CommonException
    {
        // 商品コードが入力されていない場合は処理しない
        if (StringUtil.isBlank(ret.getItemCode()))
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
        item.setItemCode(ret.getItemCode());

        // 商品名称が指定されている場合
        if (!StringUtil.isBlank(ret.getItemName()))
        {
            item.setItemName(ret.getItemName());
        }

        // JANコードが指定されている場合
        if (!StringUtil.isBlank(ret.getJan()))
        {
            item.setJan(ret.getJan());
        }

        // ITFが指定されている場合
        if (!StringUtil.isBlank(ret.getItf()))
        {
            item.setItf(ret.getItf());
        }
        item.setBundleItf("");

        // ケース入り数が0以上の場合
        if (ret.getEnteringQty() > 0)
        {
            item.setEnteringQty(ret.getEnteringQty());
        }
        item.setBundleEnteringQty(0);
        item.setLowerQty(0);
        item.setUpperQty(0);

        // 登録
        WmsUserInfo ui = new WmsUserInfo();
        itemctrl.autoCreate(item, ui);
    }

    /**
     * 出荷先マスタの登録、及び更新を行います。
     * 
     * @param rec 取込情報
     * @throws CommonException
     */
    protected void createCustomer(Retrieval ret)
            throws CommonException
    {
        // 出荷先コードが入力されていない場合は処理しない
        if (StringUtil.isBlank(ret.getCustomerCode()))
        {
            return;
        }

        // 出荷先マスタコントローラ
        CustomerController custctrl = new CustomerController(getConnection(), this.getClass());
        // 出荷先マスタエンティティ
        Customer cust = new Customer();

        // 値の設定
        cust.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        cust.setCustomerCode(ret.getCustomerCode());

        // 出荷先名称が指定されている場合
        if (!StringUtil.isBlank(ret.getCustomerName()))
        {
            cust.setCustomerName(ret.getCustomerName());
        }

        // 登録
        WmsUserInfo ui = new WmsUserInfo();
        custctrl.autoCreate(cust, ui);
    }

    /**
     * 出庫予定情報の削除を行います。
     * 
     * @param rec 取込情報
     * @throws CommonException
     */
    protected void deleteRetrievalPlan(Retrieval ret)
            throws CommonException
    {
        // 条件の設定
        _retAKey.clear();
        _retAKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        _retAKey.setShipTicketNo(ret.getShipTicketNo());
        _retAKey.setShipLineNo(ret.getShipLineNo());
        _retAKey.setBranchNo(ret.getBranchNo());
        _retAKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        // 値の設定
        _retAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        _retAKey.updateCancelFlag(SystemDefine.REGIST_KIND_DATALOADER);
        _retAKey.updateLastUpdatePname(this.getClass().getSimpleName());

        // 更新
        _retHandler.modify(_retAKey);
    }

    /**
     * 入出庫作業情報の削除を行います。
     * 
     * @param rec 取込情報
     * @throws CommonException
     */
    protected void deleteWorkInfo(Retrieval ret)
            throws CommonException
    {
        // 条件の設定
        _wInfoAKey.clear();
        _wInfoAKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        _wInfoAKey.setShipTicketNo(ret.getShipTicketNo());
        _wInfoAKey.setShipLineNo(ret.getShipLineNo());
        _wInfoAKey.setShipBranchNo(ret.getBranchNo());
        _wInfoAKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        _wInfoAKey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL);

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
        return new Retrieval();
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
        _retHandler.getStatics();
        _wInfoHandler.getStatics();
        _itemHandler.getStatics();
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
        return "$Id: RetrievalDataLoader.java 7909 2010-05-07 05:43:54Z kumano $";
    }
}

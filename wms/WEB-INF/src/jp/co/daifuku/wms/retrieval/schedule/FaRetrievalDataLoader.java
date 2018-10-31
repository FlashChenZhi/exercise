// $Id: FaRetrievalDataLoader.java 7636 2010-03-17 04:11:45Z okayama $
package jp.co.daifuku.wms.retrieval.schedule;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.fileentity.FaRetrieval;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;

/**
 * FA出庫予定データの取り込み処理を行います。<br>
 *
 * @version $Revision: 7636 $, $Date: 2010-03-17 13:11:45 +0900 (水, 17 3 2010) $
 * @author  Y.Okamura
 * @author  Last commit: $Author: okayama $
 */
public class FaRetrievalDataLoader
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
    /** 出庫予定ハンドラ */
    private RetrievalPlanHandler _retHandler = null;

    /** 出庫予定検索キー */
    private RetrievalPlanSearchKey _retKey = null;

    /** 出庫予定更新キー */
    private RetrievalPlanAlterKey _retAKey = null;

    /** 商品マスタハンドラ */
    private ItemHandler _itmHandler = null;

    /** 商品マスタ検索キー */
    private ItemSearchKey _itmKey = null;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 取り込み区分出庫の受信データの取り込みを行います。<BR>
     * 自動取込みから使用します。
     */
    public FaRetrievalDataLoader()
    {
        super(ExchangeEnvironment.LOAD_DATA_TYPE_RETRIEVAL);
    }

    /**
     * 取り込み区分出庫の受信データの取り込みを行います。<BR>
     * WEB画面から使用します。
     * 
     * @param userinfo ユーザ情報
     * @param locale ロケール
     */
    public FaRetrievalDataLoader(DfkUserInfo userinfo, Locale locale)
    {
        super(ExchangeEnvironment.LOAD_DATA_TYPE_RETRIEVAL, userinfo, locale);
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
        _retHandler = new RetrievalPlanHandler(getConnection());
        _retKey = new RetrievalPlanSearchKey();
        _retAKey = new RetrievalPlanAlterKey();
        _itmHandler = new ItemHandler(getConnection());
        _itmKey = new ItemSearchKey();

    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#check(jp.co.daifuku.wms.handler.Entity)
     */
    @Override
    protected RESULT check(Entity ent)
            throws CommonException
    {
        FaRetrieval ret = (FaRetrieval)ent;

        // 日付のフォーマットチェック
        RESULT chk = isDay(FaRetrieval.PLAN_DAY, ret.getPlanDay());
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

        // 取消区分が登録の場合
        if (ret.getCancelFlag().equals(SystemDefine.CANCEL_FLAG_NORMAL))
        {
            // 異常棚商品コード入力チェック・簡易直行用商品コード入力チェック
            if (WmsParam.IRREGULAR_ITEMCODE.equals(ret.getItemCode())
                    || WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE.equals(ret.getItemCode()))
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                        ret.getStoreMetaData().getFieldMetaData(FaRetrieval.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 数量のチェック
            if (ret.getPlanQty() <= 0)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                        ret.getStoreMetaData().getFieldMetaData(FaRetrieval.PLAN_QTY.getName()).getDescription());
                return RESULT.SKIP;
            }
            else if (ret.getPlanQty() > WmsParam.MAX_TOTAL_QTY)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                        ret.getStoreMetaData().getFieldMetaData(FaRetrieval.PLAN_QTY.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 商品コードが商品マスタに登録されていない場合はスキップとする
            _itmKey.clear();
            _itmKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _itmKey.setItemCode(ret.getItemCode());
            if (_itmHandler.count(_itmKey) == 0)
            {
                // MSG-W0023=マスタ未登録
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                        ret.getStoreMetaData().getFieldMetaData(FaRetrieval.ITEM_CODE.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 同一バッチで異なる予定日の出庫予定情報がある場合、スキップとする
            _retKey.clear();
            _retKey.setBatchNo(ret.getBatchNo());
            _retKey.setPlanDay(ret.getPlanDay(), "!=");
            _retKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            if (_retHandler.count(_retKey) > 0)
            {
                // MSG-W0075=同一バッチ内に複数予定日
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0075",
                        ret.getStoreMetaData().getFieldMetaData(FaRetrieval.PLAN_DAY.getName()).getDescription());
                return RESULT.SKIP;
            }

            // 同一の「出庫予定日＋伝票No.＋行No.＋オーダーNo.」が既に登録されている場合はスキップとする
            _retKey.clear();
            _retKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _retKey.setPlanDay(ret.getPlanDay());
            _retKey.setShipTicketNo(ret.getShipTicketNo());
            _retKey.setShipLineNo(ret.getShipLineNo());
            _retKey.setBatchNo(ret.getBatchNo());
            _retKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            if (_retHandler.count(_retKey) > 0)
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025", "");
                return RESULT.SKIP;
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;

        }
        // 取消区分が削除の場合
        else if (ret.getCancelFlag().equals(SystemDefine.CANCEL_FLAG_HOST_CANCEL))
        {
            // 未登録データの場合はスキップとする(要求仕様)
            // 出庫開始済のデータはスキップとする(要求仕様)
            // 未開始の同一データを検索し、なければスキップとする
            // 同一条件 = 出庫予定日＋伝票No.＋行No.＋オーダーNo.
            _retKey.clear();
            _retKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _retKey.setPlanDay(ret.getPlanDay());
            _retKey.setShipTicketNo(ret.getShipTicketNo());
            _retKey.setShipLineNo(ret.getShipLineNo());
            _retKey.setBatchNo(ret.getBatchNo());

            _retKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");

            try
            {
                RetrievalPlan[] retPlans = (RetrievalPlan[])_retHandler.findForUpdate(_retKey);
                if (retPlans == null || retPlans.length == 0)
                {
                    // MSG-W0027=取消該当データ無し
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0027", "");
                    return RESULT.SKIP;
                }
                else if (!retPlans[0].getSchFlag().equals(RetrievalPlan.SCH_FLAG_NOT_SCHEDULE))
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
        // 区分誤りの場合はスキップ(ここは通らないはず)
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", ret.getStoreMetaData().getFieldMetaData(
                    FaRetrieval.CANCEL_FLAG.getName()).getDescription());
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
        FaRetrieval ret = (FaRetrieval)ent;

        // 取消区分が登録の場合
        if (ret.getCancelFlag().equals(SystemDefine.CANCEL_FLAG_NORMAL))
        {
            WMSSequenceHandler _seq = new WMSSequenceHandler(getConnection());
            RetrievalPlan retPlan = new RetrievalPlan();
            try
            {
                retPlan.setPlanUkey(_seq.nextRetrievalPlanUkey());
            }
            catch (ReadWriteException e)
            {
            }

            retPlan.setLoadUnitKey(getSourceFileNameOutOfExtention());
            retPlan.setFileLineNo(getDataCnt());
            retPlan.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
            retPlan.setSchFlag(RetrievalPlan.SCH_FLAG_NOT_SCHEDULE);
            retPlan.setCancelFlag(RetrievalPlan.CANCEL_FLAG_NORMAL);
            retPlan.setPlanDay(ret.getPlanDay());
            retPlan.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            retPlan.setCustomerCode("");
            retPlan.setShipTicketNo(ret.getShipTicketNo());
            retPlan.setShipLineNo(ret.getShipLineNo());
            retPlan.setBranchNo(0);
            retPlan.setBatchNo(ret.getBatchNo());
            retPlan.setOrderNo(ret.getBatchNo());
            retPlan.setPlanAreaNo("");
            retPlan.setPlanLocationNo("");
            retPlan.setItemCode(ret.getItemCode());
            retPlan.setPlanLotNo(ret.getLotNo());
            retPlan.setPlanQty(ret.getPlanQty());
            retPlan.setReportFlag(RetrievalPlan.REPORT_FLAG_NOT_REPORT);
            retPlan.setRegistKind(RetrievalPlan.REGIST_KIND_DATALOADER);
            retPlan.setRegistPname(this.getClass().getSimpleName());
            retPlan.setLastUpdatePname(this.getClass().getSimpleName());

            try
            {
                _retHandler.create(retPlan);
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
        // 取消区分が削除の場合
        else if (ret.getCancelFlag().equals(SystemDefine.CANCEL_FLAG_HOST_CANCEL))
        {
            // 未登録データの場合はスキップとする(要求仕様)
            // 出庫開始済のデータはスキップとする(要求仕様)
            // 未開始の同一データを検索し、なければスキップとする
            // 同一条件 = 出庫予定日＋伝票No.＋行No.＋オーダーNo.
            _retAKey.clear();
            _retAKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            _retAKey.setPlanDay(ret.getPlanDay());
            _retAKey.setShipTicketNo(ret.getShipTicketNo());
            _retAKey.setShipLineNo(ret.getShipLineNo());
            _retAKey.setBatchNo(ret.getBatchNo());

            _retAKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            _retAKey.setSchFlag(RetrievalPlan.SCH_FLAG_NOT_SCHEDULE);

            _retAKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE);
            _retAKey.updateCancelFlag(RetrievalPlan.CANCEL_FLAG_HOST_CANCEL);
            _retAKey.updateLastUpdatePname(this.getClass().getSimpleName());

            try
            {
                _retHandler.modify(_retAKey);
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
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", ret.getStoreMetaData().getFieldMetaData(
                    FaRetrieval.CANCEL_FLAG.getName()).getDescription());
            return RESULT.SKIP;
        }
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#getEntity()
     */
    @Override
    protected AbstractEntity getEntity()
    {
        return new FaRetrieval();
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
        return "$Id: FaRetrievalDataLoader.java 7636 2010-03-17 04:11:45Z okayama $";
    }

}

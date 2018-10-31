// $Id: WorkInfoController.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.common.Parameter.COMPLETION_FLAG_BOXCHANGE;
import static jp.co.daifuku.wms.base.common.Parameter.COMPLETION_FLAG_REMNANT;
import static jp.co.daifuku.wms.base.entity.SystemDefine.HARDWARE_TYPE_LIST;
import static jp.co.daifuku.wms.base.entity.SystemDefine.HARDWARE_TYPE_UNSTART;
import static jp.co.daifuku.wms.base.entity.SystemDefine.JOB_TYPE_RETRIEVAL;
import static jp.co.daifuku.wms.base.entity.SystemDefine.JOB_TYPE_STORAGE;
import static jp.co.daifuku.wms.base.entity.SystemDefine.JOB_TYPE_RESTORING;
import static jp.co.daifuku.wms.base.entity.SystemDefine.RETRIEVAL_JOB_STATUS_FLAG_COMPLETION;
import static jp.co.daifuku.wms.base.entity.SystemDefine.RETRIEVAL_JOB_STATUS_FLAG_NOWWORKING;
import static jp.co.daifuku.wms.base.entity.SystemDefine.RETRIEVAL_JOB_STATUS_FLAG_UNSTART;
import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_COMPLETION;
import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_DELETE;
import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_NOWWORKING;
import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_UNSTART;
import static jp.co.daifuku.wms.base.exception.OperatorException.ERR_ALREADY_COMPLETED;
import static jp.co.daifuku.wms.base.exception.OperatorException.ERR_ALREADY_UPDATED;
import static jp.co.daifuku.wms.base.exception.OperatorException.ERR_NOT_START_EXISTS;
import static jp.co.daifuku.wms.base.exception.OperatorException.ERR_WORKING_INPROGRESS;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.AlterKey;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;
import jp.co.daifuku.wms.handler.db.SQLSearchKey;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * 入出庫作業情報を操作するためのコントローラクラスです。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  ss
 * @author  Last commit: $Author: kitamaki $
 */
// UPDATE_SS (2007-07-06)
public class WorkInfoController
        extends AbstractController
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private WMSSequenceHandler _seqHandler = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)<br>
     *
     * このコンストラクタでは、WMSSequenceHandlerを初期化します。
     *
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public WorkInfoController(Connection conn, Class caller)
    {
        super(conn, caller);
        _seqHandler = new WMSSequenceHandler(getConnection());
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 入庫開始用に入出庫作業情報の検索およびロックを行います。
     *
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>荷主コード
     * <li>予定日
     * <li>商品コード
     * <li>予定エリア
     * <li>予定棚
     * <li>予定ロットNo.
     * </ol>
     * @return 対象の入出庫作業情報
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public WorkInfo[] lockStorageStart(WorkInfo tgt)
            throws ReadWriteException,
                LockTimeOutException
    {
        AbstractDBHandler wih = new WorkInfoHandler(getConnection());
        SQLSearchKey key = new WorkInfoSearchKey();

        FieldName[] keyFields = {
                WorkInfo.CONSIGNOR_CODE,
                WorkInfo.PLAN_DAY,
                WorkInfo.ITEM_CODE,
                WorkInfo.PLAN_AREA_NO,
                WorkInfo.PLAN_LOCATION_NO,
                WorkInfo.PLAN_LOT_NO,
        };
        key = createKey(tgt, key, keyFields);

        key.setKey(WorkInfo.STATUS_FLAG, STATUS_FLAG_UNSTART);
        key.setKey(WorkInfo.JOB_TYPE, JOB_TYPE_STORAGE);

        key.setOrder(WorkInfo.WORK_DAY, true);
        key.setOrder(WorkInfo.PLAN_AREA_NO, true);
        key.setOrder(WorkInfo.PLAN_LOCATION_NO, true);
        key.setOrder(WorkInfo.PLAN_LOT_NO, true);
        key.setOrder(WorkInfo.PLAN_UKEY, true);

        WorkInfo[] records;
        records = (WorkInfo[])wih.findForUpdate(key);
        return records;
    }

    /**
     * 再入庫開始用に入出庫作業情報の検索およびロックを行います。
     *
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>予定一意キー
     * </ol>
     * @return 対象の入出庫作業情報
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 一意キーエラーが発生したときスローされます。
     */
    public WorkInfo lockRestoringStart(WorkInfo tgt)
            throws ReadWriteException,
                NoPrimaryException,
                LockTimeOutException
    {
        AbstractDBHandler wih = new WorkInfoHandler(getConnection());
        SQLSearchKey key = new WorkInfoSearchKey();

        key.setKey(WorkInfo.PLAN_UKEY, tgt.getPlanUkey());

        key.setKey(WorkInfo.STATUS_FLAG, STATUS_FLAG_UNSTART);
        key.setKey(WorkInfo.JOB_TYPE, JOB_TYPE_RESTORING);

        WorkInfo records;
        records = (WorkInfo)wih.findPrimaryForUpdate(key);
        return records;
    }

    /**
     * 出庫開始用に入出庫作業情報の検索およびロックを行います。
     *
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>荷主コード
     * <li>予定エリア
     * <li>バッチNo.
     * </ol>
     *
     * @param orderNos オーダーNo.
     * @return 対象の入出庫作業情報
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public WorkInfo[] lockRetrievalStart(WorkInfo tgt, String[] orderNos)
            throws ReadWriteException,
                LockTimeOutException
    {
        AbstractDBHandler wih = new WorkInfoHandler(getConnection());
        SQLSearchKey key = new WorkInfoSearchKey();

        FieldName[] keyFields = {
                WorkInfo.CONSIGNOR_CODE,
                WorkInfo.BATCH_NO,
                WorkInfo.PLAN_AREA_NO,
        };
        key = createKey(tgt, key, keyFields);

        key.setKey(WorkInfo.ORDER_NO, orderNos, true);
        key.setKey(WorkInfo.STATUS_FLAG, STATUS_FLAG_UNSTART);
        key.setKey(WorkInfo.JOB_TYPE, JOB_TYPE_RETRIEVAL);

        key.setOrder(WorkInfo.PLAN_LOCATION_NO, true);
        key.setOrder(WorkInfo.ITEM_CODE, true);
        key.setOrder(WorkInfo.PLAN_LOT_NO, true);
        key.setOrder(WorkInfo.ORDER_NO, true);
        key.setOrder(WorkInfo.PLAN_UKEY, true);

        WorkInfo[] records;
        records = (WorkInfo[])wih.findForUpdate(key);
        return records;
    }

    /**
     * 出庫開始用に入出庫作業情報のオーダー連番設定を行います。
     *
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>荷主コード
     * <li>予定エリア
     * <li>バッチNo.
     * </ol>
     * @param ui ユーザ情報
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public void setOrderSerialNo(WorkInfo tgt, WmsUserInfo ui)
            throws NotFoundException,
                ReadWriteException
    {
        AbstractDBHandler wih = new WorkInfoHandler(getConnection());
        AlterKey akey = new WorkInfoAlterKey();

        FieldName[] updateFields = {
            WorkInfo.ORDER_SERIAL_NO
        };
        akey = createAlterKey(tgt, akey, updateFields);
        akey.setAdhocUpdateValue(WorkInfo.HARDWARE_TYPE, ui.getHardwareType());
        akey.setAdhocUpdateValue(WorkInfo.USER_ID, ui.getUserId());
        akey.setAdhocUpdateValue(WorkInfo.TERMINAL_NO, ui.getTerminalNo());
        akey.setAdhocUpdateValue(WorkInfo.LAST_UPDATE_PNAME, getCallerName());
        akey.setKey(WorkInfo.ORDER_NO, tgt.getOrderNo());
        akey.setKey(WorkInfo.CONSIGNOR_CODE, tgt.getConsignorCode());
        akey.setKey(WorkInfo.PLAN_AREA_NO, tgt.getPlanAreaNo());
        if (!StringUtil.isBlank(tgt.getBatchNo()))
        {
            akey.setKey(WorkInfo.BATCH_NO, tgt.getBatchNo());
        }
        wih.modify(akey);
    }

    /**
     * 完了用に入出庫作業情報,予定情報のロックを行います。
     *
     * @param settingUnitKey 設定単位キー
     * @param jobType 作業区分
     * @return 作業情報 (予定一意キーのみセットされます)
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws InvalidDefineException 作業区分が 入庫または出庫ではないときスローされます。
     */
    public WorkInfo[] lockComplete(String settingUnitKey, String jobType)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                InvalidDefineException
    {
        if (!JOB_TYPE_STORAGE.equals(jobType) && !JOB_TYPE_RETRIEVAL.equals(jobType))
        {
            throw new InvalidDefineException();
        }

        AbstractDBHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        key.setKey(WorkInfo.SETTING_UNIT_KEY, settingUnitKey);
        key.setKey(WorkInfo.STATUS_FLAG, STATUS_FLAG_NOWWORKING);

        boolean storage = JOB_TYPE_STORAGE.equals(jobType);
        FieldName joinFld = (storage) ? StoragePlan.PLAN_UKEY
                                     : RetrievalPlan.PLAN_UKEY;
        key.setJoin(WorkInfo.PLAN_UKEY, joinFld);

        key.setOrder(WorkInfo.PLAN_UKEY, true);

        key.setCollect(WorkInfo.PLAN_UKEY);

        WorkInfo[] ents = (WorkInfo[])wih.findForUpdate(key);
        if (ArrayUtil.isEmpty(ents))
        {
            throw new NotFoundException();
        }
        return ents;
    }

    /**
     * 予定取り込み用に未作業の入出庫作業情報をロックします。
     *
     * @param jobType ロック対象の作業区分
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public void lockLoad(String jobType)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException
    {
        AbstractDBHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        key.setKey(WorkInfo.JOB_TYPE, jobType);
        key.setKey(WorkInfo.STATUS_FLAG, STATUS_FLAG_UNSTART);

        wih.findForUpdate(key);
    }

    /**
     * 入出庫作業情報を作業中に更新します。
     *
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>作業No.
     * <li>設定単位キー
     * <li>集約作業No
     * </ol>
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public void startWorkInfo(WorkInfo tgt, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException
    {
        AbstractDBHandler wih = new WorkInfoHandler(getConnection());
        AlterKey akey = new WorkInfoAlterKey();

        FieldName[] updateFields = {
                WorkInfo.SETTING_UNIT_KEY,
                WorkInfo.COLLECT_JOB_NO,
        };
        akey = createAlterKey(tgt, akey, updateFields);

        akey.setAdhocUpdateValue(WorkInfo.STATUS_FLAG, STATUS_FLAG_NOWWORKING);
        akey.setAdhocUpdateValue(WorkInfo.HARDWARE_TYPE, ui.getHardwareType());
        akey.setAdhocUpdateValue(WorkInfo.USER_ID, ui.getUserId());
        akey.setAdhocUpdateValue(WorkInfo.TERMINAL_NO, ui.getTerminalNo());
        akey.setAdhocUpdateValue(WorkInfo.LAST_UPDATE_PNAME, getCallerName());
        akey.setKey(WorkInfo.JOB_NO, tgt.getJobNo());

        wih.modify(akey);
    }

    /**
     * 入出庫作業情報の完了処理を行います。
     *
     * @param src 完了対象データ
     * <ol>
     * 参照される項目は以下の通りです
     * <li>予定数
     * <li>作業No.
     * <li>ハードウェア区分
     * </ol>
     *
     * @param result 完了情報
     * <ol>
     * 参照される項目は以下の通りです
     * <li>実績エリア
     * <li>実績棚
     * <li>実績ロットNo.
     * <li>実績数
     * <li>欠品数
     * <li>作業日
     * <li>作業秒数
     * </ol>
     *
     * @param compFlag 完了フラグ
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws DataExistsException 分割時に該当のデータがすでに登録されていたときスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在したときスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった
     */
    public void completeWorkInfo(WorkInfo src, WorkInfo result, String compFlag, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        // 実績数+欠品数>0か在庫確認、又は、棚間移動時は完了
        if ((0 < (result.getResultQty() + result.getShortageQty()))
                || result.getResultQty() == 0
                || (WorkInfo.JOB_TYPE_ASRS_INVENTORYCHECK.equals(src.getJobType()))
                || (WorkInfo.JOB_TYPE_ASRS_RACK_TO_RACK.equals(src.getJobType()))
                || (WorkInfo.JOB_TYPE_ASRS_REARRANGE.equals(src.getJobType())))
        {
            // complete processing
            completeWorkInfoComplete(src, result, compFlag, ui);
        }
        else
        {
            // cancel processing
            completeWorkInfoCancel(src, compFlag, ui);
        }
    }

    /**
     * 入出庫作業情報をキャンセルします。
     *
     * @param settingUnitKey 設定単位キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public void cancelWorkInfo(String settingUnitKey)
            throws ReadWriteException,
                NotFoundException
    {
        WorkInfoHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoAlterKey akey = createCancelKey();

        akey.setSettingUnitKey(settingUnitKey);
        akey.setStatusFlag(STATUS_FLAG_NOWWORKING);

        wih.modify(akey);
    }

    /**
     * 入庫開始可能かどうかチェックします。
     *
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>荷主コード
     * <li>予定日
     * <li>予定エリア
     * <li>予定棚
     * <li>予定ロットNo.
     * </ol>
     *
     * @param items 対象商品コード一覧
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws OperatorException 開始可能でないとき、内容が返されます。
     */
    public void checkStorageStart(WorkInfo tgt, String... items)
            throws ReadWriteException,
                NotFoundException,
                OperatorException
    {
        WorkInfoHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        FieldName[] keyFields = {
                WorkInfo.CONSIGNOR_CODE,
                WorkInfo.PLAN_DAY,
                WorkInfo.PLAN_AREA_NO,
                WorkInfo.PLAN_LOCATION_NO,
                WorkInfo.PLAN_LOT_NO,
        };
        key = (WorkInfoSearchKey)createKey(tgt, key, keyFields);

        key.setItemCode(items, true);
        // fix status flag
        key.setKey(WorkInfo.STATUS_FLAG, STATUS_FLAG_DELETE, "!=", "", "", true);
        key.setKey(WorkInfo.JOB_TYPE, JOB_TYPE_STORAGE);

        FieldName minStatusField = new FieldName("", "MIN_STATUS_");
        key.setCollect(WorkInfo.STATUS_FLAG, "MIN", minStatusField);

        Entity[] recs = wih.find(key);
        if (ArrayUtil.isEmpty(recs))
        {
            throw new NotFoundException();
        }

        Object minStat = recs[0].getValue(minStatusField);

        if (STATUS_FLAG_UNSTART.equals(minStat))
        {
            throw new OperatorException(ERR_ALREADY_UPDATED);
        }
        else if (STATUS_FLAG_NOWWORKING.equals(minStat))
        {
            throw new OperatorException(ERR_WORKING_INPROGRESS);
        }
        if (STATUS_FLAG_COMPLETION.equals(minStat))
        {
            throw new OperatorException(ERR_ALREADY_COMPLETED);
        }
        throw new NotFoundException();
    }

    /**
     * 再入庫開始可能かどうかチェックします。
     *
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>予定一意キー
     * </ol>
     *
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 一意キーエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws OperatorException 開始可能でないとき、内容が返されます。
     */
    public void checkReStoringStart(WorkInfo tgt)
            throws ReadWriteException,
                NoPrimaryException,
                NotFoundException,
                OperatorException
    {
        WorkInfoHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        key.setKey(WorkInfo.PLAN_UKEY, tgt.getPlanUkey());

        // fix status flag
        key.setKey(WorkInfo.STATUS_FLAG, STATUS_FLAG_DELETE, "!=", "", "", true);
        key.setKey(WorkInfo.JOB_TYPE, JOB_TYPE_RESTORING);

        key.setCollect(WorkInfo.STATUS_FLAG);

        // DFKLOOK 3.5 start
        Entity[] recs = wih.find(key);
        if (ArrayUtil.isEmpty(recs))
        {
            throw new NotFoundException();
        }
        Object Stat = recs[0].getValue(WorkInfo.STATUS_FLAG);
        // DFKLOOK 3.5 end

        if (STATUS_FLAG_UNSTART.equals(Stat))
        {
            throw new OperatorException(ERR_ALREADY_UPDATED);
        }
        else if (STATUS_FLAG_NOWWORKING.equals(Stat))
        {
            throw new OperatorException(ERR_WORKING_INPROGRESS);
        }
        if (STATUS_FLAG_COMPLETION.equals(Stat))
        {
            throw new OperatorException(ERR_ALREADY_COMPLETED);
        }
        throw new NotFoundException();
    }

    /**
     * 出庫開始可能かどうかチェックします。
     *
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>荷主コード
     * <li>予定エリア
     * <li>オーダーNo.
     * </ol>
     *
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws OperatorException 開始可能でないとき、内容が返されます。
     */
    public void checkRetrievalStart(WorkInfo tgt)
            throws ReadWriteException,
                NotFoundException,
                OperatorException
    {
        WorkInfoHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        FieldName[] keyFields = {
                WorkInfo.CONSIGNOR_CODE,
                WorkInfo.BATCH_NO,
                WorkInfo.PLAN_AREA_NO,
                WorkInfo.ORDER_NO,
        };
        key = (WorkInfoSearchKey)createKey(tgt, key, keyFields);
        // fix status flag
        key.setKey(WorkInfo.JOB_TYPE, JOB_TYPE_RETRIEVAL);
        key.setKey(WorkInfo.STATUS_FLAG, STATUS_FLAG_DELETE, "!=", "", "", true);

        FieldName minStatusField = new FieldName("", "MIN_STATUS_");
        key.setCollect(WorkInfo.STATUS_FLAG, "MIN", minStatusField);

        Entity[] recs = wih.find(key);
        if (ArrayUtil.isEmpty(recs))
        {
            throw new NotFoundException();
        }

        Object minStat = recs[0].getValue(minStatusField);

        if (STATUS_FLAG_UNSTART.equals(minStat))
        {
            throw new OperatorException(ERR_NOT_START_EXISTS);
        }
        else if (STATUS_FLAG_NOWWORKING.equals(minStat))
        {
            throw new OperatorException(ERR_WORKING_INPROGRESS);
        }
        if (STATUS_FLAG_COMPLETION.equals(minStat))
        {
            throw new OperatorException(ERR_ALREADY_COMPLETED);
        }
        throw new NotFoundException();
    }

    /**
     * 予定情報の状態を取得します。
     *
     * @param planUkey 予定一意キー
     * @return 状態フラグ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public String getPlanStatus(String planUkey)
            throws ReadWriteException,
                NotFoundException
    {
        WorkInfoHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        key.setPlanUkey(planUkey);
        key.setStatusFlag(STATUS_FLAG_DELETE, "!=", "", "", true);
        key.setStatusFlagCollect("UNIQUE");
        key.setJobTypeCollect();

        Entity[] recs = wih.find(key);
        if (ArrayUtil.isEmpty(recs))
        {
            throw new NotFoundException();
        }

        if (1 == recs.length)
        {
            Object statusFlg = recs[0].getValue(WorkInfo.STATUS_FLAG);
            if (STATUS_FLAG_UNSTART.equals(statusFlg))
            {
                return STATUS_FLAG_UNSTART;
            }
            else if (STATUS_FLAG_COMPLETION.equals(statusFlg))
            {
            	if(WorkInfo.JOB_TYPE_RETRIEVAL.equals(recs[0].getValue(WorkInfo.JOB_TYPE)))
            	{
            		// 出庫作業の場合は、スケジュールフラグをチェックする
            		RetrievalPlanSearchKey rkey = new RetrievalPlanSearchKey();
            		rkey.setPlanUkey(planUkey);
            		rkey.setSchFlagCollect();

            		Entity[] plan = (new RetrievalPlanHandler(getConnection())).find(rkey);

            		if (RetrievalPlan.SCH_FLAG_RESERVATION_SHORTAGE.equals(plan[0].getValue(RetrievalPlan.SCH_FLAG)))
            		{
            			// 欠品予約の場合は作業中
            			return STATUS_FLAG_NOWWORKING;
            		}
            	}
                return STATUS_FLAG_COMPLETION;
            }
        }
        return STATUS_FLAG_NOWWORKING;
    }

    /**
     * 入出庫作業情報から作業No.を取得し、集約して返します。
     *
     * @param works 作業情報
     * @return 重複作業No.をなくした作業No.の一覧
     */
    public static String[] getUniqueJobNos(WorkInfo[] works)
    {
        Set<String> jobNoSet = new HashSet<String>();
        for (WorkInfo work : works)
        {
            jobNoSet.add(work.getJobNo());
        }
        String[] jobNos = jobNoSet.toArray(new String[jobNoSet.size()]);
        return jobNos;
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * seqHandlerを返します。
     * @return seqHandlerを返します。
     */
    protected WMSSequenceHandler getSeqHandler()
    {
        return _seqHandler;
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 作業情報の完了処理(キャンセル)を行います。<br>
     *
     * @param src 完了(キャンセル)対象データ
     * <ol>
     * 参照される項目は以下の通りです
     * <li>作業No.
     * </ol>
     *
     * @param compFlag 完了フラグ
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    protected void completeWorkInfoCancel(WorkInfo src, String compFlag, WmsUserInfo ui)
            throws NotFoundException,
                ReadWriteException
    {
        boolean div = COMPLETION_FLAG_REMNANT.equals(compFlag) && HARDWARE_TYPE_LIST.equals(ui.getHardwareType());
        boolean boxc = COMPLETION_FLAG_BOXCHANGE.equals(compFlag);
        if (div || boxc)
        {
            return;
        }

        AbstractDBHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoAlterKey akey = createCancelKey();

        akey.setJobNo(src.getJobNo());

        wih.modify(akey);
    }

    /**
     * 作業情報の完了処理を行います。
     *
     * @param src 完了対象データ
     * <ol>
     * 参照される項目は以下の通りです
     * <li>予定数
     * <li>作業No.
     * <li>ハードウエア区分
     * </ol>
     *
     * @param resultwork 完了情報
     * <ol>
     * 参照される項目は以下の通りです
     * <li>実績エリア
     * <li>実績棚
     * <li>実績ロットNo.
     * <li>実績数
     * <li>欠品数
     * <li>作業日
     * <li>作業秒数
     * </ol>
     *
     * @param compFlag 完了フラグ
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws DataExistsException 分割時に該当のデータがすでに登録されていたときスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在したときスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった
     */
    protected void completeWorkInfoComplete(WorkInfo src, WorkInfo resultwork, String compFlag, WmsUserInfo ui)
            throws NotFoundException,
                ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        // work type check
        AbstractDBHandler wih = new WorkInfoHandler(getConnection());
        AlterKey akey = new WorkInfoAlterKey();

        boolean divided =
                (resultwork.getResultQty() < src.getPlanQty())
                        && (COMPLETION_FLAG_REMNANT.equals(compFlag) || COMPLETION_FLAG_BOXCHANGE.equals(compFlag));

        // update field setup
        FieldName[] updateFields = {
                WorkInfo.STOCK_ID,
                WorkInfo.RESULT_QTY,
                WorkInfo.SHORTAGE_QTY,
                WorkInfo.RESULT_AREA_NO,
                WorkInfo.RESULT_LOCATION_NO,
                WorkInfo.RESULT_LOT_NO,
                WorkInfo.WORK_DAY,
                WorkInfo.WORK_SECOND,
        };

        akey = createAlterKey(resultwork, akey, updateFields);

        akey.setAdhocUpdateValue(WorkInfo.STATUS_FLAG, STATUS_FLAG_COMPLETION);

        // akey.setAdhocUpdateValue(WorkInfo.HARDWARE_TYPE, ui.getHardwareType());
        akey.setAdhocUpdateValue(WorkInfo.USER_ID, ui.getUserId());
        akey.setAdhocUpdateValue(WorkInfo.TERMINAL_NO, ui.getTerminalNo());
        akey.setAdhocUpdateValue(WorkInfo.LAST_UPDATE_PNAME, getCallerName());

        if (divided)
        {
            akey.setAdhocUpdateValue(WorkInfo.PLAN_QTY, resultwork.getValue(WorkInfo.RESULT_QTY));
        }

        // set key field
        akey.setKey(WorkInfo.JOB_NO, src.getJobNo());

        // update work info
        wih.modify(akey);

        // divided, create work info for divide
        if (divided)
        {
            WorkInfo divworkinfo = (WorkInfo)src.clone();
            // get next job number
            resultwork.getJobType(); // FIXME JobType どこかにセットする?
            String jobNo = _seqHandler.nextWorkInfoJobNo();

            divworkinfo.setJobNo(jobNo);
            divworkinfo.setPlanQty(src.getPlanQty() - resultwork.getResultQty());
            divworkinfo.setResultQty(0);
            divworkinfo.setShortageQty(0);
            divworkinfo.setRegistPname(getCallerName());
            divworkinfo.setRegistDate(new SysDate());
            divworkinfo.setLastUpdatePname(getCallerName());

            String hardwareType = ui.getHardwareType();
            // additional (divide and not LIST)
            if (COMPLETION_FLAG_REMNANT.equals(compFlag) && !HARDWARE_TYPE_LIST.equals(hardwareType))
            {
                divworkinfo.setSettingUnitKey("");
                divworkinfo.setCollectJobNo("");
                divworkinfo.setStatusFlag(STATUS_FLAG_UNSTART);
                divworkinfo.setHardwareType(HARDWARE_TYPE_UNSTART);
                divworkinfo.setUserId("");
                divworkinfo.setTerminalNo("");
            }
            wih.create(divworkinfo);
        }

        // hostsend
        HostSendController hsc = new HostSendController(getConnection(), getCaller());
        boolean isHostsend = hsc.checkHostSendType(src.getConsignorCode(), src.getItemCode());
        if (isHostsend)
        {
            hsc.insertByWorkInfo(src.getJobNo(), ui);
        }
    }

    /**
     * キャンセル用の更新キーを生成して返します。
     *
     * @return 更新内容がセットされた更新キー
     */
    protected WorkInfoAlterKey createCancelKey()
    {
        WorkInfoAlterKey akey = new WorkInfoAlterKey();

        akey.updateSettingUnitKey("");
        akey.updateCollectJobNo("");
        akey.updateRftStatusFlag(STATUS_FLAG_UNSTART);
        akey.updateOrderSerialNo("");
        akey.updateSkipCnt(0);
        akey.updateResultAreaNo("");
        akey.updateResultLocationNo("");
        akey.updateResultLotNo("");
        akey.updateResultQty(0);
        akey.updateShortageQty(0);
        akey.updateWorkSecond(0);
        akey.updateStatusFlag(STATUS_FLAG_UNSTART);
        akey.updateHardwareType(HARDWARE_TYPE_UNSTART);
        akey.updateUserId("");
        akey.updateTerminalNo("");
        akey.updateLastUpdatePname(getCallerName());

        return akey;
    }

    /**
     * 出庫開始用に入出庫作業情報のRFT作業状態を作業中にします。
     *
     * @param resultwork 作業情報
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public void rftUpdateResultWorkNow(WorkInfo resultwork, WmsUserInfo ui)
            throws NotFoundException,
                ReadWriteException
    {

        AbstractDBHandler wih = new WorkInfoHandler(getConnection());
        AlterKey akey = new WorkInfoAlterKey();

        // update field setup
        FieldName[] updateFields = {
            WorkInfo.WORK_DAY,
        };

        akey = createAlterKey(resultwork, akey, updateFields);

        akey.setAdhocUpdateValue(WorkInfo.RFT_STATUS_FLAG, RETRIEVAL_JOB_STATUS_FLAG_NOWWORKING);
        akey.setAdhocUpdateValue(WorkInfo.USER_ID, ui.getUserId());
        akey.setAdhocUpdateValue(WorkInfo.TERMINAL_NO, ui.getTerminalNo());
        akey.setAdhocUpdateValue(WorkInfo.LAST_UPDATE_PNAME, getCallerName());

        akey.setKey(WorkInfo.JOB_NO, resultwork.getJobNo());

        wih.modify(akey);
    }

    /**
     * RFT出庫作業のスキップ回数を更新します。
     *
     * @param resultwork 作業情報
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public void rftUpdateResultSkipCnt(WorkInfo resultwork, WmsUserInfo ui)
            throws NotFoundException,
                ReadWriteException
    {
        AbstractDBHandler wih = new WorkInfoHandler(getConnection());
        AlterKey akey = new WorkInfoAlterKey();

        // update field setup
        FieldName[] updateFields = {
                WorkInfo.WORK_DAY,
                WorkInfo.WORK_SECOND,
        };

        akey = createAlterKey(resultwork, akey, updateFields);

        akey.setAdhocUpdateValue(WorkInfo.SKIP_CNT, HandlerUtil.toObject(resultwork.getSkipCnt() + 1));
        akey.setAdhocUpdateValue(WorkInfo.RFT_STATUS_FLAG, RETRIEVAL_JOB_STATUS_FLAG_UNSTART);
        akey.setAdhocUpdateValue(WorkInfo.USER_ID, ui.getUserId());
        akey.setAdhocUpdateValue(WorkInfo.TERMINAL_NO, ui.getTerminalNo());
        akey.setAdhocUpdateValue(WorkInfo.LAST_UPDATE_PNAME, getCallerName());

        akey.setKey(WorkInfo.JOB_NO, resultwork.getJobNo());

        wih.modify(akey);
    }

    /**
     * 出庫作業のRFT作業実績情報を更新します。
     *
     * @param src 作業対象データ
     * @param resultwork 作業情報
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public void rftUpdateResultComplete(WorkInfo src, WorkInfo resultwork, WmsUserInfo ui)
            throws NotFoundException,
                ReadWriteException
    {

        AbstractDBHandler wih = new WorkInfoHandler(getConnection());
        AlterKey akey = new WorkInfoAlterKey();

        // update field setup
        FieldName[] updateFields = {
                WorkInfo.RESULT_QTY,
                WorkInfo.RESULT_AREA_NO,
                WorkInfo.RESULT_LOCATION_NO,
                WorkInfo.RESULT_LOT_NO,
                WorkInfo.WORK_DAY,
                WorkInfo.WORK_SECOND
        };

        akey = createAlterKey(resultwork, akey, updateFields);

        akey.setAdhocUpdateValue(WorkInfo.USER_ID, ui.getUserId());
        akey.setAdhocUpdateValue(WorkInfo.TERMINAL_NO, ui.getTerminalNo());
        akey.setAdhocUpdateValue(WorkInfo.LAST_UPDATE_PNAME, getCallerName());
        akey.setAdhocUpdateValue(WorkInfo.RFT_STATUS_FLAG, RETRIEVAL_JOB_STATUS_FLAG_COMPLETION);

        akey.setKey(WorkInfo.JOB_NO, src.getJobNo());

        wih.modify(akey);
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
        return "$Id: WorkInfoController.java 7996 2011-07-06 00:52:24Z kitamaki $";
    }
}

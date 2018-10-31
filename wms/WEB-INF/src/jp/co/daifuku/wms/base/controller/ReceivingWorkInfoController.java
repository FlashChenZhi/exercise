// $Id: ReceivingWorkInfoController.java 5028 2009-09-18 04:31:29Z kishimoto $
package jp.co.daifuku.wms.base.controller;

import static jp.co.daifuku.wms.base.common.Parameter.COMPLETION_FLAG_BOXCHANGE;
import static jp.co.daifuku.wms.base.common.Parameter.COMPLETION_FLAG_REMNANT;
import static jp.co.daifuku.wms.base.entity.SystemDefine.HARDWARE_TYPE_LIST;
import static jp.co.daifuku.wms.base.entity.SystemDefine.HARDWARE_TYPE_UNSTART;
import static jp.co.daifuku.wms.base.entity.SystemDefine.JOB_TYPE_RECEIVING;
import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_COMPLETION;
import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_DELETE;
import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_NOWWORKING;
import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_SHORTAGE_RESERVATION;
import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_UNSTART;
import static jp.co.daifuku.wms.base.exception.OperatorException.ERR_ALREADY_COMPLETED;
import static jp.co.daifuku.wms.base.exception.OperatorException.ERR_ALREADY_UPDATED;
import static jp.co.daifuku.wms.base.exception.OperatorException.ERR_WORKING_INPROGRESS;

import java.sql.Connection;

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
import jp.co.daifuku.wms.base.dbhandler.ReceivingHostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ReceivingHostSend;
import jp.co.daifuku.wms.base.entity.ReceivingPlan;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.AlterKey;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;
import jp.co.daifuku.wms.handler.db.SQLSearchKey;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;


/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 入荷作業情報を操作するためのコントローラクラスです。
 *
 * @version $Revision: 5028 $, $Date: 2009-09-18 13:31:29 +0900 (金, 18 9 2009) $
 * @author  Last commit: $Author: kishimoto $
 */
public class ReceivingWorkInfoController
        extends AbstractController
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    private WMSSequenceHandler _seqHandler = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、
     * 呼び出し元クラス(ロギング,更新プログラムの保存用に使用されます)を指定して
     * インスタンスを生成します。
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public ReceivingWorkInfoController(Connection conn, Class caller)
    {
        super(conn, caller);
        _seqHandler = new WMSSequenceHandler(getConnection());
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 作業情報完了処理を行います。<BR>
     * 該当入荷作業情報データの状態を完了に更新し、
     * ハードウェア区分、予定数、実績数、欠品数、実績ロットNo.、作業日、ユーザID、端末No.、作業秒数をセットします。<BR>
     * パラメータの作業No.より入荷作業情報、各マスタを検索して入荷実績送信情報を作成します。
     *
     * @param src 完了対象データ
     * @param result 完了情報
     * @param ui WMSユーザ情報
     * @throws ReadWriteException データベース処理でエラー発生
     * @throws NotFoundException 該当データなし
     * @throws DataExistsException 分割時に該当するデータが既に登録されていた
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在していた
     * @throws ScheduleException マスタ情報が検索できなかった
     */
    public void completeWorkInfo(ReceivingWorkInfo src, ReceivingWorkInfo result, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        // ::: 入荷作業情報の更新
        ReceivingWorkInfoHandler handler = new ReceivingWorkInfoHandler(getConnection());
        ReceivingWorkInfoAlterKey alterKey = new ReceivingWorkInfoAlterKey();

        // 更新内容
        alterKey.updateStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
        if (!StringUtil.isBlank(result.getSettingUnitKey()))
        {
            alterKey.updateSettingUnitKey(result.getSettingUnitKey());
        }
        if (!StringUtil.isBlank(result.getHardwareType()))
        {
            alterKey.updateHardwareType(result.getHardwareType());
        }
        alterKey.updatePlanQty(result.getPlanQty());
        alterKey.updateResultQty(result.getResultQty());
        if (result.getShortageQty() != 0)
        {
            alterKey.updateShortageQty(result.getShortageQty());
        }
        // 欠品予約データ更新時は実績ロットNo.を更新しない
        if (!STATUS_FLAG_SHORTAGE_RESERVATION.equals(src.getStatusFlag()))
        {
            alterKey.updateResultLotNo(result.getResultLotNo());
        }
        alterKey.updateWorkDay(result.getWorkDay());
        alterKey.updateUserId(ui.getUserId());
        alterKey.updateTerminalNo(ui.getTerminalNo());
        alterKey.updateWorkSecond(result.getWorkSecond());
        alterKey.updateLastUpdatePname(getCallerName());

        // 更新条件
        alterKey.setJobNo(src.getJobNo());

        // 更新
        handler.modify(alterKey);

        if ((result.getResultQty() != 0 && SystemDefine.HARDWARE_TYPE_RFT.equals(ui.getHardwareType()))
                || StringUtil.isBlank(ui.getHardwareType()))
        {
            // ::: 入荷実績送信情報の登録
            this.insertHostSendByWorkInfo(src.getJobNo());
        }
    }

    /**
     * 入荷実績送信情報登録処理を行います。<BR>
     * パラメータの作業No.より入荷作業情報を検索して入荷実績送信情報を作成します。<BR>
     * 各マスタ情報より必要項目を検索してセットします。
     * 
     * @param jobNo 作業No.
     * @throws ReadWriteException データベース処理でエラー発生
     * @throws NotFoundException 該当データなし
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在
     * @throws DataExistsException 既に登録済
     * @throws ScheduleException 作業Noから作業情報を検索できなかった場合
     */
    public void insertHostSendByWorkInfo(String jobNo)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        // ::: 入荷作業情報、及び各マスタ情報を結合して検索する（findPrimary）
        ReceivingWorkInfoHandler handler = new ReceivingWorkInfoHandler(getConnection());
        ReceivingWorkInfoSearchKey skey = new ReceivingWorkInfoSearchKey();

        // 取得項目
        skey.setWorkDayCollect();
        skey.setJobNoCollect();
        skey.setSettingUnitKeyCollect();
        skey.setCollectJobNoCollect();
        skey.setCrossDockUkeyCollect();
        skey.setJobTypeCollect();
        skey.setStatusFlagCollect();
        skey.setHardwareTypeCollect();
        skey.setPlanUkeyCollect();
        skey.setTcdcFlagCollect();
        skey.setPlanDayCollect();
        skey.setConsignorCodeCollect();
        skey.setCollect(Consignor.CONSIGNOR_NAME);
        skey.setSupplierCodeCollect();
        skey.setCollect(Supplier.SUPPLIER_NAME);
        skey.setReceiveTicketNoCollect();
        skey.setReceiveLineNoCollect();
        skey.setItemCodeCollect();
        skey.setCollect(Item.ITEM_NAME);
        skey.setCollect(Item.JAN);
        skey.setCollect(Item.ITF);
        skey.setCollect(Item.BUNDLE_ITF);
        skey.setCollect(Item.ENTERING_QTY);
        skey.setCollect(Item.BUNDLE_ENTERING_QTY);
        skey.setPlanLotNoCollect();
        skey.setPlanQtyCollect();
        skey.setResultQtyCollect();
        skey.setShortageQtyCollect();
        skey.setResultLotNoCollect();
        skey.setUserIdCollect();
        skey.setCollect(Com_loginuser.USERNAME);
        skey.setTerminalNoCollect();
        skey.setWorkSecondCollect();

        // 結合
        skey.setJoin(ReceivingWorkInfo.CONSIGNOR_CODE, "", Consignor.CONSIGNOR_CODE, "(+)");
        skey.setJoin(ReceivingWorkInfo.CONSIGNOR_CODE, "", Supplier.CONSIGNOR_CODE, "(+)");
        skey.setJoin(ReceivingWorkInfo.SUPPLIER_CODE, "", Supplier.SUPPLIER_CODE, "(+)");
        skey.setJoin(ReceivingWorkInfo.CONSIGNOR_CODE, "", Item.CONSIGNOR_CODE, "(+)");
        skey.setJoin(ReceivingWorkInfo.ITEM_CODE, "", Item.ITEM_CODE, "(+)");
        skey.setJoin(ReceivingWorkInfo.USER_ID, "", Com_loginuser.USERID, "(+)");

        // 条件
        skey.setJobNo(jobNo);

        // 入荷作業情報
        ReceivingWorkInfo workInfo = (ReceivingWorkInfo)handler.findPrimary(skey);

        // データが存在しなかったとき
        if (workInfo == null)
        {
            throw new ScheduleException();
        }


        // ::: 入荷実績送信情報を作成し、登録する
        ReceivingHostSend hostSend = new ReceivingHostSend();

        hostSend.setWorkDay(workInfo.getWorkDay());
        hostSend.setJobNo(workInfo.getJobNo());
        hostSend.setSettingUnitKey(workInfo.getSettingUnitKey());
        hostSend.setCollectJobNo(workInfo.getCollectJobNo());
        hostSend.setCrossDockUkey(workInfo.getCrossDockUkey());
        hostSend.setJobType(workInfo.getJobType());
        hostSend.setStatusFlag(workInfo.getStatusFlag());
        hostSend.setHardwareType(workInfo.getHardwareType());
        hostSend.setPlanUkey(workInfo.getPlanUkey());
        hostSend.setTcdcFlag(workInfo.getTcdcFlag());
        hostSend.setPlanDay(workInfo.getPlanDay());
        hostSend.setConsignorCode(workInfo.getConsignorCode());
        hostSend.setConsignorName(String.valueOf(workInfo.getValue(Consignor.CONSIGNOR_NAME, "")));
        hostSend.setSupplierCode(workInfo.getSupplierCode());
        hostSend.setSupplierName(String.valueOf(workInfo.getValue(Supplier.SUPPLIER_NAME, "")));
        hostSend.setReceiveTicketNo(workInfo.getReceiveTicketNo());
        hostSend.setReceiveLineNo(workInfo.getReceiveLineNo());
        hostSend.setItemCode(workInfo.getItemCode());
        hostSend.setItemName(String.valueOf(workInfo.getValue(Item.ITEM_NAME, "")));
        hostSend.setJan(String.valueOf(workInfo.getValue(Item.JAN, "")));
        hostSend.setItf(String.valueOf(workInfo.getValue(Item.ITF, "")));
        hostSend.setBundleItf(String.valueOf(workInfo.getValue(Item.BUNDLE_ITF, "")));
        hostSend.setEnteringQty(WmsFormatter.getInt(String.valueOf(workInfo.getValue(Item.ENTERING_QTY, 0))));
        hostSend.setBundleEnteringQty(WmsFormatter.getInt(String.valueOf(workInfo.getValue(Item.BUNDLE_ENTERING_QTY, 0))));
        hostSend.setPlanLotNo(workInfo.getPlanLotNo());
        hostSend.setPlanQty(workInfo.getPlanQty());
        hostSend.setResultQty(workInfo.getResultQty());
        hostSend.setShortageQty(workInfo.getShortageQty());
        hostSend.setResultLotNo(workInfo.getResultLotNo());
        hostSend.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
        hostSend.setUserId(workInfo.getUserId());
        hostSend.setUserName(String.valueOf(workInfo.getValue(Com_loginuser.USERNAME, "")));
        hostSend.setTerminalNo(workInfo.getTerminalNo());
        hostSend.setWorkSecond(workInfo.getWorkSecond());
        hostSend.setRegistPname(getCallerName());
        hostSend.setLastUpdatePname(getCallerName());

        // 登録
        new ReceivingHostSendHandler(getConnection()).create(hostSend);
    }

    /**
     * 予定情報の状態を取得します。
     * 
     * @param planUkey 予定一意キー
     * @return 状態フラグ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public String getReceivingPlanStatus(String planUkey)
            throws ReadWriteException,
                NotFoundException
    {
        ReceivingWorkInfoHandler wih = new ReceivingWorkInfoHandler(getConnection());
        ReceivingWorkInfoSearchKey key = new ReceivingWorkInfoSearchKey();

        key.setPlanUkey(planUkey);
        key.setStatusFlag(STATUS_FLAG_DELETE, "!=", "", "", true);
        key.setStatusFlagCollect("UNIQUE");

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
                return STATUS_FLAG_COMPLETION;
            }
        }
        return STATUS_FLAG_NOWWORKING;
    }


    /**
     * 入荷開始用に入荷作業情報の検索およびロックを行います。
     * 
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>荷主コード
     * <li>予定日
     * <li>商品コード
     * <li>仕入先コード
     * <li>予定ロットNo.
     * <li>TC/DCフラグ（0:DC,1:TC）
     * </ol>
     * @return 対象の入荷情報
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public ReceivingWorkInfo[] lockReceivingStart(ReceivingWorkInfo tgt)
            throws ReadWriteException,
                LockTimeOutException
    {
        AbstractDBHandler wih = new ReceivingWorkInfoHandler(getConnection());
        SQLSearchKey key = new ReceivingWorkInfoSearchKey();

        FieldName[] keyFields = {
            ReceivingWorkInfo.CONSIGNOR_CODE,
            ReceivingWorkInfo.PLAN_DAY,
            ReceivingWorkInfo.ITEM_CODE,
            ReceivingWorkInfo.SUPPLIER_CODE,
            ReceivingWorkInfo.PLAN_LOT_NO,
            ReceivingWorkInfo.TCDC_FLAG,
        };
        key = createKey(tgt, key, keyFields);

        key.setKey(ReceivingWorkInfo.STATUS_FLAG, STATUS_FLAG_UNSTART);
        key.setKey(ReceivingWorkInfo.JOB_TYPE, JOB_TYPE_RECEIVING);

        key.setOrder(ReceivingWorkInfo.PLAN_DAY, true);
        key.setOrder(ReceivingWorkInfo.PLAN_LOT_NO, true);
        key.setOrder(ReceivingWorkInfo.SUPPLIER_CODE, true);
        key.setOrder(ReceivingWorkInfo.PLAN_UKEY, true);

        ReceivingWorkInfo[] records;
        records = (ReceivingWorkInfo[])wih.findForUpdate(key);
        return records;
    }

    /**
     * 入荷開始用に入荷作業情報の検索およびロックを行います。
     * 
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>荷主コード
     * <li>予定日
     * <li>仕入先コード
     * <li>伝票No.
     * <li>伝票行No.
     * <li>TC/DCフラグ（0:DC,1:TC）
     * </ol>
     * @return 対象の入荷情報
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public ReceivingWorkInfo[] lockWebReceivingStart(ReceivingWorkInfo tgt)
            throws ReadWriteException,
                LockTimeOutException
    {
        AbstractDBHandler wih = new ReceivingWorkInfoHandler(getConnection());
        SQLSearchKey key = new ReceivingWorkInfoSearchKey();

        FieldName[] keyFields = {
            ReceivingWorkInfo.CONSIGNOR_CODE,
            ReceivingWorkInfo.PLAN_DAY,
            ReceivingWorkInfo.SUPPLIER_CODE,
            ReceivingWorkInfo.RECEIVE_TICKET_NO,
            ReceivingWorkInfo.RECEIVE_LINE_NO,
            ReceivingWorkInfo.TCDC_FLAG
        };
        key = createKey(tgt, key, keyFields);

        key.setKey(ReceivingWorkInfo.STATUS_FLAG, STATUS_FLAG_UNSTART);
        key.setKey(ReceivingWorkInfo.JOB_TYPE, JOB_TYPE_RECEIVING);

        return (ReceivingWorkInfo[])wih.findForUpdate(key);
    }

    /**
     * 入荷開始可能かどうかチェックします。
     * 
     * @param tgt 対象データキー項目
     * <ol>
     * 参照される項目は以下の通りです
     * <li>荷主コード
     * <li>予定日
     * <li>仕入先コード
     * <li>伝票No.
     * <li>伝票行No.
     * <li>予定ロットNo.
     * <li>TC/DCフラグ（0:DC,1:TC）
     * </ol>
     * 
     * @param items 対象商品コード一覧
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws OperatorException 開始可能でないとき、内容が返されます。
     */
    public void checkReceivingStart(ReceivingWorkInfo tgt, String... items)
            throws ReadWriteException,
                NotFoundException,
                OperatorException
    {
        ReceivingWorkInfoHandler wih = new ReceivingWorkInfoHandler(getConnection());
        ReceivingWorkInfoSearchKey key = new ReceivingWorkInfoSearchKey();

        FieldName[] keyFields = {
            ReceivingWorkInfo.CONSIGNOR_CODE,
            ReceivingWorkInfo.PLAN_DAY,
            ReceivingWorkInfo.SUPPLIER_CODE,
            ReceivingWorkInfo.RECEIVE_TICKET_NO,
            ReceivingWorkInfo.RECEIVE_LINE_NO,
            ReceivingWorkInfo.PLAN_LOT_NO,
            ReceivingWorkInfo.TCDC_FLAG
        };
        key = (ReceivingWorkInfoSearchKey)createKey(tgt, key, keyFields);

        if (!ArrayUtil.isEmpty(items))
        {
            key.setItemCode(items, true);
        }
        // fix status flag
        key.setKey(ReceivingWorkInfo.STATUS_FLAG, STATUS_FLAG_DELETE, "!=", "", "", true);
        key.setKey(ReceivingWorkInfo.JOB_TYPE, JOB_TYPE_RECEIVING);

        FieldName minStatusField = new FieldName("", "MIN_STATUS_");
        key.setCollect(ReceivingWorkInfo.STATUS_FLAG, "MIN", minStatusField);

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
     * 完了用に入荷作業情報,予定情報のロックを行います。
     * 
     * @param settingUnitKey 設定単位キー
     * @param jobType 作業区分
     * @return 作業情報 (予定一意キーのみセットされます)
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws InvalidDefineException 作業区分が 入荷ではないときスローされます。
     */
    public ReceivingWorkInfo[] lockReceivingComplete(String settingUnitKey, String jobType)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                InvalidDefineException
    {
        if (!JOB_TYPE_RECEIVING.equals(jobType))
        {
            throw new InvalidDefineException();
        }

        AbstractDBHandler wih = new ReceivingWorkInfoHandler(getConnection());
        ReceivingWorkInfoSearchKey key = new ReceivingWorkInfoSearchKey();

        key.setKey(ReceivingWorkInfo.SETTING_UNIT_KEY, settingUnitKey);
        key.setKey(ReceivingWorkInfo.STATUS_FLAG, STATUS_FLAG_NOWWORKING);

        key.setJoin(ReceivingWorkInfo.PLAN_UKEY, ReceivingPlan.PLAN_UKEY);

        key.setOrder(ReceivingWorkInfo.PLAN_UKEY, true);

        key.setCollect(ReceivingWorkInfo.PLAN_UKEY);

        ReceivingWorkInfo[] ents = (ReceivingWorkInfo[])wih.findForUpdate(key);
        if (ArrayUtil.isEmpty(ents))
        {
            throw new NotFoundException();
        }
        return ents;
    }

    /**
     * 入荷作業情報を作業中に更新します。
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
    public void startWorkInfo(ReceivingWorkInfo tgt, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException
    {
        AbstractDBHandler wih = new ReceivingWorkInfoHandler(getConnection());
        AlterKey akey = new ReceivingWorkInfoAlterKey();

        FieldName[] updateFields = {
            ReceivingWorkInfo.SETTING_UNIT_KEY,
            ReceivingWorkInfo.COLLECT_JOB_NO,
        };
        akey = createAlterKey(tgt, akey, updateFields);

        akey.setAdhocUpdateValue(ReceivingWorkInfo.STATUS_FLAG, STATUS_FLAG_NOWWORKING);
        akey.setAdhocUpdateValue(ReceivingWorkInfo.HARDWARE_TYPE, ui.getHardwareType());
        akey.setAdhocUpdateValue(ReceivingWorkInfo.USER_ID, ui.getUserId());
        akey.setAdhocUpdateValue(ReceivingWorkInfo.TERMINAL_NO, ui.getTerminalNo());
        akey.setAdhocUpdateValue(ReceivingWorkInfo.LAST_UPDATE_PNAME, getCallerName());
        akey.setKey(ReceivingWorkInfo.JOB_NO, tgt.getJobNo());

        wih.modify(akey);
    }

    /**
     * 入荷作業情報の完了処理を行います。
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
    public void completeWorkInfo(ReceivingWorkInfo src, ReceivingWorkInfo result, String compFlag, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        // 実績数+欠品数>0は完了
        if (0 < (result.getResultQty() + result.getShortageQty()))
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
     * 入荷作業情報をキャンセルします。
     * 
     * @param settingUnitKey 設定単位キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public void cancelWorkInfo(String settingUnitKey)
            throws ReadWriteException,
                NotFoundException
    {
        ReceivingWorkInfoHandler wih = new ReceivingWorkInfoHandler(getConnection());
        ReceivingWorkInfoAlterKey akey = createReceivingCancelKey();

        akey.setSettingUnitKey(settingUnitKey);
        akey.setStatusFlag(STATUS_FLAG_NOWWORKING);

        wih.modify(akey);
    }

    /**
     * 欠品予約の入荷作業情報をキャンセルします。<br>
     * 通常のキャンセルのほか、実績ロットNo.もクリアします。
     * 
     * @param jobNo 作業No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public void cancelShortageWorkInfo(String jobNo)
            throws ReadWriteException,
                NotFoundException
    {
        ReceivingWorkInfoHandler wih = new ReceivingWorkInfoHandler(getConnection());
        ReceivingWorkInfoAlterKey akey = createReceivingCancelKey();

        akey.updateResultLotNo("");

        akey.setJobNo(jobNo);
        akey.setStatusFlag(STATUS_FLAG_SHORTAGE_RESERVATION);

        wih.modify(akey);
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

    /**
     * 入荷作業情報の完了処理を行います。
     * 
     * @param src 完了対象データ
     * <ol>
     * 参照される項目は以下の通りです
     * <li>予定数
     * <li>作業No.
     * <li>ハードウェア区分
     * </ol>
     * 
     * @param resultwork 完了情報
     * <ol>
     * 参照される項目は以下の通りです
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
    protected void completeWorkInfoComplete(ReceivingWorkInfo src, ReceivingWorkInfo resultwork, String compFlag,
            WmsUserInfo ui)
            throws NotFoundException,
                ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        // work type check
        AbstractDBHandler wih = new ReceivingWorkInfoHandler(getConnection());
        AlterKey akey = new ReceivingWorkInfoAlterKey();

        boolean divided =
                (resultwork.getResultQty() < src.getPlanQty())
                        && (COMPLETION_FLAG_REMNANT.equals(compFlag) || COMPLETION_FLAG_BOXCHANGE.equals(compFlag));

        // update field setup
        FieldName[] updateFields = {
            ReceivingWorkInfo.RESULT_QTY,
            ReceivingWorkInfo.SHORTAGE_QTY,
            ReceivingWorkInfo.RESULT_LOT_NO,
            ReceivingWorkInfo.WORK_DAY,
            ReceivingWorkInfo.WORK_SECOND,
        };

        akey = createAlterKey(resultwork, akey, updateFields);

        akey.setAdhocUpdateValue(ReceivingWorkInfo.STATUS_FLAG, STATUS_FLAG_COMPLETION);

        // akey.setAdhocUpdateValue(WorkInfo.HARDWARE_TYPE, ui.getHardwareType());
        akey.setAdhocUpdateValue(ReceivingWorkInfo.USER_ID, ui.getUserId());
        akey.setAdhocUpdateValue(ReceivingWorkInfo.TERMINAL_NO, ui.getTerminalNo());
        akey.setAdhocUpdateValue(ReceivingWorkInfo.LAST_UPDATE_PNAME, getCallerName());

        if (divided)
        {
            akey.setAdhocUpdateValue(ReceivingWorkInfo.PLAN_QTY, resultwork.getValue(ReceivingWorkInfo.RESULT_QTY));
        }

        // set key field
        akey.setKey(ReceivingWorkInfo.JOB_NO, src.getJobNo());

        // update work info
        wih.modify(akey);

        // divided, create work info for divide
        if (divided)
        {
            ReceivingWorkInfo divworkinfo = (ReceivingWorkInfo)src.clone();
            // get next job number
            resultwork.getJobType(); // FIXME JobType どこかにセットする?
            String jobNo = _seqHandler.nextReceivingJobNo();

            divworkinfo.setJobNo(jobNo);
            divworkinfo.setPlanQty(src.getPlanQty() - resultwork.getResultQty());
            divworkinfo.setResultQty(0);
            divworkinfo.setShortageQty(0);
            divworkinfo.setRegistPname(getCallerName());
            divworkinfo.setRegistDate(new SysDate());
            divworkinfo.setLastUpdatePname(getCallerName());

            // additional (divide and not LIST)
            if (COMPLETION_FLAG_REMNANT.equals(compFlag))
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
        ReceivingHostSendController hsc = new ReceivingHostSendController(getConnection(), getCaller());
        boolean isHostsend = hsc.checkHostSendType(src.getConsignorCode(), src.getItemCode());
        if (isHostsend)
        {
            hsc.insertByReceivingWorkInfo(src.getJobNo(), ui);
        }
    }

    /**
     * 入荷作業情報の完了処理(キャンセル)を行います。<br>
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
    protected void completeWorkInfoCancel(ReceivingWorkInfo src, String compFlag, WmsUserInfo ui)
            throws NotFoundException,
                ReadWriteException
    {
        boolean div = COMPLETION_FLAG_REMNANT.equals(compFlag) && HARDWARE_TYPE_LIST.equals(ui.getHardwareType());
        boolean boxc = COMPLETION_FLAG_BOXCHANGE.equals(compFlag);
        if (div || boxc)
        {
            return;
        }

        AbstractDBHandler wih = new ReceivingWorkInfoHandler(getConnection());
        ReceivingWorkInfoAlterKey akey = createReceivingCancelKey();

        akey.setJobNo(src.getJobNo());

        wih.modify(akey);
    }

    /**
     * キャンセル用の更新キーを生成して返します。
     * 
     * @return 更新内容がセットされた更新キー
     */
    protected ReceivingWorkInfoAlterKey createReceivingCancelKey()
    {
        ReceivingWorkInfoAlterKey akey = new ReceivingWorkInfoAlterKey();

        akey.updateSettingUnitKey("");
        akey.updateCollectJobNo("");
        akey.updateStatusFlag(STATUS_FLAG_UNSTART);
        akey.updateHardwareType(HARDWARE_TYPE_UNSTART);
        akey.updateUserId("");
        akey.updateTerminalNo("");
        akey.updateLastUpdatePname(getCallerName());

        return akey;
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------


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
        return "$Id: ReceivingWorkInfoController.java 5028 2009-09-18 04:31:29Z kishimoto $";
    }
}

// $Id: PCTRetWorkController.java 3755 2009-03-23 09:37:53Z ose $
package jp.co.daifuku.pcart.retrieval.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.controller.AbstractController;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.PCTOrderInfo;
import jp.co.daifuku.wms.base.entity.PCTRetHostSend;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;


/**
 * PCT出庫作業情報を操作するためのコントローラクラスです。<br>
 *
 *
 * @version $Revision: 3755 $, $Date: 2009-03-23 18:37:53 +0900 (月, 23 3 2009) $
 * @author  etakeda
 * @author  Last commit: $Author: ose $
 */
public class PCTRetWorkController
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

    private WMSSequenceHandler _seqHandler;

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
    public PCTRetWorkController(Connection conn, Class caller)
    {
        super(conn, caller);
        _seqHandler = new WMSSequenceHandler(getConnection());
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 完了用にPCT出庫作業情報、PCT出庫予定情報のロックを行います。
     * 完了区分が作業メンテナンス（オーダー単位、商品単位）の場合はPCT出庫実績送信情報、
     * 作業メンテナンス一括の場合はPCTオーダー情報もロックします。
     * 
     * @param inputParam 入力パラメータ
     * @return PCT出庫作業データ
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws InvalidDefineException 作業区分が 入庫または出庫ではないときスローされます。
     */
    public PCTRetWorkInfo[] lockComplete(PCTRetrievalInParameter inputParam)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                InvalidDefineException
    {
        PCTRetWorkInfoFinder wif = new PCTRetWorkInfoFinder(getConnection());
        PCTRetWorkInfoHandler wih = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfoSearchKey key = new PCTRetWorkInfoSearchKey();

        // 検索条件
        // 検索条件のセット
        // 荷主コード
        key.setConsignorCode(inputParam.getConsignorCode());
        // 予定日
        if (!StringUtil.isBlank(inputParam.getPlanDay()))
        {
            key.setPlanDay(inputParam.getPlanDay());
        }
        // バッチNo.
        if (!StringUtil.isBlank(inputParam.getBatchNo()))
        {
            key.setBatchNo(inputParam.getBatchNo());
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(inputParam.getBatchSeqNo()))
        {
            key.setBatchSeqNo(inputParam.getBatchSeqNo());
        }
        // エリア
        if (!StringUtil.isBlank(inputParam.getPlanAreaNo()))
        {
            key.setPlanAreaNo(inputParam.getPlanAreaNo());
        }
        // 得意先コード
        if (!StringUtil.isBlank(inputParam.getRegularCustomerCode()))
        {
            key.setRegularCustomerCode(inputParam.getRegularCustomerCode());
        }
        // 出荷先コード
        if (!StringUtil.isBlank(inputParam.getCustomerCode()))
        {
            key.setCustomerCode(inputParam.getCustomerCode());
        }
        // オーダーNo.
        if (!StringUtil.isBlank(inputParam.getResultOrderNo()))
        {
            key.setResultOrderNo(inputParam.getResultOrderNo());
        }
        // 最終更新日時
        if (!StringUtil.isBlank(inputParam.getLastUpdateDate()))
        {
            key.setLastUpdateDate(inputParam.getLastUpdateDate());
        }
        // 処理フラグが削除以外の場合
        if (!PCTRetrievalInParameter.PROCESSING_DIVISION_DELETE.equals(inputParam.getProcessingDivision()))
        {
            // 作業メンテナンス（オーダー単位、商品単位）
            if (PCTRetrievalInParameter.WORK_FLAG_MNT.equals(inputParam.getWorkflag()))
            {
                // 作業No.
                if (!StringUtil.isBlank(inputParam.getJobNo()))
                {
                    key.setJobNo(inputParam.getJobNo());
                }
                // 作業フラグ
                key.setStatusFlag(new String[] {
                        PCTRetrievalInParameter.STATUS_FLAG_COMPLETION,
                        PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION
                }, true);
                // 報告区分
                // PCT出庫作業実績送信情報.実績報告区分
                key.setKey(PCTRetHostSend.REPORT_FLAG, SystemDefine.REPORT_FLAG_NOT_REPORT);
                key.setJoin(PCTRetWorkInfo.JOB_NO, "", PCTRetHostSend.JOB_NO, "");
            }
            else if (PCTRetrievalInParameter.WORK_FLAG_MNT_COLLECT.equals(inputParam.getWorkflag()))
            {
                // 作業No.
                String[] jobNo = new String[inputParam.getJobNoList().size()];
                for (int i = 0; i < inputParam.getJobNoList().size(); i++)
                {
                    jobNo[i] = inputParam.getJobNoList().get(i);
                }
                key.setJobNo(jobNo, true);
                // 作業フラグ
                key.setStatusFlag(new String[] {
                        PCTRetrievalInParameter.STATUS_FLAG_COMPLETION,
                        PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION
                }, true);
                // 報告区分
                // PCT出庫作業実績送信情報.実績報告区分
                key.setKey(PCTRetHostSend.REPORT_FLAG, SystemDefine.REPORT_FLAG_NOT_REPORT);
                key.setJoin(PCTRetWorkInfo.JOB_NO, "", PCTRetHostSend.JOB_NO, "");
            }
            // 作業メンテナンス一括
            else
            {
                key.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_UNWORK);
                // 結合条件
                key.setJoin(PCTRetWorkInfo.RESULT_ORDER_NO, PCTOrderInfo.RESULT_ORDER_NO);
            }
        }

        // 結合条件
        key.setJoin(PCTRetWorkInfo.PLAN_UKEY, PCTRetPlan.PLAN_UKEY);
        // 取得するフィールド
        key.setCollect(PCTRetWorkInfo.CONSIGNOR_CODE);
        key.setCollect(PCTRetWorkInfo.PLAN_DAY);
        key.setCollect(PCTRetWorkInfo.BATCH_NO);
        key.setCollect(PCTRetWorkInfo.BATCH_SEQ_NO);
        key.setCollect(PCTRetWorkInfo.PLAN_AREA_NO);
        key.setCollect(PCTRetWorkInfo.REGULAR_CUSTOMER_CODE);
        key.setCollect(PCTRetWorkInfo.CUSTOMER_CODE);
        key.setCollect(PCTRetWorkInfo.RESULT_ORDER_NO);
        // 昇順
        key.setOrder(PCTRetWorkInfo.CONSIGNOR_CODE, true);
        key.setOrder(PCTRetWorkInfo.PLAN_DAY, true);
        key.setOrder(PCTRetWorkInfo.BATCH_NO, true);
        key.setOrder(PCTRetWorkInfo.BATCH_SEQ_NO, true);
        key.setOrder(PCTRetWorkInfo.PLAN_AREA_NO, true);
        key.setOrder(PCTRetWorkInfo.REGULAR_CUSTOMER_CODE, true);
        key.setOrder(PCTRetWorkInfo.CUSTOMER_CODE, true);
        key.setOrder(PCTRetWorkInfo.RESULT_ORDER_NO, true);

        wif.open(true);
        int count = wif.searchForUpdate(key, HandlerSysDefines.WAIT_SEC_NOWAIT);
        wif.close();
        if (count <= 0)
        {
            throw new NotFoundException();
        }

        // 作業情報取得結果
        // 検索条件にグループ化追加
        // グループ化
        key.setGroup(PCTRetWorkInfo.CONSIGNOR_CODE);
        key.setGroup(PCTRetWorkInfo.PLAN_DAY);
        key.setGroup(PCTRetWorkInfo.BATCH_NO);
        key.setGroup(PCTRetWorkInfo.BATCH_SEQ_NO);
        key.setGroup(PCTRetWorkInfo.PLAN_AREA_NO);
        key.setGroup(PCTRetWorkInfo.REGULAR_CUSTOMER_CODE);
        key.setGroup(PCTRetWorkInfo.CUSTOMER_CODE);
        key.setGroup(PCTRetWorkInfo.RESULT_ORDER_NO);

        PCTRetWorkInfo[] workInfoEntity = null;
        workInfoEntity = (PCTRetWorkInfo[])wih.find(key);

        return workInfoEntity;
    }

    /**
     * 作業情報の完了処理を行います。
     * 
     * @param param 入力パラメータ

     * @param src 完了対象データ
     * <ol>
     * 参照される項目は以下の通りです
     * <li>予定数
     * <li>作業No.
     * <li>ハードウエア区分
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
     * @param statusFlag 状態フラグ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws DataExistsException 分割時に該当のデータがすでに登録されていたときスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在したときスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった
     */
    public void completePCTRetWorkInfo(PCTRetrievalInParameter param, PCTRetWorkInfo src, String statusFlag,
            PCTRetWorkInfo result)
            throws ReadWriteException,
                NotFoundException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        // 出庫実績送信情報コントローラーハンドラを定義する
        PCTRetHostSendController hsc = new PCTRetHostSendController(getConnection(), getCaller());
        // PCT出庫作業情報を更新する
        updatePCTRetWorkInfo(src, result, statusFlag);
        // PCT出庫実績送信情報を更新する
        hsc.updatePCTRetHostsend(param, src, result, statusFlag);
    }

    /**
     * PCT出庫作業情報の更新処理を行います。 
     * 作業メンテナンス（オーダー単位、商品単位）
     * 
     * @param src 完了対象データ
     * <ol>
     * 参照される項目は以下の通りです
     * <li>作業No.
     * <li>作業日
     * </ol>
     * 
     * @param result 完了情報
     * <ol>
     * 参照される項目は以下の通りです
     * <li>予定数
     * <li>実績数
     * </ol>
     * 
     * @param statusFlag 状態フラグ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws DataExistsException 分割時に該当のデータがすでに登録されていたときスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在したときスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった
     */
    protected void updatePCTRetWorkInfo(PCTRetWorkInfo src, PCTRetWorkInfo resultwork, String statusFlag)
            throws NotFoundException,
                ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        AbstractDBHandler wih = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfoAlterKey akey = new PCTRetWorkInfoAlterKey();
        int resultQty = resultwork.getResultQty();

        // 状態フラグ
        akey.updateStatusFlag(statusFlag);
        // 実績数
        akey.updateResultQty(resultQty);
        // 欠品数
        akey.updateShortageQty(resultwork.getPlanQty() - resultQty);
        // 作業日
        akey.updateWorkDay(src.getWorkDay());
        // 最終更新処理名
        akey.updateLastUpdatePname(getCallerName());

        // 検索条件
        akey.setKey(PCTRetWorkInfo.JOB_NO, src.getJobNo());

        // 更新
        wih.modify(akey);
    }

    /**
     * PCT出庫作業情報の完了処理を行います。
     * 作業メンテナンス一括
     * 
     * @param src 対象作業情報
     * @param inputParam 入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws DataExistsException 分割時に該当のデータがすでに登録されていたときスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在したときスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった
     */
    public void updatePCTRetWorkInfoAll(PCTRetWorkInfo work, PCTRetrievalInParameter inputParam)
            throws NotFoundException,
                ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        PCTRetWorkInfoHandler wih = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfoAlterKey akey = new PCTRetWorkInfoAlterKey();

        // 処理フラグが全欠品完了
        if (PCTRetrievalInParameter.PROCESSING_DIVISION_STOCKOUT.equals(inputParam.getProcessingDivision()))
        {
            // 欠品数：PCT出庫作業情報.予定数
            akey.setAdhocUpdateValue(PCTRetWorkInfo.SHORTAGE_QTY, PCTRetWorkInfo.PLAN_QTY);
        }
        // 処理フラグが全完了
        else if (PCTRetrievalInParameter.PROCESSING_DIVISION_COMPLETION.equals(inputParam.getProcessingDivision()))
        {
            // 実績数：PCT出庫作業情報.予定数
            akey.setAdhocUpdateValue(PCTRetWorkInfo.RESULT_QTY, PCTRetWorkInfo.PLAN_QTY);
        }
        // 状態フラグ
        akey.updateStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION);
        // 端末No.
        akey.updateTerminalNo(work.getTerminalNo());
        // ユーザID
        akey.updateUserId(work.getUserId());
        // 作業日
        akey.updateWorkDay(work.getWorkDay());
        // 作業秒数
        akey.updateWorkSecond(0);
        // 最終更新処理名
        akey.updateLastUpdatePname(getCallerName());

        /* 削除条件
         * 
         *  荷主コード   =  パラメータ作業情報の荷主コード
         *  予定日       =  パラメータ作業情報の予定日
         *  バッチNo.    =  パラメータ作業情報のバッチNo.
         *  バッチSeqNo. =  パラメータ作業情報のバッチSeqNo.
         *  エリア       =  パラメータ作業情報のエリア
         *  得意先コード =  パラメータ作業情報の得意先コード
         *  出荷先コード =  パラメータ作業情報の出荷先コード
         *  オーダーNo.  =  パラメータ作業情報のオーダーNo.
         *  状態フラグ   =  未作業('0')
         *  
         */
        // 削除条件のセット
        // 荷主コード
        akey.setConsignorCode(work.getConsignorCode());
        // 予定日
        if (!StringUtil.isBlank(work.getPlanDay()))
        {
            akey.setPlanDay(work.getPlanDay());
        }
        // バッチNo.
        if (!StringUtil.isBlank(work.getBatchNo()))
        {
            akey.setBatchNo(work.getBatchNo());
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(work.getBatchSeqNo()))
        {
            akey.setBatchSeqNo(work.getBatchSeqNo());
        }
        // エリア
        if (!StringUtil.isBlank(work.getPlanAreaNo()))
        {
            akey.setPlanAreaNo(work.getPlanAreaNo());
        }
        // 得意先コード
        if (!StringUtil.isBlank(work.getRegularCustomerCode()))
        {
            akey.setRegularCustomerCode(work.getRegularCustomerCode());
        }
        // 出荷先コード
        if (!StringUtil.isBlank(work.getCustomerCode()))
        {
            akey.setCustomerCode(work.getCustomerCode());
        }
        // オーダーNo.
        if (!StringUtil.isBlank(work.getResultOrderNo()))
        {
            akey.setResultOrderNo(work.getResultOrderNo());
        }
        // 状態フラグ
        akey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_UNWORK);

        // 更新
        wih.modify(akey);
    }

    /**
     * PCT出庫作業情報の取得処理を行います。
     * 作業メンテナンス一括
     * 
     * @param src 対象作業情報
     * @return PCTRetWorkInfo[] 作業情報検索結果を返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws DataExistsException 分割時に該当のデータがすでに登録されていたときスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在したときスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった
     */
    public PCTRetWorkInfo[] getWorkInfo(PCTRetWorkInfo src)
            throws NotFoundException,
                ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        // PCT出庫作業情報ハンドラ
        PCTRetWorkInfoHandler wih = new PCTRetWorkInfoHandler(getConnection());
        // PCT出庫作業情報検索キー
        PCTRetWorkInfoSearchKey key = new PCTRetWorkInfoSearchKey();

        // 荷主コード
        key.setConsignorCode(src.getConsignorCode());
        // 予定日
        if (!StringUtil.isBlank(src.getPlanDay()))
        {
            key.setPlanDay(src.getPlanDay());
        }
        // バッチNo.
        if (!StringUtil.isBlank(src.getBatchNo()))
        {
            key.setBatchNo(src.getBatchNo());
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(src.getBatchSeqNo()))
        {
            key.setBatchSeqNo(src.getBatchSeqNo());
        }
        // エリア
        if (!StringUtil.isBlank(src.getPlanAreaNo()))
        {
            key.setPlanAreaNo(src.getPlanAreaNo());
        }
        // 得意先コード
        if (!StringUtil.isBlank(src.getRegularCustomerCode()))
        {
            key.setRegularCustomerCode(src.getRegularCustomerCode());
        }
        // 出荷先コード
        if (!StringUtil.isBlank(src.getCustomerCode()))
        {
            key.setCustomerCode(src.getCustomerCode());
        }
        // オーダーNo.
        if (!StringUtil.isBlank(src.getResultOrderNo()))
        {
            key.setResultOrderNo(src.getResultOrderNo());
        }
        // 状態フラグ
        key.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_UNWORK);

        PCTRetWorkInfo[] workInfoEntity = null;
        workInfoEntity = (PCTRetWorkInfo[])wih.find(key);

        return workInfoEntity;
    }

    /**
     * PCT出庫作業情報からPCT出庫作業情報を新規作成します。<br>
     * 
     * @param inputParam 入力パラメータ
     * @param src 作業情報
     * @param result 実績情報
     * @param statusFlag 完了フラグ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws NoPrimaryException 該当するデータが複数存在するときスローされます。
     * @throws DataExistsException すでに登録済みであったときスローされます。
     * @throws ScheduleException 作業情報からマスタを検索できなかったときスローされます。
     */
    @SuppressWarnings("unused")
    public void insertPCTRetWorkInfo(Parameter inputParam, PCTRetWorkInfo src, PCTRetWorkInfo result, String statusFlag)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        PCTRetWorkInfoHandler wih = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfo workInfoEntity = new PCTRetWorkInfo();

        // PCTRetrievalInParameterにキャスト
        PCTRetrievalInParameter inParam = (PCTRetrievalInParameter)inputParam;

        // PCT出庫作業情報(作業No)の採番
        String pctJobNo = _seqHandler.nextPCTRetWorkInfoJobNo();
        // 実績オーダーNo、オーダーSeqNoの採番
        String[] returnData = new String[2];
        PCTOrderNoController orderContoroller = new PCTOrderNoController(getConnection());
        returnData = orderContoroller.makeResultOrderNo(getConnection(), src.getOrderNo());

        // 登録する値を設定
        // 作業日
        workInfoEntity.setWorkDay(src.getWorkDay());
        // 作業No.
        workInfoEntity.setJobNo(pctJobNo);
        // 設定単位キー
        workInfoEntity.setSettingUnitKey(src.getSettingUnitKey());
        // 集約作業No
        workInfoEntity.setCollectJobNo(src.getCollectJobNo());
        // 状態フラグ
        workInfoEntity.setStatusFlag(statusFlag);
        // ハードウェア区分
        workInfoEntity.setHardwareType(src.getHardwareType());
        // 予定一意キー
        workInfoEntity.setPlanUkey(src.getPlanUkey());
        // 在庫ID
        workInfoEntity.setStockId(src.getStockId());
        // システム接続キー
        workInfoEntity.setSystemConnKey(src.getSystemConnKey());
        // 予定日
        workInfoEntity.setPlanDay(src.getPlanDay());
        // 荷主コード
        workInfoEntity.setConsignorCode(src.getConsignorCode());
        // 得意先コード
        workInfoEntity.setRegularCustomerCode(src.getRegularCustomerCode());
        // 出荷先コード
        workInfoEntity.setCustomerCode(src.getCustomerCode());
        // 出荷先分類コード
        workInfoEntity.setCustomerCategory(src.getCustomerCategory());
        // 出荷伝票No.
        workInfoEntity.setShipTicketNo(src.getShipTicketNo());
        // 出荷伝票行
        workInfoEntity.setShipLineNo(src.getShipLineNo());
        // 出荷伝票作業連番
        workInfoEntity.setShipBranchNo(src.getShipBranchNo());
        // バッチNo.
        workInfoEntity.setBatchNo(src.getBatchNo());
        // バッチSeqNo.
        workInfoEntity.setBatchSeqNo(src.getBatchSeqNo());
        // オーダーNo.
        workInfoEntity.setOrderNo(src.getOrderNo());
        // オーダーSeqNo.
        workInfoEntity.setOrderSeq(returnData[1]);
        // オーダー情報コメント
        workInfoEntity.setOrderInfo(src.getOrderInfo());
        // 予定オーダーNo.
        workInfoEntity.setPlanOrderNo(src.getPlanOrderNo());
        // 実績オーダーNo.
        workInfoEntity.setResultOrderNo(returnData[0]);
        // 予定エリア
        workInfoEntity.setPlanAreaNo(src.getPlanAreaNo());
        // 予定ゾーン
        workInfoEntity.setPlanZoneNo(src.getPlanZoneNo());
        // 作業ゾーン
        workInfoEntity.setWorkZoneNo(src.getWorkZoneNo());
        // 予定棚
        workInfoEntity.setPlanLocationNo(src.getPlanLocationNo());
        // 商品コード
        workInfoEntity.setItemCode(src.getItemCode());
        // 基準日付
        workInfoEntity.setUseByDate(src.getUseByDate());
        // アイテム情報コメント
        workInfoEntity.setItemInfo(src.getItemInfo());
        // 予定ロットNo.
        workInfoEntity.setPlanLotNo(src.getPlanLotNo());
        // 予定数(PCT出庫作業情報.予定数 - PCT出庫作業情報.実績数)
        workInfoEntity.setPlanQty(src.getPlanQty() - src.getResultQty());
        // 実績数(入力された実績数 - PCT出庫作業情報.実績数)
        workInfoEntity.setResultQty(result.getResultQty() - src.getResultQty());
        // 欠品数(PCT出庫作業情報.予定数 - 入力された実績数)
        workInfoEntity.setShortageQty(workInfoEntity.getPlanQty() - workInfoEntity.getResultQty());

        // ユーザID
        workInfoEntity.setUserId(src.getUserId());
        // 端末No、RFTNo
        workInfoEntity.setTerminalNo(src.getTerminalNo());
        // 作業秒数
        workInfoEntity.setWorkSecond(src.getWorkSecond());
        // 登録処理名
        workInfoEntity.setRegistPname(getCallerName());
        // 最終更新処理名
        workInfoEntity.setLastUpdatePname(getCallerName());
        // 箱替フラグにtrue設定
        inParam.setSubstitutes(true);

        // 登録
        wih.create(workInfoEntity);
    }

    /**
     * PCT出庫作業情報の削除処理を行います。
     * 作業メンテナンス一括
     * 
     * @param src 削除対象データ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 分割時に該当のデータがすでに登録されていたときスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在したときスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった
     */
    public void deletePCTRetWorkInfoAll(PCTRetWorkInfo src)
            throws ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        PCTRetWorkInfoHandler winfoHandler = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfoSearchKey winfoSKey = new PCTRetWorkInfoSearchKey();

        try
        {
            /* 削除条件
             * 
             *  荷主コード   =  パラメータ作業情報の荷主コード
             *  予定日       =  パラメータ作業情報の予定日
             *  バッチNo.    =  パラメータ作業情報のバッチNo.
             *  バッチSeqNo. =  パラメータ作業情報のバッチSeqNo.
             *  エリア       =  パラメータ作業情報のエリア
             *  得意先コード =  パラメータ作業情報の得意先コード
             *  出荷先コード =  パラメータ作業情報の出荷先コード
             *  オーダーNo.  =  パラメータ作業情報のオーダーNo.
             *  
             */
            // 削除条件のセット
            // 荷主コード
            winfoSKey.setConsignorCode(src.getConsignorCode());
            // 予定日
            if (!StringUtil.isBlank(src.getPlanDay()))
            {
                winfoSKey.setPlanDay(src.getPlanDay());
            }
            // バッチNo.
            if (!StringUtil.isBlank(src.getBatchNo()))
            {
                winfoSKey.setBatchNo(src.getBatchNo());
            }
            // バッチSeqNo.
            if (!StringUtil.isBlank(src.getBatchSeqNo()))
            {
                winfoSKey.setBatchSeqNo(src.getBatchSeqNo());
            }
            // エリア
            if (!StringUtil.isBlank(src.getPlanAreaNo()))
            {
                winfoSKey.setPlanAreaNo(src.getPlanAreaNo());
            }
            // 得意先コード
            if (!StringUtil.isBlank(src.getRegularCustomerCode()))
            {
                winfoSKey.setRegularCustomerCode(src.getRegularCustomerCode());
            }
            // 出荷先コード
            if (!StringUtil.isBlank(src.getCustomerCode()))
            {
                winfoSKey.setCustomerCode(src.getCustomerCode());
            }
            // オーダーNo.
            if (!StringUtil.isBlank(src.getResultOrderNo()))
            {
                winfoSKey.setResultOrderNo(src.getResultOrderNo());
            }

            // 削除
            winfoHandler.drop(winfoSKey);
        }
        catch (NotFoundException e)
        {
            // 対象オーダー情報が存在しなくても削除処理なのでエラーにしない
        }
    }

    /**
     * PCT出庫作業情報の削除処理を行います。<br>
     * (作業メンテナンス一括)<br>
     * 
     * @param plan PCT出庫予定情報
     * @return boolena 削除フラグを返す
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 分割時に該当のデータがすでに登録されていたときスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在したときスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった
     */
    public boolean deleteWorkInfoWorkMntAll(PCTRetPlan plan)
            throws ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        PCTRetWorkInfoHandler winfoHandler = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfoSearchKey winfoSKey = new PCTRetWorkInfoSearchKey();

        try
        {
            // PCT出庫予定情報.予定一意キー
            winfoSKey.setPlanUkey(plan.getPlanUkey());

            // 削除
            winfoHandler.drop(winfoSKey);

            return true;
        }
        catch (NotFoundException e)
        {
            // 対象オーダー情報が存在しなくても削除処理なのでエラーにしない
            return false;
        }
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
        return "$Id: PCTRetWorkController.java 3755 2009-03-23 09:37:53Z ose $";
    }
}

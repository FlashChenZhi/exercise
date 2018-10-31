// $Id: RetrievalOperator.java 6358 2009-12-04 00:44:01Z kishimoto $

package jp.co.daifuku.wms.retrieval.operator;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of
 * DAIFUKU Co.,Ltd. Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.entity.SystemDefine.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.common.OutParameter;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.HostSendController;
import jp.co.daifuku.wms.base.controller.RetrievalPlanController;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.controller.WorkInfoController;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkListHandler;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Reason;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.entity.WorkList;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.retrieval.allocate.AbstractAllocateOperator;
import jp.co.daifuku.wms.retrieval.allocate.ShortageOperator;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalInParameter;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOutParameter;

/**
 * 出庫作業のためのオペレータクラスです。
 * 
 * @version $Revision: 6358 $, $Date: 2007/03/26
 *          04:18:01 $
 * @author 073019
 * @author Last commit: $Author: kishimoto $
 */
// UPDATE_SS (2007-07-06)
public class RetrievalOperator
        extends AbstractOperator
{
    // ------------------------------------------------------------
    // fields (upper case only)
    // ------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    // ------------------------------------------------------------
    // class variables (prefix '$')
    // ------------------------------------------------------------
    // private static String $classVar ;

    // ------------------------------------------------------------
    // instance variables (prefix '_')
    // ------------------------------------------------------------
    // private String _instanceVar ;

    // ------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------
    /**
     * データベースコネクションと呼び出し元クラスを指定して インスタンスを生成します。
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public RetrievalOperator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    // ------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------
    /**
     * WEB画面出庫作業開始処理を行います。
     * 
     * パラメータの項目に該当するデータに対して開始処理を行い、設定単位キーを返します。<br>
     * 
     * @param inParam 出庫入力パラメータ
     * @return 出力パラメータ(設定単位キー)
     * @throws ReadWriteException
     *         データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException
     *         でにレコードがロックされていた場合にスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws OperatorException 設定不可の場合に詳細が返されます。
     * @throws ScheduleException 作業情報、在庫情報を検索できなかったときスローされます。
     * @throws DataExistsException 
     */
    public OutParameter webStart(RetrievalInParameter inParam)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                OperatorException,
                ScheduleException,
                DataExistsException
    {
        // 入出庫作業情報コントローラ
        WorkInfoController wkInfoCtrl = new WorkInfoController(getConnection(), getCaller());

        WorkInfo lockParam = new WorkInfo();
        lockParam.setConsignorCode(inParam.getConsignorCode()); // 荷主コード
        lockParam.setBatchNo(inParam.getBatchNo()); // バッチNo.
        lockParam.setPlanAreaNo(inParam.getRetrievalAreaNo()); // エリア
        lockParam.setOrderNo(inParam.getOrderNo()); // オーダーNo.

        String[] orders = {
            inParam.getOrderNo(),
        };
        // 出庫開始ロック
        WorkInfo[] workInfos = wkInfoCtrl.lockRetrievalStart(lockParam, orders);
        boolean recordsFound = !ArrayUtil.isEmpty(workInfos);

        // ロック成功
        if (recordsFound)
        {
            // ハードウェア区分の設定
            WmsUserInfo ui = inParam.getWmsUserInfo();
            ui.setHardwareType(SystemDefine.HARDWARE_TYPE_LIST);
            // 出庫開始
            OutParameter outParam = start(WmsParam.MAX_RFT_JOBCOLLECT_UNLIMITED, workInfos, ui);

            return outParam;
        }
        // ロック失敗
        else
        {
            try
            {
                // 出庫開始状態チェック
                wkInfoCtrl.checkRetrievalStart(lockParam);
            }
            catch (OperatorException e)
            {
                // 未開始データ存在エラーなら
                if (e.getErrorCode() == OperatorException.ERR_NOT_START_EXISTS)
                {
                    // 他端末で更新済みエラー
                    throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                }
                throw e;
            }
            // this will not reached.
            return null;
        }
    }

    /**
     * RFT出庫作業開始を行います。<br>
     * 
     * パラメータの項目に該当するデータに対して開始処理を行い、設定単位キーを返します。<br>
     * 入出庫作業情報が存在しないオーダーNo.が1件でも存在する場合は、NotFoundExceptionを通知します。
     * <br>
     * 他端末作業中、完了済などのチェックは行いません。
     * 
     * @param inParams 出庫入力パラメータ
     * @return 出力パラメータ(設定単位キー)
     * @throws ReadWriteException
     *         データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException
     *         でにレコードがロックされていた場合にスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws OperatorException 設定不可の場合に詳細が返されます。
     * @throws ScheduleException 作業情報、在庫情報を検索できなかったときスローされます。
     * @throws DataExistsException 
     */
    public OutParameter rftStart(RetrievalInParameter[] inParams)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                OperatorException,
                ScheduleException,
                DataExistsException
    {
        List<String> orderList = new ArrayList<String>();

        for (RetrievalInParameter inParam : inParams)
        {
            if (!StringUtil.isBlank(inParam.getOrderNo()))
            {
                orderList.add(inParam.getOrderNo());
            }
        }
        WorkInfoController wkInfoCtrl = new WorkInfoController(getConnection(), getCaller());

        WorkInfo lockParam = new WorkInfo();
        RetrievalInParameter firstParam = inParams[0];

        lockParam.setConsignorCode(firstParam.getConsignorCode()); // 荷主コード
        lockParam.setBatchNo(firstParam.getBatchNo()); // バッチNo.
        lockParam.setPlanAreaNo(firstParam.getRetrievalAreaNo()); // エリア
        lockParam.setOrderNo(firstParam.getOrderNo()); // オーダーNo.

        WorkInfo[] workInfos = wkInfoCtrl.lockRetrievalStart(lockParam, orderList.toArray(new String[0]));

        // オーダーNo.チェック
        checkOrderNo(workInfos, orderList.toArray(new String[0]));

        // オーダー連番を更新
        for (int i = 0; i < orderList.size(); i++)
        {
            WorkInfo alterParam = new WorkInfo();
            alterParam.setConsignorCode(firstParam.getConsignorCode()); // 荷主コード
            alterParam.setBatchNo(firstParam.getBatchNo()); // バッチNo.
            alterParam.setPlanAreaNo(firstParam.getRetrievalAreaNo()); // エリア
            alterParam.setOrderNo(orderList.get(i)); // オーダーNo.
            alterParam.setOrderSerialNo(String.valueOf(i + 1)); // オーダー連番
            wkInfoCtrl.setOrderSerialNo(alterParam, firstParam.getWmsUserInfo());
        }

        // ハードウェア区分の設定
        firstParam.setHardwareType(SystemDefine.HARDWARE_TYPE_RFT);

        // 出庫開始
        OutParameter outParam = start(WmsParam.MAX_RFT_JOBCOLLECT, workInfos, firstParam.getWmsUserInfo());

        return outParam;
    }

    /**
     * 出庫作業完了
     * 
     * パラメータの設定単位キーに該当するデータに対して出庫完了処理を行います。
     * パラメータの完了フラグによって確定(完了 or
     * 欠品完了)、箱替え、キャンセルのいずれかの処理を行います。
     * 
     * @param params 出庫入力パラメータ
     * @throws ReadWriteException
     *         データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException
     *         すでにレコードがロックされていた場合にスローされます。
     * @throws OperatorException
     *         完了処理で異常が発生したとき、要素番号を返します。
     * @throws InvalidDefineException
     *         パラメータがRetrievalInParameter[]でないときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws ScheduleException
     *         出庫作業完了でエラーが発生した場合にスローされます。
     */
    public void complete(RetrievalInParameter[] params)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                InvalidDefineException,
                NotFoundException,
                NoPrimaryException,
                ScheduleException
    {
        WorkInfoController wkInfoCtrl = new WorkInfoController(getConnection(), getCaller());
        for (int i = 0; i < params.length; i++)
        {
            try
            {
                RetrievalInParameter param = params[i];
                if (i == 0)
                {
                    // lock first
                    String settingKey = param.getSettingUnitKey();
                    wkInfoCtrl.lockComplete(settingKey, SystemDefine.JOB_TYPE_RETRIEVAL);
                }

                completeCollectData(param);
            }
            catch (OperatorException e)
            {
                e.setErrorLineNo(i + 1);
                throw e;
            }
            catch (NotFoundException e)
            {
                OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                opex.setErrorLineNo(i + 1);
                throw opex;
            }
        }
    }

    /**
     * 出庫作業欠品完了<br>
     * 
     * パラメータの設定単位キーに該当するデータに対して欠品完了処理を行います。<br>
     * 
     * @param params 出庫入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws OperatorException 完了処理で異常が発生したとき、要素番号を返します。
     * @throws InvalidDefineException パラメータがnullまたは空であるときスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws ScheduleException 出庫作業完了でエラーが発生した場合にスローされます。
     * @throws DataExistsException 作業情報登録済み
     */
    public void shortageComplete(RetrievalInParameter[] params)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                InvalidDefineException,
                NoPrimaryException,
                ScheduleException,
                DataExistsException
    {
        if (ArrayUtil.isEmpty(params))
        {
            throw new InvalidDefineException("parameter is null or zero lengh");
        }
        
        // create controller
        HostSendController hsc = new HostSendController(getConnection(), getCaller());
        WarenaviSystemController wsc = new WarenaviSystemController(getConnection(), getCaller());
        
        // create handler
        WMSSequenceHandler seqh = new WMSSequenceHandler(getConnection());
        RetrievalPlanHandler rph = new RetrievalPlanHandler(getConnection());
        WorkInfoHandler wih = new WorkInfoHandler(getConnection());
        
        // 作業日を取得
        String workday = wsc.getWorkDay();
        // ユーザ情報
        WmsUserInfo userinfo = params[0].getWmsUserInfo();
        // 欠品確定単位キー
        String unit_key = seqh.nextStartUnitKey();
        // 欠品確定設定日時
        Date setting_date = WmsFormatter.toDateTime(DbDateUtil.getSystemDateTime());
        
        ShortageOperator shortageOpe = new ShortageOperator(getConnection(), unit_key, setting_date, getCaller());
        
        for (int i = 0; i < params.length; i++)
        {
            RetrievalPlanSearchKey pkey = createLockShortageCompleteKey(params[i]);
            
            // lock
            RetrievalPlan plan = (RetrievalPlan)rph.findPrimaryForUpdate(pkey);
            
            if (plan == null)
            {
                // 1件も見つからない場合、Exceptionを生成
                OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                opex.setErrorLineNo(i + 1);
                throw opex;
            }
            
            // 作業の残数
            int remained_qty = AbstractAllocateOperator.getAllocationQty(getConnection(), plan);

            // 欠品情報を作成する
            shortageOpe.createShortageComplete(remained_qty, remained_qty, plan.getPlanQty() - remained_qty, plan);
            
            String jobno = seqh.nextWorkInfoJobNo();
    
            // 作業情報を欠品完了で作成する
            WorkInfo newWork = createShortageCompleteWorkInfo(plan, jobno, workday, remained_qty, userinfo);
            wih.create(newWork);

            try
            {
                // 実績送信情報を作成する
                hsc.insertByWorkInfo(jobno, userinfo);
    
                // 出庫予定情報を更新する
                RetrievalPlanAlterKey updateKey = new RetrievalPlanAlterKey();
        
                String status = getPlanStatus(plan.getPlanUkey());
        
                updateKey.setPlanUkey(plan.getPlanUkey());
                updateKey.updateStatusFlag(status);
                updateKey.updateSchFlag(RetrievalPlan.SCH_FLAG_SCHEDULE);
                updateKey.updateShortageQtyWithColumn(RetrievalPlan.SHORTAGE_QTY, new BigDecimal(remained_qty));
                if (STATUS_FLAG_COMPLETION.equals(status))
                {
                    updateKey.updateWorkDay(workday);
                }
                updateKey.updateLastUpdatePname(getCallerName());
                rph.modify(updateKey);
            }
            catch (NotFoundException e)
            {
                // 他で更新済み
                OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                opex.setErrorLineNo(i + 1);
                throw opex;
            }
        }
    }

    /**
     * 出庫作業キャンセル処理を行います。<br>
     * 
     * パラメータの設定単位キーに該当するデータに対して一括して出庫キャンセル処理を行います。
     * 
     * @param inParam 出庫入力パラメータ
     * @throws ReadWriteException
     *         データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException
     *         すでにレコードがロックされていた場合にスローされます。
     * @throws OperatorException
     *         キャンセル処理で異常が発生したとき、要素番号を返します。
     * @throws InvalidDefineException
     *         パラメータがRetrievalInParameter[]でないときスローされます。
     */
    public void cancel(RetrievalInParameter inParam)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                InvalidDefineException
    {
        WorkInfoController wkInfoCtrl = new WorkInfoController(getConnection(), getCaller());
        try
        {
            // 入出庫作業情報ロック処理
            WorkInfo[] workInfos =
                    wkInfoCtrl.lockComplete(inParam.getSettingUnitKey(), SystemDefine.JOB_TYPE_RETRIEVAL);
            // 入出庫作業情報キャンセル処理
            wkInfoCtrl.cancelWorkInfo(inParam.getSettingUnitKey());

            RetrievalPlanController retPlanCtrl = new RetrievalPlanController(getConnection(), getCaller());
            // 出庫予定情報キャンセル
            retPlanCtrl.cancelPlan(workInfos);
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 出庫RFT作業データ変更
     * 
     * @param param 入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws InvalidDefineException 定義情報が異常な場合に使用する例外です。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws ScheduleException スケジュール処理の実行中に予期しない例外が発生した場合に通知される例外です。
     * @throws NoPrimaryException 定義情報が異常な場合にスローされます。
     * @throws OperatorException 作業データの処理が正常に完了できない場合に通知される例外です。
     */
    public void rftUpdate(RetrievalInParameter param)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException,
                ScheduleException,
                NoPrimaryException,
                OperatorException
    {

        int restResultQty = param.getResultQty(); // 残実績数
        int resultQty = 0; // 実績数
        int shortageQty = 0; // 欠品数
        int workTime = 0; // 作業時間

        // 入出庫作業情報エンティティ配列の取得
        WorkInfo[] workInfos = searchWorkInfoByCollectJobNo(param);

        // 入出庫作業情報コントローラ
        WorkInfoController wkInfoCtrl = new WorkInfoController(getConnection(), getCaller());

        // 作業日の取得
        WarenaviSystemController wmsControl = new WarenaviSystemController(getConnection(), getCaller());
        String workDay = wmsControl.getWorkDay();

        try
        {
            for (WorkInfo workInfo : workInfos)
            {
                if (RetrievalInParameter.RESULT_UPDATE_TYPE_RETRIEVAL_START.equals(param.getCompletionFlag()))
                {
                    wkInfoCtrl.rftUpdateResultWorkNow(workInfo, param.getWmsUserInfo());
                }
                else
                {
                    // 完了フラグが「未作業」の場合
                    if (RetrievalInParameter.COMPLETION_FLAG_UNSTART.equals(param.getCompletionFlag()))
                    {
                        // 実績数クリア
                        resultQty = 0;
                    }
                    // 残実績数 < 入出庫作業情報.予定数
                    else if (restResultQty < workInfo.getPlanQty())
                    {
                        resultQty = restResultQty;
                    }
                    else
                    {
                        resultQty = workInfo.getPlanQty();
                    }

                    // 残実績数の減算
                    restResultQty -= resultQty;

                    // 作業時間がゼロ以外の場合
                    if (0 < param.getWorkSeconds() && resultQty != 0)
                    {
                        float wsecd = (float)param.getWorkSeconds() / (float)param.getResultQty();
                        workTime = Math.round(wsecd * resultQty);

                    }

                    WorkInfo resultWork = createResultWorkInfo(param, resultQty, shortageQty, workDay, workTime);
                    if (RetrievalInParameter.RESULT_UPDATE_TYPE_RETRIEVAL_CONFIRM.equals(param.getCompletionFlag()))
                    {
                        // 入出庫作業情報コントローラの作業情報完了処理
                        wkInfoCtrl.rftUpdateResultComplete(workInfo, resultWork, param.getWmsUserInfo());
                    }
                    else if (RetrievalInParameter.RESULT_UPDATE_TYPE_RETRIEVAL_SKIP.equals(param.getCompletionFlag()))
                    {
                        // 入出庫作業情報コントローラの作業情報完了処理
                        wkInfoCtrl.rftUpdateResultSkipCnt(workInfo, param.getWmsUserInfo());
                    }
                }
            }
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }
    // ------------------------------------------------------------
    // accessor methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // package methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // protected methods
    // ------------------------------------------------------------
    /**
     * 出庫作業開始処理を行います。<br>
     * 
     * 入出庫作業情報をパラメータとし集約して開始処理を行います。
     * 設定単位キーを採番して戻り値として返します。<br>
     * 予定棚、商品コード、予定ロットNo.、オーダーNo.単位で集約作業No.を採番して更新処理を行います。
     * 
     * @param maxCollectNoCnt
     *        集約作業No.最大件数件数無制限(-1)が渡された場合は無制限とする
     * @param workInfos 入出庫作業情報
     * @param userInfo ユーザ情報
     * @return 設定単位キー
     * @throws ReadWriteException
     *         データベースアクセスエラーが発生したときスローされます。
     * @throws OperatorException
     *         開始処理で異常が発生したとき、要素番号を返します。
     * @throws ScheduleException 作業情報、在庫情報を検索できなかったときスローされます。
     * @throws DataExistsException 
     */
    protected OutParameter start(int maxCollectNoCnt, WorkInfo[] workInfos, WmsUserInfo userInfo)
            throws ReadWriteException,
                OperatorException,
                ScheduleException,
                DataExistsException
    {
        // リターンパラメータ
        RetrievalOutParameter retParam = new RetrievalOutParameter();

        // 出庫予定情報コントローラ
        RetrievalPlanController retPlanCtrl = new RetrievalPlanController(getConnection(), getCaller());
        // 入出庫作業情報コントローラ
        WorkInfoController wkInfoCtrl = new WorkInfoController(getConnection(), getCaller());
        // シーケンスハンドラ
        WMSSequenceHandler seqHandler = new WMSSequenceHandler(getConnection());
        // キー比較用
        RetrievalInParameter saveParam = new RetrievalInParameter(userInfo);

        // 設定単位キー採番
        String settingUnitKey = seqHandler.nextWorkInfoSetUkey();
        retParam.setSettingUnitKey(settingUnitKey);

        String beforePlanKey = ""; // 前回予定一意キー
        String collectJobNo = ""; // 集約作業No.
        int numberingCnt = 0; // 集約作業No.採番件数

        for (int i = 0; i < workInfos.length; i++)
        {
            // キー比較
            boolean same = saveParam.setKeys(workInfos[i]);

            if (!same || i == 0)
            {
                // check max collect
                if (0 < maxCollectNoCnt && ++numberingCnt > maxCollectNoCnt)
                {
                    break;
                }
                // 集約作業Noの採番
                collectJobNo = seqHandler.nextWorkInfoCollectJobNo();
            }
            try
            {
                workInfos[i].setSettingUnitKey(settingUnitKey); // 設定単位キー
                workInfos[i].setCollectJobNo(collectJobNo); // 集約作業No.
                // 作業情報開始
                wkInfoCtrl.startWorkInfo(workInfos[i], userInfo);
            }
            catch (NotFoundException e)
            {
                throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            }
            String newPlanKey = workInfos[i].getPlanUkey();
            // 予定一意キーの比較
            if (!beforePlanKey.equals(newPlanKey))
            {
                try
                {
                    // 出庫予定情報を作業中に更新
                    retPlanCtrl.startPlan(newPlanKey);
                }
                catch (NotFoundException e)
                {
                    throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                }
                beforePlanKey = newPlanKey;
            }
        }
        // WorkList作成
		createWorkListByWorkInfo(settingUnitKey);

        return retParam;
    }

    /**
     * 集約作業完了
     * 
     * パラメータの集約作業No.に該当するデータに対して入出庫作業情報を検索し完了処理を行います。<br>
     * パラメータの実績数、作業時間を分配し、予定情報、作業情報、在庫情報を更新します。
     * 
     * @param param 出庫入力パラメータ
     * @throws ReadWriteException
     *         データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws InvalidDefineException
     *         指定パラメータが異常であったときスローされます。
     * @throws OperatorException 完了処理で問題があったときスローされます。
     * @throws ScheduleException
     *         集約作業完了でエラーが発生した場合にスローされます。
     * @throws LockTimeOutException
     *         ロックが獲得できなかった場合にスローされます。
     */
    protected void completeCollectData(RetrievalInParameter param)
            throws ReadWriteException,
                NoPrimaryException,
                InvalidDefineException,
                OperatorException,
                ScheduleException,
                LockTimeOutException
    {

        int restResultQty = param.getResultQty(); // 残実績数
        int resultQty = 0; // 実績数
        int shortageQty = 0; // 欠品数
        int workTime = 0; // 作業時間

        // 入出庫作業情報エンティティ配列の取得
        WorkInfo[] workInfos = searchWorkInfoByCollectJobNo(param);

        // 入出庫作業情報コントローラ
        WorkInfoController wkInfoCtrl = new WorkInfoController(getConnection(), getCaller());

        // 出庫予定情報コントローラ
        RetrievalPlanController retPlanCtrl = new RetrievalPlanController(getConnection(), getCaller());

        // 作業日の取得
        WarenaviSystemController wmsControl = new WarenaviSystemController(getConnection(), getCaller());
        String workDay = wmsControl.getWorkDay();

        try
        {
            for (WorkInfo workInfo : workInfos)
            {
                // 完了フラグが「未作業」の場合
                if (RetrievalInParameter.COMPLETION_FLAG_UNSTART.equals(param.getCompletionFlag()))
                {
                    // 実績数クリア
                    resultQty = 0;
                }
                // 残実績数 < 入出庫作業情報.予定数
                else if (restResultQty < workInfo.getPlanQty())
                {
                    resultQty = restResultQty;

                    // 完了フラグが「確定」の場合
                    if (RetrievalInParameter.COMPLETION_FLAG_DECISION.equals(param.getCompletionFlag()))
                    {
                        // 欠品数の計算
                        shortageQty = workInfo.getPlanQty() - resultQty;
                    }
                }
                else
                {
                    resultQty = workInfo.getPlanQty();
                }

                // 残実績数の減算
                restResultQty -= resultQty;

                // 作業時間がゼロ以外の場合
                if (0 < param.getWorkSeconds() && resultQty != 0)
                {
                    float wsecd = (float)param.getWorkSeconds() / (float)param.getResultQty();
                    workTime = Math.round(wsecd * resultQty);

                }

                WorkInfo resultWork = createResultWorkInfo(param, resultQty, shortageQty, workDay, workTime);

                // 入出庫作業情報コントローラの作業情報完了処理
                wkInfoCtrl.completeWorkInfo(workInfo, resultWork, param.getCompletionFlag(), param.getWmsUserInfo());
                // 出庫予定情報の更新
                retPlanCtrl.completePlan(workInfo.getPlanUkey(), resultQty, shortageQty, workDay);
                if ((resultQty + shortageQty) > 0 && wmsControl.hasStockPack())
                {
                    StockController stockCtrl = new StockController(getConnection(), getCaller());
                    Stock lockStock = new Stock();
                    lockStock.setStockId(workInfo.getStockId());
                    // 在庫情報ロック
                    Stock[] stocks = stockCtrl.lock(lockStock);
                    if (ArrayUtil.isEmpty(stocks))
                    {
                        throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                    }

                    for (Stock stock : stocks)
                    {
                        // 在庫コントローラの出庫処理
                        stockCtrl.retrieval(stock, SystemDefine.JOB_TYPE_RETRIEVAL, resultQty, shortageQty, workDay,
                                false, param.getWmsUserInfo());
                    }
                    // 実績が存在する場合
                    if (resultQty > 0)
                    {
                        // 仮置きエリアの取得
                        AreaController areaCtrl = new AreaController(getConnection(), getCaller());
                        String temporaryArea = areaCtrl.getTemporaryArea(param.getRetrievalAreaNo());

                        // 仮置きエリアが存在する場合
                        if (!StringUtil.isBlank(temporaryArea))
                        {
                            // 仮置き在庫作成
                            createTemporaryStock(param, temporaryArea, resultQty, workDay);
                        }
                    }
                }
            }
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
        catch (DataExistsException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 仮置き在庫の作成処理を行います。
     * 
     * @param param 出庫入力パラメータ
     * @param temporaryArea 仮置きエリア
     * @param resultQty 実績数
     * @param workDay 作業日
     * @throws ReadWriteException
     *         データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws NotFoundException 該当データが存在しない場合にスローされます。
     * @throws OperatorException 完了処理で問題があったときスローされます。
     * @throws ScheduleException システム定義不整合
     * @throws LockTimeOutException
     *         ロックが獲得できなかった場合にスローされます。
     * @throws DataExistsException 在庫登録済みの場合にスローされます
     */
    protected void createTemporaryStock(RetrievalInParameter param, String temporaryArea, int resultQty, String workDay)
            throws ScheduleException,
                ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                OperatorException,
                NotFoundException,
                DataExistsException
    {
        StockController stockCtrl = new StockController(getConnection(), getCaller());

        // 在庫ロック処理
        Stock[] storageStocks = stockCtrl.lock(createLockStockKey(param, temporaryArea));

        // 在庫が無い場合は新しく作成する
        if (storageStocks == null || storageStocks.length == 0)
        {
            // 在庫新規登録
            stockCtrl.initStorage(createStorageStock(param, temporaryArea, resultQty, workDay),
                    SystemDefine.JOB_TYPE_RETRIEVAL, param.getWmsUserInfo(), 0);
        }
        else
        {
            for (Stock storageStock : storageStocks)
            {
                Stock addStock = new Stock();
                addStock.setStockQty(resultQty); // 在庫数
                // DFKLOOK v3.4 積み増しの場合は、入庫日時を更新しないように修正
                //addStock.setStorageDay(workDay); // 入庫日
                // 積み増し在庫登録
                stockCtrl.addStorage(storageStock, addStock, SystemDefine.JOB_TYPE_RETRIEVAL, false,
                        param.getWmsUserInfo(), SystemDefine.DEFAULT_REASON_TYPE);
            }
        }
    }

    /**
     * 集約作業No.を条件に入出庫作業情報エンティティを取得します。
     * 
     * @param param 出庫入力パラメータ
     * @return 入出庫作業情報エンティティ配列
     * @throws ReadWriteException
     *         データベースアクセスエラーが発生したときスローされます。
     * @throws OperatorException データが存在しない場合にスローされます。
     */
    protected WorkInfo[] searchWorkInfoByCollectJobNo(RetrievalInParameter param)
            throws ReadWriteException,
                OperatorException
    {
        WorkInfoSearchKey searchKey = new WorkInfoSearchKey();

        searchKey.setCollectJobNo(param.getCollectJobNo()); // 集約作業No.

        searchKey.setPlanUkeyOrder(true);// 予定一意キー順
        searchKey.setJobNoOrder(true);// 作業No.順

        // 入出庫作業情報ハンドラ
        WorkInfoHandler wkInfoHdlr = new WorkInfoHandler(getConnection());

        // 入出庫作業情報の検索
        WorkInfo[] workInfos = (WorkInfo[])wkInfoHdlr.find(searchKey);

        // データ無しの場合
        if (workInfos == null || workInfos.length == 0)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }

        return workInfos;
    }

    /**
     * 入庫用の在庫情報エンティティを作成します。
     * 
     * @param param 出庫入力パラメータ
     * @param temporaryArea 仮置きエリア
     * @param resultQty 実績数
     * @param workDay 作業日
     * @return 入庫用の在庫情報エンティティ
     */
    protected Stock createStorageStock(RetrievalInParameter param, String temporaryArea, int resultQty, String workDay)
    {
        Stock stock = createLockStockKey(param, temporaryArea);

        // 在庫数
        stock.setStockQty(resultQty);
        // 入庫日
        stock.setStorageDay(workDay);
        // 出庫可能数
        stock.setAllocationQty(resultQty);
        // 入庫日時
        stock.setStorageDate(new SysDate());

        return stock;
    }

    /**
     * 在庫情報テーブルロック用のエンティティを作成します。
     * 
     * @param param 出庫入力パラメータ
     * @return 在庫情報テーブルロック用のエンティティ
     */
    protected Stock createLockStockKey(RetrievalInParameter param)
    {
        Stock stock = new Stock();

        // エリア
        stock.setAreaNo(param.getRetrievalAreaNo());
        // 棚
        stock.setLocationNo(param.getRetrievalLocation());
        // 荷主コード
        stock.setConsignorCode(param.getConsignorCode());
        // 商品コード
        stock.setItemCode(param.getItemCode());
        // ロットNo.
        stock.setLotNo(param.getLotNo());

        return stock;
    }

    /**
     * 在庫情報テーブルロック用のエンティティを作成します。
     * 
     * @param param 出庫入力パラメータ
     * @param temporaryArea 仮置きエリア
     * @return 在庫情報テーブルロック用のエンティティ
     */
    protected Stock createLockStockKey(RetrievalInParameter param, String temporaryArea)
    {
        Stock stock = new Stock();

        // エリア
        stock.setAreaNo(temporaryArea);
        // 棚(WmsParamのデフォルト)
        stock.setLocationNo(WmsParam.DEFAULT_LOCATION_NO);
        // 荷主コード
        stock.setConsignorCode(param.getConsignorCode());
        // 商品コード
        stock.setItemCode(param.getItemCode());
        // ロットNo.
        stock.setLotNo(param.getLotNo());

        return stock;
    }

    /**
     * 入出庫作業情報テーブル更新用のエンティティを作成します。
     * 
     * @param param 出庫入力パラメータ
     * @param resultQty 実績数
     * @param shortageQty 欠品数
     * @param systemWorkDay 作業日
     * @param workTime 作業時間
     * @return 入出庫作業情報テーブル更新用のエンティティ
     */
    protected WorkInfo createResultWorkInfo(RetrievalInParameter param, int resultQty, int shortageQty,
            String systemWorkDay, int workTime)
    {
        WorkInfo workInfo = new WorkInfo();

        // エリア
        workInfo.setResultAreaNo(param.getRetrievalAreaNo());
        // 棚
        workInfo.setResultLocationNo(param.getRetrievalLocation());
        // ロットNo.
        workInfo.setResultLotNo(param.getResultLotNo());
        // 実績数
        workInfo.setResultQty(resultQty);
        // 欠品数
        workInfo.setShortageQty(shortageQty);
        // 作業日
        workInfo.setWorkDay(systemWorkDay);
        // 作業時間
        workInfo.setWorkSecond(workTime);

        return workInfo;
    }
    
    /**
     * 入出庫作業情報テーブルに作成する欠品完了データのエンティティを作成します。
     * 
     * @param param 出庫入力パラメータ
     * @return 出庫予定情報テーブル更新用のエンティティ
     */
    protected WorkInfo createShortageCompleteWorkInfo(RetrievalPlan plan, String jobno, String workday,
            int remained, WmsUserInfo userinfo)
    {
        WorkInfo workinfo = new WorkInfo();
        
        workinfo.setJobNo(jobno);
        workinfo.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);
        workinfo.setStatusFlag(WorkInfo.STATUS_FLAG_COMPLETION);
        workinfo.setHardwareType(WorkInfo.HARDWARE_TYPE_UNSTART);
        workinfo.setPlanUkey(plan.getPlanUkey());
        workinfo.setPlanDay(plan.getPlanDay());
        workinfo.setConsignorCode(plan.getConsignorCode());
        workinfo.setCustomerCode(plan.getCustomerCode());
        workinfo.setShipTicketNo(plan.getShipTicketNo());
        workinfo.setShipLineNo(plan.getShipLineNo());
        workinfo.setShipBranchNo(plan.getBranchNo());
        workinfo.setBatchNo(plan.getBatchNo());
        workinfo.setOrderNo(plan.getOrderNo());
        workinfo.setPlanAreaNo(plan.getPlanAreaNo());
        workinfo.setPlanLocationNo(plan.getPlanLocationNo());
        workinfo.setItemCode(plan.getItemCode());
        workinfo.setPlanLotNo(plan.getPlanLotNo());
        workinfo.setPlanQty(remained);
        workinfo.setResultQty(0);
        workinfo.setShortageQty(remained);
        workinfo.setWorkDay(workday);
        workinfo.setUserId(userinfo.getUserId());
        workinfo.setTerminalNo(userinfo.getTerminalNo());
        workinfo.setRegistPname(getCallerName());
        workinfo.setLastUpdatePname(getCallerName());
        
        return workinfo;
    }
    
    /**
     * 欠品確定のために出庫予定情報テーブルロック用検索キーを作成します。
     * 
     * @param param 出庫入力パラメータ
     * @return 出庫予定情報テーブルロック用検索キー
     */
    protected RetrievalPlanSearchKey createLockShortageCompleteKey(RetrievalInParameter param)
    {
        RetrievalPlanSearchKey plan = new RetrievalPlanSearchKey();

        // 設定単位キー
        plan.setPlanUkey(param.getPlanUKey());
        // 作業状態(削除以外)
        plan.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        // スケジュールフラグ(欠品予約)
        plan.setSchFlag(RetrievalPlan.SCH_FLAG_RESERVATION_SHORTAGE);

        return plan;
    }

    /**
     * オーダーNo.チェックを行います。<br>
     * 
     * パラーメータの全オーダーNo.のデータがパラメータの入出庫作業情報に存在するかをチェックします。<br>
     * 入出庫作業情報に存在しないオーダーNo.が1件でも存在する場合はNotFoundExceptionを通知します。
     * 
     * @param workInfos 入出庫作業情報
     * @param orderNos オーダーNo.
     * @throws NotFoundException
     *         入出庫作業情報にオーダーNo.が存在しない場合にスローされます。
     */
    protected void checkOrderNo(WorkInfo[] workInfos, String[] orderNos)
            throws NotFoundException
    {
        boolean[] isExists = new boolean[orderNos.length];

        for (int i = 0; i < orderNos.length; i++)
        {
            for (WorkInfo workInfo : workInfos)
            {
                // 同一オーダーNo.の場合
                if (orderNos[i].equals(workInfo.getOrderNo()))
                {
                    isExists[i] = true;
                    break;
                }
            }
            if (!isExists[i])
            {
                throw new NotFoundException();
            }
        }
    }

    /**
     * 設定単位キーを元に、入出庫作業情報を検索を行い、作業リスト情報を作成します。<br>
     * <ul>
     * 以下のDB情報を検索します。
     * <li>出荷先マスタ
     * <li>商品マスタ
     * <li>理由区分マスタ
     * <li>ログインユーザ
     * </ul>
     *
     * @param key 設定単位キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException 作業情報、在庫情報を検索できなかったときスローされます。
     * @throws DataExistsException 
     */
    protected void createWorkListByWorkInfo(String key)
            throws ReadWriteException,
                ScheduleException, 
                DataExistsException
    {
        WorkInfoHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey wkey = new WorkInfoSearchKey();

        // 取得項目一覧の作成
        FieldName[] collects = {
            WorkInfo.JOB_NO,
            WorkInfo.SETTING_UNIT_KEY,
            WorkInfo.COLLECT_JOB_NO,
            WorkInfo.JOB_TYPE,
            WorkInfo.PLAN_UKEY,
            WorkInfo.STOCK_ID,
            WorkInfo.PLAN_DAY,
            WorkInfo.CONSIGNOR_CODE,
            Consignor.CONSIGNOR_NAME,
            WorkInfo.CUSTOMER_CODE,
            Customer.CUSTOMER_NAME,
            WorkInfo.SHIP_TICKET_NO,
            WorkInfo.SHIP_LINE_NO,
            WorkInfo.SHIP_BRANCH_NO,
            WorkInfo.BATCH_NO,
            WorkInfo.ORDER_NO,
            WorkInfo.PLAN_AREA_NO,
            WorkInfo.PLAN_LOCATION_NO,
            WorkInfo.ITEM_CODE,
            Item.ITEM_NAME,
            Item.JAN,
            Item.ITF,
            Item.BUNDLE_ITF,
            Item.ENTERING_QTY,
            Item.BUNDLE_ENTERING_QTY,
            WorkInfo.PLAN_LOT_NO,
            WorkInfo.PLAN_QTY,
            WorkInfo.REASON_TYPE,
            Reason.REASON_NAME,
            WorkInfo.USER_ID,
            WorkInfo.TERMINAL_NO,
        };

        // 取得フィールドのセット
        for (FieldName fld : collects)
        {
            wkey.setCollect(fld);
        }

        // 作業リストと列名が異なる値の取得を追加
        wkey.setCollect(Com_loginuser.USERNAME, "", WorkList.USER_NAME);

        // 検索条件のセット
        wkey.setSettingUnitKey(key);

        // 結合条件のセット
        wkey.setJoin(WorkInfo.CONSIGNOR_CODE, Consignor.CONSIGNOR_CODE);
        wkey.setJoin(WorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        wkey.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);
        wkey.setJoin(WorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        wkey.setJoin(WorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");
        wkey.setJoin(WorkInfo.REASON_TYPE, "", Reason.REASON_TYPE, "(+)");
        
        wkey.setJoin(WorkInfo.USER_ID, "", Com_loginuser.USERID, "(+)");

        // ソート
        wkey.setStockIdOrder(true);
        wkey.setJobNoOrder(true);

        // 検索実行
        Entity[] readEnts = wih.find(wkey);
        if (null == readEnts || readEnts.length == 0)
        {
            // 見つからなかった場合は、エラー
            throw new ScheduleException();
        }

        WorkListHandler wlh = new WorkListHandler(getConnection());

        String stock_id = "";
        String cname = getCallerName();
        int stock_qty = 0;
        int alloc_qty = 0;
        int work_qty = 0;
        for (Entity readEnt : readEnts)
        {
            WorkList wlEnt = new WorkList();
            wlEnt.setValueMap(readEnt.getValueMap());

            // 出庫、予定外出庫の場合
            if (StringUtil.isBlank(stock_id) || !stock_id.equals(wlEnt.getStockId()))
            {
                stock_id = wlEnt.getStockId();
                
                Stock stk = getStock(stock_id);
                alloc_qty = getTotalPlanQty(key, wlEnt.getStockId()) + stk.getAllocationQty();

                if (stk == null)
                {
                    // 平置き作業の場合、在庫がなくなっていたら全数出庫
                    stock_qty = alloc_qty;
                    // 作業前の引当可能数
                }
                else
                {
	                stock_qty = stk.getStockQty();
	                // 作業前の引当可能数
                }
            }
            else
            {
                // 引当可能数の減算
                alloc_qty -= work_qty;
            }
            
            work_qty = wlEnt.getPlanQty();
            
            // 在庫数、引当可能数のセット
            wlEnt.setAllocationQty(alloc_qty);
            wlEnt.setStockQty(stock_qty);
            
            wlEnt.setRegistDate(new SysDate());
            wlEnt.setRegistPname(cname);

            // insert
            wlh.create(wlEnt);
        }
    }
    
    /**
     * 在庫IDから在庫情報を取得します。<br>
     * 対象データが無い場合はnullを返します。
     * 
     * @param stock_id
     * @throws ReadWriteException データベースエラーの場合にスローされます。
     */
    protected Stock getStock(String stock_id)
            throws ReadWriteException
    {
        StockSearchKey skey = new StockSearchKey();
        
        skey.setStockId(stock_id);
        
        Stock[] stk = (Stock[])(new StockHandler(getConnection())).find(skey);
        
        if (stk == null || stk.length == 0)
        {
            return null;
        }
        
        return stk[0];
    }

    /**
     * 同一在庫を対象とした作業の合計予定数を取得します。
     * 
     * @param setting_ukey
     * @param stock_id
     * @throws ReadWriteException データベースエラーの場合にスローされます。
     */
    protected int getTotalPlanQty(String setting_ukey, String stock_id)
            throws ReadWriteException
    {
        WorkInfoSearchKey wkey = new WorkInfoSearchKey();
        
        wkey.setSettingUnitKey(setting_ukey);
        wkey.setStockId(stock_id);
        wkey.setStatusFlag(WorkInfo.STATUS_FLAG_DELETE, "!=");
        wkey.setPlanQtyCollect("SUM");
        
        WorkInfo[] work = (WorkInfo[])(new WorkInfoHandler(getConnection())).find(wkey);
        
        return work[0].getPlanQty();
    }
    
    /**
     * 予定情報の状態を取得します。
     * 
     * @param planUkey 予定一意キー
     * @return 状態フラグ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    protected String getPlanStatus(String planUkey)
            throws ReadWriteException,
                NotFoundException
    {
        WorkInfoHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        key.setPlanUkey(planUkey);
        key.setStatusFlag(STATUS_FLAG_DELETE, "!=", "", "", true);
        key.setStatusFlagCollect("UNIQUE");

        WorkInfo[] recs = (WorkInfo[])wih.find(key);
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
    
    // ------------------------------------------------------------
    // private methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // utility methods
    // ------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * 
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: RetrievalOperator.java 6358 2009-12-04 00:44:01Z kishimoto $";
    }
}

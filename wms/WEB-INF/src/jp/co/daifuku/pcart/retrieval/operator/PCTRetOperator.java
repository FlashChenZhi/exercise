// $Id: PCTRetOperator.java 4403 2009-06-08 03:49:16Z ota $
package jp.co.daifuku.pcart.retrieval.operator;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

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
import jp.co.daifuku.pcart.base.util.PCTAutoUpdateProcess;
import jp.co.daifuku.pcart.retrieval.controller.PCTOrderInfoController;
import jp.co.daifuku.pcart.retrieval.controller.PCTRetHostSendController;
import jp.co.daifuku.pcart.retrieval.controller.PCTRetPlanController;
import jp.co.daifuku.pcart.retrieval.controller.PCTRetWorkController;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetHostSend;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;


/**
 * PCT出庫作業のためのオペレータクラスです。
 *
 * @version $Revision: 4403 $, $Date: 2009-06-08 12:49:16 +0900 (月, 08 6 2009) $
 * @author  073019
 * @author  Last commit: $Author: ota $
 */
public class PCTRetOperator
        extends AbstractOperator
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

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * データベースコネクションと呼び出し元クラスを指定して
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public PCTRetOperator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 出庫作業完了<BR>
     * <BR>
     * パラメータの設定単位キーに該当するデータに対してPCT出庫完了処理を行います。<BR>
     * パラメータの完了フラグによって確定(完了 or 欠品完了)、箱替え、キャンセルのいずれかの処理を行います。<BR>
     * 
     * @param params 出庫入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws OperatorException 完了処理で異常が発生したとき、要素番号を返します。
     * @throws InvalidDefineException パラメータがRetrievalInParameter[]でないときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws ScheduleException 出庫作業完了でエラーが発生した場合にスローされます。
     */
    public void complete(PCTRetrievalInParameter[] params)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                InvalidDefineException,
                NotFoundException,
                NoPrimaryException,
                ScheduleException
    {
        // PCT作業コントローラクラス
        PCTRetWorkController wkInfoCtrl = new PCTRetWorkController(getConnection(), getCaller());
        //eWareNaviの作業日を取得
        WarenaviSystemController navi = new WarenaviSystemController(getConnection(), getClass());
        String workDay = navi.getWorkDay();

        for (int i = 0; i < params.length; i++)
        {
            PCTRetrievalInParameter param = params[i];

            // ユーザー情報はパラメータ配列の1番目にしかセットされていない為、
            // この場所で1番目のデータをセットする
            param.setUserId(params[0].getUserId());
            param.setUserName(params[0].getUserName());
            param.setTerminalNo(params[0].getTerminalNo());
            param.setTerminalName(params[0].getTerminalName());
            param.setTerminalAddress(params[0].getTerminalAddress());

            try
            {
                // 完了用にPCT出庫作業情報,PCT出庫予定情報のロックを行う 
                wkInfoCtrl.lockComplete(param);

                if (PCTRetrievalInParameter.WORK_FLAG_MNT_COLLECT.equals(param.getWorkflag()))
                {
                    // 集約作業情報完了
                    completeCollectWorkData(param, workDay);
                    // 集約作業予定情報完了
                    completeCollectPlanData(param, workDay);
                }
                else
                {
                    // 集約作業情報完了
                    completeWorkData(param, workDay);
                    // 集約作業予定情報完了
                    completePlanData(param, workDay);
                }
            }
            catch (NoPrimaryException e)
            {
                // 他端末で更新済み
                OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                opex.setErrorLineNo(param.getRowNo());
                throw opex;
            }
            catch (NotFoundException e)
            {
                // 他端末で更新済み
                OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                opex.setErrorLineNo(param.getRowNo());
                throw opex;
            }
        }
    }

    /**
     * 出庫作業完了(一括)<BR>
     * <BR>
     * パラメータの設定単位キーに該当するデータに対してPCT出庫完了処理を行います。<BR>
     * パラメータの完了フラグによって確定(完了 or 欠品完了)、箱替え、キャンセルのいずれかの処理を行います。<BR>
     * 
     * @param params 出庫入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws OperatorException 完了処理で異常が発生したとき、要素番号を返します。
     * @throws InvalidDefineException パラメータがRetrievalInParameter[]でないときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws ScheduleException 出庫作業完了でエラーが発生した場合にスローされます。
     */
    public void completeAll(PCTRetrievalInParameter[] params)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                InvalidDefineException,
                NotFoundException,
                NoPrimaryException,
                ScheduleException
    {
        // PCT作業コントローラクラス
        PCTRetWorkController wkInfoCtrl = new PCTRetWorkController(getConnection(), getCaller());
        // オーダー情報コントローラーハンドラを定義する
        PCTOrderInfoController osc = new PCTOrderInfoController(getConnection(), getCaller());
        // 出庫実績送信情報コントローラーハンドラを定義する
        PCTRetHostSendController hsc = new PCTRetHostSendController(getConnection(), getCaller());
        // PCT出庫予定情報ハンドラ
        PCTRetPlanSearchKey searchPKey = new PCTRetPlanSearchKey();
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());

        PCTRetWorkInfo[] workInfoEntity = null;

        //eWareNaviの作業日を取得
        WarenaviSystemController navi = new WarenaviSystemController(getConnection(), getClass());
        String workDay = navi.getWorkDay();

        try
        {
            for (int i = 0; i < params.length; i++)
            {
                PCTRetrievalInParameter param = params[i];

                // ユーザー情報はパラメータ配列の1番目にしかセットされていない為、
                // この場所で1番目のデータをセットする
                param.setUserId(params[0].getUserId());
                param.setUserName(params[0].getUserName());
                param.setTerminalNo(params[0].getTerminalNo());
                param.setTerminalName(params[0].getTerminalName());
                param.setTerminalAddress(params[0].getTerminalAddress());

                // 初回の時だけ処理する
                if (i == 0)
                {
                    // 完了用にPCT出庫作業情報,PCT出庫予定情報のロックを行う 
                    workInfoEntity = wkInfoCtrl.lockComplete(param);

                    // 集約作業情報完了
                    completeAllWorkData(param, workInfoEntity, workDay);
                }

                try
                {
                    // 実績数
                    int resultQty = 0;
                    // 予定数
                    int planQty = 0;
                    // 欠品数
                    int shortageQty = 0;

                    // 処理フラグが全完了・全欠品完了
                    if (!PCTRetrievalInParameter.PROCESSING_DIVISION_DELETE.equals(param.getProcessingDivision()))
                    {
                        // PCT出庫エンティティ配列の取得
                        PCTRetWorkInfo[] workInfos = searchWorkInfoByJobNo(param);

                        for (int j = 0; j < workInfos.length; j++)
                        {
                            PCTRetWorkInfo workInfo = workInfos[j];

                            // パラメータの作業情報で予定データを読み込む
                            /* 検索条件
                             * 
                             *  予定一意キー      =  読み込んだ作業情報の予定一意キー
                             *  
                             */
                            searchPKey.clear();
                            // 予定一意キー
                            searchPKey.setPlanUkey(workInfo.getPlanUkey());
                            // PCT出庫作業情報の検索
                            PCTRetPlan[] retPlan = (PCTRetPlan[])planHandler.find(searchPKey);

                            // 処理フラグが全欠品完了
                            if (PCTRetrievalInParameter.PROCESSING_DIVISION_STOCKOUT.equals(param.getProcessingDivision()))
                            {
                                // 予定数
                                planQty = workInfo.getPlanQty();
                                // 実績数
                                resultQty = workInfo.getResultQty();
                                // 欠品数
                                shortageQty = workInfo.getPlanQty();
                            }
                            // 処理フラグが全完了
                            else if (PCTRetrievalInParameter.PROCESSING_DIVISION_COMPLETION.equals(param.getProcessingDivision()))
                            {
                                // 予定数
                                planQty = workInfo.getPlanQty();
                                // 実績数
                                resultQty = workInfo.getPlanQty();
                                // 欠品数
                                shortageQty = workInfo.getShortageQty();
                            }

                            // ユーザID
                            workInfo.setUserId(param.getUserId());
                            // 端末No.
                            workInfo.setTerminalNo(param.getTerminalNo());
                            // 作業日
                            workInfo.setWorkDay(workDay);

                            PCTRetWorkInfo resultWorkinfo =
                                    createResultWorkInfo(param, workInfo.getJobNo(), planQty, resultQty, shortageQty, 0);
                            // PCTオーダー情報を更新する
                            osc.updatePCTOrderInfoAll(workInfo, resultWorkinfo, retPlan[0]);
                            // PCT出庫実績送信情報に新規登録される
                            hsc.insertPCTRetHostsendAll(param, workInfo, resultWorkinfo, retPlan[0]);
                        }
                    }
                }
                catch (NoPrimaryException e)
                {
                    // 他端末で更新済み
                    OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                    opex.setErrorLineNo(param.getRowNo());
                    throw opex;
                }
                catch (DataExistsException e)
                {
                    // 他端末で更新済み
                    OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                    opex.setErrorLineNo(param.getRowNo());
                    throw opex;
                }
            }
        }
        catch (NotFoundException e)
        {
            // 他端末で更新済み
            OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            throw opex;
        }
    }

    /**
     * PCT出庫予定情報関連テーブル削除<BR>
     * <BR>
     * PCT出庫予定情報に紐づく関連データを削除します。<BR>
     * 対象テーブル：PCT出庫予定情報、PCT出庫作業情報、PCTオーダー情報、PCT出庫実績送信情報<BR>
     * @param params 対象情報
     * @return boolean 削除結果を返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws InvalidDefineException 指定パラメータが異常であったときスローされます。
     * @throws OperatorException 完了処理で問題があったときスローされます。
     * @throws ScheduleException 集約作業完了でエラーが発生した場合にスローされます。
     * @throws LockTimeOutException ロックが獲得できなかった場合にスローされます。
     */
    public boolean planLinkDelete(PCTRetrievalInParameter[] params)
            throws ReadWriteException,
                NoPrimaryException,
                InvalidDefineException,
                OperatorException,
                ScheduleException,
                LockTimeOutException
    {
        /**
         * コントローラー定義
         */
        // PCT出庫作業情報コントローラ
        PCTRetWorkController wkInfoCtrl = new PCTRetWorkController(getConnection(), getCaller());
        // オーダー情報コントローラーハンドラを定義する
        PCTOrderInfoController osc = new PCTOrderInfoController(getConnection(), getCaller());
        // 出庫実績送信情報コントローラーハンドラを定義する
        PCTRetHostSendController hsc = new PCTRetHostSendController(getConnection(), getCaller());
        // PCT出庫予定情報コントローラ
        PCTRetPlanController retPlanCtrl = new PCTRetPlanController(getConnection(), getCaller());

        // PCT出庫予定情報ハンドラ
        PCTRetPlanSearchKey searchKey = new PCTRetPlanSearchKey();

        // Pカートログオフ
        PCTAutoUpdateProcess autoUpdate = new PCTAutoUpdateProcess();

        try
        {
            String[] termNo = autoUpdate.updateRft(getConnection());
            if (termNo != null)
            {
                autoUpdate.updatePctUser(getConnection(), termNo);
            }
            autoUpdate.sessionManage();
        }
        catch (NotFoundException e)
        {
            return false;
        }

        // 削除フラグ
        boolean deleteFlg = false;

        PCTRetPlanFinder finder = null;
        finder = new PCTRetPlanFinder(getConnection());
        finder.open(true);

        try
        {
            PCTRetrievalInParameter param = params[0];

            /**
             * 削除対象データの主キーを取得
             */
            // 荷主コード
            searchKey.setConsignorCode(param.getConsignorCode());
            // 予定日
            if (!StringUtil.isBlank(param.getPlanDay()))
            {
                searchKey.setPlanDay(param.getPlanDay());
            }
            // バッチNo.
            if (!StringUtil.isBlank(param.getBatchNo()))
            {
                searchKey.setBatchNo(param.getBatchNo());
            }
            // バッチSeqNo.
            if (!StringUtil.isBlank(param.getBatchSeqNo()))
            {
                searchKey.setBatchSeqNo(param.getBatchSeqNo());
            }
            // エリア
            if (!StringUtil.isBlank(param.getPlanAreaNo()))
            {
                searchKey.setPlanAreaNo(param.getPlanAreaNo());
            }
            // 得意先コード
            if (!StringUtil.isBlank(param.getRegularCustomerCode()))
            {
                searchKey.setRegularCustomerCode(param.getRegularCustomerCode());
            }
            // 出荷先コード
            if (!StringUtil.isBlank(param.getCustomerCode()))
            {
                searchKey.setCustomerCode(param.getCustomerCode());
            }
            // オーダーNo.
            if (!StringUtil.isBlank(param.getPlanOrderNo()))
            {
                searchKey.setPlanOrderNo(param.getPlanOrderNo());
            }

            // 検索
            finder.search(searchKey);

            while (finder.hasNext())
            {
                PCTRetPlan[] plan = (PCTRetPlan[])finder.getEntities(100);

                for (PCTRetPlan plans : plan)
                {
                    // PCT出庫予定情報を削除(予定一意キー)
                    if (retPlanCtrl.deletePlanWorkMntAll(plans))
                    {
                        deleteFlg = true;
                    }

                    // PCT出庫作業情報を削除(予定一意キー)
                    if (wkInfoCtrl.deleteWorkInfoWorkMntAll(plans))
                    {
                        deleteFlg = true;
                    }

                    // PCTオーダー情報を削除(予定オーダーNo.)
                    if (osc.deleteOrderInfoWorkMntAll(plans))
                    {
                        deleteFlg = true;
                    }

                    // PCT出庫実績送信情報を削除(予定一意キー)
                    if (hsc.deleteHostsendWorkMntAll(plans))
                    {
                        deleteFlg = true;
                    }
                }
            }
            return deleteFlg;
        }
        catch (NotFoundException e)
        {
            // 他端末で更新済み
            OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            throw opex;
        }
        catch (DataExistsException e)
        {
            // 他端末で更新済み
            OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            throw opex;
        }
        finally
        {
            finder.close();
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

    /**
     * 集約作業完了<BR>
     * <BR>
     * パラメータの集約作業No.に該当するデータに対してPCT出庫作業情報を検索し完了処理を行います。<BR>
     * パラメータの実績数、作業時間を分配し、予定情報、作業情報、在庫情報を更新します。<BR>
     * 
     * @param param PCT出庫入力パラメータ
     * @param workDay 作業日
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws InvalidDefineException 指定パラメータが異常であったときスローされます。
     * @throws OperatorException 完了処理で問題があったときスローされます。
     * @throws ScheduleException 集約作業完了でエラーが発生した場合にスローされます。
     * @throws LockTimeOutException ロックが獲得できなかった場合にスローされます。
     */
    protected void completeWorkData(PCTRetrievalInParameter param, String workDay)
            throws ReadWriteException,
                NoPrimaryException,
                InvalidDefineException,
                OperatorException,
                ScheduleException,
                LockTimeOutException
    {
        // 残実績数
        int restResultQty = param.getResultQty();
        // 実績数
        int resultQty = 0;
        // 予定数
        int planQty = 0;
        // 欠品数
        int shortageQty = 0;
        // 状態フラグ
        String statusFlag = null;

        // PCT出庫エンティティ配列の取得
        PCTRetWorkInfo[] workInfos = searchWorkInfoByJobNo(param);
        // PCT出庫作業情報コントローラ
        PCTRetWorkController wkInfoCtrl = new PCTRetWorkController(getConnection(), getCaller());

        // 作業No.の取得
        String jobNo = param.getJobNo();

        try
        {
            for (int i = 0; i < workInfos.length; i++)
            {
                PCTRetWorkInfo workInfo = workInfos[i];

                // 処理フラグが削除の場合
                if (PCTRetrievalInParameter.PROCESSING_DIVISION_DELETE.equals(param.getProcessingDivision()))
                {
                    // 予定数
                    planQty = workInfo.getPlanQty();
                    // 実績数
                    resultQty = workInfo.getResultQty();
                    // 欠品数
                    shortageQty = workInfo.getShortageQty();
                    // 状態フラグ：削除
                    statusFlag = PCTRetrievalInParameter.STATUS_FLAG_DELETE;
                }
                else
                {
                    // 作業メンテナンス一括の修正登録処理を行う
                    if (PCTRetrievalInParameter.STATUS_FLAG_UNWORK.equals(param.getStatusFlag1()))
                    {
                        // 処理フラグが全欠品完了
                        if (PCTRetrievalInParameter.PROCESSING_DIVISION_STOCKOUT.equals(param.getProcessingDivision()))
                        {
                            // 予定数
                            planQty = workInfo.getPlanQty();
                            // 実績数
                            resultQty = workInfo.getResultQty();
                            // 欠品数
                            shortageQty = workInfo.getPlanQty();
                            // 状態フラグ：メンテ完了
                            statusFlag = PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION;
                        }
                        // 処理フラグが全完了
                        else if (PCTRetrievalInParameter.PROCESSING_DIVISION_COMPLETION.equals(param.getProcessingDivision()))
                        {
                            // 予定数
                            planQty = workInfo.getPlanQty();
                            // 実績数
                            resultQty = workInfo.getPlanQty();
                            // 欠品数
                            shortageQty = workInfo.getShortageQty();
                            // 状態フラグ：メンテ完了
                            statusFlag = PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION;
                        }
                    }
                    // 作業メンテナンス（オーダー単位・商品単位）の修正登録処理を行う
                    else
                    {
                        // 予定数
                        planQty = workInfo.getPlanQty();
                        // 実績数
                        resultQty = restResultQty;
                        // 欠品数
                        shortageQty = workInfo.getPlanQty() - restResultQty;
                        // 状態フラグ：メンテ完了
                        statusFlag = PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION;
                    }
                }
                // ユーザID
                workInfo.setUserId(param.getUserId());
                // 端末No.
                workInfo.setTerminalNo(param.getTerminalNo());
                // 作業日
                workInfo.setWorkDay(workDay);

                // PCT作業情報完了処理
                wkInfoCtrl.completePCTRetWorkInfo(param, workInfo, statusFlag, createResultWorkInfo(param, jobNo,
                        planQty, resultQty, shortageQty, restResultQty));
            }
        }
        catch (NotFoundException e)
        {
            // 他端末で更新済み
            OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            opex.setErrorLineNo(param.getRowNo());
            throw opex;
        }
        catch (DataExistsException e)
        {
            // 他端末で更新済み
            OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            opex.setErrorLineNo(param.getRowNo());
            throw opex;
        }
    }

    /**
     * 集約作業完了<BR>
     * <BR>
     * パラメータの集約作業No.に該当するデータに対してPCT出庫作業情報を検索し完了処理を行います。<BR>
     * パラメータの実績数、作業時間を分配し、予定情報、作業情報、在庫情報を更新します。<BR>
     * 
     * @param param PCT出庫入力パラメータ
     * @param workDay 作業日
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws InvalidDefineException 指定パラメータが異常であったときスローされます。
     * @throws OperatorException 完了処理で問題があったときスローされます。
     * @throws ScheduleException 集約作業完了でエラーが発生した場合にスローされます。
     * @throws LockTimeOutException ロックが獲得できなかった場合にスローされます。
     */
    protected void completeCollectWorkData(PCTRetrievalInParameter param, String workDay)
            throws ReadWriteException,
                NoPrimaryException,
                InvalidDefineException,
                OperatorException,
                ScheduleException,
                LockTimeOutException
    {
        // 残実績数
        int restResultQty = param.getResultQty();
        // 実績数
        int resultQty = 0;
        // 予定数
        int planQty = 0;
        // 欠品数
        int shortageQty = 0;
        // 状態フラグ
        String statusFlag = null;

        // PCT出庫エンティティ配列の取得
        PCTRetWorkInfo[] workInfos = searchWorkInfoByCollectJobNo(param);
        // PCT出庫作業情報コントローラ
        PCTRetWorkController wkInfoCtrl = new PCTRetWorkController(getConnection(), getCaller());

        try
        {
            for (int i = 0; i < workInfos.length; i++)
            {
                int updateQty = restResultQty;

                PCTRetWorkInfo workInfo = workInfos[i];

                if (i < workInfos.length - 1 && updateQty > workInfo.getPlanQty())
                {
                    updateQty = workInfo.getPlanQty();
                }

                restResultQty -= updateQty;

                // 予定数
                planQty = workInfo.getPlanQty();
                // 実績数
                resultQty = updateQty;
                // 欠品数
                shortageQty = workInfo.getPlanQty() - updateQty;
                // 状態フラグ：メンテ完了
                statusFlag = PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION;
                // ユーザID
                workInfo.setUserId(param.getUserId());
                // 端末No.
                workInfo.setTerminalNo(param.getTerminalNo());
                // 作業日
                workInfo.setWorkDay(workDay);

                // PCT作業情報完了処理
                wkInfoCtrl.completePCTRetWorkInfo(param, workInfo, statusFlag, createResultWorkInfo(param,
                        workInfo.getJobNo(), planQty, resultQty, shortageQty, restResultQty));
            }
        }
        catch (NotFoundException e)
        {
            // 他端末で更新済み
            OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            opex.setErrorLineNo(param.getRowNo());
            throw opex;
        }
        catch (DataExistsException e)
        {
            // 他端末で更新済み
            OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            opex.setErrorLineNo(param.getRowNo());
            throw opex;
        }
    }

    /**
     * 集約作業完了<BR>
     * <BR>
     * パラメータの集約作業No.に該当するデータに対してPCT出庫作業情報を検索し完了処理を行います。<BR>
     * パラメータの実績数、作業時間を分配し、予定情報、作業情報、在庫情報を更新します。<BR>
     * 
     * @param param PCT出庫入力パラメータ
     * @param workData 出庫作業データ
     * @param workDay 作業日
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws InvalidDefineException 指定パラメータが異常であったときスローされます。
     * @throws OperatorException 完了処理で問題があったときスローされます。
     * @throws ScheduleException 集約作業完了でエラーが発生した場合にスローされます。
     * @throws LockTimeOutException ロックが獲得できなかった場合にスローされます。
     */
    protected void completeAllWorkData(PCTRetrievalInParameter param, PCTRetWorkInfo[] workData, String workDay)
            throws ReadWriteException,
                NoPrimaryException,
                InvalidDefineException,
                OperatorException,
                ScheduleException,
                LockTimeOutException
    {
        // PCT出庫作業情報コントローラ
        PCTRetWorkController wkInfoCtrl = new PCTRetWorkController(getConnection(), getCaller());
        // オーダー情報コントローラーハンドラを定義する
        PCTOrderInfoController osc = new PCTOrderInfoController(getConnection(), getCaller());
        // 出庫実績送信情報コントローラーハンドラを定義する
        PCTRetHostSendController hsc = new PCTRetHostSendController(getConnection(), getCaller());
        // PCT出庫予定情報コントローラ
        PCTRetPlanController retPlanCtrl = new PCTRetPlanController(getConnection(), getCaller());

        try
        {
            for (int i = 0; i < workData.length; i++)
            {
                PCTRetWorkInfo workInfo = workData[i];
                // ユーザID
                workInfo.setUserId(param.getUserId());
                // 端末No.
                workInfo.setTerminalNo(param.getTerminalNo());
                // 作業日
                workInfo.setWorkDay(workDay);

                // 処理フラグが削除の場合
                if (PCTRetrievalInParameter.PROCESSING_DIVISION_DELETE.equals(param.getProcessingDivision()))
                {
                    // PCTオーダー情報を削除する
                    osc.deletePCTOrderInfoAll(workInfo);
                    // PCT出庫実績送信情報を削除する
                    hsc.deletePCTRetHostsend(workInfo);
                    // PCT出庫予定情報を削除する
                    retPlanCtrl.deleteAllPlan(workInfo, param);
                    // PCT出庫作業情報を削除する
                    wkInfoCtrl.deletePCTRetWorkInfoAll(workInfo);
                }
                // 処理フラグが全完了・全欠品完了
                else
                {
                    // PCT出庫作業情報を取得
                    PCTRetWorkInfo[] infos = wkInfoCtrl.getWorkInfo(workInfo);

                    for (int j = 0; j < infos.length; j++)
                    {
                        PCTRetWorkInfo info = infos[j];

                        // PCT出庫予定情報を更新する
                        retPlanCtrl.completeAllPlan(workInfo, info, param);
                    }
                    // PCT出庫作業情報を更新する
                    wkInfoCtrl.updatePCTRetWorkInfoAll(workInfo, param);
                }
            }
        }
        catch (NotFoundException e)
        {
            // 他端末で更新済み
            OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            opex.setErrorLineNo(param.getRowNo());
            throw opex;
        }
        catch (DataExistsException e)
        {
            // 他端末で更新済み
            OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            opex.setErrorLineNo(param.getRowNo());
            throw opex;
        }
    }

    /**
     * 集約予定作業完了<BR>
     * <BR>
     * パラメータの集約作業No.に該当するデータに対してPCT出庫作業情報を検索し完了処理を行います。<BR>
     * パラメータの実績数、作業時間を分配し、予定情報、作業情報、在庫情報を更新します。<BR>
     * 
     * @param param   PCT出庫入力パラメータ
     * @param workDay 作業日
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws InvalidDefineException 指定パラメータが異常であったときスローされます。
     * @throws OperatorException 完了処理で問題があったときスローされます。
     * @throws ScheduleException 集約作業完了でエラーが発生した場合にスローされます。
     * @throws LockTimeOutException ロックが獲得できなかった場合にスローされます。
     */
    protected void completePlanData(PCTRetrievalInParameter param, String workDay)
            throws ReadWriteException,
                NoPrimaryException,
                InvalidDefineException,
                OperatorException,
                ScheduleException,
                LockTimeOutException
    {
        // 実績数
        int resultQty = 0;
        // 欠品数
        int shortageQty = 0;
        // 状態フラグ
        String statusFlag = null;

        // PCT庫予定情報エンティティ配列の取得
        PCTRetPlan[] plans = searchPlanByJobNo(param);
        // PCT出庫予定情報コントローラ
        PCTRetPlanController retPlanCtrl = new PCTRetPlanController(getConnection(), getCaller());

        try
        {
            // PCT予定情報の更新
            for (int i = 0; i < plans.length; i++)
            {
                PCTRetPlan plan = plans[i];

                // 処理フラグが削除の場合
                if (PCTRetrievalInParameter.PROCESSING_DIVISION_DELETE.equals(param.getProcessingDivision()))
                {
                    // PCT予定情報削除
                    retPlanCtrl.deletePlan(param, plan);
                }
                else
                {
                    // 作業メンテナンス一括の場合
                    if (PCTRetrievalInParameter.STATUS_FLAG_UNWORK.equals(param.getStatusFlag1()))
                    {
                        // 処理フラグが全完了の場合
                        if (PCTRetrievalInParameter.PROCESSING_DIVISION_COMPLETION.equals(param.getProcessingDivision()))
                        {
                            // 実績数
                            resultQty = plan.getPlanQty();
                            // 欠品数
                            shortageQty = plan.getShortageQty();
                            // 状態フラグ：完了
                            statusFlag = PCTRetrievalInParameter.STATUS_FLAG_COMPLETION;
                        }
                        // 処理フラグが全欠品完了の場合
                        else if (PCTRetrievalInParameter.PROCESSING_DIVISION_STOCKOUT.equals(param.getProcessingDivision()))
                        {
                            // 実績数
                            resultQty = plan.getResultQty();
                            // 欠品数
                            shortageQty = plan.getPlanQty();
                            // 状態フラグ：完了
                            statusFlag = PCTRetrievalInParameter.STATUS_FLAG_COMPLETION;
                        }
                    }
                    // 作業メンテナンスの場合
                    // 修正登録処理を行う
                    else
                    {
                        // 実績数
                        resultQty = getResultQty(plan.getPlanUkey());
                        // 欠品数の計算
                        shortageQty = plan.getPlanQty() - resultQty;
                    }

                    // PCT予定情報完了
                    retPlanCtrl.completePlan(plan.getPlanUkey(), resultQty, shortageQty, statusFlag, workDay);
                }
            }
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 集約予定作業完了<BR>
     * <BR>
     * パラメータの集約作業No.に該当するデータに対してPCT出庫作業情報を検索し完了処理を行います。<BR>
     * パラメータの実績数、作業時間を分配し、予定情報、作業情報、在庫情報を更新します。<BR>
     * 
     * @param param   PCT出庫入力パラメータ
     * @param workDay 作業日
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws InvalidDefineException 指定パラメータが異常であったときスローされます。
     * @throws OperatorException 完了処理で問題があったときスローされます。
     * @throws ScheduleException 集約作業完了でエラーが発生した場合にスローされます。
     * @throws LockTimeOutException ロックが獲得できなかった場合にスローされます。
     */
    protected void completeCollectPlanData(PCTRetrievalInParameter param, String workDay)
            throws ReadWriteException,
                NoPrimaryException,
                InvalidDefineException,
                OperatorException,
                ScheduleException,
                LockTimeOutException
    {
        // 実績数
        int resultQty = 0;
        // 欠品数
        int shortageQty = 0;
        // 状態フラグ
        String statusFlag = null;

        // PCT庫予定情報エンティティ配列の取得
        PCTRetWorkInfo[] workInfos = searchPlanByCollectJobNo(param);
        // PCT出庫予定情報コントローラ
        PCTRetPlanController retPlanCtrl = new PCTRetPlanController(getConnection(), getCaller());

        try
        {
            // PCT予定情報の更新
            for (int i = 0; i < workInfos.length; i++)
            {
                PCTRetWorkInfo workInfo = workInfos[i];

                // 実績数
                resultQty = getResultQty(workInfo.getPlanUkey());
                // 欠品数の計算
                shortageQty = getPlanQty(workInfo.getPlanUkey()) - resultQty;
                // PCT予定情報完了
                retPlanCtrl.completePlan(workInfo.getPlanUkey(), resultQty, shortageQty, statusFlag, workDay);
            }
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 作業No.を条件にPCT作業情報エンティティを取得します。<BR>
     * <BR>
     * @param param 出庫入力パラメータ
     * @return PCT出庫作業情報エンティティ配列
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws OperatorException データが存在しない場合にスローされます。
     */
    protected PCTRetWorkInfo[] searchWorkInfoByJobNo(PCTRetrievalInParameter param)
            throws ReadWriteException,
                OperatorException
    {
        // PCT出庫作業情報ハンドラ
        PCTRetWorkInfoHandler wkInfoHdlr = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfoSearchKey searchKey = new PCTRetWorkInfoSearchKey();

        // 作業No.
        searchKey.setJobNo(param.getJobNo());

        // 作業メンテナンス一括ではない場合のみPCT出庫実績送信情報と結合を行う
        if (!PCTRetrievalInParameter.STATUS_FLAG_UNWORK.equals(param.getStatusFlag1()))
        {
            // PCT出庫作業情報.作業No.とPCT出庫作業実績送信情報.作業No.が同一
            searchKey.setJoin(PCTRetWorkInfo.JOB_NO, PCTRetHostSend.JOB_NO);
            // PCT出庫作業実績送信情報.実績報告フラグ（0：未報告）
            searchKey.setKey(PCTRetHostSend.REPORT_FLAG, PCTRetHostSend.REPORT_FLAG_NOT_REPORT);
            // 状態フラグが4：完了または、5：メンテ完了
            searchKey.setStatusFlag(new String[] {
                    PCTRetrievalInParameter.STATUS_FLAG_COMPLETION,
                    PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION
            }, false);
        }

        // 予定一意キー順
        searchKey.setPlanUkeyOrder(true);
        // 作業No.順
        searchKey.setJobNoOrder(true);
        // PCT出庫作業情報の検索
        PCTRetWorkInfo[] workInfos = (PCTRetWorkInfo[])wkInfoHdlr.find(searchKey);

        // データ無しの場合
        if (ArrayUtil.isEmpty(workInfos))
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
        return workInfos;
    }

    /**
     * 作業No.を条件にPCT作業情報エンティティを取得します。<BR>
     * <BR>
     * @param param 出庫入力パラメータ
     * @return PCT出庫作業情報エンティティ配列
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws OperatorException データが存在しない場合にスローされます。
     */
    protected PCTRetWorkInfo[] searchWorkInfoByCollectJobNo(PCTRetrievalInParameter param)
            throws ReadWriteException,
                OperatorException
    {
        // PCT出庫作業情報ハンドラ
        PCTRetWorkInfoHandler wkInfoHdlr = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfoSearchKey searchKey = new PCTRetWorkInfoSearchKey();

        // 作業No.
        String[] jobNo = new String[param.getJobNoList().size()];
        for (int i = 0; i < param.getJobNoList().size(); i++)
        {
            jobNo[i] = param.getJobNoList().get(i);
        }
        searchKey.setJobNo(jobNo, true);

        // 作業メンテナンス一括ではない場合のみPCT出庫実績送信情報と結合を行う
        if (!PCTRetrievalInParameter.STATUS_FLAG_UNWORK.equals(param.getStatusFlag1()))
        {
            // PCT出庫作業情報.作業No.とPCT出庫作業実績送信情報.作業No.が同一
            searchKey.setJoin(PCTRetWorkInfo.JOB_NO, PCTRetHostSend.JOB_NO);
            // PCT出庫作業実績送信情報.実績報告フラグ（0：未報告）
            searchKey.setKey(PCTRetHostSend.REPORT_FLAG, PCTRetHostSend.REPORT_FLAG_NOT_REPORT);
            // 状態フラグが4：完了または、5：メンテ完了
            searchKey.setStatusFlag(new String[] {
                    PCTRetrievalInParameter.STATUS_FLAG_COMPLETION,
                    PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION
            }, false);
        }

        // 伝票No.順
        searchKey.setShipTicketNoOrder(true);
        // PCT出庫作業情報の検索
        PCTRetWorkInfo[] workInfos = (PCTRetWorkInfo[])wkInfoHdlr.find(searchKey);

        // データ無しの場合
        if (ArrayUtil.isEmpty(workInfos))
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
        return workInfos;
    }

    /**
     * 集約作業No.を条件にPCT作業情報エンティティを取得します。<BR>
     * <BR>
     * @param param 出庫入力パラメータ
     * @return PCT出庫作業情報エンティティ配列
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws OperatorException データが存在しない場合にスローされます。
     */
    protected PCTRetPlan[] searchPlanByJobNo(PCTRetrievalInParameter param)
            throws ReadWriteException,
                OperatorException
    {
        // PCT出庫予定情報ハンドラ
        PCTRetPlanSearchKey searchKey = new PCTRetPlanSearchKey();
        PCTRetPlanHandler planHdlr = new PCTRetPlanHandler(getConnection());

        // 予定一意キー順
        searchKey.setPlanUkey(param.getPlanUkey());
        // PCT出庫作業情報の検索
        PCTRetPlan[] retPlan = (PCTRetPlan[])planHdlr.find(searchKey);

        // データ無しの場合
        if (ArrayUtil.isEmpty(retPlan))
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
        return retPlan;
    }

    /**
     * 集約作業No.を条件にPCT作業情報エンティティを取得します。<BR>
     * <BR>
     * @param param 出庫入力パラメータ
     * @return PCT出庫作業情報エンティティ配列
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws OperatorException データが存在しない場合にスローされます。
     */
    protected PCTRetWorkInfo[] searchPlanByCollectJobNo(PCTRetrievalInParameter param)
            throws ReadWriteException,
                OperatorException
    {
        // PCT出庫予定情報ハンドラ
        PCTRetWorkInfoSearchKey searchKey = new PCTRetWorkInfoSearchKey();
        PCTRetWorkInfoHandler workHdlr = new PCTRetWorkInfoHandler(getConnection());

        // 作業No.
        String[] jobNo = new String[param.getJobNoList().size()];
        for (int i = 0; i < param.getJobNoList().size(); i++)
        {
            jobNo[i] = param.getJobNoList().get(i);
        }
        searchKey.setJobNo(jobNo, true);

        // 予定一意キー
        searchKey.setPlanUkeyGroup();
        searchKey.setPlanUkeyCollect();
        // 予定数
        searchKey.setPlanQtyCollect("SUM");
        // PCT出庫作業情報の検索
        PCTRetWorkInfo[] retWorkInfo = (PCTRetWorkInfo[])workHdlr.find(searchKey);

        // データ無しの場合
        if (ArrayUtil.isEmpty(retWorkInfo))
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
        return retWorkInfo;
    }

    /**
     * PCT出庫作業情報テーブル更新用のエンティティを作成します。<BR>
     * <BR>
     * @param param 出庫入力パラメータ
     * @param jobNo 作業No.
     * @param planQty 予定数
     * @param resultQty 実績数
     * @param shortageQty 欠品数
     * @param restResultQty 残実績数
     * @return PCT出庫作業情報テーブル更新用のエンティティ
     */
    protected PCTRetWorkInfo createResultWorkInfo(PCTRetrievalInParameter param, String jobNo, int planQty,
            int resultQty, int shortageQty, int restResultQty)
    {
        PCTRetWorkInfo workInfo = new PCTRetWorkInfo();

        // 作業No.
        workInfo.setJobNo(jobNo);
        // 予定数
        workInfo.setPlanQty(planQty);
        // 実績数
        workInfo.setResultQty(resultQty);
        // 欠品数 =  
        workInfo.setShortageQty(shortageQty);

        return workInfo;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 予定一意キーより、PCT出庫作業情報の実績数を取得します。<BR>
     * <BR>
     * @param planUkey 予定一意キー
     * @return 実績数
     * @throws ReadWriteException DBエラー
     */
    private int getResultQty(String planUkey)
            throws ReadWriteException
    {
        // PCT出庫作業情報ハンドラ
        PCTRetWorkInfoHandler wkInfoHdlr = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfoSearchKey searchKey = new PCTRetWorkInfoSearchKey();

        // 予定一意キー
        searchKey.setPlanUkey(planUkey);
        // 取得項目
        searchKey.setResultQtyCollect("SUM");
        // PCT出庫作業情報の検索
        PCTRetWorkInfo[] workInfos = (PCTRetWorkInfo[])wkInfoHdlr.find(searchKey);

        // データ無しの場合
        if (ArrayUtil.isEmpty(workInfos))
        {
            return 0;
        }
        return workInfos[0].getResultQty();
    }

    /**
     * 予定一意キーより、PCT出庫予定情報の予定数を取得します。<BR>
     * <BR>
     * @param planUkey 予定一意キー
     * @return 予定数
     * @throws ReadWriteException DBエラー
     */
    private int getPlanQty(String planUkey)
            throws ReadWriteException
    {
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler planHdlr = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey searchKey = new PCTRetPlanSearchKey();

        // 予定一意キー
        searchKey.setPlanUkey(planUkey);
        // PCT出庫予定情報の検索
        PCTRetPlan[] plans = (PCTRetPlan[])planHdlr.find(searchKey);

        // データ無しの場合
        if (ArrayUtil.isEmpty(plans))
        {
            return 0;
        }
        return plans[0].getPlanQty();
    }


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PCTRetOperator.java 4403 2009-06-08 03:49:16Z ota $";
    }
}

// $Id: WorkerResultController.java 5028 2009-09-18 04:31:29Z kishimoto $
package jp.co.daifuku.wms.base.controller;

import java.math.BigDecimal;
import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.WorkerResult;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 作業者別RFT実績情報コントローラクラスです。
 *
 *
 * @version $Revision: 5028 $, $Date: 2009-09-18 13:31:29 +0900 (金, 18 9 2009) $
 * @author  073019
 * @author  Last commit: $Author: kishimoto $
 */
public class WorkerResultController
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


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public WorkerResultController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 作業者別RFT実績情報作成処理を行います。<BR>
     * パラメータの項目に該当するデータに対して開始フラグがfalseの場合は作業者別RFT実績情報を更新する。<BR>
     * データが存在しなかった場合は登録を行う。
     * 
     * @param paramWorkerResult 作業者別RFT実績情報
     * @param startFlag 開始フラグ
     * <ul>
     * <li>true  : 開始時の実績情報作成(データ存在時に更新しない)
     * <li>false : 完了時の実績情報作成(データ存在時に更新する)
     * </ul>
     * @throws ReadWriteException データベース処理でエラーが発生した場合にスローされます。
     * @throws ScheduleException 予期しない例外が発生した場合にスローされます。
     * @throws DataExistsException 既にデータが登録済みの場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在した場合にスローされます。
     * @throws NotFoundException 更新対象が存在しない場合にスローされます。
     */
    public void create(WorkerResult paramWorkerResult, boolean startFlag)
            throws ReadWriteException,
                ScheduleException,
                DataExistsException,
                NoPrimaryException,
                NotFoundException
    {
        WorkerResultSearchKey key = new WorkerResultSearchKey();

        key.setWorkDay(paramWorkerResult.getWorkDay()); // 作業日
        key.setUserId(paramWorkerResult.getUserId()); // ユーザID
        key.setTerminalNo(paramWorkerResult.getTerminalNo()); // RFTNo.
        key.setJobType(paramWorkerResult.getJobType()); // 作業区分

        WorkerResult workerResult = (WorkerResult)new WorkerResultHandler(getConnection()).findPrimary(key);

        if (workerResult == null)
        {
            // 作業数量の最大値を超えている場合
            if (paramWorkerResult.getWorkQty() > WmsParam.WORKER_MAX_TOTAL_QTY)
            {
                paramWorkerResult.setWorkQty(WmsParam.WORKER_MAX_TOTAL_QTY); // 作業数量
            }
            insert(paramWorkerResult);
        }
        else
        {
            if (!startFlag)
            {
                // 作業数量の最大値を超えるような場合
                if ((paramWorkerResult.getWorkQty() + workerResult.getWorkQty()) > WmsParam.WORKER_MAX_TOTAL_QTY)
                {
                    paramWorkerResult.setWorkQty(WmsParam.WORKER_MAX_TOTAL_QTY - workerResult.getWorkQty()); // 作業数量
                }
                update(paramWorkerResult);
            }
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
     * 作業者別RFT実績情報登録処理を行います。<BR>
     * パラメータの作業者別RFT実績情報の登録処理を行う。<BR>
     * 作業開始日時にシステム日付をセットし、数量、回数、時間は0で登録する。
     * 
     * @param workerResult 作業者別RFT実績情報エンティティ
     * @throws ReadWriteException データベース処理でエラーが発生した場合にスローされます。
     * @throws NoPrimaryException ログインユーザ設定情報が複数件あった場合にスローされます。
     * @throws DataExistsException 既にデータが登録済みの場合にスローされます。
     * @throws ScheduleException システム定義情報が存在しない、もしくは複数件存在する場合にスローされます。
     */
    protected void insert(WorkerResult workerResult)
            throws ReadWriteException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        // 作業日取得
        String workDay = workerResult.getWorkDay();
        // 作業日が未設定の場合はシステム定義情報の作業日を使用する
        if (StringUtil.isBlank(workDay))
        {
            workDay = new WarenaviSystemController(getConnection(), getCaller()).getWorkDay();
        }

        // ログインユーザ設定情報を検索しユーザ名を取得する
        String userId = workerResult.getUserId();
        String userName = getUserName(userId);

        if (!StringUtil.isBlank(userName))
        {
            workerResult.setUserName(userName); // ユーザ名称
        }
        workerResult.setWorkDay(workDay); // 作業日

        // 作業者別RFT実績情報登録
        new WorkerResultHandler(getConnection()).create(workerResult);
    }

    /**
     * ユーザ情報テーブルからユーザ名を取得して返します。
     * 
     * @param userId ユーザID
     * @return ユーザ名
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NoPrimaryException 該当ユーザIDが複数存在する
     */
    protected String getUserName(String userId)
            throws ReadWriteException,
                NoPrimaryException
    {
        Com_loginuserHandler userH = new Com_loginuserHandler(getConnection());
        Com_loginuserSearchKey   userKey = new Com_loginuserSearchKey();
        userKey.setUsernameCollect();
        userKey.setUserid(userId);

        Com_loginuser user = (Com_loginuser)userH.findPrimary(userKey);
        if (null == user)
        {
            return null;
        }
        return user.getUsername();
    }

    /**
     * 作業者別RFT実績情報更新<BR>
     * パラメータの作業者別RFT実績情報の作業日、ユーザID、RFTNo.作業区分に該当するデータに対して更新処理を行う。<BR>
     * 数量、回数、時間を加算する。<BR>
     * <BR>
     * 
     * @param workerResult 作業者別RFT実績情報エンティティ
     * @throws ReadWriteException 該当データが存在しない場合にスローされます。
     * @throws NotFoundException RFT管理情報が複数件あった場合にスローされます。
     * @throws NoPrimaryException RFT管理情報が複数件あった場合にスローされます。
     */
    protected void update(WorkerResult workerResult)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException
    {
        WorkerResultAlterKey key = new WorkerResultAlterKey();

        // ログインユーザ設定情報を検索しユーザ名を取得する
        String userId = workerResult.getUserId();
        String userName = getUserName(userId);

        /* 更新値を設定する */
        if (!StringUtil.isBlank(userName))
        {
            // ユーザ名称
            key.updateUserName(userName);
        }
        // 作業数量
        key.updateWorkQtyWithColumn(WorkerResult.WORK_QTY, new BigDecimal(workerResult.getWorkQty()));
        // 作業回数(明細数)
        key.updateWorkCntWithColumn(WorkerResult.WORK_CNT, new BigDecimal(workerResult.getWorkCnt()));
        // 作業回数(作業オーダー数)
        key.updateOrderCntWithColumn(WorkerResult.ORDER_CNT, new BigDecimal(workerResult.getOrderCnt()));
        // 作業時間(秒)
        key.updateWorkTimeWithColumn(WorkerResult.WORK_TIME, new BigDecimal(workerResult.getWorkTime()));
        // 休憩時間(秒)
        key.updateRestTimeWithColumn(WorkerResult.REST_TIME, new BigDecimal(workerResult.getRestTime()));
        // 実作業時間(秒)
        key.updateRealWorkTimeWithColumn(WorkerResult.REAL_WORK_TIME, new BigDecimal(workerResult.getRealWorkTime()));
        // ミススキャン数
        key.updateMissScanCntWithColumn(WorkerResult.MISS_SCAN_CNT, new BigDecimal(workerResult.getMissScanCnt()));

        /* 更新条件を設定する */

        // 作業日
        key.setWorkDay(workerResult.getWorkDay());
        // ユーザID
        key.setUserId(workerResult.getUserId());
        // RFTNo.
        key.setTerminalNo(workerResult.getTerminalNo());
        // 作業区分
        key.setJobType(workerResult.getJobType());

        // 作業者別RFT実績情報更新
        new WorkerResultHandler(getConnection()).modify(key);
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
        return "$Id: WorkerResultController.java 5028 2009-09-18 04:31:29Z kishimoto $";
    }
}

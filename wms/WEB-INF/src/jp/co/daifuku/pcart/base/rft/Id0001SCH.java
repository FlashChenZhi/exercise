// $Id: Id0001SCH.java 4181 2009-04-21 00:14:17Z rnakai $
package jp.co.daifuku.pcart.base.rft;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.controller.WorkerResultController;
import jp.co.daifuku.wms.base.dbhandler.RftAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.rft.AbstractIdSCH;
import jp.co.daifuku.wms.base.rft.IdSchException;
import jp.co.daifuku.wms.base.util.DbDateUtil;


/**
 * RFTシステム報告スケジュール処理<br>
 * RFTシステム報告処理を実装する。
 *
 * @version $Revision: 4181 $, $Date: 2009-04-21 09:14:17 +0900 (火, 21 4 2009) $
 * @author  taki
 * @author  Last commit: $Author: rnakai $
 */


public class Id0001SCH
        extends AbstractIdSCH
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
     * コンストラクタ
     */
    public Id0001SCH()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /** システム報告スケジュール
     * 
     * @param rftNo             rftNo.
     * @param statusFlag        状態フラグ
     * @param toRest            休憩区分(true=休憩)
     * @param toRestart         再開区分(true=再開)
     * @param terminalType      端末区分
     * @param ipAddress         端末IPアドレス
     * @throws CommonException  例外が発生した場合に通知されます。
     */
    public void reportSystemSCH(String rftNo, String statusFlag, boolean toRest, boolean toRestart,
            String terminalType, String ipAddress)
            throws CommonException
    {
        // 号機番号をチェックする
        RftSearchKey key = new RftSearchKey();
        key.setRftNo(rftNo);
        Rft rft = (Rft)new RftHandler(_wConn).findPrimary(key);
        if (rft == null)
        {
            throw new IdSchException(IdSchException.ILLEGAL_TERMINAL_NUMBER);
        }

        /* 異常状態をチェックする */
        // 起動中に、起動を受信した場合
        if (SystemDefine.RFT_STATUS_FLAG_START.equals(rft.getStatusFlag()))
        {
            if (SystemDefine.RFT_STATUS_FLAG_START.equals(statusFlag))
            {
                throw new IdSchException(IdSchException.ALREADY_STARTED);
            }
        }
        // 停止中に、終了を受信した場合
        else if (SystemDefine.RFT_STATUS_FLAG_STOP.equals(rft.getStatusFlag()))
        {
            if (SystemDefine.RFT_STATUS_FLAG_STOP.equals(statusFlag))
            {
                throw new IdSchException(IdSchException.ALREADY_STOPPED);
            }
        }
        // 休憩開始日時がセットされている状態で、休憩を受信した場合
        else if (rft.getRestStartDate() != null && toRest)
        {
            throw new IdSchException(IdSchException.ALREADY_RESTED);
        }
        // 休憩開始日時がセットされていない状態で、再開を受信した場合
        else if (rft.getRestStartDate() == null && toRestart)
        {
            throw new IdSchException(IdSchException.ALREADY_RESTARTED);
        }


        // 休憩開始時間 保持変数
        Date restStartTime = new Date();

        // RFT管理情報の更新
        RftAlterKey akey = new RftAlterKey();

        akey.setRftNo(rftNo);
        if (SystemDefine.RFT_STATUS_FLAG_START.equals(statusFlag))
        {
            akey.updateStatusFlag(SystemDefine.RFT_STATUS_FLAG_START);
            akey.updateTerminalType(terminalType);
            akey.updateIpAddress(ipAddress);
        }
        else if (SystemDefine.RFT_STATUS_FLAG_STOP.equals(statusFlag))
        {
            akey.updateStatusFlag(SystemDefine.RFT_STATUS_FLAG_STOP);
        }
        else if (toRest)
        {
            akey.updateRestStartDate(DbDateUtil.getTimeStamp());
        }
        else if (toRestart)
        {
            restStartTime = rft.getRestStartDate();
            if (restStartTime == null)
            {
                // 上でチェックしてるので、この処理は通らないはず。
                throw new ScheduleException();
            }
            akey.updateRestStartDate(null);
        }
        akey.updateRadioFlag(Rft.RADIO_FLAG_IN);
        akey.updateLastUpdatePname(getClass().getSimpleName());
        new RftHandler(_wConn).modify(akey);


        //作業者別RFT実績情報の更新(報告区分が「再開」の場合のみ)
        if (toRestart)
        {
            // 休憩時間（秒数）
            long restSeconds = (long)(DbDateUtil.getTimeStamp().getTime() - restStartTime.getTime()) / 1000;
            // システム定義情報コントローラ 定義
            WarenaviSystemController warenaviSystem = new WarenaviSystemController(_wConn, getClass());

            // 作業者RFT別実績情報のエンティティを定義して、データをセット
            WorkerResult workerResult = new WorkerResult();
            workerResult.setTerminalNo(rft.getRftNo());
            workerResult.setUserId(rft.getUserId());
            workerResult.setWorkDay(warenaviSystem.getWorkDay());
            workerResult.setJobType(rft.getJobType());
            workerResult.setRestTime((int)restSeconds);
            // 作業者別実績情報コントローラを使って、作業者別RFT実績情報の更新。
            WorkerResultController workerResultCon = new WorkerResultController(_wConn, getClass());
            workerResultCon.create(workerResult, false);
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
        return "$Id: Id0001SCH.java 4181 2009-04-21 00:14:17Z rnakai $";
    }
}
//end of class

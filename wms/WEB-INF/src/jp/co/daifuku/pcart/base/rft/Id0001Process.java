// $Id: Id0001Process.java 4181 2009-04-21 00:14:17Z rnakai $
package jp.co.daifuku.pcart.base.rft;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.communication.rft.IdProcess;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.rft.IdExceptionHandler;
import jp.co.daifuku.wms.base.rft.IdSchException;
import jp.co.daifuku.wms.base.util.DbDateUtil;


/**
 * RFTからのシステム報告(ID0001)に対し、受信電文の分解、応答電文の組立を行う。 <BR>
 *
 * @version $Revision: 4181 $, $Date: 2009-04-21 09:14:17 +0900 (火, 21 4 2009) $
 * @author  taki
 * @author  Last commit: $Author: rnakai $
 */

public class Id0001Process
        extends IdProcess
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
     * コンストラクタ<BR>
     */
    public Id0001Process()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     *  システム報告処理
     * @param rdt 受信電文
     * @param sdt 送信電文
     * @throws Exception 何らかの例外が発生した場合に通知されます。
     * @see jp.co.daifuku.wms.base.communication.rft.IdProcess#processReceivedId(byte[], byte[])
     */
    @Override
    public void processReceivedId(byte[] rdt, byte[] sdt)
            throws Exception
    {
        // 要求電文解析用のインスタンスを生成
        RftId0001 rftId0001 = (RftId0001)PackageManager.getObject("RftId0001", getClass());
        rftId0001.setReceiveMessage(rdt);

        // 応答電文解析用のインスタンスを生成
        RftId5001 rftId5001 = (RftId5001)PackageManager.getObject("RftId5001", getClass());

        try
        {
            // 要求電文の内容を入力パラメータクラスにセット
            String statusFlag = new String();
            boolean toRest = false;
            boolean toRestart = false;
            if (RftId0001.REPORT_FLAG_START.equals(rftId0001.getReportFlag()))
            {
                statusFlag = SystemDefine.RFT_STATUS_FLAG_START;
            }
            else if (RftId0001.REPORT_FLAG_STOP.equals(rftId0001.getReportFlag()))
            {
                statusFlag = SystemDefine.RFT_STATUS_FLAG_STOP;
            }
            else if (RftId0001.REPORT_FLAG_REST.equals(rftId0001.getReportFlag()))
            {
                toRest = true;
            }
            else if (RftId0001.REPORT_FLAG_REPENDING.equals(rftId0001.getReportFlag()))
            {
                toRestart = true;
            }

            // スケジュールクラスのインスタンスを生成
            Id0001SCH id0001sch = (Id0001SCH)PackageManager.getObject("Id0001SCH", getClass());
            id0001sch.setConnection(_wConn);

            // スケジュールのシステム報告メソッドを呼び出す
            id0001sch.reportSystemSCH(rftId0001.getRftNo(), statusFlag, toRest, toRestart, rftId0001.getTerminalType(),
                    rftId0001.getIpAddress());

            // 応答フラグとエラー詳細をセット
            rftId5001.setAnsCode(RftId5001.ANS_CODE_NORMAL);
            rftId5001.setErrDetails(RftId5001.ErrorDetails.NORMAL);

        }
        catch (OperatorException ex)
        {
            List<String> answer = IdExceptionHandler.getResponceFlag(ex, getClass(), rftId0001.getRftNo());
            rftId5001.setAnsCode(answer.get(0));
            rftId5001.setErrDetails(answer.get(1));
        }
        catch (IdSchException ex)
        {
            String errCode = ex.getErrorCode();
            // 起動時起動受信
            if (IdSchException.ALREADY_STARTED.equals(errCode))
            {
                // 応答フラグとエラー詳細をセット
                rftId5001.setAnsCode(RftId5001.ANS_CODE_NORMAL);
                rftId5001.setErrDetails(RftId5001.ErrorDetails.ALREADY_STARTED);
            }
            // 未起動時終了受信
            else if (IdSchException.ALREADY_STOPPED.equals(errCode))
            {
                // 応答フラグとエラー詳細をセット
                rftId5001.setAnsCode(RftId5001.ANS_CODE_NORMAL);
                rftId5001.setErrDetails(RftId5001.ErrorDetails.NON_START_FINISH);
            }
            // 休憩時休憩受信
            else if (IdSchException.ALREADY_RESTED.equals(errCode))
            {
                // 応答フラグとエラー詳細をセット
                rftId5001.setAnsCode(RftId5001.ANS_CODE_NORMAL);
                rftId5001.setErrDetails(RftId5001.ErrorDetails.ALREADY_RESTED);
            }
            // 未休憩時再開受信
            else if (IdSchException.ALREADY_RESTARTED.equals(errCode))
            {
                // 応答フラグとエラー詳細をセット
                rftId5001.setAnsCode(RftId5001.ANS_CODE_ERROR);
                rftId5001.setErrDetails(RftId5001.ErrorDetails.UPDATE_FINISH);
            }
            // 号機No不正
            else if (IdSchException.ILLEGAL_TERMINAL_NUMBER.equals(errCode))
            {
                // 応答フラグとエラー詳細をセット
                rftId5001.setAnsCode(RftId5001.ANS_CODE_ERROR);
                rftId5001.setErrDetails(RftId5001.ErrorDetails.ILLEGAL_TERMINAL_NUMBER);
            }
            // 想定外のIdSchException発生時
            else
            {
                List<String> answer = IdExceptionHandler.getResponceFlag(ex, getClass(), rftId0001.getRftNo());
                rftId5001.setAnsCode(answer.get(0));
                rftId5001.setErrDetails(answer.get(1));
            }
        }
        catch (Exception ex)
        {
            List<String> answer = IdExceptionHandler.getResponceFlag(ex, getClass(), rftId0001.getRftNo());
            rftId5001.setAnsCode(answer.get(0));
            rftId5001.setErrDetails(answer.get(1));
        }
        
        /* 必須の応答電文項目をセット */
        //  (STX)
        rftId5001.setSTX();
        //  (SEQ)
        rftId5001.setSEQ(0);
        //  (ID)
        rftId5001.setID(RftId5001.ID);
        //  (端末送信時間)
        rftId5001.setRftSendDate(rftId0001.getRftSendDate());
        //  (サーバ送信時刻)
        rftId5001.setServSendDate(new Date());
        //  (端末号機No.)
        rftId5001.setRftNo(rftId0001.getRftNo());
        //  (サーバ日時)
        try
        {
            // yyyyMMddHHmmss形式 で電文にセット
            rftId5001.setServerDate(DbDateUtil.getSystemDateTime());
        }
        catch (Exception ex)
        {
            List<String> answer = IdExceptionHandler.getResponceFlag(ex, getClass(), rftId0001.getRftNo());
            rftId5001.setAnsCode(answer.get(0));
            rftId5001.setErrDetails(answer.get(1));
        }
        //  (応答フラグ) //このポイント以前で応答フラグはセットしておく事
        if (StringUtil.isBlank(rftId5001.getAnsCode()))
        {
            rftId5001.setAnsCode(RftId5001.ANS_CODE_ERROR);
            rftId5001.setErrDetails(RftId5001.ErrorDetails.INTERNAL_ERROR);
            // 6026117=<{0}号機> ID対応処理例外。致命的エラー。応答フラグ空白で応答電文を送信しようとした為、強制的にエラー応答をセットしました。
            RmiMsgLogClient.write(WmsMessageFormatter.format(6026117, rftId0001.getRftNo()), getClass().getSimpleName());
        }
        //  (ETX)
        rftId5001.setETX();

        /* トランザクションを確定 */
        try
        {
            if (RftId5001.ANS_CODE_NORMAL.equals(rftId5001.getAnsCode()))
            {
                try
                {
                    //コミット
                    _wConn.commit();
                }
                catch (Exception ex)
                {
                    _wConn.rollback();
                    throw ex;
                }
            }
            else
            {
                //ロールバック
                _wConn.rollback();
            }
        }
        catch (Exception ex)
        {
            List<String> answer = IdExceptionHandler.getResponceFlag(ex, getClass(), rftId0001.getRftNo());
            rftId5001.setAnsCode(answer.get(0));
            rftId5001.setErrDetails(answer.get(1));
        }

        // 応答電文の組立(sdtの作成)
        rftId5001.getSendMessage(sdt);
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
        return "$Id: Id0001Process.java 4181 2009-04-21 00:14:17Z rnakai $";
    }
}
//end of class



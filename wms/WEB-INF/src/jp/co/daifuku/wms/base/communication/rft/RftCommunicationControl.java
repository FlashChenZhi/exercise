// $Id: RftCommunicationControl.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.communication.rft;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;

/**
 * RFTからの電文を受信し、ID毎の該当タスクを起動する。
 * また、RFTに対し送信を行う。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>miya</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class RftCommunicationControl
        extends Thread
{
    // Class fields --------------------------------------------------
    /**
     * エラー
     */
    public static final int RET_NG = -1;

    /**
     * 正常
     */
    public static final int RET_OK = 0;


    // Class variables -----------------------------------------------
    /**
     * RFT との接続用Utilityクラスへの参照を保持する変数。
     */
    private CommunicationRft wRft = null;

    /**
     * クライアント端末の情報
     */
    private ClientTerminal _clientManager;

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return "$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $";
    }

    // Constructors --------------------------------------------------
    /**
     * RFT通信の起動元である監視スレッドよりAccept状態にて接続後インスタンスを作成する。
     * @param rft Socet Connection Object <code>CommunicationRft</code>の参照 
     * @param cManager Socet Connection Object <code>ClientTerminal</code>の参照 
     */
    public RftCommunicationControl(CommunicationRft rft, ClientTerminal cManager)
    {
        this._clientManager = cManager;

        // RFT との接続用Utilityクラスへの参照をセット。
        wRft = rft;
    }

    // Public methods ------------------------------------------------
    /**
     * 該当タスクを起動します。
     */
    public void run()
    {
        /**
         * RFT の番号を保持する変数
         */
        int wRftNumber;

        try
        {
            byte[] responseData = new byte[CommunicationRft.DEFAULT_LENGTH];
            byte[] rdata = new byte[CommunicationRft.DEFAULT_LENGTH];

            // RFT より電文を受信
            int iDataLen = wRft.recv(rdata);

            try
            {
                // RFT 号機 No. Check
                // 受信電文の号機を判断するためIDを調べる。
                RecvIdMessage idMes = new RecvIdMessage(rdata);
                wRftNumber = Integer.parseInt(idMes.getRftNo());
            }
            catch (NumberFormatException e)
            {
                // RFT No. エラーログ. STX(0x02)は's'、ETX(0x03)は'e'、Null(0x00)は'*'に変換して出力する。
                String idStr =
                        String.valueOf(wRft.serversock.getInetAddress())
                                + "["
                                + new String(rdata).replace('\u0000', '*').replace('\u0002', 's').replace('\u0003', 'e')
                                + "]";
                //6026023=ＲＦＴサーバ通信モジュールで受信したテキストに問題があり、処理できません。テキスト内容:{0}
                RmiMsgLogClient.write(WmsMessageFormatter.format(6026023, idStr), getClass().getSimpleName());
                throw new Exception();
            }

            // Trace File 使用開始設定
            TraceFile traceFile = new TraceFile(wRftNumber);

            // 受信トレース書込み
            String wkRecvstr = new String(rdata, 0, iDataLen);
            traceFile.write(TraceFile.RECIEVE, wkRecvstr);

            // デバッグ
            if (iDataLen > 0)
            {
                System.out.println("RECV DATA = " + wkRecvstr);
                System.out.println("RECV LENGTH = " + iDataLen);
            }

            // OracleDB Connection を取得する
            Connection conn = null;
            try
            {
                conn = _clientManager.getConnection(wRftNumber);
            }
            catch (SQLException e)
            {
                // DBエラーで取得失敗した場合はconnをnullにして機能呼出処理でデータベースエラーを発生させる
                conn = null;
            }

            boolean canSend = false;
            int processStatus = _clientManager.checkStatus(wRftNumber, rdata);
            switch (processStatus)
            {
                case ClientTerminal.NEW_MESSAGE:
                    // 受信電文を保存する。
                    _clientManager.setReceivedMessage(wRftNumber, rdata);

                    // 受信電文の種別を判断するためIDを調べる。
                    RecvIdMessage idMes = new RecvIdMessage(rdata);
                    String receivedId = idMes.getID();

                    // ID振り分け処理
                    IdProcessControl idProcCntl = new IdProcessControl();
                    boolean isNormal =
                            idProcCntl.executeIdProc(receivedId, rdata, responseData, conn, idMes.getRftNo());

                    if (isNormal)
                    {
                        canSend = true;
                    }
                    else
                    {
                        canSend = false;
                    }
                    break;

                case ClientTerminal.MESSAGE_RESPONSED:
                    // 送信済みの応答を返す。
                    responseData = _clientManager.getSendedMessage(wRftNumber);
                    canSend = true;
                    break;

                case ClientTerminal.MESSAGE_PROCESSING:
                default:
                    // 処理中の電文は破棄する
                    canSend = false;
                    break;
            }

            if (canSend)
            {
                // 送信電文を保存する。
                _clientManager.setSendedMessage(wRftNumber, responseData);

                // 伝文の送信を行う。
                wRft.send(responseData);

                // 送信トレース書込み
                int len = wRft.getDataLength(responseData);
                String wkSendstr = new String(responseData, 0, len);
                traceFile.write(TraceFile.SEND, wkSendstr);
                // デバッグ
                System.out.println("SEND DATA = " + wkSendstr);
                System.out.println("SEND LENGTH = " + len);
            }

            // RFTとの回線切断を確認後切断を行う。
            if (wRft != null)
            {
                System.out.println("<<< Disconnect [RftCommunicationControl] RftNo:" + wRftNumber);
                wRft.disconnect();
            }
        }
        catch (Exception e)
        {
            //6026013=ＲＦＴサーバ通信モジュールの送受信タスクで異常が発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6026013, e), getClass().getSimpleName());
        }
    }

    // Package methods -----------------------------------------------
    // Protected methods ---------------------------------------------
    // Private methods -----------------------------------------------
}
//end of class

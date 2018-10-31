// $Id: RftWatcher.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.communication.rft;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.net.ServerSocket;
import java.sql.SQLException;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;

/**
 * RFTとの送受信をする２つのタスクを起動＆監視し、どちらかに異常が起きると双方とも停止させ
 * 新たにRFTとのaccept状態となる。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>miya</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class RftWatcher
        extends Thread
{
    // Class fields --------------------------------------------------
    /**
     * RFTサーバポート(Default)を定義します。
     */
    public static final int DEFAULT_PORT = 2500;

    /**
     * RFTサーバポート(Default)を定義します。
     */
    public static final int DEFAULT_RFT_MAX = 64;

    // Class variables -----------------------------------------------
    /**
     * RFT送受信処理インスタンスへの参照を保持する変数。
     */
    private RftCommunicationControl _rftCommunicationControlInstance = null;

    /**
     * 接続のポート番号
     */
    private int wPort;

    /**
     * RFTWatcherの終了要求チェック
     */
    private static boolean $rftWatcherInstance;

    /**
     * 全号機のDBコネクション等を保持
     */
    private ClientTerminal _clientTerminal;

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public RftWatcher()
    {
        $rftWatcherInstance = true;
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                try
                {
                    // 全号機のDBコネクションをクローズ
                    if (null != _clientTerminal)
                    {
                        // このコンソール出力は消さないでください
                        System.out.println("RFTWatcher DBConn close ");
                        _clientTerminal.closeConnections();
                    }
                    // BlueDOGサービス(認証用)を終了します
                    if (WmsParam.RFT_AUTHENTICATION)
                    {
                        RftBlueDOGServices.stopBlueDOGServices(); 
                    }
                    //<jp> 6020039=RFTサーバを停止します。</jp>
                    //<en> </en>
                    RmiMsgLogClient.write(6020039, "RftWatcher");
                }
                catch (SQLException ex)
                {
                    // エラーをログファイルに落とす
                    RmiMsgLogClient.write(new TraceHandler(6026012, ex), "RftWatcher");
                }
            }
        });
    }

    // Public methods ------------------------------------------------
    /**
     * 処理を実行します。
     */
    public void run()
    {
        try
        {
            //<jp> 6020038=RFTサーバを起動します。</jp>
            //<en> </en>
            RmiMsgLogClient.write(6020038, "RftWatcher");

            // 通信ポート取得
            wPort = WmsParam.RFT_SERVER_PORT;
            if (wPort < 0)
            {
                wPort = DEFAULT_PORT;
            }

            // パッケージマネージャーを初期化する
            PackageManager.initialize(WmsParam.getConnection(), getClass());
            // <jp> BlueDOGサービス(認証用)を起動する</jp>
            // <en> Start BlueDOG services for RFT server (Database polling and Log4j Initilisation)</en>
            if (WmsParam.RFT_AUTHENTICATION)
            {
                RftBlueDOGServices.loadBleDOGServices();
            }
            // 生存確認スレッド(KeepAlive)を起動する。
            if (WmsParam.RFT_KEEP_ALIVE_POLLING_ENABLE)
            {
                new KeepAlive().start();
            }


            // このコンソール出力は消さないでください
            System.out.println("***************************** ");
            System.out.println("RFT Server Port No. = " + wPort);

            //クライアント端末に関する情報クラス インスタンス作成
            _clientTerminal = new ClientTerminal();

            // サーバソケット インスタンス作成
            ServerSocket serverInstance = new ServerSocket(wPort);

            // リソースファイルからSleep Timeを取り込む
            int wSleepTime = WmsParam.RFT_SLEEP_SEC;
            System.out.println("Sleep time = " + wSleepTime + "ms");
            System.out.println("Socket data max length = " + CommunicationRft.DEFAULT_LENGTH);
            System.out.println("***************************** ");

            $rftWatcherInstance = true;
            while ($rftWatcherInstance)
            {
                // このコンソール出力は消さないでください
                System.out.println("* RftWatcher Start !!");
                try
                {
                    // ＲＦＴ（クライアント）からの接続待ち
                    CommunicationRft wServerCommunication = new CommunicationRft();
                    wServerCommunication.connect(serverInstance);
                    // このコンソール出力は消さないでください
                    System.out.println(">>> Connect [RftWatcher] ");

                    // RFT送受信管理スレッドの起動
                    // RFTとの接続用Utilityクラスのインスタンス獲得
                    _rftCommunicationControlInstance = new RftCommunicationControl(wServerCommunication, _clientTerminal);
                    _rftCommunicationControlInstance.start();
                    Thread.sleep(wSleepTime);
                    continue;
                }
                catch (InterruptedException e)
                {
                    // このコンソール出力は消さないでください
                    System.out.println("InterruptedException" + e);
                    // エラーをログファイルに落とす
                    RmiMsgLogClient.write(new TraceHandler(6026012, e), "RftWatcher");
                    //RftLogMessage.printStackTrace(6026012, LogMessage.F_ERROR, "RftWatcher", e);
                    break;
                }
                catch (Exception e)
                {
                    // エラーをログファイルに落とす.
                    RmiMsgLogClient.write(new TraceHandler(6026012, e), "RftWatcher");
                    //RftLogMessage.printStackTrace(6026012, LogMessage.F_ERROR, "RftWatcher", e);
                }
            }
            // コネクションをクローズ
            _clientTerminal.closeConnections();
        }
        catch (Exception e)
        {
            try
            {
                // エラーをログファイルに落とす
                RmiMsgLogClient.write(new TraceHandler(6026012, e), "RftWatcher");
                if (e instanceof SQLException)
                {
                    SQLException sqlex = (SQLException)e;
                    System.out.println(sqlex.getMessage());
                }
                // コネクションをクローズ
                if (null != _clientTerminal)
                {
                    // このコンソール出力は消さないでください
                    System.out.println("RFTWatcher DBConn close ");
                    _clientTerminal.closeConnections();
                }
            }
            catch (SQLException ex)
            {
                // エラーをログファイルに落とす
                RmiMsgLogClient.write(new TraceHandler(6026012, ex), "RftWatcher");
            }
        }
    }

    /**
     * 終了処理
     */
    public void finishRequest()
    {

        $rftWatcherInstance = false;
    }

    // Package methods -----------------------------------------------
    // Protected methods ---------------------------------------------
    // Private methods -----------------------------------------------
}
//end of class

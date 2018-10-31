// $Id: As21Watcher.java 8062 2013-05-27 03:53:08Z kishimoto $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Date;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.rmi.RmiServAbstImpl;
import jp.co.daifuku.rmi.RmiUnbinder;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;

/**
 * <jp> AS21通信処理の起動と監視を行なうクラスです。 AGC単位にテキスト送信処理と受信スレッドの起動を行い、監視します。
 * どちらかに異常が発生した場合はコネクションの再接続を行い、処理を再起動します。 このクラスの１インスタンスごとに、１つのAGCとの通信を行ないます。 <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2001/07/01</TD>
 * <TD>mura</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author $Author: kishimoto $ </jp>
 */
/**
 * <en> This class starts-up & monitors 2 tasks (sending and receiving with AGC). In case the error
 * occurs, it terminates both processes and regains new connection with AGC. Then again, sending &
 * receiving tasks will be started. <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2001/07/01</TD>
 * <TD>mura</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author $Author: kishimoto $ </en>
 */
public class As21Watcher extends RmiServAbstImpl implements java.lang.Runnable
{
    // Class fields --------------------------------------------------
    /**
     * <jp> リモートオブジェクトにバインドするオブジェクト名 </jp>
     */
    /**
     * <en> Object name which bind to the remote object </en>
     */
    public static final String OBJECT_NAME = "As21Watcher";

    /**
     * KeepAlive発生認識時間
     */
    public static final long ALIVE_TIMER = WmsParam.AS21_KEEPALIVE_TIMER;

    // Class variables -----------------------------------------------
    //	private static DecimalFormat $fmt = new DecimalFormat("00");

    /**
     * <jp> AGC との接続用Utilityクラスへの参照を保持する変数。 </jp>
     */
    /**
     * <en> Variablese that preserves reference to the Utility class for the connection with AGC.
     * </en>
     */
    private CommunicationAgc _agc = null;

    /**
     * <jp> AGC 番号を保持する変数。 </jp>
     */
    /**
     * <en> Variables which preserves AGC numbers </en>
     */
    private int _agcNo = 0;

    /**
     * <jp> 受信処理インスタンスへの参照を保持する変数。 </jp>
     */
    /**
     * <en> Variables that preserves reference to the instance of receiving process </en>
     */
    private As21Receiver _as21ReceiverInstance = null;

    /**
     * <jp> 送信処理インスタンスへの参照を保持する変数。 </jp>
     */
    /**
     * <en> Variables that preserves reference to the instance of sending process </en>
     */
    private As21Sender _as21SenderInstance = null;

    /**
     * <jp> KeepAliveWatcherインスタンスへの参照を保持する変数。 </jp>
     */
    /**
     * <en> Variables that preserves reference to the instance of KeepAliveWatcher process </en>
     */
    private As21KeepAliveWatcher _as21KeepAliveInstance = null;

    /**
     * <jp> 接続先AGCのホスト名 </jp>
     */
    /**
     * <en> Host of the AGC connecting </en>
     */
    private String _host = "";

    /**
     * <jp> 接続先AGCのポート番号 </jp>
     */
    /**
     * <en> Port number of the AGC connected to </en>
     */
    private int _port = 2000;

    /**
     * <jp> ログが出続けるのを防ぐフラグ </jp>
     */
    /**
     * <en> Flag which prevents the outputs of log. </en>
     */
    private boolean _loggingFlag = true;

    /**
     * <jp> 受信処理停止指示Flag </jp>
     */
    /**
     * <en> Termination Flag for receiving process </en>
     */
    private boolean _exitFlag = false;

//    /**<jp>
//     * 多重起動フラグ
//     </jp>*/
//    /**<en>
//     * start-up overlaps
//     </en>*/
//    private boolean _overlapsFlag = false;

    // Class method --------------------------------------------------
    /**
     * <jp> このクラスのバージョンを返します。
     * @return バージョンと日付 </jp>
     */
    /**
     * <en> Returns the version of this class.
     * @return Version and the date </en>
     */
    public static String getVersion()
    {
        return ("$Revision: 8062 $,$Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $");
    }

    // Constructors --------------------------------------------------
    /**
     * <jp> コンストラクタ
     * @param host
     *        接続先AGCのホスト名
     * @param port
     *        接続先AGCのポート番号
     * @param agcNumber
     *        AGC番号
     * @throws RemoteException
     *         リモートメソッド呼び出しの実行中に発生する通信関連の例外 </jp>
     */
    /**
     * <en> constructor
     * @param host
     *        Host of the AGC connecting
     * @param port
     *        Port number of the AGC connected to
     * @param agcNumber
     *        AGC numbers
     * @throws RemoteException
     *         Exception related to communication generated while executing remote method call </en>
     */
    public As21Watcher(String host, int port, String agcNumber) throws RemoteException
    {
        _host = host;
        _port = port;
        _agcNo = Integer.parseInt(agcNumber);
        _exitFlag = false;

//        Runtime.getRuntime().addShutdownHook(new Thread()
//        {
//            public void run()
//            {
//                // 終了処理
//                try
//                {
//                    if (_overlapsFlag)
//                    {
//                        // 多重起動の場合は何もしない
//                        return;
//                    }
//
//                    DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " AS21Watcher Shutdown"));
//
//                    // <jp> AS21受信スレッドを停止する。</jp>
//                    // <en> Terminates the AS21 receiving thread.</en>
//                    if(_as21ReceiverInstance != null)
//                    {
//                        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " AS21Watcher RECV-STOP-Req"));
//                        _as21ReceiverInstance.setStop();
//                        _agc = _as21ReceiverInstance.getCommunicationAgc();
//                        _as21ReceiverInstance.interrupt();
//                    }
//
//                    // AS21送信スレッドが動作中の場合、送信スレッドの終了を設定する。
//                    if(_as21SenderInstance != null)
//                    {
//                        _as21SenderInstance.unbind();
//                        setAs21SenderInstance();
//                    }
//                    else
//                    {
//                        // unbind
//                        RmiUnbinder unbinder = new RmiUnbinder();
//                        if (unbinder.isbind(As21Sender.OBJECT_NAME + new DecimalFormat("00").format(_agcNo)))
//                        {
//                            unbinder.unbind(As21Sender.OBJECT_NAME + new DecimalFormat("00").format(_agcNo));
//                        }
//                    }
//
//                    // KeepAliveスレッドが動作中の場合、KeepAliveスレッドの終了を設定する。
//                    if(_as21KeepAliveInstance != null)
//                    {
//                        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " AS21Watcher Alive-STOP-Req"));
//                        _as21KeepAliveInstance.setStop();
//                        _as21KeepAliveInstance.interrupt();
//                        setAs21KeepAliveInstance();
//                    }
//
//                    // <jp> AGCとの接続を切断</jp>
//                    // <en> Cuts the connection with AGC</en>
//                    if(_agc != null && _agc.isConnected())
//                    {
//                        _agc.disconnect();
//                    }
//
//                    // unbind
//                    RmiUnbinder unbinder = new RmiUnbinder();
//                    if (unbinder.isbind(OBJECT_NAME + new DecimalFormat("00").format(_agcNo)))
//                    {
//                        unbinder.unbind(OBJECT_NAME + new DecimalFormat("00").format(_agcNo));
//                    }
//
//                    // <jp> 6020012=AS21通信処理を停止します。AGCNO={0}</jp>
//                    // <en> 6020012=Terminating AS21 communication process. SRC NO={0}</en>
//                    RmiMsgLogClient.write(WmsMessageFormatter.format(6020012, _agcNo), "As21Watcher");
//                }
//                catch (Exception e)
//                {
//
//                }
//            }
//        });
    }

    // Public methods ------------------------------------------------
    /**
     * <jp> 受信処理スレッドが停止した事を判断する為に受信処理インスタンスへの参照をクリアする。 本処理は起動されたスレッド側にて操作される。 </jp>
     */
    /**
     * <en> Clear reference to the instance of receiving process in order to determine that
     * processing thread for receiving messages has terinated. This process is to be manipulated by
     * the thread which has been started. </en>
     */
    public void setAs21ReceiverInstance()
    {
        _as21ReceiverInstance = null;
    }

    /**
     * <jp> 送信処理スレッドが停止した事を判断する為に送信処理インスタンスへの参照をクリアする。 本処理は起動されたスレッド側にて操作される。 </jp>
     */
    /**
     * <en> Clear reference to the instance of sending process in order to determine that processing
     * thread for sending messages has terinated. This process is to be manipulated by the thread
     * which has been started. </en>
     */
    public void setAs21SenderInstance()
    {
        _as21SenderInstance = null;
    }

    /**
     * <jp> KeepAliveWatcherスレッドが停止した事を判断する為にKeepAliveインスタンスへの参照をクリアする。 本処理は起動されたスレッド側にて操作される。 </jp>
     */
    /**
     * <en> Clear reference to the instance of keepAlive process in order to determine that
     * processing thread for sending messages has terinated. This process is to be manipulated by
     * the thread which has been started. </en>
     */
    public void setAs21KeepAliveInstance()
    {
        _as21KeepAliveInstance = null;
    }

    /**
     * <jp> As21受信インスタンスを通知します。 本処理は起動されたスレッド側にて操作される。
     * @return As21Receiver AS21受信インスタンス </jp>
     */
    public As21Receiver getReceiverInstance()
    {
        return _as21ReceiverInstance;
    }

    /**
     * <jp> KeepAliveWatcherスレッドインスタンスを通知します。 本処理は起動されたスレッド側にて操作される。
     * @return As21KeepAliveWatcher AS21KeepAliveWatcherインスタンス </jp>
     */
    public As21KeepAliveWatcher getAs21KeepAliveInstance()
    {
        return _as21KeepAliveInstance;
    }

    /**
     * <jp> ログを落とすことを許可するようにする。 本フラグは起動されたスレッド側にて操作される。 </jp>
     */
    /**
     * <en> Permits that log will be recorded. This flag is to be manipulated by the thread which
     * has been started. </en>
     */
    public void setLoggingON()
    {
        _loggingFlag = true;
    }

    /**
     * <jp> ログを落とすことを許可しないようにする。 本フラグは起動されたスレッド側にて操作される。 </jp>
     */
    /**
     * <en> Not allow to record logs. This flag is to be manipulated by the thread which has been
     * started. </en>
     */
    public void setLoggingOFF()
    {
        _loggingFlag = false;
    }

    /**
     * <jp> ログを落とすことを許可するどうかのフラグを返します。
     * @return true :ログを落とす<BR>
     *         false :ログを落とさない </jp>
     */
    /**
     * <en> Returns flag whether/not the log should be recorded
     * @return true :Log should be recorded. false :Log shouldn't be recorded </en>
     */
    public boolean getLoggingFlag()
    {
        return _loggingFlag;
    }

    /**
     * <jp> テキスト送信・受信処理を起動します。 テキスト送信処理は、As21Senderクラスをインスタンス化し、RMIによるリモートメソッド呼出しの形式で実行します。
     * テキスト受信処理は、As21Reciverクラスをインスタンス化し、スレッドとして実行します。 </jp>
     */
    /**
     * <en> Starts-up the text send & receive processing. Process of sending text can be implemented
     * by the instantiation of As21Sender class, then by calling remote method by RMI. Process of
     * receiving text can be implemented by instantiation of As21Receiver class, then by executing
     * as a thread. </en>
     */
    public void run()
    {
        try
        {
            DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " AS21Watcher Start"));

            while (_exitFlag == false)
            {
                try
                {
                    if (WmsParam.MULTI_ASRSSERVER)
                    {
                        // <jp> As21WatcherをRMI Serverへ登録</jp>
                        // <en> Registers As21Watcher in RMI Server.</en>
                        this.bind("//" + RmiSendClient.RMI_REG_SERVER + _agcNo + "/" + OBJECT_NAME
                                + new DecimalFormat("00").format(_agcNo));
                    }
                    else
                    {
                        // <jp> As21WatcherをRMI Serverへ登録</jp>
                        // <en> Registers As21Watcher in RMI Server.</en>
                        this.bind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME
                                + new DecimalFormat("00").format(_agcNo));
                    }
                    while (_exitFlag == false)
                    {
                        try
                        {
                            // <jp> AGC との接続</jp>
                            // <en> Connection with AGC </en>
                            _agc = new CommunicationAgc(_host, _port);

                            // <jp> 受信用スレッドの起動</jp>
                            // <en> Starts up the receiving thread</en>
                            if (_as21ReceiverInstance == null)
                            {
                                // <jp> AGCとの接続用Utilityクラスのインスタンス獲得</jp>
                                // <en> Gets the instance of Utility class by which connection with
                                // AGC to be established.</en>
                                _as21ReceiverInstance = new As21Receiver(_agc, this, _agcNo);
                                _as21ReceiverInstance.start();
                            }
                            // <jp> 送信スレッドはRMI用にインスタンスを作り、レジストリーに登録するのみ</jp>
                            // <en> Sending thread only creates the instance for RMI and records in
                            // registry.</en>
                            if (_as21SenderInstance == null)
                            {
                                _as21SenderInstance = new As21Sender(_agc, _agcNo);
                                if (WmsParam.MULTI_ASRSSERVER)
                                {

                                    _as21SenderInstance.bind("//" + RmiSendClient.RMI_REG_SERVER
                                            + _agcNo + "/" + As21Sender.OBJECT_NAME
                                            + new DecimalFormat("00").format(_agcNo));
                                }
                                else
                                {

                                    _as21SenderInstance.bind("//" + RmiSendClient.RMI_REG_SERVER
                                            + "/" + As21Sender.OBJECT_NAME
                                            + new DecimalFormat("00").format(_agcNo));

                                }
                            }

                            // KeepAlive発生認識時間が０の場合、KeepAlive監視は不要。
                            if (ALIVE_TIMER > 0)
                            {
                                // <jp> KeepAliveスレッドの起動</jp>
                                // <en> Starts up the keepaliveWatcher thread</en>
                                if (_as21KeepAliveInstance == null)
                                {
                                    _as21KeepAliveInstance = new As21KeepAliveWatcher(_agc, _agcNo);
                                    _as21KeepAliveInstance.start();
                                }
                            }
                            if (_loggingFlag)
                            {
                                // <jp> 起動</jp>
                                // <en> Start</en>
                                // <jp> 6020011=AS21通信処理を起動します。AGCNO={0}</jp>
                                // <en> 6020011=Starting AS21 communication process. SRC NO={0}</en>
                                RmiMsgLogClient.write(WmsMessageFormatter.format(6020011, _agcNo),
                                        "As21Watcher");
                            }

                            // V3.5.3 joinで待機するため、synchronizedブロックは不要
//                            synchronized (this)
//                            {
                                // 2008/1/26 UPDATE START
                                // waitによる待機から、As21Receiverスレッドの終了を待機するよう方法に変更する。
                                _as21ReceiverInstance.join();
                                // wait();
                                // 2008/1/26 UPDATE END
//                            }
                        }
                        catch (Exception e)
                        {
                            if (_loggingFlag)
                            {
                                // <jp>エラーをログファイルに落とす</jp>
                                // <en>Records errors in the log file.</en>
                                // <jp> 6026040=通信モジュールの監視タスクで異常が発生しました。</jp>
                                // <en> 6026040=Error occurred in the monitoring task of
                                // communication module.</en>
                                RmiMsgLogClient.write(new TraceHandler(6026040, e), getClass()
                                        .getName());
                                setLoggingOFF();
                            }
                        }
                        finally
                        {
                            if (!_exitFlag)
                            {
                                DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " AS21Watcher Finally"));
                                // <jp> 送信スレッドに終了を指示する。CarryInformationControllerがRMIコールに失敗する。</jp>
                                // <en> Orders sending thread to terminate
                                // processing.CarryInformationController fails to call RMI.</en>
                                _as21SenderInstance.unbind();
                                // <jp> 送信スレッドの終了を設定する。</jp>
                                // <jp>
                                // 受信スレッドの初期化はAs21Receiver側でthis.setAs21ReceiverInstance()がCallされて参照がClearされている。</jp>
                                // <en> Sets the termination of sending thread.</en>
                                // <en> Reference for theiInitialization of receiving thread was
                                // cleared; as this.setAs21ReceiverInstance()</en>
                                // <en> had been called on As21Receiver side.</en>
                                setAs21SenderInstance();
                                // <jp> KeepAliveスレッドの終了を設定する。</jp>
                                // <en> Sets the termination of keepAlive thread.</en>
                                if (_as21KeepAliveInstance != null)
                                {
                                    _as21KeepAliveInstance.setStop();
                                    _as21KeepAliveInstance.interrupt();
                                    setAs21KeepAliveInstance();
                                }
                                // <jp> AGCとの接続を初期化する。</jp>
                                // <en> Initialize the connection with AGC.</en>
                                if (_agc != null && _agc.isConnected())
                                {
                                    _agc.disconnect();
                                }
                                _agc = null;
                                // <jp> リソースファイルからSleep Timeを取り込む</jp>
                                // <en> Retrieves the Sleep Time from the resource file.</en>
                                int wSleepTime = WmsParam.AS21_SLEEP_SEC;
                                // <jp> AS21通信処理で回線異常となった場合の再接続実施までのSleep</jp>
                                // <en> Sleep Time before the recovery of connection in case the line
                                // error occured during AS21 communication</en>
                                // <en> processing.</en>
                                Thread.sleep(wSleepTime);
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    System.out.println("rmiregistry has not started!!");
                    Thread.sleep(3000);
                }
            }
        }
        catch (Exception e)
        {
            // <jp>エラーをログファイルに落とす</jp>
            // <en>Records errors in log file.</en>
            // <jp> 6026040=通信モジュールの監視タスクで異常が発生しました。</jp>
            // <en> 6026040=Error occurred in the monitoring task of communication module.</en>
            RmiMsgLogClient.write(new TraceHandler(6026040, e), getClass().getName());
        }
        finally
        {
            // <jp> 6020013=通信監視処理を停止します。</jp>
            // <en> 6020013=Terminating communication monitoring process.</en>
            RmiMsgLogClient.write(6020013, "As21Watcher");
            this.unbind();
            DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " AS21Watcher Stopped"));
            //System.exit(0);
        }

    }

    /**
     * <jp> 使用されないメソッドです。上位で宣言されたメソッドを仮実装するために用意されています。
     * @param params
     *        使用されません。 </jp>
     */
    /**
     * <en> This method is unused. It is prepared only for the tentative implementation of methods
     * which have been declared by upper class.
     * @param params
     *        Unused. </en>
     */
    @Override
    public synchronized void write(Object[] params)
    {
    }

    /**
     * <jp> As21通信処理を終了します。 外部よりこのメソッドを呼び出された場合、通信処理を終了します。
     * @throws IOException
     *         ファイルI/Oエラーが発生した場合に通知されます。 </jp>
     */
    /**
     * <en> Terminates the As21 communications. If this method is called externally, it terminates
     * the communication process.
     * @throws IOException :
     *         Notifies if file I/O error occured. </en>
     */
    @Override
    public synchronized void stop() throws IOException
    {
        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " AS21Watcher STOP Request"));
        // <jp> スレッドのループが終了するように、フラグを更新する。</jp>
        // <en> Updates the flag so that thread loop should terminate.</en>
        _exitFlag = true;

        try
        {
            // <jp> AS21受信スレッドを停止する。</jp>
            // <en> Terminates the AS21 receiving thread.</en>
            if (_as21ReceiverInstance != null)
            {
                DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " AS21Watcher RECV-STOP-Req"));
                _as21ReceiverInstance.setStop();
                _agc = _as21ReceiverInstance.getCommunicationAgc();
                _as21ReceiverInstance.interrupt();
            }

            // AS21送信スレッドが動作中の場合、送信スレッドの終了を設定する。
            if (_as21SenderInstance != null)
            {
                _as21SenderInstance.unbind();
                setAs21SenderInstance();
            }
            else
            {
                // unbind
                RmiUnbinder unbinder = new RmiUnbinder();
                if (unbinder.isbind(As21Sender.OBJECT_NAME + new DecimalFormat("00").format(_agcNo)))
                {
                    unbinder.unbind(As21Sender.OBJECT_NAME + new DecimalFormat("00").format(_agcNo));
                }
            }

            // KeepAliveスレッドが動作中の場合、KeepAliveスレッドの終了を設定する。
            if (_as21KeepAliveInstance != null)
            {
                DEBUG.MSG("AS21",
                        ("[" + new Date() + "] Agc-" + _agcNo + " AS21Watcher Alive-STOP-Req"));
                _as21KeepAliveInstance.setStop();
                _as21KeepAliveInstance.interrupt();
                setAs21KeepAliveInstance();
            }

            // <jp> AGCとの接続を切断</jp>
            // <en> Cuts the connection with AGC</en>
            if (_agc != null && _agc.isConnected())
            {
                _agc.disconnect();
            }

            // <jp> 6020012=AS21通信処理を停止します。AGCNO={0}</jp>
            // <en> 6020012=Terminating AS21 communication process. SRC NO={0}</en>
            RmiMsgLogClient.write(WmsMessageFormatter.format(6020012, _agcNo), "As21Watcher");
        }
        catch (Exception e)
        {
        }

        // <jp> このスレッドの待ち状態を解除する。</jp>
        // <en> Releases this thread from wait state.</en>
        this.notify();
    }

    /**
     * <jp> Wait中の本スレッドを起します。 </jp>
     */
    /**
     * <en> Activates the thread in wait state. </en>
     */
    public synchronized void wakeup()
    {
        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " AS21Watcher WakeUp"));
        this.notify();
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
// end of class

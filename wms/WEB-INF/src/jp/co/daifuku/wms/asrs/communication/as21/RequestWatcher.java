// $Id: RequestWatcher.java 8062 2013-05-27 03:53:08Z kishimoto $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.rmi.RmiServAbstImpl;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.util.CheckConnection;

/**<jp>
 * システム状態を監視するクラスです。このクラスから起動されます。<BR>
 * ステーションの作業Mode切替完了報告を一定時間内に受信できなければ<BR>
 * モード切替要求    ：モード切替要求無し<BR>
 * モード切替開始時間：null<BR>
 * にするクラスです。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/09/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author  $Author: kishimoto $
 </jp>*/
/**<en>
 * If the work mode switch completion report of the station is expected but has not been received
 * within certain time, this class changes the status as follows<BR>
 * Mode switch request : No request of swicthing mode<BR>
 * Start time for Mode switch : null<BR>
 * This class starts the process.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/09/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author  $Author: kishimoto $
 </en>*/
public class RequestWatcher
        extends RmiServAbstImpl
        implements Runnable
{
    // Class fields --------------------------------------------------
    /**<jp>
     * リモートオブジェクトにバインドするオブジェクト名
     </jp>*/
    /**<en>
     * Object to bind to the remote object
     </en>*/
    public static final String OBJECT_NAME = "RequestWatcher";

    /**<jp>
     * 起動モードです。デバッグモードで起動する場合コンストラクタに<CODE>true</CODE>をセットします。
     </jp>*/
    /**<en>
     * Start-up mode. If starting on debug mode, set constructor with <CODE>true</CODE>.
     </en>*/
    //<jp> デフォルト ノーマルモード</jp>
    //<en> Default normal mode</en>
    private static boolean $mode = false;

    // DFKLOOK ここから追加(DB再接続フラグ)

    /**<jp>
     * このフラグは、オブジェクト内で取得したDBコネクションが保持されているかの判断に使用します。
     * 使用する場合は、CheckConnectionクラスのcheckメソッドを呼出てください。
     * コネクションを失っている場合trueがセットされます。
     </jp>*/
    /**<en>
     * This flag is obtained in the DB object used to determine whether the connection isu maintained.
     * If you are using, please call the check methods of the CheckConnection class.
     * If object have lost connection is set to 'true'.
     </en>*/
    private boolean _dbConCheckFlag = false;

    // DFKLOOK ここまで

    // Class variables -----------------------------------------------
    /**<jp>
     * 作業Mode切替要求が送信され完了が受信される間隔。この時間を超えると要求はなかったこととみなします。
     </jp>*/
    /**<en>
     * The interval between the time the work mode switch request was sent and the completion is received.
     * If the time prolongs over this time, consider that the request had not been made.
     </en>*/
    //<jp> デフォルト3分間応答が来るのを待つ</jp>
    //<en> Wait for the reply for default 3 minutes</en>
    protected int _waitTime = 180000;

    /**<jp>
     * スレッド待機時間
     </jp>*/
    /**<en>
     * Thread idle time
     </en>*/
    //<jp> デフォルト10秒</jp>
    //<en> Default 10 seconds</en>
    protected int _sleepTime = 10000;

    /**<jp>
     * このフラグは、RequestWatcherクラスを終了するかどうか判断します。
     * ExitFlagがtrueになった場合、run()メソッド内の無限ループから抜けます。
     * このフラグを更新するためには、stop()メソッドを使用します。
     </jp>*/
    /**<en>
     * This flag determines whether/not to terminate the RequestWatcher class.
     * If ExitFlag changes to true, it pulls out of the infinite loop of run() method.
     * This stop() method must be used to update this flag.
     </en>*/
    private boolean _exitFlag = false;

    private Connection _conn = null;

//    /**<jp>
//     * 多重起動フラグ
//     </jp>*/
//    /**<en>
//     * start-up overlaps
//     </en>*/
//    private boolean _overlapsFlag = false;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 8062 $,$Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $");
    }

    /**<jp>
     * プロンプトに出力します。
     * @param str プロンプトに表示する文字列
     * @param system ノーマルモードで起動された場合でもログを落とす場合は<CODE>true</CODE>をセットします。
     </jp>*/
    /**<en>
     * Output to the prompt display.
     * @param str String to show on  the prompt display
     * @param system Even if started up on normal mode, and if recording the log, set <CODE>true</CODE>.
     </en>*/
    private static void println(String str, boolean system)
    {
        if ($mode || system)
        {
            System.out.println(str);
        }
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトの監視時間を使用して新しい<code>RequestWatcher</code>のインスタンスを構築します。
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    /**<en>
     * Constructs a new instance of <code>RequestWatcher</code> using the default time of monitoring.
     * @throws RemoteException  Exception related to communication generated while executing remote method call
     </en>*/
    public RequestWatcher()
            throws RemoteException
    {
        /* ADD Start 2009.12.08 */
        //データベースコネクションチェックフラグセット
        if ("1".equals(WmsParam.WMS_DB_CONNECT_CHECK))
        {
            _dbConCheckFlag = true;
        }
        /* ADD End 2009.12.08 */

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
//                    _exitFlag = true;
//                    closeConnection();
//
//                    // unbind
//                    RmiUnbinder unbinder = new RmiUnbinder();
//                    if (unbinder.isbind(OBJECT_NAME))
//                    {
//                        unbinder.unbind(OBJECT_NAME);
//                    }
//                }
//                catch (Exception e)
//                {
//
//                }
//            }
//        });

    }

    /**<jp>
     * 新しい<code>RequestWatcher</code>のインスタンスを構築します。
     * @param waittime 作業Mode切替要求が送信されて応答が帰ってくるまでの監視間隔
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    /**<en>
     * Constructs a new instance of <code>RequestWatcher</code>.
     * @param waittime Interval in monitoring: from sending of work mode switch request to receiving  the reply.
     * @throws RemoteException  Exception related to communication generated while executing remote method call
     </en>*/
    public RequestWatcher(int waittime)
            throws RemoteException
    {
        /* ADD Start 2009.12.08 */
        //データベースコネクションチェックフラグセット
        if ("1".equals(WmsParam.WMS_DB_CONNECT_CHECK))
        {
            _dbConCheckFlag = true;
        }
        /* ADD End 2009.12.08 */

        _waitTime = waittime;
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
//                    _exitFlag = true;
//                    closeConnection();
//
//                    // unbind
//                    RmiUnbinder unbinder = new RmiUnbinder();
//                    if (unbinder.isbind(OBJECT_NAME))
//                    {
//                        unbinder.unbind(OBJECT_NAME);
//                    }
//                }
//                catch (Exception e)
//                {
//
//                }
//            }
//        });

    }

    /**<jp>
     * 新しい<code>RequestWatcher</code>のインスタンスを構築します。
     * @param waittime 作業Mode切替要求が送信されて応答が帰ってくるまでの監視間隔
     * @param mode 起動モードを指定します。デバッグモードで起動する場合<CODE>true</CODE>をセットします。
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    /**<en>
     * Constructs a new instance of <code>RequestWatcher</code>.
     * @param waittime Interval in monitoring: from sending of work mode switch request to receiving of reply.
     * @param mode :designate the start-up mode. Set <CODE>true</CODE> if starting by debug mode.
     * @throws RemoteException  Exception related to communication generated while executing remote method call
     </en>*/
    public RequestWatcher(int waittime, boolean mode)
            throws RemoteException
    {
        _waitTime = waittime;
        $mode = mode;
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
//                    _exitFlag = true;
//                    closeConnection();
//
//                    // unbind
//                    RmiUnbinder unbinder = new RmiUnbinder();
//                    if (unbinder.isbind(OBJECT_NAME))
//                    {
//                        unbinder.unbind(OBJECT_NAME);
//                    }
//                }
//                catch (Exception e)
//                {
//
//                }
//            }
//        });

    }

    // Public methods ------------------------------------------------
    /**<jp>
     * スレッドで<CODE>RequestWatcher</CODE>を起動します。
     * @param args 引数
     </jp>*/
    /**<en>
     * Starts <CODE>RequestWatcher</CODE> using thread.
     * @param args : Arguments
     </en>*/
    public static void main(String[] args)
    {
        RequestWatcher reqwatch = null;
        try
        {
            //<jp> 使用法</jp>
            //<en> Usage</en>
            if (args != null && args.length == 1 && (args[0].equals("-?") || args[0].equals("-help")))
            {
                println("example of the usage  j jp.co.daifuku.asrs.communication.as21.RequestWatcher 180000 true",
                        true);
                println("Sets 1st argument to be monitor time, 2nd argument to be start mode, (true:debug mode)", true);
                System.exit(0);
            }
            else if (args != null && args.length == 2)
            {
                reqwatch = new RequestWatcher(Integer.parseInt(args[0]), new Boolean(args[1]).booleanValue());
            }
            else
            {
                reqwatch = new RequestWatcher();
            }

            new Thread(reqwatch).start();
        }
        catch (Exception e)
        {
            println("\n Cannot start up." + String.valueOf(e) + "\n", true);
            if (reqwatch != null)
            {
                //<jp> 6026064=例外が通知された為、異常終了します。タスク名={0}</jp>
                //<en> 6026064=Exception is notified. Abnormal ending. Task={0}</en>
                Object[] tobj = {
                    "RequestWatcher"
                };
                reqwatch.logging(6026064, tobj);
                println("Due to the exceptions, it abnormally ends." + new java.util.Date(), true);
                e.printStackTrace();
            }
        }
    }

    /**<jp>
     * ステーションの作業Mode切替完了報告を一定時間内に受信できなければ<BR>
     * モード切替要求    ：モード切替要求無し<BR>
     * モード切替開始時間：null<BR>
     * にします。
     </jp>*/
    /**<en>
     * If the completion report to the work mode switch request will not be received within a certain time,
     * mode switch request: no request has been made<BR>
     * Start time of mode switch: null<BR>
     </en>*/
    public void run()
    {
        _conn = null;
        int ret = 0;
        try
        {
            //<jp> レジストリに登録</jp>
            //<en> Register in the registo</en>
            this.bind("//" + RmiSendClient.RMI_REG_SERVER +"Main"+ "/" + OBJECT_NAME);

            //<jp> 6020020=リクエスト監視処理を起動します。</jp>
            //<en> Start up the request monitoring process.</en>
            RmiMsgLogClient.write(6020020, "RequestWatcher");
            println(this.getClass().getName()
                    + " Started !!  Start up the process of monitoring response to work mode switching.", true);

            //<jp> コネクションチェックメソッド</jp>
            // <en> Connection Check Method</en>
            CheckConnection chk = new CheckConnection();

            //<jp> ずっと動き続けること。</jp>
            //<en> Repeat the following</en>
            while (_exitFlag == false)
            {
                try
                {
                    //<jp> DBに再接続</jp>
                    //<en> Reestablishes the connection with DB</en>
                    if(true == chk.check(_conn, _dbConCheckFlag))
                    {
                        _conn = WmsParam.getConnection();
                    }

                    while (_exitFlag == false)
                    {
                        synchronized (this)
                        {
                            this.wait(_sleepTime);
                        }
                        if (_exitFlag)
                        {
                            break;
                        }

                        //<jp> ステーションを全件検索し作業Mode切替要求をしているステーションがあるかチェックし</jp>
                        //<jp> 存在すれば指定した時間内に応答がきていないステーションの切り替え要求を要求無しに更新する</jp>
                        //<en> Conduct seacrh over all stations to find any station that requesting the work mode change.</en>
                        //<en> If there are any, update the status: switch request to no request at all.</en>
                        StationHandler handle = new StationHandler(_conn);
                        Station[] station = (Station[])handle.find(new StationSearchKey());
                        for (int i = 0; i < station.length; i++)
                        {
                            if (!Station.MODE_REQUEST_NONE.equals(station[i].getModeRequest()))
                            {
                                if (station[i].getModeRequestDate() != null)
                                {
                                    long interval =
                                            System.currentTimeMillis() - station[i].getModeRequestDate().getTime();
                                    println("station:" + station[i].getStationNo()
                                            + " time passed after the request has sent = " + interval + "ms", false);
                                    if (interval > _waitTime)
                                    {
                                        println("station:" + station[i].getStationNo()
                                                + " Cancelled the request for work mode of switchingis", false);
                                        //<jp> 更新</jp>
                                        //<en> Update</en>
                                        StationHandler shandler = new StationHandler(_conn);
                                        StationAlterKey alterkey = new StationAlterKey();
                                        alterkey.clear();
                                        alterkey.setStationNo(station[i].getStationNo());
                                        alterkey.updateModeRequest(Station.MODE_REQUEST_NONE);
                                        alterkey.updateModeRequestDate(null);
                                        shandler.modify(alterkey);
                                        _conn.commit();
                                        //<jp> 6024027=ステーションの作業Mode切替完了報告を{0}秒以内に受信できなかったため、要求を取り消しました。</jp>
                                        //<en> 6024027=Request was canceled. Work mode switch completion was not reported within {0} seconds.</en>
                                        Object[] tobj = {
                                            String.valueOf(_waitTime / 1000)
                                        };
                                        logging(6024027, tobj);
                                    }
                                }
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    //<jp> 6016102=致命的なエラーが発生しました。{0}</jp>
                    //<en> 6016102=A fatal error occurred. {0}</en>
                    logging(6016102, e);
                    Thread.sleep(3000);
                }
                finally
                {
                    try
                    {
                        if (_conn != null)
                        {
                            _conn.rollback();
                            _conn.close();
                        }
                    }
                    catch (SQLException e)
                    {
                        //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
                        //<en> 6007030=Database error occured. error code={0}</en>
                        Object[] tobj = {
                            String.valueOf(e.getErrorCode())
                        };
                        logging(6007030, tobj);
                        _conn = null;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            ret = 1;
        }
        finally
        {
            System.out.println("RequestWatcher::::finally!!");
            //<jp> 6020021=リクエスト監視処理を停止します。</jp>
            //<en> 6020021=Terminating request monitoring.</en>
            RmiMsgLogClient.write(6020021, "RequestWatcher");
//            try
//            {
                this.unbind();
                DBUtil.close(_conn);
                if (ret > 0)
                {
                    //<jp> 6026064=例外が通知された為、異常終了します。タスク名={0}</jp>
                    //<en> 6026064=Exception is notified. Abnormal ending. Task={0}</en>
                    Object[] tobj = {
                        "RequestWatcher"
                    };
                    logging(6026064, tobj);
                    println("As the exception has been notified, it is abnormally ending." + new java.util.Date(), true);
                }
//            }
//            catch (SQLException e)
//            {
//                //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
//                //<en> 6007030=Database error occured. error code={0}</en>
//                Object[] tobj = {
//                    String.valueOf(e.getErrorCode())
//                };
//                logging(6007030, tobj);
//                ret = 1;
//            }
//            finally
//            {
//                System.exit(ret);
//            }
        }
    }

    /**<jp>
     * 使用されないメソッドです。上位で宣言されたメソッドを仮実装するために用意されています。
     * @param params 使用されません。
     </jp>*/
    /**<en>
     * This method is unused. This is prepared only to virtually implement the
     * method declared upperclass.
     * @param params Unused
     </en>*/
    public synchronized void write(Object[] params)
    {
    }

    /**<jp>
     * 処理を終了します。
     * 外部よりこのメソッドを呼び出された場合、処理を終了します。
     </jp>*/
    /**<en>
     * Terminating the process.
     * It terminates the process when it is called exrternally.
     </en>*/
    public synchronized void stop()
    {
        //<jp> スレッドのループが終了するように、フラグを更新する。</jp>
        //<en> Update the flag so taht loop of the thread terminates.</en>
        _exitFlag = true;
        //<jp> このスレッドの待ち状態を解除する。</jp>
        //<en> release this thread from the wait state.</en>
        this.notify();
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * ログファイルに出力します。
     * @param msgnum メッセージ番号
     * @param tobj メッセージ・ログへ書き込むパラメータ
     </jp>*/
    /**<en>
     * Outputting to the log file.
     * @param msgnum Message no.
     * @param tobj Parameter to write in log file
     </en>*/
    private void logging(int msgnum, Object[] tobj)
    {
        RmiMsgLogClient.write(msgnum, "RequestWatcher", tobj);
    }

    /**<jp>
     * ログファイルに出力します。
     * @param msgnum メッセージ番号
     * @param e 通知された例外
     </jp>*/
    /**<en>
     * Outputting to the log file.
     * @param msgnum Message no.
     * @param e Exception notified
     </en>*/
    private void logging(int msgnum, Exception e)
    {
        Object[] tobj = {
            TraceHandler.getStackTrace(e)
        };
        RmiMsgLogClient.write(msgnum, "RequestWatcher", tobj);
    }

    /**
     * このクラスで保持しているコネクションをクローズします。
     * 2006.10.03
     * @author E.Takeda
     *
     */
    private void closeConnection()
    {
        try
        {
            /* ADD Start 2006.10.10 E.Takeda */
            _conn.close();
            _conn.rollback();
        }
        catch (SQLException e)
        {
            // 何もしない
        }
        //<jp> 6020021=リクエスト監視処理を停止します。</jp>
        //<en> 6020021=Terminating request monitoring.</en>
        RmiMsgLogClient.write(6020021, "RequestWatcher");

    }

}
//end of class

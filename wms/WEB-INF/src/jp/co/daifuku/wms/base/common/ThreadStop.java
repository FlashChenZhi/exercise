// $Id: ThreadStop.java 8062 2013-05-27 03:53:08Z kishimoto $
package jp.co.daifuku.wms.base.common;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.UnmarshalException;
import java.sql.Connection;
import java.text.DecimalFormat;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.RmiMsgLogServer;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Executor;
import jp.co.daifuku.wms.asrs.communication.as21.As21Watcher;
import jp.co.daifuku.wms.asrs.communication.as21.AutomaticModeChangeSender;
import jp.co.daifuku.wms.asrs.communication.as21.CarryInformationController;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.communication.as21.ReArrangeAllocator;
import jp.co.daifuku.wms.asrs.communication.as21.ReArrangeTimeKeeper;
import jp.co.daifuku.wms.asrs.communication.as21.RequestWatcher;
import jp.co.daifuku.wms.asrs.communication.as21.RetrievalSender;
import jp.co.daifuku.wms.asrs.communication.as21.StorageSender;
import jp.co.daifuku.wms.asrs.communication.as21.TimeKeeper;

/**
 * 常駐タスクを終了するmainメソッドを持つクラスです。
 * メッセージログサーバー、AS21通信処理などの各スレッドRMI経由で終了します。
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author K22212
 */
public class ThreadStop
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

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

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------
    /**
     * RMI経由でスレッドを終了します。
     * @param args 引数
     */
    public static void main(String[] args)
    {
        Connection conn = null;
        try
        {
            String pName = null;
            if (!ArrayUtil.isEmpty(args))
            {
                pName = args[0];
            }
            else
            {
                // 引数未指定エラー
                throw new IllegalArgumentException();
            }


            if (pName.equalsIgnoreCase(RmiMsgLogServer.class.getSimpleName()))
            {
                //<jp> メッセージログサーバの終了</jp>
                //<en> Terminates message log server.</en>

                //<jp> メッセージログサーバーを終了します。</jp>
                //<en> Terminating message log server.</en>
                RmiMsgLogClient.write("6020002", "RmiMsgLogServer");

                stop(RmiSendClient.RMI_REG_LOG_SERVER, RmiMsgLogServer.LOGSERVER_NAME);
            }
            else if (pName.equalsIgnoreCase(As21Executor.class.getSimpleName()))
            {
                //<jp> As21Executor用スレッドの終了</jp>
                //<en> Terminates thread for As21Executor.</en>

                //<jp> DataBaseへのコネクションを獲得する。ユーザ名等はリソースファイルより獲得。</jp>
                //<en> Gets connection with DataBase. User's name and else will be obtained from resource file.</en>
                conn = WmsParam.getConnection();

                if (!WmsParam.MULTI_ASRSSERVER)
                {
                    //<jp> グループコントローラクラスからシステムに存在するすべてのグループコントローラ情報を得る。</jp>
                    //<en> From the gourp controller class, it gains data of all group controller that exists in system. </en>
                    GroupController[] gpColection = GroupController.getInstances(conn);
                    //<jp> グループコントローラ台数分 送信、受信処理を作成する。</jp>
                    //<en> Creates sending and receiving jobs for all group contoller machines.</en>
                    for (int i = 0; i < gpColection.length; i++)
                    {
                        int agcNo = Integer.parseInt(gpColection[i].getNo());
                        String rmihost = RmiSendClient.RMI_REG_SERVER;
                        String name = As21Watcher.OBJECT_NAME + new DecimalFormat("00").format(agcNo);

                        //<jp> 接続監視用スレッドの終了</jp>
                        //<en> Termination of monitoring thread for connection</en>
                        stop(rmihost, name);
                    }
                }
                else
                {
                    if (args.length <= 1)
                    {
                        // AGC号機No.未指定エラー
                        throw new IllegalArgumentException();
                    }
                    for (int i = 1; i < args.length; i++)
                    {
                        int agcNo = Integer.parseInt(args[i]);
                        String rmihost = RmiSendClient.RMI_REG_SERVER + agcNo;
                        String name = As21Watcher.OBJECT_NAME + new DecimalFormat("00").format(agcNo);

                        //<jp> 接続監視用スレッドの終了</jp>
                        //<en> Termination of monitoring thread for connection</en>
                        stop(rmihost, name);
                    }
                }
            }
            else if (pName.equalsIgnoreCase(CarryInformationController.class.getSimpleName()))
            {
                if (!WmsParam.MULTI_ASRSSERVER)
                {
                    String rmihost = RmiSendClient.RMI_REG_SERVER;

                    //<jp> 搬送指示送信処理用スレッドの終了</jp>
                    //<en> Terminates thread for transmission job of carrying instruction.</en>
                    stop(rmihost, StorageSender.OBJECT_NAME);

                    //<jp> 出庫指示送信処理用スレッドの終了</jp>
                    //<en> Terminates thread for tranmission job of output command.</en>
                    stop(rmihost, RetrievalSender.OBJECT_NAME);

                    //<jp> 自動モード切替搬送指示送信処理用スレッドの終了</jp>
                    //<en> Terminates thread for transmission job of carrying instruction on automatic switching mode.</en>
                    stop(rmihost, AutomaticModeChangeSender.OBJECT_NAME);

                    //<jp> 時間監視スレッドの終了</jp>
                    //<en> Terminates thread for time monitoring.</en>
                    stop(rmihost, TimeKeeper.OBJECT_NAME);
                }
                else
                {
                    if (args.length <= 1)
                    {
                        // AGC号機No.未指定エラー
                        throw new IllegalArgumentException();
                    }
                    for (int i = 1; i < args.length; i++)
                    {
                        int agcNo = Integer.parseInt(args[i]);
                        String rmihost = RmiSendClient.RMI_REG_SERVER + agcNo;

                        //<jp> 搬送指示送信処理用スレッドの終了</jp>
                        //<en> Terminates thread for transmission job of carrying instruction.</en>
                        stop(rmihost, StorageSender.OBJECT_NAME + agcNo);

                        //<jp> 出庫指示送信処理用スレッドの終了</jp>
                        //<en> Terminates thread for tranmission job of output command.</en>
                        stop(rmihost, RetrievalSender.OBJECT_NAME + agcNo);

                        //<jp> 自動モード切替搬送指示送信処理用スレッドの終了</jp>
                        //<en> Terminates thread for transmission job of carrying instruction on automatic switching mode.</en>
                        stop(rmihost, AutomaticModeChangeSender.OBJECT_NAME + agcNo);

                        //<jp> 時間監視スレッドの終了</jp>
                        //<en> Terminates thread for time monitoring.</en>
                        stop(rmihost, TimeKeeper.OBJECT_NAME + agcNo);
                    }
                }
            }
            else if (pName.equalsIgnoreCase(RequestWatcher.class.getSimpleName()))
            {
                //<jp> リクエスト監視処理用スレッドの終了</jp>
                //<en> Terminates thread for request monitoring job.</en>
                stop(RmiSendClient.RMI_REG_SERVER + "Main", RequestWatcher.OBJECT_NAME);
            }
            else if (pName.equalsIgnoreCase(ReArrangeAllocator.class.getSimpleName()))
            {
                //<jp> ReArrangeTimeKeeper用スレッドの終了</jp>
                //<en> Terminates thread for ReArrangeTimeKeeper.</en>
                if (!WmsParam.MULTI_ASRSSERVER)
                {
                    String rmihost = RmiSendClient.RMI_REG_SERVER;

                    //<jp> 配置替え引当処理スレッドの終了</jp>
                    //<en> Terminates thread for rearrange allocating job.</en>
                    stop(rmihost, ReArrangeAllocator.OBJECT_NAME);

                    //<jp> 時間監視スレッドの終了</jp>
                    //<en> Terminates thread for time monitoring.</en>
                    stop(rmihost, ReArrangeTimeKeeper.OBJECT_NAME);
                }
                else
                {
                    if (args.length <= 1)
                    {
                        // AGC号機No.未指定エラー
                        throw new IllegalArgumentException();
                    }
                    for (int i = 1; i < args.length; i++)
                    {
                        int agcNo = Integer.parseInt(args[i]);
                        String rmihost = RmiSendClient.RMI_REG_SERVER + agcNo;

                        //<jp> 配置替え引当処理スレッドの終了</jp>
                        //<en> Terminates thread for rearrange allocating job.</en>
                        stop(rmihost, ReArrangeAllocator.OBJECT_NAME + agcNo);

                        //<jp> 時間監視スレッドの終了</jp>
                        //<en> Terminates thread for time monitoring.</en>
                        stop(rmihost, ReArrangeTimeKeeper.OBJECT_NAME + agcNo);
                    }
                }
            }
            else
            {
                // 引数エラー
                throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException e)
        {
            // 引数エラー
            System.err.println("illegal argument!");
            System.err.println("Set the process name following the first argument.");
            System.err.print(" [ ");
            System.err.print(RmiMsgLogServer.class.getSimpleName() + " | ");
            System.err.print(As21Executor.class.getSimpleName() + " | ");
            System.err.print(CarryInformationController.class.getSimpleName() + " | ");
            System.err.print(RequestWatcher.class.getSimpleName() + " | ");
            System.err.print(ReArrangeAllocator.class.getSimpleName());
            System.err.println(" ] ");
            System.err.println("The second argument sets the AGC number. When you have AS/RS setup on other servers.");
        }
        catch (ConnectException e)
        {
            //<jp> rmiregistryが起動していないため、スレッド終了処理を行えませんでした。</jp>
            //<en> RMI registry is not activated; thread termination cannot be processed.</en>
            RmiMsgLogClient.write(6016099, "ThreadStop");
        }
        catch (Exception e)
        {
            //<jp> 致命的なエラーが発生しました。{0}</jp>
            //<en> Fatal error occurred.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6016102, e), "ThreadStop");
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * 指定された名前のスレッドを終了します。
     * @param name リモートオブジェクトにバインドした名前
     * @throws ConnectException :コネクションに失敗した時に通知されます。
     </jp>*/
    /**<en>
     * Terminates thread by specified name.
     * @param name : Name bound to remote object
     * @throws ConnectException :Notifies if a connection is refused to the remote host for a remote method call.
    </en>*/
    private static void stop(String rmihost, String name)
            throws ConnectException
    {
        try
        {
            RmiSendClient rmiSndC = new RmiSendClient(rmihost);
            //<jp> メッセージログサーバーの終了</jp>
            //<en> Terminates the message log server.</en>
            rmiSndC.stop(name);
            rmiSndC = null;
        }
        catch (NotBoundException e)
        {
            //<jp> リモートオブジェクトに指定された名前がバインドされていない</jp>
            //<jp> スレッドが起動していませんでした。スレッド名：{0}</jp>
            //<en> Specified name is not bound to remote object.</en>
            //<en> Thread is not running. Thread name:{0}</en>
            RmiMsgLogClient.write(WmsMessageFormatter.format(6016100, name), "ThreadStop");
        }
        catch (ConnectException e)
        {
            throw new ConnectException(e.getMessage());
        }
        catch (UnmarshalException e)
        {
            //<jp> RmiMsgLogServerがThreadで動作していないため、System.exit(0);を呼び出したときにExceptionが発生する。</jp>
            //<jp> ここでCatchして処理を行わない。</jp>
            //<en> As RmiLog Server is not operated on Thread, Exception occurs when System.exit(0); </en>
            //<en> is invoked. </en>
        }
        catch (Exception e)
        {
            //<jp> 致命的なエラーが発生しました。{0}</jp>
            //<en> Fatal error occurred.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6016102, e), "ThreadStop");
        }
    }
}
//end of class


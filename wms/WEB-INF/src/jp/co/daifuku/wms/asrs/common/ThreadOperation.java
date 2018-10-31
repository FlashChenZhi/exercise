// $Id: ThreadOperation.java 8062 2013-05-27 03:53:08Z kishimoto $
package jp.co.daifuku.wms.asrs.common;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.UnmarshalException;
import java.sql.Connection;
import java.text.DecimalFormat;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.RmiMsgLogServer;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Watcher;
import jp.co.daifuku.wms.asrs.communication.as21.AutomaticModeChangeSender;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.communication.as21.RequestWatcher;
import jp.co.daifuku.wms.asrs.communication.as21.RetrievalSender;
import jp.co.daifuku.wms.asrs.communication.as21.StorageSender;
import jp.co.daifuku.wms.asrs.communication.as21.TimeKeeper;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;

/**<jp>
 * 常駐タスクを終了するmainメソッドを持つクラスです。
 * メッセージログサーバー、AS21通信処理、通信監視処理、搬送指示送信処理、出庫指示送信処理、
 * 自動モード切替搬送指示送信処理、時間監視処理、リクエスト監視処理の各スレッドを終了します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/06/26</TD><TD>miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author  $Author: kishimoto $
 </jp>*/
/**<en>
 * This class preserves the main method to terminate the resident tasks.
 * It terminates each thread of message log server, AS21 communication process, communication monitoring, transmission of carrying instructions,
 * transmission of output command, transmission of carrying instruction on automatic switching mode, time monitoring, request monitoring.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/06/26</TD><TD>miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author  $Author: kishimoto $
 </en>*/
public class ThreadOperation
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
    /**<jp>
     * 各タスクの終了処理を行います。
     * @param args (未使用)
     </jp>*/
    /**<en>
     * Processes termination of each task.
     * @param args : not used
     </en>*/
    public static void main(String[] args)
    {
        try
        {
            DecimalFormat fmt = new DecimalFormat("00");

            //<jp> DataBaseへのコネクションを獲得する。ユーザ名等はリソースファイルより獲得。</jp>
            //<en> Gets connection with DataBase. User's name and else will be obtained from resource file.</en>
            Connection conn = WmsParam.getConnection();

            //<jp> グループコントローラクラスからシステムに存在するすべてのグループコントローラ情報を得る。</jp>
            //<en> From the group controller class, gets all data of group controller existing in the system.</en>
            GroupController[] gpColection = GroupController.getInstances(conn);
            //<jp> グループコントローラ台数分 送信、受信処理を終了する。</jp>
            //<en> Termination of sending and receiving jobs for all group contoller machines.</en>
            for (int i = 0; i < gpColection.length; i++)
            {
                //<jp> 接続監視用スレッドの終了</jp>
                //<en> Termination of monitoring thread for connection</en>
                stop(As21Watcher.OBJECT_NAME + fmt.format(Integer.valueOf(gpColection[i].getNo())));
            }

            //<jp> 搬送指示送信処理用スレッドの終了</jp>
            //<en> Terminates thread for transmission job of carrying instruction.</en>
            stop(StorageSender.OBJECT_NAME);

            //<jp> 出庫指示送信処理用スレッドの終了</jp>
            //<en> Terminates thread for tranmission job of output command.</en>
            stop(RetrievalSender.OBJECT_NAME);

            //<jp> 自動モード切替搬送指示送信処理用スレッドの終了</jp>
            //<en> Terminates thread for transmission job of carrying instruction on automatic switching mode.</en>
            stop(AutomaticModeChangeSender.OBJECT_NAME);

            //<jp> 時間監視スレッドの終了</jp>
            //<en> Terminates thread for time monitoring.</en>
            stop(TimeKeeper.OBJECT_NAME);

            //<jp> リクエスト監視処理用スレッドの終了</jp>
            //<en> Terminates thread for request monitoring job.</en>
            stop(RequestWatcher.OBJECT_NAME);

            //<jp> 上記の常駐系プログラムが終了してからメッセージログサーバを終了させます。</jp>
            //<en> Exit the message log server after terminating the above mentioned resident program.</en>
            Thread.sleep(20000);

            //<jp> メッセージログサーバーの終了</jp>
            //<en> Terminates message log server.</en>
            stop(RmiMsgLogServer.LOGSERVER_NAME);
        }
        catch (ConnectException e)
        {
            //<jp> rmiregistryが起動していないため、スレッド終了処理を行えませんでした。</jp>
            //<en> RMI registry is not activated; thread termination cannot be processed.</en>
            RmiMsgLogClient.write(6016099, "ThreadOperation");
        }
        catch (Exception e)
        {
            //<jp> 致命的なエラーが発生しました。{0}</jp>
            //<en> Fatal error occurred.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6016102, e), "ThreadOperation");
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
    private static void stop(String name)
            throws ConnectException
    {
        try
        {
            RmiSendClient rmiSndC = new RmiSendClient();
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
            RmiMsgLogClient.write(WmsMessageFormatter.format(6016100, name), "ThreadOperation");
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
            RmiMsgLogClient.write(new TraceHandler(6016102, e), "ThreadOperation");
        }
    }
}
//end of class

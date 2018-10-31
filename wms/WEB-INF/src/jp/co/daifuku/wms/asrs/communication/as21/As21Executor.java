// $Id: As21Executor.java 8062 2013-05-27 03:53:08Z kishimoto $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;

/**<jp>
 * AS21通信処理を起動するためのクラスです。<BR>
 * システムに定義されているグループコントローラー定義をもとに
 * AS21通信スレッドをAGC台数分起動します。<BR>
 * このクラス自身は、必要台数分起動後、終了します。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author  $Author: kishimoto $
 </jp>*/
/**<en>
 * This class starts up as many communication modules as the number of AGC machines required.
 * It terminates as it starts up.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author  $Author: kishimoto $
 </en>*/
public class As21Executor
{
    // Class fields --------------------------------------------------
    /**<jp>
     * AGCとの通信処理をAGC台数分起動する時に、1AGCづつこの時間待って起動する。(mS)
     </jp>*/
    /**<en>
     * When the communication process with AGC establishes, it starts up with respective AGC at
     * interval of this duration of time given.(mS)
     </en>*/
    private static final int AGC_EXEC_TIME = 500;

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
     * 通信モジュールをシステム内のAGC台数分起動します。
     * 起動する数は、GROUPCONTROLLER定義を元に決定されます。
     * @param args (未使用)
     </jp>*/
    /**<en>
     * Starts up as many communication modules as AGC machines in system.
     </en>
     * @param args : not used
     */
    public static void main(String[] args)
    {
        Connection conn = null;
        try
        {

            //<jp> DataBaseへのコネクションを獲得する。ユーザ名等はリソースファイルより獲得。</jp>
            //<en> Gains connection with DataBase. Required information such as users name nad else will be gained</en>
            //<en> from the resource file.</en>
            conn = WmsParam.getConnection();
            if(!WmsParam.MULTI_ASRSSERVER)
            {
                //<jp> グループコントローラクラスからシステムに存在するすべてのグループコントローラ情報を得る。</jp>
                //<en> From the gourp controller class, it gains data of all group controller that exists in system. </en>
                GroupController[] gpColection = GroupController.getInstances(conn);
                //<jp> グループコントローラ台数分 送信、受信処理を作成する。</jp>
                //<en> Creates sending and receiving jobs for all group contoller machines.</en>
                for (int i = 0; i < gpColection.length; i++)
                {
                    //<jp> AGCとの接続用Utilityクラスのインスタンス獲得</jp>
                    //<jp> 接続監視用スレッド作成＆起動</jp>
                    //<en> Acquires instances of utility class for AGC connection.</en>
                    //<en> Creates and starts up the thread for connection monitoring.</en>
                    As21Watcher as21wach =
                            new As21Watcher(gpColection[i].getIP().getHostName(), gpColection[i].getPort(),
                                    gpColection[i].getNo());
                    new Thread(as21wach).start();

                    Thread.sleep(AGC_EXEC_TIME);
                }
            }
            else
            {
                for(String agcNumber : args)
                {
                    //<jp> グループコントローラクラスから指定されたグループコントローラ情報を得る。</jp>
                    //<en> From the gourp controller class, it gains data of all group controller that exists in system. </en>
                    GroupController gpColection = GroupController.getInstance(conn, agcNumber);
                    //<jp> AGCとの接続用Utilityクラスのインスタンス獲得</jp>
                    //<jp> 接続監視用スレッド作成＆起動</jp>
                    //<en> Acquires instances of utility class for AGC connection.</en>
                    //<en> Creates and starts up the thread for connection monitoring.</en>
                    As21Watcher as21wach =
                        new As21Watcher(gpColection.getIP().getHostName(), gpColection.getPort(),
                                gpColection.getNo());
                    new Thread(as21wach).start();

                    Thread.sleep(AGC_EXEC_TIME);
                }
            }
        }
        catch (Exception e)
        {
            //<jp>エラーをログファイルに落とす    </jp>
            //<en>Record errors in the log file.    </en>
            // 6026039=通信モジュールの起動に失敗しました。
            RmiMsgLogClient.write(new TraceHandler(6026039, e), "As21Executor");
        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (SQLException e)
                {
                    // 何もしない
                }
            }
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class


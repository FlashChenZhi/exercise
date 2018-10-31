// $Id: TimeKeeper.java 8062 2013-05-27 03:53:08Z kishimoto $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.rmi.RemoteException;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.rmi.RmiServAbstImpl;
import jp.co.daifuku.wms.base.common.WmsParam;

/**<jp>
 * 搬送指示、出庫指示のデータチェック間隔を調整するクラスです。<BR>
 * 定期的に搬送指示処理、出庫指示処理、自動モード切替出庫指示処理に対して
 * データ読み込みチェックを行なうように要求します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author  $Author: kishimoto $
 </jp>*/
/**<en>
 * This class adjust the interval of data checking of carry instruction and retrieval instruction.<BR>
 * It requests to perform the data-reading check periodicaly with carry instructions, retrieval instructions
 * and automatic mode switching retrieval instructions.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author  $Author: kishimoto $
 </en>*/
public class TimeKeeper
        extends RmiServAbstImpl
        implements java.lang.Runnable
{
    // Class fields --------------------------------------------------
    /**<jp>
     * リモートオブジェクトにバインドするオブジェクト名
     </jp>*/
    /**<en>
     * Object to bind to the remote object
     </en>*/
    public static final String OBJECT_NAME = "TimeKeeper";

    // Class variables -----------------------------------------------
    /**<jp>
     * 時間監視値
     </jp>*/
    /**<en>
     * Value of time keeping
     </en>*/
    protected int _sleepTime = 1000;

    /**<jp>
     * 搬送指示インスタンスへの参照
     </jp>*/
    /**<en>
     * reference to the instance of carry instruction
     </en>*/
    protected StorageSender _storageSender;

    /**<jp>
     * 出庫指示インスタンスへの参照
     </jp>*/
    /**<en>
     * Reference to the instance of retrieval instruction
     </en>*/
    protected RetrievalSender _retrievalSender;

    /**<jp>
     * 自動モード切替搬送指示インスタンスへの参照
     </jp>*/
    /**<en>
     * Reference to the instance of automatic mode switching retrieval instructions
     </en>*/
    protected AutomaticModeChangeSender _automaticModeChangeSender;

    /**<jp>
     * このフラグは、TimeKeeperクラスを終了するかどうか判断します。
     * ExitFlagがtrueになった場合、run()メソッド内の無限ループから抜けます。
     * このフラグを更新するためには、stop()メソッドを使用します。
     </jp>*/
    /**<en>
     * This flag determines whether/not to terminate the TimeKeeper class.
     * When ExitFlag changed to true, it pulls out of the infinite loop of run() method.
     * In order to update this flag, stop() method should be used.
     </en>*/
    private boolean wExitFlag = false;

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

    // Constructors --------------------------------------------------
    /**
     * AGC 番号を保持する変数。
     */
    private String _agcNo;

    /**<jp>
     * 搬送指示インスタンスへの参照
     </jp>*/
    /**<en>
     * reference to the instance of carry instruction
     </en>*/
    /**<jp>
     * 新しい<code>TimeKeeper</code>のインスタンスを作成します
     * 指定された間隔(ms)で<code>CarryInformation</code>を読込む様に搬送指示・出庫指示をキックします。
     * @param sleepTime <code>CarryInformation</code>の読込間隔
     * @param sSend     搬送指示インスタンス
     * @param rSend     出庫指示インスタンス
     * @param srSend    自動モード切替搬送指示インスタンス
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    /**<en>
     * Create new instance of <code>TimeKeeper</code>.
     * Request the carry instruction/retrieval instruction specifying the interval(ms) to read
     * <code>CarryInformation</code>
     * @param sleepTime : interval at which <code>CarryInformation</code> is read
     * @param sSend     : instance of carry instruction
     * @param rSend     : instance of retrieval instruction
     * @param srSend    : instance of automatic mode switching retrieval instructions
     * @throws RemoteException  Exception related to communication generated while executing remote method call
     </en>*/
    public TimeKeeper(int sleepTime, StorageSender sSend, RetrievalSender rSend, AutomaticModeChangeSender srSend)
            throws RemoteException
    {
        _sleepTime = sleepTime;
        _storageSender = sSend;
        _retrievalSender = rSend;
        _automaticModeChangeSender = srSend;

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
     * 新しい<code>TimeKeeper</code>のインスタンスを作成します
     * 指定された間隔(ms)で<code>CarryInformation</code>を読込む様に搬送指示・出庫指示をキックします。
     * @param sleepTime <code>CarryInformation</code>の読込間隔
     * @param sSend     搬送指示インスタンス
     * @param rSend     出庫指示インスタンス
     * @param srSend    自動モード切替搬送指示インスタンス
     * @param agcNumber
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    public TimeKeeper(int sleepTime, StorageSender sSend, RetrievalSender rSend, AutomaticModeChangeSender srSend, String agcNumber)
    throws RemoteException
    {
        _agcNo = agcNumber;
        _sleepTime = sleepTime;
        _storageSender = sSend;
        _retrievalSender = rSend;
        _automaticModeChangeSender = srSend;

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
//                    // unbind
//                    RmiUnbinder unbinder = new RmiUnbinder();
//                    if (unbinder.isbind(OBJECT_NAME + _agcNo))
//                    {
//                        unbinder.unbind(OBJECT_NAME + _agcNo);
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
     * 指定された時間をチェックし、搬送指示・出庫指示をキックします。
     </jp>*/
    /**<en>
     * Check the specified time and request the carry isntruction/retrieval instruction
     </en>*/
    public void run()
    {
        try
        {
            //<jp> ずっと動き続けること。</jp>
            //<en> Keep repeating the following action </en>
            while (wExitFlag == false)
            {
                try
                {
                	if (WmsParam.MULTI_ASRSSERVER)
                	{
	                    //<jp> このクラスをRMI Serverへ登録</jp>
	                    //<en> Register this class to the RMI Server</en>
	                    this.bind("//" + RmiSendClient.RMI_REG_SERVER + _agcNo + "/" + OBJECT_NAME + _agcNo);
                	}
                	else
                	{
                        //<jp> このクラスをRMI Serverへ登録</jp>
                        //<en> Register this class to the RMI Server</en>
                        this.bind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME);
                	}

                    while (wExitFlag == false)
                    {
                        synchronized (this)
                        {
                            this.wait(_sleepTime);

                            //<jp> 搬送指示・出庫指示をキックします。</jp>
                            //<en> Request the carry instruction/retrieval instruction.</en>
                            _storageSender.wakeup();
                            _retrievalSender.wakeup();
                            _automaticModeChangeSender.wakeup();
                        }
                    }
                }
                catch (Exception e)
                {
                    //<jp> 6026043=搬送指示タスクで異常が発生しました。例外：{0}</jp>
                    //<en> 6026043=Error occurred in transfer instruction task. Exception:{0}</en>
                    Object[] tobj = new Object[1];
                    tobj[0] = e.getClass().getName();
                    RmiMsgLogClient.write(new TraceHandler(6026043, e), this.getClass().getName(), tobj);
                    Thread.sleep(3000);
                }
            }
        }
        catch (Exception e)
        {
            //<jp> 6026043=搬送指示タスクで異常が発生しました。例外：{0}</jp>
            //<en> 6026043=Error occurred in transfer instruction task. Exception:{0}</en>
            Object[] tobj = new Object[1];
            tobj[0] = e.getClass().getName();
            RmiMsgLogClient.write(new TraceHandler(6026043, e), this.getClass().getName(), tobj);
        }
        finally
        {
            System.out.println("TimeKeeper:::::finally");

            // unbind
            this.unbind();
//            System.exit(0);
        }
    }

    /**<jp>
     * 使用されないメソッドです。上位で宣言されたメソッドを仮実装するために用意されています。
     * @param params 使用されません。
     </jp>*/
    /**<en>
     * This method is unused. This is prepared to virtually implement declared in upperclass.
     * @param params : unused.
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
     * Process terminates if this method is called by external.
     </en>*/
    public synchronized void stop()
    {
        //<jp> スレッドのループが終了するように、フラグを更新する。</jp>
        //<en> update the flag so that the thread stops looping.</en>
        wExitFlag = true;
        //<jp> このスレッドの待ち状態を解除する。</jp>
        //<en> release this thread from the wait state.</en>
        this.notify();
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class


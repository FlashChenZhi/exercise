// $Id: ReArrangeTimeKeeper.java 8062 2013-05-27 03:53:08Z kishimoto $
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
 * 配置替え引当のデータチェック間隔を調整するクラスです。<BR>
 * 配置替え引当処理に対してデータ読み込みチェックを行なうように要求します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author  $Author: kishimoto $
 </jp>*/
public class ReArrangeTimeKeeper
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
    public static final String OBJECT_NAME = "ReArrangeTimeKeeper";

    // Class variables -----------------------------------------------
    /**<jp>
     * 時間監視値
     </jp>*/
    /**<en>
     * Value of time keeping
     </en>*/
    protected int _sleepTime = 2000;

    /**<jp>
     * 配置替え引当インスタンスへの参照
     </jp>*/
    /**<en>
     * reference to the instance of carry instruction
     </en>*/
    protected ReArrangeAllocator _reArrangeAllocator;

    /**<jp>
     * このフラグは、ReArrangeTimeKeeperクラスを終了するかどうか判断します。
     * ExitFlagがtrueになった場合、run()メソッド内の無限ループから抜けます。
     * このフラグを更新するためには、stop()メソッドを使用します。
     </jp>*/
    /**<en>
     * This flag determines whether/not to terminate the ReArrangeTimeKeeper class.
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

    /**
     * AGC 番号を保持する変数。
     */
    private String _agcNo;

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
    /**<jp>
     * 搬送指示インスタンスへの参照
     </jp>*/
    /**<en>
     * reference to the instance of carry instruction
     </en>*/
    /**<jp>
     * 新しい<code>ReArrangeTimeKeeper</code>のインスタンスを作成します
     * 指定された間隔(ms)で<code>ReArrangeSetting</code>を読込む様に配置替え引当処理をキックします。
     * @param sleepTime <code>ReArrangeSetting</code>の読込間隔
     * @param reArrange     配置替え引当インスタンス
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    public ReArrangeTimeKeeper(int sleepTime, ReArrangeAllocator reArrange) throws RemoteException
    {
        _sleepTime = sleepTime;
        _reArrangeAllocator = reArrange;

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
     * 新しい<code>ReArrangeTimeKeeper</code>のインスタンスを作成します
     * Default間隔(1000ms)で<code>ReArrangeSetting</code>を読込む様に配置替え引当処理をキックします。
     * @param reArrange     配置替え引当インスタンス
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    public ReArrangeTimeKeeper(ReArrangeAllocator reArrange) throws RemoteException
    {
        _reArrangeAllocator = reArrange;

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
     * 新しい<code>ReArrangeTimeKeeper</code>のインスタンスを作成します
     * Default間隔(1000ms)で<code>ReArrangeSetting</code>を読込む様に配置替え引当処理をキックします。
     * @param reArrange     配置替え引当インスタンス
     * @param agcNumber AGCNo.
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    public ReArrangeTimeKeeper(ReArrangeAllocator reArrange, String agcNumber) throws RemoteException
    {
        _reArrangeAllocator = reArrange;
        _agcNo = agcNumber;

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

    // Public methods ------------------------------------------------
    /**<jp>
     * 指定された時間をチェックし、配置替え引当処理をキックします。
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
                	if(WmsParam.MULTI_ASRSSERVER)
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

                            //<jp> 配置替え引当処理をキックします。</jp>
                            _reArrangeAllocator.wakeup();
                        }
                    }
                }
                catch (Exception e)
                {
                    //<jp> 6026608=配置替え引当タスクで異常が発生しました。例外：{0}</jp>
                    Object[] tobj = new Object[1];
                    tobj[0] = e.getClass().getName();
                    RmiMsgLogClient.write(new TraceHandler(6026608, e), this.getClass().getName(), tobj);
                    Thread.sleep(3000);
                }
            }
        }
        catch (Exception e)
        {
            //<jp> 6026608=配置替え引当タスクで異常が発生しました。例外：{0}</jp>
            Object[] tobj = new Object[1];
            tobj[0] = e.getClass().getName();
            RmiMsgLogClient.write(new TraceHandler(6026608, e), this.getClass().getName(), tobj);
        }
        finally
        {
            System.out.println("ReArrangeTimeKeeper:::::finally");

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


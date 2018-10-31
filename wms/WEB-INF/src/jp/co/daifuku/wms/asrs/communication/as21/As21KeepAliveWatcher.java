// $Id: As21KeepAliveWatcher.java 4725 2009-07-22 04:05:38Z shibamoto $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.WmsParam;


/**<jp>
 * AS21通信のKeepAliveを監視するThreadクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/04/01</TD><TD>kubo</TD><TD>writeメソッドにAGCとの接続をチェック</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4725 $, $Date: 2009-07-22 13:05:38 +0900 (水, 22 7 2009) $
 * @author  $Author: shibamoto $
 </jp>*/
public class As21KeepAliveWatcher
        extends Thread
{
    // Class fields --------------------------------------------------
    /** <code>AGC_NUMBER_LENGTH</code> */
    private static final int AGC_NUMBER_LENGTH = 2;

    /** <code>AGC_PREFIX</code> */
    private static final String AGC_PREFIX = "AGC";

    /**<jp>
     * AGC との接続用Utilityクラスへの参照を保持する変数。
     </jp>*/
    /**<en>
     * Variables which preserves the reference to the utility class for the connection with AGC
     </en>*/
    private CommunicationAgc _agc = null;

    /**<jp>
     * AGC の番号を保持する変数
     </jp>*/
    /**<en>
     * Variables which preserves AGC numbers
     </en>*/
    private int _agcNo = 1;

    /**<jp>
     * 停止指示Flag
     </jp>*/
    /**<en>
     * Termination Flag for receiving process
     </en>*/
    private boolean _thStop = true;

    /**
     * KeepAlive発生認識時間
     */
    public static final long ALIVE_TIMER = WmsParam.AS21_KEEPALIVE_TIMER;

    /**
     * KeepAliveSACNタイマー値
     */
    public static final long CHECK_INTERVAL = WmsParam.AS21_KEEPALIVE_CHECK;

    /**
     * KeepAlive監視誤差値
     */
    public static final long ALIVE_MARGIN = WmsParam.AS21_KEEPALIVE_MARGIN;

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
        return ("$Revision: 4725 $,$Date: 2009-07-22 13:05:38 +0900 (水, 22 7 2009) $");
    }

    // Constructors --------------------------------------------------
    /**
     * AGCとの接続用Utilityクラス、起動元監視スレッド、AGC番号をセットし、このクラスの初期化を行います。
     * @param agc         AGCとの接続用Utilityクラス<code>CommunicationAgc</code>の参照 
     * @param agcNo   AGC番号
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     */
    public As21KeepAliveWatcher(CommunicationAgc agc, int agcNo)
            throws RemoteException
    {
        //<jp> AGC との接続用Utilityクラスへの参照をセット。</jp>
        _agc = agc;
        //<jp> AGC の番号をセット。</jp>
        _agcNo = agcNo;
        // 最終送信時間初期化
//        _agc.setSendEndDate(new Date());
        // 最終受信時間初期化
//        _agc.setReceiveEndDate(new Date());
        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " ALIVE_TIMER:" + ALIVE_TIMER));
        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " CHECK_INTERVAL:" + CHECK_INTERVAL));
        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " ALIVE_MARGIN:" + ALIVE_MARGIN));
    }


    /**
     * Threadのメイン処理です。
     * <BR>最終送信時刻と現在時刻の差を監視し、KeepAliveタイマー値を過ぎると
     * KeepAliveテキスト(EOTのみ）を送信します。
     * <BR>最終受信時刻と現在時刻の差を監視し、KeepAliveタイマー値を過ぎると
     * 回線切断条件に従い、再接続又は切断処理を行います。
     */
    @Override
    public void run()
    {
        while (_thStop)
        {
            // 受信処理がSocket回線オープンまで待機
            while (!_agc.isConnected())
            {
                // Socket回線未オープン時は、wait(notify待ち)
                synchronized (this)
                {
                    try
                    {
                        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21KeepAliveWatcher WAIT"));
                        wait();
                    }
                    catch (InterruptedException e)
                    {
                        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21KeepAliveWatcher Interrapt-1"));
                        break;
                    }
                }
            }

            try
            {
                if (_agc.isConnected())
                {
                    sleep(CHECK_INTERVAL);
                    
                    // 最終送信時間がKeepAlive監視時間を経過しているか判定する。
                    if (getCompareTime() > (ALIVE_TIMER - ALIVE_MARGIN))
                    {
                        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21KeepAliveWatcher Send-Timeout[" + new Date() + "]"));
                        // KeepALiveテキスト送信
                        _aliveSend(String.valueOf(_agcNo));
                    }                    
                }
                
            }
            catch (InterruptedException e)
            {
                // 処理なし
                DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21KeepAliveWatcher Interrapt-2"));
            }
            catch (Exception e)
            {
                // 処理なし
                DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21KeepAliveWatcher Exception"));
            }
        }
        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21KeepAliveWatcher STOPPED"));
        
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    /**
     * 現在の時刻と、最終送信日時との差をミリ秒で返します。
     * @return 最終送信日時からの経過時間（ミリ秒）
     */
    private long getCompareTime()
    {
        return (new Date().getTime() - _agc.getSendEndDate().getTime());
    }

    /**<jp>
     * RMIサーバーを経由して、テキスト送信処理タスクにKeepAliveテキスト送信を指示します。
     * @param agcNo        送信対象となるグループコントローラー(AGC)番号
     * @throws Exception   メッセージが送信できなかった場合に通知されます。
     </jp>*/
    /**<en>
     * Send the specified instruction text via RMI server to the text send process task.
     * @param agcNo Group controller (AGC) number
     * @throws Exception   Notifies if trouble occurs.
     </en>*/
    private static void _aliveSend(String agcNo)
            throws Exception
    {

        Object[] param = new Object[2];
        RmiSendClient rmiSndC = null;
        if (WmsParam.MULTI_ASRSSERVER)
        {
	        //<jp> RMIを利用してAs21Senderのwriteメソッドをコール</jp>
	        //<en> Calls the write method of As21Sender using RMI.</en>
	        rmiSndC = new RmiSendClient(RmiSendClient.RMI_REG_SERVER + agcNo);
        }
        else
        {
        	rmiSndC = new RmiSendClient();
        }
        param[0] = CommunicationAgc.ETX;
        try
        {
            rmiSndC.write(formatAGC(agcNo), param);
            rmiSndC = null;
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            //<jp> 6024030=AGCとの接続が確立されていません。テキストを送信できませんでした。AGCNO={0}</jp>
            //<en> 6024030=Cannot send the text since SRC is not connected. SRC No.={0}</en>
            Object[] tObj = {
                agcNo,
            };
            RmiMsgLogClient.write(6024030, LogMessage.F_WARN, SystemTextTransmission.class.getName(), tObj);
            throw e;
        }

        param[0] = null;
    }

    /**
     * AGC Noを送信用に編集します。
     * @param agcNo agcNo.
     * @return 送信用AGC No
     */
    static String formatAGC(String agcNo)
    {
        char[] agcFmt = new char[AGC_NUMBER_LENGTH];
        Arrays.fill(agcFmt, '0');

        DecimalFormat fmt = new DecimalFormat(String.valueOf(agcFmt));
        return AGC_PREFIX + fmt.format(Integer.valueOf(agcNo));
    }

    /**<jp>
     * 本スレッドの終了をします。
     * 外部よりこのメソッドを呼び出された場合、通信処理を終了します。
     </jp>*/
    /**<en>
     * Terminates this thread.
     * If this method is called externally, communication process will be terminated.
     </en>*/
    public synchronized void setStop()
    {
        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " KeepAliveWatcher STOP-Request"));
        _thStop = false;
    }

}
//end of class

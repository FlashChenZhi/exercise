// $Id: As21Sender.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Date;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.rmi.RmiServAbstImpl;
import jp.co.daifuku.wms.base.common.DEBUG;


/**<jp>
 * AS21通信プロトコルに対応した、テキスト送信処理を行なうクラスです。
 * 送信処理はRMIを経由して、外部から実行されます。
 * このクラスは、AGC単位でインスタンス化されて使用されます。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/04/01</TD><TD>kubo</TD><TD>writeメソッドにAGCとの接続をチェック</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </jp>*/
/**<en>
 * This task conducts sending of communication messages to AGC. Practically, it will
 * be registered in RMI server then called by remote.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </en>*/
public class As21Sender
        extends RmiServAbstImpl
{
    // Class fields --------------------------------------------------
    /**<jp>
     * リモートオブジェクトにバインドするオブジェクト名
     </jp>*/
    /**<en>
     * Name of the object which bind to remote object.
     </en>*/
    public static final String OBJECT_NAME = "AGC";

    // Class variables -----------------------------------------------
    /**<jp>
     * AGC との接続用Utilityクラスへの参照を保持する変数。
     </jp>*/
    /**<en>
     * Variables which preserves the reference to Utility class for the connection with AGC.
     </en>*/
    private CommunicationAgc _agc = null;

    /**<jp>
     * AGC との通信上のSequence Number
     </jp>*/
    /**<en>
     * Sequence Number for the communication with AGC
     </en>*/
    private int _seqNum = CommunicationAgc.INITIAL_NUMBER;

    /**<jp>
     * AGC との通信上の停止制御を行うための変数
     </jp>*/
    /**<en>
     * Variables which performs the stop control over the communication with AGC
     </en>*/
    private boolean _thStop = false;

    /**<jp>
     * AGC の番号を保持する変数
     </jp>*/
    /**<en>
     * Variables which preserves AGC numbers
     </en>*/
    private int _agcNo = 1;

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
        return ("$Revision: 7996 $,$Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * AGCとの接続用Utilityクラスをセットし、このクラスの初期化を行います。
     * @param agc         AGCとの接続用Utilityクラス<code>CommunicationAgc</code>の参照
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    /**<en>
     * Sets the reference to the Unitility class for the connection with AGC; then implements the initialization of this class.
     * @param agc         : reference to the <code>CommunicationAgc</code>, which is the Unitility class for the connection with AGC
     * @throws RemoteException  Exception related to communication generated while executing remote method call
     </en>*/
    public As21Sender(CommunicationAgc agc)
            throws RemoteException
    {
        //<jp> AGC との接続用Utilityクラスへの参照をセット。</jp>
        //<en> Sets the reference to the Utility class for the connection with AGC.</en>
        _agc = agc;
        _agcNo = 0;
        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21Sender START"));
    }

    /**<jp>
     * AGCとの接続用Utilityクラスをセットし、このクラスの初期化を行います。
     * @param agc         AGCとの接続用Utilityクラス<code>CommunicationAgc</code>の参照
     * @param agcNo   AGC番号
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    /**<en>
     * Sets the reference to the Unitility class for the connection with AGC; then implements the initialization of this class.
     * @param agc         : reference to the <code>CommunicationAgc</code>, which is the Unitility class for the connection with AGC
     * @param agcNo       : AGC Number
     * @throws RemoteException  Exception related to communication generated while executing remote method call
     </en>*/
    public As21Sender(CommunicationAgc agc, int agcNo)
            throws RemoteException
    {
        //<jp> AGC との接続用Utilityクラスへの参照をセット。</jp>
        //<en> Sets the reference to the Utility class for the connection with AGC.</en>
        _agc = agc;
        _agcNo = agcNo;
        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21Sender START"));
    }

    /**<jp>
     * CarryInformationControllerからコールされ、AGCへ電文を送信する。
     * @param params AGCへの送信電文
     * @throws IOException            ファイルI/Oエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Be called by CarryInformationController, then sends messages to AGC.
     * @param params Sending message to AGC
     * @throws IOException            : Notifies if file I/O error occured.
     </en>*/
    @Override
    public void write(Object[] params)
            throws IOException
    {
        //<jp> AGCと接続済みなら</jp>
        //<en>If the connection with AGC is already established,</en>
        if (_agc.isConnected())
        {
            String text = String.valueOf(params[0]);
            synchronized (_agc)
            {
                // 2010/07/30 Y.Osawa ADD ST
                try
                {
                // 2010/07/30 Y.Osawa ADD ED
                    // 送信要求テキストがnull又は0Byte時、KeepAlive送信要求とする
                    if (text.length() == 1 && Integer.valueOf(text) == CommunicationAgc.ETX)
                    {
                        _agc.keepAliveSend();
                    }
                    else
                    {
                        _agc.send(text, _seqNum);
                        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21Sender TEXT Send"));
                        _seqNum++;
                        if (_seqNum > CommunicationAgc.MAX_SEQ_NUMBER)
                        {
                            _seqNum = CommunicationAgc.LOOP_START_NUMBER;
                        }
                    }
                // 2010/07/30 Y.Osawa ADD ST
                }
                catch (Exception e)
                {
                    // 6026614=ソケットの送信エラーが発生しました。AGCNO={0} Text={1} StackTrace={2}
                    Object[] objAgc = new Object[2];
                    objAgc[0] = new Integer(_agcNo);
                    objAgc[1] = text;
                    RmiMsgLogClient.write(new TraceHandler(6026614, e), getClass().getName(), objAgc);
                    throw (new IOException("Send Error : " + _agc._host));
                }
                // 2010/07/30 Y.Osawa ADD ED
            }
        }
        else
        {
            throw (new IOException("Connection Refuesed : " + _agc._host));
        }
    }

    /**<jp>
     * 使用されないメソッドです。上位で宣言されたメソッドを仮実装するために用意されています。
     </jp>*/
    /**<en>
     * This method is not for use. This is prepared in order to provisionally implement the method
     * declared in upper class.
     </en>*/
    @Override
    public synchronized void stop()
    {
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

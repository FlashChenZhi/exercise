// $Id: As21Receiver.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import jp.co.daifuku.wms.asrs.control.IdProcess;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;

/**<jp>
 * AS21通信プロトコルに対応した、テキスト受信処理を行なうクラスです。<BR>
 * １インスタンスごとに１つのAGCとの通信を行ないます。<BR>
 * テキスト受信後、IDに対応した処理は、このクラスから起動される
 * <code>IdProcess</code>を継承したクラスによって実行されます。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/04/01</TD><TD>kubo</TD><TD>runメソッドでAGCの接続メッセージを分けました</TD></TR>
 * <TR><TD>2004/04/01</TD><TD>inoue</TD><TD>切断、再接続時のログの落とし方を変更</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </jp>*/
/**<en>
 * Receives the communication message from AGC, and starts up the corresponding task of each ID.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/04/01</TD><TD>kubo</TD><TD>Created the individual messages for AGC connections in run method.</TD></TR>
 * <TR><TD>2004/04/01</TD><TD>inoue</TD><TD>Corrected the method of log recording at the disconnection/reconnection.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </en>*/
public class As21Receiver
        extends Thread
{
    // Class fields --------------------------------------------------
    /**<jp>
     * シーケンスNo.の長さ
     </jp>*/
    /**<en>
     * Length of sequence No.
     </en>*/
    public static final int SEQ_LENG = 4;

    // Class variables -----------------------------------------------
    /**<jp>
     * 起動元である監視スレッドへの参照を保持する変数
     </jp>*/
    /**<en>
     * Variables which preserves the reference to the monitor thread, the base of start-up process
     </en>*/
    private As21Watcher _as21Watcher = null;

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
     * 受信処理停止指示Flag
     </jp>*/
    /**<en>
     * Termination Flag for receiving process
     </en>*/
    private boolean _thStop = true;

    /**<jp>
     * Sequence Number Check 用受信予定Sequence Number記憶域
     </jp>*/
    /**<en>
     * Memory location for the Sequence Number of to-receive messages for use of Sequence Number Check
     </en>*/
    private int _seqNumInt = 0;

    /**<jp>
     * Sequence Number Check 用一時記憶域
     </jp>*/
    /**<en>
     * Temporary memory area for checking Sequence Number
     </en>*/
    private int _wkSeqNumInt = 0;

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
     * AGCとの接続用Utilityクラス、起動元監視スレッド、AGC番号をセットし、このクラスの初期化を行います。
     * @param agc         AGCとの接続用Utilityクラス<code>CommunicationAgc</code>の参照
     * @param as21Watcher 起動元監視スレッド<code>As21Watcher</code>の参照
     * @param agcNo   AGC番号
     </jp>*/
    /**<en>
     * Sets the reference to the Unitility class for the connection with AGC and the reference to
     * the start-originating monitor thread and AGC numbers; then implements the initialization of this class.
     * @param agc         : reference to the <code>CommunicationAgc</code>, which is the Unitility class for the connection with AGC
     * @param as21Watcher : reference to the monitor thread<code>As21Watcher</code>, which is the origination of start-up process
     * @param agcNo   : AGC number
     </en>*/
    public As21Receiver(CommunicationAgc agc, As21Watcher as21Watcher, int agcNo)
    {
        //<jp> 起動元監視スレッドへの参照をセット</jp>
        //<en> Sets the reference to the start-originating monitor thread.</en>
        _as21Watcher = as21Watcher;
        //<jp> AGC との接続用Utilityクラスへの参照をセット。</jp>
        //<en> Sets the reference to the Unitility class for the connection with AGC.</en>
        _agc = agc;
        //<jp> AGC の番号をセット。</jp>
        //<en> Sets AGC numbers.</en>
        _agcNo = agcNo;
        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21Receiver CREATE"));
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * ID毎の処理を起動し、受信データを渡して処理を依頼する。
     * @param id 受信電文対応処理の識別ID
     * @param byteArray 受信電文対応処理へ渡す電文
     * @param wHt ID毎の処理が既に起動済みか否かを調べるテーブルへの参照。
     * @throws InvalidDefineException IdProcess以外のインスタンスが生成された場合に通知されます。
     </jp>*/
    /**<en>
     * Starts up the operation per ID, passes the received data and requests processing.
     * @param id  :identification ID# for processing of received communication messages
     * @param byteArray :communication message to pass to the processing of received message
     * @param wHt :reference to the table in order to check whether/not the procesing per ID
     * has already started .
     * @throws InvalidDefineException :Notifies if any instance other than IdProcess was generated.
     </en>*/
    public void execIdProcess(String id, byte[] byteArray, Hashtable wHt)
            throws InvalidDefineException
    {
        //<jp> HashTable中に既にインスタンスを作ってRUNさせているものが居ないかをチェックする。</jp>
        //<en> Checks whether/not any instance has been alreade created and running in HashTable.</en>
        IdProcess idPro = null;
        if (wHt.containsKey(id))
        {
            //<jp> HashTableからデータを渡すスレッドへの参照を取り出す。</jp>
            //<en> Remove the reference to the thread that data is given from HashTable.</en>
            Object oIdPro = wHt.get(id);
            if (oIdPro instanceof IdProcess)
            {
                idPro = (IdProcess)oIdPro;
            }
            //<jp> 動作しているスレッドに対してデータを渡す。</jp>
            //<en> Passes data to the active thread.</en>
            idPro.write(byteArray);
        }
        else
        {
            //<jp> インスタンスがRUNしていなければ、作成してRUNさせる。</jp>
            //<en> If there is no instance running, generate one and let run.</en>
            try
            {
                idPro = this.firstExecIdProcess(id, byteArray);
                //<jp> ID毎に生成されるインスタンスをHashTableへ登録</jp>
                //<en> Registers the instances, generated for each ID, to the HashTable</en>
                wHt.put(id, idPro);
            }
            catch (InvalidDefineException e)
            {
                throw (new InvalidDefineException());
            }
        }
    }

    /**<jp>
     * ID毎の処理を初回のみ起動し、受信データを渡して処理を依頼する。
     * @param id 受信電文対応処理の識別ID
     * @param bytedata 受信電文対応処理へ渡す電文
     * @return IdProcessインスタンス
     * @throws InvalidDefineException IdProcess以外のインスタンスが生成された場合に通知されます。。
     </jp>*/
    /**<en>
     * Starts up the operation per ID on the first attempt only; passes the received data and request processing.
     * @param id : identification ID# for processing the received communication messages
     * @param bytedata :communication message to pass to the processing of received message
     * @return instance of IdProcess
     * @throws InvalidDefineException :Notifies if any instance other than IdProcess was generated.
     </en>*/
    public IdProcess firstExecIdProcess(String id, byte[] bytedata)
            throws InvalidDefineException
    {

        IdProcess idP = null;
        try
        {
            //<jp> IdXXProcessと言った名前のクラスをロードする。</jp>
            //<jp> XX部分は電文中から取得するIDによって決まる。</jp>
            //<en> Loads class by the name IdXXProcess</en>
            //<en> XX can be filled with ID gained from the message text.</en>
            String classname = "jp.co.daifuku.wms.asrs.control.Id" + id + "Process";
            //String classname = "Id" + id + "Process";
            Class tClass = Class.forName(classname);
            //<jp> byte型の配列として教えるためのオブジェクトを作成</jp>
            //<en> Handles byte-type array as objects.</en>
            Object pdt = Array.newInstance(Byte.TYPE, bytedata.length);
            //<jp> 作成したいクラスのコンストラクターを探す為にオブジェクト(pdt)からクラスを得る。</jp>
            //<en> Gets class from the object (pdt) so that the proper constructor should be found. </en>
            //<en> Practically, byte-type array is reuired.</en>
            Class[] paraClass = new Class[1];
            paraClass[0] = pdt.getClass();
            //<jp> ここはIdXXProcessクラスのコンストラクターがIntの引数を取る</jp>
            //<en> Here it gets the constructor of IdXXProcess class which has Int-type arguments.</en>
            Class[] p1 = {
                String.class
            };
            Constructor tConst = tClass.getConstructor(p1);
            //<jp> ここはIdXXProcessクラスのコンストラクターがIntの引数を取る(AGC Numberを渡す)</jp>
            //<en> Here, passes AGC number to the constructor of IdXXProcess class.</en>
            Object[] objAgc = new Object[1];
            objAgc[0] = String.valueOf(_agcNo);
            Object tinst = tConst.newInstance(objAgc);
            //<jp> IdProcessインスタンスをセットする。</jp>
            //<en> Sets instance of IdProcess</en>
            if (tinst instanceof IdProcess)
            {
                idP = (IdProcess)tinst;
            }
            else
            {
                throw (new InvalidDefineException());
            }
            //<jp> IdXXProcessを起動する。</jp>
            //<en> Starts-up IdXXProcess.</en>
            idP.start();
            //<jp> IdXXProcessのwriteメソッドを呼び出してデータを渡す。</jp>
            //<en> Calls write method of IdXXProcess and passes the data.</en>
            idP.write(bytedata);
        }
        catch (InvalidDefineException e)
        {
            throw (new InvalidDefineException());
        }
        catch (Exception e)
        {
            //<jp>エラーをログファイルに落とす</jp>
            //<en>Records error in the log file.</en>
            //<jp>6016030=通信モジュールの受信タスクでID対応処理の起動時に異常が発生。</jp>
            //<en>6016030=Error occurred in receiving task of communication module when the ID process was started.</en>
            RmiMsgLogClient.write(new TraceHandler(6016030, e), getClass().getName());
        }
        return (idP);
    }

    /**<jp>
     * テキスト受信メイン処理を行います。
     * AGCとの接続を行い、テキストの受信処理を行ないます。
     * 外部より<code>setStop()</code>メソッドを通じて終了要求を受け取った場合、スレッドを終了します。
     </jp>*/
    /**<en>
     * Conducts the text receiving process.
     </en>*/
    @Override
    public void run()
    {
        int count = 0;
        Hashtable wHt = new Hashtable();

        try
        {
            count = 1;
            DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21Receiver START"));
            _agc.connect();
            // 最終送信時間初期化
            _agc.setSendEndDate(new Date());
            DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21Receiver SEND-ENDTIME["
                    + _agc.getSendEndDate() + "] Agc-" + _agcNo));

            // KeepAliveスレッドの待ち状態を開放
            if (_as21Watcher.getAs21KeepAliveInstance() != null)
            {
                synchronized (_as21Watcher.getAs21KeepAliveInstance())
                {
                    _as21Watcher.getAs21KeepAliveInstance().notifyAll();
                }
            }
            //<jp> ID毎の処理が既に起動済みか否かを調べるテーブル。</jp>
            //<jp> 停止フラグが起動元である監視スレッドより停止指示を受けるまでループ</jp>
            //<en> Table to check whether/not the process per ID has already started.</en>
            //<en> Keeps looping until terminating flag changes to 'terminate' by command.</en>
            DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21Receiver CONNECT"));
            while (_thStop)
            {
                count = 2;
                //<jp> ログを落ちるようにする。</jp>
                //<en> Lets record in log.</en>
                if (!_as21Watcher.getLoggingFlag())
                {
                    Object[] objAgc = new Object[1];
                    objAgc[0] = new Integer(_agcNo);
                    //<jp> 6020032=通信モジュールの送受信タスクを起動しました AGCNO={0}</jp>
                    //<en> 6020032=Send/Receive task of the communication module is started. SRC NO={0}</en>
                    RmiMsgLogClient.write(6020032, LogMessage.F_INFO, "As21Receiver", objAgc);
                    _as21Watcher.setLoggingON();
                }

                DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21Receiver RECV-WAIT"));
                //<jp> AGC より電文を受信</jp>
                //<en> Receiving message from AGC</en>
                byte[] rdata = _agc.recv();

                // KeepAliveデータチェック
                if (rdata[0] == CommunicationAgc.ETX)
                {
                    DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21Receiver Keep-Alive RECEIVED"));
                    continue;
                }

                // SEQ No. Check
                if (!seqNumCheck(rdata))
                {
                    //<jp> 6024023=受信電文のシーケンス番号が先の受信電文と同じです。</jp>
                    //<en> 6024023=Sequence number of received message is the same as the previous one.</en>
                    Object[] tobj = new Object[1];
                    tobj[0] = null;
                    RmiMsgLogClient.write(6024023, LogMessage.F_WARN, "As21Receiver", tobj);
                    continue;
                }

                int wKbyteLength = rdata.length;
                byte[] wKbyte = new byte[wKbyteLength - SEQ_LENG];
                for (int jj = 0; jj < wKbyteLength - SEQ_LENG; jj++)
                {
                    wKbyte[jj] = rdata[jj + SEQ_LENG];
                }
                //<jp> 受信電文の種別を判断するためIDを調べる。</jp>
                //<en> Checks the ID adn determine type of the message.</en>
                IdMessage idMes = new IdMessage(wKbyte);
                //<jp> ID毎の起動処理</jp>
                //<en> Start-up process of each ID</en>
                this.execIdProcess(idMes.getID(), wKbyte, wHt);
                DEBUG.MSG("AS21",
                        ("[" + new Date() + "] Agc-" + _agcNo + " As21Receiver TEXT RECEIVED ID=" + idMes.getID()));
            }
        }
        catch (SocketTimeoutException e)
        {
            DEBUG.MSG("AS21", "[" + new Date() + "] Agc-" + _agcNo + " Receive-Timeout");
            // 2010/07/30 Y.Osawa ADD ST
            if (_as21Watcher.getLoggingFlag())
            {
                // 6024070=ソケット受信のタイムアウトが発生しました。AGCNO={0}
                Object[] objAgc = new Object[1];
                objAgc[0] = new Integer(_agcNo);
                RmiMsgLogClient.write(new TraceHandler(6024070, e), getClass().getName(), objAgc);
                _as21Watcher.setLoggingOFF();
            }
            // 2010/07/30 Y.Osawa ADD ED
        }
        catch (Exception e)
        {
            DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " count;;;" + count));
            if (_as21Watcher.getLoggingFlag())
            {
                if (count == 1)
                {
                    //<jp>エラーをログファイルに落とす</jp>
                    //<en>Records error in the log file.</en>
                    //<jp>6024033=AGCと接続できません(Connection Refused)AGCNO={0}</jp>
                    //<en>6024033=Cannot connect with SRC. (Connection Refused) SRC NO={0}</en>
                    Object[] objAgc = new Object[1];
                    objAgc[0] = new Integer(_agcNo);
                    RmiMsgLogClient.write(new TraceHandler(6024033, e), getClass().getName(), objAgc);
                    _as21Watcher.setLoggingOFF();
                }
                else
                {
                    //<jp>エラーをログファイルに落とす</jp>
                    //<en>Records error in the log file.</en>
                    //<jp>6024032=AGCとの接続が切断されました。AGCNO={0}</jp>
                    //<en>6024032=Connection with SRC is disconnected. SRC NO={0}</en>
                    Object[] objAgc = new Object[1];
                    objAgc[0] = new Integer(_agcNo);
                    RmiMsgLogClient.write(new TraceHandler(6024032, e), getClass().getName(), objAgc);
                    _as21Watcher.setLoggingOFF();
                }
            }
        }
        finally
        {
            DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21Receiver ::: finally"));
            //<jp> ID毎のスレッド終了処理</jp>
            //<en> Terminating thread per ID</en>
            finish(wHt);
            //<jp> 起動元である監視スレッドに対し、受信処理が終了した事を通知する。</jp>
            //<en> Notifies monitor thread, the origination of start-up, that receiving process terminated.</en>
            _as21Watcher.setAs21ReceiverInstance();
            //<jp> 起動元である監視スレッドに対し、割り込みを送る。</jp>
            //<en> Sends interruption to the monitor thread (the originaion of start-up)</en>

            // 2008/1/26 UPDATE START
            // waitによる待機から、As21Receiverスレッドの終了を待機するよう方法に変更したため、
            // wakeup()メソッドは呼ばない。
            //_as21Watcher.wakeup();
            // 2008/1/26 UPDATE END
        }
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
        DEBUG.MSG("AS21", ("[" + new Date() + "] Agc-" + _agcNo + " As21Receiver STOP-Request"));
        _thStop = false;
        this.notifyAll();
    }

    /**<jp>
     * AGC との接続用Utilityクラスへの参照を返します。
     * @return AGCとの接続用Utilityクラス<code>CommunicationAgc</code>の参照
     </jp>*/
    /**<en>
     * Returns the reference to the Utility class for the connection with AGC.
     * @return reference to the <code>CommunicationAgc</code>, which is the Unitility class for the connection with AGC
     </en>*/
    public CommunicationAgc getCommunicationAgc()
    {
        return _agc;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * ID毎に起動したスレッドの終了処理を行います。
     * @param ht ID毎の処理が既に起動済みか否かを調べるテーブルへの参照。
     </jp>*/
    /**<en>
     * Handles termination process of the thread started by each ID.
     * @param ht Reference to teh table to check whether/not the processing has alrady started
     * by each ID.
     </en>*/
    private void finish(Hashtable ht)
    {
        if (ht == null)
        {
            return;
        }

        IdProcess idPro = null;
        //<jp> HashTableから値のリストを取り出します</jp>
        //<en> Takes the list of value out of HashTable.</en>
        for (Enumeration e = ht.elements(); e.hasMoreElements();)
        {
            Object elemnt = e.nextElement();
            if (elemnt instanceof IdProcess)
            {
                idPro = (IdProcess)elemnt;
            }

            //<jp> 動作しているスレッドに対して終了処理を行う。</jp>
            //<en> Handles termination process for active threads.</en>
            idPro.finish();
        }

    }

    /**<jp>
     * 受信データのSequenceNumberをCheckします。
     * @param seqData 受信電文
     * @return true  :Sequence Numberが正常<BR>
     *          false :Sequence Numberが異常
     </jp>*/
    /**<en>
     * Checks the Sequence Number of received data.
     * @param seqData received message
     * @return true  :Sequence number is OK.<BR>
     *          false :Sequence number is error.
     </en>*/
    boolean seqNumCheck(byte[] seqData)
    {
        //<jp> 受信伝文中のSequence Numberを取り出し、Intに変換</jp>
        //<en> Takes out the Sequence Number in the message and convert to Int.</en>
        byte[] seqWk = new byte[SEQ_LENG];
        for (int i = 0; i < SEQ_LENG; i++)
        {
            seqWk[i] = seqData[i];
        }
        String seqNumString = new String(seqWk);
        int recvSeqNumInt = Integer.parseInt(seqNumString);
        //<jp> 受信Sequence Numberと受信予定Sequence Numbrを比較する。</jp>
        //<jp> 受信Sequence Number が0なので最初の受信</jp>
        //<en> Compares the received Sequence Number and Sequence Numbr to-receive.</en>
        //<en> If the Sequence Number of received message is 0, it is the first message received.</en>
        if (recvSeqNumInt == CommunicationAgc.INITIAL_NUMBER)
        {
            _seqNumInt = CommunicationAgc.LOOP_START_NUMBER;
        }
        //<jp> 受信Sequence Numberと受信予定Sequence Numberが同じなので正常</jp>
        //<en> If the Sequence Number of received message is the same as Sequence Number of to-receive</en>
        //<en> messages, normal</en>
        else if (recvSeqNumInt == _seqNumInt)
        {
            _seqNumInt++;
            if (_seqNumInt > CommunicationAgc.MAX_SEQ_NUMBER)
            {
                _seqNumInt = CommunicationAgc.LOOP_START_NUMBER;
            }
        }
        //<jp> 受信Sequence Numberが一つ前のものか、まったく予定と違う</jp>
        //<en> If the Sequence Number of received message is the last but one, or something unforeseen.</en>
        else
        {
            _wkSeqNumInt = _seqNumInt - 1;
            if (_wkSeqNumInt < CommunicationAgc.LOOP_START_NUMBER)
            {
                _wkSeqNumInt = CommunicationAgc.MAX_SEQ_NUMBER;
            }
            //<jp>  受信Sequence Numberが一つ前のものなので、破棄。</jp>
            //<en>  If the Sequence Number of received message is the last but one, scrap.</en>
            if (recvSeqNumInt == _wkSeqNumInt)
            {
                return (false);
            }

            //<jp>  受信Sequence Numberがまったく違うので、次をこれ＋１にする。</jp>
            //<en>  If unforseen Sequence Number has been provided to the received message, number the next </en>
            //<en>  Sequence Number by adding 1 to it (+1).</en>
            _seqNumInt = recvSeqNumInt + 1;
            if (_seqNumInt > CommunicationAgc.MAX_SEQ_NUMBER)
            {
                _seqNumInt = CommunicationAgc.LOOP_START_NUMBER;
            }
        }

        return (true);
    }
}
//end of class

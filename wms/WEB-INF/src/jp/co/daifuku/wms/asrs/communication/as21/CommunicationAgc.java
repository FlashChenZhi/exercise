// $Id: CommunicationAgc.java 8053 2013-05-15 01:00:52Z kishimoto $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
//DFKLOOK 3.5 Start
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
//DFKLOOK 3.5 End
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.util.Date;

import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
//DFKLOOK 3.5 Start
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;

//DFKLOOK 3.5 End
import jp.co.daifuku.common.RandomAcsFileHandler;
import jp.co.daifuku.wms.base.common.WmsParam;

/**<jp>
 * AS21プロトコルでのテキスト送受信処理を行なうクラスです。<BR>
 * 指定されたグループコントローラーに対して接続を行い、テキストの送受信を行ないます。
 * テキストの送受信にはTCP/IPプロトコルを用います。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  $Author: kishimoto $
 </jp>*/
/**<en>
 * This class sends/receives texts according to AS21 ptrotocol.
 * Connection is made with specified group controller, then text is sent/received.
 * Send/receive txet are done according to TCP/IP protocol.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  $Author: kishimoto $
 </en>*/
public class CommunicationAgc
        extends Object
{
    // Class fields --------------------------------------------------
    /**<jp>
     * AS21でのAGC側サーバ名(Default)を定義します。
     </jp>*/
    /**<en>
     * Defines the default server name on AGC side for AS21.
     </en>*/
    public static final String DEFAULT_NAME = "AGC01";

    /**<jp>
     * AS21でのAGC側サーバポート(Default)を定義します。
     </jp>*/
    /**<en>
     * Defines the default server port on AGC side for AS21.
     </en>*/
    public static final int DEFAULT_PORT = 2000;

    /**<jp>
     * AS21での通信電文長を定義します。
     </jp>*/
    /**<en>
     * Defines the length of communication message of AS21.
     </en>*/
    public static final int DEFAULT_LENGTH = 512;

    /**<jp>
     * AS21での通信電文最低長を定義します。
     </jp>*/
    /**<en>
     * Defines the least length of communication message of AS 21.
     </en>*/
    public static final int MINIMAM_LENGTH = 24;

    /**<jp>
     * AS21での電文の開始マークSTXを定義します。
     </jp>*/
    /**<en>
     * Defines the start mark STX of the communication message according to AS21.
     </en>*/
    public static final int STX = 0x02;

    /**<jp>
     * AS21での電文の終端マークETXを定義します。
     </jp>*/
    /**<en>
     * Defines the end mark ETX of the communication message according to AS21.
     </en>*/
    public static final int ETX = 0x03;

    /**<jp>
     * AS21でのSeqence No. Formatを定義します。
     </jp>*/
    /**<en>
     * Defines the Seqence No. Format according to AS21.
     </en>*/
    public static final String SEQ_FORMAT = "0000";

    /**<jp>
     * AS21での電文文字コードを定義します。
     </jp>*/
    /**<en>
     * Defines the character coding of communication message according to AS21.
     </en>*/
    public static final String CODE = "Shift_JIS";

    /**<jp>
     * リソース名の定義(トレースディレクトリ)
     </jp>*/
    /**<en>
     * Defines the name of resource (trace directory)
     </en>*/
    public static final String LOGS_PATH = "LOGS_PATH";

    /**<jp>
     * リソース名の定義(送信トレースポインタファイル名)
     </jp>*/
    /**<en>
     * Defines the name of resource (file name of Send trace pointer)
     </en>*/
    public static final String S_POINTER_NAME = "AS21_SEND_TRACE_POINTER_NAME";

    /**<jp>
     * リソース名の定義(受信トレースポインタファイル名)
     </jp>*/
    /**<en>
     * Defines the name of resource (file name of Receive file pointer)
     </en>*/
    public static final String R_POINTER_NAME = "AS21_RECEIVE_TRACE_POINTER_NAME";

    /**<jp>
     * リソース名の定義(通信動作ログON)
     </jp>*/
    /**<en>
     * Defines the name of resource (log-on the action of communication )
     </en>*/
    public static final String A_LOG_ON = "AS21_ACTION_LOG_ON";

    /**<jp>
     * リソース名の定義(通信動作ログのファイル名)
     </jp>*/
    /**<en>
     * Defines the name of resource (file name of the operation log of communication)
     </en>*/
    public static final String A_LOG_NAME = "AS21_ACTION_LOG_NAME";

    /**<jp>
     * リソース名の定義(トレースファイルのMaxサイズ(Byte))
     </jp>*/
    /**<en>
     * Defines the name of resource (MAX. of the trace file size, in byte)
     </en>*/
    public static final String MAX_SIZE = "TRACE_MAX_SIZE";

    /**<jp>
     * AGC との通信上のSequence Numberとしての最大値
     </jp>*/
    /**<en>
     * Maximal value as a sequence number in communicating with AGC
     </en>*/
    public static final int MAX_SEQ_NUMBER = 9999;

    /**<jp>
     * AGC との通信上のSequence NumberとしてのInitial時の値
     </jp>*/
    /**<en>
     * Initial value as a sequesnce number in communicating with AGC
     </en>*/
    public static final int INITIAL_NUMBER = 0;

    /**<jp>
     * AGC との通信上のSequence NumberとしてのLOOP時のStart値
     </jp>*/
    /**<en>
     * Start value of the loop as a sequence number in communicating with AGC
     </en>*/
    public static final int LOOP_START_NUMBER = 1;

    /**
     * KeepAlive発生認識時間
     */
    public static final long ALIVE_TIMER = WmsParam.AS21_KEEPALIVE_TIMER;

    /**
     * KeepAlive監視誤差値
     */
    public static final long ALIVE_MARGIN = WmsParam.AS21_KEEPALIVE_MARGIN;

    /**
     * 受信KeepAlive発生後、切断の可否
     * True:切断後再接続　false:待ち状態
     */
    public static final Boolean ALIVE_RESTART = WmsParam.AS21_KEEPALIVE_RECONNECT;

    /**
     * メッセージ番号 送信
     */
    public static final int SEND = 0;

    /**
     * メッセージ番号 受信
     */
    public static final int RECV = 1;
    // Class variables -----------------------------------------------
    /**<jp>
     * AGC側サーバ名
     </jp>*/
    /**<en>
     * Name of AGC server
     </en>*/
    protected String _host;

    /**<jp>
     * AGC側サーバポート
     </jp>*/
    /**<en>
     * AGC's server port
     </en>*/
    protected int _port;

    /**<jp>
     * AGC側との接続Socket
     </jp>*/
    /**<en>
     * COnnection socket with AGC
     </en>*/
    protected Socket _socket = null;

    /**<jp>
     * AGCへの出力・データストリーム
     </jp>*/
    /**<en>
     * Output data stream to AGC
     </en>*/
    protected DataOutputStream _dataOut;

    /**<jp>
     * AGCからの入力・データストリーム
     </jp>*/
    /**<en>
     * INput data stream from AGC
     </en>*/
    protected DataInputStream _dataIn;

    /**<jp>
     * トレースファイルを落とすディレクトリ
     </jp>*/
    /**<en>
     * Directory to record the trace file
     </en>*/
    protected String _logDir = null;

    /**<jp>
     * AGCとの電文トレースファイルの為のLogHandler
     </jp>*/
    /**<en>
     * Log handler for the trace file of messages sent to AGC
     </en>*/
    protected RandomAcsFileHandler _sTrHandler = null;

    /**<jp>
     * AGCとのの電文をトレースファイルに落とすか否かのフラグ
     </jp>*/
    /**<en>
     * Flag to indicate whether/not to record messages  in the trace file
     </en>*/
    protected boolean _trOn = false;

    /**<jp>
     * AGCとのの電文トレースファイル名
     </jp>*/
    /**<en>
     * Trace file of messages received
     </en>*/
    protected String _trName = "AS21Trace.txt";

    /**
     * AGCへの最終送信時間
     */
    protected Date _sendEndDate = null;

    /**<jp>
     * AGCからの受信電文トレースファイルの為のLogHandler
     </jp>*/
    /**<en>
     * Log handler for the trace file of messages received from AGC
     </en>*/
    protected RandomAcsFileHandler _rTrHandler = null;

    /**<jp>
     * AGCとの動作ログファイルの為のパラメータ
     </jp>*/
    /**<en>
     * Parameter for the AGC operation log file
     </en>*/
    protected static Object[] _aLogParam = new Object[1];

    /**<jp>
     * AGCとの送信トレースファイルの為のパラメータ
     </jp>*/
    /**<en>
     * Parameter for the Send trace file to AGC
     </en>*/
    protected static Object[] _sTrcParam = new Object[1];

    /**<jp>
     * AGCとの受信トレースファイルの為のパラメータ
     </jp>*/
    /**<en>
     * Parameter for the Receive trace file with AGC
     </en>*/
    protected static Object[] _rTrcParam = new Object[1];

    /**<jp>
     * AGCとのトレースファイルの為のパラメータ
     </jp>*/
    /**<en>
     * Parameter for the Receive trace file with AGC
     </en>*/
    protected static Object[] _trcParam = new Object[1];

    /**<jp>
     * AGCとの通信動作ログファイルの為のポート番号フォーマット
     </jp>*/
    /**<en>
     * Format the port number for the operation log file of AGC communication
     </en>*/
//    protected static DecimalFormat _fmt = new DecimalFormat("000000");

    /**<jp>
     * AGCとの通信動作ログの為の動作詳細記述Buffer
     </jp>*/
    /**<en>
     * Buffer for detailed description of the opreration for the opration log of AGC communciation
     </en>*/
    String _action = "";

    /**<jp>
     * トレースファイルのMaxサイズ(Byte)。
     </jp>*/
    /**<en>
     * Maximal size of the trace file (byte)
     </en>*/
    protected int _fileSize = 1000000;

    /**<jp>
     * トレースファイルの1行サイズ（byte）
     </jp>*/
    /**<en>
     * Size of a line in the trace file (byte)
     </en>*/
    protected int _LineLength = 512;

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
        return ("$Revision: 8053 $,$Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 下位コントローラのAGCとのTCP/IPコネクションを確立の準備をします。
     * DefaultでHost名：AGC01 ポート番号：2000へクライアントとして接続するように振る舞います。
     </jp>*/
    /**<en>
     * Prepares to establish the connection of AGC in lower class controller and TCP/IP.
     * Behaves by default to connect with the host AGC 01, at port number 2000, as a client.
     </en>*/
    public CommunicationAgc()
    {
        this(DEFAULT_NAME, DEFAULT_PORT);
    }

    /**<jp>
     * 下位コントローラのAGCとのTCP/IPコネクションを確立の準備をします。
     * 指定されたHost名、ポート番号でクライアントとして接続するように振る舞います。
     * @param host 接続先ホスト名
     * @param port 接続先ホスト ポート番号
     </jp>*/
    /**<en>
     * Prepares to establish the connection of AGC in lower class controller and TCP/IP.
     * It behaves as a cliant that connect with the specified Host, to the specified port number.
     * @param host : name of the host connecting to
     * @param port : port number of the connecting host
     </en>*/
    public CommunicationAgc(String host, int port)
    {

        this._host = host;
        this._port = port;

        //<jp> トレースファイルを落とすディレクトリをセット</jp>
        //<en> Sets the directory to record trace file</en>
        _logDir = CommonParam.getParam(LOGS_PATH);

        //<jp> トレースファイルのMAXサイズをセット</jp>
        //<en> Sets the MAX. of size of trace file</en>
        _fileSize = WmsParam.AS21_TRACE_MAX_SIZE;

        //<jp> 電文をトレースファイルに落とすか否かのフラグをセット</jp>
        //<en> Sets the flag to indicate whether/not to record the  message in trace file</en>
        if (WmsParam.AS21_TRACE_ON)
        {
            //<jp> 受信電文をトレースファイル名をセット</jp>
            //<en> Sets the trace file which the receiving message should be recorded</en>
            _trName = _logDir + host + WmsParam.AS21_TRACE_NAME;
            _trOn = true;
        }


    }

    // Public methods ------------------------------------------------
    /**<jp>
     * AGCに対してクライアントとして接続する。
     * @return  接続先AGCに対する Socket
     * @throws IOException ファイルI/Oエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Connects with AGC as a cliant
     * @return  : socket to the connecting AGC
     * @throws IOException  : Notifies if file I/O error occured.
     </en>*/
    public Socket connect()
            throws IOException
    {
        System.out.println("AGC Connect HostName =" + _host + " PortNo = " + new DecimalFormat("000000").format(_port));
        _socket = new Socket(_host, _port);

        // タイマー監視機能追加
        // タイマー値＝０は無限待ちとなります。
        int timeout = 0;
        // 受信KeepAlive発生後、再接続の可否
        // 再接続の可否がtrue且つ、KeepAlive発生認識時間が０を超える場合のみ
        // Socket受信のタイマー値をセットする。
        if (ALIVE_RESTART && ALIVE_TIMER > 0)
        {
            timeout = (int)ALIVE_TIMER + (int)ALIVE_MARGIN;
        }
        _socket.setSoTimeout(timeout);
        _dataIn = new DataInputStream(new BufferedInputStream(_socket.getInputStream()));
        _dataOut = new DataOutputStream(new BufferedOutputStream(_socket.getOutputStream()));
        //<jp> 動作ログを落とす設定か否かを見て、条件に合えばログを落とす。</jp>
        //<en> Check whether/not the operation log to be recorded; records log accordingly.</en>
        _action = "AGC Connect HostName =" + _host + " PortNo = " + new DecimalFormat("000000").format(_port);
        this.actionLogWrite(_action);

        return (_socket);
    }

    /**<jp>
     * AGCに対してクライアントとして切断する。
     * @throws IOException ファイルI/Oエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Disconnect with AGC as a cliant.
     * @throws IOException  : Notifies if file I/O error occured.
     </en>*/
    public void disconnect()
            throws IOException
    {
        _socket.close();
    }

    /**<jp>
     * 接続が完了しているかどうか確認します。
     * @return  _socket
     </jp>*/
    /**<en>
     * Confirms that connection has comleted.
     * @return  _socket
     </en>*/
    public Socket getConnected()
    {
        return _socket;
    }

    /**<jp>
     * 接続が完了しているかどうか確認します。
     * @return  接続済みなら true
     </jp>*/
    /**<en>
     * Confirms that connection has comleted.
     * @return  Returns 'true' if connection is complete.
     </en>*/
    public boolean isConnected()
    {
        return (_socket != null);
    }

    /**<jp>
     * AGCに対しての入力用ストリームを返す。
     * @return  TCP/IP Socketに対する DataInputStream
     </jp>*/
    /**<en>
     * Returns input stream to AGC
     * @return  DataInputStream to the TCP/IP Socket
     </en>*/
    public DataInputStream getInStream()
    {
        return (_dataIn);
    }

    /**<jp>
     * AGCに対しての出力用ストリームを返す。
     * @return  TCP/IP Socketに対する DataOutputStream
     </jp>*/
    /**<en>
     * Returns output stream to AGC
     * @return  DataOutputStream to the TCP/IP Socket
     </en>*/
    public DataOutputStream getOutStream()
    {
        return (_dataOut);
    }

    /**
     * AGCへの最終送信時間を返す。
     * @return Date 最終送信時間
     */
    public Date getSendEndDate()
    {
        return (_sendEndDate);
    }

    /**
     * AGCへの最終送信時間を更新します。
     * @param _endDate 最終送信時間
     */
    public void setSendEndDate(Date _endDate)
    {
        _sendEndDate = _endDate;
    }

    /**<jp>
     * AGCに電文を送信する。
     * @param  msg    送信電文(ID、ID区分、MC送信時間、AGC送信時間、DATA)
     * @param  seqNo  送信電文のSequense No.
     * @throws IOException ファイルI/Oエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Sends the communication message to AGC.
     * @param  msg   : Communication message (ID, ID classification, MC send time, AGC send time, data)
     * @param  seqNo : Sequesnce no. of the sent message
     * @throws IOException  : Notifies if file I/O error occured.
     </en>*/
    public void send(String msg, int seqNo)
            throws IOException
    {
        StringBuffer wkstbuf = new StringBuffer();
        //<jp> Seq No.の編集</jp>
        //<en> Editing the Seq no.</en>
        DecimalFormat fmt = new DecimalFormat(SEQ_FORMAT);
        String c_seqNo = fmt.format(seqNo);

        //<jp> Sequense No.と送信電文をマージ</jp>
        //<en> Sequense No. is merged with the communication message. </en>
        String wkstr = wkstbuf.append(c_seqNo).append(msg).toString();

        //<jp> 送信データは必ずwShift_JIS(AS21通信規約による)</jp>
        //<en> Sending data must be Shift_JIS (according to the regulation of AS21 communication)</en>
        byte[] s_byteMsg = wkstr.getBytes(CODE);

        //<jp> Bccを作成する</jp>
        //<en> Creates Bcc.</en>
        Bcc bcc = new Bcc();
        String sBcc = bcc.make(s_byteMsg, s_byteMsg.length);
        byte[] s_byteBcc = sBcc.getBytes(CODE);

        //<jp> STXを送信</jp>
        //<en> Sends STX.</en>
        _dataOut.writeByte(STX);
        //<jp> 電文本体を送信</jp>
        //<en> Sends the communication message body.</en>
        _dataOut.write(s_byteMsg, 0, s_byteMsg.length);
        //<jp> BCCを送信</jp>
        //<en> Sends BCC.</en>
        _dataOut.write(s_byteBcc, 0, s_byteBcc.length);
        //<jp> ETXを送信</jp>
        //<en> Sends ETX.</en>
        _dataOut.writeByte(ETX);
        //<jp> ここで本当に送信</jp>
        //<en> Sends as an actual process</en>
        _dataOut.flush();
        //<jp> 動作ログを落とす設定か否かを見て、条件に合えばログを落とす。</jp>
        //<en> Check whether/not the operation log to be recorded; records log accordingly</en>
        _action = "AGC Send ";
        this.actionLogWrite(_action);
        String send = new String(wkstr.getBytes("SJIS"), "8859_1");
        //<jp> 受信トレースを落とす設定か否かを見て、条件に合えばトレースを落とす。</jp>
        //<en> Check whether/not the receiving trace to be recorded; records trace accordingly.</en>
        this.TraceWrite(send, SEND);

        // AGC最終送信時間を更新
        setSendEndDate(new Date());
    }

    /**<jp>
     * AGCにKeepAlive電文を送信する。
     * @throws IOException ファイルI/Oエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Sends the keepALive message to AGC.
     * @throws IOException  : Notifies if file I/O error occured.
     </en>*/
    public void keepAliveSend()
            throws IOException
    {

        //<jp> ETXを送信</jp>
        //<en> Sends ETX.</en>
        _dataOut.writeByte(ETX);
        //<jp> ここで本当に送信</jp>
        //<en> Sends as an actual process</en>
        _dataOut.flush();
        //<jp> 動作ログを落とす設定か否かを見て、条件に合えばログを落とす。</jp>
        //<en> Check whether/not the operation log to be recorded; records log accordingly</en>
        _action = "AGC KeepAlive Send ";
        this.actionLogWrite(_action);

        // AGC最終送信時間を更新
        setSendEndDate(new Date());
    }

    /**<jp>
     * AGCから電文を受信する。
     * @return  受信電文はSTX,ETX,BCCが省かれた電文となります。
     * @throws InvalidProtocolException  プロトコル上、不都合な情報があった場合に通知されます。
     * @throws IOException ファイルI/Oエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Receive communication message from AGC.
     * @return  Receiving message should be a message with STX, ETX and BCC omitted.
     * @throws InvalidProtocolException  : Notifies if improper information is included for protocol aspect.
     * @throws IOException  : Notifies if file I/O error occured.
     </en>*/
    public byte[] recv()
            throws InvalidProtocolException,
                IOException
    {
        byte[] inByte = new byte[DEFAULT_LENGTH];
        byte ret = 0;
        int ii = 0;
        int rcvLength = 0;

        try
        {
            while (true)
            {
                ret = _dataIn.readByte();
                rcvLength++;

                if (ret == STX)
                {
                    //<jp> STXのみの電文は無視</jp>
                    //<en> Ignore message of STX only.</en>
                    rcvLength = 1;
                    continue;
                }
                else if (ret == ETX)
                {
                    if (rcvLength == 1)
                    {
                        // KeepAliveテキストとする。 2008/11/03
                        //<jp> 動作ログを落とす設定か否かを見て、条件に合えばログを落とす。</jp>
                        //<en> Check whether/not the operation log to be recorded; records log accordingly</en>
                        _action = "AGC Keep-Alive Receive ";
                        this.actionLogWrite(_action);
                        byte[] retbyte = new byte[1];
                        retbyte[0] = ETX;
                        return (retbyte);
                        // rcvLength = 0;
                        // continue;
                    }
                    else
                    {
                        if (rcvLength > MINIMAM_LENGTH)
                        {
                            break;
                        }
                        //<jp> 最低電文長以下の場合には電文を破棄する。</jp>
                        //<en> Break the message if its length is less than the pre-defined least message length.</en>
                        else
                        {
                            rcvLength = 0;
                            ii = 0;
                            continue;
                        }
                    }
                }
                //<jp> 通常電文受信</jp>
                //<en> Receive normal message</en>
                else
                {
                    if (rcvLength > DEFAULT_LENGTH)
                    {
                        // 6026059=データベースエラーが発生しました。メッセージ={0}
                        throw new InvalidProtocolException("6026059");
                    }
                    else if (rcvLength != 1)
                    {
                        inByte[ii++] = ret;
                        continue;
                    }
                    //<jp> STXを受信するまでは破棄</jp>
                    //<en> Keep breaking until STX is received</en>
                    else
                    {
                        rcvLength = 0;
                        continue;
                    }
                }
            }

            Bcc bcc = new Bcc();
            //<jp> BCC値のCheckを行う</jp>
            //<en> Check the BCC value.</en>
            boolean cBcc = bcc.check(inByte, ii);
            //<jp> 必要な電文長だけを別配列に移し、Stringへ変換</jp>
            //<jp> この時BCC部分は除去(2Byte分引いておく)</jp>
            //<en> Transfer the required message length to the different array; then convert to string</en>
            //<en> In so doing, omit BCC part. (2 byte portion)</en>
            if (cBcc)
            {
                byte[] retbyte = new byte[ii];
                for (int jj = 0; jj < ii - 2; jj++)
                {
                    retbyte[jj] = inByte[jj];
                }
                //<jp> 動作ログを落とす設定か否かを見て、条件に合えばログを落とす。</jp>
                //<en> Check whether/not the operation log to be recorded; records log accordingly</en>
                _action = "AGC Receive ";
                this.actionLogWrite(_action);
                //<jp> 受信トレースを落とす設定か否かを見て、条件に合えばトレースを落とす。</jp>
                //<en> Check whether/not the receiving trace to be recorded; records trace accordingly</en>
                this.TraceWrite(new String(retbyte, "8859_1"), RECV);
                return (retbyte);
            }
            else
            {
                throw new InvalidProtocolException("FTTB 6025131");
            }
        }
        catch (SocketTimeoutException e)
        {
            throw new SocketTimeoutException();
        }
    }

    /**<jp>
     * AGCとの通信アクションをロギングする。
     * @param  action ロギング内容
     </jp>*/
    /**<en>
     * Logging of operation over communication with AGC
     * @param  action :Content of logging
     </en>*/
    public void actionLogWrite(String action)
    {
        _aLogParam[0] = action;
    }

    /**<jp>
     * AGCとの通信内容をトレースする。
     * @param  trdata トレース内容
     * @throws IOException ファイルI/Oエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Trace the content received from AGC
     * @param  trdata :content of the trace
     * @param transmission:メッセージ番号(0：送信 1:受信)
     * @throws IOException  : Notifies if file I/O error occured.
     </en>*/
    public void TraceWrite(String trdata, int transmission)
            throws IOException
    {
        //DFKUPDATE 3.5 Start
        if (_trOn && (_rTrHandler == null))
        {
            _rTrHandler = new RandomAcsFileHandler(_trName, _fileSize);
        }
        if (!(_rTrHandler == null))
        {
            synchronized (_rTrHandler)
            {
                Object[] trcParam = new Object[1];
                trcParam[0] = trdata;
                _trcParam[0] = trdata;

                try
                {
//                    _rTrHandler.write(transmission, LogMessage.F_INFO, "CommunicatinAgc", _trcParam);
                    _rTrHandler.write(transmission, LogMessage.F_INFO, "CommunicatinAgc", trcParam);
                }
                catch (FileNotFoundException e)
                {
                    // 6026617=対AGC用の通信ログファイルの書込みに失敗しました。ファイルがロックされている可能性があります。
                    RmiMsgLogClient.write(new TraceHandler(6026617, e), this.getClass().getName());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date curDate = new Date();
                    String dateString = formatter.format(curDate);
                    // トレースファイル名をセット
                    _trName = _trName + dateString;
                    _rTrHandler = new RandomAcsFileHandler(_trName, _fileSize);
                    // ログ書込み
//                    _rTrHandler.write(transmission, LogMessage.F_INFO, "CommunicatinAgc", _trcParam);
                    _rTrHandler.write(transmission, LogMessage.F_INFO, "CommunicatinAgc", trcParam);

                    // 6026618=対AGC用の通信ログファイルの新規作成を行いました。ログファイル名={0}
                    RmiMsgLogClient.write(new TraceHandler(6026618, e), this.getClass().getName(), new Object[] {
                        _trName
                    });
                }
            }
        }
        //DFKUPDATE 3.5 End
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

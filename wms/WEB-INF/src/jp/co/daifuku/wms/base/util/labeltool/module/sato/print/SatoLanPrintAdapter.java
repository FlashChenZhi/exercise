package jp.co.daifuku.wms.base.util.labeltool.module.sato.print;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RandomAcsFileHandler;
import jp.co.daifuku.wms.base.util.labeltool.LabelParam;
import jp.co.daifuku.wms.base.util.labeltool.base.PrinterAdapter;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiSocketException;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiSocketTimeoutException;
import jp.co.daifuku.wms.base.util.labeltool.base.telegraph.SocketTransmitter;


/**
 * 佐藤製プリンターのLANインターフェースクラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/19</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 8046 $, $Date: 2012-05-18 10:30:40 +0900 (金, 18 5 2012) $
 * @author  chenjun
 * @author  Last commit: $Author: kanda $
 */

public class SatoLanPrintAdapter
        implements PrinterAdapter
{
    /**
     * メッセージ番号 コマンド送信:0
     */
    private static final int SEND = 0;

    /**
     * メッセージ番号 ACK受信:1
     */
    private static final int ACK = 1;

    /**
     * メッセージ番号 ステータス要求:2
     */
    private static final int ENQ = 2;

    /**
     * メッセージ番号 ステータス受信:3
     */
    private static final int STATUS = 3;

    /** <code>ポート番号1024</code> */
    public static String $PORT_1024 = "1024";

    // ISHIDAポート番号
    //public static String $PORT_1024 = "9100" ;

    //    /** <code>ポート番号1025</code> */
    //    public static String $PORT_1025 = "1025";

    /** <code>返送状態_ACK</code> */
    public static String $STATUS_ACK = new String(new byte[] {
        0x6
    });

    // 印刷OK
    public static String STATUS_OK = "6";

    public static String STATUS_NG = "15";

    /** <code>状態要求送信文字_ENQ</code> */
    private static byte $STR_ENQ = 0x5;

    /**<jp>
     * ログファイルの文字コードを定義します。
     </jp>*/
    public static final String LOG_CODE = "iso-8859-1";

    /** プリンタ接続モード */
    private PrintConnectMode connectMode = PrintConnectMode.LAN;

    /** プリンタのIPアドレス */
    private String address;

    /** 通信タイムアウト */
    private int timeout = 6;

    /** ソケット通信条件 */
    private CommMode commMode = CommMode.STATUS_3;

    /**  */
    private SocketTransmitter _transmitter = null;

    /**
     * トレースファイルを落とすディレクトリ
     */
    protected String _logDir = null;

    /**
     * ラベラとの電文トレースファイル名
     */
    protected String _trName = null;

    /**
     * トレースファイルのMaxサイズ(Byte)
     */
    protected int _fileSize = 1000000;

    /**
     * ラベラとの電文をトレースファイルに落とすか否かのフラグ
     */
    protected boolean _trOn = false;

    /**
     * ラベラとの電文をトレースファイルに落とす為のFileHandler
     */
    protected static RandomAcsFileHandler _raFileHandler = null;

    /**
     * トレースファイルパラメータ
     */
    protected Object[] _trcParam = new Object[1];

    /**
     * コンストラクタ
     *
     */
    public SatoLanPrintAdapter()
    {
        // トレースファイルを落とすディレクトリをセット
        _logDir = CommonParam.getParam("LOGS_PATH");

        // トレースファイルのMAXサイズをセット
        _fileSize = LabelParam.LABEL_TRACE_MAX_SIZE;
        // 電文をトレースファイルに落とすか否かのフラグをセット
        if (LabelParam.LABEL_TRACE_ON)
        {
            // トレースファイル名をセット
            _trName = _logDir + LabelParam.LABEL_TRACE_NAME;
            _trOn = true;
        }
    }

    /**
     * ソケット通信の接続を行います。
     * 
     * @throws DaiSocketException ソケット通信エラーが発生した場合
     */
    public void connect()
            throws DaiSocketException
    {
        // 接続済みの場合、一旦閉じる。
        if (_transmitter != null)
        {
            close();
        }

        // ソケットの接続
        _transmitter = new SocketTransmitter(address, Integer.parseInt($PORT_1024), 1024);
        _transmitter.connect();
    }

    /**
     * ソケット通信の切断を行います。
     * 
     * @throws DaiSocketException ソケット通信エラーが発生した場合
     */
    public void close()
            throws DaiSocketException
    {
        if (_transmitter != null)
        {
            _transmitter.close();
            _transmitter = null;
        }
    }

    /**
     * データを送信します。<br>
     * 
     * @param cmdStr 送信データ
     * @return 送信成功した場合(ACK)、trueを返します。<br>
     *         失敗した場合、falseを返します。
     * @throws DaiSocketException ソケット通信エラーが発生した場合
     * @throws DaiSocketTimeoutException ソケット通信タイムアウトが発生した場合
     */
    public String sendCommand(byte[] cmdStr)
            throws DaiSocketException,
                DaiSocketTimeoutException,
                IOException
    {
        byte[] receive = cmdStr;
        _transmitter.send(receive);
        // 送信ログ出力
        traceWrite(new String(cmdStr, LOG_CODE), SEND);
        receive = _transmitter.receive(timeout);

        String receiveData = String.valueOf(receive[0]);
        // ACKをログに出力
        traceWrite(new String(receive, LOG_CODE), ACK);
        return receiveData;
    }

    /**
     * プリンタの状態を取得します。<br>
     * 
     * @return プリンタの状態
     */
    public String getStatus()
            throws DaiException,
                IOException
    {
        byte[] receive = new byte[] {
            $STR_ENQ
        };

        _transmitter.send(receive);
        // ステータス要求ログ出力
        traceWrite(new String(receive, LOG_CODE), ENQ);

        receive = _transmitter.receive(timeout);

        String statusCode = new String(new byte[] {
            receive[3]
        });

        // 返却ステータスログ出力
        traceWrite(new String(receive, LOG_CODE), STATUS);

        return statusCode;
    }

    /**
     * @return the address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * @return the commMode
     */
    public CommMode getCommMode()
    {
        return commMode;
    }

    /**
     * @param commMode
     *            the commMode to set
     */
    public void setCommMode(CommMode commMode)
    {
        this.commMode = commMode;
    }

    /**
     * @return the connectMode
     */
    public PrintConnectMode getConnectMode()
    {
        return connectMode;
    }

    /**
     * @param connectMode
     *            the connectMode to set
     */
    public void setConnectMode(PrintConnectMode connectMode)
    {
        this.connectMode = connectMode;
    }

    /**
     * @return the timeout
     */
    public int getTimeout()
    {
        return timeout;
    }

    /**
     * @param timeout
     *            the timeout to set
     */
    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }

    /**
     * トレースログ出力処理
     * @param trdata 出力データ
     * @param transmission 送受信区分
     * @throws IOException 入出力エラー発生時に通知されます。
     */
    private synchronized void traceWrite(String trdata, int transmission)
            throws IOException
    {
        if (_trOn && (_raFileHandler == null)) _raFileHandler = new RandomAcsFileHandler(_trName, _fileSize);

        if (!(_raFileHandler == null))
        {
            synchronized (_raFileHandler)
            {
                _trcParam[0] = trdata;

                try
                {
                    _raFileHandler.write(transmission, LogMessage.F_INFO, "SatoLanPrintAdapter", _trcParam);
                }
                catch (FileNotFoundException e)
                {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date curDate = new Date();
                    String dateString = formatter.format(curDate);
                    // トレースファイル名をセット
                    _trName = _logDir + LabelParam.LABEL_TRACE_NAME + dateString;
                    _raFileHandler = new RandomAcsFileHandler(_trName, _fileSize);
                    // ログ書込み
                    _raFileHandler.write(transmission, LogMessage.F_INFO, "SatoLanPrintAdapter", _trcParam);
                }
            }
        }
    }

}

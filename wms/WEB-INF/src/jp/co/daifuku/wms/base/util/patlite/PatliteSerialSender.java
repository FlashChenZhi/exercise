// $Id: PatliteSerialSender.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util.patlite;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.util.patlite.PatliteOperator.PatliteStatus;

/**
 * パトライト通信のプロトコル(シリアル)に応じてコマンドの送信を行うクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Softecs
 * @author  Last commit: $Author: admin $
 */

public class PatliteSerialSender
        extends PatliteCommandSender
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    private static final String APP_NAME = "PatliteSerialSender";

    private static final String PORT_ID = "PortId";

    private static final String BAUD_RATE = "Baudrate";

    private static final String CHAR = "Character";

    private static final String STOP_BIT = "StopBit";

    private static final String PARITY_BIT = "Parity";

    private static final String FLOW = "Flow";

    private static final byte[] ACK_RES = {
        0x06,
    };

    /**
     * <code>TIME_OUT</code><br>
     * シリアルポートオープン時における監視時間(ms)<br>
     */
    private static final int TIME_OUT = 3000;

    /**
     * <code>RECV_TIME_OUT</code><br>
     * コマンド送信時の応答待ち時間(ms)<br>
     */
    private static final int RECV_TIME_OUT = 1000;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    private SerialPort _serialPort = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ。<br>
     * パトライト設定管理クラスを指定してインスタンス化します。
     * @param holder パトライト設定管理クラス
     */
    public PatliteSerialSender(PatliteSettingHolder holder)
    {
        super(holder);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * パトライトへの通信ポートをオープンします。
     * 
     * @param id パトライトID
     * @throws IOException オープン時に異常を検出した場合にスローされます
     */
    @Override
    protected void patliteOpen(String id)
            throws IOException
    {
        // パトライト設定管理クラスよりシリアルポートの設定を取得します
        PatliteSettingHolder holder = getSettingHolder();
        String portId = holder.getProperty(id, PORT_ID);
        int rate = holder.getIntProperty(id, BAUD_RATE);
        int charl = holder.getIntProperty(id, CHAR);
        int stop = holder.getIntProperty(id, STOP_BIT);
        int parity = holder.getIntProperty(id, PARITY_BIT);
        int flow = holder.getIntProperty(id, FLOW);

        CommPortIdentifier comPortId = null;

        try
        {
            // ポートID取得
            comPortId = CommPortIdentifier.getPortIdentifier(portId);
            // ポートオープン
            _serialPort = (SerialPort)comPortId.open(APP_NAME, TIME_OUT);
            // ポート設定
            _serialPort.setSerialPortParams(rate, charl, stop, parity);
            _serialPort.setFlowControlMode(flow);
        }
        catch (NoSuchPortException e)
        {
            //e.printStackTrace();
            // 6026072 = 警告灯：ポート{0}が見つかりません。
            StringBuffer message = new StringBuffer("6026072");
            message.append(MessageResource.DELIM);
            message.append(portId);
            RmiMsgLogClient.write(String.valueOf(message), this.getClass().getName());

            message = new StringBuffer(portId);
            //message.append("が見つかりません。");
            message.append(" is not found.");
            setResultString(String.valueOf(message));
            throw new IOException();
        }
        catch (UnsupportedCommOperationException e)
        {
            e.printStackTrace();
            _serialPort.close();

            // 6026074 = 警告灯：ポート{0}の設定ができません。
            StringBuffer message = new StringBuffer("6026074");
            message.append(MessageResource.DELIM);
            message.append(portId);
            RmiMsgLogClient.write(String.valueOf(message), this.getClass().getName());

            message = new StringBuffer(portId);
            //message.append("の設定ができません。");
            message.append(" cannot be setting.");
            setResultString(String.valueOf(message));
            throw new IOException();
        }
        catch (PortInUseException e)
        {
            //e.printStackTrace();
            // 6026073 = 警告灯：ポート{0}がオープンできません。
            StringBuffer message = new StringBuffer("6026073");
            message.append(MessageResource.DELIM);
            message.append(portId);
            RmiMsgLogClient.write(String.valueOf(message), this.getClass().getName());

            message = new StringBuffer(portId);
            //message.append("が使用中です。");
            message.append(" is inuse.");
            setResultString(String.valueOf(message));
            throw new IOException();
        }
    }

    /**
     * パトライトとの通信ポートをクローズします。
     * 
     * @throws IOException クローズ時に異常を検出した場合にスローされます
     */
    @Override
    protected void patliteClose()
            throws IOException
    {
        if (null == _serialPort)
        {
            //setResultString("通信ポートオブジェクトが null です。");
            setResultString("Communication port is not opened.");
            throw new IOException();
        }

        _serialPort.close();
    }

    /**
     * パトライトへのコマンドを送信します。
     * 
     * @param command 送信コマンド
     * @return コマンド送信ステータス
     */
    @Override
    protected PatliteStatus patliteSend(byte[] command)
    {
        if (null == _serialPort)
        {
            //setResultString("通信ポートオブジェクトが null です。");
            setResultString("Communication port is not opened.");
            return PatliteStatus.NAK;
        }

        InputStream recvStream = null;
        try
        {
            // コマンド送信
            PatliteLogger.write(true, getId(), command);
            OutputStream os = _serialPort.getOutputStream();
            os.write(command);
            os.flush();

            // 警告灯からの応答を受信します
            recvStream = _serialPort.getInputStream();
            _serialPort.enableReceiveTimeout(RECV_TIME_OUT);
            byte[] recv = new byte[16];
            int len = recvStream.read(recv);
            PatliteLogger.write(false, getId(), recv, len);
            PatliteStatus res = PatliteStatus.NAK;
            if (ACK_RES.length == len)
            {
                for (int i = 0; i < ACK_RES.length; i++)
                {
                    if (ACK_RES[i] != recv[i])
                    {
                        break;
                    }
                }
                res = PatliteStatus.ACK;
            }
            StringBuffer message = new StringBuffer();
            if (PatliteStatus.ACK == res)
            {
                //message.append("ACKを受信しました");
                message.append("ACK received.");
            }
            else
            {
                //message.append("NAKを受信しました");
                message.append("NAK received.");
            }
            setResultString(String.valueOf(message));
            return res;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return PatliteStatus.NAK;
        }
        catch (UnsupportedCommOperationException e)
        {
            e.printStackTrace();
            return PatliteStatus.NAK;
        }
        finally
        {
            /*            if (null != writer)
             {
             writer.close();
             }
             */
            if (null != recvStream)
            {
                try
                {
                    recvStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();

                    // 6016102 = 致命的なエラーが発生しました。{0}
                    RmiMsgLogClient.write(new TraceHandler(6016102, e), this.getClass().getName());
                    //setResultString("警告灯との受信ストリームクローズ時にエラーを検出しました");
                    setResultString("Error in Patlite closing process.");
                    return PatliteStatus.NAK;
                }
            }
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PatliteSerialSender.java 87 2008-10-04 03:07:38Z admin $";
    }
}

// $Id: PatliteSocketSender.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util.patlite;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.IllegalBlockingModeException;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.util.patlite.PatliteOperator.PatliteStatus;


/**
 * パトライト通信のプロトコル(ソケット)に応じてコマンドの送信を行うクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Softecs
 * @author  Last commit: $Author: admin $
 */


public class PatliteSocketSender
        extends PatliteCommandSender
        implements Runnable
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    private static final String PORT_ID = "PortId";

    private static final String PORT_NO = "PortNo";

    private static final byte[] ACK_RES = {
        'A',
        'C',
        'K'
    };

    private static final byte[] REQ_STATUS = {
        'R'
    };

    /**
     * <code>RECV_TIME_OUT</code><br>
     * コマンド送信時の応答待ち時間(ms)<br>
     */
    private static final int RECV_TIME_OUT = 5000;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    private Socket _socket = null;

    private boolean _ackRecieve = false;

    private byte _rcvStatus;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * パトライト設定管理クラスを指定してインスタンス化します。
     * 
     * @param holder パトライト設定管理クラス
     */
    public PatliteSocketSender(PatliteSettingHolder holder)
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
        // パトライト設定管理クラスよりソケットI/Fの設定を取得します
        // ソケットI/Fの場合、PortIdにパトライトのアドレスが設定されている
        // ものとします
        PatliteSettingHolder holder = getSettingHolder();
        String address = holder.getProperty(id, PORT_ID);
        int portno = holder.getIntProperty(id, PORT_NO);

        // ソケットを生成
        try
        {
            _socket = new Socket(address, portno);
        }
        catch (UnknownHostException e)
        {
            //e.printStackTrace();
            // 6026074 = 警告灯：ポート{0}の設定ができません。
            StringBuffer message = new StringBuffer("6026074");
            message.append(MessageResource.DELIM);
            message.append(address);
            RmiMsgLogClient.write(String.valueOf(message), this.getClass().getName());

            //message = new StringBuffer(address);
            //message.append("の設定ができません。");
            message = new StringBuffer("Cannot setting in ");
            message.append(address);
            setResultString(String.valueOf(message));
            throw new IOException();
        }
        catch (ConnectException e)
        {
            //e.printStackTrace();
            // 6026073 = 警告灯：ポート{0}がオープンできません。
            StringBuffer message = new StringBuffer("6026073");
            message.append(MessageResource.DELIM);
            message.append(address);
            RmiMsgLogClient.write(String.valueOf(message), this.getClass().getName());

            //message = new StringBuffer(address);
            //message.append("がオープンできません。");
            message = new StringBuffer("Cannot open ");
            message.append(address);
            setResultString(String.valueOf(message));
            throw new IOException();
        }
        catch (NoRouteToHostException e)
        {
            //e.printStackTrace();
            // 6026072 = 警告灯：ポート{0}が見つかりません。
            StringBuffer message = new StringBuffer("6026072");
            message.append(MessageResource.DELIM);
            message.append(address);
            RmiMsgLogClient.write(String.valueOf(message), this.getClass().getName());

            //message = new StringBuffer(address);
            //message.append("が見つかりません。");
            message = new StringBuffer("Cannot find ");
            message.append(address);
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
        if (null == _socket)
        {
            //setResultString("通信ポートオブジェクトが null です。");
            setResultString("Communication port is not opened.");
            throw new IOException();
        }

        _socket.close();
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
        if (null == _socket)
        {
            //setResultString("通信ポートオブジェクトが null です。");
            setResultString("Communication port is not opened.");
            return PatliteStatus.NAK;
        }

        try
        {
            // コマンドを送信する前に、受信の準備をします
            _socket.setSoTimeout(RECV_TIME_OUT);
            Thread rcvThread = new Thread(this);
            rcvThread.start();

            // コマンド送信に先立って、現在の状態を取得し、今回送信するコマンドに加える
            // （今回送信するコマンドによって、それまでの状態を上書きしない）
            PatliteLogger.write(true, getId(), REQ_STATUS);
            _socket.getOutputStream().write(REQ_STATUS);
            try
            {
                rcvThread.join();
                if (_ackRecieve)
                {
                    command[1] |= _rcvStatus;
                }
            }
            catch (InterruptedException e)
            {
                //e.printStackTrace();
                //setResultString("Interruptを受け付けました。");
                setResultString("Interrupt found in request status sending.");
                return PatliteStatus.NAK;
            }

            // コマンド送信
            PatliteLogger.write(true, getId(), command);
            _socket.getOutputStream().write(command);

            // 受信スレッドでの応答をチェックします
            try
            {
                rcvThread.join();
                if (_ackRecieve)
                {
                    return PatliteStatus.ACK;
                }
            }
            catch (InterruptedException e)
            {
                //e.printStackTrace();
                //setResultString("Interruptを受け付けました。");
                setResultString("Interrupt found in Patlite command sending.");
            }
        }
        catch (UnknownHostException ex)
        {
            // 6026075 = 警告灯：接続先ホストが見つかりません。
            RmiMsgLogClient.write("6026075", this.getClass().getName());
            //setResultString("接続先ホストが見つかりません。");
            setResultString("The host is not found at the connection destination. ");
        }
        catch (IllegalBlockingModeException ex)
        {
            ex.printStackTrace();
            // 6026076 = 警告灯：ソケットのIOエラーが発生しました。
            RmiMsgLogClient.write("6026076", this.getClass().getName());
            //setResultString("ソケットチャネルが非ブロックモードです。");
            setResultString("Socket channel is not blocking mode.");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            // 6026076 = 警告灯：ソケットのIOエラーが発生しました。
            RmiMsgLogClient.write("6026076", this.getClass().getName());
            //setResultString("ソケットのIOエラーが発生しました。");
            setResultString("Socket error found.");
        }
        return PatliteStatus.NAK;
    }

    /**
     * パトライトからの応答を受信するスレッドです。
     * 
     * 応答がACKであれば、$ackRecieveに<code>true</code>を設定します
     */
    public void run()
    {
        // パトライトからの応答を受信します
        byte[] recv = new byte[16];
        try
        {
            int len = _socket.getInputStream().read(recv);
            PatliteLogger.write(false, getId(), recv, len);
            if (ACK_RES.length == len)
            {
                // 応答がACKであれば、trueを設定します
                for (int i = 0; i < ACK_RES.length; i++)
                {
                    if (ACK_RES[i] != recv[i])
                    {
                        _ackRecieve = false;
                        return;
                    }
                }
                _ackRecieve = true;
            }
            else if (2 == len && REQ_STATUS[0] == recv[0])
            {
                _rcvStatus = recv[1];
                _ackRecieve = true;
            }
        }
        catch (IOException e)
        {
            // 6026076 = 警告灯：ソケットのIOエラーが発生しました。
            RmiMsgLogClient.write("6026076", this.getClass().getName());
            //setResultString("ソケットのIOエラーが発生しました。");
            setResultString("Socket error found.");
            _ackRecieve = false;
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
        return "$Id: PatliteSocketSender.java 87 2008-10-04 03:07:38Z admin $";
    }
}

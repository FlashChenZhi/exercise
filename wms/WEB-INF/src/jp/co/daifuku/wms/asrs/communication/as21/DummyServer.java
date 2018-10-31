// $Id: DummyServer.java 2604 2009-01-08 05:27:40Z tanaka $
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
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.Date;

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
 * @version $Revision: 2604 $, $Date: 2009-01-08 14:27:40 +0900 (木, 08 1 2009) $
 * @author  $Author: tanaka $
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
 * @version $Revision: 2604 $, $Date: 2009-01-08 14:27:40 +0900 (木, 08 1 2009) $
 * @author  $Author: tanaka $
 </en>*/
public class DummyServer
{

    /**
     * Receive UDP Socket
     */
    static class Server extends Thread {
        DatagramSocket socket;
        Socket _socket;
        ServerSocket ss;
        int limit;
        String _host;
        int _port;
        
        Server(String host, int port, String serverFlag) throws UnknownHostException, SocketException {
            
            _host = host;
            _port = port;
            try
            {
                if (serverFlag.equals("server"))
                {
                    ss = new ServerSocket(port);
                    System.out.println("[" + new Date() + "] " + _host + ":Server Socket MAKE!");
                    _socket = ss.accept();
                    System.out.println("[" + new Date() + "] " + _host + ":Socket ACCEPT OK");
                }
                else
                {
                    System.out.println("[" + new Date() + "] " + _host + ":Client Socket MAKE!");
                    _socket = new Socket(host, port);
                    System.out.println("[" + new Date() + "] " + _host + ":Socket Connect OK");
                }
                _socket.setSoTimeout(1000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        @Override
        public void run() {
            System.out.println("[" + new Date() + "] " + _host + ":start Server");
            System.out.flush();
            DataInputStream _dataIn = null;
            DataOutputStream _dataOut;
            Date _newDate = new Date();
            System.out.println("[" + new Date() + "] " + _host + ":接続時間[" + _newDate + "]");
            try
            {
                _dataIn = new DataInputStream(new BufferedInputStream(_socket.getInputStream()));
                _dataOut = new DataOutputStream(new BufferedOutputStream(_socket.getOutputStream()));

                int counter = 0;
                while (true)
                {
                    int lp = 0;
                    while (true)
                    {
                        try
                        {
                            byte ret = _dataIn.readByte();
                            lp++;
                            if (ret == 0x03)
                            {
                                if (lp == 1)
                                {
                                    System.out.println("[" + new Date() + "] " + _host + ":Keep-Alive Text 受信");
                                }
                                break;
                            }
                        }
                        catch (SocketTimeoutException e)
                        {
                            break;
                        }
                        lp = 0;
                        counter++;
                    }
                    
                    if ((new Date().getTime() - _newDate.getTime()) > 600000)
                    {
                        byte[] b = new byte[1];
                        b[0] = 0x03;
                        _dataOut.write(b);
                        _dataOut.flush();
                        System.out.println("[" + new Date() + "] " + _host + ":KeepAlive 送信!!");
                        
                        _newDate = new Date();
//                        System.out.println("[" + _newDate + "] " + _host + ":再認識時間");
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (_socket != null)
                {
                    try
                    {
                        _socket.close();
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) {
        
        Connection _conn = null;
        try {
            if (args.length >= 1)
            {
                _conn = WmsParam.getConnection();
                
                GroupController[] gpColection = GroupController.getInstances(_conn);

                for (int i =0; i < gpColection.length; i++)
                {
                    Server s = new Server(gpColection[i].getIP().getHostName(), gpColection[i].getPort(), args[0]);
                    new Thread(s).start();
                    
                    Thread.sleep(500);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if (_conn != null)
            {
                try
                {
                    _conn.close();
                }
                catch (Exception e)
                {
                }
            }
        }
    }

}
//end of class


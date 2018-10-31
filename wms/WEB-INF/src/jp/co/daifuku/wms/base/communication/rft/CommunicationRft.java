// $Id: CommunicationRft.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.communication.rft;

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
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DecimalFormat;

import jp.co.daifuku.common.InvalidProtocolException;

/**
 * RFT プロトコルにてRFTへ送信します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>miya</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class CommunicationRft
{
    // Class fields --------------------------------------------------
    /**
     * RFTでの通信電文長を定義します。
     */
    public static final int DEFAULT_LENGTH = 1024;

    /**
     * RFTでの通信電文最低長を定義します。
     */
    public static final int MINIMAM_LENGTH = 24;

    /**
     * RFTでの電文の開始マークSTXを定義します。
     */
    public static final int STX = 0x02;

    /**
     * RFTでの電文の終端マークETXを定義します。
     */
    public static final int ETX = 0x03;

    /**
     * RFTでのSeqence No. Formatを定義します。
     */
    public static final String SEQ_FORMAT = "0000";

    /**
     * リソース名の定義(通信動作ログON)
     */
    public static final String A_LOG_ON = "RFT_ACTION_LOG_ON";

    /**
     * リソース名の定義(通信動作ログのファイル名)
     */
    public static final String A_LOG_NAME = "RFT_ACTION_LOG_NAME";

    /**
     * RFT との通信上のSequence NumberとしてのInitial時の値
     */
    public static final int INITIAL_NUMBER = 0;

    /**
     * RFT との通信上のSequence NumberとしてのLOOP時のStart値
     */
    public static final int LOOP_START_NUMBER = 1;

    // Class variables -----------------------------------------------
    /**
     * RFTサーバ名。
     */
    protected String host;

    /**
     * RFT側との接続Socket。
     */
    protected Socket serversock = null;

    /**
     * RFTへの出力・データストリーム
     */
    protected DataOutputStream wDataOut;

    /**
     * RFTからの入力・データストリーム
     */
    protected DataInputStream wDataIn;

    /**
     * RFTとの動作ログファイルの為のパラメータ
     */
    protected static Object[] aLogParam = new Object[1];

    /**
     * RFTとの通信動作ログファイルの為のポート番号フォーマット
     */
    protected static DecimalFormat fmt = new DecimalFormat("000000");

    /**
     * RFTとの通信動作ログの為の動作詳細記述Buffer
     */
    String action = "";

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------
    /**
     * RFTサーバとしてRFTからのTCP/IPコネクションを確立の準備をします。<BR>
     * ポート番号：にてLOCALHOSTにてサーバ起動するように振る舞います。
     */
    public CommunicationRft()
    {
    }

    // Public methods ------------------------------------------------

    /**
     * RFTに対しサーバとして接続待機する。
     * @param   server ServerSocket
     * @return 接続先RFTに対する Socket
     * @throws  IOException ファイルI/Oエラーが発生した場合に通知されます。
     */
    public Socket connect(ServerSocket server)
            throws IOException
    {
        // クライアントからの接続待ち
        serversock = server.accept();
        // 接続後受信ストリーム作成
        wDataIn = new DataInputStream(new BufferedInputStream(serversock.getInputStream()));
        // 接続後送信ストリーム作成
        wDataOut = new DataOutputStream(new BufferedOutputStream(serversock.getOutputStream()));
        return (serversock);
    }

    /**
     * RFTに対して切断する。
     * @throws  IOException ファイルI/Oエラーが発生した場合に通知されます。
     */
    public void disconnect()
            throws IOException
    {
        serversock.close();
    }

    /**
     * 接続が完了しているかどうか確認します。
     * @return  接続済みなら true
     */
    public boolean isConnected()
    {
        return (serversock != null);
    }

    /**
     * RFTに対しての入力用ストリームを返す。
     * @return  TCP/IP Socketに対する DataInputStream
     */
    public DataInputStream getInStream()
    {
        return (wDataIn);
    }

    /**
     * RFTに対しての出力用ストリームを返す。
     * @return  TCP/IP Socketに対する DataOutputStream
     */
    public DataOutputStream getOutStream()
    {
        return (wDataOut);
    }

    /**
     * RFTに電文を送信する。
     * @param    msg    送信電文
     * @throws  IOException ファイルI/Oエラーが発生した場合に通知されます。
     */
    public void send(byte[] msg)
            throws IOException
    {
        // 送信伝文長を取得
        int iSendDataLength = getDataLength(msg);

        // 伝文本体を送信
        wDataOut.write(msg, 0, iSendDataLength);
        // ここで本当に送信
        wDataOut.flush();
    }

    /**
     * RFTから電文を受信する。
     * @param   recvData 受信伝文
     * @return     out RecvData 受信伝文はSTX,ETX含む。
     *              ret > 0:受信伝文長  ret = -1:ERROR 
     */
    public int recv(byte[] recvData)
    {
        byte[] inByte = new byte[DEFAULT_LENGTH];
        byte ret = 0;
        int ii = 0;
        int rcvLength = 0;
        int waitLoopCount = 0;

        try
        {
            while (true)
            {

                // readByte可能かチェックする。
                int len = wDataIn.available();
                if (len > 0)
                {
                    ret = wDataIn.readByte();
                    waitLoopCount = 0;
                }
                else
                {
                    waitLoopCount++;
                    Thread.sleep(10);
                    if (waitLoopCount > 300)
                    {
                        // ３秒間受信データが来ない場合はエラーとする。
                        throw new EOFException();
                    }
                    continue;
                }

                rcvLength++;
                if (ret == STX)
                {
                    ii = 0;
                    inByte[ii++] = ret;
                    rcvLength = 1;
                    continue;
                }
                else if (ret == ETX)
                {
                    if (rcvLength == 1)
                    {
                        rcvLength = 0;
                        continue;
                    }
                    else
                    {
                        // 最低電文長以下の場合には電文を破棄する。
                        if (rcvLength > MINIMAM_LENGTH)
                        {
                            inByte[ii++] = ret;
                            break;
                        }
                        else
                        {
                            rcvLength = 0;
                            ii = 0;
                            continue;
                        }
                    }
                }
                else
                {
                    // 通常電文受信
                    if (rcvLength >= DEFAULT_LENGTH)
                    {
                        throw new InvalidProtocolException("6026013");
                    }
                    else if (rcvLength != 1)
                    {
                        inByte[ii++] = ret;
                        continue;
                    }
                    // STXを受信するまでは破棄
                    else
                    {
                        rcvLength = 0;
                        continue;
                    }
                }
            }
        }
        catch (SocketException e)
        {
            System.out.println("CommunicationRft ::: SocketException = " + e);
            return (-1);
        }
        catch (EOFException e)
        {
            System.out.println("CommunicationRft ::: EOFException = " + e);
            return (-1);
        }
        catch (InvalidProtocolException e)
        {
            System.out.println("CommunicationRft ::: InvalidProtocolException = " + e);
            return (-1);
        }
        catch (IOException e)
        {
            System.out.println("RFTWatcher Exception e = " + e);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);

            //エラーをログファイルに落とす
            e.printStackTrace(pw);
            System.out.println("CommunicationRft ::: IOException = " + sw.toString());

            return (-1);

        }
        catch (Exception e)
        {
            System.out.println("CommunicationRft ::: Exception = " + e);
            return (-1);
        }
        finally
        {
            //受信伝文をトレースへ書込みを行う。
            for (int jj = 0; jj < ii; jj++)
            {
                recvData[jj] = inByte[jj];
            }
        }
        // 動作ログを落とす設定か否かを見て、条件に合えばログを落とす。
        action = "RFT Receive ";

        return (rcvLength);
    }

    /**
     * RFTとの通信アクションをロギングする。
     * @param   action  ロギング内容
     */
    public void actionLogWrite(String action)
    {
        aLogParam[0] = action;
    }

    /**
     * 伝文長取得。
     * @param   inData  伝文内容
     * @return      伝文長
     */
    public int getDataLength(byte[] inData)
    {
        int dataLength = 0;
        int i = 0;
        while (true)
        {
            if (inData[i] == ETX)
            {
                dataLength = i + 1;
                break;
            }
            else if (i >= DEFAULT_LENGTH)
            {
                return (0);
            }
            // 伝文長インクリメント
            i++;
        }
        return (dataLength);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

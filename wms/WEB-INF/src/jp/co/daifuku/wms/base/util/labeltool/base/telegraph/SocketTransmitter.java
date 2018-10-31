// $Id: SocketTransmitter.java 1911 2008-12-11 02:51:48Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.base.telegraph;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiSocketException;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiSocketTimeoutException;


/**
 * ソケ�?ト�?�信クラス
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2007/04/10</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class SocketTransmitter
{
    /** <code>送受信用ソケ�?�?</code> */
    private Socket _sender;

    /** <code>受信ソケ�?ト最大サイズ</code> */
    private int recvSocMaxSize;

    /** <code>サーバ�?�IPアドレス</code> */
    private String serverIpAddress;

    /** <code>サーバ�?�ポ�?�ト番号</code> */
    private int serverPort;

    /**
     * クラスコンストラクター<br>
     * �?隔ソケ�?トに接続するため�?�?��?��アドレスとポ�?�ト番号等を<br>
     * 設定しインスタンスを作�?�す�?
     * 
     * @param ip 接続するソケ�?ト�?�?��?��アドレス
     * @param portnum 接続するソケ�?ト�?�ポ�?�ト番号
     * @param maxSize 受信ソケ�?ト�?��?大サイズ
     */
    public SocketTransmitter(String ip, int portnum, int maxSize)
    {
        serverIpAddress = ip;
        serverPort = portnum;
        recvSocMaxSize = maxSize;
    }

    /**
     * ソケ�?ト接続を中断するメソ�?�?
     * 
     * @throws DaiSocketException ソケ�?ト�?�作�?�また�?�接続に失敗した�?��?
     */
    public void close()
            throws DaiSocketException
    {
        // ソケ�?ト接続有効チェ�?ク
        if (_sender.isConnected())
        {
            try
            {
                _sender.shutdownInput();
                _sender.shutdownOutput();
                _sender.close();
            }
            catch (IOException e)
            {
                throw new DaiSocketException("Socket Exception", e);
            }
        }
    }

    /**
     * アドレスとポ�?�ト番号を利用して�?る遠隔ソケ�?トに接続す�?
     * 
     * @throws DaiSocketException ソケ�?ト�?�作�?�また�?�接続に失敗した�?��?
     */
    public void connect()
            throws DaiSocketException
    {
        try
        {
            // 接続する新しいソケットを作成�する
            _sender = new Socket(serverIpAddress, serverPort);
        }
        catch (UnknownHostException e)
        {
            throw new DaiSocketException("Unknown Host Exception", e);
        }
        catch (IOException e)
        {
            throw new DaiSocketException("Socket Exception", e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * �?ータ受信�?ちと�?ータを受信した場合�?�受信処�?を行う
     * 
     * @param seconds 受信�?ータを何秒�?つ�?
     * @return 受信されたデータをByte配�?�形で返す
     * @throws DaiSocketTimeoutException 通信タイ�?アウト�?��?
     * @throws DaiSocketException ソケ�?ト�?�作�?�また�?�接続に失敗した�?��?
     */
    public byte[] receive(int seconds)
            throws DaiSocketTimeoutException,
                DaiSocketException
    {
        byte[] buff = new byte[recvSocMaxSize];
        InputStream rd;
        try
        {
            // �?ータ到�?�?ち
            _sender.setSoTimeout(seconds * 1000);
            // �?ータが到�?した場合�?�受信処�?を行う
            rd = _sender.getInputStream();
            rd.read(buff);
        }
        catch (InterruptedIOException e)
        {
            throw new DaiSocketTimeoutException();
        }
        catch (SocketException e)
        {
            throw new DaiSocketException("Socket Exception", e);
        }
        catch (IOException e)
        {
            throw new DaiSocketException("Socket Exception", e);
        }
        return buff;
    }

    /**
     * ソケ�?ト接続経由�?ータを�?�信するメソ�?�?
     * 
     * @param buff 送信用�?ータを持つByte配�??
     * @throws DaiSocketException ソケ�?ト�?�作�?�また�?�接続に失敗した�?��?
     */
    public void send(byte[] buff)
            throws DaiSocketException
    {
        // ソケ�?ト接続有効チェ�?ク
        if (_sender.isConnected())
        {
            try
            {
                OutputStream br = _sender.getOutputStream();
                br.write(buff);
                br.flush();
            }
            catch (IOException e)
            {
                throw new DaiSocketException("Socket Problem", e);
            }
        }
        else
        {
            throw new DaiSocketException();
        }
    }
}

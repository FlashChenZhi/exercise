// $Id: FTPClient.java,v 1.3 2009/06/24 04:26:43 mitsudome Exp $
package jp.co.daifuku.common.net;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotActiveException;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;

/**
 * FTP転送のためのクライアントクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2003/05/11</td><td nowrap>The person who created this file.</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.3 $, $Date: 2009/06/24 04:26:43 $
 * @author  S.Suzuki
 * @author  Last commit: $Author: mitsudome $
 */
public class FTPClient
        extends Thread
//							implements TEMP
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //	private String	$classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 送信時の返り値 (成功)
     */
    public static final int RET_OK = 0;

    /**
     * 送信時の返り値 (転送先・元のディレクトリまたはファイルが存在しない)
     */
    public static final int RET_NO_DIR = 550;

    /**
     * 送信時の返り値 (ファイルまたはディレクトリへのアクセス権限がない)
     */
    public static final int RET_PERMISSION_DENIED = 552;

    /**
     * 送信時の返り値 (ログインに失敗した)
     */
    public static final int RET_LOGIN_FAIL = 530;

    private static final int STREAM_BUFFER_SIZE = 8192;

    /* FTP returns */
    private static final int FTP_RET_NO_WAIT = -1;

    /** change private -> protected */
    protected static final int FTP_RET_CD_SUCCESS = 250;

    /** change private -> protected */
    protected static final int FTP_RET_TYPE_SET = 200;

    /** 受信時のタイムアウト値 */
    private static final int SOCKET_RECEIVE_TIMEOUT = 60000;
    
    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private String p_hostname;

    private String p_username;

    private String p_password;

    private String p_charset;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    protected FTPSession _session;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ホスト接続情報を設定してインスタンスを生成します。<br>
     * 
     * @param server サーバ名
     * @param user ユーザ名
     * @param passwd パスワード
     * @param charset ホストのキャラクタ・セット
     */
    public FTPClient(String server, String user, String passwd, String charset)
    {
        setHostname(server);
        setUsername(user);
        setPassword(passwd);
        setCharset(charset);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * FTPサーバにログインを行います。
     * @throws UnknownHostException ホストがみつからないときにスローされます。
     * @throws IOException 転送エラー,権限が不足しているなど、正常に転送できなかったときスローされます。
     */
    public void login()
            throws UnknownHostException,
                IOException
    {
        _session = login(getHostname(), getUsername(), getPassword());
        _session.setCharset(getCharset());
    }

    /**
     * FTPサーバからログアウトします。
     */
    public void logout()
    {
        logout(_session);
    }

    /**
     * ファイルを送信します。
     * 
     * @param fromFile File 転送元ファイル
     * @param toDir String 転送先ディレクトリ
     * @param toFile String 転送先ファイル名
     * @param binary boolean バイナリモード転送のときはTrue.
     * 
     * @throws FileNotFoundException 送信元ファイルが見つからないときスローされます。
     * @throws IOException 転送エラー,権限が不足しているなど、正常に転送できなかったときスローされます。
     * @throws NotActiveException FTPサーバからのレスポンスタイムアウトが発生したときスローされます。
     */
    public void put(File fromFile, String toDir, String toFile, boolean binary)
            throws FileNotFoundException,
                IOException,
                NotActiveException
    {
        putFile(_session, fromFile, toDir, toFile, binary);
    }

    /**
     * ファイルをFTPサーバーから受信します。<br>
     * 
     * @param toFile File  転送先ファイル
     * @param fromDir String 転送元ディレクトリ
     * @param fromFile String 転送元ファイル名
     * @param binary boolean バイナリモード転送のときはTrue.
     * @return FTPリターンコード, (FTP_RET_XXXX)
     * 
     * @throws IOException 転送エラー,権限が不足しているなど、正常に転送できなかったときスローされます。
     * @throws NotActiveException FTPサーバからのレスポンスタイムアウトが発生したときスローされます。
     */
    public int get(File toFile, String fromDir, String fromFile, boolean binary)
            throws IOException,
                NotActiveException
    {
        try
        {
            File saveDir = toFile.getParentFile();
            if (!saveDir.exists())
            {
                saveDir.mkdirs();
            }

            getFile(_session, toFile, fromDir, fromFile, binary);
            return _session.getStatus();
        }
        catch (IOException ioe)
        {
            int rcode = _session.getCode();
            switch (rcode)
            {
                case FTPReply.FILE_UNAVAILABLE:
                case FTPReply.NEED_ACCOUNT_FOR_STORING_FILES:
                    return rcode;
            }
            throw ioe;
        }
    }

    /**
     * ファイルをFTPサーバーから削除します。<br>
     * 
     * @param toDir String 削除元ディレクトリ
     * @param toFile String 削除元ファイル名
     * @param binary boolean バイナリモード転送のときはTrue.
     * @return FTPリターンコード, (FTP_RET_XXXX)
     * 
     * @throws IOException 転送エラー,権限が不足しているなど、正常に転送できなかったときスローされます。
     * @throws NotActiveException FTPサーバからのレスポンスタイムアウトが発生したときスローされます。
     */
    public int delete(String toDir, String toFile,  boolean binary)
            throws IOException,
                NotActiveException
    {
        try
        {
            delete(_session, toDir, toFile, binary);
            return _session.getStatus();
        }
        catch (IOException ioe)
        {
            int rcode = _session.getCode();
            switch (rcode)
            {
                case FTPReply.FILE_UNAVAILABLE:
                case FTPReply.NEED_ACCOUNT_FOR_STORING_FILES:
                    return rcode;
            }
            throw ioe;
        }
    }

    //------------------------------------------------------------
    // Static methods
    //------------------------------------------------------------

    /**
     * 複数のファイルをFTPサーバーに送信します。
     *
     * @param host String 転送先ホスト
     * @param user String ユーザ名
     * @param passwd String パスワード
     * @param fromFile File[] 転送元ファイル
     * @param toDir String[] 転送先ディレクトリ
     * @param toFile String[] 転送先ファイル名
     * @param binary boolean[] バイナリモード転送のときはTrue.
     * @param charset ホストのキャラクタ・セット名
     * 
     * @return int 正常に転送できたときはRET_OK.
     * 
     * @throws UnknownHostException ホストがみつからないときにスローされます。
     * @throws IOException 転送エラー,権限が不足しているなど、正常に転送できなかったときスローされます。
     * @throws NotActiveException FTPサーバからのレスポンスタイムアウトが発生したときスローされます。
     */
    public static int put(String host, String user, String passwd, File[] fromFile, String[] toDir, String[] toFile,
            boolean[] binary, String charset)
            throws UnknownHostException,
                IOException,
                NotActiveException
    {
        FTPSession ctrlSess = login(host, user, passwd);
        ctrlSess.setCharset(charset);

        if (ctrlSess.getStatus() != 0)
        {
            // System.out.println(ctrlSess.getLastMessage()) ;
            return ctrlSess.getStatus();
        }
        for (int i = 0; i < fromFile.length; i++)
        {
            putFile(ctrlSess, fromFile[i], toDir[i], toFile[i], binary[i]);
        }
        logout(ctrlSess);

        return RET_OK;
    }

    /**
     * ファイルをFTPサーバーに送信します。
     *
     * @param host String 転送先ホスト
     * @param user String ユーザ名
     * @param passwd String パスワード
     * @param fromFile File 転送元ファイル
     * @param toDir String 転送先ディレクトリ
     * @param toFile String 転送先ファイル名
     * @param binary boolean バイナリモード転送のときはTrue.
     * @param charset ホストのキャラクタ・セット名
     * 
     * @return int 正常に転送できたときはRET_OK.
     * 
     * @throws UnknownHostException ホストがみつからないときにスローされます。
     * @throws IOException 転送エラー,権限が不足しているなど、正常に転送できなかったときスローされます。
     * @throws NotActiveException FTPサーバからのレスポンスタイムアウトが発生したときスローされます。
     */
    public static int put(String host, String user, String passwd, File fromFile, String toDir, String toFile,
            boolean binary, String charset)
            throws UnknownHostException,
                IOException,
                NotActiveException
    {
        File[] files = {
            fromFile
        };
        String[] fromdirs = {
            toDir
        };
        String[] fromfiles = {
            toFile
        };
        boolean[] binfs = {
            binary
        };
        return put(host, user, passwd, files, fromdirs, fromfiles, binfs, charset);
    }

    /**
     * 複数のファイルをFTPサーバーから受信します。
     *
     * @param host String 転送元ホスト
     * @param user String ユーザ名
     * @param passwd String パスワード
     * @param toFile File[] 転送先ファイル
     * @param fromDir String[] 転送元ディレクトリ
     * @param fromFile String[] 転送元ファイル名
     * @param binary boolean[] バイナリモード転送のときはTrue.
     * @param charset ホストのキャラクタ・セット名
     * 
     * @return int 正常に転送できたときはRET_OK.
     * 
     * @throws UnknownHostException ホストがみつからないときにスローされます。
     * @throws IOException 転送エラー,権限が不足しているなど、正常に転送できなかったときスローされます。
     * @throws NotActiveException FTPサーバからのレスポンスタイムアウトが発生したときスローされます。
     */
    public static int get(String host, String user, String passwd, File[] toFile, String[] fromDir, String[] fromFile,
            boolean[] binary, String charset)
            throws UnknownHostException,
                IOException,
                NotActiveException
    {
        FTPSession ctrlSess = login(host, user, passwd);
        ctrlSess.setCharset(charset);

        if (ctrlSess.getStatus() != 0)
        {
            // System.out.println(ctrlSess.getLastMessage()) ;
            return ctrlSess.getStatus();
        }
        for (int i = 0; i < toFile.length; i++)
        {
            getFile(ctrlSess, toFile[i], fromDir[i], fromFile[i], binary[i]);
        }
        logout(ctrlSess);

        return RET_OK;
    }

    /**
     * ファイルをFTPサーバーから受信します。<br>
     * 
     * @param host String 転送元ホスト
     * @param user String ユーザ名
     * @param passwd String パスワード
     * @param toFile File  転送先ファイル
     * @param fromDir String 転送元ディレクトリ
     * @param fromFile String 転送元ファイル名
     * @param binary boolean バイナリモード転送のときはTrue.
     * @param charset ホストのキャラクタ・セット名
     * 
     * @return int 正常に転送できたときはRET_OK.
     * 
     * @throws UnknownHostException ホストがみつからないときにスローされます。
     * @throws IOException 転送エラー,権限が不足しているなど、正常に転送できなかったときスローされます。
     * @throws NotActiveException FTPサーバからのレスポンスタイムアウトが発生したときスローされます。
     */
    public static int get(String host, String user, String passwd, File toFile, String fromDir, String fromFile,
            boolean binary, String charset)
            throws NotActiveException,
                UnknownHostException,
                IOException
    {
        File[] files = {
            toFile
        };
        String[] fromdirs = {
            fromDir
        };
        String[] fromfiles = {
            fromFile
        };
        boolean[] binfs = {
            binary
        };
        return get(host, user, passwd, files, fromdirs, fromfiles, binfs, charset);
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * passwordを返します。
     * @return passwordを返します。
     */
    public String getPassword()
    {
        return p_password;
    }

    /**
     * passwordを設定します。
     * @param password 
     */
    public void setPassword(String password)
    {
        p_password = password;
    }

    /**
     * serverを返します。
     * @return serverを返します。
     */
    public String getHostname()
    {
        return p_hostname;
    }

    /**
     *  serverを設定します。
     * @param server
     */
    public void setHostname(String server)
    {
        p_hostname = server;
    }

    /**
     * userを返します。
     * @return userを返します。
     */
    public String getUsername()
    {
        return p_username;
    }

    /**
     * userを設定します。
     * @param user 
     */
    public void setUsername(String user)
    {
        p_username = user;
    }

    /**
     * charsetを返します。
     * @return charsetを返します。
     */
    public String getCharset()
    {
        return p_charset;
    }

    /**
     * charsetを設定します。
     * @param charset 
     */
    public void setCharset(String charset)
    {
        p_charset = charset;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 該当のFTPサーバに接続し、コントロール用のFTPSessionを 返します。
     *
     * @param host String
     * @param user String
     * @param passwd String
     * @throws UnknownHostException
     * @throws IOException
     * @return PrintWriter
     */
    private static FTPSession login(String host, String user, String passwd)
            throws UnknownHostException,
                IOException
    {
        org.apache.commons.net.ftp.FTPClient client = new org.apache.commons.net.ftp.FTPClient();
        
        // 接続を行う
        try
        {
            // set socket timeout
            client.setDefaultTimeout(SOCKET_RECEIVE_TIMEOUT);
            
            // set data timeout
            client.setDataTimeout(SOCKET_RECEIVE_TIMEOUT);
            
            client.connect(host, 21);
        }
        catch (Exception e)
        {
            // connect error
            throw new UnknownHostException(host);
        }
        
        FTPSession ctrlSession = new FTPSession();
        ctrlSession.setFTPClient(client);

        try
        {
            // ログイン
            client.login(user, passwd);
            
            checkMessage(ctrlSession, FTPReply.USER_LOGGED_IN);
    
            // ホームディレクトリを取得・保存します。
            client.pwd();
            
            checkMessage(ctrlSession, FTPReply.PATHNAME_CREATED);
            
            String homedir = ctrlSession.getParameter(1);
            ctrlSession.setHomeDirectory(homedir);
    
            return ctrlSession;
        }
        catch (SocketTimeoutException e)
        {
            throw new NotActiveException();
        }
    }

    /**
     * ログアウトします。
     * 
     * @param ctrlSession
     */
    private static void logout(FTPSession ctrlSession)
    {
        org.apache.commons.net.ftp.FTPClient client = ctrlSession.getFTPClient();
        
        if (client != null && client.isConnected())
        {
            try
            {
                // ログアウト
                client.logout();
                
                // 切断
                client.disconnect();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * ファイルを送信します。
     *
     * @param ctrlSession FTPSession
     * @param fromFile String 転送元ファイル
     * @param toDir String
     * @param toFile String
     * @param binary boolean
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void putFile(FTPSession ctrlSession, File fromFile, String toDir, String toFile, boolean binary)
            throws FileNotFoundException,
                IOException
    {
        InputStream inStream = null;

        try
        {
            // 転送前の準備
            prepareTransfer(ctrlSession, toDir, binary);

            ctrlSession.getFTPClient().setBufferSize(STREAM_BUFFER_SIZE);
            
            // 送信
            inStream = new FileInputStream(fromFile);
            ctrlSession.getFTPClient().storeFile(toFile, inStream);
            
            checkMessage(ctrlSession, FTPReply.CLOSING_DATA_CONNECTION);
        }
        catch (SocketTimeoutException e)
        {
            e.printStackTrace();
            throw new NotActiveException();
        }
        finally
        {
            if (inStream != null)
            {
                inStream.close();
            }
        }
    }

    /**
     * ファイルを受信します。
     * 
     * @param ctrlSession
     * @param toFile
     * @param fromDir
     * @param fromFile
     * @param binary
     * @throws IOException
     */
    private static void getFile(FTPSession ctrlSession, File toFile, String fromDir, String fromFile, boolean binary)
            throws IOException
    {
        OutputStream outStream = null;
        
        try
        {
            // 転送前の準備
            prepareTransfer(ctrlSession, fromDir, binary);
            
            ctrlSession.getFTPClient().setBufferSize(STREAM_BUFFER_SIZE);
            
            // 受信
            outStream = new FileOutputStream(toFile);
            ctrlSession.getFTPClient().retrieveFile(fromFile, outStream);
            
            checkMessage(ctrlSession, FTPReply.CLOSING_DATA_CONNECTION);
        }
        catch (SocketTimeoutException e)
        {
            throw new NotActiveException();
        }
        finally
        {
            if (outStream != null)
            {
                outStream.close();
            }
        }
    }

    /**
     * ファイルを削除します。
     * 
     * @param ctrlSession
     * @param toDir
     * @param toFile
     * @param binary
     * @throws IOException
     */
    private static void delete(FTPSession ctrlSession, String toDir, String toFile, boolean binary)
            throws IOException
    {
        try
        {
            // 転送前の準備
            prepareTransfer(ctrlSession, toDir, binary);
            
            ctrlSession.getFTPClient().deleteFile(toFile);
    
            checkMessage(ctrlSession, FTPReply.FILE_ACTION_OK);
        }
        catch (SocketTimeoutException e)
        {
            throw new NotActiveException();
        }
    }

    /**
     * 転送前の準備を行います。<br>
     * ・ホームディレクトリへの移動
     * ・ターゲットディレクトリへの移動
     * ・転送モード(バイナリ・ASCII)のセット
     * 
     * @param ctrlSession セッション
     * @param dir ターゲットディレクトリ名
     * @param binary バイナリモードなら true.
     * @throws IOException 
     */
    private static void prepareTransfer(FTPSession ctrlSession, String dir, boolean binary)
            throws IOException
    {
        try
        {
            org.apache.commons.net.ftp.FTPClient client = ctrlSession.getFTPClient();
    
            // change directory to HOME first.
            client.cwd(ctrlSession.getHomeDirectory());
    
            checkMessage(ctrlSession, FTPReply.FILE_ACTION_OK);
    
            // change directory on host
            client.cwd(dir);
    
            checkMessage(ctrlSession, FTPReply.FILE_ACTION_OK);
    
            // 転送モード(バイナリ・ASCII)のセット
            if (binary)
            {
                client.setFileType(FTP.BINARY_FILE_TYPE);
            }
            else
            {
                client.setFileType(FTP.ASCII_FILE_TYPE);
            }
    
            checkMessage(ctrlSession, FTPReply.COMMAND_OK);
        }
        catch (SocketTimeoutException e)
        {
            throw new NotActiveException();
        }
    }

    /**
     * コントロールセッションから受信したメッセージを判断し、
     * 異常であれば例外をスローします。
     *
     * @param ctrlSession FTPSession
     * @param waitmsg 待ち受けるコード番号
     * @throws IOException
     */
    private static void checkMessage(FTPSession ctrlSession, int waitmsg)
            throws IOException
    {
        checkMessage(ctrlSession, waitmsg, true);
    }

    /**
     * コントロールセッションから受信したメッセージを判断し、
     * 異常であれば例外をスローします。
     *
     * @param ctrlSession FTPSession
     * @param waitmsg 待ち受けるコード番号
     * @param check 接続状態をチェックする場合は true.
     * @throws IOException
     */
    private static void checkMessage(FTPSession ctrlSession, int waitmsg, boolean check)
            throws IOException
    {
        ctrlSession.setErrorMessage("");
        ctrlSession.setStatus(RET_OK);
        
        org.apache.commons.net.ftp.FTPClient client = ctrlSession.getFTPClient();

        if (client == null || !client.isConnected())
        {
            if (check)
            {
                throw new NotActiveException();
            }
            return;
        }

        ctrlSession.setLastMessage(client.getReplyString());
        int rcode = client.getReplyCode();
        
        if (waitmsg != FTP_RET_NO_WAIT && waitmsg == rcode)
        {
            // 待ち受けるコード番号の場合、そのまま抜ける
            return;
        }
        
        if (!FTPReply.isPositiveCompletion(rcode))
        {
            ctrlSession.setErrorMessage(client.getReplyString());
            ctrlSession.setStatus(rcode);

            // ファイルなしのエラー時は、ログなし
            if(RET_NO_DIR != rcode)
            {
                Object[] tObj = new Object[1];
                tObj[0] = client.getReplyString();
                RmiMsgLogClient.write(WmsMessageFormatter.format(6022426, tObj), "FTPClient");
            }
            
            throw new IOException(client.getReplyString());
        }
    }

    /** ここからeSA-M 1.2用に追加 start */
    /**
     * ファイル名称を変更します。
     *
     * @param dir 対象ディレクトリ
     * @param fromfile 対象ファイル名
     * @param tofile 変更後ファイル名
     * @param binary boolean[] バイナリモード転送のときはTrue.
     * 
     * @return int 正常に転送できたときはRET_OK.
     * 
     * @throws UnknownHostException ホストがみつからないときにスローされます。
     * @throws IOException 転送エラー,権限が不足しているなど、正常に転送できなかったときスローされます。
     * @throws NotActiveException FTPサーバからのレスポンスタイムアウトが発生したときスローされます。
     */
    public int renameFile(String dir, String fromfile, String tofile, boolean binary)
            throws UnknownHostException,
                IOException,
                NotActiveException
    {
        FTPSession ctrlSess = login(getHostname(), getUsername(), getPassword());
        ctrlSess.setCharset(getCharset());

        if (ctrlSess.getStatus() != 0)
        {
            return ctrlSess.getStatus();
        }

        rename(_session, dir, fromfile, tofile, binary);

        logout(ctrlSess);

        return RET_OK;
    }

    /**
     * ファイルをリネームします。
     * (拡張子を.putに変更）
     * 
     * @param ctrlSession
     * @param dir 対象フォルダ
     * @param fromfile 対象ファイル
     * @param tofile 変更後ファイル名
     * @param binary バイナリモード転送のときはTrue.
     * 
     * @throws IOException
     */
    private static void rename(FTPSession ctrlSession, String dir, String fromfile, String tofile, boolean binary)
            throws IOException
    {
        try
        {
            // 転送前の準備
            prepareTransfer(ctrlSession, dir, binary);
    
            // change directory to HOME first.
            ctrlSession.getFTPClient().rename(fromfile, tofile);
    
            checkMessage(ctrlSession, FTPReply.FILE_ACTION_OK);
        }
        catch (SocketTimeoutException e)
        {
            throw new NotActiveException();
        }
    }

    /**
     * 対象ディレクトリ内のファイル一覧を取得します。
     *
     * @param dir 対象ディレクトリ
     * 
     * @return int 正常に転送できたときはRET_OK.
     * 
     * @throws UnknownHostException ホストがみつからないときにスローされます。
     * @throws IOException 転送エラー,権限が不足しているなど、正常に転送できなかったときスローされます。
     * @throws NotActiveException FTPサーバからのレスポンスタイムアウトが発生したときスローされます。
     */
    public List<String> getFileList(String dir)
            throws UnknownHostException,
                IOException,
                NotActiveException
    {
        FTPSession ctrlSess = login(getHostname(), getUsername(), getPassword());
        ctrlSess.setCharset(getCharset());

        if (ctrlSess.getStatus() != 0)
        {
            System.out.println(ctrlSess.getStatus());
            return null;
        }

        List<String> list = getFileList(_session, dir, false);

        logout(ctrlSess);

        return list;
    }

    /**
     * 対象フォルダの一覧を取得します。
     * @param ctrlSession
     * @param dir 対象フォルダ
     * @param binary バイナリモード転送のときはTrue.
     * 
     * @throws IOException
     */
    private static List<String> getFileList(FTPSession ctrlSession, String dir, boolean binary)
            throws IOException
    {
        List<String> list = new ArrayList<String>();

        try
        {
            // 転送前の準備
            prepareTransfer(ctrlSession, dir, binary);
            
            String[] names = ctrlSession.getFTPClient().listNames();
            
            checkMessage(ctrlSession, FTPReply.CLOSING_DATA_CONNECTION);
            
            if (names != null)
            {
                for (String name : names)
                {
                    list.add(name);
                }
            }
        }
        catch (SocketTimeoutException e)
        {
            throw new NotActiveException();
        }

        return list;
    }

    /** eSA-M1.2用に追加 end */

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: FTPClient.java,v 1.3 2009/06/24 04:26:43 mitsudome Exp $";
    }
}


/**
 * コントロールセッションの情報を保持するエンティティクラスです。
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author 未入力
 * @version 1.0
 */

class FTPSession
{
    private org.apache.commons.net.ftp.FTPClient p_client;

    private String p_lastMessage = "";

    private String p_errorMessage = "";

    private int p_status;

    private String p_homeDirectory;

    /**
     * @return errorMessageを返します。
     */
    public String getErrorMessage()
    {
        return p_errorMessage;
    }

    /**
     * @param errorMessage errorMessageを設定します。
     */
    public void setErrorMessage(String errorMessage)
    {
        p_errorMessage = errorMessage;
    }

    /**
     * @return homeDirectoryを返します。
     */
    public String getHomeDirectory()
    {
        return p_homeDirectory;
    }

    /**
     * @param homeDirectory homeDirectoryを設定します。
     */
    public void setHomeDirectory(String homeDirectory)
    {
        p_homeDirectory = homeDirectory;
    }

    /**
     * ホストのキャラクタ・セットを指定します。
     * @param charsetname
     */
    public void setCharset(String charsetname)
    {
        p_client.setControlEncoding(charsetname);
    }

    /**
     * ホストのキャラクタ・セットを取得します。
     * @return キャラクタ・セット
     */
    public Charset getCharset()
    {
        return Charset.forName(p_client.getControlEncoding());
    }

    /**
     * 現在のステータスをセットします。
     * @param status
     */
    public void setStatus(int status)
    {
        p_status = status;
    }

    /**
     * @return 現在のステータスを返します。
     */
    public int getStatus()
    {
        return p_status;
    }

    /**
     * @return 最後のメッセージを返します。
     */
    public String getLastMessage()
    {
        return p_lastMessage;
    }

    /**
     * 最後のメッセージをセットします。
     * @param message メッセージ
     */
    public void setLastMessage(String message)
    {
        p_lastMessage = message;
    }

    /**
     * メッセージからFTPのメッセージ番号を取得します。
     * @return メッセージ番号
     */
    public int getCode()
    {
        return p_client.getReplyCode();
    }

    /**
     * メッセージから指定の位置にある値を取り出します。
     * 
     * @param pos スペースで区切られたメッセージの位置(先頭 0)
     * @return メッセージパラメータ (クオーテーションマークは取り除かれています)
     */
    public String getParameter(int pos)
    {
        String rparam = "";
        try
        {
            if (p_client != null)
            {
                String[] rs = p_client.getReplyString().split(" ");

                String tg = rs[pos];
                int st = (tg.startsWith("\"") || tg.startsWith("'")) ? 1
                                                                    : 0;
                int ed = (tg.endsWith("\"") || tg.endsWith("'")) ? 1
                                                                : 0;

                if ((st + ed) > 0)
                {
                    rparam = tg.substring(st, tg.length() - ed);
                }
                else
                {
                    rparam = tg;
                }
            }
        }
        catch (Exception e)
        {
        }
        return rparam;
    }

    /**
     * この接続のFTPClientを取得します。
     * @return FTPClient
     */
    public org.apache.commons.net.ftp.FTPClient getFTPClient()
    {
        return p_client;
    }

    /**
     * この接続のFTPClientを取得します。
     * @param client FTPClient
     */
    public void setFTPClient(org.apache.commons.net.ftp.FTPClient client)
    {
        p_client = client;
    }
}

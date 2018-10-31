// $Id: IdProcess.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.communication.rft;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;

/**
 * RFTから受信した情報を処理するためのスーパークラスです。<br>
 * 各Idごとの処理は<code>processReceivedId()</code>メソッドに
 * 記述する必要があります。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class IdProcess
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** データベース接続を保持します。*/
    // TODO private化し、名称を _connection へ変更すること 2008-03-27 
    protected Connection _wConn;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * デフォルト・コンストラクタです。<br>
     * RFT通信モジュールから生成されるため、各サブクラスでは
     * デフォルト・コンストラクタが必須です。
     */
    protected IdProcess()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 処理の流れを記述したメソッドです。<br>
     * 各Idごとの詳細処理は、<code>processReceivedId()</code>メソッドを
     * オーバーライドする必要があります。
     * 
     * @param   rdt 受信電文
     * @param   sdt 送信電文
     * @throws  Exception 何か異常が発生した場合。
     */
    public abstract void processReceivedId(byte[] rdt, byte[] sdt)
            throws Exception;

    /**
     * DBコネクションを返します。
     * 
     * @return データベースコネクションを返します。
     */
    public Connection getConnection()
    {
        return _wConn;
    }

    /**
     * DBコネクションをセットします。
     * 
     * @param connection データベース接続用 Connection
     */
    public void setConnection(Connection connection)
    {
        _wConn = connection;
    }

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
     * トランザクションをコミットします。<br>
     * データベースコネクションは、このクラスが保持しているものを使用します。
     * @throws SQLException コミットに失敗したときスローされます。
     */
    protected void commit()
            throws SQLException
    {
        try
        {
            getConnection().commit();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            rollback();
            throw e;
        }
    }

    /**
     * トランザクションをロールバックします。<br>
     * データベースコネクションは、このクラスが保持しているものを使用します。
     */
    protected void rollback()
    {
        try
        {
            getConnection().rollback();
        }
        catch (SQLException e)
        {
            // ignore error on rollback
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
        return "$Id: IdProcess.java 87 2008-10-04 03:07:38Z admin $";
    }
}

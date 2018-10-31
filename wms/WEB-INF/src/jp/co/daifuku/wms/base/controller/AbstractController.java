// $Id: AbstractController.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.handler.AlterKey;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.DatabaseHandler;
import jp.co.daifuku.wms.handler.db.SQLSearchKey;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * コントローラクラスのための抽象スーパークラスです。
 * 各コントローラクラスは、このクラスのサブクラスとして用意されます。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class AbstractController
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** データベースコネクション */
    private Connection _connection;

    /** 呼び出し元クラス (直上のクラスではない場合がある) */
    private Class _caller;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public AbstractController(Connection conn, Class caller)
    {
        super();
        setConnection(conn);
        setCaller(caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 行ロックを行います。ロックタイムアウトが発生したとき、
     * リトライ回数に指定された回数だけリトライを行います。
     * 
     * @param skey 検索キー
     * @param handler ロックのために使用するデータベースハンドラ
     * @param maxRetry リトライ回数 
     * @return ロックされた対象レコードのエンティティ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException ロックリトライオーバーの時スローされます。
     * 
     */
    public Entity[] retryLock(SearchKey skey, DatabaseHandler handler, int maxRetry)
            throws ReadWriteException,
                LockTimeOutException
    {
        int sleepSec = WmsParam.LOCK_RETRY_SLEEP_SEC;
        return retryLock(skey, handler, sleepSec, maxRetry);
    }

    /**
     * 行ロックを行います。ロックタイムアウトが発生したとき、
     * WmsParamに指定された回数だけリトライを行います。
     * 
     * @param skey 検索キー
     * @param handler ロックのために使用するデータベースハンドラ
     * @return ロックされた対象レコードのエンティティ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException ロックリトライオーバーの時スローされます。
     * 
     */
    public Entity[] retryLock(SearchKey skey, DatabaseHandler handler)
            throws ReadWriteException,
                LockTimeOutException
    {
        int rcnt = WmsParam.LOCK_RETRY_COUNT;
        return retryLock(skey, handler, rcnt);
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * 呼び出し元クラスを返します。
     * @return 呼び出し元クラス
     */
    public Class getCaller()
    {
        return _caller;
    }

    /**
     * 呼び出し元クラスを設定します。
     * @param caller 呼び出し元クラス<br>
     * nullが指定されたときは、呼び出し先クラスがセットされます。
     */
    public void setCaller(Class caller)
    {
        Class actClass = (null == caller) ? getClass()
                                         : caller;
        _caller = actClass;
    }

    /**
     * 呼び出し元クラス名を返します。
     * @return 呼び出し元クラスの短縮名
     */
    public String getCallerName()
    {
        return getCaller().getSimpleName();
    }

    /**
     * データベースコネクションを返します。
     * @return データベースコネクション
     */
    public Connection getConnection()
    {
        return _connection;
    }

    /**
     * データベースコネクションを設定します。
     * @param connection データベースコネクション
     */
    public void setConnection(Connection connection)
    {
        try
        {
            if (connection == null || connection.isClosed())
            {
                throw new RuntimeException("Connection is null or closed!");
            }
            _connection = connection;
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Can not access to database!");
        }
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 行ロックを行います。ロックタイムアウトが発生したとき、指定された秒数だけ
     * スリープし、リトライ回数に指定された回数だけリトライを行います。
     * 
     * @param skey 検索キー
     * @param handler ロックのために使用するデータベースハンドラ
     * @param sleepSeconds リトライを行う間隔 (秒)
     * @param maxRetry リトライ回数 
     * @return ロックされた対象レコードのエンティティ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException ロックリトライオーバーの時スローされます。
     * 
     */
    public Entity[] retryLock(SearchKey skey, DatabaseHandler handler, int sleepSeconds, int maxRetry)
            throws ReadWriteException,
                LockTimeOutException
    {
        // the "retry" should be greater than 0
        // the "sleep" should be greater than 0
        if (1 > maxRetry)
        {
            throw new RuntimeException("Internal error. retryLock() called with maxRetry < 1");
        }
        if (1 > sleepSeconds)
        {
            throw new RuntimeException("Internal error. retryLock() called with sleepSeconds < 1");
        }

        for (int numRetry = 0; numRetry < maxRetry; numRetry++)
        {
            try
            {
                // get records with lock
                return handler.findForUpdate(skey, sleepSeconds);
            }
            catch (LockTimeOutException e)
            {
                // ignore lock timeout, until retry over
            }
            catch (InvalidStatusException e)
            {
                // this will not occur
            }
        }
        // lock retry over
        throw new LockTimeOutException();
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    /**
     * エンティティから同一レコードを検索するのキーを生成します。
     * 
     * @param ent 対象エンティティ
     * @param key 設定対象の検索キー
     * @param keyFields キー として使用するフィールド
     * @return 検索用キー
     */
    protected SQLSearchKey createKey(Entity ent, SQLSearchKey key, FieldName[] keyFields)
    {
        // field values move to key
        for (int f = 0; f < keyFields.length; f++)
        {
            FieldName tField = keyFields[f];
            Object keyvalue = ent.getValue(tField);
            if (null != keyvalue)
            {
                key.setKey(tField, keyvalue);
            }
        }
        return key;
    }

    /**
     * エンティティから同一レコードを検索するのキーを生成します。
     * 
     * @param ent 対象エンティティ (更新値)
     * @param key 設定対象の更新データセットキー
     * @param valueFields 更新対象フィールド
     * @return 検索用キー
     */
    protected AlterKey createAlterKey(Entity ent, AlterKey key, FieldName[] valueFields)
    {
        // field values move to key
        for (int f = 0; f < valueFields.length; f++)
        {
            FieldName tField = valueFields[f];
            Object upvalue = ent.getValue(tField);
            if (null == upvalue)
            {
                continue;
            }
            if (upvalue instanceof BigDecimal)
            {
                BigDecimal bdvalue = (BigDecimal)upvalue;
                if (0 > bdvalue.compareTo(new BigDecimal(0)))
                {
                    continue;
                }
            }
            key.setAdhocUpdateValue(tField, upvalue);
        }
        return key;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AbstractController.java 87 2008-10-04 03:07:38Z admin $";
    }
}

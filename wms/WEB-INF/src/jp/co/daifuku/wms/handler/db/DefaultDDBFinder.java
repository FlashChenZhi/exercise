// $Id: DefaultDDBFinder.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.GenericEntity;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * 汎用のDirectDBFinder実装です。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Softecs
 * @author  Last commit: $Author: admin $
 */

public class DefaultDDBFinder
        extends AbstractBasicDBFinder
        implements DirectDBFinder
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
    // private String _instanceVar ;
    private boolean _hasNext = false;

    private Entity _entity = new GenericEntity();

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションと対象のテーブル名を指定して
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param tbName テーブル名
     */
    public DefaultDDBFinder(Connection conn, String tbName)
    {
        super(conn, tbName);
    }

    /**
     * データベースコネクションと対象のテーブルメタデータを指定して、
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param smeta 該当テーブルのストアメタデータ
     */
    public DefaultDDBFinder(Connection conn, StoreMetaData smeta)
    {
        super(conn, smeta);
    }

    /**
     * データベースコネクションと対象のテーブル用エンティティを指定して、
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param ent 対象テーブルのエンティティ(StoreMetaDataを取得します)
     */
    public DefaultDDBFinder(Connection conn, Entity ent)
    {
        this(conn, ent.getStoreMetaData());
        setTemplateEntity(ent);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Deprecated
    public void search(String sql, int limit)
            throws ReadWriteException
    {
        try
        {
            search(sql, false, -1);
        }
        catch (LockTimeOutException e)
        {
            // this exception will not occur
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void search(String sql)
            throws ReadWriteException
    {
        try
        {
            search(sql, false, -1);
        }
        catch (LockTimeOutException e)
        {
            // this exception will not occur
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void searchForUpdate(String sql, int waitsec)
            throws ReadWriteException,
                LockTimeOutException
    {
        search(sql, true, waitsec);
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNext()
    {
        try
        {
            return hasMoreRecord(getResultSet());
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            return false;
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * テンプレート用entityを返します。
     * @return entityを返します。
     */
    private Entity getTemplateEntity()
    {
        return _entity;
    }

    /**
     * テンプレート用entityを設定します。
     * @param entity entity
     */
    private void setTemplateEntity(Entity entity)
    {
        if (null == entity)
        {
            return;
        }
        Entity tmpEntity = (Entity)entity.clone();
        tmpEntity.clear();

        _entity = tmpEntity;
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    protected Entity createEntity()
    {
        Entity entity = getTemplateEntity();
        AbstractEntity ent = (AbstractEntity)entity.clone();
        return ent;
    }

    /**
     * {@inheritDoc}
     */
    protected boolean hasMoreRecord(ResultSet rset)
            throws SQLException
    {
        try
        {
            int currPos = rset.getRow();
            int startPoint = getStartPoint();
            if (currPos > startPoint)
            {
                _hasNext = true;
            }
            else
            {
                _hasNext = rset.next();
            }
            return _hasNext;
        }
        catch (SQLException e)
        {
            _hasNext = false;
            throw e;
        }
    }

    /**
     * データベースを検索し、対象データを取得します。<br>
     * <ul>
     * Update at 2007-11-12
     * <li>検索上限件数は現実的にSQL文が返す件数を取得する方法がないため削除されました。
     * </ul>
     * 
     * @param sql 検索SQL
     * @param withLock レコードロックを行うときは<code>true</code>を設定します
     * @param waitsec ロック待ち秒数 (無制限待ちの場合は、-1を指定します)
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     * @throws LockTimeOutException ロック待ちタイムアウト
     */
    protected void search(String sql, boolean withLock, int waitsec)
            throws ReadWriteException,
                LockTimeOutException
    {
        try
        {
            close();
            
            DEBUG.MSG(DEBUG.HANDLER, sql);

            Connection conn = getConnection();
            Statement sstmt = HandlerUtil.createStatement(conn, false, isForwardOnly());
            ResultSet result = sstmt.executeQuery(sql);

            // SAVE Statement and ResultSet
            setStatement(sstmt);
            setResultSet(result);
        }
        catch (SQLException e)
        {
            HandlerUtil.handleLockError(e, getSQLErrors(), getClass());
        }
    }

    /**
     * ステートメントを生成します。
     * @param conn データベースコネクション
     * @throws SQLException ステートメントの生成に失敗したときスローされます。
     */
    protected void setupStatement(Connection conn)
            throws SQLException
    {
        if (null == getStatement())
        {
            setStatement(HandlerUtil.createStatement(conn, true, true));
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
        return "$Id: DefaultDDBFinder.java 87 2008-10-04 03:07:38Z admin $";
    }
}

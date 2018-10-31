//$Id: AbstractDBFinder.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.InvalidSubClassException;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * データベース（テーブル）を検索するためのスーパークラスです。<br>
 * search()については、基本的にこのクラスが実装しています。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */
public abstract class AbstractDBFinder
        extends AbstractBasicDBFinder
        implements DatabaseFinder
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションと対象のテーブル名を指定して
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param tbName テーブル名
     */
    public AbstractDBFinder(Connection conn, String tbName)
    {
        super(conn, tbName);
    }

    /**
     * データベースコネクションと対象のテーブルメタデータを指定して、
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param smeta 該当テーブルのストアメタデータ
     */
    public AbstractDBFinder(Connection conn, StoreMetaData smeta)
    {
        super(conn, smeta);
    }

    /**
     * データベースコネクションと対象のテーブル用エンティティを指定して、
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param ent 対象テーブルのエンティティ(StoreMetaDataを取得します)
     */
    public AbstractDBFinder(Connection conn, Entity ent)
    {
        this(conn, ent.getStoreMetaData());
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public int search(SearchKey key)
            throws ReadWriteException
    {
        return search(key, LIMIT_UNLIMTED);
    }

    /**
     * {@inheritDoc}
     */
    public int search(SearchKey key, int limit)
            throws ReadWriteException
    {
        int cnt = 0;
        try
        {
            cnt = search(key, limit, false, WAIT_SEC_NOWAIT);
        }
        catch (LockTimeOutException e)
        {
            // this exception will not occur
            e.printStackTrace();
        }
        return cnt;
    }

    /**
     * {@inheritDoc}
     */
    public int searchForUpdate(SearchKey key, int waitsec)
            throws ReadWriteException,
                LockTimeOutException
    {
        return search(key, LIMIT_UNLIMTED, true, waitsec);
    }

    /**
     * データベースを検索し、対象データを取得します。
     * @param key 検索のためのKey
     * @param limit 最大取得件数(0以下を指定したときは全件取得)
     * @param withLock レコードロックを行うときは<code>true</code>を設定します
     * @param waitsec ロック待ち秒数 (無制限待ちの場合は、-1を指定します)
     * @return 検索結果の件数
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     * @throws LockTimeOutException ロック待ちタイムアウト
     */
    protected int search(SearchKey key, int limit, boolean withLock, int waitsec)
            throws ReadWriteException,
                LockTimeOutException
    {
        checkOpen();

        SQLSearchKey findKey = null;
        setSearchKey(null);
        if (key instanceof SQLSearchKey)
        {
            findKey = (SQLSearchKey)key;
            // save executing find key.
            setSearchKey(findKey);
        }
        else
        {
            throw new InvalidSubClassException(SQLSearchKey.class, key.getClass());
        }

        int count = 0;

        // close old statement and resultset.
        close();

        SQLGenerator gen = getSQLGenerator();
        Connection conn = getConnection();
        try
        {
            boolean noLimit = (limit < 1) || withLock;
            boolean largeQuery = (limit > MAXDISP);

            count = countTargetRecord(findKey, gen, largeQuery);

            // 件数がLIMIT以下の場合にのみ検索を実行します
            boolean underLimit = (count <= limit);

            if (noLimit || underLimit)
            {
                // get SQL string
                String findSQL = (withLock) ? gen.getFINDFORUPDATEWAITSQL(findKey, waitsec)
                                           : gen.getFINDSQL(findKey);

                DEBUG.MSG(DEBUG.HANDLER, findSQL);

                Statement sstmt = HandlerUtil.createStatement(conn, false, isForwardOnly());
                ResultSet result = sstmt.executeQuery(findSQL);

                // SAVE Statement and ResultSet
                setStatement(sstmt);
                setResultSet(result);

                count = countTargetRecord(findKey, gen, largeQuery);
            }
            else
            {
                DEBUG.MSG(DEBUG.HANDLER, "WARNING: QUERY LIMIT OVERFLOW.");
            }
        }
        catch (SQLException e)
        {
            HandlerUtil.handleLockError(e, getSQLErrors(), getClass());
        }

        setNumRecords(count);

        return count;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * 検索対象レコード数をカウントします。
     * @param findKey 検索のためのKey
     * @param gen SQLGenerator
     * @param largeQuery
     * @return レコード数
     * @throws SQLException
     */
    int countTargetRecord_OLD(SQLSearchKey findKey, SQLGenerator gen, boolean largeQuery)
            throws SQLException
    {
        Connection conn = getConnection();
        int count = 0;
        if (largeQuery && !isForwardOnly())
        {
            System.err.println("WARNING: THIS MAY TOO LARGE QUERY, Set limit or use forward only.");
        }

        Statement stmt = HandlerUtil.createStatement(conn, false, true);

        // save original collect condition
        // findKey.clearCollect();
        // String cntSQL = gen.getCOUNTSQL(findKey);

        String cntSQL = gen.getRecCOUNTSQL(findKey);

        DEBUG.MSG(DEBUG.HANDLER, cntSQL);

        // GET RESULT COUNT OF THIS QUERY
        ResultSet cntResult = stmt.executeQuery(cntSQL);
        if (cntResult.next())
        {
            count = cntResult.getInt(1);
        }
        HandlerUtil.closeStatement(stmt);
        return count;
    }

    /**
     * 検索対象レコード数をカウントします。
     * 
     * @param findKey 検索のためのKey
     * @param gen SQLGenerator
     * @param largeQuery
     * @return レコード数
     * @throws SQLException
     */
    int countTargetRecord(SQLSearchKey findKey, SQLGenerator gen, boolean largeQuery)
            throws SQLException
    {
        // save collect condition
        // 取得条件を書き換えるのでオリジナルを保存して
        // finally節でオリジナルの取得条件を復旧します。
        List orgCollect = findKey.getCollectConditionList();

        try
        {
            Connection conn = getConnection();
            int count = 0;
            if (largeQuery && !isForwardOnly())
            {
                System.err.println("WARNING: THIS MAY TOO LARGE QUERY, Set limit or use forward only.");
            }

            // 以下の条件なら結果が1行となる
            // GROUP BY が指定されている場合、集約関数が   2つ以上
            // GROUP BY が指定されていなければ、集約関数が 1以上
            int numiFunc = gen.countIntensiveFunction(findKey);
            List grpList = findKey.getGroupConditionList();
            boolean withGroupBy = (null != grpList) && (0 < grpList.size());
            int funcLimit = withGroupBy ? 1
                                       : 0;
            if (funcLimit < numiFunc)
            {
                return 1;
            }

            if (null == grpList || 0 == grpList.size())
            {
                if (0 < numiFunc)
                {
                    return 1;
                }
            }
            else
            {
                // with group
                if (1 < numiFunc)
                {
                    return 1;
                }
            }

            Statement stmt = HandlerUtil.createStatement(conn, false, true);

            findKey.clearCollect();
            String cntSQL = gen.getCOUNTSQL(findKey);

            DEBUG.MSG(DEBUG.HANDLER, cntSQL);

            // GET RESULT COUNT OF THIS QUERY
            ResultSet cntResult = stmt.executeQuery(cntSQL);
            if (cntResult.next())
            {
                count = cntResult.getInt(1);
            }
            HandlerUtil.closeStatement(stmt);
            return count;
        }
        finally
        {
            // restore original condition list
            findKey.setCollectConditionList(orgCollect);
        }
    }

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

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
        return "$Id: AbstractDBFinder.java 87 2008-10-04 03:07:38Z admin $";
    }
}

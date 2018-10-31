//$Id: AbstractBasicDBFinder.java 8082 2015-02-16 05:42:59Z okamura $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * データベース（テーブル）を検索するための基本スーパークラスです。
 *
 * @version $Revision: 8082 $, $Date: 2015-02-16 14:42:59 +0900 (月, 16 2 2015) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public abstract class AbstractBasicDBFinder
        implements BasicDatabaseFinder
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ポインタ,件数の初期値 */
    protected static final int REC_INIT = 0;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    /** ステートメントを管理する変数 */
    private Statement _statement = null;

    /** 検索結果を保持する変数 */
    private ResultSet _resultSet = null;

    /**
     * データベース接続用のConnectionインスタンス。<BR>
     * トランザクション管理は、このクラスでは行わない。
     */
    private Connection _connection;

    private String _storeName;

    private StoreMetaData _storeMetaData;

    private SQLGenerator _SQLGenerator;

    private SQLErrors _SQLErrors;

    private boolean _forwardOnly = true;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private int _startPoint = REC_INIT;

    private int _numRecords = REC_INIT;

    private boolean _isOpen = false;

    private SQLSearchKey _searchKey = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションと対象のテーブル名を指定して
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param tbName テーブル名
     */
    public AbstractBasicDBFinder(Connection conn, String tbName)
    {
        setStoreName(tbName);
        setConnection(conn);
    }

    /**
     * データベースコネクションと対象のテーブルメタデータを指定して、
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param smeta 該当テーブルのストアメタデータ
     */
    public AbstractBasicDBFinder(Connection conn, StoreMetaData smeta)
    {
        setConnection(conn);
        setStoreMetaData(smeta);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void open(boolean forwardOnly)
    {
        _numRecords = REC_INIT;
        _startPoint = REC_INIT;
        setForwardOnly(forwardOnly);

        _isOpen = true;
    }

    /**
     * {@inheritDoc}
     */
    public void close()
    {
        try
        {
            _numRecords = REC_INIT;
            _startPoint = REC_INIT;

            Connection conn = getConnection();
            if ((null == conn) || conn.isClosed())
            {
                return;
            }
            HandlerUtil.closeStatement(getStatement());
        }
        catch (SQLException e)
        {
            // 何もしません。
        }
        finally
        {
            setStatement(null);
            setResultSet(null);
        }
    }
    
    // 2015/02/16 Y.Okamura modify http://swd-redmine-odc/redmine/issues/1328対策
    /**
     * {@inheritDoc}
     */
    protected void finalizeClose()
    {
        try
        {
            _numRecords = REC_INIT;
            _startPoint = REC_INIT;
            
            Connection conn = getConnection();
            if ((null == conn))
            {
                return;
            }
            HandlerUtil.closeStatement(getStatement());
        }
        finally
        {
            setStatement(null);
            setResultSet(null);
        }
    }
    // 2015/02/16 Y.Okamura modify http://swd-redmine-odc/redmine/issues/1328対策

    /**
     * 検索結果を取得します。<BR>
     * ForwardOnlyのカーソルだった場合、このメソッドを使用して
     * 途中からのデータを取得することはできません。<BR>
     * 例：1000件のデータがヒットして、350-400件目を取得する など。<BR>
     * 
     * @param start 取得開始行 (0 = 先頭)
     * @param end 取得終了行 (0 = 取得しない)
     * @return 検索結果
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public Entity[] getEntities(int start, int end)
            throws ReadWriteException
    {
        checkOpen();

        List entityList = new ArrayList();
        Entity tmpEntity = createEntity();

        ResultSet rset = getResultSet();
        try
        {
            int count = end - start;
            int currRow = rset.getRow();
            // setup first row (with fetch)
            boolean canRead = (isForwardOnly()) ? hasMoreRecord(rset)
                                               : rset.absolute(start + 1);
            // read out of result set?
            if (!canRead)
            {
                // 指定された行が正しくありません
                // RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, getClass().getName(), null);
                // throw new ReadWriteException();
                if (isForwardOnly())
                {
                    // ForwardOnlyでない場合は、現在のResultSetが行数を表すとは
                    // 限らないので、アジャストできない
                    _numRecords = currRow;
                }
                return newEntityArray(0);
            }

            for (int rcnt = 0; rcnt < count; rcnt++)
            {
                // no fetch needed at first
                if (rcnt != 0)
                {
                    if (!rset.next())
                    {
                        // next()がfalseならば、直前のgetRow()が全体の行数を
                        // 表すので、ForwardOnlyであってもなくても、アジャストする
                        _numRecords = currRow;
                        break;
                    }
                }
                HandlerUtil.resultToEntity(rset, tmpEntity, getSearchKey());
                entityList.add(tmpEntity);

                tmpEntity = createEntity();

                currRow = rset.getRow();
                if (0 < currRow)
                {
                    _startPoint = currRow;
                }
            }

            // テーブル毎のEntity[]へキャストできるように配列を作成します
            Entity[] entityArr = newEntityArray(entityList.size());
            entityList.toArray(entityArr);

            return entityArr;
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Entity[] getEntities(int numRec)
            throws ReadWriteException
    {
        Entity[] entArr = getEntities(_startPoint, _startPoint + numRec);

        return entArr;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNext()
    {
        return (_startPoint < _numRecords);
    }

    /**
     * もしクローズされていないステートメントや
     * 結果セットがあればクローズされます。
     * @throws Throwable オブジェクトの破棄に失敗したときスローされます。
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize()
            throws Throwable
    {
        // 2015/02/16 Y.Okamura modify http://swd-redmine-odc/redmine/issues/1328対策
        // close();
        finalizeClose();
        // 2015/02/16 Y.Okamura modify http://swd-redmine-odc/redmine/issues/1328対策
        super.finalize();
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public Connection getConnection()
    {
        return _connection;
    }

    /**
     * {@inheritDoc}
     */
    public void setConnection(Connection connection)
    {
        _connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    public SQLErrors getSQLErrors()
    {
        return _SQLErrors;
    }

    /**
     * @return resultSetを返します。
     */
    public ResultSet getResultSet()
    {
        return _resultSet;
    }

    /**
     * @param resultSet resultSetを設定します。
     */
    protected void setResultSet(ResultSet resultSet)
    {
        _resultSet = resultSet;
    }

    /**
     * @return 保存されたstatementを返します。
     */
    public Statement getStatement()
    {
        return _statement;
    }

    /**
     * @param statement statementを保存します。
     */
    protected void setStatement(Statement statement)
    {
        _statement = statement;
    }

    /**
     * 対応するテーブル名を取得します。
     * @return テーブル名
     */
    public String getStoreName()
    {
        return _storeName;
    }

    /**
     * 対象のテーブル名を設定します。<br>
     * 同時に、セットされたテーブルのメタデータを取得し、保持します。
     * @param tableName テーブル名
     */
    public void setStoreName(String tableName)
    {
        StoreMetaData smeta = HandlerUtil.loadStoreMetaData(tableName);
        setStoreMetaData(smeta);
    }

    /**
     * @param storeMetaData storeMetaDataを設定します。
     */
    public void setStoreMetaData(StoreMetaData storeMetaData)
    {
        _storeMetaData = storeMetaData;
        _SQLGenerator = HandlerUtil.loadSQLGenerator(_storeMetaData);
        _SQLErrors = HandlerUtil.loadSQLErrors(_storeMetaData);

        _storeName = _storeMetaData.getName();
    }

    /**
     * 対象のSQLジェネレータを取得します。
     * @return SQLジェネレータ
     */
    public SQLGenerator getSQLGenerator()
    {
        return _SQLGenerator;
    }

    /**
     * テーブルのメタデータを取得します。
     * @return メタデータ
     */
    public StoreMetaData getStoreMetaData()
    {
        return _storeMetaData;
    }

    /**
     * @return forwardOnlyを返します。
     */
    public boolean isForwardOnly()
    {
        return _forwardOnly;
    }

    /**
     * @param forwardOnly forwardOnlyを設定します。
     */
    public void setForwardOnly(boolean forwardOnly)
    {
        _forwardOnly = forwardOnly;
    }

    /**
     * numRecordsを返します。
     * @return numRecordsを返します。
     */
    protected int getNumRecords()
    {
        return _numRecords;
    }

    /**
     * numRecordsを設定します。
     * @param numRecords numRecords
     */
    protected void setNumRecords(int numRecords)
    {
        _numRecords = numRecords;
    }

    /**
     * startPointを返します。
     * @return startPointを返します。
     */
    protected int getStartPoint()
    {
        return _startPoint;
    }

    /**
     * startPointを設定します。
     * @param startPoint startPoint
     */
    protected void setStartPoint(int startPoint)
    {
        _startPoint = startPoint;
    }

    /**
     * オープン済みでなければRuntimeExceptionをスローします。
     */
    protected void checkOpen()
    {
        if (!_isOpen)
        {
            throw new RuntimeException("Finder is not opened.");
        }
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * エンティティの配列を作成して返します。<br>
     * 配列の内容はすべてnullです。
     * 
     * @param numEnts 配列の要素数 (0以上を指定してください)
     * @return ファインダがサポートするべきエンティティの配列
     */
    Entity[] newEntityArray(int numEnts)
    {
        return (Entity[])Array.newInstance(createEntity().getClass(), numEnts);
    }

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * データベースカーソルに次のレコードがあるかどうか調査します。
     * 
     * @param rset 結果セット
     * @return 次のレコードがある場合、true.
     * @throws SQLException データベースアクセスエラー
     */
    protected boolean hasMoreRecord(ResultSet rset)
            throws SQLException
    {
        return rset.next();
    }

    /**
     * このハンドラが操作する対象テーブルのためのエンティティクラスを
     * 生成して返します。
     * 
     * @return テーブルごとのエンティティ
     */
    protected abstract Entity createEntity();

    /**
     * searchKeyを返します。
     * @return searchKeyを返します。
     */
    SQLSearchKey getSearchKey()
    {
        return _searchKey;
    }

    /**
     * searchKeyを設定します。
     * @param searchKey searchKey
     */
    void setSearchKey(SQLSearchKey searchKey)
    {
        _searchKey = searchKey;
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
        return "$Id: AbstractBasicDBFinder.java 8082 2015-02-16 05:42:59Z okamura $";
    }
}

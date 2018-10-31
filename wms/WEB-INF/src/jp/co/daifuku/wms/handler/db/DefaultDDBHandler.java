// $Id: DefaultDDBHandler.java 87 2008-10-04 03:07:38Z admin $
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
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * 汎用のDirectDBHandler実装です。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class DefaultDDBHandler
        implements DirectDBHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private Connection p_connection = null;

    private ResultSet p_result = null;

    private boolean p_simulationMode;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private Statement _stmt = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションを指定してインスタンスを生成します。
     * @param conn データベースコネクション
     */
    public DefaultDDBHandler(Connection conn)
    {
        setConnection(conn);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * SQL文を実行します。<br>
     * 問い合わせSQL文の場合は、このメソッドを実行後、
     * getResultSet()でResultSetを取得する事ができます。<br>
     * Entityとして取得したい場合は、getEntities()を使用してください。
     * 
     * @param SQL 実行するSQL文
     * @return 実行結果(更新系の場合は行数, 問い合わせ系の場合は RETURN_EXEC_QUERY)
     * @throws ReadWriteException データベースアクセスエラー発生時にスローされます。
     */
    public int execute(String SQL)
            throws ReadWriteException
    {
        // reset resultset
        p_result = null;

        String exSQL = SQL;

        DEBUG.MSG(DEBUG.HANDLER, exSQL);
        if (isSimulationMode())
        {
            return 0;
        }

        try
        {
            setupStatement(getConnection());
            Statement stmt = getStatement();
            boolean query = stmt.execute(exSQL);
            int execNum = stmt.getUpdateCount();

            if (query)
            {
                // save resultset if execute the query
                ResultSet result = stmt.getResultSet();
                p_result = result;
            }
            return execNum;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            ReadWriteException ex = new ReadWriteException(e);
            throw ex;
        }
    }

    /**
     * 問い合わせを実行します。
     * 
     * @param SQL 実行するSQL文
     * @param entityTemp エンティティ・テンプレート
     * @return 問い合わせ結果エンティティ
     * @throws ReadWriteException データベースアクセスエラー発生時にスローされます。
     */
    public Entity[] query(String SQL, Entity entityTemp)
            throws ReadWriteException
    {
        int exret = execute(SQL);
        if (isSimulationMode())
        {
            return new Entity[0];
        }
        if (exret != RETURN_EXEC_QUERY)
        {
            throw new ReadWriteException();
        }
        Entity[] retEnts = getEntities(Integer.MAX_VALUE, entityTemp);
        return retEnts;
    }

    /**
     * {@inheritDoc}
     */
    public void close()
    {
        HandlerUtil.closeStatement(_stmt);
    }

    /**
     * インスタンスが破棄される際に、close()を呼び出します。
     * @throws Throwable 破棄される際に例外が発生したときスローされます。
     */
    protected void finalize()
            throws Throwable
    {
        this.close();
        super.finalize();
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public ResultSet getResultSet()
    {
        return p_result;
    }

    /**
     * {@inheritDoc}
     */
    public Entity[] getEntities(int numRec, Entity entityTemp)
            throws ReadWriteException
    {
        try
        {
            Entity[] retEnts = new Entity[0];

            // get record from resultset
            ResultSet result = p_result;
            if (result == null)
            {
                return retEnts;
            }

            // create entity buffer array
            Vector<Entity> entbuf = new Vector<Entity>();

            for (int i = 0; i < numRec; i++)
            {
                if (!result.next())
                {
                    // finish reading, close the result set
                    HandlerUtil.closeResultSet(result);
                    p_result = null;
                    break;
                }
                Entity ent = entityTemp.getClass().newInstance();
                ent.clear();
                HandlerUtil.resultToEntity(result, ent);

                // set entity to buffer
                entbuf.add(ent);
            }

            AbstractEntity[] entityArr = (AbstractEntity[])Array.newInstance(entityTemp.getClass(), entbuf.size());
            entbuf.copyInto(entityArr);
            
            return entityArr;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            if (e instanceof SQLException)
            {
                throw new ReadWriteException(e);
            }
            else
            {
                throw new ReadWriteException();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public Connection getConnection()
    {
        return p_connection;
    }

    /**
     * {@inheritDoc}
     */
    public void setConnection(Connection conn)
    {
        p_connection = conn;
    }

    /**
     * ステートメントを返します。
     * @return SQL実行用ステートメント
     */
    protected Statement getStatement()
    {
        return _stmt;
    }

    /**
     * 仮実行モードを返します。
     * @return simulationModeを返します。
     */
    public boolean isSimulationMode()
    {
        return p_simulationMode;
    }

    /**
     * {@inheritDoc}
     */
    public void setSimulationMode(boolean modeOn)
    {
        p_simulationMode = modeOn;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * ステートメントを生成します。
     * @param conn データベースコネクション
     * @throws SQLException ステートメントの生成に失敗したとき
     * スローされます。
     */
    protected void setupStatement(Connection conn)
            throws SQLException
    {
        if (_stmt == null)
        {
            _stmt = HandlerUtil.createStatement(conn, true, true);
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
        return "$Id: DefaultDDBHandler.java 87 2008-10-04 03:07:38Z admin $";
    }
}

//$Id: AbstractDBHandler.java 8084 2015-02-16 08:02:36Z okamura $
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

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.AlterKey;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.InvalidSubClassException;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.field.FieldMetaData;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.field.validator.ValidateError;
import jp.co.daifuku.wms.handler.field.validator.ValidateException;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * データベース（テーブル）を操作するためのスーパークラスです。<br>
 * 操作についてのロジックはこのクラスが実装しています。<br>
 * 各テーブル用に用意されるハンドラクラスでは、createEntity()メソッドを
 * オーバーライドしてテーブル用のエンティティを生成するようにしてください。
 * 
 * @version $Revision: 8084 $, $Date: 2015-02-16 17:02:36 +0900 (月, 16 2 2015) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public abstract class AbstractDBHandler
        implements DatabaseHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // TODO getting from resource!
    private static final int MAX_RESULT = 5000;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private Connection p_connetction;

    private String p_storeName;

    private StoreMetaData p_storeMetaData;

    private SQLGenerator p_SQLGenerator;

    private SQLErrors p_SQLErrors;

    private boolean p_simMode = false;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションと対象のテーブル名を指定して、
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param tbName テーブル名
     */
    public AbstractDBHandler(Connection conn, String tbName)
    {
        setConnection(conn);
        setStoreName(tbName);
    }

    /**
     * データベースコネクションと対象のテーブルメタデータを指定して、
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param smeta 該当テーブルのストアメタデータ
     */
    public AbstractDBHandler(Connection conn, StoreMetaData smeta)
    {
        setConnection(conn);
        setStoreMetaData(smeta);
    }

    /**
     * データベースコネクションと対象のテーブル用エンティティを指定して、
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param ent 対象テーブルのエンティティ(StoreMetaDataを取得します)
     */
    public AbstractDBHandler(Connection conn, Entity ent)
    {
        setConnection(conn);
        setStoreMetaData(ent.getStoreMetaData());
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public Entity[] find(SearchKey key)
            throws ReadWriteException
    {
        return find(key, 0);
    }

    /**
     * {@inheritDoc}
     */
    public Entity[] find(SearchKey key, int limit)
            throws ReadWriteException
    {
        SQLSearchKey findKey = null;
        if (key instanceof SQLSearchKey)
        {
            findKey = (SQLSearchKey)key;
        }
        else
        {
            throw new InvalidSubClassException(SQLSearchKey.class, key.getClass());
        }

        Statement stmt = null;
        Entity[] entities = null;

        try
        {
            stmt = HandlerUtil.createStatement(getConnection(), false, true);

            SQLGenerator sqlgen = getSQLGenerator();
            String sqlstring = sqlgen.getFINDSQL(findKey);

            if (!p_simMode)
            {
                DEBUG.MSG(DEBUG.HANDLER, sqlstring);
                ResultSet rset = stmt.executeQuery(sqlstring);
                entities = convertEntities(rset, limit, findKey);
            }
            else
            {
                System.out.println(sqlstring);
                entities = (AbstractEntity[])Array.newInstance(createEntity().getClass(), 0);
            }
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            ReadWriteException ex = new ReadWriteException(e);
            throw ex;
        }
        finally
        {
            HandlerUtil.closeStatement(stmt);
        }
        return entities;
    }

    /**
     * {@inheritDoc}
     */
    public Entity findPrimary(SearchKey key)
            throws ReadWriteException,
                NoPrimaryException
    {
        Entity[] entities = find(key, 2);

        // 検索条件にて対象情報の件数を取得する。
        int rCount = entities.length;
        if (rCount == 0)
        {
            // 該当データが見つからない場合nullを返す。
            return null;
        }
        else if (rCount > 1)
        {
            throw (new NoPrimaryException());
        }
        return entities[0];
    }

    /**
     * {@inheritDoc}
     */
    public Entity[] findForUpdate(SearchKey key)
            throws ReadWriteException,
                LockTimeOutException
    {
        return findForUpdate(key, LIMIT_UNLIMTED, WAIT_SEC_DEFAULT);
    }

    /**
     * {@inheritDoc}
     */
    public Entity[] findForUpdate(SearchKey key, int waitSec)
            throws ReadWriteException,
                LockTimeOutException
    {
        return findForUpdate(key, LIMIT_UNLIMTED, waitSec);
    }

    /**
     * {@inheritDoc}
     */
    public Entity[] findForUpdate(SearchKey key, int limit, int waitSec)
            throws ReadWriteException,
                LockTimeOutException
    {

        return findForUpdate(key, limit, waitSec, true);
    }

    /**
     * {@inheritDoc}
     */
    public Entity findPrimaryForUpdate(SearchKey key)
            throws ReadWriteException,
                NoPrimaryException,
                LockTimeOutException
    {
        return findPrimaryForUpdate(key, WAIT_SEC_DEFAULT);
    }

    /**
     * {@inheritDoc}
     */
    public Entity findPrimaryForUpdate(SearchKey key, int waitSec)
            throws ReadWriteException,
                NoPrimaryException,
                LockTimeOutException
    {
        Entity[] entities = findForUpdate(key, 2, waitSec);
        if (null == entities || 0 == entities.length)
        {
            // 該当データが見つからないか、ロックされていたときはnullを返す。
            return null;
        }
        else if (entities.length > 1)
        {
            throw (new NoPrimaryException());
        }
        return entities[0];
    }

    /**
     * {@inheritDoc}
     */
    public int count(SearchKey key)
            throws ReadWriteException
    {
        SQLSearchKey findKey = null;
        if (key instanceof SQLSearchKey)
        {
            findKey = (SQLSearchKey)key;
        }
        else
        {
            throw new InvalidSubClassException(SQLSearchKey.class, key.getClass());
        }

        Statement stmt = null;
        try
        {
            stmt = HandlerUtil.createStatement(getConnection(), false, true);

            SQLGenerator sqlgen = getSQLGenerator();
            String sqlstring = sqlgen.getCOUNTSQL(findKey);

            if (!p_simMode)
            {
                DEBUG.MSG(DEBUG.HANDLER, sqlstring);
                ResultSet rset = stmt.executeQuery(sqlstring);
                if (rset.next())
                {
                    return rset.getInt(1);
                }
                else
                {
                    // 場合によっては「レコードが選択されませんでした。」
                    // となり、countの結果セットが空のことがある
                    return 0;
                }
            }
            else
            {
                // for Simulation mode
                System.out.println(sqlstring);
                return 0;
            }
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            ReadWriteException ex = new ReadWriteException(e);
            throw ex;
        }
        finally
        {
            HandlerUtil.closeStatement(stmt);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void create(Entity tgt)
            throws DataExistsException,
                ReadWriteException
    {
        if (tgt == null || tgt.getValueMap().size() == 0)
        {
            return;
        }

        ValidateError[] errors = tgt.validate();
        if (errors.length > 0)
        {
            // validate error!
//            ValidateException ex = new ValidateException();
//            ex.addValidateErrors(errors);
//            throw ex;
        }

        Statement stmt = null;
        try
        {
            // check duplicate
            /*
             SQLSearchKey key = new DefaultSQLSearchKey(tgt.getStoreMetaData().getName()) ;
             key.setKey(tgt) ;
             if (count(key) > 0)
             {
             throw new DataExistsException() ;
             }
             */
            // set autoupdate key
            FieldMetaData[] fmetaArr = getStoreMetaData().getFieldMetaDatas();
            for (int i = 0; i < fmetaArr.length; i++)
            {
                FieldMetaData fmeta = fmetaArr[i];
                if (fmeta.isAutoUpdate())
                {
                    FieldName field = new FieldName(getStoreName(), fmeta.getName());
                    tgt.setValue(field, new SysDate());
                }
            }

            stmt = HandlerUtil.createStatement(getConnection(), false, true);

            SQLGenerator sqlgen = getSQLGenerator();
            String sqlstring = sqlgen.getINSERTSQL(tgt);
            if (!p_simMode)
            {
                DEBUG.MSG(DEBUG.HANDLER, sqlstring);
                stmt.execute(sqlstring);
            }
            else
            {
                // for Simulation mode
                System.out.println(sqlstring);
            }
        }
        catch (SQLException e)
        {
            SQLErrors sqle = AbstractSQLErrors.getInstance(getStoreMetaData().getStoreClass());
            int errcode = sqle.parseErrorCode(e);
            if (errcode == SQLErrors.ERR_DUPLICATED)
            {
                // 6006007=既に同一データが存在するため、登録できません。
                String msgno = "6006007";
                RmiMsgLogClient.write(msgno, getClass().getName());
                throw new DataExistsException(msgno);
            }
            else
            {
                RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
                ReadWriteException ex = new ReadWriteException(e);
                throw ex;
            }
        }
        finally
        {
            HandlerUtil.closeStatement(stmt);
        }
    }

    /**
     * {@inheritDoc}
     */
    public int modify(AlterKey key)
            throws NotFoundException,
                ReadWriteException
    {
        SQLAlterKey updateKey = null;
        if (key instanceof SQLAlterKey)
        {
            updateKey = (SQLAlterKey)key;
        }
        else
        {
            throw new InvalidSubClassException(SQLAlterKey.class, key.getClass());
        }

        ValidateError[] errors = updateKey.validate();
        if (errors.length > 0)
        {
            // validate error!
//            ValidateException ex = new ValidateException();
//            ex.addValidateErrors(errors);
//            throw ex;
        }

        // DBHandlerの扱うテーブルとAlterKeyのテーブルが異なっている場合
        // RuntimeExceptionをスローします(これは、コーディングエラーです)
        String keyStoreName = updateKey.getStoreName();
        String handlerStoreName = (null == getStoreMetaData()) ? null
                                                              : getStoreMetaData().getName();
        if (!keyStoreName.equals(handlerStoreName))
        {
            StringBuffer message = new StringBuffer("Store Meta Data is crossed with Handler(");
            message.append(handlerStoreName);
            message.append(") and AlterKey(");
            message.append(keyStoreName);
            message.append(")");
            throw new RuntimeException(String.valueOf(message));
        }

        // set autoupdate key
        FieldMetaData[] fmetaArr = getStoreMetaData().getFieldMetaDatas();
        for (int i = 0; i < fmetaArr.length; i++)
        {
            FieldMetaData fmeta = fmetaArr[i];
            if (fmeta.isAutoUpdate())
            {
                FieldName field = new FieldName(getStoreName(), fmeta.getName());
                updateKey.setAdhocUpdateValue(field, new SysDate());
            }
        }

        Statement stmt = null;
        try
        {
            stmt = HandlerUtil.createStatement(getConnection(), false, true);

            SQLGenerator sqlgen = getSQLGenerator();
            String sqlstring = sqlgen.getUPDATESQL(updateKey);
            DEBUG.MSG(DEBUG.HANDLER, sqlstring);
            int numRec = stmt.executeUpdate(sqlstring);
            if (numRec == 0)
            {
                /* remove at 2007-02-28
                 Object[] tObj = {
                 getStoreName()
                 };
                 // 6006005 = 更新対象データがありません。テーブル名:{0}
                 RmiMsgLogClient.write(6006005, LogMessage.F_ERROR, getClass().getName(), tObj);
                 */
                throw (new NotFoundException());
            }
            return numRec;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            ReadWriteException ex = new ReadWriteException(e);
            throw ex;
        }
        finally
        {
            HandlerUtil.closeStatement(stmt);
        }
    }

    /**
     * {@inheritDoc}
     */
    public int drop(Entity tgt)
            throws NotFoundException,
                ReadWriteException
    {
        Statement stmt = null;
        try
        {
            stmt = HandlerUtil.createStatement(getConnection(), false, true);

            SQLGenerator sqlgen = getSQLGenerator();
            String sqlstring = sqlgen.getDELETESQL(tgt);

            if (!p_simMode)
            {
                DEBUG.MSG(DEBUG.HANDLER, sqlstring);
                int numRec = stmt.executeUpdate(sqlstring);
                if (numRec == 0)
                {
                    /*
                     Object[] tObj = {
                     getStoreName()
                     } ;
                     // 6006005 = 更新対象データがありません。テーブル名:{0}
                     RmiMsgLogClient.write(6006005, LogMessage.F_ERROR, this.getClass().getName(),
                     tObj) ;
                     */
                    throw (new NotFoundException());
                }
                return numRec;
            }
            else
            {
                System.out.println(sqlstring);
            }
            return 0;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            ReadWriteException ex = new ReadWriteException(e);
            throw ex;
        }
        finally
        {
            HandlerUtil.closeStatement(stmt);
        }
    }

    /**
     * {@inheritDoc}
     */
    public int drop(SearchKey key)
            throws ReadWriteException,
                NotFoundException
    {
        SQLSearchKey findKey = null;
        if (key instanceof SQLSearchKey)
        {
            findKey = (SQLSearchKey)key;
        }
        else
        {
            throw new InvalidSubClassException(SQLSearchKey.class, key.getClass());
        }

        Statement stmt = null;
        try
        {
            stmt = HandlerUtil.createStatement(getConnection(), false, true);

            SQLGenerator sqlgen = getSQLGenerator();
            String sqlstring = sqlgen.getDELETESQL(findKey);

            if (!p_simMode)
            {
                DEBUG.MSG(DEBUG.HANDLER, sqlstring);
                int numRec = stmt.executeUpdate(sqlstring);
                if (numRec == 0)
                {
                    /*
                     Object[] tObj = {
                     getStoreName()
                     } ;
                     // 6006005 = 更新対象データがありません。テーブル名:{0}
                     RmiMsgLogClient.write(6006005, LogMessage.F_ERROR, this.getClass().getName(),
                     tObj) ;
                     */
                    throw (new NotFoundException());
                }
                return numRec;
            }
            else
            {
                System.out.println(sqlstring);
            }
            return 0;
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            ReadWriteException ex = new ReadWriteException(e);
            throw ex;
        }
        finally
        {
            HandlerUtil.closeStatement(stmt);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean lock(SearchKey key, int waitSec)
            throws ReadWriteException,
                LockTimeOutException
    {
        Entity[] ents = findForUpdate(key, LIMIT_UNLIMTED, waitSec, false);
        // entsがnullのときは、SQL文の結果がfalse.
        return (ents != null);
    }

    /**
     * {@inheritDoc}
     */
    public boolean lock(SearchKey key)
            throws ReadWriteException,
                LockTimeOutException
    {
        return lock(key, WAIT_SEC_DEFAULT);
    }

    /**
     * {@inheritDoc}
     */
    public void lock()
            throws ReadWriteException,
                LockTimeOutException
    {
        lock(WAIT_SEC_DEFAULT);
    }

    /**
     * {@inheritDoc}
     */
    public void lock(int waitSec)
            throws ReadWriteException,
                LockTimeOutException
    {
        Statement stmt = null;
        try
        {
            stmt = HandlerUtil.createStatement(getConnection(), false, true);

            SQLGenerator sqlgen = getSQLGenerator();
            String sqlstring = sqlgen.getLOCKSQL(getStoreName(), waitSec);
            DEBUG.MSG(DEBUG.HANDLER, sqlstring);

            stmt.execute(sqlstring);

        }
        catch (SQLException e)
        {
            HandlerUtil.handleLockError(e, getSQLErrors(), getClass());
        }
        finally
        {
            HandlerUtil.closeStatement(stmt);
        }
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void setConnection(Connection conn)
    {
        p_connetction = conn;
    }

    /**
     * コネクションを取得します。(保存されているものが返されます。
     * 新しいセッションではありません)
     * @see jp.co.daifuku.wms.handler.db.DatabaseHandler#getConnection()
     */
    public Connection getConnection()
    {
        return p_connetction;
    }

    /**
     * {@inheritDoc}
     */
    public String getStoreName()
    {
        return p_storeName;
    }

    /**
     * 対象のテーブル名をセットします。<br>
     * 同時に、セットされたテーブルのメタデータを取得し、保持します。
     * @param tableName テーブル名
     */
    public void setStoreName(String tableName)
    {
        StoreMetaData smeta = HandlerUtil.loadStoreMetaData(tableName);
        setStoreMetaData(smeta);
    }

    /**
     * storeMetaDataをセットします。
     * @param storeMetaData セットするstoreMetaData
     */
    public void setStoreMetaData(StoreMetaData storeMetaData)
    {
        p_storeMetaData = storeMetaData;
        p_SQLGenerator = HandlerUtil.loadSQLGenerator(p_storeMetaData);
        p_SQLErrors = HandlerUtil.loadSQLErrors(p_storeMetaData);

        p_storeName = p_storeMetaData.getName();
    }

    /**
     * {@inheritDoc}
     */
    public StoreMetaData getStoreMetaData()
    {
        return p_storeMetaData;
    }

    /**
     * {@inheritDoc}
     */
    public SQLGenerator getSQLGenerator()
    {
        return p_SQLGenerator;
    }

    /**
     * {@inheritDoc}
     */
    public SQLErrors getSQLErrors()
    {
        return p_SQLErrors;
    }

    /**
     * {@inheritDoc}
     */
    public void setSimulationMode(boolean modeOn)
    {
        p_simMode = modeOn;
    }

    /**
     * 対象テーブルの統計情報の取得を行います。
     * このメソッド内にて、トランザクションの確定を行っています。
     * このメソッドを呼出す前に、必ずコミットを行って下さい。
     * 
     * @throws ReadWriteException
     */
    public void getStatics()
            throws ReadWriteException
    {
        try
        {
            // DBハンドラ
            StoredDBHandler sth = new StoredDBHandler(getConnection());

            //テーブル名の取得を行います。
            String tablename = getStoreName();
            if (tablename == null)
            {
                return;
            }
            // 2015/02/16 Y.Okamura start 採取した統計情報を即座に反映させるよう変更
            // (9iはデフォルトfalseで問題ないが10g、11gで問題があるので対応）
            String sql =
                    "{call dbms_stats.gather_table_stats(ownname => '" + WmsParam.WMS_DB_USER +"', tabname => '" + tablename
                            + "', estimate_percent => 100, cascade => TRUE, no_invalidate => false)}";
            // 2015/02/16 Y.Okamura end
            sth.executeProcedure(sql, null);
            getConnection().commit();
            
            Object[] tObj = {
                getStoreName()
            };
            // 6001030 = 統計情報の取得を行いました。テーブル名:{0}
            RmiMsgLogClient.write(6001030, LogMessage.F_INFO, getClass().getName(), tObj);
        }
        catch (ReadWriteException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            throw e;
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            throw new ReadWriteException(e);
        }
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
     * ロック付きの検索です。結果セットが必要でない場合のための
     * パラメータがあります。
     * 
     * @param key 検索キー
     * @param limit 検索上限数
     * @param waitSec ロック待ち時間(秒)
     * @param resultRequire 結果が必要なときtrue
     * @return 検索結果のEntity配列
     * 検索結果不要の場合は、SQL文の実行結果が0件のとき nullが返されます。
     * @throws ReadWriteException 読み込みに失敗したときスローされます。
     * @throws LockTimeOutException 
     */
    protected Entity[] findForUpdate(SearchKey key, int limit, int waitSec, boolean resultRequire)
            throws ReadWriteException,
                LockTimeOutException
    {
        SQLSearchKey findKey = null;
        if (key instanceof SQLSearchKey)
        {
            findKey = (SQLSearchKey)key;
        }
        else
        {
            throw new InvalidSubClassException(SQLSearchKey.class, key.getClass());
        }

        Statement stmt = null;
        Entity[] entities = null;

        try
        {
            stmt = HandlerUtil.createStatement(getConnection(), true, true);

            SQLGenerator sqlgen = getSQLGenerator();
            String sqlstring = sqlgen.getFINDFORUPDATEWAITSQL(findKey, waitSec);

            if (!p_simMode)
            {
                DEBUG.MSG(DEBUG.HANDLER, sqlstring);

                ResultSet rset = stmt.executeQuery(sqlstring);
                if (resultRequire)
                {
                    // 結果取得あり                    
                    entities = convertEntities(rset, limit, findKey);
                }
                else
                {
                    boolean exResult = rset.next();
                    // 結果がfalseのときは1件も読み出せなかったのでnullを返す
                    entities = (exResult) ? new Entity[0]
                                         : null;
                }
            }
            else
            {
                System.out.println(sqlstring);
                entities = (AbstractEntity[])Array.newInstance(createEntity().getClass(), 0);
            }
        }
        catch (SQLException e)
        {
            HandlerUtil.handleLockError(e, getSQLErrors(), getClass());
        }
        finally
        {
            HandlerUtil.closeStatement(stmt);
        }
        return entities;
    }

    /**
     * 結果セットをマッピングします。
     * 
     * @param rset <CODE>ResultSet</CODE> 接続結果
     * @param max 取得最大件数
     * @param key 検索に使用したキー 
     * @throws ReadWriteException データベース接続で発生した例外をそのまま通知します。
     * @return 結果のエンティティ
     */
    protected Entity[] convertEntities(ResultSet rset, int max, SQLSearchKey key)
            throws ReadWriteException
    {
        if (max > MAX_RESULT)
        {
            // 6036997 = 取得対象件数が多すぎます。
            RmiMsgLogClient.write(6036997, getClass().getName());
            throw new ReadWriteException();
        }

        Vector entityList = new Vector();
        AbstractEntity[] entityArr = null;

        // 取得件数指定
        int gettingRec = (max > 0) ? max
                                  : 0;
        try
        {
            // 初回のEntityを生成 (検索結果が0件の場合も必要)
            AbstractEntity tmpEntity = createEntity();

            while (rset.next())
            {
                HandlerUtil.resultToEntity(rset, tmpEntity, key);

                entityList.add(tmpEntity);
                // 取得件数指定があれば、カウントダウン後 break
                if (--gettingRec == 0)
                {
                    break;
                }

                // 次のEntityを生成
                tmpEntity = createEntity();
            }

            // テーブル毎のEntity[]へキャストできるように配列を作成
            entityArr = (AbstractEntity[])Array.newInstance(tmpEntity.getClass(), entityList.size());
            entityList.copyInto(entityArr);
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            ReadWriteException ex = new ReadWriteException(e);
            throw ex;
        }
        return entityArr;
    }

    /**
     * このハンドラが操作する対象テーブルのためのエンティティクラスを
     * 生成して返します。
     * 
     * @return テーブルごとのエンティティ
     */
    protected abstract AbstractEntity createEntity();

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
        return "$Id: AbstractDBHandler.java 8084 2015-02-16 08:02:36Z okamura $";
    }
}

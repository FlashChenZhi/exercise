// $Id: HandlerUtil.java 6514 2009-12-17 02:01:52Z shibamoto $
package jp.co.daifuku.wms.handler.util;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.handler.Conditions;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.AbstractSQLErrors;
import jp.co.daifuku.wms.handler.db.AbstractSQLGenerator;
import jp.co.daifuku.wms.handler.db.SQLErrors;
import jp.co.daifuku.wms.handler.db.SQLGenerator;
import jp.co.daifuku.wms.handler.db.SQLSearchKey;
import jp.co.daifuku.wms.handler.field.FieldDefine;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreDefine;
import jp.co.daifuku.wms.handler.field.StoreDefineHandler;
import jp.co.daifuku.wms.handler.field.StoreMetaData;

/**
 * Handler利用に際して便利に使用できるユーティリティメソッドを
 * 持つクラスです。
 *
 * @version $Revision: 6514 $, $Date: 2009-12-17 11:01:52 +0900 (木, 17 12 2009) $
 * @author  ss
 * @author  Last commit: $Author: shibamoto $
 */

public final class HandlerUtil
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
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * デフォルトコンストラクタ
     */
    private HandlerUtil()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * プリミティブをObjectに変換します。
     * @param value 変換元のプリミティブ値
     * @return 変換されたObject
     */
    public static Object toObject(int value)
    {
        return new BigDecimal(value);
    }

    /**
     * プリミティブをObjectに変換します。
     * @param value 変換元のプリミティブ値
     * @return 変換されたObject
     */
    public static Object toObject(double value)
    {
        return new BigDecimal(String.valueOf(value));
    }

    /**
     * プリミティブをObjectに変換します。
     * @param value 変換元のプリミティブ値
     * @return 変換されたObject
     */
    public static Object toObject(long value)
    {
        return new BigDecimal(value);
    }

    //------------------------------------------------------------
    // utility methods for database access
    //------------------------------------------------------------

    /**
     * ResultSetの現在の行からカラムデータを取り出し、Entityに
     * セットします。
     * 
     * @param rset ResultSet
     * @param ent 対象のEntity
     * @throws SQLException データベースエラー
     */
    public static void resultToEntity(ResultSet rset, Entity ent)
            throws SQLException
    {
        resultToEntity(rset, ent, (Set)null);
    }

    /**
     * 取得条件と結果セットからエンティティに値をマップします。
     * 
     * @param rset 結果セット
     * @param ent 取得値をセットするエンティティ
     * @param key 取得条件がセットされている検索キー
     * @throws SQLException データベースエラー
     */
    public static void resultToEntity(ResultSet rset, Entity ent, SQLSearchKey key)
            throws SQLException
    {
        ResultSetMetaData rMeta = rset.getMetaData();

        // 個別指定カラムの取得
        Set gotIndexes = new HashSet();
        if (hasCollect(key))
        {
            List collList = key.getCollectConditionList();
            Conditions[] conds = (Conditions[])collList.toArray(new Conditions[collList.size()]);
            for (int fidx = 0; fidx < conds.length; fidx++)
            {
                Conditions cond = conds[fidx];

                FieldName saveField = (null != cond.getSaveFieldName()) ? cond.getSaveFieldName()
                                                                       : cond.getFieldName();

                String colName = fixColumnName(saveField);
                if (null == colName)
                {
                    // not column name
                    continue;
                }
                int cidx = rset.findColumn(colName);

                // 日付時刻のクラスはgetTimestamp()でないとミリ秒を取り漏らすことがある
                // それぞれ個別のメソッドを使用する。
                Object value = getResultValue(rset, rMeta, cidx);
                ent.setValue(saveField, value, false);

                gotIndexes.add(Integer.valueOf(cidx));
            }
        }
        // 全カラム指定の取得
        resultToEntity(rset, ent, gotIndexes);
    }

    /**
     * 取得条件を持っているキーであるかどうか調べます。
     * 
     * @param key 検索キー
     * @return 取得条件があるとき true.
     */
    private static boolean hasCollect(SQLSearchKey key)
    {
        if (null != key)
        {
            List clist = key.getCollectConditionList();
            return (null != clist) && !clist.isEmpty();
        }
        return false;
    }

    /**
     * 結果セットから値を取得します。
     * 
     * @param rset 結果セット
     * @param rMeta 結果セットのメタ情報
     * @param i 取得するカラム位置
     * @return 取得値
     * @throws SQLException 値の取得に失敗
     */
    static Object getResultValue(ResultSet rset, ResultSetMetaData rMeta, int i)
            throws SQLException
    {
        Object value;
        switch (rMeta.getColumnType(i))
        {
            case Types.DATE:
                value = timestampToDate(rset.getTimestamp(i));
                break;
            case Types.TIMESTAMP:
                value = rset.getTimestamp(i);
                break;
            case Types.NUMERIC:
                int scale = rMeta.getScale(i);
                if (scale < 0)
                {
                    scale = 0;
                }
                BigDecimal bd = rset.getBigDecimal(i);
                value = (bd != null) ? bd.setScale(scale, BigDecimal.ROUND_HALF_UP)
                                    : bd;
                break;
            default:
                value = rset.getObject(i);
        }
        return value;
    }

    /**
     * 定義内容に応じてResultSetから取得するカラム名を決定します。<br>
     * 個別のカラムでないときは null が返されます。
     * 
     * @param fieldName 取得するフィールド
     * @return ResultSetから取得する名称
     */
    private static String fixColumnName(FieldName fieldName)
    {
        String alias = fieldName.getAlias();
        if (FieldName.ALL_FIELDS.equals(alias))
        {
            return null;
        }
        return alias;
    }

    /**
     * テーブルのメタデータを取得します。
     * 
     * @param tableName
     * @return ロードしたメタデータ
     */
    public static StoreMetaData loadStoreMetaData(String tableName)
    {
        File storeDef = StoreDefine.METADATA_FILE;
        File fieldDef = FieldDefine.METADATA_FILE;

        StoreMetaData metaData = StoreDefineHandler.createStoreMetaData(tableName, storeDef, fieldDef);

        if (metaData == null)
        {
            String msg = "No store define found for " + tableName;
            throw new RuntimeException(msg);
        }

        if (!metaData.getType().equals(StoreMetaData.STORE_TYPE_DB))
        {
            String msg = "This Handler does not support type:" + metaData.getType();
            throw new RuntimeException(msg);
        }

        return metaData;
    }

    /**
     * メタデータを元に、SQLジェネレータをロードします。
     * 
     * @param meta 該当テーブルメタデータ
     * @return SQLジェネレータ
     */
    public static SQLGenerator loadSQLGenerator(StoreMetaData meta)
    {
        String dbtype = (meta != null) ? meta.getStoreClass()
                                      : AbstractSQLGenerator.DBMS_TYPE_ORACLE;
        return AbstractSQLGenerator.getInstance(dbtype, meta);
    }

    /**
     * メタデータを元に、SQLエラークラスをロードします。
     * 
     * @param meta 該当テーブルメタデータ
     * @return SQLエラークラス
     */
    public static SQLErrors loadSQLErrors(StoreMetaData meta)
    {
        String dbtype = (meta != null) ? meta.getStoreClass()
                                      : AbstractSQLErrors.DBMS_TYPE_ORACLE;
        return AbstractSQLErrors.getInstance(dbtype);
    }

    /**
     * ステートメントを作成します。
     * 
     * @param conn データベースコネクション
     * @param forUpdate 更新用のとき true.
     * @param forwardOnly TODO
     * @return Statement
     * @throws SQLException
     */
    public static Statement createStatement(Connection conn, boolean forUpdate, boolean forwardOnly)
            throws SQLException
    {
        int scroll = (forwardOnly) ? ResultSet.TYPE_FORWARD_ONLY
                                  : ResultSet.TYPE_SCROLL_INSENSITIVE;
        int readwrite = (forUpdate) ? ResultSet.CONCUR_UPDATABLE
                                   : ResultSet.CONCUR_READ_ONLY;
        // int life = ResultSet.CLOSE_CURSORS_AT_COMMIT ;

        Statement stmt = conn.createStatement(scroll, readwrite);
        stmt.setFetchSize(10);
        stmt.setEscapeProcessing(true);
        return stmt;
    }

    /**
     * TimestampをDateに変換します。
     * 
     * @param tsmp
     * @return 変換されたDate
     */
    public static Date timestampToDate(Timestamp tsmp)
    {
        if (tsmp == null)
        {
            return null;
        }
        long dtsec = tsmp.getTime();
        return new Date(dtsec);
    }

    /**
     * ステートメントおよび関連するResultSetをクローズします。
     * 
     * @param stmt
     */
    public static void closeStatement(Statement stmt)
    {
        try
        {
            if (stmt != null)
            {
                closeResultSet(stmt.getResultSet());
                stmt.close();
            }
        }
        catch (SQLException e)
        {
            // このExceptionは無視します
        }
    }

    /**
     * ResultSetをクローズします。
     * 
     * @param result
     */
    public static void closeResultSet(ResultSet result)
    {
        try
        {
            if (result != null)
            {
                result.close();
            }
        }
        catch (SQLException e)
        {
            // このExceptionは無視します
        }
    }

    /**
     * 例外を判定します。
     * 
     * @param e
     * @param sqlerr SQLのエラー詳細
     * @param caller 呼び出しもとクラス 
     * @throws ReadWriteException
     * @throws LockTimeOutException 
     */
    public static void handleLockError(SQLException e, SQLErrors sqlerr, Class caller)
            throws ReadWriteException,
                LockTimeOutException
    {
        int err = sqlerr.parseErrorCode(e);
        switch (err)
        {
            case SQLErrors.ERR_LOCK_FAILED:
            case SQLErrors.ERR_LOCK_TIMEOUT:
                throw new LockTimeOutException();

            case SQLErrors.ERR_NORMAL:
                return;

            default:
                e.printStackTrace();
                RmiMsgLogClient.writeSQLTrace(e, caller.getName());
                ReadWriteException ex = new ReadWriteException(e);
                throw ex;
        }
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
     * ResultSetの現在の行からカラムデータを取り出し、Entityにセットします。<br>
     * すでに取得済みのものがあれば、取得済み一覧セットを指定してください。
     * 取得済みがない場合はnullをセットできます。
     * 
     * @param rset ResultSet
     * @param ent 対象のEntity
     * @param gots 取得済みカラムインデックスの一覧セット (1スタート)<br>
     * 取得済みがない場合はnullをセット
     * @throws SQLException データベースエラー
     */
    static void resultToEntity(ResultSet rset, Entity ent, Set gots)
            throws SQLException
    {
        ResultSetMetaData rMeta = rset.getMetaData();
        for (int i = 1; i <= rMeta.getColumnCount(); i++)
        {
            // 取得済みリストが指定されていれば取得済みかどうかチェック
            if (null != gots)
            {
                if (gots.contains(Integer.valueOf(i)))
                {
                    continue;
                }
            }
            // 日付時刻のクラスはgetTimestamp()でないとミリ秒を取り漏らすことがある
            // それぞれ個別のメソッドを使用する。
            Object value = getResultValue(rset, rMeta, i);
            String table = rMeta.getTableName(i);
            if (table.length() == 0)
            {
                table = ent.getStoreName();
            }
            String colname = rMeta.getColumnName(i);
            FieldName field = new FieldName(table, colname);

            ent.setValue(field, value, false);
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
        return "$Id: HandlerUtil.java 6514 2009-12-17 02:01:52Z shibamoto $";
    }
}

// $Id: AbstractHistoryHandler.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.analysis.dbhandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.analysis.entity.HistoryEntity;
import jp.co.daifuku.wms.analysis.operator.HistorySearchParamater;
import jp.co.daifuku.wms.analysis.operator.InventoryHistSearchParameter;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.field.FieldName;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 履歴情報ハンドラの抽象クラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/06/22</td><td nowrap>清水 正人</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  清水 正人
 * @author  Last commit: $Author: arai $
 */


public abstract class AbstractHistoryHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * LISTBOX 検索結果上限数
     */
    public static final int MAXDISP = WmsParam.MAX_NUMBER_OF_DISP_LISTBOX;

    /**
     * バッチ件数
     */
    private static final int BATCH_COUNT = 100;

    /**
     * バッチ処理用ステートメント数
     */
    private static final int STATEMENT_COUNT = 12;

    /**
     * カラム名定義 (WORK_DATE)
     */
    private final FieldName _work_date = new FieldName("", "SUMMARY_DATE");

    // 場所コード(logExceptionで使用します。)
    /**
     * writeメソッド
     */
    protected static final int PLACE_WRITE = 0;

    /**
     * deleteメソッド
     */
    protected static final int PLACE_DELETE = 1;

    /**
     * updateメソッド
     */
    protected static final int PLACE_UPDATE = 2;

    /**
     * searchメソッド
     */
    protected static final int PLACE_SEARCH = 3;

    /**
     * convertResultSetメソッド
     */
    protected static final int PLACE_CONVERT = 4;

    /**
     * cleanUpメソッド
     */
    protected static final int PLACE_CLEANUP = 5;

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
    /**
     * データベースコネクションインスタンス<BR>
     */
    private Connection _conn;

    /**
     * バッチ処理用ステートメント
     */
    private PreparedStatement[] _preStatements = new PreparedStatement[STATEMENT_COUNT];

    /**
     * 登録バッチステートメント数
     */
    private int[] _bachCount = new int[STATEMENT_COUNT];

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ<BR>
     * データベースコネクションオブジェクトを指定してインスタンス化します。<BR>
     * @param conn データベースコネクションオブジェクト<BR>
     */
    public AbstractHistoryHandler(Connection conn)
    {
        _conn = conn;

        // バッチ処理用ステートメント
        for (int i = 0; i < STATEMENT_COUNT; i++)
        {
            _preStatements[i] = null;
            _bachCount[i] = 0;
        }
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 指定された作業日より後に生成された履歴情報を削除します。<BR>
     * 履歴情報が存在しなくても、エラーとはしません。<BR>
     * @param date 作業日(YYYYMMDD)<BR>
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
     */
    public void cleanUp(String date)
            throws ReadWriteException
    {
        // パラメータで指定された作業日より削除条件を編集します。
        StringBuffer strWhere = new StringBuffer(" WHERE ");
        // 集計日
        strWhere.append(_work_date.getName());
        strWhere.append(">");
        strWhere.append(DBFormat.format(date));

        // パラメータで指定された情報から、該当する履歴データを削除するSQL文を作成します。
        Statement statement = null;
        try
        {
            statement = _conn.createStatement();
            StringBuffer strSQL = new StringBuffer("DELETE FROM ");
            strSQL.append(getTableName(date));
            strSQL.append(String.valueOf(strWhere));
            DEBUG.MSG(DEBUG.HANDLER, String.valueOf(strSQL));
            statement.executeUpdate(String.valueOf(strSQL));
        }
        catch (SQLException e)
        {
            // Exceptionをロギングします。
            logException(PLACE_CLEANUP, e);
        }
        finally
        {
            // ステートメントの後始末をします。
            try
            {
                close(null, statement);
            }
            catch (SQLException e)
            {
                // Exceptionをロギングします。
                logException(PLACE_CLEANUP, e);
            }
        }
    }

    /**
     * パラメータで指定された履歴情報を然るべきテーブルに格納します。<BR>
     * @param entity 履歴情報<BR>
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
     */
    public void write(HistoryEntity entity)
            throws ReadWriteException
    {
        StringBuffer strSQL;
        Statement statement = null;

        try
        {
            // ステートメントを作成します。
            statement = _conn.createStatement();

            // 新規追加します。
            strSQL = new StringBuffer("");
            strSQL.append(createInsertSQL(entity));
            DEBUG.MSG(DEBUG.HANDLER, String.valueOf(strSQL));
            statement.executeUpdate(String.valueOf(strSQL));
        }
        catch (SQLException e)
        {
            // 履歴情報書き込みで一意制約違反を検出した場合はデータの異常が考えられます。
            // このデータを無視し処理を続行します。
            if (e.getErrorCode() == 1)
            {
                // 6006040 = データの不整合が発生しました。{0}
                StringBuffer message = new StringBuffer(getTableName(entity));
                message.append("(");
                message.append(createConditionSQL(entity, true));
                message.append(")");
                RmiMsgLogClient.write(new TraceHandler(6006040, e), getClass().getName());
            }
            else
            {
                // Exceptionをロギングします。
                logException(PLACE_WRITE, e);
            }
        }
        finally
        {
            // ステートメントの後始末をします。
            try
            {
                close(null, statement);
            }
            catch (SQLException e)
            {
                // Exceptionをロギングします。
                logException(PLACE_WRITE, e);
            }
        }
    }

    /**
     * パラメータで指定された履歴情報をデータベース格納バッチ処理を行なうため、
     * バッチ処理用ステートメントに登録します。<BR>
     * @param entity 履歴情報<BR>
     * @throws SQLException データベースエラーが発生したときに通知されます。
     */
    public void addBatch(HistoryEntity entity)
            throws SQLException
    {
        // バッチ処理用ステートメントが未取得であれば、取得します。
        int ix = getBatchIndex(entity);
        if (null == _preStatements[ix])
        {
            Connection conn = getConnection();
            _preStatements[ix] =
                    conn.prepareStatement(getBatchSQL(ix + 1), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            _bachCount[ix] = 0;
        }

        // バッチ処理ステートメントを追加します。
        setValues(_preStatements[ix], entity);
        _preStatements[ix].addBatch();
        _bachCount[ix]++;

        // 登録したバッチステートメント数が規定数に到達したら、バッチ処理を実行します。
        if (BATCH_COUNT == _bachCount[ix])
        {
            try
            {
                _preStatements[ix].executeBatch();
            }
            catch (SQLException e)
            {
                // 履歴情報書き込みで一意制約違反を検出した場合はデータの異常が考えられます。
                // このデータを無視し処理を続行します。
                if (e.getErrorCode() == 1)
                {
                    // 6006040 = データの不整合が発生しました。{0}
                    RmiMsgLogClient.write(new TraceHandler(6006040, e), getClass().getName());
                }
                else
                {
                    throw e;
                }
            }
            _bachCount[ix] = 0;
        }
    }

    /**
     * バッチ終了処理<BR>
     * <code>addBatch</code> により登録されたバッチの終了処理を行います。<BR>
     * @throws SQLException バッチ処理の実行でエラーを検出した場合に通知されます。
     */
    public void finalizeBatch()
            throws SQLException
    {
        for (int i = 0; i < STATEMENT_COUNT; i++)
        {
            // 残っているバッチ処理を実行します。
            if (0 < _bachCount[i])
            {
                try
                {
                    _preStatements[i].executeBatch();
                }
                catch (SQLException e)
                {
                    // 履歴情報書き込みで一意制約違反を検出した場合はデータの異常が考えられます。
                    // このデータを無視し処理を続行します。
                    if (e.getErrorCode() == 1)
                    {
                        // 6006040 = データの不整合が発生しました。{0}
                        RmiMsgLogClient.write(new TraceHandler(6006040, e), getClass().getName());
                    }
                    else
                    {
                        throw e;
                    }
                }
            }

            _preStatements[i] = null;
            _bachCount[i] = 0;
        }
    }

    /**
     * 指定された履歴情報をもとに、該当するデータを削除します。<BR>
     * @param entity 履歴情報<BR>
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
     */
    public void delete(HistoryEntity entity)
            throws ReadWriteException
    {
        // 対象テーブルは、集計日より決定します。
        String tableName = getTableName(entity);

        // パラメータで指定された履歴情報より条件を取得します。
        String strWhere = createConditionSQL(entity, true);

        StringBuffer strSQL;
        Statement statement = null;
        try
        {
            statement = _conn.createStatement();
            strSQL = new StringBuffer("DELETE FROM ");
            strSQL.append(tableName);
            strSQL.append(strWhere);
            DEBUG.MSG(DEBUG.HANDLER, String.valueOf(strSQL));
            statement.executeUpdate(String.valueOf(strSQL));
        }
        catch (SQLException e)
        {
            // Exceptionをロギングします。
            logException(PLACE_DELETE, e);
        }
        finally
        {
            // ステートメントの後始末をします。
            try
            {
                close(null, statement);
            }
            catch (SQLException e)
            {
                // Exceptionをロギングします。
                logException(PLACE_DELETE, e);
            }
        }
    }

    /**
     * 指定された日付をもとに、一定期間より古い履歴情報を削除します。<BR>
     * 旧履歴情報が存在しなくても、エラーとはしません。<BR>
     * @param date 日付(YYYYMMDD)<BR>
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
     */
    public void delete(String date)
            throws ReadWriteException
    {
        // 削除対象履歴テーブル名を作成します。
        DecimalFormat tableFormat = new DecimalFormat(getTableName() + "00");
        String[] strTables = new String[12];
        for (int i = 0; i < 12; i++)
        {
            strTables[i] = tableFormat.format(i + 1);
        }

        // 一定期間前の作業日を求めます。
        String deleteDate = getDeleteDate(date);

        // パラメータで指定された履歴情報より削除条件をを編集します。
        StringBuffer strWhere = new StringBuffer(" WHERE ");
        // 集計日
        strWhere.append(_work_date.getName());
        strWhere.append("<");
        strWhere.append(DBFormat.format(deleteDate));

        // パラメータで指定された情報から、該当する履歴データを削除するSQL文を作成します。
        Statement statement = null;
        try
        {
            statement = _conn.createStatement();
            for (int i = 0; i < 12; i++)
            {
                StringBuffer strSQL = new StringBuffer("DELETE FROM ");
                strSQL.append(strTables[i]);
                strSQL.append(String.valueOf(strWhere));
                DEBUG.MSG(DEBUG.HANDLER, String.valueOf(strSQL));
                statement.executeUpdate(String.valueOf(strSQL));
            }
        }
        catch (SQLException e)
        {
            // Exceptionをロギングします。
            logException(PLACE_DELETE, e);
        }
        finally
        {
            // ステートメントの後始末をします。
            try
            {
                close(null, statement);
            }
            catch (SQLException e)
            {
                // Exceptionをロギングします。
                logException(PLACE_DELETE, e);
            }
        }
    }

    /**
     * 指定された履歴情報をもとに、該当するデータを更新します。<BR>
     * 該当する履歴情報が存在しない場合には、新規に追加されます。<BR>
     * @param entity 履歴情報<BR>
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
     */
    public void update(HistoryEntity entity)
            throws ReadWriteException
    {
        // 更新対象テーブルは、集計日より決定します。
        String tableName = getTableName(entity);

        // パラメータで指定された履歴情報より条件を取得します。
        String strWhere = createConditionSQL(entity, true);

        StringBuffer strSQL;
        Statement statement = null;
        ResultSet resultset = null;

        try
        {
            // ステートメントを作成します。
            statement = _conn.createStatement();

            // 該当データが存在するかチェックします。
            strSQL = new StringBuffer("SELECT COUNT(*) FROM ");
            strSQL.append(tableName);
            strSQL.append(strWhere);
            DEBUG.MSG(DEBUG.HANDLER, String.valueOf(strSQL));
            resultset = statement.executeQuery(String.valueOf(strSQL));
            int count = 0;
            while (resultset.next())
            {
                count = resultset.getInt(1);
            }
            if (0 == count)
            {
                // 該当データが存在しない場合は、新規に登録します。
                write(entity);
            }
            else
            {
                // 該当データが存在した場合は、更新します。
                strSQL = new StringBuffer("UPDATE ");
                strSQL.append(tableName);
                strSQL.append(createSetSQL(entity));
                strSQL.append(strWhere);
                DEBUG.MSG(DEBUG.HANDLER, String.valueOf(strSQL));
                statement.executeUpdate(String.valueOf(strSQL));
            }
        }
        catch (SQLException e)
        {
            // Exceptionをロギングします。
            System.out.println(e.getMessage() + " code = " + e.getErrorCode());
            logException(PLACE_UPDATE, e);
        }
        finally
        {
            // 結果セットとステートメントの後始末をします。
            try
            {
                close(resultset, statement);
            }
            catch (SQLException e)
            {
                // Exceptionをロギングします。
                logException(PLACE_UPDATE, e);
            }
        }

        return;
    }

    /**
     * 指定された検索条件に従って履歴情報テーブルを検索し、履歴情報を取得します。<BR>
     * @param param 履歴情報検索条件<BR>
     * @return 検索履歴情報<BR>
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
     */
    public HistoryEntity[] search(HistorySearchParamater param)
            throws ReadWriteException
    {
        HistoryEntity[] historyArr = new HistoryEntity[0];
        StringBuffer strSQL;
        Statement statement = null;
        ResultSet resultset = null;

        // 検索対象履歴テーブルを取得します。
        String[] strTableName = param.getTargetTables();
        try
        {
            // ステートメントを作成します。
            statement = _conn.createStatement();

            // 検索終了日付を保持します。
            String endDate = param.getEndDate();

            // SQL文を編集します。
            for (int i = 0; i < strTableName.length; i++)
            {
                // 去年のデータと混在しているテーブルを検索する場合は、検索範囲を変更します。
                if (12 < strTableName.length && (strTableName.length - 12) > i)
                {
                    param.setEndDate(getLastDate(param.getStartDate(), strTableName[i]));
                }
                else
                {
                    // 指定された検索終了日付に戻します。
                    param.setEndDate(endDate);
                }
                strSQL = new StringBuffer(param.getSearchSQL(strTableName[i]));
                DEBUG.MSG(DEBUG.HANDLER, String.valueOf(strSQL));
                resultset = statement.executeQuery(String.valueOf(strSQL));
                HistoryEntity[] tmpHistory = convertResultSet(resultset);
                historyArr = historyAppend(historyArr, tmpHistory);
            }
        }
        catch (SQLException e)
        {
            // Exceptionをロギングします。
            logException(PLACE_SEARCH, e);
        }
        finally
        {
            // 結果セットとステートメントの後始末をします。
            try
            {
                close(resultset, statement);
            }
            catch (SQLException e)
            {
                // Exceptionをロギングします。
                logException(PLACE_SEARCH, e);
            }
        }
        return historyArr;
    }

    /**
     * 履歴情報検索（商品一覧）。<BR>
     * 指定された検索条件に従って履歴情報テーブルを検索し、履歴情報を取得します。<BR>
     * @param param 履歴情報検索条件<BR>
     * @return 検索履歴情報<BR>
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
     */
    public ResultSet searchItemList(InventoryHistSearchParameter param)
            throws ReadWriteException
    {
        StringBuffer cntSQL;
        StringBuffer strSQL;
        Statement statement = null;
        ResultSet resultset = null;

        try
        {
            // ステートメントを作成します。
            statement = _conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cntSQL = new StringBuffer(param.getCountSQL());
            DEBUG.MSG(DEBUG.HANDLER, String.valueOf(cntSQL));
            ResultSet countret = statement.executeQuery(String.valueOf(cntSQL));

            int count = 0;
            countret.last();
            count = countret.getRow();
            // 取得件数をパラメータへセットします。
            param.setItemCount(count);
            // 件数がMAXDISP以下の場合にのみ検索を実行します
            if (count <= MAXDISP)
            {
                strSQL = new StringBuffer(param.getSearchSQL());
                DEBUG.MSG(DEBUG.HANDLER, String.valueOf(strSQL));
                resultset = statement.executeQuery(String.valueOf(strSQL));
            }
        }
        catch (SQLException e)
        {
            // Exceptionをロギングします。
            logException(PLACE_SEARCH, e);
        }
        return resultset;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * コネクション取得<BR>
     * データベースコネクションを取得します。<BR>
     * @return データベースコネクション<BR>
     */
    protected Connection getConnection()
    {
        return _conn;
    }

    /**
     * 結果セットとステートメントの後始末をします。<BR>
     * @param resultset 結果セット<BR>
     * @param statement ステートメント<BR>
     * @throws SQLException クローズ時にエラーを検出した場合に通知されます。
     */
    protected void close(ResultSet resultset, Statement statement)
            throws SQLException
    {
        // 結果セットをcloseします。
        if (null != resultset)
        {
            resultset.close();
            //resultset = null;
        }

        // ステートメントをcloseします。
        if (null != statement)
        {
            statement.close();
            //statement = null;
        }
    }

    /**
     * 履歴情報テーブルの(末尾の月を表す数字を除いた)テーブル名を取得します。<BR>
     * @return 履歴情報テーブル名<BR>
     */
    protected abstract String getTableName();

    /**
     * 履歴情報エンティティを生成します。<BR>
     * @return 履歴情報エンティティ<BR>
     */
    protected abstract HistoryEntity createEntity();

    /**
     * データベース格納バッチ処理を行う為のSQL文を取得します。<BR>
     * パラメータで指定された月によって、テーブルを決定します。<BR>
     * @param month バッチ処理を生成する月<BR>
     * @return バッチ処理用SQL文<BR>
     */
    protected abstract String getBatchSQL(int month);

    /**
     * データベース格納バッチ処理のデータ設定を行ないます。<BR>
     * @param statement バッチ処理用ステートメント<BR>
     * @param entity  履歴情報<BR>
     * @throws SQLException データベースとの接続でエラーを検出した場合に通知されます。
     */
    protected abstract void setValues(PreparedStatement statement, HistoryEntity entity)
            throws SQLException;

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 履歴情報の作業日より該当するテーブル名を決定します。<BR>
     * @param entity 履歴情報<BR>
     * @return 履歴情報テーブル名<BR>
     */
    private String getTableName(HistoryEntity entity)
    {
        // 履歴情報より作業日を取得します。
        String workDate = (String)entity.getValue(_work_date);
        // 取得した作業日よりテーブル名を決定します。
        String workMonth = workDate.substring(4, 6);
        StringBuffer tableName = new StringBuffer(getTableName());
        tableName.append(workMonth);
        return String.valueOf(tableName);
    }

    /**
     * パラメータで指定された作業日より、該当する履歴テーブルのテーブル名を決定ます。<BR>
     * @param date 作業日(YYYYMMDD)<BR>
     * @return 履歴テーブル名<BR>
     */
    private String getTableName(String date)
    {
        StringBuffer tableName = new StringBuffer(getTableName());
        tableName.append(date.substring(4, 6));
        return String.valueOf(tableName);
    }

    /**
     * パラメータで指定された履歴情報の作業日より、該当するバッチ処理用のステートメント
     * インデックスを決定します。<BR>
     * @param entity 履歴情報<BR>
     * @return バッチ処理用ステートメントインデックス<BR>
     */
    private int getBatchIndex(HistoryEntity entity)
    {
        // 履歴情報より作業日を取得します。
        String workDate = (String)entity.getValue(_work_date);
        // 取得した作業日よりテーブル名を決定します。
        String workMonth = workDate.substring(4, 6);
        return Integer.parseInt(workMonth) - 1;
    }

    /**
     * パラメータで指定された履歴情報より、履歴テーブルへのINSERT文を作成します。<BR>
     * @param entity 履歴情報<BR>
     * @return  INSERT文<BR>
     */
    private String createInsertSQL(HistoryEntity entity)
    {
        // 作成INSERT文
        StringBuffer strSQL = new StringBuffer("INSERT INTO ");

        // 履歴情報の作業日より、テーブル名を決定してSQL文に設定します。
        strSQL.append(getTableName(entity));
        strSQL.append(" (");

        // パラメータで指定された履歴情報より、履歴テーブルのフィールド名リストを
        // 取得し、履歴データをもとにINSERT文を編集します。
        StringBuffer columnNames = new StringBuffer();
        StringBuffer valueStrings = new StringBuffer();
        FieldName[] fieldLists = entity.getFieldNames();
        for (int i = 0; i < fieldLists.length; i++)
        {
            Object value = entity.getValue(fieldLists[i]);
            if (null != value)
            {
                // カラム名を編集します。
                columnNames.append(fieldLists[i].getName());
                columnNames.append(",");

                // データを編集します。
                valueStrings.append(DBFormat.format(String.valueOf(value)));
                valueStrings.append(",");
                /*----------> この間のコードは、取り敢えず保存しておきます。
                 if (value == "")
                 {
                 // データが設定されていなければ、''を設定します。
                 valueStrings.append("'',") ;
                 }
                 else if (value.toString().startsWith("TO_DATE("))
                 {
                 // "TO_DATE("で始まる項目についてはシングル
                 // クォートは付けません。
                 valueStrings.append(value) ;
                 valueStrings.append(",") ;
                 }
                 else if (value instanceof Date)
                 {
                 // DATE型の項目については、OracleのTO_DATE関数で
                 // 整形します。
                 valueStrings.append(DBFormat.format((Date)value)) ;
                 valueStrings.append(",") ;
                 }
                 else
                 {
                 // 文字列データの場合は、シングルクォートを付けます。
                 valueStrings.append(",") ;
                 valueStrings.append(value.toString()) ;
                 valueStrings.append(",") ;
                 }
                 ---------->*/
            }
        }

        // 最後に付いている","を取り除きます。
        columnNames.deleteCharAt(columnNames.length() - 1);
        valueStrings.deleteCharAt(valueStrings.length() - 1);

        // INSERT文を作成します。
        strSQL.append(String.valueOf(columnNames));
        strSQL.append(") VALUES (");
        strSQL.append(String.valueOf(valueStrings));
        strSQL.append(")");

        return String.valueOf(strSQL);
    }

    /**
     * パラメータで指定された履歴情報より、履歴テーブルの更新に用いるSET文を作成します。<BR>
     * @param entity 履歴情報<BR>
     * @return  SET文<BR>
     */
    private String createSetSQL(HistoryEntity entity)
    {
        // 作成SET文
        StringBuffer strSQL = new StringBuffer(" SET ");

        // 作業日は必ず更新対象とします。
        // 一回の日次処理で複数日の履歴生成を行うことがあるのでコメントアウトした。
        //  strSQL.append(WORK_DATE.getName()) ;
        //  strSQL.append("=") ;
        //  strSQL.append(DBFormat.format(DateOperator.getSysdate())) ;
        //  strSQL.append(",") ;

        // 更新フィールド名リストを取得して、各値を更新するSET文を編集します。
        FieldName[] fieldLists = entity.getUpdateFieldNames();
        boolean[] addType = entity.getAddCondition();
        for (int i = 0; i < fieldLists.length; i++)
        {
            Object value = entity.getValue(fieldLists[i]);
            if (null != value)
            {
                // カラム名を編集します。
                strSQL.append(fieldLists[i].getName());
                strSQL.append("=");
                if (addType[i])
                {
                    strSQL.append(fieldLists[i].getName());
                    strSQL.append("+");
                }
                // 更新データを編集します。
                strSQL.append(DBFormat.format(String.valueOf(value)));
                strSQL.append(",");
            }
        }

        // 最後に付いている","を取り除きます。
        strSQL.deleteCharAt(strSQL.length() - 1);

        return String.valueOf(strSQL);
    }

    /**
     * パラメータで指定された履歴情報より、履歴テーブルを検索するWHERE文を作成します。<BR>
     * @param entity  履歴情報<BR>
     * @param updateKey 更新時のWHERE文を作成する場合は<code>true</code>を指定します。<BR>
     * @return WHERE文<BR>
     */
    private String createConditionSQL(HistoryEntity entity, boolean updateKey)
    {
        // 作成WHERE文
        StringBuffer strSQL = new StringBuffer(" WHERE ");

        // パラメータで指定された履歴情報より、履歴テーブルのキーフィールド名リストを
        // 取得し、履歴データをもとにWHERE文を編集します。
        String strAND = " AND ";
        FieldName[] fieldLists;
        if (updateKey)
        {
            fieldLists = entity.getKeyNames();
        }
        else
        {
            fieldLists = entity.getUpdateFieldNames();
        }
        for (int i = 0; i < fieldLists.length; i++)
        {
            Object value = entity.getValue(fieldLists[i]);
            if (null != value)
            {
                // カラム名を編集します。
                strSQL.append(fieldLists[i].getName());
                if (!value.equals(""))
                {
                    strSQL.append("=");
                    // データを編集します。
                    strSQL.append(DBFormat.format(String.valueOf(value)));
                    strSQL.append(strAND);
                }
                else
                {
                    strSQL.append(" IS NULL");
                    strSQL.append(strAND);
                }
            }
        }

        // 最後に付いている" AND "を取り除きます。
        if (0 < fieldLists.length)
        {
            strSQL.delete(strSQL.length() - strAND.length(), strSQL.length());
        }

        return String.valueOf(strSQL);
    }

    /**
     * 結果セットより、履歴情報Entityを取得し配列にして返します。<BR>
     * @param result 結果セット<BR>
     * @return 履歴データ(配列)BR>
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
     */
    protected HistoryEntity[] convertResultSet(ResultSet result)
            throws ReadWriteException
    {
        // エンティティリスト
        List<HistoryEntity> entityList = Collections.synchronizedList(new ArrayList<HistoryEntity>());
        HistoryEntity[] entityArr = new HistoryEntity[0];
        HistoryEntity tmpEntity = createEntity();
        Timestamp timestamp = null;

        try
        {
            while (result.next())
            {
                // 結果セットより、エンティティを取得します。
                for (int i = 1; i <= result.getMetaData().getColumnCount(); i++)
                {
                    // 結果セットより、フィールド名を取得します。
                    String column = result.getMetaData().getColumnName(i);

                    // 結果セットより、データを取得します。
                    Object value = result.getObject(i);
                    if ((value instanceof java.util.Date) || (value instanceof oracle.sql.DATE))
                    {
                        timestamp = result.getTimestamp(i);
                        value = new java.util.Date(timestamp.getTime());
                    }

                    // エンティティに取り出したデータを格納します。
                    tmpEntity.setValue(column, value);
                }
                // エンティティリストに追加します。
                entityList.add(tmpEntity);

                // 次のエンティティを準備します。
                tmpEntity = createEntity();
            }

            // テーブル毎のHistoryEntity[]へキャストできるようにします。
            entityArr = (HistoryEntity[])java.lang.reflect.Array.newInstance(tmpEntity.getClass(), entityList.size());
            for (int i = 0; i < entityList.size(); i++)
            {
                entityArr[i] = entityList.get(i);
            }
        }
        catch (SQLException e)
        {
            // Exceptionをロギングします。
            logException(PLACE_CONVERT, e);
        }
        return entityArr;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。<BR>
     * @return リビジョン文字列。<BR>
     */
    public static String getVersion()
    {
        return "$Id: AbstractHistoryHandler.java 3208 2009-03-02 05:42:52Z arai $";
    }

    /**
     * システム定義されている一定期間を取得し、パラメータで指定された日付より
     * 一定期間前の日付を求めて通知します。<BR>
     * @param date 日付(YYYYMMDD)<BR>
     * @return 一定期間前の日付(YYYYMMDD)<BR>
     */
    protected String getDeleteDate(String date)
    {
        // 指定された日付を取り出します。
        Date targetDay = WmsFormatter.toDate(date);
        Calendar theCal = Calendar.getInstance();
        theCal.setTime(targetDay);

        // システム定義より一定期間を取得します。
        int keepingPeriod = WmsParam.KEEP_MONTHS;

        // 一定期間前の日付を算出します。
        theCal.add(Calendar.MONTH, -keepingPeriod);

        // 求めた日付より、一定期間前の日付を編集します。
        // ここで編集する日付は1ヶ月単位での削除が目的のため、日を"99"としています。
        StringBuffer returnDate = new StringBuffer(WmsFormatter.toParamYearMonth(theCal.getTime()));
        returnDate.append("99");

        return String.valueOf(returnDate);
    }

    /**
     * 指定された検索開始日とテーブル名よりその月の最終日を算出します。<BR>
     * @param startDate 検索開始日(YYYYMMDD)<BR>
     * @param tableName テーブル名<BR>
     * @return その月の最終日(YYYYMMDD)<BR>
     */
    private String getLastDate(String startDate, String tableName)
    {
        // 検索開始日より、年と月を取得します。
        int year = Integer.parseInt(startDate.substring(0, 4));
        int month = Integer.parseInt(startDate.substring(4, 6));

        // テーブル名より、月を求めます。
        int tableMonth = Integer.parseInt(tableName.substring(tableName.length() - 2));

        // 求めようとしている年月の最終日を算出します。
        if (month < tableMonth)
        {
            year++;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, tableMonth - 1);
        cal.set(Calendar.DATE, 1);
        int date = cal.getActualMaximum(Calendar.DATE);

        DecimalFormat d4 = new DecimalFormat("0000");
        DecimalFormat d2 = new DecimalFormat("00");
        StringBuffer lastDate = new StringBuffer(d4.format(year));
        lastDate.append(d2.format(tableMonth));
        lastDate.append(d2.format(date));
        return String.valueOf(lastDate);
    }

    /**
     * 既に存在する履歴情報配列に、新たに履歴情報を追加します。<BR>
     * @param source 既存履歴情報（配列）<BR>
     * @param data 追加履歴情報（配列）<BR>
     * @return 追加結果履歴情報配列<BR>
     */
    private HistoryEntity[] historyAppend(HistoryEntity[] source, HistoryEntity[] data)
    {
        int sLen = source.length;
        int dLen = data.length;
        HistoryEntity[] newArr = new HistoryEntity[sLen + dLen];
        for (int i = 0; i < sLen; i++)
        {
            newArr[i] = source[i];
        }
        for (int i = 0; i < dLen; i++)
        {
            newArr[i + sLen] = data[i];
        }
        return newArr;
    }

    /**
     * パラメータで指定されたExceptionをログ出力します。<BR>
     * また、このメソッドはログ出力した後、必ず<code>ReadWriteException</code>が
     * <code>throw</code>されます。<BR>
     * @param place 場所コード<BR>
     * @param e Exception<BR>
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
     */
    protected void logException(int place, Exception e)
            throws ReadWriteException
    {
        // 指定されたExceptionがSQLExceptionなら、一意制約違反をチェックします。
        if (e instanceof SQLException)
        {
            // 一意制約違反の場合
            SQLException sqle = (SQLException)e;
            if (sqle.getErrorCode() == 1)
            {
                // 6006007 = 既に同一データが存在するため、登録できません。
                RmiMsgLogClient.write(6006007, getClass().getName());
                throw new ReadWriteException(e);
            }

            // 6006002 = データベースエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006002, e), getClass().getName());
            throw new ReadWriteException(e);
        }

        // ここで、ReadWriteExceptionをエラーメッセージ付きでthrowします。
        if (place == PLACE_CONVERT)
        {
            // 6006039 = {0}の検索に失敗しました。ログを参照して下さい。
            RmiMsgLogClient.write(new TraceHandler(6006039, e), getClass().getName());
            throw new ReadWriteException(e);
        }
        else
        {
            // 6006002 = データベースエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006002, e), getClass().getName());
            throw new ReadWriteException(e);
        }
    }
}

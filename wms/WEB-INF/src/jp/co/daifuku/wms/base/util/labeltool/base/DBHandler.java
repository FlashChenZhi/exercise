// $$Id: DBHandler.java 8075 2014-09-19 07:16:57Z okayama $$
package jp.co.daifuku.wms.base.util.labeltool.base;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

/**
 * DBデータの操作に関する共通クラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/07/21</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 8075 $, $Date: 2014-09-19 16:16:57 +0900 (金, 19 9 2014) $
 * @author  chenjun
 * @author  Last commit: $Author: okayama $
 */
public class DBHandler
{
    /** <code>DB接続</code> */
    Connection conn = null;

    /**
     * このクラスのコンストラクタです。
     *
     * @param con DBコネクション
     */
    public DBHandler(Connection con)
    {
        setConnection(con);
    }

    /**
     * データ問合せを行います。
     *
     * @param sql SQL文
     * @return 結果リスト
     */
    public List<?> executeQuery(String sql)
    {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler rsh = new MapListHandler();
        try
        {
            System.out.println(sql);
            return (List<?>)qr.query(conn, sql, rsh);
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    /**
     * DB更新を行います。
     * @param sql SQL文
     */
    public void executeUpdate(String sql)
    {
        Statement stmt = null;
        try
        {
            stmt = conn.createStatement();
            System.out.println(sql);
            stmt.executeUpdate(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                // 失敗した場合は何もしない
            }
        }
    }

    /**
     * 接続を中止します。
     */
    public void closeConnection()
    {
        DbUtils.closeQuietly(conn);
    }

    /**
     * @return DBコネクション
     */
    public Connection getConnection()
    {
        return conn;
    }

    /**
     * @param con DBコネクション
     */
    public void setConnection(Connection con)
    {
        this.conn = con;
    }
}

package jp.co.daifuku.wms.exercise.util.query;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-2-26
 * Time: 下午3:00
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionFactory
{
    public static Connection getWmsConnection() throws SQLException
    {
        return jp.co.daifuku.bluedog.sql.ConnectionManager.getConnection("wms");
    }
}

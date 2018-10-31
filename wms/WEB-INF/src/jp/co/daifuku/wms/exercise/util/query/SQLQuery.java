package jp.co.daifuku.wms.exercise.util.query;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-2-25
 * Time: 下午2:18
 * To change this template use File | Settings | File Templates.
 */
public class SQLQuery
{
    Map<String, Tuple> map1 = new HashMap<String, Tuple>();
    Map<Integer, Tuple> map2 = new HashMap<Integer, Tuple>();
    private String sql;
    private long firstResult = -1;
    private long maxResults = 0;


    public SQLQuery setString(String key, String value)
    {
        map1.put(key, new Tuple(value, ValueType.STRING));
        return this;
    }

    public SQLQuery setDate(String key, Date value)
    {
        map1.put(key, new Tuple(value, ValueType.DATE));
        return this;
    }

    public SQLQuery setTimestamp(String key, Timestamp value)
    {
        map1.put(key, new Tuple(value, ValueType.TIMESTAMP));
        return this;
    }

    public SQLQuery setBigDecimal(String key, BigDecimal value)
    {
        map1.put(key, new Tuple(value, ValueType.BIGDECIMAL));
        return this;
    }

    public SQLQuery setInt(String key, int value)
    {
        map1.put(key, new Tuple(value, ValueType.INTEGER));
        return this;
    }

    public SQLQuery setLong(String key, long value)
    {
        map1.put(key, new Tuple(value, ValueType.LONG));
        return this;
    }

    public SQLQuery setBoolean(String key, boolean value)
    {
        map1.put(key, new Tuple(value, ValueType.BOOLEAN));
        return this;
    }

    public SQLQuery setStringArray(String key, String[] value)
    {
        map1.put(key, new Tuple(value, ValueType.STRING, true));
        return this;
    }

    public SQLQuery setIntArray(String key, Integer[] value)
    {
        map1.put(key, new Tuple(value, ValueType.INTEGER, true));
        return this;
    }

    public void setSql(String sql)
    {
        this.sql = sql;
    }

    public long count(Connection conn) throws Exception
    {
        long count = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            String finalSql = suroundCount(getFinalSQL());

            ps = setSQLValue(conn, finalSql);

            try
            {
                rs = ps.executeQuery();
//                System.out.println(finalSql);
                while (rs.next())
                {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    for (int i = 1; i <= rsmd.getColumnCount(); i++)
                    {
                        count = ((BigDecimal) rs.getObject(i)).longValue();
                    }
                }
            } finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        } finally
        {
            if (ps != null)
            {
                ps.close();
            }
        }
        return count;
    }

    public List<List<Object>> list(Connection conn) throws Exception
    {
        List<List<Object>> rows = new ArrayList<List<Object>>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            String finalSql;

            if (getFirstResult() == -1 && getMaxResults() != 0)
            {
                setFirstResult(0);
                finalSql = suroundRowNum(getFinalSQL());
            }
            else if (getFirstResult() == -1)
            {
                finalSql = getFinalSQL();
            }
            else
            {
                finalSql = suroundRowNum(getFinalSQL());
            }

            ps = setSQLValue(conn, finalSql);

            try
            {
                rs = ps.executeQuery();
//                System.out.println(finalSql);
                while (rs.next())
                {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    List<Object> cols = new ArrayList<Object>();
                    for (int i = 1; i <= rsmd.getColumnCount(); i++)
                    {
                        if (rs.getObject(i) instanceof java.sql.Date)
                        {
                            cols.add(rs.getTimestamp(i));
                        }
                        else
                        {
                            cols.add(rs.getObject(i));
                        }
                    }
                    rows.add(cols);
                }
            } finally
            {
                if (rs != null)
                {
                    rs.close();
                }
            }
        } finally
        {
            if (ps != null)
            {
                ps.close();
            }
        }
        return rows;
    }

    private PreparedStatement setSQLValue(Connection conn, String finalSql) throws SQLException
    {
        List<Integer> sortList = new ArrayList<Integer>(map2.keySet());
        Collections.sort(sortList);

        PreparedStatement ps = conn.prepareStatement(finalSql);

        for (int i = 0; i < sortList.size(); i++)
        {
            Tuple t = map2.get(sortList.get(i));
            switch (t.getValueType())
            {
                case STRING:
                    ps.setString(i + 1, (String) t.getValue());
                    break;
                case DATE:
                    ps.setTimestamp(i + 1, t.getValue() == null ? null : new Timestamp(((Date) t.getValue()).getTime
                            ()));
                    break;
                case BIGDECIMAL:
                    ps.setBigDecimal(i + 1, (BigDecimal) t.getValue());
                    break;
                case INTEGER:
                    ps.setInt(i + 1, (Integer) t.getValue());
                    break;
                case LONG:
                    ps.setLong(i + 1, (Long) t.getValue());
                    break;
                case BOOLEAN:
                    ps.setBoolean(i + 1, (Boolean) t.getValue());
                    break;
                case TIMESTAMP:
                    ps.setTimestamp(i + 1, (Timestamp) t.getValue());
                    break;
            }
        }
        return ps;
    }

    private String getFinalSQL() throws SQLException
    {
        while (true)
        {
            boolean found = false;
            for (String key : map1.keySet())
            {
                Tuple t = map1.get(key);
                if (t.isArray())
                {
                    Object[] objs = (Object[]) t.getValue();
                    if (objs != null && objs.length != 0)
                    {
                        StringBuffer temp = new StringBuffer();
                        for (int i = 1; i <= objs.length; i++)
                        {
                            temp.append(":");
                            temp.append(key);
                            temp.append(StringUtils.leftPad(String.valueOf(i), 3, '0'));
                            if (i != objs.length)
                            {
                                temp.append(",");
                            }

                            switch (t.getValueType())
                            {
                                case STRING:
                                    this.setString(key + StringUtils.leftPad(String.valueOf(i), 3, '0'),
                                            (String) objs[i - 1]);
                                    break;
                                case INTEGER:
                                    this.setInt(key + StringUtils.leftPad(String.valueOf(i), 3, '0'),
                                            (Integer) objs[i - 1]);
                                    break;
                            }
                        }

                        sql = sql.replace(":" + key, temp.toString());

                        map1.remove(key);
                        found = true;
                        break;
                    }
                }
            }
            if (!found)
            {
                break;
            }
        }

        String finalSql = sql;

        for (String key : map1.keySet())
        {
            int fromIndex = 0;
            while (true)
            {
                int index = sql.indexOf(":" + key, fromIndex);
                if (index == -1) break;
                fromIndex = index + key.length() + 1;

                finalSql = finalSql.replaceFirst(":" + key, "?");

                map2.put(index, map1.get(key));
            }
        }

        return finalSql;
    }

    private String suroundCount(String finalSQL)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT COUNT(*) FROM(");
        sb.append(finalSQL);
        sb.append(")");
        return sb.toString();
    }

    private String suroundRowNum(String finalSQL)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM (SELECT row_.*, ROWNUM rownum_ FROM (");
        sb.append(finalSQL);
        sb.append(") row_ WHERE ROWNUM <= ");
        sb.append(getFirstResult() + getMaxResults());
        sb.append(") WHERE rownum_ > ");
        sb.append(getFirstResult());
        return sb.toString();
    }

    public long getFirstResult()
    {
        return firstResult;
    }

    public void setFirstResult(long firstResult)
    {
        this.firstResult = firstResult;
    }

    public long getMaxResults()
    {
        return maxResults;
    }

    public void setMaxResults(long maxResults)
    {
        this.maxResults = maxResults;
    }


    public int doSqlQuery(Connection conn) throws SQLException
    {
        PreparedStatement ps = null;

        try
        {
            String finalSql = getFinalSQL();
            ps = setSQLValue(conn, finalSql);
            int result = ps.executeUpdate();
//            System.out.println(finalSql);
            return result;
        } finally
        {
            if (ps != null)
            {
                ps.close();
            }
        }
    }
}

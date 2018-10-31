package jp.co.daifuku.wms.exercise.util.pager;

import jp.co.daifuku.wms.exercise.util.query.SQLQuery;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;

/**
 * Author: Zhouyue
 * Date: 2009-6-15
 * Time: 15:23:39
 * Copyright Daifuku Shanghai Ltd.
 */
public class SQLQueryPager<U, V> extends Pager<U, V>
{
    private Logger logger = LogManager.getLogger(SQLQueryPager.class);

    private ISQLQueryPagerImpl<U, V> pagerImpl;

    public SQLQueryPager(ISQLQueryPagerImpl<U, V> provider)
    {
        this.pagerImpl = provider;
    }


    protected long getTotalCount(Connection conn, U params) throws Exception
    {
        return pagerImpl.assemblyQuery(conn,params).count(conn);
    }

    protected List<V> getList(Connection conn, U params) throws Exception
    {
        return getList(conn, -1, 0, params);
    }

    protected List<V> getList(Connection conn, long firstResultPos, long maxResultsCount, U params) throws Exception
    {
        SQLQuery q = pagerImpl.assemblyQuery(conn,params);

        q.setFirstResult(firstResultPos);
        q.setMaxResults(maxResultsCount);

        List list = q.list(conn);
        return pagerImpl.convertQueryResultList(conn,list, firstResultPos);
    }
}
package jp.co.daifuku.wms.exercise.util.pager;


import jp.co.daifuku.wms.exercise.util.query.SQLQuery;

import java.sql.Connection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jack
 * Date: 2009-6-16
 * Time: 12:28:52
 * To change this template use File | Settings | File Templates.
 */
public interface ISQLQueryPagerImpl<U,V>
{
    SQLQuery assemblyQuery(Connection conn, U params) throws Exception;

    List<V> convertQueryResultList(Connection conn, List<List<Object>> list, long firstResultPos) throws Exception;
}

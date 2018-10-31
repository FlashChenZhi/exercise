package jp.co.daifuku.wms.exercise.util.pager;

import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.wms.exercise.util.common.Const;
import jp.co.daifuku.wms.exercise.util.query.ConnectionFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jack
 * Date: 2009-6-15
 * Time: 12:01:02
 * To change this template use File | Settlongs | File Templates.
 */
public abstract class Pager<U, V>
{
    private Logger logger = LogManager.getLogger(Pager.class);

    private GenericPagerReturnObj<V> turn(ActionType actionType, int currentBeginIndex, int countPerPage, U params) throws Exception
    {
        GenericPagerReturnObj<V> response = new GenericPagerReturnObj<V>();
        Connection conn = null;
        try
        {
            conn = ConnectionFactory.getWmsConnection();

            long totalCount = getTotalCount(conn, params);

            if (totalCount == 0)
            {
                response.setTotalCount(0);
                response.setCurrentBeginIndex(-1);
                response.setCountPerPage(countPerPage);
                return response;
            }

            long newBeginIndex = 0;

            switch (actionType)
            {
                case FIRST:
                    newBeginIndex = 0;
                    break;
                case NEXT:
                    newBeginIndex = currentBeginIndex + countPerPage;
                    break;
                case PREV:
                    newBeginIndex = currentBeginIndex - countPerPage;
                    break;
                case LAST:
                    newBeginIndex = totalCount - (totalCount % countPerPage == 0 ? countPerPage : totalCount % countPerPage);
                    break;
                case CURRENT:
                    newBeginIndex = currentBeginIndex + 1 > totalCount ? (totalCount - (totalCount % countPerPage == 0 ? countPerPage : totalCount % countPerPage)) : currentBeginIndex;
                    break;
            }

            List<V> list = getList(conn, newBeginIndex, countPerPage, params);

            response.setTotalCount(totalCount);
            response.setCurrentBeginIndex(newBeginIndex);
            response.setCountPerPage(countPerPage);
            response.setData(list);
            return response;
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage(), ex);

            response.setTotalCount(0);
            response.setCurrentBeginIndex(0);
            response.setCountPerPage(countPerPage);

            if (conn != null)
            {
                conn.rollback();
            }
            throw ex;
        }
        finally
        {
            if (conn != null)
            {
                conn.close();
            }
        }
    }

    public GenericPagerReturnObj<V> all(U params,int maxCount, Message message) throws Exception
    {
        GenericPagerReturnObj<V> response = new GenericPagerReturnObj<V>();
        Connection conn = null;

        try
        {
            conn = ConnectionFactory.getWmsConnection();

            long totalCount = getTotalCount(conn, params);

            if(totalCount == 0)
            {
                message.setMsgResourceKey(jp.co.daifuku.wms.exercise.Message.RESULT_ZERO.getCode());

                response.setData(new ArrayList<V>());
            }
            else if (totalCount > maxCount)
            {
                message.setMsgResourceKey(jp.co.daifuku.wms.exercise.Message.RESULT_TOO_MUCH.getCode() + "\t" + totalCount + "\t" + Const.MAX_PRINT_COUNT);

                response.setData(new ArrayList<V>());
            } else
            {
                List<V> list = getList(conn, params);

                response.setData(list);
            }

            return response;
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage(), ex);

            if (conn != null)
            {
                conn.rollback();
            }
            throw ex;
        }
        finally
        {
            if (conn != null)
            {
                conn.close();
            }
        }
    }

    public long totalCount(U params) throws Exception
    {
        GenericPagerReturnObj<V> response = new GenericPagerReturnObj<V>();
        Connection conn = null;

        try
        {
            conn = ConnectionFactory.getWmsConnection();

            long totalCount = getTotalCount(conn, params);

            return totalCount;
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage(), ex);

            if (conn != null)
            {
                conn.rollback();
            }
            throw ex;
        }
        finally
        {
            if (conn != null)
            {
                conn.close();
            }
        }
    }

    public GenericPagerReturnObj<V> next(int currentBeginIndex, int countPerPage, U params) throws Exception
    {
        return turn(ActionType.NEXT, currentBeginIndex, countPerPage, params);
    }

    public GenericPagerReturnObj<V> previous(int currentBeginIndex, int countPerPage, U params) throws Exception
    {
        return turn(ActionType.PREV, currentBeginIndex, countPerPage, params);
    }

    public GenericPagerReturnObj<V> first(int countPerPage, U params) throws Exception
    {
        return turn(ActionType.FIRST, 0, countPerPage, params);
    }

    public GenericPagerReturnObj<V> last(int countPerPage, U params) throws Exception
    {
        return turn(ActionType.LAST, 0, countPerPage, params);
    }

    public GenericPagerReturnObj<V> current(int currentBeginIndex, int countPerPage, U params) throws Exception
    {
        return turn(ActionType.CURRENT, currentBeginIndex, countPerPage, params);
    }

    public GenericPagerReturnObj<V> go(int pageNo, int countPerPage, U params) throws Exception
    {
        int currentBeginIndex = pageNo <= 0 ? 0 : (pageNo - 1) * countPerPage;
        return turn(ActionType.CURRENT, currentBeginIndex, countPerPage, params);
    }

    protected abstract long getTotalCount(Connection conn, U params) throws Exception;

    protected abstract List<V> getList(Connection conn, long firstResultPos, long maxResultsCount, U params) throws Exception;

    protected abstract List<V> getList(Connection conn, U params) throws Exception;
}
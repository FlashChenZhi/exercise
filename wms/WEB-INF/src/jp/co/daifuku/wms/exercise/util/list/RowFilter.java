package jp.co.daifuku.wms.exercise.util.list;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-2-26
 * Time: 15:35:13
 * To change this template use File | Settings | File Templates.
 */
public interface RowFilter<T>
{
    public boolean filte(T entity);
}

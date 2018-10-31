package jp.co.daifuku.wms.exercise.util.list;

/**
 * Created by IntelliJ IDEA.
 * User: Jack
 * Date: 2009-2-10
 * Time: 2:00:58
 * To change this template use File | Settings | File Templates.
 */
public interface FieldFormatter<T>
{
    String format(T obj) throws Exception;

    T unformat(String value) throws Exception;
}

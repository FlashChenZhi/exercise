package jp.co.daifuku.wms.exercise.util.list;

import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Jack
 * Date: 2009-2-10
 * Time: 2:01:54
 * To change this template use File | Settings | File Templates.
 */
public class NullFieldFormatter implements FieldFormatter
{
    public String format(Object obj)
    {
        if (obj == null)
        {
            return StringUtils.EMPTY;
        }

        return obj.toString();
    }

    public Object unformat(String value)
    {
        return value;
    }
}

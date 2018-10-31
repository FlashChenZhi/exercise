package jp.co.daifuku.wms.exercise.util.list;

import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Gavin
 * Date: 2009-3-6
 * Time: 17:16:36
 * To change this template use File | Settings | File Templates.
 */
public class LongFormatter implements FieldFormatter<Long>
{
    public String format(Long value)
    {
        if (value == null)
        {
            return null;
        }
        return DecimalFormat.getNumberInstance().format(value);
    }

    public Long unformat(String value)
    {
        if (StringUtils.isBlank(value))
        {
            return null;
        }
        return Long.parseLong(value.replaceAll(",", ""));
    }
}

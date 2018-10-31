package jp.co.daifuku.wms.exercise.util.list;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-16
 * Time: 下午3:53
 * To change this template use File | Settings | File Templates.
 */
public class DBDateFormatter implements FieldFormatter<Date>
{
    private Logger logger = LogManager.getLogger(DateFormatter.class);
    private String format;

    public DBDateFormatter()
    {
        format = "yyyyMMdd";
    }

    public DBDateFormatter(String format)
    {
        this.format = format;
    }

    public String format(Date value)
    {
        if (value == null)
        {
            return null;
        }
        return new SimpleDateFormat(format).format(value);
    }

    public Date unformat(String value) throws Exception
    {
        if (StringUtils.isBlank(value))
        {
            return null;
        }
        try
        {
            return new SimpleDateFormat(format).parse(value);
        }
        catch (ParseException ex)
        {
            logger.error(ex.getMessage(), ex);

            throw ex;
        }
    }
}

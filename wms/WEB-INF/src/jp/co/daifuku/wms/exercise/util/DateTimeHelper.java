package jp.co.daifuku.wms.exercise.util;

import jp.co.daifuku.wms.exercise.util.list.DateTimeFormatter;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: lenovo
 * Date: 13-5-16
 * Time: 下午6:28
 * To change this template use File | Settings | File Templates.
 */
public class DateTimeHelper
{
    public static Date setDateTime(String date, String time, int flag) throws Exception
    {
        if (StringUtils.isBlank(date) && StringUtils.isNotBlank(time))
        {
            return null;
        }
        else if (StringUtils.isNotBlank(date) && StringUtils.isBlank(time) && flag == 1)
        {
            return new DateTimeFormatter().unformat(date + " 00:00:00");
        }
        else if (StringUtils.isNotBlank(date) && StringUtils.isBlank(time) && flag == 2)
        {
            return new DateTimeFormatter().unformat(date + " 23:59:59");
        }
        else
        {
            return new DateTimeFormatter().unformat(date + " " + time);
        }
    }
}

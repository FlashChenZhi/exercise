package jp.co.daifuku.wms.exercise.util.list;

import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-2-26
 * Time: 10:07:42
 * To change this template use File | Settings | File Templates.
 */
public class IntegerFormatter implements FieldFormatter<Integer>
{
      public String format(Integer value)
      {
            if (value == null)
            {
                  return null;
            }
            return DecimalFormat.getNumberInstance().format(value);
      }

      public Integer unformat(String value)
      {
            if (StringUtils.isBlank(value))
            {
                  return null;
            }
            return Integer.parseInt(value.replaceAll(",", ""));
      }
}

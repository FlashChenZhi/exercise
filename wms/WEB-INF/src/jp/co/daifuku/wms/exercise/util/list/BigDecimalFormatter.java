package jp.co.daifuku.wms.exercise.util.list;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Gavin
 * Date: 2009-3-6
 * Time: 17:27:04
 * To change this template use File | Settings | File Templates.
 */
public class BigDecimalFormatter implements FieldFormatter<BigDecimal>
{
    public String format(BigDecimal value)
    {
        if (value == null)
        {
            return "0";
        }

        NumberFormat format = DecimalFormat.getNumberInstance();
        format.setMaximumFractionDigits(7);

        return format.format(value);
    }

    public BigDecimal unformat(String value)
    {
        if (StringUtils.isBlank(value))
        {
            return null;
        }
        return new BigDecimal(value.replaceAll(",", ""));
    }
}

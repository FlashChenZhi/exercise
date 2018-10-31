package jp.co.daifuku.wms.exercise.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created with IntelliJ IDEA.
 * User: Zhouyue
 * Date: 13-4-12
 * Time: 下午1:38
 * To change this template use File | Settings | File Templates.
 */
public class ScaleHepler
{
    public static double setDoubleScale(double value, int scale)
    {
        return new BigDecimal(value).setScale(scale, RoundingMode.CEILING).doubleValue();
    }

    public static BigDecimal setBigDecimalScale(BigDecimal value, int scale)
    {
        if(value == null)
        {
            return BigDecimal.ZERO;
        }
        return value.setScale(scale, RoundingMode.CEILING);
    }
}

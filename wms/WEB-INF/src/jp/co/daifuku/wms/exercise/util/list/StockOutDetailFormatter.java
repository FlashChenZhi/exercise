package jp.co.daifuku.wms.exercise.util.list;

import jp.co.daifuku.wms.base.entity.SystemDefine;
import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: lenovo
 * Date: 13-12-2
 * Time: 下午3:42
 * To change this template use File | Settings | File Templates.
 */
public class StockOutDetailFormatter implements FieldFormatter
{
    public String format(Object obj) throws Exception
    {
        if (obj == null)
        {
            return null;
        }
        if (obj.equals(SystemDefine.RETRIEVAL_DETAIL_INVENTORY_CHECK))
        {
            return "盘点";
        }
        if (obj.equals(SystemDefine.RETRIEVAL_DETAIL_UNIT))
        {
            return "整箱";
        }
        if (obj.equals(SystemDefine.RETRIEVAL_DETAIL_PICKING))
        {
            return "拣选";
        }
        if (obj.equals(SystemDefine.RETRIEVAL_DETAIL_ADD_STORING))
        {
            return "加料";
        }
        if (obj.equals(SystemDefine.RETRIEVAL_DETAIL_UNKNOWN))
        {
            return "未知";
        }

        return null;
    }

    public Object unformat(String value) throws Exception
    {
        if (StringUtils.isBlank(value))
        {
            return null;
        }
        if (value.equals("盘点"))
        {
            return SystemDefine.RETRIEVAL_DETAIL_INVENTORY_CHECK;
        }
        if (value.equals("整箱"))
        {
            return SystemDefine.RETRIEVAL_DETAIL_UNIT;
        }
        if (value.equals("拣选"))
        {
            return SystemDefine.RETRIEVAL_DETAIL_PICKING;
        }
        if (value.equals("加料"))
        {
            return SystemDefine.RETRIEVAL_DETAIL_ADD_STORING;
        }
        if (value.equals("未知"))
        {
            return SystemDefine.RETRIEVAL_DETAIL_UNKNOWN;
        }

        return null;
    }
}

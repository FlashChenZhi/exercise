package jp.co.daifuku.wms.exercise.util.list;

import jp.co.daifuku.wms.base.entity.SystemDefine;
import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: lenovo
 * Date: 13-12-2
 * Time: 上午11:38
 * To change this template use File | Settings | File Templates.
 */
public class CarryStatusFormatter implements FieldFormatter<String>
{
    public String format(String obj) throws Exception
    {
        if (obj == null)
        {
            return null;
        }
        if (obj.equals(SystemDefine.CMD_STATUS_ALLOCATION))
        {
            return "已分配";
        }
        if (obj.equals(SystemDefine.CMD_STATUS_START))
        {
            return "开始";
        }
        if (obj.equals(SystemDefine.CMD_STATUS_WAIT_RESPONSE))
        {
            return "等待应答";
        }
        if (obj.equals(SystemDefine.CMD_STATUS_INSTRUCTION))
        {
            return "已指示";
        }
        if (obj.equals(SystemDefine.CMD_STATUS_PICKUP))
        {
            return "取货完成";
        }
        if (obj.equals(SystemDefine.CMD_STATUS_COMP_RETRIEVAL))
        {
            return "出库中";
        }
        if (obj.equals(SystemDefine.CMD_STATUS_ARRIVAL))
        {
            return "到达";
        }
        if (obj.equals(SystemDefine.CMD_STATUS_ERROR))
        {
            return "异常";
        }

        return null;
    }

    public String unformat(String value) throws Exception
    {
        if (StringUtils.isBlank(value))
        {
            return null;
        }
        if (value.equals("已分配"))
        {
            return SystemDefine.CMD_STATUS_ALLOCATION;
        }
        if (value.equals("开始"))
        {
            return SystemDefine.CMD_STATUS_START;
        }
        if (value.equals("等待应答"))
        {
            return SystemDefine.CMD_STATUS_WAIT_RESPONSE;
        }
        if (value.equals("已指示"))
        {
            return SystemDefine.CMD_STATUS_INSTRUCTION;
        }
        if (value.equals("取货完成"))
        {
            return SystemDefine.CMD_STATUS_PICKUP;
        }
        if (value.equals("出库中"))
        {
            return SystemDefine.CMD_STATUS_COMP_RETRIEVAL;
        }
        if (value.equals("到达"))
        {
            return SystemDefine.CMD_STATUS_ARRIVAL;
        }
        if (value.equals("异常"))
        {
            return SystemDefine.CMD_STATUS_ERROR;
        }

        return null;
    }
}

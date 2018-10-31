package jp.co.daifuku.wms.exercise.util.bluedog;

import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.wms.exercise.util.common.Const;
import org.apache.log4j.LogManager;


/**
 * Author: Zhouyue
 * Date: 2011-1-18
 * Time: 11:06:56
 * Copyright Daifuku Shanghai Ltd.
 */
public class LogHelper
{
    public static void writeOpLog(Page page, String info)
    {
        writeOpLog(page.getClass().getSimpleName(), page.getUserInfo().getUserId(), info);
    }

    public static void writeOpLog(String name, String userId, String info)
    {
        LogManager.getLogger(Const.OP_LOG_NAME).info(String.format("[%1$s][%2$s]%3$s", name, userId, info));
    }

    public static void writeCommLog(String msg)
    {
        LogManager.getLogger(Const.COMM_LOG_NAME).info(msg);
    }
}

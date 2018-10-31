package jp.co.daifuku.wms.exercise.util.common;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * Created by IntelliJ IDEA.
 * Author: Zhouyue
 * Date: 2008-2-16
 * Time: 11:02:45
 * Copyright Dsl.Worgsoft.
 */
public class LogWriter
{
    public static void writeError(Class c, Exception ex)
    {
        write(c, ex, LogType.Error);
    }

    public static void writeError(Class c, String log)
    {
        write(c, new Exception(log), LogType.Error);
    }

    public static void write(Class c, Exception ex, LogType logtype)
    {
        Logger logger = LogManager.getLogger(c);
        switch (logtype)
        {
            case Debug:
                logger.debug(ex.getMessage(), ex);
                break;
            case Error:
                logger.error(ex.getMessage(), ex);
                break;
            case Event:
                logger.info(ex.getMessage(), ex);
                break;
            case Info:
                logger.info(ex.getMessage(), ex);
                break;
            case Sql:
                logger.debug(ex.getMessage(), ex);
                break;
            case Warning:
                logger.warn(ex.getMessage(), ex);
                break;
        }
    }

    public static void writeInfo(Class c, String log)
    {
        write(c, new Exception(log), LogType.Info);
    }

    public static void writeInfo(Class c, Exception ex)
    {
        write(c, ex, LogType.Info);
    }


}


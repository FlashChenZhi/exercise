// $Id: EmLog4jLogger.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.util;

import org.apache.log4j.Category;

/**  
 * <jp>SQLログを Log4j で出力するクラスです。 <br></jp>
 * <en>This class provides sql logging methods for Log4j logging. </en>
 * @author  $Author: Muneendra 
 */
public class EmLog4jLogger
{
    /** <jp>SQL ログカテゴリ<br></jp><en>SQL log category<br></en> */
    private static Category emSql = Category.getInstance("EmSql");

    /** <jp>メンテナンスログカテゴリ<br></jp><en>Maintenance log category<br></en> */
    private static Category mainteLog = Category.getInstance("EmMaintenance");

    /** <jp>認証ログカテゴリ<br></jp><en>Authentication log category<br></en> */
    private static Category authLog = Category.getInstance("authentication");

    /** <jp>ログで使用する区切り文字<br></jp><en>Separator for log string.<br></en> */
    public static final String LOG_SEPARATOR = ":::";

    /**
     * <jp>SQL 更新処理のログを出力します。<br></jp>
     * <en>This method logs the information related to SQL update, delete and insert.<br></en>
     * @param message <jp>出力するメッセージ、SQL文 &nbsp;&nbsp;</jp><en>Output message &nbsp;&nbsp</en>
     */
    public static void sqlModify(String message)
    {
        emSql.info(message);
    }

    /**
     * <jp>SQL 検索処理のログを出力します。<br></jp>
     * <en>This method logs the information related to SQL Select statements.<br></en>
     * @param message <jp>出力するメッセージ、SQL文 &nbsp;&nbsp;</jp><en>Output message &nbsp;&nbsp;</en>
     */
    public static void sqlFind(String message)
    {
        emSql.debug(message);
    }

    /**
     * <jp>SQLのエラーのログを出力します。<br></jp>
     * <en>This method logs the information related to SQL Errors and Exception.<br></en>
     * @param message <jp>出力するメッセージ、SQL文 &nbsp;&nbsp;</jp><en>Output message &nbsp;&nbsp;</en>
     * @param exception <en>Exception  &nbsp;&nbsp;</en>
     */
    public static void sqlError(String message, Exception exception)
    {
        emSql.warn(message, exception);
    }

    /**
     * <jp>Info レベルのログを出力します。<br></jp>
     * <en>Logging the info level log. <br></en>
     * @param message <jp>ログメッセージ &nbsp;&nbsp;</jp><en>Log message &nbsp;&nbsp;</en>
     */
    public static void maintenInfo(String message)
    {
        mainteLog.info(message);
    }

    /**
     * <jp>Warn レベルのログを出力します。<br></jp>
     * <en>Logging the warn level log. <br></en>
     * @param message <jp>ログメッセージ &nbsp;&nbsp;</jp><en>Log message &nbsp;&nbsp;</en>
     */
    public static void maintenWarn(String message)
    {
        mainteLog.warn(message);
    }

    /**
     * <jp>Info レベルのログを出力します。<br></jp>
     * <en>Logging the info level log. <br></en>
     * @param message <jp>ログメッセージ &nbsp;&nbsp;</jp><en>Log message &nbsp;&nbsp;</en>
     */
    public static void authInfo(String message)
    {
        authLog.info(message);
    }

    /**
     * <jp>Warn レベルのログを出力します。<br></jp>
     * <en>Logging the warn level log. <br></en>
     * @param message <jp>ログメッセージ &nbsp;&nbsp;</jp><en>Log message &nbsp;&nbsp;</en>
     */
    public static void authWarn(String message)
    {
        authLog.warn(message);
    }
}

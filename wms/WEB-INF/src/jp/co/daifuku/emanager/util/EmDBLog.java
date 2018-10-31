// $Id: EmDBLog.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.bluedog.ui.control.Page;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.LogInfo;
import jp.co.daifuku.emanager.database.entity.Terminal;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.LogHandler;
import jp.co.daifuku.emanager.database.handler.TerminalHandler;

/**
 * UserMenuTool のオペレーションログ出力クラス。
 * @author K.Fukumori
 *
 */
public class EmDBLog
        extends P11Config
{

    /**
     * <jp>メンテナンスログを書き込みます。 <br></jp>
     * <en>Write the maintenance log. <br></en>
     * @param page  <jp>Page クラス &nbsp;&nbsp;</jp><en>Page class &nbsp;&nbsp;</en>
     * @param logClass <jp>ログ区分 &nbsp;&nbsp;</jp><en>Log catecory &nbsp;&nbsp;</en>
     * @param messageNo <jp>メッセージ番号 &nbsp;&nbsp;</jp><en>Message number &nbsp;&nbsp;</en>
     * @param detail <jp>詳細 &nbsp;&nbsp;</jp><en>Detail &nbsp;&nbsp;</en>
     * @throws SQLException
     */
    public static void writeMaintenanceLog(Page page, int logClass, String messageNo, String detail)
            throws SQLException
    {
        String message = EManagerUtil.convertMsg(messageNo);
        String ipaddress = page.getHttpRequest().getRemoteAddr();
        String terminalNo = null;
        String userid = page.getUserInfo().getUserId();
        String procName = page.getClass().getName();
        Date updateDate = new Date();

        // Log4j Logging
        EmLog4jLogger.maintenInfo(message);


        // Database Logging
        Connection conn = null;
        try
        {

            if (isDBLogginMainteLog())
            {
                conn = EmConnectionHandler.getPageDbConnection(page);

                // Search terminal number from ipaddress
                TerminalHandler termHandler = EmHandlerFactory.getTerminalHandler(conn);
                Terminal terminalEntity = termHandler.findByAddress(ipaddress);

                if (terminalEntity != null)
                {
                    terminalNo = terminalEntity.getTerminalNumber();
                }

                LogHandler logHandler = EmHandlerFactory.getLogHandler(conn);
                LogInfo loginfo =
                        makeLogInfo(updateDate, userid, ipaddress, terminalNo, logClass, message, detail, procName);
                logHandler.createMainteLog(loginfo);

                EmConnectionHandler.commit(conn);
            }
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }

    }

    /**
     * <jp>認証ログを書き込みます。 <br></jp>
     * <en>Write the Maintenance log. <br></en>
     * @param userid <jp>ユーザID &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     * @param ipaddress <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP address &nbsp;&nbsp;</en>
     * @param terminalNumber <jp>terminalNumber &nbsp;&nbsp;</jp><en>terminalNumber &nbsp;&nbsp;</en>
     * @param logClass <jp>ログ区分 &nbsp;&nbsp;</jp><en>Log catecory &nbsp;&nbsp;</en>
     * @param messageNo <jp>メッセージ番号 &nbsp;&nbsp;</jp><en>Message number &nbsp;&nbsp;</en>
     * @param detail <jp>詳細 &nbsp;&nbsp;</jp><en>Detail &nbsp;&nbsp;</en>
     * @param procName <jp>更新プロセス名 &nbsp;&nbsp;</jp><en>Update process name &nbsp;&nbsp;</en>
     * @throws SQLException
     */
    public static void writeMaintenanceLog(String userid, String ipaddress, String terminalNumber, int logClass,
            String messageNo, String detail, String procName)
            throws SQLException
    {
        String message = EManagerUtil.convertMsg(messageNo);
        Date updateDate = new Date();

        // log4j Logging
        EmLog4jLogger.maintenInfo(message);

        Connection conn = null;
        try
        {
            if (isDBLogginAuthLog())
            {
                conn = EmConnectionHandler.getConnection();

                LogHandler logHandler = EmHandlerFactory.getLogHandler(conn);
                LogInfo loginfo =
                        makeLogInfo(updateDate, userid, ipaddress, terminalNumber, logClass, message, detail, procName);
                logHandler.createMainteLog(loginfo);

                EmConnectionHandler.commit(conn);
            }
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }

    }

    /**
     * <jp>認証ログを書き込みます。 <br></jp>
     * <en>Write the authentication log. <br></en>
     * @param userid <jp>ユーザID &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     * @param ipaddress <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP address &nbsp;&nbsp;</en>
     * @param logClass <jp>ログ区分 &nbsp;&nbsp;</jp><en>Log catecory &nbsp;&nbsp;</en>
     * @param terminalNumber <jp>端末番号 &nbsp;&nbsp;</jp><en>terminalNumber &nbsp;&nbsp;</en>
     * @param messageNo <jp>メッセージ番号 &nbsp;&nbsp;</jp><en>Message number &nbsp;&nbsp;</en>
     * @param detail <jp>詳細 &nbsp;&nbsp;</jp><en>Detail &nbsp;&nbsp;</en>
     * @param procName <jp>更新プロセス名 &nbsp;&nbsp;</jp><en>Update process name &nbsp;&nbsp;</en>
     * @throws SQLException
     */
    public static void writeAuthenticationLog(String userid, String ipaddress, String terminalNumber, int logClass,
            String messageNo, String detail, String procName)
            throws SQLException
    {
        String message = EManagerUtil.convertMsg(messageNo);
        Date updateDate = new Date();

        // log4j Logging
        EmLog4jLogger.authInfo(message);

        Connection conn = null;
        try
        {
            // do not write access log if part11 flag is true because access log might have been written 
            if (isDBLogginAuthLog() && !isPart11Log())
            {
                conn = EmConnectionHandler.getConnection();
                LogHandler logHandler = EmHandlerFactory.getLogHandler(conn);
                LogInfo loginfo =
                        makeLogInfo(updateDate, userid, ipaddress, terminalNumber, logClass, message, detail, procName);
                logHandler.createAuthenticationLog(loginfo);

                EmConnectionHandler.commit(conn);
            }
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }

    }

    /**
     * <jp>DB に設定する為の LogInfo エンティティを作成します。 <br></jp>
     * <en>Create the LogInfo Entity for DB logging. <br></en>
     * @param updateDate <jp>更新日時 &nbsp;&nbsp;</jp><en>Update date &nbsp;&nbsp;</en>
     * @param userid <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @param ipaddress <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP address &nbsp;&nbsp;</en>
     * @param terminalNumber <jp>端末No. &nbsp;&nbsp;</jp><en>TerminalNo. &nbsp;&nbsp;</en>
     * @param logClass <jp>ログ区分 &nbsp;&nbsp;</jp><en>Log catecory &nbsp;&nbsp;</en>
     * @param msg <jp>メッセージ &nbsp;&nbsp;</jp><en>Message &nbsp;&nbsp;</en>
     * @param detail <jp>詳細 &nbsp;&nbsp;</jp><en>Detail &nbsp;&nbsp;</en>
     * @param procName <jp>更新プロセス名 &nbsp;&nbsp;</jp><en>Update process name &nbsp;&nbsp;</en>
     * @return <jp>LogInfo クラス &nbsp;&nbsp;</jp><en>LogInfo class &nbsp;&nbsp;</en>
     */
    private static LogInfo makeLogInfo(Date updateDate, String userid, String ipaddress, String terminalNumber,
            int logClass, String msg, String detail, String procName)
    {
        LogInfo loginfo = new LogInfo();
        loginfo.setUserId(userid);
        loginfo.setIpAddress(ipaddress);
        loginfo.setTerminalNumber(terminalNumber);
        loginfo.setLogDate(updateDate);
        loginfo.setLogClass(logClass);
        loginfo.setMessage(msg);
        loginfo.setDetails(detail);

        loginfo.setUpdateDate(updateDate);
        loginfo.setUpdateKind(EmConstants.UPDATE_KIND_INSERT);
        loginfo.setUpdateProcess(procName);
        loginfo.setUpdateTerminal(ipaddress);
        loginfo.setUpdateUser(userid);

        return loginfo;
    }

}

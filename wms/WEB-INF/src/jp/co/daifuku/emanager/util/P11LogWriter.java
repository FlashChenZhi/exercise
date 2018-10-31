// $Id: P11LogWriter.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.util;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.emanager.database.entity.AccessLog;
import jp.co.daifuku.emanager.database.entity.UserMasterLog;
import jp.co.daifuku.emanager.database.entity.OperationLog;
import jp.co.daifuku.emanager.database.handler.AccessLogHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.UserMasterChangeLogHandler;
import jp.co.daifuku.emanager.database.handler.OperationLogHandler;


/**
 * UserMenuTool のオペレーションログ出力クラス。
 * @author K.Fukumori
 *
 */
public class P11LogWriter
        extends P11Config
{
    private Connection conn = null;

    /**
     * <jp><br></jp>
     * <en>This method creates the access log</en></br>
     * @param conn Connection<jp></jp><en>java.sql.connection &nbsp;&nbsp;</en>
     */
    public P11LogWriter(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * <jp><br></jp>
     * <en>This method creates the access log</en></br>
     * @param userInfo DfkUserInfo <jp></jp><en>user information&nbsp;&nbsp;</en>
     * @param accessType int <jp></jp><en>Access type&nbsp;&nbsp;</en>
     * @param ipAddress IpAddress <jp></jp><en>IP address of the logged in terminal&nbsp;&nbsp;</en>
     * @param message String <jp></jp><en>detailed message if any &nbsp;&nbsp;</en>
     * @throws SQLException
     */
    public void createAccessLog(DfkUserInfo userInfo, int accessType, String ipAddress, String message)
            throws SQLException
    {
        AccessLog accessLog = new AccessLog();
        accessLog.setUserId(userInfo.getUserId());
        accessLog.setUserName(userInfo.getUserName());
        accessLog.setTerminalNumber(userInfo.getTerminalNumber());
        accessLog.setTerminalName(userInfo.getTerminalName());
        accessLog.setIpAddress(ipAddress);
        accessLog.setDsNumber(userInfo.getDsNumber());
        accessLog.setPageName(userInfo.getPageNameResourceKey());
        accessLog.setAccessType(accessType);
        accessLog.setDetails(message);
        this.createAccessLog(accessLog);
    }

    /**
     * <jp><br></jp>
     * <en>This method creates the access log</en></br>
     * @param userInfo DfkUserInfo <jp></jp><en>user information&nbsp;&nbsp;</en>
     * @param userId UserId<jp></jp><en>user information&nbsp;&nbsp;</en>
     * @param userName userName<jp></jp><en>user information&nbsp;&nbsp;</en>
     * @param accessType int <jp></jp><en>Access type&nbsp;&nbsp;</en>
     * @param ipAddress IpAddress <jp></jp><en>IP address of the logged in terminal&nbsp;&nbsp;</en>
     * @param message String <jp></jp><en>detailed message if any &nbsp;&nbsp;</en>
     * @throws SQLException
     */
    public void createAccessLog(DfkUserInfo userInfo, String userId, String userName, int accessType, String ipAddress,
            String message)
            throws SQLException
    {
        AccessLog accessLog = new AccessLog();
        accessLog.setUserId(userId);
        accessLog.setUserName(userName);
        accessLog.setTerminalNumber(userInfo.getTerminalNumber());
        accessLog.setTerminalName(userInfo.getTerminalName());
        accessLog.setIpAddress(ipAddress);
        accessLog.setDsNumber(userInfo.getDsNumber());
        accessLog.setPageName(userInfo.getPageNameResourceKey());
        accessLog.setAccessType(accessType);
        accessLog.setDetails(message);
        this.createAccessLog(accessLog);
    }

    /**
     * アクセスログの登録を行います。
     * @param accessLog アクセスログエンティティ
     * @throws SQLException
     */
    private void createAccessLog(AccessLog accessLog)
            throws SQLException
    {
        AccessLogHandler logHandler = EmHandlerFactory.getAccessLogHandler(this.conn);
        logHandler.createAccessLog(accessLog);
    }

    /**
     * オペレーションログの登録を行います。
     * 
     * @param userInfo ユーザ情報
     * @param operationType オペレーションタイプ
     * @param itemList 項目リスト
     * @throws SQLException DBエラーが発生した場合にスローされます。
     */
    public void createOperationLog(DfkUserInfo userInfo, int operationType, List itemList)
            throws SQLException
    {
        if (!isOperationLog())
        {
            return;
        }

        OperationLog operationLog = new OperationLog();
        // ユーザID
        operationLog.setUserId(userInfo.getUserId());
        // ユーザ名
        operationLog.setUserName(userInfo.getUserName());
        // 端末番号
        operationLog.setTerminalNumber(userInfo.getTerminalNumber());
        // 端末名称
        operationLog.setTerminalName(userInfo.getTerminalName());
        // IPアドレス
        operationLog.setIpAddress(userInfo.getTerminalAddress());
        // DS番号
        operationLog.setDsNumber(userInfo.getDsNumber());
        // ページ名リソースキー
        operationLog.setPageName(userInfo.getPageNameResourceKey());
        // アクセスタイプ
        operationLog.setOperationType(operationType);
        // 項目リスト
        operationLog.setItemList(itemList);

        OperationLogHandler handler = EmHandlerFactory.getOperationLogHandler(this.conn);

        handler.createOperationLog(operationLog);
    }

    /**
     * オペレーションログの登録を行います。
     * 
     * @param userInfo ユーザ情報
     * @param operationType オペレーションタイプ
     * @param dsNo DS番号
     * @param pageNameResourceKey ページ名リソースキー
     * @param itemList 項目リスト
     * @throws SQLException DBエラーが発生した場合にスローされます。
     */
    public void createOperationLog(DfkUserInfo userInfo, int operationType, String dsNo, String pageNameResourceKey,
            List itemList)
            throws SQLException
    {
        if (!isOperationLog())
        {
            return;
        }

        OperationLog operationLog = new OperationLog();

        // ユーザID
        operationLog.setUserId(userInfo.getUserId());
        // ユーザ名
        operationLog.setUserName(userInfo.getUserName());
        // 端末番号
        operationLog.setTerminalNumber(userInfo.getTerminalNumber());
        // 端末名称
        operationLog.setTerminalName(userInfo.getTerminalName());
        // IPアドレス
        operationLog.setIpAddress(userInfo.getTerminalAddress());
        // DS番号
        operationLog.setDsNumber(dsNo);
        // ページ名リソースキー
        operationLog.setPageName(pageNameResourceKey);
        // アクセスタイプ
        operationLog.setOperationType(operationType);
        // 項目リスト
        operationLog.setItemList(itemList);

        OperationLogHandler handler = EmHandlerFactory.getOperationLogHandler(this.conn);

        handler.createOperationLog(operationLog);
    }

    /**
     * マスタ改廃ログの登録を行います。
     * 
     * @param masterChengeLog エンティティ
     * @throws SQLException DBエラーが発生した場合にスローされます。
     */
    public void createMasterChengeLog(UserMasterLog masterChengeLog)
            throws SQLException
    {
        if (!isMasterLog())
        {
            return;
        }
        UserMasterChangeLogHandler handler = EmHandlerFactory.getUserMasterChangeLogHandler(this.conn);

        handler.createUserMasterChangeLog(masterChengeLog);
    }
}

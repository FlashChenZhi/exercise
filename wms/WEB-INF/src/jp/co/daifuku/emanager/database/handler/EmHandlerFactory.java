// $Id: EmHandlerFactory.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.Connection;

/**
 * <jp>データベースハンドラを参照するためのFactoryクラスです。 <br>
 * </jp> <en>Factory class to get reference to Database handlers <br>
 * </en>
 * 
 * @author $Author: Muneendra
 */
public class EmHandlerFactory
{

    /**
     * <jp>AuthenticationSystemクラスハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of AuthenticationSystem Handler .<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return TerminalHandler : <en>Returns reference to AuthenticationSystem
     *         Handler</en>
     */
    public static AuthenticationHandler getAuthenticationSystemHandler(Connection conn)
    {
        return new AuthenticationSystemImpl(conn);
    }

    /**
     * <jp>Userハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of user handler. <br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return UserHandler : <en>Returns reference to User handler.</en>
     */
    public static UserHandler getUserHandler(Connection conn)
    {
        return new UserHandlerImpl(conn);
    }

    /**
     * <jp>Menuハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of menu handler.<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return MainMenuHandler : <en>Returns reference to Menu Handler</en>
     */
    public static MainMenuHandler getMainMenuHandler(Connection conn)
    {
        return new MainMenuHandlerImpl(conn);
    }

    /**
     * <jp>Submenuハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of sub menu handler.<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return SubMenuHandler : <en>Returns reference to SubMenu Handler</en>
     */
    public static SubMenuHandler getSubMenuHandler(Connection conn)
    {
        return new SubMenuHandlerImpl(conn);
    }

    /**
     * <jp>Functionハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of functionhandler.<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return FunctionHandler : <en>Returns reference to Function Handler</en>
     */
    public static FunctionHandler getFunctionHandler(Connection conn)
    {
        return new FunctionHandlerImpl(conn);
    }

    /**
     * <jp>Roleハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of Role handler.<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return RoleHandler : <en>Returns reference to RoleHandler</en>
     */
    public static RoleHandler getRoleHandler(Connection conn)
    {
        return new RoleHandlerImpl(conn);
    }

    /**
     * <jp>Terminalハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of Terminal handler.<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return TerminalHandler : <en>Returns reference to TerminalHandler</en>
     */
    public static TerminalHandler getTerminalHandler(Connection conn)
    {
        return new TerminalHandlerImpl(conn);
    }

    /**
     * <jp>TerminalUserMapハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of TerminalUserMap handler.<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return TerminalUserMapHandler : <en>Returns reference to
     *         TerminalUserMapHandler</en>
     */
    public static TerminalUserMapHandler getTerminalUserMapHandler(Connection conn)
    {
        return new TerminalUserMapHandlerImpl(conn);
    }

    /**
     * <jp>RoleFunctionMapハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of RoleFunctionMapHandler handler.<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return RoleFunctionMapHandler : <en>Returns reference to
     *         TerminalUserMapHandler</en>
     */
    public static RoleFunctionMapHandler getRoleFunctionMapHandler(Connection conn)
    {
        return new RoleFunctionMapHandlerImpl(conn);
    }

    /**
     * <jp>SubmenuFunctionハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of SubmenuFunctionHandler .<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return SubmenuFunctionHandler : <en>Returns reference to
     *         SubmenuFunctionHandler</en>
     */
    public static SubmenuFunctionHandler getSubmenuFunctionHandler(Connection conn)
    {
        return new SubmenuFunctionHandlerImpl(conn);
    }

    /**
     * <jp>TerminalChangeMapハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of TerminalChangeMapHandler .<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return TerminalChangeMapHandler : <en>Returns reference to
     *         TerminalChangeMapHandler</en>
     */
    public static TerminalChangeMapHandler getTerminalChangeMapHandler(Connection conn)
    {
        return new TerminalChangeMapHandlerImpl(conn);
    }

    /**
     * <jp>PasswordHistoryハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of PassWordHistoryHandler .<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return PassWordHistoryHandler : <en>Returns reference to
     *         PassWordHistoryHandler</en>
     */
    public static PassWordHistoryHandler getPassWordHistoryHandler(Connection conn)
    {
        return new PassWordHistoryHandlerImpl(conn);
    }

    /**
     * <jp>AccessLogHandler ハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of AccessLogHandler .<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return AccessLogHandler : <en>Returns reference to
     *         AccessLogHandler</en>
     */
    public static AccessLogHandler getAccessLogHandler(Connection conn)
    {
        return new AccessLogHandlerImpl(conn);
    }

    /**
     * <jp>OperationLogHandlerハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of OperationLogHandler .<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return OperationLogHandler : <en>Returns reference to
     *         OperationLogHandler</en>
     */
    public static OperationLogHandler getOperationLogHandler(Connection conn)
    {
        return new OperationLogHandlerImpl(conn);
    }

    /**
     * <jp>Logハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of log handler.<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return LogHandler : <en>Returns reference to LogHandler</en>
     */
    public static LogHandler getLogHandler(Connection conn)
    {
        return new LogHandlerImpl(conn);
    }

    /**
     * <jp>Logtempハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of log temp handler.<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return LogHandler : <en>Returns reference to Logtemp Handler</en>
     */
    public static LogTempHandler getLogTempHandler(Connection conn)
    {
        return new LogTempHandlerImpl(conn);
    }


    /**
     * <jp>LogExpImpSetハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of LogExpImpSet handler.<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return LogHandler : <en>Returns reference to LogExpImpSet Handler</en>
     */
    public static LogExpImpSetHandler getLogExpImpSetHandler(Connection conn)
    {
        return new LogExpImpSetHandlerImpl(conn);
    }


    /**
     * <jp>DsNoHandlerハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of DsNoHandler handler.<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return DsNoHandler : <en>Returns reference to DsNoHandler Handler</en>
     */
    public static DsNoHandler getDsNoHandler(Connection conn)
    {
        return new DsNoHandlerImpl(conn);
    }

    /**
     * <jp>Part11LogHandlerハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of Part11LogHandler handler.<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return Part11LogHandler : <en>Returns reference to Part11LogHandler Handler</en>
     */
    public static Part11LogHandler getPart11LogHandler(Connection conn)
    {
        return new Part11LogHandlerImpl(conn);
    }

    /**
     * <jp>MasterChangeLogHandlerハンドラのインスタンスを生成します。 <br>
     * </jp> <en>This method creates instance of MasterChangeLogHandler .<br>
     * </en>
     * 
     * @param conn :
     *            <en> Database Connection.</en>
     * @return MasterChangeLogHandler : <en>Returns reference to
     *         MasterChangeLogHandler</en>
     */
    public static UserMasterChangeLogHandler getUserMasterChangeLogHandler(Connection conn)
    {
        return new UserMasterChangeLogHandlerImpl(conn);
    }
}

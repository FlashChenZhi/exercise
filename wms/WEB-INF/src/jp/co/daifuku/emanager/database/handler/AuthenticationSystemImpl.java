package jp.co.daifuku.emanager.database.handler;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.util.EmLog4jLogger;

/**
 * <en>This class implements AuthenticationHandler interface. </en>
 * 
 * @author $Author: Muneendra
 */
public class AuthenticationSystemImpl
        extends AbstractHandler
        implements AuthenticationHandler
{
    /**
     * @param conn
     *            <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    AuthenticationSystemImpl(Connection conn)
    {
        super.connection = conn;
    }

    /**
     * <jp>このメソッドはAuthenticationSystemクラスにインフォメーションを取って来ます。<br>
     * </jp> <en>This method fetches AuthenticationSystem info </en>
     * 
     * @return AuthenticationSystem authenticationSystem:
     *         <en>AuthenticationSystem info. Returns null If no data found</en>
     * @throws SQLException :
     *             <en>for any database access error.</en>
     */

    public AuthenticationSystem findAuthenticationSystem()
            throws SQLException
    {
        AuthenticationSystem authenSys = null;
        try
        {

            String sql = "SELECT * FROM COM_AUTHENTICATION WHERE AUTHENTICATIONID=1";
            EmLog4jLogger.sqlFind(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            super.statement = connection.createStatement();
            super.resultset = statement.executeQuery(sql);

            if (!resultset.next())
            {
                return null;
            }

            authenSys = new AuthenticationSystem();
            // set user data to user entity
            authenSys.setAuthenticationId(resultset.getString(EmTableColumns.AUTHENTICATIONSYSTEM_AUTHENTICATIONID));
            authenSys.setLoginMax(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_LOGINMAX));
            authenSys.setSameLoginUserFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_SAMELOGINUSER_FLAG));
            authenSys.setTerminalUserCheckFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_TERMINALUSERCHECK_FLAG));
            authenSys.setTerminalAdminRoleCheckFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_TERMINALADMINROLECHECK_FLAG));
            authenSys.setTerminalChangeFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_TERMINALCHANGE_FLAG));
            authenSys.setScreenLoginCheckFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_SCREENLOGINCHECK_FLAG));
            authenSys.setFailedLoginscreenLockCount(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_FAILEDLOGINSCREENLOCKCOUNT));
            authenSys.setFaieldLoginScreenLockTime(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_FAILEDLOGINSCREENLOCKTIME));
            authenSys.setFaieldLoginUserLockFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_FAILEDLOGINUSERLOCK_FLAG));

            authenSys.setSameUserCreateBlockPeriod(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_SAMEUSERCREATE_BLOCK_PERIOD));
            authenSys.setTerminalCheckFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_TERMINALCHECK_FLAG));
            authenSys.setIpRangeCheckFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_IPRANGECHECK_FLAG));
            authenSys.setIprangeMax(resultset.getString(EmTableColumns.AUTHENTICATIONSYSTEM_IPRANGE_MAX));
            authenSys.setIprangeMin(resultset.getString(EmTableColumns.AUTHENTICATIONSYSTEM_IPRANGE_MIN));
            authenSys.setDummyPassFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_DUMMYPASSWORD_FLAG));
            authenSys.setPassSafetyCheckFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_PASSWORDSAFTYCHECK_FLAG));
            authenSys.setPassExpireFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_PASSWORDEXPIRE_FLAG));
            authenSys.setPassLogCheckTime(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_PASSWORDLOGCHECKTIME));
            authenSys.setPassMinLength(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_PASSWORDMINLENGTH));
            authenSys.setFailLoginAttem(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_FAILEDLOGINATTEMPTS));
            authenSys.setPassChangeInterval(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_PWDCHANGEINTERVAL));
            authenSys.setPassExpireAlertDays(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_PASSWORDEXPIREALERTDAYS));
            authenSys.setAutoLoginFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_AUTOLOGIN_FLAG));
            authenSys.setSessionTimeoutTime(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_SESSIONTIMEOUTTIME));

            authenSys.setPart11Flag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_PART11LOG_FLAG));
            authenSys.setAccessLogFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_ACCESSLOG_FLAG));
            authenSys.setOperationLogFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_OPERATIONLOG_FLAG));
            authenSys.setMasterLogFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_MASTERLOG_FLAG));
            authenSys.setStockLogFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_STOCKLOG_FLAG));
            authenSys.setDbLogHoldDays(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_DB_LOG_HOLD_DAYS));
            authenSys.setCsvLogHoldDays(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_CSV_LOG_HOLD_DAYS));
            authenSys.setHdLogHoldYears(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_HD_LOG_HOLD_YEARS));

            authenSys.setAuthLogFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_AUTHENTICATIONLOG_FLAG));
            authenSys.setUserMaintLogFlag(resultset.getBoolean(EmTableColumns.AUTHENTICATIONSYSTEM_USERMAINTENANCELOG_FLAG));
            authenSys.setSameUserCreateBlockPeriod(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_SAMEUSERCREATE_BLOCK_PERIOD));
            authenSys.setMainMenuShowType(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_MAINMENUTYPE));
            authenSys.setWorkDate(resultset.getTimestamp((EmTableColumns.AUTHENTICATIONSYSTEM_WORKDATE)));
            authenSys.setUpdateDate(resultset.getTimestamp(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_DATE));
            authenSys.setUpdateUser(resultset.getString(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_USER));
            authenSys.setUpdateTerminal(resultset.getString(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_TERMINAL));
            authenSys.setUpdateKind(resultset.getInt(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_KIND));
            authenSys.setUpdateProcess(resultset.getString(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_PROCESS));

        }
        catch (SQLException sqlException)
        {
            EmLog4jLogger.sqlError(this.getClass().getName(), sqlException);
            throw sqlException;
        }
        finally
        {
            super.closeStatment_Resultset();
        }
        return authenSys;
    }

    /**
     * <jp>AuthenticationSystemクラスの情報を変更します。 <br>
     * </jp> <en>This method modifies AuthenticationSystem information .</en>
     * 
     * @param authenSystem
     *            <en> Main Menu information which has to be modified.</en>
     * @return int : <en>modified number of rows</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int updateAuthenticationSystem(AuthenticationSystem authenSystem)
            throws SQLException
    {
        int result;
        try
        {
            // Modify password SQL
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE COM_AUTHENTICATION SET ");
            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_LOGINMAX + "=" + authenSystem.getLoginMax() + ",");

            if (authenSystem.getSameLoginUserFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_SAMELOGINUSER_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_SAMELOGINUSER_FLAG + "=0,");
            }

            if (authenSystem.getTerminalUserCheckFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_TERMINALUSERCHECK_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_TERMINALUSERCHECK_FLAG + "=0,");
            }

            if (authenSystem.getTerminalAdminRoleCheckFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_TERMINALADMINROLECHECK_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_TERMINALADMINROLECHECK_FLAG + "=0,");
            }

            if (authenSystem.getTerminalChangeFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_TERMINALCHANGE_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_TERMINALCHANGE_FLAG + "=0,");
            }

            if (authenSystem.getScreenLoginCheckFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_SCREENLOGINCHECK_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_SCREENLOGINCHECK_FLAG + "=0,");
            }

            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_FAILEDLOGINSCREENLOCKTIME + "="
                    + authenSystem.getFaieldLoginScreenLockTime() + ",");

            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_FAILEDLOGINSCREENLOCKCOUNT + "="
                    + authenSystem.getFailedLoginscreenLockCount() + ",");

            if (authenSystem.getFaieldLoginUserLockFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_FAILEDLOGINUSERLOCK_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_FAILEDLOGINUSERLOCK_FLAG + "=0,");
            }

            if (authenSystem.getTerminalCheckFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_TERMINALCHECK_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_TERMINALCHECK_FLAG + "=0,");
            }

            if (authenSystem.getIpRangeCheckFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_IPRANGECHECK_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_IPRANGECHECK_FLAG + "=0,");
            }

            if (authenSystem.getIprangeMin() != null)
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_IPRANGE_MIN + "='" + authenSystem.getIprangeMin() + "',");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_IPRANGE_MIN + "=NULL,");
            }

            if (authenSystem.getIprangeMax() != null)
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_IPRANGE_MAX + "='" + authenSystem.getIprangeMax() + "',");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_IPRANGE_MAX + "=NULL,");
            }

            if (authenSystem.getDummyPassFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_DUMMYPASSWORD_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_DUMMYPASSWORD_FLAG + "=0,");
            }

            if (authenSystem.getPassSafetyCheckFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_PASSWORDSAFTYCHECK_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_PASSWORDSAFTYCHECK_FLAG + "=0,");
            }

            if (authenSystem.getPassExpireFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_PASSWORDEXPIRE_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_PASSWORDEXPIRE_FLAG + "=0,");
            }

            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_PASSWORDLOGCHECKTIME + "="
                    + authenSystem.getPassLogCheckTime() + ",");

            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_PASSWORDMINLENGTH + "=" + authenSystem.getPassMinLength()
                    + ",");
            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_FAILEDLOGINATTEMPTS + "=" + authenSystem.getFailLoginAttem()
                    + ",");
            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_PWDCHANGEINTERVAL + "="
                    + authenSystem.getPassChangeInterval() + ",");
            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_PASSWORDEXPIREALERTDAYS + "="
                    + authenSystem.getPassExpireAlertDays() + ",");

            if (authenSystem.getAutoLoginFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_AUTOLOGIN_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_AUTOLOGIN_FLAG + "=0,");
            }
            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_SESSIONTIMEOUTTIME + "="
                    + authenSystem.getSessionTimeoutTime() + ",");


            if (authenSystem.getPart11Flag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_PART11LOG_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_PART11LOG_FLAG + "=0,");
            }

            if (authenSystem.getAccessLogFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_ACCESSLOG_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_ACCESSLOG_FLAG + "=0,");
            }

            if (authenSystem.getMasterLogFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_MASTERLOG_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_MASTERLOG_FLAG + "=0,");
            }

            if (authenSystem.getStockLogFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_STOCKLOG_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_STOCKLOG_FLAG + "=0,");
            }

            if (authenSystem.getOperationLogFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_OPERATIONLOG_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_OPERATIONLOG_FLAG + "=0,");
            }

            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_DB_LOG_HOLD_DAYS + "=" + authenSystem.getDbLogHoldDays()
                    + ",");

            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_CSV_LOG_HOLD_DAYS + "=" + authenSystem.getCsvLogHoldDays()
                    + ",");

            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_HD_LOG_HOLD_YEARS + "=" + authenSystem.getHdLogHoldYears()
                    + ",");

            if (authenSystem.getAuthLogFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_AUTHENTICATIONLOG_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_AUTHENTICATIONLOG_FLAG + "=0,");
            }

            if (authenSystem.getUserMaintLogFlag())
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_USERMAINTENANCELOG_FLAG + "=1,");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_USERMAINTENANCELOG_FLAG + "=0,");
            }

            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_SAMEUSERCREATE_BLOCK_PERIOD + "="
                    + authenSystem.getSameUserCreateBlockPeriod() + ",");

            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_MAINMENUTYPE + "=" + authenSystem.getMainMenuShowType()
                    + ",");

            if (authenSystem.getWorkDate() != null)
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_WORKDATE + "="
                        + getDateFormat(authenSystem.getWorkDate()) + ",");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_WORKDATE + "=NULL,");
            }

            sql.append(EmTableColumns.LOGINUSER_UPDATE_DATE + "=SYSDATE,");

            if (authenSystem.getUpdateUser() != null)
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_USER + "='" + authenSystem.getUpdateUser() + "',");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_USER + "=NULL,");
            }

            if (authenSystem.getUpdateTerminal() != null)
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_TERMINAL + "='"
                        + authenSystem.getUpdateTerminal() + "',");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_TERMINAL + "=NULL,");
            }

            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_KIND + "=" + authenSystem.getUpdateKind() + ",");

            if (authenSystem.getUpdateProcess() != null)
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_PROCESS + "='" + authenSystem.getUpdateProcess()
                        + "'");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_PROCESS + "=NULL");
            }

            // Where conditions
            sql.append(" WHERE AUTHENTICATIONID ='" + authenSystem.getAuthenticationId() + "'");

            // create porepared statement
            super.statement = connection.createStatement();
            // Update user information
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            result = statement.executeUpdate(sql.toString());

        }
        catch (SQLException sqlException)
        {
            EmLog4jLogger.sqlError(this.getClass().getName(), sqlException);
            throw sqlException;
        }
        finally
        {
            super.closeStatment_Resultset();
        }
        return result;
    }

    /**
     * 
     * <en>This method updates work date.<br>
     * </en>
     * 
     * @param authenSystem       
     * @return int : <en>modified number of rows.zero value is returned if there
     *         are no records to modify</en>
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    public int updateWorkDate(AuthenticationSystem authenSystem)
            throws SQLException
    {
        int result;
        try
        {
            // Modify password SQL
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE COM_AUTHENTICATION SET ");

            if (authenSystem.getWorkDate() != null)
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_WORKDATE + "="
                        + getDateFormat(authenSystem.getWorkDate()) + ",");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_WORKDATE + "=NULL,");
            }

            sql.append(EmTableColumns.LOGINUSER_UPDATE_DATE + "=SYSDATE,");

            if (authenSystem.getUpdateUser() != null)
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_USER + "='" + authenSystem.getUpdateUser() + "',");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_USER + "=NULL,");
            }

            if (authenSystem.getUpdateTerminal() != null)
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_TERMINAL + "='"
                        + authenSystem.getUpdateTerminal() + "',");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_TERMINAL + "=NULL,");
            }

            //			 updated by eWareNavi	
            sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_KIND + "=3,");


            if (authenSystem.getUpdateProcess() != null)
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_PROCESS + "='" + authenSystem.getUpdateProcess()
                        + "'");
            }
            else
            {
                sql.append(EmTableColumns.AUTHENTICATIONSYSTEM_UPDATE_PROCESS + "=NULL");
            }

            // Where conditions
            sql.append(" WHERE AUTHENTICATIONID = 1");

            // create porepared statement
            super.statement = connection.createStatement();
            // Update user information
            EmLog4jLogger.sqlModify(this.getClass().getName() + EmLog4jLogger.LOG_SEPARATOR + sql);
            result = statement.executeUpdate(sql.toString());

        }
        catch (SQLException sqlException)
        {
            EmLog4jLogger.sqlError(this.getClass().getName(), sqlException);
            throw sqlException;
        }
        finally
        {
            super.closeStatment_Resultset();
        }
        return result;
    }

}

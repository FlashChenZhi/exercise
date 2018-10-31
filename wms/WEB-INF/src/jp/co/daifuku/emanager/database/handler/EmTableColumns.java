// $Id: EmTableColumns.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

/**
 * <jp> データベースの列名を保持するインターフェースです。 データベースの列項目を取得するクラスは、このインターフェースをインプリメントしてください。
 * </jp> <en> It is the interface that the column name of the database is held.
 * The class which acquires the column item of the database is to implement this
 * interface. </en>
 */

public abstract class EmTableColumns
{
    /**
     * AUTHENTICATIONLOG Table : LOGDATE
     */
    public static final String AUTHENTICATIONLOG_LOGDATE = "LOGDATE";

    /**
     * AUTHENTICATIONLOG Table : USERID
     */
    public static final String AUTHENTICATIONLOG_USERID = "USERID";

    /**
     * AUTHENTICATIONLOG Table : IPADDRESS
     */
    public static final String AUTHENTICATIONLOG_IPADDRESS = "IPADDRESS";

    /**
     * AUTHENTICATIONLOG Table : LOGLEVEL
     */
    public static final String AUTHENTICATIONLOG_LOGLEVEL = "LOGLEVEL";

    /**
     * AUTHENTICATIONLOG Table : NOTE
     */
    public static final String AUTHENTICATIONLOG_MESSAGE = "MESSAGE";

    /**
     * AUTHENTICATIONSYSTEM Table : AUTHENTICATIONID
     */
    public static final String AUTHENTICATIONSYSTEM_AUTHENTICATIONID = "AUTHENTICATIONID";

    /**
     * AUTHENTICATIONSYSTEM Table : LOGINMAX
     */
    public static final String AUTHENTICATIONSYSTEM_LOGINMAX = "LOGINMAX";

    /**
     * AUTHENTICATIONSYSTEM Table : LOGINMAX
     */
    public static final String AUTHENTICATIONSYSTEM_SAMELOGINUSER_FLAG = "SAMELOGINUSER_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : TERMINALUSERCHECK_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_TERMINALUSERCHECK_FLAG = "TERMINALUSERCHECK_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : TERMINALADMINROLECHECK_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_TERMINALADMINROLECHECK_FLAG = "TERMINALADMINROLECHECK_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : TERMINALCHANGE_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_TERMINALCHANGE_FLAG = "TERMINALCHANGE_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : SCREENLOGINCHECK_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_SCREENLOGINCHECK_FLAG = "SCREENLOGINCHECK_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : FAILEDLOGINSCREENLOCKTIME
     */
    public static final String AUTHENTICATIONSYSTEM_FAILEDLOGINSCREENLOCKTIME = "FAILEDLOGINSCREENLOCKTIME";

    /**
     * AUTHENTICATIONSYSTEM Table : FAILEDLOGINSCREENLOCKCOUNT
     */
    public static final String AUTHENTICATIONSYSTEM_FAILEDLOGINSCREENLOCKCOUNT = "FAILEDLOGINSCREENLOCKCOUNT";

    /**
     * AUTHENTICATIONSYSTEM Table : FAILEDLOGINUSERLOCK_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_FAILEDLOGINUSERLOCK_FLAG = "FAILEDLOGINUSERLOCK_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : FAILEDLOGINUSERLOCK_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_SAMEUSERCREATE_BLOCK_PERIOD = "SAMEUSERCREATE_BLOCK_PERIOD";

    /**
     * AUTHENTICATIONSYSTEM Table : IPRANGECHECK_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_TERMINALCHECK_FLAG = "TERMINALCHECK_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : IPRANGECHECK_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_IPRANGECHECK_FLAG = "IPRANGECHECK_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : IPRANGE_MIN
     */
    public static final String AUTHENTICATIONSYSTEM_IPRANGE_MIN = "IPRANGE_MIN";

    /**
     * AUTHENTICATIONSYSTEM Table : IPRANGE_MAX
     */
    public static final String AUTHENTICATIONSYSTEM_IPRANGE_MAX = "IPRANGE_MAX";

    /**
     * AUTHENTICATIONSYSTEM Table : DUMMYPASSWORD_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_DUMMYPASSWORD_FLAG = "DUMMYPASSWORD_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : PASSWORDSAFTYCHECK_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_PASSWORDSAFTYCHECK_FLAG = "PASSWORDSAFTYCHECK_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : PASSWORDEXPIRE_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_PASSWORDEXPIRE_FLAG = "PASSWORDEXPIRE_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : PASSWORDLOGCHECKTIME
     */
    public static final String AUTHENTICATIONSYSTEM_PASSWORDLOGCHECKTIME = "PASSWORDLOGCHECKTIME";

    /**
     * AUTHENTICATIONSYSTEM Table : PASSWORDMINLENGTH
     */
    public static final String AUTHENTICATIONSYSTEM_PASSWORDMINLENGTH = "PASSWORDMINLENGTH";

    /**
     * AUTHENTICATIONSYSTEM Table : FAILEDLOGINATTEMPTS
     */
    public static final String AUTHENTICATIONSYSTEM_FAILEDLOGINATTEMPTS = "FAILEDLOGINATTEMPTS";

    /**
     * AUTHENTICATIONSYSTEM Table : PASSWORDEXPIREALERTDAYS
     */
    public static final String AUTHENTICATIONSYSTEM_PWDCHANGEINTERVAL = "PWDCHANGEINTERVAL";

    /**
     * AUTHENTICATIONSYSTEM Table : PASSWORDEXPIREALERTDAYS
     */
    public static final String AUTHENTICATIONSYSTEM_PASSWORDEXPIREALERTDAYS = "PASSWORDEXPIREALERTDAYS";

    /**
     * AUTHENTICATIONSYSTEM Table : AUTOLOGIN_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_AUTOLOGIN_FLAG = "AUTOLOGIN_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : SESSIONTIMEOUTTIME
     */
    public static final String AUTHENTICATIONSYSTEM_SESSIONTIMEOUTTIME = "SESSIONTIMEOUTTIME";

    /**
     * AUTHENTICATIONSYSTEM Table : PART11LOG_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_PART11LOG_FLAG = "PART11LOG_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : ACCESSLOG_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_ACCESSLOG_FLAG = "ACCESSLOG_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : OPERATIONLOG_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_OPERATIONLOG_FLAG = "OPERATIONLOG_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : MASTERLOG_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_MASTERLOG_FLAG = "MASTERLOG_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : SESSIONTIMEOUTTIME
     */
    public static final String AUTHENTICATIONSYSTEM_STOCKLOG_FLAG = "STOCKLOG_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : DB_LOG_HOLD_DAYS
     */
    public static final String AUTHENTICATIONSYSTEM_DB_LOG_HOLD_DAYS = "DB_LOG_HOLD_DAYS";

    /**
     * AUTHENTICATIONSYSTEM Table : CSV_LOG_HOLD_DAYS
     */
    public static final String AUTHENTICATIONSYSTEM_CSV_LOG_HOLD_DAYS = "CSV_LOG_HOLD_DAYS";

    /**
     * AUTHENTICATIONSYSTEM Table : HD_LOG_HOLD_DAYS
     */
    public static final String AUTHENTICATIONSYSTEM_HD_LOG_HOLD_YEARS = "HD_LOG_HOLD_YEARS";

    /**
     * AUTHENTICATIONSYSTEM Table : AUTHENTICATIONLOG_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_AUTHENTICATIONLOG_FLAG = "AUTHENTICATIONLOG_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : USERMAINTENANCELOG_FLAG
     */
    public static final String AUTHENTICATIONSYSTEM_USERMAINTENANCELOG_FLAG = "USERMAINTENANCELOG_FLAG";

    /**
     * AUTHENTICATIONSYSTEM Table : MAINMENUTYPE
     */
    public static final String AUTHENTICATIONSYSTEM_MAINMENUTYPE = "MAINMENUTYPE";

    /**
     * AUTHENTICATIONSYSTEM Table : WORKDATE
     */
    public static final String AUTHENTICATIONSYSTEM_WORKDATE = "WORKDATE";

    /**
     * AUTHENTICATIONSYSTEM Table : UPDATE_DATE
     */
    public static final String AUTHENTICATIONSYSTEM_UPDATE_DATE = "UPDATE_DATE";

    /**
     * AUTHENTICATIONSYSTEM Table : UPDATE_USER
     */
    public static final String AUTHENTICATIONSYSTEM_UPDATE_USER = "UPDATE_USER";

    /**
     * AUTHENTICATIONSYSTEM Table : UPDATE_TERMINAL
     */
    public static final String AUTHENTICATIONSYSTEM_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * AUTHENTICATIONSYSTEM Table : UPDATE_KIND
     */
    public static final String AUTHENTICATIONSYSTEM_UPDATE_KIND = "UPDATE_KIND";

    /**
     * AUTHENTICATIONSYSTEM Table : UPDATE_PROCESS
     */
    public static final String AUTHENTICATIONSYSTEM_UPDATE_PROCESS = "UPDATE_PROCESS";

    /**
     * LOGINUSER Table : USERID
     */
    public static final String LOGINUSER_USERID = "USERID";

    /**
     * LOGINUSER Table : PASSWORD
     */
    public static final String LOGINUSER_PASSWORD = "PASSWORD";

    /**
     * LOGINUSER Table : USERNAME
     */
    public static final String LOGINUSER_USERNAME = "USERNAME";

    /**
     * LOGINUSER Table : ROLEID
     */
    public static final String LOGINUSER_ROLEID = "ROLEID";

    /**
     * LOGINUSER Table : DUMMYPASSWORD_FLAG
     */
    public static final String LOGINUSER_DUMMYPASSWORD_FLAG = "DUMMYPASSWORD_FLAG";

// 2008/12/08 K.Matsuda Start テーブル列名変更(USERLOCK_FLAG -> USERSTATUS)
    /**
     * LOGINUSER Table : USERSTATUS
     */
    public static final String LOGINUSER_USERSTATUS = "USERSTATUS";
// 2008/12/08 K.Matsuda End
    
    /**
     * LOGINUSER Table : PWDEXPIRES
     */
    public static final String LOGINUSER_PWDEXPIRES = "PWDEXPIRES";

    /**
     * LOGINUSER Table : PWDCHANGEINTERVAL
     */
    public static final String LOGINUSER_PWDCHANGEINTERVAL = "PWDCHANGEINTERVAL";

    /**
     * LOGINUSER Table : LASTACCESSDATE
     */
    public static final String LOGINUSER_LASTACCESSDATE = "LASTACCESSDATE";

    /**
     * LOGINUSER Table : SAMEUSERLOGINMAX
     */
    public static final String LOGINUSER_SAMEUSERLOGINMAX = "SAMEUSERLOGINMAX";

    /**
     * LOGINUSER Table : FAILEDLOGINATTEMPTS
     */
    public static final String LOGINUSER_FAILEDLOGINATTEMPTS = "FAILEDLOGINATTEMPTS";

    /**
     * LOGINUSER Table : FAILEDCOUNT
     */
    public static final String LOGINUSER_FAILEDCOUNT = "FAILEDCOUNT";

    /**
     * LOGINUSER Table : FAILEDSTARTDATE
     */
    public static final String LOGINUSER_FAILEDSTARTDATE = "FAILEDSTARTDATE";

    /**
     * LOGINUSER Table : DELETESTATUS
     */
    public static final String LOGINUSER_DELETESTATUS = "DELETESTATUS";

    /**
     * LOGINUSER Table : DELETE_DATE
     */
    public static final String LOGINUSER_DELETE_DATE = "DELETE_DATE";

    /**
     * LOGINUSER Table : DEPARTMENT
     */
    public static final String LOGINUSER_DEPARTMENT = "DEPARTMENT";

    /**
     * LOGINUSER Table : REMARK
     */
    public static final String LOGINUSER_REMARK = "REMARK";

    /**
     * LOGINUSER Table : UPDATE_DATE
     */
    public static final String LOGINUSER_UPDATE_DATE = "UPDATE_DATE";

    /**
     * LOGINUSER Table : UPDATE_USER
     */
    public static final String LOGINUSER_UPDATE_USER = "UPDATE_USER";

    /**
     * LOGINUSER Table : UPDATE_TERMINAL
     */
    public static final String LOGINUSER_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * LOGINUSER Table : UPDATE_KIND
     */
    public static final String LOGINUSER_UPDATE_KIND = "UPDATE_KIND";

    /**
     * LOGINUSER Table : UPDATE_PROCESS
     */
    public static final String LOGINUSER_UPDATE_PROCESS = "UPDATE_PROCESS";

    /**
     * USERATTRIBUTE Table : USERID
     */
    public static final String USERATTRIBUTE_USERID = "USERID";

    /**
     * USERATTRIBUTE Table : DEPARTMENT
     */
    public static final String USERATTRIBUTE_DEPARTMENT = "DEPARTMENT";

    /**
     * USERATTRIBUTE Table : REMARK
     */
    public static final String USERATTRIBUTE_REMARK = "REMARK";

    /**
     * USERATTRIBUTE Table : UPDATE_DATE
     */
    public static final String USERATTRIBUTE_UPDATE_DATE = "UPDATE_DATE";

    /**
     * USERATTRIBUTE Table : UPDATE_USER
     */
    public static final String USERATTRIBUTE_UPDATE_USER = "UPDATE_USER";

    /**
     * USERATTRIBUTE Table : UPDATE_TERMINAL
     */
    public static final String USERATTRIBUTE_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * USERATTRIBUTE Table : UPDATE_KIND
     */
    public static final String USERATTRIBUTE_UPDATE_KIND = "UPDATE_KIND";

    /**
     * USERATTRIBUTE Table : UPDATE_PROCESS
     */
    public static final String USERATTRIBUTE_UPDATE_PROCESS = "UPDATE_PROCESS";


    /**
     * ROLE Table : ROLEID
     */
    public static final String ROLE_ROLEID = "ROLEID";

    /**
     * ROLE Table : ROLENAME
     */
    public static final String ROLE_FAILEDLOGINATTEMPTS = "FAILEDLOGINATTEMPTS";

    /**
     * ROLE Table : ROLENAME
     */
    public static final String ROLE_PWDCHANGEINTERVAL = "PWDCHANGEINTERVAL";

    /**
     * ROLE Table : ROLENAME
     */
    public static final String ROLE_ROLENAME = "ROLENAME";

    /**
     * ROLE Table : TARGET
     */
    public static final String ROLE_TARGET = "TARGET";

    /**
     * ROLE Table : UPDATE_DATE
     */
    public static final String ROLE_UPDATE_DATE = "UPDATE_DATE";

    /**
     * ROLEMAP Table : UPDATE_USER
     */
    public static final String ROLE_UPDATE_USER = "UPDATE_USER";

    /**
     * ROLE Table : UPDATE_TERMINAL
     */
    public static final String ROLE_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * ROLE Table : UPDATE_KIND
     */
    public static final String ROLE_UPDATE_KIND = "UPDATE_KIND";

    /**
     * ROLEM Table : UPDATE_PROCESS
     */
    public static final String ROLE_UPDATE_PROCESS = "UPDATE_PROCESS";

    /**
     * ROLEFUNCTIONMAP Table : ROLEID
     */
    public static final String ROLEFUNCTIONMAP_ROLEID = "ROLEID";

    /**
     * ROLEFUNCTIONMAP Table : FUNCTIONID
     */
    public static final String ROLEFUNCTIONMAP_FUNCTIONID = "FUNCTIONID";

    /**
     * ROLEFUNCTIONMAP Table : UPDATE_DATE
     */
    public static final String ROLEFUNCTIONMAP_UPDATE_DATE = "UPDATE_DATE";

    /**
     * ROLEFUNCTIONMAP Table : UPDATE_USER
     */
    public static final String ROLEFUNCTIONMAP_UPDATE_USER = "UPDATE_USER";

    /**
     * ROLEFUNCTIONMAP Table : UPDATE_TERMINAL
     */
    public static final String ROLEFUNCTIONMAP_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * ROLEFUNCTIONMAP Table : UPDATE_KIND
     */
    public static final String ROLEFUNCTIONMAP_UPDATE_KIND = "UPDATE_KIND";

    /**
     * ROLEFUNCTIONMAP Table : UPDATE_PROCESS
     */
    public static final String ROLEFUNCTIONMAP_UPDATE_PROCESS = "UPDATE_PROCESS";

    /**
     * MAINMENU Table : MAINMENUID
     */
    public static final String MAINMENU_MAINMENUID = "MAINMENUID";

    /**
     * MAINMENU Table : MENUDISPODER
     */
    public static final String MAINMENU_MENUDISPORDER = "MENUDISPORDER";

    /**
     * MAINMENU Table : MENURESOURCEKEY
     */
    public static final String MAINMENU_MENURESOURCEKEY = "MUNERESOURCEKEY";

    /**
     * MAINMENU Table : UPDATE_DATE
     */
    public static final String MAINMENU_UPDATE_DATE = "UPDATE_DATE";

    /**
     * MAINMENU Table : UPDATE_USER
     */
    public static final String MAINMENU_UPDATE_USER = "UPDATE_USER";

    /**
     * MAINMENU Table : UPDATE_TERMINAL
     */
    public static final String MAINMENU_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * MAINMENU Table : UPDATE_KIND
     */
    public static final String MAINMENU_UPDATE_KIND = "UPDATE_KIND";

    /**
     * MAINMENU Table : UPDATE_PROCESS
     */
    public static final String MAINMENU_UPDATE_PROCESS = "UPDATE_PROCESS";

    /**
     * SUBMENU Table : SUBMENUID
     */
    public static final String SUBMENU_SUBMENUID = "SUBMENUID";

    /**
     * SUBMENU Table : MAINMENUID
     */
    public static final String SUBMENU_MAINMENUID = "MAINMENUID";

    /**
     * SUBMENU Table : SUBMENUDISPORDER
     */
    public static final String SUBMENU_SUBMENUDISPORDER = "SUBMENUDISPORDER";

    /**
     * SUBMENU Table : SUBMENURESOURCEKEY
     */
    public static final String SUBMENU_SUBMENURESOURCEKEY = "SUBMENURESOURCEKEY";

    /**
     * SUBMENU Table : UPDATE_DATE
     */
    public static final String SUBMENU_UPDATE_DATE = "UPDATE_DATE";

    /**
     * SUBMENU Table : UPDATE_USER
     */
    public static final String SUBMENU_UPDATE_USER = "UPDATE_USER";

    /**
     * SUBMENU Table : UPDATE_TERMINAL
     */
    public static final String SUBMENU_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * SUBMENU Table : UPDATE_KIND
     */
    public static final String SUBMENU_UPDATE_KIND = "UPDATE_KIND";

    /**
     * SUBMENU Table : UPDATE_PROCESS
     */
    public static final String SUBMENU_UPDATE_PROCESS = "UPDATE_PROCESS";

    /**
     * FUNCTION Table : FUNCTIONID
     */
    public static final String FUNCTION_FUNCTIONID = "FUNCTIONID";

    /**
     * FUNCTION Table : SUBMENUID
     */
    public static final String FUNCTION_SUBMENUID = "SUBMENUID";

    /**
     * FUNCTION Table : DS_NO
     */
    public static final String FUNCTION_DS_NO = "DS_NO";

    /**
     * FUNCTION Table : BUTTONDISPORDER
     */
    public static final String FUNCTION_BUTTONDISPORDER = "BUTTONDISPORDER";

    /**
     * FUNCTION Table : BUTTONRESOURCEKEY
     */
    public static final String FUNCTION_BUTTONRESOURCEKEY = "BUTTONRESOURCEKEY";

    /**
     * FUNCTION Table : PAGENAMERESOURCEKEY
     */
    public static final String FUNCTION_PAGENAMERESOURCEKEY = "PAGENAMERESOURCEKEY";

    /**
     * FUNCTION Table : DOAUTHENTICATION_FLAG
     */
    public static final String FUNCTION_DOAUTHENTICATION = "DOAUTHENTICATION_FLAG";

    /**
     * FUNCTION Table : URI
     */
    public static final String FUNCTION_URI = "URI";

    /**
     * FUNCTION Table : FRAMENAME
     */
    public static final String FUNCTION_FRAMENAME = "FRAMENAME";

    /**
     * FUNCTION Table : HIDDEN_FLAG
     */
    public static final String FUNCTION_HIDDEN_FLAG = "HIDDEN_FLAG";

    /**
     * FUNCTION Table : UPDATE_DATE
     */
    public static final String FUNCTION_UPDATE_DATE = "UPDATE_DATE";

    /**
     * FUNCTION Table : UPDATE_USER
     */
    public static final String FUNCTION_UPDATE_USER = "UPDATE_USER";

    /**
     * FUNCTION Table : UPDATE_TERMINAL
     */
    public static final String FUNCTION_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * FUNCTION Table : UPDATE_KIND
     */
    public static final String FUNCTION_UPDATE_KIND = "UPDATE_KIND";

    /**
     * FUNCTION Table : UPDATE_PROCESS
     */
    public static final String FUNCTION_UPDATE_PROCESS = "UPDATE_PROCESS";

    /**
     * TERMINAL Table : TERMINALNUMBER
     */
    public static final String TERMINAL_TERMINALNUMBER = "TERMINALNUMBER";

    /**
     * TERMINAL Table : TERMINALNAME
     */
    public static final String TERMINAL_TERMINALNAME = "TERMINALNAME";

    /**
     * TERMINAL Table : TERMINALADDRESS
     */
    public static final String TERMINAL_TERMINALADDRESS = "TERMINALADDRESS";

    /**
     * TERMINAL Table : ROLEID
     */
    public static final String TERMINAL_ROLEID = "ROLEID";

    /**
     * TERMINAL Table : PRINTERNAME
     */
    public static final String TERMINAL_PRINTERNAME = "PRINTERNAME";

    /**
     * TERMINAL Table : AUTOLOGIN_FLAG
     */
    public static final String TERMINAL_AUTOLOGIN_FLAG = "AUTOLOGIN_FLAG";

    /**
     * TERMINAL Table : UPDATE_DATE
     */
    public static final String TERMINAL_UPDATE_DATE = "UPDATE_DATE";

    /**
     * TERMINAL Table : UPDATE_USER
     */
    public static final String TERMINAL_UPDATE_USER = "UPDATE_USER";

    /**
     * TERMINAL Table : UPDATE_TERMINAL
     */
    public static final String TERMINAL_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * TERMINAL Table : UPDATE_KIND
     */
    public static final String TERMINAL_UPDATE_KIND = "UPDATE_KIND";

    /**
     * TERMINAL Table : UPDATE_PROCESS
     */
    public static final String TERMINAL_UPDATE_PROCESS = "UPDATE_PROCESS";

    /**
     * TERMINALUSERMAP Table : TERMINALNUMBERMAP
     */
    public static final String TERMINALUSERMAP_TERMINALNUMBER = "TERMINALNUMBER";

    /**
     * TERMINALUSERMAP Table : USERID
     */
    public static final String TERMINALUSERMAP_USERID = "USERID";

    /**
     * TERMINALUSERMAP Table : UPDATE_DATE
     */
    public static final String TERMINALUSERMAP_UPDATE_DATE = "UPDATE_DATE";

    /**
     * TERMINALUSERMAP Table : UPDATE_USER
     */
    public static final String TERMINALUSERMAP_UPDATE_USER = "UPDATE_USER";

    /**
     * /** TERMINALUSERMAP Table : UPDATE_TERMINAL
     */
    public static final String TERMINALUSERMAP_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * TERMINALUSERMAP Table : UPDATE_KIND
     */
    public static final String TERMINALUSERMAP_UPDATE_KIND = "UPDATE_KIND";

    /**
     * TERMINALUSERMAP Table : UPDATE_PROCESS
     */
    public static final String TERMINALUSERMAP_UPDATE_PROCESS = "UPDATE_PROCESS";

    /**
     * TERMINALCHANGEMAP Table : TERMINALNUMBERMAP
     */
    public static final String TERMINALCHANGEMAP_TERMINALNUMBER = "TERMINALNUMBER";

    /**
     * TERMINALCHANGEMAP Table : ROLEID
     */
    public static final String TERMINALCHANGEMAP_CHANGETERMINALNUMBER = "CHANGETERMINALNUMBER";

    /**
     * TERMINALCHANGEMAP Table : UPDATE_DATE
     */
    public static final String TERMINALCHANGEMAP_UPDATE_DATE = "UPDATE_DATE";

    /**
     * TERMINALMAP Table : UPDATE_USER
     */
    public static final String TERMINALCHANGEMAP_UPDATE_USER = "UPDATE_USER";

    /**
     * /** TERMINALCHANGEMAP Table : UPDATE_TERMINAL
     */
    public static final String TERMINALCHANGEMAP_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * TERMINALCHANGEMAP Table : UPDATE_KIND
     */
    public static final String TERMINALCHANGEMAP_UPDATE_KIND = "UPDATE_KIND";

    /**
     * TERMINALCHANGEMAP Table : UPDATE_PROCESS
     */
    public static final String TERMINALCHANGEMAP_UPDATE_PROCESS = "UPDATE_PROCESS";

    /**
     * COM_MAINTENANCELOG Table : UPDATE_PROCESS
     */
    public static final String COM_MAINTENANCELOG_LOGDATE = "LOGDATE";

    /**
     * COM_MAINTENANCELOG Table : USERID
     */
    public static final String COM_MAINTENANCELOG_USERID = "USERID";

    /**
     * COM_MAINTENANCELOG Table : IPADDRESS
     */
    public static final String COM_MAINTENANCELOG_IPADDRESS = "IPADDRESS";

    /**
     * COM_MAINTENANCELOG Table : TERMINALNUMBER
     */
    public static final String COM_MAINTENANCELOG_TERMINALNUMBER = "TERMINALNUMBER";

    /**
     * COM_MAINTENANCELOG Table : LOGCLASS
     */
    public static final String COM_MAINTENANCELOG_LOGCLASS = "LOGCLASS";

    /**
     * COM_MAINTENANCELOG Table : MESSAGE
     */
    public static final String COM_MAINTENANCELOG_MESSAGE = "MESSAGE";

    /**
     * COM_MAINTENANCELOG Table : DETAIL
     */
    public static final String COM_MAINTENANCELOG_DETAIL = "DETAIL";

    /**
     * COM_MAINTENANCELOG Table : UPDATE_DATE
     */
    public static final String COM_MAINTENANCELOG_UPDATE_DATE = "UPDATE_DATE";

    /**
     * COM_MAINTENANCELOG Table : UPDATE_USER
     */
    public static final String COM_MAINTENANCELOG_UPDATE_USER = "UPDATE_USER";

    /**
     * COM_MAINTENANCELOG Table : UPDATE_TERMINAL
     */
    public static final String COM_MAINTENANCELOG_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * COM_MAINTENANCELOG Table : UPDATE_KIND
     */
    public static final String COM_MAINTENANCELOG_UPDATE_KIND = "UPDATE_KIND";

    /**
     * COM_MAINTENANCELOG Table : UPDATE_PROCESS
     */
    public static final String COM_MAINTENANCELOG_UPDATE_PROCESS = "UPDATE_PROCESS";

    /**
     * COM_AUTHENTICATIONLOG Table : LOGDATE
     */
    public static final String COM_AUTHENTICATIONLOG_LOGDATE = "LOGDATE";

    /**
     * COM_AUTHENTICATIONLOG Table : USERID
     */
    public static final String COM_AUTHENTICATIONLOG_USERID = "USERID";

    /**
     * COM_AUTHENTICATIONLOG Table : IPADDRESS
     */
    public static final String COM_AUTHENTICATIONLOG_IPADDRESS = "IPADDRESS";

    /**
     * COM_AUTHENTICATIONLOG Table : TERMINALNUMBER
     */
    public static final String COM_AUTHENTICATIONLOG_TERMINALNUMBER = "TERMINALNUMBER";

    /**
     * COM_AUTHENTICATIONLOG Table : LOGCLASS
     */
    public static final String COM_AUTHENTICATIONLOG_LOGCLASS = "LOGCLASS";

    /**
     * COM_AUTHENTICATIONLOG Table : MESSAGE
     */
    public static final String COM_AUTHENTICATIONLOG_MESSAGE = "MESSAGE";

    /**
     * COM_AUTHENTICATIONLOG Table : DETAIL
     */
    public static final String COM_AUTHENTICATIONLOG_DETAIL = "DETAIL";

    /**
     * COM_AUTHENTICATIONLOG Table : UPDATE_DATE
     */
    public static final String COM_AUTHENTICATIONLOG_UPDATE_DATE = "UPDATE_DATE";

    /**
     * COM_AUTHENTICATIONLOG Table : UPDATE_USER
     */
    public static final String COM_AUTHENTICATIONLOG_UPDATE_USER = "UPDATE_USER";

    /**
     * COM_AUTHENTICATIONLOG Table : UPDATE_TERMINAL
     */
    public static final String COM_AUTHENTICATIONLOG_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * COM_AUTHENTICATIONLOG Table : UPDATE_KIND
     */
    public static final String COM_AUTHENTICATIONLOG_UPDATE_KIND = "UPDATE_KIND";

    /**
     * COM_AUTHENTICATIONLOG Table : UPDATE_PROCESS
     */
    public static final String COM_AUTHENTICATIONLOG_UPDATE_PROCESS = "UPDATE_PROCESS";

    /**
     * COM_AUTHLOG_TEMP Table : LOGDATE
     */
    public static final String COM_AUTHLOG_TEMP_LOGDATE = "LOGDATE";

    /**
     * COM_AUTHLOG_TEMP Table : USERID
     */
    public static final String COM_AUTHLOG_TEMP_USERID = "USERID";

    /**
     * COM_AUTHLOG_TEMP Table : IPADDRESS
     */
    public static final String COM_AUTHLOG_TEMP_IPADDRESS = "IPADDRESS";

    /**
     * COM_AUTHLOG_TEMP Table : TERMINALNUMBER
     */
    public static final String COM_AUTHLOG_TEMP_TERMINALNUMBER = "TERMINALNUMBER";

    /**
     * COM_AUTHLOG_TEMP Table : LOGCLASS
     */
    public static final String COM_AUTHLOG_TEMP_LOGCLASS = "LOGCLASS";

    /**
     * COM_AUTHLOG_TEMP Table : MESSAGE
     */
    public static final String COM_AUTHLOG_TEMP_MESSAGE = "MESSAGE";

    /**
     * COM_AUTHLOG_TEMP Table : DETAIL
     */
    public static final String COM_AUTHLOG_TEMP_DETAIL = "DETAIL";

    /**
     * COM_AUTHLOG_TEMP Table : FILENAME
     */
    public static final String COM_AUTHLOG_TEMP_FILENAME = "FILENAME";

    /**
     * COM_AUTHLOG_TEMP Table : UPDATE_DATE
     */
    public static final String COM_AUTHLOG_TEMP_UPDATE_DATE = "UPDATE_DATE";

    /**
     * COM_AUTHLOG_TEMP Table : UPDATE_USER
     */
    public static final String COM_AUTHLOG_TEMP_UPDATE_USER = "UPDATE_USER";

    /**
     * COM_AUTHLOG_TEMP Table : UPDATE_TERMINAL
     */
    public static final String COM_AUTHLOG_TEMP_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * COM_AUTHLOG_TEMP Table : UPDATE_KIND
     */
    public static final String COM_AUTHLOG_TEMP_UPDATE_KIND = "UPDATE_KIND";

    /**
     * COM_AUTHLOG_TEMP Table : UPDATE_PROCESS
     */
    public static final String COM_AUTHLOG_TEMP_UPDATE_PROCESS = "UPDATE_PROCESS";

    /**
     * COM_MAINTELOG_TEMP Table : LOGDATE
     */
    public static final String COM_MAINTELOG_TEMP_LOGDATE = "LOGDATE";

    /**
     * COM_MAINTELOG_TEMP Table : USERID
     */
    public static final String COM_MAINTELOG_TEMP_USERID = "USERID";

    /**
     * COM_MAINTELOG_TEMP Table : IPADDRESS
     */
    public static final String COM_MAINTELOG_TEMP_IPADDRESS = "IPADDRESS";

    /**
     * COM_MAINTELOG_TEMP Table : TERMINALNUMBER
     */
    public static final String COM_MAINTELOG_TEMP_TERMINALNUMBER = "TERMINALNUMBER";

    /**
     * COM_MAINTELOG_TEMP Table : LOGCLASS
     */
    public static final String COM_MAINTELOG_TEMP_LOGCLASS = "LOGCLASS";

    /**
     * COM_MAINTELOG_TEMP Table : MESSAGE
     */
    public static final String COM_MAINTELOG_TEMP_MESSAGE = "MESSAGE";

    /**
     * COM_MAINTELOG_TEMP Table : DETAIL
     */
    public static final String COM_MAINTELOG_TEMP_DETAIL = "DETAIL";

    /**
     * COM_MAINTELOG_TEMP Table : FILENAME
     */
    public static final String COM_MAINTELOG_TEMP_FILENAME = "FILENAME";

    /**
     * COM_MAINTELOG_TEMP Table : UPDATE_DATE
     */
    public static final String COM_MAINTELOG_TEMP_UPDATE_DATE = "UPDATE_DATE";

    /**
     * COM_MAINTELOG_TEMP Table : UPDATE_USER
     */
    public static final String COM_MAINTELOG_TEMP_UPDATE_USER = "UPDATE_USER";

    /**
     * COM_MAINTELOG_TEMP Table : UPDATE_TERMINAL
     */
    public static final String COM_MAINTELOG_TEMP_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * COM_MAINTELOG_TEMP Table : UPDATE_KIND
     */
    public static final String COM_MAINTELOG_TEMP_UPDATE_KIND = "UPDATE_KIND";

    /**
     * COM_MAINTELOG_TEMP Table : UPDATE_PROCESS
     */
    public static final String COM_MAINTELOG_TEMP_UPDATE_PROCESS = "UPDATE_PROCESS";

    /**
     * COM_PASSWORDHISTORY Table : UESRID
     */
    public static final String COM_PASSWORDHISTORY_UESRID = "USERID";

    /**
     * COM_PASSWORDHISTORY Table : OLDPASSWORD
     */
    public static final String COM_PASSWORDHISTORY_OLDPASSWORD = "OLDPASSWORD";

    /**
     * COM_MAINTELOG_TEMP Table : UPDATE_DATE
     */
    public static final String COM_PASSWORDHISTORY_UPDATE_DATE = "UPDATE_DATE";

    /**
     * COM_MAINTELOG_TEMP Table : UPDATE_USER
     */
    public static final String COM_PASSWORDHISTORY_UPDATE_USER = "UPDATE_USER";

    /**
     * COM_MAINTELOG_TEMP Table : UPDATE_TERMINAL
     */
    public static final String COM_PASSWORDHISTORY_UPDATE_TERMINAL = "UPDATE_TERMINAL";

    /**
     * COM_MAINTELOG_TEMP Table : UPDATE_KIND
     */
    public static final String COM_PASSWORDHISTORY_UPDATE_KIND = "UPDATE_KIND";

    /**
     * COM_MAINTELOG_TEMP Table : UPDATE_PROCESS
     */
    public static final String COM_PASSWORDHISTORY_UPDATE_PROCESS = "UPDATE_PROCESS";

    /**
     * COM_AUTHLOG_VIEW Table : LOGDATE
     */
    public static final String COM_AUTHLOG_VIEW_LOGDATE = "LOGDATE";

    /**
     * COM_AUTHLOG_VIEW Table : USERID
     */
    public static final String COM_AUTHLOG_VIEW_USERID = "USERID";

    /**
     * COM_AUTHLOG_VIEW Table : IPADDRESS
     */
    public static final String COM_AUTHLOG_VIEW_IPADDRESS = "IPADDRESS";

    /**
     * COM_AUTHLOG_VIEW Table : LOGCLASS
     */
    public static final String COM_AUTHLOG_VIEW_LOGCLASS = "LOGCLASS";

    /**
     * COM_AUTHLOG_VIEW Table : MESSAGE
     */
    public static final String COM_AUTHLOG_VIEW_MESSAGE = "MESSAGE";

    /**
     * COM_AUTHLOG_VIEW Table : LOGCLASS
     */
    public static final String COM_AUTHLOG_VIEW_DETAIL = "DETAIL";

    /**
     * COM_MAINTELOG_VIEW Table : LOGDATE
     */
    public static final String COM_MAINTELOG_VIEW_LOGDATE = "LOGDATE";

    /**
     * COM_MAINTELOG_VIEW Table : USERID
     */
    public static final String COM_MAINTELOG_VIEW_USERID = "USERID";

    /**
     * COM_MAINTELOG_VIEW Table : IPADDRESS
     */
    public static final String COM_MAINTELOG_VIEW_IPADDRESS = "IPADDRESS";

    /**
     * COM_MAINTELOG_VIEW Table : LOGCLASS
     */
    public static final String COM_MAINTELOG_VIEW_LOGCLASS = "LOGCLASS";

    /**
     * COM_MAINTELOG_VIEW Table : MESSAGE
     */
    public static final String COM_MAINTELOG_VIEW_MESSAGE = "MESSAGE";

    /**
     * COM_MAINTELOG_VIEW Table : LOGCLASS
     */
    public static final String COM_MAINTELOG_VIEW_DETAIL = "DETAIL";


    /**
     * COM_ACCESSLOG Table : LOG_DATE
     */
    public static final String COM_ACCESSLOG_LOG_DATE = "LOG_DATE";

    /**
     * COM_ACCESSLOG Table : LOG_DATE_GMT
     */
    public static final String COM_ACCESSLOG_LOG_DATE_GMT = "LOG_DATE_GMT";

    /**
     * COM_ACCESSLOG Table : USER_ID
     */
    public static final String COM_ACCESSLOG_USER_ID = "USER_ID";

    /**
     * COM_ACCESSLOG Table : USER_NAME
     */
    public static final String COM_ACCESSLOG_USER_NAME = "USER_NAME";

    /**
     * COM_ACCESSLOG Table : TERMINAL_NUMBER
     */
    public static final String COM_ACCESSLOG_TERMINAL_NUMBER = "TERMINAL_NUMBER";

    /**
     * COM_ACCESSLOG Table : TERMINAL_NAME
     */
    public static final String COM_ACCESSLOG_TERMINAL_NAME = "TERMINAL_NAME";

    /**
     * COM_ACCESSLOG Table : IPADDRESS
     */
    public static final String COM_ACCESSLOG_IPADDRESS = "IPADDRESS";

    /**
     * COM_ACCESSLOG Table : DS_NO
     */
    public static final String COM_ACCESSLOG_DS_NO = "DS_NO";

    /**
     * COM_ACCESSLOG Table : PAGENAMERESOURCEKEY
     */
    public static final String COM_ACCESSLOG_PAGENAMERESOURCEKEY = "PAGENAMERESOURCEKEY";

    /**
     * COM_ACCESSLOG Table : LOG_DATE
     */
    public static final String COM_ACCESSLOG_ACCESS_TYPE = "ACCESS_TYPE";

    /**
     * COM_ACCESSLOG Table : DETAIL
     */
    public static final String COM_ACCESSLOG_DETAIL = "DETAIL";

    /**
     * COM_LOGEXPIMP_SET Table : EXPORT_TABLENAME
     */
    public static final String COM_LOGEXPIMP_SET_EXPORT_TABLENAME = "EXPORT_TABLENAME";

    /**
     * COM_LOGEXPIMP_SET Table : IMPORT_TABLENAME
     */
    public static final String COM_LOGEXPIMP_SET_IMPORT_TABLENAME = "IMPORT_TABLENAME";

    /**
     * COM_LOGEXPIMP_SET Table : TEBLERESOURCEKEY
     */
    public static final String COM_LOGEXPIMP_SET_TEBLERESOURCEKEY = "TABLERESOURCEKEY";

    /**
     * COM_LOGEXPIMP_SET Table : CSVFILEPREFIX
     */
    public static final String COM_LOGEXPIMP_SET_CSVFILEPREFIX = "CSVFILEPREFIX";

    /**
     * COM_LOGEXPIMP_SET Table : IMPORT_DATE
     */
    public static final String COM_LOGEXPIMP_SET_IMPORT_DATE = "IMPORT_DATE";

    /**
     * COM_LOGEXPIMP_SET Table : IMPORT_FILENAME
     */
    public static final String COM_LOGEXPIMP_SET_IMPORT_FILENAME = "IMPORT_FILENAME";

    /**
     * COM_LOGEXPIMP_SET Table : DISPORDER
     */
    public static final String COM_LOGEXPIMP_SET_DISPORDER = "DISPORDER";

    /**
     * COM_LOGEXPIMP_SET Table : OUTPUT_START_DATE
     */
    public static final String COM_LOGEXPIMP_SET_OUTPUT_START_DATE = "OUTPUT_START_DATE";

    /**
     * COM_LOGEXPIMP_SET Table : EXPORT_FILENAME
     */
    public static final String COM_LOGEXPIMP_SET_EXPORT_FILENAME = "EXPORT_FILENAME";

    /**
     * COM_LOGEXPIMP_SET Table : EXPORT_FILE_LOG_DATE_TO
     */
    public static final String COM_LOGEXPIMP_SET_EXPORT_FILE_LOG_DATE_TO = "EXPORT_FILE_LOG_DATE_TO";

    /**
     * COM_LOGEXPIMP_SET Table : NEXT_EXPORT_LOG_DATE_FROM
     */
    public static final String COM_LOGEXPIMP_SET_NEXT_EXPORT_LOG_DATE_FROM = "NEXT_EXPORT_LOG_DATE_FROM";

    /**
     * COM_LOGEXPIMP_SET Table : MASTER_URI
     */
    public static final String COM_LOGEXPIMP_SET_MASTER_URI = "MASTER_URI";

    /**
     * COM_LOGEXPIMP_SET Table : MASTER_FLAG
     */
    public static final String COM_LOGEXPIMP_SET_MASTER_FLAG = "MASTER_FLAG";

    /**
     * COM_ACCESSLOG_IMP Table : LOG_DATE
     */
    public static final String COM_ACCESSLOG_IMP_LOG_DATE = "LOG_DATE";

    /**
     * COM_ACCESSLOG_IMP Table : LOG_DATE_GMT
     */
    public static final String COM_ACCESSLOG_IMP_LOG_DATE_GMT = "LOG_DATE_GMT";

    /**
     * COM_ACCESSLOG_IMP Table : USER_ID
     */
    public static final String COM_ACCESSLOG_IMP_USER_ID = "USER_ID";

    /**
     * COM_ACCESSLOG_IMP Table : USER_NAME
     */
    public static final String COM_ACCESSLOG_IMP_USER_NAME = "USER_NAME";

    /**
     * COM_ACCESSLOG_IMP Table : TERMINAL_NUMBER
     */
    public static final String COM_ACCESSLOG_IMP_TERMINAL_NUMBER = "TERMINAL_NUMBER";

    /**
     * COM_ACCESSLOG_IMP Table : TERMINAL_NAME
     */
    public static final String COM_ACCESSLOG_IMP_TERMINAL_NAME = "TERMINAL_NAME";

    /**
     * COM_ACCESSLOG_IMP Table : IPADDRESS
     */
    public static final String COM_ACCESSLOG_IMP_IPADDRESS = "IPADDRESS";

    /**
     * COM_ACCESSLOG_IMP Table : DS_NO
     */
    public static final String COM_ACCESSLOG_IMP_DS_NO = "DS_NO";

    /**
     * COM_ACCESSLOG_IMP Table : PAGENAMERESOURCEKEY
     */
    public static final String COM_ACCESSLOG_IMP_PAGENAMERESOURCEKEY = "PAGENAMERESOURCEKEY";

    /**
     * COM_ACCESSLOG_IMP Table : LOG_DATE
     */
    public static final String COM_ACCESSLOG_IMP_ACCESS_TYPE = "ACCESS_TYPE";

    /**
     * COM_ACCESSLOG_IMP Table : DETAIL
     */
    public static final String COM_ACCESSLOG_IMP_DETAIL = "DETAIL";

    /**
     * COM_USERHISTORY Table : LOG_DATE
     */
    public static final String COM_USERHISTORY_LOG_DATE = "LOG_DATE";

    /**
     * COM_USERHISTORY Table : LOG_DATE_GMT
     */
    public static final String COM_USERHISTORY_LOG_DATE_GMT = "LOG_DATE_GMT";

    /**
     * COM_USERHISTORY Table : USER_ID
     */
    public static final String COM_USERHISTORY_USER_ID = "USER_ID";

    /**
     * COM_USERHISTORY Table : USER_NAME
     */
    public static final String COM_USERHISTORY_USER_NAME = "USER_NAME";

    /**
     * COM_USERHISTORY Table : TERMINAL_NUMBER
     */
    public static final String COM_USERHISTORY_TERMINAL_NUMBER = "TERMINAL_NUMBER";

    /**
     * COM_USERHISTORY Table : TERMINAL_NAME
     */
    public static final String COM_USERHISTORY_TERMINAL_NAME = "TERMINAL_NAME";

    /**
     * COM_USERHISTORY Table : IPADDRESS
     */
    public static final String COM_USERHISTORY_IPADDRESS = "IPADDRESS";

    /**
     * COM_USERHISTORY Table : DS_NO
     */
    public static final String COM_USERHISTORY_DS_NO = "DS_NO";

    /**
     * COM_USERHISTORY Table : PAGENAMERESOURCEKEY
     */
    public static final String COM_USERHISTORY_PAGENAMERESOURCEKEY = "PAGENAMERESOURCEKEY";

    /**
     * COM_USERHISTORY Table : UPDATE_KIND
     */
    public static final String COM_USERHISTORY_UPDATE_KIND = "UPDATE_KIND";

    /**
     * COM_USERHISTORY Table : MASTER_USER_ID
     */
    public static final String COM_USERHISTORY_MASTER_USER_ID = "MASTER_USER_ID";

    /**
     * COM_USERHISTORY Table : USER_NAME_BEFORE
     */
    public static final String COM_USERHISTORY_USER_NAME_BEFORE = "USER_NAME_BEFORE";

    /**
     * COM_USERHISTORY Table : PASSWORD_BEFORE
     */
    public static final String COM_USERHISTORY_PASSWORD_BEFORE = "PASSWORD_BEFORE";

    /**
     * COM_USERHISTORY Table : PASSWORDCHANGEINTERVAL_BEFORE
     */
    public static final String COM_USERHISTORY_PASSWORDCHANGEINTERVAL_BEFORE = "PASSWORDCHANGEINTERVAL_BEFORE";

    /**
     * COM_USERHISTORY Table : PWDEXPIRES_BEFORE
     */
    public static final String COM_USERHISTORY_PWDEXPIRES_BEFORE = "PWDEXPIRES_BEFORE";

// 2009/01/20 K.Matsuda Start 列名変更(USERLOCK_FLAG -> USERSTATUS)
    /**
     * COM_USERHISTORY Table : USERSTATUS_BEFORE
     */
    public static final String COM_USERHISTORY_USERSTATUS_BEFORE = "USERSTATUS_BEFORE";
// 2009/01/20 K.Matsuda End

    /**
     * COM_USERHISTORY Table : USERLOCK_SAMEUSERLOGINMAX_BEFORE
     */
    public static final String COM_USERHISTORY_SAMEUSERLOGINMAX_BEFORE = "SAMEUSERLOGINMAX_BEFORE";

    /**
     * COM_USERHISTORY Table : FAILEDLOGINATTEMPTS_BEFORE
     */
    public static final String COM_USERHISTORY_FAILEDLOGINATTEMPTS_BEFORE = "FAILEDLOGINATTEMPTS_BEFORE";

    /**
     * COM_USERHISTORY Table : ROLE_ID_BEFORE
     */
    public static final String COM_USERHISTORY_ROLE_ID_BEFORE = "ROLE_ID_BEFORE";

    /**
     * COM_USERHISTORY Table : DEPARTMENT_BEFORE
     */
    public static final String COM_USERHISTORY_DEPARTMENT_BEFORE = "DEPARTMENT_BEFORE";

    /**
     * COM_USERHISTORY Table : REMARK_BEFORE
     */
    public static final String COM_USERHISTORY_REMARK_BEFORE = "REMARK_BEFORE";

    /**
     * COM_USERHISTORY Table : USER_NAME_AFTER
     */
    public static final String COM_USERHISTORY_USER_NAME_AFTER = "USER_NAME_AFTER";

    /**
     * COM_USERHISTORY Table : PASSWORD_AFTER
     */
    public static final String COM_USERHISTORY_PASSWORD_AFTER = "PASSWORD_AFTER";

    /**
     * COM_USERHISTORY Table : PASSWORDCHANGEINTERVAL_AFTER
     */
    public static final String COM_USERHISTORY_PASSWORDCHANGEINTERVAL_AFTER = "PASSWORDCHANGEINTERVAL_AFTER";

    /**
     * COM_USERHISTORY Table : PWDEXPIRES_AFTER
     */
    public static final String COM_USERHISTORY_PWDEXPIRES_AFTER = "PWDEXPIRES_AFTER";

// 2009/01/20 K.Matsuda Start 列名変更(USERLOCK_FLAG -> USERSTATUS)
    /**
     * COM_USERHISTORY Table : USERSTATUS_AFTER
     */
    public static final String COM_USERHISTORY_USERSTATUS_AFTER = "USERSTATUS_AFTER";
// 2009/01/20 K.Matsuda End

    /**
     * COM_USERHISTORY Table : USERLOCK_SAMEUSERLOGINMAX_AFTER
     */
    public static final String COM_USERHISTORY_SAMEUSERLOGINMAX_AFTER = "SAMEUSERLOGINMAX_AFTER";

    /**
     * COM_USERHISTORY Table : FAILEDLOGINATTEMPTS_AFTER
     */
    public static final String COM_USERHISTORY_FAILEDLOGINATTEMPTS_AFTER = "FAILEDLOGINATTEMPTS_AFTER";

    /**
     * COM_USERHISTORY Table : ROLE_ID_AFTER
     */
    public static final String COM_USERHISTORY_ROLE_ID_AFTER = "ROLE_ID_AFTER";

    /**
     * COM_USERHISTORY Table : DEPARTMENT_AFTER
     */
    public static final String COM_USERHISTORY_DEPARTMENT_AFTER = "DEPARTMENT_AFTER";

    /**
     * COM_USERHISTORY Table : REMARK_AFTER
     */
    public static final String COM_USERHISTORY_REMARK_AFTER = "REMARK_AFTER";

    /**
     * COM_OPERATIONLOG Table : LOG_DATE
     */
    public static final String COM_OPERATIONLOG_LOG_DATE = "LOG_DATE";

    /**
     * COM_OPERATIONLOG Table : LOG_DATE_GMT
     */
    public static final String COM_OPERATIONLOG_DATE_GMT = "LOG_DATE_GMT";

    /**
     * COM_OPERATIONLOG Table : USER_ID
     */
    public static final String COM_OPERATIONLOG_USER_ID = "USER_ID";

    /**
     * COM_OPERATIONLOG Table : USER_NAME
     */
    public static final String COM_OPERATIONLOG_USER_NAME = "USER_NAME";

    /**
     * COM_OPERATIONLOG Table : TERMINAL_NUMBER
     */
    public static final String COM_OPERATIONLOG_TERMINAL_NUMBER = "TERMINAL_NUMBER";

    /**
     * COM_OPERATIONLOG Table : TERMINAL_NAME
     */
    public static final String COM_OPERATIONLOG_TERMINAL_NAME = "TERMINAL_NAME";

    /**
     * COM_OPERATIONLOG Table : IPADDRESS
     */
    public static final String COM_OPERATIONLOG_IPADDRESS = "IPADDRESS";

    /**
     * COM_OPERATIONLOG Table : DS_NO
     */
    public static final String COM_OPERATIONLOG_DS_NO = "DS_NO";

    /**
     * COM_OPERATIONLOG Table : PAGENAMERESOURCEKEY
     */
    public static final String COM_OPERATIONLOG_PAGENAMERESOURCEKEY = "PAGENAMERESOURCEKEY";

    /**
     * COM_OPERATIONLOG Table : LOG_DATE
     */
    public static final String COM_OPERATIONLOG_OPERATION_TYPE = "OPERATION_TYPE";

    /**
     * COM_OPERATIONLOG Table : DETAIL
     */
    public static final String COM_OPERATIONLOG_DETAIL = "DETAIL";

    /**
     * COM_OPERATIONLOG Table : ITEM
     */
    public static final String COM_OPERATIONLOG_ITEM = "ITEM_";

}

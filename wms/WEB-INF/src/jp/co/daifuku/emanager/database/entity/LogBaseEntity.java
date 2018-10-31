package jp.co.daifuku.emanager.database.entity;

import java.util.Date;

/**
 * 
 * Part11ログに関する共通情報を保持するエンティティです。
 *
 *
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  Last commit: $Author: admin $
 */
public class LogBaseEntity
        extends BaseEntity
{

    /** <jp>詳細 &nbsp;&nbsp;</jp><en>Details &nbsp;&nbsp;</en> */
    private String details = null;

    /** <jp>ユーザ名 &nbsp;&nbsp;</jp><en>User name. &nbsp;&nbsp;</en> */
    private String userName = null;

    /** <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP adress &nbsp;&nbsp;</en> */
    private String ipAddress = null;

    /** <jp>terminalNumber  &nbsp;&nbsp;</jp><en>terminalNumber &nbsp;&nbsp;</en> */
    private String terminalNumber = null;

    /** <jp>ログ区分 &nbsp;&nbsp;</jp><en>Log class &nbsp;&nbsp;</en> */
    private int accessType;

    /** <jp>出力日時 &nbsp;&nbsp;</jp><en>Log date &nbsp;&nbsp;</en> */
    private Date logDate = null;

    /** <jp>メッセージ &nbsp;&nbsp;</jp><en>Message &nbsp;&nbsp;</en> */
    private String message = null;

    /** <jp>パスワード &nbsp;&nbsp;</jp><en>Old password &nbsp;&nbsp;</en> */
    private String oldPassword = null;

    /** <jp>更新日時 &nbsp;&nbsp;</jp><en>gmtDate &nbsp;&nbsp;</en> */
    private Date gmtDate = null;

    /** <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en> */
    private String userId = null;

    /** <jp>画面DS番号 &nbsp;&nbsp;</jp><en>dnNumber &nbsp;&nbsp;</en>*/
    private String dsNumber = null;

    /** <jp>画面名称 &nbsp;&nbsp;</jp><en>ScreenName &nbsp;&nbsp;</en>*/
    private String pageName = null;

    /** <jp>端末名 &nbsp;&nbsp;</jp><en>Terminal name. &nbsp;&nbsp;</en> */
    private String terminalName = null;

    /**
     * <jp>詳細を取得します。<br>
     * </jp> <en>Return details.<br>
     * </en>
     * 
     * @return <jp>詳細 &nbsp;&nbsp;</jp><en>Details &nbsp;&nbsp;</en>
     */
    public String getDetails()
    {
        return details;
    }


    /**
     * <jp>IPアドレスを取得します。<br>
     * </jp> <en>Return IP adress.<br>
     * </en>
     * 
     * @return <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP adress &nbsp;&nbsp;</en>
     */
    public String getIpAddress()
    {
        return ipAddress;
    }

    /**
     * <jp>terminalNumber<br>
     * </jp> <en>terminalNumber<br>
     * </en>
     * 
     * @return <jp>terminalNumber &nbsp;&nbsp;</jp><en>terminalNumber &nbsp;&nbsp;</en>
     */
    public String getTerminalNumber()
    {
        return terminalNumber;
    }

    /**
     * <jp>ログ区分を取得します。<br>
     * </jp> <en>Return accessType.<br>
     * </en>
     * 
     * @return <jp>ログ区分 &nbsp;&nbsp;</jp><en>accessType &nbsp;&nbsp;</en>
     */
    public int getAccessType()
    {
        return accessType;
    }

    /**
     * <jp>出力日時を取得します。<br>
     * </jp> <en>Return log date.<br>
     * </en>
     * 
     * @return <jp>出力日時 &nbsp;&nbsp;</jp><en>Log date &nbsp;&nbsp;</en>
     */
    public Date getLogDate()
    {
        return logDate;
    }

    /**
     * <jp>メッセージを取得します。<br>
     * </jp> <en>Return message.<br>
     * </en>
     * 
     * @return <jp>メッセージ &nbsp;&nbsp;</jp><en>Message &nbsp;&nbsp;</en>
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * <jp>パスワードを取得します。<br>
     * </jp> <en>Return old password.<br>
     * </en>
     * 
     * @return <jp>パスワード &nbsp;&nbsp;</jp><en>Old password &nbsp;&nbsp;</en>
     */
    public String getOldPassword()
    {
        return oldPassword;
    }

    /**
     * <jp>更新日時を取得します。<br>
     * </jp> <en>gmtDate.<br>
     * </en>
     * 
     * @return <jp>更新日時 &nbsp;&nbsp;</jp><en>gmtDate &nbsp;&nbsp;</en>
     */
    public Date getGmtDate()
    {
        return gmtDate;
    }

    /**
     * <jp>ユーザIDを取得します。<br>
     * </jp> <en>Return user ID.<br>
     * </en>
     * 
     * @return <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * <jp>詳細を設定します。<br>
     * </jp> <en>Sets details.<br>
     * </en>
     * 
     * @param details
     *            <jp>詳細 &nbsp;&nbsp;</jp><en>Details &nbsp;&nbsp;</en>
     */
    public void setDetails(String details)
    {
        this.details = details;
    }


    /**
     * <jp>IPアドレスを設定します。<br>
     * </jp> <en>Sets IP adress.<br>
     * </en>
     * 
     * @param ipAddress
     *            <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP adress &nbsp;&nbsp;</en>
     */
    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    /**
     * <jp>terminalNumber。<br>
     * </jp> <en>Sets terminalNumber.<br>
     * </en>
     * 
     * @param terminalNumber
     *            <jp>terminalNumber &nbsp;&nbsp;</jp><en>terminalNumber &nbsp;&nbsp;</en>
     */
    public void setTerminalNumber(String terminalNumber)
    {
        this.terminalNumber = terminalNumber;
    }

    /**
     * <jp>ログ区分を設定します。<br>
     * </jp> <en>Sets accessType.<br>
     * </en>
     * 
     * @param accessType
     *            <jp>ログ区分 &nbsp;&nbsp;</jp><en>accessType &nbsp;&nbsp;</en>
     */
    public void setAccessType(int accessType)
    {
        this.accessType = accessType;
    }

    /**
     * <jp>出力日時を設定します。<br>
     * </jp> <en>Sets log date.<br>
     * </en>
     * 
     * @param logDate
     *            <jp>出力日時 &nbsp;&nbsp;</jp><en>Log date &nbsp;&nbsp;</en>
     */
    public void setLogDate(Date logDate)
    {
        this.logDate = logDate;
    }

    /**
     * <jp>メッセージを設定します。<br>
     * </jp> <en>Sets message.<br>
     * </en>
     * 
     * @param message
     *            <jp>メッセージ &nbsp;&nbsp;</jp><en>Message &nbsp;&nbsp;</en>
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * <jp>パスワードを設定します。<br>
     * </jp> <en>Sets old password.<br>
     * </en>
     * 
     * @param oldPassword
     *            <jp>パスワード &nbsp;&nbsp;</jp><en>Old password &nbsp;&nbsp;</en>
     */
    public void setOldPassword(String oldPassword)
    {
        this.oldPassword = oldPassword;
    }

    /**
     * <jp>更新日時を設定します。<br>
     * </jp> <en>Sets gmtDate.<br>
     * </en>
     * 
     * @param gmtDate
     *            <jp>更新日時 &nbsp;&nbsp;</jp><en>gmtDate&nbsp;&nbsp;</en>
     */
    public void setGmtDate(Date gmtDate)
    {
        this.gmtDate = gmtDate;
    }

    /**
     * <jp>ユーザIDを設定します。<br>
     * </jp> <en>Sets user ID.<br>
     * </en>
     * 
     * @param userId
     *            <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    /**
     * <jp>画面DS番号を返却します。<br></jp>
     * <en>Returns dnNumebr. <br></en>
     * @return <jp>画面DS番号 &nbsp;&nbsp;</jp><en>dnNumber &nbsp;&nbsp;</en>
     */
    public String getDsNumber()
    {
        return dsNumber;
    }

    /**
     * <jp>画面DS番号を設定します。<br></jp>
     * <en>Set dnNumber . <br></en>
     * @param dsNumber <jp>メ画面DS番号&nbsp;&nbsp;</jp><en>dnNumber &nbsp;&nbsp;</en>
     */
    public void setDsNumber(String dsNumber)
    {
        this.dsNumber = dsNumber;
    }

    /**
     * <jp>ユーザ名を取得します。 <br></jp>
     * <en>Returns the userName. </en>
     * @return <jp>ユーザ名 &nbsp;&nbsp;</jp><en>User name. &nbsp;&nbsp;</en>
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * <jp>ユーザ名を設定します。 <br></jp>
     * <en>Sets userName. </en>
     * @param userName <jp>ユーザ名 &nbsp;&nbsp;</jp><en>User name. &nbsp;&nbsp;</en>
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }


    /**
     * <jp>画面名称を返却します。<br></jp>
     * <en>Returns Page Name. <br></en>
     * @return <jp>画面名 &nbsp;&nbsp;</jp><en>Page Name &nbsp;&nbsp;</en>
     */
    public String getPageName()
    {
        return pageName;
    }


    /**
     * <jp>画面名称を設定します。<br></jp>
     * <en>Set pageName . <br></en>
     * @param pageName <jp>画面名 &nbsp;&nbsp;</jp><en>Page Name &nbsp;&nbsp;</en>
     */
    public void setPageName(String pageName)
    {
        this.pageName = pageName;
    }

    /**
     * <jp>端末名を取得します。<br></jp>
     * <en>Returns the terminalName. <br></en>
     * @return <jp>端末名 &nbsp;&nbsp;</jp><en>Terminal name. &nbsp;&nbsp;</en>
     */
    public String getTerminalName()
    {
        return terminalName;
    }

    /**
     * <jp>端末名を設定します。<br></jp>
     * <en>Sets terminalName. <br></en>
     * @param terminalName <jp>端末名 &nbsp;&nbsp;</jp><en>Terminal name. &nbsp;&nbsp;</en>
     */
    public void setTerminalName(String terminalName)
    {
        this.terminalName = terminalName;
    }

}

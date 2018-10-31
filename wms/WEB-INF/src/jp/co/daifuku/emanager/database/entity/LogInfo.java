// $Id: LogInfo.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <jp>ログ情報に関するエンティティです。<br>
 * </jp> <en> This entity provides Log related information.<br>
 * </en>
 * 
 * @author Muneendra
 */
public class LogInfo
        extends BaseEntity
        implements Serializable
{
    /** <jp>詳細 &nbsp;&nbsp;</jp><en>Details &nbsp;&nbsp;</en> */
    private String details = null;

    /** <jp>取り込みファイル名 &nbsp;&nbsp;</jp><en>Taking file name &nbsp;&nbsp;</en> */
    private String fileName = null;

    // Instance variables
    /** <jp>IPアドレス &nbsp;&nbsp;</jp><en>IP adress &nbsp;&nbsp;</en> */
    private String ipAddress = null;

    /** <jp>terminalNumber  &nbsp;&nbsp;</jp><en>terminalNumber &nbsp;&nbsp;</en> */
    private String terminalNumber = null;


    /** <jp>ログ区分 &nbsp;&nbsp;</jp><en>Log class &nbsp;&nbsp;</en> */
    private int logClass;

    /** <jp>出力日時 &nbsp;&nbsp;</jp><en>Log date &nbsp;&nbsp;</en> */
    private Date logDate = null;

    /** <jp>メッセージ &nbsp;&nbsp;</jp><en>Message &nbsp;&nbsp;</en> */
    private String message = null;

    /** <jp>パスワード &nbsp;&nbsp;</jp><en>Old password &nbsp;&nbsp;</en> */
    private String oldPassword = null;

    /** <jp>更新日時 &nbsp;&nbsp;</jp><en>Update date &nbsp;&nbsp;</en> */
    private Date updateDate = null;

    /** <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en> */
    private String userId = null;

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
     * <jp>取り込みファイル名を取得します。<br>
     * </jp> <en>Return taking file name.<br>
     * </en>
     * 
     * @return <jp>取り込みファイル名 &nbsp;&nbsp;</jp><en>Taking file name
     *         &nbsp;&nbsp;</en>
     */
    public String getFileName()
    {
        return fileName;
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
     * </jp> <en>Return log class.<br>
     * </en>
     * 
     * @return <jp>ログ区分 &nbsp;&nbsp;</jp><en>Log class &nbsp;&nbsp;</en>
     */
    public int getLogClass()
    {
        return logClass;
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
     * </jp> <en>Return update date.<br>
     * </en>
     * 
     * @return <jp>更新日時 &nbsp;&nbsp;</jp><en>Update date &nbsp;&nbsp;</en>
     */
    public Date getUpdateDate()
    {
        return updateDate;
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
     * <jp>取り込みファイル名を設定します。<br>
     * </jp> <en>Sets taking file name.<br>
     * </en>
     * 
     * @param fileName
     *            <jp>取り込みファイル名 &nbsp;&nbsp;</jp><en>Taking file name
     *            &nbsp;&nbsp;</en>
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
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
     * </jp> <en>Sets log class.<br>
     * </en>
     * 
     * @param logClass
     *            <jp>ログ区分 &nbsp;&nbsp;</jp><en>Log class &nbsp;&nbsp;</en>
     */
    public void setLogClass(int logClass)
    {
        this.logClass = logClass;
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
     * </jp> <en>Sets update date.<br>
     * </en>
     * 
     * @param updateDate
     *            <jp>更新日時 &nbsp;&nbsp;</jp><en>Update date &nbsp;&nbsp;</en>
     */
    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
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
     * <jp> エンティティにセットされている文字列を返却します。<br></jp>
     * <en> Return the all entity values as one string. <br></en>
     * @return String
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("--- LogInfo Entity -------------------------------------\n");
        sb.append(" LogDate                    : " + this.logDate + "\n");
        sb.append(" UserId                     : " + this.userId + "\n");
        sb.append(" IpAddress                  : " + this.ipAddress + "\n");
        sb.append(" LogClass                   : " + this.logClass + "\n");
        sb.append(" Message                    : " + this.message + "\n");
        sb.append(" Details                    : " + this.details + "\n");
        sb.append(" FileName                   : " + this.fileName + "\n");
        sb.append(super.toAbstractString());
        sb.append("--------------------------------------------------------\n");

        return sb.toString();
    }
}

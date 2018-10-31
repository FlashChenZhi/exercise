// $Id: User.java 3965 2009-04-06 02:55:05Z admin $
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
 * <jp>ユーザIDやパスワードなどのユーザアクセス情報のエンティティです。<br>
 * </jp> <en> This entity provides User access information like user id,
 * passwofrd etc.<br>
 * </en>
 * 
 * @author Muneendra
 */
public class User
        extends BaseEntity
        implements Serializable, Cloneable
{

    // Instance variables
    /** <jp>仮パスワードフラグ &nbsp;&nbsp;</jp><en>Dummy password flag &nbsp;&nbsp;</en> */
    private boolean dummyPassFlag;

    /** <jp>認証ミス回数 &nbsp;&nbsp;</jp><en>Failed login count &nbsp;&nbsp;</en> */
    private int failedCount;

    /** <jp>最終認証ミス日時 &nbsp;&nbsp;</jp><en>Failed start date &nbsp;&nbsp;</en> */
    private Date failedStartDate = null;

    /** <jp>認証ミス猶予回数 &nbsp;&nbsp;</jp><en>Failed login attempts &nbsp;&nbsp;</en> */
    private int failLoginAttem;

    /** <jp>最終ログイン日時 &nbsp;&nbsp;</jp><en>Last access date &nbsp;&nbsp;</en> */
    private Date lastAccessDate = null;

    /** <jp>パスワード &nbsp;&nbsp;</jp><en>The password &nbsp;&nbsp;</en> */
    private String password = null;

    /**
     * <jp>パスワード有効期間日数 &nbsp;&nbsp;</jp><en>Password change interval period
     * &nbsp;&nbsp;</en>
     */
    private int pwdChangeInterval;

    /** <jp>パスワード有効期限 &nbsp;&nbsp;</jp><en>Password expire period &nbsp;&nbsp;</en> */
    private Date pwdExpire = null;

    /** <jp>ロールID &nbsp;&nbsp;</jp><en>Role ID &nbsp;&nbsp;</en> */
    private String roleID = null;

    /** <jp>同一ユーザログイン最大数 &nbsp;&nbsp;</jp><en>Same user login max &nbsp;&nbsp;</en> */
    private int sameUserLoginMax;

    /** <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en> */
    private String userID = null;
// 2008/12/08 K.Matsuda Start 
    // ユーザ状態に無効が追加のため、intに変更
    // テーブル列名変更に伴い名称変更
    /** <jp>ユーザ状態 &nbsp;&nbsp;</jp><en>User Status &nbsp;&nbsp;</en> */
    private int userStatus;
// 2008/12/08 K.Matsuda End
    /** <jp>ユーザ名 &nbsp;&nbsp;</jp><en>User name &nbsp;&nbsp;</en> */
    private String userName = null;

    /** <jp>所属 &nbsp;&nbsp;</jp><en>Department &nbsp;&nbsp;</en> */
    private String department = null;

    /** <jp>備考 &nbsp;&nbsp;</jp><en>Remarks &nbsp;&nbsp;</en> */
    private String remarks = null;

    /** <jp>削除ステータス &nbsp;&nbsp;</jp><en>User Delete status&nbsp;&nbsp;</en> */
    private int deleteStatus;

    /** <jp>削除日付 &nbsp;&nbsp;</jp><en>Delete date &nbsp;&nbsp;</en> */
    private Date deleteDate = null;


    /**
     * <jp>仮パスワードフラグを取得します。<br>
     * </jp> <en>Return dummy password flag.<br>
     * </en>
     * 
     * @return <jp>仮パスワードフラグ &nbsp;&nbsp;</jp><en>Dummy password flag
     *         &nbsp;&nbsp;</en>
     */
    public boolean getDummyPassFlag()
    {
        return dummyPassFlag;
    }

    /**
     * <jp>認証ミス回数を取得します。<br>
     * </jp> <en>Return failed login count.<br>
     * </en>
     * 
     * @return <jp>認証ミス回数 &nbsp;&nbsp;</jp><en>Failed login count &nbsp;&nbsp;</en>
     */
    public int getFailedCount()
    {
        return failedCount;
    }

    /**
     * <jp>最終認証ミス日時を取得します。<br>
     * </jp> <en>Return failed start date.<br>
     * </en>
     * 
     * @return <jp>最終認証ミス日時 &nbsp;&nbsp;</jp><en>Failed start date
     *         &nbsp;&nbsp;</en>
     */
    public Date getFailedStartDate()
    {
        return failedStartDate;
    }

    /**
     * <jp>認証ミス猶予回数を取得します。<br>
     * </jp> <en>Return failed login attempts.<br>
     * </en>
     * 
     * @return <jp>認証ミス猶予回数 &nbsp;&nbsp;</jp><en>Failed login attempts
     *         &nbsp;&nbsp;</en>
     */
    public int getFailLoginAttem()
    {
        return failLoginAttem;
    }

    /**
     * <jp>最終ログイン日時を取得します。<br>
     * </jp> <en>Return last access date.<br>
     * </en>
     * 
     * @return <jp>最終ログイン日時 &nbsp;&nbsp;</jp><en>Last access date &nbsp;&nbsp;</en>
     */
    public Date getLastAccessDate()
    {
        return lastAccessDate;
    }

    /**
     * <jp>パスワードを取得します。<br>
     * </jp> <en>Return the password.<br>
     * </en>
     * 
     * @return <jp>パスワード &nbsp;&nbsp;</jp><en>The password &nbsp;&nbsp;</en>
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * <jp>パスワード有効期間日数を取得します。<br>
     * </jp> <en>Return password change interval period.<br>
     * </en>
     * 
     * @return <jp>パスワード有効期間日数 &nbsp;&nbsp;</jp><en>Password change interval
     *         period &nbsp;&nbsp;</en>
     */
    public int getPwdChangeInterval()
    {
        return pwdChangeInterval;
    }

    /**
     * <jp>パスワード有効期限を取得します。<br>
     * </jp> <en>Return password expire period.<br>
     * </en>
     * 
     * @return <jp>パスワード有効期限 &nbsp;&nbsp;</jp><en>Password expire period
     *         &nbsp;&nbsp;</en>
     */
    public Date getPwdExpire()
    {
        return pwdExpire;
    }

    /**
     * <jp>ロールIDを取得します。<br>
     * </jp> <en>Return role ID.<br>
     * </en>
     * 
     * @return <jp>ロールID &nbsp;&nbsp;</jp><en>Role ID &nbsp;&nbsp;</en>
     */
    public String getRoleID()
    {
        return roleID;
    }

    /**
     * <jp>同一ユーザログイン最大数を取得します。<br>
     * </jp> <en>Return same user login max.<br>
     * </en>
     * 
     * @return <jp>同一ユーザログイン最大数 &nbsp;&nbsp;</jp><en>Same user login max
     *         &nbsp;&nbsp;</en>
     */
    public int getSameUserLoginMax()
    {
        return sameUserLoginMax;
    }

    /**
     * <jp>ユーザIDを取得します。<br>
     * </jp> <en>Return user ID.<br>
     * </en>
     * 
     * @return <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     */
    public String getUserID()
    {
        return userID;
    }

// 2008/12/08 K.Matsuda Start 
    // ユーザ状態に無効が追加のため、intに変更
    // テーブル列名変更に伴い名称変更
    /**
     * <jp>ユーザ状態を取得します。<br>
     * </jp> <en>Return user status.<br>
     * </en>
     * 
     * @return <jp>ユーザ状態 &nbsp;&nbsp;</jp><en>User status &nbsp;&nbsp;</en>
     */
    public int getUserStatus()
    {
        return userStatus;
    }
// 2008/12/08 K.Matsuda End
    
    /**
     * <jp>ユーザ名を取得します。<br>
     * </jp> <en>Return user name.<br>
     * </en>
     * 
     * @return <jp>ユーザ名 &nbsp;&nbsp;</jp><en>User name &nbsp;&nbsp;</en>
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * <jp>仮パスワードフラグを設定します。<br>
     * </jp> <en>Sets dummy password flag.<br>
     * </en>
     * 
     * @param dummyPassFlag
     *            <jp>仮パスワードフラグ &nbsp;&nbsp;</jp><en>Dummy password flag
     *            &nbsp;&nbsp;</en>
     */
    public void setDummyPassFlag(boolean dummyPassFlag)
    {
        this.dummyPassFlag = dummyPassFlag;
    }

    /**
     * <jp>認証ミス回数を設定します。<br>
     * </jp> <en>Sets failed login count.<br>
     * </en>
     * 
     * @param failedCount
     *            <jp>認証ミス回数 &nbsp;&nbsp;</jp><en>Failed login count
     *            &nbsp;&nbsp;</en>
     */
    public void setFailedCount(int failedCount)
    {
        this.failedCount = failedCount;
    }

    /**
     * <jp>最終認証ミス日時を設定します。<br>
     * </jp> <en>Sets failed start date.<br>
     * </en>
     * 
     * @param failedStartDate
     *            <jp>最終認証ミス日時 &nbsp;&nbsp;</jp><en>Failed start date
     *            &nbsp;&nbsp;</en>
     */
    public void setFailedStartDate(Date failedStartDate)
    {
        this.failedStartDate = failedStartDate;
    }

    /**
     * <jp>認証ミス猶予回数を設定します。<br>
     * </jp> <en>Sets failed login attempts.<br>
     * </en>
     * 
     * @param failLoginAttem
     *            <jp>認証ミス猶予回数 &nbsp;&nbsp;</jp><en>Failed login attempts
     *            &nbsp;&nbsp;</en>
     */
    public void setFailLoginAttem(int failLoginAttem)
    {
        this.failLoginAttem = failLoginAttem;
    }

    /**
     * <jp>最終ログイン日時を設定します。<br>
     * </jp> <en>Sets last access date.<br>
     * </en>
     * 
     * @param lastAccessDate
     *            <jp>最終ログイン日時 &nbsp;&nbsp;</jp><en>Last access date
     *            &nbsp;&nbsp;</en>
     */
    public void setLastAccessDate(Date lastAccessDate)
    {
        this.lastAccessDate = lastAccessDate;
    }

    /**
     * <jp>パスワードを設定します。<br>
     * </jp> <en>Sets the password.<br>
     * </en>
     * 
     * @param password
     *            <jp>パスワード &nbsp;&nbsp;</jp><en>The password &nbsp;&nbsp;</en>
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * <jp>パスワード有効期間日数を設定します。<br>
     * </jp> <en>Sets password change interval period.<br>
     * </en>
     * 
     * @param pwdChangeInterval
     *            <jp>パスワード有効期間日数 &nbsp;&nbsp;</jp><en>Password change
     *            interval period &nbsp;&nbsp;</en>
     */
    public void setPwdChangeInterval(int pwdChangeInterval)
    {
        this.pwdChangeInterval = pwdChangeInterval;
    }

    /**
     * <jp>パスワード有効期限を設定します。<br>
     * </jp> <en>Sets password expire period.<br>
     * </en>
     * 
     * @param pwdExpire
     *            <jp>パスワード有効期限 &nbsp;&nbsp;</jp><en>Password expire period
     *            &nbsp;&nbsp;</en>
     */
    public void setPwdExpire(Date pwdExpire)
    {
        this.pwdExpire = pwdExpire;
    }

    /**
     * <jp>ロールIDを設定します。<br>
     * </jp> <en>Sets role ID.<br>
     * </en>
     * 
     * @param roleID
     *            <jp>ロールID &nbsp;&nbsp;</jp><en>Role ID &nbsp;&nbsp;</en>
     */
    public void setRoleID(String roleID)
    {
        this.roleID = roleID;
    }

    /**
     * <jp>同一ユーザログイン最大数を設定します。<br>
     * </jp> <en>Sets same user login max.<br>
     * </en>
     * 
     * @param sameUserLoginMax
     *            <jp>同一ユーザログイン最大数 &nbsp;&nbsp;</jp><en>Same user login max
     *            &nbsp;&nbsp;</en>
     */
    public void setSameUserLoginMax(int sameUserLoginMax)
    {
        this.sameUserLoginMax = sameUserLoginMax;
    }

    /**
     * <jp>ユーザIDを設定します。<br>
     * </jp> <en>Sets user ID.<br>
     * </en>
     * 
     * @param userID
     *            <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     */
    public void setUserID(String userID)
    {
        this.userID = userID;
    }

// 2008/12/08 K.Matsuda Start 
    // ユーザ状態に無効が追加のため、intに変更
    // テーブル列名変更に伴い名称変更
    /**
     * <jp>ユーザ状態を設定します。<br>
     *     (1:有効、2:無効、3:操作ロック)
     * </jp> <en>Sets user status.<br>
     * </en>
     * @param userStatus
     *            <jp>ユーザロックフラグ &nbsp;&nbsp;</jp>
     *            <en>User lock flag     &nbsp;&nbsp;</en>
     */
    public void setUserStatus(int userStatus)
    {
        this.userStatus = userStatus;
    }
// 2008/12/08 K.Matsuda End

    /**
     * <jp>ユーザ名を設定します。<br>
     * </jp> <en>Sets user name.<br>
     * </en>
     * 
     * @param userName
     *            <jp>ユーザ名 &nbsp;&nbsp;</jp><en>User name &nbsp;&nbsp;</en>
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * <jp>所属を取得します。<br>
     * </jp> <en>Return department.<br>
     * </en>
     * 
     * @return <jp>所属 &nbsp;&nbsp;</jp><en>Department &nbsp;&nbsp;</en>
     */
    public String getDepartment()
    {
        return department;
    }

    /**
     * <jp>備考を取得します。<br>
     * </jp> <en>Return remarks.<br>
     * </en>
     * 
     * @return <jp>備考 &nbsp;&nbsp;</jp><en>Remarks &nbsp;&nbsp;</en>
     */
    public String getRemarks()
    {
        return remarks;
    }


    /**
     * <jp>所属を設定します。<br>
     * </jp> <en>Sets department.<br>
     * </en>
     * 
     * @param department
     *            <jp>所属 &nbsp;&nbsp;</jp><en>Department &nbsp;&nbsp;</en>
     */
    public void setDepartment(String department)
    {
        this.department = department;
    }

    /**
     * <jp>備考を設定します。<br>
     * </jp> <en>Sets remarks.<br>
     * </en>
     * 
     * @param remarks
     *            <jp>備考 &nbsp;&nbsp;</jp><en>Remarks &nbsp;&nbsp;</en>
     */
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    /**
     * <jp><br>
     * </jp> <en>Return deleteDate.<br>
     * </en> 
     * @return <jp> &nbsp;&nbsp;</jp><en>deleteDate &nbsp;&nbsp;</en>
     */
    public Date getDeleteDate()
    {
        return deleteDate;
    }

    /**
     * <jp><br>
     * </jp> <en>Set delete date<br>
     * </en>
     * 
     * @param deleteDate
     *            <jp>備考 &nbsp;&nbsp;</jp><en>deleteDate &nbsp;&nbsp;</en>
     */
    public void setDeleteDate(Date deleteDate)
    {
        this.deleteDate = deleteDate;
    }

    /**
     * <jp><br>
     * </jp> <en>Return deleteStatus.<br>
     * </en>
     * 
     * @return <jp>deleteStatus &nbsp;&nbsp;</jp><en>deleteStatus &nbsp;&nbsp;</en>
     */
    public int getDeleteStatus()
    {
        return deleteStatus;
    }

    /**
     * <jp>。<br>
     * </jp> <en>Sets deleteStatus.<br>
     * </en>
     * 
     * @param deleteStatus
     *            <jp>&nbsp;&nbsp;</jp><en>deleteStatus &nbsp;&nbsp;</en>
     */
    public void setDeleteStatus(int deleteStatus)
    {
        this.deleteStatus = deleteStatus;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public Object clone()
            throws CloneNotSupportedException
    {
        return super.clone();
    }

    /**
     * <jp> エンティティにセットされている文字列を返却します。<br></jp>
     * <en> Return the all entity values as one string. <br></en>
     * @return String
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("--- User Entity ----------------------------------------\n");
        sb.append(" UserId                     : " + this.userID + "\n");
        sb.append(" Password                   : " + this.password + "\n");
        sb.append(" UserName                   : " + this.userName + "\n");
        sb.append(" RoleId                     : " + this.roleID + "\n");
        sb.append(" DummyPasswordFlag          : " + this.dummyPassFlag + "\n");
// 2008/12/08 K.Matsuda Start 名称変更
        sb.append(" UserStatus                 : " + this.userStatus + "\n");
// 2008/12/08 K.Matsuda End 
        sb.append(" PwdExpires                 : " + this.pwdExpire + "\n");
        sb.append(" PasswordChangeInterval     : " + this.pwdChangeInterval + "\n");
        sb.append(" LastAccessDate             : " + this.lastAccessDate + "\n");
        sb.append(" SameUserLoginMax           : " + this.sameUserLoginMax + "\n");
        sb.append(" FaieldLoginAttempts        : " + this.failLoginAttem + "\n");
        sb.append(" FailedCount                : " + this.failedCount + "\n");
        sb.append(" FailedStartDate            : " + this.failedStartDate + "\n");
        sb.append(" Deaprtment                 : " + this.department + "\n");
        sb.append(" Remarks		               : " + this.remarks + "\n");
        sb.append(" Delete Status              : " + this.deleteStatus + "\n");
        sb.append(" Delete Date		           : " + this.deleteDate + "\n");
        sb.append(super.toAbstractString());
        sb.append("--------------------------------------------------------\n");

        return sb.toString();
    }


}

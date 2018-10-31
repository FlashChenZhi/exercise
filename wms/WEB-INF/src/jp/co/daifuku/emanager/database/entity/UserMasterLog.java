// $Id: UserMasterLog.java 3965 2009-04-06 02:55:05Z admin $
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
 * <jp>マスタ改廃ログ情報に関するエンティティです。<br>
 * </jp> <en> This entity provides Log related information.<br>
 * </en>
 * 
 * @author 
 */
public class UserMasterLog
        extends LogBaseEntity
        implements Serializable
{


    /** <jp>更新区分 &nbsp;&nbsp;</jp><en>Update Kind &nbsp;&nbsp;</en> */
    private int updateKind;

    /** <jp>修正前ユーザ名 &nbsp;&nbsp;</jp><en>User Name Before &nbsp;&nbsp;</en> */
    private String userNameBefore;

    /** <jp>修正前パスワード有効期間日数 &nbsp;&nbsp;</jp><en>Password Change Interval Before &nbsp;&nbsp;</en> */
    private int passwordChangeIntervalBefore;

    /** <jp>修正前パスワード有効期限 &nbsp;&nbsp;</jp><en>Ped Expires Before &nbsp;&nbsp;</en> */
    private Date pwdExpiresBefore;

// 2009/01/20 K.Matsuda Start 列名変更(USERLOCK_FLAG -> USERSTATUS)
    /** <jp>修正前ユーザ状態 &nbsp;&nbsp;</jp><en>User State Before &nbsp;&nbsp;</en> */
    private int userStatusBefore;
// 2009/01/20 K.Matsuda End

    /** <jp>修正前同一ユーザログイン最大数 &nbsp;&nbsp;</jp><en>Same User Login Max Before &nbsp;&nbsp;</en> */
    private int sameUserLoginMaxBefore;

    /** <jp>修正前ミス猶予回数 &nbsp;&nbsp;</jp><en>Failed Login Attemps Before &nbsp;&nbsp;</en> */
    private int failedLoginAttemptsBefore;

    /** <jp>修正前ロールID &nbsp;&nbsp;</jp><en>Role Id Before &nbsp;&nbsp;</en> */
    private String roleIdBefore;

    /** <jp>修正前所属 &nbsp;&nbsp;</jp><en>Department Before &nbsp;&nbsp;</en> */
    private String departmentBefore;

    /** <jp>修正前備考 &nbsp;&nbsp;</jp><en>Remark Before &nbsp;&nbsp;</en> */
    private String remarkBefore;

    /** <jp>修正後ユーザ名 &nbsp;&nbsp;</jp><en>User Name After &nbsp;&nbsp;</en> */
    private String userNameAfter;

    /** <jp>修正後パスワード有効期間日数 &nbsp;&nbsp;</jp><en>Password Change Interval After &nbsp;&nbsp;</en> */
    private int passwordChangeIntervalAfter;

    /** <jp>修正後パスワード有効期限 &nbsp;&nbsp;</jp><en>Pwd Expires After &nbsp;&nbsp;</en> */
    private Date pwdExpiresAfter;

// 2009/01/20 K.Matsuda Start 列名変更(USERLOCK_FLAG -> USERSTATUS)
    /** <jp>修正後ユーザ状態 &nbsp;&nbsp;</jp><en>User State After &nbsp;&nbsp;</en> */
    private int userStatusAfter;
// 2009/01/20 K.Matsuda End

    /** <jp>修正後同一ユーザログイン最大数 &nbsp;&nbsp;</jp><en>Same User Login Max After &nbsp;&nbsp;</en> */
    private int sameUserLoginMaxAfter;

    /** <jp>修正後ミス猶予回数 &nbsp;&nbsp;</jp><en>Failed Login Attempts After &nbsp;&nbsp;</en> */
    private int failedLoginAttemptsAfter;

    /** <jp>修正後ロールID &nbsp;&nbsp;</jp><en>User Name &nbsp;&nbsp;</en> */
    private String roleIdAfter;

    /** <jp>修正後所属 &nbsp;&nbsp;</jp><en>Department After &nbsp;&nbsp;</en> */
    private String departmentAfter;

    /** <jp>修正後備考 &nbsp;&nbsp;</jp><en>Remark After &nbsp;&nbsp;</en> */
    private String remarkAfter;

    /** <jp>masterUserId &nbsp;&nbsp;</jp><en>Master User id &nbsp;&nbsp;</en> */
    private String masterUserId;

    /** <jp>修正前パスワード &nbsp;&nbsp;</jp><en>PassWord Before &nbsp;&nbsp;</en> */
    private String passwordBefore;

    /** <jp>修正後パスワード &nbsp;&nbsp;</jp><en>PassWord After &nbsp;&nbsp;</en> */
    private String passwordAfter;

    /**
     * updateKindを返します。
     * @return updateKindを返します。
     */
    public int getUpdateKind()
    {
        return updateKind;
    }

    /**
     * updateKindを設定します。
     * @param updateKind updateKind
     */
    public void setUpdateKind(int updateKind)
    {
        this.updateKind = updateKind;
    }

    /**
     * userNameBeforeを返します。
     * @return userNameBeforeを返します。
     */
    public String getUserNameBefore()
    {
        return userNameBefore;
    }

    /**
     * userNameBeforeを設定します。
     * @param userNameBefore userNameBefore
     */
    public void setUserNameBefore(String userNameBefore)
    {
        this.userNameBefore = userNameBefore;
    }

    /**
     * passwordChangeIntervalBeforeを返します。
     * @return passwordChangeIntervalBeforeを返します。
     */
    public int getPasswordChangeIntervalBefore()
    {
        return passwordChangeIntervalBefore;
    }

    /**
     * passwordChangeIntervalBeforeを設定します。
     * @param passwordChangeIntervalBefore passwordChangeIntervalBefore
     */
    public void setPasswordChangeIntervalBefore(int passwordChangeIntervalBefore)
    {
        this.passwordChangeIntervalBefore = passwordChangeIntervalBefore;
    }

    /**
     * pwdExpiresBeforeを返します。
     * @return pwdExpiresBeforeを返します。
     */
    public Date getPwdExpiresBefore()
    {
        return pwdExpiresBefore;
    }

    /**
     * pwdExpiresBeforeを設定します。
     * @param pwdExpiresBefore pwdExpiresBefore
     */
    public void setPwdExpiresBefore(Date pwdExpiresBefore)
    {
        this.pwdExpiresBefore = pwdExpiresBefore;
    }

// 2009/01/20 K.Matsuda Start 列名変更(USERLOCK_FLAG -> USERSTATUS)
    /**
     * userStatusBeforeを返します。
     * @return userStatusBeforeを返します。
     */
    public int getUserStatusBefore()
    {
        return userStatusBefore;
    }

    /**
     * userStatusBeforeを設定します。
     * @param userStatusBefore userStatusBefore
     */
    public void setUserStatusBefore(int userStatusBefore)
    {
        this.userStatusBefore = userStatusBefore;
    }
// 2009/01/20 K.Matsuda End

    /**
     * sameUserLoginMaxBeforeを返します。
     * @return sameUserLoginMaxBeforeを返します。
     */
    public int getSameUserLoginMaxBefore()
    {
        return sameUserLoginMaxBefore;
    }

    /**
     * sameUserLoginMaxBeforeを設定します。
     * @param sameUserLoginMaxBefore sameUserLoginMaxBefore
     */
    public void setSameUserLoginMaxBefore(int sameUserLoginMaxBefore)
    {
        this.sameUserLoginMaxBefore = sameUserLoginMaxBefore;
    }

    /**
     * failedLoginAttempsBeforeを返します。
     * @return failedLoginAttempsBeforeを返します。
     */
    public int getFailedLoginAttemptsBefore()
    {
        return failedLoginAttemptsBefore;
    }

    /**
     * failedLoginAttemptsBeforeを設定します。
     * @param failedLoginAttemptsBefore failedLoginAttemptsBefore
     */
    public void setFailedLoginAttemptsBefore(int failedLoginAttemptsBefore)
    {
        this.failedLoginAttemptsBefore = failedLoginAttemptsBefore;
    }

    /**
     * roleIdBeforeを返します。
     * @return roleIdBeforeを返します。
     */
    public String getRoleIdBefore()
    {
        return roleIdBefore;
    }

    /**
     * roleIdBeforeを設定します。
     * @param roleIdBefore roleIdBefore
     */
    public void setRoleIdBefore(String roleIdBefore)
    {
        this.roleIdBefore = roleIdBefore;
    }

    /**
     * departmentBeforeを返します。
     * @return departmentBeforeを返します。
     */
    public String getDepartmentBefore()
    {
        return departmentBefore;
    }

    /**
     * departmentBeforeを設定します。
     * @param departmentBefore departmentBefore
     */
    public void setDepartmentBefore(String departmentBefore)
    {
        this.departmentBefore = departmentBefore;
    }

    /**
     * remarkBeforeを返します。
     * @return remarkBeforeを返します。
     */
    public String getRemarkBefore()
    {
        return remarkBefore;
    }

    /**
     * remarkBeforeを設定します。
     * @param remarkBefore remarkBefore
     */
    public void setRemarkBefore(String remarkBefore)
    {
        this.remarkBefore = remarkBefore;
    }

    /**
     * userNameAfterを返します。
     * @return userNameAfterを返します。
     */
    public String getUserNameAfter()
    {
        return userNameAfter;
    }

    /**
     * userNameAfterを設定します。
     * @param userNameAfter userNameAfter
     */
    public void setUserNameAfter(String userNameAfter)
    {
        this.userNameAfter = userNameAfter;
    }

    /**
     * passwordChangeIntervalAfterを返します。
     * @return passwordChangeIntervalAfterを返します。
     */
    public int getPasswordChangeIntervalAfter()
    {
        return passwordChangeIntervalAfter;
    }

    /**
     * passwordChangeIntervalAfterを設定します。
     * @param passwordChangeIntervalAfter passwordChangeIntervalAfter
     */
    public void setPasswordChangeIntervalAfter(int passwordChangeIntervalAfter)
    {
        this.passwordChangeIntervalAfter = passwordChangeIntervalAfter;
    }

    /**
     * pwdExpiresAfterを返します。
     * @return pwdExpiresAfterを返します。
     */
    public Date getPwdExpiresAfter()
    {
        return pwdExpiresAfter;
    }

    /**
     * pwdExpiresAfterを設定します。
     * @param pwdExpiresAfter pwdExpiresAfter
     */
    public void setPwdExpiresAfter(Date pwdExpiresAfter)
    {
        this.pwdExpiresAfter = pwdExpiresAfter;
    }

// 2009/01/20 K.Matsuda Start 列名変更(USERLOCK_FLAG -> USERSTATUS)
    /**
     * userStatusAfterを返します。
     * @return userStatusAfterを返します。
     */
    public int getUserStatusAfter()
    {
        return userStatusAfter;
    }

    /**
     * userStatusAfterを設定します。
     * @param userStatusAfter userStatusAfter
     */
    public void setUserStatusAfter(int userStatusAfter)
    {
        this.userStatusAfter = userStatusAfter;
    }
// 2009/01/20 K.Matsuda End

    /**
     * sameUserLoginMaxAfterを返します。
     * @return sameUserLoginMaxAfterを返します。
     */
    public int getSameUserLoginMaxAfter()
    {
        return sameUserLoginMaxAfter;
    }

    /**
     * sameUserLoginMaxAfterを設定します。
     * @param sameUserLoginMaxAfter sameUserLoginMaxAfter
     */
    public void setSameUserLoginMaxAfter(int sameUserLoginMaxAfter)
    {
        this.sameUserLoginMaxAfter = sameUserLoginMaxAfter;
    }

    /**
     * failedLoginNatTemptsAfterを返します。
     * @return failedLoginNatTemptsAfterを返します。
     */
    public int getFailedLoginAttemptsAfter()
    {
        return failedLoginAttemptsAfter;
    }

    /**
     * failedLoginAttemptsAfterを設定します。
     * @param failedLoginAttemptsAfter failedLoginAttemptsAfter
     */
    public void setFailedLoginAttemptsAfter(int failedLoginAttemptsAfter)
    {
        this.failedLoginAttemptsAfter = failedLoginAttemptsAfter;
    }

    /**
     * roleIdAfterを返します。
     * @return roleIdAfterを返します。
     */
    public String getRoleIdAfter()
    {
        return roleIdAfter;
    }

    /**
     * roleIdAfterを設定します。
     * @param roleIdAfter roleIdAfter
     */
    public void setRoleIdAfter(String roleIdAfter)
    {
        this.roleIdAfter = roleIdAfter;
    }

    /**
     * departmentBeforeを返します。
     * @return departmentBeforeを返します。
     */
    public String getDepartmentAfter()
    {
        return departmentAfter;
    }

    /**
     * departmentAfterを設定します。
     * @param departmentAfter departmentAfter
     */
    public void setDepartmentAfter(String departmentAfter)
    {
        this.departmentAfter = departmentAfter;
    }

    /**
     * remarkAfterを返します。
     * @return remarkBeforeを返します。
     */
    public String getRemarkAfter()
    {
        return remarkAfter;
    }

    /**
     * remarkAfterを設定します。
     * @param remarkAfter remarkAfter
     */
    public void setRemarkAfter(String remarkAfter)
    {
        this.remarkAfter = remarkAfter;
    }

    /**
     * マスタユーザIDを返します。
     * @return マスタユーザID
     */
    public String getMasterUserId()
    {
        return masterUserId;
    }

    /**
     * マスタユーザIDを設定します。
     * @param masterUserId マスタユーザID
     */
    public void setMasterUserId(String masterUserId)
    {
        this.masterUserId = masterUserId;
    }

    /**
     * passwordBeforeを返します。
     * @return passwordBeforeを返します。
     */
    public String getPasswordBefore()
    {
        return passwordBefore;
    }

    /**
     * passwordBeforeを設定します。
     * @param passwordBefore passwordBefore
     */
    public void setPasswordBefore(String passwordBefore)
    {
        this.passwordBefore = passwordBefore;
    }

    /**
     * passwordAfterを返します。
     * @return passwordAfterを返します。
     */
    public String getPasswordAfter()
    {
        return passwordAfter;
    }

    /**
     * passwordAfterを設定します。
     * @param passwordAfter passwordAfter
     */
    public void setPasswordAfter(String passwordAfter)
    {
        this.passwordAfter = passwordAfter;
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
        sb.append(" LogDate                      : " + this.getLogDate() + "\n");
        sb.append(" GMTDate                      : " + this.getGmtDate() + "\n");
        sb.append(" UserId                       : " + this.getUserId() + "\n");
        sb.append(" UserName                     : " + this.getUserName() + "\n");
        sb.append(" TerminalNumber               : " + this.getTerminalNumber() + "\n");
        sb.append(" TerminalName                 : " + this.getTerminalName() + "\n");
        sb.append(" IpAddress                    : " + this.getIpAddress() + "\n");
        sb.append(" DSNumber                     : " + this.getDsNumber() + "\n");
        sb.append(" PageNameResourceKey          : " + this.getPageName() + "\n");
        sb.append(" UpdateKind                   : " + this.updateKind + "\n");
        sb.append(" Master User id               : " + this.masterUserId + "\n");
        sb.append(" UserNameBefore               : " + this.userNameBefore + "\n");
        sb.append(" PassWordBefore               : " + this.passwordBefore + "\n");
        sb.append(" PasswordChangeIntervalBefore : " + this.passwordChangeIntervalBefore + "\n");
        sb.append(" PwdExpiresBefore             : " + this.pwdExpiresBefore + "\n");
// 2009/01/20 K.Matsuda Start 列名変更(USERLOCK_FLAG -> USERSTATUS)
        sb.append(" UserStatusBefore             : " + this.userStatusBefore + "\n");
// 2009/01/20 K.Matsuda End
        sb.append(" SameUserLoginMaxBefore       : " + this.sameUserLoginMaxBefore + "\n");
        sb.append(" FailedLoginAttempsBefore     : " + this.failedLoginAttemptsBefore + "\n");
        sb.append(" RoleIdBefore                 : " + this.roleIdBefore + "\n");
        sb.append(" DepartmentBefore             : " + this.departmentBefore + "\n");
        sb.append(" RemarkBefore                 : " + this.remarkBefore + "\n");
        sb.append(" UserNameAfter                : " + this.userNameAfter + "\n");
        sb.append(" PassWordAfter                : " + this.passwordAfter + "\n");
        sb.append(" PasswordChangeIntervalAfter  : " + this.passwordChangeIntervalAfter + "\n");
        sb.append(" PwdExpiresAfter              : " + this.pwdExpiresAfter + "\n");
// 2009/01/20 K.Matsuda Start 列名変更(USERLOCK_FLAG -> USERSTATUS)
        sb.append(" UserStatusAfter              : " + this.userStatusAfter + "\n");
// 2009/01/20 K.Matsuda End
        sb.append(" SameUserLoginMaxAfter        : " + this.sameUserLoginMaxAfter + "\n");
        sb.append(" FailedLoginAttempsAfter      : " + this.failedLoginAttemptsAfter + "\n");
        sb.append(" RoleIdAfter                  : " + this.roleIdAfter + "\n");
        sb.append(" DepartmentAfter              : " + this.departmentAfter + "\n");
        sb.append(" RemarkAfter                  : " + this.remarkAfter + "\n");

        //sb.append(super.toAbstractString());
        sb.append("--------------------------------------------------------\n");

        return sb.toString();
    }

}

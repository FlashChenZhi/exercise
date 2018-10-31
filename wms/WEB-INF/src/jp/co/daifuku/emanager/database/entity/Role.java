// $Id: Role.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.entity;

import java.io.Serializable;

/**
 * <jp>ロールに関する情報のエンティティです。<br>
 * </jp> <en> This entity provides role related information.<br>
 * </en>
 * 
 * @author Muneendra
 */
public class Role
        extends BaseEntity
        implements Serializable
{

    /** <jp>認証ミス猶予回数 &nbsp;&nbsp;</jp><en>Failed login attempts &nbsp;&nbsp;</en> */
    private int failLoginAttem;

    /** <jp>機能画面ID &nbsp;&nbsp;</jp><en>Function ID &nbsp;&nbsp;</en> */
    private String functionId = null;

    /**
     * <jp>パスワード有効期間日数 &nbsp;&nbsp;</jp><en>Password change interval
     * &nbsp;&nbsp;</en>
     */
    private int passChangeInterval;

    // Instance variables
    /** <jp>ロールID &nbsp;&nbsp;</jp><en>Role ID &nbsp;&nbsp;</en> */
    private String roleID = null;

    /** <jp>ロール名称 &nbsp;&nbsp;</jp><en>Role name &nbsp;&nbsp;</en> */
    private String roleName = null;

    /** <jp>Target &nbsp;&nbsp;</jp><en>Target &nbsp;&nbsp;</en> */
    private int target;

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
     * <jp>機能画面IDを取得します。<br>
     * </jp> <en>Return function ID.<br>
     * </en>
     * 
     * @return <jp>機能画面ID &nbsp;&nbsp;</jp><en>Function ID &nbsp;&nbsp;</en>
     */
    public String getFunctionId()
    {
        return functionId;
    }

    /**
     * <jp>パスワード有効期間日数を取得します。<br>
     * </jp> <en>Return password change interval.<br>
     * </en>
     * 
     * @return <jp>パスワード有効期間日数 &nbsp;&nbsp;</jp><en>Password change interval
     *         &nbsp;&nbsp;</en>
     */
    public int getPassChangeInterval()
    {
        return passChangeInterval;
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
     * <jp>ロール名称を取得します。<br>
     * </jp> <en>Return role name.<br>
     * </en>
     * 
     * @return <jp>ロール名称 &nbsp;&nbsp;</jp><en>Role name &nbsp;&nbsp;</en>
     */
    public String getRoleName()
    {
        return roleName;
    }

    /**
     * <jp><br>
     * </jp> <en>Returns the target users.<br>
     * </en>
     * 
     * @return <jp>&nbsp;&nbsp;</jp><en>target &nbsp;&nbsp;</en>
     */
    public int getTarget()
    {
        return target;
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
     * <jp>機能画面IDを設定します。<br>
     * </jp> <en>Sets function ID.<br>
     * </en>
     * 
     * @param functionId
     *            <jp>機能画面ID &nbsp;&nbsp;</jp><en>Function ID &nbsp;&nbsp;</en>
     */
    public void setFunctionId(String functionId)
    {
        this.functionId = functionId;
    }

    /**
     * <jp>パスワード有効期間日数を設定します。<br>
     * </jp> <en>Sets password change interval.<br>
     * </en>
     * 
     * @param pwdChangeInterval
     *            <jp>パスワード有効期間日数 &nbsp;&nbsp;</jp><en>Password change
     *            interval &nbsp;&nbsp;</en>
     */
    public void setPassChangeInterval(int pwdChangeInterval)
    {
        this.passChangeInterval = pwdChangeInterval;
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
     * <jp>ロール名称を設定します。<br>
     * </jp> <en>Sets role name.<br>
     * </en>
     * 
     * @param roleName
     *            <jp>ロール名称 &nbsp;&nbsp;</jp><en>Role name &nbsp;&nbsp;</en>
     */
    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    /**
     * <jp<br>
     * </jp> <en>Sets targeted users.<br>
     * </en>
     * 
     * @param target
     *            <jp>&nbsp;&nbsp;</jp><en>target &nbsp;&nbsp;</en>
     */
    public void setTarget(int target)
    {
        this.target = target;
    }

    /**
     * <jp> エンティティにセットされている文字列を返却します。<br></jp>
     * <en> Return the all entity values as one string. <br></en>
     * @return String
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("--------- Role Entity ----------------------------------\n");
        sb.append(" RoleId                     : " + this.roleID + "\n");
        sb.append(" RoleName                   : " + this.roleName + "\n");
        sb.append(" Target                   : " + this.target + "\n");
        sb.append(" FailedLoginAttempts        : " + this.failLoginAttem + "\n");
        sb.append(" PasswordChangeInterval     : " + this.passChangeInterval + "\n");
        sb.append(" FunctionId                 : " + this.functionId + "\n");
        sb.append(super.toAbstractString());
        sb.append("--------------------------------------------------------\n");

        return sb.toString();
    }
}

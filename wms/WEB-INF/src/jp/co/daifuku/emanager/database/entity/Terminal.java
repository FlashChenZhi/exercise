// $Id: Terminal.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.entity;

import java.io.Serializable;


/**
 * <jp>端末情報のエンティティです。<br>
 * </jp> <en> This entity provides Terninal information.<br>
 * </en>
 * 
 * @author Muneendra
 */
public class Terminal
        extends BaseEntity
        implements Serializable
{

    // Instance variables
    /** <jp>自動ログインフラグ &nbsp;&nbsp;</jp><en>Auto login flag &nbsp;&nbsp;</en> */
    private boolean autoLoginFlag;

    /** <jp>切り替える端末No. &nbsp;&nbsp;</jp><en>Change terminal number &nbsp;&nbsp;</en> */
    private String changeTerminalNumber = null;

    /** <jp> 切替える端末No.一覧 &nbsp;&nbsp;</jp><en>Change terminal number array &nbsp;&nbsp;</en> */
    private String[] changneTerminalNumbeArray = null;

    /** <jp> 切替える端末No.一覧 &nbsp;&nbsp;</jp><en>Is change terminal number array &nbsp;&nbsp;</en> */
    private boolean isChangneTerminalNumbeArray;

    /** <jp> 切替える端末ユーザ一覧 &nbsp;&nbsp;</jp><en>Is user ID array &nbsp;&nbsp;</en> */
    private boolean isUserIdArray;

    /** <jp>プリンタ名 &nbsp;&nbsp;</jp><en>Printer name &nbsp;&nbsp;</en> */
    private String printerName = null;

    /** <jp>ロールID &nbsp;&nbsp;</jp><en>Role ID &nbsp;&nbsp;</en> */
    private String roleId = null;

    /** <jp>端末IPアドレス &nbsp;&nbsp;</jp><en>Terminal IP adress &nbsp;&nbsp;</en> */
    private String terminalAddress = null;

    /** <jp>端末名 &nbsp;&nbsp;</jp><en>Terminal name &nbsp;&nbsp;</en> */
    private String terminalName = null;

    /** <jp>端末No. &nbsp;&nbsp;</jp><en>Terminal number &nbsp;&nbsp;</en> */
    private String terminalNumber = null;

    /** <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en> */
    private String userId = null;

    /** <jp> 切替える端末ユーザ一覧 &nbsp;&nbsp;</jp><en>User ID array &nbsp;&nbsp;</en> */
    private String[] userIdArray = null;

    /**
     * <jp>自動ログインフラグを取得します。<br>
     * </jp> <en>Return Auto login flag.<br>
     * </en>
     * 
     * @return <jp>自動ログインフラグ &nbsp;&nbsp;</jp><en>Auto login flag &nbsp;&nbsp;</en>
     */
    public boolean getAutoLoginFlag()
    {
        return autoLoginFlag;
    }

    /**
     * <jp>切り替える端末No.を取得します。<br>
     * </jp> <en>Return Change terminal number.<br>
     * </en>
     * 
     * @return <jp>切り替える端末No. &nbsp;&nbsp;</jp><en>Change terminal number
     *         &nbsp;&nbsp;</en>
     */
    public String getChangeTerminalNumber()
    {
        return changeTerminalNumber;
    }

    /**
     * <jp>切替える端末No.一覧を取得します。<br>
     * </jp> <en>Return .<br>
     * </en>
     * 
     * @return <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    public String[] getChangneTerminalNumbeArray()
    {
        return changneTerminalNumbeArray;
    }

    /**
     * <jp>プリンタ名を取得します。<br>
     * </jp> <en>Return Printer name.<br>
     * </en>
     * 
     * @return <jp>プリンタ名 &nbsp;&nbsp;</jp><en>Printer name &nbsp;&nbsp;</en>
     */
    public String getPrinterName()
    {
        return printerName;
    }

    /**
     * <jp>ロールIDを取得します。<br>
     * </jp> <en>Return Role ID.<br>
     * </en>
     * 
     * @return <jp>ロールID &nbsp;&nbsp;</jp><en>Role ID &nbsp;&nbsp;</en>
     */
    public String getRoleId()
    {
        return roleId;
    }

    /**
     * <jp>端末IPアドレスを取得します。<br>
     * </jp> <en>Return Terminal IP adress.<br>
     * </en>
     * 
     * @return <jp>端末IPアドレス &nbsp;&nbsp;</jp><en>Terminal IP adress
     *         &nbsp;&nbsp;</en>
     */
    public String getTerminalAddress()
    {
        return terminalAddress;
    }

    /**
     * <jp>端末名を取得します。<br>
     * </jp> <en>Return Terminal name.<br>
     * </en>
     * 
     * @return <jp>端末名 &nbsp;&nbsp;</jp><en>Terminal name &nbsp;&nbsp;</en>
     */
    public String getTerminalName()
    {
        return terminalName;
    }

    /**
     * <jp>端末No.を取得します。<br>
     * </jp> <en>Return Terminal number.<br>
     * </en>
     * 
     * @return <jp>端末No. &nbsp;&nbsp;</jp><en>Terminal number &nbsp;&nbsp;</en>
     */
    public String getTerminalNumber()
    {
        return terminalNumber;
    }

    /**
     * <jp>ユーザIDを取得します。<br>
     * </jp> <en>Return User ID.<br>
     * </en>
     * 
     * @return <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * <jp>ユーザID一覧を取得します。<br>
     * </jp> <en>Return .<br>
     * </en>
     * 
     * @return <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    public String[] getUserIdArray()
    {
        return userIdArray;
    }

    /**
     * <jp>切替える端末No.一覧を取得します。<br>
     * </jp> <en>Return .<br>
     * </en>
     * 
     * @return <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    public boolean isChangneTerminalNumbeArray()
    {
        return isChangneTerminalNumbeArray;
    }

    /**
     * <jp>切替えるユーザID一覧を取得します。<br>
     * </jp> <en>Return .<br>
     * </en>
     * 
     * @return <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    public boolean isUserIdArray()
    {
        return isUserIdArray;
    }

    /**
     * <jp>自動ログインフラグを設定します。<br>
     * </jp> <en>Sets Auto login flag.<br>
     * </en>
     * 
     * @param autoLoginFlag
     *            <jp>自動ログインフラグ &nbsp;&nbsp;</jp><en>Auto login flag
     *            &nbsp;&nbsp;</en>
     */
    public void setAutoLoginFlag(boolean autoLoginFlag)
    {
        this.autoLoginFlag = autoLoginFlag;
    }

    /**
     * <jp>切り替える端末No.を設定します。<br>
     * </jp> <en>Sets Change terminal number.<br>
     * </en>
     * 
     * @param changeTerminalNumber
     *            <jp>切り替える端末No. &nbsp;&nbsp;</jp><en>Change terminal number
     *            &nbsp;&nbsp;</en>
     */
    public void setChangeTerminalNumber(String changeTerminalNumber)
    {
        this.changeTerminalNumber = changeTerminalNumber;
    }

    /**
     * <jp>切替える端末No.一覧を設定します。<br>
     * </jp> <en>Sets .<br>
     * </en>
     * 
     * @param changneTerminalNumbeArray <jp>
     *            &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    public void setChangneTerminalNumbeArray(String[] changneTerminalNumbeArray)
    {
        this.changneTerminalNumbeArray = changneTerminalNumbeArray;
    }

    /**
     * <jp>切替える端末No.一覧を設定します。<br>
     * </jp> <en>Sets .<br>
     * </en>
     * 
     * @param isChangneTerminalNumbeArray <jp>
     *            &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    public void setIsChangneTerminalNumbeArray(boolean isChangneTerminalNumbeArray)
    {
        this.isChangneTerminalNumbeArray = isChangneTerminalNumbeArray;
    }

    /**
     * <jp>切替える端末ユーザ一覧を設定します。<br>
     * </jp> <en>Sets .<br>
     * </en>
     * 
     * @param isUserIdArray <jp>
     *            &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    public void setIsUserIdArray(boolean isUserIdArray)
    {
        this.isUserIdArray = isUserIdArray;
    }

    /**
     * <jp>プリンタ名を設定します。<br>
     * </jp> <en>Sets Printer name.<br>
     * </en>
     * 
     * @param printerName
     *            <jp>プリンタ名 &nbsp;&nbsp;</jp><en>Printer name &nbsp;&nbsp;</en>
     */
    public void setPrinterName(String printerName)
    {
        this.printerName = printerName;
    }

    /**
     * <jp>ロールIDを設定します。<br>
     * </jp> <en>Sets Role ID.<br>
     * </en>
     * 
     * @param roleId
     *            <jp>ロールID &nbsp;&nbsp;</jp><en>Role ID &nbsp;&nbsp;</en>
     */
    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }

    /**
     * <jp>端末IPアドレスを設定します。<br>
     * </jp> <en>Sets Terminal IP adress.<br>
     * </en>
     * 
     * @param terminalAddress
     *            <jp>端末IPアドレス &nbsp;&nbsp;</jp><en>Terminal IP adress
     *            &nbsp;&nbsp;</en>
     */
    public void setTerminalAddress(String terminalAddress)
    {
        this.terminalAddress = terminalAddress;
    }

    /**
     * <jp>端末名を設定します。<br>
     * </jp> <en>Sets Terminal name.<br>
     * </en>
     * 
     * @param terminalName
     *            <jp>端末名 &nbsp;&nbsp;</jp><en>Terminal name &nbsp;&nbsp;</en>
     */
    public void setTerminalName(String terminalName)
    {
        this.terminalName = terminalName;
    }

    /**
     * <jp>端末No.を設定します。<br>
     * </jp> <en>Sets Terminal number.<br>
     * </en>
     * 
     * @param terminalNumber
     *            <jp>端末No. &nbsp;&nbsp;</jp><en>Terminal number &nbsp;&nbsp;</en>
     */
    public void setTerminalNumber(String terminalNumber)
    {
        this.terminalNumber = terminalNumber;
    }

    /**
     * <jp>ユーザIDを設定します。<br>
     * </jp> <en>Sets User ID.<br>
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
     * <jp>切替える端末ユーザ一覧を設定します。<br>
     * </jp> <en>Sets .<br>
     * </en>
     * 
     * @param userIdArray <jp>
     *            &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     */
    public void setUserIdArray(String[] userIdArray)
    {
        this.userIdArray = userIdArray;
    }


    /**
     * <jp> エンティティにセットされている文字列を返却します。<br></jp>
     * <en> Return the all entity values as one string. <br></en>
     * @return String
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("--- Terminal Entity ------------------------------------\n");
        sb.append(" TerminalNumber             : " + this.terminalNumber + "\n");
        sb.append(" TerminalName               : " + this.terminalName + "\n");
        sb.append(" TerminalAddress            : " + this.terminalAddress + "\n");
        sb.append(" UserId                     : " + this.userId + "\n");
        sb.append(" RoleId                     : " + this.roleId + "\n");
        sb.append(" PrinterName                : " + this.printerName + "\n");
        sb.append(" AutoLoginFlag              : " + this.autoLoginFlag + "\n");
        sb.append(super.toAbstractString());
        sb.append("--------------------------------------------------------\n");

        return sb.toString();
    }
}

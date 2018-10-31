// $Id: BaseEntity.java 3965 2009-04-06 02:55:05Z admin $
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
 * <jp>全てのエンティティクラスのベースとなるクラスです。 全てのエンティティで共通のパラメータを保持します。<br>
 * </jp> <en>This is Base entity and contains the common attributes across all
 * entities. All entities should extend this entity.<br>
 * </en>
 * 
 * @author Muneendra
 */
public class BaseEntity
        implements Serializable
{
    // instance variable
    /** <jp>更新区分 &nbsp;&nbsp;</jp><en>Update kind &nbsp;&nbsp;</en> */
    private int updateKind;

    /** <jp>更新処理名 &nbsp;&nbsp;</jp><en>Update process name &nbsp;&nbsp;</en> */
    private String updateProcess = null;

    /**
     * <jp>更新端末IPアドレス &nbsp;&nbsp;</jp><en>Update terminal IP address
     * &nbsp;&nbsp;</en>
     */
    private String updateTerminal = null;

    /** <jp>更新ユーザ &nbsp;&nbsp;</jp><en>Update user ID &nbsp;&nbsp;</en> */
    private String updateUser = null;


    /** <jp>更新日時 &nbsp;&nbsp;</jp><en>Update date &nbsp;&nbsp;</en> */
    private Date updateDate = null;

    /**
     * <jp>更新区分を取得します。<br>
     * </jp> <en>Returns updated kind.<br>
     * </en>
     * 
     * @return <jp>更新区分 &nbsp;&nbsp;</jp><en>Update kind &nbsp;&nbsp;</en>
     */
    public int getUpdateKind()
    {
        return updateKind;
    }

    /**
     * <jp>更新処理名を取得します。<br>
     * </jp> <en>Returns update process.<br>
     * </en>
     * 
     * @return <jp>更新処理名 &nbsp;&nbsp;</jp><en>Update process &nbsp;&nbsp;</en>
     */
    public String getUpdateProcess()
    {
        return updateProcess;
    }

    /**
     * <jp>更新端末IPを取得します。<br>
     * </jp> <en>Returns update terminal.<br>
     * </en>
     * 
     * @return <jp>更新端末IP &nbsp;&nbsp;</jp><en>Update terminal &nbsp;&nbsp;</en>
     */
    public String getUpdateTerminal()
    {
        return updateTerminal;
    }

    /**
     * <jp>更新ユーザを取得します。<br>
     * </jp> <en>Returns update user.<br>
     * </en>
     * 
     * @return <jp>更新ユーザ &nbsp;&nbsp;</jp><en>Update user &nbsp;&nbsp;</en>
     */
    public String getUpdateUser()
    {
        return updateUser;
    }

    /**
     * <jp>更新区分を設定します。<br>
     * </jp> <en>Sets updated kind.<br>
     * </en>
     * 
     * @param updateKind
     *            <jp>更新区分 &nbsp;&nbsp;</jp><en>Update kind &nbsp;&nbsp;</en>
     */
    public void setUpdateKind(int updateKind)
    {
        this.updateKind = updateKind;
    }

    /**
     * <jp>更新処理名を設定します。<br>
     * </jp> <en>Sets update process.<br>
     * </en>
     * 
     * @param updateProcess
     *            <jp>更新処理名 &nbsp;&nbsp;</jp><en>Update process &nbsp;&nbsp;</en>
     */
    public void setUpdateProcess(String updateProcess)
    {
        this.updateProcess = updateProcess;
    }

    /**
     * <jp>更新端末IPを設定します。<br>
     * </jp> <en>Sets update terminal.</en>
     * 
     * @param updateTerminal
     *            <jp>更新端末IP &nbsp;&nbsp;</jp><en>Update terminal &nbsp;&nbsp;</en>
     */
    public void setUpdateTerminal(String updateTerminal)
    {
        this.updateTerminal = updateTerminal;
    }

    /**
     * <jp>更新ユーザを設定します。<br>
     * </jp> <en>Sets update user.<br>
     * </en>
     * 
     * @param updateUser
     *            <jp>更新ユーザ &nbsp;&nbsp;</jp><en>Update user &nbsp;&nbsp;</en>
     */
    public void setUpdateUser(String updateUser)
    {
        this.updateUser = updateUser;
    }

    /**
     * <jp><br>
     * </jp> <en>Returns updateDate.<br>
     * </en>
     * 
     * @return <jp> &nbsp;&nbsp;</jp><en>updateDate  &nbsp;&nbsp;</en>
     */
    public Date getUpdateDate()
    {
        return updateDate;
    }

    /**
     * <jp><br>
     * </jp> <en>Sets updateDate.<br>
     * </en>
     * 
     * @param updateDate
     *            <jp>更新ユーザ &nbsp;&nbsp;</jp><en>updateDate &nbsp;&nbsp;</en>
     */
    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }

    /**
     * <jp> エンティティにセットされている文字列を返却します。<br></jp>
     * <en> Return the all entity values as one string. <br></en>
     * @return String
     */
    protected String toAbstractString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(" UpdateDate                 : " + this.updateDate + "\n");
        sb.append(" UpdateUser                 : " + this.updateUser + "\n");
        sb.append(" UpdateTerminal             : " + this.updateTerminal + "\n");
        sb.append(" UpdateKind                 : " + this.updateKind + "\n");
        sb.append(" UpdateProcess              : " + this.updateProcess + "\n");

        return sb.toString();
    }
}

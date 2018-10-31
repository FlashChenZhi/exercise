// $Id: MainMenu.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <jp>メインメニュー情報のエンティティです。<br>
 * </jp> <en> This entity provides main menu information.<br>
 * </en>
 * 
 * @author Muneendra
 */
public class MainMenu
        extends BaseEntity
        implements Serializable
{

    // Instance variables
    /** <jp>メインメニューID &nbsp;&nbsp;</jp><en>Main menu ID &nbsp;&nbsp;</en> */
    private String mainMenuId = null;

    /** <jp>メインメニュー表示順 &nbsp;&nbsp;</jp><en>Menu display order &nbsp;&nbsp;</en> */
    private int menuDisplayOrder;

    /** <jp>メインメニューリソースキー &nbsp;&nbsp;</jp><en>Menu resource key &nbsp;&nbsp;</en> */
    private String menuResourceKey = null;

    /** <jp>更新日時 &nbsp;&nbsp;</jp><en>Update date &nbsp;&nbsp;</en> */
    private Timestamp updatedDate = null;

    /**
     * <jp>メインメニューIDを取得します。<br>
     * </jp> <en>Return main menu ID.<br>
     * </en>
     * 
     * @return <jp>メインメニューID &nbsp;&nbsp;</jp><en>Main menu ID &nbsp;&nbsp;</en>
     */
    public String getMainMenuId()
    {
        return mainMenuId;
    }

    /**
     * <jp>メインメニュー表示順を取得します。<br>
     * </jp> <en>Return menu display order.<br>
     * </en>
     * 
     * @return <jp>メインメニュー表示順 &nbsp;&nbsp;</jp><en>Menu display order
     *         &nbsp;&nbsp;</en>
     */
    public int getMenuDisplayOrder()
    {
        return menuDisplayOrder;
    }

    /**
     * <jp>メインメニューリソースキーを取得します。<br>
     * </jp> <en>Return menu resource key.<br>
     * </en>
     * 
     * @return <jp>メインメニューリソースキー &nbsp;&nbsp;</jp><en>Menu resource key
     *         &nbsp;&nbsp;</en>
     */
    public String getMenuResourceKey()
    {
        return menuResourceKey;
    }

    /**
     * <jp>更新日時を取得します。<br>
     * </jp> <en>Return update date.<br>
     * </en>
     * 
     * @return <jp>更新日時 &nbsp;&nbsp;</jp><en>Update date &nbsp;&nbsp;</en>
     */
    public Timestamp getUpdatedDate()
    {
        return updatedDate;
    }

    /**
     * <jp>メインメニューIDを設定します。<br>
     * </jp> <en>Sets main menu ID.<br>
     * </en>
     * 
     * @param mainMenuId
     *            <jp>メインメニューID &nbsp;&nbsp;</jp><en>Main menu ID &nbsp;&nbsp;</en>
     */
    public void setMainMenuId(String mainMenuId)
    {
        this.mainMenuId = mainMenuId;
    }

    /**
     * <jp>メインメニュー表示順を設定します。<br>
     * </jp> <en>Sets menu display order.<br>
     * </en>
     * 
     * @param menuDisplayOrder
     *            <jp>メインメニュー表示順 &nbsp;&nbsp;</jp><en>Menu display order
     *            &nbsp;&nbsp;</en>
     */
    public void setMenuDisplayOrder(int menuDisplayOrder)
    {
        this.menuDisplayOrder = menuDisplayOrder;
    }

    /**
     * <jp>メインメニューリソースキーを設定します。<br>
     * </jp> <en>Sets menu resource key.<br>
     * </en>
     * 
     * @param menuResourceKey
     *            <jp>メインメニューリソースキー &nbsp;&nbsp;</jp><en>Menu resource key
     *            &nbsp;&nbsp;</en>
     */
    public void setMenuResourceKey(String menuResourceKey)
    {
        this.menuResourceKey = menuResourceKey;
    }

    /**
     * <jp>更新日時を設定します。<br>
     * </jp> <en>Sets update date.<br>
     * </en>
     * 
     * @param updatedDate
     *            <jp>更新日時 &nbsp;&nbsp;</jp><en>Update date &nbsp;&nbsp;</en>
     */
    public void setUpdatedDate(Timestamp updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    /**
     * <jp> エンティティにセットされている文字列を返却します。<br></jp>
     * <en> Return the all entity values as one string. <br></en>
     * @return String
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("--- MainMenu Entity ------------------------------------\n");
        sb.append(" MainMenuId                 : " + this.mainMenuId + "\n");
        sb.append(" MenuDisplayOrder           : " + this.menuDisplayOrder + "\n");
        sb.append(" MenuResourceKey            : " + this.menuResourceKey + "\n");
        sb.append(super.toAbstractString());
        sb.append("--------------------------------------------------------\n");

        return sb.toString();
    }
}

// $Id: SubMenu.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.entity;

import java.io.Serializable;

/**
 * <jp>サブメニューの機能画面情報のエンティティです。<br>
 * </jp> <en> This entity provides Submenu function information.<br>
 * </en>
 * 
 * @author Muneendra
 */
public class SubMenu
        extends BaseEntity
        implements Serializable
{

    /** <jp>メインメニューID &nbsp;&nbsp;</jp><en>Mainmenu ID &nbsp;&nbsp;</en> */
    private String mainMenuId = null;

    // Instance variables
    /** <jp>サブメニュー表示順 &nbsp;&nbsp;</jp><en>Submenu display order &nbsp;&nbsp;</en> */
    private int subMenuDisplayOrder;

    /** <jp>サブメニューID &nbsp;&nbsp;</jp><en>Submenu ID &nbsp;&nbsp;</en> */
    private String subMenuId = null;

    /** <jp>サブメニュー表示文字列 &nbsp;&nbsp;</jp><en>Submenu resource key &nbsp;&nbsp;</en> */
    private String subMenuResourceKey = null;

    /**
     * <jp>メインメニューIDを取得します。<br>
     * </jp> <en>Return mainmenu ID.<br>
     * </en>
     * 
     * @return <jp>メインメニューID &nbsp;&nbsp;</jp><en>Mainmenu ID &nbsp;&nbsp;</en>
     */
    public String getMainMenuId()
    {
        return mainMenuId;
    }

    /**
     * <jp>サブメニュー表示順を取得します。<br>
     * </jp> <en>Return submenu display order.<br>
     * </en>
     * 
     * @return <jp>サブメニュー表示順 &nbsp;&nbsp;</jp><en>Submenu display order
     *         &nbsp;&nbsp;</en>
     */
    public int getSubMenuDisplayOrder()
    {
        return subMenuDisplayOrder;
    }

    /**
     * <jp>サブメニューIDを取得します。<br>
     * </jp> <en>Return submenu ID.<br>
     * </en>
     * 
     * @return <jp>サブメニューID &nbsp;&nbsp;</jp><en>Submenu ID &nbsp;&nbsp;</en>
     */
    public String getSubMenuId()
    {
        return subMenuId;
    }

    /**
     * <jp>サブメニュー表示文字列を取得します。<br>
     * </jp> <en>Return submenu resource key.<br>
     * </en>
     * 
     * @return <jp>サブメニュー表示文字列 &nbsp;&nbsp;</jp><en>Submenu resource key
     *         &nbsp;&nbsp;</en>
     */
    public String getSubMenuResourceKey()
    {
        return subMenuResourceKey;
    }

    /**
     * <jp>メインメニューIDを設定します。<br>
     * </jp> <en>Sets mainmenu ID.<br>
     * </en>
     * 
     * @param mainMenuId
     *            <jp>メインメニューID &nbsp;&nbsp;</jp><en>Mainmenu ID &nbsp;&nbsp;</en>
     */
    public void setMainMenuId(String mainMenuId)
    {
        this.mainMenuId = mainMenuId;
    }

    /**
     * <jp>サブメニュー表示順を設定します。<br>
     * </jp> <en>Sets submenu display order.<br>
     * </en>
     * 
     * @param subMenuDisplayOrder
     *            <jp>サブメニュー表示順 &nbsp;&nbsp;</jp><en>Submenu display order
     *            &nbsp;&nbsp;</en>
     */
    public void setSubMenuDisplayOrder(int subMenuDisplayOrder)
    {
        this.subMenuDisplayOrder = subMenuDisplayOrder;
    }

    /**
     * <jp>サブメニューIDを設定します。<br>
     * </jp> <en>Sets submenu ID.<br>
     * </en>
     * 
     * @param subMenuId
     *            <jp>サブメニューID &nbsp;&nbsp;</jp><en>Submenu ID &nbsp;&nbsp;</en>
     */
    public void setSubMenuId(String subMenuId)
    {
        this.subMenuId = subMenuId;
    }

    /**
     * <jp>サブメニュー表示文字列を設定します。<br>
     * </jp> <en>Sets submenu resource key.<br>
     * </en>
     * 
     * @param subMenuResourceKey
     *            <jp>サブメニュー表示文字列 &nbsp;&nbsp;</jp><en>Submenu resource key
     *            &nbsp;&nbsp;</en>
     */
    public void setSubMenuResourceKey(String subMenuResourceKey)
    {
        this.subMenuResourceKey = subMenuResourceKey;
    }

    /**
     * <jp> エンティティにセットされている文字列を返却します。<br></jp>
     * <en> Return the all entity values as one string. <br></en>
     * @return String
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("--- SubmenuMenu Entity ---------------------------------\n");
        sb.append(" SubMenuId                  : " + this.subMenuId + "\n");
        sb.append(" MainMenuId                 : " + this.mainMenuId + "\n");
        sb.append(" SubMenuDisplayOrder        : " + this.subMenuDisplayOrder + "\n");
        sb.append(" SubMenuResourceKey         : " + this.subMenuResourceKey + "\n");
        sb.append(super.toAbstractString());
        sb.append("--------------------------------------------------------\n");

        return sb.toString();
    }

}

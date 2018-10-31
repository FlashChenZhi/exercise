// $Id: Function.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.entity;

import java.io.Serializable;

/**
 * <jp>メニューと機能画面のマップ情報のエンティティです。<br>
 * </jp> <en> This entity provides menu function map information.<br>
 * </en>
 * 
 * @author Muneendra
 */
public class Function
        extends BaseEntity
        implements Serializable
{

    // Instance variables
    /**
     * <jp>サブメニューボタン表示順 &nbsp;&nbsp;</jp><en>Button display order.
     * &nbsp;&nbsp;</en>
     */
    private int buttonDisplayOrder;

    /** <jp>ボタンリソースキー &nbsp;&nbsp;</jp><en>Button resource key. &nbsp;&nbsp;</en> */
    private String buttonResourceKey = null;

    /**
     * <jp>認証チェックフラグ &nbsp;&nbsp;</jp><en>Flag for enabled the screen
     * authentication &nbsp;&nbsp;</en>
     */
    private boolean doAuthenticationFlag;

    /** <jp>Frame名 &nbsp;&nbsp;</jp><en>Frame name. &nbsp;&nbsp;</en> */
    private String frameName = null;

    /** <jp>画面ID &nbsp;&nbsp;</jp><en>Function id &nbsp;&nbsp;</en> */
    private String functionId = null;

    /**
     * <jp>客先には表示させないためのフラグ &nbsp;&nbsp;</jp><en>Flag for hidden to customer.
     * &nbsp;&nbsp;</en>
     */
    private boolean hiddenFlag;

    /**
     * <jp>ページ名リソースキー &nbsp;&nbsp;</jp><en>Screen title resource key
     * &nbsp;&nbsp;</en>
     */
    private String pageResourceKey = null;

    /** <jp>サブメニューID &nbsp;&nbsp;</jp><en>Submenu ID &nbsp;&nbsp;</en> */
    private String subMenuId = null;

    /** <jp>URI &nbsp;&nbsp;</jp><en>URI &nbsp;&nbsp;</en> */
    private String url = null;

    /** <jp>画面DS番号 &nbsp;&nbsp;</jp><en>dnNumber &nbsp;&nbsp;</en>*/
    private String dsNumber = null;

    /**
     * <jp>サブメニューボタン表示順を取得します。<br>
     * </jp> <en>Return button display order.<br>
     * </en>
     * 
     * @return <jp>サブメニューボタン表示順 &nbsp;&nbsp;</jp><en>Button display order
     *         &nbsp;&nbsp;</en>
     */
    public int getButtonDisplayOrder()
    {
        return buttonDisplayOrder;
    }

    /**
     * <jp>ボタンリソースキーを取得します。<br>
     * </jp> <en>Return button resource key.<br>
     * </en>
     * 
     * @return <jp>ボタンリソースキー &nbsp;&nbsp;</jp><en>Button resource key
     *         &nbsp;&nbsp;</en>
     */
    public String getButtonResourceKey()
    {
        return buttonResourceKey;
    }

    /**
     * <jp>認証チェックフラグを取得します。<br>
     * </jp> <en>Return flag for enabled the screen authentication.<br>
     * </en>
     * 
     * @return <jp>認証チェックフラグ &nbsp;&nbsp;</jp><en>Flag for enabled the screen
     *         authentication &nbsp;&nbsp;</en>
     */
    public boolean getDoAuthenticationFlag()
    {
        return doAuthenticationFlag;
    }

    /**
     * <jp>Frame名を取得します。<br>
     * </jp> <en>Return frame name.<br>
     * </en>
     * 
     * @return <jp>Frame名 &nbsp;&nbsp;</jp><en>Frame name &nbsp;&nbsp;</en>
     */
    public String getFrameName()
    {
        return frameName;
    }

    /**
     * <jp>画面IDを取得します。<br>
     * </jp> <en>Return function id.<br>
     * </en>
     * 
     * @return <jp>画面ID &nbsp;&nbsp;</jp><en>Function id &nbsp;&nbsp;</en>
     */
    public String getFunctionId()
    {
        return functionId;
    }

    /**
     * <jp>客先には表示させないためのフラグ。<br>
     * </jp> <en>Return flag for hidden to customer.<br>
     * </en>
     * 
     * @return <jp>客先には表示させないためのフラグ &nbsp;&nbsp;</jp><en>Flag for hidden to
     *         customer &nbsp;&nbsp;</en>
     */
    public boolean getHiddenFlag()
    {
        return hiddenFlag;
    }

    /**
     * <jp>ページ名リソースキーを取得します。<br>
     * </jp> <en>Return screen title resource key.<br>
     * </en>
     * 
     * @return <jp>ページ名リソースキー &nbsp;&nbsp;</jp><en>Screen title resource key
     *         &nbsp;&nbsp;</en>
     */
    public String getPageResourceKey()
    {
        return pageResourceKey;
    }

    /**
     * <jp>サブメニューIDを取得します。<br>
     * </jp> <en>Returns submenu ID.<br>
     * </en>
     * 
     * @return <jp>サブメニューID &nbsp;&nbsp;</jp><en>Submenu ID &nbsp;&nbsp;</en>
     */
    public String getSubMenuId()
    {
        return subMenuId;
    }

    /**
     * <jp>URIを取得します。<br>
     * </jp> <en>Return URI.<br>
     * </en>
     * 
     * @return <jp>URI &nbsp;&nbsp;</jp><en>URI &nbsp;&nbsp;</en>
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * <jp>サブメニューボタン表示順を設定します。<br>
     * </jp> <en>Sets button display order.<br>
     * </en>
     * 
     * @param buttonDisplayOrder
     *            <jp>サブメニューボタン表示順 &nbsp;&nbsp;</jp><en>Button display order
     *            &nbsp;&nbsp;</en>
     */
    public void setButtonDisplayOrder(int buttonDisplayOrder)
    {
        this.buttonDisplayOrder = buttonDisplayOrder;
    }

    /**
     * <jp>ボタンリソースキーを設定します。<br>
     * </jp> <en>Sets button resource key.<br>
     * </en>
     * 
     * @param buttonResourceKey
     *            <jp>ボタンリソースキー &nbsp;&nbsp;</jp><en>Button resource key
     *            &nbsp;&nbsp;</en>
     */
    public void setButtonResourceKey(String buttonResourceKey)
    {
        this.buttonResourceKey = buttonResourceKey;
    }

    /**
     * <jp>認証チェックフラグを設定します。<br>
     * </jp> <en>Sets flag for enabled the screen authentication.<br>
     * </en>
     * 
     * @param doAuthenticationFlag
     *            <jp>認証チェックフラグ &nbsp;&nbsp;</jp><en>Flag for enabled the
     *            screen authentication &nbsp;&nbsp;</en>
     */
    public void setDoAuthenticationFlag(boolean doAuthenticationFlag)
    {
        this.doAuthenticationFlag = doAuthenticationFlag;
    }

    /**
     * <jp>Frame名を設定します。<br>
     * </jp> <en>Sets frame name.<br>
     * </en>
     * 
     * @param frameName
     *            <jp>Frame名 &nbsp;&nbsp;</jp><en>Frame name &nbsp;&nbsp;</en>
     */
    public void setFrameName(String frameName)
    {
        this.frameName = frameName;
    }

    /**
     * <jp>画面IDを設定します。<br>
     * </jp> <en>Sets function id.<br>
     * </en>
     * 
     * @param functionId
     *            <jp>画面ID &nbsp;&nbsp;</jp><en>Function id &nbsp;&nbsp;</en>
     */
    public void setFunctionId(String functionId)
    {
        this.functionId = functionId;
    }

    /**
     * <jp>客先には表示させないためのフラグを設定します。<br>
     * </jp> <en>Sets flag for hidden to customer.<br>
     * </en>
     * 
     * @param hiddenFlag
     *            <jp>客先には表示させないためのフラグ &nbsp;&nbsp;</jp><en>Flag for hidden to
     *            customer &nbsp;&nbsp;</en>
     */
    public void setHiddenFlag(boolean hiddenFlag)
    {
        this.hiddenFlag = hiddenFlag;
    }

    /**
     * <jp>ページ名リソースキーを設定します。<br>
     * </jp> <en>Sets screen title resource key.<br>
     * </en>
     * 
     * @param pageResourceKey
     *            <jp>ページ名リソースキー &nbsp;&nbsp;</jp><en>Screen title resource
     *            key &nbsp;&nbsp;</en>
     */
    public void setPageResourceKey(String pageResourceKey)
    {
        this.pageResourceKey = pageResourceKey;
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
     * <jp>URIを設定します。<br>
     * </jp> <en>Sets URI.<br>
     * </en>
     * 
     * @param url
     *            <jp>URI &nbsp;&nbsp;</jp><en>URI &nbsp;&nbsp;</en>
     */
    public void setUrl(String url)
    {
        this.url = url;
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
     * <jp> エンティティにセットされている文字列を返却します。<br></jp>
     * <en> Return the all entity values as one string. <br></en>
     * @return String
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("--- Function Entity ------------------------------------\n");
        sb.append(" FunctionId                 : " + this.functionId + "\n");
        sb.append(" SunmenuId                  : " + this.subMenuId + "\n");
        sb.append(" ButtonDisplayOrder         : " + this.buttonDisplayOrder + "\n");
        sb.append(" ButtonResourceKey          : " + this.buttonResourceKey + "\n");
        sb.append(" PageResourceKey            : " + this.pageResourceKey + "\n");
        sb.append(" DoAuthenticationFlag       : " + this.doAuthenticationFlag + "\n");
        sb.append(" Uri                        : " + this.url + "\n");
        sb.append(" FrameName                  : " + this.frameName + "\n");
        sb.append(" HiddenFlag                 : " + this.hiddenFlag + "\n");
        sb.append(" DSNumber	               : " + this.dsNumber + "\n");
        sb.append(super.toAbstractString());
        sb.append("--------------------------------------------------------\n");

        return sb.toString();
    }

}

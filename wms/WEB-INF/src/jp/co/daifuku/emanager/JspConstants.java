// $Id: JspConstants.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager;

/**  
 * <jp>JSP で使用する定数を宣言するためのインターフェースです。<br></jp>
 * <en>This class class holds the constant values used in JSP files.<br></en>
 * @author K.Fukumori 
 */
public interface JspConstants
{

    //Class variables	
    /**  Product key ProductType */
    public static final String PRODUCTTYPE = "Product";

    /**  Product key MenuType */
    public static final String MENUTYPE = "MenuType";

    /** Request Parameter key : Number of main menu. */
    public static final String RPARAMKEY_MAINMENUCOUNT = "RPARAMKEY_MAINMENUCOUNT";

    /** Request Parameter key : Flag for logout button display. */
    public static final String RPARAMKEY_LOGOUTBUTTON = "RPARAMKEY_LOGOUTBUTTON";

    /** Request Parameter key : HTML of main menu. */
    public static final String RPARAMKEY_MAINMENUHTML = "RPARAMKEY_MAINMENUHTML";

    /** Request Parameter key : HTML of sub menu. */
    public static final String RPARAMKEY_SUBMENUHTML = "RPARAMKEY_SUBMENUHTML";

    /** Request Parameter key : Submenu title. */
    public static final String RPARAMKEY_SUBMENUTITLE = "RPARAMKEY_SUBMENUTITLE";

    /** Request Parameter key : Login user name. */
    public static final String RPARAMKEY_LOGINUSERNAME = "RPARAMKEY_LOGINUSERNAME";

    /** Request Parameter key : Login terminal name. */
    public static final String RPARAMKEY_LOGINTERMINALNAME = "RPARAMKEY_LOGINTERMINALNAME";

    /** Request Parameter key : Type for main menu string */
    public static final String RPARAMKEY_MAINMENU_LABELDISPTYPE = "RPARAMKEY_MAINMENU_LABELDISPTYPE";

    /** Request Parameter key : Flag for main menu expansion display */
    public static final String RPARAMKEY_LOGVIEW_DISPLAY = "RPARAMKEY_LOGVIEW_DISPLAY";

    /** Request Parameter key : Main menu id */
    public static final String RPARAMKEY_MAINMENUID = "id";

    /** Request Parameter key : MainMenu DispResource key. */
    public static final String RPARAMKEY_MAINMENUDISPKEY = "RPARAMKEY_MAINMENUDISPKEY";

    /** Request Parameter key : WORKDATE (used in login Success screen)*/
    public static final String WORKDATE = "WORKDATE";
}

// $Id: MainMenuBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.menu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.JspConstants;
import jp.co.daifuku.emanager.authentication.DfkSessionListener;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.entity.Function;
import jp.co.daifuku.emanager.database.handler.AuthenticationHandler;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.FunctionHandler;
import jp.co.daifuku.emanager.database.handler.MainMenuHandler;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.emanager.util.P11LogWriter;

/**
 * <jp>メインメニューの実装です。MainMenuA.jsp、MainMenuB.jsp共通で使用します。<br></jp> 
 * <en>It is mounting of a main menu. It is common to MainMenuA.jsp and MainMenuB.jsp and is used. <br></en> 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/02/13</TD>
 * <TD>K.Fukumori</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author $Author: admin $
 */
public class MainMenuBusiness
        extends MainMenu
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------
    /**
     * <jp> 画面の初期化を行います。<br></jp> 
     * <en>Initialize a screen. <br></en>
     * 
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {

    }

    /**
     * <jp> 発生条件：各コントロールイベント呼び出し前に呼び出されます。<br></jp> 
     * <en>Generating conditions: It is called before each control event call. <br></en>
     * 
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {

        DfkUserInfo userinfo = null;
        try
        {
            userinfo = (DfkUserInfo)this.getUserInfo();
        }
        catch (ClassCastException ex)
        {
            this.removeUserInfo();
        }

        if (userinfo == null)
        {
            return;
        }

        boolean logoutButton = !userinfo.getUserId().equals(EmConstants.ANONYMOUS_USER);
        jp.co.daifuku.emanager.database.entity.MainMenu mainMenuEntity[] = null;
        Connection conn = null;

        // Get MainMenuEntity from database
        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            mainMenuEntity = getMainMenuEntity(conn);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }

        // Get menu label type
        String labelDispType = EmProperties.getProperty(EmConstants.EMPARAMKEY_MAINMENU_LABELDISPTYPE);
        {
            if (labelDispType == null)
            {
                labelDispType = "normal";
            }
        }

        // Get logview display
        boolean logViewFlag = false;
        String logView = EmProperties.getProperty(EmConstants.EMPARAMKEY_LOGVIEW_DISPLAY);
        {
            if (logView != null && logView.toUpperCase().equals("TRUE"))
            {
                logViewFlag = true;
            }
            else
            {
                logViewFlag = false;
            }
        }

        // Create MainMenu HTML
        String mainMenuHtml = "";
        if (userinfo.getMainMenuType() == EmConstants.MAINMENUSHOWTYPE_A)
        {
            mainMenuHtml = "curousel".equals(labelDispType) ? createHtmlTypeACarousel(mainMenuEntity)
                                                           : createHtmlTypeA(mainMenuEntity, labelDispType);
        }
        else if (userinfo.getMainMenuType() == EmConstants.MAINMENUSHOWTYPE_B)
        {
            mainMenuHtml = "curousel".equals(labelDispType) ? createHtmlTypeBCarousel(mainMenuEntity)
                                                           : createHtmlTypeB(mainMenuEntity);
        }

        String username = userinfo.getUserName();
        if (username == null || username.length() == 0)
        {
            username = userinfo.getUserId();
        }

        // Convert the HTML special charactor.
        username = EManagerUtil.ConvertForHtml(username);

        // Set Request param

        this.getHttpRequest().setAttribute(JspConstants.RPARAMKEY_LOGINUSERNAME, username);
        this.getHttpRequest().setAttribute(JspConstants.RPARAMKEY_LOGOUTBUTTON, String.valueOf(logoutButton));
        this.getHttpRequest().setAttribute(JspConstants.RPARAMKEY_LOGVIEW_DISPLAY, String.valueOf(logViewFlag));
        this.getHttpRequest().setAttribute(JspConstants.RPARAMKEY_MAINMENUCOUNT, String.valueOf(6));
        this.getHttpRequest().setAttribute(JspConstants.RPARAMKEY_MAINMENUHTML, mainMenuHtml);
        this.getHttpRequest().setAttribute(JspConstants.RPARAMKEY_MAINMENU_LABELDISPTYPE, labelDispType);
    }

    /**
     * <jp>基底クラス<code>Page</code>のメソッドをオーバライドします。<br>
     * この中では何も行いませんが、btn_Logout_Click()イベントが発生する際に セッションタイムアウトになっていると、btn_Logout_Click()の処理が呼ばれずに
     * Errorページに遷移してしまうためです。<br></jp> 
     * <en> A base -- carry out the override of the method of class <code>Page</code>. <br>
     * Although nothing is performed in this, in case a btn_Logout_Click() event occurs, when it is
     * a session timeout, it is for changing to an Error page, without calling processing of
     * btn_Logout_Click(). <br></en>
     * 
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_LoginCheck(ActionEvent e)
            throws Exception
    {
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Event handler methods -----------------------------------------
    /**
     * <jp>未使用。<br></jp> 
     * <en>Intact.<br></en>
     * 
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Logout_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     * <jp>ログアウト・ボタンクリック時のイベントです。<br>
     * 1. User情報を削除<br>
     * 2. HttpSession.invalidate()メソッド発行 <br>
     * 3. ログイン画面に遷移 <br>
     * <br></jp> 
     * <en>It is an event at the time of a logout button click. <br>
     * 1. User information is deleted. <br>
     * 2. HttpSession.invalidate() method issue.<br>
     * 3. It changes on a login screen.<br>
     * <br></en>
     * 
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Logout_Click(ActionEvent e)
            throws Exception
    {
        DfkUserInfo userInfo = (DfkUserInfo)this.getUserInfo();
        if (userInfo != null)
        {
            // **** updted for eManager-2.0

            if (P11Config.isAccessLog() && userInfo.getDsNumber() != null)
            {
                Connection conn = null;
                try
                {
                    conn = EmConnectionHandler.getConnection();
                    String message = "Screen Logout Access log";
                    P11LogWriter p11logWriter = new P11LogWriter(conn);
                    FunctionHandler functionHandler = EmHandlerFactory.getFunctionHandler(conn);
                    Function func = functionHandler.findByFunctionId(userInfo.getFunctionId());
                    // 画面ログインをする画面からのログアウト(画面終了)の場合
                    if (func != null && func.getDoAuthenticationFlag())
                    {
                        p11logWriter.createAccessLog(userInfo, EmConstants.AUTHLOG_CLASS_LOGOUT,
                                this.httpRequest.getRemoteAddr(), message);
                    }
                    // 画面ログインをしない画面からのログアウト(画面終了)の場合
                    else
                    {
                        // 画面ログインをしない場合はシステムログインユーザIDでログを登録する
                        p11logWriter.createAccessLog(userInfo, userInfo.getSystemLoginUserId(),
                                userInfo.getSystemLoginUserName(), EmConstants.AUTHLOG_CLASS_LOGOUT,
                                this.httpRequest.getRemoteAddr(), message);
                    }
                    EmConnectionHandler.commit(conn);
                }
                catch (SQLException sqlException)
                {
                    EmConnectionHandler.rollback(conn);
                    throw sqlException;
                }
                finally
                {
                    EmConnectionHandler.closeConnection(conn);
                }
            }
            Connection conn = null;
            try
            {
                conn = EmConnectionHandler.getConnection();
                //項目リストの生成
                List list = new ArrayList();
                P11LogWriter logWriter = new P11LogWriter(conn);
                logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_LOGOUT, EmConstants.DS_NO_MAINMENU,
                        "TLE-T0144", list);

                EmConnectionHandler.commit(conn);
            }
            catch (SQLException sqlException)
            {
                EmConnectionHandler.rollback(conn);
                throw sqlException;
            }
            finally
            {
                EmConnectionHandler.closeConnection(conn);
            }

            // End update

            DfkSessionListener dfkSessionListener = (DfkSessionListener)userInfo;
            dfkSessionListener.isLogoutClick = true;
            removeUserInfo();

        }
        forward(EmProperties.getProperty(EmConstants.EMPARAMKEY_LOGOUT_URI));
    }

    /**
     * <jp>A (小アイコン) タイプのメインメニューHTMLを生成します。 <br></jp> 
     * <en>Create html for typeA. <br></en>
     * 
     * @param mainMenuEntity <jp>メインメニューのエンティティ配列 &nbsp;&nbsp;</jp><en>MainMenuEntity array &nbsp;&nbsp;</en>
     * @param labelDispType <jp>ラベルの表示タイプ &nbsp;&nbsp;</jp><en>Label display type. &nbsp;&nbsp;</en>
     * @return <jp>メインメニューのHTML &nbsp;&nbsp;</jp><en>HTML of main menu &nbsp;&nbsp;</en>
     */
    private String createHtmlTypeA(jp.co.daifuku.emanager.database.entity.MainMenu[] mainMenuEntity,
            String labelDispType)
    {
        StringBuffer sb = new StringBuffer();
        String contextPath = this.getHttpRequest().getContextPath();

        for (int i = 0; mainMenuEntity != null && i < mainMenuEntity.length; i++)
        {
            String menuid = mainMenuEntity[i].getMainMenuId();
            String title = DispResources.getText(mainMenuEntity[i].getMenuResourceKey());

            // image
            sb.append("<td width='28'");
            sb.append("  style='cursor:hand;' \n");
            sb.append("  onmouseover=\"change_Image('over', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onmousedown=\"change_Image('down', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onmouseout=\"change_Image('out', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onmouseup=\"change_Image('up', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onclick=\"if(wSubmitFlag!=true) { event.cancelBubble=true; return; } wSubmitFlag=false; openSubMenu('"
                    + menuid + "'); \n");
            sb.append("          change_Image('click', 'menu_" + menuid + "', '" + menuid + "');" + "\" ");
            sb.append(">");

            sb.append("<img src='" + contextPath + "/img/project/menu/typea/icon_" + menuid + "_0" + ".gif' ");
            sb.append("  bwidth='23' ");
            sb.append("  height='23' ");
            sb.append("  border='0' ");
            sb.append("  id='menu_" + menuid + "' ");
            sb.append(">");

            sb.append("</td>\n");

            // label
            sb.append("<td nowrap>\n");
            sb.append("<span class='lbl-white-002' id='menut_" + menuid + "'");
            sb.append("style='cursor:hand; ");
            if (labelDispType != null && (labelDispType.equals("tooltip") || labelDispType.equals("stretch")))
            {
                sb.append("display=none; ");
            }
            sb.append(" ' \n");
            sb.append("  onmouseover=\"change_Image('over', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onmousedown=\"change_Image('down', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onmouseout=\"change_Image('out', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onmouseup=\"change_Image('up', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onclick=\"if(wSubmitFlag!=true) { event.cancelBubble=true; return; } wSubmitFlag=false; openSubMenu('"
                    + menuid + "'); \n");
            sb.append("           change_Image('click', 'menu_" + menuid + "', '" + menuid + "');" + "\" ");
            sb.append(">");
            sb.append(title);
            sb.append("</span>\n");
            sb.append("</td>\n");

            // blank
            sb.append("<td width='25'></td>\n");
        }

        return sb.toString();
    }

    /**
     * <jp>B タイプ (大アイコン) のメインメニューHTMLを生成します。 <br></jp> 
     * <en>Create html for typeB . <br></en>
     * 
     * @param mainMenuEntity <jp>メインメニューのエンティティ配列 &nbsp;&nbsp;</jp><en>MainMenuEntity array &nbsp;&nbsp;</en>
     * @return <jp>メインメニューのHTML &nbsp;&nbsp;</jp><en>HTML of main menu &nbsp;&nbsp;</en>
     */
    private String createHtmlTypeB(jp.co.daifuku.emanager.database.entity.MainMenu[] mainMenuEntity)
    {
        StringBuffer sb = new StringBuffer();
        String contextPath = this.getHttpRequest().getContextPath();
        String locale = this.getHttpRequest().getLocale().toString();

        for (int i = 0; mainMenuEntity != null && i < mainMenuEntity.length; i++)
        {
            String menuid = mainMenuEntity[i].getMainMenuId();
            sb.append("<td width='77'>\n");
            sb.append("<img src='" + contextPath + "/img/project/menu/typeb/icon2_" + menuid + "_0_" + locale
                    + ".gif' ");
            sb.append("  width='77' ");
            sb.append("  height='77' ");
            sb.append("  border='0' ");
            sb.append("  id='menu_" + menuid + "' ");
            sb.append("  style='cursor:hand;' \n");
            sb.append("  onmouseover=\"change_ImageB('over', 'menu_" + menuid + "', '" + menuid + "', '" + locale
                    + "');" + "\" \n");
            sb.append("  onmousedown=\"change_ImageB('down', 'menu_" + menuid + "', '" + menuid + "', '" + locale
                    + "');" + "\" \n");
            sb.append("  onmouseout=\"change_ImageB('out', 'menu_" + menuid + "', '" + menuid + "', '" + locale + "');"
                    + "\" \n");
            sb.append("  onmouseup=\"change_ImageB('up', 'menu_" + menuid + "', '" + menuid + "', '" + locale + "');"
                    + "\" \n");
            sb.append("  onclick=\"if(wSubmitFlag!=true) { event.cancelBubble=true; return; } wSubmitFlag=false; openSubMenu('"
                    + menuid + "'); \n");
            sb.append("          change_ImageB('click', 'menu_" + menuid + "', '" + menuid + "', '" + locale + "');"
                    + "\" ");
            sb.append(">");
            sb.append("</td>\n");
        }

        return sb.toString();
    }

    /**
     * <jp>A (小アイコン) タイプのメインメニューHTMLを生成します。 <br></jp> 
     * <en>Create html for typeA. <br></en>
     * 
     * @param mainMenuEntity <jp>メインメニューのエンティティ配列 &nbsp;&nbsp;</jp><en>MainMenuEntity array &nbsp;&nbsp;</en>
     * @param labelDispType <jp>ラベルの表示タイプ &nbsp;&nbsp;</jp><en>Label display type. &nbsp;&nbsp;</en>
     * @return <jp>メインメニューのHTML &nbsp;&nbsp;</jp><en>HTML of main menu &nbsp;&nbsp;</en>
     */
    private String createHtmlTypeACarousel(jp.co.daifuku.emanager.database.entity.MainMenu[] mainMenuEntity)
    {
        StringBuffer sb = new StringBuffer();
        String contextPath = this.getHttpRequest().getContextPath();

        // left button
        sb.append("<td nowarp valign='middle'>\n");
        sb.append("<img src='" + contextPath
                + "/img/common/left_2.gif' width='40' height='23' class='prev' id='arrow_prev')>");
        sb.append("</td>\n");

        // carousel start
        sb.append("<td nowarp>\n");
        sb.append("<div class='carousel'>\n");
        sb.append(" <ul>\n");

        for (int i = 0; mainMenuEntity != null && i < mainMenuEntity.length; i++)
        {
            String menuid = mainMenuEntity[i].getMainMenuId();
            String title = DispResources.getText(mainMenuEntity[i].getMenuResourceKey());

            sb.append(" <li><table border='0'><tr valign='middle'><td>");

            // image
            sb.append("<span");
            sb.append("  style='cursor:hand;' \n");
            sb.append("  onmouseover=\"change_Image('over', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onmousedown=\"change_Image('down', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onmouseout=\"change_Image('out', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onmouseup=\"change_Image('up', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onclick=\"if(wSubmitFlag!=true) { event.cancelBubble=true; return; } wSubmitFlag=false; openSubMenu('"
                    + menuid + "'); \n");
            sb.append("          change_Image('click', 'menu_" + menuid + "', '" + menuid + "');" + "\" ");
            sb.append(">");

            sb.append("<img src='" + contextPath + "/img/project/menu/typea/icon_" + menuid + "_0" + ".gif' ");
            sb.append("  bwidth='23' ");
            sb.append("  height='23' ");
            sb.append("  border='0' ");
            sb.append("  id='menu_" + menuid + "' ");
            sb.append(">");

            sb.append("</span></td>\n<td>");

            // label
            sb.append("<span class='lbl-white-002' id='menut_" + menuid + "'");
            sb.append("style='cursor:hand; ");
            sb.append(" ' \n");
            sb.append("  onmouseover=\"change_Image('over', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onmousedown=\"change_Image('down', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onmouseout=\"change_Image('out', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onmouseup=\"change_Image('up', 'menu_" + menuid + "', '" + menuid + "');" + "\" \n");
            sb.append("  onclick=\"if(wSubmitFlag!=true) { event.cancelBubble=true; return; } wSubmitFlag=false; openSubMenu('"
                    + menuid + "'); \n");
            sb.append("           change_Image('click', 'menu_" + menuid + "', '" + menuid + "');" + "\" ");
            sb.append(">");

            sb.append(title);
            //sb.append(fillRight(title, "&nbsp;", CHAR_LENGTH));
            sb.append("</span>\n");

            // blank
            if (mainMenuEntity.length - 1 > i)
            {
                sb.append("<span width='25' nowrap>&nbsp;</span>");
            }

            sb.append("</td></tr></table></li>\n");
        }

        // carousel end
        sb.append(" </ul>\n");
        sb.append("</div>\n");
        sb.append("</td>\n");

        // right button
        sb.append("<td nowarp valign='middle'>\n");
        sb.append("<img src='" + contextPath
                + "/img/common/right_2.gif' width='40' height='23' class='next' id='arrow_next'>");
        sb.append("</td>\n");

        // add start 2008/06/23
        sb.append(getCurouselScript());
        // add end 2008/06/23
        
        return sb.toString();
    }

    /**
     * <jp>B タイプ (大アイコン) のメインメニューHTMLを生成します。 <br></jp> 
     * <en>Create html for typeB . <br></en>
     * 
     * @param mainMenuEntity <jp>メインメニューのエンティティ配列 &nbsp;&nbsp;</jp><en>MainMenuEntity array &nbsp;&nbsp;</en>
     * @return <jp>メインメニューのHTML &nbsp;&nbsp;</jp><en>HTML of main menu &nbsp;&nbsp;</en>
     */
    private String createHtmlTypeBCarousel(jp.co.daifuku.emanager.database.entity.MainMenu[] mainMenuEntity)
    {
        StringBuffer sb = new StringBuffer();
        String contextPath = this.getHttpRequest().getContextPath();
        String locale = this.getHttpRequest().getLocale().toString();

        // left button
        sb.append("<td nowarp>\n");
        sb.append("<img src='" + contextPath + "/img/common/left_2.gif' width='40' height='23' class='prev' id='arrow_prev'>");
        sb.append("</td>\n");

        // carousel start
        sb.append("<td nowarp>\n");
        sb.append("<div class='carousel'>\n");
        sb.append(" <ul>\n");

        for (int i = 0; mainMenuEntity != null && i < mainMenuEntity.length; i++)
        {
            String menuid = mainMenuEntity[i].getMainMenuId();
            sb.append(" <li>");
            
            sb.append("<span>\n");
            sb.append("<img src='" + contextPath + "/img/project/menu/typeb/icon2_" + menuid + "_0_" + locale
                    + ".gif' ");
            sb.append("  width='77' ");
            sb.append("  height='77' ");
            sb.append("  border='0' ");
            sb.append("  id='menu_" + menuid + "' ");
            sb.append("  style='cursor:hand;' \n");
            sb.append("  onmouseover=\"change_ImageB('over', 'menu_" + menuid + "', '" + menuid + "', '" + locale
                    + "');" + "\" \n");
            sb.append("  onmousedown=\"change_ImageB('down', 'menu_" + menuid + "', '" + menuid + "', '" + locale
                    + "');" + "\" \n");
            sb.append("  onmouseout=\"change_ImageB('out', 'menu_" + menuid + "', '" + menuid + "', '" + locale + "');"
                    + "\" \n");
            sb.append("  onmouseup=\"change_ImageB('up', 'menu_" + menuid + "', '" + menuid + "', '" + locale + "');"
                    + "\" \n");
            sb.append("  onclick=\"if(wSubmitFlag!=true) { event.cancelBubble=true; return; } wSubmitFlag=false; openSubMenu('"
                    + menuid + "'); \n");
            sb.append("          change_ImageB('click', 'menu_" + menuid + "', '" + menuid + "', '" + locale + "');"
                    + "\" ");
            sb.append(">");
            sb.append("</span>\n");
            
            sb.append("</li>\n");
        }

        // carousel end
        sb.append(" </ul>\n");
        sb.append("</div>\n");
        sb.append("</td>\n");

        // right button
        sb.append("<td nowarp>\n");
        sb.append("<img src='" + contextPath + "/img/common/right_2.gif' width='40' height='23' class='next' id='arrow_next'>");
        sb.append("</td>\n");

        // add start 2008/06/23
        sb.append(getCurouselScript());
        // add end 2008/06/23

        return sb.toString();
    }

    /**
     * カルーセルメニュー表示用のscriptを取得します
     * 
     * @return カルーセルメニュー表示用のscript
     */
    private String getCurouselScript()
    {
        String contextPath = this.getHttpRequest().getContextPath();
        String speed = EmProperties.getProperty(EmConstants.EMPARAMKEY_CUROUSEL_SPEED);
        String divWidth = EmProperties.getProperty(EmConstants.EMPARAMKEY_CUROUSEL_DIV_WIDTH);
        
        StringBuffer sb = new StringBuffer();
        sb.append("<script type=\"text/javascript\">\n");
        sb.append("  $(function() {\n");
        sb.append("    $(\".carousel\").jCarouselLite({\n");
        sb.append("      btnNext: \".next\",\n");
        sb.append("      btnPrev: \".prev\",\n");
        sb.append("      speed: " + speed + ",\n");
        sb.append("      mouseWheel: true,\n");
        sb.append("      divWidth: " + divWidth + ",\n");
        sb.append("      btnPrevId: \"arrow_prev\",\n");
        sb.append("      btnNextId: \"arrow_next\",\n");
        sb.append("      prevArrowEna: \"" + contextPath + "/img/common/left_1.gif\",\n");
        sb.append("      prevArrowDis: \"" + contextPath + "/img/common/left_2.gif\",\n");
        sb.append("      nextArrowEna: \"" + contextPath + "/img/common/right_1.gif\",\n");
        sb.append("      nextArrowDis: \"" + contextPath + "/img/common/right_2.gif\"\n");
        sb.append("    });\n");
        sb.append("  });\n");
        sb.append("</script>\n");      
        
        return sb.toString();
    }
    
    
    /**
     * <jp>DBを検索してメインメニューのエンティティ配列を取得します。 <br></jp> 
     * <en>Return the main menu entity.<br></en>
     * 
     * @param conn Connection
     * @return MainMenuEntity <jp>メインメニューのエンティティ配列 &nbsp;&nbsp;</jp><en>MainMenuEntity array &nbsp;&nbsp;</en>
     * @throws SQLException
     */
    private jp.co.daifuku.emanager.database.entity.MainMenu[] getMainMenuEntity(Connection conn)
            throws SQLException
    {
        AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
        AuthenticationSystem authEntity = authHandler.findAuthenticationSystem();

        DfkUserInfo userinfo = (DfkUserInfo)this.getUserInfo();

        String terminalRoleId = userinfo.getTerminalRoleId();
        String menuRoleId = userinfo.getMenuRoleId();

        MainMenuHandler handler = EmHandlerFactory.getMainMenuHandler(conn);
        jp.co.daifuku.emanager.database.entity.MainMenu mainMenuEntities[] = null;

        if (userinfo.getUserId().equals(EmConstants.DAIFUKU_SV_USER))
        {
            mainMenuEntities = handler.findAll(1);
        }
        else if (menuRoleId.equals(EmConstants.ADMIN_ROLE))
        {
            if (authEntity.getTerminalAdminRoleCheckFlag())
            {
                mainMenuEntities = handler.findByRoleIds(menuRoleId, terminalRoleId);
            }
            else
            {
                mainMenuEntities = handler.findByRoleIds(menuRoleId, menuRoleId);
            }
        }
        else
        {
            mainMenuEntities = handler.findByRoleIds(menuRoleId, terminalRoleId);
        }

        return mainMenuEntities;
    }

}
// end of class

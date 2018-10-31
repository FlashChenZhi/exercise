// $Id: SubMenuBusiness.java 8053 2013-05-15 01:00:52Z kishimoto $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.menu;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.exception.AuthenticationException;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.JspConstants;
import jp.co.daifuku.emanager.authentication.AutoAuthentication;
import jp.co.daifuku.emanager.authentication.EmAuthenticationException;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.entity.Function;
import jp.co.daifuku.emanager.database.entity.MainMenu;
import jp.co.daifuku.emanager.database.entity.SubmenuFunction;
import jp.co.daifuku.emanager.database.handler.AuthenticationHandler;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.FunctionHandler;
import jp.co.daifuku.emanager.database.handler.MainMenuHandler;
import jp.co.daifuku.emanager.database.handler.SubmenuFunctionHandler;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.emanager.util.P11LogWriter;

/**
 * <jp>サブメニューの画面クラスです。<br></jp>
 * <en>It is a screen class of the submenu. <br></en>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>K.Fukumori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  $Author: kishimoto $
 */
public class SubMenuBusiness
        extends SubMenu
{
    // Class fields --------------------------------------------------

    /** context path. */
    private String contextPath;

    /** URI of spacer gif. */
    private static final String spaceGifUri = "/img/common/spacer.gif";

    /** URI of colon (:) gif.  */
    private static final String colonGifUri = "/img/common/icon_colon2.gif";

    /** URI of loginCheckScreen */
    private static final String loginCheckScreenUri = "/login/ScreenLogin.do";

// Add Start 2010/02/23 T.Sumiyama for v5.2
    /** Style for Image **/
    private static final String imgStyle = "style=\"vertical-align:baseline;\"";
 // Add Start 2010/02/23 T.Sumiyama for v5.2

    /** Spacer tag for background */
    private String spacerTag1;

    /** Spacer tag (width:24, height:1) */
    private String spacerTag2;

    /** Colon tag */
    private String colonTag;

    /** Blank line tag */
    private String blankRowTag;

    /** <jp>１行に表示するボタンの最大数 &nbsp;&nbsp;</jp><en>Max number of button on 1 line. &nbsp;&nbsp;</en> */
    private final int MAXCOL = 4;

    /**
     * <jp>画面の初期化を行います。<br></jp>
     * <en>Initialize a screen. <br></en>
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        // create accessLog out..
        accessLogOut((DfkUserInfo)this.getUserInfo());

        // 2012/11/06 ADD START
        DfkUserInfo userInfo = (DfkUserInfo)this.getUserInfo();

        // システムログインが自動ログインの場合ログインユーザを自動ログインに戻す。
        // ただし、DAIFUKUユーザは例外
        if(EmConstants.ANONYMOUS_USER.equals(userInfo.getSystemLoginUserId())
                && !EmConstants.DAIFUKU_SV_USER.equals(userInfo.getUserId())
                && !EmConstants.ANONYMOUS_USER.equals(userInfo.getUserId()))
        {
            try
            {
                //認証クラス生成
                AutoAuthentication auth = new AutoAuthentication();
                String ipAddress = this.httpRequest.getRemoteAddr();

                DfkUserInfo userInfo2 = auth.authenticate(ipAddress, this.session);

                //UserInfoにセット
                userInfo2.setTerminalName(userInfo.getTerminalName());
                userInfo2.setTerminalNumber(userInfo.getTerminalNumber());
                userInfo2.setTerminalPrinterName(userInfo.getTerminalPrinterName());
                userInfo2.setTerminalRoleId(userInfo.getTerminalRoleId());
                userInfo2.setMainmenuId(userInfo.getMainmenuId());
                //認証->成功時はセッションに保持
                setUserInfo(userInfo2);
            }
            catch(EmAuthenticationException ex)
            {
                // エラー時は自動ログイン不可なので処理を実施しない
            }

        }
        userInfo = (DfkUserInfo)this.getUserInfo();

        String username = userInfo.getUserName();
        if (username == null || username.length() == 0)
        {
            username = userInfo.getUserId();
        }
        this.httpRequest.setAttribute(JspConstants.RPARAMKEY_LOGINUSERNAME, username);

        // 2012/11/06 ADD END
    }

    /**
     * <jp> 発生条件：各コントロールイベント呼び出し前に呼び出されます。<br></jp>
     * <en>Generating conditions: It is called before each control event call. <br></en>
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        // Check the user info
        if (!(this.getUserInfo() instanceof DfkUserInfo))
        {
            this.removeUserInfo();
            throw new AuthenticationException();
        }

// 2008/12/16 K.Matsuda Start 自動ログイン時、ユーザを戻す

// 2008/12/16 K.Matsuda End

        init();
        Connection conn = null;
        String menutitle = "";
        String subMenuHtml = "";
        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);

            // Create Title
            String mainMenuId = httpRequest.getParameter(JspConstants.RPARAMKEY_MAINMENUID);
            menutitle = getSubMenuTitle(mainMenuId, conn);

            if (mainMenuId != null)
            {
                menutitle = menutitle + DispResources.getText("TLE-T0034");
            }
            else
            {
                menutitle = "&nbsp";
            }

            // get ScreenLoginCheck flag
            AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
            AuthenticationSystem authEntity = authHandler.findAuthenticationSystem();
            //	boolean screeLoginCheckFlag = authEntity.getScreenLoginCheckFlag();

            // Create SubMenu HTML
            SubmenuFunction submenuEntities[] = getSubMenuEntities(mainMenuId, conn, authEntity);
            subMenuHtml = createHtml(submenuEntities);
        }
        catch (SQLException exception)
        {
            String message = MessageResources.getText("6403069");
            throw new Exception(message, exception);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }

        this.httpRequest.setAttribute(JspConstants.RPARAMKEY_SUBMENUTITLE, menutitle);
        this.httpRequest.setAttribute(JspConstants.RPARAMKEY_SUBMENUHTML, subMenuHtml);

// 2008/12/16 K.Matsuda Start 自動ログイン時、ユーザを戻す

// 2008/12/16 K.Matsuda End
    }

    /**
     * <jp>高速化のため、静的なHTMLタグをあらかじめ作成します。<br></jp>
     * <en>Preparate the static HTML tag for performance. <br></en>
     */
    private void init()
    {
        this.contextPath = this.httpRequest.getContextPath();

        this.spacerTag1 =
                "<td bgcolor='#dad9ee' width='7' height='1'><img src='" + contextPath + spaceGifUri
                        + "' width='7' height='1'></td>";
// Mod Start 2010/02/23 T.Sumiyama for v5.2
      this.spacerTag2 =
      "<td width='24' height='1'><img src='" + contextPath + spaceGifUri + "' width='24' height='1' " + imgStyle + "></td>";
//        this.spacerTag2 =
//                "<td width='24' height='1'><img src='" + contextPath + spaceGifUri + "' width='24' height='1'></td>";
// Mod End 2010/02/23 T.Sumiyama for v5.2
        this.colonTag =
                spacerTag2 + "<td width='6'><img src='" + contextPath + colonGifUri
                        + "' width='6' height='29' border='0'></td>" + spacerTag2;
        this.blankRowTag = createBlankRow(MAXCOL * 2 + 6);
    }

    /**
     * <jp>サブメニューのタイトルを取得します。 <br></jp>
     * <en>Return the title of submenu. <br></en>
     * @param mainMenuId <jp>メインメニューID &nbsp;&nbsp;</jp><en>Main menu ID &nbsp;&nbsp;</en>
     * @param conn <jp>Connection &nbsp;&nbsp;</jp><en>Connection &nbsp;&nbsp;</en>
     * @return <jp>サブメニューのタイトル &nbsp;&nbsp;</jp><en>Title of submenu &nbsp;&nbsp;</en>
     * @throws SQLException
     *
     */
    private String getSubMenuTitle(String mainMenuId, Connection conn)
            throws SQLException
    {
        String title = null;
        MainMenuHandler handler = EmHandlerFactory.getMainMenuHandler(conn);
        MainMenu mainMenuEntity = handler.findByMenuId(mainMenuId);
        if (mainMenuEntity != null)
        {
            String rKey = mainMenuEntity.getMenuResourceKey();
            title = DispResources.getText(rKey);
        }

        return title;
    }

    /**
     * <jp>サブメニューのHTMLを生成します。 <br></jp>
     * <en>Create the HTML for SubMenu. <br></en>
     * @param submenuEntities <jp>SubMenuFunctionEntityクラスの配列 &nbsp;&nbsp;</jp><en>SubMenuFunctionEntity array. &nbsp;&nbsp;</en>
     * @param screenLoginCheckFlag <jp>画面ログイン画面に遷移するかのフラグ &nbsp;&nbsp;</jp><en>Flag for forward to screen login screen. &nbsp;&nbsp;</en>
     * @return <jp>サブメニューのHTML &nbsp;&nbsp;</jp><en>HTML of submenu. &nbsp;&nbsp;</en>
     */
    private String createHtml(SubmenuFunction[] submenuEntities)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<table cellspacing='0' cellpadding='0'>");

        int lineCount = 0;
        for (int i = 0; submenuEntities != null && i < submenuEntities.length; i++)
        {
            String bgcolor = getLineBgColor(lineCount);
            Function functionEntities[] = submenuEntities[i].getFunctionArray();
            sb.append(blankRowTag);
            int buttonCount = 0;

            sb.append("<tr bgcolor='" + bgcolor + "'>");

            // spacer
            sb.append(spacerTag1);

            // subtitle (td * 5)
            sb.append(createSubTitle(submenuEntities[i], bgcolor));


            // button
            for (int j = 0; functionEntities != null && j < functionEntities.length; j++)
            {
                String buttonHtml = createButton(functionEntities[j], submenuEntities[i].getMainMenuId());
                if (buttonHtml != null)
                {
                    // button
                    sb.append("<td bgcolor='" + bgcolor + "'>");
                    sb.append(buttonHtml);
                    sb.append("</td>\n");

                    // spacer
                    sb.append(spacerTag2);

                    buttonCount++;
                }

            }
            for (int j = buttonCount; j < MAXCOL; j++)
            {
                // empty
                sb.append("<td bgcolor='" + bgcolor + "'></td>\n");

                // spacer
                sb.append(spacerTag2);
            }

            // space for fill
            sb.append("<td bgcolor='" + bgcolor + "' width='100%' height='1'>");
// Mod Start 2010/02/23 T.Sumiyama for v5.2
            sb.append("<img src='" + contextPath + spaceGifUri + "' width='7' height='1' " + imgStyle + ">");
//            sb.append("<img src='" + contextPath + spaceGifUri + "' width='7' height='1'>");
// Mod End 2010/02/23 T.Sumiyama for v5.2
            sb.append("</td>");

            // end spacer
            sb.append(spacerTag1);


            sb.append("</tr>\n");

            lineCount++;
        }
        sb.append("</table>");

        return sb.toString();
    }

    /**
     * <jp>サブメニューの各タイトルのHTMLを生成します。 <br></jp>
     * <en>Create the HTML for SubTitle of Submenu. <br></en>
     * @param subMenuEntity <jp>SubMenuEntityクラス &nbsp;&nbsp;</jp><en>SubMenuEntity &nbsp;&nbsp;</en>
     * @param bgcolor <jp>背景色 &nbsp;&nbsp;</jp><en>BgColor &nbsp;&nbsp;</en>
     * @return　<jp>サブメニューの各タイトルのHTML &nbsp;&nbsp;</jp><en>HTML for SubTitle of Submenu &nbsp;&nbsp;</en>
     */
    private String createSubTitle(jp.co.daifuku.emanager.database.entity.SubMenu subMenuEntity, String bgcolor)
    {
        String rKey = subMenuEntity.getSubMenuResourceKey();

        return spacerTag2 + "<td width='220' nowrap><span class='lbl-SubMebuContensName'>" + DispResources.getText(rKey)
                + "</span></td>" + colonTag;
    }


    /**
     * <jp>区切り線のHTMLを生成します。 <br></jp>
     * <en>Create the split line for table. <br></en>
     * @param colspan colspan
     * @return <jp区切り線のHTML &nbsp;&nbsp;</jp><en>split line HTML. &nbsp;&nbsp;</en>
     */
    private String createBlankRow(int colspan)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<tr height='1'>");
        sb.append("<td bgcolor='#dad9ee' width='7' height='1'>");

// Mod Start 2010/02/23 T.Sumiyama for v5.2
        sb.append("<img src='" + contextPath + spaceGifUri + "' width='7' height='1' " + imgStyle + ">");
//        sb.append("<img src='" + contextPath + spaceGifUri + "' width='7' height='1'>");
// Mod End 2010/02/23 T.Sumiyama for v5.2

        sb.append("</td>");
        sb.append("<td bgcolor='#dad9ee' width='100%' height='1' colspan='" + colspan + "'></td>");
        sb.append("<td bgcolor='#dad9ee' width='7' height='1'>");

// Mod Start 2010/02/23 T.Sumiyama for v5.2
        sb.append("<img src='" + contextPath + spaceGifUri + "' width='7' height='1' " + imgStyle + ">");
//        sb.append("<img src='" + contextPath + spaceGifUri + "' width='7' height='1'>");
// Mod End 2010/02/23 T.Sumiyama for v5.2

        sb.append("</tr>\n");
        return sb.toString();
    }

    /**
     * <jp>サブメニューの背景色を取得します。 <br></jp>
     * <en>Return the color of submenu list. <br></en>
     * @param argLineNo <jp>行番号 &nbsp;&nbsp;</jp><en>Number of line &nbsp;&nbsp;</en>
     * @return <jp>背景色 &nbsp;&nbsp;</jp><en>Background color &nbsp;&nbsp;</en>
     */
    private String getLineBgColor(int argLineNo)
    {
        if (argLineNo % 2 == 0)
        {
            return "#B0ADD3";
        }

        return "#C3C0E0";
    }

    /**
     * <jp>ボタンのHTMLを生成します。 <br></jp>
     * <en>Create the HTML for button. <br></en>
     * @param functionEntity <jp>FunctionEntityクラス &nbsp;&nbsp;</jp><en>FunctionEntity &nbsp;&nbsp;</en>
     * @param mainMenuId <jp> &nbsp;&nbsp;</jp><en> &nbsp;&nbsp;</en>
     * @param screenLoginCheckFlag <jp>画面ログイン画面に遷移するかのフラグ &nbsp;&nbsp;</jp><en>Flag for forward to screen login screen. &nbsp;&nbsp;</en>
     * @return <jp>ボタンのHTML &nbsp;&nbsp;</jp><en>HTML &nbsp;&nbsp;</en>
     */
    private String createButton(Function functionEntity, String mainMenuId)
    {
        if (functionEntity == null)
        {
            return null;
        }

        // ScreenLoginCheck
        //boolean doAuthenticationFlag = functionEntity.getDoAuthenticationFlag();

        String dispResourceKey = functionEntity.getButtonResourceKey();
        String uri = functionEntity.getUrl();
        String title = functionEntity.getPageResourceKey();
        String functionid = functionEntity.getFunctionId();
        String frame = functionEntity.getFrameName();
        String dsNumber = functionEntity.getDsNumber();

        StringBuffer sb = new StringBuffer();
        sb.append("<input type='button' class='btn-submenu-001' ");
        sb.append("id='" + functionid + "' ");
        sb.append("value='" + DispResources.getText(dispResourceKey) + "' ");

        sb.append("onclick=\"openWindow('");
        sb.append(contextPath);

        sb.append(loginCheckScreenUri);
        sb.append("?" + Constants.MENUPARAM + "=" + title + "," + functionid + "," + mainMenuId);
        sb.append("&" + EmConstants.RKEY_FUNCTIONURI + "=" + uri);
        sb.append("&" + EmConstants.RKEY_DSNUMBER + "=" + dsNumber + "'");
        sb.append(",'" + frame + "');\"");


        sb.append(">");
        return sb.toString();
    }

    /**
     * <jp>SubmenuFunctionエンティティ配列をDBから取得します。 <br></jp>
     * <en>Return the get SubMenuEntity array from DB. <br></en>
     * @param mainMenuId <jp>メインメニューID &nbsp;&nbsp;</jp><en>MainMenuID &nbsp;&nbsp;</en>
     * @param conn Connection
     * @return <jp>SubmenuFunctionエンティティ配列 &nbsp;&nbsp;</jp><en>SubMenuEntity array &nbsp;&nbsp;</en>
     * @throws SQLException
     */
    private SubmenuFunction[] getSubMenuEntities(String mainMenuId, Connection conn, AuthenticationSystem authEntity)
            throws SQLException
    {
        DfkUserInfo userinfo = (DfkUserInfo)this.getUserInfo();

        String terminalRoleId = userinfo.getTerminalRoleId();
        String menuRoleId = userinfo.getMenuRoleId();

        SubmenuFunctionHandler handler = EmHandlerFactory.getSubmenuFunctionHandler(conn);
        SubmenuFunction subMenuFunctionEntities[] = null;

        if (userinfo.getUserId().equals(EmConstants.DAIFUKU_SV_USER))
        {
            subMenuFunctionEntities = handler.findSubmenuFunctionByMenuId(mainMenuId, 0, 0);
        }
        else if (menuRoleId.equals(EmConstants.ADMIN_ROLE))
        {
            //subMenuFunctionEntities = handler.findFunctionsByRoles(mainMenuId, menuRoleId, menuRoleId);

            // if terminal role rules are applicable , get submenu list based on terminal role
            if (authEntity.getTerminalAdminRoleCheckFlag())
            {
                subMenuFunctionEntities = handler.findFunctionsByRoles(mainMenuId, menuRoleId, terminalRoleId);
            }
            else
            {
                subMenuFunctionEntities = handler.findFunctionsByRoles(mainMenuId, menuRoleId, menuRoleId);
            }
        }
        else
        {
            subMenuFunctionEntities = handler.findFunctionsByRoles(mainMenuId, menuRoleId, terminalRoleId);
        }

        return subMenuFunctionEntities;
    }

    /**
     * <jp <br></jp>
     * <en>This method updates logged in dsnumber and page resource key<br></en>
     */
    private void accessLogOut(DfkUserInfo userInfo)
            throws SQLException
    {
        // メインメニューが連続で押下された場合に2重でログが書き込まれないためにロックする
        // サーバーの負荷が高いときに当メソッドが同時に実行されるとDS番号をnullでDBに登録しエラーが発生する
        synchronized (SubMenuBusiness.class)
        {
            // if ds number is not null .. user has to be loged out from function screen.
            // user logged into the function screen at Screen login screen
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

            //update page information
            userInfo.setDsNumber(null);
            userInfo.setFunctionId(null);
            userInfo.setPageNameResourceKey(null);
            userInfo.setPageName(null);
        }
    }
}
//end of class

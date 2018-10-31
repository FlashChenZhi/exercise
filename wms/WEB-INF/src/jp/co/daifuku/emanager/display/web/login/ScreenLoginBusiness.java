// $Id: ScreenLoginBusiness.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.login;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.exception.IllegalEventException;
import jp.co.daifuku.bluedog.util.Converter;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.authentication.EmAuthenticationException;
import jp.co.daifuku.emanager.authentication.ScreenAuthentication;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.entity.Function;
import jp.co.daifuku.emanager.database.entity.Terminal;
import jp.co.daifuku.emanager.database.handler.AuthenticationHandler;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.FunctionHandler;
import jp.co.daifuku.emanager.database.handler.TerminalHandler;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.emanager.util.P11LogWriter;

/** 
 * <jp>ログイン画面クラスです。<br></jp>
 * <en>It is a login screen class. <br></en>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 */
public class ScreenLoginBusiness
        extends ScreenLogin
{

    /** 
     * <jp>画面の初期化を行います。<br></jp>
     * <en>Initialize a screen. <br></en>
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            String paramUri = this.getHttpRequest().getParameter(EmConstants.RKEY_FUNCTIONURI);
            String menuParam = this.getHttpRequest().getParameter(Constants.MENUPARAM);
            String dsNumebr = this.getHttpRequest().getParameter(EmConstants.RKEY_DSNUMBER);
            String pageResourceKey = menuParam.split(",")[0];
            String functionId = menuParam.split(",")[1];

            conn = EmConnectionHandler.getConnection();
            boolean doAuthenticationFlag = getDoAuthenticationFlag(functionId, conn);

            // 最新のプリンタ名をユーザ情報に設定する for eManager2.0.1(BusiTune対応)
            DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
            userInfo.setTerminalPrinterName(getPrinterName(conn, userInfo.getTerminalNumber()));

            // Access log true and Screenlogin flag is false.. forward to function screen after creating access log
            if (P11Config.isAccessLog() && !P11Config.isScreenLoginFlag()
                    || (P11Config.isAccessLog() && !doAuthenticationFlag))
            {
                // update dfK user info with DsNumber etc , Access Log(log out) has to be created if user clicks on menu or return
                DfkUserInfo currentUserInfo = (DfkUserInfo)getUserInfo();
                currentUserInfo.setDsNumber(dsNumebr);
                currentUserInfo.setFunctionId(functionId);
                currentUserInfo.setPageNameResourceKey(pageResourceKey);
                createNoLoginAccessLog(conn);
                EmConnectionHandler.commit(conn);
                forward("/" + paramUri + ".do" + "?" + Constants.MENUPARAM + "=" + menuParam);
            }
            //No Access Log and No screen Login, forward to function screen OR
            //screen log is true and Function authentication is false.. no log is required , forward to Function Screem
            else if ((!P11Config.isAccessLog() && !P11Config.isScreenLoginFlag())
                    || (P11Config.isScreenLoginFlag() && !doAuthenticationFlag))
            {
                // update dfK user info with DsNumber etc , Access Log(log out) has to be created if user clicks on menu or return
                DfkUserInfo currentUserInfo = (DfkUserInfo)getUserInfo();
                currentUserInfo.setDsNumber(dsNumebr);
                currentUserInfo.setFunctionId(functionId);
                currentUserInfo.setPageNameResourceKey(pageResourceKey);
                forward("/" + paramUri + ".do" + "?" + Constants.MENUPARAM + "=" + menuParam);
            }
            // show Screen login screen
            else
            {
                this.viewState.setString(EmConstants.RKEY_FUNCTIONURI, paramUri);
                this.viewState.setString(Constants.MENUPARAM, menuParam);
                this.viewState.setString(EmConstants.RKEY_FUNCTIONID, functionId);
                this.viewState.setString(EmConstants.RKEY_DSNUMBER, dsNumebr);
                this.viewState.setString(EmConstants.PAGENAMERESOUCEKEY, pageResourceKey);
                setFocus(txt_LoginID);
            }
            return;

        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            EmConnectionHandler.rollback(conn);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }

    }

    /**
     * 端末番号に対応するプリンタ名称を取得する。
     * 
     * @param conn DBコネクション
     * @param terminalNumber 端末番号
     * @return プリンタ名称
     * @throws SQLException
     */
    private String getPrinterName(Connection conn, String terminalNumber)
            throws SQLException
    {
        TerminalHandler trmhdl = EmHandlerFactory.getTerminalHandler(conn);
        Terminal terminal = trmhdl.findByTerminalNumber(terminalNumber);
        return terminal == null ? ""
                               : terminal.getPrinterName();
    }

    /** 
     * <jp><br></jp>
     * <en>Create Access Log <br></en>
     * @param dsNumebr String
     * @param pageName String
     * @param conn     Connection 
     * @throws SQLException
     */
    private void createNoLoginAccessLog(Connection conn)
            throws SQLException
    {
        DfkUserInfo currentUserInfo = (DfkUserInfo)getUserInfo();
        String message = "No Screen Login Access Log";
        P11LogWriter p11Writer = new P11LogWriter(conn);
        // 画面ログインをしない為、ユーザIDとユーザ名はシステムログイン時に設定した値を使用する
        p11Writer.createAccessLog(currentUserInfo, currentUserInfo.getSystemLoginUserId(),
                currentUserInfo.getSystemLoginUserName(), EmConstants.AUTHLOG_CLASS_NOSCREENACESS,
                this.httpRequest.getRemoteAddr(), message);
    }

    /** 
     * <jp><br></jp>
     * <en>Method returns the doAuthentication Flag for given function Id<br></en>
     * @param functionId String
     * @param conn     Connection
     * @return flag boolean 
     * @throws SQLException
     */
    private boolean getDoAuthenticationFlag(String functionId, Connection conn)
            throws SQLException
    {
        FunctionHandler functionhandler = EmHandlerFactory.getFunctionHandler(conn);
        Function function = functionhandler.findByFunctionId(functionId);
        return function.getDoAuthenticationFlag();
    }

    /** 
     * <jp>ログインボタンが押下されたときの処理です。<br></jp>
     * <en>It is processing when a login button is pushed. <br></en>
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Login_Click(ActionEvent e)
            throws Exception
    {
        try
        {

// 2008/12/11 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
            ScreenAuthentication auth = checkAuthentication(txt_LoginID.getText(), txt_Password.getText(), true, false);
// 2008/12/11 K.Matsuda End
            
            if (auth == null)
            {
                return;
            }
            //<jp>有効期限切れ間近</jp>
            //<en> Term-of-validity piece nearness. </en>
            if (auth.getResultStatus() == EmConstants.AUTHENTICATION_PWDEXPIRE_ALERT)
            {
                //<jp>パスワード変更画面へ遷移</jp>
                //<en> It changes to a password change screen. </en>
                //              this.viewState.setInt(VSKEY_PASSWORDCHANGE_CAUSE, auth.getResultStatus());
                this.viewState.setString(EmConstants.VSKEY_BACKURI, getBackUri());
                this.viewState.setString(EmConstants.VSKEY_NEXTURI, getNextUri());
                forward("/login/ChangePassword.do");
            }

            // <jp>仮パスワード状態</jp>
            // <en>Dummy password</en>
            else if (auth.getResultStatus() == EmConstants.AUTHENTICATION_DUMMYPASSWORD)
            {
                //<jp>パスワード変更画面へ遷移</jp>
                //<en> It changes to a password change screen. </en>
                //              this.viewState.setInt(VSKEY_PASSWORDCHANGE_CAUSE, auth.getResultStatus());
                this.viewState.setString(EmConstants.VSKEY_BACKURI, getBackUri());
                this.viewState.setString(EmConstants.VSKEY_NEXTURI, getNextUri());
                forward("/login/ChangePassword.do");
            }
// 2008/12/11 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
            // <jp>パスワード有効期限切れ</jp>
            // <en>Dummy password</en>
            else if (auth.getResultStatus() == EmConstants.AUTHENTICATION_PWDEXPIRED)
            {
                //<jp>パスワード変更画面へ遷移</jp>
                //<en> It changes to a password change screen. </en>
                //              this.viewState.setInt(VSKEY_PASSWORDCHANGE_CAUSE, auth.getResultStatus());
                forward("/login/ChangePassword.do");
            }
// 2008/12/11 K.Matsuda End
            else
            {
                String uri = this.viewState.getString(EmConstants.RKEY_FUNCTIONURI);
                ForwardParameters fparam = new ForwardParameters();
                fparam.addParameter(Constants.MENUPARAM, this.viewState.getString(Constants.MENUPARAM));
                forward("/" + uri + ".do" + "?" + Constants.MENUPARAM + "="
                        + this.viewState.getString(Constants.MENUPARAM));
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            String discription = "";
            if (ex.getCause() != null)
            {
                discription = ex.getCause().toString();
            }
            else
            {
                discription = ex.getMessage();
            }

            //MSG-T0041=Error occurred. {0}
            this.setAlert("MSG-T0041" + EmConstants.MSG_DELIM + discription);
        }
    }

    /**
     * <jp>テキストボックス内でEnterキーが押されたらサブミットし、認証を行う。<br></jp>
     * <en>If 'Enter' is pushed in the text box, it submits, and it attests it.<br></en>
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_LoginID_EnterKey(ActionEvent e)
            throws Exception
    {
        btn_Login_Click(null);
    }

    /**
     * <jp>テキストボックス内でEnterキーが押されたらサブミットし、認証を行う。</jp>
     * <en>If 'Enter' is pushed in the text box, it submits, and it attests it.</en>
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_Password_EnterKey(ActionEvent e)
            throws Exception
    {
        btn_Login_Click(null);
    }


    /** 
     * <jp>パスワード変更ボタンが押下されたときの処理です。<br></jp>
     * <en>It is processing when a change password button is pushed. <br></en>
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ChangePassword_Click(ActionEvent e)
            throws Exception
    {
        ScreenAuthentication auth = null;
        try
        {
// 2008/12/11 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
            auth = checkAuthentication(txt_LoginID.getText(), txt_Password.getText(), false, false);
// 2008/12/11 K.Matsuda End
            
            if (auth == null)
            {
                return;
            }

            //<jp>パスワード変更画面へ遷移</jp>
            //<en> It changes to a password change screen. </en>
            this.viewState.setString(EmConstants.VSKEY_BACKURI, getBackUri());
            this.viewState.setString(EmConstants.VSKEY_NEXTURI, getNextUri());
            forward("/login/ChangePassword.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            String discription = "";
            if (ex.getCause() != null)
            {
                discription = ex.getCause().toString();
            }
            else
            {
                discription = ex.getMessage();
            }

            //MSG-T0041=Error occurred. {0}
            this.setAlert("MSG-T0041" + EmConstants.MSG_DELIM + discription);

        }

    }

    /**
     * <jp>認証チェックを行います。 <br></jp>
     * <en>Authentication. <br></en>
     * @param userId <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @param password <jp>パスワード &nbsp;&nbsp;</jp><en>password &nbsp;&nbsp;</en>
     * @return <jp>SystemAuthentication クラス &nbsp;&nbsp;</jp><en>SystemAuthentication class &nbsp;&nbsp;</en>
     * @throws SQLException
     * @throws IllegalEventException
     */
    private ScreenAuthentication checkAuthentication(String userId, String password, boolean isLoginClick, boolean expchekflg)
            throws SQLException,
                IllegalEventException
    {
        if (userId == null || userId.equals(""))
        {
            //<jp>MSG-T0020=ユーザ名を入力してください。</jp>
            //<en> MSG-T0020 = Please input a user name. </en>
            this.setAlert("MSG-T0020");
            setFocus(txt_LoginID);
            return null;
        }
        else if (password == null || password.equals(""))
        {
            //<jp>MSG-T0021=パスワードを入力してください。</jp>
            //<en> MSG-T0021 = Please enter a password. </en>
            this.setAlert("MSG-T0021");
            setFocus(txt_Password);
            return null;
        }

        ScreenAuthentication auth = null;
        Connection conn = EmConnectionHandler.getPageDbConnection(this);
        try
        {
            //<jp>認証クラス生成</jp>
            //<en> Attestation class generation. </en>
            auth = new ScreenAuthentication();
            String functionId = this.viewState.getString(EmConstants.RKEY_FUNCTIONID);
            String dsNumber = this.viewState.getString(EmConstants.RKEY_DSNUMBER);
            String pageResourceKey = this.viewState.getString(EmConstants.PAGENAMERESOUCEKEY);

            DfkUserInfo currentUserInfo = (DfkUserInfo)getUserInfo();
            currentUserInfo.setDsNumber(dsNumber);
            currentUserInfo.setFunctionId(functionId);
            currentUserInfo.setPageNameResourceKey(pageResourceKey);

            String userName = Converter.nullToStr(currentUserInfo.getUserName(), "");

            //<jp>認証->成功時はセッションに保持</jp>
            //<en> It holds at a session at the time of an attestation-success. </en>
// 2008/12/11 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
            DfkUserInfo dfkUserInfo =
                    auth.authenticate(currentUserInfo, userId, password, this.httpRequest.getRemoteAddr(), this.session, expchekflg);
// 2008/12/11 K.Matsuda End

            // If userinfo is changed, update userinfo
            if (!this.getUserInfo().equals(dfkUserInfo))
            {
                // update userinfo
                this.setUserInfo(dfkUserInfo); // may not required this update
                // reload the main menu
                String username = dfkUserInfo.getUserName();
                if (username == null)
                {
                    username = dfkUserInfo.getUserId();
                }
                username = EManagerUtil.ConvertForHtml(username);
                addOnloadScript("try{window.parent.frame1.document.getElementById('username').innerHTML='" + username
                        + "';}catch(e){alert(e.message)}");
            }
            else
            {
                // ユーザ情報に変更が無い場合でもユーザ名称が更新されている場合はメインメニューのユーザ名称を変更する
                if (!userName.equals(dfkUserInfo.getUserName()))
                {
                    String username = dfkUserInfo.getUserName();
                    if (username == null)
                    {
                        username = dfkUserInfo.getUserId();
                    }
                    username = EManagerUtil.ConvertForHtml(username);
                    addOnloadScript("try{window.parent.frame1.document.getElementById('username').innerHTML='"
                            + username + "';}catch(e){alert(e.message)}");
                }
            }

            // eManager v2.0 AccessLog  
            // write log only if Login button is clicked..Do not write Access Log if password change button is clikced, create Access log during next button create at ChangePassword Screen
            if (isLoginClick)
            {
// 2008/12/11 K.Matsuda Start 有効期限切れはログ出力しないように修正
                // パスワード有効期限切れ、パスワード有効期限切れ(警告)、仮パスワード状態の場合はパスワード変更画面に遷移するため、ここではログは出力しない
                if (auth.getResultStatus() != EmConstants.AUTHENTICATION_PWDEXPIRE_ALERT
                        && auth.getResultStatus() != EmConstants.AUTHENTICATION_DUMMYPASSWORD
                        && auth.getResultStatus() != EmConstants.AUTHENTICATION_PWDEXPIRED)
// 2008/12/11 K.Matsuda End
                {
                    String message = EManagerUtil.convertMsg("6400001");
                    this.part11AccessLog(conn, (DfkUserInfo)this.getUserInfo(), EmConstants.AUTHLOG_CLASS_LOGIN,
                            message, this.httpRequest.getRemoteAddr(), null);
                    EmConnectionHandler.commit(conn);
                }
            }
        }
        catch (EmAuthenticationException ex)
        {
            String failedCount = this.viewState.getString(EmConstants.VSKEY_FAILEDCOUNT);
            int failedCountNum = 0;
            if (failedCount != null)
            {
                failedCountNum = Integer.parseInt(failedCount);
            }
            failedCountNum++;
            this.viewState.setInt(EmConstants.VSKEY_FAILEDCOUNT, failedCountNum);

            ;
            String msg = DispResources.getText(EManagerUtil.getLogginLocale(), ex.getMessageResourcekey());
            // write access log --eManager -2.0
            DfkUserInfo dfkUserinfo = (DfkUserInfo)this.getUserInfo();
            this.part11AccessLog(conn, dfkUserinfo, EmConstants.AUTHLOG_CLASS_FAILED, msg,
                    this.httpRequest.getRemoteAddr(), userId);
            // remove DS number from userInfo
            dfkUserinfo.setDsNumber(null);
            EmConnectionHandler.commit(conn);
            //end Emanager-2.0
            AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
            AuthenticationSystem authEntity = authHandler.findAuthenticationSystem();

            int lockThreshold = authEntity.getFailedLoginscreenLockCount();
            // 指定回数以上認証エラーもしくはユーザロック時にはロック画面に遷移する。
            if (failedCountNum >= lockThreshold && lockThreshold > 0
                    || auth.getResultStatus() == EmConstants.AUTHENTICATION_USERLOCK)
            {
                int locktime = authEntity.getFaieldLoginScreenLockTime();
                if (locktime > 0)
                {
                    ForwardParameters fparam = new ForwardParameters();
                    fparam.addParameter(EmConstants.RKEY_WAITINTERVAL, String.valueOf(locktime));

                    // Go to screen lock.
                    //                  redirect("/ScreenLock.do", fparam);
                    addOnloadScript("changeFrame('" + httpRequest.getContextPath() + "/ScreenLock.do?"
                            + EmConstants.RKEY_WAITINTERVAL + "=" + locktime + "')");
                }
            }
            this.setAlert(ex.getMessageResourcekey());
            auth = null;
            
// 2008/12/19 K.Matsuda Start エラー時にフォーカス
            setFocus(txt_LoginID);
// 2008/12/19 K.Matsuda End
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }

        return auth;

    }

    /**
     * <jp>次へ遷移する画面のURIを取得します。 <br></jp>
     * <en>Return the uri of next screen. <br></en>
     * @return URI
     */
    private String getNextUri()
    {
        StringBuffer nextUri = new StringBuffer();
        nextUri.append("/");
        nextUri.append(this.viewState.getString(EmConstants.RKEY_FUNCTIONURI) + ".do");
        nextUri.append("?");
        nextUri.append(Constants.MENUPARAM);
        nextUri.append("=");
        nextUri.append(this.viewState.getString(Constants.MENUPARAM));
        nextUri.append("&");
        nextUri.append(EmConstants.RKEY_DSNUMBER);
        nextUri.append("=");
        nextUri.append(this.viewState.getString(EmConstants.RKEY_DSNUMBER));
        //String nextUri = "/" + this.viewState.getString(EmConstants.RKEY_FUNCTIONURI) + ".do" + "?" + Constants.MENUPARAM + "=" + this.viewState.getString(Constants.MENUPARAM);
        //String nextUri = "/" + this.viewState.getString(EmConstants.RKEY_FUNCTIONURI) + ".do" + "?" + Constants.MENUPARAM + "=" + this.viewState.getString(Constants.MENUPARAM);
        return nextUri.toString();
    }

    /**
     * <jp>戻るときに遷移する画面のURIを取得します。 <br></jp>
     * <en>Return the uri of previous screen. <br></en>
     * @return URI
     */
    private String getBackUri()
    {
        StringBuffer backUri = new StringBuffer();
        backUri.append(this.httpRequest.getRequestURI().substring(this.httpRequest.getContextPath().length()));
        backUri.append("?");
        backUri.append(Constants.MENUPARAM);
        backUri.append("=");
        backUri.append(this.viewState.getString(Constants.MENUPARAM));
        backUri.append("&");
        backUri.append(EmConstants.RKEY_FUNCTIONURI);
        backUri.append("=");
        backUri.append(this.viewState.getString(EmConstants.RKEY_FUNCTIONURI));
        backUri.append("&");
        backUri.append(EmConstants.RKEY_DSNUMBER);
        backUri.append("=");
        backUri.append(this.viewState.getString(EmConstants.RKEY_DSNUMBER));

        /*String backUri = this.httpRequest.getRequestURI().substring(this.httpRequest.getContextPath().length());
         backUri += "?" + Constants.MENUPARAM + "=" + this.viewState.getString(EmConstants.MENUPARAM) 
         + "&" + EmConstants.RKEY_FUNCTIONURI + "=" + this.viewState.getString(EmConstants.RKEY_FUNCTIONURI);*/
        return backUri.toString();
    }

    /**
     * <en>Method to create the part11 AccessLog.. from eManager-2.0<br></en>
     * @throws Exception
     */
    private void part11AccessLog(Connection conn, DfkUserInfo userinfo, int type, String message, String ipAddress,
            String userId)
            throws SQLException
    {
        // create Access Log only if  access log is true
        if (!P11Config.isAccessLog())
        {
            return;
        }
        P11LogWriter p11logWriter = new P11LogWriter(conn);
        if (userId != null)
        {
            String userName = null;
            p11logWriter.createAccessLog(userinfo, userId, userName, type, ipAddress, message);
        }
        else
        {
            p11logWriter.createAccessLog(userinfo, type, ipAddress, message);
        }
    }
}
//end of class

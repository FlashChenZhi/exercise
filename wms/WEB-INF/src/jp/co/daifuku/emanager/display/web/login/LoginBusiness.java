// $Id: LoginBusiness.java 8001 2011-08-12 08:46:37Z nagao $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.login;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.exception.IllegalEventException;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.Constants;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.bluedog.webapp.ViewState;
import jp.co.daifuku.bluedog.webapp.ViewStateMap;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.authentication.EmAuthenticationException;
import jp.co.daifuku.emanager.authentication.LoginAuthentication;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.handler.AuthenticationHandler;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.util.EmProperties;
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
 * @version $Revision: 8001 $, $Date: 2011-08-12 17:46:37 +0900 (金, 12 8 2011) $
 * @author  $Author: nagao $
 */
public class LoginBusiness
        extends Login
        implements EmConstants
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------
    /**
     * <jp>画面の初期化を行います。<br></jp>
     * <en>Initialize a screen. <br></en>
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        //Clear UserInfo
        if (this.getUserInfo() != null)
        {
            this.setUserInfo(null);
        }
        setFocus(txt_LoginID);
    }
    /**
     * <jp>ログインチェックをオーバライドします。<br></jp>
     * <en>Carry out the override of the login check. <br></en>
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
 // 2008/12/04 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
    /**
     * <jp>認証チェックを行います。 <br></jp>
     * <en>Authentication. <br></en>
     * @param conn Connection
     * @param userId <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @param password <jp>パスワード &nbsp;&nbsp;</jp><en>password &nbsp;&nbsp;</en>
     * @param expchekflg パスワード期限切れをエラーとするかどうかのフラグ
     * @return <jp>SystemAuthentication クラス &nbsp;&nbsp;</jp><en>SystemAuthentication class &nbsp;&nbsp;</en>
     * @throws SQLException
     * @throws IllegalEventException
     */
    private LoginAuthentication checkAuthentication(String userId, String password, boolean expchekflg)
            throws SQLException,
                IllegalEventException
// 2008/12/04 K.Matsuda End 有効期限切れでもパスワードは変更できるように修正
    {
        if (userId == null || userId.equals(""))
        {
            //<jp>MSG-T0020=ユーザ名を入力してください。</jp>
            //<en> MSG-T0020 = Please input a user name. </en>
        	ViewStateMap viewStateMap = (ViewStateMap) session.getAttribute(this.getClass().getSimpleName() + Constants.HTTP_VIEW_STATE);
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
        Connection conn = null;
        LoginAuthentication auth = null;
        try
        {
            //<jp>認証クラス生成</jp>
            //<en> Attestation class generation. </en>
            auth = new LoginAuthentication();

            //<en> Create new HttpSession after successful login</en>
            HttpSession session = this.httpRequest.getSession(true);
            this.setSession(session);

            //<jp>認証->成功時はセッションに保持</jp>
            //<en> It holds at a session at the time of an attestation-success. </en>
// 2008/12/04 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
            DfkUserInfo dfkUserInfo = auth.authenticate(userId, password, httpRequest.getRemoteAddr(), this.session, expchekflg);
// 2008/12/04 K.Matsuda End 有効期限切れでもパスワードは変更できるように修正
            this.setUserInfo(dfkUserInfo);

        }
        catch (EmAuthenticationException ex)
        {
            String failedCount = this.viewState.getString(VSKEY_FAILEDCOUNT);
            int failedCountNum = 0;
            if (failedCount != null)
            {
                failedCountNum = Integer.parseInt(failedCount);
            }
            failedCountNum++;
            this.viewState.setInt(VSKEY_FAILEDCOUNT, failedCountNum);

            conn = EmConnectionHandler.getPageDbConnection(this);

            AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
            AuthenticationSystem authEntity = authHandler.findAuthenticationSystem();

            int lockThreshold = authEntity.getFailedLoginscreenLockCount();

            // 指定回数以上認証エラーもしくはユーザロック時にはロック画面に遷移する。
            if ((failedCountNum >= lockThreshold && lockThreshold > 0 || auth.getResultStatus() == EmConstants.AUTHENTICATION_USERLOCK))
            {
                int locktime = authEntity.getFaieldLoginScreenLockTime();

                if (locktime > 0)
                {
                    ForwardParameters fparam = new ForwardParameters();
                    fparam.addParameter(EmConstants.RKEY_WAITINTERVAL, String.valueOf(locktime));
                    redirect("/ScreenLock.do", fparam);
                }
            }
            this.setAlert(ex.getMessageResourcekey());
            auth = null;

            // Logging
            //EManagerLogger.writeAuthenticationLog(this, userId, EmConstants.AUTHLOG_CLASS_FAILED,
            //	MessageConst.MSG_6000504, DispResources.getText(EManagerLogger.getLogginLocale(), ex.getMessage()));
            setFocus(txt_LoginID);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }

        return auth;
    }
    /**
     * Add Start. 2011/03/24 T.Sumiyama
     * <jp>SessionTimeoutが発生していた場合の対応。
     * ViewStateが初期化されていたら、ページをリロードします。
     * イベントの最後で呼び出してください。</jp>
     */
    private void checkSessionTimeout()
    {
    	if(!txt_LoginID.getVisible() && !txt_Password.getVisible())
    	{
    		addOnloadScript("location.href=location.href;");
    	}
    }
    // Event handler methods -----------------------------------------
    /**
     * <jp>ログインボタンが押下されたときの処理です。<br></jp>
     * <en>It is processing when a login button is pushed. <br></en>
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Login_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);

// 2008/12/04 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
            LoginAuthentication auth = checkAuthentication(txt_LoginID.getText(), txt_Password.getText(), false);
// 2008/12/04 K.Matsuda End 有効期限切れでもパスワードは変更できるように修正

            if (auth == null)
            {
                return;
            }

            DfkUserInfo dfkUserInfo = (DfkUserInfo)this.getUserInfo();

            //Commit update date.

            //<jp>有効期限切れ間近</jp>
            //<en> Term-of-validity piece nearness. </en>
            if (auth.getResultStatus() == EmConstants.AUTHENTICATION_PWDEXPIRE_ALERT)
            {
                //<jp>パスワード変更画面へ遷移</jp>
                //<en> It changes to a password change screen. </en>
                //				this.viewState.setInt(VSKEY_PASSWORDCHANGE_CAUSE, auth.getResultStatus());
                //
                forward("/login/ChangePassword.do");
            }

            // <jp>仮パスワード状態</jp>
            // <en>Dummy password</en>
            else if (auth.getResultStatus() == EmConstants.AUTHENTICATION_DUMMYPASSWORD)
            {
                //<jp>パスワード変更画面へ遷移</jp>
                //<en> It changes to a password change screen. </en>
                //				this.viewState.setInt(VSKEY_PASSWORDCHANGE_CAUSE, auth.getResultStatus());
                forward("/login/ChangePassword.do");
            }
// 2008/12/10 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
            // <jp>パスワード有効期限切れ</jp>
            // <en>Dummy password</en>
            else if (auth.getResultStatus() == EmConstants.AUTHENTICATION_PWDEXPIRED)
            {
                //<jp>パスワード変更画面へ遷移</jp>
                //<en> It changes to a password change screen. </en>
                //              this.viewState.setInt(VSKEY_PASSWORDCHANGE_CAUSE, auth.getResultStatus());
                forward("/login/ChangePassword.do");
            }
// 2008/12/10 K.Matsuda End
            else
            {
                //<jp>メインメニューとログイン成功画面を表示</jp>
                //<en> A main menu and a login success screen are displayed. </en>
                if (dfkUserInfo.getMainMenuType() == EmConstants.MAINMENUSHOWTYPE_B)
                {
                    //Menu for the big icon.
                    forward(EmProperties.getProperty(EmConstants.EMPARAMKEY_FRAME_B_URI));
                }
                else
                {
                    //Menu for the small icon.
                    forward(EmProperties.getProperty(EmConstants.EMPARAMKEY_FRAME_A_URI));
                }
            }
            //ユーザ情報の作成
            DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
            //項目リストの生成
            List list = new ArrayList();
            list.add(EmConstants.OPELOG_PASSWORD);
            //オペレーションログ出力
            P11LogWriter logWriter = new P11LogWriter(conn);
            //			logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_LOGIN, list);
            logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_LOGIN, EmConstants.DS_NO_LOGIN,
                    "TLE-T0141", list);
            conn.commit();
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
            this.setAlert("MSG-T0041" + MSG_DELIM + discription);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
            checkSessionTimeout();
        }
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
        try
        {
// 2008/12/04 K.Matsuda Start 有効期限切れでもパスワードは変更できるように修正
            LoginAuthentication auth = checkAuthentication(txt_LoginID.getText(), txt_Password.getText(), false);
// 2008/12/04 K.Matsuda End 有効期限切れでもパスワードは変更できるように修正
            if (auth == null)
            {
                return;
            }

            //<jp>パスワード変更画面へ遷移</jp>
            //<en> It changes to a password change screen. </en>
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
            this.setAlert("MSG-T0041" + MSG_DELIM + discription);
        }
        finally
        {
        	checkSessionTimeout();
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
     * For Simple Login Screen.
     * Delete Flag.
     * @param e
     * @throws Exception
     */
    public void btn_sessionDelete_Click(ActionEvent e)
    throws Exception
    {
    	Cookie cookie = new Cookie("noimageFlag", "false");
    	cookie.setMaxAge(0);
    	getHttpResponse().addCookie(cookie);
    	addOnloadScript("location.href=location.href;");
    }
}
//end of class

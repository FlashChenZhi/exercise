// $Id: AutoLoginBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.login;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.authentication.AutoAuthentication;
import jp.co.daifuku.emanager.authentication.EmAuthenticationException;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.emanager.util.P11LogWriter;

/**
 * 
 * <jp>自動ログイン画面クラスです。<br></jp>
 * <en>Auto Login screen.<br></en> 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 */
public class AutoLoginBusiness
        extends AutoLogin
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

        Connection conn = null;
        try
        {

            String ipAddress = this.httpRequest.getRemoteAddr();

            //<jp>認証クラス生成</jp>
            //<en>Create Authentication class</en>
            AutoAuthentication auth = new AutoAuthentication();
            DfkUserInfo userinfo = auth.authenticate(ipAddress, this.session);

            //<jp>認証->成功時はセッションに保持</jp>
            //<en>When authentication succeeds, it is held in the session.</en>
            setUserInfo(userinfo);

            //<jp>メインメニューとログイン成功画面を表示</jp><en> A main menu and a login success screen are displayed. </en>
            if (userinfo.getMainMenuType() == EmConstants.MAINMENUSHOWTYPE_B)
            {
                //Menu for the big icon.
                forward(EmProperties.getProperty(EmConstants.EMPARAMKEY_FRAME_B_URI));
            }
            else
            {
                //Menu for the small icon.
                forward(EmProperties.getProperty(EmConstants.EMPARAMKEY_FRAME_A_URI));
            }

            //ユーザ情報の作成
            DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
            //項目リストの生成
            List list = new ArrayList();
            conn = EmConnectionHandler.getConnection();
            P11LogWriter logWriter = new P11LogWriter(conn);
            logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_LOGIN, EmConstants.DS_NO_AUTOLOGIN,
                    "TLE-T0142", list);

            conn.commit();

        }
        catch (EmAuthenticationException ex)
        {
            // <jp>認証エラー発生時</jp>
            // <en>When ahthentication error.</en>
            String msg = ex.getMessageResourcekey();
            lbl_Message.setText(DispResources.getText(msg));

            // Logging
            //EManagerLogger.writeAuthenticationLog(this, EmConstants.ANONYMOUS_USER, EmConstants.AUTHLOG_CLASS_FAILED,
            //	MessageConst.MSG_6000504, DispResources.getText(EManagerLogger.getLogginLocale(), ex.getMessage()));

        }
        catch (Exception ex)
        {
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
            //			this.setAlert(DISP_MSG9109 + MSG_DELIM + discription);
            lbl_Message.setResourceKey("MSG-T0041" + MSG_DELIM + discription);

        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
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

    // Event handler methods -----------------------------------------

}
//end of class

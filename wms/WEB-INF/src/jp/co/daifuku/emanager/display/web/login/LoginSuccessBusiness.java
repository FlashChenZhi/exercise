// $Id: LoginSuccessBusiness.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.login;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.JspConstants;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.entity.Terminal;
import jp.co.daifuku.emanager.database.handler.AuthenticationHandler;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.TerminalChangeMapHandler;
import jp.co.daifuku.emanager.database.handler.TerminalHandler;
import jp.co.daifuku.emanager.database.handler.TerminalUserMapHandler;
import jp.co.daifuku.emanager.util.P11LogWriter;

/**
 * <jp>ログイン成功画面クラスです。<br></jp>
 * <en>It is a login success screen class. <br></en>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 */
public class LoginSuccessBusiness
        extends LoginSuccess
        implements Constants
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------
    // Public methods ------------------------------------------------
    /** 
     *<jp>画面の初期化を行います。<br></jp>
     *<en>Initialize a screen. <br></en>
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        // wait for TagHandler to load all tag librarys. 
        try
        {
            Thread.sleep(100);

        }
        catch (InterruptedException exception)
        {
        }

        DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();
        UserInfoHandler userhandler = new UserInfoHandler(userinfo);

        String username = userinfo.getUserName();
        if (username == null || username.length() == 0)
        {
            username = userinfo.getUserId();
        }

        //<jp>{0}さんでログインしました。上部メニューより、作業を選択してください。</jp><en> It logged in by {0}. Please choose work from an up menu. </en>
        lbl_Msg01.setText(DispResources.getText("MSG-T0001", username));

        //<jp>端末名を選択し、修正ボタンをクリックしてください</jp><en> Please choose a terminal name and click a correction button. </en>
        lbl_Msg02.setText(DispResources.getText("MSG-T0002"));

        //<jp>プルダウンデータの設定</jp><en> A setup of pull down data. </en>
        int count = setPullDownData();

        //<jp>切り替え候補無し、匿名端末でのログインは端末切り替えできない</jp><en> Login with change candidate nothing and an anonymity terminal cannot carry out a terminal change. </en>
        if (count < 2 || userhandler.getIPAddress().equals(EmConstants.UNDEFINED_TERMINAL))
        {
            pul_Terminal.setEnabled(false);
            btn_Modify.setEnabled(false);
            //<jp>端末の変更はできません</jp><en> Change of a terminal cannot be performed. </en>
            lbl_Msg02.setText(DispResources.getText("MSG-T0004"));
        }

    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Event handler methods -----------------------------------------
    /**
     * <jp>端末の再設定を行います。<br></jp>
     * <en> * Perform a re-setup of a terminal. <br></en>
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Modify_Click(ActionEvent e)
            throws Exception
    {

        Connection conn = null;
        try
        {
            DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();

            //<jp>現在の端末No.</jp><en> Present terminal No. </en>
            String currentTerminalNo = userinfo.getTerminalNumber();
            //<jp>変更したい端末No.</jp><en> Terminal No. to change. </en>
            String terminalNo = pul_Terminal.getSelectedValue();

            //<jp>端末Noに変更が無ければ処理せずに抜ける</jp><en> It escapes without processing, if there is no change in Terminal No. </en>
            if (terminalNo.equals(currentTerminalNo))
            {
                return;
            }

            conn = EmConnectionHandler.getPageDbConnection(this);

// 2008/12/17 K.Matsuda Start 端末切替履歴に変更元端末も落ちるように修正
            //項目リストの生成
            List list = new ArrayList();
            list.add(userinfo.getTerminalName());
            list.add(pul_Terminal.getSelectedItem().getText());
// 2008/12/17 K.Matsuda End
            //オペレーションログ出力
            P11LogWriter logWriter = new P11LogWriter(conn);
            logWriter.createOperationLog(userinfo, EmConstants.OPELOG_CLASS_MODIFY, EmConstants.DS_NO_LOGINSUCCESS,
                    "TLE-T0145", list);

            //<jp>端末情報を再取得</jp><en> It is re-acquisition about terminal information. </en>
            TerminalHandler handler = EmHandlerFactory.getTerminalHandler(conn);
            Terminal terminalEntity = handler.findByTerminalNumber(terminalNo);

            //<jp>UserInfoにセット</jp><en> It sets to UserInfo. </en>
            userinfo.setTerminalName(terminalEntity.getTerminalName());
            userinfo.setTerminalNumber(terminalEntity.getTerminalNumber());
            userinfo.setTerminalRoleId(terminalEntity.getRoleId());
            // プリンタ名称を設定 eManager2.0.1(BusiTune対応)
            userinfo.setTerminalPrinterName(terminalEntity.getPrinterName());
            //<jp>作業端末が変更されました。</jp><en> The work terminal was changed. </en>
            lbl_Msg02.setText(DispResources.getText("MSG-T0003"));

            addOnloadScript("reloadMainMenu()");

            conn.commit();
        }
        catch (Exception ex)
        {
            //6406001=Unexpected error occurred.{0}
            //			AppLogger.write(6406001, ex, this.getClass());
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
            this.setAlert("MSG-T0041" + "\t" + discription);
            //Rolleback
            if (conn != null) conn.rollback();
        }
        finally
        {
            if (conn != null)
            {
                EmConnectionHandler.closeConnection(conn);
            }
        }
    }

    /**
     * <jp>プルダウンにデータをセットします。<br></jp>
     * <en>Set the data to PullDown. <br></en>
     * @return <jp>セットしたデータ数 &nbsp;&nbsp;</jp><en>Count of data &nbsp;&nbsp;</en>
     * @throws SQLException
     */
    private int setPullDownData()
            throws Exception
    {
        DfkUserInfo userinfo = (DfkUserInfo)this.getUserInfo();
        Connection conn = null;
        int count = 1;
        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            // Get terminal change setting from database
            TerminalChangeMapHandler tcHandler = EmHandlerFactory.getTerminalChangeMapHandler(conn);
            Terminal terminal[] = tcHandler.findByTerminalNumber(userinfo.getTerminalNumber());

            // Get system settings from database
            AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
            AuthenticationSystem authEntity = authHandler.findAuthenticationSystem();

            pul_Terminal.clearItem();
            pul_Terminal.addItem(userinfo.getTerminalNumber(), null, userinfo.getTerminalName(), true);

            if (authEntity.getTerminalChangeFlag() == true)
            {
                TerminalUserMapHandler tuHandler = EmHandlerFactory.getTerminalUserMapHandler(conn);
                for (int i = 0; terminal != null && i < terminal.length; i++)
                {
                    int teminalUser =
                            tuHandler.findCountByUserIdTerminalNumber(userinfo.getUserId(),
                                    terminal[i].getTerminalNumber());
                    if (authEntity.getTerminalUserCheckFlag() == false || teminalUser > 0
                            || userinfo.getRoleID().equals(EmConstants.ADMIN_ROLE))
                    {
                        pul_Terminal.addItem(terminal[i].getTerminalNumber(), null, terminal[i].getTerminalName(),
                                false);
                        count++;
                    }
                }

            }

            Date workDate = authEntity.getWorkDate();
            // if work date is not null , set it to the request. This work date is used only in eWareNavi and displayed in LoginSuccess screen
            if (authEntity.getWorkDate() != null)
            {
                this.txt_WorkDay.setDate(workDate);
                this.getHttpRequest().setAttribute(JspConstants.WORKDATE, workDate);
            }

        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);

        }
        return count;
    }
}
//end of class

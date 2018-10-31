// $Id: ChangePasswordBusiness.java 3965 2009-04-06 02:55:05Z admin $
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.authentication.EmAuthenticationException;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.entity.LogInfo;
import jp.co.daifuku.emanager.database.entity.User;
import jp.co.daifuku.emanager.database.entity.UserMasterLog;
import jp.co.daifuku.emanager.database.handler.AuthenticationHandler;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.PassWordHistoryHandler;
import jp.co.daifuku.emanager.database.handler.UserHandler;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.EmDate;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.emanager.util.PasswordChecker;


/** 
 * <jp>パスワード変更画面クラスです。<br></jp>
 * <en>It is a password change screen class. <br></en>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/06/26</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 */
public class ChangePasswordBusiness
        extends ChangePassword
{

    // eManager-2.0
    /**
     * DS番号
     */
    private String dsNumber = null;

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
        Connection conn = null;
        boolean dummyFlag = false;
        Date expireDate = null;
        int pwdChangeInterval = 0;
        boolean expireCheckEnabled = false;
        try
        {
            // <jp>ユーザ設定情報を取得</jp>
            // <en>Get the user settings.</en>
            String userId = this.getUserInfo().getUserId();
            conn = EmConnectionHandler.getPageDbConnection(this);
            UserHandler handler = EmHandlerFactory.getUserHandler(conn);
            User userEntity = handler.findByUserId(userId);
            dummyFlag = userEntity.getDummyPassFlag();
            expireDate = userEntity.getPwdExpire();
            pwdChangeInterval = handler.getRealPwdChangeIntervaluByUserId(userId);

            // Get the system settings.
            AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
            AuthenticationSystem authEntity = authHandler.findAuthenticationSystem();
            expireCheckEnabled = authEntity.getPassExpireFlag();
        }
        catch (SQLException ex)
        {
            EmConnectionHandler.rollback(conn);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
        String expireDays = null;
        if (dummyFlag == true)
        {
            lbl_msg0.setResourceKey("MSG-T0032");
            lbl_msg2.setVisible(false);
            btn_Next.setVisible(false);
        }
        else if (expireDate != null && pwdChangeInterval != EmConstants.PWDCHANGEINTERVAL_STATUS_NOLIMITED
                && expireCheckEnabled)
        {
// 2008/12/11 K.Matsuda Start 有効期限切れ時の処理を追加
            // 有効期限が切れているか、チェック
            Date today = new Date();
            EmDate emToday = new EmDate(today);
            if(emToday.afterDate(expireDate)) 
            {
                // MSG-T0062=パスワードの有効期限が切れています。
                lbl_msg0.setResourceKey("MSG-T0062");
                btn_Next.setVisible(false);
                lbl_msg2.setVisible(false);
            }
            else 
            {
                // パスワード有効残日数(今日が有効期限の場合は0)
                int rest = EManagerUtil.getBetweenDays(expireDate, today) - 1;
                expireDays = String.valueOf(rest);
                lbl_msg0.setResourceKey("MSG-T0034" + Constants.MSG_DELIM + expireDays);
            }
// 2008/12/11 K.Matsuda End 
        }
        else
        {
            lbl_msg0.setVisible(false);
        }
        btn_Back.setEnabled(true);
    }

    /** <jp>
     * 発生条件：各コントロールイベント呼び出し前に呼び出されます。
     * @param e ActionEvent
     * @throws Exception
     </jp> */
    /** <en>
     * Generating conditions: It is called before each control event call.
     * @param e ActionEvent
     * @throws Exception
     </en> */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        // if user clicks on MainMenu from Password Change screen. Logout access log shouldn't be created because AccessLog is created after clicking next button in this screen.
        // 	AccessLog logout record is done in SubmenuBusiness when dsnumber is not null.
        dsNumber = this.getViewState().getString(EmConstants.RKEY_DSNUMBER);
        ((DfkUserInfo)this.getUserInfo()).setDsNumber(null);
        
        setFocus(txt_OldPassword);
    }

    // Event handler methods -----------------------------------------
    /** 
     * <jp>次へボタンが押下されたときに呼ばれます。<br></jp>
     * <en>It is called when a button is pushed on the next. <br></en>
     * @param e ActionEvent
     * @throws Exception 
     */
    public void btn_Next_Click(ActionEvent e)
            throws Exception
    {

        DfkUserInfo userinfo = (DfkUserInfo)this.getUserInfo();

        int mainMenuType = userinfo.getMainMenuType();
        String nextUri = this.viewState.getString(EmConstants.VSKEY_NEXTURI);

        if (nextUri != null)
        {
            // eManager-2.0. Write Access log for screen logged in user.. Access log is written if user clicks on change password button at ScreenLogin Screen
            this.part11AccessLog();
            forward(nextUri);
        }
        else if (mainMenuType == EmConstants.MAINMENUSHOWTYPE_B)
        {
            //<jp>大きいアイコン用メニュー</jp><en> The large menu for icons. </en>
            forward(EmProperties.getProperty(EmConstants.EMPARAMKEY_FRAME_B_URI));
        }
        else
        {
            //<jp>小さいアイコン用メニュー</jp><en> The small menu for icons. </en>
            forward(EmProperties.getProperty(EmConstants.EMPARAMKEY_FRAME_A_URI));
        }
    }

    /** 
     * <jp>修正ボタンが押下されたときに呼ばれます。<br></jp>
     * <en>It is called when a correction button is pushed. <br></en>
     * @param e ActionEvent
     * @throws Exception 
     */
    public void btn_PasswordModify_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            String oldPassword = txt_OldPassword.getText();
            String newPassword = txt_NewPassword.getText();
            String reenterPassword = txt_ReenterPassword.getText();
            DfkUserInfo userinfo = (DfkUserInfo)this.getUserInfo();
            String userid = userinfo.getUserId();

            //			String userID = getViewState().getString("USER_ID");

            //<jp>必須入力チェック</jp><en> Indispensable input check. </en>
            if (oldPassword.equals(""))
            {
                //<jp>oldPasswordは妥当性チェックを行わない</jp><en> oldPassword does not perform a validity check. </en>
                setAlert("MSG-T0018");
                return;
            }
            //<jp>必須入力チェックと妥当性チェック</jp><en> An indispensable input check and a validity check. </en>
            try
            {
                txt_NewPassword.validate(true);
                txt_ReenterPassword.validate(true);
            }
            catch (ValidateException ve)
            {
                setAlert("MSG-T0035" + Constants.MSG_DELIM
                        + MessageResources.getText(ve.getErrorNo(), ve.getBinds().toArray()));
                return;
            }

            if (!newPassword.equals(reenterPassword))
            {
                //<jp>MSG-T0015=入力されたパスワードと確認用のパスワードが異なります。</jp><en> MSG-T0015 = the entered password differs from the password for a check. </en>
                setAlert("MSG-T0015");
                return;
            }

            conn = EmConnectionHandler.getPageDbConnection(this);

            PasswordChecker pwdChecker = new PasswordChecker(conn);
            pwdChecker.checkPassword(userid, oldPassword, newPassword);

            updatePassword(conn, userid, oldPassword, newPassword);
            EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_USER, "6400006", "");

            //<jp>MSG-T0019=パスワードを変更しました。</jp><en> MSG-T0019 = The password was changed. </en>
            lbl_msg0.setText("");
            lbl_msg0.setResourceKey("MSG-T0019");
            lbl_msg0.setVisible(true);
            lbl_msg1.setVisible(false);
            lbl_msg2.setVisible(false);

            txt_NewPassword.setText("");
            txt_ReenterPassword.setText("");
            txt_OldPassword.setText("");

            txt_NewPassword.setReadOnly(true);
            txt_ReenterPassword.setReadOnly(true);
            txt_OldPassword.setReadOnly(true);

            btn_Next.setVisible(true);
            btn_PasswordModify.setVisible(false);
            
            // 入力項目の背景色を入力不可の色に変更
            // (当画面は画面の作り上readonlyにしても背景色が自動で変更されないためここで明示的に指定する)
            addOnloadScript("changeBackcolor(\"txt_OldPassword\", \"#dad9ee\")");
            addOnloadScript("changeBackcolor(\"txt_NewPassword\", \"#dad9ee\")");
            addOnloadScript("changeBackcolor(\"txt_ReenterPassword\", \"#dad9ee\")");
        }
        catch (EmAuthenticationException ex)
        {
            EmConnectionHandler.rollback(conn);
            this.setAlert(ex.getMessageResourcekey());
        }
        catch (SQLException ex)
        {
            EmConnectionHandler.rollback(conn);
            int errorCode = ex.getErrorCode();

            if (errorCode == EmConstants.SQLERRORCODE_ORA0001)
            {
                // <jp>一意制約違反のときは、パスワードの認証ミスとして扱う。</jp>
                // <en>When violating it about a determinate restriction, it treats as an attestation mistake of the password.</en>
                this.setAlert("DISP_MSG-T0022");
            }
            else
            {
                this.setAlert("MSG-T0041" + Constants.MSG_DELIM + ex.getMessage());
            }
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /**
     * <jp>パスワードの更新処理を行ないます。<br></jp>
     * <en>Update the password. <br></en>
     * @param conn Connection
     * @param userid <jp>ユーザID &nbsp;&nbsp;</jp><en>User ID &nbsp;&nbsp;</en>
     * @param oldPassword <jp>現在のパスワード &nbsp;&nbsp;</jp><en>Old password &nbsp;&nbsp;</en>
     * @param newPassword <jp>新しく設定するパスワード &nbsp;&nbsp;</jp><en>New password &nbsp;&nbsp;</en>
     * @throws SQLException 
     */
    private void updatePassword(Connection conn, String userid, String oldPassword, String newPassword)
            throws Exception
    {
        UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);

        User userEntity = userHandler.findByUserId(userid);
        User beforeUserEntity = (User)userEntity.clone();
        int updateInterval = userHandler.getRealPwdChangeIntervaluByUserId(userid);

        // update expire date
        if (updateInterval != EmConstants.PWDCHANGEINTERVAL_STATUS_NOLIMITED)
        {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, updateInterval);
            userEntity.setPwdExpire(cal.getTime());
        }

        userEntity.setUpdateProcess(this.getClass().getName());
        userEntity.setUpdateTerminal(this.httpRequest.getRemoteAddr());
        userEntity.setUpdateKind(EmConstants.UPDATE_KIND_UPDATE);
        userEntity.setUpdateUser(userid);
        userEntity.setDummyPassFlag(false);

        // set new password to entity or over  
        userEntity.setPassword(newPassword);

        userHandler.updateByUserId(userEntity);

        // create password change log
        PassWordHistoryHandler pwdHistoryHandler = EmHandlerFactory.getPassWordHistoryHandler(conn);
        LogInfo logInfo = new LogInfo();
        logInfo.setUserId(userid);
        logInfo.setOldPassword(oldPassword);
        logInfo.setUpdateDate(new Date());
        logInfo.setUpdateKind(EmConstants.UPDATE_KIND_UPDATE);
        logInfo.setUpdateProcess(this.getClass().getName());
        logInfo.setUpdateTerminal(this.httpRequest.getRemoteAddr());
        logInfo.setUpdateUser(userid);

        // <jp>パスワードの変更ログを出力</jp>
        // <en>Create Password change log</en>
        pwdHistoryHandler.createPasswordHistory(logInfo);
        // delete old log .. 
        pwdHistoryHandler.deleteByUserIdAuth(userid);

        this.part11UserMasterLog(conn, userEntity, beforeUserEntity);
        EmConnectionHandler.commit(conn);
    }

    /** 
     * This method creates the logs related to Part11 namely, OperationLog and User master log
     * @param e ActionEvent 
     * @throws Exception 
     */
    private void part11UserMasterLog(Connection conn, User currentUser, User beforeUpdate)
            throws SQLException
    {

        //ユーザ情報の作成
        DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
        UserMasterLog userMasterLog = new UserMasterLog();
        // set Common Parameters
        userMasterLog.setUserId(userInfo.getUserId());
        userMasterLog.setUserName(userInfo.getUserName());
        userMasterLog.setTerminalNumber(userInfo.getTerminalNumber());
        userMasterLog.setTerminalName(userInfo.getTerminalName());
        userMasterLog.setIpAddress(userInfo.getTerminalAddress());
        userMasterLog.setDsNumber(EmConstants.DS_NO_CHANGEPASSWORD);
        userMasterLog.setPageName("TLE-T0035");

        userMasterLog.setUpdateKind(EmConstants.USERMASTER_CLASS_MODIFY);
        userMasterLog.setMasterUserId(currentUser.getUserID());

        userMasterLog.setPasswordBefore(EmConstants.OPELOG_PASSWORD);
        userMasterLog.setPasswordAfter(EmConstants.OPELOG_PASSWORD);


        if (currentUser.getPwdExpire() != null)
        {
            userMasterLog.setPwdExpiresBefore(beforeUpdate.getPwdExpire());
            userMasterLog.setPwdExpiresAfter(currentUser.getPwdExpire());
        }


        userMasterLog.setUserNameBefore(beforeUpdate.getUserName());
        userMasterLog.setPasswordChangeIntervalBefore(beforeUpdate.getPwdChangeInterval());

// 2008/12/10,2009/01/20 K.Matsuda Start テーブル仕様変更に併せて修正
        userMasterLog.setUserStatusBefore(beforeUpdate.getUserStatus());
// 2008/12/10,2009/01/20 K.Matsuda End
        userMasterLog.setSameUserLoginMaxBefore(beforeUpdate.getSameUserLoginMax());
        userMasterLog.setFailedLoginAttemptsBefore(beforeUpdate.getFailLoginAttem());
        userMasterLog.setRoleIdBefore(beforeUpdate.getRoleID());
        userMasterLog.setDepartmentBefore(beforeUpdate.getDepartment());
        userMasterLog.setRemarkBefore(beforeUpdate.getRemarks());

        userMasterLog.setUserNameAfter(currentUser.getUserName());
        userMasterLog.setPasswordChangeIntervalAfter(currentUser.getPwdChangeInterval());
// 2008/12/10,2009/01/20 K.Matsuda Start テーブル仕様変更に併せて修正
        userMasterLog.setUserStatusAfter(currentUser.getUserStatus());
// 2008/12/10,2009/01/20 K.Matsuda End
        userMasterLog.setSameUserLoginMaxAfter(currentUser.getSameUserLoginMax());
        userMasterLog.setFailedLoginAttemptsAfter(currentUser.getFailLoginAttem());
        userMasterLog.setRoleIdAfter(currentUser.getRoleID());
        userMasterLog.setDepartmentAfter(currentUser.getDepartment());
        userMasterLog.setRemarkAfter(currentUser.getRemarks());

        //項目リストの生成
        List list = new ArrayList();
        list.add(EmConstants.OPELOG_PASSWORD);
        list.add(EmConstants.OPELOG_PASSWORD);

        P11LogWriter logWriter = new P11LogWriter(conn);
        logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_MODIFY, EmConstants.DS_NO_CHANGEPASSWORD,
                "TLE-T0035", list);
        logWriter.createMasterChengeLog(userMasterLog);
    }

    /** 
     * <jp>戻るボタンが押下されたときに呼ばれます。<br></jp>
     * <en>It is called when a BACK button is pushed. <br></en>
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Back_Click(ActionEvent e)
            throws Exception
    {

        String uri = this.viewState.getString(EmConstants.VSKEY_BACKURI);
        if (uri == null)
        {
            redirect("/", null);
        }
        else
        {
            forward(uri);
        }
    }

    /**
     * <en>Method to create the part11 AccessLog.. from eManager-2.0<br></en>
     * @throws Exception
     */
    private void part11AccessLog()
            throws SQLException
    {
        Connection conn = null;
        try
        {
            conn = EmConnectionHandler.getConnection();
            // create Access Log only if  access log is true
            if (P11Config.isAccessLog())
            {
                //eManager v2.0 AccessLog
                // write Access Log
                ((DfkUserInfo)this.getUserInfo()).setDsNumber(dsNumber);
                P11LogWriter p11logWriter = new P11LogWriter(conn);
                String message = EManagerUtil.convertMsg("6400001");
                p11logWriter.createAccessLog((DfkUserInfo)this.getUserInfo(), EmConstants.AUTHLOG_CLASS_LOGIN,
                        this.httpRequest.getRemoteAddr(), message);
            }
            EmConnectionHandler.commit(conn);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }
}
//end of class

// $Id: UserRecoveryBusiness.java 5376 2009-11-04 01:43:18Z yamashita $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.Validator;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.authentication.EmAuthenticationException;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.entity.LogInfo;
import jp.co.daifuku.emanager.database.entity.User;
import jp.co.daifuku.emanager.database.entity.UserMasterLog;
import jp.co.daifuku.emanager.database.handler.AuthenticationHandler;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.EmTableColumns;
import jp.co.daifuku.emanager.database.handler.PassWordHistoryHandler;
import jp.co.daifuku.emanager.database.handler.RoleHandler;
import jp.co.daifuku.emanager.database.handler.UserHandler;
import jp.co.daifuku.emanager.display.web.setting.user.listbox.UserListBusiness;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.emanager.util.PasswordChecker;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

/** <jp>
 * ツールが生成した画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5376 $, $Date: 2009-11-04 10:43:18 +0900 (水, 04 11 2009) $
 * @author  $Author: yamashita $
 </jp> */
/** <en>
 * This screen class is created by the screen generator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5376 $, $Date: 2009-11-04 10:43:18 +0900 (水, 04 11 2009) $
 * @author  $Author: yamashita $
 </en> */
public class UserRecoveryBusiness
        extends UserRecovery
        implements EmConstants
{
    // Class fields --------------------------------------------------
    /**
     * ロック解除処理を表します。
     */
    private static final String LOCK_RELEASE = "rockRelease";

    /**
     * パスワード再発行処理を表します。
     */
    private static final String PWD_REISSUE = "pwdReissue";

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /** 
     * 各コントロールイベント呼び出し前に呼び出されます。
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
        if (menuparam != null)
        {
            //<jp>パラメータを取得</jp><en> A parameter is acquired. </en>
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);
            //<jp>ViewStateへ保持する</jp><en> It holds to ViewState. </en>
            this.getViewState().setString(M_MENUID_KEY, menuID);
            //<jp>画面名称をセットする</jp><en> A screen name is set. </en>
            lbl_SettingName.setResourceKey(title);
            //<jp>ヘルプファイルへのパスをセットする</jp><en> The path to a help file is set. </en>
            btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()));
        }
        setFocus(txt_UserId);

        // 設定ボタン押下時にValidateExceptionが発生する可能性がある為
        // page_initialize()でも設定する
        setBeforeConfirm();
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.bluedog.ui.control.Page#page_Terminate(jp.co.daifuku.bluedog.webapp.ActionEvent)
     */
    public void page_Terminate(ActionEvent e)
            throws Exception
    {
        setBeforeConfirm();
    }

    /**
     * 設定ボタンに確認メッセージをセットします。
     */
    protected void setBeforeConfirm()
    {
        // ViewStateに処理種別を取得する。
        String processkey = this.getViewState().getString(VSKEY_PROCESS_KEY);

        if (PWD_REISSUE.equals(processkey))
        {
            // 登録ボタン押下時確認ダイアログ "ロック解除しますか？"
            btn_Commit.setBeforeConfirm("MSG-T0060");
        }
        else if (LOCK_RELEASE.equals(processkey))
        {
            // 修正ボタン押下時確認ダイアログ "パスワード再発行しますか？"
            btn_Commit.setBeforeConfirm("MSG-T0059");
        }
    }

    /**
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @throws Exception 
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
// 2008/12/25 K.Matsuda Start eWareNavi以外のときは赤*を非表示
        if(!EmConstants.PRODUCT_NAME_WMS.equals(EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_NAME)))
        {
            setVisibleRequireMarks(false);
        }
// 2008/12/25 K.Matsuda End
        
        //		// プロダクトタイプをViewStateに保存
        //	    this.getViewState().setString("PRODUCTTYPE",this.request.getParameter(PRODUCTTYPE));
        //	    // メニュータイプをViewStateに保存
        //	    this.getViewState().setString("MENUTYPE",this.request.getParameter(MENUTYPE));

        this.txt_R_UserId.setReadOnly(true);
        this.txt_UserName.setReadOnly(true);
        this.txt_Password.setReadOnly(true);
        this.txt_PasswordAgain.setReadOnly(true);

        // 設定ボタン
        this.btn_Commit.setEnabled(false);
        // クリアボタン
        this.btn_Clear.setEnabled(false);
    }


    /**
     * ボタンの使用可、不可を切替えます。
     */
    protected void changebtn()
    {
        // ロック解除ボタン
        btn_LockCancellation.setEnabled(false);
        // 最パスワードボタン
        btn_PasswordReissue.setEnabled(false);

        // 設定ボタン
        btn_Commit.setEnabled(true);
        // クリアボタン
        btn_Clear.setEnabled(true);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Event handler methods -----------------------------------------
    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_UserIdSearch_Click(ActionEvent e)
            throws Exception
    {
        // メインメニュー一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();

        //ユーザ一覧画面へ検索条件をセットする
        param.setParameter(UserListBusiness.USERID_KEY, txt_UserId.getText());

        //処理中画面->結果画面
        redirect("/emanager/setting/user/listbox/UserList.do", param, "/Progress.do");
    }


    /** 
     * ロック解除ボタンが押下された時の処理を実装します
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_LockCancellation_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;

        this.btn_Clear_Click(e);

// 2009/07/09 T.Kajiwara Mod DAIFUKU_SV,DAIFUKU_PRIVATEを指定したときは表示しない
        if (EmConstants.DAIFUKU_SV_USER.equals(this.txt_UserId.getText())
        		|| EmConstants.DAIFUKU_PRIVATE_USER.equals(this.txt_UserId.getText()))
        {
            // 一致するユーザが見つからなかったとき
            // 指定されたユーザIDは登録されていません。
            this.message.setMsgResourceKey("6403015");
            return;
        }
// 2009/07/09 T.Kajiwara End        
        // システム定義ユーザチェック
        if (txt_UserId.getText().equals(ANONYMOUS_USER) || txt_UserId.getText().equals(USERMAINTENANCE_USER))
        {
            //指定されたユーザIDはシステム定義のユーザのため修正できません。
            message.setMsgResourceKey("6403020");
            return;
        }

        try
        {
            // コネクションオブジェクト取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            // ユーザハンドラ取得
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);

            // 入力されたユーザIDを持つユーザエンティティを取得
            User user = userHandler.findByUserId(this.txt_UserId.getText());

            // ユーザIDが一致するユーザが存在するかチェック
            if (user == null)
            {
                // 一致するユーザが見つからなかったとき
                // 指定されたユーザIDは登録されていません。
                this.message.setMsgResourceKey("6403015");
                return;
            }

            // エンティティの情報を各コントロールにセット
            mapping(user);

            // ロック解除処理を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, LOCK_RELEASE);

            // ボタンの使用不可を設定
            changebtn();
        }
        finally
        {
            // コネクションを閉じる
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /**
     * ユーザエンティティの情報を各コントロールに設定します。
     * @param user
     * @throws Exception
     */
    private void mapping(User user)
            throws Exception
    {
        this.txt_R_UserId.setText(user.getUserID());
        this.txt_UserName.setText(user.getUserName());
// 2008/12/16 K.Matsuda Start テーブル仕様変更に併せて修正
        if(user.getUserStatus() == EmConstants.USERSTATUS_ACTIVE) 
        {
            // 有効
            this.txt_StateOfLock.setText(DispResources.getText("LBL-T0124"));
        }
        else if(user.getUserStatus() == EmConstants.USERSTATUS_DISABLED) 
        {
            // 無効
            this.txt_StateOfLock.setText(DispResources.getText("LBL-T0276"));
        }
        else if(user.getUserStatus() == EmConstants.USERSTATUS_LOCKED) 
        {
            // ロック中
            this.txt_StateOfLock.setText(DispResources.getText("LBL-T0125"));
        }
// 2008/12/16 K.Matsuda End
        this.txt_Password.setText(user.getPassword());
        this.txt_PasswordAgain.setText(user.getPassword());
        Date passwordExpire = user.getPwdExpire();
        if (passwordExpire != null)
        {
            this.txt_PwdExpires.setDate(passwordExpire);
        }
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Commit_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);

            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);

            // 処理区分
            String proc = this.getViewState().getString(VSKEY_PROCESS_KEY);

            //完了時のメッセージ
            String comp_msg = "";

            //処理区分が押下されていない場合
            if (proc == null)
            {
                return;
            }

            // 現在指定されているIDを持つユーザを取得
            User user = userHandler.findByUserId(this.txt_R_UserId.getText());
            // make a copy of user information before update.. Used for par11 User Master log
            User beforeUpdate = (User)user.clone();
            // ユーザが正しく取得できているかどうか
            if (user == null)
            {
                // 指定されたユーザIDは登録されていません。
                this.message.setMsgResourceKey("6403015");
                return;
            }

            // 操作区分
            int operationType = 0;

            // 処理区分がロック解除のとき
            if (proc.equals(LOCK_RELEASE))
            {
                // ロック解除
                operationType = EmConstants.OPELOG_CLASS_LOCK_CANCELLATION;

// 2008/12/16 K.Matsuda Start テーブル仕様変更に併せて修正
                // 現在のロック状態を調べる
                if (user.getUserStatus() != EmConstants.USERSTATUS_LOCKED)
                {
                    // 現在ロックされていないならメッセージを表示して終了
                    this.message.setMsgResourceKey("6403051");
                    return;
                }

                // ロックされていればロック解除情報をユーザオブジェクトに設定
                user.setUserStatus(EmConstants.USERSTATUS_ACTIVE);
// 2008/12/16 K.Matsuda End
                
                // 認証ミス回数をリセット
                user.setFailedCount(0);

                // 処理終了メッセージ
                comp_msg = "6401001";
            }
            // 処理区分が再パスワードの時
            else if (proc.equals(PWD_REISSUE))
            {
                // パスワード再発行
                operationType = EmConstants.OPELOG_CLASS_PASSWORD_REISSUE;

                // パスワードの入力チェック
                this.txt_Password.validate(this, true);
                this.txt_PasswordAgain.validate(this, true);

                // 入力されたパスワードを取得
                String password = this.txt_Password.getText();
                String passwordAgain = this.txt_PasswordAgain.getText();

                // パスワードと再パスワードが一致するかをチェックする
                if (!password.equals(passwordAgain))
                {
                    // 一致しなければメッセージを表示して終了
                    this.message.setMsgResourceKey("6403052");
                    setFocus(txt_Password);
                    return;
                }

                // 現在ユーザに設定されているパスワードを取得
                String nowPassword = user.getPassword();

                PasswordChecker pwdRule = new PasswordChecker(conn);

                try
                {
                    // 入力パスワードの整合性をチェックします
                    pwdRule.checkPassword(this.txt_R_UserId.getText(), nowPassword, password);
                }
                catch (EmAuthenticationException ex)
                {
                    String errMsg = ex.getMessageResourcekey();

                    if (errMsg.indexOf("MSG-T0022") != -1)
                    {
                        // 現在のパスワードを正しく入力してください。
                        errMsg = errMsg.replaceAll("MSG-T0022", "6403055");
                    }
                    else if (errMsg.indexOf("MSG-T0027") != -1)
                    {
                        // パスワードには{0}文字以上を設定して下さい。
                        errMsg = errMsg.replaceAll("MSG-T0027", "6403056");
                    }
                    else if (errMsg.indexOf("MSG-T0029") != -1)
                    {
                        // 以前に使用したパスワードは設定できません。
                        errMsg = errMsg.replaceAll("MSG-T0029", "6403057");
                    }
                    else if (errMsg.indexOf("MSG-T0031") != -1)
                    {
                        // パスワードには英数字両方が含まれるように設定して下さい。
                        errMsg = errMsg.replaceAll("MSG-T0031", "6403058");
                    }
                    else if (errMsg.indexOf("MSG-T0028") != -1)
                    {
                        // ユーザIDと同じパスワードは設定できません。
                        errMsg = errMsg.replaceAll("MSG-T0028", "6403059");
                    }
                    else if (errMsg.indexOf("MSG-T0030") != -1)
                    {
                        // 同じ文字が{0}回以上連続するパスワードは設定できません。
                        errMsg = errMsg.replaceAll("MSG-T0030", "6403060");
                    }

                    setFocus(txt_Password);
                    this.message.setMsgResourceKey(errMsg);

                    return;
                }

                // 入力されたパスワードを新パスワードとしてユーザオブジェクトに設定
                user.setPassword(this.txt_Password.getText());

                PassWordHistoryHandler pwdHistoryHandler = EmHandlerFactory.getPassWordHistoryHandler(conn);
                pwdHistoryHandler.deleteByUserIdAuth(user.getUserID());

                AuthenticationSystem authSys =
                        EmHandlerFactory.getAuthenticationSystemHandler(conn).findAuthenticationSystem();

                if (1 < authSys.getPassLogCheckTime())
                {
                    // nowPasswordを直近の過去パスワードとして登録する
                    LogInfo nowLogInfo = createLogInfo(this.txt_R_UserId.getText(), nowPassword);
                    pwdHistoryHandler.createPasswordHistory(nowLogInfo);
                }

                // ユーザに設定されているパスワード更新期間を取得
                int pwdChangeInterval = user.getPwdChangeInterval();

                // ユーザに設定されているパスワード更新期間が-2（ロール設定を継承）の時
                if (pwdChangeInterval == PWDCHANGEINTERVAL_STATUS_EXTEND)
                {
                    // ロールハンドラを取得
                    RoleHandler roleHandler = EmHandlerFactory.getRoleHandler(conn);
                    // このユーザのロール情報を取得
                    jp.co.daifuku.emanager.database.entity.Role role = roleHandler.findByRoleId(user.getRoleID());

                    if (role != null)
                    {
                        // ロールに設定されているパスワード更新期間を取得
                        pwdChangeInterval = role.getPassChangeInterval();
                    }
                }

                // パスワード更新期間が-2ならシステム設定を使用する
                if (pwdChangeInterval == PWDCHANGEINTERVAL_STATUS_EXTEND)
                {
                    // システム設定のパスワード更新期間を取得
                    AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
                    AuthenticationSystem authSystem = authHandler.findAuthenticationSystem();
                    pwdChangeInterval = authSystem.getPassChangeInterval();
                }

                // パスワード更新期間が-1（無期限）のとき
                if (pwdChangeInterval == PWDCHANGEINTERVAL_STATUS_NOLIMITED)
                {
                    user.setPwdExpire(null);
                }
                else
                {
                    // 無期限でないならばパスワードを新たに設定する今日＋パスワード設定期間をパスワード期限とする
                    // 今日のカレンダー取得
                    Calendar calendar = new GregorianCalendar();
                    // 今日の日付にパスワード設定期間を足してパスワード期限にする
                    calendar.add(Calendar.DATE, pwdChangeInterval);
                    // パスワード期限日のDateオブジェクトを取得
                    Date pwdExpire = calendar.getTime();
                    // パスワード期限日をユーザオブジェクトに設定
                    user.setPwdExpire(pwdExpire);
                }

                // 仮パスワードフラグ設定
                user.setDummyPassFlag(authSys.getDummyPassFlag());

                // 処理終了メッセージ
                comp_msg = "6401002";
            }

            // セッションスコープからユーザ情報を取得
            DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();

            UserInfoHandler userhandler = null;

            // セッションスコープからユーザ情報を取得できたかどうか
            if (userinfo != null)
            {
                // UserHandlerInfoを取得
                userhandler = new UserInfoHandler(userinfo);
            }

            if (userhandler != null)
            {
                // 更新ユーザ設定
                user.setUpdateUser(userhandler.getUserID());
            }

            // 更新IPアドレス設定
            user.setUpdateTerminal(this.httpRequest.getRemoteAddr());

            // 処理区分設定
            user.setUpdateKind(UPDATE_KIND_UPDATE);
            // 処理クラス名を設定
            user.setUpdateProcess(this.getClass().getName());
            // ユーザ情報アップデート
            if (userHandler.updateByUserId(user) == 0)
            {
                // 指定されたユーザIDは登録されていません。
                this.message.setMsgResourceKey("6403015");
                return;
            }

            // Part11ログ出力
            this.part11UserMasterLog(conn, user, beforeUpdate, operationType);

            // コミット
            EmConnectionHandler.commit(conn);
            // クリア処理
            btn_Clear_Click(e);
            //メッセージをセット
            message.setMsgResourceKey(comp_msg);

            String msg = "";
            // 処理区分がロック解除のとき
            if (proc.equals(LOCK_RELEASE))
            {
                msg = "6400031";

            }
            // 処理区分が再パスワードの時
            else if (proc.equals(PWD_REISSUE))
            {
                msg = "6400032";
            }

            msg += Message.MSG_DELIM + getKeyEqualValueString(EmTableColumns.LOGINUSER_USERID, user.getUserID());
            EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_USER, msg, "");
        }
        catch (SQLException ex)
        {
            EmConnectionHandler.rollback(conn);
            int errCode = ex.getErrorCode();

            if (errCode == EmConstants.SQLERRORCODE_ORA0001)
            {
                //一意制約に違反しました。
                message.setMsgResourceKey("6403068");
                return;
            }
            else
            {
                //データベースエラーが発生しました。
                message.setMsgResourceKey("6403069");
                return;
            }
        }
        catch (Exception ex)
        {
            EmConnectionHandler.rollback(conn);
            throw ex;
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }


    /** 
     * This method creates the logs related to Part11 namely, OperationLog and User master log
     * @param e ActionEvent 
     * @throws Exception 
     */
    private void part11UserMasterLog(Connection conn, User currentUser, User beforeUpdate, int operationType)
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
        userMasterLog.setDsNumber(userInfo.getDsNumber());
        userMasterLog.setPageName(userInfo.getPageNameResourceKey());


        userMasterLog.setUpdateKind(EmConstants.USERMASTER_CLASS_MODIFY);
        userMasterLog.setMasterUserId(currentUser.getUserID());

        userMasterLog.setUserNameBefore(beforeUpdate.getUserName());
        userMasterLog.setPasswordBefore(EmConstants.OPELOG_PASSWORD);
        userMasterLog.setPasswordChangeIntervalBefore(beforeUpdate.getPwdChangeInterval());
        userMasterLog.setPwdExpiresBefore(beforeUpdate.getPwdExpire());
// 2008/12/16,2009/01/20 K.Matsuda Start テーブル仕様変更に併せて修正
        userMasterLog.setUserStatusBefore(beforeUpdate.getUserStatus());
// 2008/12/16,2009/01/20 K.Matsuda End
        userMasterLog.setSameUserLoginMaxBefore(beforeUpdate.getSameUserLoginMax());
        userMasterLog.setFailedLoginAttemptsBefore(beforeUpdate.getFailLoginAttem());
        userMasterLog.setRoleIdBefore(beforeUpdate.getRoleID());
        userMasterLog.setDepartmentBefore(beforeUpdate.getDepartment());
        userMasterLog.setRemarkBefore(beforeUpdate.getRemarks());

        userMasterLog.setUserNameAfter(currentUser.getUserName());
        userMasterLog.setPasswordAfter(EmConstants.OPELOG_PASSWORD);
        userMasterLog.setPasswordChangeIntervalAfter(currentUser.getPwdChangeInterval());
        userMasterLog.setPwdExpiresAfter(currentUser.getPwdExpire());
// 2008/12/16,2009/01/20 K.Matsuda Start テーブル仕様変更に併せて修正
        userMasterLog.setUserStatusAfter(currentUser.getUserStatus());
// 2008/12/16,2009/01/20 K.Matsuda End
        userMasterLog.setSameUserLoginMaxAfter(currentUser.getSameUserLoginMax());
        userMasterLog.setFailedLoginAttemptsAfter(currentUser.getFailLoginAttem());
        userMasterLog.setRoleIdAfter(currentUser.getRoleID());
        userMasterLog.setDepartmentAfter(currentUser.getDepartment());
        userMasterLog.setRemarkAfter(currentUser.getRemarks());

        //項目リストの生成
        List list = new ArrayList();
        list.add(currentUser.getUserID());
        list.add(currentUser.getUserName());
// 2008/12/16 K.Matsuda Start テーブル仕様変更に併せて修正
        list.add(String.valueOf(currentUser.getUserStatus()));
// 2008/12/16 K.Matsuda End
        list.add(EmConstants.OPELOG_PASSWORD);
        list.add(txt_PwdExpires.getText());
        P11LogWriter logWriter = new P11LogWriter(conn);
        logWriter.createOperationLog(userInfo, operationType, list);
        logWriter.createMasterChengeLog(userMasterLog);
    }

    /**
     * ユーザIDとパスワードを元にLogInfoオブジェクトを作成する。（処理区分は新規）
     * @param userId
     * @param nowPassword
     * @return LogInfo オブジェクト
     */
    private LogInfo createLogInfo(String userId, String nowPassword)
    {
        LogInfo logInfo = new LogInfo();

        // セッションスコープからユーザ情報を取得
        DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();

        UserInfoHandler userhandler = null;

        // セッションスコープからユーザ情報を取得できたかどうか
        if (userinfo != null)
        {
            userhandler = new UserInfoHandler(userinfo);
        }

        // UserInfoHandlerを取得できたかどうか
        if (userhandler != null)
        {
            // 各ユーザ情報を格納
            logInfo.setUpdateUser(userhandler.getUserID());
            logInfo.setUpdateTerminal(userhandler.getIPAddress());
        }

        logInfo.setUserId(userId);
        logInfo.setOldPassword(nowPassword);
        logInfo.setUpdateProcess(this.getClass().getName());
        logInfo.setUpdateKind(UPDATE_KIND_INSERT);

        return logInfo;
    }
    
// 2008/12/25 K.Matsuda Start 赤*を非表示
    /**
     * 必須項目のマーク(*)を非表示にします
     */
    private void setVisibleRequireMarks(boolean visible)
    {
        // *を非表示にする
        lbl_RequireUserId.setVisible(visible);
        lbl_RequirePassword.setVisible(visible);
        lbl_RequirePasswordAgain.setVisible(visible);
    }
// 2008/12/25 K.Matsuda End

    /** 
     * 再パスワードボタンが押下された時の処理を実装します
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_PasswordReissue_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        this.btn_Clear_Click(e);

// 2009/07/09 T.Kajiwara Mod DAIFUKU_SV,DAIFUKU_PRIVATE_USERを指定したときは表示しない
        if (EmConstants.DAIFUKU_SV_USER.equals(this.txt_UserId.getText())
        		|| EmConstants.DAIFUKU_PRIVATE_USER.equals(this.txt_UserId.getText()))
        {
            // 一致するユーザが見つからなかったとき
            // 指定されたユーザIDは登録されていません。
            this.message.setMsgResourceKey("6403015");
            return;
        }
// 2009/07/09 T.Kajiwara End        
        // システム定義ユーザチェック
        if (txt_UserId.getText().equals(ANONYMOUS_USER))
        {
            //指定されたユーザIDはシステム定義のユーザのため修正できません。
            message.setMsgResourceKey("6403020");
            return;
        }
        
        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);

            // ユーザハンドラ取得
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);

            // 入力されたユーザIDを持つユーザエンティティを取得
            User user = userHandler.findByUserId(this.txt_UserId.getText());

            // ユーザIDが一致するユーザが存在するかチェック
            if (user == null)
            {
                // 一致するユーザが見つからなかったとき
                // 指定されたユーザIDは登録されていません。
                this.message.setMsgResourceKey("6403015");
                return;
            }

            // エンティティの情報を各コントロールにセット
            mapping(user);

            this.txt_Password.setReadOnly(false);
            this.txt_PasswordAgain.setReadOnly(false);

            // ロック解除処理を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, PWD_REISSUE);

            // ボタンの使用不可を設定
            changebtn();
        }
        finally
        {
            // コネクションを閉じる
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        this.txt_R_UserId.setText("");
        this.txt_UserName.setText("");
        this.txt_StateOfLock.setText("");
        this.txt_Password.setText("");
        this.txt_PasswordAgain.setText("");
        this.txt_PwdExpires.setText("");

        this.txt_Password.setReadOnly(true);
        this.txt_PasswordAgain.setReadOnly(true);

        // ロック解除ボタン
        this.btn_LockCancellation.setEnabled(true);
        // 再パスワードボタン
        this.btn_PasswordReissue.setEnabled(true);
        // 設定ボタン
        this.btn_Commit.setEnabled(false);
        // クリアボタン
        this.btn_Clear.setEnabled(false);
    }

    /**
     * key文字列とvalue文字列を"="で連結して返します
     * @param key key
     * @param value value
     * @return 連結した文字列
     */
    private String getKeyEqualValueString(String key, String value)
    {
        return key + "=" + value;
    }

    /**
     * ポップアップウインドから、戻ってくるときにこのメソッドが
     * 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        DialogParameters param = ((DialogEvent)e).getDialogParameters();
        //ユーザIDを取得
        String userkey = param.getParameter(UserListBusiness.USERID_KEY);
        //空ではないときに値をセットする
        if (!Validator.isEmptyCheck(userkey))
        {
            txt_UserId.setText(userkey);
            setFocus(txt_UserId);
        }
    }

    /** 
     * メニューへボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
    }
}
//end of class

// $Id: AuthenticationSystem2Business.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.system;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.handler.AuthenticationHandler;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.emanager.util.EManagerUtil;
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
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
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
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 </en> */
public class AuthenticationSystem2Business
        extends AuthenticationSystem2
        implements EmConstants
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /** 
     * 各コントロールイベント呼び出し前に呼び出されます。
     * @param e ActionEvent
     * @exception Exception
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
    }

    /**
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @exception Exception
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

        Connection conn = null;
        try
        {
            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            AuthenticationHandler authenHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);

            //DBより取得
            AuthenticationSystem authenSystem = authenHandler.findAuthenticationSystem();
            if (authenSystem == null)
            {
                //各項目を非活性化

                //画面ロック時間
                this.txt_FailedLoginScreenLockTime.setReadOnly(true);

                //画面SessionTimeout Time
                this.txt_SessionTimeoutTime.setReadOnly(true);
                //ユーザロック設定
                this.rdo_FailedLoginUserLock_ON.setEnabled(false);
                this.rdo_FailedLoginUserLock_OFF.setEnabled(false);
                //IP有効範囲
                this.txt_IPRange_Min.setReadOnly(true);
                this.txt_IPRange_Max.setReadOnly(true);
                //パスワード履歴回数
                this.txt_PasswordLogCheckTime.setReadOnly(true);
                //パスワード最低文字数
                this.txt_PasswordMinLength.setReadOnly(true);
                //認証ミス猶予回数
                this.rdo_FailedLoginAttempts.setEnabled(false);
                this.txt_FailedLoginAttempts.setReadOnly(true);
                this.rdo_FailedLogin_Unrestricted.setEnabled(false);
                //パスワード更新間隔
                this.rdo_PwdChangeInterval.setEnabled(false);
                this.txt_PwdChangeInterval.setReadOnly(true);
                this.rdo_PwdChange_Unrestricted.setEnabled(false);
                //設定ボタン
                this.btn_Commit.setEnabled(false);
                //全取消ボタン
                this.btn_Cancel.setEnabled(false);

                return;
            }

            //各項目に値を表示
            mapping(authenSystem);

        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }

    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**
     * 各画面項目に設定値をセットします。
     * @param authenSystem AuthenticationSystem エンティティ
     */
    private void mapping(AuthenticationSystem authenSystem)
    {
        //画面ロック時間
        //		this.txt_FailedLoginScreenLockTime.setReadOnly( !(0<authenSystem.getFailedLoginscreenLockCount()) );
        this.txt_FailedLoginScreenLockTime.setReadOnly(false);

        this.txt_FailedLoginScreenLockTime.setInt(authenSystem.getFaieldLoginScreenLockTime());


        //Session Timeout Time
        this.txt_SessionTimeoutTime.setReadOnly(false);
        this.txt_SessionTimeoutTime.setInt(authenSystem.getSessionTimeoutTime());

        //ユーザロック設定
        this.rdo_FailedLoginUserLock_ON.setChecked(authenSystem.getFaieldLoginUserLockFlag());
        this.rdo_FailedLoginUserLock_OFF.setChecked(!authenSystem.getFaieldLoginUserLockFlag());

        //IPチェック
        if (authenSystem.getIpRangeCheckFlag())
        {
            //IPチェックを行う場合
            //IP有効範囲(最小)
            this.txt_IPRange_Min.setReadOnly(false);
            this.txt_IPRange_Min.setText(authenSystem.getIprangeMin());
            //IP有効範囲(最大)
            this.txt_IPRange_Max.setReadOnly(false);
            this.txt_IPRange_Max.setText(authenSystem.getIprangeMax());
        }
        else
        {
            //IPチェックを行わない場合
            //IP有効範囲(最小)
            this.txt_IPRange_Min.setReadOnly(true);
            //IP有効範囲(最大)
            this.txt_IPRange_Max.setReadOnly(true);
        }

        //パスワード履歴回数
        this.txt_PasswordLogCheckTime.setInt(authenSystem.getPassLogCheckTime());

        //パスワード最低文字数
        this.txt_PasswordMinLength.setInt(authenSystem.getPassMinLength());

        //認証ミス猶予回数
        this.rdo_FailedLoginAttempts.setEnabled(authenSystem.getFaieldLoginUserLockFlag());
        //this.txt_FailedLoginAttempts.setVisible( authenSystem.getFaieldLoginUserLockFlag() );
        this.rdo_FailedLogin_Unrestricted.setEnabled(authenSystem.getFaieldLoginUserLockFlag());

        int failLoginAttem = authenSystem.getFailLoginAttem();
        if (failLoginAttem != FAILED_ATTEMPTS_STATUS_NOLIMITED)
        {
            //制限有
            this.rdo_FailedLoginAttempts.setChecked(true);
            this.txt_FailedLoginAttempts.setReadOnly(!authenSystem.getFaieldLoginUserLockFlag());
            this.txt_FailedLoginAttempts.setInt(failLoginAttem);
        }
        else
        {
            //無制限
            this.rdo_FailedLogin_Unrestricted.setChecked(true);
            this.txt_FailedLoginAttempts.setReadOnly(true);
            this.txt_FailedLoginAttempts.setText("");
        }

        //パスワード更新間隔
        this.rdo_PwdChangeInterval.setEnabled(authenSystem.getPassExpireFlag());
        //this.txt_PwdChangeInterval.setVisible( authenSystem.getPassExpireFlag() );
        this.rdo_PwdChange_Unrestricted.setEnabled(authenSystem.getPassExpireFlag());

        int passChangeInterval = authenSystem.getPassChangeInterval();
        if (passChangeInterval != PWDCHANGEINTERVAL_STATUS_NOLIMITED)
        {
            //制限有
            this.rdo_PwdChangeInterval.setChecked(true);
            this.txt_PwdChangeInterval.setReadOnly(!authenSystem.getPassExpireFlag());
            this.txt_PwdChangeInterval.setInt(passChangeInterval);
        }
        else
        {
            //無制限
            this.rdo_PwdChange_Unrestricted.setChecked(true);
            this.txt_PwdChangeInterval.setReadOnly(true);
            this.txt_PwdChangeInterval.setText("");
        }
    }

    /**
     * IPアドレスのチェックを行います。
     * @param ip IPアドレスの文字列
     * @return TRUE 有効
     */
    private boolean ipCheck(String ip)
    {
        String reg = "(25[0-5]|(2[0-4]|1[0-9]|[1-9])?[0-9])";
        String addr = reg + "\\." + reg + "\\." + reg + "\\." + reg;
        Pattern pattern = Pattern.compile(addr);
        Matcher matcher = pattern.matcher(ip);
        boolean bool = matcher.matches();
        return bool;
    }

    /**
     * ユーザロックチェックにより入力フィールドの属性を変更します。
     * @param enabled ユーザロック設定
     */
    private void setFailedLoginAttemptsEnabled(boolean enabled)
    {
        this.rdo_FailedLoginAttempts.setEnabled(enabled);
        this.rdo_FailedLogin_Unrestricted.setEnabled(enabled);
        this.txt_FailedLoginAttempts.setReadOnly(!enabled || !this.rdo_FailedLoginAttempts.getChecked());
    }

// 2008/12/25 K.Matsuda Start 赤*を非表示
    /**
     * 必須項目のマーク(*)を非表示にします
     */
    private void setVisibleRequireMarks(boolean visible)
    {
        // *を非表示にする
        lbl_RequireScreenLockTime.setVisible(visible);
        lbl_RequireSessionTimeout.setVisible(visible);
        lbl_RequirePwdLogCheckTime.setVisible(visible);
        lbl_RequirePasswordMinLength.setVisible(visible);
    }
// 2008/12/25 K.Matsuda End
    
    // Event handler methods -----------------------------------------

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

    /** 
     * 設定ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Commit_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            AuthenticationHandler authenHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);

            //DBより取得
            AuthenticationSystem authenSystem = authenHandler.findAuthenticationSystem();

            //入力チェック
            //画面ロック時間
            this.txt_FailedLoginScreenLockTime.validate(this, true);
            if (this.txt_FailedLoginScreenLockTime.getInt() < 0)
            {
                //{0}の値は、{1}以上の数値で入力してください。
                message.setMsgResourceKey("6403075");
                ArrayList params = new ArrayList();
                params.add(DispResources.getText(this.lbl_FailedLoginScreenLockTime.getResourceKey()));
                params.add("0");
                message.setMsgParameter(params);
                setFocus(txt_FailedLoginScreenLockTime);
                return;
            }

            // session timeout time validation
            this.txt_SessionTimeoutTime.validate(this, true);
            if (txt_SessionTimeoutTime.getInt() < SESSIONTIMEOUT_MIN
                    || txt_SessionTimeoutTime.getInt() > SESSIONTIMEOUT_MAX)
            {
                //10～86400の間の値を入力してください。
                message.setMsgResourceKey("6403070");
                setFocus(txt_SessionTimeoutTime);
                return;
            }

            //IP有効範囲
            if (authenSystem.getIpRangeCheckFlag())
            {
                //IP有効範囲(最小)
                this.txt_IPRange_Min.validate(this, true);
                //IPチェック
                if (!this.ipCheck(this.txt_IPRange_Min.getText()))
                {
                    message.setMsgResourceKey("6403072");
                    ArrayList params = new ArrayList();
                    params.add(DispResources.getText(this.lbl_IPRange_Min.getResourceKey()));
                    message.setMsgParameter(params);
                    setFocus(txt_IPRange_Min);
                    return;
                }
                //IP有効範囲(最大)
                this.txt_IPRange_Max.validate(this, true);
                //IPチェック
                if (!this.ipCheck(this.txt_IPRange_Max.getText()))
                {
                    message.setMsgResourceKey("6403072");
                    ArrayList params = new ArrayList();
                    params.add(DispResources.getText(this.lbl_IPRange_Max.getResourceKey()));
                    message.setMsgParameter(params);
                    setFocus(txt_IPRange_Max);
                    return;
                }
                //IP比較
                if (1 == EManagerUtil.compareIpAddress(this.txt_IPRange_Min.getText(), this.txt_IPRange_Max.getText()))
                {
                    message.setMsgResourceKey("6403073");
                    ArrayList params = new ArrayList();
                    params.add(DispResources.getText(this.lbl_IPRange_Max.getResourceKey()));
                    params.add(DispResources.getText(this.lbl_IPRange_Min.getResourceKey()));
                    message.setMsgParameter(params);
                    setFocus(txt_IPRange_Min);
                    return;
                }
            }
            //パスワード履歴回数
            this.txt_PasswordLogCheckTime.validate(this, true);
            if (this.txt_PasswordLogCheckTime.getInt() < 0 || 10 < this.txt_PasswordLogCheckTime.getInt())
            {
                //{0}の値は、{1}以上の数値で入力してください。
                message.setMsgResourceKey("6403074");
                ArrayList params = new ArrayList();
                params.add(DispResources.getText(this.lbl_PasswordLogCheckTime.getResourceKey()));
                params.add("0");
                params.add("10");
                message.setMsgParameter(params);
                setFocus(txt_PasswordLogCheckTime);
                return;
            }
            //パスワード最低文字数
            this.txt_PasswordMinLength.validate(this, true);
            if (this.txt_PasswordMinLength.getInt() < 1 || 20 < this.txt_PasswordMinLength.getInt())
            {
                //{0}の値は、{1}～{2}の間の数値で入力してください。
                message.setMsgResourceKey("6403074");
                ArrayList params = new ArrayList();
                params.add(DispResources.getText(this.lbl_PasswordMinLength.getResourceKey()));
                params.add("1");
                params.add("20");
                message.setMsgParameter(params);
                setFocus(txt_PasswordMinLength);
                return;
            }
            //認証ミス猶予回数
            if (this.rdo_FailedLoginUserLock_ON.getChecked() && this.rdo_FailedLoginAttempts.getChecked())
            {
                this.txt_FailedLoginAttempts.validate(this, true);
                if (this.txt_FailedLoginAttempts.getInt() < 0)
                {
                    //{0}の値は、{1}以上の数値で入力してください。
                    message.setMsgResourceKey("6403075");
                    ArrayList params = new ArrayList();
                    params.add(DispResources.getText(this.lbl_FailedLoginAttempts.getResourceKey()));
                    params.add("0");
                    message.setMsgParameter(params);
                    setFocus(txt_FailedLoginAttempts);
                    return;
                }
            }
            //パスワード更新間隔
            if (authenSystem.getPassExpireFlag() && this.rdo_PwdChangeInterval.getChecked())
            {
                this.txt_PwdChangeInterval.validate(true);
                if (this.txt_PwdChangeInterval.getInt() < 1)
                {
                    //{0}の値は、{1}以上の数値で入力してください。
                    message.setMsgResourceKey("6403075");
                    ArrayList params = new ArrayList();
                    params.add(DispResources.getText(this.lbl_PwdChangeInterval.getResourceKey()));
                    params.add("1");
                    message.setMsgParameter(params);
                    setFocus(txt_PwdChangeInterval);
                    return;
                }
            }

            //更新処理
            //画面ロック時間
            authenSystem.setFaieldLoginScreenLockTime(this.txt_FailedLoginScreenLockTime.getInt());

            //ユーザロック設定
            authenSystem.setFaieldLoginUserLockFlag(this.rdo_FailedLoginUserLock_ON.getChecked());

            //タイムアウト時間
            authenSystem.setSessionTimeoutTime(this.txt_SessionTimeoutTime.getInt());

            //IP有効範囲
            if (authenSystem.getIpRangeCheckFlag())
            {
                //IP有効範囲(最小)
                authenSystem.setIprangeMin(this.txt_IPRange_Min.getText());
                //IP有効範囲(最大)
                authenSystem.setIprangeMax(this.txt_IPRange_Max.getText());
            }

            //パスワード履歴回数
            authenSystem.setPassLogCheckTime(this.txt_PasswordLogCheckTime.getInt());

            //パスワード最低文字列
            authenSystem.setPassMinLength(this.txt_PasswordMinLength.getInt());

            //認証ミス猶予回数
            if (this.rdo_FailedLoginUserLock_ON.getChecked())
            {
                if (this.rdo_FailedLoginAttempts.getChecked())
                {
                    authenSystem.setFailLoginAttem(this.txt_FailedLoginAttempts.getInt());
                }
                else
                {
                    authenSystem.setFailLoginAttem(FAILED_ATTEMPTS_STATUS_NOLIMITED);
                }
            }

            if (authenSystem.getPassExpireFlag())
            {
                //パスワード更新間隔
                if (this.rdo_PwdChangeInterval.getChecked())
                {
                    authenSystem.setPassChangeInterval(this.txt_PwdChangeInterval.getInt());
                }
                else
                {
                    authenSystem.setPassChangeInterval(PWDCHANGEINTERVAL_STATUS_NOLIMITED);
                }
            }

            //登録したユーザ情報を設定
            DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();
            if (userinfo != null)
            {
                UserInfoHandler userhandler = new UserInfoHandler(userinfo);
                if (userhandler != null)
                {
                    //更新ユーザ
                    authenSystem.setUpdateUser(userhandler.getUserID());
                }
            }
            //更新端末IP
            authenSystem.setUpdateTerminal(this.httpRequest.getRemoteAddr());
            //更新
            authenSystem.setUpdateKind(2);
            //更新処理名
            authenSystem.setUpdateProcess(this.getClass().getName());

            //DB更新
            if (authenHandler.updateAuthenticationSystem(authenSystem) == 0)
            {
                //修正対象データがありませんでした。
                message.setMsgResourceKey("6403002");
                return;
            }


            //ユーザ情報の作成
            DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
            //項目リストの生成
            List list = new ArrayList();
            list.add(txt_FailedLoginScreenLockTime.getText());
            list.add(txt_SessionTimeoutTime.getText());
            list.add(txt_IPRange_Min.getText());
            list.add(txt_IPRange_Max.getText());
            list.add(txt_PasswordLogCheckTime.getText());
            list.add(txt_PasswordMinLength.getText());
            list.add(rdo_FailedLoginUserLock_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                            : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(rdo_FailedLoginAttempts.getChecked() ? txt_FailedLoginAttempts.getText()
                                                         : String.valueOf(EmConstants.FAILED_ATTEMPTS_STATUS_NOLIMITED));
            list.add(rdo_PwdChangeInterval.getChecked() ? txt_PwdChangeInterval.getText()
                                                       : String.valueOf(EmConstants.PWDCHANGEINTERVAL_STATUS_NOLIMITED));
            //オペレーションログ出力
            P11LogWriter logWriter = new P11LogWriter(conn);
            logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_SETTING, list);

            //設定
            conn.commit();

            //6401008	更新しました。
            message.setMsgResourceKey("6401008");

            //メンテナンスログに出力するメッセージ
            //システムメンテナンスの設定を行いました。
            String mainteLog_msg = "6400009";
            //メンテナンスログ出力
            EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_SYSTEM, mainteLog_msg, "");

        }
        catch (Exception ex)
        {
            if (conn != null)
            {
                conn.rollback();
            }
            throw ex;
        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * 取消ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Cancel_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            AuthenticationHandler authenHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);

            //DBより取得
            AuthenticationSystem authenSystem = authenHandler.findAuthenticationSystem();
            if (authenSystem == null)
            {
                return;
            }

            //各項目に値を表示
            mapping(authenSystem);
        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_FailedLoginAttempts_Click(ActionEvent e)
            throws Exception
    {
        this.txt_FailedLoginAttempts.setReadOnly(false);
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_FailedLogin_Unrestricted_Click(ActionEvent e)
            throws Exception
    {
        this.txt_FailedLoginAttempts.setReadOnly(true);
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_PwdChangeInterval_Click(ActionEvent e)
            throws Exception
    {
        this.txt_PwdChangeInterval.setReadOnly(false);
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_PwdChange_Unrestricted_Click(ActionEvent e)
            throws Exception
    {
        this.txt_PwdChangeInterval.setReadOnly(true);
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_FailedLoginUserLock_ON_Click(ActionEvent e)
            throws Exception
    {
        setFailedLoginAttemptsEnabled(true);
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_FailedLoginUserLock_OFF_Click(ActionEvent e)
            throws Exception
    {
        setFailedLoginAttemptsEnabled(false);
    }

}
//end of class

// $Id: AuthenticationSystem1Business.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.system;

import java.sql.Connection;
import java.sql.SQLException;
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
public class AuthenticationSystem1Business
        extends AuthenticationSystem1
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
        
        //	    //プロダクトタイプをViewStateに保存
        //	    this.getViewState().setString("PRODUCTTYPE",this.request.getParameter(PRODUCTTYPE));
        //	    //メニュータイプをViewStateに保存
        //	    this.getViewState().setString("MENUTYPE",this.request.getParameter(MENUTYPE));

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

                //同時ログイン数
                this.txt_LoginMax.setReadOnly(true);
                //自動ログイン設定
                this.rdo_AutoLogin_ON.setEnabled(false);
                this.rdo_AutoLogin_OFF.setEnabled(false);
                //同一ユーザログイン
                this.rdo_SameLoginUser_ON.setEnabled(false);
                this.rdo_SameLoginUser_OFF.setEnabled(false);
                //端末ユーザ制限
                this.rdo_TerminalUserCheck_ON.setEnabled(false);
                this.rdo_TerminalUserCheck_OFF.setEnabled(false);
                //端末切替設定
                this.rdo_TerminalChange_ON.setEnabled(false);
                this.rdo_TerminalChange_OFF.setEnabled(false);
                //画面ログインチェック
                this.rdo_ScreenLogInCheck_ON.setEnabled(false);
                this.rdo_ScreenLogInCheck_OFF.setEnabled(false);

                //画面SessionTimeout Time
                this.txt_SessionTimeoutTime.setReadOnly(true);

                //画面ロック時間
                this.txt_FailedLoginScreenLockTime.setReadOnly(true);

                //this.txt_FailedLoginScreenLockTime.setVisible(true);

                //画面ロックカウント
                this.rdo_FailedLoginScreenLock_ON.setEnabled(false);
                this.txt_FailedLoginScreenLockCount.setReadOnly(true);
                this.rdo_FailedLoginScreenLock_OFF.setEnabled(false);
                //ユーザロック設定
                this.rdo_FailedLoginUserLock_ON.setEnabled(false);
                this.rdo_FailedLoginUserLock_OFF.setEnabled(false);
                //端末アクセス制限
                this.rdo_TerminalCheck_ON.setEnabled(false);
                this.rdo_TerminalCheck_OFF.setEnabled(false);
                //IP制限
                this.rdo_IPRangeCheck_ON.setEnabled(false);
                this.rdo_IPRangeCheck_OFF.setEnabled(false);
                //IP有効範囲
                this.txt_IPRange_Min.setReadOnly(true);
                this.txt_IPRange_Max.setReadOnly(true);
                //仮パスワード設定
                this.rdo_DummyPassWord_ON.setEnabled(false);
                this.rdo_DummyPassWord_OFF.setEnabled(false);
                //パスワード安全性チェック
                this.rdo_PassWordSaftyCheck_ON.setEnabled(false);
                this.rdo_PassWordSaftyCheck_OFF.setEnabled(false);
                //パスワード有効期間チェック
                this.rdo_PasswordExpire_ON.setEnabled(false);
                this.rdo_PasswordExpire_OFF.setEnabled(false);
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
                //パスワード切れ通知日数
                this.txt_PasswordExpireAlertDays.setReadOnly(true);
                // Part11ログ出力設定
                this.rdo_Part11Log_ON.setEnabled(false);
                this.rdo_Part11Log_OFF.setEnabled(false);
                // 画面アクセスログ出力設定
                this.rdo_AccessLog_ON.setEnabled(false);
                this.rdo_AccessLog_OFF.setEnabled(false);
                // マスタ改廃ログ出力設定
                this.rdo_MasterLog_ON.setEnabled(false);
                this.rdo_MasterLog_OFF.setEnabled(false);
                // 在庫情報ログ出力設定
                this.rdo_StockLog_ON.setEnabled(false);
                this.rdo_StockLog_OFF.setEnabled(false);
                // 画面操作ログ出力設定
                this.rdo_OperationLog_ON.setEnabled(false);
                this.rdo_OperationLog_OFF.setEnabled(false);
                // ログデータ保持日数
                this.txt_DBLogHoldDays.setReadOnly(true);
                // ログファイル作成単位(日)
                this.txt_CsvLogHoldDays.setReadOnly(true);
                // ログファイルディスク保持期間(年)
                this.txt_HDLogHoldYears.setReadOnly(true);
                //認証ログ出力設定
                this.rdo_AuthenticationLog_ON.setEnabled(false);
                this.rdo_AuthenticationLog_OFF.setEnabled(false);
                //メンテナンスログ出力設定
                this.rdo_UserMaintenanceLog_ON.setEnabled(false);
                this.rdo_UserMaintenanceLog_OFF.setEnabled(false);
                //同一ユーザ作成禁止期間(日)
                this.rdo_SameUserBlock_restricted.setEnabled(false);
                this.txt_SameUserCreateBlockPeriod.setReadOnly(true);
                this.rdo_SameUserBlock_Unrestricted.setEnabled(false);
                //メインメニューの種類
                this.rdo_LargeIcon.setEnabled(false);
                this.rdo_SmallIcon.setEnabled(false);
                //設定ボタン
                //this.btn_Commit.setEnabled( false );
                this.btn_CommitSync.setEnabled(false);
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
            throws SQLException
    {
        //同時ログイン数
        this.txt_LoginMax.setInt(authenSystem.getLoginMax());

        //自動ログイン設定
        this.rdo_AutoLogin_ON.setChecked(authenSystem.getAutoLoginFlag());
        this.rdo_AutoLogin_OFF.setChecked(!authenSystem.getAutoLoginFlag());

        //同一ユーザログイン
        this.rdo_SameLoginUser_ON.setChecked(authenSystem.getSameLoginUserFlag());
        this.rdo_SameLoginUser_OFF.setChecked(!authenSystem.getSameLoginUserFlag());

        //端末ユーザ制限
        this.rdo_TerminalUserCheck_ON.setChecked(authenSystem.getTerminalUserCheckFlag());
        this.rdo_TerminalUserCheck_OFF.setChecked(!authenSystem.getTerminalUserCheckFlag());

        //端末切替設定
        this.rdo_TerminalChange_ON.setChecked(authenSystem.getTerminalChangeFlag());
        this.rdo_TerminalChange_OFF.setChecked(!authenSystem.getTerminalChangeFlag());

        //画面ログインチェック
        this.rdo_ScreenLogInCheck_ON.setChecked(authenSystem.getScreenLoginCheckFlag());
        this.rdo_ScreenLogInCheck_OFF.setChecked(!authenSystem.getScreenLoginCheckFlag());

        //Session Timeout Time
        this.txt_SessionTimeoutTime.setInt(authenSystem.getSessionTimeoutTime());

        //画面ロック時間
        boolean screenLock_ON = 0 < authenSystem.getFailedLoginscreenLockCount();
        this.txt_FailedLoginScreenLockTime.setInt(authenSystem.getFaieldLoginScreenLockTime());
        //		this.txt_FailedLoginScreenLockTime.setReadOnly( !screenLock_ON );

        //画面ロックカウント
        if (screenLock_ON)
        {
            this.txt_FailedLoginScreenLockCount.setInt(authenSystem.getFailedLoginscreenLockCount());
        }
        else
        {
            this.txt_FailedLoginScreenLockCount.setText("");
        }
        this.txt_FailedLoginScreenLockCount.setReadOnly(!screenLock_ON);
        this.rdo_FailedLoginScreenLock_ON.setChecked(screenLock_ON);
        this.rdo_FailedLoginScreenLock_OFF.setChecked(!screenLock_ON);

        //ユーザロック設定
        this.rdo_FailedLoginUserLock_ON.setChecked(authenSystem.getFaieldLoginUserLockFlag());
        this.rdo_FailedLoginUserLock_OFF.setChecked(!authenSystem.getFaieldLoginUserLockFlag());

        //端末アクセス制限
        this.rdo_TerminalCheck_ON.setChecked(authenSystem.getTerminalCheckFlag());
        this.rdo_TerminalCheck_OFF.setChecked(!authenSystem.getTerminalCheckFlag());

        //IP制限
        boolean ipRangeCheckFlag = authenSystem.getIpRangeCheckFlag();
        this.rdo_IPRangeCheck_ON.setChecked(ipRangeCheckFlag);
        this.rdo_IPRangeCheck_OFF.setChecked(!ipRangeCheckFlag);

        //IP有効範囲
        //IP有効範囲(最小)
        this.txt_IPRange_Min.setReadOnly(!ipRangeCheckFlag);
        this.txt_IPRange_Min.setText(authenSystem.getIprangeMin());
        //IP有効範囲(最大)
        this.txt_IPRange_Max.setReadOnly(!ipRangeCheckFlag);
        this.txt_IPRange_Max.setText(authenSystem.getIprangeMax());

        //仮パスワード設定
        this.rdo_DummyPassWord_ON.setChecked(authenSystem.getDummyPassFlag());
        this.rdo_DummyPassWord_OFF.setChecked(!authenSystem.getDummyPassFlag());

        //パスワード安全性チェック
        this.rdo_PassWordSaftyCheck_ON.setChecked(authenSystem.getPassSafetyCheckFlag());
        this.rdo_PassWordSaftyCheck_OFF.setChecked(!authenSystem.getPassSafetyCheckFlag());

        //パスワード有効期間チェック
        this.rdo_PasswordExpire_ON.setChecked(authenSystem.getPassExpireFlag());
        this.rdo_PasswordExpire_OFF.setChecked(!authenSystem.getPassExpireFlag());

        //パスワード履歴回数
        this.txt_PasswordLogCheckTime.setInt(authenSystem.getPassLogCheckTime());

        //パスワード最低文字数
        this.txt_PasswordMinLength.setInt(authenSystem.getPassMinLength());

        //認証ミス猶予回数
        int failLoginAttem = authenSystem.getFailLoginAttem();
        if (failLoginAttem != FAILED_ATTEMPTS_STATUS_NOLIMITED)
        {
            //制限有
            this.rdo_FailedLoginAttempts.setChecked(true);
            this.txt_FailedLoginAttempts.setReadOnly(false);
            this.txt_FailedLoginAttempts.setInt(failLoginAttem);
        }
        else
        {
            //無制限
            this.rdo_FailedLogin_Unrestricted.setChecked(true);
            this.txt_FailedLoginAttempts.setReadOnly(true);
            this.txt_FailedLoginAttempts.setText("");
        }
        //活性化
        setFailedLoginAttemptsEnabled(authenSystem.getFaieldLoginUserLockFlag());

        //パスワード更新間隔
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

        //パスワード切れ通知日数
        this.txt_PasswordExpireAlertDays.setInt(authenSystem.getPassExpireAlertDays());

        //活性化
        setPasswordExpireControlEnabled(authenSystem.getPassExpireFlag());

        // Part11ログ出力設定
        this.rdo_Part11Log_ON.setChecked(authenSystem.getPart11Flag());
        this.rdo_Part11Log_OFF.setChecked(!authenSystem.getPart11Flag());

        // 画面アクセスログ出力設定
        this.rdo_AccessLog_ON.setChecked(authenSystem.getAccessLogFlag());
        this.rdo_AccessLog_OFF.setChecked(!authenSystem.getAccessLogFlag());

        // マスタ改廃ログ出力設定
        this.rdo_MasterLog_ON.setChecked(authenSystem.getMasterLogFlag());
        this.rdo_MasterLog_OFF.setChecked(!authenSystem.getMasterLogFlag());

        // 在庫情報ログ出力設定
        this.rdo_StockLog_ON.setChecked(authenSystem.getStockLogFlag());
        this.rdo_StockLog_OFF.setChecked(!authenSystem.getStockLogFlag());

        // 画面操作ログ出力設定
        this.rdo_OperationLog_ON.setChecked(authenSystem.getOperationLogFlag());
        this.rdo_OperationLog_OFF.setChecked(!authenSystem.getOperationLogFlag());

        // ログデータ保持日数
        this.txt_DBLogHoldDays.setInt(authenSystem.getDbLogHoldDays());

        // ログファイル作成単位(日)
        this.txt_CsvLogHoldDays.setInt(authenSystem.getCsvLogHoldDays());

        // ログファイルディスク保持期間(年)
        this.txt_HDLogHoldYears.setInt(authenSystem.getHdLogHoldYears());

        //認証ログ出力設定
        this.rdo_AuthenticationLog_ON.setChecked(authenSystem.getAuthLogFlag());
        this.rdo_AuthenticationLog_OFF.setChecked(!authenSystem.getAuthLogFlag());

        //メンテナンスログ出力設定
        this.rdo_UserMaintenanceLog_ON.setChecked(authenSystem.getUserMaintLogFlag());
        this.rdo_UserMaintenanceLog_OFF.setChecked(!authenSystem.getUserMaintLogFlag());

        // ログ関連コントロール活性化
        setLogControlEnabled(authenSystem.getPart11Flag());

        //同一ユーザ作成禁止期間設定
        int sameUserNgYears = authenSystem.getSameUserCreateBlockPeriod();
        if (sameUserNgYears != SAMEUSER_BLOCK_PERIOD_STATUS_NOLIMITED)
        {
            //制限有
            this.rdo_SameUserBlock_restricted.setChecked(true);
            this.txt_SameUserCreateBlockPeriod.setReadOnly(false);
            this.txt_SameUserCreateBlockPeriod.setInt(sameUserNgYears);
        }
        else
        {
            //無制限
            this.rdo_SameUserBlock_Unrestricted.setChecked(true);
            this.txt_SameUserCreateBlockPeriod.setReadOnly(true);
            this.txt_SameUserCreateBlockPeriod.setText("");
        }

        //メインメニューの種類
        this.rdo_LargeIcon.setChecked(authenSystem.getMainMenuShowType() == EmConstants.MAINMENUSHOWTYPE_B);
        this.rdo_SmallIcon.setChecked(authenSystem.getMainMenuShowType() == EmConstants.MAINMENUSHOWTYPE_A);

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
     * パスワード有効期間チェックにより入力フィールドの属性を変更します。
     * @param enabled パスワード有効期間設定
     */
    private void setPasswordExpireControlEnabled(boolean enabled)
    {
        //パスワード更新間隔を活性化
        this.rdo_PwdChangeInterval.setEnabled(enabled);
        this.txt_PwdChangeInterval.setReadOnly(!enabled || !this.rdo_PwdChangeInterval.getChecked());
        //this.txt_PwdChangeInterval.setVisible( enabled );
        this.rdo_PwdChange_Unrestricted.setEnabled(enabled);

        //パスワード切れ通知日数を活性化
        this.txt_PasswordExpireAlertDays.setReadOnly(!enabled);
        //this.txt_PasswordExpireAlertDays.setVisible( enabled );
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
        //this.txt_FailedLoginAttempts.setVisible( enabled );
    }

    /**
     * Part11ログ出力設定より入力フィールドの属性を変更します。
     * @param part11Enabled Part11ログ出力設定
     */
    private void setLogControlEnabled(boolean part11Enabled)
            throws SQLException
    {
        // 画面アクセスログ出力設定
        this.rdo_AccessLog_ON.setEnabled(part11Enabled);
        this.rdo_AccessLog_OFF.setEnabled(part11Enabled);
        //  マスタ改廃ログ出力設定
        this.rdo_MasterLog_ON.setEnabled(part11Enabled);
        this.rdo_MasterLog_OFF.setEnabled(part11Enabled);
        // 在庫情報ログ出力設定
        this.rdo_StockLog_ON.setEnabled(part11Enabled);
        this.rdo_StockLog_OFF.setEnabled(part11Enabled);
        // 画面操作ログ出力設定
        this.rdo_OperationLog_ON.setEnabled(part11Enabled);
        this.rdo_OperationLog_OFF.setEnabled(part11Enabled);
        // ログデータ保持日数
        this.txt_DBLogHoldDays.setReadOnly(!part11Enabled);
        // ログファイル作成単位(日)
        this.txt_CsvLogHoldDays.setReadOnly(!part11Enabled);
        // ログファイルディスク保持期間(年)
        this.txt_HDLogHoldYears.setReadOnly(!part11Enabled);
        // 認証ログ出力設定
        this.rdo_AuthenticationLog_ON.setEnabled(!part11Enabled);
        this.rdo_AuthenticationLog_OFF.setEnabled(!part11Enabled);
        // メンテナンスログ出力設定
        this.rdo_UserMaintenanceLog_ON.setEnabled(!part11Enabled);
        this.rdo_UserMaintenanceLog_OFF.setEnabled(!part11Enabled);

        Connection conn = null;
        AuthenticationSystem authenSystem = null;
        try
        {
            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            AuthenticationHandler authenHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
            //DBより取得
            authenSystem = authenHandler.findAuthenticationSystem();

        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }

        // part11 flag is off , set all part11 log flags to false, disable hold days to empty 
        // and set authentication log and maintanance log values  
        if (!part11Enabled)
        {
            // ログ出力設定をOFFにする
            this.rdo_AccessLog_OFF.setChecked(true);
            this.rdo_MasterLog_OFF.setChecked(true);
            this.rdo_StockLog_OFF.setChecked(true);
            this.rdo_OperationLog_OFF.setChecked(true);

            // ログデータに関する日付をクリアする
            this.txt_DBLogHoldDays.setText("");
            this.txt_CsvLogHoldDays.setText("");
            this.txt_HDLogHoldYears.setText("");

            this.rdo_AuthenticationLog_ON.setChecked(authenSystem.getAuthLogFlag());
            this.rdo_AuthenticationLog_OFF.setChecked(!authenSystem.getAuthLogFlag());

            //メンテナンスログ出力設定
            this.rdo_UserMaintenanceLog_ON.setChecked(authenSystem.getUserMaintLogFlag());
            this.rdo_UserMaintenanceLog_OFF.setChecked(!authenSystem.getUserMaintLogFlag());

        }
        else
        {

            // part 11 flag is true, set all part11 log related flags to actual values(DB)
            // set false to Authentication Log and Mainteanace Log 
            // ログデータに関する日付をクリアする
            String db = authenSystem.getDbLogHoldDays() == 0 ? ""
                                                            : String.valueOf(authenSystem.getDbLogHoldDays());
            this.txt_DBLogHoldDays.setText(db);
            String csv = authenSystem.getCsvLogHoldDays() == 0 ? ""
                                                              : String.valueOf(authenSystem.getCsvLogHoldDays());
            this.txt_CsvLogHoldDays.setText(csv);
            String hd = authenSystem.getHdLogHoldYears() == 0 ? ""
                                                             : String.valueOf(authenSystem.getHdLogHoldYears());
            this.txt_HDLogHoldYears.setText(hd);

            //ログ出力設定をOFFにする
            this.rdo_AccessLog_ON.setChecked(authenSystem.getAccessLogFlag());
            this.rdo_AccessLog_OFF.setChecked(!authenSystem.getAccessLogFlag());

            this.rdo_MasterLog_ON.setChecked(authenSystem.getMasterLogFlag());
            this.rdo_MasterLog_OFF.setChecked(!authenSystem.getMasterLogFlag());

            this.rdo_StockLog_ON.setChecked(authenSystem.getStockLogFlag());
            this.rdo_StockLog_OFF.setChecked(!authenSystem.getStockLogFlag());

            this.rdo_OperationLog_ON.setChecked(authenSystem.getOperationLogFlag());
            this.rdo_OperationLog_OFF.setChecked(!authenSystem.getOperationLogFlag());


            this.rdo_AuthenticationLog_ON.setChecked(false);
            this.rdo_AuthenticationLog_OFF.setChecked(true);
            //メンテナンスログ出力設定
            this.rdo_UserMaintenanceLog_ON.setChecked(false);
            this.rdo_UserMaintenanceLog_OFF.setChecked(true);
        }


    }

    /**
     * 同一ユーザ作成期間チェックにより入力フィールドの属性を変更します。
     * @param enabled 同一ユーザ作成期間設定
     */
    private void settxt_SameUserCreateBlockPeriodControlEnabled(boolean enabled)
    {
        //パスワード更新間隔を活性化
        if (enabled == true)
        {
            this.txt_SameUserCreateBlockPeriod.setReadOnly(false);
        }
        else
        {
            this.txt_SameUserCreateBlockPeriod.setReadOnly(true);
        }
    }
    
// 2008/12/25 K.Matsuda Start 赤*を非表示
    /**
     * 必須項目のマーク(*)を非表示にします
     */
    private void setVisibleRequireMarks(boolean visible)
    {
        // *を非表示にする
        lbl_RequireLoginMax.setVisible(visible);
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
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_IPRangeCheck_ON_Click(ActionEvent e)
            throws Exception
    {
        this.txt_IPRange_Min.setReadOnly(false);
        this.txt_IPRange_Max.setReadOnly(false);
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_IPRangeCheck_OFF_Click(ActionEvent e)
            throws Exception
    {
        this.txt_IPRange_Min.setReadOnly(true);
        this.txt_IPRange_Max.setReadOnly(true);
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_PasswordExpire_ON_Click(ActionEvent e)
            throws Exception
    {
        setPasswordExpireControlEnabled(true);
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_PasswordExpire_OFF_Click(ActionEvent e)
            throws Exception
    {
        setPasswordExpireControlEnabled(false);
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
    public void rdo_FailedLoginScreenLock_ON_Click(ActionEvent e)
            throws Exception
    {
        this.txt_FailedLoginScreenLockCount.setReadOnly(false);
        //		this.txt_FailedLoginScreenLockTime.setReadOnly( false );
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_FailedLoginScreenLock_OFF_Click(ActionEvent e)
            throws Exception
    {
        this.txt_FailedLoginScreenLockCount.setReadOnly(true);
        //		this.txt_FailedLoginScreenLockTime.setReadOnly( true );
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

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void rdo_Part11Log_ON_Click(ActionEvent e)
            throws Exception
    {
        setLogControlEnabled(true);
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void rdo_Part11Log_OFF_Click(ActionEvent e)
            throws Exception
    {
        setLogControlEnabled(false);
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_SameUserBlock_restricted_Click(ActionEvent e)
            throws Exception
    {
        settxt_SameUserCreateBlockPeriodControlEnabled(true);
    }

    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void rdo_SameUserBlock_Unrestricted_Click(ActionEvent e)
            throws Exception
    {
        settxt_SameUserCreateBlockPeriodControlEnabled(false);
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_CommitSync_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //フォーカスを移動
            setFocus(this.btn_Help);

            // コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            AuthenticationHandler authenHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);

            //入力チェック
            //同時ログイン数
            this.txt_LoginMax.validate(true);
            if (txt_LoginMax.getInt() < 1 && txt_LoginMax.getInt() != SAMEUSERLOGINMAX_STATUS_NOLIMITED)
            {
                //同時ログイン数には1以上の数値を入力してください（ただし、-1は除きます）。
                message.setMsgResourceKey("6403040");
                return;
            }
            // session timeout time validation
            this.txt_SessionTimeoutTime.validate(true);
            if (txt_SessionTimeoutTime.getInt() < SESSIONTIMEOUT_MIN
                    || txt_SessionTimeoutTime.getInt() > SESSIONTIMEOUT_MAX)
            {
                //10～86400の間の値を入力してください。
                message.setMsgResourceKey("6403070");
                return;
            }
            if (this.rdo_FailedLoginScreenLock_ON.getChecked())
            {
                //画面ロック時間
                this.txt_FailedLoginScreenLockTime.validate(true);
                if (this.txt_FailedLoginScreenLockTime.getInt() < 0)
                {
                    //{0}の値は、{1}以上の数値で入力してください。
                    message.setMsgResourceKey("6403075");
                    ArrayList params = new ArrayList();
                    params.add(DispResources.getText(this.lbl_FailedLoginScreenLockTime.getResourceKey()));
                    params.add("0");
                    message.setMsgParameter(params);
                    return;
                }

                //画面ロックカウント
                this.txt_FailedLoginScreenLockCount.validate(true);
                if (this.txt_FailedLoginScreenLockCount.getInt() < 1)
                {
                    //{0}の値は、{1}以上の数値で入力してください。
                    message.setMsgResourceKey("6403075");
                    ArrayList params = new ArrayList();
                    params.add(DispResources.getText(this.lbl_FailedLoginScreenLockCount.getResourceKey()));
                    params.add("1");
                    message.setMsgParameter(params);
                    return;
                }
            }
            //IP有効範囲
            if (this.rdo_IPRangeCheck_ON.getChecked())
            {
                //IP有効範囲(最小)
                this.txt_IPRange_Min.validate(true);
                //IPチェック
                if (!this.ipCheck(this.txt_IPRange_Min.getText()))
                {
                    message.setMsgResourceKey("6403072");
                    ArrayList params = new ArrayList();
                    params.add(DispResources.getText(this.lbl_IPRange_Min.getResourceKey()));
                    message.setMsgParameter(params);
                    return;
                }
                //IP有効範囲(最大)
                this.txt_IPRange_Max.validate(true);
                //IPチェック
                if (!this.ipCheck(this.txt_IPRange_Max.getText()))
                {
                    message.setMsgResourceKey("6403072");
                    ArrayList params = new ArrayList();
                    params.add(DispResources.getText(this.lbl_IPRange_Max.getResourceKey()));
                    message.setMsgParameter(params);
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
                    return;
                }
            }
            //パスワード履歴回数
            this.txt_PasswordLogCheckTime.validate(true);
            if (this.txt_PasswordLogCheckTime.getInt() < 0 || 10 < this.txt_PasswordLogCheckTime.getInt())
            {
                //{0}の値は、{1}以上の数値で入力してください。
                message.setMsgResourceKey("6403074");
                ArrayList params = new ArrayList();
                params.add(DispResources.getText(this.lbl_PasswordLogCheckTime.getResourceKey()));
                params.add("0");
                params.add("10");
                message.setMsgParameter(params);
                return;
            }
            //パスワード最低文字数
            this.txt_PasswordMinLength.validate(true);
            if (this.txt_PasswordMinLength.getInt() < 1 || 20 < this.txt_PasswordMinLength.getInt())
            {
                //{0}の値は、{1}～{2}の間の数値で入力してください。
                message.setMsgResourceKey("6403074");
                ArrayList params = new ArrayList();
                params.add(DispResources.getText(this.lbl_PasswordMinLength.getResourceKey()));
                params.add("1");
                params.add("20");
                message.setMsgParameter(params);
                return;
            }
            if (this.rdo_FailedLoginUserLock_ON.getChecked())
            {
                //認証ミス猶予回数
                if (this.rdo_FailedLoginAttempts.getChecked())
                {
                    this.txt_FailedLoginAttempts.validate(true);
                    if (this.txt_FailedLoginAttempts.getInt() < 0)
                    {
                        //{0}の値は、{1}以上の数値で入力してください。
                        message.setMsgResourceKey("6403075");
                        ArrayList params = new ArrayList();
                        params.add(DispResources.getText(this.lbl_FailedLoginAttempts.getResourceKey()));
                        params.add("0");
                        message.setMsgParameter(params);
                        return;
                    }
                }
            }
            if (this.rdo_PasswordExpire_ON.getChecked())
            {
                //パスワード更新間隔
                if (this.rdo_PwdChangeInterval.getChecked())
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
                        return;
                    }
                }
                //パスワード切れ通知日数
                this.txt_PasswordExpireAlertDays.validate(true);
                if (this.txt_PasswordExpireAlertDays.getInt() < 0)
                {
                    //{0}の値は、{1}以上の数値で入力してください。
                    message.setMsgResourceKey("6403075");
                    ArrayList params = new ArrayList();
                    params.add(DispResources.getText(this.lbl_PasswordExpireAlertDays.getResourceKey()));
                    params.add("0");
                    message.setMsgParameter(params);
                    return;
                }
            }

            // Part11ログがONの場合
            if (this.rdo_Part11Log_ON.getChecked())
            {
                // 画面アクセスログ、マスタ改廃ログ、在庫ログ、操作ログの全てがOFFの場合はエラー
                if (this.rdo_AccessLog_OFF.getChecked() && this.rdo_MasterLog_OFF.getChecked()
                        && this.rdo_StockLog_OFF.getChecked() && this.rdo_OperationLog_OFF.getChecked())
                {
                    // Part11ログを出力する場合は画面アクセス、マスタ改廃、在庫情報、画面操作のいずれかのログを出力して下さい。
                    message.setMsgResourceKey("6403078");
                    return;
                }

                // ログデータ保持日数
                this.txt_DBLogHoldDays.validate(true);
                if (this.txt_DBLogHoldDays.getInt() < 1)
                {
                    // {0}の値は、{1}以上の数値で入力してください。
                    message.setMsgResourceKey("6403075");
                    ArrayList params = new ArrayList();
                    params.add(DispResources.getText(this.lbl_DBLogHoldDays.getResourceKey()));
                    params.add("1");
                    message.setMsgParameter(params);
                    return;
                }
                // ログファイル作成単位(日)
                this.txt_CsvLogHoldDays.validate(true);
                if (this.txt_CsvLogHoldDays.getInt() < 1)
                {
                    // {0}の値は、{1}以上の数値で入力してください。
                    message.setMsgResourceKey("6403075");
                    ArrayList params = new ArrayList();
                    params.add(DispResources.getText(this.lbl_CsvLogHoldDays.getResourceKey()));
                    params.add("1");
                    message.setMsgParameter(params);
                    return;
                }
                // ログファイルディスク保持期間(年)
                this.txt_HDLogHoldYears.validate(true);
                if (this.txt_HDLogHoldYears.getInt() < 1)
                {
                    // {0}の値は、{1}以上の数値で入力してください。
                    message.setMsgResourceKey("6403075");
                    ArrayList params = new ArrayList();
                    params.add(DispResources.getText(this.lbl_HDLogHoldYears.getResourceKey()));
                    params.add("1");
                    message.setMsgParameter(params);
                    return;
                }
            }
            //同一ユーザ作成禁止期間(日)
            if (rdo_SameUserBlock_restricted.getChecked())
            {
                this.txt_SameUserCreateBlockPeriod.validate(true);
                if (this.txt_SameUserCreateBlockPeriod.getInt() < 0)
                {
                    //{0}の値は、{1}以上の数値で入力してください。
                    message.setMsgResourceKey("6403075");
                    ArrayList params = new ArrayList();
                    params.add(DispResources.getText(this.lbl_SameUserCreateBlockPeriod.getResourceKey()));
                    params.add("0");
                    message.setMsgParameter(params);
                    return;
                }
            }

            //DBより取得
            AuthenticationSystem authenSystem = authenHandler.findAuthenticationSystem();

            //更新処理
            //同時ログイン数
            authenSystem.setLoginMax(this.txt_LoginMax.getInt());

            //自動ログイン設定
            authenSystem.setAutoLoginFlag(this.rdo_AutoLogin_ON.getChecked());

            //同一ユーザログイン
            authenSystem.setSameLoginUserFlag(this.rdo_SameLoginUser_ON.getChecked());

            //端末ユーザ設定
            authenSystem.setTerminalUserCheckFlag(this.rdo_TerminalUserCheck_ON.getChecked());

            //端末切替設定
            authenSystem.setTerminalChangeFlag(this.rdo_TerminalChange_ON.getChecked());

            //画面ログインチェック
            authenSystem.setScreenLoginCheckFlag(this.rdo_ScreenLogInCheck_ON.getChecked());

            //Session Timeout Time
            authenSystem.setSessionTimeoutTime(this.txt_SessionTimeoutTime.getInt());

            //画面ロック時間
            authenSystem.setFaieldLoginScreenLockTime(this.txt_FailedLoginScreenLockTime.getInt());

            if (this.rdo_FailedLoginScreenLock_ON.getChecked())
            {
                //              //画面ロック時間
                //              authenSystem.setFaieldLoginScreenLockTime( this.txt_FailedLoginScreenLockTime.getInt() );
                //画面ロックカウント
                authenSystem.setFailedLoginscreenLockCount(this.txt_FailedLoginScreenLockCount.getInt());
            }
            else
            {
                //画面ロックカウント
                authenSystem.setFailedLoginscreenLockCount(0);
            }

            //ユーザロック設定
            authenSystem.setFaieldLoginUserLockFlag(this.rdo_FailedLoginUserLock_ON.getChecked());

            //端末アクセス制限
            authenSystem.setTerminalCheckFlag(this.rdo_TerminalCheck_ON.getChecked());

            //IP制限
            authenSystem.setIpRangeCheckFlag(this.rdo_IPRangeCheck_ON.getChecked());
            //IP有効範囲
            if (this.rdo_IPRangeCheck_ON.getChecked())
            {
                //IP有効範囲(最小)
                authenSystem.setIprangeMin(this.txt_IPRange_Min.getText());
                //IP有効範囲(最大)
                authenSystem.setIprangeMax(this.txt_IPRange_Max.getText());
            }
            else
            {
                //IP有効範囲(最小)
                authenSystem.setIprangeMin(null);
                //IP有効範囲(最大)
                authenSystem.setIprangeMax(null);
            }

            //仮パスワード設定
            authenSystem.setDummyPassFlag(this.rdo_DummyPassWord_ON.getChecked());

            //パスワード安全性チェック
            authenSystem.setPassSafetyCheckFlag(this.rdo_PassWordSaftyCheck_ON.getChecked());

            //パスワード有効期間チェック
            authenSystem.setPassExpireFlag(this.rdo_PasswordExpire_ON.getChecked());

            //パスワード履歴回数
            authenSystem.setPassLogCheckTime(this.txt_PasswordLogCheckTime.getInt());

            //パスワード最低文字列
            authenSystem.setPassMinLength(this.txt_PasswordMinLength.getInt());

            if (this.rdo_FailedLoginUserLock_ON.getChecked())
            {
                //認証ミス猶予回数
                if (this.rdo_FailedLoginAttempts.getChecked())
                {
                    authenSystem.setFailLoginAttem(this.txt_FailedLoginAttempts.getInt());
                }
                else
                {
                    authenSystem.setFailLoginAttem(FAILED_ATTEMPTS_STATUS_NOLIMITED);
                }
            }

            if (this.rdo_PasswordExpire_ON.getChecked())
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

                //パスワード切れ通知日数
                authenSystem.setPassExpireAlertDays(this.txt_PasswordExpireAlertDays.getInt());
            }

            // Part11ログ出力設定
            authenSystem.setPart11Flag(this.rdo_Part11Log_ON.getChecked());

            // 画面アクセスログ出力設定
            authenSystem.setAccessLogFlag(this.rdo_AccessLog_ON.getChecked());

            // マスタ改廃ログ出力設定
            authenSystem.setMasterLogFlag(this.rdo_MasterLog_ON.getChecked());

            // 在庫情報ログ出力設定
            authenSystem.setStockLogFlag(this.rdo_StockLog_ON.getChecked());

            // 画面操作ログ出力設定
            authenSystem.setOperationLogFlag(this.rdo_OperationLog_ON.getChecked());

            // ログデータ保持日数
            authenSystem.setDbLogHoldDays(this.txt_DBLogHoldDays.getInt());

            // ログファイル作成単位(日)
            authenSystem.setCsvLogHoldDays(this.txt_CsvLogHoldDays.getInt());

            // ログファイルディスク保持期間(年)
            authenSystem.setHdLogHoldYears(this.txt_HDLogHoldYears.getInt());

            //認証ログ出力画面
            authenSystem.setAuthLogFlag(this.rdo_AuthenticationLog_ON.getChecked());

            //メンテナンスログ出力設定
            authenSystem.setUserMaintLogFlag(this.rdo_UserMaintenanceLog_ON.getChecked());

            //同一ユーザ作成禁止期間(日)
            if (this.rdo_SameUserBlock_restricted.getChecked())
            {
                authenSystem.setSameUserCreateBlockPeriod(this.txt_SameUserCreateBlockPeriod.getInt());
            }
            else
            {
                authenSystem.setSameUserCreateBlockPeriod(EmConstants.SAMEUSER_BLOCK_PERIOD_STATUS_NOLIMITED);
            }

            //メインメニューの種類
            if (this.rdo_LargeIcon.getChecked())
            {
                authenSystem.setMainMenuShowType(EmConstants.MAINMENUSHOWTYPE_B);
            }
            else
            {
                authenSystem.setMainMenuShowType(EmConstants.MAINMENUSHOWTYPE_A);
            }

            // 登録したユーザ情報を設定
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


            list.add(txt_LoginMax.getText());
            list.add(rdo_SameLoginUser_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                      : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(rdo_AutoLogin_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                  : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(rdo_ScreenLogInCheck_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                         : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(txt_SessionTimeoutTime.getText());

            list.add(rdo_TerminalChange_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                       : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(rdo_TerminalUserCheck_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                          : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(rdo_TerminalCheck_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                      : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(rdo_IPRangeCheck_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                     : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(txt_IPRange_Min.getText());
            list.add(txt_IPRange_Max.getText());

            list.add(rdo_FailedLoginScreenLock_ON.getChecked() ? txt_FailedLoginScreenLockCount.getText()
                                                              : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(txt_FailedLoginScreenLockTime.getText());
            list.add(rdo_FailedLoginUserLock_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                            : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(rdo_FailedLoginAttempts.getChecked() ? txt_FailedLoginAttempts.getText()
                                                         : String.valueOf(EmConstants.FAILED_ATTEMPTS_STATUS_NOLIMITED));

            list.add(rdo_DummyPassWord_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                      : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(txt_PasswordLogCheckTime.getText());
            list.add(txt_PasswordMinLength.getText());
            list.add(rdo_PassWordSaftyCheck_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                           : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(rdo_PasswordExpire_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                       : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(rdo_PwdChangeInterval.getChecked() ? txt_PwdChangeInterval.getText()
                                                       : String.valueOf(EmConstants.PWDCHANGEINTERVAL_STATUS_NOLIMITED));
            list.add(txt_PasswordExpireAlertDays.getText());

            list.add(rdo_Part11Log_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                  : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(rdo_AccessLog_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                  : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(rdo_MasterLog_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                  : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(rdo_StockLog_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                 : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(rdo_OperationLog_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                     : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(txt_DBLogHoldDays.getText());
            list.add(txt_CsvLogHoldDays.getText());
            list.add(txt_HDLogHoldYears.getText());
            list.add(rdo_AuthenticationLog_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                          : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(rdo_UserMaintenanceLog_ON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                           : String.valueOf(EmConstants.DB_FLAG_FALSE));
            list.add(rdo_SameUserBlock_restricted.getChecked() ? txt_SameUserCreateBlockPeriod.getText()
                                                              : String.valueOf(EmConstants.SAMEUSER_BLOCK_PERIOD_STATUS_NOLIMITED));
            list.add(rdo_LargeIcon.getChecked() ? String.valueOf(EmConstants.MAINMENUSHOWTYPE_B)
                                               : String.valueOf(EmConstants.MAINMENUSHOWTYPE_A));

            //オペレーションログ出力
            P11LogWriter logWriter = new P11LogWriter(conn);
            logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_SETTING, list);


            //設定
            conn.commit();

            //6401008   更新しました。
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
            EmConnectionHandler.closeConnection(conn);
        }
    }


}
//end of class

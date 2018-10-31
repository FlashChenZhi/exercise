// $Id: UserBusiness.java 5376 2009-11-04 01:43:18Z yamashita $

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
import jp.co.daifuku.emanager.database.entity.Role;
import jp.co.daifuku.emanager.database.entity.User;
import jp.co.daifuku.emanager.database.entity.UserMasterLog;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.EmTableColumns;
import jp.co.daifuku.emanager.database.handler.RoleHandler;
import jp.co.daifuku.emanager.database.handler.UserHandler;
import jp.co.daifuku.emanager.display.web.setting.user.listbox.RoleListBusiness;
import jp.co.daifuku.emanager.display.web.setting.user.listbox.UserListBusiness;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.emanager.util.PasswordChecker;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;

/** <jp>
 * ユーザ設定の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
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
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5376 $, $Date: 2009-11-04 10:43:18 +0900 (水, 04 11 2009) $
 * @author  $Author: yamashita $
 </en> */
public class UserBusiness
        extends jp.co.daifuku.emanager.display.web.setting.user.User
        implements EmConstants
{
    // Class fields --------------------------------------------------
    private final String OLD_PASSWORD = "OLD_PASSWORD";

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

        if (VSKEY_INSERT.equals(processkey))
        {
            // 登録ボタン押下時確認ダイアログ "登録しますか？"
            btn_Commit.setBeforeConfirm("MSG-T0056");
        }
        else if (VSKEY_MODIFY.equals(processkey))
        {
            // 修正ボタン押下時確認ダイアログ "修正しますか？"
            btn_Commit.setBeforeConfirm("MSG-T0057");
        }
        else if (VSKEY_DELETE.equals(processkey))
        {
            // 削除ボタン押下時確認ダイアログ "削除しますか？"
            btn_Commit.setBeforeConfirm("MSG-T0058");
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
        
        //各項目を読み取り専用に設定
        //パスワード更新間隔
        this.txt_Password.setReadOnly(true);
        this.txt_Password_Re.setReadOnly(true);

        this.rdo_PwdChangeInterval.setEnabled(false);
        this.txt_PwdChangeInterval.setReadOnly(true);
        this.rdo_PwdChangeUnrestricted.setEnabled(false);
        this.rdo_PwdChangeRollSuccession.setEnabled(false);
        //ロールID
        this.txt_RoleId.setReadOnly(true);
        this.btn_P_Search2.setEnabled(false);
        //同一ユーザログイン可能数
        this.rdo_SameUserLoginMax.setEnabled(false);
        this.txt_SameUserLoginMax.setReadOnly(true);
        this.rdo_SameUserLoginUnrestricted.setEnabled(false);
// 2008/12/11 K.Matsuda Start ユーザ状態に無効を追加と名称変更
        //ユーザ状態
        this.rdo_UserStatus_Active.setEnabled(false);
        this.rdo_UserStatus_Disable.setEnabled(false);
        this.rdo_UserStatus_Locked.setEnabled(false);
// 2008/12/11 K.Matsuda End
        //認証ミス猶予回数
        this.rdo_FailedLoginRestricted.setEnabled(false);
        this.txt_FailedLoginAttempts.setReadOnly(true);
        this.rdo_FailedLoginUnrestricted.setEnabled(false);
        this.rdo_FailedLoginRollSuccession.setEnabled(false);
        //ユーザ名
        this.txt_UserName.setReadOnly(true);
        //所属
        this.txt_Belonging.setReadOnly(true);
        //備考
        this.txt_Note.setReadOnly(true);

        // 設定ボタン
        this.btn_Commit.setEnabled(false);
        // クリアボタン
        this.btn_Clear.setEnabled(false);
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

        //ロールIDを取得
        String rolekey = param.getParameter(RoleListBusiness.ROLEID_KEY);
        //空ではないときに値をセットする
        if (!Validator.isEmptyCheck(rolekey))
        {
            txt_RoleId.setText(rolekey);
            setFocus(txt_RoleId);
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    /**
     * ボタンの使用可、不可を切替えます。
     */
    protected void changebtn()
    {
        // 登録ボタン
        btn_Submit.setEnabled(false);
        // 修正ボタン
        btn_Modify.setEnabled(false);
        // 削除ボタン
        btn_Delete.setEnabled(false);

        // 設定ボタン
        btn_Commit.setEnabled(true);
        // クリアボタン
        btn_Clear.setEnabled(true);
    }

    // Private methods -----------------------------------------------
    /** 
     * 修正、登録ボタン押下時に、DBの項目を各コントロールへマッピングします。
     * @param user User エンティティ
     * @param userAtt UserAttribute エンティティ
     * @param authSystem AuthenticationSystem エンティティ
     * @throws Exception 
     */
    private void mapping(User user, AuthenticationSystem authSystem)
            throws Exception
    {
        //登録済みのユーザIDがある場合
        if (user != null)
        {
            //LoginUser表示
            //パスワード
            this.txt_Password.setText(user.getPassword());
            this.txt_Password_Re.setText(user.getPassword());

            //パスワード更新間隔
            switch (user.getPwdChangeInterval())
            {
                case PWDCHANGEINTERVAL_STATUS_NOLIMITED:
                    //無制限
                    this.txt_PwdChangeInterval.setText("");
                    this.rdo_PwdChangeUnrestricted.setChecked(true);
                    break;
                case PWDCHANGEINTERVAL_STATUS_EXTEND:
                    //ロール設定を使用
                    this.txt_PwdChangeInterval.setText("");
                    this.rdo_PwdChangeRollSuccession.setChecked(true);
                    break;
                default:
                    //制限
                    this.txt_PwdChangeInterval.setInt(user.getPwdChangeInterval());
                    this.rdo_PwdChangeInterval.setChecked(true);

                    break;
            }

            //パスワード有効期限
            //NULLか判定
            if (user.getPwdExpire() != null)
            {
                //パスワード有効期限をテキストボックスにセット
                this.txt_PwdExpires.setDate(user.getPwdExpire());
            }

            //パスワード更新間隔をViewStateに保存
            this.getViewState().setString("PwdChangeInterval", txt_PwdChangeInterval.getText());

            //ロールID
            this.txt_RoleId.setText(user.getRoleID());

            //同一ユーザログイン可能数
            if (user.getSameUserLoginMax() == SAMEUSERLOGINMAX_STATUS_NOLIMITED)
            {
                //無制限
                this.txt_SameUserLoginMax.setText("");
                this.rdo_SameUserLoginUnrestricted.setChecked(true);
            }
            else
            {
                //制限
                this.txt_SameUserLoginMax.setInt(user.getSameUserLoginMax());
                this.rdo_SameUserLoginMax.setChecked(true);
            }

// 2008/12/11 K.Matsuda Start ユーザ状態に無効を追加と名称変更
            //ユーザ状態
            switch(user.getUserStatus())
            {
                case EmConstants.USERSTATUS_ACTIVE:
                    // 有効
                    this.rdo_UserStatus_Active.setChecked(true);
                    break;
                case EmConstants.USERSTATUS_DISABLED:
                    // 無効
                    this.rdo_UserStatus_Disable.setChecked(true);
                    break;
                case EmConstants.USERSTATUS_LOCKED:
                    // ロック中
                    this.rdo_UserStatus_Locked.setChecked(true);
                    break;
                default:
                    break;
            }
// 2008/12/11 K.Matsuda End

            //認証ミス猶予回数
            switch (user.getFailLoginAttem())
            {
                case FAILED_ATTEMPTS_STATUS_NOLIMITED:
                    //無制限
                    this.txt_FailedLoginAttempts.setText("");
                    this.rdo_FailedLoginUnrestricted.setChecked(true);
                    break;
                case FAILED_ATTEMPTS_STATUS_EXTEND:
                    //ロール設定を使用
                    this.txt_FailedLoginAttempts.setText("");
                    this.rdo_FailedLoginRollSuccession.setChecked(true);
                    break;
                default:
                    //制限
                    this.txt_FailedLoginAttempts.setInt(user.getFailLoginAttem());
                    this.rdo_FailedLoginRestricted.setChecked(true);
                    break;
            }

            //ユーザ名
            this.txt_UserName.setText(user.getUserName());
            //所属
            this.txt_Belonging.setText(user.getDepartment());
            //備考
            this.txt_Note.setText(user.getRemarks());
        }
    }

    /** 
     * 修正、登録ボタン押下時に、各コントロールのReadOnlyを設定します。
     * @param process 処理区分
     * @param authSystem AuthenticationSystem エンティティ
     * 
     */
    private void setReadOnlyMenu(String process, AuthenticationSystem authSystem)
    {
        //パスワードテキストボックスを有効にする
        if (VSKEY_INSERT.equals(process))
        {
            this.txt_Password.setReadOnly(false);
            this.txt_Password_Re.setReadOnly(false);
        }
        else
        {
            this.txt_Password.setReadOnly(true);
            this.txt_Password_Re.setReadOnly(true);
        }

        if (authSystem.getPassExpireFlag())
        {
            //パスワード更新間隔テキストボックスを有効にする
            if (this.rdo_PwdChangeInterval.getChecked())
            {
                this.txt_PwdChangeInterval.setReadOnly(false);
            }
            this.txt_PwdChangeInterval.setVisible(true);
            //パスワード更新間隔を設定するラジオボタンを有効にする
            this.rdo_PwdChangeInterval.setEnabled(true);
            this.rdo_PwdChangeUnrestricted.setEnabled(true);
            this.rdo_PwdChangeRollSuccession.setEnabled(true);
        }
        else
        {
            this.rdo_PwdChangeInterval.setEnabled(false);
            this.txt_PwdChangeInterval.setReadOnly(true);
            if (!VSKEY_INSERT.equals(process))
            {
                this.txt_PwdChangeInterval.setVisible(false);
            }
            this.rdo_PwdChangeUnrestricted.setEnabled(false);
            this.rdo_PwdChangeRollSuccession.setEnabled(false);
        }

        //ロールIDテキストボックスを有効にする
        this.txt_RoleId.setReadOnly(false);
        //ロールID検索ボタンを有効にする
        this.btn_P_Search2.setEnabled(true);

        //同一ユーザログイン可能数
        if (authSystem.getSameLoginUserFlag())
        {
            //許可する
            //同一ユーザログイン可能数テキストボックスを有効にする
            if (this.rdo_SameUserLoginMax.getChecked())
            {
                this.txt_SameUserLoginMax.setReadOnly(false);
            }
            //同一ユーザログイン可能数を設定するラジオボタンを有効にする
            this.rdo_SameUserLoginMax.setEnabled(true);
            this.rdo_SameUserLoginUnrestricted.setEnabled(true);
        }

// 2008/12/11 K.Matsuda Start ユーザ状態に無効を追加
        //ロック状態
        this.rdo_UserStatus_Active.setEnabled(true);
        this.rdo_UserStatus_Disable.setEnabled(true);
        
        if (authSystem.getFaieldLoginUserLockFlag())
        {
            //ロックする
            //ロック状態を設定するラジオボタンを有効にする
            this.rdo_UserStatus_Locked.setEnabled(true);
        }
// 2008/12/11 K.Matsuda End

        if (authSystem.getFaieldLoginUserLockFlag())
        {
            //認証ミス猶予回数テキストボックスを有効にする
            if (this.rdo_FailedLoginRestricted.getChecked())
            {
                this.txt_FailedLoginAttempts.setReadOnly(false);
            }
            this.txt_FailedLoginAttempts.setVisible(true);
            //認証ミス猶予回数を設定するラジオボタンを有効にする
            this.rdo_FailedLoginRestricted.setEnabled(true);
            this.rdo_FailedLoginUnrestricted.setEnabled(true);
            this.rdo_FailedLoginRollSuccession.setEnabled(true);
        }
        else
        {
            //認証ミス猶予回数
            this.rdo_FailedLoginRestricted.setEnabled(false);
            this.txt_FailedLoginAttempts.setReadOnly(true);
            if (!VSKEY_INSERT.equals(process))
            {
                this.txt_FailedLoginAttempts.setVisible(false);
            }
            this.rdo_FailedLoginUnrestricted.setEnabled(false);
            this.rdo_FailedLoginRollSuccession.setEnabled(false);
        }

        //ユーザ名テキストボックスを有効にする
        txt_UserName.setReadOnly(false);

        //所属テキストボックスを有効にする
        txt_Belonging.setReadOnly(false);

        //備考テキストボックスを有効にする
        txt_Note.setReadOnly(false);
    }

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
     * ユーザID検索ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_P_Search_Click(ActionEvent e)
            throws Exception
    {
        //ユーザ一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();
        param.setParameter(UserListBusiness.USERID_KEY, txt_UserId.getText());

        //処理中画面->結果画面
        redirect("/emanager/setting/user/listbox/UserList.do", param, "/Progress.do");
    }

    /** 
     * 登録ボタンが押下された時の処理を実装します
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Submit_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //クリア処理
            btn_Clear_Click(e);

            //ユーザIDテキストボックスを入力チェック
            txt_UserId.validate(true);

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);

            //すでに同一のユーザIDが登録済みかを確認
            if (userHandler.findByUserId(this.txt_UserId.getText()) != null)
            {
                //すでに同一のユーザIDが登録されています。
                message.setMsgResourceKey("6403014");
                return;
            }

            // DBより認証システム情報を取得
            AuthenticationSystem authSystem =
                    EmHandlerFactory.getAuthenticationSystemHandler(conn).findAuthenticationSystem();

            // 削除済みユーザの取得
            User deletedUser = userHandler.findByUserIdDeleted(txt_UserId.getText());
            // 同一ユーザ作成禁止が無制限かどうか
            boolean nolimit =
                    deletedUser != null
                            && authSystem.getSameUserCreateBlockPeriod() == EmConstants.SAMEUSER_BLOCK_PERIOD_STATUS_NOLIMITED;
            // 同一ユーザ作成禁止期間中かどうか
            boolean isBlockPeriod =
                    deletedUser != null
                            && userHandler.isSameUserBlockPeriod(this.txt_UserId.getText(),
                                    authSystem.getSameUserCreateBlockPeriod());

            if (nolimit || isBlockPeriod)
            {
                // 同一ユーザ作成禁止期間が無期限の場合
                if (nolimit)
                {
                    // 削除日を取得
                    String deleteDate =
                            Formatter.getDateFormat(deletedUser.getDeleteDate(), Formatter.F_DATE,
                                    getHttpRequest().getLocale());
                    // 過去に削除済みのユーザIDを再登録することはできません。(削除日付 : {0})
                    message.setMsgResourceKey("6403079" + Message.MSG_DELIM + deleteDate);
                }
                // 同一ユーザ作成禁止期間中の場合
                else if (isBlockPeriod)
                {
                    // 再登録可能な日付を求める
                    Calendar deleteCal = Calendar.getInstance();
                    deleteCal.setTime(deletedUser.getDeleteDate());
                    deleteCal.add(Calendar.DATE, authSystem.getSameUserCreateBlockPeriod());
                    String deleteDate =
                            Formatter.getDateFormat(deleteCal.getTime(), Formatter.F_DATE, getHttpRequest().getLocale());
                    // 過去に削除済みのユーザIDです。{0}までは再登録することはできません。
                    message.setMsgResourceKey("6403088" + Message.MSG_DELIM + deleteDate);
                }
                return;
            }

            //ラジオボタンを初期化
            //パスワード更新間隔
            this.rdo_PwdChangeInterval.setChecked(authSystem.getPassExpireFlag());
            this.rdo_PwdChangeRollSuccession.setChecked(!authSystem.getPassExpireFlag());
            //同一ユーザログイン可能数
            this.rdo_SameUserLoginMax.setChecked(true);
// 2008/12/11 K.Matsuda Start 名称変更
            //ユーザ状態
            this.rdo_UserStatus_Active.setChecked(true);
// 2008/12/11 K.Matsuda End 
            //認証ミス猶予回数
            this.rdo_FailedLoginRestricted.setChecked(authSystem.getFaieldLoginUserLockFlag());
            this.rdo_FailedLoginRollSuccession.setChecked(!authSystem.getFaieldLoginUserLockFlag());

            // ボタンの使用不可を設定
            changebtn();

            //メニュータイプが設定者の場合
            setReadOnlyMenu(VSKEY_INSERT, authSystem);

            //ユーザIDをテキストボックスにセット
            txt_R_UserId.setText(txt_UserId.getText());

            //登録中を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_INSERT);

        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }
        setFocus(txt_Password);
    }

    /** 
     * 修正ボタンが押下された時の処理を実装します
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Modify_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //クリア処理
            btn_Clear_Click(e);

            //ユーザIDテキストボックスを入力チェック
            txt_UserId.validate(true);

            // システム定義ユーザチェック
            if (txt_UserId.getText().equals(ANONYMOUS_USER) || txt_UserId.getText().equals(USERMAINTENANCE_USER))
            {
                //指定されたユーザIDはシステム定義のユーザのため修正できません。
                message.setMsgResourceKey("6403020");
                return;
            }

// 2009/07/09 T.Kajiwara Mod DAIFUKU_SV,DAIFUKU_PRIVATEチェック
            if (txt_UserId.getText().equals(DAIFUKU_SV_USER) || txt_UserId.getText().equals(DAIFUKU_PRIVATE_USER))
            {
                //指定されたユーザIDはシステム定義のユーザのため修正できません。
                message.setMsgResourceKey("6403020");
                return;
            }
// 2009/07/09 T.Kajiwara End
            
            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            //DBよりユーザ情報を取得
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);
            User user = userHandler.findByUserId(this.txt_UserId.getText());
            //すでに同一のユーザIDが登録済みかを確認
            if (user == null)
            {
                //指定されたユーザIDは登録されていません。
                message.setMsgResourceKey("6403015");
                return;
            }

            //DBより認証システム情報を取得
            AuthenticationSystem authSystem =
                    EmHandlerFactory.getAuthenticationSystemHandler(conn).findAuthenticationSystem();

            //データベースのデータをセットする
            mapping(user, authSystem);

            //変更前のパスワードを保持
            this.viewState.setString(OLD_PASSWORD, this.txt_Password.getText());

            // ボタンの使用不可を設定
            changebtn();

            //メニュータイプが設定者の場合
            setReadOnlyMenu(VSKEY_MODIFY, authSystem);

            //ユーザIDをテキストボックスにセット
            txt_R_UserId.setText(txt_UserId.getText());

            //修正中を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_MODIFY);
        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * 削除ボタンが押下された時の処理を実装します
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Delete_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //クリア処理
            btn_Clear_Click(e);

            //ユーザIDテキストボックスを入力チェック
            txt_UserId.validate(true);

            //自分自身のユーザかチェック
            //自分自身のユーザ情報を取得
            DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();
            if (userinfo != null)
            {
                UserInfoHandler userhandler = new UserInfoHandler(userinfo);
                if (userhandler != null)
                {
                    //更新ユーザ
                    String myUserId = userhandler.getUserID();
                    if (this.txt_UserId.getText().equals(myUserId))
                    {
                        //自分自身のユーザを削除することはできません。
                        message.setMsgResourceKey("6403042");
                        return;
                    }
                }
            }

            if (this.txt_UserId.getText().equals(ANONYMOUS_USER)
                    || this.txt_UserId.getText().equals(USERMAINTENANCE_USER))
            {
                //指定されたユーザIDはシステム定義のユーザのため削除できません。
                message.setMsgResourceKey("6403019");
                return;
            }

// 2009/07/09 T.Kajiwara Mod DAIFUKU_SV,DAIFUKU_PRIVATEチェック
            if (this.txt_UserId.getText().equals(DAIFUKU_SV_USER)
                    || this.txt_UserId.getText().equals(DAIFUKU_PRIVATE_USER))            		
            {
                //指定されたユーザIDはシステム定義のユーザのため削除できません。
                message.setMsgResourceKey("6403019");
                return;
            }
// 2009/07/09 T.Kajiwara End
            
            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            //DBよりユーザ情報を取得
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);
            User user = userHandler.findByUserId(this.txt_UserId.getText());
            //すでに同一のユーザIDが登録済みかを確認
            if (user == null)
            {
                //指定されたユーザIDは登録されていません。
                message.setMsgResourceKey("6403015");
                return;
            }

            //DBより認証システム情報を取得
            AuthenticationSystem authSystem =
                    EmHandlerFactory.getAuthenticationSystemHandler(conn).findAuthenticationSystem();

            //各コントロールの値に値を設定
            mapping(user, authSystem);

            // ボタンの使用不可を設定
            changebtn();

            //ユーザIDをテキストボックスにセット
            txt_R_UserId.setText(txt_UserId.getText());

            //テキストボックスを非表示
            this.txt_PwdChangeInterval.setVisible(authSystem.getPassExpireFlag());
            this.txt_FailedLoginAttempts.setVisible(authSystem.getFaieldLoginUserLockFlag());

            //削除中を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_DELETE);
        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }
    }


    /** 
     * ロールID検索ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_P_Search2_Click(ActionEvent e)
            throws Exception
    {
        //ロール一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();
        param.setParameter(RoleListBusiness.ROLEID_KEY, txt_RoleId.getText());

        // Do not Include Terminal roles because this business class  handles only User related ta
        String target_Not = String.valueOf(EmConstants.ROLE_TARGET_TERMINAL);
        param.setParameter(RoleListBusiness.TARGET_NOTLIKE, target_Not);
        //処理中画面->結果画面
        redirect("/emanager/setting/user/listbox/RoleList.do", param, "/Progress.do");
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
            //ユーザID入力チェック(必須入力項目)
            txt_R_UserId.validate();

            //処理区分
            String proc = this.getViewState().getString(VSKEY_PROCESS_KEY);

            //ユーザIDをセット
            String userid = txt_R_UserId.getText();
            //パスワードをセット
            String password = txt_Password.getText();
            //パスワード更新間隔をセット
            int pwdchangeinterval_int = 0;
            if (this.rdo_PwdChangeInterval.getChecked())
            {
                pwdchangeinterval_int = this.txt_PwdChangeInterval.getInt();
            }
            else if (this.rdo_PwdChangeUnrestricted.getChecked())
            {
                pwdchangeinterval_int = PWDCHANGEINTERVAL_STATUS_NOLIMITED;
            }
            else
            {
                pwdchangeinterval_int = PWDCHANGEINTERVAL_STATUS_EXTEND;
            }

            //ロールIDをセット
            String roleid = txt_RoleId.getText();
            //同一ユーザログイン可能数
            int sameuserloginmax_int = 0;
            if (this.rdo_SameUserLoginMax.getChecked())
            {
                sameuserloginmax_int = this.txt_SameUserLoginMax.getInt();
            }
            else
            {
                sameuserloginmax_int = SAMEUSERLOGINMAX_STATUS_NOLIMITED;
            }

// 2008/12/11 K.Matsuda Start ユーザ状態に無効を追加
            //ユーザ状態
            int userStatus = 0;
            if(rdo_UserStatus_Active.getChecked()) 
            {
                userStatus = EmConstants.USERSTATUS_ACTIVE;
            }
            else if(rdo_UserStatus_Disable.getChecked()) 
            {
                userStatus = EmConstants.USERSTATUS_DISABLED;
            }
            else if(rdo_UserStatus_Locked.getChecked()) 
            {
                userStatus = EmConstants.USERSTATUS_LOCKED;
            }
// 2008/12/11 K.Matsuda End 
            //認証ミス猶予回数をセット
            int failedloginattempts_int = 0;
            if (this.rdo_FailedLoginRestricted.getChecked())
            {
                failedloginattempts_int = this.txt_FailedLoginAttempts.getInt();
            }
            else if (this.rdo_FailedLoginUnrestricted.getChecked())
            {
                failedloginattempts_int = FAILED_ATTEMPTS_STATUS_NOLIMITED;
            }
            else if (this.rdo_FailedLoginRollSuccession.getChecked())
            {
                failedloginattempts_int = FAILED_ATTEMPTS_STATUS_EXTEND;
            }

            //ユーザ名をセット
            String username = txt_UserName.getText();
            //所属をセット
            String department = txt_Belonging.getText();

            //備考をセット
            String remarks = txt_Note.getText();

            //コネクション取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);
            RoleHandler roleHandler = EmHandlerFactory.getRoleHandler(conn);

            //DBより認証システム情報を取得
            AuthenticationSystem authSystem =
                    EmHandlerFactory.getAuthenticationSystemHandler(conn).findAuthenticationSystem();
            //DBよりロール情報を取得
            Role role = roleHandler.findByRoleId(roleid);

            //完了時のメッセージ
            String comp_msg = "";

            //メンテナンスログに出力するメッセージ
            String mainteLog_msg = "";

            //ユーザ設定禁止期間が過ぎたデータが1件以上あればDBから削除
            if (userHandler.findCountAllDeletedUser() > 0)
            {
                //削除処理				
                //DB更新
                userHandler.deleteUsers(authSystem.getSameUserCreateBlockPeriod());
                conn.commit();
            }
            User user = null;
            User beforeUpdate = null;
            //処理区分が押下されていない場合
            if (proc == null)
            {
                return;
            }
            //登録時
            else if (proc.equals(VSKEY_INSERT))
            {
                //入力チェック
                //パスワード入力チェック(必須入力項目)
                this.txt_Password.validate(this, true);
                //パスワード確認
                if (!this.txt_Password.getText().equals(this.txt_Password_Re.getText()))
                {
                    //パスワードが再確認と一致しません。
                    message.setMsgResourceKey("6403071");
                    setFocus(txt_Password);
                    return;
                }
                //パスワード安全性チェック
                PasswordChecker pwdChecker = new PasswordChecker(conn);
                try
                {
                    pwdChecker.checkNewPassword(userid, this.txt_Password.getText());
                }
                catch (EmAuthenticationException ex)
                {
                    String errMsg = ex.getMessageResourcekey();

                    if (errMsg.indexOf("MSG-T0022") != -1)
                    {
                        errMsg = errMsg.replaceAll("MSG-T0022", "6403055");
                    }
                    else if (errMsg.indexOf("MSG-T0027") != -1)
                    {
                        errMsg = errMsg.replaceAll("MSG-T0027", "6403056");
                    }
                    else if (errMsg.indexOf("MSG-T0029") != -1)
                    {
                        errMsg = errMsg.replaceAll("MSG-T0029", "6403057");
                    }
                    else if (errMsg.indexOf("MSG-T0031") != -1)
                    {
                        errMsg = errMsg.replaceAll("MSG-T0031", "6403058");
                    }
                    else if (errMsg.indexOf("MSG-T0028") != -1)
                    {
                        errMsg = errMsg.replaceAll("MSG-T0028", "6403059");
                    }
                    else if (errMsg.indexOf("MSG-T0030") != -1)
                    {
                        errMsg = errMsg.replaceAll("MSG-T0030", "6403060");
                    }

                    message.setMsgResourceKey(errMsg);
                    setFocus(txt_Password);
                    return;
                }
                //パスワード更新間隔入力チェック(必須入力項目)
                if (authSystem.getPassExpireFlag() && this.rdo_PwdChangeInterval.getChecked())
                {
                    this.txt_PwdChangeInterval.validate(this, true);
                    if (pwdchangeinterval_int < 1)
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
                //ロールID入力チェック(必須入力項目)
                this.txt_RoleId.validate(this, true);
                //存在チェック
                if (role == null)
                {
                    //このロールIDは登録されていません。
                    message.setMsgResourceKey("6403066");
                    setFocus(txt_RoleId);
                    return;
                }
                //同一ユーザログイン可能数入力チェック(必須入力項目)
                if (authSystem.getSameLoginUserFlag() && this.rdo_SameUserLoginMax.getChecked())
                {
                    this.txt_SameUserLoginMax.validate(this, true);
                    if (sameuserloginmax_int < 1)
                    {
                        //{0}の値は、{1}以上の数値で入力してください。
                        message.setMsgResourceKey("6403075");
                        ArrayList params = new ArrayList();
                        params.add(DispResources.getText(this.lbl_SameUserLoginMax.getResourceKey()));
                        params.add("1");
                        message.setMsgParameter(params);
                        setFocus(txt_SameUserLoginMax);
                        return;
                    }
                }
                //認証ミス猶予回数入力チェック(必須入力項目)
                if (authSystem.getFaieldLoginUserLockFlag() && this.rdo_FailedLoginRestricted.getChecked())
                {
                    this.txt_FailedLoginAttempts.validate(this, true);
                    if (failedloginattempts_int < 0)
                    {
                        //{0}の値は、{1}以上の数値で入力してください。
                        message.setMsgResourceKey("6403075");
                        ArrayList params = new ArrayList();
                        params.add(DispResources.getText(this.lbl_SameUserLoginMax.getResourceKey()));
                        params.add("0");
                        message.setMsgParameter(params);
                        setFocus(txt_FailedLoginAttempts);
                        return;
                    }
                }
                txt_UserName.validate(this, false);
                txt_Belonging.validate(this, false);
                txt_Note.validate(this, false);

                //パスワード有効期限
                Date pwdexpires = null;

                int[] pwdchangeinterval_array = new int[3];
                pwdchangeinterval_array[0] = pwdchangeinterval_int;
                pwdchangeinterval_array[1] = role.getPassChangeInterval();
                pwdchangeinterval_array[2] = authSystem.getPassChangeInterval();
                for (int i = 0; i < pwdchangeinterval_array.length; i++)
                {
                    if (pwdchangeinterval_array[i] >= 0)
                    {
                        //パスワード更新間隔あり
                        //現在時刻の取得
                        Calendar cal = Calendar.getInstance();
                        Date dt = cal.getTime();

                        //PCのシステム日付とパスワード更新間隔を加算
                        cal.setTime(dt);
                        cal.add(Calendar.DATE, pwdchangeinterval_array[i]);

                        pwdexpires = cal.getTime();
                        break;
                    }
                    else if (pwdchangeinterval_array[i] == PWDCHANGEINTERVAL_STATUS_NOLIMITED)
                    {
                        //パスワード更新間隔無制限
                        pwdexpires = null;
                        break;
                    }
                    //パスワード更新間隔○○設定を使用
                }

                //登録処理

                //各項目を設定
                //ユーザ情報
                user = new User();
                //ユーザID
                user.setUserID(userid);
                //パスワード
                user.setPassword(password);
                //ユーザ名
                user.setUserName(username);
                //ロール
                user.setRoleID(roleid);
                //仮パスワードフラグ
                user.setDummyPassFlag(authSystem.getDummyPassFlag());
                //ユーザロックフラグ
                user.setUserStatus(userStatus);

                //パスワード有効期限
                user.setPwdExpire(pwdexpires);
                //パスワード更新間隔
                user.setPwdChangeInterval(pwdchangeinterval_int);
                //最終ログイン日時
                user.setLastAccessDate(null);
                //同一ユーザログイン最大数
                if (authSystem.getSameLoginUserFlag())
                {
                    //許可あり
                    user.setSameUserLoginMax(sameuserloginmax_int);
                }
                else
                {
                    //許可なし
                    user.setSameUserLoginMax(1);
                }
                //認証ミス猶予回数
                user.setFailLoginAttem(failedloginattempts_int);

                //認証ミス回数
                user.setFailedCount(0);
                //最終認証ミス日時
                user.setFailedStartDate(null);
                //削除ステータス
                user.setDeleteStatus(0);
                //削除日付
                user.setDeleteDate(null);
                // department
                user.setDepartment(department);
                //remarks
                user.setRemarks(remarks);
                //登録したユーザ情報を設定
                DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();
                if (userinfo != null)
                {
                    UserInfoHandler userhandler = new UserInfoHandler(userinfo);
                    if (userhandler != null)
                    {
                        //更新ユーザ
                        user.setUpdateUser(userhandler.getUserID());
                    }
                }
                //更新端末IP
                user.setUpdateTerminal(this.httpRequest.getRemoteAddr());
                //更新区分
                user.setUpdateKind(1);
                //更新処理名
                user.setUpdateProcess(this.getClass().getName());
                //DB登録する前にもう一度存在チェック
                if (userHandler.findByUserId(userid) != null)
                {
                    //すでに同一のユーザIDが登録されています。
                    message.setMsgResourceKey("6403014");
                    return;
                }

                //DB登録
                userHandler.createUser(user);

                //6401003=登録しました。
                comp_msg = "6401003";

                //ユーザを登録しました。({0})
                String Primaly = EmTableColumns.LOGINUSER_USERID + "=" + userid;
                mainteLog_msg = "6400007" + MSG_DELIM + Primaly;

            }
            //修正時
            else if (proc.equals(VSKEY_MODIFY))
            {
                //入力チェック
                //パスワード更新間隔入力チェック(必須入力項目)
                if (this.rdo_PwdChangeInterval.getChecked())
                {
                    this.txt_PwdChangeInterval.validate(this, true);
                    if (pwdchangeinterval_int < 1)
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
                //ロールID入力チェック(必須入力項目)
                this.txt_RoleId.validate(this, true);
                //存在チェック
                if (role == null)
                {
                    //このロールIDは登録されていません。
                    message.setMsgResourceKey("6403066");
                    setFocus(txt_RoleId);
                    return;
                }
                //同一ユーザログイン可能数入力チェック(必須入力項目)
                if (this.rdo_SameUserLoginMax.getChecked())
                {
                    this.txt_SameUserLoginMax.validate(this, true);
                    if (sameuserloginmax_int < 1)
                    {
                        //{0}の値は、{1}以上の数値で入力してください。
                        message.setMsgResourceKey("6403075");
                        ArrayList params = new ArrayList();
                        params.add(DispResources.getText(this.lbl_SameUserLoginMax.getResourceKey()));
                        params.add("1");
                        message.setMsgParameter(params);
                        setFocus(txt_SameUserLoginMax);
                        return;
                    }
                }
                //認証ミス猶予回数入力チェック(必須入力項目)
                if (authSystem.getFaieldLoginUserLockFlag() && this.rdo_FailedLoginRestricted.getChecked())
                {
                    this.txt_FailedLoginAttempts.validate(this, true);
                    if (failedloginattempts_int < 0)
                    {
                        //{0}の値は、{1}以上の数値で入力してください。
                        message.setMsgResourceKey("6403075");
                        ArrayList params = new ArrayList();
                        params.add(DispResources.getText(this.lbl_SameUserLoginMax.getResourceKey()));
                        params.add("0");
                        message.setMsgParameter(params);
                        setFocus(txt_FailedLoginAttempts);
                        return;
                    }
                }
                txt_UserName.validate(this, false);
                txt_Belonging.validate(this, false);
                txt_Note.validate(this, false);

                //更新するユーザ情報を取得
                String updateUser = "";
                DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();
                if (userinfo != null)
                {
                    UserInfoHandler userhandler = new UserInfoHandler(userinfo);
                    if (userhandler != null)
                    {
                        //更新ユーザ
                        updateUser = userhandler.getUserID();
                    }
                }
                String updateTerminal = this.httpRequest.getRemoteAddr();
                String updateProcess = this.getClass().getName();

                //ユーザ情報
                //各項目を設定
                user = userHandler.findByUserId(userid);
                // make a copy of user information before update.. Used for par11 User Master log
                beforeUpdate = (User)user.clone();

                //DB更新する前にもう一度存在チェック
                if (user == null)
                {
                    //指定されたユーザIDは登録されていません。
                    message.setMsgResourceKey("6403015");
                    return;
                }
                //パスワード
                user.setPassword(password);
                //ユーザ名
                user.setUserName(username);
                //ロール
                user.setRoleID(roleid);
                //ユーザロックフラグ
// 2008/12/11 K.Matsuda Start 有効/無効は切替えられるため、分岐を削除
                //ロックする
                user.setUserStatus(userStatus);
// 2008/12/11 K.Matsuda End
                //パスワード更新間隔
                if (authSystem.getPassExpireFlag())
                {
                    user.setPwdChangeInterval(pwdchangeinterval_int);
                }
                //最終ログイン日時
                user.setLastAccessDate(null);
                //同一ユーザログイン最大数
                if (authSystem.getSameLoginUserFlag())
                {
                    //許可する
                    user.setSameUserLoginMax(sameuserloginmax_int);
                }
                //認証ミス猶予回数
                if (authSystem.getFaieldLoginUserLockFlag())
                {
                    user.setFailLoginAttem(failedloginattempts_int);
                }
                //認証ミス回数
                user.setFailedCount(0);
                //最終認証ミス日時
                user.setFailedStartDate(null);

                // departMent
                user.setDepartment(department);

                // Remarks
                user.setRemarks(remarks);

                //更新ユーザ
                user.setUpdateUser(updateUser);
                //更新端末IP
                user.setUpdateTerminal(updateTerminal);
                //更新区分
                user.setUpdateKind(2);
                //更新処理名
                user.setUpdateProcess(updateProcess);

                //DB更新
                if (userHandler.updateByUserId(user) == 0)
                {
                    //修正対象データがありませんでした。
                    message.setMsgResourceKey("6403002");
                    return;
                }


                //6401004=修正しました。
                comp_msg = "6401004";

                //ユーザを修正しました。({0})
                String Primaly = EmTableColumns.LOGINUSER_USERID + "=" + userid;
                mainteLog_msg = "6400026" + MSG_DELIM + Primaly;

            }
            //削除時
            else if (proc.equals(VSKEY_DELETE))
            {
                //各項目を設定
                user = userHandler.findByUserId(userid);
                //DB更新する前にもう一度存在チェック
                if (user == null)
                {
                    //指定されたユーザIDは登録されていません。
                    message.setMsgResourceKey("6403015");
                    return;
                }

                //DB更新
                if (userHandler.deleteStatusuByUserId(user) == 0)
                {
                    //削除対象データがありませんでした。
                    message.setMsgResourceKey("6403003");
                    return;
                }

                /**
                 //DB更新する前にもう一度存在チェック
                 if( userHandler.findByUserId( userid )==null )
                 {
                 //指定されたユーザIDは登録されていません。
                 message.setMsgResourceKey( "6403015" );
                 return;
                 }
                 //DB削除
                 if( userHandler.deleteUser( userid )==0 )
                 {
                 //削除対象データがありませんでした。
                 message.setMsgResourceKey( "6403003" );
                 return;
                 }*/
                //パスワード履歴を削除
                EmHandlerFactory.getPassWordHistoryHandler(conn).deleteByUserId(userid);
                //端末ユーザマッピング
                EmHandlerFactory.getTerminalUserMapHandler(conn).deleteByUserId(userid);

                //6401005=削除しました。
                comp_msg = "6401005";

                //ユーザを削除しました。({0})
                String Primaly = EmTableColumns.LOGINUSER_USERID + "=" + userid;
                mainteLog_msg = "6400027" + MSG_DELIM + Primaly;


            }
            // create part11 log
            this.parr11UserMasterLog(conn, proc, user, beforeUpdate);
            //設定
            conn.commit();
            //クリア処理
            btn_Clear_Click(e);
            //メッセージをセット
            message.setMsgResourceKey(comp_msg);

            //メンテナンスログ出力
            EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_USER, mainteLog_msg, "");
        }
        catch (SQLException ex)
        {
            EmConnectionHandler.rollback(conn);

            int errorCode = ex.getErrorCode();

            if (errorCode == EmConstants.SQLERRORCODE_ORA0001)
            {
                // <jp>一意制約違反</jp>
                message.setMsgResourceKey("6403068");
            }
            else
            {
                // <jp>その他エラー</jp>
                message.setMsgResourceKey("6403069");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
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
     * This method creates the logs related to Part11 namely, Operation
     * @param e ActionEvent 
     * @throws Exception 
     */
    private void parr11UserMasterLog(Connection conn, String proc, User currentUser, User beforeUpdate)
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

        int operationType = 0;
        if (proc.equals(VSKEY_INSERT))
        {
            operationType = EmConstants.OPELOG_CLASS_REGIST;
            userMasterLog.setUpdateKind(EmConstants.USERMASTER_CLASS_REGIST);
            userMasterLog.setMasterUserId(currentUser.getUserID());
            userMasterLog.setUserNameAfter(currentUser.getUserName());
            userMasterLog.setPasswordAfter(EmConstants.OPELOG_PASSWORD);
            userMasterLog.setPasswordChangeIntervalAfter(currentUser.getPwdChangeInterval());
            userMasterLog.setPwdExpiresAfter(currentUser.getPwdExpire());
// 2008/12/11,2009/01/20 K.Matsuda Start ユーザ状態に無効を追加(intで扱うようにしたので、そのままセット)
            userMasterLog.setUserStatusAfter(currentUser.getUserStatus());
// 2008/12/11,2009/01/20 K.Matsuda End
            userMasterLog.setSameUserLoginMaxAfter(currentUser.getSameUserLoginMax());
            userMasterLog.setFailedLoginAttemptsAfter(currentUser.getFailLoginAttem());
            userMasterLog.setRoleIdAfter(currentUser.getRoleID());
            userMasterLog.setDepartmentAfter(currentUser.getDepartment());
            userMasterLog.setRemarkAfter(currentUser.getRemarks());

        }
        else if (proc.equals(VSKEY_DELETE))
        {
            operationType = EmConstants.OPELOG_CLASS_DELETE;
            userMasterLog.setUpdateKind(EmConstants.USERMASTER_CLASS_DELETE);
            userMasterLog.setMasterUserId(currentUser.getUserID());
            userMasterLog.setUserNameBefore(currentUser.getUserName());
            userMasterLog.setPasswordBefore(EmConstants.OPELOG_PASSWORD);
            userMasterLog.setPasswordChangeIntervalBefore(currentUser.getPwdChangeInterval());
            userMasterLog.setPwdExpiresBefore(currentUser.getPwdExpire());
// 2008/12/11,2009/01/20 K.Matsuda Start ユーザ状態に無効を追加(intで扱うようにしたので、そのままセット)
            userMasterLog.setUserStatusBefore(currentUser.getUserStatus());
// 2008/12/11,2009/01/20 K.Matsuda End
            userMasterLog.setSameUserLoginMaxBefore(currentUser.getSameUserLoginMax());
            userMasterLog.setFailedLoginAttemptsBefore(currentUser.getFailLoginAttem());
            userMasterLog.setRoleIdBefore(currentUser.getRoleID());
            userMasterLog.setDepartmentBefore(currentUser.getDepartment());
            userMasterLog.setRemarkBefore(currentUser.getRemarks());
        }
        else if (proc.equals(VSKEY_MODIFY))
        {
            operationType = EmConstants.OPELOG_CLASS_MODIFY;
            userMasterLog.setUpdateKind(EmConstants.USERMASTER_CLASS_MODIFY);
            userMasterLog.setMasterUserId(currentUser.getUserID());


            userMasterLog.setUserNameBefore(beforeUpdate.getUserName());
            userMasterLog.setPasswordBefore(EmConstants.OPELOG_PASSWORD);
            userMasterLog.setPasswordChangeIntervalBefore(beforeUpdate.getPwdChangeInterval());
            userMasterLog.setPwdExpiresBefore(beforeUpdate.getPwdExpire());
// 2008/12/11,2009/01/20 K.Matsuda Start ユーザ状態に無効を追加(intで扱うようにしたので、そのままセット)
            userMasterLog.setUserStatusBefore(beforeUpdate.getUserStatus());
// 2008/12/11,2009/01/20 K.Matsuda End
            userMasterLog.setSameUserLoginMaxBefore(beforeUpdate.getSameUserLoginMax());
            userMasterLog.setFailedLoginAttemptsBefore(beforeUpdate.getFailLoginAttem());
            userMasterLog.setRoleIdBefore(beforeUpdate.getRoleID());
            userMasterLog.setDepartmentBefore(beforeUpdate.getDepartment());
            userMasterLog.setRemarkBefore(beforeUpdate.getRemarks());

            userMasterLog.setUserNameAfter(currentUser.getUserName());
            userMasterLog.setPasswordAfter(EmConstants.OPELOG_PASSWORD);
            userMasterLog.setPasswordChangeIntervalAfter(currentUser.getPwdChangeInterval());
            userMasterLog.setPwdExpiresAfter(currentUser.getPwdExpire());
// 2008/12/11,2009/01/20 K.Matsuda Start ユーザ状態に無効を追加(intで扱うようにしたので、そのままセット)
            userMasterLog.setUserStatusAfter(currentUser.getUserStatus());
// 2008/12/11,2009/01/20 K.Matsuda End
            userMasterLog.setSameUserLoginMaxAfter(currentUser.getSameUserLoginMax());
            userMasterLog.setFailedLoginAttemptsAfter(currentUser.getFailLoginAttem());
            userMasterLog.setRoleIdAfter(currentUser.getRoleID());
            userMasterLog.setDepartmentAfter(currentUser.getDepartment());
            userMasterLog.setRemarkAfter(currentUser.getRemarks());
        }

        //項目リストの生成
        List list = new ArrayList();
        list.add(currentUser.getUserID());
        list.add(EmConstants.OPELOG_PASSWORD);
        list.add(String.valueOf(currentUser.getPwdChangeInterval()));
        list.add(txt_PwdExpires.getText());
        list.add(currentUser.getRoleID());
        list.add(String.valueOf(currentUser.getSameUserLoginMax()));
// 2008/12/11 K.Matsuda Start ユーザ状態に無効を追加(intで扱うようにしたので、そのままセット)
        list.add(String.valueOf(currentUser.getUserStatus()));
// 2008/12/11 K.Matsuda End
        list.add(String.valueOf(currentUser.getFailLoginAttem()));

        list.add(currentUser.getUserName());
        list.add(currentUser.getDepartment());
        list.add(currentUser.getRemarks());

        P11LogWriter logWriter = new P11LogWriter(conn);
        logWriter.createOperationLog(userInfo, operationType, list);
        logWriter.createMasterChengeLog(userMasterLog);
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
        lbl_RequirePassword_Re.setVisible(visible);
        lbl_RequireRoleId.setVisible(visible);
    }
// 2008/12/25 K.Matsuda End

    /** 
     * クリアボタンの処理を実装します
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        //ユーザIDテキストボックスをクリア
        txt_R_UserId.setText("");

        //パスワードテキストボックスをクリア
        txt_Password.setText("");
        //パスワードテキストボックスを読み取り専用にセット
        txt_Password.setReadOnly(true);

        //パスワードテキストボックス(再確認)をクリア
        txt_Password_Re.setText("");
        //パスワードテキストボックス(再確認)を読み取り専用にセット
        txt_Password_Re.setReadOnly(true);

        //パスワード更新間隔テキストボックスをクリア
        txt_PwdChangeInterval.setText("");
        //パスワード更新間隔テキストボックスを表示
        txt_PwdChangeInterval.setVisible(true);
        //パスワード更新間隔テキストボックスを読み取り専用にセット
        txt_PwdChangeInterval.setReadOnly(true);

        //パスワード更新間隔ラジオボタンをクリア
        rdo_PwdChangeInterval.setChecked(false);
        rdo_PwdChangeUnrestricted.setChecked(false);
        rdo_PwdChangeRollSuccession.setChecked(false);
        //パスワード更新間隔ラジオボタンを非活性にセット
        rdo_PwdChangeInterval.setEnabled(false);
        rdo_PwdChangeUnrestricted.setEnabled(false);
        rdo_PwdChangeRollSuccession.setEnabled(false);

        //パスワード有効期限テキストボックスをクリア
        txt_PwdExpires.setText("");

        //ロールIDテキストボックスをクリア
        txt_RoleId.setText("");
        //ロールIDテキストボックスを読み取り専用にセット
        txt_RoleId.setReadOnly(true);

        //ロールID検索ボタンを無効にセット
        btn_P_Search2.setEnabled(false);

        //同一ユーザログイン可能数テキストボックスをクリア
        txt_SameUserLoginMax.setText("");
        //同一ユーザログイン可能数テキストボックスを読み取り専用にセット
        txt_SameUserLoginMax.setReadOnly(true);

        //同一ユーザログイン可能数ラジオボタンをクリア
        rdo_SameUserLoginMax.setChecked(false);
        rdo_SameUserLoginUnrestricted.setChecked(false);
        //同一ユーザログイン可能数ラジオボタンを非活性にセット
        rdo_SameUserLoginMax.setEnabled(false);
        rdo_SameUserLoginUnrestricted.setEnabled(false);

// 2008/12/11 K.Matsuda Start 状態に無効を追加
        //ロック状態ラジオボタンをクリア
        rdo_UserStatus_Active.setChecked(false);
        rdo_UserStatus_Disable.setChecked(false);
        rdo_UserStatus_Locked.setChecked(false);
        //ロック状態ラジオボタンを非活性にセット
        rdo_UserStatus_Active.setEnabled(false);
        rdo_UserStatus_Disable.setEnabled(false);
        rdo_UserStatus_Locked.setEnabled(false);
// 2008/12/11 K.Matsuda End

        //認証ミス猶予回数テキストボックスをクリア
        txt_FailedLoginAttempts.setText("");
        //認証ミス猶予回数テキストボックスを表示
        txt_FailedLoginAttempts.setVisible(true);
        //認証ミス猶予回数テキストボックスを読取り専用にセット
        txt_FailedLoginAttempts.setReadOnly(true);

        //認証ミス猶予回数ラジオボタンをクリア
        rdo_FailedLoginRestricted.setChecked(false);
        rdo_FailedLoginUnrestricted.setChecked(false);
        rdo_FailedLoginRollSuccession.setChecked(false);
        //認証ミス猶予回数ラジオボタンを非活性にセット
        rdo_FailedLoginRestricted.setEnabled(false);
        rdo_FailedLoginUnrestricted.setEnabled(false);
        rdo_FailedLoginRollSuccession.setEnabled(false);

        //ユーザ名テキストボックスをクリア
        txt_UserName.setText("");
        //ユーザ名テキストボックスを読み取り専用にセット
        txt_UserName.setReadOnly(true);

        //所属テキストボックスをクリア
        txt_Belonging.setText("");
        //所属テキストボックスを読み取り専用にセット
        txt_Belonging.setReadOnly(true);

        //備考テキストボックスをクリア
        txt_Note.setText("");
        //備考テキストボックスを読み取り専用にセット
        txt_Note.setReadOnly(true);

        // 登録ボタン
        this.btn_Submit.setEnabled(true);
        // 修正ボタン
        this.btn_Modify.setEnabled(true);
        // 削除ボタン
        this.btn_Delete.setEnabled(true);
        // 設定ボタン
        this.btn_Commit.setEnabled(false);
        // クリアボタン
        this.btn_Clear.setEnabled(false);
    }

    /** 
     * パスワード更新間隔制限ラジオボタンクリック時の処理です。
     * パスワード更新間隔テキストボックスを編集可能に設定します。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_PwdChangeInterval_Click(ActionEvent e)
            throws Exception
    {
        this.txt_PwdChangeInterval.setReadOnly(false);
    }

    /** 
     * パスワード更新間隔無制限ラジオボタンクリック時の処理です。
     * パスワード更新間隔テキストボックスの値をクリアし、読取り専用に設定します。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_PwdChangeUnrestricted_Click(ActionEvent e)
            throws Exception
    {
        this.txt_PwdChangeInterval.setReadOnly(true);
    }

    /** 
     * パスワード更新間隔ロール設定使用ラジオボタンクリック時の処理です。
     * パスワード更新間隔テキストボックスの値をクリアし、読取り専用に設定します。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_PwdChangeRollSuccession_Click(ActionEvent e)
            throws Exception
    {
        this.rdo_PwdChangeUnrestricted_Click(e);
    }

    /** 
     * 同一ユーザログイン可能数制限ラジオボタンクリック時の処理です。
     * 同一ユーザログイン可能数テキストボックスを編集可能に設定します。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_SameUserLoginMax_Click(ActionEvent e)
            throws Exception
    {
        this.txt_SameUserLoginMax.setReadOnly(false);
    }

    /** 
     * 同一ユーザログイン可能数無制限ラジオボタンクリック時の処理です。
     * 同一ユーザログイン可能数テキストボックスの値をクリアし、読み取り専用に設定します。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_SameUserLoginUnrestricted_Click(ActionEvent e)
            throws Exception
    {
        this.txt_SameUserLoginMax.setReadOnly(true);
    }

    /** 
     * 認証ミス猶予回数制限ラジオボタンクリック時の処理です。
     * 認証ミス猶予回数テキストボックスを編集可能に設定します。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_FailedLoginRestricted_Click(ActionEvent e)
            throws Exception
    {
        this.txt_FailedLoginAttempts.setReadOnly(false);
    }

    /** 
     * 認証ミス猶予回数無制限ラジオボタンクリック時の処理です。
     * 認証ミス猶予回数テキストボックスの値をクリアし、読取り専用に設定します。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_FailedLoginUnrestricted_Click(ActionEvent e)
            throws Exception
    {
        this.txt_FailedLoginAttempts.setReadOnly(true);
    }

    /** 
     * 認証ミス猶予回数ロール設定使用ラジオボタンクリック時の処理です。
     * 認証ミス猶予回数テキストボックスの値をクリアし、読取り専用に設定します。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_FailedLoginRollSuccession_Click(ActionEvent e)
            throws Exception
    {
        this.rdo_FailedLoginUnrestricted_Click(e);
    }

}
//end of class

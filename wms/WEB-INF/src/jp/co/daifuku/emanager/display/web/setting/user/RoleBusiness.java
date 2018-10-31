// $Id: RoleBusiness.java 3965 2009-04-06 02:55:05Z admin $

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
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.bluedog.util.Validator;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.EmTableColumns;
import jp.co.daifuku.emanager.database.handler.RoleFunctionMapHandler;
import jp.co.daifuku.emanager.database.handler.RoleHandler;
import jp.co.daifuku.emanager.database.handler.TerminalHandler;
import jp.co.daifuku.emanager.database.handler.UserHandler;
import jp.co.daifuku.emanager.display.web.setting.user.listbox.RoleListBusiness;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;


/** <jp>
 * ロール設定の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
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
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 </en> */
public class RoleBusiness
        extends Role
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
        setFocus(txt_RoleId);
        
        // 設定ボタン押下時にValidateExceptionが発生する可能性がある為
        // page_initialize()でも設定する
        setBeforeConfirm();
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
        
        //        //プロダクトタイプをViewStateに保存
        //        this.getViewState().setString("PRODUCTTYPE",this.request.getParameter(PRODUCTTYPE));
        //        //メニュータイプをViewStateに保存
        //        this.getViewState().setString("MENUTYPE",this.request.getParameter(MENUTYPE));

        //ロール名テキストボックスを読み取り専用にセット
        txt_RoleName.setReadOnly(true);
        
        // 設定ボタン
        this.btn_Commit.setEnabled(false);
        // クリアボタン
        this.btn_Clear.setEnabled(false);
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

    // Private methods -----------------------------------------------
//  2008/12/25 K.Matsuda Start 赤*を非表示
    /**
     * 必須項目のマーク(*)を非表示にします
     */
    private void setVisibleRequireMarks(boolean visible)
    {
        // *を非表示にする
        lbl_RequireRoleId.setVisible(visible);
        lbl_RequireRoleName.setVisible(visible);
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
     * ロールID検索ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_P_Search_Click(ActionEvent e)
            throws Exception
    {
        //ロール一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();
        param.setParameter(RoleListBusiness.ROLEID_KEY, txt_RoleId.getText());

        //処理中画面->結果画面
        redirect("/emanager/setting/user/listbox/RoleList.do", param, "/Progress.do");
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

        this.txt_FailedLoginAttempts.setVisible(true);
        this.txt_PasswordChangeInterval.setVisible(true);

        try
        {
            //クリア処理
            btn_Clear_Click(e);

            //ロールIDテキストボックスを入力チェック
            txt_RoleId.validate(this, true);

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            //すでに同一のロールIDが登録済みかを確認
            if (isDefined(txt_RoleId.getText(), conn))
            {
                //すでに同一のロールIDが登録されています。
                message.setMsgResourceKey("6403012");
                return;
            }

            //ロール名テキストボックスを有効にする
            txt_RoleName.setReadOnly(false);

            //ロールIDをテキストボックスにセット
            txt_R_RoleId.setText(txt_RoleId.getText());

            AuthenticationSystem authSys =
                    EmHandlerFactory.getAuthenticationSystemHandler(conn).findAuthenticationSystem();

            boolean faieldLoginUserLockFlag = authSys.getFaieldLoginUserLockFlag();

            this.rdo_FailedLoginAttempts1.setEnabled(faieldLoginUserLockFlag);
            this.rdo_FailedLoginAttempts2.setEnabled(faieldLoginUserLockFlag);
            this.rdo_FailedLoginAttempts3.setEnabled(faieldLoginUserLockFlag);

            this.txt_FailedLoginAttempts.setReadOnly(!faieldLoginUserLockFlag);

            if (faieldLoginUserLockFlag)
            {
                this.rdo_FailedLoginAttempts1.setChecked(true);
            }
            else
            {
                this.rdo_FailedLoginAttempts3.setChecked(true);
            }

            boolean passExpireFlag = authSys.getPassExpireFlag();

            this.rdo_PWDChangeInterval1.setEnabled(passExpireFlag);
            this.rdo_PWDChangeInterval2.setEnabled(passExpireFlag);
            this.rdo_PWDChangeInterval3.setEnabled(passExpireFlag);

            if (passExpireFlag)
            {
                this.rdo_PWDChangeInterval1.setChecked(true);
            }
            else
            {
                this.rdo_PWDChangeInterval3.setChecked(true);
            }

            this.txt_PasswordChangeInterval.setReadOnly(!passExpireFlag);

            this.rdo_TargetCommon.setChecked(true);
            this.rdo_TargetCommon.setEnabled(true);
            this.rdo_TargetUser.setEnabled(true);
            this.rdo_TargetTerminal.setEnabled(true);

            //登録中を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_INSERT);

            // ボタンの使用不可を設定
            changebtn();
            
            setFocus(txt_RoleName);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }

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

            //ロールIDテキストボックスを入力チェック
            txt_RoleId.validate(this, true);

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            RoleHandler roleHandle = EmHandlerFactory.getRoleHandler(conn);

            jp.co.daifuku.emanager.database.entity.Role roleEntity = roleHandle.findByRoleId(txt_RoleId.getText());

            //すでに同一のロールIDが登録済みかを確認 
            if (roleEntity == null)
            {
                //指定されたロールIDは登録されていません。
                message.setMsgResourceKey("6403013");
                return;
            }

            //ロール名テキストボックスを有効にする
            txt_RoleName.setReadOnly(false);

            //ロールIDをテキストボックスにセット
            txt_R_RoleId.setText(txt_RoleId.getText());

            AuthenticationSystem authSys =
                    EmHandlerFactory.getAuthenticationSystemHandler(conn).findAuthenticationSystem();
            boolean passExpireFlag = authSys.getPassExpireFlag();
            boolean faieldLoginUserLockFlag = authSys.getFaieldLoginUserLockFlag();

            this.txt_PasswordChangeInterval.setReadOnly(!passExpireFlag);

            this.rdo_FailedLoginAttempts1.setEnabled(faieldLoginUserLockFlag);
            this.rdo_FailedLoginAttempts2.setEnabled(faieldLoginUserLockFlag);
            this.rdo_FailedLoginAttempts3.setEnabled(faieldLoginUserLockFlag);

            this.rdo_PWDChangeInterval1.setEnabled(passExpireFlag);
            this.rdo_PWDChangeInterval2.setEnabled(passExpireFlag);
            this.rdo_PWDChangeInterval3.setEnabled(passExpireFlag);

            if (faieldLoginUserLockFlag)
            {
                int failedLoginAttempts = roleEntity.getFailLoginAttem();

                switch (failedLoginAttempts)
                {
                    // 無制限
                    case FAILED_ATTEMPTS_STATUS_NOLIMITED:
                        this.txt_FailedLoginAttempts.setReadOnly(true);
                        break;
                    // システム設定を使用
                    case FAILED_ATTEMPTS_STATUS_EXTEND:
                        this.txt_FailedLoginAttempts.setReadOnly(true);
                        break;
                    // その他
                    default:
                        this.txt_FailedLoginAttempts.setReadOnly(false);
                        break;
                }
            }

            if (passExpireFlag)
            {
                int passwordChangeInterval = roleEntity.getPassChangeInterval();

                switch (passwordChangeInterval)
                {
                    // 無制限
                    case PWDCHANGEINTERVAL_STATUS_NOLIMITED:
                        this.txt_PasswordChangeInterval.setReadOnly(true);
                        break;

                    // システム設定を使用
                    case PWDCHANGEINTERVAL_STATUS_EXTEND:
                        this.txt_PasswordChangeInterval.setReadOnly(true);
                        break;

                    // その他
                    default:
                        this.txt_PasswordChangeInterval.setReadOnly(false);
                        break;
                }
            }

            this.rdo_TargetCommon.setEnabled(true);
            this.rdo_TargetUser.setEnabled(true);
            this.rdo_TargetTerminal.setEnabled(true);

            //データベースのデータをセットする
            mapping(roleEntity);

            this.txt_FailedLoginAttempts.setVisible(faieldLoginUserLockFlag);
            this.txt_PasswordChangeInterval.setVisible(passExpireFlag);

            //修正中を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_MODIFY);

            // ボタンの使用不可を設定
            changebtn();
            
            setFocus(txt_RoleName);
        }
        finally
        {
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

            //ロールIDテキストボックスを入力チェック
            txt_RoleId.validate(this, true);

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            RoleHandler roleHandler = EmHandlerFactory.getRoleHandler(conn);
            jp.co.daifuku.emanager.database.entity.Role roleEntity = roleHandler.findByRoleId(txt_RoleId.getText());

            //すでに同一のロールIDが登録済みかを確認 
            if (roleEntity == null)
            {
                //指定されたロールIDは登録されていません。
                message.setMsgResourceKey("6403013");
                return;
            }

            //データベースのデータをセットする
            mapping(roleEntity);

            AuthenticationSystem authSys =
                    EmHandlerFactory.getAuthenticationSystemHandler(conn).findAuthenticationSystem();

            this.txt_FailedLoginAttempts.setVisible(authSys.getFaieldLoginUserLockFlag());
            this.txt_PasswordChangeInterval.setVisible(authSys.getPassExpireFlag());

            // ボタンの使用不可を設定
            changebtn();
            
            //削除中を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_DELETE);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
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
            //ロールID入力チェック(必須入力項目)
            txt_R_RoleId.validate();

            String roleid = txt_R_RoleId.getText();
            String rolename = txt_RoleName.getText();

            //コネクション取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            RoleHandler roleHandler = EmHandlerFactory.getRoleHandler(conn);
            jp.co.daifuku.emanager.database.entity.Role role = null;

            //処理区分
            String proc = this.getViewState().getString(VSKEY_PROCESS_KEY);

            //完了時のメッセージ
            String comp_msg = "";

            //処理区分が押下されていない場合
            if (proc == null)
            {
                return;
            }
            //登録時
            else if (proc.equals(VSKEY_INSERT))
            {
                if (roleHandler.findByRoleId(roleid) != null)
                {
                    //すでに同一のロールIDが登録されています。
                    message.setMsgResourceKey("6403012");
                    return;
                }
                //ロール名入力チェック(必須入力項目)
                txt_RoleName.validate(this, true);

                int failedLoginAttempts = FAILED_ATTEMPTS_STATUS_NOLIMITED;

                if (this.rdo_FailedLoginAttempts1.getChecked())
                {
                    this.txt_FailedLoginAttempts.validate(this, true);
                    failedLoginAttempts = Integer.parseInt(txt_FailedLoginAttempts.getText());
                }
                else if (this.rdo_FailedLoginAttempts2.getChecked())
                {
                    failedLoginAttempts = FAILED_ATTEMPTS_STATUS_NOLIMITED;
                }
                else if (this.rdo_FailedLoginAttempts3.getChecked())
                {
                    failedLoginAttempts = FAILED_ATTEMPTS_STATUS_EXTEND;
                }

                int pwdChangeInterval = PWDCHANGEINTERVAL_STATUS_NOLIMITED;

                if (this.rdo_PWDChangeInterval1.getChecked())
                {
                    this.txt_PasswordChangeInterval.validate(this, true);
                    pwdChangeInterval = this.txt_PasswordChangeInterval.getInt();
                }
                else if (this.rdo_PWDChangeInterval2.getChecked())
                {
                    pwdChangeInterval = PWDCHANGEINTERVAL_STATUS_NOLIMITED;
                }
                else if (this.rdo_PWDChangeInterval3.getChecked())
                {
                    pwdChangeInterval = PWDCHANGEINTERVAL_STATUS_EXTEND;
                }

                int roleTarget = EmConstants.ROLE_TARGET_ALL;

                if (this.rdo_TargetCommon.getChecked())
                {
                    roleTarget = EmConstants.ROLE_TARGET_ALL;
                }
                else if (this.rdo_TargetUser.getChecked())
                {
                    roleTarget = EmConstants.ROLE_TARGET_USER;
                }
                else if (this.rdo_TargetTerminal.getChecked())
                {
                    roleTarget = EmConstants.ROLE_TARGET_TERMINAL;
                }

                role = createRoleEntity(UPDATE_KIND_INSERT);
                role.setRoleID(roleid);
                role.setRoleName(rolename);
                role.setFailLoginAttem(failedLoginAttempts);
                role.setPassChangeInterval(pwdChangeInterval);
                role.setTarget(roleTarget);

                roleHandler.createRole(role);

                //6401003=登録しました。
                comp_msg = "6401003";
            }
            //修正時
            else if (proc.equals(VSKEY_MODIFY))
            {
                if (roleHandler.findByRoleId(roleid) == null)
                {
                    //指定されたロールIDは登録されていません。
                    message.setMsgResourceKey("6403013");
                    return;
                }
                // ロール名入力チェック(必須入力項目)
                txt_RoleName.validate(this, true);

                if (this.rdo_FailedLoginAttempts1.getChecked())
                {
                    txt_FailedLoginAttempts.validate(this, true);
                }

                if (this.rdo_PWDChangeInterval1.getChecked())
                {
                    txt_PasswordChangeInterval.validate(this, true);
                }

                int failedLoginAttempts = FAILED_ATTEMPTS_STATUS_NOLIMITED;

                if (this.rdo_FailedLoginAttempts1.getChecked())
                {
                    this.txt_FailedLoginAttempts.validate(this, true);
                    failedLoginAttempts = Integer.parseInt(txt_FailedLoginAttempts.getText());
                }
                else if (this.rdo_FailedLoginAttempts2.getChecked())
                {
                    failedLoginAttempts = FAILED_ATTEMPTS_STATUS_NOLIMITED;
                }
                else if (this.rdo_FailedLoginAttempts3.getChecked())
                {
                    failedLoginAttempts = FAILED_ATTEMPTS_STATUS_EXTEND;
                }

                int pwdChangeInterval = PWDCHANGEINTERVAL_STATUS_NOLIMITED;

                if (this.rdo_PWDChangeInterval1.getChecked())
                {
                    this.txt_PasswordChangeInterval.validate(this, true);
                    pwdChangeInterval = this.txt_PasswordChangeInterval.getInt();
                }
                else if (this.rdo_PWDChangeInterval2.getChecked())
                {
                    pwdChangeInterval = PWDCHANGEINTERVAL_STATUS_NOLIMITED;
                }
                else if (this.rdo_PWDChangeInterval3.getChecked())
                {
                    pwdChangeInterval = PWDCHANGEINTERVAL_STATUS_EXTEND;
                }

                int roleTarget = EmConstants.ROLE_TARGET_ALL;

                if (this.rdo_TargetCommon.getChecked())
                {
                    roleTarget = EmConstants.ROLE_TARGET_ALL;
                }
                else if (this.rdo_TargetUser.getChecked())
                {
                    roleTarget = EmConstants.ROLE_TARGET_USER;
                }
                else if (this.rdo_TargetTerminal.getChecked())
                {
                    roleTarget = EmConstants.ROLE_TARGET_TERMINAL;
                }

                role = createRoleEntity(UPDATE_KIND_UPDATE);
                role.setRoleID(roleid);
                role.setRoleName(rolename);
                role.setFailLoginAttem(failedLoginAttempts);
                role.setPassChangeInterval(pwdChangeInterval);
                role.setTarget(roleTarget);

                if (roleHandler.updateByRoleId(role) == 0)
                {
                    //指定されたロールIDは登録されていません。
                    message.setMsgResourceKey("6403013");
                    return;
                }

                //6401004=修正しました。
                comp_msg = "6401004";
            }
            //削除時
            else if (proc.equals(VSKEY_DELETE))
            {
                if (roleHandler.findByRoleId(roleid) == null)
                { //指定されたロールIDは登録されていません。
                    message.setMsgResourceKey("6403013");
                    return;
                }
                // このロールを使用しているユーザ、端末、ロールマップが存在すればこのロールの削除は行わない
                // 各ハンドラの取得
                RoleFunctionMapHandler roleMapHandler = EmHandlerFactory.getRoleFunctionMapHandler(conn);
                UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);
                TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);

                if (0 < terminalHandler.finCountByRoleId(roleid))
                {
                    // このロールを使用している端末が存在しているため削除できません。
                    message.setMsgResourceKey("6403063");
                    return;
                }

                if (0 < userHandler.finCountByRoleId(roleid))
                {
                    // このロールを使用しているユーザが存在しているため削除できません。
                    message.setMsgResourceKey("6403064");
                    return;
                }

                if (0 < roleMapHandler.findCountByRoleId(roleid))
                {
                    // このロールを使用している機能画面が存在しているため削除できません。
                    message.setMsgResourceKey("6403065");
                    return;
                }

                if (roleHandler.deleteRole(roleid) == 0)
                {
                    // 指定されたロールIDは登録されていません。
                    message.setMsgResourceKey("6403013");
                    return;
                }

                //6401005=削除しました。
                comp_msg = "6401005";
            }

            //ユーザ情報の作成
            DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
            //項目リストの生成
            List list = new ArrayList();
            list.add(txt_R_RoleId.getText());
            list.add(txt_RoleName.getText());
            list.add(rdo_FailedLoginAttempts1.getChecked() ? txt_FailedLoginAttempts.getText()
                                                          : rdo_FailedLoginAttempts2.getChecked() ? String.valueOf(EmConstants.FAILED_ATTEMPTS_STATUS_NOLIMITED)
                                                                                                 : String.valueOf(EmConstants.FAILED_ATTEMPTS_STATUS_EXTEND));
            list.add(rdo_PWDChangeInterval1.getChecked() ? txt_PasswordChangeInterval.getText()
                                                        : rdo_PWDChangeInterval2.getChecked() ? String.valueOf(EmConstants.PWDCHANGEINTERVAL_STATUS_NOLIMITED)
                                                                                             : String.valueOf(EmConstants.PWDCHANGEINTERVAL_STATUS_EXTEND));
            list.add(rdo_TargetCommon.getChecked() ? String.valueOf(EmConstants.ROLE_TARGET_ALL)
                                                  : rdo_TargetUser.getChecked() ? String.valueOf(EmConstants.ROLE_TARGET_USER)
                                                                               : String.valueOf(EmConstants.ROLE_TARGET_TERMINAL));
            //オペレーションログ出力
            P11LogWriter logWriter = new P11LogWriter(conn);
            logWriter.createOperationLog(userInfo,
                    proc.equals(VSKEY_INSERT) ? EmConstants.OPELOG_CLASS_REGIST
                                             : proc.equals(VSKEY_MODIFY) ? EmConstants.OPELOG_CLASS_MODIFY
                                                                        : EmConstants.OPELOG_CLASS_DELETE, list);


            //設定
            EmConnectionHandler.commit(conn);
            //クリア処理
            btn_Clear_Click(e);
            //メッセージをセット
            message.setMsgResourceKey(comp_msg);

            // メンテナンスログ
            String msg = "";
            // 登録時
            if (proc.equals(VSKEY_INSERT))
            {
                msg = "6400028";
            }
            //修正時
            else if (proc.equals(VSKEY_MODIFY))
            {
                msg = "6400029";
            }
            //削除時
            else if (proc.equals(VSKEY_DELETE))
            {
                msg = "6400030";
            }

            msg += Message.MSG_DELIM + getKeyEqualValueString(EmTableColumns.ROLE_ROLEID, roleid);

            EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_ROLE, msg, "");

            this.setFocus(this.btn_Help);
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

            //データベースエラーが発生しました。
            message.setMsgResourceKey("6403069");
            return;
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
     * key文字列とvalue文字列を"="で連結して返します
     * @param key
     * @param value
     * @return 結果文字列
     */
    private String getKeyEqualValueString(String key, String value)
    {
        return key + "=" + value;
    }

    /**
     * Roleエンティティを新規作成します
     * @param updateKind
     * @return Roleエンティティ
     */
    private jp.co.daifuku.emanager.database.entity.Role createRoleEntity(int updateKind)
    {
        jp.co.daifuku.emanager.database.entity.Role role = new jp.co.daifuku.emanager.database.entity.Role();

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
            role.setUpdateUser(userhandler.getUserID());
        }

        role.setUpdateTerminal(this.getHttpRequest().getRemoteAddr());

        role.setUpdateKind(updateKind);

        role.setUpdateProcess(this.getClass().getName());

        return role;
    }


    /** 
     * クリアボタンの処理を実装します
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        //ロールIDテキストボックスをクリア
        txt_R_RoleId.setText("");
        //ロール名テキストボックスをクリア
        txt_RoleName.setText("");
        //ロール名テキストボックスを読み取り専用にセット
        txt_RoleName.setReadOnly(true);

        this.txt_FailedLoginAttempts.setText("");
        this.txt_PasswordChangeInterval.setText("");
        this.txt_FailedLoginAttempts.setReadOnly(true);
        this.txt_PasswordChangeInterval.setReadOnly(true);

        this.rdo_FailedLoginAttempts1.setChecked(true);
        this.rdo_FailedLoginAttempts1.setEnabled(false);
        this.rdo_FailedLoginAttempts2.setEnabled(false);
        this.rdo_FailedLoginAttempts3.setEnabled(false);

        this.rdo_PWDChangeInterval1.setChecked(true);
        this.rdo_PWDChangeInterval1.setEnabled(false);
        this.rdo_PWDChangeInterval2.setEnabled(false);
        this.rdo_PWDChangeInterval3.setEnabled(false);

        this.rdo_TargetCommon.setChecked(true);
        this.rdo_TargetCommon.setEnabled(false);
        this.rdo_TargetUser.setEnabled(false);
        this.rdo_TargetTerminal.setEnabled(false);

        this.txt_FailedLoginAttempts.setVisible(true);
        this.txt_PasswordChangeInterval.setVisible(true);
        
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
     * すでに同一のロールIDが登録済みかを確認します。
     * @param key  TerminalNumber
     * @param conn Connection
     * @return boolean
     * @throws Exception 
     */
    private boolean isDefined(String key, Connection conn)
            throws Exception
    {
        return EmHandlerFactory.getRoleHandler(conn).findByRoleId(key) != null;
    }

    /** 
     * 修正、登録ボタン押下時に、DBの項目を各コントロールへマッピングします。
     * @param roleEntity Roleエンティティ
     * @throws Exception 
     */
    private void mapping(jp.co.daifuku.emanager.database.entity.Role roleEntity)
            throws Exception
    {
        //登録済みのロールIDがある場合
        if (roleEntity != null)
        {
            String roleName = roleEntity.getRoleName();

            txt_R_RoleId.setText(txt_RoleId.getText());
            txt_RoleName.setText(roleName);

            int failedLoginAttempts = roleEntity.getFailLoginAttem();

            switch (failedLoginAttempts)
            {
                // 無制限
                case FAILED_ATTEMPTS_STATUS_NOLIMITED:
                    this.rdo_FailedLoginAttempts2.setChecked(true);
                    break;
                // システム設定を使用
                case FAILED_ATTEMPTS_STATUS_EXTEND:
                    this.rdo_FailedLoginAttempts3.setChecked(true);
                    break;
                // その他
                default:
                    this.rdo_FailedLoginAttempts1.setChecked(true);
                    this.txt_FailedLoginAttempts.setText(Integer.toString(failedLoginAttempts));
                    break;
            }

            int passwordChangeInterval = roleEntity.getPassChangeInterval();

            switch (passwordChangeInterval)
            {
                // 無制限
                case PWDCHANGEINTERVAL_STATUS_NOLIMITED:
                    this.rdo_PWDChangeInterval2.setChecked(true);
                    break;
                // システム設定を使用
                case PWDCHANGEINTERVAL_STATUS_EXTEND:
                    this.rdo_PWDChangeInterval3.setChecked(true);
                    break;
                // その他
                default:
                    this.rdo_PWDChangeInterval1.setChecked(true);
                    this.txt_PasswordChangeInterval.setText(Integer.toString(passwordChangeInterval));
                    break;
            }

            int roleTarget = roleEntity.getTarget();
            switch (roleTarget)
            {
                // 無制限
                case EmConstants.ROLE_TARGET_USER:
                    this.rdo_TargetUser.setChecked(true);
                    break;
                // システム設定を使用
                case EmConstants.ROLE_TARGET_TERMINAL:
                    this.rdo_TargetTerminal.setChecked(true);
                    break;
                // その他
                default:
                    this.rdo_TargetCommon.setChecked(true);
                    break;
            }
        }
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_FailedLoginAttempts1_Click(ActionEvent e)
            throws Exception
    {
        if (this.rdo_FailedLoginAttempts1.getChecked())
        {
            this.txt_FailedLoginAttempts.setReadOnly(false);
        }
        else
        {
            this.txt_FailedLoginAttempts.setReadOnly(true);
        }
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_FailedLoginAttempts2_Click(ActionEvent e)
            throws Exception
    {
        if (this.rdo_FailedLoginAttempts1.getChecked())
        {
            this.txt_FailedLoginAttempts.setReadOnly(false);
        }
        else
        {
            this.txt_FailedLoginAttempts.setReadOnly(true);
        }
    }


    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_FailedLoginAttempts3_Click(ActionEvent e)
            throws Exception
    {
        if (this.rdo_FailedLoginAttempts1.getChecked())
        {
            this.txt_FailedLoginAttempts.setReadOnly(false);
        }
        else
        {
            this.txt_FailedLoginAttempts.setReadOnly(true);
        }
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_PWDChangeInterval1_Click(ActionEvent e)
            throws Exception
    {
        if (this.rdo_PWDChangeInterval1.getChecked())
        {
            txt_PasswordChangeInterval.setReadOnly(false);
        }
        else
        {
            txt_PasswordChangeInterval.setReadOnly(true);
        }
    }


    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_PWDChangeInterval2_Click(ActionEvent e)
            throws Exception
    {
        if (this.rdo_PWDChangeInterval1.getChecked())
        {
            txt_PasswordChangeInterval.setReadOnly(false);
        }
        else
        {
            txt_PasswordChangeInterval.setReadOnly(true);
        }
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_PWDChangeInterval3_Click(ActionEvent e)
            throws Exception
    {
        if (this.rdo_PWDChangeInterval1.getChecked())
        {
            txt_PasswordChangeInterval.setReadOnly(false);
        }
        else
        {
            txt_PasswordChangeInterval.setReadOnly(true);
        }
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
    public void lbl_SettingName_Server(ActionEvent e)
            throws Exception
    {
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
    public void btn_Help_Server(ActionEvent e)
            throws Exception
    {
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
    public void tab_Click(ActionEvent e)
            throws Exception
    {
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
    public void btn_ToMenu_Server(ActionEvent e)
            throws Exception
    {
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
    public void lbl_RoleId_Server(ActionEvent e)
            throws Exception
    {
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
    public void txt_RoleId_Server(ActionEvent e)
            throws Exception
    {
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
    public void txt_RoleId_AutoCompleteItemClick(ActionEvent e)
            throws Exception
    {
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
    public void txt_RoleId_AutoComplete(ActionEvent e)
            throws Exception
    {
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
    public void txt_RoleId_EnterKey(ActionEvent e)
            throws Exception
    {
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
    public void txt_RoleId_TabKey(ActionEvent e)
            throws Exception
    {
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
    public void txt_RoleId_InputComplete(ActionEvent e)
            throws Exception
    {
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
    public void btn_P_Search_Server(ActionEvent e)
            throws Exception
    {
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
    public void lbl_ProcessingType_Server(ActionEvent e)
            throws Exception
    {
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
    public void btn_Submit_Server(ActionEvent e)
            throws Exception
    {
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
    public void btn_Modify_Server(ActionEvent e)
            throws Exception
    {
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
    public void btn_Delete_Server(ActionEvent e)
            throws Exception
    {
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
    public void lbl_RoleId2_Server(ActionEvent e)
            throws Exception
    {
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
    public void txt_R_RoleId_Server(ActionEvent e)
            throws Exception
    {
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
    public void txt_R_RoleId_AutoCompleteItemClick(ActionEvent e)
            throws Exception
    {
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
    public void txt_R_RoleId_AutoComplete(ActionEvent e)
            throws Exception
    {
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
    public void txt_R_RoleId_EnterKey(ActionEvent e)
            throws Exception
    {
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
    public void txt_R_RoleId_TabKey(ActionEvent e)
            throws Exception
    {
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
    public void txt_R_RoleId_InputComplete(ActionEvent e)
            throws Exception
    {
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
    public void lbl_RoleName_Server(ActionEvent e)
            throws Exception
    {
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
    public void txt_RoleName_Server(ActionEvent e)
            throws Exception
    {
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
    public void txt_RoleName_AutoCompleteItemClick(ActionEvent e)
            throws Exception
    {
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
    public void txt_RoleName_AutoComplete(ActionEvent e)
            throws Exception
    {
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
    public void txt_RoleName_EnterKey(ActionEvent e)
            throws Exception
    {
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
    public void txt_RoleName_TabKey(ActionEvent e)
            throws Exception
    {
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
    public void txt_RoleName_InputComplete(ActionEvent e)
            throws Exception
    {
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
    public void lbl_FailedLoginAttempts_Server(ActionEvent e)
            throws Exception
    {
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
    public void rdo_FailedLoginAttempts1_Server(ActionEvent e)
            throws Exception
    {
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
    public void txt_FailedLoginAttempts_Server(ActionEvent e)
            throws Exception
    {
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
    public void txt_FailedLoginAttempts_EnterKey(ActionEvent e)
            throws Exception
    {
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
    public void txt_FailedLoginAttempts_TabKey(ActionEvent e)
            throws Exception
    {
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
    public void rdo_FailedLoginAttempts2_Server(ActionEvent e)
            throws Exception
    {
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
    public void rdo_FailedLoginAttempts3_Server(ActionEvent e)
            throws Exception
    {
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
    public void lbl_PasswordValidityTermDays_Server(ActionEvent e)
            throws Exception
    {
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
    public void rdo_PWDChangeInterval1_Server(ActionEvent e)
            throws Exception
    {
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
    public void txt_PasswordChangeInterval_Server(ActionEvent e)
            throws Exception
    {
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
    public void txt_PasswordChangeInterval_EnterKey(ActionEvent e)
            throws Exception
    {
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
    public void txt_PasswordChangeInterval_TabKey(ActionEvent e)
            throws Exception
    {
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
    public void rdo_PWDChangeInterval2_Server(ActionEvent e)
            throws Exception
    {
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
    public void rdo_PWDChangeInterval3_Server(ActionEvent e)
            throws Exception
    {
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
    public void lbl_Target_Server(ActionEvent e)
            throws Exception
    {
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
    public void rdo_TargetCommon_Server(ActionEvent e)
            throws Exception
    {
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
    public void rdo_TargetCommon_Click(ActionEvent e)
            throws Exception
    {
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
    public void rdo_TargetUser_Server(ActionEvent e)
            throws Exception
    {
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
    public void rdo_TargetUser_Click(ActionEvent e)
            throws Exception
    {
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
    public void rdo_TargetTerminal_Server(ActionEvent e)
            throws Exception
    {
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
    public void rdo_TargetTerminal_Click(ActionEvent e)
            throws Exception
    {
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
    public void btn_Commit_Server(ActionEvent e)
            throws Exception
    {
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
    public void btn_Clear_Server(ActionEvent e)
            throws Exception
    {
    }


}
//end of class

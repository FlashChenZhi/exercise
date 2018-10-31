// $Id: TerminalBusiness.java 3965 2009-04-06 02:55:05Z admin $

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import jp.co.daifuku.emanager.database.entity.User;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.EmTableColumns;
import jp.co.daifuku.emanager.database.handler.TerminalChangeMapHandler;
import jp.co.daifuku.emanager.database.handler.TerminalHandler;
import jp.co.daifuku.emanager.database.handler.TerminalUserMapHandler;
import jp.co.daifuku.emanager.database.handler.UserHandler;
import jp.co.daifuku.emanager.display.web.setting.user.listbox.RoleListBusiness;
import jp.co.daifuku.emanager.display.web.setting.user.listbox.TerminalChangeListBusiness;
import jp.co.daifuku.emanager.display.web.setting.user.listbox.TerminalListBusiness;
import jp.co.daifuku.emanager.display.web.setting.user.listbox.TerminalUserListBusiness;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

/** <jp>
 * 端末設定の画面クラスです。
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
public class TerminalBusiness
        extends Terminal
        implements EmConstants
{
    // Class fields --------------------------------------------------

    /** 子画面を取得するときのキーです */
    public static final String CHILD_SCREEN = "CHILD_SCREEN";

    /** 親画面から呼び出される各子画面を識別します */
    public static final String TERMINAL_LIST = "TERMINAL_LIST";

    /** 子画面を識別するキー */
    public static final String TERMINAL_CHANGE_MAP = "TERMINAL_CHANGE_MAP";

    /** 子画面を識別するキー */
    public static final String TERMINAL_USER_MAP = "TERMINAL_USER_MAP";

    /** システム管理のIPアドレス */
    public static final String SYSTEM_TERMINAL_IPADDRESS = "127.0.0.1";

    /** 切替元の端末Noを保持するキー */
    public static final String PARENT_TERMINAL_NO = TerminalChangeListBusiness.PARENT_TERMINAL_NO;

    /** 選択された端末No.のリストを保持するキー */
    public static final String SELECTED_TERMINAL_NO = TerminalChangeListBusiness.SELECTED_TERMINAL_NO;

    /** 選択されたユーザIDのリストを保持するキー */
    public static final String SELECTED_USER_NO = TerminalUserListBusiness.SELECTED_USER_NO;


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
        setFocus(txt_TerminalNumber);

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

        //端末名テキストボックスを読み取り専用にセット
        txt_TerminalName.setReadOnly(true);
        //端末IPアドレステキストボックスを読み取り専用にセット
        txt_IpAddress.setReadOnly(true);
        //ロールIDテキストボックスを読み取り専用にセット
        txt_RoleId.setReadOnly(true);
        //ロールID検索ボタンを読み取り専用にセット
        btn_P_Search2.setEnabled(false);
        //プリンタ名テキストボックスを読み取り専用にセット
        txt_PrinterName.setReadOnly(true);
        // ホスト名選択ラジオボタンを読み取り専用にセット
        this.rdo_SpecifiesHostName.setEnabled(false);
        // IPアドレス選択ラジオボタンを読み取り専用にセット
        this.rdo_SpecifiesIPAddress.setEnabled(false);
        // ホスト名テキストボックスをを読み取り専用にセット
        this.txt_HostName.setReadOnly(true);
        // 自動ログインONのラジオボタンを読み取り専用にセット
        this.rdo_AutoLoginON.setEnabled(false);
        // 自動ログインOFFのラジオボタンを読み取り専用にセット
        this.rdo_AutoLoginOFF.setEnabled(false);
        // 切替端末選択ボタンを読み取り専用にセット
        this.btn_TerminalSwitch.setEnabled(false);
        // 切替ユーザ選択ボタンを読み取り専用にセット
        this.btn_TerminalUser.setEnabled(false);

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

        // 子画面で設定された子画面種別を取得
        String kindOfChildScreen = param.getParameter(CHILD_SCREEN);

        // 端末切替一覧のとき
        if (TERMINAL_CHANGE_MAP.equals(kindOfChildScreen))
        {
            // 切替端末の配列を取得
            String[] changeMap = param.getParameterValues(SELECTED_TERMINAL_NO);

            getViewState().setString(SELECTED_TERMINAL_NO, getString(changeMap));
        }
        // 端末ユーザ一覧のとき
        else if (TERMINAL_USER_MAP.equals(kindOfChildScreen))
        {
            String[] changeUserMap = param.getParameterValues(SELECTED_USER_NO);
            getViewState().setString(SELECTED_USER_NO, getString(changeUserMap));
        }
        // 端末一覧とロール一覧のとき
        else
        {
            // 端末№を取得
            String terminalkey = param.getParameter(TerminalListBusiness.TERMINALNUMBER_KEY);
            //空ではないときに値をセットする
            if (!Validator.isEmptyCheck(terminalkey))
            {
                txt_TerminalNumber.setText(terminalkey);
                setFocus(txt_TerminalNumber);
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
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Event handler methods -----------------------------------------


    /**
     * String[]をMessage.MSG_DELIMで区切って連結した文字列を返します。
     * 例）String[]{A, B, C} → "A\tB\tC"
     * @param strArr 連結する文字列の配列
     * @return 連結した文字列
     * 
     */
    private String getString(String[] strArr)
    {
        StringBuffer sb = new StringBuffer();

        if (strArr != null && 0 < strArr.length)
        {
            for (int i = 0; i < strArr.length; i++)
            {
                sb.append(strArr[i]);

                if (i < strArr.length - 1)
                {
                    sb.append(Message.MSG_DELIM);
                }
            }
        }

        // StringBufferをString型で返す
        return sb.substring(0);
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


    /** 
     * 端末№検索ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_P_Search_Click(ActionEvent e)
            throws Exception
    {
        //端末一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();
        param.setParameter(TerminalListBusiness.TERMINALNUMBER_KEY, txt_TerminalNumber.getText());

        //ViewStateよりメニュータイプを取得
        String menutype = this.getViewState().getString("MENUTYPE");
        param.setParameter(TerminalListBusiness.MENUTYPE_KEY, menutype);

        //処理中画面->結果画面
        redirect("/emanager/setting/user/listbox/TerminalList.do", param, "/Progress.do");
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

            //端末№テキストボックスを入力チェック
            txt_TerminalNumber.validate(true);

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            //すでに同一の端末№が登録済みかを確認
            if (isDefined(txt_TerminalNumber.getText(), conn))
            {
                //すでに同一の端末№が登録されています。
                message.setMsgResourceKey("6403009");
                return;
            }

            // 認証システムを取得
            AuthenticationSystem sysAuth =
                    EmHandlerFactory.getAuthenticationSystemHandler(conn).findAuthenticationSystem();

            //ロールID検索ボタンを有効にする
            btn_P_Search2.setEnabled(true);
            //端末名テキストボックスを有効にする
            txt_TerminalName.setReadOnly(false);
            //端末IPアドレステキストボックスを有効にする
            txt_IpAddress.setReadOnly(false);
            //ロールIDアドレステキストボックスを有効にする
            txt_RoleId.setReadOnly(false);
            //プリンタ名テキストボックスを有効にする
            txt_PrinterName.setReadOnly(false);
            // ホスト名選択ラジオボタンを有効にする
            this.rdo_SpecifiesHostName.setEnabled(true);
            // IPアドレス選択ラジオボタンを有効にする
            this.rdo_SpecifiesIPAddress.setEnabled(true);
            // 自動ログインONのラジオボタンを有効にする
            this.rdo_AutoLoginON.setEnabled(sysAuth.getAutoLoginFlag());
            // 自動ログインOFFのラジオボタンを有効にする
            this.rdo_AutoLoginOFF.setEnabled(sysAuth.getAutoLoginFlag());
            // 切替端末選択ボタンを有効にする
            this.btn_TerminalSwitch.setEnabled(sysAuth.getTerminalChangeFlag());
            // 切替ユーザ選択ボタンを有効にする
            this.btn_TerminalUser.setEnabled(sysAuth.getTerminalUserCheckFlag());
            // IPアドレス選択ラジオボタンを選択状態にする
            this.rdo_SpecifiesIPAddress.setChecked(true);

            //端末№をテキストボックスにセット
            txt_R_TerminalNumber.setText(txt_TerminalNumber.getText());

            // ボタンの使用不可を設定
            changebtn();

            //登録中を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_INSERT);

            setFocus(txt_TerminalName);
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

            //端末№テキストボックスを入力チェック
            txt_TerminalNumber.validate(true);

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            jp.co.daifuku.emanager.database.entity.Terminal terminal =
                    getTerminalEntity(txt_TerminalNumber.getText(), conn);

            //すでに同一の端末№が登録済みかを確認 
            if (terminal == null)
            {
                //指定された端末№は登録されていません。
                message.setMsgResourceKey("6403010");
                return;
            }

            //データベースのデータをセットする
            mapping(terminal);

            this.getViewState().setString(SELECTED_TERMINAL_NO, getString(terminal.getChangneTerminalNumbeArray()));
            this.getViewState().setString(SELECTED_USER_NO, getString(terminal.getUserIdArray()));

            //ロールID検索ボタンを有効にする
            btn_P_Search2.setEnabled(true);
            //端末名テキストボックスを有効にする
            txt_TerminalName.setReadOnly(false);

            // 設定されているアドレスがIPアドレスかどうかの判断
            if (isIpAddress(terminal.getTerminalAddress()))
            {
                // IPアドレスの場合
                // 端末IPアドレステキストボックスを有効にする
                if (!txt_IpAddress.getText().equals(SYSTEM_TERMINAL_IPADDRESS))
                {
                    txt_IpAddress.setReadOnly(false);
                    // ラジオボタンを有効にする
                    this.rdo_SpecifiesHostName.setEnabled(true);
                    this.rdo_SpecifiesIPAddress.setEnabled(true);
                }

                this.rdo_SpecifiesIPAddress.setChecked(true);
                this.rdo_SpecifiesHostName.setChecked(false);
            }
            else
            {
                // IPアドレスではない場合
                // 端末ホスト名テキストボックスを有効にする
                if (!this.txt_HostName.getText().equals(EmConstants.UNDEFINED_TERMINAL))
                {
                    this.txt_HostName.setReadOnly(false);
                    // ラジオボタンを有効にする
                    this.rdo_SpecifiesHostName.setEnabled(true);
                    this.rdo_SpecifiesIPAddress.setEnabled(true);
                }

                this.rdo_SpecifiesIPAddress.setChecked(false);
                this.rdo_SpecifiesHostName.setChecked(true);
            }

            // 認証システムを取得
            AuthenticationSystem sysAuth =
                    EmHandlerFactory.getAuthenticationSystemHandler(conn).findAuthenticationSystem();

            //ロールIDアドレステキストボックスを有効にする
            txt_RoleId.setReadOnly(false);
            //プリンタ名テキストボックスを有効にする
            txt_PrinterName.setReadOnly(false);
            // 自動ログインオンのラジオボタンを有効にする
            this.rdo_AutoLoginON.setEnabled(sysAuth.getAutoLoginFlag());
            // 自動ログインオフのラジオボタンを有効にする
            this.rdo_AutoLoginOFF.setEnabled(sysAuth.getAutoLoginFlag());

            // 端末切替ボタンを有効にする（UNDEFINED_TERMINALの場合は常に非活性）
            if (!UNDEFINED_TERMINAL.equals(terminal.getTerminalAddress()) && sysAuth.getTerminalChangeFlag())
            {
                this.btn_TerminalSwitch.setEnabled(true);
            }
            else
            {
                this.btn_TerminalSwitch.setEnabled(false);
            }

            // 端末ユーザ切替ボタンを有効にする
            this.btn_TerminalUser.setEnabled(sysAuth.getTerminalUserCheckFlag());

            //修正中を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_MODIFY);

            // ボタンの使用不可を設定
            changebtn();

            setFocus(txt_TerminalName);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /**
     * 端末No.をもとに端末情報と端末切替情報と端末ユーザ情報を取得。端末切替の一覧を端末No.のString[]で作成し、端末エンティティに
     * 格納する。端末ユーザの一覧も端末ユーザIDのString[]を作成し端末エンティティに格納する。
     * @param terminalNumber
     * @param conn
     * @return Terminal
     * @throws SQLException
     */
    private jp.co.daifuku.emanager.database.entity.Terminal getTerminalEntity(String terminalNumber, Connection conn)
            throws SQLException
    {
        // Terminalのハンドラ取得
        TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);

        // 端末情報テーブルからTerminalのエンティティを取得（このエンティティ内に端末切替一覧と端末ユーザ一覧の情報はまだ含まれない）
        jp.co.daifuku.emanager.database.entity.Terminal terminal = terminalHandler.findByTerminalNumber(terminalNumber);

        // terminalNumberに一致するTerminalのエンティティが見つからなかったらnullを返して終了
        if (terminal == null)
        {
            return null;
        }

        // 端末切替ハンドラを取得
        TerminalChangeMapHandler terminalChangeMapHandler = EmHandlerFactory.getTerminalChangeMapHandler(conn);

        // terminalNumberをもとに端末切替情報一覧を取得
        jp.co.daifuku.emanager.database.entity.Terminal[] changeTerminal =
                terminalChangeMapHandler.findByTerminalNumber(terminalNumber);

        // 取得した端末切替情報一覧の配列がnullでも長さが０でもないかをチェック
        if (changeTerminal != null && 0 < changeTerminal.length)
        {
            // 端末切替情報一覧の配列の要素であるTerminalエンティティから端末No.のみを取得してStringの配列を作る
            String[] changeTerminalNumArr = new String[changeTerminal.length];

            for (int i = 0; i < changeTerminal.length; i++)
            {
                changeTerminalNumArr[i] = changeTerminal[i].getTerminalNumber();
            }

            // 端末No.だけを抜き出したStringの配列をTerminalのエンティティに設定する
            terminal.setChangneTerminalNumbeArray(changeTerminalNumArr);
        }

        // 端末ユーザハンドラを取得
        TerminalUserMapHandler terminalUserMapHandler = EmHandlerFactory.getTerminalUserMapHandler(conn);

        // terminalNumberをもとに端末ユーザ情報一覧を取得
        jp.co.daifuku.emanager.database.entity.Terminal[] changeUser =
                terminalUserMapHandler.findByTerminalNumber(terminalNumber);

        // 取得した端末ユーザ情報一覧の配列がnullでも長さが０でもないかをチェック
        if (changeUser != null && 0 < changeUser.length)
        {
            // 端末ユーザ情報一覧の配列の要素であるTerminalエンティティからユーザIDのみを取得してStringの配列を作る
            String[] changeUserNumArr = new String[changeUser.length];

            for (int i = 0; i < changeUser.length; i++)
            {
                changeUserNumArr[i] = changeUser[i].getUserId();
            }

            // ユーザIDだけを抜き出したStringの配列をTerminalのエンティティに設定する
            terminal.setUserIdArray(changeUserNumArr);
        }

        return terminal;
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

            //端末№テキストボックスを入力チェック
            txt_TerminalNumber.validate(true);

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);
            jp.co.daifuku.emanager.database.entity.Terminal terminal =
                    terminalHandler.findByTerminalNumber(txt_TerminalNumber.getText());

            //すでに同一の端末№が登録済みかを確認 
            if (terminal == null)
            {
                //指定された端末№は登録されていません。
                message.setMsgResourceKey("6403010");
                return;
            }

            //データベースのデータをセットする
            mapping(terminal);

            //IPアドレスをチェック
            if (txt_IpAddress.getText().equals(EmConstants.UNDEFINED_TERMINAL))
            {
                //指定された端末№はシステム定義の端末のため削除できません。
                message.setMsgResourceKey("6403018");
                //クリア処理
                btn_Clear_Click(e);
                return;
            }

            //削除中を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_DELETE);

            // ボタンの使用不可を設定
            changebtn();
        }
        finally
        {
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
        String target_Not = String.valueOf(EmConstants.ROLE_TARGET_USER);
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
            //端末№入力チェック(必須入力項目)
            txt_R_TerminalNumber.validate();

            String terminalnumber = txt_R_TerminalNumber.getText();
            String terminalname = txt_TerminalName.getText();
            String ipaddress = this.rdo_SpecifiesIPAddress.getChecked() ? this.txt_IpAddress.getText()
                                                                       : this.txt_HostName.getText();
            String roleid = txt_RoleId.getText();
            String printername = txt_PrinterName.getText();
            boolean isAutoLogin = this.rdo_AutoLoginON.getChecked();

            //コネクション取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);

            //処理区分
            String proc = this.getViewState().getString(VSKEY_PROCESS_KEY);

            //完了時のメッセージ
            String comp_msg = "";

            // 端末切替一覧
            String[] changeTerminalNumberArray = getStringArray(getViewState().getString(SELECTED_TERMINAL_NO));
            // 端末ユーザ一覧
            String[] userIdArray = getStringArray(getViewState().getString(SELECTED_USER_NO));

            // 操作区分
            int operationType = 0;

            //処理区分が押下されていない場合
            if (proc == null)
            {
                return;
            }
            //登録時
            else if (proc.equals(VSKEY_INSERT))
            {
                // 登録
                operationType = EmConstants.OPELOG_CLASS_REGIST;

                // 端末名
                txt_TerminalName.validate(this, true);
                if (this.rdo_SpecifiesIPAddress.getChecked())
                {
                    // IPアドレス
                    this.txt_IpAddress.validate(this, true);
                }
                else
                {
                    // ホスト名
                    this.txt_HostName.validate(this, true);
                }
                // ロールID
                txt_RoleId.validate(this, true);
                // プリンタ名
                txt_PrinterName.validate(this, false);
                //IPアドレスをチェック
                if (ipaddress.equals(EmConstants.UNDEFINED_TERMINAL) || ipaddress.equals(SYSTEM_TERMINAL_IPADDRESS))
                {
                    //入力された端末IPアドレスはシステム定義で使用されているため登録できません。
                    message.setMsgResourceKey("6403041");
                    if (this.rdo_SpecifiesIPAddress.getChecked())
                    {
                        setFocus(txt_IpAddress);
                    }
                    else
                    {
                        setFocus(txt_HostName);
                    }
                    return;
                }

                // IPアドレスの形式チェック
                if (this.rdo_SpecifiesIPAddress.getChecked() && !this.isIpAddress(ipaddress))
                {
                    // IPアドレスの形式が正しくありません
                    message.setMsgResourceKey("6403061");
                    setFocus(txt_IpAddress);
                    return;
                }

                // ロールIDの存在チェック
                if (!isRegisteredRoleId(roleid, conn))
                {
                    // このロールIDは登録されていません。
                    message.setMsgResourceKey("6403066");
                    setFocus(txt_RoleId);
                    return;
                }

                // アドレスの存在チェック
                if (isRegisteredTerminalAddress(ipaddress, terminalHandler))
                {
                    // 指定されたアドレスを持つ端末が既に登録されています。
                    message.setMsgResourceKey("6403067");
                    if (this.rdo_SpecifiesIPAddress.getChecked())
                    {
                        setFocus(txt_IpAddress);
                    }
                    else
                    {
                        setFocus(txt_HostName);
                    }
                    return;
                }

                // 同一端末チェック
                if (terminalHandler.findByTerminalNumber(terminalnumber) != null)
                {
                    //すでに同一の端末№が登録されています。
                    message.setMsgResourceKey("6403009");
                    return;
                }

                // 新規Terminalエンティティを取得
                jp.co.daifuku.emanager.database.entity.Terminal terminal = createTerminalEntity(UPDATE_KIND_INSERT);

                // 切替端末として指定された端末No.がこの時点でDBに登録されていなければ切替端末として指定した配列から削除する
                changeTerminalNumberArray = this.deleteNotRegistTerminalNumber(changeTerminalNumberArray, conn);
                // 切替ユーザとして指定されたユーザIDがこの時点でDBに登録されていなければ切替ユーザとして指定した配列から削除する
                userIdArray = this.deleteNotRegistUserId(userIdArray, conn);

                // 各情報をセットする
                terminal.setTerminalNumber(terminalnumber);
                terminal.setTerminalName(terminalname);
                terminal.setTerminalAddress(ipaddress);
                terminal.setRoleId(roleid);
                terminal.setPrinterName(printername);
                terminal.setAutoLoginFlag(isAutoLogin);
                terminal.setChangneTerminalNumbeArray(changeTerminalNumberArray);
                terminal.setUserIdArray(userIdArray);

                // TerminalをDBに新規登録
                this.insertTerminal(terminal, conn);

                //6401003=登録しました。
                comp_msg = "6401003";
            }
            //修正時
            else if (proc.equals(VSKEY_MODIFY))
            {
                // 修正
                operationType = EmConstants.OPELOG_CLASS_MODIFY;

                // 端末名
                txt_TerminalName.validate(this, true);
                if (this.rdo_SpecifiesIPAddress.getChecked())
                {
                    // IPアドレス
                    this.txt_IpAddress.validate(this, true);
                }
                else
                {
                    // ホスト名
                    this.txt_HostName.validate(this, true);
                }
                // ロールID
                txt_RoleId.validate(this, true);
                // プリンタ名
                txt_PrinterName.validate(this, false);

                if (terminalHandler.findByTerminalNumber(terminalnumber) == null)
                {
                    //指定された端末№は登録されていません。
                    message.setMsgResourceKey("6403010");
                    return;
                }

                if (!isRegisteredRoleId(roleid, conn))
                {
                    // このロールIDは登録されていません。
                    message.setMsgResourceKey("6403066");
                    setFocus(txt_RoleId);
                    return;
                }

                // IPアドレスの形式チェック
                if (this.rdo_SpecifiesIPAddress.getChecked() && !this.isIpAddress(ipaddress))
                {
                    // IPアドレスの形式が正しくありません
                    message.setMsgResourceKey("6403061");
                    setFocus(txt_IpAddress);
                    return;
                }

                // アドレスの存在チェック
                if (isRegisteredTerminalAddress(ipaddress, terminalnumber, terminalHandler))
                {
                    // 指定されたアドレスを持つ端末が既に登録されています。
                    message.setMsgResourceKey("6403067");
                    if (this.rdo_SpecifiesIPAddress.getChecked())
                    {
                        setFocus(txt_IpAddress);
                    }
                    else
                    {
                        setFocus(txt_HostName);
                    }
                    return;
                }

                // 切替端末として指定された端末No.がこの時点でDBに登録されていなければ切替端末として指定した配列から削除する
                changeTerminalNumberArray = this.deleteNotRegistTerminalNumber(changeTerminalNumberArray, conn);
                // 切替ユーザとして指定されたユーザIDがこの時点でDBに登録されていなければ切替ユーザとして指定した配列から削除する
                userIdArray = this.deleteNotRegistUserId(userIdArray, conn);

                // 新規Terminalエンティティを取得
                jp.co.daifuku.emanager.database.entity.Terminal terminal = createTerminalEntity(UPDATE_KIND_UPDATE);

                // 各情報をセットする
                terminal.setTerminalNumber(terminalnumber);
                terminal.setTerminalName(terminalname);
                terminal.setTerminalAddress(ipaddress);
                terminal.setRoleId(roleid);
                terminal.setPrinterName(printername);
                terminal.setAutoLoginFlag(isAutoLogin);
                terminal.setChangneTerminalNumbeArray(changeTerminalNumberArray);
                terminal.setUserIdArray(userIdArray);

                if (this.updateTerminal(terminal, conn) == 0)
                {
                    //指定された端末№は登録されていません。
                    message.setMsgResourceKey("6403010");
                    return;
                }

                //6401004=修正しました。
                comp_msg = "6401004";
            }
            //削除時
            else if (proc.equals(VSKEY_DELETE))
            {
                // 削除
                operationType = EmConstants.OPELOG_CLASS_DELETE;

                if (terminalHandler.findByTerminalNumber(terminalnumber) == null)
                {
                    //指定された端末№は登録されていません。
                    message.setMsgResourceKey("6403010");
                    return;
                }

                if (ipaddress.equals(EmConstants.UNDEFINED_TERMINAL) || ipaddress.equals(SYSTEM_TERMINAL_IPADDRESS))
                {
                    //指定された端末№はシステム定義の端末のため削除できません。
                    message.setMsgResourceKey("6403018");
                    return;
                }


                if (this.deleteTerminal(terminalnumber, conn) == 0)
                {
                    //指定された端末№は登録されていません。
                    message.setMsgResourceKey("6403010");
                    return;
                }


                //6401005=削除しました。
                comp_msg = "6401005";
            }

            // Part11ログ出力
            writerPart11Log(conn, operationType, changeTerminalNumberArray, userIdArray);

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
                // 端末を登録しました。({0})
                msg = "6400023";
            }
            //修正時
            else if (proc.equals(VSKEY_MODIFY))
            {
                // 端末を修正しました。({0})
                msg = "6400024";
            }
            //削除時
            else if (proc.equals(VSKEY_DELETE))
            {
                // 端末を削除しました。({0})
                msg = "6400025";
            }

            msg += Message.MSG_DELIM + getKeyEqualValueString(EmTableColumns.TERMINAL_TERMINALNUMBER, terminalnumber);
            EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_TERMINAL, msg, "");
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
     * Part11ログ出力を行います。
     * 
     * @param conn DBコネクション
     * @param operationType 操作区分
     * @param changeTerminalNumberArray 切替端末一覧
     * @param userIdArray 端末ユーザ一覧
     * @throws SQLException
     */
    private void writerPart11Log(Connection conn, int operationType, String[] changeTerminalNumberArray,
            String[] userIdArray)
            throws SQLException
    {
        P11LogWriter logWriter = new P11LogWriter(conn);

        DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();

        //項目リストの生成
        List list = new ArrayList();
        list.add(txt_R_TerminalNumber.getText());
        list.add(txt_TerminalName.getText());
        list.add(rdo_SpecifiesIPAddress.getChecked() ? txt_IpAddress.getText()
                                                    : txt_HostName.getText());
        list.add(txt_RoleId.getText());
        list.add(txt_PrinterName.getText());
        list.add(rdo_AutoLoginON.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                             : String.valueOf(EmConstants.DB_FLAG_FALSE));

        // オペレーションログ出力
        logWriter.createOperationLog(userInfo, operationType, list);

        if (changeTerminalNumberArray != null && changeTerminalNumberArray.length > 0)
        {
            TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);

            for (int i = 0; changeTerminalNumberArray.length > i; i++)
            {
                jp.co.daifuku.emanager.database.entity.Terminal terminal =
                        terminalHandler.findByTerminalNumber(changeTerminalNumberArray[i]);

                List list2 = new ArrayList();

                list2.addAll(list);
                list2.add(terminal.getTerminalNumber());
                list2.add(terminal.getTerminalName());
                list2.add(terminal.getTerminalAddress());
                list2.add(terminal.getRoleId());
                list2.add(terminal.getPrinterName());

                //オペレーションログ出力
                logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_TERMINAL_SWITCH, list2);
            }
        }

        if (userIdArray != null && userIdArray.length > 0)
        {
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);

            for (int i = 0; userIdArray.length > i; i++)
            {
                User user = userHandler.findByUserId(userIdArray[i]);

                List list2 = new ArrayList();

                list2.addAll(list);
                list2.add(user.getUserID());
                list2.add(user.getUserName());
                list2.add(user.getRoleID());

                //オペレーションログ出力
                logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_TERMINAL_USER, list2);
            }
        }
    }

    /**
     * 引数で渡されたアドレスを持つ端末が既に登録されているかをチェックします。
     * 同時に渡される端末No.を持つ端末はチェックする対象から除外します。
     * （引数で渡されたアドレスを持つ端末が存在してもその端末の端末No.が引数で
     * 渡したものと一致するならその端末は一致したとみなさない）
     * @param ipAddress 存在をチェックするアドレス
     * @param terminalNumber 存在チェックから除外する端末No.
     * @param terminalHandler 端末ハンドラ
     * @return 登録されていればtrue　登録されていなければfalse
     * @throws SQLException
     */
    private boolean isRegisteredTerminalAddress(String ipAddress, String terminalNumber, TerminalHandler terminalHandler)
            throws SQLException
    {
        jp.co.daifuku.emanager.database.entity.Terminal terminal = terminalHandler.findByAddress(ipAddress);

        if (terminal == null || terminal.getTerminalNumber().equals(terminalNumber))
        {
            return false;
        }

        return true;
    }

    /**
     * 引数で渡されたアドレスを持つ端末が既に登録されているかをチェックします。
     * 登録されていればtrue　登録されていなければfalse
     * @param ipAddress
     * @param terminalHandler
     * @return チェック結果
     * @throws SQLException
     */
    private boolean isRegisteredTerminalAddress(String ipAddress, TerminalHandler terminalHandler)
            throws SQLException
    {
        return terminalHandler.findByAddress(ipAddress) != null;
    }

    /**
     * 引数に渡されたユーザIDの中で、このメソッドを実行した時点でDBに登録されていないものは配列から削除する
     * @param userIdArray
     * @param conn
     * @return 結果の配列
     * @throws SQLException
     */
    private String[] deleteNotRegistUserId(String[] userIdArray, Connection conn)
            throws SQLException
    {
        // 引数で渡されたユーザIDの配列がnullの場合長さが０のString[]を返す
        if (userIdArray == null)
        {
            return new String[0];
        }
        // 引数で渡されたユーザIDの配列の長さが０の場合その配列をそのまま返す
        if (userIdArray.length == 0)
        {
            return userIdArray;
        }

        // ユーザハンドラ取得
        UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);
        // ユーザ一覧を取得
        jp.co.daifuku.emanager.database.entity.User[] userArr = userHandler.findAll();
        // DBより取得したユーザの一覧が存在しなかった場合長さが０のString[]を返す
        if (userArr == null || userArr.length == 0)
        {
            return new String[0];
        }
        // 一時格納リスト
        ArrayList list = new ArrayList();

        for (int i = 0; i < userIdArray.length; i++)
        {
            for (int j = 0; j < userArr.length; j++)
            {
                // 切替ユーザIDとして指定したユーザIDを持つユーザがDBに存在するかをチェック
                if (userIdArray[i].equals(userArr[j].getUserID()))
                {
                    // 一時格納リストに格納
                    list.add(userIdArray[i]);
                    break;
                }
            }
        }
        // 一時格納リストをString[]に変換して返す
        return (String[])list.toArray(new String[0]);
    }

    /**
     * 引数に渡された端末No.の中で、このメソッドを実行した時点でDBに存在しない端末No.のものは配列から削除する
     * @param changeTerminalNumberArray
     * @param conn
     * @return 結果の配列
     * @throws SQLException
     */
    private String[] deleteNotRegistTerminalNumber(String[] changeTerminalNumberArray, Connection conn)
            throws SQLException
    {
        // 引数で渡された端末No.の配列がnullの場合、配列が０のString[]を返す
        if (changeTerminalNumberArray == null)
        {
            return new String[0];
        }
        // 引数で渡された端末No.の配列の長さが０の場合その配列をそのまま返す
        if (changeTerminalNumberArray.length == 0)
        {
            return changeTerminalNumberArray;
        }

        // 端末ハンドラ取得
        TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);
        // 端末一覧を取得
        jp.co.daifuku.emanager.database.entity.Terminal[] terminalArr = terminalHandler.findAll(0);

        // DBより取得した端末の一覧が無かった場合は登録できる切替端末は存在しないので長さが０のString[]を返す
        if (terminalArr == null || terminalArr.length == 0)
        {
            return new String[0];
        }

        // 一時格納リスト
        ArrayList list = new ArrayList();

        for (int i = 0; i < changeTerminalNumberArray.length; i++)
        {
            for (int j = 0; j < terminalArr.length; j++)
            {
                // 切替端末No.がDB登録端末に存在するかをチェック
                if (changeTerminalNumberArray[i].equals(terminalArr[j].getTerminalNumber()))
                {
                    // 存在した場合は一時格納リストに格納
                    list.add(changeTerminalNumberArray[i]);
                    break;
                }
            }
        }
        // 一時格納リストをString[]に変換して返す
        return (String[])list.toArray(new String[0]);
    }

    /**
     * 引数で渡したロールIDが登録されているかをチェックします。
     * 登録されていればtrue　登録されていなければfalse
     * @param roleid チェックしたいロールID
     * @param conn Connectionオブジェクト
     * @return 登録されていればtrue　登録されていなければfalse
     * @throws SQLException
     */
    private boolean isRegisteredRoleId(String roleid, Connection conn)
            throws SQLException
    {
        return EmHandlerFactory.getRoleHandler(conn).findByRoleId(roleid) != null;
    }

    /**
     * Message.MSG_DELIMで区切られた文字列をMessage.MSG_DELIM(\t)の部分で分割し、配列にして返します。
     * 引数に渡されたStringがnullの場合、長さが０のString[]を返します。
     * 例）"A\tB\tC" →　String[]{a, b, c}
     * @param terminalChangeString
     * @return 結果の配列
     */
    private String[] getStringArray(String terminalChangeString)
    {
        return terminalChangeString != null && !"".equals(terminalChangeString) ? terminalChangeString.split(Message.MSG_DELIM)
                                                                               : new String[0];
    }

    /**
     * Terminalエンティティの情報を新たにデータベースに新規登録します。
     * @param terminal
     * @param conn
     * @throws SQLException
     */
    private void insertTerminal(jp.co.daifuku.emanager.database.entity.Terminal terminal, Connection conn)
            throws SQLException
    {
        // 各ハンドラを取得
        TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);
        TerminalChangeMapHandler terminalChangeMapHandler = EmHandlerFactory.getTerminalChangeMapHandler(conn);
        TerminalUserMapHandler terminalUserMapHandler = EmHandlerFactory.getTerminalUserMapHandler(conn);

        // Terminal情報を新規登録
        terminalHandler.createTerminal(terminal);

        terminal.setChangneTerminalNumbeArray(deleteNullAndEmpty(terminal.getChangneTerminalNumbeArray()));
        terminal.setUserIdArray(deleteNullAndEmpty(terminal.getUserIdArray()));

        // 切替端末が設定されているか
        if (terminal.getChangneTerminalNumbeArray() != null && 0 < terminal.getChangneTerminalNumbeArray().length)
        {
            terminal.setIsChangneTerminalNumbeArray(true);
            terminalChangeMapHandler.createTerminalChangeMap(terminal);
        }

        // 切替ユーザが設定されているか
        if (terminal.getUserIdArray() != null && 0 < terminal.getUserIdArray().length)
        {
            terminal.setIsUserIdArray(true);
            terminalUserMapHandler.createTerminalUserMap(terminal);
        }
    }

    /**
     * 新しくTerminalエンティティを作成して返します。
     * @param updateKind 更新区分（1:新規　2:更新）
     * @return Terminal
     */
    private jp.co.daifuku.emanager.database.entity.Terminal createTerminalEntity(int updateKind)
    {
        jp.co.daifuku.emanager.database.entity.Terminal terminal =
                new jp.co.daifuku.emanager.database.entity.Terminal();

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
            terminal.setUpdateUser(userhandler.getUserID());
        }

        terminal.setUpdateTerminal(this.getHttpRequest().getRemoteAddr());

        terminal.setUpdateProcess(this.getClass().getName());
        terminal.setUpdateKind(updateKind);

        return terminal;
    }

    /**
     * Terminalエンティティの情報をDBに更新します。
     * @param terminal
     * @param conn
     * @return int 更新に成功した件数
     * @throws SQLException
     */
    private int updateTerminal(jp.co.daifuku.emanager.database.entity.Terminal terminal, Connection conn)
            throws SQLException
    {
        // 各ハンドラを取得
        TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);
        TerminalChangeMapHandler terminalChangeMapHandler = EmHandlerFactory.getTerminalChangeMapHandler(conn);
        TerminalUserMapHandler terminalUserMapHandler = EmHandlerFactory.getTerminalUserMapHandler(conn);

        // 更新フラグをtrueに
        terminal.setIsChangneTerminalNumbeArray(true);
        terminal.setIsUserIdArray(true);

        // 端末情報を更新
        int result = terminalHandler.updateByTerminalNumber(terminal);

        // 端末切替情報をいったん削除
        terminalChangeMapHandler.deleteTerminalChangeMap(terminal.getTerminalNumber());

        // 端末ユーザ情報をいったん削除
        terminalUserMapHandler.deleteTerminalUserMap(terminal.getTerminalNumber());

        if (result == 0)
        {
            return result;
        }

        // 端末切替一覧の配列の要素からnullと空文字を削除する
        terminal.setChangneTerminalNumbeArray(deleteNullAndEmpty(terminal.getChangneTerminalNumbeArray()));
        // 端末ユーザ一覧の配列の要素からnullと空文字を削除する
        terminal.setUserIdArray(deleteNullAndEmpty(terminal.getUserIdArray()));

        // 端末切替一覧の配列がnullか長さが０でなければ配列の中身を登録
        if (terminal.getChangneTerminalNumbeArray() != null && 0 < terminal.getChangneTerminalNumbeArray().length)
        {
            terminalChangeMapHandler.createTerminalChangeMap(terminal);
        }

        // 端末ユーザ一覧の配列がnullか長さが０でなければ配列の中身を登録
        if (terminal.getUserIdArray() != null && 0 < terminal.getUserIdArray().length)
        {
            terminalUserMapHandler.createTerminalUserMap(terminal);
        }

        return result;
    }

    /**
     * String[]の要素からnullと空文字を削除した配列を返します。
     * 引数にnullが渡されれば長さが０の配列を返します。引数で渡された配列の長さが０なら長さが０の配列を返します。
     * 引数で渡された配列の要素からnullと空文字を削除した結果、要素が０になった場合、長さが０の配列を返します。
     * @param stringArr 文字列配列
     * @return String[]
     */
    private String[] deleteNullAndEmpty(String[] stringArr)
    {
        // nullと空文字を除く文字列を格納する配列
        String[] newStrArr = null;

        // 引数で渡された配列がnullならnullを返して終了
        if (stringArr != null)
        {
            // 現配列の長さを初期値とする
            int count = stringArr.length;

            for (int i = 0; i < stringArr.length; i++)
            {
                // 配列の要素を取得
                String str = stringArr[i];

                // 取得した要素がnullか空文字ならばカウントをデクリメント
                if (str == null || "".equals(str))
                {
                    count--;
                }
            }

            // countの値が配列の長さと同じならば配列内にnullおよび空文字がないのでそのまま配列を返す。
            if (count == stringArr.length)
            {
                return stringArr;
            }

            // 配列からnullと空文字を削除した結果countが1より小さければ
            // 配列内にはnullと空文字以外の要素は無かったことになるので長さが0の配列を返す
            if (count < 1)
            {
                return new String[0];
            }
            // nullと空文字以外の要素があった場合
            else
            {
                // nullと空文字を抜いた要素数で新たに配列を作成
                newStrArr = new String[count];
                // 新しい配列に要素を追加するときに添字として使用
                int i = 0;

                for (int j = 0; j < stringArr.length; j++)
                {
                    // 配列の要素がnullか空文字でなければそれを新しい配列に格納しなおす
                    if (stringArr[j] != null && !"".equals(stringArr[j]))
                    {
                        newStrArr[i] = stringArr[j];
                        i++;
                    }
                }

                // 新たに作成した配列を返す
                return newStrArr;
            }
        }
        else
        {
            // 長さが0の配列を返す
            return new String[0];
        }
    }


    /**
     * エンティティの情報をDBから削除します。
     * @param terminalNumber
     * @param conn Connection
     * @return 削除件数
     * @throws SQLException
     */
    private int deleteTerminal(String terminalNumber, Connection conn)
            throws SQLException
    {
        // 各ハンドラを取得
        TerminalHandler terminalHandler = EmHandlerFactory.getTerminalHandler(conn);
        TerminalChangeMapHandler terminalChangeMapHandler = EmHandlerFactory.getTerminalChangeMapHandler(conn);
        TerminalUserMapHandler terminalUserMapHandler = EmHandlerFactory.getTerminalUserMapHandler(conn);

        // 端末情報を削除
        int result = terminalHandler.deleteTerminal(terminalNumber);

        // 端末切替情報を削除
        terminalChangeMapHandler.deleteTerminalChangeMap(terminalNumber);

        // 端末ユーザ情報を削除
        terminalUserMapHandler.deleteTerminalUserMap(terminalNumber);

        return result;
    }

// 2008/12/25 K.Matsuda Start 赤*を非表示
    /**
     * 必須項目のマーク(*)を非表示にします
     */
    private void setVisibleRequireMarks(boolean visible)
    {
        // *を非表示にする
        lbl_RequireTerminalNumber.setVisible(visible);
        lbl_RequireTerminalName.setVisible(visible);
        lbl_RequireIpAddress.setVisible(visible);
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
        //端末№テキストボックスをクリア
        txt_R_TerminalNumber.setText("");
        //端末名テキストボックスをクリア
        txt_TerminalName.setText("");
        //端末IPアドレステキストボックスをクリア        
        txt_IpAddress.setText("");
        //ロールIDテキストボックスをクリア        
        txt_RoleId.setText("");
        //プリンタ名テキストボックスをクリア        
        txt_PrinterName.setText("");
        //ロールID検索ボタンを無効
        btn_P_Search2.setEnabled(false);
        //端末名テキストボックスを読み取り専用にセット
        txt_TerminalName.setReadOnly(true);
        //端末IPアドレステキストボックスを読み取り専用にセット
        txt_IpAddress.setReadOnly(true);
        //ロールIDテキストボックスを読み取り専用にセット
        txt_RoleId.setReadOnly(true);
        //プリンタ名テキストボックスを読み取り専用にセット
        txt_PrinterName.setReadOnly(true);
        // ホスト名テキストボックス内をクリア
        this.txt_HostName.setText("");
        // ホスト名テキストボックスを読み取り専用にセット
        this.txt_HostName.setReadOnly(true);
        // ホスト名ラジオボタンを読み取り専用にセット
        this.rdo_SpecifiesHostName.setEnabled(false);
        // IPアドレスラジオボタンを読み取り専用にセット
        this.rdo_SpecifiesIPAddress.setEnabled(false);
        // 自動ログインオンラジオボタンを読み取り専用にセット
        this.rdo_AutoLoginON.setEnabled(false);
        // 自動ログインオフラジオボタンを読み取り専用にセット
        this.rdo_AutoLoginOFF.setEnabled(false);
        // 端末切替ボタンを無効化
        this.btn_TerminalSwitch.setEnabled(false);
        // 端末ユーザ切替ボタンを無効化
        this.btn_TerminalUser.setEnabled(false);

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

        // ViewStateから選択中の切替端末と端末ユーザの情報をクリアする
        getViewState().setString(SELECTED_TERMINAL_NO, null);
        getViewState().setString(SELECTED_USER_NO, null);
    }

    /** 
     * すでに同一の端末№が登録済みかを確認します。
     * 登録済みならtrue　登録されていなければfalse
     * @param terminalNumber  TerminalNumber
     * @param conn Connection
     * @return boolean
     * @throws Exception 
     */
    private boolean isDefined(String terminalNumber, Connection conn)
            throws Exception
    {
        // terminalNumberをもとに端末エンティティが取得できればtrueできなければfalse
        return EmHandlerFactory.getTerminalHandler(conn).findByTerminalNumber(terminalNumber) != null;
    }

    /** 
     * 修正、登録ボタン押下時に、DBの項目を各コントロールへマッピングします。
     * @param terminal Terminal エンティティ
     * @throws Exception 
     */
    private void mapping(jp.co.daifuku.emanager.database.entity.Terminal terminal)
            throws Exception
    {
        //登録済みの端末№がある場合
        if (terminal != null)
        {
            String terminalname = terminal.getTerminalName();

            String address = terminal.getTerminalAddress();
            if (isIpAddress(address))
            {
                this.rdo_SpecifiesIPAddress.setChecked(true);
                this.rdo_SpecifiesHostName.setChecked(false);
                this.txt_IpAddress.setText(address);
            }
            else
            {
                this.rdo_SpecifiesIPAddress.setChecked(false);
                this.rdo_SpecifiesHostName.setChecked(true);
                this.txt_HostName.setText(address);
            }

            String roleid = terminal.getRoleId();
            String printername = terminal.getPrinterName();

            this.txt_R_TerminalNumber.setText(txt_TerminalNumber.getText());
            this.txt_TerminalName.setText(terminalname);

            this.txt_RoleId.setText(roleid);
            this.txt_PrinterName.setText(printername);

            this.rdo_AutoLoginON.setChecked(terminal.getAutoLoginFlag());
            this.rdo_AutoLoginOFF.setChecked(!terminal.getAutoLoginFlag());
        }
    }


    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_SpecifiesHostName_Click(ActionEvent e)
            throws Exception
    {
        if (rdo_SpecifiesHostName.getChecked())
        {
            this.txt_IpAddress.setReadOnly(true);
            this.txt_HostName.setReadOnly(false);
        }
        else
        {
            this.txt_IpAddress.setReadOnly(false);
            this.txt_HostName.setReadOnly(true);
        }
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_SpecifiesIPAddress_Click(ActionEvent e)
            throws Exception
    {
        if (this.rdo_SpecifiesIPAddress.getChecked())
        {
            this.txt_IpAddress.setReadOnly(false);
            this.txt_HostName.setReadOnly(true);
        }
        else
        {
            this.txt_IpAddress.setReadOnly(true);
            this.txt_HostName.setReadOnly(false);
        }
    }


    /** 
     * 切替端末設定ボタンを押下したとき
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_TerminalSwitch_Click(ActionEvent e)
            throws Exception
    {
        //ロール一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();
        param.setParameter(PARENT_TERMINAL_NO, this.txt_R_TerminalNumber.getText());
        param.setParameter(SELECTED_TERMINAL_NO, getStringArray(getViewState().getString(SELECTED_TERMINAL_NO)));

        //処理中画面->結果画面
        redirect("/emanager/setting/user/listbox/TerminalChangeList.do", param, "/Progress.do");
    }


    /** 
     * 切替ユーザ設定ボタンを押下したとき
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_TerminalUser_Click(ActionEvent e)
            throws Exception
    {
        //ロール一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();
        param.setParameter(SELECTED_USER_NO, getStringArray(getViewState().getString(SELECTED_USER_NO)));

        //処理中画面->結果画面
        redirect("/emanager/setting/user/listbox/TerminalUserList.do", param, "/Progress.do");
    }

    /**
     * 文字列が正規表現を使ってIPアドレスであるかをチェックします。
     * @param ip
     * @return IPアドレスならtrue
     */
    private boolean isIpAddress(String ip)
    {
        String reg = "(25[0-5]|(2[0-4]|1[0-9]|[1-9])?[0-9])";
        String addr = reg + "\\." + reg + "\\." + reg + "\\." + reg;
        Pattern pattern = Pattern.compile(addr);
        Matcher matcher = pattern.matcher(ip);
        boolean bool = matcher.matches();
        return bool;
    }

    /**
     * key=value の形式で連結した文字列を返却します。
     * @param key key
     * @param value value
     * @return 連結した文字列
     */
    private String getKeyEqualValueString(String key, String value)
    {
        return key + "=" + value;
    }
}
//end of class

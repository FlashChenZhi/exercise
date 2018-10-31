// $Id: Function2Business.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.menu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.util.StringUtils;
import jp.co.daifuku.bluedog.util.Validator;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.entity.Function;
import jp.co.daifuku.emanager.database.handler.AuthenticationHandler;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.EmTableColumns;
import jp.co.daifuku.emanager.database.handler.FunctionHandler;
import jp.co.daifuku.emanager.database.handler.RoleFunctionMapHandler;
import jp.co.daifuku.emanager.display.web.setting.menu.listbox.ResourceListBusiness;
import jp.co.daifuku.emanager.util.DispResourceMap;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

/**
 * <jp> サブメニューボタン設定2画面目の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>
 * <TD>2004/12/1</TD>
 * <TD>T.Torigaki(DFK)</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author $Author: admin $ </jp>
 */
/**
 * <en> This screen class is created by the screen generator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>
 * <TD>2004/12/1</TD>
 * <TD>T.Torigaki(DFK)</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author $Author: admin $ </en>
 */
public class Function2Business
        extends Function2
        implements EmConstants
{
    /**
     * DS番号チェック済みかどうか
     */
    private boolean dsNoChecked = false;

    /**
     * DS番号確認ダイアログ用フラグのキー
     */
    private static final String DS_CONFIRM_KEY = "DS_CONFIRM_KEY";

    /**
     * DS番号確認ダイアログフラグ(ダイアログ1回目)
     */
    private static final String CONFIRM1 = "CONFIRM1";

    /**
     * DS番号確認ダイアログフラグ(ダイアログ2回目)
     */
    private static final String CONFIRM2 = "CONFIRM2";

    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------


    /* (non-Javadoc)
     * @see jp.co.daifuku.bluedog.ui.control.Page#page_ConfirmBack(jp.co.daifuku.bluedog.webapp.ActionEvent)
     */
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // DS番号チェック済みのフラグを立てる
        dsNoChecked = true;

        // 「入力されたDS番号はページ名{0}として登録済みです。登録済みのページ名で登録しますか？」(1回目に表示されるダイアログ)
        if (CONFIRM1.equals(getViewState().getString(DS_CONFIRM_KEY)))
        {
            getViewState().setString(DS_CONFIRM_KEY, "");

            boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();
            // キャンセルの場合
            if (!isExecute)
            {
                // 「入力されたページ名で以前登録した同一DS番号のページ名を上書きしますか？」
                setConfirm("MSG-T0055", true, true);
                getViewState().setString(DS_CONFIRM_KEY, CONFIRM2);
                return;
            }
            // OKの場合
            else
            {
                Connection conn = null;
                try
                {
                    conn = EmConnectionHandler.getPageDbConnection(this);

                    FunctionHandler functionHandler = EmHandlerFactory.getFunctionHandler(conn);
                    // DS番号を条件にCom_Functionテーブルを検索
                    Function[] functions = functionHandler.findByDsNo(txt_DsNo.getText());
                    // 登録済みのページ名を設定する
                    String pageName = DispResourceMap.getText(functions[0].getPageResourceKey());
                    txt_PageNameResouceKey.setText(functions[0].getPageResourceKey());
                    txt_R_PageName.setText(pageName);

                    updateFunction(conn);
                }
                finally
                {
                    EmConnectionHandler.closeConnection(conn);
                }
            }
        }
        // 「入力されたページ名で以前登録した同一DS番号のページ名を上書きしますか？」(2回目に表示されるダイアログ)
        else if (CONFIRM2.equals(getViewState().getString(DS_CONFIRM_KEY)))
        {
            getViewState().setString(DS_CONFIRM_KEY, "");

            boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();
            // キャンセルの場合
            if (!isExecute)
            {
                return;
            }
            // OKの場合
            else
            {
                Connection conn = null;
                try
                {
                    conn = EmConnectionHandler.getPageDbConnection(this);

                    FunctionHandler functionHandler = EmHandlerFactory.getFunctionHandler(conn);

                    Function function = new Function();
                    // ページ名リソースキー
                    function.setPageResourceKey(txt_PageNameResouceKey.getText());
                    // DS番号
                    function.setDsNumber(txt_DsNo.getText());
                    // 更新ユーザ
                    function.setUpdateUser(((DfkUserInfo)getUserInfo()).getUserId());
                    // 更新端末IP
                    function.setUpdateTerminal(getHttpRequest().getRemoteAddr());
                    // 更新区分(1:登録、2:修正)
                    function.setUpdateKind(2);
                    // 更新処理名
                    function.setUpdateProcess(this.getClass().getName());

                    //指定されたDS番号のレコードのページ名リソースキーを更新する
                    functionHandler.updatePageNameResourceKeyByDsNo(function);

                    updateFunction(conn);
                }
                catch (Exception ex)
                {
                    EmConnectionHandler.rollback(conn);
                }
                finally
                {
                    EmConnectionHandler.closeConnection(conn);
                }
            }
        }
    }

    /**
     * 各コントロールイベント呼び出し前に呼び出されます。
     * 
     * @param e
     *            ActionEvent
     * @exception Exception
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        // ViewStateから取得
        String menuparam = this.viewState.getString(MENUPARAM);

        if (menuparam != null)
        {
            //<jp>パラメータを取得</jp><en> A parameter is acquired. </en>
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);

            //<jp>画面名称をセットする</jp><en> A screen name is set. </en>
            lbl_SettingName.setResourceKey(title);

            //<jp>ヘルプファイルへのパスをセットする</jp><en> The path to a help file is set. </en>
            btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()));
        }

        setFocus(txt_ButtonResourceKey);
    }

    /**
     * 画面の初期化を行います。
     * 
     * @param e
     *            ActionEvent
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
        
        // DS番号確認ダイアログフラグのクリア
        getViewState().setString(DS_CONFIRM_KEY, "");

        // タブを選択状態に
        tab.setSelectedIndex(2);

        Connection conn = null;

        try
        {
            // コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);


            // 機能IDの値を判定
            // １画面目で未登録のボタンを押下した場合
            if (this.getViewState().getString("FunctionId") == null
                    || this.getViewState().getString("FunctionId").equals(""))
            {
                btn_BC_Delete.setEnabled(false);

                // メイン機能IDを取得
                String mainfunctionid = this.getViewState().getString("MainFunctionId");
                // メイン機能IDを数値に変換
                int int_mainfunctionid = Integer.parseInt(mainfunctionid);

                // 選択列を取得
                String col = this.getViewState().getString("ActiveCol");
                // 数値に変換
                int int_col = Integer.parseInt(col) - 1;

                // 機能IDを生成
                // 10101 = メイン機能ID + 選択列
                int int_functionid = int_mainfunctionid + int_col;
                // 文字列に変換
                String[] functionid = new String[1];
                functionid[0] = Integer.toString(int_functionid);

                // 登録しようとしている機能IDがすでに登録済みか検索
                for (int i = 0; i < 10; i++)
                {
                    // 2006/11/28 安井
                    FunctionHandler functionHandler = EmHandlerFactory.getFunctionHandler(conn);
                    int cnt = functionHandler.findCountByFunctionId(Integer.toString(int_functionid));

                    // すでに登録済みの場合
                    if (cnt > 0)
                    {
                        // 機能IDに１加算
                        int_functionid = int_functionid + 1;
                        // 下１桁が４を超えていた場合
                        if ((int_functionid - int_mainfunctionid) > 4)
                        {
                            // 機能IDの下１桁を１にする
                            int_functionid = int_mainfunctionid + 1;
                        }
                        functionid[0] = Integer.toString(int_functionid);
                    }
                    else
                    {
                        break;
                    }
                }

                // ラベルにセット
                lbl_In_FunctionId.setText(functionid[0]);
                lbl_In_FunctionName.setText(this.getViewState().getString("FunctionName"));
                //				// Frame名に"_self"をセット
                //				txt_FrameName.setText("_self");

                // DS番号の初期値(ゼロ埋)をセット
                txt_DsNo.setText(StringUtils.fillLeft(lbl_In_FunctionId.getText(), "0",
                        Formatter.getInt(txt_DsNo.getMaxLength())));

                // ラジオボタンの初期値を設定
                rdo_Public.setChecked(true);
                rdo_LoginDisableCheck.setChecked(true);

                // 登録中を保持
                this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_INSERT);

            }
            // １画面目で登録済みボタンを押下した場合
            else
            {
                // 機能IDを取得
                String[] functionid = new String[1];
                functionid[0] = this.getViewState().getString("FunctionId");

                // ラベルに１画面目からのパラメータをセット
                lbl_In_FunctionId.setText(functionid[0]);
                lbl_In_FunctionName.setText(this.getViewState().getString("FunctionName"));

                // 2006/11/28 安井
                FunctionHandler functionHandler = EmHandlerFactory.getFunctionHandler(conn);
                Function function = functionHandler.findByFunctionId(functionid[0]);

                if (function != null)
                {
                    // ボタンリソースキーを取得
                    String buttonResourcekey = function.getButtonResourceKey();

                    // ボタン名を取得
                    String buttonName = "";
                    try
                    {
                        buttonName = DispResources.getText(function.getButtonResourceKey());
                    }
                    catch (MissingResourceException ex)
                    {
                        buttonName = DispResources.getText("LBL-T0101");
                    }

                    // ページ名リソースキーを取得
                    String pageNameResourcekey = function.getPageResourceKey();

                    // ページ名を取得
                    String pageName = "";
                    try
                    {
                        pageName = DispResources.getText(function.getPageResourceKey());
                    }
                    catch (MissingResourceException ex)
                    {
                        pageName = DispResources.getText("LBL-T0101");
                    }
                    // URIを取得
                    String uri = function.getUrl();

                    // DS番号を取得
                    String dsNo = function.getDsNumber();

                    //					// Frame名を取得
                    //					String frameName = function.getFrameName();

                    // 画面の公開を取得
                    boolean openFlag = function.getHiddenFlag();

                    // 画面の公開を取得
                    boolean loginFlag = function.getDoAuthenticationFlag();

                    // 各テキストボックスにセット
                    txt_ButtonResourceKey.setText(buttonResourcekey);
                    txt_R_ButtonName.setText(buttonName);
                    txt_PageNameResouceKey.setText(pageNameResourcekey);
                    txt_R_PageName.setText(pageName);
                    txt_URI.setText(uri);
                    txt_DsNo.setText(dsNo);
                    //					txt_FrameName.setText(frameName);

                    if (openFlag)
                    {
                        // 非公開
                        rdo_Public.setChecked(false);
                        rdo_Private.setChecked(true);
                    }
                    else
                    {
                        // 公開
                        rdo_Public.setChecked(true);
                        rdo_Private.setChecked(false);
                    }
                    if (loginFlag)
                    {
                        // チェックする
                        rdo_LoginEnableCheck.setChecked(true);
                        rdo_LoginDisableCheck.setChecked(false);
                    }
                    else
                    {
                        // チェックしない
                        rdo_LoginEnableCheck.setChecked(false);
                        rdo_LoginDisableCheck.setChecked(true);
                    }

                    // 修正中を保持
                    this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_MODIFY);
                }
                else
                {
                    // DS番号の初期値(ゼロ埋)をセット
                    txt_DsNo.setText(StringUtils.fillLeft(lbl_In_FunctionId.getText(), "0",
                            Formatter.getInt(txt_DsNo.getMaxLength())));

                    //					// Frame名に"_self"をセット
                    //					txt_FrameName.setText("_self");

                    // ラジオボタンの初期値を設定
                    rdo_Public.setChecked(true);
                    rdo_LoginDisableCheck.setChecked(true);

                    // 登録中を保持
                    this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_INSERT);
                }
            }

            // システム設定を確認
            AuthenticationHandler authenticationHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
            AuthenticationSystem system = authenticationHandler.findAuthenticationSystem();

            // 画面ごとにログインチェックを行なうかのフラグがOFFの時
            if (!system.getScreenLoginCheckFlag())
            {
                rdo_LoginEnableCheck.setEnabled(false);
                rdo_LoginDisableCheck.setEnabled(false);
            }
        }
        catch (NumberFormatException ex)
        {
            // FUNCTION表のMAINFUNCTIONIDに数値以外のデータがセットされているため、機能IDを採番できません。
            message.setMsgResourceKey("6407002");
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
     * ポップアップウインドから、戻ってくるときにこのメソッドが 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
     * 
     * @param e
     *            ActionEvent
     * @exception Exception
     */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        DialogParameters param = ((DialogEvent)e).getDialogParameters();

        // ボタンリソースキーを取得
        String button_resourcekey = param.getParameter(ResourceListBusiness.BUTTONRESOURCE_KEY);
        String button_resourcename = param.getParameter(ResourceListBusiness.BUTTONNAME_KEY);
        // 空ではないときに値をセットする
        if (!Validator.isEmptyCheck(button_resourcekey))
        {
            txt_ButtonResourceKey.setText(button_resourcekey);
            txt_R_ButtonName.setText(button_resourcename);
            setFocus(txt_ButtonResourceKey);
        }

        // ページリソースキーを取得
        String page_resourcekey = param.getParameter(ResourceListBusiness.PAGENAMERESOURCE_KEY);
        String page_resourcename = param.getParameter(ResourceListBusiness.PAGENAME_KEY);
        // 空ではないときに値をセットする
        if (!Validator.isEmptyCheck(page_resourcekey))
        {
            txt_PageNameResouceKey.setText(page_resourcekey);
            txt_R_PageName.setText(page_resourcename);
            setFocus(txt_PageNameResouceKey);
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Event handler methods -----------------------------------------

    /**
     * メニューへボタンが押下されたときに呼ばれます
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        // ViewStateから取得
        String menuparam = this.viewState.getString(MENUPARAM);

        //<jp>パラメータを取得</jp><en> A parameter is acquired. </en>
        String menuID = CollectionUtils.getMenuParam(2, menuparam);

        // メニューのIDを設定
        forward(BusinessClassHelper.getSubMenuPath(menuID));
    }

    /**
     * ボタン名検索ボタンが押下されたときに呼ばれます。
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void btn_P_ButtonResouceKey_Click(ActionEvent e)
            throws Exception
    {
        // リソース一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();
        param.setParameter(ResourceListBusiness.RESOURCE_KEY, txt_ButtonResourceKey.getText());
        param.setParameter(ResourceListBusiness.BTNFLAG_KEY, btn_P_ButtonResouceKey.getId());

        // 処理中画面->結果画面
        redirect("/emanager/setting/menu/listbox/ResourceList.do", param, "/Progress.do");
    }

    /**
     * ページ名検索ボタンが押下されたときに呼ばれます。
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void btn_P_PageResouceKey_Click(ActionEvent e)
            throws Exception
    {
        // リソース一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();
        param.setParameter(ResourceListBusiness.RESOURCE_KEY, txt_PageNameResouceKey.getText());
        param.setParameter(ResourceListBusiness.BTNFLAG_KEY, btn_P_PageResouceKey.getId());

        // 処理中画面->結果画面
        redirect("/emanager/setting/menu/listbox/ResourceList.do", param, "/Progress.do");
    }

    /**
     * 設定ボタンが押下されたときに呼ばれます
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void btn_Commit_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;

        try
        {
            //コネクション取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            updateFunction(conn);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /**
     * Functionテーブルへの登録、更新、削除処理を行います。
     * 
     * @param conn DBコネクション
     * @throws Exception
     */
    private void updateFunction(Connection conn)
            throws Exception
    {
        try
        {
            // 機能IDの取得
            String functionid = lbl_In_FunctionId.getText();

            // ボタンリソースキー・ページ名リソースキーの取得
            String pagenameresourcekey = txt_PageNameResouceKey.getText();
            String buttonresourcekey = txt_ButtonResourceKey.getText();

            // DS番号の取得
            String dsNo = txt_DsNo.getText();

            // 処理区分
            String proc = this.getViewState().getString(VSKEY_PROCESS_KEY);

            // 入力チェック(削除時以外)
            if (!(proc.equals(VSKEY_DELETE)))
            {
                // ボタンリソースキー
                txt_ButtonResourceKey.validate(this, true);

                // ボタン名を取得
                try
                {
                    String buttonName = DispResourceMap.getText(buttonresourcekey);
                    txt_R_ButtonName.setText(buttonName);
                }
                catch (Exception ex)
                {
                    message.setMsgResourceKey("6407006");
                    setFocus(txt_ButtonResourceKey);
                    return;
                }

                // ページ名リソースキー
                txt_PageNameResouceKey.validate(this, true);

                // ページ名を取得
                try
                {
                    String pageName = DispResourceMap.getText(pagenameresourcekey);
                    txt_R_PageName.setText(pageName);
                }
                catch (Exception ex)
                {
                    message.setMsgResourceKey("6407007");
                    setFocus(txt_PageNameResouceKey);
                    return;
                }
            }

            // メイン機能ID
            String mainfunctionid = this.getViewState().getString("MainFunctionId");

            // ボタン表示順
            String buttondispnumber = this.getViewState().getString("ActiveCol");
            int int_buttondispnumber = Integer.parseInt(buttondispnumber) - 1;
            buttondispnumber = Integer.toString(int_buttondispnumber);

            // 認証チェック(現在は未実装)
            //int doauthentication_int = DOAUTHENTICATION_OFF;
            //String doauthentication = Integer.toString(doauthentication_int);

            // URI
            String uri = txt_URI.getText();

            // Frame名
            String framename = "_self"; //txt_FrameName.getText();

            // 完了時のメッセージ
            String comp_msg = "";

            // 処理区分が押下されていない場合
            if (proc == null)
            {
                return;
            }
            // 登録時
            else if (proc.equals(VSKEY_INSERT))
            {
                FunctionHandler functionHandler = EmHandlerFactory.getFunctionHandler(conn);

                // 登録事前チェック(登録されていないか？)
                if (functionHandler.findCountByFunctionId(functionid) == 0)
                {
                    // ボタンリソースキー入力チェック(必須入力項目)
                    txt_ButtonResourceKey.validate(this, true);

                    // ページ名リソースキー入力チェック(必須入力項目)
                    txt_PageNameResouceKey.validate(this, true);

                    // DS番号入力チェック(必須入力項目)
                    txt_DsNo.validate(this, true);
                    
                    // URI入力チェック(必須入力項目)
                    txt_URI.validate(this, true);

                    // Frame名入力チェック(必須入力項目)
                    //                  txt_FrameName.validate();

                    // DS番号を条件にCom_Functionテーブルを検索
                    Function[] functions = functionHandler.findByDsNo(dsNo);

                    // DS番号未チェックの場合
                    if (!dsNoChecked)
                    {
                        // 同一DS番号が登録されている場合
                        if (functions != null && functions.length > 0)
                        {
                            // ページ名が異なる場合
                            if (!functions[0].getPageResourceKey().equals(pagenameresourcekey))
                            {
                                // 登録済みページ名の取得
                                String pageName = DispResources.getText(functions[0].getPageResourceKey());
                                // 表示するメッセージの編集「入力されたDS番号はページ名{0}として登録済みです。登録済みのページ名で登録しますか？」
                                String msg = "MSG-T0054" + Constants.MSG_DELIM + pageName;
                                // 確認メッセージ表示
                                setConfirm(msg, true, true);
                                getViewState().setString(DS_CONFIRM_KEY, CONFIRM1);
                                return;
                            }
                        }
                    }

                    // ラジオボタンの取得
                    boolean loginFlag = false;
                    boolean publicFlag = true;

                    if (((RadioButton)rdo_LoginEnableCheck.getSelectedItem()).getId().equals("rdo_LoginEnableCheck"))
                    {
                        loginFlag = true;
                    }

                    if (((RadioButton)rdo_Public.getSelectedItem()).getId().equals("rdo_Public"))
                    {
                        publicFlag = false;
                    }

                    // ユーザ情報の取得
                    DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();
                    UserInfoHandler userhandler = new UserInfoHandler(userinfo);

                    String userId = userhandler.getUserID();
                    String ipAddr = this.getHttpRequest().getRemoteAddr();

                    Function function = new Function();
                    function.setFunctionId(functionid); // 画面ID
                    function.setSubMenuId(mainfunctionid); // サブメニューID
                    function.setButtonDisplayOrder(int_buttondispnumber); // サブメニューボタン表示順
                    function.setButtonResourceKey(buttonresourcekey); // ボタンリソースキー
                    function.setPageResourceKey(pagenameresourcekey); // ページ名リソースキー
                    function.setDsNumber(dsNo); // DS番号
                    function.setDoAuthenticationFlag(loginFlag); // 認証チェックフラグ（0:OFF,1:ON）
                    function.setUrl(uri); // URI
                    function.setFrameName(framename); // Frame名
                    function.setHiddenFlag(publicFlag); // 客先には表示させないためのフラグフラグ
                    function.setUpdateUser(userId); // 更新ユーザ
                    function.setUpdateTerminal(ipAddr); // 更新端末IP
                    function.setUpdateKind(1); // 更新区分（1:登録、2:修正）
                    function.setUpdateProcess(this.getClass().getName()); // 更新処理名

                    // 挿入処理
                    functionHandler.createFunction(function);

                    //ユーザ情報の作成
                    DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
                    //項目リストの生成
                    List list = new ArrayList();
                    list.add(lbl_In_FunctionId.getText());
                    list.add(lbl_In_FunctionName.getText());
                    list.add(txt_ButtonResourceKey.getText());
                    list.add(txt_R_ButtonName.getText());
                    list.add(txt_PageNameResouceKey.getText());
                    list.add(txt_R_PageName.getText());
                    list.add(txt_DsNo.getText());
                    list.add(txt_URI.getText());
                    list.add(rdo_LoginEnableCheck.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                              : String.valueOf(EmConstants.DB_FLAG_FALSE));
                    list.add(rdo_Public.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                    : String.valueOf(EmConstants.DB_FLAG_FALSE));
                    //オペレーションログ出力
                    P11LogWriter logWriter = new P11LogWriter(conn);
                    logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_SETTING, list);

                    conn.commit();

                    // 6401003=登録しました。
                    comp_msg = "6401003";

                    // メンテナンスログ挿入
                    String msg = "6400018" + Message.MSG_DELIM + EmTableColumns.FUNCTION_FUNCTIONID + "=" + functionid;
                    EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_MENU, msg, "");

                    // 修正モードに移行
                    // 修正中を保持
                    this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_MODIFY);
                    btn_BC_Delete.setEnabled(true);
                }
                else
                {
                    // 指定された画面は既にに登録されています。
                    message.setMsgResourceKey("6403046");

                    return;
                }
            }
            // 修正時
            else if (proc.equals(VSKEY_MODIFY))
            {
                FunctionHandler functionHandler = EmHandlerFactory.getFunctionHandler(conn);

                // 登録事前チェック(登録されているか？)
                if (functionHandler.findCountByFunctionId(functionid) == 1)
                {
                    // ボタンリソースキー入力チェック(必須入力項目)
                    txt_ButtonResourceKey.validate(this, true);

                    // ページ名リソースキー入力チェック(必須入力項目)
                    txt_PageNameResouceKey.validate(this, true);

                    // DS番号入力チェック(必須入力項目)
                    txt_DsNo.validate(this, true);

                    // URI入力チェック(必須入力項目)
                    txt_URI.validate(this, true);

                    // Frame名入力チェック(必須入力項目)
                    //                  txt_FrameName.validate();

                    // 現在のファンクションID以外でDS番号を条件にCom_Functionテーブルを検索
                    Function[] functions = functionHandler.findByDsNoExceptFunctionId(dsNo, functionid);

                    // DS番号未チェックの場合
                    if (!dsNoChecked)
                    {
                        // 同一DS番号が登録されている場合
                        if (functions != null && functions.length > 0)
                        {
                            // ページ名が異なる場合
                            if (!functions[0].getPageResourceKey().equals(pagenameresourcekey))
                            {
                                // 登録済みページ名の取得
                                String pageName = DispResources.getText(functions[0].getPageResourceKey());
                                // 表示するメッセージの編集
                                String msg = "MSG-T0054" + Constants.MSG_DELIM + pageName;
                                setConfirm(msg, true, true);
                                getViewState().setString(DS_CONFIRM_KEY, CONFIRM1);
                                return;
                            }
                        }
                    }

                    // ラジオボタンの取得
                    boolean loginFlag = false;
                    boolean publicFlag = true;

                    if (((RadioButton)rdo_LoginEnableCheck.getSelectedItem()).getId().equals("rdo_LoginEnableCheck"))
                    {
                        loginFlag = true;
                    }

                    if (((RadioButton)rdo_Public.getSelectedItem()).getId().equals("rdo_Public"))
                    {
                        publicFlag = false;
                    }

                    // 2006/11/28 安井
                    DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();
                    UserInfoHandler userhandler = new UserInfoHandler(userinfo);

                    String userId = userhandler.getUserID();
                    String ipAddr = userhandler.getIPAddress();

                    Function function = new Function();
                    function.setFunctionId(functionid); // 画面ID
                    function.setSubMenuId(mainfunctionid); // サブメニューID
                    function.setButtonDisplayOrder(int_buttondispnumber); // サブメニューボタン表示順
                    function.setButtonResourceKey(buttonresourcekey); // ボタンリソースキー
                    function.setPageResourceKey(pagenameresourcekey); // ページ名リソースキー
                    function.setDsNumber(dsNo); // DS番号
                    function.setDoAuthenticationFlag(loginFlag); // 認証チェックフラグ（0:OFF,1:ON）
                    function.setUrl(uri); // URI
                    function.setFrameName(framename); // Frame名
                    function.setHiddenFlag(publicFlag); // 客先には表示させないためのフラグフラグ（0:OFF,1:ON）
                    function.setUpdateUser(userId); // 更新ユーザ
                    function.setUpdateTerminal(ipAddr); // 更新端末IP
                    function.setUpdateKind(2); // 更新区分（1:登録、2:修正）
                    function.setUpdateProcess(this.getClass().getName()); // 更新処理名

                    int updateCnt = functionHandler.updateByFunctionId(function);

                    // 更新対象が存在しなかった場合
                    if (updateCnt == 0)
                    {
                        // 指定された機能IDは登録されていません。
                        message.setMsgResourceKey("6403021");

                        return;
                    }

                    //ユーザ情報の作成
                    DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
                    //項目リストの生成
                    List list = new ArrayList();
                    list.add(lbl_In_FunctionId.getText());
                    list.add(lbl_In_FunctionName.getText());
                    list.add(txt_ButtonResourceKey.getText());
                    list.add(txt_R_ButtonName.getText());
                    list.add(txt_PageNameResouceKey.getText());
                    list.add(txt_R_PageName.getText());
                    list.add(txt_URI.getText());
                    list.add(txt_DsNo.getText());
                    list.add(rdo_LoginEnableCheck.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                              : String.valueOf(EmConstants.DB_FLAG_FALSE));
                    list.add(rdo_Public.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                    : String.valueOf(EmConstants.DB_FLAG_FALSE));
                    //オペレーションログ出力
                    P11LogWriter logWriter = new P11LogWriter(conn);
                    logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_SETTING, list);

                    // コミット
                    conn.commit();

                    // メンテナンスログ挿入
                    String msg = "6400019" + Message.MSG_DELIM + EmTableColumns.FUNCTION_FUNCTIONID + "=" + functionid;
                    EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_MENU, msg, "");

                    // 6401004=修正しました。
                    comp_msg = "6401004";
                }
                else
                {
                    // 指定された機能IDは登録されていません。
                    message.setMsgResourceKey("6403021");

                    return;
                }
            }
            // 削除時
            else if (proc.equals(VSKEY_DELETE))
            {
                FunctionHandler functionHandler = EmHandlerFactory.getFunctionHandler(conn);

                if (functionHandler.findCountByFunctionId(functionid) != 0)
                {
                    // 機能IDを取得
                    String[] delete_functionid = new String[1];
                    delete_functionid[0] = lbl_In_FunctionId.getText();

                    // 削除（画面削除）
                    int updateCnt = functionHandler.deleteFunction(functionid);

                    // 更新対象が存在しなかった場合
                    if (updateCnt == 0)
                    {
                        // 指定された機能IDは登録されていません。
                        message.setMsgResourceKey("6403021");

                        return;
                    }

                    RoleFunctionMapHandler roleFuncHandler = EmHandlerFactory.getRoleFunctionMapHandler(conn);

                    // 削除（ロール機能画面マッピング削除）
                    roleFuncHandler.deleteByFunctionId(functionid);

                    //ユーザ情報の作成
                    DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
                    //項目リストの生成
                    List list = new ArrayList();
                    list.add(lbl_In_FunctionId.getText());
                    list.add(lbl_In_FunctionName.getText());
                    list.add(txt_ButtonResourceKey.getText());
                    list.add(txt_R_ButtonName.getText());
                    list.add(txt_PageNameResouceKey.getText());
                    list.add(txt_R_PageName.getText());
                    list.add(txt_URI.getText());
                    list.add(txt_DsNo.getText());
                    list.add(rdo_LoginEnableCheck.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                              : String.valueOf(EmConstants.DB_FLAG_FALSE));
                    list.add(rdo_Public.getChecked() ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                    : String.valueOf(EmConstants.DB_FLAG_FALSE));
                    //オペレーションログ出力
                    P11LogWriter logWriter = new P11LogWriter(conn);
                    logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_DELETE, list);

                    // コミット
                    conn.commit();

                    // メンテナンスログ挿入
                    String msg = "6400020" + Message.MSG_DELIM + EmTableColumns.FUNCTION_FUNCTIONID + "=" + functionid;
                    EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_MENU, msg, "");

                    // 6401005=削除しました。
                    comp_msg = "6401005";

                    // クリア処理
                    btn_Clear_Click(null);

                    // 登録モードに移行
                    // 登録中を保持
                    this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_INSERT);
                    btn_BC_Delete.setEnabled(false);
                }
                else
                {
                    // 指定された機能IDは登録されていません。
                    message.setMsgResourceKey("6403021");

                    return;
                }
            }

            // メッセージをセット
            message.setMsgResourceKey(comp_msg);
        }
        catch (SQLException se)
        {
            // ロールバック
            EmConnectionHandler.rollback(conn);

            // エラーコードの取得
            int errorCode = se.getErrorCode();

            if (errorCode == EmConstants.SQLERRORCODE_ORA0001)
            {
                // <jp>一意制約違反</jp>
                message.setMsgResourceKey("6403068");
            }
            else
            {
                // <jp>それ以外のDBエラー</jp>
                message.setMsgResourceKey("6403069");
            }
        }
        catch (Exception ex)
        {
            EmConnectionHandler.rollback(conn);
            throw ex;
        }
        finally
        {
            if (conn != null)
            {
                EmConnectionHandler.closeConnection(conn);
            }
        }
    }

// 2008/12/25 K.Matsuda Start 赤*を非表示
    /**
     * 必須項目のマーク(*)を非表示にします
     */
    private void setVisibleRequireMarks(boolean visible)
    {
        // *を非表示にする
        lbl_RequireButtonResouceKey.setVisible(visible);
        lbl_RequirePageNameResouceKey.setVisible(visible);
        lbl_RequireDSNo.setVisible(visible);
        lbl_RequireURI.setVisible(visible);
    }
// 2008/12/25 K.Matsuda End

    /**
     * クリアボタンの処理を実装します
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        // ボタンリソースキーテキストボックスをクリア
        txt_ButtonResourceKey.setText("");
        // ボタン名テキストボックスをクリア
        txt_R_ButtonName.setText("");
        // ページ名リソースキーテキストボックスをクリア
        txt_PageNameResouceKey.setText("");
        // ページ名テキストボックスをクリア
        txt_R_PageName.setText("");
        // DS番号テキストボックスをクリア
        txt_DsNo.setText("");
        // URIテキストボックスをクリア
        txt_URI.setText("");
        // Frame名に"_self"をセット
        //		txt_FrameName.setText("_self");
    }

    /**
     * 削除ボタンが押下されたときに呼ばれます
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void btn_BC_Delete_Click(ActionEvent e)
            throws Exception
    {
        // 削除中を保持
        this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_DELETE);

        // 設定処理
        btn_Commit_Click(e);
    }

    /**
     * 戻るボタンが押下されたときに呼ばれます
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void btn_Back_Click(ActionEvent e)
            throws Exception
    {
        // １画面目に遷移
        forward("/emanager/setting/menu/Function1.do");
    }
}
// end of class

// $Id: SubMenuBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $

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

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.Validator;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.MainMenu;
import jp.co.daifuku.emanager.database.entity.SubMenu;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.EmTableColumns;
import jp.co.daifuku.emanager.database.handler.FunctionHandler;
import jp.co.daifuku.emanager.database.handler.MainMenuHandler;
import jp.co.daifuku.emanager.database.handler.SubMenuHandler;
import jp.co.daifuku.emanager.display.web.setting.menu.listbox.MainMenuListBusiness;
import jp.co.daifuku.emanager.display.web.setting.menu.listbox.ResourceListBusiness;
import jp.co.daifuku.emanager.display.web.setting.menu.listbox.SubMenuListBusiness;
import jp.co.daifuku.emanager.util.DispResourceMap;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;


/** <jp>
 * ツールが生成した画面クラスです
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
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
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </en> */
public class SubMenuBusiness
        extends jp.co.daifuku.emanager.display.web.setting.menu.SubMenu
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
        
        //機能リソースキーテキストボックスを読み取り専用にセット
        txt_FunctionResourceKey.setReadOnly(true);
        //メニューIDテキストボックスを読み取り専用にセット
        txt_MenuId.setReadOnly(true);
        //機能リソースキー検索ボタンを読み取り専用にセット
        btn_P_FunctionResouceKey.setEnabled(false);
        //メニューID検索ボタンを読み取り専用にセット
        btn_P_MenuId.setEnabled(false);
    }

    /**
     * ポップアップウインドから、戻ってくるときにこのメソッドが
     * 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
     * @param e ActionEvent
     * @exception Exception
     */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        DialogParameters param = ((DialogEvent)e).getDialogParameters();

        //メイン機能IDを取得
        String functionkey = param.getParameter(SubMenuListBusiness.MAINFUNCTIONID_KEY);
        //空ではないときに値をセットする
        if (!Validator.isEmptyCheck(functionkey))
        {
            //クリア処理
            btn_Clear_Click(e);
            txt_R_MainFunctionId.setText(functionkey);
        }

        //メニューIDを取得
        String menukey = param.getParameter(MainMenuListBusiness.MENUID_KEY);
        String menuname = param.getParameter(MainMenuListBusiness.MENUNAME_KEY);
        String menuresourcekey = param.getParameter(MainMenuListBusiness.MENURESOURCE_KEY);
        //空ではないときに値をセットする
        if (!Validator.isEmptyCheck(menukey))
        {
            txt_MenuId.setText(menukey);
            setFocus(txt_MenuId);
            //メニュー名がリソースファイルに未登録の場合
            if (menuname.equals(""))
            {
                txt_R_MenuName.setText(menuresourcekey);
            }
            //メニュー名がリソースファイルに登録済みの場合
            else
            {
                txt_R_MenuName.setText(menuname);
            }

        }

        //リソースキーを取得
        String resourcekey = param.getParameter(ResourceListBusiness.RESOURCE_KEY);
        String resourcename = param.getParameter(ResourceListBusiness.RESOURCENAME_KEY);
        //空ではないときに値をセットする
        if (!Validator.isEmptyCheck(resourcekey))
        {
            txt_FunctionResourceKey.setText(resourcekey);
            txt_R_FunctionName.setText(resourcename);
            setFocus(txt_FunctionResourceKey);
        }

    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /** 
     * 修正、登録ボタン押下時に、DBの項目を各コントロールへマッピングします。
     * @param subMenu SubMenuエンティティ
     * @throws Exception 
     */
    private void mapping(SubMenu subMenu)
            throws Exception
    {
        //登録済みのメイン機能IDがある場合
        if (subMenu != null)
        {
            //メイン機能ID
            txt_R_MainFunctionId2.setText(txt_R_MainFunctionId.getText());

            //機能表示順
            txt_R_FunctionDispNumber.setText(Integer.toString(subMenu.getSubMenuDisplayOrder()));

            //メニューID
            String menuId = subMenu.getMainMenuId();
            txt_MenuId.setText(menuId);
            //メニュー名
            String menuResourceKey = "";
            String menuName = "";
            Connection conn = null;
            try
            {
                //コネクション取得
                conn = EmConnectionHandler.getPageDbConnection(this);
                MainMenuHandler menHandler = EmHandlerFactory.getMainMenuHandler(conn);

                //DBよりメインメニュリソースを取得
                MainMenu mainMenu = menHandler.findByMenuId(menuId);

                if (mainMenu != null)
                {
                    menuResourceKey = mainMenu.getMenuResourceKey();

                    if (!"".equals(menuResourceKey))
                    {
                        //メニュー名をセット
                        menuName = DispResources.getText(menuResourceKey);
                    }
                }
            }
            catch (MissingResourceException mre)
            {
                //ディスプリソースよりメニュー名が取得できなかった場合
                //メニューリソースをセット
                menuName = menuResourceKey;
            }
            finally
            {
                //コネクションを開放
                EmConnectionHandler.closeConnection(conn);
            }
            txt_R_MenuName.setText(menuName);

            //機能リソースキー
            String subMenuResourceKey = subMenu.getSubMenuResourceKey();
            txt_FunctionResourceKey.setText(subMenuResourceKey);
            //機能名
            String subMenuName = "";
            if (!"".equals(subMenuResourceKey))
            {
                try
                {
                    subMenuName = DispResources.getText(subMenuResourceKey);
                }
                catch (MissingResourceException mre)
                {
                }
            }
            txt_R_FunctionName.setText(subMenuName);
        }
    }
    
// 2008/12/25 K.Matsuda Start 赤*を非表示
    /**
     * 必須項目のマーク(*)を非表示にします
     */
    private void setVisibleRequireMarks(boolean visible)
    {
        // *を非表示にする
        lbl_RequireMenuId.setVisible(visible);
        lbl_RequireFunctionResourceKey.setVisible(visible);
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
     * メイン機能ID検索ボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_P_Search_Click(ActionEvent e)
            throws Exception
    {
        //サブメニュー一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();

        //処理中画面->結果画面
        redirect("/emanager/setting/menu/listbox/SubMenuList.do", param, "/Progress.do");
    }

    /** 
     * 登録ボタンが押下された時の処理を実装します
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Submit_Click(ActionEvent e)
            throws Exception
    {
        setFocus(txt_MenuId);

        //クリア処理
        btn_Clear_Click(e);
        txt_R_MainFunctionId.setText("");

        //メイン機能IDと機能表示順は設定時に値がセットされるため、ここでは「*」を仮にセット
        txt_R_MainFunctionId2.setText("*****");
        txt_R_FunctionDispNumber.setText("***");

        //メニューID検索ボタンを有効にする
        btn_P_MenuId.setEnabled(true);
        //メニューIDテキストボックスを有効にする
        txt_MenuId.setReadOnly(false);
        //機能リソースキー検索ボタンを有効にする
        btn_P_FunctionResouceKey.setEnabled(true);
        //機能リソースキーテキストボックスを有効にする
        txt_FunctionResourceKey.setReadOnly(false);

        //登録中を保持
        this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_INSERT);

        //設定しますか？
        btn_Commit.setBeforeConfirm("MSG-T0005");

    }

    /** 
     * 修正ボタンが押下された時の処理を実装します
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Modify_Click(ActionEvent e)
            throws Exception
    {
        setFocus(txt_MenuId);

        Connection conn = null;
        try
        {
            //クリア処理
            btn_Clear_Click(e);

            //メイン機能IDテキストボックスを入力チェック
            txt_R_MainFunctionId.validate(true);

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            SubMenuHandler subHandler = EmHandlerFactory.getSubMenuHandler(conn);

            // DBより取得
            SubMenu subMenu = subHandler.findBySubMenuId(this.txt_R_MainFunctionId.getText());

            //すでに同一のメイン機能IDが登録済みかを確認 
            if (subMenu == null)
            {
                //同一のメイン機能IDが登録されていない場合。
                // 指定されたメイン機能IDは登録されていません。
                message.setMsgResourceKey("6403016");
                return;
            }

            //メニューID検索ボタンを有効にする
            btn_P_MenuId.setEnabled(true);
            //メニューIDテキストボックスを有効にする
            txt_MenuId.setReadOnly(false);
            //機能リソースキー検索ボタンを有効にする
            btn_P_FunctionResouceKey.setEnabled(true);
            //機能リソースキーテキストボックスを有効にする
            txt_FunctionResourceKey.setReadOnly(false);

            //データベースのデータをセットする
            mapping(subMenu);

            //修正中を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_MODIFY);

            //設定しますか？
            btn_Commit.setBeforeConfirm("MSG-T0005");

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

            //メイン機能IDテキストボックスを入力チェック
            txt_R_MainFunctionId.validate(true);

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            SubMenuHandler subHandler = EmHandlerFactory.getSubMenuHandler(conn);

            //DBより取得
            SubMenu subMenu = subHandler.findBySubMenuId(this.txt_R_MainFunctionId.getText());
            if (subMenu == null)
            {
                // 同一のメイン機能IDが登録されていない場合。
                // 指定されたメイン機能IDは登録されていません。
                message.setMsgResourceKey("6403016");
                return;
            }

            //メニューID検索ボタンを無効にする
            btn_P_MenuId.setEnabled(false);
            //メニューIDテキストボックスを読み取り専用にする
            txt_MenuId.setReadOnly(true);
            //機能リソースキー検索ボタンを無効にする
            btn_P_FunctionResouceKey.setEnabled(false);
            //機能リソースキーテキストボックスを読み取り専用にする
            txt_FunctionResourceKey.setReadOnly(true);

            //データベースのデータをセットする
            mapping(subMenu);
            //削除中を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_DELETE);

            //サブメニューボタンの関連項目も削除されますが、よろしいですか？
            btn_Commit.setBeforeConfirm("MSG-T0050");
        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * メニューID検索ボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_P_MenuId_Click(ActionEvent e)
            throws Exception
    {
        //メインメニュー一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();
        param.setParameter(MainMenuListBusiness.MENUID_KEY, txt_MenuId.getText());
        param.setParameter(MainMenuListBusiness.BTNFLAG_KEY, btn_P_MenuId.getId());

        //処理中画面->結果画面
        redirect("/emanager/setting/menu/listbox/MainMenuList.do", param, "/Progress.do");
    }

    /** 
     * 機能リソースキー検索ボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_P_FunctionResouceKey_Click(ActionEvent e)
            throws Exception
    {
        //リソース一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();
        param.setParameter(ResourceListBusiness.RESOURCE_KEY, txt_FunctionResourceKey.getText());
        param.setParameter(ResourceListBusiness.BTNFLAG_KEY, btn_P_FunctionResouceKey.getId());

        //処理中画面->結果画面
        redirect("/emanager/setting/menu/listbox/ResourceList.do", param, "/Progress.do");
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
            SubMenuHandler subHandler = EmHandlerFactory.getSubMenuHandler(conn);

            //メイン機能IDをセット
            String subMenuId = txt_R_MainFunctionId2.getText();
            //機能表示順をセット
            String functiondispno = txt_R_FunctionDispNumber.getText();

            //メニューIDをセット
            String menuid = txt_MenuId.getText();
            //機能リソースキーをセット
            String resourcekey = txt_FunctionResourceKey.getText();

            //処理区分
            String proc = this.getViewState().getString(VSKEY_PROCESS_KEY);

            //完了時のメッセージ
            String comp_msg = "";

            //メンテナンスログに出力するメッセージ
            String mainteLog_msg = "";

            //処理区分が押下されていない場合
            if (proc == null)
            {
                return;
            }
            //登録時
            else if (proc.equals(VSKEY_INSERT))
            {
                //メニューIDテキストボックス入力チェック(必須入力項目)
                txt_MenuId.validate(this, true);
                //メニューIDの存在チェック
                if (EmHandlerFactory.getMainMenuHandler(conn).findByMenuId(menuid) == null)
                {
                    //指定されたメニューIDは登録されていません。
                    message.setMsgResourceKey("6403017");
                    setFocus(txt_MenuId);
                    return;
                }
                //機能リソースキーテキストボックス入力チェック(必須入力項目)
                txt_FunctionResourceKey.validate(this, true);
                //機能リソースキーの存在チェック
                if (!DispResourceMap.getKeyList(FUNCTIONRESOUCEKEY).contains(resourcekey))
                {
                    //この機能リソースキーは有効ではありません。
                    message.setMsgResourceKey("6403076");
                    setFocus(txt_FunctionResourceKey);
                    return;
                }

                //入力チェック
                txt_R_MenuName.validate(false);
                txt_R_FunctionName.validate(false);


                //機能表示順の採番
                //DBより取得
                SubMenu[] subMenuArray = null;

                subMenuArray = subHandler.findByMainMenuId(this.txt_MenuId.getText(), 1);

                //採番
                int dispNoMax = 1;
                if (subMenuArray != null)
                {
                    //登録済みのメイン機能IDがある場合
                    dispNoMax = subMenuArray[subMenuArray.length - 1].getSubMenuDisplayOrder() + 1;
                }

                //機能表示順に採番した値をセット
                functiondispno = Integer.toString(dispNoMax);

                //DBに数値データ以外の値が入っていた場合はメッセージをセットに飛ぶ
                //メイン機能IDの採番 例：10100 = メニューID+機能表示順+00
                //メニューIDをセット
                subMenuId = menuid;
                //機能表示順の番号変換
                //1 → 01に変換
                if (functiondispno.length() == 1)
                {
                    functiondispno = "0" + functiondispno;
                }
                //機能表示順を加算
                subMenuId = subMenuId.concat(functiondispno);
                //"00"を加算
                subMenuId = subMenuId.concat("00");

                //登録しようとしているメイン機能IDがすでに登録済みか検索
                for (int i = 0; i < 20; i++)
                {
                    if (subHandler.findBySubMenuId(subMenuId) == null)
                    {
                        break;
                    }

                    if (subMenuId.length() == 5)
                    {
                        //メニューIDが1桁の場合
                        int tmp = Integer.parseInt(subMenuId.substring(1));
                        tmp = tmp + 100;
                        if (tmp < 1000)
                        {
                            //機能表示順が1桁の場合
                            subMenuId = subMenuId.substring(0, 1) + "0" + Integer.toString(tmp);
                        }
                        else
                        {
                            //機能表示順が2桁の場合
                            subMenuId = subMenuId.substring(0, 1) + Integer.toString(tmp);
                        }
                    }
                    else if (subMenuId.length() == 6)
                    {
                        //メニューIDが2桁の場合
                        int tmp = Integer.parseInt(subMenuId.substring(2));
                        tmp = tmp + 100;

                        if (tmp < 1000)
                        {
                            //機能表示順が1桁の場合
                            subMenuId = subMenuId.substring(0, 2) + "0" + Integer.toString(tmp);
                        }
                        else
                        {
                            //機能表示順が2桁の場合
                            subMenuId = subMenuId.substring(0, 2) + Integer.toString(tmp);
                        }
                    }
                }

                //DBに登録
                SubMenu insertSubMenu = new SubMenu();
                insertSubMenu.setSubMenuId(subMenuId);
                insertSubMenu.setMainMenuId(menuid);
                insertSubMenu.setSubMenuDisplayOrder(dispNoMax);
                insertSubMenu.setSubMenuResourceKey(resourcekey);
                // 登録したユーザ情報を設定
                DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();
                if (userinfo != null)
                {
                    UserInfoHandler userhandler = new UserInfoHandler(userinfo);

                    if (userhandler != null)
                    {
                        insertSubMenu.setUpdateUser(userhandler.getUserID());
                    }
                }
                insertSubMenu.setUpdateTerminal(this.httpRequest.getRemoteAddr());
                insertSubMenu.setUpdateKind(1);
                insertSubMenu.setUpdateProcess(this.getClass().getName());

                //DBに登録する前にもう一度存在チェック
                if (subHandler.findBySubMenuId(subMenuId) != null)
                {
                    //すでに同一のサブメニューIDが登録されています。
                    message.setMsgResourceKey("6403045");
                    return;
                }

                subHandler.createSubMenu(insertSubMenu);

                //6401009=メイン機能ID={0}を登録しました。
                comp_msg = "6401009" + "\t" + subMenuId;

                //サブメニューを登録しました。({0})
                String Primaly = EmTableColumns.SUBMENU_SUBMENUID + "=" + subMenuId;
                mainteLog_msg = "6400014" + MSG_DELIM + Primaly;
            }
            //修正時
            else if (proc.equals(VSKEY_MODIFY))
            {
                //メイン機能ID入力チェック(必須入力項目)
                txt_R_MainFunctionId2.validate(true);

                //機能表示順テキストボックス入力チェック(必須入力項目)
                txt_R_FunctionDispNumber.validate();
                //メニューIDテキストボックス入力チェック(必須入力項目)
                txt_MenuId.validate(this, true);
                //メニューIDの存在チェック
                if (EmHandlerFactory.getMainMenuHandler(conn).findByMenuId(menuid) == null)
                {
                    //指定されたメニューIDは登録されていません。
                    message.setMsgResourceKey("6403017");
                    setFocus(txt_MenuId);
                    return;
                }
                //機能リソースキーテキストボックス入力チェック(必須入力項目)
                txt_FunctionResourceKey.validate(this, true);
                //機能リソースキーの存在チェック
                if (!DispResourceMap.getKeyList(FUNCTIONRESOUCEKEY).contains(resourcekey))
                {
                    //この機能リソースキーは有効ではありません。
                    message.setMsgResourceKey("6403076");
                    setFocus(txt_FunctionResourceKey);
                    return;
                }

                //入力チェック
                txt_R_MenuName.validate(false);
                txt_R_FunctionName.validate(false);

                //DB更新
                SubMenu modifySubMenu = new SubMenu();
                modifySubMenu.setSubMenuId(subMenuId);
                modifySubMenu.setMainMenuId(menuid);
                modifySubMenu.setSubMenuDisplayOrder(Integer.parseInt(functiondispno));
                modifySubMenu.setSubMenuResourceKey(resourcekey);
                // 登録したユーザ情報を設定
                DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();
                if (userinfo != null)
                {
                    UserInfoHandler userhandler = new UserInfoHandler(userinfo);

                    if (userhandler != null)
                    {
                        modifySubMenu.setUpdateUser(userhandler.getUserID());
                    }
                }
                modifySubMenu.setUpdateTerminal(this.httpRequest.getRemoteAddr());
                modifySubMenu.setUpdateKind(2);
                modifySubMenu.setUpdateProcess(this.getClass().getName());

                //DB更新する前にもう一度存在チェック
                if (subHandler.findBySubMenuId(subMenuId) == null)
                {
                    //指定されたサブメニューIDは登録されていません。
                    message.setMsgResourceKey("6403044");
                    return;
                }

                if (subHandler.updateBySubMenuId(modifySubMenu) == 0)
                {
                    //修正対象データがありませんでした。
                    message.setMsgResourceKey("6403002");
                    return;
                }

                //6401004=修正しました。
                comp_msg = "6401004";

                //サブメニューを修正しました。({0})
                String Primaly = EmTableColumns.SUBMENU_SUBMENUID + "=" + subMenuId;
                mainteLog_msg = "6400015" + MSG_DELIM + Primaly;
            }
            //削除時
            else if (proc.equals(VSKEY_DELETE))
            {
                //メイン機能ID入力チェック(必須入力項目)
                txt_R_MainFunctionId2.validate(true);

                //DB削除
                //DB削除する前にもう一度存在チェック
                if (subHandler.findBySubMenuId(subMenuId) == null)
                {
                    //指定されたサブメニューIDは登録されていません。
                    message.setMsgResourceKey("6403044");
                    return;
                }
                //com_submenu表を削除
                if (subHandler.deleteSubMenu(subMenuId) == 0)
                {
                    //削除対象データがありませんでした。
                    message.setMsgResourceKey("6403003");
                    return;
                }
                //com_function表を削除
                FunctionHandler funHandler = EmHandlerFactory.getFunctionHandler(conn);
                funHandler.deleteFunctionBySubmenuId(subMenuId);

                //6401005=削除しました。
                comp_msg = "6401005";

                //サブメニューを削除しました。({0})
                String Primaly = EmTableColumns.SUBMENU_SUBMENUID + "=" + subMenuId;
                mainteLog_msg = "6400016" + MSG_DELIM + Primaly;
            }

            //ユーザ情報の作成
            DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
            MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);

            //項目リストの生成
            List list = new ArrayList();
            list.add(subMenuId);
            list.add(String.valueOf(Integer.parseInt(functiondispno)));
            list.add(txt_MenuId.getText());
            list.add(DispResourceMap.getText(menuHandler.findByMenuId(txt_MenuId.getText()).getMenuResourceKey()));
            list.add(txt_FunctionResourceKey.getText());
            list.add(DispResourceMap.getText(txt_FunctionResourceKey.getText()));

            //オペレーションログ出力
            P11LogWriter logWriter = new P11LogWriter(conn);
            logWriter.createOperationLog(userInfo,
                    proc.equals(VSKEY_INSERT) ? EmConstants.OPELOG_CLASS_REGIST
                                             : proc.equals(VSKEY_MODIFY) ? EmConstants.OPELOG_CLASS_MODIFY
                                                                        : EmConstants.OPELOG_CLASS_DELETE, list);


            //設定
            conn.commit();
            //クリア処理
            btn_Clear_Click(e);
            //メッセージをセット
            message.setMsgResourceKey(comp_msg);

            //メンテナンスログ出力
            EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_MENU, mainteLog_msg, "");

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
     * クリアボタンの処理を実装します
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        //メイン機能IDテキストボックスをクリア
        txt_R_MainFunctionId2.setText("");

        //機能表示順テキストボックスをクリア
        txt_R_FunctionDispNumber.setText("");

        //メニューIDテキストボックスをクリア
        txt_MenuId.setText("");
        //メニューIDテキストボックスを読み取り専用にセット
        txt_MenuId.setReadOnly(true);
        //メニューID検索ボタンを無効にする
        btn_P_MenuId.setEnabled(false);
        //メニュー名テキストボックスをクリア
        txt_R_MenuName.setText("");

        //機能リソースキーテキストボックスをクリア
        txt_FunctionResourceKey.setText("");
        //機能リソースキーテキストボックスを読み取り専用にセット
        txt_FunctionResourceKey.setReadOnly(true);
        //機能リソースキー検索ボタンを無効にする
        btn_P_FunctionResouceKey.setEnabled(false);
        //機能名テキストボックスをクリア
        txt_R_FunctionName.setText("");

    }
}
//end of class

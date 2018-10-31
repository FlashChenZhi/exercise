// $Id: MainMenuBusiness.java 3965 2009-04-06 02:55:05Z admin $

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

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.bluedog.util.Validator;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.SubMenu;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.EmTableColumns;
import jp.co.daifuku.emanager.database.handler.FunctionHandler;
import jp.co.daifuku.emanager.database.handler.MainMenuHandler;
import jp.co.daifuku.emanager.database.handler.SubMenuHandler;
import jp.co.daifuku.emanager.display.web.setting.menu.listbox.MainMenuListBusiness;
import jp.co.daifuku.emanager.display.web.setting.menu.listbox.ResourceListBusiness;
import jp.co.daifuku.emanager.util.DispResourceMap;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

/** <jp>
 * メインメニュー設定の画面クラスです。
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
public class MainMenuBusiness
        extends MainMenu
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

        setFocus(txt_MenuResourceKey);
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
        //	    this.getViewState().setString("PRODUCTTYPE", this.request.getParameter(PRODUCTTYPE));
        //	    //メニュータイプをViewStateに保存
        //	    this.getViewState().setString("MENUTYPE", this.request.getParameter(MENUTYPE));

        //メニューリソースキーテキストボックスを読み取り専用にセット
        txt_MenuResourceKey.setReadOnly(true);
        //メニューリソースキー検索ボタンを読み取り専用にセット
        btn_P_MenuResouceKey.setEnabled(false);
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
        //メニューIDを取得
        String menukey = param.getParameter(MainMenuListBusiness.MENUID_KEY);
        //空ではないときに値をセットする
        if (!Validator.isEmptyCheck(menukey))
        {
            //クリア処理
            btn_Clear_Click(e);
            txt_R_MenuId.setText(menukey);
        }

        //リソースキーを取得
        String resourcekey = param.getParameter(ResourceListBusiness.RESOURCE_KEY);
        String resourcename = param.getParameter(ResourceListBusiness.RESOURCENAME_KEY);
        //空ではないときに値をセットする
        if (!Validator.isEmptyCheck(resourcekey))
        {
            txt_MenuResourceKey.setText(resourcekey);
            txt_R_MenuName.setText(resourcename);
            setFocus(txt_MenuResourceKey);
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

    /** 
     * メニューID検索ボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_P_Search_Click(ActionEvent e)
            throws Exception
    {
        //メインメニュー一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();

        //処理中画面->結果画面
        redirect("/emanager/setting/menu/listbox/MainMenuList.do", param, "/Progress.do");
    }


    /** 
     * 登録ボタンが押下された時の処理を実装します
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btm_Submit_Click(ActionEvent e)
            throws Exception
    {
        setFocus(txt_MenuResourceKey);

        Connection conn = null;
        try
        {
            //クリア処理
            btn_Clear_Click(e);
            txt_R_MenuId.setText("");

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);

            //DBに数値データ以外の値が入っていた場合はメッセージをセットに飛ぶ
            //メニューIDの採番
            String menuIdMax = "";
            //メニューIDの一覧取得
            jp.co.daifuku.emanager.database.entity.MainMenu[] allMainMenuEntity = menuHandler.findAll(1);

            // 登録済みのメニューがある場合
            if (allMainMenuEntity != null && allMainMenuEntity.length != 0)
            {
                int temp = 0;
                for (int i = 0; i < allMainMenuEntity.length; i++)
                {
                    // MainMenu型配列からMainMenuを取得
                    jp.co.daifuku.emanager.database.entity.MainMenu mainMenu = allMainMenuEntity[i];
                    // メニューIDの最大値検索
                    if (Integer.parseInt(mainMenu.getMainMenuId()) > temp)
                    {
                        temp = Integer.parseInt(mainMenu.getMainMenuId());
                        // メニューIDの最大値をセット
                        menuIdMax = Integer.toString(temp + 1);
                    }
                }
            }
            // メニューIDが未登録の場合
            else
            {
                menuIdMax = "1";
            }

            //メニューIDをテキストボックスにセット
            txt_R_MenuId2.setText(menuIdMax);

            //メニュー表示順テキストボックスに文字をセット
            txt_R_MenuDispNumber.setText("***");

            //メニューリソースキー検索ボタンを有効にする
            btn_P_MenuResouceKey.setEnabled(true);
            //メニューリソースキーテキストボックスを有効にする
            txt_MenuResourceKey.setReadOnly(false);

            //登録中を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_INSERT);

            //設定しますか？
            btn_Commit.setBeforeConfirm("MSG-T0005");
        }
        catch (NumberFormatException ex)
        {
            //MAINMENU表のMENUID項目に数値以外のデータがセットされているため、
            //メニューIDを採番できません。
            message.setMsgResourceKey("6407001");
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
        setFocus(txt_MenuResourceKey);

        Connection conn = null;
        try
        {
            //クリア処理
            btn_Clear_Click(e);

            //メニューIDテキストボックスを入力チェック
            txt_R_MenuId.validate(true);

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            //すでに同一のメニューIDが登録済みかを確認 
            if (!isDefined(txt_R_MenuId.getText(), conn))
            {
                //指定されたメニューIDは登録されていません。
                message.setMsgResourceKey("6403017");
                return;
            }

            //メニューリソースキー検索ボタンを有効にする
            btn_P_MenuResouceKey.setEnabled(true);
            //メニューリソースキーテキストボックスを有効にする
            txt_MenuResourceKey.setReadOnly(false);

            //データベースのデータをセットする
            mapping(conn);
            //修正中を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_MODIFY);

            //設定しますか？
            btn_Commit.setBeforeConfirm("MSG-T0005");
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

            //メニューIDテキストボックスを入力チェック
            txt_R_MenuId.validate(true);

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            if (!isDefined(txt_R_MenuId.getText(), conn))
            {
                //指定されたメニューIDは登録されていません。
                message.setMsgResourceKey("6403017");
                return;
            }

            //メニューリソースキー検索ボタンを無効にする
            btn_P_MenuResouceKey.setEnabled(false);
            //メニューリソースキーテキストボックスを読み取り専用にする
            txt_MenuResourceKey.setReadOnly(true);

            //データベースのデータをセットする
            mapping(conn);
            //削除中を保持
            this.getViewState().setString(VSKEY_PROCESS_KEY, VSKEY_DELETE);

            //サブメニュー・サブメニューボタンの関連項目も削除されますが、よろしいですか？
            btn_Commit.setBeforeConfirm("MSG-T0049");
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }


    /** 
     * メニューリソースキー検索ボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_P_MenuResouceKey_Click(ActionEvent e)
            throws Exception
    {
        //リソース一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();
        param.setParameter(ResourceListBusiness.RESOURCE_KEY, txt_MenuResourceKey.getText());
        param.setParameter(ResourceListBusiness.BTNFLAG_KEY, btn_P_MenuResouceKey.getId());

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
            //メニューID入力チェック(必須入力項目)
            txt_R_MenuId2.validate(this, true);

            String menuid = txt_R_MenuId2.getText();
            String resourcekey = txt_MenuResourceKey.getText();
            String dispOrder = txt_R_MenuDispNumber.getText();

            //コネクション取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);

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
                int menudispno_int = this.getNextMenuDispNo(menuHandler);
                dispOrder = String.valueOf(menudispno_int);

                //メニューリソースキー入力チェック(必須入力項目)
                txt_MenuResourceKey.validate(this, true);

                //入力チェック
                txt_R_MenuName.validate(false);

                if (!this.isEffectiveResourceKey(resourcekey))
                {
                    // このメニューリソースキーは有効ではありません
                    message.setMsgResourceKey("6403062");
                    return;
                }

                if (menuHandler.findByMenuId(menuid) != null)
                {
                    // すでに同一のメニューIDが登録されています。
                    message.setMsgResourceKey("6403043");
                    return;
                }

                jp.co.daifuku.emanager.database.entity.MainMenu mainMenuEntity =
                        this.createMainMenu(UPDATE_KIND_INSERT);
                mainMenuEntity.setMainMenuId(menuid);
                mainMenuEntity.setMenuResourceKey(resourcekey);
                mainMenuEntity.setMenuDisplayOrder(menudispno_int);

                menuHandler.createMainMenu(mainMenuEntity);

                //6401010=メニューID={0}を登録しました。
                comp_msg = "6401010" + "\t" + menuid;
            }
            //修正時
            else if (proc.equals(VSKEY_MODIFY))
            {
                //メニューリソースキー入力チェック(必須入力項目)
                txt_MenuResourceKey.validate(this, true);

                if (!this.isEffectiveResourceKey(resourcekey))
                {
                    // このメニューリソースキーは有効ではありません
                    message.setMsgResourceKey("6403062");
                    return;
                }

                jp.co.daifuku.emanager.database.entity.MainMenu mainMenu = menuHandler.findByMenuId(menuid);

                if (mainMenu == null)
                {
                    // 指定されたメニューIDは登録されていません。
                    message.setMsgResourceKey("6403017");
                    return;
                }

                //メニュー表示順入力チェック(必須入力項目)
                txt_R_MenuDispNumber.validate();
                //入力チェック
                txt_R_MenuName.validate(false);

                jp.co.daifuku.emanager.database.entity.MainMenu mainMenuEntity =
                        this.createMainMenu(UPDATE_KIND_UPDATE);
                mainMenuEntity.setMainMenuId(menuid);
                mainMenuEntity.setMenuDisplayOrder(mainMenu.getMenuDisplayOrder());
                mainMenuEntity.setMenuResourceKey(resourcekey);

                if (menuHandler.updateByMainMenuId(mainMenuEntity) == 0)
                {
                    // 指定されたメニューIDは登録されていません。
                    message.setMsgResourceKey("6403017");
                    return;
                }

                //6401004=修正しました。
                comp_msg = "6401004";
            }
            //削除時
            else if (proc.equals(VSKEY_DELETE))
            {
                if (menuHandler.findByMenuId(menuid) == null)
                {
                    // 指定されたメニューIDは登録されていません。
                    message.setMsgResourceKey("6403017");
                    return;
                }

                // サブメニューテーブルのハンドラ取得
                SubMenuHandler subMenuHandler = EmHandlerFactory.getSubMenuHandler(conn);

                // 各機能画面（ファンクション）テーブルのハンドラ取得
                FunctionHandler funcHandler = EmHandlerFactory.getFunctionHandler(conn);

                // メインメニューIDを元にサブメニューの一覧を取得
                SubMenu[] subMenuArr = subMenuHandler.findByMainMenuId(menuid, 0);

                if (subMenuArr != null && subMenuArr.length != 0)
                {
                    for (int i = 0; i < subMenuArr.length; i++)
                    {
                        SubMenu subMenuEntity = subMenuArr[i];
                        // サブメニューIDを元に各機能画面テーブルから削除
                        funcHandler.deleteFunctionBySubmenuId(subMenuEntity.getSubMenuId());
                    }
                }

                // メインメニューIDをもとにサブメニューを削除
                subMenuHandler.deleteByMainMenuId(menuid);

                // メインメニューを削除
                if (menuHandler.deleteMainMenu(menuid) == 0)
                {
                    // 指定されたメニューIDは登録されていません。
                    message.setMsgResourceKey("6403017");
                    return;
                }

                //6401005=削除しました。
                comp_msg = "6401005";
            }

            //ユーザ情報の作成
            DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
            //項目リストの生成
            List list = new ArrayList();
            list.add(txt_R_MenuId2.getText());
            list.add(dispOrder);
            list.add(txt_MenuResourceKey.getText());
            list.add(DispResourceMap.getText(txt_MenuResourceKey.getText()));


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
                msg = "6400010";
            }
            //修正時
            else if (proc.equals(VSKEY_MODIFY))
            {
                msg = "6400011";
            }
            //削除時
            else if (proc.equals(VSKEY_DELETE))
            {
                msg = "6400012";
            }

            msg += Message.MSG_DELIM + getKeyEqualValueString(EmTableColumns.MAINMENU_MAINMENUID, menuid);
            EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_MENU, msg, "");
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
     * このリソースキーが有効かをチェックします
     * @param resourcekey
     * @return TRUE 有効
     */
    private boolean isEffectiveResourceKey(String resourcekey)
    {
        try
        {
            DispResourceMap.getText(resourcekey);
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }


    /**
     * 現在登録されている表示順番号の最大値＋１を取得します。
     * @param menuHandler
     * @return int 現在登録されている表示順番号の最大値＋１
     * @throws SQLException
     */
    private int getNextMenuDispNo(MainMenuHandler menuHandler)
            throws SQLException
    {
        jp.co.daifuku.emanager.database.entity.MainMenu[] mainMenuArr = menuHandler.findAll(1);

        int menudispno_int = 0;

        if (mainMenuArr != null && mainMenuArr.length != 0)
        {
            for (int i = 0; i < mainMenuArr.length; i++)
            {
                if (menudispno_int <= mainMenuArr[i].getMenuDisplayOrder())
                {
                    menudispno_int = mainMenuArr[i].getMenuDisplayOrder() + 1;
                }
            }
        }

        if (menudispno_int == 0)
        {
            menudispno_int = 1;
        }
        return menudispno_int;
    }


    /** 
     * クリアボタンの処理を実装します
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        //メニュー名テキストボックスをクリア
        txt_R_MenuName.setText("");
        //メニュー表示順テキストボックスをクリア
        txt_R_MenuDispNumber.setText("");
        //メニューIDテキストボックスをクリア
        txt_R_MenuId2.setText("");
        //メニューリソースキーテキストボックスをクリア
        txt_MenuResourceKey.setText("");
        //メニューリソースキー検索ボタンを無効にする
        btn_P_MenuResouceKey.setEnabled(false);
        //メニューリソースキーテキストボックスを読み取り専用にセット
        txt_MenuResourceKey.setReadOnly(true);
    }

    /** 
     * すでに同一のメニューIDが登録済みかを確認します。
     * @param key  TerminalNumber
     * @param conn Connection
     * @return boolean
     * @throws Exception 
     */
    private boolean isDefined(String key, Connection conn)
            throws Exception
    {
        MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);

        return menuHandler.findByMenuId(key) != null;
    }

    /** 
     * 修正、登録ボタン押下時に、DBの項目を各コントロールへマッピングします。
     * @param conn Connection
     * @throws Exception 
     */
    private void mapping(Connection conn)
            throws Exception
    {
        MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);

        jp.co.daifuku.emanager.database.entity.MainMenu mainMenuEntity =
                menuHandler.findByMenuId(txt_R_MenuId.getText());

        if (mainMenuEntity != null)
        {
            txt_R_MenuId2.setText(txt_R_MenuId.getText());
            txt_R_MenuDispNumber.setText(Integer.toString(mainMenuEntity.getMenuDisplayOrder()));

            String menuNameResource =
                    this.isEffectiveResourceKey(mainMenuEntity.getMenuResourceKey()) ? mainMenuEntity.getMenuResourceKey()
                                                                                    : "LBL-T0101";

            txt_MenuResourceKey.setText(mainMenuEntity.getMenuResourceKey());
            txt_R_MenuName.setText(DispResourceMap.getText(menuNameResource));
        }
    }

    /**
     * セッションのユーザ情報を登録したMainMenuオブジェクトを作成します。
     * @param updateKind 更新区分（1:登録、2:修正）EmConstants.INSERT or EmConstants.MODIFY
     * @return jp.co.daifuku.emanager.database.entity.MainMenu
     */
    private jp.co.daifuku.emanager.database.entity.MainMenu createMainMenu(int updateKind)
    {
        // MainMenuオブジェクトを作成
        jp.co.daifuku.emanager.database.entity.MainMenu mainMenuEntity =
                new jp.co.daifuku.emanager.database.entity.MainMenu();

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
            mainMenuEntity.setUpdateUser(userhandler.getUserID());
        }

        mainMenuEntity.setUpdateTerminal(this.getHttpRequest().getRemoteAddr());

        mainMenuEntity.setUpdateKind(updateKind);

        mainMenuEntity.setUpdateProcess(this.getClass().getName());

        return mainMenuEntity;
    }

    /**
     * key=value の形に連結して返却します。
     * @param key key
     * @param value value
     * @return 連結文字列。
     */
    private String getKeyEqualValueString(String key, String value)
    {
        return key + "=" + value;
    }
    
// 2008/12/25 K.Matsuda Start 赤*を非表示
    /**
     * 必須項目のマーク(*)を非表示にします
     */
    private void setVisibleRequireMarks(boolean visible)
    {
        // *を非表示にする
        lbl_RequireMenuresourceKey.setVisible(visible);
    }
// 2008/12/25 K.Matsuda End
}
//end of class

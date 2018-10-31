//$Id: SubMenuOrderBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.menu;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.StringTokenizer;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.ui.control.ListBoxItem;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.ListBoxHelper;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.MainMenu;
import jp.co.daifuku.emanager.database.entity.SubMenu;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.MainMenuHandler;
import jp.co.daifuku.emanager.database.handler.SubMenuHandler;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;


/** <jp>
 * サブメニュー表示順変更の画面クラスです。
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
public class SubMenuOrderBusiness
        extends SubMenuOrder
        implements EmConstants
{
    // Class fields --------------------------------------------------
    protected final String MSG_DEF_CHAR = "@";

    protected final String PUL_DELIM_CHAR = ",";

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

        btn_Cancel.setEnabled(true);
        btn_Commit.setEnabled(true);
        img_ArrowDown.setVisible(true);
        img_ArrowUp.setVisible(true);
        img_ArrowLeft.setVisible(true);
        img_ArrowRight.setVisible(true);
        lbl_Add.setVisible(true);
        lbl_Delete.setVisible(true);
        ltb_FunctionOrder.setEnabled(true);
        ltb_FunctionOrder2.setEnabled(true);
    }

    /**
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @exception Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {

        Connection conn = null;
        try
        {
            //コネクション取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            //ハンドラーを取得
            MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);

            //件数チェック
            //DBより件数を取得
            if (menuHandler.findCount() == 0)
            {
                //メインメニューを登録してください。
                message.setMsgResourceKey("6403028");

                //コントロール無効化
                btn_Cancel.setEnabled(false);
                btn_Commit.setEnabled(false);
                btn_View.setEnabled(false);
                img_ArrowDown.setVisible(false);
                img_ArrowUp.setVisible(false);
                img_ArrowLeft.setVisible(false);
                img_ArrowRight.setVisible(false);
                pul_MainMenu.setEnabled(false);
                lbl_Add.setVisible(false);
                lbl_Delete.setVisible(false);
                ltb_FunctionOrder.setEnabled(false);
                ltb_FunctionOrder2.setEnabled(false);

                return;
            }

            //DBより取得
            MainMenu[] mainMenu_Array = menuHandler.findAll(1);

            if (mainMenu_Array != null)
            {
                //プルダウンデータの設定
                for (int i = 0; i < mainMenu_Array.length; i++)
                {
                    String menuId = mainMenu_Array[i].getMainMenuId();
                    String menuResourcKey = mainMenu_Array[i].getMenuResourceKey();
                    String menuName = menuResourcKey;
                    if (!"".equals(menuResourcKey))
                    {
                        try
                        {
                            menuName = DispResources.getText(menuResourcKey);
                        }
                        catch (MissingResourceException mre)
                        {
                        }
                    }
                    String dispText = menuId.trim() + ":" + menuName.trim();

                    this.pul_MainMenu.addItem(menuId, null, dispText, false);
                }
                //初期表示するデータのindexを指定
                this.pul_MainMenu.setSelectedIndex(0);
            }
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
     * 表示ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_View_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //リスボックスをクリア
            ltb_FunctionOrder.clearItem();
            ltb_FunctionOrder2.clearItem();

            //コネクション取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            SubMenuHandler subHandler = EmHandlerFactory.getSubMenuHandler(conn);

            //pullDownより選択中の値を取得
            String selectedMenuId = pul_MainMenu.getSelectedValue();
            if (selectedMenuId == null)
            {
                //メインメニューを登録してください。
                message.setMsgResourceKey("6403028");
                return;
            }

            //DBより取得
            SubMenu[] subMenu = subHandler.findByMainMenuId(selectedMenuId, 1);

            if (subMenu == null)
            {
                //DBよりサブメニューが取得できなかった場合
                //指定されたメインメニューのサブメニューは登録されていません。
                message.setMsgResourceKey("6403037");

                //コントロール無効化
                btn_Cancel.setEnabled(false);
                btn_Commit.setEnabled(false);
                img_ArrowDown.setVisible(false);
                img_ArrowUp.setVisible(false);
                img_ArrowLeft.setVisible(false);
                img_ArrowRight.setVisible(false);
                lbl_Add.setVisible(false);
                lbl_Delete.setVisible(false);
                ltb_FunctionOrder.setEnabled(false);
                ltb_FunctionOrder2.setEnabled(false);

                return;
            }

            //リストボックスに設定するアイテムの一覧を作成
            ArrayList listlisp = new ArrayList();
            ArrayList notuse_listlisp = new ArrayList();
            for (int i = 0; i < subMenu.length; i++)
            {
                String subMenuId = subMenu[i].getSubMenuId();
                String subMenuResourceKey = subMenu[i].getSubMenuResourceKey();
                String subMenuName = MSG_DEF_CHAR + subMenuResourceKey + MSG_DEF_CHAR;
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
                String dispText = subMenuId.trim() + ":" + subMenuName.trim();

                StringBuffer sb = new StringBuffer();
                sb.append(subMenuId).append(PUL_DELIM_CHAR);
                sb.append(dispText).append(PUL_DELIM_CHAR);

                if (subMenu[i].getSubMenuDisplayOrder() != EmConstants.DISPORDER_NODISPLAY)
                {
                    String selected = null;
                    if (listlisp.size() == 0)
                    {
                        selected = "1";
                    }
                    else
                    {
                        selected = "0";
                    }
                    sb.append(selected);
                    //表示項目リストボックスに設定するアイテムに追加
                    listlisp.add(sb.toString());
                }
                else
                {
                    String selected = null;
                    if (notuse_listlisp.size() == 0)
                    {
                        selected = "1";
                    }
                    else
                    {
                        selected = "0";
                    }
                    sb.append(selected);
                    //未使用項目リストボックスに設定するアイテムに追加
                    notuse_listlisp.add(sb.toString());
                }
            }

            boolean flg = false;
            if (listlisp.size() > 0)
            { //リストボックスデータをリストボックスへセット
                ListBoxHelper.setListBox(ltb_FunctionOrder, listlisp);
                flg = true;
            }
            if (notuse_listlisp.size() > 0)
            {
                //リストボックスデータをリストボックスへセット
                ListBoxHelper.setListBox(ltb_FunctionOrder2, notuse_listlisp);
                flg = true;
            }
            if (!flg)
            {
                //指定されたメインメニューのサブメニューは登録されていません。
                message.setMsgResourceKey("6403037");

                //コントロール無効化
                btn_Cancel.setEnabled(false);
                btn_Commit.setEnabled(false);
                img_ArrowDown.setVisible(false);
                img_ArrowUp.setVisible(false);
                img_ArrowLeft.setVisible(false);
                img_ArrowRight.setVisible(false);
                lbl_Add.setVisible(false);
                lbl_Delete.setVisible(false);
                ltb_FunctionOrder.setEnabled(false);
                ltb_FunctionOrder2.setEnabled(false);

                return;
            }

            if (pul_MainMenu.getSelectedItem() != null)
            {
                //選択されているメインメニューをViewStateに保存
                this.getViewState().setString("Mainmenu", pul_MainMenu.getSelectedItem().getText());
            }
        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * ↑ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void img_ArrowUp_Click(ActionEvent e)
            throws Exception
    {
        //選択した場所のIndexを取得
        List selectindex = ltb_FunctionOrder.getSelectedIndex();
        if (selectindex.size() != 0)
        {
            String stindex = (String)selectindex.get(0);

            //選択した場所が一番上以外ならば処理
            if (!stindex.equals("0"))
            {
                //リスト一覧を取得
                List function_menulist = EManagerUtil.getListBoxItems(ltb_FunctionOrder);
                //選択したアイテムを取得
                List selectitem = ltb_FunctionOrder.getSelectedItems();

                for (int i = 0; i < function_menulist.size(); i++)
                {
                    //選択したアイテムをリスト内で検索
                    if (function_menulist.get(i).equals(selectitem.get(0)))
                    {
                        //選択したアイテムを手前のアイテムと入れ替える処理
                        function_menulist.set(i, function_menulist.get(i - 1));
                        function_menulist.set(i - 1, selectitem.get(0));
                        break;
                    }
                }
            }
        }
    }

    /** 
     * ↓ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void img_ArrowDown_Click(ActionEvent e)
            throws Exception
    {
        //選択した場所のIndexを取得
        List selectindex = ltb_FunctionOrder.getSelectedIndex();

        if (selectindex.size() != 0)
        {
            String stindex = (String)selectindex.get(0);

            //リスト一覧を取得
            List function_menulist = EManagerUtil.getListBoxItems(ltb_FunctionOrder);

            //選択した場所が一番下以外ならば処理
            if (function_menulist.size() != Integer.parseInt(stindex) + 1)
            {
                //選択したアイテムを取得
                List selectitem = ltb_FunctionOrder.getSelectedItems();

                for (int i = 0; i < function_menulist.size(); i++)
                {
                    //選択したアイテムをリスト内で検索
                    if (function_menulist.get(i).equals(selectitem.get(0)))
                    {
                        //選択したアイテムを後ろのアイテムと入れ替える処理
                        function_menulist.set(i, function_menulist.get(i + 1));
                        function_menulist.set(i + 1, selectitem.get(0));
                        break;
                    }
                }
            }
        }
    }


    /** 
     * ←ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void img_ArrowLeft_Click(ActionEvent e)
            throws Exception
    {
        //選択されている項目を取得
        List selected = ltb_FunctionOrder2.getSelectedItems();
        Iterator iterator = selected.iterator();
        while (iterator.hasNext())
        {
            ListBoxItem item = (ListBoxItem)iterator.next();
            //表示項目のリストボックスへ追加
            ltb_FunctionOrder.addItem(item);
            //未使用項目のリストボックスから削除
            ltb_FunctionOrder2.removeItem(item);
        }
    }

    /** 
     * →ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void img_ArrowRight_Click(ActionEvent e)
            throws Exception
    {
        //選択されている項目を取得
        List selected = ltb_FunctionOrder.getSelectedItems();
        Iterator iterator = selected.iterator();
        while (iterator.hasNext())
        {
            ListBoxItem item = (ListBoxItem)iterator.next();
            //未使用項目のリストボックスへ追加
            ltb_FunctionOrder2.addItem(item);
            //表示項目のリストボックスから削除
            ltb_FunctionOrder.removeItem(item);
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
            //メイン機能ID
            ArrayList mainfunctionid = new ArrayList();
            //未使用項目のメイン機能ID
            ArrayList notuse_mainfunctionid = new ArrayList();

            //表示項目リストデータを取得
            List function_menulist = EManagerUtil.getListBoxItems(ltb_FunctionOrder);
            for (int i = 0; i < function_menulist.size(); i++)
            {
                String temp = ltb_FunctionOrder.getItem(i).getText();
                //リストデータを":"で分割
                StringTokenizer st = new StringTokenizer(temp, ":");
                //メイン機能IDの取得
                mainfunctionid.add(st.nextToken());
            }

            //未使用項目リストデータを取得
            List notuse_function_menulist = EManagerUtil.getListBoxItems(ltb_FunctionOrder2);
            for (int m = 0; m < notuse_function_menulist.size(); m++)
            {
                String temp2 = ltb_FunctionOrder2.getItem(m).getText();
                //リストデータを":"で分割
                StringTokenizer st = new StringTokenizer(temp2, ":");
                //未使用項目のメイン機能IDの取得
                notuse_mainfunctionid.add(st.nextToken());
            }

            //コネクション取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            SubMenuHandler subHandler = EmHandlerFactory.getSubMenuHandler(conn);

            //表示項目の機能表示順を登録
            int dispOrderCount = 1;
            for (int j = 0; j < function_menulist.size(); j++)
            {
                SubMenu upDateSubMenu = subHandler.findBySubMenuId((String)mainfunctionid.get(j));
                //存在チェック
                if (upDateSubMenu == null)
                {
                    continue;
                }

                //機能表示順をセット
                upDateSubMenu.setSubMenuDisplayOrder(dispOrderCount);
                // 登録したユーザ情報をセット
                DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();
                if (userinfo != null)
                {
                    UserInfoHandler userhandler = new UserInfoHandler(userinfo);

                    if (userhandler != null)
                    {
                        upDateSubMenu.setUpdateUser(userhandler.getUserID());
                    }
                }
                upDateSubMenu.setUpdateTerminal(this.httpRequest.getRemoteAddr());
                upDateSubMenu.setUpdateKind(2);
                upDateSubMenu.setUpdateProcess(this.getClass().getName());

                //DB更新
                if (subHandler.updateBySubMenuId(upDateSubMenu) == 0)
                {
                    continue;
                }

                //ユーザ情報の作成
                DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
                //項目リストの生成
                List list = new ArrayList();
                list.add(pul_MainMenu.getSelectedItem().getText().substring(
                        pul_MainMenu.getSelectedItem().getText().indexOf(":") + 1));
                list.add(String.valueOf(upDateSubMenu.getSubMenuDisplayOrder()));
                list.add(ltb_FunctionOrder.getItem(j).getText().substring(
                        ltb_FunctionOrder.getItem(j).getText().indexOf(":") + 1));
                list.add(String.valueOf(EmConstants.DB_FLAG_TRUE));
                //オペレーションログ出力
                P11LogWriter logWriter = new P11LogWriter(conn);
                logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_SETTING, list);

                dispOrderCount++;

            }
            //未使用項目の機能表示順を登録
            for (int k = 0; k < notuse_function_menulist.size(); k++)
            {
                SubMenu upDateSubMenu = subHandler.findBySubMenuId((String)notuse_mainfunctionid.get(k));
                //存在チェック
                if (upDateSubMenu == null)
                {
                    continue;
                }

                //未使用項目のメニュー表示順に-1をセット
                upDateSubMenu.setSubMenuDisplayOrder(EmConstants.DISPORDER_NODISPLAY);
                //登録したユーザ情報をセット
                DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();
                if (userinfo != null)
                {
                    UserInfoHandler userhandler = new UserInfoHandler(userinfo);

                    if (userhandler != null)
                    {
                        upDateSubMenu.setUpdateUser(userhandler.getUserID());
                    }
                }
                upDateSubMenu.setUpdateTerminal(this.httpRequest.getRemoteAddr());
                upDateSubMenu.setUpdateKind(2);
                upDateSubMenu.setUpdateProcess(this.getClass().getName());

                //DB更新
                subHandler.updateBySubMenuId(upDateSubMenu);

                //ユーザ情報の作成
                DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
                //項目リストの生成
                List list = new ArrayList();
                list.add(pul_MainMenu.getSelectedItem().getText().substring(
                        pul_MainMenu.getSelectedItem().getText().indexOf(":") + 1));
                list.add(String.valueOf(EmConstants.DISPORDER_NODISPLAY));
                list.add(ltb_FunctionOrder2.getItem(k).getText().substring(
                        ltb_FunctionOrder2.getItem(k).getText().indexOf(":") + 1));
                list.add(String.valueOf(EmConstants.DB_FLAG_FALSE));
                //オペレーションログ出力
                P11LogWriter logWriter = new P11LogWriter(conn);
                logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_SETTING, list);

            }

            //設定
            conn.commit();
            //6401008	更新しました。
            message.setMsgResourceKey("6401008");

            //メンテナンスログに出力するメッセージ
            //サブメニューの表示順序を設定しました。
            String mainteLog_msg = "6400017";

            //メンテナンスログ出力
            EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_MENU, mainteLog_msg, "");

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
        //ViewStateより選択されているメインメニューを取得
        String mainmenu = this.getViewState().getString("Mainmenu");

        if (mainmenu == null)
        {
            return;
        }
        //メインメニューが変更されているか判定
        else if (mainmenu.equals(pul_MainMenu.getSelectedItem().getText()))
        {
            //表示処理
            btn_View_Click(e);
        }
    }
}
//end of class

// $Id: MainMenuOrderBusiness.java 3965 2009-04-06 02:55:05Z admin $

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
import java.util.StringTokenizer;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.ui.control.ListBoxItem;
import jp.co.daifuku.bluedog.util.ListBoxHelper;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.MainMenuHandler;
import jp.co.daifuku.emanager.util.DispResourceMap;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;


/** <jp>
 * メインメニュー表示順変更の画面クラスです。
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
public class MainMenuOrderBusiness
        extends MainMenuOrder
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
    }

    /**
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @throws Exception 
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        //	    //プロダクトタイプをViewStateに保存
        //	    this.getViewState().setString("PRODUCTTYPE",this.request.getParameter(PRODUCTTYPE));
        //	    //メニュータイプをViewStateに保存
        //	    this.getViewState().setString("MENUTYPE",this.request.getParameter(MENUTYPE));

        Connection conn = null;
        try
        {
            //コネクション取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            setListBoxItem(conn);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**
     * データベースからメインメニューを取得し、リストボックスに登録します。
     * @param conn Connection
     * @throws SQLException
     * @throws Exception
     */
    private void setListBoxItem(Connection conn)
            throws SQLException,
                Exception
    {
        MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);

        jp.co.daifuku.emanager.database.entity.MainMenu[] mainMenuArr = menuHandler.findAll(1);

        if (mainMenuArr == null || mainMenuArr.length == 0)
        {
            //メインメニューを登録してください。
            message.setMsgResourceKey("6403028");
            btn_Cancel.setEnabled(false);
            btn_Commit.setEnabled(false);
            img_ArrowDown.setVisible(false);
            img_ArrowUp.setVisible(false);
            ltb_MainMenuOrder.setEnabled(false);
            return;
        }

        ArrayList listlisp = new ArrayList();

        for (int i = 0; i < mainMenuArr.length; i++)
        {
            jp.co.daifuku.emanager.database.entity.MainMenu mainMenu = mainMenuArr[i];
            StringBuffer sb = new StringBuffer();
            sb.append(mainMenu.getMainMenuId());
            sb.append(",");
            sb.append(mainMenu.getMainMenuId());
            sb.append(":");
            String resourceKey = mainMenu.getMenuResourceKey();
            if (!this.isEffectiveResourceKey(resourceKey))
            {
                resourceKey = "LBL-T0101";
            }
            sb.append(DispResourceMap.getText(resourceKey));
            sb.append(",");
            sb.append(i == 0 ? 1
                            : 0);
            listlisp.add(sb.toString());
        }

        //リストボックスデータをリストボックスへセット
        ListBoxHelper.setListBox(ltb_MainMenuOrder, listlisp);
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
     * ↑ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void img_ArrowUp_Click(ActionEvent e)
            throws Exception
    {
        //選択した場所のIndexを取得
        List selectindex = ltb_MainMenuOrder.getSelectedIndex();
        if (selectindex.size() != 0)
        {
            String stindex = (String)selectindex.get(0);

            //選択した場所が一番上以外ならば処理
            if (!stindex.equals("0"))
            {
                //リスト一覧を取得
                List menulist = EManagerUtil.getListBoxItems(ltb_MainMenuOrder);

                //選択したアイテムを取得
                List selectitem = ltb_MainMenuOrder.getSelectedItems();

                for (int i = 0; i < menulist.size(); i++)
                {
                    //選択したアイテムをリスト内で検索
                    if (menulist.get(i).equals(selectitem.get(0)))
                    {
                        //選択したアイテムを手前のアイテムと入れ替える処理
                        menulist.set(i, menulist.get(i - 1));
                        menulist.set(i - 1, selectitem.get(0));
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
        List selectindex = ltb_MainMenuOrder.getSelectedIndex();
        if (selectindex.size() != 0)
        {
            String stindex = (String)selectindex.get(0);

            //リスト一覧を取得
            List menulist = EManagerUtil.getListBoxItems(ltb_MainMenuOrder);

            //選択した場所が一番下以外ならば処理
            if (menulist.size() != Integer.parseInt(stindex) + 1)
            {
                //選択したアイテムを取得
                List selectitem = ltb_MainMenuOrder.getSelectedItems();

                for (int i = 0; i < menulist.size(); i++)
                {
                    //選択したアイテムをリスト内で検索
                    if (menulist.get(i).equals(selectitem.get(0)))
                    {
                        //選択したアイテムを後ろのアイテムと入れ替える処理
                        menulist.set(i, menulist.get(i + 1));
                        menulist.set(i + 1, selectitem.get(0));
                        break;
                    }
                }
            }
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
            // コネクション取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);

            //リストデータを取得
            List menulist = EManagerUtil.getListBoxItems(ltb_MainMenuOrder);
            // 表示順設定用変数
            int dispOrder = 1;
            for (int i = 0; i < menulist.size(); i++)
            {
                ListBoxItem item = (ListBoxItem)menulist.get(i);
                String temp = item.getText();
                //リストデータを":"で分割
                StringTokenizer st = new StringTokenizer(temp, ":");
                //メニューIDの取得
                String menuId = st.nextToken();

                jp.co.daifuku.emanager.database.entity.MainMenu mainMenu = menuHandler.findByMenuId(menuId);

                if (mainMenu == null)
                {
                    continue;
                }

                mainMenu.setMenuDisplayOrder(dispOrder);
                mainMenu.setUpdateKind(UPDATE_KIND_UPDATE);
                mainMenu.setUpdateProcess(this.getClass().getName());

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
                    mainMenu.setUpdateUser(userhandler.getUserID());
                }

                mainMenu.setUpdateTerminal(this.getHttpRequest().getRemoteAddr());

                //ユーザ情報の作成
                DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
                //項目リストの生成
                List list = new ArrayList();
                list.add(String.valueOf(mainMenu.getMenuDisplayOrder()));
                list.add(ltb_MainMenuOrder.getItem(i).getText().substring(
                        ltb_MainMenuOrder.getItem(i).getText().indexOf(":") + 1));
                //オペレーションログ出力
                P11LogWriter logWriter = new P11LogWriter(conn);
                logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_SETTING, list);

                // メインメニュー項目を更新
                if (menuHandler.updateByMainMenuId(mainMenu) != 0)
                {
                    dispOrder++;
                }
            }

            EmConnectionHandler.commit(conn);

            //6401008	更新しました。
            message.setMsgResourceKey("6401008");

            // メンテナンスログ
            EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_MENU, "6400013", "");

            this.setFocus(this.btn_Help);
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
     * 取消ボタンの処理を実装します
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Cancel_Click(ActionEvent e)
            throws Exception
    {
        //リストを削除
        ltb_MainMenuOrder.clearItem();

        Connection conn = null;
        try
        {
            //コネクション取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            // DBからメインメニュー項目を取得しリストボックスに登録しなおす
            setListBoxItem(conn);
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
}
//end of class

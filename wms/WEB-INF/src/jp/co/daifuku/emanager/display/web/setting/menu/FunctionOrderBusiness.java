// $Id: FunctionOrderBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.menu;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.ui.control.ListBoxImpl;
import jp.co.daifuku.bluedog.util.ListBoxHelper;
import jp.co.daifuku.bluedog.util.PulldownHelper;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.Function;
import jp.co.daifuku.emanager.database.entity.MainMenu;
import jp.co.daifuku.emanager.database.entity.SubMenu;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.FunctionHandler;
import jp.co.daifuku.emanager.database.handler.MainMenuHandler;
import jp.co.daifuku.emanager.database.handler.SubMenuHandler;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;


/** <jp>
 * サブメニューボタン表示順変更の画面クラスです。
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
public class FunctionOrderBusiness
        extends FunctionOrder
        implements EmConstants
{
    // Class fields --------------------------------------------------
    /**
     * 
     */
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
        ltb_FunctionMapOrder.setEnabled(true);
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
            //conn = ConnectionManager.getConnection(DATASOURCE_NAME);
            conn = EmConnectionHandler.getPageDbConnection(this);

            MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);
            MainMenu[] main = menuHandler.findAll(1);

            if (main == null)
            {
                //メインメニューを登録してください。
                message.setMsgResourceKey("6403028");

                btn_Cancel.setEnabled(false);
                btn_Commit.setEnabled(false);
                btn_View.setEnabled(false);
                img_ArrowDown.setVisible(false);
                img_ArrowUp.setVisible(false);
                pul_MainMenu.setEnabled(false);
                pul_SubMenu.setEnabled(false);
                ltb_FunctionMapOrder.setEnabled(false);

                return;
            }

            ArrayList mainPull = new ArrayList(10);
            ArrayList subPull = new ArrayList(10);
            String selected = null;
            String value = null;
            String disp = null;
            String fkey = null;

            for (int cnt = 0; cnt < main.length; cnt++)
            {
                // メニューを取得
                MainMenu mainmenu = (MainMenu)main[cnt];

                // メニューIDを取得
                value = mainmenu.getMainMenuId();

                // 表示項目を取得
                disp = EManagerUtil.getTransString(value + ":@" + mainmenu.getMenuResourceKey() + "@");

                // FKEYを取得
                fkey = mainmenu.getMainMenuId();

                if (cnt == 0)
                {
                    selected = "1";
                }
                else
                {
                    selected = "0";
                }

                StringBuffer sb = new StringBuffer();
                sb.append(value).append(PUL_DELIM_CHAR);
                sb.append(disp).append(PUL_DELIM_CHAR);
                sb.append(fkey).append(PUL_DELIM_CHAR);
                sb.append(selected);
                mainPull.add(sb.toString());
            }

            //プルダウンデータをプルダウンへセット
            PulldownHelper.setPullDown(pul_MainMenu, mainPull);

            //連動プルダウンリスト情報が存在する場合、追加する
            pul_MainMenu.addChild(pul_SubMenu);

            SubMenuHandler subHandler = EmHandlerFactory.getSubMenuHandler(conn);
            SubMenu[] sub = subHandler.findAll(1, 1);

            for (int cnt = 0; cnt < sub.length; cnt++)
            {
                // メニューを取得
                SubMenu submenu = (SubMenu)sub[cnt];

                // メニューIDを取得
                value = submenu.getSubMenuId();

                // 表示項目を取得
                disp = EManagerUtil.getTransString(value + ":@" + submenu.getSubMenuResourceKey() + "@");

                // FKEYを取得
                fkey = submenu.getMainMenuId();

                if (cnt == 0)
                {
                    selected = "1";
                }
                else
                {
                    selected = "0";
                }

                StringBuffer sb = new StringBuffer();
                sb.append(value).append(PUL_DELIM_CHAR);
                sb.append(disp).append(PUL_DELIM_CHAR);
                sb.append(fkey).append(PUL_DELIM_CHAR);
                sb.append(selected);
                subPull.add(sb.toString());
            }

            PulldownHelper.setLinkedPullDown(pul_SubMenu, subPull);

        }
        finally
        {
            if (conn != null)
            {
                EmConnectionHandler.closeConnection(conn);
            }
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
            ltb_FunctionMapOrder.clearItem();

            //コネクション取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            if (pul_SubMenu.getSelectedValue() == null)
            {
                //指定されたメインメニューのサブメニューは登録されていないか、未使用項目として設定されています。
                message.setMsgResourceKey("6403032");

                btn_Cancel.setEnabled(false);
                btn_Commit.setEnabled(false);
                img_ArrowDown.setVisible(false);
                img_ArrowUp.setVisible(false);
                ltb_FunctionMapOrder.setEnabled(false);

                return;
            }

            FunctionHandler functionHandler = EmHandlerFactory.getFunctionHandler(conn);
            Function[] func = functionHandler.findBySubMenuId(pul_SubMenu.getSelectedValue());

            ArrayList listbox = new ArrayList(10);
            String value = null;
            String disp = null;
            String selected = null;

            if (func != null)
            {
                for (int cnt = 0; cnt < func.length; cnt++)
                {
                    // 機能取得
                    Function function = func[cnt];

                    StringBuffer sb = new StringBuffer();

                    value = function.getFunctionId();

                    disp = EManagerUtil.getTransString(value + ":@" + function.getButtonResourceKey() + "@");

                    if (cnt == 0)
                    {
                        selected = "1";
                    }
                    else
                    {
                        selected = "0";
                    }

                    sb.append(value).append(PUL_DELIM_CHAR);
                    sb.append(disp).append(PUL_DELIM_CHAR);
                    sb.append(selected);
                    listbox.add(sb.toString());

                }
            }

            if (listbox.size() == 0)
            {
                //指定されたサブメニューのボタンは登録されていません。
                message.setMsgResourceKey("6403031");
                return;
            }
            else
            {
                //リストボックスデータをリストボックスへセット
                ListBoxHelper.setListBox(ltb_FunctionMapOrder, listbox);
            }

            //選択されているサブメニューをViewStateに保存
            if (pul_SubMenu.getSelectedItem() != null)
            {
                this.getViewState().setString("Submenu", pul_SubMenu.getSelectedItem().getText());
            }

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
     * ↑ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void img_ArrowUp_Click(ActionEvent e)
            throws Exception
    {
        //選択した場所のIndexを取得
        List selectindex = ltb_FunctionMapOrder.getSelectedIndex();
        if (selectindex.size() != 0)
        {
            String stindex = (String)selectindex.get(0);

            //選択した場所が一番上以外ならば処理
            if (!stindex.equals("0"))
            {
                //リスト一覧を取得
                List function_menulist = EManagerUtil.getListBoxItems(ltb_FunctionMapOrder);
                //List function_menulist = ((ListBoxImpl)ltb_FunctionMapOrder).getListBoxItems();

                //選択したアイテムを取得
                List selectitem = ltb_FunctionMapOrder.getSelectedItems();

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
        List selectindex = ((ListBoxImpl)ltb_FunctionMapOrder).getSelectedIndex();
        if (selectindex.size() != 0)
        {
            String stindex = (String)selectindex.get(0);

            //リスト一覧を取得
            List function_menulist = EManagerUtil.getListBoxItems(ltb_FunctionMapOrder);
            //List function_menulist = ((ListBoxImpl)ltb_FunctionMapOrder).getListBoxItems();

            //選択した場所が一番下以外ならば処理
            if (function_menulist.size() != Integer.parseInt(stindex) + 1)
            {
                //選択したアイテムを取得
                List selectitem = ltb_FunctionMapOrder.getSelectedItems();

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
            //機能ID
            ArrayList functionid = new ArrayList();

            //リストデータを取得
            List functionmaplist = EManagerUtil.getListBoxItems(ltb_FunctionMapOrder);

            for (int i = 0; i < functionmaplist.size(); i++)
            {
                String temp = ltb_FunctionMapOrder.getItem(i).getText();
                //リストデータを":"で分割
                StringTokenizer st = new StringTokenizer(temp, ":");
                //機能IDの取得
                functionid.add(st.nextToken());
            }

            //コネクション取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            FunctionHandler functionHandler = EmHandlerFactory.getFunctionHandler(conn);

            DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();
            UserInfoHandler userhandler = new UserInfoHandler(userinfo);

            String userId = userhandler.getUserID();
            String ipAddr = this.getHttpRequest().getRemoteAddr();

            int order = 1;

            for (int j = 0; j < functionmaplist.size(); j++)
            {

                Function function = functionHandler.findByFunctionId((String)functionid.get(j));

                if (function != null)
                {
                    function.setButtonDisplayOrder(order); // サブメニューボタン表示順
                    function.setUpdateUser(userId); // 更新ユーザ
                    function.setUpdateTerminal(ipAddr); // 更新端末IP
                    function.setUpdateKind(2); // 更新区分（1:登録、2:修正）
                    function.setUpdateProcess(this.getClass().getName()); // 更新処理名

                    // 更新
                    functionHandler.updateByFunctionId(function);

                    //ユーザ情報の作成
                    DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
                    //項目リストの生成
                    List list = new ArrayList();
                    list.add(pul_MainMenu.getSelectedItem().getText().substring(
                            pul_MainMenu.getSelectedItem().getText().indexOf(":") + 1));
                    list.add(pul_SubMenu.getSelectedItem().getText().substring(
                            pul_SubMenu.getSelectedItem().getText().indexOf(":") + 1));
                    list.add(String.valueOf(order));
                    list.add(ltb_FunctionMapOrder.getItem(j).getText().substring(
                            ltb_FunctionMapOrder.getItem(j).getText().indexOf(":") + 1));
                    //オペレーションログ出力
                    P11LogWriter logWriter = new P11LogWriter(conn);
                    logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_SETTING, list);

                    // 表示順序更新
                    order++;
                }
            }

            //設定
            conn.commit();

            // メンテナンスログ挿入
            EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_MENU, "6400021", "");

            //6401008	更新しました。
            message.setMsgResourceKey("6401008");
        }
        catch (Exception ex)
        {
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

    /** 
     * 取消ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Cancel_Click(ActionEvent e)
            throws Exception
    {
        //ViewStateより選択されているサブメニューを取得
        String submenu = this.getViewState().getString("Submenu");
        if (submenu == null)
        {
            return;
        }
        //サブメニューが変更されているか判定
        else if (submenu.equals(pul_SubMenu.getSelectedItem().getText()))
        {
            //表示処理
            btn_View_Click(e);
        }
    }
}
//end of class

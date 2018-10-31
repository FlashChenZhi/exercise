// $Id: Function1Business.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.menu;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.PulldownHelper;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.Function;
import jp.co.daifuku.emanager.database.entity.MainMenu;
import jp.co.daifuku.emanager.database.entity.SubmenuFunction;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.MainMenuHandler;
import jp.co.daifuku.emanager.database.handler.SubmenuFunctionHandler;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

/**
 * <jp> サブメニューボタン設定1画面目の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
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
 * <TD>2004/12/1</TD>
 * <TD>T.Torigaki(DFK)</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author $Author: admin $ </en>
 */
public class Function1Business
        extends Function1
        implements EmConstants
{
    // Class fields --------------------------------------------------
    /**
     * SCROLLHANDLERをセッションに保持するキー
     */
    //private final String KEY_SCROLLHANDLER = "KEY_SCROLLHANDLER";
    /**
     * メニューID・メイン機能IDが紐付いていない項目を表示させるプルダウンアイテムの値
     */
    private final String NOTBELONG = "9999";

    /**
     * 
     */
    protected final String PUL_DELIM_CHAR = ",";

    /**
     * 
     */
    protected final String MSG_DEF_CHAR = "@";

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /**
     * 各コントロールイベント呼び出し前に呼び出されます。
     * 
     * @param e ActionEvent
     * @exception Exception
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        // 初回はリクエストから取得
        String menuparam = this.getHttpRequest().getParameter(MENUPARAM);

        if (menuparam == null)
        {
            // ViewStateから情報を取得
            menuparam = this.viewState.getString(MENUPARAM);
        }
        else
        {
            // リクエストから取得した情報をViewStateに保存
            this.viewState.setString(EmConstants.MENUPARAM, menuparam);
        }


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

        // プルダウンにフォーカス
        setFocus(pul_MainMenu);

        lbl_FunctionMapInfoMsg2.setVisible(false);
    }

    /**
     * 画面の初期化を行います。
     * 
     * @param e ActionEvent
     * @exception Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        //		// メニューから遷移
        //		if (this.request.getParameter(PRODUCTTYPE) != null)
        //		{
        //			// プロダクトタイプをViewStateに保存
        //			this.getViewState().setString("PRODUCTTYPE", this.request.getParameter(PRODUCTTYPE));
        //			// メニュータイプをViewStateに保存
        //			this.getViewState().setString("MENUTYPE", this.request.getParameter(MENUTYPE));
        //		}
        //		// ２画面目から遷移
        //		else
        //		{
        //			// プロダクトタイプをViewStateに保存
        //			this.getViewState().setString("PRODUCTTYPE",
        //					this.getViewState().getString("ProductType2"));
        //			// メニュータイプをViewStateに保存
        //			this.getViewState().setString("MENUTYPE", this.getViewState().getString("MenuType2"));
        //		}

        Connection conn = null;

        try
        {
            // コネクション取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);
            MainMenu[] main = menuHandler.findAll(1);

            // メニューID・メイン機能IDが紐付いていない項目を表示させるプルダウンアイテム
            String item = NOTBELONG + "," + DispResources.getText("PUL-T0001") + "," + " ," + "0";

            ArrayList pull = new ArrayList(10);
            String value = null;
            String disp = null;
            String fkey = null;
            String selected = null;

            for (int cnt = 0; cnt < main.length; cnt++)
            {
                // メニューを取得
                MainMenu mainmenu = (MainMenu)main[cnt];

                // メニューIDを取得
                value = mainmenu.getMainMenuId();

                // 表示項目を取得
                disp = EManagerUtil.getTransString(value + ":@" + mainmenu.getMenuResourceKey() + "@");

                // トリム文字列を取得
                fkey = null;

                if (fkey == null)
                {
                    fkey = "";
                }

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
                pull.add(sb.toString());
            }

            // Extraアイテムの追加
            pull.add(EManagerUtil.getTransString(item));

            // プルダウンデータをプルダウンへセット
            PulldownHelper.setPullDown(pul_MainMenu, pull);

            // ViewStateよりメニューIDを取得
            String menuid = this.getViewState().getString("MenuId");
            if (menuid == null)
            {
                // 初回起動時
                pul_MainMenu.setSelectedIndex(0);
            }
            else
            {
                // 前回選択していたメニューを選択
                pul_MainMenu.setSelectedIndex(Integer.parseInt(menuid));
            }

            // ２画面目から遷移した場合、前回表示のリストを出す
            if (this.getViewState().getString("ActiveCol") != null)
            {
                // リスト表示処理
                btn_View_Click(e);
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

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    // Private methods -----------------------------------------------
    /**
     * リストセルに値をセットします
     * 
     * @param conn Connection
     * @param next_index int
     * @param next_end int
     * @throws Exception
     */
    // private void setList(ScrollHandler handle, int next_index, int next_end, Connection conn)
    // throws Exception
    private void setList(int next_index, int next_end, Connection conn)
            throws Exception
    {

        // メインメニューのメニューIDを取得
        String mainMenuId = pul_MainMenu.getSelectedValue();

        // DBからデータの取得
        SubmenuFunctionHandler handler = EmHandlerFactory.getSubmenuFunctionHandler(conn);
        SubmenuFunction submenuFunctionEntities[] = null;
        if (mainMenuId.equals(NOTBELONG))
        {
            submenuFunctionEntities = handler.findSubmenuFunctionByMenuId(mainMenuId, 0, 1);
        }
        else
        {
            submenuFunctionEntities = handler.findSubmenuFunctionByMenuId(mainMenuId, 0, 0);
        }

        // リストをクリア
        lst_FunctionMap.clearRow();

        // データが存在しないときは終了する。
        if (submenuFunctionEntities == null)
        {
            // データに不正がありました。
            message.setMsgResourceKey("6404001");
            return;
        }

        for (int i = 0; i < submenuFunctionEntities.length; i++)
        {
            lst_FunctionMap.addRow();
            lst_FunctionMap.setCurrentRow(lst_FunctionMap.getMaxRows() - 1);
            int colCount = 1;
            Function functionEntities[] = submenuFunctionEntities[i].getFunctionArray();

            // タイトルの設定
            String subMenuTitile = "";
            try
            {
                subMenuTitile = DispResources.getText(submenuFunctionEntities[i].getSubMenuResourceKey());
            }
            catch (MissingResourceException ex)
            {
                subMenuTitile = DispResources.getText("LBL-T0101");
            }

            lst_FunctionMap.setValue(1, subMenuTitile);

            StringBuffer toolTipText = new StringBuffer("\n");

            List functionid_list = new ArrayList();
            functionid_list.add(submenuFunctionEntities[i].getSubMenuId());

            for (int j = 0; functionEntities != null && j < functionEntities.length; j++)
            {
                // ボタン文字列をセット
                //				String buttonText = DispResources.getText(functionEntities[j].getButtonResourceKey());
                String buttonText = functionEntities[j].getButtonResourceKey();
                try
                {
                    buttonText = DispResources.getText(functionEntities[j].getButtonResourceKey());
                }
                catch (MissingResourceException ex)
                {
                }
                lst_FunctionMap.setValue(colCount + 1, buttonText);

                // ToolTip 文字列をセット
                toolTipText.append(" " + buttonText + " \t" + DispResources.getText("LBL-T0017") + " : "
                        + functionEntities[j].getFunctionId() + " \n");

                functionid_list.add(functionEntities[j].getFunctionId());

                // 列位置カウントアップ
                colCount++;
            }
            // ToolTipをセット
            lst_FunctionMap.setToolTip(lst_FunctionMap.getCurrentRow(), toolTipText.toString());

            // 隠しパラメータをセット
            String functionids =
                    CollectionUtils.getConnectedString(Arrays.asList(functionid_list.toArray(new String[functionid_list.size()])));
            lst_FunctionMap.setValue(0, functionids);
        }
    }

    // Event handler methods -----------------------------------------

    /**
     * メニューへボタンが押下されたときに呼ばれます
     * 
     * @param e ActionEvent
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
     * 表示ボタンが押下されたときに呼ばれます
     * 
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_View_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            // メインメニューのメニューIDを取得
            String[] menuid = new String[2];
            menuid[0] = pul_MainMenu.getSelectedValue();

            int total = 0;
            // メニューID・メイン機能IDが紐付いていない項目
            String other = NOTBELONG;

            // 
            conn = EmConnectionHandler.getPageDbConnection(this);
            SubmenuFunctionHandler subMenuFunction = EmHandlerFactory.getSubmenuFunctionHandler(conn);

            // その他項目の場合
            if (menuid[0].equals(other))
            {
                // データベースのデータ数合計
                total = subMenuFunction.findSubmenuFunctionCountByMenuId(menuid[0], 0, 1);

            }
            else
            {
                total = subMenuFunction.findSubmenuFunctionCountByMenuId(menuid[0], 0, 0);
            }

            // 最初のページの表示終了位置
            int end = 0;
            // データがある場合
            if (total > 0)
            {
                end = total;

                // メッセージラベルを表示
                lbl_FunctionMapInfoMsg2.setVisible(true);

                // 設定するボタンを選択してください
                lbl_FunctionMapInfoMsg2.setResourceKey("MSG-T0048");

                // リストを表示
                lst_FunctionMap.setVisible(true);

                // リストデータをセット
                setList(0, end, conn);
            }
            // データがない場合
            else
            {
                // リストを隠す
                lst_FunctionMap.clearRow();
                lst_FunctionMap.setVisible(false);

                if (menuid[0].equals(other))
                {
                    // メッセージラベルを表示
                    lbl_FunctionMapInfoMsg2.setVisible(true);
                    // 対象データはありませんでした
                    lbl_FunctionMapInfoMsg2.setResourceKey("MSG-T0010");
                }
                else
                {
                    // 指定されたメインメニューのサブメニューは登録されていません。
                    message.setMsgResourceKey("6403037");
                }
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
     * リストが押下されたときに呼ばれます
     * 
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_FunctionMap_Click(ActionEvent e)
            throws Exception
    {
        // 現在の行をセット
        lst_FunctionMap.setCurrentRow(lst_FunctionMap.getActiveRow());
        // 列取得
        int activeCol = lst_FunctionMap.getActiveCol();

        String buf = lst_FunctionMap.getValue(0);
        // 機能IDを取得
        String functionid = CollectionUtils.getString(activeCol - 1, buf);
        // メイン機能IDを取得
        String mainfunctionid = CollectionUtils.getString(0, buf);

        // 選択列をViewStateに保存
        this.getViewState().setString("ActiveCol", Integer.toString(activeCol));

        // メイン機能IDをViewStateに保存
        this.getViewState().setString("MainFunctionId", mainfunctionid);
        // 機能IDをViewStateに保存
        this.getViewState().setString("FunctionId", functionid);
        // 機能名をViewStateに保存
        this.getViewState().setString("FunctionName", lst_FunctionMap.getValue(1));

        // 選択しているメニューIDをViewStateに保存
        this.getViewState().setString("MenuId", Integer.toString(pul_MainMenu.getSelectedIndex()));

        // 2画面目に遷移
        forward("/emanager/setting/menu/Function2.do");
    }
}
// end of class

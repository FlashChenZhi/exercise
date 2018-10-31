// $Id: MainMenuListBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.menu.listbox;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.MainMenu;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.MainMenuHandler;
import jp.co.daifuku.emanager.util.DispResourceMap;
import jp.co.daifuku.emanager.util.EmProperties;

/** <jp>
 * メニュー一覧の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
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
public class MainMenuListBusiness
        extends MainMenuList
        implements EmConstants
{
    // Class fields --------------------------------------------------
    /** 
     * メニューIDの受け渡しに使用するキーです
     */
    public static final String MENUID_KEY = "MENUID_KEY";

    /** 
     * メニューリソースキーの受け渡しに使用するキーです
     */
    public static final String MENURESOURCE_KEY = "MENURESOURCE_KEY";

    /** 
     * メニュー名の受け渡しに使用するキーです
     */
    public static final String MENUNAME_KEY = "MENUNAME_KEY";

    /** 
     * アイコン表示方法の受け渡しに使用するキーです
     */
    public static final String SHOWTYPE_KEY = "SHOWTYPE_KEY";

    /**
     * 親画面が必要するリソースキーを判別するフラグです
     */
    public static final String BTNFLAG_KEY = "BTNFLAG_KEY";


    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /** 
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @exception Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {

        //画面名をセットする
        lbl_ListName.setText(DispResources.getText("TLE-T0002"));

        Connection conn = null;
        try
        {
            //Pager初期化
            setPagerValue(0, 0, 0);

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);

            //呼び出し元画面でセットされたパラメータの取得
            String dispflag = request.getParameter(BTNFLAG_KEY);
            if (dispflag == null)
            {
                dispflag = "";
            }

            //ViewStateに保存
            this.getViewState().setString("BTFLAG", dispflag);

            int total = 0;
            //親画面のサブメニュー設定の場合
            if (dispflag.equals("btn_P_MenuId"))
            {
                //呼び出し元画面でセットされたパラメータの取得
                String[] menuid = new String[1];
                menuid[0] = this.request.getParameter(MENUID_KEY);
                //ViewStateに保存
                this.getViewState().setString("MenuId", menuid[0]);

                //メニューIDテキストボックスの値によって場合分け
                if (menuid[0].equals(""))
                {
                    //データ数合計
                    total = menuHandler.findCount();
                }
                else if (menuid[0].indexOf("*") > -1)
                {
                    //「*」を「%」に置換
                    menuid[0] = menuid[0].replace('*', '%');
                    //データ数合計
                    total = menuHandler.finCountLike(menuid[0]);
                }
                else
                {
                    //データ数合計
                    total = menuHandler.findCountGreaterThan(menuid[0]);
                }
            }
            //親画面がメインメニュー設定の場合
            else
            {
                //データ数合計
                total = menuHandler.findCount();
            }

            //1ページの表示件数
            int page = Integer.parseInt(EmProperties.getProperty("ListboxSearchCount"));
            //最初のページの表示終了位置
            int end = 0;
            //データがある場合
            if (total > 0)
            {
                if (total <= page)
                {
                    end = total;
                }
                else
                {
                    end = page;
                }
                //リストデータをセット
                setList(menuHandler, 0, end);
                //pagerに値をセット
                setPagerValue(1, total, page);
            }
            else
            {
                //Pagerへの値セット
                pager.setMax(0); //最大件数
                pager.setPage(0); //1Page表示件数
                pager.setIndex(0); //開始位置
                //ヘッダーを隠します
                lst_MainMenuList.setVisible(false);
                //対象データはありませんでした
                message.setMsgResourceKey("6403077");
            }
        }
        catch (Exception ex)
        {
            throw ex;
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
     * リストセルに値をセットします
     * @param menuHandler ScrollHandler
     * @param next_index int
     * @param next_end int 
     * @throws Exception 
     */
    private void setList(MainMenuHandler menuHandler, int next_index, int next_end)
            throws Exception
    {
        //ViewStateから取得
        String dispflag = this.getViewState().getString("BTFLAG");
        MainMenu[] mainMenuArr = null;
        List list = new ArrayList();
        //親画面のサブメニュー設定の場合
        if (dispflag.equals("btn_P_MenuId"))
        {
            //呼び出し元画面でセットされたパラメータの取得
            String menuid = this.getViewState().getString("MenuId");

            //メニューIDテキストボックスの値によって場合分け
            if (menuid.equals(""))
            {
                //データベースからリストデータを取得
                mainMenuArr = menuHandler.findMenuLike("%", next_index + 1, next_end);
            }
            else if (menuid.indexOf("*") > -1)
            {
                //「*」を「%」に置換
                menuid = menuid.replace('*', '%');
                //データベースからリストデータを取得
                mainMenuArr = menuHandler.findMenuLike(menuid, next_index + 1, next_end);
            }
            else
            {
                //データベースからリストデータを取得
                mainMenuArr = menuHandler.findGreaterThan(menuid, next_index + 1, next_end);
            }
        }
        //親画面がメインメニュー設定の場合
        else
        {
            //データベースからリストデータを取得
            mainMenuArr = menuHandler.findMenuLike("%", next_index + 1, next_end);
        }

        //表を削除
        lst_MainMenuList.clearRow();

        if (mainMenuArr == null)
        {
            //Pagerへの値セット
            pager.setMax(0); //最大件数
            pager.setPage(0); //1Page表示件数
            pager.setIndex(0); //開始位置
            //ヘッダーを隠します
            lst_MainMenuList.setVisible(false);
            //対象データはありませんでした
            message.setMsgResourceKey("6403077");
            return;
        }

        for (int i = 0; i < mainMenuArr.length; i++)
        {
            list.add(mainMenuArr[i]);
        }

        Iterator itr = list.iterator();
        while (itr.hasNext())
        {
            MainMenu mainMenu = (MainMenu)itr.next();

            int count = lst_MainMenuList.getMaxRows();
            lst_MainMenuList.setCurrentRow(count);
            lst_MainMenuList.addRow();

            lst_MainMenuList.setValue(1, Integer.toString(count + next_index));
            lst_MainMenuList.setValue(2, mainMenu.getMainMenuId());
            lst_MainMenuList.setValue(3, mainMenu.getMenuResourceKey());

            String menuNameResource =
                    this.isEffectiveResourceKey(mainMenu.getMenuResourceKey()) ? mainMenu.getMenuResourceKey()
                                                                              : "LBL-T0101";

            lst_MainMenuList.setValue(4, DispResourceMap.getText(menuNameResource));
        }
    }

    /** 
     * ページャーに値をセットします
     * @param index int
     * @param total int
     * @param page int
     */
    private void setPagerValue(int index, int total, int page)
    {
        pager.setIndex(index);
        pager.setMax(total);
        pager.setPage(page);
    }

    // Event handler methods -----------------------------------------

    /** 
     * 閉じるボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
        //呼び出し元の画面へ遷移します
        parentRedirect(null);
    }

    /** 
     * Pagerの次へボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void pager_Next(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);

            int total = pager.getMax();
            int page = pager.getPage();
            int index = pager.getIndex();
            int next_index = 0;
            int next_end = 0;

            if (index + page * 2 <= total)
            {
                next_index = index + page - 1;
                next_end = index + page * 2 - 1;
            }
            else
            {
                next_index = index + page - 1;
                next_end = total;
            }

            //Pagerに値をセット
            setPagerValue(next_index + 1, total, page);

            //リストデータをセット
            setList(menuHandler, next_index, next_end);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * Pagerの前へボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void pager_Prev(ActionEvent e)
            throws Exception
    {
        Connection conn = null;

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);

            int total = pager.getMax();
            int page = pager.getPage();
            int index = pager.getIndex();
            int next_index = 0;
            int next_end = 0;

            if (index - page <= 0)
            {
                next_index = 0;
                next_end = page;
            }
            else
            {
                next_index = index - page - 1;
                next_end = index - 1;
            }

            //Pagerに値をセット
            setPagerValue(next_index + 1, total, page);

            //リストデータをセット
            setList(menuHandler, next_index, next_end);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * Pagerの最後ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void pager_Last(ActionEvent e)
            throws Exception
    {
        Connection conn = null;

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);
            int total = pager.getMax();
            int page = pager.getPage();

            int next_index = 0;
            int next_end = 0;
            if (total % page == 0)
            {
                next_index = total - page;
                next_end = total;
            }
            else
            {
                next_index = total - (total % page);
                next_end = total;
            }

            //Pagerに値をセット
            setPagerValue(next_index + 1, total, page);

            //リストデータをセット
            setList(menuHandler, next_index, next_end);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * Pagerの最初ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void pager_First(ActionEvent e)
            throws Exception
    {
        Connection conn = null;

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            MainMenuHandler menuHandler = EmHandlerFactory.getMainMenuHandler(conn);

            int total = pager.getMax();
            int page = pager.getPage();

            //Pagerに値をセット
            setPagerValue(1, total, page);

            //リストデータをセット
            setList(menuHandler, 0, page);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * リストが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void lst_MainMenuList_Click(ActionEvent e)
            throws Exception
    {
        //現在の行をセット
        lst_MainMenuList.setCurrentRow(lst_MainMenuList.getActiveRow());

        //呼び出し元の画面へ渡すパラメータ作成
        ForwardParameters param = new ForwardParameters();
        param.setParameter(MENUID_KEY, lst_MainMenuList.getValue(2));
        param.setParameter(MENURESOURCE_KEY, lst_MainMenuList.getValue(3));
        param.setParameter(MENUNAME_KEY, lst_MainMenuList.getValue(4));

        //終了処理
        btn_Close_Click(null);

        //呼び出し元の画面へ遷移します
        parentRedirect(param);
    }

    /**
     * このリソースキーが有効かをチェックします
     * @param resourcekey リソースキー
     * @return チェック結果
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

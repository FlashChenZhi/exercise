// $Id: SubMenuListBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.menu.listbox;

import java.sql.Connection;
import java.util.MissingResourceException;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.MainMenu;
import jp.co.daifuku.emanager.database.entity.SubMenu;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.MainMenuHandler;
import jp.co.daifuku.emanager.database.handler.SubMenuHandler;
import jp.co.daifuku.emanager.util.EmProperties;


/** <jp>
 * サブメニュー一覧の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
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
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </en> */
public class SubMenuListBusiness
        extends SubMenuList
        implements EmConstants
{
    // Class fields --------------------------------------------------

    /** 
     * メイン機能IDの受け渡しに使用するキーです
     */
    public static final String MAINFUNCTIONID_KEY = "MAINFUNCTIONID_KEY";

    /** 
     * メニュー名の受け渡しに使用するキーです
     */
    public static final String MENUNAME_KEY = "MENUNAME_KEY";

    /** 
     * 機能リソースキーの受け渡しに使用するキーです
     */
    public static final String FUNCTIONRESOURCE_KEY = "FUNCTIONRESOURCE_KEY";

    /** 
     * 機能名の受け渡しに使用するキーです
     */
    public static final String FUNCTIONNAME_KEY = "FUNCTIONNAME_KEY";

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
        lbl_ListName.setText(DispResources.getText("TLE-T0006"));

        Connection conn = null;
        try
        {
            //Pager初期化
            setPagerValue(0, 0, 0);

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            MainMenuHandler mainHandler = EmHandlerFactory.getMainMenuHandler(conn);
            SubMenuHandler subHandler = EmHandlerFactory.getSubMenuHandler(conn);

            //データ数合計
            int total = this.getListCount(mainHandler, subHandler);
            //1ページの表示件数
            int page = Integer.parseInt(EmProperties.getProperty("ListboxSearchCount"));
            //最初のページの表示終了位置
            int end = 0;

            //データがある場合
            if (total > 0)
            {
                //リストに表示
                if (total <= page)
                {
                    end = total;
                }
                else
                {
                    end = page;
                }

                //pagerに値をセット
                setPagerValue(1, total, page);

                //Listにセット
                setList(mainHandler, subHandler, 0, end);
            }
            else
            {
                //Pagerへの値セット
                pager.setMax(0); //最大件数
                pager.setPage(0); //1Page表示件数
                pager.setIndex(0); //開始位置				
                //ヘッダーを隠します
                lst_SubmenuList.setVisible(false);
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
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /** 
     * リストセルに値をセットします
     * @param mainHandler MainMenuHandler
     * @param subHandler SubMenuHandler
     * @param next_index int
     * @param next_end int 
     * @throws Exception 
     */
    private void setList(MainMenuHandler mainHandler, SubMenuHandler subHandler, int next_index, int next_end)
            throws Exception
    {
        //リストをクリア
        this.lst_SubmenuList.clearRow();

        //メインメニューを取得
        MainMenu[] mainMenu_Array = mainHandler.findAll(0);
        //メインメニューを取得できたか確認
        if (mainMenu_Array == null)
        {
            setPagerValue(0, 0, 0);
            this.lst_SubmenuList.setVisible(false);
            return;
        }

        int rowCount = 1;

        next_end += 1;
        next_index += 1;

        //１件以上表示するデータがあるか
        boolean dispList = false;

        for (int i = 0; i < mainMenu_Array.length; i++)
        {
            //サブメニューを取得
            SubMenu[] subMenu_Array = subHandler.findByMainMenuId(mainMenu_Array[i].getMainMenuId(), 0);
            //サブメニューを取得できたか確認
            if (subMenu_Array == null)
            {
                continue;
            }

            int subStart = 0;
            int subEnd = subMenu_Array.length;

            if (rowCount < next_index)
            {
                subStart += next_index - rowCount;
            }
            if (next_end < rowCount + subMenu_Array.length)
            {
                subEnd += next_end - (rowCount + subMenu_Array.length);
            }

            for (int j = subStart; j < subEnd; j++)
            {
                //リストに行を追加
                this.lst_SubmenuList.addRow();

                this.lst_SubmenuList.setCurrentRow(lst_SubmenuList.getMaxRows() - 1);
                //各項目に値を設定
                //メニュー名
                this.lst_SubmenuList.setValue(1, this.getDispText(mainMenu_Array[i].getMenuResourceKey(),
                        mainMenu_Array[i].getMenuResourceKey()));
                //No
                this.lst_SubmenuList.setValue(2, Integer.toString(rowCount + j));
                //メイン機能ID
                this.lst_SubmenuList.setValue(3, subMenu_Array[j].getSubMenuId());
                //機能リソースキー
                this.lst_SubmenuList.setValue(4, subMenu_Array[j].getSubMenuResourceKey());
                //機能名
                this.lst_SubmenuList.setValue(5, this.getDispText(subMenu_Array[j].getSubMenuResourceKey(), ""));

                //１件以上表示する列がある。
                dispList = true;
            }

            rowCount += subMenu_Array.length;
        }

        if (!dispList)
        {
            //表示する列が１件もない場合
            setPagerValue(0, 0, 0);
            this.lst_SubmenuList.clearRow();
            this.lst_SubmenuList.setVisible(false);
        }
    }

    /**
     * ResourceKeyに対応する文字列を取得します。
     * 見つからなかったdefaultStringを返却します。。
     * @param ResourceKey リソースキー
     * @param defaultString 見つからない場合に表示する文字列
     * @return 文字列
     */
    private String getDispText(String ResourceKey, String defaultString)
    {
        String dispText = defaultString;
        if (!"".equals(ResourceKey))
        {
            try
            {
                dispText = DispResources.getText(ResourceKey);
            }
            catch (MissingResourceException mre)
            {
            }
        }
        return dispText;
    }

    /**
     * 検索結果のカウントを返却します。
     * @param mainHandler MainMenuHandler
     * @param subHandler SubMenuHandler
     * @return カウント
     * @throws Exception
     */
    private int getListCount(MainMenuHandler mainHandler, SubMenuHandler subHandler)
            throws Exception
    {
        int count = 0;

        MainMenu[] mainMenu_Array = mainHandler.findAll(0);

        for (int i = 0; i < mainMenu_Array.length; i++)
        {
            count += subHandler.findCountByMainMenuId(mainMenu_Array[i].getMainMenuId());
        }

        return count;
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
        Connection conn = null;
        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            MainMenuHandler mainHandler = EmHandlerFactory.getMainMenuHandler(conn);
            SubMenuHandler subHandler = EmHandlerFactory.getSubMenuHandler(conn);
            setList(mainHandler, subHandler, next_index, next_end);
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
        Connection conn = null;
        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            MainMenuHandler mainHandler = EmHandlerFactory.getMainMenuHandler(conn);
            SubMenuHandler subHandler = EmHandlerFactory.getSubMenuHandler(conn);
            setList(mainHandler, subHandler, next_index, next_end);
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
        Connection conn = null;
        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            MainMenuHandler mainHandler = EmHandlerFactory.getMainMenuHandler(conn);
            SubMenuHandler subHandler = EmHandlerFactory.getSubMenuHandler(conn);
            setList(mainHandler, subHandler, next_index, next_end);
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
        int total = pager.getMax();
        int page = pager.getPage();

        //Pagerに値をセット
        setPagerValue(1, total, page);

        //リストデータをセット
        Connection conn = null;
        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            MainMenuHandler mainHandler = EmHandlerFactory.getMainMenuHandler(conn);
            SubMenuHandler subHandler = EmHandlerFactory.getSubMenuHandler(conn);
            setList(mainHandler, subHandler, 0, page);
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
    public void lst_SubmenuList_Click(ActionEvent e)
            throws Exception
    {
        //現在の行をセット
        lst_SubmenuList.setCurrentRow(lst_SubmenuList.getActiveRow());

        //呼び出し元の画面へ渡すパラメータ作成
        ForwardParameters param = new ForwardParameters();
        param.setParameter(MENUNAME_KEY, lst_SubmenuList.getValue(1));
        param.setParameter(MAINFUNCTIONID_KEY, lst_SubmenuList.getValue(3));
        param.setParameter(FUNCTIONRESOURCE_KEY, lst_SubmenuList.getValue(4));
        param.setParameter(FUNCTIONNAME_KEY, lst_SubmenuList.getValue(5));

        //終了処理
        btn_Close_Click(null);

        //呼び出し元の画面へ遷移します
        parentRedirect(param);
    }

}
//end of class

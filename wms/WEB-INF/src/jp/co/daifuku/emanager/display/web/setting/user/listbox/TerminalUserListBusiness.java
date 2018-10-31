// $Id: TerminalUserListBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.user.listbox;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.UserHandler;
import jp.co.daifuku.emanager.display.web.setting.user.TerminalBusiness;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.EmProperties;

/** <jp>
 * ツールが生成した画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
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
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 </en> */
public class TerminalUserListBusiness
        extends TerminalUserList
{
    // Class fields --------------------------------------------------
    /** 選択されたユーザIDのリストを保持するキー */
    public static final String SELECTED_USER_NO = "SELECTED_USER_NO";

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /**
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @throws Exception 
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        // 画面名称をセットする
        this.lbl_ListName.setResourceKey("LBL-T0096");

        Connection conn = null;

        // 親画面で選択されている端末ユーザの配列をArrayListに変換して取得
        String selectedUserIdList =
                EManagerUtil.getCsvFromStringArray(this.request.getParameterValues(SELECTED_USER_NO));
        // ViewState に保存
        this.viewState.setString(SELECTED_USER_NO, selectedUserIdList);

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);

            int total = userHandler.findCountAllNotAdmin();

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
                setList(userHandler, 0, end);
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
                lst_UserChange.setVisible(false);

                //対象データはありませんでした
                message.setMsgResourceKey("6403077");

            }
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * リストセルに値をセットします
     * @param userHandler UserHandler
     * @param next_index int
     * @param next_end int 
     * @throws Exception 
     */
    private void setList(UserHandler userHandler, int next_index, int next_end)
            throws Exception
    {
        // 親画面で選択されている端末No.の配列を取得
        List selectedUserIdList =
                Arrays.asList(EManagerUtil.getStringArrayFromCsv(this.viewState.getString(SELECTED_USER_NO)));

        jp.co.daifuku.emanager.database.entity.User[] userArr = userHandler.findAllNotAdmin(next_index, next_end);

        this.lst_UserChange.clearRow();

        if (userArr == null || userArr.length < 1)
        {
            this.lst_UserChange.setVisible(false);
            this.lbl_ListName.setResourceKey("MSG-T0010");
            return;
        }

        for (int i = 0; i < userArr.length; i++)
        {
            jp.co.daifuku.emanager.database.entity.User user = userArr[i];

            int count = this.lst_UserChange.getMaxRows();

            this.lst_UserChange.setCurrentRow(count);
            this.lst_UserChange.addRow();

            this.lst_UserChange.setChecked(1, selectedUserIdList.contains(user.getUserID()));
            this.lst_UserChange.setValue(2, user.getUserID());
            this.lst_UserChange.setValue(3, user.getUserName());
            this.lst_UserChange.setValue(4, user.getRoleID());
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
     * 設定ボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Commit_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            int numOfRow = this.lst_UserChange.getMaxRows();

            ArrayList list = new ArrayList();

            ForwardParameters param = new ForwardParameters();

            if (1 < numOfRow)
            {
                for (int i = 1; i <= numOfRow - 1; i++)
                {
                    this.lst_UserChange.setCurrentRow(i);
                    if (this.lst_UserChange.getChecked(1))
                    {
                        list.add(this.lst_UserChange.getValue(2));
                    }
                }

                String[] selectedUserID = (String[])list.toArray(new String[0]);

                param.setParameter(SELECTED_USER_NO, selectedUserID);
            }
            param.setParameter(TerminalBusiness.CHILD_SCREEN, TerminalBusiness.TERMINAL_USER_MAP);
            parentRedirect(param);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }


    /** 
     * Pagerの次へボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void pager_Next(ActionEvent e)
            throws Exception
    {
        //Handlerにセッションの値をセット
        Connection conn = null;

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);

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
            setList(userHandler, next_index, next_end);
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
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);

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
            setList(userHandler, next_index, next_end);
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
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);

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
            setList(userHandler, next_index, next_end);
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
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);

            int total = pager.getMax();
            int page = pager.getPage();

            //Pagerに値をセット
            setPagerValue(1, total, page);

            //リストデータをセット
            setList(userHandler, 0, page);
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

}
//end of class

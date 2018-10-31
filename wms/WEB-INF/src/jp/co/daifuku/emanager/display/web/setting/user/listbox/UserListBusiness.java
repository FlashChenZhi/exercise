// $Id: UserListBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.user.listbox;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.MissingResourceException;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.entity.User;
import jp.co.daifuku.emanager.database.handler.AuthenticationHandler;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.UserHandler;
import jp.co.daifuku.emanager.util.DispResourceMap;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.ui.web.ToolTipHelper;

/** <jp>
 * ユーザ一覧の画面クラスです。
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
public class UserListBusiness
        extends UserList
        implements EmConstants
{
    // Class fields --------------------------------------------------    
    /** 
     * ユーザIDの受け渡しに使用するキーです
     */
    public static final String USERID_KEY = "USERID_KEY";

    /** 
     * 親画面のメニュータイプの受け渡しに使用するキーです
     */
    public static final String MENUTYPE_KEY = "MENUTYPE_KEY";

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /** <jp>
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @exception Exception
     </jp> */
    /** <en>
     * This screen is initialized.
     * @param e ActionEvent
     * @exception Exception
     </en> */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        //画面名をセットする
        lbl_ListName.setText(DispResources.getText("TLE-T0015"));

        Connection conn = null;
        try
        {
            //Pager初期化
            setPagerValue(0, 0, 0);

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);
            AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);

            //呼び出し元画面でセットされたパラメータの取得
            String userId = this.request.getParameter(USERID_KEY);
            //ViewStateに保存
            this.getViewState().setString("UserId", userId);

            //検索用ユーザIDを作成
            String searchID = getSearchUserID(userId);

            //検索件数を取得
            int total = userHandler.findCountGreaterThan(searchID);

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

                //Pagerに値をセット
                setPagerValue(1, total, page);

                //リストデータをセット
                setList(userHandler, authHandler, 0, end);
            }
            else
            {
                //Pagerへの値セット
                pager.setMax(0); //最大件数
                pager.setPage(0); //1Page表示件数
                pager.setIndex(0); //開始位置				
                //ヘッダーを隠します
                lst_UserList.setVisible(false);
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
     * @param userHandler userHandler
     * @param authHandler
     * @param next_index int
     * @param next_end int 
     * @throws Exception 
     */
    private void setList(UserHandler userHandler, AuthenticationHandler authHandler, int next_index, int next_end)
            throws Exception
    {
        next_index++;

        //ViewStateからユーザIDを取得
        String userId = this.getViewState().getString("UserId");

        //検索用文字列を取得
        String searchId = getSearchUserID(userId);

        //表を削除
        lst_UserList.clearRow();

        String dateFormat = EManagerUtil.getDateFormat(this.httpRequest.getLocale(), EmConstants.F_DATE);
        //SimpleDateFormatインスタンス
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        //No.ボタンに表示する値
        int no = next_index;

        //DBより検索
        User[] user_Array = userHandler.findUserGreaterThan(searchId, next_index, next_end);
        AuthenticationSystem authSystem = authHandler.findAuthenticationSystem();

        //非表示行の設定
        //ユーザロック状態
        boolean dispUserLockColumn = authSystem.getFaieldLoginUserLockFlag();
        this.lst_UserList.setColumnHidden(5, !dispUserLockColumn);
        //パスワード有効期限
        boolean dispPwdExpires = authSystem.getPassExpireFlag();
        this.lst_UserList.setColumnHidden(6, !dispPwdExpires);

        //ユーザを取得できたかを確認　範囲内のデータが無かったか
        if (user_Array == null)
        {
            setPagerValue(0, 0, 0);
            this.lst_UserList.setVisible(false);
            return;
        }

        //１件以上表示するデータがあるか
        boolean dispList = false;

        //表にセット
        for (int i = 0; i < user_Array.length; i++)
        {
            if (user_Array[i] == null)
            {
                continue;
            }

            //行追加
            lst_UserList.addRow();
            //最終行を取得
            lst_UserList.setCurrentRow(lst_UserList.getMaxRows() - 1);

            //表にデータをセット
            //No.
            lst_UserList.setValue(1, Integer.toString(no++));
            //ユーザID
            lst_UserList.setValue(2, user_Array[i].getUserID());
            //ユーザ名
            lst_UserList.setValue(3, user_Array[i].getUserName());
            //ロールID
            lst_UserList.setValue(4, user_Array[i].getRoleID());
// 2008/12/16 K.Matsuda Start ユーザ状態に無効を追加
            //ロック状態
            if (dispUserLockColumn)
            {
                if (user_Array[i].getUserStatus() == EmConstants.USERSTATUS_ACTIVE)
                {
                    // 有効
                    lst_UserList.setValue(5, getDispText("LBL-T0124", "LBL-T0124"));
                }
                else if (user_Array[i].getUserStatus() == EmConstants.USERSTATUS_DISABLED) 
                {
                    // 無効
                    lst_UserList.setValue(5, getDispText("LBL-T0276", "LBL-T0276"));
                }
                else
                {
                    // ロック中
                    lst_UserList.setValue(5, getDispText("LBL-T0125", "LBL-T0125"));
                }
            }
// 2008/12/16 K.Matsuda End
            
            //パスワード有効期限
            if (dispPwdExpires)
            {
                int realPwdInterval = userHandler.getRealPwdChangeIntervaluByUserId(user_Array[i].getUserID());
                if (realPwdInterval == PWDCHANGEINTERVAL_STATUS_NOLIMITED)
                {
                    //パスワード更新間隔が無制限
                    lst_UserList.setValue(6, getDispText("LBL-T0126", "LBL-T0126"));
                }
                else
                {
                    //パスワード期限有
                    if (user_Array[i].getPwdExpire() != null)
                    {
                        lst_UserList.setValue(6, simpleDateFormat.format(user_Array[i].getPwdExpire()));
                    }
                }
            }

            // ToolTip設定
            ToolTipHelper toolTip = new ToolTipHelper();
            // user Name
            toolTip.add(DispResourceMap.getText("LBL-T0140"), user_Array[i].getUserName());

            lst_UserList.setToolTip(i + 1, toolTip.getText());

            //１件以上表示する列がある。
            dispList = true;
        }

        if (!dispList)
        {
            //表示する列が１件もない場合
            setPagerValue(0, 0, 0);
            this.lst_UserList.clearRow();
            this.lst_UserList.setVisible(false);
            return;
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

    /** 
     * 検索用ユーザIDを作成します
     * @param userId UserID
     * @return 検索用ユーザID 
     */
    private String getSearchUserID(String userId)
    {
        return userId;

        // 現状、「*」が入力される予定は無いのでコメントアウト(for eManager2.0)
        /*
         if (userId == null || "".equals(userId))
         {
         //検索条件指定なし
         return "%";
         }
         else if (userId.indexOf('*') > -1)
         {
         //検索条件に*有
         return userId.replace('*', '%');
         }
         else
         {
         //検索条件に*無
         return userId;
         }
         */
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
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);
            AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
            setList(userHandler, authHandler, next_index, next_end);
        }
        finally
        {
            //コネクションを開放
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
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);
            AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
            setList(userHandler, authHandler, next_index, next_end);
        }
        finally
        {
            //コネクションを開放
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
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);
            AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
            setList(userHandler, authHandler, next_index, next_end);
        }
        finally
        {
            //コネクションを開放
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
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);
            AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
            setList(userHandler, authHandler, 0, page);
        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * リストが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void lst_UserList_Click(ActionEvent e)
            throws Exception
    {
        //現在の行をセット
        lst_UserList.setCurrentRow(lst_UserList.getActiveRow());
        //呼び出し元の画面へ渡すパラメータ作成
        ForwardParameters param = new ForwardParameters();
        param.setParameter(USERID_KEY, lst_UserList.getValue(2));

        //終了処理
        btn_Close_Click(null);

        //呼び出し元の画面へ遷移します
        parentRedirect(param);
    }

}
//end of class

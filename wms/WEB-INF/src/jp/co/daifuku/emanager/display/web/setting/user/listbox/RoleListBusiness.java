//$Id: RoleListBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.user.listbox;

import java.sql.Connection;
import java.util.MissingResourceException;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.entity.Role;
import jp.co.daifuku.emanager.database.handler.AuthenticationHandler;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.RoleHandler;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.EmProperties;

/** <jp>
 * ロール一覧の画面クラスです。
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
public class RoleListBusiness
        extends RoleList
        implements EmConstants
{
    // Class fields --------------------------------------------------
    /** 
     * ロールIDの受け渡しに使用するキーです
     */
    public static final String ROLEID_KEY = "ROLEID_KEY";

    /** 
     * ロール名の受け渡しに使用するキーです
     */
    public static final String ROLENAME_KEY = "ROLENAME_KEY";

    /** 
     * 認証ミス猶予回数の受け渡しに使用するキーです
     */
    public static final String FAILEDLOGINATTEMPTS_KEY = "FAILEDLOGINATTEMPTS_KEY";

    /**
     * 表示対象外のロールを区別するフラグ
     */
    public static final String TARGET_NOTLIKE = "TARGET_NOTLIKE";


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
        lbl_ListName.setText(DispResources.getText("TLE-T0012"));

        Connection conn = null;
        try
        {
            //Pager初期化
            setPagerValue(0, 0, 0);

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            //呼び出し元画面でセットされたパラメータの取得
            String roleid = this.request.getParameter(ROLEID_KEY);

            //ViewStateに保存
            this.getViewState().setString("RoleId", roleid);

            //検索用ロールIDを作成
            String searchID = roleid;
            if (EManagerUtil.isLikeSearch(roleid))
            {
                searchID = EManagerUtil.getLikeSearchString(roleid);
            }

            RoleHandler roleHandler = EmHandlerFactory.getRoleHandler(conn);
            //get Target not
            String target_Not = this.request.getParameter(TARGET_NOTLIKE);
            int total = 0;

            // if target is null, normal role list
            if (target_Not != null)
            {
                this.getViewState().setString(TARGET_NOTLIKE, target_Not);
                int notTarget = Integer.valueOf(target_Not).intValue();
                if (EManagerUtil.isLikeSearch(roleid))
                {
                    total = roleHandler.finCountLikeAndNotTarget(searchID, notTarget);
                }
                else
                {
                    total = roleHandler.findCountGreaterThanAndNotTarget(searchID, notTarget);
                }
            }
            else
            {
                if (EManagerUtil.isLikeSearch(roleid))
                {
                    total = roleHandler.finCountLike(searchID);
                }
                else
                {
                    total = roleHandler.findCountGreaterThan(searchID);
                }
            }

            //認証システムハンドラーを取得
            AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);

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
                setList(roleHandler, authHandler, 0, end);
            }
            else
            {
                //Pagerへの値セット
                pager.setMax(0); //最大件数
                pager.setPage(0); //1Page表示件数
                pager.setIndex(0); //開始位置				
                //ヘッダーを隠します
                lst_RoleList.setVisible(false);

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
     * @param roleHandler 
     * @param authHandler 
     * @param next_index int
     * @param next_end int 
     * @throws Exception 
     */
    private void setList(RoleHandler roleHandler, AuthenticationHandler authHandler, int next_index, int next_end)
            throws Exception
    {
        next_index++;

        //表を削除
        lst_RoleList.clearRow();

        //ViewStateからロールID取得
        String roleid = this.getViewState().getString("RoleId");

        // 検索用文字列を取得
        String searchId = roleid;
        if (EManagerUtil.isLikeSearch(roleid))
        {
            searchId = EManagerUtil.getLikeSearchString(roleid);
        }

        //DBより取得
        AuthenticationSystem authSystem = authHandler.findAuthenticationSystem();

        String targetNot = this.getViewState().getString(TARGET_NOTLIKE);
        Role[] role_Array = null;

        if (targetNot != null)
        {
            int notTarget = Integer.valueOf(targetNot).intValue();
            if (EManagerUtil.isLikeSearch(roleid))
            {
                role_Array = roleHandler.findRoleLikeAndNotTarget(searchId, next_index, next_end, notTarget);
            }
            else
            {
                role_Array = roleHandler.findRoleGreaterThaAndNotTarget(searchId, next_index, next_end, notTarget);
            }
        }
        else
        {
            if (EManagerUtil.isLikeSearch(roleid))
            {
                role_Array = roleHandler.findRoleLike(searchId, next_index, next_end);
            }
            else
            {
                role_Array = roleHandler.findRoleGreaterThan(searchId, next_index, next_end);
            }
        }

        //非表示列の設定
        boolean dispFailedLoginAttemptsColumn = authSystem.getFaieldLoginUserLockFlag();
        this.lst_RoleList.setColumnHidden(5, !dispFailedLoginAttemptsColumn);

        //ロールを取得できたか確認
        if (role_Array == null)
        {
            setPagerValue(0, 0, 0);
            this.lst_RoleList.setVisible(false);
            return;
        }

        //１件以上表示するデータがあるか
        boolean dispList = false;

        //表にセット
        for (int i = 0; i < role_Array.length; i++)
        {
            if (role_Array[i] == null)
            {
                continue;
            }

            //行追加
            this.lst_RoleList.addRow();

            //表にデータをセット
            this.lst_RoleList.setCurrentRow(this.lst_RoleList.getMaxRows() - 1);
            //No.
            this.lst_RoleList.setValue(1, Integer.toString(next_index++));
            //ロールID
            this.lst_RoleList.setValue(2, role_Array[i].getRoleID());

            //ロール名
            this.lst_RoleList.setValue(3, role_Array[i].getRoleName());

            // Role Target			
            int target = role_Array[i].getTarget();
            String targetTemp = "";
            switch (target)
            {
                case EmConstants.ROLE_TARGET_USER:
                    targetTemp = this.getDispText("RDB-T0034", "RDB-T0034");
                    break;
                case EmConstants.ROLE_TARGET_TERMINAL:
                    targetTemp = this.getDispText("RDB-T0035", "RDB-T0035");
                    break;
                default:
                    targetTemp = this.getDispText("RDB-T0033", "RDB-T0033");
                    break;

            }
            this.lst_RoleList.setValue(4, targetTemp);


            //認証ミス猶予回数
            if (dispFailedLoginAttemptsColumn)
            {
                if (role_Array[i].getFailLoginAttem() == FAILED_ATTEMPTS_STATUS_NOLIMITED)
                {
                    //無制限
                    this.lst_RoleList.setValue(5, getDispText("LBL-T0126", "LBL-T0126"));
                }
                else if (role_Array[i].getFailLoginAttem() == FAILED_ATTEMPTS_STATUS_EXTEND)
                {
                    //システム設定を使用
                    int systemFailLoginAttem = authSystem.getFailLoginAttem();
                    if (systemFailLoginAttem == FAILED_ATTEMPTS_STATUS_NOLIMITED)
                    {
                        //無制限
                        this.lst_RoleList.setValue(5, getDispText("LBL-T0126", "LBL-T0126"));
                    }
                    else
                    {
                        //制限有
                        this.lst_RoleList.setValue(5, Integer.toString(systemFailLoginAttem));
                    }
                }
                else
                {
                    //制限有
                    this.lst_RoleList.setValue(5, Integer.toString(role_Array[i].getFailLoginAttem()));
                }
            }

            //１件以上表示する列がある。
            dispList = true;
        }

        if (!dispList)
        {
            //表示する列が１件もない場合
            setPagerValue(0, 0, 0);
            this.lst_RoleList.clearRow();
            this.lst_RoleList.setVisible(false);
            return;
        }
    }

    /** 
     * リストセルに値をセットします
     * @param next_index int
     * @param next_end int 
     * @throws Exception 
     */
    private void setList(int next_index, int next_end)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            RoleHandler roleHandler = EmHandlerFactory.getRoleHandler(conn);
            AuthenticationHandler authHandler = EmHandlerFactory.getAuthenticationSystemHandler(conn);
            setList(roleHandler, authHandler, next_index, next_end);
        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
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
        setList(next_index, next_end);

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
        setList(next_index, next_end);

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
        setList(next_index, next_end);

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
        setList(0, page);

    }

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
     * リストが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void lst_RoleList_Click(ActionEvent e)
            throws Exception
    {
        //現在の行をセット
        lst_RoleList.setCurrentRow(lst_RoleList.getActiveRow());

        //呼び出し元の画面へ渡すパラメータ作成
        ForwardParameters param = new ForwardParameters();
        param.setParameter(ROLEID_KEY, lst_RoleList.getValue(2));
        param.setParameter(ROLENAME_KEY, lst_RoleList.getValue(3));
        param.setParameter(FAILEDLOGINATTEMPTS_KEY, lst_RoleList.getValue(4));

        //終了処理
        btn_Close_Click(null);

        //呼び出し元の画面へ遷移します
        parentRedirect(param);
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void lbl_ListName_Server(ActionEvent e)
            throws Exception
    {
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void lst_RoleList_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void lst_RoleList_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void lst_RoleList_InputComplete(ActionEvent e)
            throws Exception
    {
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void lst_RoleList_ColumClick(ActionEvent e)
            throws Exception
    {
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void lst_RoleList_Server(ActionEvent e)
            throws Exception
    {
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void lst_RoleList_Change(ActionEvent e)
            throws Exception
    {
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Close_Server(ActionEvent e)
            throws Exception
    {
    }


}
//end of class

// $Id: RoleMapBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.ui.control.PullDownItem;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.Function;
import jp.co.daifuku.emanager.database.entity.MainMenu;
import jp.co.daifuku.emanager.database.entity.Role;
import jp.co.daifuku.emanager.database.entity.SubmenuFunction;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.EmTableColumns;
import jp.co.daifuku.emanager.database.handler.FunctionHandler;
import jp.co.daifuku.emanager.database.handler.MainMenuHandler;
import jp.co.daifuku.emanager.database.handler.RoleFunctionMapHandler;
import jp.co.daifuku.emanager.database.handler.RoleHandler;
import jp.co.daifuku.emanager.database.handler.SubmenuFunctionHandler;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

/** <jp>
 * ロールマップ登録の画面クラスです。
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
public class RoleMapBusiness
        extends RoleMap
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

        btn_Commit.setBeforeConfirm("");
    }

    /**
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {

        Connection conn = null;
        try
        {
            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            //ロールハンドラー取得
            RoleHandler roleHandler = EmHandlerFactory.getRoleHandler(conn);

            //DBより取得
            jp.co.daifuku.emanager.database.entity.Role[] role_Array = roleHandler.findAll();

            //ロールが登録されていない場合
            if (role_Array == null)
            {
                //チェックボックスを隠す
                chk_All.setVisible(false);
                //リストを隠す
                lst_RoleMap.setVisible(false);

                //ロールを登録してください。
                message.setMsgResourceKey("6403033");

                btn_Cancel.setEnabled(false);
                btn_Commit.setEnabled(false);
                btn_View.setEnabled(false);
                pul_Role.setEnabled(false);

                return;
            }

            //プルダウンデータの設定
            for (int i = 0; i < role_Array.length; i++)
            {
                PullDownItem roleItem = new PullDownItem();
                roleItem.setValue(role_Array[i].getRoleID());
                roleItem.setText(role_Array[i].getRoleName());

                this.pul_Role.addItem(roleItem);
            }
            this.pul_Role.setSelectedIndex(0);

            //メインメニューハンドラー取得
            MainMenuHandler mainHandler = EmHandlerFactory.getMainMenuHandler(conn);
            //サブメニューファンクションハンドラー取得
            SubmenuFunctionHandler subfunHandler = EmHandlerFactory.getSubmenuFunctionHandler(conn);

            //リストデータをセット
            int total = setList(mainHandler, subfunHandler);

            //データがある場合
            if (total > 0)
            {
                //リストを表示
                lst_RoleMap.setVisible(true);
            }
            //データがない場合
            else
            {
                //チェックボックスを隠す
                chk_All.setVisible(false);
                //リストを隠す
                lst_RoleMap.setVisible(false);
                //メインメニュー・サブメニュー・サブメニューボタンを登録してください。
                message.setMsgResourceKey("6403036");

                btn_Cancel.setEnabled(false);
                btn_Commit.setEnabled(false);
                btn_View.setEnabled(false);
                pul_Role.setEnabled(false);
                return;
            }

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
     * @param mainHandler MainMenuHandler
     * @param subfunHandler SubmenuFunctionHandler
     * @return total int
     * 
     * @throws Exception 
     */
    private int setList(MainMenuHandler mainHandler, SubmenuFunctionHandler subfunHandler)
            throws Exception
    {
        //リストをクリア
        lst_RoleMap.clearRow();

        int total = 0;

        //DBよりMainMenuを取得
        MainMenu[] mainMenu_Array = mainHandler.findAll(1);
        //MainMenuを取得できたか確認
        if (mainMenu_Array == null)
        {
            return total;
        }

        for (int i = 0; i < mainMenu_Array.length; i++)
        {
            //DBよりSubMenuとFunctionを取得
            SubmenuFunction[] subfun_Array =
                    subfunHandler.findSubmenuFunctionByMenuId(mainMenu_Array[i].getMainMenuId(), 1, 0);
            //SubmenuFunctionを取得できたか確認
            if (subfun_Array == null)
            {
                continue;
            }

            for (int j = 0; j < subfun_Array.length; j++)
            {
                //Functionを取得
                Function function[] = subfun_Array[j].getFunctionArray();
                //Functionを取得できたか確認
                if (function == null)
                {
                    continue;
                }
                //合計件数に加算
                total += function.length;
                for (int k = 0; k < function.length; k++)
                {

                    //リストに行を追加
                    this.lst_RoleMap.addRow();
                    this.lst_RoleMap.setCurrentRow(this.lst_RoleMap.getMaxRows() - 1);

                    //ファンクションID
                    this.lst_RoleMap.setValue(0, function[k].getFunctionId());
                    //メインメニュー
                    this.lst_RoleMap.setValue(1, getDispText(mainMenu_Array[i].getMenuResourceKey(),
                            mainMenu_Array[i].getMenuResourceKey()));
                    //サブメニュー
                    this.lst_RoleMap.setValue(2, getDispText(subfun_Array[j].getSubMenuResourceKey(),
                            subfun_Array[j].getSubMenuResourceKey()));

                    //ボタン名
                    this.lst_RoleMap.setValue(4, getDispText(function[k].getButtonResourceKey(),
                            function[k].getButtonResourceKey()));
                }
            }
        }

        return total;
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
            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            //メインメニューハンドラー取得
            MainMenuHandler mainHandler = EmHandlerFactory.getMainMenuHandler(conn);
            //サブメニューファンクションハンドラー取得
            SubmenuFunctionHandler subfunHandler = EmHandlerFactory.getSubmenuFunctionHandler(conn);

            //リストを表示
            int total = setList(mainHandler, subfunHandler);;


            //データがある場合
            if (total > 0)
            {
                //リストを表示
                lst_RoleMap.setVisible(true);
            }
            //データがない場合
            else
            {
                //チェックボックスを隠す
                chk_All.setVisible(false);
                //リストを隠す
                lst_RoleMap.setVisible(false);
                //メインメニュー・サブメニュー・サブメニューボタンを登録してください。
                message.setMsgResourceKey("6403036");

                btn_Cancel.setEnabled(false);
                btn_Commit.setEnabled(false);
                btn_View.setEnabled(false);
                pul_Role.setEnabled(false);
                return;
            }

            //ロールIDを取得
            String roleid = this.pul_Role.getSelectedValue();
            if (roleid == null)
            {
                //ロールを登録してください。
                message.setMsgResourceKey("6403033");
                return;
            }

            //ロールの存在チェック
            if (EmHandlerFactory.getRoleHandler(conn).findByRoleId(roleid) == null)
            {
                //指定されたロールIDは登録されていません。
                message.setMsgResourceKey("6403013");
                return;
            }

            if (lst_RoleMap.getMaxRows() == 1)
            {
                //メインメニュー・サブメニュー・サブメニューボタンを登録してください。
                message.setMsgResourceKey("6403036");
                return;
            }


            chk_All.setEnabled(true);

            //チェックボックスを有効にする
            for (int i = 1; i < lst_RoleMap.getMaxRows(); i++)
            {
                lst_RoleMap.setCurrentRow(i);
                lst_RoleMap.setCellEnabled(3, true);
            }

            //リストセル内のチェックをはずす
            for (int k = 1; k < lst_RoleMap.getMaxRows(); k++)
            {
                lst_RoleMap.setCurrentRow(k);
                lst_RoleMap.setChecked(3, false);
            }

            //RoleMap表の機能IDの一覧取得
            RoleFunctionMapHandler rolefunHandler = EmHandlerFactory.getRoleFunctionMapHandler(conn);
            jp.co.daifuku.emanager.database.entity.Role[] rolefun_Array = rolefunHandler.findByRoleId(roleid);

            //登録済みの機能IDがある場合
            if (rolefun_Array != null && rolefun_Array.length != 0)
            {
                //RoleMap表を検索
                for (int i = 0; i < rolefun_Array.length; i++)
                {
                    //リストデータから値を取得
                    String rolemap_functionid = rolefun_Array[i].getFunctionId();

                    //リストセルを検索
                    for (int j = 1; j < lst_RoleMap.getMaxRows(); j++)
                    {
                        //リストセル上の機能IDを取得
                        lst_RoleMap.setCurrentRow(j);
                        String buf = lst_RoleMap.getValue(0);
                        String functionid_lst = CollectionUtils.getString(0, buf);

                        //リストセル内の機能IDとRoleMap表の機能IDが一致しているか判定
                        if (rolemap_functionid.equals(functionid_lst))
                        {
                            //リストセル内のチェックボックスにチェックを入れる
                            lst_RoleMap.setChecked(3, true);
                            break;
                        }
                    }
                }
            }

            int cnt_check = 1;
            //リストセル内のチェック状態を取得
            for (int m = 1; m < lst_RoleMap.getMaxRows(); m++)
            {
                lst_RoleMap.setCurrentRow(m);
                if (lst_RoleMap.getChecked(3) == true)
                {
                    cnt_check = cnt_check + 1;
                }
            }
            //リストセル内のチェックがすべてついているか判定
            if (cnt_check == lst_RoleMap.getMaxRows())
            {
                //全て選択チェックをボックスにチェックを入れる
                chk_All.setChecked(true);
            }
            else
            {
                //全て選択チェックをボックスにチェックをはずす
                chk_All.setChecked(false);
            }

            //選択されているロールをViewStateに保存
            this.getViewState().setString("Role", pul_Role.getSelectedItem().getText());

            if (chk_All.getEnabled())
            {
                btn_Commit.setBeforeConfirm("MSG-T0005");
            }

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
     * 設定ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Commit_Click(ActionEvent e)
            throws Exception
    {
        //フォーカスを移動
        setFocus(this.btn_Help);

        if (!chk_All.getEnabled())
        {
            //ロールを選択してください。
            message.setMsgResourceKey("6403034");
            return;
        }

        Connection conn = null;
        try
        {

            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);
            //ロールハンドラーを取得
            RoleFunctionMapHandler rolefunHandler = EmHandlerFactory.getRoleFunctionMapHandler(conn);
            //ファンクションハンドラーを取得
            FunctionHandler funHandler = EmHandlerFactory.getFunctionHandler(conn);

            //ロールIDを取得
            String roleid = this.pul_Role.getSelectedValue();
            //ロールの存在チェック
            if (EmHandlerFactory.getRoleHandler(conn).findByRoleId(roleid) == null)
            {
                //指定されたロールIDは登録されていません。
                message.setMsgResourceKey("6403013");
                return;
            }

            //一旦RoleIDに紐づくロールマップを全て削除
            rolefunHandler.deleteByRoleId(roleid);

            //リストセル内のチェック状態を取得
            for (int i = 1; i < this.lst_RoleMap.getMaxRows(); i++)
            {
                this.lst_RoleMap.setCurrentRow(i);

                String functionId = CollectionUtils.getString(0, this.lst_RoleMap.getValue(0));

                //対象のボタンが存在しないまたは非公開かチェック
                if (funHandler.findCountByFunctionId(functionId) == 0
                        || funHandler.findByFunctionId(functionId).getHiddenFlag())
                {
                    continue;
                }

                if (this.lst_RoleMap.getChecked(3) == true)
                {
                    //リストセル上の機能IDを取得
                    Role insertRoleFunctionMap = new Role();

                    //ロールID
                    insertRoleFunctionMap.setRoleID(roleid);
                    //ファンクションID
                    insertRoleFunctionMap.setFunctionId(functionId);
                    // 登録したユーザ情報を設定
                    DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();
                    if (userinfo != null)
                    {
                        UserInfoHandler userhandler = new UserInfoHandler(userinfo);

                        if (userhandler != null)
                        {
                            insertRoleFunctionMap.setUpdateUser(userhandler.getUserID());
                        }
                    }
                    insertRoleFunctionMap.setUpdateTerminal(this.httpRequest.getRemoteAddr());
                    insertRoleFunctionMap.setUpdateKind(1);
                    insertRoleFunctionMap.setUpdateProcess(this.getClass().getName());

                    //DBに登録
                    rolefunHandler.createRoleFunctionMap(insertRoleFunctionMap);

                }
                //ユーザ情報の作成
                DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
                //項目リストの生成
                List list = new ArrayList();
                list.add(pul_Role.getSelectedItem().getText());
                list.add(lst_RoleMap.getValue(1));
                list.add(lst_RoleMap.getValue(2));
                list.add(lst_RoleMap.getValue(4));
                list.add(lst_RoleMap.getChecked(3) ? String.valueOf(EmConstants.DB_FLAG_TRUE)
                                                  : String.valueOf(EmConstants.DB_FLAG_FALSE));
                //オペレーションログ出力
                P11LogWriter logWriter = new P11LogWriter(conn);
                logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_SETTING, list);
            }

            //設定
            conn.commit();
            //6401008	更新しました。
            message.setMsgResourceKey("6401008");

            if (chk_All.getEnabled())
            {
                btn_Commit.setBeforeConfirm("MSG-T0005");
            }

            //メンテナンスログに出力するメッセージ
            //ロールマップを設定しました。({0})
            String Primaly = EmTableColumns.ROLEFUNCTIONMAP_ROLEID + "=" + roleid;
            String mainteLog_msg = "6400022" + MSG_DELIM + Primaly;
            //メンテナンスログ出力
            EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_ROLE, mainteLog_msg, "");

        }
        catch (SQLException ex)
        {
            EmConnectionHandler.rollback(conn);

            int errorCode = ex.getErrorCode();

            if (errorCode == EmConstants.SQLERRORCODE_ORA0001)
            {
                // <jp>一意制約違反</jp>
                message.setMsgResourceKey("6403068");
            }
            else
            {
                // <jp>その他エラー</jp>
                message.setMsgResourceKey("6403069");
            }
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
        //ViewStateより選択されているロールを取得
        String role = this.getViewState().getString("Role");
        if (role == null)
        {
            return;
        }
        //ロールが変更されているか判定
        else if (role.equals(pul_Role.getSelectedItem().getText()))
        {
            //表示処理
            btn_View_Click(e);
        }
    }

    /** 
     * 全てチェックボックスが変更されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void chk_All_Change(ActionEvent e)
            throws Exception
    {
        for (int i = 1; i < lst_RoleMap.getMaxRows(); i++)
        {
            lst_RoleMap.setCurrentRow(i);
            if (chk_All.getChecked())
            {
                lst_RoleMap.setChecked(3, true);
            }
            else
            {
                lst_RoleMap.setChecked(3, false);
            }
        }

        if (chk_All.getEnabled())
        {
            btn_Commit.setBeforeConfirm("MSG-T0005");
        }

    }
}
//end of class

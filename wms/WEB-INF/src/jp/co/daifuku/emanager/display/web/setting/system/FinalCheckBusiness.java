// $Id: FinalCheckBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.system;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.StringTokenizer;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.Function;
import jp.co.daifuku.emanager.database.entity.LogInfo;
import jp.co.daifuku.emanager.database.entity.MainMenu;
import jp.co.daifuku.emanager.database.entity.Role;
import jp.co.daifuku.emanager.database.entity.SubMenu;
import jp.co.daifuku.emanager.database.entity.Terminal;
import jp.co.daifuku.emanager.database.entity.User;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.EmTableColumns;
import jp.co.daifuku.emanager.database.handler.FunctionHandler;
import jp.co.daifuku.emanager.database.handler.MainMenuHandler;
import jp.co.daifuku.emanager.database.handler.PassWordHistoryHandler;
import jp.co.daifuku.emanager.database.handler.RoleFunctionMapHandler;
import jp.co.daifuku.emanager.database.handler.RoleHandler;
import jp.co.daifuku.emanager.database.handler.SubMenuHandler;
import jp.co.daifuku.emanager.database.handler.TerminalHandler;
import jp.co.daifuku.emanager.database.handler.UserHandler;
import jp.co.daifuku.emanager.util.EmDBLog;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;


/** <jp>
 * 整合性確認の画面クラスです。
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
public class FinalCheckBusiness
        extends FinalCheck
        implements EmConstants
{
    // Class fields --------------------------------------------------
    /**
     * エラー内容を保持するリスト
     */
    private List wErrorList = null;

    //Table Name
    private final String T_MAINMENU = "COM_MAINMENU";

    private final String T_SUBMENU = "COM_SUBMENU";

    private final String T_FUNCTION = "COM_FUNCTION";

    private final String T_ROLE = "COM_ROLE";

    private final String T_ROLEFUNCTIONMAP = "COM_ROLEFUNCTIONMAP";

    private final String T_LOGINUSER = "COM_LOGINUSER";

    private final String T_USERATTRIBUTE = "COM_USERATTRIBUTE";

    private final String T_TERMINAL = "COM_TERMINAL";

    private final String T_PASSWORDHISTORY = "COM_PASSWORDHISTORY";

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
    }

    /** 
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @exception Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /** 
     * リストセルに値をセットします
     */
    private void setList()
    {
        for (int i = 0; i < wErrorList.size(); i++)
        {
            //最終行を取得
            int count = lst_FinalCheck.getMaxRows();
            //行追加
            lst_FinalCheck.setCurrentRow(count);
            lst_FinalCheck.addRow();

            //No.
            lst_FinalCheck.setValue(1, Integer.toString(count));
            ErrorItem item = (ErrorItem)wErrorList.get(i);
            //Level
            lst_FinalCheck.setValue(2, item.getLevel());
            //TableName
            lst_FinalCheck.setValue(3, item.getTableName());
            //Message
            lst_FinalCheck.setValue(4, item.getMessage());
        }

    }

    /*
     * メインメニューチェック
     */
    private boolean checkMainMenuTable(MainMenu[] mainMenu)
            throws Exception
    {
        boolean result = true;

        //MAINMENU表のメニューリソースキーがリソースファイルに存在するか確認
        for (int i = 0; i < mainMenu.length; i++)
        {
            result &=
                    isDefinedResourceKey(EmTableColumns.MAINMENU_MENURESOURCEKEY, mainMenu[i].getMenuResourceKey(),
                            EmTableColumns.MAINMENU_MAINMENUID, mainMenu[i].getMainMenuId(), T_MAINMENU);
        }

        //MAINMENU表のメニュー表示順の重複確認
        //既に重複確認済み一覧
        ArrayList repetition_List = new ArrayList();
        for (int i = 0; i < mainMenu.length - 1; i++)
        {
            if (mainMenu[i].getMenuDisplayOrder() == -1)
            {
                //-1の場合はチェックしない
                continue;
            }
            if (repetition_List.contains(Integer.toString(mainMenu[i].getMenuDisplayOrder())))
            {
                // 既に重複確認されている。
                continue;
            }

            boolean repetition = false;
            for (int j = i + 1; j < mainMenu.length; j++)
            {
                if (mainMenu[i].getMenuDisplayOrder() == mainMenu[j].getMenuDisplayOrder())
                {
                    if (!repetition)
                    {
                        //エラー内容をセット
                        ErrorItem errorItem = new ErrorItem();
                        errorItem.setLevel(DispResources.getText("LBL-T0102"));
                        errorItem.setTableName(T_MAINMENU);
                        //{0}が重複してます。
                        String text = "ERR-T0002" + MSG_DELIM + EmTableColumns.MAINMENU_MENUDISPORDER;
                        errorItem.setMessage(DispResources.getText(text));
                        //リストに追加
                        wErrorList.add(errorItem);

                        //重複確認済み一覧に登録
                        repetition_List.add(Integer.toString(mainMenu[i].getMenuDisplayOrder()));

                        repetition = true;
                    }
                    //エラー内容をセット
                    ErrorItem errorItem = new ErrorItem();
                    errorItem.setLevel(DispResources.getText("LBL-T0102"));
                    errorItem.setTableName(T_MAINMENU);
                    //{0}が重複してます。
                    String text = "ERR-T0002" + MSG_DELIM + EmTableColumns.MAINMENU_MENUDISPORDER;
                    errorItem.setMessage(DispResources.getText(text));
                    //リストに追加
                    wErrorList.add(errorItem);

                    //重複確認済み一覧に追加
                    repetition_List.add(Integer.toString(mainMenu[j].getMenuDisplayOrder()));

                    result = false;
                }
            }
        }

        //MAINMENU表のMENUIDが数値のみで構成されていることを確認
        for (int i = 0; i < mainMenu.length; i++)
        {
            result &=
                    isNumber(EmTableColumns.MAINMENU_MAINMENUID, mainMenu[i].getMainMenuId(),
                            EmTableColumns.MAINMENU_MAINMENUID, mainMenu[i].getMainMenuId(), T_MAINMENU);
        }

        return result;
    }

    /*
     * サブメニューチェック
     */
    private boolean checkSubMenuTable(SubMenu[] subMenu, MainMenu[] mainMenu)
            throws Exception
    {
        boolean result = true;

        if (mainMenu == null)
        {
            mainMenu = new MainMenu[0];
        }


        //SUBMENU表のMENUIDがMAINMENU表に存在するかを確認
        for (int i = 0; i < subMenu.length; i++)
        {
            boolean isDefinedMainMenuID = false;

            if (subMenu[i].getMainMenuId() != null)
            {
                for (int j = 0; j < mainMenu.length; j++)
                {
                    if (subMenu[i].getMainMenuId().equals(mainMenu[j].getMainMenuId()))
                    {
                        isDefinedMainMenuID = true;
                    }
                }
            }

            if (!isDefinedMainMenuID)
            {
                //エラー内容をセット
                ErrorItem errorItem = new ErrorItem();
                errorItem.setLevel(DispResources.getText("LBL-T0102"));
                errorItem.setTableName(T_SUBMENU);
                String status1 = EmTableColumns.SUBMENU_MAINMENUID + ":" + subMenu[i].getMainMenuId();
                String status2 = EmTableColumns.SUBMENU_SUBMENUID + "=" + subMenu[i].getSubMenuId();
                //{0}は{1}に存在していません。({2})
                String text = "ERR-T0003" + MSG_DELIM + status1 + MSG_DELIM + T_MAINMENU + MSG_DELIM + status2;
                errorItem.setMessage(DispResources.getText(text));
                //リストに追加
                wErrorList.add(errorItem);
                result = false;
            }
        }

        //SUBMENU表の機能リソースキーがリソースファイルに存在するか確認
        for (int i = 0; i < subMenu.length; i++)
        {
            result &=
                    isDefinedResourceKey(EmTableColumns.SUBMENU_SUBMENURESOURCEKEY, subMenu[i].getSubMenuResourceKey(),
                            EmTableColumns.SUBMENU_SUBMENUID, subMenu[i].getSubMenuId(), T_SUBMENU);
        }

        //SUBMENU表の機能表示順の重複確認
        //既に重複確認済み一覧
        Hashtable repetition_List = new Hashtable();
        for (int i = 0; i < subMenu.length - 1; i++)
        {
            if (subMenu[i].getSubMenuDisplayOrder() == -1)
            {
                // -1の場合はチェックしない
                continue;
            }
            if (repetition_List.get(subMenu[i].getMainMenuId()) != null
                    && ((ArrayList)repetition_List.get(subMenu[i].getMainMenuId())).contains(Integer.toString(subMenu[i].getSubMenuDisplayOrder())))
            {
                // 既に重複確認されている。
                continue;
            }

            boolean repetition = false;
            for (int j = i + 1; j < subMenu.length; j++)
            {
                if (subMenu[i].getMainMenuId().equals(subMenu[j].getMainMenuId())
                        && subMenu[i].getSubMenuDisplayOrder() == subMenu[j].getSubMenuDisplayOrder())
                {
                    if (!repetition)
                    {
                        //エラー内容をセット
                        ErrorItem errorItem = new ErrorItem();
                        errorItem.setLevel(DispResources.getText("LBL-T0102"));
                        errorItem.setTableName(T_SUBMENU);
                        String status = EmTableColumns.SUBMENU_MAINMENUID + "=" + subMenu[i].getMainMenuId();
                        //{0}が重複してます。({1})
                        String text =
                                "ERR-T0005" + MSG_DELIM + EmTableColumns.SUBMENU_SUBMENUDISPORDER + MSG_DELIM + status;
                        errorItem.setMessage(DispResources.getText(text));
                        //リストに追加
                        wErrorList.add(errorItem);

                        //重複確認済み一覧に登録
                        ArrayList newRepetitionDisplayOrder_List = new ArrayList();
                        newRepetitionDisplayOrder_List.add(Integer.toString(subMenu[i].getSubMenuDisplayOrder()));
                        repetition_List.put(subMenu[i].getMainMenuId(), newRepetitionDisplayOrder_List);

                        repetition = true;
                    }
                    //エラー内容をセット
                    ErrorItem errorItem = new ErrorItem();
                    errorItem.setLevel(DispResources.getText("LBL-T0102"));
                    errorItem.setTableName(T_SUBMENU);
                    String status = EmTableColumns.SUBMENU_MAINMENUID + "=" + subMenu[i].getMainMenuId();
                    //{0}が重複してます。({1})
                    String text =
                            "ERR-T0005" + MSG_DELIM + EmTableColumns.SUBMENU_SUBMENUDISPORDER + MSG_DELIM + status;
                    errorItem.setMessage(DispResources.getText(text));
                    //リストに追加
                    wErrorList.add(errorItem);

                    //重複確認済み一覧に追加
                    ((ArrayList)repetition_List.get(subMenu[i].getMainMenuId())).add(Integer.toString(subMenu[j].getSubMenuDisplayOrder()));

                    result = false;
                }
            }
        }

        //SUBMENU表のSUBMENUIDが数値のみで構成されていることを確認
        for (int i = 0; i < subMenu.length; i++)
        {
            result &=
                    isNumber(EmTableColumns.SUBMENU_SUBMENUID, subMenu[i].getSubMenuId(),
                            EmTableColumns.SUBMENU_SUBMENUID, subMenu[i].getSubMenuId(), T_SUBMENU);
        }

        return result;
    }

    /*
     * 機能チェック
     */
    private boolean checkFunctionTable(Function[] function, SubMenu[] subMenu)
            throws Exception
    {
        boolean result = true;

        if (subMenu == null)
        {
            subMenu = new SubMenu[0];
        }


        //FUNCTION表のSUBMENUIDがSUBMENU表に存在するかを確認
        for (int i = 0; i < function.length; i++)
        {
            boolean isDefinedSubMenuid = false;

            if (function[i].getSubMenuId() != null)
            {
                for (int j = 0; j < subMenu.length; j++)
                {
                    if (function[i].getSubMenuId().equals(subMenu[j].getSubMenuId()))
                    {
                        isDefinedSubMenuid = true;
                    }
                }
            }

            if (!isDefinedSubMenuid)
            {
                //エラー内容をセット
                ErrorItem errorItem = new ErrorItem();
                errorItem.setLevel(DispResources.getText("LBL-T0102"));
                errorItem.setTableName(T_FUNCTION);
                String status1 = EmTableColumns.FUNCTION_SUBMENUID + ":" + function[i].getSubMenuId();
                String status2 = EmTableColumns.FUNCTION_FUNCTIONID + "=" + function[i].getFunctionId();
                //{0}は{1}に存在していません。({2})
                String text = "ERR-T0003" + MSG_DELIM + status1 + MSG_DELIM + T_SUBMENU + MSG_DELIM + status2;
                errorItem.setMessage(DispResources.getText(text));
                //リストに追加
                wErrorList.add(errorItem);

                result = false;
            }
        }

        //FUNCTION表の機能表示順の重複確認
        //既に重複確認済み一覧
        Hashtable repetition_List = new Hashtable();
        for (int i = 0; i < function.length - 1; i++)
        {
            if (function[i].getButtonDisplayOrder() == -1)
            {
                continue;
            }
            if (repetition_List.get(function[i].getSubMenuId()) != null
                    && ((ArrayList)repetition_List.get(function[i].getSubMenuId())).contains(Integer.toString(function[i].getButtonDisplayOrder())))
            {
                // 既に重複確認されている。
                continue;
            }

            boolean repetition = false;
            for (int j = i + 1; j < function.length; j++)
            {
                if (function[i].getSubMenuId().equals(function[j].getSubMenuId())
                        && function[i].getButtonDisplayOrder() == function[j].getButtonDisplayOrder())
                {
                    if (!repetition)
                    {
                        //エラー内容をセット
                        ErrorItem errorItem = new ErrorItem();
                        errorItem.setLevel(DispResources.getText("LBL-T0102"));
                        errorItem.setTableName(T_FUNCTION);
                        String status = EmTableColumns.FUNCTION_SUBMENUID + "=" + function[i].getSubMenuId();
                        //{0}が重複してます。({1})
                        String text =
                                "ERR-T0005" + MSG_DELIM + EmTableColumns.FUNCTION_BUTTONDISPORDER + MSG_DELIM + status;
                        errorItem.setMessage(DispResources.getText(text));
                        //リストに追加
                        wErrorList.add(errorItem);

                        //重複確認済み一覧に登録
                        ArrayList newRepetitionDisplayOrder_List = new ArrayList();
                        newRepetitionDisplayOrder_List.add(Integer.toString(function[i].getButtonDisplayOrder()));
                        repetition_List.put(function[i].getSubMenuId(), newRepetitionDisplayOrder_List);

                        repetition = true;
                    }
                    //エラー内容をセット
                    ErrorItem errorItem = new ErrorItem();
                    errorItem.setLevel(DispResources.getText("LBL-T0102"));
                    errorItem.setTableName(T_FUNCTION);
                    String status = EmTableColumns.FUNCTION_SUBMENUID + "=" + function[j].getSubMenuId();
                    //{0}が重複してます。({1})
                    String text =
                            "ERR-T0005" + MSG_DELIM + EmTableColumns.FUNCTION_BUTTONDISPORDER + MSG_DELIM + status;
                    errorItem.setMessage(DispResources.getText(text));
                    //リストに追加
                    wErrorList.add(errorItem);

                    //重複確認済み一覧に追加
                    ((ArrayList)repetition_List.get(function[j].getSubMenuId())).add(Integer.toString(function[j].getButtonDisplayOrder()));

                    result = false;
                }
            }
        }

        //FUNCTION表のBUTTONRESOURCEKEYがリソースファイルに存在するか確認
        for (int i = 0; i < function.length; i++)
        {
            result &=
                    isDefinedResourceKey(EmTableColumns.FUNCTION_BUTTONRESOURCEKEY, function[i].getButtonResourceKey(),
                            EmTableColumns.FUNCTION_FUNCTIONID, function[i].getFunctionId(), T_FUNCTION);
        }

        //FUNCTION表のPAGENAMERESOURCEKEYがリソースファイルに存在するか確認
        for (int i = 0; i < function.length; i++)
        {
            result &=
                    isDefinedResourceKey(EmTableColumns.FUNCTION_PAGENAMERESOURCEKEY, function[i].getPageResourceKey(),
                            EmTableColumns.FUNCTION_FUNCTIONID, function[i].getFunctionId(), T_FUNCTION);
        }

        //FUNCTION表のFUNCIONIDが数値のみで構成されていることを確認
        for (int i = 0; i < function.length; i++)
        {
            result &=
                    isNumber(EmTableColumns.FUNCTION_FUNCTIONID, function[i].getFunctionId(),
                            EmTableColumns.FUNCTION_FUNCTIONID, function[i].getFunctionId(), T_FUNCTION);
        }

        return result;
    }

    /*
     * ロールチェック
     */
    private boolean checkRoleTable(Role[] role)
    {
        // adminロールが存在するかのフラグ
        boolean isOK = false;

        for (int i = 0; i < role.length; i++)
        {
            // RoleIDがadminかどうか
            if ("admin".equals(role[i].getRoleID()))
            {
                // RoleIDがadminならフラグをtrueにしてループから出る
                isOK = true;
                break;
            }
        }

        // ロールの配列内にadminがいたかどうか
        if (!isOK)
        {
            // adminがいなかったときの処理
            // リストに登録

            //エラー内容をセット
            ErrorItem errorItem = new ErrorItem();
            errorItem.setLevel(DispResources.getText("LBL-T0103"));
            errorItem.setTableName(T_ROLE);
            String status1 = "admin";
            //{0}が登録されていません。
            String text = "ERR-T0006" + Message.MSG_DELIM + status1;
            errorItem.setMessage(DispResources.getText(text));
            //リストに追加
            wErrorList.add(errorItem);
        }

        return isOK;
    }

    /*
     * ロール機能チェック
     */
    private boolean checkRoleFunctionMapTable(Role[] roleFuncArr, Function[] functionArr, Role[] roleArr)
    {
        boolean isOK = true;

        // 引数に渡された配列がnullなら長さが０の配列に置き換える
        if (roleArr == null)
        {
            roleArr = new Role[0];
        }

        // 引数に渡された配列がnullなら長さが０の配列に置き換える
        if (functionArr == null)
        {
            functionArr = new Function[0];
        }

        // ロールが存在しなかった場合に格納します
        ArrayList funcRoleIdNotExistList = new ArrayList();

        // 機能画面が存在しなかった場合に格納します
        ArrayList funcRoleFuncIdNotExist = new ArrayList();

        // ファンクションロールの個数繰り返す
        for (int i = 0; i < roleFuncArr.length; i++)
        {
            // ロールが存在したことを表すフラグ
            boolean funcRoleIdExist = false;

            for (int j = 0; j < roleArr.length; j++)
            {
                // ロールの存在チェック
                if (roleFuncArr[i].getRoleID() != null && roleFuncArr[i].getRoleID().equals(roleArr[j].getRoleID()))
                {
                    funcRoleIdExist = true;
                    break;
                }
            }

            // ロールが存在したかをチェック
            if (!funcRoleIdExist)
            {
                funcRoleIdNotExistList.add(roleFuncArr[i]);
            }

            // 機能画面が存在したことを表すフラグ
            boolean funcRoleFuncIdExist = false;

            for (int j = 0; j < functionArr.length; j++)
            {
                // 機能画面の存在チェック
                if (roleFuncArr[i].getFunctionId() != null
                        && roleFuncArr[i].getFunctionId().equals(functionArr[j].getFunctionId()))
                {
                    funcRoleFuncIdExist = true;
                    break;
                }
            }

            // 機能画面が存在していなければ一覧に格納する
            if (!funcRoleFuncIdExist)
            {
                funcRoleFuncIdNotExist.add(roleFuncArr[i]);
            }
        }

        // ロールが存在しなかった項目が見つかったかをチェック
        if (!funcRoleIdNotExistList.isEmpty())
        {
            Iterator itr = funcRoleIdNotExistList.iterator();

            // このメソッド全体を通しての処理結果フラグ
            isOK = false;

            while (itr.hasNext())
            {
                // Roleを取得
                Role role = (Role)itr.next();

                //エラー内容をセット
                ErrorItem errorItem = new ErrorItem();
                errorItem.setLevel(DispResources.getText("LBL-T0102"));
                errorItem.setTableName(T_ROLEFUNCTIONMAP);
                String status1 = role.getRoleID();
                String status2 = T_ROLE;
                String status3 =
                        EmTableColumns.ROLEFUNCTIONMAP_ROLEID + "=" + role.getRoleID() + ", "
                                + EmTableColumns.ROLEFUNCTIONMAP_FUNCTIONID + "=" + role.getFunctionId();
                //{0}は{1}に存在していません。({2})
                String text =
                        "ERR-T0003" + Message.MSG_DELIM + status1 + Message.MSG_DELIM + status2 + Message.MSG_DELIM
                                + status3;
                errorItem.setMessage(DispResources.getText(text));
                //リストに追加
                wErrorList.add(errorItem);
            }
        }

        // 機能画面が存在しなかった項目が見つかったかをチェック
        if (!funcRoleFuncIdNotExist.isEmpty())
        {
            Iterator itr = funcRoleFuncIdNotExist.iterator();

            // このメソッド全体を通しての処理結果フラグ
            isOK = false;

            while (itr.hasNext())
            {
                // Roleを取得
                Role role = (Role)itr.next();

                //エラー内容をセット
                ErrorItem errorItem = new ErrorItem();
                errorItem.setLevel(DispResources.getText("LBL-T0102"));
                errorItem.setTableName(T_ROLEFUNCTIONMAP);
                String status1 = role.getFunctionId();
                String status2 = T_FUNCTION;
                String status3 =
                        EmTableColumns.ROLEFUNCTIONMAP_ROLEID + "=" + role.getRoleID() + ", "
                                + EmTableColumns.ROLEFUNCTIONMAP_FUNCTIONID + "=" + role.getFunctionId();
                //{0}は{1}に存在していません。({2})
                String text =
                        "ERR-T0003" + Message.MSG_DELIM + status1 + Message.MSG_DELIM + status2 + Message.MSG_DELIM
                                + status3;
                errorItem.setMessage(DispResources.getText(text));
                //リストに追加
                wErrorList.add(errorItem);
            }
        }

        return isOK;
    }

    /*
     * ユーザチェック
     */
    //	private boolean checkUserTable(User[] userArr, UserAttributes[] userAttributesArr, Role[] roleArr)
    private boolean checkUserTable(User[] userArr, Role[] roleArr)
    {
        // このメソッド全体を通しての処理結果を表すフラグ
        boolean isOK = true;

        /*	// COM_USERATTRIBUTEから項目が取得できなかった場合は長さが０の配列に置き換える
         if(userAttributesArr == null)
         {
         userAttributesArr = new UserAttributes[0];
         }*/
        // COM_ROLEから項目が取得できなかった場合は長さが０の配列に置き換える
        if (roleArr == null)
        {
            roleArr = new Role[0];
        }

        ArrayList roleNotExists = new ArrayList();
        ArrayList userIdNotExist = new ArrayList();

        boolean anonymousUserExists = false;
        boolean userMaintenanceExists = false;
        for (int i = 0; i < userArr.length; i++)
        {
            // 設定しているロールが存在しているかを表すフラグ
            boolean roleExists = false;

            for (int j = 0; j < roleArr.length; j++)
            {
                if (userArr[i].getRoleID().equals(roleArr[j].getRoleID()))
                {
                    roleExists = true;
                    break;
                }
            }

            if (!roleExists)
            {
                roleNotExists.add(userArr[i]);
            }

            // 設定しているユーザIDがCOM_USERATTRIBUTE表に存在するかを表すフラグ
            //			boolean userIdExist = false;	
            boolean userIdExist = true;
            /*for(int j = 0; j < userAttributesArr.length; j++)
             {
             if(userArr[i].getUserID().equals(userAttributesArr[j].getUserID()))
             {
             userIdExist = true;
             break;
             }
             }*/

            if (!userIdExist)
            {
                userIdNotExist.add(userArr[i]);
            }

            if (userArr[i].getUserID().equals(ANONYMOUS_USER))
            {
                anonymousUserExists = true;
            }
            if (userArr[i].getUserID().equals(USERMAINTENANCE_USER))
            {
                userMaintenanceExists = true;
            }
        }

        if (!anonymousUserExists)
        {
            isOK = false;
            // LoginUserのユーザIDにANONYMOUS_USERが存在しないメッセージ作成
            // リストに登録

            //エラー内容をセット
            ErrorItem errorItem = new ErrorItem();
            errorItem.setLevel(DispResources.getText("LBL-T0103"));
            errorItem.setTableName(T_LOGINUSER);
            String status1 = ANONYMOUS_USER;

            //{0}が登録されていません。
            String text = "ERR-T0006" + Message.MSG_DELIM + status1;
            errorItem.setMessage(DispResources.getText(text));
            //リストに追加
            wErrorList.add(errorItem);
        }
        if (!userMaintenanceExists)
        {
            isOK = false;
            // LoginUserのユーザIDにUSER_MNTが存在しないメッセージ作成
            // リストに登録

            //エラー内容をセット
            ErrorItem errorItem = new ErrorItem();
            errorItem.setLevel(DispResources.getText("LBL-T0103"));
            errorItem.setTableName(T_LOGINUSER);
            String status1 = USERMAINTENANCE_USER;

            //{0}が登録されていません。
            String text = "ERR-T0006" + Message.MSG_DELIM + status1;
            errorItem.setMessage(DispResources.getText(text));
            //リストに追加
            wErrorList.add(errorItem);
        }

        if (!roleNotExists.isEmpty())
        {
            Iterator itr = roleNotExists.iterator();

            while (itr.hasNext())
            {
                User user = (User)itr.next();

                //エラー内容をセット
                ErrorItem errorItem = new ErrorItem();
                errorItem.setLevel(DispResources.getText("LBL-T0102"));
                errorItem.setTableName(T_LOGINUSER);
                String status1 = user.getUserID();
                String status2 = user.getRoleID();
                String status3 = T_ROLE;
                String status4 = EmTableColumns.LOGINUSER_USERID + "=" + user.getUserID();
                //{0}の{1}は{2}に存在していません（{3}）
                String text =
                        "ERR-T0008" + Message.MSG_DELIM + status1 + Message.MSG_DELIM + status2 + Message.MSG_DELIM
                                + status3 + Message.MSG_DELIM + status4;
                errorItem.setMessage(DispResources.getText(text));
                //リストに追加
                wErrorList.add(errorItem);
            }

        }

        if (!userIdNotExist.isEmpty())
        {
            isOK = false;
            Iterator itr = userIdNotExist.iterator();

            while (itr.hasNext())
            {
                User user = (User)itr.next();

                // LoginUserのユーザIDがユーザアトリビュートテーブルにないメッセージ作成
                // リストに登録

                //エラー内容をセット
                ErrorItem errorItem = new ErrorItem();
                errorItem.setLevel(DispResources.getText("LBL-T0102"));
                errorItem.setTableName(T_LOGINUSER);
                String status1 = user.getUserID();
                String status2 = T_USERATTRIBUTE;
                String status3 = EmTableColumns.LOGINUSER_USERID + "=" + user.getUserID();;

                // {0}は{1}に存在していません。({2})
                String text =
                        "ERR-T0003" + Message.MSG_DELIM + status1 + Message.MSG_DELIM + status2 + Message.MSG_DELIM
                                + status3;
                errorItem.setMessage(DispResources.getText(text));
                //リストに追加
                wErrorList.add(errorItem);
            }
        }
        return isOK;
    }

    /*
     * ユーザアトリビュートチェック
     
     private boolean checkUserAttributesTable(User[] user, UserAttributes[] userAttributes)
     {
     boolean isExist;
     
     if(user == null)
     {
     user = new User[0];
     }
     
     
     for(int attributesCnt = 0; attributesCnt < userAttributes.length; attributesCnt++ )
     {
     // 存在フラグの初期化
     isExist = false;
     
     // ユーザアトリビュートのエンティティ取得
     UserAttributes userAttr = userAttributes[attributesCnt];
     
     // ユーザID取得
     String userId = userAttr.getUserID();
     
     for(int userCnt = 0; userCnt < user.length; userCnt++ )
     {
     // ユーザのエンティティ取得
     User usr = user[userCnt];
     
     // ユーザIDの比較
     if( userId.equals(usr.getUserID()) )
     {
     isExist = true;
     break;
     }
     }
     
     // ユーザが存在しなかった場合
     if( !isExist )
     {
     ErrorItem errorItem = new ErrorItem();
     errorItem.setLevel(DispResources.getText("LBL-T0102"));
     errorItem.setTableName(T_USERATTRIBUTE);
     String status1 = EmTableColumns.USERATTRIBUTE_USERID + ":" + userId;
     String status2 = EmTableColumns.USERATTRIBUTE_USERID + "=" + userAttr.getUserID();
     //{0}は{1}に存在していません。({2})
     String text = "ERR-T0003" + Message.MSG_DELIM + status1 + 
     Message.MSG_DELIM + T_LOGINUSER + 
     Message.MSG_DELIM + status2;
     errorItem.setMessage( DispResources.getText(text) );
     //リストに追加
     wErrorList.add(errorItem);
     }
     }
     return false;
     }*/

    /*
     * パスワード変更履歴チェック
     */
    private boolean checkPasswordHistoryTable(LogInfo[] logInfo, User[] user)
    {
        boolean isExist;

        if (user == null)
        {
            user = new User[0];
        }

        for (int logInfoCnt = 0; logInfoCnt < logInfo.length; logInfoCnt++)
        {
            // 存在フラグの初期化
            isExist = false;

            // パスワード履歴取得
            LogInfo info = logInfo[logInfoCnt];

            // ユーザID取得
            String userId = info.getUserId();

            for (int userCnt = 0; userCnt < user.length; userCnt++)
            {
                // ユーザのエンティティ取得
                User usr = user[userCnt];

                // ユーザIDの比較
                if (userId.equals(usr.getUserID()))
                {
                    isExist = true;
                    break;
                }
            }
            // ユーザが存在しなかった場合
            if (!isExist)
            {
                ErrorItem errorItem = new ErrorItem();
                errorItem.setLevel(DispResources.getText("LBL-T0102"));
                errorItem.setTableName(T_PASSWORDHISTORY);
                String status1 = EmTableColumns.COM_PASSWORDHISTORY_UESRID + ":" + userId;
                String status2 = EmTableColumns.COM_PASSWORDHISTORY_UESRID + "=" + info.getUserId();
                //{0}は{1}に存在していません。({2})
                String text =
                        "ERR-T0003" + Message.MSG_DELIM + status1 + Message.MSG_DELIM + T_LOGINUSER + Message.MSG_DELIM
                                + status2;
                errorItem.setMessage(DispResources.getText(text));
                //リストに追加
                wErrorList.add(errorItem);
            }
        }
        return false;
    }

    /*
     * ターミナル設定チェック
     */
    private boolean checkTerminalTable(Terminal[] terminals, Role[] roles)
    {
        /*
         * 設定しているロールIDがRole表に存在しているか確認する
         */
        boolean isExist;

        if (roles == null)
        {
            roles = new Role[0];
        }

        for (int terminalCnt = 0; terminalCnt < terminals.length; terminalCnt++)
        {
            // 存在フラグの初期化
            isExist = false;

            // パスワード履歴取得
            Terminal terminal = terminals[terminalCnt];

            // ターミナルID取得
            String terminalId = terminal.getRoleId();

            for (int roleCnt = 0; roleCnt < roles.length; roleCnt++)
            {
                // ロールのエンティティ取得
                Role role = roles[roleCnt];

                // ロールIDの比較
                if (terminalId.equals(role.getRoleID()))
                {
                    isExist = true;
                    break;
                }
            }
            // ロールが存在しなかった場合
            if (!isExist)
            {
                ErrorItem errorItem = new ErrorItem();
                errorItem.setLevel(DispResources.getText("LBL-T0102"));
                errorItem.setTableName(T_TERMINAL);
                String status1 = EmTableColumns.TERMINAL_ROLEID + ":" + terminalId;
                String status2 = EmTableColumns.TERMINAL_ROLEID + "=" + terminal.getTerminalNumber();
                //{0}は{1}に存在していません。({2})
                String text =
                        "ERR-T0003" + Message.MSG_DELIM + status1 + Message.MSG_DELIM + T_ROLE + Message.MSG_DELIM
                                + status2;
                errorItem.setMessage(DispResources.getText(text));
                //リストに追加
                wErrorList.add(errorItem);
            }
        }

        /*
         * UNDEFINED_TERMINALが存在しているか確認する
         */
        // 存在フラグの初期化
        isExist = false;
        for (int terminalCnt = 0; terminalCnt < terminals.length; terminalCnt++)
        {
            if (EmConstants.UNDEFINED_TERMINAL.equals(terminals[terminalCnt].getTerminalAddress()))
            {
                isExist = true;
                break;
            }
        }

        if (!isExist)
        {
            //エラー内容をセット
            ErrorItem errorItem = new ErrorItem();
            errorItem.setLevel(DispResources.getText("LBL-T0103"));
            errorItem.setTableName(T_TERMINAL);
            //{0}に{1}が登録されていません。
            String text =
                    "ERR-T0009" + Message.MSG_DELIM + T_TERMINAL + Message.MSG_DELIM + EmConstants.UNDEFINED_TERMINAL;
            errorItem.setMessage(DispResources.getText(text));
            //リストに追加
            wErrorList.add(errorItem);
        }
        /*
         * 127.0.0.1が存在しているか確認する
         */
        // 存在フラグの初期化
        isExist = false;
        for (int terminalCnt = 0; terminalCnt < terminals.length; terminalCnt++)
        {
            if ("127.0.0.1".equals(terminals[terminalCnt].getTerminalAddress()))
            {
                isExist = true;
                break;
            }
        }

        if (!isExist)
        {
            //エラー内容をセット
            ErrorItem errorItem = new ErrorItem();
            errorItem.setLevel(DispResources.getText("LBL-T0103"));
            errorItem.setTableName(T_TERMINAL);
            //{0}に{1}が登録されていません。
            String text = "ERR-T0009" + Message.MSG_DELIM + T_TERMINAL + Message.MSG_DELIM + "127.0.0.1";
            errorItem.setMessage(DispResources.getText(text));
            //リストに追加
            wErrorList.add(errorItem);
        }

        return false;
    }

    /** 
     * テーブルにデータが登録されているか確認を行います。 
     * @param dataList  確認を行うテーブルのデータ
     * @param tableName  エラー発見時にメッセージへ記録するテーブル名
     * @return 存在する場合にtrueを返します
     */
    private boolean isDefinedTableData(int size, String tableName)
    {
        //Error flag.
        boolean isOK = true;
        if (size == 0)
        {
            //エラー内容をセット
            ErrorItem errorItem = new ErrorItem();
            errorItem.setLevel(DispResources.getText("LBL-T0102"));
            errorItem.setTableName(tableName);
            String status1 = tableName;
            //{0}にはデータが登録されていません。
            String text = "ERR-T0007" + MSG_DELIM + status1;
            errorItem.setMessage(DispResources.getText(text));
            //リストに追加
            wErrorList.add(errorItem);
            isOK = false;
        }
        return isOK;
    }


    /** 
     * リソースキーがリソースファイルに存在するか確認 
     * @param resourcekey	リソースキー
     * @param tableName	エラー発見時にメッセージへ記録するテーブル名
     * @param mainColName	エラー発見時にメッセージへ記録する主キーの列名
     * @param resColName	リソースキーを定義している列名
     * @return リソースキーがファイルに存在する場合はtrueを返します
     * @throws Exception
     */
    private boolean isDefinedResourceKey(String resColName, String resColValue, String mainColName,
            String mainColValue, String tableName)
            throws Exception
    {
        //Error flag.
        boolean isOK = true;

        //取得したリソースキーでリソースファイルを検索
        try
        {
            DispResources.getText(resColValue);
        }
        catch (MissingResourceException mre)
        {
            //エラー内容をセット
            ErrorItem errorItem = new ErrorItem();
            errorItem.setLevel(DispResources.getText("LBL-T0103"));
            errorItem.setTableName(tableName);
            String status1 = resColName + ":" + resColValue;
            String status2 = mainColName + "=" + mainColValue;
            //{0}はリソースファイルに存在していません。({1})
            String text = "ERR-T0001" + MSG_DELIM + status1 + MSG_DELIM + status2;
            errorItem.setMessage(DispResources.getText(text));
            //リストに追加
            wErrorList.add(errorItem);
            isOK = false;
        }

        return isOK;
    }

    /** 
     * 数値かどうかの確認を行います。メニューIDはデータベース上、文字列として定義されますが、数値（0-9）のみを
     * 定義可能とするために確認を行います。
     * @param mainColValue 主キーの値
     * @param targetColValue 数値確認する値
     * @param tableName  エラー発見時にメッセージへ記録するテーブル名
     * @param mainColName エラー発見時にメッセージへ記録する主キーの列名
     * @param targetColName 数値確認をする列名
     * @return 数値の場合にtrueを返します
     */
    private boolean isNumber(String targetColName, String targetColValue, String mainColName, String mainColValue,
            String tableName)
    {
        //Error flag.
        boolean isOK = true;

        if (!this.isString(targetColValue, "0123456789"))
        {
            //エラー内容をセット
            ErrorItem errorItem = new ErrorItem();
            errorItem.setLevel(DispResources.getText("LBL-T0102"));
            errorItem.setTableName(tableName);
            String status1 = targetColName + ":" + targetColValue;
            String status2 = mainColName + "=" + mainColValue;
            //{0}に数値以外の文字が入っています。
            String text = "ERR-T0004" + MSG_DELIM + status1 + MSG_DELIM + status2;
            errorItem.setMessage(DispResources.getText(text));
            //リストに追加
            wErrorList.add(errorItem);
            isOK = false;
        }

        return isOK;
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
     * 開始ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Start_Click(ActionEvent e)
            throws Exception
    {
        //リストをクリア
        lst_FinalCheck.clearRow();

        Connection conn = null;
        try
        {
            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            //エラー格納リストを作成
            wErrorList = new ArrayList();

            //ハンドラー作成
            //メインメニュー設定
            MainMenuHandler mainHandler = EmHandlerFactory.getMainMenuHandler(conn);
            //サブメニュー設定
            SubMenuHandler subHandler = EmHandlerFactory.getSubMenuHandler(conn);
            //各機能画面設定
            FunctionHandler funcHandler = EmHandlerFactory.getFunctionHandler(conn);
            //ロール設定
            RoleHandler roleHandler = EmHandlerFactory.getRoleHandler(conn);
            //ロール機能画面マッピング
            RoleFunctionMapHandler rolefuncHandler = EmHandlerFactory.getRoleFunctionMapHandler(conn);
            //ログインユーザ設定
            UserHandler userHandler = EmHandlerFactory.getUserHandler(conn);
            //パスワード変更履歴
            PassWordHistoryHandler pwdHisHandler = EmHandlerFactory.getPassWordHistoryHandler(conn);
            //端末設定
            TerminalHandler terHandler = EmHandlerFactory.getTerminalHandler(conn);

            //DBより取得
            //メインメニュー設定
            MainMenu[] mainMenu_Array = mainHandler.findAll(0);

            //サブメニュー設定
            SubMenu[] subMenu_Array = subHandler.findAll(0, 0);

            //各機能画面設定
            Function[] function_Array = funcHandler.findAll(0);

            //ロール設定
            Role[] role_Array = roleHandler.findAll();

            //ロール機能画面マッピング
            Role[] roleFunctionMap_Array = rolefuncHandler.findAll();

            //ログインユーザ設定
            User[] user_Array = userHandler.findAll();

            //		/	UserAttributes[] userAtt_Array = userHandler.findUserAttributeAll();

            //パスワード変更履歴
            LogInfo[] pwdHistory_Array = pwdHisHandler.findAll();

            //端末設定
            Terminal[] terminal = terHandler.findAll(0);

            //MAINMENU表にデータが登録されているか確認
            if (isDefinedTableData(mainMenu_Array == null ? 0
                                                         : mainMenu_Array.length, T_MAINMENU))
            {
                this.checkMainMenuTable(mainMenu_Array);
            }

            //SUBMENU表にデータが登録されているか確認
            if (isDefinedTableData(subMenu_Array == null ? 0
                                                        : subMenu_Array.length, T_SUBMENU))
            {
                this.checkSubMenuTable(subMenu_Array, mainMenu_Array);
            }

            //FUNCTION表にデータが登録されているか確認  	
            if (isDefinedTableData(function_Array == null ? 0
                                                         : function_Array.length, T_FUNCTION))
            {
                this.checkFunctionTable(function_Array, subMenu_Array);
            }

            //COM_ROLEにデータが登録されているか確認
            if (isDefinedTableData(role_Array == null ? 0
                                                     : role_Array.length, T_ROLE))
            {
                this.checkRoleTable(role_Array);
            }

            // FunktionRoleMapにデータが登録されているか確認
            if (isDefinedTableData(roleFunctionMap_Array == null ? 0
                                                                : roleFunctionMap_Array.length, T_ROLEFUNCTIONMAP))
            {
                this.checkRoleFunctionMapTable(roleFunctionMap_Array, function_Array, role_Array);
            }

            // COM_LOGINUSERにデータが登録されているか確認
            if (isDefinedTableData(user_Array == null ? 0
                                                     : user_Array.length, T_LOGINUSER))
            {
                //this.checkUserTable(user_Array, userAtt_Array, role_Array);
                this.checkUserTable(user_Array, role_Array);
            }

            // COM_USERATTRIBUTE表にデータが登録されているか確認
            /*	if( isDefinedTableData(userAtt_Array == null ? 0 : userAtt_Array.length, T_TERMINAL) )
             {
             this.checkUserAttributesTable(user_Array, userAtt_Array);
             }*/

            // COM_PASSWORDHISTORY表にデータが登録されているか確認
            if (pwdHistory_Array != null && pwdHistory_Array.length > 0)
            {
                this.checkPasswordHistoryTable(pwdHistory_Array, user_Array);
            }

            // COM_TERMINL表にデータが登録されているか確認
            if (isDefinedTableData(terminal == null ? 0
                                                   : terminal.length, T_TERMINAL))
            {
                this.checkTerminalTable(terminal, role_Array);
            }

            //Set the listdata.
            setList();

            //エラーがない場合
            if (wErrorList.size() == 0)
            {
                //エラーはありませんでした。
                message.setMsgResourceKey("6401012");
            }
            else
            {
                //エラーがあります。確認して下さい。
                message.setMsgResourceKey("6403023");
            }
            //メンテナンスログに出力するメッセージ
            //整合性確認を行いました。
            String mainteLog_msg = "6400008";
            //メンテナンスログ出力
            EmDBLog.writeMaintenanceLog(this, EmConstants.MAINTELOG_CLASS_SYSTEM, mainteLog_msg, "");

            DfkUserInfo userInfo = (DfkUserInfo)this.getUserInfo();
            //項目リストの生成
            List list = new ArrayList();
            P11LogWriter logWriter = new P11LogWriter(conn);
            logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_SETTING, list);

            conn.commit();

        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * クリアボタンの処理を実装します
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        //リストをクリア
        lst_FinalCheck.clearRow();
    }

    /** 
     * エラー内容をセット・取得するためのクラス 
     */
    class ErrorItem
    {
        //エラー区分
        private String wLevel = null;

        //エラー箇所
        private String wTableName = null;

        //エラー内容
        private String wMessage = null;

        /**
         * エラー区分をセットします。
         * @param level エラー区分
         */
        public void setLevel(String level)
        {
            wLevel = level;
        }

        /**
         * エラー箇所をセットします。
         * @param tablename エラー箇所
         */
        public void setTableName(String tablename)
        {
            wTableName = tablename;
        }

        /**
         * エラー内容をセットします。
         * @param message エラー内容
         */
        public void setMessage(String message)
        {
            wMessage = message;
        }

        /**
         * エラー区分を取得します。
         * @return エラー区分
         */
        public String getLevel()
        {
            return wLevel;
        }

        /**
         * エラー箇所を取得します。
         * @return エラー箇所
         */
        public String getTableName()
        {
            return wTableName;
        }

        /**
         * エラー内容を取得します。
         * @return エラー内容
         */
        public String getMessage()
        {
            return wMessage;
        }
    }

    /** <jp>
     * 指定文字列精査。
     *
     * @param value	      対象オブジェクト
     * @param inspections 有効な文字（例：0123456789 とすると数値だけ有効）
     * @return 有効な文字のみであればtrue。それ以外はfalseを返却します。
     </jp> */
    /** <en>
     * Specification character sequence scrutinization.
     *
     * @param value	      object
     * @param inspections An effective character(Example:Only a numerical value is effective when "0123456789")
     * @return TRUE is returned when it consists of only specified characters. FALSE is returned when the character which is not specified is included.
     </en> */
    private boolean isString(Object value, String inspections)
    {

        if (value == null) value = "";
        String check = value.toString();

        if (check.length() == 0)
        {
            return false;
        }
        StringTokenizer token = new StringTokenizer(check.toLowerCase(), inspections);
        return !token.hasMoreTokens();

    }

}
//end of class

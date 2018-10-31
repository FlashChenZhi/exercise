// $Id: SearchMainteLog1Business.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.logview;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.LogInfo;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.LogTempHandler;
import jp.co.daifuku.emanager.display.web.logview.listbox.CsvLogFileListBusiness;
import jp.co.daifuku.emanager.util.CsvLogUtil;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

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
public class SearchMainteLog1Business
        extends SearchMainteLog1
        implements EmConstants
{
    // Class fields --------------------------------------------------

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

        if (lst_ReadList.getMaxRows() > 1)
        {
            // 設定ボタン・全取り消しボタン非活性
            btn_Commit.setEnabled(true);
            btn_Cancel.setEnabled(true);
        }
        else
        {
            if (!chk_IncludeLog.getChecked())
            {
                // 設定ボタン・全取り消しボタン非活性
                btn_Commit.setEnabled(false);
                btn_Cancel.setEnabled(false);
            }
        }

        // 「設定」ボタンを同期化にする。
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
        Connection conn = null;

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            LogTempHandler handler = EmHandlerFactory.getLogTempHandler(conn);
            LogInfo[] logInfo = handler.findAllMainteLogbyDistinct();

            // get date display format based on the country
            String dateFormat = EManagerUtil.getDateFormat(this.httpRequest.getLocale(), 0);
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);


            if (logInfo != null)
            {
                for (int cnt = 0; cnt < logInfo.length; cnt++)
                {
                    LogInfo info = logInfo[cnt];

                    int count = lst_ReadList.getMaxRows();
                    lst_ReadList.setCurrentRow(count);
                    lst_ReadList.addRow();

                    lst_ReadList.setValue(2, info.getFileName());
                    lst_ReadList.setValue(3, info.getUpdateUser());
                    Date date = info.getUpdateDate();
                    String value = formatter.format(date);
                    lst_ReadList.setValue(4, value);
                }

                if (logInfo.length > 0)
                {
                    // 設定ボタン・全取り消しボタン活性
                    btn_Commit.setEnabled(true);
                    btn_Cancel.setEnabled(true);
                }
            }

            if (this.getViewState().getString("Database") != null)
            {
                if (this.getViewState().getString("Database").equals("ON"))
                {
                    chk_IncludeLog.setChecked(true);
                    btn_Commit.setEnabled(true);
                }
                else
                {
                    chk_IncludeLog.setChecked(false);
                }
            }

        }
        catch (SQLException dbe)
        {
            throw dbe;
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
     * ポップアップウインドから、戻ってくるときにこのメソッドが 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
     * 
     * @param e
     *            ActionEvent
     * @exception Exception
     */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        DialogParameters param = ((DialogEvent)e).getDialogParameters();

        // CSVファイル名取得
        String csvFile = param.getParameter(CsvLogFileListBusiness.KEY);

        // コントロールに表示
        txt_CsvFile.setText(csvFile);

        if (lst_ReadList.getMaxRows() > 1)
        {
            // 設定ボタン・全取消ボタン活性
            btn_Commit.setEnabled(true);
            btn_Cancel.setEnabled(true);
        }

        if (chk_IncludeLog.getChecked())
        {
            // 設定ボタン活性
            btn_Commit.setEnabled(true);
        }

    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------


    // Event handler methods -----------------------------------------

    /** 
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
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void chk_IncludeLog_Server(ActionEvent e)
            throws Exception
    {
        // チェックオン状態の場合
        if (chk_IncludeLog.getChecked())
        {
            // 「設定」ボタンを有効化
            btn_Commit.setEnabled(true);
        }

        // チェックオフ状態
        if (!chk_IncludeLog.getChecked())
        {
            // リストセルのデータを確認
            if (lst_ReadList.getMaxRows() == 1)
            {
                // 設定ボタン・全取り消しボタン非活性
                btn_Commit.setEnabled(false);
                btn_Cancel.setEnabled(false);
            }
        }
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Add_Click(ActionEvent e)
            throws Exception
    {
        // 必須入力チェック 
        txt_CsvFile.validate();

        // ListCellのヘッダ以外にデータが登録されている場合
        if (lst_ReadList.getMaxRows() > 1)
        {
            // 登録チェック
            for (int cnt = 1; cnt < lst_ReadList.getMaxRows(); cnt++)
            {
                // カレント行を設定
                lst_ReadList.setCurrentRow(cnt);

                if (lst_ReadList.getValue(2).equals(txt_CsvFile.getText()))
                {
                    // すでに同一のCSVファイルが登録されています。
                    message.setMsgResourceKey("6403048");

                    if (lst_ReadList.getMaxRows() > 1)
                    {
                        // 設定ボタン・全取り消しボタン活性
                        btn_Commit.setEnabled(true);
                        btn_Cancel.setEnabled(true);
                    }

                    return;
                }
            }
        }

        // ユーザ情報の取得
        DfkUserInfo userinfo = (DfkUserInfo)getUserInfo();
        UserInfoHandler userhandler = new UserInfoHandler(userinfo);

        String userId = userhandler.getUserID();
        String ipAddr = this.getHttpRequest().getRemoteAddr();

        // データのインポート
        boolean isReg = CsvLogUtil.importCsv(txt_CsvFile.getText(), userId, ipAddr);

        Connection conn = null;

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            LogTempHandler handler = EmHandlerFactory.getLogTempHandler(conn);
            LogInfo[] logInfo = handler.findAllMainteLogbyDistinct();
            lst_ReadList.clearRow();

            // get date display format based on the country
            String dateFormat = EManagerUtil.getDateFormat(this.httpRequest.getLocale(), 0);
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

            if (logInfo != null)
            {
                for (int cnt = 0; cnt < logInfo.length; cnt++)
                {
                    LogInfo info = logInfo[cnt];

                    int count = lst_ReadList.getMaxRows();
                    lst_ReadList.setCurrentRow(count);
                    lst_ReadList.addRow();
                    lst_ReadList.setValue(2, info.getFileName());
                    lst_ReadList.setValue(3, info.getUpdateUser());
                    Date date = info.getUpdateDate();
                    String value = formatter.format(date);
                    lst_ReadList.setValue(4, value);
                }
            }

            if (lst_ReadList.getMaxRows() > 1)
            {
                // 設定ボタン・全取り消しボタン活性
                btn_Commit.setEnabled(true);
                btn_Cancel.setEnabled(true);
            }

            if (logInfo != null)
            {
                for (int cnt = 0; cnt < logInfo.length; cnt++)
                {
                    LogInfo info = logInfo[cnt];

                    if (info.getFileName().equals(txt_CsvFile.getText()))
                    {
                        P11LogWriter writer = new P11LogWriter(conn);

                        List itemList = new ArrayList();

                        // ファイル名
                        itemList.add(info.getFileName());
                        // 読み込みユーザID
                        itemList.add(info.getUpdateUser());
                        // 読み込み日次
                        itemList.add(formatter.format(info.getUpdateDate()));

                        // ログ出力
                        writer.createOperationLog(userinfo, EmConstants.OPELOG_CLASS_INPUT, itemList);

                        break;
                    }
                }
            }

            // テキストボックスのクリア
            txt_CsvFile.setText("");

            EmConnectionHandler.commit(conn);
        }
        catch (SQLException dbe)
        {
            EmConnectionHandler.rollback(conn);
            throw dbe;
        }
        finally
        {
            if (conn != null)
            {
                EmConnectionHandler.closeConnection(conn);
            }
        }

        if (isReg)
        {
            // 登録しました。
            message.setMsgResourceKey("6401003");
        }
        else
        {
            // すでに同一のCSVファイルが登録されています。
            message.setMsgResourceKey("6403048");
        }
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        if (lst_ReadList.getMaxRows() > 1)
        {
            // 設定ボタン・全取り消しボタン非活性
            btn_Commit.setEnabled(true);
            btn_Cancel.setEnabled(true);
        }

        // テキストボックスのクリア
        txt_CsvFile.setText("");
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Commit_Click(ActionEvent e)
            throws Exception
    {
        //選択列をViewStateに保存
        if (chk_IncludeLog.getChecked())
        {
            this.getViewState().setString("Database", "ON");
        }
        else
        {
            this.getViewState().setString("Database", "OFF");
        }

        //2画面目に遷移
        forward("/emanager/logview/SearchMainteLog2.do");
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Cancel_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;

        // ファイル名取得
        //String fileName = lst_ReadList.getValue(2);

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            LogTempHandler handler = EmHandlerFactory.getLogTempHandler(conn);

            // 削除処理
            handler.deleteAllMainteTemp();

            P11LogWriter writer = new P11LogWriter(conn);

            List itemList = new ArrayList();

            for (int i = 1; i < lst_ReadList.getMaxRows(); i++)
            {
                lst_ReadList.setCurrentRow(i);

                itemList.clear();

                // CSVファイル
                itemList.add(lst_ReadList.getValue(2));
                // 読み込みユーザ
                itemList.add(lst_ReadList.getValue(3));
                // 読み込み日次
                itemList.add(lst_ReadList.getValue(4));

                writer.createOperationLog((DfkUserInfo)getUserInfo(), EmConstants.OPELOG_CLASS_ALL_DELETE, itemList);
            }

            // コミット
            conn.commit();

            // リストセルの削除処理
            lst_ReadList.clearRow();

            // 削除しました
            message.setMsgResourceKey("6401005");

            // データベースのチェックが外れていた場合
            if (!chk_IncludeLog.getChecked())
            {
                // 設定ボタン非活性
                btn_Commit.setEnabled(false);
            }

            // 全取消ボタン非活性
            btn_Cancel.setEnabled(false);

            // テキストボックスのクリア
            txt_CsvFile.setText("");

        }
        catch (SQLException dbe)
        {
            EmConnectionHandler.rollback(conn);
            throw dbe;
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
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void lst_ReadList_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;

        //現在の行をセット
        lst_ReadList.setCurrentRow(lst_ReadList.getActiveRow());

        // ファイル名取得
        String fileName = lst_ReadList.getValue(2);

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            LogTempHandler handler = EmHandlerFactory.getLogTempHandler(conn);

            // 削除処理
            handler.deleteMainteTempByFileName(fileName);

            P11LogWriter writer = new P11LogWriter(conn);

            List itemList = new ArrayList();

            // CSVファイル
            itemList.add(lst_ReadList.getValue(2));
            // 読み込みユーザ
            itemList.add(lst_ReadList.getValue(3));
            // 読み込み日次
            itemList.add(lst_ReadList.getValue(4));

            writer.createOperationLog((DfkUserInfo)getUserInfo(), EmConstants.OPELOG_CLASS_DELETE, itemList);

            // コミット
            conn.commit();

            // リストセルの削除処理
            lst_ReadList.removeRow(lst_ReadList.getActiveRow());

            if (lst_ReadList.getMaxRows() > 1)
            {
                // 設定ボタン・全取り消しボタン活性
                btn_Commit.setEnabled(true);
                btn_Cancel.setEnabled(true);
            }
            else
            {
                // データベースのチェックが外れていた場合
                if (!chk_IncludeLog.getChecked())
                {
                    // 設定ボタン非活性
                    btn_Commit.setEnabled(false);
                }
                // 全取消ボタン非活性
                btn_Cancel.setEnabled(false);
            }
        }
        catch (SQLException dbe)
        {
            EmConnectionHandler.rollback(conn);
            throw dbe;
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
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Search_Click(ActionEvent e)
            throws Exception
    {
        //メインメニュー一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();

        // パラメータ情報の作成
        param.addParameter(CsvLogFileListBusiness.KEY, CsvLogFileListBusiness.COM_MAINTENANCELOG);

        //処理中画面->結果画面
        redirect("/emanager/logview/listbox/CsvLogFileList.do", param, "/Progress.do");

    }

}
//end of class

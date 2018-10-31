// $Id: CsvLogImportBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.logview;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.Validator;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.LogExpImpSet;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.LogExpImpSetHandler;
import jp.co.daifuku.emanager.display.web.logview.listbox.CsvLogFileListBusiness;
import jp.co.daifuku.emanager.util.CsvLogUtil2;
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
public class CsvLogImportBusiness
        extends CsvLogImport
        implements EmConstants
{
    // Class fields --------------------------------------------------
    /**
     * 履歴ファイル検索ポップアップから戻ってきた場合の対象行Noを保持するキー
     */
    private static final String CURRENT_ROW_KEY = "CURRENT_ROW_KEY";

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------


    /* (non-Javadoc)
     * @see jp.co.daifuku.bluedog.ui.control.Page#page_DlgBack(jp.co.daifuku.bluedog.webapp.ActionEvent)
     */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        DialogParameters param = ((DialogEvent)e).getDialogParameters();
        // CSVファイル名
        String fileName = param.getParameter(CsvLogFileListBusiness.KEY);
        // ディレクトリ名
        String dirName = param.getParameter(CsvLogFileListBusiness.DIR_KEY);

        //空ではないときに値をセットする
        if (!Validator.isEmptyCheck(fileName))
        {
            Object obj = getSession().getAttribute(CURRENT_ROW_KEY);
            int currentRow = Integer.parseInt((String)obj);
            getSession().removeAttribute(CURRENT_ROW_KEY);

            lst_LogFileImport.setCurrentRow(currentRow);

            lst_LogFileImport.setValue(3, fileName);


            // hiddenListを取得
            List hiddenList = CollectionUtils.getList(lst_LogFileImport.getValue(0));
            // hidden項目にディレクトリ名を追加
            hiddenList.add(dirName);

            String hidden = CollectionUtils.getConnectedString(hiddenList);
            // hiddenを再設定
            lst_LogFileImport.setValue(0, hidden);
        }
    }

    /** <jp>
     * 画面の初期化を行います。
     * @param e ActionEvent
     </jp> */
    /** <en>
     * This screen is initialized.
     * @param e ActionEvent
     </en> */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        Connection conn = null;

        try
        {
            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            LogExpImpSetHandler logSetHandler = EmHandlerFactory.getLogExpImpSetHandler(conn);

            LogExpImpSet[] settings = logSetHandler.findAll();

            if (settings == null)
            {
                return;
            }

            lst_LogFileImport.clearRow();

            for (int i = 0; i < settings.length; i++)
            {
                lst_LogFileImport.addRow();
                lst_LogFileImport.setCurrentRow(lst_LogFileImport.getMaxRows() - 1);

                // 履歴名称
                lst_LogFileImport.setValue(2, DispResources.getText(settings[i].getTableResourceKey()));
                // 取込ファイル
                lst_LogFileImport.setValue(3, "");
                // 前回取込ファイル
                lst_LogFileImport.setValue(5, settings[i].getImportFileName());

                // hidden項目の生成
                List hiddenList = new ArrayList();

                // CSVファイルプレフィックス
                hiddenList.add(settings[i].getCsvFilePrefix());
                // インポートテーブル名
                hiddenList.add(settings[i].getImportTable());

                String hidden = CollectionUtils.getConnectedString(hiddenList);
                lst_LogFileImport.setValue(0, hidden);
            }
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.bluedog.ui.control.Page#page_Initialize(jp.co.daifuku.bluedog.webapp.ActionEvent)
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


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Event handler methods -----------------------------------------

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Commit_Click(ActionEvent e)
            throws Exception
    {
        boolean checked = false;

        for (int i = 1; i < lst_LogFileImport.getMaxRows(); i++)
        {
            lst_LogFileImport.setCurrentRow(i);

            if (lst_LogFileImport.getChecked(1))
            {
                checked = true;
                if (Validator.isEmptyCheck(lst_LogFileImport.getValue(3)))
                {
                    // 選択チェックがあり、ファイル指定が無い場合はエラー
                    message.setMsgResourceKey("6403080");
                    ArrayList params = new ArrayList();
                    params.add(lst_LogFileImport.getValue(2));
                    message.setMsgParameter(params);
                    return;
                }
            }
        }

        // 1件も選択されていない場合
        if (!checked)
        {
            message.setMsgResourceKey("6403081");
            return;
        }

        Connection conn = null;

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);

            P11LogWriter writer = new P11LogWriter(conn);

            List itemList = new ArrayList();

            for (int i = 1; i < lst_LogFileImport.getMaxRows(); i++)
            {
                lst_LogFileImport.setCurrentRow(i);

                if (lst_LogFileImport.getChecked(1))
                {
                    String hidden = lst_LogFileImport.getValue(0);

                    String tableName = CollectionUtils.getString(1, hidden);
                    String dir = CollectionUtils.getString(2, hidden);
                    String file = lst_LogFileImport.getValue(3);

                    try
                    {
                        // 取込処理実行
                        CsvLogUtil2.importData(conn, dir, file, tableName);
                    }
                    catch (Exception ex)
                    {
                        throw new RuntimeException("import error. file name is " + file, ex);
                    }

                    itemList.clear();

                    // 履歴テーブル
                    itemList.add(lst_LogFileImport.getValue(2));
                    // 取込ファイル名
                    itemList.add(lst_LogFileImport.getValue(3));
                    // 前回取込ファイル名
                    itemList.add(lst_LogFileImport.getValue(5));

                    writer.createOperationLog((DfkUserInfo)getUserInfo(), EmConstants.OPELOG_CLASS_SETTING, itemList);
                }
            }

            EmConnectionHandler.commit(conn);
        }
        catch (Exception ex)
        {
            EmConnectionHandler.rollback(conn);
            throw ex;
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }

        // 再表示処理
        page_Load(null);

        // 登録しました。
        message.setMsgResourceKey("6401003");
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        for (int i = 1; i < lst_LogFileImport.getMaxRows(); i++)
        {
            lst_LogFileImport.setCurrentRow(i);

            // 選択チェックをクリア
            lst_LogFileImport.setChecked(1, false);
            // 取込ファイルをクリア
            lst_LogFileImport.setValue(3, "");
        }
    }


    /** <jp>
     * 検索ボタンが押下された場合に呼び出されます。
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void lst_LogFileImport_Click(ActionEvent e)
            throws Exception
    {
        // 押下された行Noを保持する
        getSession().setAttribute(CURRENT_ROW_KEY, String.valueOf(lst_LogFileImport.getActiveRow()));

        lst_LogFileImport.setCurrentRow(lst_LogFileImport.getActiveRow());

        ForwardParameters param = new ForwardParameters();

        // Part11フラグ
        param.addParameter(CsvLogFileListBusiness.PART11_KEY, Boolean.TRUE.toString());
        // hidden項目の取得
        String hidden = lst_LogFileImport.getValue(0);
        // ファイルプレフィックス
        String prefix = CollectionUtils.getString(0, hidden);

        param.addParameter(CsvLogFileListBusiness.FILE_PREFIX, prefix);

        redirect("/emanager/logview/listbox/CsvLogFileList.do", param, "/Progress.do");
    }


}
//end of class

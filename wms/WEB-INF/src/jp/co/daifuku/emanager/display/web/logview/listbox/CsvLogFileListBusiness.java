// $Id: CsvLogFileListBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.logview.listbox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.emanager.util.P11LogController;
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
public class CsvLogFileListBusiness
        extends CsvLogFileList
{
    // Class fields --------------------------------------------------
    /**
     * 
     */
    private static final String CSV_FILE = "CsvLogImportPath";

    /**
     * 
     */
    public static final String KEY = "FILE";

    /**
     * 
     */
    public static final String DIR_KEY = "DIR";

    /**
     * 
     */
    public static final String COM_AUTHENTICATIONLOG = "COM_AUTHENTICATIONLOG";

    /**
     * 
     */
    public static final String COM_MAINTENANCELOG = "COM_MAINTENANCELOG";

    /**
     * 
     */
    public static final String PART11_KEY = "PART11_KEY";

    /**
     * 
     */
    public static final String FILE_PREFIX = "FILE_PREFIX";

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /**
     * 画面の初期化を行います。
     * 
     * @param e   ActionEvent
     * @exception Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        try
        {
            //画面名をセットする
            lbl_ListName.setText(DispResources.getText("TLE-T0037"));

            //Pager初期化
            setPagerValue(0, 0, 0);

            //1ページの表示件数
            int page = Integer.parseInt(EmProperties.getProperty("ListboxSearchCount"));

            //最初のページの表示終了位置
            int end = 0;

            // 呼び出し元画面でセットされたパラメータの取得
            String param = request.getParameter(KEY);
            // Part11フラグ(履歴ファイル取込から呼び出されたかどうか)
            boolean searchPart11File = new Boolean(request.getParameter(PART11_KEY)).booleanValue();
            String prefix = request.getParameter(FILE_PREFIX);

            // ViewStateに保存
            this.getViewState().setString(KEY, param);
            this.getViewState().setBoolean(PART11_KEY, searchPart11File);
            this.getViewState().setString(FILE_PREFIX, prefix);

            ArrayList fileList = null;

            // Part11用ファイルを検索する場合
            if (searchPart11File)
            {
                fileList = this.part11FileSearch(prefix);
            }
            // 認証ログ、メンテナンスログを検索する場合
            else
            {
                fileList = this.fileSearch(param);
            }

            // ファイル数取得
            int total = fileList.size();

            // データがある場合
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
                setList(fileList, 0, end);

                //pagerに値をセット
                setPagerValue(1, total, page);
            }
            else
            {
                //Pagerへの値セット
                pager.setMax(0); //最大件数
                pager.setPage(0); //1Page表示件数
                pager.setIndex(0); //開始位置               

                // ヘッダーを非表示
                lst_CsvFileList.setVisible(false);

                //対象データはありませんでした
                message.setMsgResourceKey("6403077");
            }
        }
        catch (Exception ex)
        {
            //Pagerへの値セット
            pager.setMax(0); //最大件数
            pager.setPage(0); //1Page表示件数
            pager.setIndex(0); //開始位置               

            // ヘッダーを非表示
            lst_CsvFileList.setVisible(false);

            if (ex.getMessage() != null)
            {
                //対象データはありませんでした
                message.setMsgResourceKey("6403077");
            }
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
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
     * リストセルに値をセットします
     * @param fileList ファイル一覧のリスト
     * @param next_index int
     * @param next_end int 
     * @throws Exception 
     */
    private void setList(ArrayList fileList, int next_index, int next_end)
            throws Exception
    {
        //表を削除
        lst_CsvFileList.clearRow();

        ArrayList list = this.getArray(fileList, next_index, next_end);

        for (int i = 0; i < list.size(); i++)
        {
            int count = lst_CsvFileList.getMaxRows();
            lst_CsvFileList.setCurrentRow(count);
            lst_CsvFileList.addRow();

            File file = (File)list.get(i);

            List hiddenList = new ArrayList();
            // フォルダを取得
            hiddenList.add(file.getParent());
            String hidden = CollectionUtils.getConnectedString(hiddenList);

            lst_CsvFileList.setValue(0, hidden);
            lst_CsvFileList.setValue(1, Integer.toString(count + next_index));
            lst_CsvFileList.setValue(2, file.getName());
        }
    }

    /** 
     * ファイル一覧を取得します。
     * @param text
     * @return ファイル一覧のリスト
     * @throws Exception
     */
    private ArrayList fileSearch(String text)
            throws Exception
    {
        // 出力先を取得
        String exportPath = EmProperties.getProperty(CSV_FILE);

        ArrayList fileList = new ArrayList();

        File dir = new File(exportPath);

        // 存在するか？
        if (!dir.exists())
        {
            String msg = "6407005" + Message.MSG_DELIM + exportPath;
            throw new Exception(msg);
        }

        // ディレクトリか？
        if (!dir.isDirectory())
        {
            String msg = "6407003" + Message.MSG_DELIM + exportPath;
            throw new Exception(msg);
        }

        if (dir.listFiles() != null)
        {
            for (int cnt = 0; cnt < dir.listFiles().length; cnt++)
            {
                // フォルダの場合は格納しない
                if (dir.listFiles()[cnt].isFile() == false)
                {
                    continue;
                }

                // ファイル名取得
                String fileName = dir.listFiles()[cnt].getName();

                // ファイル名にパラメータの文字列が含まれる場合
                if (fileName.indexOf(text) >= 0)
                {
                    // ファイル一覧に追加
                    fileList.add(dir.listFiles()[cnt]);
                }
            }
        }

        // ファイル一覧の返却
        return fileList;
    }


    /** 
     * Part11用のファイル一覧を取得します。
     * 
     * @param prefix ファイルプレフィックス
     * @return ファイル一覧のリスト
     * @throws Exception
     */
    private ArrayList part11FileSearch(String prefix)
            throws Exception
    {
        // エクスポートパスを取得
        String exportPath = EmProperties.getProperty(EmConstants.PART11LOG_BACKUP_PATH);

        File exportDir = new File(exportPath);

        // エクスポートディレクトリが存在するか？
        if (!exportDir.exists())
        {
            String msg = "6407005" + Message.MSG_DELIM + exportPath;
            throw new Exception(msg);
        }

        // ディレクトリか？
        if (!exportDir.isDirectory())
        {
            String msg = "6407003" + Message.MSG_DELIM + exportPath;
            throw new Exception(msg);
        }

        // ファイル名とFileオブジェクトを保持するマップ
        Map fileMap = new TreeMap();

        if (exportDir.listFiles() != null)
        {
            for (int cnt = 0; cnt < exportDir.listFiles().length; cnt++)
            {
                // フォルダの場合は格納しない
                if (exportDir.listFiles()[cnt].isFile() == false)
                {
                    continue;
                }

                // ファイル名取得
                String fileName = exportDir.listFiles()[cnt].getName();

                // prefixチェック
                if (fileName.startsWith(prefix))
                {
                    // ファイル一覧に追加
                    fileMap.put(fileName, exportDir.listFiles()[cnt]);
                }
            }
        }

        // バックアップ用メディアのパスを取得
        String backUpDiskPath = EmProperties.getProperty(EmConstants.PART11LOG_EXTERNAL_DISK_PATH);

        if (P11LogController.canAccessDVDDrive(backUpDiskPath))
        {
            File backUpDisk = new File(backUpDiskPath);

            if (backUpDisk.exists() && backUpDisk.listFiles() != null)
            {
                for (int cnt = 0; cnt < backUpDisk.listFiles().length; cnt++)
                {
                    // フォルダの場合は格納しない
                    if (backUpDisk.listFiles()[cnt].isFile() == false)
                    {
                        continue;
                    }

                    // ファイル名取得
                    String fileName = backUpDisk.listFiles()[cnt].getName();

                    // prefixチェック
                    if (fileName.startsWith(prefix))
                    {
                        // 既に同じファイルがある場合はローカルディスクを優先
                        if (!fileMap.containsKey(fileName))
                        {
                            // ファイル一覧に追加
                            fileMap.put(fileName, backUpDisk.listFiles()[cnt]);
                        }
                    }
                }
            }
        }


        // ファイル一覧の返却
        return new ArrayList(fileMap.values());
    }

    /** 
     * ファイル一覧から指定件数ファイルを取得します。
     * @param fileList ファイル一覧
     * @param start 開始インデックス
     * @param end 終了インデックス
     * @return 結果のリスト
     */
    private ArrayList getArray(ArrayList fileList, int start, int end)
    {
        ArrayList list = new ArrayList();

        for (int cnt = start; cnt < end; cnt++)
        {
            if (fileList.size() < cnt)
            {
                break;
            }

            list.add(fileList.get(cnt));
        }

        return list;
    }

    // Event handler methods -----------------------------------------

    /** 
     * 
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
     * 
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

        // ViewStateから情報取得
        String param = this.getViewState().getString(KEY);

        // ファイルの一覧を取得
        ArrayList fileList = this.fileSearch(param);

        //リストデータをセット
        setList(fileList, next_index, next_end);

        //Pagerに値をセット
        setPagerValue(next_index + 1, total, page);
    }

    /** 
     * 
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

        // 呼び出し元画面でセットされたパラメータの取得
        String param = this.getViewState().getString(KEY);

        // ファイルの一覧を取得
        ArrayList fileList = this.fileSearch(param);

        //リストデータをセット
        setList(fileList, next_index, next_end);

        //Pagerに値をセット
        setPagerValue(next_index + 1, total, page);
    }

    /** 
     * 
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

        // 呼び出し元画面でセットされたパラメータの取得
        String param = this.getViewState().getString(KEY);

        // ファイルの一覧を取得
        ArrayList fileList = this.fileSearch(param);

        //リストデータをセット
        setList(fileList, next_index, next_end);

        //Pagerに値をセット
        setPagerValue(next_index + 1, total, page);
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void pager_First(ActionEvent e)
            throws Exception
    {
        int total = pager.getMax();
        int page = pager.getPage();

        // 呼び出し元画面でセットされたパラメータの取得
        String param = this.getViewState().getString(KEY);

        // ファイルの一覧を取得
        ArrayList fileList = this.fileSearch(param);

        //リストデータをセット
        setList(fileList, 0, page);

        //Pagerに値をセット
        setPagerValue(1, total, page);
    }


    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void lst_CsvFileList_Click(ActionEvent e)
            throws Exception
    {
        //現在の行をセット
        lst_CsvFileList.setCurrentRow(lst_CsvFileList.getActiveRow());

        //呼び出し元の画面へ渡すパラメータ作成
        ForwardParameters param = new ForwardParameters();

        // hidden
        String hidden = lst_CsvFileList.getValue(0);

        // ファイル名設定
        param.setParameter(KEY, lst_CsvFileList.getValue(2));
        // ディレクトリ設定
        param.setParameter(DIR_KEY, CollectionUtils.getString(0, hidden));

        // 終了処理
        btn_Close_Click(null);

        // 呼び出し元の画面へ遷移します
        parentRedirect(param);

    }

}
//end of class

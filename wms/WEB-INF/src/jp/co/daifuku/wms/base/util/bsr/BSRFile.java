//$Id: BSRFile.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util.bsr;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * BSR情報用ファイルを扱うためのクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class BSRFile
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * <code>WMS_RESOURCE</code><br>
     * WMSリソース名<br>
     */
    private static final String WMS_RESOURCE = "WMSParam";

    /**
     * <code>BSR_DIR</code><br>
     * BSR情報ファイル記録フォルダ名<br>
     */
    private static final String BSR_DIR = "BSR_DIR";

    /** ファイル出力先ディレクトリ */
    static final File FILE_DIR = getBSRInfoDir();

    /** ログファイル拡張子 */
    static final String LOG_SUFFIX = ".log";

    /** ステータスファイル拡張子 */
    static final String STS_SUFFIX = ".status";

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // FileFilter用変数
    private String _startStr;

    private String _endStr;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。<br>
     */
    public BSRFile()
    {
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * BSR情報ファイルを削除します。
     * BSR定義ファイルにて定義された最低保持件数までファイルを削除します。
     */
    public void clear()
    {
        // BSR定義ファイルのロード
        BSRCategory bc = new BSRCategory();

        // カテゴリIDの一覧取得
        String[] catList = bc.getCategoryList();
        for (int i = 0; i < catList.length; i++)
        {
            // ログファイルの一覧取得
            File[] logFileList = getFiles(FILE_DIR, catList[i], LOG_SUFFIX);
            int intLogKeep = bc.getFileKeepNum(catList[i]);
            if (logFileList.length > intLogKeep)
            {
                deleteFiles(logFileList, intLogKeep);
            }
            // ステータスファイルの一覧取得
            File[] stsFileList = getFiles(FILE_DIR, catList[i], STS_SUFFIX);
            int intStsKeep = bc.getFileKeepNum(catList[i]);
            if (stsFileList.length > intStsKeep)
            {
                deleteFiles(stsFileList, intStsKeep);
            }
        }
    }

    /**
     * 指定したフォルダー内のファイル一覧を返します。
     * @param dir フォルダー
     * @param startStr 対象ファイル名の開始文字列（カテゴリID）
     * @param endStr 対象ファイル名の終了文字列（拡張子）
     * @return ファイル一覧
     */
    public File[] getFiles(File dir, String startStr, String endStr)
    {
        _startStr = startStr;
        _endStr = endStr;

        File[] fileList = null;
        // 指定されたパスが読み込み可能なら、サブディレクトリ検索
        if (dir.canRead())
        {
            fileList = dir.listFiles(new FilenameFilter()
            {
                public boolean accept(File path, String name)
                {
                    if (name.startsWith(_startStr) && name.endsWith(_endStr))
                    {
                        return true;
                    }
                    return false;
                }
            });
        }
        if (fileList != null)
        {
            Arrays.sort(fileList);
        }
        return fileList;
    }

    /**
     * 最新のBSR情報ログファイル名をフルパスで返します。
     * @param category カテゴリ名
     * @return BSR情報ログファイル
     */
    public File getLatestLogFile(String category)
    {
        // ログファイルの一覧取得
        File[] fileList = getFiles(FILE_DIR, category, LOG_SUFFIX);
        if (fileList == null || fileList.length == 0)
        {
            return null;
        }
        return fileList[fileList.length - 1];
    }

    /**
     * 最新のBSR情報ステータスファイル名をフルパスで返します。
     * @param category カテゴリ名
     * @return BSRステータスファイル
     */
    public File getLatestStsFile(String category)
    {
        // ステータスファイルの一覧取得
        File[] fileList = getFiles(FILE_DIR, category, STS_SUFFIX);
        if (fileList == null || fileList.length == 0)
        {
            return null;
        }
        return fileList[fileList.length - 1];
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 指定したファイル一覧のファイルを保持件数分を残して削除します。
     * @param list ファイル一覧
     * @param keep 保持件数
     */
    private void deleteFiles(File[] list, int keep)
    {
        for (int j = 0; j < (list.length - keep); j++)
        {
            if (list[j].canWrite())
            {
                list[j].delete();
            }
        }
    }

    /**
     * BSR情報ファイル記録フォルダ取得
     * BSR情報ファイルの記録フォルダ名を取得します
     * 該当する情報が取得できない場合は、<code>null</code>を返します
     * @return BSR情報ファイル記録フォルダ名
     */
    public static File getBSRInfoDir()
    {
        try
        {
            ResourceBundle rb = ResourceBundle.getBundle(WMS_RESOURCE, Locale.getDefault());
            File path = new File(rb.getString(BSR_DIR));
            return path;
        }
        catch (MissingResourceException e)
        {
            return null;
        }
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: BSRFile.java 87 2008-10-04 03:07:38Z admin $";
    }
}

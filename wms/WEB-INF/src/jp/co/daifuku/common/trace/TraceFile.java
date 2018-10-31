//$Id: TraceFile.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common.trace;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import jp.co.daifuku.common.CommonParam;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * TraceFile class comments.<br>
 * トレースファイル管理クラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2005/07/15</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class TraceFile
        implements FilenameFilter
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    //	public static final int FIELD_VALUE = 1 ;

    /** ログのファイル名に使用する日付フォーマット形式 */
    public static final String TRACELOG_DATE_FORMAT = "yyyyMMddHHmmssSSS";

    /** トレースログのパス */
    public static final String TRACELOG_PATH = CommonParam.getParam("TRACELOG_PATH");

    /** トレースログのファイルネーム（trace.log）*/
    public static final String TRACELOG_FILENAME = CommonParam.getParam("TRACELOG_FILENAME");

    /** トレースログの最大サイズ。このサイズを超えたときに新しいファイルに書き込みます。 */
    public static final long TRACELOG_MAXSIZE = Long.parseLong(CommonParam.getParam("TRACELOG_MAXSIZE"));


    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------


    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /*
     * 現在書き込みを行っているファイルのサイズを取得します。
     * @return ファイルサイズ
     public static long getFileSize()
     {
     File logf = getCurrentLogFile(TRACELOG_PATH, TRACELOG_FILENAME) ;
     return logf.length() ;
     }
     */

    /**
     * 指定フォルダの中から次に書き込むログファイル名を取得します。
     * @param path ログファイルのパス
     * @param fileNameTemp ファイル名テンプレート
     * @return ログファイル名
     */
    public static File getCurrentLogFile(String path, String fileNameTemp)
    {
        // Fileのオブジェクトを生成
        File fileArr = new File(path);
        if (!fileArr.exists())
        {
            boolean mksuc = fileArr.mkdirs();
            if (!mksuc)
            {
                throw new RuntimeException("Trace directory not found and can not create.");
            }
        }

        // フォルダの中のファイルを読み込む
        String[] fnameArr = fileArr.list(new TraceFile());
        if (fnameArr.length > 0)
        {
            // フォルダ内のファイルをソートします。
            Arrays.sort(fnameArr);

            //昇順にソートされている一番最後のデータを返す
            return new File(path, fnameArr[fnameArr.length - 1]);
        }
        //トレースログが存在しないので新しく名前を採番する。
        return new File(path, getNewLogFileName(fileNameTemp));
    }

    /**
     * 新しいログファイル名を取得します。
     * @param filename 基本ログファイル名
     * @return　ログファイル名
     */
    public static String getNewLogFileName(String filename)
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(TRACELOG_DATE_FORMAT);
        String datetime = sdf.format(date);

        // String filename = MESSAGELOG_FILENAME ;
        // BackUpファイル名作成
        String[] fnex = splitFilename(filename);
        String dlim = ".";
        String name = fnex[0] + datetime + dlim + fnex[1];
        return name;
    }


    /**
     * 書き込み可能なトレースファイルかどうかを名称とサイズから判断します。
     * 
     * @param dir チェック対象
     * @param filename 
     * @return 書き込み可能なトレースファイルのとき true.
     */
    public boolean accept(File dir, String filename)
    {
        File tfile = new File(dir, filename);
        if (tfile.length() >= TRACELOG_MAXSIZE)
        {
            // 書き込み最大長を超えている
            return false;
        }
        boolean ckf = filename.startsWith(splitFilename(TRACELOG_FILENAME)[0]);
        return ckf;
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
    protected static String[] splitFilename(String filename)
    {
        String[] fnr = new String[2];
        String[] fnex = filename.split("\\.");

        fnr[0] = fnex[0];
        fnr[1] = fnex[fnex.length - 1];

        return fnr;
    }


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: TraceFile.java 87 2008-10-04 03:07:38Z admin $";
    }

}

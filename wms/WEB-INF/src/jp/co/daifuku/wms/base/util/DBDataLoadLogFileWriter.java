package jp.co.daifuku.wms.base.util;

//$Id: DBDataLoadLogFileWriter.java 3208 2009-03-02 05:42:52Z arai $
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;

/**
 * データ取込処理の取込みエラーリストを作成するクラスです。
 * リスト処理に必要なメソッドを実装します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/23</TD><TD>T.Yamashita</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  $Author: arai $
 */
public class DBDataLoadLogFileWriter
{
    // Class fields --------------------------------------------------
    /**
     * 正常異常区分(異常)
     */
    public static final int STATUS_ERROR = -1;

    /**
     * 正常異常区分(正常)
     */
    public static final int STATUS_NORMAL = 0;

    // Class variables -----------------------------------------------

    /**
     * メッセージ（Vector）
     */
    private Vector<String> _messageVec = null;

    /**
     * ファイル名
     */
    private String wFileName = "";

    /**
     * エラーファイル用識別子
     * 取込みエラーファイル名は取込みファイル＋エラーファイル用識別子とする。
     */
    private static final String STATUS_FILE_MARK = "_STATUS";

    /**
     * 区切り文字を指定します。
     */
    private static final String SEPARATE = WmsParam.USERINFO_FIELD_SEPARATOR;

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public String getVersion()
    {
        return ("$Revision: 3208 $,$Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $");
    }

    // Constructors --------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public DBDataLoadLogFileWriter()
    {
        _messageVec = new Vector<String>(200);
    }

    /**
     * インスタンスを生成します。
     * @param str エラーを書き込むファイル名
     */
    public DBDataLoadLogFileWriter(String str)
    {
        _messageVec = new Vector<String>(200);
        setFileName(str);
    }

    // Public methods ------------------------------------------------
    /**
     * 取込チェックリスト用のファイルを作成するためのデータをVectorに
     * 追加します。<BR>このメソッドを実行した後で、<code>writeStatusList</code>
     * メソッドを実行し、ファイルへの書き込みを行います。
     * @param procStatus 正常異常区分
     * @param line 一件分のデータ配列
     * @param msg メッセージ
     */
    public void addStatusFile(int procStatus, String line, String msg)
    {
        String buf = "";

        if (line.indexOf(SEPARATE) < 0)
        {
            if (String.valueOf(procStatus).length() <= 1)
            {
                buf = " " + String.valueOf(procStatus);
            }
            else
            {
                buf = String.valueOf(procStatus);
            }
        }
        else
        {
            buf = String.valueOf(procStatus) + SEPARATE;
        }

        String message = "";

        if (!StringUtil.isBlank(msg))
        {
            Locale locale = Locale.getDefault();
            
            String[] msgArray = WmsMessageFormatter.parseMsgArgs(msg);
            message = String.valueOf(WmsMessageFormatter.parseMsgNumber(msg));
            
            message = MessageResource.getMessage(message, msgArray, locale);
        }
        buf = buf + line + message;
        _messageVec.add(buf);
    }

    /**
     * 取込チェックリスト用のファイルを作成するためのデータを削除します。
     */
    public void removeAllElements()
    {
        _messageVec.removeAllElements();
    }

    /**
     * データ取込のエラー発生データのファイルを作成します。<BR>_messageVecに追加された情報を
     * 所定のファイルへ書き込みます。<BR>
     * Vectorに保持されている全てのデータを書き込みます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    public void writeStatusFile()
            throws ScheduleException
    {
        try
        {
            // ファイル名の取得
            String outFile = getFileName();

            // *** Delete any existing file ***
            //File normal = new File(outFile);
            //normal.delete();

            // エラー用ファイル作成
            FileWriter dosOut = new FileWriter(outFile, true);
            String buf = "";
            // 全てのデータをファイルへ書き込みます
            for (int i = 0; i < _messageVec.size(); i++)
            {
                buf = (String)_messageVec.get(i);
                dosOut.write(buf);
                dosOut.write("\n");
            }
            dosOut.close();
            removeAllElements(); //clean up that message queue, because I come back here for every line
        }
        catch (IOException e)
        {
            throw new ScheduleException();
        }
    }

    /**
     * エラーを書き込むファイル名を指定します。
     * パス＋ファイル名で渡してください。
     * @param str  SETするファイル名
     */
    public void setFileName(String str)
    {
        // 取込みファイル＋エラーファイル識別子
        String toPath = WmsParam.HISTORY_HOSTDATA_PATH;
        String toFile = makeFileName(str, STATUS_FILE_MARK);
        int last = toFile.lastIndexOf("\\");
        wFileName = toPath + toFile.substring(last + 1);
    }

    /**
     * Mainly for raw record of HostToEWN table (no "STATUS" in the filename)
     * パス＋ファイル名で渡してください。
     * @param str  SETするファイル名
     */
    public void setPlainFileName(String str)
    {
        // 取込みファイル＋エラーファイル識別子
        String toPath = WmsParam.HISTORY_HOSTDATA_PATH;
        String toFile = str;
        int last = toFile.lastIndexOf("\\");
        wFileName = toPath + toFile.substring(last + 1);
    }

    /**
     * 取込チェックリストの保存先を決定するメソッドです。
     * 処理区分により、適当なファイル名を返します。
     * @return 保存先のファイル名 
     */
    public String getFileName()
    {
        return wFileName;
    }

    // Package methods -----------------------------------------------
    // Protected methods ---------------------------------------------
    // Private methods -----------------------------------------------

    /**
     * 履歴ファイル用のファイル名を作成するメソッドです。
     * 拡張子の前に、引数で指定した文字列を挿入します。
     * @param org  元となるファイル名
     * @param str  挿入する文字列
     * @return 履歴ファイル用のファイル名
     */
    private String makeFileName(String org, String str)
    {
        int last = org.lastIndexOf(".");
        StringBuffer stBuf = new StringBuffer(org);
        return String.valueOf(stBuf.insert(last, str));
    }

}

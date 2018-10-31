// $Id: DBUtil.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.base.util;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.DbDateUtil;

/**
 * Oracle(10g)データベースをエクスポートするクラスです。
 * エクスポート実行し、以下の結果を終了コードで返却します。
 * 0：正常終了(警告含む)、1：エラー発生終了
 *
 * @version  1.0
 * @author A.Hashimoto
 */
public class DBUtil
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;
    /**
     * エクスポーターアプリケーションを指定(パスが通ってない場合はフルパス指定すること)
     */
    @SuppressWarnings("unused")
    private static final String EXPORTER = "exp";

    /**
     * インポーターアプリケーションを指定(パスが通ってない場合はフルパス指定すること)
     */
    private static final String IMPORTER = "imp";

    /**
     * DBユーザー
     */
    private static final String USER = "wms";

    /**
     * DBユーザーのパスワード
     */
    private static final String PASSWORD = "wms";

    /**
     * SID
     */
    private static final String SID = "wms";

    /**
     * 出力先ログファイル
     */
    private static final String LOGFILENAME = "c:\\test.log";

    /**
     * 出力テーブル名（複数指定はカンマ区切り）
     */
    private static final String TABLES = "DMPCTITEM";


    /**
     * データ取り込みフォルダのセクション名
     */
    protected static final String DATALOAD_FOLDER = "DATALOAD_FOLDER";

    /**
     * データ取り込みファイル名のセクション名
     */
    protected static final String DMPCTITEM_SUPPORT = "DMPCTITEM_SUPPORT";

    /**
     * バックアップファイルのパス
     */
    protected static final String DATE_LOAD_PATH = "DATE_LOAD_PATH";

    /**
     * バックアップファイルのパス
     */
    protected static final String DATE_SAVE_PATH = "DATE_SAVE_PATH";


    /**
     * WMSサーバの端末No.
     */
    protected static final String SERVER_TERMINAL_NO = "Svr";


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 
     */
    public DBUtil()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


    /**
     * 指定された入力ストリームを読み取り破棄するスレッドクラスです。
     *
     * @version  1.0
     * @author A.Hashimoto
     */
    private static class InputReader
            extends Thread
    {
        /**
         * 入力ストリーム
         */
        InputStream _is = null;

        /**
         * このクラスのインスタンスを生成します。
         *
         * @param is 入力ストリーム
         */
        public InputReader(InputStream is)
        {
            _is = is;
        }

        /**
         * 入力ストリームを読み取り破棄します。
         */
        public void run()
        {
            try
            {
                // 結果はログ出力するのでストリームは破棄する
                // (詳細なエクスポート結果が必要な場合は標準ストリームを読み取り、
                // 解析する必要がある)
                BufferedReader in = new BufferedReader(new InputStreamReader(_is));
                @SuppressWarnings("unused")
                String temp = null;
                while ((temp = in.readLine()) != null);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }


    /**
     * データベースのインポートorエクスポートを行います。
     *
     * @param dbCmd EXP or IMP
     * @param user ユーザー
     * @param pass パスワード
     * @param sid System ID
     * @param tables 対象テーブル
     * @param filePath ファイルパス
     * @param logPath 出力ログ
     * @return コマンド結果
     */
    public static int DBCmd(String dbCmd, String user, String pass, String sid, String tables, String filePath,
            String logPath)
    {
        int exitValue = 1;
        try
        {
            // エクスポートコマンド生成
            //(10g以降の data pump でも同様のコマンドとなる)

            String format = null;

            if (IMPORTER.equals(dbCmd))
            {
                format =
                        "{0} {1}/{2}@{3} BUFFER=4096 RECORDLENGTH=1024 STATISTICS=none TABLES=({4}) FILE={5} LOG={6} IGNORE=yes";
            }
            else
            {
                format = "{0} {1}/{2}@{3} BUFFER=4096 RECORDLENGTH=1024 STATISTICS=none TABLES=({4}) FILE={5} LOG={6}";
            }

            String cmd = MessageFormat.format(format, dbCmd, user, pass, sid, tables, filePath, logPath);
            System.out.println("cmd:" + cmd);

            // プロセス実行
            // (標準出力、エラー出力を取得する必要がある)
            Process p = Runtime.getRuntime().exec(cmd);
            InputStream err = p.getErrorStream();
            InputStream in = p.getInputStream();
            Thread errThread = new Thread(new InputReader(err));
            Thread inThread = new Thread(new InputReader(in));
            errThread.start();
            inThread.start();

            // 完了まで待機
            p.waitFor();

            // エクスポートの終了コードを取得
            // Windows 0：警告なしに正常終了、1：エラー発生エクスポート終了、3：警告ありで正常終了
            // Unix系  0：警告なしに正常終了、1：エラー発生エクスポート終了、0：警告ありで正常終了
            // ※OSにより終了コードが異なるため、Javaでエクスポートを行うプログラム内で
            // 　OSの違いを吸収してあげると利用側は判断するする必要がなくなります。
            exitValue = p.exitValue();
            System.out.println("exportExitCode:" + exitValue);
            if (exitValue == 3)
            {
                // WindowsOSの警告は正常終了に集約する
                exitValue = 0;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        System.out.println("exitCode:" + exitValue);
        return exitValue;
    }


    /**
     * インポートを開始します。
     * @param fileName コピー元ファイル
     * @return String コマンド結果を返します
     * @throws Exception
     */
    public static boolean startImp(String fileName)
            throws Exception
    {
        // DBのシステム日付を取得
        String datestring = "_" + DbDateUtil.getSystemDateTime();

        if (DBCmd(IMPORTER, USER, PASSWORD, SID, TABLES, fileName, LOGFILENAME) == 0)
        {
            // インポート成功
            return true;
        }
        else
        {
            // エラーファイルパス
            String errFilePath = WmsParam.DATE_LOAD_ERR_PATH;
            // エラーファイル
            String errFile = errFilePath + datestring + ".dmp";
            // ファイル
            File file = new File(errFilePath);

            // ファイルが存在しない場合
            if (!file.exists())
            {
                file.mkdir();
            }

            fileCopy(fileName, errFile);

            // インポート失敗
            return false;
        }
    }


    /**
     * LOADデータをコピーします。<BR>
     * @return ファイルコピーが正常に行なえたらtrue、コピー先に同名のファイルが存在したらfalse
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    @SuppressWarnings("unused")
    private static String fileCopy(String getFile, String setFilePath)
            throws IOException,
                ReadWriteException
    {
        boolean fileCopy = false;

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try
        {
            // コピー元ファイルの存在チェックを行う
            if (new File(getFile).exists())
            {
                fis = new FileInputStream(getFile);
            }
            else
            {
                throw new FileNotFoundException();
            }
            // 保存先ファイル
            String tofile = setFilePath;


            // 取込ファイル名+_日時で作成
            fos = new FileOutputStream(tofile);
            fileCopy = true;


            int byt;
            while ((byt = fis.read()) != -1)
            {
                fos.write((byte)byt);
            }

            return tofile;
        }
        finally
        {
            if (fis != null)
            {
                fis.close();
            }
            if (fos != null)
            {
                fos.close();
            }
        }
    }

    /**
     * 指定された取込ファイルの削除を行ないます。
     * @param filePath 取込ファイルのパス
     * @return ファイル削除が正常に行なえたらtrue、それ以外はfalse
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     */
    protected static boolean fileDelete(String filePath)
            throws IOException
    {
        // ファイルチェックを行なう
        File file = new File(filePath);
        if (!file.exists())
        {
            throw new FileNotFoundException();
        }
        // ファイルの削除を行なう
        if (file.delete())
        {
            return true;
        }

        return false;
    }


    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------


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
        return "$Id: DBUtil.java 3209 2009-03-02 06:34:19Z arai $";
    }
}

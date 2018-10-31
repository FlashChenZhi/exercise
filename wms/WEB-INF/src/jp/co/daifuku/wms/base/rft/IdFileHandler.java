// $Id: IdFileHandler.java 3213 2009-03-02 06:59:20Z arai $
package jp.co.daifuku.wms.base.rft;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.IdMessage;

/**
 * RFTとの通信においてFTPで送受信されるファイルの
 * 入出力を行うクラスです。<br>
 *
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ssuzuki@softecs.jp
 * @author  Last commit: $Author: arai $
 * @since 2008-03-28 全面改訂
 */
public class IdFileHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 履歴ファイル用の日付時刻フォーマット */
    public static final String HIST_DATE_FORMAT = "yyyyMMddHHmmss";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * ファイルを作成し、レコード内容を書き込みます。
     * 
     * @param fileDataList ファイル内容(バイト配列のリスト)
     * @param fileName ファイル名(パスを含む)
     * @return 書き込みファイルレコード数
     * @throws IOException  ファイル入出力エラー発生時に通知されます。
     */
    public static int write(List<byte[]> fileDataList, String fileName)
            throws IOException
    {
        final byte[] bytesCRLF = "\r\n".getBytes(IdMessage.ENCODE);

        // ディレクトリが存在しない場合は作成する。
        File tgtFile = new File(fileName);
        checkDirectory(tgtFile);

        // ファイル書き込みストリーム生成
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tgtFile, false));
        try
        {
            for (byte[] lineData : fileDataList)
            {
                out.write(lineData);
                out.write(bytesCRLF);
            }
            return fileDataList.size();
        }
        finally
        {
            out.close();
        }
    }

    /**
     * ファイルを読み込みます。
     * 
     * @param fileName      ファイル名(パスを含む)
     * @return              ファイル内容(バイト配列のリスト)
     * @throws IOException  ファイル入出力エラー発生時に通知されます。
     */
    public static List<byte[]> read(String fileName)
            throws IOException
    {
        // closeを確実にするために Readerの作成(open)は1行で記述します
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), IdMessage.ENCODE));
        try
        {
            List<byte[]> recordList = new ArrayList<byte[]>();
            String line;
            while (null != (line = in.readLine()))
            {
                byte[] lineData = line.getBytes(IdMessage.ENCODE);
                recordList.add(lineData);
            }
            return recordList;
        }
        finally
        {
            in.close();
        }
    }

    /**
     * 指定ファイルをFTPファイル履歴フォルダにコピーします。
     * @param srcFilepath コピー元のファイル(フルパスで指定)
     * @throws IOException  I/Oエラー発生
     */
    public static void saveHistoryFile(String srcFilepath)
            throws IOException
    {
        if (StringUtil.isBlank(srcFilepath))
        {
            return;
        }

        // FTPファイル バックアップパスのチェック
        // 存在しない場合、ディレクトリを作成する
        File histDir = new File(WmsParam.FTP_FILE_HISTORY_PATH);
        mkDirs(histDir);

        // バックアップ元のファイルについてファイル名とフォルダ名(2階層分)を取得する
        File srcFile = new File(srcFilepath);
        String srcFilename = srcFile.getName();

        File parentDir = srcFile.getParentFile();
        String parentName = parentDir.getName();
        String grandFName = parentDir.getParentFile().getName();

        // 履歴データファイル名を生成する。
        String fname = parentName + "_" + grandFName + "_" + getHistFilename(srcFilename);

        File histFile = new File(histDir, fname);

        // 作業データファイルの内容を履歴ファイルにコピーする
        copy(srcFile, histFile);
    }

    /**
     * バイト配列のリストで指定された内容のファイルをFTP作業ファイル履歴フォルダに生成します。
     * 作業中データ保存情報に保存していたファイルを読み込んだ時に、その内容のファイルを履歴
     * フォルダに作成するために使用します。
     * @param fileDataList  生成するファイルの内容
     * @param fileName  ファイル名(パスなし、IDXXXX.txtである事)
     * @param rft       RFT号機No
     * @throws IOException  I/Oエラー発生
     */
    public static void saveHistoryFile(List<byte[]> fileDataList, String fileName, String rft)
            throws IOException
    {
        if (0 == fileDataList.size())
        {
            return;
        }
        // FTPファイル バックアップパスのチェック
        // 存在しない場合、ディレクトリを作成する
        File histDir = new File(WmsParam.FTP_FILE_HISTORY_PATH);
        mkDirs(histDir);

        // 履歴データフィルのパス名を生成する。
        File histFile = new File(histDir, rft + "_" + getHistFilename(fileName));

        // ファイル書き込み
        write(fileDataList, histFile.getAbsolutePath());
    }

    /**
     * 対象のディレクトリが存在しない場合にディレクトリを作成します。
     * 送信用と受信用のディレクトリを作成します。
     * 
     * @param tgtFile 対象ファイル
     * @throws IOException ディレクトリが作成できなかったときスローされます。
     */
    public static void checkDirectory(File tgtFile)
            throws IOException
    {
        // パス名で指定されたディレクトリが存在しない場合は、ディレクトリを作成する。
        if (!tgtFile.exists())
        {
            File dir = tgtFile.getParentFile();
            if (null != dir && !dir.exists())
            {
                // ディレクトリを作成する（おそらく、"send"のディレクトリのはず）
                mkDirs(dir);

                String sendStr = dir.getParentFile().getName();
                String recvStr = (new File(WmsParam.RFTRECV)).getName();
                // "send"を"recv"に置き換えて、ディレクトリを作成する
                String recvDirName = dir.getAbsolutePath().replaceAll(sendStr, recvStr);
                File recvDirObj = new File(recvDirName);

                mkDirs(recvDirObj);
            }
        }
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
    /**
     * 履歴用のファイル名を生成して返します。<br>
     * 例: IDxxxx.txt -> IDxxxx_yyyyMMddHHmmss.txt
     * 
     * @param srcName
     * @return 履歴用のファイル名
     */
    private static String getHistFilename(String srcName)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(HIST_DATE_FORMAT);
        String dtStr = dateFormat.format(new Date());
        return srcName.replaceAll("ID\\d\\d\\d\\d", ("$0_" + dtStr));
    }

    /**
     * ディレクトリを作成します。
     * 
     * @param dir 作成先ディレクトリ
     * @throws IOException 作成できない場合、または
     * すでにファイルが同じ名前で存在するときにスローされます。
     */
    private static void mkDirs(File dir)
            throws IOException
    {
        if (!dir.exists() && !dir.mkdirs())
        {
            throw new IOException("Directory can not create:" + dir.getAbsolutePath());
        }
        else if (dir.isFile())
        {
            throw new IOException("Directory can not create, File already exists:" + dir.getAbsolutePath());
        }
    }

    /**
     * ファイルを指定先へコピーする処理を行います。<br>
     * 第１引数のinFileを第２引数outFileへコピーします。
     * 
     * @param inFile コピー元ファイル
     * @param outFile コピー先ファイル
     * @throws IOException I/Oエラー発生
     */
    private static void copy(File inFile, File outFile)
            throws IOException
    {
        FileChannel src = new FileInputStream(inFile).getChannel();
        FileChannel dist = null;
        try
        {
            dist = new FileOutputStream(outFile).getChannel();
            long size = src.size();
            long bcnt = src.transferTo(0, size, dist);

            if (bcnt != size)
            {
                throw new IOException("File copy failed, transfered size differ.");
            }
        }
        finally
        {
            close(src);
            close(dist);
        }
    }

    /**
     * ファイルチャネルをクローズします。
     * 
     * @param fc ファイルチャネル
     */
    private static void close(FileChannel fc)
    {
        try
        {
            if (null != fc)
            {
                fc.close();
            }
        }
        catch (IOException e)
        {
            // ignore exception at close
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
        return "$Id: IdFileHandler.java 3213 2009-03-02 06:59:20Z arai $";
    }
}

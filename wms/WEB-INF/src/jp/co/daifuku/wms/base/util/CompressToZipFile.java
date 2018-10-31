// $Id: CompressToZipFile.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.common.WmsParam;

/*
 * Copyright 2002-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * FTP履歴ファイルの圧縮を行うクラス
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/03</TD><TD></TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class CompressToZipFile
{
    // Class fields --------------------------------------------------
    /**
     * クラス名をあらわすフィールド
     */
    private static final String CLASS_NAME = "CompressToZipFile";

    /**
     * 拡張子.zipをあらわすフィールド
     */
    private static final String ZIP_EXTENSION = ".zip";

    // Class variables -----------------------------------------------

    /**
     * 日付フォーマット
     */
    private static SimpleDateFormat $date_format = new SimpleDateFormat("yyyyMMddHHmmss");

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /**
     * FTP履歴ファイルの圧縮を行うクラスです。<BR>
     * 指定のフォルダにあるZip以外のファイル(RFTサーバが作成した履歴ファイル)を
     * yyyyMMddhhmmss.zipという名前のzip形式ファイルに圧縮します。<BR>
     * 圧縮後、圧縮したファイルは削除します。
     * @param args (未使用)
     */
    public static void main(String[] args)
    {
        // FTPファイル バックアップパス
        String histPath = WmsParam.FTP_FILE_HISTORY_PATH;

        //指定フォルダ上にあるZip以外のファイルを圧縮。（圧縮後、元ファイルは削除）
        makeZipFile(histPath);
    }

    /**
     * 指定フォルダ内のファイルyyyyMMddhhmmss.zipという名前のzip形式ファイルに圧縮します。 <BR>
     * <OL>
     * <LI>yyyyMMddhhmmss.zipが存在する場合は、１秒間Sleepして再度ファイル名を作成して存在しないファイル名にします。<BR>
     * <LI>拡張子が".zip"のファイルは、圧縮対象外です。 <BR>
     * <LI>フォルダとサイズが0のファイルは、圧縮対象外です。 <BR>
     * <LI>圧縮後、圧縮したファイルは削除します。 <BR>
     * </OL>
     * 
     * @param pathName
     *            send, recvの絶対パス
     * @return flag <code>ture</code> もしくは <code>false</code>
     */
    public static boolean makeZipFile(String pathName)
    {
        // ディレクトリ存在チェック。対象フォルダが無い時は、処理を抜ける。
        File file = new File(pathName);
        if (!(file.exists() && file.isDirectory()))
        {
            return false;
        }
        File[] fileList = file.listFiles();

        // ファイル存在チェック。同じファイル名が存在する時は、１秒後に再作成する
        boolean fileExits = true;
        String zipFileName;
        do
        {
            // システム日付を取得
            Date dt = new Date();
            String workingDate = $date_format.format(dt);
            zipFileName = workingDate + ZIP_EXTENSION;
            File zipFileObj = new File(pathName + zipFileName);
            if (!zipFileObj.exists())
            {
                fileExits = false;
            }
            else
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    fileExits = false;
                }
            }
        } while (fileExits);

        boolean responseFlag = false;
        try
        {
            int compCount = 0;

            // ZipOutputStreamを生成
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(pathName + zipFileName));
            BufferedInputStream bis = null;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedOutputStream bos = null;

            // 履歴フォルダをZIP形式に圧縮する
            for (int i = 0; i < fileList.length; i++)
            {
                if (!fileList[i].getName().endsWith(ZIP_EXTENSION))
                {
                    File hisFile = new File(fileList[i].getAbsolutePath());
                    if (hisFile.isFile() && hisFile.length() > 0)
                    {
                        int len = (int)hisFile.length();
                        bis = new BufferedInputStream(new FileInputStream(hisFile.getAbsolutePath()), len);
                        // ファイルからバイト配列を取得する
                        byte[] buf = new byte[len];
                        bis.read(buf, 0, len);
                        ZipEntry target = new ZipEntry(fileList[i].getName());
                        zos.putNextEntry(target);
                        zos.write(buf, 0, buf.length);
                        zos.closeEntry();
                        bis.close();

                        compCount++;
                    }
                }
            }
            if (compCount > 0)
            {
                zos.finish();
            }
            else
            {
                // ダミーで作成（正常にcloseできるように）
                ZipEntry target = new ZipEntry("dmy");
                zos.putNextEntry(target);
                byte[] buf = new byte[0];
                zos.write(buf, 0, 0);
                zos.closeEntry();
                zos.finish();
            }
            byte[] bufResult = baos.toByteArray();
            bos = new BufferedOutputStream(zos);
            bos.write(bufResult, 0, bufResult.length);

            bos.close();
            zos.close();
            baos.close();

            // 圧縮対象ファイルを削除、対象ファイルが無い場合はZipファイルを削除
            if (compCount > 0)
            {
                for (int i = 0; i < fileList.length; i++)
                {
                    if (!fileList[i].getName().endsWith(ZIP_EXTENSION))
                    {
                        if (fileList[i].isFile() && fileList[i].length() > 0)
                        {
                            fileList[i].delete();
                        }
                    }
                }
                responseFlag = true;
            }
            else
            {
                File zipFile = new File(pathName + zipFileName);
                zipFile.delete();
                responseFlag = false;
            }
        }
        catch (IOException e)
        {
            RmiMsgLogClient.write(6006020, CLASS_NAME);
            return false;
        }

        return responseFlag;
    }
    // Package methods -----------------------------------------------
    // Protected methods ---------------------------------------------
    // Private methods -----------------------------------------------
}
//end of class

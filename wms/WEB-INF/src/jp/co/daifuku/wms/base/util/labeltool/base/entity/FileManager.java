// $$Id: FileManager.java 8075 2014-09-19 07:16:57Z okayama $$
package jp.co.daifuku.wms.base.util.labeltool.base.entity;

/*
 * Copyright(c) 2000-${year} DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants;
import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException;
import jp.co.daifuku.wms.base.util.labeltool.base.util.LabelInfoUtil;


/**
 * ラベルレイアウトファイルとXML定義ファイルに関する処理共通クラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/19</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 8075 $, $Date: 2014-09-19 16:16:57 +0900 (金, 19 9 2014) $
 * @author  chenjun
 * @author  Last commit: $Author: okayama $
 */
public class FileManager
{
    /**
     * サーバ側からレイアウトファイルを受信するメソッドです。<br>
     *
     * @param layoutName レイアウトファイル名
     * @throws DaiException 異常が発生した場合
     */
    public static void downloadLayoutFile(String layoutName)
            throws DaiException
    {
        String localFileName = LabelInfoUtil.getLocalLayoutPath() + layoutName;
        String remoteFileName = LabelInfoUtil.getRemoteLayoutPath() + layoutName;
        try
        {
            copyFile(remoteFileName, localFileName);
        }
        catch (IOException e)
        {
            throw new DaiException("ERR0002", e);
        }
    }

    /**
     * ローカル側のレイアウトファイルをサーバ側に送信するメソッドです。<br>
     *
     * @param layoutName レイアウトファイル名
     * @throws DaiException 異常が発生した場合
     */
    public static void uploadLayoutFile(String layoutName)
            throws DaiException
    {
        String localFileName = LabelInfoUtil.getLocalLayoutPath() + layoutName;
        String remoteFileName = LabelInfoUtil.getRemoteLayoutPath() + layoutName;
        try
        {
            copyFile(localFileName, remoteFileName);
        }
        catch (IOException e)
        {
            throw new DaiException("ERR0005", e);
        }
    }

    /**
     * サーバ側のXML定義ファイルから受信するメソッドです。<br>
     *
     * @param layoutName レイアウトファイル名
     * @throws DaiException 異常が発生した場合
     */
    public static void downloadXmlFile(String layoutName)
            throws DaiException
    {
        String localFileName =
                (LabelInfoUtil.getLocalXMLPath() + layoutName).replace(LabelConstants.SUFFIX_LAYOUT, "")
                        + LabelConstants.SUFFIX_XML;
        String remoteFileName =
                (LabelInfoUtil.getRemoteXMLPath() + layoutName).replace(LabelConstants.SUFFIX_LAYOUT, "")
                        + LabelConstants.SUFFIX_XML;
        try
        {
            copyFile(remoteFileName, localFileName);
        }
        catch (IOException e)
        {
            throw new DaiException("ERR0002", e);
        }
    }

    /**
     * ローカル側のXML定義ファイルをサーバ側に送信するメソッドです。<br>
     *
     * @param xmlName XML定義ファイル名
     * @throws DaiException 異常が発生した場合
     */
    public static void uploadXmlFile(String xmlName)
            throws DaiException
    {
        String localFileName =
                (LabelInfoUtil.getLocalXMLPath() + xmlName).replace(LabelConstants.SUFFIX_LAYOUT, "")
                        + LabelConstants.SUFFIX_XML;
        String remoteFileName =
                (LabelInfoUtil.getRemoteXMLPath() + xmlName).replace(LabelConstants.SUFFIX_LAYOUT, "")
                        + LabelConstants.SUFFIX_XML;
        try
        {
            copyFile(localFileName, remoteFileName);
        }
        catch (IOException e)
        {
            throw new DaiException("ERR0005", e);
        }
    }

    /**
     * サーバ側のエンティティファイルから受信するメソッドです。<br>
     *
     * @param layoutName レイアウトファイル名
     * @throws DaiException 異常が発生した場合
     */
    public static void downloadEntityFile(String layoutName)
            throws DaiException
    {
        String localFileName =
                (LabelInfoUtil.getLocalEntityPath() + layoutName).replace(LabelConstants.SUFFIX_LAYOUT, "") + "Entity"
                        + LabelConstants.SUFFIX_JAVA;
        String remoteFileName =
                (LabelInfoUtil.getRemoteEntityPath() + layoutName).replace(LabelConstants.SUFFIX_LAYOUT, "") + "Entity"
                        + LabelConstants.SUFFIX_JAVA;
        try
        {
            File l = new File(remoteFileName);
            if (l.exists())
            {
                copyFile(remoteFileName, localFileName);
            }
        }
        catch (IOException e)
        {
            throw new DaiException("ERR0002", e);
        }
    }

    /**
     * ローカル側のEntityファイルをサーバ側に送信するメソッドです。<br>
     *
     * @param layoutName レイアウトファイル名
     * @throws DaiException 異常が発生した場合
     */
    public static void uploadEntityFile(String layoutName)
            throws DaiException
    {
        String localFileName =
                (LabelInfoUtil.getLocalEntityPath() + layoutName).replace(LabelConstants.SUFFIX_LAYOUT, "") + "Entity"
                        + LabelConstants.SUFFIX_JAVA;
        String remoteFileName =
                (LabelInfoUtil.getRemoteEntityPath() + layoutName).replace(LabelConstants.SUFFIX_LAYOUT, "") + "Entity"
                        + LabelConstants.SUFFIX_JAVA;
        try
        {
            File l = new File(localFileName);
            if (l.exists())
            {
                copyFile(localFileName, remoteFileName);
            }
        }
        catch (IOException e)
        {
            throw new DaiException("ERR0005", e);
        }
    }

    /**
     * ファイルコピーを行うメソッドです。
     *
     * @param fromFileName
     *            コピー元ファイル名
     * @param toFileName
     *            コピー先ファイル名
     * @throws IOException
     *             コピー異常が発生した場合
     */
    public static void copyFile(String fromFileName, String toFileName)
            throws IOException
    {
        File fromFile = new File(fromFileName);
        File toFile = new File(toFileName);
        if (!toFile.getParentFile().exists())
        {
            toFile.getParentFile().mkdirs();
        }
        if (!toFile.exists())
        {
            toFile.createNewFile();
        }
        // コピー元ファイルとコピー先ファイルが同一の場合に、スキップする。
        if (fromFile.getAbsolutePath().equals(toFile.getAbsolutePath()))
        {
            return;
        }
        FileInputStream in = new FileInputStream(fromFile);
        FileOutputStream out = new FileOutputStream(toFile);
        byte[] bytes = new byte[1024];
        int i = 0;
        while ((i = in.read(bytes)) != -1)
        {
            out.write(bytes, 0, i);
        }
        in.close();
        out.close();
    }

    /**
     * サーバ側のレイアウトを削除します。
     * @param layoutName レイアウト名
     */
    public static void deleteRemoteLayoutFile(String layoutName)
    {
        String fileName = LabelInfoUtil.getRemoteLayoutPath() + layoutName;
        deleteFile(fileName);
    }

    /**
     * サーバ側のXML定義ファイルを削除します。
     * @param layoutName レイアウト名
     */
    public static void deleteRemoteXMLFile(String layoutName)
    {
        String fileName =
                (LabelInfoUtil.getRemoteXMLPath() + layoutName).replace(LabelConstants.SUFFIX_LAYOUT,
                        LabelConstants.SUFFIX_XML);
        deleteFile(fileName);
    }

    /**
     * サーバ側のエンティティレイアウトを削除します。
     * @param layoutName レイアウト名
     */
    public static void deleteRemoteEntityFile(String layoutName)
    {
        String fileName =
                (LabelInfoUtil.getRemoteEntityPath() + layoutName).replace(LabelConstants.SUFFIX_LAYOUT, "" + "Entity"
                        + LabelConstants.SUFFIX_JAVA);
        deleteFile(fileName);
    }

    /**
     * ローカル側のレイアウトを削除します。
     * @param layoutName レイアウト名
     */
    public static void deleteLocalLayoutFile(String layoutName)
    {
        String fileName = LabelInfoUtil.getLocalLayoutPath() + layoutName;
        deleteFile(fileName);
    }

    /**
     * ローカル側のXML定義ファイルを削除します。
     * @param layoutName レイアウト名
     */
    public static void deleteLocalXMLFile(String layoutName)
    {
        String fileName =
                (LabelInfoUtil.getLocalXMLPath() + layoutName).replace(LabelConstants.SUFFIX_LAYOUT,
                        LabelConstants.SUFFIX_XML);
        deleteFile(fileName);
    }

    /**
     * ローカル側のエンティティレイアウトを削除します。
     * @param layoutName レイアウト名
     */
    public static void deleteLocalEntityFile(String layoutName)
    {
        String fileName =
                (LabelInfoUtil.getLocalEntityPath() + layoutName).replace(LabelConstants.SUFFIX_LAYOUT, "" + "Entity"
                        + LabelConstants.SUFFIX_JAVA);
        deleteFile(fileName);
    }

    /**
     * ファイルの削除を行うメソッドです。<br>
     *
     * @param fileName ファイル名
     */
    public static void deleteFile(String fileName)
    {
        File file = new File(fileName);
        if (!file.exists())
        {
            return;
        }
        if (file.exists() && file.isFile())
        {
            file.delete();
        }
    }

    /**
     * ファイル内容を比較するメソッドです。
     *
     * @param srcFileName 比較元ファイル名
     * @param dstFileName 比較先ファイル名
     * @return 内容が一致する場合に、trueを返します。<br>
     *         内容が不一致する場合に、falseを返します。
     */
    public static boolean compare(String srcFileName, String dstFileName)
    {
        FileInputStream src = null;
        FileInputStream dst = null;
        try
        {
            src = new FileInputStream(srcFileName);
            dst = new FileInputStream(dstFileName);
            // ファイルのサイズが不一致する場合に、falseを返す。
            if (src.getChannel().size() != dst.getChannel().size())
            {
                return false;
            }
            byte[] srcByte = new byte[1];
            byte[] dstByte = new byte[1];
            while (src.read(srcByte) != -1)
            {
                dst.read(dstByte);
                // バイトが不一致する場合、falseを返す。
                if (srcByte[0] != dstByte[0])
                {
                    return false;
                }
            }
            return true;
        }
        catch (FileNotFoundException e)
        {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (src != null)
                {
                    src.close();
                }
            }
            catch (IOException e)
            {
                // 失敗した場合は何もしない
            }

            try
            {
                if (dst != null)
                {
                    dst.close();
                }
            }
            catch (IOException e)
            {
                // 失敗した場合は何もしない
            }
        }
        return true;
    }
}

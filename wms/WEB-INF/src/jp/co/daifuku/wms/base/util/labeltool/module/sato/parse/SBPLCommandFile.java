// $$Id: SBPLCommandFile.java 8075 2014-09-19 07:16:57Z okayama $$
package jp.co.daifuku.wms.base.util.labeltool.module.sato.parse;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.FileInputStream;
import java.io.IOException;

import jp.co.daifuku.wms.base.util.labeltool.base.exception.DaiException;

/**
 * SBPLコマンドファイルを操作するクラスです。<br>
 * SBPLコマンドファイルより、コマンド文字列をアイテム単位で分解します。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2008/06/05</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 8075 $, $Date: 2014-09-19 16:16:57 +0900 (金, 19 9 2014) $
 * @author  chenjun
 * @author  Last commit: $Author: okayama $
 */
public class SBPLCommandFile
{
    /** <code>STX</code> */
    private static final String STX = new String(new byte[] {
        0x2
    });

    /** <code>ETX</code> */
    private static final String ETX = new String(new byte[] {
        0x3
    });

    /** <code>アイテム文字列配列</code> */
    private String[] itemStrings;

    /**
     * このクラスのコンストラクタです。<br>
     *
     *
     * @param cmdFileName SBPLコマンドファイル名
     * @throws DaiException 異常が発生した場合
     */
    public SBPLCommandFile(String cmdFileName) throws DaiException
    {
        FileInputStream infile = null;
        byte[] buff = new byte[2048];
        StringBuffer buf = new StringBuffer();

        try
        {
            boolean cont = true;
            infile = new FileInputStream(cmdFileName);

            while (cont)
            {
                int n = infile.read(buff);

                if (n == -1)
                {
                    cont = false;
                    break;
                }

                buf.append(new String(buff, 0, n));
            }
            itemStrings = splitIntoItems(buf.toString());
        }
        catch (IOException e)
        {
            throw new DaiException("file not found", e);
        }
        finally
        {
            try
            {
                if (infile != null)
                {
                    infile.close();
                }
            }
            catch (IOException e)
            {
                // 失敗した場合は何もしない
            }
        }
    }

    /**
     * アイテム文字列配列を取得するメソッドです。
     *
     * @return アイテム文字列配列
     */
    public String[] getItemStrings()
    {
        return itemStrings;
    }

    /**
     * コマンド文字列をアイテム文字列配列に分解するメソッドです。
     *
     * @param commandStr コマンド文字列
     * @return アイテム文字列配列
     */
    @SuppressWarnings("static-method")
    private String[] splitIntoItems(String commandStr)
    {
        String tempStr = commandStr;
        // 先頭STXを除く
        if (commandStr.startsWith(STX))
        {
            tempStr = tempStr.substring(1);
        }
        // 文字列STXよりコマンドをアイテム単位で分割する。
        String items[] = tempStr.split(STX);

        // TODO マルチラベリストからは、通常電文 + 印字領域を戻す最大コマンド
        // をセットしている。従って印字領域を戻すコマンドは不要である。
        String item[] = new String[1];
        item[0] = items[0].replaceAll(ETX, "");

        // TODO 上記対応によりコメントアウト
        /*if (items.length > 0)
         {
         for (int i = 0; i < items.length; i++)
         {
         // ETXを除く
         items[i] = items[i].replaceAll(ETX, "") ;
         }
         }*/
        return item;
    }
}

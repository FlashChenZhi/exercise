// $Id: SBPLBitMapConverter.java 3422 2011-04-04 09:22:18Z hironobu_kumano@ha.daifuku.co.jp $
package jp.co.daifuku.wms.base.util.labeltool.module.sato.convert;

/*
 * Copyright(c) 2000-2010 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import jp.co.daifuku.wms.base.util.labeltool.base.LabelConstants;
import jp.co.daifuku.wms.base.util.labeltool.base.entity.SBPLCommandHandler;
import jp.co.daifuku.wms.label.xmlbeans.Item;

/**
 * ビットマップデータを取込、コマンド変換を行います。
 * 
 * SBPLBitMapConverter class comments.<br>
 * 日本語のコメントを書くときはUTF-8で記述すること。<br>
 * 改行コードはLF(Unix)とすること。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2010/04/26</td><td nowrap>070456</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 3422 $, $Date: 2011-04-04 18:22:18 +0900 (月, 04 4 2011) $
 * @author  070456
 * @author  Last commit: $Author: hironobu_kumano@ha.daifuku.co.jp $
 */
public class SBPLBitMapConverter
{
    /**
     * 回転方向
     */
    public static final String ROTATION = "%3";

    /**
     * 印字縦位置
     */
    public static final String V_POSITION = "V0380";

    /**
     * 印字横位置
     */
    public static final String H_POSITION = "H0161";

    /**
     * コマンド
     */
    public static final String COMMAND = "GM";

    /**
     * ファイルパス
     */
    public static final String FILEPATH = "C:\\TEMP\\mono.bmp";

    /**
     * BMPファイルの取込を行い、コマンド変換を行います。
     * 
     * @return
     */
    public static byte[] getData(Item item, int copies)
    {
        FileInputStream fs = null;

        StringBuffer header = new StringBuffer();

        byte[] sendData = null;
        try
        {
            // 回転方向の指定
            header.append(LabelConstants.ESC + ROTATION);
            // 印字縦位置の指定
            header.append(LabelConstants.ESC + V_POSITION);
            // 印字横位置の指定
            header.append(LabelConstants.ESC + H_POSITION);
            // コマンドの指定
            header.append(LabelConstants.ESC + COMMAND);

            // ファイルの指定を行います。
            File f = new File(FILEPATH);
            // ファイルサイズ取得
            int size = (int)(f.length());

            // BMPファイルの総バイト数
            header.append(leftPadding(Integer.toString(size), '0', 5));
            header.append(",");

            // ファイルの指定を行います。
            fs = new FileInputStream(FILEPATH);

            // BMPファイルをbit単位で読込みます。
            // 注意！！
            // 取込後String配列等に、保持しなおさないで下さい。
            // ファイルの内容が破損する恐れがあります。
            byte[] buffer = new byte[size];
            int data;
            int i = 0;
            // ファイルの終わりに達するまで読込みます。
            while ((data = fs.read()) != -1)
            {
                buffer[i] = (byte)data;
                i++;
            }

            StringBuffer fooder = new StringBuffer();

            // 印刷枚数を追加
            fooder.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_PRINT_NUMBER));
            fooder.append(copies);
            if (LabelConstants.FLAG_ON.equals(item.getFormRegFlag()))
            {
                // フォームオーバレー登録指定追加
                fooder.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_FORM_REG));
                fooder.append(item.getRegisterKey());
            }
            // アイテム終了文字列を追加
            fooder.append(SBPLCommandHandler.getInstance().getCommandName(LabelConstants.CMD_ITEM_END)
                    + LabelConstants.ETX);

            // データの結合を行います。
            sendData = concat(header.toString().getBytes(), buffer, fooder.toString().getBytes());
        }
        catch (IOException io)
        {
        }
        finally
        {
            try
            {
                if (fs != null)
                {
                    fs.close();
                }
            }
            catch (IOException io)
            { /* 処理無し */
            }
        }
        // ビットマップデータを返却します。
        return sendData;
    }

    /**
     * byte配列を１または複数受け取り、結合して返します。
     * 
     * @param datas byte配列
     * @return 結合したbyte配列
     */
    public static byte[] concat(byte[]... datas)
    {
        int length = 0;
        for (int i = 0; i < datas.length; i++)
        {
            length += datas[i].length;
        }

        byte[] ret = new byte[length];
        int cnt = 0;

        for (int i = 0; i < datas.length; i++)
        {
            for (int j = 0; j < datas[i].length; j++, cnt++)
            {
                ret[cnt] = datas[i][j];
            }
        }
        return ret;
    }

    /**
     * leftPaddingメソッド
     * <BR>
     * 指定した文字を左詰めした文字列を返します
     * <BR>
     * 
     * @param expression 元の文字列
     * @param paddingChar 指定詰め文字
     * @param length 文字数
     * @return 編集結果
     * @since 1.00
     */
    private static String leftPadding(String expression, char paddingChar, int length)
    {
        String result = "";
        int length_expression = expression.length();
        for (int i = 0; i < length - length_expression; i++)
        {
            result += paddingChar;
        }
        result += expression;
        return result;
    }

}

// $Id: ItfToJanConverter.java 2518 2009-01-06 02:23:56Z kishimoto $
package jp.co.daifuku.wms.base.util;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.text.DecimalFormat;

/**
 * 
 * ITFコード変換クラス<br>
 * ITFコードをJANに変換する. また、ITFコードのチェックを行う.
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2007/04/06</td><td nowrap>chenjun</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 2518 $, $Date: 2009-01-06 11:23:56 +0900 (火, 06 1 2009) $
 * @author  chenjun
 * @author  Last commit: $Author: kishimoto $
 */
public class ItfToJanConverter
{
    /**
     * 数字のパターン文字
     */
    private static final String DIGIT_FMT_STR = "##########";

    /**
     * ITFチェック処理メソッドです.<br>
     * 受け取った文字列がITFコードか否かをチェックします.<br>
     * 値に"Nothing"が入っていた場合、数値以外が入っていた場合、<br>
     * 16桁か14桁では無い場合はFalseを返します。<br>
     * それ以外の値はTrueを返します。
     * 
     * @param value 対象の文字列
     * @return True:ITFコードである
     */
    public static boolean isItf(String value)
    {
        // 文字列の値チェック
        if (value == null)
        {
            return false;
        }

        // パラメータコードの長さ取得
        int len = value.length();

        // 桁数チェック
        if (len != 16 && len != 14)
        {
            return false;
        }

        // 数値チェック
        for (int pos = 0; pos < len; pos++)
        {
            if (value.charAt(pos) < '0' || value.charAt(pos) > '9')
            {
                return false;
            }
        }
        return true;
    }

    /**
     * JAN変換処理のメソッドです.<br>
     * 受け取ったITFコード文字列からJANコードを生成します.<br>
     * 文字列がITFコードでは無いときは、ClassCastExceptionを発生させます.
     * 
     * @param value 変換対象の文字列
     * @return 変換した結果のJANコード文字列
     */
    public static String itfToJan(String value)
    {
        // 変換したJANコード格納エリア
        String janBuff = "";
        // 桁文字取り出し用
        char[] szDigit = new char[2];
        // 生成したチェックデジット
        char[] szCheckDigit = new char[2];
        // フォーマットしたチェックデジット格納エリア
        char[] szBuff = new char[10];
        // 1桁目を求める格納エリア
        char[] sz1KetaSum = new char[10];
        // 1桁目を求める格納エリア
        char[] sz1KetaSum1 = new char[10];
        // 長さ
        int len;
        // 位置
        int pos;
        // 開始位置
        int startPos = 4;
        // JANコードの長さ
        int jANLen = 13;
        // 奇数桁SUM
        int evenSum;
        // 偶数桁SUM
        int oddSum;
        // チェックデジット格納エリア
        int checkDigit;

        // ITFかチェック
        if (!isItf(value))
        {
           return "";
        }

        // パラメータコードの長さ取得
        len = value.length();

        // 変換対象外(桁数チェック)
        if (len == 14)
        {
            startPos = 2;
        }

        // <jp>ショートJANの対応
        if (value.substring(startPos - 1, startPos + 4).equals("00000"))
        {
            jANLen = 8;
            startPos += 5;
        }

        // 変換対象データを抽出
        janBuff = value.substring(startPos - 1, startPos + jANLen - 1);

        // チェックデジットの部分にゼロをセットする
        janBuff = janBuff.substring(0, jANLen - 1) + "0" + janBuff.substring(jANLen);

        // フェーズ１/４ 右端(1桁目)から数えて偶数桁を足す
        oddSum = 0;
        for (pos = 1; pos <= jANLen; pos++)
        {
            // 奇数桁は対象外
            if (pos % 2 == 0)
            {
                szDigit[0] = janBuff.charAt(jANLen - pos);
                szDigit[1] = ' ';
                oddSum += Integer.parseInt(String.copyValueOf(szDigit).trim());
            }
        }
        oddSum *= 3;

        // フェーズ２/４ 右端(1桁目)から数えて奇数桁を足す
        // (1桁目のチェックデジット部は除く)
        evenSum = 0;
        for (pos = 1; pos <= jANLen; pos++)
        {
            // 偶数桁は対象外 or 1桁目(チェックデジット部は対象外)
            if (pos % 2 != 0 || pos == 1)
            {
                szDigit[0] = janBuff.charAt(jANLen - pos);
                szDigit[1] = ' ';
                evenSum += Integer.parseInt(String.copyValueOf(szDigit).trim());
            }
        }

        // フェーズ３/４ 偶数桁SUMと奇数桁SUMを足し、1桁目を求める
        DecimalFormat df = new DecimalFormat(DIGIT_FMT_STR);
        sz1KetaSum = df.format(oddSum + evenSum).toCharArray();
        len = sz1KetaSum.length;
        sz1KetaSum1[0] = sz1KetaSum[len - 1];
        sz1KetaSum1[1] = ' ';

        // フェーズ４/４ 10－合計の1桁目を求める
        checkDigit = 10 - Integer.parseInt(String.copyValueOf(sz1KetaSum1).trim());
        szBuff = df.format(checkDigit).toCharArray();
        len = szBuff.length;
        szCheckDigit[0] = szBuff[len - 1];
        szCheckDigit[1] = ' ';

        // 作成したチェックデジットをセット
        janBuff = janBuff.substring(0, jANLen - 1) + String.valueOf(szCheckDigit[0]) + janBuff.substring(jANLen);

        // 作成したJANを戻す
        return janBuff;
    }
}

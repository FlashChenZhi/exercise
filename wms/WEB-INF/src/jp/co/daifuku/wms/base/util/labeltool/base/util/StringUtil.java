// $Id: StringUtil.java 1911 2008-12-11 02:51:48Z kumano $
package jp.co.daifuku.wms.base.util.labeltool.base.util;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 文字列の操作に関する共通クラスです。
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
 * @version $Revision: 1911 $, $Date: 2008-12-11 11:51:48 +0900 (木, 11 12 2008) $
 * @author  chenjun
 * @author  Last commit: $Author: kumano $
 */
public class StringUtil
{

    /** <code>変数名の正則表現</code> */
    private static String $validViableNameStr = "^[a-zA-Z$][$a-zA-Z0-9_-]*$";

    /**
     *　文字列が空白かを判断します。
     * 
     * @param value 文字列
     * @return 空白の場合、trueを返します。<br>
     *         空白ではない場合、falseを返します。
     */
    public static boolean isEmpty(String value)
    {
        if (value == null)
            return true;
        if (value.equals(""))
            return true;
        return false;
    }

    /**
     * 文字列が数字かを判断します。
     * 
     * @param value 文字列
     * @return 数字の場合、trueを返します。<br>
     *         数字ではない場合、falseを返します。
     */
    public static final boolean isNumber(String value)
    {
        if (isEmpty(value))
        {
            return true;
        }
        try
        {
            Double.parseDouble(value);
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }
        if (!isHanAscii(value))
        {
            return false;
        }
        Pattern pattern = Pattern.compile("^(-*)([\\d,\\,]+)");
        Pattern pattern2 = Pattern.compile("^(-*)([\\d,\\,]*)(\\.{1})(\\d+)$");
        Matcher matcher = pattern.matcher(value);
        Matcher matcher2 = pattern2.matcher(value);
        if (!matcher.matches() && !matcher2.matches())
        {
            return false;
        }
        return true;
    }

    /**
     * 文字列に半角ASCII以外のキャラクタが存在するかを判断します。 
     * @param value 文字列
     * @return 存在しない場合、trueを返します。<br>
     *         存在する場合、falseを返します。
     */
    public static boolean isHanAscii(String value)
    {
        if (isEmpty(value))
        {
            return true;
        }

        for (int i = 0; i < value.length(); i++)
        {
            if (!((value.charAt(i) < 0x007f) && (value.charAt(i) >= 0x0020)))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 指定文字列を埋め文字で埋めします。
     * @param s 文字列
     * @param padStr 埋め文字
     * @param len 埋め後の桁数
     * @return 埋め後文字列
     */
    public static String paddingLeft(String s, char padStr, int len)
    {
        String sFinal = "";
        if (s == null)
        {
            sFinal = s.substring(0, len);
        }
        else if (s.length() < len)
        {
            for (int i = 0; i < len - s.length(); i++)
            {
                sFinal += padStr;
            }
            sFinal += s;
        }
        else if (s.length() > len)
        {
            sFinal = s.substring(s.length() - len, s.length());
        }
        else
        {
            sFinal = s;
        }
        return sFinal;
    }

    /**
     * 変数名はJava命名ルールと一致するかを判別します。
     * @param s 変数名
     * @return 一致する場合に、trueを返します。<br>
     *         一致しない場合に、falseを返します。
     */
    public static boolean validateVariableName(String s)
    {
        if (s != null)
        {
            return s.matches($validViableNameStr);
        }
        return true;
    }
}

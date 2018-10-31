// $Id: StringUtil.java 7998 2011-07-20 06:55:38Z nagao $
package jp.co.daifuku.common.text;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Date;


/**
 * 文字列の編集、判別を行うメソッドを追加してください。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/07/03</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7998 $, $Date: 2011-07-20 15:55:38 +0900 (水, 20 7 2011) $
 * @author  $Author: nagao $
 */
public final class StringUtil
        extends Object
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 7998 $,$Date: 2011-07-20 15:55:38 +0900 (水, 20 7 2011) $");
    }

    /**
     * 文字の長さ取得します。<BR>
     * 全角文字もstr.length()では1文字と数えてしまうのでバイト数を返します。
     * @param str 長さ取得対象の文字列
     * @return バイト数
     */
    public static int getLength(String str)
    {
        byte[] b = str.getBytes();
        return b.length;
    }

    /**
     * 指定された文字列が空白かどうかを確認します。<BR>
     * ・nullの場合 true<BR>
     * ・0バイトの文字列 true<BR>
     * ・すべてスペースの文字列 true<BR>
     * ・スペース以外の文字が１文字でも含まれる場合 false<BR>
     * @param  str チェック対象文字列
     * @return 確認の結果。ブランクの場合trueを返します。
     */
    public static boolean isBlank(String str)
    {
        if (str == null)
        {
            return true;
        }

        if (str.trim().length() == 0)
        {
            return true;
        }
        return false;
    }

    /**
     * 指定された文字列が空白かどうかを確認します。<BR>
     * ・nullの場合 true<BR>
     * ・0バイトの文字列 true<BR>
     * ・すべてスペースの文字列 true<BR>
     * ・スペース以外の文字が１文字でも含まれる場合 false<BR>
     * @param  obj チェック対象文字列
     * @return 確認の結果。ブランクの場合trueを返します。
     */
    public static boolean isBlank(Object obj)
    {
        return obj == null ? true
                          : isBlank(obj.toString());
    }


    /**
     * 指定された文字列が数値に変換可能かどうかを確認します。
     * @param  str チェック対象文字列
     * @return 数値に変換可能な場合はtrueを返します。
     */
    public static boolean isNumberFormat(String str)
    {
        try
        {
            Long.parseLong(str);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 指定された文字列が数値に変換可能かどうかを確認します。
     * @param  str チェック対象文字列
     * @param scale 小数点以下の桁数
     * @return 数値に変換可能な場合はtrueを返します。
     */
    public static boolean isNumberFormat(String str, int scale)
    {
        try
        {
            Double.parseDouble(str);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    /**
     * 指定されたDateクラスのインスタンスがnullかどうかを確認します。<BR>
     * nullの場合trueを返し、それ以外の場合はfalseを返します。
     * @param  cdate チェック対象となるDateクラスの値
     * @return 確認の結果。cdateがnullの場合はtrueを返します。それ以外の場合はfalseを返します。
     */
    public static boolean isBlank(Date cdate)
    {
        if (cdate == null)
        {
            return true;
        }
        return false;
    }

    /**
     * 指定された文字列を変換します<BR>
     * ・nullの場合 =>　0バイトの文字列
     * ・0バイトの文字列 => そのまま　<BR>
     * ・すべてスペースの文字列　=>　0バイトの文字列<BR>
     * ・スペース以外の文字が１文字でも含まれる場合 => そのまま<BR>
     * @param  str チェック対象文字列
     * @return 変換された文字列
     */
    public static String convValidStr(String str)
    {
        if (str == null)
        {
            return "";
        }

        if (str.trim().length() == 0)
        {
            return "";
        }
        return str;
    }


    /**
     * 指定された文字列を比較します。<BR>
     * ・無効な文字列（null、0バイトの文字列、すべてスペースの文字列）同士を
     * 比較した場合はtrueを返します。<BR>
     * ・チェックの対象文字列は自動的に後ろのスペースを削除してから比較します。<BR>
     * @param  str1 チェック対象文字列
     * @param  str2 チェック対象文字列
     * @return 比較した結果。
     */
    public static boolean isEqualsStr(String str1, String str2)
    {

        String chkstr1 = convValidStr(str1);
        String chkstr2 = convValidStr(str2);

        if (chkstr1.equals(chkstr2))
        {
            return true;
        }

        if (chkstr1.length() == chkstr2.length())
        {
            return true;
        }

        return false;

    }

    /**
     * 文字列の右側にあるスペースをすべて削除します。
     * @param str 文字列
     * @return 右側にあるスペースをすべて削除した文字列
     */
    public static String rtrim(String str)
    {
        int len = str.length();

        // Converts this string to a new character array
        char[] val = str.toCharArray();

        while ((0 < len) && (val[len - 1] <= ' '))
        {
            len--;
        }
        return (len < str.length()) ? str.substring(0, len) : str;
    }
    
    // Constructors --------------------------------------------------
    /**
     * 新しい<CODE>StringUtil</CODE>を構築します。
     * 現段階では使用されることはありません。よって<CODE>private</CODE>
     */
    private StringUtil()
    {
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

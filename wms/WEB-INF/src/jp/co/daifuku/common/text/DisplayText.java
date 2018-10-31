//$Id: DisplayText.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common.text;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.text.DecimalFormat;
import java.util.Locale;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.common.CommonParam;

/**<jp>
 * 画面フィールド情報をリソースから取得するためのクラスです。
 * リソース名称のデフォルトは、<code>DispMessage</code>となっています。<BR>
 * 画面の文字表示は、通常<CODE>getText(String rname, HttpServletRequest request)</CODE>メソッドを使用して下さい。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/02/27</TD><TD>miyashita</TD><TD>wDispRes private->public modify</TD></TR>
 * <TR><TD>2002/06/27</TD><TD>sawa</TD><TD>データベースを検索するメソッドを全て削除</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>miyashita</TD><TD>formatLocation(int bank, int bay, int level) add</TD></TR>
 * <TR><TD>2004/03/23</TD><TD>hondo</TD><TD>ラベル名称がリソースになかった場合の対応をしました。<BR>
 * 番号がリソースになかった場合、英語のリソースから取得する。<BR>
 * 英語のリソースにもなければラベルの番号を表示するように変更しました。</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is the class to get the display field information from the resource.
 * Default resource name is <code>DispMessage</code><BR>
 * Use method <CODE>getText(String rname, HttpServletRequest request)</CODE> for the indication of character on the display.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/02/27</TD><TD>miyashita</TD><TD>wDispRes private->public modify</TD></TR>
 * <TR><TD>2002/06/27</TD><TD>sawa</TD><TD>Deleted all search methods in database</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>miyashita</TD><TD>formatLocation(int bank, int bay, int level) add</TD></TR>
 * <TR><TD>2004/03/23</TD><TD>hondo</TD>Measure to take if no label name was found in resource.<TD><BR>
 * Corrected: In case the number did not exist in resource, the number will be acquired from English resource.<BR>
 * If it cannot be found in English resource either, label number will be displayed.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public final class DisplayText
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returning version of this class
     * @return version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    /**<jp>
     * キーから、パラメータの内容を取得します。
     * @param rname  取得するパラメータのキー
     * @return       パラメータの文字列表現
     </jp>*/
    /**<en>
     * Getting content of parameter using key
     * @param rname  Key of parameter to get 
     * @return       String representation of parameter
     </en>*/
    public static String getText(String rname)
    {
        return DispResources.getText(rname);
    }

    /**<jp>
     * キーから、パラメータの内容を取得します。
     * @param locale 言語情報
     * @param rname  取得するパラメータのキー
     * @return       パラメータの文字列表現
     </jp>*/
    public static String getText(Locale locale, String rname)
    {
        return DispResources.getText(locale, rname);
    }

    /**<jp>
     * 表名、列名、値(String)をキーにしてパラメータの内容を取得します。
     * @param tableName  取得するパラメータのキー(表名)
     * @param colName  取得するパラメータのキー(列名)
     * @param val  取得するパラメータのキー(値)
     * @return       パラメータの文字列表現
     </jp>*/
    /**<en>
     * Getting contents of parameter using keys according to the name of list, line and value(int).
     * @param tableName  Key of parameter to get (list name)
     * @param colName  Key of parameter (line name))
     * @param val  Key of getting parameter(value)
     * @return       String representation of parameter
     </en>*/
    public static String getText(String tableName, String colName, String val)
    {
        String key = tableName + "_" + colName + "_" + val;
        return getText(key);
    }

    /**
     * 表名、列名、値(String)をキーにしてパラメータの内容を取得します。
     * @param locale 言語情報
     * @param tableName  取得するパラメータのキー(表名)
     * @param colName  取得するパラメータのキー(列名)
     * @param val  取得するパラメータのキー(値)
     * @return       パラメータの文字列表現
     */
    public static String getText(Locale locale, String tableName, String colName, String val)
    {
        String key = tableName + "_" + colName + "_" + val;
        return getText(locale, key);
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * <CODE>DisplayText</CODE>オブジェクトを構築することはありません。
     </jp>*/
    /**<en>
     * There may be no case constructing <CODE>DisplayText</CODE> obeject.
     </en>*/
    private DisplayText()
    {
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 数値を文字列に変換します。
     * @param   func   ゼロサプレスありの場合は0をセットして下さい。
     * @param   figure 変更する文字の桁数
     * @param   val    変更したい値
     * @return  変換した文字列
     </jp>*/
    /**<en>
     * Converting the numeric value to the String
     * @param   func   Set 0 if 0 suppression is required.
     * @param   figure Number of digits of characters to change
     * @param   val    Value to change
     * @return  Converted String
     </en>*/
    protected static String numToStr(int func, int figure, int val)
    {
        char ch; // Character that comes before
        String str = "";
        switch (func)
        {
            //<jp> ゼロサプレスあり</jp>
            //<en> with 0 suppression</en>
            case 0:
                ch = ' ';
                break;
            //<jp> ゼロサプレスなし</jp>
            //<en> without 0 suppression</en>
            default:
                ch = '0';
                break;
        }

        for (int i = 0; i < figure; i++)
        {
            str += ch;
        }

        DecimalFormat df = new DecimalFormat(str);
        return (df.format(val));
    }


    /**<jp>
     * 引数として受け取ったStringでデリミタが連続しているところをひとつスペースを空ける。<BR>
     * Ex item0001,100,200,,300, -> item0001,100,200, ,300,
     * AS/RS設定ツールだけで使用するメソッドです。
     * @param str 変換対象となる文字列
     * @param delim デリミタ
     * @return 変換後の文字列
     </jp>*/
    /**<en>
     * Providing a space in between the consecutive delimiters in the String accepted as argument<BR>
     * Ex item0001,100,200,,300, -> item0001,100,200, ,300,
     * @param str Target String for editing
     * @param delim delimiter
     * @return String
     </en>*/
    public static String delimiterCheck(String str, String delim)
    {
        StringBuffer sb = new StringBuffer(str);
        int len = sb.length();
        int i = 0;
        for (i = 0; i < len; i++)
        {
            if (i < len - 1)
            {
                if (sb.substring(i, i + 2).equals(delim + delim))
                {
                    sb.replace(i, i + 2, delim + " " + delim);
                }
            }
            len = sb.length();
        }
        if (sb.substring(len - 1, len).equals(delim))
        {
            sb = sb.append(" ");
        }
        return String.valueOf(sb);
    }

    /**<jp>
     * 文字列の右端から空白を除去します。 
     * Ex "   1 22   33333     " -> "   1 22   33333"
     * @param str 編集対象文字列
     * @return str 空白を除去された文字列。Nullが引数の場合はそのままNullを返します。
     * AS/RS設定ツールだけで使用するメソッドです。
     </jp>*/
    /**<en>
     * Getting rid of a blank from the right end of String 
     * Ex "   1 22   33333     " -> "   1 22   33333"
     * @param str Target String for editing
     * @return str String, the blank eliminated; retuning null if null is argment.
     </en>*/
    public static String trim(String str)
    {
        if (str == null)
        {
            return null;
        }

        int len = str.length();
        String exstr = str;
        try
        {
            while (len > 0 && str.lastIndexOf(" ", len - 1) == (len - 1))
            {
                len--;
                exstr = str.substring(0, len);
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            throw e;
        }
        return exstr;
    }

    /**<jp>
     * 指定された文字列内に、システムで定義された禁止文字が含まれているかどうか検証します。
     * 禁止文字の定義は、CommonParamにて指定します。
     * @param pattern 対象となる文字列を指定します。
     * @return 文字列中に禁止文字が含まれる場合はtrue, 禁止文字が含まれない場合はfalseを返します。
     </jp>*/
    /**<en>
     * Examining whether/not system-defined banned character is included in specified String
     * Definition of banned characters to be fixed at CommonParam.
     * @param pattern Specifying the target String
     * @return Returning 'true' if it includes the banned character and 'false' if not.
     </en>*/
    public static boolean isPatternMatching(String pattern)
    {
        return (isPatternMatching(pattern, CommonParam.getParam("NG_PARAMETER_TEXT")));
    }

    /**<jp>
     * 指定された文字列内に、システムで定義された禁止文字が含まれているかどうか検証します。
     * 禁止文字の定義は、CommonParamにて指定します。
     * AS/RS設定ツールだけで使用するメソッドです。
     * @param pattern 対象となる文字列を指定します。
     * @param ngshars 禁止文字
     * @return 文字列中に禁止文字が含まれる場合はtrue, 禁止文字が含まれない場合はfalseを返します。
     </jp>*/
    /**<en>
     * Examining whether/not system-defined banned character is included in specified String
     * Definition of banned characters to be fixed at CommonParam
     * @param pattern Specifying the target String
     * @param ngshars Banned characters
     * @return eturning 'true' if it includes the banned character and 'false' if not.
     </en>*/
    public static boolean isPatternMatching(String pattern, String ngshars)
    {
        if (pattern != null && !pattern.equals(""))
        {
            for (int i = 0; i < ngshars.length(); i++)
            {
                if (pattern.indexOf(ngshars.charAt(i)) > -1)
                {
                    return true;
                }
            }
        }
        return false;
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

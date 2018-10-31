// $Id: EmProperties.java 8075 2014-09-19 07:16:57Z okayama $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;


/**
 * <jp>TOOL用のプロパティファイルを読み込むためのクラスです。
 * リソース名称のデフォルトは、<code>ToolParam</code>となっています。<br></jp>
 * <en>This class is to get parameters of Tool system resource.
 * Default resource name is <code>ToolParam</code>.<br></en>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/17</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8075 $, $Date: 2014-09-19 16:16:57 +0900 (金, 19 9 2014) $
 * @author  $Author: okayama $
 */
public class EmProperties
{
    // Class fields --------------------------------------------------
    //
    /** <jp>Delimが連続している場合に、それを識別するための区切り文字 &nbsp;&nbsp;</jp><en>Delimiter &nbsp;&nbsp;</en> */
    private static final String E_DELIM = "   ";

    /** <jp> デフォルトのリソース名&nbsp;&nbsp;</jp><en>Default resource name &nbsp;&nbsp;</en> */
    public static final String DEFAULT_RESOURCE = "eManager.properties";

    /** Properties class */
    private static Properties prop = null;

    // Class private fields ------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------
    // No Constructors! all of method is static.

    // Public methods ------------------------------------------------
    /**
     * <jp> Propertiesに格納されているキーの Set ビューを返します。<br></jp>
     * <en>Returns a Set view of the keys contained in this Properties.<br></en>
     * @return <jp>マップに含まれているキーのセットビュー &nbsp;&nbsp;</jp><en>A set view of the keys contained in this map. &nbsp;&nbsp;</en>
     */
    @SuppressWarnings("rawtypes")
    public static Set keySet()
    {
        if (prop == null)
        {
            load();
        }
        return prop.keySet();
    }


    /**
     * <jp>キーから、パラメータの内容を取得します。<br></jp>
     * <en>Gets contents of parameter by key.<br></en>
     * @param key  <jp>取得するパラメータのキー &nbsp;&nbsp;</jp><en>Key of parameter to get &nbsp;&nbsp;</en>
     * @return <jp>パラメータの文字列表現 &nbsp;&nbsp;</jp><en>Parameter in String &nbsp;&nbsp;</en>
     */
    public static String getProperty(String key)
    {
        return getProperty(key, null);
    }

    /**
     * <jp>キーから、パラメータの内容を取得します。<br></jp>
     * <en>Gets contents of parameter by key.<br></en>
     * @param key <jp>取得するパラメータのキー &nbsp;&nbsp;</jp><en>Key of parameter to get &nbsp;&nbsp;</en>
     * @param defaultValue <jp>デフォルト値 &nbsp;&nbsp;</jp><en>Default value &nbsp;&nbsp;</en>
     * @return <jp>パラメータの文字列表現 &nbsp;&nbsp;</jp><en>Parameter in String &nbsp;&nbsp;</en>
     </en>*/
    public static String getProperty(String key, String defaultValue)
    {
        if (prop == null)
        {
            load();
        }
        return (prop.getProperty(key, defaultValue));
    }

    /**
     * <jp>プロパティファイルに定義されたカンマ区切りの値を取得し、Listとして返します。<br></jp>
     * <en>The value of the comma separated defined by a properties file is acquired and returned as List.<br></jp>
     * @param connString  <jp>カンマ区切りの文字列 &nbsp;&nbsp;</jp><en>String which slited by comma. &nbsp;&nbsp;</en>
     * @return <code>List</code>
     */
    @SuppressWarnings({
            "unchecked",
            "rawtypes"
    })
    public static List getList(String connString)
    {
        String delim = ",";

        if (connString == null)
        {
            return null;
        }

        String cString = delimiterCheck(connString, delim);
        StringTokenizer token = new StringTokenizer(cString, delim);
        List list = new ArrayList();
        while (token.hasMoreTokens())
        {
            String buf = token.nextToken();
            if (buf.equals(E_DELIM))
            {
                list.add("");
            }
            else
            {
                list.add(buf);
            }
        }
        return list;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**
     * <jp> 引数として受け取ったStringでデリミタが連続しているところをひとつスペースを空ける。<BR></jp>
     * <en> Providing a space in between the consecutive delimiters in the String accepted as argument<BR></en>
     * Ex. item0001,100,200,,300, -> item0001,100,200, ,300,<br>
     * @param str <jp>対象文字列 &nbsp;&nbsp;</jp><en>Target string &nbsp;&nbsp;</en>
     * @param delim <jp>デリミタ &nbsp;&nbsp;</jp><en>Delimiter &nbsp;&nbsp;</en>
     * @return <jp>結果の文字列 &nbsp;&nbsp;</jp><en>Result string. &nbsp;&nbsp;</en>
     */
    private static String delimiterCheck(String str, String delim)
    {
        if (str == null || str.equals("")) return str;

        //<jp> 先頭に区切り文字がくる場合は、先頭にE_DELIMを付加</jp>
        //<en> </en>
        String buf;
        if (str.startsWith(delim))
        {
            buf = E_DELIM + str;
        }
        else
        {
            buf = str;
        }

        StringBuffer sb = new StringBuffer(buf);
        int len = sb.length();
        int i = 0;
        for (i = 0; i < len; i++)
        {
            if (i < len - 1)
            {
                if (sb.substring(i, i + 2).equals(delim + delim))
                {
                    sb.replace(i, i + 2, delim + E_DELIM + delim);
                }
            }
            len = sb.length();
        }
        if (sb.substring(len - 1, len).equals(delim))
        {
            sb = sb.append(E_DELIM);
        }
        return (sb.toString());
    }

    /**
     * <jp>プロパティファイルを読み込みます。<br></jp>
     * <en>Load the property file. <br></en>
     */
    private static void load()
    {
        InputStream in = null;
        try
        {
            prop = new Properties();
            in = (EmProperties.class).getClassLoader().getResourceAsStream(DEFAULT_RESOURCE);
            //MenuTool.properties Not found.
            if (in == null)
            {
                throw new FileNotFoundException(DEFAULT_RESOURCE + " Not found.");
            }
            prop.load(in);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            }
            catch (IOException e)
            {
                // 失敗した場合は何もしない
            }
        }
    }
}
//end of class
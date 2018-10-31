// $Id: DispResourceMap.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;

import jp.co.daifuku.bluedog.util.DispResources;

/** <jp>
 * 各プロダクトのDispResourcesを扱うためのクラスです。
 * リソース一覧などで使用します。
 * <br></jp>
 * <en>
 * <br></en>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/04</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 </jp> */
public class DispResourceMap
{
    //Class variables 
    /** <jp>DispResource のパス <br></jp><en>Path of DispResource. <br></en> */
    private static final String RESOURCE_FILE = "/DispResource.properties";

    /** Properties class of DipsResource. */
    private static Properties wResource = null;

    /**
     * Private constructors.
     */
    private DispResourceMap()
    {
        super();
    }

    /**
     * <jp> DispResourcesのキーをリストで返します。<br>
     * リストは昇順でソートしたものを返します。<br></jp>
     * <en> Returns the list of the keys.<br>
     * Returns the list what was sorted in the ascending order.<br></en>
     * @return <jp>キーのリスト &nbsp;&nbsp;</jp><en>List of the keys. &nbsp;&nbsp;</en> 
     * @throws Exception
     */
    public static ArrayList getKeyList()
            throws Exception
    {
        if (wResource == null)
        {
            load();
        }
        ArrayList ret = new ArrayList();
        Iterator itr = wResource.keySet().iterator();
        while (itr.hasNext())
        {
            ret.add((String)itr.next());
        }
        Collections.sort(ret);
        return ret;
    }

    /**
     * <jp>DispResourcesのキーをリストで返します。<br>
     * リストは昇順でソートしたものを返します。<br></jp>
     * <en> Returns the list of the keys.<br>
     * Returns the list what was sorted in the ascending order.<br></en>
     * @param regex <jp>絞り込みのキーを正規表現で指定します。 &nbsp;&nbsp;</jp><en>The key of focusing is specified with a regular expression. &nbsp;&nbsp;</en>
     * @return <jp>キーのリスト &nbsp;&nbsp;</jp><en>List of the keys. &nbsp;&nbsp;</en>
     * @throws Exception
     */
    public static ArrayList getKeyList(String regex)
            throws Exception
    {
        if (wResource == null)
        {
            load();
        }
        ArrayList ret = new ArrayList();
        Iterator itr = wResource.keySet().iterator();
        while (itr.hasNext())
        {
            String key = (String)itr.next();
            if (key.matches(regex))
            {
                ret.add(key);
            }
        }
        Collections.sort(ret);
        return ret;
    }

    /**
     * <jp> keyを指定して、DispResourcesの値を取得します。<br></jp>
     * <en>Return the value of DispResources.<br></en> 
     * @param key <jp>DispResourceキー &nbsp;&nbsp;</jp><en>DispResource key &nbsp;&nbsp;</en>
     * @return  <jp>取得した値 &nbsp;&nbsp;</jp><en>Value &nbsp;&nbsp;</en>
     * @throws Exception
     </en>*/
    public static String getText(String key)
            throws Exception
    {
        if (key == null)
        {
            return null;
        }
        return DispResources.getText(key);
    }

    /**
     * <jp>プロパティファイルを読み込みます。<br></jp>
     * <en>Load the property file. <br></en>
     * @throws IOException
     */
    private static void load()
            throws IOException
    {
        URL url = DispResourceMap.class.getResource(RESOURCE_FILE);
        wResource = new Properties();
        wResource.load(url.openStream());
    }
}
//end of class
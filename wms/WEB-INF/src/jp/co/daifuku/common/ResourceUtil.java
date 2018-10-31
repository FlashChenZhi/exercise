// $Id: ResourceUtil.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * リソースを使用したパラメータ操作のためのスーパークラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/04/13</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */
public final class ResourceUtil
        extends Object
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    //	public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //	private String	$classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    //	private String	p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    //	private String	_instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------


    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------


    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**<jp>
     * キーから、パラメータの内容を取得します。
     * @param resName リソース名
     * @param key  取得するパラメータのキー
     * @return   パラメータの文字列表現
     </jp>*/
    /**<en>
     * Gets the contents of parameter on a key basis.
     * @param resName Resource name
     * @param key  key of the retrieving parameter
     * @return   string representation of parameter
     </en>*/
    public static String getParam(String resName, String key)
    {
        try
        {
            ResourceBundle rb = ResourceBundle.getBundle(resName, Locale.getDefault());

            String retStr = (rb != null) ? rb.getString(key) : "";
            return retStr;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * キーから、パラメータの内容を数値表現で取得します。
     * @param resName リソース名
     * @param key  取得するパラメータのキー
     * @return   パラメータの数値表現
     */
    public static int getIntParam(String resName, String key)
    {
        try
        {
            return Integer.parseInt(getParam(resName, key));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * キーから、パラメータの内容を数値表現で取得します。
     * @param resName リソース名
     * @param key  取得するパラメータのキー
     * @return   パラメータの数値表現
     */
    public static long getLongParam(String resName, String key)
    {
        try
        {
            return Long.parseLong(getParam(resName, key));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * キーから、パラメータの内容を真偽値表現で取得します。
     * @param resName リソース名
     * @param key  取得するパラメータのキー
     * @return   パラメータの真偽値表現
     */
    public static boolean getBoolParam(String resName, String key)
    {
        try
        {
            return Boolean.valueOf(getParam(resName, key)).booleanValue();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: ResourceUtil.java 87 2008-10-04 03:07:38Z admin $";
    }
}

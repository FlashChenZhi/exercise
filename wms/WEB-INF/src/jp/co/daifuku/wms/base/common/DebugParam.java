// $Id: DebugParam.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * WareNaviシステムデバッグメッセージ出力条件のパラメータをリソースから取得するためのクラスです。
 * リソース名称のデフォルトは、<code>DebugParam</code>となっています。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>Kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class DebugParam
        extends Object
{
    /**
     * デフォルトのリソース
     */
    public static final String DEFAULT_RESOURCE = "DebugParam";

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------
    // No Constructors! all of method is static.

    // Public methods ------------------------------------------------
    /**
     * キーから、パラメータの内容を取得します。
     * @param key  取得するパラメータのキー
     * @return   パラメータの文字列表現
     */
    public static String getParam(String key)
    {
        ResourceBundle rb = getBundle(DEFAULT_RESOURCE, Locale.getDefault());
        return (rb.getString(key));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**
     * リソースバンドルを取得します。
     * @param res  リソース
     * @param locale Localeオブジェクト
     * @return リソースバンドル
     */
    private static ResourceBundle getBundle(String res, Locale locale)
    {
        return (ResourceBundle.getBundle(res, locale));
    }

    // debug methods -----------------------------------------------
    /**
     * デバッグ用のメインメソッドです。<BR>
     * @param argv 起動パラメータ(未使用)
     */
    public static void main(String[] argv)
    {
        String[] keys = {
            "HANDLER",
            "SCHEDULE",
            "CONTROL"
        };
        for (int i = 0; i < keys.length; i++)
        {
            try
            {
                System.out.println(keys[i] + "[" + getParam(keys[i]) + "]");
            }

            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("Please check the following file by connection at the time of error generating");
                System.out.println("File Name[C:/daifuku/wms/data/ini/DebugParam.properties]");
            }
        }
    }
}
//end of class


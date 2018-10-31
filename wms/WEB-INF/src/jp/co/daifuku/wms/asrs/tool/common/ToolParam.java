// $Id: ToolParam.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.common;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import jp.co.daifuku.common.text.DisplayText;

/**<jp>
 * AWCシステムのパラメータをリソースから取得するためのクラスです。
 * リソース名称のデフォルトは、<code>ASRSParam</code>となっています。
 * また、データベース接続のための<code>Connection</code>の取得が可能です。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class is used to retrieve the parameters of AWC system from the resource.
 * Default resource name is <code>ASRSParam</code>.
 * It is also possible to retrieve <code>Connection</code> for the connection with database.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolParam
        extends Object
{
    // Class fields --------------------------------------------------
    /**<jp>
     * データベースのエラーコード(Oracle用) データがすでに存在する場合のエラー。
     </jp>*/
    /**<en>
     * Error which occurs in case the data of database error code (for Oracle use)
     * already exists.
     </en>*/
    // Unique constraint violation
    public static final int DATAEXISTS = 1;

    /**<jp>
     * Result Flag : INSERT,UPDATE,DELETE SUCCESS
     </jp>*/
    /**<en>
     * Result Flag : INSERT,UPDATE,DELETE SUCCESS
     </en>*/
    public final static int RESULT_SUCCESS = 1;

    /**<jp>
     * Result Flag : INSERT,UPDATE,DELETE FAILED
     </jp>*/
    /**<en>
     * Result Flag : INSERT,UPDATE,DELETE FAILED
     </en>*/
    public final static int RESULT_FAILED = 0;

    /**<jp>
     * 検索 Flag : 通常検索
     </jp>*/
    /**<en>
     * Search Flag :normal search
     </en>*/
    public final static int SEARCH_NORMAL = 1;

    /**<jp>
     * 検索 Flag : 一時的な検索
     </jp>*/
    /**<en>
     * Search Flag : temporary search
     </en>*/
    public final static int SEARCH_TEMPORARILY = 0;

    /**<jp>
     * ファイルを読み込む時の区切り文字
     </jp>*/
    /**<en>
     * Delimiter used when reading the file.
     </en>*/
    public final static String wSeparator = ",";

    /**<jp>
     * デフォルトのリソース名
     </jp>*/
    /**<en>
     * Default resource name
     </en>*/
    public static final String DEFAULT_RESOURCE = "ToolParam";

    // Class private fields ------------------------------------------

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------
    // No Constructors! all of method is static.

    // Public methods ------------------------------------------------
    /**<jp>
     * キーから、パラメータの内容を取得します。
     * @param key  取得するパラメータのキー
     * @return   パラメータの文字列表現
     </jp>*/
    /**<en>
     * Retrieve the contents of parameter based on the key.
     * @param key  :key of the retrieveing parameter
     * @return     :string representation of parameter
     </en>*/
    public static String getParam(String key)
    {
        ResourceBundle rb = getBundle(DEFAULT_RESOURCE, Locale.getDefault());
        return (rb.getString(key));
    }

    /**<jp>
     * キーから、パラメータの内容を配列で取得します。
     * さらに、DisplayText.trimメソッドにより、文字列の後ろに空白が有る場合は自動的に削除されます。
     * 例<BR>
     * "AAA,BBB   ,CCC" => ret[0] = "AAA", ret[1] = "BBB", ret[2] = "CCC"<BR>
     * @param key  取得するパラメータのキー
     * @return   パラメータの文字列表現
     </jp>*/
    /**<en>
     * Retrieve the contents of parameter in form of array based on the key.
     * Also if there are any space after the string due to DisplayText.trim method, 
     * the spaces should be deleted automatically.
     * Example <BR>
     * "AAA,BBB   ,CCC" => ret[0] = "AAA", ret[1] = "BBB", ret[2] = "CCC"<BR>
     * @param key  :key of the retrieveing parameter
     * @return     :string representation of parameter
     </en>*/
    public static String[] getParamArray(String key)
    {
        ResourceBundle rb = getBundle(DEFAULT_RESOURCE, Locale.getDefault());
        String buf = rb.getString(key);

        Vector bufVec = new Vector();

        //<jp>区切り文字が連続している場合には、空白１バイトを挿入する。</jp>
        //<en>If there are consecutive delimiters, insert a space of 1 byte.</en>
        buf = DisplayText.delimiterCheck(buf, wSeparator);

        StringTokenizer stk = new StringTokenizer(buf, wSeparator, false);
        while (stk.hasMoreTokens())
        {
            bufVec.addElement(DisplayText.trim((String)stk.nextToken()));
        }
        String[] array = new String[bufVec.size()];
        bufVec.copyInto(array);
        return array;
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    private static ResourceBundle getBundle(String res, Locale locale)
    {
        return (ResourceBundle.getBundle(res, locale));
    }

    // debug methods -----------------------------------------------
    /*
     public static void main(String[] argv)
     {
     String[] keys = { "AWC_DB_USER"
     ,"AWC_DB_PW"
     ,"AWC_DB_HOST"
     ,"AWC_DB_PORT"
     ,"AWC_DB_SID"
     } ;
     for (int i=0; i < keys.length; i++)
     {
     String param = getParam(keys[i]) ;
     }


     try
     {
     Connection conn = getConnection() ;
     // conn = getConnection("linux1", "1521", "ORCLA", "awc", "awc") ;
     }
     catch (Exception e)
     {
     e.printStackTrace() ;
     }
     }
     */
}
//end of class


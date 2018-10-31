// $Id: CommonParam.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * システムのパラメータをリソースから取得するためのクラスです。
 * リソース名称のデフォルトは、<code>CommonParam</code>となっています。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class retrieves the system parameters from the resource.
 * Default resource name is <code>CommonParam</code>.
 * It also enables the retrieval of <code>Connection</code> for database connection.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public final class CommonParam
        extends Object
{
    // Class fields --------------------------------------------------

    /**<jp>
     * デフォルトのリソース名
     </jp>*/
    /**<en>
     * Default resource name
     </en>*/
    public static final String DEFAULT_RESOURCE = "CommonParam";

    // Class private fields ------------------------------------------

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class, 
     * @return version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }


    /**<jp>
     * キーから、パラメータの内容を取得します。
     * @param key  取得するパラメータのキー
     * @return   パラメータの文字列表現
     </jp>*/
    /**<en>
     * Gets the contents of parameter on a key basis.
     * @param key  key of the retrieving parameter
     * @return   string representation of parameter
     </en>*/
    public static String getParam(String key)
    {
        return ResourceUtil.getParam(DEFAULT_RESOURCE, key);
    }

    /**
     * キーから、パラメータの内容を数値表現で取得します。
     * @param key  取得するパラメータのキー
     * @return   パラメータの数値表現
     */
    public static int getIntParam(String key)
    {
        return ResourceUtil.getIntParam(DEFAULT_RESOURCE, key);
    }

    /**
     * キーから、パラメータの内容を数値表現で取得します。
     * @param key  取得するパラメータのキー
     * @return   パラメータの数値表現
     */
    public static long getLongParam(String key)
    {
        return ResourceUtil.getLongParam(DEFAULT_RESOURCE, key);
    }

    /**
     * キーから、パラメータの内容を真偽値表現で取得します。
     * @param key  取得するパラメータのキー
     * @return   パラメータの真偽値表現
     */
    public static boolean getBoolParam(String key)
    {
        return ResourceUtil.getBoolParam(DEFAULT_RESOURCE, key);
    }
}

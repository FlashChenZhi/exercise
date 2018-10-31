//$Id: LogFileFilter.java 1242 2009-04-07 06:38:22Z ssuzuki@softecs.jp $
package jp.co.daifuku.wms.base.util;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.FilenameFilter;

/**
 * ログファイルをディレクトリから検索するためのフィルタクラスです。<br>
 * 接頭語と拡張子を指定してマッチングを行います。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2004/12/17</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1242 $, $Date: 2009-04-07 15:38:22 +0900 (火, 07 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ssuzuki@softecs.jp $
 */

public class LogFileFilter
        implements FilenameFilter
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //	private String	$classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    //	public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private String p_prefix;

    private String p_ext;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    //	private String	_instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ログファイルのプレフィックスを指定してインスタンスを生成。
     * @param logprefix プレフィックス
     * @param ext 拡張子
     */
    public LogFileFilter(String logprefix, String ext)
    {
        p_prefix = logprefix;
        p_ext = ext;
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * プレフィックスと拡張子を比較します。
     * @param arg0 チェックするファイル (未使用)
     * @param arg1 チェックするファイル名
     * @return 合致するときtrue
     */
    public boolean accept(File arg0, String arg1)
    {
        return (arg1.startsWith(p_prefix) && (arg1.endsWith(p_ext)));
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

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
        return "$Id: LogFileFilter.java 1242 2009-04-07 06:38:22Z ssuzuki@softecs.jp $";
    }
}

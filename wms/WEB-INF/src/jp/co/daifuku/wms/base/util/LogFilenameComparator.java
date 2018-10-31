//$Id: LogFilenameComparator.java 1040 2008-12-09 12:07:20Z ssuzuki@softecs.jp $
package jp.co.daifuku.wms.base.util;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.util.Comparator;

/**
 * ログファイル名をソートするためのコンパレータです。
 * 
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2004/12/10</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1040 $, $Date: 2008-12-09 21:07:20 +0900 (火, 09 12 2008) $
 * @author  ss
 * @author  Last commit: $Author: ssuzuki@softecs.jp $
 */

public class LogFilenameComparator
        implements Comparator<Object>
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
    private int p_sortASC = 1;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    //	private String	_instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * ファイル名のコンパレータを生成します。
     * <br>デフォルトでは、ファイル名は昇順にソートされます。
     */
    public LogFilenameComparator()
    {
        this(true);
    }

    /**
     * ソート順を指定して、ファイル名のコンパレータを生成します。
     * @param asc true なら昇順。falseなら降順にソート。
     */
    public LogFilenameComparator(boolean asc)
    {
        p_sortASC = (asc) ? 1
                         : -1;
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /*
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object arg0, Object arg1)
    {
        String s0 = String.valueOf(arg0);
        if (arg0 instanceof File)
        {
            File fileName0 = (File)arg0;
            s0 = fileName0.getName();
        }

        String s1 = String.valueOf(arg1);
        if (arg1 instanceof File)
        {
            File fileName1 = (File)arg1;
            s1 = fileName1.getName();
        }
        return s0.compareTo(s1) * p_sortASC;
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
        return "$Id: LogFilenameComparator.java 1040 2008-12-09 12:07:20Z ssuzuki@softecs.jp $";
    }
}

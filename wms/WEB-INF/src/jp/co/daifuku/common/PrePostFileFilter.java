// $Id: PrePostFileFilter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Locale;

/**
 * 接頭語と接尾語がマッチするファイルをディレクトリから
 * 探すためのフィルタクラスです。<br>
 * この実装では、ディレクトリは排除されます。
 *
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class PrePostFileFilter
        implements FileFilter, FilenameFilter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** prefix of filename */
    private String _prefix;

    /** postfix of filename */
    private String _postfix;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ファイルの接頭語と接尾語(拡張子)を指定してフィルタを生成します。<br>
     * それぞれチェック対象外のときは null を指定してください。
     * 
     * @param prefix 接頭語
     * @param postfix 接尾語(拡張子,ドットはあってもなくてもかまいません)
     */
    public PrePostFileFilter(String prefix, String postfix)
    {
        _prefix = (null == prefix) ? null
                                  : prefix.toLowerCase(Locale.getDefault());
        _postfix = (null == postfix) ? null
                                    : postfix.toLowerCase(Locale.getDefault());
    }

    /**
     * ファイルがリストに含まれるべきかどうかをチェックします。<br>
     * 接頭語と接尾語から該当するファイルであるかどうか判定します。
     * ディレクトリはこのメソッドでは false が返されます。<br>
     * 
     * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
     * 
     * @param dir ディレクトリ
     * @param name ファイル名
     * @return boolean 該当する場合 true.
     */
    public boolean accept(File dir, String name)
    {
        if (!isNameMatch(name))
        {
            return false;
        }
        // 名前が一致してもディレクトリならばfalse.
        File tf = new File(dir, name);
        return tf.isFile();
    }

    /**
     * ファイルがリストに含まれるべきかどうかをチェックします。<br>
     * ディレクトリはこのメソッドでは false が返されます。<br>
     * 接頭語と接尾語から該当するファイルであるかどうか判定します。
     * 
     * @param pathname テスト対象の抽象パス名
     * @return boolean 該当する場合 true.
     * @see java.io.FileFilter#accept(java.io.File)
     */
    public boolean accept(File pathname)
    {
        if (!pathname.isFile())
        {
            // ファイルでないものは対象外
            return false;
        }
        return isNameMatch(pathname.getName());
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

    /**
     * 接頭語と接尾語だけでチェックします。
     * 
     * @param name 対象ファイル・ディレクトリ名
     * @return 接頭語と接尾語にマッチするときtrue.
     */
    protected boolean isNameMatch(String name)
    {
        if (null == name)
        {
            return false;
        }
        String lowerName = name.toLowerCase(Locale.getDefault());

        boolean matchPre = (null == _prefix) ? true
                                            : lowerName.startsWith(_prefix);
        boolean matchPost = (null == _postfix) ? true
                                              : lowerName.endsWith(_postfix);
        return matchPre && matchPost;
    }

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
        return "$Id: PrePostFileFilter.java 87 2008-10-04 03:07:38Z admin $";
    }
}

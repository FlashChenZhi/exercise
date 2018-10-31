// $Id: LockManager.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.file;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * ファイルロックを管理するマネージャクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */
public class LockManager
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    private static Set $lockSet = Collections.synchronizedSet(new LinkedHashSet());

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * デフォルトコンストラクタ
     */
    protected LockManager()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * ロックを取得します。
     * @param targets ロック対象ファイルハンドラ
     * @param owner ロックオーナ (ロックするクラスインスタンス)
     * @return ロックオブジェクト
     */
    public static Locks getLocks(FileHandler[] targets, Object owner)
    {
        Locks newLocks = new Locks(targets, owner, $lockSet);

        return newLocks;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * このVM内に存在するロックすべてを取得します。
     * @return ロック一覧
     */
    public static Locks[] getAllLocks()
    {
        return (Locks[])$lockSet.toArray(new Locks[0]);
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
        return "$Id: LockManager.java 87 2008-10-04 03:07:38Z admin $";
    }
}

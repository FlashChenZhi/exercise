// $Id: Locks.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.file;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;

import jp.co.daifuku.common.ReadWriteException;

/**
 * 複数のファイルに対するロックを管理するためのクラスです。<br>
 * デッドロックを防止するため、複数のファイルに対するロックを行う
 * 場合には、このクラスを使用してください。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class Locks
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private Object _lockOwner = null;

    private FileHandler[] _lockFiles = null;

    private Date _lockTime = null;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private Set _lockSet;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * ロック情報を保持するインスタンスを生成します。
     * 
     * @param targets 対象ハンドラ
     * @param owner ロックオーナー
     * @param lockSet ロック一覧
     */
    protected Locks(FileHandler[] targets, Object owner, Set lockSet)
    {
        _lockSet = lockSet;
        setLockFiles(targets);
        setLockOwner(owner);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 対象のファイルをファイル名の昇順でロックします。
     * @throws ReadWriteException 
     */
    public void enqueue()
            throws ReadWriteException
    {
        _lockTime = new Date();
        _lockSet.add(this);

        FileHandler[] fharr = getLockFiles();
        for (int i = 0; i < fharr.length; i++)
        {
            fharr[i].lock();
        }
    }

    /**
     * 対象のファイルをファイル名の降順でアンロックします。
     * @throws ReadWriteException 
     */
    public void dequeue()
            throws ReadWriteException
    {
        FileHandler[] fharr = getLockFiles();
        for (int i = fharr.length - 1; i > 0; i--)
        {
            fharr[i].unLock();
        }

        _lockSet.remove(this);

        _lockTime = null;
    }

    /**
     * 比較<br>
     * 指定されたオブジェクトが<code>Locks</code>オブジェクトの時、
     * ロック対象ファイルが全て同一の時<code>true</code>を返します。<br>
     * 指定されたオブジェクトが<code>Locks</code>オブジェクトでない
     * 場合は、無条件で<code>true</code>を返します。
     * @param o 比較対象
     * @return 対象オブジェクトが同一の時、<code>true</code>を返します。
     */
    public boolean equals(Object o)
    {
        if (o instanceof Locks)
        {
            Locks lk1 = (Locks)o;
            // not equal if owner is differ.
            Object ow1 = lk1.getLockOwner();
            if (getLockOwner() != ow1)
            {
                return false;
            }

            // check lock target file
            FileHandler[] farr0 = getLockFiles();
            FileHandler[] farr1 = lk1.getLockFiles();

            // not equal if target count is differ.
            if (farr1.length != farr0.length)
            {
                return false;
            }
            // not equal if store name is differ.
            for (int i = 0; i < farr0.length; i++)
            {
                String sn0 = farr0[i].getStorename();
                String sn1 = farr1[i].getStorename();
                if (!sn0.equals(sn1))
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * ハッシュコード<br>
     * <code>equals()</code>をオーバーライドした場合、ハッシュコードも
     * オーバーライドしてください。
     * @return ハッシュコード
     */
    public int hashCode()
    {
        return super.hashCode();
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @return lockTimeを返します。
     */
    public Date getLockTime()
    {
        return _lockTime;
    }

    /**
     * @return lockOwnerを返します。
     */
    public Object getLockOwner()
    {
        return _lockOwner;
    }

    /**
     * @param lockOwner lockOwnerを設定します。
     */
    protected void setLockOwner(Object lockOwner)
    {
        _lockOwner = lockOwner;
    }

    /**
     * @return lockFilesを返します。
     */
    public FileHandler[] getLockFiles()
    {
        return _lockFiles;
    }

    /**
     * @param lockFiles lockFilesを設定します。
     */
    protected void setLockFiles(FileHandler[] lockFiles)
    {
        if (lockFiles == null)
        {
            throw new RuntimeException("Lock target handlers should not null.");
        }
        Arrays.sort(lockFiles, new StoreNameComparator());
        _lockFiles = lockFiles;
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
        return "$Id: Locks.java 87 2008-10-04 03:07:38Z admin $";
    }
}


/**
 * 2つの<code>FileHandler</code>オブジェクトのストア名が
 * 同一であれば、同じと判断するクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  shimizu
 * @author  Last commit: $Author: admin $
 */
class StoreNameComparator
        implements Comparator, Serializable
{
    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object o1, Object o2)
    {
        if (o1 == null || o2 == null)
        {
            throw new RuntimeException("Lock target handler should not null.");
        }
        if (o1 instanceof FileHandler && o2 instanceof FileHandler)
        {
            FileHandler f1 = (FileHandler)o1;
            FileHandler f2 = (FileHandler)o2;

            String fn1 = f1.getStorename();
            String fn2 = f2.getStorename();
            return fn1.compareTo(fn2);
        }
        return 0;
    }
}

// $Id: UniqueArrayList.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 重複データを持たないようにしたArrayListクラスです。<br>
 * Listインターフェースをパラメータとするメソッドに渡すなどの
 * 理由がない限り、LinkedHashSet などのSet インターフェースを実装した
 * クラスを使用してください。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class UniqueArrayList
        extends ArrayList
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = -7920337703264501045L;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 初期容量 10 で空のリストを作成します。
     */
    public UniqueArrayList()
    {
        super();
    }

    /**
     * 指定された初期サイズで空のリストを作成します。
     * @param initCapacity
     */
    public UniqueArrayList(int initCapacity)
    {
        super(initCapacity);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 指定されたオブジェクトをリストの最後に追加します。<br>
     * ただし、contains()メソッドで検索されるオブジェクトが
     * すでにリストに存在する場合は、追加されません。
     * 
     * @param o 追加するオブジェクト
     * @return 追加されなかったときfalse
     */
    public boolean add(Object o)
    {
        if (contains(o))
        {
            return false;
        }
        return super.add(o);
    }

    /**
     * リストの指定された位置に、指定された要素を挿入します。<br>
     * 現在その位置にある要素と後続の要素は右に移動します
     * (インデックス値に 1 を加算)。<br>
     * ただし、contains()メソッドで検索されるオブジェクトが
     * すでにリストに存在する場合は、追加されません。
     * 
     * @param index 指定の要素が挿入されるインデックス
     * @param element 挿入される要素
     */
    public void add(int index, Object element)
    {
        if (!contains(element))
        {
            super.add(index, element);
        }
    }

    /**
     * リストの末尾に、指定された Collection のすべての要素を追加します。
     * これらの要素は、指定された Collection の Iterator が返す順序で追加されます。
     * このオペレーションの実行中に指定の Collection を変更した場合の、
     * オペレーション動作は定義されていません。
     * これは、指定された Collection がこのリスト自身であり、
     * しかもこのリストが空ではない場合に、この呼び出しの動作が
     * 未定義であることを意味します。
     * ただし、contains()メソッドで検索されるオブジェクトが
     * すでにリストに存在する場合は、そのオブジェクトは追加されません。
     * 
     * @param c リストに挿入する要素
     * @return この呼び出しの結果、このリストが変更された場合は true
     */
    public boolean addAll(Collection c)
    {
        Collection rdupColl = removeDuplicate(c);
        return super.addAll(rdupColl);
    }

    /**
     * リスト内の指定された位置から、指定された Collection のすべての要素を挿入します。
     * その時点で、指定された位置に要素がある場合、その位置の要素を移動させ、
     * 後続の要素を右側に移動して、それぞれのインデックスを増やします。
     * 新しい要素は、指定された Collection の反復子が返す順序で
     * リストに格納されます。
     * ただし、contains()メソッドで検索されるオブジェクトが
     * すでにリストに存在する場合は、そのオブジェクトは追加されません。
     * 
     * @param index 指定されたコレクションから最初の要素を挿入する位置のインデックス
     * @param c リストに挿入される要素
     * @return この呼び出しの結果、このリストが変更された場合は true
     */
    public boolean addAll(int index, Collection c)
    {
        Collection rdupColl = removeDuplicate(c);
        return super.addAll(index, rdupColl);
    }

    /**
     * UniqueArrayListのインスタンスを生成します。
     * 
     * @param sync 同期するリストを生成するときはtrue.
     * @return UniqueArrayList
     */
    public static List getInstance(boolean sync)
    {
        List nlist = null;
        if (sync)
        {
            nlist = Collections.synchronizedList(new UniqueArrayList());
        }
        else
        {
            nlist = new UniqueArrayList();
        }
        return nlist;
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
    /**
     * create a new Collection without duplicate values.
     * @param c 
     * @return 重複データを省いたコレクション
     */
    protected Collection removeDuplicate(Collection c)
    {
        return new LinkedHashSet(c);
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
        return "$Id: UniqueArrayList.java 87 2008-10-04 03:07:38Z admin $";
    }
}

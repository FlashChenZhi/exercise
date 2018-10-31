// $Id: AbstractOperator.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * オペレータクラスのための抽象クラスです。<br>
 * 各オペレータはこのクラスのサブクラスとして作成してください。<br>
 * 
 * このクラスではデータベースコネクションおよび呼び出し元クラスの保持を
 * 行います。
 *
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */
public abstract class AbstractOperator
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
    private Connection _connection;

    private Class _caller;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションと呼び出し元クラスを指定して
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    protected AbstractOperator(Connection conn, Class caller)
    {
        super();

        setConnection(conn);
        setCaller(caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    /**
     * 呼び出し元クラスを返します。
     * @return 呼び出し元クラス
     */
    public Class getCaller()
    {
        return _caller;
    }

    /**
     * 呼び出しクラスの短縮名を返します。
     * 
     * @return 呼び出しクラスの短縮名
     */
    public String getCallerName()
    {
        return getCaller().getSimpleName();
    }

    /**
     * 呼び出し元クラスを設定します。
     * @param caller 呼び出し元クラス<br>
     * nullが指定されたときは、呼び出し先クラスがセットされます。
     */
    public void setCaller(Class caller)
    {
        Class actClass = (null == caller) ? getClass()
                                         : caller;
        _caller = actClass;
    }

    /**
     * データベースコネクションを返します。
     * @return データベースコネクション
     */
    public Connection getConnection()
    {
        return _connection;
    }

    /**
     * データベースコネクションを設定します。
     * @param connection データベースコネクション
     */
    public void setConnection(Connection connection)
    {
        _connection = connection;
    }

    /**
     * 重複データの確認を行います。
     * 
     * @param values エンティティ
     * @param flds 調査対象フィールド
     * @return すべて同じ場合は null.<br>
     * 異なる値がある場合は、調査対象フィールドの内容 (ObjectまたはObject[]のセットされたリスト)
     */
    public List<Object> checkDuplicate(Entity[] values, FieldName... flds)
    {
        Set vset = new LinkedHashSet();

        boolean single = (1 == flds.length);
        for (Entity ent : values)
        {
            Object value = single ? ent.getValue(flds[0])
                                 : new ArrayValue(ent, flds);
            vset.add(value);
        }
        if (2 > vset.size())
        {
            // size 0 or 1 is not duplicate.
            return null;
        }

        // return values
        if (single)
        {
            return new ArrayList(vset);
        }
        else
        {
            ArrayValue[] varr = (ArrayValue[])vset.toArray(new ArrayValue[0]);
            List rvList = new ArrayList();
            for (int i = 0; i < varr.length; i++)
            {
                rvList.add(varr[i].getValues());
            }
            return rvList;
        }
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
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
        return "$Id: AbstractOperator.java 87 2008-10-04 03:07:38Z admin $";
    }

    /**
     * 配列を保持して要素ごとに比較を行うためのクラスです。
     *
     * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
     * @author  ss
     * @author  Last commit: $Author: admin $
     */
    static class ArrayValue
    {
        private Object[] _values;

        ArrayValue(Entity ent, FieldName... flds)
        {
            _values = new Object[flds.length];
            for (int i = 0; i < _values.length; i++)
            {
                _values[i] = ent.getValue(flds[i]);
            }
        }

        /**
         * 保持している値を返します。
         * @return 保持している値一覧
         */
        public Object[] getValues()
        {
            return _values;
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         */
        public boolean equals(Object o)
        {
            if (o instanceof ArrayValue)
            {
                ArrayValue o2 = (ArrayValue)o;
                if (_values.length != o2._values.length)
                {
                    return false;
                }
                else
                {
                    for (int i = 0; i < _values.length; i++)
                    {
                        Object v1 = _values[i];
                        Object v2 = o2._values[i];
                        if (null == v1 && null == v2)
                        {
                            continue;
                        }
                        else if (null == v1 && null != v2)
                        {
                            return false;
                        }
                        else if (null != v1 && !v1.equals(v2))
                        {
                            return false;
                        }
                    }
                }
                // all value is equal
                return true;
            }
            // type mismatch
            return false;
        }

        /**
         * @see java.lang.Object#hashCode()
         */
        public int hashCode()
        {
            int hc = 0;

            if (!ArrayUtil.isEmpty(_values))
            {
                for (int i = 0; i < _values.length; i++)
                {
                    if (null != _values[i])
                    {
                        hc += _values[i].hashCode();
                    }
                }
                return (hc / _values.length);
            }
            else
            {
                return 0;
            }
        }

        /**
         * @see java.lang.Object#toString()
         */
        public String toString()
        {
            StringBuilder buf = new StringBuilder("[");

            for (int i = 0; i < _values.length; i++)
            {
                if (i > 0)
                {
                    buf.append(",");
                }
                buf.append(String.valueOf(_values[i]));
            }
            buf.append("]");
            return new String(buf);
        }
    }
}

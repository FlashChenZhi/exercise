//$Id: NumberRange.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.field.validator;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jp.co.daifuku.common.text.StringUtil;

/**
 * 数値の範囲を保持・チェックするクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class NumberRange
        extends Range
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

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
     * 定義を解釈してインスタンスを初期化します。
     * @param rangeDef 範囲定義
     */
    public NumberRange(String rangeDef)
    {
        super(rangeDef);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

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
     * {@inheritDoc}
     */
    protected Comparable createSubject(String piece)
    {
        if (StringUtil.isBlank(piece))
        {
            return null;
        }
        BigDecimal pc = new BigDecimal(piece);
        return pc;
    }

    /**
     * {@inheritDoc}
     */
    protected List createEnumList(Set set)
    {
        return (new BigDecimalList(set));
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
        return "$Id: NumberRange.java 87 2008-10-04 03:07:38Z admin $";
    }

    static class BigDecimalList
            extends ArrayList
    {
        /** <code>serialVersionUID</code>のコメント */
        private static final long serialVersionUID = -2331178865720520176L;

        BigDecimalList(Set set)
        {
            super(set);
        }

        /**
         * compareTo()を使用して、同じ値のものがあるかどうか調べます。
         * @param o 調査対象
         * @return 同じ値のものがあればtrue.
         */
        public boolean contains(Object o)
        {
            int siz = size();
            if (o instanceof BigDecimal)
            {
                BigDecimal tval = (BigDecimal)o;
                for (int i = 0; i < siz; i++)
                {
                    Object val = get(i);
                    if (val instanceof BigDecimal)
                    {
                        BigDecimal cval = (BigDecimal)val;
                        int comp = tval.compareTo(cval);
                        if (comp == 0)
                        {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }
}

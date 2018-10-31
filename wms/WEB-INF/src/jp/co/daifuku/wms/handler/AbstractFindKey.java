//$Id: AbstractFindKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 検索用に条件を保持するための仮想クラスです。<br>
 * 検索対象のストアにかかわらず使用できるように設計されています。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class AbstractFindKey
        implements FindKey
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    //public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private List p_findConditionList = null;

    /** default store name */
    private String p_storeName;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void clear()
    {
        clearKeys();
    }

    /**
     * {@inheritDoc}
     */
    public void clearKeys()
    {
        setFindConditionList(null);
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public String getStoreName()
    {
        return p_storeName;
    }

    /**
     * {@inheritDoc}
     */
    public void setStoreName(String storeName)
    {
        p_storeName = storeName;
    }

    /**
     * {@inheritDoc}
     */
    public void setKey(FindKey key)
    {
        if (key == null)
        {
            return;
        }
        List newLimitList = key.getLimitConditionList();
        getLimitConditionList().addAll(newLimitList);
    }

    /**
     * {@inheritDoc}
     */
    public void setKey(Entity ent)
    {
        if (ent == null)
        {
            return;
        }
        Map valueMap = ent.getValueMap();
        if (valueMap == null || valueMap.size() == 0)
        {
            return;
        }

        Iterator entryIt = valueMap.entrySet().iterator();
        while (entryIt.hasNext())
        {
            Map.Entry entry = (Entry)entryIt.next();
            FieldName field = (FieldName)entry.getKey();
            Object value = entry.getValue();
            setKey(field, value);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setKey(FieldName field, Object value)
    {
        setKey(field, value, "=", "", "", true);
    }

    /**
     * {@inheritDoc}
     */
    public void setKey(FieldName field, Object value, String compcode, String left_Paren, String right_Paren,
            boolean and_or_toNext)
    {
        // 検索条件を保存
        Conditions cond = new Conditions(field);
        cond.setValue(value);
        cond.setCompareOperator(compcode);
        cond.setPrefix(left_Paren);
        cond.setPostfix(right_Paren);
        cond.setANDtoNext(and_or_toNext);

        List list = getLimitConditionList();
        list.add(cond);
    }

    /**
     * {@inheritDoc}
     */
    public void setKey(FieldName field, Object[] values, boolean and_or_toNext)
    {
        int numvalues = values.length;
        int lastnum = numvalues - 1;
        for (int i = 0; i < numvalues; i++)
        {
            boolean first = (i == 0);
            boolean last = (i == lastnum);

            String left = (first) ? "("
                                 : "";
            String right = (last) ? ")"
                                 : "";
            boolean toNext = (last) ? and_or_toNext
                                   : false;
            setKey(field, values[i], "=", left, right, toNext);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setRangeKey(FieldName fld, Object rangeLow, Object rangeHigh, boolean and_or_toNext)
    {
        if (null == fld)
        {
            return;
        }

        // fix LOW
        boolean withLow = true;
        if (rangeLow instanceof String)
        {
            String strLow = (String)rangeLow;
            withLow = (0 < strLow.length());
        }
        else
        {
            withLow = (null != rangeLow);
        }

        // fix HIGH
        boolean withHigh = true;
        if (rangeHigh instanceof String)
        {
            String strHigh = (String)rangeHigh;
            withHigh = (0 < strHigh.length());
        }
        else
        {
            withHigh = (null != rangeHigh);
        }

        // NO RANGE set
        if (!withLow && !withHigh)
        {
            return;
        }

        // LOW and HIGH set, check same value?
        if (withLow && withHigh)
        {
            boolean sameValue = rangeLow.equals(rangeHigh);
            if (sameValue)
            {
                setKey(fld, rangeLow, "=", "", "", and_or_toNext);
                return;
            }
        }

        // check for swap LOW,HIGH
        Object actRangeLow = rangeLow;
        Object actRangeHigh = rangeHigh;
        if (withLow && withHigh)
        {
            if ((rangeLow instanceof Comparable) && (rangeHigh instanceof Comparable))
            {
                Comparable compLow = (Comparable)rangeLow;
                Comparable compHigh = (Comparable)rangeHigh;
                if (0 < compLow.compareTo(compHigh))
                {
                    // LOW bigger than HIGH, swap both
                    actRangeLow = rangeHigh;
                    actRangeHigh = rangeLow;
                }
            }
        }

        // set LOW
        if (withLow)
        {
            String pfx = (withHigh) ? "("
                                   : "";
            boolean toNext = withHigh | and_or_toNext;
            setKey(fld, actRangeLow, ">=", pfx, "", toNext);
        }
        // set HIGH
        if (withHigh)
        {
            String pfx = (withLow) ? ")"
                                  : "";
            setKey(fld, actRangeHigh, "<=", "", pfx, and_or_toNext);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List getLimitConditionList()
    {
        if (p_findConditionList == null)
        {
            p_findConditionList = createList();
        }
        return p_findConditionList;
    }

    /**
     * @param findList findListを設定します。
     */
    protected void setFindConditionList(List findList)
    {
        p_findConditionList = findList;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * UniqueArrayListのインスタンスを生成して返します。<br>
     * @return 同期するUniqueArrayList
     */
    protected List createUniqueList()
    {
        return UniqueArrayList.getInstance(true);
    }

    /**
     * ArrayListのインスタンスを生成して返します。<br>
     * @return 同期する ArrayList
     */
    protected List createList()
    {
        return Collections.synchronizedList(new ArrayList());
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
        return "$Id: AbstractFindKey.java 87 2008-10-04 03:07:38Z admin $";
    }
}

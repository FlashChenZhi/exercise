//$Id: Conditions.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.Serializable;

import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 検索・取得条件を保持するクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class Conditions
        implements Serializable
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    //public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    /** <code>serialVersionUID</code>のコメント */
    private static final long serialVersionUID = 4318367668026470421L;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private FieldName p_fieldName;

    private FieldName p_saveFieldName;

    private Object p_value = null;

    private String p_prefix = "";

    private String p_postfix = "";

    private boolean p_sortOrder = true;

    private boolean p_ANDtoNext = true;

    private String p_compareOperator = "=";

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 対象フィールドを指定してインスタンスを生成します。
     * @param field 対象フィールド
     */
    public Conditions(FieldName field)
    {
        setFieldName(field);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * @return fieldを返します。
     */
    public FieldName getFieldName()
    {
        return p_fieldName;
    }

    /**
     * @param field fieldを設定します。
     */
    public void setFieldName(FieldName field)
    {
        if (field != null)
        {
            p_fieldName = field;
        }
    }

    /**
     * @return valueを返します。
     */
    public Object getValue()
    {
        return p_value;
    }

    /**
     * 検索条件または更新条件の値をセットします。
     * @param value valueを設定します。
     */
    public void setValue(Object value)
    {
        p_value = value;
    }

    /**
     * @return postfixを返します。
     */
    public String getPostfix()
    {
        return p_postfix;
    }

    /**
     * @param postfix postfixを設定します。
     */
    public void setPostfix(String postfix)
    {
        if (postfix != null)
        {
            p_postfix = postfix;
        }
    }

    /**
     * @return prefixを返します。
     */
    public String getPrefix()
    {
        return p_prefix;
    }

    /**
     * @param prefix prefixを設定します。
     */
    public void setPrefix(String prefix)
    {
        if (prefix != null)
        {
            p_prefix = prefix;
        }
    }

    /**
     * @return sortOrderを返します。
     */
    public boolean isSortOrder()
    {
        return p_sortOrder;
    }

    /**
     * @param sortOrder sortOrderを設定します。
     */
    public void setSortOrder(boolean sortOrder)
    {
        p_sortOrder = sortOrder;
    }

    /**
     * @return aNDToNextを返します。
     */
    public boolean isANDtoNext()
    {
        return p_ANDtoNext;
    }

    /**
     * @param toNext aNDToNextを設定します。
     */
    public void setANDtoNext(boolean toNext)
    {
        p_ANDtoNext = toNext;
    }

    /**
     * @return compareOperatorを返します。
     */
    public String getCompareOperator()
    {
        return p_compareOperator;
    }

    /**
     * @param compareOperator compareOperatorを設定します。
     */
    public void setCompareOperator(String compareOperator)
    {
        if (compareOperator == null || compareOperator.length() == 0)
        {
            throw new RuntimeException("Compare operator required");
        }
        p_compareOperator = compareOperator;
    }

    /**
     * @return saveFieldNameを返します。
     */
    public FieldName getSaveFieldName()
    {
        return p_saveFieldName;
    }

    /**
     * @param saveFieldName saveFieldNameを設定します。
     */
    public void setSaveFieldName(FieldName saveFieldName)
    {
        p_saveFieldName = saveFieldName;
    }

    /**
     * 保持するFieldNameが同一かどうかで比較します。
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o1)
    {
        if (o1 instanceof Conditions)
        {
            Conditions c1 = (Conditions)o1;

            String fullnm0 = getFieldName().getFullName();
            String fullnm1 = c1.getFieldName().getFullName();

            boolean eq =
                    ((p_ANDtoNext == c1.p_ANDtoNext) && equals(p_compareOperator, c1.p_compareOperator)
                            && equals(p_postfix, c1.p_postfix) && equals(p_prefix, c1.p_prefix)
                            && equals(p_value, c1.p_value) && equals(fullnm0, fullnm1));

            return eq;
        }
        return false;
    }

    /**
     * ハッシュコードを返します。<br>
     * 注意: {@link Object#hashCode()} をそのまま返します。
     * @return ハッシュコードを返します。
     */
    public int hashCode()
    {
        return super.hashCode();
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
     * nullを含む同一値であるかどうかテストします。
     * 
     * @param s1 対象1
     * @param s2 対象2
     * @return 同一ならtrue.
     */
    protected boolean equals(Object s1, Object s2)
    {
        boolean ret = false;
        if (s1 == null && s2 == null)
        {
            ret = true;
        }
        else if (s1 != null)
        {
            ret = s1.equals(s2);
        }
        else
        {
            ret = false;
        }
        return ret;
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
        return "$Id: Conditions.java 87 2008-10-04 03:07:38Z admin $";
    }
}

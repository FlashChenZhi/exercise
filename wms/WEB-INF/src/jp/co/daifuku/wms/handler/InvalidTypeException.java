//$Id: InvalidTypeException.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * エンティティへのアクセスにおいて、タイプが合致していない場合に
 * スローされる例外です。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class InvalidTypeException
        extends RuntimeException
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    /** <code>serialVersionUID</code>のコメント */
    private static final long serialVersionUID = -656687238769420752L;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private FieldName p_fieldName;

    private Object p_fieldValue;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 例外対象のフィールドおよびその値をセットして初期化します。
     * @param field 対象フィールド
     * @param fieldValue フィールドの値
     */
    public InvalidTypeException(FieldName field, Object fieldValue)
    {
        setFieldName(field);
        setFieldValue(fieldValue);
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
     * @return fieldNameを返します。
     */
    public FieldName getFieldName()
    {
        return p_fieldName;
    }

    /**
     * @param fieldName fieldNameを設定します。
     */
    public void setFieldName(FieldName fieldName)
    {
        p_fieldName = fieldName;
    }

    /**
     * @return fieldValueを返します。
     */
    public Object getFieldValue()
    {
        return p_fieldValue;
    }

    /**
     * @param fieldValue fieldValueを設定します。
     */
    public void setFieldValue(Object fieldValue)
    {
        p_fieldValue = fieldValue;
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
        return "$Id: InvalidTypeException.java 87 2008-10-04 03:07:38Z admin $";
    }
}

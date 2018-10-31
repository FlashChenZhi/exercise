//$Id: DateFieldValidator.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.field.validator;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;

import jp.co.daifuku.wms.handler.field.FieldMetaData;


/**
 * 日付型のバリデータクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class DateFieldValidator
        extends FieldValidator
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
    protected int validateType(Object value, FieldMetaData meta)
    {
        if (!meta.isRequire())
        {
            if (null == value)
            {
                return RETURN_VALID;
            }
            if (0 == value.toString().trim().length())
            {
                return RETURN_VALID;
            }
        }

        int ret = (value instanceof Date) ? RETURN_VALID
                                         : RETURN_TYPE_ERROR;
        return ret;
    }

    /**
     * {@inheritDoc}
     */
    protected int validateLength(Object value, FieldMetaData meta)
    {
        return RETURN_VALID;
    }

    /**
     * {@inheritDoc}
     */
    protected int validateRange(Object value, FieldMetaData meta)
    {
        // TODO 将来的には範囲チェックしたいが、範囲の記述方法が難しい
        return RETURN_VALID;
    }

    /**
     * {@inheritDoc}
     */
    protected Range getRange(String rangeDef)
    {
        return null;
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
        return "$Id: DateFieldValidator.java 87 2008-10-04 03:07:38Z admin $";
    }
}

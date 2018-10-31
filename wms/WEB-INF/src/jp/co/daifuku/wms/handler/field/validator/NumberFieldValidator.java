//$Id: NumberFieldValidator.java 8074 2014-09-03 04:57:52Z okamura $
package jp.co.daifuku.wms.handler.field.validator;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;

import jp.co.daifuku.wms.handler.field.FieldMetaData;

/**
 * 数値型のバリデータクラスです。
 *
 * @version $Revision: 8074 $, $Date: 2014-09-03 13:57:52 +0900 (水, 03 9 2014) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public class NumberFieldValidator
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
    private Range _range = null;

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
        int ret = (value instanceof BigDecimal) ? RETURN_VALID
                                               : RETURN_TYPE_ERROR;
        return ret;
    }

    /**
     * {@inheritDoc}
     */
    protected int validateLength(Object value, FieldMetaData meta)
    {
        if (RETURN_VALID == super.validateLength(value, meta))
        {
            return RETURN_VALID;
        }

        if (value instanceof BigDecimal)
        {
            int[] scales = meta.getScales();
            int decLength = scales[0];
            int pointLength = scales[1];

            // 整数部桁数チェック
            BigDecimal dec = (BigDecimal)value;
            String iStr = dec.abs().toBigInteger().toString();
            if (iStr.length() > decLength)
            {
                return RETURN_LENGTH_ERROR;
            }

            // 小数部桁数チェック
            int pointdec = dec.scale();
            if (pointdec > pointLength)
            {
                dec.setScale(pointLength, BigDecimal.ROUND_HALF_UP);
            }
        }
        return RETURN_VALID;
    }

    /**
     * {@inheritDoc}
     */
    protected Range getRange(String rangeDef)
    {
        if (_range == null)
        {
            _range = new NumberRange(rangeDef);
        }
        return _range;
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
        return "$Id: NumberFieldValidator.java 8074 2014-09-03 04:57:52Z okamura $";
    }
}

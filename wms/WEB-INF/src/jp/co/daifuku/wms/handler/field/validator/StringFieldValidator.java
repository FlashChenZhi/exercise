//$Id: StringFieldValidator.java 8059 2013-05-24 10:19:42Z kishimoto $
package jp.co.daifuku.wms.handler.field.validator;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.UnsupportedEncodingException;

import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.wms.handler.field.FieldMetaData;

/**
 * 文字列のチェックを行うバリデータクラス。
 *
 * @version $Revision: 8059 $, $Date: 2013-05-24 19:19:42 +0900 (金, 24 5 2013) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */
public class StringFieldValidator
        extends FieldValidator
{
    private static final String CP_HANDLER_DB_CHARSET = "HANDLER_DB_CHARSET";

    private static final String DB_DEFAULT_CHARSET = "MS932";

    /**
     * # DBHandlerで バイト数計算用のキャラクタセット
     * HANDLER_DB_CHARSET = MS932
     */
    public static final String CHARSET_NAME;

    static
    {
        // setup Charset name for check byte length
        String dbCharset = CommonParam.getParam(CP_HANDLER_DB_CHARSET);
        if ((null == dbCharset) || (0 == dbCharset.length()))
        {
            // using default
            dbCharset = DB_DEFAULT_CHARSET;
        }
        CHARSET_NAME = dbCharset;
    }

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    /*
     * FieldMetaData
     * public static final int TYPECLASS_NUMERIC = 0 ;
     * public static final int TYPECLASS_ALPHABETIC = 1 ;
     * public static final int TYPECLASS_ASCII = 2 ;
     * public static final int TYPECLASS_MULTI_BYTE = 3 ;
     */
    private static final TypeClassString[] STRING_CLASSES = {
        new TypeClassNumericString(),
        new TypeClassAlphabetic(),
        new TypeClassASCII(),
        new TypeClassAny(),
    };

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
    /**
     * {@inheritDoc}
     */
    @Override
    public int validate(Object value, FieldMetaData meta, boolean valueOnly)
    {
        int ret = RETURN_VALID;

        ret = super.validate(value, meta, valueOnly);
        if ((ret != RETURN_VALID) || isNullValue(value, meta))
        {
            return ret;
        }

        // 空白チェック
        ret = validateSpace(value, meta);
        if (ret != RETURN_VALID)
        {
            return ret;
        }

        // 文字クラスチェック
        ret = validateStringClass(value, meta);
        if (ret != RETURN_VALID)
        {
            return ret;
        }

        return ret;
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
     * NULLの値かどうかをチェックします。<br>
     * Stringの長さ = 0 は、nullとみなします。
     * 
     * @param value チェック対象値
     * @param meta フィールドのメタ情報
     * @return nullとみなされる値のときtrue.
     */
    @Override
    protected boolean isNullValue(Object value, FieldMetaData meta)
    {
        if (value instanceof String)
        {
            String strValue = (String)value;
            return (0 == strValue.length());
        }
        return super.isNullValue(value, meta);
    }

    /**
     * 空白可能チェックを行います。
     *
     * @param value 対象文字列
     * @param meta フィールドメタ情報
     * @return 正常:RETURN_VALID<br>
     * 空白異常:RETURN_SPACE_ERROR
     */
    protected int validateSpace(Object value, FieldMetaData meta)
    {
        int ret = RETURN_VALID;
        boolean enSpace = meta.isEnableSpace();
        if (!enSpace)
        {
            int spidx = value.toString().trim().indexOf(" ");
            if (spidx >= 0)
            {
                ret = RETURN_SPACE_ERROR;
            }
        }

        return ret;
    }

    /**
     * 文字列の格納クラスをチェックします。
     *
     * @param value 対象文字列
     * @param meta フィールドメタ情報
     * @return 正常:RETURN_VALID<br>
     * 格納クラス異常:RETURN_TYPE_CLASS_ERROR
     */
    protected int validateStringClass(Object value, FieldMetaData meta)
    {
        int si = meta.getTypeClass();
        TypeClassString strClass = STRING_CLASSES[si];

        int ret = (strClass.isValid(value.toString())) ? RETURN_VALID
                                                      : RETURN_TYPE_CLASS_ERROR;
        return ret;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int validateType(Object value, FieldMetaData meta)
    {
        int ret = (value instanceof String) ? RETURN_VALID
                                           : RETURN_TYPE_ERROR;
        return ret;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int validateLength(Object value, FieldMetaData meta)
    {
        if (RETURN_VALID == super.validateLength(value, meta))
        {
            return RETURN_VALID;
        }

        int valLength = getByteLength(value);
        int limitLeng = meta.getLength().intValue();

        if (valLength > limitLeng)
        {
            return RETURN_LENGTH_ERROR;
        }
        return RETURN_VALID;
    }

    /**
     * returns length of byte converted
     *
     * @param value check value
     * @return length in bytes
     */
    protected int getByteLength(Object value)
    {
        byte[] byteVal = null;
        String strValue = value.toString();
        try
        {
            byteVal = strValue.getBytes(CHARSET_NAME);
        }
        catch (UnsupportedEncodingException e)
        {
            // invalid charset name defined in CommonParam
            e.printStackTrace();
            try
            {
                // try convert with my default charset
                byteVal = strValue.getBytes(DB_DEFAULT_CHARSET);
            }
            catch (UnsupportedEncodingException e1)
            {
                // it never occurs
                byteVal = strValue.getBytes();
            }
        }
        return byteVal.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Range getRange(String rangeDef)
    {
        if (_range == null)
        {
            _range = new StringRange(rangeDef);
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
     *
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: StringFieldValidator.java 8059 2013-05-24 10:19:42Z kishimoto $";
    }
}

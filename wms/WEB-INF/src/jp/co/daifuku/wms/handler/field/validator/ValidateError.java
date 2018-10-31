//$Id: ValidateError.java 6468 2009-12-15 09:01:11Z okamura $
package jp.co.daifuku.wms.handler.field.validator;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.handler.field.FieldMetaData;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * 内容チェックの結果を保持するクラスです。
 *
 * @version $Revision: 6468 $, $Date: 2009-12-15 18:01:11 +0900 (火, 15 12 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */


public class ValidateError
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** validateエラーコード (正常) */
    public static final int RETURN_VALID = 0;

    /** validateエラーコード (必須項目エラー:空白またはnull) */
    public static final int RETURN_REQUIRE_ERROR = 1;

    /** validateエラーコード (タイプエラー) */
    public static final int RETURN_TYPE_ERROR = 2;

    /** validateエラーコード (文字長または精度エラー) */
    public static final int RETURN_LENGTH_ERROR = 3;

    /** validateエラーコード (範囲エラー) */
    public static final int RETURN_RANGE_ERROR = 4;

    /** validateエラーコード (セット可能タイプクラスエラー) */
    public static final int RETURN_TYPE_CLASS_ERROR = 50;

    /** validateエラーコード (空白ありエラー) */
    public static final int RETURN_SPACE_ERROR = 99;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private FieldName _fieldName;

    private FieldMetaData _metaData;

    private int _errorCode;

    private Object _fieldValue;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * フィールドのチェック結果を保存します。
     * @param field 対象フィールド
     * @param fieldValue フィールドが保持していた値
     * @param errorCode エラーコード
     * @param metaData 対象フィールドのメタ情報
     * @see FieldValidator
     */
    public ValidateError(FieldName field, Object fieldValue, int errorCode, FieldMetaData metaData)
    {
        setFieldName(field);
        setFieldValue(fieldValue);
        setErrorCode(errorCode);
        setMetaData(metaData);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * このエラーの文字列表現を返します。
     * @return エラー内容
     */
    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        buf.append("Field=");
        buf.append(getFieldName().getFullName());
        buf.append(":Value=");
        buf.append(getFieldValue());
        buf.append(":Error=");
        buf.append(getErrorCode());

        return String.valueOf(buf);
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @return fieldNameを返します。
     */
    public FieldName getFieldName()
    {
        return _fieldName;
    }

    /**
     * @param fieldName fieldNameを設定します。
     */
    public void setFieldName(FieldName fieldName)
    {
        _fieldName = fieldName;
    }

    /**
     * @return errorCodeを返します。
     * @see FieldValidator
     */
    public int getErrorCode()
    {
        return _errorCode;
    }

    /**
     * @param errorCode errorCodeを設定します。
     */
    public void setErrorCode(int errorCode)
    {
        _errorCode = errorCode;
    }

    /**
     * @return fieldValueを返します。
     */
    public Object getFieldValue()
    {
        return _fieldValue;
    }

    /**
     * @param fieldValue fieldValueを設定します。
     */
    public void setFieldValue(Object fieldValue)
    {
        _fieldValue = fieldValue;
    }

    /**
     * metaDataを返します。
     * @return metaDataを返します。
     */
    public FieldMetaData getMetaData()
    {
        return _metaData;
    }

    /**
     * metaDataを設定します。
     * @param metaData metaData
     */
    public void setMetaData(FieldMetaData metaData)
    {
        _metaData = metaData;
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
        return "$Id: ValidateError.java 6468 2009-12-15 09:01:11Z okamura $";
    }
}

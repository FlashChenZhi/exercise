//$Id: FieldValidator.java 87 2008-10-04 03:07:38Z admin $
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
 * フィールドにセットされた値が正しいかどうかチェックするためのクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class FieldValidator
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** validateエラーコード (正常) */
    public static final int RETURN_VALID = ValidateError.RETURN_VALID;

    /** validateエラーコード (必須項目エラー:空白またはnull) */
    public static final int RETURN_REQUIRE_ERROR = ValidateError.RETURN_REQUIRE_ERROR;

    /** validateエラーコード (タイプエラー) */
    public static final int RETURN_TYPE_ERROR = ValidateError.RETURN_TYPE_ERROR;

    /** validateエラーコード (文字長または精度エラー) */
    public static final int RETURN_LENGTH_ERROR = ValidateError.RETURN_LENGTH_ERROR;

    /** validateエラーコード (範囲エラー) */
    public static final int RETURN_RANGE_ERROR = ValidateError.RETURN_RANGE_ERROR;

    /** validateエラーコード (セット可能タイプクラスエラー) */
    public static final int RETURN_TYPE_CLASS_ERROR = ValidateError.RETURN_TYPE_CLASS_ERROR;

    /** validateエラーコード (空白ありエラー) */
    public static final int RETURN_SPACE_ERROR = ValidateError.RETURN_SPACE_ERROR;

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
    /**
     * 内容チェックを行います。
     * @param value チェックするエンティティ
     * @param meta フィールドメタデータ
     * @param valueOnly 値がセットされているものだけをチェックするときは true.
     * @return チェック結果 (RETURN_XXXX)
     */
    public int validate(Object value, FieldMetaData meta, boolean valueOnly)
    {
        // no need any check if field is disabled.
        if (!meta.isEnabled())
        {
            return RETURN_VALID;
        }

        // null(必須)チェック
        boolean isNull = isNullValue(value, meta);
        if (isNull)
        {
            if (valueOnly)
            {
                return RETURN_VALID;
            }
            else
            {
                int nret = (meta.isRequire()) ? RETURN_REQUIRE_ERROR
                                             : RETURN_VALID;
                return nret;
            }
        }

        // no need value check if Value is Field name
        if (value instanceof FieldName)
        {
            return RETURN_VALID;
        }

        int ret = RETURN_VALID;

        // 型チェック
        ret = validateType(value, meta);
        if (ret != RETURN_VALID)
        {
            return ret;
        }

        // 長さチェック
        ret = validateLength(value, meta);
        if (ret != RETURN_VALID)
        {
            return ret;
        }

        // 範囲チェック
        ret = validateRange(value, meta);
        if (ret != RETURN_VALID)
        {
            return ret;
        }

        return RETURN_VALID;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * 範囲チェッククラスを生成します。
     * 
     * @param rangeDef
     * @return 範囲チェッククラス
     */
    protected abstract Range getRange(String rangeDef);

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * NULLの値かどうかをチェックします。
     * 
     * @param value チェック対象値
     * @param meta フィールドのメタ情報
     * @return nullとみなされる値のときtrue.
     */
    protected boolean isNullValue(Object value, FieldMetaData meta)
    {
        return (null == value);
    }

    /**
     * オブジェクトの型が想定されているものかどうか確認します。
     * @param value 入力値
     * @param meta フィールドメタ情報
     * @return 正しいときは RETURN_VALID
     */
    protected abstract int validateType(Object value, FieldMetaData meta);

    /**
     * 長さが正しいかどうか確認します。
     * 
     * @param value 入力値
     * @param meta フィールドメタ情報
     * @return 正しいときは RETURN_VALID
     */
    protected int validateLength(Object value, FieldMetaData meta)
    {
        if (FieldMetaData.LENGTH_UNLIMITED == meta.getLength())
        {
            return RETURN_VALID;
        }
        return RETURN_LENGTH_ERROR;
    }

    /**
     * 範囲が正しいかどうか確認します。
     * 
     * @param value 入力値
     * @param meta フィールドメタ情報
     * @return 正しいときは RETURN_VALID
     */
    protected int validateRange(Object value, FieldMetaData meta)
    {
        if (value instanceof Comparable)
        {
            String rangeDef = meta.getRange();
            if (rangeDef == null || rangeDef.length() == 0)
            {
                return RETURN_VALID;
            }

            Range rangeChecker = getRange(rangeDef);
            if (rangeChecker == null)
            {
                return RETURN_VALID;
            }

            if (Range.RANGE_VALID != rangeChecker.inRange((Comparable)value))
            {
                return RETURN_RANGE_ERROR;
            }
        }
        return RETURN_VALID;
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
        return "$Id: FieldValidator.java 87 2008-10-04 03:07:38Z admin $";
    }
}

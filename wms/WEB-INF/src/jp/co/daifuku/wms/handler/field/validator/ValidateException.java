//$Id: ValidateException.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.field.validator;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.handler.field.FieldMetaData;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * エンティティの内容をチェックしたとき、正しくないデータが発見された場合に
 * スローされる例外です。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class ValidateException
        extends RuntimeException
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    /** <code>serialVersionUID</code>のコメント */
    private static final long serialVersionUID = 7909233519240852034L;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private List _validateErrorList;

    private String _charSet = null;

    private byte[] _record = null;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 例外を初期化します。
     */
    public ValidateException()
    {
        // _validateErrorList = Collections.synchronizedList(new ArrayList()) ;
        _validateErrorList = new ArrayList();
    }

    /**
     * フィールドのエラー内容を指定して例外を生成します。
     * 
     * @param field
     * @param value
     * @param errcode
     * @param metaData
     */
    public ValidateException(FieldName field, Object value, int errcode, FieldMetaData metaData)
    {
        this();
        ValidateError err = new ValidateError(field, value, errcode, metaData);
        addValidateError(err);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * この例外の内容をロギングします。
     * @param logger ロギング元として指定するクラス
     */
    public void logMessage(Class logger)
    {
        // TODO fix message and message number!
        RmiMsgLogClient.write(9999999, logger.getName());
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * バリデートエラーを追加します。
     * @param error
     */
    public void addValidateError(ValidateError error)
    {
        _validateErrorList.add(error);
    }

    /**
     * バリデートエラーを追加します。
     * @param errors
     */
    public void addValidateErrors(ValidateError[] errors)
    {
        _validateErrorList.addAll(Arrays.asList(errors));
    }

    /**
     * バリデートエラーを取得します。
     * @return バリデートエラー
     */
    public ValidateError[] getValidateErrors()
    {
        return (ValidateError[])_validateErrorList.toArray(new ValidateError[0]);
    }

    /**
     * @see java.lang.Throwable#toString()
     */
    @Override
    public String toString()
    {
        StringBuffer buff = new StringBuffer("ValidateException\n");

        ValidateError[] errs = getValidateErrors();
        for (int i = 0; i < errs.length; i++)
        {
            buff.append(errs[i]);
            buff.append("\n");
        }

        return new String(buff);
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

    /**
     * キャラクタセットを返します。
     * @return キャラクタセットを返します。
     */
    public String getCharSet()
    {
        return _charSet;
    }

    /**
     * キャラクタセットを設定します。
     * @param charSet キャラクタセット ファイルの読み込み時以外は nullが返されます。
     */
    public void setCharSet(String charSet)
    {
        _charSet = charSet;
    }

    /**
     * レコードの内容を返します。
     * @return レコードの内容。ファイルの読み込み時以外は nullが返されます。
     */
    public byte[] getRecord()
    {
        return _record;
    }

    /**
     * recordを設定します。
     * @param record record
     */
    public void setRecord(byte[] record)
    {
        _record = record;
    }

    /**
     * レコードの内容を文字列として返します。
     * @return レコードの内容。ファイルの読み込み時以外は nullが返されます。
     */
    public String getRecordString()
    {
        byte[] rec = getRecord();
        if (null == rec)
        {
            return null;
        }

        String charset = getCharSet();
        if (null == charset || 0 == charset.length())
        {
            return new String(rec);
        }
        else
        {
            try
            {
                return new String(rec, charset);
            }
            catch (UnsupportedEncodingException e)
            {
                return new String(rec);
            }
        }
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: ValidateException.java 87 2008-10-04 03:07:38Z admin $";
    }
}

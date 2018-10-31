// $Id: RecordFormatException.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

/**
 * エンティティ生成時にフォーマットエラーを検出した際にスローされる例外です。<br>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  清水　正人
 * @author  Last commit: $Author: admin $
 */

public class RecordFormatException
        extends ParseException
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /** <code>serialVersionUID</code>のコメント */
    private static final long serialVersionUID = 7842869168326603780L;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    private byte[] _record = null;

    private String _charset = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     * 詳細メッセージとエラーを検出した位置を指定してRecordFormatExceptionを
     * インスタンス化します。
     * @param message 詳細メッセージ
     * @param offset エラーを検出した位置
     */
    public RecordFormatException(String message, int offset)
    {
        super(message, offset);
    }

    /**
     * コンストラクタ
     * 詳細メッセージを指定して、RecordFormatExceptionをインスタンス化します。
     * @param message 詳細メッセージ
     */
    public RecordFormatException(String message)
    {
        this(message, 0);
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
     * recordを返します。
     * @return recordを返します。
     */
    public byte[] getRecord()
    {
        return _record;
    }

    /**
     * recordをString型に変換して返します。
     * @return recordを返します。
     */
    public String getRecordString()
    {
        try
        {
            return new String(_record, _charset);
        }
        catch (UnsupportedEncodingException e)
        {
            //e.printStackTrace();
            return new String(_record);
        }
    }

    /**
     * record/charsetを設定します。
     * @param record record
     * @param charset charset
     */
    public void setRecord(byte[] record, String charset)
    {
        _record = record;
        _charset = charset;
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
     * このクラスのリビジョンを返します。<BR>
     * @return リビジョン文字列。<BR>
     */
    public static String getVersion()
    {
        return "$Id: RecordFormatException.java 87 2008-10-04 03:07:38Z admin $";
    }
}

// $Id: CSVRecordFormatter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.file.record;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.UnsupportedEncodingException;

import jp.co.daifuku.wms.handler.field.FieldMetaData;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.file.FileHandler;
import jp.co.daifuku.wms.handler.util.HandlerCSVTokenizer;


/**
 * CSVレコードをハンドリングするためのレコードフォーマッタクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class CSVRecordFormatter
        extends AbstractRecordFormatter
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
    private String _splitString;

    private String _quoteString;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ハンドラを指定してインスタンスを生成します。
     * @param handler このフォーマッタを使用するハンドラ
     */
    protected CSVRecordFormatter(FileHandler handler)
    {
        super(handler);

        StoreMetaData smeta = handler.getStoreMetaData();
        setSplitString(smeta.getSplitString());
        setQuoteString(smeta.getQuoteString());
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
     * @return quoteStringを返します。
     */
    public String getQuoteString()
    {
        return _quoteString;
    }

    /**
     * @param quoteString quoteStringを設定します。
     */
    public void setQuoteString(String quoteString)
    {
        _quoteString = quoteString;
    }

    /**
     * @return splitStringを返します。
     */
    public String getSplitString()
    {
        return _splitString;
    }

    /**
     * @param splitString splitStringを設定します。
     */
    public void setSplitString(String splitString)
    {
        _splitString = splitString;
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
     * {@inheritDoc}
     */
    protected String unionFields(String[] fldStrArr)
    {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < fldStrArr.length; i++)
        {
            if (i > 0)
            {
                buff.append(getSplitString());
            }
            buff.append(fldStrArr[i]);
        }

        return String.valueOf(buff);
    }

    /**
     * {@inheritDoc}
     */
    protected String fieldDecoration(FieldMetaData fmeta, String value)
    {
        String rValue = "";
        int ftype = fmeta.getType();
        if (ftype == FieldMetaData.TYPE_STRING)
        {
            String quote = getQuoteString();
            String rString = value;
            if (value.indexOf(quote) > -1)
            {
                rString = escapeQuote(value, quote);
            }
            rValue = quote + rString + quote;
        }
        else
        {
            rValue = value;
        }
        return rValue;
    }

    /**
     * {@inheritDoc}
     */
    protected void onAfterParse(String[] values)
    {
        // no processing in this class
    }

    /**
     * {@inheritDoc}
     */
    protected byte[] onBeforeParse(byte[] rec)
    {
        // no processing in this class
        return rec;
    }

    /**
     * {@inheritDoc}
     */
    protected void alignField(String[] values)
    {
        String quote = getQuoteString();
        for (int i = 0; i < values.length; i++)
        {
            String value = values[i];
            values[i] = unescapeQuote(value, quote);
        }
    }

    /**
     * {@inheritDoc}
     */
    protected String[] splitRecord(byte[] rec)
            throws UnsupportedEncodingException
    {
        char split = getSplitString().charAt(0);
        char quote = getQuoteString().charAt(0);

        String recStr = new String(rec, getStoreMetaData().getCharset());
        HandlerCSVTokenizer tok = new HandlerCSVTokenizer(recStr, split, quote);

        String[] rStrArr = new String[tok.countTokens()];

        int idx = 0;
        while (tok.hasMoreTokens())
        {
            rStrArr[idx++] = tok.nextToken();
        }
        return rStrArr;
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
        return "$Id: CSVRecordFormatter.java 87 2008-10-04 03:07:38Z admin $";
    }

    /**
     * クォーテーションで指定された文字をエスケープします。
     * @param value エスケープ対象文字列
     * @param quote クォーテーション文字
     * @return クォーテーションがエスケープされた文字列
     */
    protected String escapeQuote(String value, String quote)
    {
        return value.replaceAll(quote, quote + quote);
    }

    /**
     * エスケープされたクォーテーションを元に戻します。
     * @param value エスケープ対象文字列
     * @param quote クォーテーション文字
     * @return クォーテーションのエスケープが取り除かれた文字列
     */
    protected String unescapeQuote(String value, String quote)
    {
        if (null == value || 0 == value.length())
        {
            return value;
        }
        return value.replaceAll(quote + quote, quote);
    }
}

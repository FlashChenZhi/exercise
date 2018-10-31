// $Id: FixedCSVRecordFormatter.java 87 2008-10-04 03:07:38Z admin $
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


/**
 * 固定長のCSVファイルをハンドリングするためのレコードフォーマッタクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class FixedCSVRecordFormatter
        extends FixedRecordFormatter
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
    private int _recLength = -1;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ハンドラを指定してインスタンスを生成します。
     * @param handler このフォーマッタを使用するハンドラ
     */
    protected FixedCSVRecordFormatter(FileHandler handler)
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
    /**
     * {@inheritDoc}
     */
    public int length()
    {
        int recSize = _recLength;
        if (recSize < 0)
        {
            recSize = 0;
            String charset = getStoreMetaData().getCharset();
            try
            {
                StoreMetaData smeta = getStoreMetaData();

                FieldMetaData[] fmetaArr = smeta.getFieldMetaDatas();
                int splitLen = getSplitString().length();
                for (int i = 0; i < fmetaArr.length; i++)
                {
                    FieldMetaData fmeta = fmetaArr[i];
                    float fLen = fmeta.getLength();
                    int fLenByte = getTotalLength(fLen);
                    recSize += fLenByte;

                    if (i > 0)
                    {
                        recSize += splitLen;
                    }
                }
                recSize += FileHandler.LINE_SEPARATOR.getBytes(charset).length;
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
                throw new RuntimeException("Charactor set(" + charset + ") does not supported.");
            }
        }

        return recSize;
    }

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
    protected String[] splitRecord(byte[] rec)
            throws UnsupportedEncodingException
    {
        StoreMetaData smeta = getStoreMetaData();
        String charset = smeta.getCharset();
        int spLength = smeta.getSplitString().length();
        FieldMetaData[] fldMetaArr = sortFields(smeta);
        String[] rValues = new String[fldMetaArr.length];

        byte[] srcByteArr = rec;

        int start = 0;
        for (int i = 0; i < rValues.length; i++)
        {
            FieldMetaData fmeta = fldMetaArr[i];

            float defLen = fmeta.getLength();
            int iLen = getIntegerLength(defLen);
            int pLen = getPointLength(defLen);
            if (pLen > 0)
            {
                iLen += (pLen + 1); // add for period length (=1)
            }
            rValues[i] = new String(srcByteArr, start, iLen, charset);
            start += iLen + spLength;
        }

        return rValues;
    }

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
        return "$Id: FixedCSVRecordFormatter.java 87 2008-10-04 03:07:38Z admin $";
    }
}

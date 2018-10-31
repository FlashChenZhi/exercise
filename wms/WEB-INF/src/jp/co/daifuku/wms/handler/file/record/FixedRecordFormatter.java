// $Id: FixedRecordFormatter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.file.record;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import jp.co.daifuku.wms.handler.field.FieldMetaData;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.file.FileHandler;


/**
 * 固定長のレコードをハンドリングするためのレコードフォーマッタクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class FixedRecordFormatter
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
    // private String p_Name ;

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
    protected FixedRecordFormatter(FileHandler handler)
    {
        super(handler);
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
                for (int i = 0; i < fmetaArr.length; i++)
                {
                    FieldMetaData fmeta = fmetaArr[i];
                    float fLen = fmeta.getLength();
                    int fLenByte = getTotalLength(fLen);
                    recSize += fLenByte;
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
        // 単純にすべての値を結合します。
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < fldStrArr.length; i++)
        {
            buff.append(fldStrArr[i]);
        }
        return String.valueOf(buff);
    }

    /**
     * {@inheritDoc}
     */
    protected String fieldDecoration(FieldMetaData fmeta, String value)
    {
        // 固定長にフォーマットします。
        int len = fmeta.getByteLength();
        String rString = value;
        try
        {
            String charset = getStoreMetaData().getCharset();
            byte[] bytevalue = value.getBytes(charset);
            int valueLen = bytevalue.length;

            if (valueLen < len)
            {
                try
                {
                    rString = concatByte(value, len, ' ');
                }
                catch (UnsupportedEncodingException e)
                {
                    // usually it will not occur.
                    throw new RuntimeException(e);
                }
            }
            else if (valueLen > len)
            {
                rString = new String(bytevalue, 0, len, charset);
            }
        }
        catch (UnsupportedEncodingException e1)
        {
            // TODO
            e1.printStackTrace();
            // サポートされていないキャラクタセットが指定されました。
            // RmiMsgLogClient.write(new TraceHandler(999999, e), getClass().getName()) ;
        }
        return rString;
    }

    /**
     * {@inheritDoc}
     */
    protected void alignField(String[] values)
    {
        // 固定長部分の不要な空白などを取り除きます。
        for (int i = 0; i < values.length; i++)
        {
            String value = values[i];
            if (value.endsWith(" "))
            {
                values[i] = value.trim();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    protected String formatNumber(FieldMetaData fmeta, Object value)
    {
        String pattern = createPattern(fmeta.getLength());
        DecimalFormat fmt = new DecimalFormat(pattern);

        if (value == null)
        {
            return fmt.format(new Integer(0));
        }
        String fvalue = fmt.format(value);
        return fvalue;
    }

    /**
     * 長さ定義より成形パターンを作成します。
     * @param lenDef 長さ定義
     * @return 成形パターン文字列
     */
    private String createPattern(float lenDef)
    {
        int integ = getIntegerLength(lenDef);
        int pinteg = getPointLength(lenDef);

        StringBuffer plusBuff = new StringBuffer();
        StringBuffer minusBuff = new StringBuffer();
        for (int i = 0; i < integ; i++)
        {
            String pptn = "0";
            String mptn = "0";
            if (i == 0)
            {
                mptn = "-";
            }
            plusBuff.append(pptn);
            minusBuff.append(mptn);
        }

        for (int i = 0; i < pinteg; i++)
        {
            if (i == 0)
            {
                plusBuff.append(".");
                minusBuff.append(".");
            }

            String pptn = "0";
            String mptn = "0";
            plusBuff.append(pptn);
            minusBuff.append(mptn);
        }

        plusBuff.append(";");
        plusBuff.append(minusBuff);

        return String.valueOf(plusBuff);
    }

    /**
     * {@inheritDoc}
     */
    protected String[] splitRecord(byte[] rec)
            throws UnsupportedEncodingException
    {
        StoreMetaData smeta = getStoreMetaData();
        String charset = smeta.getCharset();
        FieldMetaData[] fldMetaArr = sortFields(smeta);
        String[] rValues = new String[fldMetaArr.length];

        byte[] srcByteArr = rec;

        int start = 0;
        for (int i = 0; i < rValues.length; i++)
        {
            FieldMetaData fmeta = fldMetaArr[i];

            float defLen = fmeta.getLength();
            int iLen = getTotalLength(defLen);
            rValues[i] = new String(srcByteArr, start, iLen, charset);
            start += iLen;
        }

        return rValues;
    }

    /**
     * int型で長さを取得します。
     * @param length 長さ
     * @return int型の長さ
     */
    protected int getIntegerLength(float length)
    {
        int iLen = (new Float(length)).intValue();
        return iLen;
    }

    /**
     * 長さ指定より小数部のサイズを取得します。
     * @param length 長さ指定
     * @return 小数部のサイズ
     */
    protected int getPointLength(float length)
    {
        String defStr = String.valueOf(length);
        String pin = defStr.substring(defStr.indexOf(".") + 1);
        int pinteg = Integer.parseInt(pin);
        return pinteg;
    }

    /**
     * 長さ指定で指定されたトータルのサイズを取得します。
     * @param length 長さ指定
     * @return トータルのサイズ
     */
    protected int getTotalLength(float length)
    {
        int iLen = getIntegerLength(length);
        int pLen = getPointLength(length);
        if (pLen > 0)
        {
            iLen += (pLen + 1); // add for period length (=1)
        }
        return iLen;
    }

    /**
     * {@inheritDoc}
     */
    protected void onAfterParse(String[] values)
    {
    }

    /**
     * {@inheritDoc}
     */
    protected byte[] onBeforeParse(byte[] rec)
    {
        return rec;
    }

    /**
     * 指定された文字列を元の文字列に長さに達するまで追加します。
     * @param src 元の文字列
     * @param length 長さ
     * @param append 追加文字
     * @return 文字追加後の文字列
     * @throws UnsupportedEncodingException
     */
    protected String concatByte(String src, int length, char append)
            throws UnsupportedEncodingException
    {
        String rString = "";

        int srclen = src.getBytes(getStoreMetaData().getCharset()).length;
        if (srclen < length)
        {
            int addb = length - srclen;
            StringBuffer buff = new StringBuffer(length);
            buff.append(src);
            for (int i = 0; i < addb; i++)
            {
                buff.append(append);
            }
            rString = String.valueOf(buff);
        }

        return rString;
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
        return "$Id: FixedRecordFormatter.java 87 2008-10-04 03:07:38Z admin $";
    }
}

// $Id: AbstractRecordFormatter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.file.record;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.RecordFormatException;
import jp.co.daifuku.wms.handler.field.FieldMetaData;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.field.validator.ValidateError;
import jp.co.daifuku.wms.handler.field.validator.ValidateException;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * ファイルレコードのフォーマッタクラスのためのスーパークラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class AbstractRecordFormatter
        implements RecordFormatter
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** タイムスタンプのデフォルトフォーマット */
    public static final String TIMESTAMP_FORMAT = "yyyyMMdd HH:mm:ss";

    private static final String FILE_CLASS_CSV = FileHandler.FILE_CLASS_CSV;

    private static final String FILE_CLASS_FIXED_CSV = FileHandler.FILE_CLASS_FIXED_CSV;

    private static final String FILE_CLASS_FIXED = FileHandler.FILE_CLASS_FIXED;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private StoreMetaData _storeMetaData;

    private FileHandler _fileHandler;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    private byte[] _parseRec;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ハンドラを指定してインスタンスを生成します。
     * @param handler このフォーマッタを使用するハンドラ
     */
    protected AbstractRecordFormatter(FileHandler handler)
    {
        setFileHandler(handler);
        Entity ent = handler.getTargetEntity();
        setStoreMetaData(ent.getStoreMetaData());
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * ストアクラスから該当するレコードフォーマッタインスタンスを返します。
     * @param handler フォーマッタを使用するハンドラ
     * @return レコードフォーマッタインスタンス
     */
    public static RecordFormatter getInstance(FileHandler handler)
    {
        String sClass = handler.getStoreMetaData().getStoreClass();
        RecordFormatter fmtr = null;
        if (FILE_CLASS_CSV.equals(sClass))
        {
            fmtr = new CSVRecordFormatter(handler);
        }
        else if (FILE_CLASS_FIXED_CSV.equals(sClass))
        {
            fmtr = new FixedCSVRecordFormatter(handler);

        }
        else if (FILE_CLASS_FIXED.equals(sClass))
        {
            fmtr = new FixedRecordFormatter(handler);
        }
        return fmtr;
    }

    /**
     * {@inheritDoc}
     */
    public String format(Entity ent)
    {
        StoreMetaData smeta = getStoreMetaData();
        String sname = smeta.getName();
        FieldMetaData[] fldmetaArr = sortFields(smeta);

        List fldStrList = new ArrayList();
        for (int i = 0; i < fldmetaArr.length; i++)
        {
            FieldMetaData fmeta = fldmetaArr[i];
            if (!fmeta.isEnabled())
            {
                continue;
            }
            String fname = fmeta.getName();
            Object value = ent.getValue(new FieldName(sname, fname), null);

            String strValue = objectToString(fmeta, value);

            fldStrList.add(fieldDecoration(fmeta, strValue));
        }

        String recStr = unionFields((String[])fldStrList.toArray(new String[fldStrList.size()]));
        return recStr;
    }

    /**
     * {@inheritDoc}
     */
    public Entity parse(byte[] rec)
            throws RecordFormatException,
                UnsupportedEncodingException
    {
        // パース対象レコードを保存します
        setParseRec(rec);

        // pre processing in sub-class
        byte[] pprec = onBeforeParse(rec);

        // split a record
        String[] values = splitRecord(pprec);
        // remove a quotes
        alignField(values);

        // post processing in sub-class
        onAfterParse(values);

        // create the entity
        try
        {
            Entity ent = buildEntity(values);

            // set original record to the entity
            setRecordToEntity(ent, rec);

            return ent;
        }
        catch (ValidateException e)
        {
            e.setCharSet(getCharset().name());
            e.setRecord(rec);
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    public int length()
    {
        return 0;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @return storeMetaDataを返します。
     */
    public StoreMetaData getStoreMetaData()
    {
        return _storeMetaData;
    }

    /**
     * @param storeMetaData storeMetaDataを設定します。
     */
    public void setStoreMetaData(StoreMetaData storeMetaData)
    {
        _storeMetaData = storeMetaData;
    }

    /**
     * @return fileHandlerを返します。
     */
    public FileHandler getFileHandler()
    {
        return _fileHandler;
    }

    /**
     * @param fileHandler fileHandlerを設定します。
     */
    public void setFileHandler(FileHandler fileHandler)
    {
        _fileHandler = fileHandler;
    }

    /**
     * parseRecを返します。
     * @return parseRecを返します。
     */
    protected byte[] getParseRec()
    {
        return _parseRec;
    }

    /**
     * parseRecを設定します。
     * @param parseRec parseRec
     */
    protected void setParseRec(byte[] parseRec)
    {
        _parseRec = parseRec;
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
     * エンティティにオリジナルレコードの内容をセットします。
     * @param e セット先のエンティティ
     * @param rec レコード内容
     * @throws UnsupportedEncodingException エンコーディング指定が不正
     */
    protected void setRecordToEntity(Entity e, byte[] rec)
            throws UnsupportedEncodingException
    {
        String orgRec = new String(rec, getCharset().name());
        e.setValue(FileHandler.FIELD_ORIGINAL_RECORD, orgRec, false);
    }

    /**
     * フィールドを位置の順にソートします。
     * @param smeta ストアメタデータ
     * @return ソートされたフィールドメタデータ
     */
    protected FieldMetaData[] sortFields(StoreMetaData smeta)
    {
        FieldMetaData[] fmetaArr = smeta.getFieldMetaDatas();
        IndexedField[] ifArr = new IndexedField[fmetaArr.length];
        for (int i = 0; i < fmetaArr.length; i++)
        {
            ifArr[i] = new IndexedField(fmetaArr[i]);
        }
        Arrays.sort(ifArr);
        for (int i = 0; i < fmetaArr.length; i++)
        {
            fmetaArr[i] = ifArr[i].getFieldMetaData();
        }
        return fmetaArr;
    }

    /**
     * 文字列配列から、エンティティを組み立てて返します。
     * @param values 元データの文字列配列
     * @return 生成されたエンティティ
     * @throws RecordFormatException パースに失敗したときスローされます。
     */
    protected Entity buildEntity(String[] values)
            throws RecordFormatException
    {
        // create entity in sub-class
        Entity ent = getFileHandler().createEntity();

        StoreMetaData smeta = getStoreMetaData();
        FieldMetaData[] fmetaArr = sortFields(smeta);
        String storeName = smeta.getName();

        if (fmetaArr.length != values.length)
        {
            RecordFormatException rfe = new RecordFormatException("It doesn't agree to stores.xml");
            rfe.setRecord(getParseRec(), getCharset().name());
            throw rfe;
        }

        for (int i = 0; i < fmetaArr.length; i++)
        {
            String strValue = values[i];

            FieldMetaData fmeta = fmetaArr[i];
            FieldName fname = new FieldName(storeName, fmeta.getName());

            int ftype = fmeta.getType();
            switch (ftype)
            {
                case FieldMetaData.TYPE_NUMBER:
                    BigDecimal bdValue = toBigDecimal(strValue, fmeta, fname);
                    ent.setValue(fname, bdValue);
                    break;
                case FieldMetaData.TYPE_STRING:
                    ent.setValue(fname, strValue);
                    break;
                case FieldMetaData.TYPE_TIMESTAMP:
                    Date dtvalue = toDate(strValue, fmeta, fname);
                    ent.setValue(fname, dtvalue);
                    break;
                default: // Unknown type is disregarded.
                    break;
            }
        }

        return ent;
    }

    /**
     * 文字列をBigDecimalに変換します。
     * 
     * @param strValue 対象文字列
     * @param fmeta 対象フィールドのメタ情報
     * @param fname 対象フィールド
     * @return BigDecimal値
     */
    protected BigDecimal toBigDecimal(String strValue, FieldMetaData fmeta, FieldName fname)
    {
        if (null == strValue)
        {
            return null;
        }

        if (0 == strValue.trim().length())
        {
            return null; 
        }

        try
        {
            return new BigDecimal(strValue);
        }
        catch (NumberFormatException e)
        {
            // error to convert to big decimal.
            throw new ValidateException(fname, strValue, ValidateError.RETURN_TYPE_ERROR, fmeta);
        }
    }

    /**
     * データで与えられた文字列をフォーマット文字列に従って成形して返します。
     * @param fmeta 対象フィールドのメタ情報
     * @param value データ
     * @param fname 対象フィールド
     * @return Date型に成形されたデータ
     */
    protected Date toDate(String value, FieldMetaData fmeta, FieldName fname)
    {
        if (null == value)
        {
            return null;
        }

        if (0 == value.trim().length())
        {
            return null;
        }

        try
        {
            String innerFormat = fmeta.getFormatPattern();
            if (innerFormat == null || innerFormat.length() == 0)
            {
                innerFormat = TIMESTAMP_FORMAT;
            }
            SimpleDateFormat fmt = new SimpleDateFormat(innerFormat);
            return fmt.parse(value);
        }
        catch (ParseException e)
        {
            // error to convert to date.
            throw new ValidateException(fname, value, ValidateError.RETURN_TYPE_ERROR, fmeta);
        }
    }

    /**
     * エンティティのオブジェクトデータを文字列に変換します。
     * @param fmeta フィールドのメタデータ
     * @param value オブジェクトデータ
     * @return 文字列化されたデータ
     */
    protected String objectToString(FieldMetaData fmeta, Object value)
    {
        int ftype = fmeta.getType();

        String rvalue = "";
        switch (ftype)
        {
            case FieldMetaData.TYPE_NUMBER:
                rvalue = formatNumber(fmeta, value);
                break;
            case FieldMetaData.TYPE_STRING:
                rvalue = (value == null) ? ""
                                        : String.valueOf(value);
                break;
            case FieldMetaData.TYPE_TIMESTAMP:
                if (fmeta.isAutoUpdate())
                {
                    value = new Date();
                }
                if (value instanceof Date)
                {
                    rvalue = dateToString(fmeta.getFormatPattern(), (Date)value);
                }
                break;
            default: // Unknown type is disregarded.
                break;
        }

        return rvalue;
    }

    /**
     * 数値データを文字列化します。
     * @param fmeta フィールドのメタデータ
     * @param value オブジェクトデータ
     * @return 文字列化されたデータ
     */
    protected String formatNumber(FieldMetaData fmeta, Object value)
    {
        if (value == null)
        {
            return "0";
        }
        return String.valueOf(value);
    }

    /**
     * 現在のキャラクタセットを返します。
     * @return キャラクタセット。<br>
     * 正しいキャラクタセットでない場合は、デフォルトキャラクタセットが返されます。
     */
    protected Charset getCharset()
    {
        Charset cuCharset = Charset.defaultCharset();
        StoreMetaData smeta = getStoreMetaData();
        if (smeta == null)
        {
            return cuCharset;
        }
        String cname = smeta.getCharset();
        try
        {
            return Charset.forName(cname);
        }
        catch (Exception e)
        {
            return cuCharset;
        }
    }

    /**
     * レコードのパースを行って、文字列配列になったあとの処理を記述します。<br>
     * サブクラスのためのフックメソッドです。
     * @param values フィールド分割されたあとの文字列配列
     */
    protected abstract void onAfterParse(String[] values);

    /**
     * レコードのパースを行う前の文字列処理を行います。<br>
     * サブクラスのためのフックメソッドです。
     * @param rec ファイルレコード
     * @return 処理後の文字列
     */
    protected abstract byte[] onBeforeParse(byte[] rec);

    /**
     * 複数のフィールド文字列を一つのレコードに組み立てます。
     * @param fldStrArr フィールドの文字列
     * @return レコードに書き込むための文字列
     */
    protected abstract String unionFields(String[] fldStrArr);

    /**
     * フィールドごとの文字列化や装飾を行います。(文字列の囲み追加等)
     * @param fmeta フィールドメタデータ
     * @param value エンティティ内のデータ
     * @return 文字列化されたフィールド内容
     */
    protected abstract String fieldDecoration(FieldMetaData fmeta, String value);

    /**
     * 囲み文字を取り除いたり、文字列長を整えたりします。
     * @param values フィールド分割された文字列
     */
    protected abstract void alignField(String[] values);

    /**
     * ファイルレコードの文字列を分割します。
     * @param rec ファイルレコード
     * @return 分割後文字列の配列
     * @throws UnsupportedEncodingException 
     */
    protected abstract String[] splitRecord(byte[] rec)
            throws UnsupportedEncodingException;

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
        return "$Id: AbstractRecordFormatter.java 87 2008-10-04 03:07:38Z admin $";
    }

    /**
     * データで与えられたDate型の値をフォーマット文字列に従って成形して返します。
     * @param format フォーマット文字列
     * @param value データ
     * @return String型に成形されたデータ
     */
    protected String dateToString(String format, Date value)
    {
        if (null == value)
        {
            return null;
        }

        String innerFormat = format;
        if (format == null || format.length() == 0)
        {
            innerFormat = TIMESTAMP_FORMAT;
        }
        SimpleDateFormat fmt = new SimpleDateFormat(innerFormat);
        return fmt.format(value);
    }

    /**
     * 項目の位置情報を保持するフィールド情報クラスです。
     *
     * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
     * @author  ss
     * @author  Last commit: $Author: admin $
     */
    static class IndexedField
            implements Comparable
    {
        private FieldMetaData _fieldMetaData;

        private int _index;

        IndexedField(FieldMetaData fmeta)
        {
            setFieldMetaData(fmeta);
        }

        /**
         * @return fieldMetaDataを返します。
         */
        public FieldMetaData getFieldMetaData()
        {
            return _fieldMetaData;
        }

        /**
         * @param fieldMetaData fieldMetaDataを設定します。
         */
        public void setFieldMetaData(FieldMetaData fieldMetaData)
        {
            _fieldMetaData = fieldMetaData;
            _index = fieldMetaData.getPosition();
        }

        /**
         * ソートのためにIndexedFieldを位置によって比較します。
         * @param arg0
         * @return 位置関係によって、負値、正値 を返します。<br>
         * 比較対象が同じ位置関係を持つことはないので、その場合は
         * RuntimeExceptionが発生します。
         */
        public int compareTo(Object arg0)
        {
            if (arg0 instanceof IndexedField)
            {
                IndexedField p1 = (IndexedField)arg0;

                int diff = _index - p1._index;
                if (diff == 0)
                {
                    throw new RuntimeException("Invalid store metadata, Position duplicated.");
                }
                return diff;
            }
            return 0;
        }
    }
}

//$Id: StoreMetaData.java 6495 2009-12-16 06:03:39Z okamura $
package jp.co.daifuku.wms.handler.field;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * テーブルやファイルのメタ情報を管理するクラスです。
 *
 * @version $Revision: 6495 $, $Date: 2009-12-16 15:03:39 +0900 (水, 16 12 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public class StoreMetaData
        extends AbstractMetaData
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    static final int MAX_REC_UNDEFINED = -1;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** Store type (Database) */
    public static final String STORE_TYPE_DB = "db";

    /** Store type (File) */
    public static final String STORE_TYPE_FILE = "file";

    /** Store type (Ring File) */
    public static final String STORE_TYPE_FILE_RING = "ring_file";

    /** Store type (Ring File with read pointer) */
    public static final String STORE_TYPE_FILE_RP_RING = "rp_ring_file";

    /** デフォルトキャラクタセット */
    public static final String DEFAULT_CHARSET = "SJIS";

    /** デフォルト区切り文字 */
    public static final String DEFAULT_SPLIT_STRING = ",";

    /** デフォルト囲み文字 */
    public static final String DEFAULT_QUOTE_STRING = "\"";

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    /** ロード対象ストア名 */
    // private String p_targetStoreName ;
    /** 注釈 */
    private String p_descriptoin = null;

    /** ストアタイプ (db or file) */
    private String p_type = null;

    /** ストアクラス (oracle, fixed etc.) */
    private String p_storeClass = null;

    /** 最大レコード数 (RINGファイル用) */
    private int p_maxRecordNumber = MAX_REC_UNDEFINED;

    /** キャラクタセット名 */
    // private String p_charset = Charset.defaultCharset().name() ;
    private String p_charset = null;

    /** 区切り文字(CSV用) */
    private String p_splitString = null;

    /** 文字囲み(CSV用) */
    private String p_quoteString = null;

    /** フィールドメタデータ */
    private Map<String, FieldMetaData> p_fieldMetaData = null;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 初期化してインスタンスを生成します。
     */
    public StoreMetaData()
    {
        p_fieldMetaData = new HashMap<String, FieldMetaData>();
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 保持している内容の文字列表現を返します。
     * @return 文字列表現
     */
    @Override
    public String toString()
    {
        StringBuffer buff = new StringBuffer(super.toString());

        buff.append(",Desc:");
        buff.append(getDescriptoin());
        buff.append(",Type:");
        buff.append(p_type);
        buff.append(",Class:");
        buff.append(p_storeClass);

        return buff.toString();
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * すべての"有効な"fieldMetaDataを返します。
     * @return すべての"有効な"fieldMetaDataを返します。
     */
    public FieldMetaData[] getFieldMetaDatas()
    {
        // 引数付きを呼び出すように変更 2009/12/15
        return getFieldMetaDatas(true);
    }

    /**
     * フィールド情報を返します。
     * 
     * @param enableOnly true:有効のみ, false:無効も含む
     * @return fieldMetaDataを返します。
     * @since 2009/12/15
     */
    public FieldMetaData[] getFieldMetaDatas(boolean enableOnly)
    {
        FieldMetaData[] metaDatas = p_fieldMetaData.values().toArray(new FieldMetaData[0]);
        List<FieldMetaData> fldMetaList = new ArrayList<FieldMetaData>();
        for (FieldMetaData metaData : metaDatas)
        {
            if ((!enableOnly) || metaData.isEnabled())
            {
                // "すべて対象" が指定されているか、
                // "フィールドが有効"ならリストに含める
                fldMetaList.add(metaData);
            }
        }
        return fldMetaList.toArray(new FieldMetaData[0]);
    }

    /**
     * @param name フィールド名
     * @return fieldMetaDataを返します。存在しないときはnull.
     */
    public FieldMetaData getFieldMetaData(String name)
    {
        return p_fieldMetaData.get(name.toUpperCase());
    }

    /**
     * @param fieldMetaData fieldMetaDataを設定します。
     */
    public void addFieldMetaData(FieldMetaData fieldMetaData)
    {
        p_fieldMetaData.put(fieldMetaData.getName(), fieldMetaData);
    }

    /**
     * @param fmetaArr fieldMetaDataを設定します。
     */
    public void addFieldMetaDatas(FieldMetaData[] fmetaArr)
    {
        for (FieldMetaData element : fmetaArr)
        {
            addFieldMetaData(element);
        }
    }

    /**
     * @param fmetaArr fieldMetaDataを入れ換えます。
     */
    protected void replaceFieldMetaDatas(FieldMetaData[] fmetaArr)
    {
        p_fieldMetaData.clear();
        for (FieldMetaData element : fmetaArr)
        {
            addFieldMetaData(element);
        }
    }

    /**
     * @return descriptoinを返します。
     */
    public String getDescriptoin()
    {
        return p_descriptoin;
    }

    /**
     * @param descriptoin descriptoinを設定します。
     */
    public void setDescriptoin(String descriptoin)
    {
        p_descriptoin = descriptoin;
    }

    /**
     * @return storeClassを返します。
     */
    public String getStoreClass()
    {
        return p_storeClass;
    }

    /**
     * @param storeClass storeClassを設定します。
     */
    public void setStoreClass(String storeClass)
    {
        if (storeClass == null)
        {
            return;
        }
        p_storeClass = storeClass.toLowerCase();
    }

    /**
     * @return typeを返します。
     */
    public String getType()
    {
        return p_type;
    }

    /**
     * @param type typeを設定します。
     */
    public void setType(String type)
    {
        if (type == null)
        {
            return;
        }
        p_type = type.toLowerCase();
    }

    /**
     * @return quoteStringを返します。
     */
    public String getQuoteString()
    {
        String quote = (p_quoteString == null) ? DEFAULT_QUOTE_STRING
                                              : p_quoteString;
        return quote;
    }

    /**
     * @param quoteString quoteStringを設定します。
     */
    public void setQuoteString(String quoteString)
    {
        p_quoteString = quoteString;
    }

    /**
     * 囲み文字が設定されているかチェックします。
     * 
     * @return 設定されているときtrue.
     */
    public boolean hasQuoteString()
    {
        return ((p_quoteString != null) && (p_quoteString.length() > 0));
    }

    /**
     * @return splitStringを返します。
     */
    public String getSplitString()
    {
        String split = (p_splitString == null) ? DEFAULT_SPLIT_STRING
                                              : p_splitString;
        return split;
    }

    /**
     * @param splitString splitStringを設定します。
     */
    public void setSplitString(String splitString)
    {
        if (splitString == null)
        {
            return;
        }

        String asstr = splitString;
        if (splitString.charAt(0) == '\\')
        {
            switch (splitString.charAt(1))
            {
                case 't':
                    asstr = "\t";
            }
        }
        p_splitString = asstr;
    }

    /**
     * 区切り文字が設定されているかチェックします。
     * 
     * @return 設定されているときtrue.
     */
    public boolean hasSplitString()
    {
        return ((p_splitString != null) && (p_splitString.length() > 0));
    }

    /**
     * @return maxRecordを返します。
     */
    public int getMaxRecordNumber()
    {
        return p_maxRecordNumber;
    }

    /**
     * @param maxRecord maxRecordを設定します。
     */
    public void setMaxRecordNumber(int maxRecord)
    {
        p_maxRecordNumber = maxRecord;
    }

    /**
     * 最大レコード数が設定されているかチェックします。
     * 
     * @return 設定されているときtrue.
     */
    public boolean hasMaxRecordNumber()
    {
        return (p_maxRecordNumber != MAX_REC_UNDEFINED);
    }

    /**
     * @return charsetを返します。
     */
    public String getCharset()
    {
        String charset = (p_charset == null) ? DEFAULT_CHARSET
                                            : p_charset;
        return charset;
    }

    /**
     * @param charset charsetを設定します。
     */
    public void setCharset(String charset)
    {
        p_charset = Charset.forName(charset).name();
    }

    /**
     * キャラクタセットが設定されているかチェックします。
     * 
     * @return 設定されているときtrue.
     */
    public boolean hasCharset()
    {
        return ((p_charset != null) && (p_charset.length() > 0));
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
        return "$Id: StoreMetaData.java 6495 2009-12-16 06:03:39Z okamura $";
    }
}

//$Id: AbstractEntity.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler;

/*
 * Copyright(c) 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.daifuku.wms.handler.field.FieldMetaData;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.field.validator.FieldValidator;
import jp.co.daifuku.wms.handler.field.validator.ValidateError;
import jp.co.daifuku.wms.handler.field.validator.ValidateException;

/**
 * 項目名と、その値の組み合わせを保持するためのエンティティクラスの
 * 基本となる仮想クラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class AbstractEntity
        implements Entity, Serializable
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    /** フィールドと値(Object)を保持するマップ */
    private Map p_valueMap;

    /** このエンティティが管理するフィールド一覧 */
    private List p_fieldList;

    /** このエンティティが管理するストア名 */
    private String p_storeName = "";

    // private StoreMetaData p_storeMetaData ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * エンティティインスタンスを生成します。
     */
    public AbstractEntity()
    {
        p_valueMap = createValueMap(null);
        p_fieldList = createFieldList(null);

        buildFieldInfo();
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public ValidateError[] validateValuesOnly()
    {
        return validate(true);
    }

    /**
     * {@inheritDoc}
     */
    public ValidateError[] validate()
    {
        return validate(false);
    }

    /**
     * {@inheritDoc}
     */
    public int validate(FieldName key)
    {
        Object value = getValueMap().get(key);
        return validate(key, value);
    }

    /**
     * {@inheritDoc}
     */
    public int validate(FieldName key, Object value)
    {
        FieldMetaData fmeta = getStoreMetaData().getFieldMetaData(key.getName());
        if (fmeta == null)
        {
            throw new RuntimeException(key + " is not defined.");
        }
        int ret = fmeta.getValidator().validate(value, fmeta, false);

        return ret;
    }

    /**
     * {@inheritDoc}
     */
    public void clear()
    {
        getValueMap().clear();
    }

    /**
     * インスタンスのクローンを返します。<br>
     * 値のコピーはリスト・マップ単位で行います。
     * @return インスタンスのコピー
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone()
    {
        try
        {
            AbstractEntity cloneObj = (AbstractEntity)super.clone();
            // copy field list
            cloneObj.p_fieldList = createFieldList(p_fieldList);
            cloneObj.p_valueMap = createValueMap(p_valueMap);
            cloneObj.p_storeName = p_storeName;

            return cloneObj;
        }
        catch (CloneNotSupportedException e)
        {
            throw new RuntimeException("Internal error. Entity does not support clone().");
        }
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void setValue(FieldName field, Object value)
    {
        setValue(field, value, true);
    }

    /**
     * {@inheritDoc}
     */
    public void setValue(FieldName field, Object value, boolean withValidate)
    {
        FieldName ffield = fixField(field);

        Object sValue = value;
        if (value instanceof BigDecimal)
        {
            BigDecimal bdec = (BigDecimal)value;
            sValue = roundScale(ffield, bdec);
        }
        if (withValidate)
        {
            int error = 0;
            if (error != ValidateError.RETURN_VALID)
            {
                throw new ValidateException(ffield, sValue, error, getStoreMetaData().getFieldMetaData(field.getName()));
            }
        }
        Map valueMap = getValueMap();
        valueMap.put(ffield, sValue);
    }

    /**
     * {@inheritDoc}
     */
    public Object getValue(FieldName field)
    {
        return getValue(field, null);
    }

    /**
     * {@inheritDoc}
     */
    public Object getValue(FieldName field, Object defaultValue)
    {
        Object value = getValueMap().get(field);
        if (value == null)
        {
            return getValueByName(field, defaultValue);
        }
        return value;
    }

    /**
     * フィールドのハッシュコードからではなく、名前だけで同一のフィールドが
     * あれば取得して返します。
     *
     * @param field 取得対象フィールド
     * @param defaultValue 見つからなかった場合のデフォルト値
     * @return フィールド値
     */
    protected Object getValueByName(FieldName field, Object defaultValue)
    {
        Set<Map.Entry> eSet = getValueMap().entrySet();
        for (Map.Entry entry : eSet)
        {
            Object key = entry.getKey();
            if (key instanceof FieldName)
            {
                FieldName fld = (FieldName)key;
                String vcname = fld.getName();
                if (vcname.equals(field.getName()) || vcname.equals(field.getAlias()))
                {
                    Object value = entry.getValue();
                    if (null != value)
                    {
                        return value;
                    }
                    else
                    {
                        return defaultValue;
                    }
                }
            }
        }
        return defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    public BigDecimal getBigDecimal(FieldName field, BigDecimal defaultValue)
    {
        Object val = getValue(field, null);
        if (val instanceof BigDecimal)
        {
            return (BigDecimal)val;
        }
        else if (val == null)
        {
            return defaultValue;
        }
        throw new InvalidTypeException(field, val);
    }

    /**
     * {@inheritDoc}
     */
    public BigDecimal getBigDecimal(FieldName field)
    {
        return getBigDecimal(field, null);
    }

    /**
     * {@inheritDoc}
     */
    public Date getDate(FieldName field, Date defaultValue)
    {
        Object val = getValue(field, null);
        if (val instanceof Date)
        {
            return (Date)val;
        }
        else if (val == null)
        {
            return defaultValue;
        }
        throw new InvalidTypeException(field, val);
    }

    /**
     * {@inheritDoc}
     */
    public Date getDate(FieldName field)
    {
        return getDate(field, null);
    }

    /**
     * {@inheritDoc}
     */
    public Map getValueMap()
    {
        return p_valueMap;
    }

    /**
     * @param fieldList fieldListを設定します。
     */
    protected void setFieldList(List fieldList)
    {
        p_fieldList = fieldList;
    }

    /**
     * カラム名リストを返します。
     *
     * @return List フィールド名のリスト
     */
    public List getFieldList()
    {
        return p_fieldList;
    }

    /**
     * {@inheritDoc}
     */
    public FieldName[] getFieldNames()
    {
        return (FieldName[])getFieldList().toArray(new FieldName[0]);
    }

    /**
     * {@inheritDoc}
     */
    public String getStoreName()
    {
        return p_storeName;
    }

    /**
     * エンティティが対応するストア名をセットします。
     *
     * @param storeName ストア名
     */
    protected void setStoreName(String storeName)
    {
        p_storeName = storeName;
    }

    /*
     * @param storeMetaData storeMetaDataを設定します。
     public void setStoreMetaData(StoreMetaData storeMetaData)
     {
     p_storeMetaData = storeMetaData ;
     }
     */

    /**
     * @param valueMap valueMapを設定します。
     */
    public void setValueMap(Map valueMap)
    {
        p_valueMap = valueMap;
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
     * フィールドの値をチェックします。
     *
     * @param valueOnly セットされている値だけをチェックするときは true.
     * @return エラーのあるフィールドの列挙,エラーなしの時は要素0.
     */
    protected ValidateError[] validate(boolean valueOnly)
    {
        List msgList = new ArrayList();

        FieldMetaData[] fmetaArr = getStoreMetaData().getFieldMetaDatas();
        for (int i = 0; i < fmetaArr.length; i++)
        {
            FieldMetaData fmeta = fmetaArr[i];
            String fldName = fmeta.getName().toUpperCase();
            FieldName field = new FieldName(getStoreName(), fldName);

            //Object fldValue = getValueMap().get(field);
            Object fldValue = getValue(field);
            int ret = fmeta.getValidator().validate(fldValue, fmeta, valueOnly);

            if (ret != FieldValidator.RETURN_VALID)
            {
                msgList.add(new ValidateError(field, fldValue, ret, fmeta));
            }
        }

        return (ValidateError[])msgList.toArray(new ValidateError[0]);
    }

    /**
     * メタデータから、フィールド情報を構築します。
     */
    protected void buildFieldInfo()
    {
        StoreMetaData meta = getStoreMetaData();

        if (meta == null)
        {
            return;
        }
        // setStoreMetaData(meta) ;

        String storeName = meta.getName();
        setStoreName(storeName);

        FieldMetaData[] fmetaArr = meta.getFieldMetaDatas();

        for (int i = 0; i < fmetaArr.length; i++)
        {
            FieldMetaData fmeta = fmetaArr[i];
            FieldName field = new FieldName(storeName.toUpperCase(), fmeta.getName().toUpperCase());
            getFieldList().add(field);
        }
    }

    /**
     * ストア名が空の場合に、このEntityのストア名をセットします。
     *
     * @param field
     * @return 補完されたFieldName
     */
    protected FieldName fixField(FieldName field)
    {
        /*
         String sname = field.getStoreName() ;
         if (sname == null || sname.length() == 0)
         {
         field.setStoreName(getStoreName()) ;
         }*/
        return field;
    }

    /**
     * BigDecimalの精度をメタデータで指定されている制度に丸めます。
     *
     * @param field 対象フィールド
     * @param bdec 対象値
     * @return 丸めた結果のBigDecimal
     */
    protected BigDecimal roundScale(FieldName field, BigDecimal bdec)
    {
        BigDecimal rvalue = bdec;

        StoreMetaData smeta = getStoreMetaData();
        FieldMetaData fmeta = getStoreMetaData().getFieldMetaData(field.getName());
        if (smeta == null || fmeta == null)
        {
            return bdec;
        }
        int[] scales = fmeta.getScales();

        // 小数部桁数チェック
        int pointdec = bdec.scale();
        if (pointdec > scales[1])
        {
            rvalue = bdec.setScale(scales[1], BigDecimal.ROUND_HALF_UP);
        }

        return rvalue;
    }

    /**
     * フィールド一覧用のリストを生成します。
     * @param src 元情報がある場合はセットします。無い場合は null をセットしてください。
     * @return フィールド一覧用のリスト<br>
     * src の内容がある場合はコピーされています。
     */
    protected List createFieldList(Collection src)
    {
        List actList = (src == null) ? new ArrayList()
                : new ArrayList(src);
        return Collections.synchronizedList(actList);
    }

    /**
     * 値保持用のマップを生成します。
     * @param src 元情報がある場合はセットします。無い場合は null をセットしてください。
     * @return 値保持用のマップ<br>
     * src の内容がある場合はコピーされています。
     */
    protected Map createValueMap(Map src)
    {
        Map actMap = (src == null) ? new HashMap()
                : new HashMap(src);
        return Collections.synchronizedMap(actMap);
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * エンティティの内容を文字列として返します。
     * @return エンティティの内容
     */
    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        buf.append(getStoreName());

        Set entrySet = getValueMap().entrySet();
        Iterator entryIt = entrySet.iterator();
        boolean first = true;
        while (entryIt.hasNext())
        {
            Map.Entry entry = (Map.Entry)entryIt.next();
            Object key = entry.getKey();
            Object value = entry.getValue();

            if (!first)
            {
                buf.append(",");
            }
            else
            {
                buf.append(":");
                first = false;
            }
            buf.append(key);
            buf.append("=");
            buf.append(value);
        }
        return String.valueOf(buf);
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AbstractEntity.java 87 2008-10-04 03:07:38Z admin $";
    }
}

// $Id: AbstractSQLAlterKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jp.co.daifuku.wms.handler.AbstractFindKey;
import jp.co.daifuku.wms.handler.Conditions;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.field.FieldMetaData;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.field.validator.FieldValidator;
import jp.co.daifuku.wms.handler.field.validator.ValidateError;
import jp.co.daifuku.wms.handler.field.validator.ValidateException;

/**
 * データベース用更新キーのための仮想クラスです。<br>
 * 各テーブル用の更新キークラスでは、このクラスを継承してgetStoreMetaData()
 * メソッドをオーバーライドし、各テーブル用のメタデータを返すようにしてください。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class AbstractSQLAlterKey
        extends AbstractFindKey
        implements SQLAlterKey
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private List _updateValueList = null;

    private Entity _updateValueEntity = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ストア名を指定してインスタンスを初期化します。
     * @param storename 対象ストア名
     */
    public AbstractSQLAlterKey(String storename)
    {
        setStoreName(storename);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public void setUpdateValues(Entity values)
    {
        _updateValueEntity = values;

        ValidateError[] errors = values.validateValuesOnly();
        if (errors.length != 0)
        {
            ValidateException ex = new ValidateException();
            ex.addValidateErrors(errors);
            throw ex;
        }

        // Check OK
        // The map has KEY:FieldName , VALUE: Object
        Map valueMap = values.getValueMap();
        FieldName[] fields = (FieldName[])valueMap.keySet().toArray(new FieldName[0]);
        for (int i = 0; i < fields.length; i++)
        {
            Object value = valueMap.get(fields[i]);
            setAdhocUpdateValue(fields[i], value);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setUpdateWithColumn(FieldName target, FieldName source, BigDecimal addValue)
    {
        Conditions srccond = new Conditions(source);
        srccond.setValue(addValue);
        setAdhocUpdateValue(target, srccond);
    }

    /**
     * {@inheritDoc}
     */
    public void clear()
    {
        super.clear();
        clearUpdateValues();
    }

    /**
     * {@inheritDoc}
     */
    public void clearUpdateValues()
    {
        setUpdateValueList(null);
    }

    /**
     * 更新対象データから、該当のカラムをクリアします。
     * 
     * @param field クリア対象カラム
     */
    public void clearUpdateValue(FieldName field)
    {
        Conditions cond = new Conditions(field);

        List list = getUpdateValueList();
        list.remove(cond);
    }

    /**
     * {@inheritDoc}
     */
    public ValidateError[] validate()
    {
        ValidateError[] errors = new ValidateError[0];
        if (_updateValueEntity != null)
        {
            errors = _updateValueEntity.validateValuesOnly();
        }
        return errors;
    }

    /**
     * {@inheritDoc}
     */
    public void setAdhocUpdateValue(FieldName field, Object value)
    {
        boolean validable = true;
        // Validate対象外のもの

        if (value instanceof Conditions)
        {
            validable = false;
        }

        // Validate対象なら内容のチェック
        if (validable)
        {
            StoreMetaData smeta = getStoreMetaData();

            // exception thrown if validate error occurs.
            ValidateException ex = validateField(field, value, smeta);
            if (ex != null)
            {
                throw ex;
            }
        }

        Conditions cond = new Conditions(field);
        cond.setValue(value);

        List list = getUpdateValueList();
        list.remove(cond);
        list.add(cond);
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public List getUpdateValueList()
    {
        if (_updateValueList == null)
        {
            _updateValueList = createUniqueList();
        }
        return _updateValueList;
    }

    /**
     * @param updateConditionList updateConditionListを設定します。
     */
    protected void setUpdateValueList(List updateConditionList)
    {
        _updateValueList = updateConditionList;
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
     * フィールドの内容を確認し、問題があれば例外を返します。
     * @param field チェック対象フィールド
     * @param value 設定内容データ
     * @param smeta 該当ストアメタデータ
     * @return 問題があればその内容を持つ例外を返します。<br>
     * 問題がなければ、nullを返します。
     */
    protected ValidateException validateField(FieldName field, Object value, StoreMetaData smeta)
    {
        if (smeta != null)
        {
            FieldMetaData fmeta = smeta.getFieldMetaData(field.getName());
            if (fmeta != null)
            {
                FieldValidator vd = fmeta.getValidator();
                if (vd != null)
                {
                    // validate and throw exception if validate error
                    int errcode = vd.validate(value, fmeta, true);
                    if (FieldValidator.RETURN_VALID != errcode)
                    {
                        return new ValidateException(field, value, errcode, fmeta);
                    }
                }
            }
        }
        return null;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * ストアメタデータを返します。
     * @return ストアメタデータ
     */
    public abstract StoreMetaData getStoreMetaData();

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AbstractSQLAlterKey.java 87 2008-10-04 03:07:38Z admin $";
    }
}

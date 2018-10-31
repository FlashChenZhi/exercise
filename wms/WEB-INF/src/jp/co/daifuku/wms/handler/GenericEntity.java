// $Id: GenericEntity.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Map;
import java.util.Set;

import jp.co.daifuku.wms.handler.db.AbstractSQLGenerator;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;

/**
 * 項目名と、その値の組み合わせを保持するための汎用エンティティクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Softecs
 * @author  Last commit: $Author: admin $
 */

public class GenericEntity
        extends AbstractEntity
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    private static final String METADATA_NAME = "GenericEntity";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * デフォルトコンストラクタ
     */
    public GenericEntity()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public StoreMetaData getStoreMetaData()
    {
        StoreMetaData metaData = new StoreMetaData();
        metaData.setName(METADATA_NAME);
        metaData.setStoreClass(AbstractSQLGenerator.DBMS_TYPE_ORACLE);
        return metaData;
    }

    /**
     * カラム名取得<br>
     * エンティティ内に保持しているカラム名の一覧を取得します。
     * @return カラム名(配列)
     */
    public String[] getColumnNames()
    {
        Map valueMap = getValueMap();
        Set entrySet = valueMap.keySet();
        Object[] entryArr = entrySet.toArray();
        String[] columnNames = new String[entryArr.length];
        for (int i = 0; i < entryArr.length; i++)
        {
            columnNames[i] = String.valueOf(entryArr[i]);
        }
        return columnNames;
    }

    /**
     * データ値取得<br>
     * カラム名で指定されたデータの値を取得します。
     * @param columnName カラム名
     * @return データ値
     */
    public Object getValue(String columnName)
    {
        return getValue(columnName, null);
    }

    /**
     * データ値取得<br>
     * カラム名で指定されたデータの値を取得します。<br>
     * データ値が未設定のときのデフォルト値を返します。
     * @param columnName カラム名
     * @param defaultValue Object 値が<code>null</code>の時に返すデフォルト値
     * @return データ値
     */
    public Object getValue(String columnName, Object defaultValue)
    {
        Object value = getValueMap().get(new FieldName(getStoreName(), columnName));
        if (null == value)
        {
            return defaultValue;
        }
        return value;
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
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
        return "$Id: GenericEntity.java 87 2008-10-04 03:07:38Z admin $";
    }
}

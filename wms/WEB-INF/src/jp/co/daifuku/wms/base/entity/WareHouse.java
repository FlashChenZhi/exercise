// $Id: WareHouse.java 5127 2009-10-13 12:20:06Z ota $
// $LastChangedRevision: 5127 $
package jp.co.daifuku.wms.base.entity;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;

import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreDefineHandler;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * WAREHOUSEのエンティティクラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class WareHouse
        extends Station
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMWAREHOUSE" ;

    /*
     * テーブル名: DMWAREHOUSE
     * ステーションNo :                STATION_NO          varchar2(16)
     * 格納区分 :                      WAREHOUSE_NO        varchar2(1)
     * 最大混載数 :                    MAX_MIXEDPALLET     number
     * 倉庫名称 :                      WAREHOUSE_NAME      varchar2(60)
     * 最終使用ステーションNo :        LAST_USED_STATION_NOvarchar2(16)
     * 自動倉庫運用種別 :              EMPLOYMENT_TYPE     varchar2(1)
     * フリーアロケーション運用区分 :  FREE_ALLOCATION_TYPEvarchar2(1)
     * 空棚検索優先区分 :              LOCATION_SEARCH_TYPEvarchar2(1)
     * アイル検索優先区分 :            AISLE_SEARCH_TYPE   varchar2(1)
     */
    /** フィールド定義 (ステーションNo(<code>STATION_NO</code>)) */
    public static final FieldName STATION_NO = new FieldName(STORE_NAME, "STATION_NO") ;

    /** フィールド定義 (格納区分(<code>WAREHOUSE_NO</code>)) */
    public static final FieldName WAREHOUSE_NO = new FieldName(STORE_NAME, "WAREHOUSE_NO") ;

    /** フィールド定義 (最大混載数(<code>MAX_MIXEDPALLET</code>)) */
    public static final FieldName MAX_MIXEDPALLET = new FieldName(STORE_NAME, "MAX_MIXEDPALLET") ;

    /** フィールド定義 (倉庫名称(<code>WAREHOUSE_NAME</code>)) */
    public static final FieldName WAREHOUSE_NAME = new FieldName(STORE_NAME, "WAREHOUSE_NAME") ;

    /** フィールド定義 (最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>)) */
    public static final FieldName LAST_USED_STATION_NO = new FieldName(STORE_NAME, "LAST_USED_STATION_NO") ;

    /** フィールド定義 (自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>)) */
    public static final FieldName EMPLOYMENT_TYPE = new FieldName(STORE_NAME, "EMPLOYMENT_TYPE") ;

    /** フィールド定義 (フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>)) */
    public static final FieldName FREE_ALLOCATION_TYPE = new FieldName(STORE_NAME, "FREE_ALLOCATION_TYPE") ;

    /** フィールド定義 (空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>)) */
    public static final FieldName LOCATION_SEARCH_TYPE = new FieldName(STORE_NAME, "LOCATION_SEARCH_TYPE") ;

    /** フィールド定義 (アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>)) */
    public static final FieldName AISLE_SEARCH_TYPE = new FieldName(STORE_NAME, "AISLE_SEARCH_TYPE") ;


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;
    /** Store meta data for this entity */
    public static final StoreMetaData $storeMetaData = StoreDefineHandler.createStoreMetaData(STORE_NAME) ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * カラム名リストを準備しインスタンスを生成します。
     */
    public WareHouse()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * ステーションNo(<code>STATION_NO</code>) に値をセットします。
     * @param value セットする値STATION_NO
     */
    public void setStationNo(String value)  // @@GEN_V3@@
    {
        setValue(STATION_NO, value);
    }

    /**
     * ステーションNo(<code>STATION_NO</code>) から値を取得します。
     * @return STATION_NO
     */
    public String getStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATION_NO, "")) ;
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>) に値をセットします。
     * @param value セットする値WAREHOUSE_NO
     */
    public void setWarehouseNo(String value)  // @@GEN_V3@@
    {
        setValue(WAREHOUSE_NO, value);
    }

    /**
     * 格納区分(<code>WAREHOUSE_NO</code>) から値を取得します。
     * @return WAREHOUSE_NO
     */
    public String getWarehouseNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WAREHOUSE_NO, "")) ;
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>) に値をセットします。
     * @param value セットする値MAX_MIXEDPALLET
     */
    public void setMaxMixedpallet(int value)  // @@GEN_V3@@
    {
        setValue(MAX_MIXEDPALLET, HandlerUtil.toObject(value));
    }

    /**
     * 最大混載数(<code>MAX_MIXEDPALLET</code>) から値を取得します。
     * @return MAX_MIXEDPALLET
     */
    public int getMaxMixedpallet()  // @@GEN_V3@@
    {
        return getBigDecimal(MAX_MIXEDPALLET, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>) に値をセットします。
     * @param value セットする値WAREHOUSE_NAME
     */
    public void setWarehouseName(String value)  // @@GEN_V3@@
    {
        setValue(WAREHOUSE_NAME, value);
    }

    /**
     * 倉庫名称(<code>WAREHOUSE_NAME</code>) から値を取得します。
     * @return WAREHOUSE_NAME
     */
    public String getWarehouseName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WAREHOUSE_NAME, "")) ;
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>) に値をセットします。
     * @param value セットする値LAST_USED_STATION_NO
     */
    public void setLastUsedStationNo(String value)  // @@GEN_V3@@
    {
        setValue(LAST_USED_STATION_NO, value);
    }

    /**
     * 最終使用ステーションNo(<code>LAST_USED_STATION_NO</code>) から値を取得します。
     * @return LAST_USED_STATION_NO
     */
    public String getLastUsedStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LAST_USED_STATION_NO, "")) ;
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>) に値をセットします。
     * @param value セットする値EMPLOYMENT_TYPE
     */
    public void setEmploymentType(String value)  // @@GEN_V3@@
    {
        setValue(EMPLOYMENT_TYPE, value);
    }

    /**
     * 自動倉庫運用種別(<code>EMPLOYMENT_TYPE</code>) から値を取得します。
     * @return EMPLOYMENT_TYPE
     */
    public String getEmploymentType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(EMPLOYMENT_TYPE, "")) ;
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>) に値をセットします。
     * @param value セットする値FREE_ALLOCATION_TYPE
     */
    public void setFreeAllocationType(String value)  // @@GEN_V3@@
    {
        setValue(FREE_ALLOCATION_TYPE, value);
    }

    /**
     * フリーアロケーション運用区分(<code>FREE_ALLOCATION_TYPE</code>) から値を取得します。
     * @return FREE_ALLOCATION_TYPE
     */
    public String getFreeAllocationType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(FREE_ALLOCATION_TYPE, "")) ;
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>) に値をセットします。
     * @param value セットする値LOCATION_SEARCH_TYPE
     */
    public void setLocationSearchType(String value)  // @@GEN_V3@@
    {
        setValue(LOCATION_SEARCH_TYPE, value);
    }

    /**
     * 空棚検索優先区分(<code>LOCATION_SEARCH_TYPE</code>) から値を取得します。
     * @return LOCATION_SEARCH_TYPE
     */
    public String getLocationSearchType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_SEARCH_TYPE, "")) ;
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>) に値をセットします。
     * @param value セットする値AISLE_SEARCH_TYPE
     */
    public void setAisleSearchType(String value)  // @@GEN_V3@@
    {
        setValue(AISLE_SEARCH_TYPE, value);
    }

    /**
     * アイル検索優先区分(<code>AISLE_SEARCH_TYPE</code>) から値を取得します。
     * @return AISLE_SEARCH_TYPE
     */
    public String getAisleSearchType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(AISLE_SEARCH_TYPE, "")) ;
    }


    /**
     * ストアメタデータを返します。
     * @return このエンティティ用ストアメタデータ
     */
    public StoreMetaData getStoreMetaData()
    {
        return $storeMetaData ;
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
        return "$Id: WareHouse.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}

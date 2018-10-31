// $Id: Aisle.java 5127 2009-10-13 12:20:06Z ota $
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
 * AISLEのエンティティクラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class Aisle
        extends Station
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMAISLE" ;

    /*
     * テーブル名: DMAISLE
     * ステーションNo. :               STATION_NO          varchar2(16)
     * 倉庫ステーションNo. :           WH_STATION_NO       varchar2(16)
     * アイルNo. :                     AISLE_NO            varchar2(3)
     * コントローラーNo. :             CONTROLLER_NO       varchar2(3)
     * ダブルディープ区分 :            DOUBLE_DEEP_KIND    varchar2(1)
     * 状態 :                          STATUS              varchar2(1)
     * 在庫確認中フラグ :              INVENTORY_CHECK_FLAGvarchar2(1)
     * 最大搬送可能数 :                MAX_CARRY           number
     */
    /** フィールド定義 (ステーションNo.(<code>STATION_NO</code>)) */
    public static final FieldName STATION_NO = new FieldName(STORE_NAME, "STATION_NO") ;

    /** フィールド定義 (倉庫ステーションNo.(<code>WH_STATION_NO</code>)) */
    public static final FieldName WH_STATION_NO = new FieldName(STORE_NAME, "WH_STATION_NO") ;

    /** フィールド定義 (アイルNo.(<code>AISLE_NO</code>)) */
    public static final FieldName AISLE_NO = new FieldName(STORE_NAME, "AISLE_NO") ;

    /** フィールド定義 (コントローラーNo.(<code>CONTROLLER_NO</code>)) */
    public static final FieldName CONTROLLER_NO = new FieldName(STORE_NAME, "CONTROLLER_NO") ;

    /** フィールド定義 (ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>)) */
    public static final FieldName DOUBLE_DEEP_KIND = new FieldName(STORE_NAME, "DOUBLE_DEEP_KIND") ;

    /** フィールド定義 (状態(<code>STATUS</code>)) */
    public static final FieldName STATUS = new FieldName(STORE_NAME, "STATUS") ;

    /** フィールド定義 (在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)) */
    public static final FieldName INVENTORY_CHECK_FLAG = new FieldName(STORE_NAME, "INVENTORY_CHECK_FLAG") ;

    /** フィールド定義 (最大搬送可能数(<code>MAX_CARRY</code>)) */
    public static final FieldName MAX_CARRY = new FieldName(STORE_NAME, "MAX_CARRY") ;


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
    public Aisle()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * ステーションNo.(<code>STATION_NO</code>) に値をセットします。
     * @param value セットする値STATION_NO
     */
    public void setStationNo(String value)  // @@GEN_V3@@
    {
        setValue(STATION_NO, value);
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>) から値を取得します。
     * @return STATION_NO
     */
    public String getStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATION_NO, "")) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>) に値をセットします。
     * @param value セットする値WH_STATION_NO
     */
    public void setWhStationNo(String value)  // @@GEN_V3@@
    {
        setValue(WH_STATION_NO, value);
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>) から値を取得します。
     * @return WH_STATION_NO
     */
    public String getWhStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WH_STATION_NO, "")) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>) に値をセットします。
     * @param value セットする値AISLE_NO
     */
    public void setAisleNo(String value)  // @@GEN_V3@@
    {
        setValue(AISLE_NO, value);
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>) から値を取得します。
     * @return AISLE_NO
     */
    public String getAisleNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(AISLE_NO, "")) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>) に値をセットします。
     * @param value セットする値CONTROLLER_NO
     */
    public void setControllerNo(String value)  // @@GEN_V3@@
    {
        setValue(CONTROLLER_NO, value);
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>) から値を取得します。
     * @return CONTROLLER_NO
     */
    public String getControllerNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONTROLLER_NO, "")) ;
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>) に値をセットします。
     * @param value セットする値DOUBLE_DEEP_KIND
     */
    public void setDoubleDeepKind(String value)  // @@GEN_V3@@
    {
        setValue(DOUBLE_DEEP_KIND, value);
    }

    /**
     * ダブルディープ区分(<code>DOUBLE_DEEP_KIND</code>) から値を取得します。
     * @return DOUBLE_DEEP_KIND
     */
    public String getDoubleDeepKind()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(DOUBLE_DEEP_KIND, "")) ;
    }

    /**
     * 状態(<code>STATUS</code>) に値をセットします。
     * @param value セットする値STATUS
     */
    public void setStatus(String value)  // @@GEN_V3@@
    {
        setValue(STATUS, value);
    }

    /**
     * 状態(<code>STATUS</code>) から値を取得します。
     * @return STATUS
     */
    public String getStatus()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATUS, "")) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>) に値をセットします。
     * @param value セットする値INVENTORY_CHECK_FLAG
     */
    public void setInventoryCheckFlag(String value)  // @@GEN_V3@@
    {
        setValue(INVENTORY_CHECK_FLAG, value);
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>) から値を取得します。
     * @return INVENTORY_CHECK_FLAG
     */
    public String getInventoryCheckFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(INVENTORY_CHECK_FLAG, "")) ;
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>) に値をセットします。
     * @param value セットする値MAX_CARRY
     */
    public void setMaxCarry(int value)  // @@GEN_V3@@
    {
        setValue(MAX_CARRY, HandlerUtil.toObject(value));
    }

    /**
     * 最大搬送可能数(<code>MAX_CARRY</code>) から値を取得します。
     * @return MAX_CARRY
     */
    public int getMaxCarry()  // @@GEN_V3@@
    {
        return getBigDecimal(MAX_CARRY, BigDecimal.ZERO).intValue() ;
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
    /**
     * アイルステーションNo(親ステーションNo)に値をセットします。
     * @param value セットする値AISLE_STATION_NO
     */
    public void setAisleStationNo(String value)
    {
        setStationNo(value);
    }

    /**
     * アイルステーションNo(親ステーションNo)を取得します。
     * @return アイルステーションNo(親ステーションNo)
     */
    public String getAisleStationNo()
    {
        return getStationNo();
    }

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
        return "$Id: Aisle.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}

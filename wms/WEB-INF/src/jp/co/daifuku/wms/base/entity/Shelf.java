// $Id: Shelf.java 4122 2009-04-10 10:58:38Z ota $
// $LastChangedRevision: 4122 $
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
 * SHELFのエンティティクラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class Shelf
        extends Station
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMSHELF" ;

    /*
     * テーブル名: DMSHELF
     * ステーションNo :                STATION_NO          varchar2(16)
     * バンク :                        BANK_NO             number
     * ベイ :                          BAY_NO              number
     * レベル :                        LEVEL_NO            number
     * アドレス :                      ADDRESS_NO          number
     * 倉庫ステーションNo :            WH_STATION_NO       varchar2(16)
     * 状態 :                          PROHIBITION_FLAG    varchar2(1)
     * 棚状態 :                        STATUS_FLAG         varchar2(1)
     * ハードゾーン :                  HARD_ZONE_ID        varchar2(3)
     * ソフトゾーン :                  SOFT_ZONE_ID        varchar2(3)
     * 親ステーションNo :              PARENT_STATION_NO   varchar2(16)
     * アクセス不可棚フラグ :          ACCESS_NG_FLAG      varchar2(1)
     * 空棚検索順 :                    PRIORITY            number
     * ペアステーションNo :            PAIR_STATION_NO     varchar2(16)
     * 手前、奥棚区分 :                SIDE                varchar2(1)
     * 荷幅 :                          WIDTH               number
     * 棚使用フラグ :                  LOCATION_USE_FLAG   varchar2(1)
     */

    /** フィールド定義 (ステーションNo(<code>STATION_NO</code>)) */
    public static final FieldName STATION_NO = new FieldName(STORE_NAME, "STATION_NO") ;

    /** フィールド定義 (バンク(<code>BANK_NO</code>)) */
    public static final FieldName BANK_NO = new FieldName(STORE_NAME, "BANK_NO") ;

    /** フィールド定義 (ベイ(<code>BAY_NO</code>)) */
    public static final FieldName BAY_NO = new FieldName(STORE_NAME, "BAY_NO") ;

    /** フィールド定義 (レベル(<code>LEVEL_NO</code>)) */
    public static final FieldName LEVEL_NO = new FieldName(STORE_NAME, "LEVEL_NO") ;

    /** フィールド定義 (アドレス(<code>ADDRESS_NO</code>)) */
    public static final FieldName ADDRESS_NO = new FieldName(STORE_NAME, "ADDRESS_NO") ;

    /** フィールド定義 (倉庫ステーションNo(<code>WH_STATION_NO</code>)) */
    public static final FieldName WH_STATION_NO = new FieldName(STORE_NAME, "WH_STATION_NO") ;

    /** フィールド定義 (状態(<code>PROHIBITION_FLAG</code>)) */
    public static final FieldName PROHIBITION_FLAG = new FieldName(STORE_NAME, "PROHIBITION_FLAG") ;

    /** フィールド定義 (棚状態(<code>STATUS_FLAG</code>)) */
    public static final FieldName STATUS_FLAG = new FieldName(STORE_NAME, "STATUS_FLAG") ;

    /** フィールド定義 (ハードゾーン(<code>HARD_ZONE_ID</code>)) */
    public static final FieldName HARD_ZONE_ID = new FieldName(STORE_NAME, "HARD_ZONE_ID") ;

    /** フィールド定義 (ソフトゾーン(<code>SOFT_ZONE_ID</code>)) */
    public static final FieldName SOFT_ZONE_ID = new FieldName(STORE_NAME, "SOFT_ZONE_ID") ;

    /** フィールド定義 (親ステーションNo(<code>PARENT_STATION_NO</code>)) */
    public static final FieldName PARENT_STATION_NO = new FieldName(STORE_NAME, "PARENT_STATION_NO") ;

    /** フィールド定義 (アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>)) */
    public static final FieldName ACCESS_NG_FLAG = new FieldName(STORE_NAME, "ACCESS_NG_FLAG") ;

    /** フィールド定義 (空棚検索順(<code>PRIORITY</code>)) */
    public static final FieldName PRIORITY = new FieldName(STORE_NAME, "PRIORITY") ;

    /** フィールド定義 (ペアステーションNo(<code>PAIR_STATION_NO</code>)) */
    public static final FieldName PAIR_STATION_NO = new FieldName(STORE_NAME, "PAIR_STATION_NO") ;

    /** フィールド定義 (手前、奥棚区分(<code>SIDE</code>)) */
    public static final FieldName SIDE = new FieldName(STORE_NAME, "SIDE") ;

    /** フィールド定義 (荷幅(<code>WIDTH</code>)) */
    public static final FieldName WIDTH = new FieldName(STORE_NAME, "WIDTH") ;

    /** フィールド定義 (棚使用フラグ(<code>LOCATION_USE_FLAG</code>)) */
    public static final FieldName LOCATION_USE_FLAG = new FieldName(STORE_NAME, "LOCATION_USE_FLAG") ;


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
    public Shelf()
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
     * バンク(<code>BANK_NO</code>) に値をセットします。
     * @param value セットする値BANK_NO
     */
    public void setBankNo(int value)  // @@GEN_V3@@
    {
        setValue(BANK_NO, HandlerUtil.toObject(value));
    }

    /**
     * バンク(<code>BANK_NO</code>) から値を取得します。
     * @return BANK_NO
     */
    public int getBankNo()  // @@GEN_V3@@
    {
        return getBigDecimal(BANK_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * ベイ(<code>BAY_NO</code>) に値をセットします。
     * @param value セットする値BAY_NO
     */
    public void setBayNo(int value)  // @@GEN_V3@@
    {
        setValue(BAY_NO, HandlerUtil.toObject(value));
    }

    /**
     * ベイ(<code>BAY_NO</code>) から値を取得します。
     * @return BAY_NO
     */
    public int getBayNo()  // @@GEN_V3@@
    {
        return getBigDecimal(BAY_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * レベル(<code>LEVEL_NO</code>) に値をセットします。
     * @param value セットする値LEVEL_NO
     */
    public void setLevelNo(int value)  // @@GEN_V3@@
    {
        setValue(LEVEL_NO, HandlerUtil.toObject(value));
    }

    /**
     * レベル(<code>LEVEL_NO</code>) から値を取得します。
     * @return LEVEL_NO
     */
    public int getLevelNo()  // @@GEN_V3@@
    {
        return getBigDecimal(LEVEL_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>) に値をセットします。
     * @param value セットする値ADDRESS_NO
     */
    public void setAddressNo(int value)  // @@GEN_V3@@
    {
        setValue(ADDRESS_NO, HandlerUtil.toObject(value));
    }

    /**
     * アドレス(<code>ADDRESS_NO</code>) から値を取得します。
     * @return ADDRESS_NO
     */
    public int getAddressNo()  // @@GEN_V3@@
    {
        return getBigDecimal(ADDRESS_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>) に値をセットします。
     * @param value セットする値WH_STATION_NO
     */
    public void setWhStationNo(String value)  // @@GEN_V3@@
    {
        setValue(WH_STATION_NO, value);
    }

    /**
     * 倉庫ステーションNo(<code>WH_STATION_NO</code>) から値を取得します。
     * @return WH_STATION_NO
     */
    public String getWhStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WH_STATION_NO, "")) ;
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>) に値をセットします。
     * @param value セットする値PROHIBITION_FLAG
     */
    public void setProhibitionFlag(String value)  // @@GEN_V3@@
    {
        setValue(PROHIBITION_FLAG, value);
    }

    /**
     * 状態(<code>PROHIBITION_FLAG</code>) から値を取得します。
     * @return PROHIBITION_FLAG
     */
    public String getProhibitionFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PROHIBITION_FLAG, "")) ;
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>) に値をセットします。
     * @param value セットする値STATUS_FLAG
     */
    public void setStatusFlag(String value)  // @@GEN_V3@@
    {
        setValue(STATUS_FLAG, value);
    }

    /**
     * 棚状態(<code>STATUS_FLAG</code>) から値を取得します。
     * @return STATUS_FLAG
     */
    public String getStatusFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATUS_FLAG, "")) ;
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>) に値をセットします。
     * @param value セットする値HARD_ZONE_ID
     */
    public void setHardZoneId(String value)  // @@GEN_V3@@
    {
        setValue(HARD_ZONE_ID, value);
    }

    /**
     * ハードゾーン(<code>HARD_ZONE_ID</code>) から値を取得します。
     * @return HARD_ZONE_ID
     */
    public String getHardZoneId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(HARD_ZONE_ID, "")) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>) に値をセットします。
     * @param value セットする値SOFT_ZONE_ID
     */
    public void setSoftZoneId(String value)  // @@GEN_V3@@
    {
        setValue(SOFT_ZONE_ID, value);
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>) から値を取得します。
     * @return SOFT_ZONE_ID
     */
    public String getSoftZoneId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SOFT_ZONE_ID, "")) ;
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>) に値をセットします。
     * @param value セットする値PARENT_STATION_NO
     */
    public void setParentStationNo(String value)  // @@GEN_V3@@
    {
        setValue(PARENT_STATION_NO, value);
    }

    /**
     * 親ステーションNo(<code>PARENT_STATION_NO</code>) から値を取得します。
     * @return PARENT_STATION_NO
     */
    public String getParentStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PARENT_STATION_NO, "")) ;
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>) に値をセットします。
     * @param value セットする値ACCESS_NG_FLAG
     */
    public void setAccessNgFlag(String value)  // @@GEN_V3@@
    {
        setValue(ACCESS_NG_FLAG, value);
    }

    /**
     * アクセス不可棚フラグ(<code>ACCESS_NG_FLAG</code>) から値を取得します。
     * @return ACCESS_NG_FLAG
     */
    public String getAccessNgFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ACCESS_NG_FLAG, "")) ;
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>) に値をセットします。
     * @param value セットする値PRIORITY
     */
    public void setPriority(int value)  // @@GEN_V3@@
    {
        setValue(PRIORITY, HandlerUtil.toObject(value));
    }

    /**
     * 空棚検索順(<code>PRIORITY</code>) から値を取得します。
     * @return PRIORITY
     */
    public int getPriority()  // @@GEN_V3@@
    {
        return getBigDecimal(PRIORITY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>) に値をセットします。
     * @param value セットする値PAIR_STATION_NO
     */
    public void setPairStationNo(String value)  // @@GEN_V3@@
    {
        setValue(PAIR_STATION_NO, value);
    }

    /**
     * ペアステーションNo(<code>PAIR_STATION_NO</code>) から値を取得します。
     * @return PAIR_STATION_NO
     */
    public String getPairStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PAIR_STATION_NO, "")) ;
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>) に値をセットします。
     * @param value セットする値SIDE
     */
    public void setSide(String value)  // @@GEN_V3@@
    {
        setValue(SIDE, value);
    }

    /**
     * 手前、奥棚区分(<code>SIDE</code>) から値を取得します。
     * @return SIDE
     */
    public String getSide()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SIDE, "")) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>) に値をセットします。
     * @param value セットする値WIDTH
     */
    public void setWidth(int value)  // @@GEN_V3@@
    {
        setValue(WIDTH, HandlerUtil.toObject(value));
    }

    /**
     * 荷幅(<code>WIDTH</code>) から値を取得します。
     * @return WIDTH
     */
    public int getWidth()  // @@GEN_V3@@
    {
        return getBigDecimal(WIDTH, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>) に値をセットします。
     * @param value セットする値LOCATION_USE_FLAG
     */
    public void setLocationUseFlag(String value)  // @@GEN_V3@@
    {
        setValue(LOCATION_USE_FLAG, value);
    }

    /**
     * 棚使用フラグ(<code>LOCATION_USE_FLAG</code>) から値を取得します。
     * @return LOCATION_USE_FLAG
     */
    public String getLocationUseFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_USE_FLAG, "")) ;
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
     * このステーションが指示情報として送信可能かどうか返します。<BR>
     * 棚の送信可能フラグは常にtrueとなります。<BR>
     * StationのisSendable()をオーバーライドします。<BR>
     * @return true : 送信可能
     */
    public boolean isSendable()
    {
        return true;
    }

    /**
     * アイルステーションNo(親ステーションNo)に値をセットします。
     * @param value セットする値AISLE_STATION_NO
     */
    public void setAisleStationNo(String value)
    {
        setParentStationNo(value);
    }

    /**
     * アイルステーションNo(親ステーションNo)を取得します。
     * @return アイルステーションNo(親ステーションNo)
     */
    public String getAisleStationNo()
    {
        return getParentStationNo();
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
        return "$Id: Shelf.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}

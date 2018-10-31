// $Id: BankSelect.java 1544 2008-11-25 09:32:24Z dmori $
// $LastChangedRevision: 1544 $
package jp.co.daifuku.wms.base.entity;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import jp.co.daifuku.wms.handler.db.AbstractDBEntity;
import jp.co.daifuku.wms.handler.field.FieldDefine;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreDefine;
import jp.co.daifuku.wms.handler.field.StoreDefineHandler;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * BANKSELECTのエンティティクラスです。
 *
 * @version $Revision: 1544 $, $Date: 2008-11-25 18:32:24 +0900 (火, 25 11 2008) $
 * @author  ss
 * @author  Last commit: $Author: dmori $
 */

public class BankSelect
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMBANKSELECT" ;

    /*
     * テーブル名: DMBANKSELECT
     * 倉庫ステーションNo. :           WH_STATION_NO       varchar2(16)
     * アイルステーションNo. :         AISLE_STATION_NO    varchar2(16)
     * バンクNo. :                     BANK_NO             number
     * ペアバンクNo :                  PAIR_BANK           number
     * 手前、奥棚区分 :                SIDE                varchar2(1)
     */

    /** フィールド定義 (倉庫ステーションNo.(<code>WH_STATION_NO</code>)) */
    public static final FieldName WH_STATION_NO = new FieldName(STORE_NAME, "WH_STATION_NO") ;

    /** フィールド定義 (アイルステーションNo.(<code>AISLE_STATION_NO</code>)) */
    public static final FieldName AISLE_STATION_NO = new FieldName(STORE_NAME, "AISLE_STATION_NO") ;

    /** フィールド定義 (バンクNo.(<code>BANK_NO</code>)) */
    public static final FieldName BANK_NO = new FieldName(STORE_NAME, "BANK_NO") ;

    /** フィールド定義 (ペアバンクNo(<code>PAIR_BANK</code>)) */
    public static final FieldName PAIR_BANK = new FieldName(STORE_NAME, "PAIR_BANK") ;

    /** フィールド定義 (手前、奥棚区分(<code>SIDE</code>)) */
    public static final FieldName SIDE = new FieldName(STORE_NAME, "SIDE") ;


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
    public BankSelect()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

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
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>) に値をセットします。
     * @param value セットする値AISLE_STATION_NO
     */
    public void setAisleStationNo(String value)  // @@GEN_V3@@
    {
        setValue(AISLE_STATION_NO, value);
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>) から値を取得します。
     * @return AISLE_STATION_NO
     */
    public String getAisleStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(AISLE_STATION_NO, "")) ;
    }

    /**
     * バンクNo.(<code>BANK_NO</code>) に値をセットします。
     * @param value セットする値BANK_NO
     */
    public void setBankNo(int value)  // @@GEN_V3@@
    {
        setValue(BANK_NO, HandlerUtil.toObject(value));
    }

    /**
     * バンクNo.(<code>BANK_NO</code>) から値を取得します。
     * @return BANK_NO
     */
    public int getBankNo()  // @@GEN_V3@@
    {
        return getBigDecimal(BANK_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * ペアバンクNo(<code>PAIR_BANK</code>) に値をセットします。
     * @param value セットする値PAIR_BANK
     */
    public void setPairBank(int value)  // @@GEN_V3@@
    {
        setValue(PAIR_BANK, HandlerUtil.toObject(value));
    }

    /**
     * ペアバンクNo(<code>PAIR_BANK</code>) から値を取得します。
     * @return PAIR_BANK
     */
    public int getPairBank()  // @@GEN_V3@@
    {
        return getBigDecimal(PAIR_BANK, BigDecimal.ZERO).intValue() ;
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
        return "$Id: BankSelect.java 1544 2008-11-25 09:32:24Z dmori $" ;
    }
}

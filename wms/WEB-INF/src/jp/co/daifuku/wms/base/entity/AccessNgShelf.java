// $Id: AccessNgShelf.java 4122 2009-04-10 10:58:38Z ota $
// $LastChangedRevision: 4122 $
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
 * ACCESSNGSHELFのエンティティクラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class AccessNgShelf
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMACCESSNGSHELF" ;

    /*
     * テーブル名: DMACCESSNGSHELF
     * 倉庫ステーションNo. :           WH_STATION_NO       varchar2(16)
     * バンク :                        BANK_NO             number
     * ベイ :                          BAY_NO              number
     * レベル :                        LEVEL_NO            number
     * 荷幅 :                          WIDTH               number
     * 開始アドレス :                  START_ADDRESS_NO    number
     * 終了アドレス :                  END_ADDRESS_NO      number
     */
    /** フィールド定義 (倉庫ステーションNo.(<code>WH_STATION_NO</code>)) */
    public static final FieldName WH_STATION_NO = new FieldName(STORE_NAME, "WH_STATION_NO") ;

    /** フィールド定義 (バンク(<code>BANK_NO</code>)) */
    public static final FieldName BANK_NO = new FieldName(STORE_NAME, "BANK_NO") ;

    /** フィールド定義 (ベイ(<code>BAY_NO</code>)) */
    public static final FieldName BAY_NO = new FieldName(STORE_NAME, "BAY_NO") ;

    /** フィールド定義 (レベル(<code>LEVEL_NO</code>)) */
    public static final FieldName LEVEL_NO = new FieldName(STORE_NAME, "LEVEL_NO") ;

    /** フィールド定義 (荷幅(<code>WIDTH</code>)) */
    public static final FieldName WIDTH = new FieldName(STORE_NAME, "WIDTH") ;

    /** フィールド定義 (開始アドレス(<code>START_ADDRESS_NO</code>)) */
    public static final FieldName START_ADDRESS_NO = new FieldName(STORE_NAME, "START_ADDRESS_NO") ;

    /** フィールド定義 (終了アドレス(<code>END_ADDRESS_NO</code>)) */
    public static final FieldName END_ADDRESS_NO = new FieldName(STORE_NAME, "END_ADDRESS_NO") ;


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
    public AccessNgShelf()
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
     * 開始アドレス(<code>START_ADDRESS_NO</code>) に値をセットします。
     * @param value セットする値START_ADDRESS_NO
     */
    public void setStartAddressNo(int value)  // @@GEN_V3@@
    {
        setValue(START_ADDRESS_NO, HandlerUtil.toObject(value));
    }

    /**
     * 開始アドレス(<code>START_ADDRESS_NO</code>) から値を取得します。
     * @return START_ADDRESS_NO
     */
    public int getStartAddressNo()  // @@GEN_V3@@
    {
        return getBigDecimal(START_ADDRESS_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 終了アドレス(<code>END_ADDRESS_NO</code>) に値をセットします。
     * @param value セットする値END_ADDRESS_NO
     */
    public void setEndAddressNo(int value)  // @@GEN_V3@@
    {
        setValue(END_ADDRESS_NO, HandlerUtil.toObject(value));
    }

    /**
     * 終了アドレス(<code>END_ADDRESS_NO</code>) から値を取得します。
     * @return END_ADDRESS_NO
     */
    public int getEndAddressNo()  // @@GEN_V3@@
    {
        return getBigDecimal(END_ADDRESS_NO, BigDecimal.ZERO).intValue() ;
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
        return "$Id: AccessNgShelf.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}

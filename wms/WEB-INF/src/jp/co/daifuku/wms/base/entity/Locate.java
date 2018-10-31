// $Id: Locate.java 5652 2009-11-11 02:40:44Z okayama $
// $LastChangedRevision: 5652 $
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
 * LOCATEのエンティティクラスです。
 *
 * @version $Revision: 5652 $, $Date: 2009-11-11 11:40:44 +0900 (水, 11 11 2009) $
 * @author  ss
 * @author  Last commit: $Author: okayama $
 */

public class Locate
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMLOCATE" ;

    /*
     * テーブル名: DMLOCATE
     * システム管理区分 :              MANAGEMENT_TYPE     varchar2(1)
     * エリアNo. :                     AREA_NO             varchar2(4)
     * 棚No. :                         LOCATION_NO         varchar2(11)
     * アイルNo. :                     AISLE_NO            number
     * バンク :                        BANK_NO             number
     * ベイ :                          BAY_NO              number
     * レベル :                        LEVEL_NO            number
     * 状態フラグ :                    STATUS_FLAG         varchar2(1)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (システム管理区分(<code>MANAGEMENT_TYPE</code>)) */
    public static final FieldName MANAGEMENT_TYPE = new FieldName(STORE_NAME, "MANAGEMENT_TYPE") ;

    /** フィールド定義 (エリアNo.(<code>AREA_NO</code>)) */
    public static final FieldName AREA_NO = new FieldName(STORE_NAME, "AREA_NO") ;

    /** フィールド定義 (棚No.(<code>LOCATION_NO</code>)) */
    public static final FieldName LOCATION_NO = new FieldName(STORE_NAME, "LOCATION_NO") ;

    /** フィールド定義 (アイルNo.(<code>AISLE_NO</code>)) */
    public static final FieldName AISLE_NO = new FieldName(STORE_NAME, "AISLE_NO") ;

    /** フィールド定義 (バンク(<code>BANK_NO</code>)) */
    public static final FieldName BANK_NO = new FieldName(STORE_NAME, "BANK_NO") ;

    /** フィールド定義 (ベイ(<code>BAY_NO</code>)) */
    public static final FieldName BAY_NO = new FieldName(STORE_NAME, "BAY_NO") ;

    /** フィールド定義 (レベル(<code>LEVEL_NO</code>)) */
    public static final FieldName LEVEL_NO = new FieldName(STORE_NAME, "LEVEL_NO") ;

    /** フィールド定義 (状態フラグ(<code>STATUS_FLAG</code>)) */
    public static final FieldName STATUS_FLAG = new FieldName(STORE_NAME, "STATUS_FLAG") ;

    /** フィールド定義 (登録日時(<code>REGIST_DATE</code>)) */
    public static final FieldName REGIST_DATE = new FieldName(STORE_NAME, "REGIST_DATE") ;

    /** フィールド定義 (登録処理名(<code>REGIST_PNAME</code>)) */
    public static final FieldName REGIST_PNAME = new FieldName(STORE_NAME, "REGIST_PNAME") ;

    /** フィールド定義 (最終更新日時(<code>LAST_UPDATE_DATE</code>)) */
    public static final FieldName LAST_UPDATE_DATE = new FieldName(STORE_NAME, "LAST_UPDATE_DATE") ;

    /** フィールド定義 (最終更新処理名(<code>LAST_UPDATE_PNAME</code>)) */
    public static final FieldName LAST_UPDATE_PNAME = new FieldName(STORE_NAME, "LAST_UPDATE_PNAME") ;


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
    public Locate()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>) に値をセットします。
     * @param value セットする値MANAGEMENT_TYPE
     */
    public void setManagementType(String value)  // @@GEN_V3@@
    {
        setValue(MANAGEMENT_TYPE, value);
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>) から値を取得します。
     * @return MANAGEMENT_TYPE
     */
    public String getManagementType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MANAGEMENT_TYPE, "")) ;
    }

    /**
     * エリアNo.(<code>AREA_NO</code>) に値をセットします。
     * @param value セットする値AREA_NO
     */
    public void setAreaNo(String value)  // @@GEN_V3@@
    {
        setValue(AREA_NO, value);
    }

    /**
     * エリアNo.(<code>AREA_NO</code>) から値を取得します。
     * @return AREA_NO
     */
    public String getAreaNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(AREA_NO, "")) ;
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>) に値をセットします。
     * @param value セットする値LOCATION_NO
     */
    public void setLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(LOCATION_NO, value);
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>) から値を取得します。
     * @return LOCATION_NO
     */
    public String getLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_NO, "")) ;
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>) に値をセットします。
     * @param value セットする値AISLE_NO
     */
    public void setAisleNo(int value)  // @@GEN_V3@@
    {
        setValue(AISLE_NO, HandlerUtil.toObject(value));
    }

    /**
     * アイルNo.(<code>AISLE_NO</code>) から値を取得します。
     * @return AISLE_NO
     */
    public int getAisleNo()  // @@GEN_V3@@
    {
        return getBigDecimal(AISLE_NO, BigDecimal.ZERO).intValue() ;
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
     * 状態フラグ(<code>STATUS_FLAG</code>) に値をセットします。
     * @param value セットする値STATUS_FLAG
     */
    public void setStatusFlag(String value)  // @@GEN_V3@@
    {
        setValue(STATUS_FLAG, value);
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>) から値を取得します。
     * @return STATUS_FLAG
     */
    public String getStatusFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATUS_FLAG, "")) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>) に値をセットします。
     * @param value セットする値REGIST_DATE
     */
    public void setRegistDate(Date value)  // @@GEN_V3@@
    {
        setValue(REGIST_DATE, value);
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>) から値を取得します。
     * @return REGIST_DATE
     */
    public Date getRegistDate()  // @@GEN_V3@@
    {
        return (Date)getValue(REGIST_DATE, null) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>) に値をセットします。
     * @param value セットする値REGIST_PNAME
     */
    public void setRegistPname(String value)  // @@GEN_V3@@
    {
        setValue(REGIST_PNAME, value);
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>) から値を取得します。
     * @return REGIST_PNAME
     */
    public String getRegistPname()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REGIST_PNAME, "")) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>) に値をセットします。
     * @param value セットする値LAST_UPDATE_DATE
     */
    public void setLastUpdateDate(Date value)  // @@GEN_V3@@
    {
        setValue(LAST_UPDATE_DATE, value);
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>) から値を取得します。
     * @return LAST_UPDATE_DATE
     */
    public Date getLastUpdateDate()  // @@GEN_V3@@
    {
        return (Date)getValue(LAST_UPDATE_DATE, null) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>) に値をセットします。
     * @param value セットする値LAST_UPDATE_PNAME
     */
    public void setLastUpdatePname(String value)  // @@GEN_V3@@
    {
        setValue(LAST_UPDATE_PNAME, value);
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>) から値を取得します。
     * @return LAST_UPDATE_PNAME
     */
    public String getLastUpdatePname()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LAST_UPDATE_PNAME, "")) ;
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
        return "$Id: Locate.java 5652 2009-11-11 02:40:44Z okayama $" ;
    }
}

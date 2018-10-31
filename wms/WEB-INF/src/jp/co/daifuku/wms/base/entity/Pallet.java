// $Id: Pallet.java 4122 2009-04-10 10:58:38Z ota $
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
 * PALLETのエンティティクラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class Pallet
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNPALLET" ;

    /*
     * テーブル名: DNPALLET
     * パレットID :                    PALLET_ID           varchar2(8)
     * 現在ステーションNo. :           CURRENT_STATION_NO  varchar2(16)
     * 倉庫ステーションNo. :           WH_STATION_NO       varchar2(16)
     * 在庫状態 :                      STATUS_FLAG         varchar2(1)
     * 引当状態 :                      ALLOCATION_FLAG     varchar2(1)
     * 空パレット状態 :                EMPTY_FLAG          varchar2(1)
     * 荷高 :                          HEIGHT              number
     * 荷幅 :                          WIDTH               number
     * ソフトゾーン :                  SOFT_ZONE_ID        varchar2(3)
     * バーコード情報 :                BCR_DATA            varchar2(30)
     * 制御情報 :                      CONTROLINFO         varchar2(30)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (パレットID(<code>PALLET_ID</code>)) */
    public static final FieldName PALLET_ID = new FieldName(STORE_NAME, "PALLET_ID") ;

    /** フィールド定義 (現在ステーションNo.(<code>CURRENT_STATION_NO</code>)) */
    public static final FieldName CURRENT_STATION_NO = new FieldName(STORE_NAME, "CURRENT_STATION_NO") ;

    /** フィールド定義 (倉庫ステーションNo.(<code>WH_STATION_NO</code>)) */
    public static final FieldName WH_STATION_NO = new FieldName(STORE_NAME, "WH_STATION_NO") ;

    /** フィールド定義 (在庫状態(<code>STATUS_FLAG</code>)) */
    public static final FieldName STATUS_FLAG = new FieldName(STORE_NAME, "STATUS_FLAG") ;

    /** フィールド定義 (引当状態(<code>ALLOCATION_FLAG</code>)) */
    public static final FieldName ALLOCATION_FLAG = new FieldName(STORE_NAME, "ALLOCATION_FLAG") ;

    /** フィールド定義 (空パレット状態(<code>EMPTY_FLAG</code>)) */
    public static final FieldName EMPTY_FLAG = new FieldName(STORE_NAME, "EMPTY_FLAG") ;

    /** フィールド定義 (荷高(<code>HEIGHT</code>)) */
    public static final FieldName HEIGHT = new FieldName(STORE_NAME, "HEIGHT") ;

    /** フィールド定義 (荷幅(<code>WIDTH</code>)) */
    public static final FieldName WIDTH = new FieldName(STORE_NAME, "WIDTH") ;

    /** フィールド定義 (ソフトゾーン(<code>SOFT_ZONE_ID</code>)) */
    public static final FieldName SOFT_ZONE_ID = new FieldName(STORE_NAME, "SOFT_ZONE_ID") ;

    /** フィールド定義 (バーコード情報(<code>BCR_DATA</code>)) */
    public static final FieldName BCR_DATA = new FieldName(STORE_NAME, "BCR_DATA") ;

    /** フィールド定義 (制御情報(<code>CONTROLINFO</code>)) */
    public static final FieldName CONTROLINFO = new FieldName(STORE_NAME, "CONTROLINFO") ;

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
    public Pallet()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * パレットID(<code>PALLET_ID</code>) に値をセットします。
     * @param value セットする値PALLET_ID
     */
    public void setPalletId(String value)  // @@GEN_V3@@
    {
        setValue(PALLET_ID, value);
    }

    /**
     * パレットID(<code>PALLET_ID</code>) から値を取得します。
     * @return PALLET_ID
     */
    public String getPalletId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PALLET_ID, "")) ;
    }

    /**
     * 現在ステーションNo.(<code>CURRENT_STATION_NO</code>) に値をセットします。
     * @param value セットする値CURRENT_STATION_NO
     */
    public void setCurrentStationNo(String value)  // @@GEN_V3@@
    {
        setValue(CURRENT_STATION_NO, value);
    }

    /**
     * 現在ステーションNo.(<code>CURRENT_STATION_NO</code>) から値を取得します。
     * @return CURRENT_STATION_NO
     */
    public String getCurrentStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CURRENT_STATION_NO, "")) ;
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
     * 在庫状態(<code>STATUS_FLAG</code>) に値をセットします。
     * @param value セットする値STATUS_FLAG
     */
    public void setStatusFlag(String value)  // @@GEN_V3@@
    {
        setValue(STATUS_FLAG, value);
    }

    /**
     * 在庫状態(<code>STATUS_FLAG</code>) から値を取得します。
     * @return STATUS_FLAG
     */
    public String getStatusFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATUS_FLAG, "")) ;
    }

    /**
     * 引当状態(<code>ALLOCATION_FLAG</code>) に値をセットします。
     * @param value セットする値ALLOCATION_FLAG
     */
    public void setAllocationFlag(String value)  // @@GEN_V3@@
    {
        setValue(ALLOCATION_FLAG, value);
    }

    /**
     * 引当状態(<code>ALLOCATION_FLAG</code>) から値を取得します。
     * @return ALLOCATION_FLAG
     */
    public String getAllocationFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ALLOCATION_FLAG, "")) ;
    }

    /**
     * 空パレット状態(<code>EMPTY_FLAG</code>) に値をセットします。
     * @param value セットする値EMPTY_FLAG
     */
    public void setEmptyFlag(String value)  // @@GEN_V3@@
    {
        setValue(EMPTY_FLAG, value);
    }

    /**
     * 空パレット状態(<code>EMPTY_FLAG</code>) から値を取得します。
     * @return EMPTY_FLAG
     */
    public String getEmptyFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(EMPTY_FLAG, "")) ;
    }

    /**
     * 荷高(<code>HEIGHT</code>) に値をセットします。
     * @param value セットする値HEIGHT
     */
    public void setHeight(int value)  // @@GEN_V3@@
    {
        setValue(HEIGHT, HandlerUtil.toObject(value));
    }

    /**
     * 荷高(<code>HEIGHT</code>) から値を取得します。
     * @return HEIGHT
     */
    public int getHeight()  // @@GEN_V3@@
    {
        return getBigDecimal(HEIGHT, BigDecimal.ZERO).intValue() ;
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
     * バーコード情報(<code>BCR_DATA</code>) に値をセットします。
     * @param value セットする値BCR_DATA
     */
    public void setBcrData(String value)  // @@GEN_V3@@
    {
        setValue(BCR_DATA, value);
    }

    /**
     * バーコード情報(<code>BCR_DATA</code>) から値を取得します。
     * @return BCR_DATA
     */
    public String getBcrData()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(BCR_DATA, "")) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>) に値をセットします。
     * @param value セットする値CONTROLINFO
     */
    public void setControlinfo(String value)  // @@GEN_V3@@
    {
        setValue(CONTROLINFO, value);
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>) から値を取得します。
     * @return CONTROLINFO
     */
    public String getControlinfo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONTROLINFO, "")) ;
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
        return "$Id: Pallet.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}

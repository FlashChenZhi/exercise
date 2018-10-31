// $Id: Arrival.java 5127 2009-10-13 12:20:06Z ota $
// $LastChangedRevision: 5127 $
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
 * ARRIVALのエンティティクラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class Arrival
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNARRIVAL" ;

    /*
     * テーブル名: DNARRIVAL
     * 到着日時 :                      ARRIVAL_DATE        timestamp
     * ステーションNo. :               STATION_NO          varchar2(16)
     * 搬送キー :                      CARRY_KEY           varchar2(16)
     * 荷高 :                          HEIGHT              number
     * バーコードデータ :              BCR_DATA            varchar2(30)
     * 制御情報 :                      CONTROLINFO         varchar2(30)
     * 送信フラグ :                    SEND_FLAG           varchar2(1)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (到着日時(<code>ARRIVAL_DATE</code>)) */
    public static final FieldName ARRIVAL_DATE = new FieldName(STORE_NAME, "ARRIVAL_DATE") ;

    /** フィールド定義 (ステーションNo.(<code>STATION_NO</code>)) */
    public static final FieldName STATION_NO = new FieldName(STORE_NAME, "STATION_NO") ;

    /** フィールド定義 (搬送キー(<code>CARRY_KEY</code>)) */
    public static final FieldName CARRY_KEY = new FieldName(STORE_NAME, "CARRY_KEY") ;

    /** フィールド定義 (荷高(<code>HEIGHT</code>)) */
    public static final FieldName HEIGHT = new FieldName(STORE_NAME, "HEIGHT") ;

    /** フィールド定義 (バーコードデータ(<code>BCR_DATA</code>)) */
    public static final FieldName BCR_DATA = new FieldName(STORE_NAME, "BCR_DATA") ;

    /** フィールド定義 (制御情報(<code>CONTROLINFO</code>)) */
    public static final FieldName CONTROLINFO = new FieldName(STORE_NAME, "CONTROLINFO") ;

    /** フィールド定義 (送信フラグ(<code>SEND_FLAG</code>)) */
    public static final FieldName SEND_FLAG = new FieldName(STORE_NAME, "SEND_FLAG") ;

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
    public Arrival()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>) に値をセットします。
     * @param value セットする値ARRIVAL_DATE
     */
    public void setArrivalDate(Date value)  // @@GEN_V3@@
    {
        setValue(ARRIVAL_DATE, value);
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>) から値を取得します。
     * @return ARRIVAL_DATE
     */
    public Date getArrivalDate()  // @@GEN_V3@@
    {
        return (Date)getValue(ARRIVAL_DATE, null) ;
    }

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
     * 搬送キー(<code>CARRY_KEY</code>) に値をセットします。
     * @param value セットする値CARRY_KEY
     */
    public void setCarryKey(String value)  // @@GEN_V3@@
    {
        setValue(CARRY_KEY, value);
    }

    /**
     * 搬送キー(<code>CARRY_KEY</code>) から値を取得します。
     * @return CARRY_KEY
     */
    public String getCarryKey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CARRY_KEY, "")) ;
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
     * バーコードデータ(<code>BCR_DATA</code>) に値をセットします。
     * @param value セットする値BCR_DATA
     */
    public void setBcrData(String value)  // @@GEN_V3@@
    {
        setValue(BCR_DATA, value);
    }

    /**
     * バーコードデータ(<code>BCR_DATA</code>) から値を取得します。
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
     * 送信フラグ(<code>SEND_FLAG</code>) に値をセットします。
     * @param value セットする値SEND_FLAG
     */
    public void setSendFlag(String value)  // @@GEN_V3@@
    {
        setValue(SEND_FLAG, value);
    }

    /**
     * 送信フラグ(<code>SEND_FLAG</code>) から値を取得します。
     * @return SEND_FLAG
     */
    public String getSendFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SEND_FLAG, "")) ;
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
        return "$Id: Arrival.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}

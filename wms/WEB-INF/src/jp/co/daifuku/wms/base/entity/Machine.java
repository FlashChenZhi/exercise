// $Id: Machine.java 7254 2010-02-26 05:58:23Z kanda $
// $LastChangedRevision: 7254 $
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
 * MACHINEのエンティティクラスです。
 *
 * @version $Revision: 7254 $, $Date: 2010-02-26 14:58:23 +0900 (金, 26 2 2010) $
 * @author  ss
 * @author  Last commit: $Author: kanda $
 */

public class Machine
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMMACHINE" ;

    /*
     * テーブル名: DMMACHINE
     * ステーションNo. :               STATION_NO          varchar2(16)
     * 機器種別 :                      MACHINE_TYPE        varchar2(2)
     * 機器No. :                       MACHINE_NO          varchar2(4)
     * 状態 :                          STATUS_FLAG         varchar2(1)
     * 異常コード :                    ERROR_CODE          varchar2(16)
     * コントローラー番号 :            CONTROLLER_NO       varchar2(3)
     * 機器名称 :                      DEVICE_NAME         varchar2(10)
     */
    /** フィールド定義 (ステーションNo.(<code>STATION_NO</code>)) */
    public static final FieldName STATION_NO = new FieldName(STORE_NAME, "STATION_NO") ;

    /** フィールド定義 (機器種別(<code>MACHINE_TYPE</code>)) */
    public static final FieldName MACHINE_TYPE = new FieldName(STORE_NAME, "MACHINE_TYPE") ;

    /** フィールド定義 (機器No.(<code>MACHINE_NO</code>)) */
    public static final FieldName MACHINE_NO = new FieldName(STORE_NAME, "MACHINE_NO") ;

    /** フィールド定義 (状態(<code>STATUS_FLAG</code>)) */
    public static final FieldName STATUS_FLAG = new FieldName(STORE_NAME, "STATUS_FLAG") ;

    /** フィールド定義 (異常コード(<code>ERROR_CODE</code>)) */
    public static final FieldName ERROR_CODE = new FieldName(STORE_NAME, "ERROR_CODE") ;

    /** フィールド定義 (コントローラー番号(<code>CONTROLLER_NO</code>)) */
    public static final FieldName CONTROLLER_NO = new FieldName(STORE_NAME, "CONTROLLER_NO") ;

    /** フィールド定義 (機器名称(<code>DEVICE_NAME</code>)) */
    public static final FieldName DEVICE_NAME = new FieldName(STORE_NAME, "DEVICE_NAME") ;


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
    public Machine()
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
     * 機器種別(<code>MACHINE_TYPE</code>) に値をセットします。
     * @param value セットする値MACHINE_TYPE
     */
    public void setMachineType(String value)  // @@GEN_V3@@
    {
        setValue(MACHINE_TYPE, value);
    }

    /**
     * 機器種別(<code>MACHINE_TYPE</code>) から値を取得します。
     * @return MACHINE_TYPE
     */
    public String getMachineType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MACHINE_TYPE, "")) ;
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>) に値をセットします。
     * @param value セットする値MACHINE_NO
     */
    public void setMachineNo(String value)  // @@GEN_V3@@
    {
        setValue(MACHINE_NO, value);
    }

    /**
     * 機器No.(<code>MACHINE_NO</code>) から値を取得します。
     * @return MACHINE_NO
     */
    public String getMachineNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MACHINE_NO, "")) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>) に値をセットします。
     * @param value セットする値STATUS_FLAG
     */
    public void setStatusFlag(String value)  // @@GEN_V3@@
    {
        setValue(STATUS_FLAG, value);
    }

    /**
     * 状態(<code>STATUS_FLAG</code>) から値を取得します。
     * @return STATUS_FLAG
     */
    public String getStatusFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATUS_FLAG, "")) ;
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>) に値をセットします。
     * @param value セットする値ERROR_CODE
     */
    public void setErrorCode(String value)  // @@GEN_V3@@
    {
        setValue(ERROR_CODE, value);
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>) から値を取得します。
     * @return ERROR_CODE
     */
    public String getErrorCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ERROR_CODE, "")) ;
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>) に値をセットします。
     * @param value セットする値CONTROLLER_NO
     */
    public void setControllerNo(String value)  // @@GEN_V3@@
    {
        setValue(CONTROLLER_NO, value);
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>) から値を取得します。
     * @return CONTROLLER_NO
     */
    public String getControllerNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONTROLLER_NO, "")) ;
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>) に値をセットします。
     * @param value セットする値DEVICE_NAME
     */
    public void setDeviceName(String value)  // @@GEN_V3@@
    {
        setValue(DEVICE_NAME, value);
    }

    /**
     * 機器名称(<code>DEVICE_NAME</code>) から値を取得します。
     * @return DEVICE_NAME
     */
    public String getDeviceName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(DEVICE_NAME, "")) ;
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
        return "$Id: Machine.java 7254 2010-02-26 05:58:23Z kanda $" ;
    }
}

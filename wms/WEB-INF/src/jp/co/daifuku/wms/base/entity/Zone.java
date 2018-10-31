// $Id: Zone.java 3213 2009-03-02 06:59:20Z arai $
// $LastChangedRevision: 3213 $
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
 * ZONEのエンティティクラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class Zone
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMZONE" ;

    /*
     * テーブル名: DMZONE
     * システム管理区分 :              MANAGEMENT_TYPE     varchar2(1)
     * エリアNo. :                     AREA_NO             varchar2(4)
     * 作業ゾーンNo. :                 WORK_ZONE_NO        varchar2(4)
     * ゾーンNo. :                     ZONE_NO             varchar2(4)
     * ゾーン名称 :                    ZONE_NAME           varchar2(40)
     * 開始棚 :                        START_LOCATION_NO   varchar2(8)
     * 終了棚 :                        END_LOCATION_NO     varchar2(8)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (システム管理区分(<code>MANAGEMENT_TYPE</code>)) */
    public static final FieldName MANAGEMENT_TYPE = new FieldName(STORE_NAME, "MANAGEMENT_TYPE") ;

    /** フィールド定義 (エリアNo.(<code>AREA_NO</code>)) */
    public static final FieldName AREA_NO = new FieldName(STORE_NAME, "AREA_NO") ;

    /** フィールド定義 (作業ゾーンNo.(<code>WORK_ZONE_NO</code>)) */
    public static final FieldName WORK_ZONE_NO = new FieldName(STORE_NAME, "WORK_ZONE_NO") ;

    /** フィールド定義 (ゾーンNo.(<code>ZONE_NO</code>)) */
    public static final FieldName ZONE_NO = new FieldName(STORE_NAME, "ZONE_NO") ;

    /** フィールド定義 (ゾーン名称(<code>ZONE_NAME</code>)) */
    public static final FieldName ZONE_NAME = new FieldName(STORE_NAME, "ZONE_NAME") ;

    /** フィールド定義 (開始棚(<code>START_LOCATION_NO</code>)) */
    public static final FieldName START_LOCATION_NO = new FieldName(STORE_NAME, "START_LOCATION_NO") ;

    /** フィールド定義 (終了棚(<code>END_LOCATION_NO</code>)) */
    public static final FieldName END_LOCATION_NO = new FieldName(STORE_NAME, "END_LOCATION_NO") ;

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
    public Zone()
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
     * 作業ゾーンNo.(<code>WORK_ZONE_NO</code>) に値をセットします。
     * @param value セットする値WORK_ZONE_NO
     */
    public void setWorkZoneNo(String value)  // @@GEN_V3@@
    {
        setValue(WORK_ZONE_NO, value);
    }

    /**
     * 作業ゾーンNo.(<code>WORK_ZONE_NO</code>) から値を取得します。
     * @return WORK_ZONE_NO
     */
    public String getWorkZoneNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WORK_ZONE_NO, "")) ;
    }

    /**
     * ゾーンNo.(<code>ZONE_NO</code>) に値をセットします。
     * @param value セットする値ZONE_NO
     */
    public void setZoneNo(String value)  // @@GEN_V3@@
    {
        setValue(ZONE_NO, value);
    }

    /**
     * ゾーンNo.(<code>ZONE_NO</code>) から値を取得します。
     * @return ZONE_NO
     */
    public String getZoneNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ZONE_NO, "")) ;
    }

    /**
     * ゾーン名称(<code>ZONE_NAME</code>) に値をセットします。
     * @param value セットする値ZONE_NAME
     */
    public void setZoneName(String value)  // @@GEN_V3@@
    {
        setValue(ZONE_NAME, value);
    }

    /**
     * ゾーン名称(<code>ZONE_NAME</code>) から値を取得します。
     * @return ZONE_NAME
     */
    public String getZoneName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ZONE_NAME, "")) ;
    }

    /**
     * 開始棚(<code>START_LOCATION_NO</code>) に値をセットします。
     * @param value セットする値START_LOCATION_NO
     */
    public void setStartLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(START_LOCATION_NO, value);
    }

    /**
     * 開始棚(<code>START_LOCATION_NO</code>) から値を取得します。
     * @return START_LOCATION_NO
     */
    public String getStartLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(START_LOCATION_NO, "")) ;
    }

    /**
     * 終了棚(<code>END_LOCATION_NO</code>) に値をセットします。
     * @param value セットする値END_LOCATION_NO
     */
    public void setEndLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(END_LOCATION_NO, value);
    }

    /**
     * 終了棚(<code>END_LOCATION_NO</code>) から値を取得します。
     * @return END_LOCATION_NO
     */
    public String getEndLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(END_LOCATION_NO, "")) ;
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
        return "$Id: Zone.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}

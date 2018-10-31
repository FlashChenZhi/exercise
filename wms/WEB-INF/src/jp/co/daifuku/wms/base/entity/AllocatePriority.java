// $Id: AllocatePriority.java 5127 2009-10-13 12:20:06Z ota $
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
 * ALLOCATEPRIORITYのエンティティクラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class AllocatePriority
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMALLOCATEPRIORITY" ;

    /*
     * テーブル名: DMALLOCATEPRIORITY
     * 引当パターンNo. :               ALLOCATE_NO         varchar2(3)
     * 引当パターン名称 :              ALLOCATE_NAME       varchar2(40)
     * 引当パターン区分 :              ALLOCATE_TYPE       varchar2(1)
     * 引当対象エリアNo. :             ALLOCATE_AREA       varchar2(4)
     * 引当対象作業場 :                STATION_NO          varchar2(4)
     * 優先順位 :                      ALLOCATE_PRIORITY   number
     * 補充元エリア区分 :              REPLENISHMENT_AREA_TYPEvarchar2(1)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (引当パターンNo.(<code>ALLOCATE_NO</code>)) */
    public static final FieldName ALLOCATE_NO = new FieldName(STORE_NAME, "ALLOCATE_NO") ;

    /** フィールド定義 (引当パターン名称(<code>ALLOCATE_NAME</code>)) */
    public static final FieldName ALLOCATE_NAME = new FieldName(STORE_NAME, "ALLOCATE_NAME") ;

    /** フィールド定義 (引当パターン区分(<code>ALLOCATE_TYPE</code>)) */
    public static final FieldName ALLOCATE_TYPE = new FieldName(STORE_NAME, "ALLOCATE_TYPE") ;

    /** フィールド定義 (引当対象エリアNo.(<code>ALLOCATE_AREA</code>)) */
    public static final FieldName ALLOCATE_AREA = new FieldName(STORE_NAME, "ALLOCATE_AREA") ;

    /** フィールド定義 (引当対象作業場(<code>STATION_NO</code>)) */
    public static final FieldName STATION_NO = new FieldName(STORE_NAME, "STATION_NO") ;

    /** フィールド定義 (優先順位(<code>ALLOCATE_PRIORITY</code>)) */
    public static final FieldName ALLOCATE_PRIORITY = new FieldName(STORE_NAME, "ALLOCATE_PRIORITY") ;

    /** フィールド定義 (補充元エリア区分(<code>REPLENISHMENT_AREA_TYPE</code>)) */
    public static final FieldName REPLENISHMENT_AREA_TYPE = new FieldName(STORE_NAME, "REPLENISHMENT_AREA_TYPE") ;

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
    public AllocatePriority()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 引当パターンNo.(<code>ALLOCATE_NO</code>) に値をセットします。
     * @param value セットする値ALLOCATE_NO
     */
    public void setAllocateNo(String value)  // @@GEN_V3@@
    {
        setValue(ALLOCATE_NO, value);
    }

    /**
     * 引当パターンNo.(<code>ALLOCATE_NO</code>) から値を取得します。
     * @return ALLOCATE_NO
     */
    public String getAllocateNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ALLOCATE_NO, "")) ;
    }

    /**
     * 引当パターン名称(<code>ALLOCATE_NAME</code>) に値をセットします。
     * @param value セットする値ALLOCATE_NAME
     */
    public void setAllocateName(String value)  // @@GEN_V3@@
    {
        setValue(ALLOCATE_NAME, value);
    }

    /**
     * 引当パターン名称(<code>ALLOCATE_NAME</code>) から値を取得します。
     * @return ALLOCATE_NAME
     */
    public String getAllocateName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ALLOCATE_NAME, "")) ;
    }

    /**
     * 引当パターン区分(<code>ALLOCATE_TYPE</code>) に値をセットします。
     * @param value セットする値ALLOCATE_TYPE
     */
    public void setAllocateType(String value)  // @@GEN_V3@@
    {
        setValue(ALLOCATE_TYPE, value);
    }

    /**
     * 引当パターン区分(<code>ALLOCATE_TYPE</code>) から値を取得します。
     * @return ALLOCATE_TYPE
     */
    public String getAllocateType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ALLOCATE_TYPE, "")) ;
    }

    /**
     * 引当対象エリアNo.(<code>ALLOCATE_AREA</code>) に値をセットします。
     * @param value セットする値ALLOCATE_AREA
     */
    public void setAllocateArea(String value)  // @@GEN_V3@@
    {
        setValue(ALLOCATE_AREA, value);
    }

    /**
     * 引当対象エリアNo.(<code>ALLOCATE_AREA</code>) から値を取得します。
     * @return ALLOCATE_AREA
     */
    public String getAllocateArea()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ALLOCATE_AREA, "")) ;
    }

    /**
     * 引当対象作業場(<code>STATION_NO</code>) に値をセットします。
     * @param value セットする値STATION_NO
     */
    public void setStationNo(String value)  // @@GEN_V3@@
    {
        setValue(STATION_NO, value);
    }

    /**
     * 引当対象作業場(<code>STATION_NO</code>) から値を取得します。
     * @return STATION_NO
     */
    public String getStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATION_NO, "")) ;
    }

    /**
     * 優先順位(<code>ALLOCATE_PRIORITY</code>) に値をセットします。
     * @param value セットする値ALLOCATE_PRIORITY
     */
    public void setAllocatePriority(int value)  // @@GEN_V3@@
    {
        setValue(ALLOCATE_PRIORITY, HandlerUtil.toObject(value));
    }

    /**
     * 優先順位(<code>ALLOCATE_PRIORITY</code>) から値を取得します。
     * @return ALLOCATE_PRIORITY
     */
    public int getAllocatePriority()  // @@GEN_V3@@
    {
        return getBigDecimal(ALLOCATE_PRIORITY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 補充元エリア区分(<code>REPLENISHMENT_AREA_TYPE</code>) に値をセットします。
     * @param value セットする値REPLENISHMENT_AREA_TYPE
     */
    public void setReplenishmentAreaType(String value)  // @@GEN_V3@@
    {
        setValue(REPLENISHMENT_AREA_TYPE, value);
    }

    /**
     * 補充元エリア区分(<code>REPLENISHMENT_AREA_TYPE</code>) から値を取得します。
     * @return REPLENISHMENT_AREA_TYPE
     */
    public String getReplenishmentAreaType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REPLENISHMENT_AREA_TYPE, "")) ;
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
        return "$Id: AllocatePriority.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}

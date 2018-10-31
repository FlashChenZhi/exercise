// $Id: Area.java 5127 2009-10-13 12:20:06Z ota $
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
 * AREAのエンティティクラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class Area
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMAREA" ;

    /*
     * テーブル名: DMAREA
     * システム管理区分 :              MANAGEMENT_TYPE     varchar2(1)
     * エリアNo. :                     AREA_NO             varchar2(4)
     * エリア名称 :                    AREA_NAME           varchar2(40)
     * エリア種別 :                    AREA_TYPE           varchar2(1)
     * 棚管理方式 :                    LOCATION_TYPE       varchar2(1)
     * 棚表示形式 :                    LOCATION_STYLE      varchar2(16)
     * 仮置在庫作成区分 :              TEMPORARY_AREA_TYPE varchar2(1)
     * 移動先仮置エリア :              TEMPORARY_AREA      varchar2(4)
     * 空棚検索方法 :                  VACANT_SEARCH_TYPE  varchar2(2)
     * 倉庫ステーションNo :            WHSTATION_NO        varchar2(16)
     * 入荷エリア :                    RECEIVING_AREA      varchar2(4)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (システム管理区分(<code>MANAGEMENT_TYPE</code>)) */
    public static final FieldName MANAGEMENT_TYPE = new FieldName(STORE_NAME, "MANAGEMENT_TYPE") ;

    /** フィールド定義 (エリアNo.(<code>AREA_NO</code>)) */
    public static final FieldName AREA_NO = new FieldName(STORE_NAME, "AREA_NO") ;

    /** フィールド定義 (エリア名称(<code>AREA_NAME</code>)) */
    public static final FieldName AREA_NAME = new FieldName(STORE_NAME, "AREA_NAME") ;

    /** フィールド定義 (エリア種別(<code>AREA_TYPE</code>)) */
    public static final FieldName AREA_TYPE = new FieldName(STORE_NAME, "AREA_TYPE") ;

    /** フィールド定義 (棚管理方式(<code>LOCATION_TYPE</code>)) */
    public static final FieldName LOCATION_TYPE = new FieldName(STORE_NAME, "LOCATION_TYPE") ;

    /** フィールド定義 (棚表示形式(<code>LOCATION_STYLE</code>)) */
    public static final FieldName LOCATION_STYLE = new FieldName(STORE_NAME, "LOCATION_STYLE") ;

    /** フィールド定義 (仮置在庫作成区分(<code>TEMPORARY_AREA_TYPE</code>)) */
    public static final FieldName TEMPORARY_AREA_TYPE = new FieldName(STORE_NAME, "TEMPORARY_AREA_TYPE") ;

    /** フィールド定義 (移動先仮置エリア(<code>TEMPORARY_AREA</code>)) */
    public static final FieldName TEMPORARY_AREA = new FieldName(STORE_NAME, "TEMPORARY_AREA") ;

    /** フィールド定義 (空棚検索方法(<code>VACANT_SEARCH_TYPE</code>)) */
    public static final FieldName VACANT_SEARCH_TYPE = new FieldName(STORE_NAME, "VACANT_SEARCH_TYPE") ;

    /** フィールド定義 (倉庫ステーションNo(<code>WHSTATION_NO</code>)) */
    public static final FieldName WHSTATION_NO = new FieldName(STORE_NAME, "WHSTATION_NO") ;

    /** フィールド定義 (入荷エリア(<code>RECEIVING_AREA</code>)) */
    public static final FieldName RECEIVING_AREA = new FieldName(STORE_NAME, "RECEIVING_AREA") ;

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
    public Area()
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
     * エリア名称(<code>AREA_NAME</code>) に値をセットします。
     * @param value セットする値AREA_NAME
     */
    public void setAreaName(String value)  // @@GEN_V3@@
    {
        setValue(AREA_NAME, value);
    }

    /**
     * エリア名称(<code>AREA_NAME</code>) から値を取得します。
     * @return AREA_NAME
     */
    public String getAreaName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(AREA_NAME, "")) ;
    }

    /**
     * エリア種別(<code>AREA_TYPE</code>) に値をセットします。
     * @param value セットする値AREA_TYPE
     */
    public void setAreaType(String value)  // @@GEN_V3@@
    {
        setValue(AREA_TYPE, value);
    }

    /**
     * エリア種別(<code>AREA_TYPE</code>) から値を取得します。
     * @return AREA_TYPE
     */
    public String getAreaType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(AREA_TYPE, "")) ;
    }

    /**
     * 棚管理方式(<code>LOCATION_TYPE</code>) に値をセットします。
     * @param value セットする値LOCATION_TYPE
     */
    public void setLocationType(String value)  // @@GEN_V3@@
    {
        setValue(LOCATION_TYPE, value);
    }

    /**
     * 棚管理方式(<code>LOCATION_TYPE</code>) から値を取得します。
     * @return LOCATION_TYPE
     */
    public String getLocationType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_TYPE, "")) ;
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>) に値をセットします。
     * @param value セットする値LOCATION_STYLE
     */
    public void setLocationStyle(String value)  // @@GEN_V3@@
    {
        setValue(LOCATION_STYLE, value);
    }

    /**
     * 棚表示形式(<code>LOCATION_STYLE</code>) から値を取得します。
     * @return LOCATION_STYLE
     */
    public String getLocationStyle()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_STYLE, "")) ;
    }

    /**
     * 仮置在庫作成区分(<code>TEMPORARY_AREA_TYPE</code>) に値をセットします。
     * @param value セットする値TEMPORARY_AREA_TYPE
     */
    public void setTemporaryAreaType(String value)  // @@GEN_V3@@
    {
        setValue(TEMPORARY_AREA_TYPE, value);
    }

    /**
     * 仮置在庫作成区分(<code>TEMPORARY_AREA_TYPE</code>) から値を取得します。
     * @return TEMPORARY_AREA_TYPE
     */
    public String getTemporaryAreaType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TEMPORARY_AREA_TYPE, "")) ;
    }

    /**
     * 移動先仮置エリア(<code>TEMPORARY_AREA</code>) に値をセットします。
     * @param value セットする値TEMPORARY_AREA
     */
    public void setTemporaryArea(String value)  // @@GEN_V3@@
    {
        setValue(TEMPORARY_AREA, value);
    }

    /**
     * 移動先仮置エリア(<code>TEMPORARY_AREA</code>) から値を取得します。
     * @return TEMPORARY_AREA
     */
    public String getTemporaryArea()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TEMPORARY_AREA, "")) ;
    }

    /**
     * 空棚検索方法(<code>VACANT_SEARCH_TYPE</code>) に値をセットします。
     * @param value セットする値VACANT_SEARCH_TYPE
     */
    public void setVacantSearchType(String value)  // @@GEN_V3@@
    {
        setValue(VACANT_SEARCH_TYPE, value);
    }

    /**
     * 空棚検索方法(<code>VACANT_SEARCH_TYPE</code>) から値を取得します。
     * @return VACANT_SEARCH_TYPE
     */
    public String getVacantSearchType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(VACANT_SEARCH_TYPE, "")) ;
    }

    /**
     * 倉庫ステーションNo(<code>WHSTATION_NO</code>) に値をセットします。
     * @param value セットする値WHSTATION_NO
     */
    public void setWhstationNo(String value)  // @@GEN_V3@@
    {
        setValue(WHSTATION_NO, value);
    }

    /**
     * 倉庫ステーションNo(<code>WHSTATION_NO</code>) から値を取得します。
     * @return WHSTATION_NO
     */
    public String getWhstationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WHSTATION_NO, "")) ;
    }

    /**
     * 入荷エリア(<code>RECEIVING_AREA</code>) に値をセットします。
     * @param value セットする値RECEIVING_AREA
     */
    public void setReceivingArea(String value)  // @@GEN_V3@@
    {
        setValue(RECEIVING_AREA, value);
    }

    /**
     * 入荷エリア(<code>RECEIVING_AREA</code>) から値を取得します。
     * @return RECEIVING_AREA
     */
    public String getReceivingArea()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RECEIVING_AREA, "")) ;
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
        return "$Id: Area.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}

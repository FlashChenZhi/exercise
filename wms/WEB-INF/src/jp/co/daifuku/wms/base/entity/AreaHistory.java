// $Id: AreaHistory.java 1544 2008-11-25 09:32:24Z dmori $
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
 * AREAHISTORYのエンティティクラスです。
 *
 * @version $Revision: 1544 $, $Date: 2008-11-25 18:32:24 +0900 (火, 25 11 2008) $
 * @author  ss
 * @author  Last commit: $Author: dmori $
 */

public class AreaHistory
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNAREAHISTORY" ;

    /*
     * テーブル名: DNAREAHISTORY
     * 出力日時 :                      LOG_DATE            timestamp
     * 出力日時(GMT) :                 LOG_DATE_GMT        timestamp
     * ユーザID :                      USER_ID             varchar2(20)
     * ユーザ名称 :                    USER_NAME           varchar2(40)
     * 端末No、RFTNo :                 TERMINAL_NO         varchar2(4)
     * 端末名称 :                      TERMINAL_NAME       varchar2(60)
     * IPアドレス :                    IP_ADDRESS          varchar2(64)
     * DS番号 :                        DS_NO               varchar2(6)
     * 画面リソースキー :              PAGENAMERESOURCEKEY varchar2(40)
     * 更新区分 :                      UPDATE_KIND         varchar2(1)
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
     * 修正後エリア名称 :              UPDATE_AREA_NAME    varchar2(40)
     * 修正後仮置在庫作成区分 :        UPDATE_TEMP_AREA_TYPEvarchar2(1)
     * 修正後移動先仮置エリア :        UPDATE_TEMP_AREA    varchar2(4)
     * 修正後空棚検索方法 :            UPDATE_VACANT_SEARCH_TYPEvarchar2(2)
     * 修正後入荷エリア :              UPDATE_RECEIVING_AREAvarchar2(4)
     */

    /** フィールド定義 (出力日時(<code>LOG_DATE</code>)) */
    public static final FieldName LOG_DATE = new FieldName(STORE_NAME, "LOG_DATE") ;

    /** フィールド定義 (出力日時(GMT)(<code>LOG_DATE_GMT</code>)) */
    public static final FieldName LOG_DATE_GMT = new FieldName(STORE_NAME, "LOG_DATE_GMT") ;

    /** フィールド定義 (ユーザID(<code>USER_ID</code>)) */
    public static final FieldName USER_ID = new FieldName(STORE_NAME, "USER_ID") ;

    /** フィールド定義 (ユーザ名称(<code>USER_NAME</code>)) */
    public static final FieldName USER_NAME = new FieldName(STORE_NAME, "USER_NAME") ;

    /** フィールド定義 (端末No、RFTNo(<code>TERMINAL_NO</code>)) */
    public static final FieldName TERMINAL_NO = new FieldName(STORE_NAME, "TERMINAL_NO") ;

    /** フィールド定義 (端末名称(<code>TERMINAL_NAME</code>)) */
    public static final FieldName TERMINAL_NAME = new FieldName(STORE_NAME, "TERMINAL_NAME") ;

    /** フィールド定義 (IPアドレス(<code>IP_ADDRESS</code>)) */
    public static final FieldName IP_ADDRESS = new FieldName(STORE_NAME, "IP_ADDRESS") ;

    /** フィールド定義 (DS番号(<code>DS_NO</code>)) */
    public static final FieldName DS_NO = new FieldName(STORE_NAME, "DS_NO") ;

    /** フィールド定義 (画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)) */
    public static final FieldName PAGENAMERESOURCEKEY = new FieldName(STORE_NAME, "PAGENAMERESOURCEKEY") ;

    /** フィールド定義 (更新区分(<code>UPDATE_KIND</code>)) */
    public static final FieldName UPDATE_KIND = new FieldName(STORE_NAME, "UPDATE_KIND") ;

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

    /** フィールド定義 (修正後エリア名称(<code>UPDATE_AREA_NAME</code>)) */
    public static final FieldName UPDATE_AREA_NAME = new FieldName(STORE_NAME, "UPDATE_AREA_NAME") ;

    /** フィールド定義 (修正後仮置在庫作成区分(<code>UPDATE_TEMP_AREA_TYPE</code>)) */
    public static final FieldName UPDATE_TEMP_AREA_TYPE = new FieldName(STORE_NAME, "UPDATE_TEMP_AREA_TYPE") ;

    /** フィールド定義 (修正後移動先仮置エリア(<code>UPDATE_TEMP_AREA</code>)) */
    public static final FieldName UPDATE_TEMP_AREA = new FieldName(STORE_NAME, "UPDATE_TEMP_AREA") ;

    /** フィールド定義 (修正後空棚検索方法(<code>UPDATE_VACANT_SEARCH_TYPE</code>)) */
    public static final FieldName UPDATE_VACANT_SEARCH_TYPE = new FieldName(STORE_NAME, "UPDATE_VACANT_SEARCH_TYPE") ;

    /** フィールド定義 (修正後入荷エリア(<code>UPDATE_RECEIVING_AREA</code>)) */
    public static final FieldName UPDATE_RECEIVING_AREA = new FieldName(STORE_NAME, "UPDATE_RECEIVING_AREA") ;


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
    public AreaHistory()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 出力日時(<code>LOG_DATE</code>) に値をセットします。
     * @param value セットする値LOG_DATE
     */
    public void setLogDate(Date value)  // @@GEN_V3@@
    {
        setValue(LOG_DATE, value);
    }

    /**
     * 出力日時(<code>LOG_DATE</code>) から値を取得します。
     * @return LOG_DATE
     */
    public Date getLogDate()  // @@GEN_V3@@
    {
        return (Date)getValue(LOG_DATE, null) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>) に値をセットします。
     * @param value セットする値LOG_DATE_GMT
     */
    public void setLogDateGmt(Date value)  // @@GEN_V3@@
    {
        setValue(LOG_DATE_GMT, value);
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>) から値を取得します。
     * @return LOG_DATE_GMT
     */
    public Date getLogDateGmt()  // @@GEN_V3@@
    {
        return (Date)getValue(LOG_DATE_GMT, null) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>) に値をセットします。
     * @param value セットする値USER_ID
     */
    public void setUserId(String value)  // @@GEN_V3@@
    {
        setValue(USER_ID, value);
    }

    /**
     * ユーザID(<code>USER_ID</code>) から値を取得します。
     * @return USER_ID
     */
    public String getUserId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USER_ID, "")) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>) に値をセットします。
     * @param value セットする値USER_NAME
     */
    public void setUserName(String value)  // @@GEN_V3@@
    {
        setValue(USER_NAME, value);
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>) から値を取得します。
     * @return USER_NAME
     */
    public String getUserName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USER_NAME, "")) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値TERMINAL_NO
     */
    public void setTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_NO, value);
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>) から値を取得します。
     * @return TERMINAL_NO
     */
    public String getTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_NO, "")) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>) に値をセットします。
     * @param value セットする値TERMINAL_NAME
     */
    public void setTerminalName(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_NAME, value);
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>) から値を取得します。
     * @return TERMINAL_NAME
     */
    public String getTerminalName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_NAME, "")) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>) に値をセットします。
     * @param value セットする値IP_ADDRESS
     */
    public void setIpAddress(String value)  // @@GEN_V3@@
    {
        setValue(IP_ADDRESS, value);
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>) から値を取得します。
     * @return IP_ADDRESS
     */
    public String getIpAddress()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(IP_ADDRESS, "")) ;
    }

    /**
     * DS番号(<code>DS_NO</code>) に値をセットします。
     * @param value セットする値DS_NO
     */
    public void setDsNo(String value)  // @@GEN_V3@@
    {
        setValue(DS_NO, value);
    }

    /**
     * DS番号(<code>DS_NO</code>) から値を取得します。
     * @return DS_NO
     */
    public String getDsNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(DS_NO, "")) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>) に値をセットします。
     * @param value セットする値PAGENAMERESOURCEKEY
     */
    public void setPagenameResourcekey(String value)  // @@GEN_V3@@
    {
        setValue(PAGENAMERESOURCEKEY, value);
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>) から値を取得します。
     * @return PAGENAMERESOURCEKEY
     */
    public String getPagenameResourcekey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PAGENAMERESOURCEKEY, "")) ;
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>) に値をセットします。
     * @param value セットする値UPDATE_KIND
     */
    public void setUpdateKind(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_KIND, value);
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>) から値を取得します。
     * @return UPDATE_KIND
     */
    public String getUpdateKind()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_KIND, "")) ;
    }

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
     * 修正後エリア名称(<code>UPDATE_AREA_NAME</code>) に値をセットします。
     * @param value セットする値UPDATE_AREA_NAME
     */
    public void setUpdateAreaName(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_AREA_NAME, value);
    }

    /**
     * 修正後エリア名称(<code>UPDATE_AREA_NAME</code>) から値を取得します。
     * @return UPDATE_AREA_NAME
     */
    public String getUpdateAreaName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_AREA_NAME, "")) ;
    }

    /**
     * 修正後仮置在庫作成区分(<code>UPDATE_TEMP_AREA_TYPE</code>) に値をセットします。
     * @param value セットする値UPDATE_TEMP_AREA_TYPE
     */
    public void setUpdateTempAreaType(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_TEMP_AREA_TYPE, value);
    }

    /**
     * 修正後仮置在庫作成区分(<code>UPDATE_TEMP_AREA_TYPE</code>) から値を取得します。
     * @return UPDATE_TEMP_AREA_TYPE
     */
    public String getUpdateTempAreaType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_TEMP_AREA_TYPE, "")) ;
    }

    /**
     * 修正後移動先仮置エリア(<code>UPDATE_TEMP_AREA</code>) に値をセットします。
     * @param value セットする値UPDATE_TEMP_AREA
     */
    public void setUpdateTempArea(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_TEMP_AREA, value);
    }

    /**
     * 修正後移動先仮置エリア(<code>UPDATE_TEMP_AREA</code>) から値を取得します。
     * @return UPDATE_TEMP_AREA
     */
    public String getUpdateTempArea()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_TEMP_AREA, "")) ;
    }

    /**
     * 修正後空棚検索方法(<code>UPDATE_VACANT_SEARCH_TYPE</code>) に値をセットします。
     * @param value セットする値UPDATE_VACANT_SEARCH_TYPE
     */
    public void setUpdateVacantSearchType(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_VACANT_SEARCH_TYPE, value);
    }

    /**
     * 修正後空棚検索方法(<code>UPDATE_VACANT_SEARCH_TYPE</code>) から値を取得します。
     * @return UPDATE_VACANT_SEARCH_TYPE
     */
    public String getUpdateVacantSearchType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_VACANT_SEARCH_TYPE, "")) ;
    }

    /**
     * 修正後入荷エリア(<code>UPDATE_RECEIVING_AREA</code>) に値をセットします。
     * @param value セットする値UPDATE_RECEIVING_AREA
     */
    public void setUpdateReceivingArea(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_RECEIVING_AREA, value);
    }

    /**
     * 修正後入荷エリア(<code>UPDATE_RECEIVING_AREA</code>) から値を取得します。
     * @return UPDATE_RECEIVING_AREA
     */
    public String getUpdateReceivingArea()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_RECEIVING_AREA, "")) ;
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
        return "$Id: AreaHistory.java 1544 2008-11-25 09:32:24Z dmori $" ;
    }
}

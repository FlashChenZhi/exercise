// $Id: LocateHistory.java 5652 2009-11-11 02:40:44Z okayama $
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
 * LOCATEHISTORYのエンティティクラスです。
 *
 * @version $Revision: 5652 $, $Date: 2009-11-11 11:40:44 +0900 (水, 11 11 2009) $
 * @author  ss
 * @author  Last commit: $Author: okayama $
 */

public class LocateHistory
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNLOCATEHISTORY" ;

    /*
     * テーブル名: DNLOCATEHISTORY
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
     * 棚No. :                         LOCATION_NO         varchar2(11)
     * アイルNo. :                     AISLE_NO            number
     * バンク :                        BANK_NO             number
     * ベイ :                          BAY_NO              number
     * レベル :                        LEVEL_NO            number
     * 状態フラグ :                    STATUS_FLAG         varchar2(1)
     * 修正後アイルNo. :               UPDATE_AISLE_NO     number
     * 棚表示形式 :                    LOCATION_STYLE      varchar2(16)
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

    /** フィールド定義 (修正後アイルNo.(<code>UPDATE_AISLE_NO</code>)) */
    public static final FieldName UPDATE_AISLE_NO = new FieldName(STORE_NAME, "UPDATE_AISLE_NO") ;

    /** フィールド定義 (棚表示形式(<code>LOCATION_STYLE</code>)) */
    public static final FieldName LOCATION_STYLE = new FieldName(STORE_NAME, "LOCATION_STYLE") ;


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
    public LocateHistory()
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
     * 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>) に値をセットします。
     * @param value セットする値UPDATE_AISLE_NO
     */
    public void setUpdateAisleNo(int value)  // @@GEN_V3@@
    {
        setValue(UPDATE_AISLE_NO, HandlerUtil.toObject(value));
    }

    /**
     * 修正後アイルNo.(<code>UPDATE_AISLE_NO</code>) から値を取得します。
     * @return UPDATE_AISLE_NO
     */
    public int getUpdateAisleNo()  // @@GEN_V3@@
    {
        return getBigDecimal(UPDATE_AISLE_NO, BigDecimal.ZERO).intValue() ;
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
        return "$Id: LocateHistory.java 5652 2009-11-11 02:40:44Z okayama $" ;
    }
}

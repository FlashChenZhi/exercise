// $Id: CustomerHistory.java 2610 2009-01-08 08:13:44Z ota $
// $LastChangedRevision: 2610 $
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
 * CUSTOMERHISTORYのエンティティクラスです。
 *
 * @version $Revision: 2610 $, $Date: 2009-01-08 17:13:44 +0900 (木, 08 1 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class CustomerHistory
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNCUSTOMERHISTORY" ;

    /*
     * テーブル名: DNCUSTOMERHISTORY
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
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 出荷先コード :                  CUSTOMER_CODE       varchar2(16)
     * 出荷先名称 :                    CUSTOMER_NAME       varchar2(40)
     * 修正後出荷先名称 :              UPDATE_CUSTOMER_NAMEvarchar2(40)
     * ルート :                        ROUTE               varchar2(4)
     * 修正後ルート :                  UPDATE_ROUTE        varchar2(4)
     * 郵便番号 :                      POSTAL_CODE         varchar2(10)
     * 修正後郵便番号 :                UPDATE_POSTAL_CODE  varchar2(10)
     * 都道府県名 :                    PREFECTURE          varchar2(20)
     * 修正後都道府県名 :              UPDATE_PREFECTURE   varchar2(20)
     * 住所 :                          ADDRESS1            varchar2(100)
     * 修正後住所 :                    UPDATE_ADDRESS1     varchar2(100)
     * ビル名等 :                      ADDRESS2            varchar2(100)
     * 修正後ビル名等 :                UPDATE_ADDRESS2     varchar2(100)
     * TEL :                           TELEPHONE           varchar2(13)
     * 修正後TEL :                     UPDATE_TELEPHONE    varchar2(13)
     * 連絡先１ :                      CONTACT1            varchar2(50)
     * 修正後連絡先１ :                UPDATE_CONTACT1     varchar2(50)
     * 連絡先２ :                      CONTACT2            varchar2(20)
     * 修正後連絡先２ :                UPDATE_CONTACT2     varchar2(20)
     * 仕分場所 :                      SORTING_PLACE       varchar2(16)
     * 修正後仕分場所 :                UPDATE_SORTING_PLACEvarchar2(16)
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

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (出荷先コード(<code>CUSTOMER_CODE</code>)) */
    public static final FieldName CUSTOMER_CODE = new FieldName(STORE_NAME, "CUSTOMER_CODE") ;

    /** フィールド定義 (出荷先名称(<code>CUSTOMER_NAME</code>)) */
    public static final FieldName CUSTOMER_NAME = new FieldName(STORE_NAME, "CUSTOMER_NAME") ;

    /** フィールド定義 (修正後出荷先名称(<code>UPDATE_CUSTOMER_NAME</code>)) */
    public static final FieldName UPDATE_CUSTOMER_NAME = new FieldName(STORE_NAME, "UPDATE_CUSTOMER_NAME") ;

    /** フィールド定義 (ルート(<code>ROUTE</code>)) */
    public static final FieldName ROUTE = new FieldName(STORE_NAME, "ROUTE") ;

    /** フィールド定義 (修正後ルート(<code>UPDATE_ROUTE</code>)) */
    public static final FieldName UPDATE_ROUTE = new FieldName(STORE_NAME, "UPDATE_ROUTE") ;

    /** フィールド定義 (郵便番号(<code>POSTAL_CODE</code>)) */
    public static final FieldName POSTAL_CODE = new FieldName(STORE_NAME, "POSTAL_CODE") ;

    /** フィールド定義 (修正後郵便番号(<code>UPDATE_POSTAL_CODE</code>)) */
    public static final FieldName UPDATE_POSTAL_CODE = new FieldName(STORE_NAME, "UPDATE_POSTAL_CODE") ;

    /** フィールド定義 (都道府県名(<code>PREFECTURE</code>)) */
    public static final FieldName PREFECTURE = new FieldName(STORE_NAME, "PREFECTURE") ;

    /** フィールド定義 (修正後都道府県名(<code>UPDATE_PREFECTURE</code>)) */
    public static final FieldName UPDATE_PREFECTURE = new FieldName(STORE_NAME, "UPDATE_PREFECTURE") ;

    /** フィールド定義 (住所(<code>ADDRESS1</code>)) */
    public static final FieldName ADDRESS1 = new FieldName(STORE_NAME, "ADDRESS1") ;

    /** フィールド定義 (修正後住所(<code>UPDATE_ADDRESS1</code>)) */
    public static final FieldName UPDATE_ADDRESS1 = new FieldName(STORE_NAME, "UPDATE_ADDRESS1") ;

    /** フィールド定義 (ビル名等(<code>ADDRESS2</code>)) */
    public static final FieldName ADDRESS2 = new FieldName(STORE_NAME, "ADDRESS2") ;

    /** フィールド定義 (修正後ビル名等(<code>UPDATE_ADDRESS2</code>)) */
    public static final FieldName UPDATE_ADDRESS2 = new FieldName(STORE_NAME, "UPDATE_ADDRESS2") ;

    /** フィールド定義 (TEL(<code>TELEPHONE</code>)) */
    public static final FieldName TELEPHONE = new FieldName(STORE_NAME, "TELEPHONE") ;

    /** フィールド定義 (修正後TEL(<code>UPDATE_TELEPHONE</code>)) */
    public static final FieldName UPDATE_TELEPHONE = new FieldName(STORE_NAME, "UPDATE_TELEPHONE") ;

    /** フィールド定義 (連絡先１(<code>CONTACT1</code>)) */
    public static final FieldName CONTACT1 = new FieldName(STORE_NAME, "CONTACT1") ;

    /** フィールド定義 (修正後連絡先１(<code>UPDATE_CONTACT1</code>)) */
    public static final FieldName UPDATE_CONTACT1 = new FieldName(STORE_NAME, "UPDATE_CONTACT1") ;

    /** フィールド定義 (連絡先２(<code>CONTACT2</code>)) */
    public static final FieldName CONTACT2 = new FieldName(STORE_NAME, "CONTACT2") ;

    /** フィールド定義 (修正後連絡先２(<code>UPDATE_CONTACT2</code>)) */
    public static final FieldName UPDATE_CONTACT2 = new FieldName(STORE_NAME, "UPDATE_CONTACT2") ;

    /** フィールド定義 (仕分場所(<code>SORTING_PLACE</code>)) */
    public static final FieldName SORTING_PLACE = new FieldName(STORE_NAME, "SORTING_PLACE") ;

    /** フィールド定義 (修正後仕分場所(<code>UPDATE_SORTING_PLACE</code>)) */
    public static final FieldName UPDATE_SORTING_PLACE = new FieldName(STORE_NAME, "UPDATE_SORTING_PLACE") ;


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
    public CustomerHistory()
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
     * 荷主コード(<code>CONSIGNOR_CODE</code>) に値をセットします。
     * @param value セットする値CONSIGNOR_CODE
     */
    public void setConsignorCode(String value)  // @@GEN_V3@@
    {
        setValue(CONSIGNOR_CODE, value);
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>) から値を取得します。
     * @return CONSIGNOR_CODE
     */
    public String getConsignorCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONSIGNOR_CODE, "")) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>) に値をセットします。
     * @param value セットする値CUSTOMER_CODE
     */
    public void setCustomerCode(String value)  // @@GEN_V3@@
    {
        setValue(CUSTOMER_CODE, value);
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>) から値を取得します。
     * @return CUSTOMER_CODE
     */
    public String getCustomerCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CUSTOMER_CODE, "")) ;
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>) に値をセットします。
     * @param value セットする値CUSTOMER_NAME
     */
    public void setCustomerName(String value)  // @@GEN_V3@@
    {
        setValue(CUSTOMER_NAME, value);
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>) から値を取得します。
     * @return CUSTOMER_NAME
     */
    public String getCustomerName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CUSTOMER_NAME, "")) ;
    }

    /**
     * 修正後出荷先名称(<code>UPDATE_CUSTOMER_NAME</code>) に値をセットします。
     * @param value セットする値UPDATE_CUSTOMER_NAME
     */
    public void setUpdateCustomerName(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_CUSTOMER_NAME, value);
    }

    /**
     * 修正後出荷先名称(<code>UPDATE_CUSTOMER_NAME</code>) から値を取得します。
     * @return UPDATE_CUSTOMER_NAME
     */
    public String getUpdateCustomerName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_CUSTOMER_NAME, "")) ;
    }

    /**
     * ルート(<code>ROUTE</code>) に値をセットします。
     * @param value セットする値ROUTE
     */
    public void setRoute(String value)  // @@GEN_V3@@
    {
        setValue(ROUTE, value);
    }

    /**
     * ルート(<code>ROUTE</code>) から値を取得します。
     * @return ROUTE
     */
    public String getRoute()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ROUTE, "")) ;
    }

    /**
     * 修正後ルート(<code>UPDATE_ROUTE</code>) に値をセットします。
     * @param value セットする値UPDATE_ROUTE
     */
    public void setUpdateRoute(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_ROUTE, value);
    }

    /**
     * 修正後ルート(<code>UPDATE_ROUTE</code>) から値を取得します。
     * @return UPDATE_ROUTE
     */
    public String getUpdateRoute()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_ROUTE, "")) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>) に値をセットします。
     * @param value セットする値POSTAL_CODE
     */
    public void setPostalCode(String value)  // @@GEN_V3@@
    {
        setValue(POSTAL_CODE, value);
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>) から値を取得します。
     * @return POSTAL_CODE
     */
    public String getPostalCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(POSTAL_CODE, "")) ;
    }

    /**
     * 修正後郵便番号(<code>UPDATE_POSTAL_CODE</code>) に値をセットします。
     * @param value セットする値UPDATE_POSTAL_CODE
     */
    public void setUpdatePostalCode(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_POSTAL_CODE, value);
    }

    /**
     * 修正後郵便番号(<code>UPDATE_POSTAL_CODE</code>) から値を取得します。
     * @return UPDATE_POSTAL_CODE
     */
    public String getUpdatePostalCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_POSTAL_CODE, "")) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>) に値をセットします。
     * @param value セットする値PREFECTURE
     */
    public void setPrefecture(String value)  // @@GEN_V3@@
    {
        setValue(PREFECTURE, value);
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>) から値を取得します。
     * @return PREFECTURE
     */
    public String getPrefecture()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PREFECTURE, "")) ;
    }

    /**
     * 修正後都道府県名(<code>UPDATE_PREFECTURE</code>) に値をセットします。
     * @param value セットする値UPDATE_PREFECTURE
     */
    public void setUpdatePrefecture(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_PREFECTURE, value);
    }

    /**
     * 修正後都道府県名(<code>UPDATE_PREFECTURE</code>) から値を取得します。
     * @return UPDATE_PREFECTURE
     */
    public String getUpdatePrefecture()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_PREFECTURE, "")) ;
    }

    /**
     * 住所(<code>ADDRESS1</code>) に値をセットします。
     * @param value セットする値ADDRESS1
     */
    public void setAddress1(String value)  // @@GEN_V3@@
    {
        setValue(ADDRESS1, value);
    }

    /**
     * 住所(<code>ADDRESS1</code>) から値を取得します。
     * @return ADDRESS1
     */
    public String getAddress1()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ADDRESS1, "")) ;
    }

    /**
     * 修正後住所(<code>UPDATE_ADDRESS1</code>) に値をセットします。
     * @param value セットする値UPDATE_ADDRESS1
     */
    public void setUpdateAddress1(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_ADDRESS1, value);
    }

    /**
     * 修正後住所(<code>UPDATE_ADDRESS1</code>) から値を取得します。
     * @return UPDATE_ADDRESS1
     */
    public String getUpdateAddress1()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_ADDRESS1, "")) ;
    }

    /**
     * ビル名等(<code>ADDRESS2</code>) に値をセットします。
     * @param value セットする値ADDRESS2
     */
    public void setAddress2(String value)  // @@GEN_V3@@
    {
        setValue(ADDRESS2, value);
    }

    /**
     * ビル名等(<code>ADDRESS2</code>) から値を取得します。
     * @return ADDRESS2
     */
    public String getAddress2()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ADDRESS2, "")) ;
    }

    /**
     * 修正後ビル名等(<code>UPDATE_ADDRESS2</code>) に値をセットします。
     * @param value セットする値UPDATE_ADDRESS2
     */
    public void setUpdateAddress2(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_ADDRESS2, value);
    }

    /**
     * 修正後ビル名等(<code>UPDATE_ADDRESS2</code>) から値を取得します。
     * @return UPDATE_ADDRESS2
     */
    public String getUpdateAddress2()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_ADDRESS2, "")) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>) に値をセットします。
     * @param value セットする値TELEPHONE
     */
    public void setTelephone(String value)  // @@GEN_V3@@
    {
        setValue(TELEPHONE, value);
    }

    /**
     * TEL(<code>TELEPHONE</code>) から値を取得します。
     * @return TELEPHONE
     */
    public String getTelephone()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TELEPHONE, "")) ;
    }

    /**
     * 修正後TEL(<code>UPDATE_TELEPHONE</code>) に値をセットします。
     * @param value セットする値UPDATE_TELEPHONE
     */
    public void setUpdateTelephone(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_TELEPHONE, value);
    }

    /**
     * 修正後TEL(<code>UPDATE_TELEPHONE</code>) から値を取得します。
     * @return UPDATE_TELEPHONE
     */
    public String getUpdateTelephone()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_TELEPHONE, "")) ;
    }

    /**
     * 連絡先１(<code>CONTACT1</code>) に値をセットします。
     * @param value セットする値CONTACT1
     */
    public void setContact1(String value)  // @@GEN_V3@@
    {
        setValue(CONTACT1, value);
    }

    /**
     * 連絡先１(<code>CONTACT1</code>) から値を取得します。
     * @return CONTACT1
     */
    public String getContact1()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONTACT1, "")) ;
    }

    /**
     * 修正後連絡先１(<code>UPDATE_CONTACT1</code>) に値をセットします。
     * @param value セットする値UPDATE_CONTACT1
     */
    public void setUpdateContact1(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_CONTACT1, value);
    }

    /**
     * 修正後連絡先１(<code>UPDATE_CONTACT1</code>) から値を取得します。
     * @return UPDATE_CONTACT1
     */
    public String getUpdateContact1()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_CONTACT1, "")) ;
    }

    /**
     * 連絡先２(<code>CONTACT2</code>) に値をセットします。
     * @param value セットする値CONTACT2
     */
    public void setContact2(String value)  // @@GEN_V3@@
    {
        setValue(CONTACT2, value);
    }

    /**
     * 連絡先２(<code>CONTACT2</code>) から値を取得します。
     * @return CONTACT2
     */
    public String getContact2()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONTACT2, "")) ;
    }

    /**
     * 修正後連絡先２(<code>UPDATE_CONTACT2</code>) に値をセットします。
     * @param value セットする値UPDATE_CONTACT2
     */
    public void setUpdateContact2(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_CONTACT2, value);
    }

    /**
     * 修正後連絡先２(<code>UPDATE_CONTACT2</code>) から値を取得します。
     * @return UPDATE_CONTACT2
     */
    public String getUpdateContact2()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_CONTACT2, "")) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>) に値をセットします。
     * @param value セットする値SORTING_PLACE
     */
    public void setSortingPlace(String value)  // @@GEN_V3@@
    {
        setValue(SORTING_PLACE, value);
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>) から値を取得します。
     * @return SORTING_PLACE
     */
    public String getSortingPlace()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SORTING_PLACE, "")) ;
    }

    /**
     * 修正後仕分場所(<code>UPDATE_SORTING_PLACE</code>) に値をセットします。
     * @param value セットする値UPDATE_SORTING_PLACE
     */
    public void setUpdateSortingPlace(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_SORTING_PLACE, value);
    }

    /**
     * 修正後仕分場所(<code>UPDATE_SORTING_PLACE</code>) から値を取得します。
     * @return UPDATE_SORTING_PLACE
     */
    public String getUpdateSortingPlace()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_SORTING_PLACE, "")) ;
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
        return "$Id: CustomerHistory.java 2610 2009-01-08 08:13:44Z ota $" ;
    }
}

// $Id: Customer.java 1661 2008-12-02 04:43:57Z rnakai $
// Handler v3.8
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
 * CUSTOMERのエンティティクラスです。
 *
 * @version $Revision: 1661 $, $Date: 2008-12-02 13:43:57 +0900 (火, 02 12 2008) $
 * @author  ss
 * @author  Last commit: $Author: rnakai $
 */

public class Customer
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMCUSTOMER" ;

    /*
     * テーブル名: DMCUSTOMER
     * CONSIGNOR_CODE :                CONSIGNOR_CODE      varchar2(16)
     * CUSTOMER_CODE :                 CUSTOMER_CODE       varchar2(16)
     * CUSTOMER_NAME :                 CUSTOMER_NAME       varchar2(40)
     * CARRIER_CODE :                  CARRIER_CODE        varchar2(16)
     * CARRIER_NAME :                  CARRIER_NAME        varchar2(40)
     * ROUTE :                         ROUTE               varchar2(4)
     * POSTAL_CODE :                   POSTAL_CODE         varchar2(10)
     * PREFECTURE :                    PREFECTURE          varchar2(20)
     * ADDRESS1 :                      ADDRESS1            varchar2(100)
     * ADDRESS2 :                      ADDRESS2            varchar2(100)
     * TELEPHONE :                     TELEPHONE           varchar2(13)
     * CONTACT1 :                      CONTACT1            varchar2(100)
     * CONTACT2 :                      CONTACT2            varchar2(100)
     * SORTING_PLACE :                 SORTING_PLACE       varchar2(16)
     * LAST_USED_DATE :                LAST_USED_DATE      date
     * REGIST_DATE :                   REGIST_DATE         timestamp
     * REGIST_PNAME :                  REGIST_PNAME        varchar2(48)
     * LAST_UPDATE_DATE :              LAST_UPDATE_DATE    timestamp
     * LAST_UPDATE_PNAME :             LAST_UPDATE_PNAME   varchar2(48)
     */
    /** カラム定義 荷主コード(<code>CONSIGNOR_CODE</code>) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** カラム定義 出荷先コード(<code>CUSTOMER_CODE</code>) */
    public static final FieldName CUSTOMER_CODE = new FieldName(STORE_NAME, "CUSTOMER_CODE") ;

    /** カラム定義 出荷先名(<code>CUSTOMER_NAME</code>) */
    public static final FieldName CUSTOMER_NAME = new FieldName(STORE_NAME, "CUSTOMER_NAME") ;

    /** カラム定義 運送業者コード(<code>CARRIER_CODE</code>) */
    public static final FieldName CARRIER_CODE = new FieldName(STORE_NAME, "CARRIER_CODE") ;

    /** カラム定義 運送業者名称(<code>CARRIER_NAME</code>) */
    public static final FieldName CARRIER_NAME = new FieldName(STORE_NAME, "CARRIER_NAME") ;

    /** カラム定義 ルート(<code>ROUTE</code>) */
    public static final FieldName ROUTE = new FieldName(STORE_NAME, "ROUTE") ;

    /** カラム定義 郵便番号(<code>POSTAL_CODE</code>) */
    public static final FieldName POSTAL_CODE = new FieldName(STORE_NAME, "POSTAL_CODE") ;

    /** カラム定義 都道府県名(<code>PREFECTURE</code>) */
    public static final FieldName PREFECTURE = new FieldName(STORE_NAME, "PREFECTURE") ;

    /** カラム定義 住所1(<code>ADDRESS1</code>) */
    public static final FieldName ADDRESS1 = new FieldName(STORE_NAME, "ADDRESS1") ;

    /** カラム定義 住所2(<code>ADDRESS2</code>) */
    public static final FieldName ADDRESS2 = new FieldName(STORE_NAME, "ADDRESS2") ;

    /** カラム定義 TEL(<code>TELEPHONE</code>) */
    public static final FieldName TELEPHONE = new FieldName(STORE_NAME, "TELEPHONE") ;

    /** カラム定義 連絡先1(<code>CONTACT1</code>) */
    public static final FieldName CONTACT1 = new FieldName(STORE_NAME, "CONTACT1") ;

    /** カラム定義 連絡先2(<code>CONTACT2</code>) */
    public static final FieldName CONTACT2 = new FieldName(STORE_NAME, "CONTACT2") ;

    /** カラム定義 仕分場所(<code>SORTING_PLACE</code>) */
    public static final FieldName SORTING_PLACE = new FieldName(STORE_NAME, "SORTING_PLACE") ;

    /** カラム定義 最終使用日(<code>LAST_USED_DATE</code>) */
    public static final FieldName LAST_USED_DATE = new FieldName(STORE_NAME, "LAST_USED_DATE") ;

    /** カラム定義 登録日時(<code>REGIST_DATE</code>) */
    public static final FieldName REGIST_DATE = new FieldName(STORE_NAME, "REGIST_DATE") ;

    /** カラム定義 登録処理名(<code>REGIST_PNAME</code>) */
    public static final FieldName REGIST_PNAME = new FieldName(STORE_NAME, "REGIST_PNAME") ;

    /** カラム定義 最終更新日時(<code>LAST_UPDATE_DATE</code>) */
    public static final FieldName LAST_UPDATE_DATE = new FieldName(STORE_NAME, "LAST_UPDATE_DATE") ;

    /** カラム定義 最終更新処理名(<code>LAST_UPDATE_PNAME</code>) */
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
    public Customer()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------
    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)に値をセットします。
     * @param value セットする値CONSIGNOR_CODE
     */
    public void setConsignorCode(String value)
    {
        setValue(CONSIGNOR_CODE, value);
    }

    /**
     * 荷主コード(<code>CONSIGNOR_CODE</code>)を取得します。
     * @return CONSIGNOR_CODE
     */
    public String getConsignorCode()
    {
        return String.valueOf(getValue(CONSIGNOR_CODE, "")) ;
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)に値をセットします。
     * @param value セットする値CUSTOMER_CODE
     */
    public void setCustomerCode(String value)
    {
        setValue(CUSTOMER_CODE, value);
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>)を取得します。
     * @return CUSTOMER_CODE
     */
    public String getCustomerCode()
    {
        return String.valueOf(getValue(CUSTOMER_CODE, "")) ;
    }

    /**
     * 出荷先名(<code>CUSTOMER_NAME</code>)に値をセットします。
     * @param value セットする値CUSTOMER_NAME
     */
    public void setCustomerName(String value)
    {
        setValue(CUSTOMER_NAME, value);
    }

    /**
     * 出荷先名(<code>CUSTOMER_NAME</code>)を取得します。
     * @return CUSTOMER_NAME
     */
    public String getCustomerName()
    {
        return String.valueOf(getValue(CUSTOMER_NAME, "")) ;
    }

    /**
     * 運送業者コード(<code>CARRIER_CODE</code>)に値をセットします。
     * @param value セットする値CARRIER_CODE
     */
    public void setCarrierCode(String value)
    {
        setValue(CARRIER_CODE, value);
    }

    /**
     * 運送業者コード(<code>CARRIER_CODE</code>)を取得します。
     * @return CARRIER_CODE
     */
    public String getCarrierCode()
    {
        return String.valueOf(getValue(CARRIER_CODE, "")) ;
    }

    /**
     * 運送業者名称(<code>CARRIER_NAME</code>)に値をセットします。
     * @param value セットする値CARRIER_NAME
     */
    public void setCarrierName(String value)
    {
        setValue(CARRIER_NAME, value);
    }

    /**
     * 運送業者名称(<code>CARRIER_NAME</code>)を取得します。
     * @return CARRIER_NAME
     */
    public String getCarrierName()
    {
        return String.valueOf(getValue(CARRIER_NAME, "")) ;
    }

    /**
     * ルート(<code>ROUTE</code>)に値をセットします。
     * @param value セットする値ROUTE
     */
    public void setRoute(String value)
    {
        setValue(ROUTE, value);
    }

    /**
     * ルート(<code>ROUTE</code>)を取得します。
     * @return ROUTE
     */
    public String getRoute()
    {
        return String.valueOf(getValue(ROUTE, "")) ;
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)に値をセットします。
     * @param value セットする値POSTAL_CODE
     */
    public void setPostalCode(String value)
    {
        setValue(POSTAL_CODE, value);
    }

    /**
     * 郵便番号(<code>POSTAL_CODE</code>)を取得します。
     * @return POSTAL_CODE
     */
    public String getPostalCode()
    {
        return String.valueOf(getValue(POSTAL_CODE, "")) ;
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)に値をセットします。
     * @param value セットする値PREFECTURE
     */
    public void setPrefecture(String value)
    {
        setValue(PREFECTURE, value);
    }

    /**
     * 都道府県名(<code>PREFECTURE</code>)を取得します。
     * @return PREFECTURE
     */
    public String getPrefecture()
    {
        return String.valueOf(getValue(PREFECTURE, "")) ;
    }

    /**
     * 住所1(<code>ADDRESS1</code>)に値をセットします。
     * @param value セットする値ADDRESS1
     */
    public void setAddress1(String value)
    {
        setValue(ADDRESS1, value);
    }

    /**
     * 住所1(<code>ADDRESS1</code>)を取得します。
     * @return ADDRESS1
     */
    public String getAddress1()
    {
        return String.valueOf(getValue(ADDRESS1, "")) ;
    }

    /**
     * 住所2(<code>ADDRESS2</code>)に値をセットします。
     * @param value セットする値ADDRESS2
     */
    public void setAddress2(String value)
    {
        setValue(ADDRESS2, value);
    }

    /**
     * 住所2(<code>ADDRESS2</code>)を取得します。
     * @return ADDRESS2
     */
    public String getAddress2()
    {
        return String.valueOf(getValue(ADDRESS2, "")) ;
    }

    /**
     * TEL(<code>TELEPHONE</code>)に値をセットします。
     * @param value セットする値TELEPHONE
     */
    public void setTelephone(String value)
    {
        setValue(TELEPHONE, value);
    }

    /**
     * TEL(<code>TELEPHONE</code>)を取得します。
     * @return TELEPHONE
     */
    public String getTelephone()
    {
        return String.valueOf(getValue(TELEPHONE, "")) ;
    }

    /**
     * 連絡先1(<code>CONTACT1</code>)に値をセットします。
     * @param value セットする値CONTACT1
     */
    public void setContact1(String value)
    {
        setValue(CONTACT1, value);
    }

    /**
     * 連絡先1(<code>CONTACT1</code>)を取得します。
     * @return CONTACT1
     */
    public String getContact1()
    {
        return String.valueOf(getValue(CONTACT1, "")) ;
    }

    /**
     * 連絡先2(<code>CONTACT2</code>)に値をセットします。
     * @param value セットする値CONTACT2
     */
    public void setContact2(String value)
    {
        setValue(CONTACT2, value);
    }

    /**
     * 連絡先2(<code>CONTACT2</code>)を取得します。
     * @return CONTACT2
     */
    public String getContact2()
    {
        return String.valueOf(getValue(CONTACT2, "")) ;
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)に値をセットします。
     * @param value セットする値SORTING_PLACE
     */
    public void setSortingPlace(String value)
    {
        setValue(SORTING_PLACE, value);
    }

    /**
     * 仕分場所(<code>SORTING_PLACE</code>)を取得します。
     * @return SORTING_PLACE
     */
    public String getSortingPlace()
    {
        return String.valueOf(getValue(SORTING_PLACE, "")) ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)に値をセットします。
     * @param value セットする値LAST_USED_DATE
     */
    public void setLastUsedDate(Date value)
    {
        setValue(LAST_USED_DATE, value);
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>)を取得します。
     * @return LAST_USED_DATE
     */
    public Date getLastUsedDate()
    {
        return (Date)getValue(LAST_USED_DATE, null) ;
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)に値をセットします。
     * @param value セットする値REGIST_DATE
     */
    public void setRegistDate(Date value)
    {
        setValue(REGIST_DATE, value);
    }

    /**
     * 登録日時(<code>REGIST_DATE</code>)を取得します。
     * @return REGIST_DATE
     */
    public Date getRegistDate()
    {
        return (Date)getValue(REGIST_DATE, null) ;
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)に値をセットします。
     * @param value セットする値REGIST_PNAME
     */
    public void setRegistPname(String value)
    {
        setValue(REGIST_PNAME, value);
    }

    /**
     * 登録処理名(<code>REGIST_PNAME</code>)を取得します。
     * @return REGIST_PNAME
     */
    public String getRegistPname()
    {
        return String.valueOf(getValue(REGIST_PNAME, "")) ;
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)に値をセットします。
     * @param value セットする値LAST_UPDATE_DATE
     */
    public void setLastUpdateDate(Date value)
    {
        setValue(LAST_UPDATE_DATE, value);
    }

    /**
     * 最終更新日時(<code>LAST_UPDATE_DATE</code>)を取得します。
     * @return LAST_UPDATE_DATE
     */
    public Date getLastUpdateDate()
    {
        return (Date)getValue(LAST_UPDATE_DATE, null) ;
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)に値をセットします。
     * @param value セットする値LAST_UPDATE_PNAME
     */
    public void setLastUpdatePname(String value)
    {
        setValue(LAST_UPDATE_PNAME, value);
    }

    /**
     * 最終更新処理名(<code>LAST_UPDATE_PNAME</code>)を取得します。
     * @return LAST_UPDATE_PNAME
     */
    public String getLastUpdatePname()
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
        return "$Id: Customer.java 1661 2008-12-02 04:43:57Z rnakai $" ;
    }
}

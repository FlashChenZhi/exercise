// $Id: HostCustomer.java 2759 2009-01-19 02:12:07Z okayama $
// $LastChangedRevision: 2759 $
package jp.co.daifuku.wms.base.fileentity;

/*
 * Copyright(c) 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.FieldDefine;
import jp.co.daifuku.wms.handler.field.StoreDefineHandler;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;
import jp.co.daifuku.wms.handler.util.HandlerUtil;


/**
 * HOSTCUSTOMERのエンティティクラスです。
 *
 * @version $Revision: 2759 $, $Date: 2009-01-19 11:12:07 +0900 (月, 19 1 2009) $
 * @author  shimizu
 * @author  Last commit: $Author: okayama $
 */

public class HostCustomer
        extends AbstractEntity
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** Name of File */
    public static final String STORE_NAME = "HOSTCUSTOMER";

    /*
     * ファイル名: HOSTCUSTOMER
     * 取込区分 :                      LOAD_FLAG           N(1)
     * 出荷先コード :                  CUSTOMER_CODE       X(16)
     * 出荷先名称 :                    CUSTOMER_NAME       A(40)
     * ルート :                        ROUTE               X(4)
     * 郵便番号 :                      POSTAL_CODE         X(10)
     * 都道府県名 :                    PREFECTURE          A(20)
     * 住所1 :                         ADDRESS1            A(100)
     * 住所2 :                         ADDRESS2            A(100)
     * TEL :                           TELEPHONE           X(13)
     * 連絡先1 :                       CONTACT1            A(50)
     * 連絡先2 :                       CONTACT2            A(20)
     * 仕分場所 :                      SORTING_PLACE       X(16)
     */

    /** フィールド定義 (取込区分(<code>LOAD_FLAG</code>)) */
    public static final FieldName LOAD_FLAG = new FieldName(STORE_NAME, "LOAD_FLAG");

    /** フィールド定義 (出荷先コード(<code>CUSTOMER_CODE</code>)) */
    public static final FieldName CUSTOMER_CODE = new FieldName(STORE_NAME, "CUSTOMER_CODE");

    /** フィールド定義 (出荷先名称(<code>CUSTOMER_NAME</code>)) */
    public static final FieldName CUSTOMER_NAME = new FieldName(STORE_NAME, "CUSTOMER_NAME");

    /** フィールド定義 (ルート(<code>ROUTE</code>)) */
    public static final FieldName ROUTE = new FieldName(STORE_NAME, "ROUTE");

    /** フィールド定義 (郵便番号(<code>POSTAL_CODE</code>)) */
    public static final FieldName POSTAL_CODE = new FieldName(STORE_NAME, "POSTAL_CODE");

    /** フィールド定義 (都道府県名(<code>PREFECTURE</code>)) */
    public static final FieldName PREFECTURE = new FieldName(STORE_NAME, "PREFECTURE");

    /** フィールド定義 (住所1(<code>ADDRESS1</code>)) */
    public static final FieldName ADDRESS1 = new FieldName(STORE_NAME, "ADDRESS1");

    /** フィールド定義 (住所2(<code>ADDRESS2</code>)) */
    public static final FieldName ADDRESS2 = new FieldName(STORE_NAME, "ADDRESS2");

    /** フィールド定義 (TEL(<code>TELEPHONE</code>)) */
    public static final FieldName TELEPHONE = new FieldName(STORE_NAME, "TELEPHONE");

    /** フィールド定義 (連絡先1(<code>CONTACT1</code>)) */
    public static final FieldName CONTACT1 = new FieldName(STORE_NAME, "CONTACT1");

    /** フィールド定義 (連絡先2(<code>CONTACT2</code>)) */
    public static final FieldName CONTACT2 = new FieldName(STORE_NAME, "CONTACT2");

    /** フィールド定義 (仕分場所(<code>SORTING_PLACE</code>)) */
    public static final FieldName SORTING_PLACE = new FieldName(STORE_NAME, "SORTING_PLACE");


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar;
    /** Store meta data for this entity */
    public static final StoreMetaData $storeMetaData = StoreDefineHandler.createStoreMetaData(
            STORE_NAME,
            new File(HandlerSysDefines.DEFINE_DIR, "filestores.xml"),    //    %%StoreMetadata
            new File(HandlerSysDefines.DEFINE_DIR, "filefields.xml")    //    %%FieldMetadata
            );

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     *  フィールド名リストを準備してインスタンスを生成します。
     */
    public HostCustomer()
    {
        super();
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 取込区分(<code>LOAD_FLAG</code>) に値をセットします。
     * @param value セットする値LOAD_FLAG
     */
    public void setLoadFlag(String value)  // @@GEN_V3@@
    {
        setValue(LOAD_FLAG, value);
    }

    /**
     * 取込区分(<code>LOAD_FLAG</code>) から値を取得します。
     * @return    取込区分
     */
    public String getLoadFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOAD_FLAG, ""));
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
     * @return    出荷先コード
     */
    public String getCustomerCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CUSTOMER_CODE, ""));
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
     * @return    出荷先名称
     */
    public String getCustomerName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CUSTOMER_NAME, ""));
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
     * @return    ルート
     */
    public String getRoute()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ROUTE, ""));
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
     * @return    郵便番号
     */
    public String getPostalCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(POSTAL_CODE, ""));
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
     * @return    都道府県名
     */
    public String getPrefecture()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PREFECTURE, ""));
    }

    /**
     * 住所1(<code>ADDRESS1</code>) に値をセットします。
     * @param value セットする値ADDRESS1
     */
    public void setAddress1(String value)  // @@GEN_V3@@
    {
        setValue(ADDRESS1, value);
    }

    /**
     * 住所1(<code>ADDRESS1</code>) から値を取得します。
     * @return    住所1
     */
    public String getAddress1()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ADDRESS1, ""));
    }

    /**
     * 住所2(<code>ADDRESS2</code>) に値をセットします。
     * @param value セットする値ADDRESS2
     */
    public void setAddress2(String value)  // @@GEN_V3@@
    {
        setValue(ADDRESS2, value);
    }

    /**
     * 住所2(<code>ADDRESS2</code>) から値を取得します。
     * @return    住所2
     */
    public String getAddress2()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ADDRESS2, ""));
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
     * @return    TEL
     */
    public String getTelephone()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TELEPHONE, ""));
    }

    /**
     * 連絡先1(<code>CONTACT1</code>) に値をセットします。
     * @param value セットする値CONTACT1
     */
    public void setContact1(String value)  // @@GEN_V3@@
    {
        setValue(CONTACT1, value);
    }

    /**
     * 連絡先1(<code>CONTACT1</code>) から値を取得します。
     * @return    連絡先1
     */
    public String getContact1()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONTACT1, ""));
    }

    /**
     * 連絡先2(<code>CONTACT2</code>) に値をセットします。
     * @param value セットする値CONTACT2
     */
    public void setContact2(String value)  // @@GEN_V3@@
    {
        setValue(CONTACT2, value);
    }

    /**
     * 連絡先2(<code>CONTACT2</code>) から値を取得します。
     * @return    連絡先2
     */
    public String getContact2()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONTACT2, ""));
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
     * @return    仕分場所
     */
    public String getSortingPlace()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SORTING_PLACE, ""));
    }


    /**
     *  ストアメタデータを返します。
     *  @return このエンティティ用ストアメタデータ
     */
    public StoreMetaData getStoreMetaData()
    {
        return $storeMetaData;
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
     *  このクラスのリビジョンを返します。
     *  @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: HostCustomer.java 2759 2009-01-19 02:12:07Z okayama $";
    }
}

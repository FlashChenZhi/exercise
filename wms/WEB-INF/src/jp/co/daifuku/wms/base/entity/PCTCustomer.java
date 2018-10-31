// $Id: PCTCustomer.java 3213 2009-03-02 06:59:20Z arai $
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
 * PCTCUSTOMERのエンティティクラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class PCTCustomer
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMPCTCUSTOMER" ;

    /*
     * テーブル名: DMPCTCUSTOMER
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 荷主名称 :                      CONSIGNOR_NAME      varchar2(40)
     * 得意先コード :                  REGULAR_CUSTOMER_CODEvarchar2(16)
     * 得意先名称 :                    REGULAR_CUSTOMER_NAMEvarchar2(40)
     * 出荷先コード :                  CUSTOMER_CODE       varchar2(16)
     * 出荷先名称 :                    CUSTOMER_NAME       varchar2(40)
     * 出荷先分類 :                    CUSTOMER_CATEGORY   varchar2(6)
     * SeqNo. :                        SEQ_NO              varchar2(5)
     * 作業優先度 :                    JOB_PRIORITY        number
     * 最終使用日 :                    LAST_USED_DATE      date
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (荷主名称(<code>CONSIGNOR_NAME</code>)) */
    public static final FieldName CONSIGNOR_NAME = new FieldName(STORE_NAME, "CONSIGNOR_NAME") ;

    /** フィールド定義 (得意先コード(<code>REGULAR_CUSTOMER_CODE</code>)) */
    public static final FieldName REGULAR_CUSTOMER_CODE = new FieldName(STORE_NAME, "REGULAR_CUSTOMER_CODE") ;

    /** フィールド定義 (得意先名称(<code>REGULAR_CUSTOMER_NAME</code>)) */
    public static final FieldName REGULAR_CUSTOMER_NAME = new FieldName(STORE_NAME, "REGULAR_CUSTOMER_NAME") ;

    /** フィールド定義 (出荷先コード(<code>CUSTOMER_CODE</code>)) */
    public static final FieldName CUSTOMER_CODE = new FieldName(STORE_NAME, "CUSTOMER_CODE") ;

    /** フィールド定義 (出荷先名称(<code>CUSTOMER_NAME</code>)) */
    public static final FieldName CUSTOMER_NAME = new FieldName(STORE_NAME, "CUSTOMER_NAME") ;

    /** フィールド定義 (出荷先分類(<code>CUSTOMER_CATEGORY</code>)) */
    public static final FieldName CUSTOMER_CATEGORY = new FieldName(STORE_NAME, "CUSTOMER_CATEGORY") ;

    /** フィールド定義 (SeqNo.(<code>SEQ_NO</code>)) */
    public static final FieldName SEQ_NO = new FieldName(STORE_NAME, "SEQ_NO") ;

    /** フィールド定義 (作業優先度(<code>JOB_PRIORITY</code>)) */
    public static final FieldName JOB_PRIORITY = new FieldName(STORE_NAME, "JOB_PRIORITY") ;

    /** フィールド定義 (最終使用日(<code>LAST_USED_DATE</code>)) */
    public static final FieldName LAST_USED_DATE = new FieldName(STORE_NAME, "LAST_USED_DATE") ;

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
    public PCTCustomer()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

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
     * 荷主名称(<code>CONSIGNOR_NAME</code>) に値をセットします。
     * @param value セットする値CONSIGNOR_NAME
     */
    public void setConsignorName(String value)  // @@GEN_V3@@
    {
        setValue(CONSIGNOR_NAME, value);
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>) から値を取得します。
     * @return CONSIGNOR_NAME
     */
    public String getConsignorName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONSIGNOR_NAME, "")) ;
    }

    /**
     * 得意先コード(<code>REGULAR_CUSTOMER_CODE</code>) に値をセットします。
     * @param value セットする値REGULAR_CUSTOMER_CODE
     */
    public void setRegularCustomerCode(String value)  // @@GEN_V3@@
    {
        setValue(REGULAR_CUSTOMER_CODE, value);
    }

    /**
     * 得意先コード(<code>REGULAR_CUSTOMER_CODE</code>) から値を取得します。
     * @return REGULAR_CUSTOMER_CODE
     */
    public String getRegularCustomerCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REGULAR_CUSTOMER_CODE, "")) ;
    }

    /**
     * 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>) に値をセットします。
     * @param value セットする値REGULAR_CUSTOMER_NAME
     */
    public void setRegularCustomerName(String value)  // @@GEN_V3@@
    {
        setValue(REGULAR_CUSTOMER_NAME, value);
    }

    /**
     * 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>) から値を取得します。
     * @return REGULAR_CUSTOMER_NAME
     */
    public String getRegularCustomerName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REGULAR_CUSTOMER_NAME, "")) ;
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
     * 出荷先分類(<code>CUSTOMER_CATEGORY</code>) に値をセットします。
     * @param value セットする値CUSTOMER_CATEGORY
     */
    public void setCustomerCategory(String value)  // @@GEN_V3@@
    {
        setValue(CUSTOMER_CATEGORY, value);
    }

    /**
     * 出荷先分類(<code>CUSTOMER_CATEGORY</code>) から値を取得します。
     * @return CUSTOMER_CATEGORY
     */
    public String getCustomerCategory()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CUSTOMER_CATEGORY, "")) ;
    }

    /**
     * SeqNo.(<code>SEQ_NO</code>) に値をセットします。
     * @param value セットする値SEQ_NO
     */
    public void setSeqNo(String value)  // @@GEN_V3@@
    {
        setValue(SEQ_NO, value);
    }

    /**
     * SeqNo.(<code>SEQ_NO</code>) から値を取得します。
     * @return SEQ_NO
     */
    public String getSeqNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SEQ_NO, "")) ;
    }

    /**
     * 作業優先度(<code>JOB_PRIORITY</code>) に値をセットします。
     * @param value セットする値JOB_PRIORITY
     */
    public void setJobPriority(int value)  // @@GEN_V3@@
    {
        setValue(JOB_PRIORITY, HandlerUtil.toObject(value));
    }

    /**
     * 作業優先度(<code>JOB_PRIORITY</code>) から値を取得します。
     * @return JOB_PRIORITY
     */
    public int getJobPriority()  // @@GEN_V3@@
    {
        return getBigDecimal(JOB_PRIORITY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>) に値をセットします。
     * @param value セットする値LAST_USED_DATE
     */
    public void setLastUsedDate(Date value)  // @@GEN_V3@@
    {
        setValue(LAST_USED_DATE, value);
    }

    /**
     * 最終使用日(<code>LAST_USED_DATE</code>) から値を取得します。
     * @return LAST_USED_DATE
     */
    public Date getLastUsedDate()  // @@GEN_V3@@
    {
        return (Date)getValue(LAST_USED_DATE, null) ;
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
        return "$Id: PCTCustomer.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}

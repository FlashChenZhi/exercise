// $Id: Reason.java,v 1.1.1.1 2009/02/10 08:55:32 arai Exp $
// $LastChangedRevision: $
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
 * REASONのエンティティクラスです。
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2009/02/10 08:55:32 $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class Reason
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMREASON" ;

    /*
     * テーブル名: DMREASON
     * 理由区分 :                      REASON_TYPE         number
     * 理由区分に対する名称 :          REASON_NAME         varchar2(40)
     * 最終使用日 :                    LAST_USED_DATE      date
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */

    /** フィールド定義 (理由区分(<code>REASON_TYPE</code>)) */
    public static final FieldName REASON_TYPE = new FieldName(STORE_NAME, "REASON_TYPE") ;

    /** フィールド定義 (理由区分に対する名称(<code>REASON_NAME</code>)) */
    public static final FieldName REASON_NAME = new FieldName(STORE_NAME, "REASON_NAME") ;

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
    public Reason()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 理由区分(<code>REASON_TYPE</code>) に値をセットします。
     * @param value セットする値REASON_TYPE
     */
    public void setReasonType(int value)  // @@GEN_V3@@
    {
        setValue(REASON_TYPE, HandlerUtil.toObject(value));
    }

    /**
     * 理由区分(<code>REASON_TYPE</code>) から値を取得します。
     * @return REASON_TYPE
     */
    public int getReasonType()  // @@GEN_V3@@
    {
        return getBigDecimal(REASON_TYPE, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 理由区分に対する名称(<code>REASON_NAME</code>) に値をセットします。
     * @param value セットする値REASON_NAME
     */
    public void setReasonName(String value)  // @@GEN_V3@@
    {
        setValue(REASON_NAME, value);
    }

    /**
     * 理由区分に対する名称(<code>REASON_NAME</code>) から値を取得します。
     * @return REASON_NAME
     */
    public String getReasonName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REASON_NAME, "")) ;
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
        return "$Id: Reason.java,v 1.1.1.1 2009/02/10 08:55:32 arai Exp $" ;
    }
}

// $Id: EWNToHost.java 7201 2010-02-24 02:36:39Z kishimoto $
// $LastChangedRevision: 7201 $
package jp.co.daifuku.wms.base.entity;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.util.Date;

import jp.co.daifuku.wms.handler.db.AbstractDBEntity;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreDefineHandler;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * EWNTOHOSTのエンティティクラスです。
 *
 * @version $Revision: 7201 $, $Date: 2010-02-24 11:36:39 +0900 (水, 24 2 2010) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */

public class EWNToHost
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNEWNTOHOST";

    /*
     * テーブル名: DNEWNTOHOST
     * 通信日時 :                      MESSAGE_DATE        timestamp
     * シーケンスNo. :                 SEQUENCE_NO         number
     * データ区分 :                    MESSAGE_ID          varchar2(25)
     * 通信データ :                    DATA                varchar2(500)
     */
    /** フィールド定義 (通信日時(<code>MESSAGE_DATE</code>)) */
    public static final FieldName MESSAGE_DATE = new FieldName(STORE_NAME, "MESSAGE_DATE") ;

    /** フィールド定義 (シーケンスNo.(<code>SEQUENCE_NO</code>)) */
    public static final FieldName SEQUENCE_NO = new FieldName(STORE_NAME, "SEQUENCE_NO") ;

    /** フィールド定義 (データ区分(<code>MESSAGE_ID</code>)) */
    public static final FieldName MESSAGE_ID = new FieldName(STORE_NAME, "MESSAGE_ID") ;

    /** フィールド定義 (通信データ(<code>DATA</code>)) */
    public static final FieldName DATA = new FieldName(STORE_NAME, "DATA") ;


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;
    /** Store meta data for this entity */
    public static final StoreMetaData $storeMetaData = StoreDefineHandler.createStoreMetaData(STORE_NAME);

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
    public EWNToHost()
    {
        super();
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 通信日時(<code>MESSAGE_DATE</code>) に値をセットします。
     * @param value セットする値MESSAGE_DATE
     */
    public void setMessageDate(Date value)  // @@GEN_V3@@
    {
        setValue(MESSAGE_DATE, value);
    }

    /**
     * 通信日時(<code>MESSAGE_DATE</code>) から値を取得します。
     * @return MESSAGE_DATE
     */
    public Date getMessageDate()  // @@GEN_V3@@
    {
        return (Date)getValue(MESSAGE_DATE, null) ;
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>) に値をセットします。
     * @param value セットする値SEQUENCE_NO
     */
    public void setSequenceNo(int value)  // @@GEN_V3@@
    {
        setValue(SEQUENCE_NO, HandlerUtil.toObject(value));
    }

    /**
     * シーケンスNo.(<code>SEQUENCE_NO</code>) から値を取得します。
     * @return SEQUENCE_NO
     */
    public int getSequenceNo()  // @@GEN_V3@@
    {
        return getBigDecimal(SEQUENCE_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>) に値をセットします。
     * @param value セットする値MESSAGE_ID
     */
    public void setMessageId(String value)  // @@GEN_V3@@
    {
        setValue(MESSAGE_ID, value);
    }

    /**
     * データ区分(<code>MESSAGE_ID</code>) から値を取得します。
     * @return MESSAGE_ID
     */
    public String getMessageId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MESSAGE_ID, "")) ;
    }

    /**
     * 通信データ(<code>DATA</code>) に値をセットします。
     * @param value セットする値DATA
     */
    public void setData(String value)  // @@GEN_V3@@
    {
        setValue(DATA, value);
    }

    /**
     * 通信データ(<code>DATA</code>) から値を取得します。
     * @return DATA
     */
    public String getData()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(DATA, "")) ;
    }


    //DFK Look add start
    /**
     * Build a string out of all the data
     * @return <code>String</code> MESSAGE_DATE, SEQUENCE_NO, MESSAGE_ID, DATA
     */
    public String getEntireRecord()
    {
        return String.valueOf(getValue(MESSAGE_DATE, "")) + "," + String.valueOf(getValue(SEQUENCE_NO, "")) + ","
                + String.valueOf(getValue(MESSAGE_ID, "")) + "," + String.valueOf(getValue(DATA, ""));
    }

    //DFK Look add end

    /**
     * ストアメタデータを返します。
     * @return このエンティティ用ストアメタデータ
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
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: EWNToHost.java 7201 2010-02-24 02:36:39Z kishimoto $";
    }
}

// $Id: StationType.java 1544 2008-11-25 09:32:24Z dmori $
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
 * STATIONTYPEのエンティティクラスです。
 *
 * @version $Revision: 1544 $, $Date: 2008-11-25 18:32:24 +0900 (火, 25 11 2008) $
 * @author  ss
 * @author  Last commit: $Author: dmori $
 */

public class StationType
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMSTATIONTYPE" ;

    /*
     * テーブル名: DMSTATIONTYPE
     * ステーションNo :                STATION_NO          varchar2(16)
     * クラス名 :                      CLASS_NAME          varchar2(128)
     */

    /** フィールド定義 (ステーションNo(<code>STATION_NO</code>)) */
    public static final FieldName STATION_NO = new FieldName(STORE_NAME, "STATION_NO") ;

    /** フィールド定義 (クラス名(<code>CLASS_NAME</code>)) */
    public static final FieldName CLASS_NAME = new FieldName(STORE_NAME, "CLASS_NAME") ;


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
    public StationType()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * ステーションNo(<code>STATION_NO</code>) に値をセットします。
     * @param value セットする値STATION_NO
     */
    public void setStationNo(String value)  // @@GEN_V3@@
    {
        setValue(STATION_NO, value);
    }

    /**
     * ステーションNo(<code>STATION_NO</code>) から値を取得します。
     * @return STATION_NO
     */
    public String getStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATION_NO, "")) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>) に値をセットします。
     * @param value セットする値CLASS_NAME
     */
    public void setClassName(String value)  // @@GEN_V3@@
    {
        setValue(CLASS_NAME, value);
    }

    /**
     * クラス名(<code>CLASS_NAME</code>) から値を取得します。
     * @return CLASS_NAME
     */
    public String getClassName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CLASS_NAME, "")) ;
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
        return "$Id: StationType.java 1544 2008-11-25 09:32:24Z dmori $" ;
    }
}

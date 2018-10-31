// $Id: LoadSize.java 4122 2009-04-10 10:58:38Z ota $
// $LastChangedRevision: 4122 $
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
 * LOADSIZEのエンティティクラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class LoadSize
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMLOADSIZE" ;

    /*
     * テーブル名: DMLOADSIZE
     * 荷姿 :                          LOAD_SIZE           number
     * 荷姿名称 :                      LOAD_SIZE_NAME      varchar2(40)
     * 荷長 :                          LENGTH              number
     * 荷高 :                          HEIGHT              number
     */
    /** フィールド定義 (荷姿(<code>LOAD_SIZE</code>)) */
    public static final FieldName LOAD_SIZE = new FieldName(STORE_NAME, "LOAD_SIZE") ;

    /** フィールド定義 (荷姿名称(<code>LOAD_SIZE_NAME</code>)) */
    public static final FieldName LOAD_SIZE_NAME = new FieldName(STORE_NAME, "LOAD_SIZE_NAME") ;

    /** フィールド定義 (荷長(<code>LENGTH</code>)) */
    public static final FieldName LENGTH = new FieldName(STORE_NAME, "LENGTH") ;

    /** フィールド定義 (荷高(<code>HEIGHT</code>)) */
    public static final FieldName HEIGHT = new FieldName(STORE_NAME, "HEIGHT") ;


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
    public LoadSize()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 荷姿(<code>LOAD_SIZE</code>) に値をセットします。
     * @param value セットする値LOAD_SIZE
     */
    public void setLoadSize(int value)  // @@GEN_V3@@
    {
        setValue(LOAD_SIZE, HandlerUtil.toObject(value));
    }

    /**
     * 荷姿(<code>LOAD_SIZE</code>) から値を取得します。
     * @return LOAD_SIZE
     */
    public int getLoadSize()  // @@GEN_V3@@
    {
        return getBigDecimal(LOAD_SIZE, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>) に値をセットします。
     * @param value セットする値LOAD_SIZE_NAME
     */
    public void setLoadSizeName(String value)  // @@GEN_V3@@
    {
        setValue(LOAD_SIZE_NAME, value);
    }

    /**
     * 荷姿名称(<code>LOAD_SIZE_NAME</code>) から値を取得します。
     * @return LOAD_SIZE_NAME
     */
    public String getLoadSizeName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOAD_SIZE_NAME, "")) ;
    }

    /**
     * 荷長(<code>LENGTH</code>) に値をセットします。
     * @param value セットする値LENGTH
     */
    public void setLength(int value)  // @@GEN_V3@@
    {
        setValue(LENGTH, HandlerUtil.toObject(value));
    }

    /**
     * 荷長(<code>LENGTH</code>) から値を取得します。
     * @return LENGTH
     */
    public int getLength()  // @@GEN_V3@@
    {
        return getBigDecimal(LENGTH, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 荷高(<code>HEIGHT</code>) に値をセットします。
     * @param value セットする値HEIGHT
     */
    public void setHeight(int value)  // @@GEN_V3@@
    {
        setValue(HEIGHT, HandlerUtil.toObject(value));
    }

    /**
     * 荷高(<code>HEIGHT</code>) から値を取得します。
     * @return HEIGHT
     */
    public int getHeight()  // @@GEN_V3@@
    {
        return getBigDecimal(HEIGHT, BigDecimal.ZERO).intValue() ;
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
        return "$Id: LoadSize.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}

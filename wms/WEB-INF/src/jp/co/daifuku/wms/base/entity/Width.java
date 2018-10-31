// $Id: Width.java 4122 2009-04-10 10:58:38Z ota $
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
 * WIDTHのエンティティクラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class Width
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMWIDTH" ;

    /*
     * テーブル名: DMWIDTH
     * 荷幅ID :                        WIDTH_ID            varchar2(3)
     * 荷幅名称 :                      WIDTH_NAME          varchar2(40)
     * 倉庫ステーションNo. :           WH_STATION_NO       varchar2(16)
     * 荷幅 :                          WIDTH               number
     * 最大格納数 :                    MAX_STORAGE         number
     * 開始バンク :                    START_BANK_NO       number
     * 開始ベイ :                      START_BAY_NO        number
     * 開始レベル :                    START_LEVEL_NO      number
     * 終了バンク :                    END_BANK_NO         number
     * 終了ベイ :                      END_BAY_NO          number
     * 終了レベル :                    END_LEVEL_NO        number
     */
    /** フィールド定義 (荷幅ID(<code>WIDTH_ID</code>)) */
    public static final FieldName WIDTH_ID = new FieldName(STORE_NAME, "WIDTH_ID") ;

    /** フィールド定義 (荷幅名称(<code>WIDTH_NAME</code>)) */
    public static final FieldName WIDTH_NAME = new FieldName(STORE_NAME, "WIDTH_NAME") ;

    /** フィールド定義 (倉庫ステーションNo.(<code>WH_STATION_NO</code>)) */
    public static final FieldName WH_STATION_NO = new FieldName(STORE_NAME, "WH_STATION_NO") ;

    /** フィールド定義 (荷幅(<code>WIDTH</code>)) */
    public static final FieldName WIDTH = new FieldName(STORE_NAME, "WIDTH") ;

    /** フィールド定義 (最大格納数(<code>MAX_STORAGE</code>)) */
    public static final FieldName MAX_STORAGE = new FieldName(STORE_NAME, "MAX_STORAGE") ;

    /** フィールド定義 (開始バンク(<code>START_BANK_NO</code>)) */
    public static final FieldName START_BANK_NO = new FieldName(STORE_NAME, "START_BANK_NO") ;

    /** フィールド定義 (開始ベイ(<code>START_BAY_NO</code>)) */
    public static final FieldName START_BAY_NO = new FieldName(STORE_NAME, "START_BAY_NO") ;

    /** フィールド定義 (開始レベル(<code>START_LEVEL_NO</code>)) */
    public static final FieldName START_LEVEL_NO = new FieldName(STORE_NAME, "START_LEVEL_NO") ;

    /** フィールド定義 (終了バンク(<code>END_BANK_NO</code>)) */
    public static final FieldName END_BANK_NO = new FieldName(STORE_NAME, "END_BANK_NO") ;

    /** フィールド定義 (終了ベイ(<code>END_BAY_NO</code>)) */
    public static final FieldName END_BAY_NO = new FieldName(STORE_NAME, "END_BAY_NO") ;

    /** フィールド定義 (終了レベル(<code>END_LEVEL_NO</code>)) */
    public static final FieldName END_LEVEL_NO = new FieldName(STORE_NAME, "END_LEVEL_NO") ;


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
    public Width()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 荷幅ID(<code>WIDTH_ID</code>) に値をセットします。
     * @param value セットする値WIDTH_ID
     */
    public void setWidthId(String value)  // @@GEN_V3@@
    {
        setValue(WIDTH_ID, value);
    }

    /**
     * 荷幅ID(<code>WIDTH_ID</code>) から値を取得します。
     * @return WIDTH_ID
     */
    public String getWidthId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WIDTH_ID, "")) ;
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>) に値をセットします。
     * @param value セットする値WIDTH_NAME
     */
    public void setWidthName(String value)  // @@GEN_V3@@
    {
        setValue(WIDTH_NAME, value);
    }

    /**
     * 荷幅名称(<code>WIDTH_NAME</code>) から値を取得します。
     * @return WIDTH_NAME
     */
    public String getWidthName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WIDTH_NAME, "")) ;
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>) に値をセットします。
     * @param value セットする値WH_STATION_NO
     */
    public void setWhStationNo(String value)  // @@GEN_V3@@
    {
        setValue(WH_STATION_NO, value);
    }

    /**
     * 倉庫ステーションNo.(<code>WH_STATION_NO</code>) から値を取得します。
     * @return WH_STATION_NO
     */
    public String getWhStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WH_STATION_NO, "")) ;
    }

    /**
     * 荷幅(<code>WIDTH</code>) に値をセットします。
     * @param value セットする値WIDTH
     */
    public void setWidth(int value)  // @@GEN_V3@@
    {
        setValue(WIDTH, HandlerUtil.toObject(value));
    }

    /**
     * 荷幅(<code>WIDTH</code>) から値を取得します。
     * @return WIDTH
     */
    public int getWidth()  // @@GEN_V3@@
    {
        return getBigDecimal(WIDTH, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>) に値をセットします。
     * @param value セットする値MAX_STORAGE
     */
    public void setMaxStorage(int value)  // @@GEN_V3@@
    {
        setValue(MAX_STORAGE, HandlerUtil.toObject(value));
    }

    /**
     * 最大格納数(<code>MAX_STORAGE</code>) から値を取得します。
     * @return MAX_STORAGE
     */
    public int getMaxStorage()  // @@GEN_V3@@
    {
        return getBigDecimal(MAX_STORAGE, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>) に値をセットします。
     * @param value セットする値START_BANK_NO
     */
    public void setStartBankNo(int value)  // @@GEN_V3@@
    {
        setValue(START_BANK_NO, HandlerUtil.toObject(value));
    }

    /**
     * 開始バンク(<code>START_BANK_NO</code>) から値を取得します。
     * @return START_BANK_NO
     */
    public int getStartBankNo()  // @@GEN_V3@@
    {
        return getBigDecimal(START_BANK_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>) に値をセットします。
     * @param value セットする値START_BAY_NO
     */
    public void setStartBayNo(int value)  // @@GEN_V3@@
    {
        setValue(START_BAY_NO, HandlerUtil.toObject(value));
    }

    /**
     * 開始ベイ(<code>START_BAY_NO</code>) から値を取得します。
     * @return START_BAY_NO
     */
    public int getStartBayNo()  // @@GEN_V3@@
    {
        return getBigDecimal(START_BAY_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>) に値をセットします。
     * @param value セットする値START_LEVEL_NO
     */
    public void setStartLevelNo(int value)  // @@GEN_V3@@
    {
        setValue(START_LEVEL_NO, HandlerUtil.toObject(value));
    }

    /**
     * 開始レベル(<code>START_LEVEL_NO</code>) から値を取得します。
     * @return START_LEVEL_NO
     */
    public int getStartLevelNo()  // @@GEN_V3@@
    {
        return getBigDecimal(START_LEVEL_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>) に値をセットします。
     * @param value セットする値END_BANK_NO
     */
    public void setEndBankNo(int value)  // @@GEN_V3@@
    {
        setValue(END_BANK_NO, HandlerUtil.toObject(value));
    }

    /**
     * 終了バンク(<code>END_BANK_NO</code>) から値を取得します。
     * @return END_BANK_NO
     */
    public int getEndBankNo()  // @@GEN_V3@@
    {
        return getBigDecimal(END_BANK_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>) に値をセットします。
     * @param value セットする値END_BAY_NO
     */
    public void setEndBayNo(int value)  // @@GEN_V3@@
    {
        setValue(END_BAY_NO, HandlerUtil.toObject(value));
    }

    /**
     * 終了ベイ(<code>END_BAY_NO</code>) から値を取得します。
     * @return END_BAY_NO
     */
    public int getEndBayNo()  // @@GEN_V3@@
    {
        return getBigDecimal(END_BAY_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>) に値をセットします。
     * @param value セットする値END_LEVEL_NO
     */
    public void setEndLevelNo(int value)  // @@GEN_V3@@
    {
        setValue(END_LEVEL_NO, HandlerUtil.toObject(value));
    }

    /**
     * 終了レベル(<code>END_LEVEL_NO</code>) から値を取得します。
     * @return END_LEVEL_NO
     */
    public int getEndLevelNo()  // @@GEN_V3@@
    {
        return getBigDecimal(END_LEVEL_NO, BigDecimal.ZERO).intValue() ;
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
        return "$Id: Width.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}

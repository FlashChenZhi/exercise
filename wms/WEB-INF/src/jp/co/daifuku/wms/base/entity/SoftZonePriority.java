// $Id: SoftZonePriority.java 4122 2009-04-10 10:58:38Z ota $
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
 * SOFTZONEPRIORITYのエンティティクラスです。
 *
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class SoftZonePriority
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMSOFTZONEPRIORITY" ;

    /*
     * テーブル名: DMSOFTZONEPRIORITY
     * 倉庫ステーションNo. :           WH_STATION_NO       varchar2(16)
     * ソフトゾーンID :                SOFT_ZONE_ID        varchar2(3)
     * 優先ソフトゾーン :              PRIORITY_SOFT_ZONE  varchar2(3)
     * ゾーン優先順 :                  PRIORITY            number
     */
    /** フィールド定義 (倉庫ステーションNo.(<code>WH_STATION_NO</code>)) */
    public static final FieldName WH_STATION_NO = new FieldName(STORE_NAME, "WH_STATION_NO") ;

    /** フィールド定義 (ソフトゾーンID(<code>SOFT_ZONE_ID</code>)) */
    public static final FieldName SOFT_ZONE_ID = new FieldName(STORE_NAME, "SOFT_ZONE_ID") ;

    /** フィールド定義 (優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>)) */
    public static final FieldName PRIORITY_SOFT_ZONE = new FieldName(STORE_NAME, "PRIORITY_SOFT_ZONE") ;

    /** フィールド定義 (ゾーン優先順(<code>PRIORITY</code>)) */
    public static final FieldName PRIORITY = new FieldName(STORE_NAME, "PRIORITY") ;


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
    public SoftZonePriority()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

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
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>) に値をセットします。
     * @param value セットする値SOFT_ZONE_ID
     */
    public void setSoftZoneId(String value)  // @@GEN_V3@@
    {
        setValue(SOFT_ZONE_ID, value);
    }

    /**
     * ソフトゾーンID(<code>SOFT_ZONE_ID</code>) から値を取得します。
     * @return SOFT_ZONE_ID
     */
    public String getSoftZoneId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SOFT_ZONE_ID, "")) ;
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>) に値をセットします。
     * @param value セットする値PRIORITY_SOFT_ZONE
     */
    public void setPrioritySoftZone(String value)  // @@GEN_V3@@
    {
        setValue(PRIORITY_SOFT_ZONE, value);
    }

    /**
     * 優先ソフトゾーン(<code>PRIORITY_SOFT_ZONE</code>) から値を取得します。
     * @return PRIORITY_SOFT_ZONE
     */
    public String getPrioritySoftZone()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PRIORITY_SOFT_ZONE, "")) ;
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>) に値をセットします。
     * @param value セットする値PRIORITY
     */
    public void setPriority(int value)  // @@GEN_V3@@
    {
        setValue(PRIORITY, HandlerUtil.toObject(value));
    }

    /**
     * ゾーン優先順(<code>PRIORITY</code>) から値を取得します。
     * @return PRIORITY
     */
    public int getPriority()  // @@GEN_V3@@
    {
        return getBigDecimal(PRIORITY, BigDecimal.ZERO).intValue() ;
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
        return "$Id: SoftZonePriority.java 4122 2009-04-10 10:58:38Z ota $" ;
    }
}

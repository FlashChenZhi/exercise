// $Id: InventoryCheck.java 5127 2009-10-13 12:20:06Z ota $
// $LastChangedRevision: 5127 $
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
 * INVENTORYCHECKのエンティティクラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class InventoryCheck
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNINVENTORYCHECK" ;

    /*
     * テーブル名: DNINVENTORYCHECK
     * スケジュールNo. :               SCHEDULE_NO         varchar2(9)
     * 倉庫ステーションNo. :           WH_STATION_NO       varchar2(16)
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 開始棚No. :                     FROM_LOCATION       varchar2(11)
     * 終了棚No. :                     TO_LOCATION         varchar2(11)
     * 開始商品コード :                FROM_ITEM_CODE      varchar2(40)
     * 終了商品コード :                TO_ITEM_CODE        varchar2(40)
     * ロットNo. :                     LOT_NO              varchar2(60)
     * ステーションNo. :               STATION_NO          varchar2(16)
     * 状態 :                          STATUS_FLAG         varchar2(1)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (スケジュールNo.(<code>SCHEDULE_NO</code>)) */
    public static final FieldName SCHEDULE_NO = new FieldName(STORE_NAME, "SCHEDULE_NO") ;

    /** フィールド定義 (倉庫ステーションNo.(<code>WH_STATION_NO</code>)) */
    public static final FieldName WH_STATION_NO = new FieldName(STORE_NAME, "WH_STATION_NO") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (開始棚No.(<code>FROM_LOCATION</code>)) */
    public static final FieldName FROM_LOCATION = new FieldName(STORE_NAME, "FROM_LOCATION") ;

    /** フィールド定義 (終了棚No.(<code>TO_LOCATION</code>)) */
    public static final FieldName TO_LOCATION = new FieldName(STORE_NAME, "TO_LOCATION") ;

    /** フィールド定義 (開始商品コード(<code>FROM_ITEM_CODE</code>)) */
    public static final FieldName FROM_ITEM_CODE = new FieldName(STORE_NAME, "FROM_ITEM_CODE") ;

    /** フィールド定義 (終了商品コード(<code>TO_ITEM_CODE</code>)) */
    public static final FieldName TO_ITEM_CODE = new FieldName(STORE_NAME, "TO_ITEM_CODE") ;

    /** フィールド定義 (ロットNo.(<code>LOT_NO</code>)) */
    public static final FieldName LOT_NO = new FieldName(STORE_NAME, "LOT_NO") ;

    /** フィールド定義 (ステーションNo.(<code>STATION_NO</code>)) */
    public static final FieldName STATION_NO = new FieldName(STORE_NAME, "STATION_NO") ;

    /** フィールド定義 (状態(<code>STATUS_FLAG</code>)) */
    public static final FieldName STATUS_FLAG = new FieldName(STORE_NAME, "STATUS_FLAG") ;

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
    public InventoryCheck()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>) に値をセットします。
     * @param value セットする値SCHEDULE_NO
     */
    public void setScheduleNo(String value)  // @@GEN_V3@@
    {
        setValue(SCHEDULE_NO, value);
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>) から値を取得します。
     * @return SCHEDULE_NO
     */
    public String getScheduleNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SCHEDULE_NO, "")) ;
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
     * 開始棚No.(<code>FROM_LOCATION</code>) に値をセットします。
     * @param value セットする値FROM_LOCATION
     */
    public void setFromLocation(String value)  // @@GEN_V3@@
    {
        setValue(FROM_LOCATION, value);
    }

    /**
     * 開始棚No.(<code>FROM_LOCATION</code>) から値を取得します。
     * @return FROM_LOCATION
     */
    public String getFromLocation()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(FROM_LOCATION, "")) ;
    }

    /**
     * 終了棚No.(<code>TO_LOCATION</code>) に値をセットします。
     * @param value セットする値TO_LOCATION
     */
    public void setToLocation(String value)  // @@GEN_V3@@
    {
        setValue(TO_LOCATION, value);
    }

    /**
     * 終了棚No.(<code>TO_LOCATION</code>) から値を取得します。
     * @return TO_LOCATION
     */
    public String getToLocation()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TO_LOCATION, "")) ;
    }

    /**
     * 開始商品コード(<code>FROM_ITEM_CODE</code>) に値をセットします。
     * @param value セットする値FROM_ITEM_CODE
     */
    public void setFromItemCode(String value)  // @@GEN_V3@@
    {
        setValue(FROM_ITEM_CODE, value);
    }

    /**
     * 開始商品コード(<code>FROM_ITEM_CODE</code>) から値を取得します。
     * @return FROM_ITEM_CODE
     */
    public String getFromItemCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(FROM_ITEM_CODE, "")) ;
    }

    /**
     * 終了商品コード(<code>TO_ITEM_CODE</code>) に値をセットします。
     * @param value セットする値TO_ITEM_CODE
     */
    public void setToItemCode(String value)  // @@GEN_V3@@
    {
        setValue(TO_ITEM_CODE, value);
    }

    /**
     * 終了商品コード(<code>TO_ITEM_CODE</code>) から値を取得します。
     * @return TO_ITEM_CODE
     */
    public String getToItemCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TO_ITEM_CODE, "")) ;
    }

    /**
     * ロットNo.(<code>LOT_NO</code>) に値をセットします。
     * @param value セットする値LOT_NO
     */
    public void setLotNo(String value)  // @@GEN_V3@@
    {
        setValue(LOT_NO, value);
    }

    /**
     * ロットNo.(<code>LOT_NO</code>) から値を取得します。
     * @return LOT_NO
     */
    public String getLotNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOT_NO, "")) ;
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>) に値をセットします。
     * @param value セットする値STATION_NO
     */
    public void setStationNo(String value)  // @@GEN_V3@@
    {
        setValue(STATION_NO, value);
    }

    /**
     * ステーションNo.(<code>STATION_NO</code>) から値を取得します。
     * @return STATION_NO
     */
    public String getStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATION_NO, "")) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>) に値をセットします。
     * @param value セットする値STATUS_FLAG
     */
    public void setStatusFlag(String value)  // @@GEN_V3@@
    {
        setValue(STATUS_FLAG, value);
    }

    /**
     * 状態(<code>STATUS_FLAG</code>) から値を取得します。
     * @return STATUS_FLAG
     */
    public String getStatusFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATUS_FLAG, "")) ;
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
        return "$Id: InventoryCheck.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}

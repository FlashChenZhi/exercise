// $Id: WarenaviSystem.java 7996 2011-07-06 00:52:24Z kitamaki $
// $LastChangedRevision: 7996 $
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
 * WARENAVISYSTEMのエンティティクラスです。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  ss
 * @author  Last commit: $Author: kitamaki $
 */

public class WarenaviSystem
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMWARENAVISYSTEM" ;

    /*
     * テーブル名: DMWARENAVISYSTEM
     * システムNo. :                   SYSTEM_NO           varchar2(1)
     * 作業日 :                        WORK_DAY            varchar2(8)
     * 日次更新日時 :                  DAILY_UPDATE_DATE   timestamp
     * 実績保持日数 :                  RESULT_HOLD_PERIOD  number
     * 予定保持日数 :                  PLAN_HOLD_PERIOD    number
     * 入荷パッケージ :                RECEIVING_PACK      varchar2(1)
     * 入庫パッケージ :                STORAGE_PACK        varchar2(1)
     * 出庫パッケージ :                RETRIEVAL_PACK      varchar2(1)
     * 仕分パッケージ :                SORTING_PACK        varchar2(1)
     * 出荷パッケージ :                SHIPPING_PACK       varchar2(1)
     * 在庫パッケージ :                STOCK_PACK          varchar2(1)
     * クロスドックパッケージ :        CROSSDOCK_PACK      varchar2(1)
     * PCTマスタパッケージ :           PCT_MASTER_PACK     varchar2(1)
     * PCT入荷パッケージ :             PCT_RECEIVING_PACK  varchar2(1)
     * PCT出庫パッケージ :             PCT_RETRIEVAL_PACK  varchar2(1)
     * PCT棚卸パッケージ :             PCT_INVENTORY_PACK  varchar2(1)
     * 分析・シミュレーションパッケージ :ANALYSIS_PACK       varchar2(1)
     * AS/RSパッケージ :               ASRS_PACK           varchar2(1)
     * マスタ管理パッケージ :          MASTER_PACK         varchar2(1)
     * FA/DA区分 :                     FADA_FLAG           varchar2(1)
     * 操作ログ管理区分 :              CONTROL_LOG_FLAG    varchar2(1)
     * マスタ更新ログ管理区分 :        MASTER_UPDATE_LOG_FLAGvarchar2(1)
     * 在庫更新ログ管理区分 :          STOCK_UPDATE_LOG_FLAGvarchar2(1)
     * ホスト通信状態 :                COMM_STATUS_FLAG    varchar2(1)
     * 日次更新中フラグ :              DAILY_UPDATE        varchar2(1)
     * 予定データ取込中フラグ :        LOAD_DATA           varchar2(1)
     * 報告データ作成中フラグ :        REPORT_DATA         varchar2(1)
     * 出庫引当中フラグ :              RETRIEVAL_ALLOCATE  varchar2(1)
     * 搬送データクリア中フラグ :      ALLOCATION_CLEAR    varchar2(1)
     * 終了処理中フラグ :              END_PROCESSING_FLAG varchar2(1)
     */
    /** フィールド定義 (システムNo.(<code>SYSTEM_NO</code>)) */
    public static final FieldName SYSTEM_NO = new FieldName(STORE_NAME, "SYSTEM_NO") ;

    /** フィールド定義 (作業日(<code>WORK_DAY</code>)) */
    public static final FieldName WORK_DAY = new FieldName(STORE_NAME, "WORK_DAY") ;

    /** フィールド定義 (日次更新日時(<code>DAILY_UPDATE_DATE</code>)) */
    public static final FieldName DAILY_UPDATE_DATE = new FieldName(STORE_NAME, "DAILY_UPDATE_DATE") ;

    /** フィールド定義 (実績保持日数(<code>RESULT_HOLD_PERIOD</code>)) */
    public static final FieldName RESULT_HOLD_PERIOD = new FieldName(STORE_NAME, "RESULT_HOLD_PERIOD") ;

    /** フィールド定義 (予定保持日数(<code>PLAN_HOLD_PERIOD</code>)) */
    public static final FieldName PLAN_HOLD_PERIOD = new FieldName(STORE_NAME, "PLAN_HOLD_PERIOD") ;

    /** フィールド定義 (入荷パッケージ(<code>RECEIVING_PACK</code>)) */
    public static final FieldName RECEIVING_PACK = new FieldName(STORE_NAME, "RECEIVING_PACK") ;

    /** フィールド定義 (入庫パッケージ(<code>STORAGE_PACK</code>)) */
    public static final FieldName STORAGE_PACK = new FieldName(STORE_NAME, "STORAGE_PACK") ;

    /** フィールド定義 (出庫パッケージ(<code>RETRIEVAL_PACK</code>)) */
    public static final FieldName RETRIEVAL_PACK = new FieldName(STORE_NAME, "RETRIEVAL_PACK") ;

    /** フィールド定義 (仕分パッケージ(<code>SORTING_PACK</code>)) */
    public static final FieldName SORTING_PACK = new FieldName(STORE_NAME, "SORTING_PACK") ;

    /** フィールド定義 (出荷パッケージ(<code>SHIPPING_PACK</code>)) */
    public static final FieldName SHIPPING_PACK = new FieldName(STORE_NAME, "SHIPPING_PACK") ;

    /** フィールド定義 (在庫パッケージ(<code>STOCK_PACK</code>)) */
    public static final FieldName STOCK_PACK = new FieldName(STORE_NAME, "STOCK_PACK") ;

    /** フィールド定義 (クロスドックパッケージ(<code>CROSSDOCK_PACK</code>)) */
    public static final FieldName CROSSDOCK_PACK = new FieldName(STORE_NAME, "CROSSDOCK_PACK") ;

    /** フィールド定義 (PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>)) */
    public static final FieldName PCT_MASTER_PACK = new FieldName(STORE_NAME, "PCT_MASTER_PACK") ;

    /** フィールド定義 (PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>)) */
    public static final FieldName PCT_RECEIVING_PACK = new FieldName(STORE_NAME, "PCT_RECEIVING_PACK") ;

    /** フィールド定義 (PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>)) */
    public static final FieldName PCT_RETRIEVAL_PACK = new FieldName(STORE_NAME, "PCT_RETRIEVAL_PACK") ;

    /** フィールド定義 (PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>)) */
    public static final FieldName PCT_INVENTORY_PACK = new FieldName(STORE_NAME, "PCT_INVENTORY_PACK") ;

    /** フィールド定義 (分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>)) */
    public static final FieldName ANALYSIS_PACK = new FieldName(STORE_NAME, "ANALYSIS_PACK") ;

    /** フィールド定義 (AS/RSパッケージ(<code>ASRS_PACK</code>)) */
    public static final FieldName ASRS_PACK = new FieldName(STORE_NAME, "ASRS_PACK") ;

    /** フィールド定義 (マスタ管理パッケージ(<code>MASTER_PACK</code>)) */
    public static final FieldName MASTER_PACK = new FieldName(STORE_NAME, "MASTER_PACK") ;

    /** フィールド定義 (FA/DA区分(<code>FADA_FLAG</code>)) */
    public static final FieldName FADA_FLAG = new FieldName(STORE_NAME, "FADA_FLAG") ;

    /** フィールド定義 (操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>)) */
    public static final FieldName CONTROL_LOG_FLAG = new FieldName(STORE_NAME, "CONTROL_LOG_FLAG") ;

    /** フィールド定義 (マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>)) */
    public static final FieldName MASTER_UPDATE_LOG_FLAG = new FieldName(STORE_NAME, "MASTER_UPDATE_LOG_FLAG") ;

    /** フィールド定義 (在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>)) */
    public static final FieldName STOCK_UPDATE_LOG_FLAG = new FieldName(STORE_NAME, "STOCK_UPDATE_LOG_FLAG") ;

    /** フィールド定義 (ホスト通信状態(<code>COMM_STATUS_FLAG</code>)) */
    public static final FieldName COMM_STATUS_FLAG = new FieldName(STORE_NAME, "COMM_STATUS_FLAG") ;

    /** フィールド定義 (日次更新中フラグ(<code>DAILY_UPDATE</code>)) */
    public static final FieldName DAILY_UPDATE = new FieldName(STORE_NAME, "DAILY_UPDATE") ;

    /** フィールド定義 (予定データ取込中フラグ(<code>LOAD_DATA</code>)) */
    public static final FieldName LOAD_DATA = new FieldName(STORE_NAME, "LOAD_DATA") ;

    /** フィールド定義 (報告データ作成中フラグ(<code>REPORT_DATA</code>)) */
    public static final FieldName REPORT_DATA = new FieldName(STORE_NAME, "REPORT_DATA") ;

    /** フィールド定義 (出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>)) */
    public static final FieldName RETRIEVAL_ALLOCATE = new FieldName(STORE_NAME, "RETRIEVAL_ALLOCATE") ;

    /** フィールド定義 (搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>)) */
    public static final FieldName ALLOCATION_CLEAR = new FieldName(STORE_NAME, "ALLOCATION_CLEAR") ;

    /** フィールド定義 (終了処理中フラグ(<code>END_PROCESSING_FLAG</code>)) */
    public static final FieldName END_PROCESSING_FLAG = new FieldName(STORE_NAME, "END_PROCESSING_FLAG") ;


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
    public WarenaviSystem()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * システムNo.(<code>SYSTEM_NO</code>) に値をセットします。
     * @param value セットする値SYSTEM_NO
     */
    public void setSystemNo(String value)  // @@GEN_V3@@
    {
        setValue(SYSTEM_NO, value);
    }

    /**
     * システムNo.(<code>SYSTEM_NO</code>) から値を取得します。
     * @return SYSTEM_NO
     */
    public String getSystemNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SYSTEM_NO, "")) ;
    }

    /**
     * 作業日(<code>WORK_DAY</code>) に値をセットします。
     * @param value セットする値WORK_DAY
     */
    public void setWorkDay(String value)  // @@GEN_V3@@
    {
        setValue(WORK_DAY, value);
    }

    /**
     * 作業日(<code>WORK_DAY</code>) から値を取得します。
     * @return WORK_DAY
     */
    public String getWorkDay()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WORK_DAY, "")) ;
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>) に値をセットします。
     * @param value セットする値DAILY_UPDATE_DATE
     */
    public void setDailyUpdateDate(Date value)  // @@GEN_V3@@
    {
        setValue(DAILY_UPDATE_DATE, value);
    }

    /**
     * 日次更新日時(<code>DAILY_UPDATE_DATE</code>) から値を取得します。
     * @return DAILY_UPDATE_DATE
     */
    public Date getDailyUpdateDate()  // @@GEN_V3@@
    {
        return (Date)getValue(DAILY_UPDATE_DATE, null) ;
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>) に値をセットします。
     * @param value セットする値RESULT_HOLD_PERIOD
     */
    public void setResultHoldPeriod(int value)  // @@GEN_V3@@
    {
        setValue(RESULT_HOLD_PERIOD, HandlerUtil.toObject(value));
    }

    /**
     * 実績保持日数(<code>RESULT_HOLD_PERIOD</code>) から値を取得します。
     * @return RESULT_HOLD_PERIOD
     */
    public int getResultHoldPeriod()  // @@GEN_V3@@
    {
        return getBigDecimal(RESULT_HOLD_PERIOD, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>) に値をセットします。
     * @param value セットする値PLAN_HOLD_PERIOD
     */
    public void setPlanHoldPeriod(int value)  // @@GEN_V3@@
    {
        setValue(PLAN_HOLD_PERIOD, HandlerUtil.toObject(value));
    }

    /**
     * 予定保持日数(<code>PLAN_HOLD_PERIOD</code>) から値を取得します。
     * @return PLAN_HOLD_PERIOD
     */
    public int getPlanHoldPeriod()  // @@GEN_V3@@
    {
        return getBigDecimal(PLAN_HOLD_PERIOD, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>) に値をセットします。
     * @param value セットする値RECEIVING_PACK
     */
    public void setReceivingPack(String value)  // @@GEN_V3@@
    {
        setValue(RECEIVING_PACK, value);
    }

    /**
     * 入荷パッケージ(<code>RECEIVING_PACK</code>) から値を取得します。
     * @return RECEIVING_PACK
     */
    public String getReceivingPack()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RECEIVING_PACK, "")) ;
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>) に値をセットします。
     * @param value セットする値STORAGE_PACK
     */
    public void setStoragePack(String value)  // @@GEN_V3@@
    {
        setValue(STORAGE_PACK, value);
    }

    /**
     * 入庫パッケージ(<code>STORAGE_PACK</code>) から値を取得します。
     * @return STORAGE_PACK
     */
    public String getStoragePack()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STORAGE_PACK, "")) ;
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_PACK
     */
    public void setRetrievalPack(String value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_PACK, value);
    }

    /**
     * 出庫パッケージ(<code>RETRIEVAL_PACK</code>) から値を取得します。
     * @return RETRIEVAL_PACK
     */
    public String getRetrievalPack()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RETRIEVAL_PACK, "")) ;
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>) に値をセットします。
     * @param value セットする値SORTING_PACK
     */
    public void setSortingPack(String value)  // @@GEN_V3@@
    {
        setValue(SORTING_PACK, value);
    }

    /**
     * 仕分パッケージ(<code>SORTING_PACK</code>) から値を取得します。
     * @return SORTING_PACK
     */
    public String getSortingPack()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SORTING_PACK, "")) ;
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>) に値をセットします。
     * @param value セットする値SHIPPING_PACK
     */
    public void setShippingPack(String value)  // @@GEN_V3@@
    {
        setValue(SHIPPING_PACK, value);
    }

    /**
     * 出荷パッケージ(<code>SHIPPING_PACK</code>) から値を取得します。
     * @return SHIPPING_PACK
     */
    public String getShippingPack()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SHIPPING_PACK, "")) ;
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>) に値をセットします。
     * @param value セットする値STOCK_PACK
     */
    public void setStockPack(String value)  // @@GEN_V3@@
    {
        setValue(STOCK_PACK, value);
    }

    /**
     * 在庫パッケージ(<code>STOCK_PACK</code>) から値を取得します。
     * @return STOCK_PACK
     */
    public String getStockPack()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STOCK_PACK, "")) ;
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>) に値をセットします。
     * @param value セットする値CROSSDOCK_PACK
     */
    public void setCrossdockPack(String value)  // @@GEN_V3@@
    {
        setValue(CROSSDOCK_PACK, value);
    }

    /**
     * クロスドックパッケージ(<code>CROSSDOCK_PACK</code>) から値を取得します。
     * @return CROSSDOCK_PACK
     */
    public String getCrossdockPack()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CROSSDOCK_PACK, "")) ;
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>) に値をセットします。
     * @param value セットする値PCT_MASTER_PACK
     */
    public void setPctMasterPack(String value)  // @@GEN_V3@@
    {
        setValue(PCT_MASTER_PACK, value);
    }

    /**
     * PCTマスタパッケージ(<code>PCT_MASTER_PACK</code>) から値を取得します。
     * @return PCT_MASTER_PACK
     */
    public String getPctMasterPack()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PCT_MASTER_PACK, "")) ;
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>) に値をセットします。
     * @param value セットする値PCT_RECEIVING_PACK
     */
    public void setPctReceivingPack(String value)  // @@GEN_V3@@
    {
        setValue(PCT_RECEIVING_PACK, value);
    }

    /**
     * PCT入荷パッケージ(<code>PCT_RECEIVING_PACK</code>) から値を取得します。
     * @return PCT_RECEIVING_PACK
     */
    public String getPctReceivingPack()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PCT_RECEIVING_PACK, "")) ;
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>) に値をセットします。
     * @param value セットする値PCT_RETRIEVAL_PACK
     */
    public void setPctRetrievalPack(String value)  // @@GEN_V3@@
    {
        setValue(PCT_RETRIEVAL_PACK, value);
    }

    /**
     * PCT出庫パッケージ(<code>PCT_RETRIEVAL_PACK</code>) から値を取得します。
     * @return PCT_RETRIEVAL_PACK
     */
    public String getPctRetrievalPack()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PCT_RETRIEVAL_PACK, "")) ;
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>) に値をセットします。
     * @param value セットする値PCT_INVENTORY_PACK
     */
    public void setPctInventoryPack(String value)  // @@GEN_V3@@
    {
        setValue(PCT_INVENTORY_PACK, value);
    }

    /**
     * PCT棚卸パッケージ(<code>PCT_INVENTORY_PACK</code>) から値を取得します。
     * @return PCT_INVENTORY_PACK
     */
    public String getPctInventoryPack()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PCT_INVENTORY_PACK, "")) ;
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>) に値をセットします。
     * @param value セットする値ANALYSIS_PACK
     */
    public void setAnalysisPack(String value)  // @@GEN_V3@@
    {
        setValue(ANALYSIS_PACK, value);
    }

    /**
     * 分析・シミュレーションパッケージ(<code>ANALYSIS_PACK</code>) から値を取得します。
     * @return ANALYSIS_PACK
     */
    public String getAnalysisPack()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ANALYSIS_PACK, "")) ;
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>) に値をセットします。
     * @param value セットする値ASRS_PACK
     */
    public void setAsrsPack(String value)  // @@GEN_V3@@
    {
        setValue(ASRS_PACK, value);
    }

    /**
     * AS/RSパッケージ(<code>ASRS_PACK</code>) から値を取得します。
     * @return ASRS_PACK
     */
    public String getAsrsPack()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ASRS_PACK, "")) ;
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>) に値をセットします。
     * @param value セットする値MASTER_PACK
     */
    public void setMasterPack(String value)  // @@GEN_V3@@
    {
        setValue(MASTER_PACK, value);
    }

    /**
     * マスタ管理パッケージ(<code>MASTER_PACK</code>) から値を取得します。
     * @return MASTER_PACK
     */
    public String getMasterPack()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MASTER_PACK, "")) ;
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>) に値をセットします。
     * @param value セットする値FADA_FLAG
     */
    public void setFadaFlag(String value)  // @@GEN_V3@@
    {
        setValue(FADA_FLAG, value);
    }

    /**
     * FA/DA区分(<code>FADA_FLAG</code>) から値を取得します。
     * @return FADA_FLAG
     */
    public String getFadaFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(FADA_FLAG, "")) ;
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>) に値をセットします。
     * @param value セットする値CONTROL_LOG_FLAG
     */
    public void setControlLogFlag(String value)  // @@GEN_V3@@
    {
        setValue(CONTROL_LOG_FLAG, value);
    }

    /**
     * 操作ログ管理区分(<code>CONTROL_LOG_FLAG</code>) から値を取得します。
     * @return CONTROL_LOG_FLAG
     */
    public String getControlLogFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONTROL_LOG_FLAG, "")) ;
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>) に値をセットします。
     * @param value セットする値MASTER_UPDATE_LOG_FLAG
     */
    public void setMasterUpdateLogFlag(String value)  // @@GEN_V3@@
    {
        setValue(MASTER_UPDATE_LOG_FLAG, value);
    }

    /**
     * マスタ更新ログ管理区分(<code>MASTER_UPDATE_LOG_FLAG</code>) から値を取得します。
     * @return MASTER_UPDATE_LOG_FLAG
     */
    public String getMasterUpdateLogFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MASTER_UPDATE_LOG_FLAG, "")) ;
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>) に値をセットします。
     * @param value セットする値STOCK_UPDATE_LOG_FLAG
     */
    public void setStockUpdateLogFlag(String value)  // @@GEN_V3@@
    {
        setValue(STOCK_UPDATE_LOG_FLAG, value);
    }

    /**
     * 在庫更新ログ管理区分(<code>STOCK_UPDATE_LOG_FLAG</code>) から値を取得します。
     * @return STOCK_UPDATE_LOG_FLAG
     */
    public String getStockUpdateLogFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STOCK_UPDATE_LOG_FLAG, "")) ;
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>) に値をセットします。
     * @param value セットする値COMM_STATUS_FLAG
     */
    public void setCommStatusFlag(String value)  // @@GEN_V3@@
    {
        setValue(COMM_STATUS_FLAG, value);
    }

    /**
     * ホスト通信状態(<code>COMM_STATUS_FLAG</code>) から値を取得します。
     * @return COMM_STATUS_FLAG
     */
    public String getCommStatusFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(COMM_STATUS_FLAG, "")) ;
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>) に値をセットします。
     * @param value セットする値DAILY_UPDATE
     */
    public void setDailyUpdate(String value)  // @@GEN_V3@@
    {
        setValue(DAILY_UPDATE, value);
    }

    /**
     * 日次更新中フラグ(<code>DAILY_UPDATE</code>) から値を取得します。
     * @return DAILY_UPDATE
     */
    public String getDailyUpdate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(DAILY_UPDATE, "")) ;
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>) に値をセットします。
     * @param value セットする値LOAD_DATA
     */
    public void setLoadData(String value)  // @@GEN_V3@@
    {
        setValue(LOAD_DATA, value);
    }

    /**
     * 予定データ取込中フラグ(<code>LOAD_DATA</code>) から値を取得します。
     * @return LOAD_DATA
     */
    public String getLoadData()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOAD_DATA, "")) ;
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>) に値をセットします。
     * @param value セットする値REPORT_DATA
     */
    public void setReportData(String value)  // @@GEN_V3@@
    {
        setValue(REPORT_DATA, value);
    }

    /**
     * 報告データ作成中フラグ(<code>REPORT_DATA</code>) から値を取得します。
     * @return REPORT_DATA
     */
    public String getReportData()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REPORT_DATA, "")) ;
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_ALLOCATE
     */
    public void setRetrievalAllocate(String value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_ALLOCATE, value);
    }

    /**
     * 出庫引当中フラグ(<code>RETRIEVAL_ALLOCATE</code>) から値を取得します。
     * @return RETRIEVAL_ALLOCATE
     */
    public String getRetrievalAllocate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RETRIEVAL_ALLOCATE, "")) ;
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>) に値をセットします。
     * @param value セットする値ALLOCATION_CLEAR
     */
    public void setAllocationClear(String value)  // @@GEN_V3@@
    {
        setValue(ALLOCATION_CLEAR, value);
    }

    /**
     * 搬送データクリア中フラグ(<code>ALLOCATION_CLEAR</code>) から値を取得します。
     * @return ALLOCATION_CLEAR
     */
    public String getAllocationClear()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ALLOCATION_CLEAR, "")) ;
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>) に値をセットします。
     * @param value セットする値END_PROCESSING_FLAG
     */
    public void setEndProcessingFlag(String value)  // @@GEN_V3@@
    {
        setValue(END_PROCESSING_FLAG, value);
    }

    /**
     * 終了処理中フラグ(<code>END_PROCESSING_FLAG</code>) から値を取得します。
     * @return END_PROCESSING_FLAG
     */
    public String getEndProcessingFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(END_PROCESSING_FLAG, "")) ;
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
        return "$Id: WarenaviSystem.java 7996 2011-07-06 00:52:24Z kitamaki $" ;
    }
}

// $Id: MoveWorkInfo.java 5127 2009-10-13 12:20:06Z ota $
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
 * MOVEWORKINFOのエンティティクラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class MoveWorkInfo
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNMOVEWORKINFO" ;

    /*
     * テーブル名: DNMOVEWORKINFO
     * 作業No. :                       JOB_NO              varchar2(8)
     * 設定単位キー :                  SETTING_UNIT_KEY    varchar2(8)
     * 作業区分 :                      JOB_TYPE            varchar2(2)
     * 状態フラグ :                    STATUS_FLAG         varchar2(1)
     * ハードウェア区分 :              HARDWARE_TYPE       varchar2(1)
     * 入荷フラグ :                    RECEIVING_FLAG      varchar2(1)
     * 在庫ID :                        STOCK_ID            varchar2(8)
     * 入出庫作業情報接続キー :        WORK_CONN_KEY       varchar2(8)
     * 移動出庫エリア :                RETRIEVAL_AREA_NO   varchar2(4)
     * 移動出庫棚 :                    RETRIEVAL_LOCATION_NOvarchar2(11)
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 商品コード :                    ITEM_CODE           varchar2(40)
     * ロットNo. :                     LOT_NO              varchar2(60)
     * 入庫日 :                        STORAGE_DAY         varchar2(8)
     * 入庫日時 :                      STORAGE_DATE        date
     * 最終出庫日 :                    RETRIEVAL_DAY       varchar2(8)
     * 移動出庫ユーザID :              RETRIEVAL_USER_ID   varchar2(20)
     * 移動出庫端末No.、RFTNo. :       RETRIEVAL_TERMINAL_NOvarchar2(4)
     * 移動出庫作業日時 :              RETRIEVAL_WORK_DATE date
     * 移動出庫作業秒数 :              RETRIEVAL_WORK_SECONDnumber
     * 予定数 :                        PLAN_QTY            number
     * 出庫数 :                        RETRIEVAL_RESULT_QTYnumber
     * 入庫数 :                        STORAGE_RESULT_QTY  number
     * 移動入庫エリア :                STORAGE_AREA_NO     varchar2(4)
     * 移動入庫予定棚 :                STORAGE_LOCATION_NO varchar2(11)
     * 移動入庫ユーザID :              STORAGE_USER_ID     varchar2(20)
     * 移動入庫端末No.、RFTNo. :       STORAGE_TERMINAL_NO varchar2(4)
     * 移動入庫作業日時 :              STORAGE_WORK_DATE   date
     * 移動入庫作業日 :                STORAGE_WORK_DAY    varchar2(8)
     * 移動入庫作業秒数 :              STORAGE_WORK_SECOND number
     * 出庫開始日時 :                  RETRIEVAL_START_DATEdate
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (作業No.(<code>JOB_NO</code>)) */
    public static final FieldName JOB_NO = new FieldName(STORE_NAME, "JOB_NO") ;

    /** フィールド定義 (設定単位キー(<code>SETTING_UNIT_KEY</code>)) */
    public static final FieldName SETTING_UNIT_KEY = new FieldName(STORE_NAME, "SETTING_UNIT_KEY") ;

    /** フィールド定義 (作業区分(<code>JOB_TYPE</code>)) */
    public static final FieldName JOB_TYPE = new FieldName(STORE_NAME, "JOB_TYPE") ;

    /** フィールド定義 (状態フラグ(<code>STATUS_FLAG</code>)) */
    public static final FieldName STATUS_FLAG = new FieldName(STORE_NAME, "STATUS_FLAG") ;

    /** フィールド定義 (ハードウェア区分(<code>HARDWARE_TYPE</code>)) */
    public static final FieldName HARDWARE_TYPE = new FieldName(STORE_NAME, "HARDWARE_TYPE") ;

    /** フィールド定義 (入荷フラグ(<code>RECEIVING_FLAG</code>)) */
    public static final FieldName RECEIVING_FLAG = new FieldName(STORE_NAME, "RECEIVING_FLAG") ;

    /** フィールド定義 (在庫ID(<code>STOCK_ID</code>)) */
    public static final FieldName STOCK_ID = new FieldName(STORE_NAME, "STOCK_ID") ;

    /** フィールド定義 (入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>)) */
    public static final FieldName WORK_CONN_KEY = new FieldName(STORE_NAME, "WORK_CONN_KEY") ;

    /** フィールド定義 (移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>)) */
    public static final FieldName RETRIEVAL_AREA_NO = new FieldName(STORE_NAME, "RETRIEVAL_AREA_NO") ;

    /** フィールド定義 (移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>)) */
    public static final FieldName RETRIEVAL_LOCATION_NO = new FieldName(STORE_NAME, "RETRIEVAL_LOCATION_NO") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE") ;

    /** フィールド定義 (ロットNo.(<code>LOT_NO</code>)) */
    public static final FieldName LOT_NO = new FieldName(STORE_NAME, "LOT_NO") ;

    /** フィールド定義 (入庫日(<code>STORAGE_DAY</code>)) */
    public static final FieldName STORAGE_DAY = new FieldName(STORE_NAME, "STORAGE_DAY") ;

    /** フィールド定義 (入庫日時(<code>STORAGE_DATE</code>)) */
    public static final FieldName STORAGE_DATE = new FieldName(STORE_NAME, "STORAGE_DATE") ;

    /** フィールド定義 (最終出庫日(<code>RETRIEVAL_DAY</code>)) */
    public static final FieldName RETRIEVAL_DAY = new FieldName(STORE_NAME, "RETRIEVAL_DAY") ;

    /** フィールド定義 (移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>)) */
    public static final FieldName RETRIEVAL_USER_ID = new FieldName(STORE_NAME, "RETRIEVAL_USER_ID") ;

    /** フィールド定義 (移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>)) */
    public static final FieldName RETRIEVAL_TERMINAL_NO = new FieldName(STORE_NAME, "RETRIEVAL_TERMINAL_NO") ;

    /** フィールド定義 (移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>)) */
    public static final FieldName RETRIEVAL_WORK_DATE = new FieldName(STORE_NAME, "RETRIEVAL_WORK_DATE") ;

    /** フィールド定義 (移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>)) */
    public static final FieldName RETRIEVAL_WORK_SECOND = new FieldName(STORE_NAME, "RETRIEVAL_WORK_SECOND") ;

    /** フィールド定義 (予定数(<code>PLAN_QTY</code>)) */
    public static final FieldName PLAN_QTY = new FieldName(STORE_NAME, "PLAN_QTY") ;

    /** フィールド定義 (出庫数(<code>RETRIEVAL_RESULT_QTY</code>)) */
    public static final FieldName RETRIEVAL_RESULT_QTY = new FieldName(STORE_NAME, "RETRIEVAL_RESULT_QTY") ;

    /** フィールド定義 (入庫数(<code>STORAGE_RESULT_QTY</code>)) */
    public static final FieldName STORAGE_RESULT_QTY = new FieldName(STORE_NAME, "STORAGE_RESULT_QTY") ;

    /** フィールド定義 (移動入庫エリア(<code>STORAGE_AREA_NO</code>)) */
    public static final FieldName STORAGE_AREA_NO = new FieldName(STORE_NAME, "STORAGE_AREA_NO") ;

    /** フィールド定義 (移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>)) */
    public static final FieldName STORAGE_LOCATION_NO = new FieldName(STORE_NAME, "STORAGE_LOCATION_NO") ;

    /** フィールド定義 (移動入庫ユーザID(<code>STORAGE_USER_ID</code>)) */
    public static final FieldName STORAGE_USER_ID = new FieldName(STORE_NAME, "STORAGE_USER_ID") ;

    /** フィールド定義 (移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>)) */
    public static final FieldName STORAGE_TERMINAL_NO = new FieldName(STORE_NAME, "STORAGE_TERMINAL_NO") ;

    /** フィールド定義 (移動入庫作業日時(<code>STORAGE_WORK_DATE</code>)) */
    public static final FieldName STORAGE_WORK_DATE = new FieldName(STORE_NAME, "STORAGE_WORK_DATE") ;

    /** フィールド定義 (移動入庫作業日(<code>STORAGE_WORK_DAY</code>)) */
    public static final FieldName STORAGE_WORK_DAY = new FieldName(STORE_NAME, "STORAGE_WORK_DAY") ;

    /** フィールド定義 (移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>)) */
    public static final FieldName STORAGE_WORK_SECOND = new FieldName(STORE_NAME, "STORAGE_WORK_SECOND") ;

    /** フィールド定義 (出庫開始日時(<code>RETRIEVAL_START_DATE</code>)) */
    public static final FieldName RETRIEVAL_START_DATE = new FieldName(STORE_NAME, "RETRIEVAL_START_DATE") ;

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
    public MoveWorkInfo()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 作業No.(<code>JOB_NO</code>) に値をセットします。
     * @param value セットする値JOB_NO
     */
    public void setJobNo(String value)  // @@GEN_V3@@
    {
        setValue(JOB_NO, value);
    }

    /**
     * 作業No.(<code>JOB_NO</code>) から値を取得します。
     * @return JOB_NO
     */
    public String getJobNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(JOB_NO, "")) ;
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>) に値をセットします。
     * @param value セットする値SETTING_UNIT_KEY
     */
    public void setSettingUnitKey(String value)  // @@GEN_V3@@
    {
        setValue(SETTING_UNIT_KEY, value);
    }

    /**
     * 設定単位キー(<code>SETTING_UNIT_KEY</code>) から値を取得します。
     * @return SETTING_UNIT_KEY
     */
    public String getSettingUnitKey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SETTING_UNIT_KEY, "")) ;
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>) に値をセットします。
     * @param value セットする値JOB_TYPE
     */
    public void setJobType(String value)  // @@GEN_V3@@
    {
        setValue(JOB_TYPE, value);
    }

    /**
     * 作業区分(<code>JOB_TYPE</code>) から値を取得します。
     * @return JOB_TYPE
     */
    public String getJobType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(JOB_TYPE, "")) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>) に値をセットします。
     * @param value セットする値STATUS_FLAG
     */
    public void setStatusFlag(String value)  // @@GEN_V3@@
    {
        setValue(STATUS_FLAG, value);
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>) から値を取得します。
     * @return STATUS_FLAG
     */
    public String getStatusFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATUS_FLAG, "")) ;
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>) に値をセットします。
     * @param value セットする値HARDWARE_TYPE
     */
    public void setHardwareType(String value)  // @@GEN_V3@@
    {
        setValue(HARDWARE_TYPE, value);
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>) から値を取得します。
     * @return HARDWARE_TYPE
     */
    public String getHardwareType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(HARDWARE_TYPE, "")) ;
    }

    /**
     * 入荷フラグ(<code>RECEIVING_FLAG</code>) に値をセットします。
     * @param value セットする値RECEIVING_FLAG
     */
    public void setReceivingFlag(String value)  // @@GEN_V3@@
    {
        setValue(RECEIVING_FLAG, value);
    }

    /**
     * 入荷フラグ(<code>RECEIVING_FLAG</code>) から値を取得します。
     * @return RECEIVING_FLAG
     */
    public String getReceivingFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RECEIVING_FLAG, "")) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>) に値をセットします。
     * @param value セットする値STOCK_ID
     */
    public void setStockId(String value)  // @@GEN_V3@@
    {
        setValue(STOCK_ID, value);
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>) から値を取得します。
     * @return STOCK_ID
     */
    public String getStockId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STOCK_ID, "")) ;
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>) に値をセットします。
     * @param value セットする値WORK_CONN_KEY
     */
    public void setWorkConnKey(String value)  // @@GEN_V3@@
    {
        setValue(WORK_CONN_KEY, value);
    }

    /**
     * 入出庫作業情報接続キー(<code>WORK_CONN_KEY</code>) から値を取得します。
     * @return WORK_CONN_KEY
     */
    public String getWorkConnKey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WORK_CONN_KEY, "")) ;
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_AREA_NO
     */
    public void setRetrievalAreaNo(String value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_AREA_NO, value);
    }

    /**
     * 移動出庫エリア(<code>RETRIEVAL_AREA_NO</code>) から値を取得します。
     * @return RETRIEVAL_AREA_NO
     */
    public String getRetrievalAreaNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RETRIEVAL_AREA_NO, "")) ;
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_LOCATION_NO
     */
    public void setRetrievalLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_LOCATION_NO, value);
    }

    /**
     * 移動出庫棚(<code>RETRIEVAL_LOCATION_NO</code>) から値を取得します。
     * @return RETRIEVAL_LOCATION_NO
     */
    public String getRetrievalLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RETRIEVAL_LOCATION_NO, "")) ;
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
     * 商品コード(<code>ITEM_CODE</code>) に値をセットします。
     * @param value セットする値ITEM_CODE
     */
    public void setItemCode(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_CODE, value);
    }

    /**
     * 商品コード(<code>ITEM_CODE</code>) から値を取得します。
     * @return ITEM_CODE
     */
    public String getItemCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_CODE, "")) ;
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
     * 入庫日(<code>STORAGE_DAY</code>) に値をセットします。
     * @param value セットする値STORAGE_DAY
     */
    public void setStorageDay(String value)  // @@GEN_V3@@
    {
        setValue(STORAGE_DAY, value);
    }

    /**
     * 入庫日(<code>STORAGE_DAY</code>) から値を取得します。
     * @return STORAGE_DAY
     */
    public String getStorageDay()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STORAGE_DAY, "")) ;
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>) に値をセットします。
     * @param value セットする値STORAGE_DATE
     */
    public void setStorageDate(Date value)  // @@GEN_V3@@
    {
        setValue(STORAGE_DATE, value);
    }

    /**
     * 入庫日時(<code>STORAGE_DATE</code>) から値を取得します。
     * @return STORAGE_DATE
     */
    public Date getStorageDate()  // @@GEN_V3@@
    {
        return (Date)getValue(STORAGE_DATE, null) ;
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_DAY
     */
    public void setRetrievalDay(String value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_DAY, value);
    }

    /**
     * 最終出庫日(<code>RETRIEVAL_DAY</code>) から値を取得します。
     * @return RETRIEVAL_DAY
     */
    public String getRetrievalDay()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RETRIEVAL_DAY, "")) ;
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_USER_ID
     */
    public void setRetrievalUserId(String value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_USER_ID, value);
    }

    /**
     * 移動出庫ユーザID(<code>RETRIEVAL_USER_ID</code>) から値を取得します。
     * @return RETRIEVAL_USER_ID
     */
    public String getRetrievalUserId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RETRIEVAL_USER_ID, "")) ;
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_TERMINAL_NO
     */
    public void setRetrievalTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_TERMINAL_NO, value);
    }

    /**
     * 移動出庫端末No.、RFTNo.(<code>RETRIEVAL_TERMINAL_NO</code>) から値を取得します。
     * @return RETRIEVAL_TERMINAL_NO
     */
    public String getRetrievalTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RETRIEVAL_TERMINAL_NO, "")) ;
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_WORK_DATE
     */
    public void setRetrievalWorkDate(Date value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_WORK_DATE, value);
    }

    /**
     * 移動出庫作業日時(<code>RETRIEVAL_WORK_DATE</code>) から値を取得します。
     * @return RETRIEVAL_WORK_DATE
     */
    public Date getRetrievalWorkDate()  // @@GEN_V3@@
    {
        return (Date)getValue(RETRIEVAL_WORK_DATE, null) ;
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_WORK_SECOND
     */
    public void setRetrievalWorkSecond(int value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_WORK_SECOND, HandlerUtil.toObject(value));
    }

    /**
     * 移動出庫作業秒数(<code>RETRIEVAL_WORK_SECOND</code>) から値を取得します。
     * @return RETRIEVAL_WORK_SECOND
     */
    public int getRetrievalWorkSecond()  // @@GEN_V3@@
    {
        return getBigDecimal(RETRIEVAL_WORK_SECOND, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>) に値をセットします。
     * @param value セットする値PLAN_QTY
     */
    public void setPlanQty(int value)  // @@GEN_V3@@
    {
        setValue(PLAN_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 予定数(<code>PLAN_QTY</code>) から値を取得します。
     * @return PLAN_QTY
     */
    public int getPlanQty()  // @@GEN_V3@@
    {
        return getBigDecimal(PLAN_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_RESULT_QTY
     */
    public void setRetrievalResultQty(int value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_RESULT_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 出庫数(<code>RETRIEVAL_RESULT_QTY</code>) から値を取得します。
     * @return RETRIEVAL_RESULT_QTY
     */
    public int getRetrievalResultQty()  // @@GEN_V3@@
    {
        return getBigDecimal(RETRIEVAL_RESULT_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>) に値をセットします。
     * @param value セットする値STORAGE_RESULT_QTY
     */
    public void setStorageResultQty(int value)  // @@GEN_V3@@
    {
        setValue(STORAGE_RESULT_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 入庫数(<code>STORAGE_RESULT_QTY</code>) から値を取得します。
     * @return STORAGE_RESULT_QTY
     */
    public int getStorageResultQty()  // @@GEN_V3@@
    {
        return getBigDecimal(STORAGE_RESULT_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>) に値をセットします。
     * @param value セットする値STORAGE_AREA_NO
     */
    public void setStorageAreaNo(String value)  // @@GEN_V3@@
    {
        setValue(STORAGE_AREA_NO, value);
    }

    /**
     * 移動入庫エリア(<code>STORAGE_AREA_NO</code>) から値を取得します。
     * @return STORAGE_AREA_NO
     */
    public String getStorageAreaNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STORAGE_AREA_NO, "")) ;
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>) に値をセットします。
     * @param value セットする値STORAGE_LOCATION_NO
     */
    public void setStorageLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(STORAGE_LOCATION_NO, value);
    }

    /**
     * 移動入庫予定棚(<code>STORAGE_LOCATION_NO</code>) から値を取得します。
     * @return STORAGE_LOCATION_NO
     */
    public String getStorageLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STORAGE_LOCATION_NO, "")) ;
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>) に値をセットします。
     * @param value セットする値STORAGE_USER_ID
     */
    public void setStorageUserId(String value)  // @@GEN_V3@@
    {
        setValue(STORAGE_USER_ID, value);
    }

    /**
     * 移動入庫ユーザID(<code>STORAGE_USER_ID</code>) から値を取得します。
     * @return STORAGE_USER_ID
     */
    public String getStorageUserId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STORAGE_USER_ID, "")) ;
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値STORAGE_TERMINAL_NO
     */
    public void setStorageTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(STORAGE_TERMINAL_NO, value);
    }

    /**
     * 移動入庫端末No.、RFTNo.(<code>STORAGE_TERMINAL_NO</code>) から値を取得します。
     * @return STORAGE_TERMINAL_NO
     */
    public String getStorageTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STORAGE_TERMINAL_NO, "")) ;
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>) に値をセットします。
     * @param value セットする値STORAGE_WORK_DATE
     */
    public void setStorageWorkDate(Date value)  // @@GEN_V3@@
    {
        setValue(STORAGE_WORK_DATE, value);
    }

    /**
     * 移動入庫作業日時(<code>STORAGE_WORK_DATE</code>) から値を取得します。
     * @return STORAGE_WORK_DATE
     */
    public Date getStorageWorkDate()  // @@GEN_V3@@
    {
        return (Date)getValue(STORAGE_WORK_DATE, null) ;
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>) に値をセットします。
     * @param value セットする値STORAGE_WORK_DAY
     */
    public void setStorageWorkDay(String value)  // @@GEN_V3@@
    {
        setValue(STORAGE_WORK_DAY, value);
    }

    /**
     * 移動入庫作業日(<code>STORAGE_WORK_DAY</code>) から値を取得します。
     * @return STORAGE_WORK_DAY
     */
    public String getStorageWorkDay()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STORAGE_WORK_DAY, "")) ;
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>) に値をセットします。
     * @param value セットする値STORAGE_WORK_SECOND
     */
    public void setStorageWorkSecond(int value)  // @@GEN_V3@@
    {
        setValue(STORAGE_WORK_SECOND, HandlerUtil.toObject(value));
    }

    /**
     * 移動入庫作業秒数(<code>STORAGE_WORK_SECOND</code>) から値を取得します。
     * @return STORAGE_WORK_SECOND
     */
    public int getStorageWorkSecond()  // @@GEN_V3@@
    {
        return getBigDecimal(STORAGE_WORK_SECOND, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_START_DATE
     */
    public void setRetrievalStartDate(Date value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_START_DATE, value);
    }

    /**
     * 出庫開始日時(<code>RETRIEVAL_START_DATE</code>) から値を取得します。
     * @return RETRIEVAL_START_DATE
     */
    public Date getRetrievalStartDate()  // @@GEN_V3@@
    {
        return (Date)getValue(RETRIEVAL_START_DATE, null) ;
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
        return "$Id: MoveWorkInfo.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}

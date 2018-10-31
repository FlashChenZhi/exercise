// $Id: WorkList.java 5192 2009-10-20 08:52:45Z okamura $
// $LastChangedRevision: 5192 $
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
 * WORKLISTのエンティティクラスです。
 *
 * @version $Revision: 5192 $, $Date: 2009-10-20 17:52:45 +0900 (火, 20 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public class WorkList
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNWORKLIST" ;

    /*
     * テーブル名: DNWORKLIST
     * 作業No. :                       JOB_NO              varchar2(8)
     * 搬送Key :                       CARRY_KEY           varchar2(16)
     * 設定単位キー :                  SETTING_UNIT_KEY    varchar2(8)
     * 集約作業No :                    COLLECT_JOB_NO      varchar2(8)
     * 作業区分 :                      JOB_TYPE            varchar2(2)
     * 予定一意キー :                  PLAN_UKEY           varchar2(10)
     * 在庫ID :                        STOCK_ID            varchar2(8)
     * パレットID :                    PALLET_ID           varchar2(8)
     * 予定日 :                        PLAN_DAY            varchar2(8)
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 荷主名称 :                      CONSIGNOR_NAME      varchar2(40)
     * 出荷先コード :                  CUSTOMER_CODE       varchar2(16)
     * 出荷先名称 :                    CUSTOMER_NAME       varchar2(40)
     * 出荷伝票No :                    SHIP_TICKET_NO      varchar2(16)
     * 出荷伝票行 :                    SHIP_LINE_NO        number
     * 出荷伝票作業枝番 :              SHIP_BRANCH_NO      number
     * バッチNo. :                     BATCH_NO            varchar2(8)
     * オーダNo. :                     ORDER_NO            varchar2(16)
     * 予定エリア :                    PLAN_AREA_NO        varchar2(4)
     * 予定棚 :                        PLAN_LOCATION_NO    varchar2(11)
     * 商品コード :                    ITEM_CODE           varchar2(40)
     * 商品名称 :                      ITEM_NAME           varchar2(100)
     * JANコード :                     JAN                 varchar2(13)
     * ケースITF :                     ITF                 varchar2(16)
     * ボールITF :                     BUNDLE_ITF          varchar2(16)
     * ケース入数 :                    ENTERING_QTY        number
     * ボール入数 :                    BUNDLE_ENTERING_QTY number
     * 予定ロットNo :                  PLAN_LOT_NO         varchar2(60)
     * 予定数 :                        PLAN_QTY            number
     * 在庫数 :                        STOCK_QTY           number
     * 出庫可能数 :                    ALLOCATION_QTY      number
     * 理由区分 :                      REASON_TYPE         number
     * 理由名称 :                      REASON_NAME         varchar2(40)
     * 優先区分 :                      PRIORITY            varchar2(1)
     * 出庫ロケーションNo. :           RETRIEVAL_STATION_NOvarchar2(16)
     * 出庫指示詳細 :                  RETRIEVAL_DETAIL    varchar2(1)
     * 作業No. :                       WORK_NO             varchar2(16)
     * 搬送元ステーションNo. :         SOURCE_STATION_NO   varchar2(16)
     * 搬送先ステーションNo. :         DEST_STATION_NO     varchar2(16)
     * スケジュールNo :                SCHEDULE_NO         varchar2(9)
     * 最終ステーションNo. :           END_STATION_NO      varchar2(16)
     * ユーザID :                      USER_ID             varchar2(20)
     * ユーザ名称 :                    USER_NAME           varchar2(40)
     * 端末No、RFTNo :                 TERMINAL_NO         varchar2(4)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     */
    /** フィールド定義 (作業No.(<code>JOB_NO</code>)) */
    public static final FieldName JOB_NO = new FieldName(STORE_NAME, "JOB_NO") ;

    /** フィールド定義 (搬送Key(<code>CARRY_KEY</code>)) */
    public static final FieldName CARRY_KEY = new FieldName(STORE_NAME, "CARRY_KEY") ;

    /** フィールド定義 (設定単位キー(<code>SETTING_UNIT_KEY</code>)) */
    public static final FieldName SETTING_UNIT_KEY = new FieldName(STORE_NAME, "SETTING_UNIT_KEY") ;

    /** フィールド定義 (集約作業No(<code>COLLECT_JOB_NO</code>)) */
    public static final FieldName COLLECT_JOB_NO = new FieldName(STORE_NAME, "COLLECT_JOB_NO") ;

    /** フィールド定義 (作業区分(<code>JOB_TYPE</code>)) */
    public static final FieldName JOB_TYPE = new FieldName(STORE_NAME, "JOB_TYPE") ;

    /** フィールド定義 (予定一意キー(<code>PLAN_UKEY</code>)) */
    public static final FieldName PLAN_UKEY = new FieldName(STORE_NAME, "PLAN_UKEY") ;

    /** フィールド定義 (在庫ID(<code>STOCK_ID</code>)) */
    public static final FieldName STOCK_ID = new FieldName(STORE_NAME, "STOCK_ID") ;

    /** フィールド定義 (パレットID(<code>PALLET_ID</code>)) */
    public static final FieldName PALLET_ID = new FieldName(STORE_NAME, "PALLET_ID") ;

    /** フィールド定義 (予定日(<code>PLAN_DAY</code>)) */
    public static final FieldName PLAN_DAY = new FieldName(STORE_NAME, "PLAN_DAY") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (荷主名称(<code>CONSIGNOR_NAME</code>)) */
    public static final FieldName CONSIGNOR_NAME = new FieldName(STORE_NAME, "CONSIGNOR_NAME") ;

    /** フィールド定義 (出荷先コード(<code>CUSTOMER_CODE</code>)) */
    public static final FieldName CUSTOMER_CODE = new FieldName(STORE_NAME, "CUSTOMER_CODE") ;

    /** フィールド定義 (出荷先名称(<code>CUSTOMER_NAME</code>)) */
    public static final FieldName CUSTOMER_NAME = new FieldName(STORE_NAME, "CUSTOMER_NAME") ;

    /** フィールド定義 (出荷伝票No(<code>SHIP_TICKET_NO</code>)) */
    public static final FieldName SHIP_TICKET_NO = new FieldName(STORE_NAME, "SHIP_TICKET_NO") ;

    /** フィールド定義 (出荷伝票行(<code>SHIP_LINE_NO</code>)) */
    public static final FieldName SHIP_LINE_NO = new FieldName(STORE_NAME, "SHIP_LINE_NO") ;

    /** フィールド定義 (出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>)) */
    public static final FieldName SHIP_BRANCH_NO = new FieldName(STORE_NAME, "SHIP_BRANCH_NO") ;

    /** フィールド定義 (バッチNo.(<code>BATCH_NO</code>)) */
    public static final FieldName BATCH_NO = new FieldName(STORE_NAME, "BATCH_NO") ;

    /** フィールド定義 (オーダNo.(<code>ORDER_NO</code>)) */
    public static final FieldName ORDER_NO = new FieldName(STORE_NAME, "ORDER_NO") ;

    /** フィールド定義 (予定エリア(<code>PLAN_AREA_NO</code>)) */
    public static final FieldName PLAN_AREA_NO = new FieldName(STORE_NAME, "PLAN_AREA_NO") ;

    /** フィールド定義 (予定棚(<code>PLAN_LOCATION_NO</code>)) */
    public static final FieldName PLAN_LOCATION_NO = new FieldName(STORE_NAME, "PLAN_LOCATION_NO") ;

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE") ;

    /** フィールド定義 (商品名称(<code>ITEM_NAME</code>)) */
    public static final FieldName ITEM_NAME = new FieldName(STORE_NAME, "ITEM_NAME") ;

    /** フィールド定義 (JANコード(<code>JAN</code>)) */
    public static final FieldName JAN = new FieldName(STORE_NAME, "JAN") ;

    /** フィールド定義 (ケースITF(<code>ITF</code>)) */
    public static final FieldName ITF = new FieldName(STORE_NAME, "ITF") ;

    /** フィールド定義 (ボールITF(<code>BUNDLE_ITF</code>)) */
    public static final FieldName BUNDLE_ITF = new FieldName(STORE_NAME, "BUNDLE_ITF") ;

    /** フィールド定義 (ケース入数(<code>ENTERING_QTY</code>)) */
    public static final FieldName ENTERING_QTY = new FieldName(STORE_NAME, "ENTERING_QTY") ;

    /** フィールド定義 (ボール入数(<code>BUNDLE_ENTERING_QTY</code>)) */
    public static final FieldName BUNDLE_ENTERING_QTY = new FieldName(STORE_NAME, "BUNDLE_ENTERING_QTY") ;

    /** フィールド定義 (予定ロットNo(<code>PLAN_LOT_NO</code>)) */
    public static final FieldName PLAN_LOT_NO = new FieldName(STORE_NAME, "PLAN_LOT_NO") ;

    /** フィールド定義 (予定数(<code>PLAN_QTY</code>)) */
    public static final FieldName PLAN_QTY = new FieldName(STORE_NAME, "PLAN_QTY") ;

    /** フィールド定義 (在庫数(<code>STOCK_QTY</code>)) */
    public static final FieldName STOCK_QTY = new FieldName(STORE_NAME, "STOCK_QTY") ;

    /** フィールド定義 (出庫可能数(<code>ALLOCATION_QTY</code>)) */
    public static final FieldName ALLOCATION_QTY = new FieldName(STORE_NAME, "ALLOCATION_QTY") ;

    /** フィールド定義 (理由区分(<code>REASON_TYPE</code>)) */
    public static final FieldName REASON_TYPE = new FieldName(STORE_NAME, "REASON_TYPE") ;

    /** フィールド定義 (理由名称(<code>REASON_NAME</code>)) */
    public static final FieldName REASON_NAME = new FieldName(STORE_NAME, "REASON_NAME") ;

    /** フィールド定義 (優先区分(<code>PRIORITY</code>)) */
    public static final FieldName PRIORITY = new FieldName(STORE_NAME, "PRIORITY") ;

    /** フィールド定義 (出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>)) */
    public static final FieldName RETRIEVAL_STATION_NO = new FieldName(STORE_NAME, "RETRIEVAL_STATION_NO") ;

    /** フィールド定義 (出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)) */
    public static final FieldName RETRIEVAL_DETAIL = new FieldName(STORE_NAME, "RETRIEVAL_DETAIL") ;

    /** フィールド定義 (作業No.(<code>WORK_NO</code>)) */
    public static final FieldName WORK_NO = new FieldName(STORE_NAME, "WORK_NO") ;

    /** フィールド定義 (搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>)) */
    public static final FieldName SOURCE_STATION_NO = new FieldName(STORE_NAME, "SOURCE_STATION_NO") ;

    /** フィールド定義 (搬送先ステーションNo.(<code>DEST_STATION_NO</code>)) */
    public static final FieldName DEST_STATION_NO = new FieldName(STORE_NAME, "DEST_STATION_NO") ;

    /** フィールド定義 (スケジュールNo(<code>SCHEDULE_NO</code>)) */
    public static final FieldName SCHEDULE_NO = new FieldName(STORE_NAME, "SCHEDULE_NO") ;

    /** フィールド定義 (最終ステーションNo.(<code>END_STATION_NO</code>)) */
    public static final FieldName END_STATION_NO = new FieldName(STORE_NAME, "END_STATION_NO") ;

    /** フィールド定義 (ユーザID(<code>USER_ID</code>)) */
    public static final FieldName USER_ID = new FieldName(STORE_NAME, "USER_ID") ;

    /** フィールド定義 (ユーザ名称(<code>USER_NAME</code>)) */
    public static final FieldName USER_NAME = new FieldName(STORE_NAME, "USER_NAME") ;

    /** フィールド定義 (端末No、RFTNo(<code>TERMINAL_NO</code>)) */
    public static final FieldName TERMINAL_NO = new FieldName(STORE_NAME, "TERMINAL_NO") ;

    /** フィールド定義 (登録日時(<code>REGIST_DATE</code>)) */
    public static final FieldName REGIST_DATE = new FieldName(STORE_NAME, "REGIST_DATE") ;

    /** フィールド定義 (登録処理名(<code>REGIST_PNAME</code>)) */
    public static final FieldName REGIST_PNAME = new FieldName(STORE_NAME, "REGIST_PNAME") ;


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
    public WorkList()
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
     * 搬送Key(<code>CARRY_KEY</code>) に値をセットします。
     * @param value セットする値CARRY_KEY
     */
    public void setCarryKey(String value)  // @@GEN_V3@@
    {
        setValue(CARRY_KEY, value);
    }

    /**
     * 搬送Key(<code>CARRY_KEY</code>) から値を取得します。
     * @return CARRY_KEY
     */
    public String getCarryKey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CARRY_KEY, "")) ;
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
     * 集約作業No(<code>COLLECT_JOB_NO</code>) に値をセットします。
     * @param value セットする値COLLECT_JOB_NO
     */
    public void setCollectJobNo(String value)  // @@GEN_V3@@
    {
        setValue(COLLECT_JOB_NO, value);
    }

    /**
     * 集約作業No(<code>COLLECT_JOB_NO</code>) から値を取得します。
     * @return COLLECT_JOB_NO
     */
    public String getCollectJobNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(COLLECT_JOB_NO, "")) ;
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
     * 予定一意キー(<code>PLAN_UKEY</code>) に値をセットします。
     * @param value セットする値PLAN_UKEY
     */
    public void setPlanUkey(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_UKEY, value);
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>) から値を取得します。
     * @return PLAN_UKEY
     */
    public String getPlanUkey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_UKEY, "")) ;
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
     * パレットID(<code>PALLET_ID</code>) に値をセットします。
     * @param value セットする値PALLET_ID
     */
    public void setPalletId(String value)  // @@GEN_V3@@
    {
        setValue(PALLET_ID, value);
    }

    /**
     * パレットID(<code>PALLET_ID</code>) から値を取得します。
     * @return PALLET_ID
     */
    public String getPalletId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PALLET_ID, "")) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>) に値をセットします。
     * @param value セットする値PLAN_DAY
     */
    public void setPlanDay(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_DAY, value);
    }

    /**
     * 予定日(<code>PLAN_DAY</code>) から値を取得します。
     * @return PLAN_DAY
     */
    public String getPlanDay()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_DAY, "")) ;
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
     * 出荷伝票No(<code>SHIP_TICKET_NO</code>) に値をセットします。
     * @param value セットする値SHIP_TICKET_NO
     */
    public void setShipTicketNo(String value)  // @@GEN_V3@@
    {
        setValue(SHIP_TICKET_NO, value);
    }

    /**
     * 出荷伝票No(<code>SHIP_TICKET_NO</code>) から値を取得します。
     * @return SHIP_TICKET_NO
     */
    public String getShipTicketNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SHIP_TICKET_NO, "")) ;
    }

    /**
     * 出荷伝票行(<code>SHIP_LINE_NO</code>) に値をセットします。
     * @param value セットする値SHIP_LINE_NO
     */
    public void setShipLineNo(int value)  // @@GEN_V3@@
    {
        setValue(SHIP_LINE_NO, HandlerUtil.toObject(value));
    }

    /**
     * 出荷伝票行(<code>SHIP_LINE_NO</code>) から値を取得します。
     * @return SHIP_LINE_NO
     */
    public int getShipLineNo()  // @@GEN_V3@@
    {
        return getBigDecimal(SHIP_LINE_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>) に値をセットします。
     * @param value セットする値SHIP_BRANCH_NO
     */
    public void setShipBranchNo(int value)  // @@GEN_V3@@
    {
        setValue(SHIP_BRANCH_NO, HandlerUtil.toObject(value));
    }

    /**
     * 出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>) から値を取得します。
     * @return SHIP_BRANCH_NO
     */
    public int getShipBranchNo()  // @@GEN_V3@@
    {
        return getBigDecimal(SHIP_BRANCH_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>) に値をセットします。
     * @param value セットする値BATCH_NO
     */
    public void setBatchNo(String value)  // @@GEN_V3@@
    {
        setValue(BATCH_NO, value);
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>) から値を取得します。
     * @return BATCH_NO
     */
    public String getBatchNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(BATCH_NO, "")) ;
    }

    /**
     * オーダNo.(<code>ORDER_NO</code>) に値をセットします。
     * @param value セットする値ORDER_NO
     */
    public void setOrderNo(String value)  // @@GEN_V3@@
    {
        setValue(ORDER_NO, value);
    }

    /**
     * オーダNo.(<code>ORDER_NO</code>) から値を取得します。
     * @return ORDER_NO
     */
    public String getOrderNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ORDER_NO, "")) ;
    }

    /**
     * 予定エリア(<code>PLAN_AREA_NO</code>) に値をセットします。
     * @param value セットする値PLAN_AREA_NO
     */
    public void setPlanAreaNo(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_AREA_NO, value);
    }

    /**
     * 予定エリア(<code>PLAN_AREA_NO</code>) から値を取得します。
     * @return PLAN_AREA_NO
     */
    public String getPlanAreaNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_AREA_NO, "")) ;
    }

    /**
     * 予定棚(<code>PLAN_LOCATION_NO</code>) に値をセットします。
     * @param value セットする値PLAN_LOCATION_NO
     */
    public void setPlanLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_LOCATION_NO, value);
    }

    /**
     * 予定棚(<code>PLAN_LOCATION_NO</code>) から値を取得します。
     * @return PLAN_LOCATION_NO
     */
    public String getPlanLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_LOCATION_NO, "")) ;
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
     * 商品名称(<code>ITEM_NAME</code>) に値をセットします。
     * @param value セットする値ITEM_NAME
     */
    public void setItemName(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_NAME, value);
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>) から値を取得します。
     * @return ITEM_NAME
     */
    public String getItemName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_NAME, "")) ;
    }

    /**
     * JANコード(<code>JAN</code>) に値をセットします。
     * @param value セットする値JAN
     */
    public void setJan(String value)  // @@GEN_V3@@
    {
        setValue(JAN, value);
    }

    /**
     * JANコード(<code>JAN</code>) から値を取得します。
     * @return JAN
     */
    public String getJan()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(JAN, "")) ;
    }

    /**
     * ケースITF(<code>ITF</code>) に値をセットします。
     * @param value セットする値ITF
     */
    public void setItf(String value)  // @@GEN_V3@@
    {
        setValue(ITF, value);
    }

    /**
     * ケースITF(<code>ITF</code>) から値を取得します。
     * @return ITF
     */
    public String getItf()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITF, "")) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>) に値をセットします。
     * @param value セットする値BUNDLE_ITF
     */
    public void setBundleItf(String value)  // @@GEN_V3@@
    {
        setValue(BUNDLE_ITF, value);
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>) から値を取得します。
     * @return BUNDLE_ITF
     */
    public String getBundleItf()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(BUNDLE_ITF, "")) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>) に値をセットします。
     * @param value セットする値ENTERING_QTY
     */
    public void setEnteringQty(int value)  // @@GEN_V3@@
    {
        setValue(ENTERING_QTY, HandlerUtil.toObject(value));
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>) から値を取得します。
     * @return ENTERING_QTY
     */
    public int getEnteringQty()  // @@GEN_V3@@
    {
        return getBigDecimal(ENTERING_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>) に値をセットします。
     * @param value セットする値BUNDLE_ENTERING_QTY
     */
    public void setBundleEnteringQty(int value)  // @@GEN_V3@@
    {
        setValue(BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value));
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>) から値を取得します。
     * @return BUNDLE_ENTERING_QTY
     */
    public int getBundleEnteringQty()  // @@GEN_V3@@
    {
        return getBigDecimal(BUNDLE_ENTERING_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>) に値をセットします。
     * @param value セットする値PLAN_LOT_NO
     */
    public void setPlanLotNo(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_LOT_NO, value);
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>) から値を取得します。
     * @return PLAN_LOT_NO
     */
    public String getPlanLotNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_LOT_NO, "")) ;
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
     * 在庫数(<code>STOCK_QTY</code>) に値をセットします。
     * @param value セットする値STOCK_QTY
     */
    public void setStockQty(int value)  // @@GEN_V3@@
    {
        setValue(STOCK_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 在庫数(<code>STOCK_QTY</code>) から値を取得します。
     * @return STOCK_QTY
     */
    public int getStockQty()  // @@GEN_V3@@
    {
        return getBigDecimal(STOCK_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 出庫可能数(<code>ALLOCATION_QTY</code>) に値をセットします。
     * @param value セットする値ALLOCATION_QTY
     */
    public void setAllocationQty(int value)  // @@GEN_V3@@
    {
        setValue(ALLOCATION_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 出庫可能数(<code>ALLOCATION_QTY</code>) から値を取得します。
     * @return ALLOCATION_QTY
     */
    public int getAllocationQty()  // @@GEN_V3@@
    {
        return getBigDecimal(ALLOCATION_QTY, BigDecimal.ZERO).intValue() ;
    }

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
     * 理由名称(<code>REASON_NAME</code>) に値をセットします。
     * @param value セットする値REASON_NAME
     */
    public void setReasonName(String value)  // @@GEN_V3@@
    {
        setValue(REASON_NAME, value);
    }

    /**
     * 理由名称(<code>REASON_NAME</code>) から値を取得します。
     * @return REASON_NAME
     */
    public String getReasonName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REASON_NAME, "")) ;
    }

    /**
     * 優先区分(<code>PRIORITY</code>) に値をセットします。
     * @param value セットする値PRIORITY
     */
    public void setPriority(String value)  // @@GEN_V3@@
    {
        setValue(PRIORITY, value);
    }

    /**
     * 優先区分(<code>PRIORITY</code>) から値を取得します。
     * @return PRIORITY
     */
    public String getPriority()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PRIORITY, "")) ;
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_STATION_NO
     */
    public void setRetrievalStationNo(String value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_STATION_NO, value);
    }

    /**
     * 出庫ロケーションNo.(<code>RETRIEVAL_STATION_NO</code>) から値を取得します。
     * @return RETRIEVAL_STATION_NO
     */
    public String getRetrievalStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RETRIEVAL_STATION_NO, "")) ;
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>) に値をセットします。
     * @param value セットする値RETRIEVAL_DETAIL
     */
    public void setRetrievalDetail(String value)  // @@GEN_V3@@
    {
        setValue(RETRIEVAL_DETAIL, value);
    }

    /**
     * 出庫指示詳細(<code>RETRIEVAL_DETAIL</code>) から値を取得します。
     * @return RETRIEVAL_DETAIL
     */
    public String getRetrievalDetail()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RETRIEVAL_DETAIL, "")) ;
    }

    /**
     * 作業No.(<code>WORK_NO</code>) に値をセットします。
     * @param value セットする値WORK_NO
     */
    public void setWorkNo(String value)  // @@GEN_V3@@
    {
        setValue(WORK_NO, value);
    }

    /**
     * 作業No.(<code>WORK_NO</code>) から値を取得します。
     * @return WORK_NO
     */
    public String getWorkNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WORK_NO, "")) ;
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>) に値をセットします。
     * @param value セットする値SOURCE_STATION_NO
     */
    public void setSourceStationNo(String value)  // @@GEN_V3@@
    {
        setValue(SOURCE_STATION_NO, value);
    }

    /**
     * 搬送元ステーションNo.(<code>SOURCE_STATION_NO</code>) から値を取得します。
     * @return SOURCE_STATION_NO
     */
    public String getSourceStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SOURCE_STATION_NO, "")) ;
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>) に値をセットします。
     * @param value セットする値DEST_STATION_NO
     */
    public void setDestStationNo(String value)  // @@GEN_V3@@
    {
        setValue(DEST_STATION_NO, value);
    }

    /**
     * 搬送先ステーションNo.(<code>DEST_STATION_NO</code>) から値を取得します。
     * @return DEST_STATION_NO
     */
    public String getDestStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(DEST_STATION_NO, "")) ;
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>) に値をセットします。
     * @param value セットする値SCHEDULE_NO
     */
    public void setScheduleNo(String value)  // @@GEN_V3@@
    {
        setValue(SCHEDULE_NO, value);
    }

    /**
     * スケジュールNo(<code>SCHEDULE_NO</code>) から値を取得します。
     * @return SCHEDULE_NO
     */
    public String getScheduleNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SCHEDULE_NO, "")) ;
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>) に値をセットします。
     * @param value セットする値END_STATION_NO
     */
    public void setEndStationNo(String value)  // @@GEN_V3@@
    {
        setValue(END_STATION_NO, value);
    }

    /**
     * 最終ステーションNo.(<code>END_STATION_NO</code>) から値を取得します。
     * @return END_STATION_NO
     */
    public String getEndStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(END_STATION_NO, "")) ;
    }

    /**
     * ユーザID(<code>USER_ID</code>) に値をセットします。
     * @param value セットする値USER_ID
     */
    public void setUserId(String value)  // @@GEN_V3@@
    {
        setValue(USER_ID, value);
    }

    /**
     * ユーザID(<code>USER_ID</code>) から値を取得します。
     * @return USER_ID
     */
    public String getUserId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USER_ID, "")) ;
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>) に値をセットします。
     * @param value セットする値USER_NAME
     */
    public void setUserName(String value)  // @@GEN_V3@@
    {
        setValue(USER_NAME, value);
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>) から値を取得します。
     * @return USER_NAME
     */
    public String getUserName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USER_NAME, "")) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値TERMINAL_NO
     */
    public void setTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_NO, value);
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>) から値を取得します。
     * @return TERMINAL_NO
     */
    public String getTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_NO, "")) ;
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
        return "$Id: WorkList.java 5192 2009-10-20 08:52:45Z okamura $" ;
    }
}

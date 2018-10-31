// $Id: PCTRetWorkInfo.java 3213 2009-03-02 06:59:20Z arai $
// $LastChangedRevision: 3213 $
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
 * PCTRETWORKINFOのエンティティクラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class PCTRetWorkInfo
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNPCTRETWORKINFO" ;

    /*
     * テーブル名: DNPCTRETWORKINFO
     * 作業No. :                       JOB_NO              varchar2(8)
     * 設定単位キー :                  SETTING_UNIT_KEY    varchar2(8)
     * 集約作業No :                    COLLECT_JOB_NO      varchar2(8)
     * 状態フラグ :                    STATUS_FLAG         varchar2(1)
     * ハードウェア区分 :              HARDWARE_TYPE       varchar2(1)
     * 予定一意キー :                  PLAN_UKEY           varchar2(10)
     * 在庫ID :                        STOCK_ID            varchar2(8)
     * システム接続キー :              SYSTEM_CONN_KEY     varchar2(16)
     * 予定日 :                        PLAN_DAY            varchar2(8)
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 得意先コード :                  REGULAR_CUSTOMER_CODEvarchar2(16)
     * 出荷先コード :                  CUSTOMER_CODE       varchar2(16)
     * 出荷先分類コード :              CUSTOMER_CATEGORY   varchar2(6)
     * 出荷伝票No :                    SHIP_TICKET_NO      varchar2(16)
     * 出荷伝票行 :                    SHIP_LINE_NO        number
     * 出荷伝票作業枝番 :              SHIP_BRANCH_NO      number
     * バッチNo. :                     BATCH_NO            varchar2(4)
     * バッチSeqNo. :                  BATCH_SEQ_NO        varchar2(12)
     * オーダNo. :                     ORDER_NO            varchar2(23)
     * オーダSeq :                     ORDER_SEQ           varchar2(2)
     * オーダ情報コメント :            ORDER_INFO          varchar2(20)
     * 予定オーダNo. :                 PLAN_ORDER_NO       varchar2(24)
     * 実績オーダNo. :                 RESULT_ORDER_NO     varchar2(24)
     * 予定エリア :                    PLAN_AREA_NO        varchar2(4)
     * 予定ゾーン :                    PLAN_ZONE_NO        varchar2(4)
     * 作業ゾーン :                    WORK_ZONE_NO        varchar2(4)
     * 予定棚 :                        PLAN_LOCATION_NO    varchar2(8)
     * 棚分割1 :                       LOC_SEPARATE_1      varchar2(4)
     * 棚分割2 :                       LOC_SEPARATE_2      varchar2(4)
     * 棚分割3 :                       LOC_SEPARATE_3      varchar2(4)
     * 棚分割4 :                       LOC_SEPARATE_4      varchar2(4)
     * 商品コード :                    ITEM_CODE           varchar2(16)
     * 基準日付 :                      USE_BY_DATE         varchar2(8)
     * アイテム情報コメント :          ITEM_INFO           varchar2(20)
     * 予定ロットNo :                  PLAN_LOT_NO         varchar2(16)
     * 予定数 :                        PLAN_QTY            number
     * 実績数 :                        RESULT_QTY          number
     * 欠品数 :                        SHORTAGE_QTY        number
     * 作業日 :                        WORK_DAY            varchar2(8)
     * ユーザID :                      USER_ID             varchar2(20)
     * 端末No、RFTNo :                 TERMINAL_NO         varchar2(4)
     * 作業秒数 :                      WORK_SECOND         number
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (作業No.(<code>JOB_NO</code>)) */
    public static final FieldName JOB_NO = new FieldName(STORE_NAME, "JOB_NO") ;

    /** フィールド定義 (設定単位キー(<code>SETTING_UNIT_KEY</code>)) */
    public static final FieldName SETTING_UNIT_KEY = new FieldName(STORE_NAME, "SETTING_UNIT_KEY") ;

    /** フィールド定義 (集約作業No(<code>COLLECT_JOB_NO</code>)) */
    public static final FieldName COLLECT_JOB_NO = new FieldName(STORE_NAME, "COLLECT_JOB_NO") ;

    /** フィールド定義 (状態フラグ(<code>STATUS_FLAG</code>)) */
    public static final FieldName STATUS_FLAG = new FieldName(STORE_NAME, "STATUS_FLAG") ;

    /** フィールド定義 (ハードウェア区分(<code>HARDWARE_TYPE</code>)) */
    public static final FieldName HARDWARE_TYPE = new FieldName(STORE_NAME, "HARDWARE_TYPE") ;

    /** フィールド定義 (予定一意キー(<code>PLAN_UKEY</code>)) */
    public static final FieldName PLAN_UKEY = new FieldName(STORE_NAME, "PLAN_UKEY") ;

    /** フィールド定義 (在庫ID(<code>STOCK_ID</code>)) */
    public static final FieldName STOCK_ID = new FieldName(STORE_NAME, "STOCK_ID") ;

    /** フィールド定義 (システム接続キー(<code>SYSTEM_CONN_KEY</code>)) */
    public static final FieldName SYSTEM_CONN_KEY = new FieldName(STORE_NAME, "SYSTEM_CONN_KEY") ;

    /** フィールド定義 (予定日(<code>PLAN_DAY</code>)) */
    public static final FieldName PLAN_DAY = new FieldName(STORE_NAME, "PLAN_DAY") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (得意先コード(<code>REGULAR_CUSTOMER_CODE</code>)) */
    public static final FieldName REGULAR_CUSTOMER_CODE = new FieldName(STORE_NAME, "REGULAR_CUSTOMER_CODE") ;

    /** フィールド定義 (出荷先コード(<code>CUSTOMER_CODE</code>)) */
    public static final FieldName CUSTOMER_CODE = new FieldName(STORE_NAME, "CUSTOMER_CODE") ;

    /** フィールド定義 (出荷先分類コード(<code>CUSTOMER_CATEGORY</code>)) */
    public static final FieldName CUSTOMER_CATEGORY = new FieldName(STORE_NAME, "CUSTOMER_CATEGORY") ;

    /** フィールド定義 (出荷伝票No(<code>SHIP_TICKET_NO</code>)) */
    public static final FieldName SHIP_TICKET_NO = new FieldName(STORE_NAME, "SHIP_TICKET_NO") ;

    /** フィールド定義 (出荷伝票行(<code>SHIP_LINE_NO</code>)) */
    public static final FieldName SHIP_LINE_NO = new FieldName(STORE_NAME, "SHIP_LINE_NO") ;

    /** フィールド定義 (出荷伝票作業枝番(<code>SHIP_BRANCH_NO</code>)) */
    public static final FieldName SHIP_BRANCH_NO = new FieldName(STORE_NAME, "SHIP_BRANCH_NO") ;

    /** フィールド定義 (バッチNo.(<code>BATCH_NO</code>)) */
    public static final FieldName BATCH_NO = new FieldName(STORE_NAME, "BATCH_NO") ;

    /** フィールド定義 (バッチSeqNo.(<code>BATCH_SEQ_NO</code>)) */
    public static final FieldName BATCH_SEQ_NO = new FieldName(STORE_NAME, "BATCH_SEQ_NO") ;

    /** フィールド定義 (オーダNo.(<code>ORDER_NO</code>)) */
    public static final FieldName ORDER_NO = new FieldName(STORE_NAME, "ORDER_NO") ;

    /** フィールド定義 (オーダSeq(<code>ORDER_SEQ</code>)) */
    public static final FieldName ORDER_SEQ = new FieldName(STORE_NAME, "ORDER_SEQ") ;

    /** フィールド定義 (オーダ情報コメント(<code>ORDER_INFO</code>)) */
    public static final FieldName ORDER_INFO = new FieldName(STORE_NAME, "ORDER_INFO") ;

    /** フィールド定義 (予定オーダNo.(<code>PLAN_ORDER_NO</code>)) */
    public static final FieldName PLAN_ORDER_NO = new FieldName(STORE_NAME, "PLAN_ORDER_NO") ;

    /** フィールド定義 (実績オーダNo.(<code>RESULT_ORDER_NO</code>)) */
    public static final FieldName RESULT_ORDER_NO = new FieldName(STORE_NAME, "RESULT_ORDER_NO") ;

    /** フィールド定義 (予定エリア(<code>PLAN_AREA_NO</code>)) */
    public static final FieldName PLAN_AREA_NO = new FieldName(STORE_NAME, "PLAN_AREA_NO") ;

    /** フィールド定義 (予定ゾーン(<code>PLAN_ZONE_NO</code>)) */
    public static final FieldName PLAN_ZONE_NO = new FieldName(STORE_NAME, "PLAN_ZONE_NO") ;

    /** フィールド定義 (作業ゾーン(<code>WORK_ZONE_NO</code>)) */
    public static final FieldName WORK_ZONE_NO = new FieldName(STORE_NAME, "WORK_ZONE_NO") ;

    /** フィールド定義 (予定棚(<code>PLAN_LOCATION_NO</code>)) */
    public static final FieldName PLAN_LOCATION_NO = new FieldName(STORE_NAME, "PLAN_LOCATION_NO") ;

    /** フィールド定義 (棚分割1(<code>LOC_SEPARATE_1</code>)) */
    public static final FieldName LOC_SEPARATE_1 = new FieldName(STORE_NAME, "LOC_SEPARATE_1") ;

    /** フィールド定義 (棚分割2(<code>LOC_SEPARATE_2</code>)) */
    public static final FieldName LOC_SEPARATE_2 = new FieldName(STORE_NAME, "LOC_SEPARATE_2") ;

    /** フィールド定義 (棚分割3(<code>LOC_SEPARATE_3</code>)) */
    public static final FieldName LOC_SEPARATE_3 = new FieldName(STORE_NAME, "LOC_SEPARATE_3") ;

    /** フィールド定義 (棚分割4(<code>LOC_SEPARATE_4</code>)) */
    public static final FieldName LOC_SEPARATE_4 = new FieldName(STORE_NAME, "LOC_SEPARATE_4") ;

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE") ;

    /** フィールド定義 (基準日付(<code>USE_BY_DATE</code>)) */
    public static final FieldName USE_BY_DATE = new FieldName(STORE_NAME, "USE_BY_DATE") ;

    /** フィールド定義 (アイテム情報コメント(<code>ITEM_INFO</code>)) */
    public static final FieldName ITEM_INFO = new FieldName(STORE_NAME, "ITEM_INFO") ;

    /** フィールド定義 (予定ロットNo(<code>PLAN_LOT_NO</code>)) */
    public static final FieldName PLAN_LOT_NO = new FieldName(STORE_NAME, "PLAN_LOT_NO") ;

    /** フィールド定義 (予定数(<code>PLAN_QTY</code>)) */
    public static final FieldName PLAN_QTY = new FieldName(STORE_NAME, "PLAN_QTY") ;

    /** フィールド定義 (実績数(<code>RESULT_QTY</code>)) */
    public static final FieldName RESULT_QTY = new FieldName(STORE_NAME, "RESULT_QTY") ;

    /** フィールド定義 (欠品数(<code>SHORTAGE_QTY</code>)) */
    public static final FieldName SHORTAGE_QTY = new FieldName(STORE_NAME, "SHORTAGE_QTY") ;

    /** フィールド定義 (作業日(<code>WORK_DAY</code>)) */
    public static final FieldName WORK_DAY = new FieldName(STORE_NAME, "WORK_DAY") ;

    /** フィールド定義 (ユーザID(<code>USER_ID</code>)) */
    public static final FieldName USER_ID = new FieldName(STORE_NAME, "USER_ID") ;

    /** フィールド定義 (端末No、RFTNo(<code>TERMINAL_NO</code>)) */
    public static final FieldName TERMINAL_NO = new FieldName(STORE_NAME, "TERMINAL_NO") ;

    /** フィールド定義 (作業秒数(<code>WORK_SECOND</code>)) */
    public static final FieldName WORK_SECOND = new FieldName(STORE_NAME, "WORK_SECOND") ;

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
    public PCTRetWorkInfo()
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
     * システム接続キー(<code>SYSTEM_CONN_KEY</code>) に値をセットします。
     * @param value セットする値SYSTEM_CONN_KEY
     */
    public void setSystemConnKey(String value)  // @@GEN_V3@@
    {
        setValue(SYSTEM_CONN_KEY, value);
    }

    /**
     * システム接続キー(<code>SYSTEM_CONN_KEY</code>) から値を取得します。
     * @return SYSTEM_CONN_KEY
     */
    public String getSystemConnKey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SYSTEM_CONN_KEY, "")) ;
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
     * 得意先コード(<code>REGULAR_CUSTOMER_CODE</code>) に値をセットします。
     * @param value セットする値REGULAR_CUSTOMER_CODE
     */
    public void setRegularCustomerCode(String value)  // @@GEN_V3@@
    {
        setValue(REGULAR_CUSTOMER_CODE, value);
    }

    /**
     * 得意先コード(<code>REGULAR_CUSTOMER_CODE</code>) から値を取得します。
     * @return REGULAR_CUSTOMER_CODE
     */
    public String getRegularCustomerCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REGULAR_CUSTOMER_CODE, "")) ;
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
     * 出荷先分類コード(<code>CUSTOMER_CATEGORY</code>) に値をセットします。
     * @param value セットする値CUSTOMER_CATEGORY
     */
    public void setCustomerCategory(String value)  // @@GEN_V3@@
    {
        setValue(CUSTOMER_CATEGORY, value);
    }

    /**
     * 出荷先分類コード(<code>CUSTOMER_CATEGORY</code>) から値を取得します。
     * @return CUSTOMER_CATEGORY
     */
    public String getCustomerCategory()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CUSTOMER_CATEGORY, "")) ;
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
     * バッチSeqNo.(<code>BATCH_SEQ_NO</code>) に値をセットします。
     * @param value セットする値BATCH_SEQ_NO
     */
    public void setBatchSeqNo(String value)  // @@GEN_V3@@
    {
        setValue(BATCH_SEQ_NO, value);
    }

    /**
     * バッチSeqNo.(<code>BATCH_SEQ_NO</code>) から値を取得します。
     * @return BATCH_SEQ_NO
     */
    public String getBatchSeqNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(BATCH_SEQ_NO, "")) ;
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
     * オーダSeq(<code>ORDER_SEQ</code>) に値をセットします。
     * @param value セットする値ORDER_SEQ
     */
    public void setOrderSeq(String value)  // @@GEN_V3@@
    {
        setValue(ORDER_SEQ, value);
    }

    /**
     * オーダSeq(<code>ORDER_SEQ</code>) から値を取得します。
     * @return ORDER_SEQ
     */
    public String getOrderSeq()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ORDER_SEQ, "")) ;
    }

    /**
     * オーダ情報コメント(<code>ORDER_INFO</code>) に値をセットします。
     * @param value セットする値ORDER_INFO
     */
    public void setOrderInfo(String value)  // @@GEN_V3@@
    {
        setValue(ORDER_INFO, value);
    }

    /**
     * オーダ情報コメント(<code>ORDER_INFO</code>) から値を取得します。
     * @return ORDER_INFO
     */
    public String getOrderInfo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ORDER_INFO, "")) ;
    }

    /**
     * 予定オーダNo.(<code>PLAN_ORDER_NO</code>) に値をセットします。
     * @param value セットする値PLAN_ORDER_NO
     */
    public void setPlanOrderNo(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_ORDER_NO, value);
    }

    /**
     * 予定オーダNo.(<code>PLAN_ORDER_NO</code>) から値を取得します。
     * @return PLAN_ORDER_NO
     */
    public String getPlanOrderNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_ORDER_NO, "")) ;
    }

    /**
     * 実績オーダNo.(<code>RESULT_ORDER_NO</code>) に値をセットします。
     * @param value セットする値RESULT_ORDER_NO
     */
    public void setResultOrderNo(String value)  // @@GEN_V3@@
    {
        setValue(RESULT_ORDER_NO, value);
    }

    /**
     * 実績オーダNo.(<code>RESULT_ORDER_NO</code>) から値を取得します。
     * @return RESULT_ORDER_NO
     */
    public String getResultOrderNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RESULT_ORDER_NO, "")) ;
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
     * 予定ゾーン(<code>PLAN_ZONE_NO</code>) に値をセットします。
     * @param value セットする値PLAN_ZONE_NO
     */
    public void setPlanZoneNo(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_ZONE_NO, value);
    }

    /**
     * 予定ゾーン(<code>PLAN_ZONE_NO</code>) から値を取得します。
     * @return PLAN_ZONE_NO
     */
    public String getPlanZoneNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_ZONE_NO, "")) ;
    }

    /**
     * 作業ゾーン(<code>WORK_ZONE_NO</code>) に値をセットします。
     * @param value セットする値WORK_ZONE_NO
     */
    public void setWorkZoneNo(String value)  // @@GEN_V3@@
    {
        setValue(WORK_ZONE_NO, value);
    }

    /**
     * 作業ゾーン(<code>WORK_ZONE_NO</code>) から値を取得します。
     * @return WORK_ZONE_NO
     */
    public String getWorkZoneNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WORK_ZONE_NO, "")) ;
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
     * 棚分割1(<code>LOC_SEPARATE_1</code>) に値をセットします。
     * @param value セットする値LOC_SEPARATE_1
     */
    public void setLocSeparate1(String value)  // @@GEN_V3@@
    {
        setValue(LOC_SEPARATE_1, value);
    }

    /**
     * 棚分割1(<code>LOC_SEPARATE_1</code>) から値を取得します。
     * @return LOC_SEPARATE_1
     */
    public String getLocSeparate1()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOC_SEPARATE_1, "")) ;
    }

    /**
     * 棚分割2(<code>LOC_SEPARATE_2</code>) に値をセットします。
     * @param value セットする値LOC_SEPARATE_2
     */
    public void setLocSeparate2(String value)  // @@GEN_V3@@
    {
        setValue(LOC_SEPARATE_2, value);
    }

    /**
     * 棚分割2(<code>LOC_SEPARATE_2</code>) から値を取得します。
     * @return LOC_SEPARATE_2
     */
    public String getLocSeparate2()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOC_SEPARATE_2, "")) ;
    }

    /**
     * 棚分割3(<code>LOC_SEPARATE_3</code>) に値をセットします。
     * @param value セットする値LOC_SEPARATE_3
     */
    public void setLocSeparate3(String value)  // @@GEN_V3@@
    {
        setValue(LOC_SEPARATE_3, value);
    }

    /**
     * 棚分割3(<code>LOC_SEPARATE_3</code>) から値を取得します。
     * @return LOC_SEPARATE_3
     */
    public String getLocSeparate3()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOC_SEPARATE_3, "")) ;
    }

    /**
     * 棚分割4(<code>LOC_SEPARATE_4</code>) に値をセットします。
     * @param value セットする値LOC_SEPARATE_4
     */
    public void setLocSeparate4(String value)  // @@GEN_V3@@
    {
        setValue(LOC_SEPARATE_4, value);
    }

    /**
     * 棚分割4(<code>LOC_SEPARATE_4</code>) から値を取得します。
     * @return LOC_SEPARATE_4
     */
    public String getLocSeparate4()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOC_SEPARATE_4, "")) ;
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
     * 基準日付(<code>USE_BY_DATE</code>) に値をセットします。
     * @param value セットする値USE_BY_DATE
     */
    public void setUseByDate(String value)  // @@GEN_V3@@
    {
        setValue(USE_BY_DATE, value);
    }

    /**
     * 基準日付(<code>USE_BY_DATE</code>) から値を取得します。
     * @return USE_BY_DATE
     */
    public String getUseByDate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USE_BY_DATE, "")) ;
    }

    /**
     * アイテム情報コメント(<code>ITEM_INFO</code>) に値をセットします。
     * @param value セットする値ITEM_INFO
     */
    public void setItemInfo(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_INFO, value);
    }

    /**
     * アイテム情報コメント(<code>ITEM_INFO</code>) から値を取得します。
     * @return ITEM_INFO
     */
    public String getItemInfo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_INFO, "")) ;
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
     * 実績数(<code>RESULT_QTY</code>) に値をセットします。
     * @param value セットする値RESULT_QTY
     */
    public void setResultQty(int value)  // @@GEN_V3@@
    {
        setValue(RESULT_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 実績数(<code>RESULT_QTY</code>) から値を取得します。
     * @return RESULT_QTY
     */
    public int getResultQty()  // @@GEN_V3@@
    {
        return getBigDecimal(RESULT_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>) に値をセットします。
     * @param value セットする値SHORTAGE_QTY
     */
    public void setShortageQty(int value)  // @@GEN_V3@@
    {
        setValue(SHORTAGE_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>) から値を取得します。
     * @return SHORTAGE_QTY
     */
    public int getShortageQty()  // @@GEN_V3@@
    {
        return getBigDecimal(SHORTAGE_QTY, BigDecimal.ZERO).intValue() ;
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
     * 作業秒数(<code>WORK_SECOND</code>) に値をセットします。
     * @param value セットする値WORK_SECOND
     */
    public void setWorkSecond(int value)  // @@GEN_V3@@
    {
        setValue(WORK_SECOND, HandlerUtil.toObject(value));
    }

    /**
     * 作業秒数(<code>WORK_SECOND</code>) から値を取得します。
     * @return WORK_SECOND
     */
    public int getWorkSecond()  // @@GEN_V3@@
    {
        return getBigDecimal(WORK_SECOND, BigDecimal.ZERO).intValue() ;
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
        return "$Id: PCTRetWorkInfo.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}

// $Id: ShortageInfo.java 5127 2009-10-13 12:20:06Z ota $
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
 * SHORTAGEINFOのエンティティクラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class ShortageInfo
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNSHORTAGEINFO" ;

    /*
     * テーブル名: DNSHORTAGEINFO
     * 予定一意キー :                  PLAN_UKEY           varchar2(10)
     * 取込単位キー :                  LOAD_UNIT_KEY       varchar2(24)
     * 出庫開始単位キー :              START_UNIT_KEY      varchar2(8)
     * 作業区分 :                      JOB_TYPE            varchar2(2)
     * 予定日 :                        PLAN_DAY            varchar2(8)
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 出荷先コード :                  CUSTOMER_CODE       varchar2(16)
     * 出荷伝票No. :                   SHIP_TICKET_NO      varchar2(16)
     * 出荷伝票行No. :                 SHIP_LINE_NO        number
     * 作業枝番 :                      BRANCH_NO           number
     * バッチNo. :                     BATCH_NO            varchar2(8)
     * オーダNo. :                     ORDER_NO            varchar2(16)
     * 予定エリアNo. :                 PLAN_AREA_NO        varchar2(4)
     * 予定棚No. :                     PLAN_LOCATION_NO    varchar2(11)
     * 商品コード :                    ITEM_CODE           varchar2(40)
     * 予定ロットNo. :                 PLAN_LOT_NO         varchar2(60)
     * 予定数 :                        PLAN_QTY            number
     * 今回予定数 :                    THIS_TIME_PLAN_QTY  number
     * 在庫数 :                        STOCK_QTY           number
     * 欠品数 :                        SHORTAGE_QTY        number
     * 補充数 :                        REPLENISHMENT_QTY   number
     * 引当済み数 :                    ALLOCATED_QTY       number
     * 引当パターンNo. :               ALLOCATE_NO         varchar2(3)
     * 補充区分 :                      REPLENISHMENT_FLAG  varchar2(1)
     * 欠品区分 :                      SHORTAGE_FLAG       varchar2(1)
     * 出庫開始日時 :                  RETRIEVAL_START_DATEdate
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (予定一意キー(<code>PLAN_UKEY</code>)) */
    public static final FieldName PLAN_UKEY = new FieldName(STORE_NAME, "PLAN_UKEY") ;

    /** フィールド定義 (取込単位キー(<code>LOAD_UNIT_KEY</code>)) */
    public static final FieldName LOAD_UNIT_KEY = new FieldName(STORE_NAME, "LOAD_UNIT_KEY") ;

    /** フィールド定義 (出庫開始単位キー(<code>START_UNIT_KEY</code>)) */
    public static final FieldName START_UNIT_KEY = new FieldName(STORE_NAME, "START_UNIT_KEY") ;

    /** フィールド定義 (作業区分(<code>JOB_TYPE</code>)) */
    public static final FieldName JOB_TYPE = new FieldName(STORE_NAME, "JOB_TYPE") ;

    /** フィールド定義 (予定日(<code>PLAN_DAY</code>)) */
    public static final FieldName PLAN_DAY = new FieldName(STORE_NAME, "PLAN_DAY") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (出荷先コード(<code>CUSTOMER_CODE</code>)) */
    public static final FieldName CUSTOMER_CODE = new FieldName(STORE_NAME, "CUSTOMER_CODE") ;

    /** フィールド定義 (出荷伝票No.(<code>SHIP_TICKET_NO</code>)) */
    public static final FieldName SHIP_TICKET_NO = new FieldName(STORE_NAME, "SHIP_TICKET_NO") ;

    /** フィールド定義 (出荷伝票行No.(<code>SHIP_LINE_NO</code>)) */
    public static final FieldName SHIP_LINE_NO = new FieldName(STORE_NAME, "SHIP_LINE_NO") ;

    /** フィールド定義 (作業枝番(<code>BRANCH_NO</code>)) */
    public static final FieldName BRANCH_NO = new FieldName(STORE_NAME, "BRANCH_NO") ;

    /** フィールド定義 (バッチNo.(<code>BATCH_NO</code>)) */
    public static final FieldName BATCH_NO = new FieldName(STORE_NAME, "BATCH_NO") ;

    /** フィールド定義 (オーダNo.(<code>ORDER_NO</code>)) */
    public static final FieldName ORDER_NO = new FieldName(STORE_NAME, "ORDER_NO") ;

    /** フィールド定義 (予定エリアNo.(<code>PLAN_AREA_NO</code>)) */
    public static final FieldName PLAN_AREA_NO = new FieldName(STORE_NAME, "PLAN_AREA_NO") ;

    /** フィールド定義 (予定棚No.(<code>PLAN_LOCATION_NO</code>)) */
    public static final FieldName PLAN_LOCATION_NO = new FieldName(STORE_NAME, "PLAN_LOCATION_NO") ;

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE") ;

    /** フィールド定義 (予定ロットNo.(<code>PLAN_LOT_NO</code>)) */
    public static final FieldName PLAN_LOT_NO = new FieldName(STORE_NAME, "PLAN_LOT_NO") ;

    /** フィールド定義 (予定数(<code>PLAN_QTY</code>)) */
    public static final FieldName PLAN_QTY = new FieldName(STORE_NAME, "PLAN_QTY") ;

    /** フィールド定義 (今回予定数(<code>THIS_TIME_PLAN_QTY</code>)) */
    public static final FieldName THIS_TIME_PLAN_QTY = new FieldName(STORE_NAME, "THIS_TIME_PLAN_QTY") ;

    /** フィールド定義 (在庫数(<code>STOCK_QTY</code>)) */
    public static final FieldName STOCK_QTY = new FieldName(STORE_NAME, "STOCK_QTY") ;

    /** フィールド定義 (欠品数(<code>SHORTAGE_QTY</code>)) */
    public static final FieldName SHORTAGE_QTY = new FieldName(STORE_NAME, "SHORTAGE_QTY") ;

    /** フィールド定義 (補充数(<code>REPLENISHMENT_QTY</code>)) */
    public static final FieldName REPLENISHMENT_QTY = new FieldName(STORE_NAME, "REPLENISHMENT_QTY") ;

    /** フィールド定義 (引当済み数(<code>ALLOCATED_QTY</code>)) */
    public static final FieldName ALLOCATED_QTY = new FieldName(STORE_NAME, "ALLOCATED_QTY") ;

    /** フィールド定義 (引当パターンNo.(<code>ALLOCATE_NO</code>)) */
    public static final FieldName ALLOCATE_NO = new FieldName(STORE_NAME, "ALLOCATE_NO") ;

    /** フィールド定義 (補充区分(<code>REPLENISHMENT_FLAG</code>)) */
    public static final FieldName REPLENISHMENT_FLAG = new FieldName(STORE_NAME, "REPLENISHMENT_FLAG") ;

    /** フィールド定義 (欠品区分(<code>SHORTAGE_FLAG</code>)) */
    public static final FieldName SHORTAGE_FLAG = new FieldName(STORE_NAME, "SHORTAGE_FLAG") ;

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
    public ShortageInfo()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

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
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>) に値をセットします。
     * @param value セットする値LOAD_UNIT_KEY
     */
    public void setLoadUnitKey(String value)  // @@GEN_V3@@
    {
        setValue(LOAD_UNIT_KEY, value);
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>) から値を取得します。
     * @return LOAD_UNIT_KEY
     */
    public String getLoadUnitKey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOAD_UNIT_KEY, "")) ;
    }

    /**
     * 出庫開始単位キー(<code>START_UNIT_KEY</code>) に値をセットします。
     * @param value セットする値START_UNIT_KEY
     */
    public void setStartUnitKey(String value)  // @@GEN_V3@@
    {
        setValue(START_UNIT_KEY, value);
    }

    /**
     * 出庫開始単位キー(<code>START_UNIT_KEY</code>) から値を取得します。
     * @return START_UNIT_KEY
     */
    public String getStartUnitKey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(START_UNIT_KEY, "")) ;
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
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>) に値をセットします。
     * @param value セットする値SHIP_TICKET_NO
     */
    public void setShipTicketNo(String value)  // @@GEN_V3@@
    {
        setValue(SHIP_TICKET_NO, value);
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>) から値を取得します。
     * @return SHIP_TICKET_NO
     */
    public String getShipTicketNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SHIP_TICKET_NO, "")) ;
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>) に値をセットします。
     * @param value セットする値SHIP_LINE_NO
     */
    public void setShipLineNo(int value)  // @@GEN_V3@@
    {
        setValue(SHIP_LINE_NO, HandlerUtil.toObject(value));
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>) から値を取得します。
     * @return SHIP_LINE_NO
     */
    public int getShipLineNo()  // @@GEN_V3@@
    {
        return getBigDecimal(SHIP_LINE_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>) に値をセットします。
     * @param value セットする値BRANCH_NO
     */
    public void setBranchNo(int value)  // @@GEN_V3@@
    {
        setValue(BRANCH_NO, HandlerUtil.toObject(value));
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>) から値を取得します。
     * @return BRANCH_NO
     */
    public int getBranchNo()  // @@GEN_V3@@
    {
        return getBigDecimal(BRANCH_NO, BigDecimal.ZERO).intValue() ;
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
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>) に値をセットします。
     * @param value セットする値PLAN_AREA_NO
     */
    public void setPlanAreaNo(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_AREA_NO, value);
    }

    /**
     * 予定エリアNo.(<code>PLAN_AREA_NO</code>) から値を取得します。
     * @return PLAN_AREA_NO
     */
    public String getPlanAreaNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_AREA_NO, "")) ;
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>) に値をセットします。
     * @param value セットする値PLAN_LOCATION_NO
     */
    public void setPlanLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_LOCATION_NO, value);
    }

    /**
     * 予定棚No.(<code>PLAN_LOCATION_NO</code>) から値を取得します。
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
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>) に値をセットします。
     * @param value セットする値PLAN_LOT_NO
     */
    public void setPlanLotNo(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_LOT_NO, value);
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>) から値を取得します。
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
     * 今回予定数(<code>THIS_TIME_PLAN_QTY</code>) に値をセットします。
     * @param value セットする値THIS_TIME_PLAN_QTY
     */
    public void setThisTimePlanQty(int value)  // @@GEN_V3@@
    {
        setValue(THIS_TIME_PLAN_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 今回予定数(<code>THIS_TIME_PLAN_QTY</code>) から値を取得します。
     * @return THIS_TIME_PLAN_QTY
     */
    public int getThisTimePlanQty()  // @@GEN_V3@@
    {
        return getBigDecimal(THIS_TIME_PLAN_QTY, BigDecimal.ZERO).intValue() ;
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
     * 補充数(<code>REPLENISHMENT_QTY</code>) に値をセットします。
     * @param value セットする値REPLENISHMENT_QTY
     */
    public void setReplenishmentQty(int value)  // @@GEN_V3@@
    {
        setValue(REPLENISHMENT_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 補充数(<code>REPLENISHMENT_QTY</code>) から値を取得します。
     * @return REPLENISHMENT_QTY
     */
    public int getReplenishmentQty()  // @@GEN_V3@@
    {
        return getBigDecimal(REPLENISHMENT_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 引当済み数(<code>ALLOCATED_QTY</code>) に値をセットします。
     * @param value セットする値ALLOCATED_QTY
     */
    public void setAllocatedQty(int value)  // @@GEN_V3@@
    {
        setValue(ALLOCATED_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 引当済み数(<code>ALLOCATED_QTY</code>) から値を取得します。
     * @return ALLOCATED_QTY
     */
    public int getAllocatedQty()  // @@GEN_V3@@
    {
        return getBigDecimal(ALLOCATED_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 引当パターンNo.(<code>ALLOCATE_NO</code>) に値をセットします。
     * @param value セットする値ALLOCATE_NO
     */
    public void setAllocateNo(String value)  // @@GEN_V3@@
    {
        setValue(ALLOCATE_NO, value);
    }

    /**
     * 引当パターンNo.(<code>ALLOCATE_NO</code>) から値を取得します。
     * @return ALLOCATE_NO
     */
    public String getAllocateNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ALLOCATE_NO, "")) ;
    }

    /**
     * 補充区分(<code>REPLENISHMENT_FLAG</code>) に値をセットします。
     * @param value セットする値REPLENISHMENT_FLAG
     */
    public void setReplenishmentFlag(String value)  // @@GEN_V3@@
    {
        setValue(REPLENISHMENT_FLAG, value);
    }

    /**
     * 補充区分(<code>REPLENISHMENT_FLAG</code>) から値を取得します。
     * @return REPLENISHMENT_FLAG
     */
    public String getReplenishmentFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REPLENISHMENT_FLAG, "")) ;
    }

    /**
     * 欠品区分(<code>SHORTAGE_FLAG</code>) に値をセットします。
     * @param value セットする値SHORTAGE_FLAG
     */
    public void setShortageFlag(String value)  // @@GEN_V3@@
    {
        setValue(SHORTAGE_FLAG, value);
    }

    /**
     * 欠品区分(<code>SHORTAGE_FLAG</code>) から値を取得します。
     * @return SHORTAGE_FLAG
     */
    public String getShortageFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SHORTAGE_FLAG, "")) ;
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
        return "$Id: ShortageInfo.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}

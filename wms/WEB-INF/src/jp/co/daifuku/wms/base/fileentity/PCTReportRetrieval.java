// $Id: PCTReportRetrieval.java 4129 2009-04-10 12:55:22Z okamura $
// $LastChangedRevision: 4129 $
package jp.co.daifuku.wms.base.fileentity;

/*
 * Copyright(c) 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.FieldDefine;
import jp.co.daifuku.wms.handler.field.StoreDefineHandler;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;
import jp.co.daifuku.wms.handler.util.HandlerUtil;


/**
 * PCTREPORTRETRIEVALのエンティティクラスです。
 *
 * @version $Revision: 4129 $, $Date: 2009-04-10 21:55:22 +0900 (金, 10 4 2009) $
 * @author  shimizu
 * @author  Last commit: $Author: okamura $
 */

public class PCTReportRetrieval
        extends AbstractEntity
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** Name of File */
    public static final String STORE_NAME = "PCTREPORTRETRIEVAL";

    /*
     * ファイル名: PCTREPORTRETRIEVAL
     * 取消区分 :                      CANCEL_FLAG         N(1)
     * 出庫予定日 :                    PLAN_DAY            N(8)
     * 荷主コード :                    CONSIGNOR_CODE      X(16)
     * 荷主名称 :                      CONSIGNOR_NAME      A(40)
     * 得意先コード :                  REGULAR_CUSTOMER_CODEX(16)
     * 得意先名称 :                    REGULAR_CUSTOMER_NAMEA(40)
     * 出荷先コード :                  CUSTOMER_CODE       X(16)
     * 出荷先名称 :                    CUSTOMER_NAME       A(40)
     * 出荷先分類 :                    CUSTOMER_CATEGORY   X(6)
     * 出荷伝票No. :                   SHIP_TICKET_NO      X(16)
     * 出荷伝票行No. :                 SHIP_LINE_NO        9(3)
     * 作業枝番 :                      BRANCH_NO           9(3)
     * バッチNo. :                     BATCH_NO            X(4)
     * バッチSeqNo. :                  BATCH_SEQ_NO        X(12)
     * 予定オーダーNo. :               PLAN_ORDER_NO       X(24)
     * オーダー情報コメント :          ORDER_INFO          A(20)
     * 通番 :                          THROUGH_NO          9(3)
     * オーダー内商品数 :              ORDER_ITEM_QTY      9(3)
     * オーダー通番 :                  ORDER_THROUGH_NO    9(3)
     * オーダー通番合計 :              ORDER_THROUGH_NO_CNT9(3)
     * 汎用フラグ :                    GENERAL_FLAG        X(1)
     * シュートNo. :                   SHOOT_NO            X(2)
     * 予定エリア :                    PLAN_AREA_NO        X(4)
     * 予定ゾーン :                    PLAN_ZONE_NO        X(4)
     * 作業ゾーン :                    WORK_ZONE_NO        X(4)
     * 予定棚 :                        PLAN_LOCATION_NO    X(8)
     * 商品コード :                    ITEM_CODE           X(16)
     * 商品名称 :                      ITEM_NAME           A(50)
     * ケース入数 :                    ENTERING_QTY        9(5)
     * ボール入数 :                    BUNDLE_ENTERING_QTY 9(5)
     * ロット入数 :                    LOT_ENTERING_QTY    9(5)
     * JANコード :                     JAN                 X(13)
     * ケースITF :                     ITF                 X(16)
     * ボールITF :                     BUNDLE_ITF          X(16)
     * 基準日付 :                      USE_BY_DATE         N(8)
     * アイテム情報コメント :          ITEM_INFO           A(20)
     * 予定ロットNo. :                 PLAN_LOT_NO         X(16)
     * 予定数 :                        PLAN_QTY            9(6)
     * 実績オーダーNo. :               RESULT_ORDER_NO     X(24)
     * 完了区分 :                      COMPLETE_FLAG       N(1)
     * 実績数 :                        RESULT_QTY          9(6)
     * 欠品数 :                        SHORTAGE_QTY        9(6)
     * 作業秒数 :                      WORK_SECOND         9(6)
     * ユーザID :                      USER_ID             X(20)
     * 端末No.、RFTNo. :               TERMINAL_NO         X(4)
     */

    /** フィールド定義 (取消区分(<code>CANCEL_FLAG</code>)) */
    public static final FieldName CANCEL_FLAG = new FieldName(STORE_NAME, "CANCEL_FLAG");

    /** フィールド定義 (出庫予定日(<code>PLAN_DAY</code>)) */
    public static final FieldName PLAN_DAY = new FieldName(STORE_NAME, "PLAN_DAY");

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE");

    /** フィールド定義 (荷主名称(<code>CONSIGNOR_NAME</code>)) */
    public static final FieldName CONSIGNOR_NAME = new FieldName(STORE_NAME, "CONSIGNOR_NAME");

    /** フィールド定義 (得意先コード(<code>REGULAR_CUSTOMER_CODE</code>)) */
    public static final FieldName REGULAR_CUSTOMER_CODE = new FieldName(STORE_NAME, "REGULAR_CUSTOMER_CODE");

    /** フィールド定義 (得意先名称(<code>REGULAR_CUSTOMER_NAME</code>)) */
    public static final FieldName REGULAR_CUSTOMER_NAME = new FieldName(STORE_NAME, "REGULAR_CUSTOMER_NAME");

    /** フィールド定義 (出荷先コード(<code>CUSTOMER_CODE</code>)) */
    public static final FieldName CUSTOMER_CODE = new FieldName(STORE_NAME, "CUSTOMER_CODE");

    /** フィールド定義 (出荷先名称(<code>CUSTOMER_NAME</code>)) */
    public static final FieldName CUSTOMER_NAME = new FieldName(STORE_NAME, "CUSTOMER_NAME");

    /** フィールド定義 (出荷先分類(<code>CUSTOMER_CATEGORY</code>)) */
    public static final FieldName CUSTOMER_CATEGORY = new FieldName(STORE_NAME, "CUSTOMER_CATEGORY");

    /** フィールド定義 (出荷伝票No.(<code>SHIP_TICKET_NO</code>)) */
    public static final FieldName SHIP_TICKET_NO = new FieldName(STORE_NAME, "SHIP_TICKET_NO");

    /** フィールド定義 (出荷伝票行No.(<code>SHIP_LINE_NO</code>)) */
    public static final FieldName SHIP_LINE_NO = new FieldName(STORE_NAME, "SHIP_LINE_NO");

    /** フィールド定義 (作業枝番(<code>BRANCH_NO</code>)) */
    public static final FieldName BRANCH_NO = new FieldName(STORE_NAME, "BRANCH_NO");

    /** フィールド定義 (バッチNo.(<code>BATCH_NO</code>)) */
    public static final FieldName BATCH_NO = new FieldName(STORE_NAME, "BATCH_NO");

    /** フィールド定義 (バッチSeqNo.(<code>BATCH_SEQ_NO</code>)) */
    public static final FieldName BATCH_SEQ_NO = new FieldName(STORE_NAME, "BATCH_SEQ_NO");

    /** フィールド定義 (予定オーダーNo.(<code>PLAN_ORDER_NO</code>)) */
    public static final FieldName PLAN_ORDER_NO = new FieldName(STORE_NAME, "PLAN_ORDER_NO");

    /** フィールド定義 (オーダー情報コメント(<code>ORDER_INFO</code>)) */
    public static final FieldName ORDER_INFO = new FieldName(STORE_NAME, "ORDER_INFO");

    /** フィールド定義 (通番(<code>THROUGH_NO</code>)) */
    public static final FieldName THROUGH_NO = new FieldName(STORE_NAME, "THROUGH_NO");

    /** フィールド定義 (オーダー内商品数(<code>ORDER_ITEM_QTY</code>)) */
    public static final FieldName ORDER_ITEM_QTY = new FieldName(STORE_NAME, "ORDER_ITEM_QTY");

    /** フィールド定義 (オーダー通番(<code>ORDER_THROUGH_NO</code>)) */
    public static final FieldName ORDER_THROUGH_NO = new FieldName(STORE_NAME, "ORDER_THROUGH_NO");

    /** フィールド定義 (オーダー通番合計(<code>ORDER_THROUGH_NO_CNT</code>)) */
    public static final FieldName ORDER_THROUGH_NO_CNT = new FieldName(STORE_NAME, "ORDER_THROUGH_NO_CNT");

    /** フィールド定義 (汎用フラグ(<code>GENERAL_FLAG</code>)) */
    public static final FieldName GENERAL_FLAG = new FieldName(STORE_NAME, "GENERAL_FLAG");

    /** フィールド定義 (シュートNo.(<code>SHOOT_NO</code>)) */
    public static final FieldName SHOOT_NO = new FieldName(STORE_NAME, "SHOOT_NO");

    /** フィールド定義 (予定エリア(<code>PLAN_AREA_NO</code>)) */
    public static final FieldName PLAN_AREA_NO = new FieldName(STORE_NAME, "PLAN_AREA_NO");

    /** フィールド定義 (予定ゾーン(<code>PLAN_ZONE_NO</code>)) */
    public static final FieldName PLAN_ZONE_NO = new FieldName(STORE_NAME, "PLAN_ZONE_NO");

    /** フィールド定義 (作業ゾーン(<code>WORK_ZONE_NO</code>)) */
    public static final FieldName WORK_ZONE_NO = new FieldName(STORE_NAME, "WORK_ZONE_NO");

    /** フィールド定義 (予定棚(<code>PLAN_LOCATION_NO</code>)) */
    public static final FieldName PLAN_LOCATION_NO = new FieldName(STORE_NAME, "PLAN_LOCATION_NO");

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE");

    /** フィールド定義 (商品名称(<code>ITEM_NAME</code>)) */
    public static final FieldName ITEM_NAME = new FieldName(STORE_NAME, "ITEM_NAME");

    /** フィールド定義 (ケース入数(<code>ENTERING_QTY</code>)) */
    public static final FieldName ENTERING_QTY = new FieldName(STORE_NAME, "ENTERING_QTY");

    /** フィールド定義 (ボール入数(<code>BUNDLE_ENTERING_QTY</code>)) */
    public static final FieldName BUNDLE_ENTERING_QTY = new FieldName(STORE_NAME, "BUNDLE_ENTERING_QTY");

    /** フィールド定義 (ロット入数(<code>LOT_ENTERING_QTY</code>)) */
    public static final FieldName LOT_ENTERING_QTY = new FieldName(STORE_NAME, "LOT_ENTERING_QTY");

    /** フィールド定義 (JANコード(<code>JAN</code>)) */
    public static final FieldName JAN = new FieldName(STORE_NAME, "JAN");

    /** フィールド定義 (ケースITF(<code>ITF</code>)) */
    public static final FieldName ITF = new FieldName(STORE_NAME, "ITF");

    /** フィールド定義 (ボールITF(<code>BUNDLE_ITF</code>)) */
    public static final FieldName BUNDLE_ITF = new FieldName(STORE_NAME, "BUNDLE_ITF");

    /** フィールド定義 (基準日付(<code>USE_BY_DATE</code>)) */
    public static final FieldName USE_BY_DATE = new FieldName(STORE_NAME, "USE_BY_DATE");

    /** フィールド定義 (アイテム情報コメント(<code>ITEM_INFO</code>)) */
    public static final FieldName ITEM_INFO = new FieldName(STORE_NAME, "ITEM_INFO");

    /** フィールド定義 (予定ロットNo.(<code>PLAN_LOT_NO</code>)) */
    public static final FieldName PLAN_LOT_NO = new FieldName(STORE_NAME, "PLAN_LOT_NO");

    /** フィールド定義 (予定数(<code>PLAN_QTY</code>)) */
    public static final FieldName PLAN_QTY = new FieldName(STORE_NAME, "PLAN_QTY");

    /** フィールド定義 (実績オーダーNo.(<code>RESULT_ORDER_NO</code>)) */
    public static final FieldName RESULT_ORDER_NO = new FieldName(STORE_NAME, "RESULT_ORDER_NO");

    /** フィールド定義 (完了区分(<code>COMPLETE_FLAG</code>)) */
    public static final FieldName COMPLETE_FLAG = new FieldName(STORE_NAME, "COMPLETE_FLAG");

    /** フィールド定義 (実績数(<code>RESULT_QTY</code>)) */
    public static final FieldName RESULT_QTY = new FieldName(STORE_NAME, "RESULT_QTY");

    /** フィールド定義 (欠品数(<code>SHORTAGE_QTY</code>)) */
    public static final FieldName SHORTAGE_QTY = new FieldName(STORE_NAME, "SHORTAGE_QTY");

    /** フィールド定義 (作業秒数(<code>WORK_SECOND</code>)) */
    public static final FieldName WORK_SECOND = new FieldName(STORE_NAME, "WORK_SECOND");

    /** フィールド定義 (ユーザID(<code>USER_ID</code>)) */
    public static final FieldName USER_ID = new FieldName(STORE_NAME, "USER_ID");

    /** フィールド定義 (端末No.、RFTNo.(<code>TERMINAL_NO</code>)) */
    public static final FieldName TERMINAL_NO = new FieldName(STORE_NAME, "TERMINAL_NO");


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar;
    /** Store meta data for this entity */
    public static final StoreMetaData $storeMetaData = StoreDefineHandler.createStoreMetaData(
            STORE_NAME,
            new File(HandlerSysDefines.DEFINE_DIR, "filestores.xml"),    //    %%StoreMetadata
            new File(HandlerSysDefines.DEFINE_DIR, "filefields.xml")    //    %%FieldMetadata
            );

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     *  フィールド名リストを準備してインスタンスを生成します。
     */
    public PCTReportRetrieval()
    {
        super();
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 取消区分(<code>CANCEL_FLAG</code>) に値をセットします。
     * @param value セットする値CANCEL_FLAG
     */
    public void setCancelFlag(String value)  // @@GEN_V3@@
    {
        setValue(CANCEL_FLAG, value);
    }

    /**
     * 取消区分(<code>CANCEL_FLAG</code>) から値を取得します。
     * @return    取消区分
     */
    public String getCancelFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CANCEL_FLAG, ""));
    }

    /**
     * 出庫予定日(<code>PLAN_DAY</code>) に値をセットします。
     * @param value セットする値PLAN_DAY
     */
    public void setPlanDay(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_DAY, value);
    }

    /**
     * 出庫予定日(<code>PLAN_DAY</code>) から値を取得します。
     * @return    出庫予定日
     */
    public String getPlanDay()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_DAY, ""));
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
     * @return    荷主コード
     */
    public String getConsignorCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONSIGNOR_CODE, ""));
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
     * @return    荷主名称
     */
    public String getConsignorName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONSIGNOR_NAME, ""));
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
     * @return    得意先コード
     */
    public String getRegularCustomerCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REGULAR_CUSTOMER_CODE, ""));
    }

    /**
     * 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>) に値をセットします。
     * @param value セットする値REGULAR_CUSTOMER_NAME
     */
    public void setRegularCustomerName(String value)  // @@GEN_V3@@
    {
        setValue(REGULAR_CUSTOMER_NAME, value);
    }

    /**
     * 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>) から値を取得します。
     * @return    得意先名称
     */
    public String getRegularCustomerName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REGULAR_CUSTOMER_NAME, ""));
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
     * @return    出荷先コード
     */
    public String getCustomerCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CUSTOMER_CODE, ""));
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
     * @return    出荷先名称
     */
    public String getCustomerName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CUSTOMER_NAME, ""));
    }

    /**
     * 出荷先分類(<code>CUSTOMER_CATEGORY</code>) に値をセットします。
     * @param value セットする値CUSTOMER_CATEGORY
     */
    public void setCustomerCategory(String value)  // @@GEN_V3@@
    {
        setValue(CUSTOMER_CATEGORY, value);
    }

    /**
     * 出荷先分類(<code>CUSTOMER_CATEGORY</code>) から値を取得します。
     * @return    出荷先分類
     */
    public String getCustomerCategory()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CUSTOMER_CATEGORY, ""));
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
     * @return    出荷伝票No.
     */
    public String getShipTicketNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SHIP_TICKET_NO, ""));
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
     * @return    出荷伝票行No.
     */
    public int getShipLineNo()  // @@GEN_V3@@
    {
        return getBigDecimal(SHIP_LINE_NO, BigDecimal.ZERO).intValue();
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
     * @return    作業枝番
     */
    public int getBranchNo()  // @@GEN_V3@@
    {
        return getBigDecimal(BRANCH_NO, BigDecimal.ZERO).intValue();
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
     * @return    バッチNo.
     */
    public String getBatchNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(BATCH_NO, ""));
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
     * @return    バッチSeqNo.
     */
    public String getBatchSeqNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(BATCH_SEQ_NO, ""));
    }

    /**
     * 予定オーダーNo.(<code>PLAN_ORDER_NO</code>) に値をセットします。
     * @param value セットする値PLAN_ORDER_NO
     */
    public void setPlanOrderNo(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_ORDER_NO, value);
    }

    /**
     * 予定オーダーNo.(<code>PLAN_ORDER_NO</code>) から値を取得します。
     * @return    予定オーダーNo.
     */
    public String getPlanOrderNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_ORDER_NO, ""));
    }

    /**
     * オーダー情報コメント(<code>ORDER_INFO</code>) に値をセットします。
     * @param value セットする値ORDER_INFO
     */
    public void setOrderInfo(String value)  // @@GEN_V3@@
    {
        setValue(ORDER_INFO, value);
    }

    /**
     * オーダー情報コメント(<code>ORDER_INFO</code>) から値を取得します。
     * @return    オーダー情報コメント
     */
    public String getOrderInfo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ORDER_INFO, ""));
    }

    /**
     * 通番(<code>THROUGH_NO</code>) に値をセットします。
     * @param value セットする値THROUGH_NO
     */
    public void setThroughNo(int value)  // @@GEN_V3@@
    {
        setValue(THROUGH_NO, HandlerUtil.toObject(value));
    }

    /**
     * 通番(<code>THROUGH_NO</code>) から値を取得します。
     * @return    通番
     */
    public int getThroughNo()  // @@GEN_V3@@
    {
        return getBigDecimal(THROUGH_NO, BigDecimal.ZERO).intValue();
    }

    /**
     * オーダー内商品数(<code>ORDER_ITEM_QTY</code>) に値をセットします。
     * @param value セットする値ORDER_ITEM_QTY
     */
    public void setOrderItemQty(int value)  // @@GEN_V3@@
    {
        setValue(ORDER_ITEM_QTY, HandlerUtil.toObject(value));
    }

    /**
     * オーダー内商品数(<code>ORDER_ITEM_QTY</code>) から値を取得します。
     * @return    オーダー内商品数
     */
    public int getOrderItemQty()  // @@GEN_V3@@
    {
        return getBigDecimal(ORDER_ITEM_QTY, BigDecimal.ZERO).intValue();
    }

    /**
     * オーダー通番(<code>ORDER_THROUGH_NO</code>) に値をセットします。
     * @param value セットする値ORDER_THROUGH_NO
     */
    public void setOrderThroughNo(int value)  // @@GEN_V3@@
    {
        setValue(ORDER_THROUGH_NO, HandlerUtil.toObject(value));
    }

    /**
     * オーダー通番(<code>ORDER_THROUGH_NO</code>) から値を取得します。
     * @return    オーダー通番
     */
    public int getOrderThroughNo()  // @@GEN_V3@@
    {
        return getBigDecimal(ORDER_THROUGH_NO, BigDecimal.ZERO).intValue();
    }

    /**
     * オーダー通番合計(<code>ORDER_THROUGH_NO_CNT</code>) に値をセットします。
     * @param value セットする値ORDER_THROUGH_NO_CNT
     */
    public void setOrderThroughNoCnt(int value)  // @@GEN_V3@@
    {
        setValue(ORDER_THROUGH_NO_CNT, HandlerUtil.toObject(value));
    }

    /**
     * オーダー通番合計(<code>ORDER_THROUGH_NO_CNT</code>) から値を取得します。
     * @return    オーダー通番合計
     */
    public int getOrderThroughNoCnt()  // @@GEN_V3@@
    {
        return getBigDecimal(ORDER_THROUGH_NO_CNT, BigDecimal.ZERO).intValue();
    }

    /**
     * 汎用フラグ(<code>GENERAL_FLAG</code>) に値をセットします。
     * @param value セットする値GENERAL_FLAG
     */
    public void setGeneralFlag(String value)  // @@GEN_V3@@
    {
        setValue(GENERAL_FLAG, value);
    }

    /**
     * 汎用フラグ(<code>GENERAL_FLAG</code>) から値を取得します。
     * @return    汎用フラグ
     */
    public String getGeneralFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(GENERAL_FLAG, ""));
    }

    /**
     * シュートNo.(<code>SHOOT_NO</code>) に値をセットします。
     * @param value セットする値SHOOT_NO
     */
    public void setShootNo(String value)  // @@GEN_V3@@
    {
        setValue(SHOOT_NO, value);
    }

    /**
     * シュートNo.(<code>SHOOT_NO</code>) から値を取得します。
     * @return    シュートNo.
     */
    public String getShootNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SHOOT_NO, ""));
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
     * @return    予定エリア
     */
    public String getPlanAreaNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_AREA_NO, ""));
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
     * @return    予定ゾーン
     */
    public String getPlanZoneNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_ZONE_NO, ""));
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
     * @return    作業ゾーン
     */
    public String getWorkZoneNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WORK_ZONE_NO, ""));
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
     * @return    予定棚
     */
    public String getPlanLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_LOCATION_NO, ""));
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
     * @return    商品コード
     */
    public String getItemCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_CODE, ""));
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
     * @return    商品名称
     */
    public String getItemName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_NAME, ""));
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
     * @return    ケース入数
     */
    public int getEnteringQty()  // @@GEN_V3@@
    {
        return getBigDecimal(ENTERING_QTY, BigDecimal.ZERO).intValue();
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
     * @return    ボール入数
     */
    public int getBundleEnteringQty()  // @@GEN_V3@@
    {
        return getBigDecimal(BUNDLE_ENTERING_QTY, BigDecimal.ZERO).intValue();
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>) に値をセットします。
     * @param value セットする値LOT_ENTERING_QTY
     */
    public void setLotEnteringQty(int value)  // @@GEN_V3@@
    {
        setValue(LOT_ENTERING_QTY, HandlerUtil.toObject(value));
    }

    /**
     * ロット入数(<code>LOT_ENTERING_QTY</code>) から値を取得します。
     * @return    ロット入数
     */
    public int getLotEnteringQty()  // @@GEN_V3@@
    {
        return getBigDecimal(LOT_ENTERING_QTY, BigDecimal.ZERO).intValue();
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
     * @return    JANコード
     */
    public String getJan()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(JAN, ""));
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
     * @return    ケースITF
     */
    public String getItf()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITF, ""));
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
     * @return    ボールITF
     */
    public String getBundleItf()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(BUNDLE_ITF, ""));
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
     * @return    基準日付
     */
    public String getUseByDate()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USE_BY_DATE, ""));
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
     * @return    アイテム情報コメント
     */
    public String getItemInfo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_INFO, ""));
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
     * @return    予定ロットNo.
     */
    public String getPlanLotNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_LOT_NO, ""));
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
     * @return    予定数
     */
    public int getPlanQty()  // @@GEN_V3@@
    {
        return getBigDecimal(PLAN_QTY, BigDecimal.ZERO).intValue();
    }

    /**
     * 実績オーダーNo.(<code>RESULT_ORDER_NO</code>) に値をセットします。
     * @param value セットする値RESULT_ORDER_NO
     */
    public void setResultOrderNo(String value)  // @@GEN_V3@@
    {
        setValue(RESULT_ORDER_NO, value);
    }

    /**
     * 実績オーダーNo.(<code>RESULT_ORDER_NO</code>) から値を取得します。
     * @return    実績オーダーNo.
     */
    public String getResultOrderNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RESULT_ORDER_NO, ""));
    }

    /**
     * 完了区分(<code>COMPLETE_FLAG</code>) に値をセットします。
     * @param value セットする値COMPLETE_FLAG
     */
    public void setCompleteFlag(String value)  // @@GEN_V3@@
    {
        setValue(COMPLETE_FLAG, value);
    }

    /**
     * 完了区分(<code>COMPLETE_FLAG</code>) から値を取得します。
     * @return    完了区分
     */
    public String getCompleteFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(COMPLETE_FLAG, ""));
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
     * @return    実績数
     */
    public int getResultQty()  // @@GEN_V3@@
    {
        return getBigDecimal(RESULT_QTY, BigDecimal.ZERO).intValue();
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
     * @return    欠品数
     */
    public int getShortageQty()  // @@GEN_V3@@
    {
        return getBigDecimal(SHORTAGE_QTY, BigDecimal.ZERO).intValue();
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
     * @return    作業秒数
     */
    public int getWorkSecond()  // @@GEN_V3@@
    {
        return getBigDecimal(WORK_SECOND, BigDecimal.ZERO).intValue();
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
     * @return    ユーザID
     */
    public String getUserId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USER_ID, ""));
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値TERMINAL_NO
     */
    public void setTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_NO, value);
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>) から値を取得します。
     * @return    端末No.、RFTNo.
     */
    public String getTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_NO, ""));
    }


    /**
     *  ストアメタデータを返します。
     *  @return このエンティティ用ストアメタデータ
     */
    public StoreMetaData getStoreMetaData()
    {
        return $storeMetaData;
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
     *  このクラスのリビジョンを返します。
     *  @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PCTReportRetrieval.java 4129 2009-04-10 12:55:22Z okamura $";
    }
}

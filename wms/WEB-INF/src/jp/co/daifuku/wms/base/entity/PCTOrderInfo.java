// $Id: PCTOrderInfo.java 3213 2009-03-02 06:59:20Z arai $
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
 * PCTORDERINFOのエンティティクラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class PCTOrderInfo
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNPCTORDERINFO" ;

    /*
     * テーブル名: DNPCTORDERINFO
     * 作業開始日時 :                  WORK_STARTTIME      date
     * 作業終了日時 :                  WORK_ENDTIME        date
     * 状態フラグ :                    STATUS_FLAG         varchar2(1)
     * ユーザID :                      USER_ID             varchar2(20)
     * 端末No、RFTNo :                 TERMINAL_NO         varchar2(3)
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 荷主名称 :                      CONSIGNOR_NAME      varchar2(40)
     * エリアNo :                      AREA_NO             varchar2(4)
     * エリア名称 :                    AREA_NAME           varchar2(40)
     * バッチNo. :                     BATCH_NO            varchar2(4)
     * バッチSeqNo. :                  BATCH_SEQ_NO        varchar2(12)
     * 得意先コード :                  REGULAR_CUSTOMER_CODEvarchar2(16)
     * 得意先名称 :                    REGULAR_CUSTOMER_NAMEvarchar2(40)
     * 出荷先コード :                  CUSTOMER_CODE       varchar2(16)
     * 出荷先名称 :                    CUSTOMER_NAME       varchar2(40)
     * 出荷先優先度 :                  CUSTOMER_PRIORITY   number
     * 予定オーダNo. :                 PLAN_ORDER_NO       varchar2(24)
     * 実績オーダNo. :                 RESULT_ORDER_NO     varchar2(24)
     * 設定単位キー :                  SETTING_UNIT_KEY    varchar2(8)
     * 作業数量 :                      WORK_QTY            number
     * 作業数量（バラ数） :            PIECE_QTY           number
     * 作業回数（明細数） :            WORK_CNT            number
     * 集品箱数 :                      BOX_CNT             number
     * 作業時間（秒） :                WORK_TIME           number
     * 実作業時間（秒） :              REAL_WORK_TIME      number
     * ミススキャン数 :                MISS_SCAN_CNT       number
     * 最小作業ゾーン :                MIN_WORK_ZONE_NO    varchar2(4)
     * 最小ゾーン :                    MIN_ZONE_NO         varchar2(4)
     * 最小棚No. :                     MIN_LOCATION_NO     varchar2(8)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (作業開始日時(<code>WORK_STARTTIME</code>)) */
    public static final FieldName WORK_STARTTIME = new FieldName(STORE_NAME, "WORK_STARTTIME") ;

    /** フィールド定義 (作業終了日時(<code>WORK_ENDTIME</code>)) */
    public static final FieldName WORK_ENDTIME = new FieldName(STORE_NAME, "WORK_ENDTIME") ;

    /** フィールド定義 (状態フラグ(<code>STATUS_FLAG</code>)) */
    public static final FieldName STATUS_FLAG = new FieldName(STORE_NAME, "STATUS_FLAG") ;

    /** フィールド定義 (ユーザID(<code>USER_ID</code>)) */
    public static final FieldName USER_ID = new FieldName(STORE_NAME, "USER_ID") ;

    /** フィールド定義 (端末No、RFTNo(<code>TERMINAL_NO</code>)) */
    public static final FieldName TERMINAL_NO = new FieldName(STORE_NAME, "TERMINAL_NO") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (荷主名称(<code>CONSIGNOR_NAME</code>)) */
    public static final FieldName CONSIGNOR_NAME = new FieldName(STORE_NAME, "CONSIGNOR_NAME") ;

    /** フィールド定義 (エリアNo(<code>AREA_NO</code>)) */
    public static final FieldName AREA_NO = new FieldName(STORE_NAME, "AREA_NO") ;

    /** フィールド定義 (エリア名称(<code>AREA_NAME</code>)) */
    public static final FieldName AREA_NAME = new FieldName(STORE_NAME, "AREA_NAME") ;

    /** フィールド定義 (バッチNo.(<code>BATCH_NO</code>)) */
    public static final FieldName BATCH_NO = new FieldName(STORE_NAME, "BATCH_NO") ;

    /** フィールド定義 (バッチSeqNo.(<code>BATCH_SEQ_NO</code>)) */
    public static final FieldName BATCH_SEQ_NO = new FieldName(STORE_NAME, "BATCH_SEQ_NO") ;

    /** フィールド定義 (得意先コード(<code>REGULAR_CUSTOMER_CODE</code>)) */
    public static final FieldName REGULAR_CUSTOMER_CODE = new FieldName(STORE_NAME, "REGULAR_CUSTOMER_CODE") ;

    /** フィールド定義 (得意先名称(<code>REGULAR_CUSTOMER_NAME</code>)) */
    public static final FieldName REGULAR_CUSTOMER_NAME = new FieldName(STORE_NAME, "REGULAR_CUSTOMER_NAME") ;

    /** フィールド定義 (出荷先コード(<code>CUSTOMER_CODE</code>)) */
    public static final FieldName CUSTOMER_CODE = new FieldName(STORE_NAME, "CUSTOMER_CODE") ;

    /** フィールド定義 (出荷先名称(<code>CUSTOMER_NAME</code>)) */
    public static final FieldName CUSTOMER_NAME = new FieldName(STORE_NAME, "CUSTOMER_NAME") ;

    /** フィールド定義 (出荷先優先度(<code>CUSTOMER_PRIORITY</code>)) */
    public static final FieldName CUSTOMER_PRIORITY = new FieldName(STORE_NAME, "CUSTOMER_PRIORITY") ;

    /** フィールド定義 (予定オーダNo.(<code>PLAN_ORDER_NO</code>)) */
    public static final FieldName PLAN_ORDER_NO = new FieldName(STORE_NAME, "PLAN_ORDER_NO") ;

    /** フィールド定義 (実績オーダNo.(<code>RESULT_ORDER_NO</code>)) */
    public static final FieldName RESULT_ORDER_NO = new FieldName(STORE_NAME, "RESULT_ORDER_NO") ;

    /** フィールド定義 (設定単位キー(<code>SETTING_UNIT_KEY</code>)) */
    public static final FieldName SETTING_UNIT_KEY = new FieldName(STORE_NAME, "SETTING_UNIT_KEY") ;

    /** フィールド定義 (作業数量(<code>WORK_QTY</code>)) */
    public static final FieldName WORK_QTY = new FieldName(STORE_NAME, "WORK_QTY") ;

    /** フィールド定義 (作業数量（バラ数）(<code>PIECE_QTY</code>)) */
    public static final FieldName PIECE_QTY = new FieldName(STORE_NAME, "PIECE_QTY") ;

    /** フィールド定義 (作業回数（明細数）(<code>WORK_CNT</code>)) */
    public static final FieldName WORK_CNT = new FieldName(STORE_NAME, "WORK_CNT") ;

    /** フィールド定義 (集品箱数(<code>BOX_CNT</code>)) */
    public static final FieldName BOX_CNT = new FieldName(STORE_NAME, "BOX_CNT") ;

    /** フィールド定義 (作業時間（秒）(<code>WORK_TIME</code>)) */
    public static final FieldName WORK_TIME = new FieldName(STORE_NAME, "WORK_TIME") ;

    /** フィールド定義 (実作業時間（秒）(<code>REAL_WORK_TIME</code>)) */
    public static final FieldName REAL_WORK_TIME = new FieldName(STORE_NAME, "REAL_WORK_TIME") ;

    /** フィールド定義 (ミススキャン数(<code>MISS_SCAN_CNT</code>)) */
    public static final FieldName MISS_SCAN_CNT = new FieldName(STORE_NAME, "MISS_SCAN_CNT") ;

    /** フィールド定義 (最小作業ゾーン(<code>MIN_WORK_ZONE_NO</code>)) */
    public static final FieldName MIN_WORK_ZONE_NO = new FieldName(STORE_NAME, "MIN_WORK_ZONE_NO") ;

    /** フィールド定義 (最小ゾーン(<code>MIN_ZONE_NO</code>)) */
    public static final FieldName MIN_ZONE_NO = new FieldName(STORE_NAME, "MIN_ZONE_NO") ;

    /** フィールド定義 (最小棚No.(<code>MIN_LOCATION_NO</code>)) */
    public static final FieldName MIN_LOCATION_NO = new FieldName(STORE_NAME, "MIN_LOCATION_NO") ;

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
    public PCTOrderInfo()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 作業開始日時(<code>WORK_STARTTIME</code>) に値をセットします。
     * @param value セットする値WORK_STARTTIME
     */
    public void setWorkStarttime(Date value)  // @@GEN_V3@@
    {
        setValue(WORK_STARTTIME, value);
    }

    /**
     * 作業開始日時(<code>WORK_STARTTIME</code>) から値を取得します。
     * @return WORK_STARTTIME
     */
    public Date getWorkStarttime()  // @@GEN_V3@@
    {
        return (Date)getValue(WORK_STARTTIME, null) ;
    }

    /**
     * 作業終了日時(<code>WORK_ENDTIME</code>) に値をセットします。
     * @param value セットする値WORK_ENDTIME
     */
    public void setWorkEndtime(Date value)  // @@GEN_V3@@
    {
        setValue(WORK_ENDTIME, value);
    }

    /**
     * 作業終了日時(<code>WORK_ENDTIME</code>) から値を取得します。
     * @return WORK_ENDTIME
     */
    public Date getWorkEndtime()  // @@GEN_V3@@
    {
        return (Date)getValue(WORK_ENDTIME, null) ;
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
     * エリアNo(<code>AREA_NO</code>) に値をセットします。
     * @param value セットする値AREA_NO
     */
    public void setAreaNo(String value)  // @@GEN_V3@@
    {
        setValue(AREA_NO, value);
    }

    /**
     * エリアNo(<code>AREA_NO</code>) から値を取得します。
     * @return AREA_NO
     */
    public String getAreaNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(AREA_NO, "")) ;
    }

    /**
     * エリア名称(<code>AREA_NAME</code>) に値をセットします。
     * @param value セットする値AREA_NAME
     */
    public void setAreaName(String value)  // @@GEN_V3@@
    {
        setValue(AREA_NAME, value);
    }

    /**
     * エリア名称(<code>AREA_NAME</code>) から値を取得します。
     * @return AREA_NAME
     */
    public String getAreaName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(AREA_NAME, "")) ;
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
     * 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>) に値をセットします。
     * @param value セットする値REGULAR_CUSTOMER_NAME
     */
    public void setRegularCustomerName(String value)  // @@GEN_V3@@
    {
        setValue(REGULAR_CUSTOMER_NAME, value);
    }

    /**
     * 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>) から値を取得します。
     * @return REGULAR_CUSTOMER_NAME
     */
    public String getRegularCustomerName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REGULAR_CUSTOMER_NAME, "")) ;
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
     * 出荷先優先度(<code>CUSTOMER_PRIORITY</code>) に値をセットします。
     * @param value セットする値CUSTOMER_PRIORITY
     */
    public void setCustomerPriority(int value)  // @@GEN_V3@@
    {
        setValue(CUSTOMER_PRIORITY, HandlerUtil.toObject(value));
    }

    /**
     * 出荷先優先度(<code>CUSTOMER_PRIORITY</code>) から値を取得します。
     * @return CUSTOMER_PRIORITY
     */
    public int getCustomerPriority()  // @@GEN_V3@@
    {
        return getBigDecimal(CUSTOMER_PRIORITY, BigDecimal.ZERO).intValue() ;
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
     * 作業数量(<code>WORK_QTY</code>) に値をセットします。
     * @param value セットする値WORK_QTY
     */
    public void setWorkQty(int value)  // @@GEN_V3@@
    {
        setValue(WORK_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 作業数量(<code>WORK_QTY</code>) から値を取得します。
     * @return WORK_QTY
     */
    public int getWorkQty()  // @@GEN_V3@@
    {
        return getBigDecimal(WORK_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 作業数量（バラ数）(<code>PIECE_QTY</code>) に値をセットします。
     * @param value セットする値PIECE_QTY
     */
    public void setPieceQty(int value)  // @@GEN_V3@@
    {
        setValue(PIECE_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 作業数量（バラ数）(<code>PIECE_QTY</code>) から値を取得します。
     * @return PIECE_QTY
     */
    public int getPieceQty()  // @@GEN_V3@@
    {
        return getBigDecimal(PIECE_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>) に値をセットします。
     * @param value セットする値WORK_CNT
     */
    public void setWorkCnt(int value)  // @@GEN_V3@@
    {
        setValue(WORK_CNT, HandlerUtil.toObject(value));
    }

    /**
     * 作業回数（明細数）(<code>WORK_CNT</code>) から値を取得します。
     * @return WORK_CNT
     */
    public int getWorkCnt()  // @@GEN_V3@@
    {
        return getBigDecimal(WORK_CNT, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 集品箱数(<code>BOX_CNT</code>) に値をセットします。
     * @param value セットする値BOX_CNT
     */
    public void setBoxCnt(int value)  // @@GEN_V3@@
    {
        setValue(BOX_CNT, HandlerUtil.toObject(value));
    }

    /**
     * 集品箱数(<code>BOX_CNT</code>) から値を取得します。
     * @return BOX_CNT
     */
    public int getBoxCnt()  // @@GEN_V3@@
    {
        return getBigDecimal(BOX_CNT, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>) に値をセットします。
     * @param value セットする値WORK_TIME
     */
    public void setWorkTime(int value)  // @@GEN_V3@@
    {
        setValue(WORK_TIME, HandlerUtil.toObject(value));
    }

    /**
     * 作業時間（秒）(<code>WORK_TIME</code>) から値を取得します。
     * @return WORK_TIME
     */
    public int getWorkTime()  // @@GEN_V3@@
    {
        return getBigDecimal(WORK_TIME, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>) に値をセットします。
     * @param value セットする値REAL_WORK_TIME
     */
    public void setRealWorkTime(int value)  // @@GEN_V3@@
    {
        setValue(REAL_WORK_TIME, HandlerUtil.toObject(value));
    }

    /**
     * 実作業時間（秒）(<code>REAL_WORK_TIME</code>) から値を取得します。
     * @return REAL_WORK_TIME
     */
    public int getRealWorkTime()  // @@GEN_V3@@
    {
        return getBigDecimal(REAL_WORK_TIME, BigDecimal.ZERO).intValue() ;
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>) に値をセットします。
     * @param value セットする値MISS_SCAN_CNT
     */
    public void setMissScanCnt(int value)  // @@GEN_V3@@
    {
        setValue(MISS_SCAN_CNT, HandlerUtil.toObject(value));
    }

    /**
     * ミススキャン数(<code>MISS_SCAN_CNT</code>) から値を取得します。
     * @return MISS_SCAN_CNT
     */
    public int getMissScanCnt()  // @@GEN_V3@@
    {
        return getBigDecimal(MISS_SCAN_CNT, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 最小作業ゾーン(<code>MIN_WORK_ZONE_NO</code>) に値をセットします。
     * @param value セットする値MIN_WORK_ZONE_NO
     */
    public void setMinWorkZoneNo(String value)  // @@GEN_V3@@
    {
        setValue(MIN_WORK_ZONE_NO, value);
    }

    /**
     * 最小作業ゾーン(<code>MIN_WORK_ZONE_NO</code>) から値を取得します。
     * @return MIN_WORK_ZONE_NO
     */
    public String getMinWorkZoneNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MIN_WORK_ZONE_NO, "")) ;
    }

    /**
     * 最小ゾーン(<code>MIN_ZONE_NO</code>) に値をセットします。
     * @param value セットする値MIN_ZONE_NO
     */
    public void setMinZoneNo(String value)  // @@GEN_V3@@
    {
        setValue(MIN_ZONE_NO, value);
    }

    /**
     * 最小ゾーン(<code>MIN_ZONE_NO</code>) から値を取得します。
     * @return MIN_ZONE_NO
     */
    public String getMinZoneNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MIN_ZONE_NO, "")) ;
    }

    /**
     * 最小棚No.(<code>MIN_LOCATION_NO</code>) に値をセットします。
     * @param value セットする値MIN_LOCATION_NO
     */
    public void setMinLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(MIN_LOCATION_NO, value);
    }

    /**
     * 最小棚No.(<code>MIN_LOCATION_NO</code>) から値を取得します。
     * @return MIN_LOCATION_NO
     */
    public String getMinLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MIN_LOCATION_NO, "")) ;
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
        return "$Id: PCTOrderInfo.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}

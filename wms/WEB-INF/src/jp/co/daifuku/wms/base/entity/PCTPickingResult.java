// $Id: PCTPickingResult.java 5741 2009-11-12 13:29:46Z kumano $
// $LastChangedRevision: 5741 $
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
 * PCTPICKINGRESULTのエンティティクラスです。
 *
 * @version $Revision: 5741 $, $Date: 2009-11-12 22:29:46 +0900 (木, 12 11 2009) $
 * @author  ss
 * @author  Last commit: $Author: kumano $
 */

public class PCTPickingResult
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNPCTPICKINGRESULT" ;

    /*
     * テーブル名: DNPCTPICKINGRESULT
     * ユーザID :                      USER_ID             varchar2(20)
     * ユーザ名称 :                    USER_NAME           varchar2(40)
     * ランク :                        RANK                varchar2(1)
     * 端末No. :                       TERMINAL_NO         varchar2(3)
     * 作業日 :                        WORK_DAY            varchar2(8)
     * 曜日 :                          DAY_OF_WEEK         number
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 荷主名称 :                      CONSIGNOR_NAME      varchar2(40)
     * エリアNo. :                     AREA_NO             varchar2(4)
     * エリア名称 :                    AREA_NAME           varchar2(40)
     * バッチNo. :                     BATCH_NO            varchar2(4)
     * 作業開始日時 :                  START_TIME          date
     * 作業終了日時 :                  END_TIME            date
     * 稼働時間 :                      OPERATE_TIME        number
     * 作業数量 :                      WORK_QTY            number
     * 作業数量(バラ) :                PIECE_QTY           number
     * 作業回数(明細数) :              WORK_CNT            number
     * 作業回数(オーダー数) :          ORDER_CNT           number
     * 作業回数(予定オーダー数) :      PLAN_ORDER_CNT      number
     * 集品箱数 :                      BOX_CNT             number
     * 作業時間 :                      WORK_TIME           number
     * 実作業時間 :                    REAL_WORK_TIME      number
     * ミススキャン数 :                MISS_SCAN_CNT       number
     * バッテリ交換時間 :              BATTERY_CHANGE_TIME number
     * 休憩時間 :                      BREAK_TIME          number
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (ユーザID(<code>USER_ID</code>)) */
    public static final FieldName USER_ID = new FieldName(STORE_NAME, "USER_ID") ;

    /** フィールド定義 (ユーザ名称(<code>USER_NAME</code>)) */
    public static final FieldName USER_NAME = new FieldName(STORE_NAME, "USER_NAME") ;

    /** フィールド定義 (ランク(<code>RANK</code>)) */
    public static final FieldName RANK = new FieldName(STORE_NAME, "RANK") ;

    /** フィールド定義 (端末No.(<code>TERMINAL_NO</code>)) */
    public static final FieldName TERMINAL_NO = new FieldName(STORE_NAME, "TERMINAL_NO") ;

    /** フィールド定義 (作業日(<code>WORK_DAY</code>)) */
    public static final FieldName WORK_DAY = new FieldName(STORE_NAME, "WORK_DAY") ;

    /** フィールド定義 (曜日(<code>DAY_OF_WEEK</code>)) */
    public static final FieldName DAY_OF_WEEK = new FieldName(STORE_NAME, "DAY_OF_WEEK") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (荷主名称(<code>CONSIGNOR_NAME</code>)) */
    public static final FieldName CONSIGNOR_NAME = new FieldName(STORE_NAME, "CONSIGNOR_NAME") ;

    /** フィールド定義 (エリアNo.(<code>AREA_NO</code>)) */
    public static final FieldName AREA_NO = new FieldName(STORE_NAME, "AREA_NO") ;

    /** フィールド定義 (エリア名称(<code>AREA_NAME</code>)) */
    public static final FieldName AREA_NAME = new FieldName(STORE_NAME, "AREA_NAME") ;

    /** フィールド定義 (バッチNo.(<code>BATCH_NO</code>)) */
    public static final FieldName BATCH_NO = new FieldName(STORE_NAME, "BATCH_NO") ;

    /** フィールド定義 (作業開始日時(<code>START_TIME</code>)) */
    public static final FieldName START_TIME = new FieldName(STORE_NAME, "START_TIME") ;

    /** フィールド定義 (作業終了日時(<code>END_TIME</code>)) */
    public static final FieldName END_TIME = new FieldName(STORE_NAME, "END_TIME") ;

    /** フィールド定義 (稼働時間(<code>OPERATE_TIME</code>)) */
    public static final FieldName OPERATE_TIME = new FieldName(STORE_NAME, "OPERATE_TIME") ;

    /** フィールド定義 (作業数量(<code>WORK_QTY</code>)) */
    public static final FieldName WORK_QTY = new FieldName(STORE_NAME, "WORK_QTY") ;

    /** フィールド定義 (作業数量(バラ)(<code>PIECE_QTY</code>)) */
    public static final FieldName PIECE_QTY = new FieldName(STORE_NAME, "PIECE_QTY") ;

    /** フィールド定義 (作業回数(明細数)(<code>WORK_CNT</code>)) */
    public static final FieldName WORK_CNT = new FieldName(STORE_NAME, "WORK_CNT") ;

    /** フィールド定義 (作業回数(オーダー数)(<code>ORDER_CNT</code>)) */
    public static final FieldName ORDER_CNT = new FieldName(STORE_NAME, "ORDER_CNT") ;

    /** フィールド定義 (作業回数(予定オーダー数)(<code>PLAN_ORDER_CNT</code>)) */
    public static final FieldName PLAN_ORDER_CNT = new FieldName(STORE_NAME, "PLAN_ORDER_CNT") ;

    /** フィールド定義 (集品箱数(<code>BOX_CNT</code>)) */
    public static final FieldName BOX_CNT = new FieldName(STORE_NAME, "BOX_CNT") ;

    /** フィールド定義 (作業時間(<code>WORK_TIME</code>)) */
    public static final FieldName WORK_TIME = new FieldName(STORE_NAME, "WORK_TIME") ;

    /** フィールド定義 (実作業時間(<code>REAL_WORK_TIME</code>)) */
    public static final FieldName REAL_WORK_TIME = new FieldName(STORE_NAME, "REAL_WORK_TIME") ;

    /** フィールド定義 (ミススキャン数(<code>MISS_SCAN_CNT</code>)) */
    public static final FieldName MISS_SCAN_CNT = new FieldName(STORE_NAME, "MISS_SCAN_CNT") ;

    /** フィールド定義 (バッテリ交換時間(<code>BATTERY_CHANGE_TIME</code>)) */
    public static final FieldName BATTERY_CHANGE_TIME = new FieldName(STORE_NAME, "BATTERY_CHANGE_TIME") ;

    /** フィールド定義 (休憩時間(<code>BREAK_TIME</code>)) */
    public static final FieldName BREAK_TIME = new FieldName(STORE_NAME, "BREAK_TIME") ;

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
    public PCTPickingResult()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

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
     * ランク(<code>RANK</code>) に値をセットします。
     * @param value セットする値RANK
     */
    public void setRank(String value)  // @@GEN_V3@@
    {
        setValue(RANK, value);
    }

    /**
     * ランク(<code>RANK</code>) から値を取得します。
     * @return RANK
     */
    public String getRank()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RANK, "")) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値TERMINAL_NO
     */
    public void setTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_NO, value);
    }

    /**
     * 端末No.(<code>TERMINAL_NO</code>) から値を取得します。
     * @return TERMINAL_NO
     */
    public String getTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_NO, "")) ;
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
     * 曜日(<code>DAY_OF_WEEK</code>) に値をセットします。
     * @param value セットする値DAY_OF_WEEK
     */
    public void setDayOfWeek(int value)  // @@GEN_V3@@
    {
        setValue(DAY_OF_WEEK, HandlerUtil.toObject(value));
    }

    /**
     * 曜日(<code>DAY_OF_WEEK</code>) から値を取得します。
     * @return DAY_OF_WEEK
     */
    public int getDayOfWeek()  // @@GEN_V3@@
    {
        return getBigDecimal(DAY_OF_WEEK, BigDecimal.ZERO).intValue() ;
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
     * エリアNo.(<code>AREA_NO</code>) に値をセットします。
     * @param value セットする値AREA_NO
     */
    public void setAreaNo(String value)  // @@GEN_V3@@
    {
        setValue(AREA_NO, value);
    }

    /**
     * エリアNo.(<code>AREA_NO</code>) から値を取得します。
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
     * 作業開始日時(<code>START_TIME</code>) に値をセットします。
     * @param value セットする値START_TIME
     */
    public void setStartTime(Date value)  // @@GEN_V3@@
    {
        setValue(START_TIME, value);
    }

    /**
     * 作業開始日時(<code>START_TIME</code>) から値を取得します。
     * @return START_TIME
     */
    public Date getStartTime()  // @@GEN_V3@@
    {
        return (Date)getValue(START_TIME, null) ;
    }

    /**
     * 作業終了日時(<code>END_TIME</code>) に値をセットします。
     * @param value セットする値END_TIME
     */
    public void setEndTime(Date value)  // @@GEN_V3@@
    {
        setValue(END_TIME, value);
    }

    /**
     * 作業終了日時(<code>END_TIME</code>) から値を取得します。
     * @return END_TIME
     */
    public Date getEndTime()  // @@GEN_V3@@
    {
        return (Date)getValue(END_TIME, null) ;
    }

    /**
     * 稼働時間(<code>OPERATE_TIME</code>) に値をセットします。
     * @param value セットする値OPERATE_TIME
     */
    public void setOperateTime(int value)  // @@GEN_V3@@
    {
        setValue(OPERATE_TIME, HandlerUtil.toObject(value));
    }

    /**
     * 稼働時間(<code>OPERATE_TIME</code>) から値を取得します。
     * @return OPERATE_TIME
     */
    public int getOperateTime()  // @@GEN_V3@@
    {
        return getBigDecimal(OPERATE_TIME, BigDecimal.ZERO).intValue() ;
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
     * 作業数量(バラ)(<code>PIECE_QTY</code>) に値をセットします。
     * @param value セットする値PIECE_QTY
     */
    public void setPieceQty(int value)  // @@GEN_V3@@
    {
        setValue(PIECE_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 作業数量(バラ)(<code>PIECE_QTY</code>) から値を取得します。
     * @return PIECE_QTY
     */
    public int getPieceQty()  // @@GEN_V3@@
    {
        return getBigDecimal(PIECE_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 作業回数(明細数)(<code>WORK_CNT</code>) に値をセットします。
     * @param value セットする値WORK_CNT
     */
    public void setWorkCnt(int value)  // @@GEN_V3@@
    {
        setValue(WORK_CNT, HandlerUtil.toObject(value));
    }

    /**
     * 作業回数(明細数)(<code>WORK_CNT</code>) から値を取得します。
     * @return WORK_CNT
     */
    public int getWorkCnt()  // @@GEN_V3@@
    {
        return getBigDecimal(WORK_CNT, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 作業回数(オーダー数)(<code>ORDER_CNT</code>) に値をセットします。
     * @param value セットする値ORDER_CNT
     */
    public void setOrderCnt(int value)  // @@GEN_V3@@
    {
        setValue(ORDER_CNT, HandlerUtil.toObject(value));
    }

    /**
     * 作業回数(オーダー数)(<code>ORDER_CNT</code>) から値を取得します。
     * @return ORDER_CNT
     */
    public int getOrderCnt()  // @@GEN_V3@@
    {
        return getBigDecimal(ORDER_CNT, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 作業回数(予定オーダー数)(<code>PLAN_ORDER_CNT</code>) に値をセットします。
     * @param value セットする値PLAN_ORDER_CNT
     */
    public void setPlanOrderCnt(int value)  // @@GEN_V3@@
    {
        setValue(PLAN_ORDER_CNT, HandlerUtil.toObject(value));
    }

    /**
     * 作業回数(予定オーダー数)(<code>PLAN_ORDER_CNT</code>) から値を取得します。
     * @return PLAN_ORDER_CNT
     */
    public int getPlanOrderCnt()  // @@GEN_V3@@
    {
        return getBigDecimal(PLAN_ORDER_CNT, BigDecimal.ZERO).intValue() ;
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
     * 作業時間(<code>WORK_TIME</code>) に値をセットします。
     * @param value セットする値WORK_TIME
     */
    public void setWorkTime(int value)  // @@GEN_V3@@
    {
        setValue(WORK_TIME, HandlerUtil.toObject(value));
    }

    /**
     * 作業時間(<code>WORK_TIME</code>) から値を取得します。
     * @return WORK_TIME
     */
    public int getWorkTime()  // @@GEN_V3@@
    {
        return getBigDecimal(WORK_TIME, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 実作業時間(<code>REAL_WORK_TIME</code>) に値をセットします。
     * @param value セットする値REAL_WORK_TIME
     */
    public void setRealWorkTime(int value)  // @@GEN_V3@@
    {
        setValue(REAL_WORK_TIME, HandlerUtil.toObject(value));
    }

    /**
     * 実作業時間(<code>REAL_WORK_TIME</code>) から値を取得します。
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
     * バッテリ交換時間(<code>BATTERY_CHANGE_TIME</code>) に値をセットします。
     * @param value セットする値BATTERY_CHANGE_TIME
     */
    public void setBatteryChangeTime(int value)  // @@GEN_V3@@
    {
        setValue(BATTERY_CHANGE_TIME, HandlerUtil.toObject(value));
    }

    /**
     * バッテリ交換時間(<code>BATTERY_CHANGE_TIME</code>) から値を取得します。
     * @return BATTERY_CHANGE_TIME
     */
    public int getBatteryChangeTime()  // @@GEN_V3@@
    {
        return getBigDecimal(BATTERY_CHANGE_TIME, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 休憩時間(<code>BREAK_TIME</code>) に値をセットします。
     * @param value セットする値BREAK_TIME
     */
    public void setBreakTime(int value)  // @@GEN_V3@@
    {
        setValue(BREAK_TIME, HandlerUtil.toObject(value));
    }

    /**
     * 休憩時間(<code>BREAK_TIME</code>) から値を取得します。
     * @return BREAK_TIME
     */
    public int getBreakTime()  // @@GEN_V3@@
    {
        return getBigDecimal(BREAK_TIME, BigDecimal.ZERO).intValue() ;
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
        return "$Id: PCTPickingResult.java 5741 2009-11-12 13:29:46Z kumano $" ;
    }
}

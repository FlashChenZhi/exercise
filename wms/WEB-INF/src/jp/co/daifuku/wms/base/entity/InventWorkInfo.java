// $Id: InventWorkInfo.java 5127 2009-10-13 12:20:06Z ota $
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
 * INVENTWORKINFOのエンティティクラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class InventWorkInfo
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNINVENTWORKINFO" ;

    /*
     * テーブル名: DNINVENTWORKINFO
     * 作業No. :                       JOB_NO              varchar2(8)
     * スケジュールNo. :               SCHEDULE_NO         varchar2(9)
     * 設定単位キー :                  SETTING_UNIT_KEY    varchar2(8)
     * 状態フラグ :                    STATUS_FLAG         varchar2(1)
     * 元状態フラグ :                  PREVIOUS_STATUS     varchar2(1)
     * エリアNo. :                     AREA_NO             varchar2(4)
     * 棚No. :                         LOCATION_NO         varchar2(11)
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 商品コード :                    ITEM_CODE           varchar2(40)
     * ロットNo. :                     LOT_NO              varchar2(60)
     * 在庫数 :                        STOCK_QTY           number
     * 出庫可能数 :                    ALLOCATION_QTY      number
     * 棚卸結果数 :                    RESULT_STOCK_QTY    number
     * 棚卸ユーザID :                  USER_ID             varchar2(20)
     * 元棚卸ユーザID :                PRE_USER_ID         varchar2(20)
     * 棚卸端末No.、RFTNo. :           TERMINAL_NO         varchar2(4)
     * 元棚卸端末No.、元RFTNo. :       PRE_TERMINAL_NO     varchar2(4)
     * 棚卸確定ユーザID :              CONFIRM_USER_ID     varchar2(20)
     * 棚卸確定日 :                    CONFIRM_WORK_DAY    varchar2(8)
     * 棚卸確定日時 :                  CONFIRM_WORK_DATE   date
     * 作業秒数 :                      WORK_SECOND         number
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (作業No.(<code>JOB_NO</code>)) */
    public static final FieldName JOB_NO = new FieldName(STORE_NAME, "JOB_NO") ;

    /** フィールド定義 (スケジュールNo.(<code>SCHEDULE_NO</code>)) */
    public static final FieldName SCHEDULE_NO = new FieldName(STORE_NAME, "SCHEDULE_NO") ;

    /** フィールド定義 (設定単位キー(<code>SETTING_UNIT_KEY</code>)) */
    public static final FieldName SETTING_UNIT_KEY = new FieldName(STORE_NAME, "SETTING_UNIT_KEY") ;

    /** フィールド定義 (状態フラグ(<code>STATUS_FLAG</code>)) */
    public static final FieldName STATUS_FLAG = new FieldName(STORE_NAME, "STATUS_FLAG") ;

    /** フィールド定義 (元状態フラグ(<code>PREVIOUS_STATUS</code>)) */
    public static final FieldName PREVIOUS_STATUS = new FieldName(STORE_NAME, "PREVIOUS_STATUS") ;

    /** フィールド定義 (エリアNo.(<code>AREA_NO</code>)) */
    public static final FieldName AREA_NO = new FieldName(STORE_NAME, "AREA_NO") ;

    /** フィールド定義 (棚No.(<code>LOCATION_NO</code>)) */
    public static final FieldName LOCATION_NO = new FieldName(STORE_NAME, "LOCATION_NO") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE") ;

    /** フィールド定義 (ロットNo.(<code>LOT_NO</code>)) */
    public static final FieldName LOT_NO = new FieldName(STORE_NAME, "LOT_NO") ;

    /** フィールド定義 (在庫数(<code>STOCK_QTY</code>)) */
    public static final FieldName STOCK_QTY = new FieldName(STORE_NAME, "STOCK_QTY") ;

    /** フィールド定義 (出庫可能数(<code>ALLOCATION_QTY</code>)) */
    public static final FieldName ALLOCATION_QTY = new FieldName(STORE_NAME, "ALLOCATION_QTY") ;

    /** フィールド定義 (棚卸結果数(<code>RESULT_STOCK_QTY</code>)) */
    public static final FieldName RESULT_STOCK_QTY = new FieldName(STORE_NAME, "RESULT_STOCK_QTY") ;

    /** フィールド定義 (棚卸ユーザID(<code>USER_ID</code>)) */
    public static final FieldName USER_ID = new FieldName(STORE_NAME, "USER_ID") ;

    /** フィールド定義 (元棚卸ユーザID(<code>PRE_USER_ID</code>)) */
    public static final FieldName PRE_USER_ID = new FieldName(STORE_NAME, "PRE_USER_ID") ;

    /** フィールド定義 (棚卸端末No.、RFTNo.(<code>TERMINAL_NO</code>)) */
    public static final FieldName TERMINAL_NO = new FieldName(STORE_NAME, "TERMINAL_NO") ;

    /** フィールド定義 (元棚卸端末No.、元RFTNo.(<code>PRE_TERMINAL_NO</code>)) */
    public static final FieldName PRE_TERMINAL_NO = new FieldName(STORE_NAME, "PRE_TERMINAL_NO") ;

    /** フィールド定義 (棚卸確定ユーザID(<code>CONFIRM_USER_ID</code>)) */
    public static final FieldName CONFIRM_USER_ID = new FieldName(STORE_NAME, "CONFIRM_USER_ID") ;

    /** フィールド定義 (棚卸確定日(<code>CONFIRM_WORK_DAY</code>)) */
    public static final FieldName CONFIRM_WORK_DAY = new FieldName(STORE_NAME, "CONFIRM_WORK_DAY") ;

    /** フィールド定義 (棚卸確定日時(<code>CONFIRM_WORK_DATE</code>)) */
    public static final FieldName CONFIRM_WORK_DATE = new FieldName(STORE_NAME, "CONFIRM_WORK_DATE") ;

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
    public InventWorkInfo()
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
     * 元状態フラグ(<code>PREVIOUS_STATUS</code>) に値をセットします。
     * @param value セットする値PREVIOUS_STATUS
     */
    public void setPreviousStatus(String value)  // @@GEN_V3@@
    {
        setValue(PREVIOUS_STATUS, value);
    }

    /**
     * 元状態フラグ(<code>PREVIOUS_STATUS</code>) から値を取得します。
     * @return PREVIOUS_STATUS
     */
    public String getPreviousStatus()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PREVIOUS_STATUS, "")) ;
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
     * 棚No.(<code>LOCATION_NO</code>) に値をセットします。
     * @param value セットする値LOCATION_NO
     */
    public void setLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(LOCATION_NO, value);
    }

    /**
     * 棚No.(<code>LOCATION_NO</code>) から値を取得します。
     * @return LOCATION_NO
     */
    public String getLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_NO, "")) ;
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
     * 棚卸結果数(<code>RESULT_STOCK_QTY</code>) に値をセットします。
     * @param value セットする値RESULT_STOCK_QTY
     */
    public void setResultStockQty(int value)  // @@GEN_V3@@
    {
        setValue(RESULT_STOCK_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 棚卸結果数(<code>RESULT_STOCK_QTY</code>) から値を取得します。
     * @return RESULT_STOCK_QTY
     */
    public int getResultStockQty()  // @@GEN_V3@@
    {
        return getBigDecimal(RESULT_STOCK_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 棚卸ユーザID(<code>USER_ID</code>) に値をセットします。
     * @param value セットする値USER_ID
     */
    public void setUserId(String value)  // @@GEN_V3@@
    {
        setValue(USER_ID, value);
    }

    /**
     * 棚卸ユーザID(<code>USER_ID</code>) から値を取得します。
     * @return USER_ID
     */
    public String getUserId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USER_ID, "")) ;
    }

    /**
     * 元棚卸ユーザID(<code>PRE_USER_ID</code>) に値をセットします。
     * @param value セットする値PRE_USER_ID
     */
    public void setPreUserId(String value)  // @@GEN_V3@@
    {
        setValue(PRE_USER_ID, value);
    }

    /**
     * 元棚卸ユーザID(<code>PRE_USER_ID</code>) から値を取得します。
     * @return PRE_USER_ID
     */
    public String getPreUserId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PRE_USER_ID, "")) ;
    }

    /**
     * 棚卸端末No.、RFTNo.(<code>TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値TERMINAL_NO
     */
    public void setTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_NO, value);
    }

    /**
     * 棚卸端末No.、RFTNo.(<code>TERMINAL_NO</code>) から値を取得します。
     * @return TERMINAL_NO
     */
    public String getTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_NO, "")) ;
    }

    /**
     * 元棚卸端末No.、元RFTNo.(<code>PRE_TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値PRE_TERMINAL_NO
     */
    public void setPreTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(PRE_TERMINAL_NO, value);
    }

    /**
     * 元棚卸端末No.、元RFTNo.(<code>PRE_TERMINAL_NO</code>) から値を取得します。
     * @return PRE_TERMINAL_NO
     */
    public String getPreTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PRE_TERMINAL_NO, "")) ;
    }

    /**
     * 棚卸確定ユーザID(<code>CONFIRM_USER_ID</code>) に値をセットします。
     * @param value セットする値CONFIRM_USER_ID
     */
    public void setConfirmUserId(String value)  // @@GEN_V3@@
    {
        setValue(CONFIRM_USER_ID, value);
    }

    /**
     * 棚卸確定ユーザID(<code>CONFIRM_USER_ID</code>) から値を取得します。
     * @return CONFIRM_USER_ID
     */
    public String getConfirmUserId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONFIRM_USER_ID, "")) ;
    }

    /**
     * 棚卸確定日(<code>CONFIRM_WORK_DAY</code>) に値をセットします。
     * @param value セットする値CONFIRM_WORK_DAY
     */
    public void setConfirmWorkDay(String value)  // @@GEN_V3@@
    {
        setValue(CONFIRM_WORK_DAY, value);
    }

    /**
     * 棚卸確定日(<code>CONFIRM_WORK_DAY</code>) から値を取得します。
     * @return CONFIRM_WORK_DAY
     */
    public String getConfirmWorkDay()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONFIRM_WORK_DAY, "")) ;
    }

    /**
     * 棚卸確定日時(<code>CONFIRM_WORK_DATE</code>) に値をセットします。
     * @param value セットする値CONFIRM_WORK_DATE
     */
    public void setConfirmWorkDate(Date value)  // @@GEN_V3@@
    {
        setValue(CONFIRM_WORK_DATE, value);
    }

    /**
     * 棚卸確定日時(<code>CONFIRM_WORK_DATE</code>) から値を取得します。
     * @return CONFIRM_WORK_DATE
     */
    public Date getConfirmWorkDate()  // @@GEN_V3@@
    {
        return (Date)getValue(CONFIRM_WORK_DATE, null) ;
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
        return "$Id: InventWorkInfo.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}

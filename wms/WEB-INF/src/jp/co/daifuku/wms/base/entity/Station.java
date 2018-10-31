// $Id: Station.java 5296 2009-10-28 05:23:30Z ota $
// $LastChangedRevision: 5296 $
package jp.co.daifuku.wms.base.entity;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.util.Date;

import jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator;
import jp.co.daifuku.wms.handler.db.AbstractDBEntity;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreDefineHandler;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * STATIONのエンティティクラスです。
 *
 * @version $Revision: 5296 $, $Date: 2009-10-28 14:23:30 +0900 (水, 28 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class Station
        extends AbstractDBEntity
        implements SystemDefine
{
    /**
     * 機器切離しの許容範囲。この台数を超えた機器の切離しが発生した場合、StatusをNGにします。
     */
    public static final int STATION_NG_JUDGMENT = 0;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMSTATION" ;

    /*
     * テーブル名: DMSTATION
     * ステーションNo. :               STATION_NO          varchar2(16)
     * 最大出庫指示可能数 :            MAX_PALLET_QTY      number
     * 最大搬送指示可能数 :            MAX_INSTRUCTION     number
     * 送信可能区分 :                  SENDABLE            varchar2(1)
     * 状態 :                          STATUS              varchar2(1)
     * コントローラーNo. :             CONTROLLER_NO       varchar2(3)
     * ステーション種別 :              STATION_TYPE        varchar2(1)
     * 設定種別 :                      SETTING_TYPE        varchar2(1)
     * 作業場種別 :                    WORKPLACE_TYPE      varchar2(1)
     * 作業表示運用 :                  OPERATION_DISPLAY   varchar2(1)
     * ステーション名称 :              STATION_NAME        varchar2(60)
     * 中断中フラグ :                  SUSPEND             varchar2(1)
     * 到着報告 :                      ARRIVAL             varchar2(1)
     * 荷姿チェック :                  LOAD_SIZE           varchar2(1)
     * 払出し区分 :                    REMOVE              varchar2(1)
     * 在庫確認中フラグ :              INVENTORY_CHECK_FLAGvarchar2(1)
     * 再入庫作業有無 :                RESTORING_OPERATION varchar2(1)
     * 再入庫搬送指示送信有無 :        RESTORING_INSTRUCTIONvarchar2(1)
     * 棚再決定区分 :                  LOCATION_SEARCH_FLAGvarchar2(1)
     * 倉庫ステーションNo. :           WH_STATION_NO       varchar2(16)
     * 親ステーションNo. :             PARENT_STATION_NO   varchar2(16)
     * アイルステーションNo. :         AISLE_STATION_NO    varchar2(16)
     * 次搬送ステーションNo. :         NEXT_STATION_NO     varchar2(16)
     * 最終使用ステーションNo. :       LAST_USED_STATION_NOvarchar2(16)
     * リジェクトステーションNo. :     REJECT_STATION_NO   varchar2(16)
     * モード切替種別 :                MODE_TYPE           varchar2(1)
     * 現在作業モード :                CURRENT_MODE        varchar2(1)
     * モード切替要求区分 :            MODE_REQUEST        varchar2(1)
     * モード切替要求日時 :            MODE_REQUEST_DATE   date
     * クラス名 :                      CLASS_NAME          varchar2(128)
     */
    /** フィールド定義 (ステーションNo.(<code>STATION_NO</code>)) */
    public static final FieldName STATION_NO = new FieldName(STORE_NAME, "STATION_NO") ;

    /** フィールド定義 (最大出庫指示可能数(<code>MAX_PALLET_QTY</code>)) */
    public static final FieldName MAX_PALLET_QTY = new FieldName(STORE_NAME, "MAX_PALLET_QTY") ;

    /** フィールド定義 (最大搬送指示可能数(<code>MAX_INSTRUCTION</code>)) */
    public static final FieldName MAX_INSTRUCTION = new FieldName(STORE_NAME, "MAX_INSTRUCTION") ;

    /** フィールド定義 (送信可能区分(<code>SENDABLE</code>)) */
    public static final FieldName SENDABLE = new FieldName(STORE_NAME, "SENDABLE") ;

    /** フィールド定義 (状態(<code>STATUS</code>)) */
    public static final FieldName STATUS = new FieldName(STORE_NAME, "STATUS") ;

    /** フィールド定義 (コントローラーNo.(<code>CONTROLLER_NO</code>)) */
    public static final FieldName CONTROLLER_NO = new FieldName(STORE_NAME, "CONTROLLER_NO") ;

    /** フィールド定義 (ステーション種別(<code>STATION_TYPE</code>)) */
    public static final FieldName STATION_TYPE = new FieldName(STORE_NAME, "STATION_TYPE") ;

    /** フィールド定義 (設定種別(<code>SETTING_TYPE</code>)) */
    public static final FieldName SETTING_TYPE = new FieldName(STORE_NAME, "SETTING_TYPE") ;

    /** フィールド定義 (作業場種別(<code>WORKPLACE_TYPE</code>)) */
    public static final FieldName WORKPLACE_TYPE = new FieldName(STORE_NAME, "WORKPLACE_TYPE") ;

    /** フィールド定義 (作業表示運用(<code>OPERATION_DISPLAY</code>)) */
    public static final FieldName OPERATION_DISPLAY = new FieldName(STORE_NAME, "OPERATION_DISPLAY") ;

    /** フィールド定義 (ステーション名称(<code>STATION_NAME</code>)) */
    public static final FieldName STATION_NAME = new FieldName(STORE_NAME, "STATION_NAME") ;

    /** フィールド定義 (中断中フラグ(<code>SUSPEND</code>)) */
    public static final FieldName SUSPEND = new FieldName(STORE_NAME, "SUSPEND") ;

    /** フィールド定義 (到着報告(<code>ARRIVAL</code>)) */
    public static final FieldName ARRIVAL = new FieldName(STORE_NAME, "ARRIVAL") ;

    /** フィールド定義 (荷姿チェック(<code>LOAD_SIZE</code>)) */
    public static final FieldName LOAD_SIZE = new FieldName(STORE_NAME, "LOAD_SIZE") ;

    /** フィールド定義 (払出し区分(<code>REMOVE</code>)) */
    public static final FieldName REMOVE = new FieldName(STORE_NAME, "REMOVE") ;

    /** フィールド定義 (在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>)) */
    public static final FieldName INVENTORY_CHECK_FLAG = new FieldName(STORE_NAME, "INVENTORY_CHECK_FLAG") ;

    /** フィールド定義 (再入庫作業有無(<code>RESTORING_OPERATION</code>)) */
    public static final FieldName RESTORING_OPERATION = new FieldName(STORE_NAME, "RESTORING_OPERATION") ;

    /** フィールド定義 (再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>)) */
    public static final FieldName RESTORING_INSTRUCTION = new FieldName(STORE_NAME, "RESTORING_INSTRUCTION") ;

    /** フィールド定義 (棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>)) */
    public static final FieldName LOCATION_SEARCH_FLAG = new FieldName(STORE_NAME, "LOCATION_SEARCH_FLAG") ;

    /** フィールド定義 (倉庫ステーションNo.(<code>WH_STATION_NO</code>)) */
    public static final FieldName WH_STATION_NO = new FieldName(STORE_NAME, "WH_STATION_NO") ;

    /** フィールド定義 (親ステーションNo.(<code>PARENT_STATION_NO</code>)) */
    public static final FieldName PARENT_STATION_NO = new FieldName(STORE_NAME, "PARENT_STATION_NO") ;

    /** フィールド定義 (アイルステーションNo.(<code>AISLE_STATION_NO</code>)) */
    public static final FieldName AISLE_STATION_NO = new FieldName(STORE_NAME, "AISLE_STATION_NO") ;

    /** フィールド定義 (次搬送ステーションNo.(<code>NEXT_STATION_NO</code>)) */
    public static final FieldName NEXT_STATION_NO = new FieldName(STORE_NAME, "NEXT_STATION_NO") ;

    /** フィールド定義 (最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>)) */
    public static final FieldName LAST_USED_STATION_NO = new FieldName(STORE_NAME, "LAST_USED_STATION_NO") ;

    /** フィールド定義 (リジェクトステーションNo.(<code>REJECT_STATION_NO</code>)) */
    public static final FieldName REJECT_STATION_NO = new FieldName(STORE_NAME, "REJECT_STATION_NO") ;

    /** フィールド定義 (モード切替種別(<code>MODE_TYPE</code>)) */
    public static final FieldName MODE_TYPE = new FieldName(STORE_NAME, "MODE_TYPE") ;

    /** フィールド定義 (現在作業モード(<code>CURRENT_MODE</code>)) */
    public static final FieldName CURRENT_MODE = new FieldName(STORE_NAME, "CURRENT_MODE") ;

    /** フィールド定義 (モード切替要求区分(<code>MODE_REQUEST</code>)) */
    public static final FieldName MODE_REQUEST = new FieldName(STORE_NAME, "MODE_REQUEST") ;

    /** フィールド定義 (モード切替要求日時(<code>MODE_REQUEST_DATE</code>)) */
    public static final FieldName MODE_REQUEST_DATE = new FieldName(STORE_NAME, "MODE_REQUEST_DATE") ;

    /** フィールド定義 (クラス名(<code>CLASS_NAME</code>)) */
    public static final FieldName CLASS_NAME = new FieldName(STORE_NAME, "CLASS_NAME") ;


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
    public Station()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

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
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>) に値をセットします。
     * @param value セットする値MAX_PALLET_QTY
     */
    public void setMaxPalletQty(int value)  // @@GEN_V3@@
    {
        setValue(MAX_PALLET_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 最大出庫指示可能数(<code>MAX_PALLET_QTY</code>) から値を取得します。
     * @return MAX_PALLET_QTY
     */
    public int getMaxPalletQty()  // @@GEN_V3@@
    {
        return getBigDecimal(MAX_PALLET_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>) に値をセットします。
     * @param value セットする値MAX_INSTRUCTION
     */
    public void setMaxInstruction(int value)  // @@GEN_V3@@
    {
        setValue(MAX_INSTRUCTION, HandlerUtil.toObject(value));
    }

    /**
     * 最大搬送指示可能数(<code>MAX_INSTRUCTION</code>) から値を取得します。
     * @return MAX_INSTRUCTION
     */
    public int getMaxInstruction()  // @@GEN_V3@@
    {
        return getBigDecimal(MAX_INSTRUCTION, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>) に値をセットします。
     * @param value セットする値SENDABLE
     */
    public void setSendable(String value)  // @@GEN_V3@@
    {
        setValue(SENDABLE, value);
    }

    /**
     * 送信可能区分(<code>SENDABLE</code>) から値を取得します。
     * @return SENDABLE
     */
    public String getSendable()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SENDABLE, "")) ;
    }

    /**
     * 状態(<code>STATUS</code>) に値をセットします。
     * @param value セットする値STATUS
     */
    public void setStatus(String value)  // @@GEN_V3@@
    {
        setValue(STATUS, value);
    }

    /**
     * 状態(<code>STATUS</code>) から値を取得します。
     * @return STATUS
     */
    public String getStatus()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATUS, "")) ;
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>) に値をセットします。
     * @param value セットする値CONTROLLER_NO
     */
    public void setControllerNo(String value)  // @@GEN_V3@@
    {
        setValue(CONTROLLER_NO, value);
    }

    /**
     * コントローラーNo.(<code>CONTROLLER_NO</code>) から値を取得します。
     * @return CONTROLLER_NO
     */
    public String getControllerNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONTROLLER_NO, "")) ;
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>) に値をセットします。
     * @param value セットする値STATION_TYPE
     */
    public void setStationType(String value)  // @@GEN_V3@@
    {
        setValue(STATION_TYPE, value);
    }

    /**
     * ステーション種別(<code>STATION_TYPE</code>) から値を取得します。
     * @return STATION_TYPE
     */
    public String getStationType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATION_TYPE, "")) ;
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>) に値をセットします。
     * @param value セットする値SETTING_TYPE
     */
    public void setSettingType(String value)  // @@GEN_V3@@
    {
        setValue(SETTING_TYPE, value);
    }

    /**
     * 設定種別(<code>SETTING_TYPE</code>) から値を取得します。
     * @return SETTING_TYPE
     */
    public String getSettingType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SETTING_TYPE, "")) ;
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>) に値をセットします。
     * @param value セットする値WORKPLACE_TYPE
     */
    public void setWorkplaceType(String value)  // @@GEN_V3@@
    {
        setValue(WORKPLACE_TYPE, value);
    }

    /**
     * 作業場種別(<code>WORKPLACE_TYPE</code>) から値を取得します。
     * @return WORKPLACE_TYPE
     */
    public String getWorkplaceType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WORKPLACE_TYPE, "")) ;
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>) に値をセットします。
     * @param value セットする値OPERATION_DISPLAY
     */
    public void setOperationDisplay(String value)  // @@GEN_V3@@
    {
        setValue(OPERATION_DISPLAY, value);
    }

    /**
     * 作業表示運用(<code>OPERATION_DISPLAY</code>) から値を取得します。
     * @return OPERATION_DISPLAY
     */
    public String getOperationDisplay()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(OPERATION_DISPLAY, "")) ;
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>) に値をセットします。
     * @param value セットする値STATION_NAME
     */
    public void setStationName(String value)  // @@GEN_V3@@
    {
        setValue(STATION_NAME, value);
    }

    /**
     * ステーション名称(<code>STATION_NAME</code>) から値を取得します。
     * @return STATION_NAME
     */
    public String getStationName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATION_NAME, "")) ;
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>) に値をセットします。
     * @param value セットする値SUSPEND
     */
    public void setSuspend(String value)  // @@GEN_V3@@
    {
        setValue(SUSPEND, value);
    }

    /**
     * 中断中フラグ(<code>SUSPEND</code>) から値を取得します。
     * @return SUSPEND
     */
    public String getSuspend()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SUSPEND, "")) ;
    }

    /**
     * 到着報告(<code>ARRIVAL</code>) に値をセットします。
     * @param value セットする値ARRIVAL
     */
    public void setArrival(String value)  // @@GEN_V3@@
    {
        setValue(ARRIVAL, value);
    }

    /**
     * 到着報告(<code>ARRIVAL</code>) から値を取得します。
     * @return ARRIVAL
     */
    public String getArrival()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ARRIVAL, "")) ;
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>) に値をセットします。
     * @param value セットする値LOAD_SIZE
     */
    public void setLoadSize(String value)  // @@GEN_V3@@
    {
        setValue(LOAD_SIZE, value);
    }

    /**
     * 荷姿チェック(<code>LOAD_SIZE</code>) から値を取得します。
     * @return LOAD_SIZE
     */
    public String getLoadSize()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOAD_SIZE, "")) ;
    }

    /**
     * 払出し区分(<code>REMOVE</code>) に値をセットします。
     * @param value セットする値REMOVE
     */
    public void setRemove(String value)  // @@GEN_V3@@
    {
        setValue(REMOVE, value);
    }

    /**
     * 払出し区分(<code>REMOVE</code>) から値を取得します。
     * @return REMOVE
     */
    public String getRemove()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REMOVE, "")) ;
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>) に値をセットします。
     * @param value セットする値INVENTORY_CHECK_FLAG
     */
    public void setInventoryCheckFlag(String value)  // @@GEN_V3@@
    {
        setValue(INVENTORY_CHECK_FLAG, value);
    }

    /**
     * 在庫確認中フラグ(<code>INVENTORY_CHECK_FLAG</code>) から値を取得します。
     * @return INVENTORY_CHECK_FLAG
     */
    public String getInventoryCheckFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(INVENTORY_CHECK_FLAG, "")) ;
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>) に値をセットします。
     * @param value セットする値RESTORING_OPERATION
     */
    public void setRestoringOperation(String value)  // @@GEN_V3@@
    {
        setValue(RESTORING_OPERATION, value);
    }

    /**
     * 再入庫作業有無(<code>RESTORING_OPERATION</code>) から値を取得します。
     * @return RESTORING_OPERATION
     */
    public String getRestoringOperation()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RESTORING_OPERATION, "")) ;
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>) に値をセットします。
     * @param value セットする値RESTORING_INSTRUCTION
     */
    public void setRestoringInstruction(String value)  // @@GEN_V3@@
    {
        setValue(RESTORING_INSTRUCTION, value);
    }

    /**
     * 再入庫搬送指示送信有無(<code>RESTORING_INSTRUCTION</code>) から値を取得します。
     * @return RESTORING_INSTRUCTION
     */
    public String getRestoringInstruction()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RESTORING_INSTRUCTION, "")) ;
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>) に値をセットします。
     * @param value セットする値LOCATION_SEARCH_FLAG
     */
    public void setLocationSearchFlag(String value)  // @@GEN_V3@@
    {
        setValue(LOCATION_SEARCH_FLAG, value);
    }

    /**
     * 棚再決定区分(<code>LOCATION_SEARCH_FLAG</code>) から値を取得します。
     * @return LOCATION_SEARCH_FLAG
     */
    public String getLocationSearchFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_SEARCH_FLAG, "")) ;
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
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>) に値をセットします。
     * @param value セットする値PARENT_STATION_NO
     */
    public void setParentStationNo(String value)  // @@GEN_V3@@
    {
        setValue(PARENT_STATION_NO, value);
    }

    /**
     * 親ステーションNo.(<code>PARENT_STATION_NO</code>) から値を取得します。
     * @return PARENT_STATION_NO
     */
    public String getParentStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PARENT_STATION_NO, "")) ;
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>) に値をセットします。
     * @param value セットする値AISLE_STATION_NO
     */
    public void setAisleStationNo(String value)  // @@GEN_V3@@
    {
        setValue(AISLE_STATION_NO, value);
    }

    /**
     * アイルステーションNo.(<code>AISLE_STATION_NO</code>) から値を取得します。
     * @return AISLE_STATION_NO
     */
    public String getAisleStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(AISLE_STATION_NO, "")) ;
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>) に値をセットします。
     * @param value セットする値NEXT_STATION_NO
     */
    public void setNextStationNo(String value)  // @@GEN_V3@@
    {
        setValue(NEXT_STATION_NO, value);
    }

    /**
     * 次搬送ステーションNo.(<code>NEXT_STATION_NO</code>) から値を取得します。
     * @return NEXT_STATION_NO
     */
    public String getNextStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(NEXT_STATION_NO, "")) ;
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>) に値をセットします。
     * @param value セットする値LAST_USED_STATION_NO
     */
    public void setLastUsedStationNo(String value)  // @@GEN_V3@@
    {
        setValue(LAST_USED_STATION_NO, value);
    }

    /**
     * 最終使用ステーションNo.(<code>LAST_USED_STATION_NO</code>) から値を取得します。
     * @return LAST_USED_STATION_NO
     */
    public String getLastUsedStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LAST_USED_STATION_NO, "")) ;
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>) に値をセットします。
     * @param value セットする値REJECT_STATION_NO
     */
    public void setRejectStationNo(String value)  // @@GEN_V3@@
    {
        setValue(REJECT_STATION_NO, value);
    }

    /**
     * リジェクトステーションNo.(<code>REJECT_STATION_NO</code>) から値を取得します。
     * @return REJECT_STATION_NO
     */
    public String getRejectStationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REJECT_STATION_NO, "")) ;
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>) に値をセットします。
     * @param value セットする値MODE_TYPE
     */
    public void setModeType(String value)  // @@GEN_V3@@
    {
        setValue(MODE_TYPE, value);
    }

    /**
     * モード切替種別(<code>MODE_TYPE</code>) から値を取得します。
     * @return MODE_TYPE
     */
    public String getModeType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MODE_TYPE, "")) ;
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>) に値をセットします。
     * @param value セットする値CURRENT_MODE
     */
    public void setCurrentMode(String value)  // @@GEN_V3@@
    {
        setValue(CURRENT_MODE, value);
    }

    /**
     * 現在作業モード(<code>CURRENT_MODE</code>) から値を取得します。
     * @return CURRENT_MODE
     */
    public String getCurrentMode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CURRENT_MODE, "")) ;
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>) に値をセットします。
     * @param value セットする値MODE_REQUEST
     */
    public void setModeRequest(String value)  // @@GEN_V3@@
    {
        setValue(MODE_REQUEST, value);
    }

    /**
     * モード切替要求区分(<code>MODE_REQUEST</code>) から値を取得します。
     * @return MODE_REQUEST
     */
    public String getModeRequest()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MODE_REQUEST, "")) ;
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>) に値をセットします。
     * @param value セットする値MODE_REQUEST_DATE
     */
    public void setModeRequestDate(Date value)  // @@GEN_V3@@
    {
        setValue(MODE_REQUEST_DATE, value);
    }

    /**
     * モード切替要求日時(<code>MODE_REQUEST_DATE</code>) から値を取得します。
     * @return MODE_REQUEST_DATE
     */
    public Date getModeRequestDate()  // @@GEN_V3@@
    {
        return (Date)getValue(MODE_REQUEST_DATE, null) ;
    }

    /**
     * クラス名(<code>CLASS_NAME</code>) に値をセットします。
     * @param value セットする値CLASS_NAME
     */
    public void setClassName(String value)  // @@GEN_V3@@
    {
        setValue(CLASS_NAME, value);
    }

    /**
     * クラス名(<code>CLASS_NAME</code>) から値を取得します。
     * @return CLASS_NAME
     */
    public String getClassName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CLASS_NAME, "")) ;
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
    /**<jp>
     * このステーションが指示情報として送信可能かどうか返します。
     * @return 送信可能フラグ
     *   true : 送信可能
     *   false : 送信不可能
     </jp>*/
    /**<en>
     * Returns whether/not this station is sendable of instruction data.
     * @return :sendable flag
     *   true  : sendable
     *   false : not sendable
     </en>*/
    public boolean isSendable()
    {
        if (Station.SENDABLE_TRUE.equals(getSendable()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**<jp>
     * このステーションがユニット出庫運用専用かどうかをチェックします。
     * このメソッドはステーションの種別をもとに運用のチェックを行ないます。
     * @return ステーションがユニット出庫運用専用であればtrueを。そうでなければfalseを返す。
     </jp>*/
    /**<en>
     * Checks whether this station is dedicated for unit retrieval operation.
     * In this method, it checks the operation based on the stataion type.
     * @return :true if the station is dedicated for unit retrievals only, or false if not.
     </en>*/
    public boolean isUnitOnly()
    {
        if (Station.STATION_TYPE_OUT.equals(getStationType()))
        {
            // コの字出庫側の場合はfalseを返します。
            if (FreeRetrievalStationOperator.class.getName().equals(getClassName()))
            {
                return false;
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    /**<jp>
     * このステーションが中断中かどうか返します。
     * @return 中断中フラグ
     *    true: 中断中の場合
     *    false: 使用可能の場合
     </jp>*/
    /**<en>
     * Returns whether/not the station is suspended.
     * @return suspention flag
     *    true : suspended
     *    false: available
     </en>*/
    public boolean isSuspend()
    {
        if (Station.SUSPEND_ON.equals(getSuspend()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**<jp>
     * このステーションが到着チェックありどうか返します。
     * @return 到着チェックありフラグ
     *    true: 到着チェックありの場合
     *    false: 到着チェックなしの場合
     </jp>*/
    /**<en>
     * Returns whether/not the arrival report check is done by this station.
     * @return flag with arriaval check 
     *    true : arrival check is done
     *    false: no check at arrival
     </en>*/
    public boolean isArrivalCheck()
    {
        if (Station.ARRIVAL_ON.equals(getArrival()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**<jp>
     * このステーションが払い出し可能かどうかを返します。
     * @return 払い出し可能フラグ
     *    true: 払出し可能の場合
     *    false: 払出し不可の場合
     </jp>*/
    /**<en>
     * Returns if this staion is available for transfer.
     * @return flag for transfer
     *    true : transfer available
     *    false: transfer unavailable
     </en>*/
    public boolean isRemove()
    {
        if (Station.REMOVE_OK.equals(getRemove()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**<jp>
     * このステーションが荷姿チェックありどうか返します。
     * @return 荷姿チェックフラグ
     *    true: 荷姿チェックありの場合
     *    false:荷姿チェックなしの場合false。
     </jp>*/
    /**<en>
     * Returns whether/not this station checks the load size.
     * @return flag of load size checki
     *    true: load size check is done
     *    false:load size is not checked
     </en>*/
    public boolean isLoadSizeCheck()
    {
        if (Station.LOAD_SIZE_ON.equals(getLoadSize()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**<jp>
     * 再入庫予定データの作成有無を取得します。
     * @return 再入庫予定データの作成有無
     *   true  : 再入庫予定データ作成
     *   false : 再入庫予定データ作成なし
     </jp>*/
    /**<en>
     * Gets whether/not the data of scheduled re-strage is created.
     * @return whether/not the data of scheduled re-strage is created
     *   true  : scheduled re-strage data is created
     *   false : scheduled re-strage data is not created
     </en>*/
    public boolean isReStoringOperation()
    {
        if (Station.RESTORING_OPERATION_CREATE.equals(getRestoringOperation()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**<jp>
     * このステーションが入庫ステーションかどうかを返します。
     * 入出庫ステーションは、trueが返ります。
     * @return 入庫ステーションフラグ
     *    true : 入庫ステーション
     *    false : 入庫ステーションでない
     </jp>*/
    /**<en>
     * Returns whether/not this station is the storage station.
     * It returns true for storage/retrieval staion.
     * @return :storage station flag
     *    true : storage station
     *    false : not storage station
     </en>*/
    public boolean isInStation()
    {
        String stationType = getStationType();
        if ((Station.STATION_TYPE_IN.equals(stationType)) || (Station.STATION_TYPE_INOUT.equals(stationType)))
        {
            return true;
        }
        else if (Station.STATION_TYPE_OUT.equals(stationType))
        {
            return false;
        }
        // 全て当てはまらなければfalseを返す。
        return false;
    }

    /**<jp>
     * このステーションが出庫ステーションかどうかを返します。
     * 入出庫ステーションは、trueが返ります。
     * @return 出庫ステーションフラグ
     *    true : 出庫ステーション
     *    false : 出庫ステーションでない
     </jp>*/
    /**<en>
     * Returns whether/not this is the retrieval station.
     * It returns true for storage/retrieval staion.
     * @return :retrieval station flag
     *    true :retrieval station
     *    false : not the retrieval station
     </en>*/
    public boolean isOutStation()
    {
        String stationType = getStationType();
        if ((Station.STATION_TYPE_OUT.equals(stationType)) || (Station.STATION_TYPE_INOUT.equals(stationType)))
        {
            return true;
        }
        else if (Station.STATION_TYPE_IN.equals(stationType))
        {
            return false;
        }
        // 全て当てはまらなければfalseを返す。
        return false;
    }

    /**<jp>
     * 入力されたステーションのモードが入庫モードかどうかを判断します。<BR>
     * ステーションのモードが入庫モードでかつモード切替要求中でなければ入庫モードと判断し、trueを返します。
     * 入庫専用ステーション、モード切替の無いステーション、自動モード切替ステーションの場合は常にtrueを返します。
     * 入庫／出庫のステーション種別チェックは行いません。
     * @return 入力されたステーションのモードが入庫ならばtrueそれ以外ならfalseを返します。
     </jp>*/
    /**<en>
     * Determines whether/not the entered station is in storage mode.<BR>
     * If the station is in storage mode and not requesting for mode switch, it determines the station
     * is in storage mode and returns true.
     * It always returns true for storage dedicated station, station with no mode switch and automatic mode
     * switching station.
     * @return :it returns 'true' if entered station is is storage mode, or returns 'false' for all other cases.
     </en>*/
    public boolean isStorageMode()
    {
        //<jp> 入出庫ステーション</jp>
        //<en> storage/retrieval ready station</en>
        if (Station.STATION_TYPE_INOUT.equals(getStationType()))
        {
            //<jp> モード切替機能あり</jp>
            //<en> Mode switch functions. </en>
            if ((Station.MODE_TYPE_AGC_CHANGE.equals(getModeType()))
                    || (Station.MODE_TYPE_AWC_CHANGE.equals(getModeType())))
            {
                //<jp> 入庫モードであるか</jp>
                //<en> If the station is in storage mode</en>
                if (Station.CURRENT_MODE_STORAGE.equals(getCurrentMode()))
                {
                    //<jp> モード切替要求中でないか</jp>
                    //<en> If the station is in storage mode</en>
                    if (Station.MODE_REQUEST_NONE.equals(getModeRequest()))
                    {
                        //<jp> 入庫モード</jp>
                        //<en> storage mode</en>
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }

    /**<jp>
     * 入力されたステーションのモードが出庫モードかどうかを判断します。<BR>
     * ステーションのモードが出庫モードでかつモード切替要求中でなければ出庫モードと判断し、trueを返します。
     * @return 入力されたステーションのモードが出庫ならばtrueそれ以外ならfalseを返します。
     </jp>*/
    /**<en>
     * Determines whether/not the entered station is in retrieval mode.<BR>
     * It checks the mode only when the mode switch type is controlled by mode selection switch on AGC side.<BR>
     * If the station is in retrieval mode and not requesting for mode switch, it determines the station is 
     * in retrieval mode and returns true.
     * @return :true if the entered station is in retrieval mode; or false for any other cases.
     </en>*/
    public boolean isRetrievalMode()
    {
        //<jp> 入出庫ステーション</jp>
        //<en> storage/retrieval ready station</en>
        if (Station.STATION_TYPE_INOUT.equals(getStationType()))
        {
            //<jp> モード切替機能あり</jp>
            //<en> Mode switch functions.</en>
            if ((Station.MODE_TYPE_AGC_CHANGE.equals(getModeType()))
                    || (Station.MODE_TYPE_AWC_CHANGE.equals(getModeType())))
            {
                //<jp> 出庫モードかチェック</jp>
                //<en> Checks if the station is in retrieval mode.</en>
                if (Station.CURRENT_MODE_RETRIEVAL.equals(getCurrentMode()))
                {
                    //<jp> モード切替要求中ではない</jp>
                    //<en> If the station is not requesting the mode switch.</en>
                    if (Station.MODE_REQUEST_NONE.equals(getModeRequest()))
                    {
                        //<jp> 出庫モード</jp>
                        //<en> Retrieval mode</en>
                        return true;
                    }
                }
                //<jp> 出庫モード以外かモード切替要求中</jp>
                //<en> Station is any mode other tahn retrieval, or it is requesting for mode switch.</en>
                return false;
            }
        }
        //<jp> モード管理なし</jp>
        //<en> No mode controls.</en>
        return true;
    }

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
        return "$Id: Station.java 5296 2009-10-28 05:23:30Z ota $" ;
    }
}

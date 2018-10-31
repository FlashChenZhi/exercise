// $Id: CarryInfo.java 1544 2008-11-25 09:32:24Z dmori $
// $LastChangedRevision: 1544 $
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
 * CARRYINFOのエンティティクラスです。
 *
 * @version $Revision: 1544 $, $Date: 2008-11-25 18:32:24 +0900 (火, 25 11 2008) $
 * @author  ss
 * @author  Last commit: $Author: dmori $
 */

public class CarryInfo
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNCARRYINFO" ;

    /*
     * テーブル名: DNCARRYINFO
     * 搬送Key :                       CARRY_KEY           varchar2(16)
     * パレットID :                    PALLET_ID           varchar2(8)
     * 作業種別 :                      WORK_TYPE           varchar2(2)
     * 出庫グループNo. :               GROUP_NO            number
     * 搬送状態 :                      CMD_STATUS          varchar2(1)
     * 優先区分 :                      PRIORITY            varchar2(1)
     * 再入庫区分 :                    RESTORING_FLAG      varchar2(1)
     * 搬送区分 :                      CARRY_FLAG          varchar2(1)
     * 出庫ロケーションNo. :           RETRIEVAL_STATION_NOvarchar2(16)
     * 出庫指示詳細 :                  RETRIEVAL_DETAIL    varchar2(1)
     * 作業No. :                       WORK_NO             varchar2(16)
     * 搬送元ステーションNo. :         SOURCE_STATION_NO   varchar2(16)
     * 搬送先ステーションNo. :         DEST_STATION_NO     varchar2(16)
     * 到着日時 :                      ARRIVAL_DATE        date
     * 制御情報 :                      CONTROLINFO         varchar2(30)
     * キャンセル要求区分 :            CANCEL_REQUEST      varchar2(1)
     * キャンセル要求日時 :            CANCEL_REQUEST_DATE date
     * スケジュールNo :                SCHEDULE_NO         varchar2(9)
     * アイルステーションNo. :         AISLE_STATION_NO    varchar2(16)
     * 最終ステーションNo. :           END_STATION_NO      varchar2(16)
     * 異常コード :                    ERROR_CODE          varchar2(4)
     * メンテナンス端末No. :           MAINTENANCE_TERMINALvarchar2(4)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */

    /** フィールド定義 (搬送Key(<code>CARRY_KEY</code>)) */
    public static final FieldName CARRY_KEY = new FieldName(STORE_NAME, "CARRY_KEY") ;

    /** フィールド定義 (パレットID(<code>PALLET_ID</code>)) */
    public static final FieldName PALLET_ID = new FieldName(STORE_NAME, "PALLET_ID") ;

    /** フィールド定義 (作業種別(<code>WORK_TYPE</code>)) */
    public static final FieldName WORK_TYPE = new FieldName(STORE_NAME, "WORK_TYPE") ;

    /** フィールド定義 (出庫グループNo.(<code>GROUP_NO</code>)) */
    public static final FieldName GROUP_NO = new FieldName(STORE_NAME, "GROUP_NO") ;

    /** フィールド定義 (搬送状態(<code>CMD_STATUS</code>)) */
    public static final FieldName CMD_STATUS = new FieldName(STORE_NAME, "CMD_STATUS") ;

    /** フィールド定義 (優先区分(<code>PRIORITY</code>)) */
    public static final FieldName PRIORITY = new FieldName(STORE_NAME, "PRIORITY") ;

    /** フィールド定義 (再入庫区分(<code>RESTORING_FLAG</code>)) */
    public static final FieldName RESTORING_FLAG = new FieldName(STORE_NAME, "RESTORING_FLAG") ;

    /** フィールド定義 (搬送区分(<code>CARRY_FLAG</code>)) */
    public static final FieldName CARRY_FLAG = new FieldName(STORE_NAME, "CARRY_FLAG") ;

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

    /** フィールド定義 (到着日時(<code>ARRIVAL_DATE</code>)) */
    public static final FieldName ARRIVAL_DATE = new FieldName(STORE_NAME, "ARRIVAL_DATE") ;

    /** フィールド定義 (制御情報(<code>CONTROLINFO</code>)) */
    public static final FieldName CONTROLINFO = new FieldName(STORE_NAME, "CONTROLINFO") ;

    /** フィールド定義 (キャンセル要求区分(<code>CANCEL_REQUEST</code>)) */
    public static final FieldName CANCEL_REQUEST = new FieldName(STORE_NAME, "CANCEL_REQUEST") ;

    /** フィールド定義 (キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>)) */
    public static final FieldName CANCEL_REQUEST_DATE = new FieldName(STORE_NAME, "CANCEL_REQUEST_DATE") ;

    /** フィールド定義 (スケジュールNo(<code>SCHEDULE_NO</code>)) */
    public static final FieldName SCHEDULE_NO = new FieldName(STORE_NAME, "SCHEDULE_NO") ;

    /** フィールド定義 (アイルステーションNo.(<code>AISLE_STATION_NO</code>)) */
    public static final FieldName AISLE_STATION_NO = new FieldName(STORE_NAME, "AISLE_STATION_NO") ;

    /** フィールド定義 (最終ステーションNo.(<code>END_STATION_NO</code>)) */
    public static final FieldName END_STATION_NO = new FieldName(STORE_NAME, "END_STATION_NO") ;

    /** フィールド定義 (異常コード(<code>ERROR_CODE</code>)) */
    public static final FieldName ERROR_CODE = new FieldName(STORE_NAME, "ERROR_CODE") ;

    /** フィールド定義 (メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>)) */
    public static final FieldName MAINTENANCE_TERMINAL = new FieldName(STORE_NAME, "MAINTENANCE_TERMINAL") ;

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
    public CarryInfo()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

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
     * 作業種別(<code>WORK_TYPE</code>) に値をセットします。
     * @param value セットする値WORK_TYPE
     */
    public void setWorkType(String value)  // @@GEN_V3@@
    {
        setValue(WORK_TYPE, value);
    }

    /**
     * 作業種別(<code>WORK_TYPE</code>) から値を取得します。
     * @return WORK_TYPE
     */
    public String getWorkType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WORK_TYPE, "")) ;
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>) に値をセットします。
     * @param value セットする値GROUP_NO
     */
    public void setGroupNo(int value)  // @@GEN_V3@@
    {
        setValue(GROUP_NO, HandlerUtil.toObject(value));
    }

    /**
     * 出庫グループNo.(<code>GROUP_NO</code>) から値を取得します。
     * @return GROUP_NO
     */
    public int getGroupNo()  // @@GEN_V3@@
    {
        return getBigDecimal(GROUP_NO, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>) に値をセットします。
     * @param value セットする値CMD_STATUS
     */
    public void setCmdStatus(String value)  // @@GEN_V3@@
    {
        setValue(CMD_STATUS, value);
    }

    /**
     * 搬送状態(<code>CMD_STATUS</code>) から値を取得します。
     * @return CMD_STATUS
     */
    public String getCmdStatus()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CMD_STATUS, "")) ;
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
     * 再入庫区分(<code>RESTORING_FLAG</code>) に値をセットします。
     * @param value セットする値RESTORING_FLAG
     */
    public void setRestoringFlag(String value)  // @@GEN_V3@@
    {
        setValue(RESTORING_FLAG, value);
    }

    /**
     * 再入庫区分(<code>RESTORING_FLAG</code>) から値を取得します。
     * @return RESTORING_FLAG
     */
    public String getRestoringFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RESTORING_FLAG, "")) ;
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>) に値をセットします。
     * @param value セットする値CARRY_FLAG
     */
    public void setCarryFlag(String value)  // @@GEN_V3@@
    {
        setValue(CARRY_FLAG, value);
    }

    /**
     * 搬送区分(<code>CARRY_FLAG</code>) から値を取得します。
     * @return CARRY_FLAG
     */
    public String getCarryFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CARRY_FLAG, "")) ;
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
     * 到着日時(<code>ARRIVAL_DATE</code>) に値をセットします。
     * @param value セットする値ARRIVAL_DATE
     */
    public void setArrivalDate(Date value)  // @@GEN_V3@@
    {
        setValue(ARRIVAL_DATE, value);
    }

    /**
     * 到着日時(<code>ARRIVAL_DATE</code>) から値を取得します。
     * @return ARRIVAL_DATE
     */
    public Date getArrivalDate()  // @@GEN_V3@@
    {
        return (Date)getValue(ARRIVAL_DATE, null) ;
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>) に値をセットします。
     * @param value セットする値CONTROLINFO
     */
    public void setControlinfo(String value)  // @@GEN_V3@@
    {
        setValue(CONTROLINFO, value);
    }

    /**
     * 制御情報(<code>CONTROLINFO</code>) から値を取得します。
     * @return CONTROLINFO
     */
    public String getControlinfo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONTROLINFO, "")) ;
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>) に値をセットします。
     * @param value セットする値CANCEL_REQUEST
     */
    public void setCancelRequest(String value)  // @@GEN_V3@@
    {
        setValue(CANCEL_REQUEST, value);
    }

    /**
     * キャンセル要求区分(<code>CANCEL_REQUEST</code>) から値を取得します。
     * @return CANCEL_REQUEST
     */
    public String getCancelRequest()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CANCEL_REQUEST, "")) ;
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>) に値をセットします。
     * @param value セットする値CANCEL_REQUEST_DATE
     */
    public void setCancelRequestDate(Date value)  // @@GEN_V3@@
    {
        setValue(CANCEL_REQUEST_DATE, value);
    }

    /**
     * キャンセル要求日時(<code>CANCEL_REQUEST_DATE</code>) から値を取得します。
     * @return CANCEL_REQUEST_DATE
     */
    public Date getCancelRequestDate()  // @@GEN_V3@@
    {
        return (Date)getValue(CANCEL_REQUEST_DATE, null) ;
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
     * 異常コード(<code>ERROR_CODE</code>) に値をセットします。
     * @param value セットする値ERROR_CODE
     */
    public void setErrorCode(String value)  // @@GEN_V3@@
    {
        setValue(ERROR_CODE, value);
    }

    /**
     * 異常コード(<code>ERROR_CODE</code>) から値を取得します。
     * @return ERROR_CODE
     */
    public String getErrorCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ERROR_CODE, "")) ;
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>) に値をセットします。
     * @param value セットする値MAINTENANCE_TERMINAL
     */
    public void setMaintenanceTerminal(String value)  // @@GEN_V3@@
    {
        setValue(MAINTENANCE_TERMINAL, value);
    }

    /**
     * メンテナンス端末No.(<code>MAINTENANCE_TERMINAL</code>) から値を取得します。
     * @return MAINTENANCE_TERMINAL
     */
    public String getMaintenanceTerminal()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MAINTENANCE_TERMINAL, "")) ;
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
        return "$Id: CarryInfo.java 1544 2008-11-25 09:32:24Z dmori $" ;
    }
}

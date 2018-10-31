// $Id: ReStoringPlan.java 7201 2010-02-24 02:36:39Z kishimoto $
// $LastChangedRevision: 7201 $
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
 * RESTORINGPLANのエンティティクラスです。
 *
 * @version $Revision: 7201 $, $Date: 2010-02-24 11:36:39 +0900 (水, 24 2 2010) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */

public class ReStoringPlan
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNRESTORINGPLAN" ;

    /*
     * テーブル名: DNRESTORINGPLAN
     * 予定一意キー :                  PLAN_UKEY           varchar2(10)
     * 状態フラグ :                    STATUS_FLAG         varchar2(1)
     * ステーションNo. :               STATION_NO          varchar2(16)
     * 作業No. :                       WORK_NO             varchar2(16)
     * 予定日 :                        PLAN_DAY            varchar2(8)
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 予定エリアNo. :                 PLAN_AREA_NO        varchar2(4)
     * 予定棚No. :                     PLAN_LOCATION_NO    varchar2(11)
     * 商品コード :                    ITEM_CODE           varchar2(40)
     * 予定ロットNo. :                 PLAN_LOT_NO         varchar2(60)
     * 入庫日 :                        STORAGE_DAY         varchar2(8)
     * 入庫日時 :                      STORAGE_DATE        date
     * 最終出庫日 :                    RETRIEVAL_DAY       varchar2(8)
     * 払出日時 :                      REMOVE_DATE         date
     * 予定数 :                        PLAN_QTY            number
     * 実績数 :                        RESULT_QTY          number
     * 欠品数 :                        SHORTAGE_QTY        number
     * 実績報告区分 :                  REPORT_FLAG         varchar2(1)
     * 作業日 :                        WORK_DAY            varchar2(8)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */

    /** フィールド定義 (予定一意キー(<code>PLAN_UKEY</code>)) */
    public static final FieldName PLAN_UKEY = new FieldName(STORE_NAME, "PLAN_UKEY") ;

    /** フィールド定義 (状態フラグ(<code>STATUS_FLAG</code>)) */
    public static final FieldName STATUS_FLAG = new FieldName(STORE_NAME, "STATUS_FLAG") ;

    /** フィールド定義 (ステーションNo.(<code>STATION_NO</code>)) */
    public static final FieldName STATION_NO = new FieldName(STORE_NAME, "STATION_NO") ;

    /** フィールド定義 (作業No.(<code>WORK_NO</code>)) */
    public static final FieldName WORK_NO = new FieldName(STORE_NAME, "WORK_NO") ;

    /** フィールド定義 (予定日(<code>PLAN_DAY</code>)) */
    public static final FieldName PLAN_DAY = new FieldName(STORE_NAME, "PLAN_DAY") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (予定エリアNo.(<code>PLAN_AREA_NO</code>)) */
    public static final FieldName PLAN_AREA_NO = new FieldName(STORE_NAME, "PLAN_AREA_NO") ;

    /** フィールド定義 (予定棚No.(<code>PLAN_LOCATION_NO</code>)) */
    public static final FieldName PLAN_LOCATION_NO = new FieldName(STORE_NAME, "PLAN_LOCATION_NO") ;

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE") ;

    /** フィールド定義 (予定ロットNo.(<code>PLAN_LOT_NO</code>)) */
    public static final FieldName PLAN_LOT_NO = new FieldName(STORE_NAME, "PLAN_LOT_NO") ;

    /** フィールド定義 (入庫日(<code>STORAGE_DAY</code>)) */
    public static final FieldName STORAGE_DAY = new FieldName(STORE_NAME, "STORAGE_DAY") ;

    /** フィールド定義 (入庫日時(<code>STORAGE_DATE</code>)) */
    public static final FieldName STORAGE_DATE = new FieldName(STORE_NAME, "STORAGE_DATE") ;

    /** フィールド定義 (最終出庫日(<code>RETRIEVAL_DAY</code>)) */
    public static final FieldName RETRIEVAL_DAY = new FieldName(STORE_NAME, "RETRIEVAL_DAY") ;

    /** フィールド定義 (払出日時(<code>REMOVE_DATE</code>)) */
    public static final FieldName REMOVE_DATE = new FieldName(STORE_NAME, "REMOVE_DATE") ;

    /** フィールド定義 (予定数(<code>PLAN_QTY</code>)) */
    public static final FieldName PLAN_QTY = new FieldName(STORE_NAME, "PLAN_QTY") ;

    /** フィールド定義 (実績数(<code>RESULT_QTY</code>)) */
    public static final FieldName RESULT_QTY = new FieldName(STORE_NAME, "RESULT_QTY") ;

    /** フィールド定義 (欠品数(<code>SHORTAGE_QTY</code>)) */
    public static final FieldName SHORTAGE_QTY = new FieldName(STORE_NAME, "SHORTAGE_QTY") ;

    /** フィールド定義 (実績報告区分(<code>REPORT_FLAG</code>)) */
    public static final FieldName REPORT_FLAG = new FieldName(STORE_NAME, "REPORT_FLAG") ;

    /** フィールド定義 (作業日(<code>WORK_DAY</code>)) */
    public static final FieldName WORK_DAY = new FieldName(STORE_NAME, "WORK_DAY") ;

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
    public ReStoringPlan()
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
     * 払出日時(<code>REMOVE_DATE</code>) に値をセットします。
     * @param value セットする値REMOVE_DATE
     */
    public void setRemoveDate(Date value)  // @@GEN_V3@@
    {
        setValue(REMOVE_DATE, value);
    }

    /**
     * 払出日時(<code>REMOVE_DATE</code>) から値を取得します。
     * @return REMOVE_DATE
     */
    public Date getRemoveDate()  // @@GEN_V3@@
    {
        return (Date)getValue(REMOVE_DATE, null) ;
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
     * 実績報告区分(<code>REPORT_FLAG</code>) に値をセットします。
     * @param value セットする値REPORT_FLAG
     */
    public void setReportFlag(String value)  // @@GEN_V3@@
    {
        setValue(REPORT_FLAG, value);
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>) から値を取得します。
     * @return REPORT_FLAG
     */
    public String getReportFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REPORT_FLAG, "")) ;
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
        return "$Id: ReStoringPlan.java 7201 2010-02-24 02:36:39Z kishimoto $" ;
    }
}

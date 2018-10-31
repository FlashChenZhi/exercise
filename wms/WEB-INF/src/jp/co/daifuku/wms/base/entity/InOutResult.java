// $Id: InOutResult.java 1544 2008-11-25 09:32:24Z dmori $
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
 * INOUTRESULTのエンティティクラスです。
 *
 * @version $Revision: 1544 $, $Date: 2008-11-25 18:32:24 +0900 (火, 25 11 2008) $
 * @author  ss
 * @author  Last commit: $Author: dmori $
 */

public class InOutResult
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNINOUTRESULT" ;

    /*
     * テーブル名: DNINOUTRESULT
     * 実績作成区分 :                  RESULT_KIND         varchar2(1)
     * 作業種別 :                      WORK_TYPE           varchar2(2)
     * ステーションNo. :               STATION_NO          varchar2(16)
     * 倉庫ステーションNo. :           WH_STATION_NO       varchar2(16)
     * アイルステーションNo. :         AISLE_STATION_NO    varchar2(16)
     * 出庫指示詳細 :                  RETRIEVAL_DETAIL    varchar2(1)
     * 強制払出し区分 :                REMOVE_FLAG         varchar2(2)
     * 作業No. :                       WORK_NO             varchar2(16)
     * 棚No. :                         LOCATION_NO         varchar2(16)
     * パレットID :                    PALLET_ID           varchar2(8)
     * 作業日 :                        WORK_DAY            varchar2(8)
     * 搬送key :                       CARRY_KEY           varchar2(16)
     * 再入庫区分 :                    RESTORING_FLAG      varchar2(1)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */

    /** フィールド定義 (実績作成区分(<code>RESULT_KIND</code>)) */
    public static final FieldName RESULT_KIND = new FieldName(STORE_NAME, "RESULT_KIND") ;

    /** フィールド定義 (作業種別(<code>WORK_TYPE</code>)) */
    public static final FieldName WORK_TYPE = new FieldName(STORE_NAME, "WORK_TYPE") ;

    /** フィールド定義 (ステーションNo.(<code>STATION_NO</code>)) */
    public static final FieldName STATION_NO = new FieldName(STORE_NAME, "STATION_NO") ;

    /** フィールド定義 (倉庫ステーションNo.(<code>WH_STATION_NO</code>)) */
    public static final FieldName WH_STATION_NO = new FieldName(STORE_NAME, "WH_STATION_NO") ;

    /** フィールド定義 (アイルステーションNo.(<code>AISLE_STATION_NO</code>)) */
    public static final FieldName AISLE_STATION_NO = new FieldName(STORE_NAME, "AISLE_STATION_NO") ;

    /** フィールド定義 (出庫指示詳細(<code>RETRIEVAL_DETAIL</code>)) */
    public static final FieldName RETRIEVAL_DETAIL = new FieldName(STORE_NAME, "RETRIEVAL_DETAIL") ;

    /** フィールド定義 (強制払出し区分(<code>REMOVE_FLAG</code>)) */
    public static final FieldName REMOVE_FLAG = new FieldName(STORE_NAME, "REMOVE_FLAG") ;

    /** フィールド定義 (作業No.(<code>WORK_NO</code>)) */
    public static final FieldName WORK_NO = new FieldName(STORE_NAME, "WORK_NO") ;

    /** フィールド定義 (棚No.(<code>LOCATION_NO</code>)) */
    public static final FieldName LOCATION_NO = new FieldName(STORE_NAME, "LOCATION_NO") ;

    /** フィールド定義 (パレットID(<code>PALLET_ID</code>)) */
    public static final FieldName PALLET_ID = new FieldName(STORE_NAME, "PALLET_ID") ;

    /** フィールド定義 (作業日(<code>WORK_DAY</code>)) */
    public static final FieldName WORK_DAY = new FieldName(STORE_NAME, "WORK_DAY") ;

    /** フィールド定義 (搬送key(<code>CARRY_KEY</code>)) */
    public static final FieldName CARRY_KEY = new FieldName(STORE_NAME, "CARRY_KEY") ;

    /** フィールド定義 (再入庫区分(<code>RESTORING_FLAG</code>)) */
    public static final FieldName RESTORING_FLAG = new FieldName(STORE_NAME, "RESTORING_FLAG") ;

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
    public InOutResult()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 実績作成区分(<code>RESULT_KIND</code>) に値をセットします。
     * @param value セットする値RESULT_KIND
     */
    public void setResultKind(String value)  // @@GEN_V3@@
    {
        setValue(RESULT_KIND, value);
    }

    /**
     * 実績作成区分(<code>RESULT_KIND</code>) から値を取得します。
     * @return RESULT_KIND
     */
    public String getResultKind()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RESULT_KIND, "")) ;
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
     * 強制払出し区分(<code>REMOVE_FLAG</code>) に値をセットします。
     * @param value セットする値REMOVE_FLAG
     */
    public void setRemoveFlag(String value)  // @@GEN_V3@@
    {
        setValue(REMOVE_FLAG, value);
    }

    /**
     * 強制払出し区分(<code>REMOVE_FLAG</code>) から値を取得します。
     * @return REMOVE_FLAG
     */
    public String getRemoveFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REMOVE_FLAG, "")) ;
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
     * 搬送key(<code>CARRY_KEY</code>) に値をセットします。
     * @param value セットする値CARRY_KEY
     */
    public void setCarryKey(String value)  // @@GEN_V3@@
    {
        setValue(CARRY_KEY, value);
    }

    /**
     * 搬送key(<code>CARRY_KEY</code>) から値を取得します。
     * @return CARRY_KEY
     */
    public String getCarryKey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CARRY_KEY, "")) ;
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
        return "$Id: InOutResult.java 1544 2008-11-25 09:32:24Z dmori $" ;
    }
}

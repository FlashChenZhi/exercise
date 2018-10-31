// $Id: Rft.java 4154 2009-04-16 04:51:42Z rnakai $
// $LastChangedRevision: 4154 $
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
 * RFTのエンティティクラスです。
 *
 * @version $Revision: 4154 $, $Date: 2009-04-16 13:51:42 +0900 (木, 16 4 2009) $
 * @author  ss
 * @author  Last commit: $Author: rnakai $
 */

public class Rft
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMRFT" ;

    /*
     * テーブル名: DMRFT
     * 号機No. :                       RFT_NO              varchar2(3)
     * ユーザID :                      USER_ID             varchar2(20)
     * 作業区分 :                      JOB_TYPE            varchar2(2)
     * 状態フラグ :                    STATUS_FLAG         varchar2(1)
     * 端末区分 :                      TERMINAL_TYPE       varchar2(2)
     * 端末IPアドレス :                IP_ADDRESS          varchar2(15)
     * 無線状態フラグ :                RADIO_FLAG          varchar2(1)
     * 休憩開始日時 :                  REST_START_DATE     date
     * 作業区分詳細 :                  JOB_DETAILS         varchar2(1)
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * エリアNo. :                     AREA_NO             varchar2(4)
     * バッチSeqNo. :                  BATCH_SEQ_NO        varchar2(12)
     * 設定単位キー :                  SETTING_UNIT_KEY    varchar2(8)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (号機No.(<code>RFT_NO</code>)) */
    public static final FieldName RFT_NO = new FieldName(STORE_NAME, "RFT_NO") ;

    /** フィールド定義 (ユーザID(<code>USER_ID</code>)) */
    public static final FieldName USER_ID = new FieldName(STORE_NAME, "USER_ID") ;

    /** フィールド定義 (作業区分(<code>JOB_TYPE</code>)) */
    public static final FieldName JOB_TYPE = new FieldName(STORE_NAME, "JOB_TYPE") ;

    /** フィールド定義 (状態フラグ(<code>STATUS_FLAG</code>)) */
    public static final FieldName STATUS_FLAG = new FieldName(STORE_NAME, "STATUS_FLAG") ;

    /** フィールド定義 (端末区分(<code>TERMINAL_TYPE</code>)) */
    public static final FieldName TERMINAL_TYPE = new FieldName(STORE_NAME, "TERMINAL_TYPE") ;

    /** フィールド定義 (端末IPアドレス(<code>IP_ADDRESS</code>)) */
    public static final FieldName IP_ADDRESS = new FieldName(STORE_NAME, "IP_ADDRESS") ;

    /** フィールド定義 (無線状態フラグ(<code>RADIO_FLAG</code>)) */
    public static final FieldName RADIO_FLAG = new FieldName(STORE_NAME, "RADIO_FLAG") ;

    /** フィールド定義 (休憩開始日時(<code>REST_START_DATE</code>)) */
    public static final FieldName REST_START_DATE = new FieldName(STORE_NAME, "REST_START_DATE") ;

    /** フィールド定義 (作業区分詳細(<code>JOB_DETAILS</code>)) */
    public static final FieldName JOB_DETAILS = new FieldName(STORE_NAME, "JOB_DETAILS") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (エリアNo.(<code>AREA_NO</code>)) */
    public static final FieldName AREA_NO = new FieldName(STORE_NAME, "AREA_NO") ;

    /** フィールド定義 (バッチSeqNo.(<code>BATCH_SEQ_NO</code>)) */
    public static final FieldName BATCH_SEQ_NO = new FieldName(STORE_NAME, "BATCH_SEQ_NO") ;

    /** フィールド定義 (設定単位キー(<code>SETTING_UNIT_KEY</code>)) */
    public static final FieldName SETTING_UNIT_KEY = new FieldName(STORE_NAME, "SETTING_UNIT_KEY") ;

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
    public Rft()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 号機No.(<code>RFT_NO</code>) に値をセットします。
     * @param value セットする値RFT_NO
     */
    public void setRftNo(String value)  // @@GEN_V3@@
    {
        setValue(RFT_NO, value);
    }

    /**
     * 号機No.(<code>RFT_NO</code>) から値を取得します。
     * @return RFT_NO
     */
    public String getRftNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RFT_NO, "")) ;
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
     * 端末区分(<code>TERMINAL_TYPE</code>) に値をセットします。
     * @param value セットする値TERMINAL_TYPE
     */
    public void setTerminalType(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_TYPE, value);
    }

    /**
     * 端末区分(<code>TERMINAL_TYPE</code>) から値を取得します。
     * @return TERMINAL_TYPE
     */
    public String getTerminalType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_TYPE, "")) ;
    }

    /**
     * 端末IPアドレス(<code>IP_ADDRESS</code>) に値をセットします。
     * @param value セットする値IP_ADDRESS
     */
    public void setIpAddress(String value)  // @@GEN_V3@@
    {
        setValue(IP_ADDRESS, value);
    }

    /**
     * 端末IPアドレス(<code>IP_ADDRESS</code>) から値を取得します。
     * @return IP_ADDRESS
     */
    public String getIpAddress()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(IP_ADDRESS, "")) ;
    }

    /**
     * 無線状態フラグ(<code>RADIO_FLAG</code>) に値をセットします。
     * @param value セットする値RADIO_FLAG
     */
    public void setRadioFlag(String value)  // @@GEN_V3@@
    {
        setValue(RADIO_FLAG, value);
    }

    /**
     * 無線状態フラグ(<code>RADIO_FLAG</code>) から値を取得します。
     * @return RADIO_FLAG
     */
    public String getRadioFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RADIO_FLAG, "")) ;
    }

    /**
     * 休憩開始日時(<code>REST_START_DATE</code>) に値をセットします。
     * @param value セットする値REST_START_DATE
     */
    public void setRestStartDate(Date value)  // @@GEN_V3@@
    {
        setValue(REST_START_DATE, value);
    }

    /**
     * 休憩開始日時(<code>REST_START_DATE</code>) から値を取得します。
     * @return REST_START_DATE
     */
    public Date getRestStartDate()  // @@GEN_V3@@
    {
        return (Date)getValue(REST_START_DATE, null) ;
    }

    /**
     * 作業区分詳細(<code>JOB_DETAILS</code>) に値をセットします。
     * @param value セットする値JOB_DETAILS
     */
    public void setJobDetails(String value)  // @@GEN_V3@@
    {
        setValue(JOB_DETAILS, value);
    }

    /**
     * 作業区分詳細(<code>JOB_DETAILS</code>) から値を取得します。
     * @return JOB_DETAILS
     */
    public String getJobDetails()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(JOB_DETAILS, "")) ;
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
        return "$Id: Rft.java 4154 2009-04-16 04:51:42Z rnakai $" ;
    }
}

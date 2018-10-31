// $Id: ExchangeHistory.java 7239 2010-02-26 05:18:07Z okayama $
// $LastChangedRevision: 7239 $
package jp.co.daifuku.wms.base.entity;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;

import jp.co.daifuku.wms.handler.db.AbstractDBEntity;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreDefineHandler;
import jp.co.daifuku.wms.handler.field.StoreMetaData;

/**
 * EXCHANGEHISTORYのエンティティクラスです。
 *
 * @version $Revision: 7239 $, $Date: 2010-02-26 14:18:07 +0900 (金, 26 2 2010) $
 * @author  ss
 * @author  Last commit: $Author: okayama $
 */

public class ExchangeHistory
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 状態：正常
     */
    public static final String STATUS_NORMAL = "0";
    /**
     * 状態：正常(スキップあり)
     */
    public static final String STATUS_SKIP = "1";
    /**
     * 状態：正常(全スキップ)
     */
    public static final String STATUS_ALL_SKIP = "2";
    /**
     * 状態：ファイル名が不正なため取得不可
     */
    public static final String STATUS_ERR_FILE_NAME = "11";
    /**
     * 状態：ファイルの取得に失敗
     */
    public static final String STATUS_ERR_DOWNLOAD = "12";
    /**
     * 状態：同一ファイル２度受信
     */
    public static final String STATUS_ERR_DUPPLICATION = "13";
    /**
     * 状態：受信データ内エラー
     */
    public static final String STATUS_ERR_DATA = "14";
    /**
     * 状態：ファイル削除中エラー
     */
    public static final String STATUS_ERR_DELETE_FILE = "15";
    /**
     * 状態:送信中にエラー発生
     */
    public static final String STATUS_ERR_SEND = "16";
    /**
     * 状態：DBエラー
     */
    public static final String STATUS_EXCEPTION = "20";

    /** テーブル名定義 */
    public static final String STORE_NAME = "DNEXCHANGEHISTORY" ;

    /*
     * テーブル名: DNEXCHANGEHISTORY
     * 送受信日時 :                    START_DATE          timestamp
     * 取込データ区分 :                JOB_TYPE            varchar2(2)
     * 送受信区分 :                    EXCHANGE_TYPE       varchar2(1)
     * 取込種別 :                      DATA_TYPE           varchar2(1)
     * 取込ファイル名 :                DATA_NAME           varchar2(30)
     * 報告単位 :                      REPORT_TYPE         varchar2(1)
     * 取込ファイル名 :                FILE_NAME           varchar2(40)
     * 状態 :                          STATUS              varchar2(2)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (送受信日時(<code>START_DATE</code>)) */
    public static final FieldName START_DATE = new FieldName(STORE_NAME, "START_DATE") ;

    /** フィールド定義 (取込データ区分(<code>JOB_TYPE</code>)) */
    public static final FieldName JOB_TYPE = new FieldName(STORE_NAME, "JOB_TYPE") ;

    /** フィールド定義 (送受信区分(<code>EXCHANGE_TYPE</code>)) */
    public static final FieldName EXCHANGE_TYPE = new FieldName(STORE_NAME, "EXCHANGE_TYPE") ;

    /** フィールド定義 (取込種別(<code>DATA_TYPE</code>)) */
    public static final FieldName DATA_TYPE = new FieldName(STORE_NAME, "DATA_TYPE") ;

    /** フィールド定義 (取込ファイル名(<code>DATA_NAME</code>)) */
    public static final FieldName DATA_NAME = new FieldName(STORE_NAME, "DATA_NAME") ;

    /** フィールド定義 (報告単位(<code>REPORT_TYPE</code>)) */
    public static final FieldName REPORT_TYPE = new FieldName(STORE_NAME, "REPORT_TYPE") ;

    /** フィールド定義 (取込ファイル名(<code>FILE_NAME</code>)) */
    public static final FieldName FILE_NAME = new FieldName(STORE_NAME, "FILE_NAME") ;

    /** フィールド定義 (状態(<code>STATUS</code>)) */
    public static final FieldName STATUS = new FieldName(STORE_NAME, "STATUS") ;

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
    public ExchangeHistory()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 送受信日時(<code>START_DATE</code>) に値をセットします。
     * @param value セットする値START_DATE
     */
    public void setStartDate(Date value)  // @@GEN_V3@@
    {
        setValue(START_DATE, value);
    }

    /**
     * 送受信日時(<code>START_DATE</code>) から値を取得します。
     * @return START_DATE
     */
    public Date getStartDate()  // @@GEN_V3@@
    {
        return (Date)getValue(START_DATE, null) ;
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>) に値をセットします。
     * @param value セットする値JOB_TYPE
     */
    public void setJobType(String value)  // @@GEN_V3@@
    {
        setValue(JOB_TYPE, value);
    }

    /**
     * 取込データ区分(<code>JOB_TYPE</code>) から値を取得します。
     * @return JOB_TYPE
     */
    public String getJobType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(JOB_TYPE, "")) ;
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>) に値をセットします。
     * @param value セットする値EXCHANGE_TYPE
     */
    public void setExchangeType(String value)  // @@GEN_V3@@
    {
        setValue(EXCHANGE_TYPE, value);
    }

    /**
     * 送受信区分(<code>EXCHANGE_TYPE</code>) から値を取得します。
     * @return EXCHANGE_TYPE
     */
    public String getExchangeType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(EXCHANGE_TYPE, "")) ;
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>) に値をセットします。
     * @param value セットする値DATA_TYPE
     */
    public void setDataType(String value)  // @@GEN_V3@@
    {
        setValue(DATA_TYPE, value);
    }

    /**
     * 取込種別(<code>DATA_TYPE</code>) から値を取得します。
     * @return DATA_TYPE
     */
    public String getDataType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(DATA_TYPE, "")) ;
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>) に値をセットします。
     * @param value セットする値DATA_NAME
     */
    public void setDataName(String value)  // @@GEN_V3@@
    {
        setValue(DATA_NAME, value);
    }

    /**
     * 取込ファイル名(<code>DATA_NAME</code>) から値を取得します。
     * @return DATA_NAME
     */
    public String getDataName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(DATA_NAME, "")) ;
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>) に値をセットします。
     * @param value セットする値REPORT_TYPE
     */
    public void setReportType(String value)  // @@GEN_V3@@
    {
        setValue(REPORT_TYPE, value);
    }

    /**
     * 報告単位(<code>REPORT_TYPE</code>) から値を取得します。
     * @return REPORT_TYPE
     */
    public String getReportType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REPORT_TYPE, "")) ;
    }

    /**
     * 取込ファイル名(<code>FILE_NAME</code>) に値をセットします。
     * @param value セットする値FILE_NAME
     */
    public void setFileName(String value)  // @@GEN_V3@@
    {
        setValue(FILE_NAME, value);
    }

    /**
     * 取込ファイル名(<code>FILE_NAME</code>) から値を取得します。
     * @return FILE_NAME
     */
    public String getFileName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(FILE_NAME, "")) ;
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
        return "$Id: ExchangeHistory.java 7239 2010-02-26 05:18:07Z okayama $" ;
    }
}

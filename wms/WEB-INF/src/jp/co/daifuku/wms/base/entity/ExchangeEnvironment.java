// $Id: ExchangeEnvironment.java 7201 2010-02-24 02:36:39Z kishimoto $
// $LastChangedRevision: 7201 $
package jp.co.daifuku.wms.base.entity;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;

import jp.co.daifuku.wms.handler.db.AbstractDBEntity;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreDefineHandler;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * EXCHANGEENVIRONMENTのエンティティクラスです。
 *
 * @version $Revision: 7201 $, $Date: 2010-02-24 11:36:39 +0900 (水, 24 2 2010) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */

public class ExchangeEnvironment
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * プレフィックス区分：プレフィックス
     */
    public static final String IS_PREFIX_TRUE = "0";

    /**
     * プレフィックス区分：ファイル名指定
     */
    public static final String IS_PREFIX_FALSE = "1";

    /**
     * エラーリスト自動発行：自動発行しない
     */
    public static final String ERROR_LIST_NOT_PRINT = "0";

    /**
     * エラーリスト自動発行：自動発行する
     */
    public static final String ERROR_LIST_AUTO_PRINT = "1";

    /** テーブル名定義 */
    public static final String STORE_NAME = "DMEXCHANGEENVIRONMENT" ;

    /*
     * テーブル名: DMEXCHANGEENVIRONMENT
     * 取込データ区分 :                JOB_TYPE            varchar2(2)
     * 送受信区分 :                    EXCHANGE_TYPE       varchar2(1)
     * 取込種別 :                      DATA_TYPE           varchar2(1)
     * 取込ファイル名 :                DATA_NAME           varchar2(30)
     * 報告単位 :                      REPORT_TYPE         varchar2(1)
     * ファイル交換フォルダパス :      FOLDER_NAME         varchar2(128)
     * ファイル名指定方法 :            IS_PREFIX           varchar2(1)
     * データID :                      DATA_ID             varchar2(16)
     * 拡張子 :                        EXTENTION           varchar2(8)
     * 取込可能行数 :                  MAX_RECORD          number
     * TimeKeeper定義ファイルパス :    TIME_KEEPER_PATH    varchar2(128)
     * クラス名 :                      CLASS_NAME          varchar2(128)
     * エラーリスト自動発行 :          AUTO_PRINT_ERROR_LISTvarchar2(1)
     */
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

    /** フィールド定義 (ファイル交換フォルダパス(<code>FOLDER_NAME</code>)) */
    public static final FieldName FOLDER_NAME = new FieldName(STORE_NAME, "FOLDER_NAME") ;

    /** フィールド定義 (ファイル名指定方法(<code>IS_PREFIX</code>)) */
    public static final FieldName IS_PREFIX = new FieldName(STORE_NAME, "IS_PREFIX") ;

    /** フィールド定義 (データID(<code>DATA_ID</code>)) */
    public static final FieldName DATA_ID = new FieldName(STORE_NAME, "DATA_ID") ;

    /** フィールド定義 (拡張子(<code>EXTENTION</code>)) */
    public static final FieldName EXTENTION = new FieldName(STORE_NAME, "EXTENTION") ;

    /** フィールド定義 (取込可能行数(<code>MAX_RECORD</code>)) */
    public static final FieldName MAX_RECORD = new FieldName(STORE_NAME, "MAX_RECORD") ;

    /** フィールド定義 (TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>)) */
    public static final FieldName TIME_KEEPER_PATH = new FieldName(STORE_NAME, "TIME_KEEPER_PATH") ;

    /** フィールド定義 (クラス名(<code>CLASS_NAME</code>)) */
    public static final FieldName CLASS_NAME = new FieldName(STORE_NAME, "CLASS_NAME") ;

    /** フィールド定義 (エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>)) */
    public static final FieldName AUTO_PRINT_ERROR_LIST = new FieldName(STORE_NAME, "AUTO_PRINT_ERROR_LIST") ;


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
    public ExchangeEnvironment()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

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
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>) に値をセットします。
     * @param value セットする値FOLDER_NAME
     */
    public void setFolderName(String value)  // @@GEN_V3@@
    {
        setValue(FOLDER_NAME, value);
    }

    /**
     * ファイル交換フォルダパス(<code>FOLDER_NAME</code>) から値を取得します。
     * @return FOLDER_NAME
     */
    public String getFolderName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(FOLDER_NAME, "")) ;
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>) に値をセットします。
     * @param value セットする値IS_PREFIX
     */
    public void setIsPrefix(String value)  // @@GEN_V3@@
    {
        setValue(IS_PREFIX, value);
    }

    /**
     * ファイル名指定方法(<code>IS_PREFIX</code>) から値を取得します。
     * @return IS_PREFIX
     */
    public String getIsPrefix()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(IS_PREFIX, "")) ;
    }

    /**
     * データID(<code>DATA_ID</code>) に値をセットします。
     * @param value セットする値DATA_ID
     */
    public void setDataId(String value)  // @@GEN_V3@@
    {
        setValue(DATA_ID, value);
    }

    /**
     * データID(<code>DATA_ID</code>) から値を取得します。
     * @return DATA_ID
     */
    public String getDataId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(DATA_ID, "")) ;
    }

    /**
     * 拡張子(<code>EXTENTION</code>) に値をセットします。
     * @param value セットする値EXTENTION
     */
    public void setExtention(String value)  // @@GEN_V3@@
    {
        setValue(EXTENTION, value);
    }

    /**
     * 拡張子(<code>EXTENTION</code>) から値を取得します。
     * @return EXTENTION
     */
    public String getExtention()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(EXTENTION, "")) ;
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>) に値をセットします。
     * @param value セットする値MAX_RECORD
     */
    public void setMaxRecord(int value)  // @@GEN_V3@@
    {
        setValue(MAX_RECORD, HandlerUtil.toObject(value));
    }

    /**
     * 取込可能行数(<code>MAX_RECORD</code>) から値を取得します。
     * @return MAX_RECORD
     */
    public int getMaxRecord()  // @@GEN_V3@@
    {
        return getBigDecimal(MAX_RECORD, BigDecimal.ZERO).intValue() ;
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>) に値をセットします。
     * @param value セットする値TIME_KEEPER_PATH
     */
    public void setTimeKeeperPath(String value)  // @@GEN_V3@@
    {
        setValue(TIME_KEEPER_PATH, value);
    }

    /**
     * TimeKeeper定義ファイルパス(<code>TIME_KEEPER_PATH</code>) から値を取得します。
     * @return TIME_KEEPER_PATH
     */
    public String getTimeKeeperPath()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TIME_KEEPER_PATH, "")) ;
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
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>) に値をセットします。
     * @param value セットする値AUTO_PRINT_ERROR_LIST
     */
    public void setAutoPrintErrorList(String value)  // @@GEN_V3@@
    {
        setValue(AUTO_PRINT_ERROR_LIST, value);
    }

    /**
     * エラーリスト自動発行(<code>AUTO_PRINT_ERROR_LIST</code>) から値を取得します。
     * @return AUTO_PRINT_ERROR_LIST
     */
    public String getAutoPrintErrorList()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(AUTO_PRINT_ERROR_LIST, "")) ;
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
        return "$Id: ExchangeEnvironment.java 7201 2010-02-24 02:36:39Z kishimoto $" ;
    }
}

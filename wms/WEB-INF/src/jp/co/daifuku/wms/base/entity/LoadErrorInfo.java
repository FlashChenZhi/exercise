// $Id: LoadErrorInfo.java 5356 2009-11-02 00:47:10Z okamura $
// $LastChangedRevision: 5356 $
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
 * LOADERRORINFOのエンティティクラスです。
 *
 * @version $Revision: 5356 $, $Date: 2009-11-02 09:47:10 +0900 (月, 02 11 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public class LoadErrorInfo
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNLOADERRORINFO" ;

    /*
     * テーブル名: DNLOADERRORINFO
     * 取込開始日時 :                  START_DATE          timestamp
     * 取込ファイル名 :                FILE_NAME           varchar2(40)
     * ファイル行No. :                 FILE_LINE_NO        number
     * 取込データ区分 :                JOB_TYPE            varchar2(2)
     * エラーレベル :                  ERROR_LEVEL         varchar2(1)
     * エラー区分 :                    ERROR_FLAG          varchar2(9)
     * 項目番号 :                      ITEM_NO             varchar2(16)
     * データ :                        DATA                varchar2(1024)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     */
    /** フィールド定義 (取込開始日時(<code>START_DATE</code>)) */
    public static final FieldName START_DATE = new FieldName(STORE_NAME, "START_DATE") ;

    /** フィールド定義 (取込ファイル名(<code>FILE_NAME</code>)) */
    public static final FieldName FILE_NAME = new FieldName(STORE_NAME, "FILE_NAME") ;

    /** フィールド定義 (ファイル行No.(<code>FILE_LINE_NO</code>)) */
    public static final FieldName FILE_LINE_NO = new FieldName(STORE_NAME, "FILE_LINE_NO") ;

    /** フィールド定義 (取込データ区分(<code>JOB_TYPE</code>)) */
    public static final FieldName JOB_TYPE = new FieldName(STORE_NAME, "JOB_TYPE") ;

    /** フィールド定義 (エラーレベル(<code>ERROR_LEVEL</code>)) */
    public static final FieldName ERROR_LEVEL = new FieldName(STORE_NAME, "ERROR_LEVEL") ;

    /** フィールド定義 (エラー区分(<code>ERROR_FLAG</code>)) */
    public static final FieldName ERROR_FLAG = new FieldName(STORE_NAME, "ERROR_FLAG") ;

    /** フィールド定義 (項目番号(<code>ITEM_NO</code>)) */
    public static final FieldName ITEM_NO = new FieldName(STORE_NAME, "ITEM_NO") ;

    /** フィールド定義 (データ(<code>DATA</code>)) */
    public static final FieldName DATA = new FieldName(STORE_NAME, "DATA") ;

    /** フィールド定義 (登録日時(<code>REGIST_DATE</code>)) */
    public static final FieldName REGIST_DATE = new FieldName(STORE_NAME, "REGIST_DATE") ;

    /** フィールド定義 (登録処理名(<code>REGIST_PNAME</code>)) */
    public static final FieldName REGIST_PNAME = new FieldName(STORE_NAME, "REGIST_PNAME") ;


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
    public LoadErrorInfo()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 取込開始日時(<code>START_DATE</code>) に値をセットします。
     * @param value セットする値START_DATE
     */
    public void setStartDate(Date value)  // @@GEN_V3@@
    {
        setValue(START_DATE, value);
    }

    /**
     * 取込開始日時(<code>START_DATE</code>) から値を取得します。
     * @return START_DATE
     */
    public Date getStartDate()  // @@GEN_V3@@
    {
        return (Date)getValue(START_DATE, null) ;
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
     * ファイル行No.(<code>FILE_LINE_NO</code>) に値をセットします。
     * @param value セットする値FILE_LINE_NO
     */
    public void setFileLineNo(int value)  // @@GEN_V3@@
    {
        setValue(FILE_LINE_NO, HandlerUtil.toObject(value));
    }

    /**
     * ファイル行No.(<code>FILE_LINE_NO</code>) から値を取得します。
     * @return FILE_LINE_NO
     */
    public int getFileLineNo()  // @@GEN_V3@@
    {
        return getBigDecimal(FILE_LINE_NO, BigDecimal.ZERO).intValue() ;
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
     * エラーレベル(<code>ERROR_LEVEL</code>) に値をセットします。
     * @param value セットする値ERROR_LEVEL
     */
    public void setErrorLevel(String value)  // @@GEN_V3@@
    {
        setValue(ERROR_LEVEL, value);
    }

    /**
     * エラーレベル(<code>ERROR_LEVEL</code>) から値を取得します。
     * @return ERROR_LEVEL
     */
    public String getErrorLevel()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ERROR_LEVEL, "")) ;
    }

    /**
     * エラー区分(<code>ERROR_FLAG</code>) に値をセットします。
     * @param value セットする値ERROR_FLAG
     */
    public void setErrorFlag(String value)  // @@GEN_V3@@
    {
        setValue(ERROR_FLAG, value);
    }

    /**
     * エラー区分(<code>ERROR_FLAG</code>) から値を取得します。
     * @return ERROR_FLAG
     */
    public String getErrorFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ERROR_FLAG, "")) ;
    }

    /**
     * 項目番号(<code>ITEM_NO</code>) に値をセットします。
     * @param value セットする値ITEM_NO
     */
    public void setItemNo(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_NO, value);
    }

    /**
     * 項目番号(<code>ITEM_NO</code>) から値を取得します。
     * @return ITEM_NO
     */
    public String getItemNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_NO, "")) ;
    }

    /**
     * データ(<code>DATA</code>) に値をセットします。
     * @param value セットする値DATA
     */
    public void setData(String value)  // @@GEN_V3@@
    {
        setValue(DATA, value);
    }

    /**
     * データ(<code>DATA</code>) から値を取得します。
     * @return DATA
     */
    public String getData()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(DATA, "")) ;
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
        return "$Id: LoadErrorInfo.java 5356 2009-11-02 00:47:10Z okamura $" ;
    }
}

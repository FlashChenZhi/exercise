// $Id: PrintHistory.java 5192 2009-10-20 08:52:45Z okamura $
// $LastChangedRevision: 5192 $
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
 * PRINTHISTORYのエンティティクラスです。
 *
 * @version $Revision: 5192 $, $Date: 2009-10-20 17:52:45 +0900 (火, 20 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public class PrintHistory
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNPRINTHISTORY" ;

    /*
     * テーブル名: DNPRINTHISTORY
     * ファイル名 :                    FILE_NAME           varchar2(64)
     * エクスポート定義XMLファイル名 : XML_FILE_NAME       varchar2(64)
     * 帳票名リソースキー :            LIST_RESOURCEKEY    varchar2(12)
     * 発行日時 :                      PRINT_DATE          date
     * 画面リソースキー :              PAGENAMERESOURCEKEY varchar2(40)
     * 端末No. :                       TERMINAL_NO         varchar2(4)
     */
    /** フィールド定義 (ファイル名(<code>FILE_NAME</code>)) */
    public static final FieldName FILE_NAME = new FieldName(STORE_NAME, "FILE_NAME") ;

    /** フィールド定義 (エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>)) */
    public static final FieldName XML_FILE_NAME = new FieldName(STORE_NAME, "XML_FILE_NAME") ;

    /** フィールド定義 (帳票名リソースキー(<code>LIST_RESOURCEKEY</code>)) */
    public static final FieldName LIST_RESOURCEKEY = new FieldName(STORE_NAME, "LIST_RESOURCEKEY") ;

    /** フィールド定義 (発行日時(<code>PRINT_DATE</code>)) */
    public static final FieldName PRINT_DATE = new FieldName(STORE_NAME, "PRINT_DATE") ;

    /** フィールド定義 (画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)) */
    public static final FieldName PAGENAMERESOURCEKEY = new FieldName(STORE_NAME, "PAGENAMERESOURCEKEY") ;

    /** フィールド定義 (端末No.(<code>TERMINAL_NO</code>)) */
    public static final FieldName TERMINAL_NO = new FieldName(STORE_NAME, "TERMINAL_NO") ;


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
    public PrintHistory()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * ファイル名(<code>FILE_NAME</code>) に値をセットします。
     * @param value セットする値FILE_NAME
     */
    public void setFileName(String value)  // @@GEN_V3@@
    {
        setValue(FILE_NAME, value);
    }

    /**
     * ファイル名(<code>FILE_NAME</code>) から値を取得します。
     * @return FILE_NAME
     */
    public String getFileName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(FILE_NAME, "")) ;
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>) に値をセットします。
     * @param value セットする値XML_FILE_NAME
     */
    public void setXmlFileName(String value)  // @@GEN_V3@@
    {
        setValue(XML_FILE_NAME, value);
    }

    /**
     * エクスポート定義XMLファイル名(<code>XML_FILE_NAME</code>) から値を取得します。
     * @return XML_FILE_NAME
     */
    public String getXmlFileName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(XML_FILE_NAME, "")) ;
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>) に値をセットします。
     * @param value セットする値LIST_RESOURCEKEY
     */
    public void setListResourcekey(String value)  // @@GEN_V3@@
    {
        setValue(LIST_RESOURCEKEY, value);
    }

    /**
     * 帳票名リソースキー(<code>LIST_RESOURCEKEY</code>) から値を取得します。
     * @return LIST_RESOURCEKEY
     */
    public String getListResourcekey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LIST_RESOURCEKEY, "")) ;
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>) に値をセットします。
     * @param value セットする値PRINT_DATE
     */
    public void setPrintDate(Date value)  // @@GEN_V3@@
    {
        setValue(PRINT_DATE, value);
    }

    /**
     * 発行日時(<code>PRINT_DATE</code>) から値を取得します。
     * @return PRINT_DATE
     */
    public Date getPrintDate()  // @@GEN_V3@@
    {
        return (Date)getValue(PRINT_DATE, null) ;
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>) に値をセットします。
     * @param value セットする値PAGENAMERESOURCEKEY
     */
    public void setPagenameResourcekey(String value)  // @@GEN_V3@@
    {
        setValue(PAGENAMERESOURCEKEY, value);
    }

    /**
     * 画面リソースキー(<code>PAGENAMERESOURCEKEY</code>) から値を取得します。
     * @return PAGENAMERESOURCEKEY
     */
    public String getPagenameResourcekey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PAGENAMERESOURCEKEY, "")) ;
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
        return "$Id: PrintHistory.java 5192 2009-10-20 08:52:45Z okamura $" ;
    }
}

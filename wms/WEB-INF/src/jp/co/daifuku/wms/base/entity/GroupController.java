// $Id: GroupController.java 5425 2009-11-06 10:33:25Z okayama $
// $LastChangedRevision: 5425 $
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
 * GROUPCONTROLLERのエンティティクラスです。
 *
 * @version $Revision: 5425 $, $Date: 2009-11-06 19:33:25 +0900 (金, 06 11 2009) $
 * @author  ss
 * @author  Last commit: $Author: okayama $
 */

public class GroupController
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DMGROUPCONTROLLER" ;

    /*
     * テーブル名: DMGROUPCONTROLLER
     * コントローラー番号 :            CONTROLLER_NO       varchar2(3)
     * 状態 :                          STATUS_FLAG         varchar2(1)
     * IPアドレス :                    IPADDRESS           varchar2(64)
     * ポート番号 :                    PORT                number
     * 作業終了応答受信日時 :          RECV_DATE           timestamp
     */
    /** フィールド定義 (コントローラー番号(<code>CONTROLLER_NO</code>)) */
    public static final FieldName CONTROLLER_NO = new FieldName(STORE_NAME, "CONTROLLER_NO") ;

    /** フィールド定義 (状態(<code>STATUS_FLAG</code>)) */
    public static final FieldName STATUS_FLAG = new FieldName(STORE_NAME, "STATUS_FLAG") ;

    /** フィールド定義 (IPアドレス(<code>IPADDRESS</code>)) */
    public static final FieldName IPADDRESS = new FieldName(STORE_NAME, "IPADDRESS") ;

    /** フィールド定義 (ポート番号(<code>PORT</code>)) */
    public static final FieldName PORT = new FieldName(STORE_NAME, "PORT") ;

    /** フィールド定義 (作業終了応答受信日時(<code>RECV_DATE</code>)) */
    public static final FieldName RECV_DATE = new FieldName(STORE_NAME, "RECV_DATE") ;


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
    public GroupController()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>) に値をセットします。
     * @param value セットする値CONTROLLER_NO
     */
    public void setControllerNo(String value)  // @@GEN_V3@@
    {
        setValue(CONTROLLER_NO, value);
    }

    /**
     * コントローラー番号(<code>CONTROLLER_NO</code>) から値を取得します。
     * @return CONTROLLER_NO
     */
    public String getControllerNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONTROLLER_NO, "")) ;
    }

    /**
     * 状態(<code>STATUS_FLAG</code>) に値をセットします。
     * @param value セットする値STATUS_FLAG
     */
    public void setStatusFlag(String value)  // @@GEN_V3@@
    {
        setValue(STATUS_FLAG, value);
    }

    /**
     * 状態(<code>STATUS_FLAG</code>) から値を取得します。
     * @return STATUS_FLAG
     */
    public String getStatusFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATUS_FLAG, "")) ;
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>) に値をセットします。
     * @param value セットする値IPADDRESS
     */
    public void setIpaddress(String value)  // @@GEN_V3@@
    {
        setValue(IPADDRESS, value);
    }

    /**
     * IPアドレス(<code>IPADDRESS</code>) から値を取得します。
     * @return IPADDRESS
     */
    public String getIpaddress()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(IPADDRESS, "")) ;
    }

    /**
     * ポート番号(<code>PORT</code>) に値をセットします。
     * @param value セットする値PORT
     */
    public void setPort(int value)  // @@GEN_V3@@
    {
        setValue(PORT, HandlerUtil.toObject(value));
    }

    /**
     * ポート番号(<code>PORT</code>) から値を取得します。
     * @return PORT
     */
    public int getPort()  // @@GEN_V3@@
    {
        return getBigDecimal(PORT, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>) に値をセットします。
     * @param value セットする値RECV_DATE
     */
    public void setRecvDate(Date value)  // @@GEN_V3@@
    {
        setValue(RECV_DATE, value);
    }

    /**
     * 作業終了応答受信日時(<code>RECV_DATE</code>) から値を取得します。
     * @return RECV_DATE
     */
    public Date getRecvDate()  // @@GEN_V3@@
    {
        return (Date)getValue(RECV_DATE, null) ;
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
        return "$Id: GroupController.java 5425 2009-11-06 10:33:25Z okayama $" ;
    }
}

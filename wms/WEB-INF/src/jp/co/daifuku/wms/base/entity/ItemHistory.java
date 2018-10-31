// $Id: ItemHistory.java 7591 2010-03-15 13:51:03Z kishimoto $
// $LastChangedRevision: 7591 $
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
 * ITEMHISTORYのエンティティクラスです。
 *
 * @version $Revision: 7591 $, $Date: 2010-03-15 22:51:03 +0900 (月, 15 3 2010) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */

public class ItemHistory
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNITEMHISTORY" ;

    /*
     * テーブル名: DNITEMHISTORY
     * 出力日時 :                      LOG_DATE            timestamp
     * 出力日時(GMT) :                 LOG_DATE_GMT        timestamp
     * ユーザID :                      USER_ID             varchar2(20)
     * ユーザ名称 :                    USER_NAME           varchar2(40)
     * 端末No、RFTNo :                 TERMINAL_NO         varchar2(4)
     * 端末名称 :                      TERMINAL_NAME       varchar2(60)
     * IPアドレス :                    IP_ADDRESS          varchar2(64)
     * DS番号 :                        DS_NO               varchar2(6)
     * 画面リソースキー :              PAGENAMERESOURCEKEY varchar2(40)
     * 更新区分 :                      UPDATE_KIND         varchar2(1)
     * システム管理区分 :              MANAGEMENT_TYPE     varchar2(1)
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 商品コード :                    ITEM_CODE           varchar2(40)
     * 商品名称 :                      ITEM_NAME           varchar2(100)
     * ソフトゾーン :                  SOFT_ZONE_ID        varchar2(3)
     * JANコード :                     JAN                 varchar2(13)
     * ケースITF :                     ITF                 varchar2(16)
     * ボールITF :                     BUNDLE_ITF          varchar2(16)
     * ケース入数 :                    ENTERING_QTY        number
     * ボール入数 :                    BUNDLE_ENTERING_QTY number
     * 上限在庫数 :                    UPPER_QTY           number
     * 下限在庫数 :                    LOWER_QTY           number
     * 把持区分 :                      HOLD_TYPE           varchar2(1)
     * 一時商品区分 :                  TEMPORARY_TYPE      varchar2(1)
     * 修正後商品名称 :                UPDATE_ITEM_NAME    varchar2(40)
     * 修正後ソフトゾーン :            UPDATE_SOFT_ZONE_ID varchar2(3)
     * 修正後JANコード :               UPDATE_JAN          varchar2(13)
     * 修正後ケースITF :               UPDATE_ITF          varchar2(16)
     * 修正後ケース入数 :              UPDATE_ENTERING_QTY number
     * 修正後上限在庫数 :              UPDATE_UPPER_QTY    number
     * 修正後下限在庫数 :              UPDATE_LOWER_QTY    number
     * 修正後把持区分 :                UPDATE_HOLD_TYPE    varchar2(1)
     * 修正後一時商品区分 :            UPDATE_TEMPORARY_TYPEvarchar2(1)
     */
    /** フィールド定義 (出力日時(<code>LOG_DATE</code>)) */
    public static final FieldName LOG_DATE = new FieldName(STORE_NAME, "LOG_DATE") ;

    /** フィールド定義 (出力日時(GMT)(<code>LOG_DATE_GMT</code>)) */
    public static final FieldName LOG_DATE_GMT = new FieldName(STORE_NAME, "LOG_DATE_GMT") ;

    /** フィールド定義 (ユーザID(<code>USER_ID</code>)) */
    public static final FieldName USER_ID = new FieldName(STORE_NAME, "USER_ID") ;

    /** フィールド定義 (ユーザ名称(<code>USER_NAME</code>)) */
    public static final FieldName USER_NAME = new FieldName(STORE_NAME, "USER_NAME") ;

    /** フィールド定義 (端末No、RFTNo(<code>TERMINAL_NO</code>)) */
    public static final FieldName TERMINAL_NO = new FieldName(STORE_NAME, "TERMINAL_NO") ;

    /** フィールド定義 (端末名称(<code>TERMINAL_NAME</code>)) */
    public static final FieldName TERMINAL_NAME = new FieldName(STORE_NAME, "TERMINAL_NAME") ;

    /** フィールド定義 (IPアドレス(<code>IP_ADDRESS</code>)) */
    public static final FieldName IP_ADDRESS = new FieldName(STORE_NAME, "IP_ADDRESS") ;

    /** フィールド定義 (DS番号(<code>DS_NO</code>)) */
    public static final FieldName DS_NO = new FieldName(STORE_NAME, "DS_NO") ;

    /** フィールド定義 (画面リソースキー(<code>PAGENAMERESOURCEKEY</code>)) */
    public static final FieldName PAGENAMERESOURCEKEY = new FieldName(STORE_NAME, "PAGENAMERESOURCEKEY") ;

    /** フィールド定義 (更新区分(<code>UPDATE_KIND</code>)) */
    public static final FieldName UPDATE_KIND = new FieldName(STORE_NAME, "UPDATE_KIND") ;

    /** フィールド定義 (システム管理区分(<code>MANAGEMENT_TYPE</code>)) */
    public static final FieldName MANAGEMENT_TYPE = new FieldName(STORE_NAME, "MANAGEMENT_TYPE") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE") ;

    /** フィールド定義 (商品名称(<code>ITEM_NAME</code>)) */
    public static final FieldName ITEM_NAME = new FieldName(STORE_NAME, "ITEM_NAME") ;

    /** フィールド定義 (ソフトゾーン(<code>SOFT_ZONE_ID</code>)) */
    public static final FieldName SOFT_ZONE_ID = new FieldName(STORE_NAME, "SOFT_ZONE_ID") ;

    /** フィールド定義 (JANコード(<code>JAN</code>)) */
    public static final FieldName JAN = new FieldName(STORE_NAME, "JAN") ;

    /** フィールド定義 (ケースITF(<code>ITF</code>)) */
    public static final FieldName ITF = new FieldName(STORE_NAME, "ITF") ;

    /** フィールド定義 (ボールITF(<code>BUNDLE_ITF</code>)) */
    public static final FieldName BUNDLE_ITF = new FieldName(STORE_NAME, "BUNDLE_ITF") ;

    /** フィールド定義 (ケース入数(<code>ENTERING_QTY</code>)) */
    public static final FieldName ENTERING_QTY = new FieldName(STORE_NAME, "ENTERING_QTY") ;

    /** フィールド定義 (ボール入数(<code>BUNDLE_ENTERING_QTY</code>)) */
    public static final FieldName BUNDLE_ENTERING_QTY = new FieldName(STORE_NAME, "BUNDLE_ENTERING_QTY") ;

    /** フィールド定義 (上限在庫数(<code>UPPER_QTY</code>)) */
    public static final FieldName UPPER_QTY = new FieldName(STORE_NAME, "UPPER_QTY") ;

    /** フィールド定義 (下限在庫数(<code>LOWER_QTY</code>)) */
    public static final FieldName LOWER_QTY = new FieldName(STORE_NAME, "LOWER_QTY") ;

    /** フィールド定義 (把持区分(<code>HOLD_TYPE</code>)) */
    public static final FieldName HOLD_TYPE = new FieldName(STORE_NAME, "HOLD_TYPE") ;

    /** フィールド定義 (一時商品区分(<code>TEMPORARY_TYPE</code>)) */
    public static final FieldName TEMPORARY_TYPE = new FieldName(STORE_NAME, "TEMPORARY_TYPE") ;

    /** フィールド定義 (修正後商品名称(<code>UPDATE_ITEM_NAME</code>)) */
    public static final FieldName UPDATE_ITEM_NAME = new FieldName(STORE_NAME, "UPDATE_ITEM_NAME") ;

    /** フィールド定義 (修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>)) */
    public static final FieldName UPDATE_SOFT_ZONE_ID = new FieldName(STORE_NAME, "UPDATE_SOFT_ZONE_ID") ;

    /** フィールド定義 (修正後JANコード(<code>UPDATE_JAN</code>)) */
    public static final FieldName UPDATE_JAN = new FieldName(STORE_NAME, "UPDATE_JAN") ;

    /** フィールド定義 (修正後ケースITF(<code>UPDATE_ITF</code>)) */
    public static final FieldName UPDATE_ITF = new FieldName(STORE_NAME, "UPDATE_ITF") ;

    /** フィールド定義 (修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>)) */
    public static final FieldName UPDATE_ENTERING_QTY = new FieldName(STORE_NAME, "UPDATE_ENTERING_QTY") ;

    /** フィールド定義 (修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>)) */
    public static final FieldName UPDATE_UPPER_QTY = new FieldName(STORE_NAME, "UPDATE_UPPER_QTY") ;

    /** フィールド定義 (修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>)) */
    public static final FieldName UPDATE_LOWER_QTY = new FieldName(STORE_NAME, "UPDATE_LOWER_QTY") ;

    /** フィールド定義 (修正後把持区分(<code>UPDATE_HOLD_TYPE</code>)) */
    public static final FieldName UPDATE_HOLD_TYPE = new FieldName(STORE_NAME, "UPDATE_HOLD_TYPE") ;

    /** フィールド定義 (修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>)) */
    public static final FieldName UPDATE_TEMPORARY_TYPE = new FieldName(STORE_NAME, "UPDATE_TEMPORARY_TYPE") ;


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
    public ItemHistory()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 出力日時(<code>LOG_DATE</code>) に値をセットします。
     * @param value セットする値LOG_DATE
     */
    public void setLogDate(Date value)  // @@GEN_V3@@
    {
        setValue(LOG_DATE, value);
    }

    /**
     * 出力日時(<code>LOG_DATE</code>) から値を取得します。
     * @return LOG_DATE
     */
    public Date getLogDate()  // @@GEN_V3@@
    {
        return (Date)getValue(LOG_DATE, null) ;
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>) に値をセットします。
     * @param value セットする値LOG_DATE_GMT
     */
    public void setLogDateGmt(Date value)  // @@GEN_V3@@
    {
        setValue(LOG_DATE_GMT, value);
    }

    /**
     * 出力日時(GMT)(<code>LOG_DATE_GMT</code>) から値を取得します。
     * @return LOG_DATE_GMT
     */
    public Date getLogDateGmt()  // @@GEN_V3@@
    {
        return (Date)getValue(LOG_DATE_GMT, null) ;
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
     * ユーザ名称(<code>USER_NAME</code>) に値をセットします。
     * @param value セットする値USER_NAME
     */
    public void setUserName(String value)  // @@GEN_V3@@
    {
        setValue(USER_NAME, value);
    }

    /**
     * ユーザ名称(<code>USER_NAME</code>) から値を取得します。
     * @return USER_NAME
     */
    public String getUserName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USER_NAME, "")) ;
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値TERMINAL_NO
     */
    public void setTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_NO, value);
    }

    /**
     * 端末No、RFTNo(<code>TERMINAL_NO</code>) から値を取得します。
     * @return TERMINAL_NO
     */
    public String getTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_NO, "")) ;
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>) に値をセットします。
     * @param value セットする値TERMINAL_NAME
     */
    public void setTerminalName(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_NAME, value);
    }

    /**
     * 端末名称(<code>TERMINAL_NAME</code>) から値を取得します。
     * @return TERMINAL_NAME
     */
    public String getTerminalName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_NAME, "")) ;
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>) に値をセットします。
     * @param value セットする値IP_ADDRESS
     */
    public void setIpAddress(String value)  // @@GEN_V3@@
    {
        setValue(IP_ADDRESS, value);
    }

    /**
     * IPアドレス(<code>IP_ADDRESS</code>) から値を取得します。
     * @return IP_ADDRESS
     */
    public String getIpAddress()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(IP_ADDRESS, "")) ;
    }

    /**
     * DS番号(<code>DS_NO</code>) に値をセットします。
     * @param value セットする値DS_NO
     */
    public void setDsNo(String value)  // @@GEN_V3@@
    {
        setValue(DS_NO, value);
    }

    /**
     * DS番号(<code>DS_NO</code>) から値を取得します。
     * @return DS_NO
     */
    public String getDsNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(DS_NO, "")) ;
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
     * 更新区分(<code>UPDATE_KIND</code>) に値をセットします。
     * @param value セットする値UPDATE_KIND
     */
    public void setUpdateKind(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_KIND, value);
    }

    /**
     * 更新区分(<code>UPDATE_KIND</code>) から値を取得します。
     * @return UPDATE_KIND
     */
    public String getUpdateKind()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_KIND, "")) ;
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>) に値をセットします。
     * @param value セットする値MANAGEMENT_TYPE
     */
    public void setManagementType(String value)  // @@GEN_V3@@
    {
        setValue(MANAGEMENT_TYPE, value);
    }

    /**
     * システム管理区分(<code>MANAGEMENT_TYPE</code>) から値を取得します。
     * @return MANAGEMENT_TYPE
     */
    public String getManagementType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(MANAGEMENT_TYPE, "")) ;
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
     * 商品名称(<code>ITEM_NAME</code>) に値をセットします。
     * @param value セットする値ITEM_NAME
     */
    public void setItemName(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_NAME, value);
    }

    /**
     * 商品名称(<code>ITEM_NAME</code>) から値を取得します。
     * @return ITEM_NAME
     */
    public String getItemName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_NAME, "")) ;
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>) に値をセットします。
     * @param value セットする値SOFT_ZONE_ID
     */
    public void setSoftZoneId(String value)  // @@GEN_V3@@
    {
        setValue(SOFT_ZONE_ID, value);
    }

    /**
     * ソフトゾーン(<code>SOFT_ZONE_ID</code>) から値を取得します。
     * @return SOFT_ZONE_ID
     */
    public String getSoftZoneId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SOFT_ZONE_ID, "")) ;
    }

    /**
     * JANコード(<code>JAN</code>) に値をセットします。
     * @param value セットする値JAN
     */
    public void setJan(String value)  // @@GEN_V3@@
    {
        setValue(JAN, value);
    }

    /**
     * JANコード(<code>JAN</code>) から値を取得します。
     * @return JAN
     */
    public String getJan()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(JAN, "")) ;
    }

    /**
     * ケースITF(<code>ITF</code>) に値をセットします。
     * @param value セットする値ITF
     */
    public void setItf(String value)  // @@GEN_V3@@
    {
        setValue(ITF, value);
    }

    /**
     * ケースITF(<code>ITF</code>) から値を取得します。
     * @return ITF
     */
    public String getItf()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITF, "")) ;
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>) に値をセットします。
     * @param value セットする値BUNDLE_ITF
     */
    public void setBundleItf(String value)  // @@GEN_V3@@
    {
        setValue(BUNDLE_ITF, value);
    }

    /**
     * ボールITF(<code>BUNDLE_ITF</code>) から値を取得します。
     * @return BUNDLE_ITF
     */
    public String getBundleItf()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(BUNDLE_ITF, "")) ;
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>) に値をセットします。
     * @param value セットする値ENTERING_QTY
     */
    public void setEnteringQty(int value)  // @@GEN_V3@@
    {
        setValue(ENTERING_QTY, HandlerUtil.toObject(value));
    }

    /**
     * ケース入数(<code>ENTERING_QTY</code>) から値を取得します。
     * @return ENTERING_QTY
     */
    public int getEnteringQty()  // @@GEN_V3@@
    {
        return getBigDecimal(ENTERING_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>) に値をセットします。
     * @param value セットする値BUNDLE_ENTERING_QTY
     */
    public void setBundleEnteringQty(int value)  // @@GEN_V3@@
    {
        setValue(BUNDLE_ENTERING_QTY, HandlerUtil.toObject(value));
    }

    /**
     * ボール入数(<code>BUNDLE_ENTERING_QTY</code>) から値を取得します。
     * @return BUNDLE_ENTERING_QTY
     */
    public int getBundleEnteringQty()  // @@GEN_V3@@
    {
        return getBigDecimal(BUNDLE_ENTERING_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>) に値をセットします。
     * @param value セットする値UPPER_QTY
     */
    public void setUpperQty(int value)  // @@GEN_V3@@
    {
        setValue(UPPER_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 上限在庫数(<code>UPPER_QTY</code>) から値を取得します。
     * @return UPPER_QTY
     */
    public int getUpperQty()  // @@GEN_V3@@
    {
        return getBigDecimal(UPPER_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>) に値をセットします。
     * @param value セットする値LOWER_QTY
     */
    public void setLowerQty(int value)  // @@GEN_V3@@
    {
        setValue(LOWER_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 下限在庫数(<code>LOWER_QTY</code>) から値を取得します。
     * @return LOWER_QTY
     */
    public int getLowerQty()  // @@GEN_V3@@
    {
        return getBigDecimal(LOWER_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 把持区分(<code>HOLD_TYPE</code>) に値をセットします。
     * @param value セットする値HOLD_TYPE
     */
    public void setHoldType(String value)  // @@GEN_V3@@
    {
        setValue(HOLD_TYPE, value);
    }

    /**
     * 把持区分(<code>HOLD_TYPE</code>) から値を取得します。
     * @return HOLD_TYPE
     */
    public String getHoldType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(HOLD_TYPE, "")) ;
    }

    /**
     * 一時商品区分(<code>TEMPORARY_TYPE</code>) に値をセットします。
     * @param value セットする値TEMPORARY_TYPE
     */
    public void setTemporaryType(String value)  // @@GEN_V3@@
    {
        setValue(TEMPORARY_TYPE, value);
    }

    /**
     * 一時商品区分(<code>TEMPORARY_TYPE</code>) から値を取得します。
     * @return TEMPORARY_TYPE
     */
    public String getTemporaryType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TEMPORARY_TYPE, "")) ;
    }

    /**
     * 修正後商品名称(<code>UPDATE_ITEM_NAME</code>) に値をセットします。
     * @param value セットする値UPDATE_ITEM_NAME
     */
    public void setUpdateItemName(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_ITEM_NAME, value);
    }

    /**
     * 修正後商品名称(<code>UPDATE_ITEM_NAME</code>) から値を取得します。
     * @return UPDATE_ITEM_NAME
     */
    public String getUpdateItemName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_ITEM_NAME, "")) ;
    }

    /**
     * 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>) に値をセットします。
     * @param value セットする値UPDATE_SOFT_ZONE_ID
     */
    public void setUpdateSoftZoneId(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_SOFT_ZONE_ID, value);
    }

    /**
     * 修正後ソフトゾーン(<code>UPDATE_SOFT_ZONE_ID</code>) から値を取得します。
     * @return UPDATE_SOFT_ZONE_ID
     */
    public String getUpdateSoftZoneId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_SOFT_ZONE_ID, "")) ;
    }

    /**
     * 修正後JANコード(<code>UPDATE_JAN</code>) に値をセットします。
     * @param value セットする値UPDATE_JAN
     */
    public void setUpdateJan(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_JAN, value);
    }

    /**
     * 修正後JANコード(<code>UPDATE_JAN</code>) から値を取得します。
     * @return UPDATE_JAN
     */
    public String getUpdateJan()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_JAN, "")) ;
    }

    /**
     * 修正後ケースITF(<code>UPDATE_ITF</code>) に値をセットします。
     * @param value セットする値UPDATE_ITF
     */
    public void setUpdateItf(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_ITF, value);
    }

    /**
     * 修正後ケースITF(<code>UPDATE_ITF</code>) から値を取得します。
     * @return UPDATE_ITF
     */
    public String getUpdateItf()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_ITF, "")) ;
    }

    /**
     * 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>) に値をセットします。
     * @param value セットする値UPDATE_ENTERING_QTY
     */
    public void setUpdateEnteringQty(int value)  // @@GEN_V3@@
    {
        setValue(UPDATE_ENTERING_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 修正後ケース入数(<code>UPDATE_ENTERING_QTY</code>) から値を取得します。
     * @return UPDATE_ENTERING_QTY
     */
    public int getUpdateEnteringQty()  // @@GEN_V3@@
    {
        return getBigDecimal(UPDATE_ENTERING_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>) に値をセットします。
     * @param value セットする値UPDATE_UPPER_QTY
     */
    public void setUpdateUpperQty(int value)  // @@GEN_V3@@
    {
        setValue(UPDATE_UPPER_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 修正後上限在庫数(<code>UPDATE_UPPER_QTY</code>) から値を取得します。
     * @return UPDATE_UPPER_QTY
     */
    public int getUpdateUpperQty()  // @@GEN_V3@@
    {
        return getBigDecimal(UPDATE_UPPER_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>) に値をセットします。
     * @param value セットする値UPDATE_LOWER_QTY
     */
    public void setUpdateLowerQty(int value)  // @@GEN_V3@@
    {
        setValue(UPDATE_LOWER_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 修正後下限在庫数(<code>UPDATE_LOWER_QTY</code>) から値を取得します。
     * @return UPDATE_LOWER_QTY
     */
    public int getUpdateLowerQty()  // @@GEN_V3@@
    {
        return getBigDecimal(UPDATE_LOWER_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>) に値をセットします。
     * @param value セットする値UPDATE_HOLD_TYPE
     */
    public void setUpdateHoldType(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_HOLD_TYPE, value);
    }

    /**
     * 修正後把持区分(<code>UPDATE_HOLD_TYPE</code>) から値を取得します。
     * @return UPDATE_HOLD_TYPE
     */
    public String getUpdateHoldType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_HOLD_TYPE, "")) ;
    }

    /**
     * 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>) に値をセットします。
     * @param value セットする値UPDATE_TEMPORARY_TYPE
     */
    public void setUpdateTemporaryType(String value)  // @@GEN_V3@@
    {
        setValue(UPDATE_TEMPORARY_TYPE, value);
    }

    /**
     * 修正後一時商品区分(<code>UPDATE_TEMPORARY_TYPE</code>) から値を取得します。
     * @return UPDATE_TEMPORARY_TYPE
     */
    public String getUpdateTemporaryType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(UPDATE_TEMPORARY_TYPE, "")) ;
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
        return "$Id: ItemHistory.java 7591 2010-03-15 13:51:03Z kishimoto $" ;
    }
}

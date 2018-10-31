// $Id: PCTOperationLog.java 3213 2009-03-02 06:59:20Z arai $
// $LastChangedRevision: 3213 $
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
 * PCTOPERATIONLOGのエンティティクラスです。
 *
 * @version $Revision: 3213 $, $Date: 2009-03-02 15:59:20 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */

public class PCTOperationLog
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNPCTOPERATIONLOG" ;

    /*
     * テーブル名: DNPCTOPERATIONLOG
     * 出力日時 :                      LOG_DATE            timestamp
     * 出力日時(GMT) :                 LOG_DATE_GMT        timestamp
     * ユーザID :                      USER_ID             varchar2(20)
     * ユーザ名 :                      USER_NAME           varchar2(40)
     * 端末No. :                       TERMINAL_NUMBER     varchar2(4)
     * 端末名 :                        TERMINAL_NAME       varchar2(60)
     * IPアドレス :                    IPADDRESS           varchar2(15)
     * DS番号 :                        DS_NO               varchar2(6)
     * ページ名リソースキー :          PAGENAMERESOURCEKEY varchar2(40)
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告) :OPERATION_TYPE      number
     * 詳細 :                          DETAIL              varchar2(500)
     * 項目1 :                         ITEM_1              varchar2(128)
     * 項目2 :                         ITEM_2              varchar2(128)
     * 項目3 :                         ITEM_3              varchar2(128)
     * 項目4 :                         ITEM_4              varchar2(128)
     * 項目5 :                         ITEM_5              varchar2(128)
     * 項目6 :                         ITEM_6              varchar2(128)
     * 項目7 :                         ITEM_7              varchar2(128)
     * 項目8 :                         ITEM_8              varchar2(128)
     * 項目9 :                         ITEM_9              varchar2(128)
     * 項目10 :                        ITEM_10             varchar2(128)
     * 項目11 :                        ITEM_11             varchar2(128)
     * 項目12 :                        ITEM_12             varchar2(128)
     * 項目13 :                        ITEM_13             varchar2(128)
     * 項目14 :                        ITEM_14             varchar2(128)
     * 項目15 :                        ITEM_15             varchar2(128)
     * 項目16 :                        ITEM_16             varchar2(128)
     * 項目17 :                        ITEM_17             varchar2(128)
     * 項目18 :                        ITEM_18             varchar2(128)
     * 項目19 :                        ITEM_19             varchar2(128)
     * 項目20 :                        ITEM_20             varchar2(128)
     * 項目21 :                        ITEM_21             varchar2(128)
     * 項目22 :                        ITEM_22             varchar2(128)
     * 項目23 :                        ITEM_23             varchar2(128)
     * 項目24 :                        ITEM_24             varchar2(128)
     * 項目25 :                        ITEM_25             varchar2(128)
     * 項目26 :                        ITEM_26             varchar2(128)
     * 項目27 :                        ITEM_27             varchar2(128)
     * 項目28 :                        ITEM_28             varchar2(128)
     * 項目29 :                        ITEM_29             varchar2(128)
     * 項目30 :                        ITEM_30             varchar2(128)
     * 項目31 :                        ITEM_31             varchar2(128)
     * 項目32 :                        ITEM_32             varchar2(128)
     * 項目33 :                        ITEM_33             varchar2(128)
     * 項目34 :                        ITEM_34             varchar2(128)
     * 項目35 :                        ITEM_35             varchar2(128)
     * 項目36 :                        ITEM_36             varchar2(128)
     * 項目37 :                        ITEM_37             varchar2(128)
     * 項目38 :                        ITEM_38             varchar2(128)
     * 項目39 :                        ITEM_39             varchar2(128)
     * 項目40 :                        ITEM_40             varchar2(128)
     */
    /** フィールド定義 (出力日時(<code>LOG_DATE</code>)) */
    public static final FieldName LOG_DATE = new FieldName(STORE_NAME, "LOG_DATE") ;

    /** フィールド定義 (出力日時(GMT)(<code>LOG_DATE_GMT</code>)) */
    public static final FieldName LOG_DATE_GMT = new FieldName(STORE_NAME, "LOG_DATE_GMT") ;

    /** フィールド定義 (ユーザID(<code>USER_ID</code>)) */
    public static final FieldName USER_ID = new FieldName(STORE_NAME, "USER_ID") ;

    /** フィールド定義 (ユーザ名(<code>USER_NAME</code>)) */
    public static final FieldName USER_NAME = new FieldName(STORE_NAME, "USER_NAME") ;

    /** フィールド定義 (端末No.(<code>TERMINAL_NUMBER</code>)) */
    public static final FieldName TERMINAL_NUMBER = new FieldName(STORE_NAME, "TERMINAL_NUMBER") ;

    /** フィールド定義 (端末名(<code>TERMINAL_NAME</code>)) */
    public static final FieldName TERMINAL_NAME = new FieldName(STORE_NAME, "TERMINAL_NAME") ;

    /** フィールド定義 (IPアドレス(<code>IPADDRESS</code>)) */
    public static final FieldName IPADDRESS = new FieldName(STORE_NAME, "IPADDRESS") ;

    /** フィールド定義 (DS番号(<code>DS_NO</code>)) */
    public static final FieldName DS_NO = new FieldName(STORE_NAME, "DS_NO") ;

    /** フィールド定義 (ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>)) */
    public static final FieldName PAGENAMERESOURCEKEY = new FieldName(STORE_NAME, "PAGENAMERESOURCEKEY") ;

    /** フィールド定義 (操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>)) */
    public static final FieldName OPERATION_TYPE = new FieldName(STORE_NAME, "OPERATION_TYPE") ;

    /** フィールド定義 (詳細(<code>DETAIL</code>)) */
    public static final FieldName DETAIL = new FieldName(STORE_NAME, "DETAIL") ;

    /** フィールド定義 (項目1(<code>ITEM_1</code>)) */
    public static final FieldName ITEM_1 = new FieldName(STORE_NAME, "ITEM_1") ;

    /** フィールド定義 (項目2(<code>ITEM_2</code>)) */
    public static final FieldName ITEM_2 = new FieldName(STORE_NAME, "ITEM_2") ;

    /** フィールド定義 (項目3(<code>ITEM_3</code>)) */
    public static final FieldName ITEM_3 = new FieldName(STORE_NAME, "ITEM_3") ;

    /** フィールド定義 (項目4(<code>ITEM_4</code>)) */
    public static final FieldName ITEM_4 = new FieldName(STORE_NAME, "ITEM_4") ;

    /** フィールド定義 (項目5(<code>ITEM_5</code>)) */
    public static final FieldName ITEM_5 = new FieldName(STORE_NAME, "ITEM_5") ;

    /** フィールド定義 (項目6(<code>ITEM_6</code>)) */
    public static final FieldName ITEM_6 = new FieldName(STORE_NAME, "ITEM_6") ;

    /** フィールド定義 (項目7(<code>ITEM_7</code>)) */
    public static final FieldName ITEM_7 = new FieldName(STORE_NAME, "ITEM_7") ;

    /** フィールド定義 (項目8(<code>ITEM_8</code>)) */
    public static final FieldName ITEM_8 = new FieldName(STORE_NAME, "ITEM_8") ;

    /** フィールド定義 (項目9(<code>ITEM_9</code>)) */
    public static final FieldName ITEM_9 = new FieldName(STORE_NAME, "ITEM_9") ;

    /** フィールド定義 (項目10(<code>ITEM_10</code>)) */
    public static final FieldName ITEM_10 = new FieldName(STORE_NAME, "ITEM_10") ;

    /** フィールド定義 (項目11(<code>ITEM_11</code>)) */
    public static final FieldName ITEM_11 = new FieldName(STORE_NAME, "ITEM_11") ;

    /** フィールド定義 (項目12(<code>ITEM_12</code>)) */
    public static final FieldName ITEM_12 = new FieldName(STORE_NAME, "ITEM_12") ;

    /** フィールド定義 (項目13(<code>ITEM_13</code>)) */
    public static final FieldName ITEM_13 = new FieldName(STORE_NAME, "ITEM_13") ;

    /** フィールド定義 (項目14(<code>ITEM_14</code>)) */
    public static final FieldName ITEM_14 = new FieldName(STORE_NAME, "ITEM_14") ;

    /** フィールド定義 (項目15(<code>ITEM_15</code>)) */
    public static final FieldName ITEM_15 = new FieldName(STORE_NAME, "ITEM_15") ;

    /** フィールド定義 (項目16(<code>ITEM_16</code>)) */
    public static final FieldName ITEM_16 = new FieldName(STORE_NAME, "ITEM_16") ;

    /** フィールド定義 (項目17(<code>ITEM_17</code>)) */
    public static final FieldName ITEM_17 = new FieldName(STORE_NAME, "ITEM_17") ;

    /** フィールド定義 (項目18(<code>ITEM_18</code>)) */
    public static final FieldName ITEM_18 = new FieldName(STORE_NAME, "ITEM_18") ;

    /** フィールド定義 (項目19(<code>ITEM_19</code>)) */
    public static final FieldName ITEM_19 = new FieldName(STORE_NAME, "ITEM_19") ;

    /** フィールド定義 (項目20(<code>ITEM_20</code>)) */
    public static final FieldName ITEM_20 = new FieldName(STORE_NAME, "ITEM_20") ;

    /** フィールド定義 (項目21(<code>ITEM_21</code>)) */
    public static final FieldName ITEM_21 = new FieldName(STORE_NAME, "ITEM_21") ;

    /** フィールド定義 (項目22(<code>ITEM_22</code>)) */
    public static final FieldName ITEM_22 = new FieldName(STORE_NAME, "ITEM_22") ;

    /** フィールド定義 (項目23(<code>ITEM_23</code>)) */
    public static final FieldName ITEM_23 = new FieldName(STORE_NAME, "ITEM_23") ;

    /** フィールド定義 (項目24(<code>ITEM_24</code>)) */
    public static final FieldName ITEM_24 = new FieldName(STORE_NAME, "ITEM_24") ;

    /** フィールド定義 (項目25(<code>ITEM_25</code>)) */
    public static final FieldName ITEM_25 = new FieldName(STORE_NAME, "ITEM_25") ;

    /** フィールド定義 (項目26(<code>ITEM_26</code>)) */
    public static final FieldName ITEM_26 = new FieldName(STORE_NAME, "ITEM_26") ;

    /** フィールド定義 (項目27(<code>ITEM_27</code>)) */
    public static final FieldName ITEM_27 = new FieldName(STORE_NAME, "ITEM_27") ;

    /** フィールド定義 (項目28(<code>ITEM_28</code>)) */
    public static final FieldName ITEM_28 = new FieldName(STORE_NAME, "ITEM_28") ;

    /** フィールド定義 (項目29(<code>ITEM_29</code>)) */
    public static final FieldName ITEM_29 = new FieldName(STORE_NAME, "ITEM_29") ;

    /** フィールド定義 (項目30(<code>ITEM_30</code>)) */
    public static final FieldName ITEM_30 = new FieldName(STORE_NAME, "ITEM_30") ;

    /** フィールド定義 (項目31(<code>ITEM_31</code>)) */
    public static final FieldName ITEM_31 = new FieldName(STORE_NAME, "ITEM_31") ;

    /** フィールド定義 (項目32(<code>ITEM_32</code>)) */
    public static final FieldName ITEM_32 = new FieldName(STORE_NAME, "ITEM_32") ;

    /** フィールド定義 (項目33(<code>ITEM_33</code>)) */
    public static final FieldName ITEM_33 = new FieldName(STORE_NAME, "ITEM_33") ;

    /** フィールド定義 (項目34(<code>ITEM_34</code>)) */
    public static final FieldName ITEM_34 = new FieldName(STORE_NAME, "ITEM_34") ;

    /** フィールド定義 (項目35(<code>ITEM_35</code>)) */
    public static final FieldName ITEM_35 = new FieldName(STORE_NAME, "ITEM_35") ;

    /** フィールド定義 (項目36(<code>ITEM_36</code>)) */
    public static final FieldName ITEM_36 = new FieldName(STORE_NAME, "ITEM_36") ;

    /** フィールド定義 (項目37(<code>ITEM_37</code>)) */
    public static final FieldName ITEM_37 = new FieldName(STORE_NAME, "ITEM_37") ;

    /** フィールド定義 (項目38(<code>ITEM_38</code>)) */
    public static final FieldName ITEM_38 = new FieldName(STORE_NAME, "ITEM_38") ;

    /** フィールド定義 (項目39(<code>ITEM_39</code>)) */
    public static final FieldName ITEM_39 = new FieldName(STORE_NAME, "ITEM_39") ;

    /** フィールド定義 (項目40(<code>ITEM_40</code>)) */
    public static final FieldName ITEM_40 = new FieldName(STORE_NAME, "ITEM_40") ;


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
    public PCTOperationLog()
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
     * ユーザ名(<code>USER_NAME</code>) に値をセットします。
     * @param value セットする値USER_NAME
     */
    public void setUserName(String value)  // @@GEN_V3@@
    {
        setValue(USER_NAME, value);
    }

    /**
     * ユーザ名(<code>USER_NAME</code>) から値を取得します。
     * @return USER_NAME
     */
    public String getUserName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USER_NAME, "")) ;
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>) に値をセットします。
     * @param value セットする値TERMINAL_NUMBER
     */
    public void setTerminalNumber(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_NUMBER, value);
    }

    /**
     * 端末No.(<code>TERMINAL_NUMBER</code>) から値を取得します。
     * @return TERMINAL_NUMBER
     */
    public String getTerminalNumber()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_NUMBER, "")) ;
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>) に値をセットします。
     * @param value セットする値TERMINAL_NAME
     */
    public void setTerminalName(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_NAME, value);
    }

    /**
     * 端末名(<code>TERMINAL_NAME</code>) から値を取得します。
     * @return TERMINAL_NAME
     */
    public String getTerminalName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_NAME, "")) ;
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
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>) に値をセットします。
     * @param value セットする値PAGENAMERESOURCEKEY
     */
    public void setPagenameresourcekey(String value)  // @@GEN_V3@@
    {
        setValue(PAGENAMERESOURCEKEY, value);
    }

    /**
     * ページ名リソースキー(<code>PAGENAMERESOURCEKEY</code>) から値を取得します。
     * @return PAGENAMERESOURCEKEY
     */
    public String getPagenameresourcekey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PAGENAMERESOURCEKEY, "")) ;
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>) に値をセットします。
     * @param value セットする値OPERATION_TYPE
     */
    public void setOperationType(int value)  // @@GEN_V3@@
    {
        setValue(OPERATION_TYPE, HandlerUtil.toObject(value));
    }

    /**
     * 操作区分(1:設定、2:登録、3:修正、4:削除、5:全削除、6:キャンセル、11:印刷、12:XLS、13:CSV、14:プレビュー、21:自動取込、22:手動取込、23:自動報告、24:手動報告)(<code>OPERATION_TYPE</code>) から値を取得します。
     * @return OPERATION_TYPE
     */
    public int getOperationType()  // @@GEN_V3@@
    {
        return getBigDecimal(OPERATION_TYPE, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 詳細(<code>DETAIL</code>) に値をセットします。
     * @param value セットする値DETAIL
     */
    public void setDetail(String value)  // @@GEN_V3@@
    {
        setValue(DETAIL, value);
    }

    /**
     * 詳細(<code>DETAIL</code>) から値を取得します。
     * @return DETAIL
     */
    public String getDetail()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(DETAIL, "")) ;
    }

    /**
     * 項目1(<code>ITEM_1</code>) に値をセットします。
     * @param value セットする値ITEM_1
     */
    public void setItem1(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_1, value);
    }

    /**
     * 項目1(<code>ITEM_1</code>) から値を取得します。
     * @return ITEM_1
     */
    public String getItem1()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_1, "")) ;
    }

    /**
     * 項目2(<code>ITEM_2</code>) に値をセットします。
     * @param value セットする値ITEM_2
     */
    public void setItem2(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_2, value);
    }

    /**
     * 項目2(<code>ITEM_2</code>) から値を取得します。
     * @return ITEM_2
     */
    public String getItem2()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_2, "")) ;
    }

    /**
     * 項目3(<code>ITEM_3</code>) に値をセットします。
     * @param value セットする値ITEM_3
     */
    public void setItem3(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_3, value);
    }

    /**
     * 項目3(<code>ITEM_3</code>) から値を取得します。
     * @return ITEM_3
     */
    public String getItem3()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_3, "")) ;
    }

    /**
     * 項目4(<code>ITEM_4</code>) に値をセットします。
     * @param value セットする値ITEM_4
     */
    public void setItem4(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_4, value);
    }

    /**
     * 項目4(<code>ITEM_4</code>) から値を取得します。
     * @return ITEM_4
     */
    public String getItem4()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_4, "")) ;
    }

    /**
     * 項目5(<code>ITEM_5</code>) に値をセットします。
     * @param value セットする値ITEM_5
     */
    public void setItem5(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_5, value);
    }

    /**
     * 項目5(<code>ITEM_5</code>) から値を取得します。
     * @return ITEM_5
     */
    public String getItem5()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_5, "")) ;
    }

    /**
     * 項目6(<code>ITEM_6</code>) に値をセットします。
     * @param value セットする値ITEM_6
     */
    public void setItem6(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_6, value);
    }

    /**
     * 項目6(<code>ITEM_6</code>) から値を取得します。
     * @return ITEM_6
     */
    public String getItem6()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_6, "")) ;
    }

    /**
     * 項目7(<code>ITEM_7</code>) に値をセットします。
     * @param value セットする値ITEM_7
     */
    public void setItem7(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_7, value);
    }

    /**
     * 項目7(<code>ITEM_7</code>) から値を取得します。
     * @return ITEM_7
     */
    public String getItem7()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_7, "")) ;
    }

    /**
     * 項目8(<code>ITEM_8</code>) に値をセットします。
     * @param value セットする値ITEM_8
     */
    public void setItem8(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_8, value);
    }

    /**
     * 項目8(<code>ITEM_8</code>) から値を取得します。
     * @return ITEM_8
     */
    public String getItem8()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_8, "")) ;
    }

    /**
     * 項目9(<code>ITEM_9</code>) に値をセットします。
     * @param value セットする値ITEM_9
     */
    public void setItem9(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_9, value);
    }

    /**
     * 項目9(<code>ITEM_9</code>) から値を取得します。
     * @return ITEM_9
     */
    public String getItem9()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_9, "")) ;
    }

    /**
     * 項目10(<code>ITEM_10</code>) に値をセットします。
     * @param value セットする値ITEM_10
     */
    public void setItem10(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_10, value);
    }

    /**
     * 項目10(<code>ITEM_10</code>) から値を取得します。
     * @return ITEM_10
     */
    public String getItem10()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_10, "")) ;
    }

    /**
     * 項目11(<code>ITEM_11</code>) に値をセットします。
     * @param value セットする値ITEM_11
     */
    public void setItem11(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_11, value);
    }

    /**
     * 項目11(<code>ITEM_11</code>) から値を取得します。
     * @return ITEM_11
     */
    public String getItem11()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_11, "")) ;
    }

    /**
     * 項目12(<code>ITEM_12</code>) に値をセットします。
     * @param value セットする値ITEM_12
     */
    public void setItem12(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_12, value);
    }

    /**
     * 項目12(<code>ITEM_12</code>) から値を取得します。
     * @return ITEM_12
     */
    public String getItem12()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_12, "")) ;
    }

    /**
     * 項目13(<code>ITEM_13</code>) に値をセットします。
     * @param value セットする値ITEM_13
     */
    public void setItem13(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_13, value);
    }

    /**
     * 項目13(<code>ITEM_13</code>) から値を取得します。
     * @return ITEM_13
     */
    public String getItem13()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_13, "")) ;
    }

    /**
     * 項目14(<code>ITEM_14</code>) に値をセットします。
     * @param value セットする値ITEM_14
     */
    public void setItem14(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_14, value);
    }

    /**
     * 項目14(<code>ITEM_14</code>) から値を取得します。
     * @return ITEM_14
     */
    public String getItem14()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_14, "")) ;
    }

    /**
     * 項目15(<code>ITEM_15</code>) に値をセットします。
     * @param value セットする値ITEM_15
     */
    public void setItem15(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_15, value);
    }

    /**
     * 項目15(<code>ITEM_15</code>) から値を取得します。
     * @return ITEM_15
     */
    public String getItem15()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_15, "")) ;
    }

    /**
     * 項目16(<code>ITEM_16</code>) に値をセットします。
     * @param value セットする値ITEM_16
     */
    public void setItem16(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_16, value);
    }

    /**
     * 項目16(<code>ITEM_16</code>) から値を取得します。
     * @return ITEM_16
     */
    public String getItem16()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_16, "")) ;
    }

    /**
     * 項目17(<code>ITEM_17</code>) に値をセットします。
     * @param value セットする値ITEM_17
     */
    public void setItem17(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_17, value);
    }

    /**
     * 項目17(<code>ITEM_17</code>) から値を取得します。
     * @return ITEM_17
     */
    public String getItem17()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_17, "")) ;
    }

    /**
     * 項目18(<code>ITEM_18</code>) に値をセットします。
     * @param value セットする値ITEM_18
     */
    public void setItem18(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_18, value);
    }

    /**
     * 項目18(<code>ITEM_18</code>) から値を取得します。
     * @return ITEM_18
     */
    public String getItem18()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_18, "")) ;
    }

    /**
     * 項目19(<code>ITEM_19</code>) に値をセットします。
     * @param value セットする値ITEM_19
     */
    public void setItem19(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_19, value);
    }

    /**
     * 項目19(<code>ITEM_19</code>) から値を取得します。
     * @return ITEM_19
     */
    public String getItem19()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_19, "")) ;
    }

    /**
     * 項目20(<code>ITEM_20</code>) に値をセットします。
     * @param value セットする値ITEM_20
     */
    public void setItem20(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_20, value);
    }

    /**
     * 項目20(<code>ITEM_20</code>) から値を取得します。
     * @return ITEM_20
     */
    public String getItem20()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_20, "")) ;
    }

    /**
     * 項目21(<code>ITEM_21</code>) に値をセットします。
     * @param value セットする値ITEM_21
     */
    public void setItem21(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_21, value);
    }

    /**
     * 項目21(<code>ITEM_21</code>) から値を取得します。
     * @return ITEM_21
     */
    public String getItem21()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_21, "")) ;
    }

    /**
     * 項目22(<code>ITEM_22</code>) に値をセットします。
     * @param value セットする値ITEM_22
     */
    public void setItem22(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_22, value);
    }

    /**
     * 項目22(<code>ITEM_22</code>) から値を取得します。
     * @return ITEM_22
     */
    public String getItem22()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_22, "")) ;
    }

    /**
     * 項目23(<code>ITEM_23</code>) に値をセットします。
     * @param value セットする値ITEM_23
     */
    public void setItem23(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_23, value);
    }

    /**
     * 項目23(<code>ITEM_23</code>) から値を取得します。
     * @return ITEM_23
     */
    public String getItem23()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_23, "")) ;
    }

    /**
     * 項目24(<code>ITEM_24</code>) に値をセットします。
     * @param value セットする値ITEM_24
     */
    public void setItem24(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_24, value);
    }

    /**
     * 項目24(<code>ITEM_24</code>) から値を取得します。
     * @return ITEM_24
     */
    public String getItem24()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_24, "")) ;
    }

    /**
     * 項目25(<code>ITEM_25</code>) に値をセットします。
     * @param value セットする値ITEM_25
     */
    public void setItem25(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_25, value);
    }

    /**
     * 項目25(<code>ITEM_25</code>) から値を取得します。
     * @return ITEM_25
     */
    public String getItem25()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_25, "")) ;
    }

    /**
     * 項目26(<code>ITEM_26</code>) に値をセットします。
     * @param value セットする値ITEM_26
     */
    public void setItem26(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_26, value);
    }

    /**
     * 項目26(<code>ITEM_26</code>) から値を取得します。
     * @return ITEM_26
     */
    public String getItem26()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_26, "")) ;
    }

    /**
     * 項目27(<code>ITEM_27</code>) に値をセットします。
     * @param value セットする値ITEM_27
     */
    public void setItem27(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_27, value);
    }

    /**
     * 項目27(<code>ITEM_27</code>) から値を取得します。
     * @return ITEM_27
     */
    public String getItem27()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_27, "")) ;
    }

    /**
     * 項目28(<code>ITEM_28</code>) に値をセットします。
     * @param value セットする値ITEM_28
     */
    public void setItem28(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_28, value);
    }

    /**
     * 項目28(<code>ITEM_28</code>) から値を取得します。
     * @return ITEM_28
     */
    public String getItem28()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_28, "")) ;
    }

    /**
     * 項目29(<code>ITEM_29</code>) に値をセットします。
     * @param value セットする値ITEM_29
     */
    public void setItem29(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_29, value);
    }

    /**
     * 項目29(<code>ITEM_29</code>) から値を取得します。
     * @return ITEM_29
     */
    public String getItem29()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_29, "")) ;
    }

    /**
     * 項目30(<code>ITEM_30</code>) に値をセットします。
     * @param value セットする値ITEM_30
     */
    public void setItem30(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_30, value);
    }

    /**
     * 項目30(<code>ITEM_30</code>) から値を取得します。
     * @return ITEM_30
     */
    public String getItem30()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_30, "")) ;
    }

    /**
     * 項目31(<code>ITEM_31</code>) に値をセットします。
     * @param value セットする値ITEM_31
     */
    public void setItem31(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_31, value);
    }

    /**
     * 項目31(<code>ITEM_31</code>) から値を取得します。
     * @return ITEM_31
     */
    public String getItem31()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_31, "")) ;
    }

    /**
     * 項目32(<code>ITEM_32</code>) に値をセットします。
     * @param value セットする値ITEM_32
     */
    public void setItem32(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_32, value);
    }

    /**
     * 項目32(<code>ITEM_32</code>) から値を取得します。
     * @return ITEM_32
     */
    public String getItem32()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_32, "")) ;
    }

    /**
     * 項目33(<code>ITEM_33</code>) に値をセットします。
     * @param value セットする値ITEM_33
     */
    public void setItem33(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_33, value);
    }

    /**
     * 項目33(<code>ITEM_33</code>) から値を取得します。
     * @return ITEM_33
     */
    public String getItem33()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_33, "")) ;
    }

    /**
     * 項目34(<code>ITEM_34</code>) に値をセットします。
     * @param value セットする値ITEM_34
     */
    public void setItem34(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_34, value);
    }

    /**
     * 項目34(<code>ITEM_34</code>) から値を取得します。
     * @return ITEM_34
     */
    public String getItem34()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_34, "")) ;
    }

    /**
     * 項目35(<code>ITEM_35</code>) に値をセットします。
     * @param value セットする値ITEM_35
     */
    public void setItem35(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_35, value);
    }

    /**
     * 項目35(<code>ITEM_35</code>) から値を取得します。
     * @return ITEM_35
     */
    public String getItem35()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_35, "")) ;
    }

    /**
     * 項目36(<code>ITEM_36</code>) に値をセットします。
     * @param value セットする値ITEM_36
     */
    public void setItem36(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_36, value);
    }

    /**
     * 項目36(<code>ITEM_36</code>) から値を取得します。
     * @return ITEM_36
     */
    public String getItem36()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_36, "")) ;
    }

    /**
     * 項目37(<code>ITEM_37</code>) に値をセットします。
     * @param value セットする値ITEM_37
     */
    public void setItem37(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_37, value);
    }

    /**
     * 項目37(<code>ITEM_37</code>) から値を取得します。
     * @return ITEM_37
     */
    public String getItem37()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_37, "")) ;
    }

    /**
     * 項目38(<code>ITEM_38</code>) に値をセットします。
     * @param value セットする値ITEM_38
     */
    public void setItem38(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_38, value);
    }

    /**
     * 項目38(<code>ITEM_38</code>) から値を取得します。
     * @return ITEM_38
     */
    public String getItem38()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_38, "")) ;
    }

    /**
     * 項目39(<code>ITEM_39</code>) に値をセットします。
     * @param value セットする値ITEM_39
     */
    public void setItem39(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_39, value);
    }

    /**
     * 項目39(<code>ITEM_39</code>) から値を取得します。
     * @return ITEM_39
     */
    public String getItem39()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_39, "")) ;
    }

    /**
     * 項目40(<code>ITEM_40</code>) に値をセットします。
     * @param value セットする値ITEM_40
     */
    public void setItem40(String value)  // @@GEN_V3@@
    {
        setValue(ITEM_40, value);
    }

    /**
     * 項目40(<code>ITEM_40</code>) から値を取得します。
     * @return ITEM_40
     */
    public String getItem40()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_40, "")) ;
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
        return "$Id: PCTOperationLog.java 3213 2009-03-02 06:59:20Z arai $" ;
    }
}

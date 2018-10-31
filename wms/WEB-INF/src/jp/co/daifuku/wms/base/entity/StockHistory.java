// $Id: StockHistory.java 7431 2010-03-08 01:11:48Z ota $
// $LastChangedRevision: 7431 $
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
 * STOCKHISTORYのエンティティクラスです。
 *
 * @version $Revision: 7431 $, $Date: 2010-03-08 10:11:48 +0900 (月, 08 3 2010) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class StockHistory
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNSTOCKHISTORY" ;

    /*
     * テーブル名: DNSTOCKHISTORY
     * 登録日時 :                      REGIST_DATE         timestamp
     * 作業日 :                        WORK_DAY            varchar2(8)
     * 増減区分 :                      INC_DEC_TYPE        varchar2(1)
     * 作業区分 :                      JOB_TYPE            varchar2(2)
     * 分析区分 :                      ANALYSIS_TYPE       varchar2(1)
     * 理由区分 :                      REASON_TYPE         number
     * 理由名称 :                      REASON_NAME         varchar2(40)
     * 在庫ID :                        STOCK_ID            varchar2(8)
     * エリアNo. :                     AREA_NO             varchar2(4)
     * 棚No. :                         LOCATION_NO         varchar2(11)
     * エリア種別 :                    AREA_TYPE           varchar2(1)
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 荷主名称 :                      CONSIGNOR_NAME      varchar2(40)
     * 商品コード :                    ITEM_CODE           varchar2(40)
     * 商品名称 :                      ITEM_NAME           varchar2(100)
     * JANコード :                     JAN                 varchar2(13)
     * ケースITF :                     ITF                 varchar2(16)
     * ボールITF :                     BUNDLE_ITF          varchar2(16)
     * ケース入数 :                    ENTERING_QTY        number
     * ボール入数 :                    BUNDLE_ENTERING_QTY number
     * ロットNo. :                     LOT_NO              varchar2(60)
     * 入庫日 :                        STORAGE_DAY         varchar2(8)
     * 入庫日時 :                      STORAGE_DATE        date
     * 最新入庫日時 :                  NEWEST_STORAGE_DATE date
     * 最終出庫日 :                    RETRIEVAL_DAY       varchar2(8)
     * 更新後在庫数 :                  UPDATE_STOCK_QTY    number
     * 在庫増減数 :                    INC_DEC_QTY         number
     * パレットID :                    PALLET_ID           varchar2(8)
     * ユーザID :                      USER_ID             varchar2(20)
     * ユーザ名称 :                    USER_NAME           varchar2(40)
     * 端末No、RFTNo :                 TERMINAL_NO         varchar2(4)
     * 端末名称 :                      TERMINAL_NAME       varchar2(60)
     * IPアドレス :                    IP_ADDRESS          varchar2(64)
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     */
    /** フィールド定義 (登録日時(<code>REGIST_DATE</code>)) */
    public static final FieldName REGIST_DATE = new FieldName(STORE_NAME, "REGIST_DATE") ;

    /** フィールド定義 (作業日(<code>WORK_DAY</code>)) */
    public static final FieldName WORK_DAY = new FieldName(STORE_NAME, "WORK_DAY") ;

    /** フィールド定義 (増減区分(<code>INC_DEC_TYPE</code>)) */
    public static final FieldName INC_DEC_TYPE = new FieldName(STORE_NAME, "INC_DEC_TYPE") ;

    /** フィールド定義 (作業区分(<code>JOB_TYPE</code>)) */
    public static final FieldName JOB_TYPE = new FieldName(STORE_NAME, "JOB_TYPE") ;

    /** フィールド定義 (分析区分(<code>ANALYSIS_TYPE</code>)) */
    public static final FieldName ANALYSIS_TYPE = new FieldName(STORE_NAME, "ANALYSIS_TYPE") ;

    /** フィールド定義 (理由区分(<code>REASON_TYPE</code>)) */
    public static final FieldName REASON_TYPE = new FieldName(STORE_NAME, "REASON_TYPE") ;

    /** フィールド定義 (理由名称(<code>REASON_NAME</code>)) */
    public static final FieldName REASON_NAME = new FieldName(STORE_NAME, "REASON_NAME") ;

    /** フィールド定義 (在庫ID(<code>STOCK_ID</code>)) */
    public static final FieldName STOCK_ID = new FieldName(STORE_NAME, "STOCK_ID") ;

    /** フィールド定義 (エリアNo.(<code>AREA_NO</code>)) */
    public static final FieldName AREA_NO = new FieldName(STORE_NAME, "AREA_NO") ;

    /** フィールド定義 (棚No.(<code>LOCATION_NO</code>)) */
    public static final FieldName LOCATION_NO = new FieldName(STORE_NAME, "LOCATION_NO") ;

    /** フィールド定義 (エリア種別(<code>AREA_TYPE</code>)) */
    public static final FieldName AREA_TYPE = new FieldName(STORE_NAME, "AREA_TYPE") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (荷主名称(<code>CONSIGNOR_NAME</code>)) */
    public static final FieldName CONSIGNOR_NAME = new FieldName(STORE_NAME, "CONSIGNOR_NAME") ;

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE") ;

    /** フィールド定義 (商品名称(<code>ITEM_NAME</code>)) */
    public static final FieldName ITEM_NAME = new FieldName(STORE_NAME, "ITEM_NAME") ;

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

    /** フィールド定義 (ロットNo.(<code>LOT_NO</code>)) */
    public static final FieldName LOT_NO = new FieldName(STORE_NAME, "LOT_NO") ;

    /** フィールド定義 (入庫日(<code>STORAGE_DAY</code>)) */
    public static final FieldName STORAGE_DAY = new FieldName(STORE_NAME, "STORAGE_DAY") ;

    /** フィールド定義 (入庫日時(<code>STORAGE_DATE</code>)) */
    public static final FieldName STORAGE_DATE = new FieldName(STORE_NAME, "STORAGE_DATE") ;

    /** フィールド定義 (最新入庫日時(<code>NEWEST_STORAGE_DATE</code>)) */
    public static final FieldName NEWEST_STORAGE_DATE = new FieldName(STORE_NAME, "NEWEST_STORAGE_DATE") ;

    /** フィールド定義 (最終出庫日(<code>RETRIEVAL_DAY</code>)) */
    public static final FieldName RETRIEVAL_DAY = new FieldName(STORE_NAME, "RETRIEVAL_DAY") ;

    /** フィールド定義 (更新後在庫数(<code>UPDATE_STOCK_QTY</code>)) */
    public static final FieldName UPDATE_STOCK_QTY = new FieldName(STORE_NAME, "UPDATE_STOCK_QTY") ;

    /** フィールド定義 (在庫増減数(<code>INC_DEC_QTY</code>)) */
    public static final FieldName INC_DEC_QTY = new FieldName(STORE_NAME, "INC_DEC_QTY") ;

    /** フィールド定義 (パレットID(<code>PALLET_ID</code>)) */
    public static final FieldName PALLET_ID = new FieldName(STORE_NAME, "PALLET_ID") ;

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
    public StockHistory()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

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
     * 増減区分(<code>INC_DEC_TYPE</code>) に値をセットします。
     * @param value セットする値INC_DEC_TYPE
     */
    public void setIncDecType(String value)  // @@GEN_V3@@
    {
        setValue(INC_DEC_TYPE, value);
    }

    /**
     * 増減区分(<code>INC_DEC_TYPE</code>) から値を取得します。
     * @return INC_DEC_TYPE
     */
    public String getIncDecType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(INC_DEC_TYPE, "")) ;
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
     * 分析区分(<code>ANALYSIS_TYPE</code>) に値をセットします。
     * @param value セットする値ANALYSIS_TYPE
     */
    public void setAnalysisType(String value)  // @@GEN_V3@@
    {
        setValue(ANALYSIS_TYPE, value);
    }

    /**
     * 分析区分(<code>ANALYSIS_TYPE</code>) から値を取得します。
     * @return ANALYSIS_TYPE
     */
    public String getAnalysisType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ANALYSIS_TYPE, "")) ;
    }

    /**
     * 理由区分(<code>REASON_TYPE</code>) に値をセットします。
     * @param value セットする値REASON_TYPE
     */
    public void setReasonType(int value)  // @@GEN_V3@@
    {
        setValue(REASON_TYPE, HandlerUtil.toObject(value));
    }

    /**
     * 理由区分(<code>REASON_TYPE</code>) から値を取得します。
     * @return REASON_TYPE
     */
    public int getReasonType()  // @@GEN_V3@@
    {
        return getBigDecimal(REASON_TYPE, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 理由名称(<code>REASON_NAME</code>) に値をセットします。
     * @param value セットする値REASON_NAME
     */
    public void setReasonName(String value)  // @@GEN_V3@@
    {
        setValue(REASON_NAME, value);
    }

    /**
     * 理由名称(<code>REASON_NAME</code>) から値を取得します。
     * @return REASON_NAME
     */
    public String getReasonName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REASON_NAME, "")) ;
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>) に値をセットします。
     * @param value セットする値STOCK_ID
     */
    public void setStockId(String value)  // @@GEN_V3@@
    {
        setValue(STOCK_ID, value);
    }

    /**
     * 在庫ID(<code>STOCK_ID</code>) から値を取得します。
     * @return STOCK_ID
     */
    public String getStockId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STOCK_ID, "")) ;
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
     * エリア種別(<code>AREA_TYPE</code>) に値をセットします。
     * @param value セットする値AREA_TYPE
     */
    public void setAreaType(String value)  // @@GEN_V3@@
    {
        setValue(AREA_TYPE, value);
    }

    /**
     * エリア種別(<code>AREA_TYPE</code>) から値を取得します。
     * @return AREA_TYPE
     */
    public String getAreaType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(AREA_TYPE, "")) ;
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
     * 荷主名称(<code>CONSIGNOR_NAME</code>) に値をセットします。
     * @param value セットする値CONSIGNOR_NAME
     */
    public void setConsignorName(String value)  // @@GEN_V3@@
    {
        setValue(CONSIGNOR_NAME, value);
    }

    /**
     * 荷主名称(<code>CONSIGNOR_NAME</code>) から値を取得します。
     * @return CONSIGNOR_NAME
     */
    public String getConsignorName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CONSIGNOR_NAME, "")) ;
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
     * ロットNo.(<code>LOT_NO</code>) に値をセットします。
     * @param value セットする値LOT_NO
     */
    public void setLotNo(String value)  // @@GEN_V3@@
    {
        setValue(LOT_NO, value);
    }

    /**
     * ロットNo.(<code>LOT_NO</code>) から値を取得します。
     * @return LOT_NO
     */
    public String getLotNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOT_NO, "")) ;
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
     * 最新入庫日時(<code>NEWEST_STORAGE_DATE</code>) に値をセットします。
     * @param value セットする値NEWEST_STORAGE_DATE
     */
    public void setNewestStorageDate(Date value)  // @@GEN_V3@@
    {
        setValue(NEWEST_STORAGE_DATE, value);
    }

    /**
     * 最新入庫日時(<code>NEWEST_STORAGE_DATE</code>) から値を取得します。
     * @return NEWEST_STORAGE_DATE
     */
    public Date getNewestStorageDate()  // @@GEN_V3@@
    {
        return (Date)getValue(NEWEST_STORAGE_DATE, null) ;
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
     * 更新後在庫数(<code>UPDATE_STOCK_QTY</code>) に値をセットします。
     * @param value セットする値UPDATE_STOCK_QTY
     */
    public void setUpdateStockQty(int value)  // @@GEN_V3@@
    {
        setValue(UPDATE_STOCK_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 更新後在庫数(<code>UPDATE_STOCK_QTY</code>) から値を取得します。
     * @return UPDATE_STOCK_QTY
     */
    public int getUpdateStockQty()  // @@GEN_V3@@
    {
        return getBigDecimal(UPDATE_STOCK_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 在庫増減数(<code>INC_DEC_QTY</code>) に値をセットします。
     * @param value セットする値INC_DEC_QTY
     */
    public void setIncDecQty(int value)  // @@GEN_V3@@
    {
        setValue(INC_DEC_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 在庫増減数(<code>INC_DEC_QTY</code>) から値を取得します。
     * @return INC_DEC_QTY
     */
    public int getIncDecQty()  // @@GEN_V3@@
    {
        return getBigDecimal(INC_DEC_QTY, BigDecimal.ZERO).intValue() ;
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
        return "$Id: StockHistory.java 7431 2010-03-08 01:11:48Z ota $" ;
    }
}
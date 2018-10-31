// $Id: ShipPlan.java 5127 2009-10-13 12:20:06Z ota $
// $LastChangedRevision: 5127 $
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
 * SHIPPLANのエンティティクラスです。
 *
 * @version $Revision: 5127 $, $Date: 2009-10-13 21:20:06 +0900 (火, 13 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: ota $
 */

public class ShipPlan
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNSHIPPLAN" ;

    /*
     * テーブル名: DNSHIPPLAN
     * 予定一意キー :                  PLAN_UKEY           varchar2(10)
     * クロスドック連携キー :          CROSS_DOCK_UKEY     varchar2(-1)
     * 取込単位キー :                  LOAD_UNIT_KEY       varchar2(24)
     * ファイル行No. :                 FILE_LINE_NO        number
     * 出荷検品状態フラグ :            WORK_STATUS_FLAG    varchar2(1)
     * バース登録状態フラグ :          BERTH_STATUS_FLAG   varchar2(1)
     * ホスト取消区分 :                CANCEL_FLAG         varchar2(1)
     * TC/DC区分 :                     TCDC_FLAG           varchar2(1)
     * 予定日 :                        PLAN_DAY            varchar2(8)
     * バッチNo. :                     BATCH_NO            varchar2(8)
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 出荷先コード :                  CUSTOMER_CODE       varchar2(16)
     * 出荷伝票No. :                   SHIP_TICKET_NO      varchar2(16)
     * 出荷伝票行No. :                 SHIP_LINE_NO        number
     * 商品コード :                    ITEM_CODE           varchar2(40)
     * 予定ロットNo. :                 PLAN_LOT_NO         varchar2(60)
     * 配分済数 :                      DISTRIBUTED_QTY     number
     * 予定数 :                        PLAN_QTY            number
     * 実績数 :                        RESULT_QTY          number
     * 欠品数 :                        SHORTAGE_QTY        number
     * 実績報告区分 :                  REPORT_FLAG         varchar2(1)
     * 作業日 :                        WORK_DAY            varchar2(8)
     * 登録区分 :                      REGIST_KIND         varchar2(1)
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */
    /** フィールド定義 (予定一意キー(<code>PLAN_UKEY</code>)) */
    public static final FieldName PLAN_UKEY = new FieldName(STORE_NAME, "PLAN_UKEY") ;

    /** フィールド定義 (クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)) */
    public static final FieldName CROSS_DOCK_UKEY = new FieldName(STORE_NAME, "CROSS_DOCK_UKEY") ;

    /** フィールド定義 (取込単位キー(<code>LOAD_UNIT_KEY</code>)) */
    public static final FieldName LOAD_UNIT_KEY = new FieldName(STORE_NAME, "LOAD_UNIT_KEY") ;

    /** フィールド定義 (ファイル行No.(<code>FILE_LINE_NO</code>)) */
    public static final FieldName FILE_LINE_NO = new FieldName(STORE_NAME, "FILE_LINE_NO") ;

    /** フィールド定義 (出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)) */
    public static final FieldName WORK_STATUS_FLAG = new FieldName(STORE_NAME, "WORK_STATUS_FLAG") ;

    /** フィールド定義 (バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)) */
    public static final FieldName BERTH_STATUS_FLAG = new FieldName(STORE_NAME, "BERTH_STATUS_FLAG") ;

    /** フィールド定義 (ホスト取消区分(<code>CANCEL_FLAG</code>)) */
    public static final FieldName CANCEL_FLAG = new FieldName(STORE_NAME, "CANCEL_FLAG") ;

    /** フィールド定義 (TC/DC区分(<code>TCDC_FLAG</code>)) */
    public static final FieldName TCDC_FLAG = new FieldName(STORE_NAME, "TCDC_FLAG") ;

    /** フィールド定義 (予定日(<code>PLAN_DAY</code>)) */
    public static final FieldName PLAN_DAY = new FieldName(STORE_NAME, "PLAN_DAY") ;

    /** フィールド定義 (バッチNo.(<code>BATCH_NO</code>)) */
    public static final FieldName BATCH_NO = new FieldName(STORE_NAME, "BATCH_NO") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (出荷先コード(<code>CUSTOMER_CODE</code>)) */
    public static final FieldName CUSTOMER_CODE = new FieldName(STORE_NAME, "CUSTOMER_CODE") ;

    /** フィールド定義 (出荷伝票No.(<code>SHIP_TICKET_NO</code>)) */
    public static final FieldName SHIP_TICKET_NO = new FieldName(STORE_NAME, "SHIP_TICKET_NO") ;

    /** フィールド定義 (出荷伝票行No.(<code>SHIP_LINE_NO</code>)) */
    public static final FieldName SHIP_LINE_NO = new FieldName(STORE_NAME, "SHIP_LINE_NO") ;

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE") ;

    /** フィールド定義 (予定ロットNo.(<code>PLAN_LOT_NO</code>)) */
    public static final FieldName PLAN_LOT_NO = new FieldName(STORE_NAME, "PLAN_LOT_NO") ;

    /** フィールド定義 (配分済数(<code>DISTRIBUTED_QTY</code>)) */
    public static final FieldName DISTRIBUTED_QTY = new FieldName(STORE_NAME, "DISTRIBUTED_QTY") ;

    /** フィールド定義 (予定数(<code>PLAN_QTY</code>)) */
    public static final FieldName PLAN_QTY = new FieldName(STORE_NAME, "PLAN_QTY") ;

    /** フィールド定義 (実績数(<code>RESULT_QTY</code>)) */
    public static final FieldName RESULT_QTY = new FieldName(STORE_NAME, "RESULT_QTY") ;

    /** フィールド定義 (欠品数(<code>SHORTAGE_QTY</code>)) */
    public static final FieldName SHORTAGE_QTY = new FieldName(STORE_NAME, "SHORTAGE_QTY") ;

    /** フィールド定義 (実績報告区分(<code>REPORT_FLAG</code>)) */
    public static final FieldName REPORT_FLAG = new FieldName(STORE_NAME, "REPORT_FLAG") ;

    /** フィールド定義 (作業日(<code>WORK_DAY</code>)) */
    public static final FieldName WORK_DAY = new FieldName(STORE_NAME, "WORK_DAY") ;

    /** フィールド定義 (登録区分(<code>REGIST_KIND</code>)) */
    public static final FieldName REGIST_KIND = new FieldName(STORE_NAME, "REGIST_KIND") ;

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
    public ShipPlan()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>) に値をセットします。
     * @param value セットする値PLAN_UKEY
     */
    public void setPlanUkey(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_UKEY, value);
    }

    /**
     * 予定一意キー(<code>PLAN_UKEY</code>) から値を取得します。
     * @return PLAN_UKEY
     */
    public String getPlanUkey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_UKEY, "")) ;
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>) に値をセットします。
     * @param value セットする値CROSS_DOCK_UKEY
     */
    public void setCrossDockUkey(String value)  // @@GEN_V3@@
    {
        setValue(CROSS_DOCK_UKEY, value);
    }

    /**
     * クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>) から値を取得します。
     * @return CROSS_DOCK_UKEY
     */
    public String getCrossDockUkey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CROSS_DOCK_UKEY, "")) ;
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>) に値をセットします。
     * @param value セットする値LOAD_UNIT_KEY
     */
    public void setLoadUnitKey(String value)  // @@GEN_V3@@
    {
        setValue(LOAD_UNIT_KEY, value);
    }

    /**
     * 取込単位キー(<code>LOAD_UNIT_KEY</code>) から値を取得します。
     * @return LOAD_UNIT_KEY
     */
    public String getLoadUnitKey()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOAD_UNIT_KEY, "")) ;
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
     * 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>) に値をセットします。
     * @param value セットする値WORK_STATUS_FLAG
     */
    public void setWorkStatusFlag(String value)  // @@GEN_V3@@
    {
        setValue(WORK_STATUS_FLAG, value);
    }

    /**
     * 出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>) から値を取得します。
     * @return WORK_STATUS_FLAG
     */
    public String getWorkStatusFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WORK_STATUS_FLAG, "")) ;
    }

    /**
     * バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>) に値をセットします。
     * @param value セットする値BERTH_STATUS_FLAG
     */
    public void setBerthStatusFlag(String value)  // @@GEN_V3@@
    {
        setValue(BERTH_STATUS_FLAG, value);
    }

    /**
     * バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>) から値を取得します。
     * @return BERTH_STATUS_FLAG
     */
    public String getBerthStatusFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(BERTH_STATUS_FLAG, "")) ;
    }

    /**
     * ホスト取消区分(<code>CANCEL_FLAG</code>) に値をセットします。
     * @param value セットする値CANCEL_FLAG
     */
    public void setCancelFlag(String value)  // @@GEN_V3@@
    {
        setValue(CANCEL_FLAG, value);
    }

    /**
     * ホスト取消区分(<code>CANCEL_FLAG</code>) から値を取得します。
     * @return CANCEL_FLAG
     */
    public String getCancelFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CANCEL_FLAG, "")) ;
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>) に値をセットします。
     * @param value セットする値TCDC_FLAG
     */
    public void setTcdcFlag(String value)  // @@GEN_V3@@
    {
        setValue(TCDC_FLAG, value);
    }

    /**
     * TC/DC区分(<code>TCDC_FLAG</code>) から値を取得します。
     * @return TCDC_FLAG
     */
    public String getTcdcFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TCDC_FLAG, "")) ;
    }

    /**
     * 予定日(<code>PLAN_DAY</code>) に値をセットします。
     * @param value セットする値PLAN_DAY
     */
    public void setPlanDay(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_DAY, value);
    }

    /**
     * 予定日(<code>PLAN_DAY</code>) から値を取得します。
     * @return PLAN_DAY
     */
    public String getPlanDay()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_DAY, "")) ;
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>) に値をセットします。
     * @param value セットする値BATCH_NO
     */
    public void setBatchNo(String value)  // @@GEN_V3@@
    {
        setValue(BATCH_NO, value);
    }

    /**
     * バッチNo.(<code>BATCH_NO</code>) から値を取得します。
     * @return BATCH_NO
     */
    public String getBatchNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(BATCH_NO, "")) ;
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
     * 出荷先コード(<code>CUSTOMER_CODE</code>) に値をセットします。
     * @param value セットする値CUSTOMER_CODE
     */
    public void setCustomerCode(String value)  // @@GEN_V3@@
    {
        setValue(CUSTOMER_CODE, value);
    }

    /**
     * 出荷先コード(<code>CUSTOMER_CODE</code>) から値を取得します。
     * @return CUSTOMER_CODE
     */
    public String getCustomerCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CUSTOMER_CODE, "")) ;
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>) に値をセットします。
     * @param value セットする値SHIP_TICKET_NO
     */
    public void setShipTicketNo(String value)  // @@GEN_V3@@
    {
        setValue(SHIP_TICKET_NO, value);
    }

    /**
     * 出荷伝票No.(<code>SHIP_TICKET_NO</code>) から値を取得します。
     * @return SHIP_TICKET_NO
     */
    public String getShipTicketNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SHIP_TICKET_NO, "")) ;
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>) に値をセットします。
     * @param value セットする値SHIP_LINE_NO
     */
    public void setShipLineNo(int value)  // @@GEN_V3@@
    {
        setValue(SHIP_LINE_NO, HandlerUtil.toObject(value));
    }

    /**
     * 出荷伝票行No.(<code>SHIP_LINE_NO</code>) から値を取得します。
     * @return SHIP_LINE_NO
     */
    public int getShipLineNo()  // @@GEN_V3@@
    {
        return getBigDecimal(SHIP_LINE_NO, BigDecimal.ZERO).intValue() ;
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
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>) に値をセットします。
     * @param value セットする値PLAN_LOT_NO
     */
    public void setPlanLotNo(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_LOT_NO, value);
    }

    /**
     * 予定ロットNo.(<code>PLAN_LOT_NO</code>) から値を取得します。
     * @return PLAN_LOT_NO
     */
    public String getPlanLotNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_LOT_NO, "")) ;
    }

    /**
     * 配分済数(<code>DISTRIBUTED_QTY</code>) に値をセットします。
     * @param value セットする値DISTRIBUTED_QTY
     */
    public void setDistributedQty(int value)  // @@GEN_V3@@
    {
        setValue(DISTRIBUTED_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 配分済数(<code>DISTRIBUTED_QTY</code>) から値を取得します。
     * @return DISTRIBUTED_QTY
     */
    public int getDistributedQty()  // @@GEN_V3@@
    {
        return getBigDecimal(DISTRIBUTED_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 予定数(<code>PLAN_QTY</code>) に値をセットします。
     * @param value セットする値PLAN_QTY
     */
    public void setPlanQty(int value)  // @@GEN_V3@@
    {
        setValue(PLAN_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 予定数(<code>PLAN_QTY</code>) から値を取得します。
     * @return PLAN_QTY
     */
    public int getPlanQty()  // @@GEN_V3@@
    {
        return getBigDecimal(PLAN_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 実績数(<code>RESULT_QTY</code>) に値をセットします。
     * @param value セットする値RESULT_QTY
     */
    public void setResultQty(int value)  // @@GEN_V3@@
    {
        setValue(RESULT_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 実績数(<code>RESULT_QTY</code>) から値を取得します。
     * @return RESULT_QTY
     */
    public int getResultQty()  // @@GEN_V3@@
    {
        return getBigDecimal(RESULT_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>) に値をセットします。
     * @param value セットする値SHORTAGE_QTY
     */
    public void setShortageQty(int value)  // @@GEN_V3@@
    {
        setValue(SHORTAGE_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 欠品数(<code>SHORTAGE_QTY</code>) から値を取得します。
     * @return SHORTAGE_QTY
     */
    public int getShortageQty()  // @@GEN_V3@@
    {
        return getBigDecimal(SHORTAGE_QTY, BigDecimal.ZERO).intValue() ;
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>) に値をセットします。
     * @param value セットする値REPORT_FLAG
     */
    public void setReportFlag(String value)  // @@GEN_V3@@
    {
        setValue(REPORT_FLAG, value);
    }

    /**
     * 実績報告区分(<code>REPORT_FLAG</code>) から値を取得します。
     * @return REPORT_FLAG
     */
    public String getReportFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REPORT_FLAG, "")) ;
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
     * 登録区分(<code>REGIST_KIND</code>) に値をセットします。
     * @param value セットする値REGIST_KIND
     */
    public void setRegistKind(String value)  // @@GEN_V3@@
    {
        setValue(REGIST_KIND, value);
    }

    /**
     * 登録区分(<code>REGIST_KIND</code>) から値を取得します。
     * @return REGIST_KIND
     */
    public String getRegistKind()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(REGIST_KIND, "")) ;
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
        return "$Id: ShipPlan.java 5127 2009-10-13 12:20:06Z ota $" ;
    }
}

// $Id: ShipResultView.java 1544 2008-11-25 09:32:24Z dmori $
// $LastChangedRevision: 1544 $
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
 * SHIPRESULTVIEWのエンティティクラスです。
 *
 * @version $Revision: 1544 $, $Date: 2008-11-25 18:32:24 +0900 (火, 25 11 2008) $
 * @author  ss
 * @author  Last commit: $Author: dmori $
 */

public class ShipResultView
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DVSHIPRESULTVIEW" ;

    /*
     * テーブル名: DVSHIPRESULTVIEW
     * 作業日 :                        WORK_DAY            varchar2(8)
     * 作業No. :                       JOB_NO              varchar2(8)
     * 設定単位キー :                  SETTING_UNIT_KEY    varchar2(8)
     * 集約作業No. :                   COLLECT_JOB_NO      varchar2(8)
     * クロスドック連携キー :          CROSS_DOCK_UKEY     varchar2(-1)
     * 作業区分 :                      JOB_TYPE            varchar2(2)
     * 出荷検品状態フラグ :            WORK_STATUS_FLAG    varchar2(1)
     * バース登録状態フラグ :          BERTH_STATUS_FLAG   varchar2(1)
     * ハードウェア区分 :              HARDWARE_TYPE       varchar2(1)
     * 予定一意キー :                  PLAN_UKEY           varchar2(10)
     * TC/DC区分 :                     TCDC_FLAG           varchar2(1)
     * 予定日 :                        PLAN_DAY            varchar2(8)
     * バッチNo. :                     BATCH_NO            varchar2(8)
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * 荷主名称 :                      CONSIGNOR_NAME      varchar2(40)
     * 出荷先コード :                  CUSTOMER_CODE       varchar2(16)
     * 出荷先名称 :                    CUSTOMER_NAME       varchar2(40)
     * 出荷伝票No. :                   SHIP_TICKET_NO      varchar2(16)
     * 出荷伝票行No. :                 SHIP_LINE_NO        number
     * 商品コード :                    ITEM_CODE           varchar2(16)
     * 商品名称 :                      ITEM_NAME           varchar2(40)
     * JANコード :                     JAN                 varchar2(13)
     * ケースITF :                     ITF                 varchar2(16)
     * ボールITF :                     BUNDLE_ITF          varchar2(16)
     * ケース入数 :                    ENTERING_QTY        number
     * ボール入数 :                    BUNDLE_ENTERING_QTY number
     * 予定ロットNo :                  PLAN_LOT_NO         varchar2(16)
     * 予定数 :                        PLAN_QTY            number
     * 実績数 :                        RESULT_QTY          number
     * 欠品数 :                        SHORTAGE_QTY        number
     * 実績ロットNo. :                 RESULT_LOT_NO       varchar2(16)
     * 結果バース :                    RESULT_BERTH        varchar2(4)
     * 実績報告区分 :                  REPORT_FLAG         varchar2(1)
     * 出荷検品ユーザID :              SHIP_USER_ID        varchar2(20)
     * 出荷検品ユーザ名称 :            SHIP_USER_NAME      varchar2(40)
     * 出荷検品端末No.、RFTNo. :       SHIP_TERMINAL_NO    varchar2(4)
     * 出荷検品作業秒数 :              SHIP_WORK_SECOND    number
     * バース登録ユーザID :            BERTH_USER_ID       varchar2(20)
     * バース登録ユーザ名称 :          BERTH_USER_NAME     varchar2(40)
     * バース登録端末No.、RFTNo. :     BERTH_TERMINAL_NO   varchar2(4)
     * バース登録作業秒数 :            BERTH_WORK_SECOND   number
     * 登録日時 :                      REGIST_DATE         timestamp
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    timestamp
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */

    /** フィールド定義 (作業日(<code>WORK_DAY</code>)) */
    public static final FieldName WORK_DAY = new FieldName(STORE_NAME, "WORK_DAY") ;

    /** フィールド定義 (作業No.(<code>JOB_NO</code>)) */
    public static final FieldName JOB_NO = new FieldName(STORE_NAME, "JOB_NO") ;

    /** フィールド定義 (設定単位キー(<code>SETTING_UNIT_KEY</code>)) */
    public static final FieldName SETTING_UNIT_KEY = new FieldName(STORE_NAME, "SETTING_UNIT_KEY") ;

    /** フィールド定義 (集約作業No.(<code>COLLECT_JOB_NO</code>)) */
    public static final FieldName COLLECT_JOB_NO = new FieldName(STORE_NAME, "COLLECT_JOB_NO") ;

    /** フィールド定義 (クロスドック連携キー(<code>CROSS_DOCK_UKEY</code>)) */
    public static final FieldName CROSS_DOCK_UKEY = new FieldName(STORE_NAME, "CROSS_DOCK_UKEY") ;

    /** フィールド定義 (作業区分(<code>JOB_TYPE</code>)) */
    public static final FieldName JOB_TYPE = new FieldName(STORE_NAME, "JOB_TYPE") ;

    /** フィールド定義 (出荷検品状態フラグ(<code>WORK_STATUS_FLAG</code>)) */
    public static final FieldName WORK_STATUS_FLAG = new FieldName(STORE_NAME, "WORK_STATUS_FLAG") ;

    /** フィールド定義 (バース登録状態フラグ(<code>BERTH_STATUS_FLAG</code>)) */
    public static final FieldName BERTH_STATUS_FLAG = new FieldName(STORE_NAME, "BERTH_STATUS_FLAG") ;

    /** フィールド定義 (ハードウェア区分(<code>HARDWARE_TYPE</code>)) */
    public static final FieldName HARDWARE_TYPE = new FieldName(STORE_NAME, "HARDWARE_TYPE") ;

    /** フィールド定義 (予定一意キー(<code>PLAN_UKEY</code>)) */
    public static final FieldName PLAN_UKEY = new FieldName(STORE_NAME, "PLAN_UKEY") ;

    /** フィールド定義 (TC/DC区分(<code>TCDC_FLAG</code>)) */
    public static final FieldName TCDC_FLAG = new FieldName(STORE_NAME, "TCDC_FLAG") ;

    /** フィールド定義 (予定日(<code>PLAN_DAY</code>)) */
    public static final FieldName PLAN_DAY = new FieldName(STORE_NAME, "PLAN_DAY") ;

    /** フィールド定義 (バッチNo.(<code>BATCH_NO</code>)) */
    public static final FieldName BATCH_NO = new FieldName(STORE_NAME, "BATCH_NO") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (荷主名称(<code>CONSIGNOR_NAME</code>)) */
    public static final FieldName CONSIGNOR_NAME = new FieldName(STORE_NAME, "CONSIGNOR_NAME") ;

    /** フィールド定義 (出荷先コード(<code>CUSTOMER_CODE</code>)) */
    public static final FieldName CUSTOMER_CODE = new FieldName(STORE_NAME, "CUSTOMER_CODE") ;

    /** フィールド定義 (出荷先名称(<code>CUSTOMER_NAME</code>)) */
    public static final FieldName CUSTOMER_NAME = new FieldName(STORE_NAME, "CUSTOMER_NAME") ;

    /** フィールド定義 (出荷伝票No.(<code>SHIP_TICKET_NO</code>)) */
    public static final FieldName SHIP_TICKET_NO = new FieldName(STORE_NAME, "SHIP_TICKET_NO") ;

    /** フィールド定義 (出荷伝票行No.(<code>SHIP_LINE_NO</code>)) */
    public static final FieldName SHIP_LINE_NO = new FieldName(STORE_NAME, "SHIP_LINE_NO") ;

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

    /** フィールド定義 (予定ロットNo(<code>PLAN_LOT_NO</code>)) */
    public static final FieldName PLAN_LOT_NO = new FieldName(STORE_NAME, "PLAN_LOT_NO") ;

    /** フィールド定義 (予定数(<code>PLAN_QTY</code>)) */
    public static final FieldName PLAN_QTY = new FieldName(STORE_NAME, "PLAN_QTY") ;

    /** フィールド定義 (実績数(<code>RESULT_QTY</code>)) */
    public static final FieldName RESULT_QTY = new FieldName(STORE_NAME, "RESULT_QTY") ;

    /** フィールド定義 (欠品数(<code>SHORTAGE_QTY</code>)) */
    public static final FieldName SHORTAGE_QTY = new FieldName(STORE_NAME, "SHORTAGE_QTY") ;

    /** フィールド定義 (実績ロットNo.(<code>RESULT_LOT_NO</code>)) */
    public static final FieldName RESULT_LOT_NO = new FieldName(STORE_NAME, "RESULT_LOT_NO") ;

    /** フィールド定義 (結果バース(<code>RESULT_BERTH</code>)) */
    public static final FieldName RESULT_BERTH = new FieldName(STORE_NAME, "RESULT_BERTH") ;

    /** フィールド定義 (実績報告区分(<code>REPORT_FLAG</code>)) */
    public static final FieldName REPORT_FLAG = new FieldName(STORE_NAME, "REPORT_FLAG") ;

    /** フィールド定義 (出荷検品ユーザID(<code>SHIP_USER_ID</code>)) */
    public static final FieldName SHIP_USER_ID = new FieldName(STORE_NAME, "SHIP_USER_ID") ;

    /** フィールド定義 (出荷検品ユーザ名称(<code>SHIP_USER_NAME</code>)) */
    public static final FieldName SHIP_USER_NAME = new FieldName(STORE_NAME, "SHIP_USER_NAME") ;

    /** フィールド定義 (出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>)) */
    public static final FieldName SHIP_TERMINAL_NO = new FieldName(STORE_NAME, "SHIP_TERMINAL_NO") ;

    /** フィールド定義 (出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>)) */
    public static final FieldName SHIP_WORK_SECOND = new FieldName(STORE_NAME, "SHIP_WORK_SECOND") ;

    /** フィールド定義 (バース登録ユーザID(<code>BERTH_USER_ID</code>)) */
    public static final FieldName BERTH_USER_ID = new FieldName(STORE_NAME, "BERTH_USER_ID") ;

    /** フィールド定義 (バース登録ユーザ名称(<code>BERTH_USER_NAME</code>)) */
    public static final FieldName BERTH_USER_NAME = new FieldName(STORE_NAME, "BERTH_USER_NAME") ;

    /** フィールド定義 (バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>)) */
    public static final FieldName BERTH_TERMINAL_NO = new FieldName(STORE_NAME, "BERTH_TERMINAL_NO") ;

    /** フィールド定義 (バース登録作業秒数(<code>BERTH_WORK_SECOND</code>)) */
    public static final FieldName BERTH_WORK_SECOND = new FieldName(STORE_NAME, "BERTH_WORK_SECOND") ;

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
    public ShipResultView()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

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
     * 作業No.(<code>JOB_NO</code>) に値をセットします。
     * @param value セットする値JOB_NO
     */
    public void setJobNo(String value)  // @@GEN_V3@@
    {
        setValue(JOB_NO, value);
    }

    /**
     * 作業No.(<code>JOB_NO</code>) から値を取得します。
     * @return JOB_NO
     */
    public String getJobNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(JOB_NO, "")) ;
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
     * 集約作業No.(<code>COLLECT_JOB_NO</code>) に値をセットします。
     * @param value セットする値COLLECT_JOB_NO
     */
    public void setCollectJobNo(String value)  // @@GEN_V3@@
    {
        setValue(COLLECT_JOB_NO, value);
    }

    /**
     * 集約作業No.(<code>COLLECT_JOB_NO</code>) から値を取得します。
     * @return COLLECT_JOB_NO
     */
    public String getCollectJobNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(COLLECT_JOB_NO, "")) ;
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
     * ハードウェア区分(<code>HARDWARE_TYPE</code>) に値をセットします。
     * @param value セットする値HARDWARE_TYPE
     */
    public void setHardwareType(String value)  // @@GEN_V3@@
    {
        setValue(HARDWARE_TYPE, value);
    }

    /**
     * ハードウェア区分(<code>HARDWARE_TYPE</code>) から値を取得します。
     * @return HARDWARE_TYPE
     */
    public String getHardwareType()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(HARDWARE_TYPE, "")) ;
    }

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
     * 出荷先名称(<code>CUSTOMER_NAME</code>) に値をセットします。
     * @param value セットする値CUSTOMER_NAME
     */
    public void setCustomerName(String value)  // @@GEN_V3@@
    {
        setValue(CUSTOMER_NAME, value);
    }

    /**
     * 出荷先名称(<code>CUSTOMER_NAME</code>) から値を取得します。
     * @return CUSTOMER_NAME
     */
    public String getCustomerName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CUSTOMER_NAME, "")) ;
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
     * 予定ロットNo(<code>PLAN_LOT_NO</code>) に値をセットします。
     * @param value セットする値PLAN_LOT_NO
     */
    public void setPlanLotNo(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_LOT_NO, value);
    }

    /**
     * 予定ロットNo(<code>PLAN_LOT_NO</code>) から値を取得します。
     * @return PLAN_LOT_NO
     */
    public String getPlanLotNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_LOT_NO, "")) ;
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
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>) に値をセットします。
     * @param value セットする値RESULT_LOT_NO
     */
    public void setResultLotNo(String value)  // @@GEN_V3@@
    {
        setValue(RESULT_LOT_NO, value);
    }

    /**
     * 実績ロットNo.(<code>RESULT_LOT_NO</code>) から値を取得します。
     * @return RESULT_LOT_NO
     */
    public String getResultLotNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RESULT_LOT_NO, "")) ;
    }

    /**
     * 結果バース(<code>RESULT_BERTH</code>) に値をセットします。
     * @param value セットする値RESULT_BERTH
     */
    public void setResultBerth(String value)  // @@GEN_V3@@
    {
        setValue(RESULT_BERTH, value);
    }

    /**
     * 結果バース(<code>RESULT_BERTH</code>) から値を取得します。
     * @return RESULT_BERTH
     */
    public String getResultBerth()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RESULT_BERTH, "")) ;
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
     * 出荷検品ユーザID(<code>SHIP_USER_ID</code>) に値をセットします。
     * @param value セットする値SHIP_USER_ID
     */
    public void setShipUserId(String value)  // @@GEN_V3@@
    {
        setValue(SHIP_USER_ID, value);
    }

    /**
     * 出荷検品ユーザID(<code>SHIP_USER_ID</code>) から値を取得します。
     * @return SHIP_USER_ID
     */
    public String getShipUserId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SHIP_USER_ID, "")) ;
    }

    /**
     * 出荷検品ユーザ名称(<code>SHIP_USER_NAME</code>) に値をセットします。
     * @param value セットする値SHIP_USER_NAME
     */
    public void setShipUserName(String value)  // @@GEN_V3@@
    {
        setValue(SHIP_USER_NAME, value);
    }

    /**
     * 出荷検品ユーザ名称(<code>SHIP_USER_NAME</code>) から値を取得します。
     * @return SHIP_USER_NAME
     */
    public String getShipUserName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SHIP_USER_NAME, "")) ;
    }

    /**
     * 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値SHIP_TERMINAL_NO
     */
    public void setShipTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(SHIP_TERMINAL_NO, value);
    }

    /**
     * 出荷検品端末No.、RFTNo.(<code>SHIP_TERMINAL_NO</code>) から値を取得します。
     * @return SHIP_TERMINAL_NO
     */
    public String getShipTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SHIP_TERMINAL_NO, "")) ;
    }

    /**
     * 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>) に値をセットします。
     * @param value セットする値SHIP_WORK_SECOND
     */
    public void setShipWorkSecond(int value)  // @@GEN_V3@@
    {
        setValue(SHIP_WORK_SECOND, HandlerUtil.toObject(value));
    }

    /**
     * 出荷検品作業秒数(<code>SHIP_WORK_SECOND</code>) から値を取得します。
     * @return SHIP_WORK_SECOND
     */
    public int getShipWorkSecond()  // @@GEN_V3@@
    {
        return getBigDecimal(SHIP_WORK_SECOND, BigDecimal.ZERO).intValue() ;
    }

    /**
     * バース登録ユーザID(<code>BERTH_USER_ID</code>) に値をセットします。
     * @param value セットする値BERTH_USER_ID
     */
    public void setBerthUserId(String value)  // @@GEN_V3@@
    {
        setValue(BERTH_USER_ID, value);
    }

    /**
     * バース登録ユーザID(<code>BERTH_USER_ID</code>) から値を取得します。
     * @return BERTH_USER_ID
     */
    public String getBerthUserId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(BERTH_USER_ID, "")) ;
    }

    /**
     * バース登録ユーザ名称(<code>BERTH_USER_NAME</code>) に値をセットします。
     * @param value セットする値BERTH_USER_NAME
     */
    public void setBerthUserName(String value)  // @@GEN_V3@@
    {
        setValue(BERTH_USER_NAME, value);
    }

    /**
     * バース登録ユーザ名称(<code>BERTH_USER_NAME</code>) から値を取得します。
     * @return BERTH_USER_NAME
     */
    public String getBerthUserName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(BERTH_USER_NAME, "")) ;
    }

    /**
     * バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値BERTH_TERMINAL_NO
     */
    public void setBerthTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(BERTH_TERMINAL_NO, value);
    }

    /**
     * バース登録端末No.、RFTNo.(<code>BERTH_TERMINAL_NO</code>) から値を取得します。
     * @return BERTH_TERMINAL_NO
     */
    public String getBerthTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(BERTH_TERMINAL_NO, "")) ;
    }

    /**
     * バース登録作業秒数(<code>BERTH_WORK_SECOND</code>) に値をセットします。
     * @param value セットする値BERTH_WORK_SECOND
     */
    public void setBerthWorkSecond(int value)  // @@GEN_V3@@
    {
        setValue(BERTH_WORK_SECOND, HandlerUtil.toObject(value));
    }

    /**
     * バース登録作業秒数(<code>BERTH_WORK_SECOND</code>) から値を取得します。
     * @return BERTH_WORK_SECOND
     */
    public int getBerthWorkSecond()  // @@GEN_V3@@
    {
        return getBigDecimal(BERTH_WORK_SECOND, BigDecimal.ZERO).intValue() ;
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
        return "$Id: ShipResultView.java 1544 2008-11-25 09:32:24Z dmori $" ;
    }
}

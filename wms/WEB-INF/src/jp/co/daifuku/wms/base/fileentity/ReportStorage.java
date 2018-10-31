// $Id: ReportStorage.java 1545 2008-11-25 09:33:35Z dmori $
// $LastChangedRevision: 1545 $
package jp.co.daifuku.wms.base.fileentity;

/*
 * Copyright(c) 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.FieldDefine;
import jp.co.daifuku.wms.handler.field.StoreDefineHandler;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;
import jp.co.daifuku.wms.handler.util.HandlerUtil;


/**
 * REPORTSTORAGEのエンティティクラスです。
 *
 * @version $Revision: 1545 $, $Date: 2008-11-25 18:33:35 +0900 (火, 25 11 2008) $
 * @author  shimizu
 * @author  Last commit: $Author: dmori $
 */

public class ReportStorage
        extends AbstractEntity
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** Name of File */
    public static final String STORE_NAME = "REPORTSTORAGE";

    /*
     * ファイル名: REPORTSTORAGE
     * 取消区分 :                      CANCEL_FLAG         N(1)
     * 入庫予定日 :                    PLAN_DAY            N(8)
     * スケジュールNo. :               RECEIVE_TICKET_NO   X(16)
     * スケジュールSeq.No. :           RECEIVE_LINE_NO     9(3)
     * 作業枝番 :                      BRANCH_NO           9(3)
     * 商品コード :                    ITEM_CODE           X(16)
     * 商品名称 :                      ITEM_NAME           A(40)
     * JANコード :                     JAN                 X(13)
     * ケースITF :                     ITF                 X(16)
     * ケース入数 :                    ENTERING_QTY        9(5)
     * ロットNo. :                     PLAN_LOT_NO         X(16)
     * 入庫予定数 :                    PLAN_QTY            9(6)
     * 入庫予定エリア :                PLAN_AREA_NO        X(4)
     * 入庫予定棚 :                    PLAN_LOCATION_NO    N(8)
     * 入庫実績数 :                    RESULT_QTY          9(7)
     * 入庫実績日 :                    WORK_DAY            N(8)
     * 入庫実績エリア :                RESULT_AREA_NO      X(4)
     * 入庫実績棚 :                    RESULT_LOCATION_NO  N(8)
     * 実績ロットNo. :                 RESULT_LOT_NO       X(16)
     * ユーザID :                      USER_ID             X(20)
     * 端末No.、RFTNo. :               TERMINAL_NO         X(4)
     */

    /** フィールド定義 (取消区分(<code>CANCEL_FLAG</code>)) */
    public static final FieldName CANCEL_FLAG = new FieldName(STORE_NAME, "CANCEL_FLAG");

    /** フィールド定義 (入庫予定日(<code>PLAN_DAY</code>)) */
    public static final FieldName PLAN_DAY = new FieldName(STORE_NAME, "PLAN_DAY");

    /** フィールド定義 (スケジュールNo.(<code>RECEIVE_TICKET_NO</code>)) */
    public static final FieldName RECEIVE_TICKET_NO = new FieldName(STORE_NAME, "RECEIVE_TICKET_NO");

    /** フィールド定義 (スケジュールSeq.No.(<code>RECEIVE_LINE_NO</code>)) */
    public static final FieldName RECEIVE_LINE_NO = new FieldName(STORE_NAME, "RECEIVE_LINE_NO");

    /** フィールド定義 (作業枝番(<code>BRANCH_NO</code>)) */
    public static final FieldName BRANCH_NO = new FieldName(STORE_NAME, "BRANCH_NO");

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE");

    /** フィールド定義 (商品名称(<code>ITEM_NAME</code>)) */
    public static final FieldName ITEM_NAME = new FieldName(STORE_NAME, "ITEM_NAME");

    /** フィールド定義 (JANコード(<code>JAN</code>)) */
    public static final FieldName JAN = new FieldName(STORE_NAME, "JAN");

    /** フィールド定義 (ケースITF(<code>ITF</code>)) */
    public static final FieldName ITF = new FieldName(STORE_NAME, "ITF");

    /** フィールド定義 (ケース入数(<code>ENTERING_QTY</code>)) */
    public static final FieldName ENTERING_QTY = new FieldName(STORE_NAME, "ENTERING_QTY");

    /** フィールド定義 (ロットNo.(<code>PLAN_LOT_NO</code>)) */
    public static final FieldName PLAN_LOT_NO = new FieldName(STORE_NAME, "PLAN_LOT_NO");

    /** フィールド定義 (入庫予定数(<code>PLAN_QTY</code>)) */
    public static final FieldName PLAN_QTY = new FieldName(STORE_NAME, "PLAN_QTY");

    /** フィールド定義 (入庫予定エリア(<code>PLAN_AREA_NO</code>)) */
    public static final FieldName PLAN_AREA_NO = new FieldName(STORE_NAME, "PLAN_AREA_NO");

    /** フィールド定義 (入庫予定棚(<code>PLAN_LOCATION_NO</code>)) */
    public static final FieldName PLAN_LOCATION_NO = new FieldName(STORE_NAME, "PLAN_LOCATION_NO");

    /** フィールド定義 (入庫実績数(<code>RESULT_QTY</code>)) */
    public static final FieldName RESULT_QTY = new FieldName(STORE_NAME, "RESULT_QTY");

    /** フィールド定義 (入庫実績日(<code>WORK_DAY</code>)) */
    public static final FieldName WORK_DAY = new FieldName(STORE_NAME, "WORK_DAY");

    /** フィールド定義 (入庫実績エリア(<code>RESULT_AREA_NO</code>)) */
    public static final FieldName RESULT_AREA_NO = new FieldName(STORE_NAME, "RESULT_AREA_NO");

    /** フィールド定義 (入庫実績棚(<code>RESULT_LOCATION_NO</code>)) */
    public static final FieldName RESULT_LOCATION_NO = new FieldName(STORE_NAME, "RESULT_LOCATION_NO");

    /** フィールド定義 (実績ロットNo.(<code>RESULT_LOT_NO</code>)) */
    public static final FieldName RESULT_LOT_NO = new FieldName(STORE_NAME, "RESULT_LOT_NO");

    /** フィールド定義 (ユーザID(<code>USER_ID</code>)) */
    public static final FieldName USER_ID = new FieldName(STORE_NAME, "USER_ID");

    /** フィールド定義 (端末No.、RFTNo.(<code>TERMINAL_NO</code>)) */
    public static final FieldName TERMINAL_NO = new FieldName(STORE_NAME, "TERMINAL_NO");


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar;
    /** Store meta data for this entity */
    public static final StoreMetaData $storeMetaData = StoreDefineHandler.createStoreMetaData(
            STORE_NAME,
            new File(HandlerSysDefines.DEFINE_DIR, "filestores.xml"),    //    %%StoreMetadata
            new File(HandlerSysDefines.DEFINE_DIR, "filefields.xml")    //    %%FieldMetadata
            );

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     *  フィールド名リストを準備してインスタンスを生成します。
     */
    public ReportStorage()
    {
        super();
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 取消区分(<code>CANCEL_FLAG</code>) に値をセットします。
     * @param value セットする値CANCEL_FLAG
     */
    public void setCancelFlag(String value)  // @@GEN_V3@@
    {
        setValue(CANCEL_FLAG, value);
    }

    /**
     * 取消区分(<code>CANCEL_FLAG</code>) から値を取得します。
     * @return    取消区分
     */
    public String getCancelFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(CANCEL_FLAG, ""));
    }

    /**
     * 入庫予定日(<code>PLAN_DAY</code>) に値をセットします。
     * @param value セットする値PLAN_DAY
     */
    public void setPlanDay(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_DAY, value);
    }

    /**
     * 入庫予定日(<code>PLAN_DAY</code>) から値を取得します。
     * @return    入庫予定日
     */
    public String getPlanDay()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_DAY, ""));
    }

    /**
     * スケジュールNo.(<code>RECEIVE_TICKET_NO</code>) に値をセットします。
     * @param value セットする値RECEIVE_TICKET_NO
     */
    public void setReceiveTicketNo(String value)  // @@GEN_V3@@
    {
        setValue(RECEIVE_TICKET_NO, value);
    }

    /**
     * スケジュールNo.(<code>RECEIVE_TICKET_NO</code>) から値を取得します。
     * @return    スケジュールNo.
     */
    public String getReceiveTicketNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RECEIVE_TICKET_NO, ""));
    }

    /**
     * スケジュールSeq.No.(<code>RECEIVE_LINE_NO</code>) に値をセットします。
     * @param value セットする値RECEIVE_LINE_NO
     */
    public void setReceiveLineNo(int value)  // @@GEN_V3@@
    {
        setValue(RECEIVE_LINE_NO, HandlerUtil.toObject(value));
    }

    /**
     * スケジュールSeq.No.(<code>RECEIVE_LINE_NO</code>) から値を取得します。
     * @return    スケジュールSeq.No.
     */
    public int getReceiveLineNo()  // @@GEN_V3@@
    {
        return getBigDecimal(RECEIVE_LINE_NO, BigDecimal.ZERO).intValue();
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>) に値をセットします。
     * @param value セットする値BRANCH_NO
     */
    public void setBranchNo(int value)  // @@GEN_V3@@
    {
        setValue(BRANCH_NO, HandlerUtil.toObject(value));
    }

    /**
     * 作業枝番(<code>BRANCH_NO</code>) から値を取得します。
     * @return    作業枝番
     */
    public int getBranchNo()  // @@GEN_V3@@
    {
        return getBigDecimal(BRANCH_NO, BigDecimal.ZERO).intValue();
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
     * @return    商品コード
     */
    public String getItemCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_CODE, ""));
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
     * @return    商品名称
     */
    public String getItemName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITEM_NAME, ""));
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
     * @return    JANコード
     */
    public String getJan()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(JAN, ""));
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
     * @return    ケースITF
     */
    public String getItf()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(ITF, ""));
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
     * @return    ケース入数
     */
    public int getEnteringQty()  // @@GEN_V3@@
    {
        return getBigDecimal(ENTERING_QTY, BigDecimal.ZERO).intValue();
    }

    /**
     * ロットNo.(<code>PLAN_LOT_NO</code>) に値をセットします。
     * @param value セットする値PLAN_LOT_NO
     */
    public void setPlanLotNo(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_LOT_NO, value);
    }

    /**
     * ロットNo.(<code>PLAN_LOT_NO</code>) から値を取得します。
     * @return    ロットNo.
     */
    public String getPlanLotNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_LOT_NO, ""));
    }

    /**
     * 入庫予定数(<code>PLAN_QTY</code>) に値をセットします。
     * @param value セットする値PLAN_QTY
     */
    public void setPlanQty(int value)  // @@GEN_V3@@
    {
        setValue(PLAN_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 入庫予定数(<code>PLAN_QTY</code>) から値を取得します。
     * @return    入庫予定数
     */
    public int getPlanQty()  // @@GEN_V3@@
    {
        return getBigDecimal(PLAN_QTY, BigDecimal.ZERO).intValue();
    }

    /**
     * 入庫予定エリア(<code>PLAN_AREA_NO</code>) に値をセットします。
     * @param value セットする値PLAN_AREA_NO
     */
    public void setPlanAreaNo(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_AREA_NO, value);
    }

    /**
     * 入庫予定エリア(<code>PLAN_AREA_NO</code>) から値を取得します。
     * @return    入庫予定エリア
     */
    public String getPlanAreaNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_AREA_NO, ""));
    }

    /**
     * 入庫予定棚(<code>PLAN_LOCATION_NO</code>) に値をセットします。
     * @param value セットする値PLAN_LOCATION_NO
     */
    public void setPlanLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(PLAN_LOCATION_NO, value);
    }

    /**
     * 入庫予定棚(<code>PLAN_LOCATION_NO</code>) から値を取得します。
     * @return    入庫予定棚
     */
    public String getPlanLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(PLAN_LOCATION_NO, ""));
    }

    /**
     * 入庫実績数(<code>RESULT_QTY</code>) に値をセットします。
     * @param value セットする値RESULT_QTY
     */
    public void setResultQty(int value)  // @@GEN_V3@@
    {
        setValue(RESULT_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 入庫実績数(<code>RESULT_QTY</code>) から値を取得します。
     * @return    入庫実績数
     */
    public int getResultQty()  // @@GEN_V3@@
    {
        return getBigDecimal(RESULT_QTY, BigDecimal.ZERO).intValue();
    }

    /**
     * 入庫実績日(<code>WORK_DAY</code>) に値をセットします。
     * @param value セットする値WORK_DAY
     */
    public void setWorkDay(String value)  // @@GEN_V3@@
    {
        setValue(WORK_DAY, value);
    }

    /**
     * 入庫実績日(<code>WORK_DAY</code>) から値を取得します。
     * @return    入庫実績日
     */
    public String getWorkDay()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(WORK_DAY, ""));
    }

    /**
     * 入庫実績エリア(<code>RESULT_AREA_NO</code>) に値をセットします。
     * @param value セットする値RESULT_AREA_NO
     */
    public void setResultAreaNo(String value)  // @@GEN_V3@@
    {
        setValue(RESULT_AREA_NO, value);
    }

    /**
     * 入庫実績エリア(<code>RESULT_AREA_NO</code>) から値を取得します。
     * @return    入庫実績エリア
     */
    public String getResultAreaNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RESULT_AREA_NO, ""));
    }

    /**
     * 入庫実績棚(<code>RESULT_LOCATION_NO</code>) に値をセットします。
     * @param value セットする値RESULT_LOCATION_NO
     */
    public void setResultLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(RESULT_LOCATION_NO, value);
    }

    /**
     * 入庫実績棚(<code>RESULT_LOCATION_NO</code>) から値を取得します。
     * @return    入庫実績棚
     */
    public String getResultLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RESULT_LOCATION_NO, ""));
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
     * @return    実績ロットNo.
     */
    public String getResultLotNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(RESULT_LOT_NO, ""));
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
     * @return    ユーザID
     */
    public String getUserId()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(USER_ID, ""));
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>) に値をセットします。
     * @param value セットする値TERMINAL_NO
     */
    public void setTerminalNo(String value)  // @@GEN_V3@@
    {
        setValue(TERMINAL_NO, value);
    }

    /**
     * 端末No.、RFTNo.(<code>TERMINAL_NO</code>) から値を取得します。
     * @return    端末No.、RFTNo.
     */
    public String getTerminalNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TERMINAL_NO, ""));
    }


    /**
     *  ストアメタデータを返します。
     *  @return このエンティティ用ストアメタデータ
     */
    public StoreMetaData getStoreMetaData()
    {
        return $storeMetaData;
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
     *  このクラスのリビジョンを返します。
     *  @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: ReportStorage.java 1545 2008-11-25 09:33:35Z dmori $";
    }
}

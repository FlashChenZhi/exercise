// $Id: InventSetting.java 1544 2008-11-25 09:32:24Z dmori $
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
 * INVENTSETTINGのエンティティクラスです。
 *
 * @version $Revision: 1544 $, $Date: 2008-11-25 18:32:24 +0900 (火, 25 11 2008) $
 * @author  ss
 * @author  Last commit: $Author: dmori $
 */

public class InventSetting
        extends AbstractDBEntity
        implements SystemDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "DNINVENTSETTING" ;

    /*
     * テーブル名: DNINVENTSETTING
     * スケジュールNo. :               SCHEDULE_NO         varchar2(9)
     * 荷主コード :                    CONSIGNOR_CODE      varchar2(16)
     * エリアNo. :                     AREA_NO             varchar2(4)
     * 開始棚No. :                     FROM_LOCATION_NO    varchar2(8)
     * 終了棚No. :                     TO_LOCATION_NO      varchar2(8)
     * 状態フラグ :                    STATUS_FLAG         varchar2(1)
     * 登録日時 :                      REGIST_DATE         date
     * 登録処理名 :                    REGIST_PNAME        varchar2(48)
     * 最終更新日時 :                  LAST_UPDATE_DATE    date
     * 最終更新処理名 :                LAST_UPDATE_PNAME   varchar2(48)
     */

    /** フィールド定義 (スケジュールNo.(<code>SCHEDULE_NO</code>)) */
    public static final FieldName SCHEDULE_NO = new FieldName(STORE_NAME, "SCHEDULE_NO") ;

    /** フィールド定義 (荷主コード(<code>CONSIGNOR_CODE</code>)) */
    public static final FieldName CONSIGNOR_CODE = new FieldName(STORE_NAME, "CONSIGNOR_CODE") ;

    /** フィールド定義 (エリアNo.(<code>AREA_NO</code>)) */
    public static final FieldName AREA_NO = new FieldName(STORE_NAME, "AREA_NO") ;

    /** フィールド定義 (開始棚No.(<code>FROM_LOCATION_NO</code>)) */
    public static final FieldName FROM_LOCATION_NO = new FieldName(STORE_NAME, "FROM_LOCATION_NO") ;

    /** フィールド定義 (終了棚No.(<code>TO_LOCATION_NO</code>)) */
    public static final FieldName TO_LOCATION_NO = new FieldName(STORE_NAME, "TO_LOCATION_NO") ;

    /** フィールド定義 (状態フラグ(<code>STATUS_FLAG</code>)) */
    public static final FieldName STATUS_FLAG = new FieldName(STORE_NAME, "STATUS_FLAG") ;

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
    public InventSetting()
    {
        super() ;
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>) に値をセットします。
     * @param value セットする値SCHEDULE_NO
     */
    public void setScheduleNo(String value)  // @@GEN_V3@@
    {
        setValue(SCHEDULE_NO, value);
    }

    /**
     * スケジュールNo.(<code>SCHEDULE_NO</code>) から値を取得します。
     * @return SCHEDULE_NO
     */
    public String getScheduleNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SCHEDULE_NO, "")) ;
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
     * 開始棚No.(<code>FROM_LOCATION_NO</code>) に値をセットします。
     * @param value セットする値FROM_LOCATION_NO
     */
    public void setFromLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(FROM_LOCATION_NO, value);
    }

    /**
     * 開始棚No.(<code>FROM_LOCATION_NO</code>) から値を取得します。
     * @return FROM_LOCATION_NO
     */
    public String getFromLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(FROM_LOCATION_NO, "")) ;
    }

    /**
     * 終了棚No.(<code>TO_LOCATION_NO</code>) に値をセットします。
     * @param value セットする値TO_LOCATION_NO
     */
    public void setToLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(TO_LOCATION_NO, value);
    }

    /**
     * 終了棚No.(<code>TO_LOCATION_NO</code>) から値を取得します。
     * @return TO_LOCATION_NO
     */
    public String getToLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(TO_LOCATION_NO, "")) ;
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>) に値をセットします。
     * @param value セットする値STATUS_FLAG
     */
    public void setStatusFlag(String value)  // @@GEN_V3@@
    {
        setValue(STATUS_FLAG, value);
    }

    /**
     * 状態フラグ(<code>STATUS_FLAG</code>) から値を取得します。
     * @return STATUS_FLAG
     */
    public String getStatusFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(STATUS_FLAG, "")) ;
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
        return "$Id: InventSetting.java 1544 2008-11-25 09:32:24Z dmori $" ;
    }
}

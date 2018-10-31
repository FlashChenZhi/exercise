// $Id: HostFixedLocateInfo.java 1545 2008-11-25 09:33:35Z dmori $
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
 * HOSTFIXEDLOCATEINFOのエンティティクラスです。
 *
 * @version $Revision: 1545 $, $Date: 2008-11-25 18:33:35 +0900 (火, 25 11 2008) $
 * @author  shimizu
 * @author  Last commit: $Author: dmori $
 */

public class HostFixedLocateInfo
        extends AbstractEntity
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** Name of File */
    public static final String STORE_NAME = "HOSTFIXEDLOCATEINFO";

    /*
     * ファイル名: HOSTFIXEDLOCATEINFO
     * 取込区分 :                      LOAD_FLAG           N(1)
     * 商品コード :                    ITEM_CODE           X(16)
     * エリア :                        AREA_NO             X(4)
     * 棚 :                            LOCATION_NO         N(8)
     * 最大保管量 :                    REPLENISHMENT_QTY   9(7)
     */

    /** フィールド定義 (取込区分(<code>LOAD_FLAG</code>)) */
    public static final FieldName LOAD_FLAG = new FieldName(STORE_NAME, "LOAD_FLAG");

    /** フィールド定義 (商品コード(<code>ITEM_CODE</code>)) */
    public static final FieldName ITEM_CODE = new FieldName(STORE_NAME, "ITEM_CODE");

    /** フィールド定義 (エリア(<code>AREA_NO</code>)) */
    public static final FieldName AREA_NO = new FieldName(STORE_NAME, "AREA_NO");

    /** フィールド定義 (棚(<code>LOCATION_NO</code>)) */
    public static final FieldName LOCATION_NO = new FieldName(STORE_NAME, "LOCATION_NO");

    /** フィールド定義 (最大保管量(<code>REPLENISHMENT_QTY</code>)) */
    public static final FieldName REPLENISHMENT_QTY = new FieldName(STORE_NAME, "REPLENISHMENT_QTY");


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
    public HostFixedLocateInfo()
    {
        super();
    }

    //------------------------------------------------------------
    // accessors
    //------------------------------------------------------------

    /**
     * 取込区分(<code>LOAD_FLAG</code>) に値をセットします。
     * @param value セットする値LOAD_FLAG
     */
    public void setLoadFlag(String value)  // @@GEN_V3@@
    {
        setValue(LOAD_FLAG, value);
    }

    /**
     * 取込区分(<code>LOAD_FLAG</code>) から値を取得します。
     * @return    取込区分
     */
    public String getLoadFlag()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOAD_FLAG, ""));
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
     * エリア(<code>AREA_NO</code>) に値をセットします。
     * @param value セットする値AREA_NO
     */
    public void setAreaNo(String value)  // @@GEN_V3@@
    {
        setValue(AREA_NO, value);
    }

    /**
     * エリア(<code>AREA_NO</code>) から値を取得します。
     * @return    エリア
     */
    public String getAreaNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(AREA_NO, ""));
    }

    /**
     * 棚(<code>LOCATION_NO</code>) に値をセットします。
     * @param value セットする値LOCATION_NO
     */
    public void setLocationNo(String value)  // @@GEN_V3@@
    {
        setValue(LOCATION_NO, value);
    }

    /**
     * 棚(<code>LOCATION_NO</code>) から値を取得します。
     * @return    棚
     */
    public String getLocationNo()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(LOCATION_NO, ""));
    }

    /**
     * 最大保管量(<code>REPLENISHMENT_QTY</code>) に値をセットします。
     * @param value セットする値REPLENISHMENT_QTY
     */
    public void setReplenishmentQty(int value)  // @@GEN_V3@@
    {
        setValue(REPLENISHMENT_QTY, HandlerUtil.toObject(value));
    }

    /**
     * 最大保管量(<code>REPLENISHMENT_QTY</code>) から値を取得します。
     * @return    最大保管量
     */
    public int getReplenishmentQty()  // @@GEN_V3@@
    {
        return getBigDecimal(REPLENISHMENT_QTY, BigDecimal.ZERO).intValue();
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
        return "$Id: HostFixedLocateInfo.java 1545 2008-11-25 09:33:35Z dmori $";
    }
}

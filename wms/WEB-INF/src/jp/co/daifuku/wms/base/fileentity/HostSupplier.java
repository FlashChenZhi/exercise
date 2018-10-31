// $Id: HostSupplier.java 1545 2008-11-25 09:33:35Z dmori $
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
 * HOSTSUPPLIERのエンティティクラスです。
 *
 * @version $Revision: 1545 $, $Date: 2008-11-25 18:33:35 +0900 (火, 25 11 2008) $
 * @author  shimizu
 * @author  Last commit: $Author: dmori $
 */

public class HostSupplier
        extends AbstractEntity
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** Name of File */
    public static final String STORE_NAME = "HOSTSUPPLIER";

    /*
     * ファイル名: HOSTSUPPLIER
     * 取込区分 :                      LOAD_FLAG           N(1)
     * 仕入先コード :                  SUPPLIER_CODE       X(16)
     * 仕入先名称 :                    SUPPLIER_NAME       A(40)
     */

    /** フィールド定義 (取込区分(<code>LOAD_FLAG</code>)) */
    public static final FieldName LOAD_FLAG = new FieldName(STORE_NAME, "LOAD_FLAG");

    /** フィールド定義 (仕入先コード(<code>SUPPLIER_CODE</code>)) */
    public static final FieldName SUPPLIER_CODE = new FieldName(STORE_NAME, "SUPPLIER_CODE");

    /** フィールド定義 (仕入先名称(<code>SUPPLIER_NAME</code>)) */
    public static final FieldName SUPPLIER_NAME = new FieldName(STORE_NAME, "SUPPLIER_NAME");


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
    public HostSupplier()
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
     * 仕入先コード(<code>SUPPLIER_CODE</code>) に値をセットします。
     * @param value セットする値SUPPLIER_CODE
     */
    public void setSupplierCode(String value)  // @@GEN_V3@@
    {
        setValue(SUPPLIER_CODE, value);
    }

    /**
     * 仕入先コード(<code>SUPPLIER_CODE</code>) から値を取得します。
     * @return    仕入先コード
     */
    public String getSupplierCode()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SUPPLIER_CODE, ""));
    }

    /**
     * 仕入先名称(<code>SUPPLIER_NAME</code>) に値をセットします。
     * @param value セットする値SUPPLIER_NAME
     */
    public void setSupplierName(String value)  // @@GEN_V3@@
    {
        setValue(SUPPLIER_NAME, value);
    }

    /**
     * 仕入先名称(<code>SUPPLIER_NAME</code>) から値を取得します。
     * @return    仕入先名称
     */
    public String getSupplierName()  // @@GEN_V3@@
    {
        return String.valueOf(getValue(SUPPLIER_NAME, ""));
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
        return "$Id: HostSupplier.java 1545 2008-11-25 09:33:35Z dmori $";
    }
}

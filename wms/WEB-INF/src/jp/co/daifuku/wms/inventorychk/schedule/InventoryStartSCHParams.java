// $Id: InventoryStartSCHParams.java 7305 2010-02-26 12:17:16Z fukuwa $
package jp.co.daifuku.wms.inventorychk.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.authentication.DfkUserInfo;

import jp.co.daifuku.foundation.common.ParamKey;
import jp.co.daifuku.foundation.common.ScheduleParams;

/**
 * BusinessクラスとSCH間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7305 $, $Date: 2010-02-26 21:17:16 +0900 (金, 26 2 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: fukuwa $
 */
public class InventoryStartSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA */
    public static final ParamKey AREA = new ParamKey("AREA");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** FUNCTION_ID */
    public static final ParamKey FUNCTION_ID = new ParamKey("FUNCTION_ID");

    /** INVENTORY_ISSUE */
    public static final ParamKey INVENTORY_ISSUE = new ParamKey("INVENTORY_ISSUE");

    /** INVENTORY_STOCK_REPORT */
    public static final ParamKey INVENTORY_STOCK_REPORT = new ParamKey("INVENTORY_STOCK_REPORT");

    /** LOCATION_FROM */
    public static final ParamKey LOCATION_FROM = new ParamKey("LOCATION_FROM");

    /** LOCATION_TO */
    public static final ParamKey LOCATION_TO = new ParamKey("LOCATION_TO");

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ユーザ情報を元にパラメータクラスを作成します。
     * @param dui ユーザ情報
     */
    public InventoryStartSCHParams()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class

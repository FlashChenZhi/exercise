// $Id: ShipLoadDASCHParams.java,v 1.1.1.1 2009/02/10 08:55:47 arai Exp $
package jp.co.daifuku.wms.ship.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.util.Map;
import jp.co.daifuku.bluedog.util.StringParameters;
import jp.co.daifuku.foundation.common.Key;
import jp.co.daifuku.foundation.common.ParamKey;
import jp.co.daifuku.wms.base.common.WmsDASCHParams;

/**
 * BusinessクラスとDASCH間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2009/02/10 08:55:47 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class ShipLoadDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ADDRESS */
    public static final ParamKey ADDRESS = new ParamKey("ADDRESS");

    /** ADDRESS2 */
    public static final ParamKey ADDRESS2 = new ParamKey("ADDRESS2");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** BERTH_NO */
    public static final ParamKey BERTH_NO = new ParamKey("BERTH_NO");

    /** CONTACT */
    public static final ParamKey CONTACT = new ParamKey("CONTACT");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** CUSTOMER_NAME */
    public static final ParamKey CUSTOMER_NAME = new ParamKey("CUSTOMER_NAME");

    /** DFK_DS_NO */
    public static final ParamKey DFK_DS_NO = new ParamKey("DFK_DS_NO");

    /** DFK_USER_ID */
    public static final ParamKey DFK_USER_ID = new ParamKey("DFK_USER_ID");

    /** DFK_USER_NAME */
    public static final ParamKey DFK_USER_NAME = new ParamKey("DFK_USER_NAME");

    /** DOCK_NO */
    public static final ParamKey DOCK_NO = new ParamKey("DOCK_NO");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** INSPECTION */
    public static final ParamKey INSPECTION = new ParamKey("INSPECTION");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** LINE_NO */
    public static final ParamKey LINE_NO = new ParamKey("LINE_NO");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** PLAN_DATE */
    public static final ParamKey PLAN_DATE = new ParamKey("PLAN_DATE");

    /** POSTAL_CODE */
    public static final ParamKey POSTAL_CODE = new ParamKey("POSTAL_CODE");

    /** PREFECTURE */
    public static final ParamKey PREFECTURE = new ParamKey("PREFECTURE");

    /** ROUTE */
    public static final ParamKey ROUTE = new ParamKey("ROUTE");

    /** SETTING_UNIT_KEY */
    public static final ParamKey SETTING_UNIT_KEY = new ParamKey("SETTING_UNIT_KEY");

    /** SHIPPING_CASE_QTY */
    public static final ParamKey SHIPPING_CASE_QTY = new ParamKey("SHIPPING_CASE_QTY");

    /** SHIPPING_PIECE_QTY */
    public static final ParamKey SHIPPING_PIECE_QTY = new ParamKey("SHIPPING_PIECE_QTY");

    /** SYS_DAY */
    public static final ParamKey SYS_DAY = new ParamKey("SYS_DAY");

    /** SYS_TIME */
    public static final ParamKey SYS_TIME = new ParamKey("SYS_TIME");

    /** TELEPHONE */
    public static final ParamKey TELEPHONE = new ParamKey("TELEPHONE");

    /** TICKET_NO */
    public static final ParamKey TICKET_NO = new ParamKey("TICKET_NO");

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
     * Default constructor
     */
    public ShipLoadDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public ShipLoadDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public ShipLoadDASCHParams(Map<Key, Object> initMap)
    {
        super(initMap);
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

// $Id: WmsDASCHParams.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.util.Map;

import jp.co.daifuku.bluedog.util.StringParameters;
import jp.co.daifuku.foundation.common.Key;
import jp.co.daifuku.foundation.common.ParamKey;
import jp.co.daifuku.foundation.common.Params;


/**
 * DASCHを使用する際、eWareNavi固有で使用するメソッド、値を保持します。
 *
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  Y.Okamura
 * @author  Last commit: $Author: arai $
 */


public class WmsDASCHParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /** TABLE_NAME KEY */
    public static final ParamKey TABLE_NAME = new ParamKey("TABLE_NAME");

    /** TABLE_NAME DnWorkInfo */
    public static final String WORK_INFO = "WORK_INFO";

    /** TABLE_NAME DnAreaHistory */
    public static final String AREA_HIST = "AREA_HIST";
    
    /** TABLE_NAME DnAreaHistoryImp */
    public static final String AREA_HIST_IMP = "AREA_HIST_IMP";
    
    /** TABLE_NAME DnItemHistory */
    public static final String ITEM_HIST = "ITEM_HIST";
    
    /** TABLE_NAME DnItemHistoryImp */
    public static final String ITEM_HIST_IMP = "ITEM_HIST_IMP";
    
    /** TABLE_NAME DnLocateHistory */
    public static final String LOCATE_HIST = "LOCATE_HIST";
    
    /** TABLE_NAME DnLocateHistoryImp */
    public static final String LOCATE_HIST_IMP = "LOCATE_HIST_IMP";
    
    /** TABLE_NAME DnCustomerHistory */
    public static final String CUSTOMER_HIST = "CUSTOMER_HIST";
    
    /** TABLE_NAME DnCustomerHistoryImp */
    public static final String CUSTOMER_HIST_IMP = "CUSTOMER_HIST_IMP";
    
    /** TABLE_NAME DnSupplierHistory */
    public static final String SUPPLIER_HIST = "SUPPLIER_HIST";
    
    /** TABLE_NAME DnSupplierHistoryImp */
    public static final String SUPPLIER_HIST_IMP = "SUPPLIER_HIST_IMP";
    
    /** TABLE_NAME DnStockHistory */
    public static final String STOCK_HIST = "STOCK_HIST";
    
    /** TABLE_NAME DnStockHistoryImp */
    public static final String STOCK_HIST_IMP = "STOCK_HIST_IMP";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * default constructor.
     */
    public WmsDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public WmsDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public WmsDASCHParams(Map<Key, Object> initMap)
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
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: WmsDASCHParams.java 3208 2009-03-02 05:42:52Z arai $";
    }
}


package jp.co.daifuku.wms.master.schedule;

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
 * @version
 * @author BusiTune 1.0 Generator.
 */
public class CustomerMasterRegistSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ADDRESS1 */
    public static final ParamKey ADDRESS1 = new ParamKey("ADDRESS1");

    /** ADDRESS2 */
    public static final ParamKey ADDRESS2 = new ParamKey("ADDRESS2");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CONTACT1 */
    public static final ParamKey CONTACT1 = new ParamKey("CONTACT1");

    /** CONTACT2 */
    public static final ParamKey CONTACT2 = new ParamKey("CONTACT2");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** CUSTOMER_NAME */
    public static final ParamKey CUSTOMER_NAME = new ParamKey("CUSTOMER_NAME");

    /** POSTAL_CODE */
    public static final ParamKey POSTAL_CODE = new ParamKey("POSTAL_CODE");

    /** PREFECTURE */
    public static final ParamKey PREFECTURE = new ParamKey("PREFECTURE");

    /** ROUTE */
    public static final ParamKey ROUTE = new ParamKey("ROUTE");

    /** SORT_PLACE */
    public static final ParamKey SORT_PLACE = new ParamKey("SORT_PLACE");

    /** TELEPHONE */
    public static final ParamKey TELEPHONE = new ParamKey("TELEPHONE");

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
    public CustomerMasterRegistSCHParams()
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
     * @return
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class

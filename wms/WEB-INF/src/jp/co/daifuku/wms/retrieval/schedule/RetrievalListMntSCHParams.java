// $Id: RetrievalListMntSCHParams.java,v 1.1.1.1 2009/02/10 08:55:47 arai Exp $
package jp.co.daifuku.wms.retrieval.schedule;

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
 * @version $Revision: 1.1.1.1 $, $Date: 2009/02/10 08:55:47 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class RetrievalListMntSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NAME */
    public static final ParamKey AREA_NAME = new ParamKey("AREA_NAME");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** CUSTOMER_NAME */
    public static final ParamKey CUSTOMER_NAME = new ParamKey("CUSTOMER_NAME");

    /** FROM_ORDER_NO */
    public static final ParamKey FROM_ORDER_NO = new ParamKey("FROM_ORDER_NO");

    /** ORDER_NO */
    public static final ParamKey ORDER_NO = new ParamKey("ORDER_NO");

    /** PLAN_AREA_NO */
    public static final ParamKey PLAN_AREA_NO = new ParamKey("PLAN_AREA_NO");

    /** PLAN_DAY */
    public static final ParamKey PLAN_DAY = new ParamKey("PLAN_DAY");

    /** SETTING_UNIT_KEY */
    public static final ParamKey SETTING_UNIT_KEY = new ParamKey("SETTING_UNIT_KEY");

    /** TO_ORDER_NO */
    public static final ParamKey TO_ORDER_NO = new ParamKey("TO_ORDER_NO");

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
    public RetrievalListMntSCHParams(DfkUserInfo dui)
    {
        super(dui);
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

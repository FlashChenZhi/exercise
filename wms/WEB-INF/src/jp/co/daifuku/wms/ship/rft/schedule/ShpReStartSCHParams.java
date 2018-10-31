// $Id$
package jp.co.daifuku.wms.ship.rft.schedule;

/*
 * Copyright(c) 2000-2011 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.foundation.common.ParamKey;
import jp.co.daifuku.foundation.common.ScheduleParams;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class ShpReStartSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CONSIGNOR_NAME */
    public static final ParamKey CONSIGNOR_NAME = new ParamKey("CONSIGNOR_NAME");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** CUSTOMER_NAME */
    public static final ParamKey CUSTOMER_NAME = new ParamKey("CUSTOMER_NAME");

    /** PENDING_ITEM */
    public static final ParamKey PENDING_ITEM = new ParamKey("PENDING_ITEM");

    /** PLAN_DATE */
    public static final ParamKey PLAN_DATE = new ParamKey("PLAN_DATE");

    /** QTY_INPUT_ENABLED */
    public static final ParamKey QTY_INPUT_ENABLED = new ParamKey("QTY_INPUT_ENABLED");

    /** SETTING_UNIT_KEY */
    public static final ParamKey SETTING_UNIT_KEY = new ParamKey("SETTING_UNIT_KEY");

    /** START_DATE_TIME */
    public static final ParamKey START_DATE_TIME = new ParamKey("START_DATE_TIME");

    /** TERMINAL_NO */
    public static final ParamKey TERMINAL_NO = new ParamKey("TERMINAL_NO");

    /** TOTAL_ITEM */
    public static final ParamKey TOTAL_ITEM = new ParamKey("TOTAL_ITEM");

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
    public ShpReStartSCHParams()
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
     * Returns current repository info for this class
     * @return version
     */
    public static String getVersion()
    {
        return "$Id$";
    }
}
//end of class

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
public class ShpInspectionSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** BUNDLE_ENTERING_QTY */
    public static final ParamKey BUNDLE_ENTERING_QTY = new ParamKey("BUNDLE_ENTERING_QTY");

    /** BUNDLE_ITF */
    public static final ParamKey BUNDLE_ITF = new ParamKey("BUNDLE_ITF");

    /** CASE_INPUT_ENABLED */
    public static final ParamKey CASE_INPUT_ENABLED = new ParamKey("CASE_INPUT_ENABLED");

    /** COLLECT_JOB_NO */
    public static final ParamKey COLLECT_JOB_NO = new ParamKey("COLLECT_JOB_NO");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** ITF */
    public static final ParamKey ITF = new ParamKey("ITF");

    /** JAN */
    public static final ParamKey JAN = new ParamKey("JAN");

    /** PENDING_ITEM */
    public static final ParamKey PENDING_ITEM = new ParamKey("PENDING_ITEM");

    /** PLAN_CASE_QTY */
    public static final ParamKey PLAN_CASE_QTY = new ParamKey("PLAN_CASE_QTY");

    /** PLAN_DATE */
    public static final ParamKey PLAN_DATE = new ParamKey("PLAN_DATE");

    /** PLAN_LOT_NO */
    public static final ParamKey PLAN_LOT_NO = new ParamKey("PLAN_LOT_NO");

    /** PLAN_PIECE_QTY */
    public static final ParamKey PLAN_PIECE_QTY = new ParamKey("PLAN_PIECE_QTY");

    /** PLAN_QTY */
    public static final ParamKey PLAN_QTY = new ParamKey("PLAN_QTY");

    /** QTY_INPUT_ENABLED */
    public static final ParamKey QTY_INPUT_ENABLED = new ParamKey("QTY_INPUT_ENABLED");

    /** RESULT_CASE_QTY */
    public static final ParamKey RESULT_CASE_QTY = new ParamKey("RESULT_CASE_QTY");

    /** RESULT_LOT_NO */
    public static final ParamKey RESULT_LOT_NO = new ParamKey("RESULT_LOT_NO");

    /** RESULT_PIECE_QTY */
    public static final ParamKey RESULT_PIECE_QTY = new ParamKey("RESULT_PIECE_QTY");

    /** RESULT_QTY */
    public static final ParamKey RESULT_QTY = new ParamKey("RESULT_QTY");

    /** SCAN_CODE */
    public static final ParamKey SCAN_CODE = new ParamKey("SCAN_CODE");

    /** SETTING_UNIT_KEY */
    public static final ParamKey SETTING_UNIT_KEY = new ParamKey("SETTING_UNIT_KEY");

    /** SHIPPING_ITF_TO_JAN */
    public static final ParamKey SHIPPING_ITF_TO_JAN = new ParamKey("SHIPPING_ITF_TO_JAN");

    /** START_DATE_TIME */
    public static final ParamKey START_DATE_TIME = new ParamKey("START_DATE_TIME");

    /** TERMINAL_NO */
    public static final ParamKey TERMINAL_NO = new ParamKey("TERMINAL_NO");

    /** TICKET_LINE_NO */
    public static final ParamKey TICKET_LINE_NO = new ParamKey("TICKET_LINE_NO");

    /** TICKET_NO */
    public static final ParamKey TICKET_NO = new ParamKey("TICKET_NO");

    /** TOTAL_ITEM */
    public static final ParamKey TOTAL_ITEM = new ParamKey("TOTAL_ITEM");

    /** USER_ID */
    public static final ParamKey USER_ID = new ParamKey("USER_ID");

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
    public ShpInspectionSCHParams()
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

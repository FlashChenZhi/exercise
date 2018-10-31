// $Id$
package jp.co.daifuku.wms.receiving.rft.schedule;

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
public class RecCompleteSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** COLLECT_JOB_NO */
    public static final ParamKey COLLECT_JOB_NO = new ParamKey("COLLECT_JOB_NO");

    /** COMPLETION_FLAG */
    public static final ParamKey COMPLETION_FLAG = new ParamKey("COMPLETION_FLAG");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** FORCE_STRAGE_FLAG */
    public static final ParamKey FORCE_STRAGE_FLAG = new ParamKey("FORCE_STRAGE_FLAG");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITF */
    public static final ParamKey ITF = new ParamKey("ITF");

    /** JAN */
    public static final ParamKey JAN = new ParamKey("JAN");

    /** MISS_SCAN_COUNT */
    public static final ParamKey MISS_SCAN_COUNT = new ParamKey("MISS_SCAN_COUNT");

    /** PLAN_DATE */
    public static final ParamKey PLAN_DATE = new ParamKey("PLAN_DATE");

    /** PLAN_LOT_NO */
    public static final ParamKey PLAN_LOT_NO = new ParamKey("PLAN_LOT_NO");

    /** RESULT_LOT_NO */
    public static final ParamKey RESULT_LOT_NO = new ParamKey("RESULT_LOT_NO");

    /** RESULT_QTY */
    public static final ParamKey RESULT_QTY = new ParamKey("RESULT_QTY");

    /** SETTING_UNIT_KEY */
    public static final ParamKey SETTING_UNIT_KEY = new ParamKey("SETTING_UNIT_KEY");

    /** START_DATE_TIME */
    public static final ParamKey START_DATE_TIME = new ParamKey("START_DATE_TIME");

    /** SUPPLIER_CODE */
    public static final ParamKey SUPPLIER_CODE = new ParamKey("SUPPLIER_CODE");

    /** TERMINAL_NO */
    public static final ParamKey TERMINAL_NO = new ParamKey("TERMINAL_NO");

    /** TICKET_LINE_NO */
    public static final ParamKey TICKET_LINE_NO = new ParamKey("TICKET_LINE_NO");

    /** TICKET_NO */
    public static final ParamKey TICKET_NO = new ParamKey("TICKET_NO");

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
    public RecCompleteSCHParams()
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

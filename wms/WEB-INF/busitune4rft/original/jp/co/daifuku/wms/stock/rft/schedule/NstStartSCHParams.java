// $Id$
package jp.co.daifuku.wms.stock.rft.schedule;

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
public class NstStartSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** BUNDLE_ENTERING_QTY */
    public static final ParamKey BUNDLE_ENTERING_QTY = new ParamKey("BUNDLE_ENTERING_QTY");

    /** BUNDLE_ITF */
    public static final ParamKey BUNDLE_ITF = new ParamKey("BUNDLE_ITF");

    /** CASE_INPUT_ENABLED */
    public static final ParamKey CASE_INPUT_ENABLED = new ParamKey("CASE_INPUT_ENABLED");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CONSIGNOR_NAME */
    public static final ParamKey CONSIGNOR_NAME = new ParamKey("CONSIGNOR_NAME");

    /** ENTERING_QTY */
    public static final ParamKey ENTERING_QTY = new ParamKey("ENTERING_QTY");

    /** ENTERING_QTY_INPUT_ENABLED */
    public static final ParamKey ENTERING_QTY_INPUT_ENABLED = new ParamKey("ENTERING_QTY_INPUT_ENABLED");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** ITF */
    public static final ParamKey ITF = new ParamKey("ITF");

    /** JAN */
    public static final ParamKey JAN = new ParamKey("JAN");

    /** NO_PLAN_STORAGE_ITF_TO_JAN */
    public static final ParamKey NO_PLAN_STORAGE_ITF_TO_JAN = new ParamKey("NO_PLAN_STORAGE_ITF_TO_JAN");

    /** START_DATE_TIME */
    public static final ParamKey START_DATE_TIME = new ParamKey("START_DATE_TIME");

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
    public NstStartSCHParams()
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

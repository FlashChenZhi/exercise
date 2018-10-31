// $Id$
package jp.co.daifuku.wms.retrieval.rft.schedule;

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
public class RetOrderStatusListSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** IS_LOCK */
    public static final ParamKey IS_LOCK = new ParamKey("IS_LOCK");

    /** ORDER_NO1 */
    public static final ParamKey ORDER_NO1 = new ParamKey("ORDER_NO1");

    /** ORDER_NO1_STATUS */
    public static final ParamKey ORDER_NO1_STATUS = new ParamKey("ORDER_NO1_STATUS");

    /** ORDER_NO2 */
    public static final ParamKey ORDER_NO2 = new ParamKey("ORDER_NO2");

    /** ORDER_NO2_STATUS */
    public static final ParamKey ORDER_NO2_STATUS = new ParamKey("ORDER_NO2_STATUS");

    /** ORDER_NO3 */
    public static final ParamKey ORDER_NO3 = new ParamKey("ORDER_NO3");

    /** ORDER_NO3_STATUS */
    public static final ParamKey ORDER_NO3_STATUS = new ParamKey("ORDER_NO3_STATUS");

    /** ORDER_NO4 */
    public static final ParamKey ORDER_NO4 = new ParamKey("ORDER_NO4");

    /** ORDER_NO4_STATUS */
    public static final ParamKey ORDER_NO4_STATUS = new ParamKey("ORDER_NO4_STATUS");

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
    public RetOrderStatusListSCHParams()
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

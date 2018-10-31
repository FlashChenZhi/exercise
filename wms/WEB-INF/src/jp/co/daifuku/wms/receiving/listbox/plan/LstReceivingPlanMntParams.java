// $Id: LstReceivingPlanMntParams.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.receiving.listbox.plan;

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
import jp.co.daifuku.foundation.common.Params;

/**
 * Transfer Object class is used for transfering data  between Business classes (Parent and POPUP screen)
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class LstReceivingPlanMntParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** PLAN_DATE */
    public static final ParamKey PLAN_DATE = new ParamKey("PLAN_DATE");

    /** RECEIVING_PLAN_DATE */
    public static final ParamKey RECEIVING_PLAN_DATE = new ParamKey("RECEIVING_PLAN_DATE");

    /** SLIP_NUMBER */
    public static final ParamKey SLIP_NUMBER = new ParamKey("SLIP_NUMBER");

    /** SUPPLIER_CODE */
    public static final ParamKey SUPPLIER_CODE = new ParamKey("SUPPLIER_CODE");

    /** SUPPLIER_NAME */
    public static final ParamKey SUPPLIER_NAME = new ParamKey("SUPPLIER_NAME");

    /** TICKET */
    public static final ParamKey TICKET = new ParamKey("TICKET");

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
    public LstReceivingPlanMntParams()
    {
        super();
    }

    /**
     * Constructor to create Object with request parameters
     * This constructor when values are passes between two screens
     * For exmaple, from POPUP to parent screen.

     * @param param StringParameters
     * @throws IOException
     */
    public LstReceivingPlanMntParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     *
     * @param initMap Map
     */
    public LstReceivingPlanMntParams(Map<Key, Object> initMap)
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
     * Returns current repository version of this class

     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class

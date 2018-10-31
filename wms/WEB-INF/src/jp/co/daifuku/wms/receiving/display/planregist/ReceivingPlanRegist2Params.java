// $Id: ReceivingPlanRegist2Params.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.receiving.display.planregist;

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
public class ReceivingPlanRegist2Params
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** IN_SLIP */
    public static final ParamKey IN_SLIP = new ParamKey("IN_SLIP");

    /** IN_SUPPLIER_CODE */
    public static final ParamKey IN_SUPPLIER_CODE = new ParamKey("IN_SUPPLIER_CODE");

    /** IN_SUPPLIER_NAME */
    public static final ParamKey IN_SUPPLIER_NAME = new ParamKey("IN_SUPPLIER_NAME");

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
    public ReceivingPlanRegist2Params()
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
    public ReceivingPlanRegist2Params(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     *
     * @param initMap Map
     */
    public ReceivingPlanRegist2Params(Map<Key, Object> initMap)
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

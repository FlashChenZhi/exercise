// $Id$
package jp.co.daifuku.wms.system.display.rftstate;

/*
 * Copyright(c) 2000-2010 DAIFUKU Co.,Ltd. All Rights Reserved.
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
 * @version $Revision$, $Date$
 * @author  BusiTune.
 * @author  Last commit: $Author$
 */
public class RFTStateParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** RFT */
    public static final ParamKey RFT = new ParamKey("RFT");

    /** RFT_STATUS */
    public static final ParamKey RFT_STATUS = new ParamKey("RFT_STATUS");

    /** TERMINAL_FLAG */
    public static final ParamKey TERMINAL_FLAG = new ParamKey("TERMINAL_FLAG");

    /** USER_NAME */
    public static final ParamKey USER_NAME = new ParamKey("USER_NAME");

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
    public RFTStateParams()
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
    public RFTStateParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     *
     * @param initMap Map
     */
    public RFTStateParams(Map<Key, Object> initMap)
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
     * Returns current repository info for this class
     * @return version
     */
    public static String getVersion()
    {
        return "$Id$";
    }
}
//end of class

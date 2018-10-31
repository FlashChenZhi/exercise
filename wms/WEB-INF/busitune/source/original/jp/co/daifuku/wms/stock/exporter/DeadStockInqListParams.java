// $Id$
package jp.co.daifuku.wms.stock.exporter;

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
 * Transfer Object class is used for transfering data between Business and Exporter(CSV,EXCEL) classes
 *
 * @version $Revision$, $Date$
 * @author  BusiTune.
 * @author  Last commit: $Author$
 */
public class DeadStockInqListParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** DFK_DS_NO */
    public static final ParamKey DFK_DS_NO = new ParamKey("DFK_DS_NO");

    /** DFK_USER_ID */
    public static final ParamKey DFK_USER_ID = new ParamKey("DFK_USER_ID");

    /** DFK_USER_NAME */
    public static final ParamKey DFK_USER_NAME = new ParamKey("DFK_USER_NAME");

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

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** RETRIEVAL_DAY */
    public static final ParamKey RETRIEVAL_DAY = new ParamKey("RETRIEVAL_DAY");

    /** STOCK_CASE_QTY */
    public static final ParamKey STOCK_CASE_QTY = new ParamKey("STOCK_CASE_QTY");

    /** STOCK_PIECE_QTY */
    public static final ParamKey STOCK_PIECE_QTY = new ParamKey("STOCK_PIECE_QTY");

    /** SYS_DAY */
    public static final ParamKey SYS_DAY = new ParamKey("SYS_DAY");

    /** SYS_TIME */
    public static final ParamKey SYS_TIME = new ParamKey("SYS_TIME");

    /** TO_RETRIEVAL_DAY */
    public static final ParamKey TO_RETRIEVAL_DAY = new ParamKey("TO_RETRIEVAL_DAY");

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
    public DeadStockInqListParams()
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
    public DeadStockInqListParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     *
     * @param initMap Map
     */
    public DeadStockInqListParams(Map<Key, Object> initMap)
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

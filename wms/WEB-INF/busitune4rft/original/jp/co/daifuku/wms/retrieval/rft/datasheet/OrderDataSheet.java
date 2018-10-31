// $Id$
package jp.co.daifuku.wms.retrieval.rft.datasheet;

/*
 * Copyright(c) 2000-2011 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import jp.co.daifuku.busitune.rft.haisurf.BasicDSModel;
import jp.co.daifuku.busitune.rft.haisurf.DSBooleanField;
import jp.co.daifuku.busitune.rft.haisurf.DSKey;
import jp.co.daifuku.busitune.rft.haisurf.DSStringField;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class OrderDataSheet
        extends BasicDSModel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DATASHEET_NAME */
    private static final String DATASHEET_NAME = "OrderDataSheet";

    /** FOCUS_FIELD */
    public static final DSKey FOCUS_FIELD = new DSKey("FOCUS_FIELD", new DSStringField(), false, 0);

    /** ORDER_NO1 */
    public static final DSKey ORDER_NO1 = new DSKey("ORDER_NO1", new DSStringField(), false, 0);

    /** ORDER_NO1_INPUT_ENABLED */
    public static final DSKey ORDER_NO1_INPUT_ENABLED = new DSKey("ORDER_NO1_INPUT_ENABLED", new DSBooleanField(), false);

    /** ORDER_NO2 */
    public static final DSKey ORDER_NO2 = new DSKey("ORDER_NO2", new DSStringField(), false, 0);

    /** ORDER_NO2_INPUT_ENABLED */
    public static final DSKey ORDER_NO2_INPUT_ENABLED = new DSKey("ORDER_NO2_INPUT_ENABLED", new DSBooleanField(), false);

    /** ORDER_NO3 */
    public static final DSKey ORDER_NO3 = new DSKey("ORDER_NO3", new DSStringField(), false, 0);

    /** ORDER_NO3_INPUT_ENABLED */
    public static final DSKey ORDER_NO3_INPUT_ENABLED = new DSKey("ORDER_NO3_INPUT_ENABLED", new DSBooleanField(), false);

    /** ORDER_NO4 */
    public static final DSKey ORDER_NO4 = new DSKey("ORDER_NO4", new DSStringField(), false, 0);

    /** ORDER_NO4_INPUT_ENABLED */
    public static final DSKey ORDER_NO4_INPUT_ENABLED = new DSKey("ORDER_NO4_INPUT_ENABLED", new DSBooleanField(), false);

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
     *
     * @param dsf DataSheet factory
     * @param locale Locale
     */
    public OrderDataSheet(DataSheetFactory dsf, Locale locale)
    {
        super(DATASHEET_NAME, dsf, locale);
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

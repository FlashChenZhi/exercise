// $Id: LocationListDataSheet.java 8041 2012-05-17 09:39:40Z nagao $
package jp.co.daifuku.wms.base.rft.datasheet;

/*
 * Copyright(c) 2000-2011 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import jp.co.daifuku.busitune.rft.haisurf.BasicDSModel;
import jp.co.daifuku.busitune.rft.haisurf.DSKey;
import jp.co.daifuku.busitune.rft.haisurf.DSStringField;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision: 8041 $, $Date: 2012-05-17 18:39:40 +0900 (木, 17 5 2012) $
 * @author  HighTune.
 * @author  Last commit: $Author: nagao $
 */
public class LocationListDataSheet
        extends BasicDSModel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DATASHEET_NAME */
    private static final String DATASHEET_NAME = "LocationListDataSheet";

    /** AREA_LOCATION */
    public static final DSKey AREA_LOCATION = new DSKey("AREA_LOCATION", new DSStringField(), true, 0);

    /** AREA_NO */
    public static final DSKey AREA_NO = new DSKey("AREA_NO", new DSStringField(), true, 0);

    /** DISP_LOCATION */
    public static final DSKey DISP_LOCATION = new DSKey("DISP_LOCATION", new DSStringField(), true, 0);

    /** LOCATION_NO */
    public static final DSKey LOCATION_NO = new DSKey("LOCATION_NO", new DSStringField(), true, 0);

    /** TITLE */
    public static final DSKey TITLE = new DSKey("TITLE", new DSStringField(), false, 0);

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
    public LocationListDataSheet(DataSheetFactory dsf, Locale locale)
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
        return "$Id: LocationListDataSheet.java 8041 2012-05-17 09:39:40Z nagao $";
    }
}
//end of class

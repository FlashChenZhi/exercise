// $Id$
package jp.co.daifuku.wms.stock.rft.display;

/*
 * Copyright(c) 2000-2011 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import jp.co.daifuku.busitune.rft.haisurf.AbstractHSBusiness;
import jp.co.daifuku.busitune.rft.haisurf.DataSheetModel;
import jp.co.daifuku.busitune.rft.haisurf.HSResult;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.rft.HaiSurfExceptionHandler;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.RftConst;
import jp.co.daifuku.wms.base.rft.datasheet.LocationDataSheet;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class StoLocationSearchBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "StoLocationSearch";

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
    public StoLocationSearchBusiness()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * callback at screen event.
     *
     * @param dsf data sheet
     * @param locale Terminal locale
     * @return executed result
     */
    public HSResult page_Load(DataSheetFactory dsf, Locale locale)
    {
        // set focus.
        setFocus("AreaNo");

        // set result code.
        return new HSResult(ResultConst.NORMAL);
    }

    /**
     * callback at screen event.
     *
     * @param dsf data sheet
     * @param locale Terminal locale
     * @return executed result
     */
    public HSResult areaNo_Enter(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        LocationDataSheet locationDataSheet = new LocationDataSheet(dsf, locale);

        // input validation.
        if (StringUtil.isBlank(locationDataSheet.getText(LocationDataSheet.SEARCH_AREA)))
        {
            setBeep(RftConst.BEEP);
            setFocus("AreaNo");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set focus.
        setFocus("SearchLoc");

        // set result code.
        return new HSResult(ResultConst.NORMAL);
    }

    /**
     * callback at screen event.
     *
     * @param dsf data sheet
     * @param locale Terminal locale
     * @return executed result
     */
    public HSResult searchLoc_Enter(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        LocationDataSheet locationDataSheet = new LocationDataSheet(dsf, locale);

        // input validation.
        if (StringUtil.isBlank(locationDataSheet.getText(LocationDataSheet.SEARCH_LOCATION)))
        {
            setBeep(RftConst.BEEP);
            setFocus("SearchLoc");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set result code.
        return new HSResult(ResultConst.NORMAL);
    }

    /**
     * callback at screen event.
     *
     * @param dsf data sheet
     * @param locale Terminal locale
     * @return executed result
     */
    public HSResult back_Click(DataSheetFactory dsf, Locale locale)
    {
        // set result code.
        return new HSResult(ResultConst.NORMAL);
    }

    /**
     * callback at screen event.
     *
     * @param dsf data sheet
     * @param locale Terminal locale
     * @return executed result
     */
    public HSResult list1_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        LocationDataSheet locationDataSheet = new LocationDataSheet(dsf, locale);

        // input validation.
        if (StringUtil.isBlank(locationDataSheet.getText(LocationDataSheet.SEARCH_AREA)))
        {
            setBeep(RftConst.BEEP);
            setFocus("AreaNo");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        locationDataSheet.setValue(LocationDataSheet.SEARCH_LOCATION, RftConst.LOCATION_FLAG_EMP);

        // set result code.
        return new HSResult(ResultConst.NORMAL);
    }

    /**
     * callback at screen event.
     *
     * @param dsf data sheet
     * @param locale Terminal locale
     * @return executed result
     */
    public HSResult list2_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        LocationDataSheet locationDataSheet = new LocationDataSheet(dsf, locale);

        // input validation.
        if (StringUtil.isBlank(locationDataSheet.getText(LocationDataSheet.SEARCH_AREA)))
        {
            setBeep(RftConst.BEEP);
            setFocus("AreaNo");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        locationDataSheet.setValue(LocationDataSheet.SEARCH_LOCATION, RftConst.LOCATION_FLAG_SPLY);

        // set result code.
        return new HSResult(ResultConst.NORMAL);
    }

    /**
     * callback at screen event.
     *
     * @param dsf data sheet
     * @param locale Terminal locale
     * @return executed result
     */
    public HSResult return_Click(DataSheetFactory dsf, Locale locale)
    {
        // set result code.
        return new HSResult(ResultConst.NORMAL);
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * returns screen name for business class.
     *
     * @return screen name of HaiSurf.
     */
    public String getScreenName()
    {
        return SCREEN_NAME;
    }

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
     *
     * @return version
     */
    public static String getVersion()
    {
        return "$Id$";
    }
}
//end of class

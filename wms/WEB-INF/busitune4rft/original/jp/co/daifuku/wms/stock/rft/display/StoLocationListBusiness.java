// $Id$
package jp.co.daifuku.wms.stock.rft.display;

/*
 * Copyright(c) 2000-2011 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.busitune.rft.haisurf.AbstractHSBusiness;
import jp.co.daifuku.busitune.rft.haisurf.DataSheetModel;
import jp.co.daifuku.busitune.rft.haisurf.HSResult;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.rft.HaiSurfExceptionHandler;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.datasheet.LocationDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.LocationListDataSheet;
import jp.co.daifuku.wms.stock.rft.schedule.StoLocationListSCH;
import jp.co.daifuku.wms.stock.rft.schedule.StoLocationListSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class StoLocationListBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "StoLocationList";

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
    public StoLocationListBusiness()
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
        // initialize DataSheets.
        LocationDataSheet locationDataSheet = new LocationDataSheet(dsf, locale);
        LocationListDataSheet locationListDataSheet = new LocationListDataSheet(dsf, locale);

        Connection conn = null;
        StoLocationListSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new StoLocationListSCH(conn, this.getClass(), locale);

            // set input parameters.
            StoLocationListSCHParams inparam = new StoLocationListSCHParams();
            inparam.set(StoLocationListSCHParams.CONSIGNOR_CODE, locationDataSheet.getValue(LocationDataSheet.CONSIGNOR_CODE));
            inparam.set(StoLocationListSCHParams.SEARCH_AREA, locationDataSheet.getValue(LocationDataSheet.SEARCH_AREA));
            inparam.set(StoLocationListSCHParams.SEARCH_LOCATION, locationDataSheet.getValue(LocationDataSheet.SEARCH_LOCATION));
            inparam.set(StoLocationListSCHParams.ITEM_CODE, locationDataSheet.getValue(LocationDataSheet.ITEM_CODE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            locationListDataSheet.setValue(DataSheetModel.SIZE, outparams.size());
            for (int i = 0; i < outparams.size(); i++)
            {
                Params outparam = outparams.get(i);
                locationListDataSheet.setValue(LocationListDataSheet.AREA_LOCATION, outparam.get(StoLocationListSCHParams.AREA_LOCATION), i);
                locationListDataSheet.setValue(LocationListDataSheet.AREA_NO, outparam.get(StoLocationListSCHParams.AREA_NO), i);
                locationListDataSheet.setValue(LocationListDataSheet.LOCATION_NO, outparam.get(StoLocationListSCHParams.LOCATION_NO), i);
                locationListDataSheet.setValue(LocationListDataSheet.DISP_LOCATION, outparam.get(StoLocationListSCHParams.DISP_LOCATION), i);
                locationListDataSheet.setValue(LocationListDataSheet.TITLE, outparam.get(StoLocationListSCHParams.TITLE));
            }

            // initialize DataSheet view.
            locationListDataSheet.initView();

            // set result code.
            return new HSResult(ResultConst.NORMAL);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return HaiSurfExceptionHandler.getResult(ex, this.getClass(), dsf, locale);
        }
        finally
        {
            DBUtil.close(conn);
        }
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
    public HSResult ent_Click(DataSheetFactory dsf, Locale locale)
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

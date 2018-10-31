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
import jp.co.daifuku.wms.base.rft.datasheet.ConsignorDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.LocationDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.NoPlanRetrievalStockListDataSheet;
import jp.co.daifuku.wms.stock.rft.schedule.NrtStockListSCH;
import jp.co.daifuku.wms.stock.rft.schedule.NrtStockListSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class NrtStockListBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "NrtStockList";

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
    public NrtStockListBusiness()
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
        ConsignorDataSheet consignorDataSheet = new ConsignorDataSheet(dsf, locale);
        LocationDataSheet locationDataSheet = new LocationDataSheet(dsf, locale);
        NoPlanRetrievalStockListDataSheet noPlanRetrievalStockListDataSheet = new NoPlanRetrievalStockListDataSheet(dsf, locale);

        Connection conn = null;
        NrtStockListSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new NrtStockListSCH(conn, this.getClass(), locale);

            // set input parameters.
            NrtStockListSCHParams inparam = new NrtStockListSCHParams();
            inparam.set(NrtStockListSCHParams.CONSIGNOR_CODE, consignorDataSheet.getValue(ConsignorDataSheet.CONSIGNOR_CODE));
            inparam.set(NrtStockListSCHParams.AREA_NO, locationDataSheet.getValue(LocationDataSheet.AREA_NO));
            inparam.set(NrtStockListSCHParams.LOCATION_NO, locationDataSheet.getValue(LocationDataSheet.LOCATION_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            noPlanRetrievalStockListDataSheet.setValue(DataSheetModel.SIZE, outparams.size());
            for (int i = 0; i < outparams.size(); i++)
            {
                Params outparam = outparams.get(i);
                noPlanRetrievalStockListDataSheet.setValue(NoPlanRetrievalStockListDataSheet.ITEM_CODE, outparam.get(NrtStockListSCHParams.ITEM_CODE), i);
                noPlanRetrievalStockListDataSheet.setValue(NoPlanRetrievalStockListDataSheet.ITEM_NAME, outparam.get(NrtStockListSCHParams.ITEM_NAME), i);
                noPlanRetrievalStockListDataSheet.setValue(NoPlanRetrievalStockListDataSheet.LOT_NO, outparam.get(NrtStockListSCHParams.LOT_NO), i);
            }

            // initialize DataSheet view.
            noPlanRetrievalStockListDataSheet.initView();

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

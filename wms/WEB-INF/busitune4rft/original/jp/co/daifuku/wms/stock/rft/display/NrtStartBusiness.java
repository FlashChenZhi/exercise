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
import jp.co.daifuku.wms.base.rft.datasheet.CommonDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.ConsignorDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.NoPlanRetrievalInDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.NoPlanRetrievalOutDataSheet;
import jp.co.daifuku.wms.stock.rft.schedule.NrtStartSCH;
import jp.co.daifuku.wms.stock.rft.schedule.NrtStartSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class NrtStartBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "NrtStart";

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
    public NrtStartBusiness()
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
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        ConsignorDataSheet consignorDataSheet = new ConsignorDataSheet(dsf, locale);
        NoPlanRetrievalInDataSheet noPlanRetrievalInDataSheet = new NoPlanRetrievalInDataSheet(dsf, locale);
        NoPlanRetrievalOutDataSheet noPlanRetrievalOutDataSheet = new NoPlanRetrievalOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        NrtStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new NrtStartSCH(conn, this.getClass(), locale);

            // set input parameters.
            NrtStartSCHParams inparam = new NrtStartSCHParams();
            inparam.set(NrtStartSCHParams.CONSIGNOR_CODE, noPlanRetrievalInDataSheet.getValue(NoPlanRetrievalInDataSheet.CONSIGNOR_CODE));
            inparam.set(NrtStartSCHParams.AREA_NO, noPlanRetrievalInDataSheet.getValue(NoPlanRetrievalInDataSheet.AREA_NO));
            inparam.set(NrtStartSCHParams.LOCATION_NO, noPlanRetrievalInDataSheet.getValue(NoPlanRetrievalInDataSheet.LOCATION_NO));
            inparam.set(NrtStartSCHParams.ITEM_CODE, noPlanRetrievalInDataSheet.getValue(NoPlanRetrievalInDataSheet.ITEM_CODE));
            inparam.set(NrtStartSCHParams.LOT_NO, noPlanRetrievalInDataSheet.getValue(NoPlanRetrievalInDataSheet.LOT_NO));
            inparam.set(NrtStartSCHParams.LOT_NO_SELECT, noPlanRetrievalInDataSheet.getValue(NoPlanRetrievalInDataSheet.LOT_NO_SELECT));
            inparam.set(NrtStartSCHParams.NO_PLAN_RETRIEVAL_ITF_TO_JAN, termInfoDataSheet.getValue(TermInfoDataSheet.NO_PLAN_RETRIEVAL_ITF_TO_JAN));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.CONSIGNOR_CODE, outparam.get(NrtStartSCHParams.CONSIGNOR_CODE));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.CONSIGNOR_NAME, outparam.get(NrtStartSCHParams.CONSIGNOR_NAME));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.AREA_LOCATION, outparam.get(NrtStartSCHParams.AREA_LOCATION));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.AREA_NO, outparam.get(NrtStartSCHParams.AREA_NO));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.LOCATION_NO, outparam.get(NrtStartSCHParams.LOCATION_NO));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.ITEM_CODE, outparam.get(NrtStartSCHParams.ITEM_CODE));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.ITEM_NAME, outparam.get(NrtStartSCHParams.ITEM_NAME));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.JAN, outparam.get(NrtStartSCHParams.JAN));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.ITF, outparam.get(NrtStartSCHParams.ITF));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.BUNDLE_ITF, outparam.get(NrtStartSCHParams.BUNDLE_ITF));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.ENTERING_QTY, outparam.get(NrtStartSCHParams.ENTERING_QTY));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.BUNDLE_ENTERING_QTY, outparam.get(NrtStartSCHParams.BUNDLE_ENTERING_QTY));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.LOT_NO, outparam.get(NrtStartSCHParams.LOT_NO));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.RESULT_LOT_NO, outparam.get(NrtStartSCHParams.RESULT_LOT_NO));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.STOCK_QTY, outparam.get(NrtStartSCHParams.STOCK_QTY));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.STOCK_CASE_QTY, outparam.get(NrtStartSCHParams.STOCK_CASE_QTY));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.STOCK_PIECE_QTY, outparam.get(NrtStartSCHParams.STOCK_PIECE_QTY));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.RESULT_QTY, outparam.get(NrtStartSCHParams.RESULT_QTY));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.RESULT_CASE_QTY, outparam.get(NrtStartSCHParams.RESULT_CASE_QTY));
                noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.RESULT_PIECE_QTY, outparam.get(NrtStartSCHParams.RESULT_PIECE_QTY));
                commonDataSheet.setValue(CommonDataSheet.START_DATE_TIME, outparam.get(NrtStartSCHParams.START_DATE_TIME));
                commonDataSheet.setValue(CommonDataSheet.CASE_INPUT_ENABLED, outparam.get(NrtStartSCHParams.CASE_INPUT_ENABLED));
                consignorDataSheet.setValue(ConsignorDataSheet.CONSIGNOR_NAME, outparam.get(NrtStartSCHParams.CONSIGNOR_NAME));
                break;
            }

            commonDataSheet.setValue(CommonDataSheet.MISS_SCAN_COUNT, 0);

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

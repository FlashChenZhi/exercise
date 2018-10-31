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
import jp.co.daifuku.wms.stock.rft.datasheet.ReceivingStorageInDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.ReceivingStorageOutDataSheet;
import jp.co.daifuku.wms.stock.rft.schedule.StdStartSCH;
import jp.co.daifuku.wms.stock.rft.schedule.StdStartSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class StdStartBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "StdStart";

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
    public StdStartBusiness()
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
        ReceivingStorageInDataSheet receivingStorageInDataSheet = new ReceivingStorageInDataSheet(dsf, locale);
        ReceivingStorageOutDataSheet receivingStorageOutDataSheet = new ReceivingStorageOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        StdStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new StdStartSCH(conn, this.getClass(), locale);

            // set input parameters.
            StdStartSCHParams inparam = new StdStartSCHParams();
            inparam.set(StdStartSCHParams.CONSIGNOR_CODE, receivingStorageInDataSheet.getValue(ReceivingStorageInDataSheet.CONSIGNOR_CODE));
            inparam.set(StdStartSCHParams.ITEM_CODE, receivingStorageInDataSheet.getValue(ReceivingStorageInDataSheet.ITEM_CODE));
            inparam.set(StdStartSCHParams.STORAGE_ITF_TO_JAN, termInfoDataSheet.getValue(TermInfoDataSheet.STORAGE_ITF_TO_JAN));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.CONSIGNOR_CODE, outparam.get(StdStartSCHParams.CONSIGNOR_CODE));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.CONSIGNOR_NAME, outparam.get(StdStartSCHParams.CONSIGNOR_NAME));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.ITEM_CODE, outparam.get(StdStartSCHParams.ITEM_CODE));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.ITEM_NAME, outparam.get(StdStartSCHParams.ITEM_NAME));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.AREA_NO, outparam.get(StdStartSCHParams.AREA_NO));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.LOCATION_NO, outparam.get(StdStartSCHParams.LOCATION_NO));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.JAN, outparam.get(StdStartSCHParams.JAN));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.ITF, outparam.get(StdStartSCHParams.ITF));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.BUNDLE_ITF, outparam.get(StdStartSCHParams.BUNDLE_ITF));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.ENTERING_QTY, outparam.get(StdStartSCHParams.ENTERING_QTY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.BUNDLE_ENTERING_QTY, outparam.get(StdStartSCHParams.BUNDLE_ENTERING_QTY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.LOT_NO, outparam.get(StdStartSCHParams.LOT_NO));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.STOCK_QTY, outparam.get(StdStartSCHParams.STOCK_QTY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.STOCK_CASE_QTY, outparam.get(StdStartSCHParams.STOCK_CASE_QTY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.STOCK_PIECE_QTY, outparam.get(StdStartSCHParams.STOCK_PIECE_QTY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.PLAN_AREA_LOCATION, outparam.get(StdStartSCHParams.PLAN_AREA_LOCATION));
                commonDataSheet.setValue(CommonDataSheet.START_DATE_TIME, outparam.get(StdStartSCHParams.START_DATE_TIME));
                commonDataSheet.setValue(CommonDataSheet.CASE_INPUT_ENABLED, outparam.get(StdStartSCHParams.CASE_INPUT_ENABLED));
                consignorDataSheet.setValue(ConsignorDataSheet.CONSIGNOR_NAME, outparam.get(StdStartSCHParams.CONSIGNOR_NAME));
                break;
            }

            receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.IS_STORAGE_START, false);
            commonDataSheet.setValue(CommonDataSheet.MISS_SCAN_COUNT, 0);
            receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_QTY, 0);
            receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_CASE_QTY, 0);
            receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_PIECE_QTY, 0);

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

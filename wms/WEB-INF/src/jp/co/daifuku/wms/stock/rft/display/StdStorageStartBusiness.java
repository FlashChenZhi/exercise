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
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.ReceivingStorageOutDataSheet;
import jp.co.daifuku.wms.stock.rft.schedule.StdStorageStartSCH;
import jp.co.daifuku.wms.stock.rft.schedule.StdStorageStartSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class StdStorageStartBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "StdStorageStart";

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
    public StdStorageStartBusiness()
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
    public HSResult itemCode_Scan(DataSheetFactory dsf, Locale locale)
    {
        // set focus.
        setFocus("ItemCode");

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
    public HSResult cancel_Click(DataSheetFactory dsf, Locale locale)
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
    public HSResult all_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        ReceivingStorageOutDataSheet receivingStorageOutDataSheet = new ReceivingStorageOutDataSheet(dsf, locale);

        // set output parameters.
        receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_QTY, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.STOCK_QTY));
        receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_CASE_QTY, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.STOCK_CASE_QTY));
        receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_PIECE_QTY, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.STOCK_PIECE_QTY));

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
    public HSResult input_Click(DataSheetFactory dsf, Locale locale)
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
        // initialize DataSheets.
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        ReceivingStorageOutDataSheet receivingStorageOutDataSheet = new ReceivingStorageOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        StdStorageStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new StdStorageStartSCH(conn, this.getClass(), locale);

            // set input parameters.
            StdStorageStartSCHParams inparam = new StdStorageStartSCHParams();
            inparam.set(StdStorageStartSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(StdStorageStartSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(StdStorageStartSCHParams.CONSIGNOR_CODE, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.CONSIGNOR_CODE));
            inparam.set(StdStorageStartSCHParams.ITEM_CODE, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.ITEM_CODE));
            inparam.set(StdStorageStartSCHParams.AREA_NO, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.AREA_NO));
            inparam.set(StdStorageStartSCHParams.LOCATION_NO, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.LOCATION_NO));
            inparam.set(StdStorageStartSCHParams.LOT_NO, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.LOT_NO));
            inparam.set(StdStorageStartSCHParams.RESULT_QTY, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.RESULT_QTY));
            inparam.set(StdStorageStartSCHParams.START_DATE_TIME, commonDataSheet.getValue(CommonDataSheet.START_DATE_TIME));
            inparam.set(StdStorageStartSCHParams.MISS_SCAN_COUNT, commonDataSheet.getValue(CommonDataSheet.MISS_SCAN_COUNT));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.CONSIGNOR_NAME, outparam.get(StdStorageStartSCHParams.CONSIGNOR_NAME));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.MOVE_JOB_NO, outparam.get(StdStorageStartSCHParams.MOVE_JOB_NO));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.ITEM_CODE, outparam.get(StdStorageStartSCHParams.ITEM_CODE));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.ITEM_NAME, outparam.get(StdStorageStartSCHParams.ITEM_NAME));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.AREA_NO, outparam.get(StdStorageStartSCHParams.AREA_NO));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.MOVE_AREA_NO, outparam.get(StdStorageStartSCHParams.MOVE_AREA_NO));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.LOCATION_NO, outparam.get(StdStorageStartSCHParams.LOCATION_NO));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.MOVE_LOCATION_NO, outparam.get(StdStorageStartSCHParams.MOVE_LOCATION_NO));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.PLAN_AREA_LOCATION, outparam.get(StdStorageStartSCHParams.PLAN_AREA_LOCATION));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.LOCATION_STYLE, outparam.get(StdStorageStartSCHParams.LOCATION_STYLE));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.JAN, outparam.get(StdStorageStartSCHParams.JAN));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.ITF, outparam.get(StdStorageStartSCHParams.ITF));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.BUNDLE_ITF, outparam.get(StdStorageStartSCHParams.BUNDLE_ITF));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.ENTERING_QTY, outparam.get(StdStorageStartSCHParams.ENTERING_QTY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.BUNDLE_ENTERING_QTY, outparam.get(StdStorageStartSCHParams.BUNDLE_ENTERING_QTY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.LOT_NO, outparam.get(StdStorageStartSCHParams.LOT_NO));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.SETTING_UNIT_KEY, outparam.get(StdStorageStartSCHParams.SETTING_UNIT_KEY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.STOCK_PACK_ENABLED, outparam.get(StdStorageStartSCHParams.STOCK_PACK_ENABLED));
                commonDataSheet.setValue(CommonDataSheet.START_DATE_TIME, outparam.get(StdStorageStartSCHParams.START_DATE_TIME));
                break;
            }

            receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.IS_STORAGE_START, true);
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

    /**
     * callback at screen event.
     *
     * @param dsf data sheet
     * @param locale Terminal locale
     * @return executed result
     */
    public HSResult cp_Click(DataSheetFactory dsf, Locale locale)
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
    public HSResult detail_Click(DataSheetFactory dsf, Locale locale)
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

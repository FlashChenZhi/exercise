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
import jp.co.daifuku.wms.base.rft.datasheet.ItemDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.ReceivingStorageInDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.ReceivingStorageOutDataSheet;
import jp.co.daifuku.wms.stock.rft.schedule.StdReStartSCH;
import jp.co.daifuku.wms.stock.rft.schedule.StdReStartSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class StdReStartBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "StdReStart";

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
    public StdReStartBusiness()
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
        ItemDataSheet itemDataSheet = new ItemDataSheet(dsf, locale);
        ReceivingStorageInDataSheet receivingStorageInDataSheet = new ReceivingStorageInDataSheet(dsf, locale);
        ReceivingStorageOutDataSheet receivingStorageOutDataSheet = new ReceivingStorageOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        StdReStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new StdReStartSCH(conn, this.getClass(), locale);

            // set input parameters.
            StdReStartSCHParams inparam = new StdReStartSCHParams();
            inparam.set(StdReStartSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.MOVE_JOB_NO, outparam.get(StdReStartSCHParams.MOVE_JOB_NO));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.SETTING_UNIT_KEY, outparam.get(StdReStartSCHParams.SETTING_UNIT_KEY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.CONSIGNOR_CODE, outparam.get(StdReStartSCHParams.CONSIGNOR_CODE));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.CONSIGNOR_NAME, outparam.get(StdReStartSCHParams.CONSIGNOR_NAME));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.ITEM_CODE, outparam.get(StdReStartSCHParams.ITEM_CODE));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.ITEM_NAME, outparam.get(StdReStartSCHParams.ITEM_NAME));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.AREA_NO, outparam.get(StdReStartSCHParams.AREA_NO));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.LOCATION_NO, outparam.get(StdReStartSCHParams.LOCATION_NO));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.MOVE_AREA_NO, outparam.get(StdReStartSCHParams.MOVE_AREA_NO));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.MOVE_LOCATION_NO, outparam.get(StdReStartSCHParams.MOVE_LOCATION_NO));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.JAN, outparam.get(StdReStartSCHParams.JAN));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.ITF, outparam.get(StdReStartSCHParams.ITF));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.BUNDLE_ITF, outparam.get(StdReStartSCHParams.BUNDLE_ITF));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.ENTERING_QTY, outparam.get(StdReStartSCHParams.ENTERING_QTY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.BUNDLE_ENTERING_QTY, outparam.get(StdReStartSCHParams.BUNDLE_ENTERING_QTY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.LOT_NO, outparam.get(StdReStartSCHParams.LOT_NO));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.STOCK_QTY, outparam.get(StdReStartSCHParams.STOCK_QTY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.STOCK_CASE_QTY, outparam.get(StdReStartSCHParams.STOCK_CASE_QTY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.STOCK_PIECE_QTY, outparam.get(StdReStartSCHParams.STOCK_PIECE_QTY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_QTY, outparam.get(StdReStartSCHParams.RESULT_QTY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_CASE_QTY, outparam.get(StdReStartSCHParams.RESULT_CASE_QTY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_PIECE_QTY, outparam.get(StdReStartSCHParams.RESULT_PIECE_QTY));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.PLAN_AREA_LOCATION, outparam.get(StdReStartSCHParams.PLAN_AREA_LOCATION));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.STOCK_PACK_ENABLED, outparam.get(StdReStartSCHParams.STOCK_PACK_ENABLED));
                commonDataSheet.setValue(CommonDataSheet.CASE_INPUT_ENABLED, outparam.get(StdReStartSCHParams.CASE_INPUT_ENABLED));
                commonDataSheet.setValue(CommonDataSheet.START_DATE_TIME, outparam.get(StdReStartSCHParams.START_DATE_TIME));
                receivingStorageInDataSheet.setValue(ReceivingStorageInDataSheet.CONSIGNOR_CODE, outparam.get(StdReStartSCHParams.CONSIGNOR_CODE));
                receivingStorageInDataSheet.setValue(ReceivingStorageInDataSheet.ITEM_CODE, outparam.get(StdReStartSCHParams.ITEM_CODE));
                consignorDataSheet.setValue(ConsignorDataSheet.CONSIGNOR_CODE, outparam.get(StdReStartSCHParams.CONSIGNOR_CODE));
                consignorDataSheet.setValue(ConsignorDataSheet.CONSIGNOR_NAME, outparam.get(StdReStartSCHParams.CONSIGNOR_NAME));
                itemDataSheet.setValue(ItemDataSheet.ITEM_CODE, outparam.get(StdReStartSCHParams.ITEM_CODE));
                itemDataSheet.setValue(ItemDataSheet.ITEM_NAME, outparam.get(StdReStartSCHParams.ITEM_NAME));
                receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.LOCATION_STYLE, outparam.get(StdReStartSCHParams.LOCATION_STYLE));
                break;
            }

            commonDataSheet.setValue(CommonDataSheet.MISS_SCAN_COUNT, 0);
            receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.IS_STORAGE_START, true);

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

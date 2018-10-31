// $Id$
package jp.co.daifuku.wms.retrieval.rft.display;

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
import jp.co.daifuku.wms.base.rft.datasheet.AreaDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.CommonDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.ConsignorDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.retrieval.rft.datasheet.OrderDataSheet;
import jp.co.daifuku.wms.retrieval.rft.datasheet.RetrievalInDataSheet;
import jp.co.daifuku.wms.retrieval.rft.datasheet.RetrievalOutDataSheet;
import jp.co.daifuku.wms.retrieval.rft.schedule.RetReStartSCH;
import jp.co.daifuku.wms.retrieval.rft.schedule.RetReStartSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class RetReStartBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "RetReStart";

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
    public RetReStartBusiness()
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
        AreaDataSheet areaDataSheet = new AreaDataSheet(dsf, locale);
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        ConsignorDataSheet consignorDataSheet = new ConsignorDataSheet(dsf, locale);
        OrderDataSheet orderDataSheet = new OrderDataSheet(dsf, locale);
        RetrievalInDataSheet retrievalInDataSheet = new RetrievalInDataSheet(dsf, locale);
        RetrievalOutDataSheet retrievalOutDataSheet = new RetrievalOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        RetReStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new RetReStartSCH(conn, this.getClass(), locale);

            // set input parameters.
            RetReStartSCHParams inparam = new RetReStartSCHParams();
            inparam.set(RetReStartSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.SETTING_UNIT_KEY, outparam.get(RetReStartSCHParams.SETTING_UNIT_KEY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.PLAN_DATE, outparam.get(RetReStartSCHParams.PLAN_DATE));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.CONSIGNOR_CODE, outparam.get(RetReStartSCHParams.CONSIGNOR_CODE));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.CONSIGNOR_NAME, outparam.get(RetReStartSCHParams.CONSIGNOR_NAME));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.LOCATION, outparam.get(RetReStartSCHParams.LOCATION));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.AREA_LOCATION, outparam.get(RetReStartSCHParams.AREA_LOCATION));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.LOCATION_STYLE, outparam.get(RetReStartSCHParams.LOCATION_STYLE));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ITEM_CODE, outparam.get(RetReStartSCHParams.ITEM_CODE));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ITEM_NAME, outparam.get(RetReStartSCHParams.ITEM_NAME));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ENTERING_QTY, outparam.get(RetReStartSCHParams.ENTERING_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.BUNDLE_ENTERING_QTY, outparam.get(RetReStartSCHParams.BUNDLE_ENTERING_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.PLAN_QTY, outparam.get(RetReStartSCHParams.PLAN_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.PLAN_CASE_QTY, outparam.get(RetReStartSCHParams.PLAN_CASE_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.PLAN_PIECE_QTY, outparam.get(RetReStartSCHParams.PLAN_PIECE_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_QTY, outparam.get(RetReStartSCHParams.RESULT_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_CASE_QTY, outparam.get(RetReStartSCHParams.RESULT_CASE_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_PIECE_QTY, outparam.get(RetReStartSCHParams.RESULT_PIECE_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.PLAN_LOT_NO, outparam.get(RetReStartSCHParams.PLAN_LOT_NO));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_LOT_NO, outparam.get(RetReStartSCHParams.RESULT_LOT_NO));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.LINE_NO, outparam.get(RetReStartSCHParams.LINE_NO));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_NO, outparam.get(RetReStartSCHParams.ORDER_NO));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_SERIAL_NO, outparam.get(RetReStartSCHParams.ORDER_SERIAL_NO));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.AREA_NO, outparam.get(RetReStartSCHParams.AREA_NO));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.JAN, outparam.get(RetReStartSCHParams.JAN));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ITF, outparam.get(RetReStartSCHParams.ITF));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.BUNDLE_ITF, outparam.get(RetReStartSCHParams.BUNDLE_ITF));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.COLLECT_JOB_NO, outparam.get(RetReStartSCHParams.COLLECT_JOB_NO));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.CUSTOMER_CODE, outparam.get(RetReStartSCHParams.CUSTOMER_CODE));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.CUSTOMER_NAME, outparam.get(RetReStartSCHParams.CUSTOMER_NAME));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.SKIP_ENABLED, outparam.get(RetReStartSCHParams.SKIP_ENABLED));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.SURVIVAL_RECORD, outparam.get(RetReStartSCHParams.SURVIVAL_RECORD));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.TOTAL_RECORD, outparam.get(RetReStartSCHParams.TOTAL_RECORD));
                commonDataSheet.setValue(CommonDataSheet.START_DATE_TIME, outparam.get(RetReStartSCHParams.START_DATE_TIME));
                commonDataSheet.setValue(CommonDataSheet.CASE_INPUT_ENABLED, outparam.get(RetReStartSCHParams.CASE_INPUT_ENABLED));
                retrievalInDataSheet.setValue(RetrievalInDataSheet.ORDER_NO1, outparam.get(RetReStartSCHParams.ORDER_NO1));
                retrievalInDataSheet.setValue(RetrievalInDataSheet.ORDER_NO2, outparam.get(RetReStartSCHParams.ORDER_NO2));
                retrievalInDataSheet.setValue(RetrievalInDataSheet.ORDER_NO3, outparam.get(RetReStartSCHParams.ORDER_NO3));
                retrievalInDataSheet.setValue(RetrievalInDataSheet.ORDER_NO4, outparam.get(RetReStartSCHParams.ORDER_NO4));
                consignorDataSheet.setValue(ConsignorDataSheet.CONSIGNOR_CODE, outparam.get(RetReStartSCHParams.CONSIGNOR_CODE));
                consignorDataSheet.setValue(ConsignorDataSheet.CONSIGNOR_NAME, outparam.get(RetReStartSCHParams.CONSIGNOR_NAME));
                areaDataSheet.setValue(AreaDataSheet.AREA_NO, outparam.get(RetReStartSCHParams.AREA_NO));
                areaDataSheet.setValue(AreaDataSheet.AREA_NAME, outparam.get(RetReStartSCHParams.AREA_NAME));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.CUSTOMER_CODE1, outparam.get(RetReStartSCHParams.CUSTOMER_CODE1));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.CUSTOMER_CODE2, outparam.get(RetReStartSCHParams.CUSTOMER_CODE2));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.CUSTOMER_CODE3, outparam.get(RetReStartSCHParams.CUSTOMER_CODE3));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.CUSTOMER_CODE4, outparam.get(RetReStartSCHParams.CUSTOMER_CODE4));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.CUSTOMER_NAME1, outparam.get(RetReStartSCHParams.CUSTOMER_NAME1));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.CUSTOMER_NAME2, outparam.get(RetReStartSCHParams.CUSTOMER_NAME2));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.CUSTOMER_NAME3, outparam.get(RetReStartSCHParams.CUSTOMER_NAME3));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.CUSTOMER_NAME4, outparam.get(RetReStartSCHParams.CUSTOMER_NAME4));
                retrievalInDataSheet.setValue(RetrievalInDataSheet.AREA_NO, outparam.get(RetReStartSCHParams.AREA_NO));
                retrievalInDataSheet.setValue(RetrievalInDataSheet.CONSIGNOR_CODE, outparam.get(RetReStartSCHParams.CONSIGNOR_CODE));
                orderDataSheet.setValue(OrderDataSheet.ORDER_NO1, outparam.get(RetReStartSCHParams.ORDER_NO1));
                orderDataSheet.setValue(OrderDataSheet.ORDER_NO2, outparam.get(RetReStartSCHParams.ORDER_NO2));
                orderDataSheet.setValue(OrderDataSheet.ORDER_NO3, outparam.get(RetReStartSCHParams.ORDER_NO3));
                orderDataSheet.setValue(OrderDataSheet.ORDER_NO4, outparam.get(RetReStartSCHParams.ORDER_NO4));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.START_DATE_TIME, outparam.get(RetReStartSCHParams.START_DATE_TIME));
                break;
            }

            commonDataSheet.setValue(CommonDataSheet.MISS_SCAN_COUNT, 0);
            retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_NO1, retrievalInDataSheet.getValue(RetrievalInDataSheet.ORDER_NO1));
            retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_NO2, retrievalInDataSheet.getValue(RetrievalInDataSheet.ORDER_NO2));
            retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_NO3, retrievalInDataSheet.getValue(RetrievalInDataSheet.ORDER_NO3));
            retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_NO4, retrievalInDataSheet.getValue(RetrievalInDataSheet.ORDER_NO4));

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

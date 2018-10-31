// $Id$
package jp.co.daifuku.wms.ship.rft.display;

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
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.rft.HaiSurfExceptionHandler;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.datasheet.CommonDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.ship.rft.datasheet.ShippingCustomerInDataSheet;
import jp.co.daifuku.wms.ship.rft.datasheet.ShippingCustomerOutDataSheet;
import jp.co.daifuku.wms.ship.rft.schedule.ShpCompleteSCH;
import jp.co.daifuku.wms.ship.rft.schedule.ShpCompleteSCHParams;
import jp.co.daifuku.wms.ship.rft.schedule.ShpInspectionSCH;
import jp.co.daifuku.wms.ship.rft.schedule.ShpInspectionSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class ShpInspectionBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "ShpInspection";

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
    public ShpInspectionBusiness()
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
    public HSResult all_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        ShippingCustomerOutDataSheet shippingCustomerOutDataSheet = new ShippingCustomerOutDataSheet(dsf, locale);

        // set output parameters.
        shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.RESULT_QTY, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.PLAN_QTY));
        shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.RESULT_CASE_QTY, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.PLAN_CASE_QTY));
        shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.RESULT_PIECE_QTY, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.PLAN_PIECE_QTY));

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
    public HSResult cp_Click(DataSheetFactory dsf, Locale locale)
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
    public HSResult input1_Click(DataSheetFactory dsf, Locale locale)
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
        ShippingCustomerInDataSheet shippingCustomerInDataSheet = new ShippingCustomerInDataSheet(dsf, locale);
        ShippingCustomerOutDataSheet shippingCustomerOutDataSheet = new ShippingCustomerOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        ShpCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new ShpCompleteSCH(conn, this.getClass(), locale);

            // set input parameters.
            ShpCompleteSCHParams inparam = new ShpCompleteSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(ShpCompleteSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(ShpCompleteSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(ShpCompleteSCHParams.PLAN_DATE, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.PLAN_DATE));
            inparam.set(ShpCompleteSCHParams.CONSIGNOR_CODE, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.CONSIGNOR_CODE));
            inparam.set(ShpCompleteSCHParams.CONSIGNOR_NAME, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.CONSIGNOR_NAME));
            inparam.set(ShpCompleteSCHParams.BATCH_NO, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.BATCH_NO));
            inparam.set(ShpCompleteSCHParams.CUSTOMER_CODE, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.CUSTOMER_CODE));
            inparam.set(ShpCompleteSCHParams.CUSTOMER_NAME, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.CUSTOMER_NAME));
            inparam.set(ShpCompleteSCHParams.SCAN_CODE, shippingCustomerInDataSheet.getValue(ShippingCustomerInDataSheet.SCAN_CODE));
            inparam.set(ShpCompleteSCHParams.SETTING_UNIT_KEY, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.SETTING_UNIT_KEY));
            inparam.set(ShpCompleteSCHParams.START_DATE_TIME, commonDataSheet.getValue(CommonDataSheet.START_DATE_TIME));
            inparam.set(ShpCompleteSCHParams.MISS_SCAN_COUNT, commonDataSheet.getValue(CommonDataSheet.MISS_SCAN_COUNT));
            inparam.set(ShpCompleteSCHParams.COMPLETION_FLAG, Parameter.COMPLETION_FLAG_DECISION);

            // SCH call.
            sch.startSCH(inparam);

            // commit.
            conn.commit();

            // set focus.
            setFocus("ItemCode");

            // set result code.
            return new HSResult(ResultConst.NORMAL);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            DBUtil.rollback(conn);
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
    public HSResult input2_Click(DataSheetFactory dsf, Locale locale)
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
        // initialize DataSheets.
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        ShippingCustomerInDataSheet shippingCustomerInDataSheet = new ShippingCustomerInDataSheet(dsf, locale);
        ShippingCustomerOutDataSheet shippingCustomerOutDataSheet = new ShippingCustomerOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        ShpInspectionSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new ShpInspectionSCH(conn, this.getClass(), locale);

            // set input parameters.
            ShpInspectionSCHParams inparam = new ShpInspectionSCHParams();
            inparam.set(ShpInspectionSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(ShpInspectionSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(ShpInspectionSCHParams.PLAN_DATE, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.PLAN_DATE));
            inparam.set(ShpInspectionSCHParams.CONSIGNOR_CODE, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.CONSIGNOR_CODE));
            inparam.set(ShpInspectionSCHParams.BATCH_NO, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.BATCH_NO));
            inparam.set(ShpInspectionSCHParams.CUSTOMER_CODE, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.CUSTOMER_CODE));
            inparam.set(ShpInspectionSCHParams.SCAN_CODE, shippingCustomerInDataSheet.getValue(ShippingCustomerInDataSheet.SCAN_CODE));
            inparam.set(ShpInspectionSCHParams.SETTING_UNIT_KEY, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.SETTING_UNIT_KEY));
            inparam.set(ShpInspectionSCHParams.ITEM_CODE, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.ITEM_CODE));
            inparam.set(ShpInspectionSCHParams.JAN, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.JAN));
            inparam.set(ShpInspectionSCHParams.ITF, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.ITF));
            inparam.set(ShpInspectionSCHParams.BUNDLE_ITF, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.BUNDLE_ITF));
            inparam.set(ShpInspectionSCHParams.SHIPPING_ITF_TO_JAN, termInfoDataSheet.getValue(TermInfoDataSheet.SHIPPING_ITF_TO_JAN));
            inparam.set(ShpInspectionSCHParams.COLLECT_JOB_NO, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.COLLECT_JOB_NO));
            inparam.set(ShpInspectionSCHParams.PLAN_LOT_NO, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.PLAN_LOT_NO));
            inparam.set(ShpInspectionSCHParams.PLAN_QTY, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.PLAN_QTY));
            inparam.set(ShpInspectionSCHParams.RESULT_LOT_NO, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.RESULT_LOT_NO));
            inparam.set(ShpInspectionSCHParams.RESULT_QTY, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.RESULT_QTY));
            inparam.set(ShpInspectionSCHParams.START_DATE_TIME, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.START_DATE_TIME));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.COLLECT_JOB_NO, outparam.get(ShpInspectionSCHParams.COLLECT_JOB_NO));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.ITEM_CODE, outparam.get(ShpInspectionSCHParams.ITEM_CODE));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.ITEM_NAME, outparam.get(ShpInspectionSCHParams.ITEM_NAME));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.PLAN_QTY, outparam.get(ShpInspectionSCHParams.PLAN_QTY));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.PLAN_CASE_QTY, outparam.get(ShpInspectionSCHParams.PLAN_CASE_QTY));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.PLAN_PIECE_QTY, outparam.get(ShpInspectionSCHParams.PLAN_PIECE_QTY));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.RESULT_QTY, outparam.get(ShpInspectionSCHParams.RESULT_QTY));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.RESULT_CASE_QTY, outparam.get(ShpInspectionSCHParams.RESULT_CASE_QTY));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.RESULT_PIECE_QTY, outparam.get(ShpInspectionSCHParams.RESULT_PIECE_QTY));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.JAN, outparam.get(ShpInspectionSCHParams.JAN));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.ITF, outparam.get(ShpInspectionSCHParams.ITF));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.BUNDLE_ITF, outparam.get(ShpInspectionSCHParams.BUNDLE_ITF));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.ENTERING_QTY, outparam.get(ShpInspectionSCHParams.ENTERING_QTY));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.BUNDLE_ENTERING_QTY, outparam.get(ShpInspectionSCHParams.BUNDLE_ENTERING_QTY));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.TICKET_NO, outparam.get(ShpInspectionSCHParams.TICKET_NO));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.TICKET_LINE_NO, outparam.get(ShpInspectionSCHParams.TICKET_LINE_NO));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.PLAN_LOT_NO, outparam.get(ShpInspectionSCHParams.PLAN_LOT_NO));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.RESULT_LOT_NO, outparam.get(ShpInspectionSCHParams.RESULT_LOT_NO));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.START_DATE_TIME, outparam.get(ShpInspectionSCHParams.START_DATE_TIME));
                commonDataSheet.setValue(CommonDataSheet.CASE_INPUT_ENABLED, outparam.get(ShpInspectionSCHParams.CASE_INPUT_ENABLED));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.QTY_INPUT_ENABLED, outparam.get(ShpInspectionSCHParams.QTY_INPUT_ENABLED));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.PENDING_ITEM, outparam.get(ShpInspectionSCHParams.PENDING_ITEM));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.TOTAL_ITEM, outparam.get(ShpInspectionSCHParams.TOTAL_ITEM));
                break;
            }

            // set focus.
            setFocus("ItemCode");

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

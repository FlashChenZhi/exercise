// $Id$
package jp.co.daifuku.wms.receiving.rft.display;

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
import jp.co.daifuku.wms.receiving.rft.datasheet.ReceivingInDataSheet;
import jp.co.daifuku.wms.receiving.rft.datasheet.ReceivingOutDataSheet;
import jp.co.daifuku.wms.receiving.rft.schedule.RecCompleteSCH;
import jp.co.daifuku.wms.receiving.rft.schedule.RecCompleteSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class RecInspectionBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "RecInspection";

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
    public RecInspectionBusiness()
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
        ReceivingOutDataSheet receivingOutDataSheet = new ReceivingOutDataSheet(dsf, locale);

        // set output parameters.
        receivingOutDataSheet.setValue(ReceivingOutDataSheet.RESULT_QTY, receivingOutDataSheet.getValue(ReceivingOutDataSheet.PLAN_QTY));
        receivingOutDataSheet.setValue(ReceivingOutDataSheet.RESULT_CASE_QTY, receivingOutDataSheet.getValue(ReceivingOutDataSheet.PLAN_CASE_QTY));
        receivingOutDataSheet.setValue(ReceivingOutDataSheet.RESULT_PIECE_QTY, receivingOutDataSheet.getValue(ReceivingOutDataSheet.PLAN_PIECE_QTY));

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
        ReceivingInDataSheet receivingInDataSheet = new ReceivingInDataSheet(dsf, locale);
        ReceivingOutDataSheet receivingOutDataSheet = new ReceivingOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        RecCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new RecCompleteSCH(conn, this.getClass(), locale);

            // set input parameters.
            RecCompleteSCHParams inparam = new RecCompleteSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(RecCompleteSCHParams.COMPLETION_FLAG, Parameter.COMPLETION_FLAG_DECISION);
            inparam.set(RecCompleteSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(RecCompleteSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(RecCompleteSCHParams.SETTING_UNIT_KEY, receivingOutDataSheet.getValue(ReceivingOutDataSheet.SETTING_UNIT_KEY));
            inparam.set(RecCompleteSCHParams.PLAN_DATE, receivingOutDataSheet.getValue(ReceivingOutDataSheet.PLAN_DATE));
            inparam.set(RecCompleteSCHParams.CONSIGNOR_CODE, receivingOutDataSheet.getValue(ReceivingOutDataSheet.CONSIGNOR_CODE));
            inparam.set(RecCompleteSCHParams.SUPPLIER_CODE, receivingOutDataSheet.getValue(ReceivingOutDataSheet.SUPPLIER_CODE));
            inparam.set(RecCompleteSCHParams.COLLECT_JOB_NO, receivingOutDataSheet.getValue(ReceivingOutDataSheet.COLLECT_JOB_NO));
            inparam.set(RecCompleteSCHParams.TICKET_NO, receivingOutDataSheet.getValue(ReceivingOutDataSheet.TICKET_NO));
            inparam.set(RecCompleteSCHParams.TICKET_LINE_NO, receivingOutDataSheet.getValue(ReceivingOutDataSheet.TICKET_LINE_NO));
            inparam.set(RecCompleteSCHParams.ITEM_CODE, receivingOutDataSheet.getValue(ReceivingOutDataSheet.ITEM_CODE));
            inparam.set(RecCompleteSCHParams.JAN, receivingOutDataSheet.getValue(ReceivingOutDataSheet.JAN));
            inparam.set(RecCompleteSCHParams.ITF, receivingOutDataSheet.getValue(ReceivingOutDataSheet.ITF));
            inparam.set(RecCompleteSCHParams.ENTERING_QTY, receivingOutDataSheet.getValue(ReceivingOutDataSheet.ENTERING_QTY));
            inparam.set(RecCompleteSCHParams.PLAN_LOT_NO, receivingOutDataSheet.getValue(ReceivingOutDataSheet.PLAN_LOT_NO));
            inparam.set(RecCompleteSCHParams.RESULT_LOT_NO, receivingOutDataSheet.getValue(ReceivingOutDataSheet.RESULT_LOT_NO));
            inparam.set(RecCompleteSCHParams.RESULT_QTY, receivingOutDataSheet.getValue(ReceivingOutDataSheet.RESULT_QTY));
            inparam.set(RecCompleteSCHParams.START_DATE_TIME, commonDataSheet.getValue(CommonDataSheet.START_DATE_TIME));
            inparam.set(RecCompleteSCHParams.MISS_SCAN_COUNT, commonDataSheet.getValue(CommonDataSheet.MISS_SCAN_COUNT));
            inparam.set(RecCompleteSCHParams.FORCE_STRAGE_FLAG, receivingInDataSheet.getValue(ReceivingInDataSheet.FORCE_STRAGE_FLAG));

            // SCH call.
            sch.startSCH(inparam);

            // commit.
            conn.commit();

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
    public HSResult itemCode_Scan(DataSheetFactory dsf, Locale locale)
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

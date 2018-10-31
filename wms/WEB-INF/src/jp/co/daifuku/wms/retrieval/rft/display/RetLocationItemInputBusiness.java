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
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.rft.HaiSurfExceptionHandler;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.RftConst;
import jp.co.daifuku.wms.base.rft.datasheet.CommonDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.retrieval.rft.datasheet.RetrievalInDataSheet;
import jp.co.daifuku.wms.retrieval.rft.datasheet.RetrievalOutDataSheet;
import jp.co.daifuku.wms.retrieval.rft.schedule.RetUpdateSCH;
import jp.co.daifuku.wms.retrieval.rft.schedule.RetUpdateSCHParams;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalInParameter;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class RetLocationItemInputBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "RetLocationItemInput";

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
    public RetLocationItemInputBusiness()
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
        RetrievalOutDataSheet retrievalOutDataSheet = new RetrievalOutDataSheet(dsf, locale);

        // set output parameters.
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.GUIDANCE_MESSAGE, "6041022");
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.LOCATION_INPUT_ENABLED, true);
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ITEM_CODE_INPUT_ENABLED, false);

        // set focus.
        setFocus("Location");

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
    public HSResult location_Enter(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        RetrievalInDataSheet retrievalInDataSheet = new RetrievalInDataSheet(dsf, locale);
        RetrievalOutDataSheet retrievalOutDataSheet = new RetrievalOutDataSheet(dsf, locale);

        // input validation.
        if (StringUtil.isBlank(retrievalInDataSheet.getText(RetrievalInDataSheet.LOCATION)))
        {
            setBeep(RftConst.BEEP);
            setFocus("Location");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.GUIDANCE_MESSAGE, "6041023");
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.LOCATION_INPUT_ENABLED, false);
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ITEM_CODE_INPUT_ENABLED, true);

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
    public HSResult itemCode_Enter(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        RetrievalInDataSheet retrievalInDataSheet = new RetrievalInDataSheet(dsf, locale);

        // input validation.
        if (StringUtil.isBlank(retrievalInDataSheet.getText(RetrievalInDataSheet.ITEM_CODE)))
        {
            setBeep(RftConst.BEEP);
            setFocus("ItemCode");
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
    public HSResult skip_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        RetrievalInDataSheet retrievalInDataSheet = new RetrievalInDataSheet(dsf, locale);
        RetrievalOutDataSheet retrievalOutDataSheet = new RetrievalOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        RetUpdateSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new RetUpdateSCH(conn, this.getClass(), locale);

            // set input parameters.
            RetUpdateSCHParams inparam = new RetUpdateSCHParams();
            inparam.set(RetUpdateSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(RetUpdateSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(RetUpdateSCHParams.COLLECT_JOB_NO, retrievalOutDataSheet.getValue(RetrievalOutDataSheet.COLLECT_JOB_NO));
            inparam.set(RetUpdateSCHParams.START_DATE_TIME, retrievalOutDataSheet.getValue(RetrievalOutDataSheet.START_DATE_TIME));
            inparam.set(RetUpdateSCHParams.COMPLETION_FLAG, RetrievalInParameter.RESULT_UPDATE_TYPE_RETRIEVAL_SKIP);
            inparam.set(RetUpdateSCHParams.SETTING_UNIT_KEY, retrievalOutDataSheet.getValue(RetrievalOutDataSheet.SETTING_UNIT_KEY));
            inparam.set(RetUpdateSCHParams.ORDER_NO1, retrievalInDataSheet.getValue(RetrievalInDataSheet.ORDER_NO1));
            inparam.set(RetUpdateSCHParams.ORDER_NO2, retrievalInDataSheet.getValue(RetrievalInDataSheet.ORDER_NO2));
            inparam.set(RetUpdateSCHParams.ORDER_NO3, retrievalInDataSheet.getValue(RetrievalInDataSheet.ORDER_NO3));
            inparam.set(RetUpdateSCHParams.ORDER_NO4, retrievalInDataSheet.getValue(RetrievalInDataSheet.ORDER_NO4));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.PLAN_DATE, outparam.get(RetUpdateSCHParams.PLAN_DATE));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.LOCATION, outparam.get(RetUpdateSCHParams.LOCATION));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.AREA_LOCATION, outparam.get(RetUpdateSCHParams.AREA_LOCATION));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.LOCATION_STYLE, outparam.get(RetUpdateSCHParams.LOCATION_STYLE));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ITEM_CODE, outparam.get(RetUpdateSCHParams.ITEM_CODE));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ITEM_NAME, outparam.get(RetUpdateSCHParams.ITEM_NAME));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ENTERING_QTY, outparam.get(RetUpdateSCHParams.ENTERING_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.BUNDLE_ENTERING_QTY, outparam.get(RetUpdateSCHParams.BUNDLE_ENTERING_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.PLAN_QTY, outparam.get(RetUpdateSCHParams.PLAN_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.PLAN_CASE_QTY, outparam.get(RetUpdateSCHParams.PLAN_CASE_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.PLAN_PIECE_QTY, outparam.get(RetUpdateSCHParams.PLAN_PIECE_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_QTY, outparam.get(RetUpdateSCHParams.RESULT_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_CASE_QTY, outparam.get(RetUpdateSCHParams.RESULT_CASE_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_PIECE_QTY, outparam.get(RetUpdateSCHParams.RESULT_PIECE_QTY));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.PLAN_LOT_NO, outparam.get(RetUpdateSCHParams.PLAN_LOT_NO));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_LOT_NO, outparam.get(RetUpdateSCHParams.RESULT_LOT_NO));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.LINE_NO, outparam.get(RetUpdateSCHParams.LINE_NO));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_NO, outparam.get(RetUpdateSCHParams.ORDER_NO));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_SERIAL_NO, outparam.get(RetUpdateSCHParams.ORDER_SERIAL_NO));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.JAN, outparam.get(RetUpdateSCHParams.JAN));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ITF, outparam.get(RetUpdateSCHParams.ITF));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.BUNDLE_ITF, outparam.get(RetUpdateSCHParams.BUNDLE_ITF));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.COLLECT_JOB_NO, outparam.get(RetUpdateSCHParams.COLLECT_JOB_NO));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.CUSTOMER_CODE, outparam.get(RetUpdateSCHParams.CUSTOMER_CODE));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.CUSTOMER_NAME, outparam.get(RetUpdateSCHParams.CUSTOMER_NAME));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.SKIP_ENABLED, outparam.get(RetUpdateSCHParams.SKIP_ENABLED));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.SURVIVAL_RECORD, outparam.get(RetUpdateSCHParams.SURVIVAL_RECORD));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.START_DATE_TIME, outparam.get(RetUpdateSCHParams.START_DATE_TIME));
                commonDataSheet.setValue(CommonDataSheet.CASE_INPUT_ENABLED, outparam.get(RetUpdateSCHParams.CASE_INPUT_ENABLED));
                break;
            }

            retrievalOutDataSheet.setValue(RetrievalOutDataSheet.GUIDANCE_MESSAGE, "6041022");
            retrievalOutDataSheet.setValue(RetrievalOutDataSheet.LOCATION_INPUT_ENABLED, true);
            retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ITEM_CODE_INPUT_ENABLED, false);

            // set focus.
            setFocus("Location");

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

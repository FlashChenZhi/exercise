// $Id: RecStartBusiness.java 8041 2012-05-17 09:39:40Z nagao $
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
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.rft.HaiSurfExceptionHandler;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.datasheet.CommonDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.ConsignorDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.ItemDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.receiving.rft.datasheet.ReceivingInDataSheet;
import jp.co.daifuku.wms.receiving.rft.datasheet.ReceivingOutDataSheet;
import jp.co.daifuku.wms.receiving.rft.schedule.RecStartSCH;
import jp.co.daifuku.wms.receiving.rft.schedule.RecStartSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision: 8041 $, $Date: 2012-05-17 18:39:40 +0900 (木, 17 5 2012) $
 * @author  HighTune.
 * @author  Last commit: $Author: nagao $
 */
public class RecStartBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "RecStart";

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
    public RecStartBusiness()
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
        ReceivingInDataSheet receivingInDataSheet = new ReceivingInDataSheet(dsf, locale);
        ReceivingOutDataSheet receivingOutDataSheet = new ReceivingOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        RecStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new RecStartSCH(conn, this.getClass(), locale);

            // set input parameters.
            RecStartSCHParams inparam = new RecStartSCHParams();
            inparam.set(RecStartSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(RecStartSCHParams.RECEIVING_ITF_TO_JAN, termInfoDataSheet.getValue(TermInfoDataSheet.RECEIVING_ITF_TO_JAN));
            inparam.set(RecStartSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(RecStartSCHParams.CONSIGNOR_CODE, receivingInDataSheet.getValue(ReceivingInDataSheet.CONSIGNOR_CODE));
            inparam.set(RecStartSCHParams.ITEM_CODE, receivingInDataSheet.getValue(ReceivingInDataSheet.ITEM_CODE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                itemDataSheet.setValue(ItemDataSheet.ITEM_NAME, outparam.get(RecStartSCHParams.ITEM_NAME));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.SETTING_UNIT_KEY, outparam.get(RecStartSCHParams.SETTING_UNIT_KEY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.CONSIGNOR_CODE, outparam.get(RecStartSCHParams.CONSIGNOR_CODE));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.CONSIGNOR_NAME, outparam.get(RecStartSCHParams.CONSIGNOR_NAME));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.PLAN_DATE, outparam.get(RecStartSCHParams.PLAN_DATE));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.SUPPLIER_CODE, outparam.get(RecStartSCHParams.SUPPLIER_CODE));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.SUPPLIER_NAME, outparam.get(RecStartSCHParams.SUPPLIER_NAME));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.COLLECT_JOB_NO, outparam.get(RecStartSCHParams.COLLECT_JOB_NO));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.ITEM_CODE, outparam.get(RecStartSCHParams.ITEM_CODE));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.ITEM_NAME, outparam.get(RecStartSCHParams.ITEM_NAME));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.PLAN_QTY, outparam.get(RecStartSCHParams.PLAN_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.PLAN_CASE_QTY, outparam.get(RecStartSCHParams.PLAN_CASE_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.PLAN_PIECE_QTY, outparam.get(RecStartSCHParams.PLAN_PIECE_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.RESULT_QTY, outparam.get(RecStartSCHParams.RESULT_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.RESULT_CASE_QTY, outparam.get(RecStartSCHParams.RESULT_CASE_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.RESULT_PIECE_QTY, outparam.get(RecStartSCHParams.RESULT_PIECE_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.JAN, outparam.get(RecStartSCHParams.JAN));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.ITF, outparam.get(RecStartSCHParams.ITF));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.BUNDLE_ITF, outparam.get(RecStartSCHParams.BUNDLE_ITF));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.ENTERING_QTY, outparam.get(RecStartSCHParams.ENTERING_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.BUNDLE_ENTERING_QTY, outparam.get(RecStartSCHParams.BUNDLE_ENTERING_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.TICKET_NO, outparam.get(RecStartSCHParams.TICKET_NO));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.TICKET_LINE_NO, outparam.get(RecStartSCHParams.TICKET_LINE_NO));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.PLAN_LOT_NO, outparam.get(RecStartSCHParams.PLAN_LOT_NO));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.RESULT_LOT_NO, outparam.get(RecStartSCHParams.PLAN_LOT_NO));
                commonDataSheet.setValue(CommonDataSheet.START_DATE_TIME, outparam.get(RecStartSCHParams.START_DATE_TIME));
                consignorDataSheet.setValue(ConsignorDataSheet.CONSIGNOR_NAME, outparam.get(RecStartSCHParams.CONSIGNOR_NAME));
                commonDataSheet.setValue(CommonDataSheet.CASE_INPUT_ENABLED, outparam.get(RecStartSCHParams.CASE_INPUT_ENABLED));
                break;
            }

            receivingOutDataSheet.setValue(ReceivingOutDataSheet.DISP_PLAN_DATE, receivingOutDataSheet.getValue(ReceivingOutDataSheet.PLAN_DATE));
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
        return "$Id: RecStartBusiness.java 8041 2012-05-17 09:39:40Z nagao $";
    }
}
//end of class

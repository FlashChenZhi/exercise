// $Id: RecReStartBusiness.java 8041 2012-05-17 09:39:40Z nagao $
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
import jp.co.daifuku.wms.receiving.rft.datasheet.ReceivingOutDataSheet;
import jp.co.daifuku.wms.receiving.rft.schedule.RecReStartSCH;
import jp.co.daifuku.wms.receiving.rft.schedule.RecReStartSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision: 8041 $, $Date: 2012-05-17 18:39:40 +0900 (木, 17 5 2012) $
 * @author  HighTune.
 * @author  Last commit: $Author: nagao $
 */
public class RecReStartBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "RecReStart";

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
    public RecReStartBusiness()
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
        ReceivingOutDataSheet receivingOutDataSheet = new ReceivingOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        RecReStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new RecReStartSCH(conn, this.getClass(), locale);

            // set input parameters.
            RecReStartSCHParams inparam = new RecReStartSCHParams();
            inparam.set(RecReStartSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.SETTING_UNIT_KEY, outparam.get(RecReStartSCHParams.SETTING_UNIT_KEY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.CONSIGNOR_CODE, outparam.get(RecReStartSCHParams.CONSIGNOR_CODE));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.CONSIGNOR_NAME, outparam.get(RecReStartSCHParams.CONSIGNOR_NAME));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.PLAN_DATE, outparam.get(RecReStartSCHParams.PLAN_DATE));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.SUPPLIER_CODE, outparam.get(RecReStartSCHParams.SUPPLIER_CODE));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.SUPPLIER_NAME, outparam.get(RecReStartSCHParams.SUPPLIER_NAME));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.COLLECT_JOB_NO, outparam.get(RecReStartSCHParams.COLLECT_JOB_NO));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.ITEM_CODE, outparam.get(RecReStartSCHParams.ITEM_CODE));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.ITEM_NAME, outparam.get(RecReStartSCHParams.ITEM_NAME));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.PLAN_QTY, outparam.get(RecReStartSCHParams.PLAN_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.PLAN_CASE_QTY, outparam.get(RecReStartSCHParams.PLAN_CASE_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.PLAN_PIECE_QTY, outparam.get(RecReStartSCHParams.PLAN_PIECE_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.RESULT_QTY, outparam.get(RecReStartSCHParams.RESULT_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.RESULT_CASE_QTY, outparam.get(RecReStartSCHParams.RESULT_CASE_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.RESULT_PIECE_QTY, outparam.get(RecReStartSCHParams.RESULT_PIECE_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.JAN, outparam.get(RecReStartSCHParams.JAN));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.ITF, outparam.get(RecReStartSCHParams.ITF));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.BUNDLE_ITF, outparam.get(RecReStartSCHParams.BUNDLE_ITF));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.ENTERING_QTY, outparam.get(RecReStartSCHParams.ENTERING_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.BUNDLE_ENTERING_QTY, outparam.get(RecReStartSCHParams.BUNDLE_ENTERING_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.TICKET_NO, outparam.get(RecReStartSCHParams.TICKET_NO));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.TICKET_LINE_NO, outparam.get(RecReStartSCHParams.TICKET_LINE_NO));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.PLAN_LOT_NO, outparam.get(RecReStartSCHParams.PLAN_LOT_NO));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.RESULT_LOT_NO, outparam.get(RecReStartSCHParams.PLAN_LOT_NO));
                commonDataSheet.setValue(CommonDataSheet.START_DATE_TIME, outparam.get(RecReStartSCHParams.START_DATE_TIME));
                consignorDataSheet.setValue(ConsignorDataSheet.CONSIGNOR_CODE, outparam.get(RecReStartSCHParams.CONSIGNOR_CODE));
                consignorDataSheet.setValue(ConsignorDataSheet.CONSIGNOR_NAME, outparam.get(RecReStartSCHParams.CONSIGNOR_NAME));
                itemDataSheet.setValue(ItemDataSheet.ITEM_CODE, outparam.get(RecReStartSCHParams.ITEM_CODE));
                itemDataSheet.setValue(ItemDataSheet.ITEM_NAME, outparam.get(RecReStartSCHParams.ITEM_NAME));
                commonDataSheet.setValue(CommonDataSheet.CASE_INPUT_ENABLED, outparam.get(RecReStartSCHParams.CASE_INPUT_ENABLED));
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
        return "$Id: RecReStartBusiness.java 8041 2012-05-17 09:39:40Z nagao $";
    }
}
//end of class

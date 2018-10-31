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
import jp.co.daifuku.wms.stock.rft.datasheet.NoPlanStorageInDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.NoPlanStorageOutDataSheet;
import jp.co.daifuku.wms.stock.rft.schedule.NstStartSCH;
import jp.co.daifuku.wms.stock.rft.schedule.NstStartSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class NstStartBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "NstStart";

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
    public NstStartBusiness()
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
        NoPlanStorageInDataSheet noPlanStorageInDataSheet = new NoPlanStorageInDataSheet(dsf, locale);
        NoPlanStorageOutDataSheet noPlanStorageOutDataSheet = new NoPlanStorageOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        NstStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new NstStartSCH(conn, this.getClass(), locale);

            // set input parameters.
            NstStartSCHParams inparam = new NstStartSCHParams();
            inparam.set(NstStartSCHParams.CONSIGNOR_CODE, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.CONSIGNOR_CODE));
            inparam.set(NstStartSCHParams.ITEM_CODE, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.ITEM_CODE));
            inparam.set(NstStartSCHParams.NO_PLAN_STORAGE_ITF_TO_JAN, termInfoDataSheet.getValue(TermInfoDataSheet.NO_PLAN_STORAGE_ITF_TO_JAN));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.CONSIGNOR_CODE, outparam.get(NstStartSCHParams.CONSIGNOR_CODE));
                noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.CONSIGNOR_NAME, outparam.get(NstStartSCHParams.CONSIGNOR_NAME));
                noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.ITEM_CODE, outparam.get(NstStartSCHParams.ITEM_CODE));
                noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.ITEM_NAME, outparam.get(NstStartSCHParams.ITEM_NAME));
                noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.JAN, outparam.get(NstStartSCHParams.JAN));
                noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.ITF, outparam.get(NstStartSCHParams.ITF));
                noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.BUNDLE_ITF, outparam.get(NstStartSCHParams.BUNDLE_ITF));
                noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.ENTERING_QTY, outparam.get(NstStartSCHParams.ENTERING_QTY));
                noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.BUNDLE_ENTERING_QTY, outparam.get(NstStartSCHParams.BUNDLE_ENTERING_QTY));
                noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.ENTERING_QTY_INPUT_ENABLED, outparam.get(NstStartSCHParams.ENTERING_QTY_INPUT_ENABLED));
                commonDataSheet.setValue(CommonDataSheet.CASE_INPUT_ENABLED, outparam.get(NstStartSCHParams.CASE_INPUT_ENABLED));
                commonDataSheet.setValue(CommonDataSheet.START_DATE_TIME, outparam.get(NstStartSCHParams.START_DATE_TIME));
                consignorDataSheet.setValue(ConsignorDataSheet.CONSIGNOR_NAME, outparam.get(NstStartSCHParams.CONSIGNOR_NAME));
                break;
            }

            commonDataSheet.setValue(CommonDataSheet.MISS_SCAN_COUNT, 0);
            noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_QTY, 0);
            noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_CASE_QTY, 0);
            noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_PIECE_QTY, 0);

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

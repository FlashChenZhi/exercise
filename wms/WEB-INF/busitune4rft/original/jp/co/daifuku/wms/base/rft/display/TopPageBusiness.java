// $Id$
package jp.co.daifuku.wms.base.rft.display;

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
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.rft.HaiSurfExceptionHandler;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.base.rft.schedule.TopPageSCH;
import jp.co.daifuku.wms.base.rft.schedule.TopPageSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class TopPageBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "TopPage";

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
    public TopPageBusiness()
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
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        TopPageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new TopPageSCH(conn, this.getClass(), locale);

            // set input parameters.
            TopPageSCHParams inparam = new TopPageSCHParams();
            inparam.set(TopPageSCHParams.IP_ADDRESS, "");

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                termInfoDataSheet.setValue(TermInfoDataSheet.TERMINAL_NO, outparam.get(TopPageSCHParams.TERMINAL_NO));
                termInfoDataSheet.setValue(TermInfoDataSheet.TERMINAL_TYPE, outparam.get(TopPageSCHParams.TERMINAL_TYPE));
                termInfoDataSheet.setValue(TermInfoDataSheet.PASSWORD_INPUT_MODE, outparam.get(TopPageSCHParams.PASSWORD_INPUT_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.RECEIVING_CONSIGNOR, outparam.get(TopPageSCHParams.RECEIVING_CONSIGNOR));
                termInfoDataSheet.setValue(TermInfoDataSheet.RECEIVING_CP_MODE, outparam.get(TopPageSCHParams.RECEIVING_CP_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.RECEIVING_ITF_TO_JAN, outparam.get(TopPageSCHParams.RECEIVING_ITF_TO_JAN));
                termInfoDataSheet.setValue(TermInfoDataSheet.STORAGE_CONSIGNOR, outparam.get(TopPageSCHParams.STORAGE_CONSIGNOR));
                termInfoDataSheet.setValue(TermInfoDataSheet.STORAGE_CP_MODE, outparam.get(TopPageSCHParams.STORAGE_CP_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.STORAGE_INSPECTION_MODE, outparam.get(TopPageSCHParams.STORAGE_INSPECTION_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.STORAGE_ITF_TO_JAN, outparam.get(TopPageSCHParams.STORAGE_ITF_TO_JAN));
                termInfoDataSheet.setValue(TermInfoDataSheet.RETRIEVAL_CONSIGNOR, outparam.get(TopPageSCHParams.RETRIEVAL_CONSIGNOR));
                termInfoDataSheet.setValue(TermInfoDataSheet.RETRIEVAL_CP_MODE, outparam.get(TopPageSCHParams.RETRIEVAL_CP_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.RETRIEVAL_INSPECTION_MODE, outparam.get(TopPageSCHParams.RETRIEVAL_INSPECTION_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.RETRIEVAL_ITF_TO_JAN, outparam.get(TopPageSCHParams.RETRIEVAL_ITF_TO_JAN));
                termInfoDataSheet.setValue(TermInfoDataSheet.SORTING_CONSIGNOR, outparam.get(TopPageSCHParams.SORTING_CONSIGNOR));
                termInfoDataSheet.setValue(TermInfoDataSheet.SORTING_CP_MODE, outparam.get(TopPageSCHParams.SORTING_CP_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.SORTING_INSPECTION_MODE, outparam.get(TopPageSCHParams.SORTING_INSPECTION_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.SORTING_ITF_TO_JAN, outparam.get(TopPageSCHParams.SORTING_ITF_TO_JAN));
                termInfoDataSheet.setValue(TermInfoDataSheet.SHIPPING_CONSIGNOR, outparam.get(TopPageSCHParams.SHIPPING_CONSIGNOR));
                termInfoDataSheet.setValue(TermInfoDataSheet.SHIPPING_CP_MODE, outparam.get(TopPageSCHParams.SHIPPING_CP_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.SHIPPING_ITF_TO_JAN, outparam.get(TopPageSCHParams.SHIPPING_ITF_TO_JAN));
                termInfoDataSheet.setValue(TermInfoDataSheet.MOVE_RETRIEVAL_CONSIGNOR, outparam.get(TopPageSCHParams.MOVE_RETRIEVAL_CONSIGNOR));
                termInfoDataSheet.setValue(TermInfoDataSheet.MOVE_RETRIEVAL_CP_MODE, outparam.get(TopPageSCHParams.MOVE_RETRIEVAL_CP_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.MOVE_RETRIEVAL_INSPECTION_MODE, outparam.get(TopPageSCHParams.MOVE_RETRIEVAL_INSPECTION_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.MOVE_RETRIEVAL_ITF_TO_JAN, outparam.get(TopPageSCHParams.MOVE_RETRIEVAL_ITF_TO_JAN));
                termInfoDataSheet.setValue(TermInfoDataSheet.MOVE_STORAGE_CONSIGNOR, outparam.get(TopPageSCHParams.MOVE_STORAGE_CONSIGNOR));
                termInfoDataSheet.setValue(TermInfoDataSheet.MOVE_STORAGE_CP_MODE, outparam.get(TopPageSCHParams.MOVE_STORAGE_CP_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.MOVE_STORAGE_INSPECTION_MODE, outparam.get(TopPageSCHParams.MOVE_STORAGE_INSPECTION_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.MOVE_STORAGE_ITF_TO_JAN, outparam.get(TopPageSCHParams.MOVE_STORAGE_ITF_TO_JAN));
                termInfoDataSheet.setValue(TermInfoDataSheet.INVENTORY_CONSIGNOR, outparam.get(TopPageSCHParams.INVENTORY_CONSIGNOR));
                termInfoDataSheet.setValue(TermInfoDataSheet.INVENTORY_CP_MODE, outparam.get(TopPageSCHParams.INVENTORY_CP_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.INVENTORY_INSPECTION_MODE, outparam.get(TopPageSCHParams.INVENTORY_INSPECTION_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.INVENTORY_ITF_TO_JAN, outparam.get(TopPageSCHParams.INVENTORY_ITF_TO_JAN));
                termInfoDataSheet.setValue(TermInfoDataSheet.NO_PLAN_STORAGE_CONSIGNOR, outparam.get(TopPageSCHParams.NO_PLAN_STORAGE_CONSIGNOR));
                termInfoDataSheet.setValue(TermInfoDataSheet.NO_PLAN_STORAGE_CP_MODE, outparam.get(TopPageSCHParams.NO_PLAN_STORAGE_CP_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.NO_PLAN_STORAGE_INSPECTION_MODE, outparam.get(TopPageSCHParams.NO_PLAN_STORAGE_INSPECTION_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.NO_PLAN_STORAGE_ITF_TO_JAN, outparam.get(TopPageSCHParams.NO_PLAN_STORAGE_ITF_TO_JAN));
                termInfoDataSheet.setValue(TermInfoDataSheet.NO_PLAN_RETRIEVAL_CONSIGNOR, outparam.get(TopPageSCHParams.NO_PLAN_RETRIEVAL_CONSIGNOR));
                termInfoDataSheet.setValue(TermInfoDataSheet.NO_PLAN_RETRIEVAL_CP_MODE, outparam.get(TopPageSCHParams.NO_PLAN_RETRIEVAL_CP_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.NO_PLAN_RETRIEVAL_INSPECTION_MODE, outparam.get(TopPageSCHParams.NO_PLAN_RETRIEVAL_INSPECTION_MODE));
                termInfoDataSheet.setValue(TermInfoDataSheet.NO_PLAN_RETRIEVAL_ITF_TO_JAN, outparam.get(TopPageSCHParams.NO_PLAN_RETRIEVAL_ITF_TO_JAN));
                break;
            }

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
    public HSResult start_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        TopPageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new TopPageSCH(conn, this.getClass(), locale);

            // set input parameters.
            TopPageSCHParams inparam = new TopPageSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(TopPageSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(TopPageSCHParams.STATUS_FLAG, SystemDefine.RFT_STATUS_FLAG_START);
            inparam.set(TopPageSCHParams.TO_REST, false);
            inparam.set(TopPageSCHParams.TO_RESTART, false);
            inparam.set(TopPageSCHParams.TERMINAL_TYPE, SystemDefine.TERMINAL_TYPE_HT);
            inparam.set(TopPageSCHParams.IP_ADDRESS, termInfoDataSheet.getValue(TermInfoDataSheet.IP_ADDRESS));

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
    public HSResult exit_Click(DataSheetFactory dsf, Locale locale)
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

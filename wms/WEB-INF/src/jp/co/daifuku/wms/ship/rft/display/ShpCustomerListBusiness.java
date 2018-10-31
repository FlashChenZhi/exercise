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
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.rft.HaiSurfExceptionHandler;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.datasheet.BatchDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.CommonDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.ConsignorDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.ship.rft.datasheet.CustomerListDataSheet;
import jp.co.daifuku.wms.ship.rft.schedule.ShpCustomerListSCH;
import jp.co.daifuku.wms.ship.rft.schedule.ShpCustomerListSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class ShpCustomerListBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "ShpCustomerList";

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
    public ShpCustomerListBusiness()
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
        BatchDataSheet batchDataSheet = new BatchDataSheet(dsf, locale);
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        ConsignorDataSheet consignorDataSheet = new ConsignorDataSheet(dsf, locale);
        CustomerListDataSheet customerListDataSheet = new CustomerListDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        ShpCustomerListSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new ShpCustomerListSCH(conn, this.getClass(), locale);

            // set input parameters.
            ShpCustomerListSCHParams inparam = new ShpCustomerListSCHParams();
            inparam.set(ShpCustomerListSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(ShpCustomerListSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(ShpCustomerListSCHParams.JOB_DETAILS, commonDataSheet.getValue(CommonDataSheet.JOB_DETAILS));
            inparam.set(ShpCustomerListSCHParams.CONSIGNOR_CODE, consignorDataSheet.getValue(ConsignorDataSheet.CONSIGNOR_CODE));
            inparam.set(ShpCustomerListSCHParams.BATCH_NO, batchDataSheet.getValue(BatchDataSheet.BATCH_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            customerListDataSheet.setValue(DataSheetModel.SIZE, outparams.size());
            for (int i = 0; i < outparams.size(); i++)
            {
                Params outparam = outparams.get(i);
                customerListDataSheet.setValue(CustomerListDataSheet.CUSTOMER_CODE, outparam.get(ShpCustomerListSCHParams.CUSTOMER_CODE), i);
                customerListDataSheet.setValue(CustomerListDataSheet.CUSTOMER_NAME, outparam.get(ShpCustomerListSCHParams.CUSTOMER_NAME), i);
            }

            // initialize DataSheet view.
            customerListDataSheet.initView();

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
    public HSResult ent_Click(DataSheetFactory dsf, Locale locale)
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
    public HSResult back_Click(DataSheetFactory dsf, Locale locale)
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

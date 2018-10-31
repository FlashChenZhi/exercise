// $Id: ShpStartBusiness.java 8041 2012-05-17 09:39:40Z nagao $
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
import jp.co.daifuku.wms.base.rft.datasheet.CommonDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.ConsignorDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.ship.rft.datasheet.CustomerDataSheet;
import jp.co.daifuku.wms.ship.rft.datasheet.ShippingCustomerInDataSheet;
import jp.co.daifuku.wms.ship.rft.datasheet.ShippingCustomerOutDataSheet;
import jp.co.daifuku.wms.ship.rft.schedule.ShpStartSCH;
import jp.co.daifuku.wms.ship.rft.schedule.ShpStartSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision: 8041 $, $Date: 2012-05-17 18:39:40 +0900 (木, 17 5 2012) $
 * @author  HighTune.
 * @author  Last commit: $Author: nagao $
 */
public class ShpStartBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "ShpStart";

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
    public ShpStartBusiness()
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
        CustomerDataSheet customerDataSheet = new CustomerDataSheet(dsf, locale);
        ShippingCustomerInDataSheet shippingCustomerInDataSheet = new ShippingCustomerInDataSheet(dsf, locale);
        ShippingCustomerOutDataSheet shippingCustomerOutDataSheet = new ShippingCustomerOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        ShpStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new ShpStartSCH(conn, this.getClass(), locale);

            // set input parameters.
            ShpStartSCHParams inparam = new ShpStartSCHParams();
            inparam.set(ShpStartSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(ShpStartSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(ShpStartSCHParams.CONSIGNOR_CODE, shippingCustomerInDataSheet.getValue(ShippingCustomerInDataSheet.CONSIGNOR_CODE));
            inparam.set(ShpStartSCHParams.BATCH_NO, shippingCustomerInDataSheet.getValue(ShippingCustomerInDataSheet.BATCH_NO));
            inparam.set(ShpStartSCHParams.CUSTOMER_CODE, shippingCustomerInDataSheet.getValue(ShippingCustomerInDataSheet.CUSTOMER_CODE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.SETTING_UNIT_KEY, outparam.get(ShpStartSCHParams.SETTING_UNIT_KEY));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.CONSIGNOR_CODE, outparam.get(ShpStartSCHParams.CONSIGNOR_CODE));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.CONSIGNOR_NAME, outparam.get(ShpStartSCHParams.CONSIGNOR_NAME));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.CUSTOMER_CODE, outparam.get(ShpStartSCHParams.CUSTOMER_CODE));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.CUSTOMER_NAME, outparam.get(ShpStartSCHParams.CUSTOMER_NAME));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.PLAN_DATE, outparam.get(ShpStartSCHParams.PLAN_DATE));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.BATCH_NO, outparam.get(ShpStartSCHParams.BATCH_NO));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.TOTAL_ITEM, outparam.get(ShpStartSCHParams.TOTAL_ITEM));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.PENDING_ITEM, outparam.get(ShpStartSCHParams.PENDING_ITEM));
                commonDataSheet.setValue(CommonDataSheet.START_DATE_TIME, outparam.get(ShpStartSCHParams.START_DATE_TIME));
                customerDataSheet.setValue(CustomerDataSheet.CUSTOMER_NAME, outparam.get(ShpStartSCHParams.CUSTOMER_NAME));
                consignorDataSheet.setValue(ConsignorDataSheet.CONSIGNOR_NAME, outparam.get(ShpStartSCHParams.CONSIGNOR_NAME));
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.QTY_INPUT_ENABLED, outparam.get(ShpStartSCHParams.QTY_INPUT_ENABLED));
                break;
            }

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
        return "$Id: ShpStartBusiness.java 8041 2012-05-17 09:39:40Z nagao $";
    }
}
//end of class

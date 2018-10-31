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
import jp.co.daifuku.wms.ship.rft.datasheet.ShippingCustomerOutDataSheet;
import jp.co.daifuku.wms.ship.rft.datasheet.ShippingShortageListDataSheet;
import jp.co.daifuku.wms.ship.rft.schedule.ShpCompleteSCH;
import jp.co.daifuku.wms.ship.rft.schedule.ShpCompleteSCHParams;
import jp.co.daifuku.wms.ship.rft.schedule.ShpShortageListSCH;
import jp.co.daifuku.wms.ship.rft.schedule.ShpShortageListSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class ShpShortageListBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "ShpShortageList";

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
    public ShpShortageListBusiness()
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
        ShippingCustomerOutDataSheet shippingCustomerOutDataSheet = new ShippingCustomerOutDataSheet(dsf, locale);
        ShippingShortageListDataSheet shippingShortageListDataSheet = new ShippingShortageListDataSheet(dsf, locale);

        Connection conn = null;
        ShpShortageListSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new ShpShortageListSCH(conn, this.getClass(), locale);

            // set input parameters.
            ShpShortageListSCHParams inparam = new ShpShortageListSCHParams();
            inparam.set(ShpShortageListSCHParams.SETTING_UNIT_KEY, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.SETTING_UNIT_KEY));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            shippingCustomerOutDataSheet.setValue(DataSheetModel.SIZE, outparams.size());
            for (int i = 0; i < outparams.size(); i++)
            {
                Params outparam = outparams.get(i);
                shippingCustomerOutDataSheet.setValue(ShippingCustomerOutDataSheet.PENDING_ITEM, outparam.get(ShpShortageListSCHParams.PENDING_ITEM));
                shippingShortageListDataSheet.setValue(ShippingShortageListDataSheet.ITEM_CODE, outparam.get(ShpShortageListSCHParams.ITEM_CODE), i);
                shippingShortageListDataSheet.setValue(ShippingShortageListDataSheet.ITEM_NAME, outparam.get(ShpShortageListSCHParams.ITEM_NAME), i);
                shippingShortageListDataSheet.setValue(ShippingShortageListDataSheet.SHORTAGE_CASE_QTY, outparam.get(ShpShortageListSCHParams.SHORTAGE_CASE_QTY), i);
                shippingShortageListDataSheet.setValue(ShippingShortageListDataSheet.SHORTAGE_PIECE_QTY, outparam.get(ShpShortageListSCHParams.SHORTAGE_PIECE_QTY), i);
            }

            // initialize DataSheet view.
            shippingCustomerOutDataSheet.initView();

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
    public HSResult shortage_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
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
            inparam.set(ShpCompleteSCHParams.BATCH_NO, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.BATCH_NO));
            inparam.set(ShpCompleteSCHParams.CUSTOMER_CODE, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.CUSTOMER_CODE));
            inparam.set(ShpCompleteSCHParams.CUSTOMER_NAME, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.CUSTOMER_NAME));
            inparam.set(ShpCompleteSCHParams.SETTING_UNIT_KEY, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.SETTING_UNIT_KEY));
            inparam.set(ShpCompleteSCHParams.START_DATE_TIME, commonDataSheet.getValue(CommonDataSheet.START_DATE_TIME));
            inparam.set(ShpCompleteSCHParams.MISS_SCAN_COUNT, commonDataSheet.getValue(CommonDataSheet.MISS_SCAN_COUNT));
            inparam.set(ShpCompleteSCHParams.COMPLETION_FLAG, Parameter.COMPLETION_FLAG_DECISION);

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
    public HSResult pending_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
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
            inparam.set(ShpCompleteSCHParams.BATCH_NO, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.BATCH_NO));
            inparam.set(ShpCompleteSCHParams.CUSTOMER_CODE, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.CUSTOMER_CODE));
            inparam.set(ShpCompleteSCHParams.CUSTOMER_NAME, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.CUSTOMER_NAME));
            inparam.set(ShpCompleteSCHParams.SETTING_UNIT_KEY, shippingCustomerOutDataSheet.getValue(ShippingCustomerOutDataSheet.SETTING_UNIT_KEY));
            inparam.set(ShpCompleteSCHParams.START_DATE_TIME, commonDataSheet.getValue(CommonDataSheet.START_DATE_TIME));
            inparam.set(ShpCompleteSCHParams.MISS_SCAN_COUNT, commonDataSheet.getValue(CommonDataSheet.MISS_SCAN_COUNT));
            inparam.set(ShpCompleteSCHParams.COMPLETION_FLAG, Parameter.COMPLETION_FLAG_REMNANT);

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

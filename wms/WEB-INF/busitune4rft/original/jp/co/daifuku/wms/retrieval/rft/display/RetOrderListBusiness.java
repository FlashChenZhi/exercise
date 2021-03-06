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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.rft.HaiSurfExceptionHandler;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.datasheet.AreaDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.CommonDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.ConsignorDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.retrieval.rft.datasheet.OrderDataSheet;
import jp.co.daifuku.wms.retrieval.rft.datasheet.OrderListDataSheet;
import jp.co.daifuku.wms.retrieval.rft.schedule.RetOrderListSCH;
import jp.co.daifuku.wms.retrieval.rft.schedule.RetOrderListSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class RetOrderListBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "RetOrderList";

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
    public RetOrderListBusiness()
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
        AreaDataSheet areaDataSheet = new AreaDataSheet(dsf, locale);
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        ConsignorDataSheet consignorDataSheet = new ConsignorDataSheet(dsf, locale);
        OrderDataSheet orderDataSheet = new OrderDataSheet(dsf, locale);
        OrderListDataSheet orderListDataSheet = new OrderListDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        RetOrderListSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new RetOrderListSCH(conn, this.getClass(), locale);

            // set input parameters.
            RetOrderListSCHParams inparam = new RetOrderListSCHParams();
            inparam.set(RetOrderListSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(RetOrderListSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(RetOrderListSCHParams.CONSIGNOR_CODE, consignorDataSheet.getValue(ConsignorDataSheet.CONSIGNOR_CODE));
            inparam.set(RetOrderListSCHParams.AREA_NO, areaDataSheet.getValue(AreaDataSheet.AREA_NO));
            inparam.set(RetOrderListSCHParams.ORDER_NO1, orderDataSheet.getValue(OrderDataSheet.ORDER_NO1));
            inparam.set(RetOrderListSCHParams.ORDER_NO2, orderDataSheet.getValue(OrderDataSheet.ORDER_NO2));
            inparam.set(RetOrderListSCHParams.ORDER_NO3, orderDataSheet.getValue(OrderDataSheet.ORDER_NO3));
            inparam.set(RetOrderListSCHParams.ORDER_NO4, orderDataSheet.getValue(OrderDataSheet.ORDER_NO4));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            orderListDataSheet.setValue(DataSheetModel.SIZE, outparams.size());
            for (int i = 0; i < outparams.size(); i++)
            {
                Params outparam = outparams.get(i);
                orderListDataSheet.setValue(OrderListDataSheet.ORDER_NO, outparam.get(RetOrderListSCHParams.ORDER_NO), i);
                orderListDataSheet.setValue(OrderListDataSheet.CUSTOMER_CODE, outparam.get(RetOrderListSCHParams.CUSTOMER_CODE), i);
                orderListDataSheet.setValue(OrderListDataSheet.CUSTOMER_NAME, outparam.get(RetOrderListSCHParams.CUSTOMER_NAME), i);
            }

            // initialize DataSheet view.
            orderListDataSheet.initView();

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
    public HSResult ent_Click(DataSheetFactory dsf, Locale locale)
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

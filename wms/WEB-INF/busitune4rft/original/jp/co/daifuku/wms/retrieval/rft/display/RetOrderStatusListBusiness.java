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
import jp.co.daifuku.wms.retrieval.rft.datasheet.RetrievalInDataSheet;
import jp.co.daifuku.wms.retrieval.rft.datasheet.RetrievalOutDataSheet;
import jp.co.daifuku.wms.retrieval.rft.schedule.RetOrderStatusListSCH;
import jp.co.daifuku.wms.retrieval.rft.schedule.RetOrderStatusListSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class RetOrderStatusListBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "RetOrderStatusList";

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
    public RetOrderStatusListBusiness()
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
        RetrievalInDataSheet retrievalInDataSheet = new RetrievalInDataSheet(dsf, locale);
        RetrievalOutDataSheet retrievalOutDataSheet = new RetrievalOutDataSheet(dsf, locale);

        Connection conn = null;
        RetOrderStatusListSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new RetOrderStatusListSCH(conn, this.getClass(), locale);

            // set input parameters.
            RetOrderStatusListSCHParams inparam = new RetOrderStatusListSCHParams();
            inparam.set(RetOrderStatusListSCHParams.CONSIGNOR_CODE, retrievalInDataSheet.getValue(RetrievalInDataSheet.CONSIGNOR_CODE));
            inparam.set(RetOrderStatusListSCHParams.AREA_NO, retrievalInDataSheet.getValue(RetrievalInDataSheet.AREA_NO));
            inparam.set(RetOrderStatusListSCHParams.ORDER_NO1, retrievalInDataSheet.getValue(RetrievalInDataSheet.ORDER_NO1));
            inparam.set(RetOrderStatusListSCHParams.ORDER_NO2, retrievalInDataSheet.getValue(RetrievalInDataSheet.ORDER_NO2));
            inparam.set(RetOrderStatusListSCHParams.ORDER_NO3, retrievalInDataSheet.getValue(RetrievalInDataSheet.ORDER_NO3));
            inparam.set(RetOrderStatusListSCHParams.ORDER_NO4, retrievalInDataSheet.getValue(RetrievalInDataSheet.ORDER_NO4));
            inparam.set(RetOrderStatusListSCHParams.IS_LOCK, retrievalInDataSheet.getValue(RetrievalInDataSheet.IS_LOCK));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_NO1_STATUS, outparam.get(RetOrderStatusListSCHParams.ORDER_NO1_STATUS));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_NO2_STATUS, outparam.get(RetOrderStatusListSCHParams.ORDER_NO2_STATUS));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_NO3_STATUS, outparam.get(RetOrderStatusListSCHParams.ORDER_NO3_STATUS));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_NO4_STATUS, outparam.get(RetOrderStatusListSCHParams.ORDER_NO4_STATUS));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_NO1, outparam.get(RetOrderStatusListSCHParams.ORDER_NO1));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_NO2, outparam.get(RetOrderStatusListSCHParams.ORDER_NO2));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_NO3, outparam.get(RetOrderStatusListSCHParams.ORDER_NO3));
                retrievalOutDataSheet.setValue(RetrievalOutDataSheet.ORDER_NO4, outparam.get(RetOrderStatusListSCHParams.ORDER_NO4));
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

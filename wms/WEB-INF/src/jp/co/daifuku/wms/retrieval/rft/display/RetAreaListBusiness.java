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
import jp.co.daifuku.wms.base.rft.datasheet.AreaListDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.CommonDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.ConsignorDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.retrieval.rft.schedule.RetAreaInputSCH;
import jp.co.daifuku.wms.retrieval.rft.schedule.RetAreaInputSCHParams;
import jp.co.daifuku.wms.retrieval.rft.schedule.RetAreaListSCH;
import jp.co.daifuku.wms.retrieval.rft.schedule.RetAreaListSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class RetAreaListBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "RetAreaList";

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
    public RetAreaListBusiness()
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
        AreaListDataSheet areaListDataSheet = new AreaListDataSheet(dsf, locale);
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        ConsignorDataSheet consignorDataSheet = new ConsignorDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        RetAreaListSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new RetAreaListSCH(conn, this.getClass(), locale);

            // set input parameters.
            RetAreaListSCHParams inparam = new RetAreaListSCHParams();
            inparam.set(RetAreaListSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(RetAreaListSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(RetAreaListSCHParams.CONSIGNOR_CODE, consignorDataSheet.getValue(ConsignorDataSheet.CONSIGNOR_CODE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            areaListDataSheet.setValue(DataSheetModel.SIZE, outparams.size());
            for (int i = 0; i < outparams.size(); i++)
            {
                Params outparam = outparams.get(i);
                areaListDataSheet.setValue(AreaListDataSheet.AREA_NO, outparam.get(RetAreaListSCHParams.AREA_NO), i);
                areaListDataSheet.setValue(AreaListDataSheet.AREA_NAME, outparam.get(RetAreaListSCHParams.AREA_NAME), i);
            }

            // initialize DataSheet view.
            areaListDataSheet.initView();

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
        // initialize DataSheets.
        AreaDataSheet areaDataSheet = new AreaDataSheet(dsf, locale);
        AreaListDataSheet areaListDataSheet = new AreaListDataSheet(dsf, locale);
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        ConsignorDataSheet consignorDataSheet = new ConsignorDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        RetAreaInputSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new RetAreaInputSCH(conn, this.getClass(), locale);

            // set input parameters.
            RetAreaInputSCHParams inparam = new RetAreaInputSCHParams();
            inparam.set(RetAreaInputSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(RetAreaInputSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(RetAreaInputSCHParams.CONSIGNOR_CODE, consignorDataSheet.getValue(ConsignorDataSheet.CONSIGNOR_CODE));
            inparam.set(RetAreaInputSCHParams.AREA_NO, areaListDataSheet.getValue(AreaListDataSheet.AREA_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                areaDataSheet.setValue(AreaDataSheet.AREA_NO, outparam.get(RetAreaInputSCHParams.AREA_NO));
                areaDataSheet.setValue(AreaDataSheet.AREA_NAME, outparam.get(RetAreaInputSCHParams.AREA_NAME));
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

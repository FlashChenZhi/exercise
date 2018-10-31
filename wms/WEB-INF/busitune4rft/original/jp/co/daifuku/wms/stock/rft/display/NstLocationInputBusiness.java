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
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.rft.HaiSurfExceptionHandler;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.RftConst;
import jp.co.daifuku.wms.base.rft.datasheet.CommonDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.LocationDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.NoPlanStorageInDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.NoPlanStorageOutDataSheet;
import jp.co.daifuku.wms.stock.rft.schedule.NstCompleteSCH;
import jp.co.daifuku.wms.stock.rft.schedule.NstCompleteSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class NstLocationInputBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "NstLocationInput";

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
    public NstLocationInputBusiness()
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
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        NoPlanStorageInDataSheet noPlanStorageInDataSheet = new NoPlanStorageInDataSheet(dsf, locale);
        NoPlanStorageOutDataSheet noPlanStorageOutDataSheet = new NoPlanStorageOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        // input validation.
        if (StringUtil.isBlank(noPlanStorageInDataSheet.getText(NoPlanStorageInDataSheet.RESULT_AREA_LOCATION)))
        {
            setBeep(RftConst.BEEP);
            setFocus("AreaLocNo");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        Connection conn = null;
        NstCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new NstCompleteSCH(conn, this.getClass(), locale);

            // set input parameters.
            NstCompleteSCHParams inparam = new NstCompleteSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(NstCompleteSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(NstCompleteSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(NstCompleteSCHParams.CONSIGNOR_CODE, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.CONSIGNOR_CODE));
            inparam.set(NstCompleteSCHParams.CONSIGNOR_NAME, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.CONSIGNOR_NAME));
            inparam.set(NstCompleteSCHParams.ITEM_CODE, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.ITEM_CODE));
            inparam.set(NstCompleteSCHParams.ITEM_NAME, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.ITEM_NAME));
            inparam.set(NstCompleteSCHParams.JAN, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.JAN));
            inparam.set(NstCompleteSCHParams.ITF, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.ITF));
            inparam.set(NstCompleteSCHParams.BUNDLE_ITF, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.BUNDLE_ITF));
            inparam.set(NstCompleteSCHParams.ENTERING_QTY, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.ENTERING_QTY));
            inparam.set(NstCompleteSCHParams.BUNDLE_ENTERING_QTY, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.BUNDLE_ENTERING_QTY));
            inparam.set(NstCompleteSCHParams.RESULT_LOT_NO, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.RESULT_LOT_NO));
            inparam.set(NstCompleteSCHParams.RESULT_QTY, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.RESULT_QTY));
            inparam.set(NstCompleteSCHParams.RESULT_AREA_LOCATION, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_AREA_LOCATION));
            inparam.set(NstCompleteSCHParams.START_DATE_TIME, commonDataSheet.getValue(CommonDataSheet.START_DATE_TIME));
            inparam.set(NstCompleteSCHParams.MISS_SCAN_COUNT, commonDataSheet.getValue(CommonDataSheet.MISS_SCAN_COUNT));
            inparam.set(NstCompleteSCHParams.FORCE_STRAGE_FLAG, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.FORCE_STRAGE_FLAG));

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
        // initialize DataSheets.
        NoPlanStorageOutDataSheet noPlanStorageOutDataSheet = new NoPlanStorageOutDataSheet(dsf, locale);

        // set output parameters.
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_AREA_LOCATION, "");

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
    public HSResult list_Click(DataSheetFactory dsf, Locale locale)
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
    public HSResult detail_Click(DataSheetFactory dsf, Locale locale)
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
    public HSResult list1_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        LocationDataSheet locationDataSheet = new LocationDataSheet(dsf, locale);
        NoPlanStorageOutDataSheet noPlanStorageOutDataSheet = new NoPlanStorageOutDataSheet(dsf, locale);

        // set output parameters.
        locationDataSheet.setValue(LocationDataSheet.SEARCH_LOCATION, RftConst.LOCATION_FLAG_EMP);
        locationDataSheet.setValue(LocationDataSheet.CONSIGNOR_CODE, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.CONSIGNOR_CODE));
        locationDataSheet.setValue(LocationDataSheet.ITEM_CODE, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.ITEM_CODE));

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
    public HSResult list2_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        LocationDataSheet locationDataSheet = new LocationDataSheet(dsf, locale);
        NoPlanStorageOutDataSheet noPlanStorageOutDataSheet = new NoPlanStorageOutDataSheet(dsf, locale);

        // set output parameters.
        locationDataSheet.setValue(LocationDataSheet.SEARCH_LOCATION, RftConst.LOCATION_FLAG_SPLY);
        locationDataSheet.setValue(LocationDataSheet.CONSIGNOR_CODE, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.CONSIGNOR_CODE));
        locationDataSheet.setValue(LocationDataSheet.ITEM_CODE, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.ITEM_CODE));

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

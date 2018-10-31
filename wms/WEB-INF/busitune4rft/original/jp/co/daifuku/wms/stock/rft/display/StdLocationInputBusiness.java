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
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.rft.HaiSurfExceptionHandler;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.RftConst;
import jp.co.daifuku.wms.base.rft.datasheet.CommonDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.LocationDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.ReceivingStorageInDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.ReceivingStorageOutDataSheet;
import jp.co.daifuku.wms.stock.rft.schedule.StdCompleteSCH;
import jp.co.daifuku.wms.stock.rft.schedule.StdCompleteSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class StdLocationInputBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "StdLocationInput";

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
    public StdLocationInputBusiness()
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
        ReceivingStorageInDataSheet receivingStorageInDataSheet = new ReceivingStorageInDataSheet(dsf, locale);
        ReceivingStorageOutDataSheet receivingStorageOutDataSheet = new ReceivingStorageOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        // input validation.
        if (StringUtil.isBlank(receivingStorageInDataSheet.getText(ReceivingStorageInDataSheet.RESULT_AREA_LOCATION)))
        {
            setBeep(RftConst.BEEP);
            setFocus("ResultAreaLocNo");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        Connection conn = null;
        StdCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new StdCompleteSCH(conn, this.getClass(), locale);

            // set input parameters.
            StdCompleteSCHParams inparam = new StdCompleteSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(StdCompleteSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(StdCompleteSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(StdCompleteSCHParams.MOVE_JOB_NO, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.MOVE_JOB_NO));
            inparam.set(StdCompleteSCHParams.SETTING_UNIT_KEY, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.SETTING_UNIT_KEY));
            inparam.set(StdCompleteSCHParams.CONSIGNOR_CODE, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.CONSIGNOR_CODE));
            inparam.set(StdCompleteSCHParams.ITEM_CODE, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.ITEM_CODE));
            inparam.set(StdCompleteSCHParams.LOT_NO, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.LOT_NO));
            inparam.set(StdCompleteSCHParams.AREA_NO, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.AREA_NO));
            inparam.set(StdCompleteSCHParams.LOCATION_NO, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.LOCATION_NO));
            inparam.set(StdCompleteSCHParams.RESULT_AREA_LOCATION, receivingStorageInDataSheet.getValue(ReceivingStorageInDataSheet.RESULT_AREA_LOCATION));
            inparam.set(StdCompleteSCHParams.RESULT_QTY, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.RESULT_QTY));
            inparam.set(StdCompleteSCHParams.FORCE_STRAGE_FLAG, receivingStorageInDataSheet.getValue(ReceivingStorageInDataSheet.FORCE_STRAGE_FLAG));
            inparam.set(StdCompleteSCHParams.COMPLETION_FLAG, Parameter.COMPLETION_FLAG_DECISION);
            inparam.set(StdCompleteSCHParams.START_DATE_TIME, commonDataSheet.getValue(CommonDataSheet.START_DATE_TIME));
            inparam.set(StdCompleteSCHParams.MISS_SCAN_COUNT, commonDataSheet.getValue(CommonDataSheet.MISS_SCAN_COUNT));

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
    public HSResult edit_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        ReceivingStorageInDataSheet receivingStorageInDataSheet = new ReceivingStorageInDataSheet(dsf, locale);
        ReceivingStorageOutDataSheet receivingStorageOutDataSheet = new ReceivingStorageOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        StdCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new StdCompleteSCH(conn, this.getClass(), locale);

            // set input parameters.
            StdCompleteSCHParams inparam = new StdCompleteSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(StdCompleteSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(StdCompleteSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(StdCompleteSCHParams.MOVE_JOB_NO, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.MOVE_JOB_NO));
            inparam.set(StdCompleteSCHParams.CONSIGNOR_CODE, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.CONSIGNOR_CODE));
            inparam.set(StdCompleteSCHParams.ITEM_CODE, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.ITEM_CODE));
            inparam.set(StdCompleteSCHParams.LOT_NO, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.LOT_NO));
            inparam.set(StdCompleteSCHParams.AREA_NO, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.AREA_NO));
            inparam.set(StdCompleteSCHParams.LOCATION_NO, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.LOCATION_NO));
            inparam.set(StdCompleteSCHParams.RESULT_QTY, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.RESULT_QTY));
            inparam.set(StdCompleteSCHParams.FORCE_STRAGE_FLAG, receivingStorageInDataSheet.getValue(ReceivingStorageInDataSheet.FORCE_STRAGE_FLAG));
            inparam.set(StdCompleteSCHParams.COMPLETION_FLAG, Parameter.COMPLETION_FLAG_DECISION);

            // SCH call.
            sch.startSCH(inparam);

            // commit.
            conn.commit();

            // set output parameters.
            receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.IS_STORAGE_START, false);

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
    public HSResult cancel_Click(DataSheetFactory dsf, Locale locale)
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
        ReceivingStorageOutDataSheet receivingStorageOutDataSheet = new ReceivingStorageOutDataSheet(dsf, locale);

        // set output parameters.
        locationDataSheet.setValue(LocationDataSheet.SEARCH_LOCATION, RftConst.LOCATION_FLAG_EMP);
        locationDataSheet.setValue(LocationDataSheet.CONSIGNOR_CODE, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.CONSIGNOR_CODE));
        locationDataSheet.setValue(LocationDataSheet.ITEM_CODE, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.ITEM_CODE));

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
        ReceivingStorageOutDataSheet receivingStorageOutDataSheet = new ReceivingStorageOutDataSheet(dsf, locale);

        // set output parameters.
        locationDataSheet.setValue(LocationDataSheet.SEARCH_LOCATION, RftConst.LOCATION_FLAG_SPLY);
        locationDataSheet.setValue(LocationDataSheet.CONSIGNOR_CODE, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.CONSIGNOR_CODE));
        locationDataSheet.setValue(LocationDataSheet.ITEM_CODE, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.ITEM_CODE));

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

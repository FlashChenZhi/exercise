// $Id$
package jp.co.daifuku.wms.receiving.rft.display;

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
import jp.co.daifuku.wms.base.rft.HaiSurfExceptionHandler;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.datasheet.CommonDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.receiving.rft.datasheet.ReceivingAreaDataSheet;
import jp.co.daifuku.wms.receiving.rft.datasheet.ReceivingOutDataSheet;
import jp.co.daifuku.wms.receiving.rft.schedule.RecTempStorageSCH;
import jp.co.daifuku.wms.receiving.rft.schedule.RecTempStorageSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class RecTempStorageBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "RecTempStorage";

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
    public RecTempStorageBusiness()
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
        ReceivingAreaDataSheet receivingAreaDataSheet = new ReceivingAreaDataSheet(dsf, locale);
        ReceivingOutDataSheet receivingOutDataSheet = new ReceivingOutDataSheet(dsf, locale);

        Connection conn = null;
        RecTempStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new RecTempStorageSCH(conn, this.getClass(), locale);

            // set input parameters.
            RecTempStorageSCHParams inparam = new RecTempStorageSCHParams();
            inparam.set(RecTempStorageSCHParams.CONSIGNOR_CODE, receivingOutDataSheet.getValue(ReceivingOutDataSheet.CONSIGNOR_CODE));
            inparam.set(RecTempStorageSCHParams.ITEM_CODE, receivingOutDataSheet.getValue(ReceivingOutDataSheet.ITEM_CODE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                receivingAreaDataSheet.setValue(ReceivingAreaDataSheet.AREA_NO, outparam.get(RecTempStorageSCHParams.AREA_NO));
                receivingAreaDataSheet.setValue(ReceivingAreaDataSheet.AREA_NAME, outparam.get(RecTempStorageSCHParams.AREA_NAME));
                receivingAreaDataSheet.setValue(ReceivingAreaDataSheet.TEMP_AREA_NO, outparam.get(RecTempStorageSCHParams.TEMP_AREA_NO));
                receivingAreaDataSheet.setValue(ReceivingAreaDataSheet.TEMP_AREA_NAME, outparam.get(RecTempStorageSCHParams.TEMP_AREA_NAME));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.TEMP_AREA_NO, outparam.get(RecTempStorageSCHParams.TEMP_AREA_NO));
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
    public HSResult ent_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        ReceivingAreaDataSheet receivingAreaDataSheet = new ReceivingAreaDataSheet(dsf, locale);
        ReceivingOutDataSheet receivingOutDataSheet = new ReceivingOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        RecTempStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new RecTempStorageSCH(conn, this.getClass(), locale);

            // set input parameters.
            RecTempStorageSCHParams inparam = new RecTempStorageSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(RecTempStorageSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(RecTempStorageSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(RecTempStorageSCHParams.SETTING_UNIT_KEY, receivingOutDataSheet.getValue(ReceivingOutDataSheet.SETTING_UNIT_KEY));
            inparam.set(RecTempStorageSCHParams.CONSIGNOR_CODE, receivingOutDataSheet.getValue(ReceivingOutDataSheet.CONSIGNOR_CODE));
            inparam.set(RecTempStorageSCHParams.ITEM_CODE, receivingOutDataSheet.getValue(ReceivingOutDataSheet.ITEM_CODE));
            inparam.set(RecTempStorageSCHParams.RESULT_LOT_NO, receivingOutDataSheet.getValue(ReceivingOutDataSheet.RESULT_LOT_NO));
            inparam.set(RecTempStorageSCHParams.ENTERING_QTY, receivingOutDataSheet.getValue(ReceivingOutDataSheet.ENTERING_QTY));
            inparam.set(RecTempStorageSCHParams.RESULT_QTY, receivingOutDataSheet.getValue(ReceivingOutDataSheet.RESULT_QTY));
            inparam.set(RecTempStorageSCHParams.TEMP_AREA_NO, receivingOutDataSheet.getValue(ReceivingOutDataSheet.TEMP_AREA_NO));
            inparam.set(RecTempStorageSCHParams.CHAGE_TEMP_AREA_NO, receivingAreaDataSheet.getValue(ReceivingAreaDataSheet.TEMP_AREA_NO));

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

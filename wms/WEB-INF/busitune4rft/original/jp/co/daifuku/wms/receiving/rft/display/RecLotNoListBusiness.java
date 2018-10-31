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
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.rft.HaiSurfExceptionHandler;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.datasheet.CommonDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.ConsignorDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.ItemDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.LotNoListDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.receiving.rft.datasheet.ReceivingInDataSheet;
import jp.co.daifuku.wms.receiving.rft.datasheet.ReceivingOutDataSheet;
import jp.co.daifuku.wms.receiving.rft.schedule.RecLotNoListSCH;
import jp.co.daifuku.wms.receiving.rft.schedule.RecLotNoListSCHParams;
import jp.co.daifuku.wms.receiving.rft.schedule.RecStartSCH;
import jp.co.daifuku.wms.receiving.rft.schedule.RecStartSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class RecLotNoListBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "RecLotNoList";

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
    public RecLotNoListBusiness()
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
        LotNoListDataSheet lotNoListDataSheet = new LotNoListDataSheet(dsf, locale);
        ReceivingInDataSheet receivingInDataSheet = new ReceivingInDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        RecLotNoListSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new RecLotNoListSCH(conn, this.getClass(), locale);

            // set input parameters.
            RecLotNoListSCHParams inparam = new RecLotNoListSCHParams();
            inparam.set(RecLotNoListSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(RecLotNoListSCHParams.RECEIVING_ITF_TO_JAN, termInfoDataSheet.getValue(TermInfoDataSheet.RECEIVING_ITF_TO_JAN));
            inparam.set(RecLotNoListSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(RecLotNoListSCHParams.CONSIGNOR_CODE, receivingInDataSheet.getValue(ReceivingInDataSheet.CONSIGNOR_CODE));
            inparam.set(RecLotNoListSCHParams.ITEM_CODE, receivingInDataSheet.getValue(ReceivingInDataSheet.ITEM_CODE));
            inparam.set(RecLotNoListSCHParams.PLAN_DATE, receivingInDataSheet.getValue(ReceivingInDataSheet.PLAN_DATE));
            inparam.set(RecLotNoListSCHParams.SUPPLIER_CODE, receivingInDataSheet.getValue(ReceivingInDataSheet.SUPPLIER_CODE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            lotNoListDataSheet.setValue(DataSheetModel.SIZE, outparams.size());
            for (int i = 0; i < outparams.size(); i++)
            {
                Params outparam = outparams.get(i);
                lotNoListDataSheet.setValue(LotNoListDataSheet.LOT_NO, outparam.get(RecLotNoListSCHParams.LOT_NO), i);
            }

            // initialize DataSheet view.
            lotNoListDataSheet.initView();

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
        // initialize DataSheets.
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        ConsignorDataSheet consignorDataSheet = new ConsignorDataSheet(dsf, locale);
        ItemDataSheet itemDataSheet = new ItemDataSheet(dsf, locale);
        LotNoListDataSheet lotNoListDataSheet = new LotNoListDataSheet(dsf, locale);
        ReceivingInDataSheet receivingInDataSheet = new ReceivingInDataSheet(dsf, locale);
        ReceivingOutDataSheet receivingOutDataSheet = new ReceivingOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        RecStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new RecStartSCH(conn, this.getClass(), locale);

            // set input parameters.
            RecStartSCHParams inparam = new RecStartSCHParams();
            inparam.set(RecStartSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(RecStartSCHParams.RECEIVING_ITF_TO_JAN, termInfoDataSheet.getValue(TermInfoDataSheet.RECEIVING_ITF_TO_JAN));
            inparam.set(RecStartSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(RecStartSCHParams.CONSIGNOR_CODE, receivingInDataSheet.getValue(ReceivingInDataSheet.CONSIGNOR_CODE));
            inparam.set(RecStartSCHParams.ITEM_CODE, receivingInDataSheet.getValue(ReceivingInDataSheet.ITEM_CODE));
            inparam.set(RecStartSCHParams.PLAN_DATE, receivingInDataSheet.getValue(ReceivingInDataSheet.PLAN_DATE));
            inparam.set(RecStartSCHParams.SUPPLIER_CODE, receivingInDataSheet.getValue(ReceivingInDataSheet.SUPPLIER_CODE));
            inparam.set(RecStartSCHParams.LOT_NO, lotNoListDataSheet.getValue(LotNoListDataSheet.LOT_NO));
            inparam.set(RecStartSCHParams.LOT_NO_SELECT, true);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            for (Params outparam : outparams)
            {
                itemDataSheet.setValue(ItemDataSheet.ITEM_NAME, outparam.get(RecStartSCHParams.ITEM_NAME));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.SETTING_UNIT_KEY, outparam.get(RecStartSCHParams.SETTING_UNIT_KEY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.CONSIGNOR_CODE, outparam.get(RecStartSCHParams.CONSIGNOR_CODE));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.CONSIGNOR_NAME, outparam.get(RecStartSCHParams.CONSIGNOR_NAME));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.PLAN_DATE, outparam.get(RecStartSCHParams.PLAN_DATE));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.SUPPLIER_CODE, outparam.get(RecStartSCHParams.SUPPLIER_CODE));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.SUPPLIER_NAME, outparam.get(RecStartSCHParams.SUPPLIER_NAME));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.COLLECT_JOB_NO, outparam.get(RecStartSCHParams.COLLECT_JOB_NO));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.ITEM_CODE, outparam.get(RecStartSCHParams.ITEM_CODE));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.ITEM_NAME, outparam.get(RecStartSCHParams.ITEM_NAME));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.PLAN_QTY, outparam.get(RecStartSCHParams.PLAN_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.PLAN_CASE_QTY, outparam.get(RecStartSCHParams.PLAN_CASE_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.PLAN_PIECE_QTY, outparam.get(RecStartSCHParams.PLAN_PIECE_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.RESULT_QTY, outparam.get(RecStartSCHParams.RESULT_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.RESULT_CASE_QTY, outparam.get(RecStartSCHParams.RESULT_CASE_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.RESULT_PIECE_QTY, outparam.get(RecStartSCHParams.RESULT_PIECE_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.JAN, outparam.get(RecStartSCHParams.JAN));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.ITF, outparam.get(RecStartSCHParams.ITF));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.BUNDLE_ITF, outparam.get(RecStartSCHParams.BUNDLE_ITF));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.ENTERING_QTY, outparam.get(RecStartSCHParams.ENTERING_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.BUNDLE_ENTERING_QTY, outparam.get(RecStartSCHParams.BUNDLE_ENTERING_QTY));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.TICKET_NO, outparam.get(RecStartSCHParams.TICKET_NO));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.TICKET_LINE_NO, outparam.get(RecStartSCHParams.TICKET_LINE_NO));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.RESULT_LOT_NO, outparam.get(RecStartSCHParams.PLAN_LOT_NO));
                receivingOutDataSheet.setValue(ReceivingOutDataSheet.PLAN_LOT_NO, outparam.get(RecStartSCHParams.PLAN_LOT_NO));
                commonDataSheet.setValue(CommonDataSheet.START_DATE_TIME, outparam.get(RecStartSCHParams.START_DATE_TIME));
                consignorDataSheet.setValue(ConsignorDataSheet.CONSIGNOR_NAME, outparam.get(RecStartSCHParams.CONSIGNOR_NAME));
                commonDataSheet.setValue(CommonDataSheet.CASE_INPUT_ENABLED, outparam.get(RecStartSCHParams.CASE_INPUT_ENABLED));
                break;
            }

            receivingOutDataSheet.setValue(ReceivingOutDataSheet.DISP_PLAN_DATE, receivingOutDataSheet.getValue(ReceivingOutDataSheet.PLAN_DATE));
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

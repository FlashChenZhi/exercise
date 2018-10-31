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
import jp.co.daifuku.busitune.rft.haisurf.DSNumberField;
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
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.NoPlanRetrievalInDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.NoPlanRetrievalOutDataSheet;
import jp.co.daifuku.wms.stock.rft.schedule.NrtCompleteSCH;
import jp.co.daifuku.wms.stock.rft.schedule.NrtCompleteSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class NrtRetrievalBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "NrtRetrieval";

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
    public NrtRetrievalBusiness()
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
    public HSResult input_Click(DataSheetFactory dsf, Locale locale)
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
    public HSResult caseQty_Enter(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        NoPlanRetrievalInDataSheet noPlanRetrievalInDataSheet = new NoPlanRetrievalInDataSheet(dsf, locale);
        NoPlanRetrievalOutDataSheet noPlanRetrievalOutDataSheet = new NoPlanRetrievalOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(noPlanRetrievalInDataSheet.getText(NoPlanRetrievalInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)NoPlanRetrievalInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(noPlanRetrievalInDataSheet.getText(NoPlanRetrievalInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)NoPlanRetrievalInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.RESULT_CASE_QTY, noPlanRetrievalInDataSheet.getValue(NoPlanRetrievalInDataSheet.RESULT_CASE_QTY));

        // set focus.
        setFocus("PieceQty");

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
    public HSResult pieceQty_Enter(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        NoPlanRetrievalInDataSheet noPlanRetrievalInDataSheet = new NoPlanRetrievalInDataSheet(dsf, locale);
        NoPlanRetrievalOutDataSheet noPlanRetrievalOutDataSheet = new NoPlanRetrievalOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(noPlanRetrievalInDataSheet.getText(NoPlanRetrievalInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)NoPlanRetrievalInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(noPlanRetrievalInDataSheet.getText(NoPlanRetrievalInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)NoPlanRetrievalInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.RESULT_PIECE_QTY, noPlanRetrievalInDataSheet.getValue(NoPlanRetrievalInDataSheet.RESULT_PIECE_QTY));

        // set focus.
        setFocus("LotNo");

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
    public HSResult return_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        NoPlanRetrievalInDataSheet noPlanRetrievalInDataSheet = new NoPlanRetrievalInDataSheet(dsf, locale);
        NoPlanRetrievalOutDataSheet noPlanRetrievalOutDataSheet = new NoPlanRetrievalOutDataSheet(dsf, locale);

        // set output parameters.
        noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.RESULT_CASE_QTY, noPlanRetrievalInDataSheet.getValue(NoPlanRetrievalInDataSheet.RESULT_CASE_QTY));
        noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.RESULT_PIECE_QTY, noPlanRetrievalInDataSheet.getValue(NoPlanRetrievalInDataSheet.RESULT_PIECE_QTY));

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
    public HSResult itemCode_Enter(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        NoPlanRetrievalInDataSheet noPlanRetrievalInDataSheet = new NoPlanRetrievalInDataSheet(dsf, locale);
        NoPlanRetrievalOutDataSheet noPlanRetrievalOutDataSheet = new NoPlanRetrievalOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(noPlanRetrievalInDataSheet.getText(NoPlanRetrievalInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)NoPlanRetrievalInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(noPlanRetrievalInDataSheet.getText(NoPlanRetrievalInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)NoPlanRetrievalInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.RESULT_LOT_NO, noPlanRetrievalInDataSheet.getValue(NoPlanRetrievalInDataSheet.RESULT_LOT_NO));

        // set focus.
        setFocus("ItemCode");

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
        NoPlanRetrievalInDataSheet noPlanRetrievalInDataSheet = new NoPlanRetrievalInDataSheet(dsf, locale);
        NoPlanRetrievalOutDataSheet noPlanRetrievalOutDataSheet = new NoPlanRetrievalOutDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(noPlanRetrievalInDataSheet.getText(NoPlanRetrievalInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)NoPlanRetrievalInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(noPlanRetrievalInDataSheet.getText(NoPlanRetrievalInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)NoPlanRetrievalInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        Connection conn = null;
        NrtCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new NrtCompleteSCH(conn, this.getClass(), locale);

            // set input parameters.
            NrtCompleteSCHParams inparam = new NrtCompleteSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(NrtCompleteSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));
            inparam.set(NrtCompleteSCHParams.USER_ID, commonDataSheet.getValue(CommonDataSheet.USER_ID));
            inparam.set(NrtCompleteSCHParams.CONSIGNOR_CODE, noPlanRetrievalOutDataSheet.getValue(NoPlanRetrievalOutDataSheet.CONSIGNOR_CODE));
            inparam.set(NrtCompleteSCHParams.AREA_NO, noPlanRetrievalOutDataSheet.getValue(NoPlanRetrievalOutDataSheet.AREA_NO));
            inparam.set(NrtCompleteSCHParams.LOCATION_NO, noPlanRetrievalOutDataSheet.getValue(NoPlanRetrievalOutDataSheet.LOCATION_NO));
            inparam.set(NrtCompleteSCHParams.ITEM_CODE, noPlanRetrievalOutDataSheet.getValue(NoPlanRetrievalOutDataSheet.ITEM_CODE));
            inparam.set(NrtCompleteSCHParams.LOT_NO, noPlanRetrievalOutDataSheet.getValue(NoPlanRetrievalOutDataSheet.LOT_NO));
            inparam.set(NrtCompleteSCHParams.RESULT_LOT_NO, noPlanRetrievalOutDataSheet.getValue(NoPlanRetrievalOutDataSheet.RESULT_LOT_NO));
            inparam.set(NrtCompleteSCHParams.RESULT_QTY, noPlanRetrievalOutDataSheet.getValue(NoPlanRetrievalOutDataSheet.RESULT_QTY));
            inparam.set(NrtCompleteSCHParams.STOCK_QTY, noPlanRetrievalOutDataSheet.getValue(NoPlanRetrievalOutDataSheet.STOCK_QTY));
            inparam.set(NrtCompleteSCHParams.START_DATE_TIME, commonDataSheet.getValue(CommonDataSheet.START_DATE_TIME));
            inparam.set(NrtCompleteSCHParams.MISS_SCAN_COUNT, commonDataSheet.getValue(CommonDataSheet.MISS_SCAN_COUNT));

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
    public HSResult cp_Click(DataSheetFactory dsf, Locale locale)
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
    public HSResult all_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        NoPlanRetrievalOutDataSheet noPlanRetrievalOutDataSheet = new NoPlanRetrievalOutDataSheet(dsf, locale);

        // set output parameters.
        noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.RESULT_QTY, noPlanRetrievalOutDataSheet.getValue(NoPlanRetrievalOutDataSheet.STOCK_QTY));
        noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.RESULT_CASE_QTY, noPlanRetrievalOutDataSheet.getValue(NoPlanRetrievalOutDataSheet.STOCK_CASE_QTY));
        noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.RESULT_PIECE_QTY, noPlanRetrievalOutDataSheet.getValue(NoPlanRetrievalOutDataSheet.STOCK_PIECE_QTY));

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

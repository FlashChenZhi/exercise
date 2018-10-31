// $Id$
package jp.co.daifuku.wms.stock.rft.display;

/*
 * Copyright(c) 2000-2011 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import jp.co.daifuku.busitune.rft.haisurf.AbstractHSBusiness;
import jp.co.daifuku.busitune.rft.haisurf.DSNumberField;
import jp.co.daifuku.busitune.rft.haisurf.DataSheetModel;
import jp.co.daifuku.busitune.rft.haisurf.HSResult;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.rft.HaiSurfExceptionHandler;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.RftConst;
import jp.co.daifuku.wms.stock.rft.datasheet.NoPlanRetrievalInDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.NoPlanRetrievalOutDataSheet;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class NrtQtyInputBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "NrtQtyInput";

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
    public NrtQtyInputBusiness()
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
        // initialize DataSheets.
        NoPlanRetrievalOutDataSheet noPlanRetrievalOutDataSheet = new NoPlanRetrievalOutDataSheet(dsf, locale);

        // set output parameters.
        noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.RESULT_LOT_NO, noPlanRetrievalOutDataSheet.getValue(NoPlanRetrievalOutDataSheet.TEMP_RESULT_LOT_NO));

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
    public HSResult lotNo_Enter(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        NoPlanRetrievalInDataSheet noPlanRetrievalInDataSheet = new NoPlanRetrievalInDataSheet(dsf, locale);
        NoPlanRetrievalOutDataSheet noPlanRetrievalOutDataSheet = new NoPlanRetrievalOutDataSheet(dsf, locale);

        // set output parameters.
        noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.RESULT_LOT_NO, noPlanRetrievalInDataSheet.getValue(NoPlanRetrievalInDataSheet.RESULT_LOT_NO));

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
        noPlanRetrievalOutDataSheet.setValue(NoPlanRetrievalOutDataSheet.RESULT_LOT_NO, noPlanRetrievalInDataSheet.getValue(NoPlanRetrievalInDataSheet.RESULT_LOT_NO));

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

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
import jp.co.daifuku.wms.stock.rft.datasheet.NoPlanStorageInDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.NoPlanStorageOutDataSheet;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class NstQtyInputBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "NstQtyInput";

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
    public NstQtyInputBusiness()
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
    public HSResult return_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        NoPlanStorageInDataSheet noPlanStorageInDataSheet = new NoPlanStorageInDataSheet(dsf, locale);
        NoPlanStorageOutDataSheet noPlanStorageOutDataSheet = new NoPlanStorageOutDataSheet(dsf, locale);

        // set output parameters.
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_CASE_QTY, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_CASE_QTY));
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_PIECE_QTY, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_PIECE_QTY));
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_LOT_NO, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_LOT_NO));

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
        NoPlanStorageInDataSheet noPlanStorageInDataSheet = new NoPlanStorageInDataSheet(dsf, locale);
        NoPlanStorageOutDataSheet noPlanStorageOutDataSheet = new NoPlanStorageOutDataSheet(dsf, locale);

        // set output parameters.
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_LOT_NO, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.TEMP_RESULT_LOT_NO));
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_CASE_QTY, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_CASE_QTY));
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_PIECE_QTY, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_PIECE_QTY));
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_LOT_NO, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_LOT_NO));

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
        // initialize DataSheets.
        NoPlanStorageInDataSheet noPlanStorageInDataSheet = new NoPlanStorageInDataSheet(dsf, locale);
        NoPlanStorageOutDataSheet noPlanStorageOutDataSheet = new NoPlanStorageOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(noPlanStorageInDataSheet.getText(NoPlanStorageInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)NoPlanStorageInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(noPlanStorageInDataSheet.getText(NoPlanStorageInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)NoPlanStorageInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_CASE_QTY, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_CASE_QTY));
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_PIECE_QTY, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_PIECE_QTY));
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_LOT_NO, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_LOT_NO));

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
        NoPlanStorageInDataSheet noPlanStorageInDataSheet = new NoPlanStorageInDataSheet(dsf, locale);
        NoPlanStorageOutDataSheet noPlanStorageOutDataSheet = new NoPlanStorageOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(noPlanStorageInDataSheet.getText(NoPlanStorageInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)NoPlanStorageInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(noPlanStorageInDataSheet.getText(NoPlanStorageInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)NoPlanStorageInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_CASE_QTY, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_CASE_QTY));

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
        NoPlanStorageInDataSheet noPlanStorageInDataSheet = new NoPlanStorageInDataSheet(dsf, locale);
        NoPlanStorageOutDataSheet noPlanStorageOutDataSheet = new NoPlanStorageOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(noPlanStorageInDataSheet.getText(NoPlanStorageInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)NoPlanStorageInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(noPlanStorageInDataSheet.getText(NoPlanStorageInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)NoPlanStorageInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_PIECE_QTY, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_PIECE_QTY));

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
        NoPlanStorageInDataSheet noPlanStorageInDataSheet = new NoPlanStorageInDataSheet(dsf, locale);
        NoPlanStorageOutDataSheet noPlanStorageOutDataSheet = new NoPlanStorageOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(noPlanStorageInDataSheet.getText(NoPlanStorageInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)NoPlanStorageInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(noPlanStorageInDataSheet.getText(NoPlanStorageInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)NoPlanStorageInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_LOT_NO, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_LOT_NO));

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
        NoPlanStorageInDataSheet noPlanStorageInDataSheet = new NoPlanStorageInDataSheet(dsf, locale);
        NoPlanStorageOutDataSheet noPlanStorageOutDataSheet = new NoPlanStorageOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(noPlanStorageInDataSheet.getText(NoPlanStorageInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)NoPlanStorageInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(noPlanStorageInDataSheet.getText(NoPlanStorageInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)NoPlanStorageInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_LOT_NO, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_LOT_NO));

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
        NoPlanStorageInDataSheet noPlanStorageInDataSheet = new NoPlanStorageInDataSheet(dsf, locale);
        NoPlanStorageOutDataSheet noPlanStorageOutDataSheet = new NoPlanStorageOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(noPlanStorageInDataSheet.getText(NoPlanStorageInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)NoPlanStorageInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(noPlanStorageInDataSheet.getText(NoPlanStorageInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)NoPlanStorageInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_CASE_QTY, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_CASE_QTY));
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_PIECE_QTY, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_PIECE_QTY));
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.RESULT_LOT_NO, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.RESULT_LOT_NO));

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

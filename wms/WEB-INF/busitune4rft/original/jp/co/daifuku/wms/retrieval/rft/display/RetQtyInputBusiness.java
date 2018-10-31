// $Id$
package jp.co.daifuku.wms.retrieval.rft.display;

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
import jp.co.daifuku.wms.retrieval.rft.datasheet.RetrievalInDataSheet;
import jp.co.daifuku.wms.retrieval.rft.datasheet.RetrievalOutDataSheet;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class RetQtyInputBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "RetQtyInput";

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
    public RetQtyInputBusiness()
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
    public HSResult caseQty_Enter(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        RetrievalInDataSheet retrievalInDataSheet = new RetrievalInDataSheet(dsf, locale);
        RetrievalOutDataSheet retrievalOutDataSheet = new RetrievalOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(retrievalInDataSheet.getText(RetrievalInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)RetrievalInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(retrievalInDataSheet.getText(RetrievalInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)RetrievalInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_CASE_QTY, retrievalInDataSheet.getValue(RetrievalInDataSheet.RESULT_CASE_QTY));

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
        RetrievalInDataSheet retrievalInDataSheet = new RetrievalInDataSheet(dsf, locale);
        RetrievalOutDataSheet retrievalOutDataSheet = new RetrievalOutDataSheet(dsf, locale);

        // input validation.
        if (StringUtil.isBlank(retrievalInDataSheet.getText(RetrievalInDataSheet.RESULT_CASE_QTY))
            || !StringUtil.isNumberFormat(retrievalInDataSheet.getText(RetrievalInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)RetrievalInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(retrievalInDataSheet.getText(RetrievalInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)RetrievalInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_PIECE_QTY, retrievalInDataSheet.getValue(RetrievalInDataSheet.RESULT_PIECE_QTY));

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
        RetrievalInDataSheet retrievalInDataSheet = new RetrievalInDataSheet(dsf, locale);
        RetrievalOutDataSheet retrievalOutDataSheet = new RetrievalOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(retrievalInDataSheet.getText(RetrievalInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)RetrievalInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(retrievalInDataSheet.getText(RetrievalInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)RetrievalInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_LOT_NO, retrievalInDataSheet.getValue(RetrievalInDataSheet.RESULT_LOT_NO));

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
        RetrievalOutDataSheet retrievalOutDataSheet = new RetrievalOutDataSheet(dsf, locale);

        // set output parameters.
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_LOT_NO, retrievalOutDataSheet.getValue(RetrievalOutDataSheet.TEMP_RESULT_LOT_NO));

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
        RetrievalInDataSheet retrievalInDataSheet = new RetrievalInDataSheet(dsf, locale);
        RetrievalOutDataSheet retrievalOutDataSheet = new RetrievalOutDataSheet(dsf, locale);

        // set output parameters.
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_CASE_QTY, retrievalInDataSheet.getValue(RetrievalInDataSheet.RESULT_CASE_QTY));
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_PIECE_QTY, retrievalInDataSheet.getValue(RetrievalInDataSheet.RESULT_PIECE_QTY));
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_LOT_NO, retrievalInDataSheet.getValue(RetrievalInDataSheet.RESULT_LOT_NO));

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
        RetrievalOutDataSheet retrievalOutDataSheet = new RetrievalOutDataSheet(dsf, locale);

        // set output parameters.
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_CASE_QTY, retrievalOutDataSheet.getValue(RetrievalOutDataSheet.PLAN_CASE_QTY));
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_PIECE_QTY, retrievalOutDataSheet.getValue(RetrievalOutDataSheet.PLAN_PIECE_QTY));

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
        RetrievalInDataSheet retrievalInDataSheet = new RetrievalInDataSheet(dsf, locale);
        RetrievalOutDataSheet retrievalOutDataSheet = new RetrievalOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(retrievalInDataSheet.getText(RetrievalInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)RetrievalInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(retrievalInDataSheet.getText(RetrievalInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)RetrievalInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_CASE_QTY, retrievalInDataSheet.getValue(RetrievalInDataSheet.RESULT_CASE_QTY));
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_PIECE_QTY, retrievalInDataSheet.getValue(RetrievalInDataSheet.RESULT_PIECE_QTY));
        retrievalOutDataSheet.setValue(RetrievalOutDataSheet.RESULT_LOT_NO, retrievalInDataSheet.getValue(RetrievalInDataSheet.RESULT_LOT_NO));

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
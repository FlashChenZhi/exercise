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
import jp.co.daifuku.wms.stock.rft.datasheet.ReceivingStorageInDataSheet;
import jp.co.daifuku.wms.stock.rft.datasheet.ReceivingStorageOutDataSheet;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class StdQtyInputBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "StdQtyInput";

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
    public StdQtyInputBusiness()
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
        ReceivingStorageInDataSheet receivingStorageInDataSheet = new ReceivingStorageInDataSheet(dsf, locale);
        ReceivingStorageOutDataSheet receivingStorageOutDataSheet = new ReceivingStorageOutDataSheet(dsf, locale);

        // set output parameters.
        receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_CASE_QTY, receivingStorageInDataSheet.getValue(ReceivingStorageInDataSheet.RESULT_CASE_QTY));
        receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_PIECE_QTY, receivingStorageInDataSheet.getValue(ReceivingStorageInDataSheet.RESULT_PIECE_QTY));

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
    public HSResult caseQty_Enter(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        ReceivingStorageInDataSheet receivingStorageInDataSheet = new ReceivingStorageInDataSheet(dsf, locale);
        ReceivingStorageOutDataSheet receivingStorageOutDataSheet = new ReceivingStorageOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(receivingStorageInDataSheet.getText(ReceivingStorageInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)ReceivingStorageInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(receivingStorageInDataSheet.getText(ReceivingStorageInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)ReceivingStorageInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_CASE_QTY, receivingStorageInDataSheet.getValue(ReceivingStorageInDataSheet.RESULT_CASE_QTY));

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
        ReceivingStorageInDataSheet receivingStorageInDataSheet = new ReceivingStorageInDataSheet(dsf, locale);
        ReceivingStorageOutDataSheet receivingStorageOutDataSheet = new ReceivingStorageOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(receivingStorageInDataSheet.getText(ReceivingStorageInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)ReceivingStorageInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(receivingStorageInDataSheet.getText(ReceivingStorageInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)ReceivingStorageInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_PIECE_QTY, receivingStorageInDataSheet.getValue(ReceivingStorageInDataSheet.RESULT_PIECE_QTY));

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
        ReceivingStorageOutDataSheet receivingStorageOutDataSheet = new ReceivingStorageOutDataSheet(dsf, locale);

        // set output parameters.
        receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_CASE_QTY, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.STOCK_CASE_QTY));
        receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_PIECE_QTY, receivingStorageOutDataSheet.getValue(ReceivingStorageOutDataSheet.STOCK_PIECE_QTY));

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
        ReceivingStorageInDataSheet receivingStorageInDataSheet = new ReceivingStorageInDataSheet(dsf, locale);
        ReceivingStorageOutDataSheet receivingStorageOutDataSheet = new ReceivingStorageOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(receivingStorageInDataSheet.getText(ReceivingStorageInDataSheet.RESULT_CASE_QTY)
                , ((DSNumberField)ReceivingStorageInDataSheet.RESULT_CASE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("CaseQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }
        if (!StringUtil.isNumberFormat(receivingStorageInDataSheet.getText(ReceivingStorageInDataSheet.RESULT_PIECE_QTY)
                , ((DSNumberField)ReceivingStorageInDataSheet.RESULT_PIECE_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("PieceQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_CASE_QTY, receivingStorageInDataSheet.getValue(ReceivingStorageInDataSheet.RESULT_CASE_QTY));
        receivingStorageOutDataSheet.setValue(ReceivingStorageOutDataSheet.RESULT_PIECE_QTY, receivingStorageInDataSheet.getValue(ReceivingStorageInDataSheet.RESULT_PIECE_QTY));

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

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
public class NstEntQtyInputBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "NstEntQtyInput";

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
    public NstEntQtyInputBusiness()
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
    public HSResult enteringQty_Enter(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        NoPlanStorageInDataSheet noPlanStorageInDataSheet = new NoPlanStorageInDataSheet(dsf, locale);
        NoPlanStorageOutDataSheet noPlanStorageOutDataSheet = new NoPlanStorageOutDataSheet(dsf, locale);

        // input validation.
        if (!StringUtil.isNumberFormat(noPlanStorageInDataSheet.getText(NoPlanStorageInDataSheet.ENTERING_QTY)
                , ((DSNumberField)NoPlanStorageInDataSheet.ENTERING_QTY.getField()).getScale()))
        {
            setBeep(RftConst.BEEP);
            setFocus("EnteringQty");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.ENTERING_QTY, noPlanStorageInDataSheet.getValue(NoPlanStorageInDataSheet.ENTERING_QTY));

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
        NoPlanStorageOutDataSheet noPlanStorageOutDataSheet = new NoPlanStorageOutDataSheet(dsf, locale);

        // set output parameters.
        noPlanStorageOutDataSheet.setValue(NoPlanStorageOutDataSheet.ENTERING_QTY, noPlanStorageOutDataSheet.getValue(NoPlanStorageOutDataSheet.TEMP_ENTERING_QTY));

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

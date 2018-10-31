// $Id$
package jp.co.daifuku.wms.stock.rft.datasheet;

/*
 * Copyright(c) 2000-2011 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import jp.co.daifuku.busitune.rft.haisurf.BasicDSModel;
import jp.co.daifuku.busitune.rft.haisurf.DSBooleanField;
import jp.co.daifuku.busitune.rft.haisurf.DSKey;
import jp.co.daifuku.busitune.rft.haisurf.DSNumberField;
import jp.co.daifuku.busitune.rft.haisurf.DSStringField;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class NoPlanStorageOutDataSheet
        extends BasicDSModel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DATASHEET_NAME */
    private static final String DATASHEET_NAME = "NoPlanStorageOutDataSheet";

    /** BACK_LABEL */
    public static final DSKey BACK_LABEL = new DSKey("BACK_LABEL", new DSStringField(), false, 0);

    /** BUNDLE_ENTERING_QTY */
    public static final DSKey BUNDLE_ENTERING_QTY = new DSKey("BUNDLE_ENTERING_QTY", new DSNumberField(0), false);

    /** BUNDLE_ITF */
    public static final DSKey BUNDLE_ITF = new DSKey("BUNDLE_ITF", new DSStringField(), false, 0);

    /** CONSIGNOR_CODE */
    public static final DSKey CONSIGNOR_CODE = new DSKey("CONSIGNOR_CODE", new DSStringField(), false, 0);

    /** CONSIGNOR_NAME */
    public static final DSKey CONSIGNOR_NAME = new DSKey("CONSIGNOR_NAME", new DSStringField(), false, 0);

    /** ENTERING_QTY */
    public static final DSKey ENTERING_QTY = new DSKey("ENTERING_QTY", new DSNumberField(0), false);

    /** ENTERING_QTY_INPUT_ENABLED */
    public static final DSKey ENTERING_QTY_INPUT_ENABLED = new DSKey("ENTERING_QTY_INPUT_ENABLED", new DSBooleanField(), false);

    /** ITEM_CODE */
    public static final DSKey ITEM_CODE = new DSKey("ITEM_CODE", new DSStringField(), false, 0);

    /** ITEM_NAME */
    public static final DSKey ITEM_NAME = new DSKey("ITEM_NAME", new DSStringField(), false, 2);

    /** ITF */
    public static final DSKey ITF = new DSKey("ITF", new DSStringField(), false, 0);

    /** JAN */
    public static final DSKey JAN = new DSKey("JAN", new DSStringField(), false, 0);

    /** QTYINPUT_TITLE */
    public static final DSKey QTYINPUT_TITLE = new DSKey("QTYINPUT_TITLE", new DSStringField(), false, 0);

    /** RESULT_AREA_LOCATION */
    public static final DSKey RESULT_AREA_LOCATION = new DSKey("RESULT_AREA_LOCATION", new DSStringField(), false, 0);

    /** RESULT_CASE_QTY */
    public static final DSKey RESULT_CASE_QTY = new DSKey("RESULT_CASE_QTY", new DSNumberField(0), false);

    /** RESULT_LOT_NO */
    public static final DSKey RESULT_LOT_NO = new DSKey("RESULT_LOT_NO", new DSStringField(), false, 0);

    /** RESULT_PIECE_QTY */
    public static final DSKey RESULT_PIECE_QTY = new DSKey("RESULT_PIECE_QTY", new DSNumberField(0), false);

    /** RESULT_QTY */
    public static final DSKey RESULT_QTY = new DSKey("RESULT_QTY", new DSNumberField(0), false);

    /** TEMP_ENTERING_QTY */
    public static final DSKey TEMP_ENTERING_QTY = new DSKey("TEMP_ENTERING_QTY", new DSNumberField(0), false);

    /** TEMP_RESULT_LOT_NO */
    public static final DSKey TEMP_RESULT_LOT_NO = new DSKey("TEMP_RESULT_LOT_NO", new DSStringField(), false, 0);

    /** TEMP_RESULT_QTY */
    public static final DSKey TEMP_RESULT_QTY = new DSKey("TEMP_RESULT_QTY", new DSNumberField(0), false);

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
     *
     * @param dsf DataSheet factory
     * @param locale Locale
     */
    public NoPlanStorageOutDataSheet(DataSheetFactory dsf, Locale locale)
    {
        super(DATASHEET_NAME, dsf, locale);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

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
     * @return version
     */
    public static String getVersion()
    {
        return "$Id$";
    }
}
//end of class

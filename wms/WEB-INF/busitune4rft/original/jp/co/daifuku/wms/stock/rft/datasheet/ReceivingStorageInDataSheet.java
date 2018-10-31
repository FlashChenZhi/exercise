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
public class ReceivingStorageInDataSheet
        extends BasicDSModel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DATASHEET_NAME */
    private static final String DATASHEET_NAME = "ReceivingStorageInDataSheet";

    /** AREA_NO */
    public static final DSKey AREA_NO = new DSKey("AREA_NO", new DSStringField(), false, 0);

    /** CONSIGNOR_CODE */
    public static final DSKey CONSIGNOR_CODE = new DSKey("CONSIGNOR_CODE", new DSStringField(), false, 0);

    /** FORCE_STRAGE_FLAG */
    public static final DSKey FORCE_STRAGE_FLAG = new DSKey("FORCE_STRAGE_FLAG", new DSBooleanField(), false);

    /** IS_CONFIRM */
    public static final DSKey IS_CONFIRM = new DSKey("IS_CONFIRM", new DSBooleanField(), false);

    /** ITEM_CODE */
    public static final DSKey ITEM_CODE = new DSKey("ITEM_CODE", new DSStringField(), false, 0);

    /** LOCATION_NO */
    public static final DSKey LOCATION_NO = new DSKey("LOCATION_NO", new DSStringField(), false, 0);

    /** RESULT_AREA_LOCATION */
    public static final DSKey RESULT_AREA_LOCATION = new DSKey("RESULT_AREA_LOCATION", new DSStringField(), false, 0);

    /** RESULT_CASE_QTY */
    public static final DSKey RESULT_CASE_QTY = new DSKey("RESULT_CASE_QTY", new DSNumberField(0), false);

    /** RESULT_PIECE_QTY */
    public static final DSKey RESULT_PIECE_QTY = new DSKey("RESULT_PIECE_QTY", new DSNumberField(0), false);

    /** SCAN_CODE */
    public static final DSKey SCAN_CODE = new DSKey("SCAN_CODE", new DSStringField(), false, 0);

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
    public ReceivingStorageInDataSheet(DataSheetFactory dsf, Locale locale)
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

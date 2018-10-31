// $Id: ReceivingStorageOutDataSheet.java 8041 2012-05-17 09:39:40Z nagao $
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
 * @version $Revision: 8041 $, $Date: 2012-05-17 18:39:40 +0900 (木, 17 5 2012) $
 * @author  HighTune.
 * @author  Last commit: $Author: nagao $
 */
public class ReceivingStorageOutDataSheet
        extends BasicDSModel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DATASHEET_NAME */
    private static final String DATASHEET_NAME = "ReceivingStorageOutDataSheet";

    /** AREA_NO */
    public static final DSKey AREA_NO = new DSKey("AREA_NO", new DSStringField(), false, 0);

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

    /** IS_STORAGE_START */
    public static final DSKey IS_STORAGE_START = new DSKey("IS_STORAGE_START", new DSBooleanField(), false);

    /** ITEM_CODE */
    public static final DSKey ITEM_CODE = new DSKey("ITEM_CODE", new DSStringField(), false, 0);

    /** ITEM_NAME */
    public static final DSKey ITEM_NAME = new DSKey("ITEM_NAME", new DSStringField(), false, 2);

    /** ITF */
    public static final DSKey ITF = new DSKey("ITF", new DSStringField(), false, 0);

    /** JAN */
    public static final DSKey JAN = new DSKey("JAN", new DSStringField(), false, 0);

    /** LOCATION_NO */
    public static final DSKey LOCATION_NO = new DSKey("LOCATION_NO", new DSStringField(), false, 0);

    /** LOCATION_STYLE */
    public static final DSKey LOCATION_STYLE = new DSKey("LOCATION_STYLE", new DSStringField(), false, 0);

    /** LOT_NO */
    public static final DSKey LOT_NO = new DSKey("LOT_NO", new DSStringField(), false, 0);

    /** MOVE_AREA_NO */
    public static final DSKey MOVE_AREA_NO = new DSKey("MOVE_AREA_NO", new DSStringField(), false, 0);

    /** MOVE_JOB_NO */
    public static final DSKey MOVE_JOB_NO = new DSKey("MOVE_JOB_NO", new DSStringField(), false, 0);

    /** MOVE_LOCATION_NO */
    public static final DSKey MOVE_LOCATION_NO = new DSKey("MOVE_LOCATION_NO", new DSStringField(), false, 0);

    /** PLAN_AREA_LOCATION */
    public static final DSKey PLAN_AREA_LOCATION = new DSKey("PLAN_AREA_LOCATION", new DSStringField(), false, 0);

    /** RESULT_AREA_LOCATION */
    public static final DSKey RESULT_AREA_LOCATION = new DSKey("RESULT_AREA_LOCATION", new DSStringField(), false, 0);

    /** RESULT_CASE_QTY */
    public static final DSKey RESULT_CASE_QTY = new DSKey("RESULT_CASE_QTY", new DSNumberField(0), false);

    /** RESULT_PIECE_QTY */
    public static final DSKey RESULT_PIECE_QTY = new DSKey("RESULT_PIECE_QTY", new DSNumberField(0), false);

    /** RESULT_QTY */
    public static final DSKey RESULT_QTY = new DSKey("RESULT_QTY", new DSNumberField(0), false);

    /** SETTING_UNIT_KEY */
    public static final DSKey SETTING_UNIT_KEY = new DSKey("SETTING_UNIT_KEY", new DSStringField(), false, 0);

    /** STOCK_CASE_QTY */
    public static final DSKey STOCK_CASE_QTY = new DSKey("STOCK_CASE_QTY", new DSNumberField(0), false);

    /** STOCK_PACK_ENABLED */
    public static final DSKey STOCK_PACK_ENABLED = new DSKey("STOCK_PACK_ENABLED", new DSBooleanField(), false);

    /** STOCK_PIECE_QTY */
    public static final DSKey STOCK_PIECE_QTY = new DSKey("STOCK_PIECE_QTY", new DSNumberField(0), false);

    /** STOCK_QTY */
    public static final DSKey STOCK_QTY = new DSKey("STOCK_QTY", new DSNumberField(0), false);

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
    public ReceivingStorageOutDataSheet(DataSheetFactory dsf, Locale locale)
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
        return "$Id: ReceivingStorageOutDataSheet.java 8041 2012-05-17 09:39:40Z nagao $";
    }
}
//end of class

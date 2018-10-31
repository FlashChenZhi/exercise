// $Id$
package jp.co.daifuku.wms.ship.rft.datasheet;

/*
 * Copyright(c) 2000-2011 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import jp.co.daifuku.busitune.rft.haisurf.BasicDSModel;
import jp.co.daifuku.busitune.rft.haisurf.DSBooleanField;
import jp.co.daifuku.busitune.rft.haisurf.DSDateField;
import jp.co.daifuku.busitune.rft.haisurf.DSKey;
import jp.co.daifuku.busitune.rft.haisurf.DSNumberField;
import jp.co.daifuku.busitune.rft.haisurf.DSStringField;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class ShippingCustomerOutDataSheet
        extends BasicDSModel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DATASHEET_NAME */
    private static final String DATASHEET_NAME = "ShippingCustomerOutDataSheet";

    /** BATCH_NO */
    public static final DSKey BATCH_NO = new DSKey("BATCH_NO", new DSStringField(), false, 0);

    /** BUNDLE_ENTERING_QTY */
    public static final DSKey BUNDLE_ENTERING_QTY = new DSKey("BUNDLE_ENTERING_QTY", new DSNumberField(0), false);

    /** BUNDLE_ITF */
    public static final DSKey BUNDLE_ITF = new DSKey("BUNDLE_ITF", new DSStringField(), false, 0);

    /** COLLECT_JOB_NO */
    public static final DSKey COLLECT_JOB_NO = new DSKey("COLLECT_JOB_NO", new DSStringField(), false, 0);

    /** CONSIGNOR_CODE */
    public static final DSKey CONSIGNOR_CODE = new DSKey("CONSIGNOR_CODE", new DSStringField(), false, 0);

    /** CONSIGNOR_NAME */
    public static final DSKey CONSIGNOR_NAME = new DSKey("CONSIGNOR_NAME", new DSStringField(), false, 0);

    /** CUSTOMER_CODE */
    public static final DSKey CUSTOMER_CODE = new DSKey("CUSTOMER_CODE", new DSStringField(), false, 0);

    /** CUSTOMER_NAME */
    public static final DSKey CUSTOMER_NAME = new DSKey("CUSTOMER_NAME", new DSStringField(), false, 2);

    /** ENTERING_QTY */
    public static final DSKey ENTERING_QTY = new DSKey("ENTERING_QTY", new DSNumberField(0), false);

    /** GUIDANCE_MODE */
    public static final DSKey GUIDANCE_MODE = new DSKey("GUIDANCE_MODE", new DSStringField(), false, 0);

    /** ITEM_CODE */
    public static final DSKey ITEM_CODE = new DSKey("ITEM_CODE", new DSStringField(), false, 0);

    /** ITEM_NAME */
    public static final DSKey ITEM_NAME = new DSKey("ITEM_NAME", new DSStringField(), false, 2);

    /** ITF */
    public static final DSKey ITF = new DSKey("ITF", new DSStringField(), false, 0);

    /** JAN */
    public static final DSKey JAN = new DSKey("JAN", new DSStringField(), false, 0);

    /** PENDING_ITEM */
    public static final DSKey PENDING_ITEM = new DSKey("PENDING_ITEM", new DSNumberField(0), false);

    /** PLAN_CASE_QTY */
    public static final DSKey PLAN_CASE_QTY = new DSKey("PLAN_CASE_QTY", new DSNumberField(0), false);

    /** PLAN_DATE */
    public static final DSKey PLAN_DATE = new DSKey("PLAN_DATE", new DSDateField(DATE_FORMAT.LONG, null), false);

    /** PLAN_LOT_NO */
    public static final DSKey PLAN_LOT_NO = new DSKey("PLAN_LOT_NO", new DSStringField(), false, 0);

    /** PLAN_PIECE_QTY */
    public static final DSKey PLAN_PIECE_QTY = new DSKey("PLAN_PIECE_QTY", new DSNumberField(0), false);

    /** PLAN_QTY */
    public static final DSKey PLAN_QTY = new DSKey("PLAN_QTY", new DSNumberField(0), false);

    /** QTY_INPUT_ENABLED */
    public static final DSKey QTY_INPUT_ENABLED = new DSKey("QTY_INPUT_ENABLED", new DSBooleanField(), false);

    /** RESULT_CASE_QTY */
    public static final DSKey RESULT_CASE_QTY = new DSKey("RESULT_CASE_QTY", new DSNumberField(0), false);

    /** RESULT_LOT_NO */
    public static final DSKey RESULT_LOT_NO = new DSKey("RESULT_LOT_NO", new DSStringField(), false, 0);

    /** RESULT_PIECE_QTY */
    public static final DSKey RESULT_PIECE_QTY = new DSKey("RESULT_PIECE_QTY", new DSNumberField(0), false);

    /** RESULT_QTY */
    public static final DSKey RESULT_QTY = new DSKey("RESULT_QTY", new DSNumberField(0), false);

    /** SETTING_UNIT_KEY */
    public static final DSKey SETTING_UNIT_KEY = new DSKey("SETTING_UNIT_KEY", new DSStringField(), false, 0);

    /** START_DATE_TIME */
    public static final DSKey START_DATE_TIME = new DSKey("START_DATE_TIME", new DSDateField(DATE_FORMAT.LONG, TIME_FORMAT.HMS), false);

    /** TEMP_RESULT_LOT_NO */
    public static final DSKey TEMP_RESULT_LOT_NO = new DSKey("TEMP_RESULT_LOT_NO", new DSStringField(), false, 0);

    /** TICKET_LINE_NO */
    public static final DSKey TICKET_LINE_NO = new DSKey("TICKET_LINE_NO", new DSNumberField(0), false);

    /** TICKET_NO */
    public static final DSKey TICKET_NO = new DSKey("TICKET_NO", new DSStringField(), false, 0);

    /** TOTAL_ITEM */
    public static final DSKey TOTAL_ITEM = new DSKey("TOTAL_ITEM", new DSNumberField(0), false);

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
    public ShippingCustomerOutDataSheet(DataSheetFactory dsf, Locale locale)
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

// $Id$
package jp.co.daifuku.wms.retrieval.rft.datasheet;

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
public class RetrievalOutDataSheet
        extends BasicDSModel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DATASHEET_NAME */
    private static final String DATASHEET_NAME = "RetrievalOutDataSheet";

    /** AREA_LOCATION */
    public static final DSKey AREA_LOCATION = new DSKey("AREA_LOCATION", new DSStringField(), false, 0);

    /** AREA_NO */
    public static final DSKey AREA_NO = new DSKey("AREA_NO", new DSStringField(), false, 0);

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

    /** CUSTOMER_CODE1 */
    public static final DSKey CUSTOMER_CODE1 = new DSKey("CUSTOMER_CODE1", new DSStringField(), false, 0);

    /** CUSTOMER_CODE2 */
    public static final DSKey CUSTOMER_CODE2 = new DSKey("CUSTOMER_CODE2", new DSStringField(), false, 0);

    /** CUSTOMER_CODE3 */
    public static final DSKey CUSTOMER_CODE3 = new DSKey("CUSTOMER_CODE3", new DSStringField(), false, 0);

    /** CUSTOMER_CODE4 */
    public static final DSKey CUSTOMER_CODE4 = new DSKey("CUSTOMER_CODE4", new DSStringField(), false, 0);

    /** CUSTOMER_NAME */
    public static final DSKey CUSTOMER_NAME = new DSKey("CUSTOMER_NAME", new DSStringField(), false, 0);

    /** CUSTOMER_NAME1 */
    public static final DSKey CUSTOMER_NAME1 = new DSKey("CUSTOMER_NAME1", new DSStringField(), false, 0);

    /** CUSTOMER_NAME2 */
    public static final DSKey CUSTOMER_NAME2 = new DSKey("CUSTOMER_NAME2", new DSStringField(), false, 0);

    /** CUSTOMER_NAME3 */
    public static final DSKey CUSTOMER_NAME3 = new DSKey("CUSTOMER_NAME3", new DSStringField(), false, 0);

    /** CUSTOMER_NAME4 */
    public static final DSKey CUSTOMER_NAME4 = new DSKey("CUSTOMER_NAME4", new DSStringField(), false, 0);

    /** ENTERING_QTY */
    public static final DSKey ENTERING_QTY = new DSKey("ENTERING_QTY", new DSNumberField(0), false);

    /** GUIDANCE_MESSAGE */
    public static final DSKey GUIDANCE_MESSAGE = new DSKey("GUIDANCE_MESSAGE", new DSStringField(), false, 0);

    /** IS_INSPECTION_COMPLETE */
    public static final DSKey IS_INSPECTION_COMPLETE = new DSKey("IS_INSPECTION_COMPLETE", new DSBooleanField(), false);

    /** ITEM_CODE */
    public static final DSKey ITEM_CODE = new DSKey("ITEM_CODE", new DSStringField(), false, 0);

    /** ITEM_CODE_INPUT_ENABLED */
    public static final DSKey ITEM_CODE_INPUT_ENABLED = new DSKey("ITEM_CODE_INPUT_ENABLED", new DSBooleanField(), false);

    /** ITEM_NAME */
    public static final DSKey ITEM_NAME = new DSKey("ITEM_NAME", new DSStringField(), false, 2);

    /** ITF */
    public static final DSKey ITF = new DSKey("ITF", new DSStringField(), false, 0);

    /** JAN */
    public static final DSKey JAN = new DSKey("JAN", new DSStringField(), false, 0);

    /** LINE_NO */
    public static final DSKey LINE_NO = new DSKey("LINE_NO", new DSStringField(), false, 0);

    /** LOCATION */
    public static final DSKey LOCATION = new DSKey("LOCATION", new DSStringField(), false, 0);

    /** LOCATION_INPUT_ENABLED */
    public static final DSKey LOCATION_INPUT_ENABLED = new DSKey("LOCATION_INPUT_ENABLED", new DSBooleanField(), false);

    /** LOCATION_STYLE */
    public static final DSKey LOCATION_STYLE = new DSKey("LOCATION_STYLE", new DSStringField(), false, 0);

    /** ORDER_NO */
    public static final DSKey ORDER_NO = new DSKey("ORDER_NO", new DSStringField(), false, 0);

    /** ORDER_NO1 */
    public static final DSKey ORDER_NO1 = new DSKey("ORDER_NO1", new DSStringField(), false, 0);

    /** ORDER_NO1_STATUS */
    public static final DSKey ORDER_NO1_STATUS = new DSKey("ORDER_NO1_STATUS", new DSStringField(), false, 0);

    /** ORDER_NO2 */
    public static final DSKey ORDER_NO2 = new DSKey("ORDER_NO2", new DSStringField(), false, 0);

    /** ORDER_NO2_STATUS */
    public static final DSKey ORDER_NO2_STATUS = new DSKey("ORDER_NO2_STATUS", new DSStringField(), false, 0);

    /** ORDER_NO3 */
    public static final DSKey ORDER_NO3 = new DSKey("ORDER_NO3", new DSStringField(), false, 0);

    /** ORDER_NO3_STATUS */
    public static final DSKey ORDER_NO3_STATUS = new DSKey("ORDER_NO3_STATUS", new DSStringField(), false, 0);

    /** ORDER_NO4 */
    public static final DSKey ORDER_NO4 = new DSKey("ORDER_NO4", new DSStringField(), false, 0);

    /** ORDER_NO4_STATUS */
    public static final DSKey ORDER_NO4_STATUS = new DSKey("ORDER_NO4_STATUS", new DSStringField(), false, 0);

    /** ORDER_SERIAL_NO */
    public static final DSKey ORDER_SERIAL_NO = new DSKey("ORDER_SERIAL_NO", new DSStringField(), false, 0);

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

    /** SHORTAGE_CASE_QTY */
    public static final DSKey SHORTAGE_CASE_QTY = new DSKey("SHORTAGE_CASE_QTY", new DSNumberField(0), false);

    /** SHORTAGE_PIECE_QTY */
    public static final DSKey SHORTAGE_PIECE_QTY = new DSKey("SHORTAGE_PIECE_QTY", new DSNumberField(0), false);

    /** SKIP_ENABLED */
    public static final DSKey SKIP_ENABLED = new DSKey("SKIP_ENABLED", new DSBooleanField(), false);

    /** START_DATE_TIME */
    public static final DSKey START_DATE_TIME = new DSKey("START_DATE_TIME", new DSDateField(DATE_FORMAT.LONG, TIME_FORMAT.HMS), false);

    /** SURVIVAL_RECORD */
    public static final DSKey SURVIVAL_RECORD = new DSKey("SURVIVAL_RECORD", new DSNumberField(0), false);

    /** TEMP_RESULT_LOT_NO */
    public static final DSKey TEMP_RESULT_LOT_NO = new DSKey("TEMP_RESULT_LOT_NO", new DSStringField(), false, 0);

    /** TOTAL_RECORD */
    public static final DSKey TOTAL_RECORD = new DSKey("TOTAL_RECORD", new DSNumberField(0), false);

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
    public RetrievalOutDataSheet(DataSheetFactory dsf, Locale locale)
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

// $Id: ReceivingOutDataSheet.java 8041 2012-05-17 09:39:40Z nagao $
package jp.co.daifuku.wms.receiving.rft.datasheet;

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
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision: 8041 $, $Date: 2012-05-17 18:39:40 +0900 (木, 17 5 2012) $
 * @author  HighTune.
 * @author  Last commit: $Author: nagao $
 */
public class ReceivingOutDataSheet
        extends BasicDSModel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DATASHEET_NAME */
    private static final String DATASHEET_NAME = "ReceivingOutDataSheet";

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

    /** DISP_PLAN_DATE */
    public static final DSKey DISP_PLAN_DATE = new DSKey("DISP_PLAN_DATE", new DSDateField(DATE_FORMAT.SHORT, null), false);

    /** ENTERING_QTY */
    public static final DSKey ENTERING_QTY = new DSKey("ENTERING_QTY", new DSNumberField(0), false);

    /** IS_AREA_SEARCH_FINISH */
    public static final DSKey IS_AREA_SEARCH_FINISH = new DSKey("IS_AREA_SEARCH_FINISH", new DSBooleanField(), false);

    /** ITEM_CODE */
    public static final DSKey ITEM_CODE = new DSKey("ITEM_CODE", new DSStringField(), false, 0);

    /** ITEM_NAME */
    public static final DSKey ITEM_NAME = new DSKey("ITEM_NAME", new DSStringField(), false, 2);

    /** ITF */
    public static final DSKey ITF = new DSKey("ITF", new DSStringField(), false, 0);

    /** JAN */
    public static final DSKey JAN = new DSKey("JAN", new DSStringField(), false, 0);

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

    /** SUPPLIER_CODE */
    public static final DSKey SUPPLIER_CODE = new DSKey("SUPPLIER_CODE", new DSStringField(), false, 0);

    /** SUPPLIER_NAME */
    public static final DSKey SUPPLIER_NAME = new DSKey("SUPPLIER_NAME", new DSStringField(), false, 0);

    /** TEMP_AREA_NO */
    public static final DSKey TEMP_AREA_NO = new DSKey("TEMP_AREA_NO", new DSStringField(), false, 0);

    /** TEMP_RESULT_LOT_NO */
    public static final DSKey TEMP_RESULT_LOT_NO = new DSKey("TEMP_RESULT_LOT_NO", new DSStringField(), false, 0);

    /** TICKET_LINE_NO */
    public static final DSKey TICKET_LINE_NO = new DSKey("TICKET_LINE_NO", new DSNumberField(0), false);

    /** TICKET_NO */
    public static final DSKey TICKET_NO = new DSKey("TICKET_NO", new DSStringField(), false, 0);

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
    public ReceivingOutDataSheet(DataSheetFactory dsf, Locale locale)
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
        return "$Id: ReceivingOutDataSheet.java 8041 2012-05-17 09:39:40Z nagao $";
    }
}
//end of class

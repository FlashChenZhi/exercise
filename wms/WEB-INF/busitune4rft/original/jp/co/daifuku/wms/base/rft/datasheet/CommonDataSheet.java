// $Id$
package jp.co.daifuku.wms.base.rft.datasheet;

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
public class CommonDataSheet
        extends BasicDSModel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DATASHEET_NAME */
    private static final String DATASHEET_NAME = "CommonDataSheet";

    /** CASE_INPUT_ENABLED */
    public static final DSKey CASE_INPUT_ENABLED = new DSKey("CASE_INPUT_ENABLED", new DSBooleanField(), false);

    /** CASE_PIECE_COLOR */
    public static final DSKey CASE_PIECE_COLOR = new DSKey("CASE_PIECE_COLOR", new DSStringField(), false, 0);

    /** CASE_PIECE_DIVISION */
    public static final DSKey CASE_PIECE_DIVISION = new DSKey("CASE_PIECE_DIVISION", new DSStringField(), false, 0);

    /** CASE_PIECE_LABEL */
    public static final DSKey CASE_PIECE_LABEL = new DSKey("CASE_PIECE_LABEL", new DSStringField(), false, 0);

    /** FOCUS_FIELD */
    public static final DSKey FOCUS_FIELD = new DSKey("FOCUS_FIELD", new DSStringField(), false, 0);

    /** HAS_MASTER_PACK */
    public static final DSKey HAS_MASTER_PACK = new DSKey("HAS_MASTER_PACK", new DSBooleanField(), false);

    /** INPUT_TYPE */
    public static final DSKey INPUT_TYPE = new DSKey("INPUT_TYPE", new DSStringField(), false, 0);

    /** IS_EXCESS */
    public static final DSKey IS_EXCESS = new DSKey("IS_EXCESS", new DSBooleanField(), false);

    /** JOB_DETAILS */
    public static final DSKey JOB_DETAILS = new DSKey("JOB_DETAILS", new DSStringField(), false, 0);

    /** JOB_TYPE */
    public static final DSKey JOB_TYPE = new DSKey("JOB_TYPE", new DSStringField(), false, 0);

    /** JOB_TYPE_NAME */
    public static final DSKey JOB_TYPE_NAME = new DSKey("JOB_TYPE_NAME", new DSStringField(), false, 0);

    /** MISS_SCAN_COUNT */
    public static final DSKey MISS_SCAN_COUNT = new DSKey("MISS_SCAN_COUNT", new DSNumberField(0), false);

    /** PASSWORD */
    public static final DSKey PASSWORD = new DSKey("PASSWORD", new DSStringField(), false, 0);

    /** RE_START */
    public static final DSKey RE_START = new DSKey("RE_START", new DSBooleanField(), false);

    /** START_DATE_TIME */
    public static final DSKey START_DATE_TIME = new DSKey("START_DATE_TIME", new DSDateField(DATE_FORMAT.LONG, TIME_FORMAT.HMS), false);

    /** TITLE */
    public static final DSKey TITLE = new DSKey("TITLE", new DSStringField(), false, 0);

    /** USER_ID */
    public static final DSKey USER_ID = new DSKey("USER_ID", new DSStringField(), false, 0);

    /** USER_NAME */
    public static final DSKey USER_NAME = new DSKey("USER_NAME", new DSStringField(), false, 0);

    /** WORKING_ID */
    public static final DSKey WORKING_ID = new DSKey("WORKING_ID", new DSStringField(), false, 0);

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
    public CommonDataSheet(DataSheetFactory dsf, Locale locale)
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

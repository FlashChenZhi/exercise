// $Id: PlanDateListDataSheet.java 8041 2012-05-17 09:39:40Z nagao $
package jp.co.daifuku.wms.base.rft.datasheet;

/*
 * Copyright(c) 2000-2011 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import jp.co.daifuku.busitune.rft.haisurf.BasicDSModel;
import jp.co.daifuku.busitune.rft.haisurf.DSDateField;
import jp.co.daifuku.busitune.rft.haisurf.DSKey;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision: 8041 $, $Date: 2012-05-17 18:39:40 +0900 (木, 17 5 2012) $
 * @author  HighTune.
 * @author  Last commit: $Author: nagao $
 */
public class PlanDateListDataSheet
        extends BasicDSModel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DATASHEET_NAME */
    private static final String DATASHEET_NAME = "PlanDateListDataSheet";

    /** PLAN_DATE */
    public static final DSKey PLAN_DATE = new DSKey("PLAN_DATE", new DSDateField(DATE_FORMAT.LONG, null), true);

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
    public PlanDateListDataSheet(DataSheetFactory dsf, Locale locale)
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
        return "$Id: PlanDateListDataSheet.java 8041 2012-05-17 09:39:40Z nagao $";
    }
}
//end of class

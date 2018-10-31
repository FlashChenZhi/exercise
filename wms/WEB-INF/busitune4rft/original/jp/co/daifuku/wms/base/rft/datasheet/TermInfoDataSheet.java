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
import jp.co.daifuku.busitune.rft.haisurf.DSKey;
import jp.co.daifuku.busitune.rft.haisurf.DSStringField;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class TermInfoDataSheet
        extends BasicDSModel
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DATASHEET_NAME */
    private static final String DATASHEET_NAME = "TermInfoDataSheet";

    /** INVENTORY_CONSIGNOR */
    public static final DSKey INVENTORY_CONSIGNOR = new DSKey("INVENTORY_CONSIGNOR", new DSStringField(), false, 0);

    /** INVENTORY_CP_MODE */
    public static final DSKey INVENTORY_CP_MODE = new DSKey("INVENTORY_CP_MODE", new DSStringField(), false, 0);

    /** INVENTORY_INSPECTION_MODE */
    public static final DSKey INVENTORY_INSPECTION_MODE = new DSKey("INVENTORY_INSPECTION_MODE", new DSBooleanField(), false);

    /** INVENTORY_ITF_TO_JAN */
    public static final DSKey INVENTORY_ITF_TO_JAN = new DSKey("INVENTORY_ITF_TO_JAN", new DSBooleanField(), false);

    /** IP_ADDRESS */
    public static final DSKey IP_ADDRESS = new DSKey("IP_ADDRESS", new DSStringField(), false, 0);

    /** MOVE_RETRIEVAL_CONSIGNOR */
    public static final DSKey MOVE_RETRIEVAL_CONSIGNOR = new DSKey("MOVE_RETRIEVAL_CONSIGNOR", new DSStringField(), false, 0);

    /** MOVE_RETRIEVAL_CP_MODE */
    public static final DSKey MOVE_RETRIEVAL_CP_MODE = new DSKey("MOVE_RETRIEVAL_CP_MODE", new DSStringField(), false, 0);

    /** MOVE_RETRIEVAL_INSPECTION_MODE */
    public static final DSKey MOVE_RETRIEVAL_INSPECTION_MODE = new DSKey("MOVE_RETRIEVAL_INSPECTION_MODE", new DSBooleanField(), false);

    /** MOVE_RETRIEVAL_ITF_TO_JAN */
    public static final DSKey MOVE_RETRIEVAL_ITF_TO_JAN = new DSKey("MOVE_RETRIEVAL_ITF_TO_JAN", new DSBooleanField(), false);

    /** MOVE_STORAGE_CONSIGNOR */
    public static final DSKey MOVE_STORAGE_CONSIGNOR = new DSKey("MOVE_STORAGE_CONSIGNOR", new DSStringField(), false, 0);

    /** MOVE_STORAGE_CP_MODE */
    public static final DSKey MOVE_STORAGE_CP_MODE = new DSKey("MOVE_STORAGE_CP_MODE", new DSStringField(), false, 0);

    /** MOVE_STORAGE_INSPECTION_MODE */
    public static final DSKey MOVE_STORAGE_INSPECTION_MODE = new DSKey("MOVE_STORAGE_INSPECTION_MODE", new DSBooleanField(), false);

    /** MOVE_STORAGE_ITF_TO_JAN */
    public static final DSKey MOVE_STORAGE_ITF_TO_JAN = new DSKey("MOVE_STORAGE_ITF_TO_JAN", new DSBooleanField(), false);

    /** NO_PLAN_RETRIEVAL_CONSIGNOR */
    public static final DSKey NO_PLAN_RETRIEVAL_CONSIGNOR = new DSKey("NO_PLAN_RETRIEVAL_CONSIGNOR", new DSStringField(), false, 0);

    /** NO_PLAN_RETRIEVAL_CP_MODE */
    public static final DSKey NO_PLAN_RETRIEVAL_CP_MODE = new DSKey("NO_PLAN_RETRIEVAL_CP_MODE", new DSStringField(), false, 0);

    /** NO_PLAN_RETRIEVAL_INSPECTION_MODE */
    public static final DSKey NO_PLAN_RETRIEVAL_INSPECTION_MODE = new DSKey("NO_PLAN_RETRIEVAL_INSPECTION_MODE", new DSBooleanField(), false);

    /** NO_PLAN_RETRIEVAL_ITF_TO_JAN */
    public static final DSKey NO_PLAN_RETRIEVAL_ITF_TO_JAN = new DSKey("NO_PLAN_RETRIEVAL_ITF_TO_JAN", new DSBooleanField(), false);

    /** NO_PLAN_STORAGE_CONSIGNOR */
    public static final DSKey NO_PLAN_STORAGE_CONSIGNOR = new DSKey("NO_PLAN_STORAGE_CONSIGNOR", new DSStringField(), false, 0);

    /** NO_PLAN_STORAGE_CP_MODE */
    public static final DSKey NO_PLAN_STORAGE_CP_MODE = new DSKey("NO_PLAN_STORAGE_CP_MODE", new DSStringField(), false, 0);

    /** NO_PLAN_STORAGE_INSPECTION_MODE */
    public static final DSKey NO_PLAN_STORAGE_INSPECTION_MODE = new DSKey("NO_PLAN_STORAGE_INSPECTION_MODE", new DSBooleanField(), false);

    /** NO_PLAN_STORAGE_ITF_TO_JAN */
    public static final DSKey NO_PLAN_STORAGE_ITF_TO_JAN = new DSKey("NO_PLAN_STORAGE_ITF_TO_JAN", new DSBooleanField(), false);

    /** PASSWORD_INPUT_MODE */
    public static final DSKey PASSWORD_INPUT_MODE = new DSKey("PASSWORD_INPUT_MODE", new DSBooleanField(), false);

    /** RECEIVING_CONSIGNOR */
    public static final DSKey RECEIVING_CONSIGNOR = new DSKey("RECEIVING_CONSIGNOR", new DSStringField(), false, 0);

    /** RECEIVING_CP_MODE */
    public static final DSKey RECEIVING_CP_MODE = new DSKey("RECEIVING_CP_MODE", new DSStringField(), false, 0);

    /** RECEIVING_ITF_TO_JAN */
    public static final DSKey RECEIVING_ITF_TO_JAN = new DSKey("RECEIVING_ITF_TO_JAN", new DSBooleanField(), false);

    /** RETRIEVAL_CONSIGNOR */
    public static final DSKey RETRIEVAL_CONSIGNOR = new DSKey("RETRIEVAL_CONSIGNOR", new DSStringField(), false, 0);

    /** RETRIEVAL_CP_MODE */
    public static final DSKey RETRIEVAL_CP_MODE = new DSKey("RETRIEVAL_CP_MODE", new DSStringField(), false, 0);

    /** RETRIEVAL_INSPECTION_MODE */
    public static final DSKey RETRIEVAL_INSPECTION_MODE = new DSKey("RETRIEVAL_INSPECTION_MODE", new DSBooleanField(), false);

    /** RETRIEVAL_ITF_TO_JAN */
    public static final DSKey RETRIEVAL_ITF_TO_JAN = new DSKey("RETRIEVAL_ITF_TO_JAN", new DSBooleanField(), false);

    /** SHIPPING_CONSIGNOR */
    public static final DSKey SHIPPING_CONSIGNOR = new DSKey("SHIPPING_CONSIGNOR", new DSStringField(), false, 0);

    /** SHIPPING_CP_MODE */
    public static final DSKey SHIPPING_CP_MODE = new DSKey("SHIPPING_CP_MODE", new DSStringField(), false, 0);

    /** SHIPPING_ITF_TO_JAN */
    public static final DSKey SHIPPING_ITF_TO_JAN = new DSKey("SHIPPING_ITF_TO_JAN", new DSBooleanField(), false);

    /** SORTING_CONSIGNOR */
    public static final DSKey SORTING_CONSIGNOR = new DSKey("SORTING_CONSIGNOR", new DSStringField(), false, 0);

    /** SORTING_CP_MODE */
    public static final DSKey SORTING_CP_MODE = new DSKey("SORTING_CP_MODE", new DSStringField(), false, 0);

    /** SORTING_INSPECTION_MODE */
    public static final DSKey SORTING_INSPECTION_MODE = new DSKey("SORTING_INSPECTION_MODE", new DSBooleanField(), false);

    /** SORTING_ITF_TO_JAN */
    public static final DSKey SORTING_ITF_TO_JAN = new DSKey("SORTING_ITF_TO_JAN", new DSBooleanField(), false);

    /** STORAGE_CONSIGNOR */
    public static final DSKey STORAGE_CONSIGNOR = new DSKey("STORAGE_CONSIGNOR", new DSStringField(), false, 0);

    /** STORAGE_CP_MODE */
    public static final DSKey STORAGE_CP_MODE = new DSKey("STORAGE_CP_MODE", new DSStringField(), false, 0);

    /** STORAGE_INSPECTION_MODE */
    public static final DSKey STORAGE_INSPECTION_MODE = new DSKey("STORAGE_INSPECTION_MODE", new DSBooleanField(), false);

    /** STORAGE_ITF_TO_JAN */
    public static final DSKey STORAGE_ITF_TO_JAN = new DSKey("STORAGE_ITF_TO_JAN", new DSBooleanField(), false);

    /** TERMINAL_NO */
    public static final DSKey TERMINAL_NO = new DSKey("TERMINAL_NO", new DSStringField(), false, 0);

    /** TERMINAL_TYPE */
    public static final DSKey TERMINAL_TYPE = new DSKey("TERMINAL_TYPE", new DSStringField(), false, 0);

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
    public TermInfoDataSheet(DataSheetFactory dsf, Locale locale)
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

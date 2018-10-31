// $Id$
package jp.co.daifuku.wms.retrieval.rft.display;

/*
 * Copyright(c) 2000-2011 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import jp.co.daifuku.busitune.rft.haisurf.AbstractHSBusiness;
import jp.co.daifuku.busitune.rft.haisurf.DataSheetModel;
import jp.co.daifuku.busitune.rft.haisurf.HSResult;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.rft.HaiSurfExceptionHandler;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.RftConst;
import jp.co.daifuku.wms.retrieval.rft.datasheet.OrderDataSheet;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class RetOrderInputBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "RetOrderInput";

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
    public RetOrderInputBusiness()
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
    public HSResult back_Click(DataSheetFactory dsf, Locale locale)
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
    public HSResult ent_Click(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        OrderDataSheet orderDataSheet = new OrderDataSheet(dsf, locale);

        // input validation.
        if (StringUtil.isBlank(orderDataSheet.getText(OrderDataSheet.ORDER_NO1)))
        {
            setBeep(RftConst.BEEP);
            setFocus("OrderNo1");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

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
    public HSResult list_Click(DataSheetFactory dsf, Locale locale)
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
    public HSResult orderNo1_Enter(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        OrderDataSheet orderDataSheet = new OrderDataSheet(dsf, locale);

        // input validation.
        if (StringUtil.isBlank(orderDataSheet.getText(OrderDataSheet.ORDER_NO1)))
        {
            setBeep(RftConst.BEEP);
            setFocus("OrderNo1");
            return new HSResult(ResultConst.VALIDATE_ERROR);
        }

        // set output parameters.
        orderDataSheet.setValue(OrderDataSheet.ORDER_NO1_INPUT_ENABLED, false);
        orderDataSheet.setValue(OrderDataSheet.ORDER_NO2_INPUT_ENABLED, true);
        orderDataSheet.setValue(OrderDataSheet.FOCUS_FIELD, RftConst.FIELD_NAME_ORDERNO2);

        // set focus.
        setFocus("OrderNo2");

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
    public HSResult orderNo2_Enter(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        OrderDataSheet orderDataSheet = new OrderDataSheet(dsf, locale);

        // set output parameters.
        orderDataSheet.setValue(OrderDataSheet.ORDER_NO2_INPUT_ENABLED, false);
        orderDataSheet.setValue(OrderDataSheet.ORDER_NO3_INPUT_ENABLED, true);
        orderDataSheet.setValue(OrderDataSheet.FOCUS_FIELD, RftConst.FIELD_NAME_ORDERNO3);

        // set focus.
        setFocus("OrderNo3");

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
    public HSResult orderNo3_Enter(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        OrderDataSheet orderDataSheet = new OrderDataSheet(dsf, locale);

        // set output parameters.
        orderDataSheet.setValue(OrderDataSheet.ORDER_NO3_INPUT_ENABLED, false);
        orderDataSheet.setValue(OrderDataSheet.ORDER_NO4_INPUT_ENABLED, true);
        orderDataSheet.setValue(OrderDataSheet.FOCUS_FIELD, RftConst.FIELD_NAME_ORDERNO4);

        // set focus.
        setFocus("OrderNo4");

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
    public HSResult orderNo4_Enter(DataSheetFactory dsf, Locale locale)
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
    public HSResult clear_Click(DataSheetFactory dsf, Locale locale)
    {
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

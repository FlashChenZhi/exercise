// $Id$
package jp.co.daifuku.wms.base.rft.schedule;

/*
 * Copyright(c) 2000-2011 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.busitune.rft.haisurf.da.AbstractHSScheduler;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.schedule.TopPageSCHParams;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.BasicDatabaseFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class TopPageSCH
        extends AbstractHSScheduler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

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
     * Constructor to create SCH object
     * @param conn Database Connection
     * @param parent Caller Class
     * @param locale Browser Locale
     */
    public TopPageSCH(Connection conn, Class parent, Locale locale)
    {
        super(conn, parent, locale);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * Receives data that was entered on the screen as parameter,
     * and retrieves output data of the List Area from the database, and return it.<BR>
     *
     * @param p Receiving condition for display data saved in <CODE>ScheduleParams</CODE>.<BR>
     * @return retrieved data saved in <CODE>Params</CODE> array.<BR>
     *          Returns 0 array if no applicable record was found.<BR>
     *          Displays up to the maximum number of display if the retrieved output exceeds the maximum.<BR>
     *          Retuns null if an error occurs in the middle of entering condition.<BR>
     * @throws CommonException Reports if an unexpected exception occurs when checkging.
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        try
        {
            List<Params> outparams = new ArrayList<Params>();
            return outparams;
        }
        finally
        {
            // close the db handler
        }
    }

    /**
     * Receieves the items that was input on the screen as a parameter and start schedule.<BR>
     *
     * @param startParams Setting items saved in <CODE>ScheduleParams</CODE> array.<BR>
     * @throws CommonException Reports all the exceptions.
     * @return Return true if the schedule ends normally and return false if it failed so.
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        return true;
    }

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

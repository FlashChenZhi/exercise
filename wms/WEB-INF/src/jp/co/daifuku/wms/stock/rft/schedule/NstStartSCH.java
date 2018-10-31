// $Id: NstStartSCH.java 8041 2012-05-17 09:39:40Z nagao $
package jp.co.daifuku.wms.stock.rft.schedule;

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
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.BasicDatabaseFinder;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.stock.rft.schedule.NstStartSCHParams;

/**
 *
 * @version $Revision: 8041 $, $Date: 2012-05-17 18:39:40 +0900 (木, 17 5 2012) $
 * @author  HighTune.
 * @author  Last commit: $Author: nagao $
 */
public class NstStartSCH
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
    public NstStartSCH(Connection conn, Class parent, Locale locale)
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
        return "$Id: NstStartSCH.java 8041 2012-05-17 09:39:40Z nagao $";
    }
}
//end of class

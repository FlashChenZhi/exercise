// $Id$
package jp.co.daifuku.wms.base.rft.display;

/*
 * Copyright(c) 2000-2011 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.busitune.rft.haisurf.AbstractHSBusiness;
import jp.co.daifuku.busitune.rft.haisurf.DataSheetModel;
import jp.co.daifuku.busitune.rft.haisurf.HSResult;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.rft.HaiSurfExceptionHandler;
import jp.co.daifuku.wms.base.rft.ResultConst;
import jp.co.daifuku.wms.base.rft.datasheet.CommonDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.MainMenuDataSheet;
import jp.co.daifuku.wms.base.rft.datasheet.TermInfoDataSheet;
import jp.co.daifuku.wms.base.rft.schedule.MainMenuSCH;
import jp.co.daifuku.wms.base.rft.schedule.MainMenuSCHParams;
import jp.co.sharedsys.basis.helper.DataSheetFactory;

/**
 *
 * @version $Revision$, $Date$
 * @author  HighTune.
 * @author  Last commit: $Author$
 */
public class MainMenuBusiness
        extends AbstractHSBusiness
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** SCREEN_NAME */
    private static final String SCREEN_NAME = "MainMenu";

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
    public MainMenuBusiness()
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
        // initialize DataSheets.
        MainMenuDataSheet mainMenuDataSheet = new MainMenuDataSheet(dsf, locale);
        TermInfoDataSheet termInfoDataSheet = new TermInfoDataSheet(dsf, locale);

        Connection conn = null;
        MainMenuSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new MainMenuSCH(conn, this.getClass(), locale);

            // set input parameters.
            MainMenuSCHParams inparam = new MainMenuSCHParams();
            inparam.set(MainMenuSCHParams.TERMINAL_NO, termInfoDataSheet.getValue(TermInfoDataSheet.TERMINAL_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // set output parameters.
            mainMenuDataSheet.setValue(DataSheetModel.SIZE, outparams.size());
            for (int i = 0; i < outparams.size(); i++)
            {
                Params outparam = outparams.get(i);
                mainMenuDataSheet.setValue(MainMenuDataSheet.MENU_NAME, outparam.get(MainMenuSCHParams.MENU_NAME), i);
                mainMenuDataSheet.setValue(MainMenuDataSheet.MENU_CODE, outparam.get(MainMenuSCHParams.MENU_CODE), i);
                mainMenuDataSheet.setValue(MainMenuDataSheet.JOB_TYPE, outparam.get(MainMenuSCHParams.JOB_TYPE), i);
                mainMenuDataSheet.setValue(MainMenuDataSheet.JOB_DETAILS, outparam.get(MainMenuSCHParams.JOB_DETAILS), i);
            }

            // initialize DataSheet view.
            mainMenuDataSheet.initView();

            // set result code.
            return new HSResult(ResultConst.NORMAL);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return HaiSurfExceptionHandler.getResult(ex, this.getClass(), dsf, locale);
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    /**
     * callback at screen event.
     *
     * @param dsf data sheet
     * @param locale Terminal locale
     * @return executed result
     */
    public HSResult exit_Click(DataSheetFactory dsf, Locale locale)
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
    public HSResult menuNo_Enter(DataSheetFactory dsf, Locale locale)
    {
        // initialize DataSheets.
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        MainMenuDataSheet mainMenuDataSheet = new MainMenuDataSheet(dsf, locale);

        Connection conn = null;
        MainMenuSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new MainMenuSCH(conn, this.getClass(), locale);

            // set input parameters.
            MainMenuSCHParams inparam = new MainMenuSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(MainMenuSCHParams.MENU_CODE, mainMenuDataSheet.getValue(MainMenuDataSheet.MENU_CODE));

            // SCH call.
            sch.startSCH(inparam);

            // commit.
            conn.commit();

            // set output parameters.
            commonDataSheet.setValue(CommonDataSheet.JOB_TYPE, mainMenuDataSheet.getValue(MainMenuDataSheet.JOB_TYPE));
            commonDataSheet.setValue(CommonDataSheet.JOB_DETAILS, mainMenuDataSheet.getValue(MainMenuDataSheet.JOB_DETAILS));

            // set result code.
            return new HSResult(ResultConst.NORMAL);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            DBUtil.rollback(conn);
            return HaiSurfExceptionHandler.getResult(ex, this.getClass(), dsf, locale);
        }
        finally
        {
            DBUtil.close(conn);
        }
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
        CommonDataSheet commonDataSheet = new CommonDataSheet(dsf, locale);
        MainMenuDataSheet mainMenuDataSheet = new MainMenuDataSheet(dsf, locale);

        Connection conn = null;
        MainMenuSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_RFT_NAME);
            sch = new MainMenuSCH(conn, this.getClass(), locale);

            // set input parameters.
            MainMenuSCHParams inparam = new MainMenuSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(MainMenuSCHParams.MENU_CODE, mainMenuDataSheet.getValue(MainMenuDataSheet.MENU_CODE));

            // SCH call.
            sch.startSCH(inparam);

            // commit.
            conn.commit();

            // set output parameters.
            commonDataSheet.setValue(CommonDataSheet.JOB_TYPE, mainMenuDataSheet.getValue(MainMenuDataSheet.JOB_TYPE));
            commonDataSheet.setValue(CommonDataSheet.JOB_DETAILS, mainMenuDataSheet.getValue(MainMenuDataSheet.JOB_DETAILS));
            commonDataSheet.setValue(CommonDataSheet.TITLE, mainMenuDataSheet.getValue(MainMenuDataSheet.MENU_NAME));

            // set result code.
            return new HSResult(ResultConst.NORMAL);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            DBUtil.rollback(conn);
            return HaiSurfExceptionHandler.getResult(ex, this.getClass(), dsf, locale);
        }
        finally
        {
            DBUtil.close(conn);
        }
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

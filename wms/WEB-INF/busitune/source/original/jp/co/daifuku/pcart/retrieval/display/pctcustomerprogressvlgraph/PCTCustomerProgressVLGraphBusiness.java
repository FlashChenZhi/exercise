// $Id: PCTCustomerProgressVLGraphBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.pcart.retrieval.display.pctcustomerprogressvlgraph;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.bluedog.webapp.ViewState;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.Constants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.retrieval.display.pctcustomerprogressvlgraph.PCTCustomerProgressVLGraph;
import jp.co.daifuku.pcart.retrieval.schedule.PCTCustomerProgressVLGraphSCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTCustomerProgressVLGraphSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class PCTCustomerProgressVLGraphBusiness
        extends PCTCustomerProgressVLGraph
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_PCTCustomerProgress(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE = new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress(LST_PROGRESS_RATE) */
    private static final ListCellKey KEY_LST_PROGRESS_RATE = new ListCellKey("LST_PROGRESS_RATE", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress(LST_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_ORDER_COUNT = new ListCellKey("LST_ORDER_COUNT", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress(LST_BOX_COUNT) */
    private static final ListCellKey KEY_LST_BOX_COUNT = new ListCellKey("LST_BOX_COUNT", new NumberCellColumn(0), true, false);

    /** lst_PCTCustomerProgress(LST_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LINE_COUNT = new ListCellKey("LST_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress(LST_LOT_COUNT) */
    private static final ListCellKey KEY_LST_LOT_COUNT = new ListCellKey("LST_LOT_COUNT", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress keys */
    private static final ListCellKey[] LST_PCTCUSTOMERPROGRESS_KEYS = {
        KEY_LST_CUSTOMER_CODE,
        KEY_LST_CUSTOMER_NAME,
        KEY_LST_PROGRESS_RATE,
        KEY_LST_ORDER_COUNT,
        KEY_LST_BOX_COUNT,
        KEY_LST_LINE_COUNT,
        KEY_LST_LOT_COUNT,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_PCTCustomerProgress */
    private ScrollListCellModel _lcm_lst_PCTCustomerProgress;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTCustomerProgressVLGraphBusiness()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        setTitle();

        // save a popup event source.
        viewState.setString(_KEY_POPUPSOURCE, request.getParameter(_KEY_POPUPSOURCE));

        // initialize pulldown models.
        initializePulldownModels();

        // process call.
        page_Load_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        // initialize components.
        initializeComponents();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Display_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Display_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PrevPage_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PrevPage_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_NextPage_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_NextPage_Click_Process();
    }

    /**
     * メニューへ遷移します。

     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        // セッションからコネクションを削除する
        SessionUtil.deleteSession(getSession());
        // メニューへ遷移します
        forward(BusinessClassHelper.getSubMenuPath(viewState.getString(Constants.M_MENUID_KEY)));
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
    /**
     *
     * @throws Exception
     */
    private void initializeComponents()
            throws Exception
    {
        // get locale.
        Locale locale = httpRequest.getLocale();

        // initialize lst_PCTCustomerProgress.
        _lcm_lst_PCTCustomerProgress = new ScrollListCellModel(lst_PCTCustomerProgress, LST_PCTCUSTOMERPROGRESS_KEYS, locale);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_PROGRESS_RATE, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_ORDER_COUNT, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_BOX_COUNT, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_LINE_COUNT, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_LOT_COUNT, true);

    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
    }

    /**
     *
     * @throws Exception
     */
    private void dispose()
            throws Exception
    {
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTCustomerProgress_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerProgressVLGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressVLGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressVLGraphSCHParams inparam = new PCTCustomerProgressVLGraphSCHParams();
            inparam.set(PCTCustomerProgressVLGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTCustomerProgress.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTCustomerProgress.getNewLine();
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(PCTCustomerProgressVLGraphSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(PCTCustomerProgressVLGraphSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_PROGRESS_RATE, outparam.get(PCTCustomerProgressVLGraphSCHParams.PROGRESS_RATE));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.BUTTON_FLAG));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.LOT_QTY));
                lst_PCTCustomerProgress_SetLineToolTip(line);
                _lcm_lst_PCTCustomerProgress.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerProgressVLGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressVLGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressVLGraphSCHParams inparam = new PCTCustomerProgressVLGraphSCHParams();
            inparam.set(PCTCustomerProgressVLGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTCustomerProgress.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTCustomerProgress.getNewLine();
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(PCTCustomerProgressVLGraphSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(PCTCustomerProgressVLGraphSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_PROGRESS_RATE, outparam.get(PCTCustomerProgressVLGraphSCHParams.PROGRESS_RATE));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.BUTTON_FLAG));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.LOT_QTY));
                lst_PCTCustomerProgress_SetLineToolTip(line);
                _lcm_lst_PCTCustomerProgress.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PrevPage_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerProgressVLGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressVLGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressVLGraphSCHParams inparam = new PCTCustomerProgressVLGraphSCHParams();
            inparam.set(PCTCustomerProgressVLGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_PREVIOUS_PAGE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTCustomerProgress.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTCustomerProgress.getNewLine();
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(PCTCustomerProgressVLGraphSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(PCTCustomerProgressVLGraphSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_PROGRESS_RATE, outparam.get(PCTCustomerProgressVLGraphSCHParams.PROGRESS_RATE));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.BUTTON_FLAG));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.LOT_QTY));
                lst_PCTCustomerProgress_SetLineToolTip(line);
                _lcm_lst_PCTCustomerProgress.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_NextPage_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerProgressVLGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressVLGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressVLGraphSCHParams inparam = new PCTCustomerProgressVLGraphSCHParams();
            inparam.set(PCTCustomerProgressVLGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_NEXT_PAGE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTCustomerProgress.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTCustomerProgress.getNewLine();
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(PCTCustomerProgressVLGraphSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(PCTCustomerProgressVLGraphSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_PROGRESS_RATE, outparam.get(PCTCustomerProgressVLGraphSCHParams.PROGRESS_RATE));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.BUTTON_FLAG));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTCustomerProgressVLGraphSCHParams.LOT_QTY));
                lst_PCTCustomerProgress_SetLineToolTip(line);
                _lcm_lst_PCTCustomerProgress.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    /**
     * 画面タイトルを設定します。
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void setTitle()
            throws Exception
    {
        // httpRequestからメニュー用パラメータを取得する
        String menuparam = this.getHttpRequest().getParameter(Constants.MENUPARAM);
        if (menuparam != null)
        {
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);

            // ViewStateへ保存する
            viewState.setString(Constants.M_TITLE_KEY, title);
            viewState.setString(Constants.M_FUNCTIONID_KEY, functionID);
            viewState.setString(Constants.M_MENUID_KEY, menuID);

            lbl_SettingName.setResourceKey(title);
        }
        else if (this.getTitleResourceKey() != null && !this.getTitleResourceKey().equals(""))
        {
            // リストボックスの場合はpage.xmlから取得する
            lbl_SettingName.setResourceKey(this.getTitleResourceKey());
        }
        else if (viewState.getString(Constants.M_TITLE_KEY) != null)
        {
            // 2画面遷移から戻ってきた場合はViewStateから取得する
            lbl_SettingName.setResourceKey(viewState.getString(Constants.M_TITLE_KEY));
        }
    }

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
        return "";
    }

}
//end of class

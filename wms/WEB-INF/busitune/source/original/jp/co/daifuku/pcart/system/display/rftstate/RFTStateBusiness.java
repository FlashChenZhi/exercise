// $Id: RFTStateBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.pcart.system.display.rftstate;

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
import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.system.display.rftstate.RFTState;
import jp.co.daifuku.pcart.system.schedule.RFTStateSCH;
import jp.co.daifuku.pcart.system.schedule.RFTStateSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
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
public class RFTStateBusiness
        extends RFTState
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_RFTState(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new StringCellColumn(), true, false);

    /** lst_RFTState(LST_DETAIL) */
    private static final ListCellKey KEY_LST_DETAIL = new ListCellKey("LST_DETAIL", new StringCellColumn(), true, false);

    /** lst_RFTState(LST_RFT_NO) */
    private static final ListCellKey KEY_LST_RFT_NO = new ListCellKey("LST_RFT_NO", new StringCellColumn(), true, false);

    /** lst_RFTState(LST_TERMINAL_TYPE) */
    private static final ListCellKey KEY_LST_TERMINAL_TYPE = new ListCellKey("LST_TERMINAL_TYPE", new StringCellColumn(), true, false);

    /** lst_RFTState(LST_STATUS) */
    private static final ListCellKey KEY_LST_STATUS = new ListCellKey("LST_STATUS", new StringCellColumn(), true, false);

    /** lst_RFTState(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME = new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_RFTState(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_RFTState keys */
    private static final ListCellKey[] LST_RFTSTATE_KEYS = {
        KEY_LST_SELECT,
        KEY_LST_DETAIL,
        KEY_LST_RFT_NO,
        KEY_LST_TERMINAL_TYPE,
        KEY_LST_STATUS,
        KEY_LST_USER_NAME,
        KEY_LST_AREA_NO,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_RFTState */
    private ListCellModel _lcm_lst_RFTState;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RFTStateBusiness()
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
    public void lst_RFTState_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_RFTState.getActiveCol();

        // choose process.
        if (_lcm_lst_RFTState.getColumnIndex(KEY_LST_SELECT) == activeCol)
        {
            // process call.
            lst_Select_Click_Process();
        }
        else if (_lcm_lst_RFTState.getColumnIndex(KEY_LST_DETAIL) == activeCol)
        {
            // process call.
            lst_Detail_Click_Process();
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ReDisplay_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ReDisplay_Click_Process();
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

        // initialize lst_RFTState.
        _lcm_lst_RFTState = new ListCellModel(lst_RFTState, LST_RFTSTATE_KEYS, locale);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_DETAIL, true);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_RFT_NO, true);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_TERMINAL_TYPE, true);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_STATUS, true);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_USER_NAME, true);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_AREA_NO, true);
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
    private void lst_RFTState_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0033", KEY_LST_USER_NAME);
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
        RFTStateSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RFTStateSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RFTStateSCHParams inparam = new RFTStateSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RFTState.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RFTState.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(RFTStateSCHParams.SELECT));
                line.setValue(KEY_LST_DETAIL, outparam.get(RFTStateSCHParams.DETAIL));
                line.setValue(KEY_LST_RFT_NO, outparam.get(RFTStateSCHParams.RFT_NO));
                line.setValue(KEY_LST_TERMINAL_TYPE, outparam.get(RFTStateSCHParams.TERMINAL_TYPE));
                line.setValue(KEY_LST_STATUS, outparam.get(RFTStateSCHParams.STATUS));
                line.setValue(KEY_LST_USER_NAME, outparam.get(RFTStateSCHParams.USER_NAME));
                line.setValue(KEY_LST_AREA_NO, outparam.get(RFTStateSCHParams.AREA_NO));
                lst_RFTState_SetLineToolTip(line);
                _lcm_lst_RFTState.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_RFTState.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_RFTState.get(i);
                lst_RFTState.setCurrentRow(i);
                lst_RFTState.setCellEnabled(_lcm_lst_RFTState.getColumnIndex(KEY_LST_SELECT), true);
                lst_RFTState.setCellEnabled(_lcm_lst_RFTState.getColumnIndex(KEY_LST_DETAIL), true);
                lst_RFTState_SetLineToolTip(clearLine);
                _lcm_lst_RFTState.set(i, clearLine);
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
    private void lst_Select_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_RFTState.getActiveRow();
        lst_RFTState.setCurrentRow(row);
        ListCellLine line = _lcm_lst_RFTState.get(row);

        // output display.
        line.setValue(KEY_LST_TERMINAL_TYPE, line.getValue(KEY_LST_TERMINAL_TYPE));
        _lcm_lst_RFTState.set(row, line);
        lst_RFTState_SetLineToolTip(line);
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Detail_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_RFTState.getActiveRow();
        lst_RFTState.setCurrentRow(row);
        ListCellLine line = _lcm_lst_RFTState.get(row);

        // output display.
        line.setValue(KEY_LST_TERMINAL_TYPE, line.getValue(KEY_LST_TERMINAL_TYPE));
        _lcm_lst_RFTState.set(row, line);
        lst_RFTState_SetLineToolTip(line);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ReDisplay_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RFTStateSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RFTStateSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RFTStateSCHParams inparam = new RFTStateSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RFTState.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RFTState.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(RFTStateSCHParams.SELECT));
                line.setValue(KEY_LST_DETAIL, outparam.get(RFTStateSCHParams.DETAIL));
                line.setValue(KEY_LST_RFT_NO, outparam.get(RFTStateSCHParams.RFT_NO));
                line.setValue(KEY_LST_TERMINAL_TYPE, outparam.get(RFTStateSCHParams.TERMINAL_TYPE));
                line.setValue(KEY_LST_STATUS, outparam.get(RFTStateSCHParams.STATUS));
                line.setValue(KEY_LST_USER_NAME, outparam.get(RFTStateSCHParams.USER_NAME));
                line.setValue(KEY_LST_AREA_NO, outparam.get(RFTStateSCHParams.AREA_NO));
                lst_RFTState_SetLineToolTip(line);
                _lcm_lst_RFTState.add(line);
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

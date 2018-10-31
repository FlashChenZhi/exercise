// $Id: StorageProgressBusiness.java 7677 2010-03-18 00:44:12Z okayama $
package jp.co.daifuku.wms.storage.display.progress;

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
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
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
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.storage.display.progress.StorageProgress;
import jp.co.daifuku.wms.storage.display.progress.ViewStateKeys;
import jp.co.daifuku.wms.storage.schedule.StorageProgressSCH;
import jp.co.daifuku.wms.storage.schedule.StorageProgressSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7677 $, $Date:: 2010-03-18 09:44:12 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class StorageProgressBusiness
        extends StorageProgress
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_StorageProgress_up(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_StorageProgress_up(LST_DETAIL_COUNT) */
    private static final ListCellKey KEY_LST_DETAIL_COUNT = new ListCellKey("LST_DETAIL_COUNT", new StringCellColumn(), true, false);

    /** lst_StorageProgress_up(LST_STORAGE_COUNT) */
    private static final ListCellKey KEY_LST_STORAGE_COUNT = new ListCellKey("LST_STORAGE_COUNT", new StringCellColumn(), true, false);

    /** lst_StorageProgress_up(LST_CASE_QTY) */
    private static final ListCellKey KEY_LST_CASE_QTY = new ListCellKey("LST_CASE_QTY", new StringCellColumn(), true, false);

    /** lst_StorageProgress_up(LST_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PIECE_QTY = new ListCellKey("LST_PIECE_QTY", new StringCellColumn(), true, false);

    /** lst_StorageProgress_up(LST_SHORTAGE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY = new ListCellKey("LST_SHORTAGE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StorageProgress_down(LST_PLAN_DAY_2) */
    private static final ListCellKey KEY_LST_PLAN_DAY_2 = new ListCellKey("LST_PLAN_DAY_2", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_StorageProgress_down(LST_DETAIL_COUNT_2) */
    private static final ListCellKey KEY_LST_DETAIL_COUNT_2 = new ListCellKey("LST_DETAIL_COUNT_2", new StringCellColumn(), true, false);

    /** lst_StorageProgress_down(LST_STORAGE_COUNT_2) */
    private static final ListCellKey KEY_LST_STORAGE_COUNT_2 = new ListCellKey("LST_STORAGE_COUNT_2", new StringCellColumn(), true, false);

    /** lst_StorageProgress_down(LST_CASE_QTY_2) */
    private static final ListCellKey KEY_LST_CASE_QTY_2 = new ListCellKey("LST_CASE_QTY_2", new StringCellColumn(), true, false);

    /** lst_StorageProgress_down(LST_PIECE_QTY_2) */
    private static final ListCellKey KEY_LST_PIECE_QTY_2 = new ListCellKey("LST_PIECE_QTY_2", new StringCellColumn(), true, false);

    /** lst_StorageProgress_down(LST_SHORTAGE_QTY_2) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY_2 = new ListCellKey("LST_SHORTAGE_QTY_2", new NumberCellColumn(0), true, false);

    /** lst_StorageProgress_up keys */
    private static final ListCellKey[] LST_STORAGEPROGRESS_UP_KEYS = {
        KEY_LST_PLAN_DAY,
        KEY_LST_DETAIL_COUNT,
        KEY_LST_CASE_QTY,
        KEY_LST_SHORTAGE_QTY,
        KEY_LST_STORAGE_COUNT,
        KEY_LST_PIECE_QTY,
    };

    /** lst_StorageProgress_down keys */
    private static final ListCellKey[] LST_STORAGEPROGRESS_DOWN_KEYS = {
        KEY_LST_PLAN_DAY_2,
        KEY_LST_DETAIL_COUNT_2,
        KEY_LST_CASE_QTY_2,
        KEY_LST_SHORTAGE_QTY_2,
        KEY_LST_STORAGE_COUNT_2,
        KEY_LST_PIECE_QTY_2,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel ProgressDisplay */
    private RadioButtonGroup _grp_ProgressDisplay;

    /** ListCellModel lst_StorageProgress_up */
    private ListCellModel _lcm_lst_StorageProgress_up;

    /** ListCellModel lst_StorageProgress_down */
    private ListCellModel _lcm_lst_StorageProgress_down;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public StorageProgressBusiness()
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

        // process call.
        page_Initialize_Process();
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

        // initialize ProgressDisplay.
        _grp_ProgressDisplay = new RadioButtonGroup(new RadioButton[]{rdo_ProgressDisplayAuto, rdo_ProgressDisplayManual}, locale);

        // initialize lst_StorageProgress_up.
        _lcm_lst_StorageProgress_up = new ListCellModel(lst_StorageProgress_up, LST_STORAGEPROGRESS_UP_KEYS, locale);
        _lcm_lst_StorageProgress_up.setToolTipVisible(KEY_LST_PLAN_DAY, false);
        _lcm_lst_StorageProgress_up.setToolTipVisible(KEY_LST_DETAIL_COUNT, false);
        _lcm_lst_StorageProgress_up.setToolTipVisible(KEY_LST_STORAGE_COUNT, false);
        _lcm_lst_StorageProgress_up.setToolTipVisible(KEY_LST_CASE_QTY, false);
        _lcm_lst_StorageProgress_up.setToolTipVisible(KEY_LST_PIECE_QTY, false);
        _lcm_lst_StorageProgress_up.setToolTipVisible(KEY_LST_SHORTAGE_QTY, false);

        // initialize lst_StorageProgress_down.
        _lcm_lst_StorageProgress_down = new ListCellModel(lst_StorageProgress_down, LST_STORAGEPROGRESS_DOWN_KEYS, locale);
        _lcm_lst_StorageProgress_down.setToolTipVisible(KEY_LST_PLAN_DAY_2, false);
        _lcm_lst_StorageProgress_down.setToolTipVisible(KEY_LST_DETAIL_COUNT_2, false);
        _lcm_lst_StorageProgress_down.setToolTipVisible(KEY_LST_STORAGE_COUNT_2, false);
        _lcm_lst_StorageProgress_down.setToolTipVisible(KEY_LST_CASE_QTY_2, false);
        _lcm_lst_StorageProgress_down.setToolTipVisible(KEY_LST_PIECE_QTY_2, false);
        _lcm_lst_StorageProgress_down.setToolTipVisible(KEY_LST_SHORTAGE_QTY_2, false);
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
    private void lst_StorageProgress_up_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_StorageProgress_down_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(rdo_ProgressDisplayAuto);
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
        StorageProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StorageProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StorageProgressSCHParams inparam = new StorageProgressSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_StorageProgress_up.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StorageProgress_up.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(StorageProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(StorageProgressSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_STORAGE_COUNT, outparam.get(StorageProgressSCHParams.STORAGE_COUNT));
                line.setValue(KEY_LST_CASE_QTY, outparam.get(StorageProgressSCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(StorageProgressSCHParams.PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(StorageProgressSCHParams.SHORTAGE_QTY));
                lst_StorageProgress_up_SetLineToolTip(line);
                _lcm_lst_StorageProgress_up.add(line);
            }

            // clear.
            rdo_ProgressDisplayAuto.setChecked(true);
            txt_ProgressRate_up.setReadOnly(true);
            txt_ProgressRate_down.setReadOnly(true);
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
        // input validation.
        rdo_ProgressDisplayAuto.validate(false);
        txt_ProgressRate_up.validate(this, false);
        txt_ProgressRate_down.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StorageProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StorageProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StorageProgressSCHParams inparam = new StorageProgressSCHParams();
            inparam.set(StorageProgressSCHParams.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
            inparam.set(StorageProgressSCHParams.PROGRESS_RATE_UP, txt_ProgressRate_up.getValue());
            inparam.set(StorageProgressSCHParams.PROGRESS_RATE_DOWN, txt_ProgressRate_down.getValue());
            inparam.set(StorageProgressSCHParams.PROCESS_FLAG, "PROCESS_FLAG");

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_StorageProgress_up.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StorageProgress_up.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(StorageProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(StorageProgressSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_STORAGE_COUNT, outparam.get(StorageProgressSCHParams.STORAGE_COUNT));
                line.setValue(KEY_LST_CASE_QTY, outparam.get(StorageProgressSCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(StorageProgressSCHParams.PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(StorageProgressSCHParams.SHORTAGE_QTY));
                viewState.setObject(ViewStateKeys.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_UP, txt_ProgressRate_up.getValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_DOWN, txt_ProgressRate_down.getValue());
                lst_StorageProgress_up_SetLineToolTip(line);
                _lcm_lst_StorageProgress_up.add(line);
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
        // input validation.
        rdo_ProgressDisplayAuto.validate(false);
        txt_ProgressRate_up.validate(this, false);
        txt_ProgressRate_down.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StorageProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StorageProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StorageProgressSCHParams inparam = new StorageProgressSCHParams();
            inparam.set(StorageProgressSCHParams.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
            inparam.set(StorageProgressSCHParams.PROGRESS_RATE_UP, txt_ProgressRate_up.getValue());
            inparam.set(StorageProgressSCHParams.PROGRESS_RATE_DOWN, txt_ProgressRate_down.getValue());
            inparam.set(StorageProgressSCHParams.PROCESS_FLAG, "PROCESS_FLAG");

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_StorageProgress_up.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StorageProgress_up.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(StorageProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(StorageProgressSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_STORAGE_COUNT, outparam.get(StorageProgressSCHParams.STORAGE_COUNT));
                line.setValue(KEY_LST_CASE_QTY, outparam.get(StorageProgressSCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(StorageProgressSCHParams.PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(StorageProgressSCHParams.SHORTAGE_QTY));
                viewState.setObject(ViewStateKeys.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_UP, txt_ProgressRate_up.getValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_DOWN, txt_ProgressRate_down.getValue());
                lst_StorageProgress_up_SetLineToolTip(line);
                _lcm_lst_StorageProgress_up.add(line);
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
        // input validation.
        rdo_ProgressDisplayAuto.validate(false);
        txt_ProgressRate_up.validate(this, false);
        txt_ProgressRate_down.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StorageProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StorageProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StorageProgressSCHParams inparam = new StorageProgressSCHParams();
            inparam.set(StorageProgressSCHParams.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
            inparam.set(StorageProgressSCHParams.PROGRESS_RATE_UP, txt_ProgressRate_up.getValue());
            inparam.set(StorageProgressSCHParams.PROGRESS_RATE_DOWN, txt_ProgressRate_down.getValue());
            inparam.set(StorageProgressSCHParams.PROCESS_FLAG, "PROCESS_FLAG");

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_StorageProgress_up.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StorageProgress_up.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(StorageProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(StorageProgressSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_STORAGE_COUNT, outparam.get(StorageProgressSCHParams.STORAGE_COUNT));
                line.setValue(KEY_LST_CASE_QTY, outparam.get(StorageProgressSCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(StorageProgressSCHParams.PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(StorageProgressSCHParams.SHORTAGE_QTY));
                viewState.setObject(ViewStateKeys.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_UP, txt_ProgressRate_up.getValue());
                viewState.setObject(ViewStateKeys.PROGRESS_RATE_DOWN, txt_ProgressRate_down.getValue());
                lst_StorageProgress_up_SetLineToolTip(line);
                _lcm_lst_StorageProgress_up.add(line);
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

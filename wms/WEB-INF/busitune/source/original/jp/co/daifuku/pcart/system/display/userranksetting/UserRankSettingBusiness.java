// $Id: UserRankSettingBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.pcart.system.display.userranksetting;

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
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.model.PullDownModel;
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
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.pcart.system.display.userranksetting.UserRankSetting;
import jp.co.daifuku.pcart.system.display.userranksetting.ViewStateKeys;
import jp.co.daifuku.pcart.system.listbox.userid.LstUserIdParams;
import jp.co.daifuku.pcart.system.schedule.UserRankSettingSCH;
import jp.co.daifuku.pcart.system.schedule.UserRankSettingSCHParams;
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
public class UserRankSettingBusiness
        extends UserRankSetting
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_UserLevelSet(HIDDEN_LAST_UP_DATE) */
    private static final ListCellKey KEY_HIDDEN_LAST_UP_DATE = new ListCellKey("HIDDEN_LAST_UP_DATE", new StringCellColumn(), false, false);

    /** lst_UserLevelSet(LST_USER_ID) */
    private static final ListCellKey KEY_LST_USER_ID = new ListCellKey("LST_USER_ID", new StringCellColumn(), true, false);

    /** lst_UserLevelSet(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME = new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_UserLevelSet(LST_PRE_LEVEL) */
    private static final ListCellKey KEY_LST_PRE_LEVEL = new ListCellKey("LST_PRE_LEVEL", new StringCellColumn(), true, false);

    /** lst_UserLevelSet(LST_SET_LEVEL) */
    private static final ListCellKey KEY_LST_SET_LEVEL = new ListCellKey("LST_SET_LEVEL", new StringCellColumn(), true, true);

    /** lst_UserLevelSet keys */
    private static final ListCellKey[] LST_USERLEVELSET_KEYS = {
        KEY_HIDDEN_LAST_UP_DATE,
        KEY_LST_USER_ID,
        KEY_LST_USER_NAME,
        KEY_LST_PRE_LEVEL,
        KEY_LST_SET_LEVEL,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Level */
    private DefaultPullDownModel _pdm_pul_Level;

    /** ListCellModel lst_UserLevelSet */
    private ListCellModel _lcm_lst_UserLevelSet;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public UserRankSettingBusiness()
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
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        // get event source.
        DialogParameters dialogParams = ((DialogEvent)e).getDialogParameters();
        String eventSource = dialogParams.getParameter(_KEY_POPUPSOURCE);
        if (StringUtil.isBlank(eventSource))
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_SearchFrom_Click"))
        {
            // process call.
            btn_SearchFrom_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_SearchTo_Click"))
        {
            // process call.
            btn_SearchTo_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchFrom_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchFrom_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchTo_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchTo_Click_Process();
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
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Clear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Set_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ListClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListClear_Click_Process();
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
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_Level.
        _pdm_pul_Level = new DefaultPullDownModel(pul_Level, locale, ui);

        // initialize lst_UserLevelSet.
        _lcm_lst_UserLevelSet = new ListCellModel(lst_UserLevelSet, LST_USERLEVELSET_KEYS, locale);
        _lcm_lst_UserLevelSet.setToolTipVisible(KEY_LST_USER_ID, false);
        _lcm_lst_UserLevelSet.setToolTipVisible(KEY_LST_USER_NAME, false);
        _lcm_lst_UserLevelSet.setToolTipVisible(KEY_LST_PRE_LEVEL, false);
        _lcm_lst_UserLevelSet.setToolTipVisible(KEY_LST_SET_LEVEL, false);
    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // load pul_Level.
            _pdm_pul_Level.init(conn);
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
    private void dispose()
            throws Exception
    {
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_UserLevelSet_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_UserIdFrom);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_UserIdFrom.setValue(null);
        txt_UserIdTo.setValue(null);
        _pdm_pul_Level.setSelectedValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchFrom_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstUserIdParams inparam = new LstUserIdParams();
        inparam.set(LstUserIdParams.USER_ID_FROM, txt_UserIdFrom.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchFrom_Click");
        redirect("/pcart/system/listbox/userid/LstUserId.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchFrom_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstUserIdParams outparam = new LstUserIdParams(dialogParams);
        txt_UserIdFrom.setValue(outparam.get(LstUserIdParams.USER_ID_FROM));

        // set focus.
        setFocus(txt_UserIdFrom);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchTo_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstUserIdParams inparam = new LstUserIdParams();
        inparam.set(LstUserIdParams.USER_ID_TO, txt_UserIdTo.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchTo_Click");
        redirect("/pcart/system/listbox/userid/LstUserId.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchTo_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstUserIdParams outparam = new LstUserIdParams(dialogParams);
        txt_UserIdTo.setValue(outparam.get(LstUserIdParams.USER_ID_TO));

        // set focus.
        setFocus(txt_UserIdTo);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_UserIdFrom.validate(this, true);
        txt_UserIdTo.validate(this, true);
        pul_Level.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        UserRankSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new UserRankSettingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            UserRankSettingSCHParams inparam = new UserRankSettingSCHParams();
            inparam.set(UserRankSettingSCHParams.USER_ID_FROM, txt_UserIdFrom.getValue());
            inparam.set(UserRankSettingSCHParams.USER_ID_TO, txt_UserIdTo.getValue());
            inparam.set(UserRankSettingSCHParams.LEVEL, _pdm_pul_Level.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_UserLevelSet.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_UserLevelSet.getNewLine();
                line.setValue(KEY_LST_USER_ID, outparam.get(UserRankSettingSCHParams.USER_ID));
                line.setValue(KEY_LST_USER_NAME, outparam.get(UserRankSettingSCHParams.USER_NAME));
                line.setValue(KEY_LST_PRE_LEVEL, outparam.get(UserRankSettingSCHParams.PRE_LEVEL));
                line.setValue(KEY_LST_SET_LEVEL, outparam.get(UserRankSettingSCHParams.PRE_LEVEL));
                viewState.setObject(ViewStateKeys.VS_USER_ID_FROM, txt_UserIdFrom.getValue());
                viewState.setObject(ViewStateKeys.VS_USER_ID_TO, txt_UserIdTo.getValue());
                viewState.setObject(ViewStateKeys.VS_LEVEL, _pdm_pul_Level.getSelectedValue());
                line.setValue(KEY_HIDDEN_LAST_UP_DATE, outparam.get(UserRankSettingSCHParams.LAST_UPDATE_DATE));
                lst_UserLevelSet_SetLineToolTip(line);
                _lcm_lst_UserLevelSet.add(line);
            }

            // clear.
            btn_Set.setEnabled(true);
            btn_ListClear.setEnabled(true);
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_UserIdFrom.setValue(null);
        txt_UserIdTo.setValue(null);
        _pdm_pul_Level.setSelectedValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_UserLevelSet.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_UserLevelSet.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            existEditedRow = true;
            lst_UserLevelSet.setCurrentRow(i);
            lst_UserLevelSet.validate(checkline.getIndex(KEY_LST_SET_LEVEL), false);
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        UserRankSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new UserRankSettingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_UserLevelSet.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_UserLevelSet.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                UserRankSettingSCHParams lineparam = new UserRankSettingSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(UserRankSettingSCHParams.USER_ID, line.getValue(KEY_LST_USER_ID));
                lineparam.set(UserRankSettingSCHParams.USER_NAME, line.getValue(KEY_LST_USER_NAME));
                lineparam.set(UserRankSettingSCHParams.PRE_LEVEL, line.getValue(KEY_LST_PRE_LEVEL));
                lineparam.set(UserRankSettingSCHParams.SET_LEVEL, line.getValue(KEY_LST_SET_LEVEL));
                lineparam.set(UserRankSettingSCHParams.LAST_UPDATE_DATE, line.getValue(KEY_HIDDEN_LAST_UP_DATE));
                lineparam.set(UserRankSettingSCHParams.LEVEL, viewState.getObject(ViewStateKeys.VS_LEVEL));
                lineparam.set(UserRankSettingSCHParams.USER_ID_FROM, viewState.getObject(ViewStateKeys.VS_USER_ID_FROM));
                lineparam.set(UserRankSettingSCHParams.USER_ID_TO, viewState.getObject(ViewStateKeys.VS_USER_ID_TO));
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_UserLevelSet.resetEditRow();
                _lcm_lst_UserLevelSet.resetHighlight();
                _lcm_lst_UserLevelSet.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_UserLevelSet.resetEditRow();
            _lcm_lst_UserLevelSet.resetHighlight();

            // set input parameters.
            UserRankSettingSCHParams inparam = new UserRankSettingSCHParams();
            inparam.set(UserRankSettingSCHParams.LEVEL, viewState.getObject(ViewStateKeys.VS_LEVEL));
            inparam.set(UserRankSettingSCHParams.USER_ID_FROM, viewState.getObject(ViewStateKeys.VS_USER_ID_FROM));
            inparam.set(UserRankSettingSCHParams.USER_ID_TO, viewState.getObject(ViewStateKeys.VS_USER_ID_TO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_UserLevelSet.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_UserLevelSet.getNewLine();
                line.setValue(KEY_LST_USER_ID, outparam.get(UserRankSettingSCHParams.USER_ID));
                line.setValue(KEY_LST_USER_NAME, outparam.get(UserRankSettingSCHParams.USER_NAME));
                line.setValue(KEY_LST_PRE_LEVEL, outparam.get(UserRankSettingSCHParams.PRE_LEVEL));
                line.setValue(KEY_LST_SET_LEVEL, outparam.get(UserRankSettingSCHParams.SET_LEVEL));
                line.setValue(KEY_HIDDEN_LAST_UP_DATE, outparam.get(UserRankSettingSCHParams.LAST_UPDATE_DATE));
                lst_UserLevelSet_SetLineToolTip(line);
                _lcm_lst_UserLevelSet.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ListClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_UserLevelSet.clear();
        btn_Set.setEnabled(false);
        btn_ListClear.setEnabled(false);
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

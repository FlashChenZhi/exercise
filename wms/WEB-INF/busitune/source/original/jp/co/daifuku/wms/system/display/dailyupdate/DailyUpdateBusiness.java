// $Id: DailyUpdateBusiness.java 7403 2010-03-05 12:51:08Z shibamoto $
package jp.co.daifuku.wms.system.display.dailyupdate;

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
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
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
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.system.display.dailyupdate.DailyUpdate;
import jp.co.daifuku.wms.system.schedule.DailyUpdateSCH;
import jp.co.daifuku.wms.system.schedule.DailyUpdateSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7403 $, $Date:: 2010-03-05 21:51:08 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class DailyUpdateBusiness
        extends DailyUpdate
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_DailyUpdate(LST_STATUS) */
    private static final ListCellKey KEY_LST_STATUS = new ListCellKey("LST_STATUS", new StringCellColumn(), true, false);

    /** lst_DailyUpdate(LST_REASON) */
    private static final ListCellKey KEY_LST_REASON = new ListCellKey("LST_REASON", new StringCellColumn(), true, false);

    /** lst_DailyUpdate(LST_NG_HAPPENED_POINT) */
    private static final ListCellKey KEY_LST_NG_HAPPENED_POINT = new ListCellKey("LST_NG_HAPPENED_POINT", new StringCellColumn(), true, false);

    /** lst_DailyUpdate keys */
    private static final ListCellKey[] LST_DAILYUPDATE_KEYS = {
        KEY_LST_STATUS,
        KEY_LST_REASON,
        KEY_LST_NG_HAPPENED_POINT,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel NoWorkInfomation */
    private RadioButtonGroup _grp_NoWorkInfomation;

    /** ListCellModel lst_DailyUpdate */
    private ScrollListCellModel _lcm_lst_DailyUpdate;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public DailyUpdateBusiness()
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
    public void btn_DvdCopy_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_DvdCopy_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Start_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Start_Click_Process();
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

        // initialize NoWorkInfomation.
        _grp_NoWorkInfomation = new RadioButtonGroup(new RadioButton[]{rdo_NoWorkInfomation_Delete, rdo_NoWorkInfomation_CarryOver}, locale);

        // initialize lst_DailyUpdate.
        _lcm_lst_DailyUpdate = new ScrollListCellModel(lst_DailyUpdate, LST_DAILYUPDATE_KEYS, locale);
        _lcm_lst_DailyUpdate.setToolTipVisible(KEY_LST_STATUS, false);
        _lcm_lst_DailyUpdate.setToolTipVisible(KEY_LST_REASON, false);
        _lcm_lst_DailyUpdate.setToolTipVisible(KEY_LST_NG_HAPPENED_POINT, false);
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
    private void lst_DailyUpdate_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_InWorkDate);
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
        DailyUpdateSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DailyUpdateSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            DailyUpdateSCHParams inparam = new DailyUpdateSCHParams();
            inparam.set(DailyUpdateSCHParams.WORK_DATE, txt_InWorkDate.getValue());
            inparam.set(DailyUpdateSCHParams.NO_WORK_INFOMATION, _grp_NoWorkInfomation.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_DailyUpdate.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_DailyUpdate.getNewLine();
                line.setValue(KEY_LST_STATUS, outparam.get(DailyUpdateSCHParams.STATUS));
                line.setValue(KEY_LST_REASON, outparam.get(DailyUpdateSCHParams.REASON));
                line.setValue(KEY_LST_NG_HAPPENED_POINT, outparam.get(DailyUpdateSCHParams.NG_HAPPENED_POINT));
                lst_DailyUpdate_SetLineToolTip(line);
                _lcm_lst_DailyUpdate.add(line);
            }

            // clear.
            btn_ReDisplay.setEnabled(true);
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
    private void btn_DvdCopy_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        DailyUpdateSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DailyUpdateSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            DailyUpdateSCHParams inparam = new DailyUpdateSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(DailyUpdateSCHParams.WORK_DATE, txt_InWorkDate.getValue());
            inparam.set(DailyUpdateSCHParams.NO_WORK_INFOMATION, _grp_NoWorkInfomation.getSelectedValue());

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());
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
    private void btn_Start_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        DailyUpdateSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DailyUpdateSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            DailyUpdateSCHParams inparam = new DailyUpdateSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(DailyUpdateSCHParams.WORK_DATE, txt_InWorkDate.getValue());
            inparam.set(DailyUpdateSCHParams.NO_WORK_INFOMATION, _grp_NoWorkInfomation.getSelectedValue());

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_InWorkDate.getStringValue(), "");
            part11List.add(txt_ExecutionDate.getStringValue(), txt_ExecutionTime.getStringValue(), "");
            part11List.add(SystemInParameter.UNSTART_DELETE, "", rdo_NoWorkInfomation_Delete.getChecked());
            part11List.add(SystemInParameter.UNSTART_HOLD, "", rdo_NoWorkInfomation_CarryOver.getChecked());
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_InWorkDate.setValue(null);
        rdo_NoWorkInfomation_Delete.setChecked(true);
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
        DailyUpdateSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DailyUpdateSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            DailyUpdateSCHParams inparam = new DailyUpdateSCHParams();
            inparam.set(DailyUpdateSCHParams.WORK_DATE, txt_InWorkDate.getValue());
            inparam.set(DailyUpdateSCHParams.NO_WORK_INFOMATION, _grp_NoWorkInfomation.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_DailyUpdate.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_DailyUpdate.getNewLine();
                line.setValue(KEY_LST_STATUS, outparam.get(DailyUpdateSCHParams.STATUS));
                line.setValue(KEY_LST_REASON, outparam.get(DailyUpdateSCHParams.REASON));
                line.setValue(KEY_LST_NG_HAPPENED_POINT, outparam.get(DailyUpdateSCHParams.NG_HAPPENED_POINT));
                lst_DailyUpdate_SetLineToolTip(line);
                _lcm_lst_DailyUpdate.add(line);
            }

            // clear.
            btn_ReDisplay.setEnabled(true);
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
